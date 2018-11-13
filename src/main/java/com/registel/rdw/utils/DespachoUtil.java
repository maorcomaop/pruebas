/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.utils.Permutation;
import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.DespachoManualBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.ProgramacionRutaBD;
import com.registel.rdw.datos.RutaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Despacho;
import com.registel.rdw.logica.GrupoRodamiento;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.ProgramacionRuta;
import com.registel.rdw.logica.Rodamiento;
import com.registel.rdw.logica.RutaRodamiento;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author lider_desarrollador      
 */
public class DespachoUtil {
    
    private ArrayList<Despacho> lst_dph;
    private ArrayList<PlanillaDespacho> lst_pd;
    private String rutas;
    
    // Variables de referencia
    private int ref_nrotaciones = 1;
    private int ref_idgrupo = -1;
    
    // Inicia parametros para generacion de planilla despacho
    // para todas las rutas que tenga una configuracion asociada.
    public DespachoUtil() {
        // Obtiene listado de configuraciones de despacho
        lst_dph = DespachoBD.select();
        lst_pd  = new ArrayList<PlanillaDespacho>();        
        
        rutas = "";
        for (Despacho dph : lst_dph) {
            String ruta = "" + dph.getIdRuta();
            if (rutas.indexOf(ruta) >= 0)
                continue;
            rutas += (rutas == "")
                  ? ruta : "," + ruta;
        }
    }
    
    // Inicia parametros para generacion de planilla despacho
    // de una ruta configurada en especifico.
    public DespachoUtil(Despacho dph) {
        // Obtiene listado de configuraciones de despacho
        lst_dph = DespachoBD.select();
        lst_pd  = new ArrayList<PlanillaDespacho>();
        
        // Se filtra lista despacho por ruta de despacho seleccionado
        int idRuta = dph.getIdRuta();
        rutas = "" + idRuta;
        
        for (int i = lst_dph.size()-1; i >= 0; i--) {
            
            Despacho item_dph = lst_dph.get(i);
            int item_idRuta = item_dph.getIdRuta();
            
            if (item_idRuta != idRuta) {
                lst_dph.remove(i);
            }
        }
    }
    
    // Inicia generacion de planilla despacho manual para una ruta
    // y fecha en especifico.
    public void generarDespachoManual(Despacho dph, Date fecha_actual) {
        
        ArrayList<String> lst_diaFestivo = SelectBD.diasFestivo();
        String orden_dias[] = null; int dia_semana = 0;
        
        // Verifica si es dia festivo o no y consulta el orden de prioridad
        // de tipo dia (F D S LD LS LV) segun el dia especificado.
        boolean dia_festivo = esDiaFestivo(fecha_actual, lst_diaFestivo);
        if (dia_festivo) {
            orden_dias = ordenDias(DIA_FESTIVO);
        } else {
            dia_semana = TimeUtil.diaDeLaSemana(fecha_actual);
            orden_dias = ordenDias(dia_semana);
        }
        
        Despacho conf_dph = null; int idx;
        int id_ruta = dph.getIdRuta();
        
        // Se busca configuracion despacho para ruta y tipo de dia mas proximo
        // segun la prioridad.
        for (int i = 0; i < orden_dias.length; i++) {
            idx = buscarDespacho(id_ruta, orden_dias[i], lst_dph);
            if (idx > -1) {
                conf_dph = lst_dph.get(idx);
                break;
            }
        }
        
        // Verifica tipo dia para dia semana e inicia generacion de planilla.
        if (conf_dph != null) {                    
            String tipo_dia = conf_dph.getTipoDia();
            if (tipo_dia.equals("LD") ||
               (tipo_dia.equals("F")  && dia_festivo) ||
               (tipo_dia.equals("LS") && dia_semana != 7) ||
               (tipo_dia.equals("LV") && dia_semana != 7 && dia_semana != 6) ||
               (tipo_dia.equals("D")  && dia_semana == 7) ||
               (tipo_dia.equals("S")  && dia_semana == 6)) {                        
               
               // Se asignan variables obtenidas por interfaz
               // movil - hora inicio - inicia punto retorno
               conf_dph.setGrupoMoviles(dph.getGrupoMoviles());
               conf_dph.setHoraInicio(dph.getHoraInicio());               
               boolean ipr = conf_dph.iniciaEnPuntoRetorno() && dph.iniciaEnPuntoRetorno();              
               conf_dph.setIniciaEnPuntoRetorno(ipr);
               
                if (ipr) System.out.println("Inicia pto ret");
                else System.out.println("No inicia pto ret");
               
               inicioGeneracionDespachoManual(conf_dph, fecha_actual);
            }
        }
    }
       
