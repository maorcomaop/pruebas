/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

/**
 *
 * @author lider_desarrollador
 */
public class IndicadorSQL {
    
    public static String sql_indicadorCumplimientoRutaSinRuta(String fecha) {
        
        String sql = "SELECT"
                    + " -1 as PK_RUTA,"
                    + " 'Ruta no detectada' as NOMBRE_RUTA,"
                    + " ir.NUMERO_VUELTA,"
                    + " avg(ir.PORCENTAJE_RUTA) as PORCENTAJE_RUTA"
                    + " FROM tbl_informacion_registradora as ir"
                    + " WHERE ir.FECHA_INGRESO = ? and"
                    + " ir.FK_RUTA is null and"
                    + " ir.NUMERO_VUELTA >= 1"
                    + " GROUP BY ir.NUMERO_VUELTA"
                    + " ORDER BY ir.NUMERO_VUELTA asc";
        return sql;
    }
    
    public static String sql_indicadorCumplimientoRuta(String fecha, String lst_ruta) {
        
        String filtro_ruta = "";
        if (lst_ruta != null && lst_ruta != "") {
            filtro_ruta = " and ir.FK_RUTA IN (" + lst_ruta + ")";
        }
        
        String sql = "SELECT"
                    + " r.PK_RUTA,"
                    + " r.NOMBRE as NOMBRE_RUTA,"
                    + " ir.NUMERO_VUELTA,"
                    + " avg(ir.PORCENTAJE_RUTA) as PORCENTAJE_RUTA"
                    + " FROM tbl_informacion_registradora as ir"
                    + " INNER JOIN tbl_ruta as r on"
                    + "   ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                    + " WHERE ir.FECHA_INGRESO = ? and"
                    + " ir.NUMERO_VUELTA >= 1"
                    + filtro_ruta
                    + " GROUP BY ir.FK_RUTA, ir.NUMERO_VUELTA"
                    + " ORDER BY r.NOMBRE asc, ir.NUMERO_VUELTA asc";
        
        return sql;
    }
    
    public static String sql_indicadorPasajeroHora_entradasSinRuta(String fecha) {
        
        String sql = "SELECT" 
                        + " s.PK_RUTA,"
                        + " s.NOMBRE_RUTA,"
                        + " s.HORA,"
                        + " ifnull(sum(s.ENTRADAS),0) as ENTRADAS from ("

                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     -1 as PK_RUTA,"
                        + "     'Ruta no detectada' as NOMBRE_RUTA,"
                        + "     hour(ir.HORA_INGRESO) as HORA,"
                        + "     ir.DIFERENCIA_NUM as ENTRADAS"
                        + " FROM tbl_informacion_registradora as ir"
                        + " WHERE ir.FECHA_INGRESO = ? and"
                        + "      (ir.FECHA_INGRESO != ir.FECHA_SALIDA_BASE_SALIDA) and"
                        + "       ir.FK_RUTA is null"
                        + " GROUP BY ir.PK_INFORMACION_REGISTRADORA"

                        + " UNION"
                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     -1 as PK_RUTA,"
                        + "     'Ruta no detectada' as NOMBRE_RUTA,"
                        + "     hour(ir.HORA_SALIDA_BASE_SALIDA) as HORA,"

                        + "     if((SELECT min(pc.ENTRADAS) FROM tbl_punto_control as pc"
                        + "              WHERE pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA)"
                        + "             < ir.ENTRADAS_BASE_SALIDA, 0,"
                        + "             (SELECT min(pc.ENTRADAS) FROM tbl_punto_control as pc"
                        + "              WHERE pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA)"
                        + "             - ir.ENTRADAS_BASE_SALIDA) as ENTRADAS"

                        + " FROM tbl_informacion_registradora as ir"
                        + " WHERE ir.FECHA_INGRESO = ? and"
                        + "       ir.FECHA_SALIDA_BASE_SALIDA = ? and"
                        + "       ir.FK_RUTA is null"
                        + " GROUP BY ir.PK_INFORMACION_REGISTRADORA"

                        + " UNION"
                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     -1 as PK_RUTA,"
                        + "     'Ruta no detectada' as NOMBRE_RUTA,"
                        + "     hour(ir.HORA_INGRESO) as HORA,"

                        + "     ir.ENTRADAS -"
                        + "         (SELECT max(pc.ENTRADAS) FROM tbl_punto_control as pc"
                        + "          WHERE pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA) as ENTRADAS"

                        + " FROM tbl_informacion_registradora as ir"
                        + " WHERE ir.FECHA_INGRESO = ? and"
                        + "       ir.FECHA_SALIDA_BASE_SALIDA = ? and"
                        + "       ir.FK_RUTA is null"
                        + " GROUP BY ir.PK_INFORMACION_REGISTRADORA"

                        + " UNION"
                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     -1 as PK_RUTA,"
                        + "     'Ruta no detectada' as NOMBRE_RUTA,"
                        + "     hour(pc.HORA_PTO_CONTROL) as HORA,"
                        + "     max(pc.entradas) - min(pc.entradas) as ENTRADAS"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_punto_control as pc on"
                        + "     ir.PK_INFORMACION_REGISTRADORA = pc.FK_INFO_REGIS"
                        + " WHERE" 
                        + "     ir.FECHA_INGRESO = ? and"
                        + "     ir.FECHA_SALIDA_BASE_SALIDA = ? and"
                        + "     ir.FK_RUTA is null"
                        + " GROUP BY pc.FK_INFO_REGIS) s GROUP BY s.PK_RUTA, s.HORA";
        return sql;
    }
    
