<%-- 
    Document   : listadoConfiguracionesLiquidacion
    Created on : 01-oct-2018, 14:51:10
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">PARÁMETROS DE CONFIGURACIÓN</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(232)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(231)}">
                        <li role="presentation">
                            <a href="/RDW1/app/liquidacion/nuevaConfiguracionLiquidacion.jsp">Nueva Configuración</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(232)}">
                        <li role="presentation"  class="active">
                            <a href="/RDW1/app/liquidacion/listadoConfiguracionesLiquidacion.jsp">Liquidaciones Configuradas</a>
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
                                    <th>Nombre Configuración</th>
                                    <th>Modo de Liquidación</th>
                                    <th>Perfil</th>
                                    <c:if test="${permissions.check(227)}">
                                        <th>Editar</th>
                                    </c:if>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lst_configuracionesDeLiquidacion.size() > 0}">
                                        <c:forEach items="${select.lst_configuracionesDeLiquidacion}" var="configuracion">
                                            <tr>
                                                <td>${configuracion.nombreConfiguracionLiquidacion}</td>
                                                <c:choose>
                                                    <c:when test="${configuracion.liquidacionNormal}">
                                                        <td>Liquidación Normal</td>
                                                    </c:when>
                                                    <c:when test="${configuracion.liquidacionPorRuta}">
                                                        <td>Liquidación Por Ruta</td>
                                                    </c:when>
                                                    <c:when test="${configuracion.liquidacionPorTramo}">
                                                        <td>Liquidación Por Tramo</td>
                                                    </c:when>
                                                    <c:when test="${configuracion.liquidacionPorTiempo}">
                                                        <td>Liquidación Por Tiempo</td>
                                                    </c:when>
                                                    <c:when test="${configuracion.liquidacionSoloPasajeros}">
                                                        <td>Liquidación Sólo Pasajeros</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>No se definió el tipo de liquidación</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>${configuracion.nombrePerfil}</td>
                                                <c:choose>
                                                    <c:when test="${configuracion.estado == 1}">
                                                        <c:if test="${permissions.check(233)}">
                                                            <td>
                                                                <form action="<c:url value='/verConfiguracionLiquidacion' />" method="post">
                                                                    <input type="hidden" name="id" value="${configuracion.id}">
                                                                    <input type="submit" class="btn btn-xs btn-info" value="Editar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(234)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoConfiguracionLiquidacion' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${configuracion.id}">
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
                                                        <c:if test="${permissions.check(233)}">
                                                            <td>
                                                                <span class="label label-danger">
                                                                    No Editable
                                                                </span>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(234)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoConfiguracionLiquidacion' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${configuracion.id}">
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
<script src="/RDW1/resources/extern/configuracionLiquidacion.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />

