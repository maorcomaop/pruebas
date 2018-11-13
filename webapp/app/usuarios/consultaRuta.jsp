
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO RUTAS</h1>    
    <section class="boxed padding">                    
        <c:choose>
            <c:when test="${permissions.check(115)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(50)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(126)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignación Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(127)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp">Edición Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(115)}">
                        <li role="presentation" class="active"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado Rutas</a></li>
                        </c:if>
                        <c:if test="${permissions.check(128)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(52)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos</a>
                        </c:if>
                </ul>                                                  
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <div class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>

                        <table id="tablaRuta" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Empresa</th>
                                    <th>Nombre</th> 
                                        <c:if test="${permissions.check(116)}">
                                        <th>Editar</th>
                                        </c:if>
                                        <c:if test="${permissions.check(117)}">
                                        <th>Estado</th>
                                        </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstruta.size() > 0}">
                                        <c:forEach items="${select.lstruta}" var="ruta">
                                            <tr>
                                                <td>${ruta.empresa}</td>
                                                <td>${ruta.nombre}</td>
                                                <c:if test="${permissions.check(116)}">
                                                    <td>
                                                        <form class="form-in-table" action="<c:url value='/pre_editarRuta' />" method="post">
                                                            <input type="hidden" id="idRuta" name="idRuta" value="${ruta.id}">
                                                            <button type="submit" class="btn btn-success btn-info btn-xs" style="width: 80px;">Editar</button>
                                                        </form>
                                                    </td>
                                                </c:if>
                                                <c:if test="${permissions.check(117)}">
                                                    <td>
                                                        <form id="form-delete-ruta-${ruta.id}" class="form-in-table" action="<c:url value='/eliminarRuta' />" method="post">
                                                            <input type="hidden" id="idRuta" name="idRuta" value="${ruta.id}">
                                                            <button type="button" class="btn btn-danger btn-xs" 
                                                                    onclick="confirmar_eliminacion('${ruta.nombre}', ${ruta.id});" 
                                                                    style="width: 80px;">Desactivar</button>
                                                        </form>
                                                    </td>
                                                </c:if>
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
    // Tabla dinamica de rutas
    $(document).ready(function () {
        $('#tablaRuta').DataTable({
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

    function confirmar_eliminacion(nombre_ruta, id_ruta) {

        var msg = "¿Est&aacute; seguro de eliminar ruta <strong>" + nombre_ruta + "</strong>?";
        var sform = "#form-delete-ruta-" + id_ruta;

        bootbox.confirm({
            title: "Eliminaci&oacute;n de registro",
            message: msg,
            buttons: {
                confirm: {
                    label: 'Aceptar',
                    className: 'btn btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn'
                }
            },
            callback: function (rs) {
                if (rs) {
                    $(sform).submit();
                }
            }
        });
    }

</script>
<jsp:include page="/include/end.jsp" />
