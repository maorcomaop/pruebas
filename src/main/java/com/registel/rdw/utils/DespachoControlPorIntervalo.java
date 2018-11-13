
package com.registel.rdw.utils;

import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.DespachoControlBD;
import com.registel.rdw.logica.DatosGPSMIN;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.PlanillaDespachoMin;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 **
 * @author lider_desarrollador
 */
public class DespachoControlPorIntervalo {
    
    private Empresa empresa;
    
    class VerificaDespacho {
        
        private ArrayList<PlanillaDespacho> lst_pd;
        private ArrayList<DatosGPSMIN> lst_gps;
        private ArrayList<PlanillaDespachoMin> lst_tiempos;
        private SimpleDateFormat ffmt  = new SimpleDateFormat("yyyy-MM-dd");
        private SimpleDateFormat hfmt  = new SimpleDateFormat("HH:mm");
        private SimpleDateFormat hfmtf = new SimpleDateFormat("HH:mm:ss");                       
        
        // Consulta planilla, eventos gps, tiempos de vuelta y asgina 
        // registros en estructuras globales para su posterior uso.
        public VerificaDespacho() {
            DespachoControlBD dcbd = new DespachoControlBD();
            lst_pd  = dcbd.planilla_a_pedido();
            lst_gps = dcbd.datos_gps(empresa);
            lst_tiempos = DespachoBD.select_tiempos_planilla();
            
            int size_lpd = 0, size_lgps = 0;
            if (lst_pd  != null) size_lpd  = lst_pd.size();
            if (lst_gps != null) size_lgps = lst_gps.size();
            System.out.println("> Puntos planilla: " + size_lpd);
            System.out.println("> Puntos gps: " + size_lgps);
        }
        
