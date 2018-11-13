/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.logica.ConfiguracionLiquidacion;
import static com.registel.rdw.utils.ConstantesSesion.obtenerEtiquetasLiquidacionPerfil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author lider_desarrollador
 */
public class PrintOutExcel {
    
    // Genera e imprime reporte excel segun su tipo
    // y parametros especificados.
    public void print(HttpServletRequest request, 
                      HttpServletResponse response,
                      ParametrosReporte pr) throws IOException {
        
        // Reporte editable XLS            
        ReporteUtilExcel rue = new ReporteUtilExcel();      
        
        ConfiguracionLiquidacion etiquetas = obtenerEtiquetasLiquidacionPerfil(request);

        MakeExcel rpte = rue.crearReporte(pr.getTipoReporte(), false, pr, etiquetas);
        String nombreArchivo = pr.getNombreReporte() + ".xls";

        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/ms-excel");            
        response.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo);

        HSSFWorkbook file = rpte.getExcelFile();
        file.write(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();
    }
}
