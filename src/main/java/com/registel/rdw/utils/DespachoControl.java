/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.DespachoControlBD;
import com.registel.rdw.logica.Empresa;

/**
 *
 * @author lider_desarrollador
 */
public class DespachoControl extends Thread {
    
    private boolean permitir_control = false;
    private String tipo_control      = Constant.CONTROL_PLANIFICADO;
    private int tiempo_comprobacion  = 5;
    private Empresa empresa;
    
    // Inicializa parametros de control despacho
    public DespachoControl(int tiempo_comprobacion) {
        this.tiempo_comprobacion = tiempo_comprobacion;
        this.permitir_control    = true;
        establecerTiempoComprobacion(tiempo_comprobacion);
    }
    
    // Pausa control despacho
    public void pausar() {
        permitir_control = false;
    }
    
    // Reanuda control despacho
    public void reanudar() {
        permitir_control = true;
    }
    
    // Verifica si control despacho se encuentra pausado
    public boolean pausado() {
        if (!permitir_control)
            return true;
        return false;
    }
    
    // Establece tipo de control de despacho
    public void establecerTipoControl(String tipo_control) {
        this.tipo_control = tipo_control;
    }
    
    // Retorna tipo de control de despacho
    public String obtenerTipoControl() {
        return tipo_control;
    }
    
    // Establece tiempo de comprobacion para control despacho
    public void establecerTiempoComprobacion(int tiempo_comprobacion) {
        DespachoControlBD.establecer_tiempo_comprobacion(tiempo_comprobacion);
    }   
    
    // Establece empresa
    public void establecerEmpresa(Empresa emp) {
        empresa = emp;
    }
    
    // Retorna empresa
    public Empresa obtenerEmpresa() {
        return empresa;
    }
    
    // Retorna variable de inicio/pausa de control despacho
    public boolean permitirControl() {
        return permitir_control;
    }    
    
    // Comprueba si cadenas de texto a y b son iguales
    public boolean esControl(String a, String b) {
        if (a != null && b != null) {
            a = a.toLowerCase();
            b = b.toLowerCase();
            if (a.compareTo(b) == 0) {
                return true;
            }
        }
        return false;
    }
    
    // Ejecuta hilo para control despacho
    public void run() {
                
        try {
            while (true) {
                
                if (permitirControl()) 
                {
                    String tipo_control = obtenerTipoControl();                    
                    
                    // Comprueba si tipo control establecido es planificado
                    // (Asociado a planilla planificada)
                    if (esControl(tipo_control, Constant.CONTROL_PLANIFICADO)) {
                        
                        DespachoControlPorTiempo dphct = new DespachoControlPorTiempo();
                        dphct.establecerEmpresa(obtenerEmpresa());
                        dphct.iniciarControl();
                        System.out.println(">| Control por tiempo-planificado");
                    
                    // Comprueba si tipo control establecido es por intervalo
                    // (Asociado a planilla a pedido / manual)
                    } else if (esControl(tipo_control, Constant.CONTROL_INTERVALO)) {
                        
                        DespachoControlPorIntervalo dphci = new DespachoControlPorIntervalo();
                        dphci.establecerEmpresa(obtenerEmpresa());
                        dphci.iniciarControl();
                        System.out.println(">| Control por intervalo llegada");
                    
                    // Asume tipo control planificado por defecto
                    } else {
                        
                        DespachoControlPorTiempo dphct = new DespachoControlPorTiempo();
                        dphct.establecerEmpresa(obtenerEmpresa());
                        dphct.iniciarControl();
                        System.out.println(">| Control por tiempo-planificado (por defecto)");
                    }
                }
                
                Thread.sleep(20000);
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }        
}
