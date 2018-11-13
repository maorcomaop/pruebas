/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import static com.registel.rdw.controladores.ControladorGPSGrupoComandos.formatDBDate;
import com.registel.rdw.datos.ConexionExterna;
import com.registel.rdw.datos.ConsultaExterna;
import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.GPSGrupoCommandoBD;
import com.registel.rdw.datos.GPSResumenBD;
import com.registel.rdw.datos.InformacionRegistradoraBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.logica.DatosGPS;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.GPSResumen;
import com.registel.rdw.logica.GpsEnvioComando;
import com.registel.rdw.logica.GpsGrupoComando;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.ParametroConsulta;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.reportes.IPK_r;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.GPSUtil;
import com.registel.rdw.utils.MakeExcel;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.TimeUtil;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorGPS extends HttpServlet {

    private static boolean SESSION_END = false;
    private static int SESSION_TIME = 0;
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

    }
    
    public void checkTimeSession(HttpServletRequest request) {
        
        SESSION_END  = false;
        SESSION_TIME = 0;
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            Usuario user = (Usuario) session.getAttribute("login");
            if (SessionTime.expireTimeSession(user)) {
                SESSION_END = true;
            } else {
                SESSION_TIME = SessionTime.timeToExpire(user);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        checkTimeSession(request);

        String requestURI = request.getRequestURI();
        String url = "";

        if (requestURI.endsWith("/conexionGPS")) {
            conexionGPS(request, response);
        } else if (requestURI.endsWith("/visualizarGPS")) {
            visualizarGPS(request, response);
        } else if (requestURI.endsWith("/infoGPS")) {
            infoGPS(request, response);
        } else if (requestURI.endsWith("/generarIPK")) {
            url = generarIPK(request, response);
        } else if (requestURI.endsWith("/exportarHistoricoGPS")) {
            url = exportarHistoricoGPS(request, response);
        } else if (requestURI.endsWith("/loadGPSStatus")) {
            loadGPSStatus(request, response);
        }

        if (url != "") {
            request.getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }

    public void loadGPSStatus(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        int seconds = Integer.parseInt(request.getParameter("seconds"));

        String lcDate = null;
        if (seconds != 0) {
            Date lastCheckDate = new Date(((new Date()).getTime() - seconds * 1000));
            lcDate = formatDBDate.format(lastCheckDate);
        }
        String gpsIds = request.getParameter("gpsIds");

        List<GPSResumen> listGpsResumen = GPSResumenBD.selectGPSResumenByGPS(gpsIds, lcDate);

        try {

            if (listGpsResumen != null) {
                jsonResponse.put("response", "OK");
                jsonResponse.put("list", listGpsResumen);
            } else {
                jsonResponse.put("response", "ERROR");
            }
        } catch (JSONException ex) {
            Logger.getLogger(ControladorGPS.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.println(jsonResponse.toString());
        out.flush();

    }

    public void conexionGPS(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        ConexionExterna ce = new ConexionExterna();
        if (!ce.estado_conexion()) {
            out.println("* Sin conexi&oacute;n externa. Verifique par&aacute;metros de conexi&oacute;n con base de datos gps.");
        }
    }

    public void visualizarGPS(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        ParametroConsulta parc = obtenerParametros(request, response);
        String fechaIni = parc.getFechaInicio();
        String fechaFin = parc.getFechaFin();
        String placa = parc.getPlaca();
        boolean esFecha = parc.esFecha();
        boolean esPlaca = parc.esPlaca();
        String idgrupoMovil = request.getParameter("grupoMovil");
        int fechaPredeterminada = parc.getFechaPredeterminada();

        // Informacion de consulta
        //HttpSession session = request.getSession();
        session.setAttribute("fechaIni_con", fechaIni);
        session.setAttribute("fechaFin_con", fechaFin);
        session.setAttribute("placa_con", placa);
        session.setAttribute("grupo_con", idgrupoMovil);
        session.setAttribute("fechaPred_con", fechaPredeterminada);

        Usuario user = (Usuario) session.getAttribute("login");
        ConsultaExterna select_ext = new ConsultaExterna();

        ArrayList<Movil> lst_movil;
        if (idgrupoMovil != "") {
            if (idgrupoMovil.contains("r")) {
                // Seleccion de ruta
                String idRuta = idgrupoMovil.substring(1);
                String fecha = fechaIni.split(" ")[0];
                fechaIni = fecha + " 00:00:00";
                fechaFin = fecha + " 23:59:59";

                if (user.esPropietario()) {
                    lst_movil = DespachoBD.moviles_en_ruta_propietario(user.getId(), Restriction.getNumber(idRuta), fecha);
                } else {
                    lst_movil = DespachoBD.moviles_en_ruta(Restriction.getNumber(idRuta), fecha);
                }
                // Si no obtenemos informacion a traves del despacho,
                // consultamos en informacion registradora
                if (lst_movil == null || lst_movil.isEmpty()) {
                    if (user.esPropietario()) {
                        lst_movil = InformacionRegistradoraBD.movilesEnRutaPropietario(user.getId(), Restriction.getNumber(idRuta), fecha);
                    } else {
                        lst_movil = InformacionRegistradoraBD.movilesEnRuta(Restriction.getNumber(idRuta), fecha);
                    }
                }
                String slst_movil = movil2string(lst_movil);
                // - select_ext.getEventos_grupo(slst_movil, fechaIni, fechaFin);
                select_ext.getUltimosEventos(slst_movil);
            } else {
                // Seleccion de agrupacion
                if (user.esPropietario()) {
                    lst_movil = MovilBD.selectByGroupAndOwner(user.getId(), Restriction.getNumber(idgrupoMovil));
                } else {
                    lst_movil = MovilBD.selectByGroup(Restriction.getNumber(idgrupoMovil));
                }
                String slst_movil = movil2string(lst_movil);
                // - select_ext.getEventos_grupo(slst_movil, fechaIni, fechaFin);
                select_ext.getUltimosEventos(slst_movil);
            }
        } else if (user.esPropietario() && placa.equalsIgnoreCase("")) {
            lst_movil = MovilBD.selectByOwnerId(user.getId());
            String slst_movil = movil2string(lst_movil);
            select_ext.getUltimosEventos(slst_movil);
        } else {
            select_ext.getEventos(placa, fechaIni, fechaFin);
            // Si hay seleccion de vehiculo y fecha predeterminada 'Mas recientes'
            // Se filtran los ultimos cinco eventos gps
            if (placa != "" && parc.getFechaPredeterminada() == 2) {
                select_ext.filtrarListado(5);
            }
        }

        // Se especifican parametros de presentacion (indices de conteo)
        String web_path = getServletContext().getRealPath("");
        Entorno entorno = new Entorno(web_path);

        String mostrar_es = entorno.obtenerPropiedad(Constant.MOSTRAR_ENTRADA_SALIDA);
        String mostrar_no = entorno.obtenerPropiedad(Constant.MOSTRAR_NIVEL_OCUPACION);
        String mostrar_noic = entorno.obtenerPropiedad(Constant.MOSTRAR_NOIC);
        if (mostrar_es == null || mostrar_es.compareTo("1") != 0) {
            mostrar_es = "0";
        }
        if (mostrar_no == null || mostrar_no.compareTo("1") != 0) {
            mostrar_no = "0";
        }
        if (mostrar_noic == null || mostrar_noic.compareTo("1") != 0) {
            mostrar_noic = "0";
        }

        ArrayList<String> lst_evt = select_ext.getLst_evt();

        out.println("<div>");

        out.println("<select id='lst_evt'>");
        if (lst_evt != null) {
            for (int i = 0; i < lst_evt.size(); i++) {
                out.println("<option>" + lst_evt.get(i) + "</option>");
            }
        }
        out.println("</select>");

        int _esFecha = (esFecha) ? 1 : 0;
        int _esPlaca = (esPlaca) ? 1 : 0;
        out.println("<input id='pruebaFecha' type='hidden' value='" + _esFecha + "'>");
        out.println("<input id='pruebaPlaca' type='hidden' value='" + _esPlaca + "'>");
        out.println("<input id='mostrar_es' name='mostrar_es' type='hidden' value='" + mostrar_es + "'>");
        out.println("<input id='mostrar_no' name='mostrar_no' type='hidden' value='" + mostrar_no + "'>");
        out.println("<input id='mostrar_noic' name='mostrar_noic' type='hidden' value='" + mostrar_noic + "'>");

        if (SESSION_END) {
            out.println("<input id='session_end' name='session_end' type='hidden' value='1'>");
            SESSION_END = false;
        } else if (SESSION_TIME >= 1) {
            out.println("<input id='session_time' name='session_time' type='hidden' value='" + SESSION_TIME + "'>");
        }

        out.println("</div>");
    }

    public ParametroConsulta obtenerParametros(HttpServletRequest request,
            HttpServletResponse response) {

        String placa = request.getParameter("placa");
        String fechaIni = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFinal");

        String verPuntoControl = request.getParameter("verPuntoControl");
        String verPasajero = request.getParameter("verPasajero");
        String verAlarma = request.getParameter("verAlarma");
        String verConsolidado = request.getParameter("verConsolidado");
        String verOtros = request.getParameter("verOtros");
        String fechaPredeterminada = request.getParameter("fechaPredeterminada");

        boolean ver_pc = (Restriction.getNumber(verPuntoControl) == 1) ? true : false;
        boolean ver_pj = (Restriction.getNumber(verPasajero) == 1) ? true : false;
        boolean ver_alm = (Restriction.getNumber(verAlarma) == 1) ? true : false;
        boolean ver_cldo = (Restriction.getNumber(verConsolidado) == 1) ? true : false;
        boolean ver_otros = (Restriction.getNumber(verOtros) == 1) ? true : false;

        ConsultaExterna.verPuntoControl = ver_pc;
        ConsultaExterna.verPasajero = ver_pj;
        ConsultaExterna.verAlarma = ver_alm;
        ConsultaExterna.verOtros = ver_otros;
        ConsultaExterna.verConsolidado = ver_cldo;

        boolean esFecha = verificarRangoFechas(fechaIni, fechaFin);
        boolean esPlaca = verificarPlaca(placa);
        placa = (esPlaca) ? placa : "";

        // Si rango de fechas es incorrecto, ff < fi
        // se corrige al dia actual
        if (!esFecha) {
            String fecha = ffmt.format(new Date());
            fechaIni = fecha + " 00:00:00";
            fechaFin = fecha + " 23:59:59";
        }

        ParametroConsulta parc = new ParametroConsulta();

        parc.setFechaInicio(fechaIni);
        parc.setFechaFin(fechaFin);
        parc.setPlaca(placa);
        parc.setVerPuntoControl(ver_pc);
        parc.setVerPasajero(ver_pj);
        parc.setVerAlarma(ver_alm);
        parc.setVerConsolidado(ver_cldo);
        parc.setVerOtros(ver_otros);
        parc.setEsFecha(esFecha);
        parc.setEsPlaca(esPlaca);
        parc.setFechaPredeterminada(Restriction.getNumber(fechaPredeterminada));

        return parc;
    }

    public void infoGPS(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        ParametroConsulta parc = obtenerParametros(request, response);
        String fechaIni = parc.getFechaInicio();
        String fechaFin = parc.getFechaFin();
        String placa = parc.getPlaca();
        int fechaPredeterminada = parc.getFechaPredeterminada();

        // Informacion de consulta        
        session.setAttribute("fechaIni_con", fechaIni);
        session.setAttribute("fechaFin_con", fechaFin);
        session.setAttribute("placa_con", placa);
        session.setAttribute("fechaPred_con", fechaPredeterminada);

        Usuario user = (Usuario) session.getAttribute("login");
        ConsultaExterna select_ext = new ConsultaExterna();

        if (user.esPropietario() && placa.equalsIgnoreCase("")) {
            ArrayList<Movil> lst_movil = MovilBD.selectByOwnerId(user.getId());
            String slst_movil = movil2string(lst_movil);
            select_ext.getUltimosEventos(slst_movil);
        } else {
            select_ext.getEventos(placa, fechaIni, fechaFin);
            // Si hay seleccion de vehiculo y fecha predeterminada 'Mas recientes'
            // Se filtran los ultimos cinco eventos gps
            if (placa != "" && parc.getFechaPredeterminada() == 2) {
                select_ext.filtrarListado(5);
            }
        }

        ArrayList<DatosGPS> lst_oevt = select_ext.getLst_oevt();
        Map<String, Movil> hmovil = MovilBD.selectMap();

        long numIni, numFin, dist;
        numIni = numFin = dist = 0;
        String ipk = "0.0";
        String indGPS = "active";
        String idGPS = "";
        String indGPSTitle = "gps conectado";

        // Se obtiene configuracion de presentacion
        String web_path = getServletContext().getRealPath("");
        Entorno entorno = new Entorno(web_path);

        // Se construye tabla con eventos gps obtenidos                
        boolean datosGlobales = false;
        int capacidad = 0;
        int indEstadoConexion = 0;
        
        String mostrar_es   = entorno.obtenerPropiedad(Constant.MOSTRAR_ENTRADA_SALIDA);
        String mostrar_no   = entorno.obtenerPropiedad(Constant.MOSTRAR_NIVEL_OCUPACION);
        String mostrar_noic = entorno.obtenerPropiedad(Constant.MOSTRAR_NOIC);        
        String ajuste_hs    = entorno.obtenerPropiedad(Constant.AJUSTE_HORA_SERVIDOR);
        String ajuste_pto   = entorno.obtenerPropiedad(Constant.AJUSTE_PTO_CONTROL);
        
        String td_es, td_no, td_noic;
        td_es = td_no = td_noic = "<td class='no-display'>";

        td_es = (mostrar_es != null && mostrar_es.compareTo("1") == 0) ? "<td align='center'>" : td_es;
        td_no = (mostrar_no != null && mostrar_no.compareTo("1") == 0) ? "<td align='center'>" : td_no;
        td_noic = (mostrar_noic != null && mostrar_noic.compareTo("1") == 0) ? "<td align='center'>" : td_noic;
        
        boolean ajustar_hs  = (ajuste_hs != null && ajuste_hs.compareTo("0") != 0) ? true : false;
        boolean ajustar_pto = (ajuste_pto != null && ajuste_pto.compareTo("1") == 0) ? true : false;
        
        StringBuilder tr = new StringBuilder();
        int size_lst = lst_oevt.size();

        for (int i = 0; i < size_lst; i++) {
            DatosGPS data = lst_oevt.get(i);

            String fechaServidor = data.getFechaServidorDate() + " " + data.getFechaServidor();
            String fechaGPS = data.getFechaGPSDate() + " " + data.getFechaGPS();
            String col_cons = "<td></td>";
            
            // Ajuste de fecha servidor
            if (ajustar_hs) {
                int hora_ajuste = Restriction.getNumber(ajuste_hs) * 60;
                Date fechaservidor  = TimeUtil.getDateTime(fechaServidor);
                Date nfechaservidor = TimeUtil.ajusteFechaHora(fechaservidor, hora_ajuste);
                fechaServidor = TimeUtil.getStrDateTime(nfechaservidor);
            }
            // Ajuste de punto de control
            String dataPuntoControl = data.getMsg();
            String dataLocalizacion = data.getLocalizacion();
            if (ajustar_pto) {
                if (data.getEsPuntoControl() == 1) {
                    String arrPuntoControl[] = dataPuntoControl.split("-");
                    if (arrPuntoControl.length >= 3 && arrPuntoControl[2] != "") {
                        dataPuntoControl = "Punto de Control - " + arrPuntoControl[2];
                        dataLocalizacion = "<b>" + dataPuntoControl + "</b>";
                    }
                }
            }

            if (data.getEstadoConsolidacion() == 1) {
                col_cons = "<td><img class='icon-check' /></td>";
            }
            if (data.getEstadoConsolidacion() == 2) {
                col_cons = "<td><img class='icon-check2' /></td>";
            }
            if (data.getEstadoConsolidacion() == 3) {
                col_cons = "<td><img class='icon-check3' /></td>";
            }

            int entradas = data.getEntradas();
            int salidas = data.getSalidas();
            int nivel_ocupacion = entradas - salidas;
            if (nivel_ocupacion < 0) {
                nivel_ocupacion = 0;
            }
            String indicatorGPS = "active";
            String classIndicatorGPS = "indicator-gps";
            String indicatorGPSTitle = "gps conectado";
            String idGPSNumber = "";
            String noic = "0";

            if (hmovil != null) {
                String splaca = data.getPlaca().toUpperCase();
                Movil m = hmovil.get(splaca);
                if (m != null) {
                    capacidad = m.getCapacidad();
                    // nivel de ocupacion vs indice de capacidad
                    if (capacidad > 0) {
                        noic = nivel_ocupacion + "/" + capacidad;
                    }
                    idGPSNumber = m.getFkGps();
                }

                indEstadoConexion = obtenerEstadoConexion(m, data);

                switch (indEstadoConexion) {
                    case 0:
                        indGPS = "";
                        indGPSTitle = "";
                        break;
                    case 1:
                        indicatorGPS = "inactive";
                        indicatorGPSTitle = "gps desconectado";
                        break;
                    case 2:
                        indicatorGPS = "active";
                        indicatorGPSTitle = "gps conectado";
                        break;
                    case 3:
                        indicatorGPS = "unhooked";
                        indicatorGPSTitle = "gps sin enganchar";
                        break;
                    case 4:
                        indicatorGPS = "reset";
                        indicatorGPSTitle = "gps modo reset";
                        break;
                    default:
                        break;
                }
            }

            if (!datosGlobales) {
                numIni = data.getNumeracionInicial();
                numFin = data.getNumeracionFinal();
                dist = data.getDistanciaRecorrida();
                ipk = data.getIpk_str();
                indGPS = indicatorGPS;
                indGPSTitle = indicatorGPSTitle;
                idGPS = idGPSNumber;
                datosGlobales = true;
            }

            if (!"".equals(placa)) {
                classIndicatorGPS = "no-indicator-gps";
            } else {
                classIndicatorGPS = "indicator-gps";
            }

            String numeracion_ = "" + data.getNumeracion();
            String totalDia_ = "" + data.getTotalDia();
            String entradas_ = "" + data.getEntradas();
            String salidas_ = "" + data.getSalidas();
            String nivelOcupacion_ = "" + nivel_ocupacion;
            String ocupacionCapacidad_ = noic;
            boolean mostrarInfo = true;

            if (data.getGpsId().equalsIgnoreCase("0") && data.getNumeracion() == 0 && data.getTotalDia() == 0 && data.getEntradas() == 0 && data.getSalidas() == 0) {
                mostrarInfo = false;
            }
            if (!mostrarInfo) {
                numeracion_ = "--";
                totalDia_ = "--";
                entradas_ = "--";
                salidas_ = "--";
                nivelOcupacion_ = "--";
                ocupacionCapacidad_ = "--";
            }

            // String args_js = data.getLatitud() + ", " + data.getLongitud();
            String args_js = "\"" +
                    data.getLatitud()   + "$" + 
                    data.getLongitud()  + "$" +
                    fechaGPS            + "$" +
                    data.getPlaca()     + "$" +
                    data.getMsg()       + "$" +
                    data.getLocalizacion_proc() + "\"";
            
            String funcion_js   = "gps_viewPoint("+ args_js +");";
            String localizacion = "<td><a class='gps-link' onclick='"+ funcion_js +"'>"+ dataLocalizacion +"</a></td>";

            String td
                    = col_cons
                    + "<td class='no-display'></td>"
                    + "<td><div class='ind-date'><div class='" + classIndicatorGPS + " " + indicatorGPS + "' data-gps='" + data.getGpsId() + "' data-plate='" + data.getPlaca() + "' title ='" + indicatorGPSTitle + "'></div><div class='indicator-gps-date date' data-plate='" + data.getPlaca() + "'>" + fechaServidor + "</div></div></td>"
                    + "<td>" + fechaGPS + "</td>"
                    + "<td align='center'>" + data.getPlaca() + "</td>"
                    // Para filtracion
                    + "<td class='no-display'>" + data.getEsPuntoControl() + "</td>"
                    + "<td class='no-display'>" + data.getEsPasajero() + "</td>"
                    + "<td class='no-display'>" + data.getEsAlarma() + "</td>"
                    // Campos restantes
                    //+ "<td>" + data.getLocalizacion() + "</td>"
                    + localizacion

                    + "<td>" + ((data.getEsPuntoControl() == 1)
                            ? "<div class = 'mis-moviles-info punto-control'>" + dataPuntoControl + "</div>"
                            : (data.getEsAlarma() == 1 && (data.getTransReason() >= 630 && data.getTransReason() <= 636))
                                    ? "<div class = 'mis-moviles-info alarma-goldcheck'>" + data.getMsg() + "</div>"
                                    : (data.getEsAlarma() == 1)
                                            ? "<div class = 'mis-moviles-info alarma'>" + data.getMsg() + "</div>"
                                            : (data.getEsPasajero() == 1)
                                                    ? "<div class = 'mis-moviles-info pasajero'>" + data.getMsg() + "</div>" : data.getMsg()) + "</td>"

                    + "<td align='center'>" + data.getAlarma() + "</td>"
                    + "<td align='center'>" + numeracion_ + "</td>"
                    + "<td align='center'>" + totalDia_ + "</td>"
                    + td_es + entradas_ + "</td>"
                    + td_es + salidas_ + "</td>"
                    + td_no + nivelOcupacion_ + "</td>"
                    + td_noic + ocupacionCapacidad_ + "</td>"
                    + "<td align='center'>" + data.getRumbo() + "</td>"
                    + "<td align='center'>" + data.getVelocidad() + "</td>"
                    + "<td align='center'>" + data.getDistanciaMetros() + "</td>"
                    + "<td align='center'>" + data.getNombreFlota() + "</td>"; // 21            

            if (i % 2 == 0) {
                tr.append("<tr>").append(td).append("</tr>");
            } else {
                tr.append("<tr style = 'background:#F2F2F2'>")
                        .append(td)
                        .append("</tr>");
            }
        }

        if (lst_oevt.size() == 0) {
            tr = new StringBuilder();
            tr.append("<tr><td colspan='21' align='center'>Consulta no obtuvo datos.</td></tr>");

            Movil m = hmovil.get(placa.toUpperCase());

            if (m != null) {
                idGPS = m.getFkGps();
                indEstadoConexion = obtenerEstadoConexion(m, null);

                switch (indEstadoConexion) {
                    case 0:
                        indGPS = "";
                        indGPSTitle = "";
                        break;
                    case 1:
                        indGPS = "inactive";
                        indGPSTitle = "gps desconectado";
                        break;
                    case 2:
                        indGPS = "active";
                        indGPSTitle = "gps conectado";
                        break;
                    case 3:
                        indGPS = "unhooked";
                        indGPSTitle = "gps sin enganchar";
                        break;
                    case 4:
                        indGPS = "reset";
                        indGPSTitle = "gps modo reset";
                        break;
                    default:
                        break;
                }
            }
        }
        out.println(tr.toString());

        int _esFecha = (parc.esFecha()) ? 1 : 0;
        int _esPlaca = (parc.esPlaca()) ? 1 : 0;

        out.println("<tr style='display: none;'>");

        out.println("<td><input id='pruebaFecha' type='hidden' value='" + _esFecha + "'></td>");
        out.println("<td><input id='pruebaPlaca' type='hidden' value='" + _esPlaca + "'></td>");

        out.println("<td><input id='info_placa'  type='hidden'  value='" + placa + "'></td>");
        out.println("<td><input id='info_numeracionInicial'  type='hidden'  value='" + numIni + "'></td>");
        out.println("<td><input id='info_numeracionFinal'    type='hidden'  value='" + numFin + "'></td>");
        out.println("<td><input id='info_distanciaRecorrida' type='hidden'  value='" + dist + "'></td>");
        out.println("<td><input id='info_ipk' type='hidden'  value='" + ipk + "'></td>");
        out.println("<td><input id='info_indicadorGPS' type='hidden'  value='" + indGPS + "'></td>");
        out.println("<td><input id='info_idGPS' type='hidden'  value='" + idGPS + "'></td>");
        out.println("<td><input id='info_indicadorGPSTitle' type='hidden'  value='" + indGPSTitle + "'></td>");
        out.println("<td><input id='info_numDatos' type='hidden'  value='" + lst_oevt.size() + "'></td>");
        out.println("<td colspan='14'></td>");

        out.println("</tr>");

        if (SESSION_END) {
            out.println("<input id='session_end' name='session_end' type='hidden' value='1'>");
            SESSION_END = false;
        } else if (SESSION_TIME >= 1) {
            out.println("<input id='session_time' name='session_time' type='hidden' value='" + SESSION_TIME + "'>");
        }
    }

    public int obtenerEstadoConexion(Movil m, DatosGPS data) {
        int indicatorGPS = 0;
        if (data != null) {
            indicatorGPS = 2;
        }

        Date nowDate = new Date();
        if (m != null) {
            Date lastReportDate = m.getFechaUltimoReporte();
            if (lastReportDate != null) {
                if ((m.getEstadoConexion() == 1 && (nowDate.getTime() - lastReportDate.getTime() > 3600000)) || m.getEstadoConexion() == 0) {
                    indicatorGPS = 1;

                } else if (m.getEstadoConexion() == 1) {
                    indicatorGPS = 2;

                } else if (m.getEstadoConexion() == 2) {
                    indicatorGPS = 3;

                } else if (m.getEstadoConexion() == 3) {
                    indicatorGPS = 4;

                }
            } else if (data != null && (nowDate.getTime() - data.getFechaHoraServidor().getTime() > 3600000)) {
                indicatorGPS = 1;

            }
        } else if (data != null && (nowDate.getTime() - data.getFechaHoraServidor().getTime() > 3600000)) {
            indicatorGPS = 1;
        }

        return indicatorGPS;
    }

    public String exportarHistoricoGPS(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ParametroConsulta parc = obtenerParametros(request, response);
        String fechaIni = parc.getFechaInicio();
        String fechaFin = parc.getFechaFin();
        String placa = parc.getPlaca();
        boolean ver_con = parc.verConsolidado();
        boolean vehiculo_sel = (placa == null || placa == "") ? false : true;

        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("login");
        ConsultaExterna select_ext = new ConsultaExterna();

        if (user.esPropietario() && placa == "") {
            ArrayList<Movil> lst_movil = MovilBD.selectByOwnerId(user.getId());
            String slst_movil = movil2string(lst_movil);
            select_ext.getUltimosEventos(slst_movil);
        } else {
            if (!vehiculo_sel) {
                ConsultaExterna.restablecerFiltro();
            }
            select_ext.getEventos(placa, fechaIni, fechaFin);
        }

        String web_path = getServletContext().getRealPath("");
        Entorno entorno = new Entorno(web_path);

        ArrayList<DatosGPS> lst_oevt = select_ext.getLst_oevt();

        MakeExcel rpte = new MakeExcel();
        rpte.reporte_HistoricoGPS("Historico gps", lst_oevt, vehiculo_sel, entorno);

        String nombre_reporte = (vehiculo_sel) ? "-" + placa : "";

        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=HistoricoGPS" + nombre_reporte + ".xls");

        HSSFWorkbook file = rpte.getExcelFile();
        file.write(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return "/RDW1/app/seguimiento/infogps.jsp";
    }

    public String generarIPK(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String placa = request.getParameter("placa_ipk");
        String fechaIni = request.getParameter("fechaInicio_ipk");
        String fechaFin = request.getParameter("fechaFinal_ipk");

        boolean esFecha = verificarRangoFechas(fechaIni, fechaFin);

        // Si rango de fechas es incorrecto, ff < fi
        // se corrige al dia actual
        if (!esFecha) {
            String fecha = ffmt.format(new Date());
            fechaIni = fecha + " 00:00:00";
            fechaFin = fecha + " 23:59:59";
        }

        if (placa == null || placa == "") {
            request.setAttribute("msg", "* Seleccione un veh&iacute;culo para reporte IPK.");
            return "/RDW1/app/seguimiento/infogps.jsp";
        }
                
        String web_path = getServletContext().getRealPath("");
        Entorno entorno = new Entorno(web_path);

        // Eventos globales para un vehiculo
        ConsultaExterna select_ext = new ConsultaExterna();
        select_ext.getEventos(placa, fechaIni, fechaFin);

        // Extraccion de IPK por bases
        ArrayList<DatosGPS> lst_oevt = select_ext.getLst_oevt();
        ArrayList<IPK_r> lst_ipkr = GPSUtil.IPK_porBase(lst_oevt);

        MakeExcel rpte = new MakeExcel();
        rpte.reporte_IPK_GPS_Resumen("Resumen IPK", lst_ipkr);
        rpte.reporte_IPK_GPS("Detalle IPK", lst_ipkr, false, entorno);
        rpte.reporte_IPK_GPS("IPK Consolidado", lst_ipkr, true, entorno); // consolidado : true

        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte-IPK-" + placa + ".xls");

        HSSFWorkbook file = rpte.getExcelFile();
        file.write(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return "/RDW1/app/seguimiento/infogps.jsp";

    }

    ////////////////////////////////////////////////////////////////////////////
    ///// Funciones auxiliares
    ////////////////////////////////////////////////////////////////////////////
    public String movil2string(ArrayList<Movil> lst) {

        String lst_movil = "";

        for (Movil m : lst) {
            String placa = "'" + m.getPlaca() + "'";
            lst_movil += (lst_movil == "") ? placa : "," + placa;
        }

        return lst_movil;
    }

    public boolean verificarRangoFechas(String fechaIni, String fechaFin) {

        long lfi, lff;
        lfi = lff = 0;

        // yyyy-MM-dd HH:MM
        try {
            if (fechaIni != "" && fechaFin != "") {
                String horaIni = fechaIni.split(" ")[1];
                String horaFin = fechaFin.split(" ")[1];
                fechaIni = fechaIni.split(" ")[0];
                fechaFin = fechaFin.split(" ")[0];

                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date fi = fmt.parse(fechaIni);
                lfi = fi.getTime();
                Date ff = fmt.parse(fechaFin);
                lff = ff.getTime();

                long lhi = Long.parseLong(horaIni.split(":")[0]);
                long lhf = Long.parseLong(horaFin.split(":")[0]);
                long lmi = Long.parseLong(horaIni.split(":")[1]);
                long lmf = Long.parseLong(horaFin.split(":")[1]);

                if (fechaFin.compareTo(fechaIni) > 0) {
                    return true;
                } else if (fechaFin.compareTo(fechaIni) == 0) {
                    if (lhf > lhi) {
                        return true;
                    } else if (lhf == lhi) {
                        if (lmf >= lmi) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (ParseException e) {
            System.err.println(e);
        }
        return false;
    }

    public boolean verificarPlaca(String placa) {
        if (placa == "" || Restriction.isPlaca(placa)) {
            return true;
        }
        return false;
    }
}
