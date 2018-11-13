/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.TipoDocumento;

/**
 *
 * @author lider_desarrollador
 */
public class tipoDocumentoBD {
    
    public static boolean exist(TipoDocumento c) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id FROM tbl_tipo_documento WHERE id = ?";

        try {
            con.setAutoCommit(false);
            ps = con.prepareCall(sql);
            ps.setInt(1, c.getId());
             rs = ps.executeQuery();                       
            if (rs.getRow() > 0) {
                return true;
            } else {
                con.rollback();
            }
        }catch (SQLException ex) {
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
        return false;
    }
   
    /*METODO QUE PERMITE INSERTAR UN NUEVO REGISTRO*/
    public static int insert(TipoDocumento c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();        
         sql.append("INSERT INTO tbl_tipo_documento ")
            .append("(tipo, descripcion, estado)")
            .append("VALUES (?,?,?)");
        if (exist(c)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                                                                
                ps.setString(1, c.getTipo());
                ps.setString(2, c.getDescripcion());                
                ps.setInt(3, new Short("1"));

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

    /*METODO QUE PERMITE SELECCIONAR UN REGISTRO*/
    public static TipoDocumento selectByOneId(TipoDocumento c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        TipoDocumento tipoDocumentoEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, tipo, descripcion, estado FROM tbl_tipo_documento WHERE (id=?) AND (estado = 1)");
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            
            tipoDocumentoEncontrado = new TipoDocumento();
            if (rs.next()) {
                tipoDocumentoEncontrado.setId(rs.getInt("id"));
                tipoDocumentoEncontrado.setTipo(rs.getString("tipo"));
                tipoDocumentoEncontrado.setDescripcion(rs.getString("descripcion"));                
                tipoDocumentoEncontrado.setEstado(rs.getInt("estado"));
                return tipoDocumentoEncontrado;
            }
            else {
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
           
    /*METODO QUE PERMITE ACTUALIZAR UN REGISTRO*/
    public static int update(TipoDocumento c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        

        StringBuilder sql = new StringBuilder();
        try {
            sql.append("UPDATE tbl_tipo_documento SET tipo=?, descripcion=? WHERE (id = ?) AND (estado=1)");                    
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());                        
            ps.setInt(1, c.getId());
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
    
    /* METODO QUE MODIFICA EL ESTADO DEL REGISTRO*/
    public static int updateEstado(TipoDocumento c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_tipo_documento SET estado=? WHERE id=? ");
        try {
             con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getEstado());
            ps_update.setInt(2, c.getId());
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
}
