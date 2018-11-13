/**
 * CLASE AUDITORIA Categorias
 * permite crear un objeto de tipo alarma para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuditoriaCategorias {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombre;    
    String nuevoDescripcion;    
    String nuevoAplicaDescuento;        
    String nuevoEsValorMoneda;
    String nuevoEsPocentaje;
    String nuevoEsFija;
    String nuevoValor;
    String nuevoAplicaGeneral;
    
    
    String antiguoNombre;    
    String antiguoDescripcion;    
    String antiguoAplicaDescuento;        
    String antiguoEsValorMoneda;
    String antiguoEsPocentaje;
    String antiguoEsFija;
    String antiguoValor;
    String antiguoAplicaGeneral;    
    Map<String, String> map = null;

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

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public String getNuevoAplicaDescuento() {
        return nuevoAplicaDescuento;
    }

    public void setNuevoAplicaDescuento(String nuevoAplicaDescuento) {
        this.nuevoAplicaDescuento = nuevoAplicaDescuento;
    }

    public String getNuevoEsValorMoneda() {
        return nuevoEsValorMoneda;
    }

    public void setNuevoEsValorMoneda(String nuevoEsValorMoneda) {
        this.nuevoEsValorMoneda = nuevoEsValorMoneda;
    }

    public String getNuevoEsPocentaje() {
        return nuevoEsPocentaje;
    }

    public void setNuevoEsPocentaje(String nuevoEsPocentaje) {
        this.nuevoEsPocentaje = nuevoEsPocentaje;
    }

    public String getNuevoEsFija() {
        return nuevoEsFija;
    }

    public void setNuevoEsFija(String nuevoEsFija) {
        this.nuevoEsFija = nuevoEsFija;
    }

    public String getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(String nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    public String getNuevoAplicaGeneral() {
        return nuevoAplicaGeneral;
    }

    public void setNuevoAplicaGeneral(String nuevoAplicaGeneral) {
        this.nuevoAplicaGeneral = nuevoAplicaGeneral;
    }

    public String getAntiguoNombre() {
        return antiguoNombre;
    }

    public void setAntiguoNombre(String antiguoNombre) {
        this.antiguoNombre = antiguoNombre;
    }

    public String getAntiguoDescripcion() {
        return antiguoDescripcion;
    }

    public void setAntiguoDescripcion(String antiguoDescripcion) {
        this.antiguoDescripcion = antiguoDescripcion;
    }

    public String getAntiguoAplicaDescuento() {
        return antiguoAplicaDescuento;
    }

    public void setAntiguoAplicaDescuento(String antiguoAplicaDescuento) {
        this.antiguoAplicaDescuento = antiguoAplicaDescuento;
    }

    public String getAntiguoEsValorMoneda() {
        return antiguoEsValorMoneda;
    }

    public void setAntiguoEsValorMoneda(String antiguoEsValorMoneda) {
        this.antiguoEsValorMoneda = antiguoEsValorMoneda;
    }

    public String getAntiguoEsPocentaje() {
        return antiguoEsPocentaje;
    }

    public void setAntiguoEsPocentaje(String antiguoEsPocentaje) {
        this.antiguoEsPocentaje = antiguoEsPocentaje;
    }

    public String getAntiguoEsFija() {
        return antiguoEsFija;
    }

    public void setAntiguoEsFija(String antiguoEsFija) {
        this.antiguoEsFija = antiguoEsFija;
    }

    public String getAntiguoValor() {
        return antiguoValor;
    }

    public void setAntiguoValor(String antiguoValor) {
        this.antiguoValor = antiguoValor;
    }

    public String getAntiguoAplicaGeneral() {
        return antiguoAplicaGeneral;
    }

    public void setAntiguoAplicaGeneral(String antiguoAplicaGeneral) {
        this.antiguoAplicaGeneral = antiguoAplicaGeneral;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    
    
    
    
    
    

    
}
