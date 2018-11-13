/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class TimeUtil {
    
    private static SimpleDateFormat ffmt  = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat fhfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
    public static final int LUN = 1,
                            MAR = 2,
                            MIE = 3,
                            JUE = 4,
                            VIE = 5,
                            SAB = 6,
                            DOM = 7;
    
    public static final String meses[] = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    
    // Retorna numero-dia-semana para dia actual
    // Lun 1 - Dom 7    
    public static int diaDeLaSemana() {
        
        Calendar fecha = Calendar.getInstance();
        switch (fecha.get(Calendar.DAY_OF_WEEK)) {
            case 1: return 7;
            case 2: return 1;
            case 3: return 2;
            case 4: return 3;
            case 5: return 4;
            case 6: return 5;
            case 7: return 6;
        } return 0;
    }
    
    // Retorna numero-dia-semana para dia de fecha especificado
    public static int diaDeLaSemana(Date d) {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);
        switch (fecha.get(Calendar.DAY_OF_WEEK)) {
            case 1: return 7;
            case 2: return 1;
            case 3: return 2;
            case 4: return 3;
            case 5: return 4;
            case 6: return 5;
            case 7: return 6;
        } return 0;
    }
    
    // Retorna numero-dia-mes para dia de fecha especificado
    public static int diaDelMes(Date d) {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);
        return fecha.get(Calendar.DAY_OF_MONTH);
    }
    
    // Retorna numero-mes de fecha especifiada
    public static int mes(Date d) {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);
        return fecha.get(Calendar.MONTH);
    }
    
    // Retorna numero-anio de fecha especificada
    public static int anio(Date d) {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);
        return fecha.get(Calendar.YEAR);
    }
    
    // Retorna numero-anio para fecha actual
    public static int anio() {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date());
        return fecha.get(Calendar.YEAR);
    }
    
    // Suma m cantidad de minutos a tiempo t
    public static Time sumarMinutos(Time t, int m) {
        
        Calendar tiempo = Calendar.getInstance();
        tiempo.setTime(t);
        tiempo.add(Calendar.MINUTE, m);
        return new Time(tiempo.getTime().getTime());
    }
    
    // Comprueba si hora se encuentra en intervalo de hora sha y shb
    public static boolean entreHoras(Time hora, String sha, String shb) {
        
        if (hora == null)
            return false;
        
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
            Time horaA = new Time(fmt.parse(sha).getTime());
            Time horaB = new Time(fmt.parse(shb).getTime());
            
            if (hora.getTime() >= horaA.getTime() &&
                hora.getTime() <= horaB.getTime())
                return true;            
            
        } catch (ParseException e) {
            System.err.println(e);
        } 
        return false;
    }
    
    // Suma un dia a fecha especificada
    public static Date sumarDia(Date d) {
        return sumarDias(d, 1);
    }
    
    // Resta un dia a fecha especificada
    public static Date restarDia(Date d) {
        return sumarDias(d, -1);
    }
    
    // Suma un mes a fecha especificada
    public static Date sumarMes(Date d) {
        return sumarMeses(d, 1);
    }
    
    // Resta un mes a fecha especificada
    public static Date restarMes(Date d) {
        return sumarMeses(d, -1);
    }
    
    // Suma n cantidad de dias a fecha especificada
    public static Date sumarDias(Date d, int n) {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);
        fecha.add(Calendar.DATE, n);        
        return fecha.getTime();
    }   
    
    // Suma n cantidad de meses a fecha especificada
    public static Date sumarMeses(Date d, int n) {
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);
        fecha.add(Calendar.MONTH, n);
        return fecha.getTime();
    }
    
    // Comprueba si fechas son iguales
    public static boolean fechasIguales(Date a, Date b) {
        
        if (a == null || b == null)
            return true;
        
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fechaA = fmt.format(a);
        String fechaB = fmt.format(b);
        
        if (fechaA.compareTo(fechaB) > 0)
            return true;
        return false;
    }
    
    // Comprueba si hora <ha> es superior o igual a hora <hb>
    public static boolean alcanceHora(Time ha, Time hb) {
        
        if (ha == null || hb == null)
            return true;
        
        if (ha.getTime() >= hb.getTime())
            return true;
        return false;
    }
    
    // Comprueba si rango de fechas [fechaA, fechaB] difiere a lo sumo n dias,
    // para fechaA siendo el dia actual.
    public static boolean rangoFechaCorrecto(String fechaA, String fechaB, int n) {
        
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fechaLim = "";        
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date dateA   = fmt.parse(fechaA);            
            Date dateLim = sumarDias(dateA, n);
            fechaLim     = fmt.format(dateLim);
        } catch (ParseException e) {
            System.err.println(e);
            return false;
        }
    
        // fecha inicial >= fecha actual
        // fecha final >= fecha inicial
        // fecha final <= n dias (de fecha inicial)        
        if (fechaA.compareTo(fechaActual) >= 0 &&
            fechaB.compareTo(fechaA) >= 0 &&
            fechaB.compareTo(fechaLim) <= 0) {
            return true;
        } 
        return false;
    }
    
    // Genera hora cero 00:00:00
    public static Time horaCero() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date fecha_cero = cal.getTime();
        Time hora_cero  = new Time(fecha_cero.getTime());
        return hora_cero;
    }
    
    // Genera hora para tiempo <time> transcurrido
    public static Time tiempoTranscurrido(long time) {
        Time hora_cero = horaCero();        
        return new Time(hora_cero.getTime() + Math.abs(time));
    }
        
    // Retorna fecha (1er dia) de mes especificado
    // 0 <= mes <= 11
    public static Date fechaEnMes(int mes) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fecha_mes = cal.getTime();
        return fecha_mes;
    }
    
    // Comprueba si mes de fecha es diferente al numero-mes especificado
    public static boolean cambioMes(Date fecha, int mes_referencia) {
                
        int mes_actual = mes(fecha);
        if (mes_actual != mes_referencia) {
            return true;
        }
        return false;
    }
    
    // Convierte fecha en cadena a objeto Date
    public static Date getDate(String fecha) {
        try {
            return ffmt.parse(fecha);            
        } catch (ParseException e) { 
            System.err.println(e);
        }            
        return null;
    }
    
    public static Date getDateTime(String fechahora) {
        try {
            return fhfmt.parse(fechahora);
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }
    
    public static Date ajusteFechaHora(Date fechahora, int tiempo) {
        if (fechahora != null) {
            long ntiempo = fechahora.getTime();
            ntiempo = ntiempo + (tiempo * 60000);
            return new Date(ntiempo);
        }
        return fechahora;
    }
    
    public static String getStrDateTime(Date date) {
        return fhfmt.format(date);
    }
    
    /*
    public static void main(String[] args) {        
        System.out.println(TimeUtil.diaDeLaSemana());
        
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
            Time t = new Time(fmt.parse("18:20").getTime());
            System.out.println(TimeUtil.sumarMinutos(t, 25));            
            System.out.println(TimeUtil.entreHoras(t, "01:10", "23:20"));
            
        } catch (ParseException e) {
            System.err.println(e);
        }
        
        Date hoy = new Date();
        System.out.println(TimeUtil.sumarDia(hoy));
        
        System.out.println(TimeUtil.fechasIguales(hoy, hoy));
        System.out.println(TimeUtil.fechasIguales(hoy, TimeUtil.sumarDia(hoy)));
    } */
}
