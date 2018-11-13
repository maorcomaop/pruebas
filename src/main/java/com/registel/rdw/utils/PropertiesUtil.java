/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author lider_desarrollador
 */
public class PropertiesUtil {    
    
    private Map<String, String> properties = null;    
    private String path = null;
    
    public PropertiesUtil (String p) {
        this.path = p;
    }
    
    // Carga propiedades desde archivo en estructura interna.
    public Map<String, String> cargarPropiedades () {        
        PropertiesBase propBase = new PropertiesBase(path);
        
        if (propBase.loadFile()) { 

            properties = new HashMap();

            Set<Map.Entry<Object, Object>> entrySet = propBase.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
                properties.put(entry.getKey() + "", entry.getValue() + "");
            }
            return properties;
        }             
        return null;
    }
    
    // Guarda propiedades internas en archivo.
    public boolean guardarPropiedades () {
        PropertiesBase propBase;
        
        if (properties != null) {
            Iterator hprop = properties.entrySet().iterator();
            
            String content = "\n";
            while (hprop.hasNext()) {
                Map.Entry hentry = (Map.Entry) hprop.next();
                content += hentry.getKey() + " = " + hentry.getValue();
                content += "\n";
            }
            
            propBase = new PropertiesBase(path);
            return propBase.saveFile(content);
        }
        return false;
    }
    
    // Guarda propiedades externas en achivo.
    public boolean guardarPropiedades (Map<String, String> properties) {
        PropertiesBase propBase;
        
        if (properties != null) {
            Iterator hprop = properties.entrySet().iterator();
            
            String content = "\n";
            while (hprop.hasNext()) {
                Map.Entry hentry = (Map.Entry) hprop.next();
                content += hentry.getKey() + " = " + hentry.getValue();
                content += "\n";
            }
            
            propBase = new PropertiesBase(path);
            return propBase.saveFile(content);
        }
        return false;
    }
    
    // Establece propiedad especificada en estructura interna.
    public void establecerPropiedad (String key, String value) {
        if (properties != null) {
            properties.put(key, value);
        }
    }
    
    // Carga propiedades desde archivo, establece/actualiza con
    // propiedades externas y almacena en archivo.
    public boolean establecerPropiedades (Map<String, String> hprop) {
        cargarPropiedades();
        
        Iterator hiterator = hprop.entrySet().iterator();
        while (hiterator.hasNext()) {
            Map.Entry hentry = (Map.Entry) hiterator.next();
            establecerPropiedad (hentry.getKey() + "", hentry.getValue() + "");
        }
        
        return guardarPropiedades();
    }
    
    // Obtiene propiedad desde estructura interna.
    public String obtenerPropiedad (String key) {
        if (properties != null) {
            return properties.get(key);
        }
        return null;
    }
}
