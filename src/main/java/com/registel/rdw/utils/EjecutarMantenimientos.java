/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.controladores.ControladorCategoriaLicencia;
import com.registel.rdw.controladores.ControladorMantenimiento;
import com.registel.rdw.datos.ForwardingBD;
import com.registel.rdw.datos.LicenciaTransitoBD;
import com.registel.rdw.datos.MantenimientoBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.NotificacionesBD;
import com.registel.rdw.datos.RevisionTMecanicaBD;
import com.registel.rdw.datos.TarjetaOperacionBD;
import com.registel.rdw.datos.VehiculoAseguradoBD;
import com.registel.rdw.datos.VehiculoMantenimientoBD;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.Forwarding;
import com.registel.rdw.logica.LicenciaTransito;
import com.registel.rdw.logica.Mantenimiento;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Notificacion;
import com.registel.rdw.logica.RevisionTMecanica;
import com.registel.rdw.logica.TarjetaOperacion;
import com.registel.rdw.logica.VehiculoAsegurado;
import com.registel.rdw.logica.VehiculoMantenimiento;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Richard Mejia
 */
public class EjecutarMantenimientos implements Runnable {

    private static ServletContext context;
    private static Entorno entorno;
    
    private static final String MSM_VENCIMIENTO_POLIZA = 
            "La póliza #%1$s del vehículo %2$s vence el %3$s. Nombre Póliza: %4$s";
    private static final String MSM_VENCIMIENTO_LICENCIA = 
            "La licencia de tránsito del conductor %1$s asociado al vehículo %2$s vence el %3$s.";
    private static final String MSM_VENCIMIENTO_TECNOMECANICA = 
            "La revisión técnico-mecánica #%1$s del vehículo %2$s vence el %3$s.";
    private static final String MSM_VENCIMIENTO_TARJETA_OPERACION = 
            "La tarjeta de operación #%1$s asociada al vehículo %2$s vence el %3$s.";
    private static final String MSM_HORAS_TRABAJADAS = "El vehículo %1$s ha alcanzado %2$.1f horas trabajadas.";
    private static final String MSM_KM_RECORRIDOS = "El vehículo %1$s ha alcanzado %2$.1f km recorridos.";
    
    private static final String CADENA_SPLIT = "<>";
    
    private static final int VALOR_POSITIVO = 1;
    private static final int VALOR_CERO = 0;
    
    public EjecutarMantenimientos(ServletContext context) {
        EjecutarMantenimientos.context = context;
        entorno = new Entorno(context.getRealPath(""));
    }
    
