/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.PuntoControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class PuntoControlBD {
    
    // Consulta puntos de control asociados a un identificador
    // de registro informacion registradora especifico.
    public static ArrayList<PuntoControl> selectBy (int idir) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT *"                    
                    + " FROM tbl_punto_control AS pc"
                    + " INNER JOIN tbl_punto AS p ON"
                    + "     pc.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                    + " WHERE pc.FK_INFO_REGIS = ?"
                    + " ORDER BY HORA_PTO_CONTROL ASC";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idir);
            rs = ps.executeQuery();
            
            ArrayList<PuntoControl> lstpc = new ArrayList<PuntoControl>();
            while (rs.next()) {
                PuntoControl pc = new PuntoControl();
                
                pc.setId(rs.getInt("PK_PUNTO_CONTROL"));
                pc.setIdInforeg(rs.getInt("FK_INFO_REGIS"));
                pc.setIdPunto(rs.getInt("FK_PUNTO"));
                pc.setFechaPuntoControl(rs.getDate("FECHA_PTO_CONTROL"));
                pc.setHoraPuntoControl(rs.getTime("HORA_PTO_CONTROL"));
                pc.setNumeracion(rs.getInt("NUMERACION"));
                pc.setEntradas(rs.getInt("ENTRADAS"));
                pc.setSalidas(rs.getInt("SALIDAS"));
                
                pc.setCodigo(rs.getInt("CODIGO_PUNTO"));
                pc.setNombre(rs.getString("NOMBRE"));
                
                lstpc.add(pc);
            }
            return lstpc;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }    
        return null;
    }
    
    // Para reporte : ruta por vehiculo
    // Consulta puntos de control asociados a un identificador
    // de registro informacion registradora especifico.
    public static ArrayList<PuntoControl> selectBy (int idir, String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT *,"
                    + " time_to_sec(HORA_PTO_CONTROL) AS HORA_PTO_CONTROL_SEC" 
                    + " FROM tbl_punto_control AS pc"
                    + " INNER JOIN tbl_punto AS p ON"
                    + "  pc.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                    + " WHERE pc.FK_INFO_REGIS = ?";                 
                    /* + " AND pc.FECHA_PTO_CONTROL = ?"; */                     
                    /* ORDER BY HORA_PTO_CONTROL ASC"; */
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idir);
            //ps.setString(2, fecha);
            rs = ps.executeQuery();
            
            ArrayList<PuntoControl> lstpc = new ArrayList<PuntoControl>();
            while (rs.next()) {
                PuntoControl pc = new PuntoControl();
                pc.setId(rs.getInt("PK_PUNTO_CONTROL"));
                pc.setIdInforeg(rs.getInt("FK_INFO_REGIS"));
                pc.setIdPunto(rs.getInt("FK_PUNTO"));
                pc.setFechaPuntoControl(rs.getDate("FECHA_PTO_CONTROL"));
                pc.setHoraPuntoControl(rs.getTime("HORA_PTO_CONTROL"));
                pc.setHoraPuntoControlSec(rs.getLong("HORA_PTO_CONTROL_SEC"));
                pc.setNumeracion(rs.getInt("NUMERACION"));
                pc.setEntradas(rs.getInt("ENTRADAS"));
                pc.setSalidas(rs.getInt("SALIDAS"));
                
                pc.setCodigo(rs.getInt("CODIGO_PUNTO"));
                pc.setNombre(rs.getString("NOMBRE"));                
                
                lstpc.add(pc);
            }
            
            return lstpc;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }    
        return null;
    }
}
