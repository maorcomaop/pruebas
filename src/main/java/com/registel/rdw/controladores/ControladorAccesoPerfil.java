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
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorAccesoPerfil extends HttpServlet {
    
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
        if (requestURI.endsWith("/listarAccesoPerfil")) {
            try {
                url = listarAccesoPerfil(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorAccesoPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String listarAccesoPerfil (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
                        
        String codigoAlarma = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");                
        String unidad_medicion = request.getParameter("unidad");
        String prioridad = request.getParameter("prioridad");               
                
        Alarma a = new Alarma();
        a.setCodigoAlarma(Integer.parseInt( codigoAlarma ));
        a.setNombreAlarma(nombre);
        a.setTipoAlarma(tipo);
        a.setUnidadDeMedicion(unidad_medicion);
        a.setPrioridad(Integer.parseInt(prioridad) );
        a.setEstado(new Short("1"));
        
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
        String tipo = request.getParameter("tipo_edit");
        String unidadDeMedicion = request.getParameter("unidad_edit");
        int prioridad = Integer.parseInt( request.getParameter("prioridad_edit") );
        url = "/app/alarma/unAlarma.jsp";
        Alarma a = new Alarma();
        
        a.setId(id);        
        a.setCodigoAlarma(codigoAlarma);
        a.setNombreAlarma(nombre);        
        a.setTipoAlarma(tipo);
        a.setUnidadDeMedicion(unidadDeMedicion);
        a.setPrioridad(prioridad);
        a.setEstado(new Short("1"));
                
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
            Logger.getLogger(ControladorAccesoPerfil.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControladorAccesoPerfil.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControladorAccesoPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
