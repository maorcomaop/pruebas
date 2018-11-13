/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.UsuarioPermisoEmpresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class UsuarioPermisoEmpresaBD {

    public static ArrayList<UsuarioPermisoEmpresa> selectEmpresasPermitidas (int idUsuario) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_usuario_permiso_empresa WHERE FK_USUARIO = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            
            ArrayList<UsuarioPermisoEmpresa> lstupe = new ArrayList<UsuarioPermisoEmpresa>();
            while (rs.next()) {
                UsuarioPermisoEmpresa upe = new UsuarioPermisoEmpresa();
                upe.setId(rs.getInt("PK_USUARIO_PERMISO_EMPRESA"));
                upe.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                upe.setIdUsuario(rs.getInt("FK_USUARIO"));
                upe.setEstado(rs.getInt("ESTADO"));
                
                lstupe.add(upe);
            }
            return lstupe;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    public static Empresa selectEmpresaPermitida (int idUsuario) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT e.NOMBRE, e.NIT, e.PK_EMPRESA FROM tbl_usuario_permiso_empresa as upe"
                        + " INNER JOIN tbl_empresa as e ON"
                        + " e.PK_EMPRESA = upe.FK_EMPRESA AND"
                        + " e.ESTADO = 1"
                        + " WHERE upe.FK_USUARIO = ? AND upe.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Empresa e = new Empresa();
                e.setNombre(rs.getString("NOMBRE"));
                e.setNit(rs.getString("NIT"));
                e.setId(rs.getInt("PK_EMPRESA"));
                return e;
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
}
