/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.logica.VehiculoPerimetro;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.SystemUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

/**
 *
 * @author lider_desarrollador
 */
public class VehiculoPerimetroDALXML {
    
    private final SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final String URI_XML_PERIMETRO_LINUX = "/config/perimetro/PerimetroVehiculosDisco.xml";
    private final String URI_XML_PERIMETRO_WIN   = "\\config\\perimetro\\PerimetroVehiculosDisco.xml";
    
    /**
     * Obtiene documento XML con lista de nodos de vehiculos en perimetro.
     */
    private Document getDocumentXML(String directorioRaiz) throws ParserConfigurationException, SAXException, IOException {                      
        try {     
             SAXBuilder builder = new SAXBuilder();
             
             String path = "";
             if (SystemUtils.IS_OS_WINDOWS)
                 path = directorioRaiz + URI_XML_PERIMETRO_WIN;
             else if (SystemUtils.IS_OS_LINUX) {
                 path = directorioRaiz + URI_XML_PERIMETRO_LINUX;
             }             

             Document doc = builder.build(new File(path));
             return doc;
         } catch (JDOMException e) {
             System.err.println(e);
         }
        return null;
    }
    
    /**
     * Obtiene lista de vehiculos en perimetro que se encuentren listados en archivo XML.
     */
    public ArrayList<VehiculoPerimetro> obtenerVehiculosPerimetrosXML (String directorioRaiz) {
        ArrayList<VehiculoPerimetro> listaVehiculosPerimetro = new ArrayList<>();
        
        try {
            // Obtenemos documentos XML
            Document document = getDocumentXML(directorioRaiz);
            Element rootNode = document.getRootElement();
            
            // Obtenemos lista de nodos que tienen etiqueta "vehiculoperimetro"
            List<Element> nodosVehiculosPerimetro = rootNode.getChildren("vehiculoperimetro");
            
            // Recorremos lista de vehiculos en perimetro   
            for (int i= 0; i < nodosVehiculosPerimetro.size(); i++) {
                Element elemento = nodosVehiculosPerimetro.get(i);

                VehiculoPerimetro vp = new VehiculoPerimetro();
                vp.setIdVehiculo(Integer.parseInt(elemento.getChildText("fk_vehiculo")));
                vp.setPlaca(elemento.getChildText("placa"));
                vp.setNumeroInterno(elemento.getChildText("numero_interno"));
                vp.setIdBase(Integer.parseInt(elemento.getChildText("fk_base")));
                vp.setRutaAsignada(((elemento.getChildText("ruta_asignada") != null) ? (elemento.getChildText("ruta_asignada")) : ("Sin Ruta")));
                vp.setHoraLlegada (((!elemento.getChildText("hora_llegada").equals("NULL")) ? (formatFecha.parse(elemento.getChildText("hora_llegada"))) : null));
                vp.setHoraSalida  (((!elemento.getChildText("hora_salida").equals("NULL"))  ? (formatFecha.parse(elemento.getChildText("hora_salida")))  : null));
                vp.setEstado(new Short(elemento.getChildText("estado")));
                vp.setFechaModificacion(((!elemento.getChildText("fecha_modificacion").equals("NULL")) ? 
                        (formatFecha.parse(elemento.getChildText("fecha_modificacion"))) : new Date()));
                
                int est = vp.getEstado();
                String est_str = (est == 1) ? "[Descarte Final]" :
                                 (est == 2) ? "[En perimetro]" :
                                 (est == 3) ? "[Descarte Inicial]" : "";
                vp.setNombreEstado(est_str);
                
                listaVehiculosPerimetro.add(vp);
            }
        }
        catch (NumberFormatException e) {
            System.err.println(e);
        }
        catch(ParserConfigurationException e){
            System.err.println(e);
        } 
        catch (SAXException e) {
            System.err.println(e);
        } 
        catch (IOException e) {
            System.err.println(e);
        } 
        catch (ParseException e) {
            System.err.println(e);
        } 
        
        return listaVehiculosPerimetro;
    }
}
