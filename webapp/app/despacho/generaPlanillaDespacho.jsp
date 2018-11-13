
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
                        <li role="presentation" class="active"><a href="#">Generaci&oacute;n de Planilla</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir Veh&iacute;culo</a></li>
                    <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                        <li><a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                    </c:if>
                </ul>
                <c:if test="${permissions.check(137)}">
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding-smin active">

                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                

                            <!-- Generacion de planilla -->
                            <form id="form-generar-planilla" name="form-generar-planilla" action="<c:url value='/generarPlanilla' />" method="post"
                                  style="margin-bottom: 46px;">
                                <div class="row" style="margin-top: 10px;">
                                    <div class="control-group">
                                        <label class="col-sm-2">Despacho de ruta</label>
                                        <div class="col-sm-4">
                                            <select id="sruta_despacho" name="sruta_despacho" class="selectpicker form-control" 
                                                    data-live-search="true" data-actions-box="true" data-container="body"
                                                    onchange="dph_consultaTipoDia();">
                                                <option value="">Seleccione una opci&oacute;n</option>
                                                <option value="all">Todas las rutas</option>
                                                <c:forEach items="${select.lstruta_despacho}" var="ruta">
                                                    <option value="${ruta.id}%${ruta.nombre}">${ruta.nombre}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>                                    
                                    <div id="infoTipoDia" name="infoTipoDia" class="control-group">                                        
                                    </div>
                                </div>                    
                                <div class="row" style="margin-top: 8px;">
                                    <div class="control-group">
                                        <label class="col-sm-2">Fecha inicial</label>
                                        <div class="col-sm-4">
                                            <input id="fechaInicial" name="fechaInicial" class="form-control" type="text" readonly="readonly">
                                        </div>
                                    </div>
                                </div>
                                <div class="row" style="margin-top: 10px;">
                                    <div class="control-group">
                                        <label class="col-sm-2">Fecha final</label>
                                        <div class="col-sm-4">
                                            <input id="fechaFinal" name="fechaFinal" class="form-control" type="text" readonly="readonly">
                                        </div>
                                    </div>
                                </div>                        
                                <div class="row" style="margin-top: 10px;">                        
                                    <div class="control-group">
                                        <label class="col-sm-2"></label>
                                        <div class="col-sm-4">
                                            <input class="btn" type="button" value="Generar planilla" onclick="dph_generarPlanilla();">
                                        </div>
                                    </div>
                                </div>
                            </form>

                            <!-- Edicion de fechas -->
                            <form id="form-edita-fecha-planilla" name="form-edita-fecha-planilla" 
                                  action="<c:url value='/editarFechasPlanilla' />"
                                  style="margin-bottom: 46px; display: none;" method="post">

                                <input type="hidden" id="edt_id" name="edt_id">

                                <div class="row" style="margin-top: 21px;">
                                    <div class="control-group">
                                        <label class="col-sm-2">Planilla N°</label>
                                        <div class="col-sm-4">
                                            <span id="edt_nombre" name="edt_nombre"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" style="margin-top: 8px;">
                                    <div class="control-group">
                                        <label class="col-sm-2">Fecha inicial</label>
                                        <div class="col-sm-4">
                                            <input id="edt_fechaInicial" name="edt_fechaInicial" class="form-control" type="text" readonly="readonly">
                                        </div>
                                    </div>
                                </div>
                                <div class="row" style="margin-top: 10px;">
                                    <div class="control-group">
                                        <label class="col-sm-2">Fecha final</label>
                                        <div class="col-sm-4">
                                            <input id="edt_fechaFinal" name="edt_fechaFinal" class="form-control" type="text" readonly="readonly">
                                            <span class="msg-advice">* Por favor seleccione el rango de fechas a suprimir.</span>
                                        </div>
                                    </div>                        
                                </div>                    
                                <div class="row" style="margin-top: 10px;">                        
                                    <div class="control-group">
                                        <label class="col-sm-2"></label>
                                        <div class="col-sm-4">
                                            <input class="btn" type="button" value="Suprimir fechas" onclick="dph_editarFechas();">
                                            <input class="btn" type="button" value="Cancelar" onclick="dph_cancelarEdicionFechas();">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- <div style="height: 20px;"></div>-->
                    </div>
                </c:if>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">
                        <table id="tablaIntervaloDph" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>N° Intv. Planilla</th>
                                    <th>Nombre</th>
                                    <th>Rutas</th>
                                    <th>Fecha inicial</th>
                                    <th>Fecha final</th>
                                    <th>Cantidad d&iacute;as</th>
                                    <th>Ver</th>
                                        <c:if test="${permissions.check(138)}">
                                        <th>Editar</th>
                                        </c:if>
                                        <c:if test="${permissions.check(139)}">
                                        <th>Deshabilitar</th>
                                        </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${select.lstintervalo_despacho}" var="idph">
                                    <tr>
                                        <td>${idph.id}</td>
                                        <td>${idph.nombreIntervalo}</td>
                                        <td>${idph.listaNombreRuta}</td>
                                        <td>${idph.fechaInicial}</td>
                                        <td>${idph.fechaFinal}</td>
                                        <td>${idph.cantidadDias}</td>
                                        <td>                            
                                            <button class="btn btn-success btn-info btn-xs" onclick="dph_verPlanillaFmt(${idph.id}, '/RDW1/verPlanilla');">Ver planilla</button>
                                        </td>
                                        <c:if test="${permissions.check(138)}">
                                            <td>
                                                <!--
                                                <form class="form-in-table" method="post">
                                                    <input type="hidden" id="idIntervalo" name="idIntervalo">
                                                    <button type="submit" class="btn btn-success btn-info btn-xs">Editar fechas</button>
                                                </form>
                                                -->
                                                <c:choose>
                                                    <c:when test="${idph.fechaInicial != idph.fechaFinal}">
                                                        <div>
                                                            <input type="hidden" id="item_rutas_${idph.id}"        value="${idph.listaRuta}">
                                                            <input type="hidden" id="item_id_${idph.id}"           value="${idph.id}">
                                                            <input type="hidden" id="item_fechaInicial_${idph.id}" value="${idph.fechaInicial}">
                                                            <input type="hidden" id="item_fechaFinal_${idph.id}"   value="${idph.fechaFinal}">
                                                            <button class="btn btn-success btn-info btn-xs" onclick="dph_preEditarFechas(${idph.id});">Editar fechas</button>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div>
                                                            <button class="btn btn-success btn-info btn-xs" disabled="disabled">Editar fechas</button>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:if>
                                        <c:if test="${permissions.check(139)}">
                                            <td>
                                                <form id="form-delete-planilla-${idph.id}" class="form-in-table" action="<c:url value='/eliminarPlanilla' />" method="post">
                                                    <input type="hidden" id="idIntervalo" name="idIntervalo" value="${idph.id}">
                                                    <button type="button" class="btn btn-danger btn-xs" 
                                                            onclick="confirmar_eliminacion(
                                                                                    '${idph.id}',
                                                                                    '${idph.nombreIntervalo}',
                                                                                    '${idph.fechaInicial}',
                                                                                    '${idph.fechaFinal}',
                                                                                    '${idph.listaNombreRuta}');">
                                                        Eliminar</button>
                                                </form>
                                            </td>
                                        </c:if>
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

        var fechaInicial = $('#fechaInicial').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            startDate: new Date(),
            language: 'es'
        }).on('changeDate', function (e) {
            fechaInicial.datepicker('hide');
        });

        var fechaFinal = $('#fechaFinal').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            startDate: new Date(),
            language: 'es'
        }).on('changeDate', function (e) {
            fechaFinal.datepicker('hide');
        });

        //$('#fechaInicial').datepicker('setDate', getDate());
        //$('#fechaFinal').datepicker('setDate', getDate());

        var edt_fi = $('#edt_fechaInicial').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            edt_fi.datepicker('hide');
        });

        var edt_ff = $('#edt_fechaFinal').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            edt_ff.datepicker('hide');
        });

        // tabla dinamica de intervalos de despacho
        $('#tablaIntervaloDph').DataTable({
            "bSort": false,
            "aLengthMenu": [5, 7, 10, 25],
            /*
            responsive: {
                details: {
                    type: 'column',
                    target: 'tr'
                }
            }, */
            "responsive": true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        // Seleccion multiple de despacho de rutas
        $('#sruta_despacho').selectpicker({
            size: 5
        });
    });

    function confirmar_eliminacion(
                    id,
                    nombre_intervalo, 
                    fecha_inicial, 
                    fecha_final, 
                    rutas) {

        var sform = "#form-delete-planilla-" + id;
        var msg = "¿Est&aacute; seguro de eliminar la siguiente agrupaci&oacute;n de planillas despacho?<br><br>";
        msg += "<strong>N° Intv. planilla:</strong> " + id + "<br>";
        msg += "<strong>Nombre:</strong> " + nombre_intervalo + "<br>";
        msg += "<strong>Fecha inicial:</strong> " + fecha_inicial + "<br>";
        msg += "<strong>Fecha final:</strong> " + fecha_final + "<br>";
        msg += "<strong>Rutas:</strong> " + rutas;

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

</script>
<jsp:include page="/include/end.jsp" />
