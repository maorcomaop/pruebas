/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.CategoriaLicencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Richard Mejia
 * 
 * @date 11/07/2018
 */
public class CategoriaLicenciaBD {
    
    private static final String PK_CATEGORIA = "PK_CATEGORIA";
    private static final String NOMBRE = "NOMBRE";
    private static final String DESCRIPCION = "DESCRIPCION";
    private static final String ESTADO = "ESTADO"; 
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    
    public static boolean insert(CategoriaLicencia categoriaLicencia) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO tbl_categoria_licencia ");
        query.append(String.format(" (%s, %s, %s, %s) ", NOMBRE, DESCRIPCION, ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?)");
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, categoriaLicencia.getNombre());
            ps.setInt(3, categoriaLicencia.getEstado());
            ps.setInt(4, 1);
            
            if (categoriaLicencia.getDescripcion() != null) {
                ps.setString(2, categoriaLicencia.getDescripcion());
            } else {
                ps.setString(2, null);
            }
            
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
    
    public static CategoriaLicencia selectByOne(CategoriaLicencia categoriaLicencia) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoriaLicencia categoriaEncontrada = null;        
        
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM tbl_categoria_licencia WHERE PK_CATEGORIA = ?");
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, categoriaLicencia.getId());
            rs = ps.executeQuery();
            categoriaEncontrada = new CategoriaLicencia();
            
            if (rs.next()) {
                categoriaEncontrada.setId(rs.getLong(PK_CATEGORIA));
                categoriaEncontrada.setNombre(rs.getString(NOMBRE));
                categoriaEncontrada.setDescripcion(rs.getString(DESCRIPCION));
                categoriaEncontrada.setEstado(rs.getInt(ESTADO));
            }
            
            return categoriaEncontrada;
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
    
    public static boolean update(CategoriaLicencia categoriaLicencia) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE tbl_categoria_licencia SET ");
        query.append(String.format(" %s = ?, %s = ?, %s = ?, %s = ? ", 
                NOMBRE, DESCRIPCION, ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_CATEGORIA));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, categoriaLicencia.getNombre());
            ps.setString(2, categoriaLicencia.getDescripcion());
            ps.setInt(3, categoriaLicencia.getEstado());
            ps.setInt(4, 1);
            ps.setLong(5, categoriaLicencia.getId());
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
    
    public static boolean updateEstado(CategoriaLicencia categoriaLicencia) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE tbl_categoria_licencia SET ");
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_CATEGORIA));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, categoriaLicencia.getEstado());
            ps.setInt(2, 1);
            ps.setLong(3, categoriaLicencia.getId());
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
