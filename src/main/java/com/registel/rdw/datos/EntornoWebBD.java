/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.EntornoWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdk.nashorn.internal.objects.NativeRegExp;

/**
 *
 * @author lider_desarrollador
 */
public class EntornoWebBD {
    
    // Consulta primera configuracion de entorno web almacenado
    public static EntornoWeb get() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_entorno_web"
                    + " ORDER BY PK_ENTORNO_WEB ASC LIMIT 1";
        
        try {            
            ps = con.prepareStatement(sql);            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                EntornoWeb ew = new EntornoWeb();
                ew.setId(rs.getInt("PK_ENTORNO_WEB"));
                ew.setDominio(rs.getString("DOMINIO"));
                ew.setEmail(rs.getString("EMAIL"));
                ew.setPass(rs.getString("PASSWORD"));
                return ew;
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
    
    // Inserta configuracion de entorno web
    public static boolean insert(EntornoWeb ew) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "INSERT INTO tbl_entorno_web (DOMINIO, EMAIL, PASSWORD)"
                + "VALUES (?,?,?)";
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, ew.getDominio());
            ps.setString(2, ew.getEmail());
            ps.setString(3, ew.getPass());
            int rs = ps.executeUpdate();
            
            if (rs == 1) {
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
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Actualiza valores de entorno web especifico    
    public static boolean update(EntornoWeb ew) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_entorno_web SET"
                        + "DOMINIO = ?,"
                        + "EMAIL = ?,"
                        + "PASSWORD = ?"
                        + "WHERE PK_ENTORNO_WEB = ?";
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, ew.getDominio());
            ps.setString(2, ew.getEmail());
            ps.setString(3, ew.getPass());
            int rs = ps.executeUpdate();
            
            if (rs == 1) {
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
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
}
