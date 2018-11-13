/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.CategoriaLicenciaBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.CategoriaLicencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Richard Mejia
 * 
 * @date 10/07/2018
 */
public class ControladorCategoriaLicencia extends HttpServlet {
    
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private static final String ESTADO = "estado";
    
    private static final String GUARDAR_CATEGORIA = "/guardarCategoriaLicencia";
    private static final String VER_CATEGORIA = "/verCategoriaLicencia";
    private static final String EDITAR_CATEGORIA = "/editarCategoriaLicencia";
    private static final String CAMBIAR_ESTADO_CATEGORIA = "/cambiarEstadoCategoriaLicencia";
    
    private static final String NUEVA_CATEGORIA_LICENCIA_JSP = "/app/licencia_conduccion/nuevaCategoriaLicencia.jsp";
    private static final String VER_CATEGORIA_LICENCIA_JSP = "/app/licencia_conduccion/verCategoriaLicencia.jsp";
    private static final String LISTADO_CATEGORIA_LICENCIA_JSP = "/app/licencia_conduccion/listadoCategoriaLicencia.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente.";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro.";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorCategoria</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorCategoria at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        
       if (requestUri == null || requestUri.isEmpty()) {
            return;
        }
       
        String url = "";
        
        if (requestUri.endsWith(GUARDAR_CATEGORIA)) {
            url = guardarCategoriaLicencia(request);
        } else if (requestUri.endsWith(VER_CATEGORIA)) {
            url = verCategoriaLicencia(request);
        } else if (requestUri.endsWith(EDITAR_CATEGORIA)) {
            url = editarCategoriaLicencia(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_CATEGORIA)) {
            url = cambiarEstadoCategoriaLicencia(request);
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * @author Richard Mejia
     * @date 11/07/2018
     * Método encargado de guardar una nueva categoria de licencias.
     * @param request servlet request
     * @return 
     */
    public String guardarCategoriaLicencia(HttpServletRequest request) {
        try {
            String nombre = request.getParameter(NOMBRE);
            String descripcion = request.getParameter(DESCRIPCION);
            CategoriaLicencia categoriaLicencia = new CategoriaLicencia();
            categoriaLicencia.setNombre(nombre);
            categoriaLicencia.setDescripcion(descripcion);
            categoriaLicencia.setEstado(1);

            if (validarCategoriaLicencia(categoriaLicencia)) {
                boolean resultado = CategoriaLicenciaBD.insert(categoriaLicencia);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            }
        } catch (Exception e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return NUEVA_CATEGORIA_LICENCIA_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 11/07/2018
     * Método encargado de consultar una categoria para poder editarla.
     * @param request servlet request
     * @return 
     */
    public String verCategoriaLicencia(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter("id");
            CategoriaLicencia categoriaLicencia = new CategoriaLicencia();
            categoriaLicencia.setId(Long.parseLong(id));
            SelectBD select = new SelectBD(request);
            CategoriaLicencia categoriaEncontrada = CategoriaLicenciaBD.selectByOne(categoriaLicencia);

            if (categoriaEncontrada != null) {
                request.getSession().setAttribute("categoriaLicencia", categoriaEncontrada);
                request.getSession().setAttribute("select", select);
                url = VER_CATEGORIA_LICENCIA_JSP;
            } else {
                url = LISTADO_CATEGORIA_LICENCIA_JSP;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return url;
    }
    
    /**
     * @author Richard Mejia
     * @date 11/07/2018
     * Método encargardo de editar una categoria de licencia.
     * @param request servlet request
     * @return 
     */
    public String editarCategoriaLicencia(HttpServletRequest request) {
        try {
            String id = request.getParameter("id_edit");
            String nombre = request.getParameter(NOMBRE);
            String descripcion = request.getParameter(DESCRIPCION);
            CategoriaLicencia categoriaLicencia = new CategoriaLicencia();
            categoriaLicencia.setId(Long.parseLong(id));
            categoriaLicencia.setNombre(nombre);
            categoriaLicencia.setDescripcion(descripcion);
            categoriaLicencia.setEstado(1);

            if (validarCategoriaLicencia(categoriaLicencia)) {
                boolean resultado = CategoriaLicenciaBD.update(categoriaLicencia);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("categoriaLicencia", categoriaLicencia);
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", MENSAJE_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return VER_CATEGORIA_LICENCIA_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 11/07/2018
     * Método que se encarga de cambiar el estado de un categoria licencia.
     * @param request servlet request
     * @return 
     */
    public String cambiarEstadoCategoriaLicencia(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String estado = request.getParameter(ESTADO);
            CategoriaLicencia categoriaLicencia = new CategoriaLicencia();
            categoriaLicencia.setId(Long.parseLong(id));
            categoriaLicencia.setEstado(Integer.parseInt(estado));
            boolean resultado = CategoriaLicenciaBD.updateEstado(categoriaLicencia);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", MENSAJE_EXITO);
                request.setAttribute("categoriaLicencia", categoriaLicencia);
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return LISTADO_CATEGORIA_LICENCIA_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 11/07/2018
     * Método encargado de verificar que los campos básico de una categoria de licencia
     * tengan valores válidos para ser guardados.
     * @param categoriaLicencia Objeto del tipo categoria licencia que se va a validar.
     * @return 
     */
    private boolean validarCategoriaLicencia(CategoriaLicencia categoriaLicencia) {
        if (categoriaLicencia != null) {
            if (categoriaLicencia.getNombre() != null) {
                if (categoriaLicencia.getEstado() == 0 || categoriaLicencia.getEstado() == 1) { 
                    return true;
                }
            }
        }
        
        return false;
    }
    
}
