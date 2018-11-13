/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author lider_desarrollador
 */
public class ParametrosReporte {

    private String nombreReporte;
    private String tituloReporte;
    private int tipoReporte;
    private int idVehiculo;
    private int idUsuario;
    private int idAlarma;    
    private int idRuta;
    private int idPunto;
    private int idBase;
    private int idEmpresa;
    private Date fechaInicio;
    private Date fechaFinal;
    private String fechaInicioStr;
    private String fechaFinalStr;
    private String listaAlarmas;
    private String listaRutas;
    private String listaVehiculos;
    private String listaVehiculosPlaca;
    private String listaConductores;    
    private boolean unaRuta;
    private int meta;
    private double meta_real;
    private boolean cruzarDespacho;
    private boolean usuarioPropietario = false;

    private String placa;
    private String numeroInterno;
    private String nombreEmpresa;
    private String nitEmpresa;
    private String nombreRuta;
    private String nombrePunto;
    private int idUsuarioLiquidador;

    private SimpleDateFormat fechaSQL = new SimpleDateFormat("yyyy-MM-dd");
    private String nombresUsuarioLiquidador;
    
    public ParametrosReporte() {}

    // Parametriza/Asigna cada valor de consulta en su respectiva variable y tipo
    public ParametrosReporte(Map<String, String> h) {
        
        if (h.get("tipoReporte") != null) {
            tipoReporte = Restriction.getNumber(h.get("tipoReporte"));
        }
        if (h.get("nombreReporte") != null) {
            nombreReporte = h.get("nombreReporte");
        }
        if (h.get("tituloReporte") != null) {
            tituloReporte = h.get("tituloReporte");
        }
        if (h.get("idVehiculo") != null) {
            idVehiculo = Restriction.getNumber(h.get("idVehiculo"));
        }
        if (h.get("idUsuario") != null) {
            idUsuario = Restriction.getNumber(h.get("idUsuario"));
        }
        if (h.get("idAlarma") != null) {
            idAlarma = Restriction.getNumber(h.get("idAlarma"));
        }
        if (h.get("idRuta") != null) {
            idRuta = Restriction.getNumber(h.get("idRuta"));
        }
        if (h.get("idBase") != null) {
            idBase = Restriction.getNumber(h.get("idBase"));
        }
        if (h.get("idEmpresa") != null) {
            idEmpresa = Restriction.getNumber(h.get("idEmpresa"));
        }
        if (h.get("fechaInicio") != null) {
            fechaInicioStr = h.get("fechaInicio");
        }
        if (h.get("fechaFinal") != null) {
            fechaFinalStr = h.get("fechaFinal");
        }
        if (h.get("strAlarmas") != null) {
            listaAlarmas = h.get("strAlarmas");
        }
        if (h.get("strRutas") != null) {
            listaRutas = h.get("strRutas");
        }
        if (h.get("strVehiculos") != null) {
            listaVehiculos = h.get("strVehiculos");
        }
        if (h.get("strVehiculosPlaca") != null) {
            listaVehiculosPlaca = h.get("strVehiculosPlaca");
        }
        if (h.get("strConductores") != null) {
            listaConductores = h.get("strConductores");
        }

        if (h.get("placa") != null) {
            placa = h.get("placa");
        }
        if (h.get("numInterno") != null) {
            numeroInterno = h.get("numInterno");
        }        
        if (h.get("nombreRuta") != null) {
            nombreRuta = h.get("nombreRuta");
        }
        if (h.get("nombreEmpresa") != null) {
            nombreEmpresa = h.get("nombreEmpresa");
        }
        if (h.get("nitEmpresa") != null) {
            nitEmpresa = h.get("nitEmpresa");
        }
        if (h.get("idUsuarioLiquidador") != null) {
            idUsuarioLiquidador = Restriction.getNumber(h.get("idUsuarioLiquidador"));
        }
        if (h.get("nombresUsuarioLiquidador") != null) {
            nombresUsuarioLiquidador = h.get("nombresUsuarioLiquidador");
        }
        if (h.get("meta") != null) {
            meta      = Restriction.getNumber(h.get("meta"));
            meta_real = Double.parseDouble(h.get("meta"));
            meta_real /= 100.0;
        }
        if (h.get("cruzarDespacho") != null) {
            int cruzarDph  = Restriction.getNumber(h.get("cruzarDespacho"));
            cruzarDespacho = (cruzarDph == 1) ? true : false;
        }
        if (h.get("usuarioPropietario") != null) {
            int usuarioProp    = Restriction.getNumber(h.get("usuarioPropietario"));
            usuarioPropietario = (usuarioProp == 1) ? true : false;
        }        

        String _unaRuta = h.get("unaRuta");

        unaRuta = (_unaRuta != null && _unaRuta.equals("t"))
                ? true
                : false;
    }

