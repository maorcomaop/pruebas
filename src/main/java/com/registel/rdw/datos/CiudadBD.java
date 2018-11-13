
package com.registel.rdw.datos;

import com.registel.rdw.logica.Ciudad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CiudadBD {
 
    public static Ciudad selectByOne(Ciudad c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT * FROM tbl_ciudad WHERE (PK_CIUDAD=?)");
        
        Ciudad ciudadEncontrada = null;
        try {
             con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            
            rs = ps.executeQuery();
            ciudadEncontrada = new Ciudad();
            if (rs.next()) {
                ciudadEncontrada.setId(rs.getInt("PK_CIUDAD"));
                ciudadEncontrada.setIddpto(rs.getInt("FK_DEPARTAMENTO"));
                ciudadEncontrada.setNombre(rs.getString("NOMBRE"));                
                return ciudadEncontrada;
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
             try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(e);
                }
                UtilBD.closeStatement(ps);
                pila_con.liberarConexion(con);
        }
        return null;
    }

}
