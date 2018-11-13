/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class AuditoriaLiquidacionGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    private int pkAuditoriaLiquidacionGeneral;
    private int fkLiqidacionGeneral;
    private int fkTarifaFija;
    private int fkVehiculo;
    private int fkConductor;
    private int fkUsuario;
    private double TotalPasajerosLiquidadosOld;
    private double TotalPasajerosLiquidadosNew;
    private double TotalValorVueltasOld;
    private double TotalValorVueltasNew;
    private double TotalValorDescuentosPasajerosOld;
    private double TotalValorDescuentosPasajerosNew;
    private double TotalValorDescuentosAdicionalOld;
    private double TotalValorDescuentosAdicionalNew;
    private int EstadoOld;
    private int EstadoNew;
    private String UsuarioRegisdata;
    private String UsuarioBd;

    public int getPkAuditoriaLiquidacionGeneral() {
        return pkAuditoriaLiquidacionGeneral;
    }

    public void setPkAuditoriaLiquidacionGeneral(int pkAuditoriaLiquidacionGeneral) {
        this.pkAuditoriaLiquidacionGeneral = pkAuditoriaLiquidacionGeneral;
    }

    public int getFkLiqidacionGeneral() {
        return fkLiqidacionGeneral;
    }

    public void setFkLiqidacionGeneral(int fkLiqidacionGeneral) {
        this.fkLiqidacionGeneral = fkLiqidacionGeneral;
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

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public double getTotalPasajerosLiquidadosOld() {
        return TotalPasajerosLiquidadosOld;
    }

    public void setTotalPasajerosLiquidadosOld(double TotalPasajerosLiquidadosOld) {
        this.TotalPasajerosLiquidadosOld = TotalPasajerosLiquidadosOld;
    }

    public double getTotalPasajerosLiquidadosNew() {
        return TotalPasajerosLiquidadosNew;
    }

    public void setTotalPasajerosLiquidadosNew(double TotalPasajerosLiquidadosNew) {
        this.TotalPasajerosLiquidadosNew = TotalPasajerosLiquidadosNew;
    }

    public double getTotalValorVueltasOld() {
        return TotalValorVueltasOld;
    }

    public void setTotalValorVueltasOld(double TotalValorVueltasOld) {
        this.TotalValorVueltasOld = TotalValorVueltasOld;
    }

    public double getTotalValorVueltasNew() {
        return TotalValorVueltasNew;
    }

    public void setTotalValorVueltasNew(double TotalValorVueltasNew) {
        this.TotalValorVueltasNew = TotalValorVueltasNew;
    }

    public double getTotalValorDescuentosPasajerosOld() {
        return TotalValorDescuentosPasajerosOld;
    }

    public void setTotalValorDescuentosPasajerosOld(double TotalValorDescuentosPasajerosOld) {
        this.TotalValorDescuentosPasajerosOld = TotalValorDescuentosPasajerosOld;
    }

    public double getTotalValorDescuentosPasajerosNew() {
        return TotalValorDescuentosPasajerosNew;
    }

    public void setTotalValorDescuentosPasajerosNew(double TotalValorDescuentosPasajerosNew) {
        this.TotalValorDescuentosPasajerosNew = TotalValorDescuentosPasajerosNew;
    }

    public double getTotalValorDescuentosAdicionalOld() {
        return TotalValorDescuentosAdicionalOld;
    }

    public void setTotalValorDescuentosAdicionalOld(double TotalValorDescuentosAdicionalOld) {
        this.TotalValorDescuentosAdicionalOld = TotalValorDescuentosAdicionalOld;
    }

    public double getTotalValorDescuentosAdicionalNew() {
        return TotalValorDescuentosAdicionalNew;
    }

    public void setTotalValorDescuentosAdicionalNew(double TotalValorDescuentosAdicionalNew) {
        this.TotalValorDescuentosAdicionalNew = TotalValorDescuentosAdicionalNew;
    }

    public int getEstadoOld() {
        return EstadoOld;
    }

    public void setEstadoOld(int EstadoOld) {
        this.EstadoOld = EstadoOld;
    }

    public int getEstadoNew() {
        return EstadoNew;
    }

    public void setEstadoNew(int EstadoNew) {
        this.EstadoNew = EstadoNew;
    }

    public String getUsuarioRegisdata() {
        return UsuarioRegisdata;
    }

    public void setUsuarioRegisdata(String UsuarioRegisdata) {
        this.UsuarioRegisdata = UsuarioRegisdata;
    }

    public String getUsuarioBd() {
        return UsuarioBd;
    }

    public void setUsuarioBd(String UsuarioBd) {
        this.UsuarioBd = UsuarioBd;
    }

    

}
