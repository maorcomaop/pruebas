/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class DatosGPSMIN {
    
    long id;
    Time horaServidor;
    Date fechaServidor;
    Time horaGPS;
    Date fechaGPS;
    String msg;
    String placa;
    String nombreFlota;   
    boolean visto = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Time getHoraServidor() {
        return horaServidor;
    }

    public void setHoraServidor(Time horaServidor) {
        this.horaServidor = horaServidor;
    }

    public Date getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(Date fechaServidor) {
        this.fechaServidor = fechaServidor;
    }

    public Time getHoraGPS() {
        return horaGPS;
    }

    public void setHoraGPS(Time horaGPS) {
        this.horaGPS = horaGPS;
    }

    public Date getFechaGPS() {
        return fechaGPS;
    }

    public void setFechaGPS(Date fechaGPS) {
        this.fechaGPS = fechaGPS;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombreFlota() {
        return nombreFlota;
    }

    public void setNombreFlota(String nombreFlota) {
        this.nombreFlota = nombreFlota;
    }
    
    public boolean punto_visto() {
        return visto;
    }
    public void marcarPuntoVisto() {
        visto = true;
    }
    
    public String toString() {
        
        SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
        
        String fecha, hora; fecha = hora = "NA";            
        
        if (this.fechaGPS != null)
            fecha = ffmt.format(this.fechaGPS);
        if (this.horaGPS != null)
            hora = hfmt.format(this.horaGPS);
        
        String s = " ", t = "\t";
        String str = "Gps. " +
                fecha       + s +
                hora        + s +        
                this.placa  + s +
                this.msg;
        
        return str;                        
    }
}
