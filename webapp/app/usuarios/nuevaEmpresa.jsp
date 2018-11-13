
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Body container -->
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO EMPRESAS</h1>    
    <c:choose>
        <c:when test="${permissions.check(['Configuracion','Empresa','RegistrarEmpresas'])}">        
            <section class="boxed padding">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/usuarios/nuevaEmpresa.jsp">Nueva Empresa</a></li>
                        <c:if test="${permissions.check(98)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaEmpresa.jsp">Listado Empresas</a></li>
                        </c:if>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-nueva-empresa" class="form-horizontal" action="<c:url value='/guardarEmpresa' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <!-- <div><p>{lnk}</p></div> -->
                            <div class="control-group col-md-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" maxlength="50" title="Digite el nombre" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Nit</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nit" id="nit" maxlength="50" title="Digite el nit" required>
                                    <span id="msg_nit"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Pa&iacute;s</label>
                                <div class="controls">
                                    <select name="spais" id="spais" onchange="filtrarDpto();" class="form-control" title="Seleccione el pais">
                                        <option></option>
                                        <c:forEach items="${select.lstpais}" var="pais">
                                            <option value="${pais.id}">${pais.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_spais"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Departamento</label>
                                <div class="controls">
                                    <select name="sdpto" id="sdpto" onchange="filtrarCiudad();" class="form-control" title="Seleccione el departamento">
                                        <option></option>
                                        <c:forEach items="${select.lstdpto}" var="dpto">
                                            <option value="${dpto.idpais},${dpto.id}">${dpto.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sdpto"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Ciudad</label>
                                <div class="controls">
                                    <select name="sciudad" id="sciudad" class="form-control" title="Seleccione la ciudad">
                                        <option></option>
                                        <c:forEach items="${select.lstciudad}" var="ciudad">
                                            <option value="${ciudad.iddpto},${ciudad.id}">${ciudad.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sciudad"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Direcci&oacute;n</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="direccion" id="direccion" maxlength="50" title="Digite la direcci&oacute;n">
                                    <span id="msg_direccion"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Tel&eacute;fono</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="telefono" id="telefono" maxlength="20" title="Digite el n&uacute;mero de telefono">
                                    <span id="msg_telefono"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">P&aacute;gina web</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="paginaweb" id="paginaweb" maxlength="50" title="Digite la url del sitio web">
                                    <span id="msg_paginaweb"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">Correo electr&oacute;nico</label>
                                <div class="controls">
                                    <input type="email" class="form-control" name="email" id="email" maxlength="50" title="Digite la direcci&oacute;n de correo">
                                    <span id="msg_email"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Moneda</label>
                                <div class="controls">
                                    <!-- <input type="text" class="form-control" name="moneda" id="moneda" maxlength="20"> -->
                                    <select id="smoneda" name="smoneda" class="form-control" title="Seleccione la moneda local">
                                        <option></option>
                                        <c:forEach items="${select.lst_moneda}" var="moneda">
                                            <option value="${moneda.id}">${moneda.codigoISO}&emsp;${moneda.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_smoneda"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Zona horaria</label>
                                <div class="controls">
                                    <!-- <input type="text" class="form-control" name="zonahoraria" id="zonahoraria" maxlength="20"> -->
                                    <select id="szonahoraria" name="szonahoraria" class="form-control" title="Seleccione la zona horaria">
                                        <option></option>
                                        <c:forEach items="${select.lst_zonahoraria}" var="zonahoraria">
                                            <option value="${zonahoraria.id}">${zonahoraria.GMT}&emsp;${zonahoraria.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_szonahoraria"></span>
                                </div>
                            </div>
                            <div class="form-group">                            
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <button style="display: none;" type="submit" class="btn"></button>
                                    <button style="width: 126px;" type="button" class="btn" onclick="sendEmpresa('ins');" title="Haga clic aqui para guardar">Guardar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </section>        
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome.jsp" />
<script>
    // Ocultamos los valores de ubicacion (dpto, ciudad)
    hiddenOptSelectArray(["sdpto", "sciudad"]);
</script>
<jsp:include page="/include/end.jsp" />

