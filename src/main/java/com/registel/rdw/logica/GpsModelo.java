
package com.registel.rdw.logica;

public class GpsModelo {

    private int id;
    private int fk_marca;
    private String nombre;
    private String descripcion;    
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_marca() {
        return fk_marca;
    }

    public void setFk_marca(int fk_marca) {
        this.fk_marca = fk_marca;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
}
