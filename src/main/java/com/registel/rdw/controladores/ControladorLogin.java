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
import com.registel.rdw.datos.LoginBD;
import com.registel.rdw.datos.PerfilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Login;
import com.registel.rdw.logica.PropiedadesServidor;
import com.registel.rdw.logica.Token;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.Perfil;
import com.registel.rdw.logica.Entorno;
import com.registel.rdw.utils.Mail;
import com.registel.rdw.datos.AccesoPerfilBD;
import com.registel.rdw.datos.ConexionExterna;
import com.registel.rdw.datos.EmpresaBD;
import com.registel.rdw.logica.AccesoAccesoPerfil;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.PermissionChecker;
import com.registel.rdw.logica.ProgramacionRuta;
import com.registel.rdw.logica.PropiedadesPerimetro;
import com.registel.rdw.logica.SelectC;
import com.registel.rdw.utils.Constant;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author lider_desarrollador
 */

/*
    getSession(false)
        Obtenemos una sesion preexistente de haberla o null en caso
        contrario. Es mas ineficiente que al preguntar con isNew().
    getSession(true)
    getSession()
        Siempre obtenemos una sesion sin importar si existe o no
        una sesion previa. Es necesario preguntar con isNew() para
        saber si es nueva o no.
 */
public class ControladorLogin extends HttpServlet {

    private static int numIntentos = 0;
    private static int numIntentosRestore = 0;

    private static final int FALLO_PERFIL = 1;
    private static final int FALLO_USUARIO = 2;

