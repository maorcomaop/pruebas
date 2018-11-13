
package com.registel.rdw.logica;

import java.sql.Timestamp;
import java.util.Date;

public class ServicioLocal {
    private int id;
    private int fk_clave;
    private String clave;
    private int fk_cliente;
    private String cliente;
    private String num_doc;
    private String nom_cliente;
    private int fk_producto;
    private String producto;
    private int fk_tipo_clave;
    private int actualizado;
    private int suspendido;
    private String tipo_clave;
    private String fecha_creacion;
    private String fecha_actualizacion;
    private String fecha_proxima_actualizacion; 
    private String fecha_expiracion; 
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_clave() {
        return fk_clave;
    }

    public void setFk_clave(int fk_clave) {
        this.fk_clave = fk_clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(int fk_cliente) {
        this.fk_cliente = fk_cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNum_doc() {
        return num_doc;
    }

    public void setNum_doc(String num_doc) {
        this.num_doc = num_doc;
    }

    public String getNom_cliente() {
        return nom_cliente;
    }

    public void setNom_cliente(String nom_cliente) {
        this.nom_cliente = nom_cliente;
    }

    public int getFk_producto() {
        return fk_producto;
    }

    public void setFk_producto(int fk_producto) {
        this.fk_producto = fk_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getFk_tipo_clave() {
        return fk_tipo_clave;
    }

    public void setFk_tipo_clave(int fk_tipo_clave) {
        this.fk_tipo_clave = fk_tipo_clave;
    }

    public int getActualizado() {
        return actualizado;
    }

    public void setActualizado(int actualizado) {
        this.actualizado = actualizado;
    }

    public int getSuspendido() {
        return suspendido;
    }

    public void setSuspendido(int suspendido) {
        this.suspendido = suspendido;
    }

    public String getTipo_clave() {
        return tipo_clave;
    }

    public void setTipo_clave(String tipo_clave) {
        this.tipo_clave = tipo_clave;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getFecha_proxima_actualizacion() {
        return fecha_proxima_actualizacion;
    }

    public void setFecha_proxima_actualizacion(String fecha_proxima_actualizacion) {
        this.fecha_proxima_actualizacion = fecha_proxima_actualizacion;
    }

    public String getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
        

    
    
    
    
    
}

