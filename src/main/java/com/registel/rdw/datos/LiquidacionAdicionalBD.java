/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.LiquidacionAdicional;
import com.registel.rdw.logica.LiquidacionGeneral;
import com.registel.rdw.logica.LiquidacionVueltas;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class LiquidacionAdicionalBD {

    public static int insert(ArrayList<LiquidacionAdicional> entidades) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement statement = null;
        int resultado = 0;

        StringBuilder query = new StringBuilder("");
        query.append("INSERT INTO tbl_liquidacion_adicional ( ");
        query.append(" FK_LIQUIDACION_GENERAL");
        query.append(" ,FK_CATEGORIAS");
        query.append(" ,ESTADO");
        query.append(" ,VALOR_DESCUENTO");
        query.append(" ,MOTIVO_DESCUENTO");
        query.append(" ,FECHA_DESCUENTO");
        query.append(" ,FECHA_MODIFICACION");
        query.append(" ,PK_UNICA");
        query.append(" ,CANTIDAD_PASAJEROS_DESCONTADOS");
        query.append(" ) VALUES ");

        LiquidacionAdicional entidad;
        for (int i = 0; i < entidades.size(); i++) {
            entidad = entidades.get(i);

            query.append(" ( ").append(entidad.getFkLiquidacionGeneral()).append(", ");
            query.append(entidad.getFkCategorias()).append(", ");
            query.append(entidad.getEstado()).append(", ");
            query.append(entidad.getValorDescuento()).append(", ");
            query.append("'").append(entidad.getMotivoDescuento()).append("'" + ", ");

            if (entidad.getFechaDescuento() != null) {
                query.append("'").append(UtilBD.setFormatDate(entidad.getFechaDescuento())).append("', ");
            } else {
                query.append("NULL").append(", ");
            }

            if (entidad.getFechaModificacion() != null) {
                query.append("'").append(UtilBD.setFormatDate(entidad.getFechaModificacion())).append("', ");
            } else {
                query.append("NULL").append(" , ");
            }

            if (entidad.getPkUnica() != null) {
                query.append("'").append(entidad.getPkUnica()).append("', ");
            } else {
                query.append("NULL").append(") ");
            }

            query.append(entidad.getCantidadPasajerosDescontados()).append(") ");

            if ((i + 1) < entidades.size()) {
                query.append(" , ");
            }
        }
        query.append(";");
        try {
            statement = con.createStatement();
            resultado = statement.executeUpdate(query.toString());
        } catch (SQLException ex) {
            Logger.getLogger(LiquidacionVueltasBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            UtilBD.closePreparedStatement(statement);
            pila_con.liberarConexion(con);
            
        }
        return resultado;
    }
    
    public static boolean updateEstateLq(LiquidacionGeneral c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_liquidacion_adicional SET ESTADO=? WHERE FK_LIQUIDACION_GENERAL=? ");
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
    
    public static boolean updateEstate(LiquidacionAdicional c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_liquidacion_adicional SET ESTADO=? WHERE PK_LIQUIDACION_ADICIONAL=? ");
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
