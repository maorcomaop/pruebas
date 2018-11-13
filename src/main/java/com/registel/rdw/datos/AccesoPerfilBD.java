/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Acceso;
import com.registel.rdw.logica.AccesoAccesoPerfil;
import com.registel.rdw.logica.AccesoPerfil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Alarma;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lider_desarrollador
 */
public class AccesoPerfilBD {

    static String procedimiento = "proc_tbl_acceso_perfil";

    public static ArrayList<AccesoAccesoPerfil> selectByPerfilId(Integer fkPerfil, Integer fkPerfilUsuarioLogin) {
        return selectByPerfilId(fkPerfil, false, fkPerfilUsuarioLogin);
    }

    public static ArrayList<AccesoAccesoPerfil> selectByPerfilId(Integer fkPerfil, boolean permissions, Integer fkPerfilUsuarioLogin) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String joinType = "LEFT";
        if (permissions) {
            joinType = "INNER";
        }

        String sql = "SELECT accs_prfl.PK_ACCESO_PERFIL,"
                + "accs_prfl.FK_PERFIL,"
                + "accs.PK_ACCESO,"
                + "accs.FK_ACCESO_PADRE,"
                + "accs.FK_GRUPO,"
                + "accs.SUBGRUPO,"
                + "accs.POSICION,"
                + "accs.NOMBRE_ACCESO,"
                + "accs.ALIAS_ACCESO,"
                + "accs.FK_ACCESO_TIPO,"
                + "accs.FK_MODULO,"
                + " ? as FK_PERFIL_USUARIO_LOGIN,"
                + "accs.MAXSUBGRUPO "
                + "FROM "
                + "(SELECT (SELECT MAX(SUBGRUPO) as MAXSUBGRUPO_ FROM tbl_acceso) as MAXSUBGRUPO, PK_ACCESO, FK_ACCESO_PADRE, FK_GRUPO, NOMBRE_ACCESO ,ALIAS_ACCESO, SUBGRUPO,POSICION, FK_ACCESO_TIPO, FK_MODULO, ESTADO FROM tbl_acceso WHERE ESTADO = 1) accs "
                + joinType + " JOIN "
                + "(SELECT PK_ACCESO_PERFIL, FK_PERFIL, FK_ACCESO, ESTADO FROM tbl_acceso_perfil WHERE FK_PERFIL = ? AND ESTADO = 1) accs_prfl "
                + "ON accs.PK_ACCESO = accs_prfl.FK_ACCESO ORDER BY accs.SUBGRUPO DESC, accs.POSICION";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, fkPerfilUsuarioLogin);
            ps.setInt(2, fkPerfil);
            rs = ps.executeQuery();

            //System.out.println("sql permissos " + sql);
            ArrayList<AccesoAccesoPerfil> lst_aap = new ArrayList<>();
            while (rs.next()) {
                AccesoAccesoPerfil a = new AccesoAccesoPerfil();
                a.setPkAcceso(rs.getInt("PK_ACCESO"));
                a.setPkAccesoPerfil(rs.getInt("PK_ACCESO_PERFIL"));
                a.setFkAccesoPadre(rs.getInt("FK_ACCESO_PADRE"));
                a.setFkGrupo(rs.getInt("FK_GRUPO"));
                a.setFkAccesoTipo(rs.getInt("FK_ACCESO_TIPO"));
                a.setFkModulo(rs.getInt("FK_MODULO"));
                a.setNombreAcceso(rs.getString("NOMBRE_ACCESO"));
                a.setAliasAcceso(rs.getString("ALIAS_ACCESO"));
                a.setSubGrupo(rs.getInt("SUBGRUPO"));
                a.setPosicion(rs.getInt("POSICION"));
                a.setFkPerfil(rs.getInt("FK_PERFIL"));
                a.setMaxSubGrupo(rs.getInt("MAXSUBGRUPO"));
                a.setFkPerfilUsuarioLogin(rs.getInt("FK_PERFIL_USUARIO_LOGIN"));
                lst_aap.add(a);
            }
            return lst_aap;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static boolean editAccesosPerfil(List<Integer> accesosId, Integer fkPerfil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection conn = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlUpdt0 = "UPDATE tbl_acceso_perfil SET ESTADO = 0 WHERE FK_PERFIL = ?;";

        try {
            ps = conn.prepareStatement(sqlUpdt0);
            ps.setInt(1, fkPerfil);
            ps.executeUpdate();

            List<Integer> lapInsrt1 = new ArrayList<>();
            List<Integer> lapUpdt1 = new ArrayList<>();

            for (Integer fkAcceso : accesosId) {
                AccesoPerfil ap = getByAceesoPerfilWoConn(fkAcceso, fkPerfil, conn);
                if (ap != null) {
                    lapUpdt1.add(fkAcceso);
                } else {
                    lapInsrt1.add(fkAcceso);
                }
            }

            if (lapUpdt1.size() > 0) {
                String sqlUpdt1 = "UPDATE tbl_acceso_perfil SET ESTADO = CASE ";
                for (Integer fkAcceso : lapUpdt1) {
                    sqlUpdt1 += "WHEN (FK_PERFIL = " + fkPerfil + " AND FK_ACCESO =" + fkAcceso + ") THEN 1 ";
                }
                sqlUpdt1 += "ELSE ESTADO END;";
                ps = conn.prepareStatement(sqlUpdt1);
                ps.executeUpdate();
            }

            if (lapInsrt1.size() > 0) {
                String sqlInsrt1 = "INSERT INTO tbl_acceso_perfil (FK_PERFIL, FK_ACCESO, ESTADO, MODIFICACION_LOCAL) VALUES ";
                for (Integer fkAcceso : lapInsrt1) {
                    sqlInsrt1 += "(" + fkPerfil + "," + fkAcceso + ",1,1),";
                }
                sqlInsrt1 = sqlInsrt1.substring(0, sqlInsrt1.length() - 1);
                sqlInsrt1 += ";";
                ps = conn.prepareStatement(sqlInsrt1);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(conn);
        }
        return true;
    }

    public AccesoPerfil getByIdWoConn(Integer id, Connection conn) {
        AccesoPerfil accesoPerfil = null;
        try {

            Statement st = conn.createStatement();
            st.executeUpdate("START TRANSACTION;");
            String query = "CALL " + procedimiento + " (" + id + ", " + UtilBD.getNullFields(5) + " NULL, NULL , 3);";

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                accesoPerfil = new AccesoPerfil();
                accesoPerfil.setId(rs.getInt("PK_ACCESO_PERFIL"));
                accesoPerfil.setFkPerfil(rs.getInt("FK_PERFIL"));
                accesoPerfil.setFkAcceso(rs.getInt("FK_ACCESO"));
                accesoPerfil.setEstado(rs.getInt("ESTADO"));
                accesoPerfil.setModificacionLocal(rs.getShort("MODIFICACION_LOCAL"));
                accesoPerfil.setFechaModificacion(rs.getTimestamp("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return accesoPerfil;
    }

    public static AccesoPerfil getByAceesoPerfilWoConn(Integer fkAcceso, Integer fkPerfil, Connection conn) {
        AccesoPerfil accesoPerfil = null;
        try {

            Statement st = conn.createStatement();
            st.executeUpdate("START TRANSACTION;");
            String query = "CALL " + procedimiento + " (NULL, " + fkPerfil + "," + fkAcceso + "," + UtilBD.getNullFields(4) + " NULL, NULL , 3);";

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                accesoPerfil = new AccesoPerfil();
                accesoPerfil.setId(rs.getInt("PK_ACCESO_PERFIL"));
                accesoPerfil.setFkPerfil(rs.getInt("FK_PERFIL"));
                accesoPerfil.setFkAcceso(rs.getInt("FK_ACCESO"));
                accesoPerfil.setEstado(rs.getInt("ESTADO"));
                accesoPerfil.setModificacionLocal(rs.getShort("MODIFICACION_LOCAL"));
                accesoPerfil.setFechaModificacion(rs.getTimestamp("FECHA_MODIFICACION"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return accesoPerfil;
    }

    public static AccesoPerfil getEntidad(ResultSet resultset) throws SQLException {
        AccesoPerfil entidad = new AccesoPerfil();
        entidad.setFechaModificacion(resultset.getTimestamp("FECHA_MODIFICACION"));
        entidad.setModificacionLocal(resultset.getShort("MODIFICACION_LOCAL"));
        entidad.setId(resultset.getInt("PK_ACCESO_PERFIL"));
        entidad.setFkPerfil(resultset.getInt("FK_PERFIL"));
        entidad.setFkAcceso(resultset.getInt("FK_ACCESO"));
        entidad.setEstado(resultset.getShort("ESTADO"));
        return entidad;
    }

}//FIN DE CLASE
