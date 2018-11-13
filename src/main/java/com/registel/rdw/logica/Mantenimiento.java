/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 *
 * @author Richard Mejia
 */
public class Mantenimiento extends EntidadConfigurable {
    
    private long fkTipoEventoMantenimiento;
    private int valorPrimeraNotificacion;
    private int repetirNotificacion;
    private int intervaloRepeticion;
    private int notificacionActiva;
    private Date fechaActivacionNotificacion;
    private String nombre;
    private String correosNotificacion;
    private String observacionesNotificacion;
    private int fkUsuario;
    private String nombreMantenimiento;
    private String unidadMedida;
    private String correosDisplay;
    private String fechaActivacionNotificacionString;
    private int enProcesamiento;
    private int cantidadNotificaciones;
    
    /**
     * @return the fkTipoEventoMantenimiento
     */
    public long getFkTipoEventoMantenimiento() {
        return fkTipoEventoMantenimiento;
    }

    /**
     * @param fkTipoEventoMantenimiento the fkTipoEventoMantenimiento to set
     */
    public void setFkTipoEventoMantenimiento(long fkTipoEventoMantenimiento) {
        this.fkTipoEventoMantenimiento = fkTipoEventoMantenimiento;
    }

    /**
     * @return the valorPrimeraNotificacion
     */
    public int getValorPrimeraNotificacion() {
        return valorPrimeraNotificacion;
    }

    /**
     * @param valorPrimeraNotificacion the valorPrimeraNotificacion to set
     */
    public void setValorPrimeraNotificacion(int valorPrimeraNotificacion) {
        this.valorPrimeraNotificacion = valorPrimeraNotificacion;
    }

    /**
     * @return the repetirNotificacion
     */
    public int getRepetirNotificacion() {
        return repetirNotificacion;
    }

    /**
     * @param repetirNotificacion the repetirNotificacion to set
     */
    public void setRepetirNotificacion(int repetirNotificacion) {
        this.repetirNotificacion = repetirNotificacion;
    }

    /**
     * @return the intervaloRepeticion
     */
    public int getIntervaloRepeticion() {
        return intervaloRepeticion;
    }

    /**
     * @param intervaloRepeticion the intervaloRepeticion to set
     */
    public void setIntervaloRepeticion(int intervaloRepeticion) {
        this.intervaloRepeticion = intervaloRepeticion;
    }

    /**
     * @return the notificacionActiva
     */
    public int getNotificacionActiva() {
        return notificacionActiva;
    }

    /**
     * @param notificacionActiva the notificacionActiva to set
     */
    public void setNotificacionActiva(int notificacionActiva) {
        this.notificacionActiva = notificacionActiva;
    }

    /**
     * @return the fechaActivacionNotificacion
     */
    public Date getFechaActivacionNotificacion() {
        return fechaActivacionNotificacion;
    }

    /**
     * @param fechaActivacionNotificacion the fechaActivacionNotificacion to set
     */
    public void setFechaActivacionNotificacion(Date fechaActivacionNotificacion) {
        this.fechaActivacionNotificacion = fechaActivacionNotificacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the correosNotificacion
     */
    public String getCorreosNotificacion() {
        return correosNotificacion;
    }

    /**
     * @param correosNotificacion the correosNotificacion to set
     */
    public void setCorreosNotificacion(String correosNotificacion) {
        this.correosNotificacion = correosNotificacion;
    }

    /**
     * @return the observacionesNotificacion
     */
    public String getObservacionesNotificacion() {
        return observacionesNotificacion;
    }

    /**
     * @param observacionesNotificacion the observacionesNotificacion to set
     */
    public void setObservacionesNotificacion(String observacionesNotificacion) {
        this.observacionesNotificacion = observacionesNotificacion;
    }

    /**
     * @return the fkUsuario
     */
    public int getFkUsuario() {
        return fkUsuario;
    }

    /**
     * @param fkUsuario the fkUsuario to set
     */
    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    /**
     * @return the nombreMantenimiento
     */
    public String getNombreMantenimiento() {
        return nombreMantenimiento;
    }

    /**
     * @param nombreMantenimiento the nombreMantenimiento to set
     */
    public void setNombreMantenimiento(String nombreMantenimiento) {
        this.nombreMantenimiento = nombreMantenimiento;
    }

    /**
     * @return the unidadMedida
     */
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * @param unidadMedida the unidadMedida to set
     */
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * @return the correosDisplay
     */
    public String getCorreosDisplay() {
        return correosDisplay;
    }

    /**
     * @param correosDisplay the correosDisplay to set
     */
    public void setCorreosDisplay(String correosDisplay) {
        this.correosDisplay = correosDisplay;
    }

    /**
     * @return the fechaActivacionNotificacionString
     */
    public String getFechaActivacionNotificacionString() {
        return fechaActivacionNotificacionString;
    }

    /**
     * @param fechaActivacionNotificacionString the fechaActivacionNotificacionString to set
     */
    public void setFechaActivacionNotificacionString(String fechaActivacionNotificacionString) {
        this.fechaActivacionNotificacionString = fechaActivacionNotificacionString;
    }

    /**
     * @return the enProcesamiento
     */
    public int getEnProcesamiento() {
        return enProcesamiento;
    }

    /**
     * @param enProcesamiento the enProcesamiento to set
     */
    public void setEnProcesamiento(int enProcesamiento) {
        this.enProcesamiento = enProcesamiento;
    }

    /**
     * @return the cantidadNotificaciones
     */
    public int getCantidadNotificaciones() {
        return cantidadNotificaciones;
    }

    /**
     * @param cantidadNotificaciones the cantidadNotificaciones to set
     */
    public void setCantidadNotificaciones(int cantidadNotificaciones) {
        this.cantidadNotificaciones = cantidadNotificaciones;
    }
    
}
