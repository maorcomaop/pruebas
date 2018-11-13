<!--PAGINA QUE SE IMPRIME CUANDO SE CARGAN LAS VUELTAS QUE HA REALIZADO UN VEHICULO EN UN DÍA-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
    var totalDistanciaN = 0.0;
    var pasajerosRegistradosN = 0;
    var numeracionFinalN = 0;
    var numeracionInicialN = 0;
</script>
<c:choose>
    <c:when test="${vueltas.size() > 0}">       
        <input type="hidden" id="cantidadPasajerosRegistradosN" class="" >
        <input type="hidden" id="numeracionFinalVehiculoN" class="" >        
        <div class="table-responsive">
            <table id="tableVueltas"   class="compact hover stripe style_tbl_vueltas" width="100%" cellspacing="0">
                <thead>                            
                    <tr>
                        <th>Vuelta</th>
                        <th>Fecha/Hora de ingreso</th>
                        <th>Num Inicial</th>
                        <th>Num Final</th>                    
                        <th>Tarifa</th>
                        <th>Distancia en KM</th>
                        <th>Pasajeros</th>
                        <th>Total</th>
                    </tr>    
                </thead>    
                <tbody>                          
                    <c:forEach items="${vueltas}" var="vuelta" begin="0" end="${vueltas.size()}" varStatus="p">
                        <tr>
                            <c:if test="${p.count == 1}">
                        <script>
                            numeracionInicialN = ${vuelta.numeracion_base_salida};
                        </script>
                    </c:if>
                    <td>${vuelta.contador_vueltas}</td>
                    <td>${vuelta.fecha_ingreso}/${vuelta.hora_ingreso}</td>
                    <td>${vuelta.numeracion_base_salida}</td>
                    <td>${vuelta.numeracion_de_llegada}</td>    
                    <script>
                        $(".tarifaN").html($("#simboloMoneda").val() + " " + $("#comboTarifaN option:selected").text());
                        totalDistanciaN = totalDistanciaN + ${vuelta.distancia};
                    </script>
                    <td align="right" class="tarifaN" ></td>
                    <td align="right">${vuelta.distancia}</td>

                    <c:choose>
                        <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                            <td align="right" class="pasajerosVuelta">-</td>
                        </c:when>
                        <c:otherwise>
                            <td align="right" class="pasajerosVuelta">${vuelta.numeracion_de_llegada - vuelta.numeracion_base_salida}</td>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${p.count == (vueltas.size())}">
                        <script>
                            numeracionFinalN = ${vuelta.numeracion_de_llegada};

                            <c:choose>
                                <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                            pasajerosRegistradosN = ${total_pasajeros_gps};
                                </c:when>
                                <c:otherwise>
                            pasajerosRegistradosN = numeracionFinalN - numeracionInicialN;
                                </c:otherwise>
                            </c:choose>
                        </script>
                    </c:if>    
                    
                    <td align="right" class="totalVuelta"></td>
                    </tr>                                                                
                </c:forEach>
                </tbody>
            </table> 
        </div>
        <c:choose>
            <c:when test ="${relacionVehiculoConductor.idRelacionVehiculoConductor == 0}">
                <script>
                    $("#btn-vehiculo-conductor").text("Relacione un Conductor");
                </script>
            </c:when>
            <c:otherwise>         
                <script>
                    $("#btn-vehiculo-conductor").text("${conductorVehiculo.nombre} ${conductorVehiculo.apellido}");
                </script>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <h5 class="title-area">NO EXISTE INFORMACION</h5> 
    </c:otherwise>
</c:choose>
<jsp:include page="/include/footerHomeLiq.jsp" ></jsp:include>
    <script>
        $("#distN").html(parseFloat(totalDistanciaN.toLocaleString('de-DE', {style: 'decimal', decimal: '3'})) + "&nbsp;<span class='label label-success'>Km</span>");
        $("#distanciaRecorridaN").val(parseFloat(totalDistanciaN));
        $("#cantidadPasajerosRegistradosN").val(parseFloat(pasajerosRegistradosN));
        $("#numeracionFinalVehiculoN").val(parseFloat(numeracionFinalN));

        $("#btn-vehiculo-conductor").attr("data-reveco-id", "${relacionVehiculoConductor.idRelacionVehiculoConductor}");
        $("#btn-vehiculo-conductor").attr("data-co-id", "${relacionVehiculoConductor.idConductor}");
        $("#btn-vehiculo-conductor").attr("data-ve-id", "${placaVehiculoId}");
        $("#PlacaN").text("${placaVehiculo}");
        $("#NIntN").text("${nInterno}");
        $("#numeracionInicial").text("${numeracion_inicial}");
        $("#numeracionFinal").text("${numeracion_final}");
        $("#notaAlerta").html("${liquidacion_relativa}");
        
        var reportedLapsN = <c:out value="${vueltas.size() > 0 ? vueltas.size() : 0}"/>;
        if (reportedLapsN === 0) {
            $("#export-btn").addClass("obfuscate");
        }
</script>



