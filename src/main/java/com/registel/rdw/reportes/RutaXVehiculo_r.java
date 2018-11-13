/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.sql.Time;
import java.util.Collection;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author lider_desarrollador
 */
public class RutaXVehiculo_r {
    
    int numeroVuelta;
    Time horaSalida;
    Time horaLlegada;
    int numeracionInicial;
    int numeracionFinal;
    int cantidadPasajeros;
    String nombreRuta;
    
    Collection<RutaXVehiculo> puntos;
    
    JRDataSource dataSource;

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Time getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Time horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public int getNumeracionInicial() {
        return numeracionInicial;
    }

    public void setNumeracionInicial(int numeracionInicial) {
        this.numeracionInicial = numeracionInicial;
    }

    public int getNumeracionFinal() {
        return numeracionFinal;
    }

    public void setNumeracionFinal(int numeracionFinal) {
        this.numeracionFinal = numeracionFinal;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public Collection<RutaXVehiculo> getPuntos() {
        return puntos;
    }

    public void setPuntos(Collection<RutaXVehiculo> puntos) {
        this.puntos = puntos;
    }

    public JRDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }    
}
