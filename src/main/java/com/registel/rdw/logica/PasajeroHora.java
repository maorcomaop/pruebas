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
public class PasajeroHora {
    
    private int idRuta;
    private String nombreRuta;
    private String hora;
    private long numeroPasajero;
    private long totalPasajero;
    private long entradas;
    private double iph;

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
        
    public long getNumeroPasajero() {
        return numeroPasajero;
    }

    public void setNumeroPasajero(long numeroPasajero) {
        this.numeroPasajero = numeroPasajero;
    }            

    public long getTotalPasajero() {
        return totalPasajero;
    }

    public void setTotalPasajero(long totalPasajero) {
        this.totalPasajero = totalPasajero;
    }

    public double getIph() {
        return iph;
    }

    public void setIph(double iph) {
        this.iph = iph;
    }

    public long getEntradas() {
        return entradas;
    }

    public void setEntradas(long entradas) {
        this.entradas = entradas;
    }        
}
