/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Entorno;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.Restriction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lider_desarrollador
 */
public class ConexionExterna {

    private Connection con = null;
    private static Entorno entorno = null;
    
    public static void establecerEntorno(Entorno e) {
        entorno = e;
    }
    
    // Establece host a conectarse segun propiedad almacenada en
    // el entorno, si no se encuetra se usa el host local
    public String establecerHost() {
        if (entorno != null) {
            String host = entorno.obtenerPropiedad(Constant.HOST_GPS);
            if (Restriction.isHost(host))
                return host;
        }
        return "localhost";
    }
    
    // Establece base de datos a conectarse segun propiedad almacenada en
    // el entorno, si no se encuentra no se establece
    public String establecerBD() {
        if (entorno != null) {
            String bd = entorno.obtenerPropiedad(Constant.BD_GPS);
            if (Restriction.isBD(bd))
                return bd;
        }
        return "";
    }

    // Establece conexion a base de datos segun propiedades almacenadas en el entorno
    // (Conexion conservada en variable global)
    public Connection conectar()
            throws SQLException, ClassNotFoundException {

        String host = establecerHost();
        String bd   = establecerBD();        
        
        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager
                        .getConnection("jdbc:mysql://" + host + ":3306/" + bd,                        
                                       "root", "diseno&desarrollo");
            } catch (SQLException e) {
                throw new SQLException(e);
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException(e.getMessage());
            }
        }
        return con;
    }
    
    // Consulta estado de conexion
    public boolean estado_conexion() {
        try {
            Connection con = conectar();
            if (con != null) {
                con.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } return false;               
    }
    
    // Establece conexion a base de datos de validacion
    public Connection conectar_validacion()
            throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager
                    .getConnection("jdbc:mysql://74.208.79.242:3306/bd_validacion",
                            "soporte_registel", "soporte_registel");
            return con;

        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }

    // Finaliza conexion con base de datos de validacion
    public void desconectar_validacion(Connection con) {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    // Finaliza conexion con base de datos global
    public void desconectar() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
