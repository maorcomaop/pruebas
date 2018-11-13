
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PROPIETARIO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(65)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="/RDW1/app/propietario/nuevoPropietario.jsp">Registro</a></li>                    
                    <li role="presentation" class="active"><a href="/RDW1/app/propietario/listadoPropietario.jsp">Listado de Propietarios</a></li>  
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <table id="tablePropietario" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>                                    
                                    <th>Nombre</th>
                                    <th>Apellido</th>                                        
                                    <th>Correo</th>                                    
                                    <th>Adicionar</th>
                                    <th>Veh&iacute;culos</th>                                        
                                    <th>Estado</th>
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lst_propietario.size() > 0}">
                                        <tr>                                    
                                            <c:forEach items="${select.lst_propietario}" var="p">    
                                                <td>${p.nombre}</td>
                                                <td>${p.apellido}</td>
                                                <td>${p.email}</td>  
                                                <td>
                                                    <form action="<c:url value='/adicionardVehiculosPropietario' />" method="post">                                                        
                                                        <input type="hidden" name="id_propietario" value="${p.id}">
                                                        <input type="submit" class="btn  btn-xs btn-success" value="Adicionar">
                                                    </form>
                                                </td>
                                                <c:choose>
                                                    <c:when test ="${p.estado == 1}"> 
                                                        
                                                            <td>
                                                                <form action="<c:url value='/consultarVehiculosPropietario' />" method="post">
                                                                    <input type="hidden" name="id" value="${p.id}">
                                                                    <input type="submit" class="btn  btn-xs btn-info" value="Ver mas">
                                                                </form>            
                                                            </td>                                                        
                                                            <td>
                                                                    <form action="<c:url value='/eliminarConductor' />" method="post">
                                                                        <input type="hidden" name="estado" value="0">
                                                                        <input type="hidden" name="id" value="${p.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-danger eliminar" value="Desactivar">
                                                                    </form>
                                                            </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                                <td><span class="label label-danger">No editable</span></td>
                                                                <td>
                                                                    <form action="<c:url value='/eliminarConductor' />" method="post">
                                                                        <input type="hidden" name="estado" value="1">
                                                                        <input type="hidden" name="id" value="${p.id}">
                                                                        <input type="submit" class="btn  btn-xs btn-success" value="Activar">
                                                                    </form>
                                                                </td>                                                                                              
                                                        
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
    $(document).ready(function ()
    {
        $('#tablePropietario').DataTable({
            aLengthMenu: [600, 1000],
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