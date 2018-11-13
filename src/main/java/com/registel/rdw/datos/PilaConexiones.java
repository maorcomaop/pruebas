/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.controladores.WebContext;
import java.sql.*;
import javax.sql.DataSource;
import javax.naming.*;

/**
 *
 * @author lider_desarrollador
 */
public class PilaConexiones {
    
    private static PilaConexiones pila_con = null;
    private DataSource dataSource = null;
    
    public synchronized static PilaConexiones obtenerInstancia () {
        if (pila_con == null)
            pila_con = new PilaConexiones();
        return pila_con;
    }
    
    public PilaConexiones () {
       try {
           InitialContext initContext = new InitialContext();
           dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/rdw");
       } catch (NamingException e) {
           System.err.println(e);
       }
    }
    
    public Connection obtenerConexion () {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println (e);
            return null;
        }
    }
    
    public Connection obtenerConexionNueva () {
        
        Connection con = null;
        try {
            
            // Obtenemos valores de configuracion de base de datos
            String user = WebContext.getParam("user");
            String pass = WebContext.getParam("pass");
            String url  = WebContext.getParam("url");
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager
                    .getConnection(url, user, pass);
            return con;
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        return con;
    }
    
    public void liberarConexion (Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println (e);
            }
        }
    }
}
