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
public class ParametroConsulta {
    
    String fechaInicio;
    String fechaFin;
    String placa;
    int fechaPredeterminada;
    
    boolean verPuntoControl;    
    boolean verPasajero;
    boolean verAlarma;
    boolean verConsolidado;
    boolean verOtros;
    
    boolean esFecha;
    boolean esPlaca;

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean verPuntoControl() {
        return verPuntoControl;
    }

    public void setVerPuntoControl(boolean verPuntoControl) {
        this.verPuntoControl = verPuntoControl;
    }

    public boolean verPasajero() {
        return verPasajero;
    }

    public void setVerPasajero(boolean verPasajero) {
        this.verPasajero = verPasajero;
    }

    public boolean verAlarma() {
        return verAlarma;
    }

    public void setVerAlarma(boolean verAlarma) {
        this.verAlarma = verAlarma;
    }

    public boolean verConsolidado() {
        return verConsolidado;
    }

    public void setVerConsolidado(boolean verConsolidado) {
        this.verConsolidado = verConsolidado;
    }

    public boolean isVerOtros() {
        return verOtros;
    }

    public void setVerOtros(boolean verOtros) {
        this.verOtros = verOtros;
    }
    
    public boolean esFecha() {
        return esFecha;
    }

    public void setEsFecha(boolean esFecha) {
        this.esFecha = esFecha;
    }

    public boolean esPlaca() {
        return esPlaca;
    }

    public void setEsPlaca(boolean esPlaca) {
        this.esPlaca = esPlaca;
    }                

    public int getFechaPredeterminada() {
        return fechaPredeterminada;
    }

    public void setFechaPredeterminada(int fechaPredeterminada) {
        this.fechaPredeterminada = fechaPredeterminada;
    }        
}
