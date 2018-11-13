
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-12">   

    <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Liquidacion','Reportes'])}">
                <ul class="nav nav-tabs">        
                    <!--<li role="presentation" ><a href="/RDW1/liquidacionTotalVehiculos">Liquidación Total de Veh&iacute;culos</a></li>-->                    
                    <li role="presentation" class="active"><a href="/RDW1/consolidadoLiquidacion">Consolidado de Liquidaci&oacute;n</a></li>
                    <li role="presentation" ><a href="/RDW1/liquidacionPorLiquidador">Reporte de Liquidaci&oacute;n por Liquidador</a></li>
                    <li role="presentation" ><a href="/RDW1/indicePasajeroKilometro">Reporte Indice Pasajero por Kil&oacute;metro</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">

                        <h2>Consolidado de Liquidación</h2>
                        <form class="form-horizontal" action="<c:url value='/reporteConsolidadoLiquidacion' />" method="post">

                            <div class="control-group">                                
                                <label class="control-label" for="inputName">Tipo de Reporte</label>
                                <div class="controls">                            
                                    <select class="selectpicker" data-style="btn-primary" name="tipo_reporte" id="tipoReporte">
                                        <option value="0">Seleccione el tipo de reporte</option>
                                        <option value="19">Consolidado Liquidación</option>
                                        <option value="20">Consolidado Vehículos no Liquidados</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                               
                                <label class="control-label" for="fecha_inicio">Fecha inicio</label>
                                <div class="controls">
                                    <input type="text" class="form-control form_datetime" name="fecha_inicio" id="dpd1">
                                </div>                  
                            </div>                  
                            <div class="control-group">
                                <label class="control-label" for="fecha_fin">Fecha fin</label>
                                <div class="controls">
                                    <input type="text" class="form-control form_datetime" name="fecha_fin" id="dpd2">                            
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                            
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonLiquidacion" value="Generar">
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

    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var checkin = $('#dpd1').datetimepicker({
        format: 'YYYY/M/D',
        useCurrent:true        
    }).on('changeDate', function (ev) {
        if (ev.date.valueOf() > checkout.date.valueOf()) {
            var newDate = new Date(ev.date);
            newDate.setDate(newDate.getDate() + 1);
            checkout.setDate(newDate);
        }
    }).data('datetimepicker');

    var checkout = $('#dpd2').datetimepicker({
       format: 'YYYY/M/D',
        useCurrent:true
    }).on('changeDate', function (ev) {
        checkout.hide();
    }).data('datetimepicker');

</script>
<jsp:include page="/include/end.jsp" />