/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.io.Serializable;

/**
 *
 * @author lider_desarrollador
 */
public class Pais implements Serializable {
    
    private int id;
    private String nombre;
    private int codArea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodArea() {
        return codArea;
    }

    public void setCodArea(int cod_area) {
        this.codArea = cod_area;
    }
    
    
}
