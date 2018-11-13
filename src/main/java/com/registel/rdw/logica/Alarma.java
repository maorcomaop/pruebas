/**
 * CLASE ALARMA
 * permite crear un objeto de tipo alarma para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

public class Alarma {
    
    int id;
    int codigoAlarma;
    String nombreAlarma;
    String tipoAlarma;    
    String unidadDeMedicion;
    int prioridad;        
    int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoAlarma() {
        return codigoAlarma;
    }

    public void setCodigoAlarma(int codigoAlarma) {
        this.codigoAlarma = codigoAlarma;
    }

    public String getNombreAlarma() {
        return nombreAlarma;
    }

    public void setNombreAlarma(String nombreAlarma) {
        this.nombreAlarma = nombreAlarma;
    }

    public String getTipoAlarma() {
        return tipoAlarma;
    }

    public void setTipoAlarma(String tipoAlarma) {
        this.tipoAlarma = tipoAlarma;
    }

    public String getUnidadDeMedicion() {
        return unidadDeMedicion;
    }

    public void setUnidadDeMedicion(String unidadDeMedicion) {
        this.unidadDeMedicion = unidadDeMedicion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}