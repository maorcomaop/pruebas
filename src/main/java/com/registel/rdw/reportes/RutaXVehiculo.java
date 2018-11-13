/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import net.sf.jasperreports.engine.JRDataSource;

import java.sql.Time;

/**
 *
 * @author lider_desarrollador
 */
public class RutaXVehiculo {
   
    int idPunto;
    String puntoControl;    
    String ruta;
    Time horaPlanificada;
    Time horaRealLlegada;
    int minutosHolgura;
    int estado;
    Time diferenciaTiempo;
    boolean llegadaEnHolgura;   
    
    Time tiempoAdelantado;
    Time tiempoAtrasado;
    int numeracion;
    int numeroVuelta;
    
    String fecha;
    String placa;
    String numeroInterno;
    
    JRDataSource dataSource;

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }
    
    public String getPuntoControl() {
        return puntoControl;
    }

    public void setPuntoControl(String puntoControl) {
        this.puntoControl = puntoControl;
    }

    public Time getHoraPlanificada() {
        return horaPlanificada;
    }

    public void setHoraPlanificada(Time horaPlanificada) {
        this.horaPlanificada = horaPlanificada;
    }

    public Time getHoraRealLlegada() {
        return horaRealLlegada;
    }

    public void setHoraRealLlegada(Time horaRealLlegada) {
        this.horaRealLlegada = horaRealLlegada;
    }

    public int getMinutosHolgura() {
        return minutosHolgura;
    }

    public void setMinutosHolgura(int minutosHolgura) {
        this.minutosHolgura = minutosHolgura;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Time getDiferenciaTiempo() {
        return diferenciaTiempo;
    }

    public void setDiferenciaTiempo(Time diferenciaTiempo) {
        this.diferenciaTiempo = diferenciaTiempo;
    }

    public boolean getLlegadaEnHolgura() {
        return llegadaEnHolgura;
    }

    public void setLlegadaEnHolgura(boolean llegadaEnHolgura) {
        this.llegadaEnHolgura = llegadaEnHolgura;
    }

    public Time getTiempoAdelantado() {
        return tiempoAdelantado;
    }

    public void setTiempoAdelantado(Time tiempoAdelantado) {
        this.tiempoAdelantado = tiempoAdelantado;
    }

    public Time getTiempoAtrasado() {
        return tiempoAtrasado;
    }

    public void setTiempoAtrasado(Time tiempoAtrasado) {
        this.tiempoAtrasado = tiempoAtrasado;
    }

    public int getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(int numeracion) {
        this.numeracion = numeracion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }
    
    public JRDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
    
    public String toString() {
        String str = "{ " +
                numeroVuelta     + " " +
                ruta             + " " +
                puntoControl     + " " +
                horaPlanificada  + " " +
                horaRealLlegada  + " " +
                minutosHolgura   + " " +
                tiempoAdelantado + " " +
                numeracion + " }";
        return str;
    }
    
}
