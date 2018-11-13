
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">   
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO LIQUIDACI&Oacute;N</h1>
    <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(4)}">
                <ul class="nav nav-tabs">        
                    <li role="presentation" class="active"><a href="/RDW1/app/liquidacion/consultarLiquidacion.jsp">Consultar Liquidaci&oacute;n</a></li>
                        <c:choose>
                            <c:when test="${permissions.check(['Liquidacion','LiquidarVehiculo'])}">
                                <c:choose>
                                    <c:when test ="${(login.idempresa == 9)}">
                                    <li role="presentation" ><a href="/RDW1/app/liquidacion/nuevaLiquidacionFusa.jsp">Liquidar Veh&iacute;culo</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li role="presentation" ><a href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">Liquidar Veh&iacute;culo</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>                    
                        </c:choose>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">


                        <form class="form-inline" action="<c:url value='/consultarLiquidacion' />" method="post">
                            <div class="row"> 
                                <div class="col-sm-12 col-xs-12">

                                    <div class="controls col-sm-3 col-xs-12">
                                        <div class="row">
                                            <label class="control-label" for="inputName">Placa</label>
                                        </div>
                                        <div class="row">
                                            <select class="selectpicker" data-width="160px" title="Seleccione la placa del veh&iacute;culo" data-style="btn-primary" name="vehiculo"  id="placa_vh">

                                                <c:forEach items="${select.lstmovil}" var="movil">
                                                    <option value="${movil.id}">${movil.placa}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>                                        

                                    <div class="controls col-sm-3 col-xs-12">
                                        <div class="row">
                                            <label for="inputName">N&uacute;mero Interno</label>                                
                                        </div>
                                        <div class="row">
                                            <select class="selectpicker" data-style="btn-primary btn  btn-xs" title="Seleccione el n&uacute;mero interno" data-width="165px" name="numero_interno_vehiculo"  id="num_interno">                                        
                                                <c:forEach items="${select.lstmovil}" var="movil">
                                                    <option value="${movil.id}">${movil.numeroInterno}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="controls col-sm-3 col-xs-12">
                                        <div class="row">                                        
                                            <label for="fecha_inicio">Fecha Inicio</label>                                        
                                        </div>
                                        <div class="row">                                        
                                            <input type="text" class="form-control" name="fecha_inicio" id="dpd1" data-toggle="tooltip" title="Haga clic aqui para seleccionar la fecha de inicio">                                        
                                        </div>
                                    </div>

                                    <div class="controls col-sm-3 col-xs-12">                                        
                                        <div class="row">                                        
                                        </div>
                                        <label for="fecha_fin">Fecha Fin</label>             
                                        <div class="row">                                        
                                            <input type="text" class="form-control form_datetime" name="fecha_fin" id="dpd2" data-toggle="tooltip" title="Haga clic aqui para seleccionar la fecha de inicio">                                                                                                    
                                        </div>
                                    </div>                               
                                    <div class="col-sm-3 pull-right">
                                        <br>
                                        <input type="submit" class="btn btn-primary botonConsultaLiquidacion" value="Consultar" data-toggle="tooltip" title="Haga clic para consultar">
                                        <button type="submit" class="btn btn-primary " name="tipo_reporte" value="27" data-toggle="tooltip" title="Haga clic para exportar los datos a Excel">Excel</button>                             
                                    </div> 

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


    <jsp:include page="/include/footerHome.jsp" />
    <script>
        var now = new Date();
        now.setDate(now.getDate() - 1);
        $('#dpd1').datetimepicker({format: 'YYYY/M/D', useCurrent: true, defaultDate: now});
        $("#dpd1").on("dp.change", function (e) {
            $('#dpd2').data("DateTimePicker").minDate(e.date);
        });

        $('#dpd2').datetimepicker({format: 'YYYY/M/D', useCurrent: false, defaultDate: new Date()});
        $("#dpd2").on('dp.change', function (e) {
            $('#dpd1').data("DateTimePicker").maxDate(e.date);
        });

        $(document).ready(function () {

        //$('[data-toggle="tooltip"]').tooltip(); 
        

            /*Carga los items del combo placa segun la busqueda del combo numero interno*/
            $("#placa_vh").change(function () {
                $('#num_interno.selectpicker').selectpicker('val', $(this).val());
            });

            /*Carga los items del combo numero interno segun la busqueda del combo placa*/
            $("#num_interno").change(function () {
                $('#placa_vh.selectpicker').selectpicker('val', $(this).val());
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
