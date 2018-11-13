/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.KeyUtil;
import com.registel.rdw.utils.PropertiesUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author lider_desarrollador
 */
public class Entorno {
    
    private Map<String, String> map = null;    
    private String file_path;

    /*
        Importante: A traves del llamado getServletContext().getRealPath("");
        se obtienen los sgtes resultados:
            Para windows : C:\Users\lider_desarrollador\Documents\NetBeansProjects\regisdata_web\build\web\
            Para linux   : /var/lib/tomcat7/webapps/RDW1
    */
    
    // Crea objeto Entorno e inicia carga de propiedades
    // especificadas en archivo entorno.properties ubicado
    // en directorio asignado a variable file_path.
    public Entorno (String web_path) {        
        
        // file_path = web_path + "entorno.properties";                
        
        if (SystemUtils.IS_OS_WINDOWS)
            file_path = web_path + "resources\\config\\entorno.properties";
        else
            file_path = web_path + "/resources/config/entorno.properties";
                
        cargarEntorno();
    }
    
    // Inicia carga de entorno, delegando lectura de archivo
    // y asignando valores obtenidos en tabla hash (variable map).
    public void cargarEntorno () {        
        if (map == null) {
            PropertiesUtil prop = new PropertiesUtil(file_path);
            map = prop.cargarPropiedades();
            decrypt_passwords();
            if (map == null)
                map = new HashMap();     
        }
    }
    
    // Inicia almacenamiento de propiedades registradas en tabla hash
    // (variable map). Se encriptan valores de contraseñas antes de
    // almacenarse.
    public boolean guardarEntorno () {        
        if (map != null) {            
            PropertiesUtil prop = new PropertiesUtil(file_path);
            encrypt_passwords();
            boolean rs = prop.guardarPropiedades(map);                        
            decrypt_passwords();
            return rs;
        }
        return false;
        
    }
    
    // Establece valor de propiedad (val) para propiedad (key)
    // en tabla hash (variable map).
    public void establecerPropiedad (String key, String val) {
        if (map != null) {
            map.put(key, val);
        }
    }
    
    // Retorna valor de propiedad para propiedad (key).    
    public String obtenerPropiedad (String key) {
        if (map != null) {
            return map.get(key);
        } else {
            cargarEntorno();
            if (map != null)
                return map.get(key);
            else
                return null;
        }
    }
    
    // Encripta valores de propiedades contraseña.
    public void encrypt_passwords() {
        if (map != null) {
            String pass = map.get(Constant.PASSWORD_CORPORATIVO);
            if (pass != null && pass != "") {
                map.put(Constant.PASSWORD_CORPORATIVO, KeyUtil.encrypt(pass));
            }
        }
    }
    
    // Desencripta valores de propiedades contraseña.
    public void decrypt_passwords() {
        if (map != null) {
            String enc_pass = map.get(Constant.PASSWORD_CORPORATIVO);
            if (enc_pass != null && enc_pass != "") {
                map.put(Constant.PASSWORD_CORPORATIVO, KeyUtil.decrypt(enc_pass));
            }
        }
    }
    
    // Retorna ubicacion de RD (Regisdata escritorio) segun 
    // propiedad <directorio_raiz> de entorno.properties 
    // (archivo administrado por RDW).
    public String getUbicacionRegisdata () {
        String dirRaiz = obtenerPropiedad(Constant.DIRECTORIO_RAIZ_RDTO);
        
        if (dirRaiz != null && dirRaiz != "") {
            if (SystemUtils.IS_OS_WINDOWS) {
                return Constant.DIR_RDTO_WIN + dirRaiz;
            } else {
                return Constant.DIR_RDTO_LINUX + dirRaiz; // Linux
            }
        }
        return null;
    }
    
    // Retorna ubicacion de system.properties segun 
    // ruta del directorio base de RD (Regisdata escritorio).
    public String getSystemPropertiesRD () {
        String baseApp = getUbicacionRegisdata();
        
        if (baseApp != null) {
            String subdir = "\\config\\system.properties";
            if (SystemUtils.IS_OS_WINDOWS) {
                return baseApp += subdir;
            } else {
                return baseApp += subdir.replace("\\", "/"); // Linux
            }
        }
        return null;
    }
    
