/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Base;
//import com.registel.rdw.logica.Etiquetas;
import com.registel.rdw.logica.InformacionRegistradora;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.Propietario;
import com.registel.rdw.logica.PropietarioVehiculo;
import com.registel.rdw.utils.ParametrosReporte;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import com.registel.rdw.reportes.AlarmaXVehiculo;
import com.registel.rdw.reportes.ComparativoProduccionRuta;
import com.registel.rdw.reportes.ConductorEstablecido;
import com.registel.rdw.reportes.ConductorXVehiculo;
import com.registel.rdw.reportes.ConsolidadoLiquidacion;
import com.registel.rdw.reportes.ConsolidadoXEmpresa;
import com.registel.rdw.reportes.NivelOcupacion;
import com.registel.rdw.reportes.ProduccionXRuta;
import com.registel.rdw.reportes.ProduccionXVehiculo;
import com.registel.rdw.reportes.PuntosControl;
import com.registel.rdw.reportes.VehiculoXAlarma;
import com.registel.rdw.reportes.ConteoPerimetro_r;
import com.registel.rdw.reportes.CumplimientoRuta;
import com.registel.rdw.reportes.CumplimientoRutaTotales;
import com.registel.rdw.reportes.DescripcionRuta;
import com.registel.rdw.reportes.DespachadorIndiceIR;
import com.registel.rdw.reportes.RutaEstablecida;
import com.registel.rdw.reportes.TarifaEstablecida;
import com.registel.rdw.reportes.VehiculoEstablecido;
import com.registel.rdw.reportes.VehiculoXRuta;
import com.registel.rdw.reportes.Despachador;
import com.registel.rdw.reportes.Gerencia;
import com.registel.rdw.reportes.GerenciaXVehiculo;
import com.registel.rdw.reportes.LiquidacionConsulta;
import com.registel.rdw.reportes.LiquidacionXLiquidador;
import com.registel.rdw.reportes.LiquidacionIPK;
import com.registel.rdw.reportes.ProduccionXHora;
import com.registel.rdw.reportes.CategoriaQueDescuentaPax;
import com.registel.rdw.reportes.Propietario_r;
import com.registel.rdw.reportes.PuntoNoRegistrado;
import com.registel.rdw.reportes.RutaXVehiculo;
import com.registel.rdw.reportes.RutaXVehiculoDph;
import com.registel.rdw.reportes.RutaXVehiculoUtil;
import com.registel.rdw.utils.ReporteUtil;
import com.registel.rdw.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author lider_desarrollador
 */
public class ReportesBD {

    private ArrayList<AlarmaXVehiculo> lst_axv;
    private ArrayList<ProduccionXVehiculo> lst_pxv;
    private ArrayList<ProduccionXRuta> lst_pxr;
    private ArrayList<ProduccionXHora> lst_pxh;
    private ArrayList<PuntosControl> lst_pc;
    private ArrayList<PuntosControl> lst_pc_gps;
    private ArrayList<VehiculoXAlarma> lst_vxa;
    private ArrayList<ConsolidadoXEmpresa> lst_cxe;
    private ArrayList<ComparativoProduccionRuta> lst_cpr;
    private ArrayList<NivelOcupacion> lst_no;
    private ArrayList<ConteoPerimetro_r> lst_cp;

    private ArrayList<RutaEstablecida> lst_re;
    private ArrayList<VehiculoEstablecido> lst_ve;
    private ArrayList<ConductorEstablecido> lst_ce;
    private ArrayList<TarifaEstablecida> lst_te;
    private ArrayList<ConductorXVehiculo> lst_cxv;

    private ArrayList<DescripcionRuta> lst_dr;
    private ArrayList<VehiculoXRuta> lst_vxr;
    private ArrayList<VehiculoXRuta> lst_vxr_dph;
    private ArrayList<Despachador> lst_d;
    private ArrayList<GerenciaXVehiculo> lst_gxv;
    private ArrayList<Gerencia> lst_g;
    private ArrayList<Propietario_r> lst_p;

    private ArrayList<ArrayList<RutaXVehiculo>> lst_rxv;
    private ArrayList<RutaXVehiculoDph> lst_rxv_dph;

    private ArrayList<ConsolidadoLiquidacion> lst_cl;
    private ArrayList<Movil> lst_cvnl;
    private ArrayList<LiquidacionXLiquidador> lst_lxl;
    private ArrayList<LiquidacionIPK> lst_ipk;
    private ArrayList<CategoriaQueDescuentaPax> lst_cat_desc_pax;
    private ArrayList<Movil> lst_vh;
    private ArrayList<LiquidacionConsulta> lst_conlq;
    
    private ArrayList<CumplimientoRuta> lst_crxv;
    private ArrayList<CumplimientoRuta> lst_crc;
    private ArrayList<CumplimientoRuta> lst_crcd;
    private ArrayList<PlanillaDespacho> lst_cdph;
    
    private ArrayList<PuntoNoRegistrado> lst_pnr;
    private ArrayList<PuntoNoRegistrado> lst_pnrxv;
    private ArrayList<PuntoNoRegistrado> lst_pnrxc;
    
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");

