/**
 * CLASE AUDITORIA Ruta * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaRutaPunto {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoPromedioMinutos;    
    String nuevoHolguraMinutos;
            
    String antiguoPromedioMinutos;    
    String antiguoHolguraMinutos;

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

    public String getNuevoPromedioMinutos() {
        return nuevoPromedioMinutos;
    }

    public void setNuevoPromedioMinutos(String nuevoPromedioMinutos) {
        this.nuevoPromedioMinutos = nuevoPromedioMinutos;
    }

    public String getNuevoHolguraMinutos() {
        return nuevoHolguraMinutos;
    }

    public void setNuevoHolguraMinutos(String nuevoHolguraMinutos) {
        this.nuevoHolguraMinutos = nuevoHolguraMinutos;
    }

    public String getAntiguoPromedioMinutos() {
        return antiguoPromedioMinutos;
    }

    public void setAntiguoPromedioMinutos(String antiguoPromedioMinutos) {
        this.antiguoPromedioMinutos = antiguoPromedioMinutos;
    }

    public String getAntiguoHolguraMinutos() {
        return antiguoHolguraMinutos;
    }

    public void setAntiguoHolguraMinutos(String antiguoHolguraMinutos) {
        this.antiguoHolguraMinutos = antiguoHolguraMinutos;
    }

    
            

    
}
