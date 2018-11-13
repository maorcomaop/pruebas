/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.DatosGPS;
import com.registel.rdw.logica.DatosGPSMIN;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.TimeUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class DespachoControlBD {
    
    private static int MIN_COMPROBACION = 5;
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void establecer_tiempo_comprobacion(int tiempo_comprobacion) {
        MIN_COMPROBACION = tiempo_comprobacion;
    }
    
    // Construye instruccion SQL para consulta de registros planilla en una
    // fecha y tipo de generacion determinada. Si se consulta la totalidad
    // de los registros se incluyen tanto registros controlados como los que no.
    // Registros no controlados son aquellos cuyo ESTADO_DESPACHO = 0, controlados
    // presentan un valor > 0.
    private String sql_planilla(boolean totalidad_registros, int tipo_generacion) {
        
        String fecha_hoy = ffmt.format(new Date());
        String opr = (totalidad_registros) ? ">=" : "=";
        
        String sql = "SELECT * FROM tbl_planilla_despacho AS pd"
                        + " INNER JOIN tbl_intervalo_despacho AS idph ON"
                        + " idph.TIPO_GENERACION = " + tipo_generacion + " AND"
                        + " idph.PK_INTERVALO_DESPACHO = pd.FK_INTERVALO_DESPACHO"
                        + " WHERE"
                        + " pd.FECHA = '" + fecha_hoy + "' AND"                
                        + " pd.ESTADO_DESPACHO " + opr + " 0 AND"
                        + " pd.ESTADO = 1"
                        + " ORDER BY pd.PLACA ASC, pd.FECHA ASC, pd.HORA_PLANIFICADA ASC";
        
        return sql;
    }
    
    // Consulta todos los registros de planilla a pedido (despacho manual)
    public ArrayList<PlanillaDespacho> planilla_a_pedido() {
        String sql = sql_planilla(true, Constant.DPH_MANUAL);
        return planilla_registrada(sql);
    }
    
    // Consulta registros de planilla planificada no controlados
    public ArrayList<PlanillaDespacho> planilla_registrada_sin_procesar() {
        String sql = sql_planilla(false, Constant.DPH_PLANIFICADO);
        return planilla_registrada(sql);
    }
    
    // Consulta todos los registros de planilla planificada
    public ArrayList<PlanillaDespacho> planilla_registrada_completa() {
        String sql = sql_planilla(true, Constant.DPH_PLANIFICADO);
        return planilla_registrada(sql);
    }
    
    // Efectua consulta de registros de planilla (manual o planificada)
    private ArrayList<PlanillaDespacho> planilla_registrada(String sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;
        int pospto   = 1;
        
        System.out.println("TIEMPO_COMPROBACION " + MIN_COMPROBACION);
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            ArrayList<PlanillaDespacho> lst_pd = new ArrayList<PlanillaDespacho>();
            while (rs.next()) {
                PlanillaDespacho pd = new PlanillaDespacho();
                
                pd.setId(rs.getInt("PK_PLANILLA_DESPACHO"));
                pd.setIdIntervalo(rs.getInt("FK_INTERVALO_DESPACHO"));
                pd.setIdRuta(rs.getInt("FK_RUTA"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setFecha(rs.getDate("FECHA"));
                pd.setIntervaloLlegada(rs.getInt("INTERVALO_LLEGADA"));
                pd.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                pd.setHoraReal(rs.getTime("HORA_REAL"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setTipoPunto(rs.getInt("TIPO_PUNTO"));
                pd.setPunto(rs.getString("PUNTO"));
                pd.setHolguraPunto(rs.getInt("HOLGURA_PUNTO"));                
                pd.setEstadoDespacho(rs.getInt("ESTADO_DESPACHO"));
                // Establece hora de comprobacion y holgura a partir de hora planificada
                // y minutos establecidos
                pd.setHoraMin(TimeUtil.sumarMinutos(pd.getHoraPlanificada(), -MIN_COMPROBACION));
                pd.setHoraMax(TimeUtil.sumarMinutos(pd.getHoraPlanificada(), MIN_COMPROBACION));
                pd.setHoraHmin(TimeUtil.sumarMinutos(pd.getHoraPlanificada(), -pd.getHolguraPunto()));
                pd.setHoraHmax(TimeUtil.sumarMinutos(pd.getHoraPlanificada(), pd.getHolguraPunto()));
                
                if (pd.getTipoPunto() == 1) {
                    pospto = 1;
                    pd.setPosicionPunto(pospto);
                } else {
                    pospto += 1;
                    pd.setPosicionPunto(pospto);
                }
                
                lst_pd.add(pd);
            }            
            return lst_pd;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }    
    
    // Consulta registros gps para una fecha en especifico
    public ArrayList<DatosGPSMIN> datos_gps(Empresa emp) {
        ConexionExterna con_ext = new ConexionExterna();
        Connection con = null;
        
        String fecha_hoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String nombre_empresa = emp.getNombre().toLowerCase();
        String fecha_inicio = fecha_hoy + " 00:00:00";
        String fecha_fin    = fecha_hoy + " 23:59:59";
        
        String sql =
            "SELECT"
                + " fw.id,"
                + " date(fw.fecha_server) AS fecha_servidor,"
                + " date(fw.fecha_gps) AS fecha_gps,"
                + " time(fw.fecha_server) AS hora_servidor,"	
                + " time(fw.fecha_gps) AS hora_gps,"
                + " fw.msg,"
                + " fw.placa,"
                + " fw.nombre_flota"
                + " FROM tbl_forwarding_wtch as fw"
                + " WHERE"
                + " fw.fecha_gps between '" + fecha_inicio + "' AND '" + fecha_fin + "' AND"
                //+ " date(fw.fecha_gps) = '" + fecha_hoy + "' AND"
                //+ " lcase(fw.nombre_flota) LIKE '%" + nombre_empresa + "%' AND"
                + " lcase(fw.msg) LIKE '%punto de control%'"
                + " ORDER BY fw.placa asc, fw.fecha_gps asc";
        
        try {
            con = con_ext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            
            ArrayList<DatosGPSMIN> lst_gps = new ArrayList<DatosGPSMIN>();
            while (rs.next()) {
                DatosGPSMIN gps = new DatosGPSMIN();
                
                gps.setId(rs.getLong("id"));
                gps.setFechaServidor(rs.getDate("fecha_servidor"));
                gps.setFechaGPS(rs.getDate("fecha_gps"));                
                gps.setHoraServidor(rs.getTime("hora_servidor"));
                gps.setHoraGPS(rs.getTime("hora_gps"));
                gps.setMsg(rs.getString("msg"));
                gps.setPlaca(rs.getString("placa"));
                gps.setNombreFlota(rs.getString("nombre_flota"));
                
                lst_gps.add(gps);
            }
            rs.close();
            ps.close();
            return lst_gps;
            
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            con_ext.desconectar();
        } 
        return null;
    }
    
    // Consulta ultimo registro de hora conseguido por un vehiculo en una
    // fecha, ruta y numero de vuelta especifico
    public Time ultima_hora_registrada(String placa, String fecha, int ruta, int nvuelta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        placa = placa.toLowerCase();
        String sql = "SELECT HORA_REAL FROM tbl_planilla_despacho WHERE"
                            + " lower(PLACA) = ? AND"
                            + " FECHA = ? AND"            
                            + " FK_RUTA = ? AND"                            
                            + " NUMERO_VUELTA = ? AND"
                            + " HORA_REAL IS NOT NULL AND"
                            + " ESTADO = 1"
                            + " ORDER BY HORA_REAL DESC LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            ps.setString(2, fecha);
            ps.setInt(3, ruta);
            ps.setInt(4, nvuelta);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getTime("HORA_REAL");
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
    
    // Consulta ultimo registro de hora conseguido en un intervalo de despacho
    // (Usado especialmente para despacho manual, cuyo identificador de intervalo,
    // identifica exclusivamente la vuelta de un vehiculo en una fecha y ruta definida).
    public Time ultima_hora_registrada(int id_intervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT HORA_REAL FROM tbl_planilla_despacho WHERE"
                            + " FK_INTERVALO_DESPACHO = ? AND"
                            + " HORA_REAL IS NOT NULL AND"
                            + " ESTADO = 1"
                            + " ORDER BY HORA_REAL DESC LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_intervalo);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getTime("HORA_REAL");
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

    // Consulta si un registro de planilla despacho especifico (de un 
    // punto, vehiculo, fecha y ruta) ha sido controlado en cierta hora.
    public boolean punto_controlado (String punto, String placa, String fecha, int ruta, String hora) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        punto = punto.toLowerCase();
        placa = placa.toLowerCase();
        String sql = "SELECT PK_PLANILLA_DESPACHO FROM tbl_planilla_despacho"
                    + " WHERE lower(PUNTO) = ? AND"
                    + " lower(PLACA) = ? AND"
                    + " FECHA = ? AND"
                    + " FK_RUTA = ? AND"
                    + " HORA_REAL = ? AND"
                    + " ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, punto);
            ps.setString(2, placa);
            ps.setString(3, fecha);
            ps.setInt(4, ruta);
            ps.setString(5, hora);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        
        return false;
    }
    
    
    // Consulta si registro de planilla despacho especifico (de un punto, vehiculo y fecha)
    // ha sido controlado en cierta hora
    public boolean punto_controlado (String punto, String placa, String fecha, String hora) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        punto = punto.toLowerCase();
        placa = placa.toLowerCase();
        String sql = "SELECT PK_PLANILLA_DESPACHO FROM tbl_planilla_despacho"
                    + " WHERE lower(PUNTO) = ? AND"
                    + " lower(PLACA) = ? AND"
                    + " FECHA = ? AND"
                    + " HORA_REAL = ? AND"
                    + " ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, punto);
            ps.setString(2, placa);
            ps.setString(3, fecha);
            ps.setString(4, hora);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        
        return false;
    }
    
    // Consulta si registro de planilla, en este caso punto base-salida
    // de un vehiculo, en una fecha, ruta y vuelta ha sido controlado.
    public boolean base_salida_controlada (String placa, String fecha, int ruta, int nvuelta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        placa = placa.toLowerCase();
        String sql = "SELECT ESTADO_DESPACHO FROM tbl_planilla_despacho"
                    + " WHERE lower(PLACA) = ? AND"
                    + " FECHA = ? AND"
                    + " FK_RUTA = ? AND"
                    + " NUMERO_VUELTA = ? AND"
                    + " TIPO_PUNTO = 1 AND"
                    + " ESTADO = 1";
                    //+ " ORDER BY HORA_PLANIFICADA ASC LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            ps.setString(2, fecha);
            ps.setInt(3, ruta);
            ps.setInt(4, nvuelta);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                int estado_despacho = rs.getInt("ESTADO_DESPACHO");
                if (estado_despacho > 0)
                    return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta si registro planilla, en este caso punto base-salida
    // de un intervalo despacho ha sido controlado.
    // (Un intervalo despacho define una vuelta de un vehiculo en una fecha y
    // ruta especifica).
    public boolean base_salida_controlada (int id_intervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT ESTADO_DESPACHO FROM tbl_planilla_despacho WHERE"
                    + " FK_INTERVALO_DESPACHO = ? AND"
                    + " TIPO_PUNTO = 1 AND"
                    + " ESTADO = 1";
                    //+ " ORDER BY HORA_PLANIFICADA ASC LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_intervalo);
                        
            rs = ps.executeQuery();
            if (rs.next()) {
                int estado_despacho = rs.getInt("ESTADO_DESPACHO");
                if (estado_despacho > 0)
                    return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta registro de ultima hora controlada de base-entrada
    // para un vehiculo en una fecha especifico.
    public Time ultima_base_entrada_controlada(String fecha, String placa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        placa = placa.toLowerCase();
        String sql = "SELECT HORA_REAL FROM tbl_planilla_despacho WHERE"
                        + " FECHA = ? AND"
                        + " lower(PLACA) = ? AND"
                        + " HORA_REAL IS NOT NULL AND"
                        + " TIPO_PUNTO = 3 AND"
                        + " ESTADO = 1"
                        + " ORDER BY HORA_REAL DESC LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, placa);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getTime("HORA_REAL");
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
    
    // Actualiza valores de planilla despacho cuando ha sido controlada
    public boolean actualizar_planilla(ArrayList<PlanillaDespacho> lst_pd) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        int n = 0;
        try {
            con.setAutoCommit(false);
            
            for (PlanillaDespacho pd : lst_pd) {
                String sql = sql_actualizar_planilla(pd);
                ps = con.prepareStatement(sql);
                ps.setTime(1, pd.getHoraReal());
                ps.setLong(2, pd.getDiferencia());
                ps.setInt(3, pd.getEstadoDespacho());
                ps.setInt(4, pd.getId());                
                n += ps.executeUpdate();
            }
            
            if (n == lst_pd.size()) {
                con.commit();
                return true;
            } else {
                con.rollback();
            }
            
        } catch (SQLException e) {
            System.err.println(e);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) { System.err.println(ie); }
            }
        } finally {
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }        
    
    // Construye instruccion SQL para actualizar planilla de despacho
    public String sql_actualizar_planilla(PlanillaDespacho pd) {
        
        String sql = "UPDATE tbl_planilla_despacho SET"
                        + " HORA_REAL = ?,"
                        + " DIFERENCIA = ?,"
                        + " ESTADO_DESPACHO = ?"
                        + " WHERE PK_PLANILLA_DESPACHO = ?";
        return sql;
    }
}
