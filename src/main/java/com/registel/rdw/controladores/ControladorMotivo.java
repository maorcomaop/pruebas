/**
 * Clase controlador Motivo
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
import com.registel.rdw.datos.AuditoriaBD;
import com.registel.rdw.datos.MotivoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Motivo;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;


public class ControladorMotivo extends HttpServlet {
    
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
        if (requestURI.endsWith("/guardarMotivo")) {
            url = guardarMotivo(request, response);
        } else if (requestURI.endsWith("/editarMotivo")) {
            url = editarMotivo(request, response);
        } else if (requestURI.endsWith("/verMasMotivo")) {
            url = verMasMotivo(request, response);        
        } 
                
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
       /**FUNCION QUE PERMITE GUARDAR UN NUEVA AUDITORIA**/ 
    public String guardarMotivo (HttpServletRequest request,
            HttpServletResponse response) {
                        
        String fk_item_a_auditar = request.getParameter("id_auditoria");
        String id_usuario = request.getParameter("id_usuario");
        String tabla_auditar = request.getParameter("tabla_a_auditar");        
        String descripcion = request.getParameter("descripcion");
                
        Motivo c = new Motivo();
        try 
        {
            c.setFkAuditoria(Integer.parseInt(fk_item_a_auditar));
            c.setTablaAuditoria(tabla_auditar);
            c.setInformacionAdicional(descripcion);
            c.setUsuario(Integer.parseInt(id_usuario));
            c.setUsuarioBD("");
        }
        catch (Exception e) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar un motivo");
            return "/app/motivo/listadoMotivo.jsp";
        }
        
        
        HttpSession session = request.getSession();
        SelectBD select = null;   
        try {
            int resultado = MotivoBD.insert(c);
            switch (resultado) {
                case 0:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar la nuevo motivo");                                        
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado un nuevo motivo "+c.getTablaAuditoria());                    
                    break;                
                default:
                    break;
            }
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return "/app/motivo/nuevoMotivo.jsp";
    }    
   
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String editarMotivo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";
        
        int id = Integer.parseInt( request.getParameter("id_edit") );        
        String nombre = request.getParameter("nombre_edit");        
        String apellido = request.getParameter("apellido_edit");
        String tipo_doc = request.getParameter("tipo_doc_edit");
        String documento = request.getParameter("numero_documento_edit");
        
        Motivo c = new Motivo();
        c.setId(id);        
        
        
        SelectBD select = new SelectBD(request);
                        
        try {
            MotivoBD.update(c);                        
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            url = "/app/motivo/unMotivo.jsp";
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorMotivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
     
        
    public String verMasMotivo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String ID =  request.getParameter("id");                
        String url="";
        Motivo c = new Motivo();
        c.setId(Integer.parseInt(ID));                
                
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        try {
                Motivo MotivoEncontrado = MotivoBD.selectByOne(c);
               if (MotivoEncontrado != null) {
                session.setAttribute("motivo", MotivoEncontrado);
                session.setAttribute("select", select);
                url = "/app/motivo/unMotivo.jsp";
            } else {
                url = "/app/motivo/listadoMotivo.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorMotivo.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
