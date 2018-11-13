/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.LicenciaTransito;
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
 * @date 12/07/2018
 */
public class LicenciaTransitoBD {
    
    private static final String TABLA = "tbl_licencia_conduccion";
    private static final String PK_LICENCIA_CONDUCCION = "PK_LICENCIA_CONDUCCION";
    private static final String FK_CONDUCTOR = "FK_CONDUCTOR";
    private static final String NUMERO_LICENCIA = "NUMERO_LICENCIA";
    private static final String FECHA_EXPEDICION = "FECHA_EXPEDICION";
    private static final String VIGENCIA = "VIGENCIA";
    private static final String FK_OFICINA_TRANSITO = "FK_OFICINA_TRANSITO";
    private static final String FK_CATEGORIA = "FK_CATEGORIA";
    private static final String OBSERVACIONES = "OBSERVACIONES";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final int ESTADO_ACTIVO = 1;
    
    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método que verifica si un conductor puede registrar una nueva licencia de tránsito.
     * Se verifica que el conductor no tenga ya una licencia de la misma categoría y que
     * la fecha de vigencia no se sobreponga con el período de la nueva licencia de tránsito.
     * Se excluye la licencia que llega para el caso de la edición.
     * @param licenciaTransito
     * @return 
     */
    public static boolean conductorPuedeRegistrarLicencia(LicenciaTransito licenciaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT COUNT(licencia.%s) ", PK_LICENCIA_CONDUCCION));
            query.append(String.format("FROM %s licencia ", TABLA));
            query.append("INNER JOIN tbl_conductor conductor ON ");
            query.append(String.format("licencia.%s = conductor.PK_CONDUCTOR AND conductor.PK_CONDUCTOR = ? ", 
                    FK_CONDUCTOR));
            query.append(String.format("WHERE licencia.%s = %d AND conductor.%s = %d ", ESTADO, ESTADO_ACTIVO, 
                    ESTADO, ESTADO_ACTIVO));
            query.append(String.format("AND licencia.%s = ? ", FK_CATEGORIA));
            query.append(String.format("AND licencia.%s BETWEEN ? AND ?", VIGENCIA));
            query.append(String.format("AND licencia.%s <> ?", PK_LICENCIA_CONDUCCION));
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, licenciaTransito.getFk_conductor());
            ps.setLong(2, licenciaTransito.getFk_categoria());
            ps.setDate(3, new java.sql.Date(licenciaTransito.getFechaExpedicion().getTime()));
            ps.setDate(4, new java.sql.Date(licenciaTransito.getVigencia().getTime()));
            ps.setLong(5, licenciaTransito.getId());
            rs = ps.executeQuery();
            int resultado = Integer.MIN_VALUE;

            if (rs.next()) {
                resultado = rs.getInt(1);
            }

            return resultado == 0;
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

