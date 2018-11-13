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
import com.registel.rdw.utils.Restriction;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class EmpresaBD {

    // Verifica si una empresa especifica existe (identificada por campo nit)
    public static boolean exist(Empresa e) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT e.PK_EMPRESA FROM tbl_empresa as e WHERE e.NIT = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setString(1, e.getNit());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Inserta una empresa nueva, verificando previamente que no exista
    public static boolean insert(Empresa e) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "INSERT INTO tbl_empresa "
                + "(FK_CIUDAD, NOMBRE, NIT, FK_MONEDA, FK_ZONAHORARIA, ESTADO, MODIFICACION_LOCAL) "
                + "VALUES (?,?,?,?,?,?,?)";

        if (exist(e)) {
            String msg = "* Registro de empresa ya existe.";
            throw new ExisteRegistroBD(msg);
        } else {
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, e.getIdciudad());
                ps.setString(2, e.getNombre());
                ps.setString(3, e.getNit());
                ps.setInt(4, e.getIdmoneda());
                ps.setInt(5, e.getIdzonahoraria());
                ps.setInt(6, 1);
                ps.setInt(7, 1);

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
    }

    // Actualiza valores de una empresa en especifico
    public static boolean update(Empresa e) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_empresa SET "
                + "NOMBRE=?, NIT=?, FK_CIUDAD=?, FK_MONEDA=?, FK_ZONAHORARIA=? WHERE PK_EMPRESA=? AND ESTADO=1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getNit());
            ps.setInt(3, e.getIdciudad());
            ps.setInt(4, e.getIdmoneda());
            ps.setInt(5, e.getIdzonahoraria());
            ps.setInt(6, e.getId());
            //ps.setString(6, a_nit);

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

    // Consulta empresa especifica por identificador asociado en su insercion
    public static Empresa getById(int id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.*, moneda.SIMBOLO as SIMBOLO_MONEDA ")
                .append("FROM tbl_empresa as e ")
                .append("INNER JOIN tbl_moneda moneda ON ")
                .append("e.FK_MONEDA = moneda.PK_MONEDA ")
                .append("WHERE e.PK_EMPRESA = ? AND ESTADO = 1");         

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Empresa e = new Empresa();                
                e.setIdciudad(rs.getInt("FK_CIUDAD"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setNit(rs.getString("NIT"));
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setLatitudWeb(rs.getString("LATITUD_WEB"));
                e.setLongitudWeb(rs.getString("LONGITUD_WEB"));
                e.setEstado(rs.getInt("ESTADO"));
                e.setIdmoneda(rs.getInt("FK_MONEDA"));
                e.setSimboloMoneda(rs.getString("SIMBOLO_MONEDA"));
                
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
    
    // Consulta y obtiene listado de empresas activas (estado en 1)
    public static ArrayList<Empresa> get() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_empresa AS e WHERE e.ESTADO = 1";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            ArrayList<Empresa> lst_emp = new ArrayList<Empresa>();
            while (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setNit(rs.getString("NIT"));
                e.setEstado(rs.getInt("ESTADO"));
                lst_emp.add(e);
            }
            return lst_emp;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    // Consulta empresa especifica
    public static Empresa get(int id) {
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
                + " WHERE e.PK_EMPRESA = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, id);
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

    // Activa o desactiva empresa especifica segun valor del
    // campo estado (1/0).
    public static boolean active(int id, int estado) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_empresa SET ESTADO=? WHERE PK_EMPRESA=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setInt(2, id);

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
    
    // Calcula ubicacion promedio de coordenadas (latitud y longitud)
    // segun los ultimos eventos encontrados de cada vehiculo 
    // perteneciente a una empresa en especifico
    public static Empresa ubicacionPromedio(Empresa emp) {
        if (emp != null) {
            String lat = emp.getLatitudWeb();
            String lon = emp.getLongitudWeb();
            
            if (lat == null || lat == "" ||
                lon == null || lon == "") {
            
                ConsultaExterna ce = new ConsultaExterna();
                ce.getUltimosEventos();
                ArrayList<DatosGPS> lst = ce.getLst_oevt();
                
                if (lst != null && lst.size() > 0) {
                    int size       = lst.size();
                    double promlat = .0;
                    double promlon = .0;
                    
                    int n = 0;
                    for (int i = 0; i < size; i++) {
                        DatosGPS item = lst.get(i);
                        double vlat   = Restriction.getRealNumber(item.getLatitud());
                        double vlon   = Restriction.getRealNumber(item.getLongitud());
                        
                        if ((vlat == 0.0 && vlon == 0.0) ||
                             vlat == Double.MIN_VALUE    ||
                             vlon == Double.MIN_VALUE)  {
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
                        
                        emp.setLatitudWeb ("" + promlat);
                        emp.setLongitudWeb("" + promlon);
                    }
                }
            }
        }
        
        return emp;
    }
}
