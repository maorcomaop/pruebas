
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-7 centered">   
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PROPIETARIOS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','RegistrarGruposMoviles'])}">
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
                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose> 
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="/RDW1/app/propietario/nuevoPropietario.jsp">Registro</a></li>                    
                    <li role="presentation" class="active"><a href="/RDW1/app/propietario/nuevoPropietario.jsp">Adicionar vehiculos a un propietario</a></li>                    
                    <li role="presentation"><a href="/RDW1/app/propietario/listadoPropietario.jsp">Listado de Propietarios</a></li>  
                </ul>
                     
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">         
                        <form class="form-horizontal" action="<c:url value='/guardarAddVehiculosPropietario' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-7">
                                        <label class="control-label" for="inputName">Propietario</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-7">
                                        <div class="controls">
                                            <select class="selectpicker" data-actions-box="true" title="Seleccione un propietario" data-style="btn-primary" name="id_p" id="owner_add">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">                                  
                                <label class="control-label">Veh&iacute;culos</label>
                                <div class="controls">
                                    <select class="selectpicker" multiple data-actions-box="true" title="Seleccione un vehiculo" data-style="btn-primary" name="id_vh" id="car_add">
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                            
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonGrupoMovil" value="Guardar">
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
        
        
        $.post("/RDW1/consultarPerfilPropietario", {perfil:'Propietario'},
            function (result) {
                
                $.post("/RDW1/consultarPropietario", {id_perfil: $.trim(result)},
                function (result) {
                        $("#owner_add").append(result);
                        $("#owner_add").selectpicker("refresh");
                        $("#owner_add option[value="+${id}+"]").attr("selected",true);  
                        $("#owner_add").selectpicker("refresh");
                        });
            });
            
         $.post("/RDW1/consultarVehiculos", {id:1}, 
         function (result) { 
                                $("#car_add").append(result); 
                                $("#car_add").selectpicker("refresh");
                            });
                window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
    });


</script>
<jsp:include page="/include/end.jsp" />