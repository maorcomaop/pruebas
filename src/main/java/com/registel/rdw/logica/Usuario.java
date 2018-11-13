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
public class Usuario {
        
    private String nombre;
    private String apellido;
    private String email;    
    private String numdoc;    
    private String login;
    private String pass;
    private String cpass;
    private String tipousr;
    private String usuariobd;
    private String perfilusuario;
    private String nombreEmpresa;
    private String nitEmpresa;
    private Empresa enterprice;
    private int id;
    private int estado;
    private int idempresa;
    private int idperfil;
    private int idtipodoc;

    // Para validacion de existencia de usuario
    private String a_numdoc;
    private String a_email;
    private String a_login;
    
    private String token;
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdtipodoc() {
        return idtipodoc;
    }

    public void setIdtipodoc(int idtipodoc) {
        this.idtipodoc = idtipodoc;
    }
    
    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getTipousr() {
        return tipousr;
    }

    public void setTipousr(String tipousr) {
        this.tipousr = tipousr;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }
    
    public int getIdperfil() {
        return idperfil;
    }

    public void setIdperfil(int idperfil) {
        this.idperfil = idperfil;
    }

    public String getA_numdoc() {
        return a_numdoc;
    }

    public void setA_numdoc(String a_numdoc) {
        this.a_numdoc = a_numdoc;
    }

    public String getA_email() {
        return a_email;
    }

    public void setA_email(String a_email) {
        this.a_email = a_email;
    }

    public String getA_login() {
        return a_login;
    }

    public void setA_login(String a_login) {
        this.a_login = a_login;
    }
    /*adicionado JAIR VIDAL*/

    public String getUsuariobd() {
        return usuariobd;
    }

    public void setUsuariobd(String usuariobd) {
        this.usuariobd = usuariobd;
    }

    public String getPerfilusuario() {
        return perfilusuario;
    }

    public void setPerfilusuario(String perfilusuario) {
        this.perfilusuario = perfilusuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Empresa getEnterprice() {
        return enterprice;
    }

    public void setEnterprice(Empresa enterprice) {
        this.enterprice = enterprice;
    }
    
    
    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }    
    
    // Comprueba si usuario es propietario a traves
    // de variable perfilusuario.
    public boolean esPropietario() {
        String nombre_perfil = this.perfilusuario;
        
        if (nombre_perfil != null) {
            nombre_perfil = nombre_perfil.toLowerCase();
            if (nombre_perfil.indexOf("propietario") >= 0) {
                return true;
            }
        }
        return false;
    }
}
