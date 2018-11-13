<%-- 
    Document   : listadoOficinaTransito
    Created on : 11-jul-2018, 12:04:00
    Author     : Richard Mejia
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO LICENCIAS DE CONDUCCIÓN</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(177)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(172)}">
                        <li role="presentation" >
                            <a href="/RDW1/app/licencia_conduccion/nuevaCategoriaLicencia.jsp">Nueva Categoría</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(173)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/listadoCategoriaLicencia.jsp">Lista Categorías</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(176)}">
                        <li role="presentation" >
                            <a href="/RDW1/app/licencia_conduccion/nuevaOficinaTransito.jsp">Nueva Oficina</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(177)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/licencia_conduccion/listadoOficinaTransito.jsp">Lista Oficinas</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(180)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/nuevaLicenciaTransito.jsp">Nueva Licencia</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(181)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/listadoLicenciaTransito.jsp">Lista Licencias</a>
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
                        <table id="tableOficinasTransito" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Teléfono</th>
                                    <th>Correo Electrónico</th>
                                    <th>Dirección</th>
                                    <th>Ciudad</th>
                                        <c:if test="${permissions.check(178)}">
                                        <th>Editar</th>
                                        </c:if>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lst_oficinasTransito.size() > 0}">
                                        <c:forEach items="${select.lst_oficinasTransito}" var="oficinaTransito">
                                            <tr>
                                                <td>${oficinaTransito.nombre}</td>
                                                <td>${oficinaTransito.telefono}</td>
                                                <td>${oficinaTransito.correo}</td>
                                                <td>${oficinaTransito.direccion}</td>
                                                <td>${oficinaTransito.nombreCiudad}</td>
                                                <c:choose>
                                                    <c:when test="${oficinaTransito.estado == 1}">
                                                        <c:if test="${permissions.check(178)}">
                                                            <td>
                                                                <form action="<c:url value='/verOficinaTransito' />" method="post">
                                                                    <input type="hidden" name="id" value="${oficinaTransito.id}">
                                                                    <input type="submit" class="btn btn-xs btn-info" value="Editar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(179)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoOficinaTransito' />" method="post" >
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${oficinaTransito.id}">
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
                                                        <c:if test="${permissions.check(178)}">
                                                            <td>
                                                                <span class="label label-danger">
                                                                    No Editable
                                                                </span>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(179)}">
                                                                <td>
                                                                    <form action="<c:url value='/cambiarEstadoOficinaTransito' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${oficinaTransito.id}">
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
<script src="/RDW1/resources/extern/funciones_validacion_oficina_transito.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