    // Consulta de alarmas ocurridas en vueltas 
    // de un vehiculo en un rango de fechas especifico.
    public void AlarmasXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_AlarmasXVehiculo();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdVehiculo());
            ps.setString(2, pr.getFechaInicioStr());
            ps.setString(3, pr.getFechaFinalStr());

            rs = ps.executeQuery();

            lst_axv = new ArrayList<AlarmaXVehiculo>();
            while (rs.next()) {
                AlarmaXVehiculo axv = new AlarmaXVehiculo();
                axv.setPlaca(rs.getString("PLACA"));
                axv.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                axv.setNombreAlarma(rs.getString("NOMBRE_ALARMA"));
                axv.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                axv.setRuta(rs.getString("NOMBRE_RUTA"));
                axv.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                axv.setFecha(rs.getDate("FECHA_ALARMA"));
                axv.setHora(rs.getTime("HORA_ALARMA"));
                axv.setCantidad(rs.getInt("CANTIDAD_ALARMA"));
                axv.setUnidadMedicion(rs.getString("UNIDAD_MEDICION"));

                lst_axv.add(axv);
            }
            System.out.println(lst_axv.size());

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta producido (cantidad de pasajeros) 
    // hecho por un vehiculo en un rango de fechas especifico.
    public void ProduccionXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ProduccionXVehiculo();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdVehiculo());
            ps.setString(2, pr.getFechaInicioStr());
            ps.setString(3, pr.getFechaFinalStr());
            ps.setInt(4, pr.getIdEmpresa());
            //ps.setInt(4, pr.getIdUsuario());

            rs = ps.executeQuery();

            lst_pxv = new ArrayList<ProduccionXVehiculo>();
            while (rs.next()) {
                ProduccionXVehiculo p = new ProduccionXVehiculo();
                p.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                p.setHoraIngreso(rs.getTime("HORA_INGRESO"));
                p.setHoraSalida(rs.getTime("HORA_SALIDA"));
                p.setEntradas(rs.getInt("ENTRADAS"));
                p.setSalidas(rs.getInt("SALIDAS"));
                p.setNombreConductor(rs.getString("NOMBRE_CONDUCTOR"));
                p.setApellidoConductor(rs.getString("APELLIDO_CONDUCTOR"));
                p.setNumeracionLlegada(rs.getInt("NUMERACION_LLEGADA"));
                p.setNumeracionSalida(rs.getInt("NUMERACION_SALIDA"));
                p.setDiferenciaNumeracion(rs.getInt("DIFERENCIA_NUMERACION"));
                p.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                p.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                p.setTotalDia(rs.getInt("TOTAL_DIA"));
                p.setPlaca(rs.getString("PLACA"));
                p.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                p.setCantidadAlarmas(rs.getInt("CANTIDAD_ALARMAS"));
                p.setConteoPerimetro(rs.getInt("CONTEO_PERIMETRO"));

                lst_pxv.add(p);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta producido (cantidad de pasajeros)
    // hecho por los vehiculos que recorrieron determinada ruta en
    // un rango de fechas especifico.
    public void ProduccionXRuta(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ProduccionXRuta();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            ps.setInt(3, pr.getIdRuta());

            rs = ps.executeQuery();

            lst_pxr = new ArrayList<ProduccionXRuta>();
            while (rs.next()) {
                ProduccionXRuta p = new ProduccionXRuta();
                p.setPlaca(rs.getString("PLACA"));
                p.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                p.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS"));
                p.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                p.setPromedioPasajeros(rs.getInt("PROMEDIO_PASAJEROS"));
                p.setFecha(rs.getDate("FECHA_INGRESO"));
                lst_pxr.add(p);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta puntos de control reportados 
    // por un vehiculo en lo recorrido del dia.
    public void PuntosControl_GPS(ParametrosReporte pr) {
        
        String fecha_actual = ffmt.format(new java.util.Date());
        String fecha_ini = fecha_actual + " 00:00:00";
        String fecha_fin = fecha_actual + " 23:59:59";
        
        // Obtiene listado de bases
        ArrayList<Base> lst_bases = BaseBD.select();

        ConexionExterna conext = new ConexionExterna();
        try {
            Connection con = conext.conectar();
            // Obtiene instruccion sql para consulta
            String sql = ReportesSQL.sql_PuntosControl_GPS();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha_ini);
            ps.setString(2, fecha_fin);
            ps.setString(3, pr.getPlaca());
            ps.setString(4, fecha_ini);
            ps.setString(5, fecha_fin);
            ps.setString(6, pr.getPlaca());

            ResultSet rs = ps.executeQuery();
            lst_pc_gps = new ArrayList<PuntosControl>();

            while (rs.next()) {
                PuntosControl pc = new PuntosControl();
                // Campo localizacion (en minusculas)
                pc.setNombrePunto(rs.getString("GPS_PUNTO_CONTROL_2"));
                pc.setFecha(rs.getDate("GPS_FECHA"));
                pc.setHora(rs.getTime("GPS_HORA"));
                pc.setNumeracion(rs.getInt("GPS_NUMERACION"));
                pc.setNumeracionInicial(rs.getInt("GPS_NUMERACION_INICIAL"));
                pc.setPlaca(rs.getString("GPS_PLACA"));

                for (Base b : lst_bases) {
                    String base = b.getNombre().toLowerCase();
                    if (pc.getNombrePunto().contains(base)) {
                        pc.setEsBase(true);
                    }
                }

                lst_pc_gps.add(pc);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
    }

    // Consulta puntos de control
    // (base salida/punto control/base llegada)
    // registrados por un vehiculo en un rango de fechas especifico.
    public void PuntosControl(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_PuntosControl();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());

            //ps.setString(3, pr.getFechaInicioStr());
            //ps.setString(4, pr.getFechaFinalStr());
            ps.setInt(3, pr.getIdVehiculo());

            ps.setString(4, pr.getFechaInicioStr());
            ps.setString(5, pr.getFechaFinalStr());

            //ps.setString(8, pr.getFechaInicioStr());
            //ps.setString(9, pr.getFechaFinalStr());
            ps.setInt(6, pr.getIdVehiculo());

            ps.setString(7, pr.getFechaInicioStr());
            ps.setString(8, pr.getFechaFinalStr());

            //ps.setString(13, pr.getFechaInicioStr());
            //ps.setString(14, pr.getFechaFinalStr());
            ps.setInt(9, pr.getIdVehiculo());

            rs = ps.executeQuery();

            lst_pc = new ArrayList<PuntosControl>();
            int numeracion_inicial = -1;
            while (rs.next()) {
                PuntosControl pc = new PuntosControl();
                pc.setTipoPunto(rs.getInt("TIPO_PTR"));
                pc.setPlaca(rs.getString("PLACA"));
                pc.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                pc.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pc.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                pc.setFecha(rs.getDate("FECHA"));
                pc.setHora(rs.getTime("HORA"));
                pc.setNumeracion(rs.getInt("NUMERACION"));                
                pc.setEntradas(rs.getInt("ENTRADAS"));
                pc.setSalidas(rs.getInt("SALIDAS"));
                pc.setCantidadAlarmas(rs.getInt("CANTIDAD_ALARMAS"));
                
                if (numeracion_inicial == -1) {
                    numeracion_inicial = pc.getNumeracion();
                    pc.setNumeracionInicial(numeracion_inicial);
                } else {
                    pc.setNumeracionInicial(numeracion_inicial);
                }

                lst_pc.add(pc);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta vehiculos que emitieron una alarma 
    // especifica en un rango de fechas.
    public void VehiculosXAlarma(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_VehiculosXAlarma(pr.getListaAlarmas());

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());            

            rs = ps.executeQuery();

            lst_vxa = new ArrayList<VehiculoXAlarma>();
            while (rs.next()) {
                VehiculoXAlarma v = new VehiculoXAlarma();
                v.setCodigo(rs.getInt("CODIGO_ALARMA"));
                v.setAlarma(rs.getString("NOMBRE_ALARMA"));
                v.setPlaca(rs.getString("PLACA"));
                v.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                v.setFecha(rs.getDate("FECHA_ALARMA"));
                v.setHora(rs.getTime("HORA_ALARMA"));
                v.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                v.setUnidadMedicion(rs.getString("UNIDAD_MEDICION"));
                v.setCantidad(rs.getInt("CANTIDAD_ALARMA"));

                lst_vxa.add(v);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta informacion relacionada al
    // producido (cantidad de pasajeros) por cada vehiculo de una
    // determinada empresa en un rango de fechas.
    public void ConsolidadoXEmpresa(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ConsolidadoXEmpresa();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            //ps.setInt(3, pr.getIdUsuario());
            ps.setInt(3, pr.getIdEmpresa());

            rs = ps.executeQuery();

            lst_cxe = new ArrayList<ConsolidadoXEmpresa>();
            while (rs.next()) {
                ConsolidadoXEmpresa c = new ConsolidadoXEmpresa();
                c.setPlaca(rs.getString("PLACA"));
                c.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                c.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS"));
                c.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                c.setConteoPerimetro(rs.getInt("CONTEO_PERIMETRO"));
                c.setConteoAlarmas(rs.getInt("CONTEO_ALARMAS"));
                c.setPromedioPasajeros(rs.getInt("PROMEDIO_PASAJEROS"));
                c.setEmpresa(rs.getString("NOMBRE_EMPRESA"));
                c.setFecha(rs.getDate("FECHA"));

                lst_cxe.add(c);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta producido (cantidad de pasajeros)
    // de un determinado numero de rutas y rango de fecha especificado.
    public void ComparativoProduccionRuta(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ComparativoProduccionRuta(pr.getListaRutas());

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            //ps.setString(3, listaRutas);

            rs = ps.executeQuery();

            lst_cpr = new ArrayList<ComparativoProduccionRuta>();
            while (rs.next()) {
                ComparativoProduccionRuta c = new ComparativoProduccionRuta();
                c.setFecha(rs.getDate("FECHA"));
                c.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                c.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));

                lst_cpr.add(c);
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta nivel de ocupacion
    // junto a indice de capacidad utilizada en cada punto, 
    // hecha por un vehiculo en una o varias rutas.
    public void NivelOcupacion(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene capacidad de un vehiculo especifico
        int capacidad = MovilBD.selectCapacidad(pr.getIdVehiculo());
        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_NivelOcupacion(pr.unaRuta(), capacidad);

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            ps.setInt(3, pr.getIdVehiculo());
            ps.setString(4, pr.getFechaInicioStr());
            ps.setString(5, pr.getFechaFinalStr());
            ps.setInt(6, pr.getIdVehiculo());
            ps.setString(7, pr.getFechaInicioStr());
            ps.setString(8, pr.getFechaFinalStr());
            ps.setInt(9, pr.getIdVehiculo());

            if (pr.unaRuta()) {
                ps.setInt(10, pr.getIdRuta());
            }

            rs = ps.executeQuery();

            lst_no = new ArrayList<NivelOcupacion>();
            while (rs.next()) {
                NivelOcupacion no = new NivelOcupacion();
                no.setPlaca(rs.getString("PLACA"));
                no.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                no.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                no.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                no.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                no.setFecha(rs.getDate("FECHA"));
                no.setHora(rs.getTime("HORA"));
                no.setNivelOcupacion(rs.getInt("NIVEL_OCUPACION"));
                no.setIcu(rs.getInt("ICU"));
                
                int tipoPunto = rs.getInt("TIPO_PUNTO");
                if (tipoPunto == 1 || tipoPunto == 3)
                    no.setEsBase(true);
                else
                    no.setEsBase(false);
                
                lst_no.add(no);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta producido (cantidad de pasajeros)
    // realizado por un vehiculo de propietario en un rango de fechas
    // especifico.
    public void Propietario(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_Propietario();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            ps.setInt(3, pr.getIdVehiculo());

            rs = ps.executeQuery();

            lst_p = new ArrayList<Propietario_r>();
            while (rs.next()) {
                Propietario_r p = new Propietario_r();

                p.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                p.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                p.setFecha(rs.getDate("FECHA"));
                p.setHoraSalida(rs.getTime("HORA_SALIDA"));
                p.setHoraLlegada(rs.getTime("HORA_LLEGADA"));
                p.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                p.setTiempoVuelta(rs.getTime("TIEMPO_VUELTA"));
                p.setConteoPerimetro(rs.getInt("CONTEO_PERIMETRO"));
                p.setPlaca(rs.getString("PLACA"));
                p.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                
                lst_p.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta informacion de recorrido de rutas hecha por vehiculo
    // en un rango de fechas especifico.
    public void RutaXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_RutaXVehiculo();

        RutaXVehiculoUtil util = new RutaXVehiculoUtil();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setInt(2, pr.getIdVehiculo());

            rs = ps.executeQuery();

            int idir, idRuta; long horaSalidaSec;
            String fecha;
            Time horaSalida;

            lst_rxv = new ArrayList<ArrayList<RutaXVehiculo>>();
            while (rs.next()) {
                idir   = rs.getInt("IR_PK");
                idRuta = rs.getInt("RUTA_PK");
                fecha  = rs.getString("FECHA");
                horaSalida = rs.getTime("HORA_SALIDA");
                horaSalidaSec = rs.getLong("HORA_SALIDA_SEC");
                
                // Verifica los puntos recorridos vs los puntos establecidos
                // en la ruta, genera una lista con los puntos analizados
                // en tiempo y existencia
                // util.verificarRutaHechaPrueba(idir, idRuta, fecha, horaSalida);
                util.verificarRutaHechaDesdeRuta(idir, idRuta, fecha, horaSalida);
                ArrayList<RutaXVehiculo> lst = util.getLstRutaXVehiculo();
                
                for (RutaXVehiculo item : lst) {
                    item.setPlaca(rs.getString("PLACA"));
                    item.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                    item.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                    item.setFecha(fecha);
                }

                //System.out.println("IR: " + idir);
                //for (RutaXVehiculo r : lst) {
                //    System.out.println(r.getRuta() +" "+ r.getPuntoControl() +" "+ r.getNumeracion());
                //}
                lst_rxv.add(lst);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta informacion de despacho (tiempos y numeracion) 
    // realizado por vehiculo y fecha especifico.
    public void RutaXVehiculoDph(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_RutaXVehiculoDph();                
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getPlaca());
            ps.setString(2, pr.getFechaInicioStr());
            
            lst_rxv_dph = new ArrayList<RutaXVehiculoDph>();
            rs = ps.executeQuery();            
            while (rs.next()) {
                RutaXVehiculoDph rxv_dph = new RutaXVehiculoDph();                
                rxv_dph.setPlaca(pr.getPlaca());
                rxv_dph.setFecha(pr.getFechaInicioStr());
                rxv_dph.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                rxv_dph.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                rxv_dph.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                rxv_dph.setTipoPunto(rs.getInt("TIPO_PUNTO"));
                rxv_dph.setPunto(rs.getString("PUNTO"));
                rxv_dph.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                rxv_dph.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                rxv_dph.setHoraRealLlegada(rs.getString("HORA_REAL_LLEGADA"));
                rxv_dph.setDiferencia(rs.getInt("DIFERENCIA"));
                rxv_dph.setDiferenciaTiempo(rs.getString("DIFERENCIA_TIEMPO"));
                rxv_dph.setEstado(rs.getInt("ESTADO"));
                lst_rxv_dph.add(rxv_dph);
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta produccion efectiva (pasajeros, dinero recaudado)
    // de todos los vehiculos que han recorrido una o varias rutas en una fecha especifica.
    public void Gerencia(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtenemos todos los vehiculos de empresa actual
        String listaVehiculos = MovilBD.selectStringId(pr.getIdEmpresa());
        // Filtramos por rutas especificadas
        String listaRutas = pr.getListaRutas();        

        if (listaVehiculos == null
            || listaVehiculos == "") {
            return;
        }

        // Obtiene instruccion sql para consulta segun empresa
        String nombreEmpresa = pr.getNombreEmpresa();
        String sql;
        
        if (ReporteUtil.esEmpresa(nombreEmpresa, ReporteUtil.EMPRESA_FUSACATAN)) {            
            sql = ReportesSQL.sql_GerenciaFusa(listaVehiculos, listaRutas);
        } else {
            sql = ReportesSQL.sql_Gerencia(listaVehiculos, listaRutas);
        }

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());

            rs = ps.executeQuery();

            lst_g = new ArrayList<Gerencia>();
            while (rs.next()) {
                Gerencia g = new Gerencia();
                g.setPlaca(rs.getString("PLACA"));
                g.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                g.setFecha(rs.getDate("FECHA"));
                g.setHora(rs.getTime("HORA"));
                g.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                g.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS")); 
                g.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                g.setCantidadAlarmas(rs.getInt("CANTIDAD_ALARMAS"));                
                g.setPasajerosDescontados(rs.getInt("PASAJEROS_DESCONTADOS"));
                g.setPasajerosLiquidados(rs.getInt("PASAJEROS_LIQUIDADOS"));
                g.setValorLiquidado(rs.getInt("VALOR_LIQUIDADO"));
                g.setValorBruto(rs.getInt("TOTAL_BRUTO"));
                g.setValorDescuentoBruto(rs.getInt("TOTAL_DTO_BRUTO"));
                g.setValorDescuentoOperativo(rs.getInt("TOTAL_DESCUENTO_OPERATIVO"));
                g.setValorDescuentoAdministrativo(rs.getInt("TOTAL_DESCUENTO_ADMIN"));              

                lst_g.add(g);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta produccion efectiva (pasajeros, dinero recaudado)
    // de vehiculos que han recorrido una o varias rutas en una fecha especifica.
    public void GerenciaXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Filtramos por vehiculos especificados
        String listaVehiculos = pr.getListaVehiculos();
        // Obtenemos todos las rutas de empresa actual
        String listaRutas = RutaBD.selectStringId(pr.getIdEmpresa());        

        if (listaVehiculos == null
            || listaVehiculos == "") {
            return;
        }        

        String nombreEmpresa = pr.getNombreEmpresa();
        String sql;
        
        // Obtiene instruccion sql para consulta por empresa
        if (ReporteUtil.esEmpresa(nombreEmpresa, ReporteUtil.EMPRESA_FUSACATAN)) {            
            sql = ReportesSQL.sql_GerenciaXVehiculoFusa(listaVehiculos, listaRutas, true);
        } else {
            sql = ReportesSQL.sql_GerenciaXVehiculo(listaVehiculos, listaRutas, true);
        }
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());

            rs = ps.executeQuery();

            lst_gxv = new ArrayList<GerenciaXVehiculo>();
            while (rs.next()) {
                GerenciaXVehiculo g = new GerenciaXVehiculo();
                g.setPlaca(rs.getString("PLACA"));
                g.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                g.setFecha(rs.getDate("FECHA"));
                g.setHora(rs.getTime("HORA"));
                g.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                g.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS"));                
                g.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                g.setCantidadAlarmas(rs.getInt("CANTIDAD_ALARMAS"));                
                g.setPasajerosDescontados(rs.getInt("PASAJEROS_DESCONTADOS"));
                g.setPasajerosLiquidados(rs.getInt("PASAJEROS_LIQUIDADOS"));
                g.setValorLiquidado(rs.getInt("VALOR_LIQUIDADO"));
                g.setValorBruto(rs.getInt("TOTAL_BRUTO"));
                g.setValorDescuentoBruto(rs.getInt("TOTAL_DTO_BRUTO"));
                g.setValorDescuentoOperativo(rs.getInt("TOTAL_DESCUENTO_OPERATIVO"));
                g.setValorDescuentoAdministrativo(rs.getInt("TOTAL_DESCUENTO_ADMIN"));

                lst_gxv.add(g);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta informacion cabecera de recorrido 
    // hecha por vehiculos en una ruta, fecha y base en especifico.
    public void Despachador_IndicesIR(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_Despachador_IndicesIR(pr.unaRuta());

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setInt(2, pr.getIdBase());
            ps.setInt(3, pr.getIdBase());

            if (pr.unaRuta()) {
                ps.setInt(4, pr.getIdRuta());
            }

            rs = ps.executeQuery();

            lst_d = new ArrayList<Despachador>();
            while (rs.next()) {
                DespachadorIndiceIR dir = new DespachadorIndiceIR();
                dir.setIdIR(rs.getInt("IR_PK"));
                dir.setIdRuta(rs.getInt("RUTA_PK"));
                dir.setHoraSalida(rs.getTime("HORA_SALIDA"));

                dir.setPlaca(rs.getString("PLACA"));
                dir.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                dir.setHoraSalida(rs.getTime("HORA_SALIDA"));
                dir.setHoraLlegada(rs.getTime("HORA_LLEGADA"));
                dir.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                dir.setNombreConductor(rs.getString("NOMBRE_CONDUCTOR"));
                dir.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                dir.setFecha(rs.getDate("FECHA"));

                Despachador(con, dir);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta informacion detallada de una vuelta
    // (puntos, tiempos, numeracion, etc) hecha por un vehiculo, 
    // en una ruta y fecha especifico.
    public void Despachador(Connection con, DespachadorIndiceIR dir)
            throws SQLException {

        int idIR = dir.getIdIR();
        int idRuta = dir.getIdRuta();
        Time horaSalida = dir.getHoraSalida();

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_Despachador();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setTime(1, horaSalida);
        ps.setInt(2, idIR);
        ps.setTime(3, horaSalida);
        ps.setInt(4, idRuta);
        ps.setInt(5, idIR);
        ps.setInt(6, idRuta);
        ps.setInt(7, idRuta);
        ps.setInt(8, idIR);
        ps.setTime(9, horaSalida);
        //ps.setInt(10, idRuta);
        ps.setInt(10, idIR);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Despachador d = new Despachador();
            d.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
            d.setPuntoEnVuelta(rs.getBoolean("PUNTO_EN_VUELTA"));
            d.setAdelantado(rs.getBoolean("ADELANTADO"));
            d.setCantidadPasajeros(rs.getInt("NUMERACION"));
            d.setEsBase(rs.getBoolean("ES_BASE"));            
            
            d.setPlaca(dir.getPlaca());
            d.setNumeroInterno(dir.getNumeroInterno());
            d.setNumeroVuelta(dir.getNumeroVuelta());
            d.setFecha(dir.getFecha());
            d.setHoraSalida(dir.getHoraSalida());
            d.setHoraLlegada(dir.getHoraLlegada());
            d.setNombreConductor(dir.getNombreConductor());
            d.setNombreRuta(dir.getNombreRuta());

            lst_d.add(d);
        }
    }

    // Consulta conteos en perimetro de un vehiculo 
    // y rango de fechas determinado.
    public void ConteoPerimetro(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ConteoPerimetro();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            ps.setInt(3, pr.getIdVehiculo());

            rs = ps.executeQuery();

            lst_cp = new ArrayList<ConteoPerimetro_r>();
            while (rs.next()) {
                ConteoPerimetro_r cp = new ConteoPerimetro_r();
                cp.setPlaca(rs.getString("PLACA"));
                cp.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                cp.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                cp.setFecha(rs.getDate("FECHA"));
                cp.setHora(rs.getTime("HORA"));
                cp.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                cp.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                cp.setConteoPerimetro(rs.getInt("CONTEO_PERIMETRO"));

                lst_cp.add(cp);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta rutas asociadas a una empresa determinada.
    public void RutasEstablecidas(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_RutasEstablecidas();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdEmpresa());

            rs = ps.executeQuery();

            lst_re = new ArrayList<RutaEstablecida>();
            while (rs.next()) {
                RutaEstablecida re = new RutaEstablecida();
                re.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                re.setEstadoCreacion(rs.getInt("ESTADO_CREACION"));

                lst_re.add(re);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta vehiculos asociados a una empresa determinada.
    public void VehiculosEstablecidos(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_VehiculosEstablecidos();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdEmpresa());

            rs = ps.executeQuery();

            lst_ve = new ArrayList<VehiculoEstablecido>();
            while (rs.next()) {
                VehiculoEstablecido ve = new VehiculoEstablecido();
                ve.setPlaca(rs.getString("PLACA"));
                ve.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                ve.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
                ve.setFechaUltimaVuelta(rs.getDate("FECHA_ULTIMA_VUELTA"));
                ve.setDiasUltimaVuelta(rs.getLong("DIAS_ULTIMA_VUELTA"));

                lst_ve.add(ve);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta conductores asociados a una empresa determinada.
    public void ConductoresEstablecidos(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ConductoresEstablecidos();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdEmpresa());

            rs = ps.executeQuery();

            lst_ce = new ArrayList<ConductorEstablecido>();
            while (rs.next()) {
                ConductorEstablecido ce = new ConductorEstablecido();
                ce.setNombre(rs.getString("NOMBRE"));
                ce.setApellido(rs.getString("APELLIDO"));
                ce.setCedula(rs.getString("CEDULA"));
                ce.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));

                lst_ce.add(ce);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta relacion conductor-vehiculo
    // de una empresa determinada.
    public void ConductoresXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_ConductoresXVehiculo();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdEmpresa());
            ps.setInt(2, pr.getIdEmpresa());

            rs = ps.executeQuery();

            lst_cxv = new ArrayList<ConductorXVehiculo>();
            while (rs.next()) {
                ConductorXVehiculo c = new ConductorXVehiculo();
                c.setPlaca(rs.getString("PLACA"));
                c.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                c.setNombreConductor(rs.getString("NOMBRE_CONDUCTOR"));                
                c.setFechaHoraAsignacion(rs.getString("FECHA_ASIGNACION"));

                lst_cxv.add(c);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta descripcion (conformacion de puntos de control y tiempos) 
    // de una o varias rutas de una empresa determinada.
    public void DescripcionRuta(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_DescripcionRutas(pr.unaRuta());

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdEmpresa());

            if (pr.unaRuta()) {
                ps.setInt(2, pr.getIdRuta());
            }

            rs = ps.executeQuery();

            lst_dr = new ArrayList<DescripcionRuta>();
            while (rs.next()) {
                DescripcionRuta d = new DescripcionRuta();
                d.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                d.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                d.setMinutosPromedio(rs.getInt("PROMEDIO_MINUTOS"));
                d.setMinutosHolgura(rs.getInt("HOLGURA_MINUTOS"));
                d.setTipoPunto(rs.getInt("TIPO_PUNTO"));

                lst_dr.add(d);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    // Consulta informacion de recorrido de vehiculos 
    // que han recorrido una ruta en rango de fechas
    // especifico.
    public void VehiculosXRuta(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_VehiculosXRuta();

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, pr.getIdRuta());
            ps.setString(2, pr.getFechaInicioStr());
            ps.setString(3, pr.getFechaInicioStr());

            rs = ps.executeQuery();

            lst_vxr = new ArrayList<VehiculoXRuta>();
            while (rs.next()) {
                VehiculoXRuta v = new VehiculoXRuta();
                v.setFechaStr(pr.getFechaInicioStr());
                v.setIdVehiculo(rs.getInt("PK_VEHICULO"));
                v.setPlaca(rs.getString("PLACA"));
                v.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                v.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                v.setNombreConductor(rs.getString("NOMBRE_CONDUCTOR"));
                v.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));                
                v.setHoraLlegada(rs.getTime("HORA_LLEGADA"));
                v.setHoraLlegadaPlaneada(rs.getString("HORA_LLEGADA_PLANEADA"));
                v.setSigno(rs.getString("SIGNO"));
                v.setDiferencia(rs.getString("DIFERENCIA"));
                v.setHoraSalida(rs.getTime("HORA_SALIDA"));
                v.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                v.setEstado(rs.getInt("ESTADO"));

                lst_vxr.add(v);
            }
            filtrarListadoXPropietario(pr);

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta informacion de despacho (tiempos y numeracion) realizado por 
    // vehiculos en una ruta y fecha especifico.
    public void VehiculosXRutaDph(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_VehiculosXRutaDph();

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, pr.getFechaInicioStr());            
            ps.setInt(2, pr.getIdRuta());

            rs = ps.executeQuery();

            lst_vxr_dph = new ArrayList<VehiculoXRuta>();
            while (rs.next()) {
                VehiculoXRuta v = new VehiculoXRuta();
                v.setFechaStr(pr.getFechaInicioStr());
                v.setIdVehiculo(rs.getInt("PK_VEHICULO"));
                v.setPlaca(rs.getString("PLACA"));
                v.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                v.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                v.setNombreConductor(rs.getString("CONDUCTOR"));
                v.setCantidadPasajeros(rs.getInt("PASAJEROS"));                
                v.setHoraLlegada(rs.getTime("HORA_REAL_LLEGADA"));
                v.setHoraLlegadaPlaneada(rs.getString("HORA_PLANIFICADA"));
                v.setDiferencia(rs.getString("DIFERENCIA_TIEMPO"));
                v.setHoraSalida(rs.getTime("HORA_SALIDA"));
                v.setEstado(rs.getInt("ESTADO"));
                v.setNombreRuta(pr.getNombreRuta());

                lst_vxr_dph.add(v);
            }            
            filtrarListadoXPropietario(pr);

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void consolidadoLiquidacion(ParametrosReporte pr, Usuario u) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = ReportesSQL.sql_ConsolidadoLiquidacion(pr.getFechaInicioStr(), pr.getFechaFinalStr(), pr.getIdEmpresa(), u.getPerfilusuario(), u.getId(), pr.getIdEmpresa(), pr.getNombreEmpresa());
        
        

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ConsolidadoLiquidacion cl;
            lst_cl = new ArrayList<>();
            while (rs.next()) {

                cl = new ConsolidadoLiquidacion();
                cl.setPkVehiculo(rs.getInt("PK_VEHICULO"));
                cl.setPlaca(rs.getString("PLACA"));
                cl.setNumInterno(rs.getString("NUM_INTERNO"));
                cl.setVueltasLiquidadas(rs.getInt("VUELTAS_LIQUIDADAS"));
                cl.setTotalPasajerosLiquidados(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS"));
                cl.setTotalPasajerosDescontados(rs.getInt("TOTAL_PASAJEROS_DESCONTADOS"));
                cl.setTotalPasajeros(rs.getInt("TOTAL_PASAJEROS"));
                cl.setTarifa(rs.getDouble("TARIFA"));
                cl.setTotalBruto(rs.getDouble("TOTAL_BRUTO"));
                if (pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN") || pr.getNombreEmpresa().equalsIgnoreCase("tierragrata") || pr.getNombreEmpresa().equalsIgnoreCase("tierra grata")) {
                    cl.setTotalDescuentosAlNeto(rs.getDouble("TOTAL_DESCUENTO_AL_NETO"));
                }                
                cl.setTotalDescuentosOperativos(rs.getDouble("TOTAL_DESCUENTOS"));
                cl.setTotalDescuentosAdministrativos(rs.getDouble("TOTAL_OTROS_DESCUENTOS"));
                cl.setSubTotal(rs.getDouble("SUB_TOTAL"));
                cl.setTotalLiquidacion(rs.getDouble("TOTAL_LIQUIDACION"));
                lst_cl.add(cl);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void consolidadoVehiculosNoLiquidados(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = ReportesSQL.sql_ConsolidadoVehiculosNoLiquidados(pr.getFechaInicioStr(), pr.getFechaFinalStr(), pr.getIdEmpresa());

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            Movil m;
            lst_cvnl = new ArrayList<>();
            while (rs.next()) {

                m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setFkEmpresa(rs.getInt("FK_EMPRESA"));

                lst_cvnl.add(m);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void reporteLiquidacionXLiquidador(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = ReportesSQL.sql_LiquidacionXLiquidador(pr.getFechaInicioStr(), pr.getFechaFinalStr(), pr.getIdUsuarioLiquidador(), pr.getIdEmpresa(), pr.getNombreEmpresa());

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            LiquidacionXLiquidador lxl;
            lst_lxl = new ArrayList<>();
            while (rs.next()) {
                lxl = new LiquidacionXLiquidador();
                lxl.setPlaca(rs.getString("PLACA"));
                lxl.setNumInterno(rs.getInt("NUM_INTERNO"));
                lxl.setNumLiquidaciones(rs.getInt("NUM_LIQUIDACIONES"));
                lxl.setLiquidador(rs.getString("LIQUIDADOR"));
                lxl.setTotalBruto(rs.getDouble("TOTAL_BRUTO"));
                if (pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN") || pr.getNombreEmpresa().equalsIgnoreCase("tierragrata") || pr.getNombreEmpresa().equalsIgnoreCase("tierra grata")) {
                    lxl.setTotalDescuentosAlNeto(rs.getDouble("TOTAL_DESCUENTO_AL_NETO"));
                }
                lxl.setTotalDescuentos(rs.getDouble("TOTAL_DESCUENTOS"));
                lxl.setSubTotal(rs.getDouble("SUB_TOTAL"));
                lxl.setTotalOtrosDescuentos(rs.getDouble("TOTAL_OTROS_DESCUENTOS"));
                lxl.setTotalPasajerosDescontados(rs.getDouble("TOTAL_PASAJEROS_DESCONTADOS"));
                lxl.setTotalLiquidacion(rs.getDouble("TOTAL"));
                lst_lxl.add(lxl);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    
    
    public void reporteIndicePasajeroKilometro(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
//        Etiquetas EtiquetasLiquidacion = LiquidacionBD.searchTags();
        
        String sql = ReportesSQL.sql_reporteIPK(pr.getFechaInicioStr(), pr.getFechaFinalStr(), pr.getIdEmpresa(), pr.getNombreEmpresa());

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            LiquidacionIPK lisLqIpk;
            lst_ipk = new ArrayList<>();
            while (rs.next()) {
                lisLqIpk = new LiquidacionIPK();
                lisLqIpk.setPk_vehiculo(rs.getInt("PK_VEHICULO"));
                lisLqIpk.setPlaca(rs.getString("PLACA"));
                lisLqIpk.setNumero_interno(rs.getString("NUM_INTERNO"));
                lisLqIpk.setRuta(rs.getString("RUTA"));
                lisLqIpk.setIpk(rs.getDouble("IPK"));
                lisLqIpk.setTotal_km(rs.getDouble("KM_TOTAL"));
                lisLqIpk.setVueltasLiquidadas(rs.getInt("VUELTAS_LIQUIDADAS"));                
                lisLqIpk.setTotalPasajeros(rs.getInt("TOTAL_PASAJEROS"));
                lisLqIpk.setTotalPasajerosDescontados(rs.getInt("TOTAL_PASAJEROS_DESCONTADOS"));
                lisLqIpk.setTotalPasajerosLiquidados(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS"));                
                lisLqIpk.setTarifa(rs.getInt("TARIFA"));
                lisLqIpk.setTotalBrutoPasajeros(rs.getDouble("TOTAL_BRUTO"));
                if (pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN") || pr.getNombreEmpresa().equalsIgnoreCase("tierragrata") || pr.getNombreEmpresa().equalsIgnoreCase("tierra grata")) {
                    lisLqIpk.setTotalDescuentosAlNeto(rs.getDouble("TOTAL_DESCUENTO_AL_NETO"));
                }
                lisLqIpk.setTotalDescuentosOperativos(rs.getDouble("TOTAL_DESCUENTOS"));                
                lisLqIpk.setTotalDescuentosAdminsitrativos(rs.getDouble("TOTAL_OTROS_DESCUENTOS"));
                lisLqIpk.setSubTotal(rs.getDouble("SUB_TOTAL"));
                lisLqIpk.setTotalLiquidacion(rs.getDouble("TOTAL_LIQUIDACION"));
                lst_ipk.add(lisLqIpk);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    public void reporteCategoriasDescuentaPax(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder r = new StringBuilder();
         

            
            try {
                    lst_cat_desc_pax = new ArrayList<>();                        
                    for (Movil vh : lst_vh) {
                        String sql = ReportesSQL.sql_consulta_categorias_descuenta_pax(pr.getFechaInicioStr()+" 00:00:00", pr.getFechaFinalStr()+" 23:59:59", vh.getId());
                        ps = con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        CategoriaQueDescuentaPax categoriaDescuento;                        
                        HashMap<Integer, Integer> todasLasCategorias= new HashMap<>();
                        boolean ultimo = rs.last();
                        if (ultimo) {
                            int fila=rs.getRow();                        
                            rs.beforeFirst();                        
                            rs.first();          
                            int i=0;
                            categoriaDescuento = new CategoriaQueDescuentaPax();                                
                            categoriaDescuento.setPk_vehiculo(rs.getInt("id_vehiculo"));                                
                            categoriaDescuento.setCategoria(rs.getString("nombre"));                                
                            categoriaDescuento.setPlaca(rs.getString("placa"));                                                        
                            categoriaDescuento.setTotalPasajeroDescontados( rs.getInt("total_pasajeros_descontados"));                        
                            categoriaDescuento.setTotalPasajeroLiquidados(rs.getInt("total_pasajeros_liquidados"));                        
                            categoriaDescuento.setTotalPasajeros(rs.getInt("total_pasajeros"));                        
                            categoriaDescuento.setIndicadorPasajeros(rs.getDouble("indicador_pasajeros"));
                            while (fila > i) {
                                    todasLasCategorias.put(rs.getInt("id_categoria"), rs.getInt("total_pasajeros_categoria"));
                                    //System.out.println("--> "+rs.getString("nombre")+", "+rs.getInt("total_pasajeros_categoria"));
                                    if (rs.isLast()){
                                        ultimo = false;
                                    }else{
                                        rs.next();
                                    }                                
                                    i++;                                
                            }//FIN WHILE


                            categoriaDescuento.setCategorias(todasLasCategorias);                                                            
                            lst_cat_desc_pax.add(categoriaDescuento);
                        }else{//IF ULTIMO
                            System.out.println("Esta vacio");
                        }
                    
                    }

            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                UtilBD.closeResultSet(rs);
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }        
        
    }
    
    public void vehiculos(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        String sql = ReportesSQL.sql_consulta_vehiculos(pr.getFechaInicioStr()+" 00:00:00", pr.getFechaFinalStr()+" 23:59:59");

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            Movil vh;
            lst_vh = new ArrayList<>();
            while (rs.next()) {
                vh = new Movil();
                vh.setId(rs.getInt("PK_VEHICULO"));                
                lst_vh.add(vh);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    public void consultaDeLiquidacion(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
    
    String sql = ReportesSQL.sql_consulta_liquidacion(pr.getFechaInicioStr(), pr.getFechaFinalStr(), pr.getIdVehiculo(), pr.getIdEmpresa(), pr.getNombreEmpresa());

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            LiquidacionConsulta lisLqIpk;
            lst_conlq = new ArrayList<>();
            while (rs.next()) {
                lisLqIpk = new LiquidacionConsulta();
                lisLqIpk.setPk_liquidacion(rs.getInt("PK_LIQUIDACION_GENERAL"));
                lisLqIpk.setFk_vehiculo(rs.getInt("FK_VEHICULO"));
                lisLqIpk.setFk_tarifa(rs.getInt("FK_TARIFA_FIJA"));
                lisLqIpk.setFk_conductor(rs.getInt("FK_CONDUCTOR"));
                lisLqIpk.setFk_usuario(rs.getInt("USUARIO"));
                lisLqIpk.setPlaca(rs.getString("PLACA"));
                lisLqIpk.setNum_interno(rs.getString("NUM_INTERNO"));               
                lisLqIpk.setValor_tarifa(rs.getInt("VALOR_TARIFA"));
                lisLqIpk.setLiquidador(rs.getString("LIQUIDADOR"));
                lisLqIpk.setConductor(rs.getString("CONDUCTOR"));
                lisLqIpk.setTotal_pasajeros_liquidados(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS"));                
                lisLqIpk.setTotal_valor_vueltas(rs.getDouble("TOTAL_BRUTO"));
                if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {                    
                    lisLqIpk.setTotal_valor_descuento_al_neto(rs.getDouble("TOTAL_DESCUENTO_AL_NETO"));
                }
                lisLqIpk.setSubtotal(rs.getDouble("SUBTOTAL"));
                lisLqIpk.setTotal_valor_descuentos(rs.getDouble("TOTAL_VALOR_DESCUENTOS"));                
                lisLqIpk.setTotal_valor_otros_descuentos(rs.getDouble("TOTAL_VALOR_OTROS_DESCUENTOS"));                
                lisLqIpk.setTotal_liquidacion(rs.getDouble("TOTAL_LIQUIDACION"));
                lisLqIpk.setEstado(rs.getInt("ESTADO"));
                lisLqIpk.setFecha_liquidacion(rs.getDate("FECHA_LIQUIDACION"));
                lst_conlq.add(lisLqIpk);
            }    
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    
    }
    
    // Consulta cumplimiento de ruta
    // (puntos reales de ruta vs puntos registrados) por vehiculos,
    //  que hicieron una ruta y fecha en especifico.
    public void cumplimientoRutaXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_CumplimientoRutaXVehiculo();
        
        try {
            ps = con.prepareStatement(sql);            
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            ps.setString(3, pr.getFechaInicioStr());
            ps.setString(4, pr.getFechaFinalStr());
            ps.setInt(5, pr.getIdRuta());
            rs = ps.executeQuery();
            
            lst_crxv = new ArrayList<CumplimientoRuta>();
            while (rs.next()) {
                CumplimientoRuta cr = new CumplimientoRuta();
                cr.setIdVehiculo(rs.getInt("PK_VEHICULO"));
                cr.setPlaca(rs.getString("PLACA"));
                cr.setNumeroInterno(rs.getString("NUM_INTERNO"));
                cr.setNombreConductores(rs.getString("LISTA_CONDUCTORES"));
                cr.setPuntosRealizados(rs.getInt("TOTAL_PUNTOS_RUTA"));
                cr.setPuntosCumplidos(rs.getInt("TOTAL_PUNTOS_CUMPLIDOS"));
                cr.setPorcentajeCumplido(rs.getDouble("PORCENTAJE_CUMPLIDO"));
                
//                if (cr.getPuntosCumplidos() > cr.getPuntosRealizados()) {
//                    cr.setPuntosCumplidos(cr.getPuntosRealizados());
//                    cr.setPorcentajeCumplido(1.0);
//                }
                
                lst_crxv.add(cr);
            }
            filtrarListadoXPropietario(pr);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta cumplimiento de ruta
    // (puntos reales de ruta vs puntos registrados) por vehiculos,
    //  en todas las rutas en una fecha en especifico.
    public void cumplimientoRutaConsolidado(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_CumplimientoRutaConsolidado();
        
        try {
            ps = con.prepareStatement(sql);            
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            rs = ps.executeQuery();
            
            lst_crc = new ArrayList<CumplimientoRuta>();
            while (rs.next()) {
                CumplimientoRuta cr = new CumplimientoRuta();
                cr.setIdRuta(rs.getInt("PK_RUTA"));
                cr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                cr.setPuntosRealizados(rs.getInt("TOTAL_PUNTOS_RUTA"));
                cr.setPuntosCumplidos(rs.getInt("TOTAL_PUNTOS_CUMPLIDOS"));
                cr.setPorcentajeCumplido(rs.getDouble("PORCENTAJE_CUMPLIDO"));
                
//                if (cr.getPuntosCumplidos() > cr.getPuntosRealizados()) {
//                    cr.setPuntosCumplidos(cr.getPuntosRealizados());
//                    cr.setPorcentajeCumplido(1.0);
//                }
                
                lst_crc.add(cr);
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta cumplimiento de ruta
    // (puntos reales de ruta vs puntos registrados) por conductores,
    // en una o varias rutas y fecha en especifico.
    public void cumplimientoRutaXConductor(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        boolean filtro_ruta = pr.unaRuta();
        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_CumplimientoRutaPorConductor(filtro_ruta);
        String nombre_conductor;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            if (filtro_ruta)
                ps.setInt(3, pr.getIdRuta());
            rs = ps.executeQuery();
            
            lst_crcd = new ArrayList<CumplimientoRuta>();            
            while (rs.next()) {
                CumplimientoRuta cr = new CumplimientoRuta();
                nombre_conductor = rs.getString("NOMBRE_CONDUCTOR");
                nombre_conductor = nombre_conductor.toLowerCase();
                nombre_conductor = StringUtils.upperFirstLetter(nombre_conductor);
                cr.setNombreConductores(nombre_conductor);
                cr.setPuntosRealizados(rs.getInt("TOTAL_PUNTOS_RUTA"));
                cr.setPuntosCumplidos(rs.getInt("TOTAL_PUNTOS_CUMPLIDOS"));
                cr.setPorcentajeCumplido(rs.getDouble("PORCENTAJE_CUMPLIDO"));                
                
//                if (cr.getPuntosCumplidos() > cr.getPuntosRealizados()) {
//                    cr.setPuntosCumplidos(cr.getPuntosRealizados());
//                    cr.setPorcentajeCumplido(1.0);
//                }
                
                lst_crcd.add(cr);
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta informacion de planilla despacho para
    // un listado de vehiculos especifico.
    public void consolidadoDespacho(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Obtiene instruccion sql para consulta
        String sql = ReportesSQL.sql_consolidadoDespacho(pr.getListaVehiculosPlaca());
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setString(2, pr.getFechaFinalStr());
            ps.setInt(3, pr.getIdRuta());
            
            rs = ps.executeQuery();
            lst_cdph = new ArrayList<PlanillaDespacho>();
            
            while (rs.next()) {
                PlanillaDespacho pd = new PlanillaDespacho();
                pd.setFecha(rs.getDate("FECHA"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setPunto(rs.getString("PUNTO"));
                pd.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                pd.setHoraReal(rs.getTime("HORA_REAL"));
                pd.setDiferencia(rs.getInt("DIFERENCIA"));
                pd.setDiferenciaTiempo(rs.getTime("DIFERENCIA_TIEMPO"));
                lst_cdph.add(pd);
            }
            
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Mantiene numeracion inicial de siguiente vuelta, igual
    // a la finalizada en la vuelta anterior.
    public void mantenerNumeracionInicial(ArrayList<ProduccionXHora> lst) {
        
        if (lst != null && lst.size() > 0) {
            
            for (int i = 1; i < lst.size(); i++) {
                ProduccionXHora p1 = lst.get(i-1);
                ProduccionXHora p2 = lst.get(i);
                
                if (p1.getPlaca().compareTo(p2.getPlaca()) == 0) {
                    p2.setNumeracionInicial(p1.getNumeracionFinal());
                }
                
                long np1 = p1.getNumeracionFinal() - p1.getNumeracionInicial();
                long np2 = p2.getNumeracionFinal() - p2.getNumeracionInicial();
                p1.setCantidadPasajeros(np1);
                p2.setCantidadPasajeros(np2);
            }
        }
    }
    
    // Mantiene registro de entrada inicial en siguiente vuelta, igual a la finalizada en
    // vuelta anterior.
    public void mantenerEntradasInicial(ArrayList<ProduccionXHora> lst) {
        
        if (lst != null && lst.size() > 0) {                        
            
            boolean hayEntrada = false;
            long np = 0;
            
            for (int i = 1; i < lst.size(); i++) {
                ProduccionXHora p1 = lst.get(i-1);
                ProduccionXHora p2 = lst.get(i);
                
                if (p1.getPlaca().compareTo(p2.getPlaca()) == 0) {
                    p2.setEntradasInicial(p1.getEntradasFinal());
                } else {
                    np = 0;
                    continue;
                }
                
                long np1 = p1.getEntradasFinal() - p1.getEntradasInicial();
                long np2 = p2.getEntradasFinal() - p2.getEntradasInicial();
                                
                if (np1 > 0) {
                    np += np1;
                    hayEntrada = true;
                }
                
                if (hayEntrada) {                    
                    if (np2 >= np) {
                        np2 = p2.getEntradasFinal() - np;                        
                        p2.setEntradasInicial(np);
                    }
                }
                
                p1.setCantidadPasajeros(np1);
                p2.setCantidadPasajeros(np2);
            }
        }
    }
    
    // Consulta producido (cantidad de pasajeros)
    // hecho por los vehiculos en cada hora.
    public void produccionAgrupadoXHora(ParametrosReporte pr) {
        
        // Obtiene instruccion sql para consulta
        ConexionExterna conext = new ConexionExterna();
        String sql = ReportesSQL.sql_ProduccionXHora();
        
        String fecha_inicial = pr.getFechaInicioStr() + " 00:00:00";
        String fecha_final   = pr.getFechaInicioStr() + " 23:59:59";       
        
        try {
            Connection con = conext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicial);
            ps.setString(2, fecha_final);
            ResultSet rs = ps.executeQuery();
            
            lst_pxh = new ArrayList<ProduccionXHora>();
            while (rs.next()) {
                ProduccionXHora pxh = new ProduccionXHora();
                pxh.setFecha(rs.getString("FECHA"));
                pxh.setHora(rs.getInt("HORA"));
                pxh.setEntradasInicial(rs.getLong("ENTRADAS_INICIAL"));
                pxh.setEntradasFinal(rs.getLong("ENTRADAS_FINAL"));
                //pxh.setNumeracionInicial(rs.getLong("NUMERACION_INICIAL"));
                //pxh.setNumeracionFinal(rs.getLong("NUMERACION_FINAL"));
                //long cantidad_pasajeros = pxh.getNumeracionFinal() - pxh.getNumeracionInicial();
                //pxh.setCantidadPasajeros(cantidad_pasajeros);
                //pxh.setCantidadPasajeros(rs.getInt("CANTIDAD_PASAJEROS"));
                pxh.setPlaca(rs.getString("PLACA"));
                lst_pxh.add(pxh);
            }
            //mantenerNumeracionInicial(lst_pxh);
            mantenerEntradasInicial(lst_pxh);
            ps.close();
            rs.close();
            
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) { 
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
    }
    
    // Consulta puntos de control no registrados de una ruta y
    // fecha en especifico.
    public void puntoNoRegistrado(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " ir.FECHA_INGRESO as FECHA,"
                        + " p.PK_PUNTO       as PK_PUNTO,"
                        + " p.NOMBRE         as NOMBRE_PUNTO,"
                        + " ir.FK_RUTA       as PK_RUTA,"
                        + " r.NOMBRE         as NOMBRE_RUTA,"
                        + " count(pnr.FK_PUNTO) as PUNTO_NO_REGISTRADO"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_punto_control_no_registrado as pnr on"
                        + "        ir.PK_INFORMACION_REGISTRADORA = pnr.FK_INFORMACION_REGISTRADORA"
                        + " INNER JOIN tbl_punto as p on"
                        + "        p.PK_PUNTO = pnr.FK_PUNTO and p.ESTADO = 1"
                        + " INNER JOIN tbl_ruta as r on"
                        + "        r.PK_RUTA = ir.FK_RUTA and r.ESTADO = 1"
                        + " WHERE"
                        + "        ir.FK_RUTA = ? and"
                        + "        ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                        + "        pnr.FK_PUNTO != 100 and pnr.FK_PUNTO != 101"
                        + " GROUP BY pnr.FK_PUNTO"
                        + " ORDER BY ir.FECHA_INGRESO asc, PUNTO_NO_REGISTRADO desc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getIdRuta());
            ps.setString(2, pr.getFechaInicioStr());
            ps.setString(3, pr.getFechaFinalStr());
            rs = ps.executeQuery();
            
            lst_pnr = new ArrayList<PuntoNoRegistrado>();
            int indice_total = 0;
            while (rs.next()) {
                
                PuntoNoRegistrado pnr = new PuntoNoRegistrado();
                pnr.setFecha(rs.getString("FECHA"));
                pnr.setIdPunto(rs.getInt("PK_PUNTO"));
                pnr.setIdRuta(rs.getInt("PK_RUTA"));
                pnr.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                pnr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pnr.setIndice(rs.getInt("PUNTO_NO_REGISTRADO"));
                indice_total += pnr.getIndice();
                
                lst_pnr.add(pnr);
            }
            estadisticaPuntoNoRegistrado(lst_pnr, indice_total);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta la cantidad de veces que un punto no fue registrado
    // por un vehiculo en una ruta y fecha especifica.
    public void puntoNoRegistradoXVehiculo(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        String sql = "SELECT"
                        + " ir.FK_RUTA       as PK_RUTA,"
                        + " ir.FECHA_INGRESO as FECHA,"
                        + " pnr.FK_PUNTO     as PK_PUNTO,"
                        + " r.NOMBRE         as NOMBRE_RUTA,"
                        + " p.NOMBRE         as NOMBRE_PUNTO,"
                        + " v.PK_VEHICULO    as PK_VEHICULO,"
                        + " v.PLACA          as PLACA,"
                        + " v.NUM_INTERNO    as NUMERO_INTERNO,"
                        + " count(pnr.FK_PUNTO) as PUNTO_NO_REGISTRADO"	
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_punto_control_no_registrado as pnr on"
                        + "     ir.PK_INFORMACION_REGISTRADORA = pnr.FK_INFORMACION_REGISTRADORA"
                        + " INNER JOIN tbl_punto as p on"
                        + "     p.PK_PUNTO = pnr.FK_PUNTO and p.ESTADO = 1"
                        + " INNER JOIN tbl_ruta as r on"
                        + "     r.PK_RUTA = ir.FK_RUTA and r.ESTADO = 1"
                        + " INNER JOIN tbl_vehiculo as v on"
                        + "     v.PK_VEHICULO = ir.FK_VEHICULO and v.ESTADO = 1"
                        + " WHERE" 
                        + "     ir.FECHA_INGRESO = ? and"
                        + "     ir.FK_RUTA = ? and"                        
                        + "     pnr.FK_PUNTO = ?"                        
                        + " GROUP BY ir.FK_VEHICULO"
                        + " ORDER BY ir.FECHA_INGRESO asc, PUNTO_NO_REGISTRADO desc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setInt(2, pr.getIdRuta());
            ps.setInt(3, pr.getIdPunto());
            rs = ps.executeQuery();
            
            lst_pnrxv = new ArrayList<PuntoNoRegistrado>();
            int indice_total = 0;
            while (rs.next()) {
                
                PuntoNoRegistrado pnr = new PuntoNoRegistrado();
                pnr.setIdRuta(rs.getInt("PK_RUTA"));
                pnr.setIdPunto(rs.getInt("PK_PUNTO"));
                pnr.setFecha(rs.getString("FECHA"));
                pnr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pnr.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                pnr.setIdVehiculo(rs.getInt("PK_VEHICULO"));
                pnr.setPlaca(rs.getString("PLACA"));
                pnr.setNumeroInterno(rs.getString("NUMERO_INTERNO"));                
                pnr.setIndice(rs.getInt("PUNTO_NO_REGISTRADO"));
                indice_total += pnr.getIndice();
                
                lst_pnrxv.add(pnr);
            }            
            estadisticaPuntoNoRegistrado(lst_pnrxv, indice_total);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Consulta la cantidad de veces que un punto no fue registrado
    // por un conductor asociado a un vehiculo en una ruta y fecha especifica.
    public void puntoNoRegistradoXConductor(ParametrosReporte pr) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " ir.FECHA_INGRESO    as FECHA,"
                        + " r.NOMBRE            as NOMBRE_RUTA,"
                        + " p.NOMBRE            as NOMBRE_PUNTO,"
                        + " v.PLACA             as PLACA,"
                        + " v.NUM_INTERNO       as NUMERO_INTERNO,"
                        + " concat(c.NOMBRE, ' ', c.APELLIDO) as NOMBRE_CONDUCTOR,"
                        + " count(pnr.FK_PUNTO) as PUNTO_NO_REGISTRADO"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_punto_control_no_registrado as pnr on"
                        + "         ir.PK_INFORMACION_REGISTRADORA = pnr.FK_INFORMACION_REGISTRADORA"
                        + " INNER JOIN tbl_punto as p on"
                        + "         p.PK_PUNTO = pnr.FK_PUNTO and p.ESTADO = 1"
                        + " INNER JOIN tbl_ruta as r on"
                        + "         r.PK_RUTA = ir.FK_RUTA and r.ESTADO = 1"
                        + " INNER JOIN tbl_vehiculo as v on"
                        + "         v.PK_VEHICULO = ir.FK_VEHICULO and v.ESTADO = 1"
                        + " INNER JOIN tbl_conductor as c on"
                        + "         c.PK_CONDUCTOR = ir.FK_CONDUCTOR and c.ESTADO = 1"
                        + " WHERE"
                        + "         ir.FECHA_INGRESO = ? and"
                        + "         ir.FK_RUTA = ? and"
                        + "         ir.FK_VEHICULO = ? and"
                        + "         pnr.FK_PUNTO = ?"
                        + " GROUP BY ir.FK_CONDUCTOR"
                        + " ORDER BY ir.FECHA_INGRESO asc, PUNTO_NO_REGISTRADO desc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getFechaInicioStr());
            ps.setInt(2, pr.getIdRuta());
            ps.setInt(3, pr.getIdVehiculo());
            ps.setInt(4, pr.getIdPunto());
            rs = ps.executeQuery();
            
            lst_pnrxc = new ArrayList<PuntoNoRegistrado>();
            int indice_total = 0;
            while (rs.next()) {
                
                PuntoNoRegistrado pnr = new PuntoNoRegistrado();
                pnr.setPlaca(rs.getString("PLACA"));
                pnr.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                pnr.setFecha(rs.getString("FECHA"));
                pnr.setIndice(rs.getInt("PUNTO_NO_REGISTRADO"));
                pnr.setNombreConductor(rs.getString("NOMBRE_CONDUCTOR"));
                indice_total += pnr.getIndice();
                
                lst_pnrxc.add(pnr);                
            }
            estadisticaPuntoNoRegistrado(lst_pnrxc, indice_total);
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }
    
    // Calcula el valor porcentual correspondiente a cada indice/cantidad
    // de un registro individual segun la cantidad total obtenida. 
    // En este caso el valor porcentual de: 
    // # de veces pto no registrado vs total # veces ptos no registrados.
    public void estadisticaPuntoNoRegistrado(
                    ArrayList<PuntoNoRegistrado> lst, 
                    int indice_total) {
        
        if (lst != null) {
            for (int i = 0; i < lst.size(); i++) {
                PuntoNoRegistrado pnr = lst.get(i);
                int indice = pnr.getIndice();
                double indice_porcentual = (double) indice / indice_total;                
                pnr.setIndicePorcentual(indice_porcentual);
                pnr.setIndiceTotal(indice_total);
            }
        }
    }        

    //    public void consolidadoLiquidacion(ParametrosReporte pr) {
    //
    //        ArrayList<Movil> lstMovil = MovilBD.getByEmpresaId(pr.getIdEmpresa());
    //
    //        lst_cl = new ArrayList();
    //
    //        InformacionRegistradoraBD irbd = new InformacionRegistradoraBD();
    //        //Consulta las vueltas por cada vehiculo que se encuentre en la empresa seleccionada
    //        for (Movil v : lstMovil) {
    //            InformacionRegistradora ir = new InformacionRegistradora();
    //            ir.setIdVehiculo(v.getId());
    //            ir.setEstado(new Short("1"));
    //            ir.setVehiculo(v);
    //            ArrayList<InformacionRegistradora> vueltas_tmp = irbd.getByRangeConsolidado(ir, pr.getFechaInicioStr(), pr.getFechaFinalStr());
    //            if (vueltas_tmp != null && vueltas_tmp.size() > 0) {
    //                lst_cl.addAll(vueltas_tmp);
    //            }
    //        }
    //    }
    
    // Filtra listados manteniendo unicamente los registros
    // de vehiculos asociados a un usuario propetario.
    public void filtrarListadoXPropietario(ParametrosReporte pr) {        
        
        /*
         * Listados filtrados
         *  lst_crxv    : listado cumplimiento por vehiculo
         *  lst_vxr     : listado vehiculo por ruta
         *  lst_vxr_dph : listado vehiculo por ruta dph
         */
        
        ArrayList<Movil> lst_mv = MovilBD.selectByOwner(pr.getIdUsuario());
        
        if (pr.esUsuarioPropietario() && lst_mv != null) {                       
            
            if (lst_crxv != null) {
                ArrayList<CumplimientoRuta> tlst_crxv = new ArrayList<CumplimientoRuta>();
                for (Movil mv : lst_mv) {                
                    for (CumplimientoRuta cr : lst_crxv) {                    
                        if (mv.getId() == cr.getIdVehiculo()) {
                            tlst_crxv.add(cr);
                        }
                    }
                }
                lst_crxv = tlst_crxv;
            }
            
            if (lst_vxr != null) {
                ArrayList<VehiculoXRuta> tlst_vxr = new ArrayList<VehiculoXRuta>();
                for (Movil mv : lst_mv) {
                    for (VehiculoXRuta vxr : lst_vxr) {
                        if (mv.getId() == vxr.getIdVehiculo()) {
                            tlst_vxr.add(vxr);
                        }
                    }
                }
                lst_vxr  = tlst_vxr;
            }
            
            if (lst_vxr_dph != null) {
                ArrayList<VehiculoXRuta> tlst_vxr_dph = new ArrayList<VehiculoXRuta>();
                for (Movil mv : lst_mv) {
                    for (VehiculoXRuta vxr : lst_vxr_dph) {
                        if (mv.getId() == vxr.getIdVehiculo()) {
                            tlst_vxr_dph.add(vxr);
                        }
                    }
                }
                lst_vxr_dph = tlst_vxr_dph;
            }
        }
    }
    
    public static double valorDescuentoCategoria(String nombre) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        if (nombre == null) return 0.0;
        
        String sql = "SELECT IF(VALOR <= 0, 0, VALOR/100) as PTJ_DESCUENTO FROM tbl_categoria_descuento WHERE"
                        + " NOMBRE LIKE ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("PTJ_DESCUENTO");
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);            
            pila_con.liberarConexion(con);
        }
        
        return 0.0;
    }        
    
    public ArrayList<AlarmaXVehiculo> getLstAlarmaXVehiculo() {
        return lst_axv;
    }

    public ArrayList<ProduccionXVehiculo> getLstProduccionXVehiculo() {
        return lst_pxv;
    }

    public ArrayList<ProduccionXRuta> getLstProduccionXRuta() {
        return lst_pxr;
    }
    
    public ArrayList<ProduccionXHora> getLstProduccionXHora() {
        return lst_pxh;
    }

    public ArrayList<PuntosControl> getLstPuntosControl() {
        return lst_pc;
    }

    public ArrayList<PuntosControl> getLstPuntosControlGPS() {
        return lst_pc_gps;
    }

    public ArrayList<VehiculoXAlarma> getLstVehiculoXAlarma() {
        return lst_vxa;
    }

    public ArrayList<ConsolidadoXEmpresa> getLstConsolidadoXEmpresa() {
        return lst_cxe;
    }

    public ArrayList<ComparativoProduccionRuta> getLstComparativoProduccionRuta() {
        return lst_cpr;
    }

    public ArrayList<NivelOcupacion> getLstNivelOcupacion() {
        return lst_no;
    }

    public ArrayList<ConteoPerimetro_r> getLstConteoPerimetro() {
        return lst_cp;
    }
    
    public ArrayList<RutaEstablecida> getLstRutaEstablecida() {
        return lst_re;
    }

    public ArrayList<VehiculoEstablecido> getLstVehiculoEstablecido() {
        return lst_ve;
    }

    public ArrayList<ConductorEstablecido> getLstConductorEstablecido() {
        return lst_ce;
    }

    public ArrayList<ConductorXVehiculo> getLstConductorXVehiculos() {
        return lst_cxv;
    }

    public ArrayList<DescripcionRuta> getLstDescripcionRuta() {
        return lst_dr;
    }

    public ArrayList<VehiculoXRuta> getLstVehiculoXRuta() {
        return lst_vxr;
    }
    
    public ArrayList<VehiculoXRuta> getLstVehiculoXRutaDph() {
        return lst_vxr_dph;
    }

    public ArrayList<Despachador> getLstDespachador() {
        return lst_d;
    }

    public ArrayList<GerenciaXVehiculo> getLstGerenciaXVehiculo() {
        return lst_gxv;
    }

    public ArrayList<Gerencia> getLstGerencia() {
        return lst_g;
    }

    public ArrayList<ArrayList<RutaXVehiculo>> getLstRutaXVehiculo() {
        return lst_rxv;
    }
    
    public ArrayList<RutaXVehiculoDph> getLstRutaXVehiculoDph() {
        return lst_rxv_dph;
    }

    public ArrayList<Propietario_r> getLstPropietario() {
        return lst_p;
    }

    public ArrayList<ConsolidadoLiquidacion> getLstConsolidadoLiquidacion() {
        return lst_cl;
    }

    public ArrayList<Movil> getLstConsolidadoVehiculosNoLiquidados() {
        return lst_cvnl;
    }

    public ArrayList<LiquidacionXLiquidador> getLstLiquidacionXLiquidador() {
        return lst_lxl;
    }
    
    public ArrayList<LiquidacionIPK> getLstIPK() {
        return lst_ipk;
    }
    
    public ArrayList<CategoriaQueDescuentaPax> getLstCat() {
        return lst_cat_desc_pax;
    }
    
    // Calcula el valor porcentual de cada registro, evaluando
    // cantidad de puntos cumplidos vs la cantidad de puntos existentes/realizados.
    public CumplimientoRutaTotales getTotalesCumplimientoRuta(ArrayList<CumplimientoRuta> lst) {
        
        CumplimientoRutaTotales crt = new CumplimientoRutaTotales();
        int puntos_realizados = 0, puntos_cumplidos = 0;
        
        for (CumplimientoRuta cr : lst) {
            puntos_realizados += cr.getPuntosRealizados();
            puntos_cumplidos  += cr.getPuntosCumplidos();
        }
        
        double porcetaje_cumplido = (double) puntos_cumplidos / (double) puntos_realizados;
        
        crt.setTotalRegistros(lst.size());
        crt.setTotalPuntosRealizados(puntos_realizados);
        crt.setTotalPuntosCumplidos(puntos_cumplidos);
        crt.setTotalPorcentajeCumplido(porcetaje_cumplido);
                
        return crt;
    }
    
    public ArrayList<CumplimientoRuta> getLstCumplimientoRutaXVehiculo() {
        return lst_crxv;
    }  
    
    public ArrayList<CumplimientoRuta> getLstCumplimientoRutaConsolidado() {
        return lst_crc;
    }
    
    public ArrayList<CumplimientoRuta> getLstCumplimientoRutaXConductor() {
        return lst_crcd;
    }
    
    public ArrayList<PlanillaDespacho> getLstConsolidadoDespacho() {
        return lst_cdph;
    }
    
    public ArrayList<PuntoNoRegistrado> getLstPuntoNoRegistrado() {
        return lst_pnr;
    }
    
    public ArrayList<PuntoNoRegistrado> getLstPuntoNoRegistradoXVehiculo() {
        return lst_pnrxv;
    }
    
    public ArrayList<PuntoNoRegistrado> getLstPuntoNoRegistradoXConductor() {
        return lst_pnrxc;
    }
    
    public ArrayList<LiquidacionConsulta> getLstConsultadeLiquidacion() {
        return lst_conlq;
    }
}
