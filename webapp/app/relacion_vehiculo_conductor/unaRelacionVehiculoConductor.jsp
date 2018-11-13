
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<jsp:include page="/include/headerJS.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ASOCIACI&Oacute;N CONDUCTOR - VEH&Iacute;CULO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(135) || permissions.check(5)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(68)}">
                        <li role="presentation"><a href="/RDW1/app/relacion_vehiculo_conductor/nuevoRelacionVehiculoConductor.jsp">Registro</a></li>
                        </c:if>    
                        <c:if test="${permissions.check(69)}">
                        <li role="presentation"><a href="/RDW1/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp">Listado</a></li>                    
                        </c:if>                    
                        <c:if test="${permissions.check(135) || permissions.check(5)}">
                        <li role="presentation" class="active"><a href="#">Modificar Veh&iacute;culo-Conductor</a></li>                    
                        </c:if>
                </ul>          
                <section>         
                    <c:choose>
                        <c:when test ="${idInfo == 0}">                                    
                            <div class="alert alert-info">
                                <button type="button" class="close fade" data-dismiss="alert">&times;</button>
                                <strong>Informaci&oacute;n </strong>${msg}.
                            </div>
                        </c:when>        
                        <c:when test ="${idInfo == 1}">                                    
                            <div class="alert alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Informaci&oacute;n </strong>${msg}.
                            </div>
                        </c:when>
                        <c:when test ="${idInfo == 2}">                                    
                            <div class="alert alert-error">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Informaci&oacute;n </strong>${msg}.
                            </div>
                        </c:when>       
                    </c:choose>  
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active" id="pestana1">         
                            <!--<form class="form-horizontal" action="<c:url value='/editarRelacionVehiculoConductor' />" method="post">-->
                            <jsp:include page="/include/relacionVehiculoConductorEdit.jsp" /> 
                            <!--</form>-->

                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <jsp:include page="/include/accesoDenegado.jsp" />
                </c:otherwise>
            </c:choose>
        </section>
</div>      
<%--<jsp:include page="/include/footerHome.jsp" />--%>
<footer>
    <center>
        <div style="width: 200px; height: 250px;"><img src="/RDW1/resources/img/logoregistel.png"></div>
    </center>
</footer>

<jsp:include page="/include/end.jsp" />
