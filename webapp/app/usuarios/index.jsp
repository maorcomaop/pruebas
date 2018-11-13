

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />


<!--<div id="wrapper-container">

    <div class="container object ">

        <div id="main-container-image col-xs-12">
            <section class="work">-->
<div id="msg" class="form-msg ${msgType}">${msgLogin}</div>
<main class="mitgliederliste">

    <c:if test="${permissions.check(['Liquidacion'])}">
        <figure class="einzel">
            <c:choose>
                <c:when test="${permissions.check(['Liquidacion','LiquidarVehiculo'])}">
                    <c:choose>
                        <c:when test ="${(login.idempresa == 9)}">
                            <a  href="/RDW1/app/liquidacion/nuevaLiquidacionFusa.jsp" >
                            </c:when>
                            <c:otherwise>
                                <a  href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp" >
                                </c:otherwise>
                            </c:choose> 
                        </c:when>                    
                        <c:otherwise>
                            <a href="/RDW1/app/liquidacion/consultarLiquidacion.jsp">
                            </c:otherwise>                    
                        </c:choose>
                        <img id="lq" src="resources/img/Liquidacion.png" alt="" style="width: 250px; height: 270px;" /></a>
                    </figure>
                </c:if>
                <c:if test="${permissions.check(['Registradora','InformacionRegistradora'])}">
                    <figure class="einzel">
                        <a href="/RDW1/cargaInicialRegistradora">
                            <img id="rg" src="resources/img/Registradora.png" alt="" style="width: 250px; height: 270px;" />                           
                        </a>	
                        <!--<figcaption title="Aqu&iacute; podr&aacute; gestionar la informaci&oacute;n procesada">Registradora</figcaption>-->
                    </figure>	
                </c:if>
                <c:if test="${permissions.check(['Configuracion','Conductor','Listado'])}">
                    <figure class="einzel">
                        <a href="/RDW1/app/conductor/listadoConductor.jsp">
                            <img id="co" src="resources/img/Conductor.png" alt="" style="width: 250px; height: 270px;" />                            
                        </a>	
                        <!--<figcaption title="Aqu&iacute; podr&aacute; gestionar la informaci&oacute;n de conductores">Conductor                            
                       </figcaption>-->
                    </figure>
                </c:if>
                <c:if test="${permissions.check(['Configuracion','Vehiculos'])}">
                    <figure class="einzel">
                        <a href="/RDW1/app/movil/listadoMovil.jsp">
                            <c:choose>
                                <c:when test="${permissions.check(29)}">
                                    <a href="/RDW1/app/movil/listadoMovil.jsp">
                                    </c:when>                     
                                    <c:when test="${permissions.check(30)}">
                                        <a href="/RDW1/app/movil/nuevoMovil.jsp">
                                        </c:when> 
                                        <c:when test="${permissions.check(32)}">
                                            <a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">
                                            </c:when> 
                                            <c:when test="${permissions.check(105)}">
                                                <a href="/RDW1/app/grupo_movil/adicionarMovilesAGrupo.jsp">
                                                </c:when> 
                                                <c:when test="${permissions.check(33)}">
                                                    <a href="/RDW1/app/grupo_movil/nuevoGrupoMovil.jsp">
                                                    </c:when> 
                                                </c:choose>  
                                                <img id="mo" src="resources/img/Vehiculos.png" alt="" style="width: 250px; height: 270px;" />                            
                                            </a>
                                            <!--<figcaption title="Aqu&iacute; podr&aacute; gestionar su flota de veh&iacute;culos">Veh&iacute;culos
                                           </figcaption>-->
                                            </figure>	
                                        </c:if>
                                        <c:if test="${permissions.check(['Usuarios'])}">
                                            <figure class="einzel">
                                                <c:choose>
                                                    <c:when test="${permissions.check(['Usuarios','ListadoUsuarios'])}">
                                                        <a  href="/RDW1/app/usuarios/consultaUsuario.jsp">
                                                        </c:when>                     
                                                        <c:when test="${permissions.check(['Usuarios','RegistroUsuarios'])}">
                                                            <a href="/RDW1/app/usuarios/nuevoUsuario.jsp">
                                                            </c:when> 
                                                            <c:when test="${permissions.check(11)}">
                                                                <a href="/RDW1/app/perfil/listadoPerfil.jsp">
                                                                </c:when> 
                                                                <c:when test="${permissions.check(12)}">
                                                                    <a href="<c:url value='/nuevoPerfil' />">
                                                                    </c:when> 
                                                                </c:choose>
                                                                <img id="us" src="resources/img/Usuarios.png" alt="" style="width: 250px; height: 270px;" />     
                                                            </a>
                                                            </figure>	
                                                        </c:if>
                                                        <c:if test="${permissions.check(['Despacho'])}">
                                                            <figure class="einzel">
                                                                <c:choose>
                                                                    <c:when test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                                                                        <a href="/RDW1/app/despacho/nuevoDespacho.jsp">
                                                                        </c:when>
                                                                        <c:when test="${permissions.check(['Despacho','ListadoDespachos'])}">
                                                                            <a href="/RDW1/app/despacho/consultaDespacho.jsp">
                                                                            </c:when>
                                                                            <c:when test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
                                                                                <a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">
                                                                                </c:when>
                                                                                <c:when test="${permissions.check(['Despacho','EstadoDespacho'])}">
                                                                                    <a href="/RDW1/app/despacho/estadoDespacho.jsp">
                                                                                    </c:when>
                                                                                    <c:when test="${permissions.check(['Despacho','ControlDespacho'])}">
                                                                                        <a href="/RDW1/app/despacho/verControlDespacho.jsp">
                                                                                        </c:when>
                                                                                        <c:when test="${permissions.check(['Despacho','ProgramacionDeRuta'])}">
                                                                                            <a href="/RDW1/app/despacho/programacionRuta.jsp">
                                                                                            </c:when>    
                                                                                        </c:choose>
                                                                                        <img id="de" src="resources/img/Despacho.png" alt="" style="width: 250px; height: 270px;" />                                                
                                                                                    </a>                                            
                                                                                    <!--<figcaption title="Aqu&iacute; podr&aacute; gestionar la programaci&oacute;n de los veh&iacute;culos">Despacho
                                                                            </figcaption>-->
                                                                                    </figure>	
                                                                                </c:if>
                                                                                <c:if test="${permissions.check(['SeguimientoGPS'])}">
                                                                                    <figure class="einzel">
                                                                                        <a href="/RDW1/app/seguimiento/gps2.jsp">
                                                                                            <img id="gps" src="resources/img/Rastreo.png" alt="" style="width: 250px; height: 270px;" />                                                    
                                                                                        </a>                                                
                                                                                        <!--<figcaption title="Aqu&iacute; podr&aacute; gestionar la ubicaci&oacute;n de los veh&iacute;culos">Rastreo
                                                                                        </figcaption>-->
                                                                                    </figure>	
                                                                                </c:if>
                                                                                <c:if test="${permissions.check(['Reportes'])}">
                                                                                    <figure class="einzel">
                                                                                        <a href="/RDW1/app/reportes/generaReporte.jsp">
                                                                                            <img id="rp" src="resources/img/Reportes.png" alt="" style="width: 250px; height: 270px;" />                                                    
                                                                                        </a>	                                                
                                                                                        <!--<figcaption title="Aqu&iacute; podr&aacute; obtener informes detallados">Reportes
                                                                                        </figcaption>-->
                                                                                    </figure>	
                                                                                </c:if>   
                                                                                </main>



                <div id="modalAsignarClave" data-backdrop="static" data-keyboard="false" class="modal fade">   
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header bg-primary">
                            <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                            <h4 class="modal-title">Registro de producto</h4>
                            <input type="hidden" id="l" value="${licencia}"/>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" action="<c:url value='/asignarLicencia' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="inputName">Identificacion*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="controls">
                                            <input type="text" name="" id="numero_documento" class="form-control" value="${login.enterprice.nit}" readonly=""/>
                                        </div>
                                    </div>                            
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="inputName">Cliente</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="controls">                                            
                                            <input type="text" name="" id="nombre_cliente" value="${login.enterprice.nombre}" class="form-control" readonly=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="inputName">Tipo Clave</label>
                                <div class="controls">
                                    <select class="selectpicker" data-actions-box="true" size="3" title="Seleccione Tipo" data-width="170px" data-style="btn-primary" name="" id="tipo_clave">
                                        <option value="1">Clave de prueba - 30 d&iacute;as</option>
                                        <option value="2">Clave trimestral - 3 meses</option>                                        
                                        <option value="3">Clave anual - 1 a&ntilde;o</option>                                        
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="inputName">Clave de licencia</label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="controls">
                                            <input type="text" name="" id="clave" class="form-control" />
                                        </div>
                                    </div>                            
                                </div>
                            </div>                                                        
                        </form>
                        </div>
                        <div class="modal-footer">
                            <a href="#" role="button"  class="btn btn-primary" id="RegistrarClave">Asignar</a>
                            <!--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
                            <!--<button type="submit" class="btn btn-primary">Save changes</button>-->
                        </div>
                    </div>
                </div>
    </div>            



                                                                                <jsp:include page="/include/footerHome.jsp" />
