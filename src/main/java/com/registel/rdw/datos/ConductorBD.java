/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 * CREADO POR JAIR HERNANDO VIDAL
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.utils.StringUtils;
import java.sql.Statement;

/**
 *
 * @author lider_desarrollador
 */
public class ConductorBD {

    public static boolean exist(Conductor c) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT c.PK_CONDUCTOR FROM tbl_conductor as c WHERE c.PK_CONDUCTOR = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            rs.close();
            con.close();
        }
        return false;
    }

    public static int insert(Conductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();        
         sql.append("INSERT INTO tbl_conductor ")
            .append("(FK_EMPRESA, FK_TIPO_DOCUMENTO, FOTO, NOMBRE, APELLIDO, CEDULA, ESTADO)")
            .append("VALUES (?,?,?,?,?,?,?)");
        if (exist(c)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, c.getCodEmpresa());
                ps.setInt(2, c.getId_tipo_documento());
                ps.setString(3, c.getFoto());
                ps.setString(4, c.getNombre());
                ps.setString(5, c.getApellido());
                ps.setString(6, c.getCedula());
                ps.setInt(7, new Short("1"));

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
        }
        return 0;
    }

    public static Conductor selectByOne(Conductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Conductor conductorEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PK_CONDUCTOR,FK_EMPRESA,FK_TIPO_DOCUMENTO,NOMBRE,APELLIDO,CEDULA,ESTADO FROM tbl_conductor WHERE (PK_CONDUCTOR=?)");        
        try {
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            
            conductorEncontrado = new Conductor();
            if (rs.next()) {
                conductorEncontrado.setId(rs.getInt("PK_CONDUCTOR"));
                conductorEncontrado.setCodEmpresa(rs.getInt("FK_EMPRESA"));
                conductorEncontrado.setId_tipo_documento(rs.getInt("FK_TIPO_DOCUMENTO"));
                //conductorEncontrado.setFoto(rs.getString("FOTO"));
                conductorEncontrado.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                conductorEncontrado.setApellido(StringUtils.upperFirstLetter(rs.getString("APELLIDO")));
                conductorEncontrado.setCedula(rs.getString("CEDULA"));
                conductorEncontrado.setEstado(rs.getInt("ESTADO"));
            }
            return conductorEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            rs.close();
            con.close();
        }
        return null;
    }

    public static Conductor get(int id) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_conductor AS c WHERE c.PK_CONDUCTOR = ? AND c.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                Conductor c = new Conductor();
                c.setId(rs.getInt("PK_CONDUCTOR"));                
                c.setNombre(rs.getString("NOMBRE"));                
                c.setApellido(rs.getString("APELLIDO"));                
                c.setEstado(rs.getInt("ESTADO"));

                return c;
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            rs.close();
            con.close();
        }
        return null;
    }

    public static int update(Conductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet resultset = null;

        StringBuilder sql = new StringBuilder();
        try {
            sql.append("UPDATE tbl_conductor SET FK_EMPRESA =?, FK_TIPO_DOCUMENTO =?,")
               .append(" NOMBRE =?, APELLIDO =?, CEDULA =?, ESTADO =? WHERE PK_CONDUCTOR = ?");                    
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());            
            ps.setInt(1, c.getCodEmpresa());            
            ps.setInt(2, c.getId_tipo_documento());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellido());
            ps.setString(5, c.getCedula());
            ps.setInt(6, c.getEstado());
            ps.setInt(7, c.getId());
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
              try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);

        }
        return 0;
    }

    public static int updateEstado(Conductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_conductor SET ESTADO=? WHERE PK_CONDUCTOR=? ");
        try {
             con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getEstado());
            ps_update.setInt(2, c.getId());
            int retorno = ps_update.executeUpdate();
            //return true;
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
    
    
    
    
    
    
    
     public static int repeatConductor (Conductor a) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;                
        
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT * FROM tbl_conductor AS c WHERE (c.CEDULA LIKE '").append(a.getCedula()).append("%');");
         System.out.println(">>>"+sql.toString());
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
