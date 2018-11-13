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
public class ProgramacionRuta {
        
    private int id;
    private int idRuta;
    private String nombreRuta;
    private String nombreProgramacion;
    private int grupo_lun;
    private int grupo_mar;
    private int grupo_mie;
    private int grupo_jue;
    private int grupo_vie;
    private int grupo_sab;
    private int grupo_dom;   
    
    private String ngrupo_lun;
    private String ngrupo_mar;
    private String ngrupo_mie;
    private String ngrupo_jue;
    private String ngrupo_vie;
    private String ngrupo_sab;
    private String ngrupo_dom;    
    
    private String grupo_lun_numinterno;
    private String grupo_mar_numinterno;
    private String grupo_mie_numinterno;
    private String grupo_jue_numinterno;
    private String grupo_vie_numinterno;
    private String grupo_sab_numinterno;
    private String grupo_dom_numinterno;
    
    private String grupo_lun_placa;
    private String grupo_mar_placa;
    private String grupo_mie_placa;
    private String grupo_jue_placa;
    private String grupo_vie_placa;
    private String grupo_sab_placa;
    private String grupo_dom_placa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }  
    
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getNombreProgramacion() {
        return nombreProgramacion;
    }

    public void setNombreProgramacion(String nombreProgramacion) {
        this.nombreProgramacion = nombreProgramacion;
    }
    
    public int getGrupo_lun() {
        return grupo_lun;
    }

    public void setGrupo_lun(int grupo_lun) {
        this.grupo_lun = grupo_lun;
    }

    public int getGrupo_mar() {
        return grupo_mar;
    }

    public void setGrupo_mar(int grupo_mar) {
        this.grupo_mar = grupo_mar;
    }

    public int getGrupo_mie() {
        return grupo_mie;
    }

    public void setGrupo_mie(int grupo_mie) {
        this.grupo_mie = grupo_mie;
    }

    public int getGrupo_jue() {
        return grupo_jue;
    }

    public void setGrupo_jue(int grupo_jue) {
        this.grupo_jue = grupo_jue;
    }

    public int getGrupo_vie() {
        return grupo_vie;
    }

    public void setGrupo_vie(int grupo_vie) {
        this.grupo_vie = grupo_vie;
    }

    public int getGrupo_sab() {
        return grupo_sab;
    }

    public void setGrupo_sab(int grupo_sab) {
        this.grupo_sab = grupo_sab;
    }

    public int getGrupo_dom() {
        return grupo_dom;
    }

    public void setGrupo_dom(int grupo_dom) {
        this.grupo_dom = grupo_dom;
    }       
    
    /* Valores numero interno */

    public String getGrupo_lun_numinterno() {
        return grupo_lun_numinterno;
    }

    public void setGrupo_lun_numinterno(String v) {
        this.grupo_lun_numinterno = v;
    }

    public String getGrupo_mar_numinterno() {
        return grupo_mar_numinterno;
    }

    public void setGrupo_mar_numinterno(String v) {
        this.grupo_mar_numinterno = v;
    }

    public String getGrupo_mie_numinterno() {
        return grupo_mie_numinterno;
    }

    public void setGrupo_mie_numinterno(String v) {
        this.grupo_mie_numinterno = v;
    }

    public String getGrupo_jue_numinterno() {
        return grupo_jue_numinterno;
    }

    public void setGrupo_jue_numinterno(String v) {
        this.grupo_jue_numinterno = v;
    }

    public String getGrupo_vie_numinterno() {
        return grupo_vie_numinterno;
    }

    public void setGrupo_vie_numinterno(String v) {
        this.grupo_vie_numinterno = v;
    }

    public String getGrupo_sab_numinterno() {
        return grupo_sab_numinterno;
    }

    public void setGrupo_sab_numinterno(String v) {
        this.grupo_sab_numinterno = v;
    }

    public String getGrupo_dom_numinterno() {
        return grupo_dom_numinterno;
    }

    public void setGrupo_dom_numinterno(String v) {
        this.grupo_dom_numinterno = v;
    }
    
    /* Valores placa */

    public String getGrupo_lun_placa() {
        return grupo_lun_placa;
    }

    public void setGrupo_lun_placa(String v) {
        this.grupo_lun_placa = v;
    }

    public String getGrupo_mar_placa() {
        return grupo_mar_placa;
    }

    public void setGrupo_mar_placa(String v) {
        this.grupo_mar_placa = v;
    }

    public String getGrupo_mie_placa() {
        return grupo_mie_placa;
    }

    public void setGrupo_mie_placa(String v) {
        this.grupo_mie_placa = v;
    }

    public String getGrupo_jue_placa() {
        return grupo_jue_placa;
    }

    public void setGrupo_jue_placa(String v) {
        this.grupo_jue_placa = v;
    }

    public String getGrupo_vie_placa() {
        return grupo_vie_placa;
    }

    public void setGrupo_vie_placa(String v) {
        this.grupo_vie_placa = v;
    }

    public String getGrupo_sab_placa() {
        return grupo_sab_placa;
    }

    public void setGrupo_sab_placa(String v) {
        this.grupo_sab_placa = v;
    }

    public String getGrupo_dom_placa() {
        return grupo_dom_placa;
    }

    public void setGrupo_dom_placa(String v) {
        this.grupo_dom_placa = v;
    }

    public String getNgrupo_lun() {
        return ngrupo_lun;
    }

    public void setNgrupo_lun(String v) {
        this.ngrupo_lun = v;
    }

    public String getNgrupo_mar() {
        return ngrupo_mar;
    }

    public void setNgrupo_mar(String v) {
        this.ngrupo_mar = v;
    }

    public String getNgrupo_mie() {
        return ngrupo_mie;
    }

    public void setNgrupo_mie(String v) {
        this.ngrupo_mie = v;
    }

    public String getNgrupo_jue() {
        return ngrupo_jue;
    }

    public void setNgrupo_jue(String v) {
        this.ngrupo_jue = v;
    }

    public String getNgrupo_vie() {
        return ngrupo_vie;
    }

    public void setNgrupo_vie(String v) {
        this.ngrupo_vie = v;
    }

    public String getNgrupo_sab() {
        return ngrupo_sab;
    }

    public void setNgrupo_sab(String v) {
        this.ngrupo_sab = v;
    }

    public String getNgrupo_dom() {
        return ngrupo_dom;
    }

    public void setNgrupo_dom(String v) {
        this.ngrupo_dom = v;
    }
}
