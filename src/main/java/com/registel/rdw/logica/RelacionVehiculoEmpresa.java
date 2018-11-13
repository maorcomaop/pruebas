/**
 * CLASE RELACION VEHICULO EMPRESA 
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 22/11/2016 9:47 AM
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class RelacionVehiculoEmpresa {
    
    int idRelacionVehiculoEmpresa;
    int idVehiculo;
    int idEmpresa;
    String matriculaVehiculo;
    String nit;
    String razonSocial;
    int estado;

    public int getIdRelacionVehiculoEmpresa() {
        return idRelacionVehiculoEmpresa;
    }

    public void setIdRelacionVehiculoEmpresa(int idRelacionVehiculoEmpresa) {
        this.idRelacionVehiculoEmpresa = idRelacionVehiculoEmpresa;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
       

    

    
}
