/**
 * CLASE AUDITORIA Punto * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaPunto {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombrePunto;    
    String nuevoDescripcion;    
    String nuevoLatitud;    
    String nuevoLongitud;    
      
        
    String antiguoNombrePunto;    
    String antiguoDescripcion;    
    String antiguoLatitud;    
    String antiguoLongitud;    

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

    public String getNuevoNombrePunto() {
        return nuevoNombrePunto;
    }

    public void setNuevoNombrePunto(String nuevoNombrePunto) {
        this.nuevoNombrePunto = nuevoNombrePunto;
    }

    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public String getNuevoLatitud() {
        return nuevoLatitud;
    }

    public void setNuevoLatitud(String nuevoLatitud) {
        this.nuevoLatitud = nuevoLatitud;
    }

    public String getNuevoLongitud() {
        return nuevoLongitud;
    }

    public void setNuevoLongitud(String nuevoLongitud) {
        this.nuevoLongitud = nuevoLongitud;
    }

    public String getAntiguoNombrePunto() {
        return antiguoNombrePunto;
    }

    public void setAntiguoNombrePunto(String antiguoNombrePunto) {
        this.antiguoNombrePunto = antiguoNombrePunto;
    }

    public String getAntiguoDescripcion() {
        return antiguoDescripcion;
    }

    public void setAntiguoDescripcion(String antiguoDescripcion) {
        this.antiguoDescripcion = antiguoDescripcion;
    }

    public String getAntiguoLatitud() {
        return antiguoLatitud;
    }

    public void setAntiguoLatitud(String antiguoLatitud) {
        this.antiguoLatitud = antiguoLatitud;
    }

    public String getAntiguoLongitud() {
        return antiguoLongitud;
    }

    public void setAntiguoLongitud(String antiguoLongitud) {
        this.antiguoLongitud = antiguoLongitud;
    }

    
    
            

    
}
