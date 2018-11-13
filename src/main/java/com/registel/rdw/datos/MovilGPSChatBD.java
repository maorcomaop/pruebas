/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.MovilGPSChat;
import com.registel.rdw.utils.Restriction;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lider_desarrollador
 */
public class MovilGPSChatBD {

    public static SimpleDateFormat formatDBDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean movilGPSChatUpdateAllViewed(String plate) {

        PilaConexiones stackCon = PilaConexiones.obtenerInstancia();
        Connection con = stackCon.obtenerConexion();
        Statement statement = null;

        try {
            statement = con.createStatement();
            String updateSql = "UPDATE tbl_gps_chat SET VISTO = 1 WHERE PLACA = '" + plate + "'AND VISTO = 0";
            int result = statement.executeUpdate(updateSql);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MovilGPSChatBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            UtilBD.closeStatement(statement);
            stackCon.liberarConexion(con);
        }
        return false;

    }

    public static MovilGPSChat movilGPSChatSave(MovilGPSChat m) throws SQLException {
        PilaConexiones stackCon = PilaConexiones.obtenerInstancia();
        Connection con = stackCon.obtenerConexion();
        Statement statement = null;

        String params = "";
        MovilGPSChat mResponse = null;
        try {

            String insertCommaGroup = "INSERT INTO tbl_gps_grupo_comando "
                    + "("
                    + "FK_GRUPO_COMANDO_TIPO,FK_TIPO_TABLA_1,FK_GPS,PLACA,ESTADO"
                    + ")"
                    + "VALUES"
                    + "("
                    + "3,3,'" + m.getFkGPS() + "','" + m.getPlate() + "',1"
                    + ")";

            con.setAutoCommit(false);
            statement = con.createStatement();
            int result0 = statement.executeUpdate(insertCommaGroup);
            if (result0 > 0) {
                int lastId0 = 0;
                String lastIdQuery0 = "SELECT MAX(PK_GRUPO_COMANDO_GPS) as max_id FROM tbl_gps_grupo_comando";
                ResultSet r0 = statement.executeQuery(lastIdQuery0);
                while (r0.next()) {
                    lastId0 = r0.getInt("max_id");
                }

                if (lastId0 > 0) {
                    m.setFkCommandGroup(lastId0);

                    JSONObject json = new JSONObject();
                    try {
                        json.put("payload", "<MSG,1,1," + m.getFkCommandGroup() + "," + m.getMessage() + ">");
                        params = json.toString(2);
                    } catch (JSONException ex) {
                        Logger.getLogger(MovilGPSChatBD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String insertCommandQuery = "INSERT INTO tbl_gps_command_send "
                            + "("
                            + "fk_command_group,id_in_group,command_key,fk_gps,plate,parameters,valid_timedate"
                            + ")"
                            + "VALUES"
                            + "("
                            + m.getFkCommandGroup() + ",1,4,'" + m.getFkGPS() + "','" + m.getPlate() + "','" + params + "','" + formatDBDate.format(new Date((((new Date()).getTime()) + 86400000))) + "'"
                            + ")";

                    int result1 = statement.executeUpdate(insertCommandQuery);
                    if (result1 > 0) {

                        int lastId1 = 0;
                        String lastIdQuery1 = "SELECT MAX(PK) as max_id FROM tbl_gps_command_send";
                        ResultSet r1 = statement.executeQuery(lastIdQuery1);
                        while (r1.next()) {
                            lastId1 = r1.getInt("max_id");
                        }
                        if (lastId1 > 0) {
                            m.setFkGPSCommandSend(lastId1);

                            String insertGPSChat = "INSERT INTO tbl_gps_chat "
                                    + "("
                                    + "FK_GPS_TIPO_MENSAJE,FK_ORIGEN,FK_GPS,PLACA,MENSAJE,FK_GPS_ENVIO_COMANDO,NUM_NOTICIAS"
                                    + ") "
                                    + "VALUES "
                                    + "("
                                    + m.getFkGPSMessageType() + "," + m.getFkOrigin() + ",'" + m.getFkGPS() + "','" + m.getPlate() + "','" + m.getMessage() + "'," + m.getFkGPSCommandSend() + "," + m.getNumNews()
                                    + ")";

                            int result2 = statement.executeUpdate(insertGPSChat);
                            if (result2 > 0) {

                                int lastId2 = 0;
                                String lastIdQuery2 = "SELECT MAX(PK_GPS_CHAT) as max_id FROM tbl_gps_chat";
                                ResultSet r2 = statement.executeQuery(lastIdQuery2);
                                while (r2.next()) {
                                    lastId2 = r2.getInt("max_id");
                                }
                                if (lastId2 > 0) {
                                    String updateGPSCommandGroup = "UPDATE tbl_gps_grupo_comando SET FK_TABLA_1 = " + lastId2 + " WHERE PK_GRUPO_COMANDO_GPS =" + lastId0;
                                    int result3 = statement.executeUpdate(updateGPSCommandGroup);
                                    if (result3 > 0) {
                                        mResponse = movilGPSChatOne(lastId2, con);
                                        if (mResponse != null) {
                                            con.commit();
                                            return mResponse;
                                        } else {
                                            con.rollback();
                                        }
                                    } else {
                                        con.rollback();
                                    }
                                }
                            } else {
                                con.rollback();
                            }
                        }
                    } else {
                        con.rollback();
                    }
                }
            } else {
                con.rollback();
            }
        } catch (SQLException ex) {
            con.rollback();
            System.err.println(ex);
        } finally {
            con.setAutoCommit(true);
            UtilBD.closeStatement(statement);
            stackCon.liberarConexion(con);
        }
        return mResponse;
    }

    public static List<MovilGPSChat> movilGPSChatList() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<MovilGPSChat> lst = null;
        String sqlList = "SELECT t4.* , t5.NO_VISTOS FROM "
                + "	("
                + "	SELECT t2.* , t3.*"
                + "		FROM 		"
                + "			(			"
                + "			SELECT t0.* , t1.* ,  "
                + "				CASE WHEN t1.VISTO IS NULL THEN 1000 ELSE t1.VISTO END AS AUX_VISTO , "
                + "				CASE WHEN t1.FK_ORIGEN IS NULL THEN 1000 ELSE t1.FK_ORIGEN END AS AUX_FK_ORIGEN  		 			"
                + "				FROM 					"
                + "					(					"
                + "					SELECT tcc.NUM_INTERNO , tcc.PK_VEHICULO , tcc.PLACA , tdd.FK_GPS FROM"
                + "				("
                + "				SELECT taa.fk_gps as FK_GPS, taa.fk_vehiculo as FK_VEHICULO "
                + "					FROM tbl_gps_hardware as taa "
                + "				 		INNER JOIN "
                + "					 		tbl_gps_inventario as tbb "
                + "					 		ON taa.fk_gps = tbb.id WHERE taa.fk_hardware IN (2, 5) AND taa.estado = 1 AND tbb.estado = 1"
                + "				) as tdd "
                + "                             INNER JOIN"
                + "                             tbl_vehiculo as tcc"
                + "                             ON tdd.fk_vehiculo = tcc.PK_VEHICULO WHERE tcc.ESTADO = 1				"
                + "					)  as t0				"
                + "					LEFT JOIN 				"
                + "					(					"
                + "					SELECT PK_GPS_CHAT,  FK_GPS_TIPO_MENSAJE, FK_ORIGEN,  PLACA as _PLACA , VISTO, MENSAJE, FK_GPS_ENVIO_COMANDO, FECHA_MODIFICACION, FECHA_CREACION FROM tbl_gps_chat 					"
                + "					WHERE PK_GPS_CHAT IN (SELECT MAX(PK_GPS_CHAT) FROM tbl_gps_chat GROUP BY PLACA)				"
                + "					)  as t1				"
                + "					ON t0.PLACA = t1._PLACA     		"
                + "			) as t2		"
                + "		LEFT JOIN 		"
                + "		(			"
                + "		SELECT PK, fk_command_group, sent, replied FROM tbl_gps_command_send 		"
                + "		) as t3		"
                + "		ON t2.FK_GPS_ENVIO_COMANDO = t3.PK ORDER BY t2.FECHA_MODIFICACION  DESC , t2.AUX_FK_ORIGEN ASC, t2.AUX_VISTO ASC     		"
                //                + "		ON t2.FK_GPS_ENVIO_COMANDO = t3.PK ORDER BY t2.AUX_FK_ORIGEN , t2.AUX_VISTO ASC, t2.FECHA_CREACION ASC    		"
                + "	) as t4"
                + "	LEFT JOIN "
                + "	(		"
                + "	SELECT PLACA as PLACA_t5 , FK_ORIGEN as FK_ORIGEN_t5 ,  COUNT(*) as NO_VISTOS FROM tbl_gps_chat WHERE VISTO = 0 AND FK_ORIGEN = 1 GROUP BY PLACA"
                + "	) as t5"
                + "	ON t4.PLACA = t5.PLACA_t5";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();
            while (rs.next()) {
                MovilGPSChat a = new MovilGPSChat();
                a.setPkVehiculo(rs.getInt("PK_VEHICULO"));
                a.setPlate(rs.getString("PLACA"));
                a.setInternalNum(rs.getString("NUM_INTERNO"));
                a.setPkGPSChat(rs.getInt("PK_GPS_CHAT"));
                a.setFkGPSMessageType(rs.getInt("FK_GPS_TIPO_MENSAJE"));
                a.setFkOrigin(rs.getInt("FK_ORIGEN"));
                a.setFkGPS(rs.getString("FK_GPS"));
                a.setViewed(rs.getBoolean("VISTO"));
                a.setNumNotViewed(rs.getInt("NO_VISTOS"));
                a.setMessage(rs.getString("MENSAJE"));
                a.setFkGPSCommandSend(rs.getInt("FK_GPS_ENVIO_COMANDO"));
                a.setCreationDate(rs.getTimestamp("FECHA_CREACION"));
                lst.add(a);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }
        return lst;
    }

    public static List<MovilGPSChat> movilGPSChatOneList(String plate) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<MovilGPSChat> lst = null;
        String sqlList = "SELECT t2.* , t3.*"
                + "		FROM "
                + "		("
                + "			SELECT t0.* , t1.* "
                + "				FROM 	"
                + "				("
                + "				SELECT tcc.NUM_INTERNO , tcc.PK_VEHICULO , tcc.PLACA , tdd.FK_GPS FROM"
                + "				("
                + "				SELECT taa.fk_gps as FK_GPS, taa.fk_vehiculo as FK_VEHICULO "
                + "					FROM tbl_gps_hardware as taa "
                + "				 		INNER JOIN "
                + "					 		tbl_gps_inventario as tbb "
                + "					 		ON taa.fk_gps = tbb.id WHERE taa.fk_hardware IN (2, 5) AND taa.estado = 1 AND tbb.estado = 1"
                + "				) as tdd "
                + "                             INNER JOIN"
                + "                             tbl_vehiculo as tcc"
                + "                             ON tdd.fk_vehiculo = tcc.PK_VEHICULO WHERE tcc.ESTADO = 1				"
                + "				)  as t0"
                + "				INNER JOIN "
                + "				("
                + "					SELECT PK_GPS_CHAT,  FK_GPS_TIPO_MENSAJE, FK_ORIGEN,  PLACA as _PLACA , VISTO, MENSAJE, FK_GPS_ENVIO_COMANDO, FECHA_CREACION FROM tbl_gps_chat "
                + "					WHERE PLACA = '" + plate + "' ORDER BY PK_GPS_CHAT DESC LIMIT 100"
                + "				)  as t1"
                + "				ON t0.PLACA = t1._PLACA   "
                + "		) as t2"
                + "		LEFT JOIN "
                + "		("
                + "			SELECT PK, fk_command_group, viewed, sent, replied, tried, forward_timedate FROM tbl_gps_command_send "
                + "		) as t3"
                + "		ON t2.FK_GPS_ENVIO_COMANDO = t3.PK ORDER BY t2.PK_GPS_CHAT ASC";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();
            while (rs.next()) {
                MovilGPSChat a = new MovilGPSChat();
                a.setPkVehiculo(rs.getInt("PK_VEHICULO"));
                a.setPlate(rs.getString("PLACA"));
                a.setInternalNum(rs.getString("NUM_INTERNO"));
                a.setPkGPSChat(rs.getInt("PK_GPS_CHAT"));
                a.setFkGPSMessageType(rs.getInt("FK_GPS_TIPO_MENSAJE"));
                a.setFkOrigin(rs.getInt("FK_ORIGEN"));
                a.setFkGPS(rs.getString("FK_GPS"));
                a.setViewed(rs.getBoolean("VISTO"));
                a.setMessage(rs.getString("MENSAJE"));
                a.setFkGPSCommandSend(rs.getInt("FK_GPS_ENVIO_COMANDO"));
                a.setCreationDate(rs.getTimestamp("FECHA_CREACION"));
                a.setProcessed(rs.getBoolean("viewed"));
                a.setSent(rs.getBoolean("sent"));
                a.setReplied(rs.getBoolean("replied"));
                a.setTries(rs.getInt("tried"));
                lst.add(a);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }
        return lst;
    }

