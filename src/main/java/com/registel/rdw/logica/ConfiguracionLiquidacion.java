/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 * @author Richard Mejia
 * @entity tbl_configuracion_liquidacion
 * @date 01/10/2019
 * POJO que representa a la tabla tbl_configuracion_liquidacion
 */
public class ConfiguracionLiquidacion extends EntidadConfigurable {

    private String nombreConfiguracionLiquidacion;
    private String etqPasajeros1;
    private String etqPasajeros2;
    private String etqPasajeros3;
    private String etqPasajeros4;
    private String etqPasajeros5;
    private String etqTotal1;
    private String etqTotal2;
    private String etqTotal3;
    private String etqTotal4;
    private String etqTotal5;
    private String etqTotal6;
    private String etqTotal7;
    private String etqTotal8;
    private String etqRepPasajeros1;
    private String etqRepPasajeros2;
    private String etqRepPasajeros3;
    private String etqRepPasajeros4;
    private String etqRepPasajeros5;
    private String etqRepTotal1;
    private String etqRepTotal2;
    private String etqRepTotal3;
    private String etqRepTotal4;
    private String etqRepTotal5;
    private String etqRepTotal6;
    private String etqRepTotal7;
    private String etqRepTotal8;
    private boolean liquidacionNormal;
    private boolean liquidacionPorRuta;
    private boolean liquidacionPorTramo;
    private boolean liquidacionPorTiempo;
    private boolean liquidacionSoloPasajeros;
    private int fkPerfil; 
    private String nombrePerfil; 

    /**
     * @return the nombreConfiguracionLiquidacion
     */
    public String getNombreConfiguracionLiquidacion() {
        return nombreConfiguracionLiquidacion;
    }

    /**
     * @param nombreConfiguracionLiquidacion the nombreConfiguracionLiquidacion to set
     */
    public void setNombreConfiguracionLiquidacion(String nombreConfiguracionLiquidacion) {
        this.nombreConfiguracionLiquidacion = nombreConfiguracionLiquidacion;
    }

    /**
     * @return the etqPasajeros1
     */
    public String getEtqPasajeros1() {
        return etqPasajeros1;
    }

    /**
     * @param etqPasajeros1 the etqPasajeros1 to set
     */
    public void setEtqPasajeros1(String etqPasajeros1) {
        this.etqPasajeros1 = etqPasajeros1;
    }

    /**
     * @return the etqPasajeros2
     */
    public String getEtqPasajeros2() {
        return etqPasajeros2;
    }

    /**
     * @param etqPasajeros2 the etqPasajeros2 to set
     */
    public void setEtqPasajeros2(String etqPasajeros2) {
        this.etqPasajeros2 = etqPasajeros2;
    }

    /**
     * @return the etqPasajeros3
     */
    public String getEtqPasajeros3() {
        return etqPasajeros3;
    }

    /**
     * @param etqPasajeros3 the etqPasajeros3 to set
     */
    public void setEtqPasajeros3(String etqPasajeros3) {
        this.etqPasajeros3 = etqPasajeros3;
    }

    /**
     * @return the etqPasajeros4
     */
    public String getEtqPasajeros4() {
        return etqPasajeros4;
    }

    /**
     * @param etqPasajeros4 the etqPasajeros4 to set
     */
    public void setEtqPasajeros4(String etqPasajeros4) {
        this.etqPasajeros4 = etqPasajeros4;
    }

    /**
     * @return the etqPasajeros5
     */
    public String getEtqPasajeros5() {
        return etqPasajeros5;
    }

    /**
     * @param etqPasajeros5 the etqPasajeros5 to set
     */
    public void setEtqPasajeros5(String etqPasajeros5) {
        this.etqPasajeros5 = etqPasajeros5;
    }

    /**
     * @return the etqTotal1
     */
    public String getEtqTotal1() {
        return etqTotal1;
    }

    /**
     * @param etqTotal1 the etqTotal1 to set
     */
    public void setEtqTotal1(String etqTotal1) {
        this.etqTotal1 = etqTotal1;
    }

    /**
     * @return the etqTotal2
     */
    public String getEtqTotal2() {
        return etqTotal2;
    }

    /**
     * @param etqTotal2 the etqTotal2 to set
     */
    public void setEtqTotal2(String etqTotal2) {
        this.etqTotal2 = etqTotal2;
    }

    /**
     * @return the etqTotal3
     */
    public String getEtqTotal3() {
        return etqTotal3;
    }

