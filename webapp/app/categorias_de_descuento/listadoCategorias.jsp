
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />


<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CATEGOR&Iacute;AS DE DESCUENTO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(112)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(111)}">
                        <li role="presentation" ><a href="/RDW1/app/categorias_de_descuento/nuevaCategoria.jsp">Registro</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/categorias_de_descuento/listadoCategorias.jsp">Listado de Categor&iacute;as</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tableCategorias"  class="display compact" width="100%">
                            <thead>
                                <tr>                                
                                    <th>Nombre</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Valor</th>
                                    <th>Se liquida</th>                                
                                    <th>Se liquida siempre</th>
                                    <th>Descuenta pasajeros</th>
                                        <c:choose>
                                            <c:when test ="${(login.idempresa == 9) || (login.idempresa == 10)}">
                                            <th>Descuenta del total</th>
                                            </c:when>
                                        </c:choose>
                                    <th>Tipo de descuento</th>
                                        <c:if test="${permissions.check(113)}">
                                        <th>Editar</th>
                                        </c:if>
                                    <th>Estado</th>
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstcategorias.size() > 0}">
                                        <tr>
                                            <c:forEach items="${select.lstcategorias}" var="categorias">    
                                                <td>${categorias.nombre}</td>
                                                <td>${categorias.descripcion}</td>
                                                <td>${categorias.valor}</td>                                    
                                                <td>
                                                    <c:choose>
                                                        <c:when test ="${categorias.aplicaDescuento == 1}">                                    
                                                            <span class="label label-success">Si</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">No</span>
                                                        </c:otherwise>                    
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test ="${categorias.es_fija == 1}">                                    
                                                            <span class="label label-success">Si</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">No</span>
                                                        </c:otherwise>                    
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test ="${categorias.descuenta_pasajeros == 1}">                                    
                                                            <span class="label label-success">Si</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">No</span>
                                                        </c:otherwise>                    
                                                    </c:choose>
                                                </td>
                                                <c:choose>
                                                    <c:when test ="${(login.idempresa == 9) || (login.idempresa == 10)}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test ="${categorias.descuenta_del_total == 1}">                                    
                                                                    <span class="label label-success">Si</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">No</span>
                                                                </c:otherwise>                    
                                                            </c:choose>
                                                        </td>
                                                    </c:when>
                                                </c:choose>
                                                <td>
                                                    <c:choose>
                                                        <c:when test ="${categorias.es_valor_moneda == 1}">                                    
                                                            <span class="label label-success">Dinero</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">Porcentaje</span>
                                                        </c:otherwise>                    
                                                    </c:choose>
                                                </td>                                                                                
                                                <c:choose>
                                                    <c:when test ="${categorias.estado == 1}">
                                                        <c:if test="${permissions.check(113)}">
                                                            <td>
                                                                <form action="<c:url value='/verMasCategoria' />" method="post">
                                                                    <input type="hidden" name="id" value="${categorias.id}">
                                                                    <input type="submit" class="btn btn-xs btn-info" value="Editar">
                                                                </form>            
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(114)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarCategoria' />" method="post">
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${categorias.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-danger eliminar" value="Desactivar">
                                                                    </form>
                                                                </c:when>                    
                                                                <c:otherwise>
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:otherwise>                                        
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(113)}">
                                                            <td><span class="label label-danger">No editable</span></td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(114)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarCategoria' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${categorias.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-success" value="Activar">
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
    $(document).ready(function () {
        $('#tableCategorias').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 500,
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