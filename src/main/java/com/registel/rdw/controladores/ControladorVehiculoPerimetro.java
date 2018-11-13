/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.VehiculoPerimetro;
import com.registel.rdw.utils.VehiculoPerimetroDALXML;
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
public class ControladorVehiculoPerimetro extends HttpServlet {
    
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
        String url = "";
        
        if (requestURI.endsWith("/consultarVP")) {
            url = consultarVehiculosPerimetro(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
    
    public String consultarVehiculosPerimetro (HttpServletRequest request,
            HttpServletResponse response) {
         
        HttpSession session = request.getSession();
        Entorno entornoRD = (Entorno) session.getAttribute("entorno");
        
        if (entornoRD == null) {
            String web_path = getServletContext().getRealPath("");
            entornoRD = new Entorno(web_path);
        }
        
        String directorioRD = entornoRD.getUbicacionRegisdata();
        
        VehiculoPerimetroDALXML fileXML = new VehiculoPerimetroDALXML();
        ArrayList<VehiculoPerimetro> lstvp = fileXML.obtenerVehiculosPerimetrosXML(directorioRD);
        
        session.setAttribute("lstvp", lstvp);
        
        return "/app/administracion/visualizaPerimetro.jsp";
    }
}
