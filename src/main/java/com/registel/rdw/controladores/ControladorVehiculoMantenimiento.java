/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.MantenimientoBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.VehiculoMantenimientoBD;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Mantenimiento;
import com.registel.rdw.logica.VehiculoMantenimiento;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @date 03/08/2018
 */
@WebServlet(name = "ControladorVehiculoMantenimiento", urlPatterns = {"/ControladorVehiculoMantenimiento"})
public class ControladorVehiculoMantenimiento extends HttpServlet {

    private String mensajeError;

    private static final String ID = "id";
    private static final String FK_VEHICULO = "fkVehiculo";
    private static final String FK_MANTENIMIENTO = "fkMantenimiento";
    private static final String VALOR_INICIAL_MANTENIMIENTO = "valorInicialMantenimiento";
    private static final String ID_EDIT = "id_edit";
    private static final String ESTADO = "estado";

    private static final String GUARDAR_VEHICULO_MANTENIMIENTO = "/guardarVehiculoMantenimiento";
    private static final String VER_VEHICULO_MANTENIMIENTO = "/verVehiculoMantenimiento";
    private static final String EDITAR_VEHICULO_MANTENIMIENTO = "/editarVehiculoMantenimiento";
    private static final String CAMBIAR_ESTADO_VEHICULO_MANTENIMIENTO = "/cambiarEstadoVehiculoMantenimiento";
    private static final String ELIMINAR_VEHICULO_MANTENIMIENTO = "/eliminarVehiculoMantenimiento";
    private static final String CARGAR_VEHICULOS_CONFIGURADOS_MTO = "/cargarVehiculosConfiguradosMto";

    private static final String NUEVO_VEHICULO_MANTENIMIENTO_JSP = "/app/mantenimiento/nuevoVehiculoMantenimiento.jsp";
    private static final String VER_VEHICULO_MANTENIMIENTO_JSP = "/app/mantenimiento/verVehiculoMantenimiento.jsp";
    private static final String LISTADO_VEHICULO_MANTENIMIENTO_JSP = "/app/mantenimiento/listadoVehiculoMantenimiento.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro";

    private static final int VALOR_POSITIVO = 1;
    private static final int VALOR_CERO = 0;
    
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
            out.println("<title>Servlet ControladorVehiculoMantenimiento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorVehiculoMantenimiento at " + request.getContextPath() + "</h1>");
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

