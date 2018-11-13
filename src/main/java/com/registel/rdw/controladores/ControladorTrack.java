/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.DataGPSBD;
import com.registel.rdw.logica.CommandGPS;
import com.registel.rdw.logica.DataGPS;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
public class ControladorTrack extends HttpServlet {

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

        boolean jsonResponseType = false;

        String requestURI = request.getRequestURI();

        String url = "/";
        if (requestURI.endsWith("/trackList")) {

            jsonResponseType = true;
            trackList(request, response);
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            try {
                DataGPSBD dataGPS = new DataGPSBD();
                List<DataGPS> dataGPSList = dataGPS.selectAll();

                if (dataGPSList == null) {
                    jsonResponse.put("success", false);
                } else {
                    jsonResponse.put("success", true);
                    jsonResponse.put("response", dataGPSList);
                }

            } catch (JSONException ex) {
                Logger.getLogger(ControladorLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println(jsonResponse.toString());
            out.flush();

        } else if (requestURI.endsWith("/trackCommandSend")) {

            jsonResponseType = true;

            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            try {
                int result = trackCommandSave(request, response);
                if (result == 0) {
                    jsonResponse.put("success", false);
                } else {
                    jsonResponse.put("success", true);
                }

            } catch (JSONException ex) {
                Logger.getLogger(ControladorTrack.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println(jsonResponse.toString());
            out.flush();
        }
    }

    public void trackList(HttpServletRequest request,
            HttpServletResponse response) {

    }

    public int trackCommandSave(HttpServletRequest request,
            HttpServletResponse response) throws JSONException {

        String[] myJsonData = request.getParameterValues("json");
        JSONObject data = new JSONObject(myJsonData[0]);

        CommandGPS commandGPS = new CommandGPS();
        commandGPS.setCommandKey(data.getString("commandKey"));
        commandGPS.setSent(false);
        commandGPS.setReplied(false);

        DataGPSBD dataGPSBD = new DataGPSBD();
        return dataGPSBD.saveCommand(commandGPS);

    }
}
