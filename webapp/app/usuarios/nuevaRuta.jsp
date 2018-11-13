
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<!-- Body container -->
<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO RUTAS</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(50)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(50)}">
                        <li role="presentation" class="active"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(126)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignación Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(127)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp">Edición Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(115)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado Rutas</a></li>
                        </c:if>
                        <c:if test="${permissions.check(128)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(52)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos</a>
                        </c:if>
                </ul>                

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-nueva-ruta" class="form-horizontal" action="<c:url value='/guardarRuta' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <!-- <div><p>{lnk}</p></div> -->
                            <div class="control-group col-md-7">
                                <label class="control-label">* Empresa</label>
                                <div class="controls">
                                    <select id="sempresa" name="sempresa" class="form-control">
                                        <option></option>
                                        <c:forEach items="${select.lstempresa}" var="empresa">
                                            <option value="${empresa.id}">${empresa.nombre}</option>
                                        </c:forEach>                                    
                                    </select>
                                    <span id="msg_sempresa"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" maxlength="50" title="Digite el nombre" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="form-group">                            
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <button style="display: none;" type="submit" class="btn"></button>
                                    <button style="width:126px;" type="button" class="btn" onclick="sendRuta('ins');" title="Haga clic aqui para guardar">Registrar</button>
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
