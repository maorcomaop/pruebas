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
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.CentroExpedicionBD;
import com.registel.rdw.logica.CentroExpedicion;
import java.util.ArrayList;


/*COMENTARIO*/
public class ControladorCentroExpedicion extends HttpServlet {
    
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
       if (requestURI.endsWith("/guardarCentroExpedicion")){
            url = guardarCentroD(request, response);
        }
        if (requestURI.endsWith("/verCentroExpedicion")){
            url=verCentroD(request, response);
        }
        if (requestURI.endsWith("/editarCentroExpedicion")){
            url=editarCentroD(request, response);
        }        
        if (requestURI.endsWith("/eliminarCentroExpedicion")){
            url=eliminarCentroD(request, response);
        }
        if (requestURI.endsWith("/todosLosCentroExpedicion")){
            url=todosLosCentros(request, response);
        }
        if (requestURI.endsWith("/todasLasCiudadesCe")){
            url=todasLasCiudades(request, response);
        }
        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
        public String guardarCentroD (HttpServletRequest request,
            HttpServletResponse response) {                
        String fk_ciudad =  request.getParameter("fk_ciudad");                
        String nombre =  request.getParameter("nombre");
        String telefono =  request.getParameter("tele");        
        
        String url="";        
        HttpSession session = request.getSession(); 
        SelectBD select = null;
        CentroExpedicion h= new CentroExpedicion();
        
         try {             
              h.setNombre(nombre);
              h.setFk_ciudad(Integer.parseInt(fk_ciudad));
              h.setTelefono(telefono);
              int id_soat = CentroExpedicionBD.insertOneCentroDExpedicion(h);
              
                  switch (id_soat) {
                case 0:                    
                    request.setAttribute("idInfo", 2);                    
                    request.setAttribute("msg", "El Centro de expedicion NO fue registrado");
                    break;                
                case 1:                    
                    select = new SelectBD(request);                    
                    session.setAttribute("select", select);       
                    request.setAttribute("idInfo", 1);                    
                    request.setAttribute("msg", "Se ha creado un nuevo Centro de expedicion");                    
                    break;                
                default:
                    break;
            }
                
                           
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("a", -1);                           
         } 
        url = "/app/centro_expedicion/nuevoCentroExpedicion.jsp";        
        return url;
    }
     
     public String verCentroD (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        CentroExpedicion c = new CentroExpedicion();
        c.setId(Integer.parseInt(id));
        c.setEstado(new Short("1"));

        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();        
        CentroExpedicion s = CentroExpedicionBD.selectCentro(c);
        if (s != null) {
                    session.setAttribute("centro_v", s);
                    session.setAttribute("select", select);
                    url = "/app/centro_expedicion/unCentroExpedicion.jsp";
            } else {
                url = "/app/centro_expedicion/listadoCentroExpedicion.jsp";
            }
         return url;
    }
       
     public String editarCentroD (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";
        HttpSession session = request.getSession();               
        SelectBD select =null;
        int id = Integer.parseInt( request.getParameter("id_edit") );        
        String nombre =  request.getParameter("nombre_edit");                
        String fk_ciudad =  request.getParameter("fk_ciudad_edit");                
        String telefono =  request.getParameter("telefono_edit");                
        
        url = "/app/centro_expedicion/unCentroExpedicion.jsp";
         CentroExpedicion c = new CentroExpedicion();
        try 
        {
            c.setId(id);    
            c.setNombre(nombre);
            c.setFk_ciudad(Integer.parseInt(fk_ciudad));
            c.setTelefono(telefono);
            
        } catch (Exception e) 
        {
           request.setAttribute("idInfo", 2);
           request.setAttribute("msg", "No se puede registrar el Centro de Diagnostico");
           return "/app/centro_expedicion/listadoCentroExpedicion.jsp";  
        }
        
            int resultado = CentroExpedicionBD.updateCentroFull(c);                        
            
             switch (resultado) {
                case 0:                    
                    request.setAttribute("msg", "No logr&oacute; actualizar el Centro de Expedicion");
                    request.setAttribute("idInfo", 2);                   
                    break;                
                case 1:              
                    select = new SelectBD(request);
                    request.setAttribute("idInfo", 1);
                    session.setAttribute("select", select);       
                    CentroExpedicion ce = CentroExpedicionBD.selectCentro(c);
                    if (ce != null) {
                        session.setAttribute("centro_v", ce);
                    }
                    request.setAttribute("msg", "Se ha modificado el Centro de Expedicion");                   
                    break;                                                                
                default:
                    break;
            }                                         
        
        return url;
    }
     
      public String eliminarCentroD (HttpServletRequest request,
            HttpServletResponse response) {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url="";
        
        CentroExpedicion c = new CentroExpedicion();
        c.setEstado(Integer.parseInt( estado ));     
        c.setId(Integer.parseInt(id));        
        HttpSession session = request.getSession();
             
            int resultado = CentroExpedicionBD.updateStateCentro(c);
            switch (resultado) {
                case 0:
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute editar el Centro de expedicion ");
                    url = "/app/centro_expedicion/listadoCentroExpedicion.jsp";
                    break;
                case 1:
                    SelectBD select = new SelectBD(true);
                    session.setAttribute("select", select);
                    url = "/app/centro_expedicion/listadoCentroExpedicion.jsp";
                default:
                    break;
            }
         return url;
    }
     
     public String todosLosCentros (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        CentroExpedicion h = new CentroExpedicion();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList retorno = CentroExpedicionBD.selectCentroExpedicionAll(h);
        session.setAttribute("allcda", retorno);
        url = "/app/centro_expedicion/ce.jsp";                       
        return url;
    }  
   
     
        public String todasLasCiudades (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        CentroExpedicion h = new CentroExpedicion();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList retorno = CentroExpedicionBD.selectCityAll(h);
        session.setAttribute("allcityce", retorno);
        url = "/app/centro_expedicion/city.jsp";                       
        return url;
    }  
}
