/**
 * CLASE MOVIL
 * permite crear un objeto de tipo perfil para ser realizar operaciones en la base de datos.
 * Creada: 28/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class Movil { // Vehiculo

    private int id;
    private int fk_propietario;
    private String fkGps;
    private int fkEmpresa;
    private int fkHardware;
    private int fkMarcaGps;
    private String numero_celular;
    private String nombreEmpresa;
    private String nombrePropietario;
    private String placa;
    private String numeroInterno;
    private int capacidad;
    private int estado;
    private int liquidar_por_franjas;
    private Empresa empresaQuePertenece;
    private String fecha_inicio;
    private String fecha_fin;

    private String fecha_h_inicio;
    private String fecha_h_fin;
    private String hora_inicio;
    private String hora_fin;

    private String diaPicoplaca;
    private String diaDescanso;

    private Date fechaUltimoReporte;
    private Date fechaDesconexion;
    private int estadoConexion;

    private boolean iniciaEnPuntoRetorno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_propietario() {
        return fk_propietario;
    }

    public void setFk_propietario(int fk_propietario) {
        this.fk_propietario = fk_propietario;
    }

    public String getFkGps() {
        return fkGps;
    }

    public void setFkGps(String fkGps) {
        this.fkGps = fkGps;
    }

    public int getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(int fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public int getFkHardware() {
        return fkHardware;
    }

    public void setFkHardware(int fkHardware) {
        this.fkHardware = fkHardware;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

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

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getLiquidar_por_franjas() {
        return liquidar_por_franjas;
    }

    public void setLiquidar_por_franjas(int liquidar_por_franjas) {
        this.liquidar_por_franjas = liquidar_por_franjas;
    }

    public Empresa getEmpresaQuePertenece() {
        return empresaQuePertenece;
    }

    public void setEmpresaQuePertenece(Empresa empresaQuePertenece) {
        this.empresaQuePertenece = empresaQuePertenece;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getFecha_h_inicio() {
        return fecha_h_inicio;
    }

    public void setFecha_h_inicio(String fecha_h_inicio) {
        this.fecha_h_inicio = fecha_h_inicio;
    }

    public String getFecha_h_fin() {
        return fecha_h_fin;
    }

    public void setFecha_h_fin(String fecha_h_fin) {
        this.fecha_h_fin = fecha_h_fin;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getDiaPicoplaca() {
        return diaPicoplaca;
    }

    public void setDiaPicoplaca(String diaPicoplaca) {
        this.diaPicoplaca = diaPicoplaca;
    }

    public String getDiaDescanso() {
        return diaDescanso;
    }

    public void setDiaDescanso(String diaDescanso) {
        this.diaDescanso = diaDescanso;
    }

    public boolean isIniciaEnPuntoRetorno() {
        return iniciaEnPuntoRetorno;
    }

    public void setIniciaEnPuntoRetorno(boolean iniciaEnPuntoRetorno) {
        this.iniciaEnPuntoRetorno = iniciaEnPuntoRetorno;
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

    public int getEstadoConexion() {
        return estadoConexion;
    }

    public void setEstadoConexion(int estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    public int getFkMarcaGps() {
        return fkMarcaGps;
    }

    public void setFkMarcaGps(int fkMarcaGps) {
        this.fkMarcaGps = fkMarcaGps;
    }
}
