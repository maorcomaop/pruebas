<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ALARMAS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(20)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(19)}">
                        <li role="presentation" ><a href="/RDW1/app/alarma/nuevoAlarma.jsp">Registro</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/alarma/listadoAlarma.jsp">Listado de Alarmas</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tableAlarma1" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>C&oacute;digo</th>                                
                                    <th>Nombre</th>
                                    <th>Tipo</th>
                                    <th>Unidad de Medici&oacute;n</th>                                
                                        <c:if test="${permissions.check(96)}">
                                        <th>Editar</th>       
                                        </c:if>
                                    <th>Estado</th>                                
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstalarma.size() > 0}">
                                        <tr>
                                            <c:forEach items="${select.lstalarma}" var="alarma">    
                                                <td>${alarma.codigoAlarma}</td>
                                                <td>${alarma.nombreAlarma}</td>
                                                <td>${alarma.tipoAlarma}</td>
                                                <td>${alarma.unidadDeMedicion}</td>                                    
                                                <c:choose>
                                                    <c:when test ="${alarma.estado == 1}">
                                                        <c:if test="${permissions.check(96)}">
                                                            <td>
                                                                <form action="<c:url value='/verMasAlarma' />" method="post">
                                                                    <input type="hidden" name="id" value="${alarma.id}">
                                                                    <input type="submit" class="btn btn-success btn-xs btn-info" value="Editar">
                                                                </form> 
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(97)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarAlarma' />" method="post">
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${alarma.id}">
                                                                        <input type="submit" class="btn btn-xs btn-danger eliminar" value="Desactivar">
                                                                    </form>
                                                                </td>
                                                            </c:when>                    
                                                            <c:otherwise>
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:otherwise>                                        
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(96)}">
                                                            <td><span class="label label-danger">No editable</span></td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(97)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarAlarma' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${alarma.id}">
                                                                        <input type="submit" class="btn btn-success btn-xs" value="Activar">
                                                                    </form>
                                                                </td>
                                                            </c:when>                    
                                                            <c:otherwise>
                                                                <td><span class="label label-danger">Inactivo</span></td>
                                                            </c:otherwise>                                        
                                                        </c:choose>
                                                    </c:otherwise>                    
                                                </c:choose>
                                            </tr>
                                        </c:forEach>        
                                    </c:when>
                                </c:choose>
                            </tbody>    
                        </table>
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
    // Tabla dinamica
    $(document).ready(function () {
        $('#tableAlarma1').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            hing: false,
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
    });

    /*Controla el acceso al boton eliminar del listado*/
    $(function () {
        $(".eliminar").click(function () {
            var mensaje = confirm("¿Desea Eliminar el registro?");
            if (mensaje) {
                console.log("¡Gracias por aceptar!");
                return true;
            } else {
                return false;
            }
        });
    });
</script>
<jsp:include page="/include/end.jsp" />