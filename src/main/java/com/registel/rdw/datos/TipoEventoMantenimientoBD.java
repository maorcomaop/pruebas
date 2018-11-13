/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.TipoEventoMantenimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard Mejia
 * 
 * @date 17/07/2018
 */
public class TipoEventoMantenimientoBD {
    
    private static final String TABLA = "tbl_tipo_evento_mantenimiento";
    private static final String PK_TIPO_EVENTO_MANTENIMIENTO = "PK_TIPO_EVENTO_MANTENIMIENTO";
    private static final String NOMBRE = "NOMBRE";
    private static final String UNIDAD_MEDIDA = "UNIDAD_MEDIDA";
    private static final String DESCRIPCION = "DESCRIPCION";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final int VALOR_POSITIVO = 1;
    
    private static void asignarParametros(PreparedStatement ps, TipoEventoMantenimiento tipoEventoMantenimiento, 
            boolean asignarPk) throws SQLException {
        ps.setString(1, tipoEventoMantenimiento.getNombre());
        ps.setString(2, tipoEventoMantenimiento.getUnidadMedida());
        ps.setString(3, tipoEventoMantenimiento.getDescripcion());
        ps.setInt(4, VALOR_POSITIVO);
        ps.setInt(5, VALOR_POSITIVO);

        if (asignarPk) {
            ps.setLong(6, tipoEventoMantenimiento.getId());
        }
    }
    
    
    public static boolean insert(TipoEventoMantenimiento tipoEventoMantenimiento) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%s, %s, %s, %s, %s) ", NOMBRE, UNIDAD_MEDIDA, DESCRIPCION, 
                ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, tipoEventoMantenimiento, false);
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
    
    public static boolean update(TipoEventoMantenimiento tipoEventoMantenimiento) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ? ", 
                NOMBRE, UNIDAD_MEDIDA, DESCRIPCION, ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_TIPO_EVENTO_MANTENIMIENTO));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, tipoEventoMantenimiento, true);
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
    
    public static boolean updateEstado(TipoEventoMantenimiento tipoEventoMantenimiento) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_TIPO_EVENTO_MANTENIMIENTO));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, tipoEventoMantenimiento.getEstado());
            ps.setInt(2, VALOR_POSITIVO);
            ps.setLong(3, tipoEventoMantenimiento.getId());
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

    public static TipoEventoMantenimiento selectByOne(TipoEventoMantenimiento tipoEventoMantenimiento) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TipoEventoMantenimiento tipoEventoMantenimientoEncontrado;

        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT * FROM %s WHERE %s = ?", TABLA, PK_TIPO_EVENTO_MANTENIMIENTO));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, tipoEventoMantenimiento.getId());
            rs = ps.executeQuery();
            tipoEventoMantenimientoEncontrado = new TipoEventoMantenimiento();

            if (rs.next()) {
                tipoEventoMantenimientoEncontrado.setId(rs.getLong(PK_TIPO_EVENTO_MANTENIMIENTO));
                tipoEventoMantenimientoEncontrado.setNombre(rs.getString(NOMBRE));
                tipoEventoMantenimientoEncontrado.setUnidadMedida(rs.getString(UNIDAD_MEDIDA));
                tipoEventoMantenimientoEncontrado.setDescripcion(rs.getString(DESCRIPCION));
                tipoEventoMantenimientoEncontrado.setEstado(rs.getInt(ESTADO));
            }

            return tipoEventoMantenimientoEncontrado;
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
    
    public static TipoEventoMantenimiento selectByOne(long pkTipoEventoMantenimiento) {
        TipoEventoMantenimiento tipoEventoMantenimiento = new TipoEventoMantenimiento();
        tipoEventoMantenimiento.setId(pkTipoEventoMantenimiento);
        
        return selectByOne(tipoEventoMantenimiento);
    }
    
    public static List<TipoEventoMantenimiento> select() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TipoEventoMantenimiento> resultado = new ArrayList<>();
        TipoEventoMantenimiento tipoEventoMantenimiento;
        StringBuilder query = new StringBuilder();
        query.append("SELECT * from tbl_tipo_evento_mantenimiento");
        
        try {
            ps = con.prepareStatement(query.toString());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                tipoEventoMantenimiento = new TipoEventoMantenimiento();
                tipoEventoMantenimiento.setId(rs.getLong("PK_TIPO_EVENTO_MANTENIMIENTO"));
                tipoEventoMantenimiento.setNombre(rs.getString("NOMBRE"));
                tipoEventoMantenimiento.setUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
                tipoEventoMantenimiento.setDescripcion(rs.getString("DESCRIPCION"));
                tipoEventoMantenimiento.setEstado(rs.getInt("ESTADO"));
                resultado.add(tipoEventoMantenimiento);
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
