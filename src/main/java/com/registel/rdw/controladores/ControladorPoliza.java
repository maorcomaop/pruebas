/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.PolizaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Poliza;
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
 * @author Richard Mejia
 *
 * @date 13/07/2018
 */
@WebServlet(name = "ControladorPoliza", urlPatterns = {"/ControladorPoliza"})
public class ControladorPoliza extends HttpServlet {

    private String mensajeError;

    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String ASEGURADORA = "aseguradora";
    private static final String AMPAROS_COBERTURAS = "amparosCoberturas";
    private static final String TIPO_POLIZA = "tipoPoliza";
    private static final String ESTADO = "estado";
    private static final String ID_EDIT = "id_edit";

    private static final String GUARDAR_POLIZA = "/guardarPoliza";
    private static final String VER_POLIZA = "/verPoliza";
    private static final String EDITAR_POLIZA = "/editarPoliza";
    private static final String CAMBIAR_ESTADO_POLIZA = "/cambiarEstadoPoliza";

    private static final String NUEVA_POLIZA_JSP = "/app/polizas/nuevaPoliza.jsp";
    private static final String VER_POLIZA_JSP = "/app/polizas/verPoliza.jsp";
    private static final String LISTADO_POLIZA_JSP = "/app/polizas/listadoPoliza.jsp";

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
            out.println("<title>Servlet ControladorPoliza</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorPoliza at " + request.getContextPath() + "</h1>");
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

        if (requestUri.endsWith(GUARDAR_POLIZA)) {
            url = guardarPoliza(request);
        } else if (requestUri.endsWith(VER_POLIZA)) {
            url = verPoliza(request);
        } else if (requestUri.endsWith(EDITAR_POLIZA)) {
            url = editarPoliza(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_POLIZA)) {
            url = cambiarEstadoPoliza(request);
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
     * @date 13/07/2018 Método encargado de guardar una poliza.
     * @param request
     * @return
     */
    public String guardarPoliza(HttpServletRequest request) {
        try {
            Poliza poliza = asignarParametrosRequest(request, false);

            if (validarPoliza(poliza)) {
                boolean resultado = PolizaBD.insert(poliza);

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

        return NUEVA_POLIZA_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 13/07/2018 
     * Método encargado de validar que los atributos
     * obligatorios de una póliza tengan valores apropiados.
     * @param poliza
     * @return
     */
    private boolean validarPoliza(Poliza poliza) {
        mensajeError = "";

        if (poliza == null) {
            mensajeError = "La póliza es null.";
            return false;
        }

        if (poliza.getNombre() == null || poliza.getNombre().isEmpty()
                || poliza.getNombre().length() > 250) {
            mensajeError = "La póliza no tiene nombre";
            return false;
        }

        if (poliza.getFkAseguradora() <= 0) {
            mensajeError = "La póliza no tiene aseguradora asociada.";
            return false;
        }

        if (poliza.getAmparosCoberturas() == null || poliza.getAmparosCoberturas().isEmpty()
                || poliza.getAmparosCoberturas().length() > 4000) {

            if (poliza.getAmparosCoberturas() != null && poliza.getAmparosCoberturas().isEmpty()) {
                mensajeError = "La póliza no tiene amparos y coberturas.";
            } else if (poliza.getAmparosCoberturas() != null && poliza.getAmparosCoberturas().length() > 4000) {
                mensajeError = "La descripción de los amparos y coberturas excede el límite de caracteres permitidos.";
            }

            return false;
        }
        
        if (poliza.getFkTipoPoliza() <= 0) {
            mensajeError = "La póliza no tiene tipo de póliza asociado.";
            return false;
        }

        return !(poliza.getEstado() != 0 && poliza.getEstado() != 1);
    }

    /**
     * @author Richard Mejia
     * @date 13/07/2018 
     * Método encargado de mostrar los detalles de una poliza.
     * @param request
     * @return
     */
    public String verPoliza(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            Poliza poliza = new Poliza();
            poliza.setId(Long.parseLong(id));
            SelectBD select = new SelectBD(request);
            Poliza polizaEncontrada = PolizaBD.selectByOne(poliza);

            if (polizaEncontrada != null) {
                request.getSession().setAttribute("poliza", polizaEncontrada);
                request.getSession().setAttribute("select", select);
                url = VER_POLIZA_JSP;
            } else {
                url = LISTADO_POLIZA_JSP;
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
     * @date 13/07/2018 
     * Método encargado de editar los valores de una poliza.
     * @param request
     * @return
     */
    public String editarPoliza(HttpServletRequest request) {
        try {
            Poliza poliza =  asignarParametrosRequest(request, true);

            if (validarPoliza(poliza)) {
                boolean resultado = PolizaBD.update(poliza);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("poliza", poliza);
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

        return VER_POLIZA_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 13/07/2018 
     * Método encargado de cambiar el estado a una aseguradora.
     * @param request
     * @return
     */
    public String cambiarEstadoPoliza(HttpServletRequest request) {
        try {
            String id = request.getParameter(ID);
            String estado = request.getParameter(ESTADO);
            Poliza poliza = new Poliza();
            poliza.setId(Long.parseLong(id));
            poliza.setEstado(Integer.parseInt(estado));
            boolean resultado = PolizaBD.updateEstado(poliza);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", MENSAJE_EXITO);
                request.setAttribute("poliza", poliza);
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return LISTADO_POLIZA_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 28-08-2018
     * Método encargado de tomar los parámetros enviados en un petición.
     * @param request
     * @param asignarId
     * @return 
     */
    private Poliza asignarParametrosRequest(HttpServletRequest request, boolean asignarId) {
        Poliza entidadConfigurable = new Poliza();
        entidadConfigurable.setNombre(request.getParameter(NOMBRE));
        entidadConfigurable.setFkAseguradora(Long.parseLong(request.getParameter(ASEGURADORA)));
        entidadConfigurable.setAmparosCoberturas(request.getParameter(AMPAROS_COBERTURAS));
        entidadConfigurable.setFkTipoPoliza(Long.parseLong(request.getParameter(TIPO_POLIZA)));
        entidadConfigurable.setEstado(VALOR_POSITIVO);

        if (asignarId) {
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID_EDIT)));
        }

        return entidadConfigurable;
    }

}
