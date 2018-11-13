
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.logica.GpsMarca;
import com.registel.rdw.logica.GpsRegistrado;
import java.sql.Statement;


public class GpsMarcaBD {
    
   public static boolean exist (GpsMarca p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_gps_marca WHERE (id = ?)";
        
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, p.getId());
            rs = ps.executeQuery();
            
            if (rs.next())
                return true;
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }        
    public static int insertOneBandGPS (GpsMarca p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idGps=0;
        sql.append("INSERT INTO tbl_gps_marca (nombre, descripcion) VALUES (?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                                
                ps.setString(2, p.getNombre());                
                ps.setString(3, p.getDescripcion());                
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {                
                    idGps = rs.getInt(1);
                }
                con.commit();
                if (retorno > 0) {
                    return idGps;
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
        }
        return 0;
    }
    /*public static int updateOneRelationshipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_propietario_vehiculo as pv SET pv.fk_propietario=?, pv.fk_vehiculo=? WHERE (pv.id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_propietario());                
                ps.setInt(2, p.getFk_vehiculo());                
                ps.setInt(3, p.getId());                
                int retorno = ps.executeUpdate();
                con.commit();
                if (retorno > 0) {
                    return 1;
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
        
        return 0;
    }*/            
    public static ArrayList<GpsMarca> selectAllBandGps (GpsMarca h) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, nombre, descripcion, estado FROM tbl_gps_marca WHERE (estado = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();
            
            ArrayList<GpsMarca> lst_alm = new ArrayList<>();
            while (rs.next()) {
                GpsMarca a = new GpsMarca();
                a.setId(rs.getInt("id"));                
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));                
                a.setEstado(rs.getInt("estado"));                
                lst_alm.add(a);
            } return lst_alm;
        }    
        catch (SQLException ex) {
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
        } return null;
    }    
    
    
    
    public static GpsMarca searchGPSById (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsMarca v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, nombre, estado FROM tbl_gps_marca WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_marca());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsMarca();
                    v.setId(rs.getInt("id"));                                        
                    v.setNombre(rs.getString("nombre"));                                                                                
                    v.setEstado(rs.getInt("estado"));
                    return v;
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
