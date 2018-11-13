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
public class DetalleRuta {
    
    private int idRuta;    
    private ArrayList<PuntoRuta> lst_pr;
    private double porcentajeRuta;

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public ArrayList<PuntoRuta> getLst() {
        return lst_pr;
    }

    public void setLst(ArrayList<PuntoRuta> lst_pr) {
        this.lst_pr = lst_pr;
    }

    public ArrayList<PuntoRuta> getLst_pr() {
        return lst_pr;
    }

    public void setLst_pr(ArrayList<PuntoRuta> lst_pr) {
        this.lst_pr = lst_pr;
    }

    public double getPorcentajeRuta() {
        return porcentajeRuta;
    }

    public void setPorcentajeRuta(double porcentajeRuta) {
        this.porcentajeRuta = porcentajeRuta;
    }

    // Compara listado de puntos de ruta respecto a listado
    // de puntos de control, a traves de su codigo y calcula
    // el porcentaje coincidencia en cantidad de puntos.
    public double compare(ArrayList<PuntoControl> lst_pc) {
        
        int npto = 0;
        
        for (int i = 0; i < lst_pc.size(); i++) {
            PuntoControl pc = lst_pc.get(i);
            
            for (int j = 0; j < lst_pr.size(); j++) {
                PuntoRuta pr = lst_pr.get(j);
                if (!pr.puntoVisto() && pr.getIdPunto() == pc.getIdPunto()) {
                    pr.setPuntoVisto(true);
                    npto += 1;
                    break;
                }
            }
        }
        
        porcentajeRuta = 0.0;
        if (lst_pr != null && lst_pr.size() > 0) {
            porcentajeRuta = npto / (double) lst_pr.size();
        } return porcentajeRuta;
    }
}
