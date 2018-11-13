
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.datos.RevisionTMecanicaBD;
import com.registel.rdw.logica.RevisionTMecanica;
import com.registel.rdw.logica.RelationShipRevisionTMVehiculo;
import java.sql.Statement;
import java.util.List;


public class RevisionTMecanicaBD {
    
   public static boolean exist (RevisionTMecanica p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_revision WHERE (id = ?)";
        
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
   public static int insertOneRevision (RevisionTMecanica p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int id=0;
        sql.append("INSERT INTO tbl_revision (codigo, fk_centro_exp, fecha_vencimiento, observaciones ) VALUES (?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                
                ps.setString(1, p.getCodigo());                                                                                 
                ps.setInt(2, p.getFk_centro_diag());              
                ps.setString(3, p.getFecha_vencimiento());
                ps.setString(4, p.getObservaciones());
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
   public static int insertOneRevisionReturnId (RevisionTMecanica p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idGps=0;
        sql.append("INSERT INTO tbl_revision (codigo, fk_centro_exp, fecha_vencimiento, observaciones ) VALUES (?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                
                ps.setString(1, p.getCodigo());                                                                                 
                ps.setInt(2, p.getFk_centro_diag());              
                ps.setString(3, p.getFecha_vencimiento());
                ps.setString(4, p.getObservaciones());
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
   public static int updateRevisionFull (RevisionTMecanica p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_revision SET fk_centro_exp=?, fecha_vencimiento=?, observaciones=? WHERE (id = ?)");
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                
                ps.setInt(1, p.getFk_centro_diag());                
                ps.setString(2, p.getFecha_vencimiento());                
                ps.setString(3, p.getObservaciones());                
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
   public static int updateRevisionState (RevisionTMecanica p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_revision SET estado=? WHERE (id = ?)");             
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
   public static RevisionTMecanica selectRevisionTMecanica (RevisionTMecanica h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, codigo, fk_centro_exp, observaciones, fecha_vencimiento, estado FROM tbl_revision WHERE (id = ?)") ;
        RevisionTMecanica a =null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getId());
            rs = ps.executeQuery();            
           
            if (rs.next()) {
                a = new RevisionTMecanica();
                a.setId(rs.getInt("id"));      
                a.setFk_centro_diag(rs.getInt("fk_centro_diag"));
                a.setCodigo(rs.getString("codigo"));                                
                a.setObservaciones(rs.getString("observaciones"));                
                a.setFecha_vencimiento(rs.getString("fecha_vencimiento"));                
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
   
   public static RevisionTMecanica selectRevisionTMecanica (RelationShipRevisionTMVehiculo h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, codigo, fk_centro_exp, observaciones, fecha_vencimiento, estado FROM tbl_revision WHERE (id = ?)") ;
        RevisionTMecanica a =null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getFk_revision());
            rs = ps.executeQuery();            
           
            if (rs.next()) {
                a = new RevisionTMecanica();
                a.setId(rs.getInt("id"));      
                a.setFk_centro_diag(rs.getInt("fk_centro_exp"));
                a.setCodigo(rs.getString("codigo"));                                
                a.setObservaciones(rs.getString("observaciones"));                
                a.setFecha_vencimiento(rs.getString("fecha_vencimiento"));                
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
   
   
   public static List<RevisionTMecanica> obtenerRevisionesTMPorVencer(String placa, int intervalo) {
       PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RevisionTMecanica> resultado = new ArrayList<>();
        
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT revision.codigo, revision.observaciones, revision.fecha_vencimiento, ");
            query.append("centroDiagnostico.nombre as nombreCda ");
            query.append("FROM tbl_revision revision ");
            query.append("INNER JOIN tbl_revision_vh revisionVh ON revision.id = revisionVh.fk_revision ");
            query.append("INNER JOIN tbl_centro_diagnostico centroDiagnostico ON ");
            query.append("revision.fk_centro_diag = centroDiagnostico.id ");
            query.append("INNER JOIN tbl_vehiculo vehiculo ON revisionVh.fk_vh = vehiculo.PK_VEHICULO ");
            query.append("WHERE revision.estado = 1 and revisionVh.estado = 1 and vehiculo.PLACA = ? ");
            query.append("and revisionVh.fecha_vencimiento BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY)");
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, placa);
            ps.setInt(2, intervalo);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                RevisionTMecanica revision = new RevisionTMecanica();
                revision.setCodigo(rs.getString("codigo"));
                revision.setObservaciones(rs.getString("observaciones"));
                revision.setFecha_vencimiento(rs.getString("fecha_vencimiento"));
                revision.setNombreCda(rs.getString("nombreCda"));
                resultado.add(revision);
            }
        } catch(SQLException sqlEx) {
            System.err.print(sqlEx);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } catch(Exception ex) {
            System.err.print(ex);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    System.err.print(ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                System.err.print(sqlEx);
            }
            
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }
        
        return resultado;
   }



   
}
