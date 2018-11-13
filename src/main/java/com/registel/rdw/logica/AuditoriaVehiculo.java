/**
 * CLASE AUDITORIA Ruta * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaVehiculo {
    
    int id;
    int fk;    
    int estado;
    String usuario;        
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoPlaca;    
    String nuevoNumeroInterno;    
                
    String antiguoPlaca;    
    String antiguoNumeroInterno;    

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

    public String getNuevoPlaca() {
        return nuevoPlaca;
    }

    public void setNuevoPlaca(String nuevoPlaca) {
        this.nuevoPlaca = nuevoPlaca;
    }

    public String getNuevoNumeroInterno() {
        return nuevoNumeroInterno;
    }

    public void setNuevoNumeroInterno(String nuevoNumeroInterno) {
        this.nuevoNumeroInterno = nuevoNumeroInterno;
    }

    public String getAntiguoPlaca() {
        return antiguoPlaca;
    }

    public void setAntiguoPlaca(String antiguoPlaca) {
        this.antiguoPlaca = antiguoPlaca;
    }

    public String getAntiguoNumeroInterno() {
        return antiguoNumeroInterno;
    }

    public void setAntiguoNumeroInterno(String antiguoNumeroInterno) {
        this.antiguoNumeroInterno = antiguoNumeroInterno;
    }

    
    

    
            

    
}
