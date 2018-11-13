/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.CommandGPS;
import com.registel.rdw.logica.DataGPS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author USER
 */
public class DataGPSBD {

    public ArrayList<DataGPS> selectAll() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        Statement statement;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_data_gps ORDER BY insert_date DESC";

        try {
            con.setAutoCommit(false);
            statement = con.createStatement();
            rs = statement.executeQuery(sql);

            con.commit();
            
            ArrayList<DataGPS> lst_dataGPS = new ArrayList<>();
            while (rs.next()) {
                DataGPS a = new DataGPS();
                a.setId(rs.getInt("PK"));
                a.setSystemCode(rs.getString("system_code"));
                a.setMessageType(rs.getInt("message_type"));
                a.setUnitID(rs.getString("unit_id"));
                a.setMessageNumerator(rs.getInt("message_numerator"));
                a.setTransReasson(rs.getInt("trans_reason"));
                a.setTransReasonSpecificData(rs.getString("trans_reason_specific_data"));
                a.setLongitude(rs.getDouble("longitude"));
                a.setLatitude(rs.getDouble("latitude"));
                a.setAltitude(rs.getDouble("altitude"));
                a.setMileageCounter(rs.getDouble("mileage_counter"));
                a.setGroundSpeed(rs.getDouble("ground_speed"));
                a.setSpeedDirection(rs.getDouble("speed_direction"));
                a.setGpsDate(rs.getTimestamp("gps_utc_date"));
                a.setNumberSatellites(rs.getInt("number_satellites"));
                a.setInsertDate(rs.getTimestamp("insert_date"));
                lst_dataGPS.add(a);
            }
            return lst_dataGPS;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {

            try {
                con.setAutoCommit(false);
            } catch (SQLException e) {
                System.out.println(e);
            }

            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public int saveCommand(CommandGPS commandGPS) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement statement = null;

        String sql = "INSERT INTO tbl_gps_command_send "
                + "(command_key, sent, replied) VALUES "
                + "('" + commandGPS.getCommandKey() + "', " + commandGPS.isSent() + "," + commandGPS.isReplied() + ")";

        System.out.println(sql);

        try {
            con.setAutoCommit(false);
            statement = con.createStatement();
            statement.executeUpdate(sql);

            con.commit();
            return 1;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                con.setAutoCommit(false);
            } catch (SQLException e) {
                System.out.println(e);
            }
            UtilBD.closeStatement(statement);
            pila_con.liberarConexion(con);
        }
        return 0;
    }

}
