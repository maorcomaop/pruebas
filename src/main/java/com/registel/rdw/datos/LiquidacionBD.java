/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.AuditoriaLiquidacionGeneral;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Liquidacion;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.LiquidacionGeneral;
import com.registel.rdw.logica.Tarifa;
import com.registel.rdw.logica.Vuelta;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.StringUtils;
import java.io.File;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author lider_desarrollador
 */
public class LiquidacionBD {

    public static boolean exist(Liquidacion c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT c.PK_CONDUCTOR FROM tbl_conductor as c WHERE c.PK_CONDUCTOR = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
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

    public static boolean existLq(LiquidacionGeneral c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PK_LIQUIDACION_GENERAL FROM tbl_liquidacion_general WHERE (PK_LIQUIDACION_GENERAL=?) AND (ESTADO=1)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            if (rs.getRow() > 0) {
                return true;
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e);
            }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    /*METODO QUE ASIGNA LIQUIDACION POR FRANJAS*/
    public static boolean asignarFranjas(String path, String franjas) {

        Entorno entorno = new Entorno(path);
        entorno.establecerPropiedad(Constant.LIQUIDACION_FRANJAS, franjas);

        if (entorno.guardarEntorno()) {
            System.out.println("Se asigno");
            return true;
        }
        return false;
    }

    /*METODO QUE INSERTA LIQUIDACION*/
    public int insertLq(LiquidacionGeneral lg) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        StringBuilder sql1 = new StringBuilder();
        sql.append("INSERT INTO tbl_liquidacion_general (FK_TARIFA_FIJA, FK_VEHICULO, FK_CONDUCTOR, TOTAL_PASAJEROS, TOTAL_PASAJEROS_LIQUIDADOS, ")
                .append(" TOTAL_VALOR_VUELTAS, TOTAL_VALOR_DESCUENTOS_PASAJEROS, TOTAL_VALOR_DESCUENTOS_ADICIONAL, TOTAL_VALOR_OTROS_DESCUENTOS, DISTANCIA_RECORRIDA, ")
                .append(" IPK, ESTADO, MODIFICACION_LOCAL, PK_UNICA, USUARIO, FECHA_INICIO_LIQUIDACION, FECHA_FIN_LIQUIDACION)")
                .append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        if (existLq(lg)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());

                ps.setInt(1, lg.getFkTarifaFija());
                ps.setInt(2, lg.getFkVehiculo());
                ps.setInt(3, lg.getFkConductor());
                ps.setInt(4, lg.getTotalPasajeros());
                ps.setInt(5, lg.getTotalPasajerosLiquidados());
                ps.setDouble(6, lg.getTotalValorVueltas());
                ps.setDouble(7, lg.getTotalValorDescuentosPasajeros());
                ps.setDouble(8, lg.getTotalValorDescuentosAdicional());
                ps.setDouble(9, lg.getTotalValorOtrosDescuentos());
                ps.setDouble(10, lg.getDistancia());
                ps.setDouble(11, lg.getIpk());
                ps.setInt(12, lg.getEstado());
                ps.setInt(13, lg.getModificacionLocal());
                ps.setString(14, lg.getPkUnica());
                ps.setInt(15, lg.getFkUsuario());
                ps.setString(16, lg.getFechaInicioLiquidacion());
                ps.setString(17, lg.getFechaFinLiquidacion());
                int retorno = ps.executeUpdate();
                con.commit();
                if (retorno > 0) {
                    sql1.append("SELECT MAX(PK_LIQUIDACION_GENERAL) AS id FROM tbl_liquidacion_general");
                    con.setAutoCommit(false);
                    ps = con.prepareStatement(sql1.toString());
                    rs = ps.executeQuery();
                    con.commit();
                    if (rs.next()) {
                        lg.setId(rs.getInt("id"));
                        return 1;
                    } else {
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }

            } catch (SQLException ex) {
                System.err.println(ex);
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException ie) {
                        System.err.println(ie);
                    }
                }

            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(e);
                }
                UtilBD.closeStatement(ps);
                pila_con.liberarConexion(con);
            }
        }/*FIN ELSE*/
        return 0;
    }

    /*METODO QUE SELECCIONA LIQUIDACIONES*/
    public static ArrayList consultaDeLiquidacion(String fecha_inicio, String fecha_fin, int fk_vehiculo, int id_empresa) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
        ArrayList listadoDeAuditorias = new ArrayList();
        Liquidacion unaLiquidacion;

        StringBuilder sql = new StringBuilder();
        if (id_empresa == 9) {
            sql.append("SELECT lg.PK_LIQUIDACION_GENERAL, lg.ESTADO, lg.FK_VEHICULO, tv.PLACA, tv.NUM_INTERNO, lg.FK_TARIFA_FIJA, tf.VALOR_TARIFA, lg.USUARIO, concat (tu.APELLIDO, \" \", tu.NOMBRE) AS LIQUIDADOR, lg.FK_CONDUCTOR, concat (tc.APELLIDO, \" \", tc.NOMBRE) AS CONDUCTOR, lg.TOTAL_PASAJEROS_LIQUIDADOS, ");
            sql.append("IF( (SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (la.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("' )  ) IS NULL, 0,  ");
            sql.append("(SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (la.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_PASAJEROS_DESCONTADOS,  ");
            sql.append("lg.TOTAL_VALOR_VUELTAS, ");
            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_VALOR_DESCUENTO_AL_NETO, ");
            sql.append(" (lg.TOTAL_VALOR_VUELTAS  - ");
            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("' )  ) IS NULL, 0,  ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) ) AS SUBTOTAL, ");

            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append("IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ) IS NULL, 0,");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fecha_inicio).append("' AND '").append(fecha_fin).append("' ))) ");
            sql.append(", ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_VALOR_DESCUENTOS,  ");

            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_VALOR_OTROS_DESCUENTOS,  ");

            sql.append("(");
            sql.append("IF( (lg.TOTAL_VALOR_VUELTAS  - ");
            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0,   ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) ) = 0, 0, ");
            sql.append("(lg.TOTAL_VALOR_VUELTAS  - ");
            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0,   ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND (cd.DESCUENTA_DEL_TOTAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) ) ");
            sql.append(") -");
            sql.append("IF((IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL,   ");
            sql.append("IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '2018/1/30 00:00:00' AND '2018/1/31 23:59:59') ) IS NULL, 0 , (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ))  ");
            sql.append(", ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) ) = 0, 0, ");
            sql.append("(IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL,   ");
            sql.append("IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '2018/1/30 00:00:00' AND '2018/1/31 23:59:59') ) IS NULL, 0 , (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') )) ");
            sql.append(", ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) )) ");
            sql.append(" - ");
            sql.append("IF((IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) )  )=0,0, ");
            sql.append("(IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) )  )) ");
            sql.append(") AS TOTAL_LIQUIDACION, ");
            sql.append(" lg.FECHA_LIQUIDACION, ");
            sql.append(" lg.FECHA_MODIFICACION ");
            sql.append("FROM tbl_liquidacion_general lg INNER JOIN tbl_tarifa_fija tf ON tf.PK_TARIFA_FIJA = lg.FK_TARIFA_FIJA INNER JOIN tbl_conductor tc ON tc.PK_CONDUCTOR = lg.FK_CONDUCTOR  INNER JOIN tbl_usuario tu ON tu.PK_USUARIO = lg.USUARIO INNER JOIN tbl_vehiculo tv ON tv.PK_VEHICULO = lg.FK_VEHICULO ");
            sql.append("WHERE (lg.ESTADO > -1)").append(" AND (tv.PLACA NOT IN ('%TG')) ");
            if (fk_vehiculo != 0) {
                sql.append("AND lg.FK_VEHICULO =  ").append(fk_vehiculo);
            }
            sql.append(" AND (lg.FECHA_LIQUIDACION between '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ");

            sql.append("ORDER BY lg.FECHA_LIQUIDACION ;");
        } else {
            sql.append("SELECT lg.PK_LIQUIDACION_GENERAL, lg.ESTADO, lg.FK_VEHICULO, tv.PLACA, tv.NUM_INTERNO, lg.FK_TARIFA_FIJA, tf.VALOR_TARIFA, lg.USUARIO, concat (tu.APELLIDO, \" \", tu.NOMBRE) AS LIQUIDADOR, lg.FK_CONDUCTOR, concat (tc.APELLIDO, \" \", tc.NOMBRE) AS CONDUCTOR, lg.TOTAL_PASAJEROS_LIQUIDADOS, ");
            sql.append("IF( (SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (la.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("' )  ) IS NULL, 0,  ");
            sql.append("(SELECT SUM(la.CANTIDAD_PASAJEROS_DESCONTADOS) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (la.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_PASAJEROS_DESCONTADOS,  ");
            sql.append("lg.TOTAL_VALOR_VUELTAS, ");

            sql.append(" (lg.TOTAL_VALOR_VUELTAS  - ");
            sql.append(" IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fecha_inicio).append("' AND '").append(fecha_fin).append(" ')) IS NULL, ");
            sql.append(" IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fecha_inicio).append("' AND '").append(fecha_fin).append(" ') ) IS NULL, ");
            sql.append("0, ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append(" ' ))) , ");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ");
            sql.append(" ) ) AS SUBTOTAL, ");

            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')) IS NULL, ");
            sql.append("IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ) IS NULL, 0,");
            sql.append(" (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN ' ").append(fecha_inicio).append("' AND '").append(fecha_fin).append("' ))) ");
            sql.append(", ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_VALOR_DESCUENTOS,  ");

            sql.append("IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) AS TOTAL_VALOR_OTROS_DESCUENTOS,  ");

            sql.append("(");
            sql.append("lg.TOTAL_VALOR_VUELTAS  - ");
            sql.append("IF((IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL,   ");
            sql.append("IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '2018/1/30 00:00:00' AND '2018/1/31 23:59:59') ) IS NULL, 0 , (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ))  ");
            sql.append(", ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) ) = 0, 0, ");
            sql.append("(IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL,   ");
            sql.append("IF ( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '2018/1/30 00:00:00' AND '2018/1/31 23:59:59') ) IS NULL, 0 , (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (lg.ESTADO=1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') )) ");
            sql.append(", ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_DESCUENTO = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) ) )) ");
            sql.append(" - ");
            sql.append("IF((IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) )  )=0,0, ");
            sql.append("(IF( (SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) IS NULL, 0, ");
            sql.append("(SELECT SUM(la.VALOR_DESCUENTO) FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON cd.PK_CATEGORIAS_DESCUENTOS = la.FK_CATEGORIAS WHERE (lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL) AND (cd.ESTADO=1) AND(cd.APLICA_GENERAL = 1) AND (lg.FECHA_LIQUIDACION BETWEEN '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("')  ) )  )) ");
            sql.append(") AS TOTAL_LIQUIDACION, ");
            sql.append(" lg.FECHA_LIQUIDACION, ");
            sql.append(" lg.FECHA_MODIFICACION ");
            sql.append("FROM tbl_liquidacion_general lg INNER JOIN tbl_tarifa_fija tf ON tf.PK_TARIFA_FIJA = lg.FK_TARIFA_FIJA INNER JOIN tbl_conductor tc ON tc.PK_CONDUCTOR = lg.FK_CONDUCTOR  INNER JOIN tbl_usuario tu ON tu.PK_USUARIO = lg.USUARIO INNER JOIN tbl_vehiculo tv ON tv.PK_VEHICULO = lg.FK_VEHICULO ");
            sql.append("WHERE (lg.ESTADO > -1)");
            if (fk_vehiculo != 0) {
                sql.append("AND lg.FK_VEHICULO =  ").append(fk_vehiculo);
            }
            sql.append(" AND (lg.FECHA_LIQUIDACION between '").append(fecha_inicio).append("' AND '").append(fecha_fin).append("') ");

            sql.append("ORDER BY lg.FECHA_LIQUIDACION ;");

        }
        System.out.println(sql.toString());

        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaLiquidacion = new Liquidacion();
                unaLiquidacion.setId(rs.getInt("PK_LIQUIDACION_GENERAL"));
                unaLiquidacion.setEstado(rs.getInt("ESTADO"));
                unaLiquidacion.setFk_vehiculo(rs.getInt("FK_VEHICULO"));
                unaLiquidacion.setPlaca(rs.getString("PLACA"));
                unaLiquidacion.setNumeroInterno(rs.getString("NUM_INTERNO"));
                unaLiquidacion.setFk_tarifa(rs.getInt("FK_TARIFA_FIJA"));
                unaLiquidacion.setValorTarifa(rs.getInt("VALOR_TARIFA"));
                unaLiquidacion.setUsuario(rs.getInt("USUARIO"));
                unaLiquidacion.setNombreCompletoLiquidador(StringUtils.upperFirstLetter(rs.getString("LIQUIDADOR")));
                unaLiquidacion.setFk_conductor(rs.getInt("FK_CONDUCTOR"));
                unaLiquidacion.setNombreCompletoConductor(StringUtils.upperFirstLetter(rs.getString("CONDUCTOR")));
                unaLiquidacion.setTotalPasajeros(rs.getDouble("TOTAL_PASAJEROS_LIQUIDADOS") + rs.getDouble("TOTAL_PASAJEROS_DESCONTADOS"));
                unaLiquidacion.setTotalPasajerosLiquidados(rs.getDouble("TOTAL_PASAJEROS_LIQUIDADOS"));
                unaLiquidacion.setTotalValorVueltas(rs.getInt("TOTAL_VALOR_VUELTAS"));
                if (id_empresa == 9) {
                    unaLiquidacion.setTotalValorDescuentoAlNeto(rs.getDouble("TOTAL_VALOR_DESCUENTO_AL_NETO"));
                }
                unaLiquidacion.setSubTotal(rs.getDouble("SUBTOTAL"));
                unaLiquidacion.setTotalValorDescuentos(rs.getDouble("TOTAL_VALOR_DESCUENTOS"));
                unaLiquidacion.setTotalRecibeConductor(rs.getDouble("SUBTOTAL") - rs.getDouble("TOTAL_VALOR_DESCUENTOS"));

                unaLiquidacion.setTotalValorOtrosDescuentos(rs.getDouble("TOTAL_VALOR_OTROS_DESCUENTOS"));
                unaLiquidacion.setTotalLiquidacion(rs.getDouble("TOTAL_LIQUIDACION"));

                unaLiquidacion.setFechaLiquidacion(rs.getDate("FECHA_LIQUIDACION"));

                listadoDeAuditorias.add(unaLiquidacion);
            }
            return listadoDeAuditorias;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    /*METODO QUE BUSCA VUELTAS PARA LIQUIDACION*/
    public static ArrayList<Vuelta> searchAround(Movil m, boolean incluirTiempo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ArrayList<Vuelta> todasLasVueltas = new ArrayList<>();
        
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ir.PK_INFORMACION_REGISTRADORA, ir.FK_VEHICULO, ir.FK_RUTA, ir.FK_USUARIO, ir.FK_BASE, ir.FK_CONDUCTOR, ir.FECHA_INGRESO, ir.HORA_INGRESO, ir.NUMERO_VUELTA, ir.NUM_VUELTA_ANT, ")
                    .append("ir.NUM_LLEGADA, ir.DIFERENCIA_NUM, ir.ENTRADAS, ir.DIFERENCIA_ENTRADA, ir.SALIDAS, ir.DIFERENCIA_SALIDA, ir.TOTAL_DIA, ir.FK_BASE_SALIDA, ir.FECHA_SALIDA_BASE_SALIDA, ir.HORA_SALIDA_BASE_SALIDA, ")
                    .append("ir.NUMERACION_BASE_SALIDA, ir.ENTRADAS_BASE_SALIDA, ir.SALIDAS_BASE_SALIDA, ir.FIRMWARE,ir.VERSION_PUNTOS, ir.ESTADO_CREACION, ir.HISTORIAL, ir.ESTADO, ir.MODIFICACION_LOCAL, ir.FECHA_MODIFICACION, ")
                    .append(" ir.PORCENTAJE_RUTA, ir.PK_UNICA, e.PK_EMPRESA, FORMAT((ir.DISTANCIA_METROS / 1000.0), 3) as distancia_km")
                    .append(" FROM tbl_vehiculo AS v INNER JOIN tbl_informacion_registradora as ir ON v.PK_VEHICULO = ir.FK_VEHICULO  INNER JOIN tbl_empresa e ON v.FK_EMPRESA = e.PK_EMPRESA  ")
                    .append(" WHERE (ir.PK_INFORMACION_REGISTRADORA NOT IN (SELECT lv.FK_INFORMACION_REGISTRADORA FROM tbl_liquidacion_vueltas as lv WHERE (lv.ESTADO = 1))) AND (ir.FECHA_INGRESO BETWEEN DATE(?) AND DATE(?)) ");

            if (incluirTiempo) {
                sql.append(" AND (ir.HORA_INGRESO BETWEEN TIME(?) AND TIME(?))");
            }

            sql.append(" AND (v.PK_VEHICULO = ?) AND (v.FK_EMPRESA = ?) AND (ir.ESTADO = 1) ")
                    .append(" ORDER BY ir.NUM_LLEGADA asc, ir.FECHA_INGRESO asc, ir.HORA_INGRESO asc;");
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            int pos = 1;
            ps.setString(pos++, m.getFecha_inicio());
            ps.setString(pos++, m.getFecha_fin());

            if (incluirTiempo) {
                ps.setString(pos++, m.getFecha_inicio());
                ps.setString(pos++, m.getFecha_fin());
            }

            ps.setInt(pos++, m.getId());
            ps.setInt(pos++, m.getFkEmpresa());
            ResultSet rs = ps.executeQuery();
            con.commit();
            Vuelta uv;
            int i = 1;

            if (rs.next()) {
                rs.beforeFirst();

                while (rs.next()) {

                    uv = new Vuelta();
                    uv.setId(rs.getInt("PK_INFORMACION_REGISTRADORA"));
                    uv.setFk_vehiculo(rs.getInt("FK_VEHICULO"));
                    uv.setFk_ruta(rs.getInt("FK_RUTA"));
                    uv.setFk_usuario(rs.getInt("FK_USUARIO"));
                    uv.setFk_base(rs.getInt("FK_BASE"));
                    uv.setFk_conductor(rs.getInt("FK_CONDUCTOR"));
                    uv.setFecha_ingreso(rs.getDate("FECHA_INGRESO"));
                    uv.setContador_vueltas(i++);
                    uv.setHora_ingreso(rs.getTime("HORA_INGRESO"));
                    uv.setNumero_vuelta(rs.getInt("NUMERO_VUELTA"));
                    uv.setNumero_vuelta_ant(rs.getInt("NUM_VUELTA_ANT"));
                    uv.setNumeracion_de_llegada(rs.getInt("NUM_LLEGADA"));
                    uv.setDiferencia_numeracion(rs.getInt("DIFERENCIA_NUM"));
                    uv.setEntradas(rs.getInt("ENTRADAS"));
                    uv.setDiferencia_entradas(rs.getInt("DIFERENCIA_ENTRADA"));
                    uv.setSalidas(rs.getInt("SALIDAS"));
                    uv.setDiferencia_salidas(rs.getInt("DIFERENCIA_SALIDA"));
                    uv.setTotal_dia(rs.getInt("TOTAL_DIA"));
                    uv.setFk_base_salida(rs.getInt("FK_BASE_SALIDA"));
                    uv.setFecha_salida_base_salida(rs.getDate("FECHA_SALIDA_BASE_SALIDA"));
                    uv.setHora_salida_base_salida(rs.getTime("HORA_SALIDA_BASE_SALIDA"));
                    uv.setNumeracion_base_salida(rs.getInt("NUMERACION_BASE_SALIDA"));
                    uv.setEntrada_base_salida(rs.getInt("ENTRADAS_BASE_SALIDA"));
                    uv.setSalidas_base_salida(rs.getInt("SALIDAS_BASE_SALIDA"));
                    uv.setFirmware(rs.getString("FIRMWARE"));
                    uv.setVersion_puntos_de_control(rs.getInt("VERSION_PUNTOS"));
                    uv.setEstado_de_creacion(rs.getInt("ESTADO_CREACION"));
                    uv.setHistorial(rs.getInt("HISTORIAL"));
                    uv.setEstado(rs.getInt("ESTADO"));
                    uv.setModificacion_local(rs.getInt("MODIFICACION_LOCAL"));
                    uv.setFecha_modificacion(rs.getDate("FECHA_MODIFICACION"));
                    uv.setPorcentaje_ruta(rs.getInt("PORCENTAJE_RUTA"));
                    uv.setPk_empresa(rs.getInt("PK_EMPRESA"));
                    uv.setDiferencia(rs.getInt("NUM_LLEGADA") - rs.getInt("NUMERACION_BASE_SALIDA"));
                    uv.setDistancia(rs.getDouble("distancia_km"));

                    todasLasVueltas.add(uv);
                }

                if (todasLasVueltas.size() > 0) {
                    return todasLasVueltas;
                } else {
                    con.rollback();
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e);
            }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }

        return null;
    }

    /*METODO QUE BUSCA UNA LIQUIDACION POR ID*/
    public static LiquidacionGeneral getById(LiquidacionGeneral c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LiquidacionGeneral lg = new LiquidacionGeneral();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PK_LIQUIDACION_GENERAL, FK_TARIFA_FIJA, FK_VEHICULO, FK_CONDUCTOR, TOTAL_PASAJEROS_LIQUIDADOS, "
                + "TOTAL_VALOR_VUELTAS, TOTAL_VALOR_DESCUENTOS_PASAJEROS, TOTAL_VALOR_DESCUENTOS_ADICIONAL, DISTANCIA_RECORRIDA, "
                + "ESTADO, FECHA_LIQUIDACION, USUARIO, ID_INICIAL_GPS, ID_FINAL_GPS, NUMERACION_INICIAL_GPS, NUMERACION_FINAL_GPS ");
        sql.append(" FROM tbl_liquidacion_general ");
        sql.append(" WHERE (PK_LIQUIDACION_GENERAL=?) AND (ESTADO=1)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            con.commit();
            if (rs.next()) {
                lg.setId(rs.getInt("PK_LIQUIDACION_GENERAL"));
                lg.setFkTarifaFija(rs.getInt("FK_TARIFA_FIJA"));
                lg.setFkVehiculo(rs.getInt("FK_VEHICULO"));
                lg.setFkConductor(rs.getInt("FK_CONDUCTOR"));
                lg.setTotalPasajerosLiquidados(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS"));
                lg.setTotalValorVueltas(rs.getInt("TOTAL_VALOR_VUELTAS"));
                lg.setTotalValorDescuentosPasajeros(rs.getInt("TOTAL_VALOR_DESCUENTOS_PASAJEROS"));
                lg.setTotalValorDescuentosAdicional(rs.getInt("TOTAL_VALOR_DESCUENTOS_ADICIONAL"));
                lg.setDistancia(rs.getInt("DISTANCIA_RECORRIDA"));
                lg.setEstado(rs.getInt("ESTADO"));
                lg.setFechaLiquidacion(rs.getDate("FECHA_LIQUIDACION"));
                lg.setFkUsuario(rs.getInt("USUARIO"));
                lg.setIdInicialGps(rs.getLong("ID_INICIAL_GPS"));
                lg.setIdFinalGps(rs.getLong("ID_FINAL_GPS"));
                lg.setNumeracionInicialGps(rs.getInt("NUMERACION_INICIAL_GPS"));
                lg.setNumeracionFinalGps(rs.getInt("NUMERACION_FINAL_GPS"));
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e);
            }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
        return lg;
    }

    /*METODO QUE ANULA LIQUIDACION*/
    public static boolean updateEstateLq(LiquidacionGeneral c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_liquidacion_general SET ESTADO=? WHERE PK_LIQUIDACION_GENERAL=? ");
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getEstado());
            ps_update.setInt(2, c.getId());
            int retorno = ps_update.executeUpdate();
            con.commit();
            if (retorno > 0) {
                return true;
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e);
            }
            UtilBD.closeStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    /*METODO QUE BUSCA TARIFA ACTIVA DE LIQUIDACION*/
    public static ArrayList<Tarifa> searchActiveTax(Tarifa c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tarifa t = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT tf.PK_TARIFA_FIJA, tf.NOMBRE_TARIFA, tf.VALOR_TARIFA, tf.TARIFA_ACTIVA, tf.ESTADO FROM tbl_tarifa_fija as tf WHERE (tf.TARIFA_ACTIVA=?) AND (tf.ESTADO=1) ORDER BY tf.VALOR_TARIFA");
        //System.out.println("->"+sql.toString());
        ArrayList<Tarifa> vh = new ArrayList();
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getActiva());
            rs = ps.executeQuery();

            while (rs.next()) {
                t = new Tarifa();
                t.setId(rs.getInt("PK_TARIFA_FIJA"));
                t.setNombre(rs.getString("NOMBRE_TARIFA"));
                t.setValor(rs.getInt("VALOR_TARIFA"));
                t.setActiva(rs.getInt("TARIFA_ACTIVA"));
                t.setEstado(rs.getInt("ESTADO"));

                vh.add(t);
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e);
            }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
        return vh;
    }

    /*METODO QUE BUSCA AUDITORIA DE  LIQUIDACION*/
    public static AuditoriaLiquidacionGeneral getAuditoriaLiquidacion(LiquidacionGeneral entidad) {
        AuditoriaLiquidacionGeneral alg = new AuditoriaLiquidacionGeneral();

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement statement = null;

        try {
            statement = con.createStatement();
            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM tbl_auditoria_liquidacion_general AS alg ");
            query.append(" WHERE (alg.FK_TBL_LIQUIDACION_GENERAL = ").append(entidad.getId()).append(") ");
            query.append(" AND (alg.FK_VEHICULO = ").append(entidad.getFkVehiculo()).append(") ");
            query.append(" AND (alg.ESTADO_NEW = ").append(entidad.getEstado()).append(") ");
            query.append(" ORDER BY  alg.PK_AUDITORIA_LIQUIDACION_GENERAL ").append(" DESC ").append(" LIMIT 1;");

            ResultSet resultset = statement.executeQuery(query.toString());

            if (resultset.next()) {
                alg.setPkAuditoriaLiquidacionGeneral(resultset.getInt("PK_AUDITORIA_LIQUIDACION_GENERAL"));
                alg.setFkLiqidacionGeneral(resultset.getInt("FK_TBL_LIQUIDACION_GENERAL"));
                alg.setFkTarifaFija(resultset.getInt("FK_TARIFA_FIJA"));
                alg.setFkVehiculo(resultset.getInt("FK_VEHICULO"));
                alg.setFkConductor(resultset.getInt("FK_CONDUCTOR"));
                alg.setFkUsuario(resultset.getInt("USUARIO_REGISDATA"));
                alg.setTotalPasajerosLiquidadosNew(resultset.getInt("TOTAL_PASAJEROS_LIQUIDADOS_NEW"));
                alg.setTotalValorVueltasNew(resultset.getDouble("TOTAL_VALOR_VUELTAS_NEW"));
                alg.setTotalValorDescuentosPasajerosNew(resultset.getDouble("TOTAL_VALOR_DESCUENTO_PASAJEROS_NEW"));
                alg.setTotalValorDescuentosAdicionalNew(resultset.getDouble("TOTAL_VALOR_DESCUENTO_ADICIONAL_NEW"));
                alg.setEstadoNew(resultset.getInt("ESTADO_NEW"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            UtilBD.closeStatement(statement);
            pila_con.liberarConexion(con);
        }
        return alg;
    }

    public static byte[] generarReporte(String io, String nombreReporte, java.util.Map parameters) throws JRException {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection miConexion = pila_con.obtenerConexion();
        File sourceFile = new File(System.getProperty("user.home").replace("\\", "/") + "/Documents/NetBeansProjects/regisdata_web/reportes/" + nombreReporte + ".jasper");
        byte[] bytes = JasperRunManager.runReportToPdf(sourceFile.getPath(), parameters, miConexion);
        return bytes;
    }

    public static net.sf.jasperreports.engine.JasperPrint generarReporte1(String io, String nombreReporte, java.util.Map parameters) throws JRException {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection miConexion = pila_con.obtenerConexion();
        return net.sf.jasperreports.engine.JasperFillManager.fillReport(io, parameters, pila_con.obtenerConexion());
    }

    /*DATOS DEL CONDUCTOR DESDE LIQUIDACION*/
    public static int insert(Liquidacion c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor (");
        sql.append(c.getId());
        sql.append(", ");/*
        sql.append(c.getCodEmpresa());
        sql.append(", ");
        sql.append(c.getId_tipo_documento());
        sql.append(", '");
        sql.append(c.getNombre());
        sql.append("', '");
        sql.append(c.getApellido());
        sql.append("', ");
        sql.append(c.getCedula());        
        sql.append(", ");*/
        sql.append(c.getEstado());
        sql.append(", 1, NULL, NULL, 0, 0);");

        if (exist(c)) {
            return 0;
        } else {
            try {
                Statement createStatement = con.createStatement();
                int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
                ResultSet resultset = createStatement.executeQuery(sql.toString());
                if (resultset.next()) {
                    return 1;
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            } finally {
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }
        }
        return 0;
    }

    public static Conductor selectByOne(Movil m) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Conductor conductorEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_conductor (");
        sql.append(m.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, ");
        sql.append(m.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());
            conductorEncontrado = new Conductor();
            if (rs.next()) {
                conductorEncontrado.setId(rs.getInt("PK_CONDUCTOR"));
                conductorEncontrado.setEstado(rs.getInt("ESTADO"));
            }
            return conductorEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int update(Liquidacion c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        try {

            sql.append("CALL proc_tbl_conductor (");
            sql.append(c.getId());
            sql.append(", ");/*
        sql.append(c.getCodEmpresa());
        sql.append(", ");
        sql.append(c.getId_tipo_documento());
        sql.append(", '");
        sql.append(c.getNombre());
        sql.append("', '");
        sql.append(c.getApellido());
        sql.append("', ");
        sql.append(c.getCedula());        
        sql.append(", ");*/
            sql.append(c.getEstado());
            sql.append(", 1, NULL, NULL, NULL, 1);");

            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            if (resultset.next()) {
                return 1;
            }
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return 0;
    }

    public static boolean updateEstado(Liquidacion c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_conductor ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_CONDUCTOR=? ");
        try {
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getEstado());
            ps_update.setInt(2, c.getId());
            ps_update.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
        }

    }
    
    
    public static boolean updateLiquidacionPorTiempos(LiquidacionGeneral liquidacion, long idInicialGps, 
            long idFinalGps, int numeracionInicial, int numeracionFinal) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE tbl_liquidacion_general SET ")
            .append("ID_INICIAL_GPS = ?, ID_FINAL_GPS = ?, NUMERACION_INICIAL_GPS = ?, NUMERACION_FINAL_GPS = ? ");
        query.append("WHERE PK_LIQUIDACION_GENERAL = ?");
        
        try {
            connection.setAutoCommit(false);
            int pos = 1;
            ps = connection.prepareStatement(query.toString());
            ps.setLong(pos++, idInicialGps);
            ps.setLong(pos++, idFinalGps);
            ps.setInt(pos++, numeracionInicial);
            ps.setInt(pos++, numeracionFinal);
            ps.setLong(pos++, liquidacion.getId());
            int pk = ps.executeUpdate();
            connection.commit();
            
            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch(SQLException sqlEx) {
            Logger.getLogger(LiquidacionBD.class.getName()).log(Level.SEVERE, null, sqlEx);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    Logger.getLogger(LiquidacionBD.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } catch(Exception ex) {
            Logger.getLogger(LiquidacionBD.class.getName()).log(Level.SEVERE, null, ex);
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ie) {
                    Logger.getLogger(LiquidacionBD.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                Logger.getLogger(LiquidacionBD.class.getName()).log(Level.SEVERE, null, sqlEx);
            }
            
            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }
        
        return false;
    }

}//FIN DE CLASE

