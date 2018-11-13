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
import com.registel.rdw.datos.ConductorBD;
import com.registel.rdw.datos.LicenciaBD;
import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.ServicioLocal;
import com.registel.rdw.logica.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import org.json.JSONException;
import org.json.JSONObject;


/*COMENTARIO*/
public class ControladorLicencia extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultarLicenciaRemota")){
            url = consultaLicenciaRemota(request, response);
        }
        else if (requestURI.endsWith("/consultarLicenciaLocal")){
            url = consultaLicenciaLocal(request, response);
        } 
        else if (requestURI.endsWith("/consultarEmpresaInicio")){
            url = consultaEmpresa(request, response);
        }
        else if (requestURI.endsWith("/asignarLicencia")){
              url = guardarLicencia(request, response);
        } 
        else if (requestURI.endsWith("/eliminarLicencia")){} 
        else if (requestURI.endsWith("/peticionLicencia")){} 
        else if (requestURI.endsWith("/consultaServicio")) {            
                url = consultaServiciosRemoto(request, response);
        }
        else if (requestURI.endsWith("/vencimientoLicencia")) {            
            try {
                url = venceLicencia(request, response);
            } catch (ExisteRegistroBD ex) {
                System.err.println(ex.getMessage());
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        //response.sendRedirect("login.jsp");        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    /*guarda la licencia en la base de datos local*/
     public String guardarLicencia (HttpServletRequest request,
            HttpServletResponse response) {
                        
                
        String fk_tipo_licencia = request.getParameter("fk_tipo_licencia");        
        String clave = request.getParameter("licencia");
        String numero_documento = request.getParameter("num_doc");
        String nombre_cliente = request.getParameter("nom_cliente");        
        
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("login");                           
        ServicioLocal c = new ServicioLocal();
        String url="";
        try
        {            
            c.setFk_tipo_clave(Integer.parseInt(fk_tipo_licencia));
            c.setClave(clave);
            c.setNum_doc(numero_documento);
            c.setNom_cliente(nombre_cliente);            
        }
        catch (Exception exx) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar la licencia");           
        }        
        
        SelectBD select = null;                               
        int resultado = LicenciaBD.insertKeyLocal(c);
             switch (resultado) {
                case 0:                    
                    request.setAttribute("idInfo", 2);                                        
                    url="/app/licencia/asignarClave.jsp";
                    break;                
                case 1:                                        
                    request.setAttribute("idInfo", 1);                                        
                    url="/app/licencia/claveAsignada.jsp";
                    break;                
                default:
                    break;
            }  
        return url;
    }    

     
     /*consulta si el servicio se encuentra activo a través de un servicio web*/
      public String consultaServiciosRemoto (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id_cliente =  request.getParameter("fk_cliente");
        String id_producto =  request.getParameter("fk_producto");        
        String clave =  request.getParameter("clave");        
        String id_servicio =  request.getParameter("id_servicio");        
        String doc_cliente =  request.getParameter("doc_cliente");        
        String nom_cliente =  request.getParameter("nom_cliente");        
        String url="";                
        HttpSession session = request.getSession();
        
        ServicioLocal c= new ServicioLocal();
        c.setNum_doc(doc_cliente);
        c.setNom_cliente(nom_cliente);
        //se conecta a internet para obtener los datos del servicio web 
        int retorno = LicenciaBD.consultServiciosPorCliente(c);        
        session.setAttribute("respuesta", retorno);                        
        url = "/app/licencia/consultaServiciosPorCliente.jsp";                        
        return url;
    }  
      
    /*consulta la licencia en la base de datos remota a través de un servicio web al inicio de la app*/
     public String consultaLicenciaRemota (HttpServletRequest request,
            HttpServletResponse response) {
        
        String doc_cliente =  request.getParameter("doc_cliente");
        String nombre_cliente =  request.getParameter("nom_cliente");        
        String url="/app/licencia/consultaLicenciaRemota.jsp";                
        HttpSession session = request.getSession();
        ServicioLocal l = new ServicioLocal();
        l.setNum_doc(doc_cliente);
        l.setNom_cliente(nombre_cliente);        
        
        servicio.Servicio retorno = LicenciaBD.consultKeyRemote(l);         
        
        session.setAttribute("respuesta", retorno);
        session.setAttribute("lic", retorno);
        return url;
    }
     
     /*consulta la licencia en la base de datos local*/
     public String consultaLicenciaLocal (HttpServletRequest request,
            HttpServletResponse response) {
            String url="/app/licencia/consultaLicenciaLocal.jsp";
        try {
            String doc_cliente =  request.getParameter("doc_cliente");
            String nombre_cliente =  request.getParameter("nom_cliente");            
            HttpSession session = request.getSession();
            ServicioLocal l = new ServicioLocal();
            l.setNum_doc(doc_cliente);;
            l.setNom_cliente(nombre_cliente);
            ServicioLocal retorno;
            retorno = LicenciaBD.selectLocalKeyByEnterprise(l);
            session.setAttribute("respuesta", retorno);            
        } catch (ExisteRegistroBD | SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return url;
    }
     
     /*consulta la empresa en la base de datos local*/
     public String consultaEmpresa (HttpServletRequest request,
            HttpServletResponse response) {
            String url="/app/licencia/consultaEmpresa.jsp";        
            String id =  request.getParameter("id");            
            HttpSession session = request.getSession();
            Empresa e1= new Empresa();
            e1.setEstado(Integer.parseInt(id));
            Empresa retorno = EmpresaBD.getActive(e1);           
            session.setAttribute("EmpresaActiva", retorno);         
        return url;
    }
     
     
     public String venceLicencia (HttpServletRequest request,
            HttpServletResponse response) throws ExisteRegistroBD, SQLException {
        
         HttpSession session = request.getSession();
         Empresa e1 = (Empresa) session.getAttribute("EmpresaActiva");
          ServicioLocal s = new ServicioLocal();
         try {
                s.setNum_doc(e1.getNit());
                s.setNom_cliente(e1.getNombre());
            } catch (Exception e) {
                ArrayList<Empresa> empresa = EmpresaBD.get();
                e1 = empresa.get(0);
                s.setNum_doc(e1.getNit());
                s.setNom_cliente(e1.getNombre());
            }
            ServicioLocal sl = LicenciaBD.selectLocalKeyByEnterprise(s);
         
         //System.out.println("----> "+sl.getFecha_actualizacion()+" "+sl.getFecha_expiracion());
         session.setAttribute("f1", sl.getFecha_actualizacion());
         session.setAttribute("f2", sl.getFecha_expiracion());
         String url="/app/licencia/consultaLicenciaVence.jsp";                
        return url;
    }
     
}
