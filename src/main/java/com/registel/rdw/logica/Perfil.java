/**
 * CLASE PERFIL 
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 19/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class Perfil {
    
    private int id;
    private String nombre;
    private String descripcion;    
    private int estado;    
    private String[] permisos;   
    private short asignarPermisos;

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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String[] getPermisos() {
        return permisos;
    }

    public void setPermisos(String[] permisos) {
        this.permisos = permisos;
    }

    public short getAsignarPermisos() {
        return asignarPermisos;
    }

    public void setAsignarPermisos(short asignarPermisos) {
        this.asignarPermisos = asignarPermisos;
    }


    
    
}
