
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO VEH&Iacute;CULOS</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(29)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(30)}">
                        <li role="presentation" ><a href="/RDW1/app/movil/nuevoMovil.jsp">Registro</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/movil/listadoMovil.jsp">Listado Veh&iacute;culos</a></li>                    
                    <li role="presentation"><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Grupos de Veh&iacute;culos</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tableMovil" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>                                
                                    <th>Empresa</th>
                                    <th>Placa</th>
                                    <th>N&uacute;mero Interno</th>
                                    <th>Id GPS</th>
                                    <th>N&uacute;mero celular</th>                                     
                                        <c:if test="${permissions.check(103)}">
                                        <th>Editar</th>
                                        </c:if>
                                    <th>Estado</th>
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstmovil.size() > 0}">
                                        <tr>
                                            <c:forEach items="${select.lstmovil}" var="movil">    
                                                <td>${movil.nombreEmpresa}</td>                                
                                                <td>${movil.placa}</td>
                                                <td>${movil.numeroInterno}</td>                                    
                                                <td>${movil.fkGps}</td>
                                                <td>${movil.numero_celular}</td>                                    
                                                <c:choose>
                                                    <c:when test ="${movil.estado == 1}"> 
                                                        <c:if test="${permissions.check(103)}">
                                                            <td>
                                                                <form action="<c:url value='/verMasMovil' />" method="post">
                                                                    <input type="hidden" name="id" value="${movil.id}">
                                                                    <input type="submit" class="btn  btn-xs btn-info" value="Editar">
                                                                </form> 
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(104)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarMovil' />" method="post">
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${movil.id}">
                                                                        <input type="submit" class="btn btn-xs  btn-danger eliminar" value="Desactivar">
                                                                    </form>
                                                                </td>
                                                            </c:when>                    
                                                            <c:otherwise>
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:otherwise>                                        
                                                        </c:choose>
                                                    </c:when>                                            
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(103)}">
                                                            <td><span class="label label-danger">No editable</span></td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(104)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarMovil' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${movil.id}">
                                                                        <input type="submit" class="btn btn-xs  btn-success" value="Activar">
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
        $('#tableMovil').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 500,
            scrollX: true,
            searching: true,
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