<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="tablePropietario" class="display" cellspacing="0" width="100%">                            
    <thead>
                                <tr>                                    
                                    <th>Placa</th>
                                    <th>N&ua&uacute;mero Interno</th>                                    
                                    <th>Eliminar</th>                                    
                                </tr>    
                            </thead>    
                            <tbody>
                                <c:choose>
                                    <c:when test="${vehiculos.size() > 0}">
                                        <tr>                                    
                                            <c:forEach items="${vehiculos}" var="p">    
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
                var id_fila = $(this).parent('td').attr('id');
                var id_propietario = $(this).attr('id');
                
                $.post("/RDW1/desactivarPropietarioVehiculo", {id: id_fila},
                function (result) {
                        if($.trim(result) === '1'){                              
                              $.post("/RDW1/actualizarConsultaVehiculosPropietario", {id_p: id_propietario}, 
                                    function (result) {
                                        $('#pestana1').html(result)
                                    });
                          }
                          else{console.log("No se ha borrado");}
                        });
                
            } else {
                return false;
            }
        });/*FIN CLIC*/
        
    });
</script>