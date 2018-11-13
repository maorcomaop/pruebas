/**
 * CLASE AUDITORIA Informacion Registradora * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaInformacionRegistradora {
    
    int id;
    int fk;    
    int fk_usuario;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    String vehiculo;
    String nuevoNumeroVuelta;    
    String nuevoNumeroVueltaAnterior;    
    String nuevoNumeroLlegada;            
    String nuevoDiferenciaNumerica;            
    String nuevoEntradas;
    String nuevoDiferenciaEntrada;
    String nuevoSalidas;
    String nuevoDiferenciaSalida;
    String nuevoFkRuta;
    String nuevoNumeracionBaseSalida;
    String nuevoEntradasBaseSalida;
    String nuevoSalidasBaseSalida;
    String nuevoFechaIngreso;
    String nuevoHoraIngreso;
    String nuevoFechaSalida;
    String nuevoHoraSalida;
    String nuevoTotalDia;
    
    
    
    
    String antiguoNumeroVuelta;    
    String antiguoNumeroVueltaAnterior;    
    String antiguoNumeroLlegada;            
    String antiguoDiferenciaNumerica;            
    String antiguoEntradas;
    String antiguoDiferenciaEntrada;
    String antiguoSalidas;
    String antiguoDiferenciaSalida;
    String antiguoFkRuta;
    String antiguoNumeracionBaseSalida;
    String antiguoEntradasBaseSalida;
    String antiguoSalidasBaseSalida;
    String antiguoFechaIngreso;
    String antiguoHoraIngreso;
    String antiguoFechaSalida;
    String antiguoHoraSalida;
    String antiguoTotalDia;
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

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public String getNuevoNumeroVuelta() {
        return nuevoNumeroVuelta;
    }

    public void setNuevoNumeroVuelta(String nuevoNumeroVuelta) {
        this.nuevoNumeroVuelta = nuevoNumeroVuelta;
    }

    public String getNuevoNumeroVueltaAnterior() {
        return nuevoNumeroVueltaAnterior;
    }

    public void setNuevoNumeroVueltaAnterior(String nuevoNumeroVueltaAnterior) {
        this.nuevoNumeroVueltaAnterior = nuevoNumeroVueltaAnterior;
    }

    public String getNuevoNumeroLlegada() {
        return nuevoNumeroLlegada;
    }

    public void setNuevoNumeroLlegada(String nuevoNumeroLlegada) {
        this.nuevoNumeroLlegada = nuevoNumeroLlegada;
    }

    public String getNuevoDiferenciaNumerica() {
        return nuevoDiferenciaNumerica;
    }

    public void setNuevoDiferenciaNumerica(String nuevoDiferenciaNumerica) {
        this.nuevoDiferenciaNumerica = nuevoDiferenciaNumerica;
    }

    public String getNuevoEntradas() {
        return nuevoEntradas;
    }

    public void setNuevoEntradas(String nuevoEntradas) {
        this.nuevoEntradas = nuevoEntradas;
    }

    public String getNuevoDiferenciaEntrada() {
        return nuevoDiferenciaEntrada;
    }

    public void setNuevoDiferenciaEntrada(String nuevoDiferenciaEntrada) {
        this.nuevoDiferenciaEntrada = nuevoDiferenciaEntrada;
    }

    public String getNuevoSalidas() {
        return nuevoSalidas;
    }

    public void setNuevoSalidas(String nuevoSalidas) {
        this.nuevoSalidas = nuevoSalidas;
    }

    public String getNuevoDiferenciaSalida() {
        return nuevoDiferenciaSalida;
    }

    public void setNuevoDiferenciaSalida(String nuevoDiferenciaSalida) {
        this.nuevoDiferenciaSalida = nuevoDiferenciaSalida;
    }

    public String getNuevoFkRuta() {
        return nuevoFkRuta;
    }

    public void setNuevoFkRuta(String nuevoFkRuta) {
        this.nuevoFkRuta = nuevoFkRuta;
    }

    public String getNuevoNumeracionBaseSalida() {
        return nuevoNumeracionBaseSalida;
    }

    public void setNuevoNumeracionBaseSalida(String nuevoNumeracionBaseSalida) {
        this.nuevoNumeracionBaseSalida = nuevoNumeracionBaseSalida;
    }

    public String getNuevoEntradasBaseSalida() {
        return nuevoEntradasBaseSalida;
    }

    public void setNuevoEntradasBaseSalida(String nuevoEntradasBaseSalida) {
        this.nuevoEntradasBaseSalida = nuevoEntradasBaseSalida;
    }

    public String getNuevoSalidasBaseSalida() {
        return nuevoSalidasBaseSalida;
    }

    public void setNuevoSalidasBaseSalida(String nuevoSalidasBaseSalida) {
        this.nuevoSalidasBaseSalida = nuevoSalidasBaseSalida;
    }

    public String getNuevoFechaIngreso() {
        return nuevoFechaIngreso;
    }

    public void setNuevoFechaIngreso(String nuevoFechaIngreso) {
        this.nuevoFechaIngreso = nuevoFechaIngreso;
    }

    public String getNuevoHoraIngreso() {
        return nuevoHoraIngreso;
    }

    public void setNuevoHoraIngreso(String nuevoHoraIngreso) {
        this.nuevoHoraIngreso = nuevoHoraIngreso;
    }

    public String getNuevoFechaSalida() {
        return nuevoFechaSalida;
    }

    public void setNuevoFechaSalida(String nuevoFechaSalida) {
        this.nuevoFechaSalida = nuevoFechaSalida;
    }

    public String getNuevoHoraSalida() {
        return nuevoHoraSalida;
    }

    public void setNuevoHoraSalida(String nuevoHoraSalida) {
        this.nuevoHoraSalida = nuevoHoraSalida;
    }

    public String getNuevoTotalDia() {
        return nuevoTotalDia;
    }

    public void setNuevoTotalDia(String nuevoTotalDia) {
        this.nuevoTotalDia = nuevoTotalDia;
    }

    public String getAntiguoNumeroVuelta() {
        return antiguoNumeroVuelta;
    }

    public void setAntiguoNumeroVuelta(String antiguoNumeroVuelta) {
        this.antiguoNumeroVuelta = antiguoNumeroVuelta;
    }

    public String getAntiguoNumeroVueltaAnterior() {
        return antiguoNumeroVueltaAnterior;
    }

    public void setAntiguoNumeroVueltaAnterior(String antiguoNumeroVueltaAnterior) {
        this.antiguoNumeroVueltaAnterior = antiguoNumeroVueltaAnterior;
    }

    public String getAntiguoNumeroLlegada() {
        return antiguoNumeroLlegada;
    }

    public void setAntiguoNumeroLlegada(String antiguoNumeroLlegada) {
        this.antiguoNumeroLlegada = antiguoNumeroLlegada;
    }

    public String getAntiguoDiferenciaNumerica() {
        return antiguoDiferenciaNumerica;
    }

    public void setAntiguoDiferenciaNumerica(String antiguoDiferenciaNumerica) {
        this.antiguoDiferenciaNumerica = antiguoDiferenciaNumerica;
    }

    public String getAntiguoEntradas() {
        return antiguoEntradas;
    }

    public void setAntiguoEntradas(String antiguoEntradas) {
        this.antiguoEntradas = antiguoEntradas;
    }

    public String getAntiguoDiferenciaEntrada() {
        return antiguoDiferenciaEntrada;
    }

    public void setAntiguoDiferenciaEntrada(String antiguoDiferenciaEntrada) {
        this.antiguoDiferenciaEntrada = antiguoDiferenciaEntrada;
    }

    public String getAntiguoSalidas() {
        return antiguoSalidas;
    }

    public void setAntiguoSalidas(String antiguoSalidas) {
        this.antiguoSalidas = antiguoSalidas;
    }

    public String getAntiguoDiferenciaSalida() {
        return antiguoDiferenciaSalida;
    }

    public void setAntiguoDiferenciaSalida(String antiguoDiferenciaSalida) {
        this.antiguoDiferenciaSalida = antiguoDiferenciaSalida;
    }

    public String getAntiguoFkRuta() {
        return antiguoFkRuta;
    }

    public void setAntiguoFkRuta(String antiguoFkRuta) {
        this.antiguoFkRuta = antiguoFkRuta;
    }

    public String getAntiguoNumeracionBaseSalida() {
        return antiguoNumeracionBaseSalida;
    }

    public void setAntiguoNumeracionBaseSalida(String antiguoNumeracionBaseSalida) {
        this.antiguoNumeracionBaseSalida = antiguoNumeracionBaseSalida;
    }

    public String getAntiguoEntradasBaseSalida() {
        return antiguoEntradasBaseSalida;
    }

    public void setAntiguoEntradasBaseSalida(String antiguoEntradasBaseSalida) {
        this.antiguoEntradasBaseSalida = antiguoEntradasBaseSalida;
    }

    public String getAntiguoSalidasBaseSalida() {
        return antiguoSalidasBaseSalida;
    }

    public void setAntiguoSalidasBaseSalida(String antiguoSalidasBaseSalida) {
        this.antiguoSalidasBaseSalida = antiguoSalidasBaseSalida;
    }

    public String getAntiguoFechaIngreso() {
        return antiguoFechaIngreso;
    }

    public void setAntiguoFechaIngreso(String antiguoFechaIngreso) {
        this.antiguoFechaIngreso = antiguoFechaIngreso;
    }

    public String getAntiguoHoraIngreso() {
        return antiguoHoraIngreso;
    }

    public void setAntiguoHoraIngreso(String antiguoHoraIngreso) {
        this.antiguoHoraIngreso = antiguoHoraIngreso;
    }

    public String getAntiguoFechaSalida() {
        return antiguoFechaSalida;
    }

    public void setAntiguoFechaSalida(String antiguoFechaSalida) {
        this.antiguoFechaSalida = antiguoFechaSalida;
    }

    public String getAntiguoHoraSalida() {
        return antiguoHoraSalida;
    }

    public void setAntiguoHoraSalida(String antiguoHoraSalida) {
        this.antiguoHoraSalida = antiguoHoraSalida;
    }

    public String getAntiguoTotalDia() {
        return antiguoTotalDia;
    }

    public void setAntiguoTotalDia(String antiguoTotalDia) {
        this.antiguoTotalDia = antiguoTotalDia;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    


    

    
    
}
