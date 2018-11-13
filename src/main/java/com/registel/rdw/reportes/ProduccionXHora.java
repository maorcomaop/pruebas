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
public class ProduccionXHora {
    
    String fecha;
    String placa;
    String numeroInterno;
    long numeracionInicial;
    long numeracionFinal;
    long entradasInicial;
    long entradasFinal;
    long cantidadPasajeros;
    int idRuta;
    String nombreRuta = "NA";
    int hora;

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

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public long getNumeracionInicial() {
        return numeracionInicial;
    }

    public void setNumeracionInicial(long numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }

    public long getNumeracionFinal() {
        return numeracionFinal;
    }

    public void setNumeracionFinal(long numeracionFinal) {
        this.numeracionFinal = numeracionFinal;
    }

    public long getEntradasInicial() {
        return entradasInicial;
    }

    public void setEntradasInicial(long entradasInicial) {
        this.entradasInicial = entradasInicial;
    }

    public long getEntradasFinal() {
        return entradasFinal;
    }

    public void setEntradasFinal(long entradasFinal) {
        this.entradasFinal = entradasFinal;
    }
        
    public long getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(long cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
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

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
    
    
}
