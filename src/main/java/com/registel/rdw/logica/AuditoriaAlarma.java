/**
 * CLASE AUDITORIA Alarma
 * permite crear un objeto de tipo alarma para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaAlarma {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombre;    
    String nuevoTipo;    
    String nuevoUnidadMedicion;        
    String nuevoPrioridad;
    
    String antiguoNombre;    
    String antiguoTipo;    
    String antiguoUnidadMedicion;        
    String antiguoPrioridad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk() {
        return fk;
    }

    public void setFk(int fk) {
        this.fk = fk;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioBD() {
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoTipo() {
        return nuevoTipo;
    }

    public void setNuevoTipo(String nuevoTipo) {
        this.nuevoTipo = nuevoTipo;
    }

    public String getNuevoUnidadMedicion() {
        return nuevoUnidadMedicion;
    }

    public void setNuevoUnidadMedicion(String nuevoUnidadMedicion) {
        this.nuevoUnidadMedicion = nuevoUnidadMedicion;
    }

    public String getNuevoPrioridad() {
        return nuevoPrioridad;
    }

    public void setNuevoPrioridad(String nuevoPrioridad) {
        this.nuevoPrioridad = nuevoPrioridad;
    }

    public String getAntiguoNombre() {
        return antiguoNombre;
    }

    public void setAntiguoNombre(String antiguoNombre) {
        this.antiguoNombre = antiguoNombre;
    }

    public String getAntiguoTipo() {
        return antiguoTipo;
    }

    public void setAntiguoTipo(String antiguoTipo) {
        this.antiguoTipo = antiguoTipo;
    }

    public String getAntiguoUnidadMedicion() {
        return antiguoUnidadMedicion;
    }

    public void setAntiguoUnidadMedicion(String antiguoUnidadMedicion) {
        this.antiguoUnidadMedicion = antiguoUnidadMedicion;
    }

    public String getAntiguoPrioridad() {
        return antiguoPrioridad;
    }

    public void setAntiguoPrioridad(String antiguoPrioridad) {
        this.antiguoPrioridad = antiguoPrioridad;
    }

    
    

    
}
