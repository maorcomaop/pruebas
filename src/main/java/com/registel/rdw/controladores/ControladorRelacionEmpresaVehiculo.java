/**
 * Clase controlador  Relacion Empresa Vehiculo
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
import com.registel.rdw.datos.RelacionVehiculoEmpresaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.RelacionVehiculoEmpresa;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import org.apache.catalina.ant.ReloadTask;


public class ControladorRelacionEmpresaVehiculo extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    //private String lnk = "Para asignarle un nuevo , haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
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
        if (requestURI.endsWith("/guardarRelacionEmpresaVehiculo")) {
            try {
                url = guardarRelacionVehiculoEmpresa(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.getMessage());
            }
        } else if (requestURI.endsWith("/editarRelacionEmpresaVehiculo")) {
            try {
                url = editarRelacionVehiculoEmpresa(request, response);
            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.getMessage());
            }
        } else if (requestURI.endsWith("/verMasRelacionEmpresaVehiculo")) {
            url = verRelacionVehiculoEmpresa(request, response);        
        } 
        else if (requestURI.endsWith("/eliminarRelacionEmpresaVehiculo")) {
            url = eliminarRelacionVehiculoEmpresa(request, response);        
        } 
                
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String guardarRelacionVehiculoEmpresa (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
                        
        String vehiculo = request.getParameter("id_vehiculo");
        String empresa = request.getParameter("id_empresa");        
        
                
        RelacionVehiculoEmpresa c = new RelacionVehiculoEmpresa();
        
         try 
        {
            c.setIdEmpresa(Integer.parseInt(empresa));
            c.setIdVehiculo(Integer.parseInt(vehiculo));
            c.setEstado(new Short("1"));
        }
        catch (Exception e) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la relacion entre empresa y veh&iacute;culo");
            return "/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp";
        }
        
        
        HttpSession session = request.getSession();
        session.setAttribute("msg", ""); 
        session.setAttribute("idInfo", -1);
        try {
            if (RelacionVehiculoEmpresaBD.insert(c) == 1){
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", "La asociacion entre el vehiculo y conductor se estableci&oacute; correctamente.");
            }
            else 
            {
                SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", "No logr&oacute; establecer una relacion");                
            }
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return "/app/relacion_empresa_movil/nuevoRelacionEmpresaVehiculo.jsp";
    }    
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO DE USUARIO**/ 
    public String editarRelacionVehiculoEmpresa (HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        
        String url=""; 
        HttpSession session = request.getSession();  
        String idRelacionVehiculoEmpresa = request.getParameter("id_edit") ;
        String idEmpresa = request.getParameter("empresa") ;       
        String idVehiculo = request.getParameter("id_vehiculo") ;        
       
        RelacionVehiculoEmpresa c = new RelacionVehiculoEmpresa();
        
         try 
        {
            c.setIdRelacionVehiculoEmpresa(Integer.parseInt(idRelacionVehiculoEmpresa ));
            c.setIdEmpresa(Integer.parseInt(idEmpresa));
            c.setIdVehiculo(Integer.parseInt(idVehiculo));
            c.setEstado(new Short("1"));
        }
        catch (Exception e) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la relacion entre empresa y veh&iacute;culo");
            return "/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp";
        }
        
         SelectBD select =null;        
        try {
            int resultado = RelacionVehiculoEmpresaBD.updateRelationShip(c);
            switch (resultado) {                
                case 0:
                    {
                        select = new SelectBD(request);
                        session.setAttribute("select", select);
                        request.setAttribute("idInfo", 0);
                        request.setAttribute("msg", "No se logr&oacute; establecer la relacion entre un veh&iacute;culo y la empresa");                        
                        url = "/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp";
                        break;
                    }
                case 1:
                    {                        
                        select = new SelectBD(request);
                        session.setAttribute("select", select);
                        request.setAttribute("idInfo", 1);                        
                        RelacionVehiculoEmpresa relacionEncontrada = RelacionVehiculoEmpresaBD.selectByOneNew(c);
                        if (relacionEncontrada != null) 
                        {
                            session.setAttribute("relacionVehiculoEmpresa", relacionEncontrada);                            
                            request.setAttribute("msg", "La asociacion entre el vehiculo y empresa se estable&oacute; correctamente.");
                        }                        
                        url = "/app/relacion_empresa_movil/nuevoRelacionEmpresaVehiculo.jsp";
                        break;
                    }          
                
                default:
                    break;
            }
            
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionEmpresaVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
    
            
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verRelacionVehiculoEmpresa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        RelacionVehiculoEmpresa c = new RelacionVehiculoEmpresa();
        c.setIdRelacionVehiculoEmpresa(Integer.parseInt(id));        
        HttpSession session = request.getSession();
        //session.removeAttribute("msg");
        //session.removeAttribute("idInfo");
        try {
                RelacionVehiculoEmpresa relacionEncontrada = RelacionVehiculoEmpresaBD.selectByOne(c);
               if (relacionEncontrada != null) {
                session.setAttribute("relacionVehiculoEmpresa", relacionEncontrada);
                
                url = "/app/relacion_empresa_movil/unaRelacionEmpresaConductor.jsp";
            } else {
                url = "/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorRelacionEmpresaVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
    
    
    
    
    /**FUNCION QUE PERMITE ELIMINAR UNA CATEGORIA**/ 
    public String eliminarRelacionVehiculoEmpresa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String estado =  request.getParameter("estado");                
        String id =  request.getParameter("id");
        String url = "/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp";  
        
        RelacionVehiculoEmpresa c = new RelacionVehiculoEmpresa();
        c.setIdRelacionVehiculoEmpresa(Integer.parseInt(id));
        c.setEstado(Integer.parseInt( estado ));     
        
        HttpSession session = request.getSession();
        
        try {
                RelacionVehiculoEmpresaBD.updateEstado(c);   
                 SelectBD select = new SelectBD(request);
                session.setAttribute("select", select);                
                                      
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorCategoriasDescuento.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
}
