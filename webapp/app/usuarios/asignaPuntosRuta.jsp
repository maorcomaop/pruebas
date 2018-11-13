
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerappRutaNew.jsp" />
<!--
<div class="col-md-9 top-space bottom-space">
    <a href="#"><strong>> Rutas</strong></a>
</div>-->

<!-- Body container -->
<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PUNTOS DE CONTROL</h1>        
        <c:choose>
            <c:when test="${permissions.check(126)}">
                <section class="boxed padding-min">
                    <ul class="nav nav-tabs">
                        <c:if test="${permissions.check(50)}">
                            <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva Ruta</a></li>
                            </c:if>
                            <c:if test="${permissions.check(126)}">
                            <li role="presentation"  class="active"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignación Puntos</a></li>
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
                            <li role="presentation"><a href="/RDW1/app/usuarios/nuevoPunto.jsp">Puntos</a>
                            </c:if>
                    </ul>

                    <div id="map" style="height: 456px;"></div> <!-- 396 -->

                    <!-- Seleccion de ruta -->                

                    <div style="margin-top: 6px;">                      
                        <div id="msg" class="form-msg ${msgType}" role="alert">${msg}</div>
                        &nbsp; <span style="color:red;">*</span> &nbsp;
                        <select id="sruta" name="sruta" onchange="marcarRuta2();">
                            <option value="">Seleccione una ruta</option>
                            <c:forEach items="${select.lstruta}" var="ruta">
                                <option value="${ruta.id}">${ruta.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </section>                                     

                <section id="asigna_puntos" class="boxed padding-min">
                    <div>
                        <!-- Lista total de puntos (Para ser agregados a ruta) -->                            
                        <c:choose>
                            <c:when test="${permissions.check(129)}">
                                <select class="select" id="listaPuntos" size="10" multiple="multiple">
                            </c:when>
                            <c:otherwise>
                                <select class="select" id="listaPuntos" size="10" style="display: none;">
                            </c:otherwise>
                        </c:choose>
                        <c:forEach items="${select.lstpunto}" var="punto">
                            <c:if test='${punto.nombre != null && punto.nombre != ""}'>
                                <option value="${punto.idpunto},${punto.codigoPunto},${punto.nombre},${punto.latitudWeb},${punto.longitudWeb}"> ${punto.nombre}</option>
                            </c:if>
                        </c:forEach>
                        </select>
                        <input type="hidden" id="ruta_editada" name="ruta_editada" value="${ruta_editada}">
                    </div>
                    <c:if test="${permissions.check(129)}">
                        <div class="buttons">
                            <input type="button" class="btn btn-default" id="btnRight"  value=">>"    onclick="toRight();"   />
                            <input type="button" class="btn btn-default" id="btnLeft"   value="<<"    onclick="toRemove();"    />                        
                            <input type="button" class="btn btn-default" id="btnUp"     value="subir" onclick="up();"        />
                            <input type="button" class="btn btn-default" id="btnDown"   value="bajar" onclick="down();"      />
                            <!-- <input type="button" class="btn" id="btnInfo"	value="info" onclick="info();" /> -->
                        </div>
                    </c:if>
                    <div>
                        <select class="select" id="listaPuntosRuta" size="10" onclick="viewPoint();" multiple>
                        </select>                    
                    </div>
                    <c:if test="${permissions.check(129)}">
                        <div class="buttons">
                            <input type="button" class="btn btn-default" id="btnRightAll"   value=">>|"  onclick="toRightAll();" />
                            <input type="button" class="btn btn-default" id="btnLeftAll"    value="|<<"  onclick="removeAll();"  />

                            <div style="padding-top: 10px;">
                                <!-- Seleccion de bases -->
                                <button type="button" class="btn btn-default" style="width: 124px;"
                                        data-toggle="modal" data-target="#myModal">Agregar bases</button>

                                <!-- Guardar puntos ruta -->
                                <form id="form-nueva-ruta" action="<c:url value='/guardarPuntosRuta' />" method="post" style="padding-top: 12px;">
                                    <input type="hidden" id="ruta" name="ruta">
                                    <input type="hidden" id="puntos" name="puntos">
                                    <button type="button" class="btn btn-primary" style="width: 124px;" onclick="sendPuntosRuta2();">Agregar puntos</button>
                                </form>                    
                            </div>
                        </div>
                    </c:if>
                </section>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
</div>            

<!-- Puntos de cada ruta que estan relacionados -->
<div style="display: none;">
    <select id="listaPuntosInit" name="listaPuntosInit">
        <c:forEach items="${select.lstpuntoruta}" var="puntoruta">
            <option value="${puntoruta.idRuta},${puntoruta.idPunto},${puntoruta.estado}"></option>
        </c:forEach>
    </select>
</div>

<!-- Bases de cada ruta que estan relacionados -->
<div style="display: none;">
    <select id="listaBasesInit" name="listaBasesInit">
        <c:forEach items="${select.lstbaseruta}" var="baseruta">
            <option value="${baseruta.idRuta},${baseruta.tipo},${baseruta.idBase},${baseruta.nombreBase},${baseruta.latitudWeb},${baseruta.longitudWeb}"></option>                        
        </c:forEach>
    </select>
</div>

<!-- Modal (Seleccion de bases) -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Seleccione las bases</h4>
            </div>
            <div class="modal-body">
                <div class="control-group">
                    <label class="control-label">Base salida</label>
                    <div class="controls">
                        <select id="sbasesalida">
                            <c:forEach items="${select.lstbase}" var="base">
                                <option value="${base.idbase},${base.nombre},${base.latitudWeb},${base.longitudWeb}">${base.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Base llegada</label>
                    <div class="controls">
                        <select id="sbasellegada">
                            <c:forEach items="${select.lstbase}" var="base">
                                <option value="${base.idbase},${base.nombre},${base.latitudWeb},${base.longitudWeb}">${base.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="addBases();">Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- Ubicacion geografica de la empresa -->
<input type="hidden" name="lat_empresa" id="lat_empresa" value="${emp.latitudWeb}" />
<input type="hidden" name="lon_empresa" id="lon_empresa" value="${emp.longitudWeb}" />                            

<jsp:include page="/include/footerappRuta.jsp" />
<script>
    window.onload = function () {
        iniciarMapa();
        verificaRutaEditada();
    };
</script>
<jsp:include page="/include/end.jsp" />