    public static List<MovilGPSChat> movilGPSChatOneControllerList() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<MovilGPSChat> lst = null;
        String sqlList = "SELECT * FROM tbl_gps_chat WHERE FK_GPS_TIPO_MENSAJE = 101 AND NUM_NOTICIAS > 0 AND ESTADO = 1 GROUP BY FK_GPS_TIPO_MENSAJE, NUM_NOTICIAS";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();
            while (rs.next()) {
                MovilGPSChat a = new MovilGPSChat();
                a.setPlate("controller");
                a.setNumNews(rs.getInt("NUM_NOTICIAS"));
                a.setPkGPSChat(rs.getInt("PK_GPS_CHAT"));
                a.setFkGPSMessageType(rs.getInt("FK_GPS_TIPO_MENSAJE"));
                a.setFkOrigin(rs.getInt("FK_ORIGEN"));
                a.setFkGPS(rs.getString("FK_GPS"));
                a.setViewed(rs.getBoolean("VISTO"));
                a.setMessage(rs.getString("MENSAJE"));
                a.setFkGPSCommandSend(rs.getInt("FK_GPS_ENVIO_COMANDO"));
                a.setCreationDate(rs.getTimestamp("FECHA_CREACION"));

                lst.add(a);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }
        return lst;
    }

    public static List<MovilGPSChat> movilGPSChatLoadCurrent(String plate, String lastCheckDate) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<MovilGPSChat> lst = null;

        String sqlList = "SELECT t3.* , t5.* FROM "
                + "		("
                + "		SELECT t0.* ,t1.*	FROM		"
                + "			(			"
                + "			SELECT FK_ENVIO_COMANDO_GPS , PLACA, FK_GPS_CHAT, FK_GPS, EDICION, PROCESADO_NEW, 			"
                + "			ENVIADO_NEW  , INTENTOS_NEW , RESPUESTA_NEW , FECHA_REENVIO_NEW,			FECHA_CREACION				"
                + "				FROM tbl_gps_envio_comando_log 					"
                + "				WHERE COMANDO = 4  					"
                + "				AND ((EDICION = 1 AND PLACA = '" + plate + "') OR (EDICION = 0))										"
                + "				AND FECHA_CREACION >= '" + lastCheckDate + "' 		"
                + "			) as t0 		"
                + "			INNER JOIN 		"
                + "			(			"
                + "				SELECT FK_GPS_TIPO_MENSAJE, FK_ORIGEN, MENSAJE, FK_GPS_ENVIO_COMANDO, VISTO , PK_GPS_CHAT                      "
                + "				FROM tbl_gps_chat WHERE ESTADO = 1		"
                + "			) as t1 		"
                + "			ON t0.FK_GPS_CHAT = t1.PK_GPS_CHAT ORDER BY t0.FECHA_CREACION ASC"
                + "                     ) as t3"
                + "			LEFT JOIN "
                + "			(		"
                + "			SELECT PLACA as PLACA_t5 , FK_ORIGEN as FK_ORIGEN_t5 ,  COUNT(*) as NO_VISTOS FROM tbl_gps_chat WHERE VISTO = 0 AND FK_ORIGEN = 1 GROUP BY PLACA"
                + "			) as t5"
                + "			ON t3.PLACA = t5.PLACA_t5";

        try {

            con.setAutoCommit(false);
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);
            con.commit();

            lst = new ArrayList<>();
            while (rs.next()) {
                MovilGPSChat a = new MovilGPSChat();
//                a.setPkVehiculo(rs.getInt("PK_VEHICULO"));
                a.setPlate(rs.getString("PLACA"));
//                a.setInternalNum(rs.getString("NUM_INTERNO"));
                a.setPkGPSChat(rs.getInt("PK_GPS_CHAT"));
                a.setFkGPSMessageType(rs.getInt("FK_GPS_TIPO_MENSAJE"));
                a.setFkOrigin(rs.getInt("FK_ORIGEN"));
                a.setFkGPS(rs.getString("FK_GPS"));
                a.setViewed(rs.getBoolean("VISTO"));
                a.setNumNotViewed(rs.getInt("NO_VISTOS"));
                a.setEdition(rs.getBoolean("EDICION"));
                a.setMessage(rs.getString("MENSAJE"));
                a.setFkGPSCommandSend(rs.getInt("FK_GPS_ENVIO_COMANDO"));
                a.setCreationDate(rs.getTimestamp("FECHA_CREACION"));
                a.setForwardDate(rs.getTimestamp("FECHA_REENVIO_NEW"));

                a.setProcessed(rs.getBoolean("PROCESADO_NEW"));
                a.setSent(rs.getBoolean("ENVIADO_NEW"));
                a.setReplied(rs.getBoolean("RESPUESTA_NEW"));
                lst.add(a);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }
        return lst;
    }

    public static MovilGPSChat movilGPSChatOne(Integer id) {
        return movilGPSChatOne(id, null);
    }

    public static MovilGPSChat movilGPSChatOne(Integer id, Connection conAux) {
        PilaConexiones stackCon = null;
        Connection con;
        if (conAux == null) {
            stackCon = PilaConexiones.obtenerInstancia();
            con = stackCon.obtenerConexion();
        } else {
            con = conAux;
        }

        ResultSet rs = null;
        Statement createStatement = null;
        MovilGPSChat a = null;
        String sqlList = "SELECT t2.* , t3.*"
                + "		FROM "
                + "		("
                + "			SELECT t0.* , t1.* "
                + "				FROM 	"
                + "				("
                + "				SELECT tcc.NUM_INTERNO , tcc.PK_VEHICULO , tcc.PLACA , tdd.FK_GPS FROM"
                + "				("
                + "				SELECT taa.fk_gps as FK_GPS, taa.fk_vehiculo as FK_VEHICULO "
                + "					FROM tbl_gps_hardware as taa "
                + "				 		INNER JOIN "
                + "					 		tbl_gps_inventario as tbb "
                + "					 		ON taa.fk_gps = tbb.id WHERE taa.fk_hardware IN (2, 5) AND taa.estado = 1 AND tbb.estado = 1"
                + "				) as tdd "
                + "                             INNER JOIN"
                + "                             tbl_vehiculo as tcc"
                + "                             ON tdd.fk_vehiculo = tcc.PK_VEHICULO WHERE tcc.ESTADO = 1"
                + "				)  as t0"
                + "				INNER JOIN "
                + "				("
                + "					SELECT PK_GPS_CHAT,  FK_GPS_TIPO_MENSAJE, FK_ORIGEN,  PLACA as _PLACA , VISTO, MENSAJE, FK_GPS_ENVIO_COMANDO, FECHA_CREACION FROM tbl_gps_chat "
                + "					WHERE PK_GPS_CHAT = " + id
                + "				)  as t1"
                + "				ON t0.PLACA = t1._PLACA   "
                + "		) as t2"
                + "		LEFT JOIN "
                + "		("
                + "			SELECT PK, fk_command_group, viewed, sent, replied, forward_timedate FROM tbl_gps_command_send "
                + "		) as t3"
                + "		ON t2.FK_GPS_ENVIO_COMANDO = t3.PK";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            while (rs.next()) {
                a = new MovilGPSChat();
                a.setPkVehiculo(rs.getInt("PK_VEHICULO"));
                a.setPlate(rs.getString("PLACA"));
                a.setInternalNum(rs.getString("NUM_INTERNO"));
                a.setPkGPSChat(rs.getInt("PK_GPS_CHAT"));
                a.setFkGPSMessageType(rs.getInt("FK_GPS_TIPO_MENSAJE"));
                a.setFkOrigin(rs.getInt("FK_ORIGEN"));
                a.setFkGPS(rs.getString("FK_GPS"));
                a.setViewed(rs.getBoolean("VISTO"));
                a.setMessage(rs.getString("MENSAJE"));
                a.setFkGPSCommandSend(rs.getInt("FK_GPS_ENVIO_COMANDO"));
                a.setCreationDate(rs.getTimestamp("FECHA_CREACION"));
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            if (conAux == null) {
                stackCon.liberarConexion(con);
            }
        }
        return a;
    }

    public static Integer maxNumNewsChat() {
        PilaConexiones stackCon = PilaConexiones.obtenerInstancia();
        Connection con = stackCon.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        String sqlMax = "SELECT MAX(NUM_NOTICIAS) AS max_news FROM tbl_gps_chat WHERE FK_GPS_TIPO_MENSAJE = 101 AND ESTADO = 1";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlMax);
            Integer numNews = 0;
            while (rs.next()) {
                numNews = rs.getInt("max_news");
            }
            return numNews;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            stackCon.liberarConexion(con);
        }
        return null;
    }

}
