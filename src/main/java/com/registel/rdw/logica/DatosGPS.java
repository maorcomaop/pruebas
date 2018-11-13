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
public class DatosGPS {

    long id;
    Time fechaServidor;
    Date fechaServidorDate;
    Date fechaHoraServidor;
    Time fechaGPS;
    Date fechaGPSDate;
    Date fechaHoraGPS;
    String rumbo;
    int velocidad;
    String msg;
    String gpsId;
    long totalDia;
    long numeracion;
    long numeracionInicial;
    long numeracionFinal;
    long distanciaRecorrida;
    int entradas;
    int salidas;
    String alarma;
    int distanciaMetros;
    String localizacion;
    String localizacion_proc;
    String latitud;
    String longitud;
    String placa;
    String nombreFlota;
    int nivelOcupacion;
    String noic; // Nivel de ocupacion vs indice de capacidad
    double ipk;
    String ipk_str;    

    int esPuntoControl = 1;
    int esPasajero = 1;
    int esAlarma = 1;
    int estadoConsolidacion = 0;

    int transReason;
    String transReasonSpecificData;
    double rumboRadianes;
    
    long distanciaParcial;
    double ipkParcial;
    String ipk_str_parcial;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Time getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(Time fechaServidor) {
        this.fechaServidor = fechaServidor;
    }

    public Time getFechaGPS() {
        return fechaGPS;
    }

    public void setFechaGPS(Time fechaGPS) {
        this.fechaGPS = fechaGPS;
    }

    public Date getFechaServidorDate() {
        return fechaServidorDate;
    }

    public void setFechaServidorDate(Date fechaServidorDate) {
        this.fechaServidorDate = fechaServidorDate;
    }

    public Date getFechaGPSDate() {
        return fechaGPSDate;
    }

    public void setFechaGPSDate(Date fechaGPSDate) {
        this.fechaGPSDate = fechaGPSDate;
    }

    public String getRumbo() {
        return rumbo;
    }

    public void setRumbo(String rumbo) {
        this.rumbo = rumbo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTotalDia() {
        return totalDia;
    }

    public void setTotalDia(long totalDia) {
        this.totalDia = totalDia;
    }

    public long getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(long numeracion) {
        this.numeracion = numeracion;
    }

    public long getNumeracionInicial() {
        return numeracionInicial;
    }

    public void setNumeracionInicial(long numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }

    public long getNumeracionFinal() {
        return numeracionFinal;
    }

    public void setNumeracionFinal(long numeracionFinal) {
        this.numeracionFinal = numeracionFinal;
    }

    public long getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(long distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public String getAlarma() {
        return alarma;
    }

    public void setAlarma(String alarma) {
        this.alarma = alarma;
    }

    public int getDistanciaMetros() {
        return distanciaMetros;
    }

    public void setDistanciaMetros(int distanciaMetros) {
        this.distanciaMetros = distanciaMetros;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombreFlota() {
        return nombreFlota;
    }

    public void setNombreFlota(String nombreFlota) {
        this.nombreFlota = nombreFlota;
    }

    public int getEsPuntoControl() {
        return esPuntoControl;
    }

    public void setEsPuntoControl(int esPuntoControl) {
        this.esPuntoControl = esPuntoControl;
    }

    public int getEsPasajero() {
        return esPasajero;
    }

    public void setEsPasajero(int esPasajero) {
        this.esPasajero = esPasajero;
    }

    public int getEsAlarma() {
        return esAlarma;
    }

    public void setEsAlarma(int esAlarma) {
        this.esAlarma = esAlarma;
    }

    public String getLocalizacion_proc() {
        return localizacion_proc;
    }

    public void setLocalizacion_proc(String localizacion_proc) {
        this.localizacion_proc = localizacion_proc;
    }

    public int getEstadoConsolidacion() {
        return estadoConsolidacion;
    }

    public void setEstadoConsolidacion(int estadoConsolidacion) {
        this.estadoConsolidacion = estadoConsolidacion;
    }

    public int getTransReason() {
        return transReason;
    }

    public void setTransReason(int transReason) {
        this.transReason = transReason;
    }

    public String getTransReasonSpecificData() {
        return transReasonSpecificData;
    }

    public void setTransReasonSpecificData(String transReasonSpecificData) {
        this.transReasonSpecificData = transReasonSpecificData;
    }

    public double getRumboRadianes() {
        return rumboRadianes;
    }

    public void setRumboRadianes(double rumboRadianes) {
        this.rumboRadianes = rumboRadianes;
    }

    public int getNivelOcupacion() {
        return nivelOcupacion;
    }

    public void setNivelOcupacion(int nivelOcupacion) {
        this.nivelOcupacion = nivelOcupacion;
    }

    public String getNoic() {
        return noic;
    }

    public void setNoic(String noic) {
        this.noic = noic;
    }   

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }        

    public String getIpk_str() {
        return ipk_str;
    }

    public void setIpk_str(String ipk) {
        this.ipk_str = ipk;
    }        

    public Date getFechaHoraServidor() {
        return fechaHoraServidor;
    }

    public void setFechaHoraServidor(Date fechaHoraServidor) {
        this.fechaHoraServidor = fechaHoraServidor;
    }

    public Date getFechaHoraGPS() {
        return fechaHoraGPS;
    }

    public void setFechaHoraGPS(Date fechaHoraGPS) {
        this.fechaHoraGPS = fechaHoraGPS;
    }

    public String getGpsId() {
        return gpsId;
    }

    public void setGpsId(String gpsId) {
        this.gpsId = gpsId;
    }

    public long getDistanciaParcial() {
        return distanciaParcial;
    }

    public void setDistanciaParcial(long distanciaParcial) {
        this.distanciaParcial = distanciaParcial;
    }

    public double getIpkParcial() {
        return ipkParcial;
    }

    public void setIpkParcial(double ipkParcial) {
        this.ipkParcial = ipkParcial;
    }

    public String getIpk_str_parcial() {
        return ipk_str_parcial;
    }

    public void setIpk_str_parcial(String ipk_str_parcial) {
        this.ipk_str_parcial = ipk_str_parcial;
    }        
    
    public String toString() {
        String s = "\t";
        return 
            this.fechaGPSDate   +" "+
            this.fechaGPS       + s +
            this.placa          + s +
            //this.msg            + s +    
            this.numeracion     + s +
            this.totalDia       + s +
            this.ipk            + s +    
            this.distanciaMetros    + s +
            this.distanciaRecorrida
            + s + " | " + s +
            this.ipkParcial     + s +
            this.distanciaParcial;
    }
}
