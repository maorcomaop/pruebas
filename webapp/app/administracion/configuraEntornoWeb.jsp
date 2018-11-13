<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />
<div class="col-lg-7 centered">  
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ENTORNO</h1>    
    <c:choose>
        <c:when test="${permissions.check(133)}">
            <section class="boxed padding">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(63)}">
                        <li role="presentation"><a href="/RDW1/app/administracion/configuraEntorno.jsp">Entorno Escritorio</a></li>                
                        </c:if>
                    <li role="presentation" class="active"><a href="#">Entorno Web</a></li>    
                    <li role="presentation"><a href="/RDW1/app/administracion/configuraPresentacionWeb.jsp">Configuraci&oacute;n presentaci&oacute;n</a></li>
                </ul>                

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-actualiza-entorno-web" class="form-horizontal" action="<c:url value='/actualizarEntornoWeb' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>

                            <div class="control-group col-md-7">
                                <label class="control-label">* Dominio/Host</label>
                                <div class="controls">                    
                                    <input type="text" id="dominio" name="dominio" value="${entorno.map['dominio_host_servidor']}" class="form-control" maxlength="256" required>
                                    <span class="form-msg-sm">* Especifique dominio web (Ej. www.empresa.com) o host como direcci&oacute;n IP:Puerto (Ej. 192.168.2.100:8084)</span>                                
                                </div>
                                <span id="msg_dom"></span>
                            </div>

                            <div class="control-group col-md-7 top-lt-space">
                                <label class="control-label">* Correo electr&oacute;nico</label>
                                <div class="controls">
                                    <input type="text" id="email_data" name="email_data" value="${entorno.map['correo_corporativo']}" class="form-control" maxlength="50" required>
                                </div>
                                <span id="msg_email_data"></span>
                            </div>

                            <div class="control-group col-md-7 top-lt-space">
                                <c:if test="${permissions.check(134)}">
                                    <label class="control-label">* Contrase&ntilde;a</label>
                                    <div class="controls">
                                        <input type="password" id="pass" name="pass" value="${entorno.map['password_corporativo']}" class="form-control" maxlength="50" required>
                                    </div>
                                    <span id="msg_pass"></span>
                                </c:if>
                            </div>
                                
                            <div class="form-group">                
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 18px; margin-left: 15px;">
                                    <button type="submit" style="display: none;"  class="btn"></button>
                                    <c:if test="${permissions.check(134)}">
                                        <button type="button" style="display: 138px;" class="btn" onclick='sendEntornoWeb();'>Actualizar entorno</button>
                                    </c:if>
                                </div>
                            </div>                
                        </form>
                    </div>
                </div>        
            </section>             
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<jsp:include page="/include/end.jsp" />
