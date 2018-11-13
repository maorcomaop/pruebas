/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.TipoPolizaBD;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.TipoPoliza;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Richard Mejia
 */
@WebServlet(name = "ControladorTipoPoliza", urlPatterns = {"/ControladorTipoPoliza"})
public class ControladorTipoPoliza extends HttpServlet {

    private String mensajeError;

    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private static final String ESTADO = "estado";
    private static final String ID_EDIT = "id_edit";

    private static final String GUARDAR_TIPO_POLIZA = "/guardarTipoPoliza";
    private static final String VER_TIPO_POLIZA = "/verTipoPoliza";
    private static final String EDITAR_TIPO_POLIZA = "/editarTipoPoliza";
    private static final String CAMBIAR_ESTADO_TIPO_POLIZA = "/cambiarEstadoTipoPoliza";

    private static final String NUEVO_TIPO_POLIZA_JSP = "/app/polizas/nuevoTipoPoliza.jsp";
    private static final String VER_TIPO_POLIZA_JSP = "/app/polizas/verTipoPoliza.jsp";
    private static final String LISTADO_TIPO_POLIZA_JSP = "/app/polizas/listadoTipoPoliza.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente.";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro.";

    private static final int VALOR_POSITIVO = 1;
    
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
            out.println("<title>Servlet ControladorTipoPoliza</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorTipoPoliza at " + request.getContextPath() + "</h1>");
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

        if (requestUri.endsWith(GUARDAR_TIPO_POLIZA)) {
            url = guardarTipoPoliza(request);
        } else if (requestUri.endsWith(VER_TIPO_POLIZA)) {
            url = verTipoPoliza(request);
        } else if (requestUri.endsWith(EDITAR_TIPO_POLIZA)) {
            url = editarTipoPoliza(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_TIPO_POLIZA)) {
            url = cambiarEstadoTipoPoliza(request);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
        processRequest(request, response);
    }

    /**
     * @author Richard Mejia
     * @date 28/08/2018
     * Método encargado de guardar un tipo de póliza.
     * @param request
     * @return 
     */
    private String guardarTipoPoliza(HttpServletRequest request) {
        try {
            TipoPoliza entidadConfigurable = asignarParametrosRequest(request, false);

            if (validarEntidadConfigurable(entidadConfigurable)) {
                boolean resultado = TipoPolizaBD.insert(entidadConfigurable);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR + " " + mensajeError);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return NUEVO_TIPO_POLIZA_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 28/08/2018
     * Método encargado de consultar los detalles de un tipo de póliza.
     * @param request
     * @return 
     */
    private String verTipoPoliza(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            TipoPoliza entidadConfigurable = TipoPolizaBD.selectByOne(Long.parseLong(id));

            if (entidadConfigurable != null && entidadConfigurable.getId() > 0) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("tipoPoliza", entidadConfigurable);
                request.getSession().setAttribute("select", select);
                url = VER_TIPO_POLIZA_JSP;
            } else {
                url = LISTADO_TIPO_POLIZA_JSP;
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return url;
    }

    /**
     * @author Richard Mejia
     * @date 28/08/2018
     * Método encargado de editar los valores de un tipo de póliza.
     * @param request
     * @return 
     */
    private String editarTipoPoliza(HttpServletRequest request) {
        try {
            TipoPoliza entidadConfigurable = asignarParametrosRequest(request, true);

            if (validarEntidadConfigurable(entidadConfigurable)) {
                boolean resultado = TipoPolizaBD.update(entidadConfigurable);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("tipoPoliza", entidadConfigurable);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR + " " + mensajeError);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return VER_TIPO_POLIZA_JSP;
    }

    /**
     * @author Richard Mejia 
     * @date 28/08/2018
     * Método encargado de cambiar el estado de un tipo de póliza.
     * @param request
     * @return 
     */
    private String cambiarEstadoTipoPoliza(HttpServletRequest request) {
        try {
            long id = Long.parseLong(request.getParameter(ID));
            int estado = Integer.parseInt(request.getParameter(ESTADO));
            EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
            entidadConfigurable.setId(id);
            entidadConfigurable.setEstado(estado);
            boolean resultado = TipoPolizaBD.updateEstado(entidadConfigurable);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", MENSAJE_EXITO);
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return LISTADO_TIPO_POLIZA_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 28/08/2018
     * Método encargado de validar los atributos de un tipo póliza.
     * @param entidadConfigurable Entida de tipo tipo póliza que se a a validar.
     * @return 
     */
    private boolean validarEntidadConfigurable(TipoPoliza entidadConfigurable) {
        try {
            mensajeError = "";
            
            if (entidadConfigurable == null) {
                mensajeError = "El tipo de póliza es null.";
                return false;
            }
            
            if (entidadConfigurable.getNombre() == null || entidadConfigurable.getNombre().isEmpty()) {
                mensajeError = "No se envió el nombre del tipo de póliza.";
                return false;
            } else if (entidadConfigurable.getNombre().length() > 250) {
                mensajeError = "El nombre del tipo de póliza supera los 250 caracteres permitidos.";
                return false;
            }
            
            if (entidadConfigurable.getDescripcion()== null || entidadConfigurable.getDescripcion().isEmpty()) {
                mensajeError = "No se envió la descripción del tipo de póliza.";
                return false;
            } else if (entidadConfigurable.getNombre().length() > 2000) {
                mensajeError = "La descripción del tipo de póliza supera los 2000 caracteres permitidos.";
                return false;
            }
            
            return !(entidadConfigurable.getEstado() != 0 && entidadConfigurable.getEstado() != 1); 
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    /**
     * @author Richard Mejia
     * @date 28/08/2018
     * Método encargado de tomar los parámetros enviados en una petición.
     * @param request
     * @param asignarId
     * @return 
     */
    private TipoPoliza asignarParametrosRequest(HttpServletRequest request, boolean asignarId) {
        TipoPoliza entidadConfigurable = new TipoPoliza();
        entidadConfigurable.setNombre(request.getParameter(NOMBRE));
        entidadConfigurable.setDescripcion(request.getParameter(DESCRIPCION));
        entidadConfigurable.setEstado(VALOR_POSITIVO);
        
        if (asignarId) {
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID_EDIT)));
        }
        
        return entidadConfigurable;
    }

}
