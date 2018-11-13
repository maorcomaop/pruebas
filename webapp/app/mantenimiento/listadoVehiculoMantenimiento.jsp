<%-- 
    Document   : listadoVehiculoMantenimiento
    Created on : 03-ago-2018, 10:40:03
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO MANTENIMIENTO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(203)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(202)}">
                        <li role="presentation">
                            <a href="/RDW1/app/mantenimiento/nuevoMantenimiento.jsp">Configurar Mantenimiento</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(203)}">
                        <li role="presentation">
                            <a href="/RDW1/app/mantenimiento/listadoMantenimiento.jsp">Mantenimientos</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(211)}">
                        <li role="presentation">
                            <a href="/RDW1/app/mantenimiento/nuevoVehiculoMantenimiento.jsp">Asociar Vehículos</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(212)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/mantenimiento/listadoVehiculoMantenimiento.jsp">Vehículos Asociados</a>
                        </li>
                    </c:if>
                </ul>
                <c:choose>      
                    <c:when test ="${idInfo == 1}">                                    
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Información </strong>${msg}.
                        </div>
                    </c:when>
                    <c:when test ="${idInfo == 2}">                                    
                        <div class="alert alert-danger1">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Información </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose> 
                <div class="tab-content">
                    <div class="tab-pane padding active" id="pestana1" role="tabpanel">
                        <table id="tableMantenimientos" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Mantenimiento</th>
                                    <th>Vehículo</th>
                                    <th>Valor Inicial Mantenimiento</th>
                                    <c:if test="${permissions.check(213)}">
                                        <th>Editar</th>
                                    </c:if>
                                    <th>Estado</th>
                                    <c:if test="${permissions.check(215)}">
                                        <th>Eliminar</th>
                                    </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lst_vehiculosMantenimiento.size() > 0}">
                                        <c:forEach items="${select.lst_vehiculosMantenimiento}" var="vehiculoMantenimiento">
                                            <tr>
                                                <td>${vehiculoMantenimiento.nombreMantenimiento}</td>
                                                <td>${vehiculoMantenimiento.placa}</td>
                                                <td>${vehiculoMantenimiento.valorInicialMantenimiento} </td>
                                                <c:choose>
                                                    <c:when test="${vehiculoMantenimiento.estado == 1}">
                                                        <c:if test="${1==1}">
                                                            <td>
                                                                <form action="<c:url value='/verVehiculoMantenimiento' />" method="post">
                                                                    <input type="hidden" name="id" value="${vehiculoMantenimiento.id}">
                                                                    <input type="submit" class="btn btn-xs btn-info" value="Editar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(214)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoVehiculoMantenimiento' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${vehiculoMantenimiento.id}">
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
                                                        <c:choose>
                                                            <c:when test="${permissions.check(215)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarVehiculoMantenimiento' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${vehiculoMantenimiento.id}">
                                                                        <input type="submit" class="btn btn-xs btn-danger eliminar" value="Eliminar">
                                                                    </form>
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    <span class="label label-success">
                                                                        -
                                                                    </span>
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(213)}">
                                                            <td>
                                                                <span class="label label-danger">
                                                                    No Editable
                                                                </span>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(214)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoVehiculoMantenimiento' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${vehiculoMantenimiento.id}">
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
                                                        <c:choose>
                                                            <c:when test="${permissions.check(215)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarVehiculoMantenimiento' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${vehiculoMantenimiento.id}">
                                                                        <input type="submit" class="btn btn-xs btn-danger eliminar" value="Eliminar">
                                                                    </form>
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    <span class="label label-success">
                                                                        -
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
<script src="/RDW1/resources/extern/funciones_validacion_configurar_mantenimiento.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
