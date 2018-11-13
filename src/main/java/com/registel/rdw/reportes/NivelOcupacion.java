/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class NivelOcupacion {
    
    String placa;
    String numeroInterno;
    int numeroVuelta;
    String nombreRuta;
    String nombrePunto;
    Date fecha;
    Time hora;
    int nivelOcupacion;
    int icu;
    boolean esBase;

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

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getNivelOcupacion() {
        return nivelOcupacion;
    }

    public void setNivelOcupacion(int nivelOcupacion) {
        this.nivelOcupacion = nivelOcupacion;
    }

    public int getIcu() {
        return icu;
    }

    public void setIcu(int icu) {
        this.icu = icu;
    }

    public boolean esBase() {
        return esBase;
    }

    public void setEsBase(boolean esBase) {
        this.esBase = esBase;
    }
    
}
