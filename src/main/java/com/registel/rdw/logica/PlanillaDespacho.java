/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class PlanillaDespacho {
    
    private int id;
    private int idConf;
    private int idRuta;
    private int idIntervalo;
    private String nombreRuta;
    private int numeroPlanilla;
    private int numeroVuelta;
    private String placa;
    private String numeroInterno;
    private Date fecha;
    private int holguraPunto;
    private String punto;
    private String nombrePunto;
    private int intervaloLlegada;
    private Time horaPlanificada;
    private Time horaMin;
    private Time horaMax;
    private Time horaHmin;
    private Time horaHmax;
    private Time horaReal;
    private long diferencia;
    private Time diferenciaTiempo;
    private int estado;
    private int estadoDespacho = 0;
    private int tipoPunto;
    private int vehiculoSustituido;
    private int posicionPunto;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConf() {
        return idConf;
    }

    public void setIdConf(int idConf) {
        this.idConf = idConf;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdIntervalo() {
        return idIntervalo;
    }

    public void setIdIntervalo(int idIntervalo) {
        this.idIntervalo = idIntervalo;
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public int getNumeroPlanilla() {
        return numeroPlanilla;
    }

    public void setNumeroPlanilla(int numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }
    
    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
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
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHolguraPunto() {
        return holguraPunto;
    }

    public void setHolguraPunto(int holguraPunto) {
        this.holguraPunto = holguraPunto;
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
    
    public Time getHoraPlanificada() {
        return horaPlanificada;
    }

    public void setHoraPlanificada(Time horaPlanificada) {
        this.horaPlanificada = horaPlanificada;
    }

    public Time getHoraMin() {
        return horaMin;
    }

    public void setHoraMin(Time horaMin) {
        this.horaMin = horaMin;
    }

    public Time getHoraMax() {
        return horaMax;
    }

    public void setHoraMax(Time horaMax) {
        this.horaMax = horaMax;
    }

    public Time getHoraHmin() {
        return horaHmin;
    }

    public void setHoraHmin(Time horaHmin) {
        this.horaHmin = horaHmin;
    }

    public Time getHoraHmax() {
        return horaHmax;
    }

    public void setHoraHmax(Time horaHmax) {
        this.horaHmax = horaHmax;
    }
    
    public Time getHoraReal() {
        return horaReal;
    }

    public void setHoraReal(Time horaReal) {
        this.horaReal = horaReal;
    }

    public long getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(long diferencia) {
        this.diferencia = diferencia;
    }

    public Time getDiferenciaTiempo() {
        return diferenciaTiempo;
    }

    public void setDiferenciaTiempo(Time diferenciaTiempo) {
        this.diferenciaTiempo = diferenciaTiempo;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstadoDespacho() {
        return estadoDespacho;
    }

    public void setEstadoDespacho(int estadoDespacho) {
        this.estadoDespacho = estadoDespacho;
    }    

    public int getTipoPunto() {
        return tipoPunto;
    }

    public void setTipoPunto(int tipoPunto) {
        this.tipoPunto = tipoPunto;
    }

    public int getVehiculoSustituido() {
        return vehiculoSustituido;
    }

    public void setVehiculoSustituido(int vehiculoSustituido) {
        this.vehiculoSustituido = vehiculoSustituido;
    }

    public int getIntervaloLlegada() {
        return intervaloLlegada;
    }

    public void setIntervaloLlegada(int intervaloLlegada) {
        this.intervaloLlegada = intervaloLlegada;
    }

    public int getPosicionPunto() {
        return posicionPunto;
    }

    public void setPosicionPunto(int posicionPunto) {
        this.posicionPunto = posicionPunto;
    }        
    
    public String toString() {
        
        SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
        
        String fecha = ffmt.format(this.fecha);
        String hora_planificada, hora_real;
        hora_planificada = hora_real = "NA";        
        
        if (this.horaPlanificada != null)
            hora_planificada = hfmt.format(this.horaPlanificada);
        if (this.horaReal != null)
            hora_real = hfmt.format(this.horaReal);
        
        String s = " ", t = "\t";
        String str = "Pto. " +
            this.idRuta             + t +
            this.numeroVuelta       + t +
            fecha                   + s +
            this.placa              + s +
            this.punto              + t +
            this.holguraPunto       + s +
            hora_planificada        + s +
            hora_real               + s +
            this.diferencia         + s +
            this.estadoDespacho;
        
        return str;                        
    }
}
