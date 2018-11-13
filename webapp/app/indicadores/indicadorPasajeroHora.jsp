
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-10 centered">
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Indicadores'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Indice de pasajero por hora</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">

                        <table>
                            <tbody>
                                <tr>
                                    <td><label>Fecha de consulta</label></td>
                                    <td style="padding-left: 8px;">
                                        <input type="text" id="fecha-indicador" name="fecha-indicador" class="form-control" readonly="readonly">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label>Ruta</label></td>
                                    <td style="padding-left: 8px;">
                                        <select id="sruta-iph" name="sruta-iph" class="select-picker form-control"
                                            data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                            data-width="auto" data-max-options="10" data-container="body"
                                            title="Seleccione ruta(s)" onchange="prepara_PasajeroHora();" multiple>
                                            <option value="-1">Ruta no detectada</option>
                                            <c:forEach items="${select.lstruta}" var="ruta">
                                                <option value="${ruta.id}">${ruta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td style="padding-left: 8px; text-align: right;">
                                        <a href="/RDW1/app/indicadores/inicio_def.jsp">Volver</a>
                                        <input type="button" onclick="prepara_PasajeroHora();" value="Consultar">                                        
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <div id="msg-iph" style="margin-top: 12px;"></div>
                        <canvas id="chart-iph" style="margin-top: 12px;" width="800" height="450"></canvas>
                        <div id="data-iph"></div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
    <input type="hidden" id="fecha-ind-gbl" name="fecha-ind-gbl" value="${fechaIndicador}">
    <input type="hidden" id="sruta-iph-gbl" name="sruta-iph-gbl" value="${rutasIph}">
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script>
    $(document).ready(function () {

        var fecha = $('#fecha-indicador').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayHighlight: true,
            //todayBtn: 'linked',
            language: 'es'
        }).on('changeDate', function (e) {
            fecha.datepicker('hide');
            prepara_PasajeroHora();
        });
        
        $('#sruta-iph').selectpicker({
            size: 8
        });

        // Para seleccionar y resaltar fecha en componente
        fecha.datepicker('setDate', new Date());
        fecha.datepicker('update');

        validaParametrosIndicador();
        prepara_PasajeroHora();
    });
</script>
<jsp:include page="/include/end.jsp" />
