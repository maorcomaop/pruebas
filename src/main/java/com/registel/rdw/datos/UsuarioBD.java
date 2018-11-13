/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.registel.rdw.datos.EmpresaBD.exist;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.StringUtils;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class UsuarioBD {

    // Comprueba si registro de usuario con determinado valor de cedula existe.
    public static boolean exist(Usuario u) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Comprueba validez de campo
        if (!Restriction.isNumberPositive(u.getNumdoc())) {
            return false;
        }

        String sql = "SELECT u.PK_USUARIO FROM tbl_usuario as u WHERE u.CEDULA = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setString(1, u.getNumdoc());
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

    // Consulta registro de usuario especifico.
    public static Usuario getById(int id) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_usuario WHERE PK_USUARIO = ?";
        Usuario u = null;
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("PK_USUARIO"));
                u.setNombre(rs.getString("NOMBRE"));
                u.setApellido(rs.getString("APELLIDO"));
                u.setA_numdoc(rs.getString("CEDULA"));
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return u;
    }

    // Consulta nombre de campo por el cual registro de usuario existe 
    // (cedula, email o login).
    public static String existByFields(Usuario u) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Comprueba validez de campos
        if (!Restriction.isNumberPositive(u.getNumdoc())
                || !Restriction.isEmail(u.getEmail())
                || !Restriction.isLogin(u.getLogin())) {
            return null;
        }

        //String sql = "SELECT u.PK_USUARIO FROM tbl_usuario as u WHERE u.CEDULA = ?";
        String sql
                = "SELECT IF (u.CEDULA = ?, 'cedula', "
                + "       IF (u.EMAIL  = ?, 'email', "
                + "       IF (u.LOGIN  = ?, 'login', 'ninguno'))) AS RS"
                + "  FROM tbl_usuario AS u WHERE "
                + " (u.CEDULA = ? OR "
                + "  u.EMAIL  = ? OR "
                + "  u.LOGIN  = ?)"; // AND u.ESTADO = 1                

        try {
            ps = con.prepareCall(sql);
            ps.setString(1, u.getNumdoc());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getLogin());
            ps.setString(4, u.getNumdoc());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("RS");
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            return null;
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return "ninguno";
    }

    // Consulta si registro de usuario existe por determinado
    // valor de campo, especificado en parametro (cedula, email o login).
    public static String existByField(Usuario u, String field) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Comprueba validez de campos
        if (!Restriction.isNumberPositive(u.getNumdoc())
                || !Restriction.isEmail(u.getEmail())
                || !Restriction.isLogin(u.getLogin())) {
            return null;
        }

        String sql, val;
        sql = val = "";

        if (field.equals("cedula")) {
            sql = "SELECT u.CEDULA FROM tbl_usuario AS u WHERE u.CEDULA = ?"; // AND u.ESTADO = 1";
            val = u.getNumdoc();
        } else if (field.equals("email")) {
            sql = "SELECT u.EMAIL FROM tbl_usuario AS u WHERE u.EMAIL = ?"; // AND u.ESTADO = 1";
            val = u.getEmail();
        } else if (field.equals("login")) {
            sql = "SELECT u.LOGIN FROM tbl_usuario AS u WHERE u.LOGIN = ?"; // AND u.ESTADO = 1";
            val = u.getLogin();
        } else {
            return null;
        }

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, val);
            rs = ps.executeQuery();

            if (rs.next()) {
                return field;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return "ninguno";
    }

    // Inserta nuevo registro de usuario, verificando antes su no existencia.
    public static boolean insert(Usuario u) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campos
        if (!Restriction.isNumberPositive("" + u.getIdperfil())
                || !Restriction.isNumberPositive("" + u.getIdempresa())
                || !Restriction.isNumberPositive("" + u.getIdtipodoc())
                || !Restriction.isNumberPositive(u.getNumdoc())
                || !Restriction.isAlpha_esp(u.getNombre())
                || !Restriction.isAlpha_esp(u.getApellido())
                || !Restriction.isEmail(u.getEmail())
                || !Restriction.isLogin(u.getLogin())
                || !Restriction.isPass(u.getPass())) {
            return false;
        }

        String sql = "INSERT INTO tbl_usuario ("
                        + " FK_PERFIL,"
                        + " CEDULA,"
                        + " NOMBRE,"
                        + " APELLIDO,"                        
                        + " EMAIL,"
                        + " LOGIN,"
                        + " CONTRASENA,"
                        + " ESTADO,"
                        + " MODIFICACION_LOCAL,"
                        + " USUARIOBD,"
                        + " ESTADO_CONEXION,"
                        + " TIPO,"
                        + " FK_TIPO_DOCUMENTO,"
                        + " FK_EMPRESA)"
                + " VALUES (?,?,?,?,?,?,sha2(?, 256),?,?,?,?,?,?,?)";

        if (exist(u)) {
            String msg = "* Registro de usuario ya existe.";
            throw new ExisteRegistroBD(msg);
        } else {
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, u.getIdperfil());
                ps.setString(2, u.getNumdoc());
                ps.setString(3, u.getNombre());
                ps.setString(4, u.getApellido());
                ps.setString(5, u.getEmail());
                ps.setString(6, u.getLogin());
                ps.setString(7, u.getPass());
                //ps.setString(8, u.getConfpass());
                ps.setInt(8, 1);
                ps.setInt(9, 1);
                //ps.setString(11, u.getTipousr());
                //ps.setString(12, u.getIdusr());
                //ps.setString(12, null);                
                ps.setString(10, "root@localhost");
                ps.setInt(11, 1);
                ps.setInt(12, 1);
                ps.setInt(13, u.getIdtipodoc());
                ps.setInt(14, u.getIdempresa());

                ps.executeUpdate();
                return true;

            } catch (SQLException ex) {
                System.err.println(ex);
            } finally {
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }
            return false;
        }
    }

    // Inserta registro de nuevo usuario inactivo (estado en 0),
    // asociado a un token para posterior uso.
    public static boolean insert_preactive(Usuario u, String token) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campos
        if (!Restriction.isNumberPositive("" + u.getIdperfil())
                || !Restriction.isNumberPositive("" + u.getIdempresa())
                || !Restriction.isNumberPositive(u.getNumdoc())
                || !Restriction.isAlpha_esp(u.getNombre())
                || !Restriction.isAlpha_esp(u.getApellido())                
                || !Restriction.isEmail(u.getEmail())
                || !Restriction.isLogin(u.getLogin())
                || !Restriction.isPass(u.getPass())
                || !Restriction.isToken(token)) {
            return false;
        }

        String sql = "INSERT INTO tbl_usuario ("
                        + " FK_PERFIL,"
                        + " CEDULA,"
                        + " NOMBRE,"
                        + " APELLIDO,"
                        + " EMAIL,"
                        + " LOGIN,"
                        + " CONTRASENA,"
                        + " ESTADO,"
                        + " MODIFICACION_LOCAL,"
                        + " USUARIOBD,"
                        + " ESTADO_CONEXION,"
                        + " TIPO,"
                        + " TOKEN,"
                        + " EXPIRE_TOKEN,"
                        + " FK_EMPRESA)"
                + " VALUES (?,?,?,?,?,?,sha2(?, 256),?,?,?,?,?,?,?,?)";

        if (exist(u)) {
            String msg = "* Registro de usuario ya existe.";
            throw new ExisteRegistroBD(msg);
        } else {
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, u.getIdperfil());
                ps.setString(2, u.getNumdoc());
                ps.setString(3, u.getNombre());
                ps.setString(4, u.getApellido());
                ps.setString(5, u.getEmail());
                ps.setString(6, u.getLogin());
                ps.setString(7, u.getPass());
                //ps.setString(8, u.getConfpass());
                ps.setInt(8, 0);
                ps.setInt(9, 1);
                //ps.setString(11, u.getTipousr());
                //ps.setString(12, u.getIdusr());
                //ps.setString(12, null);                
                ps.setString(10, "root@localhost");
                ps.setInt(11, 1);
                ps.setInt(12, 1);
                ps.setString(13, token);
                ps.setLong(14, 0);
                ps.setInt(15, u.getIdempresa());

                ps.executeUpdate();
                return true;

            } catch (SQLException ex) {
                System.err.println(ex);
            } finally {
                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }
            return false;
        }
    }

    // Elimina registro de usuario inactivo especifico.
    public static boolean delete_preactive(Usuario u) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campo
        if (!Restriction.isNumberPositive(u.getNumdoc())) {
            return false;
        }

        String sql = "DELETE FROM tbl_usuario WHERE CEDULA = ? AND ESTADO = 0";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNumdoc());
            
            if (ps.executeUpdate() == 1)
                return true;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta registro de usuario inactivo segun nombre de usuario,
    // contrasena y token asociado.
    public static Usuario user_preactive(String nomusr, String pass, String token) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();        
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Comprueba validez de campo
        if (!Restriction.isLogin(nomusr)
                || !Restriction.isPass(pass)
                || !Restriction.isToken(token)) {
            return null;
        }

        String sql = "SELECT * FROM tbl_usuario WHERE"
                + " LOGIN = ? AND CONTRASENA = sha2(?, 256) AND TOKEN = ? AND ESTADO = 0";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nomusr);
            ps.setString(2, pass);
            ps.setString(3, token);
            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setNombre(rs.getString("NOMBRE"));
                u.setApellido(rs.getString("APELLIDO"));
                u.setLogin(rs.getString("LOGIN"));
                u.setEmail(rs.getString("EMAIL"));
                u.setNumdoc(rs.getString("CEDULA"));
                u.setToken(rs.getString("TOKEN"));
                u.setIdperfil(rs.getInt("FK_PERFIL"));
                
                return u;
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

    // Activa usuario inactivo segun nombre de usuario, contrasena y token
    // asociado. Establece estado en 1.
    public static boolean change_preactive(String nomusr, String pass, String token) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campos
        if (!Restriction.isLogin(nomusr)
                || !Restriction.isPass(pass)
                || !Restriction.isToken(token)) {
            return false;
        }

        String sql = "UPDATE tbl_usuario SET ESTADO = 1, TOKEN = NULL WHERE"
                + " LOGIN = ? AND CONTRASENA = sha2(?, 256) AND TOKEN = ? AND ESTADO = 0";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nomusr);
            ps.setString(2, pass);
            ps.setString(3, token);
            int rs = ps.executeUpdate();

            if (rs == 1) 
                return true;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Activa usuario inactivo segun numero de documento asociado.
    // Establece estado en 1.
    public static boolean change_preactive (String numdoc) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        
        String sql = "UPDATE tbl_usuario SET ESTADO = 1, TOKEN = NULL WHERE"
                + " CEDULA = ? AND ESTADO = 0";
        
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, Restriction.getNumber(numdoc));
            int rs = ps.executeUpdate();
            
            if (rs == 1) {
                con.commit();
                return true;
            } else {
                con.rollback();
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
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Consulta registro de usuario especifico.
    public static Usuario get(String numdoc) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Comprueba validez de campo
        if (!Restriction.isNumberPositive(numdoc)) {
            return null;
        }

        String sql = "SELECT * FROM tbl_usuario WHERE CEDULA=? AND ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(numdoc));
            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setNombre(rs.getString("NOMBRE"));
                u.setApellido(rs.getString("APELLIDO"));                
                u.setIdtipodoc(rs.getInt("FK_TIPO_DOCUMENTO"));
                u.setNumdoc("" + rs.getInt("CEDULA"));
                u.setLogin(rs.getString("LOGIN"));
                u.setEmail(rs.getString("EMAIL"));
                u.setIdperfil(rs.getInt("FK_PERFIL"));
                u.setIdempresa(rs.getInt("FK_EMPRESA"));

                u.setA_numdoc(rs.getString("CEDULA"));
                u.setA_email(rs.getString("EMAIL"));
                u.setA_login(rs.getString("LOGIN"));

                return u;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    // Consulta registros de usuarios asociados a un listado 
    // de empresas y perfil especifico.
    public ArrayList<Usuario> getUsuariosByEmpresaPerfil(ArrayList<Empresa> empresas, int idPerfil) {
        ArrayList<Usuario> resultado = new ArrayList<>();

        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement statement = null;
        ResultSet rs = null;

        StringBuilder query = new StringBuilder();
        query.append("SELECT u.PK_USUARIO, ");
        query.append("u.FK_EMPRESA, ");
        query.append("u.FK_PERFIL, ");
        query.append("u.CEDULA, ");
        query.append("u.NOMBRE, ");
        query.append("u.APELLIDO, ");
        query.append("u.EMAIL, ");
        query.append("u.LOGIN, ");
        query.append("u.CONTRASENA, ");
        query.append("u.USUARIOBD, ");
        query.append("u.ESTADO, ");
        query.append("u.ESTADO_CONEXION, ");
        query.append("u.MODIFICACION_LOCAL, ");
        query.append("u.FECHA_MODIFICACION, ");
        query.append("u.TIPO, ");
        query.append("u.PK_UNICA ");
        query.append("FROM tbl_usuario u ");
        query.append("INNER JOIN tbl_usuario_permiso_empresa upe ON u.PK_USUARIO = upe.FK_USUARIO ");

        query.append("WHERE     u.ESTADO = 1 ");
        query.append("      AND upe.ESTADO = 1 ");
        query.append("      AND u.FK_PERFIL = ");
        query.append(idPerfil);

        if (empresas != null && empresas.size() > 0) {
            query.append(" AND upe.FK_EMPRESA IN (0");
            for (Empresa empresa : empresas) {
                query.append(", ").append(empresa.getId());
            }

            query.append(" ) ");
        }

        query.append(" GROUP BY upe.FK_USUARIO ");
        query.append(" ORDER BY u.APELLIDO ;");

        try {
            statement = con.createStatement();
            ResultSet resultset = statement.executeQuery(query.toString());

            while (resultset.next()) {

                Usuario entidad = new Usuario();
                entidad.setId(resultset.getInt("PK_USUARIO"));
                entidad.setIdempresa(resultset.getInt("FK_EMPRESA"));
                entidad.setIdperfil(resultset.getInt("FK_PERFIL"));
                entidad.setNumdoc(resultset.getString("CEDULA"));
                entidad.setNombre(resultset.getString("NOMBRE"));
                entidad.setApellido(resultset.getString("APELLIDO"));

                resultado.add(entidad);
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(statement);
            pila_con.liberarConexion(con);
        }
        return resultado;
    }

    // Actualiza valores de un registro usuario especifico.
    public static boolean update(Usuario u, String a_numdoc) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campos
        if (!Restriction.isAlpha_esp(u.getNombre())
                || !Restriction.isAlpha_esp(u.getApellido())
                || !Restriction.isNumberPositive("" + u.getIdtipodoc())
                || !Restriction.isNumberPositive(u.getNumdoc())
                || !Restriction.isNumberPositive(a_numdoc)
                || !Restriction.isEmail(u.getEmail())
                || !Restriction.isLogin(u.getLogin())
                || !Restriction.isNumberPositive("" + u.getIdperfil())
                || !Restriction.isNumberPositive("" + u.getIdempresa())) {
            return false;
        }

        String sql = "UPDATE tbl_usuario SET "
                + "NOMBRE=?, APELLIDO=?, CEDULA=?, EMAIL=?, LOGIN=?, FK_PERFIL=?, FK_EMPRESA=?, FK_TIPO_DOCUMENTO=? WHERE CEDULA=? AND ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getNumdoc());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getLogin());
            ps.setInt(6, u.getIdperfil());
            ps.setInt(7, u.getIdempresa());
            ps.setInt(8, u.getIdtipodoc());
            ps.setString(9, a_numdoc);

            if (ps.executeUpdate() == 1)
                return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Actualiza valores de un registro usuario especifico.
    public static boolean updateByUser(Usuario u, String numdoc) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campos
        if (!Restriction.isAlpha_esp(u.getNombre())
                || !Restriction.isAlpha_esp(u.getApellido())
                || !Restriction.isNumberPositive(numdoc)
                || !Restriction.isEmail(u.getEmail())
                || !Restriction.isLogin(u.getLogin())) {
            return false;
        }

        String sql = "UPDATE tbl_usuario SET NOMBRE=?, APELLIDO=?, EMAIL=?, LOGIN=? WHERE CEDULA = ? AND ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getLogin());
            ps.setString(5, numdoc);

            if (ps.executeUpdate() == 1)
                return true;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    // Actualiza campo estado de registro usuario especifico.
    public static boolean changeState(String numdoc, int estado) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        // Comprueba validez de campos
        if (!Restriction.isNumberPositive(numdoc)
            || !Restriction.isEstado(estado)) {
            return false;
        }

        String sql = "UPDATE tbl_usuario SET ESTADO=? WHERE CEDULA=?";

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setString(2, numdoc);

            if (ps.executeUpdate() == 1) {
                con.commit();
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
            try { con.setAutoCommit(true); }
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return false;
    }
    
    // Consulta minima de usuarios asociados a una empresa y perfil especifico.
    public static ArrayList<Usuario> getByPerfil(int idEmpresa, int idPerfil) {        
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_usuario AS u WHERE"
                   + " u.FK_EMPRESA = ? AND u.FK_PERFIL = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpresa);
            ps.setInt(2, idPerfil);
            rs = ps.executeQuery();
            
            ArrayList<Usuario> lst_usr = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("PK_USUARIO"));
                u.setIdempresa(rs.getInt("FK_EMPRESA"));
                u.setIdperfil(rs.getInt("FK_PERFIL"));
                u.setNumdoc(rs.getString("CEDULA"));
                u.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                u.setApellido(StringUtils.upperFirstLetter(rs.getString("APELLIDO")));
                lst_usr.add(u);
            }
            return lst_usr;            

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally { 
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);           
            pila_con.liberarConexion(con);
        }
        return null;
    }
}
