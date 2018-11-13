/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.LicenciaTransitoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.LicenciaTransito;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Richard Mejia
 * 
 * @date 12/07/2018
 */
public class ControladorLicenciaTransito extends HttpServlet {
    
    private String mensajeError;
    
    private static final String CONDUCTOR = "conductor";
    private static final String NUMERO_LICENCIA = "numeroLicencia";
    private static final String CATEGORIA = "categoria";
    private static final String FECHA_EXPEDICION = "fechaExpedicion";
    private static final String VIGENCIA = "vigencia";
    private static final String OFICINA_TRANSITO = "oficinaTransito";
    private static final String OBSERVACIONES = "observaciones";
    private static final String ESTADO = "estado";
    
    private static final String GUARDAR_LICENCIA = "/guardarLicenciaTransito";
    private static final String VER_LICENCIA= "/verLicenciaTransito";
    private static final String EDITAR_LICENCIA = "/editarLicenciaTransito";
    private static final String CAMBIAR_ESTADO_LICENCIA = "/cambiarEstadoLicenciaTransito";
    
    private static final String NUEVA_LICENCIA_TRANSITO_JSP = "/app/licencia_conduccion/nuevaLicenciaTransito.jsp";
    private static final String VER_LICENCIA_TRANSITO_JSP = "/app/licencia_conduccion/verLicenciaTransito.jsp";
    private static final String LISTADO_LICENCIA_TRANSITO_JSP = "/app/licencia_conduccion/listadoLicenciaTransito.jsp";

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
            out.println("<title>Servlet ControladorLicenciaTransito</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorLicenciaTransito at " + request.getContextPath() + "</h1>");
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

