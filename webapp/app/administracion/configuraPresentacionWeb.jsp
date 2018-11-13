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
                    <li role="presentation"><a href="/RDW1/app/administracion/configuraEntornoWeb.jsp">Entorno Web</a></li>
                    <li role="presentation" class="active"><a href="#">Configuraci&oacute;n presentaci&oacute;n</a></li>
                </ul>                

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-actualiza-presentacion" class="form-horizontal" action="<c:url value='/actualizarPresentacionWeb' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            
                            <div class="control-group col-md-7">
                                <label>> Rastreo - M&oacute;viles</label>
                                <hr style="margin-top: 0px; margin-bottom: 4px;">
                            </div>
                            
                            <div class="control-group col-md-7">
                                <label class="control-label normal-label">Ajustar hora de servidor en <input id="input_ajuste_hs" type="number" min="-24" max="24">&nbsp;hora(s)</label><br>                                
                                <label class="control-label normal-label"><input id="chk_mostrar_es" type="checkbox">&nbsp;Mostrar entradas y salidas</label><br>
                                <label class="control-label normal-label"><input id="chk_mostrar_no" type="checkbox">&nbsp;Mostrar nivel de ocupaci&oacute;n</label><br>
                                <label class="control-label normal-label"><input id="chk_mostrar_noic" type="checkbox">&nbsp;Mostrar nivel de ocupaci&oacute;n vs indice de capacidad utilizada</label><br>
                                <label class="control-label normal-label"><input id="chk_ajuste_pto" type="checkbox">&nbsp;Ajustar punto de control</label><br>

                                <input type="hidden" id="ajuste_hs" name="ajuste_hs" value="${entorno.map['ajuste_hora_servidor']}">                                
                                <input type="hidden" id="mostrar_es" name="mostrar_es" value="${entorno.map['mostrar_entrada_salida']}">
                                <input type="hidden" id="mostrar_no" name="mostrar_no" value="${entorno.map['mostrar_nivel_ocupacion']}">
                                <input type="hidden" id="mostrar_noic" name="mostrar_noic" value="${entorno.map['mostrar_noic']}">       
                                <input type="hidden" id="ajuste_pto" name="ajuste_pto" value="${entorno.map['ajuste_pto_control']}">
                            </div>
                                
                            <div class="form-group">                
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 18px; margin-left: 15px;">
                                    <button type="submit" style="display: none;"  class="btn"></button>
                                    <c:if test="${permissions.check(134)}">
                                        <button type="button" style="display: 138px;" class="btn" onclick="sendPresentacionWeb();">Actualizar configuraci&oacute;n</button>
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
<script>
    $(document).ready(function() {
        cargarPresentacionWeb();
    });
</script>
<jsp:include page="/include/end.jsp" />
