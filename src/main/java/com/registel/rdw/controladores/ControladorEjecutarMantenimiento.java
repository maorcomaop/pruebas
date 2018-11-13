/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;


import com.registel.rdw.utils.EjecutarMantenimientos;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Richard Mejia
 * 
 * @date 14/08/2018
 */
public class ControladorEjecutarMantenimiento implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new EjecutarMantenimientos(sce.getServletContext());
        
        //Delay: inicia de inmediato
        long delayInicial = 0L;
        TimeUnit unidades = TimeUnit.MINUTES;
        
        //Periodo entre ejecuciones sucesivas
        long periodo = 1L;
        
        scheduler.scheduleAtFixedRate(command, delayInicial, periodo, unidades);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
    
}
