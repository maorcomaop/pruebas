/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class IntervaloDespacho {
    
    private int id;
    private String nombreIntervalo;
    private String listaRuta;
    private String listaNombreRuta;
    private Date fechaInicial;
    private Date fechaFinal;
    private int cantidadDias;
    private int estado;   
    
    String placa;
    Time horaInicial;
    Time horaFinal;
    int numeroPlanilla;
    int numeroVuelta;    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreIntervalo() {
        return nombreIntervalo;
    }

    public void setNombreIntervalo(String nombreIntervalo) {
        this.nombreIntervalo = nombreIntervalo;
    }
    
    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) {
        this.cantidadDias = cantidadDias;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }    

    public String getListaRuta() {
        return listaRuta;
    }

    public void setListaRuta(String listaRuta) {
        this.listaRuta = listaRuta;
    }

    public String getListaNombreRuta() {
        return listaNombreRuta;
    }

    public void setListaNombreRuta(String listaNombreRuta) {
        this.listaNombreRuta = listaNombreRuta;
    }    

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Time getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Time horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    public int getNumeroPlanilla() {
        return numeroPlanilla;
    }

    public void setNumeroPlanilla(int numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }
}
