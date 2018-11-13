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
public class Empresa {
 
    private int id;
    private String nombre;
    private String nit;
    private String pais;
    private String dpto;
    private String ciudad;
    private int idpais;
    private int iddpto;
    private int idciudad;
    private String direccion;
    private String telefono;
    private String paginaweb;
    private String email;
    private int idmoneda;
    private int idzonahoraria;
    private int estado;
    private String longitudWeb;
    private String latitudWeb;
    private String simboloMoneda;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public int getIddpto() {
        return iddpto;
    }

    public void setIddpto(int iddpto) {
        this.iddpto = iddpto;
    }

    public int getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(int idciudad) {
        this.idciudad = idciudad;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaweb() {
        return paginaweb;
    }

    public void setPaginaweb(String paginaweb) {
        this.paginaweb = paginaweb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdmoneda() {
        return idmoneda;
    }

    public void setIdmoneda(int idmoneda) {
        this.idmoneda = idmoneda;
    }

    public int getIdzonahoraria() {
        return idzonahoraria;
    }

    public void setIdzonahoraria(int idzonahoraria) {
        this.idzonahoraria = idzonahoraria;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getLongitudWeb() {
        return longitudWeb;
    }

    public void setLongitudWeb(String longitudWeb) {
        this.longitudWeb = longitudWeb;
    }

    public String getLatitudWeb() {
        return latitudWeb;
    }

    public void setLatitudWeb(String latitudWeb) {
        this.latitudWeb = latitudWeb;
    }

    /**
     * @return the simboloMoneda
     */
    public String getSimboloMoneda() {
        return simboloMoneda;
    }

    /**
     * @param simboloMoneda the simboloMoneda to set
     */
    public void setSimboloMoneda(String simboloMoneda) {
        this.simboloMoneda = simboloMoneda;
    }
    
}
