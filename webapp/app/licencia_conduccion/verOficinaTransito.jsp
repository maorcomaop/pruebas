<%-- 
    Document   : verOficinaTransito
    Created on : 11-jul-2018, 12:03:45
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO LICENCIAS DE CONDUCCIÓN</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(178)}">
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
                        <li role="presentation">
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
                    <li role="presentation" class="active">
                        <a href="/RDW1/app/licencia_conduccion/listadoOficinaTransito.jsp">Oficina Tránsito: ${oficinaTransito.nombre}</a>
                    </li>
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
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <p>
                            Los campos con asterisco (*) son obligatorios.
                        </p>
                        <form class="form-horizontal" action="<c:url value='/editarOficinaTransito' />" method="post">
                            <div class="control-group">
                                <div class="control-group">                                        
                                    <div class="controls">
                                        <input type="hidden" name="id_edit" value="${oficinaTransito.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="nombre" class="control-label">Nombre Oficina Tránsito *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="nombre" id="nombre" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite el nombre de la oficina o secretaría de tránsito."
                                               value="${oficinaTransito.nombre}"
                                               maxlength="250">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="telefono" class="control-label">Teléfono Oficina Tránsito</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="telefono" id="telefono" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite el número de teléfono de la oficina o secretaría de tránsito."
                                               value="${oficinaTransito.telefono}"
                                               maxlength="50">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="correo" class="control-label">Correo Electrónico Oficina Tránsito</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="email" name="correo" id="correo" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite la dirección de correo electrónico de la oficina o secretaría de tránsito."
                                               value="${oficinaTransito.correo}"
                                               maxlength="50">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="direccion" class="control-label">Dirección Oficina Tránsito</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="direccion" id="direccion"
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite la dirección de la oficina o secretaría de tránsito."
                                               value="${oficinaTransito.direccion}"
                                               maxlength="250">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="ciudad">Ciudad *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="ciudad" id="ciudad" required>
                                            <option value="-1">Seleccione una ciudad</option>
                                            <c:forEach items="${select.lstciudad}" var="ciudad">
                                                <option value="${ciudad.id}" 
                                                        <c:if test="${ciudad.id eq oficinaTransito.fk_ciudad}">selected</c:if>
                                                        >${ciudad.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary" data-toggle="tooltip" id="btnGuardar"
                                           title="Haga clic aquí para guardar." value="Guardar">
                                </div>
                            </div> 
                        </form>
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
