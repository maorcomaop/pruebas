
package com.registel.rdw.logica;

public class RelationShipSimGps{
	

    private int id;
    private int fk_sim;    
    private int fk_vh;
    private String fk_gps;        
    private String numero_celular; 
    private String fecha_vencimiento;        
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_sim() {
        return fk_sim;
    }

    public void setFk_sim(int fk_sim) {
        this.fk_sim = fk_sim;
    }

    public int getFk_vh() {
        return fk_vh;
    }

    public void setFk_vh(int fk_vh) {
        this.fk_vh = fk_vh;
    }

    public String getFk_gps() {
        return fk_gps;
    }

    public void setFk_gps(String fk_gps) {
        this.fk_gps = fk_gps;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
    
    
    
}
