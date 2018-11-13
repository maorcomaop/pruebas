/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.GpsInventarioBD;
import com.registel.rdw.datos.SelectBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.TarjetaOperacionBD;
import com.registel.rdw.logica.TarjetaOperacion;


/*COMENTARIO*/
public class ControladorTarjetaOperacion extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    private String lnk = "Para asignarle";
    
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
        if (requestURI.endsWith("/guardarDocumentosVehiculo")){
             url = guardarDocumentosVehiculo(request, response);
        }
        if (requestURI.endsWith("/verDocumentosVehiculo")){
            url=verDocumentosVehiculo(request, response);
        }
        if (requestURI.endsWith("/editarDocumentosVehiculo")){
            url=editarDocumentosVehiculo(request, response);
        }        
        if (requestURI.endsWith("/eliminarDocumentosVehiculo")){
            url=eliminarDocumentosVehiculo(request, response);
        }       
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
      
        /*METODO QUE GUARDA TARJETA SIM*/
    public String guardarDocumentosVehiculo (HttpServletRequest request,
            HttpServletResponse response) {        
        String fk_ce =  request.getParameter("fk_ce");                
        String cod =  request.getParameter("cod");                
        String mod =  request.getParameter("mod");        
        String obs =  request.getParameter("obs");                
        String f_vto =  request.getParameter("f_vto");                
        SelectBD select = null;
        String url="";
        int r =0;
        HttpSession session = request.getSession();        
        TarjetaOperacion h= new TarjetaOperacion();        
        
         try {             
              h.setFk_centro_exp(Integer.parseInt(fk_ce));              
              h.setCodigo(cod);              
              h.setModelo(mod);
              h.setObservaciones(obs);
              h.setFecha_vencimiento(f_vto);              
              r = TarjetaOperacionBD.insertDocumentosVehiculo(h);                 
               switch (r) {
                case 0:                    
                    request.setAttribute("idInfo", 2);                    
                    request.setAttribute("msg", "La tarjeta de operacion NO fue registrado");
                    break;                
                case 1:                    
                    select = new SelectBD(request);                    
                    session.setAttribute("select", select);       
                    request.setAttribute("idInfo", 1);                    
                    request.setAttribute("msg", "Se ha creado una nueva Tarjeta de operacion");                    
                    break;                
                default:
                    break;
            }             
         } catch (Exception e) {
             System.err.println(e.getMessage());
             url = "/app/tarjetaDeOperacion/nuevoTarjetaOperacion.jsp";        
         } 
        url = "/app/tarjetaDeOperacion/nuevoTarjetaOperacion.jsp";        
        return url;
    }
         
     public String verDocumentosVehiculo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        TarjetaOperacion c = new TarjetaOperacion();
        c.setId(Integer.parseInt(id));
        c.setEstado(new Short("1"));

        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();        
        TarjetaOperacion s = TarjetaOperacionBD.selectTarjetaOperacionVehiculoById(c);
        if (s != null) {
                    session.setAttribute("trjta_v", s);
                    session.setAttribute("select", select);
                    url = "/app/tarjetaDeOperacion/unTarjetaOperacion.jsp";
            } else {
                url = "/app/tarjetaDeOperacion/listadoTarjetaOperacion.jsp";
            }
         return url;
    }
     
      public String editarDocumentosVehiculo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";
        HttpSession session = request.getSession();               
        SelectBD select =null;
        int id = Integer.parseInt( request.getParameter("id_edit") );        
        String codigo =  request.getParameter("cod_edit");                  
        String model =  request.getParameter("mod_edit");
        String fk_aseguradora =  request.getParameter("fk_ce_edit");        
        String f_vencimiento =  request.getParameter("f_vto_edit");                
        String observaciones =  request.getParameter("obs_edit");        
        url = "/app/tarjetaDeOperacion/unTarjetaOperacion.jsp";
        TarjetaOperacion c = new TarjetaOperacion();
        try 
        {
            c.setId(id);    
            c.setCodigo(codigo);
            c.setModelo(model);
            c.setFk_centro_exp(Integer.parseInt(fk_aseguradora));
            c.setFecha_vencimiento(f_vencimiento);
            c.setObservaciones(observaciones);            
        } catch (Exception e) 
        {
           request.setAttribute("idInfo", 2);
           request.setAttribute("msg", "No se puede registrar la tarjeta de operacion");
           return "/app/tarjetaDeOperacion/listadoTarjetaOperacion.jsp";  
        }
        
            int resultado = TarjetaOperacionBD.updateTarjetaOperacionRecord(c);
            
             switch (resultado) {
                case 0:                    
                    request.setAttribute("msg", "No logr&oacute; actualizar la tarjeta de operacion");
                    request.setAttribute("idInfo", 2);                   
                    break;                
                case 1:              
                    select = new SelectBD(request);
                    request.setAttribute("idInfo", 1);
                    session.setAttribute("select", select);       
                    TarjetaOperacion to = TarjetaOperacionBD.selectTarjetaOperacionVehiculoById(c);
                    if (to != null) {
                        session.setAttribute("trjta_v", to);
                    }
                    request.setAttribute("msg", "Se ha modificado el la tarjeta de operacion");                   
                    break;                                
                default:
                    break;
            }                                         
        
        return url;
    }
     
      public String eliminarDocumentosVehiculo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url="";
        
        TarjetaOperacion c = new TarjetaOperacion();
        c.setEstado(Integer.parseInt( estado ));     
        c.setId(Integer.parseInt(id));        
        HttpSession session = request.getSession();
             
            int resultado = TarjetaOperacionBD.updateTarjetaOperacionRecordState(c);
            switch (resultado) {
                case 0:
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute editar la tarjeta de operacion ");
                    url = "/app/tarjetaDeOperacion/listadoTarjetaOperacion.jsp";
                    break;
                case 1:
                    SelectBD select = new SelectBD(true);
                    session.setAttribute("select", select);
                    url = "/app/tarjetaDeOperacion/listadoTarjetaOperacion.jsp";
                default:
                    break;
            }
         return url;
    }
     
}
