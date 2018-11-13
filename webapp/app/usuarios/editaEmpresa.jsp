
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--
            <div class="col-md-9 top-space bottom-space">
                <a href="#"><strong>> Empresas</strong></a>
            </div>-->

            <!-- Body container -->
            <div class="col-lg-7 centered">
                <h1 class="display-3 bg-info text-center">M&Oacute;DULO EMPRESAS</h1>    
                <section class="boxed padding">
                    <ul class="nav nav-tabs">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaEmpresa.jsp">Nueva empresa</a></li>
                        <li role="presentation" class="active"><a href="#">Editar empresa</a></li>
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaEmpresa.jsp">Listado empresas</a></li>
                    </ul>
                                
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-actualiza-empresa" class="form-horizontal" action="<c:url value='/editarEmpresa' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" value="${emp.nombre}" maxlength="50" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Nit</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nit" id="nit" value="${emp.nit}" maxlength="50" required>
                                    <span id="msg_nit"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Pa&iacute;s</label>
                                <div class="controls">
                                    <select name="spais" id="spais" onchange="filtrarDpto();" class="form-control">
                                        <option></option>
                                        <c:forEach items="${select.lstpais}" var="pais">
                                            <option value="${pais.id}">${pais.nombre}</option>
                                        </c:forEach>                                    
                                    </select>
                                    <span id="msg_spais"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Departamento</label>
                                <div class="controls">
                                    <select name="sdpto" id="sdpto" onchange="filtrarCiudad();" class="form-control">
                                        <option></option>
                                        <c:forEach items="${select.lstdpto}" var="dpto">
                                            <option value="${dpto.idpais},${dpto.id}">${dpto.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sdpto"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Ciudad</label>
                                <div class="controls">
                                    <select name="sciudad" id="sciudad" class="form-control">
                                        <option></option>
                                        <c:forEach items="${select.lstciudad}" var="ciudad">
                                            <option value="${ciudad.iddpto},${ciudad.id}">${ciudad.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sciudad"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">Direcci&oacute;n</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="direccion" id="direccion" value="${emp.direccion}" maxlength="50">
                                    <span id="msg_direccion"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">Tel&eacute;fono</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="telefono" id="telefono" value="${emp.telefono}" maxlength="20">
                                    <span id="msg_telefono"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">P&aacute;gina web</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="paginaweb" id="paginaweb" value="${emp.paginaweb}" maxlength="50">
                                    <span id="msg_paginaweb"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">Correo electr&oacute;nico</label>
                                <div class="controls">
                                    <input type="email" class="form-control" name="email" id="email" value="${emp.email}" maxlength="50">
                                    <span id="msg_email"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Moneda</label>
                                <div class="controls">
                                    <select id="smoneda" name="smoneda" class="form-control">
                                        <option></option>
                                        <c:forEach items="${select.lst_moneda}" var="moneda">
                                            <option value="${moneda.id}">${moneda.codigoISO}&emsp;${moneda.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_smoneda"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Zona horaria</label>
                                <div class="controls">
                                    <select id="szonahoraria" name="szonahoraria" class="form-control">
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
                                    <button style="width: 126px;"  type="button" class="btn" onclick="sendEmpresa('act');">Guardar</button>
                                </div>
                            </div>          
                            <input type="hidden" name="id" id="id" value="${emp.id}">
                            <input type="hidden" name="ipais" id="ipais" value="${emp.idpais}">
                            <input type="hidden" name="idpto" id="idpto" value="${emp.iddpto}">
                            <input type="hidden" name="iciudad" id="iciudad" value="${emp.idciudad}">
                            <input type="hidden" name="imoneda" id="imoneda" value="${emp.idmoneda}">
                            <input type="hidden" name="izonahoraria" id="izonahoraria" value="${emp.idzonahoraria}">
                            <input type="hidden" name="a_nit" id="a_nit" value="${emp.nit}">
                        </form>
                        </div>
                    </div>
                </section>
            </div>

<jsp:include page="/include/footerHome.jsp" />
<script>
    // Establecemos ubicacion (pais, dpto, ciudad) del registro a editar
    setUbicacion();
</script>
<jsp:include page="/include/end.jsp" />