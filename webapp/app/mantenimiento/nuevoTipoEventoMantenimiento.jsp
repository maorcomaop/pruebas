<%-- 
    Document   : nuevoTipoEventoMantenimiento
    Created on : 17-jul-2018, 15:07:18
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO MANTENIMIENTO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(198)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(198)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/mantenimiento/nuevoTipoEventoMantenimiento.jsp">Nuevo Tipo Evento</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(199)}">
                        <li role="presentation">
                            <a href="/RDW1/app/mantenimiento/listadoTipoEventoMantenimiento.jsp">Tipos Eventos</a>
                        </li>
                    </c:if>
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
                        <li role="presentation">
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
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <p>
                            Los campos con asterisco (*) son obligatorios.
                        </p>
                        <form class="form-horizontal" action="<c:url value='/guardarTipoEventoMantenimiento' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="nombre" class="control-label">Nombre *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="nombre" id="nombre" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite el nombre del tipo de evento de mantenimiento."
                                               maxlength="250">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="unidadMedida" class="control-label">Unidad De Medida *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="unidadMedida" id="unidadMedida" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite la unidad de medida del tipo de evento. Por ejemplo: kilometros recorridos, horas trabajadas, etc."
                                               maxlength="50">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="descripcion" class="control-label">Descripción </label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <textarea name="descripcion" id="descripcion" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite la descripción del tipo de evento del mantenimiento."
                                               style="resize: none;" rows="5" maxlength="2000"></textarea>
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
<script src="/RDW1/resources/extern/funciones_validacion_tipo_evento_mantenimiento.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
