/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class UtilBD {

    // Cierra flujo de envio de instruccion SQL
    public static void closeStatement(Statement s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    // Cierra flujo de envio de instruccion SQL precompilada
    public static void closePreparedStatement(Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    // Cierra flujo de recepcion de resultados
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    // Convierte valor de fecha en cadena de texto formateada
    public static String setFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    // Genera cadena con num cantidad de elementos NULL para instruccion SQL
    public static String getNullFields(int num) {
        String nulls = "";
        for (int i = 0; i < num - 1; i++) {
            nulls += "NULL , ";
        }
        return nulls;
    }
}
