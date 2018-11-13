
package com.registel.rdw.logica;

public class RelationShipHardwareGpsCar{

    private int id;
    private int fk_vehiculo;
    private int fk_hardware;
    private int fk_sim;
    private String fk_gps;        
    private int fk_operador;
    private String numero_celular;  
    private String fecha_instalacion;        
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_vehiculo() {
        return fk_vehiculo;
    }

    public void setFk_vehiculo(int fk_vehiculo) {
        this.fk_vehiculo = fk_vehiculo;
    }

    public int getFk_hardware() {
        return fk_hardware;
    }

    public void setFk_hardware(int fk_hardware) {
        this.fk_hardware = fk_hardware;
    }

    public int getFk_sim() {
        return fk_sim;
    }

    public void setFk_sim(int fk_sim) {
        this.fk_sim = fk_sim;
    }

    public String getFk_gps() {
        return fk_gps;
    }

    public void setFk_gps(String fk_gps) {
        this.fk_gps = fk_gps;
    }

    public int getFk_operador() {
        return fk_operador;
    }

    public void setFk_operador(int fk_operador) {
        this.fk_operador = fk_operador;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getFecha_instalacion() {
        return fecha_instalacion;
    }

    public void setFecha_instalacion(String fecha_instalacion) {
        this.fecha_instalacion = fecha_instalacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    
    
}
