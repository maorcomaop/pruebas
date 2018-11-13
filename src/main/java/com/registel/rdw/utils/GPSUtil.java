/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.BaseBD;
import com.registel.rdw.logica.Base;
import com.registel.rdw.logica.DatosGPS;
import com.registel.rdw.logica.IPK;
import com.registel.rdw.reportes.IPK_r;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class GPSUtil {
    
    private static ArrayList<Base> lst_base;
    
    // Existencia de base por contenido del campo localizacion (nombre de la base)
    public static boolean esBase_porNombre (DatosGPS data) {
        
        // Localizacion en minusculas desde instruccion sql
        String loc = data.getLocalizacion_proc();

        for (int j = 0; j < lst_base.size(); j++) {
            Base b = lst_base.get(j);
            if (loc.contains(b.getNombre().toLowerCase())) {
                return true;
            }
        } return false;
    }
    
    // Existencia de base por el codigo de base en el campo msg
    public static boolean esBase_porCodigo (DatosGPS data) {
        
        String msg = data.getMsg().toLowerCase();
        
        for (int i = 0; i < lst_base.size(); i++) {
            Base b = lst_base.get(i);
            String codigoBase = "p" + b.getIdentificador();
            if (msg.contains(codigoBase))
                return true;
        } return false;
    }
    
    // Calcula IPK de entre bases
    public static ArrayList<IPK_r> IPK_porBase (ArrayList<DatosGPS> lst) {        
        
        lst_base = BaseBD.select();
        ArrayList<Integer> lst_idx_base = new ArrayList<Integer>();
               
        // Se añade primer indice de lista recorrido gps
        lst_idx_base.add(0);
        for (int i = 1; i < lst.size()-1; i++) {
            DatosGPS data  = lst.get(i);
            DatosGPS data2 = lst.get(i+1);
            
            if (esBase_porNombre(data) && !esBase_porNombre(data2)) {
                lst_idx_base.add(i);
            } else {
                continue;
            }
        }
        
        // Se añade ultimo indice de lista recorrido gps sea o no base
        int last = lst_idx_base.size()-1;
        if (lst_idx_base.get(last) < lst.size()-1) {
            lst_idx_base.add(lst.size()-1);
        }
        
        // Se calcula IPK en el rango de bases y extremos encontrados
        ArrayList<IPK> lst_ipk = new ArrayList<IPK>(); 
        for (int i = 0; i < lst_idx_base.size()-1; i++) {
            int a = lst_idx_base.get(i);
            int b = lst_idx_base.get(i+1);
            
            // Descartamos ultima base vista a = b+1
            if (a > 0)
                a += 1;
            
            IPK ipk = ipk(lst, a, b);
            lst_ipk.add(ipk); 
            
            /*
            System.out.printf("%d \t %.2f \t %.4f\n", 
                    ipk.getCantidadPasajeros(),
                    ipk.getDistanciaRecorrida(),
                    ipk.getIpk()); */
        }
        
        // Se crea lista para contener datos de IPK
        // de cada intervalo [a, b], junto con informacion de los puntos
        ArrayList<IPK_r> lst_ipkr = new ArrayList<IPK_r>();
        int k = 0;
        
        for (int i = 0; i < lst_idx_base.size()-1; i++) {
            
            int a = lst_idx_base.get(i);
            int b = lst_idx_base.get(i+1);
            
            // Descartamos ultima base vista a = b+1
            if (a > 0)
                a += 1;
            
            for (int j = a; j <= b && k < lst_ipk.size(); j++) {
                DatosGPS data = lst.get(j);
                
                // Obtenemos IPK del rango actual,
                // se almacena en cada registro del rango
                IPK ipk = lst_ipk.get(k);
                
                IPK_r ipk_r = new IPK_r();                

                ipk_r.setCantidadPasajeros(ipk.getCantidadPasajeros());
                ipk_r.setDistanciaRecorrida(ipk.getDistanciaRecorrida());
                ipk_r.setIPK(ipk.getIpk());                

                ipk_r.setPlaca(data.getPlaca());
                ipk_r.setFecha(data.getFechaGPSDate());
                ipk_r.setHora(data.getFechaGPS());
                ipk_r.setMsg(data.getMsg());
                ipk_r.setLocalizacion(data.getLocalizacion_proc());
                ipk_r.setNumeracion(data.getNumeracion());
                ipk_r.setDistancia(data.getDistanciaMetros());
                ipk_r.setEstadoConsolidacion(data.getEstadoConsolidacion());
                ipk_r.setEsPuntoControl(data.getEsPuntoControl());
                
                if (j == b) ipk_r.setEsIPK(true);                
                else        ipk_r.setEsIPK(false);
                
                lst_ipkr.add(ipk_r);
            } k++;
        }
        return lst_ipkr;
    }
    
    // Calcula IPK en el rango de registros gps [a, b]
    public static IPK ipk (ArrayList<DatosGPS> lst, int a, int b) {
        
        long numIni     = Long.MAX_VALUE;
        long numFin     = Long.MIN_VALUE;
        long distancia  = 0;
        long num        = 0;
        
        boolean extraido = false;
        for (int i = a; i <= b; i++) {
            
            DatosGPS data = lst.get(i);            
            
            if (i > 0 && !extraido) {
                num = lst.get(i-1).getNumeracion();
                extraido = true;
            } else    
                num = lst.get(i).getNumeracion();
            
            if (num > 0 && num < numIni)
                numIni = num;
            if (num > 0 && num > numFin)
                numFin = num;            
            
            distancia += data.getDistanciaMetros();
        }
        
        long   numPasajeros = (numFin - numIni);        
        double ddistancia   = (double) distancia / 1000.0;
        double ipkv         = 0.0;
        
        if (ddistancia > 0)
            ipkv = (double) numPasajeros / ddistancia;
        
        IPK ipk = new IPK();
        ipk.setCantidadPasajeros(numPasajeros);
        ipk.setDistanciaRecorrida(ddistancia);
        ipk.setIpk(ipkv);
        
        return ipk;
    }
}
