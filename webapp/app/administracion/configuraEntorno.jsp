<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<!--
    <div class="col-md-7 top-space bottom-space">
        <a href="#"><strong>> Entorno</strong></a>
    </div>-->

<div class="col-lg-7 centered">  
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ENTORNO</h1>
    <c:choose>
        <c:when test="${permissions.check(63)}">
            <section class="boxed padding">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Entorno Escritorio</a></li>
                    <c:if test="${permissions.check(133)}">
                        <li role="presentation"><a href="/RDW1/app/administracion/configuraEntornoWeb.jsp">Entorno Web</a></li>
                        <li role="presentation"><a href="/RDW1/app/administracion/configuraPresentacionWeb.jsp">Configuraci&oacute;n presentaci&oacute;n</a></li>
                    </c:if>
                </ul>                

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-actualiza-entorno" class="form-horizontal" action="<c:url value='/actualizarEntorno' />" method="post">
                            <table>
                                <tr>
                                    <td><div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div></td>
                                </tr>
                                <tr>
                                    <td>
                                        <label class="control-label">* Directorio raíz</label>
                                        <div class="controls">
                                            <input type="text" style="width:100%" class="form-control" 
                                                   id="dirRaiz" name="dirRaiz" value="${entorno.map['directorio_raiz_rdto']}" maxlength="50">
                                            <span class="form-msg-sm">* Especifique aqu&iacute; el nombre del directorio de regisdata escritorio.</span>                                    
                                        </div>
                                        <span id="msg_dirRaiz"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="col-md-7" style="padding-top: 10px;">                    
                                            <button style="display:none;" type="submit" class="btn"></button>
                                            <c:if test="${permissions.check(121)}">
                                                <button style="width: 138px;" type="button" class="btn" onclick='sendEntorno();'>Actualizar entorno</button>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>

                        <hr>

                        <form class="form-horizontal" action="<c:url value='/lanzarEntorno' />" method="post">
                            <table>
                                <tr>
                                    <td colspan="2"><label class="control-label">Estado del entorno escritorio</label></td>                            
                                </tr>
                                <tr style="height: 4px;"></tr>
                                <tr>
                                    <td>
                                        <div class="col-md-7">
                                            <div class="controls">
                                                <input class="form-control" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" 
                                                       data-on="Encendido" data-off="Apagado" data-width="112" name="estadoEntornoComp" id="estadoEntornoComp" 
                                                       type="checkbox">                                        
                                                <input type="hidden" name="estadoEntorno" id="estadoEntorno" value="${estadoEntorno}">
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div name="blkLanzar" id="blkLanzar">
                                            <c:if test="${permissions.check(121)}">
                                                <button class="btn" style="width: 128px;" id="btnLanzar" name="btnLanzar" onclick="lanzarEntorno();">Lanzar entorno</button>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>

                        <form id="form-verifica-entorno" name="form-verifica-entorno" action="<c:url value='/verificarEntorno' />" method="post"></form>
                        <form id="form-lanza-entorno" name="form-lanza-entorno" action="<c:url value='/lanzarEntorno' />" method="post"></form>
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

    window.onload = function () {
        var estado = document.getElementById("estadoEntorno").value;
        var btnLanzar = document.getElementById("btnLanzar");

        if (estado == null || estado == "") {
            document.getElementById("form-verifica-entorno").submit();
        } else if (estado == "1" || estado == 1) {
            $('#estadoEntornoComp').bootstrapToggle('on');
            $('#estadoEntornoComp').bootstrapToggle('disable');
            btnLanzar.disabled = true;
        } else {
            $('#estadoEntornoComp').bootstrapToggle('enable');
            $('#estadoEntornoComp').bootstrapToggle('off');
            $('#estadoEntornoComp').bootstrapToggle('disable');
            btnLanzar.disabled = false;
        }
    }

</script>    
<jsp:include page="/include/end.jsp" />
