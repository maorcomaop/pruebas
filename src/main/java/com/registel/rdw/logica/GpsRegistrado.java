
package com.registel.rdw.logica;

public class GpsRegistrado {

    private String id;    
    private String imei;        
    private int fk_marca;
    private String marca;
    private int fk_modelo;    
    private String modelo;
    private String celular;
    private String operador;
    private int asociado;
    private int fk_operador;
    private int sim_asociada;
    private int estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getFk_marca() {
        return fk_marca;
    }

    public void setFk_marca(int fk_marca) {
        this.fk_marca = fk_marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getFk_modelo() {
        return fk_modelo;
    }

    public void setFk_modelo(int fk_modelo) {
        this.fk_modelo = fk_modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAsociado() {
        return asociado;
    }

    public void setAsociado(int asociado) {
        this.asociado = asociado;
    }

    public int getSim_asociada() {
        return sim_asociada;
    }

    public void setSim_asociada(int sim_asociada) {
        this.sim_asociada = sim_asociada;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public int getFk_operador() {
        return fk_operador;
    }

    public void setFk_operador(int fk_operador) {
        this.fk_operador = fk_operador;
    }

    
    
        
    
    
    
}
