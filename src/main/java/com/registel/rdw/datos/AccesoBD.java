/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Acceso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.Alarma;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class AccesoBD {

    public static ArrayList<Acceso> selectAll() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_acceso WHERE ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ArrayList<Acceso> lst_acc = new ArrayList<>();
            while (rs.next()) {
                Acceso a = new Acceso();
                a.setId(rs.getInt("PK_ACCESO"));
                a.setFkAccesoPadre(rs.getInt("FK_ACCESO_PADRE"));
                a.setFkGrupo(rs.getInt("FK_GRUPO"));
                a.setNombreAcceso(rs.getString("NOMBRE_ACCESO"));
                a.setSubGrupo(rs.getInt("SUBGRUPO"));
                a.setPosicion(rs.getInt("POSICION"));
                a.setEstado(rs.getInt("ESTADO"));
                a.setModificacionLocal(rs.getInt("MODIFICACION_LOCAL"));
                a.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));

                lst_acc.add(a);
            }
            return lst_acc;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

}//FIN DE CLASE
