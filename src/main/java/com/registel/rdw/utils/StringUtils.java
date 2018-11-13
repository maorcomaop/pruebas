/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

/**
 *
 * @author USER
 */
public class StringUtils {

    public static String upperFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }

        String word_aux = "";
        String word_rsp = "";
        String[] words = original.split(" ");
        for (String word : words) {
            if (!word.equalsIgnoreCase("")) {
                word_aux = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                word_rsp += word_aux+" ";
            }
        }
        word_rsp.substring(0, (word_rsp.length() - 1));
        return word_rsp;
    }
    
    /**
     * @author Richard Mejia
     * @date 30/08/2018
     * Método encargado de generar una cadena con los elementos que le llegan como parámetro.
     * El método genera una cadena con que contiene x veces el patrón separado por comas.
     * Este método se utiliza para generar los patrones en los queries de inserts y updates.
     * @param patron Es el conjunto de caracteres que se va a repetir en la cadena generada.
     * @param apertura Es un caracter que se pondrá al inicio de la cadena generada.
     * @param cierre Es un caracter que se pondrá al final de la cadena generada.
     * @param repeticiones Es la cantidad de veces que se tiene que repetir el patrón.
     * @return Retorna una cadena cuyo primer caracter es apertura, el último caracter es cierre, y 
     * repite patron la cantidad de repeticiones que se envía.
     */
    public static String generarCadena(String patron, String apertura, String cierre, int repeticiones) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < repeticiones; i++) {

            if (i == (repeticiones - 1)) {
                resultado.append(patron).append(cierre).append(" ");
            } else if (i == 0) {
                resultado.append(apertura).append(patron).append(", ");
            } else {
                resultado.append(patron).append(", ");
            }
        }

        return resultado.toString();
    }

}
