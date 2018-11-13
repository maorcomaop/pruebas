/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.controladores.Expire;
import com.registel.rdw.logica.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Login;
import com.registel.rdw.logica.Token;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.Restriction;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class LoginBD {
        
    // Activa estado conexion de usuario especificado
    public static boolean activarConexion(String user) {
        return cambiarEstadoConexion(user, 1);
    }
    
    // Desactiva estado conexion de usuario especificado
    public static boolean desactivarConexion(String user) {
        return cambiarEstadoConexion(user, 0);
    }
    
    // Actualiza campo estado conexion para un usuario especifico.
    // El valor de estado conexion puede ser 1-conectado y 0-desconectado.
    private static boolean cambiarEstadoConexion (String user, int estado) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_usuario SET ESTADO_CONEXION = ? WHERE"
                        + " LOGIN = ? AND ESTADO = 1";
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setString(2, user);
            
            if (ps.executeUpdate() >= 1) {
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
    
    // Consulta registro de usuario para nombre de usuario y contrasena
    // especifico sin tener en cuenta la empresa.
    public static Usuario login (String user, String pass) {
        return login(user, pass, "-2");
    }
    
    // Consulta registro de usuario para nombre de usuario, contrasena
    // y empresa en especifico.
    public static Usuario login (String user, String pass, String empresa) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usr = null;
        
        // Comprueba validez de campos de consulta
        if (!Restriction.isLogin(user) ||
            !Restriction.isPass(pass)) {
            return null;
        }
        
        String sql = "SELECT PK_USUARIO, NOMBRE, APELLIDO, CEDULA, FK_EMPRESA, FK_PERFIL, EMAIL, LOGIN, CONTRASENA, USUARIOBD"
                     + " FROM tbl_usuario WHERE LOGIN = ? AND CONTRASENA = sha2(?, 256)";
        
        // Determina si se relaciona o no el campo de empresa
        int idEmpresa = Restriction.getNumber(empresa);
        if (idEmpresa == -2) {
            sql += " AND ESTADO = 1";
        } else {            
            sql += " AND FK_EMPRESA = ? AND ESTADO = 1";
        }
           
        try {                        
            ps = con.prepareStatement(sql);            
            ps.setString(1, user);
            ps.setString(2, pass);           
            if (idEmpresa != -2) { 
                ps.setInt(3, idEmpresa); 
            }
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usr = new Usuario();
                usr.setId(rs.getInt("PK_USUARIO"));
                usr.setNombre(rs.getString("NOMBRE"));
                usr.setApellido(rs.getString("APELLIDO"));
                usr.setNumdoc(rs.getString("CEDULA"));
                usr.setIdperfil(rs.getInt("FK_PERFIL"));
                usr.setIdempresa(rs.getInt("FK_EMPRESA"));
                usr.setEmail(rs.getString("EMAIL"));
                usr.setLogin(rs.getString("LOGIN"));
                //usr.setPass(rs.getString("CONTRASENA"));
                usr.setUsuariobd(rs.getString("USUARIOBD"));
                
                Empresa emp = EmpresaBD.getById(idEmpresa);
                if (emp != null) {
                    usr.setNombreEmpresa(emp.getNombre());
                    usr.setNitEmpresa(emp.getNit());
                    usr.setEnterprice(emp);
                }                
            }
            return usr;
            
        } catch (SQLException e) {
            System.err.println(e);            
        } finally { 
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Actualiza registro de usuario (campos token, expire token) 
    // asociado a un correo para permitir la restauracion de su contrasena.
    public static boolean setRestore (String email, String token) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        long datetoken = Calendar.getInstance().getTimeInMillis();        
        
        // Verifica validez de campos de consulta
        if (!Restriction.isEmail(email) ||
            !Restriction.isToken(token)) {
            return false;
        }
        
        String sql = "UPDATE tbl_usuario SET TOKEN=?, EXPIRE_TOKEN=? " +
                     "WHERE EMAIL=? AND ESTADO=1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, token);
            ps.setLong(2, datetoken);
            ps.setString(3, email);
            
            if (ps.executeUpdate() == 1)            
                return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {            
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Actualiza registro de usuario asociado a un token - resultado
    // obtenido al hacer uso de setRestore - para cambiar contrasena.
    public static boolean makeRestore (String token, String pass) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        if (!Restriction.isToken(token) ||
            !Restriction.isPass(pass)) {
            return false;
        }
        
        String sql = "UPDATE tbl_usuario SET CONTRASENA=sha2(?, 256), TOKEN=NULL, EXPIRE_TOKEN=0 WHERE TOKEN=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, token);
            
            if (ps.executeUpdate() == 1)
                return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {            
            //UtilBD.closeStatement(ps);            
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;                     
    }
    
    // Consulta informacion de usuario a partir de token asignado - a traves
    // de setRestore -.
    public static Token getRestoreData (String token) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;          
        ResultSet rs = null;
        
        // Comprueba validez de campos de consulta
        if (!Restriction.isToken(token)) {
            return null;
        }
        
        String sql = "SELECT TOKEN, EXPIRE_TOKEN FROM tbl_usuario WHERE TOKEN=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, token);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Token t = new Token();
                t.setToken(rs.getString("TOKEN"));
                t.setDate(rs.getLong("EXPIRE_TOKEN"));
                
                return t;
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {            
            //UtilBD.closeStatement(ps);            
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Actualiza contrasena asociada a usuario identificado por su
    // numero de documento.
    public static boolean changePass (String numdoc, String oldpass, String newpass) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        // Comprueba validez de campos de consulta
        if (!Restriction.isNumber(numdoc) ||
            !Restriction.isPass(oldpass)  ||
            !Restriction.isPass(newpass)) {
            return false;
        }
        
        String sql = "UPDATE tbl_usuario SET CONTRASENA=sha2(?,256) WHERE ESTADO=1 AND CEDULA=? AND CONTRASENA=sha2(?,256)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, newpass);
            ps.setString(2, numdoc);
            ps.setString(3, oldpass);
            
            if (ps.executeUpdate() == 1)
                return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
}
