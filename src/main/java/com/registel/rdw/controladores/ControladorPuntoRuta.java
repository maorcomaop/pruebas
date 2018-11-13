/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.DespachoBD;
import com.registel.rdw.datos.PuntoRutaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Despacho;
import com.registel.rdw.logica.PuntoRuta;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorPuntoRuta extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
//        String url = "/app/usuarios/editaPuntosRuta_Dph.jsp";
//        getServletContext()
//                .getRequestDispatcher(url)
//                .forward(request, response);
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
        
        if (requestURI.endsWith("/guardarPuntosRuta")) {
            url = guardarPuntosRuta(request, response);
        } else if (requestURI.endsWith("/actualizarPuntosRuta")) {
            url = actualizarPuntosRuta(request, response);
        } 
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    } 
    
    // Procesa solicitud de creacion de nuevos puntos ruta.
    // (El proceso se resume en inactivar puntos ruta previamente establecidos
    //  e insertar nuevos).
    public String guardarPuntosRuta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String ruta   = request.getParameter("ruta");
        String puntos = request.getParameter("puntos");
        
        //String sql = formatPuntosRutaSQL_INS(ruta, puntos);        
        // Extrae instrucciones de insercion para nuevos puntos ruta
        String sql = formatSQL_INS(ruta, puntos);        
        
        // Almacena nuevo conjunto de puntos ruta
        if (sql != null && PuntoRutaBD.insertAndDelete(ruta, sql)) {
            String msg = "* Punto(s) de ruta asignados correctamente."
                       + " <strong>Ahora establece los valores de cada punto y/o asigna los puntos al despacho.</strong>";
            request.setAttribute("msg", msg);
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
            request.setAttribute("ruta_editada", ruta);
            
            return "/app/usuarios/editaPuntosRuta_Dph.jsp";
            
        } else {
            request.setAttribute("msg", "* Puntos de ruta no registrados.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/asignaPuntosRuta.jsp";
    }
    
    // Procesa solicitud de actualizacion de puntos ruta, puntos despacho
    // o ambos, segun opcion especificada en interfaz del modulo ruta.
    public String actualizarPuntosRuta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idRuta     = request.getParameter("idRuta");
        String idDespacho = request.getParameter("idDespacho");
        String puntosRuta = request.getParameter("listaPuntosRuta");
        String puntosDph  = request.getParameter("listaDespacho");
        String actRuta    = request.getParameter("actualizarRuta");
        
        HttpSession session = request.getSession();
        
        if (idDespacho != "") {
            // Asigna informacion de despacho
            Despacho dph = datosDespacho(puntosDph);
            // Extrae listado de instrucciones
            ArrayList<String> listaSQL = formatPuntosRutaSQL_UPD_dph(idRuta, puntosDph);            
            boolean rdph = true;
            
            if (dph != null) {
                dph.setId(Restriction.getNumber(idDespacho));    
                
                // Actualiza despacho 
                if (DespachoBD.updateFromPR(dph)) {
                    request.setAttribute("msg", "* Puntos de despacho actualizados correctamente.");
                    request.setAttribute("msgType", "alert alert-success");
                    rdph = true;

                    SelectBD select = new SelectBD(request);                            
                    session.setAttribute("select", select);           
                    request.setAttribute("ruta_editada", idRuta);
                } else {
                    request.setAttribute("msg", "* Puntos de despacho no actualizados.");
                    request.setAttribute("msgType", "alert alert-danger");
                    rdph = false;
                }

                // Actualiza puntos ruta desde despacho
                if (rdph && actRuta.compareTo("1") == 0) {
                    if (listaSQL != null && PuntoRutaBD.update(listaSQL)) {
                        request.setAttribute("msg", "* Puntos de despacho y ruta actualizados correctamente.");
                        request.setAttribute("msgType", "alert alert-success");
                        
                        SelectBD select = new SelectBD(request);                            
                        session.setAttribute("select", select);           
                        request.setAttribute("ruta_editada", idRuta);
                    } else {
                        request.setAttribute("msg", "* Puntos de despacho actualizados. Ruta no actualizada.");
                        request.setAttribute("msgType", "alert alert-danger");
                    }
                }
            }
            return "/app/usuarios/editaPuntosRuta_Dph.jsp";
        }
        
        ArrayList<String> listaSQL = formatPuntosRutaSQL_UPD(puntosRuta);
        
        // Actualiza unicamente puntos ruta
        if (listaSQL != null && PuntoRutaBD.update(listaSQL)) {
            request.setAttribute("msg", "* Puntos de ruta actualizados correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            
            SelectBD select = new SelectBD(request);                        
            session.setAttribute("select", select);            
            request.setAttribute("ruta_editada", idRuta);
        } else {
            request.setAttribute("msg", "* Puntos de ruta no actualizados.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/editaPuntosRuta_Dph.jsp";
    }
        
    // Genera instruccion SQL INSERT para almacenar
    // puntos de ruta en el orden especificado.     
    public String formatPuntosRutaSQL_INS (String idRuta, String puntos) {
                
        if (puntos != null && puntos != "") {
            String listaPuntos[] = puntos.split("&");            
            
            String sql = "INSERT INTO tbl_ruta_punto (FK_PUNTO, FK_RUTA, PROMEDIO_MINUTOS,"
                        + " HOLGURA_MINUTOS, VALOR_PUNTO, FK_BASE, TIPO, ESTADO, MODIFICACION_LOCAL) VALUES ";
            
            // Formato entrada
            // puntos := &* idPunto, codPunto, nombre, lat, lon
            //        := &* BS|BL, idBase, nombre, lat, lon            
    
            String str = "";            
            for (int i = 0; i < listaPuntos.length; i++) {
                String infoPunto[] = listaPuntos[i].split(","); 
                String idPunto = infoPunto[0];
                String idBase  = infoPunto[1];
                
                if (idPunto.equalsIgnoreCase("BS")) {
                    if (str == "") { str = "(101," + idRuta + ",0,0,0," + idBase + ",1,1,1)"; }
                    else           { str += ",(101," + idRuta + ",0,0,0," + idBase + ",1,1,1)"; }
                } else if (idPunto.equalsIgnoreCase("BL")) {
                    if (str == "") { str = "(100," + idRuta + ",0,0,0," + idBase + ",3,1,1)"; }
                    else           { str += ",(100," + idRuta + ",0,0,0," + idBase + ",3,1,1)"; }
                } else {                
                    if (str == "") { str = "(" + idPunto + "," + idRuta + ",0,0,0,NULL,2,1,1)"; }
                    else           { str += ",(" + idPunto + "," + idRuta + ",0,0,0,NULL,2,1,1)"; }                                                   
                }                
            }    
            sql += str;                        
            
            return sql;
        }
        return null;
    }
    
    // Inicia generacion de instruccion SQL INSERT para almacenar puntos de ruta
    // en el orden especificado, tomando valores existentes (tiempos) si hay 
    // una configuracion de ruta previa. (Usado actualmente).
    public String formatSQL_INS(String sidRuta, String puntos) {
        
        int idRuta = Restriction.getNumber(sidRuta);
        ArrayList<PuntoRuta> lst_pr_nva = String2PuntoRuta(sidRuta, puntos);
        ArrayList<PuntoRuta> lst_pr_alm = PuntoRutaBD.selectByRuta_edit(idRuta);
        sincronizarDatos(lst_pr_alm, lst_pr_nva);
        
        return formatPuntosRutaSQL_INS(lst_pr_nva);        
    }
    
    // Genera listado de instrucciones SQL INSERT a partir
    // de un listado de objetos de clase PuntoRuta.
    public String formatPuntosRutaSQL_INS (ArrayList<PuntoRuta> lst_pr) {
        
        String sql = "INSERT INTO tbl_ruta_punto ("
                        + " FK_PUNTO,"
                        + " FK_RUTA,"
                        + " PROMEDIO_MINUTOS,"
                        + " HOLGURA_MINUTOS,"
                        + " VALOR_PUNTO,"
                        + " FK_BASE,"
                        + " TIPO,"
                        + " ESTADO,"
                        + " MODIFICACION_LOCAL) VALUES ";
        
        String s = ","; String str = "";
        
        if (lst_pr != null && lst_pr.size() > 0) {
            
            for (int i = 0; i < lst_pr.size(); i++) {
                PuntoRuta pr = lst_pr.get(i);
                String base = (pr.getIdPunto() == 100 || 
                               pr.getIdPunto() == 101) ? "" + pr.getIdBase() : "NULL";
                
                if (str != "") 
                    str += ",";

                str += "(" +
                    pr.getIdPunto()         + s +
                    pr.getIdRuta()          + s + 
                    pr.getPromedioMinutos() + s +
                    pr.getHolguraMinutos()  + s +
                    pr.getValorPunto()      + s +
                    base                    + s +
                    pr.getTipo() + ",1,1)";

            }
            sql += str;
            
            return sql;
        } 
        return null;
    }
    
    // Convierte cadena formateada con listado de puntos de una ruta
    // a listado de objetos de clase PuntoRuta.
    public ArrayList<PuntoRuta> String2PuntoRuta(String sidRuta, String puntos) {
        
        if (puntos != null && puntos != "") {
            String listaPuntos[] = puntos.split("&");
            
            // Formato entrada
            // puntos := &* idPunto, codPunto, nombre, lat, lon
            //        := &* BS|BL, idBase, nombre, lat, lon                        
            
            ArrayList<PuntoRuta> lst_pr = new ArrayList<PuntoRuta>();
            int idRuta = Restriction.getNumber(sidRuta);
            
            for (int i = 0; i < listaPuntos.length; i++) {
                String infoPunto[] = listaPuntos[i].split(",");
                String sidPunto = infoPunto[0];
                String sidBase  = infoPunto[1];
                
                PuntoRuta pr = new PuntoRuta();
                
                if (sidPunto.equalsIgnoreCase("BS")) {
                    pr.setIdBase(Restriction.getNumber(sidBase));
                    pr.setIdPunto(101);
                    pr.setTipo(1);
                } else if (sidPunto.equalsIgnoreCase("BL")) {
                    pr.setIdBase(Restriction.getNumber(sidBase));
                    pr.setIdPunto(100);
                    pr.setTipo(3);
                } else {
                    pr.setIdPunto(Restriction.getNumber(sidPunto));
                    pr.setTipo(2);
                }
                pr.setIdRuta(idRuta);
                lst_pr.add(pr);
            }
            return lst_pr;
        }
        return null;
    }
    
    // Traslada valores establecidos en los puntos de una ruta
    // al nuevo listado de puntos a almacenar.
    public void sincronizarDatos(ArrayList<PuntoRuta> lst_alm,
                                 ArrayList<PuntoRuta> lst_nva) {
        
        for (int i = 0; i < lst_alm.size(); i++) {
            PuntoRuta pr_alm = lst_alm.get(i);
            
            for (int j = 0; j < lst_nva.size(); j++) {
                PuntoRuta pr_nvo = lst_nva.get(j);
                
                if (pr_alm.getIdRuta()  == pr_nvo.getIdRuta()  &&
                    pr_alm.getIdPunto() == pr_nvo.getIdPunto() &&
                   !pr_nvo.puntoVisto()) {
                    
                    pr_nvo.setPromedioMinutos(pr_alm.getPromedioMinutos());
                    pr_nvo.setHolguraMinutos(pr_alm.getHolguraMinutos());
                    pr_nvo.setValorPunto(pr_alm.getValorPunto());
                    pr_nvo.setTipo(pr_alm.getTipo());
                    pr_nvo.setPuntoVisto(true);
                    break;
                }
            }
        }
    }      
        
    // Genera listado de instruccion SQL UPDATE para actualizar       
    // puntos de una ruta en modulo ruta.     
    public ArrayList<String> formatPuntosRutaSQL_UPD (String puntos) {
        
        // Formato entrada
        // puntos := &* id, idRuta, idPunto, codigoPunto, promMin, holguraMin, valorPunto, tipo                
        
        ArrayList<String> listaSQL = null;
        if (puntos != null && puntos != "") {
            String listaPuntos[] = puntos.split("&");            
            
            listaSQL = new ArrayList<String>(); String s = ",";
            for (int i = 0; i < listaPuntos.length; i++) {
                String infoPuntos[] = listaPuntos[i].split(",");
                
                String sql = "UPDATE tbl_ruta_punto SET"                        
                        + " PROMEDIO_MINUTOS = " +   infoPuntos[4] + s
                        + " HOLGURA_MINUTOS = " +    infoPuntos[5] + s
                        + " VALOR_PUNTO = " +        infoPuntos[6] + s
                        + " TIPO = " +               infoPuntos[7] 
                        + " WHERE PK_RUTA_PUNTO = " +    infoPuntos[0]
                        + "  AND FK_RUTA = " +           infoPuntos[1]
                        + "  AND FK_PUNTO = " +          infoPuntos[2];
                listaSQL.add(sql);
            }
            return listaSQL;
        }
        return null;
    }
        
    // Genera listado de instruccion SQL UPDATE para actualizar puntos ruta a partir
    // de vista de configuracion-despacho en modulo ruta.    
    public ArrayList<String> formatPuntosRutaSQL_UPD_dph (String idRuta, String puntosDph) {
        
        // Formato entrada
        // puntos := &* idDespacho, idPuntoRuta, idPunto, codigoPunto, 1-0 enDespacho, promMin, holguraMin, tiempoValle, tiempoPico
        
        ArrayList<String> listaSQL = null; String s = ",";
        if (puntosDph != null && puntosDph != "") {
            String listaPuntos[] = puntosDph.split("&");

            listaSQL = new ArrayList<String>();
            for (int i = 0; i < listaPuntos.length; i++) {
                String infoPuntos[] = listaPuntos[i].split(",");

                String sql = "UPDATE tbl_ruta_punto SET"
                            + " PROMEDIO_MINUTOS = "    + infoPuntos[5] + s
                            + " HOLGURA_MINUTOS = "     + infoPuntos[6]
                            + " WHERE PK_RUTA_PUNTO = " + infoPuntos[1]
                            + "  AND FK_RUTA = "    + idRuta
                            + "  AND FK_PUNTO = "   + infoPuntos[2]
                            + "  AND ESTADO = 1";                
                listaSQL.add(sql);
            }
            return listaSQL;
        }
        return null;
    }
    
    // Genera listado de instruccion SQL UPDATE para actualizar puntos despacho
    // a partir de vista de configuracion-despacho en el modulo ruta.
    public Despacho datosDespacho(String puntosDph) {
        
        // Formato entrada
        // puntos := &* idDespacho, idPuntoRuta, idPunto, codigoPunto, 1-0 enDespacho, promMin, holguraMin, tiempoValle, tiempoPico
        
        String listaPuntos[] = puntosDph.split("&");
        String idpto, codp, prom, holg, valle, pico;
        idpto = codp = prom = holg = valle = pico = "";
        
        for (int i = 0; i < listaPuntos.length; i++) {
            String info[] = listaPuntos[i].split(",");
            String enDespacho = info[4];
            if (enDespacho.compareTo("1") == 0) {
                idpto += (idpto == "") ? info[2] : "," + info[2];
                codp  += (codp  == "") ? info[3] : "," + info[3];
                prom  += (prom  == "") ? info[5] : "," + info[5];
                holg  += (holg  == "") ? info[6] : "," + info[6];
                valle += (valle == "") ? info[7] : "," + info[7];
                pico  += (pico  == "") ? info[8] : "," + info[8];
            }
        }
        if (idpto == "" || codp == "" || prom == "" || holg == "" || valle == "" || pico == "")
            return null;
        
        Despacho dph = new Despacho();
        dph.setIdPuntosRuta(idpto);
        dph.setPuntosRuta(codp);
        dph.setTiempoReferencia(prom);
        dph.setTiempoHolgura(holg);
        dph.setTiempoLlegadaValle(valle);
        dph.setTiempoLlegadaPico(pico);
        
        return dph;
    }            
}       