        return false;
    }

    public static boolean insert(LicenciaTransito licenciaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%s, %s, %s, %s, %s, %s, %s, %s, %s) ", FK_CONDUCTOR, 
                NUMERO_LICENCIA, FECHA_EXPEDICION, VIGENCIA, FK_CATEGORIA, FK_OFICINA_TRANSITO, 
                OBSERVACIONES, ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, licenciaTransito.getFk_conductor());
            ps.setString(2, licenciaTransito.getNumeroLicencia());
            ps.setDate(3, new java.sql.Date(licenciaTransito.getFechaExpedicion().getTime()));
            ps.setDate(4, new java.sql.Date(licenciaTransito.getVigencia().getTime()));
            ps.setLong(5, licenciaTransito.getFk_categoria());
            ps.setLong(6, licenciaTransito.getFk_oficina_transito());
            ps.setString(7, licenciaTransito.getObservaciones());
            ps.setInt(8, licenciaTransito.getEstado());
            ps.setInt(9, 1);

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
    
    public static boolean update(LicenciaTransito licenciaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? ", 
                FK_CONDUCTOR, NUMERO_LICENCIA, FECHA_EXPEDICION, VIGENCIA, FK_CATEGORIA, FK_OFICINA_TRANSITO, 
                OBSERVACIONES, ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_LICENCIA_CONDUCCION));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, licenciaTransito.getFk_conductor());
            ps.setString(2, licenciaTransito.getNumeroLicencia());
            ps.setDate(3, new java.sql.Date(licenciaTransito.getFechaExpedicion().getTime()));
            ps.setDate(4, new java.sql.Date(licenciaTransito.getVigencia().getTime()));
            ps.setLong(5, licenciaTransito.getFk_categoria());
            ps.setLong(6, licenciaTransito.getFk_oficina_transito());
            ps.setString(7, licenciaTransito.getObservaciones());
            ps.setInt(8, licenciaTransito.getEstado());
            ps.setInt(9, 1);
            ps.setLong(10, licenciaTransito.getId());
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
    
    public static boolean updateEstado(LicenciaTransito licenciaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_LICENCIA_CONDUCCION));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, licenciaTransito.getEstado());
            ps.setInt(2, 1);
            ps.setLong(3, licenciaTransito.getId());
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

    public static LicenciaTransito selectByOne(LicenciaTransito licenciaTransito) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LicenciaTransito licenciaEncontrada;
        
        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT * FROM %s WHERE %s = ?", TABLA, PK_LICENCIA_CONDUCCION));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, licenciaTransito.getId());
            rs = ps.executeQuery();
            licenciaEncontrada = new LicenciaTransito();
            
            if (rs.next()) {
                licenciaEncontrada = asignarValoresResultSet(rs);
            }
            
            return licenciaEncontrada;
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
    
    
    
    private static LicenciaTransito asignarValoresResultSet(ResultSet rs) {
        LicenciaTransito entidadDefinida = new LicenciaTransito();

        try {
            entidadDefinida.setId(rs.getLong(PK_LICENCIA_CONDUCCION));
            entidadDefinida.setFk_conductor(rs.getInt(FK_CONDUCTOR));
            entidadDefinida.setNumeroLicencia(rs.getString(NUMERO_LICENCIA));
            entidadDefinida.setFechaExpedicion(rs.getDate(FECHA_EXPEDICION));
            entidadDefinida.setVigencia(rs.getDate(VIGENCIA));
            entidadDefinida.setFk_categoria(rs.getLong(FK_CATEGORIA));
            entidadDefinida.setFk_oficina_transito(rs.getLong(FK_OFICINA_TRANSITO));
            entidadDefinida.setObservaciones(rs.getString(OBSERVACIONES));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
        } catch (SQLException e) {
            Logger.getLogger(MantenimientoBD.class.getName()).log(Level.SEVERE, null, e);
        }

        return entidadDefinida;
    }
    
    
    
    
    public static List<LicenciaTransito> obtenerLicenciasTransitoPorVencerPorPlaca(String placa, int intervalo) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LicenciaTransito> resultado = new ArrayList<>();
        
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT licenciaConduccion.*, ");
            query.append("CONCAT(conductor.NOMBRE, ' ', conductor.APELLIDO) as nombreConductor, ");
            query.append("categoriaLicencia.NOMBRE as nombreCategoria, oficinaTransito.NOMBRE as nombreOficina ");
            query.append(String.format("FROM %s licenciaConduccion ", TABLA));
            query.append("INNER JOIN tbl_conductor_vehiculo conductorVehiculo ON ");
            query.append(String.format("licenciaConduccion.%s = conductorVehiculo.%s ", FK_CONDUCTOR, FK_CONDUCTOR));
            query.append("INNER JOIN tbl_vehiculo vehiculo ON ");
            query.append("conductorVehiculo.FK_VEHICULO = vehiculo.PK_VEHICULO ");
            query.append("INNER JOIN tbl_conductor conductor ON ");
            query.append(String.format("licenciaConduccion.%s = conductor.PK_CONDUCTOR ", FK_CONDUCTOR));
            query.append("INNER JOIN tbl_categoria_licencia categoriaLicencia ON ");
            query.append(String.format("licenciaConduccion.%s = categoriaLicencia.PK_CATEGORIA ", FK_CATEGORIA));
            query.append("INNER JOIN tbl_oficina_transito oficinaTransito ON ");
            query.append(String.format("licenciaConduccion.%s = oficinaTransito.PK_OFICINA_TRANSITO ", FK_OFICINA_TRANSITO));
            query.append(String.format("WHERE licenciaConduccion.%s = %d ", ESTADO, ESTADO_ACTIVO));
            query.append(String.format("AND conductorVehiculo.%s = %d ", ESTADO, ESTADO_ACTIVO));
            query.append(String.format("AND vehiculo.%s = %d ", ESTADO, ESTADO_ACTIVO));
            query.append("AND vehiculo.PLACA = ? ");
            query.append(String.format("AND licenciaConduccion.%s BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY)", 
                    VIGENCIA));
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, placa);
            ps.setInt(2, intervalo);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                LicenciaTransito licencia = asignarValoresResultSet(rs);
                licencia.setNombreConductor(rs.getString("nombreConductor"));
                licencia.setNombreCategoria(rs.getString("nombreCategoria"));
                licencia.setNombreOficina(rs.getString("nombreOficina"));
                resultado.add(licencia);
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
            
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }
        
        return resultado;
    }
    
    
    
}
