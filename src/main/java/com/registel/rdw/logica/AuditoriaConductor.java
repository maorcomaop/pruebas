/**
 * CLASE AUDITORIA CONDUCTOR
 * permite crear un objeto de tipo alarma para ser realizar operaciones en la base de datos.
 * Creada: 21/10/2016 9:47 AM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaConductor {
    
    int id;
    int fk;
    int tipo_doc;
    String usuario;
    int estado;
    String usuarioBD;
    Date fechaEvento;
    String nuevoNombre;    
    String nuevoTipoDocumento;    
    String nuevoApellido;    
    String nuevoCedula;        
    String antiguoNombre;    
    String antiguoApellido;    
    String antiguoCedula;    
    String antiguoTipoDocumento;    

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

    public int getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(int tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public String getNuevoTipoDocumento() {
        return nuevoTipoDocumento;
    }

    public void setNuevoTipoDocumento(String nuevoTipoDocumento) {
        this.nuevoTipoDocumento = nuevoTipoDocumento;
    }

    public String getNuevoApellido() {
        return nuevoApellido;
    }

    public void setNuevoApellido(String nuevoApellido) {
        this.nuevoApellido = nuevoApellido;
    }

    public String getNuevoCedula() {
        return nuevoCedula;
    }

    public void setNuevoCedula(String nuevoCedula) {
        this.nuevoCedula = nuevoCedula;
    }

    public String getAntiguoNombre() {
        return antiguoNombre;
    }

    public void setAntiguoNombre(String antiguoNombre) {
        this.antiguoNombre = antiguoNombre;
    }

    public String getAntiguoApellido() {
        return antiguoApellido;
    }

    public void setAntiguoApellido(String antiguoApellido) {
        this.antiguoApellido = antiguoApellido;
    }

    public String getAntiguoCedula() {
        return antiguoCedula;
    }

    public void setAntiguoCedula(String antiguoCedula) {
        this.antiguoCedula = antiguoCedula;
    }

    public String getAntiguoTipoDocumento() {
        return antiguoTipoDocumento;
    }

    public void setAntiguoTipoDocumento(String antiguoTipoDocumento) {
        this.antiguoTipoDocumento = antiguoTipoDocumento;
    }

    
    

    
}