    @Override
    public void run() {
        try {
            ejecutarMantenimientos();
        } catch (Exception e) {
            Logger.getLogger(ControladorMantenimiento.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Método que se encarga de ejecutar todos los
     * mantenimientos que están asociados al usuario que ha iniciado sesión y
     * enviar la respuesta a la pantalla del navegador.
     * @param idUsuario
     * @return 
     */
    public static List<String> ejecutarMantenimientos() {
        List<String> mensajes = new ArrayList<>();
        try {
            List<Mantenimiento> mantenimientos = MantenimientoBD.selectTodosLosMantenimientos();
            
            Mantenimiento mantenimientoTmp;
            int mantenimientosSize = mantenimientos.size();

            for (int i = 0; i < mantenimientosSize; i++) {
                mantenimientoTmp = mantenimientos.get(i);

                //Si el mantenimiento no tiene activas las notificaciones, se hace nada
                if (mantenimientoTmp.getNotificacionActiva() == VALOR_CERO) {
                    continue;
                }

                if (mantenimientoTmp.getEnProcesamiento() == VALOR_CERO) {
                    mantenimientoTmp.setEnProcesamiento(VALOR_POSITIVO);

                    if (!MantenimientoBD.updateCampoEnProcesamiento(mantenimientoTmp)) {
                        continue;
                    }
                } else {
                    continue;
                }

                if (mantenimientoTmp.getFkTipoEventoMantenimiento() == MantenimientoUtils.VENCIMIENTO_DOCUMENTACION) {
                    mensajes.addAll(consultarDocumentacionPorVencer(mantenimientoTmp));
                } else if (mantenimientoTmp.getFkTipoEventoMantenimiento() == MantenimientoUtils.HORAS_TRABAJADAS) {
                    mensajes.addAll(consultarHorasTrabajadas(mantenimientoTmp));
                } else if (mantenimientoTmp.getFkTipoEventoMantenimiento() == MantenimientoUtils.KILOMETROS_RECORRIDOS) {
                    mensajes.addAll(consultarKmRecorridos(mantenimientoTmp));
                }
            }
            
            Notificacion notificacionTmp;
            String[] mensajeNombreMtoIdMto;
            int mensajesSize = mensajes.size();
            
            for (int i = 0; i < mensajesSize; i++) {
                mensajeNombreMtoIdMto = mensajes.get(i).split(CADENA_SPLIT);
                notificacionTmp = new Notificacion();
                notificacionTmp.setMensaje(mensajeNombreMtoIdMto[0]);
                notificacionTmp.setFecha(new Date());
                notificacionTmp.setFkMantenimiento(Long.parseLong(mensajeNombreMtoIdMto[2]));
                notificacionTmp.setEstado(VALOR_POSITIVO);
                notificacionTmp.setLeida(VALOR_CERO);
                NotificacionesBD.insert(notificacionTmp);
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return mensajes;
    }

    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Método que se encarga de realizar el mantenimiento de
     * vencimiento de documentación. El método consulta las polizas asociadas a
     * los vehiculos (SOAT incluido), las licencias de conduccion, las tarjetas
     * de operacion y las revisiones tecnico-mecanicas. En caso de que haya
     * documentación a punto de vencer envía las notificaciones por correo
     * electronico.
     * @param mantenimiento
     * @return
     */
    private static List<String> consultarDocumentacionPorVencer(Mantenimiento mantenimiento) {
        List<String> mensajes = new ArrayList<>();

        try {
            List<VehiculoMantenimiento> vehiculosMantenimiento = VehiculoMantenimientoBD.select(mantenimiento.getId());
            VehiculoMantenimiento vehiculoMantenimientoTmp;
            int vehiculosMantenimientoSize = vehiculosMantenimiento.size();
            Movil movilTmp;
            String placaTmp;
            String[] correos = null;

            if (mantenimiento.getCorreosNotificacion() != null) {
                correos = mantenimiento.getCorreosNotificacion().split(",");
            }

            for (int i = 0; i < vehiculosMantenimientoSize; i++) {
                vehiculoMantenimientoTmp = vehiculosMantenimiento.get(i);
                
                if (vehiculoMantenimientoTmp.getYaNotifico() != VALOR_CERO
                        && new Date().before(vehiculoMantenimientoTmp.getFechaProximaNotificacion())) {
                    continue;
                }
                
                if (new Date().before(vehiculoMantenimientoTmp.getFechaProximaNotificacion())) {
                    continue;
                }
                
                movilTmp = MovilBD.selectOne(vehiculoMantenimientoTmp.getFkVehiculo());
                
                if (movilTmp == null) {
                    continue;
                }

                placaTmp = movilTmp.getPlaca();

                //Se consultan la polizas proximas a vencer
                mtoPolizasPorVencer(mantenimiento, placaTmp, mensajes, correos);

                //Se consultan las licencias proximas a vencer
                mtoLicenciasPorVencer(mantenimiento, placaTmp, mensajes, correos);

                //Se consultan las revisiones tecnico-mecanicas proximas a vencer
                mtoTecnicoMecanicaPorVencer(mantenimiento, placaTmp, mensajes, correos);

                //Se consultan las tarjetas de operacion proximas a vencer
                mtoTarjetasOperacionPorVencer(mantenimiento, placaTmp, mensajes, correos);
                
                actualizarVehiculoMantenimiento(mantenimiento, vehiculoMantenimientoTmp, VALOR_CERO, true);
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            actualizarEstadoMantenimiento(mantenimiento);
        }

        return mensajes;
    }
    
    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método que consulta las pólizas a punto de vencer asociadas a los vehículos que están
     * asociados a un mantenimiento de vencimiento de documentación.
     * @param mantenimiento
     * @param placa
     * @param mensajes
     * @param correos 
     */
    private static void mtoPolizasPorVencer(Mantenimiento mantenimiento, String placa, List<String> mensajes, 
            String[] correos) {
        try {
            List<VehiculoAsegurado> vehiculosPolizasPorVencer = VehiculoAseguradoBD.
                    obtenerPolizasPorVencerVehiculo(placa, mantenimiento.getValorPrimeraNotificacion());
            int vehiculosPolizasPorVencerSize = vehiculosPolizasPorVencer.size();
            VehiculoAsegurado vehiculoAseguradoTmp;
            StringBuilder mensajeCorreo;

            for (int j = 0; j < vehiculosPolizasPorVencerSize; j++) {
                vehiculoAseguradoTmp = vehiculosPolizasPorVencer.get(j);
                agregarMensajeLista(String.format(MSM_VENCIMIENTO_POLIZA, vehiculoAseguradoTmp.getNumeroPoliza(),
                        vehiculoAseguradoTmp.getPlacaVehiculo(), vehiculoAseguradoTmp.getFinVigencia(),
                        vehiculoAseguradoTmp.getNombrePoliza()), mantenimiento, mensajes);
                mensajeCorreo = new StringBuilder();
                mensajeCorreo.append(String.format(MSM_VENCIMIENTO_POLIZA, vehiculoAseguradoTmp.getNumeroPoliza(),
                        vehiculoAseguradoTmp.getPlacaVehiculo(), vehiculoAseguradoTmp.getFinVigencia(),
                        vehiculoAseguradoTmp.getNombrePoliza()));
                mensajeCorreo.append(String.format("<br><br>Aseguradora: %s", 
                        vehiculoAseguradoTmp.getNombreAseguradora()));

                if (vehiculoAseguradoTmp.getNombreEmpresaVehiculo() != null) {
                    mensajeCorreo.append(String.format("<br><br>Empresa vehículo: %s", vehiculoAseguradoTmp.
                            getNombreEmpresaVehiculo()));
                }

                enviarCorreosNotificacion(correos, mantenimiento, mensajeCorreo.toString(), placa, VALOR_CERO);
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de consultar las licencias de conducir a punto de vencer de los conductores que 
     * están asociados a los vehículos asociados a un mantenimiento de vencimiento de documentación.
     * @param mantenimiento
     * @param placa
     * @param mensajes
     * @param correos 
     */
    private static void mtoLicenciasPorVencer(Mantenimiento mantenimiento, String placa, List<String> mensajes, 
            String[] correos) {
        try {
            List<LicenciaTransito> licenciasPorVencer = LicenciaTransitoBD.obtenerLicenciasTransitoPorVencerPorPlaca(
                    placa, mantenimiento.getValorPrimeraNotificacion());
            int licenciasPorVencerSize = licenciasPorVencer.size();
            LicenciaTransito licenciaTransitoTmp;
            StringBuilder mensajeCorreo;

            for (int j = 0; j < licenciasPorVencerSize; j++) {
                licenciaTransitoTmp = licenciasPorVencer.get(j);
                agregarMensajeLista(String.format(MSM_VENCIMIENTO_LICENCIA, licenciaTransitoTmp.getNombreConductor(),
                        placa, licenciaTransitoTmp.getVigencia()), mantenimiento, mensajes);
                mensajeCorreo = new StringBuilder();
                mensajeCorreo.append(String.format(MSM_VENCIMIENTO_LICENCIA, licenciaTransitoTmp.getNombreConductor(),
                        placa, licenciaTransitoTmp.getVigencia()));
                mensajeCorreo.append(String.format("<br><br>Categoría licencia: %s",
                        licenciaTransitoTmp.getNombreCategoria()));
                mensajeCorreo.append(String.format("<br><br>Oficina expedicción: %s",
                        licenciaTransitoTmp.getNombreOficina()));
                
                enviarCorreosNotificacion(correos, mantenimiento, mensajeCorreo.toString(), placa, VALOR_CERO);
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de consultar las revisiones tecnico-mecanicas de los vehiculos asociados a un
     * mantenimiento de vencimiento de documentación.
     * @param mantenimiento
     * @param placa
     * @param mensajes
     * @param correos 
     */
    private static void mtoTecnicoMecanicaPorVencer(Mantenimiento mantenimiento, String placa, List<String> mensajes, 
            String[] correos) {
        try {
            List<RevisionTMecanica> revisionesTMPorVencer = RevisionTMecanicaBD.obtenerRevisionesTMPorVencer(placa,
                    mantenimiento.getValorPrimeraNotificacion());
            int revisionesTMPorVencerSize = revisionesTMPorVencer.size();
            RevisionTMecanica revisionTMTmp;
            StringBuilder mensajeCorreo;

            for (int j = 0; j < revisionesTMPorVencerSize; j++) {
                revisionTMTmp = revisionesTMPorVencer.get(j);
                String fecha = "";

                if (revisionTMTmp.getFecha_vencimiento().length() > 10) {
                    fecha = revisionTMTmp.getFecha_vencimiento().substring(0, 10);
                }

                agregarMensajeLista(String.format(MSM_VENCIMIENTO_TECNOMECANICA, revisionTMTmp.getCodigo(),
                        placa, fecha), mantenimiento, mensajes);
                mensajeCorreo = new StringBuilder();
                mensajeCorreo.append(String.format(MSM_VENCIMIENTO_TECNOMECANICA, revisionTMTmp.getCodigo(),
                        placa, fecha));
                
                if (revisionTMTmp.getNombreCda() != null && !revisionTMTmp.getNombreCda().isEmpty()) {
                    mensajeCorreo.append(String.format("<br><br>Centro de diagnóstico automotor de expedición: %s",
                        revisionTMTmp.getNombreCda()));
                }
                
                enviarCorreosNotificacion(correos, mantenimiento, mensajeCorreo.toString(), placa, VALOR_CERO);
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de consultar las tarjetas de operación a punto de vencer de los vehículos asociados
     * a un mantenimiento de vencimiento de documentación.
     * @param mantenimiento
     * @param placa
     * @param mensajes
     * @param correos 
     */
    private static void mtoTarjetasOperacionPorVencer(Mantenimiento mantenimiento, String placa, List<String> mensajes, 
            String[] correos) {
        try {
            List<TarjetaOperacion> tarjetasOperacionPorVencer = TarjetaOperacionBD.obtenerTarjetasOperacionPorVencer(
                    placa, mantenimiento.getValorPrimeraNotificacion());
            int tarjetasOperacionPorVencerSize = tarjetasOperacionPorVencer.size();
            TarjetaOperacion tarjetaOperacionTmp;
            StringBuilder mensajeCorreo;

            for (int j = 0; j < tarjetasOperacionPorVencerSize; j++) {
                tarjetaOperacionTmp = tarjetasOperacionPorVencer.get(j);
                String fecha = "";

                if (tarjetaOperacionTmp.getFecha_vencimiento().length() > 10) {
                    fecha = tarjetaOperacionTmp.getFecha_vencimiento().substring(0, 10);
                }

                agregarMensajeLista(String.format(MSM_VENCIMIENTO_TARJETA_OPERACION, tarjetaOperacionTmp.getCodigo(),
                        placa, fecha), mantenimiento, mensajes);
                mensajeCorreo = new StringBuilder();
                mensajeCorreo.append(String.format(MSM_VENCIMIENTO_TARJETA_OPERACION, tarjetaOperacionTmp.getCodigo(),
                        placa, fecha));
                
                if (tarjetaOperacionTmp.getNombre_centro_exp() != null && 
                        !tarjetaOperacionTmp.getNombre_centro_exp().isEmpty()) {
                    mensajeCorreo.append(String.format("<br><br>Centro de expedicón: %s",
                        tarjetaOperacionTmp.getNombre_centro_exp()));
                }
                
                if (tarjetaOperacionTmp.getModelo() != null && !tarjetaOperacionTmp.getModelo().isEmpty()) {
                    mensajeCorreo.append(String.format("<br><br>Modelo: %s", tarjetaOperacionTmp.getModelo()));
                }
                
                enviarCorreosNotificacion(correos, mantenimiento, mensajeCorreo.toString(), placa, VALOR_CERO);
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Metodo encargado de realizar el mantenimiento de horas
     * trabajadas. Consulta las horas que ha estado encendido un vehiculo y las
     * va acumulando. En caso de que haya vehiculos que alcancen el umbral de
     * horas trabajada, se envía la notificacion por correo electronico.
     * @param mantenimiento
     * @return
     */
    private static List<String> consultarHorasTrabajadas(Mantenimiento mantenimiento) {
        List<String> mensajes = new ArrayList<>();

        try {
            List<VehiculoMantenimiento> vehiculosMantenimiento = VehiculoMantenimientoBD.select(mantenimiento.getId());
            VehiculoMantenimiento vehiculoMantenimientoTmp;
            int vehiculosMantenimientoSize = vehiculosMantenimiento.size();
            Movil movilTmp;
            String placaTmp;
            double horasTrabajadas;
            double diferenciaHorasTrabajadas;
            double porcentajeHorasTrabajadas;

            String[] correos = null;

            if (mantenimiento.getCorreosNotificacion() != null) {
                correos = mantenimiento.getCorreosNotificacion().split(",");
            }
            
            //El mantenimiento es estatico
            if (mantenimiento.getRepetirNotificacion() == VALOR_CERO) {

                for (int i = 0; i < vehiculosMantenimientoSize; i++) {
                    vehiculoMantenimientoTmp = vehiculosMantenimiento.get(i);
                    movilTmp = MovilBD.selectOne(vehiculoMantenimientoTmp.getFkVehiculo());

                    if (movilTmp == null) {
                        continue;
                    }

                    placaTmp = movilTmp.getPlaca();
                    horasTrabajadas = calcularHorasRecorridas(ForwardingBD.obtenerHorasTrabajadasVehiculo(placaTmp,
                        mantenimiento.getFechaActivacionNotificacion(), entorno)) + 
                            vehiculoMantenimientoTmp.getValorInicialMantenimiento();

                    if (vehiculoMantenimientoTmp.getYaNotifico() == VALOR_CERO) {

                        if (horasTrabajadas >= mantenimiento.getValorPrimeraNotificacion()) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp, 
                                    placaTmp, horasTrabajadas, MSM_HORAS_TRABAJADAS);
                        }
                    } else {
                        diferenciaHorasTrabajadas = horasTrabajadas - vehiculoMantenimientoTmp.getValorAcumulado();
                        porcentajeHorasTrabajadas = diferenciaHorasTrabajadas / 
                                mantenimiento.getValorPrimeraNotificacion();

                        if (porcentajeHorasTrabajadas >= VALOR_POSITIVO) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp, 
                                    placaTmp, horasTrabajadas, MSM_HORAS_TRABAJADAS);
                        }
                    }
                }
            } else { //El mantenimiento es ciclico
                
                for (int i = 0; i < vehiculosMantenimientoSize; i++) {
                    vehiculoMantenimientoTmp = vehiculosMantenimiento.get(i);
                    movilTmp = MovilBD.selectOne(vehiculoMantenimientoTmp.getFkVehiculo());

                    if (movilTmp == null) {
                        continue;
                    }

                    placaTmp = movilTmp.getPlaca();
                    horasTrabajadas = calcularHorasRecorridas(ForwardingBD.
                            obtenerHorasTrabajadasVehiculo(placaTmp, mantenimiento.getFechaActivacionNotificacion(), 
                                    entorno)) + vehiculoMantenimientoTmp.getValorInicialMantenimiento();
                    diferenciaHorasTrabajadas = horasTrabajadas - vehiculoMantenimientoTmp.getValorAcumulado();
                    porcentajeHorasTrabajadas = diferenciaHorasTrabajadas / mantenimiento.getValorPrimeraNotificacion();

                    if (vehiculoMantenimientoTmp.getYaNotifico() == VALOR_CERO) {

                        if (porcentajeHorasTrabajadas >= VALOR_POSITIVO) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp,
                                    placaTmp, horasTrabajadas, MSM_HORAS_TRABAJADAS);
                        }
                    } else {
                        
                        if (new Date().after(vehiculoMantenimientoTmp.getFechaProximaNotificacion()) && 
                                vehiculoMantenimientoTmp.getCantidadNotificacionesPendientes() < 
                                mantenimiento.getCantidadNotificaciones()) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp,
                                    placaTmp, horasTrabajadas, MSM_HORAS_TRABAJADAS);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            actualizarEstadoMantenimiento(mantenimiento);
        }

        return mensajes;
    }
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Metodo que calcula las horas que ha estado un vehiculo
     * encendido. Recibe una lista de datos con mensajes de VEHICULO_ENCENDIDO y
     * VEHICULO_APAGADO. Realiza el calculo y acumula el resultado.
     * @param datos
     * @return
     */
    private static double calcularHorasRecorridas(List<Forwarding> datos) {
        double resultado = 0;

        try {
            Forwarding forwardingTmpEncendido;
            Forwarding forwardingTmpApagado;
            int datosSize = datos.size();
            boolean usarForwardingEncendido;
            boolean usarForwardingApagado;

            for (int i = 0; i < datosSize; i++) {

                if (i == datos.size() - 1) {
                    forwardingTmpEncendido = datos.get(i);

                    if (!forwardingTmpEncendido.getMsg().equals(ForwardingBD.MOVIL_ENCENDIDO)) {
                        break;
                    }

                    resultado += calcularMinutos(forwardingTmpEncendido.getFechaGps(), new Date()) / 60d;
                } else {
                    forwardingTmpEncendido = datos.get(i);
                    usarForwardingEncendido = true;

                    while (!forwardingTmpEncendido.getMsg().equals(ForwardingBD.MOVIL_ENCENDIDO)) {
                        i++;

                        if (i < datosSize) {
                            forwardingTmpEncendido = datos.get(i);
                            usarForwardingEncendido = true;
                        } else {
                            usarForwardingEncendido = false;
                        }
                    }

                    i++;

                    if (i < datosSize) {
                        forwardingTmpApagado = datos.get(i);
                        usarForwardingApagado = true;

                        while (!forwardingTmpApagado.getMsg().equals(ForwardingBD.MOVIL_APAGADO)) {
                            i++;

                            if (i < datosSize) {
                                forwardingTmpApagado = datos.get(i);
                                usarForwardingApagado = true;
                            } else {
                                usarForwardingApagado = false;
                            }
                        }

                        if (usarForwardingEncendido && usarForwardingApagado) {
                            resultado += calcularMinutos(forwardingTmpEncendido.getFechaGps(),
                                    forwardingTmpApagado.getFechaGps()) / 60d;
                        } else if (usarForwardingEncendido) {
                            resultado += calcularMinutos(forwardingTmpEncendido.getFechaGps(), new Date()) / 60d;
                        }
                    } else if (usarForwardingEncendido) {
                        resultado += calcularMinutos(forwardingTmpEncendido.getFechaGps(), new Date()) / 60d;
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return resultado;
    }

    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Método que calcula los minutos que hay entre dos fechas
     * dadas.
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    private static double calcularMinutos(Date fechaInicial, Date fechaFinal) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fechaInicial);
        cal2.setTime(fechaFinal);
        long tiempoMilisegundos = cal2.getTimeInMillis() - cal1.getTimeInMillis();

        return (double) TimeUnit.MILLISECONDS.toMinutes(tiempoMilisegundos);
    }
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Metodo encargado de realizar el mantenimiento de
     * kilometros recorridos. Consulta los kilometros que ha recorrido un
     * vehiculo en un lapso determinado. En caso de que haya vehiculos que
     * alcancen el umbral de kilometros recorridos, se envía la notificacion por
     * correo electronico.
     * @param mantenimiento
     * @return
     */
    private static List<String> consultarKmRecorridos(Mantenimiento mantenimiento) {
        List<String> mensajes = new ArrayList<>();

        try {
            List<VehiculoMantenimiento> vehiculosMantenimiento = VehiculoMantenimientoBD.select(mantenimiento.getId());
            VehiculoMantenimiento vehiculoMantenimientoTmp;
            int vehiculosMantenimientoSize = vehiculosMantenimiento.size();
            double distanciaKm;
            double diferenciaDistanciaAcumulada;
            double porcentajeDistanciaKm;

            Movil movilTmp;
            String placaTmp;

            String[] correos = null;

            if (mantenimiento.getCorreosNotificacion() != null) {
                correos = mantenimiento.getCorreosNotificacion().split(",");
            }

            //El mantenimiento es estatico
            if (mantenimiento.getRepetirNotificacion() == VALOR_CERO) {

                for (int i = 0; i < vehiculosMantenimientoSize; i++) {
                    vehiculoMantenimientoTmp = vehiculosMantenimiento.get(i);
                    movilTmp = MovilBD.selectOne(vehiculoMantenimientoTmp.getFkVehiculo());

                    if (movilTmp == null) {
                        continue;
                    }

                    placaTmp = movilTmp.getPlaca();
                    distanciaKm = (ForwardingBD.obtenerKmRecorridosVehiculo(placaTmp,
                            mantenimiento.getFechaActivacionNotificacion(), entorno) / 1000d)
                            + vehiculoMantenimientoTmp.getValorInicialMantenimiento();

                    if (vehiculoMantenimientoTmp.getYaNotifico() == VALOR_CERO) {

                        if (distanciaKm >= mantenimiento.getValorPrimeraNotificacion()) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp,
                                    placaTmp, distanciaKm, MSM_KM_RECORRIDOS);
                        }
                    } else {
                        diferenciaDistanciaAcumulada = distanciaKm - vehiculoMantenimientoTmp.getValorAcumulado();
                        porcentajeDistanciaKm = diferenciaDistanciaAcumulada
                                / mantenimiento.getValorPrimeraNotificacion();

                        if (porcentajeDistanciaKm >= VALOR_POSITIVO) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp, 
                                    placaTmp, distanciaKm, MSM_KM_RECORRIDOS);
                        }
                    }
                }
            } else { //El mantenimiento es ciclico
                
                for (int i = 0; i < vehiculosMantenimientoSize; i++) {
                    vehiculoMantenimientoTmp = vehiculosMantenimiento.get(i);
                    movilTmp = MovilBD.selectOne(vehiculoMantenimientoTmp.getFkVehiculo());

                    if (movilTmp == null) {
                        continue;
                    }

                    placaTmp = movilTmp.getPlaca();
                    distanciaKm = (ForwardingBD.obtenerKmRecorridosVehiculo(placaTmp,
                            mantenimiento.getFechaActivacionNotificacion(), entorno) / 1000d)
                            + vehiculoMantenimientoTmp.getValorInicialMantenimiento();
                    diferenciaDistanciaAcumulada = distanciaKm - vehiculoMantenimientoTmp.getValorAcumulado();
                    porcentajeDistanciaKm = diferenciaDistanciaAcumulada
                            / mantenimiento.getValorPrimeraNotificacion();

                    if (vehiculoMantenimientoTmp.getYaNotifico() == VALOR_CERO) {

                        if (porcentajeDistanciaKm >= VALOR_POSITIVO) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp,
                                    placaTmp, distanciaKm, MSM_KM_RECORRIDOS);
                        }
                    } else {

                        if (new Date().after(vehiculoMantenimientoTmp.getFechaProximaNotificacion()) && 
                                vehiculoMantenimientoTmp.getCantidadNotificacionesPendientes() < 
                                mantenimiento.getCantidadNotificaciones()) {
                            mostrarNotificacionActualizar(mensajes, correos, mantenimiento, vehiculoMantenimientoTmp,
                                    placaTmp, distanciaKm, MSM_KM_RECORRIDOS);
                        }
                    } 
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            actualizarEstadoMantenimiento(mantenimiento);
        }

        return mensajes;
    }

    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de enviar los correos de notificacion de los mantenimientos ejecutados.
     * @param correos
     * @param mantenimiento
     * @param mensajeCorreo
     * @param placa
     * @param valor 
     */
    private static void enviarCorreosNotificacion(String[] correos, Mantenimiento mantenimiento, String mensajeCorreo,
            String placa, double valor) {
        
        if (correos != null) {
            int correosLength = correos.length;
            StringBuilder mensaje = new StringBuilder();
            mensaje.append(String.format(mensajeCorreo, placa, valor));
            mensaje.append(String.format("<br><br>Tipo mantenimiento: %1$s", mantenimiento.getNombreMantenimiento()));
            
            if (mantenimiento.getObservacionesNotificacion() != null) {
                mensaje.append(String.format("<br><br>Observaciones mantenimiento: %s",
                        mantenimiento.getObservacionesNotificacion()));
            }

            for (int j = 0; j < correosLength; j++) {
                enviarCorreoNotificacion(mensaje.toString(), correos[j], mantenimiento.getNombre());
            }
        }
    }
    
    /**
     * @author Richard Mejia
     * @date 30/07/2018 
     * Método que envía los correos de notificación.
     * @param mensaje
     * @param to
     * @param subject
     * @param cliente
     */
    private static void enviarCorreoNotificacion(String mensaje, String to, String subject) {
        String web_path = context.getRealPath("");
        String mensajeHtml = MantenimientoUtils.CUERPO_CORREO.replace("|cuerpoMensaje|", mensaje);
        Mail.changeDataContact(web_path);
        Mail mail = new Mail(false);
        mail.send(mensajeHtml, to, subject);
    }

    /**
     * @author Richard Mejia
     * @date 09/08/2018
     * Método encargado de actualizar los campos de un vehiculo en mantenimiento
     * dependiendo del tipo de mantenimiento asociado.
     * @param mantenimiento
     * @param vehiculoMantenimiento
     * @param valorAcumulado 
     */
    private static void actualizarVehiculoMantenimiento(Mantenimiento mantenimiento,
            VehiculoMantenimiento vehiculoMantenimiento, double valorAcumulado, 
            boolean esVencimientoDocumentacion) {

        //El mantenimiento es estatico
        if (mantenimiento.getRepetirNotificacion() == VALOR_CERO) {

            if (vehiculoMantenimiento.getYaNotifico() == VALOR_CERO) {
                vehiculoMantenimiento.setValorAcumulado(valorAcumulado);
                vehiculoMantenimiento.setYaNotifico(VALOR_POSITIVO);
            } else {
                vehiculoMantenimiento.setValorAcumulado(valorAcumulado);
            }
        } else { //El mantenimiento es ciclico

            if (vehiculoMantenimiento.getYaNotifico() == VALOR_CERO) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, mantenimiento.getIntervaloRepeticion());
                vehiculoMantenimiento.setFechaProximaNotificacion(calendar.getTime());
                vehiculoMantenimiento.setYaNotifico(VALOR_POSITIVO);
                vehiculoMantenimiento.setValorAcumulado(valorAcumulado);
                vehiculoMantenimiento.setCantidadNotificacionesPendientes(
                            vehiculoMantenimiento.getCantidadNotificacionesPendientes() - VALOR_POSITIVO);
            } else {

                if ((vehiculoMantenimiento.getCantidadNotificacionesPendientes() - VALOR_POSITIVO) == VALOR_CERO) {
                    vehiculoMantenimiento.setYaNotifico(VALOR_CERO);
                    vehiculoMantenimiento.setCantidadNotificacionesPendientes(
                            mantenimiento.getCantidadNotificaciones());
                } else {
                    vehiculoMantenimiento.setCantidadNotificacionesPendientes(
                            vehiculoMantenimiento.getCantidadNotificacionesPendientes() - VALOR_POSITIVO);
                }

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, mantenimiento.getIntervaloRepeticion());
                
                //Si es vencimiento de documentacion y ya no quedan notificaciones, se espera un día para volver a notificar
                if (vehiculoMantenimiento.getCantidadNotificacionesPendientes() == 
                        mantenimiento.getCantidadNotificaciones() && esVencimientoDocumentacion) {
                    calendar.add(Calendar.MINUTE, 1440);
                }
                
                vehiculoMantenimiento.setFechaProximaNotificacion(calendar.getTime());
            }
        }
        
        VehiculoMantenimientoBD.update(vehiculoMantenimiento);
    }
    
    /**
     * @author Richard Mejia
     * @date 09/08/2018
     * Método encargado de agregar los mensajes que se mostraran por pantalla, de enviar
     * los correos electrónicos de notificación y de actualizar las entidades de
     * vehiculos en mantenimiento.
     * @param mensajes
     * @param correos
     * @param mantenimiento
     * @param vehiculoMto
     * @param placa
     * @param valor
     * @param mensajePantalla 
     */
    private static void mostrarNotificacionActualizar(List<String> mensajes, String[] correos, Mantenimiento mantenimiento, 
            VehiculoMantenimiento vehiculoMto, String placa, double valor, String mensajePantalla) {
        agregarMensajeLista(String.format(mensajePantalla, placa, valor), mantenimiento, mensajes);
        enviarCorreosNotificacion(correos, mantenimiento, mensajePantalla, placa, valor);
        actualizarVehiculoMantenimiento(mantenimiento, vehiculoMto, valor, false);
    }
    
    /**
     * @author Richard Mejia
     * @date 13/08/2018
     * Método encargado de actualizar el campo EN_PROCESAMIENTO de un vehiculo en mantenimiento.
     * @param mantenimiento 
     */
    private static void actualizarEstadoMantenimiento(Mantenimiento mantenimiento) {
        mantenimiento.setEnProcesamiento(VALOR_CERO);
        MantenimientoBD.update(mantenimiento);
    }
    
    /**
     * @author Richard Mejia
     * @date 15/08/2018
     * Método que agrega un mensaje con la estructura indicada a la lista de mensajes.
     * @param mensaje
     * @param mantenimiento
     * @param mensajes 
     */
    private static void agregarMensajeLista(String mensaje, Mantenimiento mantenimiento, List<String> mensajes) {
        mensajes.add(mensaje + CADENA_SPLIT + mantenimiento.getNombre() + CADENA_SPLIT + mantenimiento.getId());
    }
    
}
