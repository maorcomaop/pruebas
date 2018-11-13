/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.InformacionRegistradora;
import com.registel.rdw.logica.Motivo;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.AuditoriaInformacionRegistradora;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.MovilRuta;
import com.registel.rdw.logica.Ruta;
import com.registel.rdw.logica.UsuarioPermisoEmpresa;
import com.registel.rdw.reportes.RutaXVehiculo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lider_desarrollador
 */
public class InformacionRegistradoraBD {

    private static ArrayList<InformacionRegistradora> lstinforeg;
    private static final int ERROR_PERMISO_USUARIO = 1;
    private static final int ERROR_CONSULTA = 2;
    private static final int CONSULTA_HECHA = 3;

    // Consulta registros de informacion registradora en un rango de fechas,
    // vehiculo y limite de registros especifico.
    public static int selectBy(int idUsuario, int idVehiculo, String fechaInicio, String fechaFinal, int limit) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Asigna instruccion SQL de consulta
        String sql = getBodySelectSQL();
        // Asigna restricciones para instruccion SQL de consulta
        sql += getRestSelectSQL(idVehiculo, fechaInicio, fechaFinal, limit);
        sql += " ORDER BY tbl_informacion_registradora.`FECHA_INGRESO` DESC,"
                + " tbl_informacion_registradora.`HORA_INGRESO` DESC";

        //        sql += " WHERE tbl_empresa.`PK_EMPRESA` IN (0 ";            
        //        for (UsuarioPermisoEmpresa upe : lstupe) {
        //            sql += " , " + upe.getIdEmpresa();
        //        }            
        //        sql += ") ORDER BY tbl_informacion_registradora.`FECHA_INGRESO` DESC,"
        //            + " tbl_informacion_registradora.`HORA_INGRESO` DESC;";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstinforeg = new ArrayList<InformacionRegistradora>();
            while (rs.next()) {
                agregarElemento(rs);
            }
            return CONSULTA_HECHA;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return ERROR_CONSULTA;
    }

    // Consulta registros de informacion registradora
    // limitados a una cantidad especifica.
    public static int select(int idUsuario, int limit) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Asigna instruccion SQL de consulta
        String sql = getBodySelectSQL();
        // Asigna restriccion para instruccion SQL de consulta
        sql += getRestSelectSQL(limit);
        sql += " ORDER BY tbl_informacion_registradora.`FECHA_INGRESO` DESC,"
                + " tbl_informacion_registradora.`HORA_INGRESO` DESC";

        //        sql += " WHERE tbl_empresa.`PK_EMPRESA` IN (0 ";            
        //        for (UsuarioPermisoEmpresa upe : lstupe) {
        //            sql += " , " + upe.getIdEmpresa();
        //        }            
        //        sql += ") ORDER BY tbl_informacion_registradora.`FECHA_INGRESO` DESC,"
        //            + " tbl_informacion_registradora.`HORA_INGRESO` DESC;";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstinforeg = new ArrayList<InformacionRegistradora>();
            while (rs.next()) {
                agregarElemento(rs);
            }
            return CONSULTA_HECHA;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return ERROR_CONSULTA;
    }

    // Consulta registro de informacion registradora en especifico.
    public static InformacionRegistradora selectById(int id_inforeg) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_informacion_registradora WHERE"
                + " PK_INFORMACION_REGISTRADORA = ? AND ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_inforeg);
            rs = ps.executeQuery();

            if (rs.next()) {
                InformacionRegistradora ir = agregarElementoUnico(rs);
                ir.setAlarmas(AlarmaInfoRegBD.selectBy(id_inforeg));
                ir.setPuntosControl(PuntoControlBD.selectBy(id_inforeg));
                ir.setConteosPerimetro(ConteoPerimetroBD.selectBy(id_inforeg));

                Ruta r = RutaBD.get("" + ir.getIdRuta());
                if (r != null) {
                    ir.setNombreRuta(r.getNombre());
                }

                Conductor c = ConductorBD.get(ir.getIdConductor());
                if (c != null) {
                    ir.setNombreConductor(c.getNombre() + " " + c.getApellido());
                }

                Double ptjRuta = ir.getPorcentajeRuta();
                if (ptjRuta != null) {
                    int ipr = ptjRuta.intValue();
                    ir.setPorcentajeRuta(ipr);
                }

                return ir;
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    /*ADICIONADO POR JAIR VIDAL*/
    public static Integer getAuditoria(InformacionRegistradora entidad) {
        Integer pk = null;
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        String query = "SELECT PK_AUDITORIA_INFORMACION_REGISTRADORA "
                + "FROM tbl_auditoria_informacion_registradora "
                + "WHERE FK_INFORMACION_REGISTRADORA = " + entidad.getId() + " "
                + "ORDER BY PK_AUDITORIA_INFORMACION_REGISTRADORA DESC LIMIT 1";
        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(query);

            if (resultset.next()) {
                pk = resultset.getInt("PK_AUDITORIA_INFORMACION_REGISTRADORA");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR GETAUDITORIA " + ex.getMessage());
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            try {
                con.close();
            } catch (SQLException ex1) {
                System.out.println("ERROR GETAUDITORIA " + ex1.getMessage());
            }
        }
        return pk;
    }

    public static void auditar(InformacionRegistradora ir, InformacionRegistradora irOld, Motivo m, Usuario u, AuditoriaInformacionRegistradora air) {
        m.setFkAuditoria(getAuditoria(ir));

        String mensaje = "La vuelta #: " + ir.getId() + " fue modificada por el ID = " + u.getId() + " que pertenece al usuario de regisdata = " + u.getNombre() + " " + u.getApellido()
                + " conectado a la BD con el usuario = " + u.getUsuariobd() + " el procedimiento que se realizó fue: " + m.getDescripcionMotivo()
                + " la numeracion INICIAL antes de modificar es: " + irOld.getNumeracionBS() + " y la numeracion INICIAL modificada es: " + ir.getNumeracionBS()
                + " la numeracion FINAL antes de modificar es: " + irOld.getNumeroLlegada() + " y la numeracion FINAL modificada es: " + ir.getNumeroLlegada();
        m.setInformacionAdicional(mensaje);
        m.setUsuario(u.getId());
        m.setUsuarioBD(u.getUsuariobd());
        try {
            int retorno1 = MotivoBD.insert(m);
            boolean retorno2 = AuditoriaBD.updateUserInformacionRegistradora(air);

        } catch (ExisteRegistroBD ex) {
            System.out.println("ERROR AL INSERTAR EL MOTIVO " + ex.getMessage());
        }
    }

    public static AuditoriaInformacionRegistradora getAuditoriaRegistradora(InformacionRegistradora ir) {
        AuditoriaInformacionRegistradora alg = new AuditoriaInformacionRegistradora();

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement statement = null;

        try {
            statement = con.createStatement();
            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM tbl_auditoria_informacion_registradora AS air ");
            query.append(" WHERE (air.FK_INFORMACION_REGISTRADORA = ").append(ir.getId()).append(") ");
            query.append(" AND (air.FK_VEHICULO = ").append(ir.getIdVehiculo()).append(") ");
            query.append(" AND (air.ESTADO = ").append(ir.getEstado()).append(") ");
            query.append(" ORDER BY  air.PK_AUDITORIA_INFORMACION_REGISTRADORA ").append(" DESC ").append(" LIMIT 1;");
            //System.out.println(query.toString());
            ResultSet resultset = statement.executeQuery(query.toString());

            if (resultset.next()) {
                alg.setId(resultset.getInt("PK_AUDITORIA_INFORMACION_REGISTRADORA"));
                alg.setFk(resultset.getInt("FK_INFORMACION_REGISTRADORA"));
                alg.setEstado(resultset.getInt("ESTADO"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            UtilBD.closeStatement(statement);
            pila_con.liberarConexion(con);
        }
        return alg;
    }

    /*MODIFICADO POR JAIR VIDAL*/
    public static boolean updateInfoGeneral(InformacionRegistradora ir, InformacionRegistradora irOld, Motivo m, Usuario u) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_informacion_registradora SET"
                + " FECHA_INGRESO = ?, HORA_INGRESO = ?, NUMERACION_BASE_SALIDA = ?,"
                + " NUM_LLEGADA = ?, DIFERENCIA_NUM = ?, ENTRADAS = ?, SALIDAS = ?,"
                + " DIFERENCIA_ENTRADA = ?, DIFERENCIA_SALIDA = ?"
                + " WHERE PK_INFORMACION_REGISTRADORA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ir.getFechaIngreso().getTime()));
            ps.setTime(2, new java.sql.Time(ir.getHoraIngreso().getTime()));
            ps.setInt(3, ir.getNumeracionBS());
            ps.setInt(4, ir.getNumeroLlegada());
            ps.setInt(5, ir.getDiferenciaNumero());
            ps.setInt(6, ir.getEntradas());
            ps.setInt(7, ir.getSalidas());
            ps.setInt(8, ir.getDiferenciaEntrada());
            ps.setInt(9, ir.getDiferenciaSalida());
            ps.setInt(10, ir.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            //auditar(ir, irOld, m, u);
        }
        return false;
    }

    // Actualiza informacion de base-salida para un registro de
    // informacion registradora en especifico.
    public static boolean updateInfoSalida(InformacionRegistradora ir) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_informacion_registradora SET"
                + " FECHA_SALIDA_BASE_SALIDA = ?, HORA_SALIDA_BASE_SALIDA = ?,"
                + " NUMERACION_BASE_SALIDA = ?, ENTRADAS_BASE_SALIDA = ?,"
                + " SALIDAS_BASE_SALIDA = ?"
                + " WHERE PK_INFORMACION_REGISTRADORA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ir.getFechaSalidaBS().getTime()));
            ps.setTime(2, new java.sql.Time(ir.getHoraSalidaBS().getTime()));
            ps.setInt(3, ir.getNumeracionBS());
            ps.setInt(4, ir.getEntradasBS());
            ps.setInt(5, ir.getSalidasBS());
            ps.setInt(6, ir.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Asigna ruta (identicador de ruta) a un registro de
    // informacion registradora en especifico.
    public static boolean asignaRuta(int idInfreg, int idruta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE tbl_informacion_registradora SET FK_RUTA = ?"
                + " WHERE PK_INFORMACION_REGISTRADORA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idruta);
            ps.setInt(2, idInfreg);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Extrae y asigna valores de consulta a clase instanceada de informacion
    // registradora. Retorna objeto instanceado.
    public static InformacionRegistradora agregarElementoUnico(ResultSet rs) {
        try {
            InformacionRegistradora ir = new InformacionRegistradora();

            ir.setId(rs.getInt("PK_INFORMACION_REGISTRADORA"));
            ir.setIdVehiculo(rs.getInt("FK_VEHICULO"));
            ir.setIdRuta(rs.getInt("FK_RUTA"));
            ir.setIdUsuario(rs.getInt("FK_USUARIO"));
            ir.setIdBase(rs.getInt("FK_BASE"));
            ir.setIdConductor(rs.getInt("FK_CONDUCTOR"));
            ir.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
            ir.setHoraIngreso(rs.getTime("HORA_INGRESO"));
            ir.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
            ir.setNumeroVueltaAnterior(rs.getInt("NUM_VUELTA_ANT"));
            ir.setNumeroLlegada(rs.getInt("NUM_LLEGADA"));
            ir.setDiferenciaNumero(rs.getInt("DIFERENCIA_NUM"));
            ir.setEntradas(rs.getInt("ENTRADAS"));
            ir.setDiferenciaEntrada(rs.getInt("DIFERENCIA_ENTRADA"));
            ir.setSalidas(rs.getInt("SALIDAS"));
            ir.setDiferenciaSalida(rs.getInt("DIFERENCIA_SALIDA"));
            ir.setTotalDia(rs.getInt("TOTAL_DIA"));
            ir.setIdBaseSalida(rs.getInt("FK_BASE_SALIDA"));
            ir.setFechaSalidaBS(rs.getDate("FECHA_SALIDA_BASE_SALIDA"));
            ir.setHoraSalidaBS(rs.getTime("HORA_SALIDA_BASE_SALIDA"));
            ir.setNumeracionBS(rs.getInt("NUMERACION_BASE_SALIDA"));
            ir.setEntradasBS(rs.getInt("ENTRADAS_BASE_SALIDA"));
            ir.setSalidasBS(rs.getInt("SALIDAS_BASE_SALIDA"));
            ir.setFirmware(rs.getString("FIRMWARE"));
            ir.setVersionPuntos(rs.getInt("VERSION_PUNTOS"));
            ir.setEstadoCreacion(rs.getInt("ESTADO_CREACION"));
            ir.setHistorial(rs.getInt("HISTORIAL"));
            ir.setEstado(rs.getInt("ESTADO"));
            ir.setPorcentajeRuta(rs.getDouble("PORCENTAJE_RUTA"));

            return ir;

        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    // Extrae y asigna valores de consulta a clase instanceada de informacion
    // registradora. Adiciona objeto instanceado en lista global.
    public static void agregarElemento(ResultSet rs) {
        try {
            InformacionRegistradora ir = new InformacionRegistradora();

            ir.setId(rs.getInt("PK_INFORMACION_REGISTRADORA"));
            ir.setIdVehiculo(rs.getInt("FK_VEHICULO"));
            ir.setIdRuta(rs.getInt("FK_RUTA"));
            ir.setIdUsuario(rs.getInt("FK_USUARIO"));
            ir.setIdBase(rs.getInt("FK_BASE"));
            ir.setIdConductor(rs.getInt("FK_CONDUCTOR"));
            ir.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
            ir.setHoraIngreso(rs.getTime("HORA_INGRESO"));
            ir.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
            ir.setNumeroVueltaAnterior(rs.getInt("NUM_VUELTA_ANT"));
            ir.setNumeroLlegada(rs.getInt("NUM_LLEGADA"));
            ir.setDiferenciaNumero(rs.getInt("DIFERENCIA_NUM"));
            ir.setEntradas(rs.getInt("ENTRADAS"));
            ir.setDiferenciaEntrada(rs.getInt("DIFERENCIA_ENTRADA"));
            ir.setSalidas(rs.getInt("SALIDAS"));
            ir.setDiferenciaSalida(rs.getInt("DIFERENCIA_SALIDA"));
            ir.setTotalDia(rs.getInt("TOTAL_DIA"));
            ir.setIdBaseSalida(rs.getInt("FK_BASE_SALIDA"));
            ir.setFechaSalidaBS(rs.getDate("FECHA_SALIDA_BASE_SALIDA"));
            ir.setHoraSalidaBS(rs.getTime("HORA_SALIDA_BASE_SALIDA"));
            ir.setNumeracionBS(rs.getInt("NUMERACION_BASE_SALIDA"));
            ir.setEntradasBS(rs.getInt("ENTRADAS_BASE_SALIDA"));
            ir.setSalidasBS(rs.getInt("SALIDAS_BASE_SALIDA"));
            ir.setFirmware(rs.getString("FIRMWARE"));
            ir.setVersionPuntos(rs.getInt("VERSION_PUNTOS"));
            ir.setEstadoCreacion(rs.getInt("ESTADO_CREACION"));
            ir.setHistorial(rs.getInt("HISTORIAL"));
            ir.setEstado(rs.getInt("ESTADO"));
            ir.setPlaca(rs.getString("PLACA"));
            ir.setNumeroInterno(rs.getString("NUM_INTERNO"));
            ir.setIdEmpresa(rs.getInt("PK_EMPRESA"));

            ir.setExistePuntoControl(rs.getBoolean("EXISTE_PUNTO_CONTROL"));
            ir.setExisteAlarma(rs.getBoolean("EXISTE_ALARMA"));
            ir.setPerimetro(rs.getInt("CONTEO_PERIMETRO"));
            ir.setIdLiquidacion(rs.getInt("LIQUIDACION"));

            ir.setNombreBS(rs.getString("NOMBRE_BASE_SALIDA"));
            ir.setNombreBL(rs.getString("NOMBRE_BASE_LLEGADA"));

            //Ruta ruta = RutaBD.get("" + rs.getInt("FK_RUTA"));
            //Conductor conductor = ConductorBD.get(rs.getInt("FK_CONDUCTOR"));
            String ruta = rs.getString("NOMBRE_RUTA");
            String conductor = rs.getString("NOMBRE_CONDUCTOR");

            ruta = (ruta == null) ? "NA" : ruta;
            conductor = (conductor == null) ? "NA" : conductor;

            ir.setNombreRuta(ruta);
            ir.setNombreConductor(conductor);

            lstinforeg.add(ir);

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    // Construye cuerpo de instruccion SQL para consulta de registros de
    // informacion registradora. Se complementa informacion con subconsultas
    // realizadas a otras tablas.
    public static String getBodySelectSQL() {

        String sql = "SELECT "
                + " tbl_informacion_registradora.`PK_INFORMACION_REGISTRADORA` AS PK_INFORMACION_REGISTRADORA, "
                + " tbl_informacion_registradora.`FK_VEHICULO` AS FK_VEHICULO, "
                + " tbl_informacion_registradora.`FK_RUTA` AS FK_RUTA, "
                + " tbl_informacion_registradora.`FK_USUARIO` AS FK_USUARIO, "
                + " tbl_informacion_registradora.`FK_BASE` AS FK_BASE, "
                + " tbl_informacion_registradora.`FK_CONDUCTOR` AS FK_CONDUCTOR, "
                + " tbl_informacion_registradora.`FECHA_INGRESO` AS FECHA_INGRESO, "
                + " tbl_informacion_registradora.`HORA_INGRESO` AS HORA_INGRESO, "
                + " tbl_informacion_registradora.`NUMERO_VUELTA` AS NUMERO_VUELTA, "
                + " tbl_informacion_registradora.`NUM_VUELTA_ANT` AS NUM_VUELTA_ANT, "
                + " tbl_informacion_registradora.`NUM_LLEGADA` AS NUM_LLEGADA, "
                + " tbl_informacion_registradora.`DIFERENCIA_NUM` AS DIFERENCIA_NUM, "
                + " tbl_informacion_registradora.`ENTRADAS` AS ENTRADAS, "
                + " tbl_informacion_registradora.`DIFERENCIA_ENTRADA` AS DIFERENCIA_ENTRADA, "
                + " tbl_informacion_registradora.`SALIDAS` AS SALIDAS, "
                + " tbl_informacion_registradora.`DIFERENCIA_SALIDA` AS DIFERENCIA_SALIDA, "
                + " tbl_informacion_registradora.`TOTAL_DIA` AS TOTAL_DIA, "
                + " tbl_informacion_registradora.`FK_BASE_SALIDA` AS FK_BASE_SALIDA, "
                + " tbl_informacion_registradora.`FECHA_SALIDA_BASE_SALIDA` AS FECHA_SALIDA_BASE_SALIDA, "
                + " tbl_informacion_registradora.`HORA_SALIDA_BASE_SALIDA` AS HORA_SALIDA_BASE_SALIDA, "
                + " tbl_informacion_registradora.`NUMERACION_BASE_SALIDA` AS NUMERACION_BASE_SALIDA, "
                + " tbl_informacion_registradora.`ENTRADAS_BASE_SALIDA` AS ENTRADAS_BASE_SALIDA, "
                + " tbl_informacion_registradora.`SALIDAS_BASE_SALIDA` AS SALIDAS_BASE_SALIDA, "
                + " tbl_informacion_registradora.`FIRMWARE` AS FIRMWARE, "
                + " tbl_informacion_registradora.`VERSION_PUNTOS` AS VERSION_PUNTOS, "
                + " tbl_informacion_registradora.`ESTADO_CREACION` AS ESTADO_CREACION, "
                + " tbl_informacion_registradora.`HISTORIAL` AS HISTORIAL, "
                + " tbl_informacion_registradora.`ESTADO` AS ESTADO, "
                + " tbl_informacion_registradora.`MODIFICACION_LOCAL` AS MODIFICACION_LOCAL, "
                + " tbl_informacion_registradora.`FECHA_MODIFICACION` AS FECHA_MODIFICACION, "
                + " tbl_informacion_registradora.`PORCENTAJE_RUTA` AS PORCENTAJE_RUTA, "
                + " tbl_informacion_registradora.`PK_UNICA` AS PK_UNICA, "
                + " tbl_vehiculo.`PLACA` AS PLACA, "
                + " tbl_vehiculo.`NUM_INTERNO` AS NUM_INTERNO, "
                //+ " IF ((SELECT COUNT(*) FROM tbl_liquidacion l WHERE l.FK_INFORMACION_REGISTRADORA = tbl_informacion_registradora.PK_INFORMACION_REGISTRADORA) > 0 , 1, 0) AS LIQUIDADO, "
                + " tbl_empresa.`PK_EMPRESA` AS PK_EMPRESA, "
                + " (SELECT IF(count(PK_PUNTO_CONTROL), 'true', 'false') FROM tbl_punto_control use index(FK_PUNTO_CONTROL_INFORMACION_REGISTRADORA) WHERE "
                + "         FK_INFO_REGIS = PK_INFORMACION_REGISTRADORA AND tbl_punto_control.ESTADO = 1) AS EXISTE_PUNTO_CONTROL, "
                + " (SELECT IF(count(PK_AIR), 'true', 'false') FROM tbl_alarma_info_regis use index(FK_INFORMACION_REGISTRADORA_ALARMA) WHERE "
                + "         FK_INFORMACION_REGISTRADORA = PK_INFORMACION_REGISTRADORA) AS EXISTE_ALARMA, "
                + " (SELECT IFNULL(SUM(DIFERENCIA) , 0) FROM tbl_conteo_perimetro use index(FK_INFORMACION_REGISTRADORA_VEHICULO) WHERE "
                + "         FK_INFORMACION_REGISTRADORA = PK_INFORMACION_REGISTRADORA AND tbl_conteo_perimetro.ESTADO = 1) AS CONTEO_PERIMETRO, "
                + " (SELECT IF(count(FK_LIQUIDACION_GENERAL) > 0 , FK_LIQUIDACION_GENERAL, 0) FROM tbl_liquidacion_vueltas WHERE "
                + "         FK_INFORMACION_REGISTRADORA = PK_INFORMACION_REGISTRADORA AND tbl_liquidacion_vueltas.ESTADO = 1) AS LIQUIDACION, "
                + " (SELECT NOMBRE FROM tbl_base WHERE PK_BASE = FK_BASE) AS NOMBRE_BASE_LLEGADA, "
                + " (SELECT NOMBRE FROM tbl_base WHERE PK_BASE = FK_BASE_SALIDA) AS NOMBRE_BASE_SALIDA, "
                + " (SELECT NOMBRE FROM tbl_ruta WHERE PK_RUTA = FK_RUTA AND ESTADO = 1) AS NOMBRE_RUTA, "
                + " (SELECT concat(NOMBRE, ' ', APELLIDO) FROM tbl_conductor WHERE PK_CONDUCTOR = FK_CONDUCTOR AND ESTADO = 1) AS NOMBRE_CONDUCTOR "
                + " FROM `tbl_empresa` tbl_empresa"
                + "  STRAIGHT_JOIN `tbl_vehiculo` tbl_vehiculo ON tbl_vehiculo.`FK_EMPRESA` = tbl_empresa.`PK_EMPRESA`";

        return sql;
    }

    // Construye cuerpo de restricciones para instruccion de consulta SQL
    // Se tiene en cuenta valores de vehiculo, rango de fechas y limite de registros
    public static String getRestSelectSQL(int idVehiculo, String fechaInicio, String fechaFinal, int limit) {

        String rest = " STRAIGHT_JOIN (SELECT * FROM view_tbl_informacion_registradora WHERE"
                + " FK_VEHICULO = " + idVehiculo;

        if (fechaInicio != "" && fechaFinal != "") {
            rest += " AND FECHA_INGRESO >= '" + fechaInicio + "' AND FECHA_INGRESO <= '" + fechaFinal + "'";
        } else if (fechaInicio != "") {
            rest += " AND FECHA_INGRESO = '" + fechaInicio + "'";
        }
        if (limit > 0) {
            rest += " LIMIT " + limit;
        } else {
            rest += " LIMIT 30";
        }

        rest += ") AS tbl_informacion_registradora ON tbl_vehiculo.`PK_VEHICULO` = tbl_informacion_registradora.`FK_VEHICULO`";

        return rest;
    }

    // Construye cuerpo de restriccion de limite de registros para instruccion de consulta SQL
    public static String getRestSelectSQL(int limit) {

        if (limit <= 0) {
            limit = 30;
        }

        String sql = " STRAIGHT_JOIN (SELECT * FROM view_tbl_informacion_registradora"
                + " ORDER BY `FECHA_INGRESO` DESC, `HORA_INGRESO` DESC LIMIT " + limit + ") AS"
                + " tbl_informacion_registradora ON tbl_vehiculo.`PK_VEHICULO` = tbl_informacion_registradora.`FK_VEHICULO`";

        return sql;
    }

    // Para reporte : ruta por vehiculo
    // Ruta debe existir y estar activa 
    // Consulta informacion (de identificacion y tiempo) para punto base-salida
    // asociado a un registro de informacion registradora
    public static RutaXVehiculo getBaseSalida(int idir, int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " IFNULL(r.NOMBRE, 'NA') AS NOMBRE_RUTA,"
                + " b.PK_BASE AS BASE_PK,"
                + " b.NOMBRE AS NOMBRE_PUNTO,"
                + " SEC_TO_TIME((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + rp.PROMEDIO_MINUTOS * 60)) AS HORA_PLANIFICADA,"
                + " ir.HORA_SALIDA_BASE_SALIDA AS HORA_REAL_SALIDA,"
                + " SEC_TO_TIME(ABS((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + rp.PROMEDIO_MINUTOS * 60) - TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA))) AS DIFERENCIA,"
                + " IF(TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) < ((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + (rp.PROMEDIO_MINUTOS * 60)) - (rp.HOLGURA_MINUTOS * 60)), 2,"
                + " IF(TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) > ((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + (rp.PROMEDIO_MINUTOS * 60)) + (rp.HOLGURA_MINUTOS * 60)), 1, 0)) AS ESTADO,"
                + " ir.NUMERACION_BASE_SALIDA AS NUMERACION,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " INNER JOIN tbl_ruta_punto AS rp ON"
                + "   ir.FK_RUTA = rp.FK_RUTA AND rp.FK_PUNTO = 101"
                + " LEFT JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " WHERE ir.PK_INFORMACION_REGISTRADORA = ? AND ir.FK_RUTA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idir);
            ps.setInt(2, idRuta);

            rs = ps.executeQuery();

            if (rs.next()) {
                RutaXVehiculo r = new RutaXVehiculo();

                r.setRuta(rs.getString("NOMBRE_RUTA"));
                r.setIdPunto(-1); // BASE_SALIDA
                r.setPuntoControl(rs.getString("NOMBRE_PUNTO"));
                r.setNumeracion(rs.getInt("NUMERACION"));
                r.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                r.setHoraRealLlegada(rs.getTime("HORA_REAL_SALIDA"));
                r.setDiferenciaTiempo(rs.getTime("DIFERENCIA"));
                r.setEstado(rs.getInt("ESTADO"));
                r.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));

                return r;
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    // Para reporte : ruta por vehiculo
    // Ruta debe existir y estar activa 
    // Consulta informacion (de identificacion y tiempo) para punto base-llegada
    // asociado a un registro de informacion registradora
    public static RutaXVehiculo getBaseLlegada(int idir, int idRuta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " IFNULL(r.NOMBRE, 'NA') AS NOMBRE_RUTA,"
                + " b.PK_BASE AS BASE_PK,"
                + " b.NOMBRE AS NOMBRE_PUNTO,"
                + " SEC_TO_TIME((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + rp.PROMEDIO_MINUTOS * 60)) AS HORA_PLANIFICADA,"
                + " ir.HORA_INGRESO AS HORA_REAL_LLEGADA,"
                + " SEC_TO_TIME(ABS((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + rp.PROMEDIO_MINUTOS * 60) - TIME_TO_SEC(ir.HORA_INGRESO))) AS DIFERENCIA,"
                + " IF(TIME_TO_SEC(ir.HORA_INGRESO) < ((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + (rp.PROMEDIO_MINUTOS * 60)) - (rp.HOLGURA_MINUTOS * 60)), 2,"
                + " IF(TIME_TO_SEC(ir.HORA_INGRESO) > ((TIME_TO_SEC(ir.HORA_SALIDA_BASE_SALIDA) + (rp.PROMEDIO_MINUTOS * 60)) + (rp.HOLGURA_MINUTOS * 60)), 1, 0)) AS ESTADO,"
                + " ir.NUM_LLEGADA AS NUMERACION,"
                + " ir.NUMERO_VUELTA AS NUMERO_VUELTA"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_base AS b ON"
                + "   ir.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " INNER JOIN tbl_ruta_punto AS rp ON"
                + "   rp.FK_RUTA = ir.FK_RUTA AND rp.FK_PUNTO = 100"
                + " LEFT JOIN tbl_ruta AS r ON"
                + "   ir.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " WHERE ir.PK_INFORMACION_REGISTRADORA = ? AND ir.FK_RUTA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idir);
            ps.setInt(2, idRuta);

            rs = ps.executeQuery();

            if (rs.next()) {
                RutaXVehiculo r = new RutaXVehiculo();

                r.setRuta(rs.getString("NOMBRE_RUTA"));
                r.setIdPunto(-2); // BASE_LLEGADA
                r.setPuntoControl(rs.getString("NOMBRE_PUNTO"));
                r.setNumeracion(rs.getInt("NUMERACION"));
                r.setHoraPlanificada(rs.getTime("HORA_PLANIFICADA"));
                r.setHoraRealLlegada(rs.getTime("HORA_REAL_LLEGADA"));
                r.setDiferenciaTiempo(rs.getTime("DIFERENCIA"));
                r.setEstado(rs.getInt("ESTADO"));
                r.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));

                return r;
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    // Comprueba si existe un registro para un conjunto de valores
    // asociados a una vuelta en tabla informacion registradora.    
    public static boolean existeVuelta(InformacionRegistradora ir) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_informacion_registradora AS ir WHERE"
                + " ir.FK_VEHICULO = ? AND "
                + " ir.FECHA_INGRESO = ? AND"
                + " ir.HORA_INGRESO = ? AND"
                + " ir.NUM_LLEGADA = ? AND"
                + " ir.NUMERACION_BASE_SALIDA = ? AND"
                + " ir.FK_BASE = ? AND"
                + " ir.FK_BASE_SALIDA = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ir.getIdVehiculo());
            ps.setDate(2, new java.sql.Date(ir.getFechaIngreso().getTime()));
            ps.setTime(3, new java.sql.Time(ir.getHoraIngreso().getTime()));
            ps.setInt(4, ir.getNumeroLlegada());
            ps.setInt(5, ir.getNumeracionBS());
            ps.setInt(6, ir.getIdBase());
            ps.setInt(7, ir.getIdBaseSalida());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Comprueba si existe registro de vuelta con vehiculo, fecha y numeracion
    // de llegada superior en tabla informacion registradora.
    public static boolean existeVueltaPorNumeracion(int id_movil, String fecha, long numeracion) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT ir.PK_INFORMACION_REGISTRADORA"
                    + " FROM tbl_informacion_registradora AS ir WHERE"
                    + " ir.FK_VEHICULO = ? AND"
                    + " ir.FECHA_INGRESO = ? AND"
                    + " ir.NUM_LLEGADA > ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_movil);
            ps.setString(2, fecha);
            ps.setLong(3, numeracion);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return true;
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }        

    // Consulta numero de ultima vuelta para un vehiculo y fecha en
    // especifico.
    public static int numeroUltimaVuelta(InformacionRegistradora ir) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT max(ir.NUMERO_VUELTA) AS ULTIMA_VUELTA"
                + " FROM tbl_informacion_registradora AS ir WHERE"
                + " ir.FK_VEHICULO = ? AND"
                + " ir.FECHA_INGRESO = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ir.getIdVehiculo());
            ps.setDate(2, new java.sql.Date(ir.getFechaIngreso().getTime()));
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ULTIMA_VUELTA");
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return 0;
    }

    // Consulta vehiculos asociados (circulan) a una ruta y fecha
    // en especifico.
    public static ArrayList<Movil> movilesEnRuta(int idRuta, String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " v.PLACA"
                + " FROM tbl_informacion_registradora AS ir"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " WHERE ir.FK_RUTA = ? AND ir.FECHA_INGRESO = ?"
                + " GROUP BY ir.FK_VEHICULO";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ps.setString(2, fecha);
            rs = ps.executeQuery();

            ArrayList<Movil> lst = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA"));
                lst.add(m);
            }
            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }
        return null;
    }
    
    // Consulta asociacion/circulacion de vehiculos en rutas en una fecha
    // en especifico.
    public static ArrayList<MovilRuta> movilesEnRuta(String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT"
                    + " FECHA_INGRESO,"
                    + " FK_VEHICULO,"
                    + " v.PLACA,"
                    + " v.NUM_INTERNO AS NUMERO_INTERNO,"
                    + " FK_RUTA,"
                    + " r.NOMBRE AS NOMBRE_RUTA"
                    + " FROM tbl_informacion_registradora"
                    + " INNER JOIN tbl_ruta AS r ON"
                    + "         FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                    + " INNER JOIN tbl_vehiculo AS v ON"
                    + "         FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                    + " WHERE FECHA_INGRESO = ?"
                    + " AND FK_RUTA IS NOT NULL"
                    + " GROUP BY FECHA_INGRESO, FK_VEHICULO";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
        
            ArrayList<MovilRuta> lst = new ArrayList<MovilRuta>();
            while (rs.next()) {
                MovilRuta mr = new MovilRuta();
                mr.setFecha(rs.getString("FECHA_INGRESO"));
                mr.setIdRuta(rs.getInt("FK_RUTA"));
                mr.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                mr.setIdMovil(rs.getInt("FK_VEHICULO"));
                mr.setPlaca(rs.getString("PLACA"));
                mr.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                lst.add(mr);
            }
            return lst;
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    // Consulta asociacion/circulacion de vehiculos en rutas en una fecha y 
    // propietario especifico.
    public static ArrayList<Movil> movilesEnRutaPropietario(int idPropietario, int idRuta, String fecha) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT t0.PLACA FROM"
                + " ("
                + "   SELECT"
                + "     v.PLACA , ir.FK_VEHICULO"
                + "     FROM tbl_informacion_registradora AS ir"
                + "     INNER JOIN tbl_vehiculo AS v "
                + "	ON"
                + "         ir.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + "         WHERE ir.FK_RUTA = ? AND ir.FECHA_INGRESO = ?"
                + "         GROUP BY ir.FK_VEHICULO"
                + "  ) t0"
                + "    INNER JOIN "
                + "    ("
                + "         SELECT fk_vehiculo FROM tbl_propietario_vehiculo WHERE fk_propietario = ? AND activa = 1 AND estado = 1"
                + "    ) t1"
                + "     ON t0.FK_VEHICULO = t1.fk_vehiculo";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRuta);
            ps.setString(2, fecha);
            ps.setInt(3, idPropietario);
            rs = ps.executeQuery();

            ArrayList<Movil> lst = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA"));
                lst.add(m);
            }
            return lst;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    // Verifica si cadena de texto pasada como parametro
    // corresponde con cadena cifrada y almacenada.
    public static boolean verificarAcceso(String acceso) {

        ConexionExterna conext = new ConexionExterna();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " PK_VALIDACION"
                + " FROM tbl_validacion AS v WHERE v.CADENA = sha2(?,256)";

        try {
            con = conext.conectar_validacion();
            ps = con.prepareStatement(sql);
            ps.setString(1, acceso);

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            conext.desconectar_validacion(con);
        }
        return false;
    }

    // Consulta primer registro de cadena cifrada almacenada,
    // ignora cualquier registro posterior.
    public static String cadenaDeAcceso() {

        ConexionExterna conext = new ConexionExterna();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT CADENA FROM tbl_validacion LIMIT 1";

        try {
            con = conext.conectar_validacion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("CADENA");
            }
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            conext.desconectar_validacion(con);
        }
        return null;
    }

    // Genera a traves de consulta SQL cadena cifrada
    // funcion sha2(cadena_legible, bits)
    // relacionada a una cadena de texto legible.
    public static String cadenaDeAccesoGenerada(String str) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT sha2(?,256) as CADENA";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, str);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("CADENA");
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static ArrayList<InformacionRegistradora> getLstinforeg() {
        return lstinforeg;
    }
}
