
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<!--
<div class="col-md-9 top-space bottom-space">
    <a href="#"><strong>> Rutas</strong></a>
</div>-->

<!-- Body container -->
<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO RUTAS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(128)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(50)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(126)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignación Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(127)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp">Edición Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(115)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado Rutas</a></li>
                        </c:if>
                        <c:if test="${permissions.check(128)}">
                        <li role="presentacion" class="active"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(52)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos</a>
                        </c:if>
                </ul>                
                <c:if test="${permissions.check(131)}">
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active">                    

                            <form id="form-longitud-ruta" class="form-horizontal" action="<c:url value='/actualizarLongitudRuta' />" method="post">
                                <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                           

                                <div class="control-group col-md-7">
                                    <label class="control-label">Actualizar longitud de ruta</label>
                                </div>
                                <div class="control-group col-md-7">
                                    <label class="normal-label" style="padding-top: 6px;">
                                        <input type="checkbox" id="mesAnterior" name="mesAnterior">&nbsp;Usar medidas de mes anterior</label>
                                    <input type="hidden" id="vmesAnterior" name="vmesAnterior">
                                </div>
                                <div class="form-group">                                    
                                    <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">                                    
                                        <button style="width: 126px;" type="button" class="btn" onclick="sendLongitudRuta();">Actualizar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div style="height: 20px;"></div>                
                </c:if>
                <div class="boxed padding">
                    <table id="tablaRuta" class="display" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>Empresa</th>
                                <th>Nombre</th> 
                                <th>Longitud (Mts)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${select.lstruta}" var="ruta">
                                <tr>
                                    <td>${ruta.empresa}</td>
                                    <td>${ruta.nombre}</td>
                                    <td>${ruta.distanciaMetros}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />
<script>
    // Tabla dinamica de rutas
    $(document).ready(function () {
        $('#tablaRuta').DataTable({
            "aLengthMenu": [20, 30, 50],
            responsive: true,
            scrollY: 500,
            searching: true,
            bAutoWidth: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
    });
</script>
<jsp:include page="/include/end.jsp" />
