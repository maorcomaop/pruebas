
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.RelationShipSimGps;
import java.sql.Statement;


public class GpsInventarioBD {
    
   public static boolean exist (GpsRegistrado p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_gps_inventario WHERE (id = ?)";
        
        try {
            ps = con.prepareCall(sql);
            ps.setString(1, p.getId());
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
   /*public static String insertOneGPSReturnId (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        String idGps="";
        sql.append("INSERT INTO tbl_gps_inventario (id, imei, fk_marca, fk_modelo) VALUES (?,?,?,?)");
        
        if (exist(p)) {
            return "";
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                                
                ps.setString(1, p.getId());                
                ps.setString(2, p.getImei());                
                ps.setInt(3, p.getFk_marca());
                ps.setInt(4, p.getFk_modelo());                
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {                
                    idGps = rs.getString(1);
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
        return "";
    }   */
   public static int insertOneGPS (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        
        sql.append("INSERT INTO tbl_gps_inventario (id, imei, fk_marca, fk_modelo) VALUES (?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, p.getId());
                ps.setString(2, p.getImei());
                ps.setInt(3, p.getFk_marca());
                ps.setInt(4, p.getFk_modelo());                
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
   public static int updateBrandModelGpsRegisterById (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET fk_marca=?, fk_modelo=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                                
                ps.setInt(1, p.getFk_marca());                
                ps.setInt(2, p.getFk_modelo());                                
                ps.setString(3, p.getId());                
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
   
   public static int updateStateGpsRegister (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET estado=? WHERE (id = ?)");             
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
   public static int updateGpsRegisterMatch (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET asociado=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getAsociado());                                
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
   public static int updateFielAsociateGps (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET asociado=? WHERE (id = ?)");             
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
   public static int updateGpsRegisterMatchSim (GpsRegistrado p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET sim_asociada=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getSim_asociada());                                
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
   public static int updateFiledAsociateSim (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET sim_asociada=? WHERE (id = ?)");             
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
   
   public static int updateFiledAsociateSim (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET sim_asociada=? WHERE (id = ?)");             
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
   
   
   public static int updateStateGpsRegisterbyId (RelationShipHardwareGpsCar p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_gps_inventario SET estado=? WHERE (id = ?)");             
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
   public static ArrayList<GpsRegistrado> selectOwnerAll (GpsRegistrado h) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (estado = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();
            
            ArrayList<GpsRegistrado> lst_alm = new ArrayList<>();
            while (rs.next()) {
                GpsRegistrado a = new GpsRegistrado();
                a.setId(rs.getString("id"));                
                a.setFk_marca(rs.getInt("fk_marca"));
                a.setFk_modelo(rs.getInt("fk_modelo"));                
                a.setImei(rs.getString("imei"));
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
   /*PREGUNTA POR EL CODIGO DEL GPS*/
   public static boolean existeGps (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setEstado(rs.getInt("estado"));
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
   /*PREGUNTA POR EL ID DEL GPS*/
   public static boolean existeGpsById (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();                
                if (rs.next())                
                {   
                    System.out.println("--> "+rs.getString("id"));
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
   /*PREGUNTA SI EL GPS EXISTE Y ESTA ACTIVO*/
   public static boolean existeGpsById (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();                
                if (rs.next())                
                {   
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
   /*PREGUNTA SI EL GPS EXISTE Y ESTA ACTIVO*/
   public static boolean existeGpsById (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();
                int cantidadFilas = rs.getRow();
                //if (rs.next())
                if (cantidadFilas > 0)
                {   
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
   /*PREGUNTA SI EL GPS EXISTE Y ESTA ASOCIADO A UN VEHICULO*/
   public static boolean existeYEstaAsociadoGpsById (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1) AND (asociado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();
                int cantidadFilas = rs.getRow();
                //if (rs.next())
                if (cantidadFilas > 0)
                {   
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
   /*PREGUNTA SI EL GPS EXISTE Y ESTA ASOCIADO A UN VEHICULO*/
   public static boolean estaAsociadoGpsById (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (asociado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();
                int cantidadFilas = rs.getRow();
                //if (rs.next())
                if (cantidadFilas > 0)
                {   
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
   /*BUSCA GPS POR EL ID DEL REGISTRO DEL GPS*/
   public static GpsRegistrado searchGPSById (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT gi.id, gi.imei, gi.fk_marca, gm.nombre as marca, gi.fk_modelo, gmo.nombre as modelo, gi.estado ");
        sql.append(" FROM tbl_gps_inventario as gi INNER JOIN tbl_gps_marca as gm ON gi.fk_marca=gm.id INNER JOIN tbl_gps_modelo as gmo ON gi.fk_modelo=gmo.id ");
        sql.append(" WHERE (gi.id = ?) AND (gi.estado = 1) AND (gm.estado=1) AND (gmo.estado=1) ");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setMarca(rs.getString("marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setModelo(rs.getString("modelo"));
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
  /*BUSCA GPS POR EL ID DEL UNA RELACION*/
   public static GpsRegistrado searchGPSById (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT gi.id, gi.imei, gi.fk_marca, gm.nombre as marca, gi.fk_modelo, gmo.nombre as modelo,  gi.estado ");
        sql.append("FROM tbl_gps_inventario as gi INNER JOIN tbl_gps_marca as gm ON gi.fk_marca=gm.id INNER JOIN tbl_gps_modelo as gmo ON gi.fk_modelo=gmo.id ");
        sql.append("WHERE (gi.id = ?) AND (gi.estado = 1) AND (gm.estado = 1) AND (gmo.estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setMarca(rs.getString("marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setModelo(rs.getString("modelo"));                    
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
   /*BUSCA GPS POR EL ID DEL REGISTRO DEL GPS*/
   public static GpsRegistrado searchGPSByIdMaper (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        //sql.append("SELECT id, imei, fk_marca, fk_modelo, asociado, estado FROM tbl_gps_inventario WHERE (id = ?) AND (asociado = 1)");
        sql.append("SELECT gi.id, gi.imei, gi.fk_marca, gm.nombre as marca, gi.fk_modelo, gmo.nombre as modelo, gi.asociado,  gi.estado ");
        sql.append("FROM tbl_gps_inventario as gi INNER JOIN tbl_gps_marca as gm ON gi.fk_marca=gm.id INNER JOIN tbl_gps_modelo as gmo ON gi.fk_modelo=gmo.id ");
        sql.append("WHERE (gi.id = ?) AND (gi.asociado = 1) AND (gi.estado = 1) AND (gm.estado = 1) AND (gmo.estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setMarca(rs.getString("marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setModelo(rs.getString("modelo"));                    
                    v.setAsociado(rs.getInt("asociado"));
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
   /*BUSCA GPS POR ID DE RELACION*/
   public static GpsRegistrado searchGPSById (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getFk_gps());
                rs = ps.executeQuery();
                v = null;                
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
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
   /*BUSCA GPS POR ID*/
   public static GpsRegistrado searchGPSOnlyById (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;                
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
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
   /*BUSCA GPS POR EL CODIGO DEL GPS EN ESTADO CERO*/
   public static GpsRegistrado searchGPSByCodeOff (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
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
   /*BUSCA GPS POR EL CODIGO DEL GPS EN ESTADO CERO*/
   public static GpsRegistrado searchGPSByCodeMatchOff (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, asociado, estado FROM tbl_gps_inventario WHERE (id = ?) AND (asociado = 0)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setAsociado(rs.getInt("asociado"));
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
   /*BUSCA GPS POR EL CODIGO DEL GPS EN ESTADO CERO*/
   public static GpsRegistrado searchGPSByCodeOn (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
              
        sql.append("SELECT id, imei, fk_marca, fk_modelo, estado FROM tbl_gps_inventario WHERE (id = ?) AND (estado = 1)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
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
   
   public static GpsRegistrado searchGPSByCodeOnFull (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT gi.*, gm.nombre as marca, gmo.nombre as modelo , ts.num_cel as celular, oc.id as operador ");
        sql.append(" FROM tbl_gps_inventario as gi INNER JOIN tbl_gps_marca AS gm ON gi.fk_marca=gm.id INNER JOIN tbl_gps_modelo as gmo ON gi.fk_modelo=gmo.id INNER JOIN tbl_sim_gps AS sg ON sg.fk_gps=gi.id INNER JOIN tbl_tarjeta_sim as ts ON ts.id=sg.fk_sim INNER JOIN tbl_operador_celular as oc ON oc.id=ts.fk_operador ");
        sql.append(" WHERE (gi.id = ?) AND (gi.estado = 1) AND (sg.estado = 1) LIMIT 1;");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {   
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));
                    v.setImei(rs.getString("imei"));
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setMarca(rs.getString("marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));
                    v.setModelo(rs.getString("modelo"));
                    v.setCelular(rs.getString("celular"));
                    v.setFk_operador(rs.getInt("operador"));
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
   /*BUSCA GPS LIBRE QUE NO ESTE ASOCIADO*/
   public static ArrayList<GpsRegistrado> searchGPSNotMatchWithCar (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
        ArrayList<GpsRegistrado> lst_alm = new ArrayList<>();
        sql.append("SELECT * FROM tbl_gps_inventario WHERE (asociado=?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getAsociado());
                rs = ps.executeQuery();
                v = null;
                
                while (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setAsociado(rs.getInt("asociado"));                    
                    v.setEstado(rs.getInt("estado"));                    
                    lst_alm.add(v);
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
        return lst_alm;
    }
   public static ArrayList<GpsRegistrado> searchGPSNotMatchWithOutSim (GpsRegistrado c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GpsRegistrado v;                         
        StringBuilder sql = new StringBuilder();        
        ArrayList<GpsRegistrado> lst_alm = new ArrayList<>();
        sql.append("SELECT *  FROM tbl_gps_inventario WHERE (sim_asociada=?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getEstado());
                rs = ps.executeQuery();
                v = null;
                
                while (rs.next())
                {                    
                    v = new GpsRegistrado();
                    v.setId(rs.getString("id"));                                        
                    v.setImei(rs.getString("imei"));                                                            
                    v.setFk_marca(rs.getInt("fk_marca"));
                    v.setFk_modelo(rs.getInt("fk_modelo"));                    
                    v.setAsociado(rs.getInt("asociado")); 
                    v.setSim_asociada(rs.getInt("sim_asociada")); 
                    v.setEstado(rs.getInt("estado"));                    
                    lst_alm.add(v);
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
        return lst_alm;
    }
   /* SE BUSCA SI EL GPS YA ESTAS ASOCIADO */
   public static int gpsMatchWithCar (GpsRegistrado p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fk_vehiculo, fk_hardware, fk_gps FROM tbl_gps_hardware WHERE (fk_gps=?) AND (estado = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, p.getId());
            rs = ps.executeQuery();            
            Movil m = new Movil();
            if (rs.next()) {                                                
                return 1;
            } 
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
        } return 0;
    }
   
   public static RelationShipHardwareGpsCar gpsMatchWithCarById (GpsRegistrado p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fk_vehiculo, fk_hardware, fk_gps FROM tbl_gps_hardware WHERE (fk_vehiculo=?) AND (estado = 1)") ;
        RelationShipHardwareGpsCar m =null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, p.getId());
            rs = ps.executeQuery();            
            m = new RelationShipHardwareGpsCar();
            if (rs.next()) {                                                
                m.setFk_gps(rs.getString("fk_gps"));
                m.setFk_vehiculo(rs.getInt("fk_vehiculo"));
                return m;
            } 
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
   
}
