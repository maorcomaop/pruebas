
package com.registel.rdw.logica;

public class RevisionTMecanica {

    private int id;    
    private String codigo;
    private int fk_centro_diag;    
    private String observaciones;
    private String fecha_vencimiento;
    private String fecha_creacion;                           
    private int estado; 
    
    private String nombreCda;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getFk_centro_diag() {
        return fk_centro_diag;
    }

    public void setFk_centro_diag(int fk_centro_diag) {
        this.fk_centro_diag = fk_centro_diag;
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

    /**
     * @return the nombreCda
     */
    public String getNombreCda() {
        return nombreCda;
    }

    /**
     * @param nombreCda the nombreCda to set
     */
    public void setNombreCda(String nombreCda) {
        this.nombreCda = nombreCda;
    }

    
    
}
