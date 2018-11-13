
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-6 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">        
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Incumplimiento de puntos control por conductor</h4></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Fecha</label>
                        <div class="col-sm-2">${parametrosReporte.fechaInicioStr}</div>
                    </div>                    
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Ruta</label>
                        <div class="col-sm-2">${parametrosReporte.nombreRuta}</div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-3">Punto</label>
                        <div class="col-sm-2">${parametrosReporte.nombrePunto}</div>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Veh&iacute;culo</label>
                        <div class="col-sm-2">${parametrosReporte.placa} - ${parametrosReporte.numeroInterno}</div>
                    </div>
                </div>
            </div>
                        
            <table id="tablaIncumplimientoPunto" class="display td-center" cellspacing="0" width="100%">
                <thead>
                    <tr>                        
                        <td><b>Fecha</b></td>                        
                        <td><b>Veh&iacute;culo</b></td>
                        <td><b>Conductor</b></td>
                        <td><b>Indice de incumplimiento</b></td>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="indiceTotal" value="0" />
                    <c:choose>
                        <c:when test="${fn:length(lst_pnrxc) == 0}">
                            <tr>
                                <td colspan="4">Veh&iacute;culo no relacionado con alg&uacute;n conductor.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>                        
                            <c:forEach items="${lst_pnrxc}" var="pnr">
                                <tr>
                                    <td>${pnr.fecha}</td>
                                    <td>${pnr.placa} - ${pnr.numeroInterno}</td>
                                    <td>${pnr.nombreConductor}</td>
                                    <c:choose>
                                        <c:when test="${pnr.indice >= 5}">
                                            <td class="error-color">${pnr.indice}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${pnr.indice}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                                <c:set var="indiceTotal" value="${pnr.indiceTotal}" />
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
                <tfoot>
                    <tr>
                        <td></td>
                        <td></td>                        
                        <td></td>
                        <c:if test="${indiceTotal > 0}">
                            <td>${indiceTotal}</td>
                        </c:if>
                        <c:if test="${indiceTotal <= 0}">
                            <td></td>                        
                        </c:if>
                    </tr>
                </tfoot>
            </table>
        </div>
    </section>
</div>

<jsp:include page="/include/footerHome_.jsp" />                    
<script>
    
    $(document).ready(function(){
        
        $("#tablaIncumplimientoPunto").DataTable({
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
