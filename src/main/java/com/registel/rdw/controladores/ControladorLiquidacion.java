/**
 * Clase controlador Liquidacion
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.LiquidacionBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.CategoriasDeDescuentoBD;
import com.registel.rdw.datos.RelacionVehiculoConductorBD;
import com.registel.rdw.datos.ConductorBD;
import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.datos.LiquidacionAdicionalBD;
import com.registel.rdw.datos.LiquidacionVueltasBD;
import com.registel.rdw.datos.MotivoBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.AuditoriaBD;
import com.registel.rdw.datos.ForwardingBD;
import com.registel.rdw.datos.PilaConexiones;
import com.registel.rdw.logica.AuditoriaLiquidacionGeneral;

import com.registel.rdw.logica.Liquidacion;
import com.registel.rdw.logica.LiquidacionGeneral;
import com.registel.rdw.logica.CategoriasDeDescuento;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Vuelta;
import com.registel.rdw.logica.RelacionVehiculoConductor;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.Forwarding;
import com.registel.rdw.logica.LiquidacionAdicional;
import com.registel.rdw.logica.LiquidacionVueltas;
import com.registel.rdw.logica.Motivo;
import com.registel.rdw.logica.Tarifa;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.MakeExcel;
import com.registel.rdw.utils.ReporteUtilExcel;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.registel.rdw.utils.ConstantesSesion.*;

public class ControladorLiquidacion extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String url = "/";

        if (session == null
                || session.getAttribute("login") == null
                || session.getAttribute("select") == null
                || Expire.check(session.getCreationTime())) {
            url = "/index.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean dispatcherResponse = true;
        String requestURI = request.getRequestURI();
        String url = "/";
        
        if (requestURI.endsWith("/consultarLiquidacion")) {
            url = consultarLiquidacion(request, response);
        }
        
        if (requestURI.endsWith("/verReciboLiquidacion")) {
            dispatcherResponse = false;
            verReciboLiquidacion(request, response);
        } else if (requestURI.endsWith("/eliminarConductor")) {
            url = eliminarLiquidacion(request, response);
        } else if (requestURI.endsWith("/vueltasALiquidar")) {
            try {
                url = encontrarVueltas(request, response);
            } catch (SQLException ex) {
                //Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.getMessage());
            }
        } else if (requestURI.endsWith("/vueltasALiquidarFusa")) {
            try {
                url = encontrarVueltasFusa(request, response);
            } catch (SQLException ex) {
                //Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.getMessage());
            }
        } else if (requestURI.endsWith("/matriculaVehiculoALiquidar")) {
            url = placa(request, response);
        } else if (requestURI.endsWith("/numeroInternoVehiculoALiquidar")) {
            url = numeroInterno(request, response);
        } else if (requestURI.endsWith("/categoriasDeDescuentoFijas")) {
            url = Descuentos(request, response);
        } else if (requestURI.endsWith("/categoriasDeDescuentoFijasFusa")) {
            url = DescuentosFusa(request, response);
        } else if (requestURI.endsWith("/encontrarCategoriasDeDescuento")) {
            url = encontrarCategoriaDeDescuento(request, response);
        } else if (requestURI.endsWith("/encontrarCategoriasDeDescuentoFusa")) {
            url = encontrarCategoriaDeDescuentoFusa(request, response);
        } else if (requestURI.endsWith("/buscarTarifas")) {
            url = buscarTarifas(request, response);
        } else if (requestURI.endsWith("/buscarEtiquetas")) {
            url = buscarEtq(request, response);
        }

        if (requestURI.endsWith("/guardarLiquidacion")) {
            //else if (requestURI.contains("/guardarLiquidacion")) {
            try {
                dispatcherResponse = false;
                LiquidacionGeneral liquidacionGeneral = guardarLiquidacion(request, response);
                response.setContentType("application/json");
                response.setHeader("cache-control", "no-cache");
                PrintWriter out = response.getWriter();
                JSONObject jsonResponse = new JSONObject();

                // Preparing object to send response. A list is needed
                List<LiquidacionGeneral> listLiquidacionGeneral = new ArrayList<>();

                try {
                    if (liquidacionGeneral != null) {
                        listLiquidacionGeneral.add(liquidacionGeneral);
                        jsonResponse.put("response", listLiquidacionGeneral);
                        jsonResponse.put("saved", true);
                    } else {
                        jsonResponse.put("saved", false);
                    }
                } catch (JSONException ex) {
                    //Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.getMessage());
                }
                out.println(jsonResponse.toString());
                out.flush();

            } catch (JSONException ex) {
                //Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.getMessage());
            }
        } else if (requestURI.endsWith("/anularLiquidacion")) {
            dispatcherResponse = false;
            boolean result = anularLiquidacion(request, response);
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            try {
                jsonResponse.put("id", request.getParameter("id"));
                jsonResponse.put("anulada", result);
            } catch (JSONException ex) {
                //Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.getMessage());
            }
            out.println(jsonResponse.toString());
            out.flush();
        }
        if (dispatcherResponse) {
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }

    public String consultarLiquidacion(HttpServletRequest request, HttpServletResponse response) {
        String fk_vehiculo = request.getParameter("vehiculo");
        //String num_interno_vehiculo = request.getParameter("numero_interno_vehiculo");            
        String fecha_inicio = request.getParameter("fecha_inicio") + " 00:00:00";
        String fecha_fin = request.getParameter("fecha_fin") + " 23:59:59";
        String url = "";
        HttpSession session = request.getSession();

        if (fk_vehiculo.equals("")) {
            fk_vehiculo = "0";
        }

        Usuario u = (Usuario) session.getAttribute("login");
        Empresa e = EmpresaBD.getById(u.getIdempresa());
        if (e != null) {
            if (e.getId() == 9) {
                url = "/app/liquidacion/listadoLiquidacionFusa.jsp";
            } else {
                url = "/app/liquidacion/listadoLiquidacion.jsp";
            }
        }

        if (request.getParameterMap().containsKey("tipo_reporte")) {/*PERMITE EXPORTAR A EXCEL LOS DATOS*/
            try {
                reporteLiquidacionExcel(request, response);
            } catch (IOException ex) {
                //Logger.getLogger(ControladorReporteLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.getMessage());
            }
        } else {
            try {/*BTIENE LAS LIQUIDACIONES */
                obtenerEtiquetasLiquidacionPerfil(request);
                ArrayList todasLasLiquidaciones = LiquidacionBD.consultaDeLiquidacion(fecha_inicio, fecha_fin, Integer.parseInt(fk_vehiculo), e.getId());
                session.setAttribute("datos", todasLasLiquidaciones);
                session.setAttribute("tablaAImprimir", 1);
            } catch (ExisteRegistroBD ex) {
                System.err.println(ex.getMessage());
                //request.setAttribute("msg", ex.getMessage());
            }
        }

        return url;
    }

    public String reporteLiquidacionExcel(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession();
        
        if (request.getParameterMap().containsKey("tipo_reporte")) {
            ConfiguracionLiquidacion etiquetas = obtenerEtiquetasLiquidacionPerfil(request);
            String fk_vehiculo = request.getParameter("vehiculo");
            
            if (fk_vehiculo.equals("")) {
                fk_vehiculo = "0";
            }
            
            int tipoReporte = Integer.parseInt(request.getParameter("tipo_reporte"));
            String fechaInicial = request.getParameter("fecha_inicio") + " 00:00:00";
            String fechaFinal = request.getParameter("fecha_fin") + " 23:59:59";
            Usuario u = (Usuario) session.getAttribute("login");
            Empresa e = EmpresaBD.getById(u.getIdempresa());
            String reportesPath = getServletContext().getRealPath("");
            
            if (reportesPath.endsWith("\\")) {
                reportesPath = reportesPath.substring(0, reportesPath.length() - 1);
            }

            Map parameters;
            parameters = new HashMap();
            parameters.put("tipoReporte", tipoReporte + "");
            parameters.put("fechaInicio", fechaInicial);
            parameters.put("fechaFinal", fechaFinal);
            parameters.put("idEmpresa", e.getId() + "");
            parameters.put("idVehiculo", fk_vehiculo);
            parameters.put("path", reportesPath);
            parameters.put("idUsuario", "" + u.getId());
            parameters.put("nombreUsuario", u.getNombre() + " " + u.getApellido());
            parameters.put("nombreReporte", "Consulta_Liquidacion");
            parameters.put("tituloReporte", "Consulta_Liquidacion");
            parameters.put("nombreEmpresa", e.getNombre());

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

    public String generarReciboLiquidacion(HttpServletRequest request, HttpServletResponse response,
            String vehiculoID, String liquidacionID) {
        HttpSession session = request.getSession();
        String nombreRecibo;
        Movil m = new Movil();
        ConfiguracionLiquidacion etiquetas = obtenerEtiquetasLiquidacionPerfil(request);
        Connection obtenerConexion = null;
        Usuario user = (Usuario) session.getAttribute("login");
        Empresa emp = EmpresaBD.getById(user.getIdempresa());

        if ((user.getIdempresa() == 9) || (user.getIdempresa() == 10)) {
            nombreRecibo = "Recibo_LiquidacionXVehiculo_new_dcto";
        } else {
            nombreRecibo = "Recibo_LiquidacionXVehiculo";
        }
        
        String io = "";
        String pathLogo = "";
        String pathIcon = "";
        String pathIconTitulo = "";
        String pathFooter = "";
        String simbolo_moneda = "";
        Empresa empresa = (Empresa)request.getSession().getAttribute("emp");
        
        if (empresa != null && empresa.getSimboloMoneda() != null && !empresa.getSimboloMoneda().isEmpty()) {
            simbolo_moneda = empresa.getSimboloMoneda();
        } else {
            simbolo_moneda = "$";
        }
        
        java.util.Map<String, String> parameters = new java.util.HashMap();
        m.setId(Integer.parseInt(vehiculoID));
        m.setEstado(new Short("1"));
        String SO = System.getProperty("os.name");
        String web_path = getServletContext().getRealPath("");

        if (SO.contains("Windows")) {
            io = web_path.replace("\\", "/") + "resources/reportes/" + nombreRecibo + ".jasper";
            pathLogo = web_path.replace("\\", "/") + "resources/reportes/plantilla/logo.png";
            pathIcon = web_path.replace("\\", "/") + "resources/reportes/plantilla/icon.png";
            pathIconTitulo = web_path.replace("\\", "/") + "resources/reportes/plantilla/titulo.png";
            pathFooter = web_path.replace("\\", "/") + "resources/reportes/plantilla/footer.png";
        } else if (SO.contains("Linux")) {
            io = web_path + "resources/reportes/" + nombreRecibo + ".jasper";
            pathLogo = web_path + "resources/reportes/reportes/plantilla/logo.png";
            pathIcon = web_path + "resources/reportes/reportes/plantilla/icon.png";
            pathIconTitulo = web_path + "resources/reportes/reportes/plantilla/titulo.png";
            pathFooter = web_path + "resources/reportes/reportes/plantilla/footer.png";
        }

        parameters.put("REPORT_TITLE", "RECIBO DE LIQUIDACION ");
        parameters.put("REPORT_LOGO", new File(pathLogo).getPath());
        parameters.put("REPORT_SUBTITLE", "");
        parameters.put("REPORT_ICON", new File(pathIcon).getPath());
        parameters.put("REPORT_ICON_TITLE", new File(pathIconTitulo).getPath());
        parameters.put("REPORT_FOOTER", new File(pathFooter).getPath());
        parameters.put("ETIQUETA1", "");
        parameters.put("ETIQUETA2", "");
        parameters.put("ETIQUETA3", "");
        parameters.put("ETIQUETA4", "");
        parameters.put("ETIQUETA5", "");
        parameters.put("ETIQUETA6", "");
        parameters.put("ETIQUETA7", "");
        parameters.put("ETIQUETA8", "");
        
        if (emp != null) {
            parameters.put("REPORT_EMPRESA_NAME", emp.getNombre());
            parameters.put("REPORT_EMPRESA_NIT", emp.getNit());
        }
        
        parameters.put("SIMBOLO_MONEDA", simbolo_moneda);
        
        if ((user.getIdempresa() == 9) || (user.getIdempresa() == 10)) {
            parameters.replace("ETIQUETA1", etiquetas.getEtqTotal1());
            parameters.replace("ETIQUETA2", etiquetas.getEtqTotal2());
            parameters.replace("ETIQUETA3", etiquetas.getEtqTotal3());
            parameters.replace("ETIQUETA4", etiquetas.getEtqTotal4());
            parameters.replace("ETIQUETA5", etiquetas.getEtqTotal5());
            parameters.replace("ETIQUETA6", etiquetas.getEtqTotal6());
            parameters.replace("ETIQUETA7", etiquetas.getEtqTotal7());
            parameters.replace("ETIQUETA8", etiquetas.getEtqTotal8());
        } else {
            parameters.replace("ETIQUETA1", etiquetas.getEtqTotal2());
            parameters.replace("ETIQUETA2", etiquetas.getEtqTotal3());
            parameters.replace("ETIQUETA3", etiquetas.getEtqTotal1());
            parameters.replace("ETIQUETA4", etiquetas.getEtqTotal6());
            parameters.replace("ETIQUETA5", etiquetas.getEtqTotal7());
        }

        if (SO.contains("Windows")) {
            parameters.put("SUBREPORT_DIR", web_path.replace("\\", "/") + "resources/reportes/subreportes/");
        } else if (SO.contains("Linux")) {
            parameters.put("SUBREPORT_DIR", web_path + "resources/reportes/subreportes/");
        } else if (SO.contains("Mac")) {
            parameters.put("SUBREPORT_DIR", web_path + "resources/reportes/subreportes/");
        }
        
        parameters.put("PK_LIQUIDACION", "" + liquidacionID);
        String url = "";
        
        try {
            response.setContentType("application/pdf");
            obtenerConexion = PilaConexiones.obtenerInstancia().obtenerConexion();
            JasperPrint fillReport = net.sf.jasperreports.engine.JasperFillManager.fillReport(io, parameters, obtenerConexion);
            JasperExportManager.exportReportToPdfStream(fillReport, response.getOutputStream());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, fillReport);

            if (SO.contains("Windows")) {
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, web_path + "resources\\reportes\\mi.pdf");
                url = web_path + "resources\\reportes\\mi.pdf";
            } else if (SO.contains("Linux")) {
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, web_path + "resources/reportes/reportes/mi.pdf");
                url = web_path + "resources/reportes/mi.pdf";
            } else if (SO.contains("Mac")) {
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, web_path + "resources/reportes/reportes/mi.pdf");
                url = web_path + "resources/reportes/mi.pdf";
            }
            
            exporter.exportReport();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                obtenerConexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return url;
    }

    
    public void verReciboLiquidacion(HttpServletRequest request, HttpServletResponse response) {

        String fechaInicial = request.getParameter("fecha_inicio");
        String fechaFinal = request.getParameter("fecha_fin");
        String vehiculoID = request.getParameter("id");
        String liquidacionID = request.getParameter("pk_liquidacion");
        String path = generarReciboLiquidacion(request, response, vehiculoID, liquidacionID);

    }

    public LiquidacionGeneral guardarLiquidacion(HttpServletRequest request,
            HttpServletResponse response) throws JSONException {
        String[] myJsonData = request.getParameterValues("json");
        JSONObject data = new JSONObject(myJsonData[0]);
        HttpSession session = request.getSession(false);
        LiquidacionGeneral liquidacionGeneralResponse = null;
        ArrayList<Vuelta> vueltas = (ArrayList<Vuelta>) session.getAttribute("vueltas");
        
        if (vueltas != null) {            
            String fechaInicio = "";
            String fechaFin = "";

            ConfiguracionLiquidacion configuracionLiquidacion
                    = (ConfiguracionLiquidacion) request.getSession().getAttribute(CONFIGURACION_ETIQUETAS_SESION);

            if (configuracionLiquidacion != null) {

                if (configuracionLiquidacion.isLiquidacionPorTiempo()) {
                    fechaInicio = request.getParameter("fecha_hora_inicio");
                    fechaFin = request.getParameter("fecha_hora_fin");
                } else {
                    fechaInicio = request.getParameter("fecha_inicio");
                    fechaFin = request.getParameter("fecha_fin");
                }
            }

            LiquidacionBD lgBD = new LiquidacionBD();
            LiquidacionGeneral liquidacionGeneral = new LiquidacionGeneral();
            liquidacionGeneral.setFkTarifaFija(data.getInt("fkTarifaFija"));
            liquidacionGeneral.setFkVehiculo(data.getInt("fkVehiculo"));
            liquidacionGeneral.setFkConductor(data.getInt("fkConductor"));
            liquidacionGeneral.setTotalPasajeros(data.getInt("totalPasajeros"));
            liquidacionGeneral.setTotalPasajerosLiquidados(data.getInt("totalPasajerosLiquidados"));
            liquidacionGeneral.setTotalValorVueltas(data.getInt("totalValorVueltas"));
            liquidacionGeneral.setTotalValorDescuentosPasajeros(data.getInt("totalValorDescuentosPasajeros"));
            liquidacionGeneral.setTotalValorDescuentosAdicional(data.getInt("totalValorDescuentosAdicional"));
            liquidacionGeneral.setTotalValorOtrosDescuentos(data.getInt("totalValorOtrosDescuentos"));
            liquidacionGeneral.setDistancia(data.getDouble("distancia"));
            liquidacionGeneral.setFechaInicioLiquidacion(fechaInicio);
            liquidacionGeneral.setFechaFinLiquidacion(fechaFin);
            
            if (data.getDouble("ipk") > 0) {
                liquidacionGeneral.setIpk(data.getDouble("ipk"));
            }
            
            liquidacionGeneral.setEstado(1);
            liquidacionGeneral.setModificacionLocal(0);
            liquidacionGeneral.setPkUnica("No aplica");
            liquidacionGeneral.setFkUsuario(((Usuario) session.getAttribute("login")).getId());
            
            try {
                if (lgBD.insertLq(liquidacionGeneral) == 1) {
                    ArrayList<LiquidacionVueltas> liquidacionVueltas = new ArrayList<>();
                    LiquidacionVueltas liquidacionVuelta;
                    
                    for (Vuelta vuelta : vueltas) {
                        liquidacionVuelta = new LiquidacionVueltas();
                        liquidacionVuelta.setEstado(1);
                        liquidacionVuelta.setFechaDescuento(new Date());
                        liquidacionVuelta.setFechaModificacion(new Date());
                        liquidacionVuelta.setFkInformacionRegistradora(vuelta.getId());
                        liquidacionVuelta.setFkLiquidacionGeneral(liquidacionGeneral.getId());
                        liquidacionVuelta.setMotivoDescuento("");
                        liquidacionVuelta.setPasajerosDescuento(0);
                        liquidacionVuelta.setPkUnica("");
                        liquidacionVuelta.setValorDescuento(0);
                        liquidacionVueltas.add(liquidacionVuelta);
                    }

                    LiquidacionVueltasBD lvBD = new LiquidacionVueltasBD();
                    
                    if (lvBD.insert(liquidacionVueltas, data.getInt("fkConductor")) == 1) {
                        JSONArray descuentosInfo = data.getJSONArray("descuentosInfo");
                        
                        if (descuentosInfo.length() > 0) {
                            ArrayList<LiquidacionAdicional> liquidacionesAdicionales = new ArrayList<>();
                            LiquidacionAdicional liquidacionAdicional;
                            JSONObject obj;
                            
                            for (int i = 0; i < descuentosInfo.length(); i++) {
                                obj = ((JSONObject) descuentosInfo.get(i));
                                liquidacionAdicional = new LiquidacionAdicional();
                                liquidacionAdicional.setCantidadPasajerosDescontados(obj.getInt("pasajeros"));
                                liquidacionAdicional.setEstado(1);
                                liquidacionAdicional.setFechaDescuento(new Date());
                                liquidacionAdicional.setFechaModificacion(new Date());
                                liquidacionAdicional.setFkCategorias(obj.getInt("fkCategoria"));
                                liquidacionAdicional.setFkLiquidacionGeneral(liquidacionGeneral.getId());
                                liquidacionAdicional.setMotivoDescuento(obj.getString("observaciones"));
                                liquidacionAdicional.setPkUnica("");
                                liquidacionAdicional.setValorDescuento(obj.getDouble("valor"));
                                liquidacionesAdicionales.add(liquidacionAdicional);
                            }

                            LiquidacionAdicionalBD laBD = new LiquidacionAdicionalBD();
                            if (laBD.insert(liquidacionesAdicionales) > 0) {
                                liquidacionGeneralResponse = liquidacionGeneral;
                            }
                        }
                    }
                    
                    if (configuracionLiquidacion != null && configuracionLiquidacion.isLiquidacionPorTiempo()) {
                        Movil vehiculo = MovilBD.selectOne(data.getInt("fkVehiculo"));
                        Entorno entorno = new Entorno(getServletContext().getRealPath(""));
                        Forwarding forwarding = ForwardingBD.obtenerNumeracionLiq(fechaInicio, fechaFin,
                        vehiculo.getPlaca(), entorno);

                        if (forwarding != null) {
                            LiquidacionBD.updateLiquidacionPorTiempos(liquidacionGeneral, forwarding.getIdInicial(), 
                                    forwarding.getIdFinal(), forwarding.getNumeracionInicial(), 
                                    forwarding.getNumeracionFinal());
                        }
                        
                        ForwardingBD.updateRegistrosLiquidadosGps(fechaInicio, fechaFin, vehiculo.getPlaca(), entorno);
                    } 
                }
            } catch (ExisteRegistroBD ex) {
                request.setAttribute("msg", ex.getMessage());
                System.err.println(ex.getMessage());
            }
        }
        return liquidacionGeneralResponse;
    }

    /**
     * FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR*
     * @param request
     * @param response
     * @return 
     */
    public String eliminarLiquidacion(HttpServletRequest request, HttpServletResponse response) {
        String estado = request.getParameter("estado");
        String id = request.getParameter("cedula");
        String url = "";
        Liquidacion c = new Liquidacion();
        c.setEstado(Integer.parseInt(estado));
        c.setId(Integer.parseInt(id));
        HttpSession session = request.getSession();

        try {
            LiquidacionBD.updateEstado(c);
            SelectBD select = new SelectBD(request);
            session.setAttribute("select", select);
            url = "/app/conductor/listadoConductor.jsp";

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public boolean anularLiquidacion(HttpServletRequest request,
            HttpServletResponse response) {
        boolean anull = false;
        String id = request.getParameter("id");
        String reasonDescription = request.getParameter("reasonDescription");

        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("login");

        if (reasonDescription.length() > 0) {
            LiquidacionGeneral lgTemp = new LiquidacionGeneral();
            LiquidacionGeneral lg;
            lgTemp.setId(Integer.parseInt(id));

            lg = LiquidacionBD.getById(lgTemp);

            if (lg != null) {
                lg.setEstado(0);
                lg.setFechaModificacion(new Date());

                String info = "La Liquidacion #: " + lg.getId()
                        + " fue ANULADA por el usuario ID = " + user.getId()
                        + " que pertenece al usuario = " + user.getNombre() + " " + user.getApellido()
                        + " conectado a la BD con el usuario de regisdata = " + user.getUsuariobd()
                        + " el procedimiento realizado fue: " + reasonDescription;

                if (LiquidacionBD.updateEstateLq(lg)) {
                    if (LiquidacionAdicionalBD.updateEstateLq(lg)) {
                        if (LiquidacionVueltasBD.updateEstateLq(lg)) {
                            try {
                                AuditoriaLiquidacionGeneral auditoriaLiquidacion = LiquidacionBD.getAuditoriaLiquidacion(lg);
                                Motivo oneReason = new Motivo();
                                oneReason.setFkAuditoria(auditoriaLiquidacion.getPkAuditoriaLiquidacionGeneral());
                                oneReason.setTablaAuditoria("tbl_auditoria_liquidacion_general");
                                oneReason.setDescripcionMotivo(reasonDescription);
                                oneReason.setInformacionAdicional(info);
                                oneReason.setModificacionLocal(1);
                                oneReason.setUsuario(((Usuario) session.getAttribute("login")).getId());
                                oneReason.setUsuarioBD(user.getUsuariobd());
                                auditoriaLiquidacion.setFkUsuario(((Usuario) session.getAttribute("login")).getId());
                                if (MotivoBD.insert(oneReason) > 0) {
                                    if (AuditoriaBD.updateUserLiquidacion(auditoriaLiquidacion)) {
                                        anull = true;
                                    }
                                }
                            } catch (ExisteRegistroBD ex) {
                                System.err.println(ex.getMessage());
                            }
                        }/*FIN SI LIQUIDACION VUELTAS*/
                    }/*FIN SI LIQUIDACION ADICIONAL*/
                    
                    /*
                        Si entra en este condicional, es porque la liquidación fue hecha por tiempos y se deben actualizar 
                        los registros en la base de datos del gps.
                    */
                    if (lg.getIdFinalGps() > 0 && lg.getIdInicialGps() > 0 && 
                            lg.getNumeracionFinalGps() > 0 && lg.getNumeracionInicialGps() > 0) {
                        Movil vehiculo = MovilBD.selectOne(lg.getFkVehiculo());
                        
                        if (vehiculo != null && vehiculo.getId() > 0) {
                            Entorno entorno = new Entorno(getServletContext().getRealPath(""));
                            ForwardingBD.updateRegistrosAnuladosGps(lg.getIdInicialGps(), lg.getIdFinalGps(), 
                                    vehiculo.getPlaca(), entorno);
                        }
                    }
                }/*FIN SI LIQUIDACION GENERAL*/
            }/*FIN SI LIQUIDACION NULA*/
        }/*FIN SI LARGO DE LA RAZON*/

        request.setAttribute("anulada", anull);
        return anull;
    }

    public String encontrarVueltas(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        String placaVehiculoId = request.getParameter("placa_vehiculo");
        String id_numero_interno_vehiculo = request.getParameter("numero_interno_vehiculo");
        int id_empresa = Integer.parseInt(request.getParameter("id_empresa"));
        String fechaInicio = "";
        String fechaFin = "";
        boolean esLiquidacionPorTiempo = false;
        
        ConfiguracionLiquidacion configuracionLiquidacion = 
                (ConfiguracionLiquidacion) request.getSession().getAttribute(CONFIGURACION_ETIQUETAS_SESION);
        
        if (configuracionLiquidacion != null) {
            
            if (configuracionLiquidacion.isLiquidacionPorTiempo()) {
                fechaInicio = request.getParameter("fecha_hora_inicio");
                fechaFin = request.getParameter("fecha_hora_fin");
                esLiquidacionPorTiempo = true;
            } else {
                fechaInicio = request.getParameter("fecha_inicio") + " 00:00:00";
                fechaFin = request.getParameter("fecha_fin") + " 23:59:59";
            }            
        }

        String url = "/app/liquidacion/vueltas_a_liquidar.jsp";
        HttpSession session = request.getSession();

        try {
            session.setAttribute("numeracion_inicial", 0);
            session.setAttribute("numeracion_final", 0);
            session.setAttribute("total_pasajeros_gps", 0);
            session.setAttribute("liquidacion_relativa", null);
            
            Movil movil = new Movil();
            movil.setId((placaVehiculoId.equalsIgnoreCase("")) ? 0 : Integer.parseInt(placaVehiculoId));
            movil.setFkEmpresa(id_empresa);
            movil.setNumeroInterno(id_numero_interno_vehiculo);
            movil.setFecha_inicio(fechaInicio);
            movil.setFecha_fin(fechaFin);
            movil.setEstado(1);
            Movil selectByOne = MovilBD.selectByOneView(movil);
            ArrayList<Vuelta> searchAround = LiquidacionBD.searchAround(movil, esLiquidacionPorTiempo);

            /*Se envia el vehiculoID del vehiculo*/
            RelacionVehiculoConductor vh = new RelacionVehiculoConductor();
            vh.setIdVehiculo(movil.getId());
            RelacionVehiculoConductor relacionVehiculoConductor = RelacionVehiculoConductorBD.selectByOneOne(vh);

            if (esLiquidacionPorTiempo) {
                Entorno entorno = new Entorno(getServletContext().getRealPath(""));
                Forwarding forwarding = ForwardingBD.obtenerNumeracionLiq(fechaInicio, fechaFin,
                        selectByOne.getPlaca(), entorno);

                if (forwarding != null) {
                    session.setAttribute("numeracion_inicial", forwarding.getNumeracionInicial());
                    session.setAttribute("numeracion_final", forwarding.getNumeracionFinal());
                    session.setAttribute("total_pasajeros_gps", forwarding.getTotalPasajeros());
                    
                    if (forwarding.isLiquidacionRelativa()) {
                        session.setAttribute("liquidacion_relativa", "<i class=\\\"glyphicon glyphicon-alert\\\" "
                                + "style=\\\"color: red; font-size: 18px;\\\">&nbsp;Alerta!!! Ya se realizaron "
                                + "liquidaciones en el horario seleccionado.</i>");
                    } 
                }
            }
            
            /*se envia el vehiculoID del conductor*/
            Conductor c = new Conductor();
            c.setEstado(1);
            c.setId(relacionVehiculoConductor.getIdConductor());

            Conductor conductorEncontradoDeVehiculo = ConductorBD.selectByOne(c);

            SelectBD select = new SelectBD(request);
            session.setAttribute("select", select);
            session.setAttribute("vueltas", searchAround);
            session.setAttribute("conductorVehiculo", conductorEncontradoDeVehiculo);
            session.setAttribute("relacionVehiculoConductor", relacionVehiculoConductor);
            session.setAttribute("placaVehiculoId", placaVehiculoId);
            session.setAttribute("placaVehiculo", selectByOne.getPlaca());
            session.setAttribute("nInterno", selectByOne.getNumeroInterno());
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return url;
    }

    public String encontrarVueltasFusa(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        String vehiculoId = request.getParameter("id_vehiculo");
        String placaVehiculo = request.getParameter("placa_vehiculo");
        int id_empresa = Integer.parseInt(request.getParameter("id_empresa"));
        String fechaInicio = "";
        String fechaFin = "";
        boolean esLiquidacionPorTiempo = false;
        
        ConfiguracionLiquidacion configuracionLiquidacion = 
                (ConfiguracionLiquidacion) request.getSession().getAttribute(CONFIGURACION_ETIQUETAS_SESION);
        
        if (configuracionLiquidacion != null) {
            
            if (configuracionLiquidacion.isLiquidacionPorTiempo()) {
                fechaInicio = request.getParameter("fecha_hora_inicio");
                fechaFin = request.getParameter("fecha_hora_fin");
                esLiquidacionPorTiempo = true;
            } else {
                fechaInicio = request.getParameter("fecha_inicio") + " 00:00:00";
                fechaFin = request.getParameter("fecha_fin") + " 23:59:59";
            }            
        }
        
        String url = "/app/liquidacion/vueltas_a_liquidarFusa.jsp";
        HttpSession session = request.getSession();

        try {
            session.setAttribute("numeracion_inicial", 0);
            session.setAttribute("numeracion_final", 0);
            session.setAttribute("total_pasajeros_gps", 0);
            session.setAttribute("liquidacion_relativa", null);
            
            Movil m = new Movil();
            m.setId((vehiculoId.equalsIgnoreCase("")) ? 0 : Integer.parseInt(vehiculoId));
            m.setFkEmpresa(id_empresa);
            m.setPlaca(placaVehiculo);
            m.setFecha_inicio(fechaInicio);
            m.setFecha_fin(fechaFin);
            m.setEstado(1);
            Movil selectByOne = MovilBD.selectByOneView(m);
            ArrayList<Vuelta> searchAround = LiquidacionBD.searchAround(m, esLiquidacionPorTiempo);

            /*Se envia el vehiculoID del vehiculo*/
            RelacionVehiculoConductor vh = new RelacionVehiculoConductor();
            vh.setIdVehiculo(m.getId());
            RelacionVehiculoConductor relacionVehiculoConductor = RelacionVehiculoConductorBD.selectByOneOne(vh);

            if (esLiquidacionPorTiempo) {
                Entorno entorno = new Entorno(getServletContext().getRealPath(""));
                Forwarding forwarding = ForwardingBD.obtenerNumeracionLiq(fechaInicio, fechaFin,
                        selectByOne.getPlaca(), entorno);

                if (forwarding != null) {
                    session.setAttribute("numeracion_inicial", forwarding.getNumeracionInicial());
                    session.setAttribute("numeracion_final", forwarding.getNumeracionFinal());
                    session.setAttribute("total_pasajeros_gps", forwarding.getTotalPasajeros());
                    
                    if (forwarding.isLiquidacionRelativa()) {
                        session.setAttribute("liquidacion_relativa", "<i class=\\\"glyphicon glyphicon-alert\\\" "
                                + "style=\\\"color: red; font-size: 18px;\\\">&nbsp;Alerta!!! Ya se realizaron "
                                + "liquidaciones en el horario seleccionado.</i>");
                    }
                }
            }
            
            /*se envia el vehiculoID del conductor*/
            Conductor c = new Conductor();
            c.setId(relacionVehiculoConductor.getIdConductor());
            c.setEstado(1);
            Conductor conductorEncontradoDeVehiculo = ConductorBD.selectByOne(c);
            
            SelectBD select = new SelectBD(request);
            session.setAttribute("select", select);
            session.setAttribute("vueltas", searchAround);
            session.setAttribute("conductorVehiculoF", conductorEncontradoDeVehiculo);
            session.setAttribute("relacionVehiculoConductorF", relacionVehiculoConductor);
            session.setAttribute("placaVehiculoIdF", vehiculoId);
            session.setAttribute("placaVehiculoF", selectByOne.getPlaca());
            session.setAttribute("nInternoF", selectByOne.getNumeroInterno());
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return url;
    }

    public String placa(HttpServletRequest request,
            HttpServletResponse response) {

        String id = request.getParameter("id");
        String url = "/app/liquidacion/placa.jsp";

        Movil c = new Movil();
        c.setId(Integer.parseInt(id));
        c.setEstado(1);

        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);

        try {
            ArrayList<Movil> SubModulos = MovilBD.movilesPlaca(c);
            session.setAttribute("select", select);
            session.setAttribute("placa", SubModulos);

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public String numeroInterno(HttpServletRequest request,
            HttpServletResponse response) {

        String id = request.getParameter("id");
        String url = "/app/liquidacion/numero_interno.jsp";

        Movil c = new Movil();
        c.setId(Integer.parseInt(id));
        c.setEstado(1);

        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);

        try {
            ArrayList<Movil> SubModulos = MovilBD.movilesPlaca(c);
            session.setAttribute("select", select);
            session.setAttribute("numeroInterno", SubModulos);

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public String Descuentos(HttpServletRequest request,
            HttpServletResponse response) {

        String fijo = request.getParameter("fijo");
        String url = "/app/liquidacion/categoriasDeDescuento.jsp";

        CategoriasDeDescuento c = new CategoriasDeDescuento();
        c.setEs_fija(Integer.parseInt(fijo));
        c.setEstado(1);

        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);

        try {
            ArrayList<CategoriasDeDescuento> categoriasDeDescuentoFijas = CategoriasDeDescuentoBD.categoriasDeDescuentoFijas(c);
            session.setAttribute("select", select);
            session.setAttribute("categoriasFijas", categoriasDeDescuentoFijas);

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public String DescuentosFusa(HttpServletRequest request,
            HttpServletResponse response) {

        String fijo = request.getParameter("fijo");
        String dsct = request.getParameter("descuento");
        String url = "/app/liquidacion/categoriasDeDescuentoFusa.jsp";

        CategoriasDeDescuento c = new CategoriasDeDescuento();
        c.setEs_fija(Integer.parseInt(fijo));
        c.setDescuenta_del_total(Integer.parseInt(dsct));
        c.setEstado(1);

        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);

        try {
            ArrayList<CategoriasDeDescuento> categoriasDeDescuentoFijasFusa = CategoriasDeDescuentoBD.categoriasDeDescuentoFijasFusa(c);
            session.setAttribute("select", select);
            session.setAttribute("categoriasFijasFusa", categoriasDeDescuentoFijasFusa);

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public String buscarTarifas(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        int estado = Integer.parseInt(request.getParameter("estado"));

        String url = "";
        HttpSession session = request.getSession();

        Tarifa gm = new Tarifa();
        gm.setActiva(estado);

        ArrayList retorno = new ArrayList();
        try {
            switch (id) {
                case 1: {
                    retorno = LiquidacionBD.searchActiveTax(gm);
                    break;
                }
            }
            session.setAttribute("tarifas", retorno);
            url = "/app/liquidacion/buscarTarifas.jsp";

        } catch (ExisteRegistroBD ex) {
            System.err.println("ERROR " + ex);
        }
        return url;
    }

    public String buscarEtq(HttpServletRequest request,
            HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String url = "";
        HttpSession session = request.getSession();
        //Etiquetas retorno = LiquidacionBD.searchTags(); 

        //Se consulta la configuracion de etiquetas para el perfil del usuario y se agrega como una variable de sesion
        obtenerEtiquetasLiquidacionPerfil(request);

//            session.setAttribute("tags", retorno);
        url = "/app/liquidacion/buscarEtiquetas.jsp";
        return url;
    }

    public String encontrarCategoriaDeDescuento(HttpServletRequest request,
            HttpServletResponse response) {

        String id = request.getParameter("id");
        String url = "/app/liquidacion/categoriaDeDescuentoEncontrado.jsp";

        CategoriasDeDescuento c = new CategoriasDeDescuento();
        c.setId(Integer.parseInt(id));
        c.setEs_fija(1);
        c.setEstado(1);

        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);

        try {
            CategoriasDeDescuento categoriaDeDescuento = CategoriasDeDescuentoBD.encontrarCategorias(c);

            ArrayList<CategoriasDeDescuento> categoriasDeDescuentoFijas = CategoriasDeDescuentoBD.categoriasDeDescuentoFijas(c);

            session.setAttribute("select", select);
            session.setAttribute("categoriaEncontrada", categoriaDeDescuento);
            session.setAttribute("categoriasFijas", categoriasDeDescuentoFijas);
            request.setAttribute("idFila", categoriaDeDescuento.getId() + "-" + categoriaDeDescuento.getUniqueId());

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public String encontrarCategoriaDeDescuentoFusa(HttpServletRequest request,
            HttpServletResponse response) {

        String id = request.getParameter("id");
        String url = "/app/liquidacion/categoriaDeDescuentoEncontradoFusa.jsp";

        CategoriasDeDescuento c = new CategoriasDeDescuento();
        c.setId(Integer.parseInt(id));
        c.setEstado(1);

        HttpSession session = request.getSession();
        SelectBD select = new SelectBD(request);

        try {
            CategoriasDeDescuento categoriaDeDescuento = CategoriasDeDescuentoBD.encontrarCategorias(c);
            session.setAttribute("select", select);
            session.setAttribute("categoriaEncontrada", categoriaDeDescuento);
            request.setAttribute("idFila", categoriaDeDescuento.getId() + "-" + categoriaDeDescuento.getUniqueId());

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorSubModuloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

}
