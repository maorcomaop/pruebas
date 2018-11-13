
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.logica.RelationShipRevisionTMVehiculo;
import com.registel.rdw.logica.Movil;




import java.sql.Statement;


public class RelationShipRevisionVehiculoBD {
    
    public static boolean exist (RelationShipRevisionTMVehiculo p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_revision_vh WHERE (id = ?)";
        
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
    public static int insertOneRelationShipRevisionVehiculoReturnId (RelationShipRevisionTMVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idRelationShip=0;
        sql.append("INSERT INTO tbl_revision_vh (fk_revision, fk_vh, fecha_vencimiento) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                                                
                ps.setInt(1, p.getFk_revision());                                                
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
    public static int insertOneRelationShipRevisionVehiculo (RelationShipRevisionTMVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        
        sql.append("INSERT INTO tbl_revision_vh (fk_revision, fk_vh, fecha_vencimiento) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                                
                ps.setInt(1, p.getFk_revision());                                
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
     /*ACTUALIZA EL ESTADO DE UN RELACION*/
    public static int updateOneRelationshipState (RelationShipRevisionTMVehiculo p) {        
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
    /*ACTUALIZA EL REGISTRO COMPLETO*/
    public static int updateOneRelationship (RelationShipRevisionTMVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_revision_vh SET fk_vh=?, fk_revision=?, fecha_vencimiento=?, estado=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_vh());  
                ps.setInt(2, p.getFk_revision());  
                ps.setString(3, p.getFecha_vencimiento());  
                ps.setInt(4, p.getEstado());
                              
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
    /*ACTUALIZA EL REGISTRO POR MEDIO DE LA FK revision*/
    public static int updateOneRelationshipByFkRevision (RelationShipRevisionTMVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_revision_vh SET fk_vh=?, fecha_vencimiento=?, estado=? WHERE (fk_revision = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_vh());                  
                ps.setString(2, p.getFecha_vencimiento());                  
                ps.setInt(3, p.getFk_revision());  
                              
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
    /*ACTUALIZA EL REGISTRO POR MEDIO DE LA FK VEHICULO*/
    public static int updateOneRelationshipByFkVh (RelationShipRevisionTMVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_revision_vh SET fk_revision=?, fecha_vencimiento=?, estado=? WHERE (fk_vh = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                
                ps.setInt(1, p.getFk_revision());  
                ps.setString(2, p.getFecha_vencimiento());  
                ps.setInt(3, p.getFk_vh());  
                
                              
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
    
    /*BUSCA UNA RELACION ENTRE REVISION T M Y EL VEHICULO*/
    public static RelationShipRevisionTMVehiculo searchRelationShipRevisionTMVehiculo (Movil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipRevisionTMVehiculo v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_revision, fk_vh, fecha_vencimiento,estado FROM tbl_revision_vh WHERE (fk_vh = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipRevisionTMVehiculo();
                    v.setId(rs.getInt("id"));
                    v.setFk_revision(rs.getInt("fk_revision"));                                        
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
    
}

