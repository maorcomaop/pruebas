/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

/**
 *
 * @author lider_desarrollador
 */
public class Coordenadas {

    /*
     * Convierte grados decimales a grados minutos
     * -74.0603192746312 -> 0743.619156477872
     * crd : coordenada en grados decimales
     */
    public static String toGMD (String crd) {
        
        if (crd == null || crd == "")
            return null;
        
        String enteroC  = crd.substring(0, crd.indexOf("."));   //System.out.println("\n0, indexOf(.) " + enteroC);
        String decimalC = crd.substring(crd.indexOf("."));      //System.out.println("indexOf(.) " + decimalC);
        Double doubleC = Double.parseDouble(decimalC);      
        doubleC *= 60;                                      //System.out.println("*60 " + doubleC);
        
        if (enteroC.toCharArray()[0] == '-') {
            enteroC = enteroC.substring(1);
        }
        
        String rs = "0" + enteroC + doubleC.toString();     //System.out.println("> " + rs);
        return rs;
    }
        
    /*
     *  Convierte grados minutos a grados decimales
     *  (Sin unidad de desplazamiento)
     *  0953.5107 -> enteroC : 09
     *  0953.5107 -> decimalC : 53.5107 / 60 -> 0.891845
     *  0.891845 -> doubleC_GD : .891845
     *  rs : 9 + .891845 : 9.891845
     */
    public static String toGD (String crd, String dir) {
        
        if (crd == null || crd == "" ||
            dir == null || dir == "")
            return null;
        
        String enteroC  = crd.substring(0, crd.indexOf(".") - 2);       //System.out.println("\n0, indexOf(.) - 2 " + enteroC);
        String decimalC = crd.substring(crd.indexOf(".") - 2);          //System.out.println("indexOf(.) - 2 " + decimalC);
        Double doubleC  = Double.parseDouble(decimalC);
        doubleC /= 60;                                                  //System.out.println("/60 " + doubleC);
        String doubleC_GD = doubleC.toString();
        doubleC_GD = doubleC_GD.substring(doubleC_GD.indexOf("."));     //System.out.println("substring(indexOf(.)) " + doubleC_GD);
        
        String rs = enteroC.substring(1) + doubleC_GD;
        
        if (dir.equalsIgnoreCase("sur") || 
            dir.equalsIgnoreCase("oeste")) {
            rs = "-" + rs;
        }   //System.out.println(">> " + rs);
        
        return rs;
    }
    
    /*
     * Convierte grados minutos a grados decimales
     * crd : coordenada en grados minutos
     * dir : direccion (norte sur este oeste)
     * lpe : unidades de desplazamiento de la coma tras conversion a grados minutos
     */
    public static String toGD (String crd, String dir, int lpe) {
        
        if (crd == null || crd == "" ||
            dir == null || dir == "" ||
            lpe < 0)
            return null;
        
        String enteroC  = crd.substring(0, crd.indexOf(".") - lpe);     //System.out.println("\n0, indexOf(.) - " + lpe +" "+ enteroC);
        String decimalC = crd.substring(crd.indexOf(".") - lpe);        //System.out.println("indexOf(.) - " + lpe +" "+ decimalC);
        Double doubleC  = Double.parseDouble(decimalC);
        doubleC /= 60;                                                  //System.out.println("/60 " + doubleC);
        String doubleC_GD = doubleC.toString();
        doubleC_GD = doubleC_GD.substring(doubleC_GD.indexOf("."));     //System.out.println("substring(indexOf(.)) " + doubleC_GD);
        
        String rs = enteroC.substring(1) + doubleC_GD;
        
        if (dir.equalsIgnoreCase("sur") || 
            dir.equalsIgnoreCase("oeste")) {
            rs = "-" + rs;
        }   //System.out.println(">> " + rs);
        
        return rs;
    }
    
    /* 
     * Inicia conversion de grados minutos a grados decimales,
     * antes calcula la cantidad de unidades que se desplazo la coma
     *      074 + .060319274*60 = 0743.6191564 (+ concatenacion)
     *      -74.0603192746312 -> 0743.619156477872 [4]
     *      -74.06031 -> 074 [3]
     *      lpe = 4 - 3 : 1
     *
     * cgd : coordenada en grados minutos
     * dir : direccion (norte sur este oeste)
     * cgd : coordenada grados decimales
     */
    public static String toGD (String crd, String dir, String cgd) {
        
        if (cgd == null || cgd == "") return null;
        
        // -76.1213 -> 076 Obtenemos parte entera
        String enteroCGD = cgd.substring(0, cgd.indexOf("."));
        if (enteroCGD.toCharArray()[0] == '-')
            enteroCGD = enteroCGD.substring(1);
        enteroCGD = "0" + enteroCGD;
        
        String enteroCRD = crd.substring(0, crd.indexOf("."));
        int lpe = enteroCRD.length() - enteroCGD.length();
        
        return toGD(crd, dir, lpe);
    }    
}    
    
/*
    public static void main(String args[]) {
        
        String latn = "4.861257946734083";
        String lonn = "-74.0603192746312";
        String latGMD = Coordenadas.toGMD(latn);        
        String lonGMD = Coordenadas.toGMD(lonn);
        String lat = Coordenadas.toGD(latGMD, "norte", latn);        
        String lon = Coordenadas.toGD(lonGMD, "oeste", lonn);
        
        System.out.println();
        System.out.println(latn +" "+ latGMD +" "+ lat);
        System.out.println(lonn +" "+ lonGMD +" "+ lon);
    }
}
*/