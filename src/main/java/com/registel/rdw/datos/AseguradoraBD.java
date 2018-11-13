/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Aseguradora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Richard Mejia
 * 
 * @date 13/07/2018
 */
public class AseguradoraBD {
    
    private static final String TABLA = "tbl_aseguradora";
    private static final String PK_ASEGURADORA = "PK_ASEGURADORA";
    private static final String NOMBRE = "NOMBRE";
    private static final String TELEFONO = "TELEFONO";
    private static final String PAGINA_WEB = "PAGINA_WEB";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final int VALOR_POSITIVO = 1;
    
    
    public static boolean insert(Aseguradora aseguradora) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%s, %s, %s, %s, %s) ", NOMBRE, TELEFONO, PAGINA_WEB, ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, aseguradora.getNombre());
            ps.setString(2, aseguradora.getTelefono());
            ps.setString(3, aseguradora.getPaginaWeb());
            ps.setInt(4, aseguradora.getEstado());
            ps.setInt(5, VALOR_POSITIVO);

            int pk = ps.executeUpdate();
            connection.commit();

            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlEx) {
            System.err.print(sqlEx);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    System.err.print(ie);
                }
            }
        } catch (Exception ex) {
            System.err.print(ex);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    System.err.print(ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                System.err.print(sqlEx);
            }

            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return false;
    }
    
    public static boolean update(Aseguradora aseguradora) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ? ", 
                NOMBRE, TELEFONO, PAGINA_WEB, ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_ASEGURADORA));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, aseguradora.getNombre());
            ps.setString(2, aseguradora.getTelefono());
            ps.setString(3, aseguradora.getPaginaWeb());
            ps.setInt(4, aseguradora.getEstado());
            ps.setInt(5, VALOR_POSITIVO);
            ps.setLong(6, aseguradora.getId());
            int pk = ps.executeUpdate();
            connection.commit();
            
            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch(SQLException sqlEx) {
            System.err.print(sqlEx);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } catch(Exception ex) {
            System.err.print(ex);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                System.err.print(sqlEx);
            }
            
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }
        
        return false;
    }
    
    public static boolean updateEstado(Aseguradora aseguradora) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_ASEGURADORA));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, aseguradora.getEstado());
            ps.setInt(2, VALOR_POSITIVO);
            ps.setLong(3, aseguradora.getId());
            int pk = ps.executeUpdate();
            connection.commit();
            
            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch(SQLException sqlEx) {
            System.err.print(sqlEx);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } catch(Exception ex) {
            System.err.print(ex);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                System.err.print(sqlEx);
            }
            
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }
        
        return false;
    }

    public static Aseguradora selectByOne(Aseguradora aseguradora) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Aseguradora aseguradoraEncontrada;
        
        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT * FROM %s WHERE %s = ?", TABLA, PK_ASEGURADORA));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, aseguradora.getId());
            rs = ps.executeQuery();
            aseguradoraEncontrada = new Aseguradora();            
            
            if (rs.next()) {
                aseguradoraEncontrada.setId(rs.getLong(PK_ASEGURADORA));
                aseguradoraEncontrada.setNombre(rs.getString(NOMBRE));
                aseguradoraEncontrada.setTelefono(rs.getString(TELEFONO));
                aseguradoraEncontrada.setPaginaWeb(rs.getString(PAGINA_WEB));
                aseguradoraEncontrada.setEstado(rs.getInt(ESTADO));
            }
            
            return aseguradoraEncontrada;
        } catch(SQLException sqlEx) {
            System.err.print(sqlEx);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } catch(Exception ex) {
            System.err.print(ex);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                System.err.print(sqlEx);
            }
            
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }
        
        return null;
    }
    
}
