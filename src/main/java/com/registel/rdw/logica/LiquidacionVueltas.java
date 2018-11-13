/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 *
 * @author USER
 */
public class LiquidacionVueltas {

    private int id;
    private int fkInformacionRegistradora;
    private int fkLiquidacionGeneral;
    private int estado;
    private double pasajerosDescuento;
    private double valorDescuento;
    private String motivoDescuento;
    private Date fechaDescuento;
    private Date fechaModificacion;
    private String pkUnica;

    public double getPasajerosDescuento() {
        return pasajerosDescuento;
    }

    public void setPasajerosDescuento(int pasajerosDescuento) {
        this.pasajerosDescuento = pasajerosDescuento;
    }

    public double getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(double valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public String getMotivoDescuento() {
        return motivoDescuento;
    }

    public void setMotivoDescuento(String motivoDescuento) {
        this.motivoDescuento = motivoDescuento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkInformacionRegistradora() {
        return fkInformacionRegistradora;
    }

    public void setFkInformacionRegistradora(int fkInformacionRegistradora) {
        this.fkInformacionRegistradora = fkInformacionRegistradora;
    }

    public int getFkLiquidacionGeneral() {
        return fkLiquidacionGeneral;
    }

    public void setFkLiquidacionGeneral(int fkLiquidacionGeneral) {
        this.fkLiquidacionGeneral = fkLiquidacionGeneral;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaDescuento() {
        return fechaDescuento;
    }

    public void setFechaDescuento(Date fechaDescuento) {
        this.fechaDescuento = fechaDescuento;
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

}
