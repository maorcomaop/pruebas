
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PER&Iacute;METRO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Perimetro','VerVehiculos'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Visualizaci&oacute;n de per&iacute;metro</a></li>
                        <c:if test="${permissions.check(['Perimetro','ConfiguracionPerimetro'])}">
                        <li role="presentacion"><a href="/RDW1/app/administracion/configuraPerimetro.jsp">Configuraci&oacute;n per&iacute;metro</a></li>    
                        </c:if>
                </ul>   
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <form id="form-visualiza-perimetro" action="<c:url value='/consultarVP' />" method="post"></form>              
                        <table id="tablaVehiculosPerimetro" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Placa</th>
                                    <th>N° interno</th>
                                    <th>Ruta</th>
                                    <th>Estado</th>
                                    <th>Hora de salida</th>
                                    <th>Hora de llegada</th>                                
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${lstvp.size() > 0}">
                                        <c:forEach items="${lstvp}" var="vp">
                                            <tr>
                                                <td>${vp.placa}</td>
                                                <td>${vp.numeroInterno}</td>
                                                <td>${vp.rutaAsignada}</td>
                                                <td>${vp.nombreEstado}</td>
                                                <td>${vp.horaSalida}</td>
                                                <td>${vp.horaLlegada}</td>                                            
                                            </tr>
                                        </c:forEach>         
                                    </c:when>

                                </c:choose>

                            </tbody>
                        </table>
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
<script>
    // Tabla vehiculos perimetro
    $(document).ready(function () {
        $('#tablaVehiculosPerimetro').DataTable({
            "aLengthMenu": [10, 15, 20, 100],
            scrollY: 400,
            scrollX: true,
            bInfo: false,
            paging: false,
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        consultaVehiculosPerimetro();
    });
</script>
<jsp:include page="/include/end.jsp" />
