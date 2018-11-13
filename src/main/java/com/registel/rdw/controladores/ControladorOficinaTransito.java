/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.OficinaTransitoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.OficinaTransito;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Richard Mejia
 * 
 * @date 11/07/2018
 */
public class ControladorOficinaTransito extends HttpServlet {

    private static final String NOMBRE = "nombre";
    private static final String TELEFONO = "telefono";
    private static final String DIRECCION = "direccion";
    private static final String CORREO = "correo";
    private static final String FK_CIUDAD = "ciudad";
    private static final String ESTADO = "estado";
    
    private static final String GUARDAR_OFICINA = "/guardarOficinaTransito";
    private static final String VER_OFICINA = "/verOficinaTransito";
    private static final String EDITAR_OFICINA = "/editarOficinaTransito";
    private static final String CAMBIAR_ESTADO_OFICINA = "/cambiarEstadoOficinaTransito";
    
    private static final String NUEVA_OFICINA_TRANSITO_JSP = "/app/licencia_conduccion/nuevaOficinaTransito.jsp";
    private static final String VER_OFICINA_TRANSITO_JSP = "/app/licencia_conduccion/verOficinaTransito.jsp";
    private static final String LISTADO_OFICINA_TRANSITO_JSP = "/app/licencia_conduccion/listadoOficinaTransito.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente.";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro.";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorOficinaTransito</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorOficinaTransito at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (requestUri == null || requestUri.isEmpty()) {
            return;
        }

        String url = "";

        if (requestUri.endsWith(GUARDAR_OFICINA)) {
            url = guardarOficinaTransito(request);
        } else if (requestUri.endsWith(VER_OFICINA)) {
            url = verOficinaTransito(request);
        } else if (requestUri.endsWith(EDITAR_OFICINA)) {
            url = editarOficinaTransito(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_OFICINA)) {
            url = cambiarEstadoOficinaTransito(request);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de guardar una oficina de transito.
     * @param request
     * @return 
     */
    public String guardarOficinaTransito(HttpServletRequest request) {
        try {
            String nombre = request.getParameter(NOMBRE);
            String telefono = request.getParameter(TELEFONO);
            String correo = request.getParameter(CORREO);
            String direccion = request.getParameter(DIRECCION);
            String fk_ciudad = request.getParameter(FK_CIUDAD);
            OficinaTransito oficinaTransito = new OficinaTransito();
            oficinaTransito.setNombre(nombre);
            oficinaTransito.setTelefono(telefono);
            oficinaTransito.setCorreo(correo);
            oficinaTransito.setDireccion(direccion);
            oficinaTransito.setFk_ciudad(Integer.parseInt(fk_ciudad));
            oficinaTransito.setEstado(1);
            
            if (validarOficinaTransito(oficinaTransito)) {
                boolean resultado = OficinaTransitoBD.insert(oficinaTransito);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return NUEVA_OFICINA_TRANSITO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de mostrar los detalles de una oficina de tránsito.
     * @param request
     * @return 
     */
    public String verOficinaTransito(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter("id");
            OficinaTransito oficinaTransito = new OficinaTransito();
            oficinaTransito.setId(Long.parseLong(id));
            SelectBD select = new SelectBD(request);
            OficinaTransito oficinaEncontrada = OficinaTransitoBD.selectByOne(oficinaTransito);

            if (oficinaEncontrada != null) {
                request.getSession().setAttribute("oficinaTransito", oficinaEncontrada);
                request.getSession().setAttribute("select", select);
                url = VER_OFICINA_TRANSITO_JSP;
            } else {
                url = LISTADO_OFICINA_TRANSITO_JSP;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return url;
    }

    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de editar el contenido de una oficina de tránsito.
     * @param request
     * @return 
     */
    public String editarOficinaTransito(HttpServletRequest request) {
        try {
            String id = request.getParameter("id_edit");
            String nombre = request.getParameter(NOMBRE);
            String telefono = request.getParameter(TELEFONO);
            String correo = request.getParameter(CORREO);
            String direccion = request.getParameter(DIRECCION);
            String ciudad = request.getParameter(FK_CIUDAD);
            OficinaTransito oficinaTransito = new OficinaTransito();
            oficinaTransito.setId(Long.parseLong(id));
            oficinaTransito.setNombre(nombre);
            oficinaTransito.setTelefono(telefono);
            oficinaTransito.setCorreo(correo);
            oficinaTransito.setDireccion(direccion);
            oficinaTransito.setFk_ciudad(Integer.parseInt(ciudad));
            oficinaTransito.setEstado(1);
            
            if (validarOficinaTransito(oficinaTransito)) {
                boolean resultado = OficinaTransitoBD.update(oficinaTransito);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("oficinaTransito", oficinaTransito);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return VER_OFICINA_TRANSITO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de cambiar el estado de una oficina de tránsito.
     * @param request
     * @return 
     */
    public String cambiarEstadoOficinaTransito(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String estado = request.getParameter(ESTADO);
            OficinaTransito oficinaTransito = new OficinaTransito();
            oficinaTransito.setId(Long.parseLong(id));
            oficinaTransito.setEstado(Integer.parseInt(estado));
            boolean resultado = OficinaTransitoBD.updateEstado(oficinaTransito);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", MENSAJE_EXITO);
                request.setAttribute("oficinaTransito", oficinaTransito);
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
        }
        
        return LISTADO_OFICINA_TRANSITO_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método que verifica que los atributos de una oficina de tránsito tengan
     * valores apropiados.
     * @param oficinaTransito Objeto de tipo oficina de transito que se va a validar.
     * @return 
     */
    private boolean validarOficinaTransito(OficinaTransito oficinaTransito) {
        if (oficinaTransito != null) {
            if (oficinaTransito.getNombre() != null && oficinaTransito.getFk_ciudad() > 0) {
                if (oficinaTransito.getEstado() == 0 || oficinaTransito.getEstado() == 1) {
                    return true;
                }
            }
        }
        
        return false;
    }

}
