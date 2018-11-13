/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.CalificacionConductorBD;
import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.ReportesBD;
import com.registel.rdw.logica.ConfCalificacionConductor;
import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.RendimientoConductor;
import com.registel.rdw.reportes.CumplimientoRuta;
import com.registel.rdw.reportes.PuntoNoRegistrado;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ReporteWeb {
    
    private ParametrosReporte pr;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    // Parametriza valores de consulta e inicializa variables
    // peticion y respuesta, usados en generacion de reporte.
    public ReporteWeb(Map<String, String> h,
                      HttpServletRequest req,
                      HttpServletResponse res) {
        pr = new ParametrosReporte(h);
        request  = req;
        response = res;
    }
    
    // Inicializa parametros y valores de peticion y respuesta,
    // usados en generacion de reporte.
    public ReporteWeb(ParametrosReporte pr_,
                      HttpServletRequest req,
                      HttpServletResponse res) {
        pr = pr_;
        request  = req;
        response = res;
    }
    
    // Inicia generacion de reporte web segun su tipo.
    public String generarReporteWeb(int ntp) {
        
        ReportesBD rbd = new ReportesBD();
        HttpSession session = request.getSession();
        session.setAttribute("parametrosReporte", pr);
        
        switch (ntp) {
            case 23: {
                rbd.cumplimientoRutaConsolidado(pr);
                ArrayList<CumplimientoRuta> lst = rbd.getLstCumplimientoRutaConsolidado();
                session.setAttribute("lst_crc", lst);
                session.setAttribute("totales", rbd.getTotalesCumplimientoRuta(lst));
                break;
            }
            case 24: {
                rbd.cumplimientoRutaXVehiculo(pr);
                ArrayList<CumplimientoRuta> lst = rbd.getLstCumplimientoRutaXVehiculo();
                session.setAttribute("lst_crxv", lst);
                session.setAttribute("totales", rbd.getTotalesCumplimientoRuta(lst));
                break;
            }            
            case 25: {
                rbd.cumplimientoRutaXConductor(pr);
                ArrayList<CumplimientoRuta> lst = rbd.getLstCumplimientoRutaXConductor();
                session.setAttribute("lst_crcd", lst);
                session.setAttribute("totales", rbd.getTotalesCumplimientoRuta(lst));
                break;
            }
            case 26: {
                rbd.consolidadoDespacho(pr);
                ArrayList<PlanillaDespacho> lst = rbd.getLstConsolidadoDespacho();
                //session.setAttribute("lst_cdph", lst);
                
                ArrayList<Planilla> lst_pll  = DespachoBD.formato_planillaXvuelta(lst);
                ArrayList<String> lst_pto    = ReporteUtil.extraerPuntosDespacho(lst);                
                                
                Planilla pll_prev = null; boolean estiloFila = false;
                for (Planilla pll : lst_pll) {
                    if (pll_prev != null) {
                        if (pll_prev.getPlaca().compareTo(pll.getPlaca()) != 0) {
                            estiloFila = (!estiloFila) ? true : false;
                        }
                    }
                    pll_prev = pll; 
                    String estilo = "lbl-sin-estilo-fila";
                    if (estiloFila) {
                        estilo = "lbl-estilo-fila-2";
                    }
                    pll.setEstiloFilaStr(estilo);
                }
                
                session.setAttribute("lst_pll", lst_pll);
                session.setAttribute("lst_pto", lst_pto);
                
                break;
            }
            // *** Reportes incumplimiento de punto control
            case 27: {
                rbd.puntoNoRegistrado(pr);
                ArrayList<PuntoNoRegistrado> lst_pnr = rbd.getLstPuntoNoRegistrado();
                session.setAttribute("lst_pnr", lst_pnr);                
                break;
            }
            case 28: {
                rbd.puntoNoRegistradoXVehiculo(pr);
                ArrayList<PuntoNoRegistrado> lst_pnrxv = rbd.getLstPuntoNoRegistradoXVehiculo();
                session.setAttribute("lst_pnrxv", lst_pnrxv);                
                break;
            }
            case 29: {
                rbd.puntoNoRegistradoXConductor(pr);
                ArrayList<PuntoNoRegistrado> lst_pnrxc = rbd.getLstPuntoNoRegistradoXConductor();
                session.setAttribute("lst_pnrxc", lst_pnrxc);
                break;
            }
            case 30: {
                String fecha_inicio = pr.getFechaInicioStr();
                String fecha_final  = pr.getFechaFinalStr();
                String sconductor   = pr.getListaConductores();
                ConfCalificacionConductor ccc = CalificacionConductorBD.confCalificacionConductor();
                
                ArrayList<RendimientoConductor> lst_rc = 
                    CalificacionConductorBD.calificar(fecha_inicio, fecha_final, sconductor, ccc);
                                
                session.setAttribute("lst_rc", lst_rc);
                session.setAttribute("ccc", ccc);
                break;
            }
        }
        
        String reporte_jsp = pr.getNombreReporte() + ".jsp";
        return "/app/reportes/" + reporte_jsp;
    }    
}
