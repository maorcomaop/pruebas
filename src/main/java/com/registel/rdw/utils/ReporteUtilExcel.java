/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.CalificacionConductorBD;
import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.InformacionRegistradoraBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.ReportesBD;
import com.registel.rdw.logica.ConfCalificacionConductor;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.MovilRuta;
import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.RendimientoConductor;
import com.registel.rdw.reportes.ProduccionXHora;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author lider_desarrollador
 */
public class ReporteUtilExcel {

    private MakeExcel rpte;
    
    // Inicia creacion de reporte(s) excel
    // (Usuario no es necesario especificarse)
    public MakeExcel crearReporte(int ntp, boolean dia_actual, ParametrosReporte pr, ConfiguracionLiquidacion etiquetas) {
        return crear(ntp, dia_actual, pr, null, etiquetas);
    }
    
    // Inicia creacion de reporte(s) excel, cuyos parametros
    // se encuentran en estructura hash (sin parametrizar)
    public MakeExcel crearReporte(int ntp, boolean dia_actual, Map<String, String> h, Usuario u, ConfiguracionLiquidacion etiquetas) {
        // Parametriza valores de consulta
        ParametrosReporte pr = new ParametrosReporte(h);
        return crear(ntp, dia_actual, pr, u, etiquetas);
    }
    
    // Crea reporte planilla despacho planificado
    public MakeExcel crearPlanillaDespacho(Map<String,String> h, ArrayList<Planilla> data) {
        ParametrosReporte pr = new ParametrosReporte(h);        
        MakeExcel rpte = new MakeExcel();
        rpte.planillaDespacho(data, pr, false);
        return rpte;
    }
    
    // Crea reporte planilla despacho a pedido
    public MakeExcel crearPlanillaAPedido(Map<String,String> h, ArrayList<Planilla> data) {
        ParametrosReporte pr = new ParametrosReporte(h);        
        MakeExcel rpte = new MakeExcel();
        rpte.planillaDespacho(data, pr, true);
        return rpte;
    }
    
