
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.logica.TarjetaOperacion;
import com.registel.rdw.logica.RelationShipTarjetaOperacionVehiculo;
import java.sql.Statement;
import java.util.List;


public class TarjetaOperacionBD {
    
   public static boolean exist (TarjetaOperacion p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT id FROM tbl_tarjeta_operacion WHERE (id = ?)";
        
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
   public static int insertDocumentosVehiculoReturnId (TarjetaOperacion p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idSim=0;
        sql.append("INSERT INTO tbl_tarjeta_operacion (fk_centro_exp, codigo, modelo, observaciones, fecha_vencimiento ) VALUES (?,?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);                
                ps.setInt(1, p.getFk_centro_exp());              
                ps.setString(2, p.getCodigo());                
                ps.setString(3, p.getModelo());
                ps.setString(4, p.getObservaciones());
                ps.setString(5, p.getFecha_vencimiento());
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
   public static int insertDocumentosVehiculo (TarjetaOperacion p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;        
        sql.append("INSERT INTO tbl_tarjeta_operacion (fk_centro_exp, codigo, modelo, observaciones, fecha_vencimiento ) VALUES (?,?,?,?,?)");
        
        if (exist(p)) {
            return 0;
        } else {
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_centro_exp());              
                ps.setString(2, p.getCodigo());                
                ps.setString(3, p.getModelo());
                ps.setString(4, p.getObservaciones());
                ps.setString(5, p.getFecha_vencimiento());
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
   public static int updateTarjetaOperacionRecord (TarjetaOperacion p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_operacion SET fk_centro_exp=?, modelo=?, observaciones=?, fecha_vencimiento=? WHERE (id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_centro_exp());                
                ps.setString(2, p.getModelo());                
                ps.setString(3, p.getObservaciones());                
                ps.setString(4, p.getFecha_vencimiento());                
                ps.setInt(5, p.getId());                
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
   public static int updateTarjetaOperacionRecordState (TarjetaOperacion p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tarjeta_operacion SET estado=? WHERE (id = ?)");             
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
   public static TarjetaOperacion selectTarjetaOperacionVehiculoById (TarjetaOperacion h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, codigo, modelo, fk_centro_exp, observaciones, fecha_vencimiento, estado FROM tbl_tarjeta_operacion WHERE (id = ?)") ;
        TarjetaOperacion a =null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getId());
            rs = ps.executeQuery();            
           
            if (rs.next()) {
                a = new TarjetaOperacion();
                a.setId(rs.getInt("id"));      
                a.setFk_centro_exp(rs.getInt("fk_centro_exp"));
                a.setCodigo(rs.getString("codigo"));                
                a.setModelo(rs.getString("modelo"));
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
   public static TarjetaOperacion selectTarjetaOperacionVehiculoById (RelationShipTarjetaOperacionVehiculo h) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, codigo, modelo, fk_centro_exp, observaciones, fecha_vencimiento, estado FROM tbl_tarjeta_operacion WHERE (id = ?)") ;
        TarjetaOperacion a =null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getFk_to());
            rs = ps.executeQuery();            
           
            if (rs.next()) {
                a = new TarjetaOperacion();
                a.setId(rs.getInt("id"));      
                a.setFk_centro_exp(rs.getInt("fk_centro_exp"));
                a.setCodigo(rs.getString("codigo"));                
                a.setModelo(rs.getString("modelo"));
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
      
   public static List<TarjetaOperacion> obtenerTarjetasOperacionPorVencer(String placa, int intervalo) {
       PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TarjetaOperacion> resultado = new ArrayList<>();
        
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT tarjetaOperacion.codigo, tarjetaOperacion.modelo, tarjetaOperacion.observaciones, ");
            query.append("tarjetaOperacion.fecha_vencimiento, centroExpedicion.nombre as nombreCentroExpedicion ");
            query.append("FROM tbl_tarjeta_operacion tarjetaOperacion ");
            query.append("INNER JOIN tbl_tarjeta_operacion_vh tarjetaOperacionVh ON ");
            query.append("tarjetaOperacion.id = tarjetaOperacionVh.fk_tarjeta ");
            query.append("INNER JOIN tbl_centro_expedicion centroExpedicion ON ");
            query.append("tarjetaOperacion.fk_centro_exp = centroExpedicion.id ");
            query.append("INNER JOIN tbl_vehiculo vehiculo ON tarjetaOperacionVh.fk_vh = vehiculo.PK_VEHICULO ");
            query.append("WHERE tarjetaOperacion.estado = 1 and tarjetaOperacionVh.estado = 1 and vehiculo.PLACA = ? ");
            query.append("and tarjetaOperacionVh.fecha_vencimiento BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY)");
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, placa);
            ps.setInt(2, intervalo);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                TarjetaOperacion tarjetaOperacion = new TarjetaOperacion();
                tarjetaOperacion.setCodigo(rs.getString("codigo"));
                tarjetaOperacion.setModelo(rs.getString("modelo"));
                tarjetaOperacion.setObservaciones(rs.getString("observaciones"));
                tarjetaOperacion.setFecha_vencimiento(rs.getString("fecha_vencimiento"));
                //tarjetaOperacion.setNombreCentroExpedicion(rs.getString("nombreCentroExpedicion"));
                resultado.add(tarjetaOperacion);
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
