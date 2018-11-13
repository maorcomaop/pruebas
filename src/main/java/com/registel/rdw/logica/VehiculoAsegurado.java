/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 * @author Richard Mejia
 * 
 * @date 16/07/2018
 */
public class VehiculoAsegurado {
    
    private long id;
    private long fkPoliza;
    private int fkVehiculo;
    private String numeroPoliza;
    private Date inicioVigencia;
    private Date finVigencia;
    private double valorPrima;
    private int estado;
    private String nombrePoliza;
    private String nombreAseguradora;
    private String placaVehiculo;
    private String nombreEmpresaVehiculo;
    private String inicioVigenciaBack;
    private String finVigenciaBack;

    /**
     * @return the pk_poliza_vehiculo
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the fk_poliza
     */
    public long getFkPoliza() {
        return fkPoliza;
    }

    /**
     * @param fk_poliza the fk_poliza to set
     */
    public void setFkPoliza(long fk_poliza) {
        this.fkPoliza = fk_poliza;
    }

    /**
     * @return the fk_vehiculo
     */
    public int getFkVehiculo() {
        return fkVehiculo;
    }

    /**
     * @param fk_vehiculo the fk_vehiculo to set
     */
    public void setFkVehiculo(int fk_vehiculo) {
        this.fkVehiculo = fk_vehiculo;
    }

    /**
     * @return the numero_poliza
     */
    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    /**
     * @param numero_poliza the numero_poliza to set
     */
    public void setNumeroPoliza(String numero_poliza) {
        this.numeroPoliza = numero_poliza;
    }

    /**
     * @return the inicioVigencia
     */
    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    /**
     * @param inicioVigencia the inicioVigencia to set
     */
    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    /**
     * @return the finVigencia
     */
    public Date getFinVigencia() {
        return finVigencia;
    }

    /**
     * @param finVigencia the finVigencia to set
     */
    public void setFinVigencia(Date finVigencia) {
        this.finVigencia = finVigencia;
    }

    /**
     * @return the valorPrima
     */
    public double getValorPrima() {
        return valorPrima;
    }

    /**
     * @param valorPrima the valorPrima to set
     */
    public void setValorPrima(double valorPrima) {
        this.valorPrima = valorPrima;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the nombrePoliza
     */
    public String getNombrePoliza() {
        return nombrePoliza;
    }

    /**
     * @param nombrePoliza the nombrePoliza to set
     */
    public void setNombrePoliza(String nombrePoliza) {
        this.nombrePoliza = nombrePoliza;
    }

    /**
     * @return the nombreAseguradora
     */
    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    /**
     * @param nombreAseguradora the nombreAseguradora to set
     */
    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    /**
     * @return the placaVehiculo
     */
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    /**
     * @param placaVehiculo the placaVehiculo to set
     */
    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    /**
     * @return the nombreEmpresaVehiculo
     */
    public String getNombreEmpresaVehiculo() {
        return nombreEmpresaVehiculo;
    }

    /**
     * @param nombreEmpresaVehiculo the nombreEmpresaVehiculo to set
     */
    public void setNombreEmpresaVehiculo(String nombreEmpresaVehiculo) {
        this.nombreEmpresaVehiculo = nombreEmpresaVehiculo;
    }

    /**
     * @return the inicioVigenciaBack
     */
    public String getInicioVigenciaBack() {
        return inicioVigenciaBack;
    }

    /**
     * @param inicioVigenciaBack the inicioVigenciaBack to set
     */
    public void setInicioVigenciaBack(String inicioVigenciaBack) {
        this.inicioVigenciaBack = inicioVigenciaBack;
    }

    /**
     * @return the finVigenciaBack
     */
    public String getFinVigenciaBack() {
        return finVigenciaBack;
    }

    /**
     * @param finVigenciaBack the finVigenciaBack to set
     */
    public void setFinVigenciaBack(String finVigenciaBack) {
        this.finVigenciaBack = finVigenciaBack;
    }
    
}
