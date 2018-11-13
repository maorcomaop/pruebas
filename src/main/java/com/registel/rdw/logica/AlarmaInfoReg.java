/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class AlarmaInfoReg {
    
    private int id;
    private int idAlarma;
    private int idInfreg;
    private Date fechaAlarma;
    private Date horaAlarma;
    private int cantidad;
    private int estado;
    
    private String nombre;
    private int codigo;    
    
    // Indentificadores GPS
    private long idAlarmaGPS;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public int getIdInfreg() {
        return idInfreg;
    }

    public void setIdInfreg(int idInfreg) {
        this.idInfreg = idInfreg;
    }

    public Date getFechaAlarma() {
        return fechaAlarma;
    }

    public void setFechaAlarma(Date fechaAlarma) {
        this.fechaAlarma = fechaAlarma;
    }

    public Date getHoraAlarma() {
        return horaAlarma;
    }

    public void setHoraAlarma(Date horaAlarma) {
        this.horaAlarma = horaAlarma;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public long getIdAlarmaGPS() {
        return idAlarmaGPS;
    }

    public void setIdAlarmaGPS(long idAlarmaGPS) {
        this.idAlarmaGPS = idAlarmaGPS;
    }        
    
    public String toString () {
        String str = "\t<" +
                this.getCodigo()        +" "+
                this.getIdAlarma()      +" "+
                this.getFechaAlarma()   +" "+
                this.getHoraAlarma()    +" "+
                this.getCantidad()  + " >";
        return str;                
    }
}
