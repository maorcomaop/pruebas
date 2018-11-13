
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerappRutaNew.jsp" />

<!-- Body container -->
<div class="col-lg-12 css-gps-tracking over">
    <!-- <h1 class="display-3 bg-info text-center">M&Oacute;DULO RASTREO</h1> -->
    <c:choose>
        <c:when test="${permissions.check(['SeguimientoGPS'])}">

            <div class="row">
                <div class="col-sm-2">                    
                    <div class="control-group">
                        <ul class="testing">
                            <li ><a href="/RDW1/app/seguimiento/gps2.jsp"><strong>Ver mapa</strong></a></li>
                            <li ><a href="/RDW1/app/seguimiento/infogps.jsp">M&oacute;viles</a></li>
                                <c:if test="${permissions.check(219)}">
                                <li ><a href="/RDW1/app/seguimiento/comandos.jsp">Comandos</a></li>
                                </c:if>
                        </ul>
                    </div>
                    <div class="control-group">
                        <div>M&oacute;vil</div>
                        <select id="splaca" name="splaca" class="selectpicker form-control" 
                                data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                data-container="body" onchange="gps_reset('sgrupo'); gps_haySeleccionGrupo(false); gps_cambioParametros();">
                            <option value="" selected>Todos</option>
                            <c:forEach items="${select.lstmovil}" var="veh">
                                <option value="${veh.placa},${veh.numeroInterno},${veh.capacidad}">${veh.placa} - ${veh.numeroInterno}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="control-group">
                        <div>Grupo m&oacute;vil o ruta</div>
                        <select id="sgrupo" name="sgrupo" class="selectpicker form-control"
                                data-live-search="true" data-actions-box="true" data-selected-text-format="values"                                                        
                                data-container="body" onchange="gps_reset('splaca'); gps_haySeleccionGrupo(true); gps_cambioParametros();">
                            <option value="" style="background: #46b8da;">Seleccione grupo o ruta</option>
                            <c:forEach items="${select.lstgrupomoviles}" var="grupomovil">
                                <option value="${grupomovil.id}">${grupomovil.nombreGrupo}</option>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${permissions.profileName != 'Propietario'}">
                                    <option value="" style="background: #46b8da;">Seleccione una ruta</option>
                                    <c:forEach items="${select.lstruta}" var="ruta">
                                        <option value="r${ruta.id}">${ruta.nombre}</option>
                                    </c:forEach>
                                </c:when>    
                            </c:choose>
                        </select>
                    </div>
                    <div class="control-group">
                        <div>Fecha/hora predefinida</div>
                        <select id="sfecha" name="sfecha" class="select-picker form-control"
                                onchange="gps_asignarFecha();">                            
                            <option value="1">Ninguna</option>
                            <option value="2" selected="selected">
                                Localizaci&oacute;n actual</option>
                            <option value="3">Hoy</option>
                            <option value="4">Ayer</option>
                        </select>
                    </div>
                    <div class="control-group">
                        <div>Fecha inicial</div>
                        <input id="fechaInicio" type="text" class="form-datetime form-control text-align-center" 
                               placeholder="Fecha Inicial" data-toggle="tooltip" title="Fecha Inicial">
                    </div>
                    <div class="control-group">
                        <div>Fecha final</div>
                        <input id="fechaFinal" type="text" class="form-datetime form-control text-align-center" 
                               placeholder="Fecha Final" data-toggle="tooltip" title="Fecha Final">
                    </div>                    
                    <div class="control-group">
                        <div>Filtro</div>
                        <select id="sfiltro" name="sfiltro" class="select-picker form-control"
                                multiple data-actions-box="true" data-selected-text-format="values"
                                data-container="body">
                            <option selected disabled>Filtros</option>
                            <option>Punto de control</option>
                            <option>Petici&oacute;n pasajero</option>
                            <option>Alarma</option>
                            <option>Consolidado</option>
                            <option>Otros</option>
                        </select>
                    </div>
                    <!-- 200 230 -->
                    <div style="margin: 0 auto; margin-top: 8px; height: 58px; max-width: 150px;">
                        <div>
                            <div class="track-label-search" onclick="ZOOM_IN_BOUNDS1 = true; gps_consultarGps();" 
                                 title="Consultar" data-toggle="tooltip"></div>
                        </div>
                        <div>
                            <div id="btn_pp" class="track-label-play" onclick="ZOOM_IN_BOUNDS2 = true; gps_iniciaPausaAutoConsulta();"
                                 title="Autoconsultar" data-toggle="tooltip"></div>
                        </div>                        
                        <div style="display: none;">
                            <div id="result-pp" class="track-label-positive-result" title="Resultado" data-toogle="tooltip"></div>
                        </div>
                    </div>                    
                    <div id="gps-controls" style="margin: 0 auto; margin-top: 10px; max-width: 230px; clear: both;">
                        <!--
                        <input type="button" onclick="showMark(1);" value="<">
                        <input type="button" onclick="showMark(-1);" value=">">-->                        
                        <div class="min-icon left-arrow-1" onclick="showLastMark();" title="Registro menos reciente"></div>                        
                        <div class="min-icon left-arrow"  onclick="showMark(1);"  style="margin-left: 4px;" title="Registro anterior"></div>
                        <div class="min-icon right-arrow" onclick="showMark(-1);" style="margin-left: 4px;" title="Registro siguiente"></div>
                        <div class="min-icon right-arrow-1" onclick="showFirstMark();" style="margin-left: 4px;" title="Registro m&aacute;s reciente"></div>
                        <!--
                        <div class="min-icon last-mark" onclick="focusFirstMarkAndTrack();" style="margin-left: 4px;" 
                             title="Registro m&aacute;s reciente (permite autoconsulta)"></div> -->
                        <div class="mark-info-position">
                            <span id="currentMark" name="currentMark">0</span>
                            /
                            <span id="totalMark" name="totalMark">0</span>
                        </div>
                    </div>
                    <div style="margin-top: 10px; float: left; clear: both;">
                        <div class="form-msg" id="msg"></div>
                        <div class="form-msg" id="msg-datos"></div>
                        <div class="form-msg" id="msg-gps"></div>
                    </div>                                        
                    <!--
                    <div class="control-group">
                        <div onclick="gps_fechasHoy();"  class="track-button-calendar" 
                             data-toggle="tooltip" title="Fecha Actual"></div>
                    </div>
                    <div class="track-label-car-gps" data-toggle="tooltip" title="Placa - No. Interno"></div>
                    <div class="track-label-gcars-gps" data-toggle="tooltip" title="Grupo de vehiculos"></div>            
                    -->
                </div>
                <div class="col-sm-10">
                    <div id="map" style="height: 768px;"></div>
                </div>
            </div>

            <div id="parametrosConsultados" style="display:none;">
                <input type="hidden" id="fechaIni_con" value="${fechaIni_con}">
                <input type="hidden" id="fechaFin_con" value="${fechaFin_con}">
                <input type="hidden" id="placa_con" value="${placa_con}">
                <input type="hidden" id="grupo_con" value="${grupo_con}">
                <input type="hidden" id="fechaPred_con" value="${fechaPred_con}">
            </div>

            <div id="datosGPS" name="datosGPS" style="display:none;">                    
            </div>                

            <div id="panel-informacion" class="col-sm-2" style="display: none;">                
                <div style="clear:both;">
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 8px;">Fecha</label>
                        <input id="fecha" type="text" class="form-control" readonly>                    
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Placa</label>
                        <input id="placa" type="text" class="form-control" readonly>                    
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Ubicaci&oacute;n</label>
                        <input id="localizacion" type="text" class="form-control" readonly>
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Evento</label>
                        <input id="puntoControl" type="text" class="form-control" readonly>
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Numeraci&oacute;n Inicial</label>
                        <input id="numInicial" type="text" class="form-control" readonly>
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Numeraci&oacute;n Final</label>
                        <input id="numFinal" type="text" class="form-control" readonly>
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Cantidad pasajeros</label>
                        <input id="numPasajeros" type="text" class="form-control" readonly>
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">Distancia recorrida - Km</label>
                        <input id="distanciaRecorrida" type="text" class="form-control" readonly>
                    </div>
                    <div class="control-group">
                        <label class="control-label" style="margin-top: 4px;">IPK</label>
                        <input id="ipk" type="text" class="form-control" readonly>
                    </div>                    
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<!-- Ubicacion geografica de la empresa -->
<input type="hidden" name="lat_empresa" id="lat_empresa" value="${emp.latitudWeb}" />
<input type="hidden" name="lon_empresa" id="lon_empresa" value="${emp.longitudWeb}" /> 

