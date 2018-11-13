/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class VehiculoEstablecido {
    String placa;
    String numeroInterno;
    String nombreEmpresa;
    Date fechaUltimaVuelta;
    long diasUltimaVuelta;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Date getFechaUltimaVuelta() {
        return fechaUltimaVuelta;
    }

    public void setFechaUltimaVuelta(Date fechaUltimaVuelta) {
        this.fechaUltimaVuelta = fechaUltimaVuelta;
    }

    public long getDiasUltimaVuelta() {
        return diasUltimaVuelta;
    }

    public void setDiasUltimaVuelta(long diasUltimaVuelta) {
        this.diasUltimaVuelta = diasUltimaVuelta;
    }
    
}
