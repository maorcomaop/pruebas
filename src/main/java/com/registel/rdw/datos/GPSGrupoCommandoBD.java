/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.GpsEnvioComando;
import com.registel.rdw.logica.GpsGrupoComando;
import com.registel.rdw.logica.GpsPaqueteComando;
import com.registel.rdw.logica.Usuario;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class GPSGrupoCommandoBD {

    public static SimpleDateFormat formatDBDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static List<GpsEnvioComando> loadValueCommandSaved(String fkGpsCommandType, String fkGps, String plate) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<GpsEnvioComando> lst;

        String sqlList = "SELECT FK_GPS_COMANDO_ENVIO, FK_GPS_COMANDO_TIPO, FK_GPS, PLACA, RESPONDIDO, PARAMETROS, "
                + "RESPUESTA, FECHA_MODIFICACION, FECHA_CREACION FROM tbl_gps_comando_configurado "
                + "WHERE FK_GPS IN ('" + fkGps + "') AND RESPONDIDO = 1 AND FK_GPS_COMANDO_TIPO IN (" + fkGpsCommandType + ") ORDER BY PK_GPS_COMANDO_CONFIGURADO DESC";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();

            GpsEnvioComando c;

            while (rs.next()) {
                c = new GpsEnvioComando();
                c.setPkGpsCommandSend(rs.getInt("FK_GPS_COMANDO_ENVIO"));
                c.setCreationdDate(rs.getTimestamp("FECHA_CREACION"));
                c.setFkCommandKey(rs.getInt("FK_GPS_COMANDO_TIPO"));
                c.setFkGps(rs.getString("FK_GPS"));
                c.setParams(rs.getString("PARAMETROS"));
                c.setPlate(rs.getString("PLACA"));
                c.setReplied(rs.getBoolean("RESPONDIDO"));
                c.setResponse(rs.getString("RESPUESTA"));
                c.setUpdatedDate(rs.getTimestamp("FECHA_MODIFICACION"));

                lst.add(c);
            }

            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }

        return null;
    }

    public static List<GpsEnvioComando> listGpsCommandSendCurrentStatus(String dateToCheck) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<GpsEnvioComando> lst;

        String sqlList = "SELECT PK as PK_ENVIO_COMANDO , fk_command_group as FK_GRUPO_COMANDO_GPS, id_in_group as ID_EN_GRUPO, "
                + "			command_key as FK_GPS_COMANDO_TIPO, command_to_block as BLOQUE_MEMORIA, command_numerator as NUMERO_COMANDO, "
                + "			fk_gps as FK_GPS_cs , plate as PLACA , parameters as PARAMETROS, viewed as VISTO , sent as ENVIADO , "
                + "			tried as INTENTOS , replied as RESPONDIDO , cancelled as CANCELADO, "
                + "                     valid_timedate as FECHA_VALIDEZ, update_timedate as FECHA_MODIFICACION_cs , "
                + "			insert_timedate as FECHA_CREACION_cs , forward_timedate as FECHA_RENVIO , response as RESPUESTA "
                + "                     FROM tbl_gps_command_send WHERE update_timedate >= '" + dateToCheck + "' ORDER BY PK DESC";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();

            GpsEnvioComando c;

            while (rs.next()) {
                c = new GpsEnvioComando();
                c.setCancelled(rs.getBoolean("CANCELADO"));
                c.setCommandNumerator(rs.getInt("NUMERO_COMANDO"));
                c.setCommandToBlock(rs.getString("BLOQUE_MEMORIA"));
                c.setCreationdDate(rs.getTimestamp("FECHA_CREACION_cs"));
                c.setFkCommandGroup(rs.getInt("FK_GRUPO_COMANDO_GPS"));
                c.setFkCommandKey(rs.getInt("FK_GPS_COMANDO_TIPO"));
                c.setFkGps(rs.getString("FK_GPS_cs"));
                c.setForwardDate(rs.getTimestamp("FECHA_RENVIO"));
                c.setIdInGroup(rs.getInt("ID_EN_GRUPO"));
                c.setParams(rs.getString("PARAMETROS"));
                c.setPkGpsCommandSend(rs.getInt("PK_ENVIO_COMANDO"));
                c.setPlate(rs.getString("PLACA"));
                c.setReplied(rs.getBoolean("RESPONDIDO"));
                c.setResponse(rs.getString("RESPUESTA"));
                c.setSent(rs.getBoolean("ENVIADO"));
                c.setTried(rs.getInt("INTENTOS"));
                c.setUpdatedDate(rs.getTimestamp("FECHA_MODIFICACION_cs"));
                c.setValiddDate(rs.getTimestamp("FECHA_VALIDEZ"));
                c.setViewed(rs.getBoolean("VISTO"));

                lst.add(c);
            }

            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }

        return null;
    }

    public static List<GpsGrupoComando> listGpsCommandGroupCurrentStatus(String dateToCheck) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<GpsGrupoComando> lst;

        String sqlList = "SELECT PK_GRUPO_COMANDO_GPS, FK_GPS_PAQUETE_COMANDO , NUM_COMANDOS , FK_GRUPO_COMANDO_TIPO , FK_GPS ,"
                + "FK_TIPO_TABLA_1 , FK_TABLA_1 , FK_TIPO_TABLA_2 , FK_TABLA_2 , MAX_POSPUESTOS, NUM_POSPUESTOS, FK_ESTADO_EN_GPS , NECESITA_RESET ,"
                + "ESTADO , FECHA_MODIFICACION as FECHA_MODIFICACION_gc , FECHA_CREACION as FECHA_CREACION_gc "
                + "FROM tbl_gps_grupo_comando "
                + "WHERE PK_GRUPO_COMANDO_GPS != 1 AND ESTADO = 1 AND FECHA_MODIFICACION >= '" + dateToCheck + "' ORDER BY PK_GRUPO_COMANDO_GPS DESC";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();

            GpsGrupoComando c;

            while (rs.next()) {
                c = new GpsGrupoComando();

                c.setPkGpsCommandGroup(rs.getInt("PK_GRUPO_COMANDO_GPS"));
                c.setFkGpsCommandPackage(rs.getInt("FK_GPS_PAQUETE_COMANDO"));
                c.setFkCommandGroupType(rs.getInt("FK_GRUPO_COMANDO_TIPO"));
                c.setFkGPS(rs.getString("FK_GPS"));
                c.setFkStateInGps(rs.getInt("FK_ESTADO_EN_GPS"));
                c.setFkTable1(rs.getInt("FK_TABLA_1"));
                c.setFkTable2(rs.getInt("FK_TABLA_2"));
                c.setFkTableType1(rs.getInt("FK_TIPO_TABLA_1"));
                c.setFkTableType2(rs.getInt("FK_TIPO_TABLA_2"));
                c.setMaxPostponed(rs.getInt("MAX_POSPUESTOS"));
                c.setNumPostponed(rs.getInt("NUM_POSPUESTOS"));
                c.setNeedReset(rs.getBoolean("NECESITA_RESET"));
                c.setNumCommands(rs.getInt("NUM_COMANDOS"));
                c.setStatus(rs.getBoolean("ESTADO"));
                c.setUpdateDate(rs.getTimestamp("FECHA_MODIFICACION_gc"));
                c.setCreationDate(rs.getTimestamp("FECHA_CREACION_gc"));

                lst.add(c);
            }

            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }

        return null;
    }

    public static List<GpsPaqueteComando> listGpsCommandPackageInfo(Usuario user) {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        ResultSet rs = null;
        Statement createStatement = null;
        ArrayList<GpsPaqueteComando> lst;

        String sqlCommandPackage;
        if (user.esPropietario()) {
            sqlCommandPackage = "5 AND FK_USUARIO =" + user.getId();
        } else {
            sqlCommandPackage = "FK_GPS_PAQUETE_COMANDO_TIPO";
        }

        String sqlPlate;
        if (user.esPropietario()) {
            sqlPlate = "SELECT v.PLACA "
                    + "			 FROM "
                    + "				 ("
                    + "				 SELECT FK_VEHICULO FROM tbl_propietario_vehiculo WHERE fk_propietario = " + user.getId() + " AND activa = 1 AND estado = 1"
                    + "				 ) pv"
                    + "				 INNER JOIN"
                    + "				 ("
                    + "				 SELECT PK_VEHICULO , PLACA FROM tbl_vehiculo WHERE estado = 1 "
                    + "				 ) v"
                    + "			 ON pv.FK_VEHICULO = v.PK_VEHICULO";
        } else {
            sqlPlate = "PLACA";
        }

        String sqlList = "SELECT * "
                + "	FROM "
                + "	("
                + "	SELECT PK_GPS_PAQUETE_COMANDO , FK_GPS_PAQUETE_COMANDO_TIPO, CODIGO, INTENTOS, ESTADO_EN_GPS , "
                + "     ESTADO, FECHA_MODIFICACION as FECHA_MODIFICACION_pc , FECHA_CREACION as FECHA_CREACION_pc"
                + "		FROM tbl_gps_paquete_comando "
                + "			WHERE PK_GPS_PAQUETE_COMANDO != 1 AND ESTADO = 1 AND FK_GPS_PAQUETE_COMANDO_TIPO = " + sqlCommandPackage
                + "     ) as pc"
                + "     INNER JOIN"
                + "     ("
                + "	SELECT gc.* , gs.* "
                + "		FROM "
                + "		("
                + "		SELECT FK_GPS_PAQUETE_COMANDO , PK_GRUPO_COMANDO_GPS , NUM_COMANDOS , FK_GRUPO_COMANDO_TIPO , FK_GPS , PLACA as PLACA_gc , "
                + "				 FK_TIPO_TABLA_1 , FK_TABLA_1 , FK_TIPO_TABLA_2 , FK_TABLA_2 , FK_ESTADO_EN_GPS , NECESITA_RESET , "
                + "				 ESTADO , FECHA_MODIFICACION as FECHA_MODIFICACION_gc , FECHA_CREACION as FECHA_CREACION_gc, MAX_POSPUESTOS , NUM_POSPUESTOS   "
                + "			FROM tbl_gps_grupo_comando"
                + "				WHERE PK_GRUPO_COMANDO_GPS != 1 AND ESTADO = 1 AND PLACA IN ("
                + sqlPlate + ")"
                + "		) as gc"
                + "		INNER JOIN"
                + "		("
                + "		SELECT PK as PK_ENVIO_COMANDO , fk_command_group as FK_GRUPO_COMANDO_GPS, id_in_group as ID_EN_GRUPO, command_key as FK_GPS_COMANDO_TIPO, "
                + "				 command_to_block as BLOQUE_MEMORIA, command_numerator as NUMERO_COMANDO, fk_gps as FK_GPS_cs , plate as PLACA , parameters as PARAMETROS, "
                + "                              viewed as VISTO , sent as ENVIADO , tried as INTENTOS , replied as RESPONDIDO , cancelled as CANCELADO,  "
                + "                              valid_timedate as FECHA_VALIDEZ, "
                + "                              update_timedate as FECHA_MODIFICACION_cs , insert_timedate as FECHA_CREACION_cs , forward_timedate as FECHA_RENVIO , response as RESPUESTA "
                + "			FROM tbl_gps_command_send WHERE valid_timedate >= now() "
                + "		) as gs"
                + "		ON gc.PK_GRUPO_COMANDO_GPS = gs.FK_GRUPO_COMANDO_GPS	"
                + "	) as gcs"
                + "	ON pc.PK_GPS_PAQUETE_COMANDO = gcs.FK_GPS_PAQUETE_COMANDO"
                + "		ORDER BY pc.PK_GPS_PAQUETE_COMANDO DESC , gcs.PK_GRUPO_COMANDO_GPS DESC , gcs.PK_ENVIO_COMANDO DESC";

        try {
            createStatement = con.createStatement();
            rs = createStatement.executeQuery(sqlList);

            lst = new ArrayList<>();

            Map<Integer, Integer> mPkPackage = new HashMap<>();
            Map<Integer, Integer> mPkGroup = new HashMap<>();
            Map<Integer, Integer> mPkCommand = new HashMap<>();

            GpsPaqueteComando a = new GpsPaqueteComando();
            GpsGrupoComando b = new GpsGrupoComando();
            GpsEnvioComando c = new GpsEnvioComando();

            int iPkPackage;
            int iPkGroup;
            int ipkCommand;

            while (rs.next()) {
                iPkPackage = rs.getInt("PK_GPS_PAQUETE_COMANDO");
                if (mPkPackage.containsKey(iPkPackage)) {

                } else {
                    mPkPackage.put(iPkPackage, iPkPackage);

                    a = new GpsPaqueteComando();

                    a.setPkGpsCommandPackage(iPkPackage);
                    a.setFkGPSCommandPackageType(rs.getInt("FK_GPS_PAQUETE_COMANDO_TIPO"));
                    a.setCode(rs.getString("CODIGO"));
                    a.setTries(rs.getInt("INTENTOS"));
                    a.setStateInGPSs(rs.getInt("ESTADO_EN_GPS"));
                    a.setStatus(rs.getBoolean("ESTADO"));
                    a.setUpdateDate(rs.getTimestamp("FECHA_MODIFICACION_pc"));
                    a.setCreationDate(rs.getTimestamp("FECHA_CREACION_pc"));

                    a.setGpsCommandGroupList(new ArrayList<GpsGrupoComando>());

                    lst.add(a);
                }

                iPkGroup = rs.getInt("PK_GRUPO_COMANDO_GPS");

                if (mPkGroup.containsKey(iPkGroup)) {

                } else {
                    mPkGroup.put(iPkGroup, iPkGroup);

                    b = new GpsGrupoComando();

                    b.setPkGpsCommandGroup(iPkGroup);
                    b.setFkGpsCommandPackage(rs.getInt("FK_GPS_PAQUETE_COMANDO"));
                    b.setFkCommandGroupType(rs.getInt("FK_GRUPO_COMANDO_TIPO"));
                    b.setFkGPS(rs.getString("FK_GPS"));
                    b.setPlate(rs.getString("PLACA_gc"));
                    b.setMaxPostponed(rs.getInt("MAX_POSPUESTOS"));
                    b.setNumPostponed(rs.getInt("NUM_POSPUESTOS"));
                    b.setFkStateInGps(rs.getInt("FK_ESTADO_EN_GPS"));
                    b.setFkTable1(rs.getInt("FK_TABLA_1"));
                    b.setFkTable2(rs.getInt("FK_TABLA_2"));
                    b.setFkTableType1(rs.getInt("FK_TIPO_TABLA_1"));
                    b.setFkTableType2(rs.getInt("FK_TIPO_TABLA_2"));
                    b.setNeedReset(rs.getBoolean("NECESITA_RESET"));
                    b.setNumCommands(rs.getInt("NUM_COMANDOS"));
                    b.setStatus(rs.getBoolean("ESTADO"));
                    b.setUpdateDate(rs.getTimestamp("FECHA_MODIFICACION_gc"));
                    b.setCreationDate(rs.getTimestamp("FECHA_CREACION_gc"));

                    b.setGpsCommandSendList(new ArrayList<GpsEnvioComando>());

                    a.getGpsCommandGroupList().add(b);
                }

                ipkCommand = rs.getInt("PK_ENVIO_COMANDO");
                if (mPkCommand.containsKey(ipkCommand)) {
                } else {
                    mPkCommand.put(ipkCommand, ipkCommand);

                    c = new GpsEnvioComando();
                    c.setCancelled(rs.getBoolean("CANCELADO"));
                    c.setCommandNumerator(rs.getInt("NUMERO_COMANDO"));
                    c.setCommandToBlock(rs.getString("BLOQUE_MEMORIA"));
                    c.setCreationdDate(rs.getTimestamp("FECHA_CREACION_cs"));
                    c.setFkCommandGroup(rs.getInt("FK_GRUPO_COMANDO_GPS"));
                    c.setFkCommandKey(rs.getInt("FK_GPS_COMANDO_TIPO"));
                    c.setFkGps(rs.getString("FK_GPS_cs"));
                    c.setForwardDate(rs.getTimestamp("FECHA_RENVIO"));
                    c.setIdInGroup(rs.getInt("ID_EN_GRUPO"));
                    c.setParams(rs.getString("PARAMETROS"));
                    c.setPkGpsCommandSend(ipkCommand);
                    c.setPlate(rs.getString("PLACA"));
                    c.setReplied(rs.getBoolean("RESPONDIDO"));
                    c.setResponse(rs.getString("RESPUESTA"));
                    c.setSent(rs.getBoolean("ENVIADO"));
                    c.setTried(rs.getInt("INTENTOS"));
                    c.setUpdatedDate(rs.getTimestamp("FECHA_MODIFICACION_cs"));
                    c.setValiddDate(rs.getTimestamp("FECHA_VALIDEZ"));
                    c.setViewed(rs.getBoolean("VISTO"));

                    b.getGpsCommandSendList().add(c);
                }
            }

            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(createStatement);
            pila_con.liberarConexion(con);
        }

        return null;
    }

    public static GpsPaqueteComando saveGpsCommandGroups(GpsPaqueteComando gpsPaqueteComando) throws SQLException {
        PilaConexiones stackCon = PilaConexiones.obtenerInstancia();
        Connection con = stackCon.obtenerConexion();
        Statement statement = null;

        String params = "";
        GpsPaqueteComando mResponse = null;
        try {

            String insertCommandGroups = "INSERT INTO tbl_gps_paquete_comando "
                    + "("
                    + "FK_GPS_PAQUETE_COMANDO_TIPO,CODIGO,INTENTOS,FK_USUARIO,ESTADO_EN_GPS"
                    + ")"
                    + "VALUES"
                    + "("
                    + gpsPaqueteComando.getFkGPSCommandPackageType() + ",'" + gpsPaqueteComando.getCode() + "'," + gpsPaqueteComando.getTries() + "," + gpsPaqueteComando.getFkUsuario() + ",1"
                    + ")";

            con.setAutoCommit(false);
            statement = con.createStatement();
            int resultA = statement.executeUpdate(insertCommandGroups);

            if (resultA > 0) {
                int lastIdA = 0;
                Date creationDate = null;
                String lastIdQueryA = "SELECT PK_GPS_PAQUETE_COMANDO as max_id , FECHA_CREACION FROM tbl_gps_paquete_comando WHERE PK_GPS_PAQUETE_COMANDO = (SELECT MAX(PK_GPS_PAQUETE_COMANDO) FROM tbl_gps_paquete_comando)";
                ResultSet rA = statement.executeQuery(lastIdQueryA);
                while (rA.next()) {
                    lastIdA = rA.getInt("max_id");
                    creationDate = rA.getTimestamp("FECHA_CREACION");
                }
                if (lastIdA > 0) {
                    gpsPaqueteComando.setPkGpsCommandPackage(lastIdA);
                    gpsPaqueteComando.setCreationDate(creationDate);

                    for (GpsGrupoComando gpsGrupoComando : gpsPaqueteComando.getGpsCommandGroupList()) {

                        String insertCommaGroup = "INSERT INTO tbl_gps_grupo_comando "
                                + "("
                                + "FK_GPS_PAQUETE_COMANDO, NUM_COMANDOS,FK_GRUPO_COMANDO_TIPO,MAX_POSPUESTOS,FK_GPS,PLACA,NECESITA_RESET,ESTADO"
                                + ")"
                                + "VALUES"
                                + "("
                                + "" + gpsPaqueteComando.getPkGpsCommandPackage() + "," + gpsGrupoComando.getNumCommands() + "," + gpsGrupoComando.getFkCommandGroupType() + "," + gpsGrupoComando.getMaxPostponed() + ",'" + gpsGrupoComando.getFkGPS() + "','" + gpsGrupoComando.getPlate() + "'," + gpsGrupoComando.isNeedReset() + ",1"
                                + ")";

                        int result0 = statement.executeUpdate(insertCommaGroup);

                        if (result0 > 0) {

                            int lastId0 = 0;
                            String lastIdQuery0 = "SELECT MAX(PK_GRUPO_COMANDO_GPS) as max_id FROM tbl_gps_grupo_comando";
                            ResultSet r0 = statement.executeQuery(lastIdQuery0);
                            while (r0.next()) {
                                lastId0 = r0.getInt("max_id");
                            }
                            if (lastId0 > 0) {
                                gpsGrupoComando.setPkGpsCommandGroup(lastId0);
                                for (GpsEnvioComando gpsEnvioComando : gpsGrupoComando.getGpsCommandSendList()) {
                                    String insertCommandQuery = "INSERT INTO tbl_gps_command_send "
                                            + "("
                                            + "fk_command_group,id_in_group,command_key,fk_gps,plate,parameters,valid_timedate"
                                            + ")"
                                            + "VALUES"
                                            + "("
                                            + gpsGrupoComando.getPkGpsCommandGroup() + "," + gpsEnvioComando.getIdInGroup() + "," + gpsEnvioComando.getFkCommandKey() + ",'" + gpsEnvioComando.getFkGps() + "','" + gpsEnvioComando.getPlate() + "','" + gpsEnvioComando.getParams() + "','" + formatDBDate.format(gpsEnvioComando.getValiddDate()) + "'"
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
                                            gpsEnvioComando.setPkGpsCommandSend(lastId1);
                                        } else {
                                            con.rollback();
                                        }

                                    } else {
                                        con.rollback();
                                    }
                                }
                                con.commit();
                            }
                        } else {
                            con.rollback();
                        }
                    }
                } else {
                    con.rollback();
                }
            } else {
                con.rollback();
            }
            return gpsPaqueteComando;
        } catch (SQLException ex) {
            con.rollback();
            System.err.println(ex);
            gpsPaqueteComando = null;
        } finally {
            con.setAutoCommit(true);
            UtilBD.closeStatement(statement);
            stackCon.liberarConexion(con);
        }
        return mResponse;
    }

    public static boolean resetGpsCommandGroups(String pkGpsCommandGroup) throws SQLException {
        PilaConexiones stackCon = PilaConexiones.obtenerInstancia();
        Connection con = stackCon.obtenerConexion();
        Statement statement = null;

        try {

            String updateCommandGroups = "UPDATE tbl_gps_grupo_comando SET FK_ESTADO_EN_GPS = 1 ,NUM_POSPUESTOS = 0 "
                    + "WHERE PK_GRUPO_COMANDO_GPS = " + pkGpsCommandGroup;

            String updateCommandSend = "UPDATE tbl_gps_command_send SET viewed = 0 , sent = 0 , tried = 0, replied = 0, cancelled = 0, response = NULL , "
                    + "forward_timedate = now() "
                    + "WHERE fk_command_group = " + pkGpsCommandGroup;

            statement = con.createStatement();
            statement.executeUpdate(updateCommandGroups);
            statement.executeUpdate(updateCommandSend);

            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeStatement(statement);
            stackCon.liberarConexion(con);
        }
        return false;
    }

}
