/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.TipoPoliza;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Richard Mejia
 * 
 * @date 31/07/2018
 */
public class TipoPolizaBD {
    
    private static final String TABLA = "tbl_tipo_poliza";
    private static final String PRIMARY_KEY = "PK_TIPO_POLIZA";
    private static final String NOMBRE = "NOMBRE";
    private static final String DESCRIPCION = "DESCRIPCION";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final int VALOR_POSITIVO = 1;
    
    
    private static void asignarParametros(PreparedStatement ps, EntidadConfigurable entidadConfigurable, boolean asignarPk)
            throws SQLException, Exception {
        
        if (entidadConfigurable instanceof TipoPoliza == false) {
            throw new Exception();
        }
        
        TipoPoliza entidadDefinida = (TipoPoliza) entidadConfigurable;
        
        ps.setString(1, entidadDefinida.getNombre());
        ps.setString(2, entidadDefinida.getDescripcion());
        ps.setInt(3, VALOR_POSITIVO);
        ps.setInt(4, VALOR_POSITIVO);
        
        if (asignarPk) {
            ps.setLong(5, entidadDefinida.getId());
        }
    }
    
    public static boolean insert(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%s, %s, %s, %s) ", NOMBRE, DESCRIPCION, ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, entidadConfigurable, false);
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
    
    public static boolean update(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format("%s = ?, %s = ?, %s = ?, %s = ? ", NOMBRE, DESCRIPCION, ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PRIMARY_KEY));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, entidadConfigurable, true);
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
    
    public static boolean updateEstado(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PRIMARY_KEY));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, entidadConfigurable.getEstado());
            ps.setInt(2, VALOR_POSITIVO);
            ps.setLong(3, entidadConfigurable.getId());
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

    public static TipoPoliza selectByOne(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TipoPoliza entidadDefinida;

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * ");
            query.append(String.format("FROM %s mantenimiento ", TABLA));
            query.append(String.format("WHERE %s = ?", PRIMARY_KEY));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, entidadConfigurable.getId());
            rs = ps.executeQuery();
            entidadDefinida = new TipoPoliza();

            if (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs);
            }

            return entidadDefinida;
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
    
    public static TipoPoliza selectByOne(long pkEntidadConfigurable) {
        EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
        entidadConfigurable.setId(pkEntidadConfigurable);
        
        return selectByOne(entidadConfigurable);
    }
    
    public static List<TipoPoliza> select() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TipoPoliza> resultado = new ArrayList<>();
        TipoPoliza entidadDefinida;
        StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append(String.format("FROM %s ", TABLA));
        
        try {
            ps = con.prepareStatement(query.toString());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs);
                resultado.add(entidadDefinida);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            }
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
        
        return resultado;
    }
    
    private static TipoPoliza asignarValoresResultSet(ResultSet rs) {
        TipoPoliza entidadDefinida = new TipoPoliza();

        try {
            entidadDefinida.setId(rs.getLong(PRIMARY_KEY));
            entidadDefinida.setNombre(rs.getString(NOMBRE));
            entidadDefinida.setDescripcion(rs.getString(DESCRIPCION));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
        } catch (SQLException e) {
            Logger.getLogger(MantenimientoBD.class.getName()).log(Level.SEVERE, null, e);
        }

        return entidadDefinida;
    }
    
}
