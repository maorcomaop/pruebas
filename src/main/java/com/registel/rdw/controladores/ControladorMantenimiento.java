/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.MantenimientoBD;
import com.registel.rdw.datos.NotificacionesBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.TipoEventoMantenimientoBD;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Mantenimiento;
import com.registel.rdw.logica.Notificacion;
import com.registel.rdw.logica.TipoEventoMantenimiento;
import com.registel.rdw.logica.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Richard Mejia
 *
 * @date 19/07/2018
 */
@WebServlet(name = "ControladorMantenimiento", urlPatterns = {"/ControladorMantenimiento"})
public class ControladorMantenimiento extends HttpServlet {

    private String mensajeError;

    private static final String ID = "id";
    private static final String TIPO_EVENTO_MANTENIMIENTO = "tipoEventoMantenimiento";
    private static final String UNIDADES = "unidades";
    private static final String REPETICION = "repeticion";
    private static final String INTERVALO = "intervalo";
    private static final String ACTIVAR_MANTENIMIENTO = "activarMantenimiento";
    private static final String VEHICULOS = "vehiculos";
    private static final String CORREOS = "correos";
    private static final String OBSERVACIONES = "observaciones";
    private static final String ESTADO = "estado";
    private static final String ID_EDIT = "id_edit";
    private static final String FECHA_ACTIVACION = "fechaActivacion";
    private static final String CANTIDAD_NOTIFICACIONES = "cantidadNotificaciones";
    private static final String NOMBRE = "nombre";

    private static final String GUARDAR_MANTENIMIENTO = "/guardarMantenimiento";
    private static final String VER_MANTENIMIENTO = "/verMantenimiento";
    private static final String EDITAR_MANTENIMIENTO = "/editarMantenimiento";
    private static final String CAMBIAR_ESTADO_MANTENIMIENTO = "/cambiarEstadoMantenimiento";
    private static final String CARGAR_UNIDAD_MEDIDA = "/cargarUnidadMedida";
    private static final String EJECUTAR_MANTENIMIENTO = "/ejecutarMantenimiento";

    private static final String NUEVO_MANTENIMIENTO_JSP = "/app/mantenimiento/nuevoMantenimiento.jsp";
    private static final String VER_MANTENIMIENTO_JSP = "/app/mantenimiento/verMantenimiento.jsp";
    private static final String LISTADO_MANTENIMIENTO_JSP = "/app/mantenimiento/listadoMantenimiento.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente.";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro.";

    private static final int VALOR_CERO = 0;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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

        String requestUri = request.getRequestURI();

        if (requestUri == null || requestUri.isEmpty()) {
            return;
        }

        String url = "";

        getServletContext().getRequestDispatcher(url).forward(request, response);
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

