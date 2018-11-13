/**
 * Clase controlador Conductor
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
import com.registel.rdw.datos.GPSGrupoCommandoBD;
import com.registel.rdw.datos.PilaConexiones;
import com.registel.rdw.datos.UtilBD;
import com.registel.rdw.logica.GpsEnvioComando;
import com.registel.rdw.logica.GpsGrupoComando;
import com.registel.rdw.logica.GpsPaqueteComando;
import com.registel.rdw.logica.MovilGPSChat;
import com.registel.rdw.logica.Usuario;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ControladorGPSGrupoComandos extends HttpServlet {

    public static final HashMap<String, Integer> GPS_COMMANDS_ES = new HashMap<>();
    public static final HashMap<String, Integer> GPS_BRAND = new HashMap<>();

    public static SimpleDateFormat formatDBDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {

        GPS_COMMANDS_ES.put("GEO_RUTA", 1);
        GPS_COMMANDS_ES.put("GEO_DISPONIBLE", 2);
        GPS_COMMANDS_ES.put("SERIAL_DESPACHO", 3);
        GPS_COMMANDS_ES.put("SERIAL_CHAT", 4);
        GPS_COMMANDS_ES.put("MDFCR_REP_TIEMPO_MOVIL_ON", 5);
        GPS_COMMANDS_ES.put("MDFCR_REP_TIEMPO_MOVIL_OFF", 6);
        GPS_COMMANDS_ES.put("MDFCR_REP_DISTANCIA", 7);
        GPS_COMMANDS_ES.put("MOVIL_APAGADO", 8);
        GPS_COMMANDS_ES.put("MOVIL_ENCENDIDO", 9);
        GPS_COMMANDS_ES.put("MDFCR_VELOCIDAD_LIMITE", 10);
        GPS_COMMANDS_ES.put("SLCTD_ESTADO", 11);
        GPS_COMMANDS_ES.put("RESET_GPS", 12);
        GPS_COMMANDS_ES.put("SLCTD_CONTADOR", 13);
        GPS_COMMANDS_ES.put("SLCTD_ESTADO_SENSORES", 14);
        GPS_COMMANDS_ES.put("SLCTD_ESTADO_VOLTAJES", 15);
        GPS_COMMANDS_ES.put("SLCTD_PARAM_CONTADOR", 16);
        GPS_COMMANDS_ES.put("MDFCR_CONTADOR", 17);
        GPS_COMMANDS_ES.put("MDFCR_PARAM_CONTADOR", 18);
        GPS_COMMANDS_ES.put("RESET_CONTADOR", 19);
        GPS_COMMANDS_ES.put("MDFCR_REP_TIEMPO_DISTANCIA", 20);
        GPS_COMMANDS_ES.put("MDFCR_PARAM_SERVICIO", 21);
        GPS_COMMANDS_ES.put("MDFCR_CONEXION_IP", 22);
        GPS_COMMANDS_ES.put("MDFCR_CONEXION_PUERTO", 23);
        GPS_COMMANDS_ES.put("MDFCR_IP_DOMINIO_PARTE_1", 24);
        GPS_COMMANDS_ES.put("MDFCR_IP_DOMINIO_PARTE_2", 25);
        GPS_COMMANDS_ES.put("MDFCR_PARAM_RED", 26);
        GPS_COMMANDS_ES.put("MDFCR_CONEXION_APN", 27);
        GPS_COMMANDS_ES.put("MDFCR_CONEXION_APN_PARTE_1", 28);
        GPS_COMMANDS_ES.put("MDFCR_CONEXION_APN_PARTE_2", 29);
        GPS_COMMANDS_ES.put("MDFCR_CONEXION_APN_PARTE_3", 30);
        GPS_COMMANDS_ES.put("MDFCR_IP_DOMINIO_PARTE_3", 31);

        GPS_BRAND.put("CELLOCATOR", 1);
        GPS_BRAND.put("SUNTECH", 2);

    }

    //private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
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

        if (requestURI.endsWith("/saveGPSCommandGroups")) {
            saveGPSCommand(request, response);
        }
        if (requestURI.endsWith("/listGPSCommandGroups")) {
            listGPSCommandGroups(request, response);
        }
        if (requestURI.endsWith("/loadCurrentCommandStatus")) {
            loadCurrentCommandStatus(request, response);
        }
        if (requestURI.endsWith("/resetCommandGroup")) {
            resetCommandGroup(request, response);
        }
        if (requestURI.endsWith("/loadValueCommandSaved")) {
            loadValueCommandSaved(request, response);
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    public void loadValueCommandSaved(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            String fkGpsCommandType = request.getParameter("fkGpsCommandType");
            String fkGps = request.getParameter("fkGps");
            String plate = request.getParameter("plate");
            List<GpsEnvioComando> listGpsEnvioComando = GPSGrupoCommandoBD.loadValueCommandSaved(fkGpsCommandType, fkGps, plate);
            if (listGpsEnvioComando != null) {
                jsonResponse.put("response", "OK");
                jsonResponse.put("list", listGpsEnvioComando);
            } else {
                jsonResponse.put("response", "ERROR");
            }
            out.println(jsonResponse.toString());
            out.flush();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(ControladorGPSGrupoComandos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetCommandGroup(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            String pkGpsCommandGroup = request.getParameter("pkGpsCommandGroup");
            int seconds = Integer.parseInt(request.getParameter("seconds"));
            boolean resp = GPSGrupoCommandoBD.resetGpsCommandGroups(pkGpsCommandGroup);
            if (resp) {

                Date lastCheckDate = new Date(((new Date()).getTime() - seconds * 1000));

                List<GpsEnvioComando> listGpsCommandSend = GPSGrupoCommandoBD.listGpsCommandSendCurrentStatus(formatDBDate.format(lastCheckDate));
                List<GpsGrupoComando> listGpsCommandGroup = GPSGrupoCommandoBD.listGpsCommandGroupCurrentStatus(formatDBDate.format(lastCheckDate));

                if (listGpsCommandSend != null && listGpsCommandGroup != null) {
                    jsonResponse.put("response", "OK");
                    jsonResponse.put("listCommand", listGpsCommandSend);
                    jsonResponse.put("listGroup", listGpsCommandGroup);
                } else {
                    jsonResponse.put("response", "ERROR");
                }
            } else {
                jsonResponse.put("response", "ERROR");
            }
            out.println(jsonResponse.toString());
            out.flush();
        } catch (JSONException | IOException | SQLException ex) {
            Logger.getLogger(ControladorGPSGrupoComandos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    public void loadCurrentCommandStatus(HttpServletRequest request,
            HttpServletResponse response) {

        try {

            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            int seconds = Integer.parseInt(request.getParameter("seconds"));

            Date lastCheckDate = new Date(((new Date()).getTime() - seconds * 1000));

            List<GpsEnvioComando> listGpsCommandSend = GPSGrupoCommandoBD.listGpsCommandSendCurrentStatus(formatDBDate.format(lastCheckDate));
            List<GpsGrupoComando> listGpsCommandGroup = GPSGrupoCommandoBD.listGpsCommandGroupCurrentStatus(formatDBDate.format(lastCheckDate));

            if (listGpsCommandSend != null && listGpsCommandGroup != null) {
                jsonResponse.put("response", "OK");
                jsonResponse.put("listCommand", listGpsCommandSend);
                jsonResponse.put("listGroup", listGpsCommandGroup);
            } else {
                jsonResponse.put("response", "ERROR");
            }

            out.println(jsonResponse.toString());
            out.flush();

        } catch (JSONException | IOException ex) {
            Logger.getLogger(ControladorGPSGrupoComandos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param request
     * @param response
     */
    public void listGPSCommandGroups(HttpServletRequest request,
            HttpServletResponse response) {

        try {

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            HttpSession session = request.getSession();
            Usuario user = (Usuario) session.getAttribute("login");

            List<GpsPaqueteComando> listGpsPaqueteComando = GPSGrupoCommandoBD.listGpsCommandPackageInfo(user);

            if (listGpsPaqueteComando != null) {
                jsonResponse.put("response", "OK");
                jsonResponse.put("list", listGpsPaqueteComando);
            } else {
                jsonResponse.put("response", "ERROR");
            }

            out.println(jsonResponse.toString());
            out.flush();

        } catch (JSONException | IOException ex) {
            Logger.getLogger(ControladorGPSGrupoComandos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param request
     * @param response
     */
    public void saveGPSCommand(HttpServletRequest request,
            HttpServletResponse response) {

        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            HttpSession session = request.getSession();
            Usuario user = (Usuario) session.getAttribute("login");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            String[] jsonData = request.getParameterValues("data");
            JSONObject data = new JSONObject(jsonData[0]);

            GpsPaqueteComando gpsPaqueteComando = new GpsPaqueteComando();

            int tries = data.getInt("tries");
            boolean isMainResetEnd = data.getBoolean("isMainResetEnd");

            gpsPaqueteComando.setCode(data.getString("code"));
            gpsPaqueteComando.setFkGPSCommandPackageType(5);
            gpsPaqueteComando.setStateInGPSs(1);
            gpsPaqueteComando.setStatus(true);
            gpsPaqueteComando.setCreationDate(new Date());
            gpsPaqueteComando.setTries(tries);
            gpsPaqueteComando.setIsMainResetEnd(isMainResetEnd);
            gpsPaqueteComando.setFkUsuario(user.getId());

            JSONArray idPlatesArray = data.getJSONArray("plates");
            JSONArray idGpsArray = data.getJSONArray("gps");
            JSONArray idGpsBrandArray = data.getJSONArray("gpsBrand");

            JSONArray idRouteArray = data.getJSONArray("routeId");
            JSONArray groupIdArray = data.getJSONArray("groupId");

            int numCommands = 0;
            JSONArray simpleCommands = data.getJSONArray("simpleCmmnds");

            List<GpsGrupoComando> gpsGpsGrupoComandoList = new ArrayList<>();
            List<GpsEnvioComando> gpsEnvioComandoList;
            GpsEnvioComando gpsEnvioComando;
            GpsGrupoComando gpsGrupoComando;
            JSONObject params;
            String plate;
            String gps;
            Integer gpsBrand;

            for (int i = 0; i < idPlatesArray.length(); i++) {
                if (idGpsArray.getString(i) != null) {
                    plate = idPlatesArray.getString(i);
                    gps = idGpsArray.getString(i);
                    gpsBrand = Integer.parseInt(idGpsBrandArray.getString(i));

                    gpsGrupoComando = new GpsGrupoComando();
                    gpsGrupoComando.setFkGPS(gps);
                    gpsGrupoComando.setPlate(plate);
                    gpsGrupoComando.setFkStateInGps(1);
                    gpsGrupoComando.setFkCommandGroupType(5);
                    gpsGrupoComando.setNeedReset(isMainResetEnd);
                    gpsGrupoComando.setStatus(true);
                    gpsGrupoComando.setMaxPostponed(tries);
                    gpsGrupoComando.setNumPostponed(0);
                    gpsGrupoComando.getGpsCommandSendList();

                    numCommands = 0;
                    JSONObject obj;
                    gpsEnvioComandoList = new ArrayList<>();

                    for (int j = 0; j < simpleCommands.length(); j++) {
                        obj = (JSONObject) simpleCommands.get(j);

                        if (!(Objects.equals(gpsBrand, GPS_BRAND.get("SUNTECH")) && (obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_REP_TIEMPO_MOVIL_OFF")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_REP_TIEMPO_MOVIL_ON")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_REP_DISTANCIA")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("GEO_DISPONIBLE")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_VELOCIDAD_LIMITE")
//                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_CONEXION_IP")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_CONEXION_PUERTO")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_CONEXION_APN_PART_1")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_CONEXION_APN_PART_2")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_CONEXION_APN_PART_3")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_IP_DOMINIO_PARTE_1")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_IP_DOMINIO_PARTE_2")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_IP_DOMINIO_PARTE_3")))
                                && !(Objects.equals(gpsBrand, GPS_BRAND.get("CELLOCATOR")) && (obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_REP_TIEMPO_DISTANCIA")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_PARAM_SERVICIO")
                                || obj.getInt("type") == GPS_COMMANDS_ES.get("MDFCR_PARAM_RED")))) {

                            gpsEnvioComando = new GpsEnvioComando();
                            gpsEnvioComando.setFkCommandKey(obj.getInt("type"));
                            gpsEnvioComando.setFkGps(gps);
                            gpsEnvioComando.setIdInGroup(numCommands);
                            gpsEnvioComando.setPlate(plate);
                            gpsEnvioComando.setValiddDate(new Date((new Date()).getTime() + 86400000));
                            if (obj.get("params") instanceof JSONObject) {
                                params = (JSONObject) obj.get("params");
                                gpsEnvioComando.setParams(params.toString(2));
                            } else {
                                gpsEnvioComando.setParams("");
                            }
                            numCommands++;
                            gpsEnvioComandoList.add(gpsEnvioComando);
                        }
                    }

                    gpsGrupoComando.setNumCommands(numCommands);
                    gpsGrupoComando.setGpsCommandSendList(gpsEnvioComandoList);
                    gpsGpsGrupoComandoList.add(gpsGrupoComando);

                }
            }
            gpsPaqueteComando.setGpsCommandGroupList(gpsGpsGrupoComandoList);

            GpsPaqueteComando mResponse = GPSGrupoCommandoBD.saveGpsCommandGroups(gpsPaqueteComando);

            if (mResponse != null) {
                List<GpsPaqueteComando> list = new ArrayList<>();
                list.add(mResponse);
                jsonResponse.put("response", "OK");
                jsonResponse.put("list", list);
            } else {
                jsonResponse.put("response", "ERROR");
            }

            out.println(jsonResponse.toString());
            out.flush();
        } catch (IOException | SQLException | JSONException ex) {
            Logger.getLogger(ControladorGPSGrupoComandos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
