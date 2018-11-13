/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.ProgramacionRuta;
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
public class ProgramacionRutaBD {
    
    // Consulta nombres de agrupacion de vehiculos asociados a una ruta
    // por dia de semana.
    public static ProgramacionRuta select(int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " (SELECT lower(ipgr.NOMBRE) FROM tbl_pgr AS ipgr WHERE"
                        + "  ipgr.PK_PGR = pgr.FK_PGR AND ipgr.ESTADO = 1) AS NOMBRE_PROGRAMACION,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.LUN AND g.ESTADO = 1) AS GRUPO_LUN,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.MAR AND g.ESTADO = 1) AS GRUPO_MAR,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.MIE AND g.ESTADO = 1) AS GRUPO_MIE,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.JUE AND g.ESTADO = 1) AS GRUPO_JUE,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.VIE AND g.ESTADO = 1) AS GRUPO_VIE,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.SAB AND g.ESTADO = 1) AS GRUPO_SAB,"
                        + " (SELECT lower(g.NOMBRE) FROM tbl_agrupacion AS g WHERE"
                        + "  g.PK_AGRUPACION = pgr.DOM AND g.ESTADO = 1) AS GRUPO_DOM"
                    + " FROM tbl_programacion_ruta AS pgr"
                    + " INNER JOIN tbl_ruta AS r ON"
                    + "  r.PK_RUTA = pgr.FK_RUTA AND r.ESTADO = 1"
                    + " WHERE pgr.FK_RUTA = ? AND pgr.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            rs = ps.executeQuery();
            
            if (rs.next()) {
               ProgramacionRuta pgr = new ProgramacionRuta();                              
               pgr.setNombreProgramacion(rs.getString("NOMBRE_PROGRAMACION"));
               pgr.setNgrupo_lun(rs.getString("GRUPO_LUN"));
               pgr.setNgrupo_mar(rs.getString("GRUPO_MAR"));
               pgr.setNgrupo_mie(rs.getString("GRUPO_MIE"));
               pgr.setNgrupo_jue(rs.getString("GRUPO_JUE"));
               pgr.setNgrupo_vie(rs.getString("GRUPO_VIE"));
               pgr.setNgrupo_sab(rs.getString("GRUPO_SAB"));
               pgr.setNgrupo_dom(rs.getString("GRUPO_DOM"));
               
               return pgr;               
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }        
        return null;    
    }
    
    // Consulta registros (nombre ruta, identificador de agrupacion por dia de semana) 
    // de programacion de ruta activa.
    public static ArrayList<ProgramacionRuta> selectIds() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;
        
