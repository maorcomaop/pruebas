/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.EntornoWebBD;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.EntornoWeb;
import com.registel.rdw.logica.PropiedadesServidor;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.KeyUtil;
import com.registel.rdw.utils.Mail;
import com.registel.rdw.utils.PropertiesBase;
import com.registel.rdw.utils.PropertiesUtil;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorServidor extends HttpServlet {
        
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {    
        
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) 
            throws IOException, ServletException {
        
        if (!Session.alive(request)) {
            getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request, response);
        }
        
        String requestURI = request.getRequestURI();
        String url =  "";
        
        if (requestURI.endsWith("/actualizarConfServidor")) {
            url = actualizarConfServidor (request, response);
        } else if (requestURI.endsWith("/actualizarConfServidorPerimetro")) {
            url = actualizarConfServidorPerimetro (request, response);
        } else if (requestURI.endsWith("/actualizarEntorno")) { // Informacion de RegisData
            url = actualizarEntorno (request, response);           
        } else if (requestURI.endsWith("/verificarEntorno")) {
            url = verificarEntorno (request, response);
        } else if (requestURI.endsWith("/lanzarEntorno")) {
            url = lanzarEntorno (request, response);
        } else if (requestURI.endsWith("/actualizarEntornoWeb")) {
            url = actualizarEntornoWeb(request, response);
        } else if (requestURI.endsWith("/actualizarPresentacionWeb")) {
            url = actualizarPresentacionWeb(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para actualizar configuracion de servidor.
    // La configuracion se almacena en archivo y se gestiona internamente
    // a atraves de tabla hash.
    public String actualizarConfServidor (HttpServletRequest request,
            HttpServletResponse response) {
        
        String numcon           = request.getParameter("numcon");
        String puerto           = request.getParameter("puerto");
        String tiempoActivo     = request.getParameter("tiempoActivo");
        String tiempoRetraso    = request.getParameter("tiempoRetraso");
        String brc              = request.getParameter("brc");
        String iap              = request.getParameter("iap");
        
        Map<String, String> hprop = new HashMap();
        hprop.put(Constant.NUM_MAX_CONEXIONES_SERV, numcon);
        hprop.put(Constant.PUERTO_SERV, puerto);
        hprop.put(Constant.TIEMPO_ACTIVO_SERV, tiempoActivo);
        hprop.put(Constant.TIEMPO_RETRASO_SERV, tiempoRetraso);
        hprop.put(Constant.BASE_CONFIRMACION_DESCARTE, brc);
        hprop.put(Constant.INTENTOS_ACTUALIZACION_PUNTOS, iap);
        
        Entorno entorno_rd = (Entorno) request.getSession().getAttribute("entorno");
        PropertiesUtil propUtil = new PropertiesUtil(entorno_rd.getSystemPropertiesRD());
        
        // Almacena en archivo propiedades especificadas
        if (propUtil.establecerPropiedades(hprop)) {
            request.setAttribute("msg", "* Configuraci&oacute;n del servidor almacenada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
        } else {
            request.setAttribute("msg", "* Configuraci&oacute;n del servidor no almacenada. "
                               + "Verifique asignaci&oacute;n de un <i>directorio ra&iacute;z</i> en configuraci&oacute;n entorno escritorio.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        // Actualiza vista de propiedades desde archivo
        refrescarPropiedades(request, response);    
        
        return "/app/administracion/configuraServidor.jsp";
    }
    
    // Procesa solicitud de actualizacion de configuracion de servidor perimetro.
    // La configuracion se almacena en archivo y se gestiona internamente
    // a traves de tabla hash.
    public String actualizarConfServidorPerimetro (HttpServletRequest request,
            HttpServletResponse response) {
        
        String numcon           = request.getParameter("numcon");
        String puerto           = request.getParameter("puerto");
        String tiempoActivo     = request.getParameter("tiempoActivo");
        String tiempoRetraso    = request.getParameter("tiempoRetraso");
        
        Map<String, String> hprop = new HashMap();
        hprop.put(Constant.NUM_MAX_CONEXIONES_SERV_PERIMETRO, numcon);
        hprop.put(Constant.PUERTO_SERV_PERIMETRO, puerto);
        hprop.put(Constant.TIEMPO_ACTIVO_SERV_PERIMETRO, tiempoActivo);
        hprop.put(Constant.TIEMPO_RETRASO_SERV_PERIMETRO, tiempoRetraso);
        
        Entorno entorno_rd = (Entorno) request.getSession().getAttribute("entorno");
        PropertiesUtil propUtil = new PropertiesUtil(entorno_rd.getSystemPropertiesRD());
        
        // Almacena en archivo propiedades especificadas
        if (propUtil.establecerPropiedades(hprop)) {
            request.setAttribute("msg", "* Configuraci&oacute;n del servidor per&iacute;metro almacenada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
        } else {
            request.setAttribute("msg", "* Configuraci&oacute;n del servidor per&iacute;metro no almacenada."
                               + "Verifique la asignaci&oacute;n de un <i>directorio ra&iacute;z</i> en configuraci&oacute;n entorno escritorio.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        // Actualiza vista de propiedades desde archivo
        refrescarPropiedades(request, response);    

        return "/app/administracion/configuraServidorPerimetro.jsp";
    }
    
    // Procesa solicitud para actualizacion de configuracion de entorno escritorio.    
    public String actualizarEntorno (HttpServletRequest request,
            HttpServletResponse response) {
        
        String dirRaiz      = request.getParameter("dirRaiz");
        
        String web_path     = getServletContext().getRealPath("");
        Entorno entorno_rd  = new Entorno(web_path);
        
        entorno_rd.establecerPropiedad(Constant.DIRECTORIO_RAIZ_RDTO, dirRaiz);
        
        // Almacena en archivo configuracion de entorno
        if (entorno_rd.guardarEntorno()) {
            request.setAttribute("msg", "* Entorno Regisdata escritorio almacenado correctamente.");
            request.setAttribute("msgType", "alert alert-success");
        } else {
            request.setAttribute("msg", "* Entorno Regisdata escritorio no almacenado.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("entorno", entorno_rd);
        
        return "/app/administracion/configuraEntorno.jsp";
    }
    
    // Procesa solicitud para actualizacion de configuracion de entorno web.
    public String actualizarEntornoWeb (HttpServletRequest request,
            HttpServletResponse response) {
    
        String dominio      = request.getParameter("dominio");
        String email        = request.getParameter("email_data");
        String pass         = request.getParameter("pass");                
        
        String web_path = getServletContext().getRealPath("");
        Entorno entorno_rd = new Entorno(web_path);               
        
        //pass = KeyUtil.encrypt(pass);

        // Comprueba valores de parametros de configuracion especificados
        if((!Restriction.isNameDomain(dominio)  &&
            !Restriction.isHost_IPPort(dominio)) ||
            !Restriction.isEmailLogin(email) ||
            pass == null || pass == "") {
            request.setAttribute("msg", "* Entorno Regisdata web no almacenado.");
            request.setAttribute("msgType", "alert alert-danger");
            return "/app/administracion/configuraEntornoWeb.jsp";
        }
        
        entorno_rd.establecerPropiedad(Constant.DOMINIO_HOST_SERVIDOR, dominio);
        entorno_rd.establecerPropiedad(Constant.CORREO_CORPORATIVO, email);
        entorno_rd.establecerPropiedad(Constant.PASSWORD_CORPORATIVO, pass);
        
        // Almacena en archivo configuracion de entorno web
        if (entorno_rd.guardarEntorno()) {
            Mail.changeDataContact(web_path);
            request.setAttribute("msg", "* Entorno Regisdata Web almacenado correctamente.");
            request.setAttribute("msgType", "alert alert-success");
        } else {
            request.setAttribute("msg", "* Entorno Regisdata Web no almacenado.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("entorno", entorno_rd);
        
        return "/app/administracion/configuraEntornoWeb.jsp";
    }
    
    // Procesa solicitud de actualizacion de configuracion de presentacion web.
    public String actualizarPresentacionWeb (HttpServletRequest request, 
            HttpServletResponse response) {
        
        String ajuste_hs     = request.getParameter("ajuste_hs");
        String mostrar_es    = request.getParameter("mostrar_es");
        String mostrar_no    = request.getParameter("mostrar_no");        
        String mostrar_noic  = request.getParameter("mostrar_noic");
        String ajuste_pto    = request.getParameter("ajuste_pto");
                        
        mostrar_es   = (mostrar_es.compareTo("1") == 0) ? "1" : "0";
        mostrar_no   = (mostrar_no.compareTo("1") == 0) ? "1" : "0";
        mostrar_noic = (mostrar_noic.compareTo("1") == 0) ? "1" : "0";
        ajuste_pto   = (ajuste_pto.compareTo("1") == 0) ? "1" : "0";
        
        int hs = Restriction.getNumber(ajuste_hs);
        hs = (hs < -24 || hs > 24) ? 0 : hs;
        hs = (ajuste_hs.compareTo("-1") != 0 && hs == -1) ? 0 : hs;
        ajuste_hs = "" + hs;
        
        String web_path = getServletContext().getRealPath("");
        Entorno entorno_rd = new Entorno(web_path);
        
        entorno_rd.establecerPropiedad(Constant.MOSTRAR_ENTRADA_SALIDA, mostrar_es);
        entorno_rd.establecerPropiedad(Constant.MOSTRAR_NIVEL_OCUPACION, mostrar_no);
        entorno_rd.establecerPropiedad(Constant.MOSTRAR_NOIC, mostrar_noic);
        entorno_rd.establecerPropiedad(Constant.AJUSTE_HORA_SERVIDOR, ajuste_hs);        
        entorno_rd.establecerPropiedad(Constant.AJUSTE_PTO_CONTROL, ajuste_pto);
        
        // Almacena configuracion de presentacion       
        if (entorno_rd.guardarEntorno()) {
            request.setAttribute("msg", "* Configuraci&oacute;n de presentaci&oacute;n almacenada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
        } else {
            request.setAttribute("msg", "* Configuraci&oacute;n de presentaci&oacute;n no almacenada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("entorno", entorno_rd);
        
        return "/app/administracion/configuraPresentacionWeb.jsp";
    }
    
    // Procesa solicitud para verificar si entorno escritorio 
    // se encuentra en ejecucion y notifica al usuario.
    public String verificarEntorno (HttpServletRequest request,
            HttpServletResponse response) {
        
        if (Entorno.isrun_np()) {
            request.setAttribute("estadoEntorno", "1");
        } else {
            request.setAttribute("estadoEntorno", "0");
        }
        
        return "/app/administracion/configuraEntorno.jsp";
    }
    
    // Procesa solicitud para iniciar ejecucion de entorno escritorio.
    // Notifica al usuario segun resultado obtenido.
    public String lanzarEntorno (HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        Entorno entorno_rd = (Entorno) session.getAttribute("entorno");
        
        String msg = "";
        if (entorno_rd != null) {
            String rd_path = entorno_rd.getUbicacionRegisdata();
            //System.out.println("> " + pathRD);
            //String web_path = getServletContext().getRealPath("");            

            if (rd_path != null && rd_path != "") {                
                if (Entorno.prepareAndRun(rd_path)) {
                    msg = "* Entorno escritorio se ha ejecutado correctamente.";
                    request.setAttribute("msgType", "alert alert-success");
                } else {
                    msg = "* Entorno escritorio no ejecutado.";
                    request.setAttribute("msgType", "alert alert-warning");
                }
            } else {
                msg = "* Especifique primero el nombre del directorio raiz del entorno.";
                request.setAttribute("msgType", "alert alert-info");
            }
        } 
        
        request.setAttribute("msg", msg);
        return "/app/administracion/configuraEntorno.jsp";
    }
        
    // Extrae propiedades desde archivo y actualiza
    // estructura (tabla hash) para desplegarlas
    // en la interfaz de usuario.
    public void refrescarPropiedades (HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        Entorno entorno_rd = (Entorno) request.getSession().getAttribute("entorno");
        PropiedadesServidor propServ = new PropiedadesServidor(entorno_rd.getSystemPropertiesRD());
        
        session.setAttribute("prop", propServ);
    }
    
}
