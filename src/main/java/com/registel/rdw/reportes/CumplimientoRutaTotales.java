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
public class CumplimientoRutaTotales {
    
    private int totalRegistros = 0;
    private int totalPuntosRealizados = 0;
    private int totalPuntosCumplidos = 0;
    double totalPorcentajeCumplido = 0.0;

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public int getTotalPuntosRealizados() {
        return totalPuntosRealizados;
    }

    public void setTotalPuntosRealizados(int totalPuntosRealizados) {
        this.totalPuntosRealizados = totalPuntosRealizados;
    }

    public int getTotalPuntosCumplidos() {
        return totalPuntosCumplidos;
    }

    public void setTotalPuntosCumplidos(int totalPuntosCumplidos) {
        this.totalPuntosCumplidos = totalPuntosCumplidos;
    }

    public double getTotalPorcentajeCumplido() {
        return totalPorcentajeCumplido;
    }

    public void setTotalPorcentajeCumplido(double totalPorcentajeCumplido) {
        this.totalPorcentajeCumplido = totalPorcentajeCumplido;
    }
    
    
    
}
