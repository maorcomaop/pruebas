
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-12">   
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Liquidacion','Reportes'])}">
                <ul class="nav nav-tabs">        
                    <!--<li role="presentation" class="active"><a href="/RDW1/liquidacionTotalVehiculos">Liquidaci&oacute;n Total de Veh&iacute;culos</a></li>-->
                    <li role="presentation" ><a href="/RDW1/liquidacionPorLiquidador">Reporte de Liquidaci&oacute;n por Liquidador</a></li>
                    <li role="presentation" ><a href="/RDW1/consolidadoLiquidacion">Consolidado de Liquidaci&oacute;n</a></li>
                    <li role="presentation" ><a href="/RDW1/indicePasajeroKilometro">Reporte Indice Pasajero por Kil&oacute;metro</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">

                        <h2>Liquidación Total de Vehículos</h2>
                        <form class="form-horizontal" action="<c:url value='/reporteLiquidacionTotalVehiculos' />" method="post" target="_blank">

                            <div class="control-group">
                                <label class="control-label" for="inputName">Empresa</label>
                                <div class="controls">
                                    <select class="selectpicker" data-style="btn-primary" name="empresa_id" id="company">
                                        <option value="0">Seleccione la empresa</option>
                                        <c:forEach items="${select.lstempresa}" var="empresa">
                                            <option value="${empresa.id}">${empresa.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">                               
                                <label class="control-label" for="inputName">Fecha inicio</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="fecha_inicio" id="dpd1">
                                </div>                  
                            </div>
                            <br>
                            <div class="control-group">                            
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonLiquidacion" name="pdf" value="Generar">
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
    }).on('changeDate', function (e) {
        checkin.hide();
    }).data('datepicker');

</script>
<jsp:include page="/include/end.jsp" />