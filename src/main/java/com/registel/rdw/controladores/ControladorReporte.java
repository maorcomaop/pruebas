/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.CalificacionConductorBD;
import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.UsuarioBD;
import com.registel.rdw.logica.ConfCalificacionConductor;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Usuario;
import static com.registel.rdw.utils.ConstantesSesion.obtenerEtiquetasLiquidacionPerfil;
import com.registel.rdw.utils.MakeExcel;
import com.registel.rdw.utils.ParametrosReporte;
import com.registel.rdw.utils.PrintOutExcel;
import com.registel.rdw.utils.ReporteUtil;
import com.registel.rdw.utils.ReporteUtilExcel;
import com.registel.rdw.utils.ReporteWeb;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorReporte extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {    
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        if (!Session.alive(request)) {
            getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request, response);
        }
        
        String requestURI = request.getRequestURI();
        String url = "";
        
        if (requestURI.endsWith("/generarReporte")) {
            url = generarReporte(request, response);
        } else if (requestURI.endsWith("/dataReporte")) {
            url = cargarDatos(request, response);
        }
        
        // ======================== Reportes via web ===========================
        
        // Reporte web intermedio - cumplimiento ruta
        if (requestURI.endsWith("/reporteCumplimientoRuta")) {
            url = reporteCumplimientoRutaWeb(request, response);
        }
        
        // Reporte web intermedio - incumplimiento punto por vehiculo
        if (requestURI.endsWith("/reporteIncumplimientoPuntoXVehiculo")) {
            url = reporteIncumplimientoPuntoXVehiculoWeb(request, response);
        }
        
        if (requestURI.endsWith("/reporteIncumplimientoPuntoXConductor")) {
            url = reporteIncumplimientoPuntoXConductorWeb(request, response);
        }
        
        // Exportacion a excel - desde reporte web
        if (requestURI.endsWith("/reporteWeb2Excel")) {
            toExcelFromRequest(request, response);
        }
        
        if (url != "") {
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }    
    
    // Procesa solicitud para impresion de reporte cumplimiento de ruta.
    public String reporteCumplimientoRutaWeb (HttpServletRequest request,
            HttpServletResponse response) throws IOException {
                
        String idRuta        = request.getParameter("idRuta");
        String nombreRuta    = request.getParameter("nombreRuta");
        String fechaInicio   = request.getParameter("fechaInicio");
        String fechaFinal    = request.getParameter("fechaFinal");
        String meta          = request.getParameter("meta");
        String metaReal      = request.getParameter("metaReal");
        String tituloReporte = "CUMPLIMIENTO DE RUTA POR VEHÍCULO";
        String nombreReporte = "reporte_CumplimientoRuta";
        int tipoReporte      = 24; /* Se usa consulta de reporte cumplimiento ruta x vehiculo */
        
        ParametrosReporte pr = new ParametrosReporte();
        
        if (pr != null) {
            pr.setTituloReporte(tituloReporte);
            pr.setTipoReporte(tipoReporte);
            pr.setNombreReporte(nombreReporte);
            pr.setFechaInicioStr(fechaInicio);
            pr.setFechaFinalStr(fechaFinal);
            pr.setIdRuta(Restriction.getNumber(idRuta));
            pr.setNombreRuta(nombreRuta);
            pr.setMeta(Restriction.getNumber(meta));
            pr.setMeta_real(Restriction.getRealNumber(metaReal));
            ReporteWeb rptw = new ReporteWeb(pr, request, response);
            return rptw.generarReporteWeb(tipoReporte);
        } else {        
        }
        
        return "/app/reportes/reporte_ConsolidadoRutas.jsp";
    }
    
    // Procesa solicitud para impresion de reporte incumplimiento de puntos por vehiculo.
    public String reporteIncumplimientoPuntoXVehiculoWeb (HttpServletRequest request,
            HttpServletResponse response) throws IOException {
                
        String idRuta        = request.getParameter("idRuta");
        String idPunto       = request.getParameter("idPunto");
        String nombreRuta    = request.getParameter("nombreRuta");
        String nombrePunto   = request.getParameter("nombrePunto");
        String fecha         = request.getParameter("fecha");
        String nombreReporte = "reporte_IncumplimientoPuntoXVehiculo";
        int tipoReporte      = 28;
        
        ParametrosReporte pr = new ParametrosReporte();
        
        if (pr != null) {        
            pr.setTipoReporte(tipoReporte);
            pr.setIdRuta(Restriction.getNumber(idRuta));
            pr.setIdPunto(Restriction.getNumber(idPunto));
            pr.setNombreRuta(nombreRuta);           
            pr.setNombrePunto(nombrePunto);
            pr.setNombreReporte(nombreReporte);
            pr.setFechaInicioStr(fecha);
            ReporteWeb rptw = new ReporteWeb(pr, request, response);
            return rptw.generarReporteWeb(tipoReporte);
        }
        
        return "/app/reportes/reporte_IncumplimientoPuntoXRuta.jsp";
    }
    
    // Procesa solicitud para impresion de reporte incumplimiento de puntos por conductor.
    public String reporteIncumplimientoPuntoXConductorWeb(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String idRuta           = request.getParameter("idRuta");
        String idPunto          = request.getParameter("idPunto");
        String nombreRuta       = request.getParameter("nombreRuta");
        String nombrePunto      = request.getParameter("nombrePunto");
        String idVehiculo       = request.getParameter("idVehiculo");
        String placa            = request.getParameter("placa");
        String numeroInterno    = request.getParameter("numeroInterno");
        String fecha            = request.getParameter("fecha");
        String nombreReporte    = "reporte_IncumplimientoPuntoXConductor";
        int tipoReporte         = 29;
        
        ParametrosReporte pr = new ParametrosReporte();
        
        if (pr != null) {            
            pr.setTipoReporte(tipoReporte);
            pr.setIdRuta(Restriction.getNumber(idRuta));
            pr.setIdPunto(Restriction.getNumber(idPunto));
            pr.setNombreRuta(nombreRuta);
            pr.setNombrePunto(nombrePunto);
            pr.setFechaInicioStr(fecha);
            pr.setIdVehiculo(Restriction.getNumber(idVehiculo));
            pr.setPlaca(placa);
            pr.setNumeroInterno(numeroInterno);
            pr.setNombreReporte(nombreReporte);
            ReporteWeb rptw = new ReporteWeb(pr, request, response);
            return rptw.generarReporteWeb(tipoReporte);
        }
        
        return "/app/reportes/reporte_IncumplimientoPuntoXRuta.jsp";
    }
    
    // Procesa solicitud para generacion de reportes en excel.
    // (Importante: Se extrae el valor de las variables de todos los reportes
    // que se generan en excel, sin importar si se usan o no en el reporte actual).
    public void toExcelFromRequest(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String tituloReporte = request.getParameter("tituloReporte");
        String tipoReporte   = request.getParameter("tipoReporte");
        String nombreReporte = request.getParameter("nombreReporte");
        String nombreRuta    = request.getParameter("nombreRuta");
        String idRuta        = request.getParameter("idRuta");
        String fechaInicio   = request.getParameter("fechaInicio");
        String fechaFinal    = request.getParameter("fechaFinal");
        String meta          = request.getParameter("meta");
        String metaReal      = request.getParameter("metaReal");
        String listaVehPlaca = request.getParameter("listaVehiculosPlaca");
        
        ParametrosReporte pr = new ParametrosReporte();
        
        if (pr != null) {
            pr.setTituloReporte(tituloReporte);
            pr.setTipoReporte(Restriction.getNumber(tipoReporte));
            pr.setNombreReporte(nombreReporte);
            pr.setNombreRuta(nombreRuta);
            pr.setIdRuta(Restriction.getNumber(idRuta));
            pr.setFechaInicioStr(fechaInicio);
            pr.setFechaFinalStr(fechaFinal);
            pr.setMeta(Restriction.getNumber(meta));
            pr.setMeta_real(Restriction.getRealNumber(metaReal));
            pr.setListaVehiculosPlaca(listaVehPlaca);
            
            PrintOutExcel poe = new PrintOutExcel();
            poe.print(request, response, pr);
        }
        
        /*
        HttpSession session  = request.getSession();
        ParametrosReporte pr = (ParametrosReporte) session.getAttribute("parametrosReporte");
        
        if (pr != null) {
            
            // Reporte editable XLS            
            ReporteUtilExcel rue = new ReporteUtilExcel();        
            pr.setTipoReporte(Restriction.getNumber(tipoReporte));
            pr.setNombreReporte(nombreReporte);
                        
            MakeExcel rpte = rue.crearReporte(pr.getTipoReporte(), false, pr);            
            String nombreArchivo = pr.getNombreReporte() + ".xls";            

            //response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/ms-excel");            
            response.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo);

            HSSFWorkbook file = rpte.getExcelFile();
            file.write(response.getOutputStream());            
            response.flushBuffer();
            response.getOutputStream().close();            
        }
        */
    }
                
    /* ========================= Generacion normal ========================== */
    
    // Procesa solicitud para generacion de reporte segun parametros establecidos
    // y tipo de reporte (PDF, Excel o Web).
    // Se delega proceso de generacion, unicamente se notifica o imprime resultado obtenido.
    public String generarReporte (HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Empresa empresaSesion = (Empresa)request.getSession().getAttribute("emp");
        String simbolo_moneda;
        
        if (empresaSesion != null && empresaSesion.getSimboloMoneda() != null && 
                !empresaSesion.getSimboloMoneda().isEmpty()) {
            simbolo_moneda = empresaSesion.getSimboloMoneda();
        } else {
            simbolo_moneda = "$";
        }
        
        // Se restauran variables iniciadas
        ReporteUtil.restaurarValores();
        
        String tipoReporte  = request.getParameter("tipoReporte");
        String fechaInicio  = request.getParameter("fechaInicio");
        String fechaFinal   = request.getParameter("fechaFinal");
        String placa        = request.getParameter("splaca");  // id,placa,numInterno
        String mplaca       = request.getParameter("smplaca_v");
        String esteDia      = request.getParameter("esteDia");
        String tipoArchivo  = request.getParameter("tipoArchivo");
        String ruta         = request.getParameter("sruta");
        String mruta        = request.getParameter("smruta_v");
        String malarma       = request.getParameter("smalarma_v");
        String base         = request.getParameter("sbase");
        String idLiquidador = request.getParameter("sliquidador");
        String meta         = request.getParameter("smeta");
        String mconductor   = request.getParameter("smconductor_v");        
                
        boolean dia_actual  = verificarDiaActual(fechaInicio, fechaFinal);
        
//        Etiquetas etiquetas = null;
//        etiquetas = LiquidacionBD.searchTags();
        ConfiguracionLiquidacion etiquetas = obtenerEtiquetasLiquidacionPerfil(request);
        
        if (etiquetas != null) {
            ReporteUtil.establecerEtiquetas(etiquetas);
        }        
        
        //String reportesPath = "D:\\rdw\\";        
        
        // En caso de estar sobre un SO_WIN, se quita ultimo delimitador
        String reportesPath = getServletContext().getRealPath("");
        if (reportesPath.endsWith("\\")) {
            reportesPath = reportesPath.substring(0, reportesPath.length()-1);            
        }
        
        Map<String,String> h = new HashMap<String,String>();
         
        h.put("tipoReporte", tipoReporte);
        h.put("fechaInicio", fechaInicio);
        h.put("fechaFinal", fechaFinal);        
        h.put("tipoArchivo", tipoArchivo);
        h.put("path", reportesPath);    
       
        
        // Informacion de usuario en sesion
        HttpSession session = request.getSession();        
        Usuario u = (Usuario) session.getAttribute("login");
        
        h.put("idUsuario", "" + u.getId());
        h.put("nombreUsuario", u.getNombre() +" "+ u.getApellido());
        h.put("usuarioPropietario", (u.esPropietario()) ? "1" : "0");
        
        // Nombre y titulo del reporte
        String nt[] = nombreReporte(tipoReporte, dia_actual).split(":");
        
        // Verifica si se considera una o todas las rutas
        // para reportes nivel_ocupacion, despachador, descripcion ruta,
        // cumplimiento ruta por conductor
        int ntp = Integer.parseInt(tipoReporte);
        if (ntp == 5 || ntp == 14 || ntp == 16) {
            if (!ruta.equals("0")) { // Se elige una ruta
                nt[0] += "_X1Ruta";
                h.put("unaRuta", "t");
            } else {
                h.put("unaRuta", "f");
            }
        }        
        if (ntp == 25) {
            String una_ruta = (!ruta.equals("0")) ? "t" : "f";
            h.put("unaRuta", una_ruta);
        }
        
        h.put("nombreReporte", nt[0]);
        h.put("tituloReporte", nt[1]);
        
        // Verifica si es reporte gerencia, gerencia x vehiculo para incluir
        // todos los vehiculos o todas las rutas
        ReporteUtil.incluirTotalidadRutas       = false;
        ReporteUtil.incluirTotalidadVehiculos   = false;
        ReporteUtil.incluirVehiculosPropietario = false;
        
        if (ntp == 15) {
            ReporteUtil.incluirTotalidadVehiculos = true;                        
        } else if (ntp == 18) {
            ReporteUtil.incluirTotalidadRutas = true;
        } else if (ntp == 11) {
            if (u.esPropietario()) {                
                ReporteUtil.incluirVehiculosPropietario = true;
            } else {
                ReporteUtil.incluirTotalidadVehiculos = true;                        
            }
        }
        
        // Verifica si es reporte ruta x vehiculo para generar
        // reporte desde codigo
        if (ntp == 3) {          
            ReporteUtil.desdeCodigo = true;
        } else {
            ReporteUtil.desdeCodigo = false;
        }
        
        // Verifica si es reporte ruta x vehiculo, vehiculo x ruta, despachador
        // para establecer un dia en parametro fecha y no un rango
        if (ntp == 3 || ntp == 11 || ntp == 16) {
            h.put("fechaFinal", fechaInicio);
        } 
        
        // No se necesitan fechas para reporte estadistico y descripcion ruta
        if (ntp == 13 || ntp == 14) {
            h.put("fechaInicio", "");
            h.put("fechaFinal", "");
        }
        
        // Eleccion de reportes gerencia segun empresa
        if (ntp == 15 || ntp == 18) {
            String empresa = u.getNombreEmpresa();
            String nombre_reporte = h.get("nombreReporte");
            if (ReporteUtil.esEmpresa(empresa, ReporteUtil.EMPRESA_FUSACATAN)) {
                nombre_reporte += "Fusa";
                h.put("nombreReporte", nombre_reporte);
            }                        
        }
        
        // Reportes de liquidacion
        if (ntp == 19 || ntp == 20 || ntp == 21 || ntp == 22) {            
            h.put("fechaInicio", fechaInicio + " 00:00:00");
            h.put("fechaFinal", fechaFinal + " 23:59:59");
           
            //System.out.println("---> "+EtiquetasLiquidacion.getEtq_total1() );
            /*SI EL REPORTE ES LIQUIDACION POR LIQUIDADOR SE MODIFICA EL NOMBRE SI LA EMPRESA ES DIFERENTE DE NEIVA*/
            if (ntp == 21) {
                if ((u.getNombreEmpresa().equalsIgnoreCase("FUSACATAN")) || 
                    (u.getNombreEmpresa().equalsIgnoreCase("TIERRA GRATA")) || 
                    (u.getNombreEmpresa().equalsIgnoreCase("Tierragrata")))  {
                    h.put("nombreReporte", "Reporte_LiquidacionXLiquidador_new_dcto");                    
                }
            }
            
            if (tipoArchivo.equals("w")) {
                Usuario liquidador = UsuarioBD.getById(Restriction.getNumber(idLiquidador));
                if (liquidador != null) { 
                    String nom = liquidador.getNombre();
                    String ape = liquidador.getApellido();                    
                    h.put("idUsuarioLiquidador", idLiquidador);
                    h.put("nombresUsuarioLiquidador", ape + " " + nom);
                }
            } else {
                h.put("idUsuario", idLiquidador);
            }
        }  
        
        if (ntp == 23 || ntp == 24 || ntp == 25) {
            h.put("meta", "" + meta);
            if (tipoArchivo.equals("r")) {
                ReporteUtil.reporteWeb = true;
            }
        }
        
        if (ntp == 26 || ntp == 27) {
            h.put("fechaInicio", fechaInicio);
            h.put("fechaFinal", fechaFinal);
            ReporteUtil.reporteWeb = true;
        }                
        
        // ======================= Verificacion de campos ======================
        
        // Id, placa, numeroInterno vehiculo
        if (placa.indexOf(",") >= 0) {
            h.put("idVehiculo", placa.split(",")[0]);
            h.put("placa",      placa.split(",")[1]);
            h.put("numInterno", placa.split(",")[2]);
            h.put("capacidad",  placa.split(",")[3]);
            ReporteUtil.incluirVehiculo = true;
        } else
            ReporteUtil.incluirVehiculo = false;
        
        // Id de multiples vehiculos
        if (mplaca != "" || mplaca.indexOf(",") >= 0) {
            h.put("strVehiculos", mplaca);
            h.put("strVehiculosPlaca", id2placa(mplaca));
            ReporteUtil.incluirVehiculos = true;
        } else
            ReporteUtil.incluirVehiculos = false;
        
        // Id ruta y nombre ruta
        if (ruta.indexOf(",") >= 0) {
            String arrayRuta[] = ruta.split(",");
            h.put("idRuta",     arrayRuta[0]);
            h.put("nombreRuta", arrayRuta[1]);
            ReporteUtil.incluirRuta = true;
        } else 
            ReporteUtil.incluirRuta = false;
        
        // Id ruta        
        if (mruta != "" || mruta.indexOf(",") >= 0) {
            h.put("strRutas", mruta);
            ReporteUtil.incluirRutas = true;
        } else
            ReporteUtil.incluirRutas = false;
        
        // Id alarma
        if (malarma != "" || malarma.indexOf(",") >= 0) {
            h.put("strAlarmas", malarma);
            ReporteUtil.incluirAlarma = true;
        } else 
            ReporteUtil.incluirAlarma = false;        
        
        // Id base, nombre
        if (base.indexOf(",") >= 0) {
            String arrayBase[] = base.split(",");
            h.put("idBase", arrayBase[0]);
            h.put("nombreBase", arrayBase[1]);
            ReporteUtil.incluirBase = true;
        } else 
            ReporteUtil.incluirBase = false;        
        
        // Se verifica y establece parametros de empresa
        Empresa emp = EmpresaBD.getById(u.getIdempresa());        
        if (emp != null) {
            h.put("nombreEmpresa",  emp.getNombre());
            h.put("nitEmpresa",     emp.getNit());
            h.put("idEmpresa", "" + emp.getId());
        } else {
            request.setAttribute("msg", "* Usuario no tiene relaci&oacute;n y/o permisos adecuados con la empresa.");
            request.setAttribute("msgType", "alert alert-warning");
            return "/app/reportes/generaReporte.jsp";
        }
        
        int id_ruta   = Restriction.getNumber(h.get("idRuta"));        
        String pplaca = "'" + h.get("placa") + "'";
        
        // Se verifica si existe despacho para cruzar sus datos,
        // en caso contrario se extrae los datos unicamente desde tabla info. registradora
        if (ntp == 3) {
            if (!DespachoBD.existe_planilla_en(fechaInicio, pplaca)) {
                ReporteUtil.desdeCodigo = true; 
                h.put("nombreReporte", "reporte_RutaXVehiculo2");
                h.put("cruzarDespacho", "0");                
            } else {
                ReporteUtil.desdeCodigo = false; // Reporte con cruce despacho es dinamico
                h.put("cruzarDespacho", "1");                
            }
        }
        if (ntp == 11) {
            if (!DespachoBD.existe_planilla_en(fechaInicio, id_ruta)) {
                h.put("nombreReporte", "reporte_VehiculosXRuta");
                h.put("cruzarDespacho", "0");                
            } else {
                h.put("cruzarDespacho", "1");
            }
        }
        
        if (ntp == 30) {
            h.put("fechaInicio", fechaInicio);
            h.put("fechaFinal", fechaFinal);
            h.put("strConductores", mconductor);
            ReporteUtil.reporteWeb = true;
            
            // Se verifica que exista una configuracion de desempeno activa
            ConfCalificacionConductor ccc = CalificacionConductorBD.confCalificacionConductor();
            if (ccc == null) {
                request.setAttribute("msg", "* No existe ninguna configuraci&oacute;n de desempe&ntilde;o registrada. Por favor registre una.");
                request.setAttribute("msgType", "alert alert-warning");
                request.setAttribute("result_error", "1");
                return "/app/reportes/generaReporte.jsp";
            }
        }
        
        // Creacion de reporte
        JasperPrint print = null;
        if (tipoArchivo.equals("r")) {
            
            // Reporte de solo lectura << PDF / Web >>
            ReporteUtil rpt;
            
            if (ReporteUtil.reporteWeb) {            
                ReporteWeb rptw = new ReporteWeb(h, request, response);
                return rptw.generarReporteWeb(ntp);                
            
            } else if (ReporteUtil.desdeCodigo) {
                rpt = new ReporteUtil(h);
                print = rpt.generarReporteDesdeCodigo(Integer.parseInt(h.get("tipoReporte")), simbolo_moneda);
                
            } else {
                rpt = new ReporteUtil(h);
                print = rpt.generarReporte(simbolo_moneda);
            }
            
        } else {
            
            // Reporte editable << XLS >>
            ReporteUtilExcel rue = new ReporteUtilExcel();
            MakeExcel rpte = rue.crearReporte(ntp, dia_actual, h, u, etiquetas);
            
            // Restablece placa de reportes que no necesitan
            restablecerParametro("placa", ntp, h);
            String splaca = h.get("placa");
            splaca = (splaca == null || splaca == "") ? "" : "_" + splaca;
            String nombreArchivo = h.get("nombreReporte") + splaca + ".xls";
            
            //response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/ms-excel");            
            response.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo);
            
            HSSFWorkbook file = rpte.getExcelFile();
            file.write(response.getOutputStream());            
            response.flushBuffer();
            response.getOutputStream().close();
            
            return "/app/reportes/generaReporte.jsp";
        }
        
        // Impresion de reporte de solo lectura PDF
        if (print != null) {
            
            try {                                
                // Se comprueba existencia de datos en el reporte obtenido
                boolean hayDatos = true;
                List<JRPrintPage> pp = print.getPages();                
                if (pp.size() > 0) {
                    JRPrintPage ppp = pp.get(0);
                    if (ppp.getElements().size() <= 0) 
                        hayDatos = false;                    
                } else 
                    hayDatos = false;
                
                if (!hayDatos) {
                    request.setAttribute("msg", "* Ning&uacute;n dato obtenido en el reporte.");
                    request.setAttribute("msgType", "alert alert-warning");
                    request.setAttribute("data_reporte", "1");
                    return "/app/reportes/generaReporte.jsp";
                }
                
                byte[] bytes = JasperExportManager.exportReportToPdf(print);
                
                // Inicia descarga del reporte
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                
            } catch (JRException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }                        
        }
                return "/app/reportes/generaReporte.jsp";
    }
    
    public boolean verificarDiaActual(String fechaInicial, String fechaFinal) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");                
        
        String fa = f.format(new Date());        
        if (fechaInicial.compareTo(fa) == 0 ||
            fechaFinal.compareTo(fa) == 0)
            return true;
        
        return false;
    }
    
    // Extrae nombre de archivo y titulo del reporte
    // Nota: El orden establecido debe coincidir con el desplegado 
    // en el componente select de la pagina web.
    public String nombreReporte (String sidx, boolean dia_actual) {
        int idx = Integer.parseInt(sidx);
        switch (idx) {
            case 1:  
                if (dia_actual) { return "reporte_PuntosControl_GPS:REPORTE DE PUNTOS DE CONTROL"; }
                else            { return "reporte_PuntosControl:REPORTE DE PUNTOS DE CONTROL"; }
            case 2:  return "reporte_ProduccionXVehiculo:REPORTE DE PRODUCCIÓN POR VEHÍCULO";
            case 3:  return "reporte_RutaXVehiculoDph:REPORTE DE RUTA POR VEHÍCULO";
            case 4:  return "reporte_AlarmasXVehiculo:REPORTE DE ALARMAS POR VEHÍCULO";
            case 5:  return "reporte_NivelOcupacion:REPORTE NIVEL OCUPACIÓN";
            case 6:  return "reporte_Perimetro:REPORTE CONTEOS EN PERÍMETRO POR VEHÍCULO";
            case 7:  return "reporte_ConsolidadoEmpresa:REPORTE CONSOLIDADO POR EMPRESA";
            case 8:  return "reporte_ComparacionProduccionRuta: REPORTE COMPARATIVO DE PRODUCCIÓN POR RUTA";            
            case 9:  return "reporte_ProduccionXRuta:REPORTE DE PRODUCCIÓN POR RUTA";
            case 11: return "reporte_VehiculosXRutaDph:REPORTE DE VEHÍCULOS POR RUTA";
            case 12: return "reporte_VehiculosXAlarma:REPORTE DE VEHÍCULOS POR ALARMA";
            case 13: return "reporte_Estadisticas:REPORTE ESTADÍSTICO";
            case 14: return "reporte_DescripcionRuta:REPORTE DESCRIPCIÓN RUTA";   
            case 15: return "reporte_Gerencia2:REPORTE GERENCIA";
            case 16: return "";
            case 17: return "reporte_Propietario:REPORTE PROPIETARIO";
            case 18: return "reporte_GerenciaXVehiculo:REPORTE GERENCIA POR VEHICULO";
            case 19: return "reporte_ConsolidadoLiquidacion:REPORTE CONSOLIDADO DE LIQUIDACIÓN";
            case 20: return "reporte_ConsolidadoVehiculosNoLiquidados:RESUMEN DE VEHÍCULOS NO LIQUIDADOS";            
            case 21: return "reporte_LiquidacionXLiquidador:REPORTE LIQUIDACIÓN POR LIQUIDADOR";
            case 22: return "reporte_IndicePasajerosXKilometro:REPORTE ÍNDICE PASAJEROS POR KILÓMETRO";
            case 23: return "reporte_ConsolidadoRutas:CONSOLIDADO DE RUTAS";
            case 24: return "reporte_CumplimientoRutaXVehiculo:CUMPLIMIENTO DE RUTA POR VEHÍCULO";
            case 25: return "reporte_CumplimientoRutaXConductor:CUMPLIMIENTO DE RUTA POR CONDUCTOR";
            case 26: return "reporte_ConsolidadoDespachoFmt:REPORTE CONSOLIDADO DESPACHO";
            case 27: case 28: 
            case 29: return "reporte_IncumplimientoPuntoXRuta:REPORTE INCUMPLIMIENTO PUNTOS POR RUTA";
            case 30: return "reporte_calificacionConductor:REPORTE CALIFICACION DE CONDUCTOR";
            case 35: return "reporte_CategoriasDescuentaPasajeros:REPORTE CATEGORIAS POR PASAJEROS";
            case 31: return "reporte_ProductividadPorHora:REPORTE CONSOLIDADO PRODUCTIVIDAD POR HORA";
            default: return null;
        }
    }
    
    // Procesa solicitud de carga de datos (consulta de listados) 
    // necesarios para la generacion de algunos reportes.
    public String cargarDatos(HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("login");
        int idEmpresa = usr.getIdempresa();
        
        ArrayList<Usuario> lst_liquidadores = UsuarioBD.getByPerfil(idEmpresa, 5);
        session.setAttribute("lst_liquidadores", lst_liquidadores);
        request.setAttribute("data_reporte", "1");
        
        return "/app/reportes/generaReporte.jsp";
    }    
    
    // Convierte cadena de texto formateada de id-moviles
    // en cadena de texto formateada de placa-moviles.
    public String id2placa(String lst_id) {
        
        ArrayList<Movil> lst_movil = MovilBD.select();
        String arr_id[]  = lst_id.split(",");
        String lst_placa = "";
        
        for (String id : arr_id) {            
            for (Movil m : lst_movil) {
                String id_m = "" + m.getId();
                if (id_m.equals(id)) {
                    String placa = "'" + m.getPlaca() + "'";
                    lst_placa += (lst_placa == "") ? placa : "," + placa;
                    break;
                }
            }
        }
        
        return lst_placa;
    }
    
    // Restaura parametro placa de estructura (tabla hash)
    // para reportes que no lo necesitan.
    public void restablecerParametro(String parametro, int ntp, Map<String, String> h) {
        
        if (parametro.compareTo("placa") == 0) {
            
            // Reportes que necesitan placa
            int rpt_placa[] = {1,2,3,4,5,6,17};
            boolean rpt_con_placa = false;
            
            for (int rpt : rpt_placa) {
                if (rpt == ntp) {
                    rpt_con_placa = true;
                    break;
                }
            }
            
            if (!rpt_con_placa) {
                h.put("placa", "");
            }
        }        
    }
            
    /*
    
    public ArrayList<Usuario> cargarListaLiquidadores (Usuario usr) {
        
        ArrayList<UsuarioPermisoEmpresa> upeList = 
                UsuarioPermisoEmpresaBD.selectEmpresasPermitidas(usr.getId());

        UsuarioBD uBD = new UsuarioBD();

        ArrayList<Empresa> lst_empresas = new ArrayList<>();
        
        for (UsuarioPermisoEmpresa upe : upeList) {
            Empresa empresa = new Empresa();
            empresa.setId(upe.getIdEmpresa());
            lst_empresas.add(empresa);
        }

        ArrayList<Usuario> lst_liquidadores = uBD.getUsuariosByEmpresaPerfil(lst_empresas, 5);
        return lst_liquidadores;
    }

    */
}
