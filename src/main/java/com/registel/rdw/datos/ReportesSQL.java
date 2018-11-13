/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.utils.Restriction;

/**
 *
 * @author lider_desarrollador
 */
public class ReportesSQL {
    
    // Instruccion sql para consulta de alarmas
    // ocurridas en vueltas de un vehiculo en un
    // rango de fechas especifico.
    public static String sql_AlarmasXVehiculo() {
        String sql
                = "SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " IFNULL(r.NOMBRE, 'NA') AS NOMBRE_RUTA,"
                + " air.FECHA_ALARMA AS FECHA_ALARMA,"
                + " air.HORA_ALARMA AS HORA_ALARMA,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.FECHA_INGRESO AS FECHA_INGRESO,"
                + " a.NOMBRE AS NOMBRE_ALARMA,"
                + " air.CANTIDAD_ALARMA AS CANTIDAD_ALARMA,"
                + " a.UNIDAD_MEDICION AS UNIDAD_MEDICION"
                + " FROM tbl_informacion_registradora AS ir"
                + " LEFT JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " INNER JOIN tbl_alarma_info_regis AS air ON"
                + "   ir.PK_INFORMACION_REGISTRADORA = air.FK_INFORMACION_REGISTRADORA"
                + " INNER JOIN tbl_alarma AS a ON"
                + "   a.PK_ALARMA = air.FK_ALARMA AND a.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   v.PK_VEHICULO = ir.FK_VEHICULO AND v.ESTADO = 1"
                + " WHERE ir.FK_VEHICULO = ? AND"
                + "   ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + "   air.ESTADO = 1 ORDER BY air.FECHA_ALARMA ASC, air.HORA_ALARMA ASC";
        return sql;
    }

    // Instruccion sql para consulta de producido (cantidad de pasajeros) 
    // hecho por un vehiculo en un rango de fechas especifico.
    public static String sql_ProduccionXVehiculo() {
        String sql
                = "SELECT"
                + " ir.FECHA_INGRESO AS FECHA_INGRESO,"
                + " ir.HORA_INGRESO AS HORA_INGRESO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.NUM_LLEGADA AS NUMERACION_LLEGADA,"
                + " ir.NUMERACION_BASE_SALIDA AS NUMERACION_SALIDA,"
                + " ir.ENTRADAS AS ENTRADAS,"
                + " ir.SALIDAS AS SALIDAS,"
                + " ir.TOTAL_DIA AS TOTAL_DIA,"
                //+ " ir.DIFERENCIA_NUM AS DIFERENCIA_NUMERACION,"
                + " (ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS DIFERENCIA_NUMERACION,"                
                + " IF ((SELECT sum(cp.DIFERENCIA) FROM tbl_conteo_perimetro AS cp WHERE "
                + "     cp.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) > 0,"                
                + "     (SELECT sum(cp.DIFERENCIA) FROM tbl_conteo_perimetro AS cp WHERE"
                + "     cp.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA),"
                + "     0) AS CONTEO_PERIMETRO,"
                + " IF ((SELECT count(air.CANTIDAD_ALARMA) FROM tbl_alarma_info_regis AS air WHERE"
                + "     air.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA AND air.FK_ALARMA IN (5,6,7)) > 0,"                 
                + "     (SELECT count(air.CANTIDAD_ALARMA) FROM tbl_alarma_info_regis AS air WHERE"
                + "     air.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA AND air.FK_ALARMA IN (5,6,7)),"
                + "     0) AS CANTIDAD_ALARMAS,"
                + " IFNULL((SELECT r.NOMBRE FROM tbl_ruta AS r WHERE r.PK_RUTA = ir.FK_RUTA AND r.ESTADO = 1), 'NA') AS NOMBRE_RUTA,"
                + " IFNULL(LCASE(c.NOMBRE), 'NA') AS NOMBRE_CONDUCTOR,"
                + " IFNULL(LCASE(c.APELLIDO), '') AS APELLIDO_CONDUCTOR,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA_SALIDA,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO"
                + " FROM tbl_vehiculo AS v"
                + " INNER JOIN tbl_informacion_registradora AS ir ON"
                + "   v.PK_VEHICULO = ir.FK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_empresa AS e ON"
                + "   v.FK_EMPRESA = e.PK_EMPRESA AND v.ESTADO = 1"
                + " LEFT JOIN tbl_conductor AS c ON" 
                + "   e.PK_EMPRESA = c.FK_EMPRESA AND c.PK_CONDUCTOR = ir.FK_CONDUCTOR AND v.ESTADO = 1"
                //+ " LEFT JOIN tbl_ruta AS r ON"
                //+ "   ir.FK_RUTA = r.PK_RUTA"
                + " WHERE v.PK_VEHICULO = ? AND"
                + "   ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + "   e.PK_EMPRESA = ?"
                //+ "   AND e.PK_EMPRESA IN ( SELECT upe.FK_EMPRESA FROM tbl_usuario_permiso_empresa AS upe WHERE upe.ESTADO = 1 AND upe.FK_USUARIO = ? )"
                + " GROUP BY"
                + "  ir.HORA_INGRESO"
                + " ORDER BY ir.FECHA_INGRESO ASC, ir.HORA_INGRESO ASC";
        return sql;
    }

    // Instruccion sql para consulta de producido (cantidad de pasajeros)
    // hecho por los vehiculos que recorrieron determinada ruta en
    // un rango de fechas especifico.
    public static String sql_ProduccionXRuta() {
        String sql
                = "SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " count(ir.NUMERO_VUELTA) AS CANTIDAD_VUELTAS,"
                + " sum(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS CANTIDAD_PASAJEROS,"
                + " avg(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS PROMEDIO_PASAJEROS,"
                + " ir.FECHA_INGRESO AS FECHA_INGRESO"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + "   ir.FK_RUTA = ?"
                + " GROUP BY ir.FECHA_INGRESO, v.PLACA";
        return sql;
    }
    
    // Instruccion sql para consulta de producido (cantidad de pasajeros)
    // hecho por los vehiculos en cada hora.
    public static String sql_ProduccionXHora() {
                
        String sql = "SELECT" 
                + " date(fecha_gps) as FECHA,"
                + " placa as PLACA,"
                + " hour(time(fecha_gps)) as HORA,"
                + " min(entradas) as ENTRADAS_INICIAL,"
                + " max(entradas) as ENTRADAS_FINAL"
                + " FROM tbl_forwarding_wtch"
                + " WHERE fecha_gps BETWEEN ? AND ?"                
                //+ " AND entradas > 0"
                + " GROUP BY date(fecha_gps), placa, hour(time(fecha_gps))";
        
        //        = "SELECT" 
        //        + " date(fecha_gps) as FECHA,"
        //        + " placa as PLACA,"
        //        + " hour(time(fecha_gps)) as HORA,"
        //        + " min(numeracion) as NUMERACION_INICIAL,"
        //        + " max(numeracion) as NUMERACION_FINAL"
        //        //+ " max(numeracion) - min(numeracion) as CANTIDAD_PASAJEROS"
        //        + " FROM tbl_forwarding_wtch"
        //        + " WHERE fecha_gps BETWEEN ? AND ?"
        //        + " AND numeracion > 0"
        //        + " GROUP BY date(fecha_gps), placa, hour(time(fecha_gps))";        
        
        //        String sql
        //            = "SELECT"
        //            + " date(fecha_gps) as FECHA,"
        //            + " placa as PLACA,"
        //            + " hour(time(fecha_gps)) as HORA,"            
        //
        //            + " if (hour(time(fecha_gps)) = 0,"
        //            + "     min(numeracion),"
        //            + "     ifnull ((SELECT max(fwi.numeracion) FROM tbl_forwarding_wtch as fwi"
        //            + "             WHERE fwi.placa = fw.placa AND"
        //            + "             fwi.fecha_gps < fw.fecha_gps AND"
        //            + "             fwi.numeracion > 0), min(numeracion))) as NUMERACION_INICIAL,"
        //
        //            + " max(numeracion) as NUMERACION_FINAL"
        //            + " FROM tbl_forwarding_wtch as fw"
        //            + " WHERE fecha_gps BETWEEN ? AND ?"
        //            + " AND numeracion > 0"
        //            + listaMovil
        //            + " GROUP BY date(fecha_gps), placa, hour(time(fecha_gps))";                
        
        return sql;
    }

    // Instruccion sql para consulta de puntos de control
    // reportados por un vehiculo en lo recorrido del dia.
    public static String sql_PuntosControl_GPS() {
               
        String sql
                = "SELECT"
                
                + " DATE(fw.fecha_gps) AS GPS_FECHA,"
                + " TIME(fw.fecha_gps) AS GPS_HORA,"
                + " fw.msg AS GPS_PUNTO_CONTROL,"
                + " LCASE(REPLACE(REPLACE(fw.localizacion, '<B>', ''), '</B>', '')) AS GPS_PUNTO_CONTROL_2,"
                + " fw.numeracion AS GPS_NUMERACION,"
                + " fw.placa AS GPS_PLACA,"
                + " (SELECT MIN(fwn.numeracion) FROM tbl_forwarding_wtch AS fwn"
                + "  WHERE fwn.fecha_gps BETWEEN ? AND ?"
                + "  AND fwn.placa = ? AND fwn.numeracion > 0) AS GPS_NUMERACION_INICIAL"
                
                + " FROM tbl_forwarding_wtch as fw WHERE fw.id IN ("
                + "     SELECT"
                + "         IF(fwi.msg LIKE '%Punto de Control%', fwi.id, 0) AS IDPC"
                + "     FROM tbl_forwarding_wtch AS fwi"
                + "     WHERE fwi.fecha_gps BETWEEN ? AND ?"
                + "     AND fwi.placa = ? AND fwi.numeracion > 0"
                + " ) ORDER BY fw.fecha_gps DESC";
        return sql;
    }

    // Instruccion sql para consulta de puntos de control
    // (base salida/punto control/base llegada)
    // registrados por un vehiculo en un rango de fechas especifico.
    public static String sql_PuntosControl() {
        String sql
                = "SELECT * FROM ("
                + " SELECT"
                + " 1 AS TIPO_PTR,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " b.NOMBRE AS NOMBRE_PUNTO,"
                + " ir.FECHA_SALIDA_BASE_SALIDA AS FECHA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA,"
                + " ir.NUMERACION_BASE_SALIDA AS NUMERACION,"
                + " ir.ENTRADAS_BASE_SALIDA AS ENTRADAS,"
                + " ir.SALIDAS_BASE_SALIDA AS SALIDAS,"
                + " (SELECT count(air.CANTIDAD_ALARMA) FROM tbl_alarma_info_regis AS air"
                + "  WHERE air.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) AS CANTIDAD_ALARMAS"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE_SALIDA = b.PK_BASE AND b.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                //+ "   ir.FECHA_SALIDA_BASE_SALIDA BETWEEN ? AND ? AND" /* *** */
                + "   ir.FK_VEHICULO = ?"
                + " UNION"
                + " SELECT"
                + " 2 AS TIPO_PTR,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " p.NOMBRE AS NOMBRE_PUNTO,"
                + " pc.FECHA_PTO_CONTROL AS FECHA,"
                + " pc.HORA_PTO_CONTROL AS HORA,"
                + " pc.NUMERACION AS NUMERACION,"                
                + " pc.ENTRADAS AS ENTRADAS,"
                + " pc.SALIDAS AS SALIDAS,"
                + " 0 AS CANTIDAD_ALARMAS"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_punto_control AS pc ON"
                + "   ir.PK_INFORMACION_REGISTRADORA = pc.FK_INFO_REGIS"
                //+ "   pc.FECHA_PTO_CONTROL = ir.FECHA_INGRESO"
                + " INNER JOIN tbl_punto AS p ON"
                + "   pc.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                //+ "   ir.FECHA_SALIDA_BASE_SALIDA BETWEEN ? AND ? AND" /* *** */
                + "   ir.FK_VEHICULO = ?"
                + " UNION"
                + " SELECT"
                + " 3 AS TIPO_PTR,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " b.NOMBRE AS NOMBRE_PUNTO,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " ir.HORA_INGRESO AS HORA,"
                + " ir.NUM_LLEGADA AS NUMERACION,"
                + " ir.ENTRADAS AS ENTRADAS,"
                + " ir.SALIDAS AS SALIDAS,"
                + " 0 AS CANTIDAD_ALARMAS"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                //+ "   ir.FECHA_SALIDA_BASE_SALIDA BETWEEN ? AND ? AND" /* *** */
                + "   ir.FK_VEHICULO = ?"
                + " ) AS Q ORDER BY Q.FECHA ASC, Q.HORA ASC";

        return sql;
    }

    // Instruccion sql para consulta de vehiculos
    // que emitieron una alarma especifica en un rango de fechas.
    public static String sql_VehiculosXAlarma(String lst_alm) {
        
        lst_alm = (lst_alm == null || Restriction.includeSQL(lst_alm)) ? "" : lst_alm;
        String sql
                = "SELECT"
                + " a.CODIGO_ALARMA,"
                + " a.NOMBRE AS NOMBRE_ALARMA,"
                + " a.UNIDAD_MEDICION AS UNIDAD_MEDICION,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " IFNULL(r.NOMBRE, 'NA') AS NOMBRE_RUTA,"
                + " air.FECHA_ALARMA AS FECHA_ALARMA,"
                + " air.HORA_ALARMA AS HORA_ALARMA,"
                + " air.CANTIDAD_ALARMA AS CANTIDAD_ALARMA"
                + " FROM tbl_informacion_registradora AS ir"
                + " LEFT JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_alarma_info_regis AS air ON"
                + "   ir.PK_INFORMACION_REGISTRADORA = air.FK_INFORMACION_REGISTRADORA"
                + " INNER JOIN tbl_alarma AS a ON"
                + "   air.FK_ALARMA = a.PK_ALARMA AND a.ESTADO = 1"
                + " WHERE air.FECHA_ALARMA BETWEEN ? AND ? AND"
                + "   air.FK_ALARMA IN (" + lst_alm + ")"
                //+ " ORDER BY air.FECHA_ALARMA ASC, air.HORA_ALARMA ASC, r.NOMBRE ASC";
                + " ORDER BY air.FK_ALARMA ASC, r.NOMBRE ASC, v.PLACA ASC, air.FECHA_ALARMA ASC, air.HORA_ALARMA ASC";

        return sql;
    }

