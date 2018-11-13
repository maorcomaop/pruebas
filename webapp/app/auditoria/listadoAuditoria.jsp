
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO AUDITOR&Iacute;A</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Auditoria'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="/RDW1/app/auditoria/buscarAuditoria.jsp">Buscar</a></li>
                    <li role="presentation" class="active"><a href="/RDW1/app/auditoria/listadoAuditoria.jsp">Listado de Auditor&iacute;as</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">



                        <c:choose>
                            <c:when test ="${tablaAImprimir == 1}">
                                <table id="tableAuditoriaAlarmas" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${datos}" var="alarma">    

                                                        <td>${alarma.nuevoNombre}</td>
                                                        <td>${alarma.nuevoTipo}</td>
                                                        <td>${alarma.nuevoUnidadMedicion}</td>
                                                        <td>${alarma.nuevoPrioridad}</td>                                            

                                                        <c:choose>
                                                            <c:when test ="${alarma.estado == 1}">                                                    
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><span class="label label-danger">Inactivo</span></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td>${alarma.fechaEvento}</td>                                            
                                                        <c:choose>
                                                            <c:when test ="${alarma.usuario == null}">
                                                                <td>0</td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <th>${alarma.usuario}</th>
                                                                </c:otherwise>                                                
                                                            </c:choose>
                                                        <td>${alarma.usuarioBD}</td>                                            
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${alarma.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_alarma">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>

                                                    </tr>
                                                </c:forEach> 
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table> 
                            </c:when>

                            <c:when test ="${tablaAImprimir == 2}">
                                <table id="tableAuditoriaCategorias" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>      
                                                    <c:forEach items="${datos}" var="categoria">                                                                                                                                                                                                        
                                                        <th>${categoria.nuevoNombre}</th>                                           
                                                        <td>${categoria.nuevoDescripcion}</td>
                                                        <td>${categoria.nuevoAplicaDescuento}</td>
                                                        <td>${categoria.nuevoEsValorMoneda}</td>
                                                        <td>${categoria.nuevoEsPocentaje}</td>
                                                        <td>${categoria.nuevoEsFija}</td>
                                                        <td>${categoria.nuevoValor}</td>
                                                        <td>${categoria.nuevoAplicaGeneral}</td>                                            
                                                        <c:choose>
                                                            <c:when test ="${categoria.estado == 1}">                                                    
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><span class="label label-danger">Inactivo</span></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td>${categoria.fechaEvento}</td>                                                                                        
                                                        <c:choose>
                                                            <c:when test ="${categoria.usuario == null}">
                                                                <td>0</td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <th>${categoria.usuario}</th>
                                                                </c:otherwise>                                                
                                                            </c:choose>
                                                        <td>${categoria.usuarioBD}</td>                                            
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${categoria.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_categoria_descuentos">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>

                                                    </tr>
                                                </c:forEach> 
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table> 
                            </c:when>

                            <c:when test ="${tablaAImprimir == 3}">
                                <table id="tableAuditoriaConductores" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>                                        
                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${datos}" var="conductor">    
                                                        <td>${conductor.nuevoNombre}</td>
                                                        <td>${conductor.nuevoApellido}</td>
                                                        <td>${conductor.nuevoTipoDocumento}</td>
                                                        <td>${conductor.nuevoCedula}</td>
                                                        <c:choose>
                                                            <c:when test ="${conductor.estado == 1}">                                                    
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><span class="label label-danger">Inactivo</span></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td>${conductor.fechaEvento}</td>
                                                        <td>${conductor.usuario}</td>                                                        
                                                        <td>${conductor.usuarioBD}</td>                                                                                        
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${conductor.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_conductor">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>

                                                    </tr>
                                                </c:forEach>  
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>

                            </c:when>                                        

                            <c:when test ="${tablaAImprimir == 4}">
                                <table id="tableAuditoriaEmpresas" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${datos}" var="empresa">                                                                                            
                                                        <td>${empresa.nuevoFkCiudad}</td>                                                                                        
                                                        <td>${empresa.nuevoNombre}</td>                                            
                                                        <td>${empresa.nuevoNit}</td>                                            
                                                        <c:choose>
                                                            <c:when test ="${empresa.estado == 1}">                                                    
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><span class="label label-danger">Inactivo</span></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td>${empresa.fechaEvento}</td>                                                        
                                                        <td>${empresa.usuario}</td>                                                        
                                                        <td>${empresa.usuarioBD}</td>                                            
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${empresa.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_empresa">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>

                            <c:when test ="${tablaAImprimir == 5}"> 
                                <table id="tableAuditoriaInformaciones" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${datos}" var="informacionRegistradora">                                                                                                                    
                                                        <td>${informacionRegistradora.vehiculo}</td>
                                                        <td>${informacionRegistradora.nuevoNumeroVuelta}</td>
                                                        <td>${informacionRegistradora.nuevoNumeroVueltaAnterior}</td>
                                                        <td>${informacionRegistradora.nuevoNumeroLlegada}</td>
                                                        <td>${informacionRegistradora.nuevoDiferenciaNumerica}</td>
                                                        <td>${informacionRegistradora.nuevoEntradas}</td>
                                                        <td>${informacionRegistradora.nuevoDiferenciaEntrada}</td>
                                                        <td>${informacionRegistradora.nuevoSalidas}</td>
                                                        <td>${informacionRegistradora.nuevoDiferenciaSalida}</td>
                                                        <td>${informacionRegistradora.nuevoFkRuta}</td>                                                        
                                                        <td>${informacionRegistradora.nuevoNumeracionBaseSalida}</td>
                                                        <td>${informacionRegistradora.nuevoEntradasBaseSalida}</td>
                                                        <td>${informacionRegistradora.nuevoSalidasBaseSalida}</td>
                                                        <td>${informacionRegistradora.nuevoFechaIngreso}</td>
                                                        <td>${informacionRegistradora.nuevoHoraIngreso}</td>
                                                        <td>${informacionRegistradora.nuevoFechaSalida}</td>
                                                        <td>${informacionRegistradora.nuevoHoraSalida}</td>
                                                        <td>${informacionRegistradora.fechaEvento}</td>                                                                                                                                                
                                                        <td>${informacionRegistradora.usuario}</td>                                                                
                                                        <td>${informacionRegistradora.usuarioBD}</td>
                                                        <td>${informacionRegistradora.nuevoTotalDia}</td>
                                                        <td>${informacionRegistradora.estado}</td>

                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${informacionRegistradora.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_informacion_registradora">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>

                            <c:when test ="${tablaAImprimir == 6}">
                                <table id="tableAuditoriaLiquidaciones" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${datos}" var="lq">                                                                                                                                        
                                                        <td>${lq.vehiculo}</td>
                                                        <td>${lq.total_pasajeros_liquidados_nuevo}</td>
                                                        <td>${lq.total_valor_vueltas_nuevo}</td>                                            
                                                        <td>${lq.total_valor_descuento_pasajeros_nuevo}</td>                                            
                                                        <td>${lq.total_valor_descuento_adicional_nuevo}</td>                                                                                        
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${lq.estado_nuevo == 1}">                                                    
                                                                    <span class="label label-success">Activo</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactivo</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>                                                                                                                                     
                                                        <td>${lq.fechaEvento}</td>                                                                                                    
                                                        <td>${lq.usuario}</td>                                                        
                                                        <td>${lq.usuarioBD}</td>                                            
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${lq.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_liquidacion_general">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>

                            <c:when test ="${tablaAImprimir == 7}">
                                <table id="tableAuditoriaPerfiles" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr> 
                                                    <c:forEach items="${datos}" var="perfil">                                                                                                                                                                                    
                                                        <td>${perfil.nuevoNombrePerfil}</td>
                                                        <td>${perfil.nuevoDescripcion}</td>                                            
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${perfil.estado == 1}">                                                    
                                                                    <span class="label label-success">Activo</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactivo</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${perfil.fechaEvento}</td>                                                                                        
                                                        <td>${perfil.usuario}</td>
                                                        <td>${perfil.usuarioBD}</td>
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${perfil.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_perfil">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>

                            <c:when test ="${tablaAImprimir == 8}">
                                <table id="tableAuditoriaPuntos" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${datos}" var="punto">
                                                        <td>${punto.nuevoNombrePunto}</td>                                                                                                                                
                                                        <td>${punto.nuevoDescripcion}</td>                                                                                        
                                                        <td>${punto.nuevoLatitud}</td>                                                                                        
                                                        <td>${punto.nuevoLongitud}</td>                                                                                        
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${punto.estado == 1}">                                                    
                                                                    <span class="label label-success">Activo</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactivo</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${punto.fechaEvento}</td>                                            
                                                        <td>${punto.usuario}</td>
                                                        <td>${punto.usuarioBD}</td>                                            
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${punto.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_punto">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>

                            <c:when test ="${tablaAImprimir == 9}">
                                <table id="tableAuditoriaPuntosControl" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>

                                        <c:choose>
                                            <c:when test="${datos.size() > 0}">
                                                <tr>    
                                                    <c:forEach items="${datos}" var="puntoDeControl">
                                                        <td>${puntoDeControl.nuevoHoraPuntoDeControl}</td>                                                                                        
                                                        <td>${puntoDeControl.nuevoFechaPuntoDeControl}</td>                                                                                        
                                                        <td>${puntoDeControl.nuevoNumeracion}</td>                                                                                        
                                                        <td>${puntoDeControl.nuevoEntradas}</td>                                              
                                                        <td>${puntoDeControl.nuevoSalidas}</td>  
                                                        <td>${puntoDeControl.fechaEvento}</td>                                        
                                                        <td>${puntoDeControl.usuario}</td>
                                                        <td>${puntoDeControl.usuarioBD}</td>
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${puntoDeControl.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_punto_control">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>  
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>

                            <c:when test ="${tablaAImprimir == 10}">
                                <table id="tableAuditoriaRutas" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>
                                        <tr>
                                            <c:choose>
                                                <c:when test="${datos.size() > 0}">
                                                    <c:forEach items="${datos}" var="ruta">
                                                        <td>${ruta.nuevoNombreRuta}</td>
                                                        <td>${ruta.estado}</td>                                            
                                                        <td>${ruta.fechaEvento}</td>
                                                        <td>${ruta.usuario}</td>
                                                        <td>${ruta.usuarioBD}</td>                                            
                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${ruta.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_ruta">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>  
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:when test ="${tablaAImprimir == 12}">
                                <table id="tableAuditoriaTarifas" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>
                                        <tr>
                                            <c:choose>
                                                <c:when test="${datos.size() > 0}">
                                                    <c:forEach items="${datos}" var="tarifa">
                                                        <td>${tarifa.nuevoNombreTarifa}</td>
                                                        <td>${tarifa.nuevoValorTarifa}</td> 
                                                        <td>${tarifa.nuevoTarifaActiva}</td> 
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${tarifa.estado == 1}">                                                    
                                                                    <span class="label label-success">Activo</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactivo</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${tarifa.fechaEvento}</td>   
                                                        <td>${tarifa.usuario}</td>
                                                        <td>${tarifa.usuarioBD}</td>

                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${tarifa.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_tarifa">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach> 
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:when test ="${tablaAImprimir == 13}">
                                <table id="tableAuditoriaUsuarios" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>
                                        <tr>     
                                            <c:choose>
                                                <c:when test="${datos.size() > 0}">
                                                    <c:forEach items="${datos}" var="usuario">                                                                                                                                                                                                        
                                                        <td>${usuario.nuevoFkPerfil}</td>
                                                        <td>${usuario.nuevoperfil}</td>
                                                        <td>${usuario.nuevoCedula}</td>
                                                        <td>${usuario.nuevoNombre}</td>                     
                                                        <td>${usuario.nuevoApellido}</td> 
                                                        <td>${usuario.nuevoEmail}</td>  
                                                        <td>${usuario.nuevoLogin}</td>                                                                 
                                                        <td>${usuario.conexion}</td> 
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${usuario.estado == 1}">                                                    
                                                                    <span class="label label-success">Activo</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactivo</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${usuario.fechaEvento}</td>                                            
                                                        <td>${usuario.usuario}</td>
                                                        <td>${usuario.usuarioBD}</td>

                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${usuario.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_usuario">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>  
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:when test ="${tablaAImprimir == 14}">
                                <table id="tableAuditoriaVehiculos" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <c:choose>
                                            <c:when test="${columnas.size() > 0}">
                                                <tr>
                                                    <c:forEach items="${columnas}" var="col">                               
                                                        <th>${col.nombreColumna}</th>
                                                        </c:forEach>
                                                    <th>Ver</th>                                
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </thead>    
                                    <tbody>
                                        <tr>
                                            <c:choose>
                                                <c:when test="${datos.size() > 0}">
                                                    <c:forEach items="${datos}" var="vehiculo">                                                                                                                                                                                                                                              
                                                        <td>${vehiculo.fk}</td>
                                                        <td>${vehiculo.nuevoPlaca}</td>
                                                        <td>${vehiculo.nuevoNumeroInterno}</td> 
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${vehiculo.estado == 1}">                                                    
                                                                    <span class="label label-success">Activo</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactivo</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${vehiculo.fechaEvento}</td>                                            
                                                        <td>${vehiculo.usuario}</td>
                                                        <td>${vehiculo.usuarioBD}</td>

                                                        <td>
                                                            <form action="<c:url value='/verMasAuditoria' />" method="post">
                                                                <input type="hidden" name="id" value="${vehiculo.id}">
                                                                <input type="hidden" name="table" value="tbl_auditoria_vehiculo">
                                                                <input type="submit" class="btn btn-xs btn-info" value="Ver m&aacute;s">
                                                            </form>            
                                                        </td>                                                                
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>
<jsp:include page="/include/footerHome.jsp" />
<script>
    // Tabla dinamica
    $(document).ready(function () {


        var table1 = $('#tableAuditoriaAlarmas').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 4},
                {"visible": false, "targets": 7}]
        });
        var table2 = $('#tableAuditoriaCategorias').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 8},
                {"visible": false, "targets": 11}]
        });
        var table3 = $('#tableAuditoriaConductores').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 4},
                {"visible": false, "targets": 7}]
        });
        var table4 = $('#tableAuditoriaEmpresas').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": true, "targets": 0},
                {"visible": false, "targets": 3},
                {"visible": false, "targets": 6}]
                    /*rowCallback: function(row, data, index){
                     if (data[0] != data[1]) {
                     $(row).addClass('selected');                                                        
                     }
                     else
                     {
                     console.log("iguales")
                     }
                     }*/
        });
        var table5 = $('#tableAuditoriaInformaciones').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": true, "targets": 0},
                {"visible": false, "targets": 1},
                {"visible": true, "targets": 2},
                {"visible": true, "targets": 3},
                {"visible": false, "targets": 4},
                {"visible": false, "targets": 5},
                {"visible": false, "targets": 6},
                {"visible": false, "targets": 7},
                {"visible": false, "targets": 8},
                {"visible": false, "targets": 9},
                {"visible": false, "targets": 10},
                {"visible": false, "targets": 11},
                {"visible": false, "targets": 12},
                {"visible": false, "targets": 13},
                {"visible": false, "targets": 14},
                {"visible": false, "targets": 15},
                {"visible": false, "targets": 16},
                {"visible": true, "targets": 17},
                {"visible": false, "targets": 18},
                {"visible": false, "targets": 19},
                {"visible": false, "targets": 20},
                {"visible": false, "targets": 21},                
                {"visible": true, "targets": 22}                
            ]
        });
        var table6 = $('#tableAuditoriaLiquidaciones').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 5},
                {"visible": false, "targets": 8}]
        });
        var table7 = $('#tableAuditoriaPerfiles').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 2},
                {"visible": false, "targets": 5}]
        });
        var table8 = $('#tableAuditoriaPuntos').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 4},
                {"visible": false, "targets": 7}]
        });
        var table9 = $('#tableAuditoriaPuntosControl').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}});
        var table10 = $('#tableAuditoriaRutas').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 1},
                {"visible": false, "targets": 4}]
        });
        var table11 = $('#tableAuditoriaTarifas').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 3},
                {"visible": false, "targets": 6}]
        });
        var table12 = $('#tableAuditoriaUsuarios').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 0},
                {"visible": false, "targets": 7},
                {"visible": false, "targets": 8},
                {"visible": false, "targets": 11}]
        });
        var table13 = $('#tableAuditoriaVehiculos').DataTable({aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},
            columnDefs: [{"visible": false, "targets": 0},
                {"visible": false, "targets": 3},
                {"visible": false, "targets": 5},
                {"visible": false, "targets": 6}]
        });


    });
</script>
<jsp:include page="/include/end.jsp" />
