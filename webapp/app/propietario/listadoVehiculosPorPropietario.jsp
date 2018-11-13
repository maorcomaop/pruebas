
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PROPIETARIO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(65)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="/RDW1/app/propietario/nuevoPropietario.jsp">Registro</a></li>                    
                    <li role="presentation" class="active"><a href="/RDW1/app/propietario/listadoPropietario.jsp">Listado de veh&iacute;culos por Propietario</a></li>  
                </ul>
                <div class="tab-content">                    
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">                        
                        <table id="tablePropietario" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>                                    
                                    <th>Propietario</th>
                                    <th>Placa</th>
                                    <th>Número Interno</th>                                                                                                                                                
                                    <th>Eliminar</th>                                    
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${vehiculos.size() > 0}">
                                        <tr>                                    
                                            <c:forEach items="${vehiculos}" var="p">    
                                                <td>${p.propietario}</td>
                                                <td>${p.placaVehiculo}</td>
                                                <td>${p.numeroInterno}</td>                                                                                                
                                                <td id="${p.id}"><span id="${p.fk_propietario}" class="table-remove glyphicon glyphicon-remove"></span></td>                                                 
                                                
                                            </tr>
                                        </c:forEach>        
                                    </c:when>
                                </c:choose>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td></td>
                                    <td><a href="/RDW1/app/propietario/listadoPropietario.jsp" role="button" class=" btn btn-xs btn-success">Volver</a></td>
                                    <td></td>
                                </tr>
                            </tfoot>
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
        var tablaPropietario = $('#tablePropietario').DataTable({
            aLengthMenu: [600, 1000],
            scrollY: 500,
            scrollX: true,
            searching: true,
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
        $('.table-remove').click(function () {
                         
             var mensaje = confirm("¿Desea Eliminar el registro?");             
             if (mensaje) {
                var id_relacion = $(this).parent('td').attr('id');
                var id_propietario = $(this).attr('id');
                
                $.post("/RDW1/desactivarPropietarioVehiculo", {id: id_relacion},
                function (result) {
                    
                     var obj = jQuery.parseJSON( $.trim(result) );       
                     console.log("---> "+$.trim(result));
                     if(obj.id > 0){
                        window.location.reload(true);                             
                          }
                          else{
                              console.log("No se ha borrado");
                          }                          
                        });
                
            } else {
                return false;
            }
        });/*FIN CLIC*/
        
        

        
    });

        /*$('.table-remove').off("click");*/
        
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