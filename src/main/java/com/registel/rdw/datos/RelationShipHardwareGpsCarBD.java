
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.logica.Hardware;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import java.sql.Statement;


public class RelationShipHardwareGpsCarBD {
    
    public static boolean exist (RelationShipHardwareGpsCar p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_gps_hardware WHERE (id = ?)";
        
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
    public static boolean existRelationShip (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v= null;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion  FROM tbl_gps_hardware WHERE (fk_gps = ?) AND (estado = 1)");
        
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
    public static int insertOneRelationShipHardwareGpsCarReturnId (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idRelationShip=0;
        sql.append("INSERT INTO tbl_gps_hardware (fk_vehiculo, fk_hardware, fk_gps) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                                                
                ps.setInt(1, p.getFk_vehiculo());                                
                ps.setInt(2, p.getFk_hardware());
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
    public static int insertRelationShipHdGpsVh (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        
        sql.append("INSERT INTO tbl_gps_hardware (fk_vehiculo, fk_hardware, fk_gps, fk_sim, numero_celular, fk_operador) VALUES (?,?,?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                                
                ps.setInt(1, p.getFk_vehiculo());                                
                ps.setInt(2, p.getFk_hardware());
                ps.setString(3, p.getFk_gps());                
                ps.setInt(4, p.getFk_sim());
                ps.setString(5, p.getNumero_celular());
                ps.setInt(6, p.getFk_operador());
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
    public static int updateOneRelationshipStateById (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET estado=? WHERE (id = ?)");             
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
    public static int updateOneRelationshipStateByFkGps (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET estado=? WHERE (fk_gps = ?)");             
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
        
    public static int updateOneRelationshipStateByFkGps1 (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET estado=? WHERE (fk_gps = ?)");             
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
    /*public static int updateOneRelationshipStateByFkGps (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET estado=? WHERE (fk_gps = ?)");             
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
    }   */                
    /*ACTUALIZA EL ESTADO DEL REGISTRO POR ID VEHICULO*/
    public static int updateOneRelationshipStateByIdCar (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET estado=? WHERE (fk_vehiculo = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                                
                ps.setInt(2, p.getFk_vehiculo());                
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
    public static int updateOneRelationshipStateByFkCarId (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET estado=? WHERE (fk_vehiculo = ?) AND (id=?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());
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
    }       
    /*ACTUALIZA EL GPS DEL REGISTRO POR ID y VEHICULO*/
    public static int updateOneRelationShipOnlyGPSById (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET fk_gps = ? WHERE (id = ?) AND (fk_vehiculo=?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, p.getFk_gps());
                ps.setInt(1, p.getFk_vehiculo());
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
    /*ACTUALIZA EL HARDWARE Y GPS POR ID*/
    public static int updateOneRelationshipHardwareGpsCarById (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET fk_hardware=?, fk_gps=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_hardware());                
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
    /*ACTUALIZA EL HARDWARE Y VEHICULO POR ID*/
    public static int updateOneRelationshipHardwareCarGpsAllById (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_hardware SET fk_vehiculo=?, fk_hardware=?, fk_gps=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_hardware());                
                ps.setString(2, p.getFk_gps());                
                ps.setInt(3, p.getFk_vehiculo());                
                ps.setInt(4, p.getId());                
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
    
    
    public static ArrayList<RelationShipHardwareGpsCar> selectOwnerAll (RelationShipHardwareGpsCar h) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (estado = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();
            
            ArrayList<RelationShipHardwareGpsCar> lst_alm = new ArrayList<>();
            while (rs.next()) {
                RelationShipHardwareGpsCar a = new RelationShipHardwareGpsCar();
                a.setId(rs.getInt("id"));                                
                a.setFk_vehiculo(rs.getInt("fk_vehiculo"));
                a.setFk_hardware(rs.getInt("fk_hardware"));
                a.setFk_gps(rs.getString("fk_gps"));
                a.setFecha_instalacion(rs.getString("fecha_instalacion"));                
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarByCar (Movil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fk_sim, numero_celular, fk_operador, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_operador(rs.getInt("fk_operador"));
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setNumero_celular(rs.getString("numero_celular"));
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarByGps (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_gps = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarByCar (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_vehiculo());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarOnByFkCar (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fk_sim, numero_celular, fk_operador, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_vehiculo());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_operador(rs.getInt("fk_operador"));
                    v.setNumero_celular(rs.getString("numero_celular"));
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarOnByFkCarFkGps (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_gps = ?) AND (fk_vehiculo = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());                
                ps.setInt(2, c.getFk_vehiculo());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarOffByFkCarFkGps (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_gps = ?) AND (fk_vehiculo = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());                
                ps.setInt(2, c.getFk_vehiculo());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarOnByFkCar (Movil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fk_sim, numero_celular, fk_operador, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));
                    v.setFk_sim(rs.getInt("fk_sim"));
                    v.setFk_operador(rs.getInt("fk_operador"));
                    v.setNumero_celular(rs.getString("numero_celular"));
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarOffByCar (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_vehiculo());                
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarByCarGpsHard (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?) AND (fk_gps = ?) AND (fk_hardware = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_vehiculo());                
                ps.setString(2, c.getFk_gps());
                ps.setInt(3, c.getFk_hardware());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarOffByCGH (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_vehiculo = ?) AND (fk_gps = ?) AND (fk_hardware = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_vehiculo());                
                ps.setString(2, c.getFk_gps());
                ps.setInt(3, c.getFk_hardware());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarByGps (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_gps = ?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarByHardware (Hardware c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (fk_hardware = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static RelationShipHardwareGpsCar searchRelationShipGpsHardwareCarById (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelationShipHardwareGpsCar v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fecha_instalacion, estado FROM tbl_gps_hardware WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new RelationShipHardwareGpsCar();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_vehiculo(rs.getInt("fk_vehiculo"));                    
                    v.setFk_hardware(rs.getInt("fk_hardware"));                                        
                    v.setFk_gps(rs.getString("fk_gps"));                                        
                    v.setFecha_instalacion(rs.getString("fecha_instalacion"));
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
    public static ArrayList<Movil> searchAllMoviles (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil vehiculoEncontrado;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT v.PK_VEHICULO, v.PLACA, v.NUM_INTERNO FROM tbl_vehiculo as v  WHERE (v.ESTADO=?) AND v.PK_VEHICULO NOT IN (SELECT gh.fk_vehiculo  FROM tbl_gps_hardware as gh WHERE gh.estado = 1);");                
        ArrayList<Movil> vh = new ArrayList();
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(c.getEstado(), 1);                
                rs = ps.executeQuery();
                vehiculoEncontrado = null;
                while (rs.next()) 
                {
                    vehiculoEncontrado = new Movil();
                    vehiculoEncontrado.setId(rs.getInt("PK_VEHICULO"));
                    vehiculoEncontrado.setNumeroInterno(rs.getString("NUM_INTERNO"));
                    vehiculoEncontrado.setPlaca(rs.getString("PLACA"));
                    vh.add(vehiculoEncontrado);
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
        return vh;
    }
    
}
