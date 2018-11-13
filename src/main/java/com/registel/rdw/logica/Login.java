/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 *
 * @author lider_desarrollador
 */
public class Login {
    
    private String nombre;
    private String apellido;
    private String login;
    private String email;
    private String tipousr;
    private String idusr;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipousr() {
        return tipousr;
    }

    public void setTipousr(String tipo_usr) {
        this.tipousr = tipo_usr;
    }

    public String getIdusr() {
        return idusr;
    }

    public void setIdusr(String id_usr) {
        this.idusr = id_usr;
    }
    
}
