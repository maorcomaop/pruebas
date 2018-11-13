/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.DatosGPS;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.VueltaCerrada;
import com.registel.rdw.utils.Restriction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author lider_desarrollador
 */
public class ConsultaExterna {

    public static boolean verPuntoControl = false;
    public static boolean verPasajero = false;
    public static boolean verAlarma = false;
    public static boolean verOtros = false;
    public static boolean verConsolidado = false;

    private ArrayList<String>   lst_evt;
    private ArrayList<DatosGPS> lst_oevt;
    private Map<String, Movil>  hmovil;

    private String separador = "||";

    public ConsultaExterna() {
        hmovil = MovilBD.selectMap();
    }

    ////////////////////////////////////////////////////////////////////////////
    ///// Visualizacion GPS
    ////////////////////////////////////////////////////////////////////////////
    
    // Verifica si evento/registro gps pasado como parametro concuerda
    // con filtro establecido (punto control, pasajero, alarma, consolidado, otros)
    public boolean checkFiltro(DatosGPS data) {

        if (data == null) {
            return false;
        }

        if (verPuntoControl && data.getEsPuntoControl() == 1) {
            return true;
        }
        if (verPasajero && data.getEsPasajero() == 1) {
            return true;
        }
        if (verAlarma && data.getEsAlarma() == 1) {
            return true;
        }
        if (verConsolidado && data.getEstadoConsolidacion() >= 1) {
            return true;
        }
        if (verOtros
                && data.getEsPuntoControl() != 1
                && data.getEsPasajero() != 1
                && data.getEsAlarma() != 1
                && data.getEstadoConsolidacion() == 0) {
            return true;
        }
        boolean ningunFiltro = !(
                    verPuntoControl ||
                    verPasajero ||
                    verAlarma ||
                    verConsolidado ||
                    verOtros
                );
        
        if (ningunFiltro)
            return true;
        
        return false;
    }

    // Establece filtros a su valor por defecto
    public static void restablecerFiltro() {
        verPuntoControl = false;
        verPasajero = false;
        verAlarma = false;
        verConsolidado = false;
        verOtros = false;
    }

    // Inicia consulta de ultimos eventos gps de cada vehiculo
    public void getUltimosEventos() {
        getUltimosEventos(null);
    }

