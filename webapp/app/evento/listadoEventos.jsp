<jsp:include page="/include/headerHome.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="col-lg-12">    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Configuracion','Eventos','Listado'])}">    

                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="/RDW1/app/evento/nuevoEvento.jsp">Registro</a></li>
                    <li role="presentation" class="active"><a href="/RDW1/app/evento/listadoEventos.jsp">Listado de Eventos</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tableEvento" class="display compact" cellspacing="0" width="100%">
                            <thead>
                                <tr>                                
                                    <th>C&oacute;digo</th>
                                    <th>Nombre</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Prioridad</th>                                
                                    <th>Cantidad</th>                                
                                    <th>Tipo</th>
                                    <th>Editar</th>
                                    <th>Deshabilitar</th>
                                </tr>    
                            </thead>    
                            <tbody>
                                <tr>
                                    <c:forEach items="${select.lstevento}" var="evento">    
                                        <td>${evento.codigoEvento}</td>
                                        <td>${evento.nombreGenerico}</td>
                                        <td>${evento.descripcion}</td>
                                        <td>${evento.prioridad}</td>
                                        <td>${evento.cantidad}</td>
                                        <td>${evento.tipoEvento}</td>
                                        <c:choose>
                                            <c:when test ="${evento.estado == 1}">
                                                <td>
                                                    <form action="<c:url value='/verMasEvento' />" method="post">
                                                        <input type="hidden" name="id" value="${evento.id}">
                                                        <input type="submit" class="btn  btn-xs btn-info" value="Editar">
                                                    </form>            
                                                </td>
                                                <td>
                                                    <form action="<c:url value='/eliminarEvento' />" method="post">
                                                        <input type="hidden" name="estado" value="0">
                                                        <input type="hidden" name="id" value="${evento.id}">
                                                        <input type="submit" class="btn  btn-xs btn-danger" value="Desactivar">
                                                    </form>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><span class="label label-danger">No editable</span></td>
                                                <td>
                                                    <form action="<c:url value='/eliminarEvento' />" method="post">
                                                        <input type="hidden" name="estado" value="1">
                                                        <input type="hidden" name="id" value="${evento.id}">
                                                        <input type="submit" class="btn  btn-xs btn-success" value="Activar">
                                                    </form>
                                                </td>    
                                            </c:otherwise>                    
                                        </c:choose>
                                    </tr>
                                </c:forEach>        
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
        $('#tableEvento').DataTable({
            aLengthMenu: [300, 500],
            scrollY: 400,
            scrollX: true,
            searching: true,
            bAutoWidth: false,
            bInfo: false,
            paging: false
        });
    });
</script>
<jsp:include page="/include/end.jsp" />