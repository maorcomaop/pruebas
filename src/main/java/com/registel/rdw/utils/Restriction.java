/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.util.UUID;

/**
 *
 * @author lider_desarrollador
 */
public class Restriction {
    
    private static final String EMAIL_LOGIN_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    // Es numero entero Ej. 12/-12 (sin signo +)
    public static boolean isNumber (String s) {
        if (s != null && s.matches("^-?[0-9]+$"))
            return true;
        return false;
    }
    
    // Es numero entero positivo Ej. 12 (sin signo +)
    public static boolean isNumberPositive (String s) {
        if (s != null && s.matches("^[0-9]+$"))
            return true;
        return false;
    }
    
    // Es alfabetico
    public static boolean isAlpha (String s) {
        if (s != null && s.matches("^[a-zA-Z]+$"))
            return true;
        return false;
    }
    
    // Es alfanumerico
    public static boolean isAlphanumeric (String s) {
        if (s != null && s.matches("^[a-zA-Z0-9]+$"))
            return true;
        return false;
    }
    
    // Es alfabetico con espacios
    public static boolean isAlpha_esp (String s) {
        if (s != null && s.matches("^([a-zA-Z]+\\s*)+$"))
            return true;
        return false;
    }
    
    // Es alfanumerico con espacios
    public static boolean isAlphanumeric_esp (String s) {
        if (s != null && s.matches("^([a-zA-Z0-9]+\\s*)+$"))
            return true;
        return false;
    }
    
    // Es fecha en formato yyyy-mm-dd
    public static boolean isDate (String s) {
        if (s != null && s.matches("^[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2}$"))
            return true;
        return false;
    }
    
    // Es fecha-tiempo en formato yyyy-mm-dd hh:mm:ss
    public static boolean isDateTime (String s) {
        if (s != null && s.matches("^[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2}\\s{1,1}[0-9]{2,2}:[0-9]{2,2}:[0-9]{2,2}$"))
            return true;
        return false;
    }
    
    // Es tiempo en formato hh:mm
    public static boolean isTime (String s) {
        if (s != null && s.matches("^[0-9]{2,2}:[0-9]{2,2}$"))
            return true;
        return false;
    }
    
    // Es tiempo-completo en formato hh:mm:ss
    public static boolean isFullTime (String s) {
        if (s != null && s.matches("^[0-9]{2,2}:[0-9]{2,2}:[0-9]{2,2}$"))
            return true;
        return false;
    }
    
    // Es expresion regular r
    public static boolean isThis (String s, String r) {
        if (s != null && r != null && s.matches(r))
            return true;
        return false;
    }
    
    // Es nombre de punto alfanumerico mas simbolos -.# con espacios
    public static boolean isNamePoint (String s) {
        if (s != null && s.matches("^([a-zA-Z0-9-\\.#]+\\s*)+$"))
            return true;
        return false;
    }
    
    // Es numero flotante
    public static boolean isFloatNumber (String s) {
        if (s != null && s.matches("^([0-9]*\\.)?[0-9]+$"))
            return true;
        return false;
    }
    
    // Es numero flotante negativo
    public static boolean isFloatNumber_neg (String s) {
        if (s != null && s.matches("^(-?[0-9]*\\.)?[0-9]+$"))
            return true;
        return false;
    }
    
    // Nombre de archivo, no se permiten caracteres \/:*?"<>|
    public static boolean isNameFile (String s) {
        if (s != null && s.matches("^([a-zA-Z0-9ñ_\\-\\.]+\\s*)+$"))
            return true;
        return false;
    }
    
    // Tiene tamaño size
    public static boolean size (String s, int size) {
        if (s != null && s.length() == size)
            return true;
        return false;
    }

    // Es alfanumerico con simbolo _ con longitud de 4-20
    public static boolean isLogin (String s) {
        if (s != null && s.matches("^\\w{4,20}$"))
            return true;
        return false;
    }
        
    // Es alfanumerico con simbolos !#$%&@._ de longitud 8-20
    public static boolean isPass (String s) {
        if (s != null && s.matches("^[\\w!#$%&@\\.]{8,20}$"))
            return true;
        return false;
    }
        
    // Concuerda con expresion regular para correo
    public static boolean isEmail (String s) {
        if (s != null && s.matches(EMAIL_PATTERN))
            return true;
        return false;
    }
    
    // Concuerda con expresion regular para correo o cadena alfanumerica
    // con simbolos ._-
    public static boolean isEmailLogin (String s) {
        if (s != null && (s.matches(EMAIL_LOGIN_PATTERN) || s.matches(EMAIL_PATTERN)))
            return true;
        return false;
    }
    
    // Es valor de estado 0 o 1
    public static boolean isEstado (int e) {
        if (e == 0 || e == 1)
            return true;
        return false;
    }
    
    // Es token permitido (valor alfanumerico con simbolo -)
    public static boolean isToken (String s) {
        if (s != null && s.matches("^[a-zA-Z0-9-]+$"))
            return true;
        return false;
    }
    
    // Es placa permitida (valor alfanumerico)
    public static boolean isPlaca (String s) {
        //if (s != null && s.matches("^[a-zA-Z]{3}[0-9]{3}$"))
        if (s != null && s.matches("^[0-9a-zA-Z]+$"))
            return true;
        return false;
    }
    
