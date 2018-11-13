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
public class PuntosControl {
    
    String placa;
    String numeroInterno;
    int numeroVuelta;
    String nombrePunto;
    Date fecha;
    Time hora;
    int numeracion;
    int numeracionInicial;
    int entradas;
    int salidas;
    int tipoPunto;
    int cantidadAlarmas;
    boolean esBase = false;

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

    public int getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(int numeracion) {
        this.numeracion = numeracion;
    }

    public int getNumeracionInicial() {
        return numeracionInicial;
    }

    public void setNumeracionInicial(int numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }
    
    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getTipoPunto() {
        return tipoPunto;
    }

    public void setTipoPunto(int tipoPunto) {
        this.tipoPunto = tipoPunto;
    }

    public int getCantidadAlarmas() {
        return cantidadAlarmas;
    }

    public void setCantidadAlarmas(int cantidadAlarmas) {
        this.cantidadAlarmas = cantidadAlarmas;
    }

    public boolean getEsBase() {
        return esBase;
    }

    public void setEsBase(boolean esBase) {
        this.esBase = esBase;
    }
    
}
