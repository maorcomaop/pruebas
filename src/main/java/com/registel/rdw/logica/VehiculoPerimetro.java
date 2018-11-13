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
public class VehiculoPerimetro {

    private int idVehiculoPerimetro;
    private String rutaAsignada;
    private Date horaSalida;
    private Date horaLlegada;
    private Short estado;
    private String nombreEstado;
    private Short modificacionLocal;
    private Date fechaModificacion;
    private Base base;
    private int idBase;
    //private Vehiculo vehiculo;
    private Movil vehiculo;
    private Integer idVehiculo;
    
    private String placa;
    private String numeroInterno;

    public int getIdVehiculoPerimetro() {
        return idVehiculoPerimetro;
    }

    public void setIdVehiculoPerimetro(int idVehiculoPerimetro) {
        this.idVehiculoPerimetro = idVehiculoPerimetro;
    }

    public String getRutaAsignada() {
        return rutaAsignada;
    }

    public void setRutaAsignada(String rutaAsignada) {
        this.rutaAsignada = rutaAsignada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Date getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Short getModificacionLocal() {
        return modificacionLocal;
    }

    public void setModificacionLocal(Short modificacionLocal) {
        this.modificacionLocal = modificacionLocal;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Movil getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Movil vehiculo) {
        this.vehiculo = vehiculo;
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

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
}