    // Instruccion sql para consulta de informacion relacionada al
    // producido (cantidad de pasajeros) por cada vehiculo de una
    // determinada empresa en un rango de fechas.
    public static String sql_ConsolidadoXEmpresa() {
        String sql
                = "SELECT"
                + " e.NOMBRE AS NOMBRE_EMPRESA,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " count(ir.NUMERO_VUELTA) AS CANTIDAD_VUELTAS,"
                + " sum(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS CANTIDAD_PASAJEROS,"
                + " avg(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS PROMEDIO_PASAJEROS,"                                
                
                + " (SELECT IF(sum(cp.DIFERENCIA) >= 0, sum(cp.DIFERENCIA), 0)"
                + "  FROM tbl_conteo_perimetro AS cp WHERE cp.FK_INFORMACION_REGISTRADORA IN ("
                + "     SELECT iir.PK_INFORMACION_REGISTRADORA"
                + "     FROM tbl_informacion_registradora AS iir WHERE"
                + "     iir.FECHA_INGRESO = ir.FECHA_INGRESO AND"
                + "     iir.FECHA_SALIDA_BASE_SALIDA = ir.FECHA_INGRESO AND"
                + "     iir.FK_VEHICULO = v.PK_VEHICULO"
                + " )) AS CONTEO_PERIMETRO,"
                
                + " (SELECT IF(count(air.CANTIDAD_ALARMA) >= 0, count(air.CANTIDAD_ALARMA), 0)"
                + "  FROM tbl_alarma_info_regis AS air"
                + "  WHERE"
                + "     air.FK_ALARMA IN (5,6,7) AND"
                + "     air.FK_INFORMACION_REGISTRADORA IN ("
                + "     SELECT iir.PK_INFORMACION_REGISTRADORA"
                + "     FROM tbl_informacion_registradora AS iir WHERE"
                + "     iir.FECHA_INGRESO = ir.FECHA_INGRESO AND"
                + "     iir.FECHA_SALIDA_BASE_SALIDA = ir.FECHA_INGRESO AND"
                + "     iir.FK_VEHICULO = v.PK_VEHICULO"
                + " )) AS CONTEO_ALARMAS"
                
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " INNER JOIN tbl_empresa AS e ON"
                + "   v.FK_EMPRESA = e.PK_EMPRESA AND e.ESTADO = 1"
                + " WHERE"
                + " ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + " e.PK_EMPRESA = ?"
                //+ " AND e.PK_EMPRESA IN ("
                //+ "     SELECT upe.FK_EMPRESA FROM tbl_usuario_permiso_empresa AS upe WHERE"
                //+ " upe.ESTADO = 1 AND upe.FK_USUARIO = ?"
                //+ " )"
                + " GROUP BY ir.FECHA_INGRESO, ir.FK_VEHICULO"
                + " ORDER BY ir.FECHA_INGRESO ASC, v.PLACA ASC";
        return sql;
    }

    // Instruccion sql para consulta de producido (cantidad de pasajeros)
    // de un determinado numero de rutas y rango de fecha especificado.
    public static String sql_ComparativoProduccionRuta(String listaRutas) {
                
        listaRutas = (listaRutas == null || Restriction.includeSQL(listaRutas)) ? "" : listaRutas;
        String sql
                = "SELECT"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " sum((ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA)) AS CANTIDAD_PASAJEROS,"
                + " ir.FECHA_INGRESO AS FECHA"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ?"
                + " AND ir.FK_RUTA IN (" + listaRutas + ")"
                + " GROUP BY ir.FECHA_INGRESO, ir.FK_RUTA"
                + " ORDER BY r.NOMBRE ASC, ir.FECHA_INGRESO ASC";
        return sql;
    }

    // Instruccion sql para consulta de nivel de ocupacion
    // junto a indice de capacidad utilizada en cada punto, 
    // hecha por un vehiculo en una o varias rutas.
    public static String sql_NivelOcupacion(boolean unaRuta, int capacidad) {

        String expresionICU
                = (capacidad > 0)
                        ? " IF((pc.ENTRADAS - pc.SALIDAS) > 0,"
                        + "   ((pc.ENTRADAS - pc.SALIDAS) / " + capacidad + ") * 100,"
                        + "   0)"
                        : " 0";

        expresionICU += " AS ICU";

        String sql
                = "SELECT * FROM ("
                + " SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.FK_RUTA AS RUTA_PK,"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " b.NOMBRE AS NOMBRE_PUNTO,"
                + " ir.FECHA_SALIDA_BASE_SALIDA AS FECHA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA,"
                + " 0 AS NIVEL_OCUPACION,"
                + " 0 AS ICU,"
                + " 1 AS TIPO_PUNTO"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE_SALIDA = b.PK_BASE AND b.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + "   ir.FK_VEHICULO = ?"
                + " UNION"  // UNION
                + " SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.FK_RUTA AS RUTA_PK,"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " p.NOMBRE AS NOMBRE_PUNTO,"
                + " pc.FECHA_PTO_CONTROL AS FECHA,"
                + " pc.HORA_PTO_CONTROL AS HORA,"
                + " IF((pc.ENTRADAS - pc.SALIDAS) > 0,"
                + "    (pc.ENTRADAS - pc.SALIDAS),"
                + "    0) AS NIVEL_OCUPACION,"
                + expresionICU
                + " , 2 AS TIPO_PUNTO"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_punto_control AS pc ON"
                + "   ir.PK_INFORMACION_REGISTRADORA = pc.FK_INFO_REGIS AND"
                + "   pc.FECHA_PTO_CONTROL = ir.FECHA_INGRESO"
                + " INNER JOIN tbl_punto AS p ON"
                + "   pc.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + "   ir.FK_VEHICULO = ?"
                + " UNION"  // UNION
                + " SELECT" 
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.FK_RUTA AS RUTA_PK,"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " b.NOMBRE AS NOMBRE_PUNTO,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " ir.HORA_INGRESO AS HORA,"
                + " 0 AS NIVEL_OCUPACION,"
                + " 0 AS ICU,"
                + " 3 AS TIPO_PUNTO"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + "   ir.FK_VEHICULO = ?"
                + " ) AS Q "; //ORDER BY Q.FECHA ASC, Q.HORA ASC";

        if (unaRuta) {
            sql += "WHERE Q.RUTA_PK = ? ORDER BY Q.FECHA ASC, Q.HORA ASC";
        } else {
            sql += "ORDER BY Q.FECHA ASC, Q.HORA ASC";
        }

        return sql;
    }

    // Instruccion sql para consulta de conteos en perimetro
    // de un vehiculo y rango de fechas determinado.
    public static String sql_ConteoPerimetro() {
        String sql
                = "SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " IFNULL(r.NOMBRE, 'NA') AS NOMBRE_RUTA,"
                + " cp.FECHA_CONTEO AS FECHA,"
                + " cp.HORA_INGRESO AS HORA,"
                + " (ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS CANTIDAD_PASAJEROS,"
                + " 1 AS CONTEO_PERIMETRO"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " INNER JOIN tbl_conteo_perimetro AS cp ON"
                + "   ir.PK_INFORMACION_REGISTRADORA = cp.FK_INFORMACION_REGISTRADORA"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + " ir.FK_VEHICULO = ?"
                + " ORDER BY r.NOMBRE ASC, ir.FECHA_INGRESO ASC, ir.HORA_INGRESO ASC";

        return sql;
    }

    // Instruccion sql para consulta de rutas
    // asociadas a una empresa determinada.
    public static String sql_RutasEstablecidas() {
        String sql
                = "SELECT"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " r.ESTADO_CREACION AS ESTADO_CREACION"
                + " FROM tbl_ruta AS r"
                + " WHERE r.ESTADO = 1 AND"                
                + " r.ESTADO_CREACION <> 3 AND"
                + " r.FK_EMPRESA = ?";

        return sql;
    }

    // Instruccion sql para consulta de vehiculos asociados
    // a una empresa determinada.
    public static String sql_VehiculosEstablecidos() {
        String sql
                = "SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " e.NOMBRE AS NOMBRE_EMPRESA,"
                + " (SELECT ir.FECHA_INGRESO AS FECHA"
                + "   FROM tbl_informacion_registradora AS ir"
                + "   WHERE ir.FK_VEHICULO = v.PK_VEHICULO"
                + "   ORDER BY ir.FECHA_INGRESO DESC LIMIT 1) AS FECHA_ULTIMA_VUELTA,"
                + " (SELECT IFNULL(DATEDIFF(NOW(), ir.FECHA_INGRESO), -1)"
                + "   FROM tbl_informacion_registradora AS ir"
                + "   WHERE ir.FK_VEHICULO = v.PK_VEHICULO"
                + "   ORDER BY ir.FECHA_INGRESO DESC LIMIT 1) AS DIAS_ULTIMA_VUELTA"
                + " FROM tbl_empresa AS e"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   e.PK_EMPRESA = v.FK_EMPRESA"
                + " WHERE "
                + " e.ESTADO = 1 AND"
                + " v.ESTADO = 1 AND"
                + " e.PK_EMPRESA = ?"
                + " ORDER BY v.PLACA";

        return sql;
    }

    // Instruccion sql para consulta de conductores asociados
    // a una empresa determinada.
    public static String sql_ConductoresEstablecidos() {
        String sql
                = "SELECT"
                + " LCASE(c.NOMBRE) AS NOMBRE,"
                + " LCASE(c.APELLIDO) AS APELLIDO,"
                + " c.CEDULA AS CEDULA,"
                + " e.NOMBRE AS NOMBRE_EMPRESA"
                + " FROM tbl_empresa AS e"
                + " INNER JOIN tbl_conductor AS c ON"
                + "   e.PK_EMPRESA = c.FK_EMPRESA"
                + " WHERE c.ESTADO = 1 AND"
                + " e.PK_EMPRESA = ?"
                + " ORDER BY e.PK_EMPRESA, c.NOMBRE";

        return sql;
    }

    // Instruccion sql para consulta de tarifas asociadas
    // a una empresa determinada.
    public static void sql_TarifasEstablecidas() {
        String sql
                = "SELECT"
                + " t.VALOR_PROM_KM AS VALOR_PROM_KM,"
                + " r.NOMBRE AS NOMBRE_RUTA"
                + " FROM tbl_ruta AS r"
                + " INNER JOIN tbl_tarifa AS t ON"
                + "   r.PK_RUTA = t.FK_RUTA"
                + " WHERE t.ESTADO = 1 AND"
                + " r.FK_EMPRESA = ?"
                + " ORDER BY r.FK_EMPRESA, r.NOMBRE";

        //return sql;
    }

    // Instruccion sql para consulta de relacion conductor-vehiculo
    // de una empresa determinada.
    public static String sql_ConductoresXVehiculo() {
        String sql
                = "SELECT"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " LCASE(CONCAT(c.NOMBRE, ' ', c.APELLIDO)) AS NOMBRE_CONDUCTOR,"
                //+ " cv.FECHA_ASIGNACION AS FECHA_ASIGNACION"
                + " IF (max(cv.FECHA_ASIGNACION) = '0000-00-00 00:00:00',"
                + "     date_format(max(cv.FECHA_MODIFICACION), '%Y-%m-%d %H:%i:%s'), "
                + "     date_format(max(cv.FECHA_ASIGNACION), '%Y-%m-%d %H:%i:%s')) AS FECHA_ASIGNACION"
                + " FROM tbl_conductor_vehiculo AS cv"
                + " INNER JOIN tbl_conductor as c ON"
                + "   c.PK_CONDUCTOR = cv.FK_CONDUCTOR"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   v.PK_VEHICULO = cv.FK_VEHICULO"
                + " WHERE cv.ESTADO = 1 AND"
                + " c.FK_EMPRESA = ? AND "
                + " v.FK_EMPRESA = ?"
                + " GROUP BY v.PLACA"
                + " ORDER BY v.PLACA ASC";
        
        return sql;
    }

    // Instruccion sql para consulta de descripcion (conformacion
    // de puntos de control y tiempos) de una o varias rutas 
    // de una empresa determinada.
    public static String sql_DescripcionRutas(boolean unaRuta) {
        String sql
                = "SELECT"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " if (rp.FK_PUNTO = 100 || rp.FK_PUNTO = 101,"
                + "     b.NOMBRE,"
                + "     p.NOMBRE) AS NOMBRE_PUNTO,"
                + " rp.PROMEDIO_MINUTOS AS PROMEDIO_MINUTOS,"
                + " rp.HOLGURA_MINUTOS AS HOLGURA_MINUTOS,"
                + " rp.TIPO AS TIPO_PUNTO"
                + " FROM tbl_ruta AS r"
                + " INNER JOIN tbl_ruta_punto AS rp ON"
                + "   rp.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " LEFT JOIN tbl_punto AS p ON"
                + "   rp.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " LEFT JOIN tbl_base AS b ON"
                + "   rp.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " WHERE rp.ESTADO = 1 AND r.FK_EMPRESA = ?";

        if (unaRuta) {
            sql += " AND r.PK_RUTA = ?";
        }
        
        sql += " ORDER BY rp.PK_RUTA_PUNTO ASC";

        return sql;
    }

    // Instruccion sql para consulta de informacion de recorrido
    // de vehiculos que han recorrido una ruta en rango de fechas
    // especifico.
    public static String sql_VehiculosXRuta() {
        String sql
                = "SELECT"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " v.PK_VEHICULO AS PK_VEHICULO,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " IF(c.NOMBRE IS NULL, 'NA', LCASE(CONCAT(c.NOMBRE, ' ', c.APELLIDO))) AS NOMBRE_CONDUCTOR,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA_SALIDA,"
                + " ir.HORA_INGRESO AS HORA_LLEGADA,"
                
                + " TIME_FORMAT(SEC_TO_TIME(((SELECT rp.PROMEDIO_MINUTOS FROM tbl_ruta_punto rp WHERE"
                + "      rp.FK_RUTA = ir.FK_RUTA AND rp.TIPO = 3 LIMIT 1) + (TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA)/60)) * 60), '%H:%i:%s')"
                + "    AS HORA_LLEGADA_PLANEADA,"
                + " TIME_FORMAT(SEC_TO_TIME(ABS((SELECT rp.PROMEDIO_MINUTOS FROM tbl_ruta_punto rp WHERE"
                + "      rp.FK_RUTA = ir.FK_RUTA AND rp.TIPO = 3 LIMIT 1) + (TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA)/60) - (TIME_TO_SEC(ir.HORA_INGRESO)/60))*60), '%H:%i:%s')"
                + "    AS DIFERENCIA,"
                
                + "IF ((SELECT (rp.PROMEDIO_MINUTOS - rp.HOLGURA_MINUTOS) FROM tbl_ruta_punto rp WHERE"
                + "    rp.FK_RUTA = ir.FK_RUTA AND rp.TIPO = 3 LIMIT 1) + (TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA)/60) > (TIME_TO_SEC(ir.HORA_INGRESO)/60), 2,"
                + "    IF ((SELECT (rp.PROMEDIO_MINUTOS + rp.HOLGURA_MINUTOS) FROM tbl_ruta_punto rp WHERE"
                + "        rp.FK_RUTA = ir.FK_RUTA AND rp.TIPO = 3 LIMIT 1) + (TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA)/60) < (TIME_TO_SEC(ir.HORA_INGRESO)/60), 1, 0)) AS ESTADO,"
                
                + " IF(ir.HORA_INGRESO < SEC_TO_TIME(((SELECT rp.PROMEDIO_MINUTOS FROM tbl_ruta_punto rp WHERE"
                + "       rp.FK_RUTA = ir.FK_RUTA AND rp.TIPO = 3 LIMIT 1) + (TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA)/60)) * 60), '-', ' ') AS SIGNO,"
                
                + " (ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA ) AS CANTIDAD_PASAJEROS"
                + " FROM  tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "    v.PK_VEHICULO = ir.FK_VEHICULO AND v.ESTADO = 1"
                + " LEFT JOIN tbl_conductor AS c ON"
                + "    c.PK_CONDUCTOR = ir.FK_CONDUCTOR AND c.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "    r.PK_RUTA = ir.FK_RUTA AND r.ESTADO = 1"
                + " WHERE"
                + "   ir.FK_RUTA = ? AND"
                + "   (ir.FECHA_INGRESO = ? OR"
                + "   ir.FECHA_SALIDA_BASE_SALIDA = ?) AND"
                + "   ir.ESTADO = 1"
                + " ORDER BY v.PLACA, ir.HORA_SALIDA_BASE_SALIDA ASC";

        return sql;
    }
    
