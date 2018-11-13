
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO GRUPO DE VEH&Iacute;CULOS</h1>
    <c:choose>
                    <c:when test ="${idInfo == 0}">                                    
                        <div class="alert alert-info">
                            <button type="button" class="close fade" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>        
                    <c:when test ="${idInfo == 1}">                                    
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>
                    <c:when test ="${idInfo == 2}">                                    
                        <div class="alert alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose>      
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','AdicionarGruposMoviles'])}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','RegistrarGruposMoviles'])}">
                        <li role="presentation" ><a href="/RDW1/app/grupo_movil/nuevoGrupoMovil.jsp">Registro</a></li>
                        </c:if>
                        <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','ListadoGruposMoviles'])}">
                        <li role="presentation"><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Listado de grupos</a></li>   
                        </c:if>
                    <li role="presentation" class="active"><a href="/RDW1/app/grupo_movil/adicionarMovilesAGrupo.jsp">Adicionar Veh&iacute;culos a un Grupo</a></li>                      
                </ul>

                
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">

                        <form class="form-horizontal" action="<c:url value='/adicionarVehiculosAGrupo' />" method="post">             
                            <div class="control-group">
                                <label class="control-label" for="inputName">Nombre del grupo</label>
                                <div class="controls">
                                    <select class="selectpicker" data-style="btn-primary" title="Seleccione un item" name="grupo" id="group">                                        
                                        <c:forEach items="${select.lstgrupovehiculos}" var="grupo">
                                            <option value="${grupo.id}">${grupo.nombreGrupo}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                                  
                                <label class="control-label">Permitir veh&iacute;culos en multiples grupos</label>
                                <div class="controls">                                          
                                    <input id="prueba" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="vehiculos_libres"value="1">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                                  
                                <label class="control-label">Adicionar veh&iacute;culos</label>
                                <div class="controls">
                                    <select class="selectpicker" multiple data-actions-box="true" title="Seleccione un item" data-style="btn-primary" name="vehiculo" id="code">
                                    </select>
                                </div>
                            </div>
                            <br>                         
                            <div class="control-group">                            
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonMovilesAGrupo" value="Guardar">
                                </div>
                            </div>    
                        </form>
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

        $('.selectpicker').selectpicker({
            style: 'btn-primary',
            size: 4,
            liveSearch: true
        });
        /*Carga inicialmente los vehiculos*/
        $.post("/RDW1/buscarMovilesDeGrupo", {id_grupo: 2},
                function (result) {
                    $("#code").html(result);
                    $("#code").selectpicker("refresh");
                });

        $('#prueba').change(function ()
        {
            if ($(this).prop('checked') === true)
            {
                $.post("/RDW1/buscarMovilesDeGrupo", {id_grupo: 1},
                        function (result) {
                            $("#code").html(result);
                            $("#code").selectpicker("refresh");
                        });
            } else {
                $.post("/RDW1/buscarMovilesDeGrupo", {id_grupo: 2},
                        function (result) {
                            $("#code").html(result);
                            $("#code").selectpicker("refresh");
                        });
            }
        });



        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
    });


</script>
<jsp:include page="/include/end.jsp" />