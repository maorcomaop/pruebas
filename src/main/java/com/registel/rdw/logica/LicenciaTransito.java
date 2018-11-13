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
 * @date 12/07/2018
 */
public class LicenciaTransito {
    
    private long id;
    private int fk_conductor;
    private String numeroLicencia;
    private Date fechaExpedicion;
    private Date vigencia;
    private long fk_oficina_transito;
    private int estado;
    private String observaciones;
    private long fk_categoria;
    private String nombreConductor;
    private String nombreCategoria;
    private String nombreOficina;
    
    private String fechaExpedicionString;
    private String vigenciaString;
    
    
    /**
     * @return the id
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
     * @return the fk_conductor
     */
    public int getFk_conductor() {
        return fk_conductor;
    }

    /**
     * @param fk_conductor the fk_conductor to set
     */
    public void setFk_conductor(int fk_conductor) {
        this.fk_conductor = fk_conductor;
    }

    /**
     * @return the numeroLicencia
     */
    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    /**
     * @param numeroLicencia the numeroLicencia to set
     */
    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    /**
     * @return the fechaExpedicion
     */
    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    /**
     * @param fechaExpedicion the fechaExpedicion to set
     */
    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    /**
     * @return the vigencia
     */
    public Date getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia the vigencia to set
     */
    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the fk_oficina_transito
     */
    public long getFk_oficina_transito() {
        return fk_oficina_transito;
    }

    /**
     * @param fk_oficina_transito the fk_oficina_transito to set
     */
    public void setFk_oficina_transito(long fk_oficina_transito) {
        this.fk_oficina_transito = fk_oficina_transito;
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
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the fk_categoria
     */
    public long getFk_categoria() {
        return fk_categoria;
    }

    /**
     * @param fk_categoria the fk_categoria to set
     */
    public void setFk_categoria(long fk_categoria) {
        this.fk_categoria = fk_categoria;
    }

    /**
     * @return the nombreConductor
     */
    public String getNombreConductor() {
        return nombreConductor;
    }

    /**
     * @param nombreConductor the nombreConductor to set
     */
    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    /**
     * @return the nombreCategoria
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * @param nombreCategoria the nombreCategoria to set
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * @return the nombreOficina
     */
    public String getNombreOficina() {
        return nombreOficina;
    }

    /**
     * @param nombreOficina the nombreOficina to set
     */
    public void setNombreOficina(String nombreOficina) {
        this.nombreOficina = nombreOficina;
    }

    /**
     * @return the fechaExpedicionString
     */
    public String getFechaExpedicionString() {
        return fechaExpedicionString;
    }

    /**
     * @param fechaExpedicionString the fechaExpedicionString to set
     */
    public void setFechaExpedicionString(String fechaExpedicionString) {
        this.fechaExpedicionString = fechaExpedicionString;
    }

    /**
     * @return the vigenciaString
     */
    public String getVigenciaString() {
        return vigenciaString;
    }

    /**
     * @param vigenciaString the vigenciaString to set
     */
    public void setVigenciaString(String vigenciaString) {
        this.vigenciaString = vigenciaString;
    }
    
}