    public void generarDespacho(Date fecha_inicio, Date fecha_final) {
        
        ArrayList<Integer> lst_idRuta     = RutaBD.selectId();
        ArrayList<String> lst_diasFestivo = SelectBD.diasFestivo();
        int id_ruta, dia_semana = 0;
        
        for (int i = 0; i < lst_idRuta.size(); i++) {
            id_ruta           = lst_idRuta.get(i);                        
            Date fecha_actual = fecha_inicio;            
            ref_nrotaciones   = 1;
            ref_idgrupo       = -1;
            
            do {
                
                // Verifica si es dia festivo o no y consulta el orden de prioridad
                // de tipo dia (F D S LD LS LV) segun el dia especificado.
                String orden_dias[] = null;
                boolean dia_festivo = esDiaFestivo(fecha_actual, lst_diasFestivo);
                if (dia_festivo) {
                    orden_dias = ordenDias(DIA_FESTIVO);
                } else {
                    dia_semana = TimeUtil.diaDeLaSemana(fecha_actual);
                    orden_dias = ordenDias(dia_semana);
                }
                
                // Se busca configuracion despacho para ruta y tipo de dia mas proximo
                // segun la prioridad.
                Despacho conf_dph = null; int idx;
                for (int j = 0; j < orden_dias.length; j++) {
                    idx = buscarDespacho(id_ruta, orden_dias[j], lst_dph);
                    if (idx > -1) {
                        conf_dph = lst_dph.get(idx);
                        break;
                    }
                }
                
                // Verifica tipo dia para dia semana e inicia generacion de planilla.
                if (conf_dph != null) {                    
                    String tipo_dia = conf_dph.getTipoDia();
                    if (tipo_dia.equals("LD") ||
                       (tipo_dia.equals("F")  && dia_festivo) ||
                       (tipo_dia.equals("LS") && dia_semana != 7) ||
                       (tipo_dia.equals("LV") && dia_semana != 7 && dia_semana != 6) ||
                       (tipo_dia.equals("D")  && dia_semana == 7) ||
                       (tipo_dia.equals("S")  && dia_semana == 6)) {                        
                        inicioGeneracionDespachoRetorno(conf_dph, fecha_actual);                        
                    }
                }
                               
                fecha_actual = TimeUtil.sumarDia(fecha_actual);                
                
            } while (!TimeUtil.fechasIguales(fecha_actual, fecha_final));
        }
    }
    
    public void inicioGeneracionDespachoRetorno(
                    Despacho dph,
                    Date fecha_actual) {
        
        boolean moviles_retorno = dph.movilesEnRetorno();
        
        if (dph.getProgramarRuta() == 1) {
            
            int numeroMovilesRetorno      = dph.getCantidadMovilesRetorno();            
            ArrayList<String> lst_mov     = grupoMovilPorProgramacion(dph, fecha_actual);
            ArrayList<String> lst_mov_ret = new ArrayList<String>();
            boolean hayMovilesRetorno     = false;
            
            // Si el grupo asignado a la ruta es constante (no cambia)
            // Se recupera dicho grupo con su ultimo orden
            int idgrupo_act  = dph.getIdGrupoMoviles();
            int idgrupo_prev = ref_idgrupo;
            if (idgrupo_prev > 0 && idgrupo_act == idgrupo_prev) {
                lst_mov     = dph.getListaGrupoMoviles();
                lst_mov_ret = dph.getListaGrupoMovilesRetorno();                
                hayMovilesRetorno  = true;
            } else {
                dph.setListaGrupoMoviles(lst_mov);
                dph.setListaGrupoMovilesRetorno(lst_mov_ret);
            }                        
            
            // La totalidad de moviles en ruta es la suma de los vehiculos
            // en ambas bases
            int numeroTotalMoviles = lst_mov.size() + lst_mov_ret.size();
            
            // Genera planilla despacho sin moviles en retorno
            if (numeroMovilesRetorno == 0) {
                moviles_retorno = false;
                generarDespachoRetorno(dph, fecha_actual, lst_mov, 1, false);
            
            // Genera planilla despacho con moviles en retorno
            } else if (numeroMovilesRetorno >= numeroTotalMoviles) {
                moviles_retorno = true;
                generarDespachoRetorno(dph, fecha_actual, lst_mov, 1, true);
            
            // Asigna moviles en retorno y genera planilla
            } else {
                if (!hayMovilesRetorno) {
                    // Selecciona moviles para iniciar en punto retorno desde lista de moviles
                    lst_mov_ret = obtenerMovilesRetorno(lst_mov, numeroMovilesRetorno);
                }
                moviles_retorno = true;                
                int nplanilla = generarDespachoRetorno(dph, fecha_actual, lst_mov, 1, false);
                generarDespachoRetorno(dph, fecha_actual, lst_mov_ret, nplanilla, true);                
            }
            
            ref_idgrupo = dph.getIdGrupoMoviles();
            dph.setListaGrupoMoviles(lst_mov);
            dph.setListaGrupoMovilesRetorno(lst_mov_ret);            
            
        } else {
            
            // Inicia generacion de planilla sin programacion de ruta
            // (Moviles listados directamente en configuracion)
            if (moviles_retorno) {                
                ArrayList<String> lst_mov     = dph.getListaGrupoMoviles();
                ArrayList<String> lst_mov_ret = dph.getListaGrupoMovilesRetorno();
                int nplanilla = generarDespachoRetorno(dph, fecha_actual, lst_mov, 1, false);
                generarDespachoRetorno(dph, fecha_actual, lst_mov_ret, nplanilla, true);                
            } else {
                ArrayList<String> lst_mov = dph.getListaGrupoMoviles();
                generarDespachoRetorno(dph, fecha_actual, lst_mov, 1, false);                                
            }
        }        
        
        // Rota los vehiculos segun el tipo de rotacion especificado
        if (dph.getRotarVehiculo() == 1) {
            rotacionLineal(dph, moviles_retorno);
        } else {
            rotacionAleatoria(dph, moviles_retorno);
        }        
    }
    
    // Extrae cierta cantidad nmov de moviles de listado y conforma nueva lista
    // que iniciaran en punto retorno.
    public ArrayList<String> obtenerMovilesRetorno(ArrayList<String> lst, int nmov) {
        
        ArrayList<String> nlst = new ArrayList<String>();       
        nmov = (nmov >= lst.size()) ? lst.size()-1 : nmov;
        
        for (int i = nmov; i >= 1; i--) {
            String item = lst.remove(i);
            nlst.add(item);
        }
        return nlst;
    }
    
