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
public class PropiedadesServidor {
    
    String numeroMaximoConexionesServidor;
    String tiempoActivoServidor;
    String tiempoRetrasoServidor;
    String puertoServidor;
    String baseConfirmacion;
    String intentosActualizacionPuntos;
    String numeroMaximoConexionesServidorPerimetro;
    String puertoServidorPerimetro;
    String tiempoActivoServidorPerimetro;
    String tiempoRetrasoServidorPerimetro;
    String impresoraPredeterminada;
    
    // Inicia lectura de propiedades almacenadas en archivo,
    // que posteriormente son asignadas a variables de la clase.
    public PropiedadesServidor (String path) {
        PropertiesUtil propUtil = new PropertiesUtil(path);
        propUtil.cargarPropiedades();
        
        for (int i = 0; i < lstProperties.length; i++) {
            String value = propUtil.obtenerPropiedad(lstProperties[i]);
            setPropertie(i, value);
        }
    }

    public String getNumeroMaximoConexionesServidor() {
        return numeroMaximoConexionesServidor;
    }

    public void setNumeroMaximoConexionesServidor(String numeroMaximoConexionesServidor) {
        this.numeroMaximoConexionesServidor = numeroMaximoConexionesServidor;
    }

    public String getTiempoActivoServidor() {
        return tiempoActivoServidor;
    }

    public void setTiempoActivoServidor(String tiempoActivoServidor) {
        this.tiempoActivoServidor = tiempoActivoServidor;
    }

    public String getTiempoRetrasoServidor() {
        return tiempoRetrasoServidor;
    }

    public void setTiempoRetrasoServidor(String tiempoRetrasoServidor) {
        this.tiempoRetrasoServidor = tiempoRetrasoServidor;
    }

    public String getPuertoServidor() {
        return puertoServidor;
    }

    public void setPuertoServidor(String puertoServidor) {
        this.puertoServidor = puertoServidor;
    }

    public String getBaseConfirmacion() {
        return baseConfirmacion;
    }

    public void setBaseConfirmacion(String baseConfirmacion) {
        this.baseConfirmacion = baseConfirmacion;
    }

    public String getIntentosActualizacionPuntos() {
        return intentosActualizacionPuntos;
    }

    public void setIntentosActualizacionPuntos(String intentosActualizacionPuntos) {
        this.intentosActualizacionPuntos = intentosActualizacionPuntos;
    }

    public String getNumeroMaximoConexionesServidorPerimetro() {
        return numeroMaximoConexionesServidorPerimetro;
    }

    public void setNumeroMaximoConexionesServidorPerimetro(String numeroMaximoConexionesServidorPerimetro) {
        this.numeroMaximoConexionesServidorPerimetro = numeroMaximoConexionesServidorPerimetro;
    }

    public String getPuertoServidorPerimetro() {
        return puertoServidorPerimetro;
    }

    public void setPuertoServidorPerimetro(String puertoServidorPerimetro) {
        this.puertoServidorPerimetro = puertoServidorPerimetro;
    }

    public String getTiempoActivoServidorPerimetro() {
        return tiempoActivoServidorPerimetro;
    }

    public void setTiempoActivoServidorPerimetro(String tiempoActivoServidorPerimetro) {
        this.tiempoActivoServidorPerimetro = tiempoActivoServidorPerimetro;
    }

    public String getTiempoRetrasoServidorPerimetro() {
        return tiempoRetrasoServidorPerimetro;
    }

    public void setTiempoRetrasoServidorPerimetro(String tiempoRetrasoServidorPerimetro) {
        this.tiempoRetrasoServidorPerimetro = tiempoRetrasoServidorPerimetro;
    }   

    public String getImpresoraPredeterminada() {
        return impresoraPredeterminada;
    }

    public void setImpresoraPredeterminada(String impresoraPredeterminada) {
        this.impresoraPredeterminada = impresoraPredeterminada;
    }

    public String[] getLstProperties() {
        return lstProperties;
    }

    public void setLstProperties(String[] lstProperties) {
        this.lstProperties = lstProperties;
    }
    
    
    /*
        Advertencia: Los indices de cada constante en <lstProperties> se
        debe corresponder con su respectivo metodo de cada case: de
        estructura switch en setPropertie.
    */
    
    // Llama a metodo correspondiente para asignar valor
    // de variable, segun ubicacion de constante en array
    // lstProperties.
    public void setPropertie (int key, String value) {
        switch (key) {
            case 0: setNumeroMaximoConexionesServidor(value);   break;
            case 1: setPuertoServidor(value);                   break;
            case 2: setTiempoActivoServidor(value);             break;
            case 3: setTiempoRetrasoServidor(value);            break;
            case 4: setBaseConfirmacion(value);                 break;
            case 5: setIntentosActualizacionPuntos(value);      break;
            case 6: setNumeroMaximoConexionesServidorPerimetro(value); break;
            case 7: setPuertoServidorPerimetro(value);          break;
            case 8: setTiempoActivoServidorPerimetro(value);    break;
            case 9: setTiempoRetrasoServidorPerimetro(value);   break;
        }
    }
    
    String lstProperties[] = {
        Constant.NUM_MAX_CONEXIONES_SERV,
        Constant.PUERTO_SERV,
        Constant.TIEMPO_ACTIVO_SERV,
        Constant.TIEMPO_RETRASO_SERV,
        Constant.BASE_CONFIRMACION_DESCARTE,
        Constant.INTENTOS_ACTUALIZACION_PUNTOS,
        Constant.NUM_MAX_CONEXIONES_SERV_PERIMETRO,
        Constant.PUERTO_SERV_PERIMETRO,
        Constant.TIEMPO_ACTIVO_SERV_PERIMETRO,
        Constant.TIEMPO_RETRASO_SERV_PERIMETRO
    };
}
