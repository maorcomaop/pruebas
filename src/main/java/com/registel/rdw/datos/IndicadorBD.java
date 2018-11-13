
package com.registel.rdw.datos;

import com.registel.rdw.logica.CapacidadTransportadora;
import com.registel.rdw.logica.IndicadorCapacidadTransportadora;
import com.registel.rdw.logica.IndicadorCumplimientoRuta;
import com.registel.rdw.logica.IndicadorPasajeroHora;
import com.registel.rdw.logica.IndicadorProduccion;
import com.registel.rdw.logica.IndicadorDescuentoDePasajeros;
import com.registel.rdw.logica.PasajeroHora;
import com.registel.rdw.utils.Restriction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class IndicadorBD {
    
    // Consulta indice de produccion IPK para registros que no presentan ruta,
    // contempla ademas el numero de pasajeros logrado hasta el momento.    
    public static IndicadorProduccion indicadorProduccionSinRuta(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                    + " sum(ir.num_llegada - ir.numeracion_base_salida) as PASAJEROS,"
                    + " sum(ir.distancia_metros) as DISTANCIA_RECORRIDA"
                    + " FROM tbl_informacion_registradora as ir"
                    + " where ir.fecha_ingreso = ?"
                    + " and ir.fk_ruta IS NULL";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                IndicadorProduccion ip = new IndicadorProduccion();
                
                ip.setNombreRuta("RND");    // Ruta no detectada
                ip.setProduccion(rs.getLong("PASAJEROS"));
                ip.setDistanciaRecorrida(rs.getLong("DISTANCIA_RECORRIDA"));
                ip.setTipoRegistro(IndicadorProduccion.PRODUCCION_TOTAL_SIN_RUTA);
                
                long dist  = ip.getDistanciaRecorrida();
                long prd   = ip.getProduccion(); 
                double ipk = (dist == 0) ? 0 : (double) prd / dist;
                ip.setIpk(ipk);
                
                return ip;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta indice de produccion IPK para cada ruta en una fecha especifica,
    // contempla ademas el numero de pasajeros logrado hasta el momento.
    // Registros son agrupados por ruta.
    public static ArrayList<IndicadorProduccion> indicadorProduccion(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        IndicadorProduccion ip_na = indicadorProduccionSinRuta(fecha);
        
        String sql = "SELECT"
                    + " r.nombre as NOMBRE_RUTA,"
                    + " sum(ir.num_llegada - ir.numeracion_base_salida) as PASAJEROS,"
                    + " sum(ir.distancia_metros) as DISTANCIA_RECORRIDA"
                    + " FROM tbl_informacion_registradora as ir"
                    + " INNER JOIN tbl_ruta as r on"
                    + "   ir.fk_ruta = r.pk_ruta and r.estado = 1"
                    + " WHERE ir.fecha_ingreso = ?"
                    + " GROUP BY ir.fk_ruta"
                    + " ORDER BY PASAJEROS desc";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            ArrayList<IndicadorProduccion> lst = new ArrayList<IndicadorProduccion>();
            long total_dist = 0, total_prd = 0;
            
            while (rs.next()) {
                IndicadorProduccion ip = new IndicadorProduccion();
                ip.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                ip.setProduccion(rs.getLong("PASAJEROS"));
                ip.setDistanciaRecorrida(rs.getLong("DISTANCIA_RECORRIDA"));
                
                long dist = ip.getDistanciaRecorrida();
                long prd  = ip.getProduccion();
                double ipk = (dist == 0) ? 0 : (double) prd / dist;
                ip.setIpk(ipk);
                
                total_dist += dist;
                total_prd  += prd;
                
                lst.add(ip);
            }
            
            // Agregamos registro de produccion total para rutas no detectadas
            if (ip_na != null) {                
                lst.add(ip_na);
            }
            
            // Agregamos registro de produccion total para rutas detectadas
            double ipk_t = (total_dist == 0) ? 0 : (double) total_prd / total_dist;
            IndicadorProduccion ip_t = new IndicadorProduccion();
            ip_t.setNombreRuta("Ruta detectada");
            ip_t.setDistanciaRecorrida(total_dist);
            ip_t.setProduccion(total_prd);
            ip_t.setIpk(ipk_t);
            ip_t.setTipoRegistro(IndicadorProduccion.PRODUCCION_TOTAL_RUTA);
            
            lst.add(ip_t);
            
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }    
    
    // Convierte listado de registros de indice de cumplimiento ruta a
    // listado de cadenas de texto formateadas, separada cada una por caracter %
    // Formato de cadena : nombre_ruta % v1 % v2 % v3 % vn
    // (Registros se encuentran ordenados por vuelta)
    public static ArrayList<String> 
        format_icr(ArrayList<IndicadorCumplimientoRuta> lst) {
        
        
        if (lst != null && lst.size() > 0) {
            String nombreRuta = lst.get(0).getNombreRuta();
            int idRuta        = lst.get(0).getIdRuta();
            int cumpRuta      = lst.get(0).getCumplimientoRutaEnt();
            
            String vruta = nombreRuta + "%" + cumpRuta;
            ArrayList<String> slst = new ArrayList<String>();
            
            for (int i = 1; i < lst.size(); i++) {
                String item_nombreRuta = lst.get(i).getNombreRuta();
                int item_idRuta        = lst.get(i).getIdRuta();
                int item_cumpRuta      = lst.get(i).getCumplimientoRutaEnt();
                
                if (idRuta == item_idRuta) {
                    vruta += "%" + item_cumpRuta;
                } else {                    
                    slst.add(vruta);
                    idRuta = item_idRuta;
                    vruta  = item_nombreRuta + "%" + item_cumpRuta;
                }
            }            
            slst.add(vruta);
            return slst;
        }
        return null;
    }
        
    // Consulta indice de cumplimiento ruta en una fecha especifica,
    // para registros que no presentan ruta.
    // Registros se agrupan numero de vuelta.
    public static ArrayList<IndicadorCumplimientoRuta> 
            indicadorCumplimientoRutaSinRuta(String fecha) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = IndicadorSQL.sql_indicadorCumplimientoRutaSinRuta(fecha);
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            ArrayList<IndicadorCumplimientoRuta> lst 
                    = new ArrayList<IndicadorCumplimientoRuta>();
            while (rs.next()) {
                IndicadorCumplimientoRuta icr = new IndicadorCumplimientoRuta();
                icr.setIdRuta(rs.getInt("PK_RUTA"));
                icr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                icr.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                icr.setCumplimientoRuta(rs.getDouble("PORCENTAJE_RUTA"));
                int cumpRuta = (int) Math.round(icr.getCumplimientoRuta() * 100);
                icr.setCumplimientoRutaEnt(cumpRuta);
                lst.add(icr);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }        
        return null;
    }
    
    // Consulta indice de cumplimiento ruta en una fecha y listado de rutas
    // en especifico. Si listado de rutas es una cadena vacia, se consulta
    // la totalidad de las rutas.
    // Registros se agrupan por ruta y numero de vuelta.
    public static ArrayList<String> indicadorCumplimientoRuta(
                                    String fecha, 
                                    String lst_ruta) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = IndicadorSQL.sql_indicadorCumplimientoRuta(fecha, lst_ruta);
        ArrayList<IndicadorCumplimientoRuta> icr_na = indicadorCumplimientoRutaSinRuta(fecha);
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            ArrayList<IndicadorCumplimientoRuta> lst =
                    new ArrayList<IndicadorCumplimientoRuta>();
            
            while (rs.next()) {
                IndicadorCumplimientoRuta icr = new IndicadorCumplimientoRuta();
                icr.setIdRuta(rs.getInt("PK_RUTA"));
                icr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                icr.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                icr.setCumplimientoRuta(rs.getDouble("PORCENTAJE_RUTA"));
                int cumpRuta = (int) Math.round(icr.getCumplimientoRuta() * 100);
                icr.setCumplimientoRutaEnt(cumpRuta);
                
                lst.add(icr);
            }
            
            // Se agregan registros cumplimiento-ruta para ruta no detectada,
            // si se ha seleccionado
            if (lst_ruta != null) {
                if (lst_ruta == "" || lst_ruta.indexOf("-1") >= 0) {
                    for (IndicadorCumplimientoRuta icr : icr_na) {
                        lst.add(icr);
                    }
                }
            }
            return format_icr(lst);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Agrupa listado de registros de indice de capacidad transportadora
    // por ruta, cada (ruta) representada por clase envolvente,
    // agrupa sus registros.
    public static ArrayList<IndicadorCapacidadTransportadora> 
        format_ict(ArrayList<CapacidadTransportadora> lst) {
                
        if (lst != null && lst.size() > 0) {
            int idRuta        = lst.get(0).getIdRuta();
            String nombreRuta = lst.get(0).getNombreRuta();
                
            ArrayList<IndicadorCapacidadTransportadora> lst_indicador
                    = new ArrayList<IndicadorCapacidadTransportadora>();
            ArrayList<CapacidadTransportadora> lst_detalle
                    = new ArrayList<CapacidadTransportadora>();
            
            for (int i = 0; i < lst.size(); i++) {
                CapacidadTransportadora detalle = lst.get(i);
                int item_idRuta        = detalle.getIdRuta();
                String item_nombreRuta = detalle.getNombreRuta();
                
                if (idRuta == item_idRuta) {
                    lst_detalle.add(detalle);
                } else {
                    IndicadorCapacidadTransportadora ict
                            = new IndicadorCapacidadTransportadora();
                    
                    ict.setIdRuta(idRuta);
                    ict.setNombreRuta(nombreRuta);
                    ict.setDetalle(lst_detalle);
                    lst_indicador.add(ict);
                    
                    lst_detalle = new ArrayList<CapacidadTransportadora>();
                    lst_detalle.add(detalle);
                    idRuta     = item_idRuta;
                    nombreRuta = item_nombreRuta;
                }
                
                if (i == lst.size()-1) {
                    IndicadorCapacidadTransportadora ict
                            = new IndicadorCapacidadTransportadora();
                    
                    ict.setIdRuta(idRuta);
                    ict.setNombreRuta(nombreRuta);
                    ict.setDetalle(lst_detalle);
                    lst_indicador.add(ict);
                }
            }
            return lst_indicador;
        }
        return null;
    }
        
    // Consulta indice de capacidad transportadora en una fecha especifica,
    // para registros que no presentan ruta. Registros se agrupan por hora.
    public static ArrayList<CapacidadTransportadora> indicadorCapacidadTransportadoraSinRuta(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                    + " -1 as PK_RUTA,"
                    + " 'Ruta no detectada' as NOMBRE_RUTA,"
                    + " hour(pc.HORA_PTO_CONTROL) as HORA,"
                    + " sum(if(pc.salidas > pc.entradas, 0, pc.entradas - pc.salidas)) as NIVEL_OCUPACION,"	

                    + " (select sum(v.CAPACIDAD) from tbl_vehiculo as v"
                    + " where v.ESTADO = 1 and v.PK_VEHICULO in (select iir.FK_VEHICULO from tbl_informacion_registradora as iir"
                                            + " where iir.FK_RUTA is null and iir.FECHA_INGRESO = ir.FECHA_INGRESO"
                                            + " and iir.PORCENTAJE_RUTA > 0"
                                            + " group by iir.FK_VEHICULO)) as CAPACIDAD"

                    + " FROM tbl_informacion_registradora as ir"
                    + " INNER JOIN tbl_punto_control as pc on"
                    + "     ir.PK_INFORMACION_REGISTRADORA = pc.FK_INFO_REGIS"
                    + " WHERE ir.FECHA_INGRESO = ? and ir.FK_RUTA is null"
                    + " and ir.PORCENTAJE_RUTA > 0"
                    + " GROUP BY hour(pc.HORA_PTO_CONTROL)"
                    + " ORDER BY NOMBRE_RUTA asc, HORA asc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            ArrayList<CapacidadTransportadora> lst
                    = new ArrayList<CapacidadTransportadora>();
            
            while (rs.next()) {
                CapacidadTransportadora ct = 
                        new CapacidadTransportadora();
                
                ct.setIdRuta(rs.getInt("PK_RUTA"));
                ct.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                ct.setHora(rs.getString("HORA"));
                ct.setNivelOcupacion(rs.getInt("NIVEL_OCUPACION"));
                //ct.setNumeroPasajeros(rs.getLong("PASAJEROS"));
                ct.setCapacidad(rs.getInt("CAPACIDAD"));
                
                int nivel_ocupacion = ct.getNivelOcupacion();
                int capacidad = ct.getCapacidad();
                double icu = (capacidad == 0) ? 0 : (double) nivel_ocupacion / capacidad;                
                ct.setIcuHora(icu);
                
                lst.add(ct);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta indice de capacidad transportadora en una fecha especifica.
    // Registros se agrupan por ruta y hora.
    public static ArrayList<IndicadorCapacidadTransportadora> indicadorCapacidadTransportadora(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //String sql = "SELECT"
        //            + " r.PK_RUTA,"
        //            + " r.NOMBRE as NOMBRE_RUTA,"
        //            + " hour(ir.HORA_INGRESO) as HORA,"
        //            + " sum(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) as PASAJEROS,"
        //            + " sum(ir.ENTRADAS - ir.SALIDAS_BASE_SALIDA) as NIVEL_OCUPACION,"
        //            + " sum(v.CAPACIDAD) as CAPACIDAD"
        //            + " FROM tbl_informacion_registradora as ir"
        //            + " INNER JOIN tbl_ruta as r on"
        //            + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
        //            + " INNER JOIN tbl_vehiculo as v on"
        //            + "     ir.FK_VEHICULO = v.PK_VEHICULO and v.ESTADO = 1"
        //            + " WHERE ir.FECHA_INGRESO = ?"
        //            + " GROUP BY ir.FK_RUTA, hour(ir.HORA_INGRESO)"
        //            + " ORDER BY NOMBRE_RUTA asc, HORA asc";                
        
        ArrayList<CapacidadTransportadora> ict_na = indicadorCapacidadTransportadoraSinRuta(fecha);
        
        String sql = "SELECT"
                    + " r.PK_RUTA,"
                    + " r.NOMBRE as NOMBRE_RUTA,"
                    + " hour(pc.HORA_PTO_CONTROL) as HORA,"
                    + " sum(if(pc.salidas > pc.entradas, 0, pc.entradas - pc.salidas)) as NIVEL_OCUPACION,"

                    + " (select sum(v.CAPACIDAD) from tbl_vehiculo as v"
                    + "  where v.ESTADO = 1 and v.PK_VEHICULO in (select iir.FK_VEHICULO from tbl_informacion_registradora as iir"
                    + "                          where iir.FK_RUTA = ir.FK_RUTA and iir.FECHA_INGRESO = ir.FECHA_INGRESO"
                    + "                          group by iir.FK_VEHICULO)) as CAPACIDAD"

                    + " FROM tbl_informacion_registradora as ir"
                    + " INNER JOIN tbl_punto_control as pc on"
                    + "     ir.PK_INFORMACION_REGISTRADORA = pc.FK_INFO_REGIS"
                    + " INNER JOIN tbl_ruta as r on"
                    + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                    + " WHERE  ir.FECHA_INGRESO = ? and ir.FK_RUTA is not null"
                    + " GROUP BY ir.FK_RUTA, hour(pc.HORA_PTO_CONTROL)"
                    + " ORDER BY NOMBRE_RUTA asc, HORA asc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            ArrayList<CapacidadTransportadora> lst
                    = new ArrayList<CapacidadTransportadora>();
            
            while (rs.next()) {
                CapacidadTransportadora ct 
                    = new CapacidadTransportadora();
                
                ct.setIdRuta(rs.getInt("PK_RUTA"));
                ct.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                ct.setHora(rs.getString("HORA"));
                ct.setNivelOcupacion(rs.getInt("NIVEL_OCUPACION"));
                //ct.setNumeroPasajeros(rs.getLong("PASAJEROS"));
                ct.setCapacidad(rs.getInt("CAPACIDAD"));
                
                int nivel_ocupacion = ct.getNivelOcupacion();
                int capacidad = ct.getCapacidad();
                double icu = (capacidad == 0) ? 0 : (double) nivel_ocupacion / capacidad;                
                ct.setIcuHora(icu);
                
                lst.add(ct);
            }
            
            // Se agregan registros capacidad-transportadora para ruta no detectada
            if (ict_na != null) {
                for (CapacidadTransportadora ct : ict_na) {                    
                    lst.add(ct);
                }
            }
            return format_ict(lst);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;        
    }
    
    // Agrupa listado de registros de indice pasajero por hora
    // por ruta, cada (ruta) representada por clase envolvente,
    // agrupa sus registros.
    public static ArrayList<IndicadorPasajeroHora> 
        format_iph(ArrayList<PasajeroHora> lst) {
        
        if (lst != null && lst.size() > 0) {
            int idRuta        = lst.get(0).getIdRuta();
            String nombreRuta = lst.get(0).getNombreRuta();
            
            ArrayList<IndicadorPasajeroHora> lst_indicador
                    = new ArrayList<IndicadorPasajeroHora>();
            ArrayList<PasajeroHora> lst_detalle
                    = new ArrayList<PasajeroHora>();
            
            for (int i = 0; i < lst.size(); i++) {
                PasajeroHora detalle   = lst.get(i);
                int item_idRuta        = detalle.getIdRuta();
                String item_nombreRuta = detalle.getNombreRuta();
                
                if (item_idRuta == idRuta) {
                    lst_detalle.add(detalle);
                } else {
                    IndicadorPasajeroHora iph = new IndicadorPasajeroHora();
                    iph.setIdRuta(idRuta);
                    iph.setNombreRuta(nombreRuta);
                    iph.setDetalle(lst_detalle);
                    lst_indicador.add(iph);
                    
                    lst_detalle = new ArrayList<PasajeroHora>();
                    lst_detalle.add(detalle);
                    idRuta = item_idRuta;
                    nombreRuta = item_nombreRuta;
                }
                
                if (i == lst.size()-1) {
                    IndicadorPasajeroHora iph = new IndicadorPasajeroHora();
                    iph.setIdRuta(idRuta);
                    iph.setNombreRuta(nombreRuta);
                    iph.setDetalle(lst_detalle);
                    lst_indicador.add(iph);
                }
            }
            return lst_indicador;
        }
        return null;
    }
    
    // Consulta indice de pasajeros por hora en una fecha y rutas especifico.
    // Si ruta es una cadena vacia, se consulta la totalidad de rutas.
    // Registros se agrupan por ruta y hora.
    // *** No utilizado, ver indicadorPasajeroHora_entradas. ***
    public static ArrayList<PasajeroHora> indicadorPasajeroHora(String fecha, String lst_ruta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String filtro_ruta = "";
        if (lst_ruta != null && lst_ruta != "") {
            filtro_ruta = " AND ir.FK_RUTA IN (" + lst_ruta + ")";
        }
        
        String sql = "SELECT"
                    + " r.PK_RUTA,"
                    + " r.NOMBRE as NOMBRE_RUTA,"
                    + " hour(ir.HORA_INGRESO) as HORA,"
                    + " sum(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) as PASAJEROS,"
                
                    + " (select sum(iir.NUM_LLEGADA - iir.NUMERACION_BASE_SALIDA) from"
                    + "  tbl_informacion_registradora iir where"
                    + "  iir.FK_RUTA = ir.FK_RUTA and"
                    + "  iir.FECHA_INGRESO = ir.FECHA_INGRESO) as TOTAL_PASAJEROS"
                
                    + " FROM tbl_informacion_registradora as ir"
                    + " INNER JOIN tbl_ruta as r on"
                    + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                    + " WHERE ir.FECHA_INGRESO = ?"
                    + filtro_ruta
                    + " GROUP BY ir.FK_RUTA, hour(ir.HORA_INGRESO)"
                    + " ORDER BY NOMBRE_RUTA asc, HORA asc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            ArrayList<PasajeroHora> lst = new ArrayList<PasajeroHora>();
            while (rs.next()) {
                PasajeroHora ph = new PasajeroHora();
                ph.setIdRuta(rs.getInt("PK_RUTA"));
                ph.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                ph.setHora(rs.getString("HORA"));
                ph.setNumeroPasajero(rs.getLong("PASAJEROS"));
                ph.setTotalPasajero(rs.getLong("TOTAL_PASAJEROS"));
                
                long pasajeros_hora  = ph.getNumeroPasajero();
                long total_pasajeros = ph.getTotalPasajero();
                double iph = (total_pasajeros == 0) ? 0 : (double) pasajeros_hora / total_pasajeros;
                ph.setIph(iph);
                
                lst.add(ph);
            }
            //return format_iph(lst);
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Filtra registros de pasajeros-hora segun rutas 
    // especificadas en listado lst_ruta.
    public static ArrayList<PasajeroHora> filtrarIPH(ArrayList<PasajeroHora> lst, String lst_ruta) {
        
        if (lst_ruta == null || lst_ruta == "" || lst == null || lst.size() == 0) {
            return lst;
        } else {
            ArrayList<PasajeroHora> nlst = new ArrayList<PasajeroHora>();
            String arr_ruta[] = lst_ruta.split(",");
            
            for (int i = 0; i < arr_ruta.length; i++) {
                int id_ruta = Restriction.getNumber(arr_ruta[i]);
                for (int j = 0; j < lst.size(); j++) {
                    PasajeroHora ph = lst.get(j);
                    if (ph.getIdRuta() == id_ruta) {
                        nlst.add(ph);
                    }
                }
            }
            return nlst;
        }        
    }
    
    // Consulta indice de pasajeros por hora en fecha especificada
    // para registros que no presentan ruta. El indice se calcula
    // segun las entradas detectadas en cada intervalo de hora.
    public static ArrayList<PasajeroHora> indicadorPasajeroHora_entradasSinRuta(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = IndicadorSQL.sql_indicadorPasajeroHora_entradasSinRuta(fecha);
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, fecha);
            ps.setString(3, fecha);
            ps.setString(4, fecha);
            ps.setString(5, fecha);
            ps.setString(6, fecha);
            ps.setString(7, fecha);
            rs = ps.executeQuery();
            
            ArrayList<PasajeroHora> lst = new ArrayList<PasajeroHora>();
            while (rs.next()) {
                PasajeroHora ph = new PasajeroHora();
                ph.setIdRuta(rs.getInt("PK_RUTA"));
                ph.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                ph.setHora(rs.getString("HORA"));
                ph.setEntradas(rs.getLong("ENTRADAS"));
                lst.add(ph);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta indice de pasajeros por hora en fecha y ruta(s) especificadas.
    // El indice se calcula segun las entradas detectadas en cada intervalo de hora.
    public static ArrayList<PasajeroHora> indicadorPasajeroHora_entradas(String fecha, String lst_ruta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = IndicadorSQL.sql_indicadorPasajeroHora_entradas(fecha);
        ArrayList<PasajeroHora> iph_na = indicadorPasajeroHora_entradasSinRuta(fecha);
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, fecha);
            ps.setString(3, fecha);
            ps.setString(4, fecha);
            ps.setString(5, fecha);
            ps.setString(6, fecha);
            ps.setString(7, fecha);
            rs = ps.executeQuery();
            
            ArrayList<PasajeroHora> lst = new ArrayList<PasajeroHora>();
            while (rs.next()) {
                PasajeroHora ph = new PasajeroHora();
                ph.setIdRuta(rs.getInt("PK_RUTA"));
                ph.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                ph.setHora(rs.getString("HORA"));
                ph.setEntradas(rs.getLong("ENTRADAS"));
                lst.add(ph);
            }
            
            // Se agregan registros pasajero-hora para ruta no detectada
            if (iph_na != null && iph_na.size() > 0) {
                for (PasajeroHora ph : iph_na) {
                    lst.add(ph);
                }
            }
            // Se filtran registros por rutas seleccionadas
            return filtrarIPH(lst, lst_ruta);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    
    public static ArrayList<IndicadorDescuentoDePasajeros> IndicadorDescuentoDePasajeros (IndicadorDescuentoDePasajeros h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();        
        sql.append(" SELECT la.FK_CATEGORIAS as id, a.nombre, sum(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total_categoria ");
        sql.append(" FROM tbl_liquidacion_adicional as la INNER JOIN (SELECT cd.PK_CATEGORIAS_DESCUENTOS as id, cd.NOMBRE FROM tbl_categoria_descuento as cd WHERE (cd.DESCUENTA_PASAJEROS=1) and (cd.ESTADO=1)) AS a ON a.id = la.FK_CATEGORIAS ");
        sql.append(" WHERE (la.FECHA_DESCUENTO BETWEEN ? and ?) ");
        sql.append(" GROUP BY (la.FK_CATEGORIAS) ");
        sql.append(" UNION ");
        sql.append(" SELECT CONCAT('0', '0'), CONCAT('Total', ' pasajeros'), sum(lg.TOTAL_PASAJEROS_LIQUIDADOS) as total_pasajeros ");
        sql.append(" FROM tbl_liquidacion_general as lg  ");
        sql.append(" WHERE lg.FECHA_LIQUIDACION BETWEEN ? and ? ");      
        //System.out.println(sql.toString());       
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, h.getFecha_inicio()+" 00:00:00");
            ps.setString(2, h.getFecha_fin()+" 23:59:59");
            ps.setString(3, h.getFecha_inicio()+" 00:00:00");
            ps.setString(4, h.getFecha_fin()+" 23:59:59");
            rs = ps.executeQuery();
            IndicadorDescuentoDePasajeros a=null;
            ArrayList<IndicadorDescuentoDePasajeros> lst_alm = new ArrayList<>();
            while (rs.next()) {
                a = new IndicadorDescuentoDePasajeros();
                a.setId(rs.getInt("id"));                
                a.setNombre_categoria(rs.getString("nombre"));
                a.setCantidad(rs.getInt("total_categoria"));                
                
                lst_alm.add(a);
            } return lst_alm;
        }    
        catch (SQLException ex) {
            System.err.println(ex);
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException ie) {
                        System.err.println(ie);
                    }
                }
        } finally {
           try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(e);
                }
                UtilBD.closeStatement(ps);
                pila_con.liberarConexion(con);
        } return null;
    }    
}
