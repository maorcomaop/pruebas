/**
 * Clase controlador Alarma
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
import com.registel.rdw.datos.AuditoriaBD;
import com.registel.rdw.datos.MotivoBD;
import com.registel.rdw.datos.SelectBD;

import com.registel.rdw.logica.Motivo;
import com.registel.rdw.logica.AuditoriaAlarma;
import com.registel.rdw.logica.AuditoriaCategorias;
import com.registel.rdw.logica.AuditoriaConductor;
import com.registel.rdw.logica.AuditoriaEmpresa;
import com.registel.rdw.logica.AuditoriaInformacionRegistradora;
import com.registel.rdw.logica.AuditorialLiquidacionGeneral;
import com.registel.rdw.logica.AuditoriaPerfil;
import com.registel.rdw.logica.AuditoriaPunto;
import com.registel.rdw.logica.AuditoriaPuntoDeControl;
import com.registel.rdw.logica.AuditoriaRuta;
import com.registel.rdw.logica.AuditoriaRutaPunto;
import com.registel.rdw.logica.AuditoriaTarifa;
import com.registel.rdw.logica.AuditoriaUsuario;
import com.registel.rdw.logica.AuditoriaVehiculo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorAuditoria extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    private String lnk = "Para asignarle un nueva alarma, haga click <a href='/RDW1/app/evento/nuevoEvento.jsp'>aqui.</a>";
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String nada = request.getParameter("r");       
        HttpSession session = request.getSession(false);
        String url = "/";
        
        if (session == null || 
            session.getAttribute("login") == null || 
            session.getAttribute("select") == null || 
            Expire.check(session.getCreationTime())) {
            url = "/index.jsp";
        } 
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        //String ulr1=request.getParameter("url1");        
        String requestURI = request.getRequestURI();
        
        String url = "/";
        if (requestURI.endsWith("/cargarAuditoria")) {
            url = cargarAuditoria(request, response);
        } else if (requestURI.endsWith("/verMasAuditoria")) {
            try {        
                url = verAuditoria(request, response);
            } catch (SQLException ex) {
                System.out.println(""+ex.getMessage());
            }
        }

                
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       
      /**FUNCION QUE PERMITE CARGAR el listado de auditorias**/ 
    public String cargarAuditoria (HttpServletRequest request,
            HttpServletResponse response) {
                
        String nombreTabla =  request.getParameter("tablas");         
        String fecha_inicio = request.getParameter("fecha_inicio");
        String fecha_fin = request.getParameter("fecha_fin");
        
        String fecha_hora_inicio = request.getParameter("fecha_hora_inicio");
        String fecha_hora_fin = request.getParameter("fecha_hora_fin");             
        String franjas = request.getParameter("buscar_tiempo");
        
        String cantidad_registros = request.getParameter("cantidad_registros");
        
        String cond = "  WHERE Field like '%OLD%' OR Field like '%NEW%' OR Field like '%ESTADO%' OR Field like '%USUA%' OR Field like '%EVEN%' OR Field like '%VEH%'";
        HttpSession session = request.getSession();
        
              
                
        switch(nombreTabla)
        {
            case "tbl_auditoria_alarma": {                                                
                try {
                        ArrayList<String> columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);    
                        ArrayList selectAll = AuditoriaBD.selectAllAlarma(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);                        
                        session.setAttribute("datos", selectAll);
                        session.setAttribute("columnas", columns);
                        session.setAttribute("tablaAImprimir", 1);
                        request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }                
                break;
            }
            case "tbl_auditoria_categoria_descuentos": {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllCategorias(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 2);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_conductor": {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllConductor(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 3);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_empresa":
            {                
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllEmpresas(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 4);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_informacion_registradora":
            {
                 try {
                    ArrayList selectAll = AuditoriaBD.selectAllInformacionRegistradora(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 5);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_liquidacion_general":
            {
               try {
                    ArrayList selectAll = AuditoriaBD.selectAllLiquidacion(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 6);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_perfil":
            {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllPerfil(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 7);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_punto":
            {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllPunto(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 8);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }          
            case "tbl_auditoria_ruta":
            {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllRuta(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 10);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
           
            case "tbl_auditoria_tarifa": {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllTarifa(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 12);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_usuario":
            {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllUsuario(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 13);
                    request.setAttribute("msg", "*");                        

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_vehiculo": {
                try {
                    ArrayList selectAll = AuditoriaBD.selectAllVehiculo(fecha_inicio, fecha_fin, nombreTabla, cantidad_registros);
                    ArrayList columns = AuditoriaBD.labelColumnsTable(nombreTabla, cond);
                    session.setAttribute("datos", selectAll);
                    session.setAttribute("columnas", columns);
                    session.setAttribute("tablaAImprimir", 14);
                    request.setAttribute("msg", "*");

                } catch (ExisteRegistroBD ex) {
                    request.setAttribute("msg", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }    
        }
        return "/app/auditoria/listadoAuditoria.jsp";
    }    
   
    
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verAuditoria (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String id =  request.getParameter("id");
        String fecha_inicio =  request.getParameter("fecha_inicial");
        String fecha_fin =  request.getParameter("fecha_final");
        String nombreTabla =  request.getParameter("table");
        
        String url="";
        String cond = "  WHERE Field like '%K%' OR Field like '%OLD%' OR Field like '%NEW%' OR Field like '%ESTADO%' OR Field like '%USUA%' OR Field like '%EVEN%';";
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        
        
        switch(nombreTabla)
        {
            case "tbl_auditoria_alarma": {
                try {
                    AuditoriaAlarma a = new AuditoriaAlarma();
                    a.setId(Integer.parseInt(id));
                    AuditoriaAlarma auditoriaAlarmaEncontrado = AuditoriaBD.selectByOneAlarma(a, nombreTabla, cond);
                    if (auditoriaAlarmaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaAlarmaEncontrado);
                        session.setAttribute("tablaAImprimir", 1);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_categoria_descuentos": {
                try {
                    AuditoriaCategorias a = new AuditoriaCategorias();
                    a.setId(Integer.parseInt(id));
                    AuditoriaCategorias auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneCategoria(a, nombreTabla, cond);
                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 2);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_conductor": {
                try {
                    AuditoriaConductor a = new AuditoriaConductor();
                    a.setId(Integer.parseInt(id));        
                    AuditoriaConductor auditoriaConductorEncontrado = AuditoriaBD.selectByOneConductor(a, nombreTabla, cond);
                    if (auditoriaConductorEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaConductorEncontrado);                        
                        session.setAttribute("tablaAImprimir", 3);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
            }
             case "tbl_auditoria_empresa":                 
            {                
                try {
                    AuditoriaEmpresa a = new AuditoriaEmpresa();
                    a.setId(Integer.parseInt(id));
                    AuditoriaEmpresa auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneEmpresa(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 4);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_informacion_registradora":
            {
                try {
                    AuditoriaInformacionRegistradora a = new AuditoriaInformacionRegistradora();
                    MotivoBD m = new MotivoBD();
                    a.setId(Integer.parseInt(id));
                    AuditoriaInformacionRegistradora auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneInformacionRegistradora(a, nombreTabla, cond);
                    Motivo motivoAuditoria = m.selectByOneAIR(a);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("motivo", motivoAuditoria);
                        session.setAttribute("tablaAImprimir", 5);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_liquidacion_general":
            {
             try {
                    AuditorialLiquidacionGeneral a = new AuditorialLiquidacionGeneral();
                    a.setId(Integer.parseInt(id));
                    AuditorialLiquidacionGeneral auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneLiquidacion(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("liquidacion", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 6);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_perfil": {
               try {
                    AuditoriaPerfil a = new AuditoriaPerfil();
                    a.setId(Integer.parseInt(id));
                    AuditoriaPerfil auditoriaCategoriaEncontrado = AuditoriaBD.selectByOnePerfil(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 7);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_punto":
            {                                
                 try {
                    AuditoriaPunto a = new AuditoriaPunto();
                    a.setId(Integer.parseInt(id));
                    AuditoriaPunto auditoriaCategoriaEncontrado = AuditoriaBD.selectByOnePunto(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 8);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_punto_control": {
                try {
                    AuditoriaPuntoDeControl a = new AuditoriaPuntoDeControl();
                    a.setId(Integer.parseInt(id));
                    AuditoriaPuntoDeControl auditoriaPuntoControlEncontrado = AuditoriaBD.selectByOnePuntoDeControl(a, nombreTabla, cond);

                    if (auditoriaPuntoControlEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaPuntoControlEncontrado);
                        session.setAttribute("tablaAImprimir", 9);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_ruta":
            {
                try {
                    AuditoriaRuta a = new AuditoriaRuta();
                    a.setId(Integer.parseInt(id));
                    AuditoriaRuta auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneRuta(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("miruta", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 10);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_ruta_punto":
            {
               try {
                    AuditoriaRutaPunto a = new AuditoriaRutaPunto();
                    a.setId(Integer.parseInt(id));
                    AuditoriaRutaPunto auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneRutaPunto(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 11);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_tarifa": {
                try {
                    AuditoriaTarifa a = new AuditoriaTarifa();
                    a.setId(Integer.parseInt(id));
                    AuditoriaTarifa auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneTarifa(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 12);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_usuario":
            {
                try {
                    AuditoriaUsuario a = new AuditoriaUsuario();
                    a.setId(Integer.parseInt(id));                    
                    AuditoriaUsuario auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneUsuario(a, nombreTabla);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 13);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "tbl_auditoria_vehiculo":
            {
                try {
                    AuditoriaVehiculo a = new AuditoriaVehiculo();
                    a.setId(Integer.parseInt(id));
                    AuditoriaVehiculo auditoriaCategoriaEncontrado = AuditoriaBD.selectByOneVehiculo(a, nombreTabla, cond);

                    if (auditoriaCategoriaEncontrado != null) {
                        session.setAttribute("auditoria", auditoriaCategoriaEncontrado);
                        session.setAttribute("tablaAImprimir", 14);
                        url = "/app/auditoria/unAuditoria.jsp";
                    } else {
                        url = "/app/auditoria/listadoAuditoria.jsp";
                    }

                } catch (ExisteRegistroBD ex) {
                    Logger.getLogger(ControladorAuditoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }            
            
        }
        
         return url;
    }
    
   
   



    
  
   

   
    
    
}

