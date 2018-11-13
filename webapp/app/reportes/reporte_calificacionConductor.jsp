
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Calificaci&oacute;n de conductores</h4></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group" style="margin-left: 24px;">
                        <table>
                            <tbody>
                                <tr>
                                    <td><b>Configuraci&oacute;n utilizada</b></td>
                                    <td colspan="2" style="padding-left: 12px;">${ccc.nombre}</td>
                                </tr>
                                <tr>
                                    <td><b>D&iacute;as consultados</b></td>
                                    <td style="padding-left: 12px;">${parametrosReporte.fechaInicioStr} / ${parametrosReporte.fechaFinalStr}</td>
                                    <td style="padding-left: 12px;">
                                        <form action="<c:url value='/reporteCalificacionConductor' />" method="post">
                                            <input type="hidden" id="tituloReporte" name="tituloReporte" 
                                                   value="${parametrosReporte.tituloReporte}">
                                            <input type="hidden" id="tipoReporte" name="tipoReporte" 
                                                   value="${parametrosReporte.tipoReporte}">
                                            <input type="hidden" id="nombreReporte" name="nombreReporte"
                                                   value="${parametrosReporte.nombreReporte}">
                                            <input type="hidden" id="fechaInicio" name="fechaInicio"
                                                   value="${parametrosReporte.fechaInicioStr}">
                                            <input type="hidden" id="fechaFinal" name="fechaFinal"
                                                   value="${parametrosReporte.fechaFinalStr}">
                                            <input type="hidden" id="listaConductores" name="listaConductores"
                                                   value="${parametrosReporte.listaConductores}">
                                            <button class="button2link">Excel</button>
                                        </form>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div style="margin-top: 10px; margin-left: 42px;">
                    <div class="row">
                        <div class="control-group">                        
                            <table>
                                <thead>
                                    <tr>
                                        <th colspan="2" class="text-align-center">Calificadores</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Calificaci&oacute;n m&aacute;xima</td>
                                        <td class="text-align-center">${ccc.puntajeMaximo}</td>
                                    </tr>
                                    <tr>
                                        <td>Puntos por velocidad m&aacute;xima</td>
                                        <td class="text-align-center">${ccc.puntosPorVel}</td>
                                    </tr>
                                    <tr>
                                        <td>Puntos por incumplimiento de ruta</td>
                                        <td class="text-align-center">${ccc.puntosPorRuta}</td>
                                    </tr>
                                    <tr>
                                        <td>Puntos por d&iacute;a no laborado</td>
                                        <td class="text-align-center">${ccc.puntosPorDiaNoLaborado}</td>
                                    </tr>
                                    <tr>
                                        <td>Puntos por productividad</td>
                                        <td class="text-align-center">
                                            +${ccc.puntosIpkMayor} / -${ccc.puntosIpkMenor}
                                        </td>
                                    </tr>
                                    <tr><td colspan="2" style="padding-top: 8px;"></td></tr>
                                    <tr>
                                        <td colspan="2" class="text-align-center"><b>Valores l&iacute;mite</b></td>
                                    </tr>
                                    <tr>
                                        <td>Velocidad m&aacute;xima permitida</td>
                                        <td class="text-align-center">${ccc.velocidadMaxima}</td>
                                    </tr>
                                    <tr>
                                        <td>Porcentaje ruta m&iacute;nimo</td>
                                        <td class="text-align-center">${ccc.porcentajeRuta}%</td>
                                    </tr>
                                </tbody>
                            </table>                        
                        </div>
                    </div>
                </div>
            </div>
            
            <table id="tablaRendimiento" class="display cols-center" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <th>Nombre conductor</th>
                        <th>Calificaci&oacute;n m&aacute;xima</th>
                        <c:if test="${ccc.puntosPorVel > 0}">
                            <th>Puntos por exceso velocidad</th>
                        </c:if>
                        <c:if test="${ccc.puntosPorRuta > 0}">
                            <th>Puntos por incumplimiento de ruta</th>
                        </c:if>
                        <c:if test="${ccc.puntosPorDiaNoLaborado > 0}">
                            <th>Puntos por d&iacute;as no laborados</th>
                        </c:if>
                        <c:if test="${ccc.puntosIpkMayor > 0 || ccc.puntosIpkMenor > 0}">
                            <th>Puntos por productividad</th>
                        </c:if>
                        <th>Calificaci&oacute;n alcanzada</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lst_rc}" var="rc">
                        <tr>
                            <td>${rc.nombre}</td>
                            <td>${ccc.puntajeMaximo}</td>
                            <c:if test="${ccc.puntosPorVel > 0}">
                                <td>${rc.excesoVelocidad * ccc.puntosPorVel}</td>
                            </c:if>
                            <c:if test="${ccc.puntosPorRuta > 0}">
                                <td>${rc.incumplimientoRuta * ccc.puntosPorRuta}</td>
                            </c:if>
                            <c:if test="${ccc.puntosPorDiaNoLaborado > 0}">
                                <td>${rc.diasNoLaborados * ccc.puntosPorDiaNoLaborado}</td>
                            </c:if>
                            <c:if test="${ccc.puntosIpkMayor > 0 || ccc.puntosIpkMenor > 0}">
                                <c:choose>
                                    <c:when test="${rc.ipk >= rc.ipkEmpresa}">
                                        <td>+${ccc.puntosIpkMayor}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>-${ccc.puntosIpkMenor}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <td>${rc.rendimiento}</td>
                        </tr>
                    </c:forEach>
                </tbody>                
            </table>
        </div>
    </section>
</div>
                        
<jsp:include page="/include/footerHome_.jsp" />
<script>
    
    $(document).ready(function() {
        
        $('#tablaRendimiento').DataTable({
            "bLengthChange": false,
            "showNEntries": false,
            "bSort": false,
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
    
</script>
<jsp:include page="/include/end.jsp" />