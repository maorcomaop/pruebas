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
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.ConductorBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

/*COMENTARIO*/
public class ControladorConductor extends HttpServlet {
    
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
        if (requestURI.endsWith("/guardarConductor")) {
            try {
                url = guardarConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" "+ex.getMessage());
            }
        } else if (requestURI.endsWith("/editarConductor")) {
            try {
                url = editarConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" "+ex.getMessage());
            }
        } else if (requestURI.endsWith("/verMasConductor")) {
            try {        
                url = verConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" "+ex.getMessage());
            }
        } else if (requestURI.endsWith("/eliminarConductor")) {
            try {
                url = eliminarConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" "+ex.getMessage());
            }
        }
         else if (requestURI.endsWith("/encontrarConductor")) {
            try {
                url = findConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" "+ex.getMessage());
            }
        }
        //response.sendRedirect("login.jsp");
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String guardarConductor (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
                        
        
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");                
        String documento = request.getParameter("numero_documento");
        //String foto = request.getParameter("foto");       
        String tipo_doc = request.getParameter("tipo_doc");   
        
         HttpSession session = request.getSession();
            Usuario user = (Usuario) session.getAttribute("login");    
                
        Conductor c = new Conductor();
        try
        {
            c.setNombre(nombre);
            c.setApellido(apellido);        
            c.setCedula(documento);
            c.setCodEmpresa( user.getIdempresa() );
            c.setId_tipo_documento(Integer.parseInt(tipo_doc));
            c.setFoto("");
            c.setEstado(new Short("1"));
        }
        catch (Exception exx) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el conductor");
            return "/app/conductor/listadoConductor.jsp";
        }
        
        
        SelectBD select = null;
                       
        try {
            int resultado = ConductorBD.insert(c);
             switch (resultado) {
                case 0:                    
                    request.setAttribute("idInfo", 2);                    
                    request.setAttribute("msg", "El Conductor NO fue registrado");
                    break;                
                case 1:                    
                    select = new SelectBD(request);                    
                    session.setAttribute("select", select);       
                    request.setAttribute("idInfo", 1);                    
                    request.setAttribute("msg", "Se ha creado un nuevo Conductor");                    
                    break;                
                default:
                    break;
            }
            
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return "/app/conductor/nuevoConductor.jsp";
    }    
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String editarConductor (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String url="";
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("login");  
        
        int id = Integer.parseInt( request.getParameter("id_edit") );        
        String nombre = request.getParameter("nombre_edit");        
        String apellido = request.getParameter("apellido_edit");
        String tipo_doc = request.getParameter("tipo_doc_edit");
        String documento = request.getParameter("numero_documento_edit");
       url = "/app/conductor/unConductor.jsp";
        Conductor c = new Conductor();
        try 
        {
            c.setId(id);        
            c.setCodEmpresa(user.getIdempresa());
            c.setNombre(nombre);        
            c.setApellido(apellido);
            c.setCedula(documento);
            c.setFoto("");
            c.setId_tipo_documento(Integer.parseInt(tipo_doc));
            c.setEstado(new Short("1"));
        } catch (Exception e) 
        {
           request.setAttribute("idInfo", 2);
           request.setAttribute("msg", "No se puede registrar el conductor");
           return "/app/conductor/listadoConductor.jsp";  
        }
        
        
        SelectBD select =null;
               
        try {
            int resultado = ConductorBD.update(c);                        
            
             switch (resultado) {
                case 0:                    
                    request.setAttribute("msg", "No logr&oacute; actualizar el Conductos");
                    request.setAttribute("idInfo", 2);                   
                    break;                
                case 1:              
                    select = new SelectBD(request);
                    request.setAttribute("idInfo", 1);
                    session.setAttribute("select", select);       
                    Conductor conductor = ConductorBD.selectByOne(c);
                    if (conductor != null) {
                        session.setAttribute("conductor", conductor);
                    }
                    request.setAttribute("msg", "Se ha modificado el Conductor");                   
                    break;                                
                default:
                    break;
            }                               
            
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR**/ 
    public String eliminarConductor (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("cedula");
        String url="";
        
        Conductor c = new Conductor();
        c.setEstado(Integer.parseInt( estado ));     
        c.setId(Integer.parseInt(id));        
        HttpSession session = request.getSession();
        
        try {
             
            int resultado = ConductorBD.updateEstado(c);   
            switch (resultado) {
                case 0:
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar el conductor ");
                    break;
                case 1:
                    SelectBD select = new SelectBD(request);
                    session.setAttribute("select", select);
                    url = "/app/conductor/listadoConductor.jsp";
                default:
                    break;
            }
                
                
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    
    
    
    
     /**FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR**/ 
    public String findConductor (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
                
        String id =  request.getParameter("cedula");
        String url="/app/conductor/encontradoConductor.jsp";
        
        Conductor c = new Conductor();        
        c.setCedula(id);        
        HttpSession session = request.getSession();
        
        try {
            int repeatConductor = ConductorBD.repeatConductor(c);
            if (repeatConductor > 0) {
                session.setAttribute("retorno", repeatConductor);
            }
            else{
                 throw new Exception();
            }
        } catch (ExisteRegistroBD ex) {
            System.out.println("ERROR "+ex);
        } catch (Exception ex) {
            session.setAttribute("retorno", "0");
            return url;
        }
         return url;
    }
    
    
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verConductor (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String id =  request.getParameter("id");                
        String url="";
        Conductor c = new Conductor();
        c.setId(Integer.parseInt(id));
        c.setEstado(new Short("1"));

        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        try {
                Conductor conductorlEncontrado = ConductorBD.selectByOne(c);
               if (conductorlEncontrado != null) {
                session.setAttribute("conductor", conductorlEncontrado);
                session.setAttribute("select", select);
                url = "/app/conductor/unConductor.jsp";
            } else {
                url = "/app/conductor/listadoConductor.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
