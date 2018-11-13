/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.BaseBD;
import com.registel.rdw.datos.ConsultaExterna;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.PuntoBD;
import com.registel.rdw.datos.PuntoRelacionadoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Base;
import com.registel.rdw.logica.Punto;
import com.registel.rdw.utils.Coordenadas;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorPunto extends HttpServlet {    
    
    private static final int LIMITE_PUNTOS_IMPORTAR = 2048;
    
    private static SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
       
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
        
        if (requestURI.endsWith("/guardarPunto")) {
            url = guardarPunto(request, response);
        } else if (requestURI.endsWith("/eliminarPunto")) {
            url = eliminarPunto(request, response);
        } else if (requestURI.endsWith("/eliminarPuntoBase")) {
            url = eliminarPuntoBase(request, response);
        } else if (requestURI.endsWith("/guardarListaPuntos")) {        
            url = guardarListaPuntos(request, response);
        } else if (requestURI.endsWith("/importarPuntosKML")) {
            url = importarPuntosKML(request, response);
        } else if (requestURI.endsWith("/guardarListaPuntosKML")) {
            url = guardarListaPuntosKML(request, response);
        } else if (requestURI.endsWith("/repararCoordenadas")) {
            url = repararCoordenadas(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para creacion de punto de control.    
    // Comprueba el tipo de punto y tipo de accion, para
    // redireccionar a los metodos correspondientes.
    // Delega proceso de creacion.
    public String guardarPunto (HttpServletRequest request,
            HttpServletResponse response) {
        
        String tipoPunto = request.getParameter("rtipoPunto");
        String orden     = request.getParameter("orden");
        
        if (tipoPunto.equalsIgnoreCase("base")) {
            ControladorBase cb = new ControladorBase();
            if      (orden.equalsIgnoreCase("ins")) { return cb.guardarBase(request, response); }
            else if (orden.equalsIgnoreCase("act")) { return cb.actualizarBase(request, response); } 
            else return "/app/usuarios/nuevoPunto.jsp";
        } else {
            if      (orden.equalsIgnoreCase("act"))  { return actualizarPunto(request, response); }
            else if (!orden.equalsIgnoreCase("ins")) { return "/app/usuarios/nuevoPunto.jsp"; }
        }
        
        String nombre   = request.getParameter("nombre");        
        String codPto   = request.getParameter("codPtoBase");
        String latitud  = request.getParameter("latitud");
        String longitud = request.getParameter("longitud");
        String dirLat   = request.getParameter("dirLatitud");
        String dirLon   = request.getParameter("dirLongitud");
        String radio    = request.getParameter("radio");
        String direc    = request.getParameter("direccion");
        
        Punto p = new Punto();
        p.setNombre(nombre);
        p.setDescripcion(null);
        p.setCodigoPunto(Integer.parseInt(codPto));
        p.setLatitud(latitud);
        p.setLongitud(longitud);
        p.setDireccionLatitud(dirLat);
        p.setDireccionLongitud(dirLon);
        p.setRadio(Integer.parseInt(radio));
        p.setDireccion(Integer.parseInt(direc));
        
        p.setLatitudWeb(latitud);
        p.setLongitudWeb(longitud);
        
        try {
            // Inserta nuevo punto
            if (PuntoBD.insert(p)) {
                request.setAttribute("msg", "* Punto registrado correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                SelectBD select = new SelectBD(request);
                
                HttpSession session = request.getSession();
                session.setAttribute("select", select);                
            } else {
                request.setAttribute("msg", "* Punto no registrado.");
                request.setAttribute("msgType", "alert alert-danger");
            }
        } catch (ExisteRegistroBD e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("msgType", "alert alert-info");
        }
        
        actualizarPuntosRecientes(request);
        return "/app/usuarios/nuevoPunto.jsp";
    }
    
    // Procesa solicitud para actualizacion de punto de control.
    // Delega proceso de actualizacion.
    public String actualizarPunto (HttpServletRequest request,
            HttpServletResponse response) {
            
        String nombre   = request.getParameter("nombre");        
        String codPto   = request.getParameter("codPtoBase");
        String latitud  = request.getParameter("latitud");
        String longitud = request.getParameter("longitud");
        String dirLat   = request.getParameter("dirLatitud");
        String dirLon   = request.getParameter("dirLongitud");
        String radio    = request.getParameter("radio");
        String direc    = request.getParameter("direccion");
        
        Punto p = new Punto();
        p.setNombre(nombre);
        p.setDescripcion(null);
        p.setCodigoPunto(Integer.parseInt(codPto));
        p.setLatitud(latitud);
        p.setLongitud(longitud);
        p.setDireccionLatitud(dirLat);
        p.setDireccionLongitud(dirLon);
        p.setRadio(Integer.parseInt(radio));
        p.setDireccion(Integer.parseInt(direc));        
        
        p.setLatitudWeb(latitud);
        p.setLongitudWeb(longitud);
        
        // Actualiza punto
        if (PuntoBD.update(p)) {
            request.setAttribute("msg", "* Punto actualizado correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);

            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* Punto no actualizado.");
            request.setAttribute("msgType", "alert alert-danger");
        }        
        
        actualizarPuntosRecientes(request);
        return "/app/usuarios/nuevoPunto.jsp";
    }
    
    // Procesa solicitud para eliminacion de punto control.
    // Delega proceso de eliminacion.
    public String eliminarPunto (HttpServletRequest request,
            HttpServletResponse response) {
        
        String sidpunto = request.getParameter("idpunto");
        int idpunto     = Integer.parseInt(sidpunto);
        
        try {
            // Elimina punto
            if (PuntoBD.remove(idpunto)) {            
                request.setAttribute("msg", "* Punto eliminado correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                SelectBD select = new SelectBD(request);

                HttpSession session = request.getSession();
                session.setAttribute("select", select);
            } else {
                request.setAttribute("msg", "* Punto no eliminado.");
                request.setAttribute("msgType", "alert alert-danger");
            }
        } catch (PuntoRelacionadoBD ex) {
            request.setAttribute("msg", "* Punto de control relacionado a una ruta. No es posible eliminar.");
            request.setAttribute("msgType", "alert alert-info");
        }
        
        ControladorPunto.actualizarPuntosRecientes(request);
        return "/app/usuarios/nuevoPunto.jsp";
    }  
    
    // Procesa solicitud para eliminacion de punto base.
    public String eliminarPuntoBase (HttpServletRequest request,
            HttpServletResponse response) {
        
        ControladorBase cb = new ControladorBase();
        return cb.eliminarBase(request, response);
    }
    
    // Procesa solicitud para almacenar una lista de puntos.
    // Delege extraccion, creacion de instrucciones SQL y almacenamiento
    // de los valores listados.
    public String guardarListaPuntos (HttpServletRequest request,
            HttpServletResponse response) {
        
        String slistaPuntos = request.getParameter("listaPuntos");
        String sql = formatInsertListaPuntosSQL(slistaPuntos);     
        if (sql.compareTo("-1") == 0) {
            request.setAttribute("msg", "* Cantidad l&iacute;mite de puntos superado. M&aacute;xima cantidad " + LIMITE_PUNTOS_IMPORTAR + ".");
            request.setAttribute("msgType", "alert alert-info");
            return "/app/usuarios/inoutPunto.jsp";
        }
        
        if (sql != null && verificarDatos(slistaPuntos) && PuntoBD.insertBlock(sql)) {
            request.setAttribute("msg", "* Puntos almacenados correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            SelectBD select = new SelectBD(request);
            
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        } else {
            request.setAttribute("msg", "* Puntos no almacenados.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/inoutPunto.jsp";
    }    
    
    private UploadServlet ups = null;
    
    // Procesa solicitud de importacion de archivo de puntos KML.
    public String importarPuntosKML (HttpServletRequest request,
            HttpServletResponse response) {
        
        ups = new UploadServlet();
        String msg, msgType; msg = msgType = "";
        String uploadFolder = getServletContext().getRealPath("");
        
        if (ups.upFile(request, response, uploadFolder)) {                        
            if (ups.parseKML_Plano() || ups.parseKML_Plano2() || ups.parseKML()) {
                ArrayList<Punto> lst = ups.getLstpuntosKML();
                if (lst != null) {
                    if (lst.size() > LIMITE_PUNTOS_IMPORTAR) {
                        msg = "* Cantidad l&iacute;mite de puntos superado. M&aacute;xima cantidad " + LIMITE_PUNTOS_IMPORTAR + ".";
                        msgType = "alert alert-info";
                    } else {
                        request.setAttribute("lstpuntosKML", ups.getLstpuntosKML());
                        request.setAttribute("puntosKML", "true");
                    }
                }                
            } else {
                msg = "* Imposible procesar el archivo de puntos KML.";
                msgType = "alert alert-info";
            }
        } else {
            msg = "* Imposible subir el archivo de puntos KML.";
            msgType = "alert alert-info";
        }
        
        request.setAttribute("msg", msg);
        request.setAttribute("msgType", msgType);
        return "/app/usuarios/inoutPunto.jsp";
    }
    
    // Procesa solicitud para almacenar puntos desde listado de puntos KML.
    // Delega creacion de instrucciones de insercion y proceso de almacenamiento.
    public String guardarListaPuntosKML (HttpServletRequest request,
            HttpServletResponse response) {
        
        if (ups != null) {
            ArrayList<Punto> lstpuntosKML = ups.getLstpuntosKML();
            if (lstpuntosKML != null && lstpuntosKML.size() > 0) {
                
                // Construye instruccion de insercion SQL
                String sql = formatInsertListaPuntosKML(lstpuntosKML);
                String sql_bases = formatInsertListaBasesKML(lstpuntosKML);
                
                // Inserta conjunto de puntos y bases
                boolean puntos = PuntoBD.insertBlock(sql);
                boolean bases  = BaseBD.insertBlock(sql_bases);
                
                if (puntos && bases) {
                    request.setAttribute("msg", "* Puntos y bases KML fueron almacenados correctamente.");
                    request.setAttribute("msgType", "alert alert-success");                    
                } else if (puntos) { 
                    request.setAttribute("msg", "* &Uacute;nicamente puntos de control KML fueron almacenados correctamente.");
                    request.setAttribute("msgType", "alert alert-success");                    
                } else if (bases) {
                    request.setAttribute("msg", "* &Uacute;nicamente bases KML fueron almacenados correctamente.");
                    request.setAttribute("msgType", "alert alert-success");                    
                } else {
                    request.setAttribute("msg", "* Puntos KML no almacenados.");
                    request.setAttribute("msgType", "alert alert-danger");
                }
                
                SelectBD select = new SelectBD(request);                    
                HttpSession session = request.getSession();
                session.setAttribute("select", select);
            }
        }
                
        return "/app/usuarios/inoutPunto.jsp";
    }
    
    /*
     *  Inicia conversion de coordenadas en formato GMD a 
     *  formato GD para los campos LATITUD, LONGITUD hacia los
     *  campos LATITUD_WEB, LONGITUD_WEB en la tabla tbl_punto y
     *  tbl_base.
     */
    public String repararCoordenadas (HttpServletRequest request,
            HttpServletResponse response) {
        
        String usar_gps = request.getParameter("usar_gps");
        
        if (usar_gps != null && usar_gps.compareTo("on") == 0)
            return repararCoordenadasDesdeGps(request, response);
        
        ArrayList<Base> lstbase = BaseBD.selectTranslateCoord();
        ArrayList<Punto> lstpto = PuntoBD.selectTranslateCoord();
        
        boolean updBase  = BaseBD.updateTranslateCoord(lstbase);
        boolean updPunto = PuntoBD.updateTranslateCoord(lstpto);
        String msg = "* No se repar&oacute; ninguna coordenada.";
        
        int size_base  = lstbase.size();
        int size_punto = lstpto.size();
        
        if (updBase && updPunto) {
            msg = "* Se repararon " +size_base+ " coordenadas base y " +size_punto+ " coordenadas punto.";
        } else if (updBase) {
            msg = "* Se repararon " +size_base+ " coordenadas base.";
        } else if (updPunto) {
            msg = "* Se repararon " +size_punto+ " coordenadas punto.";
        }
        
        if (updBase || updPunto) {
            SelectBD select = new SelectBD(request);
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        }
        
        request.setAttribute("msg", msg);
        request.setAttribute("msgType", "alert alert-info");
        
        return "/app/usuarios/nuevoPunto.jsp";
    }
    
    /*
     * Consulta coordenadas LATITUD y LONGITUD (GD) desde registros gps
     * de los puntos y bases existentes, para ser actualizados en sus
     * campos LATITUD WEB y LONGITUD WEB en tablas tbl_punto y tbl_base.
     */
    public String repararCoordenadasDesdeGps(HttpServletRequest request,
            HttpServletResponse response) {
        
        ArrayList<Base> lst_base   = BaseBD.selectMin();
        ArrayList<Punto> lst_punto = PuntoBD.selectMin();
        int nb, np; nb = np = 0;
        
        for (Base b : lst_base) {
            String etq_base = b.getEtiquetaBase();
            String latlon[] = ConsultaExterna.coordenadaGPS(etq_base);
            if (latlon != null) {
                b.setLatitudWeb(latlon[0]);
                b.setLongitudWeb(latlon[1]);
                nb += 1;
            }
        }
        
        for (Punto p : lst_punto) {
            String etq_pto  = p.getEtiquetaPunto();
            String latlon[] = ConsultaExterna.coordenadaGPS(etq_pto);
            if (latlon != null) {
                p.setLatitudWeb(latlon[0]);
                p.setLongitudWeb(latlon[1]);
                np += 1;
            }
        }
        
        boolean updBase  = BaseBD.updateTranslateCoord(lst_base);
        boolean updPunto = PuntoBD.updateTranslateCoord(lst_punto);
        String msg = "* No se repar&oacute; ninguna coordenada.";
        
        if (updBase && updPunto) {
            msg = "* Se repararon " + nb + " coordenadas base y " + np + " coordenadas punto.";
        } else if (updBase) {
            msg = "* Se repararon " + nb + " coordenadas base.";
        } else if (updPunto) {
            msg = "* Se repararon " + np + " coordenadas punto.";
        }
        
        if (updBase || updPunto) {
            SelectBD select = new SelectBD(request);
            HttpSession session = request.getSession();
            session.setAttribute("select", select);
        }
        
        request.setAttribute("msg", msg);
        request.setAttribute("msgType", "alert alert-info");
        
        return "/app/usuarios/nuevoPunto.jsp";
    }
    
    /*
     * Comprueba valores de cada uno de los campos existentes
     * en cadena de texto formateada con lista de puntos.
     */
    public boolean verificarDatos (String s) {
        
        if (s != null && s != "") {
            String listaPuntos[] = s.split("&");
            
            for (int i = 0; i < listaPuntos.length; i++) {
                String infoPunto[] = listaPuntos[i].split(",");
                
                if (infoPunto[0].matches("^[0-9]+$") &&
                    infoPunto[1].matches("^-?[0-9]*\\.[0-9]+$") &&
                    infoPunto[2].matches("^-?[0-9]*\\.[0-9]+$") &&
                   (infoPunto[3].equalsIgnoreCase("'norte'") || infoPunto[3].equalsIgnoreCase("'sur'")) &&
                   (infoPunto[4].equalsIgnoreCase("'este'")  || infoPunto[4].equalsIgnoreCase("'oeste'"))) {
                    
                    if (Integer.parseInt(infoPunto[0]) >= 1) continue;
                    else return false;
                } else { 
                    return false; 
                }
            }
            return true;
        }
        return false;
    }
        
    /*
     * Genera instruccion SQL INSERT para la insercion
     * de un conjunto de puntos a partir de cadena de
     * texto formateada.
     */
    public String formatInsertListaPuntosSQL (String s) {
        
        String sql = null;
        if (s != null && s != "") {
            String listaPuntos[] = s.split("&");
            if (listaPuntos.length > LIMITE_PUNTOS_IMPORTAR)
                return "-1";
            
            // Formato entrada
            // puntos := &* cod,lat,lon,dirlat,dirlon + ,latw,lonw
            
            sql = "INSERT INTO tbl_punto (CODIGO_PUNTO, LATITUD, LONGITUD, DIRECCION_LATITUD,"
               + " DIRECCION_LONGITUD, LATITUD_WEB, LONGITUD_WEB, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL)"
               + " VALUES ";
            
            String lista = "";
            for (int i = 0; i < listaPuntos.length; i++) {
                String infoPuntos[] = listaPuntos[i].split(",");
                infoPuntos[1] = "'" + Coordenadas.toGMD(infoPuntos[1]) + "'";
                infoPuntos[2] = "'" + Coordenadas.toGMD(infoPuntos[2]) + "'";
                
                String params = "";
                for (String sip : infoPuntos) {
                    if (params == "") params = sip;
                    else params += "," + sip;
                }
                
                if (lista == "") {
                    lista = "(" + params + ",1,2,1,1)";
                } else {
                    lista += ",(" + params + ",1,2,1,1)";
                }
            }            
            sql += lista;
            return sql;
        } 
        return null;
    }
    
    /*
     * Genera instruccion SQL INSERT para la insercion
     * de un conjunto de puntos a partir de una lista.
     */
    public String formatInsertListaPuntosKML (ArrayList<Punto> lst) {
        
        String sql = "INSERT INTO tbl_punto (CODIGO_PUNTO, LATITUD, LONGITUD, DIRECCION_LATITUD,"
                  + " DIRECCION_LONGITUD, LATITUD_WEB, LONGITUD_WEB, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL)"
                  + " VALUES ";        
        
        String v, lv; v = lv = "";
        
        for (int i = 0; i < lst.size(); i++) {
            Punto p = lst.get(i);
            
            // Se almacenan unicamente puntos de control, no bases
            if (p.getDireccion() == 1 ||
                p.getDireccion() == 2) {
                v = "("
                    + p.getCodigoPunto() 
                    + "," + p.getLatitud()
                    + "," + p.getLongitud()
                    + "," + p.getDireccionLatitud()
                    + "," + p.getDireccionLongitud()
                    + "," + p.getLatitudWeb()
                    + "," + p.getLongitudWeb()
                    + "," + p.getRadio()
                    + "," + p.getDireccion()
                    + ", 1, 1)";

                if (lv == "")                    
                    lv = v;
                else
                    lv += "," + v;
            }
        }
        
        sql += lv;
        return sql;
    }
    
    /*
     * Genera instruccion SQL INSERT para la insercion
     * de un conjunto de bases a partir de una lista.
     */
    public String formatInsertListaBasesKML (ArrayList<Punto> lst) {
        
        String sql = "INSERT INTO tbl_base (IDENTIFICADOR_BASE, CODIGO_BASE, LATITUD, LONGITUD, DIRECCION_LATITUD,"
                  + " DIRECCION_LONGITUD, LATITUD_WEB, LONGITUD_WEB, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL)"
                  + " VALUES ";        
        
        String v, lv; v = lv = "";
        
        for (int i = 0; i < lst.size(); i++) {
            Punto p = lst.get(i);
            
            // Se almacenan unicamente bases
            if (p.getDireccion() == 3) {
                v = "(0,"
                    + p.getCodigoPunto() 
                    + "," + p.getLatitud()
                    + "," + p.getLongitud()
                    + "," + p.getDireccionLatitud()
                    + "," + p.getDireccionLongitud()
                    + "," + p.getLatitudWeb()
                    + "," + p.getLongitudWeb()
                    + "," + p.getRadio()
                    + "," + p.getDireccion()
                    + ", 1, 1)";

                if (lv == "")                    
                    lv = v;
                else
                    lv += "," + v;
            }
        }
        
        sql += lv;
        return sql;
    }
    
    
    /*
     * Actualizacion de listado de puntos recientes.
     */
    public static void actualizarPuntosRecientes(HttpServletRequest request) {
        String fecha = ffmt.format(new Date());
        
        HttpSession session = request.getSession();
        session.setAttribute("lst_pto_rte", PuntoBD.selectPuntosRecientes(fecha));
    }
}
