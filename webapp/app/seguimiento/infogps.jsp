
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerappRutaNew.jsp" />

<div class="container-fluid over">
    <!-- <h1 class="display-3 bg-info text-center">M&Oacute;DULO RASTREO</h1> -->
    <c:choose>
        <c:when test="${permissions.check(['SeguimientoGPS'])}">
            <div class="col-lg-12 over">
                <div class="row over">
                    <section class="boxed padding-min over">
                        <div class="css-gps-tracking">
                            <ul>
                                <li ><a href="/RDW1/app/seguimiento/gps2.jsp">Ver mapa</a></li>
                                <li ><a href="#"><strong>M&oacute;viles</strong></a></li>
                                    <c:if test="${permissions.check(219)}">
                                    <li ><a href="/RDW1/app/seguimiento/comandos.jsp">Comandos</a></li>
                                    </c:if>
                            </ul>
                        </div>
                        <div style="margin: 0 auto; max-width: 1330px;">                            
                            <ul class="nav nav-tabs">
                                <li>
                                    <div style="width: 164px;">
                                        <select id="splaca" name="splaca" class="select-picker form-control"
                                                data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                                data-container="body"
                                                onchange="gps_cambioParametros();">
                                            <option value="" selected>Todos los m&oacute;viles</option>
                                            <c:forEach items="${select.lstmovil}" var="veh">
                                                <option value="${veh.placa},${veh.numeroInterno},${veh.capacidad}">${veh.placa} - ${veh.numeroInterno}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </li>
                                <li>
                                    <div style="width: 164px;">
                                        <input id="fechaInicio" type="text" class="form-control" placeholder="Fecha Inicial" title="Fecha Inicial">
                                    </div>
                                </li>
                                <li>
                                    <div style="width: 164px;">
                                        <input id="fechaFinal" type="text" class="form-control" placeholder="Fecha Final" title="Fecha Final">
                                    </div>            
                                </li>
                                <li>
                                    <select id="sfecha" name="sfecha" class="select-picker form-control"
                                            data-width="164px"
                                            onchange="gps_asignarFecha();">
                                        <option value="1">Ninguna</option>
                                        <option value="2" selected="selected">
                                            Localizaci&oacute;n actual</option>
                                        <option value="3">Hoy</option>
                                        <option value="4">Ayer</option>
                                    </select>
                                </li>
                                <li>
                                    <div>
                                        <select id="sfiltro" name="sfiltro" class="select-picker form-control" 
                                                multiple data-actions-box="true" data-selected-text-format="values"
                                                data-width="164px"
                                                onchange="gps_cambioParametros();">
                                            <option selected disabled>Filtros</option>
                                            <option>Puntos de control</option>
                                            <option>Petici&oacute;n Pasajero</option>
                                            <option>Alarmas</option>
                                            <option>Consolidado</option>
                                            <option>Otros</option>
                                        </select>
                                    </div>
                                </li>
                                <!--
                                <li>                
                                    <div class="top-space left-space">
                                        <div class="track-label-calendar-min" onclick="gps_fechasHoy();" title="Fecha actual"></div>
                                <!--
                                <input type="button" onclick="gps_fechasHoy();" style="width: 128px;" value="Usar fecha actual">

                            </div>                
                        </li>
                                -->
                                <li>
                                    <div class="top-space left-space">
                                        <div class="track-label-search-min" onclick="gps_consultarTabla(); gps_tablaResumen();" title="Consultar"></div>
                                        <!--
                                        <input type="button" onclick="gps_consultarTabla(); gps_tablaResumen();" style="width: 128px;" 
                                               value="Consultar" id="btn_consulta" name="btn_consulta"> 
                                        -->
                                    </div>
                                </li>                                                        
                                <li>
                                    <div class="top-space left-space">
                                        <div class="track-label-pause-min" onclick="gps_iniciaPausaAutoConsulta(); gps_tablaResumen();" 
                                             id="btn_pp" name="btn_pp" title="Autoconsulta"></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="top-space left-space">
                                        <div class="track-label-excel-min" onclick="gps_exportarExcel();" title="Exportar a excel"></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="top-space left-space">
                                        <div class="track-label-ipk-min" onclick="gps_generarIPK();" title="Exportar IPK"></div>
                                        <!--
                                        <input type="button" onclick="gps_generarIPK();" style="width: 86px;" value="Exportar IPK">
                                        -->
                                    </div>
                                </li>
                                <li>
                                    <div class="top-mm-space left-space" style="padding-left: 10px;">
                                        <div class ='search-icon'><div class ='icon' style="position: relative;bottom: 7px;right: 8px;"></div></div>
                                        <input type="text" class="input-trans" id="textoBusqueda" name="textoBusqueda" onkeyup="gps_buscarInfoGPS();" placeholder="Buscar">
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <!-- Mensajes de error -->
                        <div id="msg" class="form-msg" style="margin: 0 auto; max-width: 512px; text-align: center;">${msg}</div>
                        <div id="msg-gps" class="form-msg" style="margin: 0 auto; max-width: 512px; text-align: center;"></div>

                        <div style="display: none;">
                            <form id="form-ipk" action="<c:url value='/generarIPK' />" method="post">
                                <input type="hidden" id="placa_ipk" name="placa_ipk">
                                <input type="hidden" id="fechaInicio_ipk" name="fechaInicio_ipk">
                                <input type="hidden" id="fechaFinal_ipk" name="fechaFinal_ipk">
                            </form>
                        </div>

                        <div style="display: none;">
                            <form id="form-excel" action="<c:url value='/exportarHistoricoGPS' />" method="post">
                                <input type="hidden" id="placa_excel" name="placa">
                                <input type="hidden" id="fechaInicio_excel" name="fechaInicio">
                                <input type="hidden" id="fechaFinal_excel" name="fechaFinal">
                                <input type="hidden" id="verPuntoControl_excel" name="verPuntoControl">
                                <input type="hidden" id="verPasajero_excel" name="verPasajero">
                                <input type="hidden" id="verAlarma_excel" name="verAlarma">
                                <input type="hidden" id="verConsolidado_excel" name="verConsolidado">
                            </form>
                        </div>

                        <!-- Parametros con que se ha hecho la consulta -->
                        <div id="parametrosConsultados" style="display:none;">
                            <input type="hidden" id="fechaIni_con" value="${fechaIni_con}">
                            <input type="hidden" id="fechaFin_con" value="${fechaFin_con}">
                            <input type="hidden" id="placa_con" value="${placa_con}">
                            <input type="hidden" id="fechaPred_con" value="${fechaPred_con}">
                        </div>

                        <!-- Tabla resumen -->                        
                        <!-- <div class="col-lg-12" style="margin-top: 24px; padding-left: 10%;"> -->
                        <div style="margin: 0 auto; margin-top: 6px; max-width: 1330px;">
                            <table id="tablaResumenGPS" class="myTableT myTable-horizontal" style="display: none; margin-left: 100px;">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Placa</th>
                                        <th>Numeraci&oacute;n inicial</th>                    
                                        <th>Numeraci&oacute;n final</th>                    
                                        <th>Total pasajeros</th>                    
                                        <th>Distancia recorrida - Km</th>                    
                                        <th>IPK</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><div class="resumen indicator-gps" style="position: relative;left: 60px;"></div></td>
                                        <td><span class="display-none">Placa</span>                              <input type="text" id="placa"        readonly></td>
                                        <td><span class="display-none">Numeraci&oacute;n inicial</span>          <input type="text" id="numInicial"   readonly></td>
                                        <td><span class="display-none">Numeraci&oacute;n final</span>            <input type="text" id="numFinal"     readonly></td>
                                        <td><span class="display-none">Total pasajeros</span>                    <input type="text" id="numPasajeros" readonly></td>
                                        <td><span class="display-none">Distancia recorrida - Km</span>           <input type="text" id="distanciaRecorrida" readonly></td>
                                        <td><span class="display-none">IPK</span>                                <input type="text" id="ipk" readonly></td>
                                    </tr>                                    
                                </tbody>
                            </table>                            
                        </div>

                        <!-- Tabla completa -->
                        <!--<div id="datosGPS" name="datosGPS" class="col-lg-12" style="margin-top: 24px; margin-bottom: 24px;">   -->
                        <div role="tabpanel" class="padding-smin active div-table-style" id="pestana1">
                            <table id="tablaInfoGPS" class="display table-style" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th class="no-display"></th>
                                        <th class="text-align-center">Fecha servidor</th>
                                        <th class="text-align-center">Fecha GPS</th>
                                        <th class="text-align-center">Placa</th>
                                        <th class="no-display">Punto de control</th>
                                        <th class="no-display">Pasajero</th>
                                        <th class="no-display">Alarma</th>
                                        <th class="text-align-center">Localizaci&oacute;n</th>
                                        <th class="text-align-center">Evento / Punto control</th>
                                        <th class="text-align-center">Alarma</th>
                                        <th class="text-align-center">Numeraci&oacute;n</th>
                                        <th class="text-align-center">Total d&iacute;a</th>
                                            <c:choose>
                                                <c:when test="${entorno.map['mostrar_entrada_salida'] == '1'}">
                                                <th class="text-align-center">Entradas</th>
                                                <th class="text-align-center">Salidas</th>
                                                </c:when>
                                                <c:otherwise>
                                                <th class="no-display"></th>
                                                <th class="no-display"></th>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${entorno.map['mostrar_nivel_ocupacion'] == '1'}">
                                                <th class="text-align-center">Nivel de ocupaci&oacute;n</th>
                                                </c:when>
                                                <c:otherwise>
                                                <th class="no-display"></th>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${entorno.map['mostrar_noic'] == '1'}">
                                                <th class="text-align-center">Ocupaci&oacute;n / Capacidad</th>
                                                </c:when>
                                                <c:otherwise>
                                                <th class="no-display"></th>
                                                </c:otherwise>
                                            </c:choose>
                                        <th class="text-align-center">Rumbo</th>
                                        <th class="text-align-center">Velocidad</th>
                                        <th class="text-align-center">Distancia en metros</th>
                                        <th class="text-align-center">Nombre flota</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyid">
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerTrack.jsp" />
<script type="text/javascript" src="/RDW1/resources/js/hold_session.js"></script>
<script type="text/javascript">
                                            hold_session(29);
