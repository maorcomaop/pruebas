/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.VehiculoAsegurado;
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
 * @date 16/07/2018
 */
public class VehiculoAseguradoBD {
    
    private static final String TABLA = "tbl_vehiculo_asegurado";
    private static final String PK_POLIZA_VEHICULO = "PK_POLIZA_VEHICULO";
    private static final String FK_POLIZA = "FK_POLIZA";
    private static final String FK_VEHICULO = "FK_VEHICULO";
    private static final String NUMERO_POLIZA = "NUMERO_POLIZA";
    private static final String INICIO_VIGENCIA = "INICIO_VIGENCIA";
    private static final String FIN_VIGENCIA = "FIN_VIGENCIA";
    private static final String VALOR_PRIMA = "VALOR_PRIMA";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final int VALOR_POSITIVO = 1;
    
    
    private static void asignarParametros(PreparedStatement ps, VehiculoAsegurado vehiculoAsegurado, boolean asignarPk)
            throws SQLException {
        ps.setLong(1, vehiculoAsegurado.getFkPoliza());
        ps.setLong(2, vehiculoAsegurado.getFkVehiculo());
        ps.setString(3, vehiculoAsegurado.getNumeroPoliza());
        ps.setDate(4, new java.sql.Date(vehiculoAsegurado.getInicioVigencia().getTime()));
        ps.setDate(5, new java.sql.Date(vehiculoAsegurado.getFinVigencia().getTime()));
        ps.setDouble(6, vehiculoAsegurado.getValorPrima());
        ps.setInt(7, VALOR_POSITIVO);
        ps.setInt(8, VALOR_POSITIVO);

        if (asignarPk) {
            ps.setLong(9, vehiculoAsegurado.getId());
        }
    }
    
    
    
