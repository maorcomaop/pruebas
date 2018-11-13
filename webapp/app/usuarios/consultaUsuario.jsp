
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--<div class="col-md-9 top-space bottom-space">
    <a href="#"><strong>> Usuarios</strong></a>
</div>-->

<div class="col-lg-9 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO USUARIOS</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(90)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(9)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoUsuario.jsp">Registro Usuario</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/usuarios/consultaUsuario.jsp">Listado Usuarios</a></li>
                        <c:if test="${permissions.check(12)}">
                        <li role="presentation"><a href="<c:url value='/nuevoPerfil' />">Registro Perfil</a></li>
                        </c:if>
                        <c:if test="${permissions.check(11)}">
                        <li role="presentation"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado Perfiles</a></li>                    
                        </c:if>
                </ul>    

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <div class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                        <table id="tablaUsuario" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Nombre completo</th>                                
                                    <th>N&uacute;mero de documento</th>
                                    <th>Correo electr&oacute;nico</th>
                                    <th>Nombre de usuario</th>
                                    <th>Perfil</th>
                                    <th>Estado</th>
                                        <c:if test="${permissions.check(91)}">
                                        <th>Editar</th>                                
                                        </c:if>
                                        <c:if test="${permissions.check(92)}">
                                        <th>Activar/Desactivar</th>
                                        </c:if>  
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstruta.size() > 0}">
                                        <c:forEach items="${select.lstusuario}" var="usuario">
                                            <tr>
                                                <td>${usuario.nombre} ${usuario.apellido}</td>
                                                <td>${usuario.numdoc}</td>
                                                <td>${usuario.email}</td>
                                                <td>${usuario.login}</td>
                                                <td>${usuario.perfilusuario}</td>                                    
                                                <c:choose>
                                                    <c:when test="${usuario.estado == 1}">
                                                        <td> <span class="label label-success">Activo</span> </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td> <span class="label label-danger">Inactivo</span> </td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${usuario.estado == 1}">
                                                        <c:if test="${permissions.check(91)}">
                                                            <td>
                                                                <form class="form-in-table" action="<c:url value='/pre_editarUsuario' />" method="post">
                                                                    <input type="hidden" name="numdoc" value="${usuario.numdoc}">
                                                                    <input type="submit" class="btn btn-success btn-info btn-xs" value="Editar" style="width: 80px;">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:if test="${permissions.check(92)}">
                                                            <td>
                                                                <form class="form-in-table" action="<c:url value='/eliminarUsuario' />" method="post">
                                                                    <input type="hidden" name="numdoc" value="${usuario.numdoc}">
                                                                    <input type="submit" class="btn btn-danger btn-xs" value="Desactivar" style="width: 80px;">
                                                                </form>
                                                            </td>
                                                        </c:if>                    
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(91)}">
                                                            <td> <span class="label label-warning">No editable</span> </td>
                                                        </c:if>
                                                        <c:if test="${permissions.check(92)}">
                                                            <td>
                                                                <form class="form-in-table" action="<c:url value='/restaurarUsuario' />" method="post">
                                                                    <c:choose>
                                                                        <c:when test="${usuario.token != null && usuario.token != ''}">
                                                                            <input type="hidden" name="notificar" value="1">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input type="hidden" name="notificar" value="0">
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <input type="hidden" name="nombre"   value="${usuario.nombre}">                                                            
                                                                    <input type="hidden" name="apellido" value="${usuario.apellido}">                                                            
                                                                    <input type="hidden" name="login"  value="${usuario.login}">                                                            
                                                                    <input type="hidden" name="numdoc" value="${usuario.numdoc}">                                                            
                                                                    <input type="hidden" name="correo" value="${usuario.email}">                                                            
                                                                    <input type="submit" class="btn btn-success btn-xs" value="Activar" style="width: 80px;">
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
    // Tabla dinamica de usuarios
    $(document).ready(function () {
        $('#tablaUsuario').DataTable({            
            responsive: true,
            aLengthMenu: [300, 500],
            scrollY: 500,
            searching: true,
            bAutoWidth: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
    });
</script>

<jsp:include page="/include/end.jsp" />
