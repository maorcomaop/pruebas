/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class ConductorXVehiculo {
    String placa;
    String numeroInterno;
    String nombreConductor;
    Date fechaAsignacion;
    String fechaHoraAsignacion;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getFechaHoraAsignacion() {
        return fechaHoraAsignacion;
    }

    public void setFechaHoraAsignacion(String fechaHoraAsignacion) {
        this.fechaHoraAsignacion = fechaHoraAsignacion;
    }        
}
