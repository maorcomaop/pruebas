
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
 *
 * @author lider_desarrollador
 */
public class DespachoControlPorTiempo {
    
    private Empresa empresa;
    
    class VerificaDespacho {
        
        private ArrayList<PlanillaDespacho> lst_pd;
        private ArrayList<DatosGPSMIN> lst_gps;
        private ArrayList<PlanillaDespachoMin> lst_tiempos;
                
        private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
        private SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm");   
        private SimpleDateFormat hfmt_full = new SimpleDateFormat("HH:mm:ss");   
        
        // Consulta planilla, eventos gps, tiempos de vuelta y asgina 
        // registros en estructuras globales para su posterior uso.
        public VerificaDespacho() {
            DespachoControlBD dcbd = new DespachoControlBD();            
            
            lst_pd  = dcbd.planilla_registrada_sin_procesar(); // Registros de planilla despacho (sin procesar)
            lst_gps = dcbd.datos_gps(empresa); // Registros gps          
            lst_tiempos = DespachoBD.select_tiempos_planilla(); // Tiempos de cada vuelta
            
            int size_pd  = (lst_pd  != null) ? lst_pd.size() : 0;
            int size_gps = (lst_gps != null) ? lst_gps.size() : 0;
            System.out.println("> Puntos planilla: " + size_pd);
            System.out.println("> Puntos GPS: " + size_gps);
        }                
        
