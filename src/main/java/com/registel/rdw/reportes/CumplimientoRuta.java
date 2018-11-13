/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

/**
 *
 * @author lider_desarrollador
 */
public class CumplimientoRuta {
    
    int idVehiculo;
    String placa;
    String numeroInterno;
    String nombreConductores;
    String nombreRuta;
    int idRuta;
    int puntosRealizados;
    int puntosCumplidos;
    double porcentajeCumplido;

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

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

    public String getNombreConductores() {
        return nombreConductores;
    }

    public void setNombreConductores(String nombreConductores) {
        this.nombreConductores = nombreConductores;
    }
    
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }    
    
    public int getPuntosRealizados() {
        return puntosRealizados;
    }

    public void setPuntosRealizados(int puntosRealizados) {
        this.puntosRealizados = puntosRealizados;
    }

    public int getPuntosCumplidos() {
        return puntosCumplidos;
    }

    public void setPuntosCumplidos(int puntosCumplidos) {
        this.puntosCumplidos = puntosCumplidos;
    }

    public double getPorcentajeCumplido() {
        return porcentajeCumplido;
    }

    public void setPorcentajeCumplido(double porcentajeCumplido) {
        this.porcentajeCumplido = porcentajeCumplido;
    }
            
}
