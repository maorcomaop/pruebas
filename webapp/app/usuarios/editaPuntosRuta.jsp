<jsp:include page="/include/headerHomeNew.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
            <div class="col-md-9 top-space bottom-space">
                <a href="#"><strong>> Rutas</strong></a>
            </div>-->

            <!-- Body container -->
            <div class="col-lg-10 centered">
                <h1 class="display-3 bg-info text-center">M&Oacute;DULO PUNTOS DE CONTROL</h1>    
                <section class="boxed padding-min">
                    <ul class="nav nav-tabs">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva ruta</a></li>
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignar puntos</a></li>
                        <li role="presentacion" class="active"><a href="/RDW1/app/usuarios/editaPuntosRuta.jsp">Editar puntos</a></li>
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado rutas</a></li>
                        <li role="presentacion"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud ruta</a></li>
                    </ul>

                    <div id="map" style="height: 396px;"></div>                
                
                    <div>                
                        <!-- Seleccion de ruta -->                
                        <div id="msg" class="form-msg ${msgType}" role="alert">${msg}</div>
                        <div style="float: left; margin-top: 6px;">
                            &nbsp; <span style="color:red;">*</span> &nbsp;
                            <select id="sruta" name="sruta">
                                <option value="">Seleccione una ruta</option>
                                <c:forEach items="${select.lstruta}" var="ruta">
                                    <option value="${ruta.id}">${ruta.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Actualizacion de puntos ruta -->
                        <div style="float: left;" class="left-space">
                        <form id="form-actualiza-ruta" action="<c:url value='/actualizarPuntosRuta' />" method="post">
                                <input type="hidden" id="updPuntosRuta" name="updPuntosRuta">
                                <input type="hidden" id="idRuta" name="idRuta">
                                <button type="button" class="btn btn-default" disabled="disabled" onclick="sendPuntosRutaUPD();">Actualizar puntos</button>
                        </form>
                        </div>
                    </div>
                </section>
                
                <!-- Bases de ruta -->
                <div id="divtablaBasesRuta" style="display: none;">
                    <table id="tablaBasesRuta" class="display" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>                                
                                <th>Nombre base</th>
                                <th>Nombre ruta</th>
                                <th>Promedio minutos</th>
                                <th>Holgura minutos</th>
                                <th>Valor punto</th>
                                <th>Tipo</th>
                            </tr>
                        </thead>                        
                        <tbody>
                            <c:forEach items="${select.lstbaseruta}" var="baseruta">
                                <tr>
                                    <td style="display: none;">${baseruta.id}</td>
                                    <td style="display: none;">${baseruta.idRuta}</td>
                                    <td style="display: none;">${baseruta.idPunto}</td>                                    
                                    <td>
                                        <label onclick="viewMapPoint(${baseruta.latitudWeb},${baseruta.longitudWeb});">
                                            ${baseruta.nombreBase}
                                        </label>
                                    </td>
                                    <td>${baseruta.nombreRuta}</td>
                                    <td><input type="text" value="${baseruta.promedioMinutos}"  pattern="^[0-9]{1,8}$"></td>
                                    <td><input type="text" value="${baseruta.holguraMinutos}"   pattern="^[0-9]{1,8}$"></td>
                                    <td><input type="text" value="${baseruta.valorPunto}"       pattern="^[0-9]{1,8}$"></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${baseruta.tipo == 1}">
                                                <select>
                                                    <option value="0"></option>
                                                    <option value="1" selected>Base salida</option>
                                                    <option value="2">Punto</option>
                                                    <option value="3">Base llegada</option>
                                                </select>
                                            </c:when>
                                            <c:when test="${baseruta.tipo == 2}">
                                                <select>
                                                    <option value="0"></option>
                                                    <option value="1">Base salida</option>
                                                    <option value="2" selected>Punto</option>
                                                    <option value="3">Base llegada</option>
                                                </select>
                                            </c:when>
                                            <c:when test="${baseruta.tipo == 3}">
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
                
                <!-- Puntos de ruta -->
                <div id="divtablaPuntosRuta" style="display: none; margin-top: 25px;">
                    <table id="tablaPuntosRuta" class="display" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>                                
                                <th>Nombre punto</th>
                                <th>Nombre ruta</th>
                                <th>Promedio minutos</th>
                                <th>Holgura minutos</th>
                                <th>Valor punto</th>
                                <th>Tipo</th>
                            </tr>
                        </thead>                        
                        <tbody>
                            <c:forEach items="${select.lstpuntoruta}" var="puntoruta">
                                <tr>
                                    <td style="display: none;">${puntoruta.id}</td>
                                    <td style="display: none;">${puntoruta.idRuta}</td>
                                    <td style="display: none;">${puntoruta.idPunto}</td>                                    
                                    <td>
                                        <label onclick="viewMapPoint(${puntoruta.latitudWeb},${puntoruta.longitudWeb});">
                                            ${puntoruta.nombrePunto}
                                        </label>
                                    </td>
                                    <td>${puntoruta.nombreRuta}</td>
                                    <td><input type="text" value="${puntoruta.promedioMinutos}" pattern="^[0-9]{1,8}$"></td>
                                    <td><input type="text" value="${puntoruta.holguraMinutos}"  pattern="^[0-9]{1,8}$"></td>
                                    <td><input type="text" value="${puntoruta.valorPunto}"      pattern="^[0-9]{1,8}$"></td>
                                    <td>
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
            </div>
                       
            <!-- Puntos de cada ruta que estan relacionados -->
            <div style="display: none;">
                <select id="ispuntosruta" name="ispuntosruta">
                    <c:forEach items="${select.lstpuntoruta}" var="puntoruta">
                        <option value="${puntoruta.idRuta},${puntoruta.nombrePunto},${puntoruta.latitudWeb},${puntoruta.longitudWeb}"></option>
                    </c:forEach>
                </select>
            </div>
            <!-- Bases de cada ruta que estan relacionadas -->
            <div style="display: none;">
                <select id="isbasesruta" name="isbasesruta">
                    <c:forEach items="${select.lstbaseruta}" var="baseruta">
                        <option value="${baseruta.idRuta},${baseruta.nombreBase},${baseruta.latitudWeb},${baseruta.longitudWeb}"></option>
                    </c:forEach>
                </select>
            </div>
            
<!-- Ubicacion geografica de la empresa -->
<input type="hidden" name="lat_empresa" id="lat_empresa" value="${emp.latitudWeb}" />
<input type="hidden" name="lon_empresa" id="lon_empresa" value="${emp.longitudWeb}" />                 

<jsp:include page="/include/footerappRuta.jsp" />
<script>
    window.onload = function () {          
        iniciarMapa();
        window.location.replace("/RDW1/app/usuarios/editaPuntosRuta_Dph.jsp");
    };    
    
    // Tabla de bases
    $(document).ready(function() {
        $('#tablaBasesRuta').DataTable ( {
            "paging": false,
            "info": false,
            "sDom": '<"top">rt<"bottom"><"clear">',
            "bSort": false,
            responsive: true
        });
    });
    
    // Tabla dinamica de puntos ruta
    $(document).ready(function() {                
        $('#tablaPuntosRuta').DataTable({
            "aLengthMenu": [30,45,100],
            "bSort": false,
            responsive: true
        });
    });
</script>
<jsp:include page="/include/end.jsp" />