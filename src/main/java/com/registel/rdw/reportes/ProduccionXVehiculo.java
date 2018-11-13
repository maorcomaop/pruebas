/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class ProduccionXVehiculo {
    
    Date fechaIngreso;
    Time horaIngreso;
    Time horaSalida;
    int numeroVuelta;
    int numeracionLlegada;
    int numeracionSalida;
    int entradas;
    int salidas;
    int totalDia;
    int diferenciaNumeracion;
    String nombreRuta;
    String nombreConductor;
    String apellidoConductor;
    
    String placa;
    String numeroInterno;
    int cantidadAlarma;
    int conteoPerimetro;

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Time getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Time horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }

    public int getNumeracionLlegada() {
        return numeracionLlegada;
    }

    public void setNumeracionLlegada(int numeracionLlegada) {
        this.numeracionLlegada = numeracionLlegada;
    }

    public int getNumeracionSalida() {
        return numeracionSalida;
    }

    public void setNumeracionSalida(int numeracionSalida) {
        this.numeracionSalida = numeracionSalida;
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

    public int getTotalDia() {
        return totalDia;
    }

    public void setTotalDia(int totalDia) {
        this.totalDia = totalDia;
    }

    public int getDiferenciaNumeracion() {
        return diferenciaNumeracion;
    }

    public void setDiferenciaNumeracion(int diferenciaNumeracion) {
        this.diferenciaNumeracion = diferenciaNumeracion;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getApellidoConductor() {
        return apellidoConductor;
    }

    public void setApellidoConductor(String apellidoConductor) {
        this.apellidoConductor = apellidoConductor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public int getCantidadAlarmas() {
        return cantidadAlarma;
    }

    public void setCantidadAlarmas(int cantidadAlarma) {
        this.cantidadAlarma = cantidadAlarma;
    }

    public int getConteoPerimetro() {
        return conteoPerimetro;
    }

    public void setConteoPerimetro(int conteoPerimetro) {
        this.conteoPerimetro = conteoPerimetro;
    }
}
