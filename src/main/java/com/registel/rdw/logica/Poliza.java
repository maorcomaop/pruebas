/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 * @author Richard Mejia
 * 
 * @date 13/07/2018
 */
public class Poliza extends EntidadConfigurable {
    
    private String nombre;
    private long fkAseguradora;
    private String amparosCoberturas;
    private String nombreAseguradora;
    private long fkTipoPoliza;
    private String nombreTipoPoliza;

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fkAseguradora
     */
    public long getFkAseguradora() {
        return fkAseguradora;
    }

    /**
     * @param fkAseguradora the fkAseguradora to set
     */
    public void setFkAseguradora(long fkAseguradora) {
        this.fkAseguradora = fkAseguradora;
    }

    /**
     * @return the amparosCoberturas
     */
    public String getAmparosCoberturas() {
        return amparosCoberturas;
    }

    /**
     * @param amparosCoberturas the amparosCoberturas to set
     */
    public void setAmparosCoberturas(String amparosCoberturas) {
        this.amparosCoberturas = amparosCoberturas;
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
     * @return the fkTipoPoliza
     */
    public long getFkTipoPoliza() {
        return fkTipoPoliza;
    }

    /**
     * @param fkTipoPoliza the fkTipoPoliza to set
     */
    public void setFkTipoPoliza(long fkTipoPoliza) {
        this.fkTipoPoliza = fkTipoPoliza;
    }

    /**
     * @return the nombreTipoPoliza
     */
    public String getNombreTipoPoliza() {
        return nombreTipoPoliza;
    }

    /**
     * @param nombreTipoPoliza the nombreTipoPoliza to set
     */
    public void setNombreTipoPoliza(String nombreTipoPoliza) {
        this.nombreTipoPoliza = nombreTipoPoliza;
    }
    
}
