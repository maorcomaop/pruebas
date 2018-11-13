/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
/**
 *
 * @author lider_desarrollador
 */
public class PropertiesBase extends Properties {
        
    private String path = null;
    
    public PropertiesBase (String p) {
        this.path = p;
    }
    
    // Lee archivo de propiedades y carga informacion
    // en estructura de datos hash.
    public boolean loadFile () {
        FileInputStream fis = null;

        if (path == null)
            return false;
        
        try {
            File file = new File (path);
            
            if (file != null && file.exists()) {
                fis = new FileInputStream (path);
                clear();
                load(fis);
                fis.close(); return true;
            }
        } catch (Exception e) {
            System.err.println(e); 
            
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ie) {
                System.err.println(ie);
            }
        } 
        return false;
    }
        
    // Almacena en archivo propiedades establecidas.    
    public boolean saveFile (String content) {
        
        if (path == null)
            return false;
        
        try {
            File file = new File (path);
            
            if (file != null && !file.exists())
                file.createNewFile();
            
            if (file != null) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                fw.close(); return true;
            }            
        } catch (IOException e) {
            System.err.println(e); 
        } 
        return false;
    }
    
    // Obtiene valor de propiedad segun su identificador.
    public String getString (String name) {
        return super.getProperty(name);
    }
}