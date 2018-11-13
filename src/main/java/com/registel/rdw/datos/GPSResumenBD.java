/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.DatosGPS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.GPSResumen;
import com.registel.rdw.utils.Restriction;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class GPSResumenBD {

    public static ArrayList<GPSResumen> selectGPSResumenByGPS(String fkGPSIds, String lastCheckDate) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sqlAux = "";
        if (lastCheckDate != null) {
            sqlAux = " AND FECHA_MODIFICACION >= '" + lastCheckDate + "'";
        }

        String sql = "SELECT PK_GPS_RESUMEN,"
                + "FK_GPS,"
                + "IF(ESTADO_CONEXION  =  1 AND (DATE_ADD(FECHA_ULTIMO_REPORTE, INTERVAL 1 HOUR) < NOW()), 0 , ESTADO_CONEXION) as ESTADO_CONEXION,"
                + "FECHA_ULTIMO_REPORTE,"
                + "FECHA_DESCONEXION,"
                + "FECHA_MODIFICACION,"
                + "ESTADO "
                + "FROM tbl_gps_resumen WHERE FK_GPS IN (" + fkGPSIds + ")" + sqlAux + " AND ESTADO = 1";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            ArrayList<GPSResumen> lst = new ArrayList<>();
            while (rs.next()) {
                GPSResumen e = new GPSResumen();
                e.setEstadoConexion(rs.getInt("ESTADO_CONEXION"));
                e.setFechaDesconexion(rs.getTimestamp("FECHA_DESCONEXION"));
                e.setFechaUltimoReporte(rs.getTimestamp("FECHA_ULTIMO_REPORTE"));
                e.setFkGPS(rs.getString("FK_GPS"));
                e.setPkGPSResumen(rs.getInt("PK_GPS_RESUMEN"));

                lst.add(e);
            }
            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static Empresa get(String nit) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " e.PK_EMPRESA,"
                + " e.NOMBRE,"
                + " e.NIT,"
                + " e.ESTADO,"
                + " e.FK_MONEDA,"
                + " e.FK_ZONAHORARIA,"
                + " p.NOMBRE as PAIS,"
                + " d.NOMBRE as DPTO,"
                + " c.NOMBRE as CIUDAD,"
                + " p.PK_PAIS as ID_PAIS,"
                + " d.PK_DEPARTAMENTO as ID_DPTO,"
                + " c.PK_CIUDAD as ID_CIUDAD"
                + " FROM tbl_empresa as e"
                + " INNER JOIN tbl_ciudad as c"
                + "     on e.FK_CIUDAD = c.PK_CIUDAD"
                + " INNER JOIN tbl_departamento as d"
                + "     on c.FK_DEPARTAMENTO = d.PK_DEPARTAMENTO"
                + " INNER JOIN tbl_pais as p"
                + "     on d.FK_PAIS = p.PK_PAIS"
                + " WHERE e.NIT = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setString(1, nit);
            rs = ps.executeQuery();

            if (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setNit(rs.getString("NIT"));
                e.setIdmoneda(rs.getInt("FK_MONEDA"));
                e.setIdzonahoraria(rs.getInt("FK_ZONAHORARIA"));
                e.setPais(rs.getString("PAIS"));
                e.setDpto(rs.getString("DPTO"));
                e.setCiudad(rs.getString("CIUDAD"));
                e.setIdpais(rs.getInt("ID_PAIS"));
                e.setIddpto(rs.getInt("ID_DPTO"));
                e.setIdciudad(rs.getInt("ID_CIUDAD"));
                //e.setEstado(rs.getInt("ESTADO"));

                return e;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    /*ADICIONADO POR JAIR VIDAL 12-03-2018*/
 /*METODO QUE PERMITE ENCONTRAR LA EMPRESA ACTIVA EN LA BASE DE DATOS.*/
    public static Empresa getActive(Empresa em) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " e.PK_EMPRESA,"
                + " e.NOMBRE,"
                + " e.NIT,"
                + " e.ESTADO,"
                + " e.FK_MONEDA,"
                + " e.FK_ZONAHORARIA,"
                + " p.NOMBRE as PAIS,"
                + " d.NOMBRE as DPTO,"
                + " c.NOMBRE as CIUDAD,"
                + " p.PK_PAIS as ID_PAIS,"
                + " d.PK_DEPARTAMENTO as ID_DPTO,"
                + " c.PK_CIUDAD as ID_CIUDAD"
                + " FROM tbl_empresa as e"
                + " INNER JOIN tbl_ciudad as c"
                + "     on e.FK_CIUDAD = c.PK_CIUDAD"
                + " INNER JOIN tbl_departamento as d"
                + "     on c.FK_DEPARTAMENTO = d.PK_DEPARTAMENTO"
                + " INNER JOIN tbl_pais as p"
                + "     on d.FK_PAIS = p.PK_PAIS"
                + " WHERE e.ESTADO = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, em.getEstado());
            rs = ps.executeQuery();

            if (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setNit(rs.getString("NIT"));
                e.setIdmoneda(rs.getInt("FK_MONEDA"));
                e.setIdzonahoraria(rs.getInt("FK_ZONAHORARIA"));
                e.setPais(rs.getString("PAIS"));
                e.setDpto(rs.getString("DPTO"));
                e.setCiudad(rs.getString("CIUDAD"));
                e.setIdpais(rs.getInt("ID_PAIS"));
                e.setIddpto(rs.getInt("ID_DPTO"));
                e.setIdciudad(rs.getInt("ID_CIUDAD"));
                //e.setEstado(rs.getInt("ESTADO"));

                return e;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static boolean active(String nit, int estado) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_empresa SET ESTADO=? WHERE NIT=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setString(2, nit);

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    public static Empresa ubicacionPromedio(Empresa emp) {
        if (emp != null) {
            String lat = emp.getLatitudWeb();
            String lon = emp.getLongitudWeb();

            if (lat == null || lat == ""
                    || lon == null || lon == "") {

                ConsultaExterna ce = new ConsultaExterna();
                ce.getUltimosEventos();
                ArrayList<DatosGPS> lst = ce.getLst_oevt();

                if (lst != null && lst.size() > 0) {
                    int size = lst.size();
                    double promlat = .0;
                    double promlon = .0;

                    int n = 0;
                    for (int i = 0; i < size; i++) {
                        DatosGPS item = lst.get(i);
                        double vlat = Restriction.getRealNumber(item.getLatitud());
                        double vlon = Restriction.getRealNumber(item.getLongitud());

                        if ((vlat == 0.0 && vlon == 0.0)
                                || vlat == Double.MIN_VALUE
                                || vlon == Double.MIN_VALUE) {
                            continue;
                        } else {
                            n++;
                            promlat += vlat;
                            promlon += vlon;
                        }
                    }

                    if (promlat != 0.0 && promlon != 0.0 && n > 0) {
                        promlat = promlat / n;
                        promlon = promlon / n;

                        emp.setLatitudWeb("" + promlat);
                        emp.setLongitudWeb("" + promlon);
                    }
                }
            }
        }

        return emp;
    }
}
