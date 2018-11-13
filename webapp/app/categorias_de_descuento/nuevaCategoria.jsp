
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CATEGOR&Iacute;AS DE DESCUENTO</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(111)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active" ><a href="/RDW1/app/categorias_de_descuento/nuevaCategoria.jsp">Registro</a></li>
                        <c:if test="${permissions.check(112)}">
                        <li role="presentation" ><a href="/RDW1/app/categorias_de_descuento/listadoCategorias.jsp">Listado de Categor&iacute;as</a></li>
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
                        <form class="form-horizontal" action="<c:url value='/guardarCategoria' />" method="post">
                            <div class="control-group ">
                                <div class="row">
                                    <div class="col-sm-7">
                                        <label class="control-label" for="inputName">Nombre*</label>
                                    </div>
                                </div>                 
                                <div class="row">
                                    <div class="col-sm-7">
                                        <div class="controls">
                                            <input type="text" name="nombre" id="name" class="form-control" data-toggle="tooltip" title="Digite aqui el nombre" autofocus>                         
                                        </div>
                                    </div>
                                </div>
                            </div>  

                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-7">
                                        <label class="control-label" for="inputName">Descripci&oacute;n</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-7">
                                        <div class="controls">
                                            <textarea name="descripcion" id="description" class="form-control" cols="10" rows="10" data-toggle="tooltip" title="Digite aqui la descripcion" ></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div> 
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <label class="control-label" for="inputName">Aplicar siempre</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-5">
                                        <div class="controls">
                                            <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="aplicar_siempre" id="siempre" value="1">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <label class="control-label" for="inputName">Tipo de valor del descuento</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-5">
                                        <div class="controls">
                                            <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="$" data-off="%" type="checkbox" name="tipo_valor_descuento" id="valor" value="1" title="Selecione el tipo de descuento">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <label class="control-label" for="inputName">Descuento operativo</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-5">
                                        <div class="controls">
                                            <input data-toggle="toggle tooltip" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="liquidacion" title="Seleccione una opcion" id="liquida" value="1">
                                        </div>
                                    </div>
                                </div>
                            </div>   
                            <br>

                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <label class="control-label" for="inputName">Descuenta pasajeros</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-5">
                                        <div class="controls">
                                            <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="descuenta_pasajeros" id="pasajeros" value="1">
                                        </div>
                                    </div>
                                </div>

                            </div>  
                            <br>
                            <c:choose>
                                <c:when test ="${(login.idempresa == 9)}">
                                    <div class="control-group">
                                        <div class="row">
                                            <div class="col-sm-5">
                                                <label class="control-label" for="inputName">Descuenta del total</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-5">
                                                <div class="controls">
                                                    <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="descuenta_del_total" id="descuento_total" value="1" title="Selecione el tipo de descuento">
                                                </div>
                                            </div>
                                        </div>
                                    </div>  
                                    <br>
                                </c:when>                     
                            </c:choose>  
                            <div id="value">
                                <div class="control-group">
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <label class="control-label" for="inputName">Valor*</label>
                                        </div>
                                    </div>                 
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <div class="controls">
                                                <input type="text" name="valor_descuento" id="val" class="form-control" data-toggle="tooltip" title="Digite aqui el valor" >
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <br>             
                            <div class="control-group">                            
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonCategoria" data-toggle="tooltip" title="Haga clic aqui para guardar" data-placement="right" value="Guardar">
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

        $('#descuento_total').on("change", function () {
            if ($(this).prop("checked") === true) {
                $('#pasajeros').bootstrapToggle('off');
                $('#liquida').bootstrapToggle('off');
            }
        });

        $('#pasajeros').on("change", function () {
            if ($(this).prop("checked") === true) {
                $('#descuento_total').bootstrapToggle('off');
            }
        });

        $('#liquida').on("change", function () {
            if ($(this).prop("checked") === true) {
                $('#descuento_total').bootstrapToggle('off');
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