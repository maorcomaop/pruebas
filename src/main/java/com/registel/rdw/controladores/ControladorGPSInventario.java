/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import com.registel.rdw.datos.GpsInventarioBD;
import com.registel.rdw.datos.RelationShipHardwareGpsCarBD;
import com.registel.rdw.datos.RelationShipSimGpsBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import com.registel.rdw.logica.RelationShipSimGps;
import com.registel.rdw.logica.TarjetaSim;



/*COMENTARIO*/
public class ControladorGPSInventario extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultaInventarioGPS")){
            url = gpss(request, response);
        }
        if (requestURI.endsWith("/consultaInventario")){
            url = oneGps(request, response);
        }
        if (requestURI.endsWith("/guardarGPSInventario")){
            url = guardarGpsInventario(request, response);
        }
        if (requestURI.endsWith("/verGPSInventario")){
            url = verGpsInventario(request, response);
        }   
        if (requestURI.endsWith("/guardarGPSInventario")){
            url = guardarGpsInventario(request, response);
        }   
        
         if (requestURI.endsWith("/eliminarInventario")){
            url = eliminarGps(request, response);
        }
        if (requestURI.endsWith("/consultaGpsNoSim")){
            url = consultaGpsNoSim(request, response);
        }
        if (requestURI.endsWith("/consultaGpsSinAsociar")){
            url = consultaGpsSinVh(request, response);
        }   
        
        /*if (requestURI.endsWith("/reasignarGps")){
            url = reAsignarGps(request, response);
        }*/
        if (requestURI.endsWith("/asociarGps")){
            url = asociarGps(request, response);
        }
        if (requestURI.endsWith("/gpsRepetido")) {
            url = gpsRepetido(request, response);
        }        
        if (requestURI.endsWith("/consultaVehiculoTieneGps")) {
            url = consultaVehiculoConGps(request, response);
        }        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
       public String gpss (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsRegistrado h = new GpsRegistrado();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = GpsInventarioBD.selectOwnerAll(h);                   
                session.setAttribute("invallgps", retorno);                
                url = "/app/inventarioGPS/inventarios.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }  
       public String oneGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsRegistrado h = new GpsRegistrado();
        h.setId(id);
        HttpSession session = request.getSession();
        GpsRegistrado retorno = GpsInventarioBD.searchGPSByCodeOff(h);
        session.setAttribute("inv", retorno);
        url = "/app/inventarioGPS/inventario.jsp";
        return url;
    }  
               /*METODO QUE GUARDA AL HARDWARE*/
    public String guardarGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id_gps =  request.getParameter("id_gps");
        String fk_marca =  request.getParameter("marca");
        String fk_modelo =  request.getParameter("modelo");        
        String num_cel =  request.getParameter("num_cel");
        String fk_operador =  request.getParameter("oper_cel");
        String fk_vh =  request.getParameter("id_vh");
        String fk_hardware =  request.getParameter("id_har");
        String relacionado =  request.getParameter("hayRelacion");
        
        
        String url = "";
        HttpSession session = request.getSession();       
        GpsRegistrado gpsABuscar= new GpsRegistrado();
        GpsRegistrado NuevoGps= new GpsRegistrado();
        TarjetaSim nuevaSim = new TarjetaSim();
        TarjetaSim simABuscar = new TarjetaSim();
        RelationShipHardwareGpsCar r= new RelationShipHardwareGpsCar();
        RelationShipHardwareGpsCar nuevaRelacionHardwareGpsVehiculo= new RelationShipHardwareGpsCar();        
        RelationShipSimGps nuevaRelacionSimGps= new RelationShipSimGps();        
        gpsABuscar.setId(id_gps);              
        
        try {
             if (Integer.parseInt(relacionado) > 0) {
                     nuevaSim.setNum_cel(num_cel);
                     nuevaSim.setFk_operador(Integer.parseInt(fk_operador));
                     nuevaSim.setCodigo("0");
                     nuevaSim.setAsociada(1);
                     /*SE INSERTA LA SIM Y SE OBTIENE EL ID DE LA SIM*/
                    int nuevo_id_sim = TarjetaSimBD.insertOneSimCardReturnId(nuevaSim);                     
                    if (nuevo_id_sim > 0) {
                         NuevoGps.setId(id_gps);
                         NuevoGps.setFk_marca(Integer.parseInt(fk_marca));
                         NuevoGps.setFk_modelo(Integer.parseInt(fk_modelo));              
                         NuevoGps.setImei("0"); 
                         NuevoGps.setAsociado(1);
                         NuevoGps.setSim_asociada(1);
                         /*SE INSERTA EL NUEVO GPS*/
                        int retorno1 = GpsInventarioBD.insertOneGPS(NuevoGps);
                        if (retorno1 > 0) {
                            nuevaRelacionSimGps.setFk_gps(id_gps);
                            nuevaRelacionSimGps.setFk_sim(nuevo_id_sim);
                            nuevaSim.setId(nuevo_id_sim);                            
                            /*SE INSERTA LA RELACION SIM CARD - GPS*/
                            int retorno2 = RelationShipSimGpsBD.insertOneRelationShipSimGps(nuevaRelacionSimGps); 
                            /*SE ACTUALIZA EL CAMPO SIM_ASOCIADA EN GPS*/
                            int retorno3 = GpsInventarioBD.updateGpsRegisterMatchSim(NuevoGps);
                            /*SE ACTUALIZA EL CAMPO ASOCIADA EN TARJETAS SIM*/
                            int retorno4 = TarjetaSimBD.updateSimCardRecordMatch(nuevaSim);
                            if (retorno2 > 0) {
                                nuevaRelacionHardwareGpsVehiculo.setFk_gps(id_gps);
                                nuevaRelacionHardwareGpsVehiculo.setFk_hardware(Integer.parseInt(fk_hardware));
                                nuevaRelacionHardwareGpsVehiculo.setFk_vehiculo(Integer.parseInt(fk_vh));
                                /*gpsABuscar.setAsociado(1);
                                gpsABuscar.setId(id_gps);*/
                                /*SE INSERTA LA RELACION VEHICULO - HARDWARE - GPS Y SE OBTIENE EL ID*/
                                int idrhgv = RelationShipHardwareGpsCarBD.insertOneRelationShipHardwareGpsCarReturnId(nuevaRelacionHardwareGpsVehiculo);
                                int retorno5 = GpsInventarioBD.updateGpsRegisterMatch(NuevoGps);
                                /**
                                * SE INICIA LA BUSQUEDA DEL GPS REGISTRADO                                                               
                                **/                               
                               GpsRegistrado nuevoGpsEncontrado = GpsInventarioBD.searchGPSById(NuevoGps);   
                               simABuscar.setId(nuevo_id_sim);
                               /*SE INICIA LA BUSQUEDA DEL NUMERO CELULAR*/
                               TarjetaSim nuevaSimEncontrada = TarjetaSimBD.searchSimCardById(simABuscar);
                                /*SE INICIA LA BUSQUEDA DE LA RELACION H-G-V*/
                               r.setId(idrhgv);
                               RelationShipHardwareGpsCar nuevaRelacionEncontrada = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(r);                               
                               request.setAttribute("gps", nuevoGpsEncontrado);
                               request.setAttribute("sim", nuevaSimEncontrada);               
                               request.setAttribute("rhgv", nuevaRelacionEncontrada); 
                               request.setAttribute("asociado", 1);
                            }/*RELACION SIM - GPS*/
                        }/*INSERTAR GPS*/
                    }/*INSERTAR SIM*/                                  
             }/*SE REALIZA EL REGISTRO DEL GPS AL VEHICULO*/
             else{/*SE REALIZA SOLO EL REGISTRO DEL GPS SIN RELACIONARLO*/
                     nuevaSim.setNum_cel(num_cel);
                     nuevaSim.setFk_operador(Integer.parseInt(fk_operador));
                     nuevaSim.setCodigo("0");
                     nuevaSim.setAsociada(1);
                     /*SE INSERTA LA SIM Y SE OBTIENE EL ID DE LA SIM*/
                    int nuevo_id_sim = TarjetaSimBD.insertOneSimCardReturnId(nuevaSim);                     
                    if (nuevo_id_sim > 0) {
                         NuevoGps.setId(id_gps);
                         NuevoGps.setFk_marca(Integer.parseInt(fk_marca));
                         NuevoGps.setFk_modelo(Integer.parseInt(fk_modelo));              
                         NuevoGps.setImei("0"); 
                         NuevoGps.setAsociado(1);
                         NuevoGps.setSim_asociada(1);
                         /*SE INSERTA EL NUEVO GPS*/
                        int retorno1 = GpsInventarioBD.insertOneGPS(NuevoGps);
                        if (retorno1 > 0) {
                            nuevaRelacionSimGps.setFk_gps(id_gps);
                            nuevaRelacionSimGps.setFk_sim(nuevo_id_sim);
                            /*SE INSERTA LA RELACION SIM CARD - GPS*/
                            int retorno2 = RelationShipSimGpsBD.insertOneRelationShipSimGps(nuevaRelacionSimGps); 
                            /*******************************/
                            nuevaSim.setId(nuevo_id_sim);                            
                            int retorno3 = TarjetaSimBD.updateSimCardRecordMatch(nuevaSim);
                            int retorno4 = GpsInventarioBD.updateGpsRegisterMatchSim(NuevoGps);
                            
                                /**SE INICIA LA BUSQUEDA DEL GPS REGISTRADO**/                               
                               GpsRegistrado nuevoGpsEncontrado = GpsInventarioBD.searchGPSById(NuevoGps);   
                               simABuscar.setId(nuevo_id_sim);
                               /*SE INICIA LA BUSQUEDA DEL NUMERO CELULAR*/
                               TarjetaSim nuevaSimEncontrada = TarjetaSimBD.searchSimCardById(simABuscar);
                               request.setAttribute("gps", nuevoGpsEncontrado);
                               request.setAttribute("sim", nuevaSimEncontrada);               
                               request.setAttribute("asociado", 0);
                        }/*INSERTAR GPS*/
                    }/*INSERTAR SIM*/                 
             }/*SOLO SE REGISTRA EL GPS NO SE RELACIONA CON NINGUN VEHICULO*/
         } catch (Exception e) {
              System.err.println(e.getMessage());
              request.setAttribute("asociado", -1);              
         }
         url = "/app/inventarioGPS/guardar.jsp";                
         
         return url;
    }    
    public String eliminarGps (HttpServletRequest request,
            HttpServletResponse response) {        
        String cod_gps =  request.getParameter("cod");                                
        String url = "";
        HttpSession session = request.getSession();       
        GpsRegistrado gps= new GpsRegistrado();        
         try {              
                gps.setId(cod_gps);                          
                /*ENCONTRAMOS EL GPS*/
                GpsRegistrado gpsEncontrado = GpsInventarioBD.searchGPSByCodeOn(gps);            
                gpsEncontrado.setEstado(0);                
                gpsEncontrado.setAsociado(0);
                gpsEncontrado.setSim_asociada(0);
                
                //BUSCAMOS LA RELACION SIM - GPS
                RelationShipSimGps  relacionEncontrada = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(gpsEncontrado);
                relacionEncontrada.setEstado(0);
                
                //BUSCAMOS LA TARJETA SIM ASOCIADA
                TarjetaSim ts = TarjetaSimBD.searchSimCardById(relacionEncontrada);
                ts.setAsociada(0);
                //DESACTIVAMOS EL GPS
                int r1 = GpsInventarioBD.updateGpsRegisterMatch(gpsEncontrado);                
                
                /*DESACTIVAMOS LA SIM EN LA TABLA GPS*/
                int r2 = GpsInventarioBD.updateGpsRegisterMatchSim(gpsEncontrado);
                
                /*DESACTIVAMOS LAS RELACION HARDWARE - GPS - VEHICULO*//*
                
                
                *SE MODIFICA PARA EFECTOS DE EJECUTAR*
                */
                int r3 = RelationShipHardwareGpsCarBD.updateOneRelationshipStateByFkGps1(gpsEncontrado);
                
                /*DESACTIVAMOS LA SIM EN LA TABLA SIM*/
                int r4 = TarjetaSimBD.updateSimCardRecordMatch(ts);
                
                /*DESACTIVAMOS LA RELACION GPS - SIM*/
                int r5 = RelationShipSimGpsBD.updateOneRelationshipStateById(relacionEncontrada);  
                
                if ((r1>0) && (r2>0) && (r3>0) && (r4>0) && (r5>0)) {
                  request.setAttribute("r1", r1);
                  request.setAttribute("r2", r2); 
                }
                
         } catch (Exception e) {
              System.err.println(e.getMessage());              
              url = "/app/inventarioGPS/eliminar.jsp";
         }
         url = "/app/inventarioGPS/eliminar.jsp";
         return url;
    }
    public String consultaGpsNoSim (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsRegistrado h = new GpsRegistrado();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList<GpsRegistrado> retorno = GpsInventarioBD.searchGPSNotMatchWithOutSim(h);
        request.setAttribute("invgpsnosim", retorno);
        url = "/app/inventarioGPS/gpsFreeSim.jsp";
        return url;
    }  
    public String consultaGpsSinVh (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsRegistrado h = new GpsRegistrado();
        h.setAsociado(Integer.parseInt(id));
        HttpSession session = request.getSession();
        ArrayList<GpsRegistrado> retorno = GpsInventarioBD.searchGPSNotMatchWithCar(h);
        request.setAttribute("invgpsnovh", retorno);
        url = "/app/inventarioGPS/gpsFree.jsp";
        return url;
    }      
    public String reAsignarGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id_vh =  request.getParameter("id_vh");
        String cod_gps =  request.getParameter("cod_gps");
        String id_hardware =  request.getParameter("id_hard");
        
        String url="";                   
        HttpSession session = request.getSession();
        
        RelationShipHardwareGpsCar r = new RelationShipHardwareGpsCar();
        GpsRegistrado h = new GpsRegistrado();
        h.setId(cod_gps);
        h.setEstado(1);
        /*BUSCAMOS EL GPS*/
        GpsRegistrado gpsEncontrado = GpsInventarioBD.searchGPSByCodeOff(h);
        r.setFk_gps(gpsEncontrado.getId());
        r.setFk_vehiculo(Integer.parseInt(id_vh));
        r.setFk_hardware(Integer.parseInt(id_hardware));        
        r.setEstado(1);
        /*BUSCAMOS LA RELACION ENTRE EL GPS Y UN VEHICULO*/
        RelationShipHardwareGpsCar relacionEncontrada = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarByGps(r);
        if (relacionEncontrada != null) {
            /*SE VERIFICA QUE UN GPS RETIRADO DE UN VEHICULO SE ASIGNA AL MISMO EN ESE CASO SOLO SE ACTIVA LA RELACION*/
            if ( (relacionEncontrada.getFk_vehiculo() == Integer.parseInt(id_vh)) && (gpsEncontrado.getId().equals(cod_gps)) ){                                
                relacionEncontrada.setEstado(1);
                gpsEncontrado.setAsociado(1);
                int retorno1 = GpsInventarioBD.updateGpsRegisterMatch(gpsEncontrado);/*ACTUALIZA EL GPS*/
                int retorno2 = RelationShipHardwareGpsCarBD.updateOneRelationshipStateById(relacionEncontrada);/*ACTUALIZA LA RELACION*/
                
                if (retorno2 > 0) {
                    /*CARGAR LA INFORMACION DEL FORMULARIO*/
                    RelationShipHardwareGpsCar hardwareGpsVehiculoEncontrado = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(relacionEncontrada);
                    GpsRegistrado gpsEncontrado1 = GpsInventarioBD.searchGPSByIdMaper(gpsEncontrado);                    
                    RelationShipSimGps relacionSimGps = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(gpsEncontrado1);
                    TarjetaSim tarjetaSim = TarjetaSimBD.searchSimCardById(relacionSimGps);
                    
                    session.setAttribute("hard", hardwareGpsVehiculoEncontrado);
                    session.setAttribute("gps", gpsEncontrado1);
                    session.setAttribute("sim", tarjetaSim);
                }
            }            
        }/*FIN RELACION NULA*/        
        else{/*SE REGISTRA LA NUEVA RELACION ENTRE EL VEHICULO Y EL GPS*/
                /*ACTIVAMOS EL GPS DE NUEVO*/
                /*int retorno1 = GpsInventarioBD.updateStateGpsRegisterbyCod(h);*/        
                int retorno1 = GpsInventarioBD.updateStateGpsRegister(gpsEncontrado);/*ACTUALIZA EL GPS*/
                int idr = RelationShipHardwareGpsCarBD.insertOneRelationShipHardwareGpsCarReturnId(r);                
                if (idr > 0) {
                    /*CARGAR INFORMACION DEL FORMULARIO*/
                    RelationShipHardwareGpsCar nuevaRelacion = new RelationShipHardwareGpsCar();
                    nuevaRelacion.setId(idr);                    
                    
                    RelationShipHardwareGpsCar nuevoHardware = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(nuevaRelacion);
                    RelationShipSimGps nuevaRelacionSimGps = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(gpsEncontrado);                    
                    TarjetaSim nuevaSim = TarjetaSimBD.searchSimCardById(nuevaRelacionSimGps);
                    
                    session.setAttribute("hard", nuevoHardware);
                    session.setAttribute("gps", gpsEncontrado);
                    session.setAttribute("sim", nuevaSim);
                }
            }
              
        url = "/app/inventarioGPS/reasignarGpsVh.jsp";
        return url;
    }      
    public String asociarGps (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id_vh =  request.getParameter("id_vh");
        String id_gps =  request.getParameter("id_gps");
        String id_hardware =  request.getParameter("id_hrd");
        String id_sim =  request.getParameter("id_sim");
        
        
        String url="";                   
        HttpSession session = request.getSession();
        
        try {
                    GpsRegistrado h = new GpsRegistrado();
                    h.setId(id_gps);
                    h.setEstado(1);
                    /*BUSCAMOS EL GPS NO ASOCIADO*/
                    GpsRegistrado gpsEncontrado = GpsInventarioBD.searchGPSByCodeMatchOff(h);

                    RelationShipHardwareGpsCar r = new RelationShipHardwareGpsCar();
                    r.setFk_gps(gpsEncontrado.getId());
                    r.setFk_vehiculo(Integer.parseInt(id_vh));
                    r.setFk_hardware(Integer.parseInt(id_hardware));
                    r.setEstado(1);
                    /*********************************************************************/
                    RelationShipSimGps relacionSimGpsABuscar = new RelationShipSimGps();
                    relacionSimGpsABuscar.setFk_gps(id_gps);
                    relacionSimGpsABuscar.setFk_sim(Integer.parseInt(id_sim));

                    /*BUSCAMOS SI EL GPS TIEME UNA RELACION CON EL HARDWARE*/
                    RelationShipHardwareGpsCar relacionGpsCarByGps = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarByGps(r);
                    
                    /*BUSCAMOS SI EL GPS TIENE UNA LA RELACION CON LA SIM*/
                    RelationShipSimGps relacionGpsSimByGps = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(relacionSimGpsABuscar);
                    /*FALTA ADICIONA UN METODO PARA BUSCAR LA RELACION DESDE LA SIM-***/

                    if ((relacionGpsCarByGps != null) && (relacionGpsSimByGps != null)){
                        /*SE VERIFICA QUE UN GPS RETIRADO DE UN VEHICULO SE ASIGNA AL MISMO EN ESE CASO SOLO SE ACTIVA LA RELACION*/            
                        if ( (relacionGpsCarByGps.getFk_vehiculo() == Integer.parseInt(id_vh)) && (gpsEncontrado.getId().equals(id_gps)) && (relacionGpsCarByGps.getEstado() == 0)){
                            relacionGpsCarByGps.setEstado(1);
                            gpsEncontrado.setAsociado(1);
                            gpsEncontrado.setSim_asociada(1);
                            gpsEncontrado.setEstado(1);
                            /*************************************/
                            relacionSimGpsABuscar.setEstado(1);                       
                            /********************************************************************************************************************************/
                            int retorno1 = GpsInventarioBD.updateGpsRegisterMatch(gpsEncontrado);/*ACTUALIZA EL CAMPO ASIGNADO EN GPS*/                
                            int retorno2 = RelationShipHardwareGpsCarBD.updateOneRelationshipStateById(relacionGpsCarByGps);/*ACTUALIZA LA RELACION H-G-V*/
                            int retorno3 = GpsInventarioBD.updateGpsRegisterMatchSim(gpsEncontrado);/*ACTUALIZA EL CAMPO SIM ASIGNADA EN GPS*/
                            int retorno4 = TarjetaSimBD.updateSimCardRecordMatch(relacionSimGpsABuscar);/*ACTUALIZA EL CAMPO ASIGNADA EN TARJETA SIM*/
                            int retorno5 = RelationShipSimGpsBD.updateOneRelationShipSimGpsStateByGps(gpsEncontrado);/*ACTUALIZA LA RELACION SIM-GPS*/

                            if (retorno2 > 0) {
                                /*CARGAR LA INFORMACION DEL FORMULARIO*/
                                //BUSCAMOS LA RELACION HARDWARE - VEHICULO - GPS
                                RelationShipHardwareGpsCar hardwareGpsVehiculoEncontrado = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(relacionGpsCarByGps);
                                //BUSCAMOS EL GPS REGISTRADO Y ASOCIADO
                                GpsRegistrado gpsEncontrado1 = GpsInventarioBD.searchGPSByIdMaper(gpsEncontrado);                    
                                //BUSCAMOS LA RELACION GPS - SIM
                                RelationShipSimGps relacionSimGps = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(gpsEncontrado1);
                                //BUSCAMOS LA TARJETA SIM
                                TarjetaSim tarjetaSim = TarjetaSimBD.searchSimCardById(relacionSimGps);                    
                                
                                session.setAttribute("hard", hardwareGpsVehiculoEncontrado);
                                session.setAttribute("gps", gpsEncontrado1);
                                session.setAttribute("sim", tarjetaSim);
                            }
                        }
                        else{//ESTA CONDICION ASIGNA UN GPS QUE SE HA REGISTRADO ANTERIORMENTE, VUELVE A ASIGNARSE A OTRO VEHICULO DIFERENTE O AL MISMO SI SE DIERA EL CASO
                                gpsEncontrado.setAsociado(1);                        
                                gpsEncontrado.setSim_asociada(1);                

                                RelationShipSimGps nuevaRelacionSG = new RelationShipSimGps();
                                nuevaRelacionSG.setFk_gps(id_gps);
                                nuevaRelacionSG.setFk_sim(Integer.parseInt(id_sim));
                                nuevaRelacionSG.setEstado(1);

                                int retorno1 = GpsInventarioBD.updateGpsRegisterMatch(gpsEncontrado);/*ACTUALIZA EL CAMPO ASOCIADO*/
                                int retorno2 = RelationShipSimGpsBD.insertOneRelationShipSimGps(nuevaRelacionSG);/*CREA UNA NUEVA RELACION GPS-SIM*/
                                int retorno3 = GpsInventarioBD.updateGpsRegisterMatchSim(gpsEncontrado);/*ACTUALIZA EL CAMPO ASOCIADA EN GPS*/
                                int retorno4 = TarjetaSimBD.updateSimCardRecordMatch(nuevaRelacionSG);/*ACTUALIZA EL CAMPO ASOCIADA EN SIM*/
                                int idr = RelationShipHardwareGpsCarBD.insertOneRelationShipHardwareGpsCarReturnId(r);/*CREA UNA RELACION GPS-VEHICULO*/
                                if (idr > 0) {
                                    /*CARGAR INFORMACION DEL FORMULARIO*/
                                    RelationShipHardwareGpsCar nuevaRelacion = new RelationShipHardwareGpsCar();
                                    nuevaRelacion.setId(idr);
                                    //BUSCAMOS LA RELACION HARDWARE -GPS - VEHICULO
                                    RelationShipHardwareGpsCar nuevoHardwareRegistrado = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(nuevaRelacion);
                                    //BUSCAMOS LA RELACION GPS - SIM
                                    RelationShipSimGps nuevaRelacionSimGpsRegistrado = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(gpsEncontrado);                    
                                    //BUSCAMOS LA TARJETA SIM
                                    TarjetaSim nuevaSimRegistrada = TarjetaSimBD.searchSimCardById(nuevaRelacionSimGpsRegistrado);
                                    //BUSCAMOS EL GPS
                                    GpsRegistrado nuevoGpsRegistrado = GpsInventarioBD.searchGPSById(nuevaRelacionSimGpsRegistrado);

                                    session.setAttribute("hard", nuevoHardwareRegistrado);
                                    session.setAttribute("gps", nuevoGpsRegistrado);
                                    session.setAttribute("sim", nuevaSimRegistrada);
                                }
                        }
                    }/*FIN RELACION NULA*/        
                    else{/*SE REGISTRA LA NUEVA RELACION ENTRE EL VEHICULO Y EL GPS*/
                                gpsEncontrado.setAsociado(1);                        
                                gpsEncontrado.setSim_asociada(1);                

                                RelationShipSimGps nuevaRelacionSG = new RelationShipSimGps();
                                nuevaRelacionSG.setFk_gps(id_gps);
                                nuevaRelacionSG.setFk_sim(Integer.parseInt(id_sim));
                                nuevaRelacionSG.setEstado(1);

                                int retorno1 = GpsInventarioBD.updateGpsRegisterMatch(gpsEncontrado);/*ACTUALIZA EL CAMPO ASOCIADO*/
                                int retorno2 = RelationShipSimGpsBD.insertOneRelationShipSimGps(nuevaRelacionSG);/*CREA UNA NUEVA RELACION GPS-SIM*/
                                int retorno3 = GpsInventarioBD.updateGpsRegisterMatchSim(gpsEncontrado);
                                int retorno4 = TarjetaSimBD.updateSimCardRecordMatch(nuevaRelacionSG);
                                int idr = RelationShipHardwareGpsCarBD.insertOneRelationShipHardwareGpsCarReturnId(r);/*CREA UNA RELACION GPS-VEHICULO*/
                                if (idr > 0) {
                                    /*CARGAR INFORMACION DEL FORMULARIO*/
                                    RelationShipHardwareGpsCar nuevaRelacion = new RelationShipHardwareGpsCar();
                                    nuevaRelacion.setId(idr);
                                    //BUSCAMOS LA RELACION HARDWARE - VEHICULO - GPS
                                    RelationShipHardwareGpsCar nuevoHardwareRegistrado = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarById(nuevaRelacion);
                                    //BUSCAMOS LA RELACION SIM-GPS
                                    RelationShipSimGps nuevaRelacionSimGpsRegistrado = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(gpsEncontrado);                    
                                    //BUSCAMOS LA SIM
                                    TarjetaSim nuevaSimRegistrada = TarjetaSimBD.searchSimCardById(nuevaRelacionSimGpsRegistrado);
                                    //BUSCAMOS EL GPS
                                    GpsRegistrado nuevoGpsRegistrado = GpsInventarioBD.searchGPSById(nuevaRelacionSimGpsRegistrado);

                                    session.setAttribute("hard", nuevoHardwareRegistrado);
                                    session.setAttribute("gps", nuevoGpsRegistrado);
                                    session.setAttribute("sim", nuevaSimRegistrada);
                                }

                        }            
        } catch (Exception e) {
            session.setAttribute("gps", null);
            System.err.println(e.getMessage());
        }              
        url = "/app/inventarioGPS/asociar.jsp";
        return url;
    }      
     public String gpsRepetido (HttpServletRequest request,
            HttpServletResponse response){
        
        String idGpsABuscar =  request.getParameter("id");
        String url="";                   
        HttpSession session = request.getSession();
        
        GpsRegistrado a = new GpsRegistrado();
        TarjetaSim sim = new TarjetaSim();
        if (!idGpsABuscar.equals("")) {
            a.setId(idGpsABuscar.trim());
        }
         GpsRegistrado gps= new GpsRegistrado();
        int repetido = GpsInventarioBD.gpsMatchWithCar(a);
         if (repetido == 0) {
            gps = GpsInventarioBD.searchGPSById(a);
            RelationShipSimGps relacionSimGps = RelationShipSimGpsBD.searchRelationShipSimGpsByFkGps(a);
             if (relacionSimGps != null) {
                 sim = TarjetaSimBD.searchSimCardById(relacionSimGps);
                 if ( sim != null) {
                     
                 }
             }else{
             sim.setNum_cel("0");
             sim.setFk_operador(0);
             }
            
         }
        session.setAttribute("g0", repetido);
        session.setAttribute("gpsf", gps);
        session.setAttribute("simf", sim);
        url = "/app/inventarioGPS/asociado.jsp";        
        return url;
    }
     public String consultaVehiculoConGps (HttpServletRequest request,
            HttpServletResponse response){
        
        String idVehiculoABuscar =  request.getParameter("id");
        String url="";                   
        HttpSession session = request.getSession();
        
        GpsRegistrado a = new GpsRegistrado();
        if (!idVehiculoABuscar.equals("")) {
            a.setId(idVehiculoABuscar.trim());
        }
        
        RelationShipHardwareGpsCar rtno = GpsInventarioBD.gpsMatchWithCarById(a);
        session.setAttribute("av", rtno);
        url = "/app/inventarioGPS/asociadoVehiculo.jsp";        
        return url;
    }
     /******************************************************************/
     
     public String guardarGpsInventario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id_gps =  request.getParameter("id");
        String fk_marca =  request.getParameter("marca");
        String fk_modelo =  request.getParameter("modelo");        
        String num_cel =  request.getParameter("numero");
        String fk_operador =  request.getParameter("operador"); 
        String asocia =  request.getParameter("asociar"); 
        
        String url = "";
        SelectBD select = null;
        HttpSession session = request.getSession();       
        GpsRegistrado gpsABuscar= new GpsRegistrado();
        GpsRegistrado NuevoGps= new GpsRegistrado();
        TarjetaSim nuevaSim = new TarjetaSim();        
        RelationShipSimGps nuevaRelacionSimGps= new RelationShipSimGps();        
        gpsABuscar.setId(id_gps);              
        
        try { 
                if (asocia != null) {                
                     nuevaSim.setNum_cel(num_cel);
                     nuevaSim.setFk_operador(Integer.parseInt(fk_operador));
                     nuevaSim.setCodigo("0");
                     nuevaSim.setAsociada(1);
                     /*SE INSERTA LA SIM Y SE OBTIENE EL ID DE LA SIM*/
                    int nuevo_id_sim = TarjetaSimBD.insertOneSimCardReturnId(nuevaSim);                     
                    if (nuevo_id_sim > 0) {
                         NuevoGps.setId(id_gps);
                         NuevoGps.setFk_marca(Integer.parseInt(fk_marca));
                         NuevoGps.setFk_modelo(Integer.parseInt(fk_modelo));              
                         NuevoGps.setImei("0"); 
                         NuevoGps.setAsociado(1);
                         NuevoGps.setSim_asociada(1);
                         /*SE INSERTA EL NUEVO GPS*/
                        int retorno1 = GpsInventarioBD.insertOneGPS(NuevoGps);
                        if (retorno1 > 0) {
                            nuevaRelacionSimGps.setFk_gps(id_gps);
                            nuevaRelacionSimGps.setFk_sim(nuevo_id_sim);
                            nuevaSim.setId(nuevo_id_sim);                            
                            /*SE INSERTA LA RELACION SIM CARD - GPS*/
                            int retorno2 = RelationShipSimGpsBD.insertOneRelationShipSimGps(nuevaRelacionSimGps); 
                            /*SE ACTUALIZA EL CAMPO SIM_ASOCIADA EN GPS*/
                            int retorno3 = GpsInventarioBD.updateGpsRegisterMatchSim(NuevoGps);
                            /*SE ACTUALIZA EL CAMPO ASOCIADA EN TARJETAS SIM*/
                            int retorno4 = TarjetaSimBD.updateSimCardRecordMatch(nuevaSim);
                            if ((retorno1 > 0) && (retorno2 > 0) && (retorno3 > 0) && (retorno4 > 0)) {                                                             
                               switch (retorno1) {
                                   case 0:                    
                                       request.setAttribute("idInfo", 2);                    
                                       request.setAttribute("msg", "El GPS NO fue registrado");
                                       break;                
                                   case 1:                    
                                       select = new SelectBD(request);                    
                                       session.setAttribute("select", select);       
                                       request.setAttribute("idInfo", 1);                    
                                       request.setAttribute("msg", "Se ha creado un nuevo GPS");                    
                                       break;                
                                   default:
                                       break;
                               }
                               
                            }/*RELACION SIM - GPS*/
                        }/*INSERTAR GPS*/
                    }/*INSERTAR SIM*/                                  
                }/*FIN SI ASOCIA A SIM*/
                else{
                         NuevoGps.setId(id_gps);
                         NuevoGps.setFk_marca(Integer.parseInt(fk_marca));
                         NuevoGps.setFk_modelo(Integer.parseInt(fk_modelo));              
                         NuevoGps.setImei("0"); 
                         NuevoGps.setAsociado(1);
                         NuevoGps.setSim_asociada(1);
                         /*SE INSERTA EL NUEVO GPS*/
                        int retorno1 = GpsInventarioBD.insertOneGPS(NuevoGps);
                        if (retorno1 > 0) {                            
                               switch (retorno1) {
                                   case 0:                    
                                       request.setAttribute("idInfo", 2);                    
                                       request.setAttribute("msg", "El GPS NO fue registrado");
                                       break;                
                                   case 1:                    
                                       select = new SelectBD(request);                    
                                       session.setAttribute("select", select);       
                                       request.setAttribute("idInfo", 1);                    
                                       request.setAttribute("msg", "Se ha creado un nuevo GPS");                    
                                       break;                
                                   default:
                                       break;
                               }
                        }/*INSERTAR GPS*/
                }/*FIN ELSE ASOCIA SIM*/
             
         } catch (Exception e) {
              System.err.println(e.getMessage());
              url = "/app/inventarioGPS/nuevoGps.jsp";                
         }
         url = "/app/inventarioGPS/nuevoGps.jsp";                
         
         return url;
    }
     
      public String verGpsInventario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        GpsRegistrado h = new GpsRegistrado();
        h.setId(id);
        HttpSession session = request.getSession();
        GpsRegistrado retorno = GpsInventarioBD.searchGPSByCodeOnFull(h);
          if (retorno != null) {
              session.setAttribute("gps_v", retorno);
             url = "/app/inventarioGPS/unGps.jsp";
          }
          else{
                 url = "/app/inventarioGPS/listadoGps.jsp";
          }
        
        return url;
    }  
}

