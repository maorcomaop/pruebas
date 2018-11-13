
package com.registel.rdw.reportes;

import java.util.Date;

public class LiquidacionConsulta {

    public LiquidacionConsulta() {
    }
   int pk_liquidacion;
   int fk_vehiculo;
   int fk_tarifa;
   int fk_conductor;
   int fk_usuario;
   String placa;
   String num_interno;
   String conductor;
   String liquidador;
   double valor_tarifa;
   double total_pasajeros_liquidados;
   double total_valor_descuento_al_neto;
   double total_valor_vueltas;
   double total_valor_descuentos;
   double total_valor_otros_descuentos;
   double subtotal;
   double total_liquidacion;
   int estado;
   Date fecha_liquidacion;

    public int getPk_liquidacion() {
        return pk_liquidacion;
    }

    public void setPk_liquidacion(int pk_liquidacion) {
        this.pk_liquidacion = pk_liquidacion;
    }

    public int getFk_vehiculo() {
        return fk_vehiculo;
    }

    public void setFk_vehiculo(int fk_vehiculo) {
        this.fk_vehiculo = fk_vehiculo;
    }

    public int getFk_tarifa() {
        return fk_tarifa;
    }

    public void setFk_tarifa(int fk_tarifa) {
        this.fk_tarifa = fk_tarifa;
    }

    public int getFk_conductor() {
        return fk_conductor;
    }

    public void setFk_conductor(int fk_conductor) {
        this.fk_conductor = fk_conductor;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNum_interno() {
        return num_interno;
    }

    public void setNum_interno(String num_interno) {
        this.num_interno = num_interno;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getLiquidador() {
        return liquidador;
    }

    public void setLiquidador(String liquidador) {
        this.liquidador = liquidador;
    }

    public double getValor_tarifa() {
        return valor_tarifa;
    }

    public void setValor_tarifa(double valor_tarifa) {
        this.valor_tarifa = valor_tarifa;
    }

    public double getTotal_pasajeros_liquidados() {
        return total_pasajeros_liquidados;
    }

    public void setTotal_pasajeros_liquidados(double total_pasajeros_liquidados) {
        this.total_pasajeros_liquidados = total_pasajeros_liquidados;
    }

    public double getTotal_valor_descuento_al_neto() {
        return total_valor_descuento_al_neto;
    }

    public void setTotal_valor_descuento_al_neto(double total_valor_descuento_al_neto) {
        this.total_valor_descuento_al_neto = total_valor_descuento_al_neto;
    }

    public double getTotal_valor_vueltas() {
        return total_valor_vueltas;
    }

    public void setTotal_valor_vueltas(double total_valor_vueltas) {
        this.total_valor_vueltas = total_valor_vueltas;
    }

    public double getTotal_valor_descuentos() {
        return total_valor_descuentos;
    }

    public void setTotal_valor_descuentos(double total_valor_descuentos) {
        this.total_valor_descuentos = total_valor_descuentos;
    }

    public double getTotal_valor_otros_descuentos() {
        return total_valor_otros_descuentos;
    }

    public void setTotal_valor_otros_descuentos(double total_valor_otros_descuentos) {
        this.total_valor_otros_descuentos = total_valor_otros_descuentos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal_liquidacion() {
        return total_liquidacion;
    }

    public void setTotal_liquidacion(double total_liquidacion) {
        this.total_liquidacion = total_liquidacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(Date fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }
   
    
}

