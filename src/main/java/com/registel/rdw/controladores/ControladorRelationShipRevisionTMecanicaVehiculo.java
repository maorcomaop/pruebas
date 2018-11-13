/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.RelationShipRevisionVehiculoBD;
import com.registel.rdw.logica.RelationShipRevisionTMVehiculo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/*COMENTARIO*/
public class ControladorRelationShipRevisionTMecanicaVehiculo extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String nada = request.getParameter("r");       
        HttpSession session = request.getSession(false);
        String url = "/";
        
        if (session == null || 
            session.getAttribute("login") == null || 
            session.getAttribute("select") == null || 
            Expire.check(session.getCreationTime())) {
            url = "/index.jsp";
        } 
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {        
        String requestURI = request.getRequestURI();        
        String url = "/";
        if (requestURI.endsWith("/guardarRevisionVehiculo")){
            url = guardarRelacionRevisionVehiculo(request, response);
        }
        if (requestURI.endsWith("/verRevisionVehiculo")){}        
        if (requestURI.endsWith("/editarRevisionVehiculo")){} 
        if (requestURI.endsWith("/eliminarRevisionVehiculo")){} 
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    public String guardarRelacionRevisionVehiculo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String fk_sim =  request.getParameter("fk_sim");        
        String fk_gps =  request.getParameter("fk_vh");
        String f_vto =  request.getParameter("f_vencimiento");
        
        String url="";
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        RelationShipRevisionTMVehiculo h= new RelationShipRevisionTMVehiculo();
         try {                                
              h.setFk_revision(Integer.parseInt(fk_sim));              
              h.setFk_vh(Integer.parseInt(fk_gps)); 
              h.setFecha_vencimiento(f_vto);
              
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra registrar la relacion");
              url = "/app/relacion_revision_vh/guardar.jsp";
         }
        int retorno = RelationShipRevisionVehiculoBD.insertOneRelationShipRevisionVehiculo(h);
        request.setAttribute("rrvh", retorno);
        return url;
    }
  
  
}