    // Instruccion sql para consulta de informacion de
    // despacho (tiempos y numeracion) realizado por 
    // vehiculos en una ruta y fecha especifico.
    public static String sql_VehiculosXRutaDph() {
        String sql = 
            "select * from ("
               + "select"
                + " placa as PLACA,"
                + " (select v.pk_vehiculo from tbl_vehiculo as v"
                + "  where lower(v.placa) = lower(pd.placa) and v.estado = 1 limit 1) as PK_VEHICULO,"
                + " (select v.num_interno from tbl_vehiculo as v"
                + "  where lower(v.placa) = lower(pd.placa) and v.estado = 1 limit 1) as NUMERO_INTERNO,"
                + " numero_vuelta as NUMERO_VUELTA,"
                + " hora_planificada as HORA_PLANIFICADA,"
                + " hora_real as HORA_REAL_LLEGADA,"
                + " if (hora_real is null, '-',"
                + "        if (diferencia = 0, time_format(sec_to_time(abs(diferencia) / 1000), '%T'),"
                + "        if (diferencia > 0, time_format(sec_to_time(abs(diferencia) / 1000), '%T'),"
                + "                time_format(sec_to_time(abs(diferencia) / 1000), '%T')))) as DIFERENCIA_TIEMPO,"

                + " (select pdi.hora_real from tbl_planilla_despacho as pdi"
                + "  where pdi.fecha = pd.fecha and"
                + "  pdi.placa = pd.placa and"
                + "  pdi.fk_ruta = pd.fk_ruta and"
                + "  pdi.numero_vuelta = pd.numero_vuelta and"
                + "  pdi.tipo_punto = 1 and pdi.estado = 1) as HORA_SALIDA,"

                + " ifnull("
                + " (select (ir.num_llegada - ir.numeracion_base_salida) from tbl_informacion_registradora as ir"
                + "   where ir.fecha_ingreso = pd.fecha and"
                + "        ir.fk_ruta = pd.fk_ruta and"
                + "        ir.hora_ingreso = pd.hora_real and"
                + "        ir.fk_vehiculo in (select v.pk_vehiculo from tbl_vehiculo as v where lower(v.placa) = lower(pd.placa) and v.estado = 1) limit 1), -1) as PASAJEROS,"

                + " (select lcase(concat(c.nombre, ' ', c.apellido)) from tbl_informacion_registradora as ir"
                + "  inner join tbl_conductor as c on"
                + "     ir.fk_conductor = c.pk_conductor and c.estado = 1"
                + "  where ir.fecha_ingreso = pd.fecha and"
                + "     ir.fk_ruta = pd.fk_ruta and"
                + "     ir.fk_vehiculo in (select v.pk_vehiculo from tbl_vehiculo as v where lower(v.placa) = lower(pd.placa) and v.estado = 1) limit 1) as CONDUCTOR,"                
                
                + "if(diferencia = 0, 0,"
                + "if(diferencia > 0, 1, 2)) as ESTADO"

                + " from tbl_planilla_despacho as pd"
                + " where"
                + " fecha = ? and"
                + " fk_ruta = ? and"
                + " tipo_punto = 3 and"
                + " estado = 1"
                + " group by placa, numero_vuelta ) as ctr"
            + " where ctr.HORA_SALIDA is not null and"
            + " ctr.HORA_REAL_LLEGADA is not null";
        return sql;
    }

    // Instruccion sql para consultar informacion cabecera de
    // recorrido hecha por vehiculos en una ruta, fecha y
    // base en especifico.
    public static String sql_Despachador_IndicesIR(boolean unaRuta) {
        String sql
                = "SELECT"
                + " ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                + " v.PLACA AS PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA_SALIDA,"
                + " TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) AS HORA_SALIDA_INT,"
                + " ir.HORA_INGRESO AS HORA_LLEGADA,"
                + " ir.NUMERACION_BASE_SALIDA AS NUMERACION_SALIDA,"
                + " ir.NUM_LLEGADA AS NUMERACION_LLEGADA,"
                + " ir.DIFERENCIA_NUM AS CANTIDAD_PASAJEROS,"
                + " IF(c.NOMBRE IS NULL,"
                + "    'NA',"
                + "    LCASE(CONCAT(c.NOMBRE, ' ', c.APELLIDO))) AS NOMBRE_CONDUCTOR,"
                + " r.PK_RUTA AS RUTA_PK,"
                + " r.NOMBRE AS NOMBRE_RUTA,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " TIME_FORMAT(SEC_TO_TIME(ABS(TO_SECONDS(ir.HORA_INGRESO) - TO_SECONDS(ir.HORA_SALIDA_BASE_SALIDA))), '%Hh:%im') AS TIEMPO_RUTA"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " LEFT JOIN tbl_conductor AS c ON"
                + "   ir.FK_CONDUCTOR = c.PK_CONDUCTOR AND c.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO = ? AND"
                + "      (ir.FK_BASE = ? OR ir.FK_BASE_SALIDA = ?)";

        if (unaRuta) {
            sql += " AND ir.FK_RUTA = ?"
                 + " GROUP BY ir.HORA_INGRESO";
        } else {
            sql += " GROUP BY ir.HORA_INGRESO";
        }

        return sql;
    }

    // Instruccion sql para consultar informacion detallada de una
    // vuelta (puntos, tiempos, numeracion, etc) hecha por un vehiculo, 
    // en una ruta y fecha especifico.
    public static String sql_Despachador() {
        String sql
                = " SELECT"
                + " IF(rp.FK_BASE IS NULL,"
                + "     (SELECT bb.NOMBRE FROM tbl_ruta_punto AS rrp"
                + "              INNER JOIN tbl_base AS bb ON"
                + "              rrp.FK_BASE = bb.PK_BASE AND bb.ESTADO = 1"
                + "              WHERE rrp.ESTADO = 1 AND rrp.FK_RUTA = ir.FK_RUTA AND rrp.FK_PUNTO = 101),"
                + "     b.NOMBRE) AS NOMBRE_PUNTO,"
                + " IF(rp.FK_BASE IS NULL, FALSE, TRUE) AS PUNTO_EN_VUELTA,"
                + " IF(rp.PROMEDIO_MINUTOS IS NULL, FALSE,"
                + "     IF(TIME_TO_SEC(ir.HORA_INGRESO) > TIME_TO_SEC(?) + rp.PROMEDIO_MINUTOS*60, FALSE, TRUE)) AS ADELANTADO,"
                + " ir.NUMERACION_BASE_SALIDA AS NUMERACION,"
                + " TRUE AS ES_BASE"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE_SALIDA = b.PK_BASE AND b.ESTADO = 1"
                + " LEFT JOIN tbl_ruta_punto AS rp ON"
                + "   rp.ESTADO = 1 AND"
                + "   rp.FK_RUTA = ir.FK_RUTA AND"
                + "   rp.FK_BASE = ir.FK_BASE_SALIDA AND"
                + "   rp.FK_PUNTO = 101"
                + " WHERE ir.PK_INFORMACION_REGISTRADORA = ?"
                + " UNION"
                + " SELECT"
                + "   p.NOMBRE AS NOMBRE_PUNTO,"
                + "   TRUE AS PUNTO_EN_VUELTA,"
                + "   IF ((SELECT MIN(TIME_TO_SEC(pc.HORA_PTO_CONTROL) - (TIME_TO_SEC(?) + rp.PROMEDIO_MINUTOS*60))"
                + "      FROM tbl_ruta_punto AS rp WHERE rp.ESTADO = 1 AND rp.FK_RUTA = ? AND rp.FK_PUNTO = pc.FK_PUNTO) >= 0, FALSE, TRUE) AS ADELANTADO,"

                + "   pc.NUMERACION AS NUMERACION,"
                + "   FALSE AS ES_BASE"
                + " FROM tbl_punto_control AS pc"
                + " INNER JOIN tbl_punto AS p ON"
                + "   pc.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " WHERE pc.FK_INFO_REGIS = ? AND"
                + "   pc.FK_PUNTO IN ("
                + "     SELECT rp.FK_PUNTO FROM tbl_ruta_punto AS rp"
                + "     WHERE rp.FK_RUTA = ? AND rp.ESTADO = 1"
                + " )"
                + " UNION"
                + " SELECT"
                + "   p.NOMBRE AS NOMBRE_PUNTO,"
                + "   FALSE AS PUNTO_EN_VUELTA,"
                + "   FALSE AS ADELANTADO,"
                + "   0 AS NUMERACION,"
                + "   FALSE AS ES_BASE"
                + " FROM tbl_ruta_punto AS rp"
                + " INNER JOIN tbl_punto AS p ON"
                + "   rp.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " WHERE rp.ESTADO = 1 AND"
                + "   rp.FK_RUTA = ? AND"
                + "   rp.FK_PUNTO != 100 AND"
                + "   rp.FK_PUNTO != 101 AND"
                + "   rp.FK_PUNTO NOT IN ("
                + "     SELECT pc.FK_PUNTO"
                + "     FROM tbl_punto_control AS pc"
                + "     WHERE"
                + "         pc.FK_INFO_REGIS = ?"
                + " )"
                + " UNION"
                + " SELECT"
                + " IF(rp.FK_BASE IS NULL,"
                + "     (SELECT bb.NOMBRE FROM tbl_ruta_punto AS rrp"
                + "              INNER JOIN tbl_base AS bb ON"
                + "              rrp.FK_BASE = bb.PK_BASE AND bb.ESTADO = 1"
                + "              WHERE rrp.ESTADO = 1 AND rrp.FK_RUTA = ir.FK_RUTA AND rrp.FK_PUNTO = 100),"
                + "     b.NOMBRE) AS NOMBRE_PUNTO,"
                + " IF(rp.FK_BASE IS NULL, FALSE, TRUE) AS PUNTO_EN_VUELTA,"
                + " IF(rp.PROMEDIO_MINUTOS IS NULL, FALSE,"
                + "     IF(TIME_TO_SEC(ir.HORA_INGRESO) > TIME_TO_SEC(?) + rp.PROMEDIO_MINUTOS*60, FALSE, TRUE)) AS ADELANTADO,"
                + " ir.NUM_LLEGADA AS NUMERACION,"
                + " TRUE AS ES_BASE"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " LEFT JOIN tbl_ruta_punto AS rp ON"
                + "   rp.ESTADO = 1 AND"
                + "   rp.FK_RUTA = ir.FK_RUTA AND"
                + "   rp.FK_BASE = ir.FK_BASE AND"
                + "   rp.FK_PUNTO = 100"
                + " WHERE ir.PK_INFORMACION_REGISTRADORA = ?";

        return sql;
    }

    /*
    
    public static String sql_GerenciaXVehiculo(String listaVehiculos, String listaRutas) {
        
        String sql = 
            "select * from ("
                + "(select"
                    + " ir.FECHA_INGRESO AS FECHA,"
                    + " v.PLACA as PLACA,"
                    + " v.NUM_INTERNO as NUMERO_INTERNO,"
                    + " r.PK_RUTA,"
                    + " IFNULL(r.NOMBRE, 'NA') as NOMBRE_RUTA,"
                    + " count(ir.PK_INFORMACION_REGISTRADORA) as CANTIDAD_VUELTAS,"
                    + " count(ir.FK_VEHICULO) as CANTIDAD_VEHICULOS,"

                    + " (select count(air.PK_AIR) from tbl_alarma_info_regis as air where"
                    + "     air.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA and"
                    + "     air.FK_ALARMA in (5,6,7)) as CANTIDAD_ALARMAS,"

                    + " sum(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) as CANTIDAD_PASAJEROS,"
                    + " (sum(ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) / count(ir.PK_INFORMACION_REGISTRADORA)) as PROMEDIO_PASAJEROS,"

                    + " (select lg.TOTAL_PASAJEROS_LIQUIDADOS from tbl_liquidacion_vueltas as lv"
                    + "     inner join tbl_liquidacion_general as lg on"
                    + "     lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) - "
                    + " (select sum(la.CANTIDAD_PASAJEROS_DESCONTADOS) from tbl_liquidacion_adicional as la"
                    + "     inner join tbl_liquidacion_vueltas as lv on"
                    + "     lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) as PASAJEROS_LIQUIDADOS,"

                    + " (select lg.TOTAL_VALOR_VUELTAS from tbl_liquidacion_vueltas as lv"
                    + "     inner join tbl_liquidacion_general as lg on"
                    + "     lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) - "
                    + " (select sum(la.VALOR_DESCUENTO) from tbl_liquidacion_adicional as la"
                    + "     inner join tbl_liquidacion_vueltas as lv on"
                    + "     lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) as VALOR_LIQUIDADO,"
                
                    + " (select lg.TOTAL_VALOR_VUELTAS FROM tbl_liquidacion_vueltas as lv"
                    + "     inner join tbl_liquidacion_general as lg on"
                    + "     lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) as TOTAL_BRUTO,"

                    + " (select lg.TOTAL_VALOR_DESCUENTOS_ADICIONAL FROM tbl_liquidacion_vueltas as lv"
                    + "     inner join tbl_liquidacion_general as lg on"
                    + "     lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) as TOTAL_DESCUENTO_OPERATIVO,"

                    + " (select sum(la.VALOR_DESCUENTO) from tbl_liquidacion_adicional as la"
                    + "     inner join tbl_liquidacion_vueltas as lv on"
                    + "     lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL"
                    + "     inner join tbl_categoria_descuento as cd on"
                    + "     la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS and cd.APLICA_GENERAL = 1 and cd.ESTADO = 1"
                    + "     where lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) as TOTAL_DESCUENTO_ADMIN"

                    + " from tbl_informacion_registradora as ir"
                    + " inner join tbl_vehiculo as v on"
                    + "     ir.FK_VEHICULO = v.PK_VEHICULO and v.ESTADO = 1"
                    + " left join tbl_ruta as r on"
                    + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                    + " where (ir.FECHA_INGRESO between ? and ?) and"
                    + "     ir.ESTADO = 1 and"
                    + "     ir.FK_VEHICULO IN (" + listaVehiculos + ") and"
                    + "     ir.FK_RUTA IN (" + listaRutas + ")"
                + " group by ir.FECHA_INGRESO, ir.FK_VEHICULO"
                + " order by ir.FECHA_INGRESO ASC, v.PLACA ASC)"
                + " ) as s where"
                + " s.PASAJEROS_LIQUIDADOS is not null or"
                + " s.VALOR_LIQUIDADO is not null";

        return sql;
    }
    
    */

