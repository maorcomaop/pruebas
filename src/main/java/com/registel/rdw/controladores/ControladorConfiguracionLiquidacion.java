/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.ConfiguracionLiquidacionBD;
import com.registel.rdw.datos.PerfilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Perfil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.registel.rdw.utils.ConstantesSesion.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Richard Mejia
 * @date 01/10/2018
 * Controlador encargado de manejar las peticiones relacionadas con la configuración de liquidaciones.
 */
@WebServlet(name = "ControladorConfiguracionLiquidacion", urlPatterns = {"/ControladorConfiguracionLiquidacion"})
public class ControladorConfiguracionLiquidacion extends HttpServlet {

    private String mensajeError;

    private static final String PERFIL = "perfil";
    private static final String NOMBRE_CONFIGURACION_LIQUIDACION = "nombreConfiguracionLiquidacion";
    private static final String ETQ_PASAJEROS = "etqPasajeros";
    private static final String ETQ_TOTAL = "etqTotal";
    private static final String ETQ_REP_PASAJEROS = "etqRepPasajeros";
    private static final String ETQ_REP_TOTAL = "etqRepTotal";
    private static final String MODO_LIQUIDACION = "modoLiquidacion";
    private static final String SET_ETQ_PASAJEROS = "setEtqPasajeros";
    private static final String SET_ETQ_TOTAL = "setEtqTotal";
    private static final String SET_ETQ_REP_PASAJEROS = "setEtqRepPasajeros";
    private static final String SET_ETQ_REP_TOTAL = "setEtqRepTotal";
    private static final String GET_ETQ_PASAJEROS = "getEtqPasajeros";
    private static final String GET_ETQ_TOTAL = "getEtqTotal";
    private static final String GET_ETQ_REP_PASAJEROS = "getEtqRepPasajeros";
    private static final String GET_ETQ_REP_TOTAL = "getEtqRepTotal";
    
    private static final String GUARDAR_CONFIGURACION_LIQUIDACION = "/guardarConfiguracionLiquidacion";
    private static final String VER_CONFIGURACION_LIQUIDACION = "/verConfiguracionLiquidacion";
    private static final String EDITAR_CONFIGURACION_LIQUIDACION = "/editarConfiguracionLiquidacion";
    private static final String CAMBIAR_CONFIGURACION_LIQUIDACION = "/cambiarEstadoConfiguracionLiquidacion";

