/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.PuntoRuta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class PuntoRutaBD {
    
    // Inserta registros (puntos) para una ruta en especifico.
    public static boolean insert (String sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement(sql);
            
            ps.executeUpdate();            
            return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
            e.printStackTrace();            
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Desactiva, inserta y activa nuevos puntos para una ruta en especifico.
    public static boolean insertAndDelete (String idRuta, String sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        
        // Asigna instruccion SQL para inactivar ruta
        String sql_del = "UPDATE tbl_ruta_punto SET ESTADO = 0 WHERE FK_RUTA = ?";
        // Asigna instruccion que actualza estado creacion de la ruta
        String sql_upd = "UPDATE tbl_ruta SET ESTADO_CREACION = 2 WHERE PK_RUTA = ? AND ESTADO = 1";
        // En cadena sql viene especificada instruccion SQL para insercion de puntos        
        
        try {
            con.setAutoCommit(false);
            
            ps1 = con.prepareStatement(sql_del);
            ps1.setInt(1, Integer.parseInt(idRuta));
            ps1.executeUpdate();
            System.out.println("> ELIMINACION RUTA_PUNTO [OK]");
            
            ps2 = con.prepareStatement(sql);
            ps2.executeUpdate();
            System.out.println("> CREACION RUTA_PUNTO [OK]");
            
            ps3 = con.prepareStatement(sql_upd);
            ps3.setInt(1, Integer.parseInt(idRuta));
            ps3.executeUpdate();
            
            System.out.println("> CAMBIO ESTADO_INTERNO DE RUTA [OK]");
            
            con.commit();
            return true;
            
        } catch (SQLException e) {
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
            UtilBD.closePreparedStatement(ps1);
            UtilBD.closePreparedStatement(ps2);
            UtilBD.closePreparedStatement(ps3);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Actualiza valores de punto de una ruta en especifico.
    public static boolean update (ArrayList<String> listaSQL) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();        
        Statement st = null;
        
        try {
            con.setAutoCommit(false);
    
            int n = 0;            
            for (String sql : listaSQL) {
                st = con.createStatement();
                n += st.executeUpdate(sql);
            }
            
            if (n == listaSQL.size()) {                
                con.commit();
                return true;
            } else {
                con.rollback();
            }
            
        } catch (SQLException e) {
            System.err.println(e);
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
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta valores de punto de una ruta en especifico.
    public static ArrayList<PuntoRuta> selectByRuta_edit (int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_ruta_punto AS rp"
                        + " WHERE rp.FK_RUTA = ? AND rp.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            rs = ps.executeQuery();
            
            ArrayList<PuntoRuta> lst_pr = new ArrayList<PuntoRuta>();
            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();
                pr.setId(rs.getInt("PK_RUTA_PUNTO"));
                pr.setIdPunto(rs.getInt("FK_PUNTO"));
                pr.setIdRuta(rs.getInt("FK_RUTA"));
                pr.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                pr.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));
                pr.setTiempoValle(rs.getInt("TIEMPO_VALLE"));
                pr.setTiempoPico(rs.getInt("TIEMPO_PICO"));
                pr.setValorPunto(rs.getInt("VALOR_PUNTO"));
                pr.setTipo(rs.getInt("TIPO"));
                lst_pr.add(pr);
            }
            return lst_pr;
            
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
    // Ruta debe existir y estar activa 
    // Consulta valores de punto de una ruta en especifico.
    public static ArrayList<PuntoRuta> selectByRuta (int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " rp.PK_RUTA_PUNTO,"
                        + " rp.FK_PUNTO,"
                        + " rp.FK_RUTA,"
                        + " r.NOMBRE AS NOMBRE_RUTA,"
                        + " p.NOMBRE AS NOMBRE_PUNTO,"
                        + " rp.PROMEDIO_MINUTOS,"
                        + " rp.HOLGURA_MINUTOS,"
                        + " rp.VALOR_PUNTO,"
                        + " rp.TIPO"
                        + " FROM tbl_ruta_punto AS rp"
                        + " LEFT JOIN tbl_ruta AS r ON"
                        + "   rp.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                        + " INNER JOIN tbl_punto AS p ON"
                        + "   rp.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                        + " WHERE rp.FK_RUTA = ? AND"
                        + " rp.FK_PUNTO != 100 AND rp.FK_PUNTO != 101 AND rp.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            
            rs = ps.executeQuery();
            
            ArrayList<PuntoRuta> lstpr = new ArrayList<PuntoRuta>();
            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();
                pr.setId(rs.getInt("PK_RUTA_PUNTO"));
                pr.setIdPunto(rs.getInt("FK_PUNTO"));
                pr.setIdRuta(rs.getInt("FK_RUTA"));
                pr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pr.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                pr.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                pr.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));
                pr.setValorPunto(rs.getInt("VALOR_PUNTO"));
                pr.setTipo(rs.getInt("TIPO"));
                
                if (pr.getNombreRuta() == null)
                    pr.setNombreRuta("NA");
                
                lstpr.add(pr);
            }
            return lstpr;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta cantidad de puntos por ruta,
    // y determina la cantidad minima (order 0) o
    // maxima (order 1) establecida.
    public static int comparePuntosRuta (int order) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT *," 
            + " count(rp.FK_RUTA) AS CANTIDAD_PUNTOS"
            + " FROM tbl_ruta_punto AS rp"
            + " INNER JOIN tbl_ruta AS r on"
            + " rp.FK_RUTA = r.PK_RUTA"
            + " WHERE r.ESTADO = 1"
            + " GROUP BY rp.FK_RUTA";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            int n, n_ptos = Integer.MAX_VALUE;
            while (rs.next()) {
                n = rs.getInt("CANTIDAD_PUNTOS");
                
                switch (order) {
                    case 0: // MIN
                        n_ptos = (n < n_ptos) ? n : n_ptos;
                    case 1: // MAX                         
                        n_ptos = (n > n_ptos) ? n : n_ptos;
                    default:
                        n_ptos = n;
                }
                
            } return n_ptos;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return 0;
    }
    
    // Consulta minima de identificadores de puntos de una ruta en especifico.    
    public static ArrayList<PuntoRuta> selectIdPuntoByRuta (int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT rp.FK_PUNTO FROM tbl_ruta_punto AS rp WHERE rp.FK_RUTA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            rs = ps.executeQuery();
            
            ArrayList<PuntoRuta> lst_pr = new ArrayList<PuntoRuta>();
            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();
                pr.setIdPunto(rs.getInt("FK_PUNTO"));
                lst_pr.add(pr);
            } 
            return lst_pr;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } 
        return null;
    }
    
    // Consulta registros de punto para ruta en especifico.
    public static ArrayList<PuntoRuta> selectPuntoRuta_despacho (int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = 
            "SELECT"
                + " rp.PK_RUTA_PUNTO,"
                + " rp.FK_RUTA AS PK_RUTA,"
                + " rp.FK_PUNTO AS PK_PUNTO,"
                
                + " IF(rp.TIPO = 2,"	
                + "     p.CODIGO_PUNTO,"
                + "     b.IDENTIFICADOR_BASE) AS CODIGO_PUNTO,"                

                + " IF(rp.TIPO = 2,"
                + "     lcase(p.NOMBRE),"
                + "     lcase(b.NOMBRE)) AS NOMBRE_PUNTO,"
                + " rp.PROMEDIO_MINUTOS,"
                + " rp.HOLGURA_MINUTOS"

                + " FROM tbl_ruta_punto AS rp"
                + " LEFT JOIN tbl_punto AS p ON"
                + "     rp.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " LEFT JOIN tbl_base AS b ON"
                + "     rp.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " WHERE rp.FK_RUTA = ? AND rp.ESTADO = 1"
                + " ORDER BY rp.PK_RUTA_PUNTO ASC";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            rs = ps.executeQuery();
            
            ArrayList<PuntoRuta> lst_pr = new ArrayList<PuntoRuta>();
            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();
                
                pr.setId(rs.getInt("PK_RUTA_PUNTO"));
                pr.setIdRuta(rs.getInt("PK_RUTA"));
                pr.setIdPunto(rs.getInt("PK_PUNTO"));
                pr.setCodigoPunto(rs.getInt("CODIGO_PUNTO"));                
                pr.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                pr.setEtiquetaPunto("p" + rs.getInt("CODIGO_PUNTO"));
                pr.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                pr.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));
                
                lst_pr.add(pr);
            } 
            return lst_pr;
            
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
