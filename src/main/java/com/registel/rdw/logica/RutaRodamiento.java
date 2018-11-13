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
public class RutaRodamiento {
    
    int idRuta;
    String nombre;
    
    public RutaRodamiento(int id, String nombre) {
        this.idRuta = id;
        this.nombre = nombre;
    }
    
    public RutaRodamiento(RutaRodamiento rr) {
        this.idRuta = rr.getIdRuta();
        this.nombre = rr.getNombre();
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
