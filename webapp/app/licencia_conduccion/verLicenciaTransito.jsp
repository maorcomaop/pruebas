<%-- 
    Document   : verLicenciaTransito
    Created on : 12-jul-2018, 8:36:37
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO LICENCIAS DE CONDUCCIÓN</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(182)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(172)}">
                        <li role="presentation">
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
                        <a href="/RDW1/app/licencia_conduccion/listadoLicenciaTransito.jsp">Licencia Tránsito: 
                            ${licenciaTransito.numeroLicencia}</a>
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
                        <form class="form-horizontal" action="<c:url value='/editarLicenciaTransito' />" method="post">
                            <div class="control-group">
                                <div class="control-group">                                        
                                    <div class="controls">
                                        <input type="hidden" name="id_edit" value="${licenciaTransito.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="conductor">Conductor *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="conductor" id="conductor" required>
                                            <option value="-1">Seleccione un conductor</option>
                                            <c:forEach items="${select.lstconductor}" var="conductor">
                                                <c:if test="${conductor.estado eq 1}">
                                                    <option value="${conductor.id}" 
                                                            <c:if test="${conductor.id eq licenciaTransito.fk_conductor}">selected</c:if>>
                                                        ${conductor.nombre} ${conductor.apellido} - ${conductor.cedula}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="numeroLicencia" class="control-label">Número Licencia Tránsito *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="numeroLicencia" id="numeroLicencia" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite el número de la licencia de tránsito."
                                               value="${licenciaTransito.numeroLicencia}"
                                               maxlength="50">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="categoria">Categoría *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="categoria" id="categoria" required>
                                            <option value="-1">Seleccione una categoría</option>
                                            <c:forEach items="${select.lst_categoriasLicencia}" var="categoria">
                                                <c:if test="${categoria.estado eq 1}">
                                                    <option value="${categoria.id}" 
                                                            <c:if test="${categoria.id eq licenciaTransito.fk_categoria}">selected</c:if>>
                                                        ${categoria.nombre}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="oficinaTransito">Oficina Tránsito *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="oficinaTransito" id="oficinaTransito" required>
                                            <option value="-1">Seleccione una oficina</option>
                                            <c:forEach items="${select.lst_oficinasTransito}" var="oficina">
                                                <c:if test="${oficina.estado eq 1}">
                                                    <option value="${oficina.id}" <c:if test="${oficina.id eq licenciaTransito.fk_oficina_transito}">selected</c:if>>
                                                        ${oficina.nombre}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="fechaExpedicion" class="control-label">Fecha Expedición *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="date" name="fechaExpedicion" id="fechaExpedicion" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Seleccione la fecha de expedición de la licencia de tránsito."
                                               required value="${licenciaTransito.fechaExpedicionString}">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="vigencia" class="control-label">Vigencia *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="date" name="vigencia" id="vigencia" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Seleccione la fecha de vigencia de la licencia de tránsito."
                                               required value="${licenciaTransito.vigenciaString}">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="observaciones" class="control-label">Observaciones</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <textarea name="observaciones" id="observaciones" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite las observaciones acerca de la licencia de tránsito."
                                               style="resize: none;" rows="5"
                                               maxlength="2000"><c:out value="${licenciaTransito.observaciones}" /></textarea>
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
<script src="/RDW1/resources/extern/funciones_validacion_licencia_transito.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />