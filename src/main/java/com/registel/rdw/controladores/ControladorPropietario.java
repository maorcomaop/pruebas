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
import com.registel.rdw.datos.PropietarioBD;
import com.registel.rdw.logica.Propietario;
import com.registel.rdw.logica.PropietarioVehiculo;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.MovilBD;
import com.registel.rdw.logica.Movil;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/*COMENTARIO*/
public class ControladorPropietario extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultarPropietario")){
            url = propietarios(request, response);
        }
        else if (requestURI.endsWith("/consultarPerfilPropietario")) {
            url = obtenerPerfilPropietario(request, response);
        }
        else if (requestURI.endsWith("/consultarVehiculosPropietario")) {
            url = vehiculosPorPropietario(request, response);
        }
        else if (requestURI.endsWith("/consultarVehiculos")) {
            url = vehiculos(request, response);
        }
        else if (requestURI.endsWith("/guardarPropietarioVehiculos")) {
            url = guardarVehiculosAPropietario(request, response);
        }
        else if (requestURI.endsWith("/desactivarPropietarioVehiculo")) {
            url = desactivarPropietarioVehiculo(request, response);
        }
        else if (requestURI.endsWith("/adicionardVehiculosPropietario")) {
            url = addVehiculosPorPropietario(request, response);
        }
        else if (requestURI.endsWith("/guardarAddVehiculosPropietario")) {
            url = saveAddVehiculosPorPropietario(request, response);
        }
        else if (requestURI.endsWith("/consultarPropietarioDefecto")) {
            url = obtenerPropietarioPorDefecto(request, response);
        }
        
        
        
        
        //response.sendRedirect("login.jsp");        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
     public String obtenerPerfilPropietario (HttpServletRequest request,
            HttpServletResponse response) {
        String url="";        
        String perfil =  request.getParameter("perfil");
        HttpSession session = request.getSession(); 
        Propietario p = new Propietario();
        p.setPerfil(perfil);
        Perfil retorno = PropietarioBD.selectProfileOwner(p);
        session.setAttribute("perfil", retorno);
        url = "/app/movil/perfil.jsp";        
         return url;
    }
     
     public String obtenerPropietarioPorDefecto (HttpServletRequest request,
            HttpServletResponse response) {
        String url="";        
        String d =  request.getParameter("nombre");
        HttpSession session = request.getSession(); 
        Propietario p = new Propietario();
        p.setPerfil(d);
        Usuario retorno = PropietarioBD.selectOwnerForDefault(p);
        session.setAttribute("p", retorno);
        url = "/app/movil/id_pro.jsp";        
         return url;
    }
     
    public String propietarios (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id_perfil");
        String url="";                   
        Propietario p = new Propietario();
        p.setFk_perfil(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = PropietarioBD.selectOwnerAll(p);                   
                session.setAttribute("propietario", retorno);                
                url = "/app/propietario/propietario.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }
    
     public String guardarVehiculosAPropietario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("propietario");
        String[] Vehiculos = request.getParameterValues("vehiculo");      
        String url="";               
        SelectBD select =null;
        HttpSession session = request.getSession();
        int idVehiculos[] = null;
        PropietarioVehiculo pv= new PropietarioVehiculo();
         try {
                pv.setFk_propietario(Integer.parseInt(id));
                idVehiculos =new int[Vehiculos.length];
                for (int i = 0; i < Vehiculos.length; i++) 
                {
                    idVehiculos[i] = Integer.parseInt(Vehiculos[i]);
                }
                pv.setVehiculos(idVehiculos);
                
         } catch (Exception e) {
             System.err.println(e.getMessage());
              select = new SelectBD(request);
              session.setAttribute("select", select);                    
              request.setAttribute("idInfo", 2);
              request.setAttribute("msg", "Se han asociado los vehiculos al propietario");
              url = "/app/propietario/nuevoPropietario.jsp";
         }
        
        int retorno = PropietarioBD.insertRelationsShipOwnerCar(pv);
         
         try {
             switch (retorno) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No se logr&oacute asociar los vehiculos al propietario");
                    request.setAttribute("idInfo", 2);
                    url = "/app/propietario/listadoPropietario.jsp";
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se han asociado los vehiculos al propietario");
                    url = "/app/propietario/nuevoPropietario.jsp";
                    break;                
                default:
                    break;
            }   
         } catch (Exception e) {
              System.err.println(e.getMessage());
         }
         return url;
    }
    
    public String vehiculos (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        HttpSession session = request.getSession();
        ArrayList<Movil> retorno = PropietarioBD.selectCar();
        session.setAttribute("vehiculos", retorno);
        url = "/app/propietario/vehiculo.jsp";                       
         return url;
    }
    
   public String vehiculosPorPropietario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        HttpSession session = request.getSession();
        PropietarioVehiculo p= new PropietarioVehiculo();
        p.setFk_propietario(Integer.parseInt(id));
        ArrayList<PropietarioVehiculo> retorno = PropietarioBD.selectRelationsShipOwnerCar(p);
        session.setAttribute("vehiculos", retorno);
        url = "/app/propietario/listadoVehiculosPorPropietario.jsp";                       
         return url;
    }
    
   public String addVehiculosPorPropietario (HttpServletRequest request,
            HttpServletResponse response) {
        String id_propietario =  request.getParameter("id_propietario");
        HttpSession session = request.getSession();
        session.setAttribute("id", id_propietario);
        String url = "/app/propietario/addRelacionPropietario.jsp";                       
         return url;
    }
   
   public String saveAddVehiculosPorPropietario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";                   
        String id_propietario =  request.getParameter("id_p");
        String id_vehiculo =  request.getParameter("id_vh");
        SelectBD select =null;     
        
        HttpSession session = request.getSession();
        PropietarioVehiculo p= new PropietarioVehiculo();
        p.setFk_propietario(Integer.parseInt(id_propietario));
        p.setFk_vehiculo(Integer.parseInt(id_vehiculo));
        
        select = new SelectBD(request);
        int retorno = PropietarioBD.insertOneRelationshipOwnerCar(p); 
       switch (retorno) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No se logr&oacute asociar los vehiculos al propietario");
                    request.setAttribute("idInfo", 2);
                    url = "/app/propietario/addRelacionPropietario.jsp"; 
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha asociado un nuevo veh&iacute;culos al propietario");
                    url = "/app/propietario/addRelacionPropietario.jsp"; 
                    break;                
                default:
                    break;
            }           
         return url;
    }
   
   public String desactivarPropietarioVehiculo (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        PropietarioVehiculo p = new PropietarioVehiculo();
        HttpSession session = request.getSession();
        SelectBD select =null;         
        
        p.setId(Integer.parseInt(id));        
        p.setActiva(0);
        select = new SelectBD(request);
        int retorno = PropietarioBD.updateEstateOneRelationshipOwnerCar(p);
        session.setAttribute("r", retorno);                
        session.setAttribute("select", select);
        url = "/app/propietario/eliminarRelacionPropietario.jsp";
         return url;
    }
}
