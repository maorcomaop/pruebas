
package com.registel.rdw.logica;

public class RelationShipTarjetaOperacionVehiculo{
	
    private int id;
    private int fk_to;
    private int fk_vh;
    private int tipo_doc;
    private String fecha_vencimiento;
    private String fecha_creacion;
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_to() {
        return fk_to;
    }

    public void setFk_to(int fk_to) {
        this.fk_to = fk_to;
    }

    public int getFk_vh() {
        return fk_vh;
    }

    public void setFk_vh(int fk_vh) {
        this.fk_vh = fk_vh;
    }

    public int getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(int tipo_doc) {
        this.tipo_doc = tipo_doc;
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
