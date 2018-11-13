/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.EntidadConfigurable;
import com.registel.rdw.logica.Perfil;
import static com.registel.rdw.utils.ConstantesSesion.VALOR_POSITIVO;
import com.registel.rdw.utils.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Richard Mejia
 * @date 01/10/2018 DAO para acceder a la tabla tbl_configuracion_liquidacion.
 */
public class ConfiguracionLiquidacionBD extends DAOConfigurable {

    private static final String TABLA = "tbl_configuracion_liquidacion";
    private static final String PRIMARY_KEY = "id";
    private static final String FK_PERFIL = "fk_perfil";
    private static final String NOMBRE_CONFIGURACION_LIQUIDACION = "nombre_configuracion_liquidacion";
    private static final String ETQ_PASAJEROS_1 = "etq_pasajeros1";
    private static final String ETQ_PASAJEROS_2 = "etq_pasajeros2";
    private static final String ETQ_PASAJEROS_3 = "etq_pasajeros3";
    private static final String ETQ_PASAJEROS_4 = "etq_pasajeros4";
    private static final String ETQ_PASAJEROS_5 = "etq_pasajeros5";
    private static final String ETQ_TOTAL_1 = "etq_total1";
    private static final String ETQ_TOTAL_2 = "etq_total2";
    private static final String ETQ_TOTAL_3 = "etq_total3";
    private static final String ETQ_TOTAL_4 = "etq_total4";
    private static final String ETQ_TOTAL_5 = "etq_total5";
    private static final String ETQ_TOTAL_6 = "etq_total6";
    private static final String ETQ_TOTAL_7 = "etq_total7";
    private static final String ETQ_TOTAL_8 = "etq_total8";
    private static final String ETQ_REP_PASAJEROS_1 = "etq_rep_pasajeros1";
    private static final String ETQ_REP_PASAJEROS_2 = "etq_rep_pasajeros2";
    private static final String ETQ_REP_PASAJEROS_3 = "etq_rep_pasajeros3";
    private static final String ETQ_REP_PASAJEROS_4 = "etq_rep_pasajeros4";
    private static final String ETQ_REP_PASAJEROS_5 = "etq_rep_pasajeros5";
    private static final String ETQ_REP_TOTAL_1 = "etq_rep_total1";
    private static final String ETQ_REP_TOTAL_2 = "etq_rep_total2";
    private static final String ETQ_REP_TOTAL_3 = "etq_rep_total3";
    private static final String ETQ_REP_TOTAL_4 = "etq_rep_total4";
    private static final String ETQ_REP_TOTAL_5 = "etq_rep_total5";
    private static final String ETQ_REP_TOTAL_6 = "etq_rep_total6";
    private static final String ETQ_REP_TOTAL_7 = "etq_rep_total7";
    private static final String ETQ_REP_TOTAL_8 = "etq_rep_total8";
    private static final String LIQUIDACION_NORMAL = "liquidacion_normal";
    private static final String LIQUIDACION_POR_RUTA = "liquidacion_por_ruta";
    private static final String LIQUIDACION_POR_TRAMO = "liquidacion_por_tramo";
    private static final String LIQUIDACION_POR_TIEMPO = "liquidacion_por_tiempo";
    private static final String LIQUIDACION_SOLO_PASAJEROS = "liquidacion_solo_pasajeros";

    private static final Object[] PARAMETROS_INSERT_UPDATE = {FK_PERFIL, NOMBRE_CONFIGURACION_LIQUIDACION, 
        ETQ_PASAJEROS_1, ETQ_PASAJEROS_2, ETQ_PASAJEROS_3, ETQ_PASAJEROS_4, ETQ_PASAJEROS_5, ETQ_TOTAL_1, ETQ_TOTAL_2, 
        ETQ_TOTAL_3, ETQ_TOTAL_4, ETQ_TOTAL_5, ETQ_TOTAL_6, ETQ_TOTAL_7, ETQ_TOTAL_8, ETQ_REP_PASAJEROS_1, 
        ETQ_REP_PASAJEROS_2, ETQ_REP_PASAJEROS_3, ETQ_REP_PASAJEROS_4, ETQ_REP_PASAJEROS_5, ETQ_REP_TOTAL_1, 
        ETQ_REP_TOTAL_2, ETQ_REP_TOTAL_3, ETQ_REP_TOTAL_4, ETQ_REP_TOTAL_5, ETQ_REP_TOTAL_6, ETQ_REP_TOTAL_7, 
        ETQ_REP_TOTAL_8, LIQUIDACION_NORMAL, LIQUIDACION_POR_RUTA, LIQUIDACION_POR_TRAMO, LIQUIDACION_POR_TIEMPO,
        LIQUIDACION_SOLO_PASAJEROS, ESTADO};

