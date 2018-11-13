/**
 * Clase controlador Evento
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
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.EventoBD;
import com.registel.rdw.logica.Evento;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorEvento extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    //private String lnk = "Para asignarle un nuevo evento, haga click <a href='/RDW1/app/evento/nuevoEvento.jsp'>aqui.</a>";
    
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
        //String ulr1=request.getParameter("url1");        
        String requestURI = request.getRequestURI();
        
        String url = "/";
        if (requestURI.endsWith("/guardarEvento")) {
            url = guardarEvento(request, response);
        } else if (requestURI.endsWith("/editarEvento")) {
            url = editarEvento(request, response);
        } else if (requestURI.endsWith("/verMasEvento")) {
            url = verEvento(request, response);        
        } else if (requestURI.endsWith("/eliminarEvento")) {
            url = eliminarEvento(request, response);
        }
        //response.sendRedirect("login.jsp");
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String guardarEvento (HttpServletRequest request,
            HttpServletResponse response) {
                        
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String prioridad = request.getParameter("prioridad");
        String cantidad = request.getParameter("cantidad");       
        String tipo = request.getParameter("tipo");       
        SelectBD select = null;
                        
        Evento e = new Evento(); 
        try
        {
            e.setCodigoEvento(codigo);       
            e.setNombreGenerico(nombre);        
            e.setDescripcion(descripcion);       
            e.setPrioridad(Integer.parseInt(prioridad));        
            e.setCantidad(Integer.parseInt(cantidad));       
            e.setTipoEvento(tipo);
            e.setEstado(new Short("1"));
        }
        catch (Exception exx) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el evento");
            return "/app/alarma/listadoEventos.jsp";
        }           
        
        HttpSession session = request.getSession();                       
        try {
            
            int resultado = EventoBD.insert(e);
            
             switch (resultado) {
                case 0:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute; registrar el nuevo evento");                                        
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                                        
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado un nuevo Evento");                    
                    break;                
                default:
                    break;
            }   //session.setAttribute("select", select);
                //request.setAttribute("msg", "*  Se ha registrado exitosamente :"+ e.getNombreGenerico() +" como un nuevo evento.");            
            
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return "/app/evento/nuevoEvento.jsp";
    }    
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String editarEvento (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";        
        int id = Integer.parseInt( request.getParameter("id_edit") );
        String codigo =  request.getParameter("codigo_edit") ;        
        String nombre = request.getParameter("nombre_edit");        
        String descripcion = request.getParameter("descripcion_edit");
        String prioridad = request.getParameter("prioridad_edit");
        String cantidad = request.getParameter("cantidad_edit");
        String tipo = request.getParameter("tipo");
        url = "/app/evento/unEvento.jsp";
        
        Evento e = new Evento();        
         try
        {
            e.setId(id);
            e.setCodigoEvento(codigo);       
            e.setNombreGenerico(nombre);        
            e.setDescripcion(descripcion);       
            e.setPrioridad(Integer.parseInt(prioridad));        
            e.setCantidad(Integer.parseInt(cantidad));       
            e.setTipoEvento(tipo);
            e.setEstado(new Short("1"));
        }
        catch (Exception exx) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el evento");
            return "/app/alarma/listadoEventos.jsp";
        }        
        

        HttpSession session = request.getSession();       
        SelectBD select = null;
                        
        try {
            int resultado = EventoBD.update(e); 
            
              switch (resultado) {
                case 0:                 
                    request.setAttribute("msg", "No logr&oacute; actualizar el Evento");
                    request.setAttribute("idInfo", 2);                   
                    break;                
                case 1:
                    request.setAttribute("idInfo", 1);   
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    Evento evento = EventoBD.selectByOne(e);
                    if (evento != null) {
                        session.setAttribute("evento", evento);
                        request.setAttribute("msg", "Se ha modificado el Evento");                   
                    }
                    break;                                                                
                default:
                    break;
            }                           
           
            
            
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR**/ 
    public String eliminarEvento (HttpServletRequest request,
            HttpServletResponse response) {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url="";
        
        Evento c = new Evento();
        c.setEstado(Integer.parseInt( estado ));
        c.setId(Integer.parseInt(id));
        HttpSession session = request.getSession();
        
        try {
                EventoBD.updateEstado(c);   
                 SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                url = "/app/evento/listadoEventos.jsp";                        
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verEvento (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        Evento e = new Evento();
        e.setId(Integer.parseInt(id));
        e.setEstado(new Short("1"));        
        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);
        try {
                Evento eventoEncontrado = EventoBD.selectByOne(e);
               if (eventoEncontrado != null) {
                   session.setAttribute("select", select);
                session.setAttribute("evento", eventoEncontrado);               
                url = "/app/evento/unEvento.jsp";
            } else {
                url = "/app/evento/listadoEventos.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
