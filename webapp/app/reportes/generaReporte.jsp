
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="container">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <c:choose>
        <c:when test="${permissions.check(['Reportes'])}">
            <div class="col-lg-8 centered">
                <section class="padding">
                    <form target="_blank" id="form-generar-reporte" action="<c:url value='/generarReporte' />" method="post">
                        <div class="row" style="position: absolute;">
                            <!-- tipo de informe -->
                            <div class="col-xs-12 form-group">
                                <label>Tipo de reporte</label>
                                <select id="tipoReporte" name="tipoReporte" class="selectpicker form-control" onchange="seleccionReporte();">
                                    <option value="0">Seleccione un reporte</option> 
                                    <c:if test="${permissions.check(143)}">
                                        <optgroup label="Liquidaci&oacute;n">
                                            <c:if test="${permissions.check(146)}">
                                                <option value="19"> Consolidado de liquidaci&oacute;n</option>
                                            </c:if>
                                            <c:if test="${permissions.check(147)}">
                                                <option value="20"> Consolidado veh&iacute;culos no liquidados</option>
                                            </c:if>
                                            <c:if test="${permissions.check(148)}">
                                                <option value="21"> Liquidaci&oacute;n por liquidador</option>                                                                                   
                                            </c:if>
                                            <c:if test="${permissions.check(149)}">
                                                <option value="22"> &Iacute;ndice pasajeros por kil&oacute;metro</option>
                                            </c:if>
                                            <c:if test="${permissions.check(149)}">
                                                <option value="35"> Categor&iacute;a de descuento de pasajeros</option>
                                            </c:if>
                                        </optgroup>
                                    </c:if>
                                    <c:if test="${permissions.check(144)}">
                                        <optgroup label="General">
                                            <c:if test="${permissions.check(150)}">
                                                <option value="1"> Puntos de control por veh&iacute;culo</option>       <!-- v,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(151)}">
                                                <option value="2"> Producci&oacute;n por veh&iacute;culo</option>       <!-- v,fi,ff -->                                    
                                            </c:if>
                                            <c:if test="${permissions.check(152)}">
                                                <option value="3"> Ruta por veh&iacute;culo</option>                    <!-- v,f -->
                                            </c:if>
                                            <c:if test="${permissions.check(153)}">
                                                <option value="4"> Alarmas por veh&iacute;culo</option>                 <!-- v,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(154)}">
                                                <option value="5"> Nivel de ocupaci&oacute;n</option>                   <!-- v,fi,ff,r -->
                                            </c:if>
                                            <c:if test="${permissions.check(155)}">
                                                <option value="6"> Conteos en per&iacute;metro por veh&iacute;culo</option>  <!-- v,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(156)}">
                                                <option value="7"> Consolidado por empresa</option>                     <!-- e,fi,ff -->               
                                            </c:if>
                                            <c:if test="${permissions.check(157)}">
                                                <option value="8"> Comparativo producci&oacute;n por ruta</option>      <!-- rts,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(158)}">
                                                <option value="9"> Producci&oacute;n por ruta</option>                  <!-- r,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(159)}">
                                                <option value="11"> Veh&iacute;culos por ruta</option>                  <!-- r,f -->
                                            </c:if>
                                            <c:if test="${permissions.check(160)}">
                                                <option value="12"> Veh&iacute;culos por alarma</option>                <!-- alm,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(161)}">
                                                <option value="13"> Estad&iacute;stico</option>                         <!-- e -->
                                            </c:if>
                                            <c:if test="${permissions.check(162)}">
                                                <option value="14"> Descripci&oacute;n de ruta</option>                 <!-- e,r -->                       
                                            </c:if>
                                            <c:if test="${permissions.check(163)}">
                                                <option value="15"> Gerencia</option>                                   <!-- e,rts,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(164)}">
                                                <!--<option value="16"> Consolidado de Despacho</option>-->             <!-- e,bs,fi,r -->
                                            </c:if>
                                            <c:if test="${permissions.check(165)}">
                                                <option value="17"> Propietario</option>                                <!-- v,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(166)}">
                                                <option value="18"> Gerencia por veh&iacute;culo</option>               <!-- v,fi,ff -->
                                            </c:if>
                                            <c:if test="${permissions.check(222)}">
                                                <option value="31"> Consolidado productividad por hora</option>
                                            </c:if>
                                        </optgroup>
                                    </c:if>
                                    <c:if test="${permissions.check(145)}">
                                        <optgroup label="Consolidado de rutas">
                                            <c:if test="${permissions.check(167)}">
                                                <option value="23"> Consolidado de rutas</option>
                                            </c:if>
                                            <c:if test="${permissions.check(168)}">
                                                <option value="24"> Cumplimiento de ruta por veh&iacute;culo</option>
                                            </c:if>
                                            <c:if test="${permissions.check(169)}">
                                                <option value="25"> Cumplimiento de ruta por conductor</option>
                                            </c:if>
                                            <c:if test="${permissions.check(223)}">
                                                <option value="26">Consolidado de despacho</option>
                                            </c:if>
                                            <c:if test="${permissions.check(224)}">
                                                <option value="27">Incumplimiento de puntos por ruta</option>
                                            </c:if>
                                        </optgroup>
                                    </c:if>
                                    <c:if test="${permissions.check(220)}">
                                        <optgroup label="Conductor">
                                            <c:if test="${permissions.check(221)}">
                                                <option value="30">Calificaci&oacute;n de conductor</option>
                                            </c:if>
                                        </optgroup>
                                    </c:if>
                                </select>
                            </div>
                            <!-- rango de fechas -->
                            <div id="div-fec" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label id="lab-rng-fecha" class="col-xs-12">Rango de fechas</label>
                                    <label id="lab-una-fecha" class="col-xs-12">Seleccione una fecha</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6">
                                        <input id="fechaInicio" name="fechaInicio" class="form-control" type="text"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6">
                                        <input id="fechaFinal" name="fechaFinal" class="form-control" type="text"/>
                                    </div>
                                </div>
                            </div>
                            <!-- vehiculo -->            
                            <div id="div-veh" class="col-xs-6 form-group no-display">    
                                <div class="row">
                                    <label class="col-xs-12">Veh&iacute;culo</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="splaca" name="splaca" class="selectpicker form-control" 
                                                data-live-search="true" data-actions-box="true" 
                                                data-selected-text-format="values">
                                            <option value="">Placa - N&uacute;mero interno</option>
                                            <c:forEach items="${select.lstmovil}" var="veh">
                                                <option value="${veh.id},${veh.placa},${veh.numeroInterno},${veh.capacidad}">${veh.placa} - ${veh.numeroInterno}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!-- multiples vehiculos -->
                            <div id="div-mult-veh" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Veh&iacute;culo</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="smplaca" name="smplaca" class="selectpicker form-control select-maxmin" 
                                                multiple data-live-search="true" data-actions-box="true" 
                                                data-selected-text-format="values" data-width="100%"
                                                title="Placa - N&uacute;mero interno">
                                            <c:forEach items="${select.lstmovil}" var="veh">
                                                <option value="${veh.id}">${veh.placa} - ${veh.numeroInterno}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" id="smplaca_v" name="smplaca_v">
                                    </div>
                                </div>
                            </div>
                            <!-- historico -->
                            <!--
                            <div class="col-xs-6 form-group">
                                <div class="row">
                                    <label class="col-xs-12">Filtro de fecha</label>
                                </div>
                                <div class="col-xs-12 col-sm-4">
                                    <label><input type="radio" name="esteDia" id="esteDia" value="d" checked> Día actual</label>
                                </div>
                                <div class="col-xs-12 col-sm-4">
                                    <label><input type="radio" name="esteDia" id="esteDia" value="h"> Histórico</label>
                                </div>
                            </div>
                            -->                                      
                            <!-- ruta -->
                            <div id="div-rts" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Ruta</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="sruta" data-live-search="true" name="sruta" class="form-control">
                                            <option value="">Seleccione una ruta</option>
                                            <option id="opt-trts" value="0">Todas las rutas</option>
                                            <c:forEach items="${select.lstruta}" var="ruta">
                                                <option value="${ruta.id},${ruta.nombre}">${ruta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!-- multiples rutas -->
                            <div id="div-mult-rts" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Ruta</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="smruta" name="smruta" class="selectpicker form-control select-maxmin" 
                                                multiple data-live-search="true" data-actions-box="true"
                                                title="Seleccione ruta(s)"
                                                data-width="100%">
                                            <c:forEach items="${select.lstruta}" var="ruta">
                                                <option value="${ruta.id},${ruta.nombre}"> ${ruta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" id="smruta_v" name="smruta_v">
                                    </div>
                                </div>
                            </div>
                            <!-- conductores -->
                            <div id="div-mult-cond" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Conductor</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="smconductor" name="smconductor" class="selectpicker form-control select-maxmin"
                                                multiple data-live-search="true" data-actions-box="true"
                                                title="Seleccione conductor(es)"
                                                data-width="100%">
                                            <c:forEach items="${select.lstconductoractivo}" var="conductor">
                                                <option value="${conductor.id}" selected="selected">${conductor.apellido}, ${conductor.nombre}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" id="smconductor_v" name="smconductor_v">
                                    </div>
                                </div>
                            </div>
                            <!-- alarma -->
                            <div id="div-mult-alm" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Alarma</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="smalarma" name="smalarma" class="selectpicker form-control select-maxmin"
                                                multiple data-live-search="true" data-actions-box="true"
                                                title="Seleccione alarma(s)"
                                                data-width="100%">                                            
                                            <c:forEach items="${select.lstalarma}" var="alarma">
                                                <option value="${alarma.id}">${alarma.nombreAlarma}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" id="smalarma_v" name="smalarma_v">
                                    </div>
                                </div>
                            </div>
                            <!-- bases -->
                            <div id="div-bs" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Base</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="sbase" name="sbase" class="form-control">                      
                                            <option value="">Seleccione una base</option>
                                            <c:forEach items="${select.lstbase_pr}" var="base">
                                                <option value="${base.idbase},${base.nombre}">${base.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>                            
                            <!-- liquidador -->
                            <div id="div-liq" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Liquidador</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <select id="sliquidador" name="sliquidador" class="select-picker form-control select-maxmin"
                                                title="Seleccione un liquidador"
                                                data-live-search="true" data-width="100%">
                                            <c:forEach items="${lst_liquidadores}" var="liq">
                                                <option value="${liq.id}">${liq.numdoc} - ${liq.nombre} ${liq.apellido}</option>
                                            </c:forEach>
                                        </select>                                        
                                    </div>
                                </div>
                            </div>
                            <!-- Meta -->
                            <div id="div-meta" class="col-xs-6 form-group no-display">
                                <div class="row">
                                    <label class="col-xs-12">Meta %</label>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <!-- <input id="meta" name="meta" class="form-control" min="10" max="100" type="number"> -->
                                        <select id="smeta" name="smeta" class="form-control">
                                            <c:forEach begin="10" end="100" step="5" varStatus="loop">
                                                <option value="${(110-loop.index)}">${(110-loop.index)}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!-- Tipo de archivo -->
                            <div class="col-xs-6 form-group">
                                <div class="row">
                                    <label class="col-xs-12">Tipo de archivo</label>
                                </div>
                                <div class="col-xs-12 col-sm-4">
                                    <label><input type="radio" name="tipoArchivo" id="tipoArchivo" value="r" checked> PDF/Web</label>
                                </div>
                                <div class="col-xs-12 col-sm-4">
                                    <label><input type="radio" name="tipoArchivo" id="tipoArchivo" value="w"> Excel</label>
                                </div>
                            </div>
                            <!-- lanzador -->
                            <div class="col-xs-6 form-group">                
                                <div class="row" style="padding-top: 25px; padding-left: 25px;">
                                    <button type="button" class="btn btn-primary" onclick="generarReporte2();">Generar reporte</button>
                                </div>
                            </div>
                            <!-- errores -->
                            <div class="col-xs-12 form-group">
                                <div id="msg" class="form-msg ${msgType}" role="alert">${msg}</div>
                                <input type="hidden" name="result_error" id="result_error" value="${result_error}">
                            </div>
                        </div>
                    </form>
                    <form id="form-data-reporte" action="<c:url value='/dataReporte' />" method="post">
                        <input type="hidden" id="data_reporte" name="data_reporte" value="${data_reporte}">
                    </form>
                </section>
            </div>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<div style="height: 346px; width: 100%; float: left; display: block;"></div>

<jsp:include page="/include/footerHome_.jsp" />
<script>

    $(document).ready(function () {

        var result_error = $('#result_error').val();
        if (result_error != "1") {
            var data_reporte = $('#data_reporte').val();
            if (data_reporte == null || data_reporte == "") {
                $('#form-data-reporte').submit();
                return;
            }
        }

        var fechaInicio = $('#fechaInicio').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            fechaInicio.datepicker('hide');
            actualizarFechaFinal();
        });

        var fechaFinal = $('#fechaFinal').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            fechaFinal.datepicker('hide');
        });

        $('#fechaInicio').datepicker('setDate', getDate());
        $('#fechaFinal').datepicker('setDate', getDate());

        $('#smruta').selectpicker({
            size: 5
        });
        $('#smplaca').selectpicker({
            size: 5
        });
        $('#smalarma').selectpicker({
            size: 5            
        });
        $('#splaca').selectpicker({
            size: 5
        });
        $('#sliquidador').selectpicker({
            size: 5
        });
        $('#smconductor').selectpicker({
            size: 5
        });

        //ocultarParametros();
    });

</script>
<jsp:include page="/include/end.jsp" />