    // Extrae listado de moviles (grupo) para un tipo de programacion y ruta
    // asociado a una configuracion de despacho.
    public ArrayList<String> grupoMovilPorProgramacion(Despacho dph, Date fecha_actual) {
        
        String tipopg = dph.getTipoProgramacionRuta().toUpperCase();
        ArrayList<String> lst_placas;
        
        if (tipopg.compareTo("S") == 0) {

            // Programacion de ruta por semana (Uso de tabla programacion ruta plana)
            int dia_semana = TimeUtil.diaDeLaSemana(fecha_actual);
            int id_grupo   = DespachoBD.select_idgrupomovil_pgruta(dph.getIdRuta(), dia_semana);
            lst_placas     = DespachoBD.select_grupomovil_pgruta(id_grupo);
            dph.setIdGrupoMoviles(id_grupo);

        } else {

            // Programacion de ruta por mes (Uso de rodamiento)
            int mes = TimeUtil.mes(fecha_actual);
            int dia_mes  = TimeUtil.diaDelMes(fecha_actual);
            int id_grupo = grupoEnRodamiento(dph.getIdRuta(), mes, dia_mes);            
            lst_placas   = DespachoBD.select_grupomovil_pgruta(id_grupo);
            dph.setIdGrupoMoviles(id_grupo);
        }
        
        return lst_placas;
    }
    
    // Hace rotacion lineal de vehiculos con o sin punto de retorno,
    // para una configuracion de despacho especifico.
    public void rotacionLineal(Despacho dph, boolean punto_retorno) {
        
        if (punto_retorno) {
            ArrayList<String> lst_mov     = rotarVehiculos(dph.getListaGrupoMoviles());
            ArrayList<String> lst_mov_ret = rotarVehiculos(dph.getListaGrupoMovilesRetorno());
            dph.setListaGrupoMoviles(lst_mov_ret);
            dph.setListaGrupoMovilesRetorno(lst_mov);
        } else {
            ArrayList<String> lst_mov = rotarVehiculos(dph.getListaGrupoMoviles());
            dph.setListaGrupoMoviles(lst_mov);
        }
    }
    
    // Hace rotacion interna de moviles en listados respectivos,
    // siempre que no se exceda el limite de conservacion, de lo
    // contrario si hay punto retorno se intercambian listados.
    public void rotacionAleatoria(Despacho dph, boolean punto_retorno) {
        
        Permutation p = new Permutation();     
        int limite_conservacion = dph.getLimiteConservacion();
        limite_conservacion = 3;
        
        if (punto_retorno) {
            
            if (ref_nrotaciones >= limite_conservacion) {                
                ArrayList<String> lst_mov     = dph.getListaGrupoMoviles();
                ArrayList<String> lst_mov_ret = dph.getListaGrupoMovilesRetorno();
                // Intercambio de moviles entre listas (cambio en punto retorno)
                p.swap(lst_mov, lst_mov_ret);
                lst_mov     = p.resetSuccessor(lst_mov);
                lst_mov_ret = p.resetSuccessor(lst_mov_ret);                
                dph.setListaGrupoMoviles(lst_mov);
                dph.setListaGrupoMovilesRetorno(lst_mov_ret);
                ref_nrotaciones = 1;
            } else {
                ArrayList<String> lst_mov     = dph.getListaGrupoMoviles();
                ArrayList<String> lst_mov_ret = dph.getListaGrupoMovilesRetorno();
                dph.setListaGrupoMoviles(p.nextOrder(lst_mov));
                dph.setListaGrupoMovilesRetorno(p.nextOrder(lst_mov_ret));
                ref_nrotaciones += 1;
            }                    
            
        } else {
            
            // Sin punto retorno
            ArrayList<String> lst_mov = dph.getListaGrupoMoviles();
            if (ref_nrotaciones >= limite_conservacion) {
                dph.setListaGrupoMoviles(p.resetSuccessor(lst_mov));
                ref_nrotaciones = 1;
            } else {
                dph.setListaGrupoMoviles(p.nextOrder(lst_mov));
                ref_nrotaciones += 1;
            }
        }
    }
    
    // Se inicia generacion de planilla despacho manual
    // para una configuracion y fecha especifica.
    public void inicioGeneracionDespachoManual(Despacho dph, Date fecha_actual) {
        
        String placa_movil    = dph.getGrupoMoviles();
        int id_ruta           = dph.getIdRuta();
        boolean punto_retorno = dph.iniciaEnPuntoRetorno();
        
        // Asigna numero de planilla y de vuelta sgte 
        // para la actual generacion de planilla
        int numero_planilla = DespachoManualBD.numero_planilla(fecha_actual, id_ruta, placa_movil);
        int numero_vuelta   = DespachoManualBD.ultimo_numero_vuelta(id_ruta, fecha_actual, placa_movil);
        numero_vuelta       += 1;
        
        // Se ajusta cadena movil a lista de cadenas
        ArrayList<String> lst_placas = new ArrayList<String>();
        lst_placas.add(placa_movil);
        
        dph.setNumeroVuelta(numero_vuelta);
        dph.setDespachoManual(true);
        
        generarDespachoRetornoManual(
                dph,
                fecha_actual,
                lst_placas,
                numero_planilla,
                punto_retorno
        );
    }
    
