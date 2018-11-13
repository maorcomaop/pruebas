/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.ConexionExterna;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.PilaConexiones;
import com.registel.rdw.datos.ReportesBD;
import com.registel.rdw.datos.RutaBD;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.reportes.RutaXVehiculo;
import com.registel.rdw.reportes.RutaXVehiculo_r;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author lider_desarrollador
 */
public class ReporteUtil {
    
    private Map<String,String> hparametrosBase;
    private SimpleDateFormat fecha    = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy");
    private SimpleDateFormat fechaSQL = new SimpleDateFormat("yyyy-MM-dd");
    
    String diasSemana[] = {
        "Monday:lunes",
        "Tuesday:martes",
        "Wednesday:miércoles",
        "Thursday:jueves",
        "Friday:viernes",
        "Saturday:sábado",
        "Sunday:domingo"            
    };
    
    String meses[] = {
        "January:enero", "February:febrero", "March:marzo", "April:abril",
        "May:mayo", "June:junio", "July:julio", "August:agosto",
        "September:septiembre", "October:octubre", "November:noviembre", "December:diciembre"
    };
    
    private String pathReporte      = "";
    private String pathSubreporte   = "";
    private String pathPlantilla    = "";    
    
    public static boolean incluirVehiculo             = false;
    public static boolean incluirVehiculos            = false;
    public static boolean incluirTotalidadVehiculos   = false;
    public static boolean incluirVehiculosPropietario = false;
    public static boolean incluirRuta                 = false;
    public static boolean incluirRutas                = false;
    public static boolean incluirTotalidadRutas       = false;
    public static boolean incluirEmpresa  = false;
    public static boolean incluirAlarma   = false;
    public static boolean incluirBase     = false;
    
    public static boolean desdeCodigo     = false;
    public static boolean reporteWeb      = false;  
    
    private PilaConexiones pila_con = null;
    private Connection con = null;
//    private static Etiquetas etiquetas = null;
    private static ConfiguracionLiquidacion etiquetas = null;
    
    // Restaura variables de indicacion
    public static void restaurarValores() {
        
        incluirVehiculo             = false;
        incluirVehiculos            = false;
        incluirTotalidadVehiculos   = false;
        incluirVehiculosPropietario = false;
        incluirRuta             = false;
        incluirRutas            = false;
        incluirTotalidadRutas   = false;
        incluirEmpresa          = false;
        incluirAlarma           = false;
        incluirBase             = false;

        desdeCodigo     = false;
        reporteWeb      = false;
    }
    
    // Asigna etiquetas
    public static void establecerEtiquetas(/*Etiquetas e*/ConfiguracionLiquidacion e) {
        etiquetas = e;
    }
    
    // Establece ubicacion de directorios para generar reportes
    public ReporteUtil (Map<String,String> h) {
        this.hparametrosBase = h;
        
        pathReporte     = h.get("path") + "/resources/reportes";
        pathSubreporte  = pathReporte + "/subreportes";
        pathPlantilla   = pathReporte + "/plantilla";                
    }
    
    // Genera y retorna reporte
    public JasperPrint generarReporte (String simboloMoneda) 
    {
        if (hparametrosBase != null && hparametrosBase.size() > 0) {
            
            // Establece parametros jasper
            Map parameters = otrosParametros(simboloMoneda);
            
            String pathJASPER = pathReporte + "/" + hparametrosBase.get("nombreReporte") + ".jasper";
            pathJASPER = repararDelimitador(pathJASPER);
            
            try {            
                JasperPrint print = JasperFillManager.fillReport(pathJASPER, parameters, crearConexion());            
                return print;
            
            } catch (JRException e) {
                System.err.println(e);
            } finally {
                liberarConexion();
            }
        } return null;
    }
    
