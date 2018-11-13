/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.TipoEventoMantenimientoBD;
import com.registel.rdw.logica.TipoEventoMantenimiento;
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
 * @date 17/07/2018
 */
public class ControladorTipoEventoMantenimiento extends HttpServlet {
    
    private String mensajeError;

    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String UNIDAD_MEDIDA = "unidadMedida";
    private static final String DESCRIPCION = "descripcion";
    private static final String ESTADO = "estado";
    private static final String ID_EDIT = "id_edit";

    private static final String GUARDAR_TIPO_EVENTO_MANTENIMIENTO = "/guardarTipoEventoMantenimiento";
    private static final String VER_TIPO_EVENTO_MANTENIMIENTO = "/verTipoEventoMantenimiento";
    private static final String EDITAR_TIPO_EVENTO_MANTENIMIENTO = "/editarTipoEventoMantenimiento";
    private static final String CAMBIAR_ESTADO_TIPO_EVENTO_MANTENIMIENTO = "/cambiarEstadoTipoEventoMantenimiento";

    private static final String NUEVO_TIPO_EVENTO_MANTENIMIENTO_JSP = "/app/mantenimiento/nuevoTipoEventoMantenimiento.jsp";
    private static final String VER_TIPO_EVENTO_MANTENIMIENTO_JSP = "/app/mantenimiento/verTipoEventoMantenimiento.jsp";
    private static final String LISTADO_TIPO_EVENTO_MANTENIMIENTO_JSP = "/app/mantenimiento/listadoTipoEventoMantenimiento.jsp";

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
            out.println("<title>Servlet ControladorTipoEventoMantenimiento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorTipoEventoMantenimiento at " + request.getContextPath() + "</h1>");
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

