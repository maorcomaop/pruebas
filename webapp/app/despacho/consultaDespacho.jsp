
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-8 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <c:choose>
        <c:when test="${permissions.check(['Despacho','ListadoDespachos'])}">
            <section class="boxed padding-smin">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Nuevo Despacho</a></li>
                    </c:if>
                        <li role="presentation" class="active"><a href="#">Despachos</a></li>
                    <c:if test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir Veh&iacute;culo</a></li>
                    </c:if>                        
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

                        <table id="tablaDespacho" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Nombre despacho</th>
                                    <th>Nombre ruta</th>
                                    <th>Tipo d&iacute;a</th>
                                    <th>Hora de inicio</th>                
                                    <th>Hora fin</th>
                                    <!--Editar Despacho-->
                                    <c:if test="${permissions.check(124)}">
                                        <th>Editar configuraci&oacute;n</th>
                                        </c:if>
                                    <th>Ver configuraci&oacute;n</th>
                                    <th>Ver despacho</th>
                                        <c:if test="${permissions.check(124)}">
                                        <th>Intercambiar veh&iacute;culos</th>
                                        </c:if>
                                        <c:if test="${permissions.check(125)}">
                                        <th>Deshabilitar</th>
                                        </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${select.lstdespacho}" var="despacho">
                                    <tr>
                                        <td>${despacho.nombreDespacho}</td>
                                        <td>${despacho.nombreRuta}</td>
                                        <td>${despacho.tipoDia}</td>
                                        <td>${despacho.horaInicio}</td>
                                        <td>${despacho.horaFin}</td>

                                        <c:if test="${permissions.check(124)}">
                                            <td>    <!-- editar despacho -->
                                                <form class="form-in-table" action="<c:url value='/pre_editarConfDespacho' />" method="post">
                                                    <input type="hidden" id="idDespacho" name="idDespacho" value="${despacho.id}" />
                                                    <button type="submit" class="btn btn-xs" style="width: 80px;">Editar</button>
                                                </form>
                                            </td>
                                        </c:if>
                                        <td>    <!-- ver conf despacho -->
                                            <button type="submit" class="btn btn-success btn-xs" 
                                                    onclick="verDespacho('/RDW1/app/despacho/verConfiguracionDespacho.jsp', '/RDW1/verConfDespacho', ${despacho.id});" 
                                                    style="width: 80px;">Ver m&aacute;s</button>                                        
                                        </td>
                                        <td>    <!-- generar despacho -->                          
                                            <button type="submit" class="btn btn-success btn-xs"
                                                    onclick="verDespacho('/RDW1/app/despacho/verPlanillaDespacho.jsp', '/RDW1/verDespacho', ${despacho.id});"
                                                    style="width: 120px;">Ver despacho ruta</button>                                    
                                        </td>
                                        <c:if test="${permissions.check(124)}">
                                            <td>    <!-- consulta conf despacho -->
                                                <button type="submit" class="btn btn-success btn-info btn-xs" 
                                                        onclick="verDespacho('/RDW1/app/despacho/intercambioDespacho.jsp', '/RDW1/pre_intercambiarDespacho', ${despacho.id});"
                                                        style="width: 120px;">Editar intercambio</button>                                        
                                            </td>
                                        </c:if>
                                        <c:if test="${permissions.check(125)}">
                                            <td>    <!-- elimina conf despacho -->
                                                <form id="form-delete-despacho-${despacho.id}" class="form-in-table" action="<c:url value='/eliminarDespacho' />" method="post">
                                                    <input type="hidden" id="idDespacho" name="idDespacho" value="${despacho.id}">
                                                    <button type="button" class="btn btn-danger btn-xs" onclick="confirmar_eliminacion('${despacho.id}', '${despacho.nombreDespacho}');" 
                                                            style="width: 80px;">Eliminar</button>
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

    // Tabla dinamica de despachos
    $('document').ready(function () {
        $('#tablaDespacho').DataTable({
            "aLengthMenu": [5, 7, 10, 25],
            "responsive": true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
    });

    function confirmar_eliminacion(id, objeto) {

        var sform = "#form-delete-despacho-" + id;
        var msg = "¿Est&aacute; seguro de eliminar configuraci&oacute;n de despacho <strong>" + objeto + "</strong>?";

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