        // Compara registros planilla vs eventos gps en busca
        // de valores coincidentes y marcar su diferencia en tiempo.
        // Controla planilla despacho haciendo corresponder cada evento gps
        // con su respectivo registro en planilla, anotando su
        // diferencia.
        public void verificar_tiempo() {
            
            DespachoControlBD dcbd = new DespachoControlBD();
            Date hora_actual = new Date();
            long limite_comprobacion = -1;
            
            for (int i = 0; i < lst_pd.size(); i++) {
		
		PlanillaDespacho pd = lst_pd.get(i);
		
		int vuelta_actual   = pd.getNumeroVuelta();
                int pd_ruta         = pd.getIdRuta();
		String pd_punto     = pd.getPunto().toLowerCase();
		String pd_placa     = pd.getPlaca().toLowerCase();                
		String pd_fecha     = ffmt.format(pd.getFecha());
		String pd_hora      = hfmt.format(pd.getHoraPlanificada());
		String pd_hora_min  = hfmt.format(pd.getHoraMin());
		String pd_hora_max  = hfmt.format(pd.getHoraMax());                
		Time pd_hora_t      = pd.getHoraPlanificada(); 
                int tipo_punto      = pd.getTipoPunto();                
                
                // Calcula tiempo minimo entre puntos semejantes
                // (para evitar cruce incorrecto de puntos).
                // De no encontrarse, se asgina la mitad del tiempo de vuelta                
                
                limite_comprobacion = tiempo_separacion_minimo(pd_placa, pd_fecha, pd_ruta, vuelta_actual, i);
                //System.out.println("Tiempo sep. minima. " + limite_comprobacion);
                if (limite_comprobacion == -1) {
                    limite_comprobacion = 15;
                    PlanillaDespachoMin vlt = datos_vuelta(pd_placa, pd_fecha, pd_ruta, vuelta_actual);
                    if (vlt != null) {
                        long tiempo_vuelta = vlt.getTiempoVueltaMin();
                        int nptos = vlt.getCantidadPuntos();
                        if (nptos >= 2) {
                            limite_comprobacion = tiempo_vuelta / 2;
                        }
                    }
                    limite_comprobacion *= 60000;
                    //System.out.println("Tiempo estimado. " + limite_comprobacion);
                }
                
                // Descartamos registros de planilla cuya hora supera la hora actual
                // (aún no han ocurrido)
		//if (pd_hora.compareTo(hora_actual) > 0) continue;
                
                // Si vuelta comparada es superior a vuelta de la hora actual,
                // se detiene la comprobacion
                /*
                int vuelta_hora = vuelta_actual(pd_placa, pd_fecha, shora_actual);                
                if (vuelta_actual > vuelta_hora)
                    continue; */
                
                // Si hora de punto despacho a comparar esta entre rango +-10 minutos
                // de hora actual, se analiza
                
                boolean es_base_salida  = (tipo_punto == 1) ? true : false;
                boolean es_base_llegada = (tipo_punto == 3) ? true : false;
                boolean es_base = (es_base_salida || es_base_llegada);
                
                String intervalo_hora[] = intervalo_consulta();                
                if (pd_hora.compareTo(intervalo_hora[0]) < 0 ||
                    pd_hora.compareTo(intervalo_hora[1]) > 0) {
                    if (pd_hora.compareTo(intervalo_hora[1]) > 0) {
                        continue;
                    }                        
                }                           
                
		boolean punto_interno = false;    
                boolean punto_externo = false;
		long dif_int        = Long.MAX_VALUE;
		long dif_ext        = Long.MAX_VALUE;
		long dif_int_abs    = Long.MAX_VALUE;
		long dif_ext_abs    = Long.MAX_VALUE;
		Time gps_hora_int   = null;
		Time gps_hora_ext   = null;
                
                // Tiempo universal (sin restriccion de tiempo)
                long dif_univ       = Long.MAX_VALUE;
                long dif_univ_abs   = Long.MAX_VALUE;
                Time gps_hora_univ  = null;                
                
                // Indices
                int idx_gps_int, idx_gps_ext, idx_gps_univ;
                idx_gps_int = idx_gps_ext = idx_gps_univ = -1;
                
                // Se revisa cada registro de planilla despacho respecto a registros gps
		for (int j = 0; j < lst_gps.size(); j++) {
		
                    DatosGPSMIN gps  = lst_gps.get(j);
			
                    String gps_punto     = gps.getMsg().toLowerCase();
                    String gps_placa     = gps.getPlaca().toLowerCase();
                    String gps_fecha     = ffmt.format(gps.getFechaGPS());
                    String gps_hora      = hfmt.format(gps.getHoraGPS());
                    String gps_hora_full = hfmt_full.format(gps.getHoraGPS());
                    Time gps_hora_t      = gps.getHoraGPS();
                    gps_punto            = extraer_codigo_punto(gps_punto);
                        
                    String shora_actual = hfmt.format(hora_actual);
                    if (gps_hora.compareTo(shora_actual) > 0)
                        break; 
                    
                    // Existe registro de vehiculo en punto en rango de hora                    
                    if (puntos_semejantes(gps_punto, pd_punto) &&
                        gps_placa.compareTo(pd_placa) == 0 &&
                        gps_fecha.compareTo(pd_fecha) == 0 &&
                        gps_hora.compareTo(pd_hora_min) >= 0 &&
                        gps_hora.compareTo(pd_hora_max) <= 0) {                        

                        String pd_hora_hmin = hfmt.format(pd.getHoraHmin());
                        String pd_hora_hmax = hfmt.format(pd.getHoraHmax());
                        
                        // Registro de vehiculo en punto ocurrio en hora esperada (tiempo holgura)                        
                        if (gps_hora.compareTo(pd_hora_hmin) >= 0 &&
                            gps_hora.compareTo(pd_hora_hmax) <= 0) {
                            
                            dif_int       = 0;
                            gps_hora_int  = gps_hora_t;
                            idx_gps_int   = j;
                            punto_interno = true;                            
                            break;

                        } else { // Diferencia en tiempo de regitro gps respecto a punto de despacho
                            long diferencia_tiempo = gps_hora_t.getTime() - pd_hora_t.getTime();                            
                            
                            if (Math.abs(diferencia_tiempo) < dif_int_abs &&
                               !dcbd.punto_controlado(pd_punto, pd_placa, pd_fecha, pd_ruta, gps_hora_full)) {                                
                                
                                dif_int_abs   = Math.abs(diferencia_tiempo);
                                dif_int       = diferencia_tiempo;
                                gps_hora_int  = gps_hora_t;
                                idx_gps_int   = j;
                                punto_interno = true;
                            }
                        }				

                    // Existe registro de vehiculo en punto fuera del rango de hora
                    // de busqueda establecido (en la vuelta actual)
                    } else if (!es_base &&
                               puntos_semejantes(gps_punto, pd_punto) &&
                               gps_placa.compareTo(pd_placa) == 0 &&
                               gps_fecha.compareTo(pd_fecha) == 0) {                      
                    
                        // Verifica que hora gps a registrar es superior al ultimo registro almacenado
                        boolean hora_en_vuelta = hora_en_vuelta(pd_placa, pd_fecha, pd_ruta, vuelta_actual, gps_hora);
                        boolean hora_correcta  = hora_registro_correcta_bd(dcbd, pd_placa, pd_fecha, pd_ruta, vuelta_actual, gps_hora_full);
                        if (!hora_correcta) continue;

                        long diferencia_tiempo = gps_hora_t.getTime() - pd_hora_t.getTime();

                        // Si hora de registro esta fuera del rango de busqueda pero dentro de la vuelta
                        if (hora_en_vuelta) {
                            if (Math.abs(diferencia_tiempo) < dif_ext_abs &&
                                Math.abs(diferencia_tiempo) <= limite_comprobacion &&
                               !dcbd.punto_controlado(pd_punto, pd_placa, pd_fecha, pd_ruta, gps_hora_full)) {                            
                                
                                dif_ext_abs   = Math.abs(diferencia_tiempo);
                                dif_ext       = diferencia_tiempo;
                                gps_hora_ext  = gps_hora_t;
                                idx_gps_ext   = j;
                                punto_externo = true;
                            }                        
                        } else {
                            // Si hora de registro esta fuerta del rango de busqueda y fuera de vuelta
                            // Se verifica que hora de registro este en los intervalos muertos de las
                            // vueltas vecinas
                            boolean hora_en_intervalo = hora_en_intervalo_muerto(pd_placa, pd_fecha, pd_ruta, vuelta_actual, gps_hora);
                            if (hora_en_intervalo &&
                                Math.abs(diferencia_tiempo) < dif_ext_abs &&
                                Math.abs(diferencia_tiempo) <= limite_comprobacion &&
                                !dcbd.punto_controlado(pd_punto, pd_placa, pd_fecha, pd_ruta, gps_hora_full)) {
                                                                
                                dif_ext_abs   = Math.abs(diferencia_tiempo);
                                dif_ext       = diferencia_tiempo;
                                gps_hora_ext  = gps_hora_t;
                                idx_gps_ext   = j;
                                punto_externo = true;
                            }                            
                        }                                
                    }                    

                    // Se busca base en un rango maximo de tiempo hasta
                    // del estimado para la vuelta
                    if (es_base &&
                        puntos_semejantes(gps_punto, pd_punto) &&
                        gps_placa.compareTo(pd_placa) == 0 &&
                        gps_fecha.compareTo(pd_fecha) == 0) {                     

                        long diferencia_tiempo  = gps_hora_t.getTime() - pd_hora_t.getTime();                        
                        
                        if (Math.abs(diferencia_tiempo) < dif_univ_abs &&
                            Math.abs(diferencia_tiempo) <= limite_comprobacion) {                            
                                                        
                            dif_univ_abs  = Math.abs(diferencia_tiempo);
                            dif_univ      = diferencia_tiempo;
                            gps_hora_univ = gps_hora_t;
                            idx_gps_univ  = j;
                        }                        
                    }
                }                                    
                                
                // Comprueba si punto es base-salida para controlarse directamente,
                // de lo contrario solo se controlara si hubo evento base-salida previamente
                boolean registrar_punto = (es_base && es_base_salida) 
                            ? true
                            : dcbd.base_salida_controlada(pd_placa, pd_fecha, pd_ruta, vuelta_actual);
                                
                // Prepara punto para controlarse (actualizar estado y marcar diferencia de tiempo),
                // solo si cumple las siguientes condiciones.
                // 1. Es un punto
                // 2. Existe hora de evento gps relacionado
                // 3. Hora de evento no ha sido controlada
                // 4. Punto no se ha visto en estructura interna
		if (punto_interno && gps_hora_int != null && registrar_punto &&
                   !dcbd.punto_controlado(pd_punto, pd_placa, pd_fecha, pd_ruta, hfmt_full.format(gps_hora_int)) &&
                   !lst_gps.get(idx_gps_int).punto_visto()) {
                    
                    pd.setHoraReal(gps_hora_int);
                    pd.setDiferencia(dif_int);
                    pd.setEstadoDespacho(1);
                    lst_gps.get(idx_gps_int).marcarPuntoVisto();                    
		} else if
                   (punto_externo && gps_hora_ext != null && registrar_punto &&
                   !dcbd.punto_controlado(pd_punto, pd_placa, pd_fecha, pd_ruta, hfmt_full.format(gps_hora_ext)) &&
                   !lst_gps.get(idx_gps_ext).punto_visto()) {
                 
                    pd.setHoraReal(gps_hora_ext);
                    pd.setDiferencia(dif_ext);
                    pd.setEstadoDespacho(2);
                    lst_gps.get(idx_gps_ext).marcarPuntoVisto();                    
		}  else if 
                    (gps_hora_univ != null && registrar_punto &&
                    !dcbd.punto_controlado(pd_punto, pd_placa, pd_fecha, pd_ruta, hfmt_full.format(gps_hora_univ)) &&
                    !lst_gps.get(idx_gps_univ).punto_visto()) {
                                        
                    pd.setHoraReal(gps_hora_univ);
                    pd.setDiferencia(dif_univ);
                    pd.setEstadoDespacho(2);
                    lst_gps.get(idx_gps_univ).marcarPuntoVisto();                    
                }
            }
        }
        
