/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.LoginBD;
import com.registel.rdw.logica.Usuario;
import java.sql.Time;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class Session extends HttpServlet {    
    
    public static boolean CLOSE_SESSION = true;
    
    private static Usuario usuarioSesion;
    
    public static void setUsuarioSesion(Usuario usuario) {
        usuarioSesion = usuario;
    }
    
    public static Usuario getUsuarioSesion() {
        return usuarioSesion;
    }
    
    /*
     * Imprime tiempo de creacion de sesion activa.
     */
    public static void showtimeCreation(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Time time_creation = new Time(session.getCreationTime());            
            System.out.println("Time creation: " + time_creation);
        }
    }
    
    /*
     * Cierra sesion activa.
     */
    public static void close (HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession(false);
        
        String arrayVar[] = {
            "login", "select", "tmpusr", "prop", "perim", "entorno",
            "alarma", "datos", "columnas", "tablaAImprimir", "auditoria",
            "categoria", "conductor", "preusr", "evento", "gempresa",
            "grupoEncontrado", "lstinforeg", "parametrosBusqueda",
            "infoReg", "categoriaEncontrada", "categoriasFijas",
            "vueltas", "conductorVehiculo", "placa", "numeroInterno",
            "modulo", "motivo", "movil", "perfil", "relacionVehiculoEmpresa",
            "relacionVehiculoConductor", "ruta", "submodulo", "tarifa",
            "usr", "emp", "lstvp"
        };
        
        if (session != null) {
            
            Usuario usr = ( Usuario ) session.getAttribute("login");
            if (usr != null) { 
                LoginBD.desactivarConexion(usr.getLogin()); 
            }
            
            for (String var : arrayVar)
                session.removeAttribute(var);
            
            session.invalidate();            
            //session.setMaxInactiveInterval(0);
            session = null;
        }
    }        
    
    /*
     * Consulta si sesion actual se encuentra activa y
     * en vigencia (no ha expirado).
     * Cierra sesion solo si ha expirado y se autoriza
     * a traves de variable CLOSE_SESSION.
     */
    public static boolean alive (HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || 
            session.getAttribute("login") == null ||
            Expire.check(session.getCreationTime())) {
            
            // Cierra sesion
            if (CLOSE_SESSION)
                close(request, null);
            return false;
        }
        
        return true;
    }   
    
    /*
     * Consulta si sesion se encuentra activa y en vigencia (no ha expirado),
     * sin cerrarla en caso de haber expirado.
     */
    public static boolean aliveNoClose (HttpServletRequest request) {
        CLOSE_SESSION = false;
        boolean rs =  alive(request);
        CLOSE_SESSION = true;
        return rs;
    }
}
