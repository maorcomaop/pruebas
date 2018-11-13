
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">      
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO AUDITOR&Iacute;A</h1>
     <section class="boxed padding">
         <ul class="nav nav-tabs">
        <li role="presentation"><a href="/RDW1/app/auditoria/buscarAuditoria.jsp">Buscar</a></li>        
        <li role="presentation" class="active"><a href="/RDW1/app/auditoria/listadoAuditoria.jsp">Listado de Auditor&iacute;as</a></li>                    
    </ul>
     <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                     <c:choose>
                             
                        <c:when test ="${tablaAImprimir == 1}">
                             <table id="tableAuditoriaAlarmaNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                             <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                             
                                         </tr>
                                     </thead>
                                     <tbody>
                                         <tr>                             
                                             <td>Nombre:</td>                                 
                                             <th>${auditoria.nuevoNombre}</th>
                                             <th>${auditoria.antiguoNombre}</th>
                                         </tr>
                                         <tr>                             
                                             <td>Apellido:</td>
                                             <th>${auditoria.nuevoTipo}</th> 
                                             <th>${auditoria.antiguoTipo}</th>                                 
                                         </tr>

                                         <tr>
                                             <td>Unidad de medida:</td>
                                             <th>${auditoria.nuevoUnidadMedicion}</th>    
                                             <th>${auditoria.antiguoUnidadMedicion}</th>                                             
                                         </tr>
                                         <tr>
                                             <td>C&eacute;dula:</td>
                                             <th>${auditoria.nuevoPrioridad}</th>  
                                             <th>${auditoria.antiguoPrioridad}</th>                                             
                                         </tr>
                                         <tr>
                                             <td>Usuario:</td>                                 
                                             <th>${auditoria.usuario}</th>
                                            <td>${auditoria.usuario}</td>                                                                                          
                                         </tr>                                         
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                 </table>  
                        </c:when>
                        
                        <c:when test ="${tablaAImprimir == 2}">
                             <table id="tableAuditoriaCategoriaNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                            
                                         </tr>
                                     </thead>
                                     <tbody>
                                         <tr>                             
                                             <td>Nombre:</td>                                 
                                             <th>${auditoria.nuevoNombre}</th>
                                             <th>${auditoria.antiguoNombre}</th>
                                         </tr>
                                         <tr>                             
                                             <td>Descripci&oacute;n:</td>
                                             <th>${auditoria.nuevoDescripcion}</th> 
                                             <th>${auditoria.antiguoDescripcion}</th>                                 
                                         </tr>

                                         <tr>
                                             <td>Descuento Operativo:</td>
                                             <c:choose>
                                                <c:when test ="${auditoria.nuevoAplicaDescuento == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                             
                                                    <c:choose>
                                                <c:when test ="${auditoria.antiguoAplicaDescuento == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>  
                                         </tr>
                                         <tr>
                                             <td>Descuento administrativo:</td>
                                             <c:choose>
                                                <c:when test ="${auditoria.nuevoAplicaGeneral == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                             
                                                    <c:choose>
                                                <c:when test ="${auditoria.antiguoAplicaGeneral == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                                                                         
                                         </tr>
                                         <tr>
                                             <td>Dinero:</td>
                                             <c:choose>
                                                <c:when test ="${auditoria.nuevoEsValorMoneda == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                             
                                             <c:choose>
                                                <c:when test ="${auditoria.antiguoEsValorMoneda == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                                
                                         </tr>   
                                         <tr>
                                             <td>Porcentaje:</td>
                                              <c:choose>
                                                <c:when test ="${auditoria.nuevoEsPocentaje == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                             
                                             <c:choose>
                                                <c:when test ="${auditoria.antiguoEsPocentaje == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                                
                                         </tr>
                                         <tr>
                                             <td>Fija:</td>
                                             <c:choose>
                                                <c:when test ="${auditoria.nuevoEsFija == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>                                             
                                             <c:choose>
                                                <c:when test ="${auditoria.antiguoEsFija == 1}">
                                                    <td><span class="label label-success">Si</span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><span class="label label-danger">No</span></td>
                                                </c:otherwise>
                                             </c:choose>       
                                                                                        
                                         </tr>
                                         <tr>
                                             <td>Valor:</td>                                              
                                             <td>${auditoria.antiguoValor}</td>
                                             <td>${auditoria.nuevoValor}</td>
                                         </tr>                                         
                                         <tr>
                                             <td>Usuario:</td>                                 
                                             <th>${auditoria.usuario}</th>   
                                             <th>${auditoria.usuario}</th>                                             
                                         </tr>
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                 </table>                                          
                        </c:when>
                                         
                        <c:when test ="${tablaAImprimir == 3}">                             
                             <table id="tableAuditoriaConductorNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                             
                                         </tr>
                                     </thead>
                                     <tbody>
                                         <tr>                             
                                             <td>Nombre:</td>                                 
                                             <th>${auditoria.nuevoNombre}</th>
                                             <th>${auditoria.antiguoNombre}</th>
                                         </tr>
                                         <tr>                             
                                             <td>Apellido:</td>
                                             <th>${auditoria.nuevoApellido}</th>
                                             <th>${auditoria.antiguoApellido}</th>
                                         </tr>
                                         <tr>
                                             <td>Cedula:</td>
                                             <th>${auditoria.nuevoCedula}</th>                                                
                                             <th>${auditoria.antiguoCedula}</th>
                                         </tr>
                                         <tr>
                                             <td>Usuario:</td>                                 
                                             <th>${auditoria.usuario}</th>
                                             <th>${auditoria.usuario}</th>
                                         </tr>                                         
                                         <tr>
                                             <td>Tipo Documento</td>                                 
                                             <th>${auditoria.nuevoTipoDocumento}</th>
                                             <th>${auditoria.antiguoTipoDocumento}</th>
                                         </tr>                                         
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                 </table>
                        </c:when>
                         
                        <c:when test ="${tablaAImprimir == 4}">
                             <table id="tableAuditoriaEmpresaNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                           <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>   
                                         </tr>
                                     </thead>
                                     <tbody>
                                         <tr>                             
                                             <td>Nombre:</td>                                 
                                             <th>${auditoria.nuevoNombre}</th>
                                             <td>${auditoria.antiguoNombre}</td>
                                         </tr>                                         
                                         <tr>
                                             <td>Nit:</td>
                                             <th>${auditoria.nuevoNit}</th>   
                                             <td>${auditoria.antiguoNit}</td>  
                                         </tr>
                                         <tr>
                                             <td>Usuario:</td>                                 
                                             <th>${auditoria.usuario}</th> 
                                             <td>${auditoria.usuario}</td>  
                                         </tr>                                        
                                         <tr>
                                             <td>Ciudad</td>                                 
                                             <th>${auditoria.nuevoFkCiudad}</th> 
                                             <td>${auditoria.antiguoFkCiudad}</td>  
                                         </tr> 
                                     </tbody>
                                      <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                 </table>  
                        </c:when>
                         
                        <c:when test ="${tablaAImprimir == 5}">
                             <table id="tableAuditoriaInformacionRegistradoraNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                            
                                         </tr>
                                     </thead>
                                     <tbody>
                                         <tr>                             
                                             <td>Numero de vuelta:</td>                                 
                                             <th>${auditoria.nuevoNumeroVuelta}</th>
                                             <td>${auditoria.antiguoNumeroVuelta}</td>
                                         </tr>
                                         <tr>                             
                                             <td>Numero de Vuelta Anterior</td>
                                             <th>${auditoria.nuevoNumeroVueltaAnterior}</th>
                                             <td>${auditoria.antiguoNumeroVueltaAnterior}</td>
                                         </tr>
                                         <tr>
                                             <td>Numero de Llegada:</td>
                                             <th>${auditoria.nuevoNumeroLlegada}</th>
                                             <td>${auditoria.antiguoNumeroLlegada}</td>
                                         </tr>
                                         <tr>
                                             <td>Diferencia Numerica:</td>                                 
                                             <th>${auditoria.nuevoDiferenciaNumerica}</th>
                                             <td>${auditoria.antiguoDiferenciaNumerica}</td>
                                         </tr>
                                         <tr>
                                             <td>Entradas:</td>                                 
                                             <th>${auditoria.nuevoEntradas}</th>                                            
                                             <td>${auditoria.antiguoEntradas}</td>
                                         </tr>
                                         <tr>
                                             <td>Diferencia Entradas:</td>                                 
                                             <th>${auditoria.nuevoDiferenciaEntrada}</th>                                            
                                             <td>${auditoria.antiguoDiferenciaEntrada}</td>
                                         </tr>                                            
                                         <tr>
                                             <td>Salidas:</td>                                 
                                             <th>${auditoria.nuevoSalidas}</th>
                                             <td>${auditoria.antiguoSalidas}</td>
                                         </tr>
                                         <tr>
                                             <td>Diferencia Salida:</td>                                 
                                             <th>${auditoria.nuevoDiferenciaSalida}</th>
                                             <td>${auditoria.antiguoDiferenciaSalida}</td>
                                         </tr>
                                         <tr>
                                             <td>Ruta:</td>
                                             <c:choose>
                                                 <c:when test ="${auditoria.nuevoFkRuta == null}">
                                                     <td>0</td>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <th>${auditoria.nuevoFkRuta}</th>
                                                 </c:otherwise>                                                
                                             </c:choose>
                                             <c:choose>
                                                  <c:when test="${auditoria.antiguoFkRuta == null}">
                                                     <td>0</td>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <td>${auditoria.antiguoFkRuta}</td>
                                                 </c:otherwise>
                                             </c:choose>                                             
                                             
                                         </tr>                                         
                                         <tr>
                                             <td>Numeracion Base Salida</td>
                                             <th>${auditoria.nuevoNumeracionBaseSalida}</th>
                                             <td>${auditoria.antiguoNumeracionBaseSalida}</td>
                                         </tr>
                                         <tr>
                                             <td>Entrada Base Salida</td>
                                             <th>${auditoria.nuevoEntradasBaseSalida}</th>
                                             <td>${auditoria.antiguoEntradasBaseSalida}</td>
                                         </tr>
                                         <tr>
                                             <td>Salidas Base Salida</td>
                                             <th>${auditoria.nuevoSalidasBaseSalida}</th>
                                             <td>${auditoria.antiguoSalidasBaseSalida}</td>
                                         </tr>
                                         <tr>
                                             <td>Fecha ingreso</td>
                                             <th>${auditoria.nuevoFechaIngreso}</th>
                                             <td>${auditoria.antiguoFechaIngreso}</td>
                                         </tr>
                                         <tr>
                                             <td>Hora ingreso</td>
                                             <th>${auditoria.nuevoHoraIngreso}</th>
                                             <td>${auditoria.antiguoHoraIngreso}</td>
                                         </tr>
                                         <tr>
                                             <td>Fecha Salida</td>
                                             <th>${auditoria.nuevoFechaSalida}</th>
                                             <td>${auditoria.antiguoFechaSalida}</td>
                                         </tr>
                                         <tr>
                                             <td>Hora Salida</td>
                                             <th>${auditoria.nuevoHoraSalida}</th>
                                             <td>${auditoria.antiguoHoraSalida}</td>
                                         </tr>                                           
                                         <tr>
                                             <td>Fecha</td>
                                             <th>${auditoria.fechaEvento}</th>
                                             <th>${auditoria.fechaEvento}</th>
                                         </tr>                                         
                                         <tr>
                                             <td>Total d&iacute;a</td>
                                             <th>${auditoria.nuevoTotalDia}</th>                                            
                                             <td>${auditoria.antiguoTotalDia}</td>
                                         </tr>
                                         <tr>
                                             <td>Motivo</td>                                             
                                             <th>${auditoria.motivo}</th>
                                             <td>0</td>                                            
                                         </tr>
                                         <tr>
                                             <td>Estado</td>             
                                             <c:choose>
                                                 <c:when test="${auditoria.estado == 1}">
                                                     <td><span class="label label-success">Activo</span></td>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <td><span class="label label-danger">Activo</span></td>
                                                 </c:otherwise>
                                             </c:choose>                                             
                                             <c:choose>
                                                 <c:when test="${auditoria.estado == 1}">
                                                     <td><span class="label label-success">Activo</span></td>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <td><span class="label label-danger">Activo</span></td>
                                                 </c:otherwise>
                                             </c:choose>
                                         </tr>
                                         <tr>
                                             <td>Usuario</td>
                                             <td>${auditoria.usuario}</td>
                                             <td>${auditoria.usuario}</td>
                                         </tr>                                         
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                  </table>                                      
                        </c:when>                        
                         
                        <c:when test ="${tablaAImprimir == 6}">
                             <table id="tableAuditoriaLiquidacion" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                             
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Pasajeros liquidados</td>
                                     <td>${liquidacion.total_pasajeros_liquidados_nuevo}</td>
                                     <td>${liquidacion.total_pasajeros_liquidados_antiguo}</td>                                     
                                     </tr>
                                     <tr>
                                     <td>Pasajeros descontados</td>
                                     <td>${liquidacion.total_valor_descuento_pasajeros_nuevo}</td>
                                     <td>${liquidacion.total_valor_descuento_pasajeros_antiguo}</td>                                     
                                     </tr>                                   
                                     <tr>
                                     <td>Total Descuentos operativos</td>
                                     <td>${liquidacion.total_valor_descuento_adicional_nuevo}</td>
                                     <td>${liquidacion.total_valor_descuento_adicional_antiguo}</td>                                     
                                     </tr>                                   
                                     <tr>
                                     <td>Valor vueltas</td>
                                     <td>${liquidacion.total_valor_vueltas_nuevo}</td>
                                     <td>${liquidacion.total_valor_vueltas_antiguo}</td>                                     
                                     </tr>
                                     <tr>
                                     <td>Motivo</td>
                                     <td>${liquidacion.motivo}</td>
                                     <td></td>                                     
                                     </tr>
                                                                        
                                     <tr>
                                         <td>Estado</td>
                                         <c:choose>
                                             <c:when test="${liquidacion.estado_nuevo == 1}">
                                                 <td><span class="label label-success">Activo</span></td>
                                             </c:when>
                                             <c:otherwise>
                                                 <td><span class="label label-danger">Inactivo</span></td>
                                             </c:otherwise>
                                         </c:choose>                                             
                                         <c:choose>
                                             <c:when test="${liquidacion.estado_antiguo == 1}">
                                                 <td><span class="label label-success">Activo</span></td>
                                             </c:when>
                                             <c:otherwise>
                                                 <td><span class="label label-danger">Inactivo</span></td>
                                             </c:otherwise>
                                         </c:choose>                                             
                                         
                                     </tr>
                                     <tr>
                                         <td>Usuario</td>
                                         <td>${liquidacion.usuario}</td>
                                         <td>${liquidacion.usuario}</td>
                                     </tr>
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>                                                                     
                        </c:when>
                                    
                        <c:when test ="${tablaAImprimir == 7}">
                             <table id="tableAuditoriaPerfilNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                          <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                             
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Nombre</td>
                                     <td>${auditoria.nuevoNombrePerfil}</td>
                                     <td>${auditoria.antiguoNombrePerfil}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                     <td>Descripcion</td>
                                     <td>${auditoria.nuevoDescripcion}</td>
                                     <td>${auditoria.antiguoDescripcion}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                         <td>Estado</td>
                                         <c:choose>
                                             <c:when test="${auditoria.estado == 1}">
                                                 <td><span class="label label-success">Activo</span></td>
                                             </c:when>
                                             <c:otherwise>
                                                 <td><span class="label label-danger">Inactivo</span></td>
                                             </c:otherwise>
                                         </c:choose>                                             
                                                 <c:choose>
                                             <c:when test="${auditoria.estado == 1}">
                                                 <td><span class="label label-success">Activo</span></td>
                                             </c:when>
                                             <c:otherwise>
                                                 <td><span class="label label-danger">Inactivo</span></td>
                                             </c:otherwise>
                                         </c:choose>                                             
                                         
                                     </tr>
                                     <tr>
                                         <td>Usuario</td>
                                         <td>${auditoria.usuario}</td>
                                         <td>${auditoria.usuario}</td>
                                     </tr>                                     
                                     </tbody>
                                      <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>                                        
                        </c:when>
                        
                        <c:when test ="${tablaAImprimir == 8}">
                            <table id="tableAuditoriaPuntoNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                           <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                              
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Nombre</td>
                                     <td>${auditoria.nuevoNombrePunto}</td>
                                     <td>${auditoria.antiguoNombrePunto}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                     <td>Descripcion</td>
                                     <td>${auditoria.nuevoDescripcion}</td>
                                     <td>${auditoria.antiguoDescripcion}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                     <td>Latitud</td>
                                     <td>${auditoria.nuevoLatitud}</td>
                                     <td>${auditoria.antiguoLatitud}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                         <td>Estado</td>
                                         <c:choose>
                                             <c:when test="${auditoria.estado == 1}">
                                                 <td><span class="label label-success">Activo</span></td>
                                             </c:when>
                                             <c:otherwise>
                                                 <td><span class="label label-danger">Inactivo</span></td>
                                             </c:otherwise>
                                         </c:choose>                                             
                                         <c:choose>
                                             <c:when test="${auditoria.estado == 1}">
                                                 <td><span class="label label-success">Activo</span></td>
                                             </c:when>
                                             <c:otherwise>
                                                 <td><span class="label label-danger">Inactivo</span></td>
                                             </c:otherwise>
                                         </c:choose>                                             
                                         
                                     </tr>
                                     <tr>
                                         <td>Usuario</td>
                                         <td>${auditoria.usuario}</td>
                                         <td>${auditoria.usuario}</td>
                                     </tr>                                     
                                     </tbody>
                                      <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table> 
                        </c:when>
                        
                        <c:when test ="${tablaAImprimir == 9}">
                            <table id="tableAuditoriaPuntoDeControlNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                           
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Hora</td>
                                     <td>${auditoria.nuevoHoraPuntoDeControl}</td>
                                     <td>${auditoria.antiguoHoraPuntoDeControl}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                     <td>Fecha</td>
                                     <td>${auditoria.nuevoFechaPuntoDeControl}</td>
                                     <td>${auditoria.antiguoFechaPuntoDeControl}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                     <td>Latitud</td>
                                     <td>${auditoria.nuevoNumeracion}</td>
                                     <td>${auditoria.antiguoNumeracion}</td>                                     
                                     </tr>
                                     <tr>
                                         <td>Entradas</td>
                                         <td>${auditoria.nuevoEntradas}</td>
                                         <td>${auditoria.antiguoEntradas}</td>
                                     </tr>
                                     <tr>
                                         <td>Salidas</td>
                                         <td>${auditoria.nuevoSalidas}</td>
                                         <td>${auditoria.antiguoSalidas}</td>
                                     </tr>
                                     <tr>
                                         <td>Usuario</td>                                         
                                         <td>${auditoria.usuario}</td>
                                         <td>${auditoria.usuario}</td>
                                     </tr>
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>                                     
                        </c:when>

                         <c:when test ="${tablaAImprimir == 10}">
                            <table id="tableAuditoriaRutaNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                             <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                             
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Nombre</td>
                                     <td>${miruta.nuevoNombreRuta}</td>
                                     <td>${miruta.antiguoNombreRuta}</td>                                     
                                     </tr>
                                     
                                     <tr>
                                          <td>Estado</td>
                                          <c:choose>
                                              <c:when test ="${auditoria.estado == 1}">                                                    
                                                <td><span class="label label-success">Activo</span></td>
                                              </c:when>
                                              <c:otherwise>
                                                  <td><span class="label label-danger">Inactivo</span></td>
                                            </c:otherwise>
                                     </c:choose>
                                                  <c:choose>
                                              <c:when test ="${auditoria.estado == 1}">                                                    
                                                <td><span class="label label-success">Activo</span></td>
                                              </c:when>
                                              <c:otherwise>
                                                  <td><span class="label label-danger">Inactivo</span></td>
                                            </c:otherwise>
                                     </c:choose>
                                    </tr>
                                     <tr>
                                         <td>Usuario</td>                                         
                                         <td>${miruta.usuario}</td>
                                         <td>${miruta.usuario}</td>
                                     </tr>                                     
                                     </tbody>
                                      <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>                                        
                         </c:when>
                                    
                         <c:when test ="${tablaAImprimir == 12}">
                            <table id="tableAuditoriaTarifaNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                            
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Nombre Tarifa</td>
                                     <td>${auditoria.nuevoNombreTarifa}</td>                                     
                                     <td>${auditoria.antiguoNombreTarifa}</td>                                     
                                     </tr>
                                     <tr>
                                     <td>Valor Tarifa</td>
                                     <td>${auditoria.nuevoValorTarifa}</td>                                     
                                     <td>${auditoria.antiguoValorTarifa}</td>                                     
                                     </tr>   
                                     <tr>
                                     <td>Tarifa Activa</td>
                                     <td>${auditoria.nuevoTarifaActiva}</td>                                     
                                     <td>${auditoria.antiguoTarifaActiva}</td>                                     
                                     </tr> 
                                     <tr>
                                         <td>Usuario</td>                                         
                                         <td>${auditoria.usuario}</td>
                                         <td>${auditoria.usuario}</td>
                                     </tr>                                      
                                     </tbody>
                                     <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>
                         </c:when>
                                    
                         <c:when test ="${tablaAImprimir == 13}">                                        
                            <table id="tableAuditoriaUsuarioNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                           <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                              
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Perfil</td>
                                     <td>${auditoria.nuevoperfil}</td>                                     
                                     <td>${auditoria.antiguoperfil}</td>                                     
                                     </tr>                                     
                                     
                                     <tr>
                                     <td>Cedula</td>
                                     <td>${auditoria.nuevoCedula}</td>                                     
                                     <td>${auditoria.antiguoCedula}</td>                                                                          
                                     </tr>                                     
                                     
                                     <tr>
                                     <td>Nombre</td>
                                     <td>${auditoria.nuevoNombre}</td>                                     
                                     <td>${auditoria.antiguoNombre}</td>                                                                          
                                     </tr>                                     
                                     
                                     <tr>
                                     <td>Apellido</td>
                                     <td>${auditoria.nuevoApellido}</td>                                     
                                     <td>${auditoria.antiguoApellido}</td>                                                                          
                                     </tr>                                     
                                     
                                     <tr>
                                     <td>Email</td>
                                     <td>${auditoria.nuevoEmail}</td>                                     
                                     <td>${auditoria.antiguoEmail}</td>                                                                          
                                     </tr>  
                                     
                                     <tr>
                                     <td>Login</td>
                                     <td>${auditoria.nuevoLogin}</td>                                     
                                     <td>${auditoria.antiguoLogin}</td>                                                                          
                                     </tr>
                                     <tr>
                                         <td>Usuario</td>                                         
                                         <td>${auditoria.usuario}</td>
                                         <td>${auditoria.usuario}</td>
                                     </tr>
                                      <tr>
                                         <td>Conexion</td>                                         
                                         <td>${auditoria.fechaInicioSesion}</td>
                                         <td>${auditoria.fechaFinSesion}</td>
                                      </tr>                                     
                                      <tr>
                                         <td>Estado Conexion</td>                                                    
                                         <td>                                             
                                             <c:if test = "${auditoria.nuevoEstadoConexion == 1}">
                                                 <span class="label label-success">Ingreso</span>
                                             </c:if>                                             
                                             <c:if test = "${auditoria.nuevoEstadoConexion == 0}">
                                                 <span class="label label-success">Salida</span>
                                             </c:if>
                                         </td>
                                         <td>
                                             <c:if test = "${auditoria.antiguoEstadoConexion == 1}">
                                                 <span class="label label-success">Ingreso</span>
                                             </c:if>                                             
                                             <c:if test = "${auditoria.antiguoEstadoConexion == 0}">
                                                 <span class="label label-success">Salida</span>
                                             </c:if>
                                         </td>
                                      </tr>                                     
                                     </tbody>
                                       <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>
                         </c:when>
                                                                            
                         <c:when test ="${tablaAImprimir == 14}">
                            <table id="tableAuditoriaVehiculoNew" class="display" cellspacing="0" width="100%">
                                     <thead>
                                         <tr>                         
                                            <th>Etiquetas</th>                                             
                                             <th>informaci&oacute;n Nueva</th>                                             
                                             <th>informaci&oacute;n Anterior</th>                                               
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <tr>
                                     <td>Nueva placa</td>
                                     <td>${auditoria.nuevoPlaca}</td>                                     
                                     <td>${auditoria.antiguoPlaca}</td>                                     
                                     </tr>                                     
                                     
                                     <tr>
                                     <td>Numero interno</td>
                                     <td>${auditoria.nuevoNumeroInterno}</td>                                     
                                     <td>${auditoria.antiguoNumeroInterno}</td>                                                                          
                                     </tr>                                     
                                     
                                     <tr>
                                     <td>Usuario</td>                                         
                                     <td>${auditoria.usuario}</td>
                                     <td>${auditoria.usuario}</td>
                                     </tr>                                     
                                     <tr>
                                          <td>Estado</td>
                                          <c:choose>
                                              <c:when test ="${auditoria.estado == 1}">                                                    
                                                <td><span class="label label-success">Activo</span></td>
                                              </c:when>
                                              <c:otherwise>
                                                  <td><span class="label label-danger">Inactivo</span></td>
                                            </c:otherwise>
                                     </c:choose>
                                                  <c:choose>
                                              <c:when test ="${auditoria.estado == 1}">                                                    
                                                <td><span class="label label-success">Activo</span></td>
                                              </c:when>
                                              <c:otherwise>
                                                  <td><span class="label label-danger">Inactivo</span></td>
                                            </c:otherwise>
                                     </c:choose>
                                    </tr>
                                     </tbody>
                                      <tfoot>
                                         <tr>
                                             <td></td>
                                             <td><a href="/RDW1/app/auditoria/listadoAuditoria.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                             <td></td>
                                         </tr>
                                     </tfoot>
                                     </table>
                         </c:when>
                                    
                                 
           </c:choose>                                        
           </div>
         </div>
           </section>
            </div>
    <jsp:include page="/include/footerHome.jsp" />
    
