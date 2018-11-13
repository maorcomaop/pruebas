/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.datos.PilaConexiones;
import com.registel.rdw.datos.UsuarioBD;
import com.registel.rdw.datos.UsuarioPermisoEmpresaBD;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.UsuarioPermisoEmpresa;
import static com.registel.rdw.utils.ConstantesSesion.obtenerEtiquetasLiquidacionPerfil;
import com.registel.rdw.utils.MakeExcel;
import com.registel.rdw.utils.ReporteUtilExcel;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorReporteLiquidacion extends HttpServlet {

    private SimpleDateFormat fechaExtensa = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy");
    private SimpleDateFormat fechaSimple = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        doPost(request, response);
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

        boolean dispatcher = true;
        String requestURI = request.getRequestURI();
        String url = "";

        if (requestURI.endsWith("/liquidacionTotalVehiculos")) {
            url = liquidacionTotalVehiculos(request, response);
        } else if (requestURI.endsWith("/reporteLiquidacionTotalVehiculos")) {
            reporteLiquidacionTotalVehiculos(request, response);
            dispatcher = false;
        } else if (requestURI.endsWith("/liquidacionPorLiquidador")) {
            url = liquidacionPorLiquidador(request, response);
        } else if (requestURI.endsWith("/reporteLiquidacionPorLiquidador")) {
            reporteLiquidacionPorLiquidador(request, response);
            dispatcher = false;
        } else if (requestURI.endsWith("/consolidadoLiquidacion")) {
            url = consolidadoLiquidacion(request, response);
        } else if (requestURI.endsWith("/reporteConsolidadoLiquidacion")) {
            reporteConsolidadoLiquidacion(request, response);
            dispatcher = false;
        }
        
        
        else if (requestURI.endsWith("/indicePasajeroKilometro")) {
            url = indicePasajeroKilometro(request, response);
        }else if (requestURI.endsWith("/reporteIndicePasajeroKilometro")) {
            reporteIPK(request, response);
            dispatcher = false;
        }

        if (dispatcher) {
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }

    public String indicePasajeroKilometro(HttpServletRequest request,
            HttpServletResponse response) {

        String url = "/app/liquidacion/reporteIndicePasajeroKilometroLiqudacion.jsp";
        return url;
    }
    
    
    
    
    public String reporteIPK(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        //String pkEmpresa = request.getParameter("empresa_id");
        HttpSession session = request.getSession();
        String fechaInicial = request.getParameter("fecha_inicio") + " 00:00:00";
        String fechaFinal = request.getParameter("fecha_fin") + " 23:59:59";
        
         Usuario u = (Usuario) session.getAttribute("login");
         Empresa e = EmpresaBD.getById(u.getIdempresa());
         
        if (request.getParameterMap().containsKey("tipo_reporte")) {
            
            int tipoReporte = Integer.parseInt(request.getParameter("tipo_reporte"));
            
            String reportesPath = getServletContext().getRealPath("");
            
            if (reportesPath.endsWith("\\")) {
                reportesPath = reportesPath.substring(0, reportesPath.length() - 1);
            }
            Map parameters;
            parameters = new HashMap();
            parameters.put("tipoReporte", tipoReporte);
            parameters.put("fechaInicio", fechaInicial);
            parameters.put("fechaFinal", fechaFinal);
            parameters.put("idEmpresa", e.getId());
            parameters.put("path", reportesPath);            
            parameters.put("idUsuario", "" + u.getId());
            parameters.put("nombreUsuario", u.getNombre() + " " + u.getApellido());
            
            String nombreReporte = "";
            String tituloReporte = "";

            switch (tipoReporte) {
                case 22:
                    nombreReporte = "reporte_indicepasajeroskilometro";
                    tituloReporte = "REPORTE INDICE PASAJEROS POR KILOMETRO";
                    break;                
                default:
                    break;
            }

            parameters.put("nombreReporte", nombreReporte);
            parameters.put("tituloReporte", tituloReporte);
            
            Empresa emp = UsuarioPermisoEmpresaBD.selectEmpresaPermitida(u.getId());
                        
            if (emp != null) {
                parameters.put("nombreEmpresa", emp.getNombre());
                parameters.put("nitEmpresa", emp.getNit());
                parameters.put("idEmpresa", "" + emp.getId());
            } else {
                request.setAttribute("msg", "* Usuario no tiene relaci&oacute;n y/o permisos adecuados con la empresa.");
                request.setAttribute("msgType", "alert alert-warning");
                return "/app/reportes/generaReporte.jsp";
            }
            
            ConfiguracionLiquidacion etiquetas = obtenerEtiquetasLiquidacionPerfil(request);
            
            
            // Reporte editable XLS            
            ReporteUtilExcel rue = new ReporteUtilExcel();
            
            MakeExcel rpte = rue.crearReporte(tipoReporte, false, parameters, null, etiquetas);
            
            String nombreArchivo = parameters.get("nombreReporte") + ".xls";

            response.setContentType("application/ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

            HSSFWorkbook file = rpte.getExcelFile();
            file.write(response.getOutputStream());
            response.flushBuffer();
            response.getOutputStream().close();
        }
        else
        {
            
        }
        return "/app/liquidacion/reporteConsolidadoLiquidacion.jsp";
    
    }
    
    
    
    public String consolidadoLiquidacion(HttpServletRequest request,
            HttpServletResponse response) {

        String url = "/app/liquidacion/consolidadoLiquidacion.jsp";
        return url;
    }

    public void reporteConsolidadoLiquidacion(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        reporteLiquidacionExcel(request, response);
    }  
    
    public String reporteLiquidacionExcel(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        if (request.getParameterMap().containsKey("tipo_reporte")) {
            int tipoReporte = Integer.parseInt(request.getParameter("tipo_reporte"));
            String fechaInicial = request.getParameter("fecha_inicio") + " 00:00:00";
            String fechaFinal = request.getParameter("fecha_fin") + " 23:59:59";
            //String idEmpresa = request.getParameter("empresa_id");
            
             Usuario u = (Usuario) session.getAttribute("login");        
             
             Empresa e = EmpresaBD.getById(u.getIdempresa());

            String reportesPath = getServletContext().getRealPath("");
            if (reportesPath.endsWith("\\")) {
                reportesPath = reportesPath.substring(0, reportesPath.length() - 1);
            }

            Map parameters;
            parameters = new HashMap();
            parameters.put("tipoReporte", tipoReporte);
            parameters.put("fechaInicio", fechaInicial);
            parameters.put("fechaFinal", fechaFinal);
            parameters.put("idEmpresa", e.getId());
            parameters.put("path", reportesPath);
                        
            parameters.put("idUsuario", "" + u.getId());
            parameters.put("nombreUsuario", u.getNombre() + " " + u.getApellido());

            String nombreReporte = "";
            String tituloReporte = "";

            switch (tipoReporte) {
                case 19:
                    nombreReporte = "reporte_ConsolidadoLiquidacion";
                    tituloReporte = "REPORTE CONSOLIDADO DE LIQUIDACION";
                    break;
                case 20:
                    nombreReporte = "reporte_ConsolidadoVehiculosNoLiquidados";
                    tituloReporte = "RESUMEN DE VEHICULOS QUE NO LIQUIDARON";
                    break;
                case 21:
                    nombreReporte = "reporte_LiquidacionXLiquidador";
                    tituloReporte = "REPORTE LIQUIDACION DE VEHICULOS POR LIQUIDADOR";
                    break;
                default:
                    break;
            }

            parameters.put("nombreReporte", nombreReporte);
            parameters.put("tituloReporte", tituloReporte);

            

            Usuario usuarioLiquidador = null;
            if (request.getParameterMap().containsKey("usuario_liquidador_id")) {
                String idUsuarioLiquidador = request.getParameter("usuario_liquidador_id");
                parameters.put("idUsuarioLiquidador", idUsuarioLiquidador);
                usuarioLiquidador = UsuarioBD.getById(Integer.parseInt(idUsuarioLiquidador));
            }

            if (usuarioLiquidador != null) {
                parameters.put("nombresUsuarioLiquidador", usuarioLiquidador.getApellido() + " " + usuarioLiquidador.getNombre());
            }

            Empresa ep = UsuarioPermisoEmpresaBD.selectEmpresaPermitida(u.getId());            
            if (ep != null) {
                parameters.put("nombreEmpresa", ep.getNombre());
                parameters.put("nitEmpresa", ep.getNit());
                parameters.put("idEmpresa", "" + ep.getId());
            } else {
                request.setAttribute("msg", "* Usuario no tiene relaci&oacute;n y/o permisos adecuados con la empresa.");
                request.setAttribute("msgType", "alert alert-warning");
                return "/app/reportes/generaReporte.jsp";
            }
            
            ConfiguracionLiquidacion etiquetas = obtenerEtiquetasLiquidacionPerfil(request);

            // Reporte editable XLS            
            ReporteUtilExcel rue = new ReporteUtilExcel();
            MakeExcel rpte = rue.crearReporte(tipoReporte, false, parameters, null, etiquetas);
            String nombreArchivo = parameters.get("nombreReporte") + ".xls";

            response.setContentType("application/ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

            HSSFWorkbook file = rpte.getExcelFile();
            file.write(response.getOutputStream());
            response.flushBuffer();
            response.getOutputStream().close();
        }
        return "/app/liquidacion/reporteConsolidadoLiquidacion.jsp";
    }

    public String liquidacionTotalVehiculos(HttpServletRequest request,
            HttpServletResponse response) {

        String url = "/app/liquidacion/liquidacionTotalVehiculos.jsp";
        return url;
    }

    public void reporteLiquidacionTotalVehiculos(HttpServletRequest request,
            HttpServletResponse response) {
        Connection obtenerConexion = null;
        String fechaInicial = request.getParameter("fecha_inicio") + " 00:00:00";
        String fechaFinal = request.getParameter("fecha_inicio") + " 23:59:59";
        String pkEmpresa = request.getParameter("empresa_id");
        String nombreReporte = "Reporte_LiquidacionTotalVehiculos";

        Empresa emp = EmpresaBD.getById(Integer.parseInt(pkEmpresa));
        /*String io = System.getProperty("user.home").replace("\\", "/") + "/Documents/NetBeansProjects/regisdata_web/reportes/" + nombreReporte + ".jasper";
        final String pathLogo = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/logo.png";
        final String pathIcon = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/icon.png";
        final String pathIconTitulo = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/titulo.png";
        final String pathFooter = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/footer.png";*/
        String io = "";
        String pathLogo = "";
        String pathIcon = "";
        String pathIconTitulo = "";
        String pathFooter = "";
        String web_path = getServletContext().getRealPath("");
        System.out.println("---> "+web_path);


    String SO = System.getProperty("os.name");
        if (SO.contains("Windows")) {
            io = web_path.replace("\\", "/") + "resources/reportes/" + nombreReporte + ".jasper";
            pathLogo = web_path.replace("\\", "/") + "resources/reportes/plantilla/logo.png";
            pathIcon = web_path.replace("\\", "/")+ "resources/reportes/plantilla/icon.png";
            pathIconTitulo = web_path.replace("\\", "/")+ "resources/reportes/plantilla/titulo.png";
            pathFooter = web_path.replace("\\", "/")+ "resources/reportes/plantilla/footer.png";

        } else if (SO.contains("Linux")) {
            io = web_path+"resources/reportes/" + nombreReporte + ".jasper";
            pathLogo = web_path+"resources/reportes/plantilla/logo.png";
            pathIcon = web_path+"resources/reportes/plantilla/icon.png";
            pathIconTitulo = web_path+"resources/reportes/plantilla/titulo.png";
            pathFooter = web_path+"resources/reportes/plantilla/footer.png";
        }
        else if (SO.contains("Mac OS X")) {
            io = web_path+"/resources/reportes/" + nombreReporte + ".jasper";
            pathLogo = web_path+"/resources/reportes/plantilla/logo.png";
            pathIcon = web_path+"/resources/reportes/plantilla/icon.png";
            pathIconTitulo = web_path+"/resources/reportes/plantilla/titulo.png";
            pathFooter = web_path+"/resources/reportes/plantilla/footer.png";
        }






        java.util.Map parameters = new java.util.HashMap();
        parameters.put("REPORT_TITLE", "REPORTE DE LIQUIDACION TOTAL POR VEHICULO ");
        parameters.put("REPORT_LOGO", new File(pathLogo).getPath());
        parameters.put("REPORT_SUBTITLE", "");
        parameters.put("REPORT_ICON", new File(pathIcon).getPath());
        parameters.put("REPORT_ICON_TITLE", new File(pathIconTitulo).getPath());
        parameters.put("REPORT_FOOTER", new File(pathFooter).getPath());

        parameters.put("SIMBOLO_MONEDA", "$");
        if (SO.contains("Windows")) {
            parameters.put("SUBREPORT_DIR", web_path.replace("\\", "/") + "resources/reportes/subreportes/");
        }
        else if (SO.contains("Linux")) {
            parameters.put("SUBREPORT_DIR", web_path+ "resources/reportes/subreportes/");
        }
        else if (SO.contains("Mac")) {
            parameters.put("SUBREPORT_DIR", web_path+ "resources/reportes/subreportes/");
        }

        parameters.put("FECHA_SOLICITUD_INICIAL", fechaInicial);
        parameters.put("FECHA_SOLICITUD_FINAL", fechaFinal);
        parameters.put("EMPRESA_PK", emp.getId());
        parameters.put("REPORT_EMPRESA_NAME", emp.getNombre());
        parameters.put("REPORT_EMPRESA_NIT", emp.getNit());

        try {
                response.setContentType("application/pdf");
                obtenerConexion = PilaConexiones.obtenerInstancia().obtenerConexion();
                JasperPrint fillReport = net.sf.jasperreports.engine.JasperFillManager.fillReport(io, parameters, obtenerConexion);
                JasperExportManager.exportReportToPdfStream(fillReport, response.getOutputStream());
            } catch (JRException | IOException ex) {
                Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    obtenerConexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        /*try {
            response.setContentType("application/pdf");
            JasperExportManager.exportReportToPdfStream(LiquidacionBD.generarReporte1(io, nombreReporte, parameters), response.getOutputStream());
        } catch (JRException | IOException ex) {
            Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    
    public void reporteLiquidacionPorLiquidador(HttpServletRequest request,
            HttpServletResponse response) {
        Connection obtenerConexion = null;
        if (request.getParameterMap().containsKey("tipo_reporte")) {
            try {
                reporteLiquidacionExcel(request, response);
            } catch (IOException ex) {
                Logger.getLogger(ControladorReporteLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            String fechaInicial = request.getParameter("fecha_inicio") + " 00:00:00";
            String fechaFinal = request.getParameter("fecha_fin") + " 23:59:59";
            String pkUsuarioLiquidador = request.getParameter("usuario_liquidador_id");
            String nombreReporte="";
            HttpSession session = request.getSession();
            Usuario user = (Usuario) session.getAttribute("login");        
            
            if ((user.getIdempresa() == 9) || (user.getIdempresa() == 10)) {
                nombreReporte = "Reporte_LiquidacionXLiquidador_new_dcto";
            } else {
                nombreReporte = "Reporte_LiquidacionXLiquidador";
            }
            

            String io = "";
            String pathLogo = "";
            String pathIcon = "";
            String pathIconTitulo = "";
            String pathFooter = "";
            String web_path = getServletContext().getRealPath("");
            //System.out.println("---> "+web_path);

            String SO = System.getProperty("os.name");
            if (SO.contains("Windows")) {
                io = web_path.replace("\\", "/") + "resources/reportes/" + nombreReporte + ".jasper";
                pathLogo = web_path.replace("\\", "/") + "resources/reportes/plantilla/logo.png";
                pathIcon = web_path.replace("\\", "/") + "resources/reportes/plantilla/icon.png";
                pathIconTitulo = web_path.replace("\\", "/") + "resources/reportes/plantilla/titulo.png";
                pathFooter = web_path.replace("\\", "/") + "resources/reportes/plantilla/footer.png";

            } else if (SO.contains("Linux")) {
                io = web_path + "resources/reportes/" + nombreReporte + ".jasper";
                pathLogo = web_path + "resources/reportes/plantilla/logo.png";
                pathIcon = web_path + "resources/reportes/plantilla/icon.png";
                pathIconTitulo = web_path + "resources/reportes/plantilla/titulo.png";
                pathFooter = web_path + "resources/reportes/plantilla/footer.png";
            } else if (SO.contains("Mac")) {
                io = web_path + "/resources/reportes/" + nombreReporte + ".jasper";
                pathLogo = web_path + "/resources/reportes/plantilla/logo.png";
                pathIcon = web_path + "/resources/reportes/plantilla/icon.png";
                pathIconTitulo = web_path + "/resources/reportes/plantilla/titulo.png";
                pathFooter = web_path + "/resources/reportes/plantilla/footer.png";
            }

            /*String io = System.getProperty("user.home").replace("\\", "/") + "/Documents/NetBeansProjects/regisdata_web/reportes/" + nombreReporte + ".jasper";
            final String pathLogo = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/logo.png";
            final String pathIcon = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/icon.png";
            final String pathIconTitulo = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/titulo.png";
            final String pathFooter = System.getProperty("user.home") + "/Documents/NetBeansProjects/regisdata_web/reportes/plantilla/footer.png";*/
            java.util.Map parameters = new java.util.HashMap();
            parameters.put("REPORT_TITLE", "REPORTE DE LIQUIDACION POR LIQUIDADOR ");
            parameters.put("REPORT_LOGO", new File(pathLogo).getPath());
            parameters.put("REPORT_SUBTITLE", "Desde " + fechaInicial + " hasta " + fechaFinal);
            parameters.put("REPORT_ICON", new File(pathIcon).getPath());
            parameters.put("REPORT_ICON_TITLE", new File(pathIconTitulo).getPath());
            parameters.put("REPORT_FOOTER", new File(pathFooter).getPath());

            parameters.put("SIMBOLO_MONEDA", "$");
            if (SO.contains("Windows")) {
                parameters.put("SUBREPORT_DIR", web_path.replace("\\", "/") + "resources/reportes/subreportes/");
            } else if (SO.contains("Linux")) {
                parameters.put("SUBREPORT_DIR", web_path + "resources/reportes/subreportes/");
            } else if (SO.contains("Mac")) {
                parameters.put("SUBREPORT_DIR", web_path + "resources/reportes/subreportes/");
            }
            /*parameters.put("SUBREPORT_DIR", System.getProperty("user.home").replace("\\", "/") + "/Documents/NetBeansProjects/regisdata_web/reportes/subreports/");*/

            parameters.put("FECHA_INICIAL_SOLICITUD", fechaInicial);
            parameters.put("FECHA_FINAL_SOLICITUD", fechaFinal);
            parameters.put("USUARIO_PK", Integer.parseInt(pkUsuarioLiquidador));

            try {
                response.setContentType("application/pdf");
                obtenerConexion = PilaConexiones.obtenerInstancia().obtenerConexion();
                JasperPrint fillReport = net.sf.jasperreports.engine.JasperFillManager.fillReport(io, parameters, obtenerConexion);
                JasperExportManager.exportReportToPdfStream(fillReport, response.getOutputStream());
            } catch (JRException | IOException ex) {
                Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    obtenerConexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            /*try {
                response.setContentType("application/pdf");
                JasperExportManager.exportReportToPdfStream(LiquidacionBD.generarReporte1(io, nombreReporte, parameters), response.getOutputStream());
            } catch (JRException | IOException ex) {
                Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }

    public String liquidacionPorLiquidador(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        ArrayList<UsuarioPermisoEmpresa> upeList = UsuarioPermisoEmpresaBD.selectEmpresasPermitidas(((Usuario) session.getAttribute("login")).getId());

        UsuarioBD uBD = new UsuarioBD();

        ArrayList<Empresa> empresas = new ArrayList<>();
        Empresa empresa;
        for (UsuarioPermisoEmpresa upe : upeList) {
            empresa = new Empresa();
            empresa.setId(upe.getIdEmpresa());
            empresas.add(empresa);
        }

        ArrayList<Usuario> usuarios = uBD.getUsuariosByEmpresaPerfil(empresas, 5);

        session.setAttribute("usuariosLiquidadores", usuarios);

        String url = "/app/liquidacion/liquidacionPorLiquidador.jsp";

        return url;        
    }

    public boolean verificarDiaActual(String fechaInicial, String fechaFinal) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        String fa = f.format(new Date());
        if (fechaInicial.compareTo(fa) == 0
                || fechaFinal.compareTo(fa) == 0) {
            return true;
        }

        return false;
    }

    // Nombre de archivo y titulo del reporte
    // Nota: El orden establecido debe coincidir con el desplegado 
    // en el componente select de la pagina web.
    public String nombreReporte(String s, boolean dia_actual) {
        int idx = Integer.parseInt(s);
        switch (idx) {
            case 1:
                if (dia_actual) {
                    return "reporte_PuntosControl_GPS:REPORTE DE PUNTOS DE CONTROL";
                } else {
                    return "reporte_PuntosControl:REPORTE DE PUNTOS DE CONTROL";
                }
            case 2:
                return "reporte_ProduccionXVehiculo:REPORTE DE PRODUCCIÓN POR VEHÍCULO";
            case 3:
                return "reporte_RutaXVehiculo2:REPORTE DE RUTA POR VEHÍCULO";
            case 4:
                return "reporte_AlarmasXVehiculo:REPORTE DE ALARMAS POR VEHÍCULO";
            case 5:
                return "reporte_NivelOcupacion:REPORTE NIVEL OCUPACIÓN";
            case 6:
                return "reporte_Perimetro:REPORTE CONTEOS EN PERÍMETRO POR VEHÍCULO";
            case 7:
                return "reporte_ConsolidadoEmpresa:REPORTE CONSOLIDADO POR EMPRESA";
            case 8:
                return "reporte_ComparacionProduccionRuta: REPORTE COMPARATIVO DE PRODUCCIÓN POR RUTA";
            case 9:
                return "reporte_ProduccionXRuta:REPORTE DE PRODUCCIÓN POR RUTA";
            case 11:
                return "reporte_VehiculosXRuta:REPORTE DE VEHÍCULOS POR RUTA";
            case 12:
                return "reporte_VehiculosXAlarma:REPORTE DE VEHÍCULOS POR ALARMA";
            case 13:
                return "reporte_Estadisticas:REPORTE ESTADÍSTICO";
            case 14:
                return "reporte_DescripcionRuta:REPORTE DESCRIPCIÓN RUTA";
            case 15:
                return "reporte_Gerencia2:REPORTE GERENCIA";
            case 16:
                return "reporte_Despachador:REPORTE DESPACHADOR";
            case 17:
                return "reporte_Propietario:REPORTE PROPIETARIO";
            case 18:
                return "reporte_GerenciaXVehiculo:REPORTE GERENCIA POR VEHICULO";
            default:
                return null;
        }
    }
}
