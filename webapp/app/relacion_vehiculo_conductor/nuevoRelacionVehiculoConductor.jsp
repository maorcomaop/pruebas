
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ASOCIACI&Oacute;N CONDUCTOR - VEH&Iacute;CULO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(68)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(68)}">
                        <li role="presentation" class="active"><a href="/RDW1/app/relacion_vehiculo_conductor/nuevoRelacionVehiculoConductor.jsp">Registro</a></li>
                        </c:if>    
                        <c:if test="${permissions.check(69)}">
                        <li role="presentation"><a href="/RDW1/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp">Listado</a></li>                    
                        </c:if>                    
                </ul>
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
                        <form class="form-horizontal" action="<c:url value='/guardarRelacionVehiculoConductor' />" method="post">                            
                            <div class="control-group">
                                <label class="control-label" for="inputName">Veh&iacute;culo</label>
                                <div class="controls">
                                    <select class="selectpicker " name="vehiculo"  id="car">
                                        <option value="0">Seleccione un veh&iacute;culo</option>
                                        <c:forEach items="${select.lstmovil}" var="movil">
                                            <option value="${movil.id}">${movil.placa}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label" for="inputName">Conductor</label>
                                <div class="controls">
                                    <select class="selectpicker" name="conductor" id="driver">
                                        <option value="0">Seleccione un conductor</option>
                                        <c:forEach items="${select.lstconductor}" var="conductor">
                                            <option value="${conductor.id}">${conductor.apellido} ${conductor.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                            
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonRelacionCV" value="Guardar">
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
<script>

    $(document).ready(function () {
        $('.selectpicker').selectpicker({
            style: 'btn-primary',
            size: 4,
            liveSearch: true
        });

        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
    });


</script>

<jsp:include page="/include/end.jsp" />
