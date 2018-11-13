/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.registel.rdw.datos.ConductorBD;
import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.LoginBD;
import com.registel.rdw.datos.PropietarioBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.UsuarioBD;
import com.registel.rdw.logica.Conductor;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.Propietario;
import com.registel.rdw.logica.Tmpusr;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.Mail;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.StringUtils;
import java.util.ArrayList;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorUsuario extends HttpServlet {    
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
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
        
        if (requestURI.endsWith("/guardarUsuario")) {
            url = guardarUsuario (request, response);
        } else if (requestURI.endsWith("/guardarEmpresa")) {
            url = guardarEmpresa (request, response);
        } else if (requestURI.endsWith("/guardarConductor")) {
            /* url = guardarConductor (request, response);*/
        } else if (requestURI.endsWith("/pre_editarUsuario")) {
            url = pre_editarUsuario(request, response);
        } else if (requestURI.endsWith("/editarUsuario")) {
            url = editarUsuario(request, response);
        } else if (requestURI.endsWith("/eliminarUsuario")) {
            url = activarUsuario(request, response, false);
        } else if (requestURI.endsWith("/restaurarUsuario")) {
            url = activarUsuario(request, response, true);
        } else if (requestURI.endsWith("/actualizarPerfilUsuario")) {
            url = actualizarPerfilUsuario(request, response);
        } else if (requestURI.endsWith("/cambiarContrasena")) {
            url = cambiarContrasena(request, response);
        } else if (requestURI.endsWith("/eliminarUsuario_")) {
            url = eliminarUsuario(request, response);
        } else if (requestURI.endsWith("/pre_editarEmpresa")) {
            url = pre_editarEmpresa(request, response);
        } else if (requestURI.endsWith("/editarEmpresa")) {
            url = editarEmpresa(request, response);
        } else if (requestURI.endsWith("/eliminarEmpresa")) {
            url = activarEmpresa(request, response, false);
        } else if (requestURI.endsWith("/restaurarEmpresa")) {
            url = activarEmpresa(request, response, true);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para creacion de nueva empresa.
    // Delega proceso de almacenamiento, en caso de no
    // almacenarse o exista registro de empresa semejante
    // se notifica al usuario.
    public String guardarEmpresa (HttpServletRequest request,
            HttpServletResponse response) { 
        
        String nombre = request.getParameter("nombre");
        String nit    = request.getParameter("nit");
        String ciudad = request.getParameter("sciudad");
        String moneda = request.getParameter("smoneda");
        String zonahoraria = request.getParameter("szonahoraria");
        
        ciudad = ciudad.split(",")[1];
        
        Empresa e = new Empresa();
        e.setNombre(nombre);
        e.setNit(nit);
        e.setIdciudad(Restriction.getNumber(ciudad));
        e.setIdmoneda(Restriction.getNumber(moneda));
        e.setIdzonahoraria(Restriction.getNumber(zonahoraria));
        
        try {
            // Almacena empresa
            if (EmpresaBD.insert(e)) {                
                request.setAttribute("msg", "* Empresa registrada correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                actualizarDatos(request, response);
            } else {
                request.setAttribute("msg", "* Empresa no registrada.");
                request.setAttribute("msgType", "alert alert-danger");
            }            
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());            
            request.setAttribute("msgType", "alert alert-info");
        }
        
        return "/app/usuarios/nuevaEmpresa.jsp";
    }
      
    
    // Procesa solicitud para creacion de nuevo usuario.
    // Delega proceso de almacenamiento, en caso de no
    // almacenarse o existir registro semejante se notifica
    // al usuario.
    public String guardarUsuario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre   = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email    = request.getParameter("email_data");   
        String tipodoc  = request.getParameter("stipodoc");
        String numdoc   = request.getParameter("numdoc");
        String login    = request.getParameter("login");
        String pass     = request.getParameter("pass");
        String cpass    = request.getParameter("cpass");        
        String perfil   = request.getParameter("sperfil");    
        String empresa  = request.getParameter("sempresa");        
        
        Usuario u = new Usuario ();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);        
        u.setIdtipodoc(Restriction.getNumber(tipodoc));
        u.setNumdoc(numdoc);
        u.setLogin(login);
        u.setPass(pass);
        u.setCpass(cpass);                
        u.setIdperfil(Integer.parseInt(perfil));      
        u.setIdempresa(Integer.parseInt(empresa));
        
        // Comprueba existencia de usuario
        String rs = UsuarioBD.existByFields(u);
        if (rs != null) {
            request.setAttribute("msgType", "alert alert-info");
            switch (rs) {
                case "cedula":  { request.setAttribute("msg", "* N&uacute;mero de documento ya existe.");   return "/app/usuarios/nuevoUsuario.jsp"; }            
                case "email":   { request.setAttribute("msg", "* Correo electr&oacute;nico ya existe.");    return "/app/usuarios/nuevoUsuario.jsp"; }
                case "login":   { request.setAttribute("msg", "* Nombre de usuario ya existe.");            return "/app/usuarios/nuevoUsuario.jsp"; }
            }
        } else {
            request.setAttribute("msgType", "alert alert-info");
            request.setAttribute("msgRegister", "* Imposible verificar los datos. Intente de nuevo.");
            return "/app/usuarios/nuevoUsuario.jsp";
        }
        
        try {
            // Almacena usuario
            if (UsuarioBD.insert(u)) {
                request.setAttribute("msg", "* Usuario registrado correctamente.");
                request.setAttribute("msgType", "alert alert-success");
                actualizarDatos(request, response);
            } else {
                request.setAttribute("msg", "* Usuario no fue registrado.");
                request.setAttribute("msgType", "alert alert-danger");
            }
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
            request.setAttribute("msgType", "alert alert-info");
        } 
        
        return "/app/usuarios/nuevoUsuario.jsp";
    }

    // Consulta informacion de usuario para ser modificada.
    public String pre_editarUsuario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String numdoc = request.getParameter("numdoc");
        Usuario usr = UsuarioBD.get(numdoc);
        
        HttpSession session = request.getSession();
        session.setAttribute("usr", usr);
        
        return "/app/usuarios/editaUsuario.jsp";
    }
    
    // Procesa solicitud de edicion de usuario.
    // Delega proceso de actualizacion, en caso de no actualizarse
    // o exista registro semejante se notifica al usuario.
    // (solicitud proviene de seccion de usuario)
    public String editarUsuario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre   = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String tipodoc  = request.getParameter("stipodoc");
        String numdoc   = request.getParameter("numdoc");
        String login    = request.getParameter("login");
        String email    = request.getParameter("email_data");        
        String perfil   = request.getParameter("sperfil");
        String empresa  = request.getParameter("sempresa");
        String a_numdoc = request.getParameter("a_numdoc");
        String a_email  = request.getParameter("a_email");
        String a_login  = request.getParameter("a_login");

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setIdtipodoc(Restriction.getNumber(tipodoc));
        u.setNumdoc(numdoc);
        u.setLogin(login);
        u.setEmail(email);
        u.setIdperfil(Integer.parseInt(perfil));
        u.setIdempresa(Integer.parseInt(empresa));
        
        u.setA_numdoc(a_numdoc);
        u.setA_email(a_email);
        u.setA_login(a_login);
        
        // Comprueba existencia de usuario
        ArrayList<String> fields = new ArrayList<String>();
        if (!numdoc.equalsIgnoreCase(a_numdoc)) { fields.add("cedula"); }
        if (!email.equalsIgnoreCase(a_email))   { fields.add("email");  }
        if (!login.equalsIgnoreCase(a_login))   { fields.add("login");  }
        
        for (String field : fields) {        
            String rs = UsuarioBD.existByField(u, field);
            if (rs != null) {
                request.setAttribute("msgType", "alert alert-info");
                HttpSession session = request.getSession();
                session.setAttribute("usr", u);
                
                switch (rs) {
                    case "cedula":  { request.setAttribute("msg", "* N&uacute;mero de documento ya existe.");   return "/app/usuarios/editaUsuario.jsp"; }            
                    case "email":   { request.setAttribute("msg", "* Correo electr&oacute;nico ya existe.");    return "/app/usuarios/editaUsuario.jsp"; }
                    case "login":   { request.setAttribute("msg", "* Nombre de usuario ya existe.");            return "/app/usuarios/editaUsuario.jsp"; }
                }
            } else {
                request.setAttribute("msgType", "alert alert-info");
                request.setAttribute("msgRegister", "* Imposible verificar los datos. Intente de nuevo.");
                return "/app/usuarios/editaUsuario.jsp";
            }
        }
        
        // Actualiza registro de usuario
        if (UsuarioBD.update(u, a_numdoc)) {
            request.setAttribute("msg", "* Usuario actualizado correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            
            actualizarDatos(request, response);
            HttpSession session = request.getSession();            
            session.setAttribute("usr", u);                        
        } else {
            request.setAttribute("msg", "* Usuario no fue actualizado.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        return "/app/usuarios/editaUsuario.jsp";
    }
    
    // Procesa solicitud para actualizar usuario.
    // Delega proceso de actualizacion, en caso de no actualizarse
    // o exista algun registro semejante se notifica al usuario.
    // (solicitud proviene de seccion perfil de usuario)
    public String actualizarPerfilUsuario (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre   = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email    = request.getParameter("email_data");
        String login    = request.getParameter("login");
        String numdoc   = request.getParameter("numdoc");
        
        String a_email  = request.getParameter("a_email");
        String a_login  = request.getParameter("a_login");
        
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setLogin(login);
        u.setNumdoc(numdoc);
        
        u.setA_email(a_email);
        u.setA_login(a_login);
        
        // Comprueba existencia de usuario
        ArrayList<String> fields = new ArrayList<String>();        
        if (!email.equalsIgnoreCase(a_email))   { fields.add("email");  }
        if (!login.equalsIgnoreCase(a_login))   { fields.add("login");  }
        
        for (String field : fields) {        
            String rs = UsuarioBD.existByField(u, field);
            if (rs != null) {
                request.setAttribute("msgType", "alert alert-info");
                HttpSession session = request.getSession();
                session.setAttribute("login", u);
                
                switch (rs) {                    
                    case "email":   { request.setAttribute("msg", "* Correo electr&oacute;nico ya existe.");    return "/app/usuarios/perfilUsuario.jsp"; }
                    case "login":   { request.setAttribute("msg", "* Nombre de usuario ya existe.");            return "/app/usuarios/perfilUsuario.jsp"; }
                }
            } else {
                request.setAttribute("msgType", "alert alert-info");
                request.setAttribute("msgRegister", "* Imposible verificar los datos. Intente de nuevo.");
                return "/app/usuarios/perfilUsuario.jsp";
            }
        }
        
        // Actualiza registro de usuario
        if (UsuarioBD.updateByUser(u, numdoc)) {
            request.setAttribute("msg", "* Usuario actualizado correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            
            actualizarDatos(request, response);
            HttpSession session = request.getSession();            
            session.setAttribute("login", u);
        } else {
            request.setAttribute("msg", "* Usuario no fue actualizado.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/perfilUsuario.jsp";
    }
    
    // Procesa solicitud de cambio de contraseña.
    // Delega proceso de actualizacion de registro.
    public String cambiarContrasena(HttpServletRequest request,
            HttpServletResponse response) {
        
        String oldPass  = request.getParameter("oldpass");
        String newPass  = request.getParameter("newpass");
        String cnewPass = request.getParameter("cnewpass");
        String numdoc   = request.getParameter("numdoc");
    
        // Cambia contraseña
        if (LoginBD.changePass(numdoc, oldPass, newPass)) {
            request.setAttribute("msgChange", "* Contrase&ntilde;a cambiada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
        } else {
            request.setAttribute("msgChange", "* Contrase&ntilde;a no cambiada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/perfilUsuario_pass.jsp";
    }
    
    // Procesa solicitud para eliminacion de usuario.
    // Delega proceso de eliminacion.
    // (Cambia valor de campo estado a 0).
    public String eliminarUsuario(HttpServletRequest request,
            HttpServletResponse response) {
        
        String numdoc = request.getParameter("numdoc");
        
        // Elimina usuario (cambia campo estado)
        if (UsuarioBD.changeState(numdoc, 0)) {
            Session.close(request, response);
        } else {
            request.setAttribute("msgChange", "* Usuario no eliminado.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/index.jsp";
    }
    
    // Procesa solicitud de registro/activacion de usuario por primera vez o no.
    // Activa/Desactiva usuario en caso de no ser registro por primera vez.
    public String activarUsuario (HttpServletRequest request,
            HttpServletResponse response, boolean activar) {
        
        String nombre    = request.getParameter("nombre");
        String apellido  = request.getParameter("apellido");
        String login     = request.getParameter("login");
        String numdoc    = request.getParameter("numdoc");
        String email     = request.getParameter("correo");
        String notificar = request.getParameter("notificar");
        
        int estado; String str;
        if (activar) { estado = 1; str = "restaurado"; }
        else         { estado = 0; str = "eliminado"; }
        
        // Si activacion de usuario es primera vez - se notifica al usuario
        // mediante correo electronico
        if (estado == 1 && notificar.compareTo("1") == 0) {
            if (UsuarioBD.change_preactive (numdoc))  {                
                                
                String usr = StringUtils.upperFirstLetter(nombre +" "+ apellido);
                String msg = "Estimado usuario " + usr + "\n\n"
                           + "Su cuenta con nombre de usuario [ " + login + " ] ha sido activada correctamente.\n\n"                                 
                           + "Agradecemos su participación.\n\n"
                           + "Equipo Registel.";
                
                Mail mail  = new Mail(false);
                if (mail.send(msg, email)) {
                    request.setAttribute("msg", "* Usuario activado y notificado correctamente.");
                    request.setAttribute("msgType", "alert alert-success");
                    actualizarDatos(request, response);
                } else {
                    request.setAttribute("msg", "* Usuario activado imposible de notificar.");
                    request.setAttribute("msgType", "alert alert-info");
                }
            } else {
                request.setAttribute("msg", "* Usuario no activado.");
                request.setAttribute("msgType", "alert alert-info");
            }             
            return "/app/usuarios/consultaUsuario.jsp";
        }
        
        // Elimina usuario (cambia campo estado)
        if (UsuarioBD.changeState (numdoc, estado)) {
            request.setAttribute("msg", "* Usuario "+ str +" correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            actualizarDatos(request, response);
        } else {
            request.setAttribute("msg", "* Usuario no "+ str +".");
            request.setAttribute("msgType", "alert alert-danger");
        }
        return "/app/usuarios/consultaUsuario.jsp";
    }
    
    // Consulta informacion de empresa para ser modificada.
    public String pre_editarEmpresa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idempresa = request.getParameter("id");        
        Empresa emp = EmpresaBD.get(Restriction.getNumber(idempresa));
        
        HttpSession session = request.getSession();
        request.setAttribute("emp", emp);
        
        return "/app/usuarios/editaEmpresa.jsp";
    }
    
    // Procesa solicitud de actualizacion de empresa.
    // Delega proceso de actualizacion, notifica al usuario.    
    public String editarEmpresa (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre   = request.getParameter("nombre");
        String nit      = request.getParameter("nit");
        String idpais   = request.getParameter("spais");
        String iddpto   = request.getParameter("sdpto");
        String idciudad = request.getParameter("sciudad");
        String a_nit    = request.getParameter("a_nit");
        String moneda   = request.getParameter("smoneda");
        String zonahoraria = request.getParameter("szonahoraria");
        String idempresa = request.getParameter("id");       
        
        iddpto   = iddpto.split(",")[1];
        idciudad = idciudad.split(",")[1];
        
        Empresa e = new Empresa();
        e.setId(Restriction.getNumber(idempresa));
        e.setNombre(nombre);
        e.setNit(nit);
        e.setIdpais(Restriction.getNumber(idpais));
        e.setIddpto(Restriction.getNumber(iddpto));
        e.setIdciudad(Restriction.getNumber(idciudad));        
        e.setIdmoneda(Restriction.getNumber(moneda));
        e.setIdzonahoraria(Restriction.getNumber(zonahoraria));
        
        // Actualiza empresa
        if (EmpresaBD.update(e)) {
            request.setAttribute("msg", "* Empresa actualizada correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            
            actualizarDatos(request, response);
            HttpSession session = request.getSession();            
            session.setAttribute("emp", e);
        } else {
            request.setAttribute("msg", "* Empresa no fue actualizada.");
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        return "/app/usuarios/editaEmpresa.jsp";
    }
    
    // Procesa solicitud para activacion/desactivacion de empresa.
    // Delega proceso de actualizacion, notifica al usuario.
    // (cambia valor de campo estado 0-1).
    public String activarEmpresa (HttpServletRequest request,
            HttpServletResponse response, boolean activar) {
        
        String sidEmpresa = request.getParameter("id");
        int idEmpresa = Restriction.getNumber(sidEmpresa);
        
        int estado; String str;
        if (activar) {
            estado = 1; str = "restaurada";
        } else {
            estado = 0; str = "eliminada";
        }
        
        // Activa/desactiva empresa
        if (EmpresaBD.active (idEmpresa, estado)) {
            request.setAttribute("msg", "* Empresa "+str+" correctamente.");
            request.setAttribute("msgType", "alert alert-success");
            actualizarDatos(request, response);
        } else {
            request.setAttribute("msg", "* Empresa no "+str+".");
            request.setAttribute("msgType", "alert alert-danger");
        }
        return "/app/usuarios/consultaEmpresa.jsp";
    }
    
    // Consulta y actualiza listados de los diferentes modulos.
    public void actualizarDatos (HttpServletRequest request,
            HttpServletResponse response) {
        
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        
        session.setAttribute("select", select);
    }
}
