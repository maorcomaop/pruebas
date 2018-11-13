/**
 * CLASE AUDITORIA Ruta * 
 * Creada: 02/11/2016 16:20 PM
 */
package com.registel.rdw.logica;

import java.util.Date;

public class AuditoriaRuta {
    
    int id;
    int fk;
    int estado;
    String usuario;    
    String usuarioBD;
    Date fechaEvento;
    
    String nuevoNombreRuta;    
            
    String antiguoNombreRuta;    

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

    public String getNuevoNombreRuta() {
        return nuevoNombreRuta;
    }

    public void setNuevoNombreRuta(String nuevoNombreRuta) {
        this.nuevoNombreRuta = nuevoNombreRuta;
    }

    public String getAntiguoNombreRuta() {
        return antiguoNombreRuta;
    }

    public void setAntiguoNombreRuta(String antiguoNombreRuta) {
        this.antiguoNombreRuta = antiguoNombreRuta;
    }

    
    
        
            

    
}
