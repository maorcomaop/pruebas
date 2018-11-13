
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />
<div class="col-lg-8 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <c:choose>
        <c:when test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
            <section class="boxed padding-smin">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Nuevo Despacho</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ListadoDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/consultaDespacho.jsp">Despachos</a></li>
                    </c:if>                    
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                        <li role="presentation" class="active"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir Veh&iacute;culo</a></li>                    
                    <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                        <li><a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                    </c:if>
                </ul>
                
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">

                        <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                

                        <form id="form-generar-planilla-pedido" name="form-generar-planilla-pedido" 
                              action="<c:url value='/generarPlanillaAPedido' />" method="post"
                              style="margin-bottom: 46px;">
                            
                            <div class="row" style="margin-top: 10px;">
                                <div class="control-group">
                                    <label class="col-sm-2">Despacho de ruta</label>
                                    <div class="col-sm-4">
                                        <select id="sruta_despacho" name="sruta_despacho" class="selectpicker form-control" 
                                                data-live-search="true" data-actions-box="true" data-container="body"
                                                onchange="dph_consultaTipoDia();">
                                            <option value="">Seleccione una opci&oacute;n</option>                                                
                                            <c:forEach items="${select.lstruta_despacho}" var="ruta">
                                                <option value="${ruta.id}%${ruta.nombre}">${ruta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>                                    
                                <div class="control-group">
                                    <div id="infoTipoDia" name="infoTipoDia" class="col-sm-4"></div>
                                </div>
                            </div>                    
                            
                            <div class="row" style="margin-top: 10px;">
                                <div class="control-group">
                                    <label class="col-sm-2">Veh&iacute;culo</label>
                                    <div class="col-sm-4">
                                        <select id="smovil" name="smovil" class="selectpicker form-control"
                                                data-live-search="true" data-actions-box="true" data-container="body">
                                            <option value="">Seleccione una opci&oacute;n</option>
                                            <c:forEach items="${select.lstmovil}" var="movil">
                                                <option value="${movil.placa}">${movil.placa} - ${movil.numeroInterno}</option>
                                            </c:forEach>
                                        </select>
                                    </div>              
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-2">Hora salida</label>
                                    <div class="col-sm-4">
                                        <input id="hora_salida" name="hora_salida" class="form_datetime form-control" type="text" readonly="readonly">
                                    </div>
                                </div>
                            </div>                            
                            
                            <div class="row" style="margin-top: 10px;">                        
                                <div class="control-group">                                    
                                    <div class="col-sm-2"></div>
                                    <div class="col-sm-4">
                                        <label>
                                            <input id="inicia_punto_retorno" name="inicia_punto_retorno" type="checkbox">&nbsp;Salida desde punto retorno</label>
                                            <input id="vinicia_punto_retorno" name="vinicia_punto_retorno" type="hidden">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-2"></label>
                                    <div class="col-sm-4">
                                        <input class="btn" type="button" value="Guardar" style="width: 115px;" onclick="dph_generarPlanillaAPedido();">
                                    </div>
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
                                    <th></th>
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
                                    <th>Cancelar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${select.lstintervalo_despacho_manual}" var="idph">
                                    <tr>
                                        <td><span class="col-hidden-2">${idph.id}</span></td>
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
                                                <button type="submit" class="btn btn-success btn-xs" style="width: 80px;">Editar</button>
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
                                        <td>
                                            <form id="form-cancel-planilla-pedido-${idph.id}" action="<c:url value='/cancelarPlanillaAPedido' />" method="post">
                                                <input type="hidden" id="idIntervalo" name="idIntervalo" value="${idph.id}">
                                                <input type="hidden" id="numeroPlanilla" name="numeroPlanilla" value="${idph.numeroPlanilla}">
                                                <input type="hidden" id="numeroVuelta" name="numeroVuelta" value="${idph.numeroVuelta}">
                                                <input type="hidden" id="smovil" name="smovil" value="${idph.placa}">
                                                <button type="button" class="btn btn-danger btn-xs" style="width: 80px;"
                                                        onclick="confirmar_cancelacion(
                                                                        '${idph.id}',
                                                                        '${idph.numeroPlanilla}',
                                                                        '${idph.listaNombreRuta}',
                                                                        '${idph.numeroVuelta}',
                                                                        '${idph.placa}',
                                                                        '${idph.fechaInicial}',
                                                                        '${idph.horaInicial}');">
                                                Cancelar
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
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>    
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script>
    $(document).ready(function () {

        // Tabla despacho manual
        $('#tablaDespachoManual').DataTable({
            "responsive": true,
            /* "bAutoWidth": true, */            
            "paging": false,
            "bSort": false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}            
        });

        // Seleccion de ruta despacho
        $('#sruta_despacho').selectpicker({
            size: 5
        });
        
        // Seleccion de movil
        $('#smovil').selectpicker({
            size: 5
        });
        
        // Hora de salida
        $('#hora_salida').datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            startView: 1,
            minuteStep: 1,
            language: 'es'
        });
        
        dph_tabularVueltaDelDia();
    });

    function confirmar_eliminacion(
                        id,
                        numero_planilla,
                        nombre_ruta,
                        numero_vuelta,
                        placa,
                        fecha,
                        hora_salida) {

        var sform = "#form-delete-planilla-pedido-" + id;
        var msg = "¿Est&aacute; seguro de eliminar la siguiente planilla de despacho?";
        msg += " Dejar&aacute; de existir en el sistema.<br><br>"
        msg += "<strong>N° de planilla:</strong> " + numero_planilla + "<br>";
        msg += "<strong>Ruta:</strong> " + nombre_ruta + "<br>";
        msg += "<strong>Placa:</strong> " + placa + "<br>";
        msg += "<strong>Fecha:</strong> " + fecha + "<br>";
        msg += "<strong>N° vuelta</strong>: " + numero_vuelta + "<br>";
        msg += "<strong>Hora salida:</strong> " + hora_salida;

        bootbox.confirm({
            title: "Eliminaci&oacute;n de registro",
            message: msg,
            buttons: {
                confirm: {
                    label: 'Aceptar',
                    className: 'btn btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn'
                }
            },
            callback: function (rs) {
                if (rs) {
                    $(sform).submit();
                }
            }
        });
    }
    
    function confirmar_cancelacion(
                        id,
                        numero_planilla,
                        nombre_ruta,
                        numero_vuelta,
                        placa,
                        fecha,
                        hora_salida) {

        var sform = "#form-cancel-planilla-pedido-" + id;
        var msg = "¿Est&aacute; seguro de cancelar la siguiente planilla de despacho?<br>";
        msg += " No podr&aacute; hacer uso de ella en el futuro.<br><br>"
        msg += "<strong>N° de planilla:</strong> " + numero_planilla + "<br>";
        msg += "<strong>Ruta:</strong> " + nombre_ruta + "<br>";
        msg += "<strong>Placa:</strong> " + placa + "<br>";
        msg += "<strong>Fecha:</strong> " + fecha + "<br>";
        msg += "<strong>N° vuelta</strong>: " + numero_vuelta + "<br>";
        msg += "<strong>Hora salida:</strong> " + hora_salida;

        bootbox.confirm({
            title: "Cancelaci&oacute;n de registro",
            message: msg,
            buttons: {
                confirm: {
                    label: 'Aceptar',
                    className: 'btn btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn'
                }
            },
            callback: function (rs) {
                if (rs) {
                    $(sform).submit();
                }
            }
        });
    }

</script>
<jsp:include page="/include/end.jsp" />
