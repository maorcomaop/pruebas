
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerappRutaNew.jsp" />
<!-- Body container -->
<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PUNTOS DE CONTROL</h1>    
    <section class="boxed padding-min">
        <c:choose>
            <c:when test="${permissions.check(127)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(50)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(126)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignación Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(127)}">
                        <li role="presentacion"  class="active"><a href="/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp">Edición Puntos</a></li>
                        </c:if>
                        <c:if test="${permissions.check(115)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado Rutas</a></li>
                        </c:if>
                        <c:if test="${permissions.check(128)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud Ruta</a></li>
                        </c:if>
                        <c:if test="${permissions.check(52)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos</a>
                        </c:if>
                </ul>

                <div id="map" style="height: 396px;"></div>                

                <div style="margin-top: 6px;">
                    <div id="msg" class="form-msg ${msgType}" role="alert">${msg}</div>

                    <!-- Seleccion de ruta -->                
                    <div style="float: left;">                            
                        <select id="sruta" name="sruta" onchange="marcarRuta_dph();"
                                class="select-picker form-control" data-container="body" data-width="auto">
                            <option value="">Seleccione una ruta</option>
                            <c:forEach items="${select.lstruta}" var="ruta">
                                <option value="${ruta.id}">${ruta.nombre}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" id="ruta_editada" name="ruta_editada" value="${ruta_editada}">
                    </div>                       

                    <!-- Lista despachos -->
                    <div style="float: left;" class="left-space">
                        <select id="sdespacho" name="sdespacho" onchange="marcarDespacho_dph();" 
                                class="select-picker form-control" data-container="body" data-width="auto">                                
                            <option value="">Seleccione despacho</option>
                            <c:forEach items="${select.lstdespacho}" var="despacho">
                                <option value="${despacho.id},${despacho.idRuta}">
                                    ${despacho.nombreDespacho} / ${despacho.tipoDia} / ${despacho.nombreRuta}</option>
                                </c:forEach>
                        </select>
                    </div>

                    <c:if test="${permissions.check(130)}">
                        <div style="float: left;" class="left-space top-space">
                            <label><input type="checkbox" id="chk_ruta" name="chk_ruta">&nbsp;Actualizar ruta</label>
                        </div>

                        <!-- Actualizacion de puntos ruta -->
                        <div style="float: left;" class="left-ml-space">
                            <form id="form-actualiza-ruta" action="<c:url value='/actualizarPuntosRuta' />" method="post">
                                <input type="hidden" id="listaPuntosRuta" name="listaPuntosRuta">
                                <input type="hidden" id="listaDespacho" name="listaDespacho">                                
                                <input type="hidden" id="idRuta" name="idRuta">
                                <input type="hidden" id="idDespacho" name="idDespacho">
                                <input type="hidden" id="actualizarRuta" name="actualizarRuta">
                                <button type="button" class="btn btn-primary" onclick="sendPuntosRutaUPD_dph();">Actualizar puntos</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>

    <!-- Lista puntos ruta -->
    <div id="divPuntosRuta" style="display: none; margin-top: 4px;">
        <table id="tablaPuntosRuta" class="display puntero" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th style="display: none;"></th>
                    <th style="display: none;"></th>
                    <th style="display: none;"></th>
                    <th>C&oacute;digo punto</th>
                    <th>Nombre punto</th>
                    <th>Tiempo promedio (min)</th>
                    <th>Tiempo de holgura (min)</th>
                    <th style="display: none;">Valor punto</th>
                    <th style="display: none;">Tipo</th>
                </tr>
            </thead>
            <tbody>                            
                <c:forEach items="${select.lstpuntoruta_completa}" var="puntoruta">
                    <tr onclick="lightPoint(${puntoruta.latitudWeb},${puntoruta.longitudWeb}); resaltarPuntoEnTabla('tablaPuntosRuta', this);">
                        <td style="display: none;">${puntoruta.id}</td>
                        <td style="display: none;">${puntoruta.idRuta}</td>
                        <td style="display: none;">${puntoruta.idPunto}</td>
                        <td>${puntoruta.etiquetaPunto}</td>
                        <td>
                            <label>
                                ${puntoruta.nombrePunto}
                            </label>
                        </td>
                        <td><input type="number" min="0" max="5760" value="${puntoruta.promedioMinutos}"></td>
                        <td><input type="number" min="0" max="5760" value="${puntoruta.holguraMinutos}"></td>
                        <td style="display: none;"><input type="number" min="0" max="5760" value="${puntoruta.valorPunto}"></td>
                        <td style="display: none;">
                            <c:choose>
                                <c:when test="${puntoruta.tipo == 1}">
                                    <select>
                                        <option value="0"></option>
                                        <option value="1" selected>Base salida</option>
                                        <option value="2">Punto</option>
                                        <option value="3">Base llegada</option>
                                    </select>
                                </c:when>
                                <c:when test="${puntoruta.tipo == 2}">
                                    <select>
                                        <option value="0"></option>
                                        <option value="1">Base salida</option>
                                        <option value="2" selected>Punto</option>
                                        <option value="3">Base llegada</option>
                                    </select>
                                </c:when>
                                <c:when test="${puntoruta.tipo == 3}">
                                    <select>
                                        <option value="0"></option>
                                        <option value="1">Base salida</option>
                                        <option value="2">Punto</option>
                                        <option value="3" selected>Base llegada</option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select>
                                        <option value="0" selected></option>
                                        <option value="1">Base salida</option>
                                        <option value="2">Punto</option>
                                        <option value="3">Base llegada</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Lista puntos ruta despacho -->
    <div id="divPuntosRutaDph" style="display: none; margin-top: 4px;">
        <table id="tablaPuntosRutaDph" class="display puntero" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th style="display: none;"></th>
                    <th style="display: none;"></th>
                    <th style="display: none;"></th>
                    <th>C&oacute;digo punto</th>
                    <th>Nombre punto</th>
                    <th>Tiempo establecido (min)</th>
                    <th>Tiempo de holgura (min)</th>
                    <th>Tiempo de llegada valle (min)</th>
                    <th>Tiempo de llegada pico (min)</th>
                </tr>
            </thead>
            <tbody>               
                <c:forEach items="${select.lstpuntoruta_cruzada}" var="despacho">
                    <c:forEach items="${despacho.listaPuntosRuta_all}" var="puntoruta">
                        <tr>
                            <td style="display: none;">${despacho.id}</td>
                            <td style="display: none;">${puntoruta.id}</td>
                            <td style="display: none;">${puntoruta.idPunto}</td>
                            <td>${puntoruta.etiquetaPunto}</td>
                            <td>
                                <c:if test="${puntoruta.puntoEnDespacho}">
                                    <label><input type="checkbox" checked="checked">&nbsp;${puntoruta.nombrePunto}</label>
                                    </c:if>
                                    <c:if test="${!puntoruta.puntoEnDespacho}">
                                    <label><input type="checkbox">&nbsp;${puntoruta.nombrePunto}</label>
                                    </c:if>
                            </td>
                            <td><input type="number" min="0" max="5760" value="${puntoruta.promedioMinutos}"></td>
                            <td><input type="number" min="0" max="5760" value="${puntoruta.tiempoHolgura}"></td>
                            <td><input type="number" min="0" max="5760" value="${puntoruta.tiempoValle}"></td>
                            <td><input type="number" min="0" max="5760" value="${puntoruta.tiempoPico}"></td>
                        </tr>
                    </c:forEach>                                                                        
                </c:forEach>
            </tbody>
        </table>                                                                       
    </div>
