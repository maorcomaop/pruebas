<%-- 
    Document   : verPoliza
    Created on : 13-jul-2018, 15:30:57
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO PÓLIZAS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(191)}">
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
                            <a href="/RDW1/app/polizas/nuevoTipoPoliza.jsp">Agregar Tipo Póliza</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(208)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoTipoPoliza.jsp">Tipos De Póliza</a>
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
                            <a href="/RDW1/app/polizas/nuevoVehiculoAsegurado.jsp">Asegurar Vehículo</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(194)}">
                        <li role="presentation">
                            <a href="/RDW1/app/polizas/listadoVehiculoAsegurado.jsp">Vehículos Asegurados</a>
                        </li>
                    </c:if>
                    <li role="presentation" class="active">
                        <a href="#">Póliza: ${poliza.nombre}</a> 
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
                        <form class="form-horizontal" action="<c:url value='/editarPoliza' />" method="post">
                            <div class="control-group">
                                <div class="control-group">                                        
                                    <div class="controls">
                                        <input type="hidden" name="id_edit" value="${poliza.id}">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="nombre" class="control-label">Nombre Póliza *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="nombre" id="nombre" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite el nombre de la póliza."
                                               maxlength="250"
                                               value="${poliza.nombre}">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="aseguradora">Aseguradora *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="aseguradora" id="aseguradora" required>
                                            <option value="-1">Seleccione una aseguradora</option>
                                            <c:forEach items="${select.lst_aseguradoras}" var="aseguradora">
                                                <c:if test="${aseguradora.estado eq 1}">
                                                    <option value="${aseguradora.id}"
                                                            <c:if test="${aseguradora.id eq poliza.fkAseguradora}">selected</c:if>>
                                                        ${aseguradora.nombre}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="tipoPoliza">Tipo De Póliza *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="tipoPoliza" 
                                                id="tipoPoliza" required>
                                            <option value="-1">Seleccione un tipo de póliza</option>
                                            <c:forEach items="${select.lst_tipoPoliza}" var="tipoPoliza">
                                                <c:if test="${tipoPoliza.estado eq 1}">
                                                    <option value="${tipoPoliza.id}" <c:if test="${tipoPoliza.id eq poliza.fkTipoPoliza}">selected</c:if>
                                                            >${tipoPoliza.nombre}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="amparosCoberturas" class="control-label">Amparos Y Coberturas</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <textarea name="amparosCoberturas" id="amparosCoberturas" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite los amparos, coberturas y deducibles de la póliza."
                                                rows="5" maxlength="4000"><c:out value="${poliza.amparosCoberturas}" /></textarea>
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
<script src="/RDW1/resources/extern/funciones_validacion_poliza.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
