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
public class PuntoControl {

    private int id;
    private int idPunto;
    private int idInforeg;
    private Date HoraPuntoControl;
    private Date FechaPuntoControl;
    private long HoraPuntoControlSec;
    private int numeracion;
    private int entradas;
    private int salidas;
    private int estado;
    
    private int codigo;
    private String nombre;
    
    // Indentificadores GPS
    private long idPuntoControlGPS;
    
    private boolean seleccionado = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public int getIdInforeg() {
        return idInforeg;
    }

    public void setIdInforeg(int idInforeg) {
        this.idInforeg = idInforeg;
    }

    public Date getHoraPuntoControl() {
        return HoraPuntoControl;
    }

    public void setHoraPuntoControl(Date HoraPuntoControl) {
        this.HoraPuntoControl = HoraPuntoControl;
    }

    public Date getFechaPuntoControl() {
        return FechaPuntoControl;
    }

    public void setFechaPuntoControl(Date FechaPuntoControl) {
        this.FechaPuntoControl = FechaPuntoControl;
    }

    public long getHoraPuntoControlSec() {
        return HoraPuntoControlSec;
    }

    public void setHoraPuntoControlSec(long HoraPuntoControlSec) {
        this.HoraPuntoControlSec = HoraPuntoControlSec;
    }       

    public int getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(int numeracion) {
        this.numeracion = numeracion;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdPuntoControlGPS() {
        return idPuntoControlGPS;
    }

    public void setIdPuntoControlGPS(long idPuntoControlGPS) {
        this.idPuntoControlGPS = idPuntoControlGPS;
    }
    
    public boolean seleccionado() {
        return this.seleccionado;
    }
    
    public void seleccionar() {
        this.seleccionado = true;
    }
    
    public String toString() {
        String str = "\t| " +
            this.getIdPunto()            +" "+
            this.getFechaPuntoControl()  +" "+
            this.getHoraPuntoControl()   +" "+
            this.getNumeracion()         +" "+
            this.getEntradas()           +" "+
            this.getSalidas()  + " |";
        
        return str;
    }
}
