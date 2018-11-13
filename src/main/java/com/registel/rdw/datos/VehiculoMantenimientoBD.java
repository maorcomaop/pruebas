/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.controladores.Session;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.VehiculoMantenimiento;
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
 * @date 03/08/2018
 */
public class VehiculoMantenimientoBD {

    private static final String TABLA = "tbl_vehiculo_mantenimiento";
    private static final String PRIMARY_KEY = "PK_VEHICULO_MANTENIMIENTO";
    private static final String FK_VEHICULO = "FK_VEHICULO";
    private static final String FK_MANTENIMIENTO = "FK_MANTENIMIENTO";
    private static final String VALOR_INICIAL_MANTENIMIENTO = "VALOR_INICIAL_MANTENIMIENTO";
    private static final String VALOR_ACUMULADO = "VALOR_ACUMULADO";
    private static final String CANTIDAD_NOTIFICACIONES_PENDIENTES = "CANTIDAD_NOTIFICACIONES_PENDIENTES";
    private static final String YA_NOTIFICO = "YA_NOTIFICO";
    private static final String FECHA_PROXIMA_NOTIFICACION = "FECHA_PROXIMA_NOTIFICACION";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final String ELIMINADO = "ELIMINADO";

    private static final int VALOR_POSITIVO = 1;
    private static final int VALOR_CERO = 0;

    private static final Object[] PARAMETROS_INSERT_UPDATE = {FK_VEHICULO, FK_MANTENIMIENTO, VALOR_INICIAL_MANTENIMIENTO,
        VALOR_ACUMULADO, CANTIDAD_NOTIFICACIONES_PENDIENTES, YA_NOTIFICO, FECHA_PROXIMA_NOTIFICACION, ESTADO, 
        MODIFICACION_LOCAL, ELIMINADO};

    private static void asignarParametros(PreparedStatement ps, EntidadConfigurable entidadConfigurable, boolean asignarPk)
            throws SQLException, Exception {

        if (entidadConfigurable instanceof VehiculoMantenimiento == false) {
            throw new Exception();
        }

        VehiculoMantenimiento entidadDefinida = (VehiculoMantenimiento) entidadConfigurable;
        int pos = 1;

        ps.setInt(pos++, entidadDefinida.getFkVehiculo());
        ps.setLong(pos++, entidadDefinida.getFkMantenimiento());
        ps.setDouble(pos++, entidadDefinida.getValorInicialMantenimiento());
        ps.setDouble(pos++, entidadDefinida.getValorAcumulado());
        ps.setInt(pos++, entidadDefinida.getCantidadNotificacionesPendientes());
        ps.setInt(pos++, entidadDefinida.getYaNotifico());
        ps.setTimestamp(pos++, new java.sql.Timestamp(entidadDefinida.getFechaProximaNotificacion().getTime()));
        ps.setInt(pos++, entidadDefinida.getEstado());
        ps.setInt(pos++, VALOR_POSITIVO);
        ps.setInt(pos++, VALOR_CERO);

        if (asignarPk) {
            ps.setLong(pos++, entidadDefinida.getId());
        }
    }

