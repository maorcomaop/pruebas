/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.BaseBD;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.PuntoRelacionadoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Base;
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
public class ControladorBase {

    // Procesa solicitud para crear nuevo punto base.
    // Delega almacenamiento y actualizacion de listado de puntos creados recientemente.
    public String guardarBase (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre   = request.getParameter("nombre");                
        String codBase  = request.getParameter("codPtoBase");
        String latitud  = request.getParameter("latitud");
        String longitud = request.getParameter("longitud");
        String dirLat   = request.getParameter("dirLatitud");
        String dirLon   = request.getParameter("dirLongitud");
        String radio    = request.getParameter("radio");        
        
        Base b = new Base();
        b.setNombre(nombre);
        b.setIdentificador(Restriction.getNumber(codBase));
        b.setCodigoBase(0); // Codigo de empresa
        b.setLatitud(latitud);
        b.setLongitud(longitud);
        b.setDireccionLatitud(dirLat);
        b.setDireccionLongitud(dirLon);
        b.setRadio(Restriction.getNumber(radio));
        b.setDireccion(3);
        
        b.setLatitudWeb(latitud);
        b.setLongitudWeb(longitud);
                
        try {
            // Inserta nuevo punto base
            if (BaseBD.insert(b)) {
                request.setAttribute("msg", "* Base almacenada correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                SelectBD select = new SelectBD(request);
                
                HttpSession session = request.getSession();
                session.setAttribute("select", select);
            } else {
                request.setAttribute("msg", "* Base no almacenada.");
                request.setAttribute("msgType", "alert alert-danger");
            }
        } catch (ExisteRegistroBD e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("msgType", "alert alert-info");
        }
        
        // Actualiza listado de puntos
        ControladorPunto.actualizarPuntosRecientes(request);
        return "/app/usuarios/nuevoPunto.jsp";
    }
    
    // Procesa solicitud de actualizacion de punto base.
    // Delega actualizacion del punto y de listado de puntos creados recientemente.
    public String actualizarBase (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre   = request.getParameter("nombre");        
        String codBase  = request.getParameter("codPtoBase");
        String latitud  = request.getParameter("latitud");
        String longitud = request.getParameter("longitud");
        String dirLat   = request.getParameter("dirLatitud");
        String dirLon   = request.getParameter("dirLongitud");
        String radio    = request.getParameter("radio");        
        
        Base b = new Base();
        b.setNombre(nombre);
        b.setIdentificador(Restriction.getNumber(codBase));
        b.setCodigoBase(0); // Codigo de empresa
        b.setLatitud(latitud);
        b.setLongitud(longitud);
        b.setDireccionLatitud(dirLat);
        b.setDireccionLongitud(dirLon);
        b.setRadio(Restriction.getNumber(radio));
        b.setDireccion(3);
        
        b.setLatitudWeb(latitud);
        b.setLongitudWeb(longitud);
        
        // Actualiza punto base
        if (BaseBD.update(b)) {
            request.setAttribute("msg", "* Base actualizada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* Base no actualizada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        // Actualiza listado de puntos
        ControladorPunto.actualizarPuntosRecientes(request);
        return "/app/usuarios/nuevoPunto.jsp";
    }
    
    // Procesa solicitud de eliminacion de punto base.
    // Delega eliminacion de punto y actualizacion de puntos creados recientemente.
    public String eliminarBase (HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidbase = request.getParameter("idbase");
        int idbase     = Restriction.getNumber(sidbase);
        
        try {
            // Elimina base
            if (BaseBD.remove(idbase)) {
                request.setAttribute("msg", "* Base eliminada correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                SelectBD select = new SelectBD(request);

                HttpSession session = request.getSession();
                session.setAttribute("select", select);
            } else {
                request.setAttribute("msg", "* Base no eliminada.");
                request.setAttribute("msgType", "alert alert-danger");
            }
        } catch (PuntoRelacionadoBD ex) {
            request.setAttribute("msg", "* Base relacionada a una ruta. No es posible eliminar.");
            request.setAttribute("msgType", "alert alert-info");
        }
        
        // Actualiza listado de puntos
        ControladorPunto.actualizarPuntosRecientes(request);
        return "/app/usuarios/nuevoPunto.jsp";
    }
}
