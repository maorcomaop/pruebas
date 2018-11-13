/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.registel.rdw.datos.EmpresaBD.exist;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.Usuario;
import java.sql.Statement;

/**
 *
 * @author lider_desarrollador
 */
public class PerfilBD {

    public static boolean exist(Perfil p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.NOMBRE_PERFIL FROM tbl_perfil as p WHERE p.PK_PERFIL = ?");

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, p.getId());
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

    public static Perfil selectByOne(Perfil p) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Perfil perfilEncontrado = null;

        StringBuilder sql = new StringBuilder();
        //sql.append( "SELECT p.PK_PERFIL, p.NOMBRE_PERFIL, p.DESCRIPCION, P.ESTADO FROM tbl_perfil as p WHERE p.PK_PERFIL = ?" );
        sql.append("CALL proc_tbl_perfil (");
        sql.append(p.getId());
        sql.append(", NULL, NULL, NULL, ");
        sql.append(p.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        try {/*
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, p.getId());
            rs = ps.executeQuery();*/

            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());
            while (rs.next()) {
                perfilEncontrado = new Perfil();
                perfilEncontrado.setId(rs.getInt("PK_PERFIL"));
                perfilEncontrado.setNombre(rs.getString("NOMBRE_PERFIL"));
                perfilEncontrado.setDescripcion(rs.getString("DESCRIPCION"));
                perfilEncontrado.setEstado(rs.getInt("ESTADO"));
            }
            return perfilEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    
    
    
    
    public static Perfil selectByOneUser(Usuario u) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Perfil perfilEncontrado = null;

        StringBuilder sql = new StringBuilder();
        //sql.append( "SELECT u.PK_PERFIL, u.NOMBRE_PERFIL, u.DESCRIPCION, P.ESTADO FROM tbl_perfil as u WHERE u.PK_PERFIL = ?" );
        sql.append("CALL proc_tbl_perfil (");
        sql.append(u.getIdperfil());
        sql.append(", NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 3);");
        //System.out.println("---> "+sql.toString());
        try {/*
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();*/

            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());
            while (rs.next()) {
                perfilEncontrado = new Perfil();
                perfilEncontrado.setId(rs.getInt("PK_PERFIL"));
                perfilEncontrado.setNombre(rs.getString("NOMBRE_PERFIL"));
                perfilEncontrado.setDescripcion(rs.getString("DESCRIPCION"));
                perfilEncontrado.setEstado(rs.getInt("ESTADO"));
            }
            if (perfilEncontrado != null) {
                return perfilEncontrado;
            }            
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
       return null;
    }
    

    public static int insert(Perfil p) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_perfil (");
        sql.append(p.getId());
        sql.append(", '");
        sql.append(p.getNombre());
        sql.append("', '");
        sql.append(p.getDescripcion());
        sql.append("', NULL,");
        sql.append(p.getEstado());
        sql.append(", 1, NULL, NULL, 0, 0);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            if (resultset.next()) {
                ResultSet rId = createStatement.executeQuery("SELECT MAX(PK_PERFIL) AS id FROM tbl_perfil");
                if (rId.next()) {
                    p.setId(rId.getInt("id"));
                    return 1;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }

        return 0;
    }

    public static int insertOptions(String p[]) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        try {
            for (int i = 0; i < p.length; i++) {
                String cadena = p[i];
                String[] c = cadena.split(",");
                sql.append("CALL proc_tbl_acceso_perfil (NULL,").append(c[0]).append(", ").append(c[1]).append(", ").append("1").append(", 0, NULL, NULL, NULL, 0);");
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
                ResultSet resultset = createStatement.executeQuery(sql.toString());
                sql.delete(0, sql.length());
            }
            return 1;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }

        return 0;
    }

    public static int update(Perfil p) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        /*sql.append("UPDATE tbl_perfil ");
        sql.append("SET NOMBRE_PERFIL=?, DESCRIPCION=?, ESTADO=?, SYNC=?, MODIFICACION_LOCAL=? ");
        sql.append("WHERE PK_PERFIL=? ");*/
        sql.append("CALL proc_tbl_perfil (");
        sql.append(p.getId());
        sql.append(", '");
        sql.append(p.getNombre());
        sql.append("', '");
        sql.append(p.getDescripcion());
        sql.append("', NULL, ");
        sql.append(p.getEstado());
        sql.append(", 1, NULL, NULL, 0, 1);");
        try {/*
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setString(1, p.getNombre());
            ps_update.setString(2, p.getDescripcion());
            ps_update.setInt(3, p.getEstado());
            ps_update.setInt(4, 1);
            ps_update.setInt(5, 1);            
            ps_update.setInt(6, p.getId());
            ps_update.executeUpdate();            
            return 1;*/
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

    public static boolean updateEstado(Perfil p) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_perfil ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_PERFIL=? ");        
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());            
            ps_update.setInt(1, p.getEstado());            
            ps_update.setInt(2, p.getId());
            int retorno = ps_update.executeUpdate();
            con.commit();
                                   
            if (retorno > 0) {
                return true;
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
        return false;
    }
    
    public static Perfil selectById(int idPerfil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_perfil as p WHERE p.PK_PERFIL = ? AND ESTADO = 1");

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, idPerfil);
            rs = ps.executeQuery();

            if (rs.next()) {
                Perfil p = new Perfil();
                p.setId(rs.getInt("PK_PERFIL"));
                p.setNombre(rs.getString("NOMBRE_PERFIL"));
                p.setDescripcion(rs.getString("DESCRIPCION"));
                return p;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

}