    // Genera y retorna reporte con coleccion de datos especificado.
    public JasperPrint generarReporte_conDatos(JRBeanCollectionDataSource dataSource, String simboloMoneda) {

        if (hparametrosBase != null && hparametrosBase.size() > 0) {
            
            // Establece parametros jasper
            Map parameters = otrosParametros(simboloMoneda);
            
            String pathJASPER = pathReporte + "/" + hparametrosBase.get("nombreReporte") + ".jasper";
            pathJASPER = repararDelimitador(pathJASPER); 
            
            try {
                JasperPrint print = JasperFillManager.fillReport(pathJASPER, parameters, dataSource);                
                //JasperPrint print = JasperFillManager.fillReport(pathJASPER, parameters, crearConexion());
                return print;            
            } catch (JRException e) {
                System.err.println(e);
            } 
        } return null;
    }
    
    private static final int RUTA_X_VEHICULO = 3;
    
    // Genera reporte a traves de coleccion de datos generados por codigo.
    public JasperPrint generarReporteDesdeCodigo (int tipoReporte, String simboloMoneda) {
        
        if (hparametrosBase != null && hparametrosBase.size() > 0) {
            
            // Establece parametros jasper
            Map parameters = otrosParametros(simboloMoneda);
            
            String pathJASPER = pathReporte + "/" + hparametrosBase.get("nombreReporte") + ".jasper";
            pathJASPER = repararDelimitador(pathJASPER); 
            
            JRBeanCollectionDataSource dataSource = null;
            
            switch (tipoReporte) {
                case RUTA_X_VEHICULO: {
                    ArrayList<RutaXVehiculo_r> lst_rxv = reporteRutaXVehiculo();
                    dataSource = new JRBeanCollectionDataSource(lst_rxv);
                    break;
                }
            }
            
            try {
                JasperPrint print = JasperFillManager.fillReport(pathJASPER, parameters, dataSource);                
                //JasperPrint print = JasperFillManager.fillReport(pathJASPER, parameters, crearConexion());
                return print;            
            } catch (JRException e) {
                System.err.println(e);
            } 
        } return null;
    }
    
    // Reporte 3: RUTA X VEHICULO
    public ArrayList<RutaXVehiculo_r> reporteRutaXVehiculo() {
        
        // Obtencion de informacion desde codigo
        ParametrosReporte pr = new ParametrosReporte(hparametrosBase);
        ReportesBD rpt = new ReportesBD();
        rpt.RutaXVehiculo(pr);
        ArrayList<ArrayList<RutaXVehiculo>> lst = rpt.getLstRutaXVehiculo();

        // Lista RutaXVehiculo plana
        ArrayList<RutaXVehiculo_r> lst_rxv = new ArrayList<>();
        
        // Estructuracion de datos obtenidos
        RutaXVehiculo_r rxv;
        for (int i = 0; i < lst.size(); i++) {
            ArrayList<RutaXVehiculo> pts = lst.get(i);

            RutaXVehiculo base_salida  = pts.get(0);
            RutaXVehiculo base_llegada = pts.get(pts.size()-1);

            int numInicial = base_salida.getNumeracion();
            int numFinal   = base_llegada.getNumeracion();

            rxv = new RutaXVehiculo_r();                
            rxv.setNombreRuta(base_salida.getRuta());            
            rxv.setHoraSalida(base_salida.getHoraRealLlegada());
            rxv.setHoraLlegada(base_llegada.getHoraRealLlegada());
            rxv.setNumeracionInicial(numInicial);
            rxv.setNumeracionFinal(numFinal);
            rxv.setNumeroVuelta(base_salida.getNumeroVuelta());
            rxv.setCantidadPasajeros(numFinal - numInicial);                

            rxv.setPuntos(pts);
            JRBeanCollectionDataSource dataSourceIn = new JRBeanCollectionDataSource(pts);  
            rxv.setDataSource(dataSourceIn);

            lst_rxv.add(rxv);
        }
        
        return lst_rxv;
    }        
    
