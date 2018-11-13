
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Reporte cumplimiento de ruta por conductor</h4></label>
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
                        <c:choose>
                            <c:when test="${parametrosReporte.nombreRuta == null ||
                                            parametrosReporte.nombreRuta == ''}">
                                <div class="col-sm-3">Todas las rutas</div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-sm-3">${parametrosReporte.nombreRuta}</div>
                            </c:otherwise>
                        </c:choose>                                                                              
                    </div>
                    <div class="control-group">
                        <label class="col-sm-3">Meta</label>
                        <div class="col-sm-3">${parametrosReporte.meta}%</div>
                    </div>
                </div>
                <!--
                <div class="row">
                    <div class="control-group">
                        <div class="col-sm-3">
                            <form action="c:url value='/reporteWeb2Excel' />" method="post">
                                <input type="hidden" id="tipoReporte" name="tipoReporte" 
                                       value="{parametrosReporte.tipoReporte}">
                                <input type="hidden" id="nombreReporte" name="nombreReporte"
                                       value="{parametrosReporte.nombreReporte}">
                                <button class="button2link">Excel</button>
                            </form>
                        </div>
                    </div>
                </div> -->
            </div>
            
            <table id="tablaCumplimientoRuta" class="display td-center" cellspacing="0" width="100%">
                <thead>
                    <tr>                        
                        <td><strong>Conductor</td>
                        <td><strong>Puntos realizados</strong></td>
                        <td><strong>Puntos cumplidos</strong></td>
                        <td><strong>Porcentaje cumplido (%)</strong></td>
                        <td><strong>Meta (%)</strong></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lst_crcd}" var="crcd">
                        <tr>
                            <td>${crcd.nombreConductores}</td>
                            <td>${crcd.puntosRealizados}</td>
                            <td>${crcd.puntosCumplidos}</td>
                            
                            <c:if test="${crcd.porcentajeCumplido < parametrosReporte.meta_real}">
                                <td class="error-color">${crcd.porcentajeCumplido * 100}</td>
                            </c:if>
                            <c:if test="${crcd.porcentajeCumplido >= parametrosReporte.meta_real}">
                                <td>${crcd.porcentajeCumplido * 100}</td>
                            </c:if>
                            
                            <td>${parametrosReporte.meta}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                        <tr>
                            <td><strong>${totales.totalRegistros} Conductores</strong></td>
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
            order: [3, "desc"],
            bAutoWidth: false,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
    
</script>
<jsp:include page="/include/end.jsp" />
