<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">        
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Reporte consolidado despacho</h4></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-2">Ruta</label>
                        <div class="col-sm-2">${parametrosReporte.nombreRuta}</div>
                    </div>                    
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-2">Fecha de inicio</label>
                        <div class="col-sm-2">${parametrosReporte.fechaInicioStr}</div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-2">Fecha fin</label>
                        <div class="col-sm-2">${parametrosReporte.fechaFinalStr}</div>
                    </div>
                    <div class="control-group">
                        <div class="col-sm-2">
                            <form action="<c:url value='/reporteWeb2Excel' />" method="post">
                                <input type="hidden" id="tipoReporte" name="tipoReporte" 
                                       value="${parametrosReporte.tipoReporte}">
                                <input type="hidden" id="nombreReporte" name="nombreReporte"
                                       value="${parametrosReporte.nombreReporte}">
                                <button class="button2link">Excel</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <table id="tablaConsolidadoDespacho" class="display td-center" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <td><strong>N° Vuelta</strong></td>
                        <td><strong>Fecha</strong></td>
                        <td><strong>Placa</strong></td>
                        <td><strong>N° Interno</strong></td>
                        <td><strong>Punto</strong></td>
                        <td><strong>Hora planificada</strong></td>
                        <td><strong>Hora real</strong></td>
                        <td><strong>Diferencia</strong></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lst_cdph}" var="dph">
                        <tr>
                            <td>${dph.numeroVuelta}</td>
                            <td>${dph.fecha}</td>
                            <td>${dph.placa}</td>
                            <td>${dph.numeroInterno}</td>
                            <td>${dph.punto}</td>
                            <td>${dph.horaPlanificada}</td>
                            <td>                                
                                <c:if test="${dph.horaReal != null}">
                                    ${dph.horaReal}
                                </c:if>
                                <c:if test="${dph.horaReal == null}">
                                    -
                                </c:if>
                            </td>
                            
                            <c:if test="${dph.horaReal == null}">
                                <td class="lbl-gris">${dph.diferenciaTiempo}</td>
                            </c:if>
                            <c:if test="${dph.horaReal != null}">
                                <c:if test="${dph.diferencia == 0}">
                                    <td class="lbl-verde">${dph.diferenciaTiempo}</td>
                                </c:if>
                                <c:if test="${dph.diferencia < 0}">
                                    <td class="lbl-naranja">-${dph.diferenciaTiempo}</td>
                                </c:if>
                                <c:if test="${dph.diferencia > 0}">
                                    <td class="lbl-rojo">+${dph.diferenciaTiempo}</td>
                                </c:if>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>                
            </table>         
            
        </div>
    </section>
</div>
                    
<jsp:include page="/include/footerHome_.jsp" />                    
<script>
    
    $(document).ready(function(){
        
        $("#tablaConsolidadoDespacho").DataTable({
            "order": [1, "asc"],
            "paging": false,
            "ordering": false,
            "info": false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
    
</script>
<jsp:include page="/include/end.jsp" />

