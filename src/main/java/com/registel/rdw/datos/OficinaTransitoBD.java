/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.OficinaTransito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Richard Mejia
 *
 * @date 11/07/2018
 */
public class OficinaTransitoBD {

    private static final String TABLA = "tbl_oficina_transito";
    private static final String PK_OFICINA_TRANSITO = "PK_OFICINA_TRANSITO";
    private static final String NOMBRE = "NOMBRE";
    private static final String TELEFONO = "TELEFONO";
    private static final String DIRECCION = "DIRECCION";
    private static final String CORREO = "CORREO_ELECTRONICO";
    private static final String FK_CIUDAD = "FK_CIUDAD";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";

    public static boolean insert(OficinaTransito oficinaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%s, %s, %s, %s, %s, %s, %s) ", NOMBRE, TELEFONO, DIRECCION, CORREO,
                FK_CIUDAD, ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, oficinaTransito.getNombre());
            ps.setString(2, oficinaTransito.getTelefono());
            ps.setString(3, oficinaTransito.getDireccion());
            ps.setString(4, oficinaTransito.getCorreo());
            ps.setInt(5, oficinaTransito.getFk_ciudad());
            ps.setInt(6, oficinaTransito.getEstado());
            ps.setInt(7, 1);

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

    public static OficinaTransito selectByOne(OficinaTransito oficinaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        OficinaTransito oficinaEncontrada = null;

        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT * FROM %s WHERE %s = ?", TABLA, PK_OFICINA_TRANSITO));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, oficinaTransito.getId());
            rs = ps.executeQuery();
            oficinaEncontrada = new OficinaTransito();

            if (rs.next()) {
                oficinaEncontrada.setId(rs.getLong(PK_OFICINA_TRANSITO));
                oficinaEncontrada.setNombre(rs.getString(NOMBRE));
                oficinaEncontrada.setTelefono(rs.getString(TELEFONO));
                oficinaEncontrada.setCorreo(rs.getString(CORREO));
                oficinaEncontrada.setDireccion(rs.getString(DIRECCION)); 
                oficinaEncontrada.setFk_ciudad(rs.getInt(FK_CIUDAD));
            }

            return oficinaEncontrada;
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

            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return null;
    }

    public static boolean update(OficinaTransito oficinaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? ", 
                NOMBRE, TELEFONO, CORREO, DIRECCION, FK_CIUDAD, ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_OFICINA_TRANSITO));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, oficinaTransito.getNombre());
            ps.setString(2, oficinaTransito.getTelefono());
            ps.setString(3, oficinaTransito.getCorreo());
            ps.setString(4, oficinaTransito.getDireccion());
            ps.setInt(5, oficinaTransito.getFk_ciudad());
            ps.setInt(6, oficinaTransito.getEstado());
            ps.setInt(7, 1);
            ps.setLong(8, oficinaTransito.getId());
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

    public static boolean updateEstado(OficinaTransito oficinaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_OFICINA_TRANSITO));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, oficinaTransito.getEstado());
            ps.setInt(2, 1);
            ps.setLong(3, oficinaTransito.getId());
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

}
