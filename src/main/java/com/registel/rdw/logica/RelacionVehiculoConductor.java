/**
 * CLASE RELACION VEHICULO CONDUCTOR
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.io.Serializable;

/**
 *
 * @author lider_desarrollador
 */
public class RelacionVehiculoConductor implements Serializable{

    private int idRelacionVehiculoConductor;
    private int idVehiculo;
    private int idConductor;
    private String matriculaVehiculo;
    private String nombreConductor;
    private String numeroInterno;
    private int estado;
    private int activo;
    private String fecha_asignacion;

    public int getIdRelacionVehiculoConductor() {
        return idRelacionVehiculoConductor;
    }

    public void setIdRelacionVehiculoConductor(int idRelacionVehiculoConductor) {
        this.idRelacionVehiculoConductor = idRelacionVehiculoConductor;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

    
}
