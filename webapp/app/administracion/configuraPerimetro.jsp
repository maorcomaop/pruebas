
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />


<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CONFIGURACI&Oacute;N PER&Iacute;METRO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Perimetro','ConfiguracionPerimetro'])}">
                <ul class="nav nav-tabs">
                    <li role="presentacion" class="active"><a href="#">Configuraci&oacute;n per&iacute;metro</a></li>
                    <c:if test="${permissions.check(['Perimetro','VerVehiculos'])}">
                    <li role="presentacion"><a href="/RDW1/app/administracion/visualizaPerimetro.jsp">Visualizaci&oacute;n de per&iacute;metro</a></li>
                    </c:if>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">
                        <form action="<c:url value='/actualizarPerimetro' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <div style="overflow-x:auto;">
                                <table id="tablaPerimetro" class="td-space">
                                    <tbody>
                                        <tr>
                                            <td>Descarte inicial (mm)</td>  
                                            <td><input class="input-my-small" id="descarteInicial" name="descarteInicial" placeholder="1-59"
                                                       value="${perim.descarteInicial}" type="number" min="1" max="59" required></td>
                                            <td>Descarte final (mm)</td>    
                                            <td><input class="input-my-small" id="descarteFinal" name="descarteFinal" placeholder="0-59"
                                                       value="${perim.descarteFinal}" type="number" min="0" max="59" required></td>
                                        </tr>
                                        <tr>
                                            <td>Descarte holgura (mm)</td>  
                                            <td><input class="input-my-small" id="descarteHolgura" name="descarteHolgura" placeholder="1-59"
                                                       value="${perim.descarteHolgura}" type="number" min="1" max="59" required></td>
                                            <td>Comprobaci&oacute;n (mm:ss)</td>   
                                            <td>
                                                <input class="input-my-gsmall" id="compMM" name="compMM" placeholder="1-59"
                                                       value="${perim.compMin}" type="number" min="1" max="59" required>
                                                &nbsp;:&nbsp;
                                                <input class="input-my-gsmall" id="compSS" name="compSS" placeholder="0-59"
                                                       value="${perim.compSeg}" type="number" min="0" max="59" required>
                                            </td>
                                        </tr>                            
                                        <tr>
                                            <td colspan="3"></td>
                                            <c:if test="${permissions.check(['Perimetro','ConfiguracionPerimetro','EditarConfiguracionPerimetro'])}">
                                                <td><button style="width:148px;" type="submit" class="btn">Aceptar</button></td>
                                            </c:if>
                                        </tr>
                                    </tbody>
                                </table>
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
