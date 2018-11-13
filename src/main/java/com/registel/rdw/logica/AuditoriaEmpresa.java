/**
 * CLASE AUDITORIA Empresa * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaEmpresa {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombre;    
    String nuevoFkCiudad;    
    String nuevoNit;            
    
    String antiguoNombre;    
    String antiguoFkCiudad;    
    String antiguoNit;                

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

    public String getNuevoFkCiudad() {
        return nuevoFkCiudad;
    }

    public void setNuevoFkCiudad(String nuevoFkCiudad) {
        this.nuevoFkCiudad = nuevoFkCiudad;
    }

    public String getNuevoNit() {
        return nuevoNit;
    }

    public void setNuevoNit(String nuevoNit) {
        this.nuevoNit = nuevoNit;
    }

    public String getAntiguoNombre() {
        return antiguoNombre;
    }

    public void setAntiguoNombre(String antiguoNombre) {
        this.antiguoNombre = antiguoNombre;
    }

    public String getAntiguoFkCiudad() {
        return antiguoFkCiudad;
    }

    public void setAntiguoFkCiudad(String antiguoFkCiudad) {
        this.antiguoFkCiudad = antiguoFkCiudad;
    }

    public String getAntiguoNit() {
        return antiguoNit;
    }

    public void setAntiguoNit(String antiguoNit) {
        this.antiguoNit = antiguoNit;
    }



        

    
}
