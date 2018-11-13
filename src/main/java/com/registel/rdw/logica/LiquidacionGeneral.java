/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
public class LiquidacionGeneral implements Serializable {

    private int id;
    private int fkTarifaFija;
    private int fkVehiculo;
    private int fkConductor;
    private int totalPasajeros;
    private int totalPasajerosLiquidados;
    private double totalValorVueltas;
    private double totalValorDescuentosPasajeros;
    private double totalValorDescuentosAdicional;
    private double totalValorOtrosDescuentos;
    private double ipk;
    private int estado;
    private int modificacionLocal;
    private Date fechaLiquidacion;
    private Date fechaModificacion;
    private String pkUnica;
    private int fkUsuario;
    private double distancia;
    private long idInicialGps;
    private long idFinalGps;
    private int numeracionInicialGps;
    private int numeracionFinalGps;
    private String fechaInicioLiquidacion;
    private String fechaFinLiquidacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkTarifaFija() {
        return fkTarifaFija;
    }

    public void setFkTarifaFija(int fkTarifaFija) {
        this.fkTarifaFija = fkTarifaFija;
    }

    public int getFkVehiculo() {
        return fkVehiculo;
    }

    public void setFkVehiculo(int fkVehiculo) {
        this.fkVehiculo = fkVehiculo;
    }

    public int getFkConductor() {
        return fkConductor;
    }

    public void setFkConductor(int fkConductor) {
        this.fkConductor = fkConductor;
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

    public double getTotalValorVueltas() {
        return totalValorVueltas;
    }

    public void setTotalValorVueltas(double totalValorVueltas) {
        this.totalValorVueltas = totalValorVueltas;
    }

    public double getTotalValorDescuentosPasajeros() {
        return totalValorDescuentosPasajeros;
    }

    public void setTotalValorDescuentosPasajeros(double totalValorDescuentosPasajeros) {
        this.totalValorDescuentosPasajeros = totalValorDescuentosPasajeros;
    }

    public double getTotalValorDescuentosAdicional() {
        return totalValorDescuentosAdicional;
    }

    public void setTotalValorDescuentosAdicional(double totalValorDescuentosAdicional) {
        this.totalValorDescuentosAdicional = totalValorDescuentosAdicional;
    }

    public double getTotalValorOtrosDescuentos() {
        return totalValorOtrosDescuentos;
    }

    public void setTotalValorOtrosDescuentos(double totalValorOtrosDescuentos) {
        this.totalValorOtrosDescuentos = totalValorOtrosDescuentos;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getModificacionLocal() {
        return modificacionLocal;
    }

    public void setModificacionLocal(int modificacionLocal) {
        this.modificacionLocal = modificacionLocal;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getPkUnica() {
        return pkUnica;
    }

    public void setPkUnica(String pkUnica) {
        this.pkUnica = pkUnica;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    /**
     * @return the idInicialGps
     */
    public long getIdInicialGps() {
        return idInicialGps;
    }

    /**
     * @param idInicialGps the idInicialGps to set
     */
    public void setIdInicialGps(long idInicialGps) {
        this.idInicialGps = idInicialGps;
    }

    /**
     * @return the idFinalGps
     */
    public long getIdFinalGps() {
        return idFinalGps;
    }

    /**
     * @param idFinalGps the idFinalGps to set
     */
    public void setIdFinalGps(long idFinalGps) {
        this.idFinalGps = idFinalGps;
    }

    /**
     * @return the numeracionInicialGps
     */
    public int getNumeracionInicialGps() {
        return numeracionInicialGps;
    }

    /**
     * @param numeracionInicialGps the numeracionInicialGps to set
     */
    public void setNumeracionInicialGps(int numeracionInicialGps) {
        this.numeracionInicialGps = numeracionInicialGps;
    }

    /**
     * @return the numeracionFinalGps
     */
    public int getNumeracionFinalGps() {
        return numeracionFinalGps;
    }

    /**
     * @param numeracionFinalGps the numeracionFinalGps to set
     */
    public void setNumeracionFinalGps(int numeracionFinalGps) {
        this.numeracionFinalGps = numeracionFinalGps;
    }

    /**
     * @return the fechaInicioLiquidacion
     */
    public String getFechaInicioLiquidacion() {
        return fechaInicioLiquidacion;
    }

    /**
     * @param fechaInicioLiquidacion the fechaInicioLiquidacion to set
     */
    public void setFechaInicioLiquidacion(String fechaInicioLiquidacion) {
        this.fechaInicioLiquidacion = fechaInicioLiquidacion;
    }

    /**
     * @return the fechaFinLiquidacion
     */
    public String getFechaFinLiquidacion() {
        return fechaFinLiquidacion;
    }

    /**
     * @param fechaFinLiquidacion the fechaFinLiquidacion to set
     */
    public void setFechaFinLiquidacion(String fechaFinLiquidacion) {
        this.fechaFinLiquidacion = fechaFinLiquidacion;
    }

    
    
    
    
}
