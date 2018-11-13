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
public class RendimientoConductor {
    
    private int id;
    private String nombre;
    private int incumplimientoRuta;
    private int excesoVelocidad;
    private double ipk;
    private double ipkEmpresa;
    private String ipk_str;
    private String ipkEmpresa_str;
    private int rendimiento;
    private int posicion;
    private int diasNoLaborados;

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

    public int getIncumplimientoRuta() {
        return incumplimientoRuta;
    }

    public void setIncumplimientoRuta(int incumplimientoRuta) {
        this.incumplimientoRuta = incumplimientoRuta;
    }

    public int getExcesoVelocidad() {
        return excesoVelocidad;
    }

    public void setExcesoVelocidad(int excesoVelocidad) {
        this.excesoVelocidad = excesoVelocidad;
    }            

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }        

    public double getIpkEmpresa() {
        return ipkEmpresa;
    }

    public void setIpkEmpresa(double ipkEmpresa) {
        this.ipkEmpresa = ipkEmpresa;
    }   

    public String getIpk_str() {
        return ipk_str;
    }

    public void setIpk_str(String ipk_str) {
        this.ipk_str = ipk_str;
    }

    public String getIpkEmpresa_str() {
        return ipkEmpresa_str;
    }

    public void setIpkEmpresa_str(String ipkEmpresa_str) {
        this.ipkEmpresa_str = ipkEmpresa_str;
    }

    public int getDiasNoLaborados() {
        return diasNoLaborados;
    }

    public void setDiasNoLaborados(int diasNoLaborados) {
        this.diasNoLaborados = diasNoLaborados;
    }
        
    public int getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(int rendimiento) {
        this.rendimiento = rendimiento;
    }                

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    public String toString() {
        String s = "\t";
        return  this.id + s +
                this.incumplimientoRuta + s +
                this.excesoVelocidad + s +
                this.ipk + s +
                this.ipkEmpresa;
    }
}