        public void print(String s, String placa, String punto, String hora) {
            System.out.println(s +"\t"+ placa +" "+ punto +" "+ hora);
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
                if (pd.getEstadoDespacho() == 1) {
                    lst_pd_act.add(pd);
                }
                if (pd.getEstadoDespacho() == 2) {
                    lst_pd_act.add(pd);
                }
            }
            
            System.out.println("> Puntos para actualizar: " + lst_pd_act.size());
            
            // Inicia actualizacion de registros planilla
            DespachoControlBD dcbd = new DespachoControlBD();
            dcbd.actualizar_planilla(lst_pd_act);
        }
        
        // ===================== Funciones auxiliares ==========================
        
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
        
        // Comprueba si hora de evento gps hace parte de una vuelta y
        // vehiculo en especifico.
        public boolean hora_en_vuelta(String placa, String fecha, int ruta, int vuelta_actual, String shora_gps) {
            
            //String shora_gps = hfmt.format(hora_gps);
            
            if (lst_tiempos != null && lst_tiempos.size() > 0) {
                
                for (PlanillaDespachoMin pd : lst_tiempos) {
                    String pd_fecha = pd.getFechaStr();
                    String pd_placa = pd.getPlaca().toLowerCase();
                    int pd_vuelta   = pd.getNumeroVuelta();
                    int pd_ruta     = pd.getIdRuta();
                
                    if (pd_fecha.compareTo(fecha) == 0 &&
                        pd_placa.compareTo(placa) == 0 &&
                        pd_vuelta == vuelta_actual &&
                        pd_ruta == ruta) {
                        String hora_inicio = pd.getHoraInicioStr();
                        String hora_fin    = pd.getHoraFinStr();
                    
                        if (shora_gps.compareTo(hora_inicio) >= 0 &&
                            shora_gps.compareTo(hora_fin)    <= 0) {
                            return true;
                        }
                    }
                }
            } 
            return false;
        }
        
