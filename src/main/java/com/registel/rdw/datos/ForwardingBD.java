/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.controladores.ControladorCategoriaLicencia;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.Forwarding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Richard Mejia
 * 
 * @date 30/07/2018
 */
public class ForwardingBD {
    
    private static final String TABLA = "tbl_forwarding_wtch";
    private static final String ID = "id";
//    private static final String FECHA_SERVER = "fecha_server";
    private static final String FECHA_GPS = "fecha_gps";
//    private static final String RUMBO = "rumbo";
//    private static final String RUMBO_RADIANES = "rumbo_radianes";
//    private static final String GPS_ID = "gps_id";
//    private static final String VELOCIDAD = "velocidad";
//    private static final String TRANS_REASON = "trans_reason";
//    private static final String TRANS_REASON_SPECIFIC_DATA = "trans_reason_specific_data";
      private static final String MSG = "msg";
//    private static final String TOTAL_DIA = "total_dia";
    private static final String NUMERACION = "numeracion";
//    private static final String ENTRADAS = "entradas";
//    private static final String SALIDAS = "salidas";
//    private static final String ALARMA = "alarma";
    private static final String DISTANCIA_METROS = "distancia_metros";
//    private static final String LOCALIZACION = "localizacion";
//    private static final String LATITUD = "latitud";
//    private static final String LONGITUD = "longitud";
    private static final String PLACA = "placa";
//    private static final String NOMBRE_FLOTA = "nombre_flota";
//    private static final String ESTADO_CONSOLIDACION = "estado_consolidacion";
    private static final String YA_LIQUIDO = "ya_liquido";
    
    public static final String MOVIL_ENCENDIDO = "MOVILENCENDIDO";
    public static final String MOVIL_APAGADO = "MOVILAPAGADO";
    
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018
     * Metodo que obtiene la sumatoria de distancia recorrida en metros de un vehiculo a 
     * partir de una fecha dada.
     * @param placa
     * @param fecha
     * @param entorno
     * @return 
     */
    public static double obtenerKmRecorridosVehiculo(String placa, Date fecha, Entorno entorno) {
        ConexionExterna conexionExterna = null;
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        double resultado = 0;
        
        try {
            
            if (entorno != null) {
                ConexionExterna.establecerEntorno(entorno);
            }
            
            conexionExterna = new ConexionExterna();
            conexion = conexionExterna.conectar();

            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT SUM(%s) as %1$s ", DISTANCIA_METROS));
            query.append(String.format("FROM %s ", TABLA));
            query.append(String.format("WHERE %s = ? AND %s >= ?", PLACA, FECHA_GPS));
            
            ps = conexion.prepareStatement(query.toString());
            ps.setString(1, placa);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                resultado = rs.getDouble(DISTANCIA_METROS);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

                if (conexionExterna != null) {
                    conexionExterna.desconectar();
                }
            } catch (SQLException e) {
                Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        return resultado;
    }
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018
     * Metodo que consulta los datos de los momentos en que un vehiculo a pasado de
     * los estados de VEHICULO_ENCENDIDO y VEHICULO_APAGADO a partir de una fecha dada.
     * @param placa
     * @param fecha
     * @param entorno
     * @return 
     */
    public static List<Forwarding> obtenerHorasTrabajadasVehiculo(String placa, Date fecha, Entorno entorno) {
        ConexionExterna conexionExterna = null;
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Forwarding> datos = new ArrayList<>();
        
        try {
            
            if (entorno != null) {
                ConexionExterna.establecerEntorno(entorno);
            }
            
            conexionExterna = new ConexionExterna();
            conexion = conexionExterna.conectar();

            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT %s, %s, UPPER(REPLACE(MSG, ' ', '')) as %s ", ID, FECHA_GPS, MSG));
            query.append(String.format("FROM %s ", TABLA));
            query.append(String.format("WHERE %s = ? AND %s >= ? ", PLACA, FECHA_GPS));
            query.append(String.format("AND UPPER(REPLACE(%s, ' ', '')) IN (?, ?) ", MSG));
            query.append(String.format("ORDER BY %s", ID));
            
            ps = conexion.prepareStatement(query.toString());
            ps.setString(1, placa);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));
            ps.setString(3, MOVIL_ENCENDIDO);
            ps.setString(4, MOVIL_APAGADO);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Forwarding dato = new Forwarding();
                dato.setId(rs.getLong(ID));
                dato.setFechaGps(rs.getTimestamp(FECHA_GPS));
                dato.setMsg(rs.getString(MSG));
                datos.add(dato);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

                if (conexionExterna != null) {
                    conexionExterna.desconectar();
                }
            } catch (SQLException e) {
                Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        return datos;
    }
    
