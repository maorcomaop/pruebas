/**
 * CLASE AUDITORIA Punto * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaPuntoDeControl {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoHoraPuntoDeControl;    
    String nuevoFechaPuntoDeControl;    
    String nuevoNumeracion;    
    String nuevoEntradas;    
    String nuevoSalidas;    
         
        
    String antiguoHoraPuntoDeControl;    
    String antiguoFechaPuntoDeControl;    
    String antiguoNumeracion;    
    String antiguoEntradas;    
    String antiguoSalidas;    

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

    public String getNuevoHoraPuntoDeControl() {
        return nuevoHoraPuntoDeControl;
    }

    public void setNuevoHoraPuntoDeControl(String nuevoHoraPuntoDeControl) {
        this.nuevoHoraPuntoDeControl = nuevoHoraPuntoDeControl;
    }

    public String getNuevoFechaPuntoDeControl() {
        return nuevoFechaPuntoDeControl;
    }

    public void setNuevoFechaPuntoDeControl(String nuevoFechaPuntoDeControl) {
        this.nuevoFechaPuntoDeControl = nuevoFechaPuntoDeControl;
    }

    public String getNuevoNumeracion() {
        return nuevoNumeracion;
    }

    public void setNuevoNumeracion(String nuevoNumeracion) {
        this.nuevoNumeracion = nuevoNumeracion;
    }

    public String getNuevoEntradas() {
        return nuevoEntradas;
    }

    public void setNuevoEntradas(String nuevoEntradas) {
        this.nuevoEntradas = nuevoEntradas;
    }

    public String getNuevoSalidas() {
        return nuevoSalidas;
    }

    public void setNuevoSalidas(String nuevoSalidas) {
        this.nuevoSalidas = nuevoSalidas;
    }

    public String getAntiguoHoraPuntoDeControl() {
        return antiguoHoraPuntoDeControl;
    }

    public void setAntiguoHoraPuntoDeControl(String antiguoHoraPuntoDeControl) {
        this.antiguoHoraPuntoDeControl = antiguoHoraPuntoDeControl;
    }

    public String getAntiguoFechaPuntoDeControl() {
        return antiguoFechaPuntoDeControl;
    }

    public void setAntiguoFechaPuntoDeControl(String antiguoFechaPuntoDeControl) {
        this.antiguoFechaPuntoDeControl = antiguoFechaPuntoDeControl;
    }

    public String getAntiguoNumeracion() {
        return antiguoNumeracion;
    }

    public void setAntiguoNumeracion(String antiguoNumeracion) {
        this.antiguoNumeracion = antiguoNumeracion;
    }

    public String getAntiguoEntradas() {
        return antiguoEntradas;
    }

    public void setAntiguoEntradas(String antiguoEntradas) {
        this.antiguoEntradas = antiguoEntradas;
    }

    public String getAntiguoSalidas() {
        return antiguoSalidas;
    }

    public void setAntiguoSalidas(String antiguoSalidas) {
        this.antiguoSalidas = antiguoSalidas;
    }

    
            

    
}
