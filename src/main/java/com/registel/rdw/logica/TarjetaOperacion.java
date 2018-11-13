
package com.registel.rdw.logica;

public class TarjetaOperacion {

    private int id;
    private int fk_centro_exp;
    private int tipo;
    private String codigo;    
    private String nombre_centro_exp;    
    private String modelo;
    private String observaciones;
    private String fecha_vencimiento;
    private String fecha_creacion;                           
    private int estado;         

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_centro_exp() {
        return fk_centro_exp;
    }

    public void setFk_centro_exp(int fk_centro_exp) {
        this.fk_centro_exp = fk_centro_exp;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre_centro_exp() {
        return nombre_centro_exp;
    }

    public void setNombre_centro_exp(String nombre_centro_exp) {
        this.nombre_centro_exp = nombre_centro_exp;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
    
}
