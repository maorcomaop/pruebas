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
import com.registel.rdw.utils.Mail;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorContacto extends HttpServlet {
    
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
        if (requestURI.endsWith("/guardarContacto")) {            
                url = enviarMensajeDeContacto(request, response);
        } 
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
         
    public String enviarMensajeDeContacto (HttpServletRequest request,
            HttpServletResponse response) {
                        
        String correo = request.getParameter("correo");
        String mensage = request.getParameter("mensaje");        
        String msgType;

            String msg = "Regisdata Web,\n\n"
                    + "Se ha enviado una comunicacion\n\n "+mensage+ "\n\n "+correo;

            Mail mail = new Mail(false);
            
            if (mail.send(msg, "soporte@registelcolombia.com")) {                
                msgType = "1";
            } else
            {
                msgType = "0";
            }
            request.setAttribute("retorno", msgType);
            return "/app/contacto/respuesta.jsp";
    } 
}