    private static final int PARAMETROS_LENGTH = PARAMETROS_INSERT_UPDATE.length;

    public static void asignarParametros(PreparedStatement ps, EntidadConfigurable entidadConfigurable,
            boolean asignarPk) throws SQLException, Exception {

        if (entidadConfigurable instanceof ConfiguracionLiquidacion == false) {
            throw new Exception();
        }

        ConfiguracionLiquidacion entidadDefinida = (ConfiguracionLiquidacion) entidadConfigurable;
        int pos = 1;

        ps.setInt(pos++, entidadDefinida.getFkPerfil());
        ps.setString(pos++, entidadDefinida.getNombreConfiguracionLiquidacion());
        ps.setString(pos++, entidadDefinida.getEtqPasajeros1());
        ps.setString(pos++, entidadDefinida.getEtqPasajeros2());
        ps.setString(pos++, entidadDefinida.getEtqPasajeros3());
        ps.setString(pos++, entidadDefinida.getEtqPasajeros4());
        ps.setString(pos++, entidadDefinida.getEtqPasajeros5());
        ps.setString(pos++, entidadDefinida.getEtqTotal1());
        ps.setString(pos++, entidadDefinida.getEtqTotal2());
        ps.setString(pos++, entidadDefinida.getEtqTotal3());
        ps.setString(pos++, entidadDefinida.getEtqTotal4());
        ps.setString(pos++, entidadDefinida.getEtqTotal5());
        ps.setString(pos++, entidadDefinida.getEtqTotal6());
        ps.setString(pos++, entidadDefinida.getEtqTotal7());
        ps.setString(pos++, entidadDefinida.getEtqTotal8());
        ps.setString(pos++, entidadDefinida.getEtqRepPasajeros1());
        ps.setString(pos++, entidadDefinida.getEtqRepPasajeros2());
        ps.setString(pos++, entidadDefinida.getEtqRepPasajeros3());
        ps.setString(pos++, entidadDefinida.getEtqRepPasajeros4());
        ps.setString(pos++, entidadDefinida.getEtqRepPasajeros5());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal1());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal2());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal3());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal4());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal5());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal6());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal7());
        ps.setString(pos++, entidadDefinida.getEtqRepTotal8());
        ps.setBoolean(pos++, entidadDefinida.isLiquidacionNormal());
        ps.setBoolean(pos++, entidadDefinida.isLiquidacionPorRuta());
        ps.setBoolean(pos++, entidadDefinida.isLiquidacionPorTramo());
        ps.setBoolean(pos++, entidadDefinida.isLiquidacionPorTiempo());
        ps.setBoolean(pos++, entidadDefinida.isLiquidacionSoloPasajeros());
        ps.setInt(pos++, entidadDefinida.getEstado());

        if (asignarPk) {
            ps.setLong(pos++, entidadDefinida.getId());
        }
    }

    public static ConfiguracionLiquidacion asignarValoresResultSet(ResultSet rs) {
        ConfiguracionLiquidacion entidadDefinida = new ConfiguracionLiquidacion();

        try {
            entidadDefinida.setId(rs.getLong(PRIMARY_KEY));
            entidadDefinida.setFkPerfil(rs.getInt(FK_PERFIL));
            entidadDefinida.setNombreConfiguracionLiquidacion(rs.getString(NOMBRE_CONFIGURACION_LIQUIDACION));
            entidadDefinida.setEtqPasajeros1(rs.getString(ETQ_PASAJEROS_1));
            entidadDefinida.setEtqPasajeros2(rs.getString(ETQ_PASAJEROS_2));
            entidadDefinida.setEtqPasajeros3(rs.getString(ETQ_PASAJEROS_3));
            entidadDefinida.setEtqPasajeros4(rs.getString(ETQ_PASAJEROS_4));
            entidadDefinida.setEtqPasajeros5(rs.getString(ETQ_PASAJEROS_5));
            entidadDefinida.setEtqTotal1(rs.getString(ETQ_TOTAL_1));
            entidadDefinida.setEtqTotal2(rs.getString(ETQ_TOTAL_2));
            entidadDefinida.setEtqTotal3(rs.getString(ETQ_TOTAL_3));
            entidadDefinida.setEtqTotal4(rs.getString(ETQ_TOTAL_4));
            entidadDefinida.setEtqTotal5(rs.getString(ETQ_TOTAL_5));
            entidadDefinida.setEtqTotal6(rs.getString(ETQ_TOTAL_6));
            entidadDefinida.setEtqTotal7(rs.getString(ETQ_TOTAL_7));
            entidadDefinida.setEtqTotal8(rs.getString(ETQ_TOTAL_8));
            entidadDefinida.setEtqRepPasajeros1(rs.getString(ETQ_REP_PASAJEROS_1));
            entidadDefinida.setEtqRepPasajeros2(rs.getString(ETQ_REP_PASAJEROS_2));
            entidadDefinida.setEtqRepPasajeros3(rs.getString(ETQ_REP_PASAJEROS_3));
            entidadDefinida.setEtqRepPasajeros4(rs.getString(ETQ_REP_PASAJEROS_4));
            entidadDefinida.setEtqRepPasajeros5(rs.getString(ETQ_REP_PASAJEROS_5));
            entidadDefinida.setEtqRepTotal1(rs.getString(ETQ_REP_TOTAL_1));
            entidadDefinida.setEtqRepTotal2(rs.getString(ETQ_REP_TOTAL_2));
            entidadDefinida.setEtqRepTotal3(rs.getString(ETQ_REP_TOTAL_3));
            entidadDefinida.setEtqRepTotal4(rs.getString(ETQ_REP_TOTAL_4));
            entidadDefinida.setEtqRepTotal5(rs.getString(ETQ_REP_TOTAL_5));
            entidadDefinida.setEtqRepTotal6(rs.getString(ETQ_REP_TOTAL_6));
            entidadDefinida.setEtqRepTotal7(rs.getString(ETQ_REP_TOTAL_7));
            entidadDefinida.setEtqRepTotal8(rs.getString(ETQ_REP_TOTAL_8));
            entidadDefinida.setLiquidacionNormal(rs.getBoolean(LIQUIDACION_NORMAL));
            entidadDefinida.setLiquidacionPorRuta(rs.getBoolean(LIQUIDACION_POR_RUTA));
            entidadDefinida.setLiquidacionPorTramo(rs.getBoolean(LIQUIDACION_POR_TRAMO));
            entidadDefinida.setLiquidacionPorTiempo(rs.getBoolean(LIQUIDACION_POR_TIEMPO));
            entidadDefinida.setLiquidacionSoloPasajeros(rs.getBoolean(LIQUIDACION_SOLO_PASAJEROS));
            entidadDefinida.setEstado(rs.getInt(ESTADO));
        } catch (SQLException e) {
            Logger.getLogger(ConfiguracionLiquidacionBD.class.getName()).log(Level.SEVERE, null, e);
        }

        return entidadDefinida;
    }

    public static boolean insert(EntidadConfigurable entidadConfigurable) {
        return insert(entidadConfigurable, TABLA, PARAMETROS_LENGTH, PARAMETROS_INSERT_UPDATE,
                ConfiguracionLiquidacionBD.class);
    }

    public static boolean update(EntidadConfigurable entidadConfigurable) {
        return update(entidadConfigurable, TABLA, PRIMARY_KEY, PARAMETROS_LENGTH,
                PARAMETROS_INSERT_UPDATE, ConfiguracionLiquidacionBD.class);
    }

    public static boolean updateEstado(EntidadConfigurable entidadConfigurable) {
        
        if (entidadConfigurable.getEstado()== VALOR_POSITIVO) {
            ConfiguracionLiquidacion configuracion = selectByOne(entidadConfigurable);
            actualizarEstadoTodo(configuracion);
        }
        
        return updateEstado(entidadConfigurable, TABLA, PRIMARY_KEY);
    }

    public static ConfiguracionLiquidacion selectByOne(EntidadConfigurable entidadConfigurable) {
        EntidadConfigurable entidad = selectByOne(entidadConfigurable, TABLA, PRIMARY_KEY,
                ConfiguracionLiquidacionBD.class);

        if (entidad instanceof ConfiguracionLiquidacion) {
            ConfiguracionLiquidacion entidadDefinida = (ConfiguracionLiquidacion) entidad;

            return entidadDefinida;
        }

        return null;
    }

    public static ConfiguracionLiquidacion selectByOne(long pkEntidadConfigurable) {
        EntidadConfigurable entidad = DAOConfigurable.selectByOne(pkEntidadConfigurable, TABLA, PRIMARY_KEY,
                ConfiguracionLiquidacionBD.class);

        if (entidad instanceof ConfiguracionLiquidacion) {
            ConfiguracionLiquidacion entidadDefinida = (ConfiguracionLiquidacion) entidad;

            return entidadDefinida;
        }

        return null;
    }

    public static List<ConfiguracionLiquidacion> select() {
        List<EntidadConfigurable> lista = DAOConfigurable.select(TABLA, ConfiguracionLiquidacionBD.class);
        List<ConfiguracionLiquidacion> listaEntidadesDefinidas = new ArrayList<>();
        
        if (lista != null && !lista.isEmpty()) {
            List<Perfil> perfilesConsultados = new ArrayList<>();
            EntidadConfigurable entidad;
            ConfiguracionLiquidacion configuracionLiquidacionTmp;
            Perfil perfilTmp = new Perfil();
            int listaSize = lista.size();
            int perfilesConsultadosSize;
            boolean yaConsultoPerfil;

            for (int i = 0; i < listaSize; i++) {
                entidad = lista.get(i);

                if (entidad instanceof ConfiguracionLiquidacion) {
                    configuracionLiquidacionTmp = (ConfiguracionLiquidacion) entidad;
                    perfilesConsultadosSize = perfilesConsultados.size();
                    yaConsultoPerfil = false;
                    
                    for (int j = 0; j < perfilesConsultadosSize; j++) {
                        perfilTmp = perfilesConsultados.get(j);
                        
                        if (perfilTmp.getId() == configuracionLiquidacionTmp.getFkPerfil()) {
                            yaConsultoPerfil = true;
                            break;
                        }
                    }
                    
                    if (!yaConsultoPerfil) {
                        perfilTmp = PerfilBD.selectById(configuracionLiquidacionTmp.getFkPerfil());
                        
                        if (perfilTmp != null && perfilTmp.getId() > 0) {
                            perfilesConsultados.add(perfilTmp);
                        }
                    }
                    
                    if (perfilTmp != null && perfilTmp.getId() > 0) {
                        configuracionLiquidacionTmp.setNombrePerfil(perfilTmp.getNombre());
                        listaEntidadesDefinidas.add(configuracionLiquidacionTmp);
                    }
                }
            }
        }
        
        return listaEntidadesDefinidas;
    }
    
    public static boolean existeRegistroActivo(ConfiguracionLiquidacion entidadDefinida) {
        List<ConfiguracionLiquidacion> listaEntidadesDefinidas = select();
        
        if (listaEntidadesDefinidas == null || listaEntidadesDefinidas.isEmpty()) {
            return false;
        }
        
        ConfiguracionLiquidacion configuracionLiquidacionTmp;
        int listaEntidadesDefinidasSize = listaEntidadesDefinidas.size();
        
        for (int i = 0; i < listaEntidadesDefinidasSize; i++) {
            configuracionLiquidacionTmp = listaEntidadesDefinidas.get(i);
            
            if (configuracionLiquidacionTmp.getEstado() == VALOR_POSITIVO && 
                    configuracionLiquidacionTmp.getFkPerfil() == entidadDefinida.getFkPerfil() &&
                    configuracionLiquidacionTmp.getId() != entidadDefinida.getId()) {
                return true;
            }
        }
        
        return false;
    } 
    
    
    public static boolean actualizarEstadoTodo(ConfiguracionLiquidacion entidadConfigurable) {
        PilaConexiones pila_conexion = PilaConexiones.obtenerInstancia();
        Connection connection = pila_conexion.obtenerConexion();
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %1$s SET %2$s = 0 WHERE %3$s = ?", TABLA, ESTADO, FK_PERFIL));
        
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query.toString());
            ps.setInt(1, entidadConfigurable.getFkPerfil());
            int pk = ps.executeUpdate();
            connection.commit();

            if (pk > 0) {
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlEx) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ex);

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException sqlEx) {
                Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, sqlEx);
            }

            UtilBD.closeStatement(ps);
            pila_conexion.liberarConexion(connection);
        }

        return false;
    }

    
    
    public static ConfiguracionLiquidacion obtenerConfiguracionActiva(int idPerfil) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConfiguracionLiquidacion entidadDefinida = null;
        StringBuilder query = new StringBuilder();

        try {
            query.append(String.format("SELECT * from %1$s WHERE %2$s = ? AND %3$s = ?", TABLA, ESTADO, FK_PERFIL));
            ps = con.prepareStatement(query.toString());
            ps.setInt(1, VALOR_POSITIVO);
            ps.setInt(2, idPerfil);
            rs = ps.executeQuery();

            while (rs.next()) {
                entidadDefinida = asignarValoresResultSet(rs);
                return entidadDefinida;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ex);

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ie);
                }
            }
        } catch (SecurityException | IllegalArgumentException ex) {
            Logger.getLogger(DAOConfigurable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }

        return entidadDefinida;
    }
}
