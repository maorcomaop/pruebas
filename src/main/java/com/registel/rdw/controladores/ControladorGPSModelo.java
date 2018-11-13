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
import com.registel.rdw.datos.GpsModeloBD;
import com.registel.rdw.logica.GpsModelo;


/*COMENTARIO*/
public class ControladorGPSModelo extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultaModeloGPS")){
            url = modelo(request, response);
        }
         if (requestURI.endsWith("/consultaModelosGPS")){
            url = modelos(request, response);
        }
        if (requestURI.endsWith("/guardarModeloGPS")){
            url = guardarModelo(request, response);
        }        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       public String modelo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsModelo h = new GpsModelo();       
        h.setFk_marca(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        ArrayList retorno = GpsModeloBD.selectOneModel(h);                   
        session.setAttribute("modl", retorno);                
        url = "/app/modeloGPS/modelo.jsp";               
        return url;
    }  
       public String modelos (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsModelo h = new GpsModelo();       
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        ArrayList retorno = GpsModeloBD.selectAllModels(h);                   
        session.setAttribute("modls", retorno);                
        url = "/app/modeloGPS/modelos.jsp";                       
         return url;
    }  
       
        /*METODO QUE GUARDA AL HARDWARE*/
    public String guardarModelo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String modelo =  request.getParameter("id");
        String marca =  request.getParameter("nombre");
        String serial =  request.getParameter("nombre");
        
        String url="";               
        
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        GpsModelo h= new GpsModelo();
         try {                                
              h.setNombre(marca);
              h.setDescripcion(modelo);              
                
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra registrar el modelo");
              url = "/app/modeloGPS/modelos.jsp";
         }
        
        int retorno = GpsModeloBD.insertOneGPS(h);
         
         try {
             switch (retorno) {
                case 0:
                    request.setAttribute("msg", "No se logr&oacute registrar el GPS");
                    request.setAttribute("idInfo", 2);
                    url = "/app/inventarioGPS/nuevoInventario.jsp";
                    break;                
                case 1:                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se han registrado un nuevo GPS");
                    url = "/app/inventarioGPS/nuevoInventario.jsp";
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
