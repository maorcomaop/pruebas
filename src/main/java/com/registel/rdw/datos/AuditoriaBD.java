/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Motivo;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.Ciudad;
import com.registel.rdw.logica.TipoDocumento;
import com.registel.rdw.logica.AuditoriaAlarma;
import com.registel.rdw.logica.AuditoriaCategorias;
import com.registel.rdw.logica.AuditoriaConductor;
import com.registel.rdw.logica.AuditoriaEmpresa;
import com.registel.rdw.logica.AuditoriaInformacionRegistradora;
import com.registel.rdw.logica.AuditoriaLiquidacionGeneral;
import com.registel.rdw.logica.AuditorialLiquidacionGeneral;
import com.registel.rdw.logica.AuditoriaPerfil;
import com.registel.rdw.logica.AuditoriaPunto;
import com.registel.rdw.logica.AuditoriaPuntoDeControl;
import com.registel.rdw.logica.AuditoriaRuta;
import com.registel.rdw.logica.AuditoriaRutaPunto;
import com.registel.rdw.logica.AuditoriaTarifa;
import com.registel.rdw.logica.AuditoriaUsuario;
import com.registel.rdw.logica.AuditoriaVehiculo;

import com.registel.rdw.logica.Columna;
import com.registel.rdw.logica.ServicioLocal;
import com.registel.rdw.logica.Movil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lider_desarrollador
 */
public class AuditoriaBD {

    /*FUNCION QUE PERMITE OBTENER EL NOMBRE DE LAS COLUMNAS DE UNA TABLA*/
    public static ArrayList<String> labelColumnsTable(String nameTable, String condicion) throws ExisteRegistroBD, SQLException {

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int inicio = con.getMetaData().getURL().indexOf("6");
        int fin = con.getMetaData().getURL().indexOf("?");
        String url = con.getMetaData().getURL().substring((inicio+2), fin);
        ArrayList listadoDeEtiquetas = new ArrayList();
        Columna unaColumna;

        StringBuilder sql = new StringBuilder();
        sql.append("SHOW COLUMNS FROM ");
        sql.append(nameTable);
        sql.append(" FROM ");        
        sql.append( url );        
        sql.append(condicion);
        //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaColumna = new Columna();
                if (!rs.getString("Field").startsWith("FECHA") && rs.getString("Field").endsWith("_NEW")) {
                    unaColumna.setNombreColumna(rs.getString("Field").replace("_NEW", "").replace("_", " "));
                    listadoDeEtiquetas.add(unaColumna);
                }
                if (!rs.getString("Field").startsWith("FECHA") && rs.getString("Field").endsWith("_New")) {
                    unaColumna.setNombreColumna(rs.getString("Field").replace("_NEW", "").replace("_", " "));
                    listadoDeEtiquetas.add(unaColumna);
                }
                if (rs.getString("Field").startsWith("FECHA") && rs.getString("Field").endsWith("_NEW")) {
                    unaColumna.setNombreColumna(rs.getString("Field").replace("_NEW", "").replace("_", " "));
                    listadoDeEtiquetas.add(unaColumna);
                }
                if (rs.getString("Field").endsWith("EVENTO")) {
                    unaColumna.setNombreColumna(rs.getString("Field"));
                    listadoDeEtiquetas.add(unaColumna);
                }
                /*if (rs.getString("Field").startsWith("ESTADO") && rs.getString("Field").endsWith("_NEW")) {
                    unaColumna.setNombreColumna(rs.getString("Field").replace("_NEW", "").replace("_", " "));
                    listadoDeEtiquetas.add(unaColumna);
                }*/
                /*if (rs.getString("Field").equals("ESTADO_NEW")) {
                    unaColumna.setNombreColumna(rs.getString("Field").replace("_NEW", "").replace("_", " "));
                    listadoDeEtiquetas.add(unaColumna);
                }*/
                if (rs.getString("Field").equals("ESTADO")) {
                    unaColumna.setNombreColumna(rs.getString("Field"));
                    listadoDeEtiquetas.add(unaColumna);
                }
                /*if (rs.getString("Field").startsWith("ESTADO") && !rs.getString("Field").endsWith("_NEW")) {
                    if (!rs.getString("Field").endsWith("_OLD")) {
                    unaColumna.setNombreColumna(rs.getString("Field"));
                    listadoDeEtiquetas.add(unaColumna);}
                }*/
                if (rs.getString("Field").startsWith("USUARIO") ) {
                    unaColumna.setNombreColumna(rs.getString("Field"));
                    listadoDeEtiquetas.add(unaColumna);
                }
                if (rs.getString("Field").startsWith("FK_V") ) {
                    unaColumna.setNombreColumna(rs.getString("Field"));
                    listadoDeEtiquetas.add(unaColumna);
                }
                if (rs.getString("Field").startsWith("FK_P") && rs.getString("Field").endsWith("_NEW")) {
                    unaColumna.setNombreColumna(rs.getString("Field").replace("_NEW", "").replace("_", " "));
                    listadoDeEtiquetas.add(unaColumna);
                }
                
                /*if (rs.getString("Field").contains("USUARIOBD")) {
                    unaColumna.setNombreColumna(rs.getString("Field"));
                    listadoDeEtiquetas.add(unaColumna);
                }*/
                
            }
            return listadoDeEtiquetas;
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
    
