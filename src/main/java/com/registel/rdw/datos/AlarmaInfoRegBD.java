/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.AlarmaInfoReg;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class AlarmaInfoRegBD {
        
    public static ArrayList<AlarmaInfoReg> selectBy (int id_infreg) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT *"                    
                    + " FROM tbl_alarma_info_regis AS air"
                    + " INNER JOIN tbl_alarma AS a ON"
                    + "     air.FK_ALARMA = a.PK_ALARMA"
                    + " WHERE air.FK_INFORMACION_REGISTRADORA = ?"
                    + " AND air.ESTADO = 1"
                    + " ORDER BY HORA_ALARMA ASC";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_infreg);
            rs = ps.executeQuery();
            
            ArrayList<AlarmaInfoReg> lstalm = new ArrayList<AlarmaInfoReg>();
            while (rs.next()) {
                AlarmaInfoReg air = new AlarmaInfoReg();
                air.setId(rs.getInt("PK_AIR"));
                air.setIdAlarma(rs.getInt("FK_ALARMA"));
                air.setIdInfreg(rs.getInt("FK_INFORMACION_REGISTRADORA"));
                air.setFechaAlarma(rs.getDate("FECHA_ALARMA"));
                air.setHoraAlarma(rs.getTime("HORA_ALARMA"));
                air.setCantidad(rs.getInt("CANTIDAD_ALARMA"));
                air.setEstado(rs.getInt("ESTADO"));
                
                air.setNombre(rs.getString("NOMBRE"));
                air.setCodigo(rs.getInt("CODIGO_ALARMA"));                                
                
                lstalm.add(air);
            }
            return lstalm;
            
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
