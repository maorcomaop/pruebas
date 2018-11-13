/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class GrupoMovilDespacho {
    
    int id;
    int idGrupoMovil;
    int idMovil;
    String numeroInterno;
    String placa;
    boolean movilEnDespacho = false;
    boolean iniciaEnPuntoRetorno = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGrupoMovil() {
        return idGrupoMovil;
    }

    public void setIdGrupoMovil(int idGrupoMovil) {
        this.idGrupoMovil = idGrupoMovil;
    }

    public int getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(int idMovil) {
        this.idMovil = idMovil;
    }
    
    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isMovilEnDespacho() {
        return movilEnDespacho;
    }

    public void setMovilEnDespacho(boolean movilEnDespacho) {
        this.movilEnDespacho = movilEnDespacho;
    }    

    public boolean isIniciaEnPuntoRetorno() {
        return iniciaEnPuntoRetorno;
    }

    public void setIniciaEnPuntoRetorno(boolean iniciaEnPuntoRetorno) {
        this.iniciaEnPuntoRetorno = iniciaEnPuntoRetorno;
    }    
}
