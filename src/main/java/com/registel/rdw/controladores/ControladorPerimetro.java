/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.PropiedadesPerimetro;
import com.registel.rdw.logica.PropiedadesServidor;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.PropertiesUtil;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorPerimetro extends HttpServlet {
    
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
        
        if (requestURI.endsWith("/actualizarPerimetro")) {
            url = actualizarPerimetro (request, response);
        } 
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para actualizar configuracion de perimetro.
    // La configuracion se almacena en archivo y se maneja internamente
    // a traves de tabla hash.
    public String actualizarPerimetro (HttpServletRequest request,
            HttpServletResponse response) {
        
        String descarteInicial  = request.getParameter("descarteInicial");
        String descarteFinal    = request.getParameter("descarteFinal");
        String descarteHolgura  = request.getParameter("descarteHolgura");
        String comp_min         = request.getParameter("compMM");
        String comp_seg         = request.getParameter("compSS");
        
        int idi = Restriction.getNumber(descarteInicial);
        int idf = Restriction.getNumber(descarteFinal);
        int idh = Restriction.getNumber(descarteHolgura);
        int icomp_min = Restriction.getNumber(comp_min);
        int icomp_seg = Restriction.getNumber(comp_seg);
        
        if (idi >= 1 && idi <= 59 &&
            idf >= 0 && idf <= 59 &&
            idh >= 1 && idh <= 59 &&
            icomp_min >= 1 && icomp_min <= 59 &&
            icomp_seg >= 0 && icomp_seg <= 59) {
        
            Entorno entornoRD = (Entorno) request.getSession().getAttribute("entorno");
            PropertiesUtil propUtil = new PropertiesUtil(entornoRD.getPerimetroPropertiesRD());

            int comprobacion = icomp_min * 60 + icomp_seg;

            Map<String, String> hprop = new HashMap();
            hprop.put(Constant.DESCARTE_INICIAL, descarteInicial);
            hprop.put(Constant.DESCARTE_FINAL, descarteFinal);
            hprop.put(Constant.DESCARTE_HOLGURA, descarteHolgura);
            hprop.put(Constant.COMPROBACION, "" + comprobacion);

            // Almacena en archivo configuracion de perimetro
            if (propUtil.establecerPropiedades(hprop)) {
                request.setAttribute("msg", "* Configuraci&oacute;n de per&iacute;metro almacenada correctamente.");
                request.setAttribute("msgType", "alert alert-success");
            } else {
                request.setAttribute("msg", "* Configuraci&oacute;n de per&iacute;metro no almacenada. "
                        + "Verifique asignaci&oacute;n de un <i>directorio ra&iacute;z</i> en configuraci&oacute;n entorno escritorio.");
                request.setAttribute("msgType", "alert alert-danger");
            }

            // Actualiza variable de configuracion de perimetro
            refrescarPropiedades(request, response);
        } else {
            request.setAttribute("msg", "* Valores de configuraci&oacute;n incorrectos.");
            request.setAttribute("msgType", "alert alert-danger");
        }

        return "/app/administracion/configuraPerimetro.jsp";
    }
    
    // Actualiza variable usada en vista de configuracion de perimetro 
    // con base en lo que reside en archivo
    public void refrescarPropiedades (HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        Entorno entornoRD = (Entorno) request.getSession().getAttribute("entorno");
        PropiedadesPerimetro propPerim = new PropiedadesPerimetro(entornoRD.getPerimetroPropertiesRD());
        
        session.setAttribute("perim", propPerim);
    }
}
