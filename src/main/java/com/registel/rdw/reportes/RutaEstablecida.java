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
public class RutaEstablecida {
    String nombreRuta;
    int estadoCreacion;
    String estadoCreacionStr;

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public int getEstadoCreacion() {
        return estadoCreacion;
    }

    public void setEstadoCreacion(int estadoCreacion) {
        this.estadoCreacion = estadoCreacion;
    }

    public String getEstadoCreacionStr() {
        return estadoCreacionStr;
    }

    public void setEstadoCreacionStr(String estadoCreacionStr) {
        this.estadoCreacionStr = estadoCreacionStr;
    }
    
    
}
