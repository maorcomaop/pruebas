<%-- 
    Document   : listadoAseguradora
    Created on : 13-jul-2018, 12:25:18
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">M�DULO P�LIZAS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(186)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(185)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevaAseguradora.jsp">Agregar Aseguradora</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(186)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/polizas/listadoAseguradora.jsp">Aseguradoras</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(207)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevoTipoPoliza.jsp">Agregar Tipo P�liza</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(208)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoTipoPoliza.jsp">Tipos De P�liza</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(189)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevaPoliza.jsp">Agregar P�liza</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(190)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoPoliza.jsp">P�lizas</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(193)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/nuevoVehiculoAsegurado.jsp">Asegurar Veh�culo</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(194)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoVehiculoAsegurado.jsp">Veh�culos Asegurados</a>
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
                        <table id="tableAseguradoras" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Tel�fono</th>
                                    <th>P�gina Web</th>
                                    <c:if test="${permissions.check(187)}">
                                        <th>Editar</th>
                                    </c:if>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lst_aseguradoras.size() > 0}">
                                        <c:forEach items="${select.lst_aseguradoras}" var="aseguradora">
                                            <tr>
                                                <td>${aseguradora.nombre}</td>
                                                <td>${aseguradora.telefono}</td>
                                                <td><a href="${aseguradora.paginaWebBack}" target="_blank">${aseguradora.paginaWeb}</a></td>
                                                <c:choose>
                                                    <c:when test="${aseguradora.estado == 1}">
                                                        <c:if test="${permissions.check(187)}">
                                                            <td>
                                                                <form action="<c:url value='/verAseguradora' />" method="post">
                                                                    <input type="hidden" name="id" value="${aseguradora.id}">
                                                                    <input type="submit" class="btn btn-xs btn-info" value="Editar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(188)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoAseguradora' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${aseguradora.id}">
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
                                                        <c:if test="${permissions.check(187)}">
                                                            <td>
                                                                <span class="label label-danger">
                                                                    No Editable
                                                                </span>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(188)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoAseguradora' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${aseguradora.id}">
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
<script src="/RDW1/resources/extern/funciones_validacion_aseguradora.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
