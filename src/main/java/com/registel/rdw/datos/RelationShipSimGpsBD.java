
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.TarjetaSim;
import com.registel.rdw.logica.RelationShipSimGps;

import java.sql.Statement;


public class RelationShipSimGpsBD {
    
    public static boolean exist (RelationShipSimGps p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_gps_sim WHERE (id = ?)";
        
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
    public static boolean existRelationShipByGps (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v= null;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps FROM tbl_gps_sim WHERE (fk_gps = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    return true;
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
        return false;
    }
    public static int insertOneRelationShipSimGpsReturnId (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idRelationShip=0;
        sql.append("INSERT INTO tbl_gps_sim (fk_sim, numero_celular, fk_gps) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                                                
                ps.setInt(1, p.getFk_sim());                                                
                ps.setString(2, p.getNumero_celular());                
                ps.setString(3, p.getFk_gps());                                
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
    public static int insertOneRelationShipSimGps (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;        
        sql.append("INSERT INTO tbl_gps_sim (fk_sim, numero_celular, fk_gps, fk_vh) VALUES (?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, p.getFk_sim());                                                
                ps.setString(2, p.getNumero_celular());                
                ps.setString(3, p.getFk_gps());              
                ps.setInt(4, p.getFk_vh());              
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
    /*ACTUALIZA EL ESTADO DEL REGISTRO POR ID DEL REGISTRO*/
    public static int updateOneRelationshipStateById (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET estado=? WHERE (id = ?)");             
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
    /*ACTUALIZA EL ESTADO DEL REGISTRO POR ID GPS*/
    public static int updateOneRelationshipStateByFkGps (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET estado=? WHERE (fk_gps = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                                
                ps.setString(2, p.getFk_gps());                
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
    /*ACTUALIZA EL ESTADO DEL REGISTRO POR ID GPS*/
    public static int updateOneRelationshipStateByIdGps (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET estado=? WHERE (fk_gps = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                                
                ps.setString(2, p.getId());                
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
    /*ACTUALIZA EL ESTADO DEL REGISTRO POR ID GPS*/
    public static int updateOneRelationShipSimGpsStateByGps (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET estado=? WHERE (fk_gps = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                                
                ps.setString(2, p.getId());                
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
/*ACTUALIZA EL ESTADO DEL REGISTRO POR ID SIM CARD*/
    public static int updateOneRelationshipStateByIdSim (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET estado=? WHERE (fk_sim = ?)");             
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
    public static int updateStateRelationShipOfFkSimFkGps (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET estado=? WHERE (fk_sim = ?) AND (fk_gps = ?)");
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                                
                ps.setInt(2, p.getFk_sim());                
                ps.setString(3, p.getFk_gps());                
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
    /*ACTUALIZA SIM Y GPS POR ID*/
    public static int updateOneRelationshipSimGpsAllById (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET fk_sim=?, fk_gps=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_sim());                
                ps.setString(2, p.getFk_gps());                
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
    }      
    /*ACTUALIZA GPS POR FK SIM*/
    public static int updateOneRelationshipSimGpsOnlyGpsByFkSim (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET fk_gps=? WHERE (fk_sim = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                              
                ps.setString(1, p.getFk_gps());                
                ps.setInt(2, p.getFk_sim());                
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
    /*ACTUALIZA SIM POR FK GPS*/
    public static int updateOneRelationshipSimGpsOnlySimByFkGps (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET fk_sim=? WHERE (fk_gps = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                              
                ps.setInt(1, p.getFk_sim());                
                ps.setString(2, p.getFk_gps());                
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
    
    /*ACTUALIZA SIM y NUMERO CELULAR POR FK GPS*/
    public static int updateOneRelationshipSimGpsByFkGps (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_sim SET fk_sim=?, numero_celular=? WHERE (fk_gps = ?) AND (estado = 1)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                              
                ps.setInt(1, p.getFk_sim());                
                ps.setString(2, p.getNumero_celular());                
                ps.setString(3, p.getFk_gps());                
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
    public static ArrayList<RelationShipSimGps> selectRelationShipSimGpsAll (RelationShipSimGps h) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (estado = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();
            
            ArrayList<RelationShipSimGps> lst_alm = new ArrayList<>();
            while (rs.next()) {
                RelationShipSimGps a = new RelationShipSimGps();
                a.setId(rs.getInt("id"));                                
                a.setFk_sim(rs.getInt("fk_sim"));                
                a.setFk_gps(rs.getString("fk_gps"));
                a.setFecha_vencimiento(rs.getString("fecha_registro"));                
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
    /*BUSCA UNA RELACION EL GPS REGISTRADO*/
    public static RelationShipSimGps searchRelationShipSimGpsByFkGps (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFk_gps(rs.getString("numero_celular"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /*BUSCA UNA RELACION NO ACTIVA*/
    public static RelationShipSimGps searchRelationShipSimGpsOffByFkGps (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /**/
    public static RelationShipSimGps searchRelationShipSimGpsOffByFkGpsFkSim (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_sim = ?) AND (fk_gps = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());
                ps.setString(2, c.getFk_gps());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    public static RelationShipSimGps searchRelationShipSimGpsOffByFkGps (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setNumero_celular(rs.getString("numero_celular"));
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /*BUSCA UNA RELACION SIM -GPS NO ACTIVA*/
    public static RelationShipSimGps searchRelationShipSimGpsByFkGps(RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    public static RelationShipSimGps searchRelationShipSimGpsOnByFkGps(RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (estado=1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setNumero_celular(rs.getString("numero_celular"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    
    public static RelationShipSimGps searchRelationShipSimGpsOnByGpsNCel(RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (numero_celular = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getFk_gps());
                ps.setString(2, c.getNumero_celular());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setNumero_celular(rs.getString("numero_celular"));
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    public static RelationShipSimGps searchRelationShipSimGpsOffByGpsNCel(RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (numero_celular = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getFk_gps());
                ps.setString(2, c.getNumero_celular());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setNumero_celular(rs.getString("numero_celular"));
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    
    public static RelationShipSimGps searchRelationShipSimGpsByGpsSim(RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (fk_sim = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getFk_gps());
                ps.setInt(2, c.getFk_sim());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    public static RelationShipSimGps searchRelationShipSimGpsOffByGpsSim(RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (fk_sim = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getFk_gps());
                ps.setInt(2, c.getFk_sim());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /*BUSCA UNA RELACION SIM -GPS NO ACTIVA*/
    public static RelationShipSimGps searchRelationShipSimGpsOnByFkGpsFkSim (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();
              
        sql.append("SELECT id, fk_sim, numero_celular,fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_sim = ?) AND (fk_gps = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());
                ps.setString(2, c.getFk_gps());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /**/
    public static RelationShipSimGps searchRelationShipSimGpsByGps (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_gps = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /**/
    public static RelationShipSimGps searchRelationShipSimGpsBySim (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_sim,numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_sim = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /**/
    public static RelationShipSimGps searchRelationShipSimGpsBySim (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_sim, numero_celular,fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (fk_sim = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
    /**/
    public static RelationShipSimGps searchRelationShipSimGpsById (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipSimGps v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_sim, numero_celular, fk_gps, fecha_registro, estado FROM tbl_gps_sim WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipSimGps();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_vencimiento(rs.getString("fecha_registro"));
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
