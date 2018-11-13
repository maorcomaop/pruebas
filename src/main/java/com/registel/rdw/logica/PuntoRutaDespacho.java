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
public class PuntoRutaDespacho {
    
    private int id;
    private int idPunto;
    private int idRuta;    
    private int codigoPunto;
    private int promedioMinutos;
    private int tiempoHolgura;
    private int tiempoValle;
    private int tiempoPico;    
    private String etiquetaPunto;
    private String nombrePunto;
    private boolean puntoEnDespacho = false;     
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }
    
    public int getPromedioMinutos() {
        return promedioMinutos;
    }

    public void setPromedioMinutos(int promedioMinutos) {
        this.promedioMinutos = promedioMinutos;
    }

    public boolean isPuntoEnDespacho() {
        return puntoEnDespacho;
    }

    public void setPuntoEnDespacho(boolean puntoEnDespacho) {
        this.puntoEnDespacho = puntoEnDespacho;
    }

    public int getTiempoHolgura() {
        return tiempoHolgura;
    }

    public void setTiempoHolgura(int tiempoHolgura) {
        this.tiempoHolgura = tiempoHolgura;
    }

    public int getTiempoValle() {
        return tiempoValle;
    }

    public void setTiempoValle(int tiempoValle) {
        this.tiempoValle = tiempoValle;
    }

    public int getTiempoPico() {
        return tiempoPico;
    }

    public void setTiempoPico(int tiempoPico) {
        this.tiempoPico = tiempoPico;
    }

    public int getCodigoPunto() {
        return codigoPunto;
    }

    public void setCodigoPunto(int codigoPunto) {
        this.codigoPunto = codigoPunto;
    }

    public String getEtiquetaPunto() {
        return etiquetaPunto;
    }

    public void setEtiquetaPunto(String etiquetaPunto) {
        this.etiquetaPunto = etiquetaPunto;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }
    
}
