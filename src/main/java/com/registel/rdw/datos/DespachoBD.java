/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Base;
import com.registel.rdw.logica.Despacho;
import com.registel.rdw.logica.GrupoMovilDespacho;
import com.registel.rdw.logica.IntervaloDespacho;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.PlanillaDespachoMin;
import com.registel.rdw.logica.ProgramacionRuta;
import com.registel.rdw.logica.Punto;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.TimeUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class DespachoBD {
    
    private static SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm");   
    private static SimpleDateFormat hfmt_full = new SimpleDateFormat("HH:mm:ss");   
    private static int TIPO_GENERACION_DPH = Constant.DPH_PLANIFICADO;
    public  static final String NODATA     = "NODATA";
    
    public static void tipogeneracion_despacho(int tipo_dph) {
        TIPO_GENERACION_DPH = tipo_dph;
    }
    
    public static int obtener_tipogeneracion_despacho() {
        return TIPO_GENERACION_DPH;
    }

    // Consulta identificadores de configuracion despacho activos
    public static ArrayList<Integer> select_id() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_CONFIGURACION_DESPACHO FROM tbl_configuracion_despacho"
                        + " WHERE ESTADO = 1";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            ArrayList<Integer> lst = new ArrayList<Integer>();
            while (rs.next()) {
                lst.add(rs.getInt("PK_CONFIGURACION_DESPACHO"));
            }
            
            return lst;
        
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Comprueba si existe registro despacho con ruta y tipo de dia especifico
    public static boolean exist(Despacho dph) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT cd.PK_CONFIGURACION_DESPACHO"
                   + " FROM tbl_configuracion_despacho AS cd"
                   + " WHERE cd.FK_RUTA = ? AND cd.TIPO_DIA = ? AND cd.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, dph.getIdRuta());
            ps.setString(2, dph.getTipoDia());
            rs = ps.executeQuery();
            
            if (rs.next())
                return true;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta tipos de dias almacenados de una ruta
    public static ArrayList<String> select_tipos_dias(int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT cd.TIPO_DIA"
                        + " FROM tbl_configuracion_despacho AS cd"
                        + " WHERE cd.FK_RUTA = ? AND cd.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            rs = ps.executeQuery();
            
            ArrayList<String> lst = new ArrayList<String>();
            while (rs.next()) {
                lst.add(rs.getString("TIPO_DIA"));
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
    
    // Inserta configuracion de nuevo despacho
    public static boolean insert(Despacho dph) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "INSERT INTO tbl_configuracion_despacho ("
                        + "NOMBRE_DESPACHO,"
                        + "FK_RUTA,"
                        + "FK_GRUPO_VEHICULO,"
                        + "TIPO_DIA,"
                        + "CANTIDAD_VUELTAS,"
                        + "LISTA_VEHICULOS,"
                        + "LISTA_PUNTOS,"
                        + "HORA_INICIO_RUTA,"
                        + "HORA_FIN_RUTA,"                        
                        + "LISTA_HORAS_PICO,"
                        + "TIEMPO_REFERENCIA,"
                        + "TIEMPO_HOLGURA,"
                        + "TIEMPO_ENTRE_PUNTOS_VALLE,"
                        + "TIEMPO_ENTRE_PUNTOS_PICO,"                        
                        + "INTERVALO_DESPACHO_VALLE,"
                        + "INTERVALO_DESPACHO_PICO,"
                        + "TIEMPO_SALIDA,"
                        + "TIEMPO_SALIDA_PICO,"
                        + "HORAS_TRABAJO,"
                        + "PUNTO_RETORNO,"
                        + "HORA_INICIO_RETORNO,"
                        + "INTERVALO_RETORNO,"
                        + "TIEMPO_SALIDA_RETORNO,"
                        + "TIEMPO_AJUSTE_RETORNO,"
                        + "LISTA_VEHICULOS_RETORNO,"
                        + "ROTACION_VEHICULOS,"
                        + "PROGRAMACION_RUTA,"
                        + "TIPO_PROGRAMACION_RUTA,"
                        + "LISTA_ID_PUNTOS,"
                        + "LIMITE_CONSERVACION_ROTACION,"
                        + "CANTIDAD_VEHICULOS_RETORNO,"
                        + "ESTADO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
                
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, dph.getNombreDespacho());
            ps.setInt(2, dph.getIdRuta());
            ps.setInt(3, dph.getIdGrupoMoviles());
            ps.setString(4, dph.getTipoDia());
            ps.setInt(5, dph.getCantidadVueltas());
            ps.setString(6, dph.getGrupoMoviles());
            ps.setString(7, dph.getPuntosRuta());
            ps.setTime(8, dph.getHoraInicio());
            ps.setTime(9, dph.getHoraFin());
            ps.setString(10, dph.getHoraPico());
            ps.setString(11, dph.getTiempoReferencia());
            ps.setString(12, dph.getTiempoHolgura());
            ps.setString(13, dph.getTiempoLlegadaValle());
            ps.setString(14, dph.getTiempoLlegadaPico());
            ps.setInt(15, dph.getIntervaloValle());
            ps.setInt(16, dph.getIntervaloPico());
            ps.setInt(17, dph.getTiempoSalida());
            ps.setInt(18, dph.getTiempoSalidaPico());
            ps.setInt(19, dph.getHorasTrabajo());
            ps.setString(20, dph.getPuntoRetorno());
            ps.setTime(21, dph.getHoraInicioRetorno());
            ps.setInt(22, dph.getIntervaloRetorno());
            ps.setInt(23, dph.getTiempoSalidaRetorno());
            ps.setInt(24, dph.getTiempoAjusteRetorno());
            ps.setString(25, dph.getGrupoMovilesRetorno());
            ps.setInt(26, dph.getRotarVehiculo());
            ps.setInt(27, dph.getProgramarRuta());
            ps.setString(28, dph.getTipoProgramacionRuta());
            ps.setString(29, dph.getIdPuntosRuta());
            ps.setInt(30, dph.getLimiteConservacion());
            ps.setInt(31, dph.getCantidadMovilesRetorno());
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }    
    
    // Consulta tiempos (hora inicio y fin) de configuraciones de despacho 
    // de una ruta en especifico
    public static ArrayList<Despacho> selectByRuta(int id_ruta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_configuracion_despacho WHERE FK_RUTA = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_ruta);
            rs = ps.executeQuery();
            
            ArrayList<Despacho> lst = new ArrayList<Despacho>();
            while (rs.next()) {
                Despacho dph = new Despacho();
                dph.setId(rs.getInt("PK_CONFIGURACION_DESPACHO"));
                dph.setNombreDespacho(rs.getString("NOMBRE_DESPACHO"));
                dph.setHoraInicio(rs.getTime("HORA_INICIO_RUTA"));
                dph.setHoraFin(rs.getTime("HORA_FIN_RUTA"));
                dph.setIdRuta(rs.getInt("FK_RUTA"));
                lst.add(dph);
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
    
    // Comprueba si despacho tiene moviles en punto retorno, de serlo
    // retorna arreglo con moviles y establece true en variable de comprobacion,
    // de lo contrario retorna arreglo con valor "NODATA" y false en variable de
    // comprobacion
    public static String[] arrayMovilesRetorno(Despacho dph, String sep) {
        
        String array[] = { NODATA };        
        String str = dph.getGrupoMovilesRetorno();
        
        if (str == null || str == "" ||
            str.compareTo("null") == 0 ||
            str.compareTo(NODATA) == 0) {
            dph.movilesEnRetorno(false);
        } else {
            array = str.split(sep);
            dph.movilesEnRetorno(true);
        }
        return array;
    }
    
    // Consulta configuracion de despacho con identificador especifico
    public static Despacho select(int idDespacho) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT *, r.NOMBRE AS NOMBRE_RUTA FROM tbl_configuracion_despacho AS dph"
                        + " INNER JOIN tbl_ruta AS r ON"
                        + " r.PK_RUTA = dph.FK_RUTA AND r.ESTADO = 1"
                        + " WHERE dph.PK_CONFIGURACION_DESPACHO = ? AND dph.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idDespacho);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Despacho dph = new Despacho();
                dph.setId(rs.getInt("PK_CONFIGURACION_DESPACHO"));
                dph.setNombreDespacho(rs.getString("NOMBRE_DESPACHO"));
                dph.setHoraInicio(rs.getTime("HORA_INICIO_RUTA"));
                dph.setHoraFin(rs.getTime("HORA_FIN_RUTA"));
                dph.setHoraPico(rs.getString("LISTA_HORAS_PICO"));
                dph.setGrupoMoviles(rs.getString("LISTA_VEHICULOS"));
                dph.setIdPuntosRuta(rs.getString("LISTA_ID_PUNTOS"));
                dph.setPuntosRuta(rs.getString("LISTA_PUNTOS"));
                dph.setIdRuta(rs.getInt("FK_RUTA"));
                dph.setIdGrupoMoviles(rs.getInt("FK_GRUPO_VEHICULO"));
                dph.setNombreRuta(rs.getString("NOMBRE_RUTA"));                
                dph.setTiempoReferencia(rs.getString("TIEMPO_REFERENCIA"));
                dph.setTiempoHolgura(rs.getString("TIEMPO_HOLGURA"));
                dph.setTiempoLlegadaValle(rs.getString("TIEMPO_ENTRE_PUNTOS_VALLE"));
                dph.setTiempoLlegadaPico(rs.getString("TIEMPO_ENTRE_PUNTOS_PICO"));
                dph.setIntervaloValle(rs.getInt("INTERVALO_DESPACHO_VALLE"));
                dph.setIntervaloPico(rs.getInt("INTERVALO_DESPACHO_PICO"));
                dph.setTiempoSalida(rs.getInt("TIEMPO_SALIDA"));
                dph.setTiempoSalidaPico(rs.getInt("TIEMPO_SALIDA_PICO"));
                dph.setHorasTrabajo(rs.getInt("HORAS_TRABAJO"));
                dph.setPuntoRetorno(rs.getString("PUNTO_RETORNO"));
                dph.setHoraInicioRetorno(rs.getTime("HORA_INICIO_RETORNO"));
                dph.setIntervaloRetorno(rs.getInt("INTERVALO_RETORNO"));
                dph.setTiempoSalidaRetorno(rs.getInt("TIEMPO_SALIDA_RETORNO"));
                dph.setTiempoAjusteRetorno(rs.getInt("TIEMPO_AJUSTE_RETORNO"));
                dph.setGrupoMovilesRetorno(rs.getString("LISTA_VEHICULOS_RETORNO"));
                dph.setTipoDia(rs.getString("TIPO_DIA"));               
                dph.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS"));
                dph.setRotarVehiculo(rs.getInt("ROTACION_VEHICULOS"));
                dph.setProgramarRuta(rs.getInt("PROGRAMACION_RUTA"));
                dph.setTipoProgramacionRuta(rs.getString("TIPO_PROGRAMACION_RUTA"));
                dph.setLimiteConservacion(rs.getInt("LIMITE_CONSERVACION_ROTACION"));
                dph.setCantidadMovilesRetorno(rs.getInt("CANTIDAD_VEHICULOS_RETORNO"));
                
                String lstIdPuntos[]      = dph.getIdPuntosRuta().split(",");
                String lstPuntos[]        = dph.getPuntosRuta().split(",");
                String lstTiempoRef[]     = dph.getTiempoReferencia().split(",");
                String lstTiempoHolgura[] = dph.getTiempoHolgura().split(",");
                String lstTiempoValle[]   = dph.getTiempoLlegadaValle().split(",");
                String lstTiempoPico[]    = dph.getTiempoLlegadaPico().split(",");
                String lstMoviles[]       = dph.getGrupoMoviles().split(",");
                String lstMovilesRetorno[] = arrayMovilesRetorno(dph, ",");
                                
                dph.setListaPuntosRuta(lstPuntos, lstTiempoHolgura, lstTiempoValle, lstTiempoPico);
                dph.setListaPuntosRuta_all(dph.getIdRuta(), lstIdPuntos, lstPuntos, lstTiempoRef, lstTiempoHolgura, lstTiempoValle, lstTiempoPico);
                dph.setListaGrupoMoviles(lstMoviles);
                dph.setListaGrupoMovilesRetorno(lstMovilesRetorno);
                dph.setListaGrupoMoviles_movil(select_moviles(dph.getGrupoMoviles(), dph.getGrupoMovilesRetorno()));                
                dph.setListaGrupoMoviles_all(dph.getIdGrupoMoviles(), dph.getListaGrupoMoviles(), dph.getListaGrupoMovilesRetorno());
                
                String punto_retorno = dph.getPuntoRetorno();
                if (punto_retorno != null && punto_retorno.indexOf(",") >= 0)
                    dph.setIniciaEnPuntoRetorno(true);
                
                String horaPico = dph.getHoraPico();
                dph.setHoraPicoFmt(horaPico);
                if (horaPico != null && horaPico != "") {
                    String lstHoraPico[] = dph.getHoraPico().split(",");
                    dph.setListaHoraPico(lstHoraPico);
                }
                
                return dph;
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {            
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);            
        } return null;
    }        
    
    // Consulta configuraciones de despacho activas
    public static ArrayList<Despacho> select() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT *, r.NOMBRE AS NOMBRE_RUTA FROM tbl_configuracion_despacho AS dph"
                        + " INNER JOIN tbl_ruta AS r ON"
                        + " r.PK_RUTA = dph.FK_RUTA AND r.ESTADO = 1"
                        + " WHERE dph.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList<Despacho> lst_dph = new ArrayList<Despacho>();
            while (rs.next()) {
                Despacho dph = new Despacho();
                dph.setId(rs.getInt("PK_CONFIGURACION_DESPACHO"));
                dph.setNombreDespacho(rs.getString("NOMBRE_DESPACHO"));
                dph.setHoraInicio(rs.getTime("HORA_INICIO_RUTA"));
                dph.setHoraFin(rs.getTime("HORA_FIN_RUTA"));
                dph.setHoraPico(rs.getString("LISTA_HORAS_PICO"));
                dph.setGrupoMoviles(rs.getString("LISTA_VEHICULOS"));
                dph.setIdPuntosRuta(rs.getString("LISTA_ID_PUNTOS"));
                dph.setPuntosRuta(rs.getString("LISTA_PUNTOS"));
                dph.setIdRuta(rs.getInt("FK_RUTA"));
                dph.setIdGrupoMoviles(rs.getInt("FK_GRUPO_VEHICULO"));
                dph.setNombreRuta(rs.getString("NOMBRE_RUTA"));                
                dph.setTiempoReferencia(rs.getString("TIEMPO_REFERENCIA"));
                dph.setTiempoHolgura(rs.getString("TIEMPO_HOLGURA"));
                dph.setTiempoLlegadaValle(rs.getString("TIEMPO_ENTRE_PUNTOS_VALLE"));
                dph.setTiempoLlegadaPico(rs.getString("TIEMPO_ENTRE_PUNTOS_PICO"));
                dph.setIntervaloValle(rs.getInt("INTERVALO_DESPACHO_VALLE"));
                dph.setIntervaloPico(rs.getInt("INTERVALO_DESPACHO_PICO"));
                dph.setTiempoSalida(rs.getInt("TIEMPO_SALIDA"));
                dph.setTiempoSalidaPico(rs.getInt("TIEMPO_SALIDA_PICO"));
                dph.setHorasTrabajo(rs.getInt("HORAS_TRABAJO"));
                dph.setPuntoRetorno(rs.getString("PUNTO_RETORNO"));
                dph.setHoraInicioRetorno(rs.getTime("HORA_INICIO_RETORNO"));
                dph.setIntervaloRetorno(rs.getInt("INTERVALO_RETORNO"));
                dph.setTiempoSalidaRetorno(rs.getInt("TIEMPO_SALIDA_RETORNO"));
                dph.setTiempoAjusteRetorno(rs.getInt("TIEMPO_AJUSTE_RETORNO"));
                dph.setGrupoMovilesRetorno(rs.getString("LISTA_VEHICULOS_RETORNO"));
                dph.setTipoDia(rs.getString("TIPO_DIA"));               
                dph.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS"));
                dph.setRotarVehiculo(rs.getInt("ROTACION_VEHICULOS"));
                dph.setProgramarRuta(rs.getInt("PROGRAMACION_RUTA"));
                dph.setTipoProgramacionRuta(rs.getString("TIPO_PROGRAMACION_RUTA"));
                dph.setLimiteConservacion(rs.getInt("LIMITE_CONSERVACION_ROTACION"));
                dph.setCantidadMovilesRetorno(rs.getInt("CANTIDAD_VEHICULOS_RETORNO"));
                
                String lstIdPuntos[]      = dph.getIdPuntosRuta().split(",");
                String lstPuntos[]        = dph.getPuntosRuta().split(",");
                String lstTiempoRef[]     = dph.getTiempoReferencia().split(",");
                String lstTiempoHolgura[] = dph.getTiempoHolgura().split(",");
                String lstTiempoValle[]   = dph.getTiempoLlegadaValle().split(",");
                String lstTiempoPico[]    = dph.getTiempoLlegadaPico().split(",");
                String lstMoviles[]       = dph.getGrupoMoviles().split(",");
                String lstMovilesRetorno[] = arrayMovilesRetorno(dph, ",");
                                
                dph.setListaPuntosRuta(lstPuntos, lstTiempoHolgura, lstTiempoValle, lstTiempoPico);                
                dph.setListaPuntosRuta_all(dph.getIdRuta(), lstIdPuntos, lstPuntos, lstTiempoRef, lstTiempoHolgura, lstTiempoValle, lstTiempoPico);
                dph.setListaGrupoMoviles(lstMoviles);                
                dph.setListaGrupoMovilesRetorno(lstMovilesRetorno);
                dph.setListaGrupoMoviles_movil(select_moviles(dph.getGrupoMoviles(), dph.getGrupoMovilesRetorno()));                
                dph.setListaGrupoMoviles_all(dph.getIdGrupoMoviles(), dph.getListaGrupoMoviles(), dph.getListaGrupoMovilesRetorno());
                
                String punto_retorno = dph.getPuntoRetorno();
                if (punto_retorno != null && punto_retorno.indexOf(",") >= 0)
                    dph.setIniciaEnPuntoRetorno(true);
                
                String horaPico = dph.getHoraPico();
                dph.setHoraPicoFmt(horaPico);
                if (horaPico != null && horaPico != "") {
                    String lstHoraPico[] = dph.getHoraPico().split(",");
                    dph.setListaHoraPico(lstHoraPico);
                }                
                
                lst_dph.add(dph);
            }
            return lst_dph;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Genera cadena de texto formateada para consulta SQL en base de datos
    public static String formatStringSQL(String s) {
        String arr[] = s.split(",");
        String ss = "";
        for (String st : arr) {
            st = "'" + st + "'";
            ss += (ss == "") ? st : "," + st;
        }
        return ss;
    }
    
    // Comprueba si cadena de texto formateada es una lista
    // de valores permitidos para consulta en base de datos
    public static boolean listaMovilesCorrecta(String lst) {
        if (lst == null || lst == "" ||
            lst.compareTo("null") == 0 ||
            lst.compareTo(NODATA) == 0 ||
            // Comprueba que cadena de texto no incluya instrucciones SQL
            Restriction.includeSQL(lst)) {
            return false;
        }
        return true;
    }
    
    // Consulta moviles para asociar numero interno y definir si inician en
    // punto retorno, segun los moviles especificados en las cadenas de texto
    // pasadas como parametro
    public static ArrayList<Movil> select_moviles(String moviles, String movilesRetorno) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;        
        
        // Comprueba contenido de cadenas
        if (!listaMovilesCorrecta(moviles))          moviles = "";
        if (!listaMovilesCorrecta(movilesRetorno))   movilesRetorno = "";        
        
        String smoviles = new String(movilesRetorno);
        smoviles += (movilesRetorno == "") ? moviles : "," + moviles;
        smoviles = formatStringSQL(smoviles);
                
        String sql = "SELECT * FROM tbl_vehiculo AS v "
                   + " WHERE v.PLACA IN ("+ smoviles +") AND v.ESTADO = 1"
                   + " ORDER BY FIELD(placa, " + smoviles + ")";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ArrayList lst_movil = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                
                lst_movil.add(m);
            }
            
            String mvr[] = movilesRetorno.split(",");
            for (int i = 0; i < mvr.length; i++) {
                for (int j = 0; j < lst_movil.size(); j++) {
                    Movil mv = (Movil) lst_movil.get(j);
                    if (mv.getPlaca().equalsIgnoreCase(mvr[i])) {
                        mv.setIniciaEnPuntoRetorno(true);
                        break;
                    }
                }
            }
            return lst_movil;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Intercambia grupo de moviles de la configuracion de despachos
    // cuyos indicadores son pasados como parametros
    public static boolean rotarDespacho(int id1, int id2) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        // Consulta configuracion de despacho
        Despacho dph1 = select(id1);
        Despacho dph2 = select(id2);
        
        if (dph1 == null || dph2 == null)
            return false;
        
        String sql = "UPDATE tbl_configuracion_despacho SET"
                        + " FK_GRUPO_VEHICULO = ?,"
                        + " LISTA_VEHICULOS = ?,"
                        + " PROGRAMACION_RUTA = ?"
                        + " WHERE PK_CONFIGURACION_DESPACHO = ? AND ESTADO = 1";
        
        try {
            con.setAutoCommit(false);
            
            ps1 = con.prepareStatement(sql);
            ps1.setInt(1, dph1.getIdGrupoMoviles());
            ps1.setString(2, dph1.getGrupoMoviles());
            ps1.setInt(3, dph1.getProgramarRuta());
            ps1.setInt(4, dph2.getId());
            
            ps1.executeUpdate();
            
            ps2 = con.prepareStatement(sql);
            ps2.setInt(1, dph2.getIdGrupoMoviles());
            ps2.setString(2, dph2.getGrupoMoviles());
            ps2.setInt(3, dph2.getProgramarRuta());
            ps2.setInt(4, dph1.getId());
            
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
    
    // Inactiva configuracion de despacho a traves de su identificador,
    // establece (estado en 0)
    public static boolean delete(int id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_configuracion_despacho SET ESTADO = 0"
                + " WHERE PK_CONFIGURACION_DESPACHO = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Consulta si existe planilla despacho (registros) en fechas especificadas
    public static boolean existe_planilla_entre(Date fecha_inicial, Date fecha_final) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_INTERVALO_DESPACHO FROM tbl_intervalo_despacho"
                        + " WHERE"
                        + " (? >= FECHA_INICIAL AND ? <= FECHA_FINAL) OR"
                        + " (? >= FECHA_INICIAL AND ? <= FECHA_FINAL) OR"
                        + " (FECHA_INICIAL >= ? AND FECHA_INICIAL <= ?) OR"
                        + " (FECHA_FINAL >= ? AND FECHA_FINAL <= ?) AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(2, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(3, new java.sql.Date(fecha_final.getTime()));
            ps.setDate(4, new java.sql.Date(fecha_final.getTime()));
            ps.setDate(5, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(6, new java.sql.Date(fecha_final.getTime()));
            ps.setDate(7, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(8, new java.sql.Date(fecha_final.getTime()));
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
        } return false;
    }
    
    // Consulta si movil/placa tiene registros de planilla en la fecha especificada
    public static boolean existe_planilla_en(String fecha, String placa) {
        return existe_planilla_entre(fecha, fecha, -1, placa);
    }
    
    // Consulta si ruta tiene registros de planilla en la fecha especificada
    public static boolean existe_planilla_en(String fecha, int id_ruta) {
        return existe_planilla_entre(fecha, fecha, id_ruta, null);
    }
    
    // Consulta si existen registros en planilla despacho en un rango de fechas,
    // para una ruta o movil
    public static boolean existe_planilla_entre(String fecha_inicial, String fecha_final, int id_ruta, String placa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String rest = null; int id_elemt = -1;
        if (id_ruta > 0)                        { rest = "FK_RUTA = " + id_ruta; }
        else if (placa != null && placa != "")  { rest = "PLACA = " + placa; }
        if (rest == null) return false;
        
        String sql = "SELECT PK_PLANILLA_DESPACHO FROM tbl_planilla_despacho"
                        + " WHERE"
                        + " FECHA BETWEEN ? AND ? AND "
                        + rest;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicial);
            ps.setString(2, fecha_final);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);            
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta si existen registros de planilla en un 
    // rango de fechas, para una ruta en especifico    
    public static boolean existe_planilla_entre(Date fecha_inicial, Date fecha_final, int id_ruta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_INTERVALO_DESPACHO FROM tbl_intervalo_despacho"
                        + " WHERE"
                        + " LISTA_RUTA LIKE '%" + id_ruta + "%' AND ("
                        + " (? >= FECHA_INICIAL AND ? <= FECHA_FINAL) OR"
                        + " (? >= FECHA_INICIAL AND ? <= FECHA_FINAL) OR"
                        + " (FECHA_INICIAL >= ? AND FECHA_INICIAL <= ?) OR"
                        + " (FECHA_FINAL >= ? AND FECHA_FINAL <= ?)) AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);            
            ps.setDate(1, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(2, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(3, new java.sql.Date(fecha_final.getTime()));
            ps.setDate(4, new java.sql.Date(fecha_final.getTime()));
            ps.setDate(5, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(6, new java.sql.Date(fecha_final.getTime()));
            ps.setDate(7, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(8, new java.sql.Date(fecha_final.getTime()));
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
        } return false;
    }
    
    // Inserta planilla(s) de despacho de cada ruta, separando sus
    // regitros a medida que se itera por cada una de ellas.
    // Antes de esto se registra en tabla tbl_intervalo_despacho 
    // informacion que identifica la planilla a ser almacenada.
    public static boolean insert_planilla_individual(
            Date fecha_inicial,
            Date fecha_final,
            ArrayList<PlanillaDespacho> lst,
            String rutas,
            int tipo_generacion_dph) {
        
        if (lst.size() == 0)
            return false;                
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps  = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        
        String sql  = "INSERT INTO tbl_intervalo_despacho ("
                        + "NOMBRE_INTERVALO,"
                        + "LISTA_RUTA,"
                        + "FECHA_INICIAL,"
                        + "FECHA_FINAL,"
                        + "TIPO_GENERACION,"
                        + "ESTADO) VALUES (?,?,?,?,?,1)";
        
        // Se conforma el nombre de planilla a traves de su rango de fechas
        String nombre_planilla = nombre_planilla(fecha_inicial, fecha_final);
        
        try {
            con.setAutoCommit(false);
            
            String lst_rutas[] = rutas.split(",");
            int n = 0, n2 = 0;
            
            for (String ruta : lst_rutas) {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nombre_planilla);
                ps.setString(2, ruta);
                ps.setDate(3, new java.sql.Date(fecha_inicial.getTime()));
                ps.setDate(4, new java.sql.Date(fecha_final.getTime()));
                ps.setInt(5, tipo_generacion_dph);
                // Se registra intervalo de despacho (informacion identificativa)
                n += ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                
                if (rs.next()) {
                    // Se recupera identificador de intervalo 
                    // para asociarlo a cada registro de la planilla
                    int idIntervalo = rs.getInt(1);
                    // Se extraen registros de planilla de la ruta 
                    // que en el momento se esta tratando                    
                    ArrayList<PlanillaDespacho> lst_planilla_ruta = obtenerPlanillaDeRuta(ruta, lst);
                    
                    // Se asigna indentificador de intervalo
                    for (PlanillaDespacho pd : lst_planilla_ruta) {
                        pd.setIdIntervalo(idIntervalo);
                    }                    
                    // Se crea instruccion SQL e insertan registros de planilla
                    String sql2 = sql_insert_planilla(lst_planilla_ruta);                    
                    ps2 = con.prepareStatement(sql2);
                    n2 += ps2.executeUpdate();
                }
            }
            
            if (n == lst_rutas.length && n2 == lst.size()) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            UtilBD.closePreparedStatement(ps);
            UtilBD.closePreparedStatement(ps2);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Extrae registros de planilla despacho de la ruta especificada
    public static ArrayList<PlanillaDespacho> obtenerPlanillaDeRuta(
                                    String ruta, 
                                    ArrayList<PlanillaDespacho> lst_pd) {
                
        ArrayList<PlanillaDespacho> lst = new ArrayList<PlanillaDespacho>();
        int idRuta_new = Restriction.getNumber(ruta);
        
        for (PlanillaDespacho pd : lst_pd) {
            int idRuta_pd = pd.getIdRuta();
            if (idRuta_new == idRuta_pd) {
                lst.add(pd);
            }
        }
        return lst;
    }    
    
    // Inserta registros de planilla despacho para una sola ruta o redirije
    // a metodo insert_planilla_individual en caso de ser un listado
    public static boolean insert_planilla(
            Date fecha_inicial, 
            Date fecha_final,
            ArrayList<PlanillaDespacho> lst,
            String rutas,
            int tipo_generacion_dph) {
        
        if (lst.size() == 0)
            return false;
        
        if (rutas.indexOf(",") >= 0) {            
            return insert_planilla_individual(fecha_inicial, fecha_final, lst, rutas, Constant.DPH_PLANIFICADO);
        }
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps  = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
                
        String sql  = "INSERT INTO tbl_intervalo_despacho ("
                        + "NOMBRE_INTERVALO,"
                        + "LISTA_RUTA,"
                        + "FECHA_INICIAL,"
                        + "FECHA_FINAL,"
                        + "TIPO_GENERACION,"
                        + "ESTADO) VALUES (?,?,?,?,?,1)";
        
        int n, n2; n = n2 = 0;        
        // Se conforma el nombre de planilla a traves de su rango de fechas
        String nombre_planilla = nombre_planilla(fecha_inicial, fecha_final);
        
        try {
            con.setAutoCommit(false);            
            
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre_planilla);
            ps.setString(2, rutas);
            ps.setDate(3, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(4, new java.sql.Date(fecha_final.getTime()));
            ps.setInt(5, tipo_generacion_dph);
            // Se registra intervalo de despacho (informacion identificativa)
            n  = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
            
                // Se recupera y asocia identificador de intervalo
                // a cada registro de planilla despacho
                int idIntervalo = rs.getInt(1);
                for (PlanillaDespacho pd : lst) {
                    pd.setIdIntervalo(idIntervalo);
                }
                
                // Se crea instruccion SQL e insertan registros de planilla
                String sql2 = sql_insert_planilla(lst);
                ps2 = con.prepareStatement(sql2);
                n2  = ps2.executeUpdate();
                
                if (n == 1 && n2 == lst.size()) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                }
            }            
        } catch(SQLException e) {
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
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            UtilBD.closePreparedStatement(ps2);            
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Crea instruccion SQL en cadena de texto 
    // para insercion de planilla despacho especificada
    public static String sql_insert_planilla(ArrayList<PlanillaDespacho> lst) {
        
        String sql = "INSERT INTO tbl_planilla_despacho ("
                        + "FK_CONFIGURACION_DESPACHO,"
                        + "FK_INTERVALO_DESPACHO,"
                        + "FK_RUTA,"
                        + "NUMERO_PLANILLA,"
                        + "NUMERO_VUELTA,"
                        + "PLACA,"
                        + "FECHA,"
                        + "HOLGURA_PUNTO,"
                        + "TIPO_PUNTO,"
                        + "PUNTO,"
                        + "INTERVALO_LLEGADA,"
                        + "HORA_PLANIFICADA,"
                        + "ESTADO_DESPACHO,"
                        + "ESTADO) VALUES ";
        
        String values, data, s; s = ","; values = "";
        
        for (PlanillaDespacho pd : lst) {
            String fecha    = new SimpleDateFormat("yyyy-MM-dd").format(pd.getFecha());
            String hora_pfd = pd.getHoraPlanificada().toString();
            String placa    = pd.getPlaca();
            String punto    = pd.getPunto();
            
            fecha    = "'" + fecha + "'";
            hora_pfd = "'" + hora_pfd + "'";
            placa    = "'" + placa + "'";
            punto    = "'" + punto + "'";
            
            data = "(" +
                pd.getIdConf()          + s +
                pd.getIdIntervalo()     + s +
                pd.getIdRuta()          + s +
                pd.getNumeroPlanilla()  + s +
                pd.getNumeroVuelta()    + s +
                placa                   + s +
                fecha                   + s +
                pd.getHolguraPunto()    + s +                
                pd.getTipoPunto()       + s +
                punto                   + s +
                pd.getIntervaloLlegada()+ s +
                hora_pfd                + s +
                pd.getEstadoDespacho()  + s +
                "1)";
            
            values += (values == "")
                   ? data : "," + data;
        }
                
        sql += values;        
        return sql;
    }
    
    // Consulta si registros de planilla, asociados a un intervalo despacho
    // se encuentran libres (no han sido procesados)
    public static boolean es_planilla_libre(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT FK_INTERVALO_DESPACHO FROM tbl_planilla_despacho AS pd"
                        + " WHERE"
                        + " pd.FK_INTERVALO_DESPACHO = ? AND"
                        + " pd.ESTADO_DESPACHO = 1 AND "
                        + " pd.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return true;
    }
    
    // Consulta si registros de planilla en un rango de fechas, asociados a un 
    // intervalo despacho se encuentran libres (no han sido procesados)
    public static boolean es_intervalo_planilla_libre(
            int idIntervalo, 
            Date fecha_inicial,
            Date fecha_final) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT FK_INTERVALO_DESPACHO FROM tbl_planilla_despacho AS pd"
                        + " WHERE"
                        + " pd.FK_INTERVALO_DESPACHO = ? AND"
                        + " pd.FECHA >= ? AND"
                        + " pd.FECHA <= ? AND"
                        + " pd.ESTADO_DESPACHO = 1 AND"
                        + " pd.ESTADO = 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            ps.setDate(2, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(3, new java.sql.Date(fecha_final.getTime()));
            rs = ps.executeQuery();
            
            if (rs.next())
                return false;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return true;
    }
    
    // Edita intervalo de despacho (rango de fechas) para una planilla de despacho
    // Esto supone la eliminacion de registros de planilla en el rango de fechas
    // estipulado y el renombre del intervalo
    // Debido a que la edicion surge al movilizar la fecha final del intervalo,
    // la nueva fecha final, es un dia anterior a la fecha de inicio eliminada.
    public static boolean editar_intervalo_planilla(
            int idIntervalo,
            Date fecha_inicial,
            Date fecha_final) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps  = null;
        PreparedStatement ps2 = null;
        int n, n2; n = n2 = 0;
        
        String sql = "DELETE FROM tbl_planilla_despacho WHERE"
                        + " FK_INTERVALO_DESPACHO = ? AND"
                        + " FECHA >= ? AND"
                        + " FECHA <= ? AND"
                        + " ESTADO = 1";
        
        String sql2 = "UPDATE tbl_intervalo_despacho SET"                        
                        + " FECHA_FINAL = ?"
                        + " WHERE"
                        + " PK_INTERVALO_DESPACHO = ? AND ESTADO = 1";
        
        try {
            con.setAutoCommit(false);
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            ps.setDate(2, new java.sql.Date(fecha_inicial.getTime()));
            ps.setDate(3, new java.sql.Date(fecha_final.getTime()));
            n = ps.executeUpdate();          
            
            // Se selecciona el dia anterior a la fecha inicial del rango eliminado
            Date dia_anterior = TimeUtil.restarDia(fecha_inicial);

            ps2 = con.prepareStatement(sql2);
            ps2.setDate(1, new java.sql.Date(dia_anterior.getTime()));
            ps2.setInt(2, idIntervalo);            
            
            n2 = ps2.executeUpdate();
            
            if (n > 0 && n2 == 1) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            UtilBD.closePreparedStatement(ps);
            UtilBD.closePreparedStatement(ps2);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Actualiza nombre del intervalo de planilla segun el rango de fechas
    // establecido en su registro
    public static boolean cambiar_nombre_planilla(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        // Consulta intervalo de planilla
        IntervaloDespacho idph = select_planilla(idIntervalo);
        if (idph == null)
            return false;
        
        // Establece nuevo nombre de intervalo
        String nombre_planilla 
                = nombre_planilla(idph.getFechaInicial(), idph.getFechaFinal());
        
        String sql = "UPDATE tbl_intervalo_despacho SET NOMBRE_INTERVALO = ?"
                        + " WHERE PK_INTERVALO_DESPACHO = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre_planilla);
            ps.setInt(2, idIntervalo);
            ps.executeUpdate();
           
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Elimina registros de planilla despacho asociados a un intervalo despacho
    // (Registro identificativo del intervalo tambien es eliminado)
    public static boolean delete_planilla(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps  = null;       
        PreparedStatement ps2 = null;        
        
        String sql  = "DELETE FROM tbl_planilla_despacho WHERE"
                        + " FK_INTERVALO_DESPACHO = ?";
        String sql2 = "DELETE FROM tbl_intervalo_despacho WHERE"
                        + " PK_INTERVALO_DESPACHO = ?";
                
        int nps, nps2; nps = nps2 = 0;
        
        try {
            con.setAutoCommit(false);
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            nps = ps.executeUpdate();
            
            ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, idIntervalo);
            nps2 = ps2.executeUpdate();
            
            if (nps >= 0 && nps2 == 1) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            UtilBD.closePreparedStatement(ps);
            UtilBD.closePreparedStatement(ps2);
            pila_con.liberarConexion(con);
        } return false;
    }
    
    // Cancela uso de registros de planilla despacho asociados a un
    // intervalo. Campo estado en registros de planilla e intervalo se actualiza a 0.
    public static boolean cancel_planilla(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        String sql1 = "UPDATE tbl_planilla_despacho SET ESTADO = 0 WHERE"
                        + " FK_INTERVALO_DESPACHO = ?";
        String sql2 = "UPDATE tbl_intervalo_despacho SET ESTADO = 0 WHERE"
                        + " PK_INTERVALO_DESPACHO = ?";
        
        try {
            con.setAutoCommit(false);
            ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, idIntervalo);
            int nps1 = ps1.executeUpdate();
            
            ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, idIntervalo);
            int nps2 = ps2.executeUpdate();
            
            if (nps1 >= 0 && nps2 == 1) {
                con.commit();
                return true;
            } else {
                con.rollback();
            }
            
        } catch (SQLException e) {
            System.err.println(e);
            if (con != null) {
                try { con.rollback(); }
                catch (SQLException ie) { 
                    System.err.println(ie); 
                }
            }
        } finally {
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps1);
            UtilBD.closePreparedStatement(ps2);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta registros de planilla despacho para un intervalo despacho especifico
    public static IntervaloDespacho select_planilla(int idIntervalo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_intervalo_despacho AS idph"
                    + " WHERE idph.PK_INTERVALO_DESPACHO = ? AND ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                IntervaloDespacho idph = new IntervaloDespacho();
                idph.setId(rs.getInt("PK_INTERVALO_DESPACHO"));
                idph.setListaRuta(rs.getString("LISTA_RUTA"));
                idph.setFechaInicial(rs.getDate("FECHA_INICIAL"));
                idph.setFechaFinal(rs.getDate("FECHA_FINAL"));
                
                return idph;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);            
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Actualiza valores de configuracion de despacho
    public static boolean update(Despacho dph) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_configuracion_despacho SET"
                        + " NOMBRE_DESPACHO = ?,"
                        + " FK_GRUPO_VEHICULO = ?,"
                        + " TIPO_DIA = ?,"
                        + " CANTIDAD_VUELTAS = ?,"
                        + " LISTA_VEHICULOS = ?,"
                        + " LISTA_PUNTOS = ?,"
                        + " HORA_INICIO_RUTA = ?,"
                        + " HORA_FIN_RUTA = ?,"
                        + " LISTA_HORAS_PICO = ?,"
                        + " TIEMPO_REFERENCIA = ?,"
                        + " TIEMPO_ENTRE_PUNTOS_VALLE = ?,"
                        + " TIEMPO_ENTRE_PUNTOS_PICO = ?,"
                        + " TIEMPO_HOLGURA = ?,"
                        + " INTERVALO_DESPACHO_VALLE = ?,"
                        + " INTERVALO_DESPACHO_PICO = ?,"
                        + " TIEMPO_SALIDA = ?,"
                        + " TIEMPO_SALIDA_PICO = ?,"
                        + " HORAS_TRABAJO = ?,"
                        + " PUNTO_RETORNO = ?,"
                        + " HORA_INICIO_RETORNO = ?,"
                        + " INTERVALO_RETORNO = ?,"
                        + " TIEMPO_SALIDA_RETORNO = ?,"
                        + " TIEMPO_AJUSTE_RETORNO = ?,"
                        + " LISTA_VEHICULOS_RETORNO = ?,"
                        + " ROTACION_VEHICULOS = ?,"
                        + " PROGRAMACION_RUTA = ?,"
                        + " TIPO_PROGRAMACION_RUTA = ?,"
                        + " LISTA_ID_PUNTOS = ?,"
                        + " LIMITE_CONSERVACION_ROTACION = ?,"
                        + " CANTIDAD_VEHICULOS_RETORNO = ?"
                        + " WHERE PK_CONFIGURACION_DESPACHO = ? AND ESTADO = 1";
                
        try {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, dph.getNombreDespacho());
            ps.setInt(2, dph.getIdGrupoMoviles());
            ps.setString(3, dph.getTipoDia());
            ps.setInt(4, dph.getCantidadVueltas());
            ps.setString(5, dph.getGrupoMoviles());
            ps.setString(6, dph.getPuntosRuta());
            ps.setTime(7, dph.getHoraInicio());
            ps.setTime(8, dph.getHoraFin());
            ps.setString(9, dph.getHoraPico());
            ps.setString(10, dph.getTiempoReferencia());
            ps.setString(11, dph.getTiempoLlegadaValle());
            ps.setString(12, dph.getTiempoLlegadaPico());
            ps.setString(13, dph.getTiempoHolgura());
            ps.setInt(14, dph.getIntervaloValle());
            ps.setInt(15, dph.getIntervaloPico());
            ps.setInt(16, dph.getTiempoSalida());
            ps.setInt(17, dph.getTiempoSalidaPico());
            ps.setInt(18, dph.getHorasTrabajo());
            ps.setString(19, dph.getPuntoRetorno());
            ps.setTime(20, dph.getHoraInicioRetorno());
            ps.setInt(21, dph.getIntervaloRetorno());
            ps.setInt(22, dph.getTiempoSalidaRetorno());
            ps.setInt(23, dph.getTiempoAjusteRetorno());
            ps.setString(24, dph.getGrupoMovilesRetorno());
            ps.setInt(25, dph.getRotarVehiculo());
            ps.setInt(26, dph.getProgramarRuta());
            ps.setString(27, dph.getTipoProgramacionRuta());
            ps.setString(28, dph.getIdPuntosRuta());
            ps.setInt(29, dph.getLimiteConservacion());
            ps.setInt(30, dph.getCantidadMovilesRetorno());
            ps.setInt(31, dph.getId());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }    
    
    // Actualiza valores de configuracion de despacho a partir 
    // del modulo de puntos de ruta
    public static boolean updateFromPR(Despacho dph) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_configuracion_despacho SET"
                        + " LISTA_ID_PUNTOS = ?,"
                        + " LISTA_PUNTOS = ?,"        
                        + " TIEMPO_REFERENCIA = ?,"
                        + " TIEMPO_HOLGURA = ?,"
                        + " TIEMPO_ENTRE_PUNTOS_VALLE = ?,"
                        + " TIEMPO_ENTRE_PUNTOS_PICO = ?"
                        + " WHERE PK_CONFIGURACION_DESPACHO = ? AND ESTADO = 1";
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, dph.getIdPuntosRuta());
            ps.setString(2, dph.getPuntosRuta());
            ps.setString(3, dph.getTiempoReferencia());
            ps.setString(4, dph.getTiempoHolgura());
            ps.setString(5, dph.getTiempoLlegadaValle());
            ps.setString(6, dph.getTiempoLlegadaPico());
            ps.setInt(7, dph.getId());
            
            int n = ps.executeUpdate();
            
            if (n == 1) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta moviles que transitan en una ruta y fecha en especifico
    public static ArrayList<Movil> moviles_en_ruta(int idRuta, String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT" 
                    + " pd.PLACA"
                    + " FROM tbl_planilla_despacho AS pd"
                    + " WHERE pd.FK_RUTA = ? AND pd.FECHA = ?"
                    + " GROUP BY pd.PLACA";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ps.setString(2, fecha);
            rs = ps.executeQuery();
            
            ArrayList<Movil> lst_movil = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA"));
                lst_movil.add(m);
            }
            return lst_movil;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);            
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta moviles asociados a un propietario 
    // que transitan en una ruta y fecha en especifico
    public static ArrayList<Movil> moviles_en_ruta_propietario(int idPropietario, int idRuta, String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT t3.PLACA FROM			"
                + "			("
                + "			SELECT"
                + "                PLACA"
                + "                FROM tbl_planilla_despacho"
                + "                WHERE FK_RUTA = ? AND FECHA = ?"
                + "                 GROUP BY PLACA"
                + "			) as t0"
                + "			INNER JOIN"
                + "			("
                + "				SELECT * FROM "
                + "					(	"
                + "					SELECT fk_vehiculo FROM tbl_propietario_vehiculo WHERE fk_propietario = ? AND activa = 1 AND estado = 1 "
                + "					) t1"
                + "					INNER JOIN"
                + "					("
                + "					SELECT PK_VEHICULO , PLACA FROM tbl_vehiculo WHERE ESTADO = 1"
                + "					) t2"
                + "					ON fk_vehiculo = PK_VEHICULO			"
                + "			) t3"
                + "			ON t0.PLACA = t3.PLACA";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ps.setString(2, fecha);
            ps.setInt(3, idPropietario);
            rs = ps.executeQuery();

            ArrayList<Movil> lst_movil = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA"));
                lst_movil.add(m);
            }
            return lst_movil;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta registros de planilla despacho para movil, ruta y 
    // fecha en especifico que no han sido controlados.
    // La consulta se agrupa por vuelta, lo que permite conocer
    // hora de inicio, hora de fin y cantidad de puntos controlados.
    public static ArrayList<PlanillaDespachoMin> select_planilla_movil(
            int idRuta,
            String fecha,
            String placa) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                    + " pd.PK_PLANILLA_DESPACHO,"
                    + " count(pd.NUMERO_VUELTA) AS CANTIDAD_REGISTROS,"
                    + " pd.FK_INTERVALO_DESPACHO,"
                    + " pd.FK_RUTA,"                    
                    + " pd.NUMERO_VUELTA,"
                    + " pd.FECHA,"
                    + " pd.PLACA,"                    
                    + " min(pd.HORA_PLANIFICADA) AS HORA_INICIO,"
                    + " max(pd.HORA_PLANIFICADA) AS HORA_FIN,"
                    + " r.NOMBRE AS NOMBRE_RUTA"
                    + " FROM tbl_planilla_despacho AS pd"
                    + " INNER JOIN tbl_ruta AS r ON"
                    + "  pd.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                    + " WHERE pd.FK_RUTA = ? AND"
                    + "  pd.FECHA = ? AND"
                    + "  pd.PLACA = ? AND"
                    + "  pd.TIPO_PUNTO >= 1"
                    + " GROUP BY pd.NUMERO_VUELTA"
                    + " HAVING sum(pd.ESTADO_DESPACHO) = 0 AND sum(if(pd.HORA_REAL IS NULL, 0, 1)) = 0";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ps.setString(2, fecha);
            ps.setString(3, placa);
            rs = ps.executeQuery();
            
            ArrayList<PlanillaDespachoMin> lst_pd = new ArrayList<PlanillaDespachoMin>();
            while (rs.next()) {
                PlanillaDespachoMin pd = new PlanillaDespachoMin();
                pd.setId(rs.getInt("PK_PLANILLA_DESPACHO"));
                pd.setIdIntervalo(rs.getInt("FK_INTERVALO_DESPACHO"));
                pd.setCantidadRegistros(rs.getInt("CANTIDAD_REGISTROS"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setIdRuta(rs.getInt("FK_RUTA"));
                pd.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pd.setFecha(rs.getDate("FECHA"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setHoraInicio(rs.getTime("HORA_INICIO"));
                pd.setHoraFin(rs.getTime("HORA_FIN"));
                pd.setHoraInicioStr(hfmt.format(pd.getHoraInicio()));
                pd.setHoraFinStr(hfmt.format(pd.getHoraFin()));
                
                lst_pd.add(pd);
            }
            return lst_pd;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);            
            pila_con.liberarConexion(con);
        }
        return null;
    }
        
    // Consulta cantidad de registros a sustituir para un vehiculo
    // en una vuelta.
    public static int registros_sustitucion_movil(String sql) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("CANTIDAD_REGISTROS");
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return 0;
    }
    
    // Actualiza registros de planilla despacho pasados en lista como 
    // parametro, cuyo valor de movil/placa a sido sustituido
    public static boolean sustituir_movil(ArrayList<String> lst_upd, int nregistros) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        int n = 0;
        
        try {
            con.setAutoCommit(false);
            
            for (String sql : lst_upd) {
                st = con.createStatement();
                n += st.executeUpdate(sql);
            }
                        
            if (n == nregistros) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
    
    // Consulta registros de planilla despacho activa (estado en 1)
    // para fecha en especifico
    public static ArrayList<PlanillaDespacho> select_planilla_sin_formato(
            int idRuta,
            Date fecha) {
            return select_planilla_sin_formato(idRuta, fecha, fecha);
    }
    
    // Consulta registros de planilla despacho activa (estado en 1)
    // para rango de fechas en especifico
    public static ArrayList<PlanillaDespacho> select_planilla_sin_formato(
            int idRuta,
            Date fechaInicial,
            Date fechaFinal) {
        
        ArrayList<Movil> lst_moviles = MovilBD.select();
        ArrayList<Punto> lst_puntos  = PuntoBD.select();
        ArrayList<Base> lst_bases    = BaseBD.select();                
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " pd.NUMERO_PLANILLA,"
                        + " pd.NUMERO_VUELTA,"
                        + " pd.FECHA,"
                        + " pd.TIPO_PUNTO,"
                        + " pd.PUNTO,"
                        + " pd.PLACA,"
                        + " pd.INTERVALO_LLEGADA,"
                        + " pd.HORA_PLANIFICADA,"
                        + " pd.HORA_REAL,"
                        + " pd.DIFERENCIA,"
                        + " pd.ESTADO_DESPACHO,"
                        + " pd.VEHICULO_SUSTITUIDO,"
                        + " r.NOMBRE AS NOMBRE_RUTA"
                        + " FROM tbl_planilla_despacho AS pd"
                        + " INNER JOIN tbl_intervalo_despacho AS idph ON"
                        + "   idph.PK_INTERVALO_DESPACHO = pd.FK_INTERVALO_DESPACHO AND"
                        + "   idph.TIPO_GENERACION = ?"
                        + " INNER JOIN tbl_ruta AS r ON"
                        + "   pd.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                        + " WHERE pd.FK_RUTA = ? AND"
                        + " pd.FECHA >= ? AND pd.FECHA <= ? AND"
                        + " pd.ESTADO = 1"
                        + " ORDER BY pd.PK_PLANILLA_DESPACHO";
                        //+ " ORDER BY pd.FK_INTERVALO_DESPACHO ASC, pd.VEHICULO_SUSTITUIDO ASC, pd.NUMERO_PLANILLA ASC, pd.FECHA ASC, pd.HORA_PLANIFICADA ASC";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, TIPO_GENERACION_DPH);
            ps.setInt(2, idRuta);
            ps.setDate(3, new java.sql.Date(fechaInicial.getTime()));
            ps.setDate(4, new java.sql.Date(fechaFinal.getTime()));
            rs = ps.executeQuery();
            
            ArrayList<PlanillaDespacho> lst = new ArrayList<PlanillaDespacho>();
            while (rs.next()) {
                PlanillaDespacho pd = new PlanillaDespacho();
                
                pd.setNumeroPlanilla(rs.getInt("NUMERO_PLANILLA"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setFecha(rs.getDate("FECHA"));
                pd.setTipoPunto(rs.getInt("TIPO_PUNTO"));
                pd.setPunto(rs.getString("PUNTO"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pd.setIntervaloLlegada(rs.getInt("INTERVALO_LLEGADA"));
                pd.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                pd.setHoraReal(rs.getTime("HORA_REAL"));
                pd.setDiferencia(rs.getLong("DIFERENCIA"));
                pd.setEstadoDespacho(rs.getInt("ESTADO_DESPACHO"));
                pd.setVehiculoSustituido(rs.getInt("VEHICULO_SUSTITUIDO"));
                
                String pd_placa    = pd.getPlaca().toLowerCase();
                String pd_codpunto = pd.getPunto().toLowerCase();
                String pd_codbase  = pd_codpunto;
                
                // Asociamos numero interno - placa                                
                for (Movil m : lst_moviles) {
                    String m_placa = m.getPlaca().toLowerCase();
                    if (pd_placa.compareTo(m_placa) == 0) {
                        pd.setNumeroInterno(m.getNumeroInterno());
                        break;
                    }
                }                
                
                // Asociamos nombre punto control - p[codigo]
                for (Punto p : lst_puntos) {
                    String p_codpunto = "p" + p.getCodigoPunto();
                    if (pd_codpunto.compareTo(p_codpunto) == 0) {
                        pd.setNombrePunto(p.getNombre());
                        break;
                    }
                }
                
                // Asociamos nombre base - p[codigo]                
                for (Base b : lst_bases) {                    
                    int codigo_punto = b.getIdentificador();
                    if (pd.getTipoPunto() == 1) codigo_punto += 1;
                    String b_codbase = "p" + codigo_punto;
                    if (pd_codbase.compareTo(b_codbase) == 0) {
                        pd.setNombrePunto(b.getNombre());
                        break;
                    }
                }
                
                lst.add(pd);
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
    
    // Consulta registros de planilla despacho activa (estado en 1)
    // asociado a un intervalo despacho especifico
    public static ArrayList<PlanillaDespacho> select_planilla_sin_formato(int idIntervalo) { //, boolean actualizarTiempo) {
        
        ArrayList<Movil> lst_moviles = MovilBD.select();
        ArrayList<Punto> lst_puntos  = PuntoBD.select();
        ArrayList<Base> lst_bases    = BaseBD.select();
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                        + " pd.NUMERO_PLANILLA,"
                        + " pd.NUMERO_VUELTA,"
                        + " pd.FECHA,"
                        + " pd.TIPO_PUNTO,"
                        + " pd.PUNTO,"
                        + " pd.PLACA,"                        
                        + " pd.HORA_PLANIFICADA,"
                        + " pd.HORA_REAL,"
                        + " pd.INTERVALO_LLEGADA,"
                        + " r.NOMBRE AS NOMBRE_RUTA"
                        + " FROM tbl_planilla_despacho AS pd"
                        + " INNER JOIN tbl_configuracion_despacho AS cd ON"
                        + "  cd.PK_CONFIGURACION_DESPACHO = pd.FK_CONFIGURACION_DESPACHO AND"
                        + "  cd.ESTADO = 1"
                        + " INNER JOIN tbl_ruta AS r ON"
                        + "  cd.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                        + " WHERE pd.FK_INTERVALO_DESPACHO = ?";
                
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idIntervalo);
            rs = ps.executeQuery();
            
            ArrayList<PlanillaDespacho> lst_pd = new ArrayList<PlanillaDespacho>();
            while (rs.next()) {
                PlanillaDespacho pd = new PlanillaDespacho();                
                
                pd.setNumeroPlanilla(rs.getInt("NUMERO_PLANILLA"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setFecha(rs.getDate("FECHA"));
                pd.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pd.setTipoPunto(rs.getInt("TIPO_PUNTO"));
                pd.setPunto(rs.getString("PUNTO"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                pd.setHoraReal(rs.getTime("HORA_REAL"));
                pd.setIntervaloLlegada(rs.getInt("INTERVALO_LLEGADA"));
                
                // Asociamos numero interno - placa
                String pd_placa = pd.getPlaca().toLowerCase();
                
                for (Movil m : lst_moviles) {
                    String mv_placa = m.getPlaca().toLowerCase();
                    if (pd_placa.compareTo(mv_placa) == 0) {
                        pd.setNumeroInterno(m.getNumeroInterno());
                        break;
                    }
                }
                
                // Asociamos nombre punto control - p[codigo]
                for (Punto p : lst_puntos) {                    
                    String cod_punto = "p" + p.getCodigoPunto();
                    if (cod_punto.compareTo(pd.getPunto()) == 0) {
                        pd.setNombrePunto(p.getNombre());
                        break;
                    }
                }
                
                // Asociamos nombre base - p[codigo]
                for (Base b : lst_bases) {                    
                    int codigo_punto = (pd.getTipoPunto() == 1) 
                                     ? b.getIdentificador() + 1
                                     : b.getIdentificador();
                    String cod_base = "p" + codigo_punto;
                    if (cod_base.compareTo(pd.getPunto()) == 0) {
                        pd.setNombrePunto(b.getNombre());
                        break;
                    }
                }
                
                lst_pd.add(pd);
            }
            
            return lst_pd;
            
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } return null;
    }
    
    // Consulta registros de tiempo de planilla despacho para movil, fecha y 
    // numero de vuelta en especifico
    public static ArrayList<PlanillaDespacho> select_tiempos_vuelta(
            String placa, String fecha, int nvuelta) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT pd.HORA_PLANIFICADA FROM tbl_planilla_despacho AS pd WHERE"
                        + " lcase(pd.PLACA) = ? AND"
                        + " pd.FECHA = ? AND"
                        + " pd.NUMERO_VUELTA = ?"
                        + " ORDER BY pd.FECHA asc, pd.HORA_PLANIFICADA asc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            ps.setString(2, fecha);
            ps.setInt(3, nvuelta);
            rs = ps.executeQuery();
            
            ArrayList<PlanillaDespacho> lst_pd = new ArrayList<PlanillaDespacho>();
            while (rs.next()) {
                PlanillaDespacho pd = new PlanillaDespacho();
                pd.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                lst_pd.add(pd);
            }
            return lst_pd;
            
        } catch(SQLException e) {
            System.err.println(e);
        } finally {            
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta informacion resumida de las planillas de despacho
    // que se encuentran activas en un rango de fechas.
    // Informacion destacada incluye placa, fecha, hora inicial y final de cada vuelta
    public static ArrayList<PlanillaDespachoMin> select_tiempos_planilla(
            String fecha_inicio,
            String fecha_final) {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                + " pd.FK_INTERVALO_DESPACHO,"
                + " pd.NUMERO_PLANILLA,"
                + " pd.FK_RUTA,"
                + " cd.NOMBRE_DESPACHO,"                
                + " pd.FECHA,"
                + " pd.PLACA,"
                + " (SELECT r.NOMBRE FROM tbl_ruta AS r WHERE r.PK_RUTA = pd.FK_RUTA AND ESTADO = 1) AS NOMBRE_RUTA,"
                + " (SELECT idph.TIPO_GENERACION FROM tbl_intervalo_despacho AS idph"
                + "  WHERE idph.PK_INTERVALO_DESPACHO = pd.FK_INTERVALO_DESPACHO AND idph.ESTADO = 1) AS TIPO_GENERACION,"
                + " min(pd.HORA_PLANIFICADA) AS HORA_INICIO,"
                + " max(pd.HORA_PLANIFICADA) AS HORA_FIN"
                + " FROM tbl_planilla_despacho AS pd"
                + " INNER JOIN tbl_configuracion_despacho AS cd ON"
                + "     cd.PK_CONFIGURACION_DESPACHO = pd.FK_CONFIGURACION_DESPACHO AND"
                + "     cd.ESTADO = 1"                
                + " WHERE pd.FECHA BETWEEN ? AND ? AND"
                + " pd.ESTADO = 1"
                + " GROUP BY fk_intervalo_despacho, numero_vuelta, fecha, placa"
                + " ORDER BY numero_planilla asc, fecha asc, placa asc, hora_inicio asc";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicio);
            ps.setString(2, fecha_final);
            rs = ps.executeQuery();
            
            ArrayList<PlanillaDespachoMin> lst = new ArrayList<PlanillaDespachoMin>();
            while (rs.next()) {
                PlanillaDespachoMin pd = new PlanillaDespachoMin();
                pd.setIdIntervalo(rs.getInt("FK_INTERVALO_DESPACHO"));
                pd.setNumeroPlanilla(rs.getInt("NUMERO_PLANILLA"));
                pd.setIdRuta(rs.getInt("FK_RUTA"));
                pd.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                pd.setPlaca(rs.getString("PLACA"));
                pd.setNombreDespacho(rs.getString("NOMBRE_DESPACHO"));                
                pd.setFecha(rs.getDate("FECHA"));
                pd.setFechaStr(ffmt.format(pd.getFecha()));
                pd.setHoraInicio(rs.getTime("HORA_INICIO"));
                pd.setHoraFin(rs.getTime("HORA_FIN"));
                pd.setTipoGeneracion(rs.getInt("TIPO_GENERACION"));
                pd.setHoraInicioStr(hfmt_full.format(pd.getHoraInicio()));
                pd.setHoraFinStr(hfmt_full.format(pd.getHoraFin()));
                lst.add(pd);
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
    
    // Consulta informacion resumida de las planillas de despacho
    // que se encuentran activas en un rango de fechas.
    // Informacion destacada incluye placa, fecha, numero de vuelta, 
    // hora inicial y final de cada vuelta
    public static ArrayList<PlanillaDespachoMin> select_tiempos_planilla() {
        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String fecha_hoy = ffmt.format(new Date());
        
        String sql = "SELECT"
                    + " pd.FK_INTERVALO_DESPACHO,"
                    + " count(pd.NUMERO_VUELTA) AS CANTIDAD_PUNTOS,"
                    + " pd.NUMERO_VUELTA,"
                    + " pd.FK_RUTA,"
                    + " pd.FECHA,"
                    + " pd.PLACA,"
                    + " min(pd.HORA_PLANIFICADA) AS HORA_INICIO,"
                    + " max(pd.HORA_PLANIFICADA) AS HORA_FIN"
                    
                    //+ "(SELECT pdi.HORA_REAL FROM tbl_planilla_despacho AS pdi WHERE"
                    //+ "  pdi.PLACA = pd.PLACA AND"
                    //+ "  pdi.FK_RUTA = pd.FK_RUTA AND"
                    //+ "  pdi.FECHA = pd.FECHA AND"
                    //+ "  pdi.NUMERO_VUELTA = pd.NUMERO_VUELTA AND"
                    //+ "  pdi.HORA_REAL IS NOT NULL ORDER BY pdi.HORA_REAL DESC LIMIT 1) AS ULTIMA_HORA_REGISTRADA"
                    
                    + " FROM tbl_planilla_despacho AS pd"
                    + " WHERE pd.FECHA = ? AND pd.TIPO_PUNTO >= 1"
                    + " GROUP BY pd.PLACA, pd.FK_RUTA, pd.FECHA, pd.NUMERO_VUELTA";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha_hoy);
            rs = ps.executeQuery();
            
            ArrayList<PlanillaDespachoMin> lst_pd = new ArrayList<PlanillaDespachoMin>();
            while (rs.next()) {
                
                PlanillaDespachoMin pd = new PlanillaDespachoMin();
                pd.setIdIntervalo(rs.getInt("FK_INTERVALO_DESPACHO"));
                pd.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                pd.setIdRuta(rs.getInt("FK_RUTA"));                
                pd.setPlaca(rs.getString("PLACA"));                
                pd.setCantidadPuntos(rs.getInt("CANTIDAD_PUNTOS"));
                //pd.setUltimaHoraRegistrada(rs.getTime("ULTIMA_HORA_REGISTRADA"));
                
                Date fecha       = rs.getDate("FECHA");
                Time hora_inicio = rs.getTime("HORA_INICIO");
                Time hora_fin    = rs.getTime("HORA_FIN");                                
                String sfecha    = ffmt.format(fecha);
                String shora_ini = hfmt.format(hora_inicio);
                String shora_fin = hfmt.format(hora_fin);
                
                long nhora_inicio = hora_inicio.getTime();
                long nhora_fin    = hora_fin.getTime();
                long tiempo_min   = ((nhora_fin - nhora_inicio) / 1000) / 60;
                if (tiempo_min <= 0) tiempo_min = 15;
                                
                pd.setFecha(fecha); 
                pd.setFechaStr(sfecha);
                pd.setHoraInicio(hora_inicio); 
                pd.setHoraInicioStr(shora_ini);
                pd.setHoraFin(hora_fin); 
                pd.setHoraFinStr(shora_fin);
                pd.setTiempoVueltaMin(tiempo_min);                
                                
                lst_pd.add(pd);
            }
            return lst_pd;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta si determinado movil se encuentra cumpliendo algun despacho        
    // (estado base llegada es 0 - aun no llega a base) en fecha especificada
    public static boolean movil_en_despacho(String placa, String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        String sql = "SELECT ESTADO_DESPACHO FROM tbl_planilla_despacho"
                        + " WHERE PLACA = ? AND"
                        + " FECHA = ? AND"
                        + " TIPO_PUNTO = 3 AND"
                        + " ESTADO_DESPACHO = 0 AND"
                        + " ESTADO = 1";
                        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            ps.setString(2, fecha);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {            
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // ======================= Dias festivo ====================================
    
    // Consulta si registro de dia festivo existe    
    public static boolean existe_dia_festivo(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT PK_DIA_FESTIVO FROM tbl_dia_festivo WHERE DIA_FESTIVO = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {            
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        
        return false;
    }
    
    // Inserta fecha como dia festivo
    public static boolean insert_dia_festivo(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "INSERT INTO tbl_dia_festivo (DIA_FESTIVO) VALUES (?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            if (ps.executeUpdate() == 1)
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
    
    // Elimina fecha como dia festivo
    public static boolean delete_dia_festivo(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con  = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "DELETE FROM tbl_dia_festivo WHERE DIA_FESTIVO = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            if (ps.executeUpdate() >= 1)
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
    
    // ======================= Programacion ruta ===============================
    
    // Inserta nombre de programacion de ruta y retorna identificador del registro
    public static int insert_nombre_programacion_ruta(String nombre) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "INSERT INTO tbl_pgr (NOMBRE, ESTADO) VALUES (?, 1)";
        
        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch(SQLException e) {
            System.err.println(e);
        } finally {            
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return 0;
    }
    
    // Inserta detalle de nueva programacion de ruta a traves de
    // bloque de instrucciones SQL insert en parametro.
    // Antes de la insercion se inactivan todas las programaciones existentes y
    // se activa la registrada.
    public static boolean insert_programacion_ruta(String sql_ins) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st  = null;
        Statement st1 = null;        
        
        String sql_est = "UPDATE tbl_programacion_ruta SET ESTADO = 0";
        int nest, nins;
        
        try {
            con.setAutoCommit(false);
            
            st = con.createStatement();
            nest = st.executeUpdate(sql_est);
            
            st1 = con.createStatement();
            nins = st1.executeUpdate(sql_ins);
            
            if (nest >= 0 && nins >= 0)
                con.commit();
            else
                con.rollback();
            
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
            UtilBD.closeStatement(st);
            UtilBD.closeStatement(st1);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    public static boolean update_programacion_ruta(String sql_upd, ArrayList<String> lst_upd) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        int n, n1; n = n1 = 0;
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql_upd);
            n = ps.executeUpdate();
                        
            for (String sql : lst_upd) {
                ps1 = con.prepareStatement(sql);
                n1 += ps1.executeUpdate();
            }
            
            if (n == 1 && n1 == lst_upd.size()) {
                con.commit();
                return true;
            } else {
                con.rollback();
            }
            
        } catch (SQLException e) {
            System.err.println(e);
            try {
                if (con != null) con.rollback();
            } catch (SQLException ie) {
                System.err.println(ie);
            }
        } finally {            
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Elimina programacion de ruta a traves de su identificador.
    // Dicha operacion consiste en eliminar el detalle de la programacion y 
    // nombre asociado, registros que residen en tablas diferentes.
    // El nombre de la programacion e identificador se encuentran en tabla tbl_pgr.
    public static boolean delete_programacion_ruta(int id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st  = null;
        Statement st1 = null;                

        String sql_det = "DELETE FROM tbl_programacion_ruta WHERE FK_PGR = " + id;
        String sql_id  = "DELETE FROM tbl_pgr WHERE PK_PGR = " + id;
        int ndet, nid;
        
        try {
            con.setAutoCommit(false);
            
            st = con.createStatement();
            ndet = st1.executeUpdate(sql_det);
            
            st1 = con.createStatement();
            nid = st.executeUpdate(sql_id);
            
            if (nid == 1 && ndet >= 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            UtilBD.closeStatement(st1);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Activa programacion de ruta a traves de su identificador.
    // Esta operacion requiere la desactivacion de todos los registros detalle y
    // activacion de los registros de la programacion correspondiente.    
    public static boolean activa_programacion_ruta(int id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps  = null;
        PreparedStatement ps1 = null;        

        String sql_des = "UPDATE tbl_programacion_ruta SET ESTADO = 0";
        String sql_act = "UPDATE tbl_programacion_ruta SET ESTADO = 1"
                            + " WHERE FK_PGR = ?";
        int ndes, nact;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql_des);
            ndes = ps.executeUpdate();
            
            ps1 = con.prepareStatement(sql_act);
            ps1.setInt(1, id);
            nact = ps1.executeUpdate();
            
            if (ndes >= 0 && nact >= 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            UtilBD.closePreparedStatement(ps);
            UtilBD.closePreparedStatement(ps1);
            pila_con.liberarConexion(con);
        }
        return false;
    }    
    
    // Desactiva programacion de ruta a traves de su identificador.
    // Operacion que resulta de actualizar campo estado a 0 en 
    // registros detalle y nombre asociado. Registros que residen
    // en tablas diferentes.
    // El nombre de la programacion e identificador se encuentran en tabla tbl_pgr.
    public static boolean desactivar_programacion_ruta(int id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st  = null;
        Statement st1 = null;
        
        String sql_id  = "UPDATE tbl_pgr SET ESTADO = 0 WHERE PK_PGR = " + id;
        String sql_det = "UPDATE tbl_programacion_ruta SET ESTADO = 0 WHERE FK_PGR = " + id;
        int nid, ndet;
        
        try {
            con.setAutoCommit(false);
            
            st = con.createStatement();
            ndet = st.executeUpdate(sql_det);
            
            st1 = con.createStatement();
            nid = st1.executeUpdate(sql_id);
            
            if (nid == 1 && ndet >= 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
            }
            
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
            UtilBD.closeStatement(st);
            UtilBD.closeStatement(st1);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Desactiva todas las programaciones de ruta existentes
    public static boolean desactivar_programacion_ruta() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st  = null;
        
        String sql = "UPDATE tbl_programacion_ruta SET ESTADO = 0";
        int n;
        
        try {
            con.setAutoCommit(false);
            
            st = con.createStatement();
            n  = st.executeUpdate(sql);            
            
            if (n > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
            }
            
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
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta nombre de la programacion de ruta activa
    public static ProgramacionRuta programacion_ruta_activa() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();        
        Statement st = null;
        ResultSet rs = null;
        
        String sql = 
            "SELECT pgr.PK_PGR, pgr.NOMBRE FROM tbl_programacion_ruta AS pgruta"
            + " INNER JOIN tbl_pgr AS pgr ON"
            + " pgr.PK_PGR = pgruta.FK_PGR AND pgr.ESTADO = 1"
            + " WHERE pgruta.ESTADO = 1 limit 1";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            if (rs.next()) {
                ProgramacionRuta pgr = new ProgramacionRuta();
                pgr.setId(rs.getInt("PK_PGR"));
                pgr.setNombreProgramacion(rs.getString("NOMBRE"));
                return pgr;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        
        ProgramacionRuta no_pgr = new ProgramacionRuta();
        no_pgr.setNombreProgramacion("Ninguna");
        no_pgr.setId(0);
        return no_pgr;
    }
    
    // Consulta identificador de grupo de vehiculos asignado 
    // para un dia y ruta especifico
    public static int select_idgrupomovil_pgruta(int idRuta, int dia_semana) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM tbl_programacion_ruta AS pgr WHERE"
                        + " pgr.FK_RUTA = ? AND pgr.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                switch (dia_semana) {
                    case 1: return rs.getInt("LUN");
                    case 2: return rs.getInt("MAR");
                    case 3: return rs.getInt("MIE");
                    case 4: return rs.getInt("JUE");
                    case 5: return rs.getInt("VIE");
                    case 6: return rs.getInt("SAB");
                    case 7: return rs.getInt("DOM");
                }
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        } 
        return 0;
    }
    
    // Consulta moviles pertenecientes a un grupo especifico
    public static ArrayList<String> select_grupomovil_pgruta(int idGrupo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                    + "   v.PLACA"
                    + " FROM tbl_agrupacion_vehiculo AS av"
                    + " INNER JOIN tbl_vehiculo AS v ON"
                    + "   av.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                    + " WHERE av.FK_AGRUPACION = ? AND av.ESTADO = 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idGrupo);
            rs = ps.executeQuery();
            
            ArrayList<String> lst = new ArrayList<String>();
            while (rs.next()) {
                lst.add(rs.getString("PLACA"));
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
    
    // =========================================================================
    // Division por numero_vuelta
    // =========================================================================
    
    // Divide registros de planilla despacho por vehiculo y numero de vuelta
    // En el proceso se crea clase primaria que envuelve los registros de vuelta
    // de cada vehiculo.
    public static ArrayList<Planilla> formato_planillaXvuelta(ArrayList<PlanillaDespacho> lst) {
        
        int flag = 1; String veh_a, veh_b;
        boolean cambio_veh;
        
        ArrayList<Planilla> 
            lst_pll = new ArrayList<Planilla>();        
        ArrayList<PlanillaDespacho> 
            pd_detalle = new ArrayList<PlanillaDespacho>();        
        
        // Listado de vehiculos sustituidos
        ArrayList<Planilla> 
            lst_pll_st = new ArrayList<Planilla>();        
        
        // Se asgina primera vuelta encontrada a variable control
        if (lst != null && lst.size() > 0) {
            flag = lst.get(0).getNumeroVuelta();
        } else {
            return lst_pll;
        }
        
        for (int i = 0; i < lst.size(); i++) {
            PlanillaDespacho pd = lst.get(i);
            int numero_vuelta = pd.getNumeroVuelta();
            
            cambio_veh = false;
            if (i >= 1) {
                veh_a = lst.get(i-1).getPlaca();
                veh_b = lst.get(i).getPlaca();
                if (veh_a.compareTo(veh_b) != 0)
                    cambio_veh = true;
            }
            
            // Se divide lista de planilla despacho
            // por numero de vuelta
            if (numero_vuelta == flag && !cambio_veh) {                                
                // Se agrupa en lista registros de planilla del vehiculo actual
                pd_detalle.add(pd);
            } else {
                Planilla pll = new Planilla();
                
                // Se almacena registros de vuelta que estaba en curso
                pll.setNumeroVuelta(flag); 
                pll.setFecha(pd_detalle.get(0).getFecha()); 
                pll.setPlaca(pd_detalle.get(0).getPlaca());
                pll.setNumeroInterno(pd_detalle.get(0).getNumeroInterno());
                pll.setIdRuta(pd_detalle.get(0).getIdRuta());
                pll.setRuta(pd_detalle.get(0).getNombreRuta());                
                pll.setNumeroPlanilla(pd_detalle.get(0).getNumeroPlanilla());
                pll.setVehiculoSustituido(pd_detalle.get(0).getVehiculoSustituido());
                pll.setDetalle(pd_detalle);                                    
                
                // Se diferencia almacenamiento de registros si el vehiculo
                // fue sustituido o no
                if (pll.getVehiculoSustituido() == 1)
                    lst_pll_st.add(pll);
                else
                    lst_pll.add(pll);
                    
                // Se adiciona en lista primer registro de nueva vuelta
                // Se actualiza variable de control al valor de nueva vuelta
                pd_detalle = new ArrayList<PlanillaDespacho>();                
                pd_detalle.add(pd);
                flag = numero_vuelta;
            }
            
            // Se almacena ultima vuelta generada
            if (i == lst.size()-1) {
                Planilla pll = new Planilla();
                                
                pll.setNumeroVuelta(flag);
                pll.setFecha(pd_detalle.get(0).getFecha());
                pll.setPlaca(pd_detalle.get(0).getPlaca());
                pll.setNumeroInterno(pd_detalle.get(0).getNumeroInterno());
                pll.setIdRuta(pd_detalle.get(0).getIdRuta());
                pll.setRuta(pd_detalle.get(0).getNombreRuta());
                pll.setNumeroPlanilla(pd_detalle.get(0).getNumeroPlanilla());
                pll.setVehiculoSustituido(pd_detalle.get(0).getVehiculoSustituido());
                pll.setDetalle(pd_detalle);                                
                
                if (pll.getVehiculoSustituido() == 1)
                    lst_pll_st.add(pll);                
                else
                    lst_pll.add(pll);
            }
        }
        
        // Se adicionan registros de vehiculos sustituidos a listado general
        if (lst_pll != null && lst_pll_st != null) {
            for (Planilla pll : lst_pll_st) {
                lst_pll.add(pll);
            }
        }
        
        return lst_pll;
    }
    
    // =========================================================================
    // Division por vehiculo
    // =========================================================================
    
    // Divide registros de planilla despacho por vehiculo
    public static ArrayList<Planilla> formato_planillaXvehiculo(ArrayList<PlanillaDespacho> lst) {
        
        if (lst != null && lst.size() > 0) {
            
            PlanillaDespacho pd0 = lst.get(0);
            String placa_prev = pd0.getPlaca();
            int veh_std_prev  = pd0.getVehiculoSustituido();
            
            ArrayList<Planilla> lst_pll 
                    = new ArrayList<Planilla>();
            ArrayList<PlanillaDespacho> pd_detalle
                    = new ArrayList<PlanillaDespacho>();
            
            pd_detalle.add(pd0);
            
            for (int i = 1; i < lst.size(); i++) {
                PlanillaDespacho pd = lst.get(i);
                String placa = pd.getPlaca();
                int veh_std  = pd.getVehiculoSustituido();
                
                // Se divide lista de planilla despacho por vehiculo
                if (placa.compareTo(placa_prev) == 0 && veh_std == veh_std_prev) {
                    // Se agrupa en lista registros de planilla del vehiculo actual
                    pd_detalle.add(pd);
                } else {
                    
                    Planilla pll = new Planilla();
                                        
                    // Se almacena registros de vehiculo que estaba en curso
                    pll.setFecha(pd_detalle.get(0).getFecha());
                    pll.setPlaca(pd_detalle.get(0).getPlaca());
                    pll.setNumeroInterno(pd_detalle.get(0).getNumeroInterno());
                    pll.setRuta(pd_detalle.get(0).getNombreRuta());          
                    pll.setNumeroPlanilla(pd_detalle.get(0).getNumeroPlanilla());
                    pll.setDetalle(pd_detalle);
                    lst_pll.add(pll);
                    
                    // Se adiciona en lista primer registro de nuevo vehiculo
                    // Se actualiza variable de control al nuevo vehiculo
                    pd_detalle = new ArrayList<PlanillaDespacho>();
                    pd_detalle.add(pd);
                    placa_prev   = pd.getPlaca();
                    veh_std_prev = pd.getVehiculoSustituido();
                }
                
                // Se adiciona ultimo vehiculo
                if (i == lst.size()-1) {
                    Planilla pll = new Planilla();
                    
                    pll.setFecha(pd_detalle.get(0).getFecha());
                    pll.setPlaca(pd_detalle.get(0).getPlaca());
                    pll.setNumeroInterno(pd_detalle.get(0).getNumeroInterno());
                    pll.setRuta(pd_detalle.get(0).getNombreRuta());
                    pll.setNumeroPlanilla(pd_detalle.get(0).getNumeroPlanilla());
                    pll.setDetalle(pd_detalle);                    
                    
                    lst_pll.add(pll);
                }
            }
            return lst_pll;
        } 
        return null;
    }
    
    static String mes[] = { "ENE", "FEB", "MAR", "ABR", "MAY", "JUN",
                            "JUL", "AGO", "SEP", "OCT", "NOV", "DIC" };    
    
    // Establece nombre de intervalo de planilla a partir del rango
    // de fechas especificado para este
    public static String nombre_planilla(Date fi, Date ff) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String sfi = fmt.format(fi);
        String sff = fmt.format(ff);
        
        String afi[] = sfi.split("-");
        String aff[] = sff.split("-");
        
        int mes_ini = Restriction.getNumber(afi[1]);
        int mes_fin = Restriction.getNumber(aff[1]);
        mes_ini -= 1;
        mes_fin -= 1;
        
        String nombre;
        if (mes_ini >= 0 && mes_fin >= 0) {
            if (mes_ini == mes_fin) {
                nombre = mes[mes_ini];
            } else {
                nombre = mes[mes_ini] + "-" + mes[mes_fin];
            }

            nombre += " " + afi[2] +"-"+ aff[2];
            return nombre;
        }
        return "NA";
    }
}