        // Compara registros planilla vs eventos gps en busca
        // de valores coincidentes y marcar su diferencia en tiempo.
        // Controla planilla despacho haciendo corresponder cada evento gps
        // con su respectivo registro en planilla, anotando su
        // diferencia.
        public void verificar_intervalo() {
            
            Time hora_salida_t        = null;            
            String hora_actual        = hfmt.format(new Date());
            DespachoControlBD dcbd    = new DespachoControlBD();
            boolean es_base_salida    = false;
            boolean es_base_entrada   = false;    
            boolean puntos_semejantes = false;
            long limite_comprobacion  = -1;
            long rango_anticipacion   = 0;
            long rango_comprobacion   = -1;
            int numpto = 1;
                        
            for (int i = 0; i < lst_pd.size(); i++) {                                
                
                PlanillaDespacho pd = lst_pd.get(i);
                String punto_pd     = pd.getPunto().toLowerCase();
                String placa_pd     = pd.getPlaca().toLowerCase();
                String fecha_pd     = ffmt.format(pd.getFecha());                 
                String hora_pd      = hfmt.format(pd.getHoraPlanificada());                
                Time hora_pd_t      = pd.getHoraPlanificada();
                int tiempo_tramo_pd = pd.getIntervaloLlegada();
                int tipo_punto_pd   = pd.getTipoPunto();
                int estado_pd       = pd.getEstadoDespacho();
                int ruta_pd         = pd.getIdRuta();
                int holgura_punto   = pd.getHolguraPunto();
                int vuelta_actual   = pd.getNumeroVuelta();
                int pospto          = pd.getPosicionPunto();
                es_base_salida      = false;
                es_base_entrada     = false;
                puntos_semejantes   = false;
                
                // Se obtiene hora real de salida (si hubo)
                if (tipo_punto_pd == 1) {                    
                    Time hora = pd.getHoraReal();                    
                    if (hora != null) {
                        hora_salida_t = new Time(hora.getTime());
                    }                    
                    es_base_salida = true;                    
                }
                if (tipo_punto_pd == 3) {
                    es_base_entrada = true;
                }
                
                // Si vuelta fue cerrada (llega a base entrada)
                // se descartan futuros eventos coincidentes,
                // es decir se deja de controlar la vuelta
                if (base_entrada_controlada(pd.getIdIntervalo())) {
                    continue;
                }
                                
                // Calcula tiempo de comprobacion y anticipacion,
                // ambos basados en el tiempo minimo entre puntos semejantes (si existen),
                // de lo contrario, se asigna la mitad del tiempo de vuelta.
                // Para tiempo de asignacion si esta basado en puntos semejantes,
                // se ajusta al 70% del tiempo.
                if (tipo_punto_pd == 1 || rango_comprobacion == -1) {
                    
                    rango_comprobacion = tiempo_separacion_minimo(i, pd.getIdIntervalo());
                    rango_anticipacion = new Long(rango_comprobacion);
                    puntos_semejantes  = true;
                    
                    if (rango_comprobacion == -1) {
                        rango_comprobacion = rango_anticipacion = 15;
                        PlanillaDespachoMin vlt = datos_vuelta(pd.getIdIntervalo());
                        if (vlt != null) {
                            long tiempo_vuelta = vlt.getTiempoVueltaMin();
                            int nptos = vlt.getCantidadPuntos();
                            if (nptos >= 2) {
                                rango_comprobacion = tiempo_vuelta / 2;
                                rango_anticipacion = new Long(rango_comprobacion);
                                puntos_semejantes  = false;
                            }
                        }                        
                    }                    
                    rango_comprobacion *= 60000;
                    
                    if (puntos_semejantes) {
                        rango_anticipacion *= .70;
                    }
                }                                
                
                // Se permite hacer control de puntos planillados con anticipacion
                // hasta cierta cantidad de tiempo (10 minutos por defecto)
                String intervalo_consulta[] = intervalo_consulta(rango_anticipacion);
                if (hora_pd.compareTo(intervalo_consulta[1]) > 0) {
                    continue;
                }
                                
                
                // Se descartan puntos despacho controlados
                if (!es_base_salida && estado_pd >= 1) 
                    continue;
                
                boolean punto_interno = false;
                boolean punto_externo = false;
                boolean punto_secundario = false;
                
                int  idx_gps        = -1;                  
                long diferencia_gps = Long.MAX_VALUE;              
                Time hora_gps_tt    = null;
                
                for (int j = 0; j < lst_gps.size(); j++) {
                    
                    DatosGPSMIN gps  = lst_gps.get(j);
                    String punto_gps = gps.getMsg().toLowerCase();
                    String placa_gps = gps.getPlaca().toLowerCase();
                    String fecha_gps = ffmt.format(gps.getFechaGPS());
                    String hora_gps  = hfmt.format(gps.getHoraGPS());
                    String hora_gpsf = hfmtf.format(gps.getHoraGPS());
                    punto_gps        = extraer_codigo_punto(punto_gps);
                    Time hora_gps_t  = gps.getHoraGPS();
                                        
                    if (hora_gps.compareTo(hora_actual) > 0)
                        continue;
                    
                    if (gps.punto_visto()) 
                        continue;                                        
                    
                    // Existe evento gps para punto de planilla planificado
                    // de un vehiculo y fecha especifico.
                    if ((puntos_semejantes(punto_gps, punto_pd) || 
                        punto_base_entrada(punto_gps, es_base_entrada)) &&
                        fecha_pd.compareTo(fecha_gps) == 0 &&
                        placa_pd.compareTo(placa_gps) == 0) {                                                                        
                        
                        // Comprueba si punto no ha sido controlado
                        if (dcbd.punto_controlado(punto_pd, placa_pd, fecha_pd, hora_gpsf)) {                            
                            continue;
                        }                                                
                        
                        long diferencia = 0;
                                                
                        PlanillaDespachoMin vlt = datos_vuelta(pd.getIdIntervalo());
                        if (vlt != null) {
                            // Punto secundario (ubicacion >= mitad de la ruta)
                            punto_secundario = (pospto >= vlt.getCantidadPuntos()/2) ? true : false;
                            /*
                            String hora_fin_vuelta = vlt.getHoraFinStr();                            
                            if (!es_base_entrada && hora_gpsf.compareTo(hora_fin_vuelta) > 0) {                                
                                break;
                            } */
                        }
                            
                        if (es_base_salida) {
                            // Si es hora de salida se calcula diferencia
                            // respecto a evento gps del punto
                            diferencia = hora_gps_t.getTime() - hora_pd_t.getTime();
                        } else {
                            if (hora_salida_t != null) {

                                // Se calcula tiempo de llegada a punto segun hora de salida real
                                // e intervalo de llegada al punto, adicionalmente se estima y compara
                                // holgura del punto respecto al tiempo calculado
                                //Time hora_punto = TimeUtil.sumarMinutos(hora_salida_t, tiempo_tramo_pd);
                                Time hora_punto = pd.getHoraPlanificada();
                                Time min_holgura_punto = TimeUtil.sumarMinutos(hora_punto, -holgura_punto);
                                Time max_holgura_punto = TimeUtil.sumarMinutos(hora_punto, holgura_punto);
                                String min_holgura = hfmtf.format(min_holgura_punto);
                                String max_holgura = hfmtf.format(max_holgura_punto);
                                
                                if (hora_gpsf.compareTo(min_holgura) >= 0 &&
                                    hora_gpsf.compareTo(max_holgura) <= 0) {
                                    diferencia = 0;                                
                                } else {                            
                                    //long tiempo_tramo_gps = hora_gps_t.getTime() - hora_salida_t.getTime();
                                    //diferencia = tiempo_tramo_gps - tiempo_tramo_pd * 60000;
                                    diferencia = hora_gps_t.getTime() - hora_pd_t.getTime();
                                }

                            } else {
                                // Si no existe hora de salida controlada,
                                // se calcula diferencia respecto a evento gps del punto
                                diferencia = hora_gps_t.getTime() - hora_pd_t.getTime();                                
                            }
                        }
                                             
                        // Establece limite de comprobacion para base salida/entrada infinita
                        // (Son los unicos puntos que no tienen restriccion de tiempo).                        
                        // Si existe algun registro de base entrada, se obtiene el mas reciente
                        // y se verifica que base salida sea superior o igual a este.
                        if (es_base_salida) {
                            limite_comprobacion = Long.MAX_VALUE;
                            Time hora_entrada_t = dcbd.ultima_base_entrada_controlada(fecha_pd, placa_pd);
                            if (vuelta_actual > 1 && hora_entrada_t == null) {
                                continue;
                            } 
                            if (hora_entrada_t != null) {
                                String hora_entrada = hfmtf.format(hora_entrada_t);                                
                                if (hora_gpsf.compareTo(hora_entrada) < 0) {
                                    continue;
                                }
                            }
                        } else if (es_base_entrada) {
                            limite_comprobacion = Long.MAX_VALUE;
                        } else {
                            limite_comprobacion = rango_comprobacion;
                        }

                        // Se busca siempre evento gps mas cercano a punto de planilla
                        // que actualmente se esta controlando.                        
                        if (Math.abs(diferencia) <  Math.abs(diferencia_gps) &&
                           (Math.abs(diferencia) <= limite_comprobacion || punto_secundario) &&
                            hora_registro_correcta_bd(dcbd, pd.getIdIntervalo(), hora_gpsf)) {
                            
                            //print(pd, gps);
                            System.out.println("Lim. comprobacion: " + limite_comprobacion);
                            System.out.println("Rango. antcp: " + rango_anticipacion);
                                                        
                            diferencia_gps = diferencia;
                            hora_gps_tt = hora_gps_t;
                            idx_gps = j;
                            
                            if (diferencia == 0) {
                                punto_interno = true;
                                punto_externo = false;                                
                                break;
                            } else {
                                punto_externo = true;
                                punto_interno = false;
                            }
                        }                        
                    }
                }
                
                // Si punto a registrar no es base salida o no existe una controlada
                // se descarta control para esa vuelta
                if (!dcbd.base_salida_controlada(pd.getIdIntervalo()) &&
                    !es_base_salida) {                    
                    continue;
                }
                
                // Prepara punto para controlarse (actualizar estado y marcar diferencia de tiempo),
                // solo si cumple las siguientes condiciones.
                // 1. Existe hora de evento gps relacionado
                // 2. Punto no se ha visto en estructura interna
                if (hora_gps_tt != null && !lst_gps.get(idx_gps).punto_visto()) {
                    
                    pd.setHoraReal(hora_gps_tt);
                    pd.setDiferencia(diferencia_gps);                    
                    if (punto_interno) pd.setEstadoDespacho(11);                    
                    if (punto_externo) pd.setEstadoDespacho(22);
                                                            
                    lst_gps.get(idx_gps).marcarPuntoVisto();
                    
                    // En caso de ser base salida (se registra individualmente)
                    if (es_base_salida) {                                                
                        break;
                    }
                }
            }
        }
        