<form id="frm-gps" action="<c:url value='/visualizarGPS' />" method="post">                        
</form>

<jsp:include page="/include/footerTrack.jsp" />
<script type="text/javascript" src="/RDW1/resources/js/hold_session.js"></script>
<script type="text/javascript">
                            hold_session(29);
</script>
<script type="text/javascript" src="/RDW1/resources/js/hold_server_session.js"></script>
<script type="text/javascript">
                            hold_server_session(${maxInactiveInterval});
</script>
<script>
    $(document).ready(function () {

        var dateInit = new Date();
        dateInit.setHours(0);
        dateInit.setMinutes(0);
        dateInit.setSeconds(0);
        var dateEnd = new Date();
        dateEnd.setHours(23);
        dateEnd.setMinutes(59);
        dateEnd.setSeconds(59);

        $('#sfecha').selectpicker({
            size: 4
        });

        $('#splaca').selectpicker({
            size: 8
        });
        //$('#splaca').change(gps_mostrarPanelInformacion);        

        $('#sgrupo').selectpicker({
            size: 8
        });

        $('#sfiltro').selectpicker({
            size: 8
        });

        $('#fechaInicio').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            useCurrent: true,
            date: dateInit,
            locale: 'es'
        }).on('dp.change', gps_ajusteFecha);

        $('#fechaFinal').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            useCurrent: true,
            date: dateEnd,
            locale: 'es'
        }).on('dp.change', gps_ajusteFecha);

        iniciarMapaConLocacion();
        gps_verificarParametrosConsulta();

        if (gps_enLocalizacionActual()) {
            gps_iniciaAutoConsulta();
        } else {
            gps_consultarGps();
            gps_habilitarAutoConsulta(false);
        }

        window.scroll(0, 150);

        var menu_item_rastreo = document.getElementById("menu-item-rastreo");
        menu_item_rastreo.className += " select-menu-item";
        gps_mostrarComponente('gps-controls', false);

        gps_verificaConexion();

    });
</script>
<jsp:include page="/include/end.jsp" />
