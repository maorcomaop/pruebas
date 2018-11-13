/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import static com.registel.rdw.datos.MovilBD.selectId;
import com.registel.rdw.logica.PuntoRuta;
import com.registel.rdw.logica.DetalleRuta;
import com.registel.rdw.logica.Ruta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static com.registel.rdw.datos.MovilBD.selectId;
import static com.registel.rdw.datos.MovilBD.selectId;
import static com.registel.rdw.datos.MovilBD.selectId;

/**
 *
 * @author lider_desarrollador
 */
public class RutaBD {
    
    // Inserta registro de nueva ruta.
    public static boolean insert (Ruta r) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql =
                "INSERT INTO tbl_ruta (NOMBRE, HISTORIAL, ESTADO_CREACION, FK_EMPRESA, MODIFICACION_LOCAL, ESTADO) " +
                    "VALUES (?,?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setInt(2, 1);
            ps.setInt(3, 1);
            ps.setInt(4, r.getIdEmpresa());
            ps.setInt(5, 1);
            ps.setInt(6, 1);
            
            ps.executeUpdate();            
            return true;
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta ruta especifica segun identificador.
    public static Ruta get (String idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_ruta WHERE PK_RUTA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idRuta));
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Ruta r = new Ruta();
                r.setId(rs.getInt("PK_RUTA"));
                r.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                r.setNombre(rs.getString("NOMBRE"));

                return r;
            }
            
        } catch (SQLException e) {
            System.err.println(e); 
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Actualiza valores de una ruta especifica.
    public static boolean update (Ruta r) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_ruta SET NOMBRE = ?, FK_EMPRESA = ? WHERE " +
                "PK_RUTA = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getIdEmpresa());
            ps.setInt(3, r.getId());            
            ps.executeUpdate();     
            
            return true;
            
        } catch (SQLException e) {
            System.out.println(e); 
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Desactiva ruta junto puntos asociados de una ruta en especifico.
    public static boolean remove (String idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
                
        String sql1 = "UPDATE tbl_ruta SET ESTADO = 0 WHERE PK_RUTA = ?";
        String sql2 = "UPDATE tbl_ruta_punto SET ESTADO = 0 WHERE FK_RUTA = ?";
        
        try {
            con.setAutoCommit(false);
            
            ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, Integer.parseInt(idRuta));
            ps1.executeUpdate();
            
            ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, Integer.parseInt(idRuta));
            ps2.executeUpdate();
            
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
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
            UtilBD.closePreparedStatement(ps1);
            UtilBD.closePreparedStatement(ps2);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta minima (identificador, distancia) de rutas activas.
    public static ArrayList<Ruta> select () {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_ruta AS r WHERE r.ESTADO = 1";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            ArrayList<Ruta> lst_ruta = new ArrayList<Ruta>();
            while (rs.next()) {
                Ruta r = new Ruta();
                r.setId(rs.getInt("PK_RUTA"));                                
                r.setDistanciaMetros(rs.getInt("DISTANCIA_METROS"));
                lst_ruta.add(r);
            }
            return lst_ruta;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta longitud de ruta promedio para un mes y anio en especifico,
    // siempre que el porcentaje de coincidencia de ruta sea superior al 80%.
    public static ArrayList<Ruta> selectLongitudRuta(int mes, int anio) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"                    
                    + " FK_RUTA,"
                    + " avg(DISTANCIA_METROS) AS DISTANCIA_METROS"
                    + " FROM tbl_informacion_registradora AS ir"
                    + " WHERE"
                    + "  ir.PORCENTAJE_RUTA >= .80 AND"
                    + "  month(ir.FECHA_INGRESO) = ? AND"
                    + "  year(ir.FECHA_INGRESO) = ?"
                    + "  GROUP BY ir.FK_RUTA";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mes);
            ps.setInt(2, anio);
            rs = ps.executeQuery();
            
            ArrayList<Ruta> lst_ruta = new ArrayList<Ruta>();
            while (rs.next()) {
                Ruta r = new Ruta();
                r.setId(rs.getInt("FK_RUTA"));
                r.setDistanciaMetros((int) rs.getDouble("DISTANCIA_METROS"));
                lst_ruta.add(r);
            }
            return lst_ruta;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Actualiza longitud de ruta para un listado especifico.
    public static boolean updateLongitudRuta(ArrayList<String> lst_sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();        
        Statement st = null;
        int n = 0;
        
        try {
            con.setAutoCommit(false);
            
            for (String sql : lst_sql) {
                st = con.createStatement();
                n += st.executeUpdate(sql);
            }
            if (n == lst_sql.size()) {
                con.commit();
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
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
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta minima (identificador) de rutas activas.
    public static ArrayList<Integer> selectId () {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT r.PK_RUTA FROM tbl_ruta AS r WHERE r.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Integer> lst_id = new ArrayList<Integer>();
            while (rs.next()) {
                lst_id.add(rs.getInt("PK_RUTA"));
            } 
            return lst_id;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Consulta minima (identificador) de rutas activas asociadas a una empresa.
    public static ArrayList<Integer> selectId (int idEmpresa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT r.PK_RUTA FROM tbl_ruta AS r"
                    + " WHERE r.FK_EMPRESA = ? AND r.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();
            
            ArrayList<Integer> lst_id = new ArrayList<Integer>();
            while (rs.next()) {
                lst_id.add(rs.getInt("PK_RUTA"));
            } 
            return lst_id;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Convierte listado de identificadores de ruta en una cadena
    // de texto formateada (separa por ,) asociadas a una empresa.
    public static String selectStringId(int idEmpresa) {
       
        // Consulta identificadores de rutas
        ArrayList<Integer> lst_id = selectId(idEmpresa);
        String s = "";
        for (Integer id : lst_id) {
            s += (s == "") ? id.intValue() : "," + id.intValue();
        }
        return s;
    }
    
    // Consulta listado de rutas junto con sus puntos asociados.
    public static ArrayList<DetalleRuta> selectDetalleRuta() {
        ArrayList<Integer> lst_idRuta = RutaBD.selectId();
        ArrayList<DetalleRuta> lst_prd = new ArrayList<DetalleRuta>();
        
        for (Integer idRuta : lst_idRuta) {
            // Consulta identificadores de puntos para ruta especificada
            ArrayList<PuntoRuta> lst_pr = PuntoRutaBD.selectIdPuntoByRuta(idRuta);
            
            DetalleRuta prd = new DetalleRuta();
            prd.setIdRuta(idRuta);
            prd.setLst(lst_pr);
            
            lst_prd.add(prd);
            
        } return lst_prd;
    }    
}
