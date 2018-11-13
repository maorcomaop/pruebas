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
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.RelationShipHardwareGpsCarBD;
import com.registel.rdw.datos.GpsInventarioBD;
import com.registel.rdw.logica.Hardware;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;


/*COMENTARIO*/
public class ControladorRelationShipHardwareGpsCar extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultaRelacionHarwareGpsVeh")){
            url = consultaRelacionesHardwareGpsVeh(request, response);
        }
        if (requestURI.endsWith("/guardarRelacionHarwareGpsVeh")){
            url = guardarRelacionHardwareGpsVeh(request, response);
        }        
        if (requestURI.endsWith("/eliminarRelacionHarwareGpsVeh")){
            url =eliminarRelacionHardwareGpsVeh(request, response);
        }
        if (requestURI.endsWith("/consultaVehiculosSinRelacionar")){
            url =consultaVehiculoSinRelacion(request, response);
        }        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       public String consultaRelacionesHardwareGpsVeh (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        RelationShipHardwareGpsCar h = new RelationShipHardwareGpsCar();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = RelationShipHardwareGpsCarBD.selectOwnerAll(h);                   
                session.setAttribute("inv", retorno);                
                url = "/app/inventarioGPS/inventarios.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }  
       
        /*METODO QUE GUARDA AL HARDWARE*/
    public String guardarRelacionHardwareGpsVeh (HttpServletRequest request,
            HttpServletResponse response) {
        
        String fk_hardware =  request.getParameter("fk_hd");
        String fk_vehiculo =  request.getParameter("fk_vh");
        String fk_gps =  request.getParameter("fk_gps");
        
        String url="";               
        
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        RelationShipHardwareGpsCar h= new RelationShipHardwareGpsCar();
         try {                                
              h.setFk_hardware(Integer.parseInt(fk_hardware));
              h.setFk_vehiculo(Integer.parseInt(fk_vehiculo));
              h.setFk_gps(fk_gps);
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra registrar el hardware");
              url = "/app/inventarioGPS/nuevoInventario.jsp";
         }
        
        int retorno = RelationShipHardwareGpsCarBD.insertOneRelationShipHardwareGpsCarReturnId(h);
         /*
         try {
             switch (retorno) {
                case 0:
                    request.setAttribute("msg", "No se logr&oacute registrar el GPS");
                    request.setAttribute("idInfo", 2);
                    url = "/app/inventarioGPS/nuevoInventario.jsp";
                    break;                
                case 1:                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se han registrado un nuevo GPS");
                    url = "/app/inventarioGPS/nuevoInventario.jsp";
                    break;                
                default:
                    break;
            }   
         } catch (Exception e) {
              System.err.println(e.getMessage());
         }*/
         return url;
    }
    
    public String eliminarRelacionHardwareGpsVeh (HttpServletRequest request,
            HttpServletResponse response) {
                        
        String codigo =  request.getParameter("codigo");        
        String url="";                       
        HttpSession session = request.getSession();
        
        RelationShipHardwareGpsCar h= new RelationShipHardwareGpsCar();
        RelationShipHardwareGpsCar g= new RelationShipHardwareGpsCar();
        g.setId(Integer.parseInt(codigo));
        RelationShipHardwareGpsCar r = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(g);
         try {                            
              h.setFk_gps(r.getFk_gps());                
              h.setEstado(0);
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra actualizar");
              url = "/app/hardware/actualizar.jsp";
         }        
         int retorno = RelationShipHardwareGpsCarBD.updateOneRelationshipStateByFkGps(null);
         /*int retorno1 = GpsInventarioBD.updateStateGpsRegister(g);*/
         request.setAttribute("inv", retorno);
         url = "/app/hardware/actualizar.jsp";         
         return url;
    }
    
    
    
    
      public String consultaVehiculoSinRelacion (HttpServletRequest request,
            HttpServletResponse response) {        
        String id =  request.getParameter("id");
        String url="";                   
        RelationShipHardwareGpsCar h= new RelationShipHardwareGpsCar();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList<Movil> retorno = RelationShipHardwareGpsCarBD.searchAllMoviles(h);
        session.setAttribute("inv", retorno);
        url = "/app/inventarioGPS/vehiculoSinRelacion.jsp";
        return url;
    }  
}
