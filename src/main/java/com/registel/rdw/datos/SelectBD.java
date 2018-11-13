
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
import java.util.ArrayList;
import com.registel.rdw.logica.Ciudad;
import com.registel.rdw.logica.Departamento;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Pais;
import com.registel.rdw.logica.Perfil;

import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Propietario;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import com.registel.rdw.logica.GpsMarca;
import com.registel.rdw.logica.GpsModelo;
import com.registel.rdw.logica.Hardware;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.Operador;
import com.registel.rdw.logica.Alarma;
import com.registel.rdw.logica.Aseguradora;
import com.registel.rdw.logica.Base;
import com.registel.rdw.logica.BaseRuta;
import com.registel.rdw.logica.CategoriaLicencia;
import com.registel.rdw.logica.Evento;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Modulo;
import com.registel.rdw.logica.SubModulo;
import com.registel.rdw.logica.SubItemModulo;

import com.registel.rdw.logica.Punto;
import com.registel.rdw.logica.Ruta;
import com.registel.rdw.logica.PuntoRuta;
import com.registel.rdw.logica.Tarifa;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.Motivo;
import com.registel.rdw.logica.RelacionVehiculoConductor;
import com.registel.rdw.logica.RelacionVehiculoEmpresa;
import com.registel.rdw.logica.GrupoMovil;
import com.registel.rdw.logica.CategoriasDeDescuento;
import com.registel.rdw.logica.Despacho;
import com.registel.rdw.logica.Dia;
import com.registel.rdw.logica.GrupoEmpresa;
import com.registel.rdw.logica.GrupoMovilDespacho;
import com.registel.rdw.logica.IntervaloDespacho;
import com.registel.rdw.logica.Moneda;
import com.registel.rdw.logica.ProgramacionRuta;
import com.registel.rdw.logica.ProgramacionRuta_nombre;
import com.registel.rdw.logica.SelectC;
import com.registel.rdw.logica.TarjetaSim;
import com.registel.rdw.logica.ZonaHoraria;
import com.registel.rdw.logica.TipoDocumento;
import com.registel.rdw.logica.VueltaCerrada;
import com.registel.rdw.logica.servidorDeImpresion;
import com.registel.rdw.logica.TarjetaOperacion;
import com.registel.rdw.logica.CentroDiagnostico;
import com.registel.rdw.logica.CentroExpedicion;
import com.registel.rdw.logica.ConfiguracionLiquidacion;
import com.registel.rdw.logica.LicenciaTransito;
import com.registel.rdw.logica.Mantenimiento;
import com.registel.rdw.logica.OficinaTransito;
import com.registel.rdw.logica.Poliza;
import com.registel.rdw.logica.TipoEventoMantenimiento;
import com.registel.rdw.logica.TipoPoliza;
import com.registel.rdw.logica.VehiculoAsegurado;
import com.registel.rdw.logica.VehiculoMantenimiento;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.StringUtils;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class SelectBD {

    private ArrayList<Pais> lstpais;
    private ArrayList<Departamento> lstdpto;
    private ArrayList<Ciudad> lstciudad;
    private ArrayList<Perfil> lstperfil;
    private ArrayList<Conductor> lstconductor;
    private ArrayList<Conductor> lstconductoractivo;
    private ArrayList<Alarma> lstalarma;
    private ArrayList<Evento> lstevento;
    private ArrayList<Movil> lstmovil;
    private ArrayList<Empresa> lstempresa;
    private ArrayList<GrupoEmpresa> lstgempresa;
    private ArrayList<Usuario> lstusuario;
    private ArrayList<Base> lstbase;
    private ArrayList<Base> lstbase_pr;
    private ArrayList<Punto> lstpunto;
    private ArrayList<Punto> lstpuntobase;
    private ArrayList<Ruta> lstruta;
    private ArrayList<Ruta> lstrutas;
    private ArrayList<PuntoRuta> lstpuntoruta;
    private ArrayList<PuntoRuta> lstpuntoruta_completa;
    private ArrayList<Despacho> lstpuntoruta_cruzada;
    private ArrayList<BaseRuta> lstbaseruta;
    private ArrayList<Tarifa> lsttarifa;
    private ArrayList<Motivo> lstmotivo;
    private ArrayList<Modulo> lstmodulo;
    private ArrayList<SubItemModulo> lstitemsubmodulo;
    private ArrayList<servidorDeImpresion> lstitemservidorimpresion;
    private ArrayList<SubModulo> lstsubmodulo;
    private ArrayList<RelacionVehiculoConductor> lstrelacionvehiculoconductor;
    private ArrayList<RelacionVehiculoEmpresa> lstrelacionvehiculoempresa;
    private ArrayList<GrupoMovil> lstgrupomoviles;
    private ArrayList<GrupoMovil> lstgrupovehiculos;
    private ArrayList<CategoriasDeDescuento> lstcategorias;
    private ArrayList<PuntoRuta> lstpuntoruta_despacho;
    private ArrayList<GrupoMovilDespacho> lstgrupomoviles_despacho;
    private ArrayList<Despacho> lstdespacho;
    private ArrayList<IntervaloDespacho> lstintervalo_despacho;
    private ArrayList<IntervaloDespacho> lstintervalo_despacho_manual;
    private ArrayList<Ruta> lstruta_despacho;
    private ArrayList<ProgramacionRuta> lst_pgruta;
    private ArrayList<ProgramacionRuta> lst_pgruta_completa;
    private ArrayList<ProgramacionRuta_nombre> lst_pgruta_nombre;
    private ArrayList<Dia> lst_diasfestivo;
    private ArrayList<Moneda> lst_moneda;
    private ArrayList<ZonaHoraria> lst_zonahoraria;
    private ArrayList<TipoDocumento> lst_tipodocumento;
    private ArrayList<VueltaCerrada> lst_vueltacerrada;
    private ArrayList<Propietario> lst_propietario;
    private ArrayList<Hardware> lst_hardware;
    private ArrayList<RelationShipHardwareGpsCar> lst_gpshardware;
    private ArrayList<GpsMarca> lst_gpsmarca;
    private ArrayList<GpsModelo> lst_gpsmodelo;
    private ArrayList<GpsRegistrado> lst_gpsregistrado;
    private ArrayList<Operador> lst_operador;
    private ArrayList<TarjetaSim> lst_tarjeta_sim;
    private ArrayList<TarjetaOperacion> lstDocumentosVehiculo;
    private ArrayList<CentroDiagnostico> lstCentroDiagnostico;
    private ArrayList<CentroExpedicion> lstCentroExpedicion;

    private List<CategoriaLicencia> lst_categoriasLicencia;
    private List<OficinaTransito> lst_oficinasTransito;
    private List<LicenciaTransito> lst_licenciasTransito;
    private List<Aseguradora> lst_aseguradoras;
    private List<Poliza> lst_polizas;
    private List<VehiculoAsegurado> lst_vehiculosAsegurados;
    private List<TipoEventoMantenimiento> lst_tipoEventoMantenimiento;
    private List<Mantenimiento> lst_mantenimiento;
    private List<TipoPoliza> lst_tipoPoliza;
    private List<VehiculoMantenimiento> lst_vehiculosMantenimiento;
    private List<ConfiguracionLiquidacion> lst_configuracionesDeLiquidacion;                

    private boolean usr_propietario = false;
    private int id_usr = -1;
    private int id_empresa;

    public SelectBD(Usuario usr) {
        verificarPerfilUsuario(usr);
        select(true);
    }

    public SelectBD(HttpServletRequest request) {
        if (request != null && request.getSession(false) != null) {
            HttpSession session = request.getSession();
            Usuario usr = (Usuario) session.getAttribute("login");
            verificarPerfilUsuario(usr);
        }
        select(true);
    }

    public SelectBD(boolean all) {
        select(all);
    }

    public SelectBD(int id) {
        selectList(id);
    }

    public SelectBD(int lst_id[]) {
        for (int i = 0; i < lst_id.length; i++) {
            selectList(lst_id[i]);
        }
    }

    public void verificarPerfilUsuario(Usuario usr) {
        if (usr != null) {
            if (usr.esPropietario()) {
                usr_propietario = true;
                id_usr = usr.getId();
            }
            id_empresa = usr.getIdempresa();
        }
    }

    public void select(boolean all) {
        if (all) {
            selectPais();
            selectDpto();
            selectCiudad();
            selectEmpresa();
            selectGrupoEmpresa();
            selectPerfil();
            selectUsuario();
            selectConductor();
            selectConductorActivo();
            selectAlarma();
            selectEvento();
            selectMovil();
            selectBase();
            selectBase_PuntoRuta();
            selectPunto();
            selectPuntoBase();
            selectPuntoRuta();
            selectPuntoRuta_completa();
            selectPuntoRuta_cruzada();
            selectPuntoRuta_despacho();
            selectBaseRuta();
            selectRuta();
            selectTarifa();
            selectMotivo();
            selectRelacionVehiculoConductor();
            selectRelacionVehiculoEmpresa();
            selectGrupoMoviles();
            selectGrupoVehiculos();
            selectGrupoMoviles_despacho();
            selectRutas1();
            selectCategorias();
            selectModulos();
            selectDespacho();
            selectIntervaloDespacho();
            selectIntervaloDespachoManual();
            selectRutaDespacho();
            selectDiasFestivo();
            selectProgramacionRuta();
            selectProgramacionRuta_completa();
            selectProgramacionRuta_nombre();
            selectProgramacionRuta_activa();
            selectMoneda();
            selectZonaHoraria();
            selectTipoDocumento();
            selectservidoresDeImpresion();
            selectVueltaCerrada();
            selectPropietario();
            selectHardware();
            selectGpsHardware();
            selectGpsRegistrado();
            selectGpsMarca();
            selectGpsModelo();
            selectOperador();
            selectTarjetaSim();
            //selectCentroDiagnostico();
            //selectCentroExpedicion();
            //selectTarjetaDeOperacion();
            selectCategoriaLicencia();
            selectOficinaTransito();
            selectLicenciaTransito();
            selectAseguradora();
            selectPoliza();
            selectVehiculoAsegurado();
            selectTipoEventoMantenimiento();
            selectMantenimiento();
            selectTipoPoliza();
            selectVehiculosMantenimiento();
            selectConfiguracionesDeLiquidacion();
        }
    }

    public void selectList(int ID_LST) {
        if (ID_LST == SelectC.LST_PAIS.ordinal()) {
            selectPais();
        }
        if (ID_LST == SelectC.LST_DPTO.ordinal()) {
            selectDpto();
        }
        if (ID_LST == SelectC.LST_CIUDAD.ordinal()) {
            selectCiudad();
        }
        if (ID_LST == SelectC.LST_PERFIL.ordinal()) {
            selectPerfil();
        }
        if (ID_LST == SelectC.LST_CONDUCTOR.ordinal()) {
            selectConductor();
        }
        if (ID_LST == SelectC.LST_CONDUCTOR_ACTIVO.ordinal()) {
            selectConductorActivo();
        }
        if (ID_LST == SelectC.LST_ALARMA.ordinal()) {
            selectAlarma();
        }
        if (ID_LST == SelectC.LST_EVENTO.ordinal()) {
            selectEvento();
        }
        if (ID_LST == SelectC.LST_MOVIL.ordinal()) {
            selectMovil();
        }
        if (ID_LST == SelectC.LST_EMPRESA.ordinal()) {
            selectEmpresa();
        }
        if (ID_LST == SelectC.LST_GEMPRESA.ordinal()) {
            selectGrupoEmpresa();
        }
        if (ID_LST == SelectC.LST_USUARIO.ordinal()) {
            selectUsuario();
        }
        if (ID_LST == SelectC.LST_BASE.ordinal()) {
            selectBase();
        }
        if (ID_LST == SelectC.LST_BASE_PR.ordinal()) {
            selectBase_PuntoRuta();
        }
        if (ID_LST == SelectC.LST_PUNTO.ordinal()) {
            selectPunto();
        }
        if (ID_LST == SelectC.LST_PUNTOBASE.ordinal()) {
            selectPuntoBase();
        }
        if (ID_LST == SelectC.LST_RUTA.ordinal()) {
            selectRuta();
        }
        if (ID_LST == SelectC.LST_RUTAS.ordinal()) {
            selectRutas1();
        }
        if (ID_LST == SelectC.LST_PUNTORUTA.ordinal()) {
            selectPuntoRuta();
        }
        if (ID_LST == SelectC.LST_PUNTORUTA_COMPLETA.ordinal()) {
            selectPuntoRuta_completa();
        }
        if (ID_LST == SelectC.LST_PUNTORUTA_CRUZADA.ordinal()) {
            selectPuntoRuta_cruzada();
        }
        if (ID_LST == SelectC.LST_BASERUTA.ordinal()) {
            selectBaseRuta();
        }
        if (ID_LST == SelectC.LST_TARIFA.ordinal()) {
            selectTarifa();
        }
        if (ID_LST == SelectC.LST_MOTIVO.ordinal()) {
            selectMotivo();
        }
        if (ID_LST == SelectC.LST_MODULO.ordinal()) {
            selectModulos();
        }
        if (ID_LST == SelectC.LST_ITEMS_SUBMODULO.ordinal()) {
            selectSubModulosItems();
        }
        if (ID_LST == SelectC.LST_SUBMODULO.ordinal()) {
            selectSubModulos();
        }
        if (ID_LST == SelectC.LST_RELACION_VEHICULO_CONDUCTOR.ordinal()) {
            selectRelacionVehiculoConductor();
        }
        if (ID_LST == SelectC.LST_RELACION_VEHICULO_EMPRESA.ordinal()) {
            selectRelacionVehiculoEmpresa();
        }
        if (ID_LST == SelectC.LST_GRUPO_MOVILES.ordinal()) {
            selectGrupoMoviles();
        }
        if (ID_LST == SelectC.LST_CATEGORIAS.ordinal()) {
            selectCategorias();
        }
        if (ID_LST == SelectC.LST_PUNTORUTA_DESPACHO.ordinal()) {
            selectPuntoRuta_despacho();
        }
        if (ID_LST == SelectC.LST_GRUPOMOVILES_DESPACHO.ordinal()) {
            selectGrupoMoviles_despacho();
        }
        if (ID_LST == SelectC.LST_DESPACHO.ordinal()) {
            selectDespacho();
        }
        if (ID_LST == SelectC.LST_INTERVALO_DESPACHO.ordinal()) {
            selectIntervaloDespacho();
        }
        if (ID_LST == SelectC.LST_INTERVALO_DESPACHO_MANUAL.ordinal()) {
            selectIntervaloDespachoManual();
        }
        if (ID_LST == SelectC.LST_RUTA_DESPACHO.ordinal()) {
            selectRutaDespacho();
        }
        if (ID_LST == SelectC.LST_DIA_FESTIVO.ordinal()) {
            selectDiasFestivo();
        }
        if (ID_LST == SelectC.LST_PGRUTA.ordinal()) {
            selectProgramacionRuta();
        }
        if (ID_LST == SelectC.LST_PGRUTA_COMPLETA.ordinal()) {
            selectProgramacionRuta_completa();
        }
        if (ID_LST == SelectC.LST_PGRUTA_NOMBRE.ordinal()) {
            selectProgramacionRuta_nombre();
        }
        if (ID_LST == SelectC.PROGRAMACION_RUTA_ACTIVA.ordinal()) {
            selectProgramacionRuta_activa();
        }
        if (ID_LST == SelectC.LST_MONEDA.ordinal()) {
            selectMoneda();
        }
        if (ID_LST == SelectC.LST_ZONAHORARIA.ordinal()) {
            selectZonaHoraria();
        }
        if (ID_LST == SelectC.LST_TIPODOCUMENTO.ordinal()) {
            selectTipoDocumento();
        }
        if (ID_LST == SelectC.LST_VUELTA_CERRADA.ordinal()) {
            selectVueltaCerrada();
        }
        if (ID_LST == SelectC.LST_SERVIDOR_IMPRESION.ordinal()) {
            selectservidoresDeImpresion();
        }
    }

    public void selectPais() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_pais";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            lstpais = new ArrayList<Pais>();

            while (rs.next()) {
                Pais p = new Pais();
                p.setId(rs.getInt("PK_PAIS"));
                p.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                p.setCodArea(rs.getInt("CODIGO_AREA"));

                lstpais.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectDpto() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_departamento";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstdpto = new ArrayList<Departamento>();
            while (rs.next()) {
                Departamento d = new Departamento();
                d.setId(rs.getInt("PK_DEPARTAMENTO"));
                d.setIdpais(rs.getInt("FK_PAIS"));
                d.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));

                lstdpto.add(d);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectCiudad() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_ciudad";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstciudad = new ArrayList<Ciudad>();
            while (rs.next()) {
                Ciudad c = new Ciudad();
                c.setId(rs.getInt("PK_CIUDAD"));
                c.setIddpto(rs.getInt("FK_DEPARTAMENTO"));
                c.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));

                lstciudad.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPerfil() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_perfil WHERE (NOMBRE_PERFIL NOT IN('SuperUsuario')) "
                + " ORDER BY ESTADO DESC, NOMBRE_PERFIL ASC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstperfil = new ArrayList<Perfil>();
            while (rs.next()) {
                Perfil p = new Perfil();
                p.setId(rs.getInt("PK_PERFIL"));
                p.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE_PERFIL")));
                p.setDescripcion(StringUtils.upperFirstLetter(rs.getString("DESCRIPCION")));

                p.setEstado(rs.getInt("ESTADO"));
                lstperfil.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectConductor() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT * FROM tbl_conductor ORDER BY ESTADO DESC, APELLIDO ASC";
        String sql = "SELECT"
                + "  e.NOMBRE as EMPRESA,"
                + "  c.PK_CONDUCTOR,"
                + "  c.NOMBRE,"
                + "  c.APELLIDO,"
                + "  c.CEDULA,"
                + "  c.ESTADO,"
                + "  c.MODIFICACION_LOCAL"
                + " FROM tbl_conductor as c"
                + " INNER JOIN tbl_empresa as e ON"
                + "  e.PK_EMPRESA = c.FK_EMPRESA"
                + " ORDER BY ESTADO DESC, APELLIDO ASC ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstconductor = new ArrayList<Conductor>();
            while (rs.next()) {
                Conductor c = new Conductor();
                c.setNombreEmpresa(rs.getString("EMPRESA"));
                c.setId(rs.getInt("PK_CONDUCTOR"));
                c.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                c.setApellido(StringUtils.upperFirstLetter(rs.getString("APELLIDO")));
                c.setCedula(rs.getString("CEDULA"));
                c.setEstado(rs.getInt("ESTADO"));
                lstconductor.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectConductorActivo() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT * FROM tbl_conductor ORDER BY ESTADO DESC, APELLIDO ASC";
        String sql = "SELECT"
                + "  e.NOMBRE as EMPRESA,"
                + "  c.PK_CONDUCTOR,"
                + "  c.NOMBRE,"
                + "  c.APELLIDO,"
                + "  c.CEDULA,"
                + "  c.ESTADO,"
                + "  c.MODIFICACION_LOCAL"
                + " FROM tbl_conductor as c"
                + " INNER JOIN tbl_empresa as e ON"
                + "  e.PK_EMPRESA = c.FK_EMPRESA WHERE c.ESTADO = 1"
                + " ORDER BY APELLIDO ASC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstconductoractivo = new ArrayList<Conductor>();
            while (rs.next()) {
                Conductor c = new Conductor();
                c.setNombreEmpresa(rs.getString("EMPRESA"));
                c.setId(rs.getInt("PK_CONDUCTOR"));
                c.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                c.setApellido(StringUtils.upperFirstLetter(rs.getString("APELLIDO")));
                c.setCedula(rs.getString("CEDULA"));
                c.setEstado(rs.getInt("ESTADO"));
                lstconductoractivo.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectAlarma() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "  a.PK_ALARMA,"
                + "  a.CODIGO_ALARMA,"
                + "  a.NOMBRE,"
                + "  a.TIPO,"
                + "  a.UNIDAD_MEDICION,"
                + "  a.PRIORIDAD,"
                + "  a.ESTADO"
                + " FROM tbl_alarma as a"
                + " ORDER BY a.ESTADO DESC, a.NOMBRE ASC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstalarma = new ArrayList<>();
            while (rs.next()) {
                Alarma a = new Alarma();
                a.setId(rs.getInt("PK_ALARMA"));
                a.setCodigoAlarma(rs.getInt("CODIGO_ALARMA"));
                a.setNombreAlarma(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                a.setTipoAlarma(rs.getString("TIPO"));
                a.setUnidadDeMedicion(rs.getString("UNIDAD_MEDICION"));
                a.setPrioridad(rs.getInt("PRIORIDAD"));
                a.setEstado(rs.getInt("ESTADO"));
                lstalarma.add(a);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectEvento() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "  c.PK_EVENTO,"
                + "  c.CODIGO,"
                + "  c.NOMBRE_GENERICO,"
                + "  c.DESCRIPCION,"
                + "  c.PRIORIDAD,"
                + "  c.CANTIDAD,"
                + "  c.TIPO_EVENTO,"
                + "  c.ESTADO"
                + " FROM tbl_evento as c"
                + " ORDER BY c.ESTADO DESC, c.NOMBRE_GENERICO ASC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstevento = new ArrayList<>();
            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("PK_EVENTO"));
                e.setCodigoEvento(rs.getString("CODIGO"));
                e.setNombreGenerico(StringUtils.upperFirstLetter(rs.getString("NOMBRE_GENERICO")));
                e.setDescripcion(StringUtils.upperFirstLetter(rs.getString("DESCRIPCION")));
                e.setPrioridad(rs.getInt("PRIORIDAD"));
                e.setCantidad(rs.getInt("CANTIDAD"));
                e.setTipoEvento(rs.getString("TIPO_EVENTO"));
                e.setEstado(rs.getInt("ESTADO"));
                lstevento.add(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public String sql_selectMovil() {

        String sFkEmpresa;
        if (id_empresa > 0) {
            sFkEmpresa = "" + id_empresa;
        } else {
            sFkEmpresa = "v.FK_EMPRESA";
        }

        String sFkUsuario;
        String sJoinPropietario;

        if (usr_propietario) {
            sFkUsuario = "" + id_usr;
            sJoinPropietario = "INNER JOIN";
        } else {
            sFkUsuario = "fk_propietario";
            sJoinPropietario = "LEFT JOIN";
        }

        String sql = "SELECT veg.* , p.fk_propietario as FK_PROPIETARIO "
                + "		    FROM  "
                + "			   ( "
                + "				SELECT ve.* , gh.fk_gps as FK_GPS, gh.fk_hardware as FK_HARDWARE , gh.fk_marca as FK_MARCA, gh.numero_celular as NUMERO_CELULAR "
                + "					FROM	"
                + "					( "
                + "					 SELECT "
                + "		                e.NOMBRE, v.PK_VEHICULO, v.FK_EMPRESA, "
                + "		                v.PLACA, v.NUM_INTERNO, v.CAPACIDAD, v.ESTADO, "
                + "		                v.MODIFICACION_LOCAL "
                + "		          FROM tbl_vehiculo as v "
                + "		          INNER JOIN tbl_empresa as e ON "
                + "		          v.FK_EMPRESA = e.PK_EMPRESA "
                + "		          WHERE (v.ESTADO = 1) AND (v.FK_EMPRESA=" + sFkEmpresa + ") AND e.ESTADO = 1  "
                + "		          ORDER BY v.PLACA ASC "
                + "		         ) as ve "
                + "		         LEFT JOIN "
                + "		         ( "
                + "                         SELECT a.fk_vehiculo , a.fk_hardware, a.fk_gps , b.fk_marca, numero_celular FROM "
                + "			 	(			"
                + "                                     SELECT fk_vehiculo , fk_hardware, fk_gps, numero_celular "
                + "					FROM tbl_gps_hardware "
                + "					WHERE estado = 1 "
                + "					) a "
                + "					INNER JOIN "
                + "					( "
                + "						 		SELECT c.id , c.fk_marca "
                + "									FROM 		 	"
                + "										(		"
                + "										SELECT id, fk_marca "
                + "										FROM tbl_gps_inventario "
                + "										WHERE estado = 1 "
                + "										) c"                
                + "					) b "
                + "					ON a.fk_gps = b.id "
                + "			) as gh "
                + "				ON ve.PK_VEHICULO = gh.fk_vehiculo "
                + "				) as veg "
                + "				" + sJoinPropietario + " "
                + "				( "
                + "				SELECT fk_propietario , fk_vehiculo "
                + "					FROM tbl_propietario_vehiculo WHERE activa = 1 AND estado = 1 AND fk_propietario = " + sFkUsuario + " 		"
                + "				) as p"
                + "				ON veg.PK_VEHICULO = p.fk_vehiculo ORDER BY veg.PLACA ASC";

        return sql;
    }

    public void selectMovil() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = sql_selectMovil();
        //System.out.println(sql.toString());
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstmovil = new ArrayList<>();
            while (rs.next()) {
                Movil m = new Movil();

                m.setNombreEmpresa(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                m.setId(rs.getInt("PK_VEHICULO"));
                m.setFkEmpresa(rs.getInt("FK_EMPRESA"));
                m.setPlaca(rs.getString("PLACA"));
                m.setNumeroInterno(rs.getString("NUM_INTERNO"));
                m.setCapacidad(rs.getInt("CAPACIDAD"));
                m.setEstado(rs.getInt("ESTADO"));
                m.setFkHardware(rs.getInt("FK_HARDWARE"));
                m.setFkGps(rs.getString("FK_GPS"));
                m.setFkMarcaGps(rs.getInt("FK_MARCA"));
                m.setNumero_celular(rs.getString("NUMERO_CELULAR"));
                lstmovil.add(m);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectEmpresa() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT * FROM tbl_empresa";        
        String sql = "SELECT"
                + "  e.PK_EMPRESA,"
                + "  e.NOMBRE,"
                + "  e.NIT,"
                + "  e.ESTADO,"
                + "  e.LONGITUD_WEB,"
                + "  e.LATITUD_WEB,"
                + "  p.NOMBRE AS PAIS,"
                + "  d.NOMBRE AS DPTO,"
                + "  c.NOMBRE AS CIUDAD,"
                + "  p.PK_PAIS AS IDPAIS,"
                + "  d.PK_DEPARTAMENTO AS IDDPTO,"
                + "  c.PK_CIUDAD AS IDCIUDAD"
                + " FROM tbl_empresa AS e"
                + " INNER JOIN tbl_ciudad AS c ON"
                + "  e.FK_CIUDAD = c.PK_CIUDAD"
                + " INNER JOIN tbl_departamento AS d ON"
                + "  c.FK_DEPARTAMENTO = d.PK_DEPARTAMENTO"
                + " INNER JOIN tbl_pais AS p ON"
                + "  d.FK_PAIS = p.PK_PAIS WHERE e.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstempresa = new ArrayList<Empresa>();
            while (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getInt("PK_EMPRESA"));
                e.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                e.setNit(rs.getString("NIT"));
                e.setPais(StringUtils.upperFirstLetter(rs.getString("PAIS")));
                e.setDpto(StringUtils.upperFirstLetter(rs.getString("DPTO")));
                e.setCiudad(StringUtils.upperFirstLetter(rs.getString("CIUDAD")));
                e.setIdpais(rs.getInt("IDPAIS"));
                e.setIddpto(rs.getInt("IDDPTO"));
                e.setIdciudad(rs.getInt("IDCIUDAD"));
                e.setEstado(rs.getInt("ESTADO"));
                e.setLongitudWeb(rs.getString("LONGITUD_WEB"));
                e.setLatitudWeb(rs.getString("LATITUD_WEB"));

                lstempresa.add(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectGrupoEmpresa() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql
                = "SELECT"
                + "  a.PK_AGRUPACION as PK_AGRUPACION,"
                + "  a.NOMBRE as NOMBRE_AGRUPACION,"
                + "  a.FK_EMPRESA as FK_EMPRESA,"
                + "  a.ESTADO as ESTADO,"
                + "  a.APLICARTIEMPOS as APLICARTIEMPOS,"
                + "  e.NOMBRE as NOMBRE_EMPRESA"
                + " FROM tbl_agrupacion AS a INNER JOIN tbl_empresa AS e ON"
                + "  a.FK_EMPRESA = e.PK_EMPRESA AND a.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstgempresa = new ArrayList<GrupoEmpresa>();
            while (rs.next()) {
                GrupoEmpresa gemp = new GrupoEmpresa();
                gemp.setId(rs.getInt("PK_AGRUPACION"));
                gemp.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE_AGRUPACION")));
                gemp.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                gemp.setEstado(rs.getInt("ESTADO"));
                gemp.setAplicarTiempos(rs.getInt("APLICARTIEMPOS"));
                gemp.setNombreEmpresa(StringUtils.upperFirstLetter(rs.getString("NOMBRE_EMPRESA")));

                lstgempresa.add(gemp);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectUsuario() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT u.*, p.NOMBRE_PERFIL AS PERFIL "
                + " FROM tbl_usuario as u INNER JOIN tbl_perfil AS p ON p.PK_PERFIL = u.FK_PERFIL ORDER BY ESTADO DESC, NOMBRE ASC, APELLIDO ASC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstusuario = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                u.setApellido(StringUtils.upperFirstLetter(rs.getString("APELLIDO")));
                u.setPerfilusuario(rs.getString("PERFIL"));
                u.setNumdoc("" + rs.getInt("CEDULA"));
                u.setEmail(rs.getString("EMAIL"));
                u.setLogin(rs.getString("LOGIN"));
                u.setToken(rs.getString("TOKEN"));
                u.setEstado(rs.getInt("ESTADO"));

                lstusuario.add(u);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectBase() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT * FROM tbl_base WHERE ESTADO = 1";
        String sql = "SELECT *, "
                + "(SELECT MAX(IDENTIFICADOR_BASE) FROM tbl_base AS bi WHERE bi.ESTADO = 1) as MAXCOD "
                + "FROM tbl_base b WHERE b.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstbase = new ArrayList<Base>();
            while (rs.next()) {
                Base b = new Base();
                b.setIdbase(rs.getInt("PK_BASE"));
                b.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                b.setIdentificador(rs.getInt("IDENTIFICADOR_BASE"));
                b.setCodigoBase(rs.getInt("CODIGO_BASE"));
                b.setLatitud(rs.getString("LATITUD"));
                b.setLongitud(rs.getString("LONGITUD"));
                b.setDireccionLatitud(rs.getString("DIRECCION_LATITUD"));
                b.setDireccionLongitud(rs.getString("DIRECCION_LONGITUD"));
                b.setRadio(rs.getInt("RADIO"));
                b.setDireccion(rs.getInt("DIRECCION"));
                b.setEstado(rs.getInt("ESTADO"));
                b.setMaxcod(rs.getInt("MAXCOD"));

                // Conversion a formato web GD
                //b.setLatitud(Coordenadas.toGD(b.getLatitud(), b.getDireccionLatitud()));
                //b.setLongitud(Coordenadas.toGD(b.getLongitud(), b.getDireccionLongitud()));
                String lat_web = rs.getString("LATITUD_WEB");
                String lon_web = rs.getString("LONGITUD_WEB");
                lat_web = (lat_web == null || lat_web == "") ? "'NA'" : lat_web;
                lon_web = (lon_web == null || lon_web == "") ? "'NA'" : lon_web;

                b.setLatitudWeb(lat_web);
                b.setLongitudWeb(lon_web);

                lstbase.add(b);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectBase_PuntoRuta() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " b.PK_BASE,"
                + " b.NOMBRE,"
                + " b.IDENTIFICADOR_BASE,"
                + " b.CODIGO_BASE,"
                + " b.LATITUD,"
                + " b.LONGITUD,"
                + " b.DIRECCION_LATITUD,"
                + " b.DIRECCION_LONGITUD,"
                + " b.LATITUD_WEB,"
                + " b.LONGITUD_WEB,"
                + " b.RADIO,"
                + " b.DIRECCION,"
                + " b.ESTADO"
                + " FROM tbl_base AS b"
                + " INNER JOIN tbl_ruta_punto AS rp ON"
                + "   rp.FK_BASE = b.PK_BASE AND rp.ESTADO = 1"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   rp.FK_RUTA = r.PK_RUTA AND r.ESTADO = 1"
                + " GROUP BY b.PK_BASE";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lstbase_pr = new ArrayList<Base>();
            while (rs.next()) {
                Base b = new Base();
                b.setIdbase(rs.getInt("PK_BASE"));
                b.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                b.setIdentificador(rs.getInt("IDENTIFICADOR_BASE"));
                b.setCodigoBase(rs.getInt("CODIGO_BASE"));
                b.setLatitud(rs.getString("LATITUD"));
                b.setLongitud(rs.getString("LONGITUD"));
                b.setDireccionLatitud(rs.getString("DIRECCION_LATITUD"));
                b.setDireccionLongitud(rs.getString("DIRECCION_LONGITUD"));
                b.setLatitudWeb(rs.getString("LATITUD_WEB"));
                b.setLongitudWeb(rs.getString("LONGITUD_WEB"));
                b.setRadio(rs.getInt("RADIO"));
                b.setDireccion(rs.getInt("DIRECCION"));
                b.setEstado(rs.getInt("ESTADO"));

                lstbase_pr.add(b);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeStatement(st);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPunto() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT * FROM tbl_punto ORDER BY ESTADO DESC, NOMBRE ASC";
        String sql = "SELECT *, "
                + "(SELECT MAX(CODIGO_PUNTO) FROM tbl_punto AS pi WHERE pi.ESTADO = 1) as MAXCOD "
                + "FROM tbl_punto p WHERE p.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstpunto = new ArrayList<Punto>();
            while (rs.next()) {
                Punto p = new Punto();
                p.setIdpunto(rs.getInt("PK_PUNTO"));
                p.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                p.setDescripcion(StringUtils.upperFirstLetter(rs.getString("DESCRIPCION")));
                p.setCodigoPunto(rs.getInt("CODIGO_PUNTO"));
                p.setLatitud(rs.getString("LATITUD"));
                p.setLongitud(rs.getString("LONGITUD"));
                p.setDireccionLatitud(StringUtils.upperFirstLetter(rs.getString("DIRECCION_LATITUD")));
                p.setDireccionLongitud(StringUtils.upperFirstLetter(rs.getString("DIRECCION_LONGITUD")));
                p.setDireccion(rs.getInt("DIRECCION"));
                p.setRadio(rs.getInt("RADIO"));
                p.setEstado(rs.getInt("ESTADO"));
                p.setMaxcod(rs.getInt("MAXCOD"));

                // Conversion a formato web GD                
                //p.setLatitud(Coordenadas.toGD(p.getLatitud(), p.getDireccionLatitud()));
                //p.setLongitud(Coordenadas.toGD(p.getLongitud(), p.getDireccionLongitud()));
                String lat_web = rs.getString("LATITUD_WEB");
                String lon_web = rs.getString("LONGITUD_WEB");
                lat_web = (lat_web == null || lat_web == "") ? "'NA'" : lat_web;
                lon_web = (lon_web == null || lon_web == "") ? "'NA'" : lon_web;

                p.setLatitudWeb(lat_web);
                p.setLongitudWeb(lon_web);

                lstpunto.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPuntoBase() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM ("
                + "SELECT"
                + "   PK_PUNTO AS ID,"
                + "   CODIGO_PUNTO AS CODIGO,"
                + "   NOMBRE"
                + " FROM tbl_punto WHERE ESTADO = 1"
                + " UNION"
                + " SELECT"
                + "   PK_BASE AS ID,"
                + "   IDENTIFICADOR_BASE AS CODIGO,"
                + "   NOMBRE"
                + " FROM tbl_base WHERE ESTADO = 1"
                + ") AS s ORDER BY s.CODIGO ASC";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lstpuntobase = new ArrayList<Punto>();
            while (rs.next()) {
                Punto p = new Punto();
                p.setIdpunto(rs.getInt("ID"));
                p.setCodigoPunto(rs.getInt("CODIGO"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setEtiquetaPunto("p" + rs.getInt("CODIGO"));
                lstpuntobase.add(p);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public void selectRuta() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "  r.PK_RUTA as PK_RUTA,"
                + "  r.FK_EMPRESA as FK_EMPRESA,"
                + "  r.NOMBRE as NOMBRE_RUTA,"
                + "  r.ESTADO as ESTADO,"
                + "  r.DISTANCIA_METROS,"
                + "  e.NOMBRE as NOMBRE_EMPRESA"
                + " FROM tbl_ruta as r"
                + " INNER JOIN tbl_empresa as e ON"
                + "  r.FK_EMPRESA = e.PK_EMPRESA WHERE r.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstruta = new ArrayList<Ruta>();
            while (rs.next()) {
                Ruta r = new Ruta();
                r.setId(rs.getInt("PK_RUTA"));
                r.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE_RUTA")));
                r.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                r.setEmpresa(StringUtils.upperFirstLetter(rs.getString("NOMBRE_EMPRESA")));
                r.setEstado(rs.getInt("ESTADO"));
                r.setDistanciaMetros(rs.getInt("DISTANCIA_METROS"));

                lstruta.add(r);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPuntoRuta_completa() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " rp.PK_RUTA_PUNTO,"
                + " rp.FK_RUTA,"
                + " rp.FK_PUNTO,"
                + " IF(rp.TIPO = 2,"
                + "     p.CODIGO_PUNTO,"
                + "     b.IDENTIFICADOR_BASE) AS CODIGO_PUNTO,"
                + " IF(rp.TIPO = 2,"
                + "     lcase(p.NOMBRE),"
                + "     lcase(b.NOMBRE)) AS NOMBRE_PUNTO,"
                + " IF(rp.TIPO = 2,"
                + "     p.LATITUD_WEB,"
                + "     b.LATITUD_WEB) AS LATITUD_WEB,"
                + " IF(rp.TIPO = 2,"
                + "     p.LONGITUD_WEB,"
                + "     b.LONGITUD_WEB) AS LONGITUD_WEB,"
                + " rp.PROMEDIO_MINUTOS,"
                + " rp.HOLGURA_MINUTOS,"
                + " rp.TIEMPO_VALLE,"
                + " rp.TIEMPO_PICO,"
                + " rp.VALOR_PUNTO,"
                + " rp.TIPO,"
                + " rp.FK_BASE"
                + " FROM tbl_ruta_punto AS rp"
                + " LEFT JOIN tbl_punto AS p ON"
                + "     rp.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " LEFT JOIN tbl_base AS b ON"
                + "     rp.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " WHERE"
                + " rp.ESTADO = 1 AND"
                + " rp.FK_RUTA IN ("
                + "     SELECT r.PK_RUTA FROM tbl_ruta AS r WHERE r.ESTADO = 1"
                + ") ORDER BY rp.PK_RUTA_PUNTO asc";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lstpuntoruta_completa = new ArrayList<PuntoRuta>();
            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();

                pr.setId(rs.getInt("PK_RUTA_PUNTO"));
                pr.setIdRuta(rs.getInt("FK_RUTA"));
                pr.setIdPunto(rs.getInt("FK_PUNTO"));
                pr.setCodigoPunto(rs.getInt("CODIGO_PUNTO"));
                pr.setEtiquetaPunto("p" + pr.getCodigoPunto());
                pr.setNombrePunto(rs.getString("NOMBRE_PUNTO"));
                pr.setLatitudWeb(rs.getString("LATITUD_WEB"));
                pr.setLongitudWeb(rs.getString("LONGITUD_WEB"));
                pr.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                pr.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));
                pr.setTiempoValle(rs.getInt("TIEMPO_VALLE"));
                pr.setTiempoPico(rs.getInt("TIEMPO_PICO"));
                pr.setValorPunto(rs.getInt("VALOR_PUNTO"));
                pr.setTipo(rs.getInt("TIPO"));
                pr.setIdBase(rs.getInt("FK_BASE"));

                lstpuntoruta_completa.add(pr);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPuntoRuta_cruzada() {
        ArrayList<Integer> lst_dph = DespachoBD.select_id();

        lstpuntoruta_cruzada = new ArrayList<Despacho>();

        for (int id_dph : lst_dph) {
            Despacho dph = DespachoBD.select(id_dph);
            lstpuntoruta_cruzada.add(dph);
        }
    }

    public void selectPuntoRuta() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql
                = "SELECT "
                + "rp.PK_RUTA_PUNTO as PK_RUTA_PUNTO, "
                + "rp.FK_PUNTO as FK_PUNTO, "
                + "rp.FK_RUTA as FK_RUTA, "
                + "p.NOMBRE as NOMBRE_PUNTO, "
                + "r.NOMBRE as NOMBRE_RUTA, "
                + "p.LATITUD as LATITUD, "
                + "p.LONGITUD as LONGITUD, "
                + "p.LATITUD_WEB as LATITUD_WEB, "
                + "p.LONGITUD_WEB as LONGITUD_WEB, "
                + "p.DIRECCION_LATITUD as DIRECCION_LATITUD, "
                + "p.DIRECCION_LONGITUD as DIRECCION_LONGITUD, "
                + "rp.PROMEDIO_MINUTOS as PROMEDIO_MINUTOS, "
                + "rp.HOLGURA_MINUTOS as HOLGURA_MINUTOS, "
                + "rp.VALOR_PUNTO as VALOR_PUNTO, "
                + "rp.TIPO as TIPO, "
                + "rp.ESTADO as ESTADO "
                + "FROM tbl_ruta_punto as rp "
                + "INNER JOIN tbl_punto as p ON "
                + "  p.PK_PUNTO = rp.FK_PUNTO "
                + "INNER JOIN tbl_ruta as r ON "
                + "  r.PK_RUTA = rp.FK_RUTA " // 1ra version hasta aqui
                + "WHERE p.ESTADO = 1 AND r.ESTADO = 1 AND rp.ESTADO = 1 ORDER BY rp.PK_RUTA_PUNTO ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstpuntoruta = new ArrayList<PuntoRuta>();
            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();
                pr.setId(rs.getInt("PK_RUTA_PUNTO"));
                pr.setIdPunto(rs.getInt("FK_PUNTO"));
                pr.setIdRuta(rs.getInt("FK_RUTA"));
                pr.setNombrePunto(StringUtils.upperFirstLetter(rs.getString("NOMBRE_PUNTO")));
                pr.setNombreRuta(StringUtils.upperFirstLetter(rs.getString("NOMBRE_RUTA")));
                pr.setLatitud(rs.getString("LATITUD"));
                pr.setLongitud(rs.getString("LONGITUD"));
                pr.setDireccionLatitud(StringUtils.upperFirstLetter(rs.getString("DIRECCION_LATITUD")));
                pr.setDireccionLongitud(StringUtils.upperFirstLetter(rs.getString("DIRECCION_LONGITUD")));
                pr.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                pr.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));
                pr.setValorPunto(rs.getInt("VALOR_PUNTO"));
                pr.setTipo(rs.getInt("TIPO"));
                pr.setEstado(rs.getInt("ESTADO"));

                // Conversion a formato web GD                
                //pr.setLatitud(Coordenadas.toGD(pr.getLatitud(), pr.getDireccionLatitud()));
                //pr.setLongitud(Coordenadas.toGD(pr.getLongitud(), pr.getDireccionLongitud()));
                pr.setLatitudWeb(rs.getString("LATITUD_WEB"));
                pr.setLongitudWeb(rs.getString("LONGITUD_WEB"));

                lstpuntoruta.add(pr);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectBaseRuta() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql
                = "SELECT "
                + "rp.PK_RUTA_PUNTO as PK_RUTA_PUNTO, "
                + "rp.FK_PUNTO as FK_PUNTO, "
                + "rp.FK_RUTA as FK_RUTA, "
                + "rp.FK_BASE as FK_BASE, "
                + "rp.TIPO as TIPO, "
                + "rp.PROMEDIO_MINUTOS as PROMEDIO_MINUTOS, "
                + "rp.HOLGURA_MINUTOS as HOLGURA_MINUTOS, "
                + "rp.VALOR_PUNTO as VALOR_PUNTO, "
                + "b.NOMBRE as NOMBRE_BASE, "
                + "b.LATITUD as LATITUD, "
                + "b.LONGITUD as LONGITUD, "
                + "b.LATITUD_WEB as LATITUD_WEB, "
                + "b.LONGITUD_WEB as LONGITUD_WEB, "
                + "b.DIRECCION_LATITUD as DIRECCION_LATITUD, "
                + "b.DIRECCION_LONGITUD as DIRECCION_LONGITUD, "
                + "r.NOMBRE as NOMBRE_RUTA, "
                + "rp.ESTADO as ESTADO "
                + "FROM tbl_ruta_punto as rp "
                + "INNER JOIN tbl_base as b ON "
                + "  b.PK_BASE = rp.FK_BASE "
                + "INNER JOIN tbl_ruta as r ON "
                + "  r.PK_RUTA = rp.FK_RUTA "
                + "WHERE rp.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstbaseruta = new ArrayList<BaseRuta>();
            while (rs.next()) {
                BaseRuta br = new BaseRuta();
                br.setId(rs.getInt("PK_RUTA_PUNTO"));
                br.setIdPunto(rs.getInt("FK_PUNTO"));
                br.setIdRuta(rs.getInt("FK_RUTA"));
                br.setIdBase(rs.getInt("FK_BASE"));
                br.setNombreBase(StringUtils.upperFirstLetter(rs.getString("NOMBRE_BASE")));
                br.setNombreRuta(StringUtils.upperFirstLetter(rs.getString("NOMBRE_RUTA")));
                br.setLatitud(rs.getString("LATITUD"));
                br.setLongitud(rs.getString("LONGITUD"));
                br.setDireccionLatitud(StringUtils.upperFirstLetter(rs.getString("DIRECCION_LATITUD")));
                br.setDireccionLongitud(StringUtils.upperFirstLetter(rs.getString("DIRECCION_LONGITUD")));
                br.setTipo(rs.getInt("TIPO"));
                br.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                br.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));
                br.setValorPunto(rs.getInt("VALOR_PUNTO"));
                br.setEstado(rs.getInt("ESTADO"));

                // Conversion a formato web GD                
                //br.setLatitud(Coordenadas.toGD(br.getLatitud(), br.getDireccionLatitud()));
                //br.setLongitud(Coordenadas.toGD(br.getLongitud(), br.getDireccionLongitud()));
                br.setLatitudWeb(rs.getString("LATITUD_WEB"));
                br.setLongitudWeb(rs.getString("LONGITUD_WEB"));

                lstbaseruta.add(br);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectTarifa() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //String sql = "SELECT * FROM tbl_tarifa_fija WHERE TARIFA_ACTIVA = 1";
        String sql = "SELECT * FROM tbl_tarifa_fija WHERE 1 = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lsttarifa = new ArrayList<Tarifa>();
            while (rs.next()) {
                Tarifa t = new Tarifa();
                t.setId(rs.getInt("PK_TARIFA_FIJA"));
                t.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE_TARIFA")));
                t.setValor(rs.getDouble("VALOR_TARIFA"));
                t.setActiva(rs.getInt("TARIFA_ACTIVA"));
                t.setEstado(rs.getInt("ESTADO"));

                lsttarifa.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPuntoRuta_despacho() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql
                = "SELECT"
                + " rp.FK_RUTA AS PK_RUTA,"
                + " rp.FK_PUNTO AS FK_PUNTO,"
                + " IF(rp.TIPO = 2,"
                + "     p.CODIGO_PUNTO,"
                + "     b.IDENTIFICADOR_BASE) AS CODIGO_PUNTO,"
                + " IF(rp.TIPO = 2,"
                + "     lcase(p.NOMBRE),"
                + "     lcase(b.NOMBRE)) AS NOMBRE_PUNTO,"
                + " rp.PROMEDIO_MINUTOS,"
                + " rp.HOLGURA_MINUTOS"
                + " FROM tbl_ruta_punto AS rp"
                + " LEFT JOIN tbl_punto AS p ON"
                + "     rp.FK_PUNTO = p.PK_PUNTO AND p.ESTADO = 1"
                + " LEFT JOIN tbl_base AS b ON"
                + "     rp.FK_BASE = b.PK_BASE AND b.ESTADO = 1"
                + " WHERE rp.ESTADO = 1 AND rp.FK_RUTA IN ("
                + "     SELECT r.PK_RUTA FROM tbl_ruta AS r WHERE r.ESTADO = 1"
                + ") ORDER BY rp.PK_RUTA_PUNTO ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstpuntoruta_despacho = new ArrayList<PuntoRuta>();

            while (rs.next()) {
                PuntoRuta pr = new PuntoRuta();

                pr.setIdRuta(rs.getInt("PK_RUTA"));
                pr.setIdPunto(rs.getInt("FK_PUNTO"));
                pr.setCodigoPunto(rs.getInt("CODIGO_PUNTO"));
                pr.setNombrePunto(StringUtils.upperFirstLetter(rs.getString("NOMBRE_PUNTO")));
                pr.setEtiquetaPunto("p" + rs.getInt("CODIGO_PUNTO"));
                pr.setPromedioMinutos(rs.getInt("PROMEDIO_MINUTOS"));
                pr.setHolguraMinutos(rs.getInt("HOLGURA_MINUTOS"));

                lstpuntoruta_despacho.add(pr);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectMotivo() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_motivo LIMIT 0, 1000;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstmotivo = new ArrayList<Motivo>();
            while (rs.next()) {
                Motivo t = new Motivo();
                t.setId(rs.getInt("PK_MOTIVO"));
                t.setFkAuditoria(rs.getInt("FK_AUDITORIA"));
                t.setTablaAuditoria(StringUtils.upperFirstLetter(rs.getString("TABLA_AUDITORIA")));
                t.setInformacionAdicional(StringUtils.upperFirstLetter(rs.getString("DESCRIPCION_MOTIVO")));
                t.setUsuario(rs.getInt("USUARIO"));
                t.setUsuarioBD(rs.getString("USUARIO_BD"));

                lstmotivo.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectRelacionVehiculoConductor() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "  cv.PK_CONDUCTOR_VECHICULO,"
                + "  cv.FK_CONDUCTOR,"
                + "  cv.FK_VEHICULO,"
                + "  v.PLACA,"
                + "  v.NUM_INTERNO,"
                + "  CONCAT(c.APELLIDO, ' ', c.NOMBRE) AS NOMBRE,"
                + "  cv.ESTADO,"
                + "  cv.ACTIVO"
                + " FROM tbl_vehiculo as v"
                + " INNER JOIN tbl_conductor_vehiculo as cv ON"
                + "  v.PK_VEHICULO = cv.FK_VEHICULO"
                + " INNER JOIN tbl_conductor as c ON"
                + "  c.PK_CONDUCTOR = cv.FK_CONDUCTOR WHERE cv.ESTADO = 1 AND c.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstrelacionvehiculoconductor = new ArrayList<RelacionVehiculoConductor>();
            while (rs.next()) {
                RelacionVehiculoConductor t = new RelacionVehiculoConductor();
                t.setIdRelacionVehiculoConductor(rs.getInt("PK_CONDUCTOR_VECHICULO"));
                t.setIdConductor(rs.getInt("FK_CONDUCTOR"));
                t.setIdVehiculo(rs.getInt("FK_VEHICULO"));
                t.setMatriculaVehiculo(rs.getString("PLACA"));
                t.setNumeroInterno(rs.getString("NUM_INTERNO"));
                t.setNombreConductor(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                t.setEstado(rs.getInt("ESTADO"));
                t.setActivo(rs.getInt("ACTIVO"));

                lstrelacionvehiculoconductor.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectRelacionVehiculoEmpresa() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "CALL proc_tbl_vehiculo_empresa (NULL, NULL, NULL, NULL, 4);";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstrelacionvehiculoempresa = new ArrayList<RelacionVehiculoEmpresa>();
            while (rs.next()) {
                RelacionVehiculoEmpresa t = new RelacionVehiculoEmpresa();
                t.setIdRelacionVehiculoEmpresa(rs.getInt("PK_VEHICULO_EMPRESA"));
                t.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                t.setIdVehiculo(rs.getInt("FK_VEHICULO"));
                t.setMatriculaVehiculo(rs.getString("PLACA"));
                t.setRazonSocial(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                t.setNit(rs.getString("NIT"));
                t.setEstado(rs.getInt("ESTADO"));

                lstrelacionvehiculoempresa.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectGrupoVehiculos() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT"
                    + "  a.PK_AGRUPACION,"
                    + "  a.FK_EMPRESA,"
                    + "  a.NOMBRE as NOMBRE_GRUPO,"
                    + "  e.NOMBRE as NOMBRE_EMPRESA,"
                    + "  a.APLICARTIEMPOS,"
                    + "  a.ESTADO"
                    + " FROM tbl_agrupacion as a"
                    + " INNER JOIN tbl_empresa as e ON"
                    + "  a.FK_EMPRESA = e.PK_EMPRESA "
                    + "  WHERE a.ESTADO = 1";

            if (usr_propietario) {
                sql = "	SELECT * FROM 	"
                        + "("
                        + "SELECT t3.NOMBRE as NOMBRE_GRUPO, "
                        + "t3.PK_AGRUPACION , "
                        + "t3.APLICARTIEMPOS , "
                        + "t3.FK_EMPRESA , "
                        + "t3.ESTADO FROM"
                        + "	             ("
                        + "	             SELECT DISTINCT t0.FK_AGRUPACION FROM"
                        + "	                    			("
                        + "	                    				SELECT FK_AGRUPACION , FK_VEHICULO FROM tbl_agrupacion_vehiculo WHERE ESTADO = 1 "
                        + "	                    			) t0"
                        + "	                    			INNER JOIN"
                        + "	                    			("
                        + "	                    				SELECT fk_vehiculo as fk_vehiculo_prop FROM tbl_propietario_vehiculo WHERE fk_propietario = " + id_usr + " AND activa = 1 AND estado =1 "
                        + "	                    			) t1"
                        + "	                    			ON t0.FK_VEHICULO = t1.fk_vehiculo_prop"
                        + "	                    	) t2"
                        + "	                    	INNER JOIN "
                        + "	                    	("
                        + "	                    	SELECT PK_AGRUPACION, NOMBRE , APLICARTIEMPOS, FK_EMPRESA , ESTADO FROM tbl_agrupacion WHERE ESTADO = 1"
                        + "	                    	) t3"
                        + "	                    	ON t2.FK_AGRUPACION = t3.PK_AGRUPACION"
                        + "                    	) t4"
                        + "                    	INNER JOIN"
                        + "                    	("
                        + "							  	SELECT PK_EMPRESA, NOMBRE as NOMBRE_EMPRESA FROM tbl_empresa WHERE ESTADO = 1"
                        + "							) t5"
                        + "							ON t4.FK_EMPRESA = t5.PK_EMPRESA";
            }

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstgrupovehiculos = new ArrayList<GrupoMovil>();
            while (rs.next()) {
                GrupoMovil t = new GrupoMovil();
                t.setId(rs.getInt("PK_AGRUPACION"));
                t.setCodEmpresa(rs.getInt("FK_EMPRESA"));
                t.setNombreEmpresa(StringUtils.upperFirstLetter(rs.getString("NOMBRE_EMPRESA")));
                t.setNombreGrupo(StringUtils.upperFirstLetter(rs.getString("NOMBRE_GRUPO")));
                t.setAplicaTiempos(rs.getInt("APLICARTIEMPOS"));
                t.setEstado(rs.getInt("ESTADO"));

                lstgrupovehiculos.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectGrupoMoviles() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT"
                    + "  a.PK_AGRUPACION,"
                    + "  a.FK_EMPRESA,"
                    + "  a.NOMBRE as NOMBRE_GRUPO,"
                    + "  e.NOMBRE as NOMBRE_EMPRESA,"
                    + "  a.APLICARTIEMPOS,"
                    + "  a.ESTADO"
                    + " FROM tbl_agrupacion as a"
                    + " INNER JOIN tbl_empresa as e ON"
                    + "  a.FK_EMPRESA = e.PK_EMPRESA ";

            if (usr_propietario) {
                sql = "	SELECT * FROM 	"
                        + "("
                        + "SELECT t3.NOMBRE as NOMBRE_GRUPO, "
                        + "t3.PK_AGRUPACION , "
                        + "t3.APLICARTIEMPOS , "
                        + "t3.FK_EMPRESA , "
                        + "t3.ESTADO FROM"
                        + "	             ("
                        + "	             SELECT DISTINCT t0.FK_AGRUPACION FROM"
                        + "	                    			("
                        + "	                    				SELECT FK_AGRUPACION , FK_VEHICULO FROM tbl_agrupacion_vehiculo WHERE ESTADO = 1 "
                        + "	                    			) t0"
                        + "	                    			INNER JOIN"
                        + "	                    			("
                        + "	                    				SELECT fk_vehiculo as fk_vehiculo_prop FROM tbl_propietario_vehiculo WHERE fk_propietario = " + id_usr + " AND activa = 1 AND estado =1 "
                        + "	                    			) t1"
                        + "	                    			ON t0.FK_VEHICULO = t1.fk_vehiculo_prop"
                        + "	                    	) t2"
                        + "	                    	INNER JOIN "
                        + "	                    	("
                        + "	                    	SELECT PK_AGRUPACION, NOMBRE , APLICARTIEMPOS, FK_EMPRESA , ESTADO FROM tbl_agrupacion WHERE ESTADO = 1"
                        + "	                    	) t3"
                        + "	                    	ON t2.FK_AGRUPACION = t3.PK_AGRUPACION"
                        + "                    	) t4"
                        + "                    	INNER JOIN"
                        + "                    	("
                        + "							  	SELECT PK_EMPRESA, NOMBRE as NOMBRE_EMPRESA FROM tbl_empresa WHERE ESTADO = 1"
                        + "							) t5"
                        + "							ON t4.FK_EMPRESA = t5.PK_EMPRESA";
            }

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstgrupomoviles = new ArrayList<GrupoMovil>();
            while (rs.next()) {
                GrupoMovil t = new GrupoMovil();
                t.setId(rs.getInt("PK_AGRUPACION"));
                t.setCodEmpresa(rs.getInt("FK_EMPRESA"));
                t.setNombreEmpresa(StringUtils.upperFirstLetter(rs.getString("NOMBRE_EMPRESA")));
                t.setNombreGrupo(StringUtils.upperFirstLetter(rs.getString("NOMBRE_GRUPO")));
                t.setAplicaTiempos(rs.getInt("APLICARTIEMPOS"));
                t.setEstado(rs.getInt("ESTADO"));

                lstgrupomoviles.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectGrupoMoviles_despacho() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + "   av.FK_AGRUPACION,"
                + "   v.PK_VEHICULO,"
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

            lstgrupomoviles_despacho = new ArrayList<GrupoMovilDespacho>();
            while (rs.next()) {
                GrupoMovilDespacho gmd = new GrupoMovilDespacho();
                gmd.setIdGrupoMovil(rs.getInt("FK_AGRUPACION"));
                gmd.setIdMovil(rs.getInt("PK_VEHICULO"));
                gmd.setNumeroInterno(rs.getString("NUM_INTERNO"));
                gmd.setPlaca(rs.getString("PLACA"));

                lstgrupomoviles_despacho.add(gmd);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectRutas1() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_ruta (NULL, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, 3);");
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstrutas = new ArrayList<Ruta>();
            while (rs.next()) {
                Ruta t = new Ruta();
                t.setId(rs.getInt("PK_RUTA"));
                t.setIdEmpresa(rs.getInt("FK_EMPRESA"));
                t.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                t.setHistorial(rs.getInt("HISTORIAL"));
                t.setEstadoCreacion(rs.getInt("ESTADO_CREACION"));
                t.setEstado(rs.getInt("ESTADO"));

                lstrutas.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectCategorias() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_categoria_descuento (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);");
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstcategorias = new ArrayList<CategoriasDeDescuento>();
            while (rs.next()) {
                CategoriasDeDescuento t = new CategoriasDeDescuento();
                t.setId(rs.getInt("PK_CATEGORIAS_DESCUENTOS"));
                t.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                t.setDescripcion(StringUtils.upperFirstLetter(rs.getString("DESCRIPCION")));
                t.setValor(rs.getDouble("VALOR"));
                t.setAplicaDescuento(rs.getInt("APLICA_DESCUENTO"));
                t.setAplicaGeneral(rs.getInt("APLICA_GENERAL"));
                t.setEs_valor_moneda(rs.getInt("ES_VALOR_MONEDA"));
                t.setEs_valor_porcentaje(rs.getInt("ES_PORCENTAJE"));
                t.setEs_fija(rs.getInt("ES_FIJA"));
                t.setDescuenta_pasajeros(rs.getInt("DESCUENTA_PASAJEROS"));
                t.setDescuenta_del_total(rs.getInt("DESCUENTA_DEL_TOTAL"));
                t.setEstado(rs.getInt("ESTADO"));
                lstcategorias.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectModulos() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_grupo (NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 3);");
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstmodulo = new ArrayList<Modulo>();
            while (rs.next()) {
                Modulo t = new Modulo();
                t.setId(rs.getInt("PK_GRUPO"));
                t.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                t.setPosicion(rs.getInt("POSICION"));
                t.setEstado(rs.getInt("ESTADO"));
                lstmodulo.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectservidoresDeImpresion() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        servidorDeImpresion si = null;
        lstitemservidorimpresion = new ArrayList<servidorDeImpresion>();
        for (PrintService printService : printServices) {
            si = new servidorDeImpresion();
            si.setNombre(printService.getName());
            lstitemservidorimpresion.add(si);
        }

    }

    public void selectSubModulosItems() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_acceso (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 3);");
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstitemsubmodulo = new ArrayList<SubItemModulo>();
            while (rs.next()) {
                SubItemModulo t = new SubItemModulo();
                t.setId(rs.getInt("PK_ACCESO"));
                t.setFk_grupo(rs.getInt("FK_GRUPO"));
                t.setFk_submodulo(rs.getInt("FK_SUBMODULO"));
                t.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE_ACCESO")));
                t.setNombreLargo(StringUtils.upperFirstLetter(rs.getString("NOMBRE_LARGO")));
                t.setTeclaAcceso(rs.getString("TECLA_ACCESO"));
                t.setUbicacion(rs.getString("UBICACION"));
                t.setRutaImagen(rs.getString("Imagen"));
                t.setSubGrupo(rs.getString("SUBGRUPO"));
                t.setPosicion(rs.getInt("POSICION"));
                t.setPosicionSubGrupo(rs.getInt("POSICIONSUBGRUPO"));
                t.setEstado(rs.getInt("ESTADO"));
                lstitemsubmodulo.add(t);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectSubModulos() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CALL proc_tbl_submodulo (NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3);");
        try {
            Statement createStatement = con.createStatement();
            int retorno = createStatement.executeUpdate("START TRANSACTION;");
            rs = createStatement.executeQuery(sql.toString());

            lstsubmodulo = new ArrayList<SubModulo>();
            while (rs.next()) {
                SubModulo t = new SubModulo();
                t.setId(rs.getInt("PK_SUBMODULO"));
                t.setFk_modulo(rs.getInt("FK_MODULO"));
                t.setNombre(rs.getString("NOMBRE"));
                t.setEstado(rs.getInt("ESTADO"));
                lstsubmodulo.add(t);
            }
            /*
            Set<String> linkedHashSet = new LinkedHashSet<String>();
		linkedHashSet.addAll(lstsubmodulo);
		lstsubmodulo.clear();
                lstsubmodulo.addAll(linkedHashSet);  */

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectDespacho() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT *, r.NOMBRE AS NOMBRE_RUTA FROM tbl_configuracion_despacho AS dph"
                + " INNER JOIN tbl_ruta AS r ON"
                + " r.PK_RUTA = dph.FK_RUTA AND r.ESTADO = 1"
                + " WHERE dph.ESTADO = 1";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lstdespacho = new ArrayList<Despacho>();
            while (rs.next()) {
                Despacho dph = new Despacho();
                dph.setId(rs.getInt("PK_CONFIGURACION_DESPACHO"));
                dph.setNombreDespacho(StringUtils.upperFirstLetter(rs.getString("NOMBRE_DESPACHO")));
                dph.setHoraInicio(rs.getTime("HORA_INICIO_RUTA"));
                dph.setHoraFin(rs.getTime("HORA_FIN_RUTA"));
                dph.setHoraPico(rs.getString("LISTA_HORAS_PICO"));
                dph.setGrupoMoviles(rs.getString("LISTA_VEHICULOS"));
                dph.setPuntosRuta(rs.getString("LISTA_PUNTOS"));
                dph.setIdRuta(rs.getInt("FK_RUTA"));
                dph.setNombreRuta(rs.getString("NOMBRE_RUTA"));
                dph.setTiempoLlegadaValle(rs.getString("TIEMPO_ENTRE_PUNTOS_VALLE"));
                dph.setTiempoLlegadaPico(rs.getString("TIEMPO_ENTRE_PUNTOS_PICO"));
                dph.setIntervaloValle(rs.getInt("INTERVALO_DESPACHO_VALLE"));
                dph.setIntervaloPico(rs.getInt("INTERVALO_DESPACHO_PICO"));
                dph.setTipoDia(rs.getString("TIPO_DIA"));
                dph.setCantidadVueltas(rs.getInt("CANTIDAD_VUELTAS"));
                dph.setRotarVehiculo(rs.getInt("ROTACION_VEHICULOS"));

                lstdespacho.add(dph);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectRutaDespacho() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT r.PK_RUTA, r.NOMBRE FROM tbl_configuracion_despacho AS cd"
                + " INNER JOIN tbl_ruta AS r ON"
                + "   r.PK_RUTA = cd.FK_RUTA AND r.ESTADO = 1"
                + " WHERE cd.ESTADO = 1"
                + " GROUP BY cd.FK_RUTA"
                + " ORDER BY r.NOMBRE ASC";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lstruta_despacho = new ArrayList<Ruta>();
            while (rs.next()) {
                Ruta r = new Ruta();
                r.setId(rs.getInt("PK_RUTA"));
                r.setNombre(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));

                lstruta_despacho.add(r);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    // Intervalo despacho planificado
    public void selectIntervaloDespacho() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT *,"
                + " DATEDIFF(FECHA_FINAL, FECHA_INICIAL)+1 AS CANTIDAD_DIAS"
                + " FROM tbl_intervalo_despacho WHERE ESTADO = 1 AND TIPO_GENERACION = ?"
                + " ORDER BY FECHA_FINAL DESC";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Constant.DPH_PLANIFICADO);
            rs = ps.executeQuery();

            lstintervalo_despacho = new ArrayList<IntervaloDespacho>();

            while (rs.next()) {
                IntervaloDespacho idph = new IntervaloDespacho();
                idph.setId(rs.getInt("PK_INTERVALO_DESPACHO"));
                idph.setNombreIntervalo(StringUtils.upperFirstLetter(rs.getString("NOMBRE_INTERVALO")));
                idph.setListaRuta(rs.getString("LISTA_RUTA"));
                idph.setFechaInicial(rs.getDate("FECHA_INICIAL"));
                idph.setFechaFinal(rs.getDate("FECHA_FINAL"));
                idph.setCantidadDias(rs.getInt("CANTIDAD_DIAS"));
                idph.setEstado(rs.getInt("ESTADO"));

                // Busqueda de nombres de ruta
                String nombre_rutas = rutasDeDespacho(idph.getListaRuta());
                idph.setListaNombreRuta(nombre_rutas);
                lstintervalo_despacho.add(idph);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectIntervaloDespachoManual() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " idph.PK_INTERVALO_DESPACHO,"
                + " pd.NUMERO_PLANILLA,"
                + " (SELECT r.NOMBRE FROM tbl_ruta AS r WHERE r.PK_RUTA = pd.FK_RUTA AND r.ESTADO = 1) AS NOMBRE_RUTA,"
                + " pd.NUMERO_VUELTA,"
                + " pd.PLACA,"
                + " pd.FECHA,"
                + " min(pd.HORA_PLANIFICADA) AS HORA_INICIO,"
                + " max(pd.HORA_PLANIFICADA) AS HORA_FIN"
                + " FROM tbl_intervalo_despacho AS idph"
                + " INNER JOIN tbl_planilla_despacho AS pd ON"
                + "   idph.PK_INTERVALO_DESPACHO = pd.FK_INTERVALO_DESPACHO"
                + " WHERE idph.ESTADO = 1 AND pd.TIPO_PUNTO >= 1"
                + " AND idph.TIPO_GENERACION = ?"
                + " GROUP BY idph.PK_INTERVALO_DESPACHO"
                + " ORDER BY pd.FK_RUTA ASC, pd.FECHA DESC, HORA_INICIO ASC, pd.PLACA ASC";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Constant.DPH_MANUAL);
            rs = ps.executeQuery();

            lstintervalo_despacho_manual = new ArrayList<IntervaloDespacho>();
            while (rs.next()) {
                IntervaloDespacho idph = new IntervaloDespacho();

                idph.setNumeroPlanilla(rs.getInt("NUMERO_PLANILLA"));
                idph.setNumeroVuelta(rs.getInt("NUMERO_VUELTA"));
                idph.setListaNombreRuta(rs.getString("NOMBRE_RUTA"));
                idph.setPlaca(rs.getString("PLACA"));
                idph.setFechaInicial(rs.getDate("FECHA"));
                idph.setHoraInicial(rs.getTime("HORA_INICIO"));
                idph.setHoraFinal(rs.getTime("HORA_FIN"));
                idph.setId(rs.getInt("PK_INTERVALO_DESPACHO"));

                lstintervalo_despacho_manual.add(idph);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public static String rutasDeDespacho(String lista_ruta) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        if (!Restriction.isThis(lista_ruta, "^[0-9,]+$")) {
            return lista_ruta;
        }

        String sql = "SELECT r.NOMBRE FROM tbl_ruta AS r WHERE"
                + " r.PK_RUTA IN (" + lista_ruta + ")"
                + " ORDER BY r.NOMBRE ASC";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            String lst_nruta = "";
            while (rs.next()) {
                String nombre_ruta = rs.getString("NOMBRE");
                lst_nruta += (lst_nruta == "")
                        ? nombre_ruta : ", " + nombre_ruta;
            }
            return lst_nruta;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return "";
    }

    public void selectProgramacionRuta() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT r.NOMBRE, pg_ruta.* FROM tbl_programacion_ruta AS pg_ruta"
                + " INNER JOIN tbl_ruta AS r ON"
                + "  r.PK_RUTA = pg_ruta.FK_RUTA AND r.ESTADO = 1"
                + " WHERE pg_ruta.ESTADO = 1";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_pgruta = new ArrayList<ProgramacionRuta>();
            while (rs.next()) {
                ProgramacionRuta pgr = new ProgramacionRuta();
                pgr.setIdRuta(rs.getInt("FK_RUTA"));
                pgr.setNombreRuta(StringUtils.upperFirstLetter(rs.getString("NOMBRE")));
                pgr.setGrupo_lun(rs.getInt("LUN"));
                pgr.setGrupo_mar(rs.getInt("MAR"));
                pgr.setGrupo_mie(rs.getInt("MIE"));
                pgr.setGrupo_jue(rs.getInt("JUE"));
                pgr.setGrupo_vie(rs.getInt("VIE"));
                pgr.setGrupo_sab(rs.getInt("SAB"));
                pgr.setGrupo_dom(rs.getInt("DOM"));

                lst_pgruta.add(pgr);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeStatement(st);
            UtilBD.closeResultSet(rs);
            pila_con.liberarConexion(con);
        }
    }

    /* 
        Listado completo de rutas programadas o no
        String sql = "SELECT "
            + " r.PK_RUTA,"
            + " r.NOMBRE,"
            + " pg_ruta.LUN, pg_ruta.MAR, pg_ruta.MIE,"
            + " pg_ruta.JUE, pg_ruta.VIE, pg_ruta.SAB,"
            + " pg_ruta.DOM"
            + " FROM tbl_ruta AS r"
            + " LEFT OUTER JOIN tbl_programacion_ruta AS pg_ruta ON"
            + "  pg_ruta.FK_RUTA = r.PK_RUTA AND pg_ruta.ESTADO = 1"
            + " WHERE r.ESTADO = 1"
            + " ORDER BY pg_ruta.ESTADO DESC, r.NOMBRE ASC"; */
    public void selectProgramacionRuta_completa() {
        lst_pgruta_completa = ProgramacionRutaBD.select();
    }

    public void selectProgramacionRuta_nombre() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_pgr WHERE ESTADO = 1";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_pgruta_nombre = new ArrayList<ProgramacionRuta_nombre>();

            while (rs.next()) {
                ProgramacionRuta_nombre pg = new ProgramacionRuta_nombre();
                pg.setId(rs.getInt("PK_PGR"));
                pg.setNombre(rs.getString("NOMBRE"));
                lst_pgruta_nombre.add(pg);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public ProgramacionRuta selectProgramacionRuta_activa() {
        return DespachoBD.programacion_ruta_activa();        
    }

    private static String meses[] = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    public void selectDiasFestivo() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " PK_DIA_FESTIVO,"
                + " DIA_FESTIVO,"
                + " DAY(DIA_FESTIVO) AS DIA,"
                + " MONTH(DIA_FESTIVO) AS MES,"
                + " YEAR(DIA_FESTIVO) AS ANIO"
                + " FROM tbl_dia_festivo ORDER BY DIA_FESTIVO ASC";

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_diasfestivo = new ArrayList<Dia>();
            while (rs.next()) {
                int id = rs.getInt("PK_DIA_FESTIVO");
                int dia = rs.getInt("DIA");
                int mes = rs.getInt("MES");
                int anio = rs.getInt("ANIO");
                String sfecha = fmt.format(rs.getDate("DIA_FESTIVO"));
                String descp = dia + " de " + meses[mes - 1] + " del " + anio;

                Dia ddia = new Dia();
                ddia.setId(id);
                ddia.setFecha(sfecha);
                ddia.setDescripcion(descp);

                lst_diasfestivo.add(ddia);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public static ArrayList<String> diasFestivo() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT DIA_FESTIVO FROM tbl_dia_festivo";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            String dia_festivo;
            ArrayList<String> lst_dias_festivo = new ArrayList<String>();
            while (rs.next()) {
                dia_festivo = fmt.format(rs.getDate("DIA_FESTIVO"));
                lst_dias_festivo.add(dia_festivo);
            }
            return lst_dias_festivo;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
        return null;
    }

    public void selectMoneda() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT PK_MONEDA, CODIGO_ISO, CODIGO_NUM, NOMBRE, SIMBOLO"
                + " FROM tbl_moneda";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_moneda = new ArrayList<Moneda>();
            while (rs.next()) {
                Moneda m = new Moneda();
                m.setId(rs.getInt("PK_MONEDA"));
                m.setCodigoISO(rs.getString("CODIGO_ISO"));
                m.setCodigoNum("" + rs.getInt("CODIGO_NUM"));
                m.setNombre(rs.getString("NOMBRE"));
                m.setSimbolo(rs.getString("SIMBOLO"));
                lst_moneda.add(m);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public void selectZonaHoraria() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_zonahoraria";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_zonahoraria = new ArrayList<ZonaHoraria>();
            while (rs.next()) {
                ZonaHoraria zh = new ZonaHoraria();
                zh.setId(rs.getInt("PK_ZONAHORARIA"));
                zh.setGMT(rs.getString("GMT"));
                zh.setNombre(rs.getString("NOMBRE"));
                lst_zonahoraria.add(zh);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public void selectTipoDocumento() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_tipo_documento WHERE ESTADO = 1";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_tipodocumento = new ArrayList<TipoDocumento>();
            while (rs.next()) {
                TipoDocumento tp = new TipoDocumento();
                tp.setId(rs.getInt("id"));
                tp.setTipo(rs.getString("tipo"));
                tp.setDescripcion(rs.getString("descripcion"));
                tp.setEstado(rs.getInt("estado"));
                lst_tipodocumento.add(tp);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public void selectVueltaCerrada() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT"
                + " PLACA, NUMERO_INTERNO,"
                + " BASE, NUMERACION, MOTIVO,"
                + " date(FECHA) AS FECHA,"
                + " time(FECHA) AS HORA,"
                + " USUARIO"
                + " FROM tbl_vuelta_cerrada ORDER BY FECHA DESC, HORA DESC";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            lst_vueltacerrada = new ArrayList<VueltaCerrada>();
            while (rs.next()) {
                VueltaCerrada vc = new VueltaCerrada();
                vc.setPlaca(rs.getString("PLACA"));
                vc.setNumero_interno(rs.getString("NUMERO_INTERNO"));
                vc.setFecha(rs.getDate("FECHA"));
                vc.setHora(rs.getTime("HORA"));
                vc.setBase(rs.getString("BASE"));
                vc.setNumeracion(rs.getLong("NUMERACION"));
                vc.setMotivo(rs.getString("MOTIVO"));
                vc.setUsuario(rs.getString("USUARIO"));
                lst_vueltacerrada.add(vc);
            }

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(st);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPropietario() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        Propietario p = new Propietario();
        p.setPerfil("Propietario");
        Perfil profile = PropietarioBD.selectProfileOwner(p);
        sql.append("SELECT * FROM tbl_usuario WHERE (FK_PERFIL =?) AND (ESTADO = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, profile.getId());
            rs = ps.executeQuery();
            Propietario a = null;
            lst_propietario = new ArrayList<>();
            while (rs.next()) {
                a = new Propietario();
                a.setId(rs.getInt("PK_USUARIO"));
                a.setFk_perfil(rs.getInt("FK_PERFIL"));
                a.setNombre(rs.getString("NOMBRE"));
                a.setApellido(rs.getString("APELLIDO"));
                a.setEmail(rs.getString("EMAIL"));
                a.setEstado(rs.getInt("ESTADO"));
                lst_propietario.add(a);
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
    }

    public void selectHardware() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        Hardware a = null;
        sql.append("SELECT * FROM tbl_hardware WHERE (estado = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_hardware = new ArrayList<>();
            while (rs.next()) {
                a = new Hardware();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));
                a.setEstado(rs.getInt("estado"));
                lst_hardware.add(a);
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
    }

    public void selectGpsHardware() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        RelationShipHardwareGpsCar a = null;
        sql.append("SELECT * FROM tbl_gps_hardware WHERE (estado = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_gpshardware = new ArrayList<>();
            while (rs.next()) {
                a = new RelationShipHardwareGpsCar();
                a.setId(rs.getInt("id"));
                a.setFk_vehiculo(rs.getInt("fk_vehiculo"));
                a.setFk_hardware(rs.getInt("fk_hardware"));
                a.setFk_gps(rs.getString("fk_gps"));
                a.setFecha_instalacion(rs.getString("fecha_instalacion"));
                a.setEstado(rs.getInt("estado"));
                lst_gpshardware.add(a);
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
    }

    public void selectGpsRegistrado() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        GpsRegistrado a = null;
        sql.append("SELECT gi.*, gm.nombre as marca, gmo.nombre as modelo , ts.num_cel as celular");
        sql.append(" FROM tbl_gps_inventario as gi INNER JOIN tbl_gps_marca AS gm ON gi.fk_marca=gm.id INNER JOIN tbl_gps_modelo as gmo ON gi.fk_modelo=gmo.id INNER JOIN tbl_gps_sim AS sg ON sg.fk_gps=gi.id INNER JOIN tbl_tarjeta_sim as ts ON ts.id=sg.fk_sim ");
        sql.append(" WHERE (gi.estado > -1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_gpsregistrado = new ArrayList<>();
            while (rs.next()) {
                a = new GpsRegistrado();
                a.setId(rs.getString("id"));
                a.setImei(rs.getString("imei"));
                a.setFk_marca(rs.getInt("fk_marca"));
                a.setMarca(rs.getString("marca"));
                a.setFk_modelo(rs.getInt("fk_modelo"));
                a.setModelo(rs.getString("modelo"));
                a.setCelular(rs.getString("celular"));
                a.setEstado(rs.getInt("estado"));
                lst_gpsregistrado.add(a);
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
    }

    public void selectGpsMarca() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        GpsMarca a = null;
        sql.append("SELECT * FROM tbl_gps_marca WHERE (estado = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_gpsmarca = new ArrayList<>();
            while (rs.next()) {
                a = new GpsMarca();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));
                a.setEstado(rs.getInt("estado"));
                lst_gpsmarca.add(a);
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
    }

    public void selectGpsModelo() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        GpsModelo a = null;
        sql.append("SELECT * FROM tbl_gps_modelo WHERE (estado = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_gpsmodelo = new ArrayList<>();
            while (rs.next()) {
                a = new GpsModelo();
                a.setId(rs.getInt("id"));
                a.setFk_marca(rs.getInt("id_marca"));
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));
                a.setEstado(rs.getInt("estado"));
                lst_gpsmodelo.add(a);
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
    }

    public void selectOperador() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        Operador a = null;
        sql.append("SELECT * FROM tbl_operador_celular WHERE (estado = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_operador = new ArrayList<>();
            while (rs.next()) {
                a = new Operador();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));
                a.setEstado(rs.getInt("estado"));
                lst_operador.add(a);
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
    }

    public void selectTarjetaSim() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        TarjetaSim a = null;
        sql.append("SELECT id, fk_operador, codigo, num_cel, estado FROM tbl_tarjeta_sim WHERE (estado = 1)");
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_tarjeta_sim = new ArrayList<>();
            while (rs.next()) {
                a = new TarjetaSim();
                a.setId(rs.getInt("id"));
                a.setFk_operador(rs.getInt("fk_operador"));
                a.setCodigo(rs.getString("codigo"));
                a.setNum_cel(rs.getString("num_cel"));
                a.setEstado(rs.getInt("estado"));
                lst_tarjeta_sim.add(a);
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
    }

    public void selectCategoriaLicencia() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoriaLicencia categoriaLicencia;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT pk_categoria, nombre, descripcion, estado FROM tbl_categoria_licencia");

        try {
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_categoriasLicencia = new ArrayList<>();

            while (rs.next()) {
                categoriaLicencia = new CategoriaLicencia();
                categoriaLicencia.setId(rs.getLong("pk_categoria"));
                categoriaLicencia.setNombre(rs.getString("nombre"));
                categoriaLicencia.setDescripcion(rs.getString("descripcion"));
                categoriaLicencia.setEstado(rs.getInt("estado"));
                lst_categoriasLicencia.add(categoriaLicencia);
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
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectOficinaTransito() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        OficinaTransito oficinaTransito;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT oficina.pk_oficina_transito, oficina.nombre, oficina.telefono, oficina.direccion, ");
        sql.append("oficina.correo_electronico, oficina.fk_ciudad, oficina.estado, ciudad.NOMBRE as nombreCiudad ");
        sql.append("FROM tbl_oficina_transito oficina ");
        sql.append("inner join tbl_ciudad ciudad ON oficina.FK_CIUDAD = ciudad.PK_CIUDAD");

        try {
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_oficinasTransito = new ArrayList<>();

            while (rs.next()) {
                oficinaTransito = new OficinaTransito();
                oficinaTransito.setId(rs.getLong("pk_oficina_transito"));
                oficinaTransito.setNombre(rs.getString("nombre"));
                oficinaTransito.setTelefono(rs.getString("telefono"));
                oficinaTransito.setDireccion(rs.getString("direccion"));
                oficinaTransito.setCorreo(rs.getString("correo_electronico"));
                oficinaTransito.setFk_ciudad(rs.getInt("fk_ciudad"));
                oficinaTransito.setNombreCiudad(rs.getString("nombreCiudad"));
                oficinaTransito.setEstado(rs.getInt("estado"));
                lst_oficinasTransito.add(oficinaTransito);
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
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectLicenciaTransito() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LicenciaTransito licenciaTransito;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT licencia.PK_LICENCIA_CONDUCCION, licencia.FK_CONDUCTOR, ");
        sql.append("CONCAT(conductor.NOMBRE, ' ', conductor.APELLIDO) as nombreConductor, ");
        sql.append("licencia.NUMERO_LICENCIA, licencia.FECHA_EXPEDICION, licencia.VIGENCIA, licencia.FK_CATEGORIA, ");
        sql.append("categoria.NOMBRE as nombreCategoria, licencia.FK_OFICINA_TRANSITO, ");
        sql.append("oficina.NOMBRE as nombreOficina, licencia.OBSERVACIONES, licencia.ESTADO ");
        sql.append("FROM tbl_licencia_conduccion licencia ");
        sql.append("INNER JOIN tbl_conductor conductor ON licencia.FK_CONDUCTOR = conductor.PK_CONDUCTOR ");
        sql.append("INNER JOIN tbl_categoria_licencia categoria ON licencia.FK_CATEGORIA = categoria.PK_CATEGORIA ");
        sql.append("INNER JOIN tbl_oficina_transito oficina ON licencia.FK_OFICINA_TRANSITO = oficina.PK_OFICINA_TRANSITO ");

        try {
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_licenciasTransito = new ArrayList<>();

            while (rs.next()) {
                licenciaTransito = new LicenciaTransito();
                licenciaTransito.setId(rs.getLong("pk_licencia_conduccion"));
                licenciaTransito.setFk_conductor(rs.getInt("fk_conductor"));
                licenciaTransito.setNombreConductor(rs.getString("nombreConductor"));
                licenciaTransito.setNumeroLicencia(rs.getString("numero_licencia"));
                licenciaTransito.setFechaExpedicion(rs.getDate("fecha_expedicion"));
                licenciaTransito.setVigencia(rs.getDate("vigencia"));
                licenciaTransito.setFk_categoria(rs.getLong("fk_categoria"));
                licenciaTransito.setNombreCategoria(rs.getString("nombreCategoria"));
                licenciaTransito.setFk_oficina_transito(rs.getLong("fk_oficina_transito"));
                licenciaTransito.setNombreOficina(rs.getString("nombreOficina"));
                licenciaTransito.setObservaciones(rs.getString("observaciones"));
                licenciaTransito.setEstado(rs.getInt("estado"));
                lst_licenciasTransito.add(licenciaTransito);
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
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectAseguradora() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Aseguradora aseguradora;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_aseguradora");

        try {
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            lst_aseguradoras = new ArrayList<>();

            while (rs.next()) {
                aseguradora = new Aseguradora();
                aseguradora.setId(rs.getLong("pk_aseguradora"));
                aseguradora.setNombre(rs.getString("nombre"));
                aseguradora.setTelefono(rs.getString("telefono"));
                aseguradora.setPaginaWeb(rs.getString("pagina_web"));
                aseguradora.setEstado(rs.getInt("estado"));

                if (!aseguradora.getPaginaWeb().startsWith("http")) {
                    aseguradora.setPaginaWebBack(String.format("http://%s", aseguradora.getPaginaWeb()));
                } else {
                    aseguradora.setPaginaWebBack(aseguradora.getPaginaWeb());
                }

                lst_aseguradoras.add(aseguradora);
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
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectPoliza() {
        lst_polizas = PolizaBD.select();
    }

    public void selectVehiculoAsegurado() {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        VehiculoAsegurado vehiculoAsegurado;
        StringBuilder query = new StringBuilder();
        query.append("SELECT polizaVehiculo.PK_POLIZA_VEHICULO, polizaVehiculo.FK_POLIZA, poliza.NOMBRE as nombrePoliza, ");
        query.append("polizaVehiculo.FK_VEHICULO, vehiculo.PLACA as placaVehiculo, empresa.NOMBRE as nombreEmpresaVehiculo, ");
        query.append("polizaVehiculo.NUMERO_POLIZA, polizaVehiculo.INICIO_VIGENCIA, polizaVehiculo.FIN_VIGENCIA, ");
        query.append("polizaVehiculo.VALOR_PRIMA, polizaVehiculo.ESTADO ");
        query.append("FROM tbl_vehiculo_asegurado polizaVehiculo ");
        query.append("INNER JOIN tbl_poliza poliza ON polizaVehiculo.FK_POLIZA = poliza.PK_POLIZA ");
        query.append("INNER JOIN tbl_aseguradora aseguradora ON poliza.FK_ASEGURADORA = aseguradora.PK_ASEGURADORA ");
        query.append("INNER JOIN tbl_vehiculo vehiculo ON polizaVehiculo.FK_VEHICULO = vehiculo.PK_VEHICULO ");
        query.append("INNER JOIN tbl_empresa empresa ON vehiculo.FK_EMPRESA = empresa.PK_EMPRESA ");

        try {
            ps = con.prepareStatement(query.toString());
            rs = ps.executeQuery();
            lst_vehiculosAsegurados = new ArrayList<>();

            while (rs.next()) {
                vehiculoAsegurado = new VehiculoAsegurado();
                vehiculoAsegurado.setId(rs.getLong("PK_POLIZA_VEHICULO"));
                vehiculoAsegurado.setFkPoliza(rs.getLong("FK_POLIZA"));
                vehiculoAsegurado.setFkVehiculo(rs.getInt("FK_VEHICULO"));
                vehiculoAsegurado.setNumeroPoliza(rs.getString("NUMERO_POLIZA"));
                vehiculoAsegurado.setInicioVigencia(rs.getDate("INICIO_VIGENCIA"));
                vehiculoAsegurado.setFinVigencia(rs.getDate("FIN_VIGENCIA"));
                vehiculoAsegurado.setValorPrima(rs.getDouble("VALOR_PRIMA"));
                vehiculoAsegurado.setEstado(rs.getInt("ESTADO"));
                vehiculoAsegurado.setNombrePoliza(rs.getString("nombrePoliza"));
                vehiculoAsegurado.setPlacaVehiculo(rs.getString("placaVehiculo"));
                vehiculoAsegurado.setNombreEmpresaVehiculo(rs.getString("nombreEmpresaVehiculo"));
                lst_vehiculosAsegurados.add(vehiculoAsegurado);
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
            UtilBD.closeResultSet(rs);
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);
        }
    }

    public void selectTipoEventoMantenimiento() {
        lst_tipoEventoMantenimiento = TipoEventoMantenimientoBD.select();
    }

    public void selectMantenimiento() {
        lst_mantenimiento = MantenimientoBD.select();
    }

    public void selectTipoPoliza() {
        lst_tipoPoliza = TipoPolizaBD.select();
    }

    private void selectVehiculosMantenimiento() {
        lst_vehiculosMantenimiento = VehiculoMantenimientoBD.select();
    }    
    
    private void selectConfiguracionesDeLiquidacion() {
        lst_configuracionesDeLiquidacion = ConfiguracionLiquidacionBD.select();
    }
    
    
    /*
        GETTERS AND SETTERS
    */
    public ArrayList<Pais> getLstpais() {
        return lstpais;
    }

    public ArrayList<Departamento> getLstdpto() {
        return lstdpto;
    }

    public ArrayList<Ciudad> getLstciudad() {
        return lstciudad;
    }

    public ArrayList<Perfil> getLstperfil() {
        return lstperfil;
    }

    public ArrayList<Empresa> getLstempresa() {
        return lstempresa;
    }

    public ArrayList<GrupoEmpresa> getLstgempresa() {
        return lstgempresa;
    }

    public ArrayList<Usuario> getLstusuario() {
        return lstusuario;
    }

    public ArrayList<Conductor> getLstconductor() {
        return lstconductor;
    }

    public ArrayList<Conductor> getLstconductoractivo() {
        return lstconductoractivo;
    }

    public ArrayList<Alarma> getLstalarma() {
        return lstalarma;
    }

    public ArrayList<Evento> getLstevento() {
        return lstevento;
    }

    public ArrayList<Movil> getLstmovil() {
        return lstmovil;
    }

    public ArrayList<Base> getLstbase() {
        return lstbase;
    }

    public ArrayList<Punto> getLstpunto() {
        return lstpunto;
    }

    public ArrayList<Punto> getLstpuntobase() {
        return lstpuntobase;
    }

    public ArrayList<Ruta> getLstruta() {
        return lstruta;
    }

    public ArrayList<Ruta> getLstrutas() {
        return lstrutas;
    }

    public ArrayList<PuntoRuta> getLstpuntoruta_completa() {
        return lstpuntoruta_completa;
    }

    public ArrayList<Despacho> getLstpuntoruta_cruzada() {
        return lstpuntoruta_cruzada;
    }

    public ArrayList<PuntoRuta> getLstpuntoruta() {
        return lstpuntoruta;
    }

    public ArrayList<BaseRuta> getLstbaseruta() {
        return lstbaseruta;
    }

    public ArrayList<Tarifa> getLsttarifa() {
        return lsttarifa;
    }

    public ArrayList<Motivo> getLstmotivo() {
        return lstmotivo;
    }

    public ArrayList<RelacionVehiculoConductor> getLstrelacionvehiculoconductor() {
        return lstrelacionvehiculoconductor;
    }

    public ArrayList<RelacionVehiculoEmpresa> getLstrelacionvehiculoempresa() {
        return lstrelacionvehiculoempresa;
    }

    public ArrayList<GrupoMovil> getLstgrupomoviles() {
        return lstgrupomoviles;
    }

    public ArrayList<GrupoMovil> getLstgrupovehiculos() {
        return lstgrupovehiculos;
    }

    public ArrayList<CategoriasDeDescuento> getLstcategorias() {
        return lstcategorias;
    }

    public ArrayList<Modulo> getLstmodulo() {
        return lstmodulo;
    }

    public ArrayList<SubItemModulo> getLstitemsubmodulo() {
        return lstitemsubmodulo;
    }

    public ArrayList<SubModulo> getLstsubmodulo() {
        return lstsubmodulo;
    }

    public ArrayList<PuntoRuta> getLstpuntoruta_despacho() {
        return lstpuntoruta_despacho;
    }

    public ArrayList<GrupoMovilDespacho> getLstgrupomoviles_despacho() {
        return lstgrupomoviles_despacho;
    }

    public ArrayList<Despacho> getLstdespacho() {
        return lstdespacho;
    }

    public ArrayList<IntervaloDespacho> getLstintervalo_despacho() {
        return lstintervalo_despacho;
    }

    public ArrayList<IntervaloDespacho> getLstintervalo_despacho_manual() {
        return lstintervalo_despacho_manual;
    }

    public ArrayList<Ruta> getLstruta_despacho() {
        return lstruta_despacho;
    }

    public ArrayList<ProgramacionRuta> getLst_pgruta() {
        return lst_pgruta;
    }

    public ArrayList<ProgramacionRuta> getLst_pgruta_completa() {
        return lst_pgruta_completa;
    }

    public ArrayList<ProgramacionRuta_nombre> getLst_pgruta_nombre() {
        return lst_pgruta_nombre;
    }

    public ArrayList<Base> getLstbase_pr() {
        return lstbase_pr;
    }

    public ArrayList<Moneda> getLst_moneda() {
        return lst_moneda;
    }

    public ArrayList<ZonaHoraria> getLst_zonahoraria() {
        return lst_zonahoraria;
    }

    public ArrayList<TipoDocumento> getLst_tipodocumento() {
        return lst_tipodocumento;
    }

    public ArrayList<servidorDeImpresion> getLstimpresion() {
        return lstitemservidorimpresion;
    }

    public ArrayList<VueltaCerrada> getLst_vueltacerrada() {
        return lst_vueltacerrada;
    }

    public ArrayList<Propietario> getLst_propietario() {
        return lst_propietario;
    }

    public ArrayList<Hardware> getLst_hardware() {
        return lst_hardware;
    }

    public ArrayList<RelationShipHardwareGpsCar> getLst_gpshardware() {
        return lst_gpshardware;
    }

    public ArrayList<GpsMarca> getLst_gpsmarca() {
        return lst_gpsmarca;
    }

    public ArrayList<GpsModelo> getLst_gpsmodelo() {
        return lst_gpsmodelo;
    }

    public ArrayList<GpsRegistrado> getLst_gpsregistrado() {
        return lst_gpsregistrado;
    }

    public ArrayList<Operador> getLst_operador() {
        return lst_operador;
    }

    public ArrayList<Dia> getLst_diasfestivo() {
        return lst_diasfestivo;
    }

    public ArrayList<TarjetaSim> getLst_tarjeta_sim() {
        return lst_tarjeta_sim;
    }

    public ArrayList<CentroDiagnostico> getLstCentroDiagnostico() {
        return lstCentroDiagnostico;
    }

    public ArrayList<CentroExpedicion> getLstCentroExpedicion() {
        return lstCentroExpedicion;
    }

    public ArrayList<TarjetaOperacion> getLstDocumentosVehiculo() {
        return lstDocumentosVehiculo;
    }

    /**
     * @return the lst_categoriasLicencia
     */
    public List<CategoriaLicencia> getLst_categoriasLicencia() {
        return lst_categoriasLicencia;
    }

    /**
     * @param lst_categoriasLicencia the lst_categoriasLicencia to set
     */
    public void setLst_categoriasLicencia(List<CategoriaLicencia> lst_categoriasLicencia) {
        this.lst_categoriasLicencia = lst_categoriasLicencia;
    }

    /**
     * @return the lst_oficinasTransito
     */
    public List<OficinaTransito> getLst_oficinasTransito() {
        return lst_oficinasTransito;
    }

    /**
     * @param lst_oficinasTransito the lst_oficinasTransito to set
     */
    public void setLst_oficinasTransito(List<OficinaTransito> lst_oficinasTransito) {
        this.lst_oficinasTransito = lst_oficinasTransito;
    }

    /**
     * @return the lst_licenciasTransito
     */
    public List<LicenciaTransito> getLst_licenciasTransito() {
        return lst_licenciasTransito;
    }

    /**
     * @param lst_licenciasTransito the lst_licenciasTransito to set
     */
    public void setLst_licenciasTransito(List<LicenciaTransito> lst_licenciasTransito) {
        this.lst_licenciasTransito = lst_licenciasTransito;
    }

    /**
     * @return the lst_aseguradoras
     */
    public List<Aseguradora> getLst_aseguradoras() {
        return lst_aseguradoras;
    }

    /**
     * @param lst_aseguradoras the lst_aseguradoras to set
     */
    public void setLst_aseguradoras(List<Aseguradora> lst_aseguradoras) {
        this.lst_aseguradoras = lst_aseguradoras;
    }

    /**
     * @return the lst_polizas
     */
    public List<Poliza> getLst_polizas() {
        return lst_polizas;
    }

    /**
     * @param lst_polizas the lst_polizas to set
     */
    public void setLst_polizas(List<Poliza> lst_polizas) {
        this.lst_polizas = lst_polizas;
    }

    /**
     * @return the lst_vehiculosAsegurados
     */
    public List<VehiculoAsegurado> getLst_vehiculosAsegurados() {
        return lst_vehiculosAsegurados;
    }

    /**
     * @param lst_vehiculosAsegurados the lst_vehiculosAsegurados to set
     */
    public void setLst_vehiculosAsegurados(List<VehiculoAsegurado> lst_vehiculosAsegurados) {
        this.lst_vehiculosAsegurados = lst_vehiculosAsegurados;
    }

    /**
     * @return the lst_tipoEventoMantenimiento
     */
    public List<TipoEventoMantenimiento> getLst_tipoEventoMantenimiento() {
        return lst_tipoEventoMantenimiento;
    }

    /**
     * @param lst_tipoEventoMantenimiento the lst_tipoEventoMantenimiento to set
     */
    public void setLst_tipoEventoMantenimiento(List<TipoEventoMantenimiento> lst_tipoEventoMantenimiento) {
        this.lst_tipoEventoMantenimiento = lst_tipoEventoMantenimiento;
    }

    /**
     * @return the lst_mantenimiento
     */
    public List<Mantenimiento> getLst_mantenimiento() {
        return lst_mantenimiento;
    }

    /**
     * @param lst_mantenimiento the lst_mantenimiento to set
     */
    public void setLst_mantenimiento(List<Mantenimiento> lst_mantenimiento) {
        this.lst_mantenimiento = lst_mantenimiento;
    }

    /**
     * @return the lst_tipoPoliza
     */
    public List<TipoPoliza> getLst_tipoPoliza() {
        return lst_tipoPoliza;
    }

    /**
     * @param lst_tipoPoliza the lst_tipoPoliza to set
     */
    public void setLst_tipoPoliza(List<TipoPoliza> lst_tipoPoliza) {
        this.lst_tipoPoliza = lst_tipoPoliza;
    }

    /**
     * @return the lst_vehiculosMantenimiento
     */
    public List<VehiculoMantenimiento> getLst_vehiculosMantenimiento() {
        return lst_vehiculosMantenimiento;
    }

    /**
     * @param lst_vehiculosMantenimiento the lst_vehiculosMantenimiento to set
     */
    public void setLst_vehiculosMantenimiento(List<VehiculoMantenimiento> lst_vehiculosMantenimiento) {
        this.lst_vehiculosMantenimiento = lst_vehiculosMantenimiento;
    }

    /**
     * @return the lst_configuracionesDeLiquidacion
     */
    public List<ConfiguracionLiquidacion> getLst_configuracionesDeLiquidacion() {
        return lst_configuracionesDeLiquidacion;
    }

    /**
     * @param lst_configuracionesDeLiquidacion the lst_configuracionesDeLiquidacion to set
     */
    public void setLst_configuracionesDeLiquidacion(List<ConfiguracionLiquidacion> lst_configuracionesDeLiquidacion) {
        this.lst_configuracionesDeLiquidacion = lst_configuracionesDeLiquidacion;
    }

}
