/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class Planilla {
    
    private int numeroPlanilla;
    private int numeroVuelta;
    private Date fecha;
    private String placa; 
    private String numeroInterno;
    private String ruta;
    private int idRuta;
    private int vehiculoSustituido;
    private boolean vueltaUnica = false;
    private boolean estiloFila = false;    
    private String estiloFilaStr;
    
    private ArrayList<PlanillaDespacho> detalle;

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
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
    
    public ArrayList<PlanillaDespacho> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<PlanillaDespacho> detalle) {
        this.detalle = detalle;
    }    

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }    

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getVehiculoSustituido() {
        return vehiculoSustituido;
    }

    public void setVehiculoSustituido(int vehiculoSustituido) {
        this.vehiculoSustituido = vehiculoSustituido;
    }    

    public boolean isEstiloFila() {
        return estiloFila;
    }

    public void setEstiloFila(boolean estiloFila) {
        this.estiloFila = estiloFila;
    }

    public String getEstiloFilaStr() {
        return estiloFilaStr;
    }

    public void setEstiloFilaStr(String estiloFilaStr) {
        this.estiloFilaStr = estiloFilaStr;
    }

    public boolean isVueltaUnica() {
        return vueltaUnica;
    }

    public void setVueltaUnica(boolean vueltaUnica) {
        this.vueltaUnica = vueltaUnica;
    }
}
