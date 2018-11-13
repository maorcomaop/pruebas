/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.PlanillaDespachoMin;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class PlanillaUtil {
    
    private static SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat hfmt_full = new SimpleDateFormat("HH:mm:ss");
    
    // Selecciona registros de listado pasado como parametro, cuya vuelta
    // se presenta en la hora actual.
    public ArrayList<Planilla> filtro_hora_actual(ArrayList<Planilla> lst_pll) {
        
        Date fecha_actual  = new Date();
        String hora_actual = hfmt.format(fecha_actual);
        
        ArrayList<Planilla> lst_pll_ha = new ArrayList<Planilla>();
        
        for (int i = 0; i < lst_pll.size(); i++) {
            Planilla pll = lst_pll.get(i);
            ArrayList<PlanillaDespacho> lst_pd = pll.getDetalle();            
            
            int numero_vuelta = 0;            
            for (int j = 0; j < lst_pd.size(); j++) {
                PlanillaDespacho pd = lst_pd.get(j);                
                String hora_pd = hfmt.format(pd.getHoraPlanificada());
                
                // Si hora planificada es >= hora actual
                // se captura el numero de vuelta (vuelta actual)
                if (hora_pd.compareTo(hora_actual) >= 0) {                    
                    numero_vuelta = pd.getNumeroVuelta();
                    break;
                }
            }
            
            ArrayList<PlanillaDespacho> lst_pd_ha = 
                    new ArrayList<PlanillaDespacho>();
            
            // Se seleccionan registros de vuelta actual
            for (int j = 0; j < lst_pd.size(); j++) {
                PlanillaDespacho pd = lst_pd.get(j);
                
                if (pd.getNumeroVuelta() == numero_vuelta) {
                    lst_pd_ha.add(pd);
                }
            }
            
            Planilla pll_ha = new Planilla();
            pll_ha.setFecha(pll.getFecha());
            pll_ha.setPlaca(pll.getPlaca());
            pll_ha.setNumeroInterno(pll.getNumeroInterno());
            pll_ha.setRuta(pll.getRuta());
            pll_ha.setNumeroVuelta(numero_vuelta);
            pll_ha.setNumeroPlanilla(pll.getNumeroPlanilla());
            pll_ha.setDetalle(lst_pd_ha);
            
            if (!existe_vehiculo(lst_pll_ha, pll_ha.getPlaca()))
                lst_pll_ha.add(pll_ha);
        }
        
        return lst_pll_ha;
    }
    
    // Comprueba si vehiculo existe en listado.
    public boolean existe_vehiculo(ArrayList<Planilla> lst_pll, String placa) {
        
        placa = placa.toLowerCase();        
        
        for (Planilla pll : lst_pll) {
            String placa_pll = pll.getPlaca().toLowerCase();
            if (placa_pll.compareTo(placa) == 0)
                return true;
        }
        return false;
    }
    
    // Establece fecha (objeto Date) con hora especifica.
    public Date establecer_hora(Time hora) {
        
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        String str_hora   = fmt.format(new Date(hora.getTime()));
        String arr_hora[] = str_hora.split(":");
               
        int hora_reg = Restriction.getNumber(arr_hora[0]);
        int min_reg  = Restriction.getNumber(arr_hora[1]);
        int sec_reg  = Restriction.getNumber(arr_hora[2]);
        
        long hora_mil = (hora_reg * 3600) + (min_reg * 60) + sec_reg;
        hora_mil *= 1000;
        
        Calendar fecha_actual = Calendar.getInstance();
        fecha_actual.set(Calendar.HOUR_OF_DAY, 0);
        fecha_actual.set(Calendar.MINUTE, 0);
        fecha_actual.set(Calendar.SECOND, 0);
        fecha_actual.set(Calendar.MILLISECOND, 0);
        
        Date fecha_cero = fecha_actual.getTime();
        long fecha_hora = fecha_cero.getTime() + hora_mil;
        Date fecha_reg  = new Date(fecha_hora);
        
        return fecha_reg;
    }
    
    // Extrae numero de vuelta cuya hora actual se encuentra en
    // en el horario de duracion de alguna vuelta establecida
    // (hora inicial <= hora actual <= hora final).
    public static int vuelta_actual(ArrayList<PlanillaDespachoMin> lst_pd) {
            
        Date hora_actual = new Date();
        String shora_actual = hfmt.format(hora_actual);        

        for (PlanillaDespachoMin pd : lst_pd) {
            String hora_inicio = pd.getHoraInicioStr();
            String hora_fin    = pd.getHoraFinStr();
            if (shora_actual.compareTo(hora_inicio) >= 0 &&
                shora_actual.compareTo(hora_fin) <= 0) {
                return pd.getNumeroVuelta();
            }
        }

        return 0;
    }
    
    // Extrae registros cuya vuelta son igual o superior a vuelta actual.
    public static ArrayList<PlanillaDespachoMin> filtro_hora_actual_min(ArrayList<PlanillaDespachoMin> lst) {
        
        ArrayList<PlanillaDespachoMin> nlst = new ArrayList<PlanillaDespachoMin>();
        int nvuelta = vuelta_actual(lst);
        
        for (PlanillaDespachoMin pd : lst) {
            if (pd.getNumeroVuelta() >= nvuelta) {
                nlst.add(pd);
            }
        }
        
        return nlst;
    }
}
