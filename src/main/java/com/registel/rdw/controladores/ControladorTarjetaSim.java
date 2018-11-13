/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.GpsInventarioBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.datos.RelationShipSimGpsBD;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.RelationShipSimGps;
import com.registel.rdw.logica.TarjetaSim;


/*COMENTARIO*/
public class ControladorTarjetaSim extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String nada = request.getParameter("r");       
        HttpSession session = request.getSession(false);
        String url = "/";
        
        if (session == null || 
            session.getAttribute("login") == null || 
            session.getAttribute("select") == null || 
            Expire.check(session.getCreationTime())) {
            url = "/index.jsp";
        } 
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {        
        String requestURI = request.getRequestURI();        
        String url = "/";
        if (requestURI.endsWith("/consultaTarjetasSim")){
            url = tarjetas(request, response);
        }
        if (requestURI.endsWith("/consultaTarjetaSim")){
            url = unaTarjeta(request, response);
        }
        if (requestURI.endsWith("/guardarTarjetaSim")){
            url = guardarTarjeta(request, response);
        }        
        if (requestURI.endsWith("/consultaTarjetaSimNoAsociada")){
            url = consultaSimNoGps(request, response);
        }
        if (requestURI.endsWith("/asociarTarjetaSim")){
            url = asociarTarjetaSimAGps(request, response);
        }
        if (requestURI.endsWith("/simRepetida")) {
            url = simRepetida(request, response);
        }
        if (requestURI.endsWith("/consultaVehiculoTieneGps")) {
            //url = consultaVehiculoConGps(request, response);
        }  
        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       public String tarjetas (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        TarjetaSim h = new TarjetaSim();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = TarjetaSimBD.selectOwnerAll(h);                   
                session.setAttribute("invall", retorno);                
                url = "/app/tarjetaSim/tarjetas.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }  
       public String unaTarjeta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        TarjetaSim h = new TarjetaSim();
        h.setCodigo(id);
        HttpSession session = request.getSession();
        TarjetaSim retorno = TarjetaSimBD.searchSimCardByCode(h);
        session.setAttribute("inv", retorno);
        url = "/app/tarjetaSim/tarjeta.jsp";
        return url;
    }  
       
        public String consultaSimNoGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        TarjetaSim ts = new TarjetaSim();
        ts.setAsociada(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList<TarjetaSim> retorno = TarjetaSimBD.searchSimNotMatchWithOutGps(ts);        
        request.setAttribute("invsimnogps", retorno);
        url = "/app/tarjetaSim/simFreeGps.jsp";
        return url;
    }  
       
        /*METODO QUE GUARDA TARJETA SIM*/
    public String guardarTarjeta (HttpServletRequest request,
            HttpServletResponse response) {        
        String id_gps =  request.getParameter("id_gps");                
        String num_celular =  request.getParameter("num_cel");                
        String fk_operador =  request.getParameter("id_oper");        
        String aplicar =  request.getParameter("aplicar");                
        String url="";
        int idSim =0;
        HttpSession session = request.getSession();        
        TarjetaSim h= new TarjetaSim();
        RelationShipSimGps r= new RelationShipSimGps();
        
         try {             
              h.setFk_operador(Integer.parseInt(fk_operador));              
              h.setCodigo("0");              
              h.setNum_cel(num_celular);
              
              if (Integer.parseInt(aplicar) > 0) {
                 idSim = TarjetaSimBD.insertOneSimCardReturnId(h);                 
                 r.setFk_sim(idSim);
                 r.setFk_gps(id_gps);
                 int r1 = RelationShipSimGpsBD.insertOneRelationShipSimGps(r);
                  if (r1 > 0) {
                     r.setEstado(1);
                     /*SE ACTUALIZAN LOS CAMPOS ASOCIADO EN TARJETA Y GPS*/
                     int r2 = TarjetaSimBD.updateSimCardRecordMatch(r);
                     int r3 = GpsInventarioBD.updateFiledAsociateSim(r);                    
                  }
                  
                  GpsRegistrado gps = GpsInventarioBD.searchGPSById(r);
                  TarjetaSim sim = TarjetaSimBD.searchSimCardById(r);                  
                  request.setAttribute("g", gps);
                  request.setAttribute("s", sim);
                  request.setAttribute("a", aplicar);
              }
              else{
                  idSim = TarjetaSimBD.insertOneSimCard(h);
                  request.setAttribute("a", aplicar);
              }              
              
         } catch (Exception e) {
             System.err.println(e.getMessage());
              request.setAttribute("a", -1);                           
         } 
        url = "/app/tarjetaSim/guardar.jsp";        
        return url;
    }
    
    
    public String asociarTarjetaSimAGps (HttpServletRequest request,
            HttpServletResponse response) {
        String id_sim =  request.getParameter("id_sim");
        String id_gps =  request.getParameter("id_gps");
        HttpSession session = request.getSession();
        String url="";
        TarjetaSim ts=null;
        RelationShipSimGps r=null;
        try {
             ts = new TarjetaSim();
             ts.setId(Integer.parseInt(id_sim));
             ts.setAsociada(0);
            /*BUSCAMOS LA TARJETA SIM LIBRE*/
            TarjetaSim tarejtaSimEncontradaLibre = TarjetaSimBD.searchOneSimNotMatchWithGps(ts);
                         
            r = new RelationShipSimGps();
            r.setFk_gps(id_gps);          
            r.setFk_sim(Integer.parseInt(id_sim));
            r.setEstado(1);
                                    
            /*ENCUENTRA LA RELACION ENTRE LA SIM CARD Y EL GPS*/
            RelationShipSimGps relacionOffencontrada = RelationShipSimGpsBD.searchRelationShipSimGpsOffByFkGpsFkSim(r);
        
            if (relacionOffencontrada != null) {
                 /*REACTIVA LA RELACION QUE HAYA TENIDO ANTES UN GPS CON UNA SIM CARD*/
                    if ((relacionOffencontrada.getFk_gps().equals(id_gps)) && (relacionOffencontrada.getFk_sim() == tarejtaSimEncontradaLibre.getId())){
                        relacionOffencontrada.setEstado(1);
                        //ACTUALIZA EL CAMPO asociada EN LA TARJETA SIM
                        int retorno1 = TarjetaSimBD.updateSimCardRecordMatch(relacionOffencontrada);
                        //ACTUALIZA EL CAMPO gps_asociada EN EL GPS
                        int retorno2 = GpsInventarioBD.updateFiledAsociateSim(relacionOffencontrada);
                        //ACTUALIZA EL CAMPO estado EN LA RELACION GPS - SIM
                        int retorno3 = RelationShipSimGpsBD.updateStateRelationShipOfFkSimFkGps(relacionOffencontrada);
                        if ((retorno1>0) && (retorno2>0) && (retorno3>0)) {
                            RelationShipSimGps RelacionactuActualizada = RelationShipSimGpsBD.searchRelationShipSimGpsById(relacionOffencontrada);
                            TarjetaSim tarjeta = TarjetaSimBD.searchSimCardById(r);
                            GpsRegistrado gps = GpsInventarioBD.searchGPSById(r);
                            if (RelacionactuActualizada != null) {
                                request.setAttribute("tjt", tarjeta);
                                request.setAttribute("gps", gps);
                                url = "/app/tarjetaSim/asociar.jsp";        
                            }
                        }     
                    }else{//ESTABLECE UNA NUEVA RELACION CON UN GPS EXISTENTE Y UNA SIM CARD NUEVA                

                            //ACTUALIZA EL CMAMPO asociada EN LA TARJETA SIM
                            int retorno1 = TarjetaSimBD.updateSimCardRecordMatch(r);
                            //ACTUALIZA EL CAMPO gps_asociada EN EL GPS
                            int retorno2 = GpsInventarioBD.updateFiledAsociateSim(r);
                            //INSERTA UNA NUEVA RELACION ENTRE EL GPS EXISTENTE Y UNA SIM DIFERENTE A QUE TUVO
                            int retorno3 = RelationShipSimGpsBD.insertOneRelationShipSimGpsReturnId(r);
                            r.setId(retorno3);
                            if ((retorno1>0) && (retorno2>0) && (retorno3>0)) {
                                relacionOffencontrada.setId(retorno3);
                                RelationShipSimGps RelacionactuActualizada = RelationShipSimGpsBD.searchRelationShipSimGpsById(r);
                                TarjetaSim tarjeta = TarjetaSimBD.searchSimCardById(r);
                                GpsRegistrado gps = GpsInventarioBD.searchGPSById(r);
                                if (RelacionactuActualizada != null) {
                                    request.setAttribute("tjt", tarjeta);
                                    request.setAttribute("gps", gps);
                                    url = "/app/tarjetaSim/asociar.jsp";        
                                }
                            }
                    }                       
            }else{
                //ACTUALIZA EL CMAMPO asociada EN LA TARJETA SIM
                    int retorno1 = TarjetaSimBD.updateSimCardRecordMatch(r);
                    //ACTUALIZA EL CAMPO gps_asociada EN EL GPS
                    int retorno2 = GpsInventarioBD.updateFiledAsociateSim(r);
                    //INSERTA UNA NUEVA RELACION ENTRE EL GPS EXISTENTE Y UNA SIM DIFERENTE A QUE TUVO
                    int retorno3 = RelationShipSimGpsBD.insertOneRelationShipSimGpsReturnId(r);
                    r.setId(retorno3);
                    if ((retorno1>0) && (retorno2>0) && (retorno3>0)) {
                        relacionOffencontrada.setId(retorno3);
                        RelationShipSimGps RelacionactuActualizada = RelationShipSimGpsBD.searchRelationShipSimGpsById(r);
                        TarjetaSim tarjeta = TarjetaSimBD.searchSimCardById(r);
                        GpsRegistrado gps = GpsInventarioBD.searchGPSById(r);
                        if (RelacionactuActualizada != null) {
                            request.setAttribute("tjt", tarjeta);
                            request.setAttribute("gps", gps);
                            url = "/app/tarjetaSim/asociar.jsp";        
                        }
                    }
            }
           
        
        } catch (Exception e) {
            System.err.println(e.getMessage());
            request.setAttribute("tjt", null);
            url = "/app/tarjetaSim/asociar.jsp";
        }        
        
        
        return url;
    }
    
    
     public String simRepetida (HttpServletRequest request,
            HttpServletResponse response){
        
        String numeroCelular =  request.getParameter("numero");
        String url="";                   
        HttpSession session = request.getSession();
        
        TarjetaSim a = new TarjetaSim();
        if (!numeroCelular.equals("")) {
            a.setNum_cel(numeroCelular.trim());
        }        
        int repetido = TarjetaSimBD.simWithNumCel(a);
        TarjetaSim sim0 = TarjetaSimBD.searchSimCardByCelular(a);
        session.setAttribute("s0", repetido);
        session.setAttribute("s1", sim0);
        url = "/app/tarjetaSim/asociado.jsp";        
        return url;
    }
}
