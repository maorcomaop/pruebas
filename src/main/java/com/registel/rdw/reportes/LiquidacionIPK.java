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
public class LiquidacionIPK {
    
    int pk_vehiculo;    
    String empresa;
    String placa;
    String numero_interno;    
    String ruta;    
    double ipk;
    double total_km;
    int vueltasLiquidadas;    
    int totalPasajeros;
    int totalPasajerosLiquidados;
    int totalPasajerosDescontados;    
    int tarifa;
    
    double totalBrutoPasajeros;
    double totalDescuentosAlNeto;
    double subTotal;
    double totalDescuentosOperativos;
    double totalDescuentosAdminsitrativos;
    double totalLiquidacion;
    
    long distanciaKm;
    double distanciaRecorrida;    
    double promedioIPK;

    public int getPk_vehiculo() {
        return pk_vehiculo;
    }

    public void setPk_vehiculo(int pk_vehiculo) {
        this.pk_vehiculo = pk_vehiculo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumero_interno() {
        return numero_interno;
    }

    public void setNumero_interno(String numero_interno) {
        this.numero_interno = numero_interno;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    public double getTotal_km() {
        return total_km;
    }

    public void setTotal_km(double total_km) {
        this.total_km = total_km;
    }

  

    public int getVueltasLiquidadas() {
        return vueltasLiquidadas;
    }

    public void setVueltasLiquidadas(int vueltasLiquidadas) {
        this.vueltasLiquidadas = vueltasLiquidadas;
    }

    public int getTotalPasajeros() {
        return totalPasajeros;
    }

    public void setTotalPasajeros(int totalPasajeros) {
        this.totalPasajeros = totalPasajeros;
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

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public double getTotalBrutoPasajeros() {
        return totalBrutoPasajeros;
    }

    public void setTotalBrutoPasajeros(double totalBrutoPasajeros) {
        this.totalBrutoPasajeros = totalBrutoPasajeros;
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

    public double getTotalDescuentosOperativos() {
        return totalDescuentosOperativos;
    }

    public void setTotalDescuentosOperativos(double totalDescuentosOperativos) {
        this.totalDescuentosOperativos = totalDescuentosOperativos;
    }

    public double getTotalDescuentosAdminsitrativos() {
        return totalDescuentosAdminsitrativos;
    }

    public void setTotalDescuentosAdminsitrativos(double totalDescuentosAdminsitrativos) {
        this.totalDescuentosAdminsitrativos = totalDescuentosAdminsitrativos;
    }

    public double getTotalLiquidacion() {
        return totalLiquidacion;
    }

    public void setTotalLiquidacion(double totalLiquidacion) {
        this.totalLiquidacion = totalLiquidacion;
    }

    public long getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(long distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(double distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public double getPromedioIPK() {
        return promedioIPK;
    }

    public void setPromedioIPK(double promedioIPK) {
        this.promedioIPK = promedioIPK;
    }

    
    
    
    
}