        if (requestUri.endsWith(GUARDAR_VEHICULO_MANTENIMIENTO)) {
            url = guardarVehiculoMantenimiento(request);
        } else if (requestUri.endsWith(VER_VEHICULO_MANTENIMIENTO)) {
            url = verVehiculoMantenimiento(request);
        } else if (requestUri.endsWith(EDITAR_VEHICULO_MANTENIMIENTO)) {
            url = editarVehiculoMantenimiento(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_VEHICULO_MANTENIMIENTO)) {
            url = cambiarEstadoVehiculoMantenimiento(request);
        } else if (requestUri.endsWith(ELIMINAR_VEHICULO_MANTENIMIENTO)) {
            url = eliminarVehiculoMantenimiento(request);
        } else if (requestUri.endsWith(CARGAR_VEHICULOS_CONFIGURADOS_MTO)) {
            cargarVehiculosMantenimiento(request, response);
            return;
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
        processRequest(request, response);
    }
    
    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Método encargado de guardar un mantenimiento.
     * @param request
     * @return
     */
    public String guardarVehiculoMantenimiento(HttpServletRequest request) {
        try {
            VehiculoMantenimiento entidadConfigurable = asignarParametrosRequest(request, false);

            if (validarVehiculoMantenimiento(entidadConfigurable)) {
                boolean resultado = VehiculoMantenimientoBD.insert(entidadConfigurable);

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

        return NUEVO_VEHICULO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Método encargado de validar que los atributos
     * obligatorios de un mantenimiento tengan valores apropiados.
     * @param vehiculoAsegurado
     * @return
     */
    private boolean validarVehiculoMantenimiento(VehiculoMantenimiento entidadConfigurable) {
        try {
            mensajeError = "";
            Mantenimiento mantenimiento;

            if (entidadConfigurable == null) {
                mensajeError = "La entidad enviada es null";
                return false;
            }

            if (entidadConfigurable.getFkVehiculo() < 1) {
                mensajeError = "No se envió el vehículo asociado al mantenimiento";
                return false;
            } else {
                boolean existeVehiculo = MovilBD.selectByOne(entidadConfigurable.getFkVehiculo());

                if (!existeVehiculo) {
                    mensajeError = "El vehículo no existe";
                    return false;
                }
            }

            if (entidadConfigurable.getFkMantenimiento() < 1) {
                mensajeError = "No se envió el mantenimiento";
                return false;
            } else {
                mantenimiento = MantenimientoBD.selectByOne(entidadConfigurable.getFkMantenimiento());
                
                if (mantenimiento == null || mantenimiento.getId() <= 0) {
                    mensajeError = "El mantenimiento no existe";
                    return false;
                }
            }
            
            if (entidadConfigurable.getValorInicialMantenimiento() < 0) {
                mensajeError = "El valor inicial del mantenimiento debe ser mayor o igual a 0";
                return false;
            } else {
                
                if (entidadConfigurable.getValorInicialMantenimiento() > mantenimiento.getValorPrimeraNotificacion()) {
                    mensajeError = String.format("El valor inicial del mantenimiento es mayor al valor de la primera "
                            + "notificación (%1$d)", mantenimiento.getValorPrimeraNotificacion());
                    return false;
                }
            }
            
            VehiculoMantenimiento yaExisteVehiculoMto = VehiculoMantenimientoBD.exits(entidadConfigurable);
            
            if (yaExisteVehiculoMto != null) {
                mensajeError = "El vehículo ya fue asociado al mantenimiento";
                return false;
            }
            
            entidadConfigurable.setCantidadNotificacionesPendientes(mantenimiento.getCantidadNotificaciones());
            
            return !(entidadConfigurable.getEstado() != 0 && entidadConfigurable.getEstado() != 1);
        } catch (ExisteRegistroBD e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Método encargado de mostrar los detalles de un mantenimiento.
     * @param request
     * @return
     */
    public String verVehiculoMantenimiento(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            VehiculoMantenimiento entidadDefinida = VehiculoMantenimientoBD.selectByOne(Long.parseLong(id));

            if (entidadDefinida != null && entidadDefinida.getId() > 0) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("vehiculoMantenimiento", entidadDefinida);
                request.getSession().setAttribute("select", select);
                url = VER_VEHICULO_MANTENIMIENTO_JSP;
            } else {
                url = LISTADO_VEHICULO_MANTENIMIENTO_JSP;
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
     * Método encargado de editar los valores de un mantenimiento.
     * @param request
     * @return
     */
    public String editarVehiculoMantenimiento(HttpServletRequest request) {
        try {
            VehiculoMantenimiento entidadConfigurable = asignarParametrosRequest(request, true);

            if (validarVehiculoMantenimiento(entidadConfigurable)) {
                boolean resultado = VehiculoMantenimientoBD.update(entidadConfigurable);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("vehiculoMantenimiento", entidadConfigurable);
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

        return VER_VEHICULO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 23/07/2018 
     * Método encargado de cambiar el estado a un mantenimiento.
     * @param request
     * @return
     */
    public String cambiarEstadoVehiculoMantenimiento(HttpServletRequest request) {
        try {
            EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID)));
            entidadConfigurable.setEstado(Integer.parseInt(request.getParameter(ESTADO)));
            boolean resultado = VehiculoMantenimientoBD.updateEstado(entidadConfigurable);

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
        
        return LISTADO_VEHICULO_MANTENIMIENTO_JSP;
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
    private VehiculoMantenimiento asignarParametrosRequest(HttpServletRequest request, boolean asignarId) {
        VehiculoMantenimiento entidadConfigurable = new VehiculoMantenimiento();
        entidadConfigurable.setFkVehiculo(Integer.parseInt(request.getParameter(FK_VEHICULO)));
        entidadConfigurable.setFkMantenimiento(Long.parseLong(request.getParameter(FK_MANTENIMIENTO)));
        entidadConfigurable.setValorInicialMantenimiento(Integer.parseInt(
                request.getParameter(VALOR_INICIAL_MANTENIMIENTO)));
        entidadConfigurable.setFechaProximaNotificacion(new Date());
        entidadConfigurable.setEstado(VALOR_POSITIVO);

        if (asignarId) {
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID_EDIT)));
        }

        return entidadConfigurable;
    }

    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método que se encarga de realizar el borrado lógico de un vehículo asociado a un mantenimiento.
     * @param request
     * @return 
     */
    private String eliminarVehiculoMantenimiento(HttpServletRequest request) {
        try {
            EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID)));
            entidadConfigurable.setEstado(Integer.parseInt(request.getParameter(ESTADO)));
            boolean resultado = VehiculoMantenimientoBD.delete(entidadConfigurable);

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
        
        return LISTADO_VEHICULO_MANTENIMIENTO_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de consultar los vehiculos asociados a un mantenimiento.
     * Sólo se cargan los registros cuyo estado es ACTIVO y no han sido eliminados.
     * @param request
     * @param response 
     */
    private void cargarVehiculosMantenimiento(HttpServletRequest request, HttpServletResponse response) {
        try {
            long idMantenimiento = Long.parseLong(request.getParameter("idMantenimiento"));
            List<VehiculoMantenimiento> resultado = VehiculoMantenimientoBD.select(idMantenimiento);

            if (resultado.size() > VALOR_CERO) {
                response.setCharacterEncoding("UTF-8");
                PrintWriter printWriter = response.getWriter();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("response", "OK");
                jsonResponse.put("listaVehiculosMto", resultado);
                printWriter.println(jsonResponse.toString());
                printWriter.flush();
            }
        } catch (IOException | NumberFormatException | JSONException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