        // Comprueba si hora de evento gps se encuentra por fuera
        // de intervalo de tiempo de vuelta, para un vehiculo en especifico.
        public boolean hora_en_intervalo_muerto(String placa, String fecha, int ruta, int vuelta_actual, String hora_gps) {
            
            int hora_inicio = 0,
                hora_final  = 1;
            String horaLim, horaA, horaB;
            horaLim = horaA = horaB = null;
            
            if (vuelta_actual <= 1) {
                // Antes de hora de inicio de primera vuelta.
                horaLim = hora_vuelta(placa, fecha, ruta, vuelta_actual+1, hora_inicio);
                if (horaLim != null && hora_gps.compareTo(horaLim) < 0) return true;
            } else {
                // Entre hora final de vuelta previa y hora final de vuelta actual
                // sin incluirlas.
                horaA = hora_vuelta(placa, fecha, ruta, vuelta_actual-1, hora_final);
                horaB = hora_vuelta(placa, fecha, ruta, vuelta_actual, hora_final);
                if (horaA != null && horaB != null &&
                    hora_gps.compareTo(horaA) > 0 &&
                    hora_gps.compareTo(horaB) < 0) {
                    return true;
                }
            }
            return false;
        }
        
        // Extrae hora de inicio o final de una vuelta y vehiculo en especifico.
        public String hora_vuelta(String placa, String fecha, int ruta, int nvuelta, int inicio_final) {
            
            if (lst_tiempos != null && lst_tiempos.size() > 0) {
                
                for (PlanillaDespachoMin pd : lst_tiempos) {
                    String pd_fecha = pd.getFechaStr();
                    String pd_placa = pd.getPlaca().toLowerCase();
                    int pd_vuelta   = pd.getNumeroVuelta();
                    int pd_ruta     = pd.getIdRuta();
                
                    if (pd_fecha.compareTo(fecha) == 0 &&
                        pd_placa.compareTo(placa) == 0 &&
                        pd_vuelta == nvuelta &&
                        pd_ruta == ruta) {
                        String hora = (inicio_final == 0)
                                    ? pd.getHoraInicioStr()
                                    : pd.getHoraFinStr();
                        return hora;
                    }
                }
            }
            return null;
        }
        
