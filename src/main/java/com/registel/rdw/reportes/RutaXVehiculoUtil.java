/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.reportes;

import com.registel.rdw.datos.InformacionRegistradoraBD;
import com.registel.rdw.datos.PuntoControlBD;
import com.registel.rdw.datos.PuntoRutaBD;
import com.registel.rdw.logica.PuntoControl;
import com.registel.rdw.logica.PuntoRuta;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class RutaXVehiculoUtil {
    
    private ArrayList<RutaXVehiculo> lst_rxv;    
    private Time horaSalida;
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
        
    // Verifica los puntos recorridos vs los puntos de ruta
    // en sentido (Puntos ruta --> Puntos de control)
    public void verificarRutaHechaDesdeRuta (
                                int idir, 
                                int idRuta, 
                                String fecha,
                                Time _horaSalida) {
        
	ArrayList<PuntoRuta> lstr      = PuntoRutaBD.selectByRuta(idRuta);
	ArrayList<PuntoControl> lstpc  = PuntoControlBD.selectBy(idir, fecha);
	
	horaSalida  = _horaSalida;
	
	lst_rxv = new ArrayList<RutaXVehiculo>();
	
	if (lstr != null && lstpc != null) {
		
            // Consultamos informacion de bases
            RutaXVehiculo baseSalida  = InformacionRegistradoraBD.getBaseSalida(idir, idRuta);
            RutaXVehiculo baseLlegada = InformacionRegistradoraBD.getBaseLlegada(idir, idRuta);

            PuntoRuta pr, spr;
            PuntoControl pc, spc;
            RutaXVehiculo np;

            int puntoControl, puntoRuta;
            long mindif, dif;
            
            int size_ruta = lstr.size()/2;
            int size_pc   = lstpc.size()/2;
            
            for (int j = 0; j < size_ruta; j++) {
                
                pr = lstr.get(j); 
                puntoRuta = pr.getIdPunto();
                mindif = Long.MAX_VALUE;

                spc = null; spr = null;
                for (int i = 0; i < size_pc; i++) {
                    
                    pc = lstpc.get(i); 
                    puntoControl = pc.getIdPunto();

                    // Se considera y descarta la seleccion previa de puntos iguales
                    // Ej: Dos veces punto Villa garzon
                    if (puntoRuta == puntoControl && !pc.seleccionado()) {
                        dif = diferenciaTiempo(pr, pc);
                        if (dif < mindif) {
                            spc = pc;
                            spr = pr;
                            mindif = dif;
                        }
                    } 
                }
                
                if ((j+1) >= size_ruta) {
                    size_ruta = lstr.size();
                    size_pc   = lstpc.size();
                }

                if (spc != null && spr != null) {
                    spc.seleccionar();  // Seleccion por referencia (de lstpc)
                    np = crearPunto(spr, spc);
                    lst_rxv.add(np);
                } else {
                    np = crearPuntoNoEncontrado(pr);
                    lst_rxv.add(np);
                }
            }

            if (baseSalida != null)
                lst_rxv.add(0, baseSalida);
            if (baseLlegada != null)
                lst_rxv.add(baseLlegada);
            
            // Se descartan puntos que no corresponden a la fecha de estudio
            int size = lst_rxv.size();
            for (int i = size-1; i >= 0; i--) {
                RutaXVehiculo p = lst_rxv.get(i);
                String fechaPunto = p.getFecha();
                if (fechaPunto != null && fechaPunto.compareTo(fecha) != 0) {
                    lst_rxv.remove(i);
                }
            }
            //print();                        
	}        
    }
    
    public RutaXVehiculo crearPunto (PuntoRuta pr, PuntoControl pc) {
        
        RutaXVehiculo p = new RutaXVehiculo();
        
        long diferenciaTiempo = diferenciaTiempo(pr, pc);
        long horaPlanificada  = horaPlanificada(pr);
        String fecha          = ffmt.format(pc.getFechaPuntoControl());
        
        String nombrePunto = pc.getIdPunto() + " " + pc.getNombre();
        
        p.setIdPunto(pc.getIdPunto());
        p.setPuntoControl(nombrePunto);
        p.setNumeracion(pc.getNumeracion());
        p.setRuta(pr.getNombreRuta());
        p.setFecha(fecha);
        p.setHoraPlanificada(new Time(horaPlanificada));
        p.setHoraRealLlegada((Time) pc.getHoraPuntoControl());
        p.setMinutosHolgura(pr.getHolguraMinutos());        
        p.setDiferenciaTiempo(new Time(diferenciaTiempo));
        p.setEstado(estadoTiempo(pr, pc));
        
        return p;
    }
    
    public RutaXVehiculo crearPuntoNoEncontrado (PuntoControl pc) {
        
        RutaXVehiculo p = new RutaXVehiculo();
        
        String nombrePunto = pc.getIdPunto() + " " + pc.getNombre();
        
        p.setIdPunto(-3);
        p.setPuntoControl(nombrePunto);
        
        return p;
    }
    
    public RutaXVehiculo crearPuntoNoEncontrado (PuntoRuta pr) {
        
        RutaXVehiculo p = new RutaXVehiculo();
        
        String nombrePunto = pr.getIdPunto() + " " + pr.getNombrePunto();
        
        p.setIdPunto(-3);
        p.setRuta(pr.getNombreRuta());
        p.setPuntoControl(nombrePunto);        
        
        return p;
    }
    
    public void agregarPunto (int i, PuntoRuta pr, PuntoControl pc) {
        
        RutaXVehiculo p = crearPunto (pr, pc);
        lst_rxv.add(i, p);
    }
    
    public long diferenciaTiempo (PuntoRuta pr, PuntoControl pc) {
        
        long tiempoPC = pc.getHoraPuntoControl().getTime();
        long tiempoPR = horaSalida.getTime() + (pr.getPromedioMinutos() * 60 * 1000);
        
        long diff = Math.abs((tiempoPC - tiempoPR));        
        
        long h = diff / (60 * 60 * 1000) % 24;
        long m = diff / (60 * 1000) % 60;
        long s = diff / 1000 % 60;
        
        String sdiff = h +":"+ m +":"+ s;        
        
        try {
            Date ddiff = hfmt.parse(sdiff);
            return ddiff.getTime();
        } catch (ParseException e) {
            System.err.println(e);
        }        
        return 0;
    }
    
    public int estadoTiempo (PuntoRuta pr, PuntoControl pc) {
        
        long tiempoPC = pc.getHoraPuntoControl().getTime();
        long holguraMin = (horaSalida.getTime() + (pr.getPromedioMinutos() * 60000)) - (pr.getHolguraMinutos() * 60000);
        long holguraMax = (horaSalida.getTime() + (pr.getPromedioMinutos() * 60000)) + (pr.getHolguraMinutos() * 60000);
              
        // 0 a tiempo, 1 atrasado, 2 adelantado
        return (tiempoPC >= holguraMin && tiempoPC <= holguraMax) ? 0 :
               (tiempoPC > holguraMax) ? 1 : 2;
    }
    
    public long horaPlanificada (PuntoRuta pr) {        
               
        long hora = horaSalida.getTime() + (pr.getPromedioMinutos() * 60 * 1000);
        return hora;        
    }
    
    public void print () {
        
        for (RutaXVehiculo p : lst_rxv) {
            System.out.print(p.getIdPunto());
            System.out.print("\t" + p.getPuntoControl().substring(0, 10));
            System.out.print("\t" + p.getNumeracion());
            System.out.print("\t" + p.getHoraPlanificada());
            System.out.print("\t" + p.getHoraRealLlegada());
            System.out.print("\t" + p.getMinutosHolgura());
            System.out.print("\t" + p.getDiferenciaTiempo());
            System.out.print("\t" + p.getEstado());
            System.out.println();
        }
    }
    
    public ArrayList<RutaXVehiculo> getLstRutaXVehiculo () {
        return lst_rxv;
    }
    
    /*
    public static void main(String args[]) {
        
        RutaXVehiculoUtil r = new RutaXVehiculoUtil();
        
        r.verificarRutaHecha(412245, 89, "2016-08-09", new Time(21, 31, 0));
        r.print();
    }
    */
}
