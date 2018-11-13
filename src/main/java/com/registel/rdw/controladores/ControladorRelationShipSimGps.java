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
import com.registel.rdw.datos.RelationShipSimGpsBD;
import com.registel.rdw.datos.GpsInventarioBD;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.TarjetaSim;
import com.registel.rdw.logica.RelationShipSimGps;


/*COMENTARIO*/
public class ControladorRelationShipSimGps extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultaRelacionesSimGps")){
            url = consultaRelacionesSimGps(request, response);
        }
        if (requestURI.endsWith("/consultaRelacionSimGps")){
            url = consultaRelacionesSimGps(request, response);
        }        
        if (requestURI.endsWith("/guardarRelacionSimGps")){
            url = guardarRelacionSimGps(request, response);            
        } 
        if (requestURI.endsWith("/eliminarRelacionSimGps")){
            url =eliminarRelacionSimGps(request, response);
        } 
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       public String consultaRelacionesSimGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        RelationShipSimGps h = new RelationShipSimGps();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = RelationShipSimGpsBD.selectRelationShipSimGpsAll(h);                   
                session.setAttribute("inv", retorno);                
                url = "/app/relacion_sim_gps/relaciones_sim_gps.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }  
        
       public String consultaRelacionSimGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsRegistrado h = new GpsRegistrado();
        h.setId(id);
        HttpSession session = request.getSession();        
        RelationShipSimGps retorno = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(h);
        session.setAttribute("inv", retorno);                
        url = "/app/relacion_sim_gps/relaciones_sim_gps.jsp";                       
        return url;
    } 
       
        /*METODO QUE GUARDA AL HARDWARE*/
    public String guardarRelacionSimGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String fk_sim =  request.getParameter("fk_sim");        
        String fk_gps =  request.getParameter("fk_gps");
        
        String url="";               
        
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        RelationShipSimGps h= new RelationShipSimGps();
         try {                                
              h.setFk_sim(Integer.parseInt(fk_sim));              
              h.setFk_gps(fk_gps);                
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "No se logra registrar el hardware");
              url = "/app/inventarioGPS/nuevoInventario.jsp";
         }
        
        int retorno = RelationShipSimGpsBD.insertOneRelationShipSimGps(h);
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
    
    public String eliminarRelacionSimGps (HttpServletRequest request,
            HttpServletResponse response) {
                        
        String cel =  request.getParameter("cel");        
        String url="";                       
        HttpSession session = request.getSession();
        TarjetaSim ts = new TarjetaSim();
        ts.setNum_cel(cel);
        /*SE BUSCA LA SIM CAR ASOCIADA*/
        TarjetaSim simEncontrada = TarjetaSimBD.searchSimCardByCelular(ts);           
        simEncontrada.setAsociada(0);                
        simEncontrada.setEstado(0); 
        /*SE ACTUALIZA EL CAMPO ASOCIADA DE LA SIM */        
        int retorno1 = TarjetaSimBD.updateSimCardRecordMatch(simEncontrada);                
        /*BUSCO LA RELACION SIM GPS CON LA SIM ENCONTRADA*/
        RelationShipSimGps relacionEncontrada = RelationShipSimGpsBD.searchRelationShipSimGpsBySim(simEncontrada);
        relacionEncontrada.setEstado(0);                
        /*SE ACTUALIZA EL CAMPO ASOCIADA DEL GPS */
        int retorno2 = GpsInventarioBD.updateFiledAsociateSim(relacionEncontrada);        
        /*SE ACTUALIZA EL CAMPO ESTADO DE LA RELACION SIM CARD CON GPS*/
        int retorno3 = RelationShipSimGpsBD.updateOneRelationshipStateByFkGps(relacionEncontrada);
        
        if ((retorno1 > 0) && (retorno2 > 0) && (retorno3>0)) {
            request.setAttribute("inv1", retorno1);
            request.setAttribute("inv2", retorno2);
            request.setAttribute("inv3", retorno2);
        }else{
            request.setAttribute("inv1", null);
            request.setAttribute("inv2", retorno2);
            request.setAttribute("inv3", retorno2);
        }
        url = "/app/relacion_sim_gps/actualizar.jsp";         
        return url;
    }
}
