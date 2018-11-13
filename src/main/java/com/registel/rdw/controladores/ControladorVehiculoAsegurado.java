/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.PolizaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.VehiculoAseguradoBD;
import com.registel.rdw.logica.Poliza;
import com.registel.rdw.logica.VehiculoAsegurado;
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
 * @date 16/07/2018
 */
public class ControladorVehiculoAsegurado extends HttpServlet {
    
    private String mensajeError;

    private static final String ID = "id";
    private static final String POLIZA = "poliza";
    private static final String VEHICULO = "vehiculo";
    private static final String NUMERO_POLIZA = "numeroPoliza";
    private static final String INICIO_VIGENCIA = "inicioVigencia";
    private static final String FIN_VIGENCIA = "finVigencia";
    private static final String VALOR_PRIMA = "valorPrima";
    private static final String ESTADO = "estado";
    private static final String ID_EDIT = "id_edit";

    private static final String GUARDAR_VEHICULO_ASEGURADO = "/guardarVehiculoAsegurado";
    private static final String VER_VEHICULO_ASEGURADO = "/verVehiculoAsegurado";
    private static final String EDITAR_VEHICULO_ASEGURADO = "/editarVehiculoAsegurado";
    private static final String CAMBIAR_ESTADO_VEHICULO_ASEGURADO = "/cambiarEstadoVehiculoAsegurado";

    private static final String NUEVO_VEHICULO_ASEGURADO_JSP = "/app/polizas/nuevoVehiculoAsegurado.jsp";
    private static final String VER_VEHICULO_ASEGURADO_JSP = "/app/polizas/verVehiculoAsegurado.jsp";
    private static final String LISTADO_VEHICULO_ASEGURADO_JSP = "/app/polizas/listadoVehiculoAsegurado.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente.";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro.";

    private static final int VALOR_POSITIVO = 1;
    private static final int CANTIDAD_ATRIBUTOS = 9;

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
            out.println("<title>Servlet ControladorAsegurarVehiculo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorAsegurarVehiculo at " + request.getContextPath() + "</h1>");
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