    private static VehiculoAsegurado asignarValoresResultSet(ResultSet rs) {
        VehiculoAsegurado entidadDefinida = new VehiculoAsegurado();

        try {
            entidadDefinida.setId(rs.getLong(PK_POLIZA_VEHICULO));
            entidadDefinida.setFkPoliza(rs.getLong(FK_POLIZA));
            entidadDefinida.setFkVehiculo(rs.getInt(FK_VEHICULO));
            entidadDefinida.setNumeroPoliza(rs.getString(NUMERO_POLIZA));
            entidadDefinida.setInicioVigencia(rs.getDate(INICIO_VIGENCIA));
            entidadDefinida.setFinVigencia(rs.getDate(FIN_VIGENCIA));
            entidadDefinida.setValorPrima(rs.getDouble(VALOR_PRIMA));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
            entidadDefinida.setPlacaVehiculo(rs.getString("PLACA"));
        } catch (SQLException e) {
            Logger.getLogger(MantenimientoBD.class.getName()).log(Level.SEVERE, null, e);
        }

        return entidadDefinida;
    }
    
    
    public static boolean insert(VehiculoAsegurado vehiculoAsegurado) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%s, %s, %s, %s, %s, %s, %s, %s) ", FK_POLIZA, FK_VEHICULO, NUMERO_POLIZA, 
                INICIO_VIGENCIA, FIN_VIGENCIA, VALOR_PRIMA, ESTADO, MODIFICACION_LOCAL));
        query.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, vehiculoAsegurado, false);
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
    
    public static boolean update(VehiculoAsegurado vehiculoAsegurado) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? ", 
                FK_POLIZA, FK_VEHICULO, NUMERO_POLIZA, INICIO_VIGENCIA, FIN_VIGENCIA, VALOR_PRIMA, 
                ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_POLIZA_VEHICULO));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, vehiculoAsegurado, true);
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
    
    public static boolean updateEstado(VehiculoAsegurado vehiculoAsegurado) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_POLIZA_VEHICULO));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, vehiculoAsegurado.getEstado());
            ps.setInt(2, VALOR_POSITIVO);
            ps.setLong(3, vehiculoAsegurado.getId());
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

    public static VehiculoAsegurado selectByOne(VehiculoAsegurado vehiculoAsegurado) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        VehiculoAsegurado vehiculoAseguradoEncontrado;

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT vehiculoAsegurado.*, vehiculo.PLACA ");
            query.append(String.format("FROM %s vehiculoAsegurado ", TABLA));
            query.append("INNER JOIN tbl_vehiculo vehiculo ON vehiculoAsegurado.fk_vehiculo = vehiculo.pk_vehiculo ");
            query.append(String.format("WHERE %s = ?", PK_POLIZA_VEHICULO));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, vehiculoAsegurado.getId());
            rs = ps.executeQuery();
            vehiculoAseguradoEncontrado = new VehiculoAsegurado();

            if (rs.next()) {
                vehiculoAseguradoEncontrado = asignarValoresResultSet(rs);
            }

            return vehiculoAseguradoEncontrado;
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
    
    public static VehiculoAsegurado selectByOne(long pkVehiculoAsegurado) {
        VehiculoAsegurado vehiculoAsegurado = new VehiculoAsegurado();
        vehiculoAsegurado.setId(pkVehiculoAsegurado);
        
        return selectByOne(vehiculoAsegurado);
    }
    
    /**
     * @author Richard Mejia
     * @date 16/07/2018
     * Metodo encargado de validar si un vehiculo es asegurable para una determinada
     * poliza en un lapso seleccionado.
     * Un vehiculo no puede tener dos polizas iguales cuyas fechas se sobrepongan.
     * @param vehiculoAsegurado
     * @return 
     */    
    public static boolean vehiculoEsAsegurable(VehiculoAsegurado vehiculoAsegurado) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT COUNT(vehiculoAsegurado.%s) ", PK_POLIZA_VEHICULO));
            query.append(String.format("FROM %s vehiculoAsegurado ", TABLA));
            query.append(String.format("WHERE vehiculoAsegurado.%s = %d ", ESTADO, VALOR_POSITIVO));
            query.append(String.format("AND vehiculoAsegurado.%s <> ? ", PK_POLIZA_VEHICULO));
            query.append(String.format("AND vehiculoAsegurado.%s = ? ", FK_POLIZA));
            query.append(String.format("AND vehiculoAsegurado.%s = ? ", FK_VEHICULO));
            query.append(String.format("AND vehiculoAsegurado.%s BETWEEN ? AND ?", FIN_VIGENCIA));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, vehiculoAsegurado.getId());
            ps.setLong(2, vehiculoAsegurado.getFkPoliza());
            ps.setInt(3, vehiculoAsegurado.getFkVehiculo());
            ps.setDate(4, new java.sql.Date(vehiculoAsegurado.getInicioVigencia().getTime()));
            ps.setDate(5, new java.sql.Date(vehiculoAsegurado.getFinVigencia().getTime()));
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
    
    
    public static List<VehiculoAsegurado> obtenerPolizasPorVencerVehiculo(String placa, int intervalo) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<VehiculoAsegurado> resultado = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT vehiculoAsegurado.*, vehiculo.PLACA, poliza.NOMBRE as nombrePoliza, ");
            query.append("empresa.NOMBRE as nombreEmpresaVehiculo, aseguradora.NOMBRE as nombreAseguradora ");
            query.append(String.format("FROM %s vehiculoAsegurado ", TABLA));
            query.append("INNER JOIN tbl_vehiculo vehiculo ");
            query.append(String.format("ON vehiculoAsegurado.%s = vehiculo.PK_VEHICULO ", FK_VEHICULO));
            query.append("INNER JOIN tbl_poliza poliza ON ");
            query.append(String.format("vehiculoAsegurado.%s = poliza.PK_POLIZA ", FK_POLIZA));
            query.append("INNER JOIN tbl_aseguradora aseguradora ON ");
            query.append("poliza.FK_ASEGURADORA = aseguradora.PK_ASEGURADORA ");
            query.append("LEFT JOIN tbl_vehiculo_empresa vehiculoEmpresa ON ");
            query.append("vehiculo.PK_VEHICULO = vehiculoEmpresa.FK_VEHICULO ");
            query.append("LEFT JOIN tbl_empresa empresa ON ");
            query.append("vehiculoEmpresa.FK_EMPRESA = empresa.PK_EMPRESA ");
            query.append(String.format("WHERE vehiculoAsegurado.%s = %d ", ESTADO, VALOR_POSITIVO));
            query.append(String.format("AND vehiculo.%s = %d ", ESTADO, VALOR_POSITIVO));
            query.append(String.format("AND vehiculoAsegurado.%s BETWEEN ", FIN_VIGENCIA));
            query.append("NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY) ");
            query.append("AND vehiculo.PLACA = ? ");
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, intervalo);
            ps.setString(2, placa);
            rs = ps.executeQuery();

            while (rs.next()) {
                VehiculoAsegurado vehiculoAsegurado = asignarValoresResultSet(rs);
                vehiculoAsegurado.setNombrePoliza(rs.getString("nombrePoliza"));
                vehiculoAsegurado.setNombreEmpresaVehiculo(rs.getString("nombreEmpresaVehiculo"));
                vehiculoAsegurado.setNombreAseguradora(rs.getString("nombreAseguradora"));
                resultado.add(vehiculoAsegurado);
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

            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return resultado;
    }
    
    
}
