/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.RelacionVehiculoEmpresa;
import java.sql.CallableStatement;
import java.sql.Statement;


/**
 *
 * @author lider_desarrollador
 */
public class RelacionVehiculoEmpresaBD {
    
    public static boolean exist (RelacionVehiculoEmpresa c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo_empresa (NULL").append(", ").append(c.getIdEmpresa()).append(", ").append(c.getIdVehiculo()).append(", ").append(c.getEstado()).append(", 3);");                
        
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
    
    
        
     
    
        public static int updateRelationShip (RelacionVehiculoEmpresa c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
                
        StringBuilder sql = new StringBuilder();        
        sql.append("CALL proc_tbl_vehiculo_empresa (").append(c.getIdRelacionVehiculoEmpresa()).append(", ").append(c.getIdEmpresa()).append(", ").append(c.getIdVehiculo()).append(", ").append(c.getEstado()).append(", 1);");
        try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                  
                return 1;                             
                                                
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {            
            UtilBD.closePreparedStatement(ps_update);            
            pila_con.liberarConexion(con);
        }
        return 0;
    }
    
        
    public static int insert (RelacionVehiculoEmpresa c) throws ExisteRegistroBD, SQLException {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo_empresa (NULL, ").append(c.getIdEmpresa()).append(", ").append(c.getIdVehiculo()).append(", 1, 0);");        
        if (exist(c)) {//pregunta si existe la relacion entre ese vehiculo y conductor                       
            return 0;
        }
        else if(!exist(c))
        {            
            /*if (updateRelationShip(c)) {//actualiza la relacion existente*/
             try 
             {
                    Statement createStatement = con.createStatement();
                    int retorno = createStatement.executeUpdate("START TRANSACTION;");
                    ResultSet resultset = createStatement.executeQuery(sql.toString());                    

                    if (resultset.next()) {
                        return 1;
                    }
             }
             catch (SQLException ex) 
             {
                    System.err.println(ex);
             }
             finally 
             {
                    UtilBD.closePreparedStatement(ps);
                    pila_con.liberarConexion(con);
                    con.close();
             }
                /*}*/
        }
        else {
            try {                
                Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());
                con.setAutoCommit(false);
                con.commit();                                              
                if (resultset.next()) {
                    return 1;                
                }                
            } catch (SQLException ex) {
                System.err.println(ex); 
            } finally {                
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }
        }
        return -1;
    }
    
    
    
    
        
    
    
    
    
    
    
        public static RelacionVehiculoEmpresa selectByOne (RelacionVehiculoEmpresa c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelacionVehiculoEmpresa relacionVehiculoEmpresaEncontrado;        
                 
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo_empresa( ");
        sql.append(c.getIdRelacionVehiculoEmpresa());
        sql.append(", NULL, NULL, 1, 3);");                        
        
        try {
            Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
            relacionVehiculoEmpresaEncontrado = new RelacionVehiculoEmpresa();
            if (resultset.next())
            {
                relacionVehiculoEmpresaEncontrado.setIdRelacionVehiculoEmpresa(resultset.getInt("PK_VEHICULO_EMPRESA"));                                                
                relacionVehiculoEmpresaEncontrado.setIdEmpresa(resultset.getInt("FK_EMPRESA"));                                
                relacionVehiculoEmpresaEncontrado.setIdVehiculo(resultset.getInt("FK_VEHICULO"));                   
                relacionVehiculoEmpresaEncontrado.setEstado(resultset.getInt("ESTADO"));
            }
             return relacionVehiculoEmpresaEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
      
        
        
        public static RelacionVehiculoEmpresa selectByOneNew (RelacionVehiculoEmpresa c) throws ExisteRegistroBD{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelacionVehiculoEmpresa relacionVehiculoEmpresaEncontrado;        
                 
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo_empresa(NULL, NULL,").append(c.getIdVehiculo()).append(", 1, 3);");                        
        
        try {
            Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");                
                ResultSet resultset = createStatement.executeQuery(sql.toString());                
            relacionVehiculoEmpresaEncontrado = new RelacionVehiculoEmpresa();
            if (resultset.next())
            {
                relacionVehiculoEmpresaEncontrado.setIdRelacionVehiculoEmpresa(resultset.getInt("PK_VEHICULO_EMPRESA"));                                                
                relacionVehiculoEmpresaEncontrado.setIdEmpresa(resultset.getInt("FK_EMPRESA"));                                
                relacionVehiculoEmpresaEncontrado.setIdVehiculo(resultset.getInt("FK_VEHICULO"));                   
                relacionVehiculoEmpresaEncontrado.setEstado(resultset.getInt("ESTADO"));
            }
             return relacionVehiculoEmpresaEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

        
        
        
        
         public static int updateEstado(RelacionVehiculoEmpresa c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_vehiculo_empresa ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_VEHICULO_EMPRESA=? ");          
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());            
            ps_update.setInt(1, c.getEstado());            
            ps_update.setInt(2, c.getIdRelacionVehiculoEmpresa());
            int retorno = ps_update.executeUpdate();            
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
             try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return 0;
    }
        
                           
            
}//FIN DE CLASE
