/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Modulo;
import java.sql.Statement;

/**
 *
 * @author lider_desarrollador
 */
public class ModuloBD {
    
    public static boolean exist (Modulo c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_grupo (");
        sql.append(c.getId());
        sql.append(", '");
        sql.append(c.getNombre());   
        sql.append("', NULL,NULL, NULL,");                   
        sql.append(c.getEstado());   
        sql.append(", NULL, NULL, NULL, NULL, 3);");   
        
        
        try {
           Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
                resultset.last();
                int row = resultset.getRow();
                if (row > 0)
                {
                   return true;
                }
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    public static int insert (Modulo c) throws ExisteRegistroBD {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_grupo (NULL, '");
        sql.append(c.getNombre());
        sql.append("', '");                
        sql.append(c.getTeclaDeAcceso());
        sql.append("', '");                
        sql.append(c.getRuta_imagen());
        sql.append("', ");                
        sql.append(c.getPosicion());        
        sql.append(", ");
        sql.append(c.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 0);");
        
        if (exist(c)) {
            return 0;                
        } else {
            try {                
                Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");
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
    
    
    
    
    
    
    
    
    
        public static Modulo selectByOne (Modulo c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Modulo ModuloEncontrado;
          StringBuilder sql = new StringBuilder();
          sql.append("CALL proc_tbl_grupo (");
          sql.append(c.getId());
          sql.append(", NULL, NULL, NULL, NULL, NULL");   
          /*sql.append(c.getEstado());   */
          sql.append(", NULL, NULL, NULL, NULL, 3);");             
        
        try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
                ModuloEncontrado = new Modulo();
            if (resultset.next())
            {
               ModuloEncontrado.setId(resultset.getInt("PK_GRUPO"));
               ModuloEncontrado.setNombre(resultset.getString("NOMBRE"));               
               ModuloEncontrado.setEstado(resultset.getInt("ESTADO"));
            }                
                             
            return ModuloEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
        
        
        public static int update (Modulo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;   
                
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_grupo (").append(c.getId()).append(", '").append(c.getNombre()).append("', '").append(c.getTeclaDeAcceso()).append("', '").append(c.getRuta_imagen()).append("', ").append(c.getPosicion()).append(", ").append(c.getEstado()).append(",NULL, NULL, NULL, NULL, 1);");
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
        
        
        public static int updateEstado (Modulo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
                        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_grupo (").append(c.getId()).append(", ").append(" NULL, NULL, NULL, NULL, ").append(c.getEstado()).append(", NULL, NULL, NULL, NULL, 5);");         
        try {
            Statement createStatement = con.createStatement();
             int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
             int executeUpdate1 = createStatement.executeUpdate(sql.toString());  
             return 1;
        } catch (SQLException ex) {
            System.err.println(ex);
            
        } finally {
           UtilBD.closePreparedStatement(ps_update);
           pila_con.liberarConexion(con);
        }
        return 0;
    }
            
            
}//FIN DE CLASE
