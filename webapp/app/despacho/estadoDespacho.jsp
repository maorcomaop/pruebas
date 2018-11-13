
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-8 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <section class="boxed padding-smin">
        <c:choose>
            <c:when test="${permissions.check(['Despacho','EstadoDespacho'])}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Nuevo Despacho</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ListadoDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/consultaDespacho.jsp">Despachos</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir Veh&iacute;culo</a></li>
                    </c:if>                        
                        <li role="presentation" class="active"><a href="#">Estado</a></li>
                    <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                        <li><a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                    </c:if>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">                

                        <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                                                                

                        <div class="row">
                            <div class="control-group">
                                <label class="col-sm-4">Tipo de control de despacho</label>
                            </div>
                        </div>
                        
                        <div class="row" style="padding-top: 10px; padding-left: 30px;">
                            <table>
                                <tbody>
                                    <tr>
                                        <td>
                                            <select name="stipoControl" id="stipoControl" class="form-control">
                                                <option value="ctrl-tiempo-planificado">Control por tiempo planificado</option>
                                                <option value="ctrl-intervalo-llegada">Control por intervalo de llegada</option>
                                            </select>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>                                
                        </div>
                        
                        <input type="hidden" id="vtipoControl" name="vtipoControl" value="${entorno.map['tipo_control_despacho']}" />
                        
                        <div class="row" style="padding-top: 10px;">
                            <div class="control-group">
                                <label class="col-sm-4">Rango de tiempo de comprobaci&oacute;n para control despacho (min 0-120)</label>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 10px; padding-left: 30px;">
                            <table>
                                <tbody>
                                    <tr>
                                        <td>
                                            <input type="number" id="tiempoControl" class="form-control" style="width: 128px;"
                                                   value="${entorno.map['rango_tiempo_control_despacho']}"
                                            <c:if test="${!permissions.check(140)}">disabled</c:if>>
                                        </td>
                                        <c:if test="${permissions.check(140)}">
                                            <td style="padding-left: 10px;">
                                                <button class="btn" style="width: 128px;" onclick="dph_guardarTiempoControl();">Guardar</button>
                                            </td>
                                        </c:if>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <hr>

                        <div class="row">
                            <div class="control-group">
                                <label class="col-sm-4">Estado control despacho</label>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 10px; padding-left: 30px;">                    
                            <table>
                                <tbody>
                                    <tr>
                                        <td>
                                            <input class="form-control" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" 
                                                   data-on="Iniciado" data-off="Detenido" data-width="128" name="estadoDespachoInput" id="estadoDespachoInput" 
                                                   type="checkbox">
                                        </td>
                                        <c:if test="${permissions.check(141)}">
                                            <td style="padding-left: 10px;">
                                                <c:choose>                                        
                                                    <c:when test="${estadoDespacho == '1'}">
                                                        <button class="btn" style="width: 128px;" onclick="dph_iniciaControl();">Detener</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn" style="width: 128px;" onclick="dph_iniciaControl();">Iniciar</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:if>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <form id="form-estado-control-despacho" action="<c:url value='/estadoDespacho' />" method="post"></form>
                        <form id="form-inicia-control-despacho" action="<c:url value='/iniciarDespacho' />" method="post"></form>

                        <input type="hidden" id="estadoDespacho" name="estadoDespacho" value="${estadoDespacho}" />                        

                        <hr>

                        <div class="row">
                            <div class="control-group">
                                <a class="col-sm-4" href="/RDW1/app/despacho/verControlDespacho.jsp">Ir a control despacho</a>
                            </div>
                        </div>

                    </div>
                </div>
            </c:when>   
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>
<jsp:include page="/include/footerHome_.jsp" />
<script>
    $(document).ready(function () {

        // Valor por defecto para tiempoControl
        var tiempo_control = $('#tiempoControl').val();
        if (tiempo_control == null || tiempo_control == "") {
            $('#tiempoControl').val(5);
        }
        
        var vtipo_control = $('#vtipoControl').val();
        if (vtipo_control != null && vtipo_control != "") {
            selectElementAUX('stipoControl', vtipo_control);
        }

        var estado = $('#estadoDespacho')[0];
        $('#estadoDespachoInput').bootstrapToggle('enable');

        if (estado == null || estado.value == "") {
            $('#form-estado-control-despacho').submit();
        } else if (estado.value == "1" || estado.value == 1) {
            $('#estadoDespachoInput').bootstrapToggle('on');
            $('#estadoDespachoInput').bootstrapToggle('disable');
        } else {
            $('#estadoDespachoInput').bootstrapToggle('off');
            $('#estadoDespachoInput').bootstrapToggle('disable');
        }

    });
</script>
<jsp:include page="/include/end.jsp" />