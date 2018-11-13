<%-- 
    Document   : nuevaConfiguracionLiquidacion
    Created on : 01-oct-2018, 14:50:27
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">PARÁMETROS DE CONFIGURACIÓN</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(231)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(231)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/liquidacion/nuevaConfiguracionLiquidacion.jsp">Nueva Configuración</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(232)}">
                        <li role="presentation">
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
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <p>
                            Los campos con asterisco (*) son obligatorios.
                        </p>
                        <form class="form-horizontal formulario" action="<c:url value='/guardarConfiguracionLiquidacion' />" method="post">
                            <div class="control-group">
                                <fieldset>
                                    <legend>Nueva Configuración de Liquidación</legend>
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <label for="nombreConfiguracionLiquidacion" class="control-label" >Nombre Configuración *</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <input type="text" name="nombreConfiguracionLiquidacion" id="nombreConfiguracionLiquidacion" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un nombre que permita identificar a la configuración de la liquidación." 
                                                   maxlength="40" required>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="control-group">
                                <fieldset>
                                    <legend>Etiquetas Editables Formulario</legend>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label for="etqPasajeros1" class="control-label" >ETQ_PASAJEROS 1</label>
                                            <input type="text" name="etqPasajeros1" id="etqPasajeros1" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 1." 
                                                   maxlength="40">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="etqPasajeros2" class="control-label" >ETQ_PASAJEROS 2</label>
                                            <input type="text" name="etqPasajeros2" id="etqPasajeros2" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 2." 
                                                   maxlength="40">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="etqPasajeros3" class="control-label" >ETQ_PASAJEROS 3</label>
                                            <input type="text" name="etqPasajeros3" id="etqPasajeros3" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 3." 
                                                   maxlength="40">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label for="etqPasajeros4" class="control-label" >ETQ_PASAJEROS 4</label>
                                            <input type="text" name="etqPasajeros4" id="etqPasajeros4" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 4." 
                                                   maxlength="40">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="etqPasajeros5" class="control-label" >ETQ_PASAJEROS 5</label>
                                            <input type="text" name="etqPasajeros5" id="etqPasajeros5" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 5." 
                                                   maxlength="40">
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="divider"><hr></div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="etqTotal1" class="control-label" >ETQ_TOTAL 1</label>
                                        <input type="text" name="etqTotal1" id="etqTotal1" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 1." 
                                               maxlength="40">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqTotal2" class="control-label" >ETQ_TOTAL 2</label>
                                        <input type="text" name="etqTotal2" id="etqTotal2" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 2." 
                                               maxlength="40">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqTotal3" class="control-label" >ETQ_TOTAL 3</label>
                                        <input type="text" name="etqTotal3" id="etqTotal3" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 3." 
                                               maxlength="40">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="etqTotal4" class="control-label" >ETQ_TOTAL 4</label>
                                        <input type="text" name="etqTotal4" id="etqTotal4" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 4." 
                                               maxlength="40">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqTotal5" class="control-label" >ETQ_TOTAL 5</label>
                                        <input type="text" name="etqTotal5" id="etqTotal5" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 5." 
                                               maxlength="40">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqTotal6" class="control-label" >ETQ_TOTAL 6</label>
                                        <input type="text" name="etqTotal6" id="etqTotal6" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 6." 
                                               maxlength="40">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="etqTotal7" class="control-label" >ETQ_TOTAL 7</label>
                                        <input type="text" name="etqTotal7" id="etqTotal7" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 7." 
                                               maxlength="40">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqTotal8" class="control-label" >ETQ_TOTAL 8</label>
                                        <input type="text" name="etqTotal8" id="etqTotal8" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 8." 
                                               maxlength="40">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <fieldset>
                                    <legend>Etiquetas Editables Reportes</legend>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label for="etqRepPasajeros1" class="control-label" >ETQ_PASAJEROS 1 *</label>
                                            <input type="text" name="etqRepPasajeros1" id="etqRepPasajeros1" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 1." 
                                                   maxlength="40" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="etqRepPasajeros2" class="control-label" >ETQ_PASAJEROS 2 *</label>
                                            <input type="text" name="etqRepPasajeros2" id="etqRepPasajeros2" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 2." 
                                                   maxlength="40" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="etqRepPasajeros3" class="control-label" >ETQ_PASAJEROS 3 *</label>
                                            <input type="text" name="etqRepPasajeros3" id="etqRepPasajeros3" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 3." 
                                                   maxlength="40" required>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label for="etqRepPasajeros4" class="control-label" >ETQ_PASAJEROS 4 *</label>
                                            <input type="text" name="etqRepPasajeros4" id="etqRepPasajeros4" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 4." 
                                                   maxlength="40" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="etqRepPasajeros5" class="control-label" >ETQ_PASAJEROS 5 *</label>
                                            <input type="text" name="etqRepPasajeros5" id="etqRepPasajeros5" 
                                                   class="form-control" data-toggle="tooltip" 
                                                   title="Ingrese un valor para ETQ_PASAJEROS 5." 
                                                   maxlength="40" required>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="divider"><hr></div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="etqRepTotal1" class="control-label" >ETQ_TOTAL 1 *</label>
                                        <input type="text" name="etqRepTotal1" id="etqRepTotal1" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 1." 
                                               maxlength="40" required>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqRepTotal2" class="control-label" >ETQ_TOTAL 2 *</label>
                                        <input type="text" name="etqRepTotal2" id="etqRepTotal2" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 2." 
                                               maxlength="40" required>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqRepTotal3" class="control-label" >ETQ_TOTAL 3 *</label>
                                        <input type="text" name="etqRepTotal3" id="etqRepTotal3" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 3." 
                                               maxlength="40" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="etqRepTotal4" class="control-label" >ETQ_TOTAL 4 *</label>
                                        <input type="text" name="etqRepTotal4" id="etqRepTotal4" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 4." 
                                               maxlength="40" required>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqRepTotal5" class="control-label" >ETQ_TOTAL 5 *</label>
                                        <input type="text" name="etqRepTotal5" id="etqRepTotal5" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 5." 
                                               maxlength="40" required>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqRepTotal6" class="control-label" >ETQ_TOTAL 6 *</label>
                                        <input type="text" name="etqRepTotal6" id="etqRepTotal6" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 6." 
                                               maxlength="40" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="etqRepTotal7" class="control-label" >ETQ_TOTAL 7 *</label>
                                        <input type="text" name="etqRepTotal7" id="etqRepTotal7" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 7." 
                                               maxlength="40" required>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="etqRepTotal8" class="control-label" >ETQ_TOTAL 8 *</label>
                                        <input type="text" name="etqRepTotal8" id="etqRepTotal8" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Ingrese un valor para ETQ_TOTAL 8." 
                                               maxlength="40" required>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <fieldset>
                                    <legend id="leyendaModoLiquidacion">
                                        Modo de Liquidación
                                    </legend>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <input type="radio" name="modoLiquidacion" id="liquidacionNormal" value="1" checked="checked">
                                            <label for="liquidacionNormal" data-toggle="tooltip" 
                                                   title="Seleccione si la liquidación es normal.">Normal</label>

                                            <input type="radio" name="modoLiquidacion" id="liquidacionPorRuta" value="2"> 
                                            <label for="liquidacionPorRuta" data-toggle="tooltip" 
                                                   title="Seleccione si la liquidación es por ruta.">Por Ruta</label>

                                            <input type="radio" name="modoLiquidacion" id="liquidacionPorTramo" value="3"> 
                                            <label for="liquidacionPorTramo" data-toggle="tooltip" 
                                                   title="Seleccione si la liquidación es por tramo.">Por Tramo</label>

                                            <input type="radio" name="modoLiquidacion" id="liquidacionPorTiempo" value="4">
                                            <label for="liquidacionPorTiempo" data-toggle="tooltip" 
                                                   title="Seleccione si la liquidación es tiempo.">Por Tiempo</label>

                                            <input type="radio" name="modoLiquidacion" id="liquidacionSoloPasajeros" value="5"> 
                                            <label for="liquidacionSoloPasajeros" data-toggle="tooltip" id="labelSoloPasajeros"
                                                   title="Seleccione si la liquidación es sólo por pasajeros.">Sólo Pasajeros</label>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <label for="perfil" class="control-label" >Aplicar a *</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <select class="selectpicker form-control" data-style="btn-primary" name="perfil" 
                                                        id="perfil" required>
                                                    <option value="-1">Seleccione un perfil</option>
                                                    <c:if test="${permissions.check(-1)}">
                                                        <option value="1">Super Usuario</option>
                                                    </c:if>
                                                    <c:forEach items="${select.lstperfil}" var="perfil">
                                                        <c:if test="${perfil.estado eq 1}">
                                                            <option value="${perfil.id}">${perfil.nombre}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <label for="perfil" class="control-label" >Estado *</label>
                                        </div>
                                        <div class="col-sm-12">
                                            <input type="radio" name="estado" id="activo" value="1" checked="checked">
                                            <label for="activo" data-toggle="tooltip" 
                                                   title="Seleccione si la configuración se crea en estado activo.">Activo</label>

                                            <input type="radio" name="estado" id="inactivo" value="0"> 
                                            <label for="inactivo" data-toggle="tooltip" 
                                                   title="Seleccione si la configuración se crea en estado inactivo.">Inactivo</label>
                                        </div>
                                    </div>
                                </fieldset>
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
<script src="/RDW1/resources/extern/configuracionLiquidacion.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />