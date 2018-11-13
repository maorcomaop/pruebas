/**
 * CLASE MODULO
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class Modulo {
    
    int id;
    String nombre;
    String teclaDeAcceso;
    String ruta_imagen;
    int posicion;
    int estado;       

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

    public String getTeclaDeAcceso() {
        return teclaDeAcceso;
    }

    public void setTeclaDeAcceso(String teclaDeAcceso) {
        this.teclaDeAcceso = teclaDeAcceso;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
}
