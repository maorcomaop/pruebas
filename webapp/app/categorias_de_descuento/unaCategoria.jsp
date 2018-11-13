
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">

    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CATEGOR&Iacute;AS DE DESCUENTO</h1>

    <section class="boxed padding">

        <ul class="nav nav-tabs">
            <li role="presentation"><a href="/RDW1/app/categorias_de_descuento/nuevaCategoria.jsp">Registro</a></li>
            <li role="presentation" ><a href="/RDW1/app/categorias_de_descuento/listadoCategorias.jsp">Listado de Categor&iacute;as</a></li>                    
            <li role="presentation" class="active"><a href="/RDW1/app/categorias_de_descuento/unaCategoria.jsp">Categor&iacute;a -> ${categoria.nombre}</a></li>                    
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
                <form class="form-horizontal" action="<c:url value='/editarCategoria' />" method="post">
                    <input type="hidden" name="id_edit" value="${categoria.id}">
                    <div class="control-group">
                        <div class="row">
                            <div class="col-sm-5">
                                <label class="control-label" for="inputName">Nombre</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5">
                                <div class="controls">
                                    <input type="text" name="nombre" id="name" placeholder="" class="form-control" title="Especifica un nombre correcto." required value="${categoria.nombre}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="control-group">
                        <div class="row">
                            <div class="col-sm-5">
                                <label class="control-label" for="inputName">Descripci&oacute;n</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5">
                                <div class="controls">
                                    <textarea name="descripcion" cols="10" id="description" rows="10" class="form-control" >${categoria.descripcion}</textarea>
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
                                    <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="$" data-off="%" type="checkbox" name="tipo_descuento" id="valor" value="1">
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
                                    <input data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="liquidacion" id="liquida" value="1">
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
                        <c:when test ="${(login.idempresa == 9) || (login.idempresa == 10)}">
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
                    <br>
                    <div id="caja_moneda">
                        <div class="control-group">
                            <div class="row">
                                <div class="col-sm-5">
                                    <label class="control-label" id="etq" for="inputName">Valor</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-5">
                                    <div class="controls">
                                        <input type="text" name="valor_moneda" id="val" class="form-control" value="${categoria.valor}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>            
                    <br>
                    <div class="control-group">                            
                        <div class="controls">
                            <input type="submit" class="btn btn-primary botonCategoria" value="Guardar">
                        </div>
                    </div>    

            </div>    

        </div>    

        </form>         
    </section>
</div>      
<jsp:include page="/include/footerHome.jsp" /> 
<script>
    $(document).ready(function () {

        if (${categoria.aplicaDescuento} === 1)
        {
            $('#liquida').bootstrapToggle('on');
        } else
        {
            $('#liquida').bootstrapToggle('off');
        }



        if (${categoria.es_fija} === 1)
        {
            $('#siempre').bootstrapToggle('on');
        } else
        {
            $('#siempre').bootstrapToggle('off');
        }


        if (${categoria.descuenta_pasajeros} === 1)
        {
            $('#pasajeros').bootstrapToggle('on');
        } else
        {
            $('#pasajeros').bootstrapToggle('off');
        }

        if (${categoria.descuenta_del_total} === 1)
        {
            $('#descuento_total').bootstrapToggle('on');
        } else
        {
            $('#descuento_total').bootstrapToggle('off');
        }

        if (${categoria.es_valor_moneda} === 1)
        {
            $('#valor').bootstrapToggle('on');
            //$("#valor").prop("checked", true);
            $('#value').val(${categoria.valor});
            console.log("pasa aqui" + $('#valor').prop('checked'));
        } else
        {
            $('#valor').bootstrapToggle('off');
            //$("#valor").prop("checked", false);
            $('#value').val(${categoria.valor});
        }

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