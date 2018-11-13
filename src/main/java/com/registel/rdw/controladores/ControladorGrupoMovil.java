/**
 * Clase controlador GrupoMovil
 * Permite establecer la comunicacion entre la vista del usuario y el modelo de la aplicacion
 * Creada por: JAIR HERNANDO VIDAL 19/10/2016 9:15:15 AM
 */
package com.registel.rdw.controladores;

import com.registel.rdw.datos.EmpresaBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.GrupoMovilBD;
import com.registel.rdw.datos.SelectBD;
import com.registel.rdw.logica.Empresa;
import com.registel.rdw.logica.GrupoMovil;
import com.registel.rdw.logica.GrupoVehiculo;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.logica.VehiculoNuevoListado;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ControladorGrupoMovil extends HttpServlet {
    
    private static final String USR_EMPR = "empr";
    private static final String USR_COND = "cond";    
    private static final String USR_PROP = "prop";
    
    //private String lnk = "Para asignarle un nuevo perfil, haga click <a href='/RDW1/app/perfil/nuevoPerfil.jsp'>aqui.</a>";
    
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
        //String ulr1=request.getParameter("url1");        
        String requestURI = request.getRequestURI();
        
        String url = "/";
        if (requestURI.endsWith("/guardarGrupoMovil")) {
            url = guardarGrupoMovil(request, response);
        } else if (requestURI.endsWith("/editarGrupoMovil")) {
            url = editarOrdenGrupoMovil(request, response);
        } else if (requestURI.endsWith("/verMasGrupoMovil")) {
            url = verGrupoMovil(request, response);        
        } else if (requestURI.endsWith("/eliminarGrupoMovil")) {
            url = eliminarGrupoMovil(request, response);
        }else if (requestURI.endsWith("/adicionarVehiculosAGrupo")) {
            url = adicionarVehiculosAGrupoMovil(request, response);
        }else if (requestURI.endsWith("/adicionarRutasAGrupo")) {
            url = eliminarGrupoMovil(request, response);
        }else if (requestURI.endsWith("/movilesDeGrupo")) {            
                url = movilesQuePertenecenAGrupo(request, response);            
        }else if (requestURI.endsWith("/buscarMovilesDeGrupo")) {            
                url = buscarMoviles(request, response);            
        } else if (requestURI.endsWith("/eliminarMovilDeGrupo")) {            
                url = eliminarUnMovilDeGrupo(request, response);            
        } 
        else if (requestURI.endsWith("/consultarGrupoDefecto")) {            
                url = obtenerGrupoPorDefecto(request, response);            
        } 
        
        //response.sendRedirect("login.jsp");
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
   
      /**FUNCION QUE PERMITE GUARDAR UN NUEVO PERFIL DE USUARIO**/ 
    public String guardarGrupoMovil (HttpServletRequest request,
            HttpServletResponse response) {
                        
        
        String nombre = request.getParameter("nombre");
        String aplicaTiempos = request.getParameter("aplica");   
        String adicionarRutas = request.getParameter("adicionar_rutas");           
        String[] Rutas = request.getParameterValues("rutas");           
        String url="";       
        int idRutas[] = null;
                
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("login");                     
        Empresa e = EmpresaBD.getById(u.getIdempresa());
         
        GrupoMovil c = new GrupoMovil();
        try 
        {
            c.setCodEmpresa(e.getId() );
            c.setNombreGrupo(nombre);
            if ( aplicaTiempos != null ) 
            {
                c.setAplicaTiempos(Integer.parseInt(aplicaTiempos));
            }
            else
            {
                c.setAplicaTiempos(0);
            }
            if ( adicionarRutas != null ) 
            {
                idRutas =new int[Rutas.length];
                for (int i = 0; i < Rutas.length; i++) 
                {
                    idRutas[i] = Integer.parseInt(Rutas[i]);
                }
            }       
            c.setFkRutas(idRutas);
            c.setEstado(new Short("1"));
        }
        catch (Exception ex) 
        {
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el grupo");
            return "/app/grupo_movil/listadoGrupoMovil.jsp";
        }
        
        
        SelectBD select =null;                       
        try {
            int resultado = GrupoMovilBD.insert(c);
            switch (resultado) {
                case 0:
                    select = new SelectBD(request);
                    session.setAttribute("select", select);
                    request.setAttribute("msg", "No se logr&oacute establecer el nuevo grupo por que ya se encuentra creado");
                    request.setAttribute("idInfo", 2);
                    url = "/app/grupo_movil/listadoGrupoMovil.jsp";
                    break;                
                case 1:                    
                    select = new SelectBD(request);
                    session.setAttribute("select", select);                    
                    request.setAttribute("idInfo", 1);
                    request.setAttribute("msg", "Se ha creado un nuevo grupo de veh&iacute;culos");
                    url = "/app/grupo_movil/nuevoGrupoMovil.jsp";
                    break;                
                default:
                    break;
            }         
            
        } catch (ExisteRegistroBD ex) {
            request.setAttribute("msg", ex.getMessage());
        }         
        return url;
    }    
   
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String editarOrdenGrupoMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String url="";
        HttpSession session = request.getSession();
        
        int id = Integer.parseInt( request.getParameter("id_edit") );
        
        String nombre = request.getParameter("nombre_edit");      
        String aplicaTiempos =  request.getParameter("aplica");
        String listado =  request.getParameter("listado_vh");        
        Usuario u = (Usuario) session.getAttribute("login");                     
        Empresa e = EmpresaBD.getById(u.getIdempresa());
        
        JSONArray myArray = null;                 
        GrupoMovil c = new GrupoMovil();
         SelectBD select =null; 
        try 
        {            
            c.setId(id);        
            c.setFkGrupo(id);
            c.setCodEmpresa(e.getId());
            c.setNombreGrupo(nombre); 
           
            if (aplicaTiempos != null)
            {
                c.setAplicaTiempos(1);        
            }
            else
            {
                c.setAplicaTiempos(0);        
            }            
            c.setEstado(1);
            /**************************************************/
                    int resultado = GrupoMovilBD.update(c);  
                    if (!listado.equals("")) {
                        myArray = new JSONArray(listado);
                        ArrayList<VehiculoNuevoListado> list = new ArrayList<VehiculoNuevoListado>();            
                        for (int i=0; i<myArray.length(); i++) {
                            JSONObject jsonObject = myArray.getJSONObject(i);    
                            //System.out.print(">>>"+jsonObject.getInt("id_vh")+"\t"+jsonObject.getInt("pk")+"\t"+jsonObject.getString("placa")+"\t"+jsonObject.getString("num"));
                            //System.out.println();
                            VehiculoNuevoListado vh = new VehiculoNuevoListado();
                            vh.setId(jsonObject.getInt("pk"));
                            vh.setPlaca(jsonObject.getString("placa"));
                            vh.setNumero_interno(jsonObject.getString("num"));                
                            list.add(vh);
                        }
                        c.setFkVehiculosNuevoOrden(list);
                         int r1 = GrupoMovilBD.deleteCarsOfGroup(c);
                            if (r1 > 0 ) {                
                                int r2 = GrupoMovilBD.addNewOrderCarsOfGroup(c);
                                if (r2>0) {}
                            }
                    }/*fin si lista vacia*/
                        switch (resultado) {
                          case 0:
                              select = new SelectBD(request);
                              session.setAttribute("select", select);
                              request.setAttribute("msg", "No logr&oacute; establecer un nuevo grupo");
                              request.setAttribute("idInfo", 0);
                              url = "/app/grupo_movil/listadoGrupoMovil.jsp";
                              break;                
                          case 1:                    
                              select = new SelectBD(request);
                              session.setAttribute("select", select);
                              request.setAttribute("idInfo", 1);
                              ArrayList retorno = GrupoMovilBD.selectByAllMovilesGroup(c);
                              GrupoMovil grupoEncontrado = GrupoMovilBD.selectByOneNew(c);
                              if (grupoEncontrado != null) {
                                  session.setAttribute("grupoEncontrado", grupoEncontrado);
                                  session.setAttribute("vehiculos", retorno);
                                  request.setAttribute("msg", "Se ha modificado el grupo de vehiculos");
                              }                    
                              url = "/app/grupo_movil/unGrupoMovil.jsp";
                              break;
                          default:
                              break;
                      }
           
            
        }/*fin try*/
        catch (Exception ex) 
        {
            System.err.println(ex.getMessage());
            request.setAttribute("idInfo", 2);
            request.setAttribute("msg", "No se puede registrar el grupo de veh&iacute;culos");
            return "/app/grupo_movil/listadoGrupoMovil.jsp";
        }
         
                
        
                                        
        
        return url;
    }    
    
    /**FUNCION QUE PERMITE ELIMINAR**/ 
    public String eliminarGrupoMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");
        String estado =  request.getParameter("estado");
        String url="";        
        GrupoMovil c = new GrupoMovil();        
        c.setId(Integer.parseInt(id));                        
        c.setEstado(Integer.parseInt(estado));
        HttpSession session = request.getSession();
        
        try {
            GrupoMovilBD.updateState(c);
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorGrupoMovil.class.getName()).log(Level.SEVERE, null, ex);
        }
        SelectBD select = new SelectBD(request);
        session.setAttribute("select", select);
        url = "/app/grupo_movil/listadoGrupoMovil.jsp";
         return url;
    }
    public String movilesQuePertenecenAGrupo(HttpServletRequest request,
            HttpServletResponse response)
    {
        String id =  request.getParameter("id_grupo");
        String url="";                   
        HttpSession session = request.getSession();
        GrupoMovil gm = new GrupoMovil();
        gm.setId(Integer.parseInt(id));
        gm.setEstado(1);
        try {
                ArrayList retorno = GrupoMovilBD.selectByAllMovilesGroup(gm);
                session.setAttribute("vehiculos", retorno);                
                url = "/app/grupo_movil/vehiculoQuePertenecenAGrupo.jsp";
               
        } catch (ExisteRegistroBD ex) {
            System.out.println("ERROR "+ex);
        }
         return url;
    }
    
    /**FUNCION QUE PERMITE EDITAR UN NUEVO PERFIL DE USUARIO**/ 
    public String verGrupoMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String id =  request.getParameter("id");                
        String url="";
        GrupoMovil c = new GrupoMovil();
        c.setId(Integer.parseInt(id));  
        c.setEstado(1);
        
        SelectBD select = new SelectBD(request);
        HttpSession session = request.getSession();
        try {
                GrupoMovil grupoMovilEncontrado = GrupoMovilBD.selectByOne(c);
                ArrayList todosLosVehiculosAsociadosAUnGrupo = GrupoMovilBD.selectByAllMovilesGroup(c);
                 
                if (grupoMovilEncontrado != null) {
                session.setAttribute("grupoEncontrado", grupoMovilEncontrado);
                session.setAttribute("vehiculos", todosLosVehiculosAsociadosAUnGrupo);       
                session.setAttribute("select", select);
                url = "/app/grupo_movil/unGrupoMovil.jsp";
            } else {
                url = "/app/grupo_movil/listadoGrupoMovil.jsp";
            }
               
        } catch (ExisteRegistroBD ex) {
            Logger.getLogger(ControladorGrupoMovil.class.getName()).log(Level.SEVERE, null, ex);
        }
         return url;
    }
     
    public String buscarMoviles(HttpServletRequest request,
            HttpServletResponse response)
    {
        int id = Integer.parseInt(request.getParameter("id_grupo")); 
        String url="";                   
        HttpSession session = request.getSession();
        GrupoMovil gm = new GrupoMovil();
        gm.setId(id);
        gm.setEstado(1);
        ArrayList retorno = new ArrayList();;
        try {
            switch(id)
            {
                case 1:{                    
                    retorno = GrupoMovilBD.searchAllMovilesGroup(gm);
                    break;
                }
                case 2:{                    
                    retorno = GrupoMovilBD.searchAllMovilesNotGroup(gm);
                    break;
                }
                default:{
                    
                }
            }
            session.setAttribute("vehiculos", retorno);
            url = "/app/grupo_movil/buscarMoviles.jsp";
               
        } catch (ExisteRegistroBD ex) {
            System.out.println("ERROR "+ex);
        }
         return url;
    }
        
    
    public String eliminarUnMovilDeGrupo(HttpServletRequest request,
            HttpServletResponse response)
    {
        String id_vh =  request.getParameter("id");
        String id_grupo =  request.getParameter("id_grupo");        
        String estado =  request.getParameter("estado");
        String url="";                   
        HttpSession session = request.getSession();
        GrupoMovil gm = new GrupoMovil();   
        SelectBD select = new SelectBD(request);
        try {                
                gm.setId(Integer.parseInt(id_grupo));
                gm.setFkVehiculo(Integer.parseInt(id_vh));
                gm.setEstado(Integer.parseInt(estado));
                int valor = GrupoMovilBD.updateStateMovilInGroup(gm);
                if (valor > 0) {   
                    gm.setEstado(1);
                    GrupoMovil grupoMovilEncontrado = GrupoMovilBD.selectOneMovilGroup(gm);
                    ArrayList todosLosVehiculosAsociadosAUnGrupo = GrupoMovilBD.selectByAllMovilesGroup(gm);
                    if (grupoMovilEncontrado != null) {
                            session.setAttribute("grupoEncontrado", grupoMovilEncontrado);
                            session.setAttribute("vehiculos", todosLosVehiculosAsociadosAUnGrupo);       
                            session.setAttribute("select", select);
                            //request.getSession().setAttribute("id", gm);                
                            url = "/app/grupo_movil/unGrupoMovil.jsp";
                    }//IF GRUPO
                }//IF ACTUALIZADO
                else
                {
                    url = "/app/grupo_movil/listadoGrupoMovil.jsp";
                }
               
        } catch (ExisteRegistroBD ex) {
            System.out.println("ERROR "+ex);
        } 
         return url;
    }
    
    
        public String adicionarVehiculosAGrupoMovil (HttpServletRequest request,
            HttpServletResponse response) {
        
        String idgrupo =  request.getParameter("grupo");
        String[] vehiculos =  request.getParameterValues("vehiculo");    
        String aplicaVariosGrupos =  request.getParameter("vehiculos_libres");
               
        ArrayList listaVh = new ArrayList();
        String url="";        
        GrupoMovil c = new GrupoMovil();
        HttpSession session = request.getSession();
        SelectBD select = null;
        StringBuilder placas= null;    
            try 
            {   /*CONTROLA SI LA LISTA VIENE NULA O NO TIENE VEHICULOS DESDE LA INTERFAZ*/             
                if ( vehiculos != null ) 
                {                     
                    for (int i = 0; i < vehiculos.length; i++) 
                    {
                        int retorno = GrupoMovilBD.selectById(Integer.parseInt(vehiculos[i]), Integer.parseInt(idgrupo));
                        if (retorno == 0) {
                            listaVh.add(Integer.parseInt(vehiculos[i]));
                        }
                    }/*CONTROLA SI DESPUES BUSCAR LOS VEHICULOS LA NUEVA LISTA TIENE VEHICULOS*/
                    if (listaVh.size() > 0) {
                        c.setFkGrupo(Integer.parseInt(idgrupo));
                        c.setFkVehiculos(listaVh);
                        c.setEstado(1);
                        int resultado = GrupoMovilBD.addMoviltoGroup(c);
                        if (resultado > 0) {
                            resultado = 1;
                        }                
                        switch (resultado) {
                           case 0:
                               select = new SelectBD(request);
                               session.setAttribute("select", select);
                               request.setAttribute("idInfo", 0);                    
                               request.setAttribute("msg", "No logr&oacute; adicionar veh&iacute;culos al grupo");                    
                               break;                
                           case 1:                    
                               select = new SelectBD(request);
                               request.setAttribute("idInfo", 1);
                               session.setAttribute("select", select);
                               placas = new StringBuilder();
                               request.setAttribute("msg", "Se han adicionado los veh&iacute;culos al grupo ");                    
                               break;
                           default:
                               break;
                       }/*FIN SWITCH*/
                    }else{/*FIN LISTA DE VEHICULOS*/
                        throw new Exception();
                    }                    
                }/*FIN SI LISTA VEHICULOS*/
                else
                {                    
                    throw new Exception();
                }
            }
            catch (Exception e) 
            {
                System.err.println(e.getMessage());
                request.setAttribute("idInfo", 2);
                request.setAttribute("msg", "No se pueden asociar los vehiculos al grupo; Algunos ya pertenecen a este grupo");
                return "/app/grupo_movil/adicionarMovilesAGrupo.jsp";
            }        
                           
        return "/app/grupo_movil/adicionarMovilesAGrupo.jsp";
    }
        
        
        
        
        
        public String obtenerGrupoPorDefecto (HttpServletRequest request,
            HttpServletResponse response) {
        String url="";        
        String nombre_grupo =  request.getParameter("gp");
        HttpSession session = request.getSession(); 
        GrupoMovil p = new GrupoMovil();
        p.setNombreGrupo(nombre_grupo);
        GrupoMovil retorno = GrupoMovilBD.selectGroupforDefault(p);
        session.setAttribute("gp", retorno);
        url = "/app/movil/grupo.jsp";        
         return url;
    }
}

