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
                                <input type="hidden" id="listaVehiculosPlaca" name="listaVehiculosPlaca"
                                       value="${parametrosReporte.listaVehiculosPlaca}">
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
                        <c:forEach items="${lst_pto}" var="pto">
                            <td><strong>${pto}</strong></td>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="separador" value=""></c:set>
                    <c:forEach items="${lst_pll}" var="pll">
                        
                        <c:if test="${pll.numeroVuelta == 1}">
                            <c:set var="separador" value="lbl-separador"></c:set>
                        </c:if>
                        <c:if test="${pll.numeroVuelta != 1}">
                            <c:set var="separador" value=""></c:set>
                        </c:if>
                        <tr>
                            <td class="${pll.estiloFilaStr} ${separador}">${pll.numeroVuelta}</td>
                            <td class="${pll.estiloFilaStr} ${separador}">${pll.fecha}</td>
                            <td class="${pll.estiloFilaStr} ${separador}">${pll.placa}</td>
                            <td class="${pll.estiloFilaStr} ${separador}">${pll.numeroInterno}</td>                           
                            
                            <c:forEach items="${pll.detalle}" var="pd">                                
                                <c:if test="${pd.horaReal == null}">
                                    <td class="lbl-gris ${separador}">-</td>
                                </c:if>
                                <c:if test="${pd.horaReal != null}">
                                    <c:if test="${pd.diferencia == 0}">
                                        <td class="lbl-verde ${separador}">${pd.diferenciaTiempo}</td>
                                    </c:if>
                                    <c:if test="${pd.diferencia < 0}">
                                        <td class="lbl-naranja ${separador}">-${pd.diferenciaTiempo}</td>
                                    </c:if>
                                    <c:if test="${pd.diferencia > 0}">
                                        <td class="lbl-rojo ${separador}">+${pd.diferenciaTiempo}</td>
                                    </c:if>
                                </c:if>
                            </c:forEach>                                        
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

