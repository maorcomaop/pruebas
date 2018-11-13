/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.PlanillaDespacho;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class DespachoControlHTML {
    
    private static SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat hfmt_min = new SimpleDateFormat("HH:mm");
    private static int TIPO_GEN_DPH = Constant.DPH_PLANIFICADO;
    
    public static void tipo_despacho(int tipo_dph) {
        TIPO_GEN_DPH = tipo_dph;
    }
    
    public static String tbl_historia(ArrayList<Planilla> lst_pll) {
        
        String tbl_enc, tbl_det, tbl, fila_puntos, fila_tiempos, cols, placa;
        tbl_enc = tbl_det = tbl = fila_puntos = fila_tiempos = cols = placa = "";
        
        String  tr = "<tr>", _tr = "</tr>",
                td = "<td>", _td = "</td>";      
        
        Planilla pll_prev = null;
        boolean cambio_vehiculo = false;
                
        for (int i = 0; i < lst_pll.size(); i++) {
            
            if (i >= 1)
                pll_prev = lst_pll.get(i-1); 
            
            Planilla pll      = lst_pll.get(i);                        
            int numero_vuelta = pll.getNumeroVuelta();                       
            
            if (pll_prev != null && 
                pll_prev.getPlaca().compareTo(pll.getPlaca()) != 0) {
                cambio_vehiculo = true;
            }
                        
            ArrayList<PlanillaDespacho> pd_detalle = pll.getDetalle();
            
            if (numero_vuelta == 1 || cambio_vehiculo) {          
                
                if (tbl_enc == "") {
                    tbl_enc     = tabla_encabezado(pll);                
                    fila_puntos = fila_puntos(pll);
                    placa       = pll.getPlaca();
                }
                if (fila_tiempos != "") {
                    tbl_det      = tabla_detalle(fila_puntos, fila_tiempos, placa);  
                    tbl          += tbl_enc + tbl_det;                    
                    tbl_enc      = tabla_encabezado(pll);                
                    fila_puntos  = fila_puntos(pll);
                    placa        = pll.getPlaca();
                    fila_tiempos = "";
                }                                
                cambio_vehiculo = false;
            } 
            
            if (TIPO_GEN_DPH == Constant.DPH_MANUAL) {
                // Manual
                fila_tiempos += fila_tiempos_intervalo(numero_vuelta, pd_detalle);
            } else { 
                // Planificado
                fila_tiempos += fila_tiempos(numero_vuelta, pd_detalle);            
            } 
            
            if (i == lst_pll.size()-1) {
                tbl_enc     = tabla_encabezado(pll);
                fila_puntos = fila_puntos(pll);
                placa       = pll.getPlaca();
                tbl_det     = tabla_detalle(fila_puntos, fila_tiempos, placa);
                tbl         += tbl_enc + tbl_det;
            }
        }
        
        return tbl;
    }
    
    public static String tabla_encabezado(Planilla pll) {
        
        String nplanilla    = DespachoUtil.formatoNumeroPlanilla(pll.getNumeroPlanilla());
        String ruta         = pll.getRuta();
        String placa        = pll.getPlaca();
        String ninterno     = pll.getNumeroInterno();
        ninterno            = (ninterno == null || ninterno == "") ? "-" : ninterno;
        String fecha        = ffmt.format(pll.getFecha());
        
        // Se asgina Fecha/hora salida si es despacho a pedido
        String columna_fecha = "<th>Fecha</th>";
        if (TIPO_GEN_DPH == Constant.DPH_MANUAL) {
            ArrayList<PlanillaDespacho> pd_detalle = pll.getDetalle();
            PlanillaDespacho pd = pd_detalle.get(0);
            String hora_salida  = hfmt.format(pd.getHoraPlanificada());
            fecha += " " + hora_salida;
            columna_fecha = "<th>Fecha/hora salida</th>";
        }
                
        String tbl = 
            "<table id='" +placa+ "' class='tbl-style-cpd' style='margin-top: 32px;'>"
                + "<thead>"
                +   "<tr>"
                +   "<th></th>"  + "<td id ='"+placa+"' class='to_customize'><div class='custom-icon'></div></td>"
                +   "<th>N° Planilla</th>"  + "<td>" + nplanilla + "</td>"
                +   "<th>N° Interno</th>"   + "<td>" + ninterno  + "</td>"
                +   "<th>Placa</th>"        + "<td>" + placa  + "</td>"
                +   "<th>Ruta</th>"         + "<td>" + ruta   + "</td>"
                +   columna_fecha           + "<td>" + fecha  + "</td>"
                +   "</tr>"
                + "</thead>"
            + "</table>";
        
        return tbl;
    }
    
    public static String tabla_detalle(String fila_puntos, String fila_tiempos, String placa) {
        String tbl = 
            "<table id='" +placa+ "' class='tbl-style-cpdd'>"
            +   "<thead>" + fila_puntos  + "</thead>"
            +   "<tbody>" + fila_tiempos + "</tbody>"
            + "</table>";
        return tbl;
    }
    
    public static String fila_puntos(Planilla pll) {
        
        ArrayList<PlanillaDespacho> pd_detalle = pll.getDetalle();
        
        String  tr = "<tr>", _tr = "</tr>",
                td = "<td>", _td = "</td>";                
        
        String cols  = td + _td, fila_puntos;        
        
        for (int i = 0; i < pd_detalle.size(); i++) {
            
            PlanillaDespacho pd = pd_detalle.get(i);
            String nombrePunto = DespachoUtil.obtieneNombrePunto(pd);
            
            td = "<td data-toggle='tooltip' title='"+ nombrePunto +"'>";
            cols += td + pd.getPunto() + _td;
        }
        fila_puntos = tr + cols + _tr;
        return fila_puntos;
    }
    
    public static String fila_tiempos_intervalo(int numero_vuelta, ArrayList<PlanillaDespacho> lst_pd) {
        
        String  tr = "<tr>", _tr = "</tr>",
                td = "<td>", _td = "</td>";
        
        String shora_real, shora_planificada, sdiferencia, clase_lbl;
        Time hora_salida = null;        
        
        String cols = td + numero_vuelta + _td;
        
        for (int i = 0; i < lst_pd.size(); i++) {
            PlanillaDespacho pd   = lst_pd.get(i);
            Time hora_planificada = pd.getHoraPlanificada();
            Time hora_real        = pd.getHoraReal();
            long diferencia       = pd.getDiferencia();
            int tipo_punto        = pd.getTipoPunto();
            int tiempo_tramo      = pd.getIntervaloLlegada();
            
            shora_planificada = hfmt.format(hora_planificada); // -
            shora_real = sdiferencia = "-";            
            clase_lbl  = "lbl-gris";
            
            if (hora_real != null) {
                if (tipo_punto == 1) {
                    hora_salida       = hora_real;
                    shora_planificada = hfmt.format(hora_planificada);
                    shora_real        = hfmt.format(hora_real);
                    diferencia        = pd.getDiferencia(); // 0
                } else {
                    if (hora_salida != null) {
                        //Time thora_planificada = TimeUtil.sumarMinutos(hora_salida, tiempo_tramo);                        
                        //shora_planificada      = hfmt.format(thora_planificada);
                        shora_planificada      = hfmt.format(hora_planificada);
                        shora_real             = hfmt.format(hora_real);
                        diferencia             = pd.getDiferencia();
                    } else {
                        shora_planificada = hfmt.format(hora_planificada);
                        shora_real        = hfmt.format(hora_real);
                        diferencia        = pd.getDiferencia();
                    }
                }            
            
                if (diferencia == 0)  {
                    sdiferencia = hfmt.format(TimeUtil.horaCero());
                    clase_lbl = "lbl-verde";
                } else {
                    sdiferencia = hfmt.format(TimeUtil.tiempoTranscurrido(diferencia));                                    
                    if (diferencia > 0) {
                        sdiferencia = "+" + sdiferencia;
                        clase_lbl = "lbl-rojo";
                    } else {
                        sdiferencia = "-" + sdiferencia;
                        clase_lbl = "lbl-naranja";
                    }
                }
            }
            
            td = "<td class='"+ clase_lbl +"'>";
            cols += td +
                    "<span class='primary'>"    + shora_planificada +  "</span>" +
                    "<span class='secondary'>"  + shora_real        +  "</span>" +
                    "<span class='secondary'>"  + sdiferencia       +  "</span>"
                + _td;            
        }
        
        return tr + cols + _tr;
    }
    
    public static String fila_tiempos(int numero_vuelta, ArrayList<PlanillaDespacho> lst_pd) {                           
        
        String  tr = "<tr>", _tr = "</tr>",
                td = "<td>", _td = "</td>";                
        
        String cols = td + numero_vuelta + _td;
        String hora_planificada, hora_real, tiempo_diferencia, clase_lbl;
        
        for (int i = 0; i < lst_pd.size(); i++) {
            PlanillaDespacho pd = lst_pd.get(i);
            
            hora_planificada = hfmt.format(pd.getHoraPlanificada());
            long diferencia  = pd.getDiferencia();
            Time tiempo = TimeUtil.horaCero();      
            
            hora_real = tiempo_diferencia = "-";
            clase_lbl = "lbl-gris";
            
            if (pd.getEstadoDespacho() == 1 ||
                pd.getEstadoDespacho() == 2) {
                hora_real = hfmt.format(pd.getHoraReal());
                
                if (diferencia == 0) {
                    tiempo_diferencia = "00:00:00";
                    clase_lbl = "lbl-verde";
                
                } else if (diferencia > 0) {
                    tiempo = new Time(tiempo.getTime() + diferencia);
                    tiempo_diferencia = "+" + hfmt.format(tiempo);
                    clase_lbl = "lbl-rojo";
                
                } else {
                    tiempo = new Time(tiempo.getTime() + (-1 * diferencia));
                    tiempo_diferencia = "-" + hfmt.format(tiempo);
                    clase_lbl = "lbl-naranja";
                }
            } 
            
            if (pd.getEstadoDespacho() == 2) {
                clase_lbl = "lbl-morado";
            }
            if (pd.getEstadoDespacho() == -1) {
                hora_planificada = hora_real = tiempo_diferencia = "-";
                clase_lbl = "lbl-negro";
            }
            
            td = "<td class='" + clase_lbl + "'>";
            cols += td +
                "<span class='primary'>"   + hora_planificada  + "</span>" +
                "<span class='secondary'>" + hora_real         + "</span>" +
                "<span class='secondary'>" + tiempo_diferencia + "</span>"
                //"<span class='" + clase_lbl + "'></span>"                       
            + _td;
        } 
        String row = tr + cols + _tr;
        return row;
    }    
}
