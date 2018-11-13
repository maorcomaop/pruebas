/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.CategoriasDeDescuento;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class CategoriasDeDescuentoBD {

    public static boolean exist(CategoriasDeDescuento c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (PK_CATEGORIAS_DESCUENTOS=?) AND (ESTADO=?) AND (NOMBRE LIKE ?)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getEstado());
            ps.setString(3, c.getNombre());
            rs = ps.executeQuery();                       
            if (rs.getRow() > 0) {
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

    public static int insert(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
         sql.append("INSERT INTO tbl_categoria_descuento ")
            .append("(NOMBRE, DESCRIPCION, VALOR, APLICA_DESCUENTO, APLICA_GENERAL, ES_VALOR_MONEDA, ES_PORCENTAJE, ES_FIJA, DESCUENTA_PASAJEROS, DESCUENTA_DEL_TOTAL, ESTADO)")
            .append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        if (exist(c)) {
            return 0;
        } else {
            try {
                 con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getDescripcion());
                ps.setDouble(3, c.getValor());
                ps.setInt(4, c.getAplicaDescuento());
                ps.setInt(5, c.getAplicaGeneral());
                ps.setInt(6, c.getEs_valor_moneda());
                ps.setInt(7, c.getEs_valor_porcentaje());
                ps.setInt(8, c.getEs_fija());
                ps.setInt(9, c.getDescuenta_pasajeros());
                ps.setInt(10, c.getDescuenta_del_total());
                ps.setInt(11, new Short("1"));

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
        }/*FIN ELSE*/
        return 0;
    }

    public static CategoriasDeDescuento selectByOne(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();        
        
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (PK_CATEGORIAS_DESCUENTOS=?) AND (ESTADO=?)");
        
        CategoriasDeDescuento categoriaEncontrada = null;
        try {
             con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getEstado());
            rs = ps.executeQuery();
            categoriaEncontrada = new CategoriasDeDescuento();
            if (rs.next()) {
                categoriaEncontrada.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                categoriaEncontrada.setNombre(rs.getString("NOMBRE"));
                categoriaEncontrada.setDescripcion(rs.getString("DESCRIPCION"));
                categoriaEncontrada.setValor(rs.getDouble("VALOR"));
                categoriaEncontrada.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                categoriaEncontrada.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                categoriaEncontrada.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                categoriaEncontrada.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                categoriaEncontrada.setEs_fija(rs.getInt("ES_FIJA"));
                categoriaEncontrada.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                categoriaEncontrada.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                categoriaEncontrada.setEstado(rs.getInt("ESTADO"));
                return categoriaEncontrada;
            } else {
                con.rollback();
            }
            //return categoriaEncontrada;
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

    public static int update(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_categoria_descuento ")
           .append(" SET NOMBRE=?, DESCRIPCION=?, VALOR=?, APLICA_DESCUENTO=?, APLICA_GENERAL=?, ES_VALOR_MONEDA=?, ES_PORCENTAJE=?, ")
           .append(" ES_FIJA=?, DESCUENTA_PASAJEROS=?, DESCUENTA_DEL_TOTAL=?, ESTADO=? ")
           .append(" WHERE PK_CATEGORIAS_DESCUENTOS=?");       
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());            
            
            ps_update.setString(1, c.getNombre());            
            ps_update.setString(2, c.getDescripcion());
            ps_update.setDouble(3, c.getValor());
            ps_update.setInt(4, c.getAplicaDescuento());
            ps_update.setInt(5, c.getAplicaGeneral());
            ps_update.setInt(6, c.getEs_valor_moneda());
            ps_update.setInt(7, c.getEs_valor_porcentaje());
            ps_update.setInt(8, c.getEs_fija());
            ps_update.setInt(9, c.getDescuenta_pasajeros());
            ps_update.setInt(10, c.getDescuenta_del_total());
            ps_update.setInt(11, c.getEstado());
            ps_update.setInt(12, c.getId());
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
    
    public static int updateEstado(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_categoria_descuento ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_CATEGORIAS_DESCUENTOS=? ");          
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
    
    public static ArrayList<CategoriasDeDescuento> categoriasDeDescuentoFijas(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CategoriasDeDescuento> lstdatos = null;
        StringBuilder sql = new StringBuilder();                
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (ES_FIJA=?) AND (ESTADO=?)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getEs_fija());
            ps.setInt(2, c.getEstado());
            rs = ps.executeQuery();            

            lstdatos = new ArrayList<CategoriasDeDescuento>();
            while (rs.next()) {
                CategoriasDeDescuento t = new CategoriasDeDescuento();
                t.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                t.setNombre(rs.getString("NOMBRE"));
                t.setDescripcion(rs.getString("DESCRIPCION"));
                t.setValor(rs.getDouble("VALOR"));
                t.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                t.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                t.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                t.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                t.setEs_fija(rs.getInt("ES_FIJA"));
                t.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                t.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                t.setEstado(rs.getInt("ESTADO"));
                t.setUniqueId(new BigInteger(32, new SecureRandom()).toString(32));
                lstdatos.add(t);
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
        return lstdatos;
    }
    public static ArrayList<CategoriasDeDescuento> categoriasQueDescuentanPasajeros() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CategoriasDeDescuento> lstdatos = null;
        StringBuilder sql = new StringBuilder();                
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (DESCUENTA_PASAJEROS=?)  AND (ESTADO=1) ORDER BY (ES_FIJA) ASC , NOMBRE ASC");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, 1);            
            rs = ps.executeQuery();            

            lstdatos = new ArrayList<CategoriasDeDescuento>();
            while (rs.next()) {
                CategoriasDeDescuento t = new CategoriasDeDescuento();
                t.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                t.setNombre(rs.getString("NOMBRE"));
                t.setDescripcion(rs.getString("DESCRIPCION"));
                t.setValor(rs.getDouble("VALOR"));
                t.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                t.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                t.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                t.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                t.setEs_fija(rs.getInt("ES_FIJA"));
                t.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                t.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                t.setEstado(rs.getInt("ESTADO"));                
                lstdatos.add(t);
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
        return lstdatos;
    }
    
    public static ArrayList<CategoriasDeDescuento> categoriasDeDescuentoFijasFusa(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CategoriasDeDescuento> lstdatos = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (ES_FIJA=?) AND (ESTADO=?)");
        try {
           con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getEs_fija());
            ps.setInt(2, c.getEstado());
            rs = ps.executeQuery();    
            lstdatos = new ArrayList<>();
            while (rs.next()) {
                CategoriasDeDescuento t = new CategoriasDeDescuento();
                t.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                t.setNombre(rs.getString("NOMBRE"));
                t.setDescripcion(rs.getString("DESCRIPCION"));
                t.setValor(rs.getDouble("VALOR"));
                t.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                t.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                t.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                t.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                t.setEs_fija(rs.getInt("ES_FIJA"));
                t.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                t.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                t.setEstado(rs.getInt("ESTADO"));
                t.setUniqueId(new BigInteger(32, new SecureRandom()).toString(32));
                lstdatos.add(t);
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
        return lstdatos;
    }

    public static CategoriasDeDescuento encontrarCategorias(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoriasDeDescuento t = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (PK_CATEGORIAS_DESCUENTOS=?) AND (ESTADO=?)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getEstado());
            rs = ps.executeQuery();

            while (rs.next()) {
                t = new CategoriasDeDescuento();
                t.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                t.setNombre(rs.getString("NOMBRE"));
                t.setDescripcion(rs.getString("DESCRIPCION"));
                t.setValor(rs.getDouble("VALOR"));
                t.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                t.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                t.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                t.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                t.setEs_fija(rs.getInt("ES_FIJA"));
                t.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                t.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                t.setEstado(rs.getInt("ESTADO"));
                t.setUniqueId(new BigInteger(32, new SecureRandom()).toString(32));
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
        return t;
    }
        
    public static CategoriasDeDescuento encontrarCategoriasFusa(CategoriasDeDescuento c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoriasDeDescuento t = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_categoria_descuento WHERE (PK_CATEGORIAS_DESCUENTOS=?) AND (ESTADO=?)");
        try {
             con.setAutoCommit(false);
             ps = con.prepareStatement(sql.toString());
             ps.setInt(1, c.getId());
             ps.setInt(2, c.getEstado());
             rs = ps.executeQuery();

            while (rs.next()) {
                t = new CategoriasDeDescuento();
                t.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                t.setNombre(rs.getString("NOMBRE"));
                t.setDescripcion(rs.getString("DESCRIPCION"));
                t.setValor(rs.getDouble("VALOR"));
                t.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                t.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                t.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                t.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                t.setEs_fija(rs.getInt("ES_FIJA"));
                t.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                t.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                t.setEstado(rs.getInt("ESTADO"));
                t.setUniqueId(new BigInteger(32, new SecureRandom()).toString(32));
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
        return t;
    }

}//FIN DE CLASE

