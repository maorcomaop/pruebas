/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.controladores.Session;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Mantenimiento;
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
 * @author Richard Mejia
 * 
 * @date 31/07/2018
 */
public class MantenimientoBD {
    
    private static final String TABLA = "tbl_mantenimiento";
    private static final String PRIMARY_KEY = "PK_MANTENIMIENTO";
    private static final String FK_TIPO_EVENTO_MANTENIMIENTO = "FK_TIPO_EVENTO_MANTENIMIENTO";
    private static final String VALOR_PRIMERA_NOTIFICACION = "VALOR_PRIMERA_NOTIFICACION";
    private static final String REPETIR_NOTIFICACION = "REPETIR_NOTIFICACION";
    private static final String INTERVALO_NOTIFICACION = "INTERVALO_NOTIFICACION";
    private static final String NOTIFICACION_ACTIVA = "NOTIFICACION_ACTIVA";
    private static final String FECHA_ACTIVACION_NOTIFICACION = "FECHA_ACTIVACION_NOTIFICACION";
    private static final String NOMBRE = "NOMBRE";
    private static final String CORREOS_NOTIFICACION = "CORREOS_NOTIFICACION";
    private static final String OBSERVACIONES_NOTIFICACION = "OBSERVACIONES_NOTIFICACION";
    private static final String FK_USUARIO = "FK_USUARIO";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final String EN_PROCESAMIENTO = "EN_PROCESAMIENTO";
    private static final String CANTIDAD_NOTIFICACIONES = "CANTIDAD_NOTIFICACIONES";
    
    private static final int VALOR_POSITIVO = 1;
    private static final int VALOR_CERO = 0;
    
    private static final Object[] PARAMETROS_INSERT_UPDATE = { FK_TIPO_EVENTO_MANTENIMIENTO, VALOR_PRIMERA_NOTIFICACION,
        REPETIR_NOTIFICACION, INTERVALO_NOTIFICACION, NOTIFICACION_ACTIVA, FECHA_ACTIVACION_NOTIFICACION,
        CORREOS_NOTIFICACION, OBSERVACIONES_NOTIFICACION, FK_USUARIO, ESTADO, MODIFICACION_LOCAL,  
        EN_PROCESAMIENTO, CANTIDAD_NOTIFICACIONES, NOMBRE };
    
    private static void asignarParametros(PreparedStatement ps, EntidadConfigurable entidadConfigurable, boolean asignarPk)
            throws SQLException, Exception {
        
        if (entidadConfigurable instanceof Mantenimiento == false) {
            throw new Exception();
        }
        
        Mantenimiento entidadDefinida = (Mantenimiento) entidadConfigurable;
        int pos = 1;
        
        ps.setLong(pos++, entidadDefinida.getFkTipoEventoMantenimiento());
        ps.setInt(pos++, entidadDefinida.getValorPrimeraNotificacion());
        ps.setInt(pos++, entidadDefinida.getRepetirNotificacion());
        ps.setInt(pos++, entidadDefinida.getIntervaloRepeticion());
        ps.setInt(pos++, entidadDefinida.getNotificacionActiva());
        ps.setDate(pos++, new java.sql.Date(entidadDefinida.getFechaActivacionNotificacion().getTime()));
        ps.setString(pos++, entidadDefinida.getCorreosNotificacion());
        ps.setString(pos++, entidadDefinida.getObservacionesNotificacion());
        ps.setInt(pos++, entidadDefinida.getFkUsuario());
        ps.setInt(pos++, entidadDefinida.getEstado());
        ps.setInt(pos++, VALOR_POSITIVO);
        ps.setInt(pos++, entidadDefinida.getEnProcesamiento());
        ps.setInt(pos++, entidadDefinida.getCantidadNotificaciones());
        ps.setString(pos++, entidadDefinida.getNombre());

        if (asignarPk) {
            ps.setLong(pos++, entidadDefinida.getId());
        }
    }
    
