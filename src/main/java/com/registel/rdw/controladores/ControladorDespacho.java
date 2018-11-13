/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.DespachoControlBD;
import com.registel.rdw.datos.DespachoManualBD;
import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.datos.ProgramacionRutaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Despacho;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.GrupoRodamiento;
import com.registel.rdw.logica.IntervaloDespacho;
import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.PlanillaDespachoMin;
import com.registel.rdw.logica.ProgramacionRuta;
import com.registel.rdw.logica.Rodamiento;
import com.registel.rdw.logica.RutaRodamiento;
import com.registel.rdw.logica.SelectC;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.DespachoControl;
import com.registel.rdw.utils.DespachoControlHTML;
import com.registel.rdw.utils.DespachoControlPorIntervalo;
import com.registel.rdw.utils.DespachoControlPorTiempo;
import com.registel.rdw.utils.DespachoUtil;
import com.registel.rdw.utils.DespachoUtil2;
import com.registel.rdw.utils.MakeExcel;
import com.registel.rdw.utils.PlanillaUtil;
import com.registel.rdw.utils.ReporteUtil;
import com.registel.rdw.utils.ReporteUtilExcel;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.TimeUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 **
 * @author lider_desarrollador
 */
public class ControladorDespacho extends HttpServlet {
    
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat hfmt_full = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm");        
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        String requestURI = request.getRequestURI();
        String url = "";
        
        if (requestURI.endsWith("/crearConfDespacho")) {
            url = crearConfiguracionDespacho(request, response);
        } else if (requestURI.endsWith("/verConfDespacho")) {
            url = verConfiguracionDespacho(request, response);
        } else if (requestURI.endsWith("/pre_intercambiarDespacho")) {
            url = pre_intercambiarDespacho(request, response);   
        } else if (requestURI.endsWith("/intercambiarDespacho")) {
            url = intercambiarDespacho(request, response);
        } else if (requestURI.endsWith("/pre_editarConfDespacho")) {
            url = pre_editarConfiguracionDespacho(request, response);
        } else if (requestURI.endsWith("/editarConfDespacho")) {
            url = editarConfiguracionDespacho(request, response);
        } else if (requestURI.endsWith("/eliminarDespacho")) {
            url = eliminarDespacho(request, response);
        } else if (requestURI.endsWith("/verDespacho")) {
            url = verDespacho(request, response);
        } else if (requestURI.endsWith("/generarPlanilla")) {
            url = generarPlanilla(request, response);
        } else if (requestURI.endsWith("/generarPlanillaAPedido")) {
            url = generarPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/pre_editarPlanillaAPedido")) {
            url = pre_editarPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/editarPlanillaAPedido")) {
            url = editarPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/editarFechasPlanilla")) {
            url = editarFechasPlanilla(request, response);
        } else if (requestURI.endsWith("/eliminarPlanilla")) {            
            url = eliminarPlanilla(request, response);
        } else if (requestURI.endsWith("/eliminarPlanillaAPedido")) {                        
            url = eliminarPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/cancelarPlanillaAPedido")) {
            url = cancelarPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/verPlanilla")) {
            verPlanilla(request, response);            
        } else if (requestURI.endsWith("/verPlanillaAPedido")) {
            verPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/imprimirPlanilla")) {
            url = imprimirPlanilla(request, response);
        } else if (requestURI.endsWith("/imprimirPlanillaAPedido")) {
            url = imprimirPlanillaAPedido(request, response);
        } else if (requestURI.endsWith("/verControlDespacho")) {
            verControlDespacho(request, response);
        } else if (requestURI.endsWith("/creaProgramacionRuta")) {
            url = creaProgramacionRuta(request, response);
        } else if (requestURI.endsWith("/actualizaProgramacionRuta")) {
            url = actualizaProgramacionRuta(request, response);
        } else if (requestURI.endsWith("/activaProgramacionRuta")) {
            url = activaProgramacionRuta(request, response);
        } else if (requestURI.endsWith("/eliminaProgramacionRuta")) {
            url = eliminaProgramacionRuta(request, response);
        } else if (requestURI.endsWith("/inactivaProgramacionRuta")) {
            url = inactivaProgramacionRuta(request, response);
        } else if (requestURI.endsWith("/verRodamiento")) {
            verRodamiento(request, response);
        } else if (requestURI.endsWith("/estadoDespacho")) {
            url = estadoDespacho(request, response);
        } else if (requestURI.endsWith("/iniciarDespacho")) {
            url = iniciarDespacho(request, response);
        } else if (requestURI.endsWith("/tiempoControlDespacho")) {
            guardarTiempoControl(request, response);
        } else if (requestURI.endsWith("/verificarTipoDia")) {
            tipoDiaPermitido(request, response);
        } else if (requestURI.endsWith("/consultarPlanillaMovil")) {
            consultarPlanillaMovil(request, response);
        } else if (requestURI.endsWith("/sustituirMovil")) {
            url = sustituirMovil(request, response);
        } else if (requestURI.endsWith("/consultaTipoDia")) {
            consultaTipoDia(request, response);
        } else if (requestURI.endsWith("/adicionarDiaFestivo")) {
            url = adicionaDiaFestivo(request, response);
        } else if (requestURI.endsWith("/eliminarDiaFestivo")) {
            url = eliminaDiaFestivo(request, response);
        }
        
        if (url != "") {
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }
    
    // Extrae, valida y asigna los parametros relacionados a la configuracion 
    // de un despacho enviados en una solicitud en variable objeto Despacho.
    public Despacho procesarDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
            String ruta                 = request.getParameter("sruta");
            String idGrupoMoviles       = request.getParameter("sgrupomovil");
            String tipoDia              = request.getParameter("stipodia");
            String shoraInicio          = request.getParameter("horaInicio");
            String shoraFin             = request.getParameter("horaFin");
            String intervaloValle       = request.getParameter("intervaloValle");
            String intervaloPico        = request.getParameter("intervaloPico");
            String listaHoraPico        = request.getParameter("listaHoraPico");
            String idPuntosRuta         = request.getParameter("idPuntosRuta");
            String puntosRuta           = request.getParameter("puntosRuta");
            String tiempoReferencia     = request.getParameter("tiempoReferencia");
            String tiempoHolgura        = request.getParameter("tiempoHolgura");
            String tiempollegadaValle   = request.getParameter("tiempoLlegadaValle");
            String tiempollegadaPico    = request.getParameter("tiempoLlegadaPico");
            String tiempoSalida         = request.getParameter("tiempoSalida");
            String horasTrabajo         = request.getParameter("horasTrabajo");
            String limiteConservacion   = request.getParameter("limiteConservacion");
            String grupoMoviles         = request.getParameter("grupoMoviles");
            String rotarVehiculo        = request.getParameter("vrotarVehiculo");            
            String programarRuta        = request.getParameter("vprogramarRuta");
            String nombreDespacho       = request.getParameter("nombreDespacho");
            String tipoEvento           = request.getParameter("tipoEvento");
            String tipoProgRuta         = request.getParameter("stipopg");
            
            // Informacion punto retorno
            String puntoRetorno        = request.getParameter("spuntoretorno");
            String shoraInicioRetorno  = request.getParameter("horaInicio_rt");
            String intervaloRetorno    = request.getParameter("intervaloDespacho_rt");
            String tiempoSalidaRetorno = request.getParameter("tiempoSalida_rt");
            String tiempoAjusteRetorno = request.getParameter("tiempoAjuste_rt");
            String grupoMovilesRetorno = request.getParameter("grupoMoviles_rt");
            String cantidadMovilesRetorno = request.getParameter("cantidadMoviles_rt");
            
            Despacho dph = new Despacho();
            dph.setIdRuta(Restriction.getNumber(ruta));
            dph.setIdGrupoMoviles(Restriction.getNumber(idGrupoMoviles));
            dph.setTipoDia(tipoDia);
            dph.setHoraInicio(string2time(shoraInicio));
            dph.setHoraFin(string2time(shoraFin));
            dph.setIntervaloValle(Restriction.getNumber(intervaloValle));
            dph.setIntervaloPico(Restriction.getNumber(intervaloPico));
            dph.setTiempoSalida(Restriction.getNumber(tiempoSalida));
            dph.setHorasTrabajo(Restriction.getNumber(horasTrabajo));
            dph.setLimiteConservacion(Restriction.getNumber(limiteConservacion));
            dph.setHoraPico(listaHoraPico);
            dph.setIdPuntosRuta(idPuntosRuta);
            dph.setPuntosRuta(puntosRuta);
            dph.setTiempoReferencia(tiempoReferencia);
            dph.setTiempoHolgura(tiempoHolgura);
            dph.setTiempoLlegadaValle(tiempollegadaValle);
            dph.setTiempoLlegadaPico(tiempollegadaPico);
            dph.setGrupoMoviles(grupoMoviles);
            dph.setRotarVehiculo(Restriction.getNumber(rotarVehiculo));
            dph.setProgramarRuta(Restriction.getNumber(programarRuta));
            dph.setNombreDespacho(nombreDespacho);
                        
            // Se establecen valores con respecto si hay programacion-ruta o no
            if (dph.getProgramarRuta() == 1) {
                dph.setIdGrupoMoviles(1000);                
                dph.setGrupoMoviles(DespachoBD.NODATA);
                dph.setGrupoMovilesRetorno(DespachoBD.NODATA);
                dph.setTipoProgramacionRuta(tipoProgRuta);
                dph.setCantidadMovilesRetorno(Restriction.getNumber(cantidadMovilesRetorno));
            } else {       
                if (grupoMoviles.compareTo(DespachoBD.NODATA) == 0 &&
                    grupoMovilesRetorno.compareTo(DespachoBD.NODATA) == 0) {
                    //System.out.println("Sin moviles...");
                    dph.setIdGrupoMoviles(1000);
                }
                dph.setProgramarRuta(0);
                dph.setTipoProgramacionRuta("NA");                            
            }
            
            // Se establecen valores con respecto si hay punto-retorno o no
            if (puntoRetorno != null && puntoRetorno != "") {
                dph.setPuntoRetorno(puntoRetorno);
                dph.setHoraInicioRetorno(string2time(shoraInicioRetorno));
                dph.setIntervaloRetorno(Restriction.getNumber(intervaloRetorno));
                dph.setTiempoSalidaRetorno(Restriction.getNumber(tiempoSalidaRetorno));
                dph.setTiempoAjusteRetorno(Restriction.getNumber(tiempoAjusteRetorno));
                if (dph.getProgramarRuta() == 1) { grupoMovilesRetorno = DespachoBD.NODATA; }
                dph.setGrupoMovilesRetorno(grupoMovilesRetorno);
            } else {
                dph.setPuntoRetorno(null);
                dph.setHoraInicioRetorno(null);
                dph.setIntervaloRetorno(0);
                dph.setTiempoSalidaRetorno(0);
                dph.setTiempoAjusteRetorno(0);
                dph.setGrupoMovilesRetorno(DespachoBD.NODATA);
            }        
            
            // Se valida campos criticos
            int n_idpuntos      = StringUtils.countMatches(dph.getIdPuntosRuta(), ",");
            int n_puntos        = StringUtils.countMatches(dph.getPuntosRuta(), ",");
            int n_treferencia   = StringUtils.countMatches(dph.getTiempoReferencia(), ",");
            int n_tholgura      = StringUtils.countMatches(dph.getTiempoHolgura(), ",");
            int n_tvalle        = StringUtils.countMatches(dph.getTiempoLlegadaValle(), ",");
            int n_tpico         = StringUtils.countMatches(dph.getTiempoLlegadaPico(), ",");
            int n_registros     = n_idpuntos;            
            String listaGrupoMoviles = dph.getGrupoMoviles();
            boolean grupoMovilValido = (listaGrupoMoviles.compareTo(DespachoBD.NODATA) == 0 || 
                                        Restriction.isAlphaNumericListSeparateByComma(listaGrupoMoviles));            
            String listaGrupoMovilesRetorno = dph.getGrupoMovilesRetorno();
            boolean grupoMovilesRetornoValidado = (listaGrupoMovilesRetorno.compareTo(DespachoBD.NODATA) == 0 ||
                                        Restriction.isAlphaNumericListSeparateByComma(listaGrupoMovilesRetorno));
                        
