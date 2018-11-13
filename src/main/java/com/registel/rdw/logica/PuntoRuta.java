/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class PuntoRuta {
    
    private int id;
    private int idPunto;
    private int idBase;
    private int idRuta;    
    private int promedioMinutos;
    private int holguraMinutos;
    private int tiempoValle;
    private int tiempoPico;
    private int valorPunto;
    private int tipo;
    private int estado;
    private int codigoPunto;
    private String nombreRuta;
    private String nombrePunto;    
    private String etiquetaPunto;
    private String latitud;
    private String longitud;
    private String latitudWeb;
    private String longitudWeb;
    private String direccionLatitud;
    private String direccionLongitud;
    private boolean puntoVisto = false;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }
    
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }
    
    public int getPromedioMinutos() {
        return promedioMinutos;
    }

    public void setPromedioMinutos(int promedioMinutos) {
        this.promedioMinutos = promedioMinutos;
    }

    public int getHolguraMinutos() {
        return holguraMinutos;
    }

    public void setHolguraMinutos(int holguraMinutos) {
        this.holguraMinutos = holguraMinutos;
    }

    public int getTiempoValle() {
        return tiempoValle;
    }

    public void setTiempoValle(int tiempoValle) {
        this.tiempoValle = tiempoValle;
    }

    public int getTiempoPico() {
        return tiempoPico;
    }

    public void setTiempoPico(int tiempoPico) {
        this.tiempoPico = tiempoPico;
    }
    
    public int getValorPunto() {
        return valorPunto;
    }

    public void setValorPunto(int valorPunto) {
        this.valorPunto = valorPunto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }

    public String getEtiquetaPunto() {
        return etiquetaPunto;
    }

    public void setEtiquetaPunto(String etiquetaPunto) {
        this.etiquetaPunto = etiquetaPunto;
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

    public String getDireccionLatitud() {
        return direccionLatitud;
    }

    public void setDireccionLatitud(String direccionLatitud) {
        this.direccionLatitud = direccionLatitud;
    }

    public String getDireccionLongitud() {
        return direccionLongitud;
    }

    public void setDireccionLongitud(String direccionLongitud) {
        this.direccionLongitud = direccionLongitud;
    }

    public String getLatitudWeb() {
        return latitudWeb;
    }

    public void setLatitudWeb(String latitudWeb) {
        this.latitudWeb = latitudWeb;
    }

    public String getLongitudWeb() {
        return longitudWeb;
    }

    public void setLongitudWeb(String longitudWeb) {
        this.longitudWeb = longitudWeb;
    }

    public boolean puntoVisto() {
        return puntoVisto;
    }

    public void setPuntoVisto(boolean puntoVisto) {
        this.puntoVisto = puntoVisto;
    }
    
    public int getCodigoPunto() {
        return codigoPunto;
    }

    public void setCodigoPunto(int codigoPunto) {
        this.codigoPunto = codigoPunto;
    }
}