    // Genera planilla despacho con o sin retorno, para un listado de
    // moviles, fecha y configuracion especifica.
    public int generarDespachoRetorno(
                    Despacho dph,
                    Date fecha_actual,
                    ArrayList<String> lst_placas,
                    int numero_planilla,
                    boolean punto_retorno) {
	
        ArrayList<String[]> lst_puntos = dph.getListaPuntosRuta();
        ArrayList<String> lst_horapico = dph.getListaHoraPico();
        
        Time hora_inicio, hora_final;
        int tiempo_salida;
        
        hora_final                = dph.getHoraFin();
        String spunto_retorno     = dph.getPuntoRetorno();
        int tiempo_ajuste_vuelta1 = dph.getTiempoAjusteRetorno();        
        int horas_trabajo         = dph.getHorasTrabajo();                
        
        // Asigna tiempo de salida en base a si es punto retorno o no
	if (punto_retorno) {
            hora_inicio   = dph.getHoraInicioRetorno();            
            tiempo_salida = dph.getTiempoSalidaRetorno();
	} else {
            hora_inicio   = dph.getHoraInicio();
            tiempo_salida = dph.getTiempoSalida();
        }
        
        // Calcula hora limite de trabajo
        Time hora_limite  = TimeUtil.sumarMinutos(hora_inicio, horas_trabajo * 60);
        
        if (lst_placas == null || lst_placas.size() == 0) return 1;
        
        for (int i = 0; i < lst_placas.size(); i++) {
            String placa = lst_placas.get(i);
            
            // Verifica si movil tiene dia laboral
            Movil movil = MovilBD.select(placa);
            if (!esDiaLaboral(movil, fecha_actual))
                continue;
            
            // Verifica si ya se ha alcanzado la hora final del despacho establecido
            if (TimeUtil.alcanceHora(hora_inicio, hora_final))
                return numero_planilla;       
            
            Time tiempo_punto     = hora_inicio;
            Time hora_inicio_ruta = hora_inicio;
            Time hora_final_ruta  = hora_final;
            
            int tiempo_reciente    = -1,
                tiempo_retorno     = 0,
                tiempo_intervalo   = 0,
                tiempo_inicio_ruta = 0;
            
            int numero_vuelta = dph.getNumeroVuelta();
            do {
                
                // Se verifica si se ha cumplido la cantidad de horas laborales                
                if (TimeUtil.alcanceHora(hora_inicio_ruta, hora_limite)) break;
                
                boolean vieneHoraPico         = false;            
                boolean alcanzaPuntoRetorno   = false;                
                int nptos = lst_puntos.size();
                
                for (int j = 0; j < nptos; j++) {
                    
                    String punto = lst_puntos.get(j)[0];
                    int tiempo_holgura = Restriction.getNumber(lst_puntos.get(j)[1]);
                    int tiempo_valle   = Restriction.getNumber(lst_puntos.get(j)[2]);
                    int tiempo_pico    = Restriction.getNumber(lst_puntos.get(j)[3]);
                    
                    // Asigna tiempo del punto y actual/reciente segun si se encuentra
                    // en franja de hora pico o no.
                    if (vieneHoraPico) {
                        tiempo_punto    = TimeUtil.sumarMinutos(hora_inicio_ruta, tiempo_pico);
                        tiempo_reciente = tiempo_pico;
                    } else {
                        vieneHoraPico = enHoraPico(tiempo_punto, lst_horapico);
                        tiempo_punto  = (vieneHoraPico)
                                ? TimeUtil.sumarMinutos(hora_inicio_ruta, tiempo_pico)
                                : TimeUtil.sumarMinutos(hora_inicio_ruta, tiempo_valle);
                        tiempo_reciente = (vieneHoraPico) ? tiempo_pico : tiempo_valle;
                    }                                                                                
                    
                    PlanillaDespacho pd = new PlanillaDespacho();
                    pd.setIdConf(dph.getId());
                    pd.setIdRuta(dph.getIdRuta());
                    pd.setNombreRuta(dph.getNombreRuta());
                    pd.setFecha(fecha_actual);
                    pd.setPlaca(placa);
                    pd.setPunto(punto);
                    pd.setHoraPlanificada(tiempo_punto);
                    pd.setHolguraPunto(tiempo_holgura);
                    pd.setNumeroPlanilla(numero_planilla);
                    pd.setNumeroVuelta(numero_vuelta);                    
                    
                    // Asigna tipo de punto 1 base-salida, 2 punto, 3 base-llegada
                    int tipoPunto = (j == 0) ? 1 : 
                                    (j == nptos-1) ? 3 : 2;                                    
                    if (tipoPunto == 1) { tiempo_inicio_ruta = tiempo_reciente; }
                    pd.setTipoPunto(tipoPunto);                                        
                    
                    // Actualiza tiempo de punto en base a si hubo
                    // punto retorno o no
                    if (punto_retorno) {                        
                        
                        if (numero_vuelta == 1 &&
                            alcanzaPuntoRetorno) {
                            // Se calcula diferencia de tiempo con respecto a punto retorno en primer vuelta
                            tiempo_punto     = TimeUtil.sumarMinutos(hora_inicio_ruta, (tiempo_reciente - tiempo_retorno));
                            tiempo_intervalo = (tiempo_reciente - tiempo_retorno);
                        
                        } else if (numero_vuelta == 2) {
                            // Se aplica ajuste de segunda vuelta con tiempo de ajuste
                            tiempo_punto = TimeUtil.sumarMinutos(tiempo_punto, tiempo_ajuste_vuelta1);
                        
                        } else if (numero_vuelta >= 3) {
                            // A partir de tercer vuelta se hace uso del tiempo salida corriente
                            tiempo_punto = TimeUtil.sumarMinutos(tiempo_punto, tiempo_salida);
                        }
                    } else {                       
                        if (numero_vuelta >= 2) {
                            tiempo_punto = TimeUtil.sumarMinutos(tiempo_punto, tiempo_salida);
                        }
                    }
                    
                    // Verifica si es punto retorno en primera vuelta, 
                    // y hace ajuste de tiempos y estados necesarios.
                    if (numero_vuelta == 1 && punto_retorno) {
                        if (!alcanzaPuntoRetorno) {
                            if (spunto_retorno.indexOf(punto) >= 0) {
                                // Si es punto retorno se actualiza su tiempo
                                // y se toma como referencia para calculos 
                                // de los puntos sucesores en la primera vuelta
                                alcanzaPuntoRetorno = true;
                                tiempo_punto     = hora_inicio;
                                tiempo_retorno   = tiempo_reciente;
                                tiempo_intervalo = tiempo_inicio_ruta;
                                pd.setTipoPunto(1);
                            } else {
                                // Puntos no controlados antes del retorno se inhabilitan
                                // (Si salida es desde este)                                
                                pd.setTipoPunto(0);
                                pd.setEstadoDespacho(-1);                                
                                tiempo_punto = TimeUtil.horaCero();
                                tiempo_intervalo = 0;
                            }
                        }
                    } else {
                        tiempo_intervalo = tiempo_reciente;
                    }
                    
                    pd.setHoraPlanificada(tiempo_punto);                    
                    pd.setIntervaloLlegada(tiempo_intervalo);                    
                    
                    lst_pd.add(pd);
                }
                
                hora_inicio_ruta = tiempo_punto;
                numero_vuelta += 1;
                
                // Finalizamos en primera vuelta realizada, si es despacho manual
                if (dph.despachoManual()) return 0;
                                
            } while (!TimeUtil.alcanceHora(hora_inicio_ruta, hora_final_ruta));
            
            // Asigna hora de inicio/salida de siguiente vuelta en base a los intervalos
            // especificados.
            if (punto_retorno) {
                hora_inicio = TimeUtil.sumarMinutos(hora_inicio, dph.getIntervaloRetorno());
            } else {
                hora_inicio = (enHoraPico(hora_inicio, lst_horapico))
                        ? TimeUtil.sumarMinutos(hora_inicio, dph.getIntervaloPico())
                        : TimeUtil.sumarMinutos(hora_inicio, dph.getIntervaloValle());
            }
            
            numero_planilla += 1;
        }
        return numero_planilla;
    }
    