<script>
    $(document).ready(function() {
            var tabla0 = $('#tableAuditoriaAlarmaNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                 $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }                     
            });
            /***********************************************************/
            var tabla1 = $('#tableAuditoriaCategoriaNew').DataTable({
            searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                 $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales");
                                 $(row).css('color', '#000000');
                             }
                         }                     
            });
            /***********************************************************/
            var tabla2 = $('#tableAuditoriaConductorNew').DataTable({
                 searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            /***********************************************************/
            var tabla3 = $('#tableAuditoriaEmpresaNew').DataTable({
                 searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            /***********************************************************/
            var tabla4 = $('#tableAuditoriaInformacionRegistradoraNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                // console.log("iguales")
                             }
                         }
            });

            $('#tableAuditoriaLiquidacion').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 console.log("iguales")
                             }
                         }
            });
            
            $('#tableAuditoriaPerfilNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            
            $('#tableAuditoriaPuntoNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                 $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });            
            
            $('#tableAuditoriaPuntoDeControlNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                 $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            
            $('#tableAuditoriaRutaNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            
            
            
            
            $('#tableAuditoriaTarifaNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            
             $('#tableAuditoriaUsuarioNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                             
                         }
            });
            
            $('#tableAuditoriaVehiculoNew').DataTable({
                searching: false,
            paging: false,
            bInfo: false,
            rowCallback: function(row, data, index){
                             if (data[1] !== data[2]) {
                                $(row).css('background-color', '#FFD700');                                 
                                 $(row).css('font-weight', 'bold');
                                 $(row).css('color', '#000000');
                                 $(row).find('td:eq(0)').css('color', '#000000');
                             }
                             else{
                                 //console.log("iguales")
                             }
                         }
            });
            
            
    });	        
            
    
</script>
<jsp:include page="/include/end.jsp" />