        public void print(PlanillaDespacho pd, DatosGPSMIN gps) {
            System.out.println();            
            System.out.println(pd);
            for (int i = 1; i <= 50; i++)
                System.out.print(".");
            System.out.println();
            System.out.println(gps);
        }
        
        // Actualiza registros de planilla despacho que se han
        // relacionado con algun evento gps, segun el procedimiento
        // de control verificar_tiempo.
        public void actualizar() {
            ArrayList<PlanillaDespacho> lst_pd_act 
                    = new ArrayList<PlanillaDespacho>();            
            
            // Selecciona registros para actualizar
            for (int i = 0; i < lst_pd.size(); i++) {
                PlanillaDespacho pd = lst_pd.get(i);
                
                if (pd.getEstadoDespacho() == 11) {
                    pd.setEstadoDespacho(1);
                    lst_pd_act.add(pd);
                }
                if (pd.getEstadoDespacho() == 22) {
                    pd.setEstadoDespacho(2);
                    lst_pd_act.add(pd);
                }
            }
            
            System.out.println("> Puntos para actualizar: " + lst_pd_act.size());
            
            // Inicia actualizacion de registros planilla
            DespachoControlBD dcbd = new DespachoControlBD();
            dcbd.actualizar_planilla(lst_pd_act);            
        }
        
