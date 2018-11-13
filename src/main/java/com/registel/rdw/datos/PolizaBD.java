/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Poliza;
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
 * @date 13/07/2018
 */
public class PolizaBD {
    
    private static final String TABLA = "tbl_poliza";
    private static final String PK_POLIZA = "PK_POLIZA";
    private static final String NOMBRE = "NOMBRE";
    private static final String FK_ASEGURADORA = "FK_ASEGURADORA";
    private static final String AMPAROS_COBERTURAS = "AMPAROS_COBERTURAS";
    private static final String ESTADO = "ESTADO";
    private static final String MODIFICACION_LOCAL = "MODIFICACION_LOCAL";
    private static final String FK_TIPO_POLIZA = "FK_TIPO_POLIZA";
    private static final int VALOR_POSITIVO = 1;
    
    
    private static void asignarParametros(PreparedStatement ps, EntidadConfigurable entidadConfigurable, boolean asignarPk)
            throws SQLException, Exception {

        if (entidadConfigurable instanceof Poliza == false) {
            throw new Exception();
        }

        Poliza entidadDefinida = (Poliza) entidadConfigurable;
        ps.setString(1, entidadDefinida.getNombre());
        ps.setLong(2, entidadDefinida.getFkAseguradora());
        ps.setString(3, entidadDefinida.getAmparosCoberturas());
        ps.setInt(4, entidadDefinida.getEstado());
        ps.setInt(5, VALOR_POSITIVO);
        ps.setLong(6, entidadDefinida.getFkTipoPoliza());

        if (asignarPk) {
            ps.setLong(7, entidadDefinida.getId());
        }
    }
    
    
    private static Poliza asignarValoresResultSet(ResultSet rs) {
        Poliza entidadDefinida = new Poliza();

        try {
            entidadDefinida.setId(rs.getLong(PK_POLIZA));
            entidadDefinida.setNombre(rs.getString(NOMBRE));
            entidadDefinida.setFkAseguradora(rs.getLong(FK_ASEGURADORA));
            entidadDefinida.setAmparosCoberturas(rs.getString(AMPAROS_COBERTURAS));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
            entidadDefinida.setFkTipoPoliza(rs.getLong(FK_TIPO_POLIZA));
            entidadDefinida.setNombreAseguradora(rs.getString("nombreAseguradora"));
            entidadDefinida.setNombreTipoPoliza(rs.getString("nombreTipoPoliza"));
        } catch (SQLException e) {
            Logger.getLogger(MantenimientoBD.class.getName()).log(Level.SEVERE, null, e);
        }

        return entidadDefinida;
    }
    
    
    public static boolean insert(Poliza poliza) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s ", TABLA));
        query.append(String.format(" (%1$s, %2$s, %3$s, %4$s, %5$s, %6$s) ", NOMBRE, FK_ASEGURADORA, 
                AMPAROS_COBERTURAS, ESTADO, MODIFICACION_LOCAL, FK_TIPO_POLIZA));
        query.append(" VALUES(?, ?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, poliza, false);
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
    
    public static boolean update(Poliza poliza) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format("%1$s = ?, %2$s = ?, %3$s = ?, %4$s = ?, %5$s = ?, %6$s = ? ", 
                NOMBRE, FK_ASEGURADORA, AMPAROS_COBERTURAS, ESTADO, MODIFICACION_LOCAL, FK_TIPO_POLIZA));
        query.append(String.format(" WHERE %1$s = ?", PK_POLIZA));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            asignarParametros(ps, poliza, true);
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
    
    public static boolean updateEstado(Poliza poliza) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", TABLA));
        query.append(String.format(" %s = ?, %s = ? ", ESTADO, MODIFICACION_LOCAL));
        query.append(String.format(" WHERE %s = ?", PK_POLIZA));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, poliza.getEstado());
            ps.setInt(2, VALOR_POSITIVO);
            ps.setLong(3, poliza.getId());
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

    public static Poliza selectByOne(Poliza poliza) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Poliza polizaEncontrada;
        
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT poliza.*, aseguradora.nombre as nombreAseguradora, tipoPoliza.nombre as nombreTipoPoliza ");
            query.append(String.format("FROM %1$s poliza ", TABLA));
            query.append("INNER JOIN tbl_aseguradora aseguradora ON poliza.FK_ASEGURADORA = aseguradora.PK_ASEGURADORA ");
            query.append("INNER JOIN tbl_tipo_poliza tipoPoliza ON poliza.FK_TIPO_POLIZA = tipoPoliza.PK_TIPO_POLIZA ");
            query.append(String.format("WHERE poliza.%1$s = ?", PK_POLIZA));
            ps = connection.prepareStatement(query.toString());
            ps.setLong(1, poliza.getId());
            rs = ps.executeQuery();
            polizaEncontrada = new Poliza();

            if (rs.next()) {
                polizaEncontrada = asignarValoresResultSet(rs);
            }
            
            return polizaEncontrada;
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
    
    public static Poliza selectByOne(long pkPoliza) {
        Poliza poliza = new Poliza();
        poliza.setId(pkPoliza);
        
        return selectByOne(poliza);
    }
    
    
    public static List<Poliza> select() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Poliza> resultado = new ArrayList<>();
        Poliza poliza;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT poliza.*, aseguradora.nombre as nombreAseguradora, tipoPoliza.nombre as nombreTipoPoliza ");
        sql.append(String.format("FROM %1$s poliza ", TABLA));
        sql.append("INNER JOIN tbl_aseguradora aseguradora ON poliza.FK_ASEGURADORA = aseguradora.PK_ASEGURADORA ");
        sql.append("INNER JOIN tbl_tipo_poliza tipoPoliza ON poliza.FK_TIPO_POLIZA = tipoPoliza.PK_TIPO_POLIZA ");
        
        try {
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();           
            
            while (rs.next()) {
                poliza = asignarValoresResultSet(rs);
                resultado.add(poliza);
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
