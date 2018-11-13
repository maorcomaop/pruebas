/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.ConfiguracionLiquidacionBD;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.Usuario;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Richard Mejia
 * @date 01/10/2018
 * Esta clase permite almacenar todas las constantes de la aplicación.
 */
public final class ConstantesSesion {
    
    private ConstantesSesion() {}
    
    public static final String MENSAJE_EXITO = "Registro guardado correctamente";
    public static final String MENSAJE_ERROR = "No fue posible guardar el registro";
    public static final String ID_INFO = "idInfo";
    public static final String MSG = "msg";
    public static final String SELECT = "select";
    public static final String ESTADO = "estado";
    public static final String ID_EDIT = "id_edit";
    public static final String ID = "id";
    
    public static final int VALOR_POSITIVO = 1;
    public static final int VALOR_CERO = 0;
    
    public static final String CONFIGURACION_LIQUIDACION = "configuracionLiquidacion";
    public static final String CONFIGURACIONES_LIQUIDACION = "configuracionesLiquidacion";
    public static final int CONS_LIQUIDACION_NORMAL = 1;
    public static final int CONS_LIQUIDACION_POR_RUTA = 2;
    public static final int CONS_LIQUIDACION_POR_TRAMO = 3;
    public static final int CONS_LIQUIDACION_POR_TIEMPO = 4;
    public static final int CONS_LIQUIDACION_SOLO_PASAJEROS = 5;
    
    public static final String CONFIGURACION_ETIQUETAS_SESION = "configuracion_etiquetas_sesion";
    
    
    
    public static ConfiguracionLiquidacion obtenerEtiquetasLiquidacionPerfil(HttpServletRequest request) {
        Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("login");
        ConfiguracionLiquidacion configuracionLiquidacion = new ConfiguracionLiquidacion();
        
        if (usuarioSesion != null && usuarioSesion.getIdperfil() > 0) {
            configuracionLiquidacion
                    = ConfiguracionLiquidacionBD.obtenerConfiguracionActiva(usuarioSesion.getIdperfil());

            if (configuracionLiquidacion == null || configuracionLiquidacion.getId() == 0) {
                configuracionLiquidacion = new ConfiguracionLiquidacion();
                configuracionLiquidacion.setNombreConfiguracionLiquidacion(
                        "no se encontró una configuración de etiquetas para este perfil, se utiliza una configuración "
                                + "de emergencia. Si desea cambiar los valores de las etiquetas, comuníquese con un "
                                + "administrador");
                configuracionLiquidacion.setEtqPasajeros1("No Aplica");
                configuracionLiquidacion.setEtqPasajeros2("Pasajeros Descontados");
                configuracionLiquidacion.setEtqPasajeros3("No Aplica");
                configuracionLiquidacion.setEtqPasajeros4("No Aplica");
                configuracionLiquidacion.setEtqPasajeros5("No Aplica");
                configuracionLiquidacion.setEtqTotal1("Subtotal");
                configuracionLiquidacion.setEtqTotal2("Total");
                configuracionLiquidacion.setEtqTotal3("Descuentos Operativos");
                configuracionLiquidacion.setEtqTotal4("No Aplica");
                configuracionLiquidacion.setEtqTotal5("Recibe del Condutor");
                configuracionLiquidacion.setEtqTotal6("Descuentos Administrativos");
                configuracionLiquidacion.setEtqTotal7("Total Liquidación");
                configuracionLiquidacion.setEtqTotal8("Subtotal");
                configuracionLiquidacion.setEtqRepPasajeros1("Pasajeros Registrados");
                configuracionLiquidacion.setEtqRepPasajeros2("Pasajeros Descontados");
                configuracionLiquidacion.setEtqRepPasajeros3("Total Pasajerso");
                configuracionLiquidacion.setEtqRepPasajeros4("No Aplica");
                configuracionLiquidacion.setEtqRepPasajeros5("No Aplica");
                configuracionLiquidacion.setEtqRepTotal1("Total");
                configuracionLiquidacion.setEtqRepTotal2("Descuento Neto");
                configuracionLiquidacion.setEtqRepTotal3("Total Neto Pasajeros");
                configuracionLiquidacion.setEtqRepTotal4("Descuentos Operativos");
                configuracionLiquidacion.setEtqRepTotal5("Recibe del Conductor");
                configuracionLiquidacion.setEtqRepTotal6("Descuentos Administrativos");
                configuracionLiquidacion.setEtqRepTotal7("Total Liquidación");
                configuracionLiquidacion.setEtqRepTotal8("No Aplica");
            }
            
            request.getSession().setAttribute(
                    ConstantesSesion.CONFIGURACION_ETIQUETAS_SESION, configuracionLiquidacion);
        }
        
        return configuracionLiquidacion;
    }
    
}
