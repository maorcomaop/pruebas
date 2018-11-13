/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
public class AccesoPerfil implements Serializable{
    
    private int id;
    private int fkPerfil;
    private int fkAcceso;
    private int estado;
    private int modificacionLocal;
    private Date fechaModificacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkPerfil() {
        return fkPerfil;
    }

    public void setFkPerfil(int fkPerfil) {
        this.fkPerfil = fkPerfil;
    }

    public int getFkAcceso() {
        return fkAcceso;
    }

    public void setFkAcceso(int fkAcceso) {
        this.fkAcceso = fkAcceso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getModificacionLocal() {
        return modificacionLocal;
    }

    public void setModificacionLocal(int modificacionLocal) {
        this.modificacionLocal = modificacionLocal;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
}
