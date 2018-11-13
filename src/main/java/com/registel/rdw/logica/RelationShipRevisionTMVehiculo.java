
package com.registel.rdw.logica;

public class RelationShipRevisionTMVehiculo{
	

    private int id;
    private int fk_revision;
    private int fk_vh;
    private String fecha_vencimiento;
    private String fecha_creacion;
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_revision() {
        return fk_revision;
    }

    public void setFk_revision(int fk_revision) {
        this.fk_revision = fk_revision;
    }

    public int getFk_vh() {
        return fk_vh;
    }

    public void setFk_vh(int fk_vh) {
        this.fk_vh = fk_vh;
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
