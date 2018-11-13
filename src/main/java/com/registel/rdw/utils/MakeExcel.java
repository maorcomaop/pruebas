
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import com.registel.rdw.datos.CategoriasDeDescuentoBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.ReportesBD;
import com.registel.rdw.logica.ConteoPerimetro;
import com.registel.rdw.logica.AlarmaInfoReg;
import com.registel.rdw.logica.CategoriasDeDescuento;
import com.registel.rdw.logica.ConfCalificacionConductor;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.DatosGPS;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.InformacionRegistradora;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Planilla;
import com.registel.rdw.logica.PlanillaDespacho;
import com.registel.rdw.logica.PuntoControl;
import com.registel.rdw.logica.RendimientoConductor;
import com.registel.rdw.reportes.AlarmaXVehiculo;
import com.registel.rdw.reportes.CategoriaQueDescuentaPax;
import com.registel.rdw.reportes.ComparativoProduccionRuta;
import com.registel.rdw.reportes.ConductorEstablecido;
import com.registel.rdw.reportes.ConductorXVehiculo;
import com.registel.rdw.reportes.ConsolidadoLiquidacion;
import com.registel.rdw.reportes.ConsolidadoXEmpresa;
import com.registel.rdw.reportes.NivelOcupacion;
import com.registel.rdw.reportes.ProduccionXRuta;
import com.registel.rdw.reportes.ProduccionXVehiculo;
import com.registel.rdw.reportes.PuntosControl;
import com.registel.rdw.reportes.VehiculoXAlarma;
import com.registel.rdw.reportes.ConteoPerimetro_r;
import com.registel.rdw.reportes.CumplimientoRuta;
import com.registel.rdw.reportes.DescripcionRuta;
import com.registel.rdw.reportes.Despachador;
import com.registel.rdw.reportes.Gerencia;
import com.registel.rdw.reportes.GerenciaXVehiculo;
import com.registel.rdw.reportes.IPK_r;
import com.registel.rdw.reportes.LiquidacionXLiquidador;
import com.registel.rdw.reportes.LiquidacionIPK;
import com.registel.rdw.reportes.LiquidacionConsulta;
import com.registel.rdw.reportes.ProduccionXHora;
import com.registel.rdw.reportes.Propietario_r;
import com.registel.rdw.reportes.RutaEstablecida;
import com.registel.rdw.reportes.RutaXVehiculo;
import com.registel.rdw.reportes.RutaXVehiculoDph;
import com.registel.rdw.reportes.VehiculoEstablecido;
import com.registel.rdw.reportes.VehiculoXRuta;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
//import org.apache.poi.hssf.record.formula.functions.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author lider_desarrollador
 */
public class MakeExcel {

    private HSSFWorkbook libro;
    private CellStyle estiloTitulo;
    private CellStyle estiloBold;
    private CellStyle estiloError;
    private CellStyle estiloItalic;
    private CellStyle estiloItalicBold;
    private CellStyle estiloRojo;
    private CellStyle estiloNaranja;
    private CellStyle estiloVerde;
    private CellStyle estiloGris;
    private CellStyle estiloPiePagina;
    private CellStyle estiloFila;
    private CellStyle estiloFila2;
    private CellStyle estiloFila3;
    private CellStyle alineacionVerticalCentro;
    private CellStyle alineacionDerecha;
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat hfmt = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat fhfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat hfmt_min = new SimpleDateFormat("HH:mm");

    public MakeExcel() {
        libro = new HSSFWorkbook();

        estiloTitulo = getTitleStyle(libro);
        estiloTitulo.setFont(getTitleFont(libro));

        estiloBold = getDefaultStyle(libro);
        estiloBold.setFont(getFont(libro, false, true));

        estiloError = getErrorStyle(libro);
        estiloError.setFont(getFont(libro, false, false));

        estiloItalic = getDefaultStyle(libro);
        estiloItalic.setFont(getFont(libro, true, false));

        estiloItalicBold = getDefaultStyle(libro);
        estiloItalicBold.setFont(getFont(libro, true, true));

        estiloRojo = getRedStyle(libro);
        estiloNaranja = getOrangeStyle(libro);
        estiloVerde = getGreenStyle(libro);
        estiloGris = getGreyStyle(libro);
        estiloFila = getRowStyle(libro, 1);
        estiloFila2 = getRowStyle(libro, 2);
        estiloFila3 = getRowStyle(libro, 3);

        estiloPiePagina = getDefaultStyle(libro);
        Font font_pie = getFont(libro, false, false);
        font_pie.setFontHeight((short) 172);
        estiloPiePagina.setFont(font_pie);
        estiloPiePagina.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        alineacionVerticalCentro = getDefaultStyle(libro);
        alineacionVerticalCentro.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        alineacionDerecha = getDefaultStyle(libro);
        alineacionDerecha.setAlignment(CellStyle.ALIGN_RIGHT);
    }

