/**
 * Clase controlador Sub modulo
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
import com.registel.rdw.datos.SubModuloBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.SubModulo;
import com.registel.rdw.logica.SubItemModulo;
import com.registel.rdw.logica.Modulo;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorSubModulo extends HttpServlet {
    
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
        //String ulr1=request.getParameter("url1");        
        String requestURI = request.getRequestURI();
        
        String url = "/";
        if (requestURI.endsWith("/guardarSubModulo")) {
            url = guardarSubModulo(request, response);
        } else if (requestURI.endsWith("/editarSubModulo")) {
            url = editarSubModulo(request, response);
        } else if (requestURI.endsWith("/verMasSubModulo")) {
            url = verSubModulo(request, response);        
        } else if (requestURI.endsWith("/eliminarSubModulo")) {
            url = eliminarSubModulo(request, response);
        }
        //response.sendRedirect("login.jsp");
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO MODULO **/ 
    public String guardarSubModulo (HttpServletRequest request,
            HttpServletResponse response) {
                        
        
        String idmodulo = request.getParameter("modulo");
        String SubModulo = request.getParameter("nombreSubModulo");     
        String descripcion = request.getParameter("descripcion");     
        
                        
        String url = "/app/sub_modulos/nuevoSubModulo.jsp";
        HttpSession session = request.getSession();
        SelectBD select =null;
        
        SubModulo c = new SubModulo();
        c.setFk_modulo(Integer.parseInt(idmodulo));
        c.setNombre(SubModulo);
        c.setDescripcion(descripcion);
        c.setEstado(new Short("1"));
                       
        try {
                int resultado = SubModuloBD.insert(c);
                
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
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO REGISTRO**/ 
    public String editarSubModulo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="/app/sub_modulos/unSubModulo.jsp";
                
        int id_modulo = Integer.parseInt( request.getParameter("id_modulo") );
        int id_submodulo = Integer.parseInt( request.getParameter("id_submodulo") );
        String nombreSubModulo = request.getParameter("nombreSubModulo");           
        String descripcion = request.getParameter("descripcion");           
        String estado = request.getParameter("estado");                
        
        int resultado=0;
        SelectBD select =null; 
        HttpSession session = request.getSession();
        
                
        SubModulo c = new SubModulo();
        c.setId(id_submodulo);
        c.setFk_modulo(id_modulo);
        c.setNombre(nombreSubModulo);
         if (estado != null) 
        {
            c.setEstado(Integer.parseInt(estado));
        }
        else
        {
            c.setEstado(new Short("0"));
        }
         
        c.setDescripcion(descripcion);
                 
                        
        try {            
                resultado = SubModuloBD.update(c);  
            
            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 0);                   
                    request.setAttribute("msg", "No logr&oacute; establecer un nuevo modulo");                    
                    break;                
                case 1:
                    request.setAttribute("idInfo", 1);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    SubModulo submodulo = SubModuloBD.selectByOne(c);
                    if (submodulo != null) {
                        session.setAttribute("submodulo", submodulo);
                    }
                    request.setAttribute("msg", "Se ha modificado el item");                   
                    break;                                
                default:
                    break;
            }
           
           
            
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModulo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UN REGISTRO**/ 
    public String eliminarSubModulo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String estado =  request.getParameter("estado");                
        String url = "/app/sub_modulos/listadoSubModulo.jsp";  
        
        SubModulo c = new SubModulo();
        c.setId(Integer.parseInt(id));
        c.setEstado(Integer.parseInt( estado ));     
        
        HttpSession session = request.getSession();
        
        try {
                SubModuloBD.updateEstado(c);   
                 SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                                      
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModulo.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verSubModulo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        SubModulo c = new SubModulo();
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        
        c.setId(Integer.parseInt(id));
        //c.setEstado(new Short("1"));
        try {
               SubModulo submoduloEncontrado = SubModuloBD.selectByOne(c);
               Modulo m = new Modulo();
               m.setId( submoduloEncontrado.getFk_modulo());
               Modulo moduloEncontrado = SubModuloBD.selectByOne1(m);
               if (moduloEncontrado != null) {
                session.setAttribute("submodulo", submoduloEncontrado);
                session.setAttribute("modulo", moduloEncontrado);
                session.setAttribute("select", select);
                url = "/app/sub_modulos/unSubModulo.jsp";
            } else {
                url = "/app/sub_modulos/listadoSubModulo.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModulo.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
