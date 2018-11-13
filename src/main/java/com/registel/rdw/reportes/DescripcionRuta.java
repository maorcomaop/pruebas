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
public class DescripcionRuta {
    String nombreRuta;
    String nombrePunto;
    int minutosPromedio;
    int minutosHolgura;
    int tipoPunto;

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }

    public int getMinutosPromedio() {
        return minutosPromedio;
    }

    public void setMinutosPromedio(int minutosPromedio) {
        this.minutosPromedio = minutosPromedio;
    }

    public int getMinutosHolgura() {
        return minutosHolgura;
    }

    public void setMinutosHolgura(int minutosHolgura) {
        this.minutosHolgura = minutosHolgura;
    }

    public int getTipoPunto() {
        return tipoPunto;
    }

    public void setTipoPunto(int tipoPunto) {
        this.tipoPunto = tipoPunto;
    }
}
