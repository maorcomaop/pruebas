/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.GpsInventarioBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.datos.RevisionTMecanicaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.RevisionTMecanica;




/*COMENTARIO*/
public class ControladorRevisionTMecanica extends HttpServlet {
    
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
        
        if (requestURI.endsWith("/guardarTMecanica")){
            url = guardarTMecanica(request, response);
        }        
        if (requestURI.endsWith("/verTMecanica")){
            url=verRevisionTMecanica(request, response);
        }
        if (requestURI.endsWith("/editarTMecanica")){
            url=editarRevision(request, response);
        }
        if (requestURI.endsWith("/eliminarTMecanica")) {
            url = eliminarRevisionTMecanica(request, response);
        }                
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
        /*METODO QUE GUARDA TARJETA SIM*/
    public String guardarTMecanica (HttpServletRequest request,
            HttpServletResponse response) {        
        String codigo =  request.getParameter("cod");                
        String fk_centro =  request.getParameter("fk_c_d");                
        String f_vencimiento =  request.getParameter("f_vto");                
        String observaciones =  request.getParameter("obs");        
        String url="";        
        SelectBD select = null;
        HttpSession session = request.getSession();        
        RevisionTMecanica h= new RevisionTMecanica();
        
         try {             
              
              h.setFk_centro_diag(Integer.parseInt(fk_centro));              
              h.setCodigo(codigo);              
              h.setFecha_vencimiento(f_vencimiento);
              h.setObservaciones(observaciones);                                
              int retorno = RevisionTMecanicaBD.insertOneRevision(h);
               switch (retorno) {
                case 0:                    
                    request.setAttribute("idInfo", 2);                    
                    request.setAttribute("msg", "La Revision NO fue registrado");
                    break;                
                case 1:                    
                    select = new SelectBD(request);                    
                    session.setAttribute("select", select);       
                    request.setAttribute("idInfo", 1);                    
                    request.setAttribute("msg", "Se ha creado un nueva Revision ");                    
                    break;                
                default:
                    break;
            }                  
         } catch (Exception e) {
             System.err.println(e.getMessage());              
         } 
        url = "/app/revisionTMecanica/nuevoRevisionTMecanica.jsp";            
        return url;
    }
    
    
    
    public String verRevisionTMecanica (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        RevisionTMecanica c = new RevisionTMecanica();
        c.setId(Integer.parseInt(id));
        c.setEstado(new Short("1"));

        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();        
        RevisionTMecanica s = RevisionTMecanicaBD.selectRevisionTMecanica(c);
        if (s != null) {
                    session.setAttribute("rev_v", s);
                    session.setAttribute("select", select);
                    url = "/app/revisionTMecanica/unaRevisionTMecanica.jsp";
            } else {
                url = "/app/revisionTMecanica/listadoRevisionTMecanica.jsp";
            }
         return url;
    }
    
    
    public String editarRevision (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";
        HttpSession session = request.getSession();               
        SelectBD select =null;
        int id = Integer.parseInt( request.getParameter("id_edit") );        
        String codigo =  request.getParameter("cod_edit");                
        String fk_cda =  request.getParameter("fk_c_d_edit");                
        String f_vencimiento =  request.getParameter("f_vto_edit");                
        String observaciones =  request.getParameter("obs_edit");        
        url = "/app/revisionTMecanica/unaRevisionTMecanica.jsp";
        RevisionTMecanica c = new RevisionTMecanica();
        try 
        {
            c.setId(id);    
            c.setCodigo(codigo);
            c.setFk_centro_diag(Integer.parseInt(fk_cda));
            c.setFecha_vencimiento(f_vencimiento);
            c.setObservaciones(observaciones);            
        } catch (Exception e) 
        {
           request.setAttribute("idInfo", 2);
           request.setAttribute("msg", "No se puede registrar el soat");
           return "/app/revisionTMecanica/listadoRevisionTMecanica.jsp";  
        }
        
            int resultado = RevisionTMecanicaBD.updateRevisionFull(c);
            
             switch (resultado) {
                case 0:                    
                    request.setAttribute("msg", "No logr&oacute; actualizar el revision");
                    request.setAttribute("idInfo", 2);                   
                    break;                
                case 1:              
                    select = new SelectBD(request);
                    request.setAttribute("idInfo", 1);
                    session.setAttribute("select", select);       
                    RevisionTMecanica conductor = RevisionTMecanicaBD.selectRevisionTMecanica(c);
                    if (conductor != null) {
                        session.setAttribute("rev_v", conductor);
                    }
                    request.setAttribute("msg", "Se ha modificado la revision tecnico mecanica");                   
                    break;                                
                default:
                    break;
            }                                         
        
        return url;
    }
    
    
    
      public String eliminarRevisionTMecanica (HttpServletRequest request,
            HttpServletResponse response) {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url="";
        
        RevisionTMecanica c = new RevisionTMecanica();
        c.setEstado(Integer.parseInt( estado ));     
        c.setId(Integer.parseInt(id));        
        HttpSession session = request.getSession();
             
            int resultado = RevisionTMecanicaBD.updateRevisionState(c);
            switch (resultado) {
                case 0:
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute editar el soat ");
                    url = "/app/revisionTMecanica/listadoRevisionTMecanica.jsp";
                    break;
                case 1:
                    SelectBD select = new SelectBD(true);
                    session.setAttribute("select", select);
                    url = "/app/revisionTMecanica/listadoRevisionTMecanica.jsp";
                default:
                    break;
            }
         return url;
    }
}
