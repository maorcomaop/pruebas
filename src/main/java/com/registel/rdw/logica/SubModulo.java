/**
 * CLASE SUBMODULO
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class SubModulo {
    
    int id;
    int fk_modulo;
    String nombre;
    String descripcion;        
    int estado;        

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_modulo() {
        return fk_modulo;
    }

    public void setFk_modulo(int fk_modulo) {
        this.fk_modulo = fk_modulo;
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
