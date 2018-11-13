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
public class ConsolidadoLiquidacion {

    private int pkVehiculo;
    private String placa;
    private String numInterno;
    private int vueltasLiquidadas;
    private int totalPasajerosLiquidados;
    private int totalPasajerosDescontados;
    private int totalPasajeros;
    private double tarifa;
    private double totalBruto;
    private double totalDescuentosOperativos;
    private double totalDescuentosAlNeto;
    private double totalDescuentosAdministrativos;
    private double subTotal;
    private double totalLiquidacion;

    public int getPkVehiculo() {
        return pkVehiculo;
    }

    public void setPkVehiculo(int pkVehiculo) {
        this.pkVehiculo = pkVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumInterno() {
        return numInterno;
    }

    public void setNumInterno(String numInterno) {
        this.numInterno = numInterno;
    }

    public int getVueltasLiquidadas() {
        return vueltasLiquidadas;
    }

    public void setVueltasLiquidadas(int vueltasLiquidadas) {
        this.vueltasLiquidadas = vueltasLiquidadas;
    }

    public int getTotalPasajerosLiquidados() {
        return totalPasajerosLiquidados;
    }

    public void setTotalPasajerosLiquidados(int totalPasajerosLiquidados) {
        this.totalPasajerosLiquidados = totalPasajerosLiquidados;
    }

    public int getTotalPasajerosDescontados() {
        return totalPasajerosDescontados;
    }

    public void setTotalPasajerosDescontados(int totalPasajerosDescontados) {
        this.totalPasajerosDescontados = totalPasajerosDescontados;
    }

    public int getTotalPasajeros() {
        return totalPasajeros;
    }

    public void setTotalPasajeros(int totalPasajeros) {
        this.totalPasajeros = totalPasajeros;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public double getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(double totalBruto) {
        this.totalBruto = totalBruto;
    }

    public double getTotalDescuentosOperativos() {
        return totalDescuentosOperativos;
    }

    public void setTotalDescuentosOperativos(double totalDescuentosOperativos) {
        this.totalDescuentosOperativos = totalDescuentosOperativos;
    }

    public double getTotalDescuentosAlNeto() {
        return totalDescuentosAlNeto;
    }

    public void setTotalDescuentosAlNeto(double totalDescuentosAlNeto) {
        this.totalDescuentosAlNeto = totalDescuentosAlNeto;
    }

    public double getTotalDescuentosAdministrativos() {
        return totalDescuentosAdministrativos;
    }

    public void setTotalDescuentosAdministrativos(double totalDescuentosAdministrativos) {
        this.totalDescuentosAdministrativos = totalDescuentosAdministrativos;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotalLiquidacion() {
        return totalLiquidacion;
    }

    public void setTotalLiquidacion(double totalLiquidacion) {
        this.totalLiquidacion = totalLiquidacion;
    }

    

}