        if (requestUri.endsWith(GUARDAR_TIPO_EVENTO_MANTENIMIENTO)) {
            url = guardarTipoEventoMantenimiento(request, response);
        } else if (requestUri.endsWith(VER_TIPO_EVENTO_MANTENIMIENTO)) {
            url = verTipoEventoMantenimiento(request, response);
        } else if (requestUri.endsWith(EDITAR_TIPO_EVENTO_MANTENIMIENTO)) {
            url = editarTipoEventoMantenimiento(request, response);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_TIPO_EVENTO_MANTENIMIENTO)) {
            url = cambiarEstadoTipoEventoMantenimiento(request, response);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);processRequest(request, response);
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
     * @date 16/07/2018 Método encargado de guardar un vehiculo asegurado.
     * @param request
     * @param response
     * @return
     */
    public String guardarTipoEventoMantenimiento(HttpServletRequest request, HttpServletResponse response) {
        try {
            TipoEventoMantenimiento tipoEventoMantenimiento = asignarParametrosRequest(request, false);

            if (validarTipoEventoMantenimiento(tipoEventoMantenimiento)) {
                boolean resultado = TipoEventoMantenimientoBD.insert(tipoEventoMantenimiento);

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
        }

        return NUEVO_TIPO_EVENTO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 16/07/2018 Método encargado de validar que los atributos
     * obligatorios de un vehiculo asegurado tengan valores apropiados.
     * @param vehiculoAsegurado
     * @return
     */
    private boolean validarTipoEventoMantenimiento(TipoEventoMantenimiento tipoEventoMantenimiento) {
        try {
            mensajeError = "";

        if (tipoEventoMantenimiento == null) {
            mensajeError = "Tipo de evento de manteminieto es null.";
            return false;
        }
        
        if (tipoEventoMantenimiento.getNombre() == null || tipoEventoMantenimiento.getNombre().isEmpty() 
                || tipoEventoMantenimiento.getNombre().length() > 250) {
            
            if (tipoEventoMantenimiento.getNombre() == null || tipoEventoMantenimiento.getNombre().isEmpty()) {
                mensajeError = "No se envió el nombre del tipo de evento de mantenimiento.";
            } else if (tipoEventoMantenimiento.getNombre().length() > 250) {
                mensajeError = "El nombre del tipo de evento de mantenimiento supera los caracteres permitidos.";
            }
            
            return false;
        }
        
        if (tipoEventoMantenimiento.getUnidadMedida()== null || tipoEventoMantenimiento.getUnidadMedida().isEmpty() 
                || tipoEventoMantenimiento.getUnidadMedida().length() > 50) {
            
            if (tipoEventoMantenimiento.getUnidadMedida() == null || tipoEventoMantenimiento.getUnidadMedida().isEmpty()) {
                mensajeError = "No se envió la unidad de medida del tipo de evento de mantenimiento.";
            } else if (tipoEventoMantenimiento.getNombre().length() > 250) {
                mensajeError = "La unidad de medida del tipo de evento de mantenimiento supera los caracteres permitidos.";
            }
            
            return false;
        }
        
        if (tipoEventoMantenimiento.getDescripcion() != null && tipoEventoMantenimiento.getDescripcion().length() > 2000) {
            mensajeError = "La descripción del tipo de evento de mantenimiento supera los limites permitidos.";
            return false;
        }
        
        return !(tipoEventoMantenimiento.getEstado() != 0 && tipoEventoMantenimiento.getEstado() != 1);
        } catch (Exception e) {
            System.err.print(e);
        }
        
        return false;
    }

    /**
     * @author Richard Mejia
     * @date 16/07/2018 Método encargado de mostrar los detalles de un vehiculo asegurado.
     * @param request
     * @param response
     * @return
     */
    public String verTipoEventoMantenimiento(HttpServletRequest request, HttpServletResponse response) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            TipoEventoMantenimiento tipoEventoMantenimiento = 
                    TipoEventoMantenimientoBD.selectByOne(Long.parseLong(id));

            if (tipoEventoMantenimiento != null) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("tipoEventoMantenimiento", tipoEventoMantenimiento);
                request.getSession().setAttribute("select", select);
                url = VER_TIPO_EVENTO_MANTENIMIENTO_JSP;
            } else {
                url = LISTADO_TIPO_EVENTO_MANTENIMIENTO_JSP;
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return url;
    }

    /**
     * @author Richard Mejia
     * @date 16/07/2018 
     * Método encargado de editar los valores de un vehiculo asegurado.
     * @param request
     * @param response
     * @return
     */
    public String editarTipoEventoMantenimiento(HttpServletRequest request, HttpServletResponse response) {
        try {
            TipoEventoMantenimiento tipoEventoMantenimiento = asignarParametrosRequest(request, true);

            if (validarTipoEventoMantenimiento(tipoEventoMantenimiento)) {
                boolean resultado = TipoEventoMantenimientoBD.update(tipoEventoMantenimiento);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("tipoEventoMantenimiento", tipoEventoMantenimiento);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
        }

        return VER_TIPO_EVENTO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 13/07/2018 
     * Método encargado de cambiar el estado a un vehiculo asegurado.
     * @param request
     * @param response
     * @return
     */
    public String cambiarEstadoTipoEventoMantenimiento(HttpServletRequest request, HttpServletResponse response) {
        try {
            long id = Long.parseLong(request.getParameter(ID));
            int estado = Integer.parseInt(request.getParameter(ESTADO));
            TipoEventoMantenimiento tipoEventoMantenimiento = new TipoEventoMantenimiento();
            tipoEventoMantenimiento.setId(id);
            tipoEventoMantenimiento.setEstado(estado);
            boolean resultado = TipoEventoMantenimientoBD.updateEstado(tipoEventoMantenimiento);

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
        }

        return LISTADO_TIPO_EVENTO_MANTENIMIENTO_JSP;
    }
   
    private TipoEventoMantenimiento asignarParametrosRequest(HttpServletRequest request, boolean asignarId) {
        TipoEventoMantenimiento tipoEventoMantenimiento = new TipoEventoMantenimiento();
        tipoEventoMantenimiento.setNombre(request.getParameter(NOMBRE));
        tipoEventoMantenimiento.setUnidadMedida(request.getParameter(UNIDAD_MEDIDA));
        tipoEventoMantenimiento.setDescripcion(request.getParameter(DESCRIPCION));
        tipoEventoMantenimiento.setEstado(VALOR_POSITIVO);
        
        if (asignarId) {
            tipoEventoMantenimiento.setId(Long.parseLong(request.getParameter(ID_EDIT)));
        }
        
        return tipoEventoMantenimiento;
    }

}
