/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 * @author Richard Mejia
 * 
 * @date 03/08/2018
 */
public class VehiculoMantenimiento extends EntidadConfigurable {
    
    private int fkVehiculo;
    private long fkMantenimiento;
    private double valorInicialMantenimiento;
    private double valorAcumulado;
    private int cantidadNotificacionesPendientes;
    private int yaNotifico;
    private Date fechaProximaNotificacion;
    private String nombreMantenimiento;
    private String placa;
    private int eliminado;
    
    /**
     * @return the fkVehiculo
     */
    public int getFkVehiculo() {
        return fkVehiculo;
    }

    /**
     * @param fkVehiculo the fkVehiculo to set
     */
    public void setFkVehiculo(int fkVehiculo) {
        this.fkVehiculo = fkVehiculo;
    }

    /**
     * @return the fkMantenimiento
     */
    public long getFkMantenimiento() {
        return fkMantenimiento;
    }

    /**
     * @param fkMantenimiento the fkMantenimiento to set
     */
    public void setFkMantenimiento(long fkMantenimiento) {
        this.fkMantenimiento = fkMantenimiento;
    }

    /**
     * @return the valorInicialMantenimiento
     */
    public double getValorInicialMantenimiento() {
        return valorInicialMantenimiento;
    }

    /**
     * @param valorInicialMantenimiento the valorInicialMantenimiento to set
     */
    public void setValorInicialMantenimiento(double valorInicialMantenimiento) {
        this.valorInicialMantenimiento = valorInicialMantenimiento;
    }

    /**
     * @return the valorAcumulado
     */
    public double getValorAcumulado() {
        return valorAcumulado;
    }

    /**
     * @param valorAcumulado the valorAcumulado to set
     */
    public void setValorAcumulado(double valorAcumulado) {
        this.valorAcumulado = valorAcumulado;
    }

    /**
     * @return the cantidadNotificacionesPendientes
     */
    public int getCantidadNotificacionesPendientes() {
        return cantidadNotificacionesPendientes;
    }

    /**
     * @param cantidadNotificacionesPendientes the cantidadNotificacionesPendientes to set
     */
    public void setCantidadNotificacionesPendientes(int cantidadNotificacionesPendientes) {
        this.cantidadNotificacionesPendientes = cantidadNotificacionesPendientes;
    }

    /**
     * @return the yaNotifico
     */
    public int getYaNotifico() {
        return yaNotifico;
    }

    /**
     * @param yaNotifico the yaNotifico to set
     */
    public void setYaNotifico(int yaNotifico) {
        this.yaNotifico = yaNotifico;
    }

    /**
     * @return the fechaProximaNotificacion
     */
    public Date getFechaProximaNotificacion() {
        return fechaProximaNotificacion;
    }

    /**
     * @param fechaProximaNotificacion the fechaProximaNotificacion to set
     */
    public void setFechaProximaNotificacion(Date fechaProximaNotificacion) {
        this.fechaProximaNotificacion = fechaProximaNotificacion;
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
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the eliminado
     */
    public int getEliminado() {
        return eliminado;
    }

    /**
     * @param eliminado the eliminado to set
     */
    public void setEliminado(int eliminado) {
        this.eliminado = eliminado;
    }
    
}
