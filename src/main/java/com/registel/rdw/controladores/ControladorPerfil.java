/**
 * Clase controlador Perfil
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.AccesoPerfilBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.PerfilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.AccesoAccesoPerfil;
import com.registel.rdw.logica.Modulo;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.SubModulo;
import com.registel.rdw.logica.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

public class ControladorPerfil extends HttpServlet {

    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";
    private static final String USR_PROP = "prop";

    private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        if (!Session.alive(request)) {
            getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request, response);
        }

        String requestURI = request.getRequestURI();

        String url = "/";
        if (requestURI.endsWith("/guardaEdicionPerfil")) {
            url = editarPerfil(request, response);
        } else if (requestURI.endsWith("/guardarPerfil")) {
            url = guardarPerfil(request, response);
        } else if (requestURI.endsWith("/editarPerfil")) {
            url = cargarPerfil(request, response, "edit");
        } else if (requestURI.endsWith("/verMasPerfil")) {
            url = cargarPerfil(request, response, "view");
        } else if (requestURI.endsWith("/nuevoPerfil")) {
            url = cargarPerfil(request, response, "create");
        } else if (requestURI.endsWith("/eliminarPerfil")) {
            url = eliminarPerfil(request, response);
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO
     *
     *
     * @param request
     * @param response
     * @return
     */
    public String guardarPerfil(HttpServletRequest request,
            HttpServletResponse response) {

        String nombre = request.getParameter("name_new");
        String descripcion = request.getParameter("description_new");

        Perfil p = new Perfil();
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setEstado(new Short("1"));

        HttpSession session = request.getSession();
        try {
            int resultado = PerfilBD.insert(p);

            SelectBD select = new SelectBD(request);
            switch (resultado) {
                case 0:

                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar el nuevo perfil");
                    break;
                case 1:

                    List<Integer> accesosId = new ArrayList<>();
                    List<String> parameterNamesList = Collections.list(request.getParameterNames());
                    for (String param : parameterNamesList) {
                        if (param.contains("acceso-")) {
                            accesosId.add(Integer.parseInt(request.getParameter(param)));
                        }
                    }
                    AccesoPerfilBD.editAccesosPerfil(accesosId, p.getId());

                    session.setAttribute("select", select);
                    request.setAttribute("msg", "Se ha creado un nuevo perfil");
                    break;
                default:
                    break;
            }

        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }
        return cargarPerfil(request, response, "view", p.getId());
    }

    /**
     * FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO
     *
     *
     * @param request
     * @param response
     * @return
     */
    public String editarPerfil(HttpServletRequest request,
            HttpServletResponse response) {

        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("name_edit");
        String descripcion = request.getParameter("description_edit");

        List<Integer> accesosId = new ArrayList<>();
        List<String> parameterNamesList = Collections.list(request.getParameterNames());
        for (String param : parameterNamesList) {
            if (param.contains("acceso-")) {
                accesosId.add(Integer.parseInt(request.getParameter(param)));
            }
        }
        AccesoPerfilBD.editAccesosPerfil(accesosId, id);
        //comenting 
//
        SelectBD select = null;
        HttpSession session = request.getSession();

        Perfil p = new Perfil();
        p.setId(id);
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setEstado(new Short("1"));

        try {
            int resultado = PerfilBD.update(p);
            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 0);
                    request.setAttribute("msg", "No logr&oacute; actualizar el perfil");
                    break;
                case 1:
                    request.setAttribute("idInfo", 1);
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    Perfil categoria = PerfilBD.selectByOne(p);
                    if (categoria != null) {
                        session.setAttribute("perfil", categoria);
                        request.setAttribute("msg", "Se ha modificado el perfil");
                    }
                    break;
                default:
                    break;
            }

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cargarPerfil(request, response, "view");
    }

    public String cargarPerfil(HttpServletRequest request,
            HttpServletResponse response, String action) {
        return cargarPerfil(request, response, action, null);
    }

    /**
     * FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO
     *
     *
     * @param request
     * @param response
     * @param action
     * @param idPerfil
     * @return
     */
    public String cargarPerfil(HttpServletRequest request,
            HttpServletResponse response, String action, Integer idPerfil) {

        Integer id = -1;
        if (request.getParameterMap().containsKey("id")) {
            id = Integer.parseInt(request.getParameter("id"));
        } else if (idPerfil != null) {
            id = idPerfil;
        }

        String url = "";
        Perfil p = new Perfil();
        p.setId(id);
        p.setEstado(new Short("1"));
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("login");
        try {
            Perfil perfilEncontrado = PerfilBD.selectByOne(p);

            if (perfilEncontrado != null) {
                session.setAttribute("perfil", perfilEncontrado);
            } else {
                session.setAttribute("perfil", p);
            }

            if (action.contains("create")) {
                session.setAttribute("accesoPerfil", organizeAccessObject(AccesoPerfilBD.selectByPerfilId(-1, user.getIdperfil())));
                url = "/app/perfil/nuevoPerfil.jsp";
            } else if (action.contains("view")) {
                session.setAttribute("accesoPerfil", organizeAccessObject(AccesoPerfilBD.selectByPerfilId(id, true, user.getIdperfil())));
                url = "/app/perfil/verPerfil.jsp";
            } else if (action.contains("edit")) {
                session.setAttribute("accesoPerfil", organizeAccessObject(AccesoPerfilBD.selectByPerfilId(id, user.getIdperfil())));
                url = "/app/perfil/editarPerfil.jsp";
            } else {
                url = "/app/perfil/listadoPerfil.jsp";
            }

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public static List<AccesoAccesoPerfil> organizeAccessObject(List<AccesoAccesoPerfil> laap) {

        if (laap.size() > 0) {
            // organizing  data to set groups and subgroups relating the access with the left main menu
            List<List<AccesoAccesoPerfil>> lo = new ArrayList<>();
            // gettiing the max subgroup to start from back to front grouping children with parents
            int maxSubGrupo = laap.get(0).getMaxSubGrupo();
            // setting a data structure to save the different subgroups
            for (int i = 0; i <= maxSubGrupo; i++) {
                lo.add(new ArrayList<AccesoAccesoPerfil>());
            }

            int subGrupo = 0;
            for (AccesoAccesoPerfil aParent : laap) {

                subGrupo = aParent.getSubGrupo();
                lo.get(subGrupo).add(aParent);

                if (subGrupo != maxSubGrupo) {

                    for (AccesoAccesoPerfil aapChild : lo.get(subGrupo + 1)) {
                        // looking for children
                        if (aParent.getPkAcceso() == aapChild.getFkAccesoPadre()) {
                            aParent.getSubGrupos().add(aapChild);
                        }

                    }
                    // ordering by position field
                    Collections.sort(aParent.getSubGrupos(), new Comparator<AccesoAccesoPerfil>() {
                        @Override
                        public int compare(AccesoAccesoPerfil o1, AccesoAccesoPerfil o2) {
                            return new Integer(o1.getPosicion()).compareTo(o2.getPosicion());
                        }
                    });
                }
            }
            // ordering by position field
            Collections.sort(lo.get(0), new Comparator<AccesoAccesoPerfil>() {
                @Override
                public int compare(AccesoAccesoPerfil o1, AccesoAccesoPerfil o2) {
                    return new Integer(o1.getPosicion()).compareTo(o2.getPosicion());
                }
            });
            return lo.get(0);
        }
        return null;
    }

    /**
     * FUNCION QUE PERMITE ELIMINAR UN PERFIL*
     */
    public String eliminarPerfil(HttpServletRequest request,
            HttpServletResponse response) {

        String estado = request.getParameter("estado");
        String id = request.getParameter("id");
        String url = "";

        Perfil c = new Perfil();
        c.setEstado(Integer.parseInt(estado));
        c.setId(Integer.parseInt(id));
        HttpSession session = request.getSession();

        try {
            PerfilBD.updateEstado(c);
            SelectBD select = new SelectBD(request);
            session.setAttribute("select", select);
            url = "/app/perfil/listadoPerfil.jsp";

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public static String remove(String input) {
        // Cadena de caracteres original aParent sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }//remove1

}
