/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class VueltaCerrada {
    
    Date fecha;
    String fechaStr;
    String placa;
    String numero_interno;
    Time hora;
    String base;
    String baseGps;
    long numeracion;
    String usuario;
    String motivo;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBaseGps() {
        return baseGps;
    }

    public void setBaseGps(String baseGps) {
        this.baseGps = baseGps;
    }
    
    public long getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(long numeracion) {
        this.numeracion = numeracion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
