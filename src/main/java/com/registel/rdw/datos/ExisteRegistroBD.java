/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

/**
 *
 * @author lider_desarrollador
 */
public class ExisteRegistroBD extends Exception {
    
    // Instancia y lanza excepcion con mensaje asociado    
    public ExisteRegistroBD (String msg) {
        super (msg);
    }
}
