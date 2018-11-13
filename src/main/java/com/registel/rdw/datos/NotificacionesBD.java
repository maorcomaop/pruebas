/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Notificacion;
import com.registel.rdw.utils.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Mejia
 */
public class NotificacionesBD {
    
    private static final String TABLA = "tbl_notificaciones";
    private static final String PRIMARY_KEY = "PK_NOTIFICACION";
    private static final String FK_MANTENIMIENTO = "FK_MANTENIMIENTO";
    private static final String MENSAJE = "MENSAJE";
    private static final String FECHA = "FECHA";
    private static final String LEIDA = "LEIDA";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final String NOMBRE_MANTENIMIENTO = "NOMBRE_MANTENIMIENTO";
    
    
    private static final int VALOR_POSITIVO = 1;
    private static final int VALOR_CERO = 0;
    
    private static final Object[] PARAMETROS_INSERT_UPDATE = { FK_MANTENIMIENTO, MENSAJE, FECHA, LEIDA, ESTADO, 
        MODIFICACION_LOCAL };
    
    private static void asignarParametros(PreparedStatement ps, EntidadConfigurable entidadConfigurable, boolean asignarPk)
            throws SQLException, Exception {
        
        if (entidadConfigurable instanceof Notificacion == false) {
            throw new Exception();
        }
        
        Notificacion entidadDefinida = (Notificacion) entidadConfigurable;
        int pos = 1;
        
        ps.setLong(pos++, entidadDefinida.getFkMantenimiento());
        ps.setString(pos++, entidadDefinida.getMensaje());
        ps.setTimestamp(pos++, new java.sql.Timestamp(entidadDefinida.getFecha().getTime()));
        ps.setInt(pos++, entidadDefinida.getLeida());
        ps.setInt(pos++, VALOR_POSITIVO);
        ps.setInt(pos++, VALOR_POSITIVO);

        if (asignarPk) {
            ps.setLong(pos++, entidadDefinida.getId());
        }
    }
    
    private static Notificacion asignarValoresResultSet(ResultSet rs) {
        Notificacion entidadDefinida = new Notificacion();

        try {
            entidadDefinida.setId(rs.getLong(PRIMARY_KEY));
            entidadDefinida.setFkMantenimiento(rs.getLong(FK_MANTENIMIENTO));
            entidadDefinida.setMensaje(rs.getString(MENSAJE));
            entidadDefinida.setFecha(rs.getTimestamp(FECHA));
            entidadDefinida.setLeida(rs.getInt(LEIDA));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
            entidadDefinida.setNombreMantenimiento(rs.getString(NOMBRE_MANTENIMIENTO));
        } catch (SQLException e) {
            Logger.getLogger(MantenimientoBD.class.getName()).log(Level.SEVERE, null, e);
        }

        return entidadDefinida;
    }
    
    public static boolean insert(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(StringUtils.generarCadena("%s", "(", ")", PARAMETROS_INSERT_UPDATE.length), 
                PARAMETROS_INSERT_UPDATE));
        query.append(String.format(StringUtils.generarCadena("?", "VALUES(", ")", PARAMETROS_INSERT_UPDATE.length), 
                PARAMETROS_INSERT_UPDATE));

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
        query.append(String.format(StringUtils.generarCadena("%s = ?", " ", " ", PARAMETROS_INSERT_UPDATE.length), 
                PARAMETROS_INSERT_UPDATE));
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
    
    public static List<Notificacion> selectUpdate(int idUsuario) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Notificacion> resultado = new ArrayList<>();
        Notificacion entidadDefinida;
        StringBuilder query = new StringBuilder();
        query.append("SELECT notificaciones.*, mantenimiento.NOMBRE AS NOMBRE_MANTENIMIENTO ");
        query.append(String.format("FROM %s notificaciones ", TABLA));
        query.append("INNER JOIN tbl_mantenimiento mantenimiento ON ");
        query.append(String.format("notificaciones.%s = mantenimiento.PK_MANTENIMIENTO ", 
                FK_MANTENIMIENTO));
        query.append(String.format("WHERE notificaciones.%1$s = %2$d AND notificaciones.%3$s = %4$d ", 
                ESTADO, VALOR_POSITIVO, LEIDA, VALOR_CERO));
        query.append(String.format("AND mantenimiento.FK_USUARIO = ? AND mantenimiento.%1$s = %2$d ", 
                ESTADO, VALOR_POSITIVO));
        
        try {
            ps = con.prepareStatement(query.toString());
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs);
                entidadDefinida.setLeida(VALOR_POSITIVO);
                update(entidadDefinida);
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
    
}
