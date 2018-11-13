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
public class GrupoRodamiento {
    
    int idGrupo;
    String nombre;
    
    public GrupoRodamiento(int id, String nombre) {
        this.idGrupo = id;
        this.nombre  = nombre;
    }
    
    public GrupoRodamiento(GrupoRodamiento gr) {
        this.idGrupo = gr.getIdGrupo();
        this.nombre  = gr.getNombre();
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
}
