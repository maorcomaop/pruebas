/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

/**
 *
 * @author lider_desarrollador
 */
import com.registel.rdw.logica.Punto;
import com.registel.rdw.utils.Coordenadas;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DATA_DIRECTORY  = "resources" + File.separator + "data";
    private static final int MAX_MEMORY_SIZE    = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024;
    private String filePath = null;
    
    // Lista para almacenar datos procesados por funciones internas
    private ArrayList<Punto> lstpuntosKML = null;
    
    /*
     * Sube archivo cuyo nombre y contenido 
     * fue pasado por formulario al servidor.
     */
    public boolean upFile(HttpServletRequest request, 
            HttpServletResponse response,
            String uploadFolder) {

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return false;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        // String uploadFolder = getServletContext().getRealPath("")
        // uploadFolder += DATA_DIRECTORY;        
        
        if (uploadFolder.endsWith("\\") || uploadFolder.endsWith("/"))
            uploadFolder += DATA_DIRECTORY;
        else
            uploadFolder += File.separator + DATA_DIRECTORY;        

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField() && 
                    item.getName() != null && 
                    item.getName() != "") {

                    String fileName = new File(item.getName()).getName();
                    filePath = uploadFolder + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                    // System.out.println(filePath);
                    // saves the file to upload directory
                    item.write(uploadedFile);                               
                    
                    return true;
                }
            }
        } catch (FileUploadException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        } return false;
    }
    
    /*
     * Inicia lectura y parseo de archivo KML (Formato plano),
     * y almacena datos en lista.
     */ 
    public boolean parseKML_Plano() {
                
        if (filePath == null || filePath == "")
            return false;
        
        final Kml kml = Kml.unmarshal(new File(filePath));
        if (kml == null) return false;
        Feature feature = kml.getFeature();
        
        try {
            if (feature != null &&
                feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();

                lstpuntosKML = new ArrayList<Punto>();
                for (Feature f : featureList) {
                    Placemark pc = (Placemark) f;
                    Geometry geo = pc.getGeometry();

                    double coord[] = parseGeometry(geo);
                    if (coord == null) return false;

                    String latGD = String.valueOf(coord[0]);
                    String lonGD = String.valueOf(coord[1]);

                    Punto p = new Punto();                

                    p.setLatitudWeb(latGD);
                    p.setLongitudWeb(lonGD);
                    p.setLatitud(Coordenadas.toGMD(latGD));
                    p.setLongitud(Coordenadas.toGMD(lonGD));                

                    lstpuntosKML.add(p);

                } 
                return true;            
            }        
        } catch (Exception e) {
            System.err.println("Formato no permitido para archivo kml.");
        }
        return false;
    }
    
    /*
     * Inicia lectura y parseo de archivo KML (Formato plano),
     * y almacena datos en lista.
     */ 
    public boolean parseKML_Plano2() {
                
        if (filePath == null || filePath == "")
            return false;
        
        final Kml kml = Kml.unmarshal(new File(filePath));
        if (kml == null) return false;
        Feature feature = kml.getFeature();
        
        try {
            if (feature != null &&
                feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();            

                lstpuntosKML = new ArrayList<Punto>();
                for (Feature f : featureList) {
                    if (f instanceof Folder) {
                        Folder folder  = (Folder) document.getFeature().get(0);
                        int sizeFolder = folder.getFeature().size();

                        for (int i = 0; i < sizeFolder; i++) {

                            Placemark pc = (Placemark) folder.getFeature().get(i);
                            Geometry geo = pc.getGeometry();

                            double coord[] = parseGeometry(geo);
                            if (coord == null) return false;

                            String latGD = String.valueOf(coord[0]);
                            String lonGD = String.valueOf(coord[1]);                        

                            Punto p = new Punto();                

                            p.setLatitudWeb(latGD);
                            p.setLongitudWeb(lonGD);
                            p.setLatitud(Coordenadas.toGMD(latGD));
                            p.setLongitud(Coordenadas.toGMD(lonGD));                

                            lstpuntosKML.add(p);
                        }
                    }
                } 
                return true;
            }
        } catch (Exception e) {
            System.err.println("Formato no permitido para archivo kml.");
        }
        return false;
    }
    
    /*
     * Inicia lectura y parseo de archivo KML (Formato registel),
     * y almacena datos en lista.
     */ 
    public boolean parseKML () {
        
        if (filePath == null || filePath == "")
            return false;
        
        final Kml kml = Kml.unmarshal(new File(filePath));
        if (kml == null) return false;
        Feature feature = kml.getFeature();
        
        try {
            if (feature != null &&
                feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();

                for (Feature f : featureList) {
                    if (f instanceof Folder) {
                        Folder folder = (Folder) document.getFeature().get(0);
                        int folderSize = folder.getFeature().size();

                        if (folderSize > 90)
                            return false;

                        lstpuntosKML = new ArrayList<Punto>();
                        for (int i = 0; i < folderSize; i++) {
                            Placemark pc = (Placemark) folder.getFeature().get(i);
                            String desc  = pc.getDescription();

                            if (desc != null && desc.length() >= 5) {
                                String array[] = desc.split(",");

                                if (isNumeric(array[0]) && 
                                    isNumeric(array[1]) && 
                                    isNumeric(array[2])) {
                                    int codigoPunto = Integer.parseInt(array[0]);
                                    int radio = Integer.parseInt(array[1]);
                                    int direccion = Integer.parseInt(array[2]);

                                    if (codigoPunto == 100 || codigoPunto == 101) return false; // Codigos reservados para BS, BL
                                    if (radio <= 0 || radio > 250) return false;
                                    if (direccion <= 0 || direccion > 3) return false;

                                    Geometry geo = pc.getGeometry();
                                    double coord[] = parseGeometry(geo);
                                    if (coord == null) return false;

                                    String dirLat = (coord[0] > 0) ? "'Norte'" : "'Sur'";
                                    String dirLon = (coord[1] > 0) ? "'Este'"  : "'Oeste'";                               

                                    String latGD = String.valueOf(coord[0]);
                                    String lonGD = String.valueOf(coord[1]);

                                    Punto p = new Punto();
                                    p.setCodigoPunto(codigoPunto);
                                    p.setRadio(radio);
                                    p.setDireccion(direccion);
                                    p.setLatitudWeb(latGD);
                                    p.setLongitudWeb(lonGD);
                                    p.setDireccionLatitud(dirLat);
                                    p.setDireccionLongitud(dirLon);

                                    p.setLatitud(Coordenadas.toGMD(latGD));
                                    p.setLongitud(Coordenadas.toGMD(lonGD));

                                    lstpuntosKML.add(p);
                                }
                            }
                        } 
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Formato no permitido para archivo kml.");
        }
        return false;
    }
    
    /*
     * Extrae coordenadas Latitud y Longitud
     * relacionadas a objeto Geometry.
     */
    public double[] parseGeometry (Geometry g) {
        
        if (g != null && g instanceof Point) {
            Point p = (Point) g;
            List<Coordinate> coord = p.getCoordinates();

            if (coord != null) {
                double rs[] = {
                    coord.get(0).getLatitude(),
                    coord.get(0).getLongitude()
                };
                return rs;
            }
        }
        return null;
    }
    
    public String getFilePath () {
        return filePath;
    }
    
    public ArrayList<Punto> getLstpuntosKML () {
        return lstpuntosKML;
    }
}