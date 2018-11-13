/**
 * Clase controlador Perfil
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 27/11/2017 10:06:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.AccesoPerfilBD;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.VentanaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.datos;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControladorVentana  extends HttpServlet
{
     @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
         if (!Session.alive(request)) {
            getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request, response);
        }

        String requestURI = request.getRequestURI();

        String url = "/";
         if (requestURI.endsWith("/verDatos")) {
            
        }else if (requestURI.endsWith("/cargarDatos")) {
            url = cargarDatos(request, response);
        }
         getServletContext().getRequestDispatcher(url).forward(request, response);
    }


        public String cargarDatos(HttpServletRequest request,
            HttpServletResponse response) {

        datos a = new datos();
        HttpSession session = request.getSession();        
        
        String placaVehiculoId = request.getParameter("valor");
                
        String SO = System.getProperty("os.name");
        Date fecha = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
         String now = dateFormat.format(fecha);
         SelectBD select = null;
         select = new SelectBD(request);
         try 
         {               
              if (SO.contains("Windows")) {
                  String resultado = VentanaBD.readFile("C:\\Regisdata Transporte Online WIFI\\logs\\ventanaSerial\\Wifi("+now+").rdto");
                  session.setAttribute("resultado", resultado);
              }
              else if (SO.contains("Linux")){
                  String resultado = VentanaBD.readFile("/home/rdw/RDTO_WIFI/logs/ventanaSerial/Wifi("+now+").rdto");
                  session.setAttribute("resultado", resultado);
              }
             
         }
         catch (ExisteRegistroBD ex) 
         {
         
         }
        return "/app/ventana/wifi.jsp";
    }


}
