<%-- 
    Document   : notificaciones
    Created on : 24-jul-2018, 16:39:36
    Author     : Richard Mejia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">NOTIFICACIONES</h1>
    <section class="boxed padding">
        <div class="tab-content">
            <div class="tab-pane padding active" id="pestana1" role="tabpanel">
                <table id="tableMantenimientos" class="display" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Notificación</th>
                            <th>Mantenimiento</th>
                            <th>Fecha</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${notificaciones.size() > 0}">
                                <c:forEach items="${notificaciones}" var="notificacion">
                                    <tr>
                                        <td>${notificacion.idNotificacion}</td>
                                        <td>${notificacion.mensaje}</td>
                                        <td>${notificacion.nombreMantenimiento}</td>
                                        <td>${notificacion.fechaCadena}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </section>  
</div>
<jsp:include page="/include/footerHome.jsp" />
<script src="/RDW1/resources/extern/funciones_validacion_configurar_mantenimiento.js" charset="UTF-8"></script>
<jsp:include page="/include/end.jsp" />