        if (requestUri.endsWith(CARGAR_UNIDAD_MEDIDA)) {
            cargarUnidadMedida(request, response);
            return;
        } else if (requestUri.endsWith(GUARDAR_MANTENIMIENTO)) {
            url = guardarMantenimiento(request);
        } else if (requestUri.endsWith(VER_MANTENIMIENTO)) {
            url = verMantenimiento(request);
        } else if (requestUri.endsWith(EDITAR_MANTENIMIENTO)) {
            url = editarMantenimiento(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_MANTENIMIENTO)) {
            url = cambiarEstadoMantenimiento(request);
        } else if (requestUri.endsWith(EJECUTAR_MANTENIMIENTO)) {
            consultarNotificaciones(request, response);
            return;
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Metodo encargado de consultar el nombre de la unidad de
     * medida del tipo de evento de mantenimiento que se solicita desde la
     * vista.
     * @param request
     * @param response
     */
    public void cargarUnidadMedida(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("tipoEventoMantenimiento") == null) {
                return;
            }
            long idTipoEvento = Long.parseLong(request.getParameter("tipoEventoMantenimiento"));
            List<TipoEventoMantenimiento> eventos = TipoEventoMantenimientoBD.select();
            TipoEventoMantenimiento tipoEventoMantenimientoTmp;
            int eventosSize = eventos.size();

            for (int i = 0; i < eventosSize; i++) {
                tipoEventoMantenimientoTmp = eventos.get(i);

                if (tipoEventoMantenimientoTmp != null && tipoEventoMantenimientoTmp.getId() == idTipoEvento) {
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter printWriter = response.getWriter();
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("response", "OK");
                    jsonResponse.put("unidadMedida", tipoEventoMantenimientoTmp.getUnidadMedida());
                    jsonResponse.put("id", tipoEventoMantenimientoTmp.getId());
                    printWriter.println(jsonResponse.toString());
                    printWriter.flush();
                    return;
                }
            }
        } catch (IOException | NumberFormatException | JSONException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Método encargado de guardar un mantenimiento.
     * @param request
     * @return
     */
    public String guardarMantenimiento(HttpServletRequest request) {
        try {
            Mantenimiento entidadConfigurable = asignarParametrosRequest(request, false);

            if (validarMantenimiento(entidadConfigurable)) {
                boolean resultado = MantenimientoBD.insert(entidadConfigurable);

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

        return NUEVO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 Método encargado de validar que los atributos
     * obligatorios de un mantenimiento tengan valores apropiados.
     * @param vehiculoAsegurado Objeto de tipo vehiculo asegurado que se va a validar.
     * @return
     */
    private boolean validarMantenimiento(Mantenimiento entidadConfigurable) {
        try {
            mensajeError = "";

            if (entidadConfigurable == null) {
                mensajeError = "El mantenimiento es null.";
                return false;
            }

            if (entidadConfigurable.getFkTipoEventoMantenimiento() < 1) {
                mensajeError = "No se envió tipo de evento de mantenimiento.";
                return false;
            } else {
                TipoEventoMantenimiento tipoEventoMantenimiento
                        = TipoEventoMantenimientoBD.selectByOne(entidadConfigurable.getFkTipoEventoMantenimiento());

                if (tipoEventoMantenimiento == null || tipoEventoMantenimiento.getId() <= 0) {
                    mensajeError = "El tipo de evento de mantenimiento no existe.";
                    return false;
                }
            }

            if (entidadConfigurable.getValorPrimeraNotificacion() < 1) {
                mensajeError = "No se enviaron unidades para generar la primera notificación.";
                return false;
            } else if (entidadConfigurable.getValorPrimeraNotificacion() > 9999999) {
                mensajeError = "Cantidad no permitida para generar primera notificación (Unidad de medida).";
                return false;
            }

            if (!(entidadConfigurable.getRepetirNotificacion() == 0 || entidadConfigurable.getRepetirNotificacion() == 1)) {
                mensajeError = "No se estableció un valor que indique si se deben repetir las notificaciones.";
                return false;
            }

            if (entidadConfigurable.getIntervaloRepeticion() < 0 || entidadConfigurable.getIntervaloRepeticion() > 525600) {
                mensajeError = "Cantidad no permitida para el intervalo de repetición.";
                return false;
            }

            if (entidadConfigurable.getRepetirNotificacion() == 1 && entidadConfigurable.getIntervaloRepeticion() <= 0) {
                mensajeError = "Se estableción que el evento se repite, pero no se definió un intervalo para las repeticiones.";
                return false;
            }

            if (!(entidadConfigurable.getNotificacionActiva() == 0 || entidadConfigurable.getNotificacionActiva() == 1)) {
                mensajeError = "No se estableció si las alertas se inician o no después de guardar el mantenimiento";
                return false;
            }

            if (entidadConfigurable.getNombre() == null || entidadConfigurable.getNombre().isEmpty()) {
                mensajeError = "No se envió un nombre para el mantenimento.";
                return false;
            } else if (entidadConfigurable.getNombre().length() > 250) {
                mensajeError = "El nombre del mantenimiento supera los caracteres permitidos (250).";
                return false;
            }

            if (entidadConfigurable.getCorreosNotificacion() != null
                    && entidadConfigurable.getCorreosNotificacion().length() > 2000) {
                mensajeError = "El campo de correos supera el límite de caracteres permitido (2000).";
                return false;
            }

            if (entidadConfigurable.getCorreosNotificacion() != null
                    && entidadConfigurable.getCorreosNotificacion().length() > 0) {
                String[] correos = entidadConfigurable.getCorreosNotificacion().split(",");
                int correosSize = correos.length;

                for (int i = 0; i < correosSize; i++) {

                    if (!validarCorreo(correos[i])) {
                        mensajeError = String.format("La dirección de correo electrónico %s no es válida.", correos[i]);
                        return false;
                    }
                }
            }

            if (entidadConfigurable.getObservacionesNotificacion() != null
                    && entidadConfigurable.getObservacionesNotificacion().length() > 2000) {
                mensajeError = "El campo de observaciones supera el límite de caracteres permitido (2000)";
                return false;
            }

            if (entidadConfigurable.getFkUsuario() <= 0) {
                mensajeError = "No hay un usuario con sesión iniciada para crear el mantenimiento.";
                return false;
            }

            if (entidadConfigurable.getCantidadNotificaciones() < 0 || entidadConfigurable.getCantidadNotificaciones() > 10) {
                mensajeError = "La cantidad de notificaciones no es válido. Mínimo 1 y máximo 10";
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
     * @date 23/07/2018 
     * Método encargado de mostrar los detalles de un
     * mantenimiento.
     * @param request
     * @return
     */
    public String verMantenimiento(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            Mantenimiento mantenimiento = MantenimientoBD.selectByOne(Long.parseLong(id));

            if (mantenimiento != null) {
                darFormatoCadenaFecha(mantenimiento);
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("mantenimiento", mantenimiento);
                request.getSession().setAttribute("select", select);
                url = VER_MANTENIMIENTO_JSP;
            } else {
                url = LISTADO_MANTENIMIENTO_JSP;
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
     * @date 23/07/2018 
     * Método encargado de editar los valores de un
     * mantenimiento.
     * @param request
     * @return
     */
    public String editarMantenimiento(HttpServletRequest request) {
        try {
            Mantenimiento entidadConfigurable = asignarParametrosRequest(request, true);

            if (validarMantenimiento(entidadConfigurable)) {
                boolean resultado = MantenimientoBD.update(entidadConfigurable);

                if (resultado) {
                    darFormatoCadenaFecha(entidadConfigurable);
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("mantenimiento", entidadConfigurable);
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

        return VER_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Método encargado de cambiar el estado a un
     * mantenimiento.
     * @param request
     * @return
     */
    public String cambiarEstadoMantenimiento(HttpServletRequest request) {
        try {
            long id = Long.parseLong(request.getParameter(ID));
            int estado = Integer.parseInt(request.getParameter(ESTADO));
            EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
            entidadConfigurable.setId(id);
            entidadConfigurable.setEstado(estado);
            boolean resultado = MantenimientoBD.updateEstado(entidadConfigurable);

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

        return LISTADO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Metodo encargado de consultar los parametros de una
     * peticion y asignarlos al objeto correspondiente para generar un
     * mantenimiento.
     * @param request
     * @param asignarId
     * @return
     */
    private Mantenimiento asignarParametrosRequest(HttpServletRequest request, boolean asignarId) {
        Mantenimiento entidadConfigurable = new Mantenimiento();
        entidadConfigurable.setFkTipoEventoMantenimiento(Long.parseLong(request.getParameter(TIPO_EVENTO_MANTENIMIENTO)));
        entidadConfigurable.setValorPrimeraNotificacion(Integer.parseInt(request.getParameter(UNIDADES)));
        entidadConfigurable.setRepetirNotificacion(Integer.parseInt(request.getParameter(REPETICION)));
        entidadConfigurable.setIntervaloRepeticion(Integer.parseInt(request.getParameter(INTERVALO)));
        entidadConfigurable.setNotificacionActiva(Integer.parseInt(request.getParameter(ACTIVAR_MANTENIMIENTO)));
        entidadConfigurable.setCorreosNotificacion(request.getParameter(CORREOS));
        entidadConfigurable.setObservacionesNotificacion(request.getParameter(OBSERVACIONES));
        entidadConfigurable.setEstado(entidadConfigurable.getNotificacionActiva());
        entidadConfigurable.setFkUsuario(((Usuario) request.getSession().getAttribute("login")).getId());
        entidadConfigurable.setnombre(request.getParameter(NOMBRE));
        entidadConfigurable.setFechaActivacionNotificacion(convertirFecha(request.getParameter(FECHA_ACTIVACION)));
        entidadConfigurable.setEnProcesamiento(VALOR_CERO);
        entidadConfigurable.setCantidadNotificaciones(Integer.parseInt(request.getParameter(CANTIDAD_NOTIFICACIONES)));

        String[] vehiculosRequest = request.getParameterValues(VEHICULOS);

        if (vehiculosRequest != null && vehiculosRequest.length > 0) {
            StringBuilder vehiculos = new StringBuilder();
            int vehiculosRequestSize = vehiculosRequest.length;

            for (int i = 0; i < vehiculosRequestSize; i++) {

                if (i == (vehiculosRequestSize - 1)) {
                    vehiculos.append(vehiculosRequest[i]);
                } else {
                    vehiculos.append(vehiculosRequest[i]).append(",");
                }
            }
        }

        if (asignarId) {
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID_EDIT)));
        }

        return entidadConfigurable;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Metodo encargado de validar que una cadena tenga el
     * formato correcto para ser una direccion de correo electronico.
     * @param correo Cadena que se va a validar.
     * @return
     */
    private boolean validarCorreo(String correo) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(correo);

        return matcher.find();
    }

    /**
     * @author Richard Mejia
     * @date 12/07/2018 
     * Método encargado de convertir una cadena con el patron
     * yyyy-MM-dd a un objeto de tipo Date.
     * @param cadena Cadena con la que se va a crear un objeto tipo Date.
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
     * @date 13/07/2018 
     * Método que se encarga de dar formato a la fecha de
     * inicio del mantenimiento. Se realiz este metodo porque los campos de tipo
     * input>date no se estaban actualizando al momento de realizar la
     * actualizacion de la informacion de un mantenimiento.
     * @param licenciaTransito Objeto tipo mantenimiento al que se le consultaran las fechas.
     */
    private void darFormatoCadenaFecha(Mantenimiento mantenimiento) {
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        mantenimiento.setFechaActivacionNotificacionString((formatoFecha.format(mantenimiento.getFechaActivacionNotificacion())));
    }

    /**
     * @author Richard Mejia
     * @date 30/07/2018 Método que se encarga de ejecutar todos los
     * mantenimientos que están asociados al usuario que ha iniciado sesión y
     * enviar la respuesta a la pantalla del navegador.
     * @param request
     * @param response
     */
    private void consultarNotificaciones(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getSession().getAttribute("login") == null) {
                return;
            }

            int idUsuario = ((Usuario) request.getSession().getAttribute("login")).getId();
            List<Notificacion> notificaciones = NotificacionesBD.selectUpdate(idUsuario);
            int notificacionesSize = notificaciones.size();

            if (notificacionesSize > 0) {
                List<String> mensajesPantalla = new ArrayList<>();
                DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String fechaCadena;
                Notificacion notificacionTmp;

                for (int i = 0; i < notificacionesSize; i++) {
                    notificacionTmp = notificaciones.get(i);
                    fechaCadena = formatoFecha.format(notificacionTmp.getFecha());
                    notificacionTmp.setFechaCadena(fechaCadena);
                    notificacionTmp.setIdNotificacion();
                    mensajesPantalla.add(notificacionTmp.getMensaje());
                }

                response.setCharacterEncoding("UTF-8");
                PrintWriter printWriter = response.getWriter();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("response", "OK");
                jsonResponse.put("mensajes", mensajesPantalla);
                printWriter.println(jsonResponse.toString());
                printWriter.flush();

                List<Notificacion> notificacionesAnteriores = (List<Notificacion>) request.getSession().getAttribute("notificaciones");

                if (notificacionesAnteriores != null) {
                    notificaciones.addAll(notificacionesAnteriores);
                }

                request.getSession().setAttribute("notificaciones", notificaciones);
            }
        } catch (IOException | JSONException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
