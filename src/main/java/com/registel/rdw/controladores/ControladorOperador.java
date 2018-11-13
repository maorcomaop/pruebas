/**
 * Clase controlador Conductor
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.OperadorBD;
import com.registel.rdw.datos.TarjetaSimBD;
import com.registel.rdw.logica.Operador;
import com.registel.rdw.logica.TarjetaSim;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;


/*COMENTARIO*/
public class ControladorOperador extends HttpServlet {
    
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
        if (requestURI.endsWith("/consultaOperadores")){
            url = operadores(request, response);
        }       
        if (requestURI.endsWith("/consultaOperador")){
            url = unOperador(request, response);
        }
        if (requestURI.endsWith("/guardarOperador")){
            //url = operadores(request, response);
        } 
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
     public String operadores (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        Operador h = new Operador();
        h.setEstado(Integer.parseInt(id));
        HttpSession session = request.getSession();        
        try {
                ArrayList retorno = OperadorBD.selectOwnerAll(h);                   
                session.setAttribute("op", retorno);                
                url = "/app/operador/operadores.jsp";               
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return url;
    }  
     
     
      public String unOperador (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String url="";                   
        Operador h = new Operador();
        h.setId(Integer.parseInt(id));
        HttpSession session = request.getSession();
        TarjetaSim sim = new TarjetaSim();
        sim.setId(Integer.parseInt(id));
        TarjetaSim rsim = TarjetaSimBD.searchSimCardById(sim);
        Operador retorno = OperadorBD.searchOperatorBySimCard(rsim);
        session.setAttribute("ope", retorno);
        url = "/app/operador/operador.jsp";
        return url;
    }  
}
