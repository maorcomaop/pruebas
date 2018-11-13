/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.LiquidacionGeneral;
import com.registel.rdw.logica.LiquidacionVueltas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class LiquidacionVueltasBD {

    public int insert(ArrayList<LiquidacionVueltas> entidades, int fkConductor) throws ExisteRegistroBD {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement statement = null;

        int resultado = 0;
        int filas_resultado = 0;
        SimpleDateFormat fecha_hora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        StringBuilder query = new StringBuilder("");
        StringBuilder query_conductor = new StringBuilder("");

        query.append("INSERT INTO tbl_liquidacion_vueltas ( ");
        query.append(" FK_INFORMACION_REGISTRADORA");
        query.append(" ,FK_LIQUIDACION_GENERAL");
        query.append(" ,ESTADO");
        query.append(" ,PASAJEROS_DESCUENTO");
        query.append(" ,VALOR_DESCUENTO");
        query.append(" ,MOTIVO_DESCUENTO");
        query.append(" ,FECHA_DESCUENTO");
        query.append(" ,PK_UNICA");
        query.append(" ,FECHA_MODIFICACION");
        query.append(" ) VALUES ");

        int fk_liquidacion_gen = 0;

        for (int i = 0; i < entidades.size(); i++) {
            LiquidacionVueltas entidad = entidades.get(i);

            if (i == 0) {
                fk_liquidacion_gen = entidad.getFkLiquidacionGeneral();
            }

            query.append(" ( ").append(entidad.getFkInformacionRegistradora()).append(", ");
            query.append(entidad.getFkLiquidacionGeneral()).append(", ");
            query.append(entidad.getEstado()).append(", ");
            query.append(entidad.getPasajerosDescuento()).append(", ");
            query.append(entidad.getValorDescuento()).append(", ");
            query.append("'").append(entidad.getMotivoDescuento()).append("', ");

            if (entidad.getFechaDescuento() != null) {
                query.append("'").append(fecha_hora.format(entidad.getFechaDescuento())).append("', ");
            } else {
                query.append("NULL").append(", ");
            }

            if (entidad.getPkUnica() != null) {
                query.append("'").append(entidad.getPkUnica()).append("', ");
            } else {
                query.append("NULL").append(", ");
            }

            if (entidad.getFechaModificacion() != null) {
                query.append("'").append(fecha_hora.format(entidad.getFechaModificacion())).append("') ");
            } else {
                query.append("NULL").append(" ) ");
            }

            if ((i + 1) < entidades.size()) {
                query.append(" , ");
            }
            //Se actualiza la vuelta del vehiculo, asociando el conductor correspondiente de esa vuelta
            query_conductor.append("UPDATE tbl_informacion_registradora SET FK_CONDUCTOR = ").append(fkConductor).append(" WHERE PK_INFORMACION_REGISTRADORA = ").append(entidad.getFkInformacionRegistradora()).append(" ;  ");

            try {
                statement = con.createStatement();
                filas_resultado = statement.executeUpdate(query_conductor.toString());
                if (filas_resultado > 0) {
                    resultado = 1;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                Logger.getLogger(LiquidacionVueltasBD.class.getName()).log(Level.SEVERE, null, ex);
                resultado = 0;
                break;
            }
            query_conductor.delete(0, query_conductor.length());
        }
        query.append(";");
        if (resultado == 1) {
            resultado = 0;
            try {
                statement = con.createStatement();
                filas_resultado = statement.executeUpdate(query.toString());
                if (filas_resultado > 0) {
                    resultado = 1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(LiquidacionVueltasBD.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                UtilBD.closePreparedStatement(statement);
                pila_con.liberarConexion(con);
            }
        }
        return resultado;
    }
    
    public static boolean updateEstateLq(LiquidacionGeneral c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_liquidacion_vueltas SET ESTADO=? WHERE FK_LIQUIDACION_GENERAL=? ");
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());            
            ps_update.setInt(1, c.getEstado());            
            ps_update.setInt(2, c.getId());
            int retorno = ps_update.executeUpdate();            
             con.commit();
            if (retorno > 0) {
                return true;
            } else {
                con.rollback();
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
             try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    public static boolean updateEstate(LiquidacionVueltas c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_liquidacion_vueltas SET ESTADO=? WHERE PK_LIQUIDACION_VUELTAS=? ");
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());            
            ps_update.setInt(1, c.getEstado());            
            ps_update.setInt(2, c.getId());
            int retorno = ps_update.executeUpdate();            
             con.commit();
            if (retorno > 0) {
                return true;
            } else {
                con.rollback();
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
             try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return false;
    
    }
}
