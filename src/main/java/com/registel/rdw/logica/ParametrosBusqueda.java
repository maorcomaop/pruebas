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
public class ParametrosBusqueda {
    
    // Informacion registradora
    String idMovil;
    String idUsuario;
    String fechaInicio;
    String fechaFinal;
    String limite;
    String tipoDia;
    String itemMovil;
    
    String idBaseSalida;
    String idBaseLlegada;
    String idRuta;
    String numeroVuelta;

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }        

    public String getItemMovil() {
        return itemMovil;
    }

    public void setItemMovil(String itemMovil) {
        this.itemMovil = itemMovil;
    }

    public String getIdBaseSalida() {
        return idBaseSalida;
    }

    public void setIdBaseSalida(String idBaseSalida) {
        this.idBaseSalida = idBaseSalida;
    }

    public String getIdBaseLlegada() {
        return idBaseLlegada;
    }

    public void setIdBaseLlegada(String idBaseLlegada) {
        this.idBaseLlegada = idBaseLlegada;
    }

    public String getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(String numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }
}
