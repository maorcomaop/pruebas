
package com.registel.rdw.logica;

public class TarjetaSim {

    private int id;
    private String codigo;
    private String imei;    
    private int fk_operador;
    private String operador;        
    private String num_cel;        
    private int asociada;
    private int estado;

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getFk_operador() {
        return fk_operador;
    }

    public void setFk_operador(int fk_operador) {
        this.fk_operador = fk_operador;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getNum_cel() {
        return num_cel;
    }

    public void setNum_cel(String num_cel) {
        this.num_cel = num_cel;
    }

    public int getAsociada() {
        return asociada;
    }

    public void setAsociada(int asociada) {
        this.asociada = asociada;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
    
    
    
    
}
