
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--
<div class="col-md-9 top-space bottom-space">
    <a href="#"><strong>> Empresas</strong></a>
</div>-->

<div class="col-lg-8 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO EMPRESAS</h1>    
    <div class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Configuracion','Empresa','ListadoEmpresas'])}">      
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(22)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaEmpresa.jsp">Nueva Empresa</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="#">Listado Empresas</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <div class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>

                        <table id="tablaEmpresa" class="display" cellspacing="0" width="100%">                        
                            <thead>                                                          
                                <tr>
                                    <th>Nombre</th>
                                    <th>Nit</th>
                                    <th>Pa&iacute;s</th>
                                    <th>Departamento</th>
                                    <th>Ciudad</th>
                                    <th>Direcci&oacute;n</th>
                                    <th>Tel&eacute;fono</th>
                                    <th>P&aacute;gina web</th>
                                    <th>Correo electr&oacute;nico</th>
                                        <c:if test="${permissions.check(99)}">
                                        <th>Editar</th>
                                        </c:if>
                                    <th>Estado</th>                                
                                </tr>
                            </thead>        
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstempresa.size() > 0}">
                                        <c:forEach items="${select.lstempresa}" var="empresa">
                                            <tr>
                                                <td>${empresa.nombre}</td>
                                                <td>${empresa.nit}</td>
                                                <td>${empresa.pais}</td>
                                                <td>${empresa.dpto}</td>
                                                <td>${empresa.ciudad}</td>
                                                <td>${empresa.direccion}</td>
                                                <td>${empresa.telefono}</td>
                                                <td>${empresa.paginaweb}</td>
                                                <td>${empresa.email}</td>
                                                <c:choose>
                                                    <c:when test="${empresa.estado == 1}">
                                                        <c:if test="${permissions.check(99)}">
                                                            <td>
                                                                <form class="form-in-table" action="<c:url value='/pre_editarEmpresa' />" method="post">                                                                    
                                                                    <input type="hidden" name="id" value="${empresa.id}">
                                                                    <input type="hidden" name="nit" value="${empresa.nit}">
                                                                    <input type="submit" class="btn btn-success btn-info btn-xs" value="Editar" style="font-size: 9pt; width: 80px;">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(100)}">
                                                                <td>
                                                                    <form id="form-delete-empresa-${empresa.id}" class="form-in-table" action="<c:url value='/eliminarEmpresa' />" method="post">
                                                                        <input type="hidden" name="id" value="${empresa.id}">
                                                                        <input type="hidden" name="nit" value="${empresa.nit}">
                                                                        <input type="button" class="btn btn-danger btn-xs" value="Desactivar" 
                                                                               onclick="confirmar_eliminacion('${empresa.nombre}', ${empresa.id});"
                                                                               style="font-size: 9pt; width: 80px;">
                                                                    </form>
                                                                </td>
                                                            </c:when>                    
                                                            <c:otherwise>
                                                                <td><span class="label label-success">Activo</span></td>
                                                            </c:otherwise>                                        
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissions.check(99)}">
                                                            <td><span class="label label-danger">No editable</span></td>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${permissions.check(100)}">
                                                                <td>
                                                                    <form class="form-in-table" action="<c:url value='/restaurarEmpresa' />" method="post">
                                                                        <input type="hidden" name="id" value="${empresa.id}">
                                                                        <input type="hidden" name="nit" value="${empresa.nit}">
                                                                        <input type="submit" class="btn btn-success btn-xs" value="Activar" style="font-size: 9pt; width: 80px;">
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
    </div>
</div>

<jsp:include page="/include/footerHome.jsp" />

<script>
    // Tabla dinamica empresa
    $(document).ready(function () {
        $('#tablaEmpresa').DataTable({
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

    function confirmar_eliminacion(nombre_empresa, id_empresa) {

        var msg = "¿Est&aacute; seguro de eliminar empresa <strong>" + nombre_empresa + "</strong>?";
        var sform = "#form-delete-empresa-" + id_empresa;

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
