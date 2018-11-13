/**
 * Clase controlador Modulo
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 01/12/2016 10:26:20 AM
 */
package com.registel.rdw.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.ModuloBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Modulo;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorModulo extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
//    private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
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
        if (requestURI.endsWith("/guardarModulo")) {
            url = guardarModulo(request, response);
        } else if (requestURI.endsWith("/editarModulo")) {
            url = editarModulo(request, response);
        } else if (requestURI.endsWith("/verMasModulo")) {
            url = verModulo(request, response);        
        } else if (requestURI.endsWith("/eliminarModulo")) {
            url = eliminarModulo(request, response);
        }
  
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR **/ 
    public String guardarModulo (HttpServletRequest request,
            HttpServletResponse response) {
                        
        String nombre = request.getParameter("nombre");        
        
        String url = "/app/modulo/nuevoModulo.jsp";
        HttpSession session = request.getSession();
        SelectBD select =null;
        Modulo c = new Modulo();
        c.setNombre(nombre);
        c.setTeclaDeAcceso("0");
        c.setRuta_imagen("ninguno");
        c.setPosicion(0);        
        c.setEstado(new Short("1"));       
              
                       
        try {
                int resultado = ModuloBD.insert(c);
                
                switch (resultado) {
                case 0:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar el modulo correctamente");                                        
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado un nuevo modulo");
                    break;                
                default:
                    break;
            }  
            
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return url;
    }    
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String editarModulo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="/app/modulo/unModulo.jsp";
        
        int id = Integer.parseInt( request.getParameter("id_edit") );
        String nombre = request.getParameter("nombre");   
        String estado = request.getParameter("estado");                
        
        int resultado=0;
        SelectBD select =null; 
        HttpSession session = request.getSession();              
        
        Modulo c = new Modulo();        
        c.setId(id);                
        c.setNombre(nombre);        
        
         if (estado != null) 
        {
            c.setEstado(Integer.parseInt(estado));
        }
        else
        {
            c.setEstado(new Short("0"));
        }
        
         c.setPosicion(0);
         c.setRuta_imagen("ninguno");
         c.setTeclaDeAcceso("0");
        
                        
        try {            
                resultado = ModuloBD.update(c);  
            
            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No logr&oacute; establecer un nuevo modulo");
                    request.setAttribute("idInfo", 0);                   
                    break;                
                case 1:
                    request.setAttribute("idInfo", 1);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    Modulo categoria = ModuloBD.selectByOne(c);
                    if (categoria != null) {
                        session.setAttribute("modulo", categoria);
                        request.setAttribute("msg", "Se ha modificado el nuevo modulo");                   
                    }
                    
                    break;                
                default:
                    break;
            }            
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorModulo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR**/ 
    public String eliminarModulo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String estado =  request.getParameter("estado");                
        String url = "/app/modulo/listadoModulos.jsp";  
        
        Modulo c = new Modulo();
        c.setId(Integer.parseInt(id));
        c.setEstado(Integer.parseInt( estado ));     
        
        HttpSession session = request.getSession();
        
        try {
                ModuloBD.updateEstado(c);   
                 SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                                      
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorModulo.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }    
    
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verModulo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        Modulo c = new Modulo();
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        
        c.setId(Integer.parseInt(id));        
        try {
                Modulo moduloEncontrado = ModuloBD.selectByOne(c);
               if (moduloEncontrado != null) {
                session.setAttribute("modulo", moduloEncontrado);
                session.setAttribute("select", select);
                url = "/app/modulo/unModulo.jsp";
            } else {
                url = "/app/modulo/unModulo.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorModulo.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
