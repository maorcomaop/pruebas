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
public class Tmpusr {
    
    // Conductor, propietario
    private String nombre;
    private String apellido;
    private String numdoc;
    private int codEmpresa;
    
    // Empresa
    private String nempresa;
    private String nit;

    private String tipousr;
    
    public Tmpusr () {
        nombre = "";
        apellido = "";
        numdoc = "";
        nempresa = "";
        nit = "";
        tipousr = "";
        codEmpresa = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public int getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        this.codEmpresa = codEmpresa;
    }
    
    public String getNempresa() {
        return nempresa;
    }

    public void setNempresa(String nempresa) {
        this.nempresa = nempresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTipousr() {
        return tipousr;
    }

    public void setTipousr(String tipousr) {
        this.tipousr = tipousr;
    }
    
}