    public int generarDespachoRetornoManual(
                    Despacho dph,
                    Date fecha_actual,
                    ArrayList<String> lst_placas,
                    int numero_planilla,
                    boolean punto_retorno) {
	
	ArrayList<String[]> lst_puntos = dph.getListaPuntosRuta();
	ArrayList<String> lst_horapico = dph.getListaHoraPico();
	
	Time hora_inicio, hora_final;
	int tiempo_salida;
	
	hora_inicio           = dph.getHoraInicio();
	hora_final            = dph.getHoraFin();                
	String spunto_retorno = dph.getPuntoRetorno();	
	int horas_trabajo     = dph.getHorasTrabajo();   
        tiempo_salida         = (punto_retorno) 
                                ? dph.getTiempoSalidaRetorno() 
                                : dph.getTiempoSalida();
	
	//Time hora_limite  = TimeUtil.sumarMinutos(hora_inicio, horas_trabajo * 60);
	
	if (lst_placas == null || lst_placas.size() == 0) return 1;
	
	for (int i = 0; i < lst_placas.size(); i++) {
		
            String placa = lst_placas.get(i);

            // Verifica si movil tiene dia laboral
            Movil movil = MovilBD.select(placa);
            if (!esDiaLaboral(movil, fecha_actual))
                continue;

            // Verifica si ya se ha alcanzado la hora final del despacho establecido
            if (TimeUtil.alcanceHora(hora_inicio, hora_final))
                return numero_planilla;

            Time tiempo_punto     = hora_inicio;
            Time hora_inicio_ruta = hora_inicio;

            int tiempo_reciente    = -1,
                tiempo_retorno     = 0,
                tiempo_intervalo   = 0,
                tiempo_inicio_ruta = 0;

            int numero_vuelta = dph.getNumeroVuelta();

            // Se verifica si se ha cumplido la cantidad de horas laborales                
            //if (TimeUtil.alcanceHora(hora_inicio_ruta, hora_limite)) break;

            boolean vieneHoraPico         = false;            
            boolean alcanzaPuntoRetorno   = false;                
            int nptos = lst_puntos.size();

            for (int j = 0; j < nptos; j++) {

                String punto       = lst_puntos.get(j)[0];
                int tiempo_holgura = Restriction.getNumber(lst_puntos.get(j)[1]);
                int tiempo_valle   = Restriction.getNumber(lst_puntos.get(j)[2]);
                int tiempo_pico    = Restriction.getNumber(lst_puntos.get(j)[3]);

                // Asigna tiempo del punto y actual/reciente segun si se encuentra
                // en franja de hora pico o no.
                if (vieneHoraPico) {
                    tiempo_punto    = TimeUtil.sumarMinutos(hora_inicio_ruta, tiempo_pico);
                    tiempo_reciente = tiempo_pico;
                } else {
                    vieneHoraPico = enHoraPico(tiempo_punto, lst_horapico);
                    tiempo_punto  = (vieneHoraPico)
                                    ? TimeUtil.sumarMinutos(hora_inicio_ruta, tiempo_pico)
                                    : TimeUtil.sumarMinutos(hora_inicio_ruta, tiempo_valle);
                    tiempo_reciente = (vieneHoraPico) ? tiempo_pico : tiempo_valle;
                }                                                                                

                PlanillaDespacho pd = new PlanillaDespacho();
                pd.setIdConf(dph.getId());
                pd.setIdRuta(dph.getIdRuta());
                pd.setNombreRuta(dph.getNombreRuta());
                pd.setFecha(fecha_actual);
                pd.setPlaca(placa);
                pd.setPunto(punto);
                pd.setHoraPlanificada(tiempo_punto);
                pd.setHolguraPunto(tiempo_holgura);
                pd.setNumeroPlanilla(numero_planilla);
                pd.setNumeroVuelta(numero_vuelta);                    

                // Asigna tipo de punto 1 base-salida, 2 punto, 3 base-llegada
                int tipoPunto = (j == 0) ? 1 : 
                                (j == nptos-1) ? 3 : 2;                                    
                if (tipoPunto == 1) { tiempo_inicio_ruta = tiempo_reciente; }
                pd.setTipoPunto(tipoPunto);                                        

                // Actualiza tiempo de punto en base a si hubo
                // punto retorno o no
                if (punto_retorno) {                        
                    if (alcanzaPuntoRetorno) {
                        // Se calcula diferencia de tiempo con respecto a punto retorno
                        tiempo_punto     = TimeUtil.sumarMinutos(hora_inicio_ruta, (tiempo_reciente - tiempo_retorno));
                        tiempo_intervalo = (tiempo_reciente - tiempo_retorno);				
                    } 
                } else {                       
                    if (numero_vuelta >= 2) {
                        tiempo_punto = TimeUtil.sumarMinutos(tiempo_punto, tiempo_salida);
                    }
                }

                // Verifica si es punto retorno y hace ajuste 
                // de tiempos y estados necesarios.
                if (punto_retorno) {
                    if (!alcanzaPuntoRetorno) {
                        if (spunto_retorno.indexOf(punto) >= 0) {
                            // Si es punto retorno se actualiza su tiempo
                            // y se toma como referencia para calculos 
                            // de los puntos sucesores en la primera vuelta
                            alcanzaPuntoRetorno = true;
                            tiempo_punto     = hora_inicio;
                            tiempo_retorno   = tiempo_reciente;
                            tiempo_intervalo = tiempo_inicio_ruta;
                            pd.setTipoPunto(1);
                        } else {
                            // Puntos no controlados antes del retorno se inhabilitan
                            // (Si salida es desde este)                                
                            pd.setTipoPunto(0);
                            pd.setEstadoDespacho(-1);                                
                            tiempo_punto = TimeUtil.horaCero();
                            tiempo_intervalo = 0;
                        }
                    }
                } else {
                    tiempo_intervalo = tiempo_reciente;
                }

                pd.setHoraPlanificada(tiempo_punto);                    
                pd.setIntervaloLlegada(tiempo_intervalo);                    

                lst_pd.add(pd);
            }
	}
		
	return 0;
    }
    
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    // Crea rodamiento (asignacion de grupo moviles a ruta) reiniciando
    // orden de rotacion cada semana.
    public ArrayList<Rodamiento> rodamientoSemana(int mes) {
        
        Date fecha = TimeUtil.fechaEnMes(mes);
        ArrayList<ProgramacionRuta> lst_pgr = ProgramacionRutaBD.select();
        ArrayList<Rodamiento> lst_rod = new ArrayList<Rodamiento>();
        
        while (!TimeUtil.cambioMes(fecha, mes)) {
            
            int dia_semana = TimeUtil.diaDeLaSemana(fecha);
            int dia_mes    = TimeUtil.diaDelMes(fecha);
            // Obtiene asignacion de grupo moviles para dia de semana especifico.
            Rodamiento rod = creaRodamiento(lst_pgr, dia_semana, dia_mes);
            lst_rod.add(rod);
            
            fecha = TimeUtil.sumarDia(fecha);
        }
        return lst_rod;
    }
    