        if (requestUri.endsWith(GUARDAR_VEHICULO_ASEGURADO)) {
            url = guardarVehiculoAsegurado(request);
        } else if (requestUri.endsWith(VER_VEHICULO_ASEGURADO)) {
            url = verVehiculoAsegurado(request);
        } else if (requestUri.endsWith(EDITAR_VEHICULO_ASEGURADO)) {
            url = editarVehiculoAsegurado(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_VEHICULO_ASEGURADO)) {
            url = cambiarEstadoVehiculoAsegurado(request);
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
     * @date 16/07/2018 
     * Método encargado de guardar un vehiculo asegurado.
     * @param request
     * @return
     */
    public String guardarVehiculoAsegurado(HttpServletRequest request) {
        try {
            String[] parametrosRequest = obtenerParametrosRequest(request);
            VehiculoAsegurado vehiculoAsegurado = new VehiculoAsegurado();
            vehiculoAsegurado.setFkPoliza(Long.parseLong(parametrosRequest[1]));
            vehiculoAsegurado.setFkVehiculo(Integer.parseInt(parametrosRequest[2]));
            vehiculoAsegurado.setNumeroPoliza(parametrosRequest[3]);
            vehiculoAsegurado.setInicioVigencia(convertirFecha(parametrosRequest[4]));
            vehiculoAsegurado.setFinVigencia(convertirFecha(parametrosRequest[5]));
            vehiculoAsegurado.setValorPrima(Double.parseDouble(parametrosRequest[6]));
            vehiculoAsegurado.setEstado(VALOR_POSITIVO);

            if (validarVehiculoAsegurado(vehiculoAsegurado)) {
                boolean resultado = VehiculoAseguradoBD.insert(vehiculoAsegurado);

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

        return NUEVO_VEHICULO_ASEGURADO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 16/07/2018 
     * Método encargado de validar que los atributos
     * obligatorios de un vehiculo asegurado tengan valores apropiados.
     * @param vehiculoAsegurado
     * @return
     */
    private boolean validarVehiculoAsegurado(VehiculoAsegurado vehiculoAsegurado) {
        try {
            mensajeError = "";

        if (vehiculoAsegurado == null) {
            mensajeError = "El vehículo asegurado es null.";
            return false;
        }

        if (vehiculoAsegurado.getFkPoliza() < 1) {
            mensajeError = "No se envió póliza.";
            return false;
        } else {
            Poliza poliza = PolizaBD.selectByOne(vehiculoAsegurado.getFkPoliza());
            
            if (poliza == null || poliza.getId() <= 0) {
                mensajeError = "La póliza enviada no existe.";
                return false;
            }
        }

        if (vehiculoAsegurado.getFkVehiculo() < 1) {
            mensajeError = "No se envió vehículo.";
            return false;
        } else {
            boolean existeVehiculo = MovilBD.selectByOne(vehiculoAsegurado.getFkVehiculo());
            
            if (!existeVehiculo) {
                mensajeError = "El vehículo no existe.";
                return false;
            }
        }

        if (vehiculoAsegurado.getNumeroPoliza() == null || vehiculoAsegurado.getNumeroPoliza().isEmpty()
                || vehiculoAsegurado.getNumeroPoliza().length() > 50) {

            if (vehiculoAsegurado.getNumeroPoliza() != null && vehiculoAsegurado.getNumeroPoliza().isEmpty()) {
                mensajeError = "No se envió número de póliza.";
            } else if (vehiculoAsegurado.getNumeroPoliza() != null && vehiculoAsegurado.getNumeroPoliza().length() > 50) {
                mensajeError = "El número de póliza ingresado supera el límite de caracteres permitidos.";
            }

            return false;
        }

        if (vehiculoAsegurado.getInicioVigencia() == null) {
            mensajeError = "No se envió la fecha de inicio de la vigencia de la póliza.";
            return false;
        }
        
        if (vehiculoAsegurado.getFinVigencia() == null) {
            mensajeError = "No se envió la fecha de inicio de la vigencia de la póliza";
            return false;
        }
        
        if (vehiculoAsegurado.getFinVigencia().before(vehiculoAsegurado.getInicioVigencia())) {
            mensajeError = "La fecha de fin de vigencia no puede ser menor a la fecha de inicio de vigencia.";
            return false;
        }
        
        if (vehiculoAsegurado.getValorPrima() <= 0) {
            mensajeError = "El valor de la prima total no es válido.";
            return false;
        }
        
        if (!VehiculoAseguradoBD.vehiculoEsAsegurable(vehiculoAsegurado)) {
            mensajeError = "El vehículo ya tiene una póliza cuya vigencia se sobrepone con las fechas seleccionadas.";
            return false;
        }
        
        return !(vehiculoAsegurado.getEstado() != 0 && vehiculoAsegurado.getEstado() != 1);
        } catch (ExisteRegistroBD e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return false;
    }

    /**
     * @author Richard Mejia
     * @date 16/07/2018 
     * Método encargado de mostrar los detalles de un vehiculo asegurado.
     * @param request
     * @return
     */
    public String verVehiculoAsegurado(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            VehiculoAsegurado vehiculoAsegurado = VehiculoAseguradoBD.selectByOne(Long.parseLong(id));

            if (vehiculoAsegurado != null) {
                darFormatoCadenaFecha(vehiculoAsegurado);
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("vehiculoAsegurado", vehiculoAsegurado);
                request.getSession().setAttribute("select", select);
                url = VER_VEHICULO_ASEGURADO_JSP;
            } else {
                url = LISTADO_VEHICULO_ASEGURADO_JSP;
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
     * @date 16/07/2018 
     * Método encargado de editar los valores de un vehiculo asegurado.
     * @param request
     * @return
     */
    public String editarVehiculoAsegurado(HttpServletRequest request) {
        try {
            String[] parametrosRequest = obtenerParametrosRequest(request);
            VehiculoAsegurado vehiculoAsegurado = new VehiculoAsegurado();
            vehiculoAsegurado.setFkPoliza(Long.parseLong(parametrosRequest[1]));
            vehiculoAsegurado.setFkVehiculo(Integer.parseInt(parametrosRequest[2]));
            vehiculoAsegurado.setNumeroPoliza(parametrosRequest[3]);
            vehiculoAsegurado.setInicioVigencia(convertirFecha(parametrosRequest[4]));
            vehiculoAsegurado.setFinVigencia(convertirFecha(parametrosRequest[5]));
            vehiculoAsegurado.setValorPrima(Double.parseDouble(parametrosRequest[6]));
            vehiculoAsegurado.setEstado(VALOR_POSITIVO);
            vehiculoAsegurado.setId(Long.parseLong(parametrosRequest[8]));

            if (validarVehiculoAsegurado(vehiculoAsegurado)) {
                boolean resultado = VehiculoAseguradoBD.update(vehiculoAsegurado);

                if (resultado) {
                    darFormatoCadenaFecha(vehiculoAsegurado);
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("vehiculoAsegurado", vehiculoAsegurado);
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

        return VER_VEHICULO_ASEGURADO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 13/07/2018 
     * Método encargado de cambiar el estado a un vehiculo asegurado.
     * @param request
     * @return
     */
    public String cambiarEstadoVehiculoAsegurado(HttpServletRequest request) {
        try {
            long id = Long.parseLong(request.getParameter(ID));
            int estado = Integer.parseInt(request.getParameter(ESTADO));
            VehiculoAsegurado vehiculoAsegurado = new VehiculoAsegurado();
            vehiculoAsegurado.setId(id);
            vehiculoAsegurado.setEstado(estado);
            boolean resultado = VehiculoAseguradoBD.updateEstado(vehiculoAsegurado);

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

        return LISTADO_VEHICULO_ASEGURADO_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 16/07/2018
     * Metodo encargado de obtener todos los atributos de una petición.
     * @param request
     * @return 
     */
    private String[] obtenerParametrosRequest(HttpServletRequest request) {
        String id = request.getParameter(ID);
        String poliza = request.getParameter(POLIZA);
        String vehiculo = request.getParameter(VEHICULO);
        String numeroPoliza = request.getParameter(NUMERO_POLIZA);
        String inicioVigencia = request.getParameter(INICIO_VIGENCIA);
        String finVigencia = request.getParameter(FIN_VIGENCIA);
        String valorPrima = request.getParameter(VALOR_PRIMA);
        String estado = request.getParameter(ESTADO);
        String id_edit = request.getParameter(ID_EDIT);
        String[] respuesta = new String[CANTIDAD_ATRIBUTOS];
        respuesta[0] = id;
        respuesta[1] = poliza;
        respuesta[2] = vehiculo;
        respuesta[3] = numeroPoliza;
        respuesta[4] = inicioVigencia;
        respuesta[5] = finVigencia;
        respuesta[6] = valorPrima;
        respuesta[7] = estado;
        respuesta[8] = id_edit;

        return respuesta;
    }
    
    
    /**
     * @author Richard Mejia
     * @date 16/07/2018
     * Método encargado de convertir una cadena con el patron yyyy-MM-dd a un
     * objeto de tipo Date.
     * @param cadena Cadena con la cual se va a crear un objeto de tipo Date.
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
     * @date 16/07/2018
     * Método que se encarga de dar formato a las fechas de inicio y fin de vigencia
     * de vehiculo asegurado.
     * Se realiz este metodo porque los campos de tipo input>date no se estaban
     * actualizando al momento de realizar la actualizacion de la informacion
     * de una licencia de transito.
     * @param vehiculoAsegurado Objeto de tipo vehiculo asegurado del cual se tomaran las fechas.
     */
    private void darFormatoCadenaFecha(VehiculoAsegurado vehiculoAsegurado) {
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        vehiculoAsegurado.setInicioVigenciaBack(formatoFecha.format(vehiculoAsegurado.getInicioVigencia()));
        vehiculoAsegurado.setFinVigenciaBack(formatoFecha.format(vehiculoAsegurado.getFinVigencia()));
    }
    
}
