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
public class BaseRuta {

    private int id;
    private int idRuta;
    private int idPunto;
    private int idBase;    
    private String nombreBase;
    private String nombreRuta;
    private String latitud;
    private String longitud;
    private String latitudWeb;
    private String longitudWeb;
    private String direccionLatitud;
    private String direccionLongitud;
    private int promedioMinutos;
    private int holguraMinutos;
    private int valorPunto;    
    private int tipo;
    private int estado;
    
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
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

    public int getValorPunto() {
        return valorPunto;
    }

    public void setValorPunto(int valorPunto) {
        this.valorPunto = valorPunto;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
