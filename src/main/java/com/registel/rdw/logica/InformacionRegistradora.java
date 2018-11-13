/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class InformacionRegistradora {
    
    private int id;
    private int idVehiculo;
    private int idRuta;
    private int idUsuario;
    private int idBase;
    private int idConductor;
    private double porcentajeRuta;
    private Date fechaIngreso;
    private Date horaIngreso;
    private int numeroVuelta;
    private int numeroVueltaAnterior;
    private int numeroLlegada;
    private int diferenciaNumero;
    private int entradas;
    private int diferenciaEntrada;
    private int salidas;
    private int diferenciaSalida;
    private int totalDia;
   
    private boolean existeRuta;
    private boolean existePuntoControl;
    private boolean existeAlarma;
    private boolean existeRecorrido;
    private int perimetro;    
    private int idLiquidacion;
    private String nombreRuta;
    private String nombreConductor;
    
    private String placa;
    private String numeroInterno;
    private int idEmpresa;
    
    private int idBaseSalida;
    private String nombreBS;
    private String nombreBL;
    private Date fechaSalidaBS;
    private Date horaSalidaBS;
    private int numeracionBS;
    private int entradasBS;
    private int salidasBS;
    private String firmware;
    private int versionPuntos;
    private int estadoCreacion;
    private int historial;
    private int estado;
    
    // Identificadores GPS
    private long idBaseIniGPS;
    private long idBaseFinGPS;
    
    private Base base_salida;
    private Conductor conductor;
    private Base base_llegada;
    private Ruta ruta;
    private Movil vehiculo;

    public Base getBase_salida() {
        return base_salida;
    }

    public void setBase_salida(Base base_salida) {
        this.base_salida = base_salida;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Base getBase_llegada() {
        return base_llegada;
    }

    public void setBase_llegada(Base base_llegada) {
        this.base_llegada = base_llegada;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Movil getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Movil vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    
    private ArrayList<AlarmaInfoReg> lstalarma;
    private ArrayList<PuntoControl> lstptocontrol;
    private ArrayList<ConteoPerimetro> lstcp;
    
    public InformacionRegistradora () {
        lstalarma = new ArrayList<AlarmaInfoReg>();
        lstptocontrol = new ArrayList<PuntoControl>();
        lstcp = new ArrayList<ConteoPerimetro>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public double getPorcentajeRuta() {
        return porcentajeRuta;
    }

    public void setPorcentajeRuta(double porcentajeRuta) {
        this.porcentajeRuta = porcentajeRuta;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Date horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }

    public int getNumeroVueltaAnterior() {
        return numeroVueltaAnterior;
    }

    public void setNumeroVueltaAnterior(int numeroVueltaAnterior) {
        this.numeroVueltaAnterior = numeroVueltaAnterior;
    }

    public int getNumeroLlegada() {
        return numeroLlegada;
    }

    public void setNumeroLlegada(int numeroLlegada) {
        this.numeroLlegada = numeroLlegada;
    }

    public int getDiferenciaNumero() {
        return diferenciaNumero;
    }

    public void setDiferenciaNumero(int diferenciaNumero) {
        this.diferenciaNumero = diferenciaNumero;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getDiferenciaEntrada() {
        return diferenciaEntrada;
    }

    public void setDiferenciaEntrada(int diferenciaEntrada) {
        this.diferenciaEntrada = diferenciaEntrada;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getDiferenciaSalida() {
        return diferenciaSalida;
    }

    public void setDiferenciaSalida(int diferenciaSalida) {
        this.diferenciaSalida = diferenciaSalida;
    }

    public int getTotalDia() {
        return totalDia;
    }

    public void setTotalDia(int totalDia) {
        this.totalDia = totalDia;
    }

    public int getIdBaseSalida() {
        return idBaseSalida;
    }

    public void setIdBaseSalida(int idBaseSalida) {
        this.idBaseSalida = idBaseSalida;
    }

    public Date getFechaSalidaBS() {
        return fechaSalidaBS;
    }

    public void setFechaSalidaBS(Date fechaSalidaBS) {
        this.fechaSalidaBS = fechaSalidaBS;
    }

    public Date getHoraSalidaBS() {
        return horaSalidaBS;
    }

    public void setHoraSalidaBS(Date horaSalidaBS) {
        this.horaSalidaBS = horaSalidaBS;
    }

    public int getNumeracionBS() {
        return numeracionBS;
    }

    public void setNumeracionBS(int numeracionBS) {
        this.numeracionBS = numeracionBS;
    }

    public int getEntradasBS() {
        return entradasBS;
    }

    public void setEntradasBS(int entradasBS) {
        this.entradasBS = entradasBS;
    }

    public int getSalidasBS() {
        return salidasBS;
    }

    public void setSalidasBS(int salidasBS) {
        this.salidasBS = salidasBS;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public int getVersionPuntos() {
        return versionPuntos;
    }

    public void setVersionPuntos(int versionPuntos) {
        this.versionPuntos = versionPuntos;
    }

    public int getEstadoCreacion() {
        return estadoCreacion;
    }

    public void setEstadoCreacion(int estadoCreacion) {
        this.estadoCreacion = estadoCreacion;
    }

    public int getHistorial() {
        return historial;
    }

    public void setHistorial(int historial) {
        this.historial = historial;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isExisteRuta() {
        return existeRuta;
    }

    public void setExisteRuta(boolean existeRuta) {
        this.existeRuta = existeRuta;
    }

    public boolean isExistePuntoControl() {
        return existePuntoControl;
    }

    public void setExistePuntoControl(boolean existePuntoControl) {
        this.existePuntoControl = existePuntoControl;
    }

    public boolean isExisteAlarma() {
        return existeAlarma;
    }

    public void setExisteAlarma(boolean existeAlarma) {
        this.existeAlarma = existeAlarma;
    }

    public boolean isExisteRecorrido() {
        return existeRecorrido;
    }

    public void setExisteRecorrido(boolean existeRecorrido) {
        this.existeRecorrido = existeRecorrido;
    }

    public int getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(int perimetro) {
        this.perimetro = perimetro;
    }

    public int getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(int idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
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

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreBS() {
        return nombreBS;
    }

    public void setNombreBS(String nombreBS) {
        this.nombreBS = nombreBS;
    }

    public String getNombreBL() {
        return nombreBL;
    }

    public void setNombreBL(String nombreBL) {
        this.nombreBL = nombreBL;
    }
    
    public ArrayList<AlarmaInfoReg> getLstalarma() {
        return this.lstalarma;
    }
    
    public void setAlarma(AlarmaInfoReg a) {
        this.lstalarma.add(a);
    }
    
    public void setAlarmas(ArrayList<AlarmaInfoReg> lst) {
        this.lstalarma = lst;
    }
    
    public ArrayList<PuntoControl> getLstptocontrol() {
        return this.lstptocontrol;
    }
    
    public void setPuntoControl(PuntoControl pc) {
        this.lstptocontrol.add(pc);
    }
    
    public void setPuntosControl(ArrayList<PuntoControl> lst) {
        this.lstptocontrol = lst;
    }
    
    public ArrayList<ConteoPerimetro> getLstcp() {
        return this.lstcp;
    }
    
    public void setConteoPerimetro (ConteoPerimetro cp) {
        this.lstcp.add(cp);
    }
    
    public void setConteosPerimetro (ArrayList<ConteoPerimetro> lst) {
        this.lstcp = lst;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public long getIdBaseIniGPS() {
        return idBaseIniGPS;
    }

    public void setIdBaseIniGPS(long idBaseIniGPS) {
        this.idBaseIniGPS = idBaseIniGPS;
    }

    public long getIdBaseFinGPS() {
        return idBaseFinGPS;
    }

    public void setIdBaseFinGPS(long idBaseFinGPS) {
        this.idBaseFinGPS = idBaseFinGPS;
    }
    
    public String toString() {
        String str = "[ " +
                this.getNumeroVuelta()      +" "+
                this.getFechaSalidaBS()     +" "+ 
                this.getHoraSalidaBS()      +" "+ 
                this.getFechaIngreso()      +" "+ 
                this.getHoraIngreso()       +" "+ 
                this.getIdVehiculo()        +" "+ 
                this.getIdBaseSalida()      +" "+ 
                this.getIdBase()            +" "+ 
                this.getNumeracionBS()      +" "+ 
                this.getNumeroLlegada()     +" "+ 
                this.getDiferenciaNumero()  +" "+ 
                this.getEntradas()          +" "+
                this.getEntradasBS()        +" "+
                this.getDiferenciaEntrada() +" "+
                this.getSalidas()           +" "+
                this.getSalidasBS()         +" "+
                this.getDiferenciaSalida()  +" "+
                this.getTotalDia() + " ]";
                
        return str;
    }
}
