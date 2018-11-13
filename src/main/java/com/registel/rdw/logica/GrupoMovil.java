/**
 * CLASE PERFIL 
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author lider_desarrollador
 */
public class GrupoMovil {
    
    int id;    
    int fkGrupo;    
    int fkVehiculo;        
    int aplicaTiempos;
    int codEmpresa;
    int estado;
    String nombreGrupo;
    String nombreEmpresa;
    int [] fkRutas;
    ArrayList<Integer> fkVehiculos;
    ArrayList<VehiculoNuevoListado> fkVehiculosNuevoOrden;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkGrupo() {
        return fkGrupo;
    }

    public void setFkGrupo(int fkGrupo) {
        this.fkGrupo = fkGrupo;
    }

    public int getFkVehiculo() {
        return fkVehiculo;
    }

    public void setFkVehiculo(int fkVehiculo) {
        this.fkVehiculo = fkVehiculo;
    }

    public int getAplicaTiempos() {
        return aplicaTiempos;
    }

    public void setAplicaTiempos(int aplicaTiempos) {
        this.aplicaTiempos = aplicaTiempos;
    }

    public int getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public int[] getFkRutas() {
        return fkRutas;
    }

    public void setFkRutas(int[] fkRutas) {
        this.fkRutas = fkRutas;
    }

    public ArrayList<Integer> getFkVehiculos() {
        return fkVehiculos;
    }

    public void setFkVehiculos(ArrayList<Integer> fkVehiculos) {
        this.fkVehiculos = fkVehiculos;
    }

    public ArrayList<VehiculoNuevoListado> getFkVehiculosNuevoOrden() {
        return fkVehiculosNuevoOrden;
    }

    public void setFkVehiculosNuevoOrden(ArrayList<VehiculoNuevoListado> fkVehiculosNuevoOrden) {
        this.fkVehiculosNuevoOrden = fkVehiculosNuevoOrden;
    }

    
    
    

    

    
}
