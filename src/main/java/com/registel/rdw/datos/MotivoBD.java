/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.AuditoriaInformacionRegistradora;
import com.registel.rdw.logica.AuditoriaLiquidacionGeneral;
import com.registel.rdw.logica.AuditorialLiquidacionGeneral;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Motivo;
import java.sql.Statement;

/**
 *
 * @author lider_desarrollador
 */
public class MotivoBD {

    public static boolean exist(Motivo c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT c.PK_MOTIVO FROM tbl_motivo as c WHERE c.PK_MOTIVO = ?";

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
        }
        return false;
    }

    public static int insert(Motivo a) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append( "INSERT INTO tbl_motivo (FK_AUDITORIA, TABLA_AUDITORIA, DESCRIPCION_MOTIVO, INFORMACION_ADICIONAL, USUARIO, USUARIO_BD, MODIFICACION_LOCAL ) " );
        sql.append("VALUES (?,?,?,?,?,?,?)");                
        try {
            con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                
                ps.setInt(1, a.getFkAuditoria());
                ps.setString(2, a.getTablaAuditoria());
                ps.setString(3, a.getDescripcionMotivo());
                ps.setString(4, a.getInformacionAdicional());
                ps.setInt(5, a.getUsuario());
                ps.setString(6, a.getUsuarioBD());
                ps.setInt(7, 0);
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

    public static Motivo selectByOne(Motivo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Motivo motivoEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_motivo (");
        sql.append(c.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            motivoEncontrado = new Motivo();
            if (rs.next()) {
                motivoEncontrado.setFkAuditoria(rs.getInt("FK_AUDITORIA"));
                motivoEncontrado.setTablaAuditoria(rs.getString("TABLA_AUDITORIA"));
                motivoEncontrado.setDescripcionMotivo(rs.getString("DESCRIPCION_MOTIVO"));
                motivoEncontrado.setUsuario(rs.getInt("USUARIO"));
                motivoEncontrado.setUsuarioBD(rs.getString("USUARIO_BD"));
            }
            return motivoEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    public static Motivo selectByOneAIR(AuditoriaInformacionRegistradora air) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Motivo motivoEncontrado;

        StringBuilder sql = new StringBuilder();        
        
         sql.append(" SELECT *")
            .append(" FROM tbl_motivo")
            .append(" WHERE (FK_AUDITORIA=?) AND (TABLA_AUDITORIA='tbl_informacion_registradora')");  

        try {            
            con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());          
                ps.setInt(1, air.getId());                
                rs = ps.executeQuery();
                con.commit();

            motivoEncontrado = new Motivo();
            if (rs.next()) {
                motivoEncontrado.setId(rs.getInt("PK_MOTIVO"));
                motivoEncontrado.setFkAuditoria(rs.getInt("FK_AUDITORIA"));
                motivoEncontrado.setTablaAuditoria(rs.getString("TABLA_AUDITORIA"));
                motivoEncontrado.setDescripcionMotivo(rs.getString("DESCRIPCION_MOTIVO"));
                 motivoEncontrado.setInformacionAdicional(rs.getString("INFORMACION_ADICIONAL"));
                motivoEncontrado.setUsuario(rs.getInt("USUARIO"));
                motivoEncontrado.setUsuarioBD(rs.getString("USUARIO_BD"));
                return motivoEncontrado;
            }
            else{
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
        return null;
    }

    
    
    public static Motivo selectByOneALQ(AuditorialLiquidacionGeneral alq) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Motivo motivoEncontrado;

        StringBuilder sql = new StringBuilder();
        /*sql.append("CALL proc_tbl_motivo (NULL,");
        sql.append(alq.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3);");*/
         sql.append(" SELECT *")
            .append(" FROM tbl_motivo")
            .append(" WHERE (FK_AUDITORIA=?) AND (TABLA_AUDITORIA='tbl_auditoria_liquidacion_general')");                       
                
        try {           
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());          
                ps.setInt(1, alq.getId());                
                rs = ps.executeQuery();
                con.commit();
            
            motivoEncontrado = new Motivo();
            if (rs.next()) {
                motivoEncontrado.setFkAuditoria(rs.getInt("FK_AUDITORIA"));
                motivoEncontrado.setTablaAuditoria(rs.getString("TABLA_AUDITORIA"));
                motivoEncontrado.setDescripcionMotivo(rs.getString("DESCRIPCION_MOTIVO"));
                motivoEncontrado.setInformacionAdicional(rs.getString("INFORMACION_ADICIONAL"));
                if (rs.getInt("USUARIO")>0) {
                    motivoEncontrado.setUsuario(rs.getInt("USUARIO"));
                }                
                motivoEncontrado.setUsuarioBD(rs.getString("USUARIO_BD"));
                return motivoEncontrado;
            }
            else
            {
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
        return null;
    }

    
    public static int update(Motivo c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        /*sql.append("UPDATE tbl_motivo ");
        sql.append("SET FK_AUDITORIA=?, TABLA_AUDITORIA=?, DESCRIPCION_MOTIVO=?, USUARIO=?, USUARIO_BD=?, MODIFICACION_LOCAL=? ");
        sql.append("WHERE PK_MOTIVO=? ");*/
        sql.append("CALL proc_tbl_motivo (");
        sql.append(c.getId());
        sql.append(", ");
        sql.append(c.getFkAuditoria());
        sql.append(", '");
        sql.append(c.getTablaAuditoria());
        sql.append("', '");
        sql.append(c.getInformacionAdicional());
        sql.append("', NULL, NULL, 1);");
        try {
            /*ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.geFfkAuditoria());
            ps_update.setString(2, c.getTablaAuditoria());
            ps_update.setString(3, c.getDescripcionMotivo());
            ps_update.setInt(4, c.getUsuario());
            ps_update.setString(5, c.getUsuarioBD());
            ps_update.setInt(6, 1);                        
            ps_update.setInt(7, c.getId());                        
            ps_update.executeUpdate();            
            return true;*/
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            if (resultset.next()) {
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

}//FIN DE CLASE
