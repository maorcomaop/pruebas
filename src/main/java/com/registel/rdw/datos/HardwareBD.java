/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.GpsHardwareMovil;
import com.registel.rdw.logica.Hardware;
import com.registel.rdw.logica.Movil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lider_desarrollador
 */
public class HardwareBD {

    public static boolean exist(Hardware p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id FROM tbl_hardware WHERE (id = ?)";

        try {
            ps = con.prepareCall(sql);
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

    public static int insertOneHardware(Hardware p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int idPropietario = 0;
        sql.append("INSERT INTO tbl_hardware (nombre, descripcion) VALUES (?,?)");

        if (exist(p)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getDescripcion());
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPropietario = rs.getInt(1);
                }
                con.commit();
                if (retorno > 0) {
                    return idPropietario;
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
    }/*
    public static int updateOneRelationshipOwnerCar (PropietarioVehiculo p) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_propietario_vehiculo as pv SET pv.fk_propietario=?, pv.fk_vehiculo=? WHERE (pv.id = ?)");             
            try {                
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setInt(1, p.getFk_propietario());                
                ps.setInt(2, p.getFk_vehiculo());                
                ps.setInt(3, p.getId());                
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
    }*/
    public static ArrayList<Hardware> selectAllHardware(Hardware h) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, nombre, descripcion, estado FROM tbl_hardware WHERE (estado = ?)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, h.getEstado());
            rs = ps.executeQuery();

            ArrayList<Hardware> lst_alm = new ArrayList<>();
            while (rs.next()) {
                Hardware a = new Hardware();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));
                a.setEstado(rs.getInt("estado"));
                lst_alm.add(a);
            }
            return lst_alm;
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

    public static Hardware searchHardwareById(RelationShipHardwareGpsCar c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Hardware v;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT id, nombre, estado FROM tbl_hardware WHERE (id = ?) AND (estado = 1)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, c.getFk_gps());
            rs = ps.executeQuery();
            v = null;
            if (rs.next()) {
                v = new Hardware();
                v.setId(rs.getInt("id"));
                v.setNombre(rs.getString("nombre"));
                v.setEstado(rs.getInt("estado"));
                return v;
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

}
