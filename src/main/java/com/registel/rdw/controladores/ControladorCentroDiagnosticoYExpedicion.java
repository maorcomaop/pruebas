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
import com.registel.rdw.datos.CentroDiagnosticoBD;
import com.registel.rdw.logica.CentroDiagnostico;
import java.util.ArrayList;


/*COMENTARIO*/
public class ControladorCentroDiagnosticoYExpedicion extends HttpServlet {
    
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
        if (requestURI.endsWith("/guardarCentroDiagnostico")){
            url = guardarCentroD(request, response);
        }
        if (requestURI.endsWith("/verCentroDiagnostico")){
            url=verCentroD(request, response);
        }
        if (requestURI.endsWith("/editarCentroDiagnostico")){
            url=editarCentroD(request, response);
        }        
        if (requestURI.endsWith("/eliminarCentroDiagnostico")){
            url=eliminarCentroD(request, response);
        }
        if (requestURI.endsWith("/consultaTodosCentroDiagnostico")){
            url=todosLosCentrosDiagnostico(request, response);
        }
        if (requestURI.endsWith("/consultaTodosCentroExpedicion")){
            url=todosLosCentrosExpedicion(request, response);
        }
        if (requestURI.endsWith("/todasLasCiudadesCda")){
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
        CentroDiagnostico h= new CentroDiagnostico();
        
         try {             
              h.setNombre(nombre);
              h.setFk_ciudad(Integer.parseInt(fk_ciudad));
              h.setTelefono(telefono);
              int id_soat = CentroDiagnosticoBD.insertOneCentroDDiagnostico(h);
              
                  switch (id_soat) {
                case 0:                    
                    request.setAttribute("idInfo", 2);                    
                    request.setAttribute("msg", "El Centro de diagnostico NO fue registrado");
                    break;                
                case 1:                    
                    select = new SelectBD(request);                    
                    session.setAttribute("select", select);       
                    request.setAttribute("idInfo", 1);                    
                    request.setAttribute("msg", "Se ha creado un nuevo Centro de diagnostico");                    
                    break;                
                default:
                    break;
            }
                
                           
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("a", -1);                           
         } 
        url = "/app/centro_diagnostico/nuevoCentroDiagnostico.jsp";        
        return url;
    }
     
     public String verCentroD (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        CentroDiagnostico c = new CentroDiagnostico();
        c.setId(Integer.parseInt(id));
        c.setEstado(new Short("1"));

        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();        
        CentroDiagnostico s = CentroDiagnosticoBD.selectCentro(c);
        if (s != null) {
                    session.setAttribute("centro_v", s);
                    session.setAttribute("select", select);
                    url = "/app/centro_diagnostico/unCentroDiagnostico.jsp";
            } else {
                url = "/app/centro_diagnostico/listadoCentroDiagnostico.jsp";
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
        
        url = "/app/centro_diagnostico/unCentroDiagnostico.jsp";
         CentroDiagnostico c = new CentroDiagnostico();
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
           return "/app/centro_diagnostico/listadoCentroDiagnostico.jsp";  
        }
        
            int resultado = CentroDiagnosticoBD.updateCentroFull(c);                        
            
             switch (resultado) {
                case 0:                    
                    request.setAttribute("msg", "No logr&oacute; actualizar el Centro de Diagnostico");
                    request.setAttribute("idInfo", 2);                   
                    break;                
                case 1:              
                    select = new SelectBD(request);
                    request.setAttribute("idInfo", 1);
                    session.setAttribute("select", select);       
                    CentroDiagnostico ce = CentroDiagnosticoBD.selectCentro(c);
                    if (ce != null) {
                        session.setAttribute("centro_v", ce);
                    }
                    request.setAttribute("msg", "Se ha modificado el Centro de Diagnostico");                   
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
        
        CentroDiagnostico c = new CentroDiagnostico();
        c.setEstado(Integer.parseInt( estado ));     
        c.setId(Integer.parseInt(id));        
        HttpSession session = request.getSession();
             
            int resultado = CentroDiagnosticoBD.updateStateCentro(c);
            switch (resultado) {
                case 0:
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute editar el soat ");
                    url = "/app/centro_diagnostico/listadoCentroDiagnostico.jsp";
                    break;
                case 1:
                    SelectBD select = new SelectBD(true);
                    session.setAttribute("select", select);
                    url = "/app/centro_diagnostico/listadoCentroDiagnostico.jsp";
                default:
                    break;
            }
         return url;
    }
      
      
    public String todosLosCentrosDiagnostico (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        CentroDiagnostico h = new CentroDiagnostico();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList retorno = CentroDiagnosticoBD.selectCentroDiagnosticoAll(h);
        session.setAttribute("allcda", retorno);
        url = "/app/centro_diagnostico/cda.jsp";                       
        return url;
    }  
       
    public String todosLosCentrosExpedicion (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        CentroDiagnostico h = new CentroDiagnostico();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList retorno = CentroDiagnosticoBD.selectCentroExpedicionAll(h);
        session.setAttribute("allce", retorno);
        url = "/app/centro_diagnostico/ce.jsp";                       
        return url;
    }  
         
          public String todasLasCiudades (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        CentroDiagnostico h = new CentroDiagnostico();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList retorno = CentroDiagnosticoBD.selectCityAll(h);
        session.setAttribute("allcitycda", retorno);
        url = "/app/centro_diagnostico/city.jsp";                       
        return url;
    }  
}
