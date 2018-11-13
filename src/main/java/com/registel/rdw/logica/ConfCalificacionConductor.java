/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class ConfCalificacionConductor {
    
    private String nombre;
    private int puntajeMaximo;
    private int puntosPorVel;
    private int puntosPorRuta;
    private int puntosPorDiaNoLaborado;
    private int puntosPorDiaDescanco;
    private int puntosIpkMayor;
    private int puntosIpkMenor;
    private int velocidadMaxima;
    private int porcentajeRuta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(int puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public int getPuntosPorVel() {
        return puntosPorVel;
    }

    public void setPuntosPorVel(int puntosPorVel) {
        this.puntosPorVel = puntosPorVel;
    }

    public int getPuntosPorRuta() {
        return puntosPorRuta;
    }

    public void setPuntosPorRuta(int puntosPorRuta) {
        this.puntosPorRuta = puntosPorRuta;
    }

    public int getPuntosPorDiaNoLaborado() {
        return puntosPorDiaNoLaborado;
    }

    public void setPuntosPorDiaNoLaborado(int puntosPorDiaNoLaborado) {
        this.puntosPorDiaNoLaborado = puntosPorDiaNoLaborado;
    }

    public int getPuntosPorDiaDescanco() {
        return puntosPorDiaDescanco;
    }

    public void setPuntosPorDiaDescanco(int puntosPorDiaDescanco) {
        this.puntosPorDiaDescanco = puntosPorDiaDescanco;
    }

    public int getPuntosIpkMayor() {
        return puntosIpkMayor;
    }

    public void setPuntosIpkMayor(int puntosIpkMayor) {
        this.puntosIpkMayor = puntosIpkMayor;
    }

    public int getPuntosIpkMenor() {
        return puntosIpkMenor;
    }

    public void setPuntosIpkMenor(int puntosIpkMenor) {
        this.puntosIpkMenor = puntosIpkMenor;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(int velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public int getPorcentajeRuta() {
        return porcentajeRuta;
    }

    public void setPorcentajeRuta(int porcentajeRuta) {
        this.porcentajeRuta = porcentajeRuta;
    }                
}
