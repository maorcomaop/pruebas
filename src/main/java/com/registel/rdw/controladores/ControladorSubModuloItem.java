/**
 * Clase controlador Sun Modulo Item
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
import com.registel.rdw.datos.SubModuloItemBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.SubModuloBD;
import com.registel.rdw.logica.SubModulo;
import com.registel.rdw.logica.SubItemModulo;
import com.registel.rdw.logica.Modulo;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorSubModuloItem extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    //private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
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
        if (requestURI.endsWith("/guardarSubModuloItem")) {
            url = guardarSubModuloItem(request, response);
        } else if (requestURI.endsWith("/editarSubModuloItem")) {
            url = editarSubModuloItem(request, response);
        } else if (requestURI.endsWith("/verMasSubModuloItem")) {
            url = verSubModuloItem(request, response);        
        } else if (requestURI.endsWith("/eliminarSubModuloItem")) {
            url = eliminarSubModuloItem(request, response);
        }else if (requestURI.endsWith("/pruebaSubModuloItem")) {
            url = prueba(request, response);
        }
        
        //response.sendRedirect("login.jsp");
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO **/ 
    public String guardarSubModuloItem (HttpServletRequest request,
            HttpServletResponse response) {
                        
        
        String idmodulo = request.getParameter("modulo");
        String idSubModulo = request.getParameter("submodulo");     
        String submoduloItemMenu = request.getParameter("nombreSubModuloItem");     
        
                        
        String url = "/app/sub_modulos_item/nuevoSubModuloItem.jsp";
        HttpSession session = request.getSession();
        SelectBD select = null;
        
        SubItemModulo c = new SubItemModulo();
        
        c.setFk_grupo(Integer.parseInt(idmodulo));
        c.setFk_submodulo(Integer.parseInt(idSubModulo));
        c.setNombre(submoduloItemMenu);
        c.setNombreLargo("No Aplica");        
        c.setCodigoDeAcceso("No Aplica");                
        c.setTeclaAcceso("No Aplica");
        c.setUbicacion("Ninguno");
        c.setRutaImagen("No Aplica");
        c.setSubGrupo("No Aplica");
        c.setPosicion(0);
        c.setPosicionSubGrupo(0);
        c.setEstado(new Short("1"));
              
                       
        try {
                int resultado = SubModuloItemBD.insert(c);
                
                switch (resultado) {
                case 0:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar el item correctamente");                                        
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado un nuevo item de menu");                    
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
    public String editarSubModuloItem (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="/app/sub_modulos_item/listadoSubModuloItem.jsp";
        
        int idModulo = Integer.parseInt( request.getParameter("id_modulo") );
        int idSubModulo = Integer.parseInt( request.getParameter("id_submodulo") );
        int idSubModuloItem = Integer.parseInt( request.getParameter("id_submoduloitem") );
        
        
        String nombreSubModulo = request.getParameter("nombreSubModulo");   
        String nombreSubModuloItem = request.getParameter("nombreSubModuloItem");   
        String estado = request.getParameter("estado");                
        
        int resultado=0;
        SelectBD select =null; 
        HttpSession session = request.getSession();
        
                
        SubItemModulo c = new SubItemModulo();
        c.setId(idSubModuloItem);
        c.setFk_grupo(idSubModulo);
        c.setFk_submodulo(idModulo);
        
        c.setNombre(nombreSubModuloItem);
        c.setNombreLargo("No aplica");
        c.setTeclaAcceso("No aplica");
        c.setCodigoDeAcceso("No aplica");
        c.setUbicacion("Ninguno");
        c.setRutaImagen("No aplica");
        c.setSubGrupo(nombreSubModulo);
        c.setPosicion(0);
        c.setPosicionSubGrupo(0);        
        
        
         if (estado != null) 
        {
            c.setEstado(Integer.parseInt(estado));
        }
        else
        {
            c.setEstado(new Short("0"));
        }
        
                 
                        
        try {            
                resultado = SubModuloItemBD.update(c);  
            
            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No logr&oacute; establecer un nuevo item");
                    request.setAttribute("idInfo", 0);                   
                    break;                
                case 1:
                    request.setAttribute("idInfo", 1);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    SubItemModulo submodulo = SubModuloItemBD.selectByOne(c);
                    if (submodulo != null) {
                        session.setAttribute("submodulo", submodulo);
                        request.setAttribute("msg", "Se ha modificado el item");                   
                    }                    
                    break;                                
                default:
                    break;
            }
           
           
            
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UN REGISTRO+**/ 
    public String eliminarSubModuloItem (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String estado =  request.getParameter("estado");                
        String url = "/app/sub_modulos_item/listadoSubModuloItem.jsp";  
        
        SubItemModulo c = new SubItemModulo();
        c.setId(Integer.parseInt(id));
        c.setEstado(Integer.parseInt( estado ));     
        
        HttpSession session = request.getSession();
        
        try {
                SubModuloItemBD.updateEstado(c);   
                 SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                                      
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verSubModuloItem (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        SubItemModulo c = new SubItemModulo();
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        
        c.setId(Integer.parseInt(id));
        //c.setEstado(new Short("1"));
        try {
               SubItemModulo submoduloEncontrado = SubModuloItemBD.selectByOne(c);
               SubModulo m = new SubModulo();
               m.setId( submoduloEncontrado.getFk_submodulo());
               
               SubModulo moduloEncontrado = SubModuloBD.selectByOne2(m);
               
               if ( (submoduloEncontrado != null) && (moduloEncontrado != null)  ){
                session.setAttribute("submodulo", submoduloEncontrado);
                session.setAttribute("modulo", moduloEncontrado);
                session.setAttribute("select", select);
                url = "/app/sub_modulos_item/unSubModuloItem.jsp";
            } else {
                url = "/app/sub_modulos_item/listadoSubModuloItem.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
         
    public String prueba (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url = "/app/sub_modulos_item/prueba.jsp";          
        
        SubModulo c = new SubModulo();
        c.setFk_modulo(Integer.parseInt(id));
        c.setEstado(1);     
        
        HttpSession session = request.getSession();
        
        try {
                ArrayList<SubModulo> SubModulos = SubModuloItemBD.SubModulos(c);   
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                session.setAttribute("modulo", SubModulos);
                                      
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
