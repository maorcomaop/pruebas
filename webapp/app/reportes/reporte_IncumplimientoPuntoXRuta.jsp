
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-6 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REPORTES</h1>    
    <section class="boxed padding-smin">        
        <div class="tab-content">
            
            <div class="padding-smin">
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-8"><h4>Incumplimiento de puntos control por ruta</h4></label>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Fecha de inicio</label>
                        <div class="col-sm-2">${parametrosReporte.fechaInicioStr}</div>
                    </div>
                    <div class="control-group">
                        <label class="col-sm-3">Fecha fin</label>
                        <div class="col-sm-2">${parametrosReporte.fechaFinalStr}</div>
                    </div>
                </div>
                <div class="row">
                    <div class="control-group">
                        <label class="col-sm-3">Ruta</label>
                        <div class="col-sm-2">${parametrosReporte.nombreRuta}</div>
                    </div>
                </div>
            </div>
                        
            <table id="tablaIncumplimientoPunto" class="display td-center" cellspacing="0" width="100%">
                <thead>
                    <tr>                        
                        <td><b>Fecha</b></td>
                        <td><b>Punto control</b></td>
                        <td><b>Indice de incumplimiento</b></td>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="indiceTotal" value="0" />
                    <c:forEach items="${lst_pnr}" var="pnr">
                        <tr>
                            <td>${pnr.fecha}</td>
                            <td>
                                <form target="_blank" action="<c:url value='/reporteIncumplimientoPuntoXVehiculo' />" method="post">
                                    <input type="hidden" id="idRuta" name="idRuta" value="${pnr.idRuta}">
                                    <input type="hidden" id="idPunto" name="idPunto" value="${pnr.idPunto}">
                                    <input type="hidden" id="nombreRuta" name="nombreRuta" value="${pnr.nombreRuta}">
                                    <input type="hidden" id="nombrePunto" name="nombrePunto" value="${pnr.nombrePunto}">                                    
                                    <input type="hidden" id="fecha" name="fecha" value="${pnr.fecha}">
                                    <button class="button2link">${pnr.nombrePunto}</button>
                                </form>                                
                            </td>
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
                </tbody>
                <tfoot>
                    <tr>
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