    public static String sql_indicadorPasajeroHora_entradas(String fecha) {
        
        String sql = "SELECT"
                        + " s.PK_RUTA,"
                        + " s.NOMBRE_RUTA,"
                        + " s.HORA,"
                        + " sum(s.ENTRADAS) as ENTRADAS from ("

                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     ir.FK_RUTA as PK_RUTA,"
                        + "     r.NOMBRE as NOMBRE_RUTA,"
                        + "     hour(ir.HORA_INGRESO) as HORA,"
                        + "     ir.DIFERENCIA_NUM as ENTRADAS"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_ruta as r on"
                        + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                        + " WHERE ir.FECHA_INGRESO = ? and"
                        + "      (ir.FECHA_INGRESO != ir.FECHA_SALIDA_BASE_SALIDA)"
                        + " GROUP BY ir.PK_INFORMACION_REGISTRADORA"

                        + " UNION"
                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     ir.FK_RUTA as PK_RUTA,"
                        + "     r.NOMBRE as NOMBRE_RUTA,"
                        + "     hour(ir.HORA_SALIDA_BASE_SALIDA) as HORA,"

                        + "     if((SELECT min(pc.ENTRADAS) FROM tbl_punto_control as pc"
                        + "              WHERE pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA)"
                        + "             < ir.ENTRADAS_BASE_SALIDA, 0,"
                        + "             (SELECT min(pc.ENTRADAS) FROM tbl_punto_control as pc"
                        + "              WHERE pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA)"
                        + "             - ir.ENTRADAS_BASE_SALIDA) as ENTRADAS"

                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_ruta as r on"
                        + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                        + " where ir.FECHA_INGRESO = ? and"
                        + "     ir.FECHA_SALIDA_BASE_SALIDA = ?"
                        + " GROUP BY ir.PK_INFORMACION_REGISTRADORA"

                        + " UNION"
                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     ir.FK_RUTA as PK_RUTA,"
                        + "     r.NOMBRE as NOMBRE_RUTA,"
                        + "     hour(ir.HORA_INGRESO) as HORA,"

                        + "     ir.ENTRADAS -"
                        + "         (SELECT max(pc.ENTRADAS) FROM tbl_punto_control as pc"
                        + "          WHERE pc.FK_INFO_REGIS = ir.PK_INFORMACION_REGISTRADORA) as ENTRADAS"

                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_ruta as r on"
                        + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                        + " where ir.FECHA_INGRESO = ? and"
                        + "     ir.FECHA_SALIDA_BASE_SALIDA = ?"
                        + " GROUP BY ir.PK_INFORMACION_REGISTRADORA"

                        + " UNION"
                        + " SELECT"
                        + "     ir.PK_INFORMACION_REGISTRADORA as IR_PK,"
                        + "     ir.FK_RUTA as PK_RUTA,"
                        + "     r.NOMBRE as NOMBRE_RUTA,"
                        + "     hour(pc.HORA_PTO_CONTROL) as HORA,"
                        + "     max(pc.entradas) - min(pc.entradas) as ENTRADAS"
                        + " FROM tbl_informacion_registradora as ir"
                        + " INNER JOIN tbl_punto_control as pc on"
                        + "     ir.PK_INFORMACION_REGISTRADORA = pc.FK_INFO_REGIS"
                        + " INNER JOIN tbl_ruta as r on"
                        + "     ir.FK_RUTA = r.PK_RUTA and r.ESTADO = 1"
                        + " WHERE"
                        + "     ir.FECHA_INGRESO = ? and"
                        + "     ir.FECHA_SALIDA_BASE_SALIDA = ?"
                        + " GROUP BY pc.FK_INFO_REGIS) s GROUP BY s.PK_RUTA, s.HORA";
        
        return sql;
    }    
}