    // Procesa solicitud web (peticion GET),
    // en la que verifica si sesion se encuentra activa y sin expirar,
    // si es asi redirije al usuario a la pagina de inicio de la aplicacion,
    // de lo contrario a la pagina de inicio de sesion.
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        String url = "";
        if (session == null || session.getAttribute("login") == null || Expire.check(session.getCreationTime())) { // login
            Session.close(request, response);
            url = "/index.jsp";
        } else {
            url = "/app/usuarios/index.jsp";
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
        String url = "";

        if (requestURI.endsWith("/iniciarSesion")) {
            url = iniciarSesion(request, response);
        } else if (requestURI.endsWith("/cerrarSesion")) {
            url = cerrarSesion(request, response);
        } else if (requestURI.endsWith("/restaurarContrasena")) {
            url = restaurarContrasena(request, response);
        } else if (requestURI.endsWith("/make_restaurarContrasena")) {
            url = make_restaurarContrasena(request, response);
        } else if (requestURI.endsWith("/logindata")) {
            logindata(request, response);
        } else if (requestURI.endsWith("/conexionInternet")) {
            url = cnx(request, response);
        } else if (requestURI.endsWith("/holdSessionServer")) {
            url = holdSessionServer(request, response);
        }

        if (url != "") {
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }

    // Consulta listado de datos usados para el acceso a la aplicacion.
    // En este caso se consulta las empresas registradas y se especifican
    // en componente select (html), para su posterior seleccion.
    public void logindata(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        SelectBD select = new SelectBD(SelectC.LST_EMPRESA.ordinal());
        ArrayList<Empresa> lst_empresa = select.getLstempresa();

        String s = "";
        for (Empresa empresa : lst_empresa) {
            String v = empresa.getId() + "," + empresa.getNombre();
            s += (s == "") ? v : "?" + v;
        }

        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        out.println(s);
    }

    // Procesa peticion de inicio de sesion.
    // Delega proceso de consulta.
    // Se contabiliza la cantidad de inicio de sesion fallidos, 
    // que de ser igual o superior a tres, la sesion es bloqueada
    // por un tiempo determinado. En dicho tiempo, el proceso
    // de autenticacion sera interrumpido.
    public String iniciarSesion(HttpServletRequest request,
            HttpServletResponse response) {

        Session.close(request, response);
        HttpSession session = request.getSession();
        String slogin = request.getParameter("login");
        String spass = request.getParameter("pass");
        String sempresa = request.getParameter("sempresa");

        String url = "";
        Perfil perfilUsuario = null;

        /*
        if (!Expire.checkWait()) {
            request.setAttribute("msgLogin", "* Inicio de sesi&oacute;n bloqueado.");
            request.setAttribute("msgType", "alert alert-danger");
            return "/login_error.jsp";
        } */
        // Consulta si sesion de usuario ha sido o sigue bloqueada.
        // Impide proceso de autenticacion de usuario si es verdadero.        
        Expire expire = new Expire();
        if (Expire.is_lock_session(slogin, sempresa)) {
            request.setAttribute("msgLogin", "* Inicio de sesi&oacute;n bloqueado.");
            request.setAttribute("msgType", "alert alert-danger");
            return "/login_error.jsp";
        }

        // Consulta credenciales de usuario
        Usuario user = LoginBD.login(slogin, spass, sempresa);

        if (user != null && LoginBD.activarConexion(user.getLogin())) {
            // Si credenciales son correctas y su estado de conexion
            // es activada, se inicia consulta de parametros, usados
            // en el interior del aplicativo.
            try {
                Session.setUsuarioSesion(user);

                perfilUsuario = PerfilBD.selectByOneUser(user);

                if (perfilUsuario != null) {

                    // Actualiza hora de inicio de sesion
                    SessionTime sessionTime = new SessionTime();
                    SessionTime.setTimeSession(user);

                    user.setPerfilusuario(perfilUsuario.getNombre());
                    SelectBD select = new SelectBD(user);
                    ProgramacionRuta pgr = select.selectProgramacionRuta_activa();

                    String web_path = getServletContext().getRealPath("");
                    Entorno entorno_rd = new Entorno(web_path);
                    Mail.changeDataContact(web_path);

                    PropiedadesServidor propServ = new PropiedadesServidor(
                            entorno_rd.getSystemPropertiesRD());
                    PropiedadesPerimetro propPerim = new PropiedadesPerimetro(
                            entorno_rd.getPerimetroPropertiesRD());

                    ConexionExterna.establecerEntorno(entorno_rd);
                    Empresa emp = EmpresaBD.ubicacionPromedio(user.getEnterprice());

                    long lastAccessedTime = session.getLastAccessedTime();
                    Timestamp a = new Timestamp(lastAccessedTime);
                    session.setAttribute("inicio_sesion", a);
                    //int maxInactiveInterval = session.getMaxInactiveInterval();
                    //System.out.println("<----> "+a+" <> "+maxInactiveInterval);

                    session.setAttribute("login", user);
                    session.setAttribute("emp", emp);
                    session.setAttribute("select", select);
                    session.setAttribute("prop", propServ);
                    session.setAttribute("perim", propPerim);
                    session.setAttribute("entorno", entorno_rd);
                    session.setAttribute("pgruta_activa", pgr.getNombreProgramacion());
                    session.setAttribute("pgruta_activa_id", pgr.getId());
                    session.setAttribute("maxInactiveInterval", session.getMaxInactiveInterval());

                    ArrayList<AccesoAccesoPerfil> accessProfile = AccesoPerfilBD.selectByPerfilId(user.getIdperfil(), true, user.getIdperfil());
                    PermissionChecker pc;
                    pc = new PermissionChecker(
                            ControladorPerfil.organizeAccessObject(accessProfile), user.getIdperfil(), user.getPerfilusuario());
                    pc.setAccessProfile(accessProfile);

                    if (pc.getAccess() != null) {
                        session.setAttribute("permissions", pc);
                    }
                    url = "/app/usuarios/index.jsp";
                    /*url = "/app/licencia/asignarClave.jsp";*/

                    String sistemaDeAyuda = entorno_rd.obtenerPropiedad(Constant.SISTEMA_DE_AYUDA);

                    if (sistemaDeAyuda != null && !sistemaDeAyuda.isEmpty()) {
                        session.setAttribute(Constant.SISTEMA_DE_AYUDA, sistemaDeAyuda);
                    }
                } else {
                    /*SE GENERA UNA EXCEPCION SI EL PERFIL NO SE ENCUENTRA ACTIVO*/
                    throw new Exception();
                }

            } catch (Exception ex) {

                /*CUENTA LOS INTENTOS DE INICIO DE SESION SI EL PERFIL NO SE ENCUENTRA ACTIVO*/
                String msg = chequearBloqueoSesion(FALLO_PERFIL, slogin, sempresa);
                request.setAttribute("msgLogin", msg);
                request.setAttribute("msgType", "alert alert-danger");
                url = "/login_error.jsp";
            }
        } else {

            /*CUENTA LOS INTENTOS DE INICIO DE SESION SI EL USUARIO ESTA NULO*/
            String msg = chequearBloqueoSesion(FALLO_USUARIO, slogin, sempresa);
            request.setAttribute("msgLogin", msg);
            request.setAttribute("msgType", "alert alert-danger");
            url = "/login_error.jsp";
        }
        return url;
    }

    // Intenta bloquear inicio de sesion con base en el
    // numero de intentos fallidos. Si este es mayor o igual
    // a tres, la sesion es bloqueada.
    // Se relaciona mensaje de error segun su tipo.
    public String chequearBloqueoSesion(int tipoFallo, String login, String empresa) {

        String msg;

        switch (tipoFallo) {
            case FALLO_PERFIL:
                msg = "* El usuario no tiene permisos para el ingreso al sistema.";
                break;
            case FALLO_USUARIO:
                msg = "* El usuario, contrase&ntilde;a o empresa pueden ser incorrectos, por favor intente de nuevo.";
                break;
            default:
                msg = "* El usuario, contrase&ntilde;a o empresa pueden ser incorrectos, por favor intente de nuevo.";
        }

        numIntentos += 1;
        if (numIntentos >= 3) {
            //Expire.setTimeNow();
            long time = new Date().getTime();
            Expire.lock_session(login, empresa, time);
            numIntentos = 0;
        }

        return msg;
    }

    // Procesa solicitud para restauracion de contraseña.
    // Se asocia cadena de texto unica al usuario, que se
    // almacena (usada para verificacion) y se envia correo 
    // electronico con un enlace para la restauracion de contraseña.
    public String restaurarContrasena(HttpServletRequest request,
            HttpServletResponse response) {

        String email = request.getParameter("email_data");
        // Genera cadena unica
        String uuid = UUID.randomUUID().toString();
        String msgRestore, msgType;

        // Se recupera dominio o host
        String web_path = getServletContext().getRealPath("");
        Entorno entorno = new Entorno(web_path);
        String dominio = entorno.obtenerPropiedad(Constant.DOMINIO_HOST_SERVIDOR);

        if (dominio == null || dominio == "") {
            request.setAttribute("msgType", "alert alert-info");
            request.setAttribute("msgRestore", "* Imposible enviar el correo electr&oacute;nico. Verifique su configuraci&oacute;n.");
            return "/restore.jsp";
        }

        dominio = "http://" + dominio;

        // Se almacena relacion de cadena unica y usuario, y
        // se envia correo al usuario para realizar restauracion.
        if (LoginBD.setRestore(email, uuid)) {

            String msg = "Estimado usuario de Regisdata Web,\n\n"
                    + "Para asginar una nueva contraseña, por favor diríjase al siguiente enlace:\n"
                    + dominio + "/RDW1/restorepass.jsp?token=" + uuid + "\n\n"
                    + "Tenga presente que este enlace expirará en cinco minutos, tiempo limite para\n"
                    + "realizar el cambio de contraseña.\n\n"
                    + "Agradecemos su atención.\n\n"
                    + "Equipo Registel.";

            Mail.changeDataContact(web_path);
            Mail mail = new Mail(false);
            if (mail.send(msg, email)) {
                msgRestore = "* Por favor verifique su correo electr&oacute;nico y siga las instrucciones.";
                msgType = "alert alert-info";
            } else {
                msgRestore = "* Mensaje no enviado. Por favor verifique su correo electr&oacute;nico e intente de nuevo.";
                msgType = "alert alert-danger";
            }
        } else {
            msgRestore = "* Recuperaci&oacute;n de contrase&ntilde;a no iniciado. Credencial no v&aacute;lida.";
            msgType = "alert alert-danger";
        }

        request.setAttribute("msgRestore", msgRestore);
        request.setAttribute("msgType", msgType);
        return "/restore.jsp";
    }

    // Procesa solicitud para cierre de sesion.
    public String cerrarSesion(HttpServletRequest request,
            HttpServletResponse response) {

        Session.close(request, response);
        return "/index.jsp";
    }

    // Procesa solicitud de cambio de contraseña,
    // una vez se ha hecho la peticion al sistema
    // (Envio de enlace para restauracion).
    // Se verifica que no haya pasado el tiempo permitido para realizar 
    // restauracion (5min) y/o intentado mas de 3 veces. 
    // Si usuario hace el proceso correctamente, la contraseña nueva 
    // es almacenada y se permite iniciar sesion.
    public String make_restaurarContrasena(HttpServletRequest request,
            HttpServletResponse response) {

        numIntentosRestore += 1;

        if (numIntentosRestore > 3) {
            numIntentosRestore = 0;
            return "/index.jsp";
        }

        String token = request.getParameter("token");
        String pass = request.getParameter("pass");
        String cpass = request.getParameter("cpass");
        String msg = "";
        String bdToken;
        long bdDate;

        // Se obtiene token asignado a usuario
        // (cadena unica y fecha de creacion)
        Token t = LoginBD.getRestoreData(token);

        long currentDate = Calendar.getInstance().getTimeInMillis();

        if (t != null) {
            bdToken = t.getToken();
            bdDate = t.getDate();
            currentDate /= 1000;
            bdDate /= 1000;

            long dif_date = Math.abs((currentDate - bdDate));

            if (bdToken.equalsIgnoreCase(token)
                    && dif_date >= 0 && dif_date <= 300) {

                // Se actualiza contraseña
                if (LoginBD.makeRestore(token, pass)) {
                    msg = "* Contrase&ntilde;a restaurada correctamente. ";
                    msg += "Ahora puede iniciar sesi&oacute;n.";
                    request.setAttribute("msgLogin", msg);
                    request.setAttribute("msgType", "alert alert-success");
                    return "/index.jsp";
                } else {
                    msg = "* Contrase&ntilde;a no restaurada.";
                }
            } else if (dif_date < 0 || dif_date > 300) {
                msg = "* El tiempo para restaurar la contrase&ntilde;a a caducado. ";
                msg += "Por favor inicie de nuevo el proceso ";
                msg += "<a href='/RDW1/index.jsp'>aquí.</a><br>";

            } else {
                return "/index.jsp";
            }
        } else {
            return "/index.jsp";
        }

        request.setAttribute("msgRestore", msg);
        request.setAttribute("msgType", "alert alert-danger");
        return "/restorepass.jsp";
    }

    // Comprueba si es tipo de usuario especificado.
    public boolean esTipoUsuario(Login login, String tipo) {
        if (login.getTipousr().equalsIgnoreCase(tipo)) {
            return true;
        } else {
            return false;
        }
    }

    // Genera cadena de texto de nombres de empresa
    // a partir de un listado.
    public String nombreEmpresas(ArrayList<Empresa> lst_emp) {

        String s = "";

        for (Empresa emp : lst_emp) {
            String nombre = emp.getNombre();
            if (nombre == null || nombre == "") {
                continue;
            }
            nombre = nombre.toLowerCase();
            s += (s == "") ? nombre : "," + nombre;
        }
        return s;
    }

    // Comprueba conexion a internet.
    public String cnx(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String dirWeb = "www.google.com.co";
        String url = "";
        int puerto = 80;
        HttpSession session = request.getSession();
        try {
            Socket s = new Socket(dirWeb, puerto);
            if (s.isConnected()) {
                session.setAttribute("v", 1);
                url = "/app/usuarios/respuesta.jsp";
            }
        } catch (Exception e) {
            session.setAttribute("v", 0);
            url = "/app/usuarios/respuesta.jsp";
        }
        return url;
    }

    /**
     *
     * Mètodo para mantener activa la sesión
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String holdSessionServer(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        int maxInactiveInterval = session.getMaxInactiveInterval();
        Long creationTime = session.getCreationTime();
        Long lastAccessedTime = session.getLastAccessedTime();

        Usuario user = (Usuario) session.getAttribute("login");

        session.setAttribute("maxInactiveInterval", maxInactiveInterval);
        session.setAttribute("creationTime", creationTime);
        session.setAttribute("lastAccessedTime", lastAccessedTime);
        session.setAttribute("userInfoLogged", user);

        return "/app/usuarios/infoSession.jsp";

    }

}
