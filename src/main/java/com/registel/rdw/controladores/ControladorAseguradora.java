/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.AseguradoraBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Aseguradora;
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
 * @date 13/07/2018
 */
public class ControladorAseguradora extends HttpServlet {

    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String TELEFONO = "telefono";
    private static final String PAGINA_WEB = "paginaWeb";
    private static final String ESTADO = "estado";
    private static final String ID_EDIT = "id_edit";
    
    private static final String GUARDAR_ASEGURADORA = "/guardarAseguradora";
    private static final String VER_ASEGURADORA = "/verAseguradora";
    private static final String EDITAR_ASEGURADORA = "/editarAseguradora";
    private static final String CAMBIAR_ESTADO_ASEGURADORA = "/cambiarEstadoAseguradora";
    
    private static final String NUEVA_ASEGURADORA_JSP = "/app/polizas/nuevaAseguradora.jsp";
    private static final String VER_ASEGURADORA_JSP = "/app/polizas/verAseguradora.jsp";
    private static final String LISTADO_ASEGURADORA_JSP = "/app/polizas/listadoAseguradora.jsp";

    private static final String MENSAJE_EXITO = "Registro guardado correctamente.";
    private static final String MENSAJE_ERROR = "No fue posible guardar el registro.";
    
    private static final int VALOR_POSITIVO = 1;
    
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
            out.println("<title>Servlet ControladorAseguradora</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorAseguradora at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        if (requestUri.endsWith(GUARDAR_ASEGURADORA)) {
            url = guardarAseguradora(request);
        } else if (requestUri.endsWith(VER_ASEGURADORA)) {
            url = verAseguradora(request);
        } else if (requestUri.endsWith(EDITAR_ASEGURADORA)) {
            url = editarAseguradora(request);
        } else if (requestUri.endsWith(CAMBIAR_ESTADO_ASEGURADORA)) {
            url = cambiarEstadoAseguradora(request);
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
     * @date 13/07/2018
     * Método encargado de guardar una aseguradora.
     * @param request
     * @return 
     */
    public String guardarAseguradora(HttpServletRequest request) {
        try {
            String nombre = request.getParameter(NOMBRE);
            String telefono = request.getParameter(TELEFONO);
            String paginaWeb = request.getParameter(PAGINA_WEB);
            Aseguradora aseguradora = new Aseguradora();
            aseguradora.setNombre(nombre);
            aseguradora.setTelefono(telefono);
            aseguradora.setPaginaWeb(paginaWeb);
            aseguradora.setEstado(VALOR_POSITIVO);
            
            if (validarAseguradora(aseguradora)) {
                boolean resultado = AseguradoraBD.insert(aseguradora);

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
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return NUEVA_ASEGURADORA_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 13/07/2018
     * Método encargado de validar que los atributos obligatorios de una aseguradora
     * tengan valores apropiados.
     * @param aseguradora Objeto de tipo aseguradora que se va a validar.
     * @return 
     */
    private boolean validarAseguradora(Aseguradora aseguradora) {
        
        if (aseguradora == null) {
            return false;
        }
        
        if (aseguradora.getNombre() == null || aseguradora.getNombre().isEmpty() || 
                aseguradora.getNombre().length() > 50) {
            return false;
        }
            
        if (aseguradora.getTelefono() != null && aseguradora.getTelefono().length() > 50) {
            return false;
        }
        
        if (aseguradora.getPaginaWeb() != null && aseguradora.getPaginaWeb().length() > 250) {
            return false;
        }
        
        return !(aseguradora.getEstado() != 0 && aseguradora.getEstado() != 1);
    }

    /**
     * @author Richard Mejia
     * @date 13/07/2018
     * Método encargado de mostrar los detalles de una aseguradora.
     * @param request
     * @return 
     */
    public String verAseguradora(HttpServletRequest request) {
        String url = "";

        try {
            String id = request.getParameter(ID);
            Aseguradora aseguradora = new Aseguradora();
            aseguradora.setId(Long.parseLong(id));
            SelectBD select = new SelectBD(request);
            Aseguradora aseguradoraEncontrada = AseguradoraBD.selectByOne(aseguradora);
            
            if (aseguradoraEncontrada != null) {
                request.getSession().setAttribute("aseguradora", aseguradoraEncontrada);
                request.getSession().setAttribute("select", select);
                url = VER_ASEGURADORA_JSP;
            } else {
                url = LISTADO_ASEGURADORA_JSP;
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
     * @date 13/07/2018
     * Método encargado de editar los valores de una aseguradora.
     * @param request
     * @return 
     */
    public String editarAseguradora(HttpServletRequest request) {
        try {
            String id = request.getParameter(ID_EDIT);
            String nombre = request.getParameter(NOMBRE);
            String telefono = request.getParameter(TELEFONO);
            String paginaWeb = request.getParameter(PAGINA_WEB);
            Aseguradora aseguradora = new Aseguradora();
            aseguradora.setId(Long.parseLong(id));
            aseguradora.setNombre(nombre);
            aseguradora.setTelefono(telefono);
            aseguradora.setPaginaWeb(paginaWeb);
            aseguradora.setEstado(VALOR_POSITIVO);  

            if (validarAseguradora(aseguradora)) {
                boolean resultado = AseguradoraBD.update(aseguradora);

                if (resultado) {
                    SelectBD select = new SelectBD(request);
                    request.getSession().setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", MENSAJE_EXITO);
                    request.setAttribute("aseguradora", aseguradora);
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

        return VER_ASEGURADORA_JSP;
    }
    
    /**
     * @author Richard Mejia
     * @date 13/07/2018
     * Método encargado de cambiar el estado a una aseguradora.
     * @param request
     * @return 
     */
    public String cambiarEstadoAseguradora(HttpServletRequest request) {
        try {
            String id = request.getParameter(ID);
            String estado = request.getParameter(ESTADO);
            Aseguradora aseguradora = new Aseguradora();
            aseguradora.setId(Long.parseLong(id));
            aseguradora.setEstado(Integer.parseInt(estado));
            boolean resultado = AseguradoraBD.updateEstado(aseguradora);

            if (resultado) {
                SelectBD select = new SelectBD(request);
                request.getSession().setAttribute("select", select);
                request.setAttribute("idInfo", 1);
                request.setAttribute("msg", MENSAJE_EXITO);
                request.setAttribute("aseguradora", aseguradora);
            } else {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", MENSAJE_ERROR);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", MENSAJE_ERROR + "\n" + e.getMessage());
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }

        return LISTADO_ASEGURADORA_JSP;
    }

}