<script>
    $("#main-container-image").delay(1250).animate({opacity: '1'}, 250);        
    /*function hola() {   alert("Hola");}                                                                                     
    jsCron.set("49 * * * *", hola);*/
    
        $("#RegistrarClave").click(function () {                
            $.post("/RDW1/asignarLicencia", { num_doc: $("#numero_documento").val(),
                                         nom_cliente:$("#nombre_cliente").val(),
                                         fk_tipo_licencia:$("#tipo_clave").val(),
                                         licencia:$("#clave").val()},
                function (result) {                        
                        if($.trim(result) === '1'){
                            /*$("#respuesta").html("<div class='alert alert-info'><button type='button' class='close fade' data-dismiss='alert'>&times;</button><strong>!!! FELICITACIONES </strong> ha registrado su producto</div>");*/
                            alert("¡¡¡ Gracias por Registrarte !!!");
                            $("#modalAsignarClace").modal("hide");                            
                            window.location.reload(true);                            
                        }
                        }); 
                    });        
    
      $(document).ready(function () {
        
        var urlBrowser = window.location.href;
        var lc = $("#l").val();
        if(lc === '1'){
            $("#modalAsignarClave").modal("show");
        }
        else{
            console.log("tiene licencia");
        }       
        
        
        if (urlBrowser.endsWith('/iniciarSesion') || urlBrowser.endsWith('/iniciarSesion#')){
            $("#lq").attr("src", "resources/img/Liquidacion.png");
            $("#co").attr("src", "resources/img/Conductor.png");
            $("#mo").attr("src", "resources/img/Vehiculos.png");
            $("#us").attr("src", "resources/img/Usuarios.png");
            $("#de").attr("src", "resources/img/Despacho.png");
            $("#gps").attr("src", "resources/img/Rastreo.png");
            $("#rp").attr("src", "resources/img/Reportes.png");
        } else{
            $("#lq").attr("src", "../../resources/img/Liquidacion.png");
            $("#rg").attr("src", "../../resources/img/Registradora.png");
            $("#co").attr("src", "../../resources/img/Conductor.png");
            $("#mo").attr("src", "../../resources/img/Vehiculos.png");
            $("#us").attr("src", "../../resources/img/Usuarios.png");
            $("#de").attr("src", "../../resources/img/Despacho.png");
            $("#gps").attr("src", "../../resources/img/Rastreo.png");
            $("#rp").attr("src", "../../resources/img/Reportes.png");
        }
    });
    </script>
    <jsp:include page="/include/end.jsp" />