    // Retorna ubicacion de perimetro.properties segun 
    // ruta del directorio base de RD (Regisdata escritorio).
    public String getPerimetroPropertiesRD () {
        String baseApp = getUbicacionRegisdata();
        
        if (baseApp != null) {
            String subdir = "\\config\\perimetro\\perimetro.properties";
            if (SystemUtils.IS_OS_WINDOWS) {
                return baseApp += subdir;
            } else {
                return baseApp += subdir.replace("\\", "/"); // Linux
            }
        }
        return null;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// Ejecucion de entorno RDTO (Escritorio)
    ////////////////////////////////////////////////////////////////////////////    
    
    private static String pathApp;
    private static String cmdFindApp;
    private static String cmdExecApp;
    private static String nameApp;
    private static boolean prepared = false;
    
    // Prepara e inicia ejecucion de entorno escritorio.
    public static boolean prepareAndRun (String path) {
        
        if (SystemUtils.IS_OS_WINDOWS) {            
            prepareWIN(path);
            return run();
        } else {      
            prepareLINUX(path);
            return run();
        }        
    }
    
    // Inicializa variables para busqueda de proceso y nombre de aplicacion
    // segun sistema operativo residente.
    public static void prepareFind () {
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdFindApp = System.getenv("windir") + "\\system32\\tasklist.exe";
            nameApp    = Constant.NAME_RDTO_WIN;
        } else {
            cmdFindApp = "ps -aux";
            nameApp    = Constant.NAME_RDTO_LINUX;
        }
    }
    
    // Inicializa variables de busqueda de proceso, ejecucion y nombre de
    // aplicacion para sistema operativo windows.
    public static void prepareWIN (String path) {
        cmdFindApp = System.getenv("windir") + "\\system32\\tasklist.exe";
        //cmdExecApp = path + "resources\\config\\rdto-start.bat";
        cmdExecApp = path + "\\rdto-start.bat";
        nameApp    = Constant.NAME_RDTO_WIN;                
    }
    
    // Inicializa variables de busqueda de proceso, ejecucion y nombre de
    // aplicacion para sistema operativo unix/linux.
    public static void prepareLINUX (String path) {        
        cmdFindApp = "ps -aux";
        //cmdExecApp = path + "resources/config/rdto-start.sh";
        cmdExecApp = path + "/rdto-start.sh";
        System.out.println("path> " + cmdExecApp);
        //cmdExecApp = "run_rdto";
        nameApp    = Constant.NAME_RDTO_LINUX;                
    }

    /*
    public static void prepareWIN (String path) {
        pathApp    = path + "\\" + Constant.NAME_RDTO_WIN;
        cmdFindApp = System.getenv("windir") + "\\system32\\tasklist.exe";
        cmdExecApp = pathApp;
        nameApp    = Constant.NAME_RDTO_WIN;                
    }
    
    public static void prepareLINUX (String path) {        
        pathApp    = path + "/" + Constant.NAME_RDTO_LINUX;
        cmdFindApp = "ps -aux";
        cmdExecApp = path + "/RDTO3.sh";
        nameApp    = Constant.NAME_RDTO_LINUX;                
    } */
    
    // Comprueba si aplicacion RD (Regisdata escritorio)
    // no se encuentra en ejecucion para iniciarlo.
    public static boolean run () {
        
        if (!isrun()) {
            return runapp();
        } return true;
    }
    
    // Comprueba si aplicacion RD (Regisdata escritorio)
    // se encuentra en ejecucion o no.
    public static boolean isrun () {
        
        String cmd = cmdFindApp;        
        BufferedReader input = null;
        try {
            String line;
            Process p = Runtime.getRuntime().exec(cmd);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()));            
            
            while ((line = input.readLine()) != null) {
                line = line.toLowerCase();
                //System.out.println("> " + line);
                if (line.contains(nameApp.toLowerCase())) {
                    System.out.println(">* " + line);
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
        return false;
    }
    
    // Comprueba si aplicacion RD (Regisdata escritorio)
    // se encuentra en ejecucion para variables de 
    // ejecucion y busqueda no establecidas.
    public static boolean isrun_np () {
        prepareFind(); 
        return isrun();
    }
    
    // Ejecuta aplicacion RD (Regisdata escritorio).
    public static boolean runapp () {
        
        String cmd = cmdExecApp;      
        System.out.println("> run_cmd: " + cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor(1000, TimeUnit.MILLISECONDS);
            if (isrun())
                return true;            
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return false;
    }

}
