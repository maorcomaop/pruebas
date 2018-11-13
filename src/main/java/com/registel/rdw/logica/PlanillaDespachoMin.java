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
public class PlanillaDespachoMin {
    
    private int id;    
    private int idRuta;
    private int idIntervalo;
    private int numeroPlanilla;
    private String nombreRuta;    
    private int numeroVuelta;
    private String placa;
    private String numeroInterno;
    private Date fecha;    
    private Time horaInicio;
    private Time horaFin;
    private int tipoGeneracion;
    private int cantidadRegistros;
    private int cantidadPuntos;
    private long tiempoVueltaMin;    
    private boolean iniciaPuntoRetorno;
    
    private String fechaStr;
    private String horaInicioStr;
    private String horaFinStr;
    private String nombreDespacho;
    private Time ultimaHoraRegistrada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }

    public String getHoraInicioStr() {
        return horaInicioStr;
    }

    public void setHoraInicioStr(String horaInicioStr) {
        this.horaInicioStr = horaInicioStr;
    }

    public String getHoraFinStr() {
        return horaFinStr;
    }

    public void setHoraFinStr(String horaFinStr) {
        this.horaFinStr = horaFinStr;
    }

    public String getNombreDespacho() {
        return nombreDespacho;
    }

    public void setNombreDespacho(String nombreDespacho) {
        this.nombreDespacho = nombreDespacho;
    }
    
    public long getTiempoVueltaMin() {
        return tiempoVueltaMin;
    }

    public void setTiempoVueltaMin(long tiempoVueltaMin) {
        this.tiempoVueltaMin = tiempoVueltaMin;
    }        

    public Time getUltimaHoraRegistrada() {
        return ultimaHoraRegistrada;
    }

    public void setUltimaHoraRegistrada(Time ultimaHoraRegistrada) {
        this.ultimaHoraRegistrada = ultimaHoraRegistrada;
    }    

    public int getCantidadPuntos() {
        return cantidadPuntos;
    }

    public void setCantidadPuntos(int cantidadPuntos) {
        this.cantidadPuntos = cantidadPuntos;
    }

    public int getNumeroPlanilla() {
        return numeroPlanilla;
    }

    public void setNumeroPlanilla(int numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }    

    public boolean isIniciaPuntoRetorno() {
        return iniciaPuntoRetorno;
    }

    public void setIniciaPuntoRetorno(boolean iniciaPuntoRetorno) {
        this.iniciaPuntoRetorno = iniciaPuntoRetorno;
    }

    public int getTipoGeneracion() {
        return tipoGeneracion;
    }

    public void setTipoGeneracion(int tipoGeneracion) {
        this.tipoGeneracion = tipoGeneracion;
    }
        
    public String toString() {
        String str = 
            this.numeroPlanilla  + "\t" +
            this.idRuta          + "\t" +
            this.fechaStr        + "\t" +
            this.placa           + "\t" +
            this.numeroVuelta    + "\t" +
            this.horaInicioStr   + "\t" +
            this.horaFinStr;
        return str;
    }
}
