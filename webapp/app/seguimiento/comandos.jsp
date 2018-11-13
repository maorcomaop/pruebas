
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerappRutaNew.jsp" />

<!--
            <div class="col-md-9 top-space bottom-space">
                <a href="#"><strong>> Rutas</strong></a>
            </div>-->

<!-- Body container -->
<div class="col-lg-12 css-gps-tracking over">
    <!-- <h1 class="display-3 bg-info text-center">M&Oacute;DULO RASTREO</h1> -->
    <c:choose>
        <c:when test="${permissions.check(['SeguimientoGPS'])}">
            <div class="row over">
                <div class="col-sm-12 over">
                    <section class="boxed padding-min no-scroll over">
                        <ul class="testing">
                            <li ><a href="/RDW1/app/seguimiento/gps2.jsp">Ver mapa</a></li>
                            <li ><a href="/RDW1/app/seguimiento/infogps.jsp">M&oacute;viles</a></li>
                                <c:if test="${permissions.check(219)}">
                                <li ><a href="/RDW1/app/seguimiento/comandos.jsp"><strong>Comandos</strong></a></li>
                                </c:if>
                        </ul>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="col-sm-8">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row"> 
                                                <div class="col-sm-12">
                                                    <div class="col-sm-6">
                                                        <div class="row">                                         
                                                            <div class="col-sm-2">
                                                                <div class="track-label-car-sendtype" data-toggle="tooltip" title="Tipo de envío"></div>
                                                            </div>
                                                            <div class="col-sm-10"> 
                                                                <select id="combo-send-type" name="combo-send-type" class="selectpicker form-control" 
                                                                        data-actions-box="true" data-selected-text-format="values"
                                                                        data-container="body" title="Tipo de Envío" onchange="set_send_type(this.value)" >
                                                                    <option value="IND" selected>Individual</option>
                                                                    <option value="MUL">Multiple</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <div class="row">                                         
                                                            <div class="col-sm-2">
                                                                <div class="track-label-car-gps" data-toggle="tooltip" title="Placa - No. Interno"></div>
                                                            </div>
                                                            <div class="col-sm-10"> 
                                                                <div class="multicombo-selected obfuscate"> 
                                                                    <select id="multicombo-plate" name="combo-plate" class="selectpicker form-control" 
                                                                            data-live-search="true" multiple data-actions-box="true" data-selected-text-format="values" 
                                                                            data-container="body" title="Seleccione los Vehículos" onchange="change_multicombo_plate(this.value)">
                                                                        <c:forEach items="${select.lstmovil}" var="veh">
                                                                            <c:if test="${veh.fkGps != null}">
                                                                                <option value="${veh.placa}-${veh.fkGps}-${veh.fkMarcaGps}">${veh.placa} - ${veh.numeroInterno}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>                                                     
                                                                </div>
                                                                <div class="combo-selected"> 
                                                                    <select id="combo-plate" name="combo-plate" class="selectpicker form-control" 
                                                                            data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                                                            data-container="body" title="Seleccione un Vehiculo" onchange="change_combo_plate(this.value)">
                                                                        <c:forEach items="${select.lstmovil}" var="veh">
                                                                            <c:if test="${veh.fkGps != null}">
                                                                                <option value="${veh.placa}-${veh.fkGps}-${veh.fkMarcaGps}">${veh.placa} - ${veh.numeroInterno}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>                                                     
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <div class="row">                                         
                                                            <div class="col-sm-2">
                                                                <div class="track-label-cmd-gps" data-toggle="tooltip" title="Comando"></div>
                                                            </div>
                                                            <div class="col-sm-10"> 
                                                                <select id="combo-command" name="combo-command" class="selectpicker form-control" 
                                                                        data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                                                        data-container="body" title="Seleccione un Comando" onchange="add_command(this.value)" >
                                                                    <option style="background: #8eacbb" value="a">GPS</option>
                                                                    <c:if test="${permissions.check(225)}">
                                                                        <option value="11">SLCTD_ESTADO</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(235)}">
                                                                        <option value="12">RESET_GPS</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(236)}">
                                                                        <option value="5">MDFCR_REP_TIEMPO_MOVIL_ON</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(237)}">
                                                                        <option value="6">MDFCR_REP_TIEMPO_MOVIL_OFF</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(238)}">
                                                                        <option value="7">MDFCR_REP_DISTANCIA</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(226)}">
                                                                        <option value="10">MDFCR_LIMITE_VELOCIDAD</option>  
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(239)}">
                                                                        <option value="22">MDFCR_CONEXION_IP_DOMINIO</option>                                                                    
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(240)}">
                                                                        <option value="23">MDFCR_CONEXION_PUERTO</option>   
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(241)}">
                                                                        <option value="27">MDFCR_CONEXION_APN</option>   
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(242)}">
                                                                        <option value="2">ACTIVAR_GEO_PUNTOS</option>                                                                    
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(227)}">
                                                                        <option value="8">MOVIL_APAGADO</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(228)}">
                                                                        <option value="9">MOVIL_ENCENDIDO</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(-1)}">
                                                                        <option style="background: #8eacbb" value="b">REGISBUS</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(243)}">
                                                                        <option value="13">SLCTD_CONTADOR</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(244)}">
                                                                        <option value="19">RESET_CONTADOR</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(245)}">
                                                                        <option value="14">SLCTD_ESTADO_SENSORES</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(246)}">
                                                                        <option value="15">SLCTD_ESTADO_VOLTAJES</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(247)}">
                                                                        <option value="16">SLCTD_PARAM_CONTADOR</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(248)}">
                                                                        <option value="17">MDFCR_CONTADOR</option>
                                                                    </c:if>
                                                                    <c:if test="${permissions.check(249)}">
                                                                        <option value="18">MDFCR_PARAM_CONTADOR</option>
                                                                    </c:if>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <br>
                                                        <div class="row" >
                                                            <div class="controls col-sm-8" style="display: none">
                                                                <label class="control-label" for="inputName">Código</label>
                                                                <input type="text" name="cmd_code" class="form-control" id="cmd-code" title="Digite el Código" value="" maxlength="100">
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="controls col-sm-3">
                                                                <label class="control-label" for="inputName">Intentos</label>
                                                                <input type="number" name="cmd_tries" class="form-control" id="cmd-tries" title="Digite el Número de Intentos" min="1" max="10" value="2">
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <div class="row" style="position: relative; top: 20px;">
                                                            <div class="controls col-sm-4">
                                                                <input id = "cmd-save" type="button" name="cmd_save" value="Guardar" class="btn" style="background:#2196f3;color:white" id="cmd_save">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="row">
                                                            <div class="col-sm-12">
                                                                <span>Comandos a Enviar</span>
                                                                <br>
                                                                <div class='commands_added'>
                                                                    <div class='command_list'>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>                                
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="col-sm-8">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row"> 
                                                <div class="col-sm-12">
                                                    <div id = 'progress-sidebar' class='progress-sidebar'>
                                                        <div class='search' style="display: none">
                                                            <div class ='search-icon'><div class ='icon'></div></div>
                                                            <div class ='search-field'><input id='input-search' type="text" placeholder=" Buscar móvil"></div>
                                                        </div>
                                                        <div id = 'progress-container' class='progress-container'>
                                                        </div>
                                                        <div id = 'progress-info' class='progress-info'>
                                                        </div>                                    
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>                                
                            </div>
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
<div id="modalaux-id" class="modalaux">
    <div class="modalaux-content">
        <div class ="modalaux-message">...</div>
        <div class ="modalaux-close">
            <input type="button" value="Continuar" class="btn" style="background:#2196f3;color:white">
        </div>
    </div>
</div>
<jsp:include page="/include/footerTrack.jsp" />

<script>
    var menu_item_rastreo = document.getElementById("menu-item-rastreo");
    menu_item_rastreo.className += " select-menu-item";
</script>    
<script type="text/javascript" src="/RDW1/resources/js/comandos/comandos.js"></script>
<script type="text/javascript" src="/RDW1/resources/js/gps_status/gps_status.js"></script>

<jsp:include page="/include/end.jsp" />
