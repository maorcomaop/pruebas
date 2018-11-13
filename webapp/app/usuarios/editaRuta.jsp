
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--
            <div class="col-md-9 top-space bottom-space">
                <a href="#"><strong>> Rutas</strong></a>
            </div>-->
    
            <!-- Body container -->
            <div class="col-lg-7 centered">
                <h1 class="display-3 bg-info text-center">M&Oacute;DULO RUTAS</h1>    
                <section class="boxed padding">                    
                    <ul class="nav nav-tabs">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaRuta.jsp">Nueva ruta</a></li>
                        <li role="presentation" class="active"><a href="#">Editar ruta</a></li>                    
                        <li role="presentation"><a href="/RDW1/app/usuarios/asignaPuntosRuta.jsp">Asignar puntos</a></li>
                        <li role="presentacion"><a href="/RDW1/app/usuarios/editaPuntosRuta.jsp">Editar puntos</a></li>
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaRuta.jsp">Listado rutas</a></li>
                        <li role="presentacion"><a href="/RDW1/app/usuarios/longitudRuta.jsp">Longitud ruta</a></li>
                    </ul>                
                
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-actualiza-ruta" class="form-horizontal" action="<c:url value='/editarRuta' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                        
                            <div class="control-group col-md-7">
                                <label class="control-label">* Empresa</label>
                                <div class="controls">
                                    <select name="sempresa" id="sempresa" class="form-control">
                                        <option></option>
                                        <c:forEach items="${select.lstempresa}" var="empresa">
                                            <option value="${empresa.id}">${empresa.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sempresa"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" value="${ruta.nombre}" 
                                           maxlength="50" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="form-group">                            
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <button style="display: none;" type="submit" class="btn"></button>
                                    <button style="width:126px;" type="button" class="btn" onclick="sendRuta('act');">Guardar</button>
                                </div>
                            </div>                        
                            <input type="hidden" name="prevempresa" id="prevempresa" value="${ruta.idEmpresa}">
                            <input type="hidden" name="idRuta" id="idRuta" value="${ruta.id}">
                        </form>
                        </div>
                    </div>
                </section>
            </div>

<jsp:include page="/include/footerHome.jsp" />
<script>
    // Ubica empresa en componente select
    selectElement();
</script>
<jsp:include page="/include/end.jsp" />