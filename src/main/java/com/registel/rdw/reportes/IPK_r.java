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
public class IPK_r {
    
    String placa;
    String msg;
    String localizacion;
    Date fecha;
    Time hora;
    long numeracion;
    long cantidadPasajeros;
    long distancia;
    double distanciaRecorrida;
    double IPK;
    boolean esIPK;
    int estadoConsolidacion;
    int esPuntoControl;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
        
    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
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

    public long getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(long numeracion) {
        this.numeracion = numeracion;
    }

    public long getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(long cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public long getDistancia() {
        return distancia;
    }

    public void setDistancia(long distancia) {
        this.distancia = distancia;
    }

    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(double distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public double getIPK() {
        return IPK;
    }

    public void setIPK(double IPK) {
        this.IPK = IPK;
    }

    public boolean getEsIPK() {
        return esIPK;
    }

    public void setEsIPK(boolean esIPK) {
        this.esIPK = esIPK;
    }

    public int getEstadoConsolidacion() {
        return estadoConsolidacion;
    }

    public void setEstadoConsolidacion(int estadoConsolidacion) {
        this.estadoConsolidacion = estadoConsolidacion;
    }

    public int getEsPuntoControl() {
        return esPuntoControl;
    }

    public void setEsPuntoControl(int esPuntoControl) {
        this.esPuntoControl = esPuntoControl;
    }        
}
