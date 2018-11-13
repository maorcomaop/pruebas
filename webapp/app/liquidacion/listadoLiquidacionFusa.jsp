
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--nada--->         
<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO LIQUIDACI&Oacute;N</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(81)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="/RDW1/app/liquidacion/consultarLiquidacion.jsp">Consultar Liquidaci&oacute;n</a></li>        
                        <c:if test="${permissions.check(['Liquidacion','LiquidarVehiculo'])}">
                            <c:choose>
                                <c:when test ="${(login.idempresa == 9)}">
                                <li role="presentation" ><a href="/RDW1/app/liquidacion/nuevaLiquidacionFusa.jsp">Liquidar Veh&iacute;culo</a></li>
                                </c:when>
                                <c:otherwise>                    
                                <li role="presentation" ><a href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">Liquidar Veh&iacute;culo</a></li>
                                </c:otherwise>
                            </c:choose> 
                        </c:if> 

                    <li role="presentation" class="active"><a href="/RDW1/app/liquidacion/listadoLiquidacionFusa.jsp">Listado Liquidaciones</a></li>                    
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <!--<table id="tableLiquidation" class="display compact" cellspacing="0" width="100%">-->
                        <c:choose>
                            <c:when test="${datos.size() > 0}">
                                <table id="tableLiquidation" class="display compact" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>                                
                                            <th>Codigo liquidacion</th>
                                            <th>FK Veh&Iacute;culo</th>
                                            <th>FK tarifa</th>
                                            <th>FK conductor</th>
                                            <th>Placa</th>
                                            <th>N&uacute;mero interno</th>                                
                                            <th>Usuario</th>
                                            <th>Liquidador</th>                                
                                            <th>Conductor</th>
                                            <th>Tarifa aplicada</th>
                                            <th>Total pasajeros liquidados</th>
                                            <!---<th>Total neto Pasajeros</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal8}</th>
                                            <!---<th>Total descuento al neto</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal2}</th>
                                            <!---<th>Sub total</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal3}</th>
                                            <!---<th>Total descuentos operativos</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal4}</th>
                                            <!---<th>Recibe del conductor</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal5}</th>
                                            <!--<th>Total descuentos administrativos</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal6}</th>
                                            <!--<th>Total recibo</th>-->
                                            <th>${configuracion_etiquetas_sesion.etqTotal7}</th>
                                            <th>Fecha de Liquidaci&oacute;n</th>
                                            <th>Estado</th>
                                                <c:if test="${permissions.check(82)}">
                                                <th>Ver</th>
                                                </c:if>
                                                <c:if test="${permissions.check(83)}">
                                                <th>Anular</th>
                                                </c:if>
                                        </tr>
                                    </thead>    
                                    <tbody>                            
                                        <tr>
                                            <c:forEach items="${datos}" var="liquidacion">    

                                                <td>${liquidacion.id}</td>
                                                <td>${liquidacion.fk_vehiculo}</td>                                            
                                                <td>${liquidacion.fk_tarifa}</td>
                                                <td>${liquidacion.fk_conductor}</td>
                                                <td>${liquidacion.placa}</td>                                            
                                                <td>${liquidacion.numeroInterno}</td>
                                                <td>${liquidacion.usuario}</td>                                            
                                                <td>${liquidacion.nombreCompletoLiquidador}</td>                                            
                                                <td>${liquidacion.nombreCompletoConductor}</td>                                            
                                                <td>${liquidacion.valorTarifa}</td>
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "4" value = "${liquidacion.totalPasajerosLiquidados}" /></td>                                        
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorVueltas + liquidacion.totalValorDescuentoAlNeto}" /></td>                                        
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorDescuentoAlNeto}" /></td>   
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorVueltas}" /></td>
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorDescuentos}" /></td>
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorVueltas - liquidacion.totalValorDescuentos}" /></td>
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorOtrosDescuentos}" /></td>                                        
                                                <td><fmt:formatNumber type = "number" maxIntegerDigits = "10" value = "${liquidacion.totalValorVueltas - liquidacion.totalValorDescuentos - liquidacion.totalValorOtrosDescuentos}" /></td>                                  
                                                <td>${liquidacion.fechaLiquidacion}</td>
                                                <td class="estado" data-id="${liquidacion.id}">
                                                    <c:choose>
                                                        <c:when test ="${liquidacion.estado == 1}">
                                                            <span class="label label-success">Activo</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-danger">Anulada</span>                                            
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <c:if test="${permissions.check(82)}">
                                                    <td>
                                                        <div class="row">
                                                            <div class="col-sm-3">
                                                                <form action="<c:url value='/verReciboLiquidacion' />" method="post" target="_blank">
                                                                    <input type="hidden" name="id" value="${liquidacion.fk_vehiculo}">                                                    
                                                                    <input type="hidden" name="pk_liquidacion" value="${liquidacion.id}">
                                                                    <!--<input type="submit" target="_blank" class="btn  btn-xs  btn-info" value="Ver Recibo">-->
                                                                    <input type="submit" class="btn  btn-xs  btn-info" value="Recibo">
                                                                </form>
                                                            </div>                                                
                                                        </div>
                                                    </td>
                                                </c:if>
                                                <c:if test="${permissions.check(83)}">
                                                    <td>
                                                        <c:choose>
                                                            <c:when test ="${liquidacion.estado == 1}">
                                                                <div class="col-sm-3">
                                                                    <button type="submit" class="btn  btn-xs  btn-danger annul" data-id="${liquidacion.id}">Anular</button>
                                                                </div>
                                                            </c:when>
                                                        </c:choose>                                    
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>                            
                                    </tbody>    
                                </table>
                            </c:when>
                            <c:otherwise>
                                <h5 class="title-area">NO EXISTE INFORMACION</h5> 
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:when> 
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancel" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Confirmaci&oacute;n</h4>
            </div>
            <div class="modal-body">
                ¿Se encuentra seguro de anular la liquidaci&oacute;n?
            </div>
            <div class="modal-footer">           
                <button type="button" class="btn btn-default cancel" >no</button>
                <button type="button" class="btn btn-primary question-yes">si</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModalReason" data-id="-1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancel" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Motivo de la Anulaci&oacute;n</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="text_reason" id="text_reason">

                    </div>
                    <textarea class="form-control" rows="5" id="description_reason"></textarea>
                </div>
            </div>
            <div class="modal-footer">           
                <button type="button" class="btn btn-default cancel">cancelar</button>
                <!--<button type="submit" class="btn btn-default send" data-dismiss="modal">anular</button>-->
                <button type="button" class="btn btn-primary send">anular</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/include/footerHome.jsp" />