</div>

<!-- Puntos de cada ruta relacionados -->
<div style="display: none;">
    <select id="lstpuntosruta" name="lstpuntosruta">
        <c:forEach items="${select.lstpuntoruta_completa}" var="puntoruta">
            <option value="${puntoruta.idRuta},${puntoruta.nombrePunto},${puntoruta.latitudWeb},${puntoruta.longitudWeb}"></option>
        </c:forEach>
    </select>
</div>

<!-- Ubicacion geografica de la empresa -->
<input type="hidden" name="lat_empresa" id="lat_empresa" value="${emp.latitudWeb}" />
<input type="hidden" name="lon_empresa" id="lon_empresa" value="${emp.longitudWeb}" />                 

<jsp:include page="/include/footerappRuta.jsp" />
<script>

    // Tabla dinamica de puntos ruta
    $(document).ready(function () {

        iniciarMapa();

        $('#sruta').selectpicker({
            size: 8
        });

        $('#sdespacho').selectpicker({
            size: 8
        });

        $('#tablaPuntosRuta').DataTable({
            "bLengthChange": false,
            "showNEntries": false,
            "bSort": false,
            "paging": false,
            "scrollX": true
                    //responsive: true
        });

        $('#tablaPuntosRutaDph').DataTable({
            "bLengthChange": false,
            "showNEntries": false,
            "bSort": false,
            "paging": false,
            "scrollX": true
                    //responsive: true
        });

        verificaRutaEditada();
    });
</script>
<jsp:include page="/include/end.jsp" />