/**
 * Clase controlador Relacion Vehiculo Conductor
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
import com.registel.rdw.datos.PilaConexiones;
import com.registel.rdw.datos.RelacionVehiculoConductorBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.RelacionVehiculoConductor;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import org.apache.catalina.ant.ReloadTask;
import org.json.JSONException;
import org.json.JSONObject;

public class ControladorRelacionVehiculoConductor extends HttpServlet {

    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";
    private static final String USR_PROP = "prop";

    //private String lnk = "Para asignarle un nuevo , haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String nada = request.getParameter("r");
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
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/";
        boolean dispatcherResponse = true;

        if (requestURI.endsWith("/guardarRelacionVehiculoConductor")) {
            try {
                url = guardarRelacionVehiculoConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/editarRelacionVehiculoConductorAjax")) {
            try {
                dispatcherResponse = false;
                editarRelacionVehiculoConductorAjax(request, response);
            } catch (SQLException ex) {
                System.out.println(" " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/editarRelacionVehiculoConductor")) {
            try {
                url = editarRelacionVehiculoConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/verMasRelacionVehiculoConductor")) {
            try {
                url = verRelacionVehiculoConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/activarRelacionVehiculoConductor")) {
            try {
                dispatcherResponse = false;
                activarRelacionVehiculoConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" " + ex.getMessage());
            }
        } else if (requestURI.endsWith("/actualizarRelacionVehiculoConductor")) {
            try {
                dispatcherResponse = false;
                actualizarRelacionVehiculoConductor(request, response);
            } catch (SQLException ex) {
                System.out.println(" " + ex.getMessage());
            }
        }
        if (dispatcherResponse) {
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }

    /**
     * FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO*
     */
    public String guardarRelacionVehiculoConductor(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {

        String vehiculo = request.getParameter("vehiculo");
        String conductor = request.getParameter("conductor");
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("Hora y fecha: "+hourdateFormat.format(date));
        
        RelacionVehiculoConductor c = new RelacionVehiculoConductor();

        try {
            c.setIdConductor(Integer.parseInt(conductor));
            c.setIdVehiculo(Integer.parseInt(vehiculo));
            c.setEstado(new Short("1"));
            c.setFecha_asignacion(hourdateFormat.format(date));
        } catch (Exception e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la relación entre el conductor y el veh&iacute;culo");
            return "/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute("msg", "");
        try {
            if (RelacionVehiculoConductorBD.insert(c) == 1) {
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", "La relación entre el vehiculo y conductor se estableci&oacute; correctamente.");
            } else {
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", "No se logr&oacute; establecer una relación");
            }
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }
        return "/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp";
    }

    /**
     * FUNCION QUE PERMITE EDITAR UN NUEVO DE USUARIO*
     */
    public String editarRelacionVehiculoConductor(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {

        String url = "";
        HttpSession session = request.getSession();
        String idRelacionVehiculoConductor = request.getParameter("id_edit");
        String idVehiculo = request.getParameter("id_vehiculo");
        String idConductor = request.getParameter("conductor");

        RelacionVehiculoConductor c = new RelacionVehiculoConductor();

        try {
            c.setIdRelacionVehiculoConductor(Integer.parseInt(idRelacionVehiculoConductor));
            c.setIdConductor(Integer.parseInt(idConductor));
            c.setIdVehiculo(Integer.parseInt(idVehiculo));
            c.setEstado(new Short("1"));
        } catch (Exception e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la relación entre conductor y veh&iacute;culo");
            return "/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp";
        }

        SelectBD select = null;
        try {
            int resultado = RelacionVehiculoConductorBD.insert(c);
            switch (resultado) {
                case -1: {
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No logr&oacute; establecer una relacion");
                    request.setAttribute("idInfo", 2);
                    url = "/app/relacion_vehiculo_conductor/unaRelacionVehiculoConductor.jsp";
                    break;
                }
                case 0: {
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "El vehiculo ya tiene relacionado a un conductor; si desea modificar esta relación escoja otro conductor de la lista desplegable");
                    request.setAttribute("idInfo", 0);
                    url = "/app/relacion_vehiculo_conductor/unaRelacionVehiculoConductor.jsp";
                    break;
                }
                case 1: {
                    request.setAttribute("idInfo", 1);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    RelacionVehiculoConductor conductorlEncontrado = RelacionVehiculoConductorBD.selectByOneNew(c);
                    if (conductorlEncontrado != null) {
                        session.setAttribute("relacionVehiculoConductor", conductorlEncontrado);
                        request.setAttribute("msg", "La relación entre el vehículo y conductor se estable&oacute; correctamente.");
                    }
                    url = "/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp";
                    break;
                }

                default:
                    break;
            }

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public void editarRelacionVehiculoConductorAjax(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException {

        HttpSession session = request.getSession();
        String idRelacionVehiculoConductor = request.getParameter("id_edit");
        String idVehiculo = request.getParameter("id_vehiculo");
        String idConductor = request.getParameter("conductor");

        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");
        try {
            PrintWriter out = response.getWriter();

            JSONObject jsonResponse = new JSONObject();

            RelacionVehiculoConductor c = new RelacionVehiculoConductor();

            try {
                c.setIdRelacionVehiculoConductor(Integer.parseInt(idRelacionVehiculoConductor));
                c.setIdConductor(Integer.parseInt(idConductor));
                c.setIdVehiculo(Integer.parseInt(idVehiculo));
                c.setEstado(new Short("1"));
            } catch (Exception e) {
                try {
                    jsonResponse.put("idInfo", 2);
                    jsonResponse.put("msg", "No se puede registrar la relación entre conductor y veh&iacute;culo");
                } catch (JSONException ex) {
                    Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            SelectBD select = null;

            int resultado = RelacionVehiculoConductorBD.insert(c);
            switch (resultado) {
                case -1: {
                    try {
                        jsonResponse.put("msg", "No se logr&oacute; establecer una relación");
                        jsonResponse.put("idInfo", 2);
                    } catch (JSONException ex) {
                        Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case 0: {
                    try {
                        jsonResponse.put("msg", "El vehículo ya tiene relacionado a este conductor");
                        jsonResponse.put("idInfo", 0);
                    } catch (JSONException ex) {
                        Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case 1: {
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    List<RelacionVehiculoConductor> list = RelacionVehiculoConductorBD.selectAll(c);
                    try {
                        jsonResponse.put("relacionVehiculoConductor", list);
                        jsonResponse.put("idInfo", 1);
                        jsonResponse.put("msg", "La relación entre el vehículo y el conductor se estableció correctamente.");
                    } catch (JSONException ex) {
                        Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                default:
                    break;
            }

            out.println(jsonResponse.toString());
            out.flush();

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO*
     */
    public String verRelacionVehiculoConductor(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {

        String id = request.getParameter("id");
        String vehiculoId = request.getParameter("vehiculoId");

        String url = "";
        RelacionVehiculoConductor c = new RelacionVehiculoConductor();
        c.setIdRelacionVehiculoConductor(Integer.parseInt(id));
        c.setIdVehiculo(Integer.parseInt(vehiculoId));

        HttpSession session = request.getSession();
        try {
            RelacionVehiculoConductor conductorEncontrado = RelacionVehiculoConductorBD.selectByOne(c);
            List<RelacionVehiculoConductor> listVehiculoConductor = RelacionVehiculoConductorBD.selectAll(c);
            if (conductorEncontrado != null) {
                session.setAttribute("relacionVehiculoConductor", conductorEncontrado);
            } else {
                c.setIdConductor(-1);
                session.setAttribute("relacionVehiculoConductor", c);
            }
            session.setAttribute("listVehiculoConductor", listVehiculoConductor);
            url = "/app/relacion_vehiculo_conductor/unaRelacionVehiculoConductor.jsp";
            session.setAttribute("ajaxPage", true);
            if (request.getParameterMap().containsKey("include_")) {
                session.setAttribute("ajaxPage", true);
                url = "/include/relacionVehiculoConductorEdit.jsp";
            }
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionVehiculoConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public void activarRelacionVehiculoConductor(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException {

        String id = request.getParameter("id");
        String vehiculoId = request.getParameter("id_vehicle");
        String activo = request.getParameter("active");

        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        RelacionVehiculoConductor c = new RelacionVehiculoConductor();
        c.setIdRelacionVehiculoConductor(Integer.parseInt(id));
        c.setIdVehiculo(Integer.parseInt(vehiculoId));
        c.setActivo(Integer.parseInt(activo));

        try {
            boolean result = RelacionVehiculoConductorBD.activeOnlyOneRelation(c);
            List<RelacionVehiculoConductor> list = RelacionVehiculoConductorBD.selectAll(c);
            RelacionVehiculoConductor conductorEncontrado = RelacionVehiculoConductorBD.selectByOne(c);
            List<RelacionVehiculoConductor> respConductor = new ArrayList<>();
            if (result) {
                respConductor.add(conductorEncontrado);
                try {

                    SelectBD select = new SelectBD(request);
                    HttpSession session = request.getSession();
                    session.setAttribute("select", select);

                    jsonResponse.put("conductorVehiculo", respConductor);
                    jsonResponse.put("relacionVehiculoConductor", list);
                    jsonResponse.put("idInfo", 1);

                    if (c.getActivo() == 1) {
                        jsonResponse.put("msg", "El conductor fué activado correctamente.");
                        jsonResponse.put("activo", true);
                    } else {
                        jsonResponse.put("msg", "El conductor fué desactivado correctamente.");
                        jsonResponse.put("activo", false);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(ControladorRelacionVehiculoConductor.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    jsonResponse.put("idInfo", 2);
                    jsonResponse.put("msg", "La acción no pudo ser efectuada.");

                } catch (JSONException ex) {
                    Logger.getLogger(ControladorRelacionVehiculoConductor.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.println(jsonResponse.toString());
            out.flush();
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionVehiculoConductor.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarRelacionVehiculoConductor(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException {

        String id = request.getParameter("id");
        String vehiculoId = request.getParameter("id_vehicle");
        String estado = request.getParameter("status");

        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        RelacionVehiculoConductor c = new RelacionVehiculoConductor();
        c.setIdRelacionVehiculoConductor(Integer.parseInt(id));
        c.setIdVehiculo(Integer.parseInt(vehiculoId));
        c.setEstado(Integer.parseInt(estado));

        try {
            boolean result = RelacionVehiculoConductorBD.updateOneRelation(c);
            if (result) {
                try {
                    SelectBD select = new SelectBD(request);
                    HttpSession session = request.getSession();
                    session.setAttribute("select", select);
                    c.setEstado(1);
                    List<RelacionVehiculoConductor> list = RelacionVehiculoConductorBD.selectAll(c);
                    jsonResponse.put("relacionVehiculoConductor", list);
                    jsonResponse.put("idInfo", 1);
                    jsonResponse.put("msg", "La relación fue eliminada correctamente.");

                } catch (JSONException ex) {
                    Logger.getLogger(ControladorRelacionVehiculoConductor.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    jsonResponse.put("idInfo", 2);
                    jsonResponse.put("msg", "La relación no pudo ser eliminada.");
                } catch (JSONException ex) {
                    Logger.getLogger(ControladorRelacionVehiculoConductor.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.println(jsonResponse.toString());
            out.flush();

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionVehiculoConductor.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
