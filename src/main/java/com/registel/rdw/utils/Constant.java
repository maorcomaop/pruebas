/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

/**
 *
 * @author lider_desarrollador
 */
public class Constant {
    
    // system.properties
    public static final String NUM_MAX_CONEXIONES_SERV = "hilos_permitidos_wifi";
    public static final String PUERTO_SERV = "puerto_wifi";
    public static final String TIEMPO_ACTIVO_SERV = "tiempo_conexion_activa_wifi";
    public static final String TIEMPO_RETRASO_SERV = "delay_conexiones_wifi";
    public static final String BASE_CONFIRMACION_DESCARTE = "base_confirmacion_descarte";
    public static final String INTENTOS_ACTUALIZACION_PUNTOS = "intentos_actualizacion_puntos";
    
    public static final String NUM_MAX_CONEXIONES_SERV_PERIMETRO = "hilos_permitidos_perimetro";
    public static final String PUERTO_SERV_PERIMETRO = "puerto_perimetro";
    public static final String TIEMPO_ACTIVO_SERV_PERIMETRO = "tiempo_conexion_activa_perimetro";
    public static final String TIEMPO_RETRASO_SERV_PERIMETRO = "delay_conexiones_perimetro";
    
    // perimetro.properties
    public static final String DESCARTE_INICIAL = "descarte_inicial";
    public static final String DESCARTE_FINAL = "descarte_final";
    public static final String DESCARTE_HOLGURA = "descarte_holgura";
    public static final String COMPROBACION = "comprobacion";
    
    // entorno.properties
    public static final String DIRECTORIO_RAIZ_RDTO = "directorio_raiz_rdto";
    public static final String AUTOINICIO_CONSOLIDACION = "autoinicio_consolidacion";
    public static final String TIEMPO_ESPERA_POR_EVENTO = "tiempo_espera_por_evento";            
    public static final String RANGO_TIEMPO_CONTROL_DESPACHO = "rango_tiempo_control_despacho";
    public static final String TIPO_CONTROL_DESPACHO = "tipo_control_despacho";
    public static final String DOMINIO_HOST_SERVIDOR = "dominio_host_servidor";
    public static final String CORREO_CORPORATIVO = "correo_corporativo";
    public static final String PASSWORD_CORPORATIVO = "password_corporativo";
    public static final String MOSTRAR_ENTRADA_SALIDA = "mostrar_entrada_salida";
    public static final String MOSTRAR_NIVEL_OCUPACION = "mostrar_nivel_ocupacion";
    public static final String MOSTRAR_NOIC = "mostrar_noic";
    public static final String AJUSTE_HORA_SERVIDOR = "ajuste_hora_servidor";    
    public static final String AJUSTE_PTO_CONTROL = "ajuste_pto_control";
    
    public static final String CONTROL_PLANIFICADO = "ctrl-tiempo-planificado";
    public static final String CONTROL_INTERVALO   = "ctrl-intervalo-llegada";
    
    public static final int DPH_PLANIFICADO = 1;    // asociado a CONTROL PLANIFICADO
    public static final int DPH_MANUAL = 2;         // asociado a CONTROL INTERVALO
    
    // Ubicacion base regisdata escritorio
    public static final String DIR_RDTO_WIN   = "C:\\";
    public static final String DIR_RDTO_LINUX = "/home/rdw/";
    
    public static final String NAME_RDTO_WIN = "RegisDataOnlineWifi.exe";
    public static final String NAME_RDTO_LINUX = "AppBaseRDTWifiV10.jar";
    
    // Liquidacion
    public static final String IMPRESORA_POS_LIQUIDACION = "impresora_pos";
    public static final String LIQUIDACION_FRANJAS = "liquidacion_franjas";
    
    // Base de datos externa (GPS)
    public static final String HOST_GPS = "host_gps";
    public static final String BD_GPS = "bd_gps";
    
    public static final String SISTEMA_DE_AYUDA = "sistema_de_ayuda";
}
