/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.registel.rdw.logica.GrupoMovil;
import com.registel.rdw.logica.GrupoMovilDespacho;
import com.registel.rdw.logica.GrupoVehiculo;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.VehiculoNuevoListado;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author lider_desarrollador
 */
public class GrupoMovilBD {

    public static boolean exist(GrupoMovil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_agrupacion (");
        sql.append(c.getId());
        sql.append(", NULL, NULL, NULL, ");
        sql.append(c.getEstado());
        sql.append(", NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            resultset.last();
            int row = resultset.getRow();
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

    public static GrupoMovil existRelationShipGroupMobileByCar(Movil m) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoMovil gm = null;
        String sql = "SELECT PK_AGRUPACION_VEHICULO, FK_AGRUPACION, FK_VEHICULO, ESTADO FROM tbl_agrupacion_vehiculo WHERE (FK_VEHICULO = ?) and (ESTADO=1)";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, m.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                gm = new GrupoMovil();
                gm.setId(rs.getInt("PK_AGRUPACION_VEHICULO"));
                gm.setFkGrupo(rs.getInt("FK_AGRUPACION"));
                gm.setFkVehiculo(rs.getInt("FK_VEHICULO"));
            }
            return gm;

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int insert(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        int id = 0, v = 0;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_agrupacion (NULL, '");
        sql.append(c.getNombreGrupo());
        sql.append("',");
        sql.append(c.getCodEmpresa());
        sql.append(", ");
        sql.append(c.getAplicaTiempos());
        sql.append(", ");
        sql.append(c.getEstado());
        sql.append(", ");
        sql.append("1, NULL, NULL, 0);");

        if (exist(c)) {
            return 0;
        } else {
            try {
                Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");
                ResultSet resultset = createStatement.executeQuery(sql.toString());
                if (resultset.next()) {
                    //return 1;
                    ResultSet executeQuery = createStatement.executeQuery("CALL proc_tbl_agrupacion(null, null, null, null, null, null, null, null, 5);");
                    if (executeQuery.next()) {
                        id = executeQuery.getInt("id");
                        if (c.getFkRutas() != null) {
                            v = insertRoutes(c.getFkRutas(), id);
                            return v;
                        } else {
                            return 1;
                        }
                    }
                    UtilBD.closeResultSet(executeQuery);
                }
                UtilBD.closeResultSet(resultset);
            } catch (SQLException ex) {
                System.err.println(ex);
            } finally {

                UtilBD.closePreparedStatement(ps);
                pila_con.liberarConexion(con);
            }
        }
        return 0;
    }

    public static int insertOneRelationShipGroup(GrupoMovil gm) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tbl_agrupacion_vehiculo (FK_AGRUPACION, FK_VEHICULO, ESTADO) VALUES (?,?,?)");
        if (exist(gm)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, gm.getFkGrupo());
                ps.setInt(2, gm.getFkVehiculo());
                ps.setInt(3, gm.getEstado());
                int retorno = ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idAgrupacion = rs.getInt(1);
                }
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
        }/*FIN ELSE*/
        return 0;
    }