    /**
     * @param etqTotal3 the etqTotal3 to set
     */
    public void setEtqTotal3(String etqTotal3) {
        this.etqTotal3 = etqTotal3;
    }

    /**
     * @return the etqTotal4
     */
    public String getEtqTotal4() {
        return etqTotal4;
    }

    /**
     * @param etqTotal4 the etqTotal4 to set
     */
    public void setEtqTotal4(String etqTotal4) {
        this.etqTotal4 = etqTotal4;
    }

    /**
     * @return the etqTotal5
     */
    public String getEtqTotal5() {
        return etqTotal5;
    }

    /**
     * @param etqTotal5 the etqTotal5 to set
     */
    public void setEtqTotal5(String etqTotal5) {
        this.etqTotal5 = etqTotal5;
    }

    /**
     * @return the etqTotal6
     */
    public String getEtqTotal6() {
        return etqTotal6;
    }

    /**
     * @param etqTotal6 the etqTotal6 to set
     */
    public void setEtqTotal6(String etqTotal6) {
        this.etqTotal6 = etqTotal6;
    }

    /**
     * @return the etqTotal7
     */
    public String getEtqTotal7() {
        return etqTotal7;
    }

    /**
     * @param etqTotal7 the etqTotal7 to set
     */
    public void setEtqTotal7(String etqTotal7) {
        this.etqTotal7 = etqTotal7;
    }

    /**
     * @return the etqTotal8
     */
    public String getEtqTotal8() {
        return etqTotal8;
    }

    /**
     * @param etqTotal8 the etqTotal8 to set
     */
    public void setEtqTotal8(String etqTotal8) {
        this.etqTotal8 = etqTotal8;
    }

    /**
     * @return the etqRepPasajeros1
     */
    public String getEtqRepPasajeros1() {
        return etqRepPasajeros1;
    }

    /**
     * @param etqRepPasajeros1 the etqRepPasajeros1 to set
     */
    public void setEtqRepPasajeros1(String etqRepPasajeros1) {
        this.etqRepPasajeros1 = etqRepPasajeros1;
    }

    /**
     * @return the etqRepPasajeros2
     */
    public String getEtqRepPasajeros2() {
        return etqRepPasajeros2;
    }

    /**
     * @param etqRepPasajeros2 the etqRepPasajeros2 to set
     */
    public void setEtqRepPasajeros2(String etqRepPasajeros2) {
        this.etqRepPasajeros2 = etqRepPasajeros2;
    }

    /**
     * @return the etqRepPasajeros3
     */
    public String getEtqRepPasajeros3() {
        return etqRepPasajeros3;
    }

    /**
     * @param etqRepPasajeros3 the etqRepPasajeros3 to set
     */
    public void setEtqRepPasajeros3(String etqRepPasajeros3) {
        this.etqRepPasajeros3 = etqRepPasajeros3;
    }

    /**
     * @return the etqRepPasajeros4
     */
    public String getEtqRepPasajeros4() {
        return etqRepPasajeros4;
    }

    /**
     * @param etqRepPasajeros4 the etqRepPasajeros4 to set
     */
    public void setEtqRepPasajeros4(String etqRepPasajeros4) {
        this.etqRepPasajeros4 = etqRepPasajeros4;
    }

    /**
     * @return the etqRepPasajeros5
     */
    public String getEtqRepPasajeros5() {
        return etqRepPasajeros5;
    }

    /**
     * @param etqRepPasajeros5 the etqRepPasajeros5 to set
     */
    public void setEtqRepPasajeros5(String etqRepPasajeros5) {
        this.etqRepPasajeros5 = etqRepPasajeros5;
    }

    /**
     * @return the etqRepTotal1
     */
    public String getEtqRepTotal1() {
        return etqRepTotal1;
    }

    /**
     * @param etqRepTotal1 the etqRepTotal1 to set
     */
    public void setEtqRepTotal1(String etqRepTotal1) {
        this.etqRepTotal1 = etqRepTotal1;
    }

    /**
     * @return the etqRepTotal2
     */
    public String getEtqRepTotal2() {
        return etqRepTotal2;
    }

    /**
     * @param etqRepTotal2 the etqRepTotal2 to set
     */
    public void setEtqRepTotal2(String etqRepTotal2) {
        this.etqRepTotal2 = etqRepTotal2;
    }

    /**
     * @return the etqRepTotal3
     */
    public String getEtqRepTotal3() {
        return etqRepTotal3;
    }

