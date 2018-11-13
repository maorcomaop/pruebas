<%-- 
    Document   : nuevoVehiculoMantenimiento
    Created on : 03-ago-2018, 10:39:08
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO MANTENIMIENTO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(202)}">
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
                        <li role="presentation" class="active">
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
                        <form class="form-horizontal formulario" action="<c:url value='/guardarVehiculoMantenimiento' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="fkMantenimiento">Mantenimiento *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="fkMantenimiento" 
                                                id="fkMantenimiento" required>
                                            <option value="-1">Seleccione un mantenimiento</option>
                                            <c:forEach items="${select.lst_mantenimiento}" var="mantenimiento">
                                                <c:if test="${mantenimiento.estado eq 1}">
                                                    <option value="${mantenimiento.id}">${mantenimiento.nombre}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="fkVehiculo">Vehículo *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="fkVehiculo" 
                                                id="fkVehiculo" required>
                                            <option value="-1">Seleccione un vehículo</option>
                                            <c:forEach items="${select.lstmovil}" var="vehiculo">
                                                <c:if test="${vehiculo.estado eq 1}">
                                                    <option value="${vehiculo.id}">${vehiculo.placa}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="valorInicialMantenimiento" class="control-label" id="lblUnidadMedida">
                                            Valor Actual Mantenimiento </label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="number" name="valorInicialMantenimiento" id="valorInicialMantenimiento" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Cantidad de unidades con las cuales se inicia el mantenimiento a configurar."
                                               min="0" max="9999999" >
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
                    <div class="tab-pane padding active" id="pestana1" role="tabpanel">
                        <table id="tableVehiculosMantenimiento" class="display" cellspacing="0" width="100%">
                            <caption>Vehículos Asociados Al Mantenimiento</caption>
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
<script src="/RDW1/resources/extern/funciones_validacion_vehiculo_mantenimiento.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