    /*FUNCION QUE EXTRAE TODOS LOS REGISTROS DE LA TABLA AUDITORIAS*/
    public static ArrayList selectAllConductor(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaConductor unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(nameTable);
        sql.append(" WHERE FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");        
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
        switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaAuditoria = new AuditoriaConductor();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_CONDUCTOR"));
                unaAuditoria.setFk(rs.getInt("FK_CONDUCTOR"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /**
                 * **********************************
                 */
                unaAuditoria.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                unaAuditoria.setNuevoApellido(rs.getString("APELLIDO_NEW"));
                unaAuditoria.setNuevoCedula(rs.getString("CEDULA_NEW"));
                /**
                 * ****************************
                 */                
                
                unaAuditoria.setEstado(rs.getInt("ESTADO"));
                
                
                /*OBTIENE EL NOMBRE DEL USUARIO*/
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }    
                }
                
                /*OBTIENE EL TIPO DE DOCUMENTO*/
                TipoDocumento c = new TipoDocumento();
                if (rs.getInt("TIPO_DOC_NEW")>0) {
                    c.setId(rs.getInt("TIPO_DOC_NEW"));
                TipoDocumento r = tipoDocumentoBD.selectByOneId(c);
                unaAuditoria.setNuevoTipoDocumento(r.getTipo());
                }
                
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllAlarma(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaAlarma unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(nameTable);
        sql.append(" WHERE FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
        switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        
        System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaAuditoria = new AuditoriaAlarma();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_ALARMA"));
                unaAuditoria.setFk(rs.getInt("FK_ALARMA"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /**
                 * **********************************
                 */
                unaAuditoria.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                unaAuditoria.setNuevoTipo(rs.getString("TIPO_NEW"));
                unaAuditoria.setNuevoUnidadMedicion(rs.getString("UNIDAD_MEDICION_NEW"));
                unaAuditoria.setNuevoPrioridad(rs.getString("PRIORIDAD_NEW"));
                /**
                 * ****************************
                 */
                unaAuditoria.setEstado(rs.getInt("ESTADO"));                
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }    
                }
                
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllCategorias(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        ArrayList<String> changeRecord = new ArrayList<>();
        
        Map<String, String> change = new HashMap<String, String>();
        
        AuditoriaCategorias unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(nameTable);
        sql.append(" WHERE FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
        switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaAuditoria = new AuditoriaCategorias();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_CATEGORIA_DESCUENTOS"));
                unaAuditoria.setFk(rs.getInt("FK_TBL_CATEGORIA"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /***********************************/                
                unaAuditoria.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                unaAuditoria.setNuevoDescripcion(rs.getString("DESCRIPCION_NEW"));
                unaAuditoria.setNuevoAplicaDescuento(rs.getString("APLICA_DESCUENTO_NEW"));
                unaAuditoria.setNuevoAplicaGeneral(rs.getString("APLICA_GENERAL_NEW"));
                unaAuditoria.setNuevoEsValorMoneda(rs.getString("ES_VALOR_MONEDA_NEW"));
                unaAuditoria.setNuevoEsPocentaje(rs.getString("ES_PORCENTAJE_NEW"));
                unaAuditoria.setNuevoEsFija(rs.getString("ES_FIJA_NEW"));
                unaAuditoria.setNuevoValor(rs.getString("VALOR_NEW"));                
                unaAuditoria.setEstado(rs.getInt("ESTADO"));       
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }    
                }
                 
                unaAuditoria.setUsuarioBD(rs.getString("USUARIOBD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllEmpresas(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaEmpresa unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(nameTable);
        sql.append(" WHERE FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
        switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();
            Ciudad c = new Ciudad();
            while (rs.next()) {
                unaAuditoria = new AuditoriaEmpresa();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_EMPRESA"));
                unaAuditoria.setFk(rs.getInt("FK_EMPRESA"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /**
                 * **********************************
                 */
                
                unaAuditoria.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                unaAuditoria.setNuevoFkCiudad(rs.getString("FK_CIUDAD_NEW"));
                c.setId( Integer.parseInt(rs.getString("FK_CIUDAD_NEW")) );
                Ciudad selectByOne = CiudadBD.selectByOne(c);
                unaAuditoria.setNuevoFkCiudad(selectByOne.getNombre());
                unaAuditoria.setNuevoNit(rs.getString("NIT_NEW"));                
                /**
                 * ****************************
                 */
                unaAuditoria.setEstado(rs.getInt("ESTADO"));    
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }   
                }
                 
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllInformacionRegistradora(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaInformacionRegistradora unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT air.* FROM ");
        sql.append(nameTable).append(" as air INNER JOIN tbl_motivo as m ON air.PK_AUDITORIA_INFORMACION_REGISTRADORA= m.FK_AUDITORIA ");
        sql.append(" WHERE air.FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
        switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY air.FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY air.FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY air.FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        //System.out.println(sql.toString());
        Movil a = new Movil();
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                    unaAuditoria = new AuditoriaInformacionRegistradora();                
                    unaAuditoria.setId(rs.getInt("PK_AUDITORIA_INFORMACION_REGISTRADORA"));                    
                    unaAuditoria.setFk(rs.getInt("FK_INFORMACION_REGISTRADORA"));
                    if (rs.getInt("FK_VEHICULO") > 0) {
                    a.setId(rs.getInt("FK_VEHICULO"));
                    a.setEstado(1);
                    Movil s = MovilBD.selectByOneView(a);
                    unaAuditoria.setVehiculo(s.getPlaca());
                    }
                    else{
                        unaAuditoria.setVehiculo("No aplica");
                    }
                
                    unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                    /**
                     * **********************************
                     */
                    unaAuditoria.setNuevoNumeroVuelta(rs.getString("NUMERO_VUELTA_NEW"));
                    unaAuditoria.setNuevoNumeroVueltaAnterior(rs.getString("NUM_VUELTA_ANT_NEW"));
                    unaAuditoria.setNuevoNumeroLlegada(rs.getString("NUM_LLEGADAL_NEW"));
                    unaAuditoria.setNuevoDiferenciaNumerica(rs.getString("DIFERENCIA_NUM_NEW"));
                    unaAuditoria.setNuevoEntradas(rs.getString("ENTRADAS_NEW"));
                    unaAuditoria.setNuevoDiferenciaEntrada(rs.getString("DIFERENCIA_ENTRADA_NEW"));
                    unaAuditoria.setNuevoSalidas(rs.getString("SALIDAS_NEW"));
                    unaAuditoria.setNuevoDiferenciaSalida(rs.getString("DIFERENCIA_SALIDA_NEW"));

                    if (rs.getInt("fk_ruta_New") > 0) {
                    unaAuditoria.setNuevoFkRuta(rs.getString("fk_ruta_New"));                
                    }
                    else{
                        unaAuditoria.setNuevoFkRuta("no aplica");
                    }                
                    unaAuditoria.setNuevoNumeracionBaseSalida(rs.getString("NUMERACION_BASE_SALIDA_NEW"));                
                    unaAuditoria.setNuevoEntradasBaseSalida(rs.getString("ENTRADAS_BASE_SALIDA_NEW"));                                
                    unaAuditoria.setNuevoSalidasBaseSalida(rs.getString("SALIDAS_BASE_SALIDAS_NEW"));
                    unaAuditoria.setNuevoFechaIngreso(rs.getString("FECHA_INGRESO_NEW"));
                    unaAuditoria.setNuevoHoraIngreso(rs.getString("HORA_INGRESO_NEW"));
                    unaAuditoria.setNuevoFechaSalida(rs.getString("FECHA_SALIDA_NEW"));                                             
                    unaAuditoria.setNuevoHoraSalida(rs.getString("HORA_SALIDA_NEW"));
                    unaAuditoria.setNuevoTotalDia(rs.getString("TOTAL_DIA_NEW"));    
                    if (rs.getInt("USUARIO")>0) {
                    Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                     if (u != null) {
                         unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                     }
                     else
                     {
                         unaAuditoria.setUsuario("No Aplica");
                     }
                }
                    
                    unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                    unaAuditoria.setEstado(rs.getInt("ESTADO"));
                    listadoDeAuditorias.add(unaAuditoria);
               
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllLiquidacion(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeLiquidacion = new ArrayList();
        AuditorialLiquidacionGeneral unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT alq.* FROM ");
        sql.append(nameTable).append(" as alq INNER JOIN tbl_motivo as m ON alq.PK_AUDITORIA_LIQUIDACION_GENERAL= m.FK_AUDITORIA ");
        sql.append(" WHERE alq.FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
        //System.out.println(">>> "+sql.toString());
       switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY alq.FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY alq.FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY alq.FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        
        Movil a = new Movil();
        
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                unaAuditoria = new AuditorialLiquidacionGeneral();
               unaAuditoria.setId(rs.getInt("PK_AUDITORIA_LIQUIDACION_GENERAL"));
                unaAuditoria.setFk(rs.getInt("FK_TBL_LIQUIDACION_GENERAL"));                               
                
                /*******************************************************/
                unaAuditoria.setFk_tarifa_fija(rs.getInt("FK_TARIFA_FIJA"));                               
                unaAuditoria.setFk_vehiculo(rs.getInt("FK_VEHICULO"));  
                if (rs.getInt("FK_VEHICULO")>0) {
                    a.setId(rs.getInt("FK_VEHICULO"));
                a.setEstado(1);
                Movil s = MovilBD.selectByOneView(a);
                unaAuditoria.setVehiculo(s.getPlaca());
                }                
                
                unaAuditoria.setFk_conductor(rs.getInt("FK_CONDUCTOR"));                                               
                unaAuditoria.setTotal_pasajeros_liquidados_nuevo(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS_NEW"));                 
                unaAuditoria.setTotal_valor_vueltas_nuevo(rs.getInt("TOTAL_VALOR_VUELTAS_NEW"));                               
                unaAuditoria.setTotal_valor_descuento_pasajeros_nuevo(rs.getInt("TOTAL_VALOR_DESCUENTO_PASAJEROS_NEW"));                               
                unaAuditoria.setTotal_valor_descuento_adicional_nuevo(rs.getInt("TOTAL_VALOR_DESCUENTO_ADICIONAL_NEW"));                                               
                /**********************************************************/
                unaAuditoria.setFechaLiquidacion(rs.getDate("FECHA_LIQUIDACION"));                               
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                unaAuditoria.setUsuarioBD(rs.getString("USUARIOBD"));
                if (rs.getInt("USUARIO_REGISDATA")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO_REGISDATA"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }   
                }
                   
                unaAuditoria.setEstado_nuevo(rs.getInt("ESTADO_NEW"));
               
                
                listadoDeLiquidacion.add(unaAuditoria);
            }
            return listadoDeLiquidacion;
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
    public static ArrayList selectAllPerfil(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaPerfil unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(nameTable);
        sql.append(" WHERE FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");        
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
       switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaAuditoria = new AuditoriaPerfil();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_PERFIL"));
                unaAuditoria.setFk(rs.getInt("FK_PERFIL"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /**
                 * **********************************
                 */
                unaAuditoria.setNuevoNombrePerfil(rs.getString("NOMBRE_PERFIL_NEW"));
                unaAuditoria.setNuevoDescripcion(rs.getString("DESCRIPCION_NEW"));                
                /**
                 * ****************************
                 */
                unaAuditoria.setEstado(rs.getInt("ESTADO"));  
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }    
                }
                  
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllPunto(String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaPunto unaAuditoria;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(nameTable);
        sql.append(" WHERE FECHA_EVENTO BETWEEN '");
        sql.append(fecha_inicio);
        sql.append(" 00:00:00");
        sql.append("' AND '");
        sql.append(fecha_fin);
        sql.append(" 23:59:59'");
 switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                unaAuditoria = new AuditoriaPunto();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_PUNTO"));
                unaAuditoria.setFk(rs.getInt("FK_PUNTO"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /**
                 * **********************************
                 */
                unaAuditoria.setNuevoNombrePunto(rs.getString("NOMBRE_NEW"));
                unaAuditoria.setNuevoDescripcion(rs.getString("DESCRIPCION_NEW"));
                unaAuditoria.setNuevoLatitud(rs.getString("LATITUD_NEW"));
                unaAuditoria.setNuevoLongitud(rs.getString("LONGITUD_NEW"));                
                /**
                 * ****************************
                 */
                unaAuditoria.setEstado(rs.getInt("ESTADO"));  
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }   
                }
                  
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                
                listadoDeAuditorias.add(unaAuditoria);
            }
            return listadoDeAuditorias;
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
    public static ArrayList selectAllRuta (String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaRuta unaAuditoria;
                 
        StringBuilder sql= new StringBuilder();
        sql.append( "SELECT * FROM " );
        sql.append( nameTable );
        sql.append( " WHERE FECHA_EVENTO BETWEEN '" );
        sql.append( fecha_inicio );
        sql.append(" 00:00:00");
        sql.append( "' AND '" );
        sql.append( fecha_fin );
        sql.append(" 23:59:59'");
         switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
        
            //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());            
            rs = ps.executeQuery();
                    
            while (rs.next())
            {
                unaAuditoria = new AuditoriaRuta();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_RUTA"));
                unaAuditoria.setFk(rs.getInt("FK_RUTA"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /*************************************/
                unaAuditoria.setNuevoNombreRuta(rs.getString("NOMBRE_NEW"));                
                /*******************************/
                unaAuditoria.setAntiguoNombreRuta(rs.getString("NOMBRE_OLD"));
                /*******************************/
                unaAuditoria.setEstado(rs.getInt("ESTADO"));                                
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }    
                }
                  
                listadoDeAuditorias.add(unaAuditoria);
            }
             return listadoDeAuditorias;
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
    public static ArrayList selectAllTarifa (String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaTarifa unaAuditoria;
                 
        StringBuilder sql= new StringBuilder();
        sql.append( "SELECT * FROM " );
        sql.append( nameTable );
        sql.append( " WHERE FECHA_EVENTO BETWEEN '" );
        sql.append( fecha_inicio );
        sql.append(" 00:00:00");
        sql.append( "' AND '" );
        sql.append( fecha_fin );
        sql.append(" 23:59:59'");
         switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
            //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());            
            rs = ps.executeQuery();
                    
            while (rs.next())
            {
                unaAuditoria = new AuditoriaTarifa();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_TARIFA"));                
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /*********************************************************************/
                unaAuditoria.setNuevoNombreTarifa(rs.getString("NOMBRE_TARIFA_NEW"));                
                unaAuditoria.setNuevoTarifaActiva(rs.getInt("TARIFA_ACTIVA_NEW"));   
                unaAuditoria.setNuevoValorTarifa(rs.getDouble("VALOR_NEW"));                                 
                /**********************************************************************/
                unaAuditoria.setEstado(rs.getInt("ESTADO"));                                
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }    
                }
                 
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
             return listadoDeAuditorias;
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
    public static ArrayList selectAllUsuario (String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList listadoDeAuditorias = new ArrayList();
           AuditoriaUsuario unaAuditoria;
                 
        StringBuilder sql= new StringBuilder();
        sql.append( "SELECT * FROM " );
        sql.append( nameTable );
        sql.append( " WHERE FECHA_EVENTO BETWEEN '" );
        sql.append( fecha_inicio );
        sql.append(" 00:00:00");
        sql.append( "' AND '" );
        sql.append( fecha_fin );
        sql.append(" 23:59:59'");
         switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
            //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());            
            rs = ps.executeQuery();
                    
            Perfil p = new Perfil();
            
            while (rs.next())
            {
                unaAuditoria = new AuditoriaUsuario();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_USUARIO"));                
                unaAuditoria.setFk(rs.getInt("FK_USUARIO"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));                
                /*************************************/
                unaAuditoria.setNuevoFkPerfil(rs.getInt("FK_PERFIL_NEW"));
                if (rs.getInt("FK_PERFIL_NEW")>0) {
                p.setId(rs.getInt("FK_PERFIL_NEW"));
                p.setEstado(1);
                Perfil s = PerfilBD.selectByOne(p);
                if (s != null) {
                    unaAuditoria.setNuevoperfil(s.getNombre());
                }
                else
                {
                    unaAuditoria.setNuevoperfil("No Aplica");
                }    
                }
                   
                                  
                
                unaAuditoria.setNuevoCedula(rs.getString("CEDULA_NEW"));
                unaAuditoria.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                unaAuditoria.setNuevoApellido(rs.getString("APELLIDO_NEW"));
                unaAuditoria.setNuevoEmail(rs.getString("EMAIL_NEW"));
                unaAuditoria.setNuevoLogin(rs.getString("LOGIN_NEW"));
                unaAuditoria.setConexion(rs.getInt("ESTADO_CONEXION_OLD"));
                
                /*******************************/
                unaAuditoria.setEstado(rs.getInt("ESTADO"));
                if (rs.getInt("USUARIO") >0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }   
                }
                   
                
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
             return listadoDeAuditorias;
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
    public static ArrayList selectAllVehiculo (String fecha_inicio, String fecha_fin, String nameTable, String cantidad) throws ExisteRegistroBD, SQLException{
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList listadoDeAuditorias = new ArrayList();
        AuditoriaVehiculo unaAuditoria;
                 
        StringBuilder sql= new StringBuilder();
        sql.append( "SELECT * FROM " );
        sql.append( nameTable );
        sql.append( " WHERE FECHA_EVENTO BETWEEN '" );
       sql.append( fecha_inicio );
        sql.append(" 00:00:00");
        sql.append( "' AND '" );
        sql.append( fecha_fin );
        sql.append(" 23:59:59'");
        switch (Integer.parseInt(cantidad)) {
            case 0: {
                sql.append(" ORDER BY FECHA_EVENTO ASC;");
                break;
            }
            case 100: {
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            case 200: {                
                sql.append(" ORDER BY FECHA_EVENTO ASC ").append(" LIMIT 0, ").append(cantidad).append(";");
                break;
            }
            default: {
                break;
            }
        }
            //System.out.println(sql.toString());
        try {
            ps = con.prepareCall(sql.toString());            
            rs = ps.executeQuery();
                    
            while (rs.next())
            {
                unaAuditoria = new AuditoriaVehiculo();
                unaAuditoria.setId(rs.getInt("PK_AUDITORIA_VECHICULO"));
                unaAuditoria.setFk(rs.getInt("FK_VEHICULO"));
                unaAuditoria.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /*************************************/
                unaAuditoria.setNuevoPlaca(rs.getString("PLACA_NEW"));
                unaAuditoria.setNuevoNumeroInterno(rs.getString("NUM_INTERNO_NEW"));                
                /*******************************/
                unaAuditoria.setEstado(rs.getInt("ESTADO")); 
                if (rs.getInt("USUARIO")>0) {
                    
                }
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    unaAuditoria.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    unaAuditoria.setUsuario("No Aplica");
                }  
                unaAuditoria.setUsuarioBD(rs.getString("USUARIO_BD"));
                listadoDeAuditorias.add(unaAuditoria);
            }
             return listadoDeAuditorias;
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
   
    
    
    
    
    
    
    
    
    
    
    
    /*FUNCIONES QUE EXTRAEN SOLO UN ARCHIVO DE AUDITORIA*/
    public static AuditoriaAlarma selectByOneAlarma(AuditoriaAlarma au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaAlarma auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_ALARMA=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaAlarma();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_ALARMA"));
                auditoriaEncontrada.setFk(rs.getInt("FK_ALARMA"));
                auditoriaEncontrada.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                auditoriaEncontrada.setNuevoTipo(rs.getString("TIPO_NEW"));
                auditoriaEncontrada.setNuevoUnidadMedicion(rs.getString("UNIDAD_MEDICION_NEW"));
                auditoriaEncontrada.setNuevoPrioridad(rs.getString("PRIORIDAD_NEW"));                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoNombre(rs.getString("NOMBRE_OLD"));
                auditoriaEncontrada.setAntiguoTipo(rs.getString("TIPO_OLD"));
                auditoriaEncontrada.setAntiguoUnidadMedicion(rs.getString("UNIDAD_MEDICION_OLD"));
                auditoriaEncontrada.setAntiguoPrioridad(rs.getString("PRIORIDAD_OLD"));
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }    
                }
                 
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaCategorias selectByOneCategoria(AuditoriaCategorias au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaCategorias auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_CATEGORIA_DESCUENTOS=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaCategorias();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_CATEGORIA_DESCUENTOS"));
                auditoriaEncontrada.setFk(rs.getInt("FK_TBL_CATEGORIA"));                
                
                auditoriaEncontrada.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                auditoriaEncontrada.setNuevoDescripcion(rs.getString("DESCRIPCION_NEW"));
                auditoriaEncontrada.setNuevoAplicaDescuento(rs.getString("APLICA_DESCUENTO_NEW"));
                auditoriaEncontrada.setNuevoEsValorMoneda(rs.getString("ES_VALOR_MONEDA_NEW"));
                auditoriaEncontrada.setNuevoEsPocentaje(rs.getString("ES_PORCENTAJE_NEW"));
                auditoriaEncontrada.setNuevoEsFija(rs.getString("ES_FIJA_NEW"));
                auditoriaEncontrada.setNuevoValor(rs.getString("VALOR_NEW"));
                auditoriaEncontrada.setNuevoAplicaGeneral(rs.getString("APLICA_GENERAL_NEW"));                

                /*******************************************************/
                auditoriaEncontrada.setAntiguoNombre(rs.getString("NOMBRE_OLD"));
                auditoriaEncontrada.setAntiguoDescripcion(rs.getString("DESCRIPCION_OLD"));
                auditoriaEncontrada.setAntiguoAplicaDescuento(rs.getString("APLICA_DESCUENTO_OLD"));
                auditoriaEncontrada.setAntiguoEsValorMoneda(rs.getString("ES_VALOR_MONEDA_OLD"));
                auditoriaEncontrada.setAntiguoEsPocentaje(rs.getString("ES_PORCENTAJE_OLD"));
                auditoriaEncontrada.setAntiguoEsFija(rs.getString("ES_FIJA_OLD"));
                auditoriaEncontrada.setAntiguoValor(rs.getString("VALOR_OLD"));
                auditoriaEncontrada.setAntiguoAplicaGeneral(rs.getString("APLICA_GENERAL_OLD"));                

                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }   
                }
                  
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIOBD"));
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaEmpresa selectByOneEmpresa(AuditoriaEmpresa au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaEmpresa auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_EMPRESA=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaEmpresa();
            Ciudad c = new Ciudad();
            
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_EMPRESA"));
                auditoriaEncontrada.setFk(rs.getInt("FK_EMPRESA"));                
                
                auditoriaEncontrada.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                auditoriaEncontrada.setNuevoFkCiudad(rs.getString("FK_CIUDAD_NEW"));
                auditoriaEncontrada.setNuevoNit(rs.getString("NIT_NEW"));
                if (rs.getString("FK_CIUDAD_NEW") != null) {
                    c.setId( Integer.parseInt(rs.getString("FK_CIUDAD_NEW")) );
                    CiudadBD.selectByOne(c);
                    auditoriaEncontrada.setNuevoFkCiudad(c.getNombre());
                }
                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoNombre(rs.getString("NOMBRE"));
                auditoriaEncontrada.setAntiguoFkCiudad(rs.getString("FK_CIUDAD"));
                auditoriaEncontrada.setAntiguoNit(rs.getString("NIT"));
                c.setId( Integer.parseInt(rs.getString("FK_CIUDAD")) );
                Ciudad selectByOne = CiudadBD.selectByOne(c);
                auditoriaEncontrada.setAntiguoFkCiudad(selectByOne.getNombre());
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }    
                }
                 
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaConductor selectByOneConductor(AuditoriaConductor au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaConductor auditoriaEncontrada;

        StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_CONDUCTOR=?");        
        
        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaConductor();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_CONDUCTOR"));
                auditoriaEncontrada.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                auditoriaEncontrada.setNuevoApellido(rs.getString("APELLIDO_NEW"));
                auditoriaEncontrada.setNuevoCedula(rs.getString("CEDULA_NEW"));                
                auditoriaEncontrada.setNuevoTipoDocumento(rs.getString("TIPO_DOC_NEW"));
                
                if (rs.getInt("TIPO_DOC_NEW") > 0) {
                    TipoDocumento c = new TipoDocumento();
                    c.setId(rs.getInt("TIPO_DOC_NEW"));
                    TipoDocumento r = tipoDocumentoBD.selectByOneId(c);
                    auditoriaEncontrada.setNuevoTipoDocumento(r.getTipo());
                }
                
                /************************************************************************/
                auditoriaEncontrada.setAntiguoNombre(rs.getString("NOMBRE_OLD"));
                auditoriaEncontrada.setAntiguoApellido(rs.getString("APELLIDO_OLD"));
                auditoriaEncontrada.setAntiguoCedula(rs.getString("CEDULA_OLD"));                
                auditoriaEncontrada.setAntiguoTipoDocumento(rs.getString("TIPO_DOC_OLD"));
                
                if (rs.getInt("TIPO_DOC_OLD") > 0) {
                TipoDocumento c1 = new TipoDocumento();
                c1.setId(rs.getInt("TIPO_DOC_OLD"));
                TipoDocumento r1 = tipoDocumentoBD.selectByOneId(c1);
                auditoriaEncontrada.setAntiguoTipoDocumento(r1.getTipo());
                }
                /**************************************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));                
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaInformacionRegistradora selectByOneInformacionRegistradora(AuditoriaInformacionRegistradora au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaInformacionRegistradora auditoriaEncontrada;
        
        

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_INFORMACION_REGISTRADORA=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaInformacionRegistradora();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_INFORMACION_REGISTRADORA"));
                auditoriaEncontrada.setFk(rs.getInt("FK_INFORMACION_REGISTRADORA"));
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /**
                 * **********************************
                 */
                auditoriaEncontrada.setNuevoNumeroVuelta(rs.getString("NUMERO_VUELTA_NEW"));
                auditoriaEncontrada.setNuevoNumeroVueltaAnterior(rs.getString("NUM_VUELTA_ANT_NEW"));
                auditoriaEncontrada.setNuevoNumeroLlegada(rs.getString("NUM_LLEGADAL_NEW"));
                auditoriaEncontrada.setNuevoDiferenciaNumerica(rs.getString("DIFERENCIA_NUM_NEW"));
                auditoriaEncontrada.setNuevoEntradas(rs.getString("ENTRADAS_NEW"));
                auditoriaEncontrada.setNuevoDiferenciaEntrada(rs.getString("DIFERENCIA_ENTRADA_NEW"));
                auditoriaEncontrada.setNuevoSalidas(rs.getString("SALIDAS_NEW"));
                auditoriaEncontrada.setNuevoDiferenciaSalida(rs.getString("DIFERENCIA_SALIDA_NEW"));
                auditoriaEncontrada.setNuevoFkRuta(rs.getString("fk_ruta_New"));                
                auditoriaEncontrada.setNuevoNumeracionBaseSalida(rs.getString("NUMERACION_BASE_SALIDA_NEW"));                
                auditoriaEncontrada.setNuevoEntradasBaseSalida(rs.getString("ENTRADAS_BASE_SALIDA_NEW"));                                
                auditoriaEncontrada.setNuevoSalidasBaseSalida(rs.getString("SALIDAS_BASE_SALIDAS_NEW"));
                auditoriaEncontrada.setNuevoFechaIngreso(rs.getString("FECHA_INGRESO_NEW"));
                auditoriaEncontrada.setNuevoHoraIngreso(rs.getString("HORA_INGRESO_NEW"));
                auditoriaEncontrada.setNuevoFechaSalida(rs.getString("FECHA_SALIDA_NEW"));                                             
                auditoriaEncontrada.setNuevoHoraSalida(rs.getString("HORA_SALIDA_NEW"));
                auditoriaEncontrada.setNuevoTotalDia(rs.getString("TOTAL_DIA_OLD"));
                
                /**
                 * ****************************
                 */
                auditoriaEncontrada.setAntiguoNumeroVuelta(rs.getString("NUMERO_VUELTA_OLD"));
                auditoriaEncontrada.setAntiguoNumeroVueltaAnterior(rs.getString("NUM_VUELTA_ANT_OLD"));
                auditoriaEncontrada.setAntiguoNumeroLlegada(rs.getString("NUM_LLEGADA_OLD"));
                auditoriaEncontrada.setAntiguoDiferenciaNumerica(rs.getString("DIFERENCIA_NUM_OLD"));
                auditoriaEncontrada.setAntiguoEntradas(rs.getString("ENTRADAS_OLD"));
                auditoriaEncontrada.setAntiguoDiferenciaEntrada(rs.getString("DIFERENCIA_ENTRADA_OLD"));
                auditoriaEncontrada.setAntiguoSalidas(rs.getString("SALIDAS_OLD"));
                auditoriaEncontrada.setAntiguoDiferenciaSalida(rs.getString("DIFERENCIA_SALIDA_OLD"));
                auditoriaEncontrada.setAntiguoFkRuta(rs.getString("fk_ruta_Old"));                
                auditoriaEncontrada.setAntiguoNumeracionBaseSalida(rs.getString("NUMERACION_BASE_SALIDA_OLD"));                
                auditoriaEncontrada.setAntiguoEntradasBaseSalida(rs.getString("ENTRADAS_BASE_SALIDA_OLD"));                                
                auditoriaEncontrada.setAntiguoSalidasBaseSalida(rs.getString("SALIDAS_BASE_SALIDA_OLD"));
                auditoriaEncontrada.setAntiguoFechaIngreso(rs.getString("FECHA_INGRESO_OLD"));
                auditoriaEncontrada.setAntiguoHoraIngreso(rs.getString("HORA_INGRESO_OLD"));
                auditoriaEncontrada.setAntiguoFechaSalida(rs.getString("FECHA_SALIDA_OLD"));                                             
                auditoriaEncontrada.setAntiguoHoraSalida(rs.getString("HORA_SALIDA_OLD"));
                auditoriaEncontrada.setAntiguoTotalDia(rs.getString("TOTAL_DIA_OLD"));
                
                /**
                 * ****************************
                 */
               
               Motivo motivoEncontrado = MotivoBD.selectByOneAIR(auditoriaEncontrada);
               if (motivoEncontrado != null) {
                    auditoriaEncontrada.setMotivo( motivoEncontrado.getInformacionAdicional() );
                }else{
                   auditoriaEncontrada.setMotivo( "No aplica" );
               }
               //auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));        
                if (rs.getInt("USUARIO") > 0) {
                    Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                } 
                } 
               
               auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));
               
                             
               
               
            }
            return auditoriaEncontrada;
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
    public static AuditorialLiquidacionGeneral selectByOneLiquidacion(AuditorialLiquidacionGeneral au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditorialLiquidacionGeneral auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_LIQUIDACION_GENERAL=?");   
    Movil a = new Movil();
        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditorialLiquidacionGeneral();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_LIQUIDACION_GENERAL"));
                auditoriaEncontrada.setFk(rs.getInt("FK_TBL_LIQUIDACION_GENERAL"));                               
                
                /*******************************************************/
                auditoriaEncontrada.setFk_tarifa_fija(rs.getInt("FK_TARIFA_FIJA"));                               
                auditoriaEncontrada.setFk_vehiculo(rs.getInt("FK_VEHICULO"));  
                if (rs.getInt("FK_VEHICULO")>0) {
                    a.setId(rs.getInt("FK_VEHICULO"));
                a.setEstado(1);
                Movil s = MovilBD.selectByOneView(a);
                auditoriaEncontrada.setVehiculo(s.getPlaca());
                }
                
                
                auditoriaEncontrada.setFk_conductor(rs.getInt("FK_CONDUCTOR"));                               
                auditoriaEncontrada.setTotal_pasajeros_liquidados_antiguo(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS_OLD"));                               
                auditoriaEncontrada.setTotal_pasajeros_liquidados_nuevo(rs.getInt("TOTAL_PASAJEROS_LIQUIDADOS_NEW"));                  
                auditoriaEncontrada.setTotal_valor_vueltas_antiguo(rs.getInt("TOTAL_VALOR_VUELTAS_OLD"));                               
                auditoriaEncontrada.setTotal_valor_vueltas_nuevo(rs.getInt("TOTAL_VALOR_VUELTAS_NEW"));                               
                auditoriaEncontrada.setTotal_valor_descuento_pasajeros_antiguo(rs.getInt("TOTAL_VALOR_DESCUENTO_PASAJEROS_OLD"));                               
                auditoriaEncontrada.setTotal_valor_descuento_pasajeros_antiguo(rs.getInt("TOTAL_VALOR_DESCUENTO_PASAJEROS_NEW"));                               
                auditoriaEncontrada.setTotal_valor_descuento_adicional_antiguo(rs.getInt("TOTAL_VALOR_DESCUENTO_ADICIONAL_OLD"));                               
                auditoriaEncontrada.setTotal_valor_descuento_adicional_nuevo(rs.getInt("TOTAL_VALOR_DESCUENTO_ADICIONAL_NEW"));                                               
                /**********************************************************/
                                                
                Motivo motivoLQ = MotivoBD.selectByOneALQ(auditoriaEncontrada);
                if (motivoLQ != null) {
                    auditoriaEncontrada.setMotivo( motivoLQ.getInformacionAdicional() );
                }else{
                    auditoriaEncontrada.setMotivo( "No aplica" );
                }
                
                auditoriaEncontrada.setFechaLiquidacion(rs.getDate("FECHA_LIQUIDACION"));                               
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));                
                 if (rs.getInt("USUARIO_REGISDATA") > 0) {
                    Usuario u = UsuarioBD.getById(rs.getInt("USUARIO_REGISDATA"));                
                 if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }
                
                }
                
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIOBD"));
                auditoriaEncontrada.setEstado_antiguo(rs.getInt("ESTADO_OLD"));
                auditoriaEncontrada.setEstado_nuevo(rs.getInt("ESTADO_NEW"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaPerfil selectByOnePerfil(AuditoriaPerfil au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaPerfil auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_PERFIL=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaPerfil();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_PERFIL"));
                auditoriaEncontrada.setFk(rs.getInt("FK_PERFIL"));                
                
                auditoriaEncontrada.setNuevoNombrePerfil(rs.getString("NOMBRE_PERFIL_NEW"));                
                auditoriaEncontrada.setAntiguoDescripcion(rs.getString("DESCRIPCION_NEW"));                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoNombrePerfil(rs.getString("NOMBRE_PERFIL_OLD"));                
                auditoriaEncontrada.setAntiguoDescripcion(rs.getString("DESCRIPCION_OLD"));                
                
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }   
                }
                  
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaPunto selectByOnePunto(AuditoriaPunto au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaPunto auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_PUNTO=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaPunto();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_PUNTO"));
                auditoriaEncontrada.setFk(rs.getInt("FK_PUNTO"));                
                
                auditoriaEncontrada.setNuevoNombrePunto(rs.getString("NOMBRE_NEW"));                
                auditoriaEncontrada.setNuevoDescripcion(rs.getString("DESCRIPCION_NEW"));                
                auditoriaEncontrada.setNuevoLatitud(rs.getString("LATITUD_NEW"));                
                auditoriaEncontrada.setNuevoLongitud(rs.getString("LONGITUD_NEW"));                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoNombrePunto(rs.getString("NOMBRE_OLD"));                
                auditoriaEncontrada.setAntiguoDescripcion(rs.getString("DESCRIPCION_OLD"));                
                auditoriaEncontrada.setAntiguoLatitud(rs.getString("LATITUD_OLD"));                
                auditoriaEncontrada.setAntiguoLongitud(rs.getString("LONGITUD_OLD"));                                
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }   
                }
                  
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
            }
            return auditoriaEncontrada;
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
    public static AuditoriaPuntoDeControl selectByOnePuntoDeControl(AuditoriaPuntoDeControl au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaPuntoDeControl auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_PUNTO_CONTROL=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaPuntoDeControl();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_PUNTO_CONTROL"));
                auditoriaEncontrada.setFk(rs.getInt("FK_PUNTO_CONTROL"));                
                
                auditoriaEncontrada.setNuevoHoraPuntoDeControl(rs.getString("HORA_PTO_CONTROL_NEW"));                
                auditoriaEncontrada.setNuevoFechaPuntoDeControl(rs.getString("FECHA_PTO_CONTROL_NEW"));                
                auditoriaEncontrada.setNuevoNumeracion(rs.getString("NUMERACION_NEW"));                
                auditoriaEncontrada.setNuevoEntradas(rs.getString("ENTRADAS_NEW"));                
                auditoriaEncontrada.setNuevoSalidas(rs.getString("SALIDAS_NEW"));                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoHoraPuntoDeControl(rs.getString("HORA_PTO_CONTROL_OLD"));                
                auditoriaEncontrada.setAntiguoFechaPuntoDeControl(rs.getString("FECHA_PTO_CONTROL_OLD"));                
                auditoriaEncontrada.setAntiguoNumeracion(rs.getString("NUMERACION_OLD"));                
                auditoriaEncontrada.setAntiguoEntradas(rs.getString("ENTRADAS_OLD"));                
                auditoriaEncontrada.setAntiguoSalidas(rs.getString("SALIDAS_OLD"));                
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));  
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }   
                }
                  
            }
            return auditoriaEncontrada;
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
    public static AuditoriaRuta selectByOneRuta(AuditoriaRuta au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaRuta auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_RUTA=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaRuta();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_RUTA"));
                auditoriaEncontrada.setFk(rs.getInt("FK_RUTA"));                
                
                auditoriaEncontrada.setNuevoNombreRuta(rs.getString("NOMBRE_NEW"));                                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoNombreRuta(rs.getString("NOMBRE_OLD"));                                
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));
                if (rs.getInt("USUARIO")>0) {
                  Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }   
                }
                                
            }
            return auditoriaEncontrada;
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
    public static AuditoriaRutaPunto selectByOneRutaPunto(AuditoriaRutaPunto au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaRutaPunto auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_RUTA_PUNTO=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaRutaPunto();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_RUTA_PUNTO"));
                auditoriaEncontrada.setFk(rs.getInt("FK_RUTA_PUNTO"));                
                
                auditoriaEncontrada.setNuevoPromedioMinutos(rs.getString("PROMEDIO_MINUTOS_NEW"));     
                auditoriaEncontrada.setNuevoHolguraMinutos(rs.getString("HOLGURA_MINUTOS_NEW"));     
                
                /*******************************************************/
                auditoriaEncontrada.setAntiguoPromedioMinutos(rs.getString("PROMEDIO_MINUTOS_OLD"));     
                auditoriaEncontrada.setAntiguoHolguraMinutos(rs.getString("HOLGURA_MINUTOS_OLD"));     
                /**********************************************************/
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));  
                if (rs.getInt("USUARIO")>0) {
                 Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }   
                }
                  
            }
            return auditoriaEncontrada;
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
    public static AuditoriaTarifa selectByOneTarifa(AuditoriaTarifa au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaTarifa auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_TARIFA=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaTarifa();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_TARIFA"));                
                
                auditoriaEncontrada.setNuevoNombreTarifa(rs.getString("NOMBRE_TARIFA_NEW"));                                    
                auditoriaEncontrada.setNuevoValorTarifa(rs.getDouble("VALOR_NEW"));     
                auditoriaEncontrada.setNuevoTarifaActiva(rs.getInt("TARIFA_ACTIVA_NEW"));     
                /*******************************************************/
                auditoriaEncontrada.setNuevoNombreTarifa(rs.getString("NOMBRE_TARIFA_OLD"));                                    
                auditoriaEncontrada.setNuevoValorTarifa(rs.getDouble("VALOR_OLD"));     
                auditoriaEncontrada.setNuevoTarifaActiva(rs.getInt("TARIFA_ACTIVA_OLD"));     
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                 
                if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }    
                }
                 
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));                
            }
            return auditoriaEncontrada;
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
    public static AuditoriaUsuario selectByOneUsuario(AuditoriaUsuario au, String nameTable) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaUsuario auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_USUARIO=?");                  
        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            Perfil p_new = new Perfil();
            Perfil p_old = new Perfil();
            //System.out.println(sql.toString());
            auditoriaEncontrada = new AuditoriaUsuario();
            if (rs.next()) {
                 auditoriaEncontrada = new AuditoriaUsuario();
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_USUARIO"));                              
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /****************************************************************/
                auditoriaEncontrada.setNuevoFkPerfil(rs.getInt("FK_PERFIL_NEW")); 
                if (rs.getInt("FK_PERFIL_NEW")>0) {
                    p_new.setId(rs.getInt("FK_PERFIL_NEW"));
                    p_new.setEstado(1);
                    Perfil s1 = PerfilBD.selectByOne(p_new);
                    auditoriaEncontrada.setNuevoperfil(s1.getNombre());                
                }
                
                
                
                
                
                auditoriaEncontrada.setNuevoCedula(rs.getString("CEDULA_NEW"));
                auditoriaEncontrada.setNuevoNombre(rs.getString("NOMBRE_NEW"));
                auditoriaEncontrada.setNuevoApellido(rs.getString("APELLIDO_NEW"));
                auditoriaEncontrada.setNuevoEmail(rs.getString("EMAIL_NEW"));
                auditoriaEncontrada.setNuevoLogin(rs.getString("LOGIN_NEW"));                                
                auditoriaEncontrada.setNuevoEstadoConexion(rs.getInt("ESTADO_CONEXION_NEW"));
                auditoriaEncontrada.setFechaInicioSesion(rs.getDate("FECHA_INICIO_SESION"));
                
                
                
                /*******************************/
                if (rs.getInt("FK_PERFIL_OLD")>0) {
                    p_old.setId(rs.getInt("FK_PERFIL_OLD"));
                    p_old.setEstado(1);
                    Perfil s2 = PerfilBD.selectByOne(p_old);
                    auditoriaEncontrada.setAntiguoperfil(s2.getNombre());
                }
                
                
                auditoriaEncontrada.setAntiguoFkPerfil(rs.getInt("FK_PERFIL_OLD"));
                auditoriaEncontrada.setAntiguoCedula(rs.getString("CEDULA_OLD"));
                auditoriaEncontrada.setAntiguoNombre(rs.getString("NOMBRE_OLD"));
                auditoriaEncontrada.setAntiguoApellido(rs.getString("APELLIDO_OLD"));
                auditoriaEncontrada.setAntiguoEmail(rs.getString("EMAIL_OLD"));
                auditoriaEncontrada.setAntiguoLogin(rs.getString("LOGIN_OLD"));                
                auditoriaEncontrada.setFechaFinSesion(rs.getDate("FECHA_FIN_SESION"));
                auditoriaEncontrada.setAntiguoEstadoConexion(rs.getInt("ESTADO_CONEXION_OLD"));
                               
                /*******************************/
                
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
                
                
                if (rs.getInt("USUARIO") > 0) {
                    Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));
                    if (u != null) {
                        auditoriaEncontrada.setUsuario(u.getNombre() + " " + u.getApellido());
                    } else {
                        auditoriaEncontrada.setUsuario("No Aplica");
                    }
                }                 

                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));                           
            }            
          
            
            return auditoriaEncontrada;
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
    public static AuditoriaVehiculo selectByOneVehiculo(AuditoriaVehiculo au, String nameTable, String cond) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AuditoriaVehiculo auditoriaEncontrada;

         StringBuilder sql = new StringBuilder();       
        sql.append("SELECT * FROM ");
        sql.append(nameTable);        
        sql.append(" WHERE PK_AUDITORIA_VECHICULO=?");   

        try {
            ps = con.prepareCall(sql.toString());
            ps.setInt(1, au.getId());
            rs = ps.executeQuery();

            auditoriaEncontrada = new AuditoriaVehiculo();
            if (rs.next()) {
                auditoriaEncontrada.setId(rs.getInt("PK_AUDITORIA_VECHICULO"));
                auditoriaEncontrada.setFk(rs.getInt("FK_VEHICULO"));                
                auditoriaEncontrada.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                /*************************************/
                auditoriaEncontrada.setNuevoPlaca(rs.getString("PLACA_NEW"));
                auditoriaEncontrada.setNuevoNumeroInterno(rs.getString("NUM_INTERNO_NEW"));
                /*******************************/
                auditoriaEncontrada.setAntiguoPlaca(rs.getString("PLACA_OLD"));
                auditoriaEncontrada.setAntiguoNumeroInterno(rs.getString("NUM_INTERNO_OLD"));                
                /*******************************/
                auditoriaEncontrada.setEstado(rs.getInt("ESTADO"));
                //auditoriaEncontrada.setUsuario(rs.getInt("USUARIO"));     
                if (rs.getInt("USUARIO")>0) {
                Usuario u = UsuarioBD.getById(rs.getInt("USUARIO"));                
                 if (u != null) {
                    auditoriaEncontrada.setUsuario(u.getNombre()+" "+u.getApellido());
                }
                else
                {
                    auditoriaEncontrada.setUsuario("No Aplica");
                }    
                }
                 
                
                auditoriaEncontrada.setUsuarioBD(rs.getString("USUARIO_BD"));           
            }
            return auditoriaEncontrada;
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
    
    
     
    public static boolean updateEstado(AuditoriaAlarma au) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_auditoria_conductor ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_AUDITORIA_CONDUCTOR=? ");
        try {
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, au.getEstado());
            ps_update.setInt(2, au.getId());
            ps_update.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
            con.close();
        }

    }
    /*public static boolean updateEstadoMacroRuta(AuditorialLiquidacionGeneral au) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_auditoria_macro_ruta ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_AUDITORIA_MACRO_RUTA=? ");
        try {
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, au.getEstado_nuevo());
            ps_update.setInt(2, au.getId());
            ps_update.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
            con.close();
        }

    }*/
    
    
    public static boolean updateUserLiquidacion(AuditoriaLiquidacionGeneral alg) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_auditoria_liquidacion_general ");
        sql.append("SET USUARIO_REGISDATA=? ");
        sql.append("WHERE PK_AUDITORIA_LIQUIDACION_GENERAL=? ");
        try {
             con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, alg.getFkUsuario());
            ps.setInt(2, alg.getPkAuditoriaLiquidacionGeneral());
            int retorno = ps.executeUpdate();           
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
                UtilBD.closeStatement(ps);
                pila_con.liberarConexion(con);
            }        
        return false;
    }
    
    public static boolean updateUserInformacionRegistradora(AuditoriaInformacionRegistradora alg) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_auditoria_informacion_registradora ");
        sql.append("SET USUARIO=? ");
        sql.append("WHERE PK_AUDITORIA_INFORMACION_REGISTRADORA=? ");
        try {
             con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, alg.getFk_usuario());
            ps.setInt(2, alg.getId());
            int retorno = ps.executeUpdate();           
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
                UtilBD.closeStatement(ps);
                pila_con.liberarConexion(con);
            }        
        return false;
    }

   
}//FIN DE CLASE

