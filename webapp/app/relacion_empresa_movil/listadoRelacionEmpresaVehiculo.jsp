
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">   
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ASOCIACI&Oacute;N EMPRESA - VEH&Iacute;CULO</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(26)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(27)}">
                        <li role="presentation" ><a href="/RDW1/app/relacion_empresa_movil/nuevoRelacionEmpresaVehiculo.jsp">Registro</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp">Listado Empresa - Veh&iacute;culo</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tableRelacionVehiculoEmpresa" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>                                
                                    <th>Placa</th>
                                    <th>Empresa</th>                                
                                    <th>Estado</th>
                                        <c:if test="${permissions.check(101)}">
                                        <th>Editar</th>
                                        </c:if>
                                        <c:if test="${permissions.check(102)}">
                                        <th>Deshabilitar</th>
                                        </c:if>
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstrelacionvehiculoempresa.size() > 0}">
                                        <tr>
                                            <c:forEach items="${select.lstrelacionvehiculoempresa}" var="relacionVehiculoEmpresa">    

                                                <td>${relacionVehiculoEmpresa.matriculaVehiculo}</td>
                                                <td>${relacionVehiculoEmpresa.razonSocial}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test ="${relacionVehiculoEmpresa.estado == 1}">                                    
                                                            <span class="label label-success">Activo</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">Inactivo</span>
                                                        </c:otherwise>                    
                                                    </c:choose>
                                                </td>                                 


                                                <c:choose>
                                                    <c:when test ="${relacionVehiculoEmpresa.estado == 1}">
                                                        <c:if test="${permissions.check(101)}">
                                                            <td>
                                                                <form action="<c:url value='/verMasRelacionEmpresaVehiculo' />" method="post">
                                                                    <input type="hidden" name="id" value="${relacionVehiculoEmpresa.idRelacionVehiculoEmpresa}">
                                                                    <input type="submit" class="btn  btn-xs  btn-info" value="Editar">
                                                                </form>            
                                                            </td>
                                                        </c:if>
                                                        <c:if test="${permissions.check(102)}">
                                                            <td>
                                                                <form action="<c:url value='/eliminarRelacionEmpresaVehiculo' />" method="post">
                                                                    <input type="hidden" name="estado" value="0">
                                                                    <input type="hidden" name="id" value="${relacionVehiculoEmpresa.idRelacionVehiculoEmpresa}">
                                                                    <input type="submit" class="btn  btn-xs btn-danger eliminar" value="Desactivar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(101)}">
                                                            <td><span class="label label-danger">No editable</span></td>
                                                        </c:if>
                                                        <c:if test="${permissions.check(102)}">
                                                            <td>
                                                                <form action="<c:url value='/eliminarRelacionEmpresaVehiculo' />" method="post">
                                                                    <input type="hidden" name="estado" value="1">
                                                                    <input type="hidden" name="id" value="${relacionVehiculoEmpresa.idRelacionVehiculoEmpresa}">
                                                                    <input type="submit" class="btn  btn-xs btn-success" value="Activar">
                                                                </form>
                                                            </td>
                                                        </c:if>
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
        $('#tableRelacionVehiculoEmpresa').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 500,
            scrollX: true,
            searching: true,
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        //$(".alert").alert('close')
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
