/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class ConductorVehiculo {
    
    private int idConductor;
    private String fecha;
    private String placa;
    private int incumplimientoRuta;
    private int excesoVelocidad;
    private double ipk;

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getIncumplimientoRuta() {
        return incumplimientoRuta;
    }

    public void setIncumplimientoRuta(int incumplimientoRuta) {
        this.incumplimientoRuta = incumplimientoRuta;
    }

    public int getExcesoVelocidad() {
        return excesoVelocidad;
    }

    public void setExcesoVelocidad(int excesoVelocidad) {
        this.excesoVelocidad = excesoVelocidad;
    }        

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }            
}
