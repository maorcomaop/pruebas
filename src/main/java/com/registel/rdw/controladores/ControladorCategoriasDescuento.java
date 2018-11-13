/**
 * Clase controlador categorias de descuento
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 01/12/2016 10:26:20 AM
 */
package com.registel.rdw.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.CategoriasDeDescuentoBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.CategoriasDeDescuento;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorCategoriasDescuento extends HttpServlet {

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

        if (session == null
                || session.getAttribute("login") == null
                || session.getAttribute("select") == null
                || Expire.check(session.getCreationTime())) {
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
        if (requestURI.endsWith("/guardarCategoria")) {
            url = guardarCategoria(request, response);
        } else if (requestURI.endsWith("/editarCategoria")) {
            url = editarCategoria(request, response);
        } else if (requestURI.endsWith("/verMasCategoria")) {
            url = verCategoria(request, response);
        } else if (requestURI.endsWith("/eliminarCategoria")) {
            url = eliminarCategoria(request, response);
        }
        //response.sendRedirect("login.jsp");

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO*
     */
    public String guardarCategoria(HttpServletRequest request,
            HttpServletResponse response) {

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        String seLiquida = request.getParameter("liquidacion");
        String es_fija = request.getParameter("aplicar_siempre");
        String descuentaPasajeros = request.getParameter("descuenta_pasajeros");
        String tipoDescuento = request.getParameter("tipo_valor_descuento");
        String descuentaDelTotal = request.getParameter("descuenta_del_total");
        String valorDinero = request.getParameter("valor_descuento");

        //String valorPorcentaje = request.getParameter("porcentaje");           
        String url = "/app/categorias_de_descuento/nuevaCategoria.jsp";
        HttpSession session = request.getSession();
        SelectBD select = null;
        CategoriasDeDescuento c = new CategoriasDeDescuento();
        try {
            c.setNombre(nombre);
            c.setDescripcion(descripcion);
            /**
             * *****************************************
             */
            if (seLiquida != null) {
                c.setAplicaDescuento(Integer.parseInt(seLiquida));
            } else if (seLiquida == null) {
                c.setAplicaGeneral(1);
            }
            /**
             * *****************************************
             */
            if (es_fija != null) {
                c.setEs_fija(Integer.parseInt(es_fija));
            }
            /**
             * *****************************************
             */
            if (descuentaPasajeros != null) {
                c.setDescuenta_pasajeros(Integer.parseInt(descuentaPasajeros));
            }

            /**
             * *****************************************
             */
            if (tipoDescuento != null) {
                if (valorDinero.equals("")) {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se puede registrar la categoria por que los valores ingresados no corresponden a los permitidos, Debe ingresar un valor mayor a 0.0 y menor de 100.0");
                    return url;
                } else {
                    try {
                        if (Double.parseDouble(valorDinero) >= 0.0) {
                            c.setEs_valor_moneda(1);
                            c.setValor(Double.parseDouble(valorDinero));
                        }
                    } catch (Exception e) {
                        request.setAttribute("idInfo", 2);
                        request.setAttribute("msg", "No se puede registrar la categoria el valor ingresado no se acepta, Debe ingresar un valor mayor de 0.0");
                        return url;
                    }
                }

            } else if (valorDinero.equals("")) {
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", "No se puede registrar la categoria por que los valores ingresados no corresponden a los permitidos, Debe ingresar un valor mayor a 0.0 y menor de 100.0");
                return url;
            } else {
                try {
                    if ((Double.parseDouble(valorDinero) >= 0.0) && (Double.parseDouble(valorDinero) <= 100.0)) {
                        c.setEs_valor_porcentaje(1);
                        c.setValor(Double.parseDouble(valorDinero));
                    }
                } catch (Exception e) {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se puede registrar la categoria por que los valores ingresados no corresponden a los permitidos, Debe ingresar un valor mayor a 0.0 y menor de 100.0");
                    return url;
                }
            }
            /**
             * *****************************************
             */
            if (descuentaDelTotal != null) {
                c.setDescuenta_del_total(1);
                c.setAplicaGeneral(0);
                c.setAplicaDescuento(0);
            }
            c.setEstado(new Short("1"));

        } catch (Exception e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar una categoria");
            return "/app/categorias_de_descuento/listadoCategorias.jsp";
        }

        try {
            int resultado = CategoriasDeDescuentoBD.insert(c);

            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se logr&oacute registrar la categoria de descuento");
                    break;
                case 1:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado una nueva categoria de descuento");
                    break;
                default:
                    break;
            }

        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }
        return url;
    }

    /**
     * FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO*
     */
    public String editarCategoria(HttpServletRequest request,
            HttpServletResponse response) {

        String url = "/app/categorias_de_descuento/unaCategoria.jsp";

        int id = Integer.parseInt(request.getParameter("id_edit"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String seliquida = request.getParameter("liquidacion");
        String seaplicasiempre = request.getParameter("aplicar_siempre");
        String sedescuentapasajeros = request.getParameter("descuenta_pasajeros");
        String sedescuentatotal = request.getParameter("descuenta_del_total");
        String tipoDescuento = request.getParameter("tipo_descuento");
        String valorDinero = request.getParameter("valor_moneda");
        int resultado = 0;
        SelectBD select = null;
        HttpSession session = request.getSession();

        CategoriasDeDescuento c = new CategoriasDeDescuento();
        try {
            c.setId(id);
            c.setNombre(nombre);
            c.setDescripcion(descripcion);
            if (seliquida != null) {
                c.setAplicaDescuento(Integer.parseInt(seliquida));
            } else if (seliquida == null) {
                c.setAplicaGeneral(1);
            }
            /**
             * *****************************************************
             */
            if (seaplicasiempre != null) {
                c.setEs_fija(Integer.parseInt(seaplicasiempre));
            } else {
                c.setEs_fija(0);
            }
            /**
             * *****************************************************
             */
            if (sedescuentapasajeros != null) {
                c.setDescuenta_pasajeros(Integer.parseInt(sedescuentapasajeros));
            } else {
                c.setDescuenta_pasajeros(0);
            }

            /**
             * *****************************************************
             */
            if (tipoDescuento != null) {

                if (valorDinero.equals("")) {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se puede registrar la categoria por que los valores ingresados no corresponden a los permitidos, Debe ingresar un valor mayor a 0.0 y menor de 100.0");
                    return url;
                }

                if (!valorDinero.equals("")) {
                    try {
                        if (Double.parseDouble(valorDinero) >= 0.0) {
                            c.setEs_valor_moneda(1);
                            c.setValor(Double.parseDouble(valorDinero));
                        }
                    } catch (Exception e) {
                        request.setAttribute("idInfo", 2);
                        request.setAttribute("msg", "No se puede registrar la categoria el valor ingresado no se acepta, Debe ingresar un valor mayor de 0.0");
                        return url;
                    }
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se puede registrar la categoria el valor ingresado no es un numero, Debe ingresar un valor en la caja de texto");
                    return url;
                }

            } else {
                if (valorDinero.equals("")) {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se puede registrar la categoria por que los valores ingresados no corresponden a los permitidos, Debe ingresar un valor mayor a 0.0 y menor de 100.0");
                    return url;
                }

                if (!valorDinero.equals("")) {
                    try {
                        if ((Double.parseDouble(valorDinero) >= 0.0) && (Double.parseDouble(valorDinero) <= 100.0)) {
                            c.setEs_valor_porcentaje(1);
                            c.setValor(Double.parseDouble(valorDinero));
                        }
                    } catch (Exception e) {
                        request.setAttribute("idInfo", 2);
                        request.setAttribute("msg", "No se puede registrar la categoria por que los valores ingresados no corresponden a los permitidos, Debe ingresar un valor mayor a 0.0 y menor de 100.0");
                        return url;
                    }
                } else {
                    request.setAttribute("idInfo", 2);
                    request.setAttribute("msg", "No se puede registrar la categoria el valor ingresado no es un numero, Debe ingresar un valor en la caja de texto");
                    return url;
                }
            }
            /**
             * *****************************************************
             */
            if (sedescuentatotal != null) {
                c.setDescuenta_del_total(Integer.parseInt(sedescuentatotal));
                c.setAplicaGeneral(0);
                c.setAplicaDescuento(0);
            } else {
                c.setDescuenta_del_total(0);
            }

            /**
             * *****************************************************
             */
            c.setEstado(new Short("1"));

        } catch (Exception e) {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar una categoria");
            return "/app/categorias_de_descuento/listadoCategorias.jsp";
        }

        try {

            resultado = CategoriasDeDescuentoBD.update(c);

            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 0);
                    request.setAttribute("msg", "No logr&oacute; establecer una nueva categoria");

                    break;
                case 1:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("idInfo", 1);
                    CategoriasDeDescuento categoria = CategoriasDeDescuentoBD.selectByOne(c);
                    if (categoria != null) {
                        session.setAttribute("categoria", categoria);
                        request.setAttribute("msg", "Se ha modificado la categoria correctamente");
                    }
                    break;
                default:
                    break;
            }
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorCategoriasDescuento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    /**
     * FUNCION QUE PERMITE ELIMINAR UNA CATEGORIA*
     */
    public String eliminarCategoria(HttpServletRequest request,
            HttpServletResponse response) {

        String estado = request.getParameter("estado");
        String id = request.getParameter("id");
        String url = "/app/categorias_de_descuento/listadoCategorias.jsp";

        CategoriasDeDescuento c = new CategoriasDeDescuento();
        c.setId(Integer.parseInt(id));
        c.setEstado(Integer.parseInt(estado));

        HttpSession session = request.getSession();

        try {
            CategoriasDeDescuentoBD.updateEstado(c);
            SelectBD select = new SelectBD(request);
            session.setAttribute("select", select);

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorCategoriasDescuento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    /**
     * FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO*
     */
    public String verCategoria(HttpServletRequest request,
            HttpServletResponse response) {

        String id = request.getParameter("id");
        String url = "";
        CategoriasDeDescuento c = new CategoriasDeDescuento();
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();

        c.setId(Integer.parseInt(id));
        c.setEstado(new Short("1"));
        try {
            CategoriasDeDescuento categoriaEncontrado = CategoriasDeDescuentoBD.selectByOne(c);
            if (categoriaEncontrado != null) {
                session.setAttribute("categoria", categoriaEncontrado);
                session.setAttribute("select", select);
                url = "/app/categorias_de_descuento/unaCategoria.jsp";
            } else {
                url = "/app/categorias_de_descuento/listadoCategorias.jsp";
            }

        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorCategoriasDescuento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
}
