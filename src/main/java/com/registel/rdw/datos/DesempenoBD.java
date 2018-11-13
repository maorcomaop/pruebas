
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import com.registel.rdw.datos.DesempenoBD;
import com.registel.rdw.logica.Desempeno;


public class DesempenoBD {
    /*VERIFICA SI EXISTE O NO LA CONFIGURACION*/
   public static boolean exist (Desempeno p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_conductor_desempeno WHERE (id = ?)";
        
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
   /*REGISTRA LA CONFIGURACION*/
   public static int insertOneConfig (Desempeno p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        
        sql.append("INSERT INTO tbl_conductor_desempeno (nombre, puntaje_max, puntos_ex_vel, velocidad_max, ");
                                            sql.append(" puntos_ruta, porcentaje_ruta, puntos_dia_no, ");
                                            sql.append(" dias_descanso, puntos_ipk_mas, punto_ipk_menos)");
        sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                
                ps.setString(1, p.getNombre());
                ps.setInt(2, p.getPuntaje_max());
                ps.setInt(3, p.getPuntos_exceso_velocidad());
                ps.setInt(4, p.getVelocidad_max());
                ps.setInt(5, p.getPuntos_cumplimiento_de_ruta());                
                ps.setInt(6, p.getPorcentaje_ruta());                
                ps.setInt(7, p.getPuntos_dia_no_laborado());                
                ps.setInt(8, p.getDias_descanso());                
                ps.setInt(9, p.getPuntos_ipk_mas());
                ps.setInt(10, p.getPuntos_ipk_menos());
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
   /*SELECCIONA TODAS LAS CONFIGURACIONES*/
   public static ArrayList<Desempeno> selectOwnerAll (Desempeno h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, nombre, estado FROM tbl_conductor_desempeno WHERE (estado = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();
            
            ArrayList<Desempeno> lst_alm = new ArrayList<>();
            while (rs.next()) {
                Desempeno a = new Desempeno();
                a.setId(rs.getInt("id"));                
                a.setNombre(rs.getString("nombre"));                                
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
   /*SELECCIONA UNA CONFIGURACION*/
   public static Desempeno selectOneConf (Desempeno h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        Desempeno a = null;
        sql.append("SELECT id, nombre, puntaje_max, puntos_ex_vel, velocidad_max, puntos_ruta, porcentaje_ruta, puntos_dia_no, dias_descanso, puntos_ipk_mas, punto_ipk_menos, estado ") ;
        sql.append(" FROM tbl_conductor_desempeno WHERE (id = ?)") ;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getId());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                a = new Desempeno();
                a.setId(rs.getInt("id"));                
                a.setNombre(rs.getString("nombre"));
                a.setPuntaje_max(rs.getInt("puntaje_max"));
                a.setDias_descanso(rs.getInt("dias_descanso"));
                a.setPuntos_dia_no_laborado(rs.getInt("puntos_dia_no"));
                a.setPorcentaje_ruta(rs.getInt("porcentaje_ruta"));
                a.setPuntos_cumplimiento_de_ruta(rs.getInt("puntos_ruta"));                
                a.setPuntos_exceso_velocidad(rs.getInt("puntos_ex_vel"));
                a.setVelocidad_max(rs.getInt("velocidad_max"));
                a.setPuntos_ipk_mas(rs.getInt("puntos_ipk_mas"));
                a.setPuntos_ipk_menos(rs.getInt("punto_ipk_menos"));                
                
                a.setEstado(rs.getInt("estado"));                
                
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
   /*ACTUALIZA LA CONFIGURACION ACTUAL*/
   public static int updateOneConf (Desempeno p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_conductor_desempeno SET nombre=?, puntaje_max=?, puntos_ex_vel=?, ");
        sql.append(" velocidad_max=?, puntos_ruta=?, porcentaje_ruta=?, ");
        sql.append(" puntos_dia_no=?, dias_descanso=?, puntos_ipk_mas=?, ");
        sql.append(" punto_ipk_menos=? WHERE (id = ?)");
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                                
                ps.setString(1, p.getNombre());                
                ps.setInt(2, p.getPuntaje_max());                                
                ps.setInt(3, p.getPuntos_exceso_velocidad());                
                ps.setInt(4, p.getVelocidad_max());
                ps.setInt(5, p.getPuntos_cumplimiento_de_ruta());
                ps.setInt(6, p.getPorcentaje_ruta());
                ps.setInt(7, p.getPuntos_dia_no_laborado());
                ps.setInt(8, p.getDias_descanso());
                ps.setInt(9, p.getPuntos_ipk_mas());
                ps.setInt(10, p.getPuntos_ipk_menos());
                ps.setInt(11, p.getId());
                
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
   /*CAMBIA EL ESTADO DE UNA CONFIGURACION*/
   public static int updateState (Desempeno p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_conductor_desempeno SET  estado=? WHERE (id = ?)");             
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

