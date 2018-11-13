/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 * @author Richard Mejia
 * 
 * @date 25/07/2018
 */
public class Notificacion extends EntidadConfigurable {
    
    private int idNotificacion;
    private String mensaje;
    private Date fecha;
    private String fechaCadena;
    private String nombreMantenimiento;
    private long fkMantenimiento;
    private int leida;
    
    private static int contador = 1;

    /**
     * @return the idNotificacion
     */
    public int getIdNotificacion() {
        return idNotificacion;
    }
    
    /**
     * 
     */
    public void setIdNotificacion() {
        
        if (idNotificacion == 0) {
            idNotificacion = contador++;
        }
    }
    
    /**
     * 
     */
    public void setIdNotificacion(int idNotificacionPantalla) {
        this.idNotificacion = idNotificacionPantalla;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the fechaCadena
     */
    public String getFechaCadena() {
        return fechaCadena;
    }

    /**
     * @param fechaCadena the fechaCadena to set
     */
    public void setFechaCadena(String fechaCadena) {
        this.fechaCadena = fechaCadena;
    }

    /**
     * @return the nombreMantenimiento
     */
    public String getNombreMantenimiento() {
        return nombreMantenimiento;
    }

    /**
     * @param nombreMantenimiento the nombreMantenimiento to set
     */
    public void setNombreMantenimiento(String nombreMantenimiento) {
        this.nombreMantenimiento = nombreMantenimiento;
    }

    /**
     * @return the fkMantenimiento
     */
    public long getFkMantenimiento() {
        return fkMantenimiento;
    }

    /**
     * @param fkMantenimiento the fkMantenimiento to set
     */
    public void setFkMantenimiento(long fkMantenimiento) {
        this.fkMantenimiento = fkMantenimiento;
    }

    /**
     * @return the leida
     */
    public int getLeida() {
        return leida;
    }

    /**
     * @param leida the leida to set
     */
    public void setLeida(int leida) {
        this.leida = leida;
    }

}
