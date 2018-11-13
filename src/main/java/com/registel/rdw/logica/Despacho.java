/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import com.registel.rdw.datos.GrupoMovilBD;
import com.registel.rdw.datos.PuntoRutaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.utils.Restriction;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class Despacho {
    
    int id;
    int idRuta;
    int idGrupoMoviles;
    String nombreRuta;
    String tipoDia;
    Time horaInicio;
    Time horaFin;
    int cantidadVueltas;
    int intervaloValle;
    int intervaloPico;
    String horaPico;
    String horaPicoFmt;
    String tiempoReferencia;
    String tiempoHolgura;
    String tiempoLlegadaValle;
    String tiempoLlegadaPico;
    int tiempoSalida;
    int tiempoSalidaPico;
    int rotarVehiculo;
    int programarRuta;
    String nombreDespacho;
    String idPuntosRuta;
    String puntosRuta;
    String grupoMoviles;
    boolean ordenInicial = true;
    String tipoProgramacionRuta;
    int horasTrabajo;
    int limiteConservacion;
    Date fecha;
    
    String puntoRetorno;
    Time horaInicioRetorno;
    int intervaloRetorno;
    int tiempoSalidaRetorno;
    int tiempoAjusteRetorno;
    String grupoMovilesRetorno;
    int cantidadMovilesRetorno;
    
    boolean movilesEnRetorno = false;
    boolean iniciaEnPuntoRetorno = false;
    boolean despachoManual = false;
    boolean aleatoriedadPorReiteracionRuta = false;
    int numeroVuelta = 1;
    int numeroRotacion = 1;
        
    ArrayList<String[]> listaPuntosRuta;
    ArrayList<String> listaGrupoMoviles;
    ArrayList<String> listaGrupoMovilesRetorno;
    ArrayList<String> listaHoraPico;
    
    ArrayList<PuntoRutaDespacho> listaPuntosRuta_all;
    ArrayList<GrupoMovilDespacho> listaGrupoMoviles_all;
    
    ArrayList<Movil> listaGrupoMoviles_movil;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdGrupoMoviles() {
        return idGrupoMoviles;
    }

    public void setIdGrupoMoviles(int idGrupoMoviles) {
        this.idGrupoMoviles = idGrupoMoviles;
    }
    
    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    
    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public int getCantidadVueltas() {
        return cantidadVueltas;
    }

    public void setCantidadVueltas(int cantidadVueltas) {
        this.cantidadVueltas = cantidadVueltas;
    }

    public String getTiempoReferencia() {
        return tiempoReferencia;
    }

    public void setTiempoReferencia(String tiempoReferencia) {
        this.tiempoReferencia = tiempoReferencia;
    }
    
    public String getTiempoHolgura() {
        return tiempoHolgura;
    }

    public void setTiempoHolgura(String tiempoHolgura) {
        this.tiempoHolgura = tiempoHolgura;
    }
    
    public int getIntervaloValle() {
        return intervaloValle;
    }

    public void setIntervaloValle(int intervaloValle) {
        this.intervaloValle = intervaloValle;
    }

    public int getIntervaloPico() {
        return intervaloPico;
    }

    public void setIntervaloPico(int intervaloPico) {
        this.intervaloPico = intervaloPico;        
    }

    public String getHoraPico() {
        return horaPico;        
    }

    public void setHoraPico(String horaPico) {
        this.horaPico = horaPico;
        setHoraPicoFmt(horaPico);
    }

    public String getHoraPicoFmt() {
        return horaPicoFmt;
    }

    // Convierte formato de horas pico de la forma hp1,hp2,..,hpn
    // al formato hp1 hp2 .. hpn.
    // Establece valor obtenido en variable global horaPicoFmt.
    public void setHoraPicoFmt(String horaPico) {
        //this.horaPicoFmt = horaPicoFmt;
        
        String str = null;
        
        if (horaPico != null && horaPico != "") {
            
            String array[] = horaPico.split(","); str = "";
            for (int i = 0; i < array.length; i++) {
                str += (str == "") ? array[i] : "  " + array[i];
            }
        } this.horaPicoFmt = str;
    }
    
    public String getTiempoLlegadaValle() {
        return tiempoLlegadaValle;
    }

    public void setTiempoLlegadaValle(String tiempoLlegadaValle) {
        this.tiempoLlegadaValle = tiempoLlegadaValle;
    }

    public String getTiempoLlegadaPico() {
        return tiempoLlegadaPico;
    }

    public void setTiempoLlegadaPico(String tiempoLlegadaPico) {
        this.tiempoLlegadaPico = tiempoLlegadaPico;
    }

    public int getRotarVehiculo() {
        return rotarVehiculo;
    }

    public void setRotarVehiculo(int rotarVehiculo) {
        this.rotarVehiculo = rotarVehiculo;
    }

    public int getProgramarRuta() {
        return programarRuta;
    }

    public void setProgramarRuta(int programarRuta) {
        this.programarRuta = programarRuta;
    }
    
    public String getNombreDespacho() {
        return nombreDespacho;
    }

    public void setNombreDespacho(String nombreDespacho) {
        this.nombreDespacho = nombreDespacho;
    }

    public String getIdPuntosRuta() {
        return idPuntosRuta;
    }

    public void setIdPuntosRuta(String idPuntosRuta) {
        this.idPuntosRuta = idPuntosRuta;
    }
    
    public String getPuntosRuta() {
        return puntosRuta;
    }

    public void setPuntosRuta(String puntosRuta) {
        this.puntosRuta = puntosRuta;
    }

    public String getGrupoMoviles() {
        return grupoMoviles;
    }

    public void setGrupoMoviles(String grupoMoviles) {
        this.grupoMoviles = grupoMoviles;
    }

    public ArrayList<String[]> getListaPuntosRuta() {
        return listaPuntosRuta;
    }
    
    // Transfiere valores de arreglos de cadenas de texto con parametros de puntos
    // a una lista enlazada de arreglos (listaPuntosRuta).
    public void setListaPuntosRuta(String listaPuntosRuta[],
                                   String listaTiempoHolgura[],
                                   String listaTiempoValle[],
                                   String listaTiempoPico[]) {
        
        //this.listaPuntosRuta = new ArrayList<String>(Arrays.asList(listaPuntosRuta));

        this.listaPuntosRuta = new ArrayList<String[]>();
        for (int i = 0; i < listaPuntosRuta.length; i++) {
            String elemt[] = {
                listaPuntosRuta[i],
                listaTiempoHolgura[i],
                listaTiempoValle[i],
                listaTiempoPico[i]
            };
            this.listaPuntosRuta.add(elemt);
        }
    }
    
    public ArrayList<PuntoRutaDespacho> getListaPuntosRuta_all() {
        return listaPuntosRuta_all;
    }
    
    // Crea lista total de puntos de una ruta junto con
    // informacion de despacho.
    // Asocia informacion de despacho de cada punto a puntos de ruta
    // a traves de su identificador y diferencia de tiempo minima.
    public void setListaPuntosRuta_all(int idRuta,
                                       String listaIdPuntosRutaDph[],
                                       String listaPuntosRutaDph[],
                                       String listaTiempoReferencia[],
                                       String listaTiempoHolgura[],
                                       String listaTiempoValle[],
                                       String listaTiempoPico[]) {
        
        ArrayList<PuntoRuta> listaPuntosRuta_origen = PuntoRutaBD.selectPuntoRuta_despacho(idRuta);
        this.listaPuntosRuta_all = new ArrayList<PuntoRutaDespacho>();
        
        // Puntos de despacho
        for (int i = 0; i < listaIdPuntosRutaDph.length; i++) {            
            String punto_dph   = listaIdPuntosRutaDph[i];
            int tiempo_dph     = Restriction.getNumber(listaTiempoReferencia[i]);
            int min_diferencia = Integer.MAX_VALUE; 
            int idx_pr  = -1, 
                idx_dph = -1;
            
            // Puntos de ruta
            for (int j = 0; j < listaPuntosRuta_origen.size(); j++) {
                PuntoRuta pr    = listaPuntosRuta_origen.get(j);
                String punto_pr = "" + pr.getIdPunto();
                int tiempo_pr   = pr.getPromedioMinutos();
                
                int tiempo_dif  = Math.abs(tiempo_dph - tiempo_pr);
                if (punto_dph.compareTo(punto_pr) == 0 &&
                    tiempo_dif < min_diferencia &&
                    !pr.puntoVisto()) {
                    min_diferencia = tiempo_dif;
                    idx_dph = i;
                    idx_pr  = j;                    
                    if (min_diferencia == 0) break;
                }
            }
            
            if (idx_pr >= 0) {
                
                // Si punto ruta esta en despacho,
                // se inserta en estructura con valores de tiempo
                // y se establece punto_en_despacho
                
                // Datos                            
                int tiempoReferencia = Restriction.getNumber(listaTiempoReferencia[idx_dph]);
                int tiempoHolgura    = Restriction.getNumber(listaTiempoHolgura[idx_dph]);
                int tiempoValle      = Restriction.getNumber(listaTiempoValle[idx_dph]);
                int tiempoPico       = Restriction.getNumber(listaTiempoPico[idx_dph]);
                String puntoRutaDph  = listaPuntosRutaDph[idx_dph];
                tiempoReferencia     = (tiempoReferencia < 0) ? 0 : tiempoReferencia;
                tiempoHolgura = (tiempoHolgura < 0) ? 0 : tiempoHolgura;
                tiempoValle   = (tiempoValle < 0) ? 0 : tiempoValle;
                tiempoPico    = (tiempoPico < 0) ? 0 : tiempoPico;

                PuntoRuta pr  = listaPuntosRuta_origen.get(idx_pr);
                pr.setEtiquetaPunto(puntoRutaDph);
                pr.setPromedioMinutos(tiempoReferencia);
                pr.setHolguraMinutos(tiempoHolgura);
                pr.setTiempoValle(tiempoValle);
                pr.setTiempoPico(tiempoPico);
                pr.setPuntoVisto(true);
            }
        }
        
        for (PuntoRuta pr : listaPuntosRuta_origen) {
            PuntoRutaDespacho pr_dph = new PuntoRutaDespacho();
            
            pr_dph.setNombrePunto(pr.getNombrePunto());
            pr_dph.setId(pr.getId());
            pr_dph.setIdPunto(pr.getIdPunto());
            pr_dph.setCodigoPunto(pr.getCodigoPunto());
            pr_dph.setEtiquetaPunto(pr.getEtiquetaPunto());
            pr_dph.setIdRuta(idRuta);
            pr_dph.setPromedioMinutos(pr.getPromedioMinutos());
            pr_dph.setTiempoHolgura(pr.getHolguraMinutos());
            pr_dph.setTiempoValle(pr.getTiempoValle());
            pr_dph.setTiempoPico(pr.getTiempoPico());
            
            if (pr.puntoVisto()) {
                pr_dph.setPuntoEnDespacho(true);
            }
            
            listaPuntosRuta_all.add(pr_dph);
        }
    }
    
    public ArrayList<String> getListaGrupoMoviles() {
        return listaGrupoMoviles;
    }
    
    public void setListaGrupoMoviles(ArrayList<String> listaGrupoMoviles) {
        this.listaGrupoMoviles = listaGrupoMoviles;
    }
    
    public ArrayList<String> getListaGrupoMovilesRetorno() {
        return listaGrupoMovilesRetorno;
    }
    
    public void setListaGrupoMovilesRetorno(ArrayList<String> listaGrupoMovilesRetorno) {
        this.listaGrupoMovilesRetorno = listaGrupoMovilesRetorno;
    }

    public void setListaGrupoMoviles(String listaGrupoMoviles[]) {
        this.listaGrupoMoviles = new ArrayList<String>(Arrays.asList(listaGrupoMoviles));
    }
    
    public void setListaGrupoMovilesRetorno(String listaGrupoMovilesRetorno[]) {
        this.listaGrupoMovilesRetorno = new ArrayList<String>(Arrays.asList(listaGrupoMovilesRetorno));
    }
    
    public ArrayList<GrupoMovilDespacho> getListaGrupoMoviles_all() {
        return listaGrupoMoviles_all;
    }
    
    // Conforma lista de todos los moviles registrados por grupo,
    // y marca seleccionado aquellos que coincidan con el id_grupo_movil
    // pasado como parametro.    
    public void setListaGrupoMoviles_all(
            int idGrupoMovil,
            ArrayList<String> lstMovilDph,
            ArrayList<String> lstMovilRetornoDph) {
        
        SelectBD select = new SelectBD(SelectC.LST_GRUPOMOVILES_DESPACHO.ordinal());
        ArrayList<GrupoMovilDespacho> lstGrupoMovil = select.getLstgrupomoviles_despacho();
                
        listaGrupoMoviles_all = new ArrayList<GrupoMovilDespacho>();
        
        // Moviles que inician en punto retorno
        for (int i = 0; i < lstMovilRetornoDph.size(); i++) {
            String movil_dph = lstMovilRetornoDph.get(i);
            for (int j = 0; j < lstGrupoMovil.size(); j++) {
                GrupoMovilDespacho gm = lstGrupoMovil.get(j);
                if (gm.getIdGrupoMovil() == idGrupoMovil &&
                    gm.getPlaca().compareTo(movil_dph) == 0) {
                    gm.setMovilEnDespacho(true);
                    gm.setIniciaEnPuntoRetorno(true);
                    listaGrupoMoviles_all.add(gm);
                    break;
                }
            }
        }
        
        // Moviles que inician en base corriente
        for (int i = 0; i < lstMovilDph.size(); i++) {
            String movil_dph = lstMovilDph.get(i);
            for (int j = 0; j < lstGrupoMovil.size(); j++) {
                GrupoMovilDespacho gm = lstGrupoMovil.get(j);
                if (gm.getIdGrupoMovil() == idGrupoMovil &&
                    gm.getPlaca().compareTo(movil_dph) == 0) {
                    gm.setMovilEnDespacho(true);
                    listaGrupoMoviles_all.add(gm);
                    break;
                }
            }
        }
        
        for (int i = 0; i < lstGrupoMovil.size(); i++) {
            GrupoMovilDespacho gm = lstGrupoMovil.get(i);
            if (!gm.isMovilEnDespacho()) {
                listaGrupoMoviles_all.add(gm);
            }
        }
    }  

    public ArrayList<Movil> getListaGrupoMoviles_movil() {
        return listaGrupoMoviles_movil;
    }

    public void setListaGrupoMoviles_movil(ArrayList<Movil> listaGrupoMoviles_movil) {
        this.listaGrupoMoviles_movil = listaGrupoMoviles_movil;
    }
    
    public ArrayList<String> getListaHoraPico() {
        return listaHoraPico;
    }

    public void setListaHoraPico(String listaHoraPico[]) {
        this.listaHoraPico = new ArrayList<String>(Arrays.asList(listaHoraPico));
    }          

    public int getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(int tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    public int getTiempoSalidaPico() {
        return tiempoSalidaPico;
    }

    public void setTiempoSalidaPico(int tiempoSalidaPico) {
        this.tiempoSalidaPico = tiempoSalidaPico;
    }
    
    public boolean ordenInicial() {
        return ordenInicial;
    }

    public void ordenInicial(boolean ordenInicial) {
        this.ordenInicial = ordenInicial;
    }    

    public String getTipoProgramacionRuta() {
        return tipoProgramacionRuta;
    }

    public void setTipoProgramacionRuta(String progRuta) {
        this.tipoProgramacionRuta = progRuta;
    }

    public int getHorasTrabajo() {
        return horasTrabajo;
    }

    public void setHorasTrabajo(int horasTrabajo) {
        this.horasTrabajo = horasTrabajo;
    }
    
    public String getPuntoRetorno() {
        return puntoRetorno;
    }

    public void setPuntoRetorno(String puntoRetorno) {
        this.puntoRetorno = puntoRetorno;
    }

    public Time getHoraInicioRetorno() {
        return horaInicioRetorno;
    }

    public void setHoraInicioRetorno(Time horaInicioRetorno) {
        this.horaInicioRetorno = horaInicioRetorno;
    }

    public int getIntervaloRetorno() {
        return intervaloRetorno;
    }

    public void setIntervaloRetorno(int intervaloRetorno) {
        this.intervaloRetorno = intervaloRetorno;
    }

    public int getTiempoSalidaRetorno() {
        return tiempoSalidaRetorno;
    }

    public void setTiempoSalidaRetorno(int tiempoSalidaRetorno) {
        this.tiempoSalidaRetorno = tiempoSalidaRetorno;
    }

    public int getTiempoAjusteRetorno() {
        return tiempoAjusteRetorno;
    }

    public void setTiempoAjusteRetorno(int tiempoAjusteRetorno) {
        this.tiempoAjusteRetorno = tiempoAjusteRetorno;
    }
    
    public String getGrupoMovilesRetorno() {
        return grupoMovilesRetorno;
    }

    public void setGrupoMovilesRetorno(String grupoMovilesRetorno) {
        this.grupoMovilesRetorno = grupoMovilesRetorno;
    }   

    public boolean movilesEnRetorno() {
        return movilesEnRetorno;
    }

    public void movilesEnRetorno(boolean movilesEnRetorno) {
        this.movilesEnRetorno = movilesEnRetorno;
    }

    public int getLimiteConservacion() {
        return limiteConservacion;
    }

    public void setLimiteConservacion(int limiteConservacion) {
        this.limiteConservacion = limiteConservacion;
    }    

    public int getCantidadMovilesRetorno() {
        return cantidadMovilesRetorno;
    }

    public void setCantidadMovilesRetorno(int cantidadMovilesRetorno) {
        this.cantidadMovilesRetorno = cantidadMovilesRetorno;
    }
    
    public void setIniciaEnPuntoRetorno(boolean iniciaPuntoRetorno) {
        this.iniciaEnPuntoRetorno = iniciaPuntoRetorno;
    }
    
    public boolean iniciaEnPuntoRetorno() {
        return iniciaEnPuntoRetorno;
    }

    public int getNumeroVuelta() {
        return numeroVuelta;
    }

    public void setNumeroVuelta(int numeroVuelta) {
        this.numeroVuelta = numeroVuelta;
    }        

    public boolean despachoManual() {
        return despachoManual;
    }

    public void setDespachoManual(boolean despachoManual) {
        this.despachoManual = despachoManual;
    }    

    public boolean aleatoriedadPorReiteracionRuta() {
        return aleatoriedadPorReiteracionRuta;
    }

    public void setAleatoriedadPorReiteracionRuta(boolean aleatoriedad) {
        this.aleatoriedadPorReiteracionRuta = aleatoriedad;
    }        

    public int getNumeroRotacion() {
        return numeroRotacion;
    }

    public void setNumeroRotacion(int numeroRotacion) {
        this.numeroRotacion = numeroRotacion;
    }        

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }        
}