    // Inicia creacion de instruccion sql para reporte gerencia 
    // con lista de rutas especifico. (Especifico para empresa Fusacatan).
    public static String sql_GerenciaFusa(String listaVehiculos, String listaRutas) {

        String sql = sql_GerenciaXVehiculoFusa(listaVehiculos, listaRutas, false);
        return sql;
    }  
    
    // Instruccion sql para consulta de produccion efectiva (pasajeros, dinero recaudado)
    // de vehiculos que han recorrido una ruta o varias en una fecha especifica.
    // (Especifico para empresa Fusacatan).
    public static String sql_GerenciaXVehiculoFusa(String listaVehiculos, String listaRutas, boolean ruta_nula) {
        
        listaRutas     = (listaRutas == null || Restriction.includeSQL(listaRutas)) ? "" : listaRutas;
        listaVehiculos = (listaVehiculos == null || Restriction.includeSQL(listaVehiculos)) ? "" : listaVehiculos;
        listaRutas     = (ruta_nula)
                            ? " (ir.FK_RUTA IN (" + listaRutas + ") OR ir.FK_RUTA IS NULL)"
                            : "  ir.FK_RUTA IN (" + listaRutas + ")";        
        
        String sql = 
            "SELECT" 	
                + " date(lv.FECHA_DESCUENTO) as FECHA,"
                + " time(lv.FECHA_DESCUENTO) as HORA,"
                + " ifnull((select v.PLACA from tbl_vehiculo as v where v.PK_VEHICULO = ir.FK_VEHICULO and v.ESTADO = 1), 'NA') as PLACA,"
                + " ifnull((select v.NUM_INTERNO from tbl_vehiculo as v where v.PK_VEHICULO = ir.FK_VEHICULO and v.ESTADO = 1), 'NA') as NUMERO_INTERNO,"
                
                /*
                + " ifnull((select r.NOMBRE from tbl_ruta as r where r.PK_RUTA = ir.FK_RUTA and r.ESTADO = 1), 'NA') as NOMBRE_RUTA,"
                + " ir.FK_RUTA,"
                */
                
                + " ifnull((select r.NOMBRE"
		+ "     from tbl_informacion_registradora as iir"
		+ "     inner join tbl_ruta as r on"
                + "         r.PK_RUTA = iir.FK_RUTA and r.ESTADO = 1"
		+ "     where iir.FK_VEHICULO = ir.FK_VEHICULO and iir.FECHA_INGRESO = ir.FECHA_INGRESO"
		+ "     order by iir.PORCENTAJE_RUTA desc limit 1), 'NA') as NOMBRE_RUTA,"

                + " ifnull((select r.PK_RUTA"
                + "     from tbl_informacion_registradora as iir"
                + "     inner join tbl_ruta as r on"
                + "         r.PK_RUTA = iir.FK_RUTA and r.ESTADO = 1"
		+ "     where iir.FK_VEHICULO = ir.FK_VEHICULO and iir.FECHA_INGRESO = ir.FECHA_INGRESO"
		+ "     order by iir.PORCENTAJE_RUTA desc limit 1), 0) as FK_RUTA,"

                + " (select count(air.PK_AIR) from tbl_alarma_info_regis as air where"
                + "     air.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA and"
                + "     air.FK_ALARMA in (5,6,7)) as CANTIDAD_ALARMAS,"

                + " (select count(iir.PK_INFORMACION_REGISTRADORA) from tbl_informacion_registradora as iir"
		+ "     where iir.PK_INFORMACION_REGISTRADORA in ("
		+ "         select ilv.FK_INFORMACION_REGISTRADORA from tbl_liquidacion_vueltas as ilv"
		+ "         where ilv.FK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL and lv.ESTADO = 1"
		+ " )) as CANTIDAD_VUELTAS,"

                + " (select sum(iir.NUM_LLEGADA - iir.NUMERACION_BASE_SALIDA) from tbl_informacion_registradora as iir"
                + "     where iir.PK_INFORMACION_REGISTRADORA in ("
                + "         select ilv.FK_INFORMACION_REGISTRADORA from tbl_liquidacion_vueltas as ilv"
                + "         where ilv.FK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL and lv.ESTADO = 1"
                + "         )) as CANTIDAD_PASAJEROS,"               
                
                + " (select sum(la.CANTIDAD_PASAJEROS_DESCONTADOS) from tbl_liquidacion_adicional as la"
                + "     where lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) as PASAJEROS_DESCONTADOS,"

                + " (select lg.TOTAL_PASAJEROS_LIQUIDADOS from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as PASAJEROS_LIQUIDADOS,"                               

                + " (select lg.TOTAL_PASAJEROS_LIQUIDADOS * ta.VALOR_TARIFA from tbl_liquidacion_general as lg"
                + "  inner join tbl_tarifa_fija as ta on"
                + "     ta.PK_TARIFA_FIJA = lg.FK_TARIFA_FIJA"
                + " where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as TOTAL_BRUTO,"
                
                + " (select lg.TOTAL_VALOR_VUELTAS from tbl_liquidacion_general as lg"
                + "  where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as TOTAL_DTO_BRUTO,"

                + " (select lg.TOTAL_VALOR_DESCUENTOS_ADICIONAL from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as TOTAL_DESCUENTO_OPERATIVO,"

                + " (select sum(la.VALOR_DESCUENTO) from tbl_liquidacion_adicional as la"
                + "     inner join tbl_categoria_descuento as cd on"
                + "         la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS and cd.APLICA_GENERAL = 1 and cd.ESTADO = 1"
                + "     where lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) as TOTAL_DESCUENTO_ADMIN,"
                
                + " (select lg.TOTAL_PASAJEROS_LIQUIDADOS * ta.VALOR_TARIFA from tbl_liquidacion_general as lg"
                + "  inner join tbl_tarifa_fija as ta on"
                + "     ta.PK_TARIFA_FIJA = lg.FK_TARIFA_FIJA"
                + " where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) -"
                + " (select sum(la.VALOR_DESCUENTO) from tbl_liquidacion_adicional as la"
                + "     where lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) as VALOR_LIQUIDADO"

                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_liquidacion_vueltas AS lv ON"
                + "     lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA"
                + " WHERE (date(lv.FECHA_DESCUENTO) BETWEEN ? and ?) and"
                + "     ir.FK_VEHICULO IN (" + listaVehiculos + ") and"
                //+ "     ir.FK_RUTA IN (" + listaRutas + ") and"
                +       listaRutas 
                + "     and lv.ESTADO = 1"
                + " GROUP BY lv.FK_LIQUIDACION_GENERAL";
        
        return sql;
    }

    // Inicia creacion de instruccion sql para reporte gerencia 
    // con lista de rutas especifico.
    public static String sql_Gerencia(String listaVehiculos, String listaRutas) {

        String sql = sql_GerenciaXVehiculo(listaVehiculos, listaRutas, false);
        return sql;
    }  
    
    // Instruccion sql para consulta de produccion efectiva (pasajeros, dinero recaudado)
    // de vehiculos que han recorrido una ruta o varias en una fecha especifica.
    public static String sql_GerenciaXVehiculo(String listaVehiculos, String listaRutas, boolean ruta_nula) {
        
        listaRutas     = (listaRutas == null || Restriction.includeSQL(listaRutas)) ? "" : listaRutas;
        listaVehiculos = (listaVehiculos == null || Restriction.includeSQL(listaVehiculos)) ? "" : listaVehiculos;
        listaRutas     = (ruta_nula)
                            ? " (ir.FK_RUTA IN (" + listaRutas + ") OR ir.FK_RUTA IS NULL)"
                            : "  ir.FK_RUTA IN (" + listaRutas + ")";        
        
        String sql = 
            "SELECT" 	
                + " date(lv.FECHA_DESCUENTO) as FECHA,"
                + " time(lv.FECHA_DESCUENTO) as HORA,"
                + " ifnull((select v.PLACA from tbl_vehiculo as v where v.PK_VEHICULO = ir.FK_VEHICULO and v.ESTADO = 1), 'NA') as PLACA,"
                + " ifnull((select v.NUM_INTERNO from tbl_vehiculo as v where v.PK_VEHICULO = ir.FK_VEHICULO and v.ESTADO = 1), 'NA') as NUMERO_INTERNO,"
                
                /*
                + " ifnull((select r.NOMBRE from tbl_ruta as r where r.PK_RUTA = ir.FK_RUTA and r.ESTADO = 1), 'NA') as NOMBRE_RUTA,"
                + " ir.FK_RUTA,"
                */
                
                + " ifnull((select r.NOMBRE"
		+ "     from tbl_informacion_registradora as iir"
		+ "     inner join tbl_ruta as r on"
                + "         r.PK_RUTA = iir.FK_RUTA and r.ESTADO = 1"
		+ "     where iir.FK_VEHICULO = ir.FK_VEHICULO and iir.FECHA_INGRESO = ir.FECHA_INGRESO"
		+ "     order by iir.PORCENTAJE_RUTA desc limit 1), 'NA') as NOMBRE_RUTA,"

                + " ifnull((select r.PK_RUTA"
                + "     from tbl_informacion_registradora as iir"
                + "     inner join tbl_ruta as r on"
                + "         r.PK_RUTA = iir.FK_RUTA and r.ESTADO = 1"
		+ "     where iir.FK_VEHICULO = ir.FK_VEHICULO and iir.FECHA_INGRESO = ir.FECHA_INGRESO"
		+ "     order by iir.PORCENTAJE_RUTA desc limit 1), 0) as FK_RUTA,"

                + " (select count(air.PK_AIR) from tbl_alarma_info_regis as air where"
                + "     air.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA and"
                + "     air.FK_ALARMA in (5,6,7)) as CANTIDAD_ALARMAS,"

                + " (select count(iir.PK_INFORMACION_REGISTRADORA) from tbl_informacion_registradora as iir"
		+ "     where iir.PK_INFORMACION_REGISTRADORA in ("
		+ "         select ilv.FK_INFORMACION_REGISTRADORA from tbl_liquidacion_vueltas as ilv"
		+ "         where ilv.FK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL and lv.ESTADO = 1"
		+ " )) as CANTIDAD_VUELTAS,"

                + " (select sum(iir.NUM_LLEGADA - iir.NUMERACION_BASE_SALIDA) from tbl_informacion_registradora as iir"
                + "     where iir.PK_INFORMACION_REGISTRADORA in ("
                + "         select ilv.FK_INFORMACION_REGISTRADORA from tbl_liquidacion_vueltas as ilv"
                + "         where ilv.FK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL and lv.ESTADO = 1"
                + "         )) as CANTIDAD_PASAJEROS,"
                
                + " (select sum(la.CANTIDAD_PASAJEROS_DESCONTADOS) from tbl_liquidacion_adicional as la"
                + "     where lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) as PASAJEROS_DESCONTADOS,"

                + " (select lg.TOTAL_PASAJEROS_LIQUIDADOS from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as PASAJEROS_LIQUIDADOS,"

                + " (select lg.TOTAL_VALOR_VUELTAS from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as TOTAL_BRUTO,"
                
                + " (select lg.TOTAL_VALOR_VUELTAS from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as TOTAL_DTO_BRUTO,"

                + " (select lg.TOTAL_VALOR_DESCUENTOS_ADICIONAL from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) as TOTAL_DESCUENTO_OPERATIVO,"

                + " (select sum(la.VALOR_DESCUENTO) from tbl_liquidacion_adicional as la"
                + "     inner join tbl_categoria_descuento as cd on"
                + "         la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS and cd.APLICA_GENERAL = 1 and cd.ESTADO = 1"
                + "     where lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) as TOTAL_DESCUENTO_ADMIN,"
                
                + " (select lg.TOTAL_VALOR_VUELTAS from tbl_liquidacion_general as lg"
                + "     where lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) - "
                + " (select sum(la.VALOR_DESCUENTO) from tbl_liquidacion_adicional as la"
                + "     where lv.FK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) as VALOR_LIQUIDADO"

                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_liquidacion_vueltas AS lv ON"
                + "     lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA"
                + " WHERE (date(lv.FECHA_DESCUENTO) BETWEEN ? and ?) and"
                + "     ir.FK_VEHICULO IN (" + listaVehiculos + ") and"
                //+ "     ir.FK_RUTA IN (" + listaRutas + ") and"
                +       listaRutas 
                + "     and lv.ESTADO = 1"
                + " GROUP BY lv.FK_LIQUIDACION_GENERAL";
        
        return sql;
    }

    // Instruccion sql para consultar rutas recorridas por vehiculos
    // en un rango de fechas especifico.
    public static String sql_RutaXVehiculo() {
        String sql
                = "SELECT"
                + " ir.PK_INFORMACION_REGISTRADORA AS IR_PK,"
                + " ir.FK_RUTA AS RUTA_PK,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA_SALIDA,"
                + " time_to_sec(ir.HORA_SALIDA_BASE_SALIDA) AS HORA_SALIDA_SEC,"
                + " ir.NUMERO_VUELTA,"
                + " v.PLACA,"
                + " v.NUM_INTERNO AS NUMERO_INTERNO"
                //      + " r.NOMBRE AS NOMBRE_RUTA"
                + " FROM tbl_informacion_registradora AS ir"
                //      + " INNER JOIN tbl_ruta AS r ON"
                //      + "  ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "     v.PK_VEHICULO = ir.FK_VEHICULO AND v.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO = ? AND ir.FK_VEHICULO = ? AND"
                + " ir.FK_RUTA IS NOT NULL";

        return sql;
    }
    
