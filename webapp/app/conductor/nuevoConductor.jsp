
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CONDUCTOR</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(66)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/conductor/nuevoConductor.jsp">Registro</a></li>
                        <c:if test="${permissions.check(65)}">
                        <li role="presentation"><a href="/RDW1/app/conductor/listadoConductor.jsp">Listado Conductores</a></li>                    
                        </c:if>
                        <li role="presentation"><a href="/RDW1/app/relacion_vehiculo_conductor/listadoRelacionVehiculoConductor.jsp">Conductor - Veh&iacute;culo </a></li>                    
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
                        <div>Los campos con asterisco (*) son obligatorios</div> 
                        <form class="form-horizontal" action="<c:url value='/guardarConductor' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Tipo de documento*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="tipo_doc" id="type">
                                            <option value="0">Seleccione un tipo</option>
                                            <c:forEach items="${select.lst_tipodocumento}" var="typeDoc">
                                                <option value="${typeDoc.id}">${typeDoc.tipo}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">N&uacute;mero de documento*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">   
                                        <input type="text" name="numero_documento" id="num_doc"  class="form-control" data-toggle="tooltip" title="Digite aqui el n&u&uacute;mero de documento" autofocus>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                               
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Nombre*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <input type="text" name="nombre" id="name" class="form-control" data-toggle="tooltip" title="Digite aqui el nombre">
                                    </div>
                                </div>
                            </div>

                            <br>

                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Apellido*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <input type="text" name="apellido" id="last" class="form-control" data-toggle="tooltip" title="Digite aqui el apellido">
                                    </div>
                                </div>
                            </div> 
                            <br>

                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonconductor" data-toggle="tooltip" title="Haga clic aqui para guardar" value="Guardar">
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
    //$('[data-toggle="tooltip"]').tooltip(); 
        $("#num_doc").keyup(function ()
                /*$("#num_doc").blur(function ()*/
                {
                    $.post("/RDW1/encontrarConductor", {cedula: $(this).val()},
                            function (result) {
                                var valor = parseInt(result);
                                if (valor === 1)
                                {
                                    //$(this).closest('.form-group').removeClass('has-success').addClass('has-error');                                    
                                    $("#num_doc").focus().after('<span class="error">Este documento, ya existe</span>');
                                    $("#name").attr('disabled', 'disabled');
                                    $("#last").attr('disabled', 'disabled');
                                    console.log("el codigo ya existe");
                                } else
                                {
                                    console.log("el codigo NO existe");
                                    $("#name").removeAttr('disabled');
                                    $("#last").removeAttr('disabled');
                                }
                            });


                });


        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);

    });
</script>
<jsp:include page="/include/end.jsp" />