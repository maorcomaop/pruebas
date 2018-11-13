
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-7 centered">   
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO GRUPO DE VEH&Iacute;CULOS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','RegistrarGruposMoviles'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/grupo_movil/nuevoGrupoMovil.jsp">Registro</a></li>

                    <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','ListadoGruposMoviles'])}">
                        <li role="presentation"><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Listado de Grupos</a></li>  
                        </c:if>
                        <c:if test="${permissions.check(['Configuracion','Vehiculos','GruposMoviles','AdicionarGruposMoviles'])}">
                        <li role="presentation"><a href="/RDW1/app/grupo_movil/adicionarMovilesAGrupo.jsp">Adicionar Veh&iacute;culos</a></li>  
                        </c:if>
                </ul>
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
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">         
                        <form class="form-horizontal" action="<c:url value='/guardarGrupoMovil' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-7">
                                        <label class="control-label" for="inputName">Nombre del grupo</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-7">
                                        <div class="controls">
                                            <input type="text" name="nombre" id="name" class="form-control" title="Digite el nombre del grupo">
                                        </div>
                                    </div>
                                </div>
                            </div>                                                    
                            <div class="control-group">                                  
                                <label class="control-label">Aplicar tiempos</label>
                                <div class="controls">                     
                                    <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="aplica" value="1">
                                </div>
                            </div>              
                            <div class="control-group">                                  
                                <label class="control-label">Adicionar rutas</label>
                                <div class="controls">                                          
                                    <input id="prueba" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="adicionar_rutas"value="1">
                                </div>
                            </div>
                            <div id="addrutas">                 
                                <div class="control-group">
                                    <label class="control-label" for="inputName">Rutas</label>
                                    <div class="controls">
                                        <select class="selectpicker" multiple data-actions-box="true" title="Seleccione un item" data-style="btn-primary" name="rutas" id="route">                      
                                            <c:forEach items="${select.lstrutas}" var="rutas">
                                                <option value="${rutas.id}">${rutas.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
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
        $("#addrutas").hide();
        $('#prueba').change(function ()
        {
            if ($(this).prop('checked') === true)
            {
                $('#addrutas').toggle("slow");
            } else
            {
                $('#addrutas').toggle("hide");
            }
        });

        $('.selectpicker').selectpicker({
            style: 'btn-primary',
            size: 4,
            liveSearch: true
        });

        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
    });


</script>
<jsp:include page="/include/end.jsp" />