        // ======================= Funciones auxiliares ========================
        
        // Extrae codigo punto de cadena de texto - campo msg
        public String extraer_codigo_punto(String s) {
            String ss[] = s.split("-");
            if (ss.length >= 2) {
                return ss[1].trim();
            }
            return s;
        }
        
        // Comprueba si punto gps coincide con punto planilla,
        // teniendo en cuenta posibles ceros adelante.
        public boolean puntos_semejantes(String punto_gps, String punto_pd) {
            
            if (punto_gps.compareTo(punto_pd) == 0) {
                return true; 
            } else {
                String codigoPunto = punto_pd.substring(1);
                if (punto_gps.compareTo("p0" + codigoPunto) == 0 ||
                    punto_gps.compareTo("p00" + codigoPunto) == 0 ||
                    punto_gps.compareTo("p000" + codigoPunto) == 0 ||
                    punto_gps.compareTo("p0000" + codigoPunto) == 0 ||
                    punto_gps.compareTo("p00000" + codigoPunto) == 0) {
                    return true;
                }
            }
            return false;
        }
        
        // Comprueba si punto gps corresponde a un punto tipo base
        // (codificacion >= 900), cuando bases en ruta planificada
        // no corresponden a realizadas en campo.
        public boolean punto_base_entrada(String punto_gps, boolean es_base_entrada) {
            String scodigoPuntoGps = punto_gps.substring(1);
            int codigoPuntoGps     = Restriction.getNumber(scodigoPuntoGps);
            return (codigoPuntoGps >= 900 && (codigoPuntoGps % 2 == 0) && es_base_entrada);
        }
        
        // Comprueba si base entrada para un intervalo despacho especifico
        // ha sido controlada.
        // (Intervalo de despacho define registros asociados a vehiculo, 
        // ruta, vuelta y fecha en especifico).
        public boolean base_entrada_controlada(int id) {
            
            for (int i = 0; i < lst_pd.size(); i++) {
                PlanillaDespacho pd = lst_pd.get(i);
                if (pd.getIdIntervalo() == id &&
                    pd.getTipoPunto() == 3) {                    
                    return (pd.getEstadoDespacho() > 0);
                }
            }
            return true;
        }
        
