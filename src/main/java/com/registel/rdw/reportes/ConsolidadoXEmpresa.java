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
public class ConsolidadoXEmpresa {
    
    String placa;
    String numeroInterno;
    String empresa;
    int cantidadVueltas;
    int cantidadPasajeros;
    int promedioPasajeros;
    int conteoPerimetro;
    int conteoAlarmas;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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

    public int getConteoPerimetro() {
        return conteoPerimetro;
    }

    public void setConteoPerimetro(int conteoPerimetro) {
        this.conteoPerimetro = conteoPerimetro;
    }

    public int getConteoAlarmas() {
        return conteoAlarmas;
    }

    public void setConteoAlarmas(int conteoAlarmas) {
        this.conteoAlarmas = conteoAlarmas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
