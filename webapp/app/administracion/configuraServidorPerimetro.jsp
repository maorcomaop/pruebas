
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PER&Iacute;METRO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(119)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(58)}">
                        <li role="presentation"><a href="/RDW1/app/administracion/configuraServidor.jsp">Configuraci&oacute;n servidor</a></li>        
                        </c:if>
                    <li role="presentation" class="active"><a href="#">Configuraci&oacute;n servidor per&iacute;metro</a></li>        
                </ul>    

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                            
                        <form class="form-horizontal" action="<c:url value='/actualizarConfServidorPerimetro' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <div class="control-group col-md-7">
                                <label class="control-label">N&uacute;mero de conexiones</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="numcon" id="numcon" value="${prop.numeroMaximoConexionesServidorPerimetro}" 
                                           min="1" max="99" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Puerto</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="puerto" id="puerto" value="${prop.puertoServidorPerimetro}" 
                                           min="1024" max="65535" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Tiempo activo por conexi&oacute;n</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="tiempoActivo" id="tiempoActivo" value="${prop.tiempoActivoServidorPerimetro}" 
                                           min="1" max="99999" required>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Tiempo de retraso por conexi&oacute;n</label>
                                <div class="controls">                    
                                    <input type="number" class="form-control" name="tiempoRetraso" id="tiempoRetraso" value="${prop.tiempoRetrasoServidorPerimetro}" 
                                           min="1" max="99999" required>
                                </div>
                            </div>
                            <div class="form-group">                
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <c:if test="${permissions.check(120)}">
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