        // Establece limite de inspeccion/consulta de puntos a una distancia
        // de 10 minutos, si rango especificado es incorrecto.
        // (Permite controlar puntos que han ocurrido anticipadamente).
        public String[] intervalo_consulta(long rango) {
            
            if (rango <= 0) rango = 10;
            
            long MINUTOS_EN_MILISEGUNDOS = 60000;
            Calendar fecha = Calendar.getInstance();
            long time = fecha.getTimeInMillis();
            
            Date min_time = new Date(time - MINUTOS_EN_MILISEGUNDOS * rango);
            Date max_time = new Date(time + MINUTOS_EN_MILISEGUNDOS * rango);
            String times[] = { hfmt.format(min_time), hfmt.format(max_time) };
            
            return times;
        }
        
        // Extrae tiempo de diferencia minimo entre puntos semejantes
        // de una vuelta asociada a un intervalo de despacho especifico.
        // (Intervalo de despacho define registros asociados a vehiculo, 
        // ruta, vuelta y fecha en especifico).
        // De no encontrarse puntos semejantes se retorna -1.
        public long tiempo_separacion_minimo(int idx, int id) {
            
            ArrayList<PlanillaDespacho> vlt = new ArrayList<PlanillaDespacho>();
            
            if (lst_pd != null && lst_pd.size() > 0) {
                
                for (int i = idx; i < lst_pd.size(); i++) {
                    PlanillaDespacho pd = lst_pd.get(i);
                    
                    if (pd.getIdIntervalo() == id) {
                        vlt.add(pd);
                    } else {
                        break;
                    }
                }
                
                if (vlt.size() > 0) {
                    int vsize = vlt.size();
                    long min_dif = Long.MAX_VALUE;
                    long dif;
                    
                    for (int i = 0; i < vsize; i++) {
                        String p1 = vlt.get(i).getPunto();
                        Time   h1 = vlt.get(i).getHoraPlanificada();
                        
                        for (int j = 0; j < vsize; j++) {
                            String p2 = vlt.get(j).getPunto();
                            Time   h2 = vlt.get(j).getHoraPlanificada();
                    
                            if (p1.compareTo(p2) == 0) {
                                if (h1.getTime() == h2.getTime()) {
                                    continue;
                                } else {
                                    dif = h1.getTime() - h2.getTime();
                                    dif = Math.abs(dif);
                                    if (dif < min_dif) {                                        
                                        min_dif = dif;
                                    }
                                }                   
                            }
                        }
                    }
                    if (min_dif < Long.MAX_VALUE) 
                        return min_dif / 60000;
                }
            }
            return -1;
        }
        
        // Extrae datos de vuelta (tiempos) para un intervalo de despacho especifico.
        // (Intervalo de despacho define registros asociados a un vehiculo, ruta, 
        // vuelta y fecha en especifico).
        public PlanillaDespachoMin datos_vuelta(int id) {
            
            if (lst_tiempos != null && lst_tiempos.size() > 0) {
                for (PlanillaDespachoMin pd : lst_tiempos) {
                    if (pd.getIdIntervalo() == id) {                        
                        return pd;
                    }
                }
            }
            return null;
        }
        
        // Comprueba si hora de evento gps es igual o superior a
        // ultima hora controlada. (Evita almacenar puntos con tiempos inferiores).
        public boolean hora_registro_correcta_bd(DespachoControlBD dcbd, 
                                                 int id_intervalo,
                                                 String hora_gps) {
            
            if (dcbd != null) {
                Time ult_hora_reg_t = dcbd.ultima_hora_registrada(id_intervalo);
                if (ult_hora_reg_t == null) return true;
                String ult_hora_reg = hfmtf.format(ult_hora_reg_t);
                //print("ult. hora. registrada: ", ult_hora_reg, " - ", hora_gps, "");
                
                return (hora_gps.compareTo(ult_hora_reg) >= 0); // ? true : false;
            }
            return false;
        }                
    }
    
    // Asgina empresa
    public void establecerEmpresa(Empresa emp) {
        empresa = emp;
    }
    
    // Retorna empresa
    public Empresa obtenerEmpresa() {
        return empresa;
    }
    
    public void iniciarControl() {
        
        VerificaDespacho vdph = new VerificaDespacho();
        vdph.verificar_intervalo();
        vdph.actualizar();               

    }
}
