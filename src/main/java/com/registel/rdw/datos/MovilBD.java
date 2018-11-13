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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lider_desarrollador
 */
public class MovilBD {

    public static boolean exist(Movil m) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT c.PK_VEHICULO FROM tbl_vehiculo as c WHERE c.PK_VEHICULO = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, m.getId());
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

    public static int insertReturnId(Movil m) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idVehiculo = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tbl_vehiculo (FK_EMPRESA, PLACA, NUM_INTERNO, CAPACIDAD, DIA_PICOPLACA, DIA_DESCANSO, ESTADO) ");
        sql.append(" VALUES (?,?,?,?,?,?,?)");

        if (exist(m)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, m.getFkEmpresa());
                ps.setString(2, m.getPlaca());
                ps.setString(3, m.getNumeroInterno());
                ps.setInt(4, m.getCapacidad());
                ps.setString(5, m.getDiaPicoplaca());
                ps.setString(6, m.getDiaDescanso());
                ps.setInt(7, m.getEstado());
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idVehiculo = rs.getInt(1);
                }
                con.commit();
                if (retorno > 0) {
                    return idVehiculo;
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

    public static int insert(Movil m) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tbl_vehiculo (FK_EMPRESA, PLACA, NUM_INTERNO, CAPACIDAD, DIA_PICOPLACA, DIA_DESCANSO, ESTADO) ");
        sql.append(" VALUES (?,?,?,?,?,?,?)");

        if (exist(m)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());
                ps.setInt(1, m.getFkEmpresa());
                ps.setString(2, m.getPlaca());
                ps.setString(3, m.getNumeroInterno());
                ps.setInt(4, m.getCapacidad());
                ps.setString(5, m.getDiaPicoplaca());
                ps.setString(6, m.getDiaDescanso());
                ps.setInt(7, m.getEstado());
                int retorno = ps.executeUpdate();
                con.commit();
                if (retorno > 0) {
                    return retorno;
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

    public static Movil selectByOne(Movil m) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil MovilEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo (NULL, NULL, '");
        sql.append(m.getPlaca());
        sql.append("', '");
        sql.append(m.getNumeroInterno());
        sql.append("', NULL, NULL, NULL, ");
        sql.append(m.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            MovilEncontrado = new Movil();
            if (rs.next()) {
                MovilEncontrado.setId(rs.getInt("PK_VEHICULO"));
                MovilEncontrado.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                MovilEncontrado.setPlaca(rs.getString("PLACA"));
                MovilEncontrado.setNumeroInterno(rs.getString("NUM_INTERNO"));
                MovilEncontrado.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                MovilEncontrado.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                MovilEncontrado.setCapacidad(Integer.parseInt(rs.getString("CAPACIDAD")));
                MovilEncontrado.setEstado(rs.getInt("ESTADO"));
            }

            /*StringBuilder sql1 = new StringBuilder();
            sql1.append("CALL proc_tbl_vehiculo (").append(MovilEncontrado.getCodEmpresa()).append(", NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 3);");
            int executeUpdate1 = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql1.toString());
            Empresa e = new Empresa();
            if (rs.next()) {
                e.setNit(rs.getString(""));
                e.setNombre(rs.getString(""));
            }*/
            return MovilEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static Movil selectByOne1(Movil m) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil MovilEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo (NULL, NULL, '");
        sql.append(m.getPlaca());
        sql.append("', '");
        sql.append(m.getNumeroInterno());
        sql.append("', NULL, NULL, NULL, ");
        sql.append(m.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            MovilEncontrado = new Movil();
            if (rs.next()) {
                MovilEncontrado.setId(rs.getInt("PK_VEHICULO"));
                MovilEncontrado.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                MovilEncontrado.setPlaca(rs.getString("PLACA"));
                MovilEncontrado.setNumeroInterno(rs.getString("NUM_INTERNO"));
                MovilEncontrado.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                MovilEncontrado.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                MovilEncontrado.setCapacidad(Integer.parseInt(rs.getString("CAPACIDAD")));
                MovilEncontrado.setEstado(rs.getInt("ESTADO"));
            }

            StringBuilder sql1 = new StringBuilder();
            sql1.append("CALL proc_tbl_empresa (").append(MovilEncontrado.getFkEmpresa()).append(", NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 3);");
            int executeUpdate1 = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql1.toString());
            Empresa e = new Empresa();
            if (rs.next()) {
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setIdciudad(rs.getInt("FK_CIUDAD"));
                e.setNit(rs.getString("NIT"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setEstado(rs.getInt("ESTADO"));
            }
            MovilEncontrado.setEmpresaQuePertenece(e);

            return MovilEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static Movil selectByOneView(Movil m) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil MovilEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo (");
        sql.append(m.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, ");
        sql.append(m.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            MovilEncontrado = new Movil();
            if (rs.next()) {
                MovilEncontrado.setId(rs.getInt("PK_VEHICULO"));
                MovilEncontrado.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                MovilEncontrado.setPlaca(rs.getString("PLACA"));
                MovilEncontrado.setNumeroInterno(rs.getString("NUM_INTERNO"));
                MovilEncontrado.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                MovilEncontrado.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                MovilEncontrado.setCapacidad(Integer.parseInt(rs.getString("CAPACIDAD")));
                MovilEncontrado.setEstado(rs.getInt("ESTADO"));
            }

            StringBuilder sql1 = new StringBuilder();
            sql1.append("CALL proc_tbl_empresa (").append(MovilEncontrado.getFkEmpresa()).append(", NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 3);");
            int executeUpdate1 = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql1.toString());
            Empresa e = new Empresa();
            if (rs.next()) {
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setIdciudad(rs.getInt("FK_CIUDAD"));
                e.setNit(rs.getString("NIT"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setEstado(rs.getInt("ESTADO"));
            }
            MovilEncontrado.setEmpresaQuePertenece(e);

            return MovilEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int update(Movil m) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_vehiculo SET FK_EMPRESA=?, PLACA=?, NUM_INTERNO=?, CAPACIDAD=?, DIA_PICOPLACA=?, DIA_DESCANSO=?, ESTADO=? WHERE (PK_VEHICULO = ?)");
        //System.out.println(sql.toString());
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, m.getFkEmpresa());
            ps.setString(2, m.getPlaca());
            ps.setString(3, m.getNumeroInterno());
            ps.setInt(4, m.getCapacidad());
            ps.setString(5, m.getDiaPicoplaca());
            ps.setString(6, m.getDiaDescanso());
            ps.setInt(7, m.getEstado());
            ps.setInt(8, m.getId());
            int retorno = ps.executeUpdate();
            con.commit();
            if (retorno > 0) {
                return 1;
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
        return 0;

    }

    public static int updateEstado(Movil m) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_vehiculo ");
        sql.append("SET ESTADO=? ");
        sql.append("WHERE PK_VEHICULO=? ");
        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, m.getEstado());
            ps_update.setInt(2, m.getId());
            int retorno = ps_update.executeUpdate();

            con.commit();
            if (retorno > 0) {
                return 1;
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
        return 0;
    }

    public static ArrayList<Movil> movilesPlaca(Movil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Movil> lstdatos = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo (");
        sql.append(c.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, ");
        sql.append(c.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        //System.out.println(sql.toString());
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstdatos = new ArrayList<Movil>();
            while (rs.next()) {
                Movil t = new Movil();
                t.setId(rs.getInt("PK_VEHICULO"));
                t.setPlaca(rs.getString("PLACA"));
                t.setNumeroInterno(rs.getString("NUM_INTERNO"));
                t.setEstado(rs.getInt("ESTADO"));
                lstdatos.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return lstdatos;
    }

    public static ArrayList<Movil> listadoPlacas(Movil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Movil> lstdatos = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_vehiculo (NULL, NULL, NULL, NULL, NULL, NULL, NULL, ");
        sql.append(c.getEstado());
        sql.append(", NULL, NULL, NULL, NULL, 3);");

        System.out.println(sql.toString());
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstdatos = new ArrayList<Movil>();
            while (rs.next()) {
                Movil t = new Movil();
                t.setId(rs.getInt("PK_VEHICULO"));
                t.setPlaca(rs.getString("PLACA"));
                t.setNumeroInterno(rs.getString("NUM_INTERNO"));
                t.setEstado(rs.getInt("ESTADO"));
                lstdatos.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return lstdatos;
    }

    public static ArrayList<Integer> selectId() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT v.PK_VEHICULO FROM tbl_vehiculo AS v WHERE v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ArrayList<Integer> lst_id = new ArrayList<Integer>();
            while (rs.next()) {
                int id = rs.getInt("PK_VEHICULO");
                lst_id.add(id);
            }
            return lst_id;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static ArrayList<Integer> selectId(int idEmpresa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT v.PK_VEHICULO FROM tbl_vehiculo AS v"
                + " WHERE v.FK_EMPRESA = ? AND v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpresa);

            rs = ps.executeQuery();

            ArrayList<Integer> lst_id = new ArrayList<Integer>();
            while (rs.next()) {
                int id = rs.getInt("PK_VEHICULO");
                lst_id.add(id);
            }
            return lst_id;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static String selectStringId(int idEmpresa) {

        ArrayList<Integer> lst_id = selectId(idEmpresa);
        String s = "";
        for (Integer id : lst_id) {
            s += (s == "") ? id.intValue() : "," + id.intValue();
        }
        return s;
    }

    public static ArrayList<Movil> getByEmpresaId(int idEmpresa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Movil> lstMovil = new ArrayList();
        String sql = "SELECT v.PK_VEHICULO FROM tbl_vehiculo AS v"
                + " WHERE v.FK_EMPRESA = ? AND v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpresa);

            rs = ps.executeQuery();

            Movil movil;
            while (rs.next()) {
                movil = new Movil();
                movil.setId(rs.getInt("PK_VEHICULO"));
                movil.setNumeroInterno(rs.getString("NUM_INTERNO"));
                movil.setPlaca(rs.getString("PLACA"));
                movil.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                movil.setCapacidad(rs.getInt("CAPACIDAD"));
                movil.setEstado(rs.getInt("ESTADO"));
                lstMovil.add(movil);
            }
            return lstMovil;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int selectCapacidad(int idMovil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT v.CAPACIDAD FROM tbl_vehiculo AS v"
                + " WHERE v.PK_VEHICULO = ? AND v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMovil);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("CAPACIDAD");
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

    public static ArrayList<Movil> select() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "  v.PK_VEHICULO,"
                + "  v.PLACA,"
                + "  v.NUM_INTERNO,"
                + "  v.CAPACIDAD,"
                + "  v.ESTADO"
                + " FROM tbl_vehiculo as v"
                + " WHERE v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ArrayList<Movil> lst_movil = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();

                m.setId(rs.getInt("PK_VEHICULO"));
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setEstado(rs.getInt("ESTADO"));

                lst_movil.add(m);

            }
            return lst_movil;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int selectID(String placa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " v.PK_VEHICULO"
                + " FROM tbl_vehiculo as v"
                + " WHERE v.PLACA = ? AND v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("PK_VEHICULO");
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

    public static Movil select(String placa) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " v.PK_VEHICULO,"
                + " v.FK_EMPRESA,"
                + " v.PLACA,"
                + " v.NUM_INTERNO,"
                + " v.CAPACIDAD,"
                + " v.DIA_PICOPLACA,"
                + " v.DIA_DESCANSO,"
                + " v.ESTADO"
                + " FROM tbl_vehiculo AS v"
                + " WHERE v.PLACA = ? AND v.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            rs = ps.executeQuery();

            if (rs.next()) {
                Movil m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setEstado(rs.getInt("ESTADO"));

                return m;
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

    public static ArrayList<Movil> selectByGroup(int idgrupoMovil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql
                = "SELECT v.* FROM tbl_agrupacion_vehiculo AS av"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "     v.PK_VEHICULO = av.FK_VEHICULO AND v.ESTADO = 1"
                + " WHERE av.FK_AGRUPACION = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idgrupoMovil);
            rs = ps.executeQuery();

            ArrayList<Movil> lst = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setPlaca(rs.getString("PLACA"));
                lst.add(m);
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

    public static int placaRepetida(Movil p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PK_VEHICULO, PLACA FROM tbl_vehiculo WHERE (PLACA=?) AND (ESTADO = 1)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, p.getPlaca());
            rs = ps.executeQuery();
            Movil m = new Movil();
            if (rs.next()) {
                return 1;
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
        return 0;
    }

    public static ArrayList<Movil> selectByGroupAndOwner(int idPropietario, int idgrupoMovil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql
                = "	SELECT * FROM"
                + "			"
                + "			("
                + "			SELECT v.* FROM tbl_agrupacion_vehiculo AS av"
                + "                 INNER JOIN tbl_vehiculo AS v ON"
                + "                     v.PK_VEHICULO = av.FK_VEHICULO AND v.ESTADO = 1"
                + "                 WHERE av.FK_AGRUPACION = ?"
                + "         )t0 "
                + "			INNER JOIN "
                + "         ("
                + "			SELECT fk_vehiculo FROM tbl_propietario_vehiculo WHERE fk_propietario = ? AND activa = 1 AND estado = 1 	"
                + "			) t1"
                + "			ON t0.PK_VEHICULO = t1.fk_vehiculo";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idgrupoMovil);
            ps.setInt(2, idPropietario);
            rs = ps.executeQuery();

            ArrayList<Movil> lst = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setPlaca(rs.getString("PLACA"));
                lst.add(m);
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

    public static ArrayList<Movil> selectByOwnerId(int userId) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM "
                + "             (SELECT"
                + "                PK_VEHICULO,"
                + "                FK_EMPRESA,"
                + "                PLACA,"
                + "                NUM_INTERNO,"
                + "                CAPACIDAD,"
                + "                DIA_PICOPLACA,"
                + "                DIA_DESCANSO,"
                + "                ESTADO"
                + "                FROM tbl_vehiculo "
                + "                WHERE ESTADO = 1) t0"
                + "                INNER JOIN"
                + "                ("
                + "		 SELECT fk_vehiculo FROM tbl_propietario_vehiculo "
                + "		 WHERE fk_propietario = ? AND activa = 1 AND estado = 1) t1"
                + "		 ON t0.PK_VEHICULO = t1.fk_vehiculo";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            ArrayList<Movil> lst = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setPlaca(rs.getString("PLACA"));
                lst.add(m);
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

    public static ArrayList<Movil> selectByOwner(int id_propietario) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "  v.PK_VEHICULO,"
                + "  v.PLACA,"
                + "  v.NUM_INTERNO AS NUMERO_INTERNO"
                + " FROM tbl_propietario_vehiculo as pv"
                + " INNER JOIN tbl_vehiculo as v ON"
                + "   pv.fk_vehiculo = v.PK_VEHICULO and v.ESTADO = 1"
                + " WHERE pv.fk_propietario = ? and pv.ACTIVA = 1 and pv.ESTADO = 1"
                + " ORDER BY v.PLACA ASC";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_propietario);
            rs = ps.executeQuery();

            ArrayList<Movil> lst = new ArrayList<Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUMERO_INTERNO"));
                lst.add(m);
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

    public static Map<String, Movil> selectMap() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT t0.* , t3.* "
                + "		FROM"
                + "		("
                + "		SELECT * FROM tbl_vehiculo WHERE ESTADO = 1"
                + "		) as t0"
                + "		LEFT JOIN "
                + "		("
                + "		SELECT t1.fk_vehiculo , t2.*"
                + "			FROM "
                + "			("
                + "			SELECT fk_gps , fk_vehiculo FROM tbl_gps_hardware WHERE estado = 1"
                + "			) as t1"
                + "			LEFT JOIN "
                + "			("
                + "				SELECT * FROM"
                + "					("
                + "					SELECT FK_GPS, ESTADO_CONEXION, FECHA_ULTIMO_REPORTE, FECHA_DESCONEXION FROM tbl_gps_resumen  ORDER BY FECHA_ULTIMO_REPORTE DESC"
                + "					) as tt GROUP BY tt.FK_GPS"
                + "			) as t2"
                + "			ON t1.fk_gps = t2.FK_GPS"
                + "		) as t3"
                + "		ON t0.PK_VEHICULO = t3.fk_vehiculo";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            Map<String, Movil> hmovil = new HashMap<String, Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA").toUpperCase());
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                m.setFkGps(rs.getString("FK_GPS"));
                m.setFechaUltimoReporte(rs.getTimestamp("FECHA_ULTIMO_REPORTE"));
                m.setFechaDesconexion(rs.getTimestamp("FECHA_DESCONEXION"));
                m.setEstadoConexion(rs.getInt("ESTADO_CONEXION"));
                hmovil.put(m.getPlaca(), m);
            }
            return hmovil;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static Map<String, Movil> selectMovilMap() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_vehiculo WHERE estado = 1";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            Map<String, Movil> hmovil = new HashMap<String, Movil>();
            while (rs.next()) {
                Movil m = new Movil();
                m.setPlaca(rs.getString("PLACA").toUpperCase());
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                hmovil.put(m.getPlaca(), m);
            }
            return hmovil;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static boolean selectByOne(int pkMovil) throws ExisteRegistroBD {
        Movil movil = new Movil();
        movil.setId(pkMovil);

        return exist(movil);
    }

    public static Movil selectOne(int pkMovil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_vehiculo WHERE PK_VEHICULO = ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pkMovil);
            rs = ps.executeQuery();

            if (rs.next()) {
                Movil m = new Movil();
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setDiaPicoplaca(rs.getString("DIA_PICOPLACA"));
                m.setDiaDescanso(rs.getString("DIA_DESCANSO"));
                m.setEstado(rs.getInt("ESTADO"));

                return m;
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

    public static ArrayList<Movil> searchGPSVehicleAll(String fkHardwareList, String plates, String groups) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement s = null;
        ResultSet rs = null;
        ArrayList<Movil> list;
        String sql = "SELECT a.fk_hardware , a.fk_gps , b.placa	, b.pk_vehiculo	"
                + "	FROM		"
                + "		(		"
                + "		select fk_vehiculo,fk_hardware,fk_gps from tbl_gps_hardware where fk_hardware IN (" + fkHardwareList + ") and estado = 1		"
                + "		) as a		"
                + "	INNER JOIN 		"
                + "	(		"
                + "	select pk_vehiculo, placa from tbl_vehiculo where "
                + "	 placa in (" + (plates.equalsIgnoreCase("") ? null : plates) + ") or "
                + "	 placa in ("
                + "			SELECT d.placa FROM"
                + "					("
                + "					SELECT fk_vehiculo, fk_agrupacion from tbl_agrupacion_vehiculo WHERE fk_agrupacion IN (" + (groups.equalsIgnoreCase("") ? null : groups) + ") and estado = 1 "
                + "					) as c"
                + "					INNER JOIN "
                + "					("
                + "					select pk_vehiculo , placa from tbl_vehiculo where estado = 1"
                + "					) as d"
                + "					ON c.fk_vehiculo = d.pk_vehiculo	"
                + "				)"
                + "	and "
                + "	estado =1		"
                + "	) as b		"
                + "	ON a.fk_vehiculo = b.pk_vehiculo";

        try {
            con.setAutoCommit(false);
            s = con.createStatement();
            rs = s.executeQuery(sql);
            Movil m;
            list = new ArrayList<>();
            while (rs.next()) {
                m = new Movil();
                m.setFkGps(rs.getString("fk_gps"));
                m.setPlaca(rs.getString("placa"));
                m.setFkHardware(rs.getInt("fk_hardware"));

                list.add(m);
            }
            return list;
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
            UtilBD.closeStatement(s);
            pila_con.liberarConexion(con);
        }
        return null;
    }

} //FIN DE CLASE
