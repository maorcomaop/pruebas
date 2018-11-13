
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Reporte cumplimiento de ruta</h4></label>
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
                        <label class="col-sm-3">Ruta</label>
                        <div class="col-sm-3">${parametrosReporte.nombreRuta}</div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-3">Meta</label>
                        <div class="col-sm-3">${parametrosReporte.meta}%</div>
                    </div>
                </div>
                <div class="row">
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
                                <input type="hidden" id="idRuta" name="idRuta"
                                       value="${parametrosReporte.idRuta}">
                                <input type="hidden" id="nombreRuta" name="nombreRuta"
                                       value="${parametrosReporte.nombreRuta}">
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
            
            <table id="tablaCumplimientoRuta" class="display td-center" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <td><strong>Veh&iacute;culo</strong></td>
                        <td><strong>Conductor(es</strong>)</td>
                        <td><strong>Puntos realizados</strong></td>
                        <td><strong>Puntos cumplidos</strong></td>
                        <td><strong>Porcentaje cumplido (%)</strong></td>
                        <td><strong>Meta (%)</strong></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lst_crxv}" var="crxv">
                        <tr>                  
                            <td>${crxv.placa}</td>                        
                            <td>${crxv.nombreConductores}</td>
                            <td>${crxv.puntosRealizados}</td>
                            <td>${crxv.puntosCumplidos}</td>
                            
                            <c:if test="${crxv.porcentajeCumplido < parametrosReporte.meta_real}">
                                <td class="error-color">${crxv.porcentajeCumplido * 100}</td>
                            </c:if>
                            <c:if test="${crxv.porcentajeCumplido >= parametrosReporte.meta_real}">
                                <td>${crxv.porcentajeCumplido * 100}</td>
                            </c:if>
                            
                            <td>${parametrosReporte.meta}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                        <tr>
                            <td><strong>${totales.totalRegistros} Veh&iacute;culos</strong></td>
                            <td></td>
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
    
    $(document).ready(function() {
        
        $('#tablaCumplimientoRuta').DataTable({
            "bLengthChange": false,
            "showNEntries": false,
            /* "bSort": false, */
            order: [4, "desc"],
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
    
</script>
<jsp:include page="/include/end.jsp" />