    // Consulta eventos gps de cada vehiculo pasado en parametro
    // (lista de placa de vehiculos formateada)
    public void getUltimosEventos(String lstMoviles) {

        ConexionExterna conext = new ConexionExterna();

        String placas = "";
        if (lstMoviles != null) {
            placas = "WHERE placa IN (" + lstMoviles + ") ";
        }
        try {
            Connection con = conext.conectar();

            String sql = "SELECT fw.*,"
                    + " LCASE(REPLACE(REPLACE(fw.localizacion, '<B>', ''), '</B>', '')) AS localizacion_proc,"
                    + " DATE(fw.fecha_server) AS fecha_server_date,"
                    + " fw.fecha_server AS fecha_server,"
                    + " DATE(fw.fecha_gps) AS fecha_gps_date,"
                    + " fw.fecha_gps AS fecha_gps,"
                    + " IF(fw.msg LIKE '%Punto de Control%', 1, 0) AS es_punto_control,"
                    + " IF(fw.msg LIKE '%Petición de Pasajero%', 1, 0) AS es_pasajero,"
                    + " IF(CAST(fw.alarma AS unsigned) > 0, 1, 0) AS es_alarma,"
                    + " 0 AS numeracion_inicial,"
                    + " 0 AS numeracion_final,"
                    + " 0 AS distancia_recorrida"
                    + " FROM tbl_forwarding_wtch AS fw"
                    + " INNER JOIN"
                    + " (SELECT"
                    + " placa, max(fecha_gps) AS maxfec"
                    + " FROM tbl_forwarding_wtch " + placas + ""
                    + " GROUP BY placa) fwi ON"
                    + " fwi.placa = fw.placa AND fwi.maxfec = fw.fecha_gps"
                    + " GROUP BY fw.placa ORDER BY fw.fecha_gps DESC";

            //System.out.println("sql last events : " + sql);

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            lst_evt = new ArrayList<>();
            lst_oevt = new ArrayList<>();
            while (rs.next()) {
                // Recupera y crea objeto para conservar registros
                DatosGPS data = createData(rs);

                // Filtra registro gps
                if (checkFiltro(data)) {
                    // Genera formato texto de registro consultado
                    lst_evt.add(formatData(data));
                    lst_oevt.add(data);
                }
            }
            rs.close();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
    }

    
    // Consulta eventos gps en un rango de fecha y hora especifico para un vehiculo
    public void getEventos(
            String placa,
            String fechaIni,
            String fechaFin) {

        if ("".equals(placa)) {
            getUltimosEventos();
            return;
        }
        System.out.println(fechaIni + " / " + fechaFin + " / " + placa);

        ConexionExterna conext = new ConexionExterna();
        try {
            Connection con = conext.conectar();
            
            String sql = "SELECT *,"
                    + " LCASE(REPLACE(REPLACE(fw.localizacion, '<B>', ''), '</B>', '')) AS localizacion_proc,"
                    + " fw.fecha_server AS fecha_server,"
                    + " DATE(fw.fecha_server) AS fecha_server_date,"
                    + " fw.fecha_gps AS fecha_gps,"
                    + " DATE(fw.fecha_gps) AS fecha_gps_date,"
                    + " IF(fw.msg LIKE '%Punto de Control%', 1, 0) AS es_punto_control,"
                    + " IF(fw.msg LIKE '%Petición de Pasajero%', 1, 0) AS es_pasajero,"
                    + " IF(CAST(fw.alarma AS unsigned) > 0, 1, 0) AS es_alarma,"
                    + " (select min(fwi.numeracion) from tbl_forwarding_wtch as fwi where"
                    + "     fwi.placa = ? and (fwi.fecha_gps BETWEEN ? AND ?) and"
                    + "     fwi.numeracion > 0) as numeracion_inicial,"
                    + " (select max(fwi.numeracion) from tbl_forwarding_wtch as fwi where"
                    + "     fwi.placa = ? and (fwi.fecha_gps BETWEEN ? AND ?) and"
                    + "     fwi.numeracion > 0) as numeracion_final,"
                    + " (select sum(fwi.distancia_metros) from tbl_forwarding_wtch as fwi where"
                    + "     fwi.placa = ? and (fwi.fecha_gps BETWEEN ? AND ?)) as distancia_recorrida"
                    + " FROM tbl_forwarding_wtch AS fw"
                    + " WHERE fw.placa = ?"
                    + " AND (fw.fecha_gps BETWEEN ? AND ?)"
                    + " ORDER BY fw.fecha_gps DESC, fw.id DESC";

            System.out.println("getEventos : " + sql);

            //System.out.println("sql get events : " + sql);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            ps.setString(2, fechaIni);
            ps.setString(3, fechaFin);
            ps.setString(4, placa);
            ps.setString(5, fechaIni);
            ps.setString(6, fechaFin);

            ps.setString(7, placa);
            ps.setString(8, fechaIni);
            ps.setString(9, fechaFin);
            ps.setString(10, placa);
            ps.setString(11, fechaIni);
            ps.setString(12, fechaFin);
            ResultSet rs = ps.executeQuery();

            lst_evt = new ArrayList<>();
            lst_oevt = new ArrayList<>();

            while (rs.next()) {
                // Recupera y crea objeto para conservar registros
                DatosGPS data = createData(rs);

                // Filtra registro gps
                if (checkFiltro(data)) {
                    // Genera formato texto de registro consultado
                    //lst_evt.add(formatData(data));
                    lst_oevt.add(data);
                }
            }
            // Calcula IPK progresivo para cada registro y 
            // crea listado con formato texto            
            ipkPorPunto();            
            
            rs.close();
            ps.close();            

        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
    }

    // Establece consulta de eventos gps para un conjunto de vehiculos
    // listados en parametro de texto formateado
    public String sql_eventosPorGrupo(String lst_movil) {

        String sql = "SELECT *,"
                + " LCASE(REPLACE(REPLACE(fw.localizacion, '<B>', ''), '</B>', '')) AS localizacion_proc,"
                + " DATE(fw.fecha_server) AS fecha_server_date,"
                + " fw.fecha_server AS fecha_server,"
                + " DATE(fw.fecha_gps) AS fecha_gps_date,"
                + " fw.fecha_gps AS fecha_gps,"
                + " IF(fw.msg LIKE '%Punto de Control%', 1, 0) AS es_punto_control,"
                + " IF(fw.msg LIKE '%Petición de Pasajero%', 0, 0) AS es_pasajero,"
                + " IF(CAST(fw.alarma AS unsigned) > 0, 1, 0) AS es_alarma,"
                + " 0 AS numeracion_inicial,"
                + " 0 AS numeracion_final,"
                + " 0 AS distancia_recorrida"
                + " FROM tbl_forwarding_wtch AS fw"
                + " WHERE fw.placa IN (" + lst_movil + ")"
                + " AND (fw.fecha_gps BETWEEN ? AND ?)"
                + " ORDER BY fw.fecha_gps DESC";

        return sql;
    }
   
    // Consulta eventos en un rango de fechas especifico para una lista de vehiculos     
    public void getEventos_grupo(
            String lst_movil,
            String fechaIni,
            String fechaFin) {

        System.out.println(fechaIni + " / " + fechaFin + " / " + lst_movil);
        if (lst_movil == "") {
            return;
        }

        ConexionExterna conext = new ConexionExterna();
        try {
            Connection con = conext.conectar();
            String sql = sql_eventosPorGrupo(lst_movil);

            //System.out.println("sql get events : " + sql);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fechaIni);
            ps.setString(2, fechaFin);

            ResultSet rs = ps.executeQuery();

            lst_evt = new ArrayList<String>();
            lst_oevt = new ArrayList<DatosGPS>();

            while (rs.next()) {
                // Recupera y crea objeto para conservar registros
                DatosGPS data = createData(rs);

                // Filtra registro gps
                if (checkFiltro(data)) {
                    // Genera formato texto de registro consultado
                    lst_evt.add(formatData(data));
                    lst_oevt.add(data);
                }
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
    }

    // Consulta coordenadas gps segun etiqueta/codigo de punto control    
    public static String[] coordenadaGPS(String etq_pto) {

        ConexionExterna conext = new ConexionExterna();

        String sql = "SELECT fw.latitud, fw.longitud"
                + " FROM tbl_forwarding_wtch AS fw"
                + " WHERE fw.msg LIKE '%" + etq_pto + "%' LIMIT 1";

        try {
            Connection con = conext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] latlon = new String[2];
                latlon[0] = rs.getString("latitud");
                latlon[1] = rs.getString("longitud");
                return latlon;
            }

        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
        return null;
    }

    // Calcula indices de:
    //   - Nivel de ocupacion
    //   - Noic = nivel ocupacion vs indice capacidad
    public void calcularIndices(DatosGPS data) {

        String placa = data.getPlaca().toUpperCase();

        Movil movil   = (hmovil != null) ? hmovil.get(placa) : null;
        int capacidad = (movil  != null) ? movil.getCapacidad() : 0;

        int nivel_ocupacion = data.getEntradas() - data.getSalidas();
        if (nivel_ocupacion < 0) {
            nivel_ocupacion = 0;
        }
        String noic = nivel_ocupacion + " / " + capacidad;

        data.setNivelOcupacion(nivel_ocupacion);
        data.setNoic(noic);
    }

    // Recupera valores de campos consultados y los asigna
    // a variables correspondiente de objeto DatosGPS creado
    public DatosGPS createData(ResultSet rs) throws SQLException {

        DecimalFormat df = new DecimalFormat("0.00");
        
        DatosGPS data = new DatosGPS();
        data.setId(rs.getLong("id"));
        data.setFechaServidor(rs.getTime("fecha_server"));
        data.setFechaServidorDate(rs.getDate("fecha_server_date"));
        data.setFechaHoraServidor(rs.getTimestamp("fecha_server"));
        data.setFechaGPS(rs.getTime("fecha_gps"));
        data.setFechaGPSDate(rs.getDate("fecha_gps_date"));
        data.setFechaHoraGPS(rs.getTimestamp("fecha_gps"));
        data.setEsPuntoControl(rs.getInt("es_punto_control"));
        data.setEsPasajero(rs.getInt("es_pasajero"));
        data.setEsAlarma(rs.getInt("es_alarma"));
        data.setRumbo(rs.getString("rumbo"));
        data.setVelocidad(rs.getInt("velocidad"));
        data.setMsg(rs.getString("msg"));
        data.setTotalDia(rs.getInt("total_dia"));
        data.setNumeracion(rs.getLong("numeracion"));
        data.setNumeracionInicial(rs.getLong("numeracion_inicial"));
        data.setNumeracionFinal(rs.getLong("numeracion_final"));
        data.setDistanciaRecorrida(rs.getLong("distancia_recorrida"));
        data.setEntradas(rs.getInt("entradas"));
        data.setSalidas(rs.getInt("salidas"));
        data.setAlarma(rs.getString("alarma"));
        data.setDistanciaMetros(rs.getInt("distancia_metros"));
        data.setLocalizacion(rs.getString("localizacion"));
        data.setLocalizacion_proc(rs.getString("localizacion_proc"));
        data.setLatitud(rs.getString("latitud"));
        data.setLongitud(rs.getString("longitud"));
        data.setPlaca(rs.getString("placa"));
        data.setNombreFlota(rs.getString("nombre_flota"));
        data.setEstadoConsolidacion(rs.getInt("estado_consolidacion"));
        data.setTransReason(rs.getInt("trans_reason"));
        data.setTransReasonSpecificData(rs.getString("trans_reason_specific_data"));
        data.setRumboRadianes(rs.getDouble("rumbo_radianes"));
        data.setGpsId(rs.getString("gps_id"));
        
        long numeracion_inicial  = data.getNumeracionInicial();
        long numeracion_final    = data.getNumeracionFinal();
        long distancia           = data.getDistanciaRecorrida();
        double ddistancia        = (double) distancia / 1000.0;
        long npasajeros          = (numeracion_final - numeracion_inicial);
        
        double ipk = 0.0;
        if (distancia > 0) {
            ipk = (double) npasajeros / ddistancia;
        }
        data.setIpk(ipk);
        data.setIpk_str(df.format(ipk));        

        if (data.getEsAlarma() == 0) {
            data.setAlarma("0");
        }

        String placa = data.getPlaca();
        if (placa == null || placa == "" || placa.compareTo("null") == 0) {
            return null;
        }

        // Inicia calculo de indices
        calcularIndices(data);                

        return data;
    }

    // Crea cadena de texto formateada (separada por valor definido)
    // para valores de objeto DatosGPS    
    public String formatData(DatosGPS data) {

        String fmt
                = data.getId() + separador
                + // 0
                data.getFechaServidor() + separador
                + // 1                
                data.getFechaGPS() + separador
                + // 2            
                data.getRumbo() + separador
                + // 3
                data.getVelocidad() + separador
                + // 4
                data.getMsg() + separador
                + // 5
                data.getTotalDia() + separador
                + // 6
                data.getNumeracion() + separador
                + // 7
                data.getEntradas() + separador
                + // 8
                data.getSalidas() + separador
                + // 9
                data.getAlarma() + separador
                + // 10
                data.getDistanciaMetros() + separador
                + // 11
                data.getLocalizacion() + separador
                + // 12
                data.getLatitud() + separador
                + // 13
                data.getLongitud() + separador
                + // 14
                data.getPlaca() + separador
                + // 15
                data.getNombreFlota() + separador
                + // 16
                data.getFechaServidorDate() + separador
                + // 17
                data.getFechaGPSDate() + separador
                + // 18
                data.getNumeracionInicial() + separador
                + // 19
                data.getEsPuntoControl() + separador
                + // 20
                data.getEsPasajero() + separador
                + // 21
                data.getEsAlarma() + separador
                + // 22
                data.getNumeracionFinal() + separador
                + // 23
                data.getDistanciaRecorrida() + separador
                + // 24
                data.getTransReason() + separador
                + // 25
                data.getTransReasonSpecificData() + separador
                + // 26
                data.getRumboRadianes() + separador
                + // 27
                data.getNivelOcupacion() + separador
                + // 28
                data.getNoic() + separador // Nivel ocupacion vs indice de capacidad
                + // 29                
                data.getIpk_str() + separador
                + // 30
                data.getGpsId() + separador
                + // 31
                data.getDistanciaParcial() + separador
                + // 32
                data.getIpk_str_parcial();
                  // 33
        return fmt;
    }        
    
    // Filtra listados a una cantidad de registros especifica.
    public void filtrarListado(int max_size) {
        
        if (lst_evt != null && lst_evt.size() > max_size) {
            int size = lst_evt.size();
            for (int i = size-1; i >= max_size; i--) {
                lst_evt.remove(i);
            }
        }
        
        if (lst_oevt != null && lst_oevt.size() > max_size) {
            int size = lst_oevt.size();
            for (int i = size-1; i >= max_size; i--) {
                lst_oevt.remove(i);
            }
        }
    }
    
    // Calcula valor IPK parcial por cada punto.
    // (Logrado hasta el punto actual referenciado).
    public void ipkPorPunto() {
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        if (lst_oevt != null && lst_oevt.size() > 0) {
            
            int size = lst_oevt.size();
            long distancia          = 0;
            long numeracionInicial  = 0;
            long numeracionActual   = 0;            
            boolean numeracionVista = false;            
            
            double ipk = 0.0;
            
            for (int i = size-1; i >= 0; i--) {
                DatosGPS data = lst_oevt.get(i);
                if (!numeracionVista &&
                    data.getNumeracion() > 0) {
                    numeracionInicial = data.getNumeracion();
                    numeracionVista = true;
                }
                if (data.getNumeracion() > 0) {
                    numeracionActual = data.getNumeracion();
                }
                distancia += data.getDistanciaMetros();
                
                // Se calcula IPK parcial en cada punto
                long npasajeros = numeracionActual - numeracionInicial;
                if (distancia == 0) {
                    ipk = 0.0;
                } else {
                    double ddistancia = (double) distancia / 1000.0;
                    ipk = (double) npasajeros / ddistancia;
                }
                data.setIpkParcial(ipk);
                data.setIpk_str_parcial(df.format(ipk));
                data.setDistanciaParcial(distancia);
                //System.out.println("> " + data);
            }
            
            // Crea lista de informacion gps (cadenas de texto formateada)
            lst_evt = new ArrayList<String>();
            for (DatosGPS data : lst_oevt) {
                lst_evt.add(formatData(data));
            }
        }
    }

    // Mantiene numeracion en cada registro hasta encontrar una numeracion nueva
    // (y seguir este procedimiento hasta repasar todos los registros)
    public void mantenerNumeracion() {

        if (lst_oevt != null && lst_oevt.size() > 0) {

            long numeracion = maximaNumeracion();

            for (int i = 0; i < lst_oevt.size(); i++) {
                DatosGPS data = lst_oevt.get(i);
                if (data.getNumeracion() == 0 ||
                    data.getNumeracion() > numeracion) {
                    data.setNumeracion(numeracion);
                } else if (data.getNumeracion() <= numeracion) {
                    numeracion = data.getNumeracion();
                }
            }

            // Crea lista de informacion gps (cadenas de texto formateada)
            lst_evt = new ArrayList<String>();
            for (DatosGPS data : lst_oevt) {
                lst_evt.add(formatData(data));
            }
        }
    }

    // Recupera maxima numeracion hallada en listado global de registros gps,
    // de no hallarse alguno se retorno 0
    public long maximaNumeracion() {

        long max_n = Long.MIN_VALUE;

        if (lst_oevt != null && lst_oevt.size() > 0) {
            for (int i = 0; i < lst_oevt.size(); i++) {
                long n = lst_oevt.get(i).getNumeracion();
                if (n > 0 && n > max_n) {
                    max_n = n;
                    break;
                }
            }
        }

        if (max_n == Long.MIN_VALUE) {
            return 0;
        }
        return max_n;
    }

    public ArrayList<String> getLst_evt() {
        return lst_evt;
    }

    public ArrayList<DatosGPS> getLst_oevt() {
        return lst_oevt;
    }

    ////////////////////////////////////////////////////////////////////////////
    ///// Eventos de consolidacion
    ////////////////////////////////////////////////////////////////////////////
    
    // Consulta ultimo conteo (registro de numeracion) ocurrido
    // para un vehiculo y fecha en especifico
    public DatosGPS ultimoConteo(String fecha, String placa) {

        ConexionExterna conext = new ConexionExterna();
        Connection con = null;

        String sql = "SELECT * FROM tbl_forwarding_wtch WHERE"
                + " fecha_gps <= ? AND"
                + " placa = ? AND"
                + " numeracion > 0"
                + " ORDER BY fecha_gps desc LIMIT 1";

        try {
            con = conext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, placa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DatosGPS gps = new DatosGPS();
                gps.setNumeracion(rs.getLong("numeracion"));
                gps.setEntradas(rs.getInt("entradas"));
                gps.setSalidas(rs.getInt("salidas"));
                gps.setTotalDia(rs.getInt("total_dia"));
                gps.setNombreFlota(rs.getString("nombre_flota"));                
                gps.setGpsId(rs.getString("gps_id"));
                gps.setRumbo(rs.getString("rumbo"));
                //gps.setMsg(rs.getString("msg"));
                //gps.setLatitud(rs.getString("latitud"));
                //gps.setLongitud(rs.getString("longitud"));                
                return gps;
            }

        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            conext.desconectar();
        }
        return null;
    }

    // Registra el cierre de una vuelta para un vehiculo y fecha en especifico.
    // El registro se realiza en tablas tbl_forwarding_wtch y tbl_vuelta_cerrada
    // de diferente base de datos. En caso de no lograrse algun registro, la
    // operacion es cancelada y no ocurre el cierre.
    public boolean cerrarVuelta(
            VueltaCerrada vc,
            DatosGPS gps) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con_rdw = pila_con.obtenerConexion();

        ConexionExterna conext = new ConexionExterna();
        Connection con = null;
        boolean error = false;

        String sql = "INSERT INTO tbl_forwarding_wtch ("                    
                    + "fecha_server,"
                    + "fecha_gps,"
                    + "placa,"
                    + "msg,"
                    + "localizacion,"                    
                    + "nombre_flota,"
                    + "numeracion,"
                    + "entradas,"
                    + "salidas,"
                    + "total_dia,"
                    + "gps_id,"
                    + "rumbo,"
                    + "latitud,"
                    + "longitud) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String sql1 = "INSERT INTO tbl_vuelta_cerrada ("
                    + "fecha,"
                    + "placa,"
                    + "numero_interno,"
                    + "base,"
                    + "numeracion,"
                    + "motivo,"
                    + "usuario) VALUES (?,?,?,?,?,?,?)";

        try {
            con = conext.conectar();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vc.getFechaStr());
            ps.setString(2, vc.getFechaStr());
            ps.setString(3, vc.getPlaca());
            ps.setString(4, vc.getBaseGps());
            ps.setString(5, vc.getBaseGps());
            ps.setString(6, gps.getNombreFlota());
            ps.setLong(7, gps.getNumeracion());
            ps.setInt(8, gps.getEntradas());
            ps.setInt(9, gps.getSalidas());
            ps.setLong(10, gps.getTotalDia());
            ps.setString(11, gps.getGpsId());
            ps.setString(12, gps.getRumbo());
            ps.setString(13, gps.getLatitud());
            ps.setString(14, gps.getLongitud());

            int n = ps.executeUpdate();

            con_rdw.setAutoCommit(false);
            PreparedStatement ps1 = con_rdw.prepareStatement(sql1);
            ps1.setString(1, vc.getFechaStr());
            ps1.setString(2, vc.getPlaca());
            ps1.setString(3, vc.getNumero_interno());
            ps1.setString(4, vc.getBase());
            ps1.setLong(5, gps.getNumeracion());
            ps1.setString(6, vc.getMotivo());
            ps1.setString(7, vc.getUsuario());

            int n1 = ps1.executeUpdate();

            if (n == 1 && n1 == 1) {
                con.commit();
                con_rdw.commit();
                return true;
            } else {
                con.rollback();
                con_rdw.rollback();
            }

        } catch (SQLException e) {
            System.err.println(e);
            error = true;
        } catch (ClassNotFoundException e) {
            System.err.println(e);
            error = true;
        } finally {
            try {
                con.setAutoCommit(true);
                con_rdw.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e);
            }

            if (error) {
                if (con != null) {
                    try {
                        con.rollback();
                        con_rdw.rollback();
                    } catch (SQLException e) {
                        System.err.println(e);
                    }
                }
            }

            conext.desconectar();
            pila_con.liberarConexion(con_rdw);
        }

        return false;
    }
}
