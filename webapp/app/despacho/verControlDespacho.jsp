
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="container-fluid">
    <div class="col-lg-12 centered">
        <h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
        <div class="row">
            <section class="boxed padding-min" style="height: 100%; min-height: 640px; overflow: hidden;">
                <c:choose>
                    <c:when test="${permissions.check(['Despacho'])}">
                        <ul class="nav nav-tabs">
                            <li role="presentacion"><a href="#">Control despacho</a></li>                    
                            <li>
                                <c:if test="${entorno.map['tipo_control_despacho'] == null ||                                       
                                              entorno.map['tipo_control_despacho'] == '' ||                                       
                                              entorno.map['tipo_control_despacho'] == 'ctrl-tiempo-planificado'}">
                                      <div style="width: 208px;">
                                          <select id="sruta" name="sruta" class="select-picker form-control"
                                                  data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                                  data-container="body">
                                              <option value="">Seleccione una ruta</option>
                                              <c:forEach items="${select.lstruta_despacho}" var="ruta_dph">
                                                  <option value="${ruta_dph.id}">${ruta_dph.nombre}</option>
                                              </c:forEach>
                                          </select>
                                      </div>
                                </c:if>

                                <c:if test="${entorno.map['tipo_control_despacho'] == 'ctrl-intervalo-llegada'}">
                                    <div style="width: 208px;">
                                        <select id="sruta" name="sruta" class="select-picker form-control"
                                                data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                                data-container="body" multiple>
                                            <option value="" disabled>Seleccione ruta(s)</option>
                                            <c:forEach items="${select.lstruta_despacho}" var="ruta_dph">
                                                <option value="${ruta_dph.id}">${ruta_dph.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </c:if>
                            </li>
                            <li>
                                <div style="width: 208px;">
                                    <input type="text" id="fecha-ctr" name="fecha-ctr" class="form-control" placeholder="Fecha" readonly="readonly">
                                </div>
                            </li>                    
                            <!--
                            <li>
                                <div style="width: 208px;">
                                    <input type="text" id="fechaFinal" name="fechaFinal" class="form-control" placeholder="Fecha final" readonly="readonly">
                                </div>
                            </li> -->
                            <li>
                                <div class="top-space left-space">
                                    <input type="button" onclick="dph_usarFechaHoy();" value="Fecha de hoy">
                                </div>
                            </li>
                            <li>
                                <div class="top-space left-space">
                                    <label><input id="mostrarHistoria" name="mostrarHistoria" type="checkbox">&nbsp;Mostrar historia</label>                            
                                </div>
                            </li>                    
                            <li>
                                <div class="left-space">
                                    <select id="smovil" name="smovil" class="select-picker form-control"
                                            data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                            onchange="dph_filtrarPorMovil();" multiple data-width="auto" data-max-options="10"
                                            data-container="body"
                                            title="Seleccione veh&iacute;culo(s)">
                                        <!-- <option value="">Seleccione un movil</option> -->
                                        <c:forEach items="${select.lstmovil}" var="movil">
                                            <option value="${movil.placa}">${movil.numeroInterno} - ${movil.placa}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li> 
                            <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                                <li><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Configuraci&oacute;n</a></li>
                                </c:if>
                                <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                                <li><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                                </c:if>
                        </ul>

                        <div class="msg-dph">                        
                            <table>
                                <tbody>
                                    <tr>
                                        <td style="padding-right: 6px;"><img class="icon-grey"><label>&nbsp;No pas&oacute;</label></td>
                                        <td style="padding-right: 6px;"><img class="icon-pasajero"><label>&nbsp;A tiempo</label></td>
                                        <td style="padding-right: 6px;"><img class="icon-puntoActual"><label>&nbsp;Adelantado</label></td>
                                        <td style="padding-right: 6px;"><img class="icon-alarma"><label>&nbsp;Atrasado</label></td>
                                        <td style="padding-right: 6px;"><img class="icon-puntoControl"><label>&nbsp;Fuera de rango de comprobaci&oacute;n</label></td>
                                    </tr>
                                </tbody>
                            </table>                        
                        </div>

                        <input type="hidden" id="p_ruta"            value="${dph_ruta}">
                        <input type="hidden" id="p_fecha"           value="${dph_fecha}">
                        <input type="hidden" id="p_mostrarHistoria" value="${dph_mostrar_historia}">
                        <input type="hidden" id="p_movil"           value="${dph_movil}">

                        <c:if test="${estadoDespacho == null || estadoDespacho == '0'}">
                            <div id="msg-estado" class="form-msg msg-dph" role="alert">
                                * Control despacho inactivo. Para activarlo hazlo <a href="/RDW1/app/despacho/estadoDespacho.jsp">aqu&iacute;</a>.
                            </div>
                        </c:if>                        

                        <div id="msg" class="form-msg msg-dph" style="display: none;" role="alert">${msg}</div>                         

                        <div class="row">

                            <div class="col-sm-9">
                                <div id="controlDespacho" class="div-style-cpd">
                                </div>
                            </div>
                            <div class="col-sm-3" style="background-color: inherit;">
                                <div id = 'chat-sidebar' class='chat-sidebar'>
                                    <div class='chat-options'>

                                        <div class='search'>
                                            <div class ='search-icon'><div class ='icon'></div></div>
                                            <div class ='search-field'><input id='input-search' type="text" placeholder=" Buscar vehículo"></div>
                                        </div>
                                        <div class='broadcast' title="Anuncio multidestino">
                                            <div id="controller" class ='broadcast-icon'><div class ='icon'></div></div>
                                        </div>
                                    </div>
                                    <div id = 'chat-container' class='chat-container'>
                                    </div>
                                    <div id = 'moviles-info' class='moviles-info'>
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="/include/accesoDenegado.jsp" />
                    </c:otherwise>
                </c:choose>
            </section>
        </div>
    </div>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script type="text/javascript" src="/RDW1/resources/js/chat/chat.js"></script>
<script type="text/javascript" src="/RDW1/resources/js/gps_status/gps_status.js"></script>
<jsp:include page="/include/end.jsp" />
