<%-- 
    Document   : listaVehiculosAsegurados
    Created on : 16-jul-2018, 10:54:45
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO PÓLIZAS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(194)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(185)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevaAseguradora.jsp">Agregar Aseguradora</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(186)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoAseguradora.jsp">Aseguradoras</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(207)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevoTipoPoliza.jsp">Tipos De Póliza</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(208)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoTipoPoliza.jsp">Aseguradoras</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(189)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevaPoliza.jsp">Agregar Póliza</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(190)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoPoliza.jsp">Pólizas</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(193)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevoVehiculoAsegurado.jsp">Asegurar Vehículos</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(194)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/polizas/listadoVehiculoAsegurado.jsp">Vehículos Asegurados</a>
                        </li>
                    </c:if>
                </ul>
                <c:choose>      
                    <c:when test ="${idInfo == 1}">                                    
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>
                    <c:when test ="${idInfo == 2}">                                    
                        <div class="alert alert-danger1">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose> 
                <div class="tab-content">
                    <div class="tab-pane padding active" id="pestana1" role="tabpanel">
                        <table id="tableLicenciaTransito" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Nombre Póliza</th>
                                    <th>Número Póliza</th>
                                    <th>Placa Vehículo</th>
                                    <th>Prima Total</th>
                                    <th>Inicio Vigencia</th>
                                    <th>Fin Vigencia</th>
                                    <c:if test="${permissions.check(195)}">
                                        <th>Editar</th>
                                    </c:if>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lst_vehiculosAsegurados.size() > 0}">
                                        <c:forEach items="${select.lst_vehiculosAsegurados}" var="vehiculoAsegurado">
                                            <tr>
                                                <td>${vehiculoAsegurado.nombrePoliza}</td>
                                                <td>${vehiculoAsegurado.numeroPoliza}</td>
                                                <td>${vehiculoAsegurado.placaVehiculo}</td>
                                                <td>${vehiculoAsegurado.valorPrima}</td>
                                                <td>${vehiculoAsegurado.inicioVigencia}</td>
                                                <td>${vehiculoAsegurado.finVigencia}</td>
                                                <c:choose>
                                                    <c:when test="${vehiculoAsegurado.estado == 1}">
                                                        <c:if test="${permissions.check(195)}">
                                                            <td>
                                                                <form action="<c:url value='/verVehiculoAsegurado' />" method="post">
                                                                    <input type="hidden" name="id" value="${vehiculoAsegurado.id}">
                                                                    <input type="submit" class="btn btn-xs btn-info" value="Editar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(196)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoVehiculoAsegurado' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${vehiculoAsegurado.id}">
                                                                        <input type="submit" class="btn btn-xs btn-danger eliminar" value="Desactivar">
                                                                    </form>
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    <span class="label label-success">
                                                                        Activo
                                                                    </span>
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(195)}">
                                                            <td>
                                                                <span class="label label-danger">
                                                                    No Editable
                                                                </span>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(196)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoVehiculoAsegurado' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${vehiculoAsegurado.id}">
                                                                        <input type="submit" class="btn btn-xs btn-success" value="Activar">
                                                                    </form>
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    <span class="label label-danger">
                                                                        Inactivo
                                                                    </span>
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
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
<script src="/RDW1/resources/extern/funciones_validacion_asegurar_vehiculo.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
