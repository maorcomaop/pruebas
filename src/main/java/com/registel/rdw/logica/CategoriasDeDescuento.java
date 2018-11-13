/**
 * CLASE PERFIL
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.io.Serializable;

/**
 *
 * @author lider_desarrollador
 */
public class CategoriasDeDescuento implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private double valor;
    private int aplicaDescuento;
    private int aplicaGeneral;
    private int es_fija;
    private int tipoDeValor;
    private int es_valor_moneda;
    private int es_valor_porcentaje;
    private int descuenta_pasajeros;
    private int descuenta_del_total;
    private int estado;
    private String uniqueId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getAplicaDescuento() {
        return aplicaDescuento;
    }

    public void setAplicaDescuento(int aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    public int getAplicaGeneral() {
        return aplicaGeneral;
    }

    public void setAplicaGeneral(int aplicaGeneral) {
        this.aplicaGeneral = aplicaGeneral;
    }

    public int getEs_fija() {
        return es_fija;
    }

    public void setEs_fija(int es_fija) {
        this.es_fija = es_fija;
    }

    public int getTipoDeValor() {
        return tipoDeValor;
    }

    public void setTipoDeValor(int tipoDeValor) {
        this.tipoDeValor = tipoDeValor;
    }

    public int getEs_valor_moneda() {
        return es_valor_moneda;
    }

    public void setEs_valor_moneda(int es_valor_moneda) {
        this.es_valor_moneda = es_valor_moneda;
    }

    public int getEs_valor_porcentaje() {
        return es_valor_porcentaje;
    }

    public void setEs_valor_porcentaje(int es_valor_porcentaje) {
        this.es_valor_porcentaje = es_valor_porcentaje;
    }

    public int getDescuenta_pasajeros() {
        return descuenta_pasajeros;
    }

    public void setDescuenta_pasajeros(int descuenta_pasajeros) {
        this.descuenta_pasajeros = descuenta_pasajeros;
    }

    public int getDescuenta_del_total() {
        return descuenta_del_total;
    }

    public void setDescuenta_del_total(int descuenta_del_total) {
        this.descuenta_del_total = descuenta_del_total;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    

}
