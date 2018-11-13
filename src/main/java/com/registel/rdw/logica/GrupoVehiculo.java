/**
 * CLASE PERFIL 
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.util.ArrayList;

public class GrupoVehiculo {
    
    private int id;    
    private int idVehiculo;    
    private int idGrupo;    
    private int estado;
    private String placa;
    private String numInterno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumInterno() {
        return numInterno;
    }

    public void setNumInterno(String numInterno) {
        this.numInterno = numInterno;
    }

    
    
    

    
}
