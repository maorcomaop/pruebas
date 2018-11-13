/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.ConsultaExterna;
import com.registel.rdw.datos.InformacionRegistradoraBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.DatosGPS;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.VueltaCerrada;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorConsolidacion extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) 
            throws IOException, ServletException {
        
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) 
            throws IOException, ServletException {
        
        String requestURI = request.getRequestURI();
        String url = "";
        
        if (requestURI.endsWith("/cerrarVuelta")) {
            url = cerrarVuelta(request, response);
        }
        
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    // Procesa solicitud para cerrar vuelta de un movil, en punto base y 
    // hora especifico. Se comprueba que no exista vuelta con numeracion
    // de la hora especificada.
    public String cerrarVuelta(HttpServletRequest request,
            HttpServletResponse response) {
        
        String movil  = request.getParameter("smovil"); // id, placa, numero_interno
        String base   = request.getParameter("sbase");  // codigo_base % nombre_base % latitud % longitud
        String fecha  = request.getParameter("fecha");
        String motivo = request.getParameter("motivo");
        String msg    = "";
        
        String arr_base[] = base.split("%");
        String cod_base = arr_base[0];
        String nom_base = arr_base[1];
        String latitud  = arr_base[2];
        String longitud = arr_base[3];
        
        if (cod_base == "" || nom_base == "" || latitud == "" || longitud == "") {
            msg = "* Punto base no especificado correctamente. Verique valores de puntos base.";
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "alert alert-info");
            return "/app/registradora/cierraVuelta.jsp";
        }
        
        String base_gps = "Punto de Control Base - " + cod_base + " - Cierre por software";
        
        String placa = "", numero_interno = "";
        int id_movil = 0;
        
        // Comprueba veracidad de parametros
        boolean es_parametro = (!Restriction.includeSQL(movil) && 
                                !Restriction.includeSQL(motivo) &&
                                !Restriction.includeSQL(base)) ? true : false;        
        boolean es_fecha = Restriction.isDateTime(fecha);
        
        String arr_movil[] = movil.split(",");                     
        
        if (arr_movil != null && arr_movil.length == 3) {
            id_movil       = Restriction.getNumber(arr_movil[0]);        
            placa          = arr_movil[1];
            numero_interno = arr_movil[2];
            es_parametro   = (id_movil > 0) ? true : false;
        } else { 
            es_parametro = false; 
        }
        
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("login");
        String nombre_usuario = user.getNombre() +" "+ user.getApellido();
        
        if (es_parametro && es_fecha) {
            
            // Inicia registro de cierre
            ConsultaExterna consulta_gps = new ConsultaExterna();            
            
            // Consulta ultimo conteo/numeracion hasta fecha/hora especificada
            DatosGPS gps = consulta_gps.ultimoConteo(fecha, placa);            
            
            String fecha_hora[] = fecha.split(" ");
            String sfecha   = fecha_hora[0];
            long numeracion = gps.getNumeracion();
            
            // Se verifica si existe vuelta registrada con numeracion 
            // superior de la fecha/hora especificada
            if (gps == null) {
                
                msg = "* Registro gps para movil <b>" + placa + "</b> y hora <b>" + fecha + "</b> no encontrado.";
                
            } else if (InformacionRegistradoraBD.existeVueltaPorNumeracion(id_movil, fecha, numeracion)) {
                
                msg = "* No se permite cerrar vuelta intermedia. Vuelta con numeraci&oacute;n"
                           + " superior a <b>" + numeracion + "</b> existe.";                                
            }
            
            if (msg != "") {
                request.setAttribute("msg", msg);
                request.setAttribute("msgType", "alert alert-info");
                return "/app/registradora/cierraVuelta.jsp";
            }

            movil = "[" + placa + " - " + numero_interno + "]";
            base  = cod_base + " - " + nom_base;
            
            VueltaCerrada vc = new VueltaCerrada();
            vc.setPlaca(placa);
            vc.setNumero_interno(numero_interno);
            vc.setFechaStr(fecha);
            vc.setBase(base);
            vc.setBaseGps(base_gps);
            vc.setMotivo(motivo);
            vc.setUsuario(nombre_usuario);
            
            gps.setLatitud(latitud);
            gps.setLongitud(longitud);
                        
            // Cierra vuelta
            if (gps != null && 
                consulta_gps.cerrarVuelta(vc, gps)) {
                request.setAttribute("msg", "* Vuelta de veh&iacute;culo " + movil + " cerrada correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);
            } else {
                request.setAttribute("msg", "* Vuelta de veh&iacute;culo " + movil + " no cerrada.");
                request.setAttribute("msgType", "alert alert-danger");
            }
            
        } else {
            request.setAttribute("msg", "* Vuelta no cerrada. Par&aacute;metros no permitidos.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/registradora/cierraVuelta.jsp";
    }
}
