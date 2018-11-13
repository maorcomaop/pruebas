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
import com.registel.rdw.datos.HardwareBD;
import com.registel.rdw.logica.Hardware;
import java.sql.SQLException;
import java.util.ArrayList;


/*COMENTARIO*/
public class ControladorHardware extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultaHardware")){
            url = hardware(request, response);
        }        
        if (requestURI.endsWith("/guardarHardware")){
            url = guardarHardware(request, response);
        } 
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
             
    public String hardware (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        Hardware h = new Hardware();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = HardwareBD.selectAllHardware(h);                   
                session.setAttribute("hd", retorno);                
                url = "/app/hardware/hardware.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }
    
    
    
    /*METODO QUE GUARDA AL HARDWARE*/
    public String guardarHardware (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String nombre =  request.getParameter("nombre");
        
        String url="";               
        
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        Hardware h= new Hardware();
         try {                                
              h.setId(Integer.parseInt(id));
              h.setNombre(nombre);
                
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra registrar el hardware");
              url = "/app/hardware/hardware.jsp";
         }
        
        int retorno = HardwareBD.insertOneHardware(h);
         
         try {
             switch (retorno) {
                case 0:
                    request.setAttribute("msg", "No se logr&oacute registrar el hardware");
                    request.setAttribute("idInfo", 2);
                    url = "/app/hardware/hardware.jsp";
                    break;                
                case 1:                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se han registrado un nuevo hardware");
                    url = "/app/hardware/hardware.jsp";
                    break;                
                default:
                    break;
            }   
         } catch (Exception e) {
              System.err.println(e.getMessage());
         }
         return url;
    }
  
}
