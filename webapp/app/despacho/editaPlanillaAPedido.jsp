
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />
<div class="col-lg-8 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <c:choose>
        <c:when test="${permissions.check(['Despacho', 'GeneracionDePlanilla'])}">
            <c:choose>
                <c:when test="${planilla == null}">
                    * No existe planillaDespacho relacionada.
                </c:when>
                <c:otherwise>                            

                    <section class="boxed padding-smin">                                
                        <ul class="nav nav-tabs">
                            <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                                <li role="presentation"><a href="#">Nuevo Despacho</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','ListadoDespachos'])}">
                                <li role="presentation"><a href="/RDW1/app/despacho/consultaDespacho.jsp">Despachos</a></li>
                            </c:if>                    
                                <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                                <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                                <li role="presentation" class="active"><a href="#">Edici&oacute;n de Planilla a Pedido</a></li>
                                <li role="presentation"><a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir Veh&iacute;culo</a></li>                    
                            <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                                <li role="presentation"><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                            </c:if>
                            <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                                <li><a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                            </c:if>
                        </ul>
                        
                        <div class="tab-content">                                    
                            <div role="tabpanel" class="tab-pane padding-smin active" style="padding-left: 20px;">                                   

                                <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                                                                

                                <div class="row">
                                    <div class="control-group">
                                        <label class="col-sm-2 gray-style">Ruta</label>
                                        <div class="col-sm-4">${planilla.nombreRuta}</div>
                                    </div>
                                    <div class="control-group">
                                        <label class="col-sm-2 gray-style">Veh&iacute;culo</label>
                                        <div class="col-sm-4">${planilla.placa}</div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="control-group">
                                        <label class="col-sm-2 gray-style">Fecha</label>
                                        <div class="col-sm-4">${planilla.fecha}</div>
                                    </div>                    
                                    <div class="control-group">
                                        <label class="col-sm-2 gray-style">Hora salida</label>
                                        <div class="col-sm-4">${planilla.horaInicio}</div> 
                                    </div>
                                </div>
                                    
                                <div class="row">
                                    <div class="control-group">
                                        <label class="col-sm-2 gray-style">Salida desde punto retorno</label>
                                        <div class="col-sm-4">
                                            <c:if test="${planilla.iniciaPuntoRetorno}">
                                                Si
                                            </c:if>
                                            <c:if test="${!planilla.iniciaPuntoRetorno}">
                                                No
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <hr/>

                                <form id="form-edita-planilla-pedido" action="<c:url value='/editarPlanillaAPedido' />" method="post">
                                    <input type="hidden" id="idIntervalo" name="idIntervalo" value="${planilla.idIntervalo}">
                                    <input type="hidden" id="numeroPlanilla" name="numeroPlanilla" value="${planilla.numeroPlanilla}">
                                    <input type="hidden" id="numeroVuelta" name="numeroVuelta" value="${planilla.numeroVuelta}">
                                    <input type="hidden" id="nombreRuta" name="nombreRuta" value="${planilla.nombreRuta}">
                                    <input type="hidden" id="fecha" name="fecha" value="${planilla.fecha}">
                                    <input type="hidden" id="smovil" name="smovil" value="${planilla.placa}">
                                    <input type="hidden" id="horaSalida" name="horaSalida" value="${planilla.horaInicio}">
                                    <div class="row">
                                        <div class="control-group">
                                            <label class="col-sm-2">Reemplazar por veh&iacute;culo</label>
                                            <div class="col-sm-4">
                                                <select id="ssmovil" name="ssmovil" class="selectpicker form-control"
                                                        data-live-search="true" data-actions-box="true" data-container="body">
                                                    <option value="">Seleccione una opci&oacute;n</option>
                                                    <c:forEach items="${select.lstmovil}" var="movil">
                                                        <option value="${movil.placa}">${movil.placa} - ${movil.numeroInterno}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">                                                   
                                            <button type="button" class="btn" onclick="dph_editarPlanillaAPedido();">Reemplazar</button>
                                        </div>
                                    </div>
                                </form>

                            </div>
                        </div>
                                    
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane padding-smin active">
                                <label><input type="checkbox" id="chk_dia_actual" name="chk_dia_actual" checked="checked"
                                  onclick="dph_tabularVueltaDelDia();">&nbsp;Tabular &uacute;nicamente d&iacute;a actual</label>
                                <table id="tablaDespachoManual" class="display" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>                                  
                                            <th class="col-hidden-2"></th>
                                            <th>N° Planilla</th>
                                            <th>Ruta</th>        
                                            <th>N° Vuelta</th>
                                            <th>Placa</th>
                                            <th>Fecha</th>
                                            <th>Hora salida</th>
                                            <th>Hora llegada</th>
                                            <th>Ver planilla</th>
                                            <th>Editar</th>
                                            <th>Deshabilitar</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${select.lstintervalo_despacho_manual}" var="idph">
                                            <tr>
                                                <td class="col-hidden-2">${idph.id}</td>
                                                <td>${idph.numeroPlanilla}</td>
                                                <td>${idph.listaNombreRuta}</td>
                                                <td>${idph.numeroVuelta}</td>
                                                <td>${idph.placa}</td>
                                                <td>${idph.fechaInicial}</td>
                                                <td>${idph.horaInicial}</td>
                                                <td>${idph.horaFinal}</td>
                                                <td>
                                                    <button class="btn btn-success btn-info btn-xs" onclick="dph_verPlanillaFmt(${idph.id}, '/RDW1/verPlanillaAPedido');">Ver planilla</button>
                                                </td>
                                                <td>
                                                    <form id="form-pre-edita-planilla-pedido" action="<c:url value='/pre_editarPlanillaAPedido' />" method="post">
                                                        <input type="hidden" id="idIntervalo" name="idIntervalo" value="${idph.id}">
                                                        <button type="submit" style="width: 80px;" class="btn btn-success btn-xs">Editar</button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form id="form-delete-planilla-pedido-${idph.id}" action="<c:url value='/eliminarPlanillaAPedido' />" method="post">
                                                        <input type="hidden" id="idIntervalo" name="idIntervalo" value="${idph.id}">
                                                        <input type="hidden" id="numeroPlanilla" name="numeroPlanilla" value="${idph.numeroPlanilla}">
                                                        <input type="hidden" id="numeroVuelta" name="numeroVuelta" value="${idph.numeroVuelta}">
                                                        <input type="hidden" id="smovil" name="smovil" value="${idph.placa}">
                                                        <button type="button" class="btn btn-danger btn-xs" style="width: 80px;"
                                                                onclick="confirmar_eliminacion(
                                                                                '${idph.id}',
                                                                                '${idph.numeroPlanilla}',
                                                                                '${idph.listaNombreRuta}',
                                                                                '${idph.numeroVuelta}',
                                                                                '${idph.placa}',
                                                                                '${idph.fechaInicial}',
                                                                                '${idph.horaInicial}');">
                                                        Eliminar
                                                        </button>
                                                    </form>
                                                </td>                                        
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>                                   
                    </section>

                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>
        
<jsp:include page="/include/footerHome_.jsp" />
<script>
    $(document).ready(function() {
        
        // Tabla despacho manual
        $('#tablaDespachoManual').DataTable({
            "bSort": false,
            "aLengthMenu": [5, 7, 10, 25],
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
        dph_tabularVueltaDelDia();        
    });
</script>
<jsp:include page="/include/end.jsp" />