        String sql = "SELECT r.NOMBRE, pgr.* FROM tbl_programacion_ruta AS pgr"
                    + " INNER JOIN tbl_ruta AS r ON"    
                    + "  r.PK_RUTA = pgr.FK_RUTA AND r.ESTADO = 1"
                    + " WHERE pgr.ESTADO = 1";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            ArrayList<ProgramacionRuta> lst_pgruta = new ArrayList<ProgramacionRuta>();
            while (rs.next()) {
               ProgramacionRuta pgr = new ProgramacionRuta();
               pgr.setIdRuta(rs.getInt("FK_RUTA"));
               pgr.setNombreRuta(rs.getString("NOMBRE"));
               pgr.setGrupo_lun(rs.getInt("LUN"));
               pgr.setGrupo_mar(rs.getInt("MAR"));
               pgr.setGrupo_mie(rs.getInt("MIE"));
               pgr.setGrupo_jue(rs.getInt("JUE"));
               pgr.setGrupo_vie(rs.getInt("VIE"));
               pgr.setGrupo_sab(rs.getInt("SAB"));
               pgr.setGrupo_dom(rs.getInt("DOM"));
               
               lst_pgruta.add(pgr);
            }
            return lst_pgruta;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeStatement(st);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }        
        return null;
    }
    
    // Consulta nombre de agrupacion de vehiculos para una agrupacion especifica.
    public static String selectNombreAgrupacion(int idGrupo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT a.NOMBRE FROM tbl_agrupacion AS a"
                    + " WHERE a.PK_AGRUPACION = ? AND a.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idGrupo);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("NOMBRE");
            }
            
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return "NA";
    }
    
    // Consulta vehiculos (placa/numero interno) asociados a una agrupacion especifica.
    public static String[] selectMoviles(int idGrupo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Tener en cuenta longitud de variable
        // SET group_concat_max_len = 2048

        String sql = "SELECT"
                        + " group_concat(v.NUM_INTERNO separator '  ') AS MOVILES_NUMERO_INTERNO,"
                        + " group_concat(v.PLACA separator '  ') AS MOVILES_PLACA"
                        + " FROM tbl_agrupacion_vehiculo AS av"
                        + " INNER JOIN tbl_vehiculo AS v ON"
                        + "  v.PK_VEHICULO = av.FK_VEHICULO AND v.ESTADO = 1"
                        + " WHERE av.FK_AGRUPACION = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idGrupo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String arr_veh[] = new String[2];
                arr_veh[0] = rs.getString("MOVILES_NUMERO_INTERNO");
                arr_veh[1] = rs.getString("MOVILES_PLACA");
                return arr_veh;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }               
        String arr[] = { "NA", "NA" }; // a b
        return arr;        
    }
    
    // Consulta y asocia identificadores de agrupaciones de vehiculos
    // con sus nombres de agrupacion y vehiculos de programacion de ruta
    // activa.
    public static ArrayList<ProgramacionRuta> select() {
        
        // Consulta programacion de ruta activa (rutas establecidas)
        ArrayList<ProgramacionRuta> lst_pgruta = selectIds();        
        
        for (int i = 0; i < lst_pgruta.size(); i++) {
            ProgramacionRuta pgr = lst_pgruta.get(i);            
            
            // Consulta vehiculos de agrupacion
            String lst_veh_lun[] = selectMoviles(pgr.getGrupo_lun());
            String lst_veh_mar[] = selectMoviles(pgr.getGrupo_mar());
            String lst_veh_mie[] = selectMoviles(pgr.getGrupo_mie());
            String lst_veh_jue[] = selectMoviles(pgr.getGrupo_jue());
            String lst_veh_vie[] = selectMoviles(pgr.getGrupo_vie());
            String lst_veh_sab[] = selectMoviles(pgr.getGrupo_sab());
            String lst_veh_dom[] = selectMoviles(pgr.getGrupo_dom());
            
            // Consulta nombre de agrupacion
            String ngrupo_lun = selectNombreAgrupacion(pgr.getGrupo_lun());
            String ngrupo_mar = selectNombreAgrupacion(pgr.getGrupo_mar());
            String ngrupo_mie = selectNombreAgrupacion(pgr.getGrupo_mie());
            String ngrupo_jue = selectNombreAgrupacion(pgr.getGrupo_jue());
            String ngrupo_vie = selectNombreAgrupacion(pgr.getGrupo_vie());
            String ngrupo_sab = selectNombreAgrupacion(pgr.getGrupo_sab());
            String ngrupo_dom = selectNombreAgrupacion(pgr.getGrupo_dom());
            
            pgr.setGrupo_lun_numinterno(lst_veh_lun[0]);
            pgr.setGrupo_mar_numinterno(lst_veh_mar[0]);
            pgr.setGrupo_mie_numinterno(lst_veh_mie[0]);
            pgr.setGrupo_jue_numinterno(lst_veh_jue[0]);
            pgr.setGrupo_vie_numinterno(lst_veh_vie[0]);
            pgr.setGrupo_sab_numinterno(lst_veh_sab[0]);
            pgr.setGrupo_dom_numinterno(lst_veh_dom[0]);
            
            pgr.setGrupo_lun_placa(lst_veh_lun[1]);
            pgr.setGrupo_mar_placa(lst_veh_mar[1]);
            pgr.setGrupo_mie_placa(lst_veh_mie[1]);
            pgr.setGrupo_jue_placa(lst_veh_jue[1]);
            pgr.setGrupo_vie_placa(lst_veh_vie[1]);
            pgr.setGrupo_sab_placa(lst_veh_sab[1]);
            pgr.setGrupo_dom_placa(lst_veh_dom[1]);            
            
            pgr.setNgrupo_lun(ngrupo_lun);
            pgr.setNgrupo_mar(ngrupo_mar);
            pgr.setNgrupo_mie(ngrupo_mie);
            pgr.setNgrupo_jue(ngrupo_jue);
            pgr.setNgrupo_vie(ngrupo_vie);
            pgr.setNgrupo_sab(ngrupo_sab);
            pgr.setNgrupo_dom(ngrupo_dom);
        }
        
        return lst_pgruta;
    }
}