    public int getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(int tipoReporte) {
        this.tipoReporte = tipoReporte;
    }
    
    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getTituloReporte() {
        return tituloReporte;
    }

    public void setTituloReporte(String tituloReporte) {
        this.tituloReporte = tituloReporte;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }
    
    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public SimpleDateFormat getFechaSQL() {
        return fechaSQL;
    }

    public void setFechaSQL(SimpleDateFormat fechaSQL) {
        this.fechaSQL = fechaSQL;
    }

    public String getFechaInicioStr() {
        return fechaInicioStr;
    }

    public void setFechaInicioStr(String fechaInicioStr) {
        this.fechaInicioStr = fechaInicioStr;
    }

    public String getFechaFinalStr() {
        return fechaFinalStr;
    }

    public void setFechaFinalStr(String fechaFinalStr) {
        this.fechaFinalStr = fechaFinalStr;
    }

    public String getListaAlarmas() {
        return listaAlarmas;
    }

    public void setListaAlarmas(String listaAlarmas) {
        this.listaAlarmas = listaAlarmas;
    }
    
    public String getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(String listaRutas) {
        this.listaRutas = listaRutas;
    }

    public String getListaVehiculos() {
        return listaVehiculos;
    }

    public void setListaVehiculos(String listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public String getListaVehiculosPlaca() {
        return listaVehiculosPlaca;
    }

    public void setListaVehiculosPlaca(String listaVehiculosPlaca) {
        this.listaVehiculosPlaca = listaVehiculosPlaca;
    }
    
    public boolean unaRuta() {
        return unaRuta;
    }

    public void unaRuta(boolean unaRuta) {
        this.unaRuta = unaRuta;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }
    
    public Date toDate(String sd) {
        try {
            Date d = fechaSQL.parse(sd);
            return d;
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }
    
    public int getIdUsuarioLiquidador() {
        return idUsuarioLiquidador;
    }

    public void setIdUsuarioLiquidador(int idUsuarioLiquidador) {
        this.idUsuarioLiquidador = idUsuarioLiquidador;
    }

    public String getNombresUsuarioLiquidador() {
        return nombresUsuarioLiquidador;
    }

    public void setNombresUsuarioLiquidador(String nombresUsuarioLiquidador) {
        this.nombresUsuarioLiquidador = nombresUsuarioLiquidador;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public double getMeta_real() {
        return meta_real;
    }

    public void setMeta_real(double meta_real) {
        this.meta_real = meta_real;
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public boolean esUnaRuta() {
        return unaRuta;
    }

    public void setUnaRuta(boolean unaRuta) {
        this.unaRuta = unaRuta;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public boolean cruzarDespacho() {
        return cruzarDespacho;
    }

    public void setCruzarDespacho(boolean cruzarDespacho) {
        this.cruzarDespacho = cruzarDespacho;
    }    

    public boolean esUsuarioPropietario() {
        return usuarioPropietario;
    }

    public void setUsuarioPropietario(boolean usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }

    public String getListaConductores() {
        return listaConductores;
    }

    public void setListaConductores(String listaConductores) {
        this.listaConductores = listaConductores;
    }        
}
