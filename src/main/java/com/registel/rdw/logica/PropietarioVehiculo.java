
package com.registel.rdw.logica;

public class PropietarioVehiculo {
private int id;
private int fk_propietario;
private int fk_vehiculo;
private String placaVehiculo;
private String numeroInterno;
private String propietario;
int [] vehiculos;
private int estado;
private int activa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_propietario() {
        return fk_propietario;
    }

    public void setFk_propietario(int fk_propietario) {
        this.fk_propietario = fk_propietario;
    }

    public int getFk_vehiculo() {
        return fk_vehiculo;
    }

    public void setFk_vehiculo(int fk_vehiculo) {
        this.fk_vehiculo = fk_vehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int[] getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(int[] vehiculos) {
        this.vehiculos = vehiculos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        this.activa = activa;
    }

    

    
    

}
