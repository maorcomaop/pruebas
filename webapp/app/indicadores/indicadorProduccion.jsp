
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-10 centered">
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Indicadores'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Indice de productividad por ruta</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">
                        <div class="row">
                            <div class="control-group">
                                <label class="col-sm-2">Fecha de consulta</label>
                                <div class="col-sm-4">
                                    <input type="text" id="fecha-indicador" name="fecha-indicador" readonly="readonly">
                                    <input type="button" onclick="prepara_Produccion();" value="Consultar">
                                    <a href="/RDW1/app/indicadores/inicio_def.jsp">Volver</a>
                                </div>                                
                            </div>
                        </div>
                        <div id="msg-ip" style="margin-top: 12px;"></div>
                        <canvas id="chart-ip" style="margin-top: 12px;" width="800" height="450"></canvas>
                        <div id="data-ip"></div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
    <input type="hidden" id="fecha-ind-gbl" name="fecha-ind-gbl" value="${fechaIndicador}">
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
            prepara_Produccion();
        });

        // Para seleccionar y resaltar fecha en componente
        fecha.datepicker('setDate', new Date());
        fecha.datepicker('update');

        validaParametrosIndicador();
        prepara_Produccion();
    });
</script>
<jsp:include page="/include/end.jsp" />
