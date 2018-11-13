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
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.GpsInventarioBD;
import com.registel.rdw.datos.RelationShipHardwareGpsCarBD;
import com.registel.rdw.datos.RelationShipSimGpsBD;
import com.registel.rdw.datos.RelationShipRevisionVehiculoBD;
import com.registel.rdw.datos.RelationShipTarjetaOperacionVehiculoBD;

import com.registel.rdw.datos.GrupoMovilBD;
import com.registel.rdw.datos.GpsMarcaBD;
import com.registel.rdw.datos.GpsModeloBD;
import com.registel.rdw.datos.OperadorBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.datos.PropietarioBD;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.datos.TarjetaOperacionBD;
import com.registel.rdw.datos.SelectBD;

import com.registel.rdw.datos.RevisionTMecanicaBD;
import com.registel.rdw.datos.HardwareBD;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.GrupoMovil;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.PropietarioVehiculo;
import com.registel.rdw.logica.GpsRegistrado;
import com.registel.rdw.logica.Hardware;
import com.registel.rdw.logica.RelationShipHardwareGpsCar;
import com.registel.rdw.logica.RelationShipRevisionTMVehiculo;
import com.registel.rdw.logica.RelationShipSimGps;
import com.registel.rdw.logica.RelationShipTarjetaOperacionVehiculo;

import com.registel.rdw.logica.GpsMarca;

import com.registel.rdw.logica.GpsModelo;
import com.registel.rdw.logica.GrupoVehiculo;
import com.registel.rdw.logica.Operador;
import com.registel.rdw.logica.RevisionTMecanica;
import com.registel.rdw.logica.TarjetaOperacion;

