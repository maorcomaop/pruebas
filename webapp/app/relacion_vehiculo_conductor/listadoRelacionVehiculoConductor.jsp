
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ASOCIACI&Oacute;N CONDUCTOR - VEH&Iacute;CULO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(69)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(68)}">
                        <li role="presentation" ><a href="/RDW1/app/relacion_vehiculo_conductor/nuevoRelacionVehiculoConductor.jsp">Registro</a></li>
                        </c:if>    
                        <c:if test="${permissions.check(69)}">
                        <li role="presentation" class="active"><a href="/RDW1/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp">Listado</a></li>                    
                        </c:if>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tableRelacion" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>                                
                                    <th>Placa</th>
                                    <th>Numero Interno</th>
                                    <th>Nombre del Conductor Asociado</th>                                
                                    <th>Estado</th>
                                        <c:if test="${permissions.check(135)}">
                                        <th>Editar</th>
                                        </c:if>
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstrelacionvehiculoempresa.size() > 0}">
                                        <tr>
                                            <c:forEach items="${select.lstrelacionvehiculoconductor}" var="relacionVehiculoConductor">    

                                                <td>${relacionVehiculoConductor.matriculaVehiculo}</td>
                                                <td>${relacionVehiculoConductor.numeroInterno}</td>
                                                <td>${relacionVehiculoConductor.nombreConductor}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test ="${relacionVehiculoConductor.activo == 1}">                                    
                                                            <span class="label label-success">Activo</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">Inactivo</span>
                                                        </c:otherwise>                    
                                                    </c:choose>
                                                </td>                                   
                                                <c:if test="${permissions.check(135)}">
                                                    <td>
                                                        <form action="<c:url value='/verMasRelacionVehiculoConductor' />" method="post">
                                                            <input type="hidden" name="id" value="${relacionVehiculoConductor.idRelacionVehiculoConductor}">
                                                            <input type="hidden" name="vehiculoId" value="${relacionVehiculoConductor.idVehiculo}">
                                                            <input type="submit" class="btn  btn-xs btn-info" value="Editar">
                                                        </form>            
                                                    </td>
                                                </c:if>
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
    // Tabla dinamica
    $(document).ready(function () {
        $('#tableRelacion').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 500,
            scrollX: true,
            searching: true,
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        //$(".alert").alert('close')
    });

</script>
<jsp:include page="/include/end.jsp" />