    // Es nombre de dominio de la forma nombreDominio.subnombreDominio.dominio
    public static boolean isNameDomain (String s) {
        if (s != null && s.matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{0,61}[a-zA-Z0-9]{0,1}\\.([a-zA-Z]{1,6}|[a-zA-Z0-9-]{1,30}\\.[a-zA-Z]{2,3})$"))
            return true;
        return false;        
    }

    // Es numero de IP/Puerto de la forma ###.###.###.###:##### || localhost:#####
    public static boolean isHost_IPPort (String s) {
        if (s != null & s.matches("^([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}|localhost)(:[0-9]{1,5})*$"))
            return true;
        return false;
    }
    
    // Es lista numerica separada por (,)
    public static boolean isNumericListSeparateByComma(String s) {
        if (s != null & s.matches("^[0-9]+(,[0-9]+)+$"))
            return true;
        return false;
    }
    
    // Es lista alfanumerica separada por (,)
    public static boolean isAlphaNumericListSeparateByComma(String s) {
        if (s != null & s.matches("^[a-zA-Z0-9-]+(,[a-zA-Z0-9-]+)*$"))
            return true;
        return false;
    }
    
    // Incluye instrucciones SQL
    public static boolean includeSQL (String s) {
        String ss = new String(s);
        if (ss != null) {
            ss = ss.toLowerCase();
            if (ss.indexOf("database") >= 0 ||
                ss.indexOf("alter")  >= 0 ||
                ss.indexOf("drop")   >= 0 ||
                ss.indexOf("delete") >= 0 ||
                ss.indexOf("insert") >= 0 ||
                ss.indexOf("update") >= 0 ||
                ss.indexOf("select") >= 0 ||
                ss.indexOf("from")   >= 0 ||
                ss.indexOf("table")  >= 0 ||
                ss.indexOf("*") >= 0 ||
                ss.indexOf("=") >= 0 ||
                ss.indexOf("?") >= 0) {
                return true;
            }
        }
        return false;
    }
    
    // Es numero
    public static int getNumber (String s) {        
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        } catch (NullPointerException e) {
            return -1;
        }
    }
    
    // Es numero real
    public static double getRealNumber (String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return Double.MIN_VALUE;
        } catch (NullPointerException e) {
            return Double.MIN_VALUE;
        }
    }
    
    // Es host cadena localhost || 1.1.1.1 <= s <= 254.254.254.254
    public static boolean isHost (String s) {
        
        if (s != null) {
            
            s = s.toLowerCase();
            if (s.compareTo("localhost") == 0)
                return true;
            
            String ip[] = s.split("\\.");
            if (ip.length == 4) {
                int a = getNumber(ip[0]);
                int b = getNumber(ip[1]);
                int c = getNumber(ip[2]);
                int d = getNumber(ip[3]);
                if (a >= 1 && a <= 254 &&
                    b >= 1 && b <= 254 &&
                    c >= 1 && c <= 254 &&
                    d >= 1 && d <= 254)
                    return true;
            }
        }
        return false;
    }
    
    // Es alfanumerico con simbolo _
    public static boolean isBD (String s) {
        if (s != null && s.matches("^[a-zA-Z0-9_]+$"))
           return true;
        return false;
    }
}

/*    
    public static void main(String args[]) {
        
        String data[] = {
            "789sd", "asf!!", "sdf478!!", " asd", " dsdf7815", "777-77-77", "45:2", "abcz",
            "78456", "asdfg", "asdf1234", "asd dfs", "sdf 7514", "2222-22-22", "48:48", "abcz", "--48", "-48",
            "78.78.", "..", "78.", ".", ".7894", "78.0", "as df insertASDF", "updatE dfsd3", "delEte sd#", "update 123"
        };
        
        for (int i = data.length-4; i < data.length; i++) {
            String rs = (Restriction.includeSQL(data[i])) ? "true" : "false";
            System.out.println("includeSQL: \t\t" + data[i] + "\t" + rs);
        }
        
        for (int i = 0; i < 1000; i++) {
            String s = UUID.randomUUID().toString();
            String rs = (Restriction.isToken(s)) ? "yes" : "no";
            System.out.println(s +" : "+ rs);
        }
                
        for (int i = 0; i < data.length; i++) {
            
            String rs = (Restriction.isNumber(data[i])) ? "true" : "false";
            System.out.println("isNumber: \t\t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isAlpha(data[i])) ? "true" : "false";
            System.out.println("isAlpha: \t\t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isAlphanumeric(data[i])) ? "true" : "false";
            System.out.println("isAlphanum: \t\t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isAlpha_esp(data[i])) ? "true" : "false";
            System.out.println("isAlpha_esp: \t\t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isAlphanumeric_esp(data[i])) ? "true" : "false";
            System.out.println("isAlphanum_esp: \t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isDate(data[i])) ? "true" : "false";
            System.out.println("isDate: \t\t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isTime(data[i])) ? "true" : "false";
            System.out.println("isTime: \t\t" + data[i] + "\t" + rs);
            
            rs = (Restriction.isFloatNumber(data[i])) ? "true" : "false";
            System.out.println("isFloatnum: \t\t" + data[i] + "\t" + rs);
            
        }
        
    }
*/ 