    private static VehiculoMantenimiento asignarValoresResultSet(ResultSet rs) {
        VehiculoMantenimiento entidadDefinida = new VehiculoMantenimiento();

        try {
            entidadDefinida.setId(rs.getLong(PRIMARY_KEY));
            entidadDefinida.setFkVehiculo(rs.getInt(FK_VEHICULO));
            entidadDefinida.setFkMantenimiento(rs.getLong(FK_MANTENIMIENTO));
            entidadDefinida.setValorInicialMantenimiento(rs.getDouble(VALOR_INICIAL_MANTENIMIENTO));
            entidadDefinida.setValorAcumulado(rs.getDouble(VALOR_ACUMULADO));
            entidadDefinida.setCantidadNotificacionesPendientes(rs.getInt(CANTIDAD_NOTIFICACIONES_PENDIENTES));
            entidadDefinida.setYaNotifico(rs.getInt(YA_NOTIFICO));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
            entidadDefinida.setFechaProximaNotificacion(rs.getTimestamp(FECHA_PROXIMA_NOTIFICACION));
            entidadDefinida.setEliminado(rs.getInt(ELIMINADO));
            entidadDefinida.setNombreMantenimiento(rs.getString("nombreMantenimiento"));
            entidadDefinida.setPlaca(rs.getString("placa"));
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

    public static VehiculoMantenimiento selectByOne(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        VehiculoMantenimiento entidadDefinida;

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT vehiculoMantenimiento.* ");
            query.append(String.format("FROM %s vehiculoMantenimiento ", TABLA));
            query.append(String.format("WHERE %s = ?", PRIMARY_KEY));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, entidadConfigurable.getId());
            rs = ps.executeQuery();
            entidadDefinida = new VehiculoMantenimiento();

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

    public static VehiculoMantenimiento selectByOne(long pkEntidadConfigurable) {
        EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
        entidadConfigurable.setId(pkEntidadConfigurable);

        return selectByOne(entidadConfigurable);
    }

    public static List<VehiculoMantenimiento> select() {
        
        if (Session.getUsuarioSesion() == null) {
            return new ArrayList<>();
        }
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<VehiculoMantenimiento> resultado = new ArrayList<>();
        VehiculoMantenimiento entidadDefinida;
        StringBuilder query = new StringBuilder();

        try {
            query.append("SELECT vehiculoMantenimiento.*, ");
            query.append("mantenimiento.NOMBRE as nombreMantenimiento, vehiculo.PLACA ");
            query.append(String.format("FROM %s vehiculoMantenimiento ", TABLA));
            query.append(String.format("INNER JOIN tbl_vehiculo vehiculo ON vehiculoMantenimiento.%1$s = "
                    + "vehiculo.PK_VEHICULO ", FK_VEHICULO));
            query.append(String.format("INNER JOIN tbl_mantenimiento mantenimiento ON vehiculoMantenimiento.%1$s = "
                    + "mantenimiento.PK_MANTENIMIENTO ", FK_MANTENIMIENTO));
            query.append(String.format("WHERE vehiculoMantenimiento.%1$s = %2$d ", ELIMINADO, VALOR_CERO));
            query.append(String.format("AND mantenimiento.%1$s = %2$d AND mantenimiento.FK_USUARIO = ?", ESTADO, 
                    VALOR_POSITIVO));
            ps = con.prepareStatement(query.toString());
            ps.setInt(1, Session.getUsuarioSesion().getId());
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

    public static List<VehiculoMantenimiento> select(long fkMantenimento) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<VehiculoMantenimiento> resultado = new ArrayList<>();
        VehiculoMantenimiento entidadDefinida;
        StringBuilder query = new StringBuilder();

        try {
            query.append("SELECT vehiculoMantenimiento.*, ");
            query.append("mantenimiento.NOMBRE as nombreMantenimiento, vehiculo.PLACA ");
            query.append(String.format("FROM %s vehiculoMantenimiento ", TABLA));
            query.append(String.format("INNER JOIN tbl_vehiculo vehiculo ON vehiculoMantenimiento.%1$s = "
                    + "vehiculo.PK_VEHICULO ", FK_VEHICULO));
            query.append(String.format("INNER JOIN tbl_mantenimiento mantenimiento ON vehiculoMantenimiento.%1$s = "
                    + "mantenimiento.PK_MANTENIMIENTO ", FK_MANTENIMIENTO));
            query.append(String.format("WHERE vehiculoMantenimiento.%1$s = ? ", FK_MANTENIMIENTO));
            query.append(String.format("AND vehiculoMantenimiento.%1$s = %2$d ", ELIMINADO, VALOR_CERO));
            ps = con.prepareStatement(query.toString());
            ps.setLong(1, fkMantenimento);
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

    public static boolean delete(EntidadConfigurable entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL, ELIMINADO));
        query.append(String.format(" WHERE %s = ?", PRIMARY_KEY));

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, entidadConfigurable.getEstado());
            ps.setInt(2, VALOR_POSITIVO);
            ps.setInt(3, VALOR_POSITIVO);
            ps.setLong(4, entidadConfigurable.getId());
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
    
    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de verificar que si un vehículo ya fue asociado a un mantenimiento.
     * Este método busca todos los registros que no hayan sido eliminados.
     * @param vehiculoMantenimiento
     * @return 
     */
    public static VehiculoMantenimiento exits(VehiculoMantenimiento vehiculoMantenimiento) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT vehiculoMantenimiento.* ");
            query.append(String.format("FROM %s vehiculoMantenimiento ", TABLA));
            query.append(String.format("WHERE %1$s = ? AND %2$s = ? AND %3$s = ? AND %4$s <> ?", 
                    FK_VEHICULO, FK_MANTENIMIENTO, ELIMINADO, PRIMARY_KEY));
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, vehiculoMantenimiento.getFkVehiculo());
            ps.setLong(2, vehiculoMantenimiento.getFkMantenimiento());
            ps.setInt(3, VALOR_CERO);
            ps.setLong(4, vehiculoMantenimiento.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                VehiculoMantenimiento entidadDefinida = asignarValoresResultSet(rs);
                
                return entidadDefinida;
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

        return null;
    }
    
}
