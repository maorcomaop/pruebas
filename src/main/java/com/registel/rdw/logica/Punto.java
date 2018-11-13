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
public class Punto {
    
    int idpunto;
    String nombre;
    String descripcion;
    String latitud;
    String longitud;
    String latitudWeb;
    String longitudWeb;
    int codigoPunto;
    String direccionLatitud;
    String direccionLongitud;
    int radio;
    int direccion;
    int estado;
    int maxcod;
    String etiquetaPunto;
    String tipo;

    public int getIdpunto() {
        return idpunto;
    }

    public void setIdpunto(int idpunto) {
        this.idpunto = idpunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getCodigoPunto() {
        return codigoPunto;
    }

    public void setCodigoPunto(int codigoPunto) {
        this.codigoPunto = codigoPunto;
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

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getMaxcod() {
        return maxcod;
    }

    public void setMaxcod(int maxcod) {
        this.maxcod = maxcod;
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

    public String getEtiquetaPunto() {
        return etiquetaPunto;
    }

    public void setEtiquetaPunto(String etiquetaPunto) {
        this.etiquetaPunto = etiquetaPunto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