import com.registel.rdw.logica.TarjetaSim;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorMovil extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    //private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
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
        //String ulr1=request.getParameter("url1");        
        String requestURI = request.getRequestURI();
        
        String url = "/";
        if (requestURI.endsWith("/guardarMovil")) {
            url = guardarMovil(request, response);
        } else if (requestURI.endsWith("/editarMovil")) {
            url = editarMovil(request, response);
        } else if (requestURI.endsWith("/verMasMovil")) {
            url = verMovil(request, response);        
        } else if (requestURI.endsWith("/eliminarMovil")) {
            url = eliminarMovil(request, response);
        }else if (requestURI.endsWith("/placaRepetida")) {
            url = PlacaRepetida(request, response);
        }
        
        //response.sendRedirect("login.jsp");
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String guardarMovil (HttpServletRequest request,
            HttpServletResponse response) {
                        
        HttpSession session = request.getSession();
        
        String placa = request.getParameter("placa");
        String num_interno = request.getParameter("numero_interno");  
        String idPropietario = request.getParameter("propietario");  
        String capacidad_pasajeros = request.getParameter("cant_pasajeros");          
        String[] diasPicoYPlaca = request.getParameterValues("dia_pico_placa");          
        String[] diasDescanso = request.getParameterValues("dias_descanso");          
        String idGrupo = request.getParameter("grupo");  
               
        /*INFORMACION GPS ASOCIADO AL VEHICULO*/
        String fk_hardware = request.getParameter("hardware");  
        String id_gps = request.getParameter("id_gps");          
        String fk_marca = request.getParameter("marca_gps");  
        String fk_modelo = request.getParameter("modelo_gps");  
        String numero_celular = request.getParameter("num_cel_gps");  
        String fk_operador = request.getParameter("oper_cel_gps");  
                
        String diasPyP="";
        String diasD="";        
        /**INFORMACION REVISION TECNICO MECANICA**/
        String codigo_ = request.getParameter("codigo_");        
        String fecha_v_= request.getParameter("f_vto_");  
        String fk_cda_ = request.getParameter("centro_diag_");          
        String obs_ = request.getParameter("observaciones_");  
        /** TARJETA DE OPERACION**/
        String codigo_to = request.getParameter("codigo_to");
        String modelo_to = request.getParameter("modelo_to");        
        String fecha_v_to= request.getParameter("f_vto_to");          
        String fk_exp_to = request.getParameter("centro_exp_to");          
        String obs_to = request.getParameter("observaciones_to");  
        /****/
                
        
        Usuario user = (Usuario) session.getAttribute("login");        
        Movil m = new Movil();        
        int id_Vh = 0;        
        int retornoPropietario=0;
        int retornoGrupoMovil=0;    
        int retornoRelacionHardwareGpsVehiculo = 0;
                
        try {
            SelectBD select = null;
            if (user.getIdempresa() > 0) {
                m.setFkEmpresa(user.getIdempresa() );        
            } else {
                throw  new NullPointerException();
            }
            if (!placa.equals("")) {
                m.setPlaca(placa);
            } else {
                m.setPlaca("");
            }
            if (!num_interno.equals("")) {
                m.setNumeroInterno(num_interno);
            } else {
                m.setNumeroInterno("0");
            }
            if (!capacidad_pasajeros.equals("")) {
                m.setCapacidad(Integer.parseInt(capacidad_pasajeros));            
            } else{
                m.setCapacidad(0);
            }
            
            if (diasPicoYPlaca != null) {
                for (String diasPicoYPlaca1 : diasPicoYPlaca) {
                diasPyP += diasPicoYPlaca1;
                diasPyP += ",";
                }
                m.setDiaPicoplaca(diasPyP.substring(0, (diasPyP.length() - 1) ) );
            }else{
                m.setDiaPicoplaca("0");
            }
            
            /*************************************************/
            if (diasDescanso != null) {
                for (String diasDescanso1 : diasDescanso) {
                diasD += diasDescanso1;
                diasD += ",";
                }    
                m.setDiaDescanso(diasD.substring(0, (diasD.length() - 1) ) );
            }
            else{
                m.setDiaDescanso("0");
            }                        
            m.setEstado(new Short("1"));
            //INSERTA VEHICULO, RETORNA ID DEL VEHICULO REGISTRADO            
            id_Vh = MovilBD.insertReturnId(m);
            
            if (id_Vh > 0) {
                
                try {//INSERTA LA RELACION VEHICULO PROPIETARIO                           
                        PropietarioVehiculo pv = new PropietarioVehiculo(); 
                        if (id_Vh > 0) {
                            pv.setFk_vehiculo(id_Vh);
                        } else{
                            throw  new NullPointerException();
                        }                        
                        if (!idPropietario.equals("")) {
                            pv.setFk_propietario(Integer.parseInt(idPropietario));                            
                        } else{
                            throw  new NullPointerException();
                        }
                        pv.setEstado(1);                
                        retornoPropietario = PropietarioBD.insertOneRelationshipOwnerCar(pv);
                    
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                try {//INSERTA LA RELACION DE UN VEHICULO A UN GRUPO                       
                        GrupoMovil gm = new GrupoMovil();
                        if (id_Vh>0) {
                         gm.setFkVehiculo(id_Vh);
                        } else{
                            throw  new NullPointerException();
                        } 
                        
                        if (!idGrupo.equals("")) {
                            gm.setFkGrupo(Integer.parseInt(idGrupo));
                        } else{
                            throw  new NullPointerException();
                        }                        
                        gm.setEstado(1);   
                        retornoGrupoMovil = GrupoMovilBD.insertOneRelationShipGroup(gm);                    
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                }             
                                
                
                 try {//INSERTA LA RELACION HARDWARE - GPS - VEHICULO
                    RelationShipHardwareGpsCar rhgv = new RelationShipHardwareGpsCar();
                    if (id_Vh>0) {
                        rhgv.setFk_vehiculo(id_Vh);
                    } else {
                       throw  new NullPointerException();
                    }                    
                    if (!fk_hardware.equals("")) {
                        rhgv.setFk_hardware(Integer.parseInt(fk_hardware));
                    } else {
                        fk_hardware="0";
                       rhgv.setFk_hardware(0);
                    }
                     if (!id_gps.equals("")) {
                         rhgv.setFk_gps(id_gps);
                         //1. SE PREGUNTA SI EXISTE EL GPS
                         if (GpsInventarioBD.existeGpsById(rhgv)) {
                             rhgv.setFk_gps(id_gps);
                         } else//SINO SE REGISTRA
                         if (!id_gps.contains("No asignado")) {
                             GpsRegistrado nuevoGps = new GpsRegistrado();
                             nuevoGps.setId(id_gps);
                             nuevoGps.setImei("0");
                             nuevoGps.setFk_marca(Integer.parseInt(fk_marca));
                             nuevoGps.setFk_modelo(Integer.parseInt(fk_modelo));
                             if (GpsInventarioBD.insertOneGPS(nuevoGps) > 0) {
                                 //System.out.println("SE REGISTRA GPS PRIMERA VEZ");
                                 rhgv.setFk_gps(id_gps);
                             }
                         }
                     } else {
                         rhgv.setFk_gps("No asignado");
                     }
                     if (!numero_celular.equals("")) {
                         rhgv.setNumero_celular(numero_celular);
                         if (TarjetaSimBD.existeSimCardByNCel1(rhgv)) {
                             rhgv.setNumero_celular(numero_celular);
                         } else if (!numero_celular.contains("No asignado")) {
                             TarjetaSim ts = new TarjetaSim();
                             ts.setImei("0");
                             ts.setNum_cel(numero_celular);
                             ts.setFk_operador(Integer.parseInt(fk_operador));
                             int id_nueva_tarjeta_sim = TarjetaSimBD.insertOneSimCardReturnId(ts);
                             if (id_nueva_tarjeta_sim > 0) {
                                 //System.out.println("SE REGISTRA UNA SIM POR PRIMERA VEZ CON EL NUMERO CELULAR");
                                 rhgv.setFk_sim(id_nueva_tarjeta_sim);
                                 rhgv.setNumero_celular(numero_celular);
                             }
                         }//
                     } else {
                         rhgv.setNumero_celular("No asignado");
                     }

                     if (!fk_operador.equals("")) {
                         rhgv.setFk_operador(Integer.parseInt(fk_operador));                         
                     } else {
                         fk_operador="0";
                         rhgv.setFk_operador(0);
                     }
                     rhgv.setEstado(1);
                    //INSERTA LA RELACION HARDWARE - VEHICULO - GPS
                     //if ((Integer.parseInt(fk_hardware) > 0) && (Integer.parseInt(fk_operador) > 0) && (!id_gps.contains("No asignado"))) {
                     if ((Integer.parseInt(fk_hardware) > 0) && (!id_gps.contains("No asignado"))) {
                         retornoRelacionHardwareGpsVehiculo = RelationShipHardwareGpsCarBD.insertRelationShipHdGpsVh(rhgv);
                     }else{
                         System.out.println("no se registra gps, pues no posee informacion");
                     }                    
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                
                
                if ( (id_Vh > 0) || (retornoPropietario>0) || (retornoGrupoMovil > 0) || (retornoRelacionHardwareGpsVehiculo>0)) {                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                                        
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado un nuevo movil"); 
                }else{
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar el nuevo movil");                                        
                }                
            }/*FIN SI VEHICULO*/
        } catch (Exception e) {
            System.err.println(e.getMessage());
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el movil");
            return "/app/movil/nuevoMovil.jsp";
        }                                  
        return "/app/movil/nuevoMovil.jsp";
    }    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        Movil c = new Movil();        
        c.setId(Integer.parseInt(id));        
        c.setEstado(new Short("1"));        
        SelectBD select = new SelectBD(request);        
        HttpSession session = request.getSession();  
        session.setAttribute("select", select);        
        
        Movil movilEncontrado = new Movil();
        GrupoVehiculo relacionGrupoEncontrada = new GrupoVehiculo();
        PropietarioVehiculo relacionPropietarioEncontrada = new PropietarioVehiculo();
        RelationShipHardwareGpsCar relacionHardwareGpsVehiculoEncontrada = new RelationShipHardwareGpsCar();
        GpsRegistrado gpsRegistradoEncontrado = new GpsRegistrado();
        Operador relacionOperador = new Operador();
        int partes =0;
                try {
                        movilEncontrado = MovilBD.selectByOneView(c); 
                        if (movilEncontrado != null) {                            
                            session.setAttribute("movil", movilEncontrado);                        
                            partes++;
                        }                        
                } catch (Exception e) {
                    session.removeAttribute("movil");
                  System.err.println(e.getMessage());
                } 
                //relacion grupo vehiculo
                try {                        
                        relacionGrupoEncontrada = GrupoMovilBD.searchGroupByMovil(movilEncontrado);
                        if (relacionGrupoEncontrada != null) {                            
                            session.setAttribute("rg", relacionGrupoEncontrada);
                            partes++;
                        }
                        else{
                            throw new NullPointerException();
                        }                        
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                  session.removeAttribute("rg");                  
                }
                //relacion propietario -vehiculo
                try {
                        relacionPropietarioEncontrada = PropietarioBD.existRelationShipByCar(movilEncontrado);  
                        if (relacionPropietarioEncontrada != null) {
                            session.setAttribute("rp", relacionPropietarioEncontrada);
                            partes++;
                        }
                        else{
                            throw new NullPointerException();
                        }
                        
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                  session.removeAttribute("rp");                                      
                } 
                //relacion hardware -gps -vehiculo
                try {                        
                        relacionHardwareGpsVehiculoEncontrada = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarByCar(movilEncontrado);
                        /******************************************************/
                        if (relacionHardwareGpsVehiculoEncontrada != null) {
                        partes++;
                        session.setAttribute("rhgv", relacionHardwareGpsVehiculoEncontrada);                        
                        }else{
                            throw new NullPointerException();
                        }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                  session.removeAttribute("rhgv");                   
                }    
                
                try {
                    if (!relacionHardwareGpsVehiculoEncontrada.getFk_gps().contains("No asignado")) {
                        GpsRegistrado gps = new GpsRegistrado();
                        gps.setId(relacionHardwareGpsVehiculoEncontrada.getFk_gps());
                        gpsRegistradoEncontrado = GpsInventarioBD.searchGPSById(relacionHardwareGpsVehiculoEncontrada);  
                        GpsMarca marca = GpsMarcaBD.searchGPSById(gpsRegistradoEncontrado);
                        GpsModelo modelo = GpsModeloBD.selectOneModelOnlyById(gpsRegistradoEncontrado);
                        TarjetaSim sim = TarjetaSimBD.searchSimCardById1(relacionHardwareGpsVehiculoEncontrada);        
                        /*************************************************/
                        if (gpsRegistradoEncontrado != null) {                           
                                partes++;
                                session.setAttribute("gps", gpsRegistradoEncontrado);
                                session.setAttribute("mca", marca);
                                session.setAttribute("mdl", modelo);
                                session.setAttribute("sim", sim);
                        }else{
                            throw new NullPointerException();
                        }                       
                    }                        
                } catch (Exception e) {                    
                    session.removeAttribute("gps");                     
                    session.removeAttribute("mca");
                    session.removeAttribute("mdl");
                    session.removeAttribute("sim");
                }
                                
                try {
                        RelationShipRevisionTMVehiculo r0 = RelationShipRevisionVehiculoBD.searchRelationShipRevisionTMVehiculo(c);
                        RevisionTMecanica revisionTMEncontrada = RevisionTMecanicaBD.selectRevisionTMecanica(r0);
                        if (revisionTMEncontrada != null) {
                            partes++;
                            session.setAttribute("rtm", revisionTMEncontrada);
                        }else{
                            throw new NullPointerException();
                        }
                        
                } catch (Exception e) {
                     session.removeAttribute("rtm");    
                }
                
                
                try {
                        RelationShipTarjetaOperacionVehiculo r0 = RelationShipTarjetaOperacionVehiculoBD.searchRelationShipTarjetaOperacionVehiculo(c);                        
                        TarjetaOperacion tarjetaOperacionEncontrada = TarjetaOperacionBD.selectTarjetaOperacionVehiculoById(r0);
                        
                        if (tarjetaOperacionEncontrada != null) {
                            partes++;
                            session.setAttribute("to", tarjetaOperacionEncontrada);
                        }else{
                            throw new NullPointerException();
                        }                        
                } catch (Exception e) {
                     session.removeAttribute("to");    
                }
               /*****/
                if (partes >= 1){
                    url = "/app/movil/unMovil.jsp";
                }
                else{
                    url = "/app/movil/listadoMovil.jsp";
                }
         return url;
    }
    public String editarMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id_vh = request.getParameter("id_edit");
        /*id_vh relacion grupo - vehiculo*/
        String id_relacion_grupo_actual = request.getParameter("id_g_edit");        
        /*id_vh relacion propietario - vehiculo*/
        String id_relacion_prop_actual = request.getParameter("id_p_edit");  
        
        String placa_old = request.getParameter("placa_edit_old");        
        
        //informacion del gps
        String id_relacion_hd_gps_vh_old = request.getParameter("id_rghv_edit_old");         
        String id_gps_old = request.getParameter("id_gps_edit_old");                        
        String id_hardware_old = request.getParameter("id_hard_edit_old");                 
        String numero_celular_old = request.getParameter("num_celular_old");         
        String id_sim_old = request.getParameter("id_sim_edit_old");         
        String id_operador_old = request.getParameter("id_op_edit");
        
        /**************************************************************/
        String id_rtm_actual = request.getParameter("id_rtm");        
        String cod_rtm_actual = request.getParameter("cod_rtm");                
        String fk_cda_rtm_actual = request.getParameter("fk_cda_rtm");  
        String fv_rtm_actual = request.getParameter("fvto_rtm");
        /**************************************************************/
        String id_to_actual = request.getParameter("id_to");        
        String cod_to_actual = request.getParameter("cod_to");                
        String fk_ce_to_actual = request.getParameter("fk_ce_to");    
        String fv_to_actual = request.getParameter("fvto_to");
        
        
        /********************DATOS PARA ACTUALIZAR DEL VEHICULO*******************************/
        String placa_new = request.getParameter("placa_edit_new");        
        String numeroInterno = request.getParameter("numero_interno_edit");           
        String fk_propietario = request.getParameter("propietario_edit");                   
        String capacidad_pasajeros = request.getParameter("cant_pasajeros_edit");          
        String fk_grupo = request.getParameter("grupo_edit");
        /********************DATOS PARA ACTUALIZAR DESPACHO*******************************/
        String[] diasPicoYPlaca = request.getParameterValues("dia_pico_placa_edit");          
        String[] diasDescanso = request.getParameterValues("dias_descanso_edit");          
                
        /******************DATOS PARA ACTUALIZAR GPS****************************/        
        String id_gps_new = request.getParameter("codigo_gps_edit");        
        String id_hardware_new = request.getParameter("hardware_edit");           
        String id_marca_gps_new = request.getParameter("marca_gps_edit");
        String id_modelo_gps_new = request.getParameter("modelo_gps_edit");
        String id_sim_new = request.getParameter("id_sim_edit");        
        String numero_celular_new = request.getParameter("numero_celular_edit");         
        String id_operador_new = request.getParameter("operador_celular_edit");
        /*DATOS PARA ACTUALIZAR LA REVISION TECNICO MECANICA*/
        String cod_rtm = request.getParameter("cod_rtm_edit");
        String fk_cda_rtm = request.getParameter("id_cda_rtm_edit");
        String fv_rtm = request.getParameter("fv_rtm_edit");
        String obs_rtm = request.getParameter("obs_edit");
        /*DATOS PARA ACTUALIZAR LA TARJETA DE OPERACION*/
        String cod_to = request.getParameter("cod_to_edit");                
        String mod_to = request.getParameter("mod_to_edit");                        
        String fk_ce_to = request.getParameter("fk_ce_to_edit");        
        String fv_to = request.getParameter("fv_to_edit");
        String obs_to = request.getParameter("obs_to_edit");
        /*************VARIABLES DE CONTROL***********************************************************/
        String eliminarGps;
        String eliminarSim;
        /******************INFORMACION DE VARIABLES*************************************************/
        String diasPyP="";
        String diasD="";        
        HttpSession session = request.getSession();          
        Usuario user = (Usuario) session.getAttribute("login");                
        Movil m = new Movil();
        PropietarioVehiculo pv = new PropietarioVehiculo();
        GrupoMovil gm = new GrupoMovil();
        TarjetaSim encontradoSim = new TarjetaSim();
        Operador op= new Operador();        
        String url="/app/movil/unMovil.jsp";       
        int idGps=0;        
        int retornoNuevoGps=0;
        int retorno_vh=0;
        SelectBD select = null;   
        //actualiza vehiculo
        try {
             if (!id_vh.equals("")) {
                m.setId(Integer.parseInt(id_vh));
            }
            m.setFkEmpresa(user.getIdempresa()); 
            if (!placa_new.equals("")) {
                m.setPlaca(placa_new);
            }
            if (!capacidad_pasajeros.equals("")) {
                m.setCapacidad(Integer.parseInt(capacidad_pasajeros));
            }            
            if (!numeroInterno.equals("")) {
                m.setNumeroInterno(numeroInterno);
            }
            /************************************************************/            
            if (diasPicoYPlaca != null) {
                for (String diasPicoYPlaca1 : diasPicoYPlaca) {
                diasPyP += diasPicoYPlaca1;
                diasPyP += ",";
                }
                m.setDiaPicoplaca(diasPyP.substring(0, (diasPyP.length() - 1) ) );
            }
            else{
                m.setDiaPicoplaca("0");
            }
             /*************************************************/
            if (diasDescanso != null) {
                for (String diasDescanso1 : diasDescanso) {
                    diasD += diasDescanso1;
                    diasD += ",";
                }
                m.setDiaDescanso(diasD.substring(0, (diasD.length() - 1) ) );
            }
            else{
                m.setDiaDescanso("0");
            }            
            m.setEstado(new Short("1"));        
            /*SE ACTUALIZA EL REGISTRO MOVIL*/
            retorno_vh = MovilBD.update(m);  
            /* ACTUALIZAR RELACION PROPIETARIO - VEHICULO */
            if (retorno_vh > 0) {                
            
             try {                    
                    if ( Integer.parseInt(fk_propietario) > 0){
                        PropietarioVehiculo relacionExistente = PropietarioBD.existRelationShipByCar(m);
                        if (relacionExistente != null) {
                            /*ACTUALIZA EL PROPIETARIO EN LA RELACION EXISTENTE*/
                            pv.setId(relacionExistente.getId());
                            pv.setFk_propietario(Integer.parseInt(fk_propietario));
                            if (PropietarioBD.updateOneRelationshipOwnerCarByid(pv) <= 0) {
                                throw new NullPointerException();
                            }
                            else{
                                System.out.println("Se actualizo el propietario");
                            }
                        }/*RELACION NULA, ENTONCES LA CREA UNA NUEVA*/
                        else{
                            pv.setFk_vehiculo(m.getId());
                            pv.setFk_propietario(Integer.parseInt(fk_propietario));
                            if (PropietarioBD.insertOneRelationshipOwnerCar(pv) <= 0){
                                    throw new NullPointerException();
                            }else{
                                System.out.println("Se adiciono una nueva relacion propietario");
                            }
                        }
                    }//EL ID PROPETARIO EN BLANCO
                    else{throw new NullPointerException();}
                    //FIN SI VACIO FK PROPIETARIO
                    } catch (NumberFormatException | NullPointerException e) {System.err.println(e.getMessage());}           
            
             
               
                  /* ACTUALIZAR RELACION GRUPO - VEHICULO*/
                try {
                    if (!fk_grupo.equals("")) {
                        GrupoMovil relacionExitente = GrupoMovilBD.existRelationShipGroupMobileByCar(m);
                                if (relacionExitente != null) {
                                    gm.setFkGrupo(Integer.parseInt(fk_grupo));
                                    gm.setId(relacionExitente.getId());
                                    if (GrupoMovilBD.updateRelationShipGroupCarById(gm) <= 0) {
                                        throw new NullPointerException();}
                                 }else{
                                    gm.setFkGrupo(Integer.parseInt(fk_grupo));
                                    gm.setFkVehiculo(m.getId());                                    
                                    gm.setEstado(1);                                    
                                        if (GrupoMovilBD.insertOneRelationShipGroup(gm) <= 0) {
                                            throw new NullPointerException();}
                                }//
                    }else{throw new NullPointerException();}                    
                } catch (NumberFormatException | ExisteRegistroBD | NullPointerException e) {System.err.println(e.getMessage());}
                
                
                
                
                // ACTUALIZAR LA RELACION GPS - VEHICULO - HARDWARE                
                try {
                        RelationShipHardwareGpsCar rhgv = new RelationShipHardwareGpsCar();                                              
                        RelationShipHardwareGpsCar RelacionHardwareGpsVehiculoEncontradaActiva = null;
                         //1. INGRESAMOS LA INFORMACION DE LA NUEVA RELACION
                                rhgv.setId(0);
                                if (!id_vh.equals("")) {
                                    rhgv.setFk_vehiculo(Integer.parseInt(id_vh));
                                } else {
                                    throw new Exception();
                                }

                                //SE CREA EL GPS SINO EXISTE
                                if (!id_gps_new.equals("")) {
                                    rhgv.setFk_gps(id_gps_new);
                                    //1. SE PREGUNTA SI EXISTE EL GPS
                                    if (GpsInventarioBD.existeGpsById(rhgv)) {
                                        rhgv.setFk_gps(id_gps_new);
                                    } else {//SINO SE REGISTRA
                                        if (!id_gps_new.contains("0")) {
                                            GpsRegistrado nuevoGps = new GpsRegistrado();
                                            nuevoGps.setId(id_gps_new);
                                            nuevoGps.setImei("0");
                                            nuevoGps.setFk_marca(Integer.parseInt(id_marca_gps_new));
                                            nuevoGps.setFk_modelo(Integer.parseInt(id_modelo_gps_new));
                                            if (GpsInventarioBD.insertOneGPS(nuevoGps) > 0) {
                                                System.out.println("SE REGISTRA UN GPS POR PRIMERA VEZ");
                                                rhgv.setFk_gps(id_gps_new);
                                            }
                                        }
                                    }
                                } else {
                                    rhgv.setFk_gps("0");
                                }

                                if (!id_hardware_new.equals("")) {
                                    rhgv.setFk_hardware(Integer.parseInt(id_hardware_new));
                                } else {
                                    id_hardware_new="0";
                                    rhgv.setFk_hardware(0);
                                }

                                //SE CREA LA TARJETA SIM SINO EXISTE
                                if (!id_sim_new.equals("")) {
                                    rhgv.setFk_sim(Integer.parseInt(id_sim_new));
                                    //1. SE PREGUNTA SI EXISTE LA SIM CARD
                                    if (TarjetaSimBD.existeSimCardById1(rhgv)) {
                                        rhgv.setFk_sim(Integer.parseInt(id_sim_new));
                                        
                                    } else//SINO SE REGISTRA
                                    if (Integer.parseInt(id_sim_new) > 0) {//PODRIA SER QUE EL ID FUERA CERO
                                        TarjetaSim ts = new TarjetaSim();
                                        ts.setImei("0");
                                        ts.setNum_cel(numero_celular_new);
                                        ts.setFk_operador(Integer.parseInt(id_operador_new));
                                        int id_nueva_tarjeta_sim = TarjetaSimBD.insertOneSimCardReturnId(ts);
                                        if (id_nueva_tarjeta_sim > 0) {
                                            System.out.println("SE REGISTRA UNA SIM POR PRIMERA VEZ");
                                            rhgv.setFk_sim(id_nueva_tarjeta_sim);
                                            rhgv.setNumero_celular(numero_celular_new);
                                        }
                                    }
                                    } else {
                                        id_sim_new ="0";
                                        rhgv.setFk_sim(0);
                                    }
                                
                                    if (!numero_celular_new.equals("")) {
                                            rhgv.setNumero_celular(numero_celular_new);
                                            if (TarjetaSimBD.existeSimCardByNCel1(rhgv)) {
                                                rhgv.setNumero_celular(numero_celular_new);
                                            }else{
                                                if (!numero_celular_new.contains("0")) {
                                                    TarjetaSim ts = new TarjetaSim();
                                                    ts.setImei("0");
                                                    ts.setNum_cel(numero_celular_new);
                                                    ts.setFk_operador(Integer.parseInt(id_operador_new));
                                                    int id_nueva_tarjeta_sim = TarjetaSimBD.insertOneSimCardReturnId(ts);
                                                    if (id_nueva_tarjeta_sim > 0) {
                                                        System.out.println("SE REGISTRA UNA SIM POR PRIMERA VEZ CON EL NUMERO CELULAR");
                                                        rhgv.setFk_sim(id_nueva_tarjeta_sim);
                                                        rhgv.setNumero_celular(numero_celular_new);
                                                    }
                                                }                                                
                                            }//
                                    } else {
                                         rhgv.setNumero_celular("0");
                                    }
                                    
                                    if (!id_operador_new.equals("")) {
                                        rhgv.setFk_operador(Integer.parseInt(id_operador_new));
                                    } else {
                                         id_operador_new ="0";
                                         rhgv.setFk_operador(0);
                                    }
                                
                        //SE BUSCA RELACION QUE TENGA EL VEHICULO
                        if (!id_vh.equals("")){                            
                            rhgv.setFk_vehiculo(Integer.parseInt(id_vh));
                            RelacionHardwareGpsVehiculoEncontradaActiva = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarOnByFkCar(rhgv);                            
                        }else{throw  new Exception();}                        
                        //RELACION ENTRE EL GPS Y VEHICULO ---> VERIFICA SI LA RELACION ENCONTRADA EXISTE Y ESTA ACTIVA
                        if (RelacionHardwareGpsVehiculoEncontradaActiva != null) {
                        //VERIFICA SI LOS DATOS DE LA RELACION ENCONTRADA SON IGUALES EN GPS, HARDWARE Y SIM;  SI ES ASI NO HACE NINGUN CAMBIO                            
                            if ((RelacionHardwareGpsVehiculoEncontradaActiva.getFk_vehiculo() == Integer.parseInt(id_vh)) 
                                    && (RelacionHardwareGpsVehiculoEncontradaActiva.getFk_gps().equals(id_gps_new))
                                    && (RelacionHardwareGpsVehiculoEncontradaActiva.getFk_hardware() == Integer.parseInt(id_hardware_new)) 
                                    && (RelacionHardwareGpsVehiculoEncontradaActiva.getNumero_celular().equals(numero_celular_new))) {
                            } else {
                                //EN ESTE CASO DEBE CREAR UNA NUEVA RELACION PUESTO QUE YA NO ES LA MISMA POR ALGUN CAMBIO
                                //1. DESACTIVAMOS LA RELACION ACTUAL UTLIZANDO EL ID DEL VEHICULO Y LIBERAMOS EL GPS
                                rhgv.setFk_vehiculo(Integer.parseInt(id_vh));
                                rhgv.setEstado(0);
                                if (RelationShipHardwareGpsCarBD.updateOneRelationshipStateByIdCar(rhgv) > 0) {
                                } else {
                                        System.out.println("NO SE ACTUALIZA LA RELACION H - G - V");
                                }
                               
                                //2.SE ADICIONA NUEVAMENTE LA INFORMACION   
                                //if ((!id_gps_new.equals("")) && (!id_hardware_new.equals("0")) && (!numero_celular_new.equals(""))) {
                                if ( (!id_gps_new.equals("")) && (!id_hardware_new.equals("0")) && (!id_operador_new.equals("0"))) {
                                    rhgv.setFk_gps(id_gps_new);
                                    //3. SE CREA LA NUEVA RELACION 
                                    if (RelationShipHardwareGpsCarBD.insertRelationShipHdGpsVh(rhgv) > 0) {
                                        System.out.println("SE REGISTRA LA RELACION H - G - V ");
                                    } else {
                                        System.out.println("NO SE REGISTRA LA RELACION H - G - V");
                                    }
                                } else {
                                    rhgv.setFk_gps("0");
                                }
                                
                            }
                        } else {//SI LA RELACION NO EXISTE O ES PRIMERA VEZ QUE SE RELACIONA EL VEHICULO A UN HARDWARE Y GPS                                                                                                      
                                //1. SE INSERTA UNA NUEVA RELACION DE HARDWARE - GPS - VEHICULO
                                if (RelationShipHardwareGpsCarBD.insertRelationShipHdGpsVh(rhgv) > 0) {
                                    System.out.println("SE REGISTRA UNA NUEVA LA RELACION H - G - V POR PRIMERA VEZ");
                                } else {
                                    System.out.println("SE REGISTRA LA RELACION H - G - V");
                                }                                              
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }                                                
                                
               /**ACTUALIZA LA REVISION TECNICO MECANICA**/              
               try {
                    RevisionTMecanica rtm = new RevisionTMecanica();
                    //REVISION T MECANICA                    
                    if (!id_rtm_actual.equals("")) {
                        rtm.setId(Integer.parseInt(id_rtm_actual));
                    }else{
                        throw new NullPointerException();
                    }
                    if (!cod_rtm.equals("")) {
                        rtm.setCodigo(cod_rtm);
                    }else{
                        throw new NullPointerException();
                    }
                    if (!fk_cda_rtm.equals("")) {
                        rtm.setFk_centro_diag(Integer.parseInt(fk_cda_rtm));                        
                    }else{
                        throw new NullPointerException();
                    }                    
                    if (!fv_rtm.equals("")) {
                        rtm.setFecha_vencimiento(fv_rtm);
                    }                    
                    if (!obs_rtm.equals("")) {
                        rtm.setObservaciones(obs_rtm);                    
                    }
                    //VERIFICA SI EL CODIGO DE LA REVISION ES EL MISMO
                    if (cod_rtm.equals(cod_rtm_actual)) {//ACTUALIZA LA INFORMACION DE REVISION
                        int ro = RevisionTMecanicaBD.updateRevisionFull(rtm);
                        if (ro > 0) {
                            System.out.println("revision t m actualizada");
                        }
                    }else{//CREA UNA NUEVA REVISION Y RELACIONA NUEVAMENTE CON EL VEHICULO
                        rtm.setId(0);
                        int idRevisionTecnicoMecanica = RevisionTMecanicaBD.insertOneRevisionReturnId(rtm);
                        RelationShipRevisionTMVehiculo rrtm = new RelationShipRevisionTMVehiculo();                                                                        
                        if (!id_vh.equals("")) {
                            rrtm.setFk_vh(Integer.parseInt(id_vh));
                        }else{
                            throw new NullPointerException();
                        }
                        rrtm.setFk_revision(idRevisionTecnicoMecanica);
                        rrtm.setFecha_vencimiento(fv_rtm);
                        rrtm.setEstado(0);
                        int r0 = RelationShipRevisionVehiculoBD.updateOneRelationshipState(rrtm);
                        if (r0 > 0) {
                            int r1 = RelationShipRevisionVehiculoBD.insertOneRelationShipRevisionVehiculo(rrtm);
                            if (r1 > 0) {                                
                                System.out.println("Se crea una nueva relacion con revision");
                            }                        
                        }
                        
                    }
                    
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
               
               /**
                * 
                * ACTUALIZACION DE TARJETA DE OPERACION
                
                **/
                      
               try {
                    //TARJETA DE OPERACION
                    TarjetaOperacion to = new TarjetaOperacion();
                     if (!id_to_actual.equals("")) {
                        to.setId(Integer.parseInt(id_to_actual));
                    }else{
                        throw new NullPointerException();
                    } 
                    if (!cod_to.equals("")) {
                    to.setCodigo(cod_to);
                    }else{
                        throw new NullPointerException();
                    }        
                    
                    if (!mod_to.equals("")) {
                        to.setModelo(mod_to);
                    }
                    
                    if (!fk_ce_to.equals("")) {
                        to.setFk_centro_exp(Integer.parseInt(fk_ce_to));
                    }else{
                        throw new NullPointerException();
                    }        
                    
                    if (!fv_to.equals("")) {
                        to.setFecha_vencimiento(fv_to);
                    }        
                    
                    if (!obs_to.equals("")) {
                        to.setObservaciones(obs_to);
                    }
                    //VERIFICA SI EL CODIGO ES EL MISMO
                    if (cod_rtm.equals(cod_rtm_actual)) {//ACTUALIZA LA INFORMACION DE LA TARJETA
                        int r0 = TarjetaOperacionBD.updateTarjetaOperacionRecord(to);
                        if (r0 > 0) {
                           System.out.println("tarjeta operacion");                         
                        }    
                    }else{//CREA UNA NUEVA TARJETA DE OPERACION Y LA RELACIONA CON EL VEHICULO
                        int idTarjetaOperacion = TarjetaOperacionBD.insertDocumentosVehiculoReturnId(to);
                        RelationShipTarjetaOperacionVehiculo rrto = new RelationShipTarjetaOperacionVehiculo();
                        rrto.setFk_to(idTarjetaOperacion);                        
                        if (!id_vh.equals("")) {
                            rrto.setFk_vh(Integer.parseInt(id_vh));
                        }else{
                            throw new NullPointerException();
                        }                        
                        rrto.setFecha_vencimiento(fv_to);
                        rrto.setEstado(0);
                        int r0 = RelationShipTarjetaOperacionVehiculoBD.updateOneRelationshipState(rrto);
                        if (r0 > 1) {
                            int r1 = RelationShipTarjetaOperacionVehiculoBD.insertOneRelationShipTarjetaOperacion(rrto);
                            if (r1 >0) {
                                System.out.println("Se crea una nueva relacion con tarjeta de operacion");
                            }
                        }
                        
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
         
                   
                //  CARGAR DATOS DESPUES DE ACTUALIZAR               
                    select = new SelectBD(request);
                  switch (retorno_vh) {
                    case 0:
                        
                        session.setAttribute("select", select);
                        request.setAttribute("idInfo", 0);
                        request.setAttribute("msg", "No logr&oacute; actualizar el movil");                    
                        break;
                    case 1:
                    {                    
                        session.setAttribute("select", select);
                        request.setAttribute("idInfo", 1);
                        request.setAttribute("msg", "Se ha logrado actualizar la informacion del veh&iacute;culo");                    
                        //BUSCA EL MOVIL
                        Movil encontradoMovil = MovilBD.selectByOne(m);
                        if (encontradoMovil != null) {
                            System.out.println("Movil encontrado");
                        }
                        //BUSCA EL PROPIETARIO
                        PropietarioVehiculo relacionPropietarioVehiculoEncontrada = PropietarioBD.existRelationShipByCar(m);
                        if (relacionPropietarioVehiculoEncontrada != null) {
                            System.out.println("Propietario encontrado");
                        }
                        //BUSCA EL VEHICULO
                        GrupoVehiculo grupoMovilEncontrado = GrupoMovilBD.searchGroupByMovil(m);                        
                        if (grupoMovilEncontrado != null) {
                            System.out.println("Grupo encontrado");
                        }
                        //BUSCA LA RELACION ENTRE EL HARDWARE - GPS -VEHICULO
                            RelationShipHardwareGpsCar relacionHardwareGpsVehiculoEncontrada = RelationShipHardwareGpsCarBD.searchRelationShipGpsHardwareCarOnByFkCar(m);
                            GpsRegistrado GpsEncontrado = null;
                            GpsMarca marcaEncontrada = null;
                            GpsModelo modeloEncontrado = null;                            
                            TarjetaSim tarjetaSimEncontrada = null;
                            //verifica si la relacion existe
                        if (relacionHardwareGpsVehiculoEncontrada != null) {
                            //BUSCA EL GPS
                            GpsEncontrado = GpsInventarioBD.searchGPSById(relacionHardwareGpsVehiculoEncontrada);
                            if (GpsEncontrado != null) {
                                //BUSCA LA MARCA
                                marcaEncontrada = GpsMarcaBD.searchGPSById(GpsEncontrado);
                                if (marcaEncontrada != null) {
                                    System.out.println("Marca encontrado");
                                }
                                //BUSCA EL MODELO
                                modeloEncontrado = GpsModeloBD.selectOneModelOnlyById(GpsEncontrado);
                                if (modeloEncontrado != null) {
                                    System.out.println("Modelo encontrado");
                                }
                            }
                            tarjetaSimEncontrada = TarjetaSimBD.searchSimCardById1(relacionHardwareGpsVehiculoEncontrada);
                        }
                        
                        //BUSCA LA RELACION CON LA REVISION
                        RelationShipRevisionTMVehiculo relacionRevisionTMecanicaEncontrada = RelationShipRevisionVehiculoBD.searchRelationShipRevisionTMVehiculo(m);
                        RevisionTMecanica revisionTMecanicaEncontrada=null;
                        if (relacionRevisionTMecanicaEncontrada != null) {
                            revisionTMecanicaEncontrada = RevisionTMecanicaBD.selectRevisionTMecanica(relacionRevisionTMecanicaEncontrada);
                            if (revisionTMecanicaEncontrada != null) {
                                System.out.println("Revision encontrada");
                            }
                        }
                        //BUSCA LA RELACION TARJETA DE OPERACION                        
                        RelationShipTarjetaOperacionVehiculo relacionTarjetaOperacionVehiculoEncontrada = RelationShipTarjetaOperacionVehiculoBD.searchRelationShipTarjetaOperacionVehiculo(m);
                        TarjetaOperacion tarjetaOperacionEncontrada = null;
                        if (relacionTarjetaOperacionVehiculoEncontrada != null) {
                            tarjetaOperacionEncontrada = TarjetaOperacionBD.selectTarjetaOperacionVehiculoById(relacionTarjetaOperacionVehiculoEncontrada);
                            if (tarjetaOperacionEncontrada != null) {
                                System.out.println("Tarjeta encontrada");
                            }
                        }
                        
                        
                        if (encontradoMovil != null) {                            
                           session.setAttribute("movil", encontradoMovil);                            
                           session.setAttribute("rp", relacionPropietarioVehiculoEncontrada);
                           session.setAttribute("rg", grupoMovilEncontrado);                           
                           session.setAttribute("rhgv", relacionHardwareGpsVehiculoEncontrada);
                           session.setAttribute("gps", GpsEncontrado);
                           session.setAttribute("mca", marcaEncontrada);
                           session.setAttribute("mdl", modeloEncontrado);
                           session.setAttribute("sim", tarjetaSimEncontrada);
                           session.setAttribute("rtm", revisionTMecanicaEncontrada);
                           session.setAttribute("to", tarjetaOperacionEncontrada);
                        }                                            
                        break;
                        }
                    default:
                    {
                        break;
                    }
                   }
               
                       
            }else{//FIN SI ACTUALIZA MOVIL
                throw new NullPointerException();
            }//FIN ELSE ACTUALIZA MOVIL
                
          }catch (Exception e) {
            System.err.println(e.getMessage());
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el movil");
            url = "/app/movil/listadoMovil.jsp";
        }  
        return url;
    }
    
    /**FUNCION QUE PERMITE ELIMINAR UN CONDUCTOR**/ 
    public String eliminarMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url="";
        Movil m = new Movil();
        try 
        {            
            m.setEstado(Integer.parseInt( estado ));     
            m.setId(Integer.parseInt(id));        
        } catch (Exception e) {
            System.out.println(" ->> "+e.getMessage());
        }
        
        HttpSession session = request.getSession();
        
        
        try {
            int resultado = MovilBD.updateEstado(m);
            RelationShipHardwareGpsCar rhgv = new RelationShipHardwareGpsCar();
            rhgv.setFk_vehiculo(m.getId());
            int resultadoRelacion = RelationShipHardwareGpsCarBD.updateOneRelationshipStateByFkCarId(rhgv);            
            if (resultadoRelacion > 0) {
                System.out.println("se ha desactivado la relacion");
            }
                switch (resultado) {                    
                case 0:   
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar el movil ");
                    break;
                case 1:                        
                        SelectBD select = new SelectBD(request);
                        session.setAttribute("select", select);
                        url = "/app/movil/listadoMovil.jsp";                        
                        break;
                default:
                    break;
                }
               
               
        } catch (ExisteRegistroBD ex) {
            System.out.println("---> "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("---> "+ex.getMessage());
        }
         return url;
    }
    
   
    
    
    public String PlacaRepetida (HttpServletRequest request,
            HttpServletResponse response){
        
        String valor =  request.getParameter("placa");
        String url="";                   
        HttpSession session = request.getSession();
        
        Movil a = new Movil();
        if (!valor.equals("")) {
            a.setPlaca(valor.trim());
        }
        int repetido = MovilBD.placaRepetida(a);                    
        session.setAttribute("p", repetido);
        url = "/app/movil/pRepetida.jsp";        
        return url;
    }
    
    
    
}/*FIN CLASE*/

