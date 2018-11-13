
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Indicadores'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Descuento de Pasajeros por Categor&iacute;a</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                
                        <table>
                            <tbody>
                                <tr>
                                    <td style="padding-top: 8px;">
                                        <label>Fecha de consulta</label>
                                    </td>
                                    <td style="padding-left: 8px;">
                                        <input type="text" id="fecha_busqueda_pasajeros" name="" class="form-control" readonly="readonly" value="${fechaIndicador}">
                                    </td>
                                    <td style="padding-left: 8px; text-align: right;">
                                        <a class="button success" href="/RDW1/app/indicadores/inicio_def.jsp">Volver</a>
                                        <input type="button" onclick="traerDatosParaElGrafico();" value="Consultar">
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <div id="msg" style="margin-top: 12px;"></div>
                        <canvas id="pie-chart" style="margin-top: 12px;" width="800" height="450"></canvas>
                        <div id="data"></div>
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
<jsp:include page="/include/end.jsp" />
<script>
    $(document).ready(function () {        
        var fecha1 = $('#fecha_busqueda_pasajeros').datepicker({
            dateFormat: 'yyyy-mm-dd',          
            autoclose: true,
            todayHighlight: true,            
            language: 'es'});        
    });    
    prepara_PasajeroDescontado1();
</script>
    