
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.datos.GpsInventarioBD;
import com.registel.rdw.logica.TarjetaSim;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.RelationShipSimGps;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import java.sql.Statement;


public class TarjetaSimBD {
    
   public static boolean exist (TarjetaSim p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_gps_inventario WHERE (id = ?)";
        
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
   public static int insertOneSimCardReturnId (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idSim=0;
        sql.append("INSERT INTO tbl_tarjeta_sim (fk_operador, codigo, num_cel) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                
                ps.setInt(1, p.getFk_operador());              
                ps.setString(2, p.getImei());                                                                                 
                ps.setString(3, p.getNum_cel());
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {                
                    idSim = rs.getInt(1);
                }
                con.commit();
                if (retorno > 0) {
                    return idSim;
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
   public static int insertOneSimCard (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idGps=0;
        sql.append("INSERT INTO tbl_tarjeta_sim (fk_operador, codigo, num_cel) VALUES (?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                
                ps.setInt(1, p.getFk_operador());
                ps.setString(2, p.getCodigo());                
                ps.setString(3, p.getNum_cel());
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {                
                    idGps = rs.getInt(1);
                }
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
   public static int updateSimCardRecord (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET fk_operador=?, codigo=?, num_cel=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_operador());
                ps.setString(2, p.getCodigo());                                
                ps.setString(3, p.getNum_cel());                
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
   public static int updateSimCardRecordOper (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET fk_operador=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_operador());                
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
   public static int updateSimCardRecordState (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET estado=? WHERE (id = ?)");             
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
   public static int updateSimCardRecordStateByRelationShip (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET estado=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                
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
   public static int updateSimCardRecordMatch (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET asociada=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                
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
   public static int updateSimCardRecordMatchByNCel (RelationShipSimGps p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET asociada=? WHERE (num_cel = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getEstado());                
                ps.setString(2, p.getNumero_celular());                
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
   public static int updateSimCardRecordMatch (TarjetaSim p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_sim SET asociada=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getAsociada());                
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
   
   public static ArrayList<TarjetaSim> selectOwnerAll (TarjetaSim h) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (estado = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();
            
            ArrayList<TarjetaSim> lst_alm = new ArrayList<>();
            while (rs.next()) {
                TarjetaSim a = new TarjetaSim();
                a.setId(rs.getInt("id"));      
                a.setFk_operador(rs.getInt("fk_operador"));
                a.setCodigo(rs.getString("codigo"));                                
                a.setNum_cel(rs.getString("num_cel"));                
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
   /*PREGUNTA SI EXISTE UNA SIM CARD POR EL ID DEL REGISTRO*/
   public static boolean existeSimCardById (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (id = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
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
   /*PREGUNTA SI EXISTE UNA SIM CARD POR EL ID DEL REGISTRO*/
   public static boolean existeSimCardById (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (id = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());
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
   /*PREGUNTA SI EXISTE UNA SIM CARD POR EL ID DEL REGISTRO*/
   public static boolean existeSimCardById1 (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (id = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());
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
   /*PREGUNTA SI EXISTE UNA SIM CARD POR EL ID DEL REGISTRO*/
   public static boolean existeSimCardByNCel (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (num_cel = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getNum_cel());
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
   /*PREGUNTA SI EXISTE UNA SIM CARD POR EL ID DEL REGISTRO*/
   public static boolean existeSimCardByNCel (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (num_cel = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getNumero_celular());
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
   /*PREGUNTA SI EXISTE UNA SIM CARD POR EL ID DEL REGISTRO*/
   public static boolean existeSimCardByNCel1 (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (num_cel = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getNumero_celular());
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
   /*PREGUNTA POR EL ID DEL REGISTRO DEL GPS*/
   public static TarjetaSim searchSimCardById (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT ts.id, ts.fk_operador, ts.codigo, oc.nombre as op, ts.num_cel, ts.estado FROM tbl_tarjeta_sim as ts INNER JOIN tbl_operador_celular as oc ON ts.fk_operador= oc.id  WHERE  (ts.id = ?) AND (ts.estado = 1) AND (oc.estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_operador(rs.getInt("fk_operador"));             
                    v.setCodigo(rs.getString("codigo"));
                    v.setOperador(rs.getString("op"));
                    v.setNum_cel(rs.getString("num_cel"));                            
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
   /*BUSCA UNA SIM POR EL GPS ASOCIADO EN LA RELACION SIM GPS*/   
   public static TarjetaSim searchSimCardById (RelationShipSimGps c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT ts.id, ts.fk_operador, op.nombre,  ts.codigo, ts.num_cel, ts.estado ") ;
        sql.append("FROM tbl_tarjeta_sim as ts INNER JOIN tbl_operador_celular as op ON ts.fk_operador=op.id ") ;
        sql.append("WHERE (ts.id = ?) AND (ts.estado = 1) AND (op.estado=1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_operador(rs.getInt("fk_operador"));             
                    v.setCodigo(rs.getString("codigo"));
                    v.setOperador(rs.getString("nombre"));
                    v.setNum_cel(rs.getString("num_cel"));                            
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
   /*BUSCA UNA SIM POR EL GPS ASOCIADO EN LA RELACION HD - GPS - VH*/   
   public static TarjetaSim searchSimCardById1 (RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT ts.id, ts.fk_operador, op.nombre,  ts.codigo, ts.num_cel, ts.estado ") ;
        sql.append("FROM tbl_tarjeta_sim as ts INNER JOIN tbl_operador_celular as op ON ts.fk_operador=op.id ") ;
        sql.append("WHERE (ts.id = ?) AND (ts.estado = 1) AND (op.estado=1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getFk_sim());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                    
                    v.setFk_operador(rs.getInt("fk_operador"));             
                    v.setCodigo(rs.getString("codigo"));
                    v.setOperador(rs.getString("nombre"));
                    v.setNum_cel(rs.getString("num_cel"));                            
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
   /*BUSCA UNA SIM CARD POR EL CODIGO ASOCIADO*/
   public static TarjetaSim searchSimCardByCode (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (codigo = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getCodigo());
                rs = ps.executeQuery();
                v = null;
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                    
                    v.setCodigo(rs.getString("codigo"));                                        
                    v.setFk_operador(rs.getInt("fk_operador"));                                                
                    v.setNum_cel(rs.getString("num_cel"));                            
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
   /*BUSCA UNA SIM CARD POR EL NUMERO CELULAR ASOCIADO*/
   public static TarjetaSim searchSimCardByCelular (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, asociada, estado FROM tbl_tarjeta_sim WHERE (num_cel = ?) AND (estado = 1)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getNum_cel());
                rs = ps.executeQuery();                
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                    
                    v.setCodigo(rs.getString("codigo"));                                        
                    v.setFk_operador(rs.getInt("fk_operador"));                                                
                    v.setNum_cel(rs.getString("num_cel"));                            
                    v.setAsociada(rs.getInt("asociada"));           
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
   
   /*BUSCA UNA SIM CARD POR EL NUMERO CELULAR SI ESTA INACTIVA*/
   public static TarjetaSim searchSimCardOffByCelular (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v;                         
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (num_cel = ?) AND (estado = 0)") ;
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setString(1, c.getNum_cel());
                rs = ps.executeQuery();
                v = null;
                con.commit();
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                    
                    v.setCodigo(rs.getString("codigo"));                                        
                    v.setFk_operador(rs.getInt("fk_operador"));                                                
                    v.setNum_cel(rs.getString("num_cel"));                            
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
    /*BUSCA TODAS LAS SIM CARD QUE NO ESTAN ASOCIADAS A UN GPS*/
   public static ArrayList<TarjetaSim> searchSimNotMatchWithOutGps (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v;                         
        StringBuilder sql = new StringBuilder();        
        ArrayList<TarjetaSim> lst_alm = new ArrayList<>();
        sql.append("SELECT *  FROM tbl_tarjeta_sim WHERE (asociada=?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getAsociada());
                rs = ps.executeQuery();
                v = null;
                
                while (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                                        
                    v.setFk_operador(rs.getInt("fk_operador"));                                                            
                    v.setCodigo(rs.getString("codigo"));
                    v.setNum_cel(rs.getString("num_cel"));                                        
                    v.setAsociada(rs.getInt("asociada"));                     
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
   public static TarjetaSim searchOneSimNotMatchWithGps (TarjetaSim c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarjetaSim v = null;                         
        StringBuilder sql = new StringBuilder();                
        sql.append("SELECT *  FROM tbl_tarjeta_sim WHERE (id=?) AND (asociada=?)");
        
        try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, c.getId());
                ps.setInt(2, c.getAsociada());
                rs = ps.executeQuery();
                v = null;                
                if (rs.next())
                {                    
                    v = new TarjetaSim();
                    v.setId(rs.getInt("id"));                                        
                    v.setFk_operador(rs.getInt("fk_operador"));                                                            
                    v.setCodigo(rs.getString("codigo"));
                    v.setNum_cel(rs.getString("num_cel"));                                        
                    v.setAsociada(rs.getInt("asociada"));                     
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
   /*BUSCA UNA SIM CARD QUE ESTE ASOCIADA A UN GPS*/
   public static int simMatchWithGps (TarjetaSim t) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sg.fk_sim, sg.fk_gps  FROM tbl_gps_sim as sg INNER JOIN tbl_tarjeta_sim as ts ON sg.fk_sim = ts.id WHERE (ts.num_cel=?) AND (sg.estado = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, t.getNum_cel());
            rs = ps.executeQuery();            
            
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
   
   public static int simMatchWithGps1 (TarjetaSim t) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fk_sim, numero_celular, fk_gps  FROM tbl_gps_sim WHERE (numero_celular=?) AND (estado = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, t.getNum_cel());
            rs = ps.executeQuery();            
            
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
   public static int simWithNumCel (TarjetaSim t) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, fk_vehiculo, fk_hardware, fk_gps, fk_sim, numero_celular, fk_operador, estado FROM tbl_gps_hardware WHERE (numero_celular=?) AND (estado = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, t.getNum_cel());
            rs = ps.executeQuery();            
            
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
}
