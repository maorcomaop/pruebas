/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.AlarmaInfoRegBD;
import com.registel.rdw.datos.ConteoPerimetroBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.registel.rdw.datos.InformacionRegistradoraBD;
import com.registel.rdw.datos.PuntoControlBD;
import com.registel.rdw.datos.RutaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.AuditoriaInformacionRegistradora;
import com.registel.rdw.logica.InformacionRegistradora;
import com.registel.rdw.logica.Motivo;
import com.registel.rdw.logica.ParametrosBusqueda;
import com.registel.rdw.logica.Ruta;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.MakeExcel;
import com.registel.rdw.utils.Restriction;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorInfoRegistradora extends HttpServlet {
    
    private static final int ERROR_PERMISO_USUARIO = 1;
    private static final int ERROR_CONSULTA = 2;
    private static final int CONSULTA_HECHA = 3;
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {        
        
        // Si se inicia liquidacion de vehiculo (numero interno)
        String sveh = request.getParameter("id");
        int veh = Restriction.getNumber(sveh);        
        
        HttpSession session = request.getSession();
        Usuario u = ( Usuario ) session.getAttribute("login");
        
        String url;
        
        if (veh > 0) {
            request.setAttribute("veh_inforeg", veh);
            
            url = "/app/liquidacion/nuevaLiquidacion.jsp";            
            
            if (u != null && u.getNombreEmpresa() != null) {
                String nombre_empresa = u.getNombreEmpresa().toLowerCase();
                if (nombre_empresa.contains("fusacatan") ||
                    nombre_empresa.contains("tierragrata"))
                    url = "/app/liquidacion/nuevaLiquidacionFusa.jsp";            
            }
            
        } else {                 

            String fecha_inicio, fecha_final, id_movil;
            int id_usuario = (u != null) ? u.getId() : 0, rs, nlimite;

            // Se verifica si existe consulta previa
            ParametrosBusqueda pr = (ParametrosBusqueda) session.getAttribute("parametrosBusqueda");
            if (pr != null) {
                fecha_inicio = pr.getFechaInicio();
                fecha_final  = pr.getFechaFinal();
                id_usuario   = Restriction.getNumber(pr.getIdUsuario());
                id_movil     = pr.getIdMovil();
                nlimite      = Restriction.getNumber(pr.getLimite());
            } else {
                fecha_inicio = fecha_final = id_movil = "";
                nlimite = 30;
                pr = new ParametrosBusqueda();
                pr.setIdUsuario("" + id_usuario);
                pr.setLimite("" + nlimite);
                session.setAttribute("parametrosBusqueda", pr);
            }
                        
            // Se consulta informacion registradora con respecto a parametros establecidos
            int nid_movil = Restriction.getNumber(id_movil);
            if (id_movil     != null && id_movil     != "" &&
                fecha_inicio != null && fecha_inicio != "") {            
                
                rs = InformacionRegistradoraBD.selectBy(id_usuario, nid_movil, fecha_inicio, fecha_final, nlimite);

            } else {            
                rs = InformacionRegistradoraBD.select(id_usuario, nlimite);
            }       

            if (rs == CONSULTA_HECHA) {                
                ArrayList<InformacionRegistradora> lst = InformacionRegistradoraBD.getLstinforeg();
                session.setAttribute("lstinforeg", lst);
                request.setAttribute("busquedaPrevia", "1");
            }
            
            url = "/app/registradora/consultaInfoRegistradora.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
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
        
        if (requestURI.endsWith("/consultarInfoReg")) { 
            url = consultarInfoRegistradora(request, response);
        } else if (requestURI.endsWith("/mostrarInfoReg")) {
            url = mostrarInfoRegistradora(request, response);
        } else if (requestURI.endsWith("/editarInfoGeneral")) {
            url = editarInfoGeneral(request, response);
        } else if (requestURI.endsWith("/editarInfoSalida")) {
            url = editarInfoSalida(request, response);
        } else if (requestURI.endsWith("/exportarExcel")) {
            url = exportarExcel(request, response);
        } else if (requestURI.endsWith("/asignaRutaIR")) {
            url = asignaRuta(request, response);
        } 
        
        if (url != "") {
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }
    
    // Procesa solicitud de consulta en tabla informacion registradora para
    // un conjunto de parametros establecidos.
    public String consultarInfoRegistradora (HttpServletRequest request,
            HttpServletResponse response) {       
        
        String id_movil     = request.getParameter("id_movil");
        String placa        = request.getParameter("placa");
        String num_interno  = request.getParameter("num_interno");
        String fecha_inicio = request.getParameter("fechaInicio");
        String fecha_final  = request.getParameter("fechaFinal");
        String limite       = request.getParameter("slimite");
        String tipodia      = request.getParameter("stdia");
        String autoconsulta = request.getParameter("autoconsulta");        
        String fnumvuelta   = request.getParameter("p_numvuelta");        
        String fruta        = request.getParameter("p_ruta");
        
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("login");
        int id_usuario = u.getId(); int rs = 0;
                
        String msg, msg_type; msg = msg_type = "";
        String item_movil, item_usuario; item_movil = item_usuario = "";
        
        ArrayList<InformacionRegistradora> lstinforeg = null;                
        int nlimite = Restriction.getNumber(limite);
        
        // Si peticion proviene de mostrarInfoRegistradora, se
        // verifican los parametros de busqueda previos y se usan.
        // Esto con el fin de mantener los datos visualizados consistentes,
        // y no cambien tras consulta mas especifica.
        ParametrosBusqueda pr1 = null; 
        String sbusquedaPrevia = (String) request.getAttribute("busquedaPrevia");
        
        boolean busquedaPrevia = false;
        if (sbusquedaPrevia != null && sbusquedaPrevia.compareTo("1") == 0) {
            busquedaPrevia = true;
            pr1 = (ParametrosBusqueda) session.getAttribute("parametrosBusqueda");
            if (pr1 != null) {                                        
                fecha_inicio = pr1.getFechaInicio();
                fecha_final  = pr1.getFechaFinal();
                id_usuario   = Restriction.getNumber(pr1.getIdUsuario());
                id_movil     = pr1.getIdMovil();
                item_movil   = pr1.getItemMovil();                                  
                nlimite      = Restriction.getNumber(pr1.getLimite());                
                pr1.setIdRuta(fruta);
                pr1.setNumeroVuelta(fnumvuelta);
            }
        }
        
        // Se consulta informacion registradora con respecto a parametros establecidos
        int nid_movil = Restriction.getNumber(id_movil);        
        if (id_movil     != null && id_movil     != "" &&
            fecha_inicio != null && fecha_inicio != "") {                        
            
            rs = InformacionRegistradoraBD.selectBy(id_usuario, nid_movil, fecha_inicio, fecha_final, nlimite);
            
        } else if (!busquedaPrevia && (id_movil != "" || fecha_inicio != "")) {                        
            msg = "* Debe elegir una fecha inicial y una placa.";
            msg_type = "alert alert-info";            
        
        } else {            
            rs = InformacionRegistradoraBD.select(id_usuario, nlimite);
        }       
        
        boolean error = false;
        if (msg == "") {
            if (rs == ERROR_PERMISO_USUARIO) {
                msg = "* Usuario no tiene relaci&oacute;n y/o permisos adecuados con la empresa.";
                msg_type = "alert alert-warning";
                error = true;
            } else if (rs == ERROR_CONSULTA) {
                msg = "* Error en consulta.";
                msg_type = "alert alert-warning";
                error = true;
            }
        } else { error = true; } 
                
        // Se conservan parametros de busqueda en la interfaz
        item_movil   = id_movil + "," + placa + "," + num_interno;
        item_usuario = "" + id_usuario;
        
        ParametrosBusqueda pr = new ParametrosBusqueda();
        pr.setFechaInicio(fecha_inicio);
        pr.setFechaFinal(fecha_final);        
        pr.setIdUsuario(item_usuario);
        pr.setIdMovil(id_movil);
        pr.setItemMovil(item_movil);        
        pr.setIdRuta(fruta);
        pr.setNumeroVuelta(fnumvuelta);        
        pr.setLimite(limite);
        pr.setTipoDia(tipodia);
        
        // Si peticion proviene de mostrarInfoRegistradora y hubo parametros
        // establecidos, estos se mantienen (se reemplazan)
        if (pr1 != null) { pr = pr1; }
        session.setAttribute("parametrosBusqueda", pr);
        
        // Si autoconsulta esta activa, se mantiene
        if (autoconsulta != null && autoconsulta.equalsIgnoreCase("true")) {
            request.setAttribute("autoconsulta", "true");
        }
        
        if (error) {
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", msg_type);
        } else {
            lstinforeg = InformacionRegistradoraBD.getLstinforeg();        
            session.setAttribute("lstinforeg", lstinforeg);            
        }
        
        session.setAttribute("data_ir", "1");
        return "/app/registradora/consultaInfoRegistradora.jsp";
    }
    
    // Procesa solicitud de consulta para un registro de informacion registradora
    // en especifico.
    public String mostrarInfoRegistradora (HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidInforeg = request.getParameter("idInforeg");
        String numInterno = request.getParameter("numeroInterno");
        String placa      = request.getParameter("placa");
        String nombreBS   = request.getParameter("nombreBS");
        String nombreBL   = request.getParameter("nombreBL");       
        
        int idInforeg = Restriction.getNumber(sidInforeg);        
        InformacionRegistradora ir = InformacionRegistradoraBD.selectById(idInforeg);
        
        // Se establece busquedaPrevia para mantener resultado
        // de consulta previa visible
        request.setAttribute("busquedaPrevia", "1");
        // Se consulta nuevamente (con parametros previos)
        consultarInfoRegistradora(request, response);
        
        HttpSession session = request.getSession();
                        
        if (ir != null) {
            ir.setNumeroInterno(numInterno);
            ir.setPlaca(placa);            
            ir.setNombreBS(nombreBS);
            ir.setNombreBL(nombreBL);
            session.setAttribute("infoReg", ir);
            request.setAttribute("mostrarInfo", "true");
        } else {
            session.setAttribute("infoReg", null);
            request.setAttribute("mostrarInfo", "false");
        }               
                
        return "/app/registradora/consultaInfoRegistradora.jsp";
    }
    
    // Procesa solicitud de edicion de registro informacion registradora
    // especifico. Para la edicion es necesario especificar contraseña
    // para realizar accion.
    public String editarInfoGeneral (HttpServletRequest request,
            HttpServletResponse response) {
                
        String numInterno   = request.getParameter("numeroInterno");
        String fechaIngreso = request.getParameter("fechaIngreso");
        String horaIngreso  = request.getParameter("horaIngreso");
        String numInicial   = request.getParameter("numeracionBS");
        String numFinal     = request.getParameter("numeroLlegada");
        String diferencia   = request.getParameter("diferenciaNumero");
        String entradas     = request.getParameter("entradas");
        String salidas      = request.getParameter("salidas");
        String difEntrada   = request.getParameter("diferenciaEntrada");
        String difSalida    = request.getParameter("diferenciaSalida");
        String idInfreg     = request.getParameter("idInfreg");
        String acceso       = request.getParameter("accesoInfoGeneral");

        // Se verifica acceso para edicion
        if (acceso == "" || !verificaAcceso(acceso, request)) {
            request.setAttribute("msg", "* Modificaci&oacute;n no realizada, acceso denegado. Verifique su contrase&ntilde;a.");
            request.setAttribute("msgType", "alert alert-danger");
            return "/app/registradora/consultaInfoRegistradora.jsp";
        }
                
        String motivoEdicion = request.getParameter("motivoEdicion");        
        
        DateFormat fi = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hi = new SimpleDateFormat("HH:mm:ss");
        Date fechaIngreso_date = null;
        Date horaIngreso_date  = null;
        try {
            fechaIngreso_date = fi.parse(fechaIngreso);
            horaIngreso_date  = hi.parse(horaIngreso);
        } catch (ParseException e) { System.err.println(e); }
        
        
        Motivo razon_modificacion = new Motivo();
        razon_modificacion.setDescripcionMotivo(motivoEdicion);
        razon_modificacion.setModificacionLocal(new Short("0"));
        razon_modificacion.setTablaAuditoria("tbl_informacion_registradora");
        
        InformacionRegistradora ir_prev = InformacionRegistradoraBD.selectById(Restriction.getNumber(idInfreg));
        
        HttpSession session = request.getSession();
        Usuario user = ( Usuario ) session.getAttribute("login");        

        InformacionRegistradora ir = new InformacionRegistradora();
        ir.setFechaIngreso(fechaIngreso_date);
        ir.setHoraIngreso(horaIngreso_date);
        ir.setNumeracionBS(Restriction.getNumber(numInicial));
        ir.setNumeroLlegada(Restriction.getNumber(numFinal));
        ir.setDiferenciaNumero(Restriction.getNumber(diferencia));
        ir.setEntradas(Restriction.getNumber(entradas));
        ir.setSalidas(Restriction.getNumber(salidas));
        ir.setDiferenciaEntrada(Restriction.getNumber(difEntrada));
        ir.setDiferenciaSalida(Restriction.getNumber(difSalida));
        ir.setId(Restriction.getNumber(idInfreg));
        
        // Actualiza registro de informacion registradora
        if (InformacionRegistradoraBD.updateInfoGeneral(ir, ir_prev, razon_modificacion, user)) {
            request.setAttribute("msg", "* Informaci&oacute;n general de registradora actualizada correctamente.");
            request.setAttribute("msgType", "alert alert-success");            
            
            // Almacena audicion de actualizacion de registro
            AuditoriaInformacionRegistradora air = InformacionRegistradoraBD.getAuditoriaRegistradora(ir_prev);
            air.setFk_usuario(user.getId());
            InformacionRegistradoraBD.auditar(ir, ir_prev, razon_modificacion, user, air);
                        
            request.setAttribute("busquedaPrevia", "1");
            return consultarInfoRegistradora(request, response);
        } else {
            request.setAttribute("msg", "* Informaci&oacute;n general de registradora no actualizada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/registradora/consultaInfoRegistradora.jsp";
    }
    
    // Procesa solicitud para editar campos de salida para un
    // registro de informacion registradora.
    public String editarInfoSalida (HttpServletRequest request,
            HttpServletResponse response) {
       
        String fechaSalidaBS    = request.getParameter("fechaSalidaBS");
        String horaSalidaBS     = request.getParameter("horaSalidaBS");
        String numeracionBS     = request.getParameter("numeracionBS");
        String entradasBS       = request.getParameter("entradasBS");
        String salidasBS        = request.getParameter("salidasBS");
        String idInfreg         = request.getParameter("idInfreg");
        String acceso           = request.getParameter("accesoInfoSalida");

        // Verifica acceso para edicion
        if (acceso == "" || !verificaAcceso(acceso, request)) {
            request.setAttribute("msg", "* Modificaci&oacute;n no realizada, acceso denegado. Verifique su contrase&ntilde;a.");
            request.setAttribute("msgType", "alert alert-danger");
            return "/app/registradora/consultaInfoRegistradora.jsp";
        }
        
        DateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hs = new SimpleDateFormat("HH:mm:ss");
        Date fechaSalida_date = null;
        Date horaSalida_date  = null;
        try {
            fechaSalida_date = fs.parse(fechaSalidaBS);
            horaSalida_date  = hs.parse(horaSalidaBS);
        } catch (ParseException e) { System.err.println(e); }
        
        InformacionRegistradora ir = new InformacionRegistradora();
        ir.setFechaSalidaBS(fechaSalida_date);
        ir.setHoraSalidaBS(horaSalida_date);
        ir.setNumeracionBS(Restriction.getNumber(numeracionBS));
        ir.setEntradasBS(Restriction.getNumber(entradasBS));
        ir.setSalidasBS(Restriction.getNumber(salidasBS));
        ir.setId(Restriction.getNumber(idInfreg));
        
        HttpSession session = request.getSession();
        Usuario user = ( Usuario ) session.getAttribute("login");
        
        // Actualiza registros salida de informacion registradora
        if (InformacionRegistradoraBD.updateInfoSalida(ir)) {
            request.setAttribute("msg", "* Informaci&oacute;n de salida de registradora actualizada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            
            request.setAttribute("busquedaPrevia", "1");
            return consultarInfoRegistradora(request, response);
        } else {
            request.setAttribute("msg", "* Informaci&oacute;n de salida de registradora no actualizada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/registradora/consultaInfoRegistradora.jsp";
    }

    // Consulta registros de informacion registradora para un conjunto de
    // parametros especificados en la elaboracion de reporte excel.
    public ArrayList<InformacionRegistradora> consultaInfoRegistradoraExcel (
                                HttpServletRequest request,
                                HttpServletResponse response) {
        
	String id_movil     = request.getParameter("id_movil_e");
	String placa        = request.getParameter("placa_e");
	String num_interno  = request.getParameter("num_interno_e");
	String fecha_inicio = request.getParameter("fechaInicio_e");
	String fecha_final  = request.getParameter("fechaFinal_e");
	String limite       = request.getParameter("slimite_e");
	
	HttpSession session = request.getSession();
	Usuario u = (Usuario) session.getAttribute("login");
	int id_usuario = u.getId(); int rs = 0;
			
	String msg, msg_type; msg = msg_type = "";	
	
	ArrayList<InformacionRegistradora> lstinforeg = null;                
	int nlimite = Restriction.getNumber(limite);
	
	// Se consulta informacion registradora con respecto a parametros establecidos
	int nid_movil = Restriction.getNumber(id_movil);        
	if (id_movil     != null && id_movil     != "" &&
            fecha_inicio != null && fecha_inicio != "") {                        
		
            rs = InformacionRegistradoraBD.selectBy(id_usuario, nid_movil, fecha_inicio, fecha_final, nlimite);
		
	} else if (id_movil == "" || fecha_inicio == "") {
            msg = "* Debe elegir una fecha inicial y una placa.";
            msg_type = "alert alert-info";            
	
	} else {            
            rs = InformacionRegistradoraBD.select(id_usuario, nlimite);
	}       
	
	boolean error = false;
	if (msg == "") {
            if (rs == ERROR_PERMISO_USUARIO) {
                msg = "* Usuario no tiene relaci&oacute;n y/o permisos adecuados con la empresa.";
                msg_type = "alert alert-warning";
                error = true;
            } else if (rs == ERROR_CONSULTA) {
                msg = "* Error en consulta.";
                msg_type = "alert alert-warning";
                error = true;
            }
	} else { error = true; } 

	if (error) {
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", msg_type);
            request.setAttribute("error", "1");
            return null;
	} else {                        
            return InformacionRegistradoraBD.getLstinforeg();
	}
    }
    
    // Procesa solicitud para exportar listado de informacion registradora
    // a excel.
    public String exportarExcel (HttpServletRequest request,
            HttpServletResponse response) throws IOException {       
                            
        //HttpSession session = request.getSession();
        //ArrayList<InformacionRegistradora> lstinfreg = 
            //(ArrayList<InformacionRegistradora>) session.getAttribute("lstinforeg");        
            
        // Consulta de registros de informacion registradora
        ArrayList<InformacionRegistradora> lstinfreg = consultaInfoRegistradoraExcel(request, response);
        String error = (String) request.getAttribute("error");
        if (error != null && error.compareTo("1") == 0) {
            return "/app/registradora/consultaInfoRegistradora.jsp";
        }
        
        if (lstinfreg != null && lstinfreg.size() > 0) {
            MakeExcel excelFile = new MakeExcel();           
            
            // Se consulta informacion relacionada de cada registro de informacion registradora
            for (InformacionRegistradora ir : lstinfreg) {
                Ruta r = RutaBD.get("" + ir.getIdRuta());
                
                if (r != null) { ir.setNombreRuta(r.getNombre()); }
                else           { ir.setNombreRuta("NA"); }
                
                ir.setAlarmas(AlarmaInfoRegBD.selectBy(ir.getId()));
                ir.setConteosPerimetro(ConteoPerimetroBD.selectBy(ir.getId()));
                ir.setPuntosControl(PuntoControlBD.selectBy(ir.getId()));
            }
            
            excelFile.addSheet_IR("Informacion registradora", lstinfreg);
            excelFile.addSheet_ALM("Alarmas", lstinfreg);
            excelFile.addSheet_PC("Puntos de control", lstinfreg);
            excelFile.addSheet_CP("Conteos en perimetro", lstinfreg);
            
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Reporte-informacion-registradora.xls");
            
            HSSFWorkbook file = excelFile.getExcelFile();
            file.write(response.getOutputStream());
            response.flushBuffer();
            response.getOutputStream().close();
        }
        
        return "/app/registradora/consultaInfoRegistradora.jsp";
    }
    
    // Procesa solicitud para asignacion de ruta a un registro
    // de informacion registradora especifico.
    public String asignaRuta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidInfreg = request.getParameter("idInfreg");
        String sidruta   = request.getParameter("sruta");
        String acceso    = request.getParameter("accesoAsignarRuta");

        // Verifica acceso para edicion
        if (acceso == "" || !verificaAcceso(acceso, request)) {
            request.setAttribute("msg", "* Modificaci&oacute;n, acceso denegado. Verifique su contrase&ntilde;a.");
            request.setAttribute("msgType", "alert alert-danger");
            return "/app/registradora/consultaInfoRegistradora.jsp";
        }
        
        int idInfreg = Integer.parseInt(sidInfreg);
        int idruta   = Integer.parseInt(sidruta);
        
        // Actualiza ruta de registro informacion registradora
        if (InformacionRegistradoraBD.asignaRuta(idInfreg, idruta)) {
            request.setAttribute("msg", "* Ruta asignada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            
            request.setAttribute("busquedaPrevia", "1");
            return consultarInfoRegistradora(request, response);
        } else {
            request.setAttribute("msg", "* Ruta no asignada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/registradora/consultaInfoRegistradora.jsp";
    }
    
    private static String accessKey = null;
    
    // Comprueba si llave especificada corresponde a la almacenada
    // y posteriormente otorgar acceso. Si llave es correcta se
    // almacena temporalmente para agilizar comprobacion posterior.
    public boolean verificaAcceso (String key, HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        String prevKey = (String) session.getAttribute("mydog");
        
        if (prevKey == null || prevKey.compareTo("1") != 0)
            accessKey = null;
        
        String genKey = InformacionRegistradoraBD.cadenaDeAccesoGenerada(key);
        
        if (accessKey == null) {
            // Se consulta y valida clave de acceso en servidor remoto
            String tempKey = InformacionRegistradoraBD.cadenaDeAcceso();
            if (tempKey != null && tempKey.compareTo(genKey) == 0) {
                accessKey  = tempKey; session.setAttribute("mydog", "1");
                return true;
            }
        } else {
            // Se usa variable temporal con clave almacenada, tras una
            // consulta previa al servidor.
            if (accessKey.compareTo(genKey) == 0) {
                return true;
            } 
        }
        
        return false;
    }
}
