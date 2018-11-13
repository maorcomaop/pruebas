/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Base;
import com.registel.rdw.logica.Punto;
import com.registel.rdw.utils.Coordenadas;
import com.registel.rdw.utils.Restriction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class PuntoBD {
    
    // Comprubea si existe registro de punto especifico
    // segun su codigo asociado.
    public static boolean exist (Punto p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT p.PK_PUNTO FROM tbl_punto as p WHERE " +
                "p.CODIGO_PUNTO = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getCodigoPunto());
            rs = ps.executeQuery();
            
            if (rs.next())
                return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Inserta registro de punto nuevo, verificando antes
    // su no existencia.
    public static boolean insert (Punto p) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        if (!Restriction.isNamePoint(p.getNombre())) {
            return false;
        }
        
        String sql = "INSERT INTO tbl_punto (NOMBRE, DESCRIPCION, LATITUD, LONGITUD, CODIGO_PUNTO, " + 
                "DIRECCION_LATITUD, DIRECCION_LONGITUD, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL, LATITUD_WEB, LONGITUD_WEB) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        if (exist(p)) {
            String msg = "* Registro de punto ya existe.";
            throw new ExisteRegistroBD (msg);            
        } else {
            try {
                // Conversion de coordenadas a formato Grados Minutos GMD (usado en regisdata)
                p.setLatitud(Coordenadas.toGMD(p.getLatitud()));
                p.setLongitud(Coordenadas.toGMD(p.getLongitud()));
                
                ps = con.prepareStatement(sql);
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getDescripcion());
                ps.setString(3, p.getLatitud());
                ps.setString(4, p.getLongitud());
                ps.setInt(5, p.getCodigoPunto());
                ps.setString(6, p.getDireccionLatitud());
                ps.setString(7, p.getDireccionLongitud());
                ps.setInt(8, p.getRadio());
                ps.setInt(9, p.getDireccion());
                ps.setInt(10, 1);
                ps.setInt(11, 1);
                
                ps.setString(12, p.getLatitudWeb());
                ps.setString(13, p.getLongitudWeb());

                ps.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.err.println(e); 
            } finally {
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            } return false;
        }
    }
    
    // Actualiza valores de un punto especifico.
    public static boolean update (Punto p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        if (!Restriction.isNamePoint(p.getNombre())) {
            return false;
        }
        
        String sql = "UPDATE tbl_punto SET NOMBRE=?, DESCRIPCION=?, LATITUD=?, LONGITUD=?, " + 
                "DIRECCION_LATITUD=?, DIRECCION_LONGITUD=?, RADIO=?, DIRECCION=?, LATITUD_WEB=?, LONGITUD_WEB=? " +
                "WHERE CODIGO_PUNTO=? AND ESTADO = 1";        
        
        try {
            // Conversion de coordenadas a formato Grados Minutos GMD (usado en regisdata)
            p.setLatitud(Coordenadas.toGMD(p.getLatitud()));
            p.setLongitud(Coordenadas.toGMD(p.getLongitud()));
            
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getLatitud());
            ps.setString(4, p.getLongitud());            
            ps.setString(5, p.getDireccionLatitud());
            ps.setString(6, p.getDireccionLongitud());
            ps.setInt(7, p.getRadio());
            ps.setInt(8, p.getDireccion());
            
            ps.setString(9, p.getLatitudWeb());
            ps.setString(10, p.getLongitudWeb());
            
            ps.setInt(11, p.getCodigoPunto());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    public static boolean pointInUse(int idpunto) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "select"
                    + "     rp.FK_PUNTO"
                    + " from tbl_ruta_punto as rp"
                    + " inner join tbl_ruta as r on"
                    + "     rp.FK_RUTA = r.PK_RUTA" // and rp.ESTADO = 1
                    + " where r.ESTADO = 1 and"
                    + " rp.FK_PUNTO = ?"
                    + " limit 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idpunto);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return true;
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Desactiva registro de un punto especifico.
    public static boolean remove (int idpunto) throws PuntoRelacionadoBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps1 = null;
        //PreparedStatement ps2 = null;
        
        if (pointInUse(idpunto)) {
            throw new PuntoRelacionadoBD();
        }
        
        String sql1 = "UPDATE tbl_punto SET ESTADO = 0 WHERE PK_PUNTO = ?";
        //String sql2 = "UPDATE tbl_ruta_punto SET ESTADO = 0 WHERE FK_PUNTO = ?";
        
        try {
            con.setAutoCommit(false);
            ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, idpunto);
            ps1.executeUpdate();
            
            //ps2 = con.prepareStatement(sql2);
            //ps2.setInt(1, idpunto);
            //ps2.executeUpdate();
            
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ie) {
                System.err.println(ie);
            } 
        } finally {
            try { con.setAutoCommit(false); } 
            catch (SQLException e) { System.out.println(e); }
            UtilBD.closePreparedStatement(ps1);
            //UtilBD.closePreparedStatement(ps2);
            pila_con.liberarConexion(con);
        } return false;         
    }
    
    //    public static boolean remove (int idpunto) {
    //        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
    //        Connection con = pila_con.obtenerConexion();
    //        PreparedStatement ps = null;
    //        
    //        String sql = "UPDATE tbl_punto SET ESTADO = 0 " +
    //                  "WHERE PK_PUNTO = ?";
    //                //"WHERE CODIGO_PUNTO = ?";
    //                
    //        try {
    //            ps = con.prepareStatement(sql);
    //            ps.setInt(1, idpunto);
    //            
    //            ps.executeUpdate();
    //            return true;
    //            
    //        } catch (SQLException e) {
    //            System.err.println(e); return false;
    //        } finally {
    //            UtilBD.closePreparedStatement(ps);
    //            pila_con.liberarConexion(con);
    //        }
    //    }
    
    // Inserta bloque de puntos (conjunto de instrucciones de insercion SQL)
    public static boolean insertBlock (String sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        
        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta puntos cuya coordenada web no se encuentra establecida,
    // realiza conversion (grados decimales) con respecto a coordenada 
    // normal establecida (campos latitud y longitud) en tabla tbl_punto.
    public static ArrayList<Punto> selectTranslateCoord () {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_PUNTO, LATITUD, LONGITUD, DIRECCION_LONGITUD, DIRECCION_LATITUD FROM tbl_punto WHERE"
                + " LATITUD_WEB IS NULL OR LONGITUD_WEB IS NULL AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Punto> lst = new ArrayList<Punto>();
            while (rs.next()) {
                Punto p = new Punto();
                p.setIdpunto(rs.getInt("PK_PUNTO"));
                p.setLatitud(rs.getString("LATITUD"));
                p.setLongitud(rs.getString("LONGITUD"));
                p.setDireccionLatitud(rs.getString("DIRECCION_LATITUD"));
                p.setDireccionLongitud(rs.getString("DIRECCION_LONGITUD"));
                
                // Conversion de coordenadas a Grados Decimales GD - formato web
                p.setLatitudWeb(Coordenadas.toGD(p.getLatitud(), p.getDireccionLatitud()));
                p.setLongitudWeb(Coordenadas.toGD(p.getLongitud(), p.getDireccionLongitud()));
                
                lst.add(p);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Actualiza valor de coordenadas web para un listado de puntos
    // especificado en tabla tbl_punto.
    public static boolean updateTranslateCoord (ArrayList<Punto> lst) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
               
        try {
            con.setAutoCommit(false);
            for (Punto p : lst) {
                String sql = "UPDATE tbl_punto SET LATITUD_WEB = " + p.getLatitudWeb()
                        + ", LONGITUD_WEB = " + p.getLongitudWeb() + " WHERE PK_PUNTO = " + p.getIdpunto();
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
            }
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) { System.err.println(ie); }
            } 
        } finally {
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta registros de puntos activos.
    public static ArrayList<Punto> select ()  {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_punto p WHERE p.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Punto> lst_punto = new ArrayList<Punto>();
            while (rs.next()) {
                Punto p = new Punto();
                p.setIdpunto(rs.getInt("PK_PUNTO"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setDescripcion(rs.getString("DESCRIPCION"));
                p.setCodigoPunto(rs.getInt("CODIGO_PUNTO"));
                p.setLatitud(rs.getString("LATITUD"));
                p.setLongitud(rs.getString("LONGITUD"));
                p.setDireccionLatitud(rs.getString("DIRECCION_LATITUD"));
                p.setDireccionLongitud(rs.getString("DIRECCION_LONGITUD"));
                p.setDireccion(rs.getInt("DIRECCION"));
                p.setRadio(rs.getInt("RADIO"));
                p.setEstado(rs.getInt("ESTADO"));
                
                // Conversion de coordenadas a Grados Decimales GD - formato web
                //p.setLatitud(Coordenadas.toGD(p.getLatitud(), p.getDireccionLatitud()));
                //p.setLongitud(Coordenadas.toGD(p.getLongitud(), p.getDireccionLongitud()));
                
                p.setLatitudWeb(rs.getString("LATITUD_WEB"));
                p.setLongitudWeb(rs.getString("LONGITUD_WEB"));
                
                lst_punto.add(p);
            } 
            return lst_punto;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Consulta minima (identificador, codigo) de puntos activos.
    public static ArrayList<Punto> selectMin () {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_PUNTO, CODIGO_PUNTO FROM tbl_punto WHERE ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Punto> lst = new ArrayList<Punto>();
            while (rs.next()) {
                Punto p = new Punto();
                p.setIdpunto(rs.getInt("PK_PUNTO"));
                p.setCodigoPunto(rs.getInt("CODIGO_PUNTO"));
                String etiqueta_punto = "p" + p.getCodigoPunto();
                p.setEtiquetaPunto(etiqueta_punto);
                
                lst.add(p);
            }
            return lst;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta de puntos creados y/o modificados
    // en fecha superior o igual a la especificada.
    public static ArrayList<Punto> selectPuntosRecientes(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql =  "SELECT * FROM ("
                        + " SELECT"
                        + "   PK_PUNTO AS ID,"
                        + "   CODIGO_PUNTO AS CODIGO,"
                        + "   'punto' AS TIPO,"
                        + "   NOMBRE AS NOMBRE,"
                        + "   LATITUD_WEB AS LATITUD,"
                        + "   LONGITUD_WEB AS LONGITUD,"
                        + "   FECHA_MODIFICACION AS FECHA_CREACION"
                        + " FROM tbl_punto WHERE"
                        + "   ESTADO = 1 AND"
                        + "   date(FECHA_MODIFICACION) >= ?"
                        + " UNION"
                        + " SELECT "
                        + "   PK_BASE AS ID,"
                        + "   IDENTIFICADOR_BASE AS CODIGO,"
                        + "   'base' AS TIPO,"
                        + "   NOMBRE AS NOMBRE,"
                        + "   LATITUD_WEB AS LATITUD,"
                        + "   LONGITUD_WEB AS LONGITUD,"
                        + "   FECHA_MODIFICACION AS FECHA_CREACION"
                        + " FROM tbl_base WHERE"
                        + "  ESTADO = 1 AND"
                        + "  date(FECHA_MODIFICACION) >= ?"
                    + ") AS s ORDER BY s.FECHA_CREACION DESC";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, fecha);
            rs = ps.executeQuery();
            
            ArrayList<Punto> lst = new ArrayList<Punto>();
            while (rs.next()) {
                Punto p = new Punto(); 
                p.setIdpunto(rs.getInt("ID"));
                p.setCodigoPunto(rs.getInt("CODIGO"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setLatitudWeb(rs.getString("LATITUD"));
                p.setLongitudWeb(rs.getString("LONGITUD"));
                p.setTipo(rs.getString("TIPO"));
                lst.add(p);
            }           
            return lst;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
}

