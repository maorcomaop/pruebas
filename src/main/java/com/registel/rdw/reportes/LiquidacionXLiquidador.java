/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

/**
 *
 * @author USER
 */
public class LiquidacionXLiquidador {

    private String placa;
    private int numLiquidaciones;
    private int numInterno;
    private double totalBruto;
    private double totalDescuentos;
    private double totalDescuentosAlNeto;
    private double subTotal;
    private double totalOtrosDescuentos;
    private double totalPasajerosDescontados;
    private double totalLiquidacion;
    private String liquidador;
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getNumLiquidaciones() {
        return numLiquidaciones;
    }

    public void setNumLiquidaciones(int numLiquidaciones) {
        this.numLiquidaciones = numLiquidaciones;
    }

    public int getNumInterno() {
        return numInterno;
    }

    public void setNumInterno(int numInterno) {
        this.numInterno = numInterno;
    }

    public double getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(double totalBruto) {
        this.totalBruto = totalBruto;
    }

    public double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public double getTotalDescuentosAlNeto() {
        return totalDescuentosAlNeto;
    }

    public void setTotalDescuentosAlNeto(double totalDescuentosAlNeto) {
        this.totalDescuentosAlNeto = totalDescuentosAlNeto;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotalOtrosDescuentos() {
        return totalOtrosDescuentos;
    }

    public void setTotalOtrosDescuentos(double totalOtrosDescuentos) {
        this.totalOtrosDescuentos = totalOtrosDescuentos;
    }

    public double getTotalPasajerosDescontados() {
        return totalPasajerosDescontados;
    }

    public void setTotalPasajerosDescontados(double totalPasajerosDescontados) {
        this.totalPasajerosDescontados = totalPasajerosDescontados;
    }

    public double getTotalLiquidacion() {
        return totalLiquidacion;
    }

    public void setTotalLiquidacion(double totalLiquidacion) {
        this.totalLiquidacion = totalLiquidacion;
    }

    public String getLiquidador() {
        return liquidador;
    }

    public void setLiquidador(String liquidador) {
        this.liquidador = liquidador;
    }
    
    

}
