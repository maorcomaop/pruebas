/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class IndicadorCapacidadTransportadora {
    
    private int idRuta;
    private String nombreRuta;
    private ArrayList<CapacidadTransportadora> detalle;

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

    public ArrayList<CapacidadTransportadora> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<CapacidadTransportadora> detalle) {
        this.detalle = detalle;
    }        
}
