/**
 * Clase controlador Alarma
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
import com.registel.rdw.datos.AlarmaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Alarma;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorAlarma extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    private String lnk = "Para asignarle un nueva alarma, haga click <a href='/RDW1/app/evento/nuevoEvento.jsp'>aqui.</a>";
    
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
        if (requestURI.endsWith("/guardarAlarma")) {
            try {
                url = guardarAlarma(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/editarAlarma")) {
            try {
                url = editarAlarma(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/verMasAlarma")) {
            try {
                url = verAlarma(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/eliminarAlarma")) {
            try {
                url = eliminarAlarma(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/ultimaAlarma")) {
            try {
                url = lastAlarma(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/repetidaAlarma")) {
            try {
                url = repeatAlarma(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        //response.sendRedirect("login.jsp");

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String guardarAlarma (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
                        
        String codigoAlarma = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        
        /*String tipo = request.getParameter("tipo");                        
        String cual_tipo = request.getParameter("cual_tipo");*/
        
        String unidad_medicion = request.getParameter("unidad");
        String cual_unidad_medicion = request.getParameter("cual_unidad");
        
        
        /*String prioridad = request.getParameter("prioridad");               
        String cual_prioridad = request.getParameter("cual_prioridad");*/               
                
        Alarma a = new Alarma();
       
        try
        {
            a.setCodigoAlarma(Integer.parseInt( codigoAlarma ));
            a.setNombreAlarma(nombre);
            a.setTipoAlarma("Hardware");            
            if (unidad_medicion.equals("Otro")) 
            {                
                a.setUnidadDeMedicion("Otro-"+cual_unidad_medicion);
            }
            else
            {
                a.setUnidadDeMedicion(unidad_medicion);
            }            
            a.setPrioridad(new Short("1") );
            a.setEstado(new Short("1"));
        }
        catch (Exception e) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la alarma");
            return "/app/alarma/listadoAlarma.jsp";
        }        
        
        
        HttpSession session = request.getSession();   
        SelectBD select = null;                     
        try {
            int resultado = AlarmaBD.insert(a);            
             switch (resultado) {
                case 0:
                    request.setAttribute("idInfo", 2);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No se logr&oacute registrar la nueva alarma");                                        
                    break;                
                case 1:
                    request.setAttribute("idInfo", 1);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                                   
                    request.setAttribute("msg", "Se ha creado un nueva alarma "+a.getNombreAlarma());                    
                    break;                
                default:
                    break;
            }
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return "/app/alarma/nuevoAlarma.jsp";
    }    
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String editarAlarma (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String url="";
        
        int id = Integer.parseInt( request.getParameter("id_edit") );
        int codigoAlarma = Integer.parseInt( request.getParameter("codigo_alarma_edit") );        
        String nombre = request.getParameter("nombre_edit");                
        String unidadDeMedicion = request.getParameter("unidad_edit");
        String cual_unidad = request.getParameter("cual_unidad_edit");        

        url = "/app/alarma/unAlarma.jsp";
        Alarma a = new Alarma();
                       
        try{
            a.setId(id);        
            a.setCodigoAlarma(codigoAlarma);
            a.setNombreAlarma(nombre);        
            a.setTipoAlarma("Hardware");
            a.setUnidadDeMedicion(unidadDeMedicion);
            
            if (unidadDeMedicion.equals("Otro")) 
            {                
                a.setUnidadDeMedicion("Otro-"+cual_unidad);
            }
            else
            {
                a.setUnidadDeMedicion(unidadDeMedicion);
            }
            a.setPrioridad(1);
            a.setEstado(1);
        }
        catch (Exception e) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la alarma");
            return "/app/alarma/listadoAlarma.jsp";
        }  
                
        SelectBD select = null;
                        
        try {
            int resultado = AlarmaBD.update(a);                        
            HttpSession session = request.getSession();           
            
             switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);                   
                    request.setAttribute("msg", "No logr&oacute; actualizar la alarma");
                    break;                
                case 1:
                    select = new SelectBD(request);
                    request.setAttribute("idInfo", 1);                    
                    session.setAttribute("select", select);
                    Alarma alarmaEncontrada = AlarmaBD.selectByOne(a);
                    if (alarmaEncontrada != null) {
                        session.setAttribute("alarma", alarmaEncontrada);
                        request.setAttribute("msg", "Se ha modificado la alarma");                   
                    }                    
                    break;                                                                
                default:
                    break;
            }                             
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorAlarma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR**/ 
    public String eliminarAlarma (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url="";
        
        Alarma a = new Alarma();
        a.setEstado(Integer.parseInt( estado ));     
        a.setId(Integer.parseInt(id));        
        HttpSession session = request.getSession();
        
        try {
                AlarmaBD.updateEstado(a);   
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                url = "/app/alarma/listadoAlarma.jsp";                        
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorAlarma.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
                
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verAlarma (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String id =  request.getParameter("id");                
        String url="";
        
        Alarma a = new Alarma();
        a.setId(Integer.parseInt(id));        
        a.setEstado(new Short("1"));
        
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        try {
                Alarma alarmaEncontrado = AlarmaBD.selectByOne(a);
               if (alarmaEncontrado != null) {
                session.setAttribute("alarma", alarmaEncontrado);
                session.setAttribute("select", select);
                url = "/app/alarma/unAlarma.jsp";
            } else {
                url = "/app/alarma/listadoAlarma.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorAlarma.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    
    
    public String lastAlarma (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String valor =  request.getParameter("valor");
        String url="";                   
        HttpSession session = request.getSession();
        
        try {
                ArrayList retorno = AlarmaBD.selectUltimaAlarma(valor);                   
                session.setAttribute("alarmas", retorno);                
                url = "/app/alarma/ultima.jsp";                        
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorAlarma.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    public String repeatAlarma (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String valor =  request.getParameter("id_alarma");
        String url="";                   
        HttpSession session = request.getSession();
        if (valor.equals("")) {
            
        }
        Alarma a = new Alarma();
        a.setId(Integer.parseInt(valor.trim()));
        try {
            int selectUltimaAlarma = AlarmaBD.repeatAlarma(a);
            if (selectUltimaAlarma > 0) {
                session.setAttribute("retorno", selectUltimaAlarma);
                url = "/app/alarma/repetida.jsp";
            }
            else{
                throw new Exception();
            }
        } catch (ExisteRegistroBD ex) {
            System.out.println("ERROR "+ex.getMessage());
        } catch (Exception ex) {
            session.setAttribute("retorno", "0");
            return url;
        }
         return url;
    }
}
