<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div id="navbar">
    <div class="offline-ui offline-ui-up">
        <div class="offline-ui-content">
        </div>    
    </div>

    <div id="alert_ui" name="alert_ui" class="alert-app-ui">
        <div>Ha finalizado la sesi&oacute;n.</div>
    </div>

    <nav class="navbar navbar-default  navbar-light  navbar-static-top" role="navigation" style="background-color: #1F4E8E;">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/RDW1/app/usuarios/index.jsp">
                        <i class="icon-home"></i>&nbsp;&nbsp;Inicio
                    </a>
                </li>
                <c:if test="${permissions.check(['Indicadores'])}">
                    <li>
                        <a href="/RDW1/app/indicadores/inicio_def.jsp">
                            <i class="glyphicon glyphicon-stats"></i>&nbsp;&nbsp;Indicadores
                        </a>
                    </li>
                </c:if>
                <c:if test="${permissions.check(['Auditoria'])}">
                    <li>
                        <a href="/RDW1/app/auditoria/buscarAuditoria.jsp">
                            <i class="glyphicon glyphicon-eye-open"></i>&nbsp;&nbsp;Auditor&iacute;as
                        </a>
                    </li>
                </c:if>

                <c:if test="${permissions.check(['Liquidacion'])}">
                    <li> 
                        <c:choose>
                            <c:when test="${permissions.check(['Liquidacion','LiquidarVehiculo'])}">
                                <c:choose>
                                    <c:when test ="${(login.idempresa == 9)}">
                                        <a href="/RDW1/app/liquidacion/nuevaLiquidacionFusa.jsp" >
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp" >
                                            </c:otherwise>
                                        </c:choose> 
                                    </c:when>                    
                                    <c:otherwise>
                                        <a href="/RDW1/app/liquidacion/consultarLiquidacion.jsp">
                                        </c:otherwise>                    
                                    </c:choose>
                                    <i class="glyphicon glyphicon-usd"></i>&nbsp;&nbsp;Liquidaci&oacute;n</a>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Usuarios'])}"> 
                                <li class="dropdown">
                                    <a  href="#">
                                        <i class="icon-user"></i>&nbsp;&nbsp;Usuarios
                                    </a>
                                    <ul class="dropdown-menu">
                                        <c:if test="${permissions.check(9)}">
                                            <li>
                                                <a href="/RDW1/app/usuarios/nuevoUsuario.jsp">Registro</a>
                                            </li>
                                        </c:if>

                                        <c:if test="${permissions.check(90)}">
                                            <li>
                                                <a href="/RDW1/app/usuarios/consultaUsuario.jsp">Listado</a>
                                            </li>
                                        </c:if>

                                        <c:if test="${permissions.check(['Usuarios','Perfiles'])}">
                                            <li class="dropdown dropdown-submenu">
                                                <a class="dropdown-toggle" href="#">Perfiles</a>
                                                <ul class="dropdown-menu">
                                                    <c:if test="${permissions.check(11)}">
                                                        <li>
                                                            <a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(12)}">
                                                        <li>
                                                            <a href="<c:url value='/nuevoPerfil'/>">Registro</a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </li>
                                        </c:if>
                                    </ul>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Registradora'])}">
                                <li>
                                    <a href="/RDW1/cargaInicialRegistradora">
                                        <i class="glyphicon glyphicon-transfer"></i>&nbsp;&nbsp;Registradora
                                    </a>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Reportes'])}">
                                <li>
                                    <a href="/RDW1/app/reportes/generaReporte.jsp">
                                        <i class="glyphicon glyphicon-duplicate"></i>&nbsp;&nbsp;Reportes
                                    </a>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['SeguimientoGPS'])}">
                                <!--
                                <li>
                                    <a href="/RDW1/app/seguimiento/gps.jsp">
                                        <i class="glyphicon glyphicon-globe"></i>&nbsp;&nbsp;Rastreo
                                    </a>
                                </li>
                                -->
                                <li class="dropdown">
                                    <a id="menu-item-rastreo" href="#">
                                        <i class="glyphicon glyphicon-globe"></i>&nbsp;&nbsp;Rastreo
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="/RDW1/app/seguimiento/gps2.jsp">Ver mapa</a>
                                        </li>
                                        <li>
                                            <a href="/RDW1/app/seguimiento/infogps.jsp">M&oacute;viles</a>
                                        </li>
                                        <c:if test="${permissions.check(219)}">
                                            <li>
                                                <a href="/RDW1/app/seguimiento/comandos.jsp">Comandos</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Despacho'])}">
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                        <i class="glyphicon glyphicon-calendar"></i>&nbsp;&nbsp;Despacho
                                    </a>
                                    <ul class="dropdown-menu">              
                                        <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                                            <li>
                                                <a href="/RDW1/app/despacho/nuevoDespacho.jsp">Configuraci&oacute;n</a>
                                            </li>        
                                        </c:if>
                                        <c:if test="${permissions.check(['Despacho','ListadoDespachos'])}">
                                            <li>
                                                <a href="/RDW1/app/despacho/consultaDespacho.jsp">Listado</a>
                                            </li>        
                                        </c:if>    
                                        <c:if test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
                                            <li>
                                                <a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a>
                                            </li>        
                                            <li>
                                                <a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a>
                                            </li>        
                                            <li>
                                                <a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir veh&iacute;culo</a>
                                            </li>
                                        </c:if>                                            
                                        <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                                            <li>
                                                <a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a>
                                            </li>        
                                        </c:if>
                                        <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                                            <li>
                                                <a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a>
                                            </li>        
                                        </c:if>
                                        <c:if test="${permissions.check(['Despacho','ProgramacionDeRuta'])}">
                                            <li>
                                                <a href="/RDW1/app/despacho/programacionRuta.jsp">Programaci&oacute;n de Ruta</a>
                                            </li>               
                                        </c:if>
                                    </ul>
                                </li>
                            </c:if>
                                
                            <c:if test="${permissions.check(['Configuracion'])}">
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                        <i class="glyphicon glyphicon-cog"></i>&nbsp;&nbsp;Configuraci&oacute;n
                                    </a>

                                    <ul class="dropdown-menu">
                                        <c:choose>
                                            <c:when test="${permissions.check(20)}">
                                                <li>
                                                    <a href="/RDW1/app/alarma/listadoAlarma.jsp">Alarmas</a>
                                                </li> 
                                            </c:when>                    
                                            <c:when test="${permissions.check(19)}">
                                                <li>
                                                    <a href="/RDW1/app/alarma/nuevoAlarma.jsp">Alarmas</a>
                                                </li>
                                            </c:when>                    
                                        </c:choose>
                                        <c:if test="${permissions.check(['Configuracion','Empresa'])}">
                                            <li class="dropdown dropdown-submenu">
                                                <a class="dropdown-toggle"  href="#">Empresa</a>
                                                <ul class="dropdown-menu">       
                                                    <c:if test="${permissions.check(98)}">
                                                        <li>
                                                            <a href="/RDW1/app/usuarios/consultaEmpresa.jsp">Listado</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(22)}">
                                                        <li>
                                                            <a href="/RDW1/app/usuarios/nuevaEmpresa.jsp">Registro</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(['Configuracion','Empresa','RelacionEmpresaVehiculo'])}">
                                                        <c:choose>
                                                            <c:when test="${permissions.check(26)}">
                                                                <li>
                                                                    <a href="/RDW1/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp">Empresa - Veh&iacute;culo</a>
                                                                </li>
                                                            </c:when>                    
                                                            <c:when test="${permissions.check(27)}">
                                                                <li>
                                                                    <a href="/RDW1/app/relacion_empresa_movil/nuevoRelacionEmpresaVehiculo.jsp">Empresa - Veh&iacute;culo</a>
                                                                </li>
                                                            </c:when>                    
                                                        </c:choose>
                                                    </c:if>
                                                </ul>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(['Configuracion','Vehiculos'])}">
                                            <li  class="dropdown dropdown-submenu">
                                                <a class="dropdown-toggle" href="#">Veh&iacute;culos</a>
                                                <ul class="dropdown-menu">       
                                                    <li>
                                                        <a href="/RDW1/app/propietario/nuevoPropietario.jsp">Propietario</a>
                                                    </li>
                                                    <c:if test="${permissions.check(29)}">
                                                        <li>
                                                            <a href="/RDW1/app/movil/listadoMovil.jsp">Listado</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(30)}">
                                                        <li>
                                                            <a href="/RDW1/app/movil/nuevoMovil.jsp">Registro</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles'])}">
                                                        <c:choose>
                                                            <c:when test="${permissions.check(32)}">
                                                                <li>
                                                                    <a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Grupos de Veh&iacute;culos</a>
                                                                </li>
                                                            </c:when>                    
                                                            <c:when test="${permissions.check(105)}">
                                                                <li>
                                                                    <a href="/RDW1/app/grupo_movil/adicionarMovilesAGrupo.jsp">Grupos de Veh&iacute;culos</a>
                                                                </li>
                                                            </c:when>                    
                                                            <c:when test="${permissions.check(33)}">
                                                                <li>
                                                                    <a href="/RDW1/app/grupo_movil/nuevoGrupoMovil.jsp">Grupos de Veh&iacute;culos</a>
                                                                </li>
                                                            </c:when>                    
                                                        </c:choose>
                                                    </c:if>
                                                    <li class="dropdown dropdown-submenu">
                                                        <a class="dropdown-toggle" href="#">Soat</a>
                                                        <ul class="dropdown-menu">
                                                            <li>
                                                                <a href="/RDW1/app/soat/nuevoSoat.jsp">Soat</a>
                                                            </li>
                                                            <li>
                                                                <a href="/RDW1/app/soat/nuevoSoat.jsp">Aseguradora</a>
                                                            </li>
                                                        </ul>  
                                                    </li>
                                                    <li class="dropdown dropdown-submenu">
                                                        <a class="dropdown-toggle" href="#">Revision Tecnico Mecanica</a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="/RDW1/app/revisionTMecanica/nuevoRevisionTMecanica.jsp">Revision T&eacute;cnico Mec&aacute;nica</a></li>
                                                            <li><a href="/RDW1/app/centro_diagnostico/nuevoCentroDiagnostico.jsp">Centro de diagnostico</a></li>
                                                        </ul>  
                                                    </li>
                                                    <li  class="dropdown dropdown-submenu">
                                                        <a class="dropdown-toggle" href="#">Tarjeta de operacion</a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="/RDW1/app/centro_expedicion/nuevoCentroExpedicion.jsp">Centro de Expedicion</a></li>
                                                            <li><a href="/RDW1/app/tarjetaDeOperacion/nuevoTarjetaOperacion.jsp">Tarjeta Operacion</a></li>
                                                        </ul>  
                                                    </li>                                                      
                                                    <!--<li  class="dropdown dropdown-submenu">
                                                        <a class="dropdown-toggle" href="#">GPS</a>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="/RDW1/app/inventarioGPS/nuevoGps.jsp">Registrar</a></li>                                                                
                                                        </ul>  
                                                    </li> -->
                                                </ul>
                                            </li>
                                        </c:if>

                                        <c:if test="${permissions.check(['Configuracion','Liquidacion'])}">
                                            <li class="dropdown dropdown-submenu">
                                                <a class="dropdown-toggle" href="#">Liquidaci&oacute;n</a>
                                                <ul class="dropdown-menu">                                                 
                                                    <c:choose>
                                                        <c:when test="${permissions.check(112)}">
                                                            <li>
                                                                <a href="/RDW1/app/categorias_de_descuento/listadoCategorias.jsp">Categor&iacute;as de Descuento</a>
                                                            </li>
                                                        </c:when>                    
                                                        <c:when test="${permissions.check(111)}">
                                                            <li>
                                                                <a href="/RDW1/app/categorias_de_descuento/nuevaCategoria.jsp">Categor&iacute;as de Descuento</a>
                                                            </li>
                                                        </c:when>                    
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${permissions.check(108)}">
                                                            <li>
                                                                <a href="/RDW1/app/usuarios/consultaTarifa.jsp">Tarifas</a>
                                                            </li>
                                                        </c:when>                    
                                                        <c:when test="${permissions.check(48)}">
                                                            <li>
                                                                <a href="/RDW1/app/usuarios/nuevaTarifa.jsp">Tarifas</a>
                                                            </li>
                                                        </c:when>                    
                                                    </c:choose>
                                                    <c:if test="${permissions.check(231)}">
                                                        <li>
                                                            <a href="/RDW1/app/liquidacion/nuevaConfiguracionLiquidacion.jsp">Configurar Liquidación</a>
                                                        </li>
                                                    </c:if>                    
                                                    <c:if test="${permissions.check(232)}">
                                                        <li>
                                                            <a href="/RDW1/app/liquidacion/listadoConfiguracionesLiquidacion.jsp">Liquidaciones Configuradas</a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </li>
                                        </c:if>

                                        <c:if test="${permissions.check(['Configuracion','Rutas'])}">
                                            <li class="dropdown dropdown-submenu">
                                                <a class="dropdown-toggle" href="#">Rutas</a>
                                                <ul class="dropdown-menu"> 
                                                    <c:if test="${permissions.check(50)}">
                                                        <li>
                                                            <a href="/RDW1/app/usuarios/nuevaRuta.jsp">Registro</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(115)}">
                                                        <li>
                                                            <a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado</a>
                                                        </li>
                                                    </c:if>
                                                    <c:choose>
                                                        <c:when test="${permissions.check(126)}">
                                                            <li>
                                                                <a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Ajustes</a>
                                                            </li>
                                                        </c:when>                    
                                                        <c:when test="${permissions.check(127)}">
                                                            <li>
                                                                <a href="/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp">Ajustes</a>
                                                            </li>
                                                        </c:when>                    
                                                        <c:when test="${permissions.check(128)}">
                                                            <li>
                                                                <a href="/RDW1/app/usuarios/longitudRuta.jsp">Ajustes</a>
                                                            </li>
                                                        </c:when>                    
                                                    </c:choose>
                                                    <c:if test="${permissions.check(51)}">
                                                        <li>
                                                            <a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos de Control</a>
                                                        </li>
                                                    </c:if>                    
                                                    <c:if test="${permissions.check(53)}">
                                                        <li>
                                                            <a href="/RDW1/app/usuarios/inoutPunto.jsp">Importar / Exportar Puntos de Control</a>
                                                        </li>
                                                    </c:if>  
                                    </ul>
                                </li>
                            </c:if>    
                                
                            <c:if test="${permissions.check(['Configuracion','Servidor'])}">
                                <c:choose>
                                    <c:when test="${permissions.check(58)}">
                                        <li>
                                            <a href="/RDW1/app/administracion/configuraServidor.jsp">Servidor</a>
                                        </li>
                                    </c:when>                    
                                        <c:when test="${permissions.check(119)}">
                                            <li>
                                                <a href="/RDW1/app/administracion/configuraServidorPerimetro.jsp">Servidor</a>
                                            </li>
                                        </c:when>                                        
                                    </c:choose>
                            </c:if>

                            <c:if test="${permissions.check(['Configuracion','Entorno'])}">
                                <c:choose>
                                    <c:when test="${permissions.check(63)}">
                                        <li>
                                            <a href="/RDW1/app/administracion/configuraEntorno.jsp">Entorno</a>
                                        </li>
                                    </c:when>                    
                                    <c:when test="${permissions.check(133)}">
                                        <li>
                                            <a href="/RDW1/app/administracion/configuraEntornoWeb.jsp">Entorno</a>
                                        </li>
                                    </c:when>                                        
                                </c:choose>
                            </c:if>

                            <c:if test="${permissions.check(['Configuracion','Conductor'])}">
                                <li class="dropdown dropdown-submenu">
                                    <a class="dropdown-toggle" href="#">Conductor</a>
                                    <ul class="dropdown-menu">       
                                        <c:if test="${permissions.check(65)}">
                                            <li>
                                                <a href="/RDW1/app/conductor/listadoConductor.jsp">Listado</a>
                                            </li>
                                        </c:if>                    
                                        <c:if test="${permissions.check(66)}">
                                            <li>
                                                <a href="/RDW1/app/conductor/nuevoConductor.jsp">Registro</a>
                                            </li>
                                        </c:if>                                        
                                        <!--RelacionVehiculoConductor-->
                                        <c:if test="${permissions.check(67)}">                   
                                            <c:choose>
                                                <c:when test="${permissions.check(69)}">
                                                    <li>
                                                        <a href="/RDW1/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp">Conductor - Veh&iacute;culo</a>
                                                    </li>
                                                </c:when>                    
                                                <c:when test="${permissions.check(68)}">
                                                    <li>
                                                        <a href="/RDW1/app/relacion_vehiculo_conductor/nuevoRelacionVehiculoConductor.jsp">Conductor - Veh&iacute;culo</a>
                                                    </li>
                                                </c:when>                                        
                                            </c:choose>
                                        </c:if>
                                        <li>
                                            <a href="/RDW1/app/conductor/configuracionDesempeno.jsp">Configuracion desempeño</a>
                                        </li>
                                    </ul>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Configuracion', 'LicenciasConduccion'])}">
                                <li class="dropdown dropdown-submenu">
                                    <a href="#" class="dropdown-toggle">Licencias Cond.</a>
                                    <ul class="dropdown-menu">
                                        <c:if test="${permissions.check(172)}">
                                            <li>
                                                <a href="/RDW1/app/licencia_conduccion/nuevaCategoriaLicencia.jsp">Nueva Categoría</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(173)}">
                                            <li>
                                                <a href="/RDW1/app/licencia_conduccion/listadoCategoriaLicencia.jsp">Categorías</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(176)}">
                                            <li>
                                                <a href="/RDW1/app/licencia_conduccion/nuevaOficinaTransito.jsp">Nueva Oficina Tránsito</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(177)}">
                                            <li>
                                                <a href="/RDW1/app/licencia_conduccion/listadoOficinaTransito.jsp">Oficinas Tránsito</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(180)}">
                                            <li>
                                                <a href="/RDW1/app/licencia_conduccion/nuevaLicenciaTransito.jsp">Nueva Licencia</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(181)}">
                                            <li>
                                                <a href="/RDW1/app/licencia_conduccion/listadoLicenciaTransito.jsp">Licencias</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Configuracion', 'Polizas'])}">
                                <li class="dropdown dropdown-submenu">
                                    <a href="#" class="dropdown-toggle">Pólizas</a>
                                    <ul class="dropdown-menu">
                                        <c:if test="${permissions.check(185)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/nuevaAseguradora.jsp">Nueva Aseguradora</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(186)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/listadoAseguradora.jsp">Aseguradoras</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(207)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/nuevoTipoPoliza.jsp">Nuevo Tipo Póliza</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(208)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/listadoTipoPoliza.jsp">Tipos De Pólizas</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(189)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/nuevaPoliza.jsp">Nueva Póliza</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(190)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/listadoPoliza.jsp">Pólizas</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(193)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/nuevoVehiculoAsegurado.jsp">Asegurar Vehículo</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(194)}">
                                            <li>
                                                <a href="/RDW1/app/polizas/listadoVehiculoAsegurado.jsp">Vehículos Asegurados</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </li>
                            </c:if>

                            <c:if test="${permissions.check(['Configuracion', 'Mantenimiento'])}">
                                <li class="dropdown dropdown-submenu">
                                    <a href="#" class="dropdown-toggle">Mantenimiento</a>
                                    <ul class="dropdown-menu">
                                        <c:if test="${permissions.check(202)}">
                                            <li>
                                                <a href="/RDW1/app/mantenimiento/nuevoMantenimiento.jsp">Configurar Mantenimiento</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(203)}">
                                            <li>
                                                <a href="/RDW1/app/mantenimiento/listadoMantenimiento.jsp">Mantenimientos Configurados</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(211)}">
                                            <li>
                                                <a href="/RDW1/app/mantenimiento/nuevoVehiculoMantenimiento.jsp">Asociar Vehículo Mantenimiento</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${permissions.check(213)}">
                                            <li>
                                                <a href="/RDW1/app/mantenimiento/listadoVehiculoMantenimiento.jsp">Vehiculos En Mantenimiento</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                    
                <c:if test="${permissions.check(['Notificaciones'])}">
                    <li>
                        <a id="linkNotificaciones" href="/RDW1/app/notificaciones/notificaciones.jsp">
                            <i id="notificaciones" class="glyphicon glyphicon-bell" style="color: white"></i>&nbsp;&nbsp;Notificaciones
                        </a>
                    </li>
                </c:if>

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="glyphicon glyphicon-wrench"></i>&nbsp;&nbsp;Acerca de</a>
                    <ul class="dropdown-menu">       
                        <li><a href="#" data-toggle="modal" data-load-url="#" data-target="#modalAcerca">Licencia</a></li>
                        <li><a href="#"  data-toggle="modal" data-target="#contact_us" data-whatever="@mdo">Contactenos</a></li>
                        <li><a href="<c:choose><c:when test="${fn:length(sistema_de_ayuda) > 0}">${sistema_de_ayuda}</c:when><c:otherwise>#</c:otherwise></c:choose>"  
                               target="_blank">Ayuda</a></li>
                        <!--<li><a href="#">Manual de usuario</a></li>-->
                    </ul>
                </li>

            </ul>
            <ul class="nav navbar-nav navbar-right">                        
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
                       aria-haspopup="true" aria-expanded="false">Conectado como: ${login.login} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/RDW1/app/usuarios/perfilUsuario.jsp">Perfil: ${login.perfilusuario}</a>
                        </li>                                
                        <li role="separator" class="divider"></li>
                        <li>
                            <a href="#" onclick="closeApp();">Salir</a>
                            <form id="form-close" class="form-in-table" action="<c:url value='/cerrarSesion'/>" method="post"></form>
                        </li>
                    </ul>
                </li>
            </ul>   
        </div>
    </nav>
</div>
                        