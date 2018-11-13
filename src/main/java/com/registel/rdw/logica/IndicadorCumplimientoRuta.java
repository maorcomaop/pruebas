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
public class IndicadorCumplimientoRuta {
    
    private int idRuta;
    private String nombreRuta;
    private int numeroVuelta;
    private double cumplimientoRuta;
    private int cumplimientoRutaEnt;

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

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }

    public double getCumplimientoRuta() {
        return cumplimientoRuta;
    }

    public void setCumplimientoRuta(double cumplimientoRuta) {
        this.cumplimientoRuta = cumplimientoRuta;
    }

    public int getCumplimientoRutaEnt() {
        return cumplimientoRutaEnt;
    }

    public void setCumplimientoRutaEnt(int cumplimientoRutaEnt) {
        this.cumplimientoRutaEnt = cumplimientoRutaEnt;
    }           
}
