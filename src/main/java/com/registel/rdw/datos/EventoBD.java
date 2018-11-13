/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Evento;
import java.sql.Statement;


/**
 *
 * @author lider_desarrollador
 */
public class EventoBD {
    
    public static boolean exist (Evento c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT e.PK_EVENTO FROM tbl_evento as e WHERE e.PK_EVENTO = ?";
        
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, c.getId());
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
    
    public static int insert (Evento e) throws ExisteRegistroBD {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        StringBuilder sql = new StringBuilder();

        sql.append("CALL proc_tbl_evento (");        
        sql.append(e.getId());
        sql.append(", '");
        sql.append(e.getCodigoEvento());
        sql.append("', '");
        sql.append(e.getNombreGenerico());
        sql.append("', '");
        sql.append(e.getDescripcion());
        sql.append("', ");
        sql.append(e.getPrioridad());        
        sql.append(", ");
        sql.append(e.getCantidad());                
        sql.append(", '");
        sql.append(e.getTipoEvento());                
        sql.append("', ");
        sql.append(e.getEstado());        
        sql.append(", 1, NULL, NULL, 0, 0);"); 
        
        if (exist(e)) {
            return 0;
        } else {
            try {
                  Statement createStatement = con.createStatement();
                  int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                  ResultSet resultset = createStatement.executeQuery(sql.toString());  
            if (resultset.next()) 
            {
                return 1;
            }          
            } catch (SQLException ex) {
                System.err.println(ex); 
                
            } finally {                
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }
        }
        return 0;
    }
    
    
    
    
    
    
    
    
    
        public static Evento selectByOne (Evento e) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Evento eventoEncontrado;
                 
        StringBuilder sql= new StringBuilder();        
        sql.append("CALL proc_tbl_evento (");        
        sql.append(e.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, ");
        sql.append(e.getEstado());        
        sql.append(", NULL, NULL, NULL, NULL, 3);"); 
        
        try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                rs = createStatement.executeQuery(sql.toString());  
                 
            eventoEncontrado = new Evento();
            if (rs.next())
            {
                eventoEncontrado.setId(rs.getInt("PK_EVENTO")); 
                eventoEncontrado.setCodigoEvento(rs.getString("CODIGO"));                
                eventoEncontrado.setNombreGenerico(rs.getString("NOMBRE_GENERICO"));
                eventoEncontrado.setDescripcion(rs.getString("DESCRIPCION"));
                eventoEncontrado.setPrioridad(rs.getInt("PRIORIDAD"));                                
                eventoEncontrado.setCantidad(rs.getInt("CANTIDAD"));
                eventoEncontrado.setTipoEvento(rs.getString("TIPO_EVENTO"));
                eventoEncontrado.setEstado(rs.getInt("ESTADO"));
            }
             return eventoEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
        
        
        public static int update (Evento e) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
        
                
        StringBuilder sql= new StringBuilder();
        
        sql.append("CALL proc_tbl_evento (");        
        sql.append(e.getId());
        sql.append(", '");
        sql.append(e.getCodigoEvento());
        sql.append("', '");
        sql.append(e.getNombreGenerico());
        sql.append("', '");
        sql.append(e.getDescripcion());
        sql.append("', ");
        sql.append(e.getPrioridad());        
        sql.append(", ");
        sql.append(e.getCantidad());                
        sql.append(", '");
        sql.append(e.getTipoEvento());                
        sql.append("', ");
        sql.append(e.getEstado());        
        sql.append(", 1, NULL, NULL, 0, 1);"); 
        try {
            
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
            ResultSet resultset = createStatement.executeQuery(sql.toString());  
            if (resultset.next()) 
            {
                return 1;
            }          

        } catch (SQLException ex) {
            System.err.println(ex);
            
        } finally {
           UtilBD.closePreparedStatement(ps_update);
           pila_con.liberarConexion(con);
        }
        return 0;
    }
        
        
        public static boolean updateEstado (Evento e) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
                        
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE tbl_evento ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_EVENTO=? ");
        try {
            ps_update = con.prepareStatement(sql.toString());            
            ps_update.setInt(1, e.getEstado());
            ps_update.setInt(2, e.getId());            
            ps_update.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally {
           UtilBD.closePreparedStatement(ps_update);
           pila_con.liberarConexion(con);
        }
        
    }
            
            
}//FIN DE CLASE