    // Crea estructura de datos (tabla hash) en la que se almacenan
    // parametros usados en la generacion de los reportes.
    public Map otrosParametros (String simboloMoneda) 
    {        
        final Map p = new HashMap();
        // Iconos generales
        final String pathLogo       = pathPlantilla + "/logo.png";
        final String pathIcon       = pathPlantilla + "/icon.png";
        final String pathIconTitulo = pathPlantilla + "/titulo.png";
        final String pathFooter     = pathPlantilla + "/footer.png";
        // Otros iconos
        final String pathIcon_conductor = pathPlantilla + "/conductor.png";
        final String pathIcon_empresa   = pathPlantilla + "/empresa.png";
        final String pathIcon_ruta      = pathPlantilla + "/ruta.png";
        final String pathIcon_tarifa    = pathPlantilla + "/tarifa.png";
        final String pathIcon_vehiculo  = pathPlantilla + "/vehiculo.png";
        final String pathIcon_vc        = pathPlantilla + "/vc.png";
        final String pathIcon_check     = pathPlantilla + "/check.png";
        final String pathIcon_uncheck   = pathPlantilla + "/uncheck.png";
        
        pathSubreporte += "/";
        pathSubreporte = repararDelimitador(pathSubreporte);
                
        Date fechaInicio = toDate(hparametrosBase.get("fechaInicio"));
        Date fechaFinal  = toDate(hparametrosBase.get("fechaFinal"));                
        String titulo    = hparametrosBase.get("tituloReporte");        
        
        int idUsuario  = Integer.parseInt(hparametrosBase.get("idUsuario"));
        int idEmpresa  = Integer.parseInt(hparametrosBase.get("idEmpresa"));        
        
        String subtitulo;
        if (fechaInicio != null && fechaFinal != null) {
            subtitulo = (fechaInicio.equals(fechaFinal)) 
                    ? "Del día " + fecha.format(fechaInicio)
                    : "Del día " + fecha.format(fechaInicio) + " al día " + fecha.format(fechaFinal);
            subtitulo = repararSubtitulo(subtitulo);
        } else if (fechaInicio != null) {
            subtitulo = "Del día " + fecha.format(fechaInicio);
            subtitulo = repararSubtitulo(subtitulo);
        } else {
            subtitulo = "";
        }
        
        p.put("NOMBRE_USUARIO", hparametrosBase.get("nombreUsuario"));        
        p.put("FECHA_INICIAL", fechaInicio);
        p.put("FECHA_FINAL", fechaFinal);        
        p.put("USUARIO_PK", idUsuario);
        p.put("EMPRESA_PK", idEmpresa);        
        p.put("SUBREPORT_DIR", pathSubreporte);           
        p.put("FECHA_IMPRESION", fechaImpresion());        
        
        p.put("FECHA_INICIAL_SOLICITUD", hparametrosBase.get("fechaInicio"));
        p.put("FECHA_FINAL_SOLICITUD", hparametrosBase.get("fechaFinal"));
        p.put("SIMBOLO_MONEDA", simboloMoneda);
        
        p.put("REPORT_TITLE", titulo);
        p.put("REPORT_SUBTITLE", subtitulo);
        p.put("REPORT_LOGO", new File(pathLogo).getPath());
        p.put("REPORT_ICON", new File(pathIcon).getPath());
        p.put("REPORT_ICON_TITLE", new File(pathIconTitulo).getPath());
        p.put("REPORT_FOOTER", new File(pathFooter).getPath());        
        p.put("ICON_CONDUCTOR", new File(pathIcon_conductor).getPath());        
        p.put("ICON_RUTA", new File(pathIcon_ruta).getPath());
        p.put("ICON_TARIFA", new File(pathIcon_tarifa).getPath());
        p.put("ICON_VEHICULO", new File(pathIcon_vehiculo).getPath());
        p.put("ICON_VC", new File(pathIcon_vc).getPath());
        p.put("ICON_CHECK", new File(pathIcon_check).getPath());
        p.put("ICON_UNCHECK", new File(pathIcon_uncheck).getPath());                
        
        p.put("REPORT_EMPRESA_NAME", hparametrosBase.get("nombreEmpresa"));
        p.put("REPORT_EMPRESA_NIT", hparametrosBase.get("nitEmpresa"));
        
        // Etiquetas
        if (etiquetas != null) {
            p.put("ETQ_P1", etiquetas.getEtqRepPasajeros1());
            p.put("ETQ_P2", etiquetas.getEtqRepPasajeros2());
            p.put("ETQ_P3", etiquetas.getEtqRepPasajeros3());
            p.put("ETQ_P4", etiquetas.getEtqRepPasajeros4());
            p.put("ETQ_P5", etiquetas.getEtqRepPasajeros5());
            p.put("ETQ_T1", etiquetas.getEtqRepTotal1());
            p.put("ETQ_T2", etiquetas.getEtqRepTotal2());
            p.put("ETQ_T3", etiquetas.getEtqRepTotal3());
            p.put("ETQ_T4", etiquetas.getEtqRepTotal4());
            p.put("ETQ_T5", etiquetas.getEtqRepTotal5());
            p.put("ETQ_T6", etiquetas.getEtqRepTotal6());
            p.put("ETQ_T7", etiquetas.getEtqRepTotal7());
            p.put("ETQ_T8", etiquetas.getEtqRepTotal8());
//            p.put("ETQ_T9", etiquetas.getEtq_rep_total9());
//            p.put("ETQ_T10", etiquetas.getEtq_rep_total10());
        }
        
        if (incluirVehiculo) {
            int idVehiculo = Integer.parseInt(hparametrosBase.get("idVehiculo"));
            int capacidad  = Integer.parseInt(hparametrosBase.get("capacidad"));
            p.put("VEHICULO_PLACA", hparametrosBase.get("placa"));
            p.put("VEHICULO_NUMINTERNO", hparametrosBase.get("numInterno"));        
            p.put("VEHICULO_PK", idVehiculo);
            p.put("VEHICULO_CAPACIDAD", capacidad);
        }
        
        String strVehiculos = "";
        if (incluirVehiculos)   
            strVehiculos = hparametrosBase.get("strVehiculos");
        if (incluirTotalidadVehiculos)
            strVehiculos = MovilBD.selectStringId(idEmpresa);        
        if (incluirVehiculosPropietario) {                        
            ArrayList<Movil> lst_mv    = MovilBD.selectByOwner(idUsuario);            
            List<Integer> lstVehiculos = new ArrayList<>();
            for (Movil mv : lst_mv) {
                lstVehiculos.add(mv.getId());
            }
            strVehiculos = null;
            p.put("LISTA_VEHICULOS_PK", lstVehiculos);
        }
        
        // Asigna vehiculos como listado de identificadores
        // (Se convierte cadena formateada en lista)
        if (strVehiculos != null && strVehiculos != "") {
            List<Integer> lstVehiculos = toList(strVehiculos);
            p.put("LISTA_VEHICULOS_PK", lstVehiculos);
        }
        
        if (incluirRuta) {
            int idRuta = Integer.parseInt(hparametrosBase.get("idRuta"));
            String nombreRuta = hparametrosBase.get("nombreRuta");
            p.put("RUTA_PK", idRuta);
            p.put("NOMBRE_RUTA", nombreRuta);
        }        
        
        String strRutas = "";
        if (incluirRutas) 
            strRutas = hparametrosBase.get("strRutas");                    
        if (incluirTotalidadRutas) 
            strRutas = RutaBD.selectStringId(idEmpresa);        
            
        // Asigna rutas como listado de identificadores
        // (Se convierte cadena formateada en lista)
        if (strRutas != null && strRutas != "") {
            List<Integer> lstRutas = toList(strRutas);
            p.put("LISTA_RUTAS_PK", lstRutas);
        }        
        
        if (incluirAlarma) {            
            String strAlarmas = hparametrosBase.get("strAlarmas");
            List<Integer> lstAlm = toList(strAlarmas);
            p.put("LISTA_ALARMAS_PK", lstAlm);
        }
        
        if (incluirBase) {            
            int idBase = Integer.parseInt(hparametrosBase.get("idBase"));
            p.put("BASE_PK", idBase);
            p.put("NOMBRE_BASE", hparametrosBase.get("nombreBase"));
        }
        
        p.put("GPS_CONNECTION", crearConexionExterna());
        
        return p;
    }
    