    /**
     * @param etqRepTotal3 the etqRepTotal3 to set
     */
    public void setEtqRepTotal3(String etqRepTotal3) {
        this.etqRepTotal3 = etqRepTotal3;
    }

    /**
     * @return the etqRepTotal4
     */
    public String getEtqRepTotal4() {
        return etqRepTotal4;
    }

    /**
     * @param etqRepTotal4 the etqRepTotal4 to set
     */
    public void setEtqRepTotal4(String etqRepTotal4) {
        this.etqRepTotal4 = etqRepTotal4;
    }

    /**
     * @return the etqRepTotal5
     */
    public String getEtqRepTotal5() {
        return etqRepTotal5;
    }

    /**
     * @param etqRepTotal5 the etqRepTotal5 to set
     */
    public void setEtqRepTotal5(String etqRepTotal5) {
        this.etqRepTotal5 = etqRepTotal5;
    }

    /**
     * @return the etqRepTotal6
     */
    public String getEtqRepTotal6() {
        return etqRepTotal6;
    }

    /**
     * @param etqRepTotal6 the etqRepTotal6 to set
     */
    public void setEtqRepTotal6(String etqRepTotal6) {
        this.etqRepTotal6 = etqRepTotal6;
    }

    /**
     * @return the etqRepTotal7
     */
    public String getEtqRepTotal7() {
        return etqRepTotal7;
    }

    /**
     * @param etqRepTotal7 the etqRepTotal7 to set
     */
    public void setEtqRepTotal7(String etqRepTotal7) {
        this.etqRepTotal7 = etqRepTotal7;
    }

    /**
     * @return the liquidacionNormal
     */
    public boolean isLiquidacionNormal() {
        return liquidacionNormal;
    }

    /**
     * @param liquidacionNormal the liquidacionNormal to set
     */
    public void setLiquidacionNormal(boolean liquidacionNormal) {
        this.liquidacionNormal = liquidacionNormal;
    }

    /**
     * @return the liquidacionPorRuta
     */
    public boolean isLiquidacionPorRuta() {
        return liquidacionPorRuta;
    }

    /**
     * @param liquidacionPorRuta the liquidacionPorRuta to set
     */
    public void setLiquidacionPorRuta(boolean liquidacionPorRuta) {
        this.liquidacionPorRuta = liquidacionPorRuta;
    }

    /**
     * @return the liquidacionPorTramo
     */
    public boolean isLiquidacionPorTramo() {
        return liquidacionPorTramo;
    }

    /**
     * @param liquidacionPorTramo the liquidacionPorTramo to set
     */
    public void setLiquidacionPorTramo(boolean liquidacionPorTramo) {
        this.liquidacionPorTramo = liquidacionPorTramo;
    }

    /**
     * @return the liquidacionPorTiempo
     */
    public boolean isLiquidacionPorTiempo() {
        return liquidacionPorTiempo;
    }

    /**
     * @param liquidacionPorTiempo the liquidacionPorTiempo to set
     */
    public void setLiquidacionPorTiempo(boolean liquidacionPorTiempo) {
        this.liquidacionPorTiempo = liquidacionPorTiempo;
    }

    /**
     * @return the liquidacionSoloPasajeros
     */
    public boolean isLiquidacionSoloPasajeros() {
        return liquidacionSoloPasajeros;
    }

    /**
     * @param liquidacionSoloPasajeros the liquidacionSoloPasajeros to set
     */
    public void setLiquidacionSoloPasajeros(boolean liquidacionSoloPasajeros) {
        this.liquidacionSoloPasajeros = liquidacionSoloPasajeros;
    }
    
    /**
     * @return the etqRepTotal8
     */
    public String getEtqRepTotal8() {
        return etqRepTotal8;
    }

    /**
     * @param etqRepTotal8 the etqRepTotal8 to set
     */
    public void setEtqRepTotal8(String etqRepTotal8) {
        this.etqRepTotal8 = etqRepTotal8;
    }

    /**
     * @return the fkPerfil
     */
    public int getFkPerfil() {
        return fkPerfil;
    }

    /**
     * @param fkPerfil the fkPerfil to set
     */
    public void setFkPerfil(int fkPerfil) {
        this.fkPerfil = fkPerfil;
    }

    /**
     * @return the nombrePerfil
     */
    public String getNombrePerfil() {
        return nombrePerfil;
    }

    /**
     * @param nombrePerfil the nombrePerfil to set
     */
    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
    
}
