/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.SelectC;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lider_desarrollador
 */
public class SessionFilter implements Filter {
    
    private ArrayList<String> urlList;
    
    public void destroy() {}
    
    /*
        Verifica existencia de sesion valida para las paginas
        que lo necesitan.
    */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
        
        boolean allowedRequest = false;
        boolean alive = true;
        
        if (urlList.contains(url)) {
            allowedRequest = true;
        }        
        
        if (!allowedRequest) {
            //HttpSession session = request.getSession(false);
            //if (session == null || session.getAttribute("login") == null) {
            //    response.sendRedirect("/RDW1/index.jsp");
            //}
            if (!Session.alive(request)) {
                response.sendRedirect("/RDW1/index.jsp");                
                alive = false;
            }
        } 
        
        if (alive) {
            chain.doFilter(req, res);
            return;        
        }        
    }
    
    /*
        Crea lista de URLs que no necesitan de una sesion
        para desplegarse Ej. paginas de inicio, paginas de error,
        paginas de informacion, etc. 
    */
    public void init (FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        
        urlList = new ArrayList<String>();
        
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }
    }
}
