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
                        <a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos de control</a>
                    </c:if>
                    -
                    <c:if test="${permissions.check(53)}">
                        <a href="#"><strong>Importar/Exportar</strong></a>
                    </c:if>
                </div>
                
                <div>
                    <section>                        
                        <div id="map"></div>
                        
                        <div class="form-group pull-right">
                            <button class="btn btn-default" onclick="removeListaPuntos();">Remover puntos</button>
                            <button class="btn btn-primary" onclick="sendListaPuntos();">Almacenar puntos</button>
                        </div>
                        <form id="form-lista-puntos" action="<c:url value='/guardarListaPuntos' />" method="post">
                            <input type="hidden" name="lastCod" id="lastCod" value="${select.lstpunto[0].maxcod}">
                            <input type="hidden" name="listaPuntos" id="listaPuntos">                    
                        </form>
                        <form id="form-lista-puntos-kml" action="<c:url value='/guardarListaPuntosKML' />" method="post">
                            <input type="hidden" name="puntosKML" id="puntosKML" value="${puntosKML}">
                        </form>
                    </section>
                </div>       

                <div>
                    <div id="msg" style="width: 256px;" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                    <table>
                        <tr>
                            <td>Latitud</td>
                            <td>&nbsp;<input type="text" name="latitud" id="latitud" placeholder="Latitud" readonly required></td>
                        </tr>
                        <tr>
                            <td>Longitud</td>
                            <td>&nbsp;<input type="text" name="longitud" id="longitud" placeholder="Longitud" readonly required></td>
                        </tr>
                        <tr>
                            <td>Dirección latitud</td>
                            <td>&nbsp;<input type="text" name="dirLatitud" id="dirLatitud" placeholder="Dirección Latitud" readonly required></td>
                        </tr>
                        <tr>
                            <td>Dirección longitud</td>
                            <td>&nbsp;<input type="text" name="dirLongitud" id="dirLongitud" placeholder="Dirección Longitud" readonly required></td>
                        </tr>
                    </table>

                    <!-- ***** Formato JSON -->
                    <table style="margin-top: 10px;">
                        <tr><td colspan="2"><label>+ Formato JSON</label></td></tr>
                        <tr>
                            <td colspan="2"><span class="label label-info" id="upload-info-file"></span></td>
                        </tr>
                        <tr>
                            <td>
                                <label class="btn btn-default">
                                    <input type="file" id="archivoPuntos" style="display:none;"
                                           onchange="$('#upload-info-file').html($(this).val());">
                                    Archivo
                                </label>
                            </td>
                            <td>
                                <label class="btn btn-default">
                                    <input id="uploadLink" type="button" style="display: none;">&nbsp;&nbsp;Importar puntos&nbsp;&nbsp;
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <a class="btn btn-default" style="width: 100%;" href="#" 
                                   id="downloadLink" download="data.json" onclick="exportToJsonFile();">Exportar puntos</a>
                            </td>
                        </tr>
                    </table>

                    <!-- ***** Formato KML -->
                    <form action="<c:url value='/importarPuntosKML' />" method="post" enctype="multipart/form-data">
                        <table style="margin-top: 10px;">
                            <tr><td colspan="2"><label>+ Formato KML</label></td></tr>
                            <tr>
                                <td colspan="2"><span class="label label-info" id="upload-info-file-kml"></span></td>
                            </tr>
                            <tr>
                                <td>
                                    <label class="btn btn-default">
                                        <input type="file" name="archivoPuntosKML" id="archivoPuntosKML" style="display:none;"
                                               onchange="$('#upload-info-file-kml').html($(this).val());">
                                        Archivo
                                    </label>
                                </td>
                                <td>
                                    <label class="btn btn-default">
                                        <input id="uploadLinkKML" type="submit" style="display: none;">&nbsp;&nbsp;&nbsp;&nbsp;Importar KML&nbsp;&nbsp;&nbsp;
                                    </label>                
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <a class="btn btn-default" style="width: 100%;" href="#" 
                                       id="downloadLinkKML" download="data.kml" onclick="exportToKMLFile();">Exportar a KML</a>
                                </td>
                            </tr>
                        </table>            
                    </form>

                    <select id="spuntosKML" style="display: none;">
                        <c:forEach items="${lstpuntosKML}" var="puntoKML">
                            <option value="${puntoKML.latitudWeb},${puntoKML.longitudWeb}"></option>
                        </c:forEach>
                    </select>
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

<jsp:include page="/include/footerappMap.jsp" />
<script>
    window.onload = function () {
        onlyOnePoint = false;
        visualizaPuntosKML();
    };
</script>
<jsp:include page="/include/end.jsp" />