    public static int insertRoutes(int c[], int id) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet executeQuery = null;
        StringBuilder sql = new StringBuilder();
        if (c.length <= 0) {
            return 0;
        } else {
            try {

                for (int i = 0; i < c.length; i++) {
                    sql.append("CALL proc_tbl_agrupacion_ruta (NULL, ");
                    sql.append(id);
                    sql.append(", ");
                    sql.append(c[i]);
                    sql.append(", ");
                    sql.append("1, 1, NULL, 0);");
                    Statement createStatement = con.createStatement();
                    int retorno = createStatement.executeUpdate("START TRANSACTION;");
                    executeQuery = createStatement.executeQuery(sql.toString());
                    sql.delete(0, sql.length());
                }

                if (executeQuery.next()) {
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

    public static GrupoMovil selectByOne(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoMovil grupoEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_agrupacion (");
        sql.append(c.getId());
        sql.append(", NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            grupoEncontrado = new GrupoMovil();
            if (resultset.next()) {
                grupoEncontrado.setId(resultset.getInt("PK_AGRUPACION"));
                grupoEncontrado.setCodEmpresa(resultset.getInt("FK_EMPRESA"));
                grupoEncontrado.setNombreGrupo(resultset.getString("NOMBRE"));
                grupoEncontrado.setAplicaTiempos(resultset.getInt("APLICARTIEMPOS"));
                grupoEncontrado.setEstado(resultset.getInt("ESTADO"));
            }
            return grupoEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static GrupoVehiculo selectRelationMovilGroup(GrupoMovil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoVehiculo grupoEncontrado = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM tbl_agrupacion_vehiculo WHERE (PK_AGRUPACION_VEHICULO =?) AND (ESTADO = 1);");
       
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());            
            rs = ps.executeQuery();
            grupoEncontrado = null;
            if (rs.next()) {
                grupoEncontrado = new GrupoVehiculo();
                grupoEncontrado.setId(rs.getInt("PK_AGRUPACION_VEHICULO"));
                grupoEncontrado.setIdVehiculo(rs.getInt("FK_VEHICULO"));
                grupoEncontrado.setIdGrupo(rs.getInt("FK_VEHICULO"));                
                grupoEncontrado.setEstado(rs.getInt("ESTADO"));
                return grupoEncontrado;
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
        return grupoEncontrado;
    }
    
    
     public static GrupoMovil selectOneMovilGroup(GrupoMovil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoMovil grupoEncontrado = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM tbl_agrupacion WHERE (PK_AGRUPACION =?) AND (ESTADO = 1);");
       
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());            
            rs = ps.executeQuery();
            grupoEncontrado = null;
            if (rs.next()) {
                grupoEncontrado = new GrupoMovil();
                grupoEncontrado.setId(rs.getInt("PK_AGRUPACION"));
                grupoEncontrado.setNombreGrupo(rs.getString("NOMBRE"));
                grupoEncontrado.setCodEmpresa(rs.getInt("FK_EMPRESA"));
                grupoEncontrado.setAplicaTiempos(rs.getInt("APLICARTIEMPOS"));
                grupoEncontrado.setEstado(rs.getInt("ESTADO"));
                return grupoEncontrado;
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
        return grupoEncontrado;
    }


    
    
    public static ArrayList<GrupoVehiculo> selectByAllMovilesGroup(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoVehiculo grupoEncontrado;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT av.PK_AGRUPACION_VEHICULO, av.FK_VEHICULO, av.FK_AGRUPACION, av.ESTADO, v.PLACA, v.NUM_INTERNO, v.ESTADO FROM tbl_agrupacion_vehiculo as av INNER JOIN tbl_vehiculo as v ON av.FK_VEHICULO = v.PK_VEHICULO WHERE (av.FK_AGRUPACION =?) AND (av.ESTADO = ?);");

        ArrayList vh = new ArrayList();
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getEstado());
            rs = ps.executeQuery();
            grupoEncontrado = null;
            while (rs.next()) {
                grupoEncontrado = new GrupoVehiculo();
                grupoEncontrado.setId(rs.getInt("PK_AGRUPACION_VEHICULO"));
                grupoEncontrado.setIdVehiculo(rs.getInt("FK_VEHICULO"));
                grupoEncontrado.setIdGrupo(rs.getInt("FK_AGRUPACION"));
                grupoEncontrado.setPlaca(rs.getString("PLACA"));
                grupoEncontrado.setNumInterno(rs.getString("NUM_INTERNO"));
                grupoEncontrado.setEstado(rs.getInt("ESTADO"));

                vh.add(grupoEncontrado);
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

    public static ArrayList<Movil> searchAllMovilesNotGroup(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil vehiculoEncontrado;
        StringBuilder sql = new StringBuilder();

        //sql.append("SELECT v.PK_VEHICULO, v.PLACA FROM tbl_vehiculo as v WHERE (v.ESTADO=?) AND (v.PK_VEHICULO NOT IN (SELECT av.FK_VEHICULO FROM tbl_agrupacion_vehiculo AS av WHERE (av.FK_AGRUPACION = ?) AND (av.ESTADO = ?) ORDER BY av.FK_VEHICULO)) ORDER BY v.PK_VEHICULO;");
        sql.append("SELECT * FROM tbl_vehiculo as v WHERE (v.ESTADO=?) AND (v.PK_VEHICULO NOT IN (SELECT av.FK_VEHICULO FROM tbl_agrupacion_vehiculo AS av WHERE (av.ESTADO = ?) ORDER BY av.FK_VEHICULO)) ORDER BY v.PK_VEHICULO;");

        ArrayList<Movil> vh = new ArrayList();
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getEstado());
            ps.setInt(2, c.getEstado());

            rs = ps.executeQuery();
            vehiculoEncontrado = null;
            while (rs.next()) {
                vehiculoEncontrado = new Movil();
                vehiculoEncontrado.setId(rs.getInt("PK_VEHICULO"));
                vehiculoEncontrado.setNumeroInterno(rs.getString("NUM_INTERNO"));
                vehiculoEncontrado.setPlaca(rs.getString("PLACA"));
                vh.add(vehiculoEncontrado);
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

    public static ArrayList<Movil> searchAllMovilesGroup(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil vehiculoEncontrado;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM tbl_vehiculo as v WHERE (v.ESTADO=?) ORDER BY v.PK_VEHICULO;");
        ArrayList<Movil> vh = new ArrayList();
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            vehiculoEncontrado = null;
            while (rs.next()) {
                vehiculoEncontrado = new Movil();
                vehiculoEncontrado.setId(rs.getInt("PK_VEHICULO"));
                vehiculoEncontrado.setNumeroInterno(rs.getString("NUM_INTERNO"));
                vehiculoEncontrado.setPlaca(rs.getString("PLACA"));
                vh.add(vehiculoEncontrado);
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

    public static ArrayList<GrupoMovil> getGroupsByGPSHardware(String fkGPSHardware) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement s = null;
        ResultSet rs = null;

        String sql = "SELECT b.pk_agrupacion as PK_AGRUPACION,  b.nombre as NOMBRE"
                + "	FROM		"
                + "	(		"
                + "		SELECT fk_vehiculo,fk_hardware,fk_gps FROM tbl_gps_hardware WHERE fk_hardware IN (" + fkGPSHardware + ") AND estado = 1		"
                + "	) as a		"
                + "	INNER JOIN 		"
                + "	(		"
                + "		SELECT d.pk_agrupacion, d.nombre, c.fk_vehiculo FROM"
                + "			("
                + "			SELECT fk_vehiculo, fk_agrupacion FROM tbl_agrupacion_vehiculo WHERE estado = 1 "
                + "			) as c"
                + "			INNER JOIN "
                + "			("
                + "			SELECT pk_agrupacion , nombre FROM tbl_agrupacion where estado = 1"
                + "			) as d"
                + "		ON c.fk_agrupacion = d.pk_agrupacion	"
                + "	) as b		"
                + "ON a.fk_vehiculo = b.fk_vehiculo GROUP BY PK_AGRUPACION ORDER BY NOMBRE";

        try {
            s = con.createStatement();
            rs = s.executeQuery(sql);

            ArrayList<GrupoMovil> list = new ArrayList();
            GrupoMovil gm;

            while (rs.next()) {
                gm = new GrupoMovil();
                gm.setFkGrupo(rs.getInt("PK_AGRUPACION"));
                gm.setNombreGrupo(rs.getString("NOMBRE"));
                list.add(gm);
            }
            return list;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(s);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int searchMovil(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movil vehiculoEncontrado;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT av.FK_VEHICULO FROM tbl_agrupacion_vehiculo AS av WHERE (av.FK_VEHICULO = ?) AND (av.ESTADO = ?);");

        ArrayList<Movil> vh = new ArrayList();
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getEstado());

            rs = ps.executeQuery();
            vehiculoEncontrado = null;
            if (rs.next()) {
                return 1;
                /*ve = new Movil();
                    ve.setId(rs.getInt("FK_VEHICULO"));                    
                    vh.add(ve);*/
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

    public static GrupoVehiculo searchGroupByMovil(Movil c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoVehiculo ve;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT PK_AGRUPACION_VEHICULO, FK_AGRUPACION, FK_VEHICULO FROM tbl_agrupacion_vehiculo WHERE (FK_VEHICULO = ?) AND (ESTADO = 1);");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            ve = null;
            if (rs.next()) {
                ve = new GrupoVehiculo();
                ve.setId(rs.getInt("PK_AGRUPACION_VEHICULO"));
                ve.setIdGrupo(rs.getInt("FK_AGRUPACION"));
                ve.setIdVehiculo(rs.getInt("FK_VEHICULO"));
                return ve;
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
        return null;
    }

    public static GrupoMovil selectByOneNew(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        GrupoMovil grupoEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_agrupacion (");
        sql.append(c.getId());
        sql.append(", NULL, NULL, NULL, 1, NULL, NULL, NULL, 3);");

        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            grupoEncontrado = new GrupoMovil();
            if (resultset.next()) {
                grupoEncontrado.setId(resultset.getInt("PK_AGRUPACION"));
                grupoEncontrado.setCodEmpresa(resultset.getInt("FK_EMPRESA"));
                grupoEncontrado.setNombreGrupo(resultset.getString("NOMBRE"));
                grupoEncontrado.setAplicaTiempos(resultset.getInt("APLICARTIEMPOS"));
                grupoEncontrado.setEstado(resultset.getInt("ESTADO"));
            }
            return grupoEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static int deleteCarsOfGroup(GrupoMovil g) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM tbl_agrupacion_vehiculo WHERE FK_AGRUPACION = ?");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, g.getFkGrupo());
            int r = ps.executeUpdate();
            if (r > 0) {
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

    public static int addNewOrderCarsOfGroup(GrupoMovil g) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        int r = 0;

        sql.append("INSERT INTO tbl_agrupacion_vehiculo (FK_AGRUPACION, FK_VEHICULO, ESTADO) VALUES (?,?,?)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            for (int i = 0; i < g.getFkVehiculosNuevoOrden().size(); i++) {
                ps.setInt(1, g.getFkGrupo());
                ps.setInt(2, ((VehiculoNuevoListado) g.getFkVehiculosNuevoOrden().get(i)).getId());
                ps.setInt(3, g.getEstado());
                //System.out.println("INSERT INTO tbl_agrupacion_vehiculo (FK_AGRUPACION, FK_VEHICULO, ESTADO) VALUES ("+g.getFkGrupo()+","+( (VehiculoNuevoListado) g.getFkVehiculosNuevoOrden().get(i)).getId()+","+g.getEstado()+")");    
                r += ps.executeUpdate();
            }

            con.commit();
            if (r > 0) {
                return r;
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

    public static int update(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_agrupacion (");
        sql.append(c.getId());
        sql.append(", '");
        sql.append(c.getNombreGrupo());
        sql.append("', ");
        sql.append(c.getCodEmpresa());
        sql.append(", ");
        sql.append(c.getAplicaTiempos());
        sql.append(", 1, 1, NULL, NULL, 1);");

        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            return 1;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return 0;
    }

    public static boolean updateState(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_agrupacion (");
        sql.append(c.getId());
        sql.append(", NULL, NULL, NULL, ");
        sql.append(c.getEstado());
        sql.append(", 1, NULL, NULL, 4);");
        try {
            Statement createStatement = con.createStatement();
            int executeUpdate = createStatement.executeUpdate("START TRANSACTION;");
            ResultSet resultset = createStatement.executeQuery(sql.toString());
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closePreparedStatement(ps_update);
            pila_con.liberarConexion(con);
        }
        return false;
    }

    public static int updateStateMovilInGroup(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_agrupacion_vehiculo SET ESTADO =? WHERE FK_VEHICULO = ?");

        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getEstado());
            ps_update.setInt(2, c.getFkVehiculo());

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

    public static int updateRelationShipGroupCarById(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_agrupacion_vehiculo SET FK_AGRUPACION =? WHERE PK_AGRUPACION_VEHICULO = ?");

        try {
            con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getFkGrupo());
            ps_update.setInt(2, c.getId());
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

    public static int selectById(int idMovil, int idGrupo) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_agrupacion_vehiculo as av WHERE av.FK_VEHICULO=? AND av.FK_AGRUPACION=?");
        //System.out.println("SELECT * FROM tbl_agrupacion_vehiculo as av WHERE av.FK_VEHICULO="+idMovil+" AND av.FK_AGRUPACION="+idGrupo);
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, idMovil);
            ps.setInt(2, idGrupo);
            rs = ps.executeQuery();
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

    public static int addMoviltoGroup1(GrupoMovil c) throws ExisteRegistroBD {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;
        ResultSet executeQuery = null;
        StringBuilder sql = new StringBuilder();

        try {

            for (int i = 0; i < c.getFkVehiculos().size(); i++) {
                sql.append("CALL proc_tbl_agrupacion_vehiculo (NULL, ");
                sql.append(c.getId());
                sql.append(", ");
                sql.append(c.getFkVehiculos().get(i).intValue());
                sql.append(", 1, 0);");
                Statement createStatement = con.createStatement();
                int retorno = createStatement.executeUpdate("START TRANSACTION;");
                executeQuery = createStatement.executeQuery(sql.toString());
                sql.delete(0, sql.length());
            }
            if (executeQuery.next()) {
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

    public static int addMoviltoGroup(GrupoMovil p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        ResultSet rs = null;
        int retorno = 0;
        sql.append("INSERT INTO tbl_agrupacion_vehiculo (FK_AGRUPACION, FK_VEHICULO, ESTADO) VALUES (?,?,?)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            for (int i = 0; i < p.getFkVehiculos().size(); i++) {
                ps.setInt(1, p.getFkGrupo());
                ps.setInt(2, p.getFkVehiculos().get(i));
                ps.setInt(3, p.getEstado());
                //System.out.println("INSERT INTO tbl_agrupacion_vehiculo (FK_AGRUPACION, FK_VEHICULO, ESTADO) VALUES ("+p.getFkGrupo()+", "+p.getFkVehiculos().get(i)+","+p.getEstado()+")");    
                retorno += ps.executeUpdate();
            }

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

        return 0;
    }

    public static ArrayList<GrupoMovilDespacho> selectGrupoMoviles_despacho() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "   av.FK_AGRUPACION,"
                + "   v.NUM_INTERNO,"
                + "   v.PLACA"
                + " FROM tbl_agrupacion_vehiculo AS av"
                + " INNER JOIN tbl_vehiculo AS v ON"
                + "   av.FK_VEHICULO = v.PK_VEHICULO AND v.ESTADO = 1"
                + " WHERE av.FK_AGRUPACION IN ("
                + "   SELECT a.PK_AGRUPACION FROM tbl_agrupacion AS a WHERE a.ESTADO = 1"
                + " )"
                + " ORDER BY av.FK_AGRUPACION ASC, v.PLACA ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ArrayList<GrupoMovilDespacho> lstgrupomoviles_despacho
                    = new ArrayList<GrupoMovilDespacho>();
            while (rs.next()) {
                GrupoMovilDespacho gmd = new GrupoMovilDespacho();
                gmd.setIdGrupoMovil(rs.getInt("FK_AGRUPACION"));
                gmd.setNumeroInterno(rs.getString("NUM_INTERNO"));
                gmd.setPlaca(rs.getString("PLACA"));

                lstgrupomoviles_despacho.add(gmd);
            }
            return lstgrupomoviles_despacho;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public static GrupoMovil selectGroupforDefault(GrupoMovil p) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_agrupacion WHERE (NOMBRE =?) AND (ESTADO = 1)");

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, p.getNombreGrupo());
            rs = ps.executeQuery();
            GrupoMovil a = new GrupoMovil();
            if (rs.next()) {
                a.setId(rs.getInt("PK_AGRUPACION"));
                a.setNombreGrupo(rs.getString("NOMBRE"));
                a.setCodEmpresa(rs.getInt("FK_EMPRESA"));
                a.setAplicaTiempos(rs.getInt("APLICARTIEMPOS"));
                a.setEstado(rs.getInt("ESTADO"));
            }
            return a;
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
        return null;
    }

}//FIN DE CLASE
