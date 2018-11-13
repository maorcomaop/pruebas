<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="nav navbar-nav" style="padding-left: 20px;">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
           aria-haspopup="true" aria-expanded="false">
            <span class="glyphicon glyphicon-menu-hamburger"></span>
        </a>
        <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">

            <li><a href="/RDW1/app/usuarios/index.jsp">Home</a></li>

            <c:if test="${permissions.check(['Auditoria'])}">
                <li class="divider"></li>
                <li class="dropdown-submenu">
                    <c:if test="${permissions.check(['Auditoria','Buscar'])}">
                    <a tabindex="-1" href="/RDW1/app/auditoria/buscarAuditoria.jsp">Auditor&iacute;a</a>
                    </c:if>
                </li>
            </c:if>

            <c:if test="${permissions.check(['Liquidacion'])}">    
                <li class="divider"></li>
                <li class="dropdown-submenu"><a tabindex="-1" href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">Liquidaci&oacute;n</a>                        
                    <ul class="dropdown-menu">
                        <c:if test="${permissions.check(['Liquidacion','Consultar'])}">   
                            <li><a tabindex="-1" href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">Liquidar Veh&iacute;culo</a></li>                                        
                            </c:if>            
                            <c:if test="${permissions.check(['Liquidacion','Reportes'])}">   
                            <li><a tabindex="-1" href="/RDW1/app/liquidacion/consolidadoLiquidacion.jsp">Reportes</a></li>                                        
                            </c:if>
                    </ul>
                </li>
            </c:if>

            <c:if test="${permissions.check(['Perimetro'])}">
                <li class="divider"></li>
                <c:if test="${permissions.check(['Perimetro','VerVehiculos'])}">                                            
                <li class="dropdown-submenu"><a tabindex="-1" href="/RDW1/app/administracion/visualizaPerimetro.jsp">Per&iacute;metro</a>
                </li>
                </c:if>
            </c:if>

            <c:if test="${permissions.check(['Usuarios'])}">
                <li class="divider"></li>
                <li class="dropdown-submenu"><a tabindex="-1" href="/RDW1/app/usuarios/nuevoUsuario.jsp">Usuarios</a>                        
                    <ul class="dropdown-menu">

                        <c:if test="${permissions.check(['Usuarios','Registro'])}">
                            <li><a tabindex="-1" href="/RDW1/app/usuarios/nuevoUsuario.jsp">Registro</a></li>                                                        
                            </c:if>
                            <c:if test="${permissions.check(['Usuarios','Permisos'])}">
                            <li class="dropdown-submenu"><a href="#">Permisos</a>                                
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Usuarios','Permisos','AsignarPerfiles'])}">
                                        <li><a href="/RDW1/app/perfil/listadoPerfil.jsp">Perfiles</a></li>
                                        </c:if>        
                                        <c:if test="${permissions.check(['Usuarios','Permisos','AsignarPerfiles'])}">
                                        <li><a href="<c:url value='/nuevoPerfil' />">Asignar Permisos</a></li>
                                        </c:if>        
                                </ul>
                            </li>
                        </c:if>       
                    </ul>
                </li>
            </c:if>

            <c:if test="${permissions.check(['Registradora'])}">
                <li class="divider"></li>
                <c:if test="${permissions.check(['Registradora','InformacionRegistradora'])}">
                <li class="dropdown-submenu"><a tabindex="-1" href="/RDW1/app/registradora/consultaInfoRegistradora.jsp">Registradora</a>                                            
                </li>
                </c:if>
            </c:if>

            <c:if test="${permissions.check(['Reportes'])}">
                <li class="divider"></li>
                <li><a href="/RDW1/app/reportes/generaReporte.jsp">Reportes</a>
                </li>
            </c:if>

            <c:if test="${permissions.check(['SeguimientoGPS'])}">
                <li class="divider"></li>
                <li><a href="/RDW1/app/seguimiento/gps.jsp">Seguimiento GPS</a>                                    
                </li>
            </c:if>           
           
            <c:if test="${permissions.check(['Despacho'])}">
                <li class="divider"></li>
                <li class="dropdown-submenu"><a tabindex="-1" href="#">Despacho</a>
                    <ul class="dropdown-menu">
                        <c:if test="${permissions.check(['Despacho','Configuracion'])}">
                            <li><a tabindex="-1" href="/RDW1/app/despacho/nuevoDespacho.jsp">Configuraci&oacute;n</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','Listado'])}">
                            <li><a tabindex="-1" href="/RDW1/app/despacho/consultaDespacho.jsp">Listado</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
                            <li><a tabindex="-1" href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','Estado'])}">
                            <li><a tabindex="-1" href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','Control'])}">
                            <li><a tabindex="-1" href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','ProgramacionDeRuta'])}">
                            <li><a tabindex="-1" href="/RDW1/app/despacho/programacionRuta.jsp">Programaci&oacute;n de Ruta</a></li>
                            </c:if>
                    </ul>
                </li>
            </c:if>
            <c:if test="${permissions.check(['Configuracion'])}">
                <li class="divider"></li>
                <li class="dropdown-submenu"><a tabindex="-1" href="#">Configuraci&oacute;n</a>                        
                    <ul class="dropdown-menu">
                        <c:if test="${permissions.check(['Configuracion','Alarmas'])}">
                            <c:if test="${permissions.check(['Configuracion','Alarmas','Registrar'])}">                                        
                            <li class="dropdown-submenu"><a href="/RDW1/app/alarma/listadoAlarma.jsp">Alarmas</a>                                
                            </li>
                            </c:if>
                        </c:if>

                        <c:if test="${permissions.check(['Configuracion','Empresa'])}">
                            <li class="dropdown-submenu"><a href="/RDW1/app/usuarios/nuevaEmpresa.jsp">Empresa</a>                                
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Configuracion','Empresa','Registrar'])}">
                                        <li><a href="/RDW1/app/usuarios/nuevaEmpresa.jsp">Registrar</a></li>                                                                                            
                                        <c:if test="${permissions.check(['Configuracion','Empresa','RelacionEmpresaVehiculo'])}">
                                            <li class="dropdown-submenu"><a href="/RDW1/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp">
                                                    Relación Empresa - Veh&iacute;culo</a>                                
                                                <ul class="dropdown-menu">                                                    
                                                        <c:if test="${permissions.check(['Configuracion','Empresa','RelacionEmpresaVehiculo','Registrar'])}">
                                                        <li><a href="/RDW1/app/relacion_empresa_movil/nuevoRelacionEmpresaVehiculo.jsp">Registrar</a></li>
                                                        </c:if>
                                                </ul>
                                            </li>
                                        </c:if>
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>


                        <c:if test="${permissions.check(['Configuracion','Vehiculos'])}">
                            <li class="dropdown-submenu"><a href="/RDW1/app/movil/listadoMovil.jsp">Veh&iacute;culos</a>                                
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Configuracion','Vehiculos','Listado'])}">
                                        <li><a href="/RDW1/app/movil/listadoMovil.jsp">Listado</a></li>
                                        </c:if>
                                        <c:if test="${permissions.check(['Configuracion','Vehiculos','Registrar'])}">
                                        <!--<li><a href="/RDW1/app/movil/nuevoMovil.jsp">Registrar</a></li>-->
                                    </c:if>
                                    <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles'])}">
                                        <li class="dropdown-submenu"><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Grupo de Veh&iacute;culos</a>                                
                                            <ul class="dropdown-menu">
                                                <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','Listado'])}">
                                                    <!--<li><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Listado</a></li>-->
                                                </c:if>
                                                <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','Registrar'])}">
                                                    <li><a href="/RDW1/app/grupo_movil/nuevoGrupoMovil.jsp">Registrar</a></li>
                                                    </c:if>
                                            </ul>
                                        </li>                                                
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>
                       

                        <c:if test="${permissions.check(['Configuracion','Liquidacion'])}">    
                            <li class="dropdown-submenu"><a href="/RDW1/app/categorias_de_descuento/listadoCategorias.jsp">Liquidaci&oacute;n</a>
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Configuracion','Liquidacion','Tarifas'])}">
                                        <li class="dropdown-submenu"><a href="/RDW1/app/usuarios/nuevaTarifa.jsp">Tarifas</a>                                
                                            <ul class="dropdown-menu">
                                                <c:if test="${permissions.check(['Configuracion','Liquidacion','Tarifas','Registrar'])}">
                                                    <li><a href="/RDW1/app/usuarios/nuevaTarifa.jsp">Registrar</a></li>
                                                    </c:if>
                                            </ul>
                                        </li>
                                    </c:if>
                                    <c:if test="${permissions.check(['Configuracion','Liquidacion','CategoriasDeDescuento'])}">
                                        <li><a href="/RDW1/app/categorias_de_descuento/listadoCategorias.jsp">Categor&iacute;as de Descuento</a></li>
                                        </c:if>
                                </ul>
                            </li>
                        </c:if>

                        <c:if test="${permissions.check(['Configuracion','Rutas'])}">
                            <li class="dropdown-submenu"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Rutas</a>
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Configuracion','Rutas','Registrar'])}">
                                        <li><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Registrar</a></li>
                                        </c:if>
                                        <c:if test="${permissions.check(['Configuracion','Rutas','PuntosDeControl'])}">
                                        <li class="dropdown-submenu"><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos de Control</a>                                
                                            <ul class="dropdown-menu">
                                                <c:if test="${permissions.check(['Configuracion','Rutas','PuntosDeControl','Creacion'])}">
                                                    <li><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Creaci&oacute;n</a></li>
                                                    </c:if>    
                                                    <c:if test="${permissions.check(['Configuracion','Rutas','PuntosDeControl','ImportarExportar'])}">
                                                    <li><a href="/RDW1/app/usuarios/inoutPunto.jsp">Importar / Exportar</a></li>
                                                    </c:if>
                                            </ul>
                                        </li>
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>

                        <c:if test="${permissions.check(['Configuracion','Eventos'])}">    
                            <li class="dropdown-submenu"><a href="/RDW1/app/evento/listadoEventos.jsp">Eventos</a>                                
                            </li>
                        </c:if>

                        <c:if test="${permissions.check(['Configuracion','Servidor'])}">
                            <li class="dropdown-submenu"><a href="/RDW1/app/administracion/configuraServidor.jsp">Servidor</a>                                
                            </li>
                        </c:if>

                        <c:if test="${permissions.check(['Configuracion','Perimetro'])}">
                            <li class="dropdown-submenu"><a href="/RDW1/app/administracion/configuraPerimetro.jsp">Per&iacute;metro</a>                                
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Configuracion','Perimetro','Configurar'])}">    
                                        <li><a href="/RDW1/app/administracion/configuraPerimetro.jsp">Configurar</a></li>
                                        </c:if>
                                        <c:if test="${permissions.check(['Configuracion','Perimetro','ConfigurarServidor'])}">    
                                        <li><a href="/RDW1/app/administracion/configuraServidorPerimetro.jsp">Configurar Servidor</a></li>                                                                                                
                                        </c:if>
                                        <c:if test="${permissions.check(['Configuracion','Perimetro','Visualizar'])}">
                                        <li><a href="/RDW1/app/administracion/visualizaPerimetro.jsp">Visualizar</a></li>
                                        </c:if>
                                </ul>
                            </li>
                        </c:if>


                        <c:if test="${permissions.check(['Configuracion','Entorno'])}">   
                            <li class="dropdown-submenu"><a href="/RDW1/app/administracion/configuraEntorno.jsp">Entorno</a>
                            </li> 
                        </c:if>

                        <c:if test="${permissions.check(['Configuracion','Conductor'])}">
                            <li class="dropdown-submenu"><a href="/RDW1/app/conductor/listadoConductor.jsp">Conductor</a>                                
                                <ul class="dropdown-menu">
                                    <c:if test="${permissions.check(['Configuracion','Conductor','Listado'])}">
                                        <li><a href="/RDW1/app/conductor/listadoConductor.jsp">Listado</a></li>
                                        </c:if>
                                        <c:if test="${permissions.check(['Configuracion','Conductor','Listado'])}">
                                        <!--<li><a href="/RDW1/app/conductor/nuevoConductor.jsp">Registro</a></li>         -->
                                    </c:if>
                                    <c:if test="${permissions.check(['Configuracion','Conductor','RelacionVehiculoConductor'])}">
                                        <li class="dropdown-submenu"><a href="/RDW1/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp">
                                                Relacion Veh&iacute;culo - conductor</a>                                
                                            <ul class="dropdown-menu">
                                                <c:if test="${permissions.check(['Configuracion','Conductor','RelacionVehiculoConductor','Registro'])}">
                                                    <li><a href="/RDW1/app/relacion_vehiculo_conductor/nuevoRelacionVehiculoConductor.jsp">Registro</a></li>
                                                    </c:if>
                                                    <c:if test="${permissions.check(['Configuracion','Conductor','RelacionVehiculoConductor','Listado'])}">
                                                    <!--<li><a href="/RDW1/app/relacion_vehiculo_conductor/unaRelacionVehiculoConductor.jsp">Listado</a></li>-->
                                                </c:if>
                                            </ul>
                                        </li>  
                                    </c:if>
                                </ul>
                            </li>                        
                        </c:if>

                    </c:if>
                </ul>
            </li>
        </ul>                            
    </li>
</ul>
