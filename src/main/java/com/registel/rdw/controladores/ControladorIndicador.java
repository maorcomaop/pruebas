
package com.registel.rdw.controladores;

import com.registel.rdw.datos.IndicadorBD;
import com.registel.rdw.logica.CapacidadTransportadora;
import com.registel.rdw.logica.IndicadorCapacidadTransportadora;
import com.registel.rdw.logica.IndicadorCumplimientoRuta;
import com.registel.rdw.logica.IndicadorPasajeroHora;
import com.registel.rdw.logica.IndicadorDescuentoDePasajeros;
import com.registel.rdw.logica.IndicadorProduccion;
import com.registel.rdw.logica.PasajeroHora;
import com.registel.rdw.utils.Restriction;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ControladorIndicador extends HttpServlet {
    
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
        
    // Almacena parametros de consulta en variable de sesion web,
    // para usar entre cada una de las paginas de los indicadores.
    public void guardarParametros(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {                

        String fecha = request.getParameter("fecha");
        String sruta_icr = request.getParameter("sruta_icr");
        String sruta_iph = request.getParameter("sruta_iph");

        HttpSession session = request.getSession();
        session.setAttribute("fechaIndicador", fecha);
        session.setAttribute("rutasIcr", sruta_icr);
        session.setAttribute("rutasIph", sruta_iph);        
        
        response.setContentType("text/html; charset=iso-8859-1");
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();        
        String url = "";
        
        String desdePaginaIndicadores = request.getParameter("desdePaginaIndicadores");
        if (desdePaginaIndicadores != null &&
            desdePaginaIndicadores.compareTo("1") == 0) {
            guardarParametros(request, response);
            return;
        }
        
        if (requestURI.endsWith("/indicadorProduccion")) {
            indicadorProduccion(request, response);
        } else if (requestURI.endsWith("/indicadorCumplimientoRuta")) {
            indicadorCumplimientoRuta(request, response);            
        } else if (requestURI.endsWith("/indicadorCapacidadTransportadora")) {
            indicadorCapacidadTransportadora(request, response);
        } else if (requestURI.endsWith("/indicadorPasajeroHora")) {
            indicadorPasajeroHora(request, response);
        }
        if (requestURI.endsWith("/indicadorDescuentoDePasajeros")) {
            indicadorDescuentoPas(request, response);
        }
        if (requestURI.endsWith("/indicadorDescuentoDePasajerosExterno")) {
            indicadorDescuentoDePasajerosExterno(request, response);
        }
        
        if (url != "") {
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }        
    }
    
    // Procesa solicitud de generacion de indicador de produccion.
    // Delega proceso de consulta de datos para elaboracion del reporte.
    // Imprime resultado en componente select (html), que sera
    // procesado por navegador para construir grafico.
    public void indicadorProduccion(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String fecha = request.getParameter("fecha");
        ArrayList<IndicadorProduccion> lst = IndicadorBD.indicadorProduccion(fecha);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        String select  = "<div style='display: none;'>"
                       + "<select id='sdata-ip' name='sdata-ip'>";
        String fselect = "</select></div>";        
                
        if (lst != null) {
            String options = "";
            DecimalFormat df = new DecimalFormat("0.00");
            
            for (int i = 0; i < lst.size(); i++) {
                IndicadorProduccion ip = lst.get(i);
                if (ip.getTipoRegistro() == 
                    IndicadorProduccion.PRODUCCION_TOTAL_RUTA) {
                    continue;
                }
                String value = ip.getNombreRuta()           + "%" + 
                               ip.getProduccion()           + "%" + 
                               ip.getDistanciaRecorrida()   + "%" +
                               df.format(ip.getIpk());
                options += "<option value='" + value + "'></option>";
            }
            out.println(select);
            out.println(options);
            out.println(fselect);
        } else {
            out.println(select);
            out.println(fselect);
        }
    }
    
    // Procesa solicitud de generacion de indicador de cumplimiento ruta.
    // Delega proceso de consulta de datos para elaboracion del reporte.
    // Imprime resultado en componente select (html), que sera
    // procesado por navegador para construir grafico.
    public void indicadorCumplimientoRuta(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String fecha = request.getParameter("fecha");
        String sruta = request.getParameter("sruta-icr");
        ArrayList<String> lst = IndicadorBD.indicadorCumplimientoRuta(fecha, sruta);
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        String select  = "<div style='display: none;'>"
                       + "<select id='sdata-icr' name='sdata-icr'>";
        String fselect = "</select></div>";        
        
        if (lst != null) {
            String options = ""; int maxVuelta = 0;
            for (int i = 0; i < lst.size(); i++) {
                String value = lst.get(i);                
                options += "<option value='" + value + "'></option>";
                
                String elemts[] = value.split("%");
                //if (maxVuelta < elemts.length-1) {
                    //maxVuelta = elemts.length-1;
                //}
                if (elemts.length-1 > maxVuelta) {
                    maxVuelta = elemts.length-1;
                }
            }            
            out.println(select);
            out.println(options);
            out.println(fselect);
            out.println("<div><input type='hidden' id='maxVuelta-icr' name='maxVuelta-icr' value='"+ maxVuelta +"'></div>");
        } else {
            out.println(select);
            out.println(fselect);
        }
    }
    
    private static int hora_inicio = 4;
    private static int hora_fin    = 23;
    
    // Establece hora limite si fecha es dia actual, utilizada
    // para imprimir solo los eventos ocurridos hasta una
    // hora adicional a la actual.
    private int horaLimite(String fecha) {
        
        int hora_lim = hora_fin;
        String fecha_act = ffmt.format(new Date());
        
        if (fecha_act.compareTo(fecha) == 0) {
            Calendar calendar = Calendar.getInstance();
            hora_lim = calendar.get(Calendar.HOUR_OF_DAY);
            if (hora_lim < hora_fin) {
                hora_lim += 1;
            }
        }
        return hora_lim;
    }
    
    // Procesa solicitud de generacion de indicador de capacidad transportadora.
    // Delega proceso de consulta de datos para elaboracion del reporte.
    // Imprime resultado en componente select (html), que sera
    // procesado por navegador para construir grafico.
    public void indicadorCapacidadTransportadora(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String fecha = request.getParameter("fecha");        
        
        ArrayList<IndicadorCapacidadTransportadora> lst
                = IndicadorBD.indicadorCapacidadTransportadora(fecha);        
        
        IndicadorCapacidadTransportadora ict;
        ArrayList<CapacidadTransportadora> detalle_ct;
        CapacidadTransportadora ct;
        DecimalFormat df = new DecimalFormat("0.00");
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String cabecera, filas, cols; cabecera = filas = cols = "";
        boolean existe_hora = false;
        
        // Limitamos visualizacion de indicador hasta hora_actual + 1,
        // solo si es fecha actual
        int hora_lim = horaLimite(fecha);
        
        if (lst != null && lst.size() > 0) 
        {
            for (int hr = hora_inicio; hr <= hora_lim; hr++) {
                String hora = (hr < 10) ? "0" + hr : "" + hr;
                hora += ":00";
                cols += "<th>" + hora + "</th>";
            }
            cols     = "<th>Ruta</th>" + cols;
            cabecera = "<tr>" + cols + "</tr>";

            for (int i = 0; i < lst.size(); i++) {
                ict        = lst.get(i);
                detalle_ct = ict.getDetalle();
                cols = "";
                
                for (int k = hora_inicio; k <= hora_lim; k++) {
                    existe_hora = false;
                    for (int j = 0; j < detalle_ct.size(); j++) {
                        ct         = detalle_ct.get(j);
                        int hora   = Restriction.getNumber(ct.getHora());
                        double icu = ct.getIcuHora();
                        
                        // Colores de celda segun estadistica
                        // Rojo     icu <= 70%
                        // Naranja  70% < icu <= 150%
                        // Verde    150% < icu <= 200%
                        // Violeta  icu > 200%
                        
                        String td = "<td class='text-align-center";
                        if (icu <= 0.70) {
                            td += " lbl-rojo-claro'>";
                        } else if (icu > 0.70 && icu <= 1.50) {
                            td += " lbl-naranja-claro'>";
                        } else if (icu > 1.50 && icu <= 2.00) {
                            td += " lbl-verde-claro'>";
                        } else {
                            td += " lbl-morado-claro'>";
                        }
                        
                        if (hora == k) {
                            cols += td + df.format(icu) + "</td>";
                            existe_hora = true;
                            break;
                        }
                    }
                    if (!existe_hora) {
                        cols += "<td class='text-align-center'>-</td>";   
                    }
                }
                
                String ruta = ict.getNombreRuta();
                cols   = "<td>" + ruta + "</td>" + cols;
                filas += "<tr>" + cols + "</tr>";
            }

            String table = "<table id='tabla-ict' class='table-style'>"
                         + "<caption class='title'>"+ fecha +" / &Iacute;ndice de capacidad transportadora</caption>"
                         + "<thead>" + cabecera + "</thead>"
                         + "<tbody>" + filas + "</tbody>";

            out.println("<div>");
            out.println(table);
            out.println("</div>");
        }
    }
    
    // Procesa solicitud de generacion de indicador pasajero hora.
    // Delega proceso de consulta de datos para elaboracion del reporte.
    // Imprime resultado en componente select (html), que sera
    // procesado por navegador para construir grafico.
    public void indicadorPasajeroHora(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        String fecha = request.getParameter("fecha");
        String sruta = request.getParameter("sruta-iph");
        
        ArrayList<PasajeroHora> lst =
                IndicadorBD.indicadorPasajeroHora_entradas(fecha, sruta);        
        
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        
        String select  = "<div style='display: none;'>"
                       + "<select id='sdata-iph' name='sdata-iph'>";
        String fselect = "</select></div>";
        
        if (lst != null && lst.size() > 0)
        {
            // Limitamos visualizacion de indicador hasta hora_actual + 1,
            // solo si es fecha actual
            int hora_lim = horaLimite(fecha);            
            String options = "";
            
            for (PasajeroHora ph : lst) {           
                int hora_ph = Restriction.getNumber(ph.getHora());
                if (hora_ph > hora_lim) continue;
                String value = ph.getIdRuta()           + "%" +
                               ph.getNombreRuta()       + "%" +                               
                               ph.getHora()             + "%" +                               
                               ph.getEntradas();
                options += "<option value='" + value + "'></option>";
            }
            
            out.println(select);
            out.println(options);
            out.println(fselect);
            out.println("<input type='hidden' id='hora_inicio_iph' name='hora_inicio_iph' value='"+ hora_inicio +"'>");
            out.println("<input type='hidden' id='hora_fin_iph' name='hora_fin_iph' value='" + hora_lim + "'>");
        } else {            
            out.println(select);
            out.println(fselect);
        }
    }

/*ADICIONADO POR JAIR VIDAL*/     
     public void indicadorDescuentoPas(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
         String fecha = request.getParameter("fecha");        
         StringBuilder nombres = new StringBuilder();
         StringBuilder valores = new StringBuilder();
         String total = "";
         HttpSession session = request.getSession();        
        
         IndicadorDescuentoDePasajeros p = new IndicadorDescuentoDePasajeros();         
         p.setFecha_inicio(fecha);
         p.setFecha_fin(fecha);        
         int i=0;
         PrintWriter out = response.getWriter();
         
        ArrayList<IndicadorDescuentoDePasajeros> ls = IndicadorBD.IndicadorDescuentoDePasajeros(p);
         if ((ls != null) && (ls.size() > 0) ) {
             nombres.append("[{");
             for (IndicadorDescuentoDePasajeros l : ls) {
                 nombres.append('"').append(i++).append('"').append(':').append('"').append(l.getNombre_categoria()).append('"').append(",");                 
             }
             nombres.append("}");
             String n = nombres.toString().substring(0, nombres.toString().length()-3);             
             n += '"';
             n += "}]";
             /************************************************************************************************/
             i=0;
             valores.append("[{");
             for (IndicadorDescuentoDePasajeros l : ls) {
                 valores.append('"').append(i++).append('"').append(':').append(l.getCantidad()).append(",");
             }
             valores.append("}]");
             String v = valores.toString().substring(0, valores.toString().length()-3);             
             v += "}]";            

             total = "{"+'"'+"label"+'"'+":"+n+", ";
             total += '"'+"value"+'"'+":"+v+"}";             
         }
         out.println(total);         
    }
     
     
     
     /*FUNCION QUE REDIRECCIONA AL GRAFICO MAS AMPLIO*/
     public void indicadorDescuentoDePasajerosExterno(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
         String f = request.getParameter("fecha");
         String pagina = request.getParameter("desdePaginaIndicadores");         
         HttpSession session = request.getSession();
         session.setAttribute("fecha_ind", f);
         response.setContentType("text/html; charset=iso-8859-1");
         out.println(f);

    }
}