    private static final String NUEVA_CONFIGURACION_LIQUIDACION_JSP = 
            "/app/liquidacion/nuevaConfiguracionLiquidacion.jsp";
    private static final String VER_CONFIGURACION_LIQUIDACION_JSP = "/app/liquidacion/verConfiguracionLiquidacion.jsp";
    private static final String LISTADO_CONFIGURACIONES_LIQUIDACION_JSP = 
            "/app/liquidacion/listadoConfiguracionesLiquidacion.jsp";

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
        procesarPeticion(request, response);
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
        procesarPeticion(request, response);
    }
    // </editor-fold>
    
    /**
     * @author Richard Mejia
     * @date 01/10/2018
     * Método que se encarga de manejar todas las peticiones que llegan desde los JSP asociados.
     * Maneja tanto las peticiones GET como POST.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void procesarPeticion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (requestUri == null || requestUri.isEmpty()) {
            return;
        }

        String url = "";

        if (requestUri.endsWith(GUARDAR_CONFIGURACION_LIQUIDACION)) {
            url = guardarConfiguracionLiquidacion(request);
        } else if (requestUri.endsWith(VER_CONFIGURACION_LIQUIDACION)) {
            url = verConfiguracionLiquidacion(request);
        } else if (requestUri.endsWith(EDITAR_CONFIGURACION_LIQUIDACION)) {
            url = editarConfiguracionLiquidacion(request);
        } else if (requestUri.endsWith(CAMBIAR_CONFIGURACION_LIQUIDACION)) {
            url = cambiarEstadoConfiguracionLiquidacion(request);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    /**
     * @author Richard Mejia
     * @date 03/10/2018
     * Método encargado de guardar una configuración de liquidación de rdw.
     * @param request
     * @return
     */
    public String guardarConfiguracionLiquidacion(HttpServletRequest request) {
        try {
            ConfiguracionLiquidacion entidadConfigurable = asignarParametrosRequest(request, false);

            if (validarConfiguracionLiquidacion(entidadConfigurable)) {
                boolean resultado = ConfiguracionLiquidacionBD.insert(entidadConfigurable);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute(SELECT, select);
                    request.setAttribute(ID_INFO, 1);
                    request.setAttribute(MSG, MENSAJE_EXITO);
                } else {
                    request.setAttribute(ID_INFO, 2);
                    request.setAttribute(MSG, MENSAJE_ERROR);
                }
            } else {
                request.setAttribute(ID_INFO, 2);
                request.setAttribute(MSG, MENSAJE_ERROR + " " + mensajeError);
            }
        } catch (NumberFormatException e) {
            request.setAttribute(ID_INFO, 2);
            request.setAttribute(MSG, MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException ex) {
            request.setAttribute(ID_INFO, 2);
            request.setAttribute(MSG, MENSAJE_ERROR + "\n" + ex.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return NUEVA_CONFIGURACION_LIQUIDACION_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 03/10/2018
     * Método encargado de validar que los atributos
     * obligatorios de una configuración de liquidación tengan valores apropiados.
     * @param entidadConfigurable Objeto de tipo configuración de liquidación que se va a validar.
     * @return
     */
    private boolean validarConfiguracionLiquidacion(ConfiguracionLiquidacion entidadConfigurable) {
        try {
            mensajeError = "";

            if (entidadConfigurable == null) {
                mensajeError = "La configuración de la liquidación es null";
                return false;
            }
            
            if (entidadConfigurable.getNombreConfiguracionLiquidacion() == null || 
                    entidadConfigurable.getNombreConfiguracionLiquidacion().isEmpty()) {
                mensajeError = "No se asignó nombre a la configuración de la liquidación";
                return false;
            } else if (entidadConfigurable.getNombreConfiguracionLiquidacion().length() > 40) {
                mensajeError = "El nombre de la configuración de la liquidación supera los caracteres permitidos (20)";
                return false;
            }
            
            Method method;
            String variableString;
            
            for (int i = 1; i <= 5; i++) {
                method = ConfiguracionLiquidacion.class.getDeclaredMethod(GET_ETQ_PASAJEROS + i);
                variableString = (String) method.invoke(entidadConfigurable);
                
                if (variableString == null || variableString.isEmpty()) {
                    mensajeError = String.format("No se asignó valor a ETQ_PASAJEROS%1$s del formulario", i);
                    return false;
                } else if (variableString.length() > 50) {
                    mensajeError = String.format("ETQ_PASAJEROS%1$s del formulario supera los caracteres "
                            + "permitidos (50)", i);
                    return false;
                }
            }
            
            for (int i = 1; i <= 8; i++) {
                method = ConfiguracionLiquidacion.class.getDeclaredMethod(GET_ETQ_TOTAL + i);
                variableString = (String) method.invoke(entidadConfigurable);
                
                if (variableString == null || variableString.isEmpty()) {
                    mensajeError = String.format("No se asignó valor a ETQ_TOTAL%1$s del formulario", i);
                    return false;
                } else if (variableString.length() > 50) {
                    mensajeError = String.format("ETQ_TOTAL%1$s del formulario supera los caracteres "
                            + "permitidos (50)", i);
                    return false;
                }
            }
            
            for (int i = 1; i <= 5; i++) {
                method = ConfiguracionLiquidacion.class.getDeclaredMethod(GET_ETQ_REP_PASAJEROS + i);
                variableString = (String) method.invoke(entidadConfigurable);
                
                if (variableString == null || variableString.isEmpty()) {
                    mensajeError = String.format("No se asignó valor a ETQ_PASAJEROS%1$s del reporte", i);
                    return false;
                } else if (variableString.length() > 50) {
                    mensajeError = String.format("ETQ_PASAJEROS%1$s del reporte supera los caracteres "
                            + "permitidos (50)", i);
                    return false;
                }
            }
            
            for (int i = 1; i <= 8; i++) {
                method = ConfiguracionLiquidacion.class.getDeclaredMethod(GET_ETQ_REP_TOTAL + i);
                variableString = (String) method.invoke(entidadConfigurable);
                
                if (variableString == null || variableString.isEmpty()) {
                    mensajeError = String.format("No se asignó valor a ETQ_PASAJEROS%1$s del reporte", i);
                    return false;
                } else if (variableString.length() > 50) {
                    mensajeError = String.format("ETQ_TOTAL%1$s del reporte supera los caracteres "
                            + "permitidos (50)", i);
                    return false;
                }
            }
            
            if (!entidadConfigurable.isLiquidacionNormal() && !entidadConfigurable.isLiquidacionPorRuta() &&
                    !entidadConfigurable.isLiquidacionPorTiempo() && !entidadConfigurable.isLiquidacionPorTramo() &&
                    !entidadConfigurable.isLiquidacionSoloPasajeros()) {
                mensajeError = "Debe seleccionar un modo de liquidación.";
                return false;
            }
            
            if (entidadConfigurable.getEstado() == VALOR_POSITIVO) {
                
                if (ConfiguracionLiquidacionBD.existeRegistroActivo(entidadConfigurable)) {
                    mensajeError = "Ya existe una configuración activa, no puede crear otra en estado activo";
                    return false;
                }
            }
            
            if (entidadConfigurable.getFkPerfil() > 0) {
                Perfil perfil = PerfilBD.selectById(entidadConfigurable.getFkPerfil());
                
                if (perfil == null || perfil.getId() <= 0) {
                    mensajeError = "El perfil asociado no existe";
                return false;
                }
            } else {
                mensajeError = "No se asignó un perfil a la configuración de etiquetas de liquidación";
                return false;
            }
            
            return true;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | 
                SecurityException | InvocationTargetException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            mensajeError = e.getMessage();
        }
        
        return false;
    }

    /**
     * @author Richard Mejia
     * @date 03/10/2018
     * Método encargado de mostrar los detalles de una configuración de liquidación de rdw.
     * @param request
     * @return
     */
    public String verConfiguracionLiquidacion(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            ConfiguracionLiquidacion entidadConfigurable = ConfiguracionLiquidacionBD.selectByOne(Long.parseLong(id));
            
            if (entidadConfigurable != null && entidadConfigurable.getId() > 0) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute(CONFIGURACION_LIQUIDACION, entidadConfigurable);
                request.getSession().setAttribute(SELECT, select);
                url = VER_CONFIGURACION_LIQUIDACION_JSP;
            } else {
                url = LISTADO_CONFIGURACIONES_LIQUIDACION_JSP;
            }
        } catch (NumberFormatException e) {
            request.setAttribute(ID_INFO, 2);
            request.setAttribute(MSG, MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return url;
    }
    
    /**
     * @author Richard Mejia
     * @date 03/10/2018
     * Método encargado de editar los valores de una configuración de liquidación de rdw.
     * @param request
     * @return
     */
    public String editarConfiguracionLiquidacion(HttpServletRequest request) {
        try {
            ConfiguracionLiquidacion entidadConfigurable = asignarParametrosRequest(request, true);

            if (validarConfiguracionLiquidacion(entidadConfigurable)) {
                boolean resultado = ConfiguracionLiquidacionBD.update(entidadConfigurable);

                if (resultado) {
                    request.setAttribute(ID_INFO, 1);
                    request.setAttribute(MSG, MENSAJE_EXITO);
                    request.setAttribute(CONFIGURACION_LIQUIDACION, entidadConfigurable);
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute(SELECT, select);
                } else {
                    request.setAttribute(ID_INFO, 2);
                    request.setAttribute(MSG, MENSAJE_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute(ID_INFO, 2);
            request.setAttribute(MSG, MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException e) {
            request.setAttribute(ID_INFO, 2);
            request.setAttribute(MSG, MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return VER_CONFIGURACION_LIQUIDACION_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 03/10/2018
     * Método encargado de cambiar el estado a una configuración de liquidación de rdw.
     * @param request
     * @return
     */
    public String cambiarEstadoConfiguracionLiquidacion(HttpServletRequest request) {
        try {
            long id = Long.parseLong(request.getParameter(ID));
            int estado = Integer.parseInt(request.getParameter(ESTADO));
            EntidadConfigurable entidadConfigurable = new EntidadConfigurable();
            entidadConfigurable.setId(id);
            entidadConfigurable.setEstado(estado);
            boolean resultado = ConfiguracionLiquidacionBD.updateEstado(entidadConfigurable);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute(SELECT, select);
                request.setAttribute(ID_INFO, 1);
                request.setAttribute(MSG, MENSAJE_EXITO);
            } else {
                request.setAttribute(ID_INFO, 2);
                request.setAttribute(MSG, MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute(ID_INFO, 2);
            request.setAttribute(MSG, MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return LISTADO_CONFIGURACIONES_LIQUIDACION_JSP;
    }

    /**
     * @author Richard Mejia
     * @date 03/10/2018
     * Metodo encargado de consultar los parametros de una
     * peticion y asignarlos al objeto correspondiente para generar una
     * configuración de liquidación de RDW.
     * @param request Peticion realizada desde el navegador del cliente.
     * @param asignarId Indica si se debe asignar el id al objeto creado.
     * @return Retorna un objeto de tipo ConfiguracionLiquidacion con los valores de los parametros
     * enviados en la peticion.
     */
    private ConfiguracionLiquidacion asignarParametrosRequest(HttpServletRequest request, boolean asignarId) throws 
            NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ConfiguracionLiquidacion entidadConfigurable = new ConfiguracionLiquidacion();
        entidadConfigurable.setFkPerfil(Integer.parseInt(request.getParameter(PERFIL)));
        entidadConfigurable.setNombreConfiguracionLiquidacion(request.getParameter(
                NOMBRE_CONFIGURACION_LIQUIDACION).replace("\"", ""));
        entidadConfigurable.setEstado(Integer.parseInt(request.getParameter(ESTADO)));
        entidadConfigurable.setLiquidacionNormal(false);
        entidadConfigurable.setLiquidacionPorRuta(false);
        entidadConfigurable.setLiquidacionPorTiempo(false);
        entidadConfigurable.setLiquidacionPorTramo(false);
        entidadConfigurable.setLiquidacionSoloPasajeros(false);
        
        Method method;

        for (int i = 1; i <= 5; i++) {
            method = ConfiguracionLiquidacion.class.getDeclaredMethod(SET_ETQ_PASAJEROS + i, String.class);
            method.invoke(entidadConfigurable, request.getParameter(ETQ_PASAJEROS + i).replaceAll("\"", ""));
        }

        for (int i = 1; i <= 8; i++) {
            method = ConfiguracionLiquidacion.class.getDeclaredMethod(SET_ETQ_TOTAL + i, String.class);
            method.invoke(entidadConfigurable, request.getParameter(ETQ_TOTAL + i).replaceAll("\"", ""));
        }

        for (int i = 1; i <= 5; i++) {
            method = ConfiguracionLiquidacion.class.getDeclaredMethod(SET_ETQ_REP_PASAJEROS + i, String.class);
            method.invoke(entidadConfigurable, request.getParameter(ETQ_REP_PASAJEROS + i).replaceAll("\"", ""));
        }

        for (int i = 1; i <= 8; i++) {
            method = ConfiguracionLiquidacion.class.getDeclaredMethod(SET_ETQ_REP_TOTAL + i, String.class);
            method.invoke(entidadConfigurable, request.getParameter(ETQ_REP_TOTAL + i).replaceAll("\"", ""));
        }
           
        int modoLiquidacion = Integer.parseInt(request.getParameter(MODO_LIQUIDACION));
        
        switch (modoLiquidacion) {
            case CONS_LIQUIDACION_NORMAL:
                entidadConfigurable.setLiquidacionNormal(true);
                break;
            case CONS_LIQUIDACION_POR_RUTA:
                entidadConfigurable.setLiquidacionPorRuta(true);
                break;
            case CONS_LIQUIDACION_POR_TIEMPO:
                entidadConfigurable.setLiquidacionPorTiempo(true);
                break;
            case CONS_LIQUIDACION_POR_TRAMO:
                entidadConfigurable.setLiquidacionPorTramo(true);
                break;
            case CONS_LIQUIDACION_SOLO_PASAJEROS:
                entidadConfigurable.setLiquidacionSoloPasajeros(true);
                break; 
        }
        
        if (asignarId) {
            entidadConfigurable.setId(Long.parseLong(request.getParameter(ID_EDIT)));
        }

        return entidadConfigurable;
    } 

}
