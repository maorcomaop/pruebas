<!--PAGINA QUE SE IMPRIME CUANDO SE CARGAN LAS VUELTAS QUE HA REALIZADO UN VEHICULO EN UN DÍA-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    var numeracionInicialF = 0;
    var numeracionFinalF = 0;    
    var pasajerosRegistradosF=0;
    var totalDistanciaF = 0;    
    var intervalValueF = 0;
</script>

<c:choose>
    <c:when test="${vueltas.size() > 0}">        
        <input type="hidden" id="cantidadPasajerosRegistradosF" class="" />
        <input type="hidden" id="numeracionFinalVehiculoF" class="" />
        
        <table id="tableVueltasF"   class="compact hover stripe style_tbl_vueltas" width="100%" cellspacing="0">
            <thead>                            
                <tr>
                    <th>No. Vueltas</th>
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
                <tr>                                
                    <c:forEach items="${vueltas}" var="vuelta" begin="0" end="${vueltas.size()}" varStatus="p">
                        <c:if test="${p.count == 1}">
                    <script>
                        numeracionInicialF = ${vuelta.numeracion_base_salida};
                        //console.log("valor de inicial ----> " + numeracionInicial);
                    </script>
                </c:if>
                <td align="center" >${vuelta.contador_vueltas}</td>
                <td align="center" >${vuelta.fecha_ingreso}/${vuelta.hora_ingreso}</td>
                <td>${vuelta.numeracion_base_salida}</td>
                <td>${vuelta.numeracion_de_llegada}</td>
                <script>$(".tarifaF").html($("#simboloMoneda").val() + " " + $("#comboTarifaF option:selected").text());
                totalDistanciaF = totalDistanciaF + ${vuelta.distancia};</script>
                <td class="tarifaF" align="center" style="width:10%"></td>                
                <td align="center">${vuelta.distancia}</td>    
                
                <c:choose>
                    <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                        <td class= "pasajerosVueltaF i_${p.count}" align="center">-</td>
                    </c:when>
                    <c:otherwise>
                        <td class= "pasajerosVueltaF i_${p.count}" align="center">${vuelta.numeracion_de_llegada - vuelta.numeracion_base_salida}</td>
                    </c:otherwise>
                </c:choose>
                        
                <c:if test="${p.count == (vueltas.size())}">
                    <script>
                        numeracionFinalF = ${vuelta.numeracion_de_llegada}; 
                        
                        <c:choose>
                            <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                                pasajerosRegistradosF = ${total_pasajeros_gps};
                            </c:when>
                            <c:otherwise>
                                pasajerosRegistradosF = numeracionFinalF - numeracionInicialF;
                            </c:otherwise>
                        </c:choose>          
                    </script>
                </c:if>
                    
                <c:choose>
                    <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                        <td align="right" class="totalVuelta"></td>
                    </c:when>
                    <c:otherwise>
                        <td align="right" class="totalVuelta"></td>
                    </c:otherwise>
                </c:choose>
                    
                    
                <td class= "totalVueltaF i_${p.count}"align="center"></td>
            </tr>                                                                
        </c:forEach>
    </tbody>      
    
</table> 
    <c:choose>
     <c:when test ="${relacionVehiculoConductorF.idRelacionVehiculoConductor == 0}">
         <script>
         $( "#btn-vehiculo-conductorF" ).text("Relacione un Conductor");
         </script>
     </c:when>
     <c:otherwise>         
         <script>
         $( "#btn-vehiculo-conductorF" ).text("${conductorVehiculoF.nombre} ${conductorVehiculoF.apellido}");
         </script>
     </c:otherwise>
 </c:choose>
         
</c:when>
<c:otherwise>
    <h5 class="title-area">NO EXISTE INFORMACION</h5> 
</c:otherwise>
</c:choose>
<jsp:include page="/include/footerHomeLiqF.jsp" />
<script>        
   $("#distF").html(totalDistanciaF.toLocaleString('de-DE', { style: 'decimal', decimal: '3' })+"&nbsp;<span class='label label-success'>Km</span>");
   $("#cantidadPasajerosRegistradosF").val(parseFloat(pasajerosRegistradosF));
   $("#numeracionFinalVehiculoF").val(parseFloat(numeracionFinalF)); 
   $("#distanciaRecorridaF").val(totalDistanciaF);   
   $( "#btn-vehiculo-conductorF" ).attr( "data-reveco-id", "${relacionVehiculoConductorF.idRelacionVehiculoConductor}" );
   $( "#btn-vehiculo-conductorF" ).attr( "data-co-id", "${relacionVehiculoConductorF.idConductor}" );
   $( "#btn-vehiculo-conductorF" ).attr( "data-ve-id", "${placaVehiculoIdF}" );
   $( "#PlacaF" ).text( "${placaVehiculoF}" );
   $( "#NIntF" ).text( "${nInternoF}" );
   $("#numeracionInicial").text("${numeracion_inicial}")
   $("#numeracionFinal").text("${numeracion_final}");
   $("#notaAlerta").html("${liquidacion_relativa}");
   
    intervalValueF = 0;
//    intervalValueF = setInterval(function () {
//        intervalingFusa();
//    }, 250);
   var reportedLapsF = <c:out value="${vueltas.size() > 0 ? vueltas.size() : 0}"/>;
        if (reportedLapsF === 0) {
            $("#btn_guardar_lq").addClass("obfuscate");
        }
</script>

