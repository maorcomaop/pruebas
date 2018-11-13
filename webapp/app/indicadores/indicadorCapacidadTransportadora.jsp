
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-10 centered">
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Indicadores'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Indice de capacidad transportadora</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">

                        <table>
                            <tbody>
                                <tr>
                                    <td style="padding-top: 4px;"><label>Fecha de consulta</label></td>
                                    <td style="padding-left: 8px;">
                                        <input type="text" id="fecha-indicador" name="fecha-indicador" readonly="readonly">
                                    </td>                        
                                    <td style="padding-left: 8px; text-align: right;">
                                        <input type="button" onclick="prepara_CapacidadTransportadora();" value="Consultar">
                                        <a href="/RDW1/app/indicadores/inicio_def.jsp">Volver</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>                

                        <div id="msg-ict" style="margin-top: 12px;"></div>                
                        <div id="data-ict" class="div-table-style" style="margin-top: 12px;"></div>
                        <div id="resumen-ict" class="top-xl-space" style="display: none;">
                            <table class="tbl-style bottom-space">
                                <tbody>
                                    <!-- <tr><td class="text-align-center" colspan="4">Indicadores</td></tr> -->
                                    <tr>
                                        <td class="lbl-rojo-claro" title="Capacidad no alcanzada">
                                            x <= 0.70</td>
                                        <td class="lbl-naranja-claro" title="Capacidad casi alcanzada y/o superada">
                                            0.70 < x <= 1.50</td>
                                        <td class="lbl-verde-claro" title="Capacidad superada">
                                            1.50 < x <= 2.00</td>
                                        <td class="lbl-morado-claro" title="Capacidad superada a m&aacute;s del doble">
                                            x > 2.00</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
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
            prepara_CapacidadTransportadora();
        });

        // Para seleccionar y resaltar fecha en componente
        fecha.datepicker('setDate', new Date());
        fecha.datepicker('update');

        validaParametrosIndicador();
        prepara_CapacidadTransportadora();
    });
</script>
<jsp:include page="/include/end.jsp" />
