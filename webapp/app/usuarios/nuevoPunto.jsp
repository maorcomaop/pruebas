<jsp:include page="/include/headerappMapNew.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PUNTOS DE CONTROL</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(51)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(50)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva Ruta</a></li>
                    </c:if>
                    <c:if test="${permissions.check(126)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignación Puntos</a></li>
                    </c:if>
                    <c:if test="${permissions.check(127)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp">Edición Puntos</a></li>
                    </c:if>
                    <c:if test="${permissions.check(115)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado Rutas</a></li>
                    </c:if>
                    <c:if test="${permissions.check(128)}">
                        <li role="presentacion"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud Ruta</a></li>
                    </c:if>
                    <c:if test="${permissions.check(52)}">
                        <li role="presentation" class="active"><a href="#">Puntos</a>
                    </c:if>
                </ul>
                
                <div style="margin-top: 10px; margin-bottom: 10px; margin-left: 4px;">
                    <c:if test="${permissions.check(52)}">                        
                        <a href="#"><strong>Puntos de control</strong></a>
                    </c:if>
                    -
                    <c:if test="${permissions.check(53)}">                        
                        <a href="/RDW1/app/usuarios/inoutPunto.jsp">Importar/Exportar</a>
                    </c:if>
                </div> 

                <div> 
                    <section>
                        <div style="height: 548px;" id="map"></div>                
                        <!--
                        <div id="controls">                    
                            <input type="button" value="addPath" onclick="addPath();" />
                            <input type="button" value="removePath" onclick="removePath();" />                    
            
                            <input id="myFile" type="file" />
                            <input id="uploadLink" type="button" value="uploadFile" />
            
                            <a href="#" id="downloadLink" download="data.json" onclick="exportToJsonFile();">downloadFile</a>                    
                        </div>
                        -->                
                    </section>
                </div>

                <div>
                    <div id="msg" style="margin-top: 8px;" class="form-msg bottom-space ${msgType}" alert="alert">${msg}</div>

                    <form id="form-punto" class="form-inline" action="<c:url value='/guardarPunto' />" method="post"                          
                          <c:if test="${!permissions.check(142) && !permissions.check(52)}"> style="display: none"</c:if>         
                              >                        
                              <div style="overflow-x:auto;">
                                  <table id="tablaPunto" width="100%">
                                      <tr>
                                          <td class="t">* Nombre</td>
                                          <td><input type="text" name="nombre" id="nombre" placeholder="Nombre" pattern="^([a-zA-Z0-9-\.#]+\s*)+$" maxlength="50" 
                                                     title="&Uacute;nicamente caracteres del alfabeto, n&uacute;meros y s&iacute;mbolos #-." required></td>
                                          <td class="t">* Latitud</td>
                                          <td><input type="text" name="latitud" id="latitud" placeholder="Latitud" onchange="setDireccionPunto_m();" required></td>
                                          <td class="t">* Longitud</td>
                                          <td><input type="text" name="longitud" id="longitud" placeholder="Longitud" onchange="setDireccionPunto_m();" required></td>
                                          <td class="t">* C&oacute;digo punto</td>
                                          <td><input type="text" name="codPtoBase" id="codPtoBase" placeholder="Código punto" readonly required> </td>
                                      </tr>
                                      <tr>
                                          <td class="t">Direcci&oacute;n Latitud/Longitud</td>
                                          <td>
                                              <input type="text" name="dirLatLon" id="dirLatLon" placeholder="Dirección Latitud/Longitud" readonly>
                                              <input type="hidden" name="dirLatitud" id="dirLatitud" placeholder="Dirección Latitud" readonly required>
                                              <input type="hidden" name="dirLongitud" id="dirLongitud" placeholder="Dirección Longitud" readonly required>
                                          </td>
                                          <td class="t">* Radio</td>
                                          <td><input type="number" name="radio" id="radio" placeholder="Radio" value="1" min="1" max="250" required></td>
                                          <td class="t">* Tipo de punto</td>
                                          <td>
                                              <div id="tipoPunto" class="form-inline">
                                                  <label class="radio-inline"><input type="radio" name="rtipoPunto" id="rtipoPunto" 
                                                                                     value="base" onclick="setDireccionPunto(false);"> Base</label>
                                                  &nbsp;
                                                  <label class="radio-inline"><input type="radio" name="rtipoPunto" id="rtipoPunto" 
                                                                                     value="punto" onclick="setDireccionPunto(true);"> Punto control</label>
                                              </div>
                                          </td>
                                          <td class="t">* Direcci&oacute;n</td>
                                          <td>
                                              <select name="direccion" id="direccion" disabled>
                                                  <!-- <option value="3">Base</option> -->
                                                  <option value="1">Entrando</option>
                                                  <option value="2">Saliendo</option>
                                              </select>
                                          </td>
                                      </tr>                
                                      <tr>
                                          <td></td><td></td>
                                          <td></td><td></td>                    
                                          <td></td><td></td>                    
                                          <td colspan="2">
                                              <div id="group-insert" class="form-group">
                                                  <button class="btn btn-primary" type="submit" style="display: none;"></button>
                                              <c:if test="${permissions.check(142)}">
                                                  <button class="btn btn-primary" type="button" onclick="sendPunto('ins');">Agregar punto</button>
                                                  <button class="btn btn-primary" type="button" onclick="cancelPunto(); addCurrentMarks();">Cancelar</button>
                                              </c:if>
                                              <input type="hidden" name="maxcodPto"  id="maxcodPto"  value="${select.lstpunto[0].maxcod}">
                                              <input type="hidden" name="maxcodBase" id="maxcodBase" value="${select.lstbase[0].maxcod}">
                                              <input type="hidden" name="orden" id="orden">
                                          </div>
                                          <div id="group-update" class="form-group" style="display: none;">
                                              <c:if test="${permissions.check(52)}">
                                                  <button class="btn btn-primary" type="button" onclick="sendPunto('act');">Actualizar punto</button>
                                                  <button class="btn btn-primary" type="button" onclick="cancelPunto(); addCurrentMarks();">Cancelar</button>
                                              </c:if>
                                          </div>
                                      </td>                    
                                  </tr>
                              </table>
                          </div>
                          <%--</c:if>--%>
                    </form>

                </div>        

                <div style="margin-top: 48px;"></div>

                <div>
                    <table id="tablaPuntos" class="display puntero" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>Nombre</th>                    
                                <th>C&oacute;digo base/punto</th>
                                <th>Latitud</th>
                                <th>Longitud</th>
                                <th class="col-hidden-2">Direcci&oacute;n Latitud</th>
                                <th class="col-hidden-2">Direcci&oacute;n Longitud</th>
                                <th>Radio</th>
                                <th>Direcci&oacute;n</th>
                                    <c:if test="${permissions.check(52)}">
                                    <th></th>
                                    </c:if>
                                    <c:if test="${permissions.check(132)}">
                                    <th></th>
                                    </c:if>
                                <th class="col-hidden-2"></th>
                                <th class="col-hidden-2"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Lista de bases -->
                            <c:forEach items="${select.lstbase}" var="base">
                                <tr onclick="mostrarPunto(${base.latitudWeb}, ${base.longitudWeb}); resaltarPuntoEnTabla('tablaPuntos', this);">
                                    <td>${base.nombre}</td>                            
                                    <td>${base.identificador}</td>
                                    <td>${base.latitudWeb}</td>
                                    <td>${base.longitudWeb}</td>
                                    <td class="col-hidden-2">${base.direccionLatitud}</td>
                                    <td class="col-hidden-2">${base.direccionLongitud}</td>
                                    <td>${base.radio}</td>
                                    <td>
                                        <c:if test="${base.direccion == 3}">Base</c:if>
                                        </td>
                                    <c:if test="${permissions.check(52)}">
                                        <td>
                                            <button onclick="editarPunto(${base.idbase}, 'base');"
                                                    class="btn btn-primary btn-xs"
                                                    style="width: 80px;">Editar</button>
                                        </td>
                                    </c:if>
                                    <c:if test="${permissions.check(132)}">
                                        <td>
                                            <form id="form-base-delete-${base.idbase}" class="form-in-table" action="<c:url value='/eliminarPuntoBase' />" method="post">
                                                <!-- value="{base.codigoBase}"> -->
                                                <input type="hidden" name="idbase" id="idbase" value="${base.idbase}"> 
                                                <button type="button" class="btn btn-danger btn-xs"
                                                        onclick="confirmar_eliminacion(1, '${base.nombre}', ${base.idbase});"
                                                        style="width: 80px;">Eliminar</button>
                                            </form>
                                        </td>
                                    </c:if>
                                    <td class="col-hidden-2">base</td>
                                    <td class="col-hidden-2">${base.idbase}</td>
                                </tr>
                            </c:forEach>

                            <!-- Lista de puntos control -->
                            <c:forEach items="${select.lstpunto}" var="punto">
                                <tr onclick="mostrarPunto(${punto.latitudWeb}, ${punto.longitudWeb}); resaltarPuntoEnTabla('tablaPuntos', this);">
                                    <td>${punto.nombre}</td>                            
                                    <td>${punto.codigoPunto}</td>
                                    <td>${punto.latitudWeb}</td>
                                    <td>${punto.longitudWeb}</td>
                                    <td class="col-hidden-2">${punto.direccionLatitud}</td>
                                    <td class="col-hidden-2">${punto.direccionLongitud}</td>
                                    <td>${punto.radio}</td>
                                    <td>
                                        <c:if test="${punto.direccion == 1}">Entrando</c:if>
                                        <c:if test="${punto.direccion == 2}">Saliendo</c:if>                                
                                        </td>
                                    <c:if test="${permissions.check(52)}">
                                        <td>
                                            <button onclick="editarPunto(${punto.idpunto}, 'punto');"
                                                    class="btn btn-primary btn-xs"
                                                    style="width: 80px;">Editar</button>
                                        </td>
                                    </c:if>
                                    <c:if test="${permissions.check(132)}">
                                        <td>
                                            <form id="form-punto-delete-${punto.idpunto}" class="form-in-table" action="<c:url value='/eliminarPunto' />" method="post">
                                                <!-- value="{punto.codigoPunto}"> -->
                                                <input type="hidden" name="idpunto" id="idpunto" value="${punto.idpunto}"> 
                                                <button type="button" class="btn btn-danger btn-xs"
                                                        onclick="confirmar_eliminacion(2, '${punto.nombre}', ${punto.idpunto});"
                                                        style="width: 80px;">Eliminar</button>
                                            </form>
                                        </td>
                                    </c:if>
                                    <td class="col-hidden-2">punto</td>
                                    <td class="col-hidden-2">${punto.idpunto}</td>
                                </tr>
                            </c:forEach>
                        </tbody>                
                    </table>
                </div>                                        

                <div class="span12" style="margin-top: 10px;">
                    <form action="<c:url value='/repararCoordenadas' />" method="post">                        
                        <button type="submit">Reparar coordenadas</button>
                        <label><input type="checkbox" id="usar_gps" name="usar_gps">&nbsp;Usar coordenadas gps</label>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>        
