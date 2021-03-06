
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">        
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Reporte consolidado rutas</h4></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Fecha de inicio</label>
                        <div class="col-sm-3">${parametrosReporte.fechaInicioStr}</div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-3">Fecha fin</label>
                        <div class="col-sm-3">${parametrosReporte.fechaFinalStr}</div>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Meta</label>
                        <div class="col-sm-3">${parametrosReporte.meta}%</div>
                    </div>
                    <div class="control-group">
                        <div class="col-sm-3">
                            <form action="<c:url value='/reporteWeb2Excel' />" method="post">
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
                                <input type="hidden" id="meta" name="meta"
                                       value="${parametrosReporte.meta}">
                                <input type="hidden" id="metaReal" name="metaReal"
                                       value="${parametrosReporte.meta_real}">
                                <button class="button2link">Excel</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <table id="tablaConsolidadoRutas" class="display td-center" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <td><strong>Ruta</strong></td>
                        <td><strong>Puntos realizados</strong></td>
                        <td><strong>Puntos cumplidos</strong></td>
                        <td><strong>Porcentaje cumplido (%)</strong></td>
                        <td><strong>Meta (%)</strong></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lst_crc}" var="crc">
                        <tr>
                            <td>
                                <form target="_blank" action="<c:url value='/reporteCumplimientoRuta' />" method="post">
                                    <input type="hidden" id="idRuta" name="idRuta" value="${crc.idRuta}">
                                    <input type="hidden" id="nombreRuta" name="nombreRuta" value="${crc.nombreRuta}">
                                    <input type="hidden" id="fechaInicio" name="fechaInicio" value="${parametrosReporte.fechaInicioStr}">
                                    <input type="hidden" id="fechaFinal" name="fechaFinal" value="${parametrosReporte.fechaFinalStr}">
                                    <input type="hidden" id="meta" name="meta" value="${parametrosReporte.meta}">
                                    <input type="hidden" id="metaReal" name="metaReal" value="${parametrosReporte.meta_real}">
                                    <button class="button2link">${crc.nombreRuta}</button>
                                </form>
                            </td>
                            <td>${crc.puntosRealizados}</td>
                            <td>${crc.puntosCumplidos}</td>
                            
                            <c:if test="${crc.porcentajeCumplido < parametrosReporte.meta_real}">
                                <td class="error-color">${crc.porcentajeCumplido * 100}</td>
                            </c:if>
                            <c:if test="${crc.porcentajeCumplido >= parametrosReporte.meta_real}">
                                <td>${crc.porcentajeCumplido * 100}</td>
                            </c:if>
                            
                            <td>${parametrosReporte.meta}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td><strong>${totales.totalRegistros} Rutas</strong></td>
                        <td><strong>${totales.totalPuntosRealizados}</strong></td>
                        <td><strong>${totales.totalPuntosCumplidos}</strong></td>
                        <td><strong>${totales.totalPorcentajeCumplido * 100}</strong></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>         
            
        </div>
    </section>
</div>
                    
<jsp:include page="/include/footerHome_.jsp" />                    
<script>
    
    $(document).ready(function(){
        
        $("#tablaConsolidadoRutas").DataTable({
            "bLengthChange": false,
            "showNEntries": false,
            /* "bSort": false, */
            order: [3, "desc"],
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
    
</script>
<jsp:include page="/include/end.jsp" />
