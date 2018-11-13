/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.SelectC;
import com.registel.rdw.logica.Tarifa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class TarifaBD {
    
    // Consulta si determinado valor de tarifa se encuentra registrado.
    public static boolean exist (String valor) {
        SelectBD select = new SelectBD(SelectC.LST_TARIFA.ordinal());
        ArrayList<Tarifa> lst = select.getLsttarifa();
        
        String valor_alm;
        for (Tarifa t : lst) {
            valor_alm = "" + t.getValor();
            if (valor_alm.compareTo(valor) == 0) {
                return true;
            }
        }
        return false;
    }
    
    // Inserta nuevo registro de tarifa.
    public static boolean insert (Tarifa t) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "INSERT INTO tbl_tarifa_fija (NOMBRE_TARIFA, VALOR_TARIFA, TARIFA_ACTIVA, ESTADO, MODIFICACION_LOCAL) "
                + "VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setDouble(2, t.getValor());
            ps.setInt(3, 1);
            ps.setInt(4, 1);
            ps.setInt(5, 1);
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta registro de tarifa a partir de identificador.
    public static Tarifa get (String idTarifa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_tarifa_fija WHERE PK_TARIFA_FIJA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idTarifa));
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Tarifa t = new Tarifa();
                t.setId(rs.getInt("PK_TARIFA_FIJA"));
                t.setNombre(rs.getString("NOMBRE_TARIFA"));
                t.setValor(rs.getDouble("VALOR_TARIFA"));                
                t.setActiva(rs.getInt("TARIFA_ACTIVA"));
                t.setEstado(rs.getInt("ESTADO"));
                
                return t;
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
    
    // Actualiza valores de tarifa especificada.
    public static boolean update (Tarifa t) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_tarifa_fija SET NOMBRE_TARIFA = ?, VALOR_TARIFA = ? "
                + "WHERE PK_TARIFA_FIJA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setDouble(2, t.getValor());
            ps.setInt(3, t.getId());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
        
    // Actualiza campo estado activa/inactiva 1/0 de tarifa especificada.
    public static boolean changeState (String idTarifa, String estado) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_tarifa_fija SET ESTADO = ? WHERE PK_TARIFA_FIJA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(estado));
            ps.setInt(2, Integer.parseInt(idTarifa));            
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Actualiza campo tarifa activa 1/0 de tarifa especificada.
    public static boolean changeActive (String idTarifa, String activar) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_tarifa_fija SET TARIFA_ACTIVA = ? WHERE PK_TARIFA_FIJA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(activar));
            ps.setInt(2, Integer.parseInt(idTarifa));
            
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
}