    private static Mantenimiento asignarValoresResultSet(ResultSet rs) {
        Mantenimiento entidadDefinida = new Mantenimiento();

        try {
            entidadDefinida.setId(rs.getLong(PRIMARY_KEY));
            entidadDefinida.setFkTipoEventoMantenimiento(rs.getLong(FK_TIPO_EVENTO_MANTENIMIENTO));
            entidadDefinida.setValorPrimeraNotificacion(rs.getInt(VALOR_PRIMERA_NOTIFICACION));
            entidadDefinida.setRepetirNotificacion(rs.getInt(REPETIR_NOTIFICACION));
            entidadDefinida.setIntervaloRepeticion(rs.getInt(INTERVALO_NOTIFICACION));
            entidadDefinida.setNotificacionActiva(rs.getInt(NOTIFICACION_ACTIVA));
            entidadDefinida.setFechaActivacionNotificacion(rs.getDate(FECHA_ACTIVACION_NOTIFICACION));
            entidadDefinida.setCorreosNotificacion(rs.getString(CORREOS_NOTIFICACION));
            entidadDefinida.setObservacionesNotificacion(rs.getString(OBSERVACIONES_NOTIFICACION));
            entidadDefinida.setFkUsuario(rs.getInt(FK_USUARIO));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
            entidadDefinida.setEnProcesamiento(rs.getInt(EN_PROCESAMIENTO));
            entidadDefinida.setCantidadNotificaciones(rs.getInt(CANTIDAD_NOTIFICACIONES));
            entidadDefinida.setnombre(rs.getString(NOMBRE));
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
        query.append(String.format(" %s = ?, %s = ?, %s = ? ", ESTADO, NOTIFICACION_ACTIVA, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PRIMARY_KEY));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, entidadConfigurable.getEstado());
            ps.setInt(2, entidadConfigurable.getEstado());
            ps.setInt(3, VALOR_POSITIVO);
            ps.setLong(4, entidadConfigurable.getId());
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

    public static Mantenimiento selectByOne(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Mantenimiento entidadDefinida;

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT mantenimiento.*, tipoEventoMantenimiento.nombre as nombreMantenimiento ");
            query.append(String.format("FROM %s mantenimiento ", TABLA));
            query.append("INNER JOIN tbl_tipo_evento_mantenimiento tipoEventoMantenimiento ON ");
            query.append("mantenimiento.fk_tipo_evento_mantenimiento = tipoEventoMantenimiento.pk_tipo_evento_mantenimiento ");
            query.append(String.format("WHERE %s = ?", PRIMARY_KEY));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, entidadConfigurable.getId());
            rs = ps.executeQuery();
            entidadDefinida = new Mantenimiento();

            if (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs);
                entidadDefinida.setNombreMantenimiento(rs.getString("nombreMantenimiento"));
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
    
    public static Mantenimiento selectByOne(long pkEntidadConfigurable) {
        EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
        entidadConfigurable.setId(pkEntidadConfigurable);
        
        return selectByOne(entidadConfigurable);
    }
    
    public static List<Mantenimiento> select() {
        
        if (Session.getUsuarioSesion() == null) {
            return new ArrayList<>();
        }
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Mantenimiento> resultado = new ArrayList<>();
        Mantenimiento entidadDefinida;
        StringBuilder query = new StringBuilder();
        query.append("SELECT mantenimiento.*, tipoEventoMantenimiento.NOMBRE AS NOMBRE_TIPO_EVENTO_MANTENIMIENTO, ");
        query.append("tipoEventoMantenimiento.UNIDAD_MEDIDA ");
        query.append(String.format("FROM %s mantenimiento ", TABLA));
        query.append("INNER JOIN tbl_tipo_evento_mantenimiento tipoEventoMantenimiento ON ");
        query.append(String.format("mantenimiento.%s = tipoEventoMantenimiento.PK_TIPO_EVENTO_MANTENIMIENTO ", 
                FK_TIPO_EVENTO_MANTENIMIENTO));
        query.append(String.format("WHERE mantenimiento.%1$s = ? ", FK_USUARIO));        
        
        try {
            ps = con.prepareStatement(query.toString());
            ps.setInt(1, Session.getUsuarioSesion().getId());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs); 
                entidadDefinida.setNombreMantenimiento(rs.getString("NOMBRE_TIPO_EVENTO_MANTENIMIENTO"));
                entidadDefinida.setUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
                String[] correos = entidadDefinida.getCorreosNotificacion().split(",");
                StringBuilder correosDetinatarios = new StringBuilder();
                int correosSize = correos.length;
                
                for (int i = 0; i < correosSize; i++) {
                    correosDetinatarios.append(correos[i]).append("\t");
                }
                
                entidadDefinida.setCorreosDisplay(correosDetinatarios.toString());
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
    
    public static List<Mantenimiento> selectTodosLosMantenimientos() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Mantenimiento> resultado = new ArrayList<>();
        Mantenimiento entidadDefinida;
        StringBuilder query = new StringBuilder();
        query.append("SELECT mantenimiento.*, tipoEventoMantenimiento.NOMBRE AS NOMBRE_TIPO_EVENTO_MANTENIMIENTO, ");
        query.append("tipoEventoMantenimiento.UNIDAD_MEDIDA ");
        query.append(String.format("FROM %s mantenimiento ", TABLA));
        query.append("INNER JOIN tbl_tipo_evento_mantenimiento tipoEventoMantenimiento ON ");
        query.append(String.format("mantenimiento.%s = tipoEventoMantenimiento.PK_TIPO_EVENTO_MANTENIMIENTO ", 
                FK_TIPO_EVENTO_MANTENIMIENTO));
        query.append("INNER JOIN tbl_usuario usuario ON mantenimiento.fk_usuario = usuario.pk_usuario ");
        query.append(String.format("WHERE mantenimiento.%1$s = %2$d ", ESTADO, VALOR_POSITIVO));
        
        try {
            ps = con.prepareStatement(query.toString());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs);
                entidadDefinida.setNombreMantenimiento(rs.getString("NOMBRE_TIPO_EVENTO_MANTENIMIENTO"));
                entidadDefinida.setUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
                String[] correos = entidadDefinida.getCorreosNotificacion().split(",");
                StringBuilder correosDetinatarios = new StringBuilder();
                int correosSize = correos.length;
                
                for (int i = 0; i < correosSize; i++) {
                    correosDetinatarios.append(correos[i]).append("\t");
                }
                
                entidadDefinida.setCorreosDisplay(correosDetinatarios.toString());
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
    
    public static boolean updateCampoEnProcesamiento(Mantenimiento mantenimiento) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ? ", EN_PROCESAMIENTO));
        query.append(String.format(" WHERE %s = ?", PRIMARY_KEY));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, mantenimiento.getEnProcesamiento());
            ps.setLong(2, mantenimiento.getId());
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
