<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerNoMenu.jsp" /> <!-- /include/headerHomeNew_.jsp -->

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Intercambio despacho</title>

        <link rel="icon" type="image/png" href="img/favicon.png"/>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
        <meta http-equiv='X-UA-Compatible' content='IE=Edge'>
        <meta name="robots" content="index, follow"/>
        <meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="author" content="">        

        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">
    </head>
    <body>
        <div class="col-lg-7 centered">            
            <c:choose>
                <c:when test="${permissions.check(['Despacho'])}">
                    <c:choose>
                        <c:when test="${despacho == null}">
                            * No existe configuraci&oacute;n de despacho relacionado.
                        </c:when>
                        <c:otherwise>                            

                            <section class="boxed padding-smin">                                
                                <div class="tab-content">                                    
                                    <div role="tabpanel" class="tab-pane padding-smin active" style="padding-left: 20px;">                
                                        
                                        <h3>Intercambio de veh&iacute;culos entre despachos</h3>
                                        <hr/>
                                        
                                        <form id="form-rotar-despacho" action="<c:url value='/intercambiarDespacho' />" method="post">
                                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                                                                

                                            <div class="row">
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Ruta / Despacho</label>
                                                    <div class="col-sm-4">${despacho.nombreRuta} / ${despacho.nombreDespacho}</div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Tipo de d&iacute;a</label>
                                                    <div class="col-sm-4">${despacho.tipoDia}</div>
                                                </div>                    
                                            </div>

                                            <div class="row">
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Hora de inicio</label>
                                                    <div class="col-sm-4">${despacho.horaInicio}</div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Hora fin</label>
                                                    <div class="col-sm-4">${despacho.horaFin}</div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Intervalo despacho valle (min)</label>
                                                    <div class="col-sm-4">${despacho.intervaloValle}</div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Intervalo despacho pico (min)</label>
                                                    <div class="col-sm-4">${despacho.intervaloPico}</div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Nombre despacho</label>
                                                    <div class="col-sm-4">${despacho.nombreDespacho}</div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Rotar veh&iacute;culos</label>
                                                    <div class="col-sm-4">
                                                        <c:choose>
                                                            <c:when test="${despacho.rotarVehiculo == 1}">
                                                                Si
                                                            </c:when>
                                                            <c:otherwise>
                                                                No
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>                    

                                            <hr>

                                            <div class="row">
                                                <div class="control-group">
                                                    <label class="col-sm-2 gray-style">Intercambiar veh&iacute;culos con despacho</label>                        
                                                    <div class="col-sm-4">
                                                        <select id="sidDespacho" name="sidDespacho" class="col-sm-10">
                                                            <option value="">Seleccione un despacho</option>
                                                            <c:forEach items="${select.lstdespacho}" var="dph">
                                                                <option value="${dph.id}">${dph.nombreRuta} - ${dph.nombreDespacho}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <input type="hidden" id="idDespacho" name="idDespacho" value="${despacho.id}" />
                                                    <input type="hidden" id="idRuta" name="idRuta" value="${despacho.idRuta}" />
                                                    <c:if test="${permissions.check(124)}">
                                                        <input type="button" class="btn" onclick="dph_hacerIntercambio();" style="margin-left: 15px;" value="Hacer intercambio" />
                                                    </c:if>
                                                </div>
                                            </div>
                                        </form>
                                    </div>                                    
                                </div>
                            </section>

                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <jsp:include page="/include/accesoDenegado.jsp" />
                </c:otherwise>
            </c:choose>
        </div>
        
<jsp:include page="/include/footerHome_.jsp" />
<jsp:include page="/include/end.jsp" />
