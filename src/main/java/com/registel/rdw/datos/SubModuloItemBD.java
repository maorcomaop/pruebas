/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Modulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.SubItemModulo;
import com.registel.rdw.logica.SubModulo;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class SubModuloItemBD {
    
    public static boolean exist (SubItemModulo c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_acceso (");
        sql.append(c.getId());
        sql.append(", ");
        sql.append(c.getFk_grupo());
        sql.append(", ");
        sql.append(c.getFk_submodulo());
        sql.append(", '");        
        sql.append(c.getNombre());   
        sql.append("', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ");                   
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
    
    public static int insert (SubItemModulo c) throws ExisteRegistroBD {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_acceso (NULL, ");
        sql.append(c.getFk_grupo());
        sql.append(", ");
        sql.append(c.getFk_submodulo());
        sql.append(", '");
        sql.append(c.getNombre());
        sql.append("', '");
        sql.append(c.getCodigoDeAcceso());
        sql.append("', '");
        sql.append(c.getNombreLargo());
        sql.append("', '");
        sql.append(c.getTeclaAcceso());
        sql.append("', '");
        sql.append(c.getUbicacion());
        sql.append("', '");
        sql.append(c.getRutaImagen());
        sql.append("', '");
        sql.append(c.getSubGrupo());
        sql.append("', ");
        sql.append(c.getPosicion());
        sql.append(", ");
        sql.append(c.getPosicionSubGrupo());
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
    
    
    
    
    
    
    
    
    
        public static SubItemModulo selectByOne (SubItemModulo c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SubItemModulo ModuloEncontrado;
          StringBuilder sql = new StringBuilder();
          sql.append("CALL proc_tbl_acceso (").append(c.getId()).append(", NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3);");
        
        try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
                ModuloEncontrado = new SubItemModulo();
            if (resultset.next())
            {
               ModuloEncontrado.setId(resultset.getInt("PK_ACCESO"));
               ModuloEncontrado.setFk_grupo(Integer.parseInt( resultset.getString("FK_GRUPO") ));               
               ModuloEncontrado.setFk_submodulo(Integer.parseInt( resultset.getString("FK_SUBMODULO") ));               
               ModuloEncontrado.setNombre(resultset.getString("NOMBRE_ACCESO"));               
               ModuloEncontrado.setCodigoDeAcceso(resultset.getString("CODIGO_ACCESO"));               
               ModuloEncontrado.setNombreLargo(resultset.getString("NOMBRE_LARGO"));               
               ModuloEncontrado.setTeclaAcceso(resultset.getString("TECLA_ACCESO"));               
               ModuloEncontrado.setUbicacion(resultset.getString("UBICACION"));               
               ModuloEncontrado.setRutaImagen(resultset.getString("Imagen"));               
               ModuloEncontrado.setSubGrupo(resultset.getString("SUBGRUPO"));               
               ModuloEncontrado.setPosicion(resultset.getInt("POSICION"));               
               ModuloEncontrado.setPosicionSubGrupo(resultset.getInt("POSICIONSUBGRUPO"));
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
        
        
         public static Modulo selectByOne1 (Modulo c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Modulo ModuloEncontrado;
          StringBuilder sql = new StringBuilder();
          sql.append("CALL proc_tbl_grupo (");
          sql.append(c.getId());
          sql.append(", NULL, NULL, NULL, NULL, 1");             
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
        
        
        public static int update (SubItemModulo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;   
                
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_acceso (");
        sql.append(c.getId());        
        sql.append(", ");
        sql.append(c.getFk_submodulo());
        sql.append(", ");
        sql.append(c.getFk_grupo());
        sql.append(", '");
        sql.append(c.getNombre());        
        sql.append("', '");
        sql.append(c.getCodigoDeAcceso());        
        sql.append("', '");
        sql.append(c.getNombreLargo());
        sql.append("', '");        
        sql.append(c.getTeclaAcceso());                
        sql.append("', '");
        sql.append(c.getUbicacion());
        sql.append("', '");
        sql.append(c.getRutaImagen());        
        sql.append("', '");
        sql.append(c.getSubGrupo());        
        sql.append("', ");
        sql.append(c.getPosicion());        
        sql.append(", ");
        sql.append(c.getPosicionSubGrupo());                
        sql.append(", ");
        sql.append(c.getEstado());
        sql.append(",NULL, NULL, NULL, NULL, 1);");
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
        
        
        public static int updateEstado (SubItemModulo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
                        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_acceso (").append(c.getId()).append(", ").append(" NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ").append(c.getEstado()).append(", NULL, NULL, NULL, NULL, 5);");
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
        
        
        
        public static ArrayList<SubModulo> SubModulos(SubModulo c) throws ExisteRegistroBD  {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        ArrayList<SubModulo> lstsubmodulo = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_submodulo (NULL, ");          
        sql.append(c.getFk_modulo());          
        sql.append(", NULL, NULL, NULL, NULL, 1, 3);");          
        try {
           Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");                
                rs = createStatement.executeQuery(sql.toString());
            
            lstsubmodulo = new ArrayList<SubModulo>();
            while (rs.next()) {
                  SubModulo t = new SubModulo();
                t.setId(rs.getInt("PK_SUBMODULO"));
                t.setFk_modulo(rs.getInt("FK_MODULO"));                
                t.setNombre(rs.getString("NOMBRE"));                                
                t.setEstado(rs.getInt("ESTADO"));
                lstsubmodulo.add(t);
            }
                
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return lstsubmodulo;
        }    
            
}//FIN DE CLASE
