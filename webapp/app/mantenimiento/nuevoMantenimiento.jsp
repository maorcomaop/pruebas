<%-- 
    Document   : nuevoMantenimiento
    Created on : 18-jul-2018, 8:56:14
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
                        <li role="presentation" class="active">
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
                        <form class="form-horizontal formulario" action="<c:url value='/guardarMantenimiento' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="nombre" class="control-label" >Nombre Mantenimiento *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="nombre" id="nombre" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un nombre que permita identificar al mantenimiento." 
                                               maxlength="250">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" for="tipoEventoMantenimiento">Tipo Evento Mantenimiento *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="tipoEventoMantenimiento" 
                                                id="tipoEventoMantenimiento" required>
                                            <option value="-1">Seleccione un tipo de evento</option>
                                            <c:forEach items="${select.lst_tipoEventoMantenimiento}" var="tipoEventoMantenimiento">
                                                <c:if test="${tipoEventoMantenimiento.estado eq 1}">
                                                    <option value="${tipoEventoMantenimiento.id}">${tipoEventoMantenimiento.nombre}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="fechaActivacion" class="control-label">Fecha Activación Mantenimiento *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="date" name="fechaActivacion" id="fechaActivacion" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Seleccione la fecha inicial para las notificaciones. Por defecto se toma la fecha actual."
                                               required>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="unidades" class="control-label" id="lblUnidadMedida">Valor Inicial Notificación *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="number" name="unidades" id="unidades" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Cantidad de unidades con las cuales se generan las notificaciones del mantenimiento."
                                               min="0" max="9999999" >
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" id="lblRepeticion">Cantidad de Notificaciones *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="radio" name="repeticion" id="estatico"
                                               data-toggle="tooltip" 
                                               title="Seleccione si el evento es estático o se repetirá indefinidamente."
                                               value="0">
                                        <label for="estatico">Una Vez</label>
                                        <input type="radio" name="repeticion" id="ciclico"
                                               data-toggle="tooltip" 
                                               title="Seleccione si el evento es estático o se repetirá indefinidamente."
                                               value="1"> 
                                        <label for="ciclico">Varias Veces</label>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="intervalo" class="control-label" >Intervalo Repetición (Minutos) *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="number" name="intervalo" id="intervalo" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese cada cuanto se deben repetir las notificaciones. El tiempo se establece en minutos."
                                               min="1" max="525600" >
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="cantidadNotificaciones" class="control-label" >Cantidad Repeticiones Notificación *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="number" name="cantidadNotificaciones" id="cantidadNotificaciones" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese la cantidad de notificaciones que desea recibir para los mantenimientos cíclicos."
                                               min="1" max="10" >
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="control-label" id="lblActivarMantenimiento">Activar Mantenimiento</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="radio" name="activarMantenimiento" id="activar"
                                               data-toggle="tooltip" 
                                               title="Seleccione si el mantenimiento se activa inmediatamente después de guardado."
                                               value="1">
                                        <label for="activar">Activar</label>
                                        <input type="radio" name="activarMantenimiento" id="noActivar"
                                               data-toggle="tooltip" 
                                               title="Seleccione si el mantenimiento se activa inmediatamente después de guardado."
                                               value="0"> 
                                        <label for="noActivar">No Activar</label>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="correos" class="control-label">Notificar a los Siguientes Correos Electrónicos</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <textarea name="correos" id="correos" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese las direcciones de correo electrónico de las personas que recibirán las notificaciones. Las direcciones de correo electrónico se separan por comas sin dejar espacios."
                                               style="resize: none;" rows="5" maxlength="2000"></textarea>
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
                                               title="Puede ingresar las observaciones que desee agregar a la notificación."
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
<script src="/RDW1/resources/extern/funciones_validacion_configurar_mantenimiento.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />

