/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class AccesoAccesoPerfil implements Serializable {

    private int pkAccesoPerfil;
    private int fkPerfil;
    private int pkAcceso;
    private int fkAccesoPadre;
    private int fkAccesoTipo;
    private int fkGrupo;
    private int fkModulo;
    private int subGrupo;
    private int posicion;
    private int maxSubGrupo;
    private int fkPerfilUsuarioLogin;
    private String nombreAcceso;
    private String aliasAcceso;
    private List<AccesoAccesoPerfil> subGrupos = new ArrayList<>();

    
    
    public int getPkAccesoPerfil() {
        return pkAccesoPerfil;
    }

    public void setPkAccesoPerfil(int pkAccesoPerfil) {
        this.pkAccesoPerfil = pkAccesoPerfil;
    }

    public int getFkPerfil() {
        return fkPerfil;
    }

    public void setFkPerfil(int fkPerfil) {
        this.fkPerfil = fkPerfil;
    }

    public int getPkAcceso() {
        return pkAcceso;
    }

    public void setPkAcceso(int pkAcceso) {
        this.pkAcceso = pkAcceso;
    }

    public int getFkAccesoPadre() {
        return fkAccesoPadre;
    }

    public void setFkAccesoPadre(int fkAccesoPadre) {
        this.fkAccesoPadre = fkAccesoPadre;
    }

    public int getFkGrupo() {
        return fkGrupo;
    }

    public void setFkGrupo(int fkGrupo) {
        this.fkGrupo = fkGrupo;
    }

    public int getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(int subGrupo) {
        this.subGrupo = subGrupo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombreAcceso() {
        return nombreAcceso;
    }

    public void setNombreAcceso(String nombreAcceso) {
        this.nombreAcceso = nombreAcceso;
    }

    public List<AccesoAccesoPerfil> getSubGrupos() {
        return subGrupos;
    }

    public void setSubGrupos(ArrayList<AccesoAccesoPerfil> subGrupos) {
        this.subGrupos = subGrupos;
    }

    public int getMaxSubGrupo() {
        return maxSubGrupo;
    }

    public void setMaxSubGrupo(int maxSubGrupo) {
        this.maxSubGrupo = maxSubGrupo;
    }

    public String getAliasAcceso() {
        return aliasAcceso;
    }

    public void setAliasAcceso(String aliasAcceso) {
        this.aliasAcceso = aliasAcceso;
    }

    public int getFkAccesoTipo() {
        return fkAccesoTipo;
    }

    public void setFkAccesoTipo(int fkAccesoTipo) {
        this.fkAccesoTipo = fkAccesoTipo;
    }

    public int getFkModulo() {
        return fkModulo;
    }

    public void setFkModulo(int fkModulo) {
        this.fkModulo = fkModulo;
    }

    public int getFkPerfilUsuarioLogin() {
        return fkPerfilUsuarioLogin;
    }

    public void setFkPerfilUsuarioLogin(int fkPerfilUsuarioLogin) {
        this.fkPerfilUsuarioLogin = fkPerfilUsuarioLogin;
    }

}