    // Instruccion sql para consulta de informacion de
    // despacho (tiempos y numeracion) realizado por 
    // vehiculo y fecha especifico.
    public static String sql_RutaXVehiculoDph() {
        String sql =
            "select"                
                + " punto as PUNTO,"
                + " tipo_punto as TIPO_PUNTO,"                
                + " hora_planificada as HORA_PLANIFICADA,"
                + " if (hora_real is null, '-', time_format(hora_real, '%T')) as HORA_REAL_LLEGADA,"
                + " holgura_punto as MINUTOS_HOLGURA,"
                + " diferencia as DIFERENCIA,"

                + " if (hora_real is null, '-',"
                + "       if (diferencia = 0, time_format(sec_to_time(abs(diferencia) / 1000), '%T'),"
                + "       if (diferencia > 0, time_format(sec_to_time(abs(diferencia) / 1000), '+%T'),"
                + "               time_format(sec_to_time(abs(diferencia) / 1000), '-%T')))) as DIFERENCIA_TIEMPO,"

                + " if (tipo_punto = 1, (select b.nombre from tbl_base as b where concat('p', (b.identificador_base - 1)) = punto and b.estado = 1),"
                + " if (tipo_punto = 3, (select b.nombre from tbl_base as b where concat('p', b.identificador_base) = punto and b.estado = 1),"
                + "       (select p.nombre from tbl_punto as p where concat('p', p.codigo_punto) = punto and p.estado = 1))) as NOMBRE_PUNTO,"

                + " if (hora_real is null, 0,"
                + " if (diferencia = 0, 1,"
                + " if (diferencia > 0, 2, 3))) as ESTADO,"
                + " numero_vuelta as NUMERO_VUELTA,"
                + " v.num_interno as NUMERO_INTERNO,"
                + " r.NOMBRE as NOMBRE_RUTA"

               + " from tbl_planilla_despacho as pd"
               + " inner join tbl_vehiculo as v on"
               + "  lower(v.placa) = lower(pd.placa) and v.estado = 1"
               + " inner join tbl_ruta as r on"
               + "  r.pk_ruta = pd.fk_ruta and r.estado = 1"
               + " where"
               + " pd.placa = ? and"
               + " pd.fecha = ?";
        return sql;
    }

    // Instruccion sql para consulta de producido (cantidad de pasajeros)
    // realizado por un vehiculo de propietario en un rango de fechas
    // especifico.
    public static String sql_Propietario() {
        String sql 
            = "SELECT"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA,"
                + " ir.FECHA_INGRESO AS FECHA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA_SALIDA,"
                + " ir.HORA_INGRESO AS HORA_LLEGADA,"
                /*+ " r.NOMBRE AS NOMBRE_RUTA,"*/
                + " IF((SELECT r.NOMBRE FROM tbl_ruta as r WHERE r.PK_RUTA = ir.FK_RUTA) IS NULL, 'N/A', (SELECT r.NOMBRE FROM tbl_ruta as r WHERE r.PK_RUTA = ir.FK_RUTA) ) AS NOMBRE_RUTA,"
                + " (ir.NUM_LLEGADA - ir.NUMERACION_BASE_SALIDA) AS CANTIDAD_PASAJEROS,"
                + " SEC_TO_TIME(ABS(TO_SECONDS(ir.HORA_INGRESO) - TO_SECONDS(ir.HORA_SALIDA_BASE_SALIDA))) AS TIEMPO_VUELTA,"

                + " (SELECT count(cp.PK_CONTEO_PERIMETRO) FROM tbl_conteo_perimetro AS cp"
                + "  WHERE cp.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA) AS CONTEO_PERIMETRO,"

                // Informacion de vehiculo
                + " (SELECT v.PLACA FROM tbl_vehiculo AS v WHERE v.PK_VEHICULO = ir.FK_VEHICULO AND v.ESTADO = 1) AS PLACA,"
                + " (SELECT v.NUM_INTERNO FROM tbl_vehiculo AS v WHERE v.PK_VEHICULO = ir.FK_VEHICULO AND v.ESTADO = 1) AS NUMERO_INTERNO"

                + " FROM tbl_informacion_registradora AS ir"
                /*+ " INNER JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"*/
                + " LEFT OUTER JOIN tbl_conteo_perimetro AS cp ON"
                + "   ir.PK_INFORMACION_REGISTRADORA = cp.FK_INFORMACION_REGISTRADORA"
                + " WHERE"
                + " ir.FECHA_INGRESO BETWEEN ? AND ? AND"
                + " ir.FK_VEHICULO = ?"
                + " ORDER BY ir.FECHA_INGRESO ASC, ir.HORA_SALIDA_BASE_SALIDA ASC";
        System.out.println(sql);
        return sql;
    }
        
    // Instruccion sql para consulta de cumplimiento de ruta
    // (puntos reales de ruta vs puntos registrados) por vehiculos,
    //  que hicieron una ruta y fecha en especifico.
    public static String sql_CumplimientoRutaXVehiculo() {
        String sql = 
            "SELECT" 
                + "   v.PK_VEHICULO,"
                + "   v.PLACA,"
                + "   v.NUM_INTERNO,"

                + "   (select group_concat(sc.NOMBRE separator ', ') from ("	
                + "       select lcase(concat(c.NOMBRE, ' ', c.APELLIDO)) as NOMBRE,"
                + "              cv.FK_VEHICULO"
                + "       from tbl_conductor_vehiculo as cv"
                + "       inner join tbl_conductor as c on"
                + "           c.PK_CONDUCTOR = cv.FK_CONDUCTOR"
                + "       where date(cv.FECHA_ASIGNACION) between ? and ?"
                + "       group by cv.FK_CONDUCTOR"
                + "    ) as sc where sc.FK_VEHICULO = ir.FK_VEHICULO) as LISTA_CONDUCTORES,"

                + " sum((select count(rp.PK_RUTA_PUNTO) from tbl_ruta_punto as rp"
                + "      where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1)) as TOTAL_PUNTOS_RUTA,"

                + "   sum(("
                //+ "       select count(pc.FK_PUNTO) from tbl_punto_control as pc"
                //+ "       where pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA and pc.FK_PUNTO in ("
                //+ "           select rp.FK_PUNTO from tbl_ruta_punto as rp"
                //+ "           where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1"
                + "         func_puntos_vistos(ir.PK_INFORMACION_REGISTRADORA, ir.FK_RUTA)"
                + "   )+2) as TOTAL_PUNTOS_CUMPLIDOS,"

                + " round("			
                + "   sum(("
                //+ "       select count(pc.FK_PUNTO) from tbl_punto_control as pc"
                //+ "       where pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA and pc.FK_PUNTO in ("
                //+ "           select rp.FK_PUNTO from tbl_ruta_punto as rp"
                //+ "           where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1"
                + "     func_puntos_vistos(ir.PK_INFORMACION_REGISTRADORA, ir.FK_RUTA)"
                + "   )+2)"
                + "   /   "			
                + "   sum((select count(rp.PK_RUTA_PUNTO) from tbl_ruta_punto as rp"
                + "        where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1))"
                + " ,2) as PORCENTAJE_CUMPLIDO"		

                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "     ir.FK_VEHICULO = v.PK_VEHICULO and v.ESTADO = 1"
                + " WHERE" 
                + "     ir.FECHA_INGRESO BETWEEN ? and ?"
                + "     and ir.FK_RUTA = ?"
                + " GROUP BY ir.FK_VEHICULO"
                + " ORDER BY PORCENTAJE_CUMPLIDO desc, v.PLACA asc";
        
        return sql;
    }
    
    // Instruccion sql para consulta de cumplimiento de ruta
    // (puntos reales de ruta vs puntos registrados) por vehiculos,
    //  en todas las rutas en una fecha en especifico.
    public static String sql_CumplimientoRutaConsolidado() {
        
        String sql = 
            "SELECT"
                + " r.PK_RUTA,"
                + " r.NOMBRE as NOMBRE_RUTA,"	                

                + " sum((select count(rp.PK_RUTA_PUNTO) from tbl_ruta_punto as rp"
                + "      where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1)) as TOTAL_PUNTOS_RUTA,"

                + " sum(("
                //+ "       select count(pc.FK_PUNTO) from tbl_punto_control as pc"
                //+ "       where pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA and pc.FK_PUNTO in ("
                //+ "           select rp.FK_PUNTO from tbl_ruta_punto as rp"
                //+ "           where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1"
                + "     func_puntos_vistos(ir.PK_INFORMACION_REGISTRADORA, ir.FK_RUTA)"
                + " )+2) as TOTAL_PUNTOS_CUMPLIDOS,"

                + " round("			
                + "     sum(("
                //+ "       select count(pc.FK_PUNTO) from tbl_punto_control as pc"
                //+ "       where pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA and pc.FK_PUNTO in ("
                //+ "           select rp.FK_PUNTO from tbl_ruta_punto as rp"
                //+ "           where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1"
                + "         func_puntos_vistos(ir.PK_INFORMACION_REGISTRADORA, ir.FK_RUTA)"
                + "     )+2)"
                + "     / "
                + "     sum((select count(rp.PK_RUTA_PUNTO) from tbl_ruta_punto as rp"
                + "          where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1))"
                + " ,2) as PORCENTAJE_CUMPLIDO"		

                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_ruta AS r ON"
                + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                + " WHERE"
                + "     ir.FECHA_INGRESO BETWEEN ? and ?"
                + " GROUP BY ir.FK_RUTA"
                + " ORDER BY PORCENTAJE_CUMPLIDO desc, r.NOMBRE ASC";
        
        return sql;
    }
    
    // Instruccion sql para consulta de cumplimiento de ruta
    // (puntos reales de ruta vs puntos registrados) por conductores,
    // en una o varias rutas y fecha en especifico.
    public static String sql_CumplimientoRutaPorConductor(boolean seleccionRuta) {

        String filtro_ruta = (seleccionRuta) ? " and ir.FK_RUTA = ?" : "";
        
        String sql =
            "SELECT"
                + " concat(c.NOMBRE, ' ', c.APELLIDO) AS NOMBRE_CONDUCTOR,"

                + " sum((select count(rp.PK_RUTA_PUNTO) from tbl_ruta_punto as rp"
                + "      where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1)) as TOTAL_PUNTOS_RUTA,"

                + " sum(("
                //+ "   select count(pc.FK_PUNTO) from tbl_punto_control as pc"
                //+ "   where pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA and pc.FK_PUNTO in ("
                //+ "       select rp.FK_PUNTO from tbl_ruta_punto as rp"
                //+ "       where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1"
                + "     func_puntos_vistos(ir.PK_INFORMACION_REGISTRADORA, ir.FK_RUTA)"
                + " )+2) as TOTAL_PUNTOS_CUMPLIDOS,"

                + " round("			
                + "     sum(("
                //+ "         select count(pc.FK_PUNTO) from tbl_punto_control as pc"
                //+ "         where pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA and pc.FK_PUNTO in ("
                //+ "             select rp.FK_PUNTO from tbl_ruta_punto as rp"
                //+ "             where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1"
                + "         func_puntos_vistos(ir.PK_INFORMACION_REGISTRADORA, ir.FK_RUTA)"
                + "     )+2)"
                + "     /  "   			
                + "     sum((select count(rp.PK_RUTA_PUNTO) from tbl_ruta_punto as rp"
                + "          where rp.FK_RUTA = ir.FK_RUTA and rp.ESTADO = 1))"
                + " ,2) as PORCENTAJE_CUMPLIDO"		

                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_conductor AS c ON"
                + "      ir.FK_CONDUCTOR = c.PK_CONDUCTOR AND c.ESTADO = 1"
                + " WHERE"
                + "     ir.FECHA_INGRESO BETWEEN ? and ?"
                +       filtro_ruta
                + " GROUP BY ir.FK_CONDUCTOR"
                + " ORDER BY PORCENTAJE_CUMPLIDO desc, NOMBRE_CONDUCTOR asc";
        
        return sql;
    }
    
    // Instruccion sql para consulta de informacion de planilla despacho para
    // un listado de vehiculos especifico.
    public static String sql_consolidadoDespacho(String listaVehiculos) {
                
        listaVehiculos = (listaVehiculos == null || Restriction.includeSQL(listaVehiculos)) ? "" : listaVehiculos;
        String sql = 
            "SELECT"
                + " pd.FECHA,"
                + " pd.PLACA,"
                + " pd.NUMERO_VUELTA,"
                + " pd.PUNTO,"
                + " pd.HORA_PLANIFICADA,"
                + " pd.HORA_REAL,"
                + " pd.DIFERENCIA,"
                + " TIME_FORMAT(SEC_TO_TIME(ABS(pd.diferencia)/1000), '%T') as DIFERENCIA_TIEMPO,"
                + "     (SELECT v.NUM_INTERNO FROM tbl_vehiculo as v WHERE"
                + "      lower(v.PLACA) = lower(pd.PLACA) and v.ESTADO = 1 LIMIT 1) AS NUMERO_INTERNO"
                + " FROM tbl_planilla_despacho as pd"
                + " WHERE pd.FECHA BETWEEN ? and ? and"
                + "     pd.FK_RUTA = ? and"
                + "     pd.PLACA IN (" + listaVehiculos + ")"
                + " ORDER BY pd.FECHA asc,"
                + "     pd.PLACA asc,"
                + "     pd.NUMERO_VUELTA asc,"
                + "     pd.HORA_PLANIFICADA asc";
        
        return sql;
    }
    
    // Instruccion sql para consulta de puntos de control no registrados.    
    public static String sql_puntosNoRegistrados_agrupado() {
        
        String sql =  
            "SELECT"
                + " r.NOMBRE as NOMBRE_RUTA,"
                + " ir.FECHA_INGRESO,"
                + " ir.HORA_SALIDA_BASE_SALIDA as HORA_SALIDA,"
                + " ir.HORA_INGRESO,"
                + " ir.NUMERO_VUELTA,"
                + " p.NOMBRE as NOMBRE_PUNTO,"
                + " count(npc.PK_PUNTO_CONTROL_NO_REGISTRADO) as CANTIDAD_PUNTOS"
                + " FROM tbl_informacion_registradora as ir"
                + " INNER JOIN tbl_punto_control_no_registrado as npc on"
                + "   ir.PK_INFORMACION_REGISTRADORA = npc.FK_INFORMACION_REGISTRADORA"
                + " INNER JOIN tbl_punto as p on"
                + "   p.PK_PUNTO = npc.FK_PUNTO and p.ESTADO = 1"
                + " INNER JOIN tbl_ruta as r on"
                + "   r.PK_RUTA = ir.FK_RUTA and r.ESTADO = 1"
                + " WHERE ir.FECHA_INGRESO between ? and ?"
                + " GROUP BY npc.FK_PUNTO"
                + " ORDER BY CANTIDAD desc, ir.FECHA_INGRESO asc, ir.HORA_INGRESO asc";
        return sql;
    }
    
