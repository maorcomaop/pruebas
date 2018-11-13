/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.text.DecimalFormat;

/**
 *
 * @author lider_desarrollador
 */
public class Profile {
    
    private long time = 0;
    
    public Profile() {
        time = System.currentTimeMillis();
    }
    
    public void takeTime(String name) {
        
        long last_time   = System.currentTimeMillis();
        long elapse_time = last_time - time;
        time = last_time;
        DecimalFormat df = new DecimalFormat("0.00");
        
        double sec_time  = (double) elapse_time / 1000;
        double min_time  = (double) sec_time / 60;
        double hora_time = (double) min_time / 60;
        
        String hora = df.format(hora_time);
        String min  = df.format(min_time);
        String sec  = df.format(sec_time);
        
        System.out.println("> " + name +"\t"+ hora +"h "+ min +"m "+ sec + "s");
    }
    
}
