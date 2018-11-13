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
public class Acceso implements Serializable{
    
    private int id;
    private int fkAccesoPadre;
    private int fkGrupo;
    private String nombreAcceso;
    private int subGrupo;
    private int posicion;
    private int estado;
    private int modificacionLocal;
    private Date fechaModificacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkAccesoPadre() {
        return fkAccesoPadre;
    }

    public void setFkAccesoPadre(int fkAccesoPadre) {
        this.fkAccesoPadre = fkAccesoPadre;
    }

    public int getFkGrupo() {
        return fkGrupo;
    }

    public void setFkGrupo(int fkGrupo) {
        this.fkGrupo = fkGrupo;
    }

    public String getNombreAcceso() {
        return nombreAcceso;
    }

    public void setNombreAcceso(String nombreAcceso) {
        this.nombreAcceso = nombreAcceso;
    }

    public int getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(int subGrupo) {
        this.subGrupo = subGrupo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
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
