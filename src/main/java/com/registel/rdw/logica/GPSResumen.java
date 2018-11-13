/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 *
 * @author ANDREI
 */
public class GPSResumen {

    private int pkGPSResumen;
    private String fkGPS;
    private int estadoConexion;
    private Date fechaUltimoReporte;
    private Date fechaDesconexion;
    private Date fechaDesenganche;

    public int getPkGPSResumen() {
        return pkGPSResumen;
    }

    public void setPkGPSResumen(int pkGPSResumen) {
        this.pkGPSResumen = pkGPSResumen;
    }

    public String getFkGPS() {
        return fkGPS;
    }

    public void setFkGPS(String fkGPS) {
        this.fkGPS = fkGPS;
    }

    public int getEstadoConexion() {
        return estadoConexion;
    }

    public void setEstadoConexion(int estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    public Date getFechaUltimoReporte() {
        return fechaUltimoReporte;
    }

    public void setFechaUltimoReporte(Date fechaUltimoReporte) {
        this.fechaUltimoReporte = fechaUltimoReporte;
    }

    public Date getFechaDesconexion() {
        return fechaDesconexion;
    }

    public void setFechaDesconexion(Date fechaDesconexion) {
        this.fechaDesconexion = fechaDesconexion;
    }

    public Date getFechaDesenganche() {
        return fechaDesenganche;
    }

    public void setFechaDesenganche(Date fechaDesenganche) {
        this.fechaDesenganche = fechaDesenganche;
    }
    
    

}
