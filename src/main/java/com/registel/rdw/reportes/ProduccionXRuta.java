/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class ProduccionXRuta {
    
    String placa;
    String numeroInterno;
    int cantidadVueltas;
    int cantidadPasajeros;
    int promedioPasajeros;
    Date fecha;

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

    public int getCantidadVueltas() {
        return cantidadVueltas;
    }

    public void setCantidadVueltas(int cantidadVueltas) {
        this.cantidadVueltas = cantidadVueltas;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public int getPromedioPasajeros() {
        return promedioPasajeros;
    }

    public void setPromedioPasajeros(int promedioPasajeros) {
        this.promedioPasajeros = promedioPasajeros;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
