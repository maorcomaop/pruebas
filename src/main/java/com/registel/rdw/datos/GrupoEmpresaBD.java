/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.GrupoEmpresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lider_desarrollador
 */
public class GrupoEmpresaBD {
    
    // Inserta nuevo registro de grupo empresa,
    // que se relaciona determinada empresa con un grupo (identificado por
    // numero unico incremental) creado al momento del registro.
    public static boolean insert (GrupoEmpresa gemp) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "INSERT tbl_agrupacion (NOMBRE, FK_EMPRESA, APLICARTIEMPOS, ESTADO, MODIFICACION_LOCAL) "
                + "VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, gemp.getNombre());
            ps.setInt(2, gemp.getIdEmpresa());
            ps.setInt(3, 1);
            ps.setInt(4, 1);
            ps.setInt(5, 1);
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta grupo empresa especifico identificado por valor numerico unico
    // y se encuentre activo
    public static GrupoEmpresa get (String id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_agrupacion WHERE PK_AGRUPACION = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            rs = ps.executeQuery();
            
            if (rs.next()) {
                GrupoEmpresa gemp = new GrupoEmpresa();
                gemp.setId(rs.getInt("PK_AGRUPACION"));
                gemp.setNombre(rs.getString("NOMBRE"));
                gemp.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                gemp.setAplicarTiempos(rs.getInt("APLICARTIEMPOS"));
                gemp.setEstado(rs.getInt("ESTADO"));
                
                return gemp;
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
    
    // Actualiza campos de un grupo empresa especifico
    public static boolean update (GrupoEmpresa gemp) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_agrupacion SET NOMBRE = ?, FK_EMPRESA = ? "
                + "WHERE PK_AGRUPACION = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, gemp.getNombre());
            ps.setInt(2, gemp.getIdEmpresa());
            //ps.setInt(3, gemp.getAplicarTiempos());
            ps.setInt(3, gemp.getId());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Desactiva un grupo empresa especifico, para inhabilitar su uso
    // (asigna a campo estado el valor de 0)
    public static boolean remove (String id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_agrupacion SET ESTADO = 0 WHERE PK_AGRUPACION = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            
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
