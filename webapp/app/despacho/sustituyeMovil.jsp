
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />
<div class="col-lg-8 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <c:choose>
        <c:when test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">

            <section class="boxed padding-smin">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Nuevo Despacho</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ListadoDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/consultaDespacho.jsp">Despachos</a></li>
                    </c:if>                    
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                        <li role="presentation" class="active"><a href="#">Sustituir Veh&iacute;culo</a></li>
                    <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                        <li><a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                    </c:if>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">

                        <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                

                        <!-- Consulta de movil -->
                        <form id="form-consulta-movil" name="form-consulta-movil" action="<c:url value='/consultarPlanillaMovil' />" method="post">                              
                            <div class="row" style="margin-top: 10px;">
                                <div class="control-group">
                                    <label class="col-sm-2">Ruta</label>
                                    <div class="col-sm-4">
                                        <select id="sruta" name="sruta" class="selectpicker form-control" 
                                                data-live-search="true" data-container="body"> 
                                            <option value="">Seleccione una ruta</option>                                            
                                            <c:forEach items="${select.lstruta_despacho}" var="ruta">
                                                <option value="${ruta.id}">${ruta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>                            
                                <div class="control-group">
                                    <label class="col-sm-2">Fecha</label>
                                    <div class="col-sm-4">
                                        <input id="fecha-stc" name="fecha-stc" class="form-control" type="text" readonly="readonly">
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top: 10px;">                        
                                <div class="control-group">
                                    <label class="col-sm-2">Veh&iacute;culo</label>
                                    <div class="col-sm-4">
                                        <select id="smovil" name="smovil" class="selectpicker form-control"
                                                data-live-search="true" data-container="body"
                                                onchange="dph_consultarPlanillaMovil();">
                                            <option value="">Seleccione un veh&iacute;culo</option>
                                            <option value="NA">Sin veh&iacute;culo</option>
                                            <c:forEach items="${select.lstmovil}" var="movil">
                                                <option value="${movil.placa}">${movil.placa}</option>
                                            </c:forEach>
                                        </select>                                            
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label><input id="totalidad_vueltas" name="totalidad_vueltas" type="checkbox"
                                                  onclick="dph_consultarPlanillaMovil();">&nbsp;Mostrar totalidad de vueltas</label>
                                </div>
                                <!--
                                <div class="control-group">
                                    <input class="btn" type="button" value="Consultar veh&iacute;culo" style="width: 142px;"
                                           onclick="dph_consultarPlanillaMovil();">
                                </div>
                                -->
                            </div>
                        </form>
                              
                        <!-- <hr> -->                              
                        
                    </div>
                </div>
            </section>

            <div style="height: 20px;"></div>                

            <div class="boxed padding">
                <table id="tablaPlanillaMovil" class="display cols-center" cellspacing="0" width="100%">
                    <thead>                        
                        <tr>
                            <th class="col-hidden-2"></th>     
                            <th class="col-hidden-2"></th>
                            <th> 
                                <input id="btn_check"   type="button" onclick="dph_checkVueltas(true);"  value="+"> 
                                <input id="btn_nocheck" type="button" onclick="dph_checkVueltas(false);" value="-" class="no-display"> 
                                N° vuelta</th>
                            <th>Veh&iacute;culo</th>                            
                            <th>Fecha</th>
                            <th>Ruta</th>                            
                            <th>Hora inicio</th>
                            <th>Hora fin</th>
                        </tr>
                    </thead>
                    <tbody id="tbodyid">                        
                    </tbody>
                </table>
            </div> 
            
            <div style="height: 20px;"></div>                
            
            <section class="boxed padding-smin">                
                <div class="tab-pane padding-smin">
                    <form id="form-sustituye-movil" name="form-sustituye-movil" action="<c:url value='/sustituirMovil' />" method="post">
                        <div class="row">
                            <div class="control-group">
                                <label class="col-sm-2">Sustituir por veh&iacute;culo</label>
                                <div class="col-sm-4">
                                    <select id="nsmovil" name="nsmovil" class="form-control"
                                            data-live-search="true" data-container="body">
                                        <option value="">Seleccione un veh&iacute;culo</option>
                                        <option value="NA">Sin veh&iacute;culo</option>
                                        <c:forEach items="${select.lstmovil}" var="movil">
                                            <option value="${movil.placa}">${movil.placa}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <input class="btn" type="button" value="Sustituir veh&iacute;culo" style="width: 142px;"
                                       onclick="dph_sustituirMovil();">
                            </div>
                        </div>
                        <input type="hidden" id="ruta_s"  name="ruta_s">
                        <input type="hidden" id="fecha_s" name="fecha_s">
                        <input type="hidden" id="movil_s" name="movil_s">
                        <input type="hidden" id="nregistros_s" name="nregistros_s">
                        <input type="hidden" id="lst_vueltas" name="lst_vueltas">
                    </form>                
                </div>
            </section>
            
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script>
    $(document).ready(function () {

        var fecha = $('#fecha-stc').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            startDate: new Date(),
            language: 'es'
        }).on('changeDate', function (e) {
            fecha.datepicker('hide');
        });
        
        $('#sruta').selectpicker({
            size: 5
        });
        
        $('#smovil').selectpicker({
            size: 5
        });
        
        $('#nsmovil').selectpicker({
            size: 5
        });
        
        $('#tablaPlanillaMovil').DataTable({
            "bLengthChange": false,
            "showNEntries": false,
            "bSort": false,
            "searching": false,
            "scrollX": true,            
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
</script>
<jsp:include page="/include/end.jsp" />
