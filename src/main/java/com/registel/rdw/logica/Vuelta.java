/**
 * CLASE VUELTA
 * permite
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class Vuelta implements Serializable {

    int id;
    int pk_empresa;
    int fk_vehiculo;
    int fk_ruta;
    int fk_usuario;
    int fk_base;
    int fk_conductor;
    int contador_vueltas;
    Date fecha_ingreso;
    Time hora_ingreso;
    int numero_vuelta;
    int numero_vuelta_ant;
    int numeracion_de_llegada;
    int diferencia_numeracion;
    int entradas;
    int diferencia_entradas;
    int salidas;
    int diferencia_salidas;
    int total_dia;
    int fk_base_salida;
    Date fecha_salida_base_salida;
    Time hora_salida_base_salida;
    int numeracion_base_salida;
    int entrada_base_salida;
    int salidas_base_salida;
    String firmware;
    int version_puntos_de_control;
    int estado_de_creacion;
    int historial;
    int estado;
    int modificacion_local;
    Date fecha_modificacion;
    double porcentaje_ruta;
    int diferencia;
    double distancia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPk_empresa() {
        return pk_empresa;
    }

    public void setPk_empresa(int pk_empresa) {
        this.pk_empresa = pk_empresa;
    }

    public int getFk_vehiculo() {
        return fk_vehiculo;
    }

    public void setFk_vehiculo(int fk_vehiculo) {
        this.fk_vehiculo = fk_vehiculo;
    }

    public int getFk_ruta() {
        return fk_ruta;
    }

    public void setFk_ruta(int fk_ruta) {
        this.fk_ruta = fk_ruta;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getFk_base() {
        return fk_base;
    }

    public void setFk_base(int fk_base) {
        this.fk_base = fk_base;
    }

    public int getFk_conductor() {
        return fk_conductor;
    }

    public void setFk_conductor(int fk_conductor) {
        this.fk_conductor = fk_conductor;
    }

    public int getContador_vueltas() {
        return contador_vueltas;
    }

    public void setContador_vueltas(int contador_vueltas) {
        this.contador_vueltas = contador_vueltas;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Time getHora_ingreso() {
        return hora_ingreso;
    }

    public void setHora_ingreso(Time hora_ingreso) {
        this.hora_ingreso = hora_ingreso;
    }

    public int getNumero_vuelta() {
        return numero_vuelta;
    }

    public void setNumero_vuelta(int numero_vuelta) {
        this.numero_vuelta = numero_vuelta;
    }

    public int getNumero_vuelta_ant() {
        return numero_vuelta_ant;
    }

    public void setNumero_vuelta_ant(int numero_vuelta_ant) {
        this.numero_vuelta_ant = numero_vuelta_ant;
    }

    public int getNumeracion_de_llegada() {
        return numeracion_de_llegada;
    }

    public void setNumeracion_de_llegada(int numeracion_de_llegada) {
        this.numeracion_de_llegada = numeracion_de_llegada;
    }

    public int getDiferencia_numeracion() {
        return diferencia_numeracion;
    }

    public void setDiferencia_numeracion(int diferencia_numeracion) {
        this.diferencia_numeracion = diferencia_numeracion;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getDiferencia_entradas() {
        return diferencia_entradas;
    }

    public void setDiferencia_entradas(int diferencia_entradas) {
        this.diferencia_entradas = diferencia_entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getDiferencia_salidas() {
        return diferencia_salidas;
    }

    public void setDiferencia_salidas(int diferencia_salidas) {
        this.diferencia_salidas = diferencia_salidas;
    }

    public int getTotal_dia() {
        return total_dia;
    }

    public void setTotal_dia(int total_dia) {
        this.total_dia = total_dia;
    }

    public int getFk_base_salida() {
        return fk_base_salida;
    }

    public void setFk_base_salida(int fk_base_salida) {
        this.fk_base_salida = fk_base_salida;
    }

    public Date getFecha_salida_base_salida() {
        return fecha_salida_base_salida;
    }

    public void setFecha_salida_base_salida(Date fecha_salida_base_salida) {
        this.fecha_salida_base_salida = fecha_salida_base_salida;
    }

    public Time getHora_salida_base_salida() {
        return hora_salida_base_salida;
    }

    public void setHora_salida_base_salida(Time hora_salida_base_salida) {
        this.hora_salida_base_salida = hora_salida_base_salida;
    }

    public int getNumeracion_base_salida() {
        return numeracion_base_salida;
    }

    public void setNumeracion_base_salida(int numeracion_base_salida) {
        this.numeracion_base_salida = numeracion_base_salida;
    }

    public int getEntrada_base_salida() {
        return entrada_base_salida;
    }

    public void setEntrada_base_salida(int entrada_base_salida) {
        this.entrada_base_salida = entrada_base_salida;
    }

    public int getSalidas_base_salida() {
        return salidas_base_salida;
    }

    public void setSalidas_base_salida(int salidas_base_salida) {
        this.salidas_base_salida = salidas_base_salida;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public int getVersion_puntos_de_control() {
        return version_puntos_de_control;
    }

    public void setVersion_puntos_de_control(int version_puntos_de_control) {
        this.version_puntos_de_control = version_puntos_de_control;
    }

    public int getEstado_de_creacion() {
        return estado_de_creacion;
    }

    public void setEstado_de_creacion(int estado_de_creacion) {
        this.estado_de_creacion = estado_de_creacion;
    }

    public int getHistorial() {
        return historial;
    }

    public void setHistorial(int historial) {
        this.historial = historial;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getModificacion_local() {
        return modificacion_local;
    }

    public void setModificacion_local(int modificacion_local) {
        this.modificacion_local = modificacion_local;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public double getPorcentaje_ruta() {
        return porcentaje_ruta;
    }

    public void setPorcentaje_ruta(double porcentaje_ruta) {
        this.porcentaje_ruta = porcentaje_ruta;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }


    
    

}