    public HSSFWorkbook getExcelFile() {
        return this.libro;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Reportes
    ////////////////////////////////////////////////////////////////////////////
    public void reporte_AlarmasXVehiculo(String titulo, ArrayList<AlarmaXVehiculo> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA INGRESO", "N° VUELTA", "RUTA",
            "FECHA ALARMA", "HORA", "NOMBRE", "CANTIDAD", "UNIDAD MEDICION"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos de la tabla
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue((String) title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }
        
        for (AlarmaXVehiculo a : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                a.getPlaca(), a.getNumeroInterno(), a.getFechaIngreso(),
                a.getNumeroVuelta(), a.getRuta(), a.getFecha(), a.getHora(),
                a.getNombreAlarma(), a.getCantidad(), a.getUnidadMedicion()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ProduccionXVehiculo(String titulo, ArrayList<ProduccionXVehiculo> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA", "N° VUELTA", "RUTA",
            "HORA SALIDA", "HORA LLEGADA", "NUM. INICIAL", "NUM. FINAL",
            "TOTAL PASAJEROS", "CONTEOS EN PERIMETRO", "CANT. ALARMAS BLOQUEO", "CONDUCTOR"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos de la tabla
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue((String) title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ProduccionXVehiculo p : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                p.getPlaca(), p.getNumeroInterno(), p.getFechaIngreso(), p.getNumeroVuelta(), p.getNombreRuta(),
                p.getHoraSalida(), p.getHoraIngreso(), p.getNumeracionSalida(), p.getNumeracionLlegada(),
                p.getDiferenciaNumeracion(), p.getConteoPerimetro(), p.getCantidadAlarmas(),
                p.getNombreConductor() + " " + p.getApellidoConductor()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ProduccionXRuta(String titulo, ArrayList<ProduccionXRuta> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA", "N° DE VUELTAS", "CANTIDAD PASAJEROS",
            "PROMEDIO POR VUELTA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ProduccionXRuta p : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                p.getPlaca(), p.getNumeroInterno(), p.getFecha(),
                p.getCantidadVueltas(), p.getCantidadPasajeros(), p.getPromedioPasajeros()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ProduccionXHora(String titulo, ArrayList<ProduccionXHora> data, ParametrosReporte pr) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "FECHA", "PLACA", "NUMERO INTERNO", "RUTA", "HORA", "PRODUCTIVIDAD"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        Row filaTitulo = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdaTitulo = filaTitulo.createCell(cellnum++);
            celdaTitulo.setCellValue(title);
            celdaTitulo.setCellStyle(estiloTitulo);
        }

        int hora;
        String shora;
        int color = 1;

        if (data != null && data.size() > 0) {

            String placa = data.get(0).getPlaca();
            for (ProduccionXHora pxh : data) {
                long cantidad_pasajeros = pxh.getCantidadPasajeros();
                if (cantidad_pasajeros < 0) {
                    cantidad_pasajeros = 0;
                }

                hora = pxh.getHora();
                shora = (hora < 10)
                        ? "0" + hora + ":00:00 - 0" + hora + ":59:59"
                        : hora + ":00:00 - " + hora + ":59:59";

                Object cellData[] = {
                    pxh.getFecha(), pxh.getPlaca(), pxh.getNumeroInterno(),
                    pxh.getNombreRuta(), shora, cantidad_pasajeros
                };

                if (placa.compareTo(pxh.getPlaca()) != 0) {
                    color = (color == 1) ? 2 : 1;
                    placa = pxh.getPlaca();
                }

                cellnum = 0;
                Row fila = hoja.createRow(rownum++);
                for (Object idata : cellData) {
                    Cell celda = fila.createCell(cellnum++);
                    setCellValue(celda, idata);

                    if (color == 1) {
                        celda.setCellStyle(estiloFila3);
                    }
                }
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_IPK_GPS(String titulo, ArrayList<IPK_r> data_gps, boolean consolidado, Entorno entorno) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String ajuste_pto   = entorno.obtenerPropiedad(Constant.AJUSTE_PTO_CONTROL);
        boolean ajustar_pto = (ajuste_pto != null && ajuste_pto.compareTo("1") == 0) ? true : false;
        
        String titles[] = {
            "PLACA", "LOCALIZACION / PUNTO CONTROL", "FECHA", "HORA", "NUMERACION", "DISTANCIA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        // Se omite distancia en reporte ipk-consolidado
        int size_cols = (consolidado) ? titles.length - 1 : titles.length;
        for (int i = 0; i < size_cols; i++) {
            String title = titles[i];
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data_gps == null || data_gps.size() == 0) {
            return;
        }
          
        // Consolidado IPK
        double distanciaRecorrida = 0;
        long cantidadPasajeros = 0;
        for (IPK_r ipk : data_gps) {
            if (ipk.getEsIPK()) {
                distanciaRecorrida += ipk.getDistanciaRecorrida();
                cantidadPasajeros += ipk.getCantidadPasajeros();
            }
        }
        double ipk_cons = 0.0;
        if (distanciaRecorrida > 0) {
            ipk_cons = cantidadPasajeros / distanciaRecorrida;
        }

        for (IPK_r ipk : data_gps) {

            // Ajuste punto de control
            String dataPuntoControl = ipk.getLocalizacion();
            if (ajustar_pto) {
                if (ipk.getEsPuntoControl() == 1) {
                    String puntoControl = ipk.getMsg();
                    String arrPuntoControl[] = puntoControl.split("-");
                    if (arrPuntoControl.length >= 3 && arrPuntoControl[2] != "") {
                        dataPuntoControl = "Punto de Control - " + arrPuntoControl[2];
                    }
                }
            } else {
                if (ipk.getEsPuntoControl() == 1) {
                    dataPuntoControl = ipk.getMsg();
                }
            }

            // Ingresamos puntos sean bases, no-bases, o extremos
            Object celldata[] = {
                ipk.getPlaca(), dataPuntoControl, ipk.getFecha(), ipk.getHora(),
                ipk.getNumeracion(), ipk.getDistancia()
            };

            if (consolidado) {
                if (ipk.getEstadoConsolidacion() == 0
                        && !ipk.getEsIPK()) {
                    continue;
                }
            }

            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            for (int i = 0; i < size_cols; i++) {
                Object idata = celldata[i];
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;

                // Se aplica estilo cuando sea punto base o extremo final
                if (ipk.getEsIPK()) {
                    celda.setCellStyle(estiloItalicBold);
                }
            }

            // Se muestra IPK cuando sea punto base o extremo
            if (ipk.getEsIPK()) {
                fila = hoja.createRow(rownum++);
                cellnum = 0;
                hoja.addMergedRegion(new CellRangeAddress(
                        rownum - 1,
                        rownum,
                        0,
                        5));
                String str = "          ["
                        + " Pasajeros: " + ipk.getCantidadPasajeros()
                        + "    Distancia Recorrida KM: " + ipk.getDistanciaRecorrida()
                        + "    IPK: " + String.format("%.2f", ipk.getIPK()) + " ]";

                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, str);
                celda.setCellStyle(alineacionVerticalCentro);
                rownum += 1;
            }
        }

        // Titulo IPK consolidado
        Row fila = hoja.createRow(rownum++);
        hoja.addMergedRegion(new CellRangeAddress(
                rownum - 1,
                rownum - 1,
                0, 5));
        String str = " IPK Consolidado";
        Cell celda = fila.createCell(0);
        setCellValue(celda, str);
        celda.setCellStyle(estiloBold);

        // IPK consolidado
        fila = hoja.createRow(rownum++);
        hoja.addMergedRegion(new CellRangeAddress(
                rownum - 1,
                rownum,
                0,
                5));
        str = "          ["
                + " Total Pasajeros: " + cantidadPasajeros
                + "    Total Distancia Recorrida KM: " + String.format("%.2f", distanciaRecorrida)
                + "    IPK: " + String.format("%.2f", ipk_cons) + " ]";
        celda = fila.createCell(0);
        setCellValue(celda, str);
        celda.setCellStyle(alineacionVerticalCentro);
        rownum += 1;        

        rellenarHoja(hoja, rownum, 6);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_HistoricoGPS(String titulo, ArrayList<DatosGPS> data_gps, boolean vehiculo_sel, Entorno entorno) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String mostrar_es   = entorno.obtenerPropiedad(Constant.MOSTRAR_ENTRADA_SALIDA);
        String mostrar_no   = entorno.obtenerPropiedad(Constant.MOSTRAR_NIVEL_OCUPACION);
        String mostrar_noic = entorno.obtenerPropiedad(Constant.MOSTRAR_NOIC);
        String ajuste_hs    = entorno.obtenerPropiedad(Constant.AJUSTE_HORA_SERVIDOR);
        String ajuste_pto   = entorno.obtenerPropiedad(Constant.AJUSTE_PTO_CONTROL);
        Map<String, Movil> hmovil = MovilBD.selectMap();
        
        boolean ajustar_hs  = (ajuste_hs != null && ajuste_hs.compareTo("0") != 0) ? true : false;
        boolean ajustar_pto = (ajuste_pto != null && ajuste_pto.compareTo("1") == 0) ? true : false;

        String titles[] = {
            "FECHA SERVIDOR", "FECHA GPS", "PLACA", "LOCALIZACION", "EVENTO / PUNTO",
            "ALARMA", "NUMERACION", "TOTAL DIA", "ENTRADAS", "SALIDAS", "NIVEL DE OCUPACION",
            "OCUPACION / CAPACIDAD", "RUMBO", "VELOCIDAD", "DISTANCIA EN METROS", "NOMBRE FLOTA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        Row row = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celda = row.createCell(cellnum++);
            celda.setCellValue(title);
            celda.setCellStyle(estiloTitulo);
        }
        
        if (data_gps == null || data_gps.size() == 0) {
            return;
        }

        for (int i = 0; i < data_gps.size(); i++) {
            DatosGPS gps = data_gps.get(i);
            String placa = gps.getPlaca().toUpperCase();

            Movil m = hmovil.get(placa);
            int capacidad = 0;
            if (m != null) {
                capacidad = m.getCapacidad();
            }

            int nivel_ocupacion = gps.getEntradas() - gps.getSalidas();
            if (nivel_ocupacion < 0) {
                nivel_ocupacion = 0;
            }
            String noic = nivel_ocupacion + " / " + capacidad;

            String fechaHoraGps = ffmt.format(gps.getFechaGPSDate()) + " " + hfmt.format(gps.getFechaGPS());
            String fechaHoraServidor = ffmt.format(gps.getFechaServidorDate()) + " " + hfmt.format(gps.getFechaServidor());
            
            // Ajuste de fecha servidor
            if (ajustar_hs) {
                int hora_ajuste = Restriction.getNumber(ajuste_hs) * 60;
                Date fechaservidor  = TimeUtil.getDateTime(fechaHoraServidor);
                Date nfechaservidor = TimeUtil.ajusteFechaHora(fechaservidor, hora_ajuste);
                fechaHoraServidor = TimeUtil.getStrDateTime(nfechaservidor);
            }
            // Ajuste de punto de control
            String dataPuntoControl = gps.getMsg();
            String dataLocalizacion = gps.getLocalizacion_proc();
            if (ajustar_pto) {
                if (gps.getEsPuntoControl() == 1) {
                    String arrPuntoControl[] = dataPuntoControl.split("-");
                    if (arrPuntoControl.length >= 3 && arrPuntoControl[2] != "") {
                        dataPuntoControl = "Punto de Control - " + arrPuntoControl[2];
                        dataLocalizacion = dataPuntoControl;
                    }
                }
            }

            Object celldata[] = {
                fechaHoraServidor, fechaHoraGps, gps.getPlaca(), dataLocalizacion,
                dataPuntoControl, gps.getAlarma(), gps.getNumeracion(), gps.getTotalDia(), gps.getEntradas(), gps.getSalidas(),
                nivel_ocupacion, noic, gps.getRumbo(), gps.getVelocidad(), gps.getDistanciaMetros(), gps.getNombreFlota()
            };

            cellnum = 0;
            row = hoja.createRow(rownum++);
            for (Object cdata : celldata) {
                Cell celda = row.createCell(cellnum++);
                setCellValue(celda, cdata);
                if (cellnum >= 6) {
                    celda.setCellStyle(alineacionDerecha);
                }
                if (cellnum-1 == 3 || cellnum-1 == 4) {
                    if (gps.getEsPuntoControl() == 1) {
                        celda.setCellStyle(estiloBold);
                    }
                }
            }
        }

        // C8 entradas
        // C9 salidas
        // C10 nivel ocupacion
        // C11 noic
        ArrayList<Integer> hidden_cols = new ArrayList<Integer>();

        if (mostrar_es == null || mostrar_es.compareTo("0") == 0) {
            hidden_cols.add(8);
            hidden_cols.add(9);
        }
        if (mostrar_no == null || mostrar_no.compareTo("0") == 0) {
            hidden_cols.add(10);
        }
        if (mostrar_noic == null || mostrar_noic.compareTo("0") == 0) {
            hidden_cols.add(11);
        }

        for (int i = 0; i < hidden_cols.size(); i++) {
            int hidden_col = hidden_cols.get(i);
            for (int j = 0; j < rownum; j++) {
                Row rowdata = hoja.getRow(j);
                Cell celldata = rowdata.getCell(hidden_col);
                rowdata.removeCell(celldata);
                hoja.setColumnWidth(hidden_col, 0);
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_IPK_GPS_Resumen(String titulo, ArrayList<IPK_r> data_gps) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "FECHA", "HORA LLEGADA", "HORA SALIDA", "CANTIDAD PASAJEROS", "DISTANCIA RECORRIDA KM", "IPK"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data_gps == null || data_gps.size() == 0) {
            return;
        }

        // Consolidado IPK
        double distanciaRecorrida = 0;
        long cantidadPasajeros = 0;
        for (IPK_r ipk : data_gps) {
            if (ipk.getEsIPK()) {
                distanciaRecorrida += ipk.getDistanciaRecorrida();
                cantidadPasajeros += ipk.getCantidadPasajeros();
            }
        }
        double ipk_cons = 0.0;
        if (distanciaRecorrida > 0) {
            ipk_cons = cantidadPasajeros / distanciaRecorrida;
        }

        Row fila;
        for (int i = 0; i < data_gps.size(); i++) {

            IPK_r ipk = data_gps.get(i);

            if (i == 0 || ipk.getEsIPK()) {
                IPK_r ipk_fin = baseSiguiente(data_gps, i);
                fila = hoja.createRow(rownum++);
                cellnum = 0;

                if (ipk_fin != null) {
                    String horaIngreso = (ipk_fin == null)
                            ? "-"
                            : "" + ipk_fin.getHora();

                    Object celldata[] = {
                        ipk.getPlaca(), ipk.getFecha(), ipk.getHora(), horaIngreso, ipk_fin.getCantidadPasajeros(),
                        ipk_fin.getDistanciaRecorrida(), String.format("%.2f", ipk_fin.getIPK())
                    };

                    for (Object idata : celldata) {
                        Cell celda = fila.createCell(cellnum);
                        setCellValue(celda, idata);
                        cellnum++;
                    }
                } else {
                    rownum -= 1;
                }
            }
        }

        // IPK consolidado
        fila = hoja.createRow(rownum++);
        hoja.addMergedRegion(new CellRangeAddress(
                rownum - 1,
                rownum,
                0,
                6));
        String str = "          ["
                + " Total Pasajeros: " + cantidadPasajeros
                + "    Total Distancia Recorrida KM: " + String.format("%.2f", distanciaRecorrida)
                + "    IPK: " + String.format("%.2f", ipk_cons) + " ]";
        Cell celda = fila.createCell(0);
        setCellValue(celda, str);
        celda.setCellStyle(alineacionVerticalCentro);
        rownum += 1;

        rellenarHoja(hoja, rownum, 6);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public IPK_r baseSiguiente(ArrayList<IPK_r> data_gps, int pos) {
        IPK_r ipk = null;
        for (int i = pos + 1; i < data_gps.size(); i++) {
            ipk = data_gps.get(i);
            if (ipk.getEsIPK()) {
                return ipk;
            }
        }
        return ipk;
    }

    public void reporte_PuntosControl_GPS(String titulo, ArrayList<PuntosControl> data_gps) {
        HSSFSheet hoja = libro.createSheet(titulo + " GPS");

        String titles[] = {
            "PLACA", "PUNTO CONTROL", "FECHA", "HORA", "NUMERACION", "CANTIDAD PASAJEROS"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data_gps != null && data_gps.size() > 0) {
            for (PuntosControl pc : data_gps) {
                Row fila = hoja.createRow(rownum++);
                cellnum = 0;
                Object celldata[] = {
                    pc.getPlaca(), pc.getNombrePunto(), pc.getFecha(), pc.getHora(),
                    pc.getNumeracion(), (pc.getNumeracion() - pc.getNumeracionInicial())
                };

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;

                    // Se aplica estilo a bases
                    if (pc.getEsBase()) {
                        celda.setCellStyle(estiloItalicBold);
                    }
                }
            }
        }
        
        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_PuntosControl(String titulo, ArrayList<PuntosControl> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "PUNTO CONTROL", "FECHA", "HORA",
            "NUMERACION", "CANTIDAD PASAJEROS", "ENTRADAS", "SALIDAS", "CANT. ALARMAS BLOQUEO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (PuntosControl pc : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            int cantidad_pasajeros = (pc.getNumeracion() - pc.getNumeracionInicial());
            cantidad_pasajeros = (cantidad_pasajeros < 0) ? 0 : cantidad_pasajeros;

            Object celldata[] = {
                pc.getPlaca(), pc.getNumeroInterno(), pc.getNombrePunto(),
                pc.getFecha(), pc.getHora(), pc.getNumeracion(),
                cantidad_pasajeros,
                pc.getEntradas(), pc.getSalidas(),
                pc.getCantidadAlarmas()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;

                // Marcamos las bases
                if (pc.getTipoPunto() == 1 || pc.getTipoPunto() == 3) {
                    celda.setCellStyle(estiloItalicBold);
                }
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_VehiculosXAlarma(String titulo, ArrayList<VehiculoXAlarma> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA", "HORA", "RUTA", "ALARMA",
            "CANTIDAD", "UNIDAD DE MEDICION"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (VehiculoXAlarma v : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            String alarma = v.getCodigo() + " - " + v.getAlarma();
            Object celldata[] = {
                v.getPlaca(), v.getNumeroInterno(), v.getFecha(), v.getHora(),
                v.getNombreRuta(), alarma, v.getCantidad(), v.getUnidadMedicion()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ConsolidadoXEmpresa(String titulo, ArrayList<ConsolidadoXEmpresa> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "EMPRESA", "PLACA", "N° INTERNO", "FECHA", "CANTIDAD DE VUELTAS",
            "TOTAL PASAJEROS", "CONTEOS EN PERIMETRO", "CANT. ALARMAS BLOQUEO", "PROMEDIO PASAJEROS POR VUELTA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ConsolidadoXEmpresa c : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                c.getEmpresa(), c.getPlaca(), c.getNumeroInterno(), c.getFecha(), c.getCantidadVueltas(),
                c.getCantidadPasajeros(), c.getConteoPerimetro(), c.getConteoAlarmas(),
                c.getPromedioPasajeros()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ComparativoProduccionRuta(String titulo, ArrayList<ComparativoProduccionRuta> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "FECHA", "RUTA", "CANTIDAD PASAJEROS"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ComparativoProduccionRuta c : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                c.getFecha(), c.getNombreRuta(), c.getCantidadPasajeros()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_NivelOcupacion(String titulo, ArrayList<NivelOcupacion> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "N° VUELTA", "FECHA", "HORA",
            "RUTA", "PUNTO CONTROL", "NIVEL OCUPACION", "ICU %"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (NivelOcupacion n : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                n.getPlaca(), n.getNumeroInterno(), n.getNumeroVuelta(),
                n.getFecha(), n.getHora(), n.getNombreRuta(), n.getNombrePunto(),
                n.getNivelOcupacion(), n.getIcu()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;

                if (n.esBase()) {
                    celda.setCellStyle(estiloItalicBold);
                }
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ConteoPerimetro(String titulo, ArrayList<ConteoPerimetro_r> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "N° VUELTA", "FECHA", "HORA",
            "RUTA", "CANTIDAD PASAJEROS", "CONTEO PERIMETRO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ConteoPerimetro_r c : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                c.getPlaca(), c.getNumeroInterno(), c.getNumeroVuelta(), c.getFecha(),
                c.getHora(), c.getNombreRuta(), c.getCantidadPasajeros(), c.getConteoPerimetro()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    /* >>> Informacion para reporte estadistico <<< */
    public void reporte_Estadistico_RE(ArrayList<RutaEstablecida> data) {
        HSSFSheet hoja = libro.createSheet("RUTAS ESTABLECIDAS");

        String titles[] = {
            "RUTA", "ESTADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }
        
        for (RutaEstablecida r : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String estadoCreacion
                    = (r.getEstadoCreacion() == 1)
                            ? "Sin puntos de control"
                            : "Ruta Definida";

            Object celldata[] = {
                r.getNombreRuta(), estadoCreacion
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_Estadistico_VE(ArrayList<VehiculoEstablecido> data) {
        HSSFSheet hoja = libro.createSheet("VEHICULOS ESTABLECIDOS");

        String titles[] = {
            "PLACA", "N° INTERNO", "EMPRESA", "FECHA ULTIMA VUELTA", "DIAS ULTIMA VUELTA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (VehiculoEstablecido v : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String fechaUV, diasUV;

            fechaUV = (v.getFechaUltimaVuelta() == null)
                    ? "No reportado"
                    : v.getFechaUltimaVuelta().toString();

            diasUV = (v.getDiasUltimaVuelta() < 0)
                    ? "No reportado"
                    : "" + v.getDiasUltimaVuelta();

            Object celldata[] = {
                v.getPlaca(), v.getNumeroInterno(), v.getNombreEmpresa(),
                fechaUV, diasUV
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_Estadistico_CE(ArrayList<ConductorEstablecido> data) {
        HSSFSheet hoja = libro.createSheet("CONDUCTORES ESTABLECIDOS");

        String titles[] = {
            "NOMBRE", "APELLIDO", "CEDULA", "EMPRESA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ConductorEstablecido c : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                c.getNombre(), c.getApellido(), c.getCedula(), c.getNombreEmpresa()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_Estadistico_CV(ArrayList<ConductorXVehiculo> data) {
        HSSFSheet hoja = libro.createSheet("CONDUCTORES POR VEHICULO");

        String titles[] = {
            "PLACA", "N° INTERNO", "CONDUCTOR", "FECHA DE ASIGNACION"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (ConductorXVehiculo c : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                c.getPlaca(), c.getNumeroInterno(), c.getNombreConductor(),
                c.getFechaHoraAsignacion()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_DescripcionRuta(String titulo, ArrayList<DescripcionRuta> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "RUTA", "PUNTO", "MINUTOS PROMEDIO", "MINUTOS HOLGURA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }
        
        for (DescripcionRuta r : data) {

            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                r.getNombreRuta(), r.getNombrePunto(), r.getMinutosPromedio(),
                r.getMinutosHolgura()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);

                if (r.getTipoPunto() == 1) {
                    setCellValue(celda, idata);
                    cellnum++;
                } else {
                    if (cellnum > 0) {
                        setCellValue(celda, idata);
                    }
                    cellnum++;
                }

                //setCellValue(celda, idata);
                //cellnum++;
                if (r.getTipoPunto() == 1
                        || r.getTipoPunto() == 3) {
                    if (cellnum - 1 == 1) {
                        celda.setCellStyle(estiloItalicBold);
                    }
                }
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_VehiculosXRuta(String titulo, ArrayList<VehiculoXRuta> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "N° INTERNO", "PLACA", "N° VUELTA", "FECHA", "RUTA", "CONDUCTOR",
            "HORA SALIDA", "HORA LLEGADA PLANEADA", "HORA REAL LLEGADA",
            "DIFERENCIA (HH:MM:SS)", "ESTADO", "TOTAL PASAJEROS"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        VehiculoXRuta v_prev = null;
        CellStyle estiloFila = null;

        for (VehiculoXRuta v : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String signo, sestado;
            CellStyle cestado;
            switch (v.getEstado()) {
                case 0:
                    signo = "";
                    sestado = "A tiempo";
                    cestado = estiloVerde;
                    break;
                case 1:
                    signo = "+";
                    sestado = "Atrasado";
                    cestado = estiloRojo;
                    break;
                case 2:
                    signo = "-";
                    sestado = "Adelantado";
                    cestado = estiloNaranja;
                    break;
                default:
                    signo = "";
                    sestado = "-";
                    cestado = null;
            }

            String diferencia = signo + v.getDiferencia();
            String conductor = v.getNombreConductor();
            int pasajeros = v.getCantidadPasajeros();
            String spasajeros = (pasajeros == -1) ? "-" : "" + pasajeros;
            if (conductor == null || conductor == "") {
                conductor = "NA";
            }

            Object celldata[] = {
                v.getNumeroInterno(), v.getPlaca(), v.getNumeroVuelta(), v.getFechaStr(),
                v.getNombreRuta(), conductor,
                v.getHoraSalida(), v.getHoraLlegadaPlaneada(), v.getHoraLlegada(),
                diferencia, spasajeros, sestado
            };

            if (v_prev == null) {
                estiloFila = null;
            } else if (v_prev.getPlaca().compareTo(v.getPlaca()) != 0) {
                estiloFila = (estiloFila == null) ? this.estiloFila3 : null;
            }
            v_prev = v;

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
                if (estiloFila != null) {
                    celda.setCellStyle(estiloFila);
                }
                if (cellnum - 1 == 11) {
                    celda.setCellStyle(cestado);
                }
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_Despachador(String titulo, ArrayList<Despachador> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "N° VUELTA", "RUTA", "FECHA", "HORA SALIDA",
            "HORA LLEGADA", "PUNTO", "EN VUELTA", "ADELANTADO", "NUMERACION",
            "PASAJEROS POR PUNTO", "CONDUCTOR"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        Despachador d, d2;
        int pasajerosPorPunto, numa, numb;
        d = d2 = null;
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (int i = 0; i < data.size(); i++) {

            if (i > 0) {
                d = data.get(i - 1);
                d2 = data.get(i);
                if (d.getEsBase() && d2.getEsBase()) {
                    d = data.get(i);
                    pasajerosPorPunto = 0;
                } else {
                    numa = d.getCantidadPasajeros();
                    numb = d2.getCantidadPasajeros();
                    pasajerosPorPunto
                            = (numa > 0 && numb > 0) ? numb - numa : 0;
                    d = d2;
                }
            } else {
                d = data.get(i);
                pasajerosPorPunto = 0;
            }

            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String enVuelta = (d.esPuntoEnVuelta()) ? "si" : "no";
            String adelantado = (d.estaAdelantado()) ? "+" : "-";

            Object celldata[] = {
                d.getPlaca(), d.getNumeroInterno(), d.getNumeroVuelta(),
                d.getNombreRuta(), d.getFecha(), d.getHoraSalida(), d.getHoraLlegada(),
                d.getNombrePunto(), enVuelta, adelantado, d.getCantidadPasajeros(),
                pasajerosPorPunto, d.getNombreConductor()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;

                if (d.getEsBase()) {
                    celda.setCellStyle(estiloItalicBold);
                }
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_GerenciaXVehiculo(
            String titulo,
            ArrayList<GerenciaXVehiculo> data) {

        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA LIQ.", "HORA", "RUTA", "N° VUELTAS",
            "CANTIDAD PASAJEROS", "PASAJEROS DESCONTADOS", "PASAJEROS LIQUIDADOS",
            "TOTAL", "DTO. OPERATIVO", "DTO. ADMINISTRATIVO", "TOTAL LIQUIDADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        for (GerenciaXVehiculo g : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String fecha_liq = ffmt.format(g.getFecha());
            String hora_liq = hfmt.format(new Date(g.getHora().getTime()));
            Object celldata[] = {
                g.getPlaca(), g.getNumeroInterno(), fecha_liq, hora_liq, g.getNombreRuta(),
                g.getCantidadVueltas(), g.getCantidadPasajeros(), g.getPasajerosDescontados(),
                g.getPasajerosLiquidados(), g.getValorBruto(), g.getValorDescuentoOperativo(),
                g.getValorDescuentoAdministrativo(), g.getValorLiquidado()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_Gerencia(
            String titulo,
            ArrayList<Gerencia> data) {

        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA LIQ.", "HORA", "RUTA", "N° VUELTAS",
            "CANTIDAD PASAJEROS", "PASAJEROS DESCONTADOS", "PASAJEROS LIQUIDADOS",
            "TOTAL", "DTO. OPERATIVO", "DTO. ADMINISTRATIVO", "TOTAL LIQUIDADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }
        
        for (Gerencia g : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String fecha_liq = ffmt.format(g.getFecha());
            String hora_liq = hfmt.format(new Date(g.getHora().getTime()));
            Object celldata[] = {
                g.getPlaca(), g.getNumeroInterno(), fecha_liq, hora_liq, g.getNombreRuta(),
                g.getCantidadVueltas(), g.getCantidadPasajeros(), g.getPasajerosDescontados(),
                g.getPasajerosLiquidados(), g.getValorBruto(), g.getValorDescuentoOperativo(),
                g.getValorDescuentoAdministrativo(), g.getValorLiquidado()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Reportes gerencia Fusa
    ////////////////////////////////////////////////////////////////////////////
    public void reporte_GerenciaXVehiculoFusa(
            String titulo,
            ArrayList<GerenciaXVehiculo> data) {

        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA LIQ.", "HORA", "RUTA", "N° VUELTAS",
            "CANTIDAD PASAJEROS", "PASAJEROS LIQUIDADOS", "SUBTOTAL", "TOTAL",
            "DTO. OPERATIVO", "DTO. ADMINISTRATIVO", "TOTAL LIQUIDADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        double ptj_desc = ReportesBD.valorDescuentoCategoria("tolerancia");

        for (GerenciaXVehiculo g : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String fecha_liq = ffmt.format(g.getFecha());
            String hora_liq = hfmt.format(new Date(g.getHora().getTime()));

            int brutoDescuento = (int) (g.getValorBruto() * ptj_desc);
            int totalBrutoDescuento = g.getValorBruto() - brutoDescuento;
            int valorLiquidado
                    = g.getValorBruto()
                    - brutoDescuento
                    - g.getValorDescuentoOperativo()
                    - g.getValorDescuentoAdministrativo();

            Object celldata[] = {
                g.getPlaca(), g.getNumeroInterno(), fecha_liq, hora_liq, g.getNombreRuta(),
                g.getCantidadVueltas(), g.getCantidadPasajeros(),
                g.getPasajerosLiquidados(), g.getValorBruto(), totalBrutoDescuento, g.getValorDescuentoOperativo(),
                g.getValorDescuentoAdministrativo(), valorLiquidado
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_GerenciaFusa(
            String titulo,
            ArrayList<Gerencia> data) {

        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "PLACA", "N° INTERNO", "FECHA LIQ.", "HORA", "RUTA", "N° VUELTAS",
            "CANTIDAD PASAJEROS", "PASAJEROS LIQUIDADOS", "SUBTOTAL", "TOTAL",
            "DTO. OPERATIVO", "DTO. ADMINISTRATIVO", "TOTAL LIQUIDADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        if (data == null || data.size() == 0) {
            return;
        }
        
        double ptj_desc = ReportesBD.valorDescuentoCategoria("tolerancia");

        for (Gerencia g : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            int brutoDescuento = (int) (g.getValorBruto() * ptj_desc);
            int totalBrutoDescuento = g.getValorBruto() - brutoDescuento;
            int valorLiquidado
                    = g.getValorBruto()
                    - brutoDescuento
                    - g.getValorDescuentoOperativo()
                    - g.getValorDescuentoAdministrativo();

            String fecha_liq = ffmt.format(g.getFecha());
            String hora_liq = hfmt.format(new Date(g.getHora().getTime()));
            Object celldata[] = {
                g.getPlaca(), g.getNumeroInterno(), fecha_liq, hora_liq, g.getNombreRuta(),
                g.getCantidadVueltas(), g.getCantidadPasajeros(), g.getPasajerosLiquidados(),
                g.getValorBruto(), totalBrutoDescuento, g.getValorDescuentoOperativo(),
                g.getValorDescuentoAdministrativo(), valorLiquidado
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_RutaXVehiculo(String titulo, ArrayList<ArrayList<RutaXVehiculo>> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "FECHA", "N° VUELTA", "RUTA", "PLACA", "N° INTERNO", "PUNTO CONTROL", "NUMERACION", "HORA PLANIFICADA", "HORA REAL DE LLEGADA",
            "MINUTOS HOLGURA", "DIFERENCIA", "ESTADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (int i = 0; i < data.size(); i++) {
            ArrayList<RutaXVehiculo> data_in = data.get(i);

            for (RutaXVehiculo r : data_in) {
                Row fila = hoja.createRow(rownum++);
                cellnum = 0;

                String signo = (r.getEstado() == 0) ? ""
                        : (r.getEstado() == 1) ? "+" : "-";
                String diferenciaTiempo = signo + r.getDiferenciaTiempo();

                if (r.getIdPunto() == -1) // para base salida, se quita -
                {
                    diferenciaTiempo = "" + r.getDiferenciaTiempo();
                }

                String estado;
                CellStyle cestado;
                if (r.getHoraRealLlegada() == null) {
                    estado = "-";
                    cestado = estiloGris;
                } else if (r.getEstado() == 0) {
                    estado = "A tiempo";
                    cestado = estiloVerde;
                } else if (r.getEstado() == 1) {
                    estado = "Atrasado";
                    cestado = estiloRojo;
                } else {
                    estado = "Adelantado";
                    cestado = estiloNaranja;
                }

                Object celldata[];
                if (r.getIdPunto() == -3) { // punto no encontrado en ruta
                    Object tmpdata[] = {
                        r.getFecha(), r.getNumeroVuelta(), r.getRuta(), r.getPlaca(), r.getNumeroInterno(), r.getPuntoControl(), "-", "-", "-", "-", "-", "-"
                    };
                    celldata = tmpdata;
                } else {
                    Object tmpdata[] = {
                        r.getFecha(), r.getNumeroVuelta(), r.getRuta(), r.getPlaca(), r.getNumeroInterno(), r.getPuntoControl(),
                        r.getNumeracion(), r.getHoraPlanificada(), r.getHoraRealLlegada(), r.getMinutosHolgura(),
                        diferenciaTiempo, estado
                    };
                    celldata = tmpdata;
                }

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;

                    // Marcamos las bases
                    if (r.getIdPunto() == -1 || r.getIdPunto() == -2) {
                        celda.setCellStyle(estiloItalicBold);
                    }

                    if (cellnum - 1 == 11) {
                        celda.setCellStyle(cestado);
                    }
                }
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_RutaXVehiculoDph(String titulo, ArrayList<RutaXVehiculoDph> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "FECHA", "N° VUELTA", "RUTA", "PLACA", "N° INTERNO", "PUNTO CONTROL", "HORA PLANIFICADA", "HORA REAL DE LLEGADA",
            "MINUTOS HOLGURA", "DIFERENCIA", "ESTADO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (RutaXVehiculoDph r : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            String hora_llegada = r.getHoraRealLlegada();
            String estado;
            CellStyle cestado;
            if (hora_llegada == null || hora_llegada == "" || hora_llegada.compareTo("-") == 0) {
                estado = "-";
                cestado = estiloGris;
            } else {
                long diferencia = r.getDiferencia();
                if (diferencia == 0) {
                    estado = "A tiempo";
                    cestado = estiloVerde;
                } else if (diferencia > 0) {
                    estado = "Atrasado";
                    cestado = estiloRojo;
                } else {
                    estado = "Adelantado";
                    cestado = estiloNaranja;
                }
            }

            Object celldata[] = {
                r.getFecha(), r.getNumeroVuelta(), r.getNombreRuta(), r.getPlaca(), r.getNumeroInterno(),
                r.getNombrePunto(), r.getHoraPlanificada(), r.getHoraRealLlegada(),
                r.getMinutosHolgura(), r.getDiferenciaTiempo(), estado
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;

                // Marcamos las bases
                if (r.getTipoPunto() == 1 || r.getTipoPunto() == 3) {
                    celda.setCellStyle(estiloItalicBold);
                }
                if (cellnum - 1 == 10) {
                    celda.setCellStyle(cestado);
                }
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_Propietario(String titulo, ArrayList<Propietario_r> data) {
        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {
            "N° VUELTA", "PLACA", "N° INTERNO", "FECHA", "HORA SALIDA", "HORA LLEGADA", "RUTA",
            "CANTIDAD PASAJEROS", "TIEMPO VUELTA", "CONTEO PERIMETRO"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }                
        
        for (Propietario_r p : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            Object celldata[] = {
                p.getNumeroVuelta(), p.getPlaca(), p.getNumeroInterno(), p.getFecha(),
                p.getHoraSalida(), p.getHoraLlegada(), p.getNombreRuta(),
                p.getCantidadPasajeros(), p.getTiempoVuelta(), p.getConteoPerimetro()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }        

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void inicio_CumplimientoRuta(HSSFSheet hoja, ParametrosReporte pr) {

        Row f1 = hoja.createRow(0);
        Row f2 = hoja.createRow(1);
        Row f3 = hoja.createRow(2);
        String ruta = pr.getNombreRuta();
        ruta = (ruta == null || ruta == "") ? "Todas las rutas" : ruta;
        Cell c11 = f1.createCell(0);
        c11.setCellValue("Empresa");
        Cell c12 = f1.createCell(1);
        c12.setCellValue(pr.getNombreEmpresa());
        Cell c21 = f2.createCell(0);
        c21.setCellValue("Ruta");
        Cell c22 = f2.createCell(1);
        c22.setCellValue(ruta);
        Cell c23 = f2.createCell(2);
        c23.setCellValue("Meta (%)");
        Cell c24 = f2.createCell(3);
        c24.setCellValue("" + pr.getMeta());
        Cell c31 = f3.createCell(0);
        c31.setCellValue("Fecha inicial");
        Cell c32 = f3.createCell(1);
        c32.setCellValue(pr.getFechaInicioStr());
        Cell c33 = f3.createCell(2);
        c33.setCellValue("Fecha final");
        Cell c34 = f3.createCell(3);
        c34.setCellValue(pr.getFechaFinalStr());
        c11.setCellStyle(estiloTitulo);
        c21.setCellStyle(estiloTitulo);
        c23.setCellStyle(estiloTitulo);
        c31.setCellStyle(estiloTitulo);
        c33.setCellStyle(estiloTitulo);
    }

    public void reporte_CumplimientoRutaXVehiculo(String titulo, ArrayList<CumplimientoRuta> data, ParametrosReporte pr) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int rownum, cellnum;
        rownum = cellnum = 0;

        // Datos iniciales
        inicio_CumplimientoRuta(hoja, pr);
        rownum = 4;

        String titles[] = {
            "VEHICULO", "CONDUCTOR(ES)", "PUNTOS REALIZADOS", "PUNTOS CUMPLIDOS", "PORCENTAJE CUMPLIDO (%)", "META (%)"
        };

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        int total_puntosRealizados = 0,
            total_puntosCumplidos = 0;
        
        if (data == null || data.size() == 0) {
            return;
        }
        
        for (CumplimientoRuta cr : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            int porcentaje_cumplido = (int) (cr.getPorcentajeCumplido() * 100.0);
            total_puntosRealizados += cr.getPuntosRealizados();
            total_puntosCumplidos += cr.getPuntosCumplidos();
            String conductores = cr.getNombreConductores();
            conductores = (conductores == null || conductores == "") ? "Sin asignar" : conductores;

            Object celldata[] = {
                cr.getPlaca(), conductores, cr.getPuntosRealizados(), cr.getPuntosCumplidos(),
                porcentaje_cumplido, pr.getMeta()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);

                // Meta no alcanzada
                if (cellnum == 4
                        && porcentaje_cumplido < pr.getMeta()) {
                    celda.setCellStyle(estiloError);
                }
                setCellValue(celda, idata);
                cellnum++;
            }
        }

        // Totales
        int total_vehiculos = data.size();
        int puntos_realizados = (total_puntosRealizados == 0) ? 1 : total_puntosRealizados;
        double total_porcentaje = ((double) total_puntosCumplidos / (double) puntos_realizados) * 100.0;
        Row ff = hoja.createRow(rownum++);
        Cell cf1 = ff.createCell(1);
        cf1.setCellValue(total_vehiculos + " Vehículos");
        Cell cf2 = ff.createCell(2);
        cf2.setCellValue(total_puntosRealizados);
        Cell cf3 = ff.createCell(3);
        cf3.setCellValue(total_puntosCumplidos);
        Cell cf4 = ff.createCell(4);
        cf4.setCellValue(total_porcentaje);
        cf1.setCellStyle(estiloBold);
        cf2.setCellStyle(estiloBold);
        cf3.setCellStyle(estiloBold);
        cf4.setCellStyle(estiloBold);

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_CumplimientoRutaConsolidado(String titulo, ArrayList<CumplimientoRuta> data, ParametrosReporte pr) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int rownum, cellnum;
        rownum = cellnum = 0;

        // Datos iniciales
        inicio_CumplimientoRuta(hoja, pr);
        rownum = 4;

        String titles[] = {
            "RUTA", "PORCENTAJE CUMPLIDO (%)", "META (%)"
        };

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        int total_puntosRealizados = 0,
            total_puntosCumplidos = 0;
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (CumplimientoRuta cr : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            int porcentaje_cumplido = (int) (cr.getPorcentajeCumplido() * 100.0);
            total_puntosRealizados += cr.getPuntosRealizados();
            total_puntosCumplidos += cr.getPuntosCumplidos();

            Object celldata[] = {
                cr.getNombreRuta(), porcentaje_cumplido, pr.getMeta()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);

                // Meta no alcanzada
                if (cellnum == 1
                        && porcentaje_cumplido < pr.getMeta()) {
                    celda.setCellStyle(estiloError);
                }
                setCellValue(celda, idata);
                cellnum++;
            }
        }

        // Totales
        int total_rutas = data.size();
        int puntos_realizados = (total_puntosRealizados == 0) ? 1 : total_puntosRealizados;
        double total_porcentaje = ((double) total_puntosCumplidos / (double) puntos_realizados) * 100.0;
        Row ff = hoja.createRow(rownum++);
        Cell cf1 = ff.createCell(0);
        cf1.setCellValue(total_rutas + " Rutas");
        Cell cf2 = ff.createCell(1);
        cf2.setCellValue(total_porcentaje);
        cf1.setCellStyle(estiloBold);
        cf2.setCellStyle(estiloBold);

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_CumplimientoRutaXConductor(String titulo, ArrayList<CumplimientoRuta> data, ParametrosReporte pr) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int rownum, cellnum;
        rownum = cellnum = 0;

        // Datos iniciales
        inicio_CumplimientoRuta(hoja, pr);
        rownum = 4;

        String titles[] = {
            "CONDUCTOR", "PUNTOS REALIZADOS", "PUNTOS CUMPLIDOS", "PORCENTAJE CUMPLIDO (%)", "META (%)"
        };

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        int total_puntosCumplidos = 0, total_puntosRealizados = 0;      

        for (CumplimientoRuta cr : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            int porcentaje_cumplido = (int) (cr.getPorcentajeCumplido() * 100.0);
            total_puntosCumplidos += cr.getPuntosCumplidos();
            total_puntosRealizados += cr.getPuntosRealizados();

            String nombre_conductor = cr.getNombreConductores();
            Object celldata[] = {
                nombre_conductor, cr.getPuntosRealizados(), cr.getPuntosCumplidos(),
                cr.getPorcentajeCumplido(), pr.getMeta()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);

                // Meta no alcanzada
                if (cellnum == 3
                        && porcentaje_cumplido < pr.getMeta()) {
                    celda.setCellStyle(estiloError);
                }
                setCellValue(celda, idata);
                cellnum++;
            }
        }

        int total_conductores = data.size();
        int puntos_realizados = (total_puntosRealizados == 0) ? 1 : total_puntosRealizados;
        double total_porcentaje = ((double) total_puntosCumplidos / (double) puntos_realizados) * 100.0;
        Row ff = hoja.createRow(rownum++);
        Cell cf0 = ff.createCell(0);
        cf0.setCellValue(total_conductores + " Conductores");
        Cell cf1 = ff.createCell(1);
        cf1.setCellValue(total_puntosRealizados);
        Cell cf2 = ff.createCell(2);
        cf2.setCellValue(total_puntosCumplidos);
        Cell cf3 = ff.createCell(3);
        cf3.setCellValue(total_porcentaje);
        cf0.setCellStyle(estiloBold);
        cf1.setCellStyle(estiloBold);
        cf2.setCellStyle(estiloBold);
        cf3.setCellStyle(estiloBold);

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public int inicio_ConsolidadoDespacho(HSSFSheet hoja, ParametrosReporte pr) {
        int rownum = 0;
        Row f1 = hoja.createRow(rownum++);
        Cell c11 = f1.createCell(0);
        c11.setCellValue("Ruta");
        Cell c12 = f1.createCell(1);
        c12.setCellValue(pr.getNombreRuta());
        Row f2 = hoja.createRow(rownum++);
        Cell c21 = f2.createCell(0);
        c21.setCellValue("Fecha Inicio");
        Cell c22 = f2.createCell(1);
        c22.setCellValue(pr.getFechaInicioStr());
        Cell c23 = f2.createCell(2);
        c23.setCellValue("Fecha Final");
        Cell c24 = f2.createCell(3);
        c24.setCellValue(pr.getFechaFinalStr());
        c11.setCellStyle(estiloTitulo);
        c21.setCellStyle(estiloTitulo);
        c23.setCellStyle(estiloTitulo);
        return rownum;
    }

    public void reporte_ConsolidadoDespacho(String titulo, ArrayList<Planilla> lst_pll, ArrayList<String> lst_pto, ParametrosReporte pr) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int rownum, cellnum;
        rownum = cellnum = 0;

        rownum = inicio_ConsolidadoDespacho(hoja, pr);

        String titles[] = {"N° vuelta", "Fecha", "Placa", "N° interno"};

        // Titulos estaticos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (lst_pll == null || lst_pll.size() == 0 ||
            lst_pto == null || lst_pto.size() == 0) {
            return;
        }
        
        // Puntos como titulos
        for (String pto_title : lst_pto) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(pto_title);
            celdacab.setCellStyle(estiloTitulo);
        }      

        Planilla pll_prev = null;
        CellStyle estiloFila = null;
        cellnum = 0;

        for (Planilla pll : lst_pll) {
            Row fila = hoja.createRow(rownum++);
            Cell celda_1 = fila.createCell(0);
            celda_1.setCellValue(pll.getNumeroVuelta());
            Cell celda_2 = fila.createCell(1);
            celda_2.setCellValue(ffmt.format(pll.getFecha()));
            Cell celda_3 = fila.createCell(2);
            celda_3.setCellValue(pll.getPlaca());
            Cell celda_4 = fila.createCell(3);
            celda_4.setCellValue(pll.getNumeroInterno());

            if (pll_prev != null && pll_prev.getPlaca().compareTo(pll.getPlaca()) != 0) {
                estiloFila = (estiloFila == null) ? this.estiloFila3 : null;
            }

            if (estiloFila != null) {
                celda_1.setCellStyle(estiloFila);
                celda_2.setCellStyle(estiloFila);
                celda_3.setCellStyle(estiloFila);
                celda_4.setCellStyle(estiloFila);
            }
            pll_prev = pll;
            cellnum = 4;
            
            for (PlanillaDespacho pd : pll.getDetalle()) {
                Cell celda_tiempo = fila.createCell(cellnum++);
                String hora_llegada = hfmt.format(pd.getDiferenciaTiempo());

                long diferencia_tiempo = pd.getDiferencia();
                String signo = (diferencia_tiempo == 0) ? ""
                        : (diferencia_tiempo > 0) ? "+" : "-";

                if (pd.getHoraReal() == null) {
                    celda_tiempo.setCellValue("-");
                    celda_tiempo.setCellStyle(estiloGris);
                } else {
                    celda_tiempo.setCellValue(signo + pd.getDiferenciaTiempo());
                    CellStyle estilo_celda = (diferencia_tiempo == 0) ? estiloVerde
                            : (diferencia_tiempo > 0) ? estiloRojo : estiloNaranja;
                    celda_tiempo.setCellStyle(estilo_celda);
                }
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public int encabezado_planilla(HSSFSheet hoja, int rownum, Planilla pll, ParametrosReporte pr) {

        // fila0
        Row fila_t1 = hoja.createRow(rownum);
        hoja.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));
        Cell ct1 = fila_t1.createCell(0);
        ct1.setCellValue(pr.getNombreEmpresa());
        hoja.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 4));
        Cell ct2 = fila_t1.createCell(3);
        ct2.setCellValue(pr.getNitEmpresa());

        // fila1
        int r = rownum + 1;
        Row fila_enc1 = hoja.createRow(r);

        Cell c00 = fila_enc1.createCell(0);
        c00.setCellValue("Fecha");
        hoja.addMergedRegion(new CellRangeAddress(r, r, 1, 2));
        Cell c12 = fila_enc1.createCell(1);
        c12.setCellValue(ffmt.format(pll.getFecha()));
        Cell c3 = fila_enc1.createCell(3);
        c3.setCellValue("Placa");
        Cell c4 = fila_enc1.createCell(4);
        c4.setCellValue(pll.getPlaca());

        // fila2
        r = rownum + 2;
        Row fila_enc2 = hoja.createRow(r);
        Cell c00a = fila_enc2.createCell(0);
        c00a.setCellValue("Ruta");
        hoja.addMergedRegion(new CellRangeAddress(r, r, 1, 2));
        Cell c12a = fila_enc2.createCell(1);
        c12a.setCellValue(pll.getRuta());

        Cell c3a = fila_enc2.createCell(3);
        c3a.setCellValue("# Interno");
        Cell c4a = fila_enc2.createCell(4);
        c4a.setCellValue(pll.getNumeroInterno());

        // fila3
        r = rownum + 3;
        Row fila_enc3 = hoja.createRow(r);
        Cell c00b = fila_enc3.createCell(0);
        c00b.setCellValue("# Plla");
        Cell c1b = fila_enc3.createCell(1);
        c1b.setCellValue(pll.getNumeroPlanilla());

        if (pll.isVueltaUnica()) {
            Cell c3b = fila_enc3.createCell(3);
            c3b.setCellValue("# Vlta");
            Cell c4b = fila_enc3.createCell(4);
            c4b.setCellValue(pll.getNumeroVuelta());
            c3b.setCellStyle(estiloBold);
        }

        ct1.setCellStyle(estiloBold);
        ct2.setCellStyle(estiloBold);
        c00.setCellStyle(estiloBold);
        c00a.setCellStyle(estiloBold);
        c3.setCellStyle(estiloBold);
        c3a.setCellStyle(estiloBold);
        c00b.setCellStyle(estiloBold);

        return rownum + 4;
    }

    public int piedepagina_planilla(HSSFSheet hoja, int rownum) {

        Row fila3 = hoja.createRow(rownum);
        hoja.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 4));
        Cell celda3 = fila3.createCell(0);
        setCellValue(celda3, "Registel");
        rownum += 1;
        Row fila4 = hoja.createRow(rownum);
        hoja.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 4));
        Cell celda4 = fila4.createCell(0);
        setCellValue(celda4, "Cra 8a No. 33-25 / Cali - Colombia");
        rownum += 1;
        Row fila5 = hoja.createRow(rownum);
        hoja.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 4));
        Cell celda5 = fila5.createCell(0);
        setCellValue(celda5, "Teléfonos: +57 (2)4415840 | +57 (2)3719131");
        celda3.setCellStyle(estiloPiePagina);
        celda4.setCellStyle(estiloPiePagina);
        celda5.setCellStyle(estiloPiePagina);

        return rownum + 1;
    }

    public void planillaDespacho(ArrayList<Planilla> data,
            ParametrosReporte pr,
            boolean vuelta_unica) {

        HSSFSheet hoja = libro.createSheet(pr.getTituloReporte());
        hoja.setMargin(Sheet.TopMargin, .10);
        hoja.setMargin(Sheet.LeftMargin, .10);
        hoja.setMargin(Sheet.BottomMargin, .10);
        hoja.setMargin(Sheet.RightMargin, .10);

        int rownum, cellnum;
        rownum = cellnum = 0;
        boolean primera_planilla = true;

        int ncols = 7; // Numero de columnas de encabezado        

        for (int i = 0; i < data.size(); i++) {

            Planilla pll = data.get(i);
            int nvuelta = pll.getNumeroVuelta();
            ArrayList<PlanillaDespacho> lst_pd = pll.getDetalle();

            int ncols_detalle = lst_pd.size();
            ncols = (ncols_detalle > ncols) ? ncols_detalle : ncols;

            if (nvuelta == 1 || vuelta_unica) {
                if (!primera_planilla) {
                    rownum = piedepagina_planilla(hoja, rownum);
                    rownum += 2;
                }

                pll.setVueltaUnica(vuelta_unica);
                rownum = encabezado_planilla(hoja, rownum, pll, pr);
                Row fila = hoja.createRow(rownum++);   // Cod.punto
                Row fila2 = hoja.createRow(rownum++);   // Tiempo.punto

                for (int j = 0; j < lst_pd.size(); j++) {
                    PlanillaDespacho pd = lst_pd.get(j);
                    Cell celda = fila.createCell(j);
                    Cell celda2 = fila2.createCell(j);
                    setCellValue(celda, pd.getPunto());

                    String hora_planificada = hfmt_min.format(pd.getHoraPlanificada());
                    if (pd.getTipoPunto() == 0) {
                        hora_planificada = "-";
                    }
                    setCellValue(celda2, hora_planificada);
                    celda.setCellStyle(estiloBold);
                }
                primera_planilla = false;

            } else {
                Row fila = hoja.createRow(rownum++);
                for (int j = 0; j < lst_pd.size(); j++) {
                    PlanillaDespacho pd = lst_pd.get(j);
                    Cell celda = fila.createCell(j);

                    String hora_planificada = hfmt_min.format(pd.getHoraPlanificada());
                    if (pd.getTipoPunto() == 0) {
                        hora_planificada = "-";
                    }
                    setCellValue(celda, hora_planificada);
                }
            }
        }

        piedepagina_planilla(hoja, rownum);

        for (int i = 0; i < ncols; i++) {
            hoja.setColumnWidth(i, 1611); // Ancho de columnas aprox: 45px
        }
    }

    public void reporte_CalificacionConductor(String titulo,
            ArrayList<RendimientoConductor> data,
            ParametrosReporte pr,
            ConfCalificacionConductor ccc) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int rownum, cellnum;
        rownum = cellnum = 0;

        String titles[] = {
            "NOMBRE CONDUCTOR",
            "CALIFICACION MAXIMA",
            "PUNTOS POR EXCESO VELOCIDAD",
            "PUNTOS POR INCUMPLIMIENTO DE RUTA",
            "PUNTOS POR DIAS NO LABORADOS",
            "PUNTOS POR PRODUCTIVIDAD",
            "CALIFICACION ALCANZADA"
        };

        Row filaTitulos = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdaTitulo = filaTitulos.createCell(cellnum++);
            celdaTitulo.setCellValue(title);
            celdaTitulo.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (RendimientoConductor rc : data) {

            String puntosProductividad
                    = (rc.getIpk() >= rc.getIpkEmpresa())
                            ? "+" + ccc.getPuntosIpkMayor()
                            : "-" + ccc.getPuntosIpkMenor();

            Object obj[] = {
                rc.getNombre(),
                ccc.getPuntajeMaximo(),
                rc.getExcesoVelocidad() * ccc.getPuntosPorVel(),
                rc.getIncumplimientoRuta() * ccc.getPuntosPorRuta(),
                rc.getDiasNoLaborados() * ccc.getPuntosPorDiaNoLaborado(),
                puntosProductividad,
                rc.getRendimiento()
            };

            Row filaDatos = hoja.createRow(rownum++);
            for (int i = 0; i < obj.length; i++) {
                Object item_obj = obj[i];
                Cell celdaDatos = filaDatos.createCell(i);
                setCellValue(celdaDatos, item_obj);
                if (i == 5) {
                    celdaDatos.setCellStyle(alineacionDerecha);
                }
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Reportes liquidacion
    ////////////////////////////////////////////////////////////////////////////
    public void reporte_ConsolidadoLiquidacion(String titulo, ArrayList<ConsolidadoLiquidacion> data, ParametrosReporte pr, ConfiguracionLiquidacion etiquetas) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int totalConteos = 0, totalVehiculos = 0, totalvueltasLiquidadas = 0, totalPasajerosDescontados = 0, totalPasajerosLiquidados = 0, totalPasajeros = 0;
        double totalDescuentoAlNeto = 0.0, totalDescuentosAdministrativos = 0.0, totalBruto = 0.0, totalDescuentosOperativos = 0.0, totalSubtotal = 0.0, totalLiquidaciones = 0.0;

//        Etiquetas EtiquetasLiquidacion = LiquidacionBD.searchTags();
        String titles[] = {"PLACA",
            "N° INTERNO",
            "VUELTAS LIQUIDADAS",
            "TOTAL PASAJEROS LIQUIDADOS",
            "TOTAL PASAJEROS DESCONTADOS",
            "TOTAL PASAJEROS",
            "TARIFA",
            //            (EtiquetasLiquidacion.getEtq_rep_total1()).toUpperCase(), //Total 
            //            (EtiquetasLiquidacion.getEtq_rep_total4()).toUpperCase(), // Descuentos Operativos 
            //            (EtiquetasLiquidacion.getEtq_rep_total5()).toUpperCase(), //Recibe del Conductor 
            //            (EtiquetasLiquidacion.getEtq_rep_total6()).toUpperCase(), // Descuentos Administrativos 
            //            (EtiquetasLiquidacion.getEtq_rep_total7()).toUpperCase()
            etiquetas.getEtqRepTotal1().toUpperCase(),
            etiquetas.getEtqRepTotal4().toUpperCase(),
            etiquetas.getEtqRepTotal5().toUpperCase(),
            etiquetas.getEtqRepTotal6().toUpperCase(),
            etiquetas.getEtqRepTotal7().toUpperCase()
        }; // Total Liquidaciòn 

        Row rowTitle0 = hoja.createRow((short) 0);

        Cell celdacab0 = rowTitle0.createCell(1);
        celdacab0.setCellValue("Empresa");
        celdacab0.setCellStyle(estiloTitulo);

        Cell celdacab1 = rowTitle0.createCell(2);
        celdacab1.setCellValue("Fecha inicio");
        celdacab1.setCellStyle(estiloTitulo);

        Cell celdacab2 = rowTitle0.createCell(3);
        celdacab2.setCellValue("Fecha fin");
        celdacab2.setCellStyle(estiloTitulo);

        Row rowTitle1 = hoja.createRow((short) 1);

        Cell celdacab3 = rowTitle1.createCell(1);
        celdacab3.setCellValue(pr.getNombreEmpresa());

        Cell celdacab4 = rowTitle1.createCell(2);
        celdacab4.setCellValue(pr.getFechaInicioStr());

        Cell celdacab5 = rowTitle1.createCell(3);
        celdacab5.setCellValue(pr.getFechaFinalStr());

        int rownum, cellnum;
        rownum = 2;
        cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);

        if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {
            String titlesF[] = {"PLACA",
                "N° INTERNO",
                "VUELTAS LIQUIDADAS",
                "TOTAL PASAJEROS LIQUIDADOS",
                "TOTAL PASAJEROS DESCONTADOS",
                "TOTAL PASAJEROS",
                "TARIFA",
                //                (EtiquetasLiquidacion.getEtq_rep_total1()).toUpperCase(), // Subtotal 
                //                (EtiquetasLiquidacion.getEtq_rep_total2()).toUpperCase(), // Descuento al Neto 
                //                (EtiquetasLiquidacion.getEtq_rep_total3()).toUpperCase(), // Total
                //                (EtiquetasLiquidacion.getEtq_rep_total4()).toUpperCase(), // Descuentos Operativos 
                //                (EtiquetasLiquidacion.getEtq_rep_total5()).toUpperCase(), // Descuentos Administrativos 
                //                (EtiquetasLiquidacion.getEtq_rep_total6()).toUpperCase()
                etiquetas.getEtqRepTotal1().toUpperCase(),
                etiquetas.getEtqRepTotal2().toUpperCase(),
                etiquetas.getEtqRepTotal3().toUpperCase(),
                etiquetas.getEtqRepTotal4().toUpperCase(),
                etiquetas.getEtqRepTotal5().toUpperCase(),
                etiquetas.getEtqRepTotal6().toUpperCase()
            }; // Total Liquidación 
            
            for (String title : titlesF) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        } else {
            for (String title : titles) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        }

        for (ConsolidadoLiquidacion cl : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {

                double totalBrutoF = (cl.getTotalPasajerosLiquidados() * cl.getTarifa());
                double totalLiquidacionF = cl.getTotalBruto() - cl.getTotalDescuentosOperativos() - cl.getTotalDescuentosAdministrativos();

                Object celldataF[] = {
                    cl.getPlaca(), cl.getNumInterno(), cl.getVueltasLiquidadas(), cl.getTotalPasajerosLiquidados(),
                    cl.getTotalPasajerosDescontados(), cl.getTotalPasajeros(), cl.getTarifa(), totalBrutoF, cl.getTotalDescuentosAlNeto(), cl.getTotalBruto(), cl.getTotalDescuentosOperativos(),
                    cl.getTotalDescuentosAdministrativos(), totalLiquidacionF};

                totalvueltasLiquidadas += cl.getVueltasLiquidadas();
                totalPasajerosLiquidados += cl.getTotalPasajerosLiquidados();
                totalPasajerosDescontados += cl.getTotalPasajerosDescontados();
                totalPasajeros += cl.getTotalPasajeros();
//                totalBruto += cl.getTotalBruto();
                totalBruto += totalBrutoF;
                totalDescuentoAlNeto += cl.getTotalDescuentosAlNeto();
//                totalSubtotal += cl.getSubTotal();
                totalSubtotal += cl.getTotalBruto();
                totalDescuentosOperativos += cl.getTotalDescuentosOperativos();
                totalDescuentosAdministrativos += cl.getTotalDescuentosAdministrativos();
                totalLiquidaciones += totalLiquidacionF;
                totalVehiculos++;

                for (Object idata : celldataF) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                }

            } else {
                Object celldata[] = {
                    cl.getPlaca(), cl.getNumInterno(), cl.getVueltasLiquidadas(), cl.getTotalPasajerosLiquidados(),
                    cl.getTotalPasajerosDescontados(), cl.getTotalPasajeros(), cl.getTarifa(), cl.getTotalBruto(), cl.getTotalDescuentosOperativos(),
                    cl.getSubTotal(), cl.getTotalDescuentosAdministrativos(), cl.getTotalLiquidacion()};

                totalvueltasLiquidadas += cl.getVueltasLiquidadas();
                totalPasajerosLiquidados += cl.getTotalPasajerosLiquidados();
                totalPasajerosDescontados += cl.getTotalPasajerosDescontados();
                totalPasajeros += cl.getTotalPasajeros();
                totalBruto += cl.getTotalBruto();
                totalDescuentosOperativos += cl.getTotalDescuentosOperativos();
                totalSubtotal += cl.getSubTotal();
                totalDescuentosAdministrativos += cl.getTotalDescuentosAdministrativos();
                totalLiquidaciones += cl.getTotalLiquidacion();
                totalVehiculos++;

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                }
            }

        }

        rellenarHoja(hoja, rownum, cellnum);
        totalesConsolidado(hoja, rownum, totalVehiculos, totalvueltasLiquidadas,
                totalPasajerosLiquidados, totalPasajerosDescontados,
                totalPasajeros, totalBruto, totalDescuentoAlNeto,
                totalDescuentosOperativos, totalDescuentosAdministrativos,
                totalSubtotal, totalLiquidaciones, pr.getNombreEmpresa());
        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_ConsolidadoVehiculosNoLiquidados(String titulo, ArrayList<Movil> data, ParametrosReporte pr) {

        HSSFSheet hoja = libro.createSheet(titulo);

        String titles[] = {"PLACA", "N° INTERNO"};

        Row rowTitle0 = hoja.createRow((short) 0);
//        Etiquetas EtiquetasLiquidacion = LiquidacionBD.searchTags();

        Cell celdacab0 = rowTitle0.createCell(1);
        celdacab0.setCellValue("Empresa");
        celdacab0.setCellStyle(estiloTitulo);

        Cell celdacab1 = rowTitle0.createCell(2);
        celdacab1.setCellValue("Fecha inicio");
        celdacab1.setCellStyle(estiloTitulo);

        Cell celdacab2 = rowTitle0.createCell(3);
        celdacab2.setCellValue("Fecha fin");
        celdacab2.setCellStyle(estiloTitulo);

        Row rowTitle1 = hoja.createRow((short) 1);

        Cell celdacab3 = rowTitle1.createCell(1);
        celdacab3.setCellValue(pr.getNombreEmpresa());

        Cell celdacab4 = rowTitle1.createCell(2);
        celdacab4.setCellValue(pr.getFechaInicioStr());

        Cell celdacab5 = rowTitle1.createCell(3);
        celdacab5.setCellValue(pr.getFechaFinalStr());

        int rownum, cellnum;
        rownum = 2;
        cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue(title);
            celdacab.setCellStyle(estiloTitulo);
        }

        for (Movil m : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            Object celldata[] = {
                m.getPlaca(), m.getNumeroInterno()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);
                cellnum++;
            }
        }

        rellenarHoja(hoja, rownum, cellnum);

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void reporte_LiquidacionXLiquidador(String titulo, ArrayList<LiquidacionXLiquidador> data, ParametrosReporte pr, ConfiguracionLiquidacion etiquetas) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int totalConteos = 0, totalVehiculos = 0, totalLiquidaciones = 0, totalPasajerosDescontados = 0;
        double totalSumaTotalOtrosDescuentos = 0.0, totalSumaBruto = 0.0, totalSumaDescuentos = 0.0, totalSumaSubtotal = 0.0, totalTodasLiquidaciones = 0.0;
        double totalDescuentoAlNeto = 0.0;
        //String titles[]= {"PLACA", "N° INTERNO", "LIQUIDACIONES", "LIQUIDADOR", "TOTAL PASAJEROS DESCONTADOS", "TOTAL NETO", "TOTAL DESCUENTOS", "SUB TOTAL", "TOTAL OTROS DESCUENTOS", "TOTAL LIQUIDADO"};

//        Etiquetas EtiquetasLiquidacion = LiquidacionBD.searchTags();
        String titles[] = {"PLACA",
            "N° INTERNO",
            "LIQUIDACIONES",
            "LIQUIDADOR",
            "TOTAL PASAJEROS DESCONTADOS",
            //            EtiquetasLiquidacion.getEtq_rep_total1(),
            //            EtiquetasLiquidacion.getEtq_rep_total4(),
            //            EtiquetasLiquidacion.getEtq_rep_total5(),
            //            EtiquetasLiquidacion.getEtq_rep_total6(),
            //            EtiquetasLiquidacion.getEtq_rep_total7()
            etiquetas.getEtqRepTotal1().toUpperCase(),
            etiquetas.getEtqRepTotal4().toUpperCase(),
            etiquetas.getEtqRepTotal5().toUpperCase(),
            etiquetas.getEtqRepTotal6().toUpperCase(),
            etiquetas.getEtqRepTotal7().toUpperCase()
        };

        Row rowTitle0 = hoja.createRow((short) 0);

        Cell celdacab0 = rowTitle0.createCell(1);
        celdacab0.setCellValue("Empresa");
        celdacab0.setCellStyle(estiloTitulo);

        Cell celdacab1 = rowTitle0.createCell(2);
        celdacab1.setCellValue("Fecha inicio");
        celdacab1.setCellStyle(estiloTitulo);

        Cell celdacab2 = rowTitle0.createCell(3);
        celdacab2.setCellValue("Fecha fin");
        celdacab2.setCellStyle(estiloTitulo);

        Row rowTitle1 = hoja.createRow((short) 1);

        Cell celdacab3 = rowTitle1.createCell(1);
        celdacab3.setCellValue(pr.getNombreEmpresa());

        Cell celdacab4 = rowTitle1.createCell(2);
        celdacab4.setCellValue(pr.getFechaInicioStr());

        Cell celdacab5 = rowTitle1.createCell(3);
        celdacab5.setCellValue(pr.getFechaFinalStr());

        int rownum, cellnum;
        rownum = 2;
        cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        /**/
        if (pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN") || pr.getNombreEmpresa().equalsIgnoreCase("tierragrata") || pr.getNombreEmpresa().equalsIgnoreCase("tierra grata")) {
            //String titlesF[] = {"PLACA", "N° INTERNO", "LIQUIDACIONES", "LIQUIDADOR", "TOTAL PASAJEROS DESCONTADOS", "TOTAL NETO", "TOTAL DESCUENTO AL NETO", "TOTAL DESCUENTOS", "SUB TOTAL", "TOTAL OTROS DESCUENTOS", "TOTAL LIQUIDADO"};
            String titlesF[] = {"PLACA",
                "N° INTERNO",
                "LIQUIDACIONES",
                "LIQUIDADOR",
                "TOTAL PASAJEROS DESCONTADOS",
//                EtiquetasLiquidacion.getEtq_rep_total1(),
//                EtiquetasLiquidacion.getEtq_rep_total2(),
//                EtiquetasLiquidacion.getEtq_rep_total3(),
//                EtiquetasLiquidacion.getEtq_rep_total4(),
//                EtiquetasLiquidacion.getEtq_rep_total5(),
//                EtiquetasLiquidacion.getEtq_rep_total6()
                etiquetas.getEtqRepTotal1().toUpperCase(),
                etiquetas.getEtqRepTotal2().toUpperCase(),
                etiquetas.getEtqRepTotal3().toUpperCase(),
                etiquetas.getEtqRepTotal4().toUpperCase(),
                etiquetas.getEtqRepTotal5().toUpperCase(),
                etiquetas.getEtqRepTotal6().toUpperCase()
            };
            for (String title : titlesF) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        } else {
            for (String title : titles) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        }//FIN ELSE EMPRESA

        double sumaBrutoF;
        double totalLiquidacionF;

        for (LiquidacionXLiquidador m : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            Object celldata[] = {
                m.getPlaca(), m.getNumInterno(), m.getNumLiquidaciones(), m.getLiquidador(), m.getTotalPasajerosDescontados(), m.getTotalBruto(), m.getTotalDescuentos(), m.getSubTotal(), m.getTotalOtrosDescuentos(),
                m.getTotalLiquidacion()};

            if (pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN") || pr.getNombreEmpresa().equalsIgnoreCase("tierragrata") || pr.getNombreEmpresa().equalsIgnoreCase("tierra grata")) {

                sumaBrutoF = m.getTotalBruto() + m.getTotalDescuentosAlNeto();
                totalLiquidacionF = m.getTotalBruto() - m.getTotalDescuentos() - m.getTotalOtrosDescuentos();
                Object celldataF[] = {
                    m.getPlaca(), m.getNumInterno(), m.getNumLiquidaciones(), m.getLiquidador(), m.getTotalPasajerosDescontados(), sumaBrutoF, m.getTotalDescuentosAlNeto(), m.getTotalBruto(), m.getTotalDescuentos(), m.getTotalOtrosDescuentos(),
                    totalLiquidacionF};
                for (Object idata : celldataF) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                }
                totalLiquidaciones += m.getNumLiquidaciones();
                totalSumaBruto += (Double) sumaBrutoF;
                totalSumaDescuentos += (Double) m.getTotalDescuentos();
                totalDescuentoAlNeto += (Double) m.getTotalDescuentosAlNeto();
                totalSumaSubtotal += (Double) m.getTotalBruto();
                totalSumaTotalOtrosDescuentos += (Double) m.getTotalOtrosDescuentos();
                totalPasajerosDescontados += m.getTotalPasajerosDescontados();
                totalTodasLiquidaciones += totalLiquidacionF;
            } else {
                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                }

                totalLiquidaciones += m.getNumLiquidaciones();
                totalSumaBruto += (Double) m.getTotalBruto();
                totalSumaDescuentos += (Double) m.getTotalDescuentos();
                totalSumaSubtotal += (Double) m.getSubTotal();
                totalSumaTotalOtrosDescuentos += (Double) m.getTotalOtrosDescuentos();
                totalPasajerosDescontados += m.getTotalPasajerosDescontados();
                totalTodasLiquidaciones += m.getTotalLiquidacion();
            }//FIN ELSE EMPRESA

            totalVehiculos++;
            totalConteos++;

        }

        rellenarHoja(hoja, rownum, cellnum);
        /*if (pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN") || pr.getNombreEmpresa().equalsIgnoreCase("tierragrata") || pr.getNombreEmpresa().equalsIgnoreCase("tierra grata")) {
            totales(hoja, rownum, totalVehiculos, 
                totalLiquidaciones, totalPasajerosDescontados, 
                totalSumaTotalOtrosDescuentos, totalSumaBruto, totalDescuentoAlNeto, 
                totalSumaDescuentos, totalSumaSubtotal, totalTodasLiquidaciones, pr.getNombreEmpresa());
        }else{*/
        totales(hoja, rownum, totalVehiculos,
                totalLiquidaciones, totalPasajerosDescontados,
                totalSumaTotalOtrosDescuentos, totalSumaBruto, totalDescuentoAlNeto,
                totalSumaDescuentos, totalSumaSubtotal, totalTodasLiquidaciones, pr.getNombreEmpresa());
        //}

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }

        // Asignar automaticamente el tamaño de las celdas en funcion del contenido
        //for (int i = 0; i < totalConteos; i++)  {hoja.autoSizeColumn((short) i);}
    }

    public void reporte_IPK(String titulo, ArrayList<LiquidacionIPK> data, ParametrosReporte pr, ConfiguracionLiquidacion etiquetas) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int totalConteos = 0, totalPasajeros = 0,
                totalPasajerosDescontados = 0, totalPasajerosLiquidados = 0,
                totalVueltasLiquidadas = 0, totalVehiculos = 0;

        double totalIPK = 0, totalBruto = 0, totalKm = 0,
                totalDescuentoAlNeto = 0, totalDescuentosOperativos = 0, totalDescuentosAdministrativos = 0,
                subTotal = 0, totalLiquidaciones = 0;

//        Etiquetas EtiquetasLiquidacion = null;
//        EtiquetasLiquidacion = LiquidacionBD.searchTags();

        //String titles[] = {"PLACA", "N° INTERNO", "RUTA", "TOTAL_KM","IPK", "VUELTAS LIQUIDADAS", "TOTAL PASAJEROS", "TOTAL PASAJEROS DESCONTADOS", "TOTAL PASAJEROS LIQUIDADOS", "TARIFA", "TOTAL BRUTO", "TOTAL DESCUENTOS OPERATIVOS", "TOTAL DESCUENTOS ADMINISTRATIVOS", "SUBTOTAL", "TOTAL LIQUIDACIÓN"};
        String titles[] = {"PLACA",
            "N° INTERNO",
            "RUTA",
            "TOTAL_KM",
            "IPK",
            "VUELTAS LIQUIDADAS",
            "TOTAL PASAJEROS",
            "TOTAL PASAJEROS DESCONTADOS",
            "TOTAL PASAJEROS LIQUIDADOS",
            "TARIFA",
            //            EtiquetasLiquidacion.getEtq_rep_total1(),
            //            EtiquetasLiquidacion.getEtq_rep_total4(),
            //            EtiquetasLiquidacion.getEtq_rep_total5(),
            //            EtiquetasLiquidacion.getEtq_rep_total6(),
            //            EtiquetasLiquidacion.getEtq_rep_total7()
            etiquetas.getEtqRepTotal1().toUpperCase(),
            etiquetas.getEtqRepTotal4().toUpperCase(),
            etiquetas.getEtqRepTotal5().toUpperCase(),
            etiquetas.getEtqRepTotal6().toUpperCase(),
            etiquetas.getEtqRepTotal7().toUpperCase()
        };

        Row rowTitle0 = hoja.createRow((short) 0);

        Cell celdacab0 = rowTitle0.createCell(1);
        celdacab0.setCellValue("Empresa");
        celdacab0.setCellStyle(estiloTitulo);

        Cell celdacab1 = rowTitle0.createCell(2);
        celdacab1.setCellValue("Fecha inicio");
        celdacab1.setCellStyle(estiloTitulo);

        Cell celdacab2 = rowTitle0.createCell(3);
        celdacab2.setCellValue("Fecha fin");
        celdacab2.setCellStyle(estiloTitulo);

        Row rowTitle1 = hoja.createRow((short) 1);

        Cell celdacab3 = rowTitle1.createCell(1);
        celdacab3.setCellValue(pr.getNombreEmpresa());

        Cell celdacab4 = rowTitle1.createCell(2);
        celdacab4.setCellValue(pr.getFechaInicioStr());

        Cell celdacab5 = rowTitle1.createCell(3);
        celdacab5.setCellValue(pr.getFechaFinalStr());

        int rownum, cellnum;
        rownum = 2;
        cellnum = 0;

        // Titulos
        Row filacab = hoja.createRow(rownum++);
        if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN"))
                || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata"))
                || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {
            String titlesF[] = {"PLACA",
                "N° INTERNO",
                "RUTA",
                "TOTAL_KM",
                "IPK",
                "VUELTAS LIQUIDADAS",
                "TOTAL PASAJEROS",
                "TOTAL PASAJEROS DESCONTADOS",
                "TOTAL PASAJEROS LIQUIDADOS",
                "TARIFA",
                //                EtiquetasLiquidacion.getEtq_rep_total1(),
                //                EtiquetasLiquidacion.getEtq_rep_total2(),
                //                EtiquetasLiquidacion.getEtq_rep_total3(),
                //                EtiquetasLiquidacion.getEtq_rep_total4(),
                //                EtiquetasLiquidacion.getEtq_rep_total5(),
                //                EtiquetasLiquidacion.getEtq_rep_total6()
                etiquetas.getEtqRepTotal1().toUpperCase(),
                etiquetas.getEtqRepTotal2().toUpperCase(),
                etiquetas.getEtqRepTotal3().toUpperCase(),
                etiquetas.getEtqRepTotal4().toUpperCase(),
                etiquetas.getEtqRepTotal5().toUpperCase(),
                etiquetas.getEtqRepTotal6().toUpperCase()
            };

            for (String title : titlesF) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        } else {//FIN SI
            for (String title : titles) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        }
        /*ESCRIBIR LOS DATOS EN LA HOJA*/
        double totalLiquidacionF = 0;
        for (LiquidacionIPK m : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN"))
                    || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata"))
                    || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {//fusa

                totalLiquidacionF = m.getSubTotal() - m.getTotalDescuentosOperativos() - m.getTotalDescuentosAdminsitrativos();

                Object celldataF[] = {m.getPlaca(),
                    m.getNumero_interno(),
                    m.getRuta(),
                    m.getTotal_km(),
                    m.getIpk(),
                    m.getVueltasLiquidadas(),
                    m.getTotalPasajeros(),
                    m.getTotalPasajerosDescontados(),
                    m.getTotalPasajerosLiquidados(),
                    m.getTarifa(),
                    m.getTotalBrutoPasajeros(),
                    m.getTotalDescuentosAlNeto(),
                    m.getSubTotal(),
                    m.getTotalDescuentosOperativos(),
                    m.getTotalDescuentosAdminsitrativos(),
                    totalLiquidacionF};

                totalIPK += m.getIpk();
                totalKm += m.getTotal_km();
                totalVueltasLiquidadas += m.getVueltasLiquidadas();
                totalPasajeros += m.getTotalPasajeros();
                totalPasajerosDescontados += m.getTotalPasajerosDescontados();
                totalPasajerosLiquidados += m.getTotalPasajerosLiquidados();
                totalBruto += m.getTotalBrutoPasajeros();
                totalDescuentoAlNeto += m.getTotalDescuentosAlNeto();
                subTotal += m.getSubTotal();
                totalDescuentosOperativos += m.getTotalDescuentosOperativos();
                totalDescuentosAdministrativos += m.getTotalDescuentosAdminsitrativos();
                totalLiquidaciones += totalLiquidacionF;

                for (Object idata : celldataF) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                }
            } else {//neiva
                Object celldata[] = {m.getPlaca(),
                    m.getNumero_interno(),
                    m.getRuta(),
                    m.getTotal_km(),
                    m.getIpk(),
                    m.getVueltasLiquidadas(),
                    m.getTotalPasajeros(),
                    m.getTotalPasajerosDescontados(),
                    m.getTotalPasajerosLiquidados(),
                    m.getTarifa(),
                    m.getTotalBrutoPasajeros(),
                    m.getTotalDescuentosOperativos(),
                    m.getSubTotal(),
                    m.getTotalDescuentosAdminsitrativos(),
                    m.getTotalLiquidacion()};

                totalIPK += m.getIpk();
                totalKm += m.getTotal_km();
                totalVueltasLiquidadas += m.getVueltasLiquidadas();
                totalPasajeros += m.getTotalPasajeros();
                totalPasajerosDescontados += m.getTotalPasajerosDescontados();
                totalPasajerosLiquidados += m.getTotalPasajerosLiquidados();
                totalBruto += m.getTotalBrutoPasajeros();
                totalDescuentosOperativos += m.getTotalDescuentosOperativos();
                subTotal += m.getSubTotal();
                totalDescuentosAdministrativos += m.getTotalDescuentosAdminsitrativos();

                totalLiquidaciones += m.getTotalLiquidacion();

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                }
            }//FIN ELSE            
            totalVehiculos++;
            totalConteos++;
        }

        rellenarHoja(hoja, rownum, cellnum);
        /*totalesIPK(hoja, rownum, totalPasajeros, totalPasajerosDescontados, totalPasajerosLiquidados,
                    totalVueltasLiquidadas, totalVehiculos,totalKm, totalIPK, totalBruto, totalDescuentoAlNeto,
                    totalDescuentosOperativos, totalDescuentosAdministrativos, sumaBrutoF, totalLiquidaciones, pr.getNombreEmpresa());*/
        totalesIPK(hoja, rownum, totalConteos, totalPasajeros, totalPasajerosDescontados, totalPasajerosLiquidados,
                totalVueltasLiquidadas, totalVehiculos, totalKm, totalIPK, totalBruto, totalDescuentoAlNeto,
                totalDescuentosOperativos, totalDescuentosAdministrativos, subTotal, totalLiquidaciones, pr.getNombreEmpresa());
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    public void consulta_De_Liquidacion(String titulo, ArrayList<LiquidacionConsulta> data, ParametrosReporte pr, ConfiguracionLiquidacion etiquetas) {

        HSSFSheet hoja = libro.createSheet(titulo);
        int totalConteos = 0, totalPasajerosLiquidados = 0, totalVehiculos = 0;
        double totalBruto = 0, totalDescuentoAlNeto = 0, subtotal = 0, totalDescuentos = 0, totalOtrosDescuentos = 0, totalLiquidaciones = 0;
//        Etiquetas EtiquetasLiquidacion = null;
//        EtiquetasLiquidacion = LiquidacionBD.searchTags();

        String titles[] = {"RECIBO",
            "PLACA",
            "N° INTERNO",
            "ESTADO",
            "TARIFA",
            "PASAJEROS LIQUIDADOS",
            //            (EtiquetasLiquidacion.getEtq_total2()).toUpperCase(),
            //            (EtiquetasLiquidacion.getEtq_total3()).toUpperCase(),
            //            (EtiquetasLiquidacion.getEtq_total5()).toUpperCase(),
            //            (EtiquetasLiquidacion.getEtq_total6()).toUpperCase(),
            //            (EtiquetasLiquidacion.getEtq_total7()).toUpperCase(),
            etiquetas.getEtqRepTotal2().toUpperCase(),
            etiquetas.getEtqRepTotal3().toUpperCase(),
            etiquetas.getEtqRepTotal5().toUpperCase(),
            etiquetas.getEtqRepTotal6().toUpperCase(),
            etiquetas.getEtqRepTotal7().toUpperCase(),
            "CONDUCTOR",
            "LIQUIDADOR",
            "FECHA DE LIQUIDACION"
        };

        Row rowTitle0 = hoja.createRow((short) 0);

        Cell celdacab0 = rowTitle0.createCell(1);
        celdacab0.setCellValue("Empresa");
        celdacab0.setCellStyle(estiloTitulo);

        Cell celdacab1 = rowTitle0.createCell(2);
        celdacab1.setCellValue("Fecha inicio");
        celdacab1.setCellStyle(estiloTitulo);

        Cell celdacab2 = rowTitle0.createCell(3);
        celdacab2.setCellValue("Fecha fin");
        celdacab2.setCellStyle(estiloTitulo);

        Row rowTitle1 = hoja.createRow((short) 1);

        Cell celdacab3 = rowTitle1.createCell(1);
        celdacab3.setCellValue(pr.getNombreEmpresa());

        Cell celdacab4 = rowTitle1.createCell(2);
        celdacab4.setCellValue(pr.getFechaInicioStr());

        Cell celdacab5 = rowTitle1.createCell(3);
        celdacab5.setCellValue(pr.getFechaFinalStr());

        int rownum, cellnum;
        rownum = 2;
        cellnum = 0;

        Row filacab = hoja.createRow(rownum++);
        if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {
            // Titulos            
            String titles1[] = {"RECIBO",
                "PLACA",
                "N° INTERNO",
                "ESTADO",
                "TARIFA",
                "TOTAL PASAJEROS LIQUIDADOS",
//                EtiquetasLiquidacion.getEtq_total8(),
//                EtiquetasLiquidacion.getEtq_total2(),
//                EtiquetasLiquidacion.getEtq_total3(),
//                EtiquetasLiquidacion.getEtq_total4(),
//                EtiquetasLiquidacion.getEtq_total6(),
//                EtiquetasLiquidacion.getEtq_total7(),
                etiquetas.getEtqRepTotal8().toUpperCase(),
                etiquetas.getEtqRepTotal2().toUpperCase(),
                etiquetas.getEtqRepTotal3().toUpperCase(),
                etiquetas.getEtqRepTotal4().toUpperCase(),
                etiquetas.getEtqRepTotal6().toUpperCase(),
                etiquetas.getEtqRepTotal7().toUpperCase(),
                "CONDUCTOR",
                "LIQUIDADOR",
                "FECHA DE LIQUIDACION"};

            //String titles1[] = {"RECIBO", "PLACA", "N° INTERNO", "ESTADO", "TARIFA", "PASAJEROS LIQUIDADOS", "TOTAL BRUTO", "TOTAL DESCUENTO AL NETO", "SUBTOTAL", "TOTAL DESCUENTOS OPERATIVOS", "TOTAL OTROS DESCUENTOS", "TOTAL LIQUIDACIÓN", "CONDUCTOR", "LIQUIDADOR", "FECHA DE LIQUIDACION"};            
            for (String title : titles1) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        } else {//FIN ELSE
            for (String title : titles) {
                Cell celdacab = filacab.createCell(cellnum++);
                celdacab.setCellValue(title);
                celdacab.setCellStyle(estiloTitulo);
            }
        }

        double subtotalF;
        double totalLiquidacionF;

        /*ESCRIBIR LOS DATOS*/
        for (LiquidacionConsulta m : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;

            if ((pr.getNombreEmpresa().equalsIgnoreCase("FUSACATAN")) || (pr.getNombreEmpresa().equalsIgnoreCase("tierragrata"))
                    || (pr.getNombreEmpresa().equalsIgnoreCase("tierra grata"))) {

                subtotalF = m.getValor_tarifa() * m.getTotal_pasajeros_liquidados();
                totalLiquidacionF = m.getTotal_valor_vueltas() - m.getTotal_valor_descuentos() - m.getTotal_valor_otros_descuentos();

                Object celldataF[] = {m.getPk_liquidacion(), m.getPlaca(), m.getNum_interno(), m.getEstado(), m.getValor_tarifa(),
                    m.getTotal_pasajeros_liquidados(), subtotalF, m.getTotal_valor_descuento_al_neto(), m.getTotal_valor_vueltas(), m.getTotal_valor_descuentos(), m.getTotal_valor_otros_descuentos(),
                    totalLiquidacionF, m.getConductor(), m.getLiquidador(), m.getFecha_liquidacion()};

                totalPasajerosLiquidados += m.getTotal_pasajeros_liquidados();
                totalBruto += subtotalF;
                totalDescuentoAlNeto += m.getTotal_valor_descuento_al_neto();
                subtotal += m.getTotal_valor_vueltas();
                totalDescuentos += m.getTotal_valor_descuentos();
                totalOtrosDescuentos += m.getTotal_valor_otros_descuentos();
                totalLiquidaciones += totalLiquidacionF;

                //totalVehiculos++;
                //totalConteos++;           
                for (Object idata : celldataF) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);
                    cellnum++;
                    //System.out.println(cellnum + "<***>"+celda+"--->"+idata);
                }
            } else {//FIN SI
                Object celldata[] = {m.getPk_liquidacion(),
                    m.getPlaca(),
                    m.getNum_interno(),
                    m.getEstado(),
                    m.getValor_tarifa(),
                    m.getTotal_pasajeros_liquidados(),
                    m.getTotal_valor_vueltas(),
                    m.getTotal_valor_descuentos(),
                    m.getSubtotal(),
                    m.getTotal_valor_otros_descuentos(),
                    m.getTotal_liquidacion(),
                    m.getConductor(),
                    m.getLiquidador(),
                    m.getFecha_liquidacion()};

                totalPasajerosLiquidados += m.getTotal_pasajeros_liquidados();
                totalBruto += m.getTotal_valor_vueltas();
                totalDescuentos += m.getTotal_valor_descuentos();
                subtotal += m.getSubtotal();
                totalOtrosDescuentos += m.getTotal_valor_otros_descuentos();
                totalLiquidaciones += m.getTotal_liquidacion();

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);

                    cellnum++;
                }
            }//FIN ELSE

            totalVehiculos++;
            totalConteos++;
            //System.out.println("--> "+totalVehiculos+"<-->"+totalConteos);
        }//FIN FOR
        rellenarHoja(hoja, rownum, cellnum);
        totalesConsultaLiquidacion(hoja, rownum, totalVehiculos, totalPasajerosLiquidados, totalBruto, subtotal, totalDescuentoAlNeto, totalDescuentos, totalOtrosDescuentos, totalLiquidaciones, pr.getNombreEmpresa());
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }

    }

    public void consulta_categorias_descuentan_pasajeros(String titulo, ArrayList<CategoriaQueDescuentaPax> data, ParametrosReporte pr) {

        HSSFSheet hoja = libro.createSheet(titulo);
        double totalFila[] = null;
        String titles[] = null;
        int cdpSize = 0;
        CategoriasDeDescuento c = null;
        ArrayList<CategoriasDeDescuento> categoriasQueDescuentanPasajeros = CategoriasDeDescuentoBD.categoriasQueDescuentanPasajeros();
        try {

            cdpSize = categoriasQueDescuentanPasajeros.size();
            titles = new String[cdpSize + 5];
            titles[0] = "PLACA";

            titles[cdpSize + 1] = "DESCUENTO DE PASAJEROS POR VEHICULO";
            titles[cdpSize + 2] = "TOTAL PASAJEROS LIQUIDADOS";
            titles[cdpSize + 3] = "TOTAL PASAJEROS";
            titles[cdpSize + 4] = "INDICADOR(%)";

            Row rowTitle0 = hoja.createRow((short) 0);

            Cell celdacab0 = rowTitle0.createCell(1);
            celdacab0.setCellValue("Empresa");
            celdacab0.setCellStyle(estiloTitulo);

            Cell celdacab1 = rowTitle0.createCell(2);
            celdacab1.setCellValue("Fecha inicio");
            celdacab1.setCellStyle(estiloTitulo);

            Cell celdacab2 = rowTitle0.createCell(3);
            celdacab2.setCellValue("Fecha fin");
            celdacab2.setCellStyle(estiloTitulo);

            Row rowTitle1 = hoja.createRow((short) 1);

            Cell celdacab3 = rowTitle1.createCell(1);
            celdacab3.setCellValue(pr.getNombreEmpresa());

            Cell celdacab4 = rowTitle1.createCell(2);
            celdacab4.setCellValue(pr.getFechaInicioStr());

            Cell celdacab5 = rowTitle1.createCell(3);
            celdacab5.setCellValue(pr.getFechaFinalStr());

            int rownum, cellnum;
            rownum = 2;
            cellnum = 0;

            Row filacab = hoja.createRow(rownum++);
            // Titulos                      
            Cell celdacab = filacab.createCell(0);
            celdacab.setCellValue(titles[0]);
            celdacab.setCellStyle(estiloTitulo);

            HashMap< Integer, Integer> catIdCell = new HashMap<>();

            // creando los titulos para las categorias
            for (int i = 0; i < categoriasQueDescuentanPasajeros.size(); i++) {

                Cell celdacabAux = filacab.createCell(i + 1);
                celdacabAux.setCellValue((categoriasQueDescuentanPasajeros.get(i).getNombre()).toUpperCase());
                celdacabAux.setCellStyle(estiloTitulo);
                // Asignando ubicacion a las categorias con relación a las celdas
                catIdCell.put(categoriasQueDescuentanPasajeros.get(i).getId(), i + 1);
            }

            Cell celdacab10 = filacab.createCell(cdpSize + 1);
            celdacab10.setCellValue(titles[cdpSize + 1]);
            celdacab10.setCellStyle(estiloTitulo);

            Cell celdacab11 = filacab.createCell(cdpSize + 2);
            celdacab11.setCellValue(titles[cdpSize + 2]);
            celdacab11.setCellStyle(estiloTitulo);

            Cell celdacab12 = filacab.createCell(cdpSize + 3);
            celdacab12.setCellValue(titles[cdpSize + 3]);
            celdacab12.setCellStyle(estiloTitulo);

            Cell celdacab13 = filacab.createCell(cdpSize + 4);
            celdacab13.setCellValue(titles[cdpSize + 4]);
            celdacab13.setCellStyle(estiloTitulo);
            /*ESCRIBIR LOS DATOS*/
            totalFila = new double[cdpSize + 4];
            Object celldataF[] = null;
            //escritura de los datos en la hoja
            if (data.size() > 0) {
                for (CategoriaQueDescuentaPax m : data) {
                    celldataF = new Object[cdpSize + 5];
                    Row fila = hoja.createRow(rownum++);
                    cellnum = 0;
                    HashMap< Integer, Integer> categorias = m.getCategorias();
                    //instancia el arreglo con la cantidad de categorias encontradas + la placa;                       
                    System.out.print(m.getCategoria() + "\t");
                    celldataF[0] = m.getPlaca();
                    Iterator it = categorias.keySet().iterator();

                    // LLenando con Ceros todos los campos
                    for (int i = 0; i < categoriasQueDescuentanPasajeros.size(); i++) {
                        celldataF[i + 1] = 0;
                    }

                    /*EXTRAE LAS CATEGORIAS CON SUS VALORES DE PASAJEROS*/
                    while (it.hasNext()) {
                        Integer key = (Integer) it.next();

                        celldataF[catIdCell.get(key)] = categorias.get(key);
                        totalFila[catIdCell.get(key) - 1] += categorias.get(key);

                    }
                    //envia los datos a imprimir a la hoja y totaliza
                    celldataF[cdpSize + 1] = m.getTotalPasajeroDescontados();
                    totalFila[cdpSize] += m.getTotalPasajeroDescontados();
                    //envia los datos a imprimir a la hoja y totaliza
                    celldataF[cdpSize + 2] = m.getTotalPasajeroLiquidados();
                    totalFila[cdpSize + 1] += m.getTotalPasajeroLiquidados();
                    //envia los datos a imprimir a la hoja y totaliza
                    celldataF[cdpSize + 3] = m.getTotalPasajeros();
                    totalFila[cdpSize + 2] += m.getTotalPasajeros();
                    //envia los datos a imprimir a la hoja y totaliza
                    celldataF[cdpSize + 4] = (double) Math.round(m.getIndicadorPasajeros() * 100) / 100;

                    for (Object idata : celldataF) {
                        Cell celda = fila.createCell(cellnum);
                        setCellValue(celda, idata);
                        cellnum++;
                        //System.out.print(idata+"\t");                            
                    }
                }//FIN FOR

                totalFila[categoriasQueDescuentanPasajeros.size() + 3] = (double) Math.round(((totalFila[categoriasQueDescuentanPasajeros.size()] / totalFila[categoriasQueDescuentanPasajeros.size() + 2]) * 100.0) * 100) / 100;
                rellenarHoja(hoja, rownum, cellnum);
                totalesCategorias(hoja, rownum, totalFila, pr.getNombreEmpresa());
                for (int i = 0; i < rownum; i++) {
                    hoja.autoSizeColumn(i);
                }
            } else {//fin si
                throw new Exception();
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }//fin funcion

    /*RECIBE STRING*/
    public void createCell(Row row, int i, String value, CellStyle style) {
        Cell cell = row.createCell(i);
        value += "";
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    public void createCell(Row row, int i, double value, CellStyle style) {
        Cell cell = row.createCell(i);
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    public void totalesCategorias(HSSFSheet hoja, int totalConteos, double totales[], String nombreEmpresa) {
        try {
            CellStyle tittleStyle;
            Row row = hoja.createRow((short) (totalConteos));
            tittleStyle = getTitleStyle(libro);
            Font fontTitle = getTitleFont(libro);
            tittleStyle.setFont(fontTitle);
            createCell(row, 0, "TOTALES", tittleStyle);
            for (int i = 0; i < totales.length; i++) {
                //System.out.println("--> "+(i+1)+"<****> "+(i-1));
                createCell(row, i + 1, totales[i], tittleStyle);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }

    }

    public void totalesConsultaLiquidacion(HSSFSheet hoja, int totalConteos, int totalregistros, int totalPasajerosLiquidados, double totalBruto,
            double subtotal, double totalDescuentoAlNeto, double totalDescuentos, double totalOtrosDescuentos, double totalLiquidaciones, String nombreEmpresa) {
        CellStyle tittleStyle;
        Row row = hoja.createRow((short) (totalConteos));
        tittleStyle = getTitleStyle(libro);
        Font fontTitle = getTitleFont(libro);
        tittleStyle.setFont(fontTitle);
        //System.out.println("row ---> "+row+" total "+totalConteos);
        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))) {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, totalregistros, tittleStyle);
            createCell(row, 2, "", tittleStyle);
            createCell(row, 3, "", tittleStyle);
            createCell(row, 4, "", tittleStyle);
            createCell(row, 5, totalPasajerosLiquidados, tittleStyle);
            createCell(row, 6, totalBruto, tittleStyle);
            createCell(row, 7, totalDescuentoAlNeto, tittleStyle);
            createCell(row, 8, subtotal, tittleStyle);
            createCell(row, 9, totalDescuentos, tittleStyle);
            createCell(row, 10, totalOtrosDescuentos, tittleStyle);
            createCell(row, 11, totalLiquidaciones, tittleStyle);
            createCell(row, 12, "", tittleStyle);
            createCell(row, 13, "", tittleStyle);
            createCell(row, 14, "", tittleStyle);
        } else {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, totalregistros, tittleStyle);
            createCell(row, 2, "", tittleStyle);
            createCell(row, 3, "", tittleStyle);
            createCell(row, 4, "", tittleStyle);
            createCell(row, 5, totalPasajerosLiquidados, tittleStyle);
            createCell(row, 6, totalBruto, tittleStyle);
            createCell(row, 7, totalDescuentos, tittleStyle);
            createCell(row, 8, subtotal, tittleStyle);
            createCell(row, 9, totalOtrosDescuentos, tittleStyle);
            createCell(row, 10, totalLiquidaciones, tittleStyle);
            createCell(row, 11, "", tittleStyle);
            createCell(row, 12, "", tittleStyle);
            createCell(row, 13, "", tittleStyle);
        }
    }

    public void totalesConsultaLiquidacionFusa(HSSFSheet hoja, int totalConteos, int totalregistros, int totalPasajerosLiquidados, double totalBruto,
            double totalDescuentoAlNeto, double totalDescuentos, double totalOtrosDescuentos, double totalLiquidaciones) {
        CellStyle tittleStyle;
        Row row = hoja.createRow((short) (totalConteos));
        tittleStyle = getTitleStyle(libro);
        Font fontTitle = getTitleFont(libro);
        tittleStyle.setFont(fontTitle);

    }

    public void totalesIPK(HSSFSheet hoja, int filas, int totalConteos, int totalPasajeros, int totalPasajerosDescontados, int totalPasajerosLiquidados,
            int totalVueltasLiquidadas, int totalVehiculos, double totalkm, double totalIPK, double totalBruto, double totalDescuentoAlNeto,
            double totalDescuentos, double totalOtrosDescuentos, double subTotal, double totalLiquidaciones, String nombreEmpresa) {
        CellStyle tittleStyle;
        Row row = hoja.createRow((short) (filas));
        tittleStyle = getTitleStyle(libro);
        Font fontTitle = getTitleFont(libro);
        tittleStyle.setFont(fontTitle);
        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))) {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, totalConteos, tittleStyle);
            createCell(row, 2, "", tittleStyle);
            createCell(row, 3, totalkm, tittleStyle);
            createCell(row, 4, totalIPK, tittleStyle);
            createCell(row, 5, totalVueltasLiquidadas, tittleStyle);
            createCell(row, 6, totalPasajeros, tittleStyle);
            createCell(row, 7, totalPasajerosDescontados, tittleStyle);
            createCell(row, 8, totalPasajerosLiquidados, tittleStyle);
            createCell(row, 9, "", tittleStyle);
            createCell(row, 10, totalBruto, tittleStyle);
            createCell(row, 11, totalDescuentoAlNeto, tittleStyle);
            createCell(row, 12, subTotal, tittleStyle);
            createCell(row, 13, totalDescuentos, tittleStyle);
            createCell(row, 14, totalOtrosDescuentos, tittleStyle);
            createCell(row, 15, totalLiquidaciones, tittleStyle);
        } else {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, totalConteos, tittleStyle);
            createCell(row, 2, "", tittleStyle);
            createCell(row, 3, totalkm, tittleStyle);
            createCell(row, 4, totalIPK, tittleStyle);
            createCell(row, 5, totalVueltasLiquidadas, tittleStyle);
            createCell(row, 6, totalPasajeros, tittleStyle);
            createCell(row, 7, totalPasajerosDescontados, tittleStyle);
            createCell(row, 8, totalPasajerosLiquidados, tittleStyle);
            createCell(row, 9, "", tittleStyle);
            createCell(row, 10, totalBruto, tittleStyle);
            createCell(row, 11, totalDescuentos, tittleStyle);
            createCell(row, 12, subTotal, tittleStyle);
            createCell(row, 13, totalOtrosDescuentos, tittleStyle);
            createCell(row, 14, totalLiquidaciones, tittleStyle);
        }//fin else
    }

    public void totalesConsolidado(HSSFSheet hoja, int totalConteos, int vehiculos, int totalvueltasLiquidadas, int totalPasajerosLiquidados,
            int totalPasajerosDescontados, int totalPasajeros, double totalBruto, double totalDescuentoAlNeto, double totalDescuentosOperativos,
            double totalDescuentosAdminsitrativos, double totalSubtotal, double totalLiquidaciones, String nombreEmpresa) {
        CellStyle tittleStyle;
        Row row = hoja.createRow((short) (totalConteos));
        tittleStyle = getTitleStyle(libro);
        Font fontTitle = getTitleFont(libro);
        tittleStyle.setFont(fontTitle);

        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))) {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, vehiculos, tittleStyle);
            createCell(row, 2, totalvueltasLiquidadas, tittleStyle);
            createCell(row, 3, totalPasajerosLiquidados, tittleStyle);
            createCell(row, 4, totalPasajerosDescontados, tittleStyle);
            createCell(row, 5, totalPasajeros, tittleStyle);
            createCell(row, 6, "", tittleStyle);
            createCell(row, 7, totalBruto, tittleStyle);
            createCell(row, 8, totalDescuentoAlNeto, tittleStyle);
            createCell(row, 9, totalSubtotal, tittleStyle);
            createCell(row, 10, totalDescuentosOperativos, tittleStyle);
            createCell(row, 11, totalDescuentosAdminsitrativos, tittleStyle);
            createCell(row, 12, totalLiquidaciones, tittleStyle);
        } else {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, vehiculos, tittleStyle);
            createCell(row, 2, totalvueltasLiquidadas, tittleStyle);
            createCell(row, 3, totalPasajerosLiquidados, tittleStyle);
            createCell(row, 4, totalPasajerosDescontados, tittleStyle);
            createCell(row, 5, totalPasajeros, tittleStyle);
            createCell(row, 6, "", tittleStyle);
            createCell(row, 7, totalBruto, tittleStyle);
            createCell(row, 8, totalDescuentosOperativos, tittleStyle);
            createCell(row, 9, totalSubtotal, tittleStyle);
            createCell(row, 10, totalDescuentosAdminsitrativos, tittleStyle);
            createCell(row, 11, totalLiquidaciones, tittleStyle);
        }
    }

    public void totales(HSSFSheet hoja, int totalConteos, int totalVehiculos,
            int totalLiquidaciones, int totalPasajerosDescontados,
            double totalSumaTotalOtrosDescuentos, double totalSumaBruto, double totalDescuentoAlNeto,
            double totalSumaDescuentos, double totalSumaSubtotal, double totalTodasLiquidaciones, String nombreEmpresa) {
        CellStyle tittleStyle;
        Row row = hoja.createRow((short) (totalConteos));
        tittleStyle = getTitleStyle(libro);
        Font fontTitle = getTitleFont(libro);
        tittleStyle.setFont(fontTitle);
        if (nombreEmpresa.equalsIgnoreCase("FUSACATAN") || nombreEmpresa.equalsIgnoreCase("tierragrata") || nombreEmpresa.equalsIgnoreCase("tierra grata")) {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, totalVehiculos, tittleStyle);
            createCell(row, 2, totalLiquidaciones, tittleStyle);
            createCell(row, 3, "", tittleStyle);
            createCell(row, 4, totalPasajerosDescontados, tittleStyle);
            createCell(row, 5, totalSumaBruto, tittleStyle);
            createCell(row, 6, totalDescuentoAlNeto, tittleStyle);
            createCell(row, 7, totalSumaSubtotal, tittleStyle);
            createCell(row, 8, totalSumaDescuentos, tittleStyle);
            createCell(row, 9, totalSumaTotalOtrosDescuentos, tittleStyle);
            createCell(row, 10, totalTodasLiquidaciones, tittleStyle);
        } else {
            createCell(row, 0, "TOTALES", tittleStyle);
            createCell(row, 1, totalVehiculos, tittleStyle);
            createCell(row, 2, totalLiquidaciones, tittleStyle);
            createCell(row, 3, "", tittleStyle);
            createCell(row, 4, totalPasajerosDescontados, tittleStyle);
            createCell(row, 5, totalSumaBruto, tittleStyle);
            createCell(row, 6, totalSumaDescuentos, tittleStyle);
            createCell(row, 7, totalSumaSubtotal, tittleStyle);
            createCell(row, 8, totalSumaTotalOtrosDescuentos, tittleStyle);
            createCell(row, 9, totalTodasLiquidaciones, tittleStyle);
        }//FIN ELSE
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Rellenar hoja
    ////////////////////////////////////////////////////////////////////////////
    // Rellena celdas de hoja con espacios en blanco.
    public void rellenarHoja(HSSFSheet hoja, int rownum, int cellnum) {
        try {
            if (rownum > 0) {
                for (int i = rownum; i < rownum + 100; i++) {
                    Row fila = hoja.createRow(i);
                    for (int j = 0; j < cellnum; j++) {
                        Cell celda = fila.createCell(j);
                        setCellValue(celda, " ");
                    }
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    /// Informacion registradora
    ////////////////////////////////////////////////////////////////////////////
    // Crea hoja (para reporte excel) con informacion registradora.
    public void addSheet_IR(String name, ArrayList<InformacionRegistradora> data) {
        HSSFSheet hoja = libro.createSheet(name);

        String titles[] = {
            "ID", "PLACA", "N° INTERNO", "RUTA", "CONDUCTOR",
            "FECHA INGRESO", "HORA INGRESO", "N° VUELTA",
            "NUM SALIDA", "NUM LLEGADA", "DIF NUMERACION",
            "ENTRADAS BS", "ENTRADAS", "DIF ENTRADAS",
            "SALIDAS BS", "SALIDAS", "DIF SALIDAS", "TOTAL DIA"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos de la tabla
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue((String) title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (InformacionRegistradora ir : data) {
            Row fila = hoja.createRow(rownum++);
            cellnum = 0;
            Object celldata[] = {
                ir.getId(), ir.getPlaca(), ir.getNumeroInterno(), ir.getNombreRuta(),
                ir.getNombreConductor(), ir.getFechaIngreso(), ir.getHoraIngreso(),
                ir.getNumeroVuelta(), ir.getNumeroVueltaAnterior(), ir.getNumeroLlegada(),
                ir.getDiferenciaNumero(), ir.getEntradasBS(), ir.getEntradas(), ir.getDiferenciaEntrada(),
                ir.getSalidasBS(), ir.getSalidas(), ir.getDiferenciaSalida(), ir.getTotalDia()
            };

            for (Object idata : celldata) {
                Cell celda = fila.createCell(cellnum);
                setCellValue(celda, idata);

                if (cellnum == 0 || cellnum == 3) {
                    celda.setCellStyle(estiloBold);
                }
                if (cellnum == 4) {
                    celda.setCellStyle(estiloItalic);
                }

                cellnum++;
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    // Crea hoja (para reporte excel) con informacion de alarmas.
    public void addSheet_ALM(String name, ArrayList<InformacionRegistradora> data) {
        HSSFSheet hoja = libro.createSheet(name);

        String titles[] = {
            "ID VUELTA", "ALARMA", "FECHA", "HORA", "CANTIDAD"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos de la tabla
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue((String) title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (InformacionRegistradora ir : data) {
            // Lista de alarmas
            for (AlarmaInfoReg air : ir.getLstalarma()) {
                Row fila = hoja.createRow(rownum++);
                cellnum = 0;
                Object celldata[] = {
                    ir.getId(), air.getNombre(), air.getFechaAlarma(), air.getHoraAlarma(), air.getCantidad()
                };

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);

                    if (cellnum == 0 || cellnum == 1) {
                        celda.setCellStyle(estiloBold);
                    }

                    cellnum++;
                }
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    // Crea hoja (para reporte excel) con informacion de puntos de control.
    public void addSheet_PC(String name, ArrayList<InformacionRegistradora> data) {
        HSSFSheet hoja = libro.createSheet(name);

        String titles[] = {
            "ID VUELTA", "PUNTO", "FECHA", "HORA", "NUMERACION", "ENTRADAS", "SALIDAS"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos de la tabla
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue((String) title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (InformacionRegistradora ir : data) {

            // Informacion de base salida
            Object base_salida[] = {
                ir.getId(), ir.getNombreBS(), ir.getFechaSalidaBS(), ir.getHoraSalidaBS(),
                ir.getNumeracionBS(), ir.getEntradasBS(), ir.getSalidasBS()
            };

            Row fila_bs = hoja.createRow(rownum++);
            cellnum = 0;
            for (Object idata_bs : base_salida) {
                Cell celda_bs = fila_bs.createCell(cellnum);
                setCellValue(celda_bs, idata_bs);

                if (cellnum == 0 || cellnum == 1) {
                    celda_bs.setCellStyle(estiloBold);
                }

                cellnum++;
            }

            // Lista puntos de control
            for (PuntoControl pc : ir.getLstptocontrol()) {
                Row fila = hoja.createRow(rownum++);
                cellnum = 0;

                Object celldata[] = {
                    ir.getId(), pc.getNombre(), pc.getFechaPuntoControl(), pc.getHoraPuntoControl(),
                    pc.getNumeracion(), pc.getEntradas(), pc.getSalidas()
                };

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);

                    if (cellnum == 0) {
                        celda.setCellStyle(estiloBold);
                    }

                    cellnum++;
                }
            }

            // Informacion de base llegada
            Object base_llegada[] = {
                ir.getId(), ir.getNombreBL(), ir.getFechaIngreso(), ir.getHoraIngreso(),
                ir.getNumeroLlegada(), ir.getEntradas(), ir.getSalidas()
            };

            Row fila_bl = hoja.createRow(rownum++);
            cellnum = 0;
            for (Object idata_bl : base_llegada) {
                Cell celda_bl = fila_bl.createCell(cellnum);
                setCellValue(celda_bl, idata_bl);

                if (cellnum == 0 || cellnum == 1) {
                    celda_bl.setCellStyle(estiloBold);
                }

                cellnum++;
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    // Crea hoja (para reporte excel) con informacion de conteo perimetro.
    public void addSheet_CP(String name, ArrayList<InformacionRegistradora> data) {
        HSSFSheet hoja = libro.createSheet(name);

        String titles[] = {
            "ID VUELTA", "FECHA", "HORA", "NUM INICIAL", "NUM FINAL", "DIFERENCIA", "ENTRADAS", "SALIDAS"
        };

        int rownum, cellnum;
        rownum = cellnum = 0;

        // Titulos de la tabla
        Row filacab = hoja.createRow(rownum++);
        for (String title : titles) {
            Cell celdacab = filacab.createCell(cellnum++);
            celdacab.setCellValue((String) title);
            celdacab.setCellStyle(estiloTitulo);
        }
        
        if (data == null || data.size() == 0) {
            return;
        }

        for (InformacionRegistradora ir : data) {
            for (ConteoPerimetro cp : ir.getLstcp()) {
                Row fila = hoja.createRow(rownum++);
                cellnum = 0;

                Object celldata[] = {
                    ir.getId(), cp.getFechaConteo(), cp.getHoraIngreso(), cp.getNumInicial(),
                    cp.getNumFinal(), cp.getDiferencia(), cp.getEntradas(), cp.getSalidas()
                };

                for (Object idata : celldata) {
                    Cell celda = fila.createCell(cellnum);
                    setCellValue(celda, idata);

                    if (cellnum == 0) {
                        celda.setCellStyle(estiloBold);
                    }

                    cellnum++;
                }
            }
        }

        // Establecer longitud de las celdas conforme a su contenido
        for (int i = 0; i < rownum; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    // Escribe reporte en archivo ubicado en directorio path.
    public void export(String path) {
        try {
            FileOutputStream out = new FileOutputStream(new File(path));
            libro.write(out);
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    // Establece valor en celda segun el tipo referenciado por idata.
    public void setCellValue(Cell celda, Object idata) {
        if (idata instanceof String) {
            celda.setCellValue((String) idata);
        } else if (idata instanceof Integer) {
            celda.setCellValue((Integer) idata);
        } else if (idata instanceof Double) {
            celda.setCellValue((Double) idata);
        } else if (idata instanceof Date) {
            celda.setCellValue(idata.toString());
        } else if (idata instanceof Long) {
            celda.setCellValue((Long) idata);
        }
    }

    // Establece valor para variable de color usado en la
    // coloracion de celdas.
    public HSSFColor setColor(HSSFWorkbook wb, byte r, byte g, byte b) {
        HSSFPalette palette = wb.getCustomPalette();
        HSSFColor hssfColor = null;
        try {
            hssfColor = palette.findColor(r, g, b);
            if (hssfColor == null) {
                palette.setColorAtIndex(HSSFColor.PALE_BLUE.index, r, g, b);
                hssfColor = palette.getColor(HSSFColor.PALE_BLUE.index);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return hssfColor;
    }

    // Crea estilo de celda en color rojo.
    public final CellStyle getRedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        return style;
    }

    // Crea estilo de celda en color naranja.
    public final CellStyle getOrangeStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        return style;
    }

    // Crea estilo de celda en color gris.
    public final CellStyle getGreenStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        return style;
    }

    // Crea estilo de celda en color gris claro.
    public final CellStyle getGreyStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        return style;
    }

    // Crea estilo de celda en color especificado.
    public final CellStyle getRowStyle(HSSFWorkbook wb, int color_index) {
        CellStyle style = wb.createCellStyle();
        HSSFColor rowColor
                = (color_index == 1) ? setColor(wb, (byte) 186, (byte) 208, (byte) 220)
                        : // Azul gris claro
                        (color_index == 2) ? setColor(wb, (byte) 247, (byte) 247, (byte) 247)
                                : // Gris claro
                                setColor(wb, (byte) 224, (byte) 227, (byte) 233);     // Azul claro
        style.setFillForegroundColor(rowColor.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return style;
    }

    // Crea estilo de celda para titulos.
    public final CellStyle getTitleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return style;
    }

    // Crea estilo de celda para mensaje de error.
    public final CellStyle getErrorStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return style;
    }

    // Crea estilo de celda por defecto.
    public final CellStyle getDefaultStyle(Workbook wb) {
        return wb.createCellStyle();
    }

    // Crea estilo de fuente.
    public final Font getTitleFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.WHITE.getIndex());

        return font;
    }

    // Crea estilo de fuente segun tipo especificado.
    // (italica, resaltada, ambas o ninguna).
    public final Font getFont(Workbook wb, boolean italic, boolean bold) {
        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setItalic(italic);
        if (bold) {
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        }

        return font;
    }
}
