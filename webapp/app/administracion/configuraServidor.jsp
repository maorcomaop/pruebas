
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CONFIGURACI&Oacute;N SERVIDOR WIFI</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(58)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Configuración Servidor</a></li>        
                        <c:if test="${permissions.check(119)}">
                        <li role="presentation"><a href="/RDW1/app/administracion/configuraServidorPerimetro.jsp">Configuración Servidor Per&iacute;metro</a></li>        
                        </c:if>                    
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                            
                        <form class="form-horizontal" action="<c:url value='/actualizarConfServidor' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <div class="control-group col-md-7">
                                <label class="control-label">N&uacute;mero de conexiones</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="numcon" id="numcon" value="${prop.numeroMaximoConexionesServidor}" 
                                           min="1" max="99" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Puerto</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="puerto" id="puerto" value="${prop.puertoServidor}" 
                                           min="1024" max="65535" required>                           
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Tiempo activo por conexi&oacute;n</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="tiempoActivo" id="tiempoActivo" value="${prop.tiempoActivoServidor}" 
                                           min="1" max="99999" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Tiempo de retraso por conexi&oacute;n</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="tiempoRetraso" id="tiempoRetraso" value="${prop.tiempoRetrasoServidor}" 
                                           min="1" max="99999" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Base de respuesta confirmaci&oacute;n</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="brc" id="brc" value="${prop.baseConfirmacion}" 
                                           min="1" max="99" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Intentos de actualizaci&oacute;n de puntos</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="iap" id="iap" value="${prop.intentosActualizacionPuntos}" 
                                           min="1" max="99" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <c:if test="${permissions.check(118)}">
                                        <button type="submit" class="btn">Actualizar configuraci&oacute;n</button>
                                    </c:if>
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
<jsp:include page="/include/end.jsp" />
