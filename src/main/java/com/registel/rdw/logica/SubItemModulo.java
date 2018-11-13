/**
 * CLASE SUBITEMMODULO
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class SubItemModulo {
    
    int id;
    int fk_grupo;
    int fk_submodulo;
    String nombre;
    String codigoDeAcceso;
    String nombreLargo;
    String teclaAcceso;
    String ubicacion;
    String rutaImagen;
    String subGrupo;    
    int posicion;
    int posicionSubGrupo;
    int estado;
    int modificacion;
    String pk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_grupo() {
        return fk_grupo;
    }

    public void setFk_grupo(int fk_grupo) {
        this.fk_grupo = fk_grupo;
    }

    public int getFk_submodulo() {
        return fk_submodulo;
    }

    public void setFk_submodulo(int fk_submodulo) {
        this.fk_submodulo = fk_submodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoDeAcceso() {
        return codigoDeAcceso;
    }

    public void setCodigoDeAcceso(String codigoDeAcceso) {
        this.codigoDeAcceso = codigoDeAcceso;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getTeclaAcceso() {
        return teclaAcceso;
    }

    public void setTeclaAcceso(String teclaAcceso) {
        this.teclaAcceso = teclaAcceso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPosicionSubGrupo() {
        return posicionSubGrupo;
    }

    public void setPosicionSubGrupo(int posicionSubGrupo) {
        this.posicionSubGrupo = posicionSubGrupo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getModificacion() {
        return modificacion;
    }

    public void setModificacion(int modificacion) {
        this.modificacion = modificacion;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    
    
    
    
}
