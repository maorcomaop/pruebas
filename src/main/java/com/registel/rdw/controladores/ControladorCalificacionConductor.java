/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.logica.ConfCalificacionConductor;
import com.registel.rdw.logica.RendimientoConductor;
import com.registel.rdw.utils.ParametrosReporte;
import com.registel.rdw.utils.PrintOutExcel;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorCalificacionConductor extends HttpServlet {
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) {
        
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String url = "";
        
        if (requestURI.endsWith("/reporteCalificacionConductor")) {
            reporteCalificacionConductor(request, response);
        }
        
        if (url != "") {
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }
    
    // Procesa solicitud de extraccion de reporte de calificacion de
    // conductor. Crea objeto para conservar parametros de consulta, 
    // inicia generacion e impresion de reporte en archivo excel.
    public void reporteCalificacionConductor(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String tituloReporte    = request.getParameter("tituloReporte");
        String tipoReporte      = request.getParameter("tipoReporte");        
        String nombreReporte    = request.getParameter("nombreReporte");
        String fechaInicio      = request.getParameter("fechaInicio");
        String fechaFinal       = request.getParameter("fechaFinal");
        String listaConductores = request.getParameter("listaConductores");
        
        ParametrosReporte pr = new ParametrosReporte();
        pr.setTipoReporte(Restriction.getNumber(tipoReporte));
        pr.setNombreReporte(nombreReporte);
        pr.setFechaInicioStr(fechaInicio);
        pr.setFechaFinalStr(fechaFinal);
        pr.setListaConductores(listaConductores);
        pr.setTituloReporte(tituloReporte);
        
        PrintOutExcel poe = new PrintOutExcel();
        poe.print(request, response, pr);
    }    
}
