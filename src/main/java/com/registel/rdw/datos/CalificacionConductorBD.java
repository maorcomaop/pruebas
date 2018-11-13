/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.ConductorVehiculo;
import com.registel.rdw.logica.ConfCalificacionConductor;
import com.registel.rdw.logica.DatosVehiculo;
import com.registel.rdw.logica.ExcesoVelocidad;
import com.registel.rdw.logica.IncumplimientoRuta;
import com.registel.rdw.logica.RendimientoConductor;
import com.registel.rdw.utils.Profile;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.StringUtils;
import com.registel.rdw.utils.TimeUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author lider_desarrollador
 */
public class CalificacionConductorBD {
    
    private static SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    
    // Consulta configuracion de calificacion para conductor activo    
    public static ConfCalificacionConductor confCalificacionConductor() {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_conductor_desempeno WHERE estado = 1";
        
        try {
            st = con.createStatement();            
            rs = st.executeQuery(sql);            
                        
            if (rs.next()) {
                ConfCalificacionConductor ccc = new ConfCalificacionConductor();
                ccc.setNombre(rs.getString("nombre"));
                ccc.setPuntajeMaximo(rs.getInt("puntaje_max"));
                ccc.setPuntosPorVel(rs.getInt("puntos_ex_vel"));
                ccc.setVelocidadMaxima(rs.getInt("velocidad_max"));
                ccc.setPuntosPorRuta(rs.getInt("puntos_ruta"));
                ccc.setPorcentajeRuta(rs.getInt("porcentaje_ruta"));
                ccc.setPuntosPorDiaNoLaborado(rs.getInt("puntos_dia_no"));
                ccc.setPuntosPorDiaDescanco(rs.getInt("dias_descanso"));
                ccc.setPuntosIpkMayor(rs.getInt("puntos_ipk_mas"));
                ccc.setPuntosIpkMenor(rs.getInt("punto_ipk_menos"));
                return ccc;
            }            
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta relacion conductor-vehiculo (que vehiculo conduce cada conductor)
    // en un rango de dias especifico
    public static ArrayList<ConductorVehiculo> listadoConductorVehiculo(
                        String fecha_inicio,
                        String fecha_final) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " FK_CONDUCTOR,"
                        + " FK_VEHICULO,"
                        + " v.placa as PLACA,"
                        + " fecha_ingreso as FECHA"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_vehiculo as v on"
                        + "     v.pk_vehiculo = ir.fk_vehiculo and v.estado = 1"
                        + " WHERE fecha_ingreso between ? and ?"
                        + " GROUP BY fecha_ingreso, fk_conductor, fk_vehiculo";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicio);
            ps.setString(2, fecha_final);
            rs = ps.executeQuery();
            
            ArrayList<ConductorVehiculo> lst = new ArrayList<ConductorVehiculo>();
            while (rs.next()) {
                ConductorVehiculo cv = new ConductorVehiculo();
                cv.setIdConductor(rs.getInt("FK_CONDUCTOR"));
                cv.setFecha(rs.getString("FECHA"));
                cv.setPlaca(rs.getString("PLACA"));
                lst.add(cv);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println("listadoConductorVehiculo: " + e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta cantidad de veces que se incumplio una ruta
    // por cada conductor en un rango de dias especifico
    public static ArrayList<IncumplimientoRuta> incumplimientoRuta(
                        String fecha_inicio,
                        String fecha_final,
                        double porcentaje_ruta) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " FK_CONDUCTOR,"                        
                        + " count(pk_informacion_registradora) as CANTIDAD_INCUMPLIMIENTO_RUTA"
                        + " FROM tbl_informacion_registradora as ir"
                        + " WHERE porcentaje_ruta < ? and"
                        + " fecha_ingreso between ? and ?"
                        + " GROUP BY fk_conductor";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, porcentaje_ruta);
            ps.setString(2, fecha_inicio);
            ps.setString(3, fecha_final);
            rs = ps.executeQuery();
            
            ArrayList<IncumplimientoRuta> lst = new ArrayList<IncumplimientoRuta>();
            while (rs.next()) {
                IncumplimientoRuta incruta = new IncumplimientoRuta();
                incruta.setIdConductor(rs.getInt("FK_CONDUCTOR"));
                incruta.setCantidad(rs.getInt("CANTIDAD_INCUMPLIMIENTO_RUTA"));
                lst.add(incruta);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println("incumplimientoRuta: " + e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;

    }
    
    // Consulta cantidad de veces que se excede velocidad limite por cada
    // vehiculo (placa) en un rango de dias especifico
    public static ArrayList<ExcesoVelocidad> excesoVelocidad(
                        String fecha_inicio,
                        String fecha_final,
                        int limite_velocidad) {        
        
        ConexionExterna conext = new ConexionExterna();
        Connection con = null;
        
        String sql = "SELECT"
                        + " date(fecha_gps) as FECHA,"
                        + " placa as PLACA,"
                        + " count(id) as CANTIDAD_EXCESO_VELOCIDAD"
                        + " FROM tbl_forwarding_wtch"
                        + " WHERE velocidad > ? and"
                        + " fecha_gps between ? and ?"
                        + " GROUP BY date(fecha_gps), placa";
        
        fecha_inicio += " 00:00:00";
        fecha_final  += " 23:59:59";
        
        try {
            con = conext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, limite_velocidad);
            ps.setString(2, fecha_inicio);
            ps.setString(3, fecha_final);
            ResultSet rs = ps.executeQuery();
            
            ArrayList<ExcesoVelocidad> lst = new ArrayList<ExcesoVelocidad>();
            while (rs.next()) {
                ExcesoVelocidad ev = new ExcesoVelocidad();
                ev.setFecha(rs.getString("FECHA"));
                ev.setPlaca(rs.getString("PLACA"));
                ev.setCantidad(rs.getInt("CANTIDAD_EXCESO_VELOCIDAD"));
                lst.add(ev);
            }
            rs.close();
            ps.close();
            return lst;
            
        } catch (SQLException e) {
            System.err.println("excesoVelocidad: " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("excesoVelocidad: " + e);
        } finally {
            conext.desconectar();
        }
        return null;
    }
    
    // Consulta listado de conductores en un rango de dias especifico
    // para posteriormente agregarle su calificacion
    public static ArrayList<RendimientoConductor> listadoConductor(
                        String fecha_inicio,
                        String fecha_final) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " FK_CONDUCTOR,"
                        + " concat(c.NOMBRE, ' ', c.APELLIDO) as NOMBRE_CONDUCTOR"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_conductor as c on"
                        + "     c.PK_CONDUCTOR = ir.FK_CONDUCTOR and c.ESTADO = 1"
                        + " WHERE fecha_ingreso between ? and ?"
                        + " GROUP BY ir.FK_CONDUCTOR";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicio);
            ps.setString(2, fecha_final);
            rs = ps.executeQuery();
            
            ArrayList<RendimientoConductor> lst = new ArrayList<RendimientoConductor>();
            while (rs.next()) {
                RendimientoConductor rc = new RendimientoConductor();
                rc.setId(rs.getInt("FK_CONDUCTOR"));                
                String nombre = rs.getString("NOMBRE_CONDUCTOR");
                nombre = StringUtils.upperFirstLetter(nombre);
                rc.setNombre(nombre);                
                lst.add(rc);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println("listadoConductor: " + e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
        
    }
    
    // Califica conductor segun la configuracion establecida y el rendimiento
    // obtenido de este
    public static void calificaConductor(
                    ConfCalificacionConductor ccc,
                    RendimientoConductor rc) {
        
        int puntajeMaximo = ccc.getPuntajeMaximo();
        
        puntajeMaximo -= (ccc.getPuntosPorVel()  * rc.getExcesoVelocidad());
        puntajeMaximo -= (ccc.getPuntosPorRuta() * rc.getIncumplimientoRuta());
        puntajeMaximo -= (ccc.getPuntosPorDiaNoLaborado() * rc.getDiasNoLaborados());                
        
        if (rc.getIpk() >= rc.getIpkEmpresa()) {
            puntajeMaximo += ccc.getPuntosIpkMayor();
        } else {
            puntajeMaximo -= ccc.getPuntosIpkMenor();
        }
        
        rc.setRendimiento(puntajeMaximo);
    }
    
    // Consulta, agrupa y califica un listado de conductores en un rango
    // de dias especifico
    public static ArrayList<RendimientoConductor> 
                            calificar(String fecha_inicio, 
                                      String fecha_final,
                                      String sconductor,
                                      ConfCalificacionConductor ccc) {
                                
        int max_vel     = ccc.getVelocidadMaxima();
        double ptj_ruta = ccc.getPorcentajeRuta() / (double) 100.0;
            
        ArrayList<ConductorVehiculo> lst_cv    = listadoConductorVehiculo(fecha_inicio, fecha_final);
        ArrayList<ExcesoVelocidad> lst_ev      = excesoVelocidad(fecha_inicio, fecha_final, max_vel);
        ArrayList<IncumplimientoRuta> lst_ir   = incumplimientoRuta(fecha_inicio, fecha_final, ptj_ruta);
        ArrayList<RendimientoConductor> lst_rc = listadoConductor(fecha_inicio, fecha_final);        
        DecimalFormat df = new DecimalFormat("0.00");
        
        // Consulta IPK de empresa (todos los vehiculos)                
        double ipk_empresa = IPKBD2.ipk_empresa(fecha_inicio, fecha_final);
        
        // Consulta IPK de cada vehiculo por dia
        IPKBD2.ipk_pordia(fecha_inicio, fecha_final);
        Map<String, DatosVehiculo> hmap = IPKBD2.getHmapIpk();
        
        if (lst_cv != null &&
            lst_ev != null &&
            lst_ir != null &&
            lst_rc != null &&
            ccc != null) {            
            
            // Asocia cada exceso de velocidad de cada vehiculo por dia
            // con relacion vehiculo-conductor de ese dia
            for (ExcesoVelocidad ev : lst_ev) {                
                String placa_exc = ev.getPlaca();
                String fecha_exc = ev.getFecha();
                for (ConductorVehiculo cv : lst_cv) {
                    if (cv.getFecha().compareTo(fecha_exc) == 0 &&
                        cv.getPlaca().compareTo(placa_exc) == 0) {
                        cv.setExcesoVelocidad(ev.getCantidad());
                        break;
                    }
                }
            }
            
            // Asocia IPK de cada vehiculo por dia con relacion
            // vehiculo-conductor por dia
            for (ConductorVehiculo cv : lst_cv) {                
                String placa  = cv.getPlaca();
                String fecha  = cv.getFecha();
                String key    = placa + "-" + fecha;
                if (hmap != null) {
                    DatosVehiculo dv = hmap.get(key);
                    if (dv != null) {
                        cv.setIpk(dv.getIpk());
                    }                    
                }
            }

            for (RendimientoConductor rc : lst_rc) {
                int idConductor = rc.getId();
                
                // Se asocia cantidad de exceso velocidad por dia
                // con relacion vehiculo-conductor por dia 
                // Suma y promedia IPK de vehiculo-conductor por dia
                // Agrupa fechas laboradas de cada conductor
                double ipk = 0.0; int n = 0;
                String fechasLabor = "";
                for (ConductorVehiculo cv : lst_cv) {
                    if (idConductor == cv.getIdConductor()) {
                        int excvel = rc.getExcesoVelocidad();
                        excvel += cv.getExcesoVelocidad();
                        rc.setExcesoVelocidad(excvel);
                        ipk += cv.getIpk(); n++;
                        // Agrupa fechas laboradas
                        String fecha = cv.getFecha();
                        fechasLabor  = (fechasLabor == "") ? fecha : "," + fecha;
                    }
                }                
                if (n > 0) ipk = ipk / (double) n;
                rc.setIpk(ipk);
                rc.setIpk_str(df.format(ipk));
                
                // Establece dias no laborados                
                if (fecha_inicio.compareTo(fecha_final) == 0) {
                    if (!fechasLabor.contains(fecha_inicio)) {
                        rc.setDiasNoLaborados(1);
                    }
                } else {
                    Date fec_inicio = TimeUtil.getDate(fecha_inicio);
                    Date fec_final  = TimeUtil.getDate(fecha_final);
                    String sfec; int diasNoLaborados = 0;
                    
                    do { 
                        sfec = ffmt.format(fec_inicio);
                        if (!fechasLabor.contains(sfec)) {
                            diasNoLaborados += 1;
                        }
                        fec_inicio = TimeUtil.sumarDia(fec_inicio);
                    } while (!TimeUtil.fechasIguales(fec_inicio, fec_final));
                    
                    rc.setDiasNoLaborados(diasNoLaborados);
                }
                
                // Asocia calificacion de incumplimiento ruta
                // con relacion vehiculo-conductor por dia
                for (IncumplimientoRuta ir : lst_ir) {
                    if (idConductor == ir.getIdConductor()) {
                        int incruta = rc.getIncumplimientoRuta();
                        incruta += ir.getCantidad();
                        rc.setIncumplimientoRuta(incruta);
                    }
                }
                
                // Asocia IPK empresa
                rc.setIpkEmpresa(ipk_empresa);
                rc.setIpkEmpresa_str(df.format(ipk_empresa));                
            }
            
            // Se filtra y califica listado por conductores seleccionados
            String lst_conductor[] = sconductor.split(",");
            ArrayList<RendimientoConductor> lst_frc = new ArrayList<RendimientoConductor>();
            
            for (int i = 0; i < lst_conductor.length; i++) {
                int idConductor = Restriction.getNumber(lst_conductor[i]);
                for (RendimientoConductor rc : lst_rc) {
                    if (idConductor == rc.getId()) {                        
                        calificaConductor(ccc, rc);
                        lst_frc.add(rc);
                        break;
                    }
                }
            }
            
            // Ordenamos descendentemente por calificacion
            Collections.sort(lst_frc, new Comparator<RendimientoConductor>() {                

                @Override
                public int compare(RendimientoConductor rc1, RendimientoConductor rc2) {
                    int r1 = rc1.getRendimiento();
                    int r2 = rc2.getRendimiento();
                    return (r1 < r2) ? 1
                         : (r1 > r2) ? -1
                         : 0;
                }
            });            
            
            return lst_frc;
        }
        return null;
    }
}
