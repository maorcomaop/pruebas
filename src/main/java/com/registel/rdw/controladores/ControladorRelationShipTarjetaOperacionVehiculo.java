/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.RelationShipTarjetaOperacionVehiculoBD;
import com.registel.rdw.logica.RelationShipTarjetaOperacionVehiculo;


/*COMENTARIO*/
public class ControladorRelationShipTarjetaOperacionVehiculo extends HttpServlet {
    
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
        if (requestURI.endsWith("/guardarRelacionSimGps")){
            url = guardarRelacionTarjetaOperacionVehiculo(request, response);            
        }
        if (requestURI.endsWith("/verTarjetaOVehiculo")){}
        if (requestURI.endsWith("/editarTarjetaOVehiculo")){}
        if (requestURI.endsWith("/eliminarTarjetaOVehiculo")){}
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
       
        /*METODO QUE GUARDA AL HARDWARE*/
    public String guardarRelacionTarjetaOperacionVehiculo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String fk_t =  request.getParameter("fk_tarjeta");        
        String fk_vh =  request.getParameter("fk_vh");
        String f_vto =  request.getParameter("f_vto");
        
        String url="";               
        
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        RelationShipTarjetaOperacionVehiculo h= new RelationShipTarjetaOperacionVehiculo();
         try {                                
              h.setFk_to(Integer.parseInt(fk_t));                            
              h.setFk_vh(Integer.parseInt(fk_vh));                
              h.setFecha_vencimiento(f_vto);
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra registrar el hardware");
              url = "/app/relacion_tarjeta_operacion_vh/guardar.jsp";
         }
        
        int retorno = RelationShipTarjetaOperacionVehiculoBD.insertOneRelationShipTarjetaOperacion(null);
        request.setAttribute("rtov", retorno); 
        return url;
    }
    
  
}
