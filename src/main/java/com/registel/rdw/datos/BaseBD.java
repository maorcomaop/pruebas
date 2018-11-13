/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Base;
import com.registel.rdw.logica.Punto;
import com.registel.rdw.utils.Coordenadas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class BaseBD {
    
    // Verifica si punto-base existe en tabla tbl_base con respecto
    // a su identificador
    public static boolean exist (Base b) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT b.PK_BASE FROM tbl_base as b WHERE " +
                "b.IDENTIFICADOR_BASE = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, b.getCodigoBase());
            rs = ps.executeQuery();
            
            if (rs.next())
                return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }

    // Inserta nuevo punto-base en tabla tbl_base solo si es nueva,
    // de lo contrario arroja excepcion de que ya existe
    public static boolean insert (Base b) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "INSERT INTO tbl_base (NOMBRE, LATITUD, LONGITUD, CODIGO_BASE, IDENTIFICADOR_BASE, " + 
                "DIRECCION_LATITUD, DIRECCION_LONGITUD, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL, LATITUD_WEB, LONGITUD_WEB) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        if (exist(b)) {
            String msg = "* Registro de base ya existe.";
            throw new ExisteRegistroBD (msg);            
        } else {
            try {
                // Metodo toGMD convierte coordenada a grados minutos
                // (Formato regisdata)
                b.setLatitud(Coordenadas.toGMD(b.getLatitud()));
                b.setLongitud(Coordenadas.toGMD(b.getLongitud()));
                
                ps = con.prepareStatement(sql);
                ps.setString(1, b.getNombre());                
                ps.setString(2, b.getLatitud());
                ps.setString(3, b.getLongitud());
                ps.setInt(4, b.getCodigoBase());
                ps.setInt(5, b.getIdentificador());
                ps.setString(6, b.getDireccionLatitud());
                ps.setString(7, b.getDireccionLongitud());
                ps.setInt(8, b.getRadio());
                ps.setInt(9, b.getDireccion());
                ps.setInt(10, 1);
                ps.setInt(11, 1);
                
                ps.setString(12, b.getLatitudWeb());
                ps.setString(13, b.getLongitudWeb());

                ps.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.err.println(e); 
            } finally {
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            } return false;
        }
    }
        
    // Actualiza registro de punto-base existente en tabla tbl_base
    public static boolean update (Base b) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_base SET NOMBRE=?, LATITUD=?, LONGITUD=?, " +
                "DIRECCION_LATITUD=?, DIRECCION_LONGITUD=?, RADIO=?, DIRECCION=?, LATITUD_WEB=?, LONGITUD_WEB=? " +
                "WHERE IDENTIFICADOR_BASE=? AND ESTADO = 1";
        
        try {
            // Metodo toGMD convierte coordenada a grados minutos
            // (Formato regisdata)
            b.setLatitud(Coordenadas.toGMD(b.getLatitud()));
            b.setLongitud(Coordenadas.toGMD(b.getLongitud()));
            
            ps = con.prepareStatement(sql);
            ps.setString(1, b.getNombre());            
            ps.setString(2, b.getLatitud());
            ps.setString(3, b.getLongitud());            
            ps.setString(4, b.getDireccionLatitud());
            ps.setString(5, b.getDireccionLongitud());
            ps.setInt(6, b.getRadio());
            ps.setInt(7, b.getDireccion());
            
            ps.setString(8, b.getLatitudWeb());
            ps.setString(9, b.getLongitudWeb());
            
            ps.setInt(10, b.getIdentificador());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    public static boolean pointInUse(int idbase) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "select"
                    + "     rp.FK_BASE"
                    + " from tbl_ruta_punto as rp"
                    + " inner join tbl_ruta as r on"
                    + "     rp.FK_RUTA = r.PK_RUTA"
                    + " where r.ESTADO = 1 and"
                    + " rp.FK_BASE = ?"
                    + " limit 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idbase);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return true;
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Inactiva punto-base en tabla tbl_base a traves de identificador
    // (establece campo estado en 0)
    public static boolean remove (int idbase) throws PuntoRelacionadoBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        if (pointInUse(idbase)) {
            throw new PuntoRelacionadoBD();
        }
        
        String sql1 = "UPDATE tbl_base SET ESTADO = 0 WHERE PK_BASE = ?";
        //String sql2 = "UPDATE tbl_ruta_punto SET ESTADO = 0 WHERE FK_BASE = ?";
        
        try {
            con.setAutoCommit(false);
            ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, idbase);
            ps1.executeUpdate();
            
            //ps2 = con.prepareStatement(sql2);
            //ps2.setInt(1, idbase);
            //ps2.executeUpdate();
            
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ie) {
                System.err.println(ie);
            } 
        } finally {
            try { con.setAutoCommit(false); } 
            catch (SQLException e) { System.out.println(e); }
            UtilBD.closePreparedStatement(ps1);
            //UtilBD.closePreparedStatement(ps2);
            pila_con.liberarConexion(con);
        } return false;        
    }
    
    // Consulta listado de puntos-base activas (estado en 1) en tabla tbl_base
    public static ArrayList<Base> select () {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        
        String sql = "SELECT * FROM tbl_base b WHERE b.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Base> lstbase = new ArrayList<Base>();
            while (rs.next()) {
                Base b = new Base();
                b.setIdbase(rs.getInt("PK_BASE"));
                b.setNombre(rs.getString("NOMBRE"));
                b.setIdentificador(rs.getInt("IDENTIFICADOR_BASE"));
                b.setCodigoBase(rs.getInt("CODIGO_BASE"));
                b.setLatitud(rs.getString("LATITUD"));
                b.setLongitud(rs.getString("LONGITUD"));
                b.setDireccionLatitud(rs.getString("DIRECCION_LATITUD"));
                b.setDireccionLongitud(rs.getString("DIRECCION_LONGITUD"));
                b.setRadio(rs.getInt("RADIO"));
                b.setDireccion(rs.getInt("DIRECCION"));
                b.setEstado(rs.getInt("ESTADO"));                
                
                // Conversion a formato web GD (grados decimales)
                //b.setLatitud(Coordenadas.toGD(b.getLatitud(), b.getDireccionLatitud()));
                //b.setLongitud(Coordenadas.toGD(b.getLongitud(), b.getDireccionLongitud()));
                
                b.setLatitudWeb(rs.getString("LATITUD_WEB"));
                b.setLongitudWeb(rs.getString("LONGITUD_WEB"));
                
                lstbase.add(b);
            } return lstbase;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Consulta minima de identificador de puntos-base activas en tabla tbl_base
    public static ArrayList<Base> selectMin () {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_BASE, IDENTIFICADOR_BASE FROM tbl_base WHERE ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Base> lst = new ArrayList<Base>();
            while (rs.next()) {
                Base b = new Base();
                b.setIdbase(rs.getInt("PK_BASE"));
                b.setIdentificador(rs.getInt("IDENTIFICADOR_BASE"));
                String etiqueta = "p" + b.getIdentificador();
                b.setEtiquetaBase(etiqueta);
                
                lst.add(b);
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
    
    // Inserta bloque de listado de puntos-base formateado en cadena sql
    public static boolean insertBlock (String sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        
        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta puntos-base cuya coordenada web no se encuentra establecida,
    // realiza conversion (grados decimales) con respecto a coordenada 
    // normal establecida (campos latitud y longitud) en tabla tbl_base
    public static ArrayList<Base> selectTranslateCoord() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_BASE, LATITUD, LONGITUD, DIRECCION_LONGITUD, DIRECCION_LATITUD FROM tbl_base WHERE"
                + " LATITUD_WEB IS NULL OR LONGITUD_WEB IS NULL AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Base> lst = new ArrayList<Base>();
            while (rs.next()) {
                Base b = new Base();
                b.setIdbase(rs.getInt("PK_BASE"));
                b.setLatitud(rs.getString("LATITUD"));
                b.setLongitud(rs.getString("LONGITUD"));
                b.setDireccionLatitud(rs.getString("DIRECCION_LATITUD"));
                b.setDireccionLongitud(rs.getString("DIRECCION_LONGITUD"));
                
                // Metodo toGD convierte coordenada a grados decimales
                b.setLatitud(Coordenadas.toGD(b.getLatitud(), b.getDireccionLatitud()));
                b.setLongitud(Coordenadas.toGD(b.getLongitud(), b.getDireccionLongitud()));
                
                b.setLatitudWeb(b.getLatitud());
                b.setLongitudWeb(b.getLongitud());
                
                lst.add(b);
            }
            return lst;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Actualiza valor de coordenadas web para un listado de puntos-base
    // especificado en tabla tbl_base
    public static boolean updateTranslateCoord (ArrayList<Base> lst) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
               
        try {
            con.setAutoCommit(false);
            for (Base b : lst) {
                String sql = "UPDATE tbl_base SET"
                            + " LATITUD_WEB = " + b.getLatitudWeb() + ","
                            + " LONGITUD_WEB = " + b.getLongitudWeb()
                            + " WHERE PK_BASE = " + b.getIdbase();
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
            }
            con.commit();
            return true;
            
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
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }    
}
