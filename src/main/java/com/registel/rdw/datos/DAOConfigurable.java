/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.utils.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Richard Mejia
 * @date 12/09/2018
 * DAO genérico que contiene el CRUD de las entidades del sistema.
 */
public abstract class DAOConfigurable {
    
    protected static final String ESTADO = "ESTADO";

    /**
     * @author Richard Mejia
     * @date 19/09/2018
     * Método encargado de realizar los INSERTS a las tablas de las base de datos.
     * @param entidadConfigurable Entidad que representa a una tabla de la base de datos
     * @param tabla Nombre de la tabla de la base de datos
     * @param length Indica la cantidad de parámetros que se deben agregar en el insert
     * @param array Arreglo con los nombres de las columnas de la tabla de base de datos
     * @param clase Es la clase que realiza la invocación de este método
     * @return 
     */
    protected static boolean insert(EntidadConfigurable entidadConfigurable, String tabla, int length, 
            Object[] array, Class clase) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", tabla));
        query.append(String.format(StringUtils.generarCadena("%s", "(", ")", length), array));
        query.append(String.format(StringUtils.generarCadena("?", "VALUES(", ")", length), array));

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            Method asignarParametrosClase = clase.getDeclaredMethod("asignarParametros", PreparedStatement.class, 
                    EntidadConfigurable.class, boolean.class);
            asignarParametrosClase.invoke(null, ps, entidadConfigurable, false);
            int insert = ps.executeUpdate();
            connection.commit();
            
            if (insert > 0) {
                ResultSet claveGenerada = ps.getGeneratedKeys();
                
                if (claveGenerada.next()) {
                    entidadConfigurable.setId(claveGenerada.getLong(1));
                }
                
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | 
                SecurityException | InvocationTargetException sqlEx) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);
            }

            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return false;
    }    
    
    /**
     * @author Richard Mejia
     * @date 19/09/2018
     * Método que se encarga de realizar los UPDATES en las tablas de la base de datos.
     * @param entidadConfigurable Entidad que representa a una tabla de la base de datos
     * @param tabla Nombre de la tabla de la base de datos
     * @param clavePrimaria Nombre de la clave primaria de la tabla de base de datos
     * @param length Indica la cantidad de parámetros que se deben agregar en el update
     * @param array Arreglo con los nombres de las columnas de la tabla de base de datos
     * @param clase Es la clase que realiza la invocación de este método
     * @return 
     */
    protected static boolean update(EntidadConfigurable entidadConfigurable, String tabla, String clavePrimaria, 
            int length, Object[] array, Class clase) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", tabla));
        query.append(String.format(StringUtils.generarCadena("%s = ?", " ", " ", length), array));
        query.append(String.format(" WHERE %s = ?", clavePrimaria));

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            Method asignarParametrosClase = clase.getDeclaredMethod("asignarParametros", PreparedStatement.class, 
                    EntidadConfigurable.class, boolean.class);
            asignarParametrosClase.invoke(null, ps, entidadConfigurable, true);
            int pk = ps.executeUpdate();
            connection.commit();

            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | 
                SecurityException | InvocationTargetException sqlEx) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);
            }

            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return false;
    }

    /**
     * @author Richard Mejia
     * @date 19/09/2018
     * Método encargado de realizar las ACTUALIZACIONES DE ESTADO de las tablas de la base de datos
     * @param entidadConfigurable Entidad que representa a una tabla de la base de datos
     * @param tabla Nombre de la tabla de la base de datos
     * @param clavePrimaria Nombre de la clave primaria de la tabla de base de datos
     * @return 
     */
    protected static boolean updateEstado(EntidadConfigurable entidadConfigurable, String tabla, String clavePrimaria) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", tabla));
        query.append(String.format(" %s = ? ", ESTADO));
        query.append(String.format(" WHERE %s = ?", clavePrimaria));
        int pos = 1;
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            
            ps.setInt(pos++, entidadConfigurable.getEstado());
            ps.setLong(pos++, entidadConfigurable.getId());
            int pk = ps.executeUpdate();
            connection.commit();

            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlEx) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ex);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);
            }

            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return false;
    }
    
    /**
     * @author Richard Mejia
     * @date 19/09/2018
     * Método encargado de seleccionar un registro de las tablas de la base de datos cuya clave primaria
     * sea igual al id de la entidad configurable que llega como parametro.
     * @param entidadConfigurable Entidad que representa a una tabla de la base de datos
     * @param tabla Nombre de la tabla de la base de datos
     * @param clavePrimaria Nombre de la clave primaria de la tabla de base de datos
     * @param clase Es la clase que realiza la invocación de este método
     * @return 
     */
    protected static EntidadConfigurable selectByOne(EntidadConfigurable entidadConfigurable, String tabla, 
            String clavePrimaria, Class clase) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        EntidadConfigurable entidadDefinida;
        
        try {
            StringBuilder query = new StringBuilder();
            query.append(String.format("SELECT * FROM %1$s WHERE %2$s = ?", tabla, clavePrimaria));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, entidadConfigurable.getId());
            rs = ps.executeQuery();
            entidadDefinida = new EntidadConfigurable();
            Method asignarValoresResultSetClase = clase.getDeclaredMethod("asignarValoresResultSet", ResultSet.class);

            if (rs.next()) {
                entidadDefinida = (EntidadConfigurable) asignarValoresResultSetClase.invoke(null, rs);
            }

            return entidadDefinida;
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | 
                SecurityException | InvocationTargetException sqlEx) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);
            }

            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return null;
    }

    /**
     * @author Richard Mejia
     * @date 19/09/2018
     * Método encargado de seleccionar un registro de la base de datos cuya clave primaria sea igual al
     * id que llega como parámetro.
     * @param pkEntidadConfigurable Id del registro que se quiere consultar
     * @param tabla Nombre de la tabla de la base de datos
     * @param clavePrimaria Nombre de la clave primaria de la tabla
     * @param clase Es la clase que realiza la invocación de este método
     * @return 
     */
    protected static EntidadConfigurable selectByOne(long pkEntidadConfigurable, String tabla, String clavePrimaria, 
            Class clase) {
        EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
        entidadConfigurable.setId(pkEntidadConfigurable);

        return selectByOne(entidadConfigurable, tabla, clavePrimaria, clase);
    }

    /**
     * @author Richard Mejia
     * @date 19/09/2018
     * Método encargado de consultar todos los registros de una tabla de la base de datos.
     * @param tabla Nombre de la tabla que se va a consultar
     * @param clase Es la clase que realiza la invocación de este método
     * @return 
     */
    protected static List<EntidadConfigurable> select(String tabla, Class clase) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EntidadConfigurable> resultado = new ArrayList<>();
        EntidadConfigurable entidadDefinida;
        StringBuilder query = new StringBuilder();

        try {
            query.append(String.format("SELECT * from %1$s", tabla));
            ps = con.prepareStatement(query.toString());
            rs = ps.executeQuery();
            Method asignarValoresResultSetClase = clase.getDeclaredMethod("asignarValoresResultSet", ResultSet.class);

            while (rs.next()) {
                entidadDefinida = (EntidadConfigurable) asignarValoresResultSetClase.invoke(null, rs);
                resultado.add(entidadDefinida);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ex);

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
                IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }

        return resultado;
    }
    
}
