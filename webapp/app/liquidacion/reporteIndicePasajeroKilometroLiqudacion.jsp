
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test ="${(login.idperfil == 1)}">        
        <jsp:include page="/include/headerHomeNew.jsp" />
        </c:when>
        <c:otherwise>
        <jsp:include page="/include/headerHome.jsp" />
        </c:otherwise>
    </c:choose>  
<div class="col-lg-12">   
    <!--FORMULARIO PARA VISUALIZAR REPORTE IPK-->
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Liquidacion','Consultar'])}">

                <ul class="nav nav-tabs">                            
                    
                    <!--<li role="presentation" ><a href="/RDW1/liquidacionTotalVehiculos">Liquidaci”n Total de Veh&iacute;culos</a></li>-->                    
                    <li role="presentation" ><a href="/RDW1/consolidadoLiquidacion">Consolidado de Liquidaci&oacute;n</a></li>
                    <li role="presentation" ><a href="/RDW1/liquidacionPorLiquidador">Reporte de Liquidaci&oacute;n por Liquidador</a></li>
                    <li role="presentation" class="active" ><a href="/RDW1/indicePasajeroKilometro">Reporte Indice Pasajero por Kil&oacute;metro</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">

                        <h2>Indice de pasajero por kilometro recorrido</h2>
                        <form class="form-horizontal" action="<c:url value='/reporteIndicePasajeroKilometro' />" method="post">
                            
                            <div class="control-group">                               
                                <label class="control-label" for="fecha_inicio">Fecha inicio</label>
                                <div class="controls">
                                    <input type="text" class="form-control " name="fecha_inicio" id="dpd1">
                                </div>                  
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label" for="fecha_fin">Fecha fin</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="fecha_fin" id="dpd2">                            
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                            
                                <div class="controls">                                                                        
                                    <!--<button type="submit" class="btn btn-primary botonIndicePasajeroKilometro" name="tipo_reporte" value="22">Generar</button>-->
                                    <button type="submit" class="btn btn-primary botonIndicePasajeroKilometro" name="tipo_reporte" value="22">Generar Excel</button>
                                </div>
                            </div>  
                        </form>   
                    </div>
                </div>
            </section>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome.jsp" />
<script>

    $('#dpd1').datetimepicker({format: 'YYYY/M/D', useCurrent:true});    
    $("#dpd1").on("dp.change", function (e) { 
        $('#dpd2').data("DateTimePicker").minDate(e.date); 
    });

    $('#dpd2').datetimepicker({ format: 'YYYY/M/D', useCurrent:false});            
    $("#dpd2").on('dp.change', function (e) { 
        $('#dpd1').data("DateTimePicker").maxDate(e.date); 
    });   
       
    

    $(document).ready(function () {
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