            if (n_registros >= 1 &&
                n_registros == n_puntos &&
                n_registros == n_treferencia &&
                n_registros == n_tholgura &&
                n_registros == n_tvalle &&
                n_registros == n_tpico &&
                Restriction.isNumericListSeparateByComma(dph.getIdPuntosRuta()) &&
                Restriction.isAlphaNumericListSeparateByComma(dph.getPuntosRuta()) &&
                Restriction.isNumericListSeparateByComma(dph.getTiempoReferencia()) &&
                Restriction.isNumericListSeparateByComma(dph.getTiempoHolgura()) &&
                Restriction.isNumericListSeparateByComma(dph.getTiempoLlegadaValle()) &&
                Restriction.isNumericListSeparateByComma(dph.getTiempoLlegadaPico()) &&
                grupoMovilValido &&
                grupoMovilesRetornoValidado) {
                return dph;
            } else {                
                request.setAttribute("msg", "* Imposible almacenar la configuraci&oacute;n. Verifique los campos especificados.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-danger");
                return null;
            }
    }
    
    // Convierte cadena de texto con formato de tiempo
    // en variable de tiempo.
    public Time string2time(String st) {
        
        try {            
            Time t = new Time(hfmt.parse(st).getTime());
            return t;
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }
    
    // Extrae descripcion textual de dia segun sigla en (tipo-dia) especificado.
    public String descripcionTipoDia(String tipodia) {
        tipodia = tipodia.toUpperCase();
        String rs = "";
        if (tipodia.compareTo("LD") == 0) rs = "( Lunes - Domingo )";
        if (tipodia.compareTo("LS") == 0) rs = "( Lunes - S&aacute;bado )";    
        if (tipodia.compareTo("LV") == 0) rs = "( Lunes - Viernes )";
        if (tipodia.compareTo("D") == 0)  rs = "( Domingo )";
        if (tipodia.compareTo("S") == 0)  rs = "( S&aacute;bado )";
        if (tipodia.compareTo("F") == 0)  rs = "( Festivo )";
        return rs;
    }
    
    // Comprueba si tipo-dia enviado en solicitud es permitido,
    // segun valores almacenados y relacionados con una ruta.
    public void tipoDiaPermitido(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String sidRuta = request.getParameter("idRuta_alm");
        String tipoDia_alm = request.getParameter("tipoDia_alm");
        String tipoDia_sel = request.getParameter("tipoDia_sel");
        int idRuta = Restriction.getNumber(sidRuta);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        tipoDia_alm = tipoDia_alm.toUpperCase();
        tipoDia_sel = tipoDia_sel.toUpperCase();
        
        if (tipoDia_alm.compareTo(tipoDia_sel) == 0 &&
            tipoDia_alm != "") {
        
            out.println("1");
            
        } else {            
            
            Despacho dph = new Despacho();
            dph.setIdRuta(idRuta);
            dph.setTipoDia(tipoDia_sel);

            String rs = (DespachoUtil.tipoDiaPermitido(dph, tipoDia_alm)) ? "1" : "0";
            out.println(rs);
        } 
    }
    
    // Procesa solicitud de creacion de configuracion despacho, 
    // en la que se delega extraccion, comprobacion y almacenamiento de valores.
    public String crearConfiguracionDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        Despacho dph = procesarDespacho(request, response);
        if (dph == null) return "/app/despacho/nuevoDespacho.jsp";
        
        // Se verifica si tipo-dia es permitido
        if (!DespachoUtil.tipoDiaPermitido(dph, null)) {
            
            String tipo_dia = descripcionTipoDia(dph.getTipoDia());
            request.setAttribute("msg", "* Tipo de d&iacute;a " + tipo_dia + " no permitido. Verifique los tipos existentes.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            return "/app/despacho/nuevoDespacho.jsp";
        }
            
        // Se almacena despacho, solo si no existe
        if (!DespachoBD.exist(dph)) {
            if (DespachoBD.insert(dph)) {

                HttpSession session = request.getSession();
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);

                request.setAttribute("msg", "* Configuraci&oacute;n de despacho almacenada correctamente.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
            } else {
                request.setAttribute("msg", "* Configuraci&oacute;n de despacho no almacenada.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-danger");
            }
        } else {
            request.setAttribute("msg", "* Configuraci&oacute;n de despacho ya existe.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
                            
        return "/app/despacho/nuevoDespacho.jsp";
    }
    
    // Consulta configuracion de despacho especifico para hacer
    // impresion de sus valores, tras solicitud de ver configuracion despacho.
    public String verConfiguracionDespacho(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String idDespacho = request.getParameter("idDespacho");        
        int id = Restriction.getNumber(idDespacho);
        
        Despacho dph = DespachoBD.select(id);
        ProgramacionRuta pgr = null;
        
        // Se ignora identificador del punto
        if (dph != null) {
            pgr = ProgramacionRutaBD.select(dph.getIdRuta());
            String punto_retorno = dph.getPuntoRetorno();
            if (punto_retorno != null && punto_retorno.indexOf(",") >= 0) {
                punto_retorno = punto_retorno.split(",")[1];
                dph.setPuntoRetorno(punto_retorno);
            }
        }
        
        request.setAttribute("despacho", dph);
        request.setAttribute("pgr", pgr);

        return "/app/despacho/verConfiguracionDespacho.jsp";
    }
    
    // Consulta configuracion de despacho especifico para
    // dar inicio al intercambio de grupo-moviles.
    public String pre_intercambiarDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        String idDespacho = request.getParameter("idDespacho");
        int id = Restriction.getNumber(idDespacho);
        
        Despacho dph = DespachoBD.select(id);
        request.setAttribute("despacho", dph);
        
        return "/app/despacho/intercambioDespacho.jsp";
    }
    
    // Procesa solicitud de intercambio de grupo-moviles entre
    // dos despachos especificados.
    public String intercambiarDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        String idDespacho  = request.getParameter("idDespacho");
        String idDespacho2 = request.getParameter("sidDespacho");
        
        int id  = Restriction.getNumber(idDespacho);
        int id2 = Restriction.getNumber(idDespacho2);
        Despacho dph  = DespachoBD.select(id);
        Despacho dph2 = DespachoBD.select(id2);
        request.setAttribute("despacho", dph);
        
        String inter = "[ " + 
            dph.getNombreRuta()  + " - " + dph.getNombreDespacho()  + " / " +
            dph2.getNombreRuta() + " - " + dph2.getNombreDespacho()
        + " ]";
        
        // Se almacena intercambio
        if (DespachoBD.rotarDespacho(id, id2)) {
            request.setAttribute("msg", "* Intercambio de veh&iacute;culos entre despachos " + inter + " se realiz&oacute; correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
        } else {
            request.setAttribute("msg", "* Intercambio de veh&iacute;culos entre despachos no realizado.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");            
        }
        
        return "/app/despacho/intercambioDespacho.jsp";
    }
    
    // Consulta configuracion de despacho especifico para dar inicio
    // a edicion de configuracion.
    public String pre_editarConfiguracionDespacho(HttpServletRequest request,
            HttpServletResponse response) {       
        
        String idDespacho = request.getParameter("idDespacho");        
        int id = Restriction.getNumber(idDespacho);
              
        Despacho dph = DespachoBD.select(id);        
        dph.setListaGrupoMoviles_all(
                dph.getIdGrupoMoviles(), 
                dph.getListaGrupoMoviles(), 
                dph.getListaGrupoMovilesRetorno());        
        
        request.setAttribute("despacho", dph);
        
        return "/app/despacho/editaConfiguracionDespacho.jsp";
    }
    
    // Procesa solicitud de edicion de configuracion despacho,
    // en la que se delega extraccion, comprobacion y almacenamiento de valores.
    public String editarConfiguracionDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidDespacho = request.getParameter("idDespacho");        
        String tipoDia_alm = request.getParameter("tipoDia_alm");        
        
        Despacho dph = procesarDespacho(request, response);
        if (dph == null) return "/app/despacho/editaConfiguracionDespacho.jsp";        
        dph.setId(Restriction.getNumber(sidDespacho));
        
        String tipoDia_nvo = dph.getTipoDia();
        
        // Se verifica si tipo-dia es permitido
        if (tipoDia_alm.compareTo(tipoDia_nvo) != 0 && !DespachoUtil.tipoDiaPermitido(dph, tipoDia_alm)) {
            
            String tipo_dia = descripcionTipoDia(dph.getTipoDia());
            request.setAttribute("msg", "* Tipo de d&iacute;a " + tipo_dia + " no permitido. Verifique los tipos existentes.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            return "/app/despacho/nuevoDespacho.jsp";
        }
        
        // Se actualiza valores de configuracion despacho
        if (DespachoBD.update(dph)) {
            
            Despacho dph_act = DespachoBD.select(Restriction.getNumber(sidDespacho));
            dph_act.setListaGrupoMoviles_all(
                    dph_act.getIdGrupoMoviles(), 
                    dph_act.getListaGrupoMoviles(),
                    dph_act.getListaGrupoMovilesRetorno());
            
            request.setAttribute("despacho", dph_act);
            request.setAttribute("msg", "* Configuraci&oacute;n de despacho actualizado correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            
            SelectBD select = new SelectBD(request);
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            
        } else { 
            request.setAttribute("msg", "* Configuraci&oacute;n de despacho no actualizado.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        
        return "/app/despacho/editaConfiguracionDespacho.jsp";
    }
    
    // Procesa solicitud de eliminacion de configuracion despacho.
    public String eliminarDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        String idDespacho = request.getParameter("idDespacho");
        int id = Restriction.getNumber(idDespacho);
        
        // Se elimina configuracion despacho
        if (DespachoBD.delete(id)) {
            
            HttpSession session = request.getSession();
            SelectBD select = new SelectBD(request);
            session.setAttribute("select", select);
            
            request.setAttribute("msg", "* Despacho eliminado correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
        } else {
            request.setAttribute("msg", "* Despacho no eliminado.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/despacho/consultaDespacho.jsp";
    }
    
    // Procesa solicitud para imprimir planilla relacionada
    // a configuracion de despacho.
    // La planilla se genera con respecto a valores especificados
    // en la configuracion del despacho.
    public String verDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        String idDespacho = request.getParameter("idDespacho");
        int id = Restriction.getNumber(idDespacho);
        
        Date fecha_inicial = new Date();
        Date fecha_final   = TimeUtil.sumarDias(fecha_inicial, 6);
        
        Despacho dph = DespachoBD.select(id);
        
        DespachoUtil dphu = new DespachoUtil(dph);
        dphu.generarDespacho(fecha_inicial, fecha_final);        
        ArrayList<PlanillaDespacho> lst_pd = dphu.getListaPlanillaDespacho();
        
        request.setAttribute("despacho", dph);
        request.setAttribute("lst_planillaDespacho", lst_pd);
        
        return "/app/despacho/verPlanillaDespacho.jsp";
    }
    
    // Consulta tipos-dia relacionados a configuracion(es) de despacho
    // de una ruta especifica.
    public void consultaTipoDia(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        String sruta_dph = request.getParameter("sruta_dph"); // idruta % nombre_ruta        
        int id_ruta      = Restriction.getNumber(sruta_dph.split("%")[0]);        
        String nom_ruta  = sruta_dph.split("%")[1];
        
        ArrayList<String> lst_tipodia = DespachoBD.select_tipos_dias(id_ruta);
        String ftipodia = "";
        String sep = "\"padding-left: 6px;\"";
        
        for (int i = 0; i < lst_tipodia.size(); i++) {
            String tipodia = lst_tipodia.get(i);            
            tipodia  = TipoDia(tipodia);
            ftipodia += (ftipodia == "") ? tipodia : ", " + tipodia;
        }
        if (ftipodia == "") ftipodia = "NA";
        
        String tbl = "<table>"
                    + "<thead>"
                    +   "<tr>"
                    +       "<th>Ruta</th>"
                    +       "<th style="+ sep +">Dias a aplicar</th>"
                    +   "</tr>"
                    + "</thead>"
                    + "<tbody>"
                    + " <tr>"
                    + "     <td>"+ nom_ruta +"</td>"
                    + "     <td style="+ sep +">"+ ftipodia +"</td>"
                    + " </tr>"
                    + "<tbody></table>";
        
        out.println(tbl);
    }
    
    // Extra descripcion relacionada con sigla de tipo-dia.
    public String TipoDia(String tipodia) {
        String stipodia = (tipodia.equalsIgnoreCase("lv")) ? "Lunes - Viernes"
                        : (tipodia.equalsIgnoreCase("ls")) ? "Lunes - S&aacute;bado"
                        : (tipodia.equalsIgnoreCase("ld")) ? "Lunes - Domingo"
                        : (tipodia.equalsIgnoreCase("s")) ? "S&aacute;bado"
                        : (tipodia.equalsIgnoreCase("d")) ? "Domingo"
                        : (tipodia.equalsIgnoreCase("f")) ? "Festivo"
                        : "";
        return stipodia;
    }
    
    // Construye mensaje de error para registros 
    // de planilla despacho planificado cruzados.
    public String msg_error_generacion_planilla_planificado(PlanillaDespachoMin pll_cruzada) {
        
        String ruta      = pll_cruzada.getNombreRuta();
        String nruta     = (ruta.toLowerCase().contains("ruta")) ? ruta : "Ruta " + ruta;
        String fecha     = pll_cruzada.getFechaStr();
        int id_intervalo = pll_cruzada.getIdIntervalo();
        int numero_pll   = pll_cruzada.getNumeroPlanilla();
        String pll_info  = "";
        
        int tipo_generacion = pll_cruzada.getTipoGeneracion();
        if (tipo_generacion == Constant.DPH_MANUAL) {
            pll_info = "<b>[ " + nruta + "  " + fecha + "  Planilla N° " + numero_pll + " - Planilla a pedido ]</b>";
        } else {       
            pll_info = "<b>[ " + nruta + "  " + fecha + "  Intv. Planilla N° " + id_intervalo + " ]</b>";
        }
        
        String msg = "* Horario de Planilla despacho " + pll_info + " se cruza con generaci&oacute;n actual.";
        return msg;
    }
    
    // Construye mensaje de error para registros 
    // de planilla despacho a pedido cruzados.
    public String msg_error_generacion_planilla_pedido(PlanillaDespachoMin pll_cruzada, String movil, String hora_salida) {
        
        String ruta      = pll_cruzada.getNombreRuta();
        String nruta     = (ruta.toLowerCase().contains("ruta")) ? ruta : "Ruta " + ruta;
        String fecha     = pll_cruzada.getFechaStr();
        int id_intervalo = pll_cruzada.getIdIntervalo();
        int numero_pll   = pll_cruzada.getNumeroPlanilla();        

        String msg = "* Hora de salida <b>" + hora_salida + "</b> para veh&iacute;culo <b>" +
                        movil + "</b> contenida en despacho <b>[ " +
                        nruta + "  " + fecha;
                        
        int tipo_generacion = pll_cruzada.getTipoGeneracion();
        if (tipo_generacion == Constant.DPH_PLANIFICADO) {
            msg += "  Intv. Planilla N° " + id_intervalo + " - Planilla planificada ]</b>";
        } else {
            msg += "  Planilla N° " + numero_pll + " ]</b>";
        }

        return msg;
    }
    
    // Procesa solicitud para generacion de planilla despacho planificado
    // segun ruta especificada o la totalidad de ellas. 
    // Se comprueba que registros planilla no existan en el rango de fechas y hora
    // especificado.
    public String generarPlanilla(HttpServletRequest request,
            HttpServletResponse response) {
                
        String sruta_despacho = request.getParameter("sruta_despacho"); // id_ruta % nombre_ruta
        String sfecha_inicial = request.getParameter("fechaInicial");
        String sfecha_final   = request.getParameter("fechaFinal");
        
        if (!TimeUtil.rangoFechaCorrecto(sfecha_inicial, sfecha_final, 30)) { // 31 dias
            request.setAttribute("msg", "* Rango de fecha mal especificado. Diferencia m&aacute;xima hasta 31 d&iacute;as.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            return "/app/despacho/generaPlanillaDespacho.jsp";
        }
        
        Date fecha_inicial, fecha_final; 
        fecha_inicial = fecha_final = null;
        
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            fecha_inicial = fmt.parse(sfecha_inicial);
            fecha_final   = fmt.parse(sfecha_final);
        } catch (ParseException e) {
            System.err.println(e);            
            request.setAttribute("msg", "* Error al aplicar formato a las fechas.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            return "/app/despacho/generaPlanillaDespacho.jsp";
        }
        
        boolean existe_planilla = true;
        DespachoUtil2 dphu = null;                     
                
        String nruta_despacho = "", nruta = "";
        if (sruta_despacho.compareTo("all") == 0) {
            nruta = "<b>Todas las rutas</b>";
        } else {
            String array_ruta_despacho[] = sruta_despacho.split("%");
            if (array_ruta_despacho.length == 2) {
                sruta_despacho = array_ruta_despacho[0]; // id_ruta
                nruta_despacho = array_ruta_despacho[1]; // nombre_ruta

                nruta = (nruta_despacho.toLowerCase().contains("ruta"))
                        ? "<b>" + nruta_despacho + "</b>"
                        : "ruta <b>" + nruta_despacho + "</b>";
            } else {
                nruta = "<b>Ruta NA</b>";
            }
        }
        
        if (sruta_despacho.compareTo("all") == 0) {            
            
            // Generacion de planilla despacho para todas las rutas configuradas                                    
            dphu = new DespachoUtil2();
            
            // Verifica planilla despacho que incluya hora inicio de configuracion de
            // despacho actual (En este caso de todas las configuraciones de despacho)
            // Si es asi, se impide generacion de planilla en intervalo de fecha y hora.            
            PlanillaDespachoMin pll_cruzada = planilla_cruzada_en_hora_planificada(-1, sfecha_inicial, sfecha_final);
            if (pll_cruzada != null) {
                String msg = msg_error_generacion_planilla_planificado(pll_cruzada);
                request.setAttribute("msg", msg);
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
                return "/app/despacho/generaPlanillaDespacho.jsp";
            } else {
                existe_planilla = false;
            }
            
        } else {
            
            // Generacion de planilla despacho por ruta seleccionada                    
            int id_ruta  = Restriction.getNumber(sruta_despacho);
            
            if (id_ruta > 0) {
                Despacho dph = new Despacho();
                dph.setIdRuta(id_ruta);

                dphu = new DespachoUtil2(dph);
                
                // Verifica planilla despacho que incluya hora inicio de configuracion de
                // despacho actual (En este caso para configuraciones despacho de ruta especificada)
                // Si es asi, se impide generacion de planilla en intervalo de fecha y hora.                
                PlanillaDespachoMin pll_cruzada = planilla_cruzada_en_hora_planificada(id_ruta, sfecha_inicial, sfecha_final);
                if (pll_cruzada != null) {
                    String msg = msg_error_generacion_planilla_planificado(pll_cruzada);
                    request.setAttribute("msg", msg);
                    request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
                    return "/app/despacho/generaPlanillaDespacho.jsp";
                } else {
                    existe_planilla = false;
                }
                
            } else {
                request.setAttribute("msg", "* Ruta no relacionada.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
                return "/app/despacho/generaPlanillaDespacho.jsp";
            }               
        } 
        
        if (!existe_planilla) {
                        
            // Se genera planilla(s) despacho en rango de fechas especificado
            dphu.generarDespacho(fecha_inicial, fecha_final);
            ArrayList<PlanillaDespacho> lst = dphu.getListaPlanillaDespacho();
            String rutas = dphu.getRutas();            
    
            // Se almacena planilla(s) de despacho generada(s)
            if (DespachoBD.insert_planilla(fecha_inicial, fecha_final, lst, rutas, Constant.DPH_PLANIFICADO)) {
                String msg = "* Planilla despacho para " + nruta + " entre " + sfecha_inicial + " y " + sfecha_final + 
                             " almacenada correctamente.";
                request.setAttribute("msg", msg);
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
                
                SelectBD select = new SelectBD(request);
                HttpSession session = request.getSession();
                session.setAttribute("select", select);                
            } else {
                request.setAttribute("msg", "* Planilla despacho para " + nruta + " no almacenada.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            }            
        } 
        
        return "/app/despacho/generaPlanillaDespacho.jsp";
    }   
    
    // Procesa solicitud para creacion de planilla despacho a pedido
    // para un movil, en una ruta y hora de salida especifico.
    public String generarPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sruta_despacho   = request.getParameter("sruta_despacho");
        String smovil           = request.getParameter("smovil");
        String hora_salida      = request.getParameter("hora_salida") + ":00";
        String vipr             = request.getParameter("vinicia_punto_retorno");
        Date fecha_actual       = new Date();
        String sfecha_actual    = ffmt.format(fecha_actual);        
        Time hora_salida_t      = string2time(hora_salida);                       
        boolean inicia_punto_retorno = (vipr.compareTo("1") == 0) ? true : false;
        boolean existe_planilla = true;        
        
        String nruta_despacho   = "";
        String array_ruta[]     = sruta_despacho.split("%");
        sruta_despacho          = array_ruta[0]; // id_ruta
        nruta_despacho          = array_ruta[1]; // nombre_ruta
        
        String nruta = (nruta_despacho.toLowerCase().contains("ruta"))
                        ? "<b>" + nruta_despacho + "</b>"
                        : "ruta <b>" + nruta_despacho + "</b>";
        
        int id_ruta = Restriction.getNumber(sruta_despacho);
        if (id_ruta > 0) {
                        
            // Se verifica que no exista alguna otra planilla despacho que contenga
            // la fecha y hora de salida de planilla a crearse. De ser asi, se
            // notifica y detiene la creacion de la planilla.
            PlanillaDespachoMin pll_cruzada = planilla_cruzada_en_hora_manual(smovil, sfecha_actual, hora_salida);
            if (pll_cruzada != null) {                
                String msg = msg_error_generacion_planilla_pedido(pll_cruzada, smovil, hora_salida);
                request.setAttribute("msg", msg);
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
                return "/app/despacho/generaPlanillaAPedido.jsp";
            } else {
                existe_planilla = false;
            }
            
            // Se comprueba que movil a despacharse (para el que se creara planilla despacho a pedido)
            // este libre y no este cumpliendo otro despacho.
            if (DespachoBD.movil_en_despacho(smovil, sfecha_actual)) {
                request.setAttribute("msg", "* Veh&iacute;culo <b>" + smovil + "</b> en ruta. En espera su llegada a base.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
                return "/app/despacho/generaPlanillaAPedido.jsp";
            }
        }
        
        if (!existe_planilla) {            
            Despacho dph = new Despacho();
            dph.setIdRuta(id_ruta);
            dph.setHoraInicio(hora_salida_t);
            dph.setGrupoMoviles(smovil);            
            dph.setIniciaEnPuntoRetorno(inicia_punto_retorno);
            
            DespachoUtil dphu = new DespachoUtil();
            dphu.generarDespachoManual(dph, fecha_actual);
            ArrayList<PlanillaDespacho> lst = dphu.getListaPlanillaDespacho();
            String ruta = "" + dph.getIdRuta();
            
            String msg = "* Planilla despacho para " + nruta + " con veh&iacute;culo <b>" + smovil + "</b>";
            
            if (DespachoBD.insert_planilla(fecha_actual, fecha_actual, lst, ruta, Constant.DPH_MANUAL)) {                
                request.setAttribute("msg", msg + " almacenada correctamente.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
                
                SelectBD select = new SelectBD(true);
                HttpSession session = request.getSession();
                session.setAttribute("select", select);                
            } else {
                request.setAttribute("msg", msg + " no almacenada.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            }
        }
        
        return "/app/despacho/generaPlanillaAPedido.jsp";
    }
    
    // Consulta planilla despacho a pedido especifico que sera editado.
    public String pre_editarPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidIntervalo = request.getParameter("idIntervalo");
        int idIntervalo     = Restriction.getNumber(sidIntervalo);
        
        PlanillaDespachoMin pd = DespachoManualBD.obtener_planilla(idIntervalo);
        boolean ipr            = DespachoManualBD.inicio_desde_punto_retorno(idIntervalo);
        pd.setIniciaPuntoRetorno(ipr);
        
        request.setAttribute("planilla", pd);
        
        return "/app/despacho/editaPlanillaAPedido.jsp";
    }
    
    // Procesa solicitud de edicion planilla despacho a pedido,
    // comprobando posibilidad de reemplazo por un nuevo movil y
    // posteriormente actualizando los registros.
    // (La edicion de planilla despacho a pedido se limita a reemplazar
    // el movil asginado por uno nuevo).
    public String editarPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidIntervalo     = request.getParameter("idIntervalo");
        String smovil           = request.getParameter("smovil");
        String nombre_ruta      = request.getParameter("nombreRuta");
        String numero_planilla  = request.getParameter("numeroPlanilla");
        String numero_vuelta    = request.getParameter("numeroVuelta");
        String fecha            = request.getParameter("fecha");
        String hora_salida      = request.getParameter("horaSalida");
        int idIntervalo         = Restriction.getNumber(sidIntervalo);
        
        // Movil por cual reemplazar
        String ssmovil = request.getParameter("ssmovil");
        
        boolean existe_planilla = true;        
        
        // Verifica que no exista planilla que involucre al nuevo movil
        // en la fecha y hora salida establecido.
        PlanillaDespachoMin pll_cruzada = planilla_cruzada_en_hora_manual(ssmovil, fecha, hora_salida);
        if (pll_cruzada != null) {
            
            String ruta               = pll_cruzada.getNombreRuta();
            String nombre_ruta_cruce  = (ruta.toLowerCase().contains("ruta")) ? ruta : "Ruta " + ruta;
            String fecha_cruce        = pll_cruzada.getFechaStr();
            int numero_planilla_cruce = pll_cruzada.getNumeroPlanilla();
                
            String msg = "* Hora de salida <b>" + hora_salida + "</b> para veh&iacute;culo <b>" +
                            ssmovil            + "</b> contenida en despacho <b>[ " +
                            nombre_ruta_cruce  + "  " +                           
                            fecha_cruce        + "  " +
                            "Planilla N° "     + numero_planilla_cruce + " ]</b>";
                
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            
            PlanillaDespachoMin pd = DespachoManualBD.obtener_planilla(idIntervalo);            
            request.setAttribute("planilla", pd);
            
            return "/app/despacho/editaPlanillaAPedido.jsp";
        } else {
            existe_planilla = false;
        }
        
        nombre_ruta = (nombre_ruta.toLowerCase().contains("ruta"))
                     ? nombre_ruta : "Ruta " + nombre_ruta;
        
        String msg = "* <b>Vuelta N° " + numero_vuelta + "</b> de <b>Planilla N° " + numero_planilla 
                   + " " + nombre_ruta + " " + fecha + " " + hora_salida + "</b>";
        
        if (!existe_planilla) {
            if (DespachoManualBD.sustituir_vehiculo(idIntervalo, ssmovil)) {
                request.setAttribute("msg", msg + " sustituida por veh&iacute;culo <b>" + ssmovil + "</b>.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");               
                
            } else {
                request.setAttribute("msg", msg + " no sustituida.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            }
        }
                
        PlanillaDespachoMin pd = DespachoManualBD.obtener_planilla(idIntervalo);
        request.setAttribute("planilla", pd);
        actualizarDatos(request, response);
        
        return "/app/despacho/editaPlanillaAPedido.jsp";
    }
    
    // Edita el rango de fechas (de los registros de planilla despacho planificado),
    // comprobandose antes si regitros no han sido controlados.
    public String editarFechasPlanilla(HttpServletRequest request,
            HttpServletResponse response) {
        
        String edt_id = request.getParameter("edt_id");
        String edt_fi = request.getParameter("edt_fechaInicial");
        String edt_ff = request.getParameter("edt_fechaFinal");
        
        int id = Restriction.getNumber(edt_id);
        Date dfi, dff; dfi = dff = null;
        
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            dfi = fmt.parse(edt_fi);
            dff = fmt.parse(edt_ff);
        } catch (ParseException e) {
            System.err.println(e);
            request.setAttribute("msg", "* Error al aplicar formato a las fechas.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            return "/app/despacho/generaPlanillaDespacho.jsp";
        }
        
        // Comprueba si planilla despacho en rango de fecha
        // especificado, no se encuentra controlada
        if (DespachoBD.es_intervalo_planilla_libre(id, dfi, dff)) {
            // Modifica rango de fecha de la planilla despacho
            if (DespachoBD.editar_intervalo_planilla(id, dfi, dff)) {
                // Cambia nombre de planilla (especificado con base al rango de fecha)
                DespachoBD.cambiar_nombre_planilla(id);
                
                HttpSession session = request.getSession();
                SelectBD select = new SelectBD(request);                
                session.setAttribute("select", select);
                
                request.setAttribute("msg", "* Fechas de planilla N° " + edt_id + " editadas correctamente.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
            } else {
                request.setAttribute("msg", "* Fechas de planilla N° " + edt_id + " no editadas.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            }
        } else {
            String msg = "* Planilla N° " +edt_id+ " ha sido procesada en fechas "
                        + edt_fi + " - " + edt_ff + " y no es posible editar.";
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/despacho/generaPlanillaDespacho.jsp";
    }
    
    // Procesa solicitud de eliminacion de registros de planilla
    // despacho planificado.
    // (Elimina registros de la base de datos)
    public String eliminarPlanilla(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidIntervalo = request.getParameter("idIntervalo");
        int idIntervalo = Restriction.getNumber(sidIntervalo);
        
        // Eliminacion de planilla si no ha sido procesada
        // (Sin uso en control de despacho)
        if (DespachoBD.es_planilla_libre(idIntervalo)) {
            if (DespachoBD.delete_planilla(idIntervalo)) {
                request.setAttribute("msg", "* Intv. de planilla despacho N° " + idIntervalo + " eliminado correctamente.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
                
                SelectBD select = new SelectBD(request);
                HttpSession session = request.getSession();
                session.setAttribute("select", select);
            } else {
                request.setAttribute("msg", "* Intv. de planilla despacho N° " + idIntervalo + " no eliminado.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            }
        } else {
            request.setAttribute("msg", "* Intv. de planilla despacho N° " + idIntervalo + " ha sido procesado y no puede eliminarse.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/despacho/generaPlanillaDespacho.jsp";
    }    
    
    // Procesa solicitud de eliminacion de registros de planilla
    // despacho a pedido.
    // (Elimina registros de la base de datos)
    public String eliminarPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidIntervalo   = request.getParameter("idIntervalo");
        String numeroPlanilla = request.getParameter("numeroPlanilla");
        String numeroVuelta   = request.getParameter("numeroVuelta");
        String smovil         = request.getParameter("smovil");
        int idIntervalo = Restriction.getNumber(sidIntervalo);
        
        String msg = "* <b>Vuelta N° " + numeroVuelta + "</b> en <b>Planilla despacho N° " + numeroPlanilla + "</b>"
                   + " de veh&iacute;culo <b>" + smovil + "</b>";
        
        // Eliminacion de planilla que no ha sido procesada
        // (Sin uso en control de despacho)
        if (DespachoBD.es_planilla_libre(idIntervalo)) {
            if (DespachoBD.delete_planilla(idIntervalo)) {
                request.setAttribute("msg", msg + " eliminada correctamente.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
                
                SelectBD select = new SelectBD(true);
                HttpSession session = request.getSession();
                session.setAttribute("select", select);
            } else {
                request.setAttribute("msg", msg + " no eliminada.");
                request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
            }
        } else {
            request.setAttribute("msg", msg + " ha sido procesada y no puede eliminarse.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/despacho/generaPlanillaAPedido.jsp";        
    }
    
    // Procesa solicitud de cancelacion de planilla despacho a pedido.
    // (Actualiza campo estado a 0 para inhabilitar el uso de registros).
    public String cancelarPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidIntervalo   = request.getParameter("idIntervalo");
        String numeroPlanilla = request.getParameter("numeroPlanilla");
        String numeroVuelta   = request.getParameter("numeroVuelta");
        String smovil         = request.getParameter("smovil");
        int idIntervalo = Restriction.getNumber(sidIntervalo);
        
        String msg = "* <b>Vuelta N° " + numeroVuelta + "</b> en <b>Planilla despacho N° " + numeroPlanilla + "</b>"
                   + " de veh&iacute;culo <b>" + smovil + "</b>";
        
        // Cancelacion de planilla        
        if (DespachoBD.cancel_planilla(idIntervalo)) {
            request.setAttribute("msg", msg + " cancelada correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");

            SelectBD select = new SelectBD(true);
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", msg + " no cancelada.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/despacho/generaPlanillaAPedido.jsp";        
    }
    
    // Procesa solicitud para almacenamiento de tiempo y tipo
    // de control de despacho.
    public void guardarTiempoControl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String tiempo_control = request.getParameter("tiempoControl");
        String tipo_control   = request.getParameter("tipoControl");
        
        String web_path = getServletContext().getRealPath("");        
        Entorno entornoRD = new Entorno(web_path);
        entornoRD.establecerPropiedad(Constant.RANGO_TIEMPO_CONTROL_DESPACHO, tiempo_control);
        entornoRD.establecerPropiedad(Constant.TIPO_CONTROL_DESPACHO, tipo_control);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        if (entornoRD.guardarEntorno()) {
            HttpSession session = request.getSession();
            session.setAttribute("entorno", entornoRD);            
            out.println("1");
        } else {
            out.println("0");
        }   
    }
    
    // Consulta estado de control-despacho segun
    // instanciacion y variable interna de clase DespachoControl.
    public String estadoDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        //        HttpSession session = request.getSession();
        //        DespachoControlUtil dphu = 
        //                (DespachoControlUtil) session.getAttribute("controlDespacho");
        
        ServletContext app = getServletConfig().getServletContext();
        DespachoControl dphu = (DespachoControl) app.getAttribute("controlDespacho");
        
        String estado;
        if (dphu == null) {
            estado = "0";
        } else if (dphu.pausado()) {
            estado = "0";
        } else {
            estado = "1";
        }               
        
        app.setAttribute("estadoDespacho", estado);
        
        return "/app/despacho/estadoDespacho.jsp";
    }
            
    // Procesa solicitud de inicio/pausa de control-despacho,
    // para el que establece variables de control, y controla
    // transicion segun su actual estado.
    public String iniciarDespacho(HttpServletRequest request,
            HttpServletResponse response) {
        
        ServletContext app     = getServletConfig().getServletContext();
        Entorno entornoRD      = (Entorno) request.getSession().getAttribute("entorno");
        String stiempo_control = entornoRD.obtenerPropiedad(Constant.RANGO_TIEMPO_CONTROL_DESPACHO);
        String stipo_control   = entornoRD.obtenerPropiedad(Constant.TIPO_CONTROL_DESPACHO);
        int tiempo_control     = Restriction.getNumber(stiempo_control);
        if (tiempo_control == -1) tiempo_control = 5;
        
        Usuario usr = (Usuario) request.getSession().getAttribute("login");
        Empresa emp = EmpresaBD.getById(usr.getIdempresa());
        
        DespachoControl dphu = (DespachoControl) app.getAttribute("controlDespacho");
        
        String estado;
        if (dphu == null) {
            dphu = new DespachoControl(tiempo_control);            
            dphu.establecerTipoControl(stipo_control);
            dphu.establecerEmpresa(emp);
            dphu.start();
            estado = "1";
        } else if (dphu.pausado()) {            
            dphu.establecerTiempoComprobacion(tiempo_control);
            dphu.establecerTipoControl(stipo_control);
            dphu.establecerEmpresa(emp);
            dphu.reanudar();
            estado = "1";
        } else {
            dphu.pausar();
            estado = "0";
        }
        
        app.setAttribute("controlDespacho", dphu);
        app.setAttribute("estadoDespacho", estado);

        return "/app/despacho/estadoDespacho.jsp";
    }
    
    /*
    ============================================================================
    === Planilla con formato de impresion (division por vuelta)
    ============================================================================
    */
    
    // Se asignan parametros de empresa en estructura a partir
    // de datos enviados en solicitud.
    public Map<String, String> parametrosPlanilla(HttpServletRequest request) {
        
        Map<String, String> hmap = new HashMap<String, String>();
        
        HttpSession session   = request.getSession();
        Usuario user          = (Usuario) session.getAttribute("login");
        String nombre_empresa = (user != null) ? user.getNombreEmpresa() : "NA";
        String nit_empresa    = (user != null) ? user.getNitEmpresa() : "NA";
        
        // Se asignan parametros de empresa        
        hmap.put("nombreReporte", "planillaDespacho");
        hmap.put("nombreEmpresa", nombre_empresa);   
        hmap.put("nitEmpresa", nit_empresa);
        
        return hmap;
    }
    
    // Procesa solicitud para imprimir (despliegue en archivo Excel)
    // registros de planilla despacho planificada.
    public String imprimirPlanilla(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String sidIntervalo = request.getParameter("idIntervalo");
        int idIntervalo     = Restriction.getNumber(sidIntervalo);
                
        ArrayList<PlanillaDespacho> lst_pd = DespachoBD.select_planilla_sin_formato(idIntervalo);
        ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvuelta(lst_pd);
        String nombre_ruta = nombreRuta(lst_pll);       
        
        Map<String, String> hparametros = parametrosPlanilla(request);
        hparametros.put("tituloReporte", "Planilla despacho - " + nombre_ruta);
        hparametros.put("nombreArchivo", "planillaDespacho_" + nombre_ruta);
        
        // Se verifica si hubo seleccion de un vehiculo en fecha especifica,
        // unicamente vehiculo o todos los vehiculos
        String sfecha_veh  = request.getParameter("fecha_veh");
        String fecha, ninterno; fecha = ninterno = "";
        
        if (sfecha_veh != "") {
            
            if (sfecha_veh.indexOf(",") >= 0) {
                String fecha_veh[] = sfecha_veh.split(",");
                if (fecha_veh.length == 2) {
                    fecha    = fecha_veh[0];
                    ninterno = fecha_veh[1];
                    lst_pll = filtrarPlanilla(fecha, ninterno, lst_pll);                                
                }
            } else {
                ninterno = sfecha_veh;
                lst_pll = filtrarPlanilla(ninterno, lst_pll);
            }
            
            hparametros.put("tituloReporte", "Planilla despacho - Vehículo " + ninterno);
            hparametros.put("nombreArchivo", "planillaDespacho_Vehiculo_" + ninterno);
        }
        
        // Se inicia generacion de reporte planilla despacho planificado (Formato POS)
        return imprimirPlanilla(response, hparametros, lst_pll, Constant.DPH_PLANIFICADO);
    }        
    
    // Procesa solicitud para imprimir (despliegue en archivo Excel)
    // registros de planilla despacho manual.
    public String imprimirPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String sidIntervalo = request.getParameter("idIntervalo");
        String ninterno     = request.getParameter("numeroInterno");
        int idIntervalo     = Restriction.getNumber(sidIntervalo);
                
        ArrayList<PlanillaDespacho> lst_pd = DespachoBD.select_planilla_sin_formato(idIntervalo);
        ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvuelta(lst_pd);
        
        Map<String, String> hparametros = parametrosPlanilla(request);
        hparametros.put("tituloReporte", "Planilla a pedido - Veh. " + ninterno);
        hparametros.put("nombreArchivo", "planillaAPedidoVehiculo_" + ninterno);
        
        // Se inicia generacion de reporte planilla despacho a pedido (Formato POS)
        return imprimirPlanilla(response, hparametros, lst_pll, Constant.DPH_MANUAL);
    }
    
    // Consulta registros de planilla y crea reporte segun tipo
    // de despacho, en formato POS, que se almacena en archivo Excel.
    public String imprimirPlanilla(
            HttpServletResponse response,
            Map<String,String> hparametros,
            ArrayList<Planilla> data,
            int tipo_dph) throws IOException {
        
        // Reporte editable XLS            
        ReporteUtilExcel rue = new ReporteUtilExcel();
        MakeExcel rpte = null;
        
        if (tipo_dph == Constant.DPH_PLANIFICADO) {
            rpte = rue.crearPlanillaDespacho(hparametros, data);
        } else if (tipo_dph == Constant.DPH_MANUAL) {
            rpte = rue.crearPlanillaAPedido(hparametros, data);
        }
        
        String nombreArchivo = hparametros.get("nombreArchivo") + ".xls";

        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/ms-excel");            
        response.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo);

        HSSFWorkbook file = rpte.getExcelFile();
        file.write(response.getOutputStream());            
        response.flushBuffer();
        response.getOutputStream().close();

        return "/app/despacho/generaPlanillaDespacho.jsp";        
    }
    
    // Procesa consulta para visualizar planilla despacho a pedido
    // (vuelta de un movil en ruta especifica) en formato
    // web y enlace para impresion en formato POS.
    public void verPlanillaAPedido(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String sidIntervalo = request.getParameter("idIntervalo");
        int idIntervalo = Restriction.getNumber(sidIntervalo);
        
        ArrayList<PlanillaDespacho> lst_pd = DespachoBD.select_planilla_sin_formato(idIntervalo);
        ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvuelta(lst_pd);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String td  = "<td>",  tr  = "<tr>",
               td_ = "</td>", tr_ = "</tr>",
               tbl = "";
        
        String numeroInterno = "";
        for (int i = 0; i < lst_pll.size(); i++) {
            
            Planilla pll      = lst_pll.get(i);
            int numero_vuelta = pll.getNumeroVuelta();
            numeroInterno     = pll.getNumeroInterno();
            
            ArrayList<PlanillaDespacho> pd_detalle = pll.getDetalle();
            String tbl_enc    = tabla_encabezado(pll);
            String row_puntos = fila_puntos(pll);
            
            String cols = td + numero_vuelta + td_;
            for (int j = 0; j < pd_detalle.size(); j++) {
                PlanillaDespacho pd     = pd_detalle.get(j);
                String hora_planificada = hfmt.format(pd.getHoraPlanificada());
                // Si punto esta inhabilitado, no se visualiza hora
                int tipo_punto = pd.getTipoPunto();
                if (tipo_punto == 0) hora_planificada = "-";
                cols += td + hora_planificada + td_;
            }            
            
            String rows    = tr + cols + tr_;
            String tbl_det = tabla_detalle(row_puntos, rows);
            tbl            = tbl_enc + tbl_det;
        }
        
        if (tbl != "") {
            String imp = "<div class='top-space left-space'>"
                            + "<form method='POST' action='/RDW1/imprimirPlanillaAPedido'>"                            
                            +       "<input type='hidden' name='idIntervalo' id='idIntervalo' value=" + idIntervalo + ">"
                            +       "<input type='hidden' name='numeroInterno' id='numeroInterno' value=" + numeroInterno + ">"
                            +       "<input type='submit' class='btn' value='Imprimir'>"
                            + "</form>"
                        + "</div>";            
            out.println(tbl + imp);
        }
    }
            
    // Procesa consulta para visualizar planilla despacho planificado
    // en formato web y enlace para impresion en formato POS.
    public void verPlanilla(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String sidIntervalo = request.getParameter("idIntervalo");
        int idIntervalo = Restriction.getNumber(sidIntervalo);
        
        ArrayList<PlanillaDespacho> lst_pd = DespachoBD.select_planilla_sin_formato(idIntervalo);
        ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvuelta(lst_pd);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        String tbl, tbl_enc, tbl_det, cols, rows, row_puntos;
        tbl = tbl_enc = tbl_det = cols = rows = row_puntos = "";
        String tr = "<tr>", _tr = "</tr>", td = "<td>", _td = "</td>";
        String ruta = ""; String placa_ref = "";
        
        ArrayList<String[]> options  = new ArrayList<String[]>();
        ArrayList<String[]> options2 = new ArrayList<String[]>();
        
        for (int i = 0; i < lst_pll.size(); i++) {
            
            Planilla pll      = lst_pll.get(i);                        
            int numero_vuelta = pll.getNumeroVuelta();            
            String fecha      = ffmt.format(pll.getFecha());
            String placa      = pll.getPlaca();
            String nint       = pll.getNumeroInterno();            
            ruta              = pll.getRuta();
            
            ArrayList<PlanillaDespacho> pd_detalle = pll.getDetalle();            
            
            if (numero_vuelta == 1) {
                if (tbl_enc == "") {
                    tbl_enc    = tabla_encabezado(pll);                
                    row_puntos = fila_puntos(pll);
                }
                if (rows != "") {
                    tbl_det    = tabla_detalle(row_puntos, rows);  
                    tbl        += tbl_enc + tbl_det;                    
                    tbl_enc    = tabla_encabezado(pll);                
                    row_puntos = fila_puntos(pll);
                    rows       = "";
                }
            } 
            
            cols = td + numero_vuelta + _td;
            for (int j = 0; j < pd_detalle.size(); j++) {
                PlanillaDespacho pd     = pd_detalle.get(j);
                String hora_planificada = hfmt.format(pd.getHoraPlanificada());
                // Si punto esta inhabilitado, no se visualiza hora
                int tipo_punto = pd.getTipoPunto();
                if (tipo_punto == 0) hora_planificada = "-";
                cols += td + hora_planificada + _td;
            }            
            rows += tr + cols + _tr;
            
            if (i == lst_pll.size()-1) {
                //tbl_enc = tabla_encabezado(pll);
                //row_puntos = fila_puntos(pll);
                tbl_det = tabla_detalle(row_puntos, rows);
                tbl     += tbl_enc + tbl_det;
            }
            
            if (numero_vuelta == 1) {
                
                String option_1[] = {   // value, option
                    nint,
                    placa + " - " + nint
                };
                
                String option_2[] = {   // value, option
                    fecha + "," + nint,
                    fecha + " - " + placa + " - " + nint
                };
                
                if (placa_ref == "" ||
                    placa_ref.indexOf(placa) == -1) {
                    options2.add(option_1);
                    placa_ref += placa;                   
                }
                
                options.add(option_2);                
            }
        }
        
        // Se unen listas options y options2
        for (int i = options2.size()-1; i >= 0; i--) {
            options.add(0, options2.get(i));
        }
        
        String opts = "<option value=''>Todos los veh&iacute;culos</option>";        
        for (int i = 0; i < options.size(); i++) {
            String op[] = options.get(i);
            opts += "<option value='" + op[0] + "'>" + op[1] + "</option>";
        }

        if (tbl != "") {
            String imp = "<div>"
                            + "<form method='POST' action='/RDW1/imprimirPlanilla'>"
                            +       "<select name='fecha_veh' id='fecha_veh' class='selectpicker form-control'>" + opts + "</select>"
                            +       "<input type='hidden' name='idIntervalo' id='idIntervalo' value=" + idIntervalo + ">"
                            +       "<input type='submit' class='btn' value='Imprimir'>"
                            + "</form>"
                        + "</div>"
                        + "<h4>&nbsp;" + ruta + "</h4><hr/>";
            tbl = imp + tbl;
        }
        
        out.println(tbl);
    }        
    
    // Construye tabla encabezado (con informacion generica)
    // para tabla de registros planilla despacho.
    public String tabla_encabezado(Planilla pll) {
        
        int nplanilla     = pll.getNumeroPlanilla();
        String snplanilla = DespachoUtil.formatoNumeroPlanilla(nplanilla);
        int idruta        = pll.getIdRuta();
        String ruta       = pll.getRuta();
        String placa      = pll.getPlaca();
        String ninterno   = pll.getNumeroInterno();
        ninterno          = (ninterno == null || ninterno == "") ? "-" : ninterno;
        
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(pll.getFecha());
                
        String tbl =                         
            "<table class='tbl-style tbl-text-center' style='margin-top:12px;'>"
                + "<thead>"
                +   "<tr>"
                +   "<th class='lbl'>N° Planilla</th>"
                +   "<th class='lbl'>Ruta</th>"
                +   "<th class='lbl'>N° Interno</th>"
                +   "<th class='lbl'>Placa</th>"
                +   "<th class='lbl'>Fecha</th>"
                +   "</tr>"
                + "</thead>"
                + "<tbody>"
                +   "<tr>"
                +   "<td>" + snplanilla + "</td>"
                +   "<td>" + ruta  + "</td>"
                +   "<td>" + ninterno + "</td>"
                +   "<td>" + placa + "</td>"
                +   "<td>" + fecha + "</td>"
                +   "</tr>"
                + "</tbody>"
            + "</table>";
        
        return tbl;
    }
    
    // Construye tabla detalle (puntos y tiempos)
    // para registros de planilla despacho.
    public String tabla_detalle(String row_puntos, String rows) {
        String tbl = 
            "<table class='tbl-style tbl-text-center'>"
            +   "<thead>" + row_puntos + "</thead>"
            +   "<tbody>" + rows + "</tbody>"
            + "</table>";
        return tbl;
    }

    // Construye fila de tabla que contiene nombres de puntos
    // de una planilla despacho.
    public String fila_puntos(Planilla pll) {
        
        ArrayList<PlanillaDespacho> pd_detalle = pll.getDetalle();        
        
        String cols, row; row = "";
        cols = "<td></td>";
        for (int i = 0; i < pd_detalle.size(); i++) {
            PlanillaDespacho pd = pd_detalle.get(i);
            cols += "<td>" + pd.getPunto() + "</td>";
        }
        row = "<tr>" + cols + "</tr>";
        return row;
    }
    
    // Filtra listado de planilla despacho en una fecha y movil especifico.
    public ArrayList<Planilla> filtrarPlanilla(String fecha, String ninterno, ArrayList<Planilla> lst) {
        
        ArrayList<Planilla> nlst = new ArrayList<Planilla>();
        for (Planilla pll : lst) {
            String fecha_pll    = ffmt.format(pll.getFecha());
            String ninterno_pll = pll.getNumeroInterno();
            if (fecha_pll.compareTo(fecha) == 0 &&
                ninterno_pll.compareTo(ninterno) == 0) {
                nlst.add(pll);
            }
        }
        return nlst;
    }
    
    // Filtra listado de planilla despacho en un movil especifico.
    public ArrayList<Planilla> filtrarPlanilla(String ninterno, ArrayList<Planilla> lst) {
        
        ArrayList<Planilla> nlst = new ArrayList<Planilla>();
        for (Planilla pll : lst) {            
            String ninterno_pll = pll.getNumeroInterno();
            if (ninterno_pll.compareTo(ninterno) == 0) {
                nlst.add(pll);
            }
        }
        return nlst;
    }
    
    // Extrae nombre de ruta de una planilla despacho.    
    // (Usado para nombrar archivo de impresion de planilla).
    public String nombreRuta(ArrayList<Planilla> lst) {                
        try {
            String nombre_ruta = lst.get(0).getDetalle().get(0).getNombreRuta();
            nombre_ruta        = nombre_ruta.replace(' ', '_');
            String ruta_min    = nombre_ruta.toLowerCase();
            if (ruta_min.contains("ruta")) {
                return nombre_ruta;
            } 
            return "Ruta_" + nombre_ruta;
        } catch (NullPointerException e) {
            return "";
        }
    }
    
    /*
    ============================================================================
    === Planilla con formato de control (division por vehiculo)
    ============================================================================
    */
    
    // Asocia tipo de control-despacho con el tipo de generacion-despacho,
    // para asi controlar los registros de un tipo con su control adecuado.
    public static int asociarControl2Generacion(String ctrl_dph) {
        if (ctrl_dph != null) {
            if (ctrl_dph.compareTo(Constant.CONTROL_PLANIFICADO) == 0)
                return Constant.DPH_PLANIFICADO;
            if (ctrl_dph.compareTo(Constant.CONTROL_INTERVALO) == 0)
                return Constant.DPH_MANUAL;
        }
        return Constant.DPH_PLANIFICADO;
    }
    
    // Consulta, formatea y construye planilla de control despacho de una ruta y fecha
    // en especifico. El formato consiste en dividir los registros de planilla,
    // en las vueltas de los moviles asignados a la ruta.
    public void controlarHistoria(
                            String sruta, 
                            Date fecha,
                            PrintWriter out) {
        
        String array_ruta[] = sruta.split(",");
        String tbl = "";
        
        for (int i = 0; i < array_ruta.length; i++) {
            int id_ruta = Restriction.getNumber(array_ruta[i]);
            
            // Se muestra historia de despacho (rango total de hora de ruta)
            ArrayList<PlanillaDespacho> lst_pd = DespachoBD.select_planilla_sin_formato(id_ruta, fecha);
            ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvuelta(lst_pd);

            if (lst_pd  == null || lst_pd.size()  == 0 ||
                lst_pll == null || lst_pll.size() == 0) {
                continue;
            }

            // Historia control despacho
            tbl += DespachoControlHTML.tbl_historia(lst_pll);            
        }
        
        if (tbl == "") {
            tbl = "* Sin datos para mostrar.";
        }
        out.println("<div>" + tbl + "</div>");
    }
    
    // Consulta, formatea y construye planilla de control despacho de una ruta, fecha,
    //  y hora en especifico. El formato consiste en dividir los registros de planilla
    // por movil y filtrar la vuelta que transcurra en hora actual.
    public void controlarHoraActual(
                            String sruta,
                            Date fecha,
                            int tipo_gen_dph,
                            PrintWriter out) {
        
        String array_ruta[] = sruta.split(",");
        String div_sep = "<div style='margin-top: 40px;'></div>";
        String tbl = "";        
        
        for (int i = 0; i < array_ruta.length; i++) {
        
            int id_ruta = Restriction.getNumber(array_ruta[i]);
            
            // Se muestra vuelta actual de cada vehiculo (simplificado en una vista)
            ArrayList<PlanillaDespacho> lst_pd = DespachoBD.select_planilla_sin_formato(id_ruta, fecha);
            ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvehiculo(lst_pd);

            if (lst_pd  == null || lst_pd.size() == 0 ||
                lst_pll == null || lst_pll.size() == 0) {
                continue;
            }

            // Filtrado por hora actual
            PlanillaUtil putil = new PlanillaUtil();
            ArrayList<Planilla> lst_pll_proc = putil.filtro_hora_actual(lst_pll);
            ArrayList<PlanillaDespacho> lst_pd_q;

            Planilla pll_info = null;
            String fila_detalle = "";

            for (Planilla pll : lst_pll_proc) {
                pll_info = pll;

                if (tipo_gen_dph == Constant.DPH_MANUAL) {
                    fila_detalle += fila_control_detalle_intervalo_min(pll);
                } else if (tipo_gen_dph == Constant.DPH_PLANIFICADO) {
                    fila_detalle += fila_control_detalle_min(pll);
                } else {
                    fila_detalle += fila_control_detalle_min(pll);
                }
            }

            if (pll_info != null) {
                if (i > 0) {
                    tbl += div_sep;
                }
                tbl += tabla_control_detalle_min(pll_info, fila_detalle);                
            }
        }
        if (tbl == "") {
            tbl = "* Sin datos para mostrar.";
        }
        out.println("<div>" + tbl + "</div>");
    }
    
    // Procesa solicitud de visualizacion de control-despacho
    // de una ruta, fecha y moviles especificados.
    // Delega construccion de vista segun el tipo de presentacion seleccionado
    // (Con o sin historia).
    public void verControlDespacho(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String sruta            = request.getParameter("sruta");
        String sfecha           = request.getParameter("fecha");        
        String mostrar_historia = request.getParameter("mostrarHistoria");
        String smovil           = request.getParameter("smovil");        
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
                
        int id_ruta = Restriction.getNumber(sruta);
        Date fecha  = null;
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            fecha = fmt.parse(sfecha);
        } catch (ParseException e) {
            System.err.println(e);
            return;
        }
        
        // Se guardan parametros de consulta en sesion, para evitar
        // su reespecificacion cuando exista un cambio de contexto
        HttpSession session = request.getSession();
        session.setAttribute("dph_ruta", sruta);
        session.setAttribute("dph_fecha", sfecha);
        session.setAttribute("dph_mostrar_historia", mostrar_historia);
        session.setAttribute("dph_movil", smovil);
        
        // Se obtiene tipo de control despacho almacenado,
        // para controlar solo los datos de planilla asociados
        Entorno entorno      = (Entorno) session.getAttribute("entorno");
        String tipo_ctrl_dph = entorno.obtenerPropiedad(Constant.TIPO_CONTROL_DESPACHO);
        int tipo_gen_dph     = asociarControl2Generacion(tipo_ctrl_dph);
        DespachoBD.tipogeneracion_despacho(tipo_gen_dph);
        
        if (mostrar_historia.compareTo("1") == 0) {
            
            if (sruta == null) {
                out.println("<div class='form-msg'>* Seleccione al menos una ruta.</div>");
            } else {
                DespachoControlHTML.tipo_despacho(tipo_gen_dph);
                controlarHistoria(sruta, fecha, out);
            }            
            
        } else { // if (mostrar_historia.compareTo("0") == 0) {
            
            if (sruta == null) {
                out.println("<div class='form-msg'>* Seleccione al menos una ruta.</div>");
            } else {
                controlarHoraActual(sruta, fecha, tipo_gen_dph, out);
            }                    
        }
    }
    
    // ======================= Tabla control despacho (hora actual - simplificado)
    // ======================= Usado actualmente
    
    // Construye fila cabecera con informacion generica 
    // de planilla despacho de un movil.
    public String fila_control_cabecera(Planilla pll) {
        
        String placa     = pll.getPlaca();
        String ninterno  = pll.getNumeroInterno();
        ninterno         = (ninterno == null || ninterno == "") ? "-" : ninterno;
        String nplanilla = DespachoUtil.formatoNumeroPlanilla(pll.getNumeroPlanilla());
        String nvuelta   = "" + pll.getNumeroVuelta();
        
        String tr = "<tr id='" + placa + "'>", _tr = "</tr>",
               td = "<td>", _td = "</td>", 
               td_info = "<td id='" + placa + "' class='to_customize'><div class='custom-icon'></div>";
        
        String cols, lbl_class, stiempo;
        cols = td_info + _td + 
               td + ninterno  + _td +
               td + placa     + _td +
               td + nplanilla + _td +
               td + nvuelta   + _td;
        
        return cols;
    }
    
    // Construye fila con informacion de control minima (diferencia de tiempo)
    // para un conjunto de registros de planilla a pedido de un movil.
    public String fila_control_detalle_intervalo_min(Planilla pll) {
        
        String cols = fila_control_cabecera(pll);
        String lbl_class, sdiferencia;
        Time hora_salida = null;
        
        String placa = pll.getPlaca();
        String tr = "<tr id='" + placa + "'>", _tr = "</tr>", 
               td = "<td>", _td = "</td>";
                
        SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
        ArrayList<PlanillaDespacho> lst_pd = pll.getDetalle();
        
        for (int i = 0; i < lst_pd.size(); i++) {
            PlanillaDespacho pd   = lst_pd.get(i);            
            Time hora_real        = pd.getHoraReal();
            long diferencia       = pd.getDiferencia();            
            
            sdiferencia = "-";
            lbl_class   = "lbl-gris";
            
            if (hora_real != null) {                
                diferencia = pd.getDiferencia(); // tipoPunto(1) -> diferencia(0)
            
                if (diferencia == 0)  {
                    sdiferencia = hfmt.format(TimeUtil.horaCero());
                    lbl_class = "lbl-verde";
                } else {
                    sdiferencia = hfmt.format(TimeUtil.tiempoTranscurrido(diferencia));                                    
                    if (diferencia > 0) {
                        sdiferencia = "+" + sdiferencia;
                        lbl_class = "lbl-rojo";
                    } else {
                        sdiferencia = "-" + sdiferencia;
                        lbl_class = "lbl-naranja";
                    }
                }
            }
            
            td = "<td class='"+ lbl_class +"'>";
            cols += td + sdiferencia + _td;
        }
        
        return tr + cols + _tr;
    }
    
    // Construye fila con informacion de control minima (diferencia de tiempo)
    // para un conjunto de registros de planilla planificada de un movil.
    public String fila_control_detalle_min(Planilla pll) {
                
        String cols = fila_control_cabecera(pll);
        String lbl_class, stiempo;
        
        String placa = pll.getPlaca();
        String tr = "<tr id='" + placa + "'>", _tr = "</tr>", 
               td = "<td>", _td = "</td>";
                
        SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
        ArrayList<PlanillaDespacho> lst = pll.getDetalle();
        
        for (int i = 0; i < lst.size(); i++) {
            PlanillaDespacho pd = lst.get(i);
            long diferencia = pd.getDiferencia();
            Time tiempo = TimeUtil.horaCero();
            
            lbl_class = "lbl-gris";
            stiempo   = "-";
            
            if (pd.getEstadoDespacho() == 1 ||
                pd.getEstadoDespacho() == 2) {
                
                if (diferencia > 0) {
                    tiempo    = new Time(tiempo.getTime() + diferencia);
                    stiempo   = "+" + hfmt.format(tiempo);
                    lbl_class = "lbl-rojo";
                } else if (diferencia < 0) {
                    tiempo    = new Time(tiempo.getTime() + (-1 * diferencia));
                    stiempo   = "-" + hfmt.format(tiempo);
                    lbl_class = "lbl-naranja";
                } else {
                    stiempo   = "";
                    lbl_class = "lbl-verde";
                }
            } 
            
            if (pd.getEstadoDespacho() == 2) {
                lbl_class = "lbl-morado";
            }
            if (pd.getEstadoDespacho() == -1) {
                stiempo   = "-";
                lbl_class = "lbl-negro";
            }
            
            td = "<td class='" + lbl_class + "'>";
            cols += td + stiempo + _td;
        }
        
        return tr + cols + _tr;
    }
    
    // Construye tablas de informacion y control minima para un
    // conjunto de registros de planilla.
    public String tabla_control_detalle_min(
            Planilla pll,
            String fila_detalle) {
        
        String th = "<th>", _th = "</th>";
        
        String cols = th + _th + 
                      th + _th +
                      th + _th +
                      th + "N° Planilla" + _th +
                      th + "N° Vuelta"   + _th;
                
        for (PlanillaDespacho pd : pll.getDetalle()) {
            String nombrePunto = DespachoUtil.obtieneNombrePunto(pd);
            th = "<th data-toggle='tooltip' title='"+ nombrePunto +"'>";
            cols += th + pd.getPunto() + _th;
        }
        
        String hora_actual = new SimpleDateFormat("HH:mm").format(new Date());
        
        String tbl_hora = 
            "<table class='tbl-style-cpd'>"
                + "<thead>"
                    + "<th>Ruta</th>" 
                    + "<td>" + pll.getRuta() + "</td>"                     
                    + "<th>" + hora_actual   + "</th>"
                + "</thead>"
            + "</table>";
        
        String tbl = 
            "<table class='tbl-style-cpd td-size' style='margin-top: 6px;'>"
                + "<thead><tr>" + cols + "</tr></thead>"
                + "<tbody>" + fila_detalle + "</tbody>"
            + "</table>";
        
        return tbl_hora + tbl;
    }        
        
    // ==================================== Tabla control despacho (hora actual)
    
    // Construye fila cabecera con informacion generica 
    // de planilla despacho de un movil.
    public String tabla_control_encabezado(Planilla pll) {
        
        String ruta  = pll.getRuta();
        String placa = pll.getPlaca();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(pll.getFecha());
        
        String tbl = 
            "<table id='" +placa+ "' class='tbl-style-cpd' style='margin-top: 18px;'>"
                + "<thead>"
                +   "<tr>"
                +   "<th>Placa</th>"    +   "<td>" + placa  + "</td>"
                +   "<th>Ruta</th>"     +   "<td>" + ruta + "</td>"
                +   "<th>Fecha</th>"    +   "<td>" + fecha + "</td>"
                +   "</tr>"
                + "</thead>"                
            + "</table>";
        
        return tbl;
    }
    
    // Construye tabla de control minima para un conjunto 
    // de registros de planilla.
    public String tabla_control_detalle(ArrayList<PlanillaDespacho> lst_pd) {
        
        String tr = "<tr>", _tr = "</tr>", 
               td = "<td>", _td = "</td>",
               th = "<th>", _th = "</th>";
        
        String cols_p = "", cols_t = td + _td, placa = "";
        
        String hora_planificada, hora_real, tiempo_diferencia, lbl_class;
        SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");        
        SimpleDateFormat hfmt_min = new SimpleDateFormat("HH:mm");        
        
        for (int i = 0; i < lst_pd.size(); i++) {
            PlanillaDespacho pd = lst_pd.get(i);
            
            if (i == 0) {
                cols_p  = th + "N° vuelta " + pd.getNumeroVuelta() + _th;
                cols_p += th + pd.getPunto() + _th;
                placa   = pd.getPlaca();
            } else {
                cols_p += th + pd.getPunto() + _th;
            }
            
            hora_planificada = hfmt_min.format(pd.getHoraPlanificada());
            long diferencia  = pd.getDiferencia();
            Time tiempo = TimeUtil.horaCero();
            
            hora_real = tiempo_diferencia = "-";
            lbl_class = "lbl-gris";
            
            if (pd.getEstadoDespacho() == 1 ||
                pd.getEstadoDespacho() == 2) {
                hora_real = hfmt_min.format(pd.getHoraReal());
                
                if (diferencia == 0) {
                    tiempo_diferencia = "00:00:00";
                    lbl_class = "lbl-verde";
                
                } else if (diferencia > 0) {
                    tiempo = new Time(tiempo.getTime() + diferencia);
                    tiempo_diferencia = "+" + hfmt.format(tiempo);
                    lbl_class = "lbl-rojo";
                
                } else {
                    tiempo = new Time(tiempo.getTime() + (-1 * diferencia));
                    tiempo_diferencia = "-" + hfmt.format(tiempo);
                    lbl_class = "lbl-naranja";
                }
            } 
            
            if (pd.getEstadoDespacho() == 2) {
                lbl_class = "lbl-morado";
            }
            if (pd.getEstadoDespacho() == -1) {
                hora_planificada = hora_real = tiempo_diferencia = "-";
                lbl_class = "lbl-negro";
            }
            
            td = "<td class='" + lbl_class + "'>";
            cols_t += td +
                "<span class='primary'>"   + hora_planificada  + "</span>" +
                "<span class='secondary'>" + hora_real         + "</span>" +
                "<span class='secondary'>" + tiempo_diferencia + "</span>"
                //"<span class='" + clase_lbl + "'></span>"                       
            + _td;
        } 
        
        String tbl = "<table id='" +placa+ "' class='tbl-style-cpdd'>"
                   + "<thead>"  + cols_p + "</thead>"
                   + "<tbody> " + cols_t + " </tbody></table>";
        return tbl;
    }
    
    /*
    ============================================================================
    === Programacion ruta
    ============================================================================
    */
    
    // Procesa solicitud para creacion de programacion-ruta.
    // El identificador del nombre de programacion-ruta registrado en tabla
    // independiente, se asocia a los registros detalle de la programacion.
    public String creaProgramacionRuta(HttpServletRequest request,
            HttpServletResponse response) {
        
        String pg_nombre = request.getParameter("nombreProgramacion");
        String pg_ruta = request.getParameter("programacionRuta");       
        
        // Se inserta nombre asignado a programacion-ruta
        int id = DespachoBD.insert_nombre_programacion_ruta(pg_nombre);
        // Obtiene instruccion sql de programacion-ruta
        String sql = sql_insert_programacion_ruta(id, pg_ruta);
        
        // Inserta programacion-ruta
        if (DespachoBD.insert_programacion_ruta(sql)) {            
            actualizar_listados(request, response);
            request.setAttribute("msg", "* Programaci&oacute;n de rutas registrado correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");            
        } else {
            request.setAttribute("msg", "* Programaci&oacute;n de rutas no registrado.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        ProgramacionRuta pgr = DespachoBD.programacion_ruta_activa();
        HttpSession session  = request.getSession();
        session.setAttribute("pgruta_activa", pgr.getNombreProgramacion());
        session.setAttribute("pgruta_activa_id", pgr.getId());        
        
        return "/app/despacho/programacionRuta.jsp";
    }
    
    public String actualizaProgramacionRuta(HttpServletRequest request,
            HttpServletResponse response) {
        
        String pg_nombre = request.getParameter("nombreProgramacion_upd");
        String pg_ruta   = request.getParameter("programacionRuta_upd");
        String sid_pg    = request.getParameter("idProgramacionRuta_upd");
        int id_pg = Restriction.getNumber(sid_pg);
        
        String sql_upd = "UPDATE tbl_pgr SET NOMBRE = '" + pg_nombre + "' WHERE PK_PGR = " + id_pg;        
        ArrayList<String> lst_upd = sql_update_programacion_ruta(id_pg, pg_ruta);
        
        if (DespachoBD.update_programacion_ruta(sql_upd, lst_upd)) {
            actualizar_listados(request, response);
            request.setAttribute("msg", "* Programaci&oacute;n de ruta actualizada correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
        } else {
            request.setAttribute("msg", "* Programaci&oacute;n de ruta no actualizada.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        ProgramacionRuta pgr = DespachoBD.programacion_ruta_activa();
        HttpSession session  = request.getSession();
        session.setAttribute("pgruta_activa", pgr.getNombreProgramacion());
        session.setAttribute("pgruta_activa_id", pgr.getId());                        
        
        return "/app/despacho/programacionRuta.jsp";
    }
    
    // Construye instruccion de insercion SQL para valores de 
    // programacion-ruta que provienen con formato:
    // idRuta1,g1,g2...g7 & idRuta2,g1,g2...g7 & idRutaN,g1,g2...g7    
    // Se adiciona identificador de nombre programacion-ruta
    // al inicio del listado de valores.
    public String sql_insert_programacion_ruta(int id, String pg_ruta) {
        
        String sql = "INSERT INTO tbl_programacion_ruta"
                    + " (FK_PGR, FK_RUTA, LUN, MAR, MIE, JUE, VIE, SAB, DOM, ESTADO)"
                    + " VALUES ";
        
        String s, ss = "";
        String arr_ruta[] = pg_ruta.split("&");        
        
        for (int i = 0; i < arr_ruta.length; i++) {
            String arr_grupo[] = arr_ruta[i].split(",");
            s = "" + id;
            for (int j = 0; j < arr_grupo.length; j++) {
                s += "," + arr_grupo[j];
            }
            s = "(" + s + ",1)";
            ss += (ss == "") ? s : "," + s;            
        }
        return sql + ss;
    }
    
    public ArrayList<String> sql_update_programacion_ruta(int id, String pg_ruta) {
        
        String arr_ruta[] = pg_ruta.split("&");
        ArrayList<String> lst = new ArrayList<String>();
        
        for (int i = 0; i < arr_ruta.length; i++) {            
            String arr_grupo[] = arr_ruta[i].split(",");
            String ruta        = arr_grupo[0];
            String set_grupo = " LUN = " + arr_grupo[1] + ","
                             + " MAR = " + arr_grupo[2] + ","
                             + " MIE = " + arr_grupo[3] + ","
                             + " JUE = " + arr_grupo[4] + ","
                             + " VIE = " + arr_grupo[5] + ","
                             + " SAB = " + arr_grupo[6] + ","
                             + " DOM = " + arr_grupo[7];
            String where  = "FK_PGR = " + id + " AND FK_RUTA = " + ruta;
            String update = "UPDATE tbl_programacion_ruta SET " + set_grupo + " WHERE " + where;
            lst.add(update);            
        }
        return lst;
    }
    
    // Activa programacion-ruta especificada.
    // Establece valor de campo estado en 1 para registros de programacion elegida.
    public String activaProgramacionRuta(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sid_pgruta = request.getParameter("idProgramacionRuta_act");
        int id_pgruta = Restriction.getNumber(sid_pgruta);
        
        if (DespachoBD.activa_programacion_ruta(id_pgruta)) {
            actualizar_listados(request, response);
            request.setAttribute("msg", "* Programaci&oacute;n de ruta activada correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
        } else {
            request.setAttribute("msg", "* Programaci&oacute;n de ruta no activada.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        ProgramacionRuta pgr = DespachoBD.programacion_ruta_activa();
        HttpSession session  = request.getSession();
        session.setAttribute("pgruta_activa", pgr.getNombreProgramacion());
        session.setAttribute("pgruta_activa_id", pgr.getId());        
        
        return "/app/despacho/programacionRuta.jsp";
    }
    
    // Desactiva programacion-ruta especificada.
    // Establece valor de campo estado a 1 para registros de programacion elegida.
    public String eliminaProgramacionRuta(HttpServletRequest request,
            HttpServletResponse response) {
        
        String sid_pgruta = request.getParameter("idProgramacionRuta_des");
        int id_pgruta = Restriction.getNumber(sid_pgruta);
        
        if (DespachoBD.desactivar_programacion_ruta(id_pgruta)) {
            actualizar_listados(request, response);
            request.setAttribute("msg", "* Programaci&oacute;n de ruta eliminada correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
        } else {
            request.setAttribute("msg", "* Programaci&oacute;n de ruta no eliminada.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        ProgramacionRuta pgr = DespachoBD.programacion_ruta_activa();
        HttpSession session  = request.getSession();
        session.setAttribute("pgruta_activa", pgr.getNombreProgramacion());
        session.setAttribute("pgruta_activa_id", pgr.getId());        
        
        return "/app/despacho/programacionRuta.jsp";
    }
        
    // Procesa solicitud de inactivacion de programacion-ruta.
    public String inactivaProgramacionRuta(HttpServletRequest request,
            HttpServletResponse response) {
        
        /*
        if (DespachoBD.desactivar_programacion_ruta()) {
            actualizar_listados(request, response);
            request.setAttribute("msg", "* Ahora puede crear una nueva programaci&oacute;n de ruta.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
            
            HttpSession session = request.getSession();
            session.setAttribute("pgruta_activa", "Ninguna");
            
        } else {
            request.setAttribute("msg", "* Imposible deshabilitar programaciones de ruta previas.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        } */
        
        int lst_idx[] = { 
            SelectC.LST_RUTA.ordinal(),
            SelectC.LST_GEMPRESA.ordinal(),
            SelectC.LST_PGRUTA_NOMBRE.ordinal()
        };
        
        SelectBD select = new SelectBD(lst_idx);
        request.setAttribute("select", select);
        request.setAttribute("pgruta_activa", "Ninguna");
        request.setAttribute("pgruta_activa_id", 0);
        
        return "/app/despacho/programacionRuta.jsp";
    }
    
    // Procesa solicitud de almacenamiendo de fecha festivo.
    // Almacena fecha especificada como dia festivo.
    public String adicionaDiaFestivo(HttpServletRequest request,
            HttpServletResponse response) {
        
        String fecha = request.getParameter("fecha-festivo");
        
        String msg, msg_type;
        
        // Comprueba que fecha no exista
        if (!DespachoBD.existe_dia_festivo(fecha)) {
            // Almacena fecha
            if (DespachoBD.insert_dia_festivo(fecha)) {
                msg = "* D&iacute;a festivo almacenado correctamente.";
                msg_type = "form-msg bottom-space alert alert-success";
                
                SelectBD select = new SelectBD(true);
                request.setAttribute("select", select);
            } else {
                msg = "* D&iacute;a festivo no almacenado.";
                msg_type = "form-msg bottom-space alert alert-info";
            }                   
        } else {
            msg = "* D&iacute;a festivo ya existe.";
            msg_type = "form-msg bottom-space alert alert-info";
        }
        
        request.setAttribute("msg", msg);
        request.setAttribute("msgType", msg_type);
        
        return "/app/despacho/diaFestivo.jsp";
    }
    
    // Procesa solicitud de eliminacion de dia festivo.
    // Elimina registro de fecha especificado de la base de datos.
    public String eliminaDiaFestivo(HttpServletRequest request,
            HttpServletResponse response) {
        
        String fecha = request.getParameter("fecha");
        
        // Elimina registro
        if (DespachoBD.delete_dia_festivo(fecha)) {
            request.setAttribute("msg", "* D&iacute;a festivo eliminado correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
            
            SelectBD select = new SelectBD(true);
            request.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* D&iacute;a festivo no eliminado.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/despacho/diaFestivo.jsp";
    }
    
    // Actualiza listados de los diferentes modulos,
    // que se presentan en la interfaz web.
    public void actualizar_listados(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);
        session.setAttribute("select", select);
    }
    
    /*
    ============================================================================
    === Rodamiento
    ============================================================================
    */
    
    // Procesa solicitud de visualizacion de rodamiento para un mes y
    // tipo especifico (semanal, mensual).
    // Rodamiento es generado con los registros de programacion-ruta activo.
    public void verRodamiento(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        String smes = request.getParameter("mesRodamiento");
        String stipoRodamiento = request.getParameter("tipoRodamiento");
        int tipoRodamiento = Restriction.getNumber(stipoRodamiento);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        String pgruta_activa = (String) request.getSession().getAttribute("pgruta_activa");
        if (pgruta_activa == null || pgruta_activa == "" || pgruta_activa.compareToIgnoreCase("ninguna") == 0) {
            out.println("* Debe almacenar una programaci&oacute;n de ruta previamente.");
            return;
        }
        
        int mes = Restriction.getNumber(smes);
        
        if (mes < 0 || mes > 11) { mes = 0; }
        String intervalo = (tipoRodamiento == 1) ? "Mensual" : "Semanal";
        
        String titulo = "Planilla rodamiento"
                            + " (" + intervalo + ") - "
                            + TimeUtil.meses[mes] + " " 
                            + TimeUtil.anio();
        
        titulo = "<div><strong>" + titulo + "</strong></div><br/>";
        
        DespachoUtil dutil = new DespachoUtil();
        
        // Efectua rodamiento segun el tipo
        ArrayList<Rodamiento> lst_rod;        
        lst_rod = (tipoRodamiento == 1)
                ? dutil.rodamientoMes(mes)
                : dutil.rodamientoSemana(mes);
        
        String cabecera, cuerpo, fila; cuerpo = "";
        
        String tr = "<tr>", _tr = "</tr>",               
               td = "<td>", _td = "</td>",
               th = "<th class='tbl'>", _th = "</th>";
        
        cabecera = th + _th +
                   th + _th;
        
        for (int i = 0; i < lst_rod.size(); i++) {
            
            Rodamiento rod = lst_rod.get(i);        
            String dia = td + rod.getDia()       + _td + 
                         td + rod.getNombreDia() + _td;
            
            if (i == 0) {                
                for (RutaRodamiento rr : rod.getLst_ruta()) {
                    cabecera += th + rr.getNombre() + _th;
                }
            }
            
            fila = dia;
            
            for (GrupoRodamiento gr : rod.getLst_grupo()) {
                fila += td + gr.getNombre() + _td;
            }                        
            
            cuerpo += tr + fila + _tr;
        }
        
        cabecera = tr + cabecera + _tr;
        
        String tbl = "<table class='tbl-style'>"
                        + "<thead>" + cabecera + "</thead>"
                        + "<tbody>" + cuerpo + "</tbody>"
                    + "</table>";               
                
        out.println(titulo + tbl);
    }
    
    /*
    ============================================================================
    === Sustitucion de movil
    ============================================================================
    */       
    
    // Procesa solicitud de planilla despacho ligada a un movil.
    // Visualiza en tabla planilla despacho de un movil, en fecha y
    // ruta especifico.
    public void consultarPlanillaMovil(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String ruta  = request.getParameter("sruta");
        String fecha = request.getParameter("fecha");
        String placa = request.getParameter("smovil");
        String totalidad_vueltas = request.getParameter("totalidad_vueltas");
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        // Consulta planilla despacho
        int idRuta = Restriction.getNumber(ruta);
        ArrayList<PlanillaDespachoMin> lst_pd_comp =
                DespachoBD.select_planilla_movil(idRuta, fecha, placa);
                
        ArrayList<PlanillaDespachoMin> lst_pd = lst_pd_comp;
        String fecha_actual = ffmt.format(new Date());
        
        // Verifica y decide si muestra todas las vueltas o unicamente
        // a partir de la vuelta actual
        if (fecha_actual.compareTo(fecha) == 0) {
            if (totalidad_vueltas.compareTo("1") != 0)    
                lst_pd = PlanillaUtil.filtro_hora_actual_min(lst_pd_comp);
        }
        
        String rows = "";        
        
        if (lst_pd != null && lst_pd.size() > 0) {
            
            for (int i = 0; i < lst_pd.size(); i++) {
                PlanillaDespachoMin pd = lst_pd.get(i);
                String hora_inicio = hfmt_full.format(pd.getHoraInicio());
                String hora_fin    = hfmt_full.format(pd.getHoraFin());
                int nvuelta        = pd.getNumeroVuelta();
                String chk_nvuelta = "<label><input type='checkbox'> Vuelta " + nvuelta + "</label>";
                
                String cols = 
                      "<td class='col-hidden-2'>" + pd.getNumeroVuelta() + "</td>"
                    + "<td class='col-hidden-2'>" + pd.getCantidadRegistros() + "</td>"
                    + "<td>" + chk_nvuelta + "</td>"
                    + "<td>" + pd.getPlaca() + "</td>"
                    + "<td>" + fecha + "</td>"
                    + "<td>" + pd.getNombreRuta() + "</td>"
                    + "<td>" + hora_inicio + "</td>"
                    + "<td>" + hora_fin + "</td>";
                rows += "<tr>" + cols + "</tr>";
            }
            out.print(rows);
            
        } else {
            String nodata = "<tr><td colspan='7'>Consulta no obtuvo datos.</td></tr>";
            out.print(nodata);
        }
    }
    
    // Procesa solicitud para sustitucion de movil asociado a una planilla despacho.
    public String sustituirMovil(HttpServletRequest request,
            HttpServletResponse response) {
        
        String ruta    = request.getParameter("ruta_s");
        String fecha   = request.getParameter("fecha_s");
        String smovil  = request.getParameter("movil_s");   // Movil a sustituir
        String nmovil  = request.getParameter("nsmovil");   // Movil por el que se sustituye                
        String lst_vueltas = request.getParameter("lst_vueltas");        
        //String snregistros = request.getParameter("nregistros_s");
        
        int nregistros = 0;
        
        // Obtiene conjunto de registros de planilla a actualizar
        ArrayList<String> lst_select = sql_registros_sustitucionMovil(
                ruta, fecha, smovil, lst_vueltas);                
        
        // Extrae cantidad de registros a sustituir, para validar
        // en el momento de la sustitucion
        for (String sql : lst_select) {
            nregistros += DespachoBD.registros_sustitucion_movil(sql);
        }
        
        // Obtiene conjunto de instrucciones SQL de sustitucion
        ArrayList<String> lst_upd = sql_sustitucionMovil(
                ruta, fecha, nmovil, smovil, lst_vueltas);           
        
        if (DespachoBD.sustituir_movil(lst_upd, nregistros)) {
            request.setAttribute("msg", "* Sustituci&oacute;n de veh&iacute;culo realizado correctamente.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
        } else {
            request.setAttribute("msg", "* Sustituci&oacute;n de veh&iacute;culo no realizada.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-warning");
        }
        
        return "/app/despacho/sustituyeMovil.jsp";
    }
    
    // Construye listado de instrucciones de consulta SQL, para determinar
    // la cantidad de registros a actualizar, segun las vueltas seleccionadas.
    public ArrayList<String> sql_registros_sustitucionMovil(
            String ruta,
            String fecha,
            String smovil,
            String lst_vueltas) {
        
        String arr_vueltas[] = lst_vueltas.split(",");
        ArrayList<String> lst_select = new ArrayList<String>();
        
        for (int i = 0; i < arr_vueltas.length; i++) {
            String sql = "SELECT count(PK_PLANILLA_DESPACHO) AS CANTIDAD_REGISTROS"
                            + " FROM tbl_planilla_despacho WHERE"
                            + " FK_RUTA = "  + ruta  + " AND "
                            + " PLACA = '"   + smovil + "' AND "
                            + " FECHA = '"   + fecha + "' AND"
                            + " NUMERO_VUELTA = " + arr_vueltas[i];
            lst_select.add(sql);
        }
        return lst_select;
    }
    
    // Construye listado de instrucciones de actualizacion SQL,
    // que actualiza el movil asociado a vueltas de una planilla despacho.
    public ArrayList<String> sql_sustitucionMovil(
            String ruta, 
            String fecha, 
            String nmovil,
            String smovil,
            String lst_vueltas) {
        
        String arr_vueltas[] = lst_vueltas.split(",");
        ArrayList<String> lst_upd = new ArrayList<String>();
        
        for (int i = 0; i < arr_vueltas.length; i++) {            
            String sql = "UPDATE tbl_planilla_despacho SET"
                        + " PLACA = '"  + nmovil   + "',"
                        + " VEHICULO_SUSTITUIDO = 1"
                        + " WHERE"                              
                        + " FK_RUTA = " + ruta    + " AND "
                        + " PLACA = '"  + smovil  + "' AND "
                        + " FECHA = '"  + fecha   + "' AND "
                        + " NUMERO_VUELTA = " + arr_vueltas[i];            
            lst_upd.add(sql);
        }
        return lst_upd;
    }
        
    // Verifica si despacho planificado ya existe en tabla planilla
    // La verificacion se basa en si existe despacho de una ruta
    // cuya hora inicio se contenga en rango de horas de planilla actual.
    public PlanillaDespachoMin planilla_cruzada_en_hora_planificada(
                    int id_ruta, 
                    String fecha_inicio, 
                    String fecha_final) {
        
        ArrayList<Despacho> lst_dph = (id_ruta == -1)
                ? DespachoBD.select()
                : DespachoBD.selectByRuta(id_ruta);
        
        ArrayList<PlanillaDespachoMin> lst_intv_tiempos = 
                DespachoBD.select_tiempos_planilla(fecha_inicio, fecha_final);
        
        for (Despacho dph : lst_dph) {
            
            String nombre_dph      = dph.getNombreDespacho();
            String hora_inicio_dph = hfmt_full.format(dph.getHoraInicio());
            int id_ruta_dph        = dph.getIdRuta();
            
            for (PlanillaDespachoMin pll : lst_intv_tiempos) {
                
                String hora_inicio_pll = pll.getHoraInicioStr();
                String hora_fin_pll    = pll.getHoraFinStr();
                int id_ruta_pll        = pll.getIdRuta();
                
                if (id_ruta_dph == id_ruta_pll &&
                    hora_inicio_dph.compareTo(hora_inicio_pll) >= 0 &&
                    hora_inicio_dph.compareTo(hora_fin_pll) < 0) {
                    return pll;
                }
            }
        }
        
        return null;
    }
    
    // Verifica si despacho manual ya existe en planilla (por hora salida)
    // La verificacion se basa en si hora salida esta contenida
    // en horas de otra vuelta.
    public PlanillaDespachoMin planilla_cruzada_en_hora_manual(
                    String placa,
                    String fecha,                    
                    String hora_salida) {
        
        ArrayList<PlanillaDespachoMin> lst_intv_tiempos = 
                DespachoBD.select_tiempos_planilla(fecha, fecha);
        
        for (PlanillaDespachoMin pll : lst_intv_tiempos) {            
            
            String placa_pll        = pll.getPlaca();
            String fecha_pll        = pll.getFechaStr();            
            String hora_inicio_pll  = pll.getHoraInicioStr();
            String hora_fin_pll     = pll.getHoraFinStr();
            int id_ruta_pll         = pll.getIdRuta();            
            
            if (fecha_pll.compareTo(fecha) == 0 &&
                placa_pll.compareTo(placa) == 0 &&
                hora_salida.compareTo(hora_inicio_pll) >= 0 &&
                hora_salida.compareTo(hora_fin_pll) < 0) {
                return pll;
            }
        }
        return null;
    }
    
    // Actualiza listados de los diferentes modulos,
    // que se presentan en la interfaz web.
    public void actualizarDatos (HttpServletRequest request,
            HttpServletResponse response) {        
        SelectBD select = new SelectBD(true);
        HttpSession session = request.getSession();        
        session.setAttribute("select", select);
    }
}
