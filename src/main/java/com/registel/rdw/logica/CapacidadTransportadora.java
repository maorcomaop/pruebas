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
public class CapacidadTransportadora {

    int idRuta;
    String nombreRuta;
    String hora;
    int nivelOcupacion;
    long numeroPasajeros;
    int capacidad;
    double icuHora;

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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getNivelOcupacion() {
        return nivelOcupacion;
    }

    public void setNivelOcupacion(int nivelOcupacion) {
        this.nivelOcupacion = nivelOcupacion;
    }

    public long getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(long numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getIcuHora() {
        return icuHora;
    }

    public void setIcuHora(double icuHora) {
        this.icuHora = icuHora;
    }    
}
