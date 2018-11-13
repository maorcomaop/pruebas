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
public class UserSession {
    
    private String user;
    private int id_empresa;
    private long time = 0;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdEmpresa() {
        return id_empresa;
    }

    public void setIdEmpresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }       
    
}
