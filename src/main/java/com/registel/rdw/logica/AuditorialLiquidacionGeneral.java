/**
 * CLASE AUDITORIA Macro Ruta * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditorialLiquidacionGeneral {
    
    int id;
    int fk;
    int fk_vehiculo;
    int fk_conductor;
    int fk_tarifa_fija;
    int fk_usuario;
    int total_pasajeros_liquidados_nuevo;
    int total_pasajeros_liquidados_antiguo;
    int total_valor_vueltas_nuevo;
    int total_valor_vueltas_antiguo;
    int total_valor_descuento_pasajeros_nuevo;
    int total_valor_descuento_pasajeros_antiguo;
    int total_valor_descuento_adicional_nuevo;
    int total_valor_descuento_adicional_antiguo;    
    int estado_nuevo;    
    int estado_antiguo;
    Date fechaLiquidacion;    
    Date fechaEvento;
    String vehiculo;    
    String usuario;    
    String usuarioBD;
    String motivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk() {
        return fk;
    }

    public void setFk(int fk) {
        this.fk = fk;
    }

    public int getFk_vehiculo() {
        return fk_vehiculo;
    }

    public void setFk_vehiculo(int fk_vehiculo) {
        this.fk_vehiculo = fk_vehiculo;
    }

    public int getFk_conductor() {
        return fk_conductor;
    }

    public void setFk_conductor(int fk_conductor) {
        this.fk_conductor = fk_conductor;
    }

    public int getFk_tarifa_fija() {
        return fk_tarifa_fija;
    }

    public void setFk_tarifa_fija(int fk_tarifa_fija) {
        this.fk_tarifa_fija = fk_tarifa_fija;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getTotal_pasajeros_liquidados_nuevo() {
        return total_pasajeros_liquidados_nuevo;
    }

    public void setTotal_pasajeros_liquidados_nuevo(int total_pasajeros_liquidados_nuevo) {
        this.total_pasajeros_liquidados_nuevo = total_pasajeros_liquidados_nuevo;
    }

    public int getTotal_pasajeros_liquidados_antiguo() {
        return total_pasajeros_liquidados_antiguo;
    }

    public void setTotal_pasajeros_liquidados_antiguo(int total_pasajeros_liquidados_antiguo) {
        this.total_pasajeros_liquidados_antiguo = total_pasajeros_liquidados_antiguo;
    }

    public int getTotal_valor_vueltas_nuevo() {
        return total_valor_vueltas_nuevo;
    }

    public void setTotal_valor_vueltas_nuevo(int total_valor_vueltas_nuevo) {
        this.total_valor_vueltas_nuevo = total_valor_vueltas_nuevo;
    }

    public int getTotal_valor_vueltas_antiguo() {
        return total_valor_vueltas_antiguo;
    }

    public void setTotal_valor_vueltas_antiguo(int total_valor_vueltas_antiguo) {
        this.total_valor_vueltas_antiguo = total_valor_vueltas_antiguo;
    }

    public int getTotal_valor_descuento_pasajeros_nuevo() {
        return total_valor_descuento_pasajeros_nuevo;
    }

    public void setTotal_valor_descuento_pasajeros_nuevo(int total_valor_descuento_pasajeros_nuevo) {
        this.total_valor_descuento_pasajeros_nuevo = total_valor_descuento_pasajeros_nuevo;
    }

    public int getTotal_valor_descuento_pasajeros_antiguo() {
        return total_valor_descuento_pasajeros_antiguo;
    }

    public void setTotal_valor_descuento_pasajeros_antiguo(int total_valor_descuento_pasajeros_antiguo) {
        this.total_valor_descuento_pasajeros_antiguo = total_valor_descuento_pasajeros_antiguo;
    }

    public int getTotal_valor_descuento_adicional_nuevo() {
        return total_valor_descuento_adicional_nuevo;
    }

    public void setTotal_valor_descuento_adicional_nuevo(int total_valor_descuento_adicional_nuevo) {
        this.total_valor_descuento_adicional_nuevo = total_valor_descuento_adicional_nuevo;
    }

    public int getTotal_valor_descuento_adicional_antiguo() {
        return total_valor_descuento_adicional_antiguo;
    }

    public void setTotal_valor_descuento_adicional_antiguo(int total_valor_descuento_adicional_antiguo) {
        this.total_valor_descuento_adicional_antiguo = total_valor_descuento_adicional_antiguo;
    }

    public int getEstado_nuevo() {
        return estado_nuevo;
    }

    public void setEstado_nuevo(int estado_nuevo) {
        this.estado_nuevo = estado_nuevo;
    }

    public int getEstado_antiguo() {
        return estado_antiguo;
    }

    public void setEstado_antiguo(int estado_antiguo) {
        this.estado_antiguo = estado_antiguo;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioBD() {
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    


    
    

    
  
            

    
}
