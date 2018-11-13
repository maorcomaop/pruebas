/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.LoginBD;
import com.registel.rdw.datos.PerfilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.datos.UsuarioBD;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.PropiedadesPerimetro;
import com.registel.rdw.logica.PropiedadesServidor;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.SelectC;
import com.registel.rdw.utils.Constant;
import com.registel.rdw.utils.Mail;
import com.registel.rdw.utils.Restriction;
import com.registel.rdw.utils.StringUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 *
 * @author lider_desarrollador
 */
public class ControladorCuenta extends HttpServlet {
    
    private SimpleDateFormat ffmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        // Consulta listados necesarios para creacion de cuenta
        int idx_listas[] = {
            SelectC.LST_EMPRESA.ordinal(),
            SelectC.LST_PERFIL.ordinal()
        };
        
        SelectBD select = new SelectBD(idx_listas);
        request.setAttribute("select", select);
        String url = "/register.jsp";
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        String requestURI = request.getRequestURI();
        String url = "";
        
        if (requestURI.endsWith("/crearCuenta")) {
            url = crearCuenta(request, response);
        } else if (requestURI.endsWith("/activarCuenta")) {
            //url = activarCuenta(request, response);
            url = activarCuenta_porHumano(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    // Procesa solicitud para crear cuenta de usuario, inactiva
    // y notificacion por correo electronico al usuario.
    public String crearCuenta (HttpServletRequest request,
            HttpServletResponse response) {
        
        String nombre       = request.getParameter("nombre");
        String apellido     = request.getParameter("apellido");
        String empresa      = request.getParameter("sempresa");
        String perfil       = request.getParameter("sperfil");
        String numdoc       = request.getParameter("numdoc");
        String email        = request.getParameter("email_data");
        String nomusr       = request.getParameter("nomusr");
        String pass         = request.getParameter("pass");
        String cpass        = request.getParameter("cpass");
        
        Usuario u = new Usuario();
        //u.setIdperfil(3);  // 3 Perfil cliente
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setIdempresa(Restriction.getNumber(empresa));
        u.setIdperfil(Restriction.getNumber(perfil));
        u.setNumdoc(numdoc);
        u.setEmail(email);
        u.setLogin(nomusr);
        u.setPass(pass);        
        u.setCpass(cpass);
        //u.setEstado(0);
        request.setAttribute("preusr", u);
        
        // Restablece listado de empresa y perfil
        int idx_listas[] = { SelectC.LST_EMPRESA.ordinal(), SelectC.LST_PERFIL.ordinal() };
        SelectBD select = new SelectBD(idx_listas);
        request.setAttribute("select", select);
        
        // Verificacion de captcha
        /*
        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6Lc7lhYUAAAAADzYIdH8YIVrRTh-cym5ageI4yGa");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (!reCaptchaResponse.isValid()) {
            request.setAttribute("msgType", "alert alert-info");
            request.setAttribute("msgRegister", "* Valor de captcha inválido.");
            return "/register.jsp";
        } */
        
        String token = UUID.randomUUID().toString();
        String msgRegister = "";         
        
        // Comprueba si usuario se encuentra registrado
        String rs = UsuarioBD.existByFields(u);
        if (rs != null) {
            request.setAttribute("msgType", "alert alert-info");
            switch (rs) {
                case "cedula":  { request.setAttribute("msgRegister", "* N&uacute;mero de documento ya existe.");   return "/register.jsp"; }            
                case "email":   { request.setAttribute("msgRegister", "* Correo electr&oacute;nico ya existe.");    return "/register.jsp"; }
                case "login":   { request.setAttribute("msgRegister", "* Nombre de usuario ya existe.");            return "/register.jsp"; }
            }
        } else {
            request.setAttribute("msgType", "alert alert-info");
            request.setAttribute("msgRegister", "* Imposible verificar los datos. Intente de nuevo.");
            return "/register.jsp";
        }
        
        // Se recupera dominio o host
        String web_path = getServletContext().getRealPath("");
        Entorno entorno = new Entorno(web_path);
        String dominio  = entorno.obtenerPropiedad(Constant.DOMINIO_HOST_SERVIDOR);
        
        if (dominio == null || dominio == "") {
            request.setAttribute("msgType", "alert alert-info");
            request.setAttribute("msgRegister", "* Imposible enviar el correo electr&oacute;nico. Verifique su configuraci&oacute;n.");
            return "/register.jsp";
        }
        
        dominio = "http://" + dominio;
        String fecha = ffmt.format(new Date());
        
        try {
            // Almacena usuario y envia notificacion por correo
            if (UsuarioBD.insert_preactive(u, token)) {
                String usr = StringUtils.upperFirstLetter(nombre +" "+ apellido);
                String msg = usr + ", Bienvenido a Regisdata Web\n\n"
                        + "Para activar su cuenta y tener acceso a la aplicación, "
                        + "por favor diríjase al siguiente enlace:\n"
                        + dominio + "/RDW1/inicio.jsp?token=" + token + "\n"
                        + "Inicie sesión con su nombre de usuario y contraseña.\n\n"
                        + "Agradecemos su participación.\n\n"
                        + "Equipo Registel.";                
                
                // Envio de notificacion
                Mail.changeDataContact(web_path);
                Mail mail = new Mail(false);            
                if (mail.send(msg, email)) {
                    msgRegister = "* Por favor verifique su correo electr&oacute;nico y siga las instrucciones.";                    
                } else {
                    // Se elimina usuario y reporta la no creacion del usuario
                    UsuarioBD.delete_preactive(u);
                    msgRegister = "** Usuario imposible de crear. Por favor verifique sus datos.";
                }
            } else {
                // Se reporta la no creacion del usuario
                msgRegister = "* Usuario imposible de crear. Por favor verifique sus datos.";                
            }
        } catch (ExisteRegistroBD e) {
            request.setAttribute("msgRegister", e.getMessage());
            request.setAttribute("msgType", "alert alert-danger");
        }
        
        request.setAttribute("preusr", null);
        request.setAttribute("msgRegister", msgRegister);
        request.setAttribute("msgType", "alert alert-info");
        return "/register.jsp";
    }
    
    // Procesa solicitud de activacion de cuenta de usuario registrada,
    // proceso que envia correo electronico a usuario soporte del sistema,
    // en que se pide activacion humana de la cuenta.
    public String activarCuenta_porHumano (HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        String nomusr = request.getParameter("nomusr");
        String pass   = request.getParameter("pass");
        String token  = request.getParameter("token");        
                
        String web_path = getServletContext().getRealPath("");
        Mail.changeDataContact(web_path);
        String email_support = Mail.getEmail();        
        
        // Consulta registro de cuenta de usuario creada
        Usuario u = UsuarioBD.user_preactive(nomusr, pass, token);
        if (u != null) {
            String nom   = u.getNombre();
            String ape   = u.getApellido();
            String login = u.getLogin();
            String nid   = u.getNumdoc();
            String email = u.getEmail();
            String fecha = ffmt.format(new Date());
            Perfil perfil  = PerfilBD.selectById(u.getIdperfil());
            String sperfil = (perfil == null) ? u.getIdperfil() + " - NA" : perfil.getNombre();
            sperfil        = (sperfil == null) ? u.getIdperfil() + " - NA" : sperfil.toLowerCase();
            
            String msg = "Equipo de soporte, Regisdata Web,  " + fecha + "\n\n"
                    + "Registro de nuevo usuario realizado:\n\n"
                    + "Nombre: "   + nom + "\n"
                    + "Apellido: " + ape + "\n"
                    + "Nombre de usuario: " + login + "\n"            
                    + "Perfil: "   + sperfil + "\n"
                    + "Cédula: "   + nid + "\n"
                    + "Correo: "   + email + "\n\n"
                    + "Por favor verificar los datos y activar usuario.";            
            
            // Envia notificacion a usuario de soporte
            Mail mail = new Mail(false);        
            if (mail.send(msg, email_support)) {
                request.setAttribute("msgLogin", 
                        "* Se ha contactado con el administrador para la activaci&oacute;n de su cuenta. "
                        + "Ser&aacute; notificado a trav&eacute;s del correo electr&oacute;nico.");
                request.setAttribute("msgType", "alert alert-success");
            } else {
                request.setAttribute("msgLogin", 
                        "* Imposible iniciar proceso de activaci&oacute;. "
                        + "Intente de nuevo a trav&eacute;s del enlace enviado.");
                request.setAttribute("msgType", "alert alert-info");
            }
        } else {
            request.setAttribute("msgLogin", "* Imposible relacionar usuario especificado.");
            request.setAttribute("msgType", "alert alert-info");
        }
        
        return "/index.jsp";
    }
    
    // Procesa solicitud de activacion de cuenta de usuario.
    public String activarCuenta (HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        String nomusr = request.getParameter("nomusr");
        String pass   = request.getParameter("pass");
        String token  = request.getParameter("token");
           
        Usuario u = null;
        // Activa cuenta de usuario previamente registrada y validada
        if (UsuarioBD.change_preactive(nomusr, pass, token)) {
            
            // Inicio de sesion
            u = LoginBD.login(nomusr, pass);
            if (u != null) {
                
                SelectBD select = new SelectBD(u);
            
                String web_path = getServletContext().getRealPath("");
                Entorno entornoRD = new Entorno(web_path);
                
                PropiedadesServidor propServ = new PropiedadesServidor(
                                    entornoRD.getSystemPropertiesRD());
                PropiedadesPerimetro propPerim = new PropiedadesPerimetro (
                                    entornoRD.getPerimetroPropertiesRD());

                session.setAttribute("login", u);
                session.setAttribute("select", select);            
                session.setAttribute("prop", propServ);
                session.setAttribute("perim", propPerim);
                session.setAttribute("entorno", entornoRD);
                
                request.setAttribute("msgLogin", "* Cuenta activada. Por favor reinicie la sesi&oacute;n.");
                request.setAttribute("msgType", "alert alert-success");
                return "/app/usuarios/index.jsp";
            } else {
                request.setAttribute("msgLogin", "* Imposible iniciar sesi&oacute;n. Int&eacute;ntelo de nuevo.");
                request.setAttribute("msgType", "alert alert-info");
                return "/index.jsp";
            }
        } else {
            request.setAttribute("msgLogin", "* Imposible activar su cuenta. Acceda al enlace desde su correo e intente de nuevo.");
            request.setAttribute("msgType", "alert alert-info");
            return "/index.jsp";
        }
    }
    
    // Establece en mayuscula primer caracter de cada
    // palabra encontrada en cadena de texto.
    public String firstUpperCase(String s) {
        if (s == null || s.length() == 0)
            return s;
        
        String arr_s[] = s.split(" ");
        String sub_s; s = "";
        for (int i = 0; i < arr_s.length; i++) {            
            sub_s = arr_s[i];
            
            if (sub_s == null || sub_s.length() == 0) {
                s += (s == "") ? sub_s : " " + sub_s;
            } else {
                sub_s = sub_s.substring(0, 1).toUpperCase() + sub_s.substring(1);
                s += (s == "") ? sub_s : " " + sub_s;
            }
        }
        return s;
    }
}
