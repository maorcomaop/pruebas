/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.ConteoPerimetro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class ConteoPerimetroBD {
    
    public static ArrayList<ConteoPerimetro> selectBy (int id_infreg) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_conteo_perimetro"
                    + " WHERE FK_INFORMACION_REGISTRADORA = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_infreg);
            rs = ps.executeQuery();
            
            ArrayList<ConteoPerimetro> lstcp = new ArrayList<ConteoPerimetro>();
            while (rs.next()) {
                ConteoPerimetro cp = new ConteoPerimetro();
                cp.setFechaConteo(rs.getDate("FECHA_CONTEO"));
                cp.setHoraIngreso(rs.getTime("HORA_INGRESO"));
                cp.setNumInicial(rs.getInt("NUM_INICIAL"));
                cp.setNumFinal(rs.getInt("NUM_FINAL"));
                cp.setDiferencia(rs.getInt("DIFERENCIA"));
                cp.setEntradas(rs.getInt("ENTRADAS"));
                cp.setSalidas(rs.getInt("SALIDAS"));
                //cp.setEstado(rs.getInt("ESTADO"));
                
                lstcp.add(cp);
            }
            return lstcp;
            
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