    public List<Integer> toList(String s) {
        List<Integer> lst = new ArrayList<Integer>();
            
        if (s.indexOf(",") >= 0) {
            String elemts[] = s.split(",");                
            for (String elemt : elemts) {
                lst.add(Integer.parseInt(elemt));
            }                
        } else {
            lst.add(Integer.parseInt(s));
        }
        
        return lst;
    }
    
    // Convierte delimitador unix a windows
    public String repararDelimitador (String path) {
        if (SystemUtils.IS_OS_WINDOWS) {
            path = path.replace("/", "\\");
        }
        return path;
    }
    
    // Sustituye dia/mes de ingles a español
    public String repararSubtitulo (String sub) {
        
        String dia_en, dia_es, mes_en, mes_es; 
        
        for (String dia : diasSemana) {
            dia_en = dia.split(":")[0];  
            dia_es = dia.split(":")[1];  
            
            if (sub.contains(dia_en)) {
                sub = sub.replaceAll(dia_en, dia_es);
            }
        } 
        
        for (String mes : meses) {
            mes_en = mes.split(":")[0];  
            mes_es = mes.split(":")[1];  
            
            if (sub.contains(mes_en)) {
                sub = sub.replaceAll(mes_en, mes_es);
            }
        }        
        
        return sub;
    }
    
