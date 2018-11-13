/**
 * CLASE LIQUIDACION
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class Liquidacion implements Serializable {

    int id;
    int estado;
    int fk_vehiculo;
    String placa;
    String numeroInterno;
    int fk_tarifa;
    double valorTarifa;
    int usuario;
    String nombreCompletoLiquidador;
    int fk_conductor;
    String nombreCompletoConductor;
    double totalPasajeros;
    double totalPasajerosLiquidados;
    double totalValorVueltas;    
    double totalValorDescuentoAlNeto;
    double subTotal;
    double totalRecibeConductor;
    double totalValorDescuentos;    
    double totalValorOtrosDescuentos;
    double totalLiquidacion;
    Date fechaLiquidacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getFk_vehiculo() {
        return fk_vehiculo;
    }

    public void setFk_vehiculo(int fk_vehiculo) {
        this.fk_vehiculo = fk_vehiculo;
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

    public int getFk_tarifa() {
        return fk_tarifa;
    }

    public void setFk_tarifa(int fk_tarifa) {
        this.fk_tarifa = fk_tarifa;
    }

    public double getValorTarifa() {
        return valorTarifa;
    }

    public void setValorTarifa(double valorTarifa) {
        this.valorTarifa = valorTarifa;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompletoLiquidador() {
        return nombreCompletoLiquidador;
    }

    public void setNombreCompletoLiquidador(String nombreCompletoLiquidador) {
        this.nombreCompletoLiquidador = nombreCompletoLiquidador;
    }

    public int getFk_conductor() {
        return fk_conductor;
    }

    public void setFk_conductor(int fk_conductor) {
        this.fk_conductor = fk_conductor;
    }

    public String getNombreCompletoConductor() {
        return nombreCompletoConductor;
    }

    public void setNombreCompletoConductor(String nombreCompletoConductor) {
        this.nombreCompletoConductor = nombreCompletoConductor;
    }

    public double getTotalPasajeros() {
        return totalPasajeros;
    }

    public void setTotalPasajeros(double totalPasajeros) {
        this.totalPasajeros = totalPasajeros;
    }

    public double getTotalPasajerosLiquidados() {
        return totalPasajerosLiquidados;
    }

    public void setTotalPasajerosLiquidados(double totalPasajerosLiquidados) {
        this.totalPasajerosLiquidados = totalPasajerosLiquidados;
    }

    public double getTotalValorVueltas() {
        return totalValorVueltas;
    }

    public void setTotalValorVueltas(double totalValorVueltas) {
        this.totalValorVueltas = totalValorVueltas;
    }

    public double getTotalValorDescuentos() {
        return totalValorDescuentos;
    }

    public void setTotalValorDescuentos(double totalValorDescuentos) {
        this.totalValorDescuentos = totalValorDescuentos;
    }

    public double getTotalValorOtrosDescuentos() {
        return totalValorOtrosDescuentos;
    }

    public void setTotalValorOtrosDescuentos(double totalValorOtrosDescuentos) {
        this.totalValorOtrosDescuentos = totalValorOtrosDescuentos;
    }

    public double getTotalLiquidacion() {
        return totalLiquidacion;
    }

    public void setTotalLiquidacion(double totalLiquidacion) {
        this.totalLiquidacion = totalLiquidacion;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public double getTotalValorDescuentoAlNeto() {
        return totalValorDescuentoAlNeto;
    }

    public void setTotalValorDescuentoAlNeto(double totalValorDescuentoAlNeto) {
        this.totalValorDescuentoAlNeto = totalValorDescuentoAlNeto;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotalRecibeConductor() {
        return totalRecibeConductor;
    }

    public void setTotalRecibeConductor(double totalRecibeConductor) {
        this.totalRecibeConductor = totalRecibeConductor;
    }

    
    
    
    

}