</div>

<!-- Ubicacion geografica de la empresa -->
<input type="hidden" name="lat_empresa" id="lat_empresa" value="${emp.latitudWeb}" />
<input type="hidden" name="lon_empresa" id="lon_empresa" value="${emp.longitudWeb}" /> 

<!-- Puntos mas recientes -->
<div style="display: none;">
    <select id="lst_pto_rte" name="lst_pto_rte">
        <c:forEach items="${lst_pto_rte}" var="pto">
            <option value="${pto.idpunto}|${pto.codigoPunto}|${pto.nombre}|${pto.latitudWeb}|${pto.longitudWeb}|${pto.tipo}"></option>
        </c:forEach>
    </select>
</div>

<jsp:include page="/include/footerappMap.jsp" />
<script>

    // Tabla dinamica de puntos

    /*
     $(document).ready(function () {
     $('#tablaPunto')
     .addClass('nowrap')
     .dataTable( {
     responsive: true
     });
     });
     */

    $(document).ready(function () {
        
        $('#tablaPuntos').DataTable({
            "aLengthMenu": [7, 15, 20, 25],
            "sDom": '<"top"lfp>rt<"bottom"i>', /* default lfrtip */
            /*
             "columnDefs": [ {
             "targets": [4,5,10,11],
             "visible": false
             }], */
            responsive: true,
            scrollY: 500,
            searching: true,
            bAutoWidth: true,
            bInfo: false,
            paging: false
        });        
    });

    function confirmar_eliminacion(tipo, objeto, id) {

        var stipo = (tipo == 1) ? 'la base' : 'el punto';
        var sform = (tipo == 1) ? '#form-base-delete' : '#form-punto-delete';
        sform += '-' + id;
        var msg = "¿Est&aacute; seguro de eliminar " + stipo + " <strong>" + objeto + "</strong>?";

        bootbox.confirm({
            title: "Eliminaci&oacute;n de registro",
            message: msg,
            buttons: {
                confirm: {
                    label: 'Aceptar',
                    className: 'btn btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn'
                }
            },
            callback: function (rs) {
                if (rs) {
                    $(sform).submit();
                }
            }
        });
    }

</script>
<jsp:include page="/include/end.jsp" />
