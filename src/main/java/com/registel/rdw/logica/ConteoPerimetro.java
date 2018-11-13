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
public class ConteoPerimetro {
    
    int id;
    int idVehiculo;
    int idInfreg;
    Date fechaConteo;
    Date horaIngreso;
    int numInicial;
    int numFinal;
    int diferencia;
    int entradas;
    int salidas;
    int estado;
    
    // Indentificadores GPS
    long idConteoPerimetroGPS;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdInfreg() {
        return idInfreg;
    }

    public void setIdInfreg(int idInfreg) {
        this.idInfreg = idInfreg;
    }

    public Date getFechaConteo() {
        return fechaConteo;
    }

    public void setFechaConteo(Date fechaConteo) {
        this.fechaConteo = fechaConteo;
    }

    public Date getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Date horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public int getNumInicial() {
        return numInicial;
    }

    public void setNumInicial(int numInicial) {
        this.numInicial = numInicial;
    }

    public int getNumFinal() {
        return numFinal;
    }

    public void setNumFinal(int numFinal) {
        this.numFinal = numFinal;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
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

    public long getIdConteoPerimetroGPS() {
        return idConteoPerimetroGPS;
    }

    public void setIdConteoPerimetroGPS(long idConteoPerimetroGPS) {
        this.idConteoPerimetroGPS = idConteoPerimetroGPS;
    }

    public String toString() {
        String str = "\t% " +
            this.getIdVehiculo()    +" "+
            this.getFechaConteo()   +" "+
            this.getHoraIngreso()   +" "+ 
            this.getNumInicial()    +" "+
            this.getNumFinal()      +" "+
            this.getEntradas()      +" "+
            this.getSalidas() +" %";
        
        return str;
    }
}
