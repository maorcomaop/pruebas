
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PERFILES</h1>    
    <section class="boxed padding">

        <c:choose>
            <c:when test="${permissions.check(10)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(9)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoUsuario.jsp">Registro Usuario</a></li>                    
                        </c:if>
                        <c:if test="${permissions.check(90)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaUsuario.jsp">Listado Usuarios</a></li>
                        </c:if>
                        <c:if test="${permissions.check(12)}">
                        <li role="presentation" ><a href="<c:url value='/nuevoPerfil' />">Registro Perfil</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado Perfiles</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tablePerfil" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Perfil</th>
                                    <th>Descripcion</th>
                                        <c:choose>
                                            <c:when test="${permissions.check(93)}">
                                            <th>Editar</th>
                                            </c:when>                    
                                            <c:when test="${permissions.check(11)}">
                                            <th>Ver</th>
                                            </c:when>                    
                                        </c:choose>
                                    <th>Estado</th>
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstperfil.size() > 0}">
                                        <tr>
                                            <c:forEach items="${select.lstperfil}" var="perfil">    
                                                <td>${perfil.nombre}</td>
                                                <td>${perfil.descripcion}</td>
                                                <c:choose>
                                                    <c:when test ="${perfil.estado == 1}">                                    
                                                        <c:choose>
                                                            <c:when test="${permissions.check(93)}">
                                                                <td>
                                                                    <form action="<c:url value='/editarPerfil' />" method="post">
                                                                        <input type="hidden" name="id" value="${perfil.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-info" value="Editar">
                                                                    </form>            
                                                                </td>                
                                                            </c:when>                    
                                                            <c:when test="${permissions.check(11)}">
                                                                <td>
                                                                    <form action="<c:url value='/verMasPerfil' />" method="post">
                                                                        <input type="hidden" name="id" value="${perfil.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-info" value=" Ver ">
                                                                    </form>            
                                                                </td>
                                                            </c:when>                    
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(94)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarPerfil' />" method="post">
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${perfil.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-danger" value="Desactivar">
                                                                    </form>
                                                                </td>
                                                            </c:when>                    
                                                            <c:otherwise>
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:otherwise>                                        
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(93) || permissions.check(11)}">
                                                                <td><span class="label label-danger">No editable</span></td>           
                                                            </c:when>                    
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(94)}">
                                                                <td>
                                                                    <form action="<c:url value='/eliminarPerfil' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${perfil.id}">
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
            </section>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../../include/footerHome.jsp" />
<script>
    // Tabla dinamica
    $(document).ready(function () {
        $('#tablePerfil').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 500,
            scrollX: true,
            searching: true,
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        }
        );
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
