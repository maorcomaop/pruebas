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
import com.registel.rdw.datos.DesempenoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Desempeno;
import java.util.ArrayList;



/*COMENTARIO*/
public class ControladorDesempeno extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
        
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {}
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {        
        String requestURI = request.getRequestURI();        
        String url = "/";
        if (requestURI.endsWith("/guardarConfiguracion")){
            url = guardarConfig(request, response);
        }    
        if (requestURI.endsWith("/consultaConfiguracion")){
            url = configuraciones(request, response);
        }
          if (requestURI.endsWith("/buscarConfiguracion")){
            url = configuracionEncontrada(request, response);
        }
        if (requestURI.endsWith("/editarConfiguracion")){
            url = editarConfig(request, response);
        }
        if (requestURI.endsWith("/inactivarConfiguracion")){
            url = desactivarConf(request, response);
        }
        if (requestURI.endsWith("/activarConfiguracion")){
            url = activarConf(request, response);
        }
          
        
        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       public String guardarConfig (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre =  request.getParameter("nombre");        
        String puntaje_max =  request.getParameter("puntaje_max");        
        String pts_exceso_vel =  request.getParameter("puntos_exceso_vel");
        String vel_max =  request.getParameter("vel_max");
        
        String pts_ruta =  request.getParameter("puntos_ruta");
        String porcentaje_ruta =  request.getParameter("porcentaje_ruta");
        
        String pts_dias_no_labor =  request.getParameter("puntos_dias_no_labor");
        String cant_dias_desc =  request.getParameter("cantidad_dias_descanso");
        
        String pts_ipk_mayor =  request.getParameter("puntos_ipk_mayor");
        String pts_ipk_menor =  request.getParameter("puntos_ipk_menor");

        
        String url="";  
         int retorno = 0;
        HttpSession session = request.getSession();        
        Desempeno h = new Desempeno();
        try {
                h.setNombre(nombre);
                h.setPuntaje_max(Integer.parseInt(puntaje_max));
                h.setPuntos_exceso_velocidad(Integer.parseInt(pts_exceso_vel));
                h.setVelocidad_max(Integer.parseInt(vel_max));

                h.setPuntos_cumplimiento_de_ruta(Integer.parseInt(pts_ruta));
                h.setPorcentaje_ruta(Integer.parseInt(porcentaje_ruta));

                h.setPuntos_dia_no_laborado(Integer.parseInt(pts_dias_no_labor));
                h.setDias_descanso(Integer.parseInt(cant_dias_desc));

                h.setPuntos_ipk_mas(Integer.parseInt(pts_ipk_mayor));
                h.setPuntos_ipk_menos(Integer.parseInt(pts_ipk_menor));
                retorno = DesempenoBD.insertOneConfig(h);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", "Se ha registrado la configuraci&oacute;n");
           } catch (Exception e) {
               System.err.println(e.getMessage());
                request.setAttribute("idInfo", 2);                    
                request.setAttribute("msg", "No se logr&oacute; registrar la configuraci&oacute;n ");
           }
        
        url = "/app/conductor/configuracionDesempeno.jsp";                       
        return url;
    }  

       
        public String configuraciones (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        Desempeno h = new Desempeno();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList retorno = DesempenoBD.selectOwnerAll(h);                   
        session.setAttribute("conf", retorno);                
        url = "/app/conductor/configuraciones.jsp";               
        
         return url;
    }  
        
         public String configuracionEncontrada (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        Desempeno h = new Desempeno();       
        h.setId(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        Desempeno retorno = DesempenoBD.selectOneConf(h);                   
        session.setAttribute("desconf", retorno);                
        url = "/app/conductor/unaConfiguracionDesempeno.jsp";               
        return url;
    }  
                
         
         public String editarConfig (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String nombre =  request.getParameter("nombre");        
        String puntaje_max =  request.getParameter("puntaje_max");        
        String pts_exceso_vel =  request.getParameter("puntos_exceso_vel");
        String vel_max =  request.getParameter("vel_max");
        
        String pts_ruta =  request.getParameter("puntos_ruta");
        String porcentaje_ruta =  request.getParameter("porcentaje_ruta");
        
        String pts_dias_no_labor =  request.getParameter("puntos_dias_no_labor");
        String cant_dias_desc =  request.getParameter("cantidad_dias_descanso");
        
        String pts_ipk_mayor =  request.getParameter("puntos_ipk_mayor");
        String pts_ipk_menor =  request.getParameter("puntos_ipk_menor");

        
        String url="";                   
        Desempeno h = new Desempeno();
        int retorno = 0;
        HttpSession session = request.getSession();        
         try {
                h.setId(Integer.parseInt(id));
                h.setNombre(nombre);
                h.setPuntaje_max(Integer.parseInt(puntaje_max));
                h.setPuntos_exceso_velocidad(Integer.parseInt(pts_exceso_vel));
                h.setVelocidad_max(Integer.parseInt(vel_max));

                h.setPuntos_cumplimiento_de_ruta(Integer.parseInt(pts_ruta));
                h.setPorcentaje_ruta(Integer.parseInt(porcentaje_ruta));

                h.setPuntos_dia_no_laborado(Integer.parseInt(pts_dias_no_labor));
                h.setDias_descanso(Integer.parseInt(cant_dias_desc));

                h.setPuntos_ipk_mas(Integer.parseInt(pts_ipk_mayor));
                h.setPuntos_ipk_menos(Integer.parseInt(pts_ipk_menor));                
                retorno = DesempenoBD.updateOneConf(h);
                session.setAttribute("edt", retorno);             
             } catch (Exception e) {
                 System.err.println(e.getMessage());
                 session.setAttribute("edt", 0);
             }
        url = "/app/conductor/configuracionModificada.jsp";                       
        return url;
    }  
         
         
         
        public String desactivarConf (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");        
        String url="";                   
        Desempeno h = new Desempeno();       
        h.setId(Integer.parseInt(id));
        h.setEstado(0);
        HttpSession session = request.getSession();        
        int retorno = DesempenoBD.updateState(h);
        session.setAttribute("inact", retorno);                
        url = "/app/conductor/ConfiguracionInActiva.jsp";               
        return url;
    }  
        
        
    /***********************************************/
        
        public String activarConf (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");        
        String url="";                   
        Desempeno h = new Desempeno();       
        h.setId(Integer.parseInt(id));
        h.setEstado(1);
        HttpSession session = request.getSession();        
        int retorno = DesempenoBD.updateState(h);
        session.setAttribute("act", retorno);                
        url = "/app/conductor/ConfiguracionActiva.jsp";               
        return url;
    }  
}