    public static String sql_ConsolidadoLiquidacion(String fechaInicio, String fechaFin, int pkEmpresa, String Perfil, int idPropietario, int idEmpresa, String nombreEmpresa) {

        StringBuilder query = new StringBuilder();
        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))) {
                if(Perfil.equalsIgnoreCase("Propietario")){
                    query.append("SELECT vh.PK_VEHICULO, vh.PLACA, vh.NUM_INTERNO, ");
                }else{
                    query.append("SELECT v.PK_VEHICULO, v.PLACA, v.NUM_INTERNO,  ");
                }
                query.append(" (SELECT COUNT(lv.PK_LIQUIDACION_VUELTAS) ");
                query.append(" FROM tbl_liquidacion_vueltas lv INNER JOIN tbl_informacion_registradora ir ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA ");
                if(Perfil.equalsIgnoreCase("Propietario")){
                    query.append(" WHERE (ir.FK_VEHICULO = vh.PK_VEHICULO) AND (lv.estado=1) AND (lv.FECHA_MODIFICACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) ").append("AS VUELTAS_LIQUIDADAS, ");        
                }else{
                    query.append(" WHERE (ir.FK_VEHICULO = v.PK_VEHICULO) AND (lv.estado=1) AND (lv.FECHA_MODIFICACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) ").append("AS VUELTAS_LIQUIDADAS, ");            
                }                 
                query.append(" SUM(TOTAL_PASAJEROS_LIQUIDADOS) AS TOTAL_PASAJEROS_LIQUIDADOS,  ");
                
                query.append(" SUM( IF( (SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total ");
                query.append(" FROM tbl_liquidacion_adicional AS la WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) IS NULL , ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total  ");
                query.append(" FROM tbl_liquidacion_adicional AS la WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) ) ) AS TOTAL_PASAJEROS_DESCONTADOS,  ");
                
                query.append(" SUM( IF ( ((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + TOTAL_PASAJEROS_LIQUIDADOS)IS NULL, ");
                query.append(" 0, ");
                query.append(" ((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total  ");
                query.append(" FROM tbl_liquidacion_adicional AS la  ");
                query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + TOTAL_PASAJEROS_LIQUIDADOS) ) ) AS TOTAL_PASAJEROS, ");
                
                query.append(" (SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (lg.ESTADO = 1) AND (tf.ESTADO = 1) AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin)
                        .append("')) AS TARIFA, ");
                
                query.append(" SUM( lg.TOTAL_VALOR_VUELTAS) AS TOTAL_BRUTO,  ");
                query.append(" SUM(	IF( (SELECT SUM(la1.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, 0,");
                query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS  ");
                query.append(" WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '")
                     .append(fechaInicio).append("' AND '").append(fechaFin).append("')))) AS TOTAL_DESCUENTO_AL_NETO, ");
                
                query.append(" SUM((lg.TOTAL_VALOR_VUELTAS -  ");
                query.append(" (IF( (SELECT SUM(la1.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");                        
                query.append(" WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, 0,");
                query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')))) ");                                
                query.append(" ) ) AS SUB_TOTAL,  ");
                /*DEscuentos operativos*/
                query.append(" SUM( IF(  (SELECT SUM(la.VALOR_DESCUENTO)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin)
                     .append("')))) AS TOTAL_DESCUENTOS,   ");
                /*descuentos administrativos*/
                query.append(" SUM( IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  AND (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM( la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  AND (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin)
                     .append("')))) AS TOTAL_OTROS_DESCUENTOS, ");
                /*SUBTOTAL*/               
                query.append(" SUM(( (lg.TOTAL_VALOR_VUELTAS -  ");
                /*DESCUENTO AL NETO*/
                query.append(" ( IF( (SELECT SUM(la1.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");              
                query.append(" 0, ");                
                query.append(" (SELECT SUM(la1.VALOR_DESCUENTO)  ");
                query.append(" FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");                
                query.append(" WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("'))))");
                query.append(" ) - ");
                /*DESCUENTOS OPERATIVOS*/
                query.append(" ( IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");                
                query.append(" AND (cd.APLICA_DESCUENTO = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)) IS NULL, ");
                query.append(" 0, ");
                query.append("(SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");               
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ) ) ) ");
                query.append(" - ");
                /*DESCUENTOS ADMINISTRATIVOS*/
                query.append("(IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");              
                query.append(" AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM( la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS  ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)))) ");
                query.append(")) AS TOTAL_LIQUIDACION ");
                                
                if(Perfil.equalsIgnoreCase("Propietario")){
                    query.append(" FROM tbl_liquidacion_general lg INNER JOIN (SELECT v.* FROM tbl_propietario_vehiculo AS pv INNER JOIN tbl_vehiculo as v ON pv.fk_vehiculo= v.PK_VEHICULO WHERE (pv.fk_propietario = ").append(idPropietario).append(") AND (v.ESTADO=1) AND (v.FK_EMPRESA = ").append(pkEmpresa).append(" )) AS vh ON lg.FK_VEHICULO = vh.PK_VEHICULO   ");
                    query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");            
                    query.append(" GROUP BY lg.FK_VEHICULO ");
                    query.append(" ORDER BY vh.NUM_INTERNO; ");
                }else{
                    query.append(" FROM tbl_liquidacion_general lg INNER JOIN tbl_vehiculo v ON lg.FK_VEHICULO = v.PK_VEHICULO   ");
                    query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                    query.append(" AND (v.ESTADO = 1) AND (v.FK_EMPRESA = ").append(pkEmpresa).append(") ");
                    query.append(" GROUP BY lg.FK_VEHICULO ");
                    query.append(" ORDER BY v.NUM_INTERNO; ");
                }
        } else{//FIN SI; VERIFICA LA EMPRESA SI ES FUSACATAN
            if(Perfil.equalsIgnoreCase("Propietario")){
                    query.append("SELECT vh.PK_VEHICULO, vh.PLACA, vh.NUM_INTERNO, ");
                }else{
                    query.append("SELECT v.PK_VEHICULO, v.PLACA, v.NUM_INTERNO,  ");
                }
                query.append(" (SELECT COUNT(lv.PK_LIQUIDACION_VUELTAS) ");
                query.append(" FROM tbl_liquidacion_vueltas lv INNER JOIN tbl_informacion_registradora ir ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA ");
                if(Perfil.equalsIgnoreCase("Propietario")){
                    query.append(" WHERE (ir.FK_VEHICULO = vh.PK_VEHICULO) AND (lv.estado=1) AND (lv.FECHA_MODIFICACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) ").append("AS VUELTAS_LIQUIDADAS, ");        
                }else{
                    query.append(" WHERE (ir.FK_VEHICULO = v.PK_VEHICULO) AND (lv.estado=1) AND (lv.FECHA_MODIFICACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) ").append("AS VUELTAS_LIQUIDADAS, ");            
                }                 
                query.append(" SUM(TOTAL_PASAJEROS_LIQUIDADOS) AS TOTAL_PASAJEROS_LIQUIDADOS,  ");
                
                query.append(" SUM( IF( (SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");            
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) GROUP BY lg.PK_LIQUIDACION_GENERAL) IS NULL , ");
                query.append(" 0 , ");
                query.append(" (SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append("  WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");            
                query.append("   AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) ) ) AS TOTAL_PASAJEROS_DESCONTADOS,  ");
                query.append(" SUM( IF ( ((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");                            
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + TOTAL_PASAJEROS_LIQUIDADOS)IS NULL, ");
                query.append(" 0,  ");               
                query.append(" ((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) as total ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");            
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + TOTAL_PASAJEROS_LIQUIDADOS) ) ) AS TOTAL_PASAJEROS, ");
                
                query.append(" (SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (lg.ESTADO = 1) AND (tf.ESTADO = 1) AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                query.append(" ) AS TARIFA,  ");
                
                query.append(" SUM( lg.TOTAL_VALOR_VUELTAS) AS TOTAL_BRUTO,  ");
                
                
                query.append(" SUM( IF(  (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");            
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin)
                     .append("')))) AS TOTAL_DESCUENTOS, ");
                
                query.append(" SUM((lg.TOTAL_VALOR_VUELTAS - ");
                query.append(" (( IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");            
                query.append(" AND (cd.APLICA_DESCUENTO = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ) ) ) )");
                query.append(" )) AS SUB_TOTAL, ");
                query.append(" SUM( IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");
                query.append(" 0, ");
                query.append("(SELECT SUM( la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1)  AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin)
                     .append("')))) AS TOTAL_OTROS_DESCUENTOS,  ");                
                query.append(" SUM(( lg.TOTAL_VALOR_VUELTAS - ");
                /*DESCUENTOS OPERATIVOS*/
                query.append(" ( IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_DESCUENTO = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ) IS NULL, ");                
                query.append(" 0, ");
                
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_DESCUENTO = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')))) ");                                
                query.append(" - ");
                /*DESCUENTOS ADMINISTRATIVOS*/
                query.append(" (IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM( la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (lg.ESTADO = 1) AND (cd.ESTADO = 1) AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') )))");                
                query.append(" ) ) AS TOTAL_LIQUIDACION  ");
                
                if(Perfil.equalsIgnoreCase("Propietario")){
                    query.append(" FROM tbl_liquidacion_general lg INNER JOIN (SELECT v.* FROM tbl_propietario_vehiculo AS pv INNER JOIN tbl_vehiculo as v ON pv.fk_vehiculo= v.PK_VEHICULO WHERE (pv.fk_propietario = ").append(idPropietario).append(") AND (v.ESTADO=1) AND (v.FK_EMPRESA = ").append(pkEmpresa).append(" )) AS vh ON lg.FK_VEHICULO = vh.PK_VEHICULO   ");
                    query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");            
                    query.append(" GROUP BY lg.FK_VEHICULO ");
                    query.append(" ORDER BY vh.NUM_INTERNO; ");
                }else{
                    query.append(" FROM tbl_liquidacion_general lg INNER JOIN tbl_vehiculo v ON lg.FK_VEHICULO = v.PK_VEHICULO   ");
                    query.append(" WHERE (lg.estado = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                    query.append(" AND (v.ESTADO = 1) AND (v.FK_EMPRESA = ").append(pkEmpresa).append(") ");
                    query.append(" GROUP BY lg.FK_VEHICULO ");
                    query.append(" ORDER BY v.NUM_INTERNO; ");
                }
        }//FIN ELSE
        //System.out.println(query.toString());
        return query.toString();
    }

    public static String sql_ConsolidadoVehiculosNoLiquidados(String fechaInicio, String fechaFin, int pkEmpresa) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT PLACA, NUM_INTERNO, PK_VEHICULO, FK_EMPRESA ");
        query.append("FROM tbl_vehiculo as v  ");
        query.append("WHERE PK_VEHICULO NOT IN (SELECT DISTINCT FK_VEHICULO FROM tbl_liquidacion_general WHERE (FECHA_LIQUIDACION BETWEEN '")
                .append(fechaInicio).append("' AND '")
                .append(fechaFin).append("') AND (ESTADO=1)) AND (ESTADO = 1) AND (FK_EMPRESA = ")
                .append(pkEmpresa).append(");");

        return query.toString();
    }

    public static String sql_LiquidacionXLiquidador(String fechaInicio, String fechaFin, int pkUsuario, int pkEmpresa, String nombreEmpresa) {
        StringBuilder query = new StringBuilder();
        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))){
            query.append(" SELECT tv.PLACA, tv.NUM_INTERNO, count(lg.PK_LIQUIDACION_GENERAL) AS NUM_LIQUIDACIONES, SUM(lg.TOTAL_VALOR_VUELTAS) AS TOTAL_BRUTO, ");
            query.append("(SELECT concat(u.NOMBRE, u.APELLIDO) FROM tbl_usuario as u WHERE u.PK_USUARIO=").append(pkUsuario).append(") AS LIQUIDADOR,");
            query.append(" SUM(	IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL,");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0,");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ),");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ) ) AS TOTAL_DESCUENTO_AL_NETO,");
            query.append(" SUM((lg.TOTAL_VALOR_VUELTAS -   (IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL,");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0,");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ),");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.DESCUENTA_DEL_TOTAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ))) ) AS SUB_TOTAL, ");
            query.append(" SUM(IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL, ");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0,");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ),");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ) ) AS TOTAL_DESCUENTOS, ");
            query.append(" SUM(IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL, 0,");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ) ) AS TOTAL_OTROS_DESCUENTOS, ");
            query.append(" SUM( (  (lg.TOTAL_VALOR_VUELTAS - (IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL,");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0,");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ),");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ) ) ) ");
            query.append(" - (IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL, 0,");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ))   )  ) AS TOTAL,");
            query.append(" SUM(IF( (SELECT SUM(la2.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional as la2 WHERE (la2.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL)) IS NULL,0,(SELECT SUM(la2.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional as la2 WHERE (la2.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL)) )  ) AS TOTAL_PASAJEROS_DESCONTADOS ");
            query.append(" FROM tbl_liquidacion_general lg INNER JOIN tbl_vehiculo tv ON tv.PK_VEHICULO = lg.FK_VEHICULO ");
            query.append(" WHERE (lg.USUARIO = ").append(pkUsuario).append(") AND (lg.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
            query.append(" GROUP BY tv.PLACA ");
            query.append(" ORDER BY tv.PLACA ASC ");            
        }else
        {
            query.append(" SELECT tv.PLACA, tv.NUM_INTERNO, count(lg.PK_LIQUIDACION_GENERAL) AS NUM_LIQUIDACIONES, SUM(lg.TOTAL_VALOR_VUELTAS) AS TOTAL_BRUTO,  ");
            query.append("(SELECT concat(u.NOMBRE, u.APELLIDO) FROM tbl_usuario as u WHERE u.PK_USUARIO=").append(pkUsuario).append(") AS LIQUIDADOR,");
            query.append(" SUM(IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, ");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '  ").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0, ");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ),");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '  ").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) ) ) AS TOTAL_DESCUENTOS, ");
            query.append(" SUM((lg.TOTAL_VALOR_VUELTAS -   (IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '  ").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL,");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0,");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ), ");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ) )) ) AS SUB_TOTAL,");
            query.append(" SUM(IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) IS NULL, 0,");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) ) ) AS TOTAL_OTROS_DESCUENTOS,");
            query.append(" SUM( (  (lg.TOTAL_VALOR_VUELTAS - (IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL,");
            query.append(" IF( (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(") IS NULL, 0,");
            query.append(" (SELECT SUM(la3.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la3 WHERE (la3.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) ),");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_DESCUENTO = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append("  ) ) ) )");
            query.append(" - (IF( (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ) IS NULL, 0,");
            query.append(" (SELECT SUM(la1.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional as la1 INNER JOIN tbl_categoria_descuento as cd ON la1.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS WHERE (cd.APLICA_GENERAL = 1) AND (la1.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" ))))) AS TOTAL,");
            query.append(" SUM(IF( (SELECT SUM(la2.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional as la2 WHERE (la2.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL)) IS NULL,0,(SELECT SUM(la2.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional as la2 WHERE (la2.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL)) )  ) AS TOTAL_PASAJEROS_DESCONTADOS ");
            query.append(" FROM tbl_liquidacion_general lg INNER JOIN tbl_vehiculo tv ON tv.PK_VEHICULO = lg.FK_VEHICULO ");
            query.append(" WHERE (lg.USUARIO =").append(pkUsuario).append(") AND (lg.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ").append(" GROUP BY tv.PLACA").append(" ORDER BY tv.PLACA ASC;");
        }        
        //System.out.println(query.toString());
        return query.toString();       
    }
    
    
    /*REPORTE IPK LIQUIDACION*/
    public static String sql_reporteIPK(String fechaInicio, String fechaFin, int pkEmpresa, String nombreEmpresa) {
        StringBuilder query = new StringBuilder();
        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))) {
            query.append("SELECT v.PK_VEHICULO, v.PLACA, v.NUM_INTERNO, ");
                query.append(" 	IF((	SELECT count(r.NOMBRE) FROM tbl_ruta as r WHERE ( r.PK_RUTA IN ( SELECT ir.FK_RUTA ");
                query.append(" FROM tbl_liquidacion_vueltas as lv INNER JOIN tbl_informacion_registradora as ir ON ir.PK_INFORMACION_REGISTRADORA= lv.FK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.PORCENTAJE_RUTA IS NOT NULL) AND (ir.PORCENTAJE_RUTA <> 0) AND (ir.FK_RUTA IS NOT NULL) ");
                query.append(" AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                query.append(" AND (v.PK_VEHICULO = lg.FK_VEHICULO) AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append("GROUP BY(lv.FECHA_DESCUENTO))) AND (r.ESTADO = 1)) > 1, ");
                query.append(" 'NO APLICA', ");
                query.append(" IF ((	SELECT count(r.NOMBRE) FROM tbl_ruta as r WHERE ( r.PK_RUTA IN (	SELECT ir.FK_RUTA ");
                query.append(" FROM tbl_liquidacion_vueltas as lv INNER JOIN tbl_informacion_registradora as ir ON ir.PK_INFORMACION_REGISTRADORA= lv.FK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.PORCENTAJE_RUTA IS NOT NULL) AND (ir.PORCENTAJE_RUTA <> 0) AND (ir.FK_RUTA IS NOT NULL)  ");
                query.append(" AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (v.PK_VEHICULO = lg.FK_VEHICULO) AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY(lv.FECHA_DESCUENTO))) AND (r.ESTADO = 1)) = 0,  ");
                query.append(" 'NO APLICA', ");
                query.append("(	SELECT r.NOMBRE ");
                query.append(" FROM tbl_ruta as r WHERE ( r.PK_RUTA IN ( SELECT ir.FK_RUTA ");
                query.append(" FROM tbl_liquidacion_vueltas as lv INNER JOIN tbl_informacion_registradora as ir ON ir.PK_INFORMACION_REGISTRADORA= lv.FK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.PORCENTAJE_RUTA IS NOT NULL) AND (ir.PORCENTAJE_RUTA <> 0) AND (ir.FK_RUTA IS NOT NULL) ");
                query.append(" AND (lv.FECHA_DESCUENTO BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (v.PK_VEHICULO = lg.FK_VEHICULO) AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY(lv.FECHA_DESCUENTO))) AND (r.ESTADO = 1)))) AS RUTA, ");

                query.append(" SUM( IF((SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL) ) IS NULL, 0,");
                query.append(" (SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL)))) AS KM_TOTAL, ");

                query.append(" SUM( IF(lg.TOTAL_PASAJEROS_LIQUIDADOS = 0, 0, lg.TOTAL_PASAJEROS_LIQUIDADOS) / IF((SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL) ) IS NULL, 0, ");
                query.append(" (SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL) )) ) AS IPK, ");


                query.append(" SUM((SELECT COUNT(lv.PK_LIQUIDACION_VUELTAS)  ");
                query.append(" FROM tbl_liquidacion_vueltas lv INNER JOIN tbl_informacion_registradora ir ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.FK_VEHICULO = v.PK_VEHICULO)  ");
                query.append(" AND (lv.FECHA_MODIFICACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append(" )) AS VUELTAS_LIQUIDADAS, ");
                
                query.append(" IF(SUM((	SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la  ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + lg.TOTAL_PASAJEROS_LIQUIDADOS ");
                query.append(" ) = 0, 0, ");
                query.append(" (SUM((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + lg.TOTAL_PASAJEROS_LIQUIDADOS)) ) AS TOTAL_PASAJEROS, ");
                
                query.append(" IF( SUM(( SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL)) = 0, ");
                query.append(" 0, ");
                query.append(" (SUM((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la  ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL)))) AS TOTAL_PASAJEROS_DESCONTADOS, ");
                
                
                query.append(" IF(SUM(TOTAL_PASAJEROS_LIQUIDADOS)=0, 0, SUM(TOTAL_PASAJEROS_LIQUIDADOS)) AS TOTAL_PASAJEROS_LIQUIDADOS, ");
                
                query.append(" IF((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (tf.ESTADO = 1) AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA))  ");
                query.append(" IS NULL, 0, ");
                query.append(" (SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA))) AS TARIFA, ");
                
                
                query.append(" IF(SUM(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS) ) ");
                query.append(" IS NULL, ");
                query.append(" 0, ");
                query.append(" SUM(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS )  ");
                query.append(" )) AS TOTAL_BRUTO, ");
                
                query.append(" SUM(IF((	SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS   ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)   AND (cd.ESTADO=1)    ");
                query.append(" AND (cd.DESCUENTA_DEL_TOTAL = 1)   AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("'))IS NULL,  ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append("  WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)   AND (cd.ESTADO=1)  AND(cd.DESCUENTA_DEL_TOTAL = 1)   ");
                query.append("   AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("'))) ) AS TOTAL_DESCUENTO_AL_NETO,   ");;
                
                
                
                query.append(" SUM( ");
                query.append(" IF(((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (tf.ESTADO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append(" ')");                
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS)IS NULL,");
                query.append(" 0, ");
                query.append(" ((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1)   AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append(" ')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS)) ");
                query.append(" - ");
                query.append(" IF(( SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)   AND (cd.ESTADO=1) ");
                query.append(" AND (cd.DESCUENTA_DEL_TOTAL = 1)   AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)   AND (cd.ESTADO=1)  AND(cd.DESCUENTA_DEL_TOTAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("'))) ");
                query.append(") AS SUB_TOTAL,  ");
                
                
                
                
                
                
                query.append(" SUM(IF((	SELECT SUM(la.VALOR_DESCUENTO)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (cd.ESTADO=1)  ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" ) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND(cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin)
                     .append("')))) AS TOTAL_DESCUENTOS,  ");

                query.append(" SUM(IF((	SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS  ");
                query.append(" WHERE (cd.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" ) IS NULL,  ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (la.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (la.ESTADO=1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)))) AS TOTAL_OTROS_DESCUENTOS, ");
                             

                query.append(" SUM(IF(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE  (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS)  ");
                query.append(" IS NULL,  ");
                query.append(" 0, ");
                query.append(" ((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS ) ) ");
                query.append(" - ");
                query.append(" IF( ( SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1)  ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("'))");
                query.append(" IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND(cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ))");;
                query.append(" - ");
                query.append(" IF( (	SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND (cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') )");
                query.append(" IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND(cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ) )");
                query.append(" ) AS TOTAL_LIQUIDACION ");
                query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_vehiculo as v ON lg.FK_VEHICULO = v.PK_VEHICULO  ");
                query.append(" WHERE (lg.estado = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (v.ESTADO = 1) AND (v.FK_EMPRESA = ").append(pkEmpresa).append(") AND (v.NUM_INTERNO NOT LIKE '%TG') ");
                query.append(" GROUP BY lg.FK_VEHICULO ");
                query.append(" ORDER BY v.NUM_INTERNO;");   
        }//fin si
        else{
                query.append("SELECT v.PK_VEHICULO, v.PLACA, v.NUM_INTERNO, ");
                query.append(" 	IF((	SELECT count(r.NOMBRE)  ");
                query.append(" FROM tbl_ruta as r ");
                query.append(" WHERE ( r.PK_RUTA IN (	SELECT ir.FK_RUTA ");
                query.append(" FROM tbl_liquidacion_vueltas as lv INNER JOIN tbl_informacion_registradora as ir ON ir.PK_INFORMACION_REGISTRADORA= lv.FK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.PORCENTAJE_RUTA IS NOT NULL) ");
                query.append(" AND (ir.PORCENTAJE_RUTA <> 0) AND (ir.FK_RUTA IS NOT NULL) ");
                query.append(" AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");
                query.append(" AND (v.PK_VEHICULO = lg.FK_VEHICULO) AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append("GROUP BY(lv.FECHA_DESCUENTO))) AND (r.ESTADO = 1)) > 1, ");
                query.append(" 'NO APLICA', ");
                query.append(" IF ((	SELECT count(r.NOMBRE) FROM tbl_ruta as r WHERE ( r.PK_RUTA IN (	SELECT ir.FK_RUTA ");
                query.append(" FROM tbl_liquidacion_vueltas as lv INNER JOIN tbl_informacion_registradora as ir ON ir.PK_INFORMACION_REGISTRADORA= lv.FK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.PORCENTAJE_RUTA IS NOT NULL) AND (ir.PORCENTAJE_RUTA <> 0) AND (ir.FK_RUTA IS NOT NULL)  ");
                query.append(" AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (v.PK_VEHICULO = lg.FK_VEHICULO) AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY(lv.FECHA_DESCUENTO))) AND (r.ESTADO = 1)) = 0,  ");
                query.append(" 'NO APLICA', ");
                query.append("(	SELECT r.NOMBRE ");
                query.append(" FROM tbl_ruta as r WHERE ( r.PK_RUTA IN ( SELECT ir.FK_RUTA ");
                query.append(" FROM tbl_liquidacion_vueltas as lv INNER JOIN tbl_informacion_registradora as ir ON ir.PK_INFORMACION_REGISTRADORA= lv.FK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.PORCENTAJE_RUTA IS NOT NULL) AND (ir.PORCENTAJE_RUTA <> 0) AND (ir.FK_RUTA IS NOT NULL) ");
                query.append(" AND (lv.FECHA_DESCUENTO BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (v.PK_VEHICULO = lg.FK_VEHICULO) AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY(lv.FECHA_DESCUENTO))) AND (r.ESTADO = 1)))) AS RUTA, ");

                query.append(" SUM( IF((SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0), 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL) ) IS NULL, 0,");
                query.append(" (SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL)))) AS KM_TOTAL, ");

                query.append(" SUM( IF(lg.TOTAL_PASAJEROS_LIQUIDADOS = 0, 0, lg.TOTAL_PASAJEROS_LIQUIDADOS) / IF((SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL) ) IS NULL, 0, ");
                query.append(" (SELECT SUM(FORMAT((ir.DISTANCIA_METROS / 1000.0) , 1)) ");
                query.append(" FROM tbl_informacion_registradora AS ir INNER JOIN tbl_liquidacion_vueltas as lv ON lv.FK_INFORMACION_REGISTRADORA= ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.ESTADO = 1) AND (lv.ESTADO = 1) AND (ir.FK_VEHICULO =lg.FK_VEHICULO) AND (ir.DISTANCIA_METROS>=0) AND (ir.PORCENTAJE_RUTA >=0 ) ");
                query.append(" AND (lv.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL) AND (lv.FECHA_DESCUENTO BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" GROUP BY(lv.FK_LIQUIDACION_GENERAL) )) ) AS IPK, ");


                query.append(" SUM((SELECT COUNT(lv.PK_LIQUIDACION_VUELTAS)  ");
                query.append(" FROM tbl_liquidacion_vueltas lv INNER JOIN tbl_informacion_registradora ir ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA ");
                query.append(" WHERE (ir.FK_VEHICULO = v.PK_VEHICULO)  ");
                query.append(" AND (lv.FECHA_MODIFICACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = lv.FK_LIQUIDACION_GENERAL) ");
                query.append(" )) AS VUELTAS_LIQUIDADAS, ");
                
                query.append(" IF(SUM((	SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la  ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + lg.TOTAL_PASAJEROS_LIQUIDADOS ");
                query.append(" ) = 0, 0, ");
                query.append(" (SUM((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL) + lg.TOTAL_PASAJEROS_LIQUIDADOS)) ) AS TOTAL_PASAJEROS, ");
                
                query.append(" IF( SUM(( SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) ");
                query.append(" FROM tbl_liquidacion_adicional AS la ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL)) = 0, ");
                query.append(" 0, ");
                query.append(" (SUM((SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la  ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" GROUP BY lg.PK_LIQUIDACION_GENERAL)))) AS TOTAL_PASAJEROS_DESCONTADOS, ");
                
                query.append(" IF(SUM(TOTAL_PASAJEROS_LIQUIDADOS)=0, 0, SUM(TOTAL_PASAJEROS_LIQUIDADOS)) AS TOTAL_PASAJEROS_LIQUIDADOS, ");
                
                query.append(" IF((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (tf.ESTADO = 1) AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA))  ");
                query.append(" IS NULL, 0, ");
                query.append(" (SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA))) AS TARIFA, ");
                
                
                query.append(" IF(SUM(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS) ) ");
                query.append(" IS NULL, ");
                query.append(" 0, ");
                query.append(" SUM(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS )  ");
                query.append(" )) AS TOTAL_BRUTO, ");
                
                
                query.append(" SUM(IF((	SELECT SUM(la.VALOR_DESCUENTO)  ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (cd.ESTADO=1)  ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" ) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND(cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')))) AS TOTAL_DESCUENTOS,  ");

                query.append(" SUM(IF((	SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS  ");
                query.append(" WHERE (cd.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" ) IS NULL,  ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento As cd ON la.FK_CATEGORIAS= cd.PK_CATEGORIAS_DESCUENTOS ");
                query.append(" WHERE (la.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (la.ESTADO=1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (cd.APLICA_GENERAL = 1) AND (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)))) AS TOTAL_OTROS_DESCUENTOS, ");

                query.append(" SUM(IF(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS ");
                query.append(" ) IS NULL, ");
                query.append(" 0, ");
                query.append(" ((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS)) ");
                query.append(" - ");
                query.append(" IF(( SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" ) IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL)  ");
                query.append(" AND (cd.ESTADO=1)  ");
                query.append(" AND(cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')))");
                query.append(" ) AS SUB_TOTAL, ");

                query.append(" SUM(IF(((SELECT tf.VALOR_TARIFA  ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE  (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS)  ");
                query.append(" IS NULL,  ");
                query.append(" 0, ");
                query.append(" ((SELECT tf.VALOR_TARIFA ");
                query.append(" FROM tbl_tarifa_fija as tf  ");
                query.append(" WHERE (tf.ESTADO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (lg.FK_TARIFA_FIJA = tf.PK_TARIFA_FIJA)) * lg.TOTAL_PASAJEROS_LIQUIDADOS ) ) ");
                query.append(" - ");
                query.append(" IF( ( SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1)  ");
                query.append(" AND (cd.APLICA_DESCUENTO = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("'))");
                query.append(" IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND(cd.APLICA_DESCUENTO = 1)  ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ))");;
                query.append(" - ");
                query.append(" IF( (	SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND (cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') )");
                query.append(" IS NULL, ");
                query.append(" 0, ");
                query.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
                query.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
                query.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) ");
                query.append(" AND (cd.ESTADO=1) ");
                query.append(" AND(cd.APLICA_GENERAL = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ) )");
                query.append(" ) AS TOTAL_LIQUIDACION ");
                query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_vehiculo as v ON lg.FK_VEHICULO = v.PK_VEHICULO  ");
                query.append(" WHERE (lg.estado = 1) ");
                query.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')");
                query.append(" AND (v.ESTADO = 1) AND (v.FK_EMPRESA = ").append(pkEmpresa).append(") AND (v.NUM_INTERNO NOT LIKE '%TG') ");
                query.append(" GROUP BY lg.FK_VEHICULO ");
                query.append(" ORDER BY v.NUM_INTERNO;");        
        }
        //System.out.println(query.toString());
        return query.toString();       
    }
    
    
    
    
    
    
    /*CONSULTA DE LIQUIDACION*/
    public static String sql_consulta_liquidacion(String fecha_inicio, String fecha_fin, int fk_vehiculo, int idEmpres, String nombreEmpresa) {
        StringBuilder sql = new StringBuilder();
        if ((nombreEmpresa.equalsIgnoreCase("FUSACATAN")) || (nombreEmpresa.equalsIgnoreCase("tierragrata")) || (nombreEmpresa.equalsIgnoreCase("tierra grata"))) {
            sql.append(" SELECT lg.PK_LIQUIDACION_GENERAL, lg.FK_VEHICULO, lg.FK_TARIFA_FIJA, lg.USUARIO, lg.FK_CONDUCTOR, ");
            sql.append(" lg.ESTADO, tv.PLACA, tv.NUM_INTERNO, concat (tu.APELLIDO, \" \", tu.NOMBRE) AS LIQUIDADOR,  ");
            sql.append(" concat (tc.APELLIDO, \" \", tc.NOMBRE) AS CONDUCTOR, ");
            sql.append(" lg.TOTAL_PASAJEROS_LIQUIDADOS, ");
            sql.append(" tf.VALOR_TARIFA,  ");
            sql.append(" lg.TOTAL_VALOR_VUELTAS AS TOTAL_BRUTO,  ");            
                       
            /*DESCUENTO AL NETO*/
            sql.append(" IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.DESCUENTA_DEL_TOTAL = 1) ");        
            sql.append("AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append("0, ");        
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) ");        
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");        
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.DESCUENTA_DEL_TOTAL = 1) ");
            sql.append("AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin)
               .append("'))) AS TOTAL_DESCUENTO_AL_NETO, ");
            
             /*SUBTOTAL*/
            sql.append("  (lg.TOTAL_VALOR_VUELTAS - ( IF( (SELECT SUM(la.VALOR_DESCUENTO)  ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.DESCUENTA_DEL_TOTAL = 1) ");
            sql.append("AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append("0, ");        
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) ");        
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");        
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.DESCUENTA_DEL_TOTAL = 1) ");
            sql.append("AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin)
               .append("'))))) AS SUBTOTAL,");
            /*DESCUENTOS OPERATIVOS*/
            sql.append(" IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");            
            sql.append(" 0 , ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1)  ");
            sql.append("AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin)
               .append("'))) AS TOTAL_VALOR_DESCUENTOS, ");
            
            /*DESCUENTO ADMINISTRATIVO*/
            sql.append(" IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1)  ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append(" 0 , ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fecha_inicio).append("' AND '").append(fecha_fin)
               .append("') ) ) AS TOTAL_VALOR_OTROS_DESCUENTOS, ");            
            
            
            
            /*TOTAL LIQUIDACION*/
            sql.append(" ( (lg.TOTAL_VALOR_VUELTAS -  ");
            /*descuentos al neto*/
            sql.append("  (IF ( (SELECT SUM(la.VALOR_DESCUENTO) ");            
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append("  WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.DESCUENTA_DEL_TOTAL = 1) ");
            sql.append("  AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fecha_inicio).append("' AND '").append(fecha_fin).append("'))IS NULL, ");            
            sql.append("0,  ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.DESCUENTA_DEL_TOTAL = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("'))))) ");
            sql.append(" - ");
            /*descuentos operativos*/
            sql.append(" (IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS  ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL,  ");
            sql.append(" 0 , ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO)  ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("'))) )");
            sql.append(" - ");
            /*descuentos administrativos*/
            sql.append(" (IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL,  ");
            sql.append(" 0,  ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) ");
            sql.append(" AND (lg.FECHA_LIQUIDACION BETWEEN '") .append(fecha_inicio).append("' AND '").append(fecha_fin).append("'))))");
            sql.append(" )AS TOTAL_LIQUIDACION, lg.FECHA_LIQUIDACION, lg.FECHA_MODIFICACION ");
            sql.append(" ");
            sql.append(" ");            
            sql.append("FROM tbl_liquidacion_general lg ");
            sql.append("INNER JOIN tbl_tarifa_fija tf ON tf.PK_TARIFA_FIJA = lg.FK_TARIFA_FIJA ");
            sql.append("INNER JOIN tbl_conductor tc ON tc.PK_CONDUCTOR = lg.FK_CONDUCTOR  ");
            sql.append("INNER JOIN tbl_usuario tu ON tu.PK_USUARIO = lg.USUARIO ");
            sql.append("INNER JOIN tbl_vehiculo tv ON tv.PK_VEHICULO = lg.FK_VEHICULO ");
            sql.append("WHERE lg.ESTADO > -1 ");
            if (fk_vehiculo != 0) {
                sql.append("AND lg.FK_VEHICULO =  ").append(fk_vehiculo);
            }
            sql.append(" AND (lg.FECHA_LIQUIDACION between '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ");
            sql.append("ORDER BY lg.FECHA_LIQUIDACION ;");        
        }else{//FIN SI; CONSULTA NEIVA            
            sql.append("SELECT lg.PK_LIQUIDACION_GENERAL, ");
            sql.append("lg.FK_VEHICULO, ");
            sql.append("lg.FK_TARIFA_FIJA, ");
            sql.append("lg.USUARIO, ");
            sql.append("lg.FK_CONDUCTOR, ");
            sql.append("lg.ESTADO, ");        
            sql.append("tv.PLACA, ");
            sql.append("tv.NUM_INTERNO, ");                    
            sql.append("concat (tu.APELLIDO, \" \", tu.NOMBRE) AS LIQUIDADOR, ");        
            sql.append("concat (tc.APELLIDO, \" \", tc.NOMBRE) AS CONDUCTOR, ");
            sql.append("lg.TOTAL_PASAJEROS_LIQUIDADOS, ");
            sql.append("tf.VALOR_TARIFA, ");        
            sql.append("lg.TOTAL_VALOR_VUELTAS as TOTAL_BRUTO, ");
            /*DESCUENTOS OPERATIVOS*/            
            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");
            sql.append(" AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ) IS NULL, ");
            sql.append(" 0, ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");
            sql.append(" AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) ");
            sql.append(" ) AS TOTAL_VALOR_DESCUENTOS, ");
            /*SUBTOTAL*/
            sql.append(" (lg.TOTAL_VALOR_VUELTAS - ");
            sql.append(" IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");
            sql.append(" AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ) IS NULL, ");
            sql.append(" 0, ");            
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");            
            sql.append(" AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) ");
            sql.append(" )) as SUBTOTAL,");            
            /*DESCUENTOS ADMINISTRATIVOS*/
            sql.append(" IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");            
            sql.append(" AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append(" 0, ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");
            sql.append(" AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) ");
            sql.append(" ) AS TOTAL_VALOR_OTROS_DESCUENTOS, ");
            /*TOTAL LIQUIDACION*/           
            
            sql.append(" (");
            sql.append(" lg.TOTAL_VALOR_VUELTAS - ");
            sql.append(" (IF( (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");                        
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");                        
            sql.append(" AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL,");
            sql.append(" 0, ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append("  FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");                        
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");                                                            
            sql.append(" AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) ");
            sql.append(" )) ");            
            sql.append(" - ");
            sql.append(" (IF( (	SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");
            sql.append(" AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append(" 0, ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) ");
            sql.append(" FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS ");
            sql.append(" WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (cd.ESTADO=1) ");
            sql.append(" AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) ");
            sql.append(" ))) as TOTAL_LIQUIDACION, ");                        
            
            sql.append(" lg.FECHA_LIQUIDACION, ");
            sql.append(" lg.FECHA_MODIFICACION ");
            sql.append("FROM tbl_liquidacion_general lg ");
            sql.append("INNER JOIN tbl_tarifa_fija tf ON tf.PK_TARIFA_FIJA = lg.FK_TARIFA_FIJA ");
            sql.append("INNER JOIN tbl_conductor tc ON tc.PK_CONDUCTOR = lg.FK_CONDUCTOR  ");
            sql.append("INNER JOIN tbl_usuario tu ON tu.PK_USUARIO = lg.USUARIO ");
            sql.append("INNER JOIN tbl_vehiculo tv ON tv.PK_VEHICULO = lg.FK_VEHICULO ");
            sql.append("WHERE lg.ESTADO > -1 ");
            if (fk_vehiculo != 0) {
                sql.append("AND lg.FK_VEHICULO =  ").append(fk_vehiculo);
            }
            sql.append(" AND (lg.FECHA_LIQUIDACION between '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ");
            sql.append("ORDER BY lg.FECHA_LIQUIDACION ;");        
        }
        System.out.println(sql.toString());
        return sql.toString();       
    }

    public static String sql_consulta_categorias_descuenta_pax(String fechaInicio, String fechaFin, int id_vehiculo) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT id_vehiculo, placa, id_categoria, nombre, sum(pasajeros) as total_pasajeros_categoria, ");
        /*TOTAL PASAJEROS DESCONTADOS*/
        query.append(" ( SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON la.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')  AND (lg.ESTADO=1) AND (v.PK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" GROUP BY (v.PK_VEHICULO)) as total_pasajeros_descontados, ");
        /*TOTAL PASAJEROS LIQUIDADOS*/        
        query.append(" (SELECT SUM(lg.TOTAL_PASAJEROS_LIQUIDADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.ESTADO=1) AND (lg.FK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" ) AS total_pasajeros_liquidados, ");       
        /*TOTAL PASAJEROS*/
        query.append(" ( ");        
        query.append(" ( SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON la.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')  AND (lg.ESTADO=1) AND (v.PK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" GROUP BY (v.PK_VEHICULO)) ");
        query.append(" + ");        
        query.append(" (SELECT SUM(lg.TOTAL_PASAJEROS_LIQUIDADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.ESTADO=1) AND (lg.FK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" ) ");
        query.append(" ) AS total_pasajeros, ");       
        
        query.append(" ((( SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON la.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')  AND (lg.ESTADO=1) AND (v.PK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" GROUP BY (v.PK_VEHICULO)) ");
        query.append(" / "); 
        query.append(" ( ");        
        query.append(" ( SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON la.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')  AND (lg.ESTADO=1) AND (v.PK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" GROUP BY (v.PK_VEHICULO)) ");
        query.append(" + ");        
        query.append(" (SELECT SUM(lg.TOTAL_PASAJEROS_LIQUIDADOS) ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') AND (lg.ESTADO=1) AND (lg.FK_VEHICULO = ").append(id_vehiculo).append(")");
        query.append(" ))) * 100.0) as indicador_pasajeros");
        
        
        query.append(" FROM (SELECT lg.FK_VEHICULO as id_vehiculo, la.FK_CATEGORIAS as id_categoria, la.CANTIDAD_PASAJEROS_DESCONTADOS as pasajeros, v.PLACA, cd1.NOMBRE ");
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON la.FK_LIQUIDACION_GENERAL=lg.PK_LIQUIDACION_GENERAL INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO INNER JOIN tbl_categoria_descuento as cd1 ON cd1.PK_CATEGORIAS_DESCUENTOS=la.FK_CATEGORIAS ");        
        query.append(" WHERE (lg.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("') ");        
        query.append(" AND (la.FK_LIQUIDACION_GENERAL IN (SELECT lg1.PK_LIQUIDACION_GENERAL ");
        query.append(" FROM tbl_liquidacion_general as lg1  ");
        query.append(" WHERE (lg1.ESTADO=1) AND (lg1.FECHA_LIQUIDACION BETWEEN '").append(fechaInicio).append("' AND '").append(fechaFin).append("')))");
        query.append(" AND (la.FK_CATEGORIAS IN (SELECT cd.PK_CATEGORIAS_DESCUENTOS ");
        query.append(" FROM tbl_categoria_descuento as cd  ");
        query.append(" WHERE (cd.ESTADO=1) AND (cd.DESCUENTA_PASAJEROS = 1))) ");
        query.append(" ORDER BY(lg.FK_VEHICULO)) AS C  ");
        query.append(" WHERE (id_vehiculo = (").append(id_vehiculo).append(") )");
        query.append(" GROUP BY(id_categoria) ");
        System.out.println("Categorias///// "+query.toString());
        return query.toString();       
    }
    
    public static String sql_consulta_vehiculos(String fechaInicio, String fechaFin) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT DISTINCT v.PK_VEHICULO ");        
        query.append(" FROM tbl_liquidacion_general as lg INNER JOIN tbl_vehiculo as v ON v.PK_VEHICULO=lg.FK_VEHICULO ");        
        query.append(" WHERE (lg.ESTADO=1) AND (v.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fechaInicio).append("' AND '").append(fechaFin).append("'); ");                
        //System.out.println(query.toString());
        return query.toString();       
    }
}