    /**
     * @author Richard Mejia
     * @date 24/10/2018
     * Método encargado de obtener la cantidad de pasajeros a liquidar en una liquidación por tiempo.
     * Se consultan los registros de la tabla tbl_forwarding_wtch donde la fecha del server se encuentra
     * en el intervalo que se le envía, que correspondan a la placa y no se han utilizado aún en alguna
     * liquidación. De los recursos recuperados se resta la numeración mayor con la numeración menor y el
     * resultado es la cantidad de pasajeros que se deben liquidar para esa placa en el lapso determinado.
     * @param fechaInicio Fecha con hora que indica desde qué momento se deben buscar los registros.
     * @param fechaFin Fecha con hora que indica hasta qué momento se deben buscar los registros.
     * @param placa Valor de la placa con la que se van a filtrar los registros de la tabla.
     * @param entorno Objeto que permite realizar la conexión a la base de datos de reportes del gps.
     * @return Retorna la cantidad de pasajeros a liquidar. Si no se encuentran valores o sucede un error se
     * retorna 0.
     */
    public static Forwarding obtenerNumeracionLiq(String fechaInicio, String fechaFin, String placa, Entorno entorno) {
        ConexionExterna conexionExterna = null;
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Forwarding resultado = null;
        
        try {
            if (entorno != null) {
                ConexionExterna.establecerEntorno(entorno);
            }
            
            conexionExterna = new ConexionExterna();
            conexion = conexionExterna.conectar();

            StringBuilder query = new StringBuilder();
            query.append("CALL `sp_obtener_valores_liquidacion_tiempo`(?, ?, ?)");
            int pos = 1;
            ps = conexion.prepareStatement(query.toString());
            ps.setString(pos++, placa);
            ps.setString(pos++, fechaInicio);
            ps.setString(pos++, fechaFin);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                resultado = new Forwarding();
                resultado.setTotalPasajeros(rs.getInt("TOTAL_PASAJEROS"));
                resultado.setNumeracionInicial(rs.getInt("NUMERACION_INICIAL"));
                resultado.setNumeracionFinal(rs.getInt("NUMERACION_FINAL"));
                resultado.setIdInicial(rs.getLong("ID_GPS_INICIAL"));
                resultado.setIdFinal(rs.getLong("ID_GPS_FINAL"));
                resultado.setLiquidacionRelativa(rs.getBoolean("LIQUIDACION_RELATIVA"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ForwardingBD.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

                if (conexionExterna != null) {
                    conexionExterna.desconectar();
                }
            } catch (SQLException e) {
                Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        return resultado;
    }
    
    /**
     * @Richard Mejia
     * @date 24/10/2018
     * Método encargado de actualizar a 1 el valor del campo ya_liquido los registros de la tabla tbl_forwarding_wtch 
     * que fueron utilizados en una liquidación por tiempo. 
     * @param fechaInicio Fecha con hora que indica desde qué momento se deben actualizar los registros.
     * @param fechaFin Fecha con hora que indica hasta qué momento se deben actualizar los registros.
     * @param placa Valor de la placa con la que se van a actualizar los registros de la tabla.
     * @param entorno Objeto que permite realizar la conexión a la base de datos de reportes del gps.
     * @return Retorna true si logra actualizar los registros y no se presenta ningún error.
     */
    public static boolean updateRegistrosLiquidadosGps(String fechaInicio, String fechaFin, String placa, Entorno entorno) {
        ConexionExterna conexionExterna = null;
        Connection conexion = null;
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();

        try {
            if (entorno != null) {
                ConexionExterna.establecerEntorno(entorno);
            }

            conexionExterna = new ConexionExterna();
            conexion = conexionExterna.conectar();
            query.append(String.format("UPDATE %s SET ", TABLA));
            query.append(String.format("%1$s = 1 ", YA_LIQUIDO));
            query.append(String.format("WHERE %1$s BETWEEN ? AND ? AND LOWER(%2$s) = LOWER(?) AND %3$s > 0 AND %4$s = 0",
                    FECHA_GPS, PLACA, NUMERACION, YA_LIQUIDO));
            conexion.setAutoCommit(false);
            int pos = 1;
            ps = conexion.prepareStatement(query.toString());
            ps.setString(pos++, fechaInicio);
            ps.setString(pos++, fechaFin);
            ps.setString(pos++, placa);
            int pk = ps.executeUpdate();
            conexion.commit();

            if (pk > 0) {
                return true;
            } else {
                conexion.rollback();
            }
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(ForwardingBD.class.getName()).log(Level.SEVERE, null, e);
            
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch(SQLException ie) {
                    Logger.getLogger(ForwardingBD.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

                if (conexionExterna != null) {
                    conexionExterna.desconectar();
                }
            } catch (SQLException e) {
                Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return false;
    }
    
    /**
     * @author Richard Mejia
     * @date 29/10/2018
     * Método encargado de desmarcar como ya liquidados los registros de la base de datos del gps que fueron utilizados
     * en una liquidacion por tiempos que fue anulada.
     * @param idInicialGps Id inicial del rango de registros que se van a actualizar
     * @param idFinalGps Id final del rango de registros que se van a actualizar
     * @param placa Valor de la placa con la que se van a actualizar los registros de la tabla.
     * @param entorno Objeto que permite realizar la conexión a la base de datos de reportes del gps.
     * @return Retorna true si logra actualizar los registros y no se presenta ningún error.
     */
    public static boolean updateRegistrosAnuladosGps(long idInicialGps, long idFinalGps, String placa, Entorno entorno) {
        ConexionExterna conexionExterna = null;
        Connection conexion = null;
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();

        try {
            if (entorno != null) {
                ConexionExterna.establecerEntorno(entorno);
            }

            conexionExterna = new ConexionExterna();
            conexion = conexionExterna.conectar();
            query.append(String.format("UPDATE %s SET ", TABLA));
            query.append(String.format("%1$s = 0 ", YA_LIQUIDO));
            query.append(String.format("WHERE %1$s BETWEEN ? AND ? AND LOWER(%2$s) = LOWER(?) AND %3$s > 0 AND %4$s = 1",
                    ID, PLACA, NUMERACION, YA_LIQUIDO));
            conexion.setAutoCommit(false);
            int pos = 1;
            ps = conexion.prepareStatement(query.toString());
            ps.setLong(pos++, idInicialGps);
            ps.setLong(pos++, idFinalGps);
            ps.setString(pos++, placa);
            int pk = ps.executeUpdate();
            conexion.commit();

            if (pk > 0) {
                return true;
            } else {
                conexion.rollback();
            }
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(ForwardingBD.class.getName()).log(Level.SEVERE, null, e);
            
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch(SQLException ie) {
                    Logger.getLogger(ForwardingBD.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

                if (conexionExterna != null) {
                    conexionExterna.desconectar();
                }
            } catch (SQLException e) {
                Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return false;
    }
    
}
