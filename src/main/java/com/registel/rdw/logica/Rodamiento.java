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
public class Rodamiento {
    
    private int dia;
    private String nombreDia;    
    private ArrayList<RutaRodamiento> lst_ruta;
    private ArrayList<GrupoRodamiento> lst_grupo;

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public ArrayList<RutaRodamiento> getLst_ruta() {
        return lst_ruta;
    }

    public void setLst_ruta(ArrayList<RutaRodamiento> lst_ruta) {
        this.lst_ruta = lst_ruta;
    }

    public ArrayList<GrupoRodamiento> getLst_grupo() {
        return lst_grupo;
    }

    public void setLst_grupo(ArrayList<GrupoRodamiento> lst_grupo) {
        this.lst_grupo = lst_grupo;
    }
}
