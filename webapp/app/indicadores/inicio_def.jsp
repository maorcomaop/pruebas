
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-12 centered">    
    <c:choose>
        <c:when test="${permissions.check(['Indicadores'])}">
    
            <header class="ind-header">
                <div>
                    <table align="center">
                        <tr>
                            <td class="font-title" align="center">Indicadores del d&iacute;a</td>
                            <td style="padding-left: 20px; padding-top: 4px;">
                                <input type="text" id="fecha-indicador" name="fecha-indicador" class="form-control" 
                                       onchange="iniciaIndicadores();" readonly="readonly">                                
                            </td>
                        </tr>
                    </table>
                </div>
            </header>

            <input type="hidden" id="tabla-indicador" name="tabla-indicador">
            <div class="ind-container">

                <!-- IP -->
                <div class="minw widget over-widget" onclick="irAIndicador(IP);">
                    <div class="style-title">
                        <table id="msg-ip" width="100%">
                            <tr><td class="font-title">&Iacute;ndice de productividad por ruta (Cantidad de pasajeros)</td></tr>
                            <tr id="msg-ip-tr" class="opacity0"><td class="form-msg-sm">* Sin datos para visualizar.</td></tr>
                        </table>
                    </div>
                    <div class="canvas-container">                                        
                        <canvas id="chart-ip" class="canvas-container"></canvas>
                        <div id="data-ip"></div>                
                    </div>
                </div>

                <!-- ICR -->
                <div class="minw widget">
                    <div class="style-title">
                        <table id="msg-icr" width="100%">
                            <tr><td class="font-title">&Iacute;ndice de cumplimiento de ruta (%)</td></tr>
                            <tr id="msg-icr-tr" class="opacity0"><td class="form-msg-sm">* Sin datos para visualizar.</td></tr>
                        </table>
                    </div>
                    <div class="canvas-container">                        
                        <div class="float-right">
                            <table>
                                <tr><td>
                                    <select id="sruta-icr" name="sruta-icr" class="select-picker form-control"
                                        data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                        data-width="auto" data-max-options="10" data-container="body"
                                        title="Seleccione ruta(s)" onchange="reiniciaIndicador(ICR);" multiple>
                                        <option value="-1">Ruta no detectada</option>
                                        <c:forEach items="${select.lstruta}" var="ruta">
                                            <option value="${ruta.id}">${ruta.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </td></tr>
                            </table>
                        </div>
                        <canvas id="chart-icr" class="canvas-container over-widget" onclick="irAIndicador(ICR);"></canvas>
                        <div id="data-icr"></div>
                    </div>
                </div>

                <!-- IPH -->
                <div class="minw widget">         
                    <div class="style-title">
                        <table id="msg-iph" width="100%">
                            <tr><td class="font-title">&Iacute;ndice de pasajeros por hora (Cantidad de entradas)</td></tr>
                            <tr id="msg-iph-tr" class="opacity0"><td class="form-msg-sm">* Sin datos para visualizar.</td></tr>
                        </table>                            
                    </div>
                    <div class="canvas-container">                        
                        <div class="float-right">
                            <table>
                                <tr><td>
                                    <select id="sruta-iph" name="sruta-iph" class="select-picker form-control"
                                        data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                        data-width="auto" data-max-options="10" data-container="body"
                                        title="Seleccione ruta(s)" onchange="reiniciaIndicador(IPH);" multiple>
                                        <option value="-1">Ruta no detectada</option>
                                        <c:forEach items="${select.lstruta}" var="ruta">
                                            <option value="${ruta.id}">${ruta.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </td></tr>
                            </table>
                        </div>
                        <canvas id="chart-iph" class="canvas-container over-widget" onclick="irAIndicador(IPH);"></canvas>
                        <div id="data-iph"></div>
                    </div>
                </div>
                
                <!--INDICADOR DE PASAJERO POR CATEGORIA--->
                 <div class="minw widget">
                    <div class="style-title">
                        <table id="msg-iph" width="100%">
                            <tr><td class="font-title">&Iacute;ndice descuento de pasajero por categoria</td></tr>
                            <tr id="msg-idpc" class="opacity0"><td class="form-msg-sm">* Sin datos para visualizar.</td></tr>
                            <tr></tr>
                        </table>
                    </div>
                    <div class="canvas-container">                        
                        <div class="float-right">
                            <table>
                                <tr><td></td></tr>
                            </table>
                        </div>
                        <canvas id="chart-idpc" class="canvas-container over-widget" onclick="irAIndicadorDescuentoPasajeros(IPC);"></canvas>
                        <div id="data-iph"></div>
                    </div>
                </div>
                
                <!-- ICT -->
                <div class="maxw widget over-widget" onclick="irAIndicador(ICT);">
                    <div class="style-title">
                        <table id="msg-ict" class="opacity0">
                            <tr><td class="font-title">&Iacute;ndice de capacidad transportadora</td></tr>
                            <tr><td class="form-msg-sm">* Sin datos para visualizar.</td></tr>
                        </table>
                    </div>
                    <div class="canvas-container centered">
                        <div id="data-ict" class="div-table-style y-scroll top-space"></div>
                        <div id="resumen-ict" class="top-xl-space" style="display: none;">
                            <table class="tbl-style bottom-xl-space">
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

            </div>
            <input type="hidden" id="fecha-ind-gbl" name="fecha-ind-gbl" value="${fechaIndicador}">
            <input type="hidden" id="sruta-icr-gbl" name="sruta-icr-gbl" value="${rutasIcr}">
            <input type="hidden" id="sruta-iph-gbl" name="sruta-iph-gbl" value="${rutasIph}">

        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script>
    $(document).ready(function() {
                
        var fecha = $('#fecha-indicador').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            //todayHighlight: true,
            //todayBtn: 'linked',
            language: 'es'
        }).on('changeDate', function (e) {
            fecha.datepicker('hide');
        }); 
        
        $('#sruta-icr').selectpicker({
            size: 8
        });
        $('#sruta-iph').selectpicker({
            size: 8
        });
        
        // Para seleccionar y resaltar fecha en componente        
        fecha.datepicker('setDate', new Date());
        fecha.datepicker('update');
        
        validaParametrosIndicador();
        iniciaIndicadores();
    });
</script>
<jsp:include page="/include/end.jsp" />