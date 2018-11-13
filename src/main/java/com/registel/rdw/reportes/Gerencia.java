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
public class Gerencia {
    
    Date fecha;
    Time hora;
    String placa;
    String numeroInterno;
    String nombreRuta;
    int cantidadVueltas;
    int cantidadVehiculos;
    int cantidadPasajeros;
    int promedioPasajeros;
    int cantidadAlarmas;
    int pasajerosDescontados;
    int pasajerosLiquidados;
    int valorLiquidado;
    
    int valorBruto;
    int valorDescuentoBruto;
    int valorDescuentoOperativo;
    int valorDescuentoAdministrativo;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
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

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public int getCantidadVueltas() {
        return cantidadVueltas;
    }

    public void setCantidadVueltas(int cantidadVueltas) {
        this.cantidadVueltas = cantidadVueltas;
    }

    public int getCantidadVehiculos() {
        return cantidadVehiculos;
    }

    public void setCantidadVehiculos(int cantidadVehiculos) {
        this.cantidadVehiculos = cantidadVehiculos;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public int getPromedioPasajeros() {
        return promedioPasajeros;
    }

    public void setPromedioPasajeros(int promedioPasajeros) {
        this.promedioPasajeros = promedioPasajeros;
    }

    public int getCantidadAlarmas() {
        return cantidadAlarmas;
    }

    public void setCantidadAlarmas(int cantidadAlarmas) {
        this.cantidadAlarmas = cantidadAlarmas;
    }

    public int getPasajerosDescontados() {
        return pasajerosDescontados;
    }

    public void setPasajerosDescontados(int pasajerosDescontados) {
        this.pasajerosDescontados = pasajerosDescontados;
    }
        
    public int getPasajerosLiquidados() {
        return pasajerosLiquidados;
    }

    public void setPasajerosLiquidados(int pasajerosLiquidados) {
        this.pasajerosLiquidados = pasajerosLiquidados;
    }

    public int getValorLiquidado() {
        return valorLiquidado;
    }

    public void setValorLiquidado(int valorLiquidado) {
        this.valorLiquidado = valorLiquidado;
    }

    public int getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(int valorBruto) {
        this.valorBruto = valorBruto;
    }

    public int getValorDescuentoBruto() {
        return valorDescuentoBruto;
    }

    public void setValorDescuentoBruto(int valorDescuentoBruto) {
        this.valorDescuentoBruto = valorDescuentoBruto;
    }
        
    public int getValorDescuentoOperativo() {
        return valorDescuentoOperativo;
    }

    public void setValorDescuentoOperativo(int valorDescuentoOperativo) {
        this.valorDescuentoOperativo = valorDescuentoOperativo;
    }

    public int getValorDescuentoAdministrativo() {
        return valorDescuentoAdministrativo;
    }

    public void setValorDescuentoAdministrativo(int valorDescuentoAdministrativo) {
        this.valorDescuentoAdministrativo = valorDescuentoAdministrativo;
    }    
}
