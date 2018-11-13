<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO ALARMAS</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(19)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/alarma/nuevoAlarma.jsp">Registro</a></li>
                        <c:if test="${permissions.check(20)}">
                        <li role="presentation"><a href="/RDW1/app/alarma/listadoAlarma.jsp">Listado de Alarmas</a></li>                    
                        </c:if>
                </ul>
                <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
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
                        <div>Los campos con aterisco (*) con obligatorios</div>
                        <form class="form-horizontal" action="<c:url value='/guardarAlarma' />" method="post">  
                            <div class="control-group">
                                <label class="control-label" for="inputName" >Codigo*</label>
                                <div class="controls">
                                    <select class="selectpicker" data-style="btn-primary" name="codigo" id="code" title="Seleccione un c&oacute;digo">                                        
                                    </select>
                                </div>                            
                                <br>
                                <div class="control-group">
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <label class="control-label" for="inputName">Nombre*</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <div class="controls">
                                                <input type="text" name="nombre" id="name" class="form-control" data-toggle="tooltip" title="Digite aqui el nombre" required>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="control-group">
                                        <label class="control-label" for="inputName">Unidad de Medida</label>
                                        <div class="controls">
                                            <select class="selectpicker" data-style="btn-primary" name="unidad" id="measure">
                                                <option value="0">Seleccione</option>
                                                <option value="Minutos">Minutos</option>
                                                <option value="Segundos">Segundos</option>                         
                                                <option value="Pasajeros">Pasajeros</option>
                                                <option value="Otro">Otro</option>
                                            </select>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-5">
                                                <label class="control-label" id="etq3" for="cual">Cual?: </label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-5">
                                                <div class="controls">
                                                    <input type="text" class="form-control" name="cual_unidad" id="which_one" data-toggle="tooltip" title="Digite aqui la unidad de medida" >
                                                </div>                  
                                            </div>                  
                                        </div> 
                                    </div>
                                    <br>
                                    <div class="control-group">                            
                                        <div class="controls">
                                            <input type="submit" class="btn btn-primary botonAlarma" value="Guardar" data-toggle="tooltip" title="Haga clic para guardar">
                                        </div>
                                    </div>    

                                </div>
                        </form>                            
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
        $("#etq1").hide();
        $("#which_type").hide();

        $("#etq3").hide();
        $("#which_one").hide();

        $("#type").change(function ()
        {
            var item = $(this).val();
            if (item === "Otro")
            {
                console.log("DEBE APARECER CUAL");
                $("#etq1").show();
                $("#which_type").show();
            } else
            {
                console.log("NO ENCUENTRA");
                $("#etq1").hide();
                $("#which_type").hide();

            }
        });

        /************************************/
        $("#measure").change(function ()
        {
            var item = $(this).val();
            if (item === "Otro")
            {
                console.log("DEBE APARECER CUAL");
                $("#etq3").show();
                $("#which_one").show();
            } else
            {
                console.log("NO ENCUENTRA");
                $("#etq3").hide();
                $("#which_one").hide();

            }
        });

        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);

    });

    $.post("/RDW1/ultimaAlarma", {valor: 1},
            function (result) {
                $("#code").append(result);
                $("#code").selectpicker("refresh");
            });

    /*$("#code").change(function ()
     {   
     $.post("/RDW1/repetidaAlarma", {id_alarma:$(this).val()},
     function (result) {                        
     var valor = parseInt(result);                        
     if(valor === 1)
     {
     alert("Debe seleccionar otro codigo, ya existe una alarma con este codigo");
     }
     else
     {
     console.log("el codigo es apto");
     }                        
     });
     });*/

    $(document).ready(function () {
        $('.selectpicker').selectpicker({
            style: 'btn-primary',
            size: 4,
            liveSearch: true
        });
    });
    /* $('.selectpicker').selectpicker({
     style: 'btn-info',
     size: 4
     });*/
</script>
<jsp:include page="/include/end.jsp" />
