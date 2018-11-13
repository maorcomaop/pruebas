/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.sql.Time;

/**
 *
 * @author lider_desarrollador
 */
public class RutaXVehiculoDph {
    
    String fecha;
    int numeroVuelta;
    Time horaPlanificada;
    String horaRealLlegada;
    String diferenciaTiempo;
    int minutosHolgura;
    long diferencia;
    String punto;
    String nombrePunto;
    int tipoPunto;
    String nombreRuta;
    String placa;
    String numeroInterno;
    int estado;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }
    
    public Time getHoraPlanificada() {
        return horaPlanificada;
    }

    public void setHoraPlanificada(Time horaPlanificada) {
        this.horaPlanificada = horaPlanificada;
    }

    public String getHoraRealLlegada() {
        return horaRealLlegada;
    }

    public void setHoraRealLlegada(String horaRealLlegada) {
        this.horaRealLlegada = horaRealLlegada;
    }

    public String getDiferenciaTiempo() {
        return diferenciaTiempo;
    }

    public void setDiferenciaTiempo(String diferenciaTiempo) {
        this.diferenciaTiempo = diferenciaTiempo;
    }

    public int getMinutosHolgura() {
        return minutosHolgura;
    }

    public void setMinutosHolgura(int minutosHolgura) {
        this.minutosHolgura = minutosHolgura;
    }

    public long getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(long diferencia) {
        this.diferencia = diferencia;
    }

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }

    public int getTipoPunto() {
        return tipoPunto;
    }

    public void setTipoPunto(int tipoPunto) {
        this.tipoPunto = tipoPunto;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
}