    // Crea rodamiento (asignacion de grupo moviles a ruta) reiniciando
    // orden de rotacion cada mes.
    public ArrayList<Rodamiento> rodamientoMes(int mes) {
        
        Date fecha = TimeUtil.fechaEnMes(mes);        
        ArrayList<ProgramacionRuta> lst_pgr = ProgramacionRutaBD.select();
        ArrayList<Rodamiento> lst_rod = new ArrayList<Rodamiento>();               
        
        int dia_semana_prev = 0; boolean nueva_semana = false;
        while (!TimeUtil.cambioMes(fecha, mes)) {
            
            int dia_semana = TimeUtil.diaDeLaSemana(fecha);
            int dia_mes    = TimeUtil.diaDelMes(fecha);
    
            // Si comienza nueva semana (rotar rodamiento)
            if (dia_semana < dia_semana_prev || nueva_semana)
                nueva_semana = true;
            
            // Una vez se crea el rodamiento por primera vez, las
            // siguientes asignaciones seran en base al rodamiento previo.
            // Es decir, se hace una rotacion del rodamiento anterior.
            Rodamiento rod = null;            
            if (nueva_semana) {
                rod = rotaRodamiento(lst_rod, dia_semana, dia_mes);                
            } else {
                rod = creaRodamiento(lst_pgr, dia_semana, dia_mes);
            }
            lst_rod.add(rod);
            
            dia_semana_prev = dia_semana;
            fecha = TimeUtil.sumarDia(fecha);
        }        
        
        return lst_rod;
    }    
    
