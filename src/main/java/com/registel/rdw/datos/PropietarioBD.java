/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Propietario;
import com.registel.rdw.logica.PropietarioVehiculo;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Usuario;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class PropietarioBD {
    
    public static boolean exist (PropietarioVehiculo p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_propietario_vehiculo WHERE (id = ?)";
        
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
    
    public static boolean existRelationShipOwnerCar (PropietarioVehiculo p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_propietario_vehiculo WHERE (fk_propietario = ?) and (fk_vehiculo = ?)";
        
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, p.getFk_propietario());
            ps.setInt(2, p.getFk_vehiculo());
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
    
    public static PropietarioVehiculo existOneRelationShipOwnerCar (PropietarioVehiculo p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        PropietarioVehiculo pv= null;
        
        String sql = "SELECT * FROM tbl_propietario_vehiculo WHERE (fk_propietario = ?) and (fk_vehiculo = ?)";
        
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, p.getFk_propietario());
            ps.setInt(2, p.getFk_vehiculo());
            rs = ps.executeQuery();            
            if (rs.next())
            {
                pv = new PropietarioVehiculo();
                pv.setId(rs.getInt("id"));
                pv.setFk_propietario(rs.getInt("fk_propietario"));
                pv.setFk_vehiculo(rs.getInt("fk_vehiculo"));
            }            
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return pv;
    }
    
    public static boolean existRelationShipOwner (PropietarioVehiculo p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_propietario_vehiculo WHERE (fk_propietario = ?)";
        
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, p.getFk_propietario());            
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
    
    public static PropietarioVehiculo existRelationShipByCar (Movil m) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        PropietarioVehiculo pv= null;
        String sql = "SELECT id, fk_propietario, fk_vehiculo, fecha_creacion, estado FROM tbl_propietario_vehiculo WHERE (fk_vehiculo = ?) and (estado=1)";
        
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, m.getId());            
            rs = ps.executeQuery();
            
           if (rs.next())
            {
                pv = new PropietarioVehiculo();
                pv.setId(rs.getInt("id"));
                pv.setFk_propietario(rs.getInt("fk_propietario"));
                pv.setFk_vehiculo(rs.getInt("fk_vehiculo"));                
            }
           return pv;
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
        
    public static int insertOneRelationshipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idPropietario=0;
        sql.append("INSERT INTO tbl_propietario_vehiculo (fk_propietario, fk_vehiculo) VALUES (?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                
                ps.setInt(1, p.getFk_propietario());
                ps.setInt(2, p.getFk_vehiculo());                
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {                
                    idPropietario = rs.getInt(1);
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
        }/*FIN ELSE*/
        return 0;
    }
    
    public static int insertRelationsShipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tbl_propietario_vehiculo (fk_propietario, fk_vehiculo) VALUES (?,?)");
        int retorno = 0;
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                int[] v = p.getVehiculos();
                for (int i = 0; i < v.length; i++) 
                {
                    ps.setInt(1, p.getFk_propietario());
                    ps.setInt(2, v[i]);                    
                    int e = ps.executeUpdate();
                    retorno = retorno + e;
                    con.commit();
                }               
                
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
        }/*FIN ELSE*/
        return 0;
    }
    public static int updateEstateOneRelationshipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_propietario_vehiculo SET activa=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getActiva());                                
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
    public static int updateRelationshipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_propietario_vehiculo as pv SET pv.activa = 0 WHERE (pv.id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getId());                
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
    public static int updateOneRelationshipOwnerCar (PropietarioVehiculo p) {        
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
    }    
    public static int updateOneRelationshipOwnerCarByid (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_propietario_vehiculo SET fk_propietario=? WHERE (id=?) AND (estado=1)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                
                ps.setInt(1, p.getFk_propietario());                
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
            
    public static ArrayList<Propietario> selectOwnerAll (Propietario p) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_usuario AS u WHERE (u.FK_PERFIL = ?) AND (u.ESTADO = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, p.getFk_perfil());
            rs = ps.executeQuery();
            
            ArrayList<Propietario> lst_alm = new ArrayList<>();
            while (rs.next()) {
                Propietario a = new Propietario();                
                a.setId(rs.getInt("PK_USUARIO"));
                a.setFk_perfil(rs.getInt("FK_PERFIL"));
                a.setFk_empresa(rs.getInt("FK_EMPRESA"));
                a.setFk_tipo_documento(rs.getInt("FK_TIPO_DOCUMENTO"));
                a.setCedula(rs.getString("CEDULA"));
                a.setNombre(rs.getString("NOMBRE"));
                a.setApellido(rs.getString("APELLIDO"));
                a.setLogin(rs.getString("LOGIN"));
                a.setEstado(rs.getInt("ESTADO"));                
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
    
    public static Perfil selectProfileOwner (Propietario p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_perfil as p WHERE (p.NOMBRE_PERFIL =?) AND (p.ESTADO = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, p.getPerfil());
            rs = ps.executeQuery();
            Perfil a = new Perfil();            
            if (rs.next()) {                                
                a.setId(rs.getInt("PK_PERFIL"));
                a.setNombre(rs.getString("NOMBRE_PERFIL"));
                a.setDescripcion(rs.getString("DESCRIPCION"));                
                a.setEstado(rs.getInt("ESTADO"));                                
            } return a;
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
    public static Usuario selectOwnerForDefault (Propietario p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_usuario WHERE (NOMBRE=?) AND (ESTADO = 1)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, p.getPerfil());
            rs = ps.executeQuery();
            Usuario a = new Usuario();            
            if (rs.next()) {                                
                a.setId(rs.getInt("PK_USUARIO"));
                a.setIdperfil(rs.getInt("FK_PERFIL"));
            } return a;
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
    
    public static ArrayList<PropietarioVehiculo> selectRelationsShipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        sql.append("SELECT pv.id, pv.fk_propietario, v.PLACA as placa, v.NUM_INTERNO as numero, concat(u.nombre,' ',u.apellido) as nombre ")
           .append("FROM tbl_propietario_vehiculo as pv INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO = pv.fk_vehiculo INNER JOIN tbl_usuario as u ON u.PK_USUARIO=pv.fk_propietario INNER JOIN tbl_perfil as p ON p.PK_PERFIL=u.FK_PERFIL ")
           .append("WHERE (pv.fk_propietario = ?) AND (p.NOMBRE_PERFIL = 'Propietario') AND (pv.activa = 1)");
                ArrayList<PropietarioVehiculo> lst = null;
            try {                
                lst = new ArrayList<>();
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_propietario());                
                rs = ps.executeQuery();
                PropietarioVehiculo a= null;
                while (rs.next()) {                                
                    a = new PropietarioVehiculo();
                    a.setId(rs.getInt("id"));
                    a.setFk_propietario(rs.getInt("fk_propietario"));                                        
                    a.setPropietario(rs.getString("nombre"));
                    a.setPlacaVehiculo(rs.getString("placa"));
                    a.setNumeroInterno(rs.getString("numero"));                                       
                    lst.add(a);
                }                
                if (lst.size() > 0) {
                    return lst;
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
       
        return lst;
    }
    
    public static ArrayList<Movil> selectCar () {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        sql.append("SELECT v.PK_VEHICULO, v.PLACA, v.NUM_INTERNO FROM tbl_vehiculo as v WHERE (v.PK_VEHICULO NOT IN(SELECT fk_vehiculo FROM tbl_propietario_vehiculo WHERE activa=1)) AND (v.ESTADO = ?)");
                ArrayList<Movil> lst = null;
            try {                
                lst = new ArrayList<>();
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, 1);                
                rs = ps.executeQuery();
                Movil a= null;
                while (rs.next()) {                                
                    a = new Movil();
                    a.setId(rs.getInt("PK_VEHICULO"));                    
                    a.setPlaca(rs.getString("PLACA"));
                    a.setNumeroInterno(rs.getString("NUM_INTERNO"));                    
                    lst.add(a);
                }                
                if (lst.size() > 0) {
                    return lst;
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
       
        return lst;
    }
}