        if (requestUri.endsWith(GUARDAR_LICENCIA)) {
            mensajeError = "";
            url = guardarLicenciaTransito(request);
        } else if (requestUri.endsWith(VER_LICENCIA)) {
            url = verLicenciaTransito(request);
        } else if (requestUri.endsWith(EDITAR_LICENCIA)) {
            url = editarLicenciaTransito(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_LICENCIA)) {
            url = cambiarEstadoLicenciaTransito(request);
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
     * Método encargado de guardar una licencia de tránsito.
     * @param request
     * @return 
     */
    public String guardarLicenciaTransito(HttpServletRequest request) {
        try {
            String conductor = request.getParameter(CONDUCTOR);
            String numeroLicencia = request.getParameter(NUMERO_LICENCIA);
            String categoriaLicencia = request.getParameter(CATEGORIA);
            String oficinaTransito = request.getParameter(OFICINA_TRANSITO);
            String fechaExpedicion = request.getParameter(FECHA_EXPEDICION);
            String vigencia = request.getParameter(VIGENCIA);
            String observaciones = request.getParameter(OBSERVACIONES);
            LicenciaTransito licenciaTransito = new LicenciaTransito();
            licenciaTransito.setFk_conductor(Integer.parseInt(conductor));
            licenciaTransito.setNumeroLicencia(numeroLicencia);
            licenciaTransito.setFk_categoria(Integer.parseInt(categoriaLicencia));
            licenciaTransito.setFk_oficina_transito(Integer.parseInt(oficinaTransito));
            licenciaTransito.setFechaExpedicion(convertirFecha(fechaExpedicion));
            licenciaTransito.setVigencia(convertirFecha(vigencia));
            licenciaTransito.setObservaciones(observaciones);
            licenciaTransito.setEstado(1);
            
            if (validarLicenciaTransito(licenciaTransito)) {
                boolean resultado = LicenciaTransitoBD.insert(licenciaTransito);

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
                    request.setAttribute("msg", mensajeError);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return NUEVA_LICENCIA_TRANSITO_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de validar que los atributos obligatorios de una licencia
     * de transito tengan valores apropiados.
     * @param licenciaTransito Objeto de tipo licencia de transito que se va a validar.
     * @return 
     */
    private boolean validarLicenciaTransito(LicenciaTransito licenciaTransito) {
        if (licenciaTransito != null) {
            if (licenciaTransito.getNumeroLicencia() != null || !licenciaTransito.getNumeroLicencia().isEmpty()) {
                if (licenciaTransito.getFk_conductor() > 0) {
                    if (licenciaTransito.getFk_categoria() > 0) {
                        if (licenciaTransito.getFk_oficina_transito() > 0) {
                            if (licenciaTransito.getEstado() == 0 || licenciaTransito.getEstado() == 1) {
                                if (licenciaTransito.getFechaExpedicion().before(new Date())) {
                                    if (licenciaTransito.getVigencia().after(licenciaTransito.getFechaExpedicion())) {
                                        if (LicenciaTransitoBD.conductorPuedeRegistrarLicencia(licenciaTransito)) {
                                            return true;
                                        } else {
                                            mensajeError += "El conductor ya tiene una licencia vigente "
                                                    + "para la categoría seleccionada.";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
    
    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de convertir una cadena con el patron yyyy-MM-dd a un
     * objeto de tipo Date.
     * @param cadena Cadena de la que se toma el valor para crear un objeto Date.
     * @return 
     */
    private Date convertirFecha(String cadena) {
        if (cadena != null && !cadena.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            String[] datos = null;
            
            if (cadena.contains("/")) {
                datos = cadena.split("/");
            } else if (cadena.contains("-")) {
                datos = cadena.split("-");
            }
            
            calendar.set(Calendar.YEAR, Integer.parseInt(datos[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(datos[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(datos[2]));
            
            return calendar.getTime();
        } else {
            return new Date();
        }
    }

    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de mostrar los detalles de una licencia de tránsito.
     * @param request
     * @return 
     */
    public String verLicenciaTransito(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter("id");
            LicenciaTransito licenciaTransito = new LicenciaTransito();
            licenciaTransito.setId(Long.parseLong(id));
            LicenciaTransito licenciaEncontrada = LicenciaTransitoBD.selectByOne(licenciaTransito);
            
            if (licenciaEncontrada != null) {                
                darFormatoCadenaFecha(licenciaEncontrada);
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("licenciaTransito", licenciaEncontrada);
                request.getSession().setAttribute("select", select);
                url = VER_LICENCIA_TRANSITO_JSP;
            } else {
                url = LISTADO_LICENCIA_TRANSITO_JSP;
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
     * Método encargado de editar los valores de una licencia de tránsito.
     * @param request
     * @return 
     */
    public String editarLicenciaTransito(HttpServletRequest request) {
        try {
            String id = request.getParameter("id_edit");
            String conductor = request.getParameter(CONDUCTOR);
            String numeroLicencia = request.getParameter(NUMERO_LICENCIA);
            String categoriaLicencia = request.getParameter(CATEGORIA);
            String oficinaTransito = request.getParameter(OFICINA_TRANSITO);
            String fechaExpedicion = request.getParameter(FECHA_EXPEDICION);
            String vigencia = request.getParameter(VIGENCIA);
            String observaciones = request.getParameter(OBSERVACIONES);
            LicenciaTransito licenciaTransito = new LicenciaTransito();
            licenciaTransito.setId(Long.parseLong(id));
            licenciaTransito.setFk_conductor(Integer.parseInt(conductor));
            licenciaTransito.setNumeroLicencia(numeroLicencia);
            licenciaTransito.setFk_categoria(Integer.parseInt(categoriaLicencia));
            licenciaTransito.setFk_oficina_transito(Integer.parseInt(oficinaTransito));
            licenciaTransito.setFechaExpedicion(convertirFecha(fechaExpedicion));
            licenciaTransito.setVigencia(convertirFecha(vigencia));
            licenciaTransito.setObservaciones(observaciones);
            licenciaTransito.setEstado(1);
            darFormatoCadenaFecha(licenciaTransito);            

            if (validarLicenciaTransito(licenciaTransito)) {
                boolean resultado = LicenciaTransitoBD.update(licenciaTransito);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("licenciaTransito", licenciaTransito);
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

        return VER_LICENCIA_TRANSITO_JSP;
    }
    
    /**
     * @author Richard Mejia 
     * @date 13/07/2018
     * Método que se encarga de dar formato a las fechas de expedición y vigencia
     * de una licencia de transito.
     * Se realiz este metodo porque los campos de tipo input>date no se estaban
     * actualizando al momento de realizar la actualizacion de la informacion
     * de una licencia de transito.
     * @param licenciaTransito Objeto tipo licencia de transito del cual se tomaran las fechas.
     */
    private void darFormatoCadenaFecha(LicenciaTransito licenciaTransito) {
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            licenciaTransito.setFechaExpedicionString(formatoFecha.format(licenciaTransito.getFechaExpedicion()));
            licenciaTransito.setVigenciaString(formatoFecha.format(licenciaTransito.getVigencia()));
    }
    
    /**
     * @author Richard Mejia
     * @date 12/07/2018
     * Método encargado de cambiar el estado a una licencia de tránsito.
     * @param request
     * @return 
     */
    public String cambiarEstadoLicenciaTransito(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String estado = request.getParameter(ESTADO);
            LicenciaTransito licenciaTransito = new LicenciaTransito();
            licenciaTransito.setId(Long.parseLong(id));
            licenciaTransito.setEstado(Integer.parseInt(estado));
            boolean resultado = LicenciaTransitoBD.updateEstado(licenciaTransito);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", MENSAJE_EXITO);
                request.setAttribute("licenciaTransito", licenciaTransito);
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
        }

        return LISTADO_LICENCIA_TRANSITO_JSP;
    }

}
