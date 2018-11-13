/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author lider_desarrollador
 */
public class WebContext {
    
    private static Map<String,String> hparam;
    
    public static void addParam(String param, String val) {
        if (hparam == null) {
            hparam = new HashMap<String,String>();
        }
        hparam.put(param, val);
    }
    
    public static String getParam(String param) {
        if (hparam != null)
            return hparam.get(param);
        return null;
    }    
}
