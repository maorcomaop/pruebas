/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 * * CREADO POR JAIR HERNANDO VIDAL
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Movil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.RelacionVehiculoConductor;
import com.registel.rdw.utils.StringUtils;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lider_desarrollador
 */
public class RelacionVehiculoConductorBD {

    public static boolean exist(RelacionVehiculoConductor c) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo (NULL");
        sql.append(", ");
        sql.append(c.getIdConductor());
        sql.append(", ");
        sql.append(c.getIdVehiculo());
        sql.append(", ");
        sql.append(" NULL, ");
        sql.append(" NULL, ");
        sql.append(c.getEstado());
        sql.append(", ");
        sql.append(" NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());
            rs.last();
            int row = rs.getRow();
            if (row > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    public static boolean updateRelationShip(RelacionVehiculoConductor c) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;
        ResultSet resultset = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo (");
        sql.append(c.getIdRelacionVehiculoConductor());
        sql.append(", ");
        sql.append(c.getIdConductor());
        sql.append(", ");
        sql.append(c.getIdVehiculo());
        sql.append(", ");
        sql.append(" NULL, 0, NULL, NULL, NULL, NULL, 5);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            resultset = createStatement.executeQuery(sql.toString());
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
            resultset.close();
            con.close();
        }
        return false;
    }

    public static int insert(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {

        if (exist(c)) {//pregunta si existe la relacion entre ese vehiculo y conductor                       
            return 0;
        } else {

            PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
            Connection con = pila_con.obtenerConexion();
           
            try {

                String sqlInsert = "INSERT INTO tbl_conductor_vehiculo (FK_CONDUCTOR, FK_VEHICULO, ACTIVO , ESTADO, FECHA_ASIGNACION)"
                        + "VALUES (" + c.getIdConductor() + ", " + c.getIdVehiculo() + ", 1, 1,'"+c.getFecha_asignacion()+"')";

                String sqlUpdate = "UPDATE tbl_conductor_vehiculo SET ACTIVO = 0 WHERE FK_VEHICULO = " + c.getIdVehiculo() + " AND ACTIVO = 1";
                Statement createStatement0 = con.createStatement();
                int return0 = createStatement0.executeUpdate(sqlUpdate);
                if (return0 >= 0) {
                    Statement createStatement1 = con.createStatement();
                    int return1 = createStatement1.executeUpdate(sqlInsert);
                    if (return1 >= 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            } finally {
                pila_con.liberarConexion(con);
            }
        }
        return -1;
    }

    public static RelacionVehiculoConductor selectByOne(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelacionVehiculoConductor relacionVehiculoConductorEncontrado = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo ( ");
        sql.append(c.getIdRelacionVehiculoConductor());
        sql.append(", NULL, NULL, NULL, NULL, 1, NULL, NULL ,NULL, NULL , 7);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());

            while (resultset.next()) {
                relacionVehiculoConductorEncontrado = new RelacionVehiculoConductor();
                relacionVehiculoConductorEncontrado.setIdRelacionVehiculoConductor(resultset.getInt("PK_CONDUCTOR_VECHICULO"));
                relacionVehiculoConductorEncontrado.setIdConductor(resultset.getInt("FK_CONDUCTOR"));
                relacionVehiculoConductorEncontrado.setIdVehiculo(resultset.getInt("FK_VEHICULO"));
                relacionVehiculoConductorEncontrado.setEstado(resultset.getInt("ESTADO"));
                relacionVehiculoConductorEncontrado.setActivo(resultset.getInt("ACTIVO"));
                relacionVehiculoConductorEncontrado.setNombreConductor(StringUtils.upperFirstLetter(resultset.getString("APELLIDO")) + " " + StringUtils.upperFirstLetter(resultset.getString("NOMBRE")));
            }
            return relacionVehiculoConductorEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();

        }
        return null;
    }

    public static boolean activeOnlyOneRelation(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelacionVehiculoConductor relacionVehiculoConductorEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo ( ");
        sql.append(c.getIdRelacionVehiculoConductor());
        sql.append(", NULL,");
        sql.append(c.getIdVehiculo()).append(",");
        sql.append(c.getActivo());
        sql.append(", NULL, NULL, 1, NULL ,NULL, NULL , 5);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            createStatement.executeQuery(sql.toString());
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();

        }
        return false;
    }

    public static boolean updateOneRelation(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo ( ");
        sql.append(c.getIdRelacionVehiculoConductor());
        sql.append(", NULL, NULL, NULL, NULL, ");
        sql.append(c.getEstado());
        sql.append(", 1, NULL ,NULL, NULL , 6);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            createStatement.executeQuery(sql.toString());
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();

        }
        return false;
    }

    public static List<RelacionVehiculoConductor> selectAll(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelacionVehiculoConductor a;
        List<RelacionVehiculoConductor> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo (NULL, NULL,");
        sql.append(c.getIdVehiculo());
        sql.append(", NULL, NULL, 1, NULL, NULL ,NULL, NULL , 8);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            while (resultset.next()) {
                a = new RelacionVehiculoConductor();
                a.setIdRelacionVehiculoConductor(resultset.getInt("PK_CONDUCTOR_VECHICULO"));
                a.setIdConductor(resultset.getInt("FK_CONDUCTOR"));
                a.setIdVehiculo(resultset.getInt("FK_VEHICULO"));
                a.setEstado(resultset.getInt("ESTADO"));
                a.setActivo(resultset.getInt("ACTIVO"));
                a.setNombreConductor(StringUtils.upperFirstLetter(resultset.getString("APELLIDO")) + " " + StringUtils.upperFirstLetter(resultset.getString("NOMBRE")));
                list.add(a);
            }
            return list;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();

        }
        return null;
    }

    public static RelacionVehiculoConductor selectByOneOne(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<RelacionVehiculoConductor> list = new ArrayList<>();
        RelacionVehiculoConductor a = new RelacionVehiculoConductor();

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo ( NULL, NULL, ").append(c.getIdVehiculo()).append(",1,NULL,1, NULL, NULL ,NULL, NULL , 3);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            if (resultset.next()) {
                a.setIdRelacionVehiculoConductor(resultset.getInt("PK_CONDUCTOR_VECHICULO"));
                a.setIdConductor(resultset.getInt("FK_CONDUCTOR"));
                a.setIdVehiculo(resultset.getInt("FK_VEHICULO"));
                a.setEstado(resultset.getInt("ESTADO"));
                a.setActivo(resultset.getInt("ACTIVO"));
            }
            return a;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();
        }
        return null;
    }

    public static RelacionVehiculoConductor selectByOneNew(RelacionVehiculoConductor c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        RelacionVehiculoConductor relacionVehiculoConductorEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor_vehiculo(NULL, NULL,");
        sql.append(c.getIdVehiculo());
        sql.append(" ,NULL, NULL, 1, NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            relacionVehiculoConductorEncontrado = new RelacionVehiculoConductor();
            if (resultset.next()) {
                relacionVehiculoConductorEncontrado.setIdRelacionVehiculoConductor(resultset.getInt("PK_CONDUCTOR_VECHICULO"));
                relacionVehiculoConductorEncontrado.setIdConductor(resultset.getInt("FK_CONDUCTOR"));
                relacionVehiculoConductorEncontrado.setIdVehiculo(resultset.getInt("FK_VEHICULO"));
                relacionVehiculoConductorEncontrado.setEstado(resultset.getInt("ESTADO"));
            }
            return relacionVehiculoConductorEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            con.close();
        }
        return null;
    }

}//FIN DE CLASE