<script>
    // Tabla dinamica
    $(document).ready(function () {

        if ($('#tableLiquidation td').length > 0) {
            $('#tableLiquidation').DataTable({
                aLengthMenu: [300, 500],
                scrollY: 400,
                scrollX: true,
                columnDefs: [{targets: [0], visible: false},
                    {targets: [1], visible: false},
                    {targets: [2], visible: false},
                    {targets: [3], visible: false},
                    {targets: [6], visible: false},
                    {className: "dt-center", targets: "_all"}],
                searching: true,
                bAutoWidth: false,
                bInfo: false,
                paging: false
            });
        }

        $('#tableLiquidation button.annul').click(function (e) {
            $('#myModalReason').attr("data-id", $(this).data("id"));
            $('#text_reason').text("Explique la razón por la cual desea anular la liquidación #" + $(this).data("id"));
            $('body').addClass('modal-open');
            $('body').append('<div class="modal-backdrop fade in"></div>');
            $('#myModal').fadeIn(0, function () {
                $('#myModal').addClass("in");
            });

        });

        $('#myModal .question-yes').click(function (e) {
            $('#myModal').removeClass("in");
            $('#myModalReason').fadeIn(0, function () {
                $('#myModalReason').addClass("in");
            });
        });
        $('.modal .cancel').click(function (e) {
            closeModalWindows();
        });

        $('#myModalReason .send').click(function (e) {

            var reasonText = $('textarea#description_reason').val();
            if ((reasonText.trim()) === "" && (reasonText.trim()).length >= 10) {
                alert("Debe especificar una razón por la cual desea realizar el procedimiento");
                return null;
            }
            closeModalWindows();
            console.log($('textarea#description_reason'));
            $.post("/RDW1/anularLiquidacion",
                    {
                        id: $('#myModalReason').attr('data-id'),
                        reasonDescription: reasonText
                    },
                    function (response) {
                        if (response.anulada) {
                            var iButton = $('button.annul[data-id=' + response.id + ']')[0];
                            iButton.closest('div').remove();
                            var tdEstado = $('td.estado[data-id=' + response.id + ']')[0];
                            tdEstado.innerHTML = "<span class='label label-danger'>Anulada</span>";
                            $('textarea#description_reason').val("");
                        } else {
                            alert("Hubo un error intentando anular la liquidación . Intentelo nuevamente o comuniquese con el administrador del sistema");
                        }
                    });
        });
    });

    function closeModalWindows() {
        $('.modal').removeClass("in");
        $('.modal-backdrop').remove();
        $('body').removeAttr("class");
        $('.modal').css("display", "none");
        $('textarea#description_reason').val("");
    }
</script>
<jsp:include page="/include/end.jsp" />