    // Inicia creacion de reporte excel segun tipo y parametros especificados.
    public MakeExcel crear(int ntp, boolean dia_actual, ParametrosReporte pr, Usuario u, ConfiguracionLiquidacion etiquetas) {
        
        ReportesBD rbd = new ReportesBD();
        rpte = new MakeExcel();
        String tituloReporte   = pr.getTituloReporte();
        boolean cruzarDespacho = pr.cruzarDespacho();                
                
        switch (ntp) {
            case 1: {
                rbd.PuntosControl(pr);
                if (dia_actual) {
                    rbd.PuntosControl_GPS(pr);
                    rpte.reporte_PuntosControl_GPS(tituloReporte, rbd.getLstPuntosControlGPS());
                    rpte.reporte_PuntosControl(tituloReporte, rbd.getLstPuntosControl());
                } else {
                    rpte.reporte_PuntosControl(tituloReporte, rbd.getLstPuntosControl());
                }
                break;
            }
            case 2: {
                rbd.ProduccionXVehiculo(pr);
                rpte.reporte_ProduccionXVehiculo(tituloReporte, rbd.getLstProduccionXVehiculo());
                break;
            }
            case 3: {
                if (cruzarDespacho) {
                    rbd.RutaXVehiculoDph(pr);
                    rpte.reporte_RutaXVehiculoDph(tituloReporte, rbd.getLstRutaXVehiculoDph());
                    break;
                } else {
                    rbd.RutaXVehiculo(pr);
                    rpte.reporte_RutaXVehiculo(tituloReporte, rbd.getLstRutaXVehiculo());
                    break;
                }
            }
            case 4: {
                rbd.AlarmasXVehiculo(pr);
                rpte.reporte_AlarmasXVehiculo(tituloReporte, rbd.getLstAlarmaXVehiculo());
                break;
            }
            case 5: {
                rbd.NivelOcupacion(pr);
                rpte.reporte_NivelOcupacion(tituloReporte, rbd.getLstNivelOcupacion());
                break;
            }
            case 6: {
                rbd.ConteoPerimetro(pr);
                rpte.reporte_ConteoPerimetro(tituloReporte, rbd.getLstConteoPerimetro());
                break;
            }
            case 7: {
                rbd.ConsolidadoXEmpresa(pr);
                rpte.reporte_ConsolidadoXEmpresa(tituloReporte, rbd.getLstConsolidadoXEmpresa());
                break;
            }
            case 8: {
                rbd.ComparativoProduccionRuta(pr);
                rpte.reporte_ComparativoProduccionRuta(tituloReporte, rbd.getLstComparativoProduccionRuta());
                break;
            }
            case 9: {
                rbd.ProduccionXRuta(pr);
                rpte.reporte_ProduccionXRuta(tituloReporte, rbd.getLstProduccionXRuta());
                break;
            }
            case 11: {
                if (cruzarDespacho) {
                    rbd.VehiculosXRutaDph(pr);
                    rpte.reporte_VehiculosXRuta(tituloReporte, rbd.getLstVehiculoXRutaDph());
                } else {
                    rbd.VehiculosXRuta(pr);
                    rpte.reporte_VehiculosXRuta(tituloReporte, rbd.getLstVehiculoXRuta());                    
                }
                break;
            }
            case 12: {
                rbd.VehiculosXAlarma(pr);
                rpte.reporte_VehiculosXAlarma(tituloReporte, rbd.getLstVehiculoXAlarma());
                break;
            }
            case 13: {
                rbd.RutasEstablecidas(pr);
                rbd.VehiculosEstablecidos(pr);
                rbd.ConductoresEstablecidos(pr);
                rbd.ConductoresXVehiculo(pr);

                rpte.reporte_Estadistico_RE(rbd.getLstRutaEstablecida());
                rpte.reporte_Estadistico_VE(rbd.getLstVehiculoEstablecido());
                rpte.reporte_Estadistico_CE(rbd.getLstConductorEstablecido());
                rpte.reporte_Estadistico_CV(rbd.getLstConductorXVehiculos());
                break;
            }
            case 14: {
                rbd.DescripcionRuta(pr);
                rpte.reporte_DescripcionRuta(tituloReporte, rbd.getLstDescripcionRuta());
                break;
            }
            case 15: {
                rbd.Gerencia(pr); 
                if (ReporteUtil.esEmpresa(u.getNombreEmpresa(), ReporteUtil.EMPRESA_FUSACATAN)) {                    
                    rpte.reporte_GerenciaFusa(tituloReporte, rbd.getLstGerencia());
                } else {
                    rpte.reporte_Gerencia(tituloReporte, rbd.getLstGerencia());
                }
                break;
            }
            case 16: {
                rbd.Despachador_IndicesIR(pr);
                rpte.reporte_Despachador(tituloReporte, rbd.getLstDespachador());
                break;
            }
            case 17: {
                rbd.Propietario(pr);
                rpte.reporte_Propietario(tituloReporte, rbd.getLstPropietario());
                break;
            }
            case 18: {
                rbd.GerenciaXVehiculo(pr); 
                if (ReporteUtil.esEmpresa(u.getNombreEmpresa(), ReporteUtil.EMPRESA_FUSACATAN)) {
                    rpte.reporte_GerenciaXVehiculoFusa(tituloReporte, rbd.getLstGerenciaXVehiculo());
                } else {
                    rpte.reporte_GerenciaXVehiculo(tituloReporte, rbd.getLstGerenciaXVehiculo());
                }
                break;
            }
            case 19: {
                rbd.consolidadoLiquidacion(pr, u);
                rpte.reporte_ConsolidadoLiquidacion(tituloReporte, rbd.getLstConsolidadoLiquidacion(), pr, etiquetas);
                break;
            }
            case 20: {
                rbd.consolidadoVehiculosNoLiquidados(pr);
                rpte.reporte_ConsolidadoVehiculosNoLiquidados(tituloReporte, rbd.getLstConsolidadoVehiculosNoLiquidados(), pr);
                break;
            }
            case 21: {
                rbd.reporteLiquidacionXLiquidador(pr);
                rpte.reporte_LiquidacionXLiquidador(tituloReporte, rbd.getLstLiquidacionXLiquidador(), pr, etiquetas);
                break;
            }
            case 22: {
                rbd.reporteIndicePasajeroKilometro(pr);
                rpte.reporte_IPK(tituloReporte, rbd.getLstIPK(), pr, etiquetas);
                break;
            }
            case 23: {
                rbd.cumplimientoRutaConsolidado(pr);
                rpte.reporte_CumplimientoRutaConsolidado(tituloReporte, rbd.getLstCumplimientoRutaConsolidado(), pr);
                break;
            }
            case 24: {
                rbd.cumplimientoRutaXVehiculo(pr);
                rpte.reporte_CumplimientoRutaXVehiculo(tituloReporte, rbd.getLstCumplimientoRutaXVehiculo(), pr);
                break;
            }
            case 25: {
                rbd.cumplimientoRutaXConductor(pr);
                rpte.reporte_CumplimientoRutaXConductor(tituloReporte, rbd.getLstCumplimientoRutaXConductor(), pr);
                break;
            }         
            case 26: {
                rbd.consolidadoDespacho(pr);
                ArrayList<PlanillaDespacho> lst = rbd.getLstConsolidadoDespacho();                                
                ArrayList<Planilla> lst_pll = DespachoBD.formato_planillaXvuelta(lst);
                ArrayList<String> lst_pto   = ReporteUtil.extraerPuntosDespacho(lst);                                
                rpte.reporte_ConsolidadoDespacho(tituloReporte, lst_pll, lst_pto, pr);
                break;
            }
            case 27: {
                rbd.consultaDeLiquidacion(pr);
                rpte.consulta_De_Liquidacion(tituloReporte, rbd.getLstConsultadeLiquidacion(), pr, etiquetas);
                break;
            }
            case 30: {
                ConfCalificacionConductor ccc = CalificacionConductorBD.confCalificacionConductor();
                ArrayList<RendimientoConductor> lst_rc = 
                        CalificacionConductorBD.calificar(
                                pr.getFechaInicioStr(), 
                                pr.getFechaFinalStr(), 
                                pr.getListaConductores(), 
                                ccc);
                rpte.reporte_CalificacionConductor(tituloReporte, lst_rc, pr, ccc);
                break;
            }
            case 31: {
                String fecha = pr.getFechaInicioStr();
                
                rbd.produccionAgrupadoXHora(pr);
                ArrayList<ProduccionXHora> lst_pxh = rbd.getLstProduccionXHora();
                ArrayList<MovilRuta> lst_mr = InformacionRegistradoraBD.movilesEnRuta(fecha);
                Map<String, Movil> hmovil = MovilBD.selectMovilMap();                                
                
                if (lst_mr != null && lst_pxh != null) {
                    
                    // Se asocia numero interno a vehiculo
                    if (hmovil != null) {
                        for (int i = 0; i < lst_pxh.size(); i++) {                        
                            ProduccionXHora pxh = lst_pxh.get(i);
                            Movil m = hmovil.get(pxh.getPlaca().toUpperCase());
                            pxh.setNumeroInterno("NA");
                            if (m != null) {
                                pxh.setNumeroInterno(m.getNumeroInterno());
                            }
                        }
                    }
                    
                    // Se asocia ruta a vehiculo
                    for (int i = 0; i < lst_mr.size(); i++) {
                        MovilRuta mr = lst_mr.get(i);                        
                        String placa_mr = mr.getPlaca().toUpperCase();

                        for (int j = 0; j < lst_pxh.size(); j++) {
                            ProduccionXHora pxh = lst_pxh.get(j);                            
                            String placa = pxh.getPlaca().toUpperCase();

                            if (placa_mr.compareTo(placa) == 0) {
                                pxh.setIdRuta(mr.getIdRuta());
                                pxh.setNombreRuta(mr.getNombreRuta());                                
                            }
                        }
                    }
                    
                    // Se filtran vehiculos seleccionados
                    String movil_sel = pr.getListaVehiculosPlaca();                    
                    ArrayList<ProduccionXHora> lst_pxhf = new ArrayList<>();
                    
                    if (movil_sel != null && movil_sel != "") {
                        String lst_ms[] = movil_sel.split(",");
                    
                        for (int i = 0; i < lst_ms.length; i++) {
                            String placa_ms = lst_ms[i].toUpperCase();
                        
                            for (int j = 0; j < lst_pxh.size(); j++) {
                                ProduccionXHora pxh = lst_pxh.get(j);
                                String placa = pxh.getPlaca().toUpperCase();
                                if (placa_ms.contains(placa)) {
                                    lst_pxhf.add(pxh);
                                }
                            }
                        }
                    }
                    
                    rpte.reporte_ProduccionXHora(tituloReporte, lst_pxhf, pr);
                }
                break;
            }
            case 35: {
                rbd.vehiculos(pr);
                rbd.reporteCategoriasDescuentaPax(pr);
                rpte.consulta_categorias_descuentan_pasajeros(tituloReporte, rbd.getLstCat(), pr);
                break;
            }
        }

        return rpte;
    }
    
    
}
