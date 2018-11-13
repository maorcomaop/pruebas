/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.PropertiesUtil;

/**
 *
 * @author lider_desarrollador
 */
public class PropiedadesPerimetro {
    
    private String descarteInicial;
    private String descarteFinal;
    private String descarteHolgura;
    private String comprobacion;
    private String compMin;
    private String compSeg;
    
    // Inicia lectura de propiedades almacenadas en archivo,
    // que posteriormente son asignadas a variables de la clase.
    public PropiedadesPerimetro (String path) {
        PropertiesUtil propUtil = new PropertiesUtil(path);
        propUtil.cargarPropiedades();
        
        for (int i = 0; i < lstProperties.length; i++) {
            String value = propUtil.obtenerPropiedad(lstProperties[i]);
            if (value !=  null)
                setPropertie(i, value);
        }
    }

    public String getDescarteInicial() {
        return descarteInicial;
    }

    public void setDescarteInicial(String descarteInicial) {
        this.descarteInicial = descarteInicial;
    }

    public String getDescarteFinal() {
        return descarteFinal;
    }

    public void setDescarteFinal(String descarteFinal) {
        this.descarteFinal = descarteFinal;
    }

    public String getDescarteHolgura() {
        return descarteHolgura;
    }

    public void setDescarteHolgura(String descarteHolgura) {
        this.descarteHolgura = descarteHolgura;
    }

    public String getComprobacion() {
        return comprobacion;
    }

    public void setComprobacion(String comprobacion) {
        this.comprobacion = comprobacion;
    }

    public String getCompMin() {
        return compMin;
    }

    public void setCompMin(String compMin) {
        this.compMin = compMin;
    }

    public String getCompSeg() {
        return compSeg;
    }

    public void setCompSeg(String compSeg) {
        this.compSeg = compSeg;
    }
    
    /*
        Advertencia: Los indices de cada constante en <lstProperties> se
        debe corresponder con su respectivo metodo de cada case: de
        estructura switch en setPropertie.
    */
    
    // Llama a metodo correspondiente para asignar valor
    // de variable, segun ubicacion de constante en array
    // lstProperties.
    public void setPropertie (int index, String value) {
        switch (index) {
            case 0: setDescarteInicial(value); break;
            case 1: setDescarteFinal(value); break;
            case 2: setDescarteHolgura(value); break;
            case 3: { 
                setComprobacion(value);
                int sectime = Integer.parseInt(value);
                int compMin = sectime / 60;
                int compSec = sectime % 60;
                setCompMin("" + compMin);
                setCompSeg("" + compSec);
            } break;
        }
    }
    
    private String lstProperties[] = {
        Constant.DESCARTE_INICIAL,
        Constant.DESCARTE_FINAL,
        Constant.DESCARTE_HOLGURA,
        Constant.COMPROBACION
    };
}
