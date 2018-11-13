/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 * * CREADO POR JAIR HERNANDO VIDAL
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Alarma;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


/**
 *
 * @author lider_desarrollador
 */
public class AlarmaBD {
    
    public static boolean exist (Alarma a) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
                
         StringBuilder sql= new StringBuilder();        
        sql.append("CALL proc_tbl_alarma (");        
        sql.append(a.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, ");
        sql.append(a.getEstado());        
        sql.append(", NULL, NULL, NULL, NULL, 3);");   
        
        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
            ResultSet resultset = createStatement.executeQuery(sql.toString());  
            if (resultset.next()) 
            {
                return true;
            }          
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();
        }
        return false;
    }
    
    public static int insert (Alarma a) throws ExisteRegistroBD, SQLException {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;             
        ResultSet resultset = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_alarma (");        
        sql.append(a.getId());
        sql.append(", ");
        sql.append(a.getCodigoAlarma());
        sql.append(", '");
        sql.append(a.getNombreAlarma());
        sql.append("', '");
        sql.append(a.getTipoAlarma());
        sql.append("', '");
        sql.append(a.getUnidadDeMedicion());        
        sql.append("', ");
        sql.append(a.getPrioridad());        
        sql.append(", ");
        sql.append(a.getEstado());        
        sql.append(", 1, 'No Aplica', NULL, NULL, 0);");   
        if (exist(a)) {           
            return 0;
        } else {
            try {
                 Statement createStatement = con.createStatement();
                 int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
                 resultset = createStatement.executeQuery(sql.toString());  
            if (resultset.next()) 
            {
                return 1;
            }          
            } catch (SQLException ex) {
                System.err.println(ex);                 
            } finally {                
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);                
                resultset.close();
                con.close();
            }
        }
        return 1;
    }
    
    
    
    
    
    
    
    
    
        public static Alarma selectByOne (Alarma a) throws ExisteRegistroBD, SQLException{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Alarma alarmaEncontrado;
                 
        StringBuilder sql= new StringBuilder();
        sql.append("CALL proc_tbl_alarma (");        
        sql.append(a.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, ");
        sql.append(a.getEstado());        
        sql.append(", NULL, NULL, NULL, NULL, 3);");   
        
        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
            rs = createStatement.executeQuery(sql.toString());  
        
            alarmaEncontrado = new Alarma();
            if (rs.next())
            {
                alarmaEncontrado.setId(rs.getInt("PK_ALARMA"));
                alarmaEncontrado.setCodigoAlarma(rs.getInt("CODIGO_ALARMA"));
                alarmaEncontrado.setNombreAlarma(rs.getString("NOMBRE"));
                alarmaEncontrado.setTipoAlarma(rs.getString("TIPO"));
                alarmaEncontrado.setUnidadDeMedicion(rs.getString("UNIDAD_MEDICION"));                
                alarmaEncontrado.setPrioridad(rs.getInt("PRIORIDAD"));
                alarmaEncontrado.setEstado(rs.getInt("ESTADO"));               
            }
             return alarmaEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();
        }
        return null;
    }
        
        
        public static int update (Alarma a) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null; 
        ResultSet resultset = null;
        
        StringBuilder sql= new StringBuilder();
        
        sql.append("CALL proc_tbl_alarma (");                               
        sql.append(a.getId());        
        sql.append(", ");
        sql.append(a.getCodigoAlarma());        
        sql.append(", '");
        sql.append(a.getNombreAlarma());
        sql.append("', '");
        sql.append(a.getTipoAlarma());
        sql.append("', '");
        sql.append(a.getUnidadDeMedicion());        
        sql.append("', ");
        sql.append(a.getPrioridad());        
        sql.append(", ");
        sql.append(a.getEstado());        
        sql.append(", 1, 'No Aplica', NULL, NULL, 1);");   
        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
            resultset = createStatement.executeQuery(sql.toString());  
            if (resultset.next()) 
            {
                return 1;
            }
        } catch (SQLException ex) {
            System.err.println(ex);            
        } finally {
           UtilBD.closePreparedStatement(ps_update);
           pila_con.liberarConexion(con);           
           resultset.close();
           con.close();
        }
        return 0;
    }
        
        
        public static int updateEstado (Alarma a) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
                        
        StringBuilder sql= new StringBuilder();

        sql.append("CALL proc_tbl_alarma (");
        sql.append(a.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, ");
        sql.append(a.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 5);");
        
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
           con.close();
        }
       return 0;
    }
        
    public static ArrayList<Alarma> select () throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_alarma AS a WHERE a.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Alarma> lst_alm = new ArrayList<>();
            while (rs.next()) {
                Alarma a = new Alarma();
                a.setCodigoAlarma(rs.getInt("CODIGO_ALARMA"));
                a.setEstado(rs.getInt("ESTADO"));
                a.setId(rs.getInt("PK_ALARMA"));
                a.setNombreAlarma(rs.getString("NOMBRE"));
                a.setPrioridad(rs.getInt("PRIORIDAD"));
                a.setTipoAlarma(rs.getString("TIPO"));
                a.setUnidadDeMedicion(rs.getString("UNIDAD_MEDICION"));
                
                lst_alm.add(a);
            } return lst_alm;
            
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);            
            con.close();
        } return null;
    }
    
    
    
    public static ArrayList selectUltimaAlarma (String v) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;        
        Alarma a= null;
        
        ArrayList<Integer> todosLosCodigos = new ArrayList<Integer>();
        ArrayList<Integer> codigosAlarma = new ArrayList<Integer>();
        
        for (int i = 1; i <= 99; i++)
        {
            todosLosCodigos.add(i);            
        }
              
        
        StringBuilder sql= new StringBuilder();
        sql.append(" SELECT CODIGO_ALARMA AS PK_ALARMA FROM tbl_alarma as a WHERE (a.ESTADO > -1) ORDER BY(PK_ALARMA) ");
        
        try {
           Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
            ResultSet rs = createStatement.executeQuery(sql.toString());  
            while (rs.next()) 
            {   
                codigosAlarma.add( rs.getInt("PK_ALARMA") );
            }
            for (int i = 0; i < codigosAlarma.size(); i++) {
               Integer a1 = codigosAlarma.get(i);
                
               for (int j = 0; j < todosLosCodigos.size(); j++) {
                   Integer all = todosLosCodigos.get(j);
                   
                   if (Objects.equals(a1, all)) {
                       todosLosCodigos.remove(all);
                   }
                }
            }           
            
        } catch (SQLException ex) {
            todosLosCodigos.clear();
            System.err.println(ex);            
        } finally {            
           UtilBD.closePreparedStatement(ps_update);
           pila_con.liberarConexion(con);           
           con.close();
        }
       return todosLosCodigos;
    }
    
    
    
    
    
        public static int repeatAlarma (Alarma a) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;                
        
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT * FROM tbl_alarma AS a WHERE a.CODIGO_ALARMA = ").append(a.getId()).append(";");
       
        try {
           Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");                
            ResultSet rs = createStatement.executeQuery(sql.toString());  
            if (rs.next()) 
            {                
                return 1;
            }
        } catch (SQLException ex) {
            System.err.println(ex);            
        } finally {
           UtilBD.closePreparedStatement(ps_update);
           pila_con.liberarConexion(con);           
           con.close();
        }
       return 0;
    }
        
                       
}//FIN DE CLASE
