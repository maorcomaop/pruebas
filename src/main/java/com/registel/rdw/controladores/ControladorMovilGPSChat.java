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
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.GrupoMovilBD;
import com.registel.rdw.datos.MovilGPSChatBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.GrupoMovil;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.MovilGPSChat;
import com.registel.rdw.logica.Usuario;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class ControladorMovilGPSChat extends HttpServlet {

    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";
    private static final String USR_PROP = "prop";
    private static final int CHAT_ORIGIN_DRIVER = 1;
    private static final int CHAT_ORIGIN_CONTROLLER = 2;
    private static final int CHAT_MESSAGE_TYPE_CONTROLLER_MESSAGE = 100;
    private static final int CHAT_MESSAGE_TYPE_CONTROLLER_NEWS = 101;

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

        if (requestURI.endsWith("/saveMovilGPSChat")) {
            saveMovilGPSChat(request, response);
        } else if (requestURI.endsWith("/loadMovilGPSChatList")) {
            loadMovilGPSChatList(request, response);
        } else if (requestURI.endsWith("/loadMovilGPSChatOneList")) {
            loadMovilGPSChatOneList(request, response);
        } else if (requestURI.endsWith("/loadCurrentMovilGPSChat")) {
            loadCurrentMovilGPSChat(request, response);
        } else if (requestURI.endsWith("/updateAllViewed")) {
            updateAllViewed(request, response);
        }
    }

    public void loadCurrentMovilGPSChat(HttpServletRequest request,
            HttpServletResponse response) {

        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            List<MovilGPSChat> mgcList = MovilGPSChatBD.movilGPSChatLoadCurrent(request.getParameter("plate"), request.getParameter("lastCheckDate"));
            if (mgcList != null) {
                jsonResponse.put("response", "OK");
                jsonResponse.put("mgcList", mgcList);
            } else {
                jsonResponse.put("response", "ERROR");
            }
            out.println(jsonResponse.toString());
            out.flush();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ControladorMovilGPSChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadMovilGPSChatList(HttpServletRequest request,
            HttpServletResponse response) {

        try {

            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            List<MovilGPSChat> mgcList = MovilGPSChatBD.movilGPSChatList();
            jsonResponse.put("mgcList", mgcList);
            List<GrupoMovil> gmList = GrupoMovilBD.getGroupsByGPSHardware("2,5");
            jsonResponse.put("gmList", gmList);

            out.println(jsonResponse.toString());
            out.flush();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ControladorMovilGPSChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadMovilGPSChatOneList(HttpServletRequest request,
            HttpServletResponse response) {

        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            if (request.getParameter("plate").equalsIgnoreCase("controller")) {

                List<MovilGPSChat> mgcOneList = MovilGPSChatBD.movilGPSChatOneControllerList();

                jsonResponse.put("response", "OK");
                jsonResponse.put("mgcOneList", mgcOneList);
                jsonResponse.put("mgcAllViewed", true);

            } else {

                List<MovilGPSChat> mgcOneList = MovilGPSChatBD.movilGPSChatOneList(request.getParameter("plate"));
                boolean result = MovilGPSChatBD.movilGPSChatUpdateAllViewed(request.getParameter("plate"));

                jsonResponse.put("response", "OK");
                jsonResponse.put("mgcOneList", mgcOneList);
                jsonResponse.put("mgcAllViewed", result);
            }
            out.println(jsonResponse.toString());
            out.flush();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ControladorMovilGPSChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateAllViewed(HttpServletRequest request,
            HttpServletResponse response) {

        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();

            boolean result = false;
            result = MovilGPSChatBD.movilGPSChatUpdateAllViewed(request.getParameter("plate"));
            jsonResponse.put("mgcAllViewed", result);

            out.println(jsonResponse.toString());
            out.flush();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ControladorMovilGPSChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    public void saveMovilGPSChat(HttpServletRequest request,
            HttpServletResponse response) {

        try {
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            String gpsParameter = request.getParameter("gps");
            String messageParameter = request.getParameter("message");
            String plateParameter = request.getParameter("plate");
            String typeParameter = request.getParameter("type");
            String groupParameter = request.getParameter("group");

            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            MovilGPSChat m;
            if (!typeParameter.equalsIgnoreCase("multi")) {
                m = new MovilGPSChat();
                m.setFkGPS(gpsParameter);
                m.setFkGPSMessageType(CHAT_MESSAGE_TYPE_CONTROLLER_MESSAGE);
                m.setFkOrigin(CHAT_ORIGIN_CONTROLLER);
                m.setMessage(messageParameter);
                m.setPlate(plateParameter);
                m.setNumNews(0);

                MovilGPSChat mResponse = MovilGPSChatBD.movilGPSChatSave(m);

                if (mResponse != null) {
                    List<MovilGPSChat> mgcOne = new ArrayList<>();
                    mgcOne.add(mResponse);
                    jsonResponse.put("response", "OK");
                    jsonResponse.put("mgcOne", mgcOne);
                } else {
                    jsonResponse.put("response", "ERROR");
                }
            } else {

                MovilGPSChat mResponseAux = new MovilGPSChat();

                MovilGPSChat mResponse = new MovilGPSChat();
                mResponse.setFkGPS("controller");
                mResponse.setFkGPSMessageType(CHAT_MESSAGE_TYPE_CONTROLLER_NEWS);
                mResponse.setFkOrigin(CHAT_ORIGIN_CONTROLLER);
                mResponse.setMessage(messageParameter);
                mResponse.setPlate("controller");
                mResponse.setInternalNum("controller");

                ArrayList<Movil> list = MovilBD.searchGPSVehicleAll("2,5", plateParameter, groupParameter);
                Integer maxNews = MovilGPSChatBD.maxNumNewsChat();
                if (maxNews != null) {
                    maxNews++;
                }
                int success = 0;
                if (list != null && maxNews != null) {

                    for (Movil ml : list) {
                        m = new MovilGPSChat();
                        m.setFkGPS(ml.getFkGps());
                        m.setFkGPSMessageType(CHAT_MESSAGE_TYPE_CONTROLLER_NEWS);
                        m.setFkOrigin(CHAT_ORIGIN_CONTROLLER);
                        m.setMessage(messageParameter);
                        m.setPlate(ml.getPlaca());
                        m.setNumNews(maxNews);

                        mResponseAux = MovilGPSChatBD.movilGPSChatSave(m);
                        if (mResponseAux != null) {
                            success++;
                        }
                    }
                    if (success == list.size()) {
                        jsonResponse.put("response", "OK");
                        List<MovilGPSChat> mgcOne = new ArrayList<>();
                        mResponse.setCreationDate(mResponseAux.getCreationDate());
                        mgcOne.add(mResponse);
                        jsonResponse.put("mgcOne", mgcOne);
                    } else {
                        jsonResponse.put("response", "ERROR - Creando mensajes");
                    }

                } else {
                    jsonResponse.put("response", "ERROR - Obteniendo información de los vehículos");
                }
            }
            out.println(jsonResponse.toString());
            out.flush();
        } catch (IOException | SQLException | JSONException ex) {
            Logger.getLogger(ControladorMovilGPSChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
