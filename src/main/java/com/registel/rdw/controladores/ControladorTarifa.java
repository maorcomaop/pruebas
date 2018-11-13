/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.TarifaBD;
import com.registel.rdw.logica.Tarifa;
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
public class ControladorTarifa extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {            
        
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
        String url = "";
        
        if (requestURI.endsWith("/guardarTarifa")) {
            url = guardarTarifa(request, response);
        } else if (requestURI.endsWith("/pre_editarTarifa")) {
            url = pre_editarTarifa(request, response);
        } else if (requestURI.endsWith("/editarTarifa")) {
            url = editarTarifa(request, response);
        } else if (requestURI.endsWith("/activarTarifa")) {
            url = activarTarifa(request, response);
        } else if (requestURI.endsWith("/eliminarTarifa")) {
            url = eliminarTarifa(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para crear nueva tarifa.
    // Delega proceso de almacenamiento, comprueba antes 
    // si valor de tarifa se encuentra registrada.
    public String guardarTarifa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre = request.getParameter("nombre");
        String valor  = request.getParameter("valor");
        
        String svalor = "" + Double.parseDouble(valor);
        
        // Comprueba existencia de tarifa con valor especificado
        if (TarifaBD.exist(svalor)) {
            request.setAttribute("msg", "* Tarifa con valor $" + svalor + " ya existe.");
            request.setAttribute("msgType", "alert alert-info");
            return "/app/usuarios/nuevaTarifa.jsp";
        }
        
        Tarifa t = new Tarifa();
        t.setNombre(nombre);
        t.setValor(Double.parseDouble(valor));
        
        // Almacena tarifa
        if (TarifaBD.insert(t)) {
            request.setAttribute("msg", "* Tarifa almacenada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);            
        } else {
            request.setAttribute("msg", "* Tarifa no almacenada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/nuevaTarifa.jsp";
    }
    
    // Consulta informacion asociada a una tarifa,
    // para ser editada.
    public String pre_editarTarifa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idTarifa = request.getParameter("idTarifa");
        Tarifa t = TarifaBD.get (idTarifa);
        
        HttpSession session = request.getSession();
        session.setAttribute("tarifa", t);
        
        return "/app/usuarios/editaTarifa.jsp";
    }
    
    // Procesa solicitud de actualizacion de una tarifa.
    // Delega actualizacion de tarifa.
    public String editarTarifa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre = request.getParameter("nombre");
        String valor  = request.getParameter("valor");
        String idTarifa = request.getParameter("idTarifa");
        
        Tarifa t = new Tarifa();
        t.setNombre(nombre);
        t.setValor(Double.parseDouble(valor));
        t.setId(Integer.parseInt(idTarifa));
        
        // Actualiza tarifa
        if (TarifaBD.update(t)) {
            request.setAttribute("msg", "* Tarifa actualizada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            session.setAttribute("tarifa", t);
        } else {
            request.setAttribute("msg", "* Tarifa no actualizada.");            
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/editaTarifa.jsp";
    }
    
    // Procesa solicitud de activacion/desactivacion de tarifa.
    // (cambia valor de campo activo 0-1).
    public String activarTarifa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idTarifa = request.getParameter("idTarifa");
        String activar  = request.getParameter("activar");
        
        String val  = (activar.compareTo("1") == 0)
                    ? "activada"
                    : "desactivada";
        
        String msg = "* Tarifa " + val + " correctamente.";
        
        // Activa/desactiva tarifa
        if (TarifaBD.changeActive(idTarifa, activar)) {
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "alert alert-success");            
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* Tarifa no activada/desactivada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/consultaTarifa.jsp";
    }

    // Procesa solicitud de eliminacion/restauracion de tarifa.
    // (cambia valor de campo estado 0-1).    
    public String eliminarTarifa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idTarifa = request.getParameter("idTarifa");
        String estado = request.getParameter("estado");
        
        String accion = (estado.compareTo("1") == 0) ? "restaurada" : "eliminada";
        
        // Elimina/restaura tarifa
        if (TarifaBD.changeState(idTarifa, estado)) {
            request.setAttribute("msg", "* Tarifa " + accion + " correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* Tarifa no eliminada.");            
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/consultaTarifa.jsp";
    }
}