</script>
<script type="text/javascript" src="/RDW1/resources/js/hold_server_session.js"></script>
<script type="text/javascript">
                                            hold_server_session(${maxInactiveInterval});
</script>
<script type="text/javascript" src="/RDW1/resources/js/gps_status/gps_status.js"></script>
<script>
                                            $(document).ready(function () {

                                                $('#sfecha').selectpicker({
                                                    size: 4
                                                });

                                                $('#splaca').selectpicker({
                                                    size: 8
                                                });

                                                $('#fechaInicio').datetimepicker({
                                                    format: 'YYYY-MM-DD HH:mm:ss',
                                                    useCurrent: true,
                                                    locale: 'es'
                                                }).on('dp.change', gps_ajusteFecha);

                                                $('#fechaFinal').datetimepicker({
                                                    format: 'YYYY-MM-DD HH:mm:ss',
                                                    useCurrent: true,
                                                    locale: 'es'
                                                }).on('dp.change', gps_ajusteFecha);

                                                $('#sfiltro').selectpicker({
                                                    size: 5
                                                });

                                                /*
                                                 $('#tablaInfoGPS').DataTable({
                                                 //"aLengthMenu": [30,45,60],
                                                 "bLengthChange": false,
                                                 "showNEntries": false,
                                                 "bSort": false,
                                                 "searching": false,
                                                 "columnDefs": [{
                                                 "targets": [1, 5, 6, 7],
                                                 "visible": false
                                                 }],
                                                 "scrollX": true,
                                                 scrollY: 500,
                                                 bInfo: false,
                                                 paging: false,
                                                 language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
                                                 //responsive: true        
                                                 });  */

                                                gps_informacionTabular(true);
                                                gps_verificarParametrosConsulta();

                                                if (gps_enLocalizacionActual()) {
                                                    gps_iniciaAutoConsulta();
                                                } else {
                                                    gps_consultarTabla();
                                                    gps_habilitarAutoConsulta(false);
                                                }

                                                var menu_item_rastreo = document.getElementById("menu-item-rastreo");
                                                menu_item_rastreo.className += " select-menu-item";

                                                gps_verificaConexion();

                                                /*
                                                 gps_reanudarAutoConsultaAuto();
                                                 gps_iniciarAutoConsultaTabla();
                                                 setInterval("gps_iniciarAutoConsultaTabla()", 6000); */
                                            });
</script>

<jsp:include page="/include/end.jsp" />
