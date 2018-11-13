
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<!-- Body container -->
<div class="col-lg-8 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO TARIFAS</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(48)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Nueva Tarifa</a></li>
                        <c:if test="${permissions.check(108)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaTarifa.jsp">Listado Tarifas</a></li>                
                        </c:if>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-nueva-tarifa" class="form-horizontal" action="<c:url value='/guardarTarifa' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <!-- <div><p>{lnk}</p></div> -->
                            <div class="control-group col-md-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" maxlength="20" title="Digite el nombre" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Valor</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="valor" id="valor" title="Digite el valor" maxlength="10" required>
                                    <span id="msg_valor"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <button style="display: none;" type="submit" class="btn"></button>
                                    <button style="width: 126px;" type="button" class="btn" title="Haga clic aqui para guardar" onclick="sendTarifa('ins');">Guardar</button>
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
