/**
 * CLASE AUDITORIA Macro Ruta * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaPerfil {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombrePerfil;    
    String nuevoDescripcion;    
        
    String antiguoNombrePerfil;    
    String antiguoDescripcion;    

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

    public String getNuevoNombrePerfil() {
        return nuevoNombrePerfil;
    }

    public void setNuevoNombrePerfil(String nuevoNombrePerfil) {
        this.nuevoNombrePerfil = nuevoNombrePerfil;
    }

    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public String getAntiguoNombrePerfil() {
        return antiguoNombrePerfil;
    }

    public void setAntiguoNombrePerfil(String antiguoNombrePerfil) {
        this.antiguoNombrePerfil = antiguoNombrePerfil;
    }

    public String getAntiguoDescripcion() {
        return antiguoDescripcion;
    }

    public void setAntiguoDescripcion(String antiguoDescripcion) {
        this.antiguoDescripcion = antiguoDescripcion;
    }

    

    
            

    
}
