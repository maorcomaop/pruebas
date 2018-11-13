
package com.registel.rdw.datos;

import com.registel.rdw.logica.Movil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.RelationShipTarjetaOperacionVehiculo;
import java.sql.Statement;


public class RelationShipTarjetaOperacionVehiculoBD {
    
    public static boolean exist (RelationShipTarjetaOperacionVehiculo p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_tarjeta_operacion_vh WHERE (id = ?)";
        
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
    public static int insertOneRelationShipTarjetaOperacionVhReturnId (RelationShipTarjetaOperacionVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idRelationShip=0;
        sql.append("INSERT INTO tbl_tarjeta_operacion_vh (fk_tarjeta, fk_vh, fecha_vencimiento) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, p.getFk_to());                                                
                ps.setInt(2, p.getFk_vh());                
                ps.setString(3, p.getFecha_vencimiento());
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {                
                    idRelationShip = rs.getInt(1);
                }
                con.commit();
                if (retorno > 0) {
                    return idRelationShip;
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
    public static int insertOneRelationShipTarjetaOperacion (RelationShipTarjetaOperacionVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        
        sql.append("INSERT INTO tbl_tarjeta_operacion_vh (fk_tarjeta, fk_vh, fecha_vencimiento) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                                
                ps.setInt(1, p.getFk_to());                                
                ps.setInt(2, p.getFk_vh());                                
                ps.setString(3, p.getFecha_vencimiento());                
                int retorno = ps.executeUpdate();
                con.commit();
                if (retorno > 0) {
                    return retorno;
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
    
    /*BUSCA UNA RELACION EL GPS REGISTRADO*/
    public static RelationShipTarjetaOperacionVehiculo searchRelationShipTarjetaOperacionVehiculo (Movil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipTarjetaOperacionVehiculo v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_tarjeta, fk_vh, fecha_vencimiento,estado FROM tbl_tarjeta_operacion_vh WHERE (fk_vh = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipTarjetaOperacionVehiculo();
                    v.setId(rs.getInt("id"));
                    v.setFk_to(rs.getInt("fk_tarjeta"));                                        
                    v.setFk_vh(rs.getInt("fk_vh"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_vencimiento"));                    
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
    
    //ACTUALIZA EL ESTADO DE LA RELACION
    public static int updateOneRelationshipState (RelationShipTarjetaOperacionVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_revision_vh SET estado=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                                
                ps.setInt(2, p.getId());                
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
    }                    
}

