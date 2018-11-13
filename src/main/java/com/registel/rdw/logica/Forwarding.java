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
 * @date 23/07/2018
 */
public class Forwarding {
    
    private long id;
    private Date fechaServer;
    private Date fechaGps;
    private String rumbo;
    private double rumboRadianes;
    private String gpsId;
    private int velocidad;
    private int transReason;
    private String transReasonSpecificData;
    private String msg;
    private long totalDia;
    private long numeracion;
    private int entradas;
    private int salidas;
    private String alarma;
    private int distanciaMetros;
    private String localizacion;
    private String latitud;
    private String longitud;
    private String placa;
    private String nombreFlota;
    private int estadoConsolidacion;
    private int numeracionInicial;
    private int numeracionFinal;
    private long idInicial;
    private long idFinal;
    private int totalPasajeros;
    private boolean liquidacionRelativa;

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
     * @return the fechaServer
     */
    public Date getFechaServer() {
        return fechaServer;
    }

    /**
     * @param fechaServer the fechaServer to set
     */
    public void setFechaServer(Date fechaServer) {
        this.fechaServer = fechaServer;
    }

    /**
     * @return the fecha_gps
     */
    public Date getFechaGps() {
        return fechaGps;
    }

    /**
     * @param fechaGps the fechaGps to set
     */
    public void setFechaGps(Date fechaGps) {
        this.fechaGps = fechaGps;
    }

    /**
     * @return the rumbo
     */
    public String getRumbo() {
        return rumbo;
    }

    /**
     * @param rumbo the rumbo to set
     */
    public void setRumbo(String rumbo) {
        this.rumbo = rumbo;
    }

    /**
     * @return the rumboRadianes
     */
    public double getRumboRadianes() {
        return rumboRadianes;
    }

    /**
     * @param rumboRadianes the rumboRadianes to set
     */
    public void setRumboRadianes(double rumboRadianes) {
        this.rumboRadianes = rumboRadianes;
    }

    /**
     * @return the gpsId
     */
    public String getGpsId() {
        return gpsId;
    }

    /**
     * @param gpsId the gpsId to set
     */
    public void setGpsId(String gpsId) {
        this.gpsId = gpsId;
    }

    /**
     * @return the velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * @return the transReason
     */
    public int getTransReason() {
        return transReason;
    }

    /**
     * @param transReason the transReason to set
     */
    public void setTransReason(int transReason) {
        this.transReason = transReason;
    }

    /**
     * @return the transReasonSpecificData
     */
    public String getTransReasonSpecificData() {
        return transReasonSpecificData;
    }

    /**
     * @param transReasonSpecificData the transReasonSpecificData to set
     */
    public void setTransReasonSpecificData(String transReasonSpecificData) {
        this.transReasonSpecificData = transReasonSpecificData;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the totaldia
     */
    public long getTotaldia() {
        return totalDia;
    }

    /**
     * @param totalDia the totalDia to set
     */
    public void setTotalDia(long totalDia) {
        this.totalDia = totalDia;
    }

    /**
     * @return the numeracion
     */
    public long getNumeracion() {
        return numeracion;
    }

    /**
     * @param numeracion the numeracion to set
     */
    public void setNumeracion(long numeracion) {
        this.numeracion = numeracion;
    }

    /**
     * @return the entradas
     */
    public int getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    /**
     * @return the salidas
     */
    public int getSalidas() {
        return salidas;
    }

    /**
     * @param salidas the salidas to set
     */
    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    /**
     * @return the alarma
     */
    public String getAlarma() {
        return alarma;
    }

    /**
     * @param alarma the alarma to set
     */
    public void setAlarma(String alarma) {
        this.alarma = alarma;
    }

    /**
     * @return the distanciaMetros
     */
    public int getDistanciaMetros() {
        return distanciaMetros;
    }

    /**
     * @param distanciaMetros the distanciaMetros to set
     */
    public void setDistanciaMetros(int distanciaMetros) {
        this.distanciaMetros = distanciaMetros;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @param localizacion the localizacion to set
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     * @return the latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the nombreFlota
     */
    public String getNombreFlota() {
        return nombreFlota;
    }

    /**
     * @param nombreFlota the nombreFlota to set
     */
    public void setNombreFlota(String nombreFlota) {
        this.nombreFlota = nombreFlota;
    }

    /**
     * @return the estadoConsolidacion
     */
    public int getEstadoConsolidacion() {
        return estadoConsolidacion;
    }

    /**
     * @param estadoConsolidacion the estadoConsolidacion to set
     */
    public void setEstadoConsolidacion(int estadoConsolidacion) {
        this.estadoConsolidacion = estadoConsolidacion;
    }

    /**
     * @return the numeracionInicial
     */
    public int getNumeracionInicial() {
        return numeracionInicial;
    }

    /**
     * @param numeracionInicial the numeracionInicial to set
     */
    public void setNumeracionInicial(int numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }

    /**
     * @return the numeracionFinal
     */
    public int getNumeracionFinal() {
        return numeracionFinal;
    }

    /**
     * @param numeracionFinal the numeracionFinal to set
     */
    public void setNumeracionFinal(int numeracionFinal) {
        this.numeracionFinal = numeracionFinal;
    }

    /**
     * @return the idInicial
     */
    public long getIdInicial() {
        return idInicial;
    }

    /**
     * @param idInicial the idInicial to set
     */
    public void setIdInicial(long idInicial) {
        this.idInicial = idInicial;
    }

    /**
     * @return the idFinal
     */
    public long getIdFinal() {
        return idFinal;
    }

    /**
     * @param idFinal the idFinal to set
     */
    public void setIdFinal(long idFinal) {
        this.idFinal = idFinal;
    }

    /**
     * @return the totalPasajeros
     */
    public int getTotalPasajeros() {
        return totalPasajeros;
    }

    /**
     * @param totalPasajeros the totalPasajeros to set
     */
    public void setTotalPasajeros(int totalPasajeros) {
        this.totalPasajeros = totalPasajeros;
    }

    /**
     * @return the liquidacionRelativa
     */
    public boolean isLiquidacionRelativa() {
        return liquidacionRelativa;
    }

    /**
     * @param liquidacionRelativa the liquidacionRelativa to set
     */
    public void setLiquidacionRelativa(boolean liquidacionRelativa) {
        this.liquidacionRelativa = liquidacionRelativa;
    }
    
}
