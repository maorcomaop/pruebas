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
public class IndicadorProduccion {
    
    private String nombreRuta;
    private long produccion;
    private long distanciaRecorrida;
    double ipk;
        
    public static final int PRODUCCION_RUTA             = 1;
    public static final int PRODUCCION_TOTAL_RUTA       = 2;
    public static final int PRODUCCION_TOTAL_SIN_RUTA   = 3;
   
    private int tipoRegistro = PRODUCCION_RUTA;

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public long getProduccion() {
        return produccion;
    }

    public void setProduccion(long produccion) {
        this.produccion = produccion;
    }        

    public long getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(long distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }        

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }    

    public int getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(int tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }        
}