    // Crea rodamiento en que se asigna grupos de moviles 
    // a rutas segun dia de semana especificado.
    public Rodamiento creaRodamiento(ArrayList<ProgramacionRuta> lst_pgr,
                                     int dia_semana,
                                     int dia_mes) {
        
        Rodamiento rod = new Rodamiento(); 
        
        ArrayList<RutaRodamiento> lst_ruta  = new ArrayList<RutaRodamiento>();
        ArrayList<GrupoRodamiento> lst_grupo = new ArrayList<GrupoRodamiento>();      
        
        rod.setDia(dia_mes);
        
        for (ProgramacionRuta pgr : lst_pgr) {
            
            lst_ruta.add(new RutaRodamiento(
                                pgr.getIdRuta(),
                                pgr.getNombreRuta()));            
            
            switch (dia_semana) {
                case TimeUtil.LUN: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_lun(), pgr.getNgrupo_lun())); rod.setNombreDia("LUN"); break; }
                case TimeUtil.MAR: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_mar(), pgr.getNgrupo_mar())); rod.setNombreDia("MAR"); break; }
                case TimeUtil.MIE: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_mie(), pgr.getNgrupo_mie())); rod.setNombreDia("MIE"); break; }
                case TimeUtil.JUE: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_jue(), pgr.getNgrupo_jue())); rod.setNombreDia("JUE"); break; }
                case TimeUtil.VIE: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_vie(), pgr.getNgrupo_vie())); rod.setNombreDia("VIE"); break; }
                case TimeUtil.SAB: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_sab(), pgr.getNgrupo_sab())); rod.setNombreDia("SAB"); break; }
                case TimeUtil.DOM: { lst_grupo.add(new GrupoRodamiento(pgr.getGrupo_dom(), pgr.getNgrupo_dom())); rod.setNombreDia("DOM"); break; }
            }                                                
        }
        
        rod.setLst_ruta(lst_ruta);
        rod.setLst_grupo(lst_grupo);        
        
        return rod;
    }
    
    // Crea rodamiento con base en rodamiento previo (dia anterior).
    // Los grupos de moviles son rotados antes de su asignacion.
    public Rodamiento rotaRodamiento(ArrayList<Rodamiento> lst_rod,
                                    int dia_semana,
                                    int dia_mes) {
        
        int last_index = lst_rod.size() - 1;
        Rodamiento rod = new Rodamiento();
        Rodamiento rod_prev = lst_rod.get(last_index);
        
        ArrayList<GrupoRodamiento> lst = rod_prev.getLst_grupo();
        ArrayList<GrupoRodamiento> lst_grupo = new ArrayList<GrupoRodamiento>(lst.size());
        for (GrupoRodamiento grupo : lst) {
            lst_grupo.add(new GrupoRodamiento(grupo));
        }
        
        String nombre_dia = "";
        switch (dia_semana) {
            case TimeUtil.LUN: { nombre_dia = "LUN"; break; }
            case TimeUtil.MAR: { nombre_dia = "MAR"; break; }
            case TimeUtil.MIE: { nombre_dia = "MIE"; break; }
            case TimeUtil.JUE: { nombre_dia = "JUE"; break; }
            case TimeUtil.VIE: { nombre_dia = "VIE"; break; }
            case TimeUtil.SAB: { nombre_dia = "SAB"; break; }
            case TimeUtil.DOM: { nombre_dia = "DOM"; break; }
        }                                                
        
        rod.setDia(dia_mes);
        rod.setNombreDia(nombre_dia);
        rod.setLst_ruta(rod_prev.getLst_ruta());
        // Grupos se rotan linealmente antes de asignacion.
        rod.setLst_grupo(rotarGrupo(lst_grupo));
        
        return rod;
    }
    
    // Extrae grupo movil asignado en ruta y fecha especifico.
    public int grupoEnRodamiento(int idRuta, int mes, int dia_mes) {
        
        // Genera rodamiento en mes especificado.
        ArrayList<Rodamiento> lst_rod
                = rodamientoMes(mes);
        
        for (Rodamiento rod : lst_rod) {
            if (rod.getDia() == dia_mes) {
                ArrayList<RutaRodamiento> lst_ruta = rod.getLst_ruta();
                for (int i = 0; i < lst_ruta.size(); i++) {
                    RutaRodamiento rr = lst_ruta.get(i);
                    if (rr.getIdRuta() == idRuta) {
                        ArrayList<GrupoRodamiento> lst_grupo = rod.getLst_grupo();
                        GrupoRodamiento gr = lst_grupo.get(i);
                        return gr.getIdGrupo();
                    }
                }
            }
        }
        return -1;
    }
    
    // Obtiene posicion en listado de configuracion despacho para
    // una ruta y tipo dia especifico.
    public int buscarDespacho(int idRuta, 
                              String tipo_dia, 
                              ArrayList<Despacho> lst_dph) {
        
        for (int i = 0; i < lst_dph.size(); i++) {
            Despacho dph = lst_dph.get(i);
            if (dph.getIdRuta() == idRuta &&
                dph.getTipoDia().compareTo(tipo_dia) == 0)
                return i;
        } 
        return -1;
    }
    
    // Comprueba si hora especificada se encuentra en un intervalo de hora pico.
    public boolean enHoraPico(Time hora, ArrayList<String> lst_horapico) {
        
        if (lst_horapico != null) {
            for (int i = 0; i < lst_horapico.size(); i++) {                
                String hora_pico[] = lst_horapico.get(i).split("-");
                if (hora_pico.length == 2 && 
                    TimeUtil.entreHoras(hora, hora_pico[0], hora_pico[1]))
                    return true;
            } 
        }
        return false;
    }
    
    // Comprueba si dia especificado se encuentra en listado de dias festivos.
    public boolean esDiaFestivo(Date dia, ArrayList<String> lst_dias_festivos) {
        
        String sdia = new SimpleDateFormat("yyyy-MM-dd").format(dia);
        if (lst_dias_festivos != null) {
            for (int i = 0; i < lst_dias_festivos.size(); i++) {                
                if (sdia.compareTo(lst_dias_festivos.get(i)) == 0) {                    
                    return true;
                }
            }
        } return false;
    }
    
    // Comprueba si movil en fecha especificada esta asignado como
    // dia de descanso o pico y placa.
    public boolean esDiaLaboral(Movil m, Date fecha_actual) {
        
        String diaPicoPlaca = m.getDiaPicoplaca();
        String diaDescanso  = m.getDiaDescanso();        
        int dia_semana = TimeUtil.diaDeLaSemana(fecha_actual);
        int dia_mes    = TimeUtil.diaDelMes(fecha_actual);
        
        if (diaPicoPlaca != null && diaPicoPlaca != "") {
            String dia_pp[] = diaPicoPlaca.split(",");
            for (int i = 0; i < dia_pp.length; i++) {
                if (dia_semana == Restriction.getNumber(dia_pp[i]))
                    return false;
            }
        }
        
        if (diaDescanso != null && diaDescanso != "") {
            String dia_desc[] = diaDescanso.split(",");
            for (int i = 0; i < dia_desc.length; i++) {
                if (dia_mes == Restriction.getNumber(dia_desc[i]))
                    return false;
            }
        }
        
        return true;
    }
    
    // Rota linealmente listado de vehiculos especificado.
    // (Ultimo vehiculo pasa a ser el primero).
    public ArrayList<String> rotarVehiculos(ArrayList<String> lst) {
        
        if (lst == null)     return null;
        if (lst.size() == 0) return lst;
        
        int idx_ult_veh = lst.size() - 1;
        String ult_veh  = lst.get(idx_ult_veh);
        lst.remove(idx_ult_veh);
        lst.add(0, ult_veh);
        
        return lst;
    }
    
    // Rota linealmente listado de rodamiento especificado.
    // (Ultimo grupo pasa a ser el primero).
    public ArrayList<GrupoRodamiento> rotarGrupo(ArrayList<GrupoRodamiento> lst) {
        
        if (lst == null)     return null;
        if (lst.size() == 0) return lst;
        
        int idx_ult_grp = lst.size() - 1;
        GrupoRodamiento ult_grp = lst.get(idx_ult_grp);
        lst.remove(idx_ult_grp);
        lst.add(0, ult_grp);
        
        return lst;
    }
    
    private static final int DIA_FESTIVO = 8;
    
    // Retorna listado de tipo dia ordenado segun su prioridad 
    // para un dia de semana especifico.
    public String[] ordenDias(int dia) {
        
        switch (dia) {            
            case DIA_FESTIVO: 
                    String orden1[] = {"F", "D", "S", "LD", "LS", "LV"}; return orden1;            
            case 7: String orden2[] = {"D", "LD", "LS", "LV"}; return orden2;
            case 6: String orden3[] = {"S", "LD", "LS", "LV"}; return orden3;
            case 5: 
            case 4: 
            case 3: 
            case 2:
            case 1: String orden4[] = {"LD", "LS", "LV"}; return orden4;                
        } return null;
    }
    
    public ArrayList<PlanillaDespacho> getListaPlanillaDespacho() {
        return lst_pd;
    }
    
    public String getRutas() {
        if (rutas != "")
            return rutas;
        return null;
    }
    
    // Mezcla/reordena aleatoriamente listado especificado.
    public void shuffle(ArrayList<String> lst) {
        long seed = System.nanoTime();
        Collections.shuffle(lst, new Random(seed));
    }
    
    // ====================== Funciones auxiliares =============================
    
    private static int N_DIGITOS = 5;
    
    // Genera numero de planilla segun el formato 00000#.
    public static String formatoNumeroPlanilla(int nplanilla) {
        
        String r = "";
        String s = "" + nplanilla;
        char digitos_n[]  = s.toCharArray();        
        int nceros = N_DIGITOS - digitos_n.length;
        
        for (int i = 0; i < nceros; i++) {
            r += "0";
        }
        r += s;
        return r;    
    }
    
    // Comprobacion para tipo_dia nuevo y/o modificado
    // Comprueba si nuevo tipo dia especificado 'ntipo_dia' es
    // permitido, teniendo en cuenta tipos de dia existentes
    // de una ruta.
    public static boolean tipoDiaPermitido(Despacho dph, String tipo_dia_previo) {
        
        String tipo_dia, tipos_permitidos = "";
        
        String ntipo_dia = dph.getTipoDia().toUpperCase();
        boolean rs = true;
        
        ArrayList<String> tipos_alm = DespachoBD.select_tipos_dias(dph.getIdRuta());
        
        // Descartamos tipo dia previo
        if (tipo_dia_previo != null) {
            tipos_alm = excluirTipoDia(tipos_alm, tipo_dia_previo);
        }
        if (tipos_alm == null) return false;
        
        for (int i = 0; i < tipos_alm.size(); i++) {            
            tipo_dia = tipos_alm.get(i).toUpperCase();
            
            if (tipo_dia.compareTo("LD") == 0) { tipos_permitidos = "F"; } 
            if (tipo_dia.compareTo("LS") == 0) { tipos_permitidos = "D F"; }
            if (tipo_dia.compareTo("LV") == 0) { tipos_permitidos = "S D F"; } 
            if (tipo_dia.compareTo("F") == 0)  { tipos_permitidos = "LD LS LV"; }        
            if (tipo_dia.compareTo("D") == 0)  { tipos_permitidos = "LS LV S F"; }
            if (tipo_dia.compareTo("S") == 0)  { tipos_permitidos = "LV D F"; }        
            
            if (tipos_permitidos.indexOf(ntipo_dia) >= 0) {
                rs &= true;
            } else {
                rs &= false;
            }
        }
        return rs;
    }
    
    // Remueve tipo de dia previo de listado de tipos existentes para una ruta.
    public static ArrayList<String> excluirTipoDia(
                                        ArrayList<String> tipos_alm,
                                        String tipo_dia_previo) {
        
        tipo_dia_previo = tipo_dia_previo.toUpperCase();
        for (int i = 0; i < tipos_alm.size(); i++) {
            String tipo_dia_alm = tipos_alm.get(i).toUpperCase();
            if (tipo_dia_previo.compareTo(tipo_dia_alm) == 0) {
                tipos_alm.remove(i);
                return tipos_alm;
            }
        }
        return tipos_alm;
    }
    
    // Extrae nombre de punto segun el codigo de punto especificado.
    public static String obtieneNombrePunto(PlanillaDespacho pd) {
        
        String codigoPunto = pd.getPunto();
        String nombrePunto = pd.getNombrePunto();
        if (codigoPunto == null || nombrePunto == null) 
            return "";
        
        codigoPunto = codigoPunto.toLowerCase();
        nombrePunto = nombrePunto.toLowerCase();
        
        if (!nombrePunto.contains(codigoPunto)) {
                    
            String str = nombrePunto;
            int index  = str.indexOf("-");
            if (index >= 0) {
                String substr = str.substring(index+1);
                substr = substr.trim();
                nombrePunto = codigoPunto + " - " + substr;
            }            
        }
        return StringUtils.upperFirstLetter(nombrePunto);
    }
}
