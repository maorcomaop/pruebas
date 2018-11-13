/**
 * CLASE AUDITORIA*
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class Motivo {

    private int id;
    private int fkAuditoria;
    private String tablaAuditoria;
    private String descripcionMotivo;
    private String informacionAdicional;
    private int modificacionLocal;
    private int usuario;
    private String usuarioBD;
    private Date fechaModificacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkAuditoria() {
        return fkAuditoria;
    }

    public void setFkAuditoria(int fkAuditoria) {
        this.fkAuditoria = fkAuditoria;
    }

    public String getTablaAuditoria() {
        return tablaAuditoria;
    }

    public void setTablaAuditoria(String tablaAuditoria) {
        this.tablaAuditoria = tablaAuditoria;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public int getModificacionLocal() {
        return modificacionLocal;
    }

    public void setModificacionLocal(int modificacionLocal) {
        this.modificacionLocal = modificacionLocal;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioBD() {
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    

}
