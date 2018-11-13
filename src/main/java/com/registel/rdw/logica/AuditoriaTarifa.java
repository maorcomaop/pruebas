/**
 * CLASE AUDITORIA Ruta * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaTarifa {
    
    int id;    
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombreTarifa; 
    int nuevoTarifaActiva;
    double nuevoValorTarifa;
    
                
    String antiguoNombreTarifa;    
    int antiguoTarifaActiva;
    double antiguoValorTarifa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNuevoNombreTarifa() {
        return nuevoNombreTarifa;
    }

    public void setNuevoNombreTarifa(String nuevoNombreTarifa) {
        this.nuevoNombreTarifa = nuevoNombreTarifa;
    }

    public int getNuevoTarifaActiva() {
        return nuevoTarifaActiva;
    }

    public void setNuevoTarifaActiva(int nuevoTarifaActiva) {
        this.nuevoTarifaActiva = nuevoTarifaActiva;
    }

    public double getNuevoValorTarifa() {
        return nuevoValorTarifa;
    }

    public void setNuevoValorTarifa(double nuevoValorTarifa) {
        this.nuevoValorTarifa = nuevoValorTarifa;
    }

    public String getAntiguoNombreTarifa() {
        return antiguoNombreTarifa;
    }

    public void setAntiguoNombreTarifa(String antiguoNombreTarifa) {
        this.antiguoNombreTarifa = antiguoNombreTarifa;
    }

    public int getAntiguoTarifaActiva() {
        return antiguoTarifaActiva;
    }

    public void setAntiguoTarifaActiva(int antiguoTarifaActiva) {
        this.antiguoTarifaActiva = antiguoTarifaActiva;
    }

    public double getAntiguoValorTarifa() {
        return antiguoValorTarifa;
    }

    public void setAntiguoValorTarifa(double antiguoValorTarifa) {
        this.antiguoValorTarifa = antiguoValorTarifa;
    }
    

    
  
    
            

    
}
