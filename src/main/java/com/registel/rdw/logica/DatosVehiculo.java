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
public class DatosVehiculo {

    private String placa;
    private String fecha;
    private long numeracionInicial;
    private long numeracionFinal;
    private long distanciaRecorrida;
    private double ipk;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
     
    public long getNumeracionInicial() {
        return numeracionInicial;
    }

    public void setNumeracionInicial(long numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }

    public long getNumeracionFinal() {
        return numeracionFinal;
    }

    public void setNumeracionFinal(long numeracionFinal) {
        this.numeracionFinal = numeracionFinal;
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
    
    public String toString() {
        String s = "\t";
        return this.placa   + s +
               this.numeracionInicial   + s +
               this.numeracionFinal     + s +
               this.distanciaRecorrida;
    }
}