        // Extrae tiempo de diferencia minimo entre puntos semejantes
        // de una vuelta y vehiculo en especifico.
        // De no encontrarse puntos semejantes se retorna -1.
        public long tiempo_separacion_minimo(String placa, String fecha, int ruta, int nvuelta, int idx) {
            
            ArrayList<PlanillaDespacho> vlt = new ArrayList<PlanillaDespacho>();
            
            if (lst_pd != null && lst_pd.size() > 0) {
                
                for (int i = idx; i < lst_pd.size(); i++) {
                    PlanillaDespacho pd = lst_pd.get(i);
                    String pd_fecha     = ffmt.format(pd.getFecha());
                    String pd_placa     = pd.getPlaca().toLowerCase();
                    int pd_ruta         = pd.getIdRuta();
                    int pd_nvuelta      = pd.getNumeroVuelta();
                    
                    if (pd_fecha.compareTo(fecha) == 0 &&
                        pd_placa.compareTo(placa) == 0 &&
                        pd_ruta == ruta &&
                        pd_nvuelta == nvuelta) {
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
                        return min_dif;
                }
            }
            return -1;
        }
        
        // Extrae datos de vuelta (tiempos) para una vuelta y vehiculo en especifico.
        public PlanillaDespachoMin datos_vuelta(String placa, String fecha, int ruta, int vuelta_actual) {
                        
            if (lst_tiempos != null && lst_tiempos.size() > 0) {
                
                for (PlanillaDespachoMin pd : lst_tiempos) {
                    String pd_fecha = pd.getFechaStr();
                    String pd_placa = pd.getPlaca().toLowerCase();
                    int pd_vuelta   = pd.getNumeroVuelta();
                    int pd_ruta     = pd.getIdRuta();
                
                    if (pd_fecha.compareTo(fecha) == 0 &&
                        pd_placa.compareTo(placa) == 0 &&
                        pd_vuelta == vuelta_actual &&
                        pd_ruta == ruta) {
                        return pd;
                    }
                }
            }
            return null;
        }
        
        // Extrae vuelta actual para un vehiculo segun la fecha y hora actual.
        public int vuelta_actual(String placa, String fecha, String hora_actual) {
            
            ArrayList<PlanillaDespacho> lst_vlt = new ArrayList<PlanillaDespacho>();
            
            for (PlanillaDespacho pd : lst_pd) {
                String placa_pd = pd.getPlaca().toLowerCase();
                String fecha_pd = ffmt.format(pd.getFecha());                              
                if (placa_pd.compareTo(placa) == 0 &&
                    fecha_pd.compareTo(fecha) == 0) {
                    lst_vlt.add(pd);
                }
            }            
            
            if (lst_vlt.size() > 0) {
                for (int i = 0; i < lst_vlt.size()-1; i++) {
                    PlanillaDespacho pd1 = lst_vlt.get(i);
                    PlanillaDespacho pd2 = lst_vlt.get(i+1);
                    String hora1 = hfmt.format(pd1.getHoraPlanificada());
                    String hora2 = hfmt.format(pd2.getHoraPlanificada());                    
                    if (hora_actual.compareTo(hora1) >= 0 &&
                        hora_actual.compareTo(hora2) <= 0) {                        
                        return pd2.getNumeroVuelta();
                    }
                }
            }
            return 0;
        }        
        
        // Comprueba si hora de evento gps es igual o superior a
        // ultima hora controlada. (Evita almacenar puntos con tiempos inferiores).
        public boolean hora_registro_correcta_bd(DespachoControlBD dcbd, 
                                                 String placa, 
                                                 String fecha, 
                                                 int ruta, 
                                                 int nvuelta, 
                                                 String hora_gps) {
            
            if (dcbd != null) {
                Time ult_hora_reg_t = dcbd.ultima_hora_registrada(placa, fecha, ruta, nvuelta);
                if (ult_hora_reg_t == null) return true;
                String ult_hora_reg = hfmt_full.format(ult_hora_reg_t);
                
                return (hora_gps.compareTo(ult_hora_reg) >= 0); // ? true : false;
            }
            return false;
        }
        
        // Establece limite de inspeccion/consulta de puntos a una distancia
        // por defecto no superior a 10 minutos de diferencia.
        // (Permite controlar puntos que han ocurrido anticipadamente).
        public String[] intervalo_consulta() {
            
            long MINUTO_EN_MILISEGUNDOS = 60000;
            Calendar fecha = Calendar.getInstance();
            long time = fecha.getTimeInMillis();
            
            Date min_time  = new Date(time - MINUTO_EN_MILISEGUNDOS * 10);
            Date max_time  = new Date(time + MINUTO_EN_MILISEGUNDOS * 10);
            String times[] = { hfmt.format(min_time), hfmt.format(max_time) };
            
            return times;
        }
    }
    
    // Asigna empresa
    public void establecerEmpresa(Empresa emp) {
        empresa = emp;
    }
    
    // Retorna empresa
    public Empresa obtenerEmpresa() {
        return empresa;
    }
    
    public void iniciarControl() {
        
        VerificaDespacho vdph = new VerificaDespacho();        
        vdph.verificar_tiempo();
        vdph.actualizar();               
        
    }
}
