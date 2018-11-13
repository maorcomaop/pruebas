/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.PlanillaDespachoMin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class DespachoManualBD {

    private static SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");

    // Consulta numero de planilla asignado a un vehiculo 
    // en una fecha y ruta. De no encontrarse el vehiculo
    // se retorna el siguiente numero de planilla de la ruta.
    public static int numero_planilla(Date fecha, int id_ruta, String placa_movil) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sfecha = ffmt.format(fecha);

        String sql = "SELECT"
                + " PLACA,"
                + " NUMERO_PLANILLA,"
                + " FK_RUTA"
                + " FROM tbl_planilla_despacho WHERE"
                + " FECHA = ?"
                + " GROUP BY PLACA, FK_RUTA";

        int numero_planilla = 0;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sfecha);
            rs = ps.executeQuery();

            while (rs.next()) {
                String placa_movil_pd = rs.getString("PLACA");
                int numero_planilla_pd = rs.getInt("NUMERO_PLANILLA");
                int id_ruta_pd = rs.getInt("FK_RUTA");
                if (placa_movil.compareTo(placa_movil_pd) == 0
                    && id_ruta == id_ruta_pd) {
                    return numero_planilla_pd;
                }
                if (numero_planilla_pd > numero_planilla) {
                    numero_planilla = numero_planilla_pd;
                }
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }

        return numero_planilla + 1;
    }

    // Consulta ultimo numero de vuelta de un vehiculo en una ruta y
    // fecha en especifico.
    public static int ultimo_numero_vuelta(int id_ruta, Date fecha, String placa_movil) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sfecha = ffmt.format(fecha);

        String sql = "SELECT max(NUMERO_VUELTA) as NUMERO_VUELTA FROM tbl_planilla_despacho WHERE"
                + " FK_RUTA = ? AND"
                + " FECHA = ? AND"
                + " PLACA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_ruta);
            ps.setString(2, sfecha);
            ps.setString(3, placa_movil);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("NUMERO_VUELTA");
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }

        return 0;
    }
    
    // Consulta minima de registro de planilla con punto-salida para el intervalo despacho
    // especificado.
    public static PlanillaDespachoMin obtener_planilla(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " pd.FK_INTERVALO_DESPACHO,"
                + " pd.NUMERO_PLANILLA,"
                + " pd.NUMERO_VUELTA,"
                + " pd.FECHA,"
                + " pd.HORA_PLANIFICADA,"
                + " pd.PLACA,"
                + " pd.FK_RUTA,"
                + " (SELECT r.NOMBRE FROM tbl_ruta AS r"
                + "  WHERE r.PK_RUTA = pd.FK_RUTA AND r.ESTADO = 1) AS NOMBRE_RUTA"
                + " FROM tbl_planilla_despacho AS pd WHERE"
                + " pd.FK_INTERVALO_DESPACHO = ? AND"
                + " pd.TIPO_PUNTO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            rs = ps.executeQuery();

            if (rs.next()) {
                PlanillaDespachoMin pd = new PlanillaDespachoMin();

                pd.setIdIntervalo(rs.getInt("FK_INTERVALO_DESPACHO"));
                pd.setNumeroPlanilla(rs.getInt("NUMERO_PLANILLA"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setFecha(rs.getDate("FECHA"));
                pd.setHoraInicio(rs.getTime("HORA_PLANIFICADA"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setIdRuta(rs.getInt("FK_RUTA"));
                pd.setNombreRuta(rs.getString("NOMBRE_RUTA"));

                return pd;
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

    // Consulta si registros de planilla despacho de un intervalo
    // contiene vueltas con salida desde punto retorno
    public static boolean inicio_desde_punto_retorno(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT count(PK_PLANILLA_DESPACHO) AS REG_PREV_PUNTO_RETORNO"
                + " FROM tbl_planilla_despacho"
                + " WHERE FK_INTERVALO_DESPACHO = ? AND ESTADO_DESPACHO = -1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            rs = ps.executeQuery();

            if (rs.next()) {
                int nreg = rs.getInt("REG_PREV_PUNTO_RETORNO");
                if (nreg >= 1) {
                    return true;
                }
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

    // Consulta cantidad de registros planilla que seran actualizados
    // en caso de que exista una sustitucion de vehiculo, 
    // para un intervalo despacho definido
    public static int registros_sustitucion_vehiculo(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT count(FK_INTERVALO_DESPACHO) AS CANTIDAD_REGISTROS FROM tbl_planilla_despacho"
                + " WHERE FK_INTERVALO_DESPACHO = ? AND ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("CANTIDAD_REGISTROS");
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return -1;
    }

    // Consulta que sustituye registros de planilla con un nuevo vehiculo,
    // para un intervalo despacho definido
    public static boolean sustituir_vehiculo(int idIntervalo, String placa_movil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Consulta cantidad de registros a actualizar
        int nitems = registros_sustitucion_vehiculo(idIntervalo);

        String sql = "UPDATE tbl_planilla_despacho SET PLACA = ?"
                + " WHERE FK_INTERVALO_DESPACHO = ? AND ESTADO = 1";

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, placa_movil);
            ps.setInt(2, idIntervalo);
            int n = ps.executeUpdate();

            if (n == nitems) {

                String sql2 = "UPDATE tbl_intervalo_despacho SET CONF_RUTA = 0 , CONF_DESPACHO = 0 WHERE PK_INTERVALO_DESPACHO = ? AND ESTADO = 1";
                ps = con.prepareStatement(sql2);
                ps.setInt(1, idIntervalo);
                ps.executeUpdate();
                
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
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

}
