/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.RutaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Ruta;
import com.registel.rdw.utils.TimeUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorRuta extends HttpServlet {
    
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
        
        if (requestURI.endsWith("/guardarRuta")) {
            url = guardarRuta(request, response);
        } else if (requestURI.endsWith("/pre_editarRuta")) {
            url = pre_editarRuta(request, response);
        } else if (requestURI.endsWith("/editarRuta")) {
            url = editarRuta(request, response);
        } else if (requestURI.endsWith("/eliminarRuta")) {
            url = eliminarRuta(request, response);
        } else if (requestURI.endsWith("/actualizarLongitudRuta")) {
            url = actualizarLongitudRuta(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para creacion de nueva ruta.
    // Delega proceso de almacenamiento.
    public String guardarRuta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidEmpresa  = request.getParameter("sempresa");
        String nombreRuta = request.getParameter("nombre");
        int idEmpresa = Integer.parseInt(sidEmpresa);
        
        Ruta r = new Ruta();
        r.setNombre(nombreRuta);
        r.setIdEmpresa(idEmpresa);
        
        // Almacena ruta
        if (RutaBD.insert(r)) {
            String msg = "* Ruta registrada correctamente."
                       + " <strong>Ahora asigna los puntos y bases a la ruta.</strong>";
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            
            return "/app/usuarios/asignaPuntosRuta.jsp";
            
        } else {
            request.setAttribute("msg", "* Ruta no registrada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/nuevaRuta.jsp";
    }
    
    // Procesa solicitud de consulta de ruta para ser editada.
    public String pre_editarRuta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idRuta = request.getParameter("idRuta");
        Ruta r = RutaBD.get(idRuta);
        
        HttpSession session = request.getSession();
        session.setAttribute("ruta", r);
        
        return "/app/usuarios/editaRuta.jsp";
    }
    
    // Procesa solicitud para actualizacion de ruta.
    // Delega proceso de actualizacion.
    public String editarRuta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre = request.getParameter("nombre");
        String idRuta = request.getParameter("idRuta");
        String idEmpresa = request.getParameter("sempresa");
        
        Ruta r = new Ruta();
        r.setNombre(nombre);
        r.setId(Integer.parseInt(idRuta));
        r.setIdEmpresa(Integer.parseInt(idEmpresa));
        
        // Actualiza ruta
        if (RutaBD.update(r)) {
            request.setAttribute("msg", "* Ruta actualizada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            session.setAttribute("ruta", r);
        } else {
            request.setAttribute("msg", "* Ruta no actualizada.");    
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/editaRuta.jsp";
    }
    
    // Procesa solicitud para eliminacion de ruta.
    // Delega proceso de eliminacion.
    // (valor de campo estado se cambia a 0).
    public String eliminarRuta (HttpServletRequest request,
            HttpServletResponse response) {
            
        String idRuta = request.getParameter("idRuta");
        
        // Elimina ruta
        if (RutaBD.remove(idRuta)) {
            request.setAttribute("msg", "* Ruta eliminada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* Ruta no fue eliminada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/consultaRuta.jsp";
    }
    
    // Procesa solicitud para actualizar longitud de rutas.
    public String actualizarLongitudRuta(HttpServletRequest request,
            HttpServletResponse response) {
        
        String mesAnterior = request.getParameter("vmesAnterior");        
        
        int mes, anio; Date fecha = new Date();
        if (mesAnterior.compareTo("1") == 0) {
            Date fecha_ant = TimeUtil.restarMes(fecha);
            mes  = TimeUtil.mes(fecha_ant) + 1;
            anio = TimeUtil.anio(fecha_ant);
        } else {
            mes  = TimeUtil.mes(fecha) + 1;
            anio = TimeUtil.anio(fecha);
        }
                
        ArrayList<Ruta> lst_ruta  = RutaBD.select();
        ArrayList<Ruta> lst_lruta = RutaBD.selectLongitudRuta(mes, anio);
        // Actualiza valores de longitud para cada ruta
        actualizarLongitudRuta(lst_ruta, lst_lruta);
        // Crea lista de instrucciones SQL
        ArrayList<String> lst_sql = formatDistanciaRutaSQL_UPD(lst_ruta);
        
        // Actualiza longitudes de ruta
        if (lst_sql != null && RutaBD.updateLongitudRuta(lst_sql)) {
            int n = lst_sql.size(); String msg;
            
            if (n == 1) { msg = "* Actualizada una longitud de ruta."; }
            else        { msg = "* Actualizadas " + n + " longitudes de ruta."; }
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "form-msg bottom-space alert alert-success");
            
            SelectBD select = new SelectBD(request);
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            
        } else {
            request.setAttribute("msg", "* Longitudes de ruta no actualizadas.");
            request.setAttribute("msgType", "form-msg bottom-space alert alert-info");
        }
        
        return "/app/usuarios/longitudRuta.jsp";
    }
    
    // Itera sobre cada elemento de listado de rutas y actualiza su longitud.
    public void actualizarLongitudRuta(ArrayList<Ruta> lst_ruta,
                                       ArrayList<Ruta> lst_lruta) {
        
        if (lst_lruta != null && lst_lruta.size() > 0) {
            
            for (int i = 0; i < lst_lruta.size(); i++) {
                Ruta lr = lst_lruta.get(i);
                
                for (int j = 0; j < lst_ruta.size(); j++) {
                    Ruta r = lst_ruta.get(j);
                
                    if (r.getId() == lr.getId() &&
                        lr.getDistanciaMetros() > 0) {
                        r.setDistanciaMetros(lr.getDistanciaMetros());
                    }
                }
            }
        }
    }
    
    // Crea listado de instrucciones SQL para actualizacion de longitud de ruta.
    public ArrayList<String> formatDistanciaRutaSQL_UPD(ArrayList<Ruta> lst_ruta) {
        
        if (lst_ruta != null && lst_ruta.size() > 0) {
            ArrayList<String> lst_sql = new ArrayList<String>();
            
            for (Ruta r : lst_ruta) {
                String sql = "UPDATE tbl_ruta SET DISTANCIA_METROS = " + r.getDistanciaMetros()
                                + " WHERE PK_RUTA = " + r.getId()
                                + " AND ESTADO = 1";
                if (r.getDistanciaMetros() > 0)
                    lst_sql.add(sql);                
            }
            return lst_sql;
        }
        return null;
    }    
}
