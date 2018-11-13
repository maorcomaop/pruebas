/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Modulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.SubModulo;
import java.sql.Statement;

/**
 *
 * @author lider_desarrollador
 */
public class SubModuloBD {
    
    /*FUNCION QUE VERIFICA SI EXISTE UN REGISTRO*/
    public static boolean exist (SubModulo c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_submodulo (");
        sql.append(c.getId());
        sql.append(", ");
        sql.append(c.getFk_modulo());
        sql.append(", '");
        sql.append(c.getNombre());   
        sql.append("', NULL, NULL, NULL, ");                   
        sql.append(c.getEstado());   
        sql.append(", 3);");   
        
        
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
    /*FUNCION QUE INSERTA UN REGISTRO*/  
    public static int insert (SubModulo c) throws ExisteRegistroBD {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_submodulo (NULL, ");
        sql.append(c.getFk_modulo());
        sql.append(", '");
        sql.append(c.getNombre());
        sql.append("', '");
        sql.append(c.getDescripcion());
        sql.append("', NULL, NULL, ");
        sql.append(c.getEstado());
        sql.append(", 0); ");
        
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
    
    
    
    
    
    
    
    
        /*FUNCION QUE SELECCIONA UN SOLO REGISTRO*/    
        public static SubModulo selectByOne (SubModulo c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SubModulo ModuloEncontrado;
          StringBuilder sql = new StringBuilder();
          sql.append("CALL proc_tbl_submodulo (").append(c.getId()).append(", NULL, NULL, NULL, NULL, NULL, NULL, 3);");
        
        try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
                ModuloEncontrado = new SubModulo();
            if (resultset.next())
            {
               ModuloEncontrado.setId(resultset.getInt("PK_SUBMODULO"));
               ModuloEncontrado.setFk_modulo(resultset.getInt("FK_MODULO"));
               ModuloEncontrado.setNombre(resultset.getString("NOMBRE") );
               ModuloEncontrado.setDescripcion(resultset.getString("DESCRIPCION"));
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
        
        /*FUNCION QUE CONSULTA UN SOLO REGISTRO*/
         public static Modulo selectByOne1 (Modulo c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Modulo ModuloEncontrado;
          StringBuilder sql = new StringBuilder();
          sql.append("CALL proc_tbl_grupo (");
          sql.append(c.getId());
          sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3);");             
          
        
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
         
                         
        
        
         /*FUNCION QUE SELECCIONA UN SOLO REGISTRO*/    
        public static SubModulo selectByOne2 (SubModulo c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SubModulo ModuloEncontrado;
          StringBuilder sql = new StringBuilder();
          sql.append("CALL proc_tbl_submodulo (").append(c.getId()).append(", NULL, NULL, NULL, NULL, NULL, NULL, 3);");
        
        try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
                ModuloEncontrado = new SubModulo();
            if (resultset.next())
            {
               ModuloEncontrado.setId(resultset.getInt("PK_SUBMODULO"));
               ModuloEncontrado.setFk_modulo(resultset.getInt("FK_MODULO"));
               ModuloEncontrado.setNombre(resultset.getString("NOMBRE") );
               ModuloEncontrado.setDescripcion(resultset.getString("DESCRIPCION"));
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
         
        
        /*FUNCION QUE ACTUALIZA TODO EL REGISTRO*/
        public static int update (SubModulo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;   
                
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_submodulo (");
        sql.append(c.getId());
        sql.append(", ");
        sql.append(c.getFk_modulo());
        sql.append(", '");
        sql.append(c.getNombre());
        sql.append("', '");
        sql.append(c.getDescripcion());
        sql.append("', NULL, NULL,");        
        sql.append(c.getEstado());
        sql.append(", 1);");
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
        
        /*FUNCION QUE ACTULIZA EL ESTADO DE UN REGISTRO*/
        public static int updateEstado (SubModulo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
                        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_submodulo (").append(c.getId()).append(", ").append(" NULL, NULL, NULL, NULL, NULL, ").append(c.getEstado()).append(", 4);");         
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