    // Cambia zona horaria a America/Bogota
    public String fechaImpresion () {
        
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        
        fmt.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        
        String dateFmt = fmt.format(date);
        String dateArr[] = dateFmt.split(" ");
        
        String rs = "Día: " + dateArr[0] + "   Hora: " + dateArr[1];
        
        return rs;
        //return fmt.format(date);
    }
    
    // Convierte fecha en cadena de texto a objeto Date
    public Date toDate (String sd) {
        if (sd == "")
            return null;
        try {
            Date d = fechaSQL.parse(sd);
            return d;
        } catch (ParseException e) {
            System.err.println(e);
        } return null;
    }
    
    // Crea conexion con base de datos local
    public Connection crearConexion () {
        pila_con = PilaConexiones.obtenerInstancia();
        con = pila_con.obtenerConexion();        
        return con;
    }
    
    // Crea conexion con base de datos especifica
    public Connection crearConexionNueva () {
        pila_con = new PilaConexiones();
        con = pila_con.obtenerConexionNueva();
        return con;
    }
    
    // Crea conexion con base de datos no local
    public Connection crearConexionExterna () {
        ConexionExterna conext = new ConexionExterna();
        try {
            return conext.conectar();
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        return null;
    }
    
    // Libera conexion con base de datos (local/no local)
    public void liberarConexion () {
        if (pila_con != null &&
                 con != null) {
            pila_con.liberarConexion(con);
        }
    }
    
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>> Utilidades globales
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    // Extrae puntos de despacho de un listado de puntos especificado.
    // (Actua sobre los puntos de la primera vuelta).
    public static ArrayList<String> extraerPuntosDespacho(ArrayList<PlanillaDespacho> lst) {
        ArrayList<String> lst_pto = new ArrayList<>();
                
        for (PlanillaDespacho pd : lst) {
            if (pd.getNumeroVuelta() == 1) {
                lst_pto.add(pd.getPunto());
            } else {
                if (lst_pto.size() > 0)
                    return lst_pto;
                else
                    continue;
            }
        }
        return lst_pto;
    }
    
    public static final String EMPRESA_FUSACATAN = "fusacatan tierragrata tierra grata";
    
    // Comprueba si cadena de texto contiene o es una empresa en especifico.
    public static boolean esEmpresa(String nombreEmpresa, String constanteEmpresa) {
        
        if (nombreEmpresa == null ||
            constanteEmpresa == null)
            return false;
        
        nombreEmpresa    = nombreEmpresa.toLowerCase();
        constanteEmpresa = constanteEmpresa.toLowerCase();
        
        if (constanteEmpresa.contains(nombreEmpresa)) {
            return true;
        }
        return false;
    }
}
