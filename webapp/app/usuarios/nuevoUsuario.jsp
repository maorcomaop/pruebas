
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--
            <div class="col-md-9 top-space bottom-space">
                <a href="#"><strong> Usuarios</strong></a>
            </div>-->

<!-- Body container -->
<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO USUARIOS</h1>    
    <section class="boxed padding"> 
        <c:choose>
            <c:when test="${permissions.check(9)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Registro Usuario</a></li>                    

                    <c:if test="${permissions.check(90)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaUsuario.jsp">Listado Usuarios</a></li>
                    </c:if>
                    <c:if test="${permissions.check(12)}">
                        <li role="presentation"><a href="<c:url value='/nuevoPerfil' />">Registro Perfil</a></li>
                    </c:if>
                    <c:if test="${permissions.check(11)}">
                        <li role="presentation"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado Perfiles</a></li>                    
                    </c:if>
                </ul>                

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active">                    

                        <form role="form" id="form-nuevo-usuario" class="form-horizontal" action="<c:url value='/guardarUsuario' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <!-- <div class="form-msg"><p>{lnk}</p></div> -->
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" maxlength="50" title="Digite el nombre" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Apellido</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="apellido" id="apellido" maxlength="50" title="Digite el apellido" required>
                                    <span id="msg_apellido"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Empresa</label>
                                <div class="controls">
                                    <select id="sempresa" name="sempresa" class="form-control" title="Seleccione la empresa">
                                        <option></option>
                                        <c:forEach items="${select.lstempresa}" var="empresa">
                                            <option value="${empresa.id}">${empresa.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sempresa"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Perfil</label>
                                <div class="controls">
                                    <select name="sperfil" id="sperfil" class="form-control" title="Seleecione el perfil">
                                        <option></option>
                                        <c:forEach items="${select.lstperfil}" var="perfil">
                                            <option value="${perfil.id}">${perfil.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_sperfil"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Tipo de documento</label>
                                <div class="controls">                                
                                    <select name="stipodoc" id="stipodoc" class="form-control" title="Digite el tipo de documento">
                                        <option></option>
                                        <c:forEach items="${select.lst_tipodocumento}" var="tp">
                                            <option value="${tp.id}">${tp.tipo}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="msg_stipodoc"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* N&uacute;mero de documento</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="numdoc" id="numdoc" maxlength="13" title="Digite el n&uacute;mero de documento" required>
                                    <span id="msg_numdoc"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">Direcci&oacute;n</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="direccion" id="direccion" title="Digite la direccion" maxlength="50">
                                    <span id="msg_direccion"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">Tel&eacute;fono</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="telefono" id="telefono" title="Digite el n&uacute;mero de telefono" maxlength="20">
                                    <span id="msg_telefono"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">M&oacute;vil</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="movil" id="movil" title="Digite el n&uacute;mero celular" maxlength="20">
                                    <span id="msg_movil"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Correo electr&oacute;nico</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="email_data" id="email_data" title="Digite la direcci&oacute;n de correo" maxlength="50" required>
                                    <span id="msg_email_data"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Nombre de usuario</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="login" id="login" placeholder="Al menos de 4 caracteres" title="Digite el usuario" maxlength="20" required>
                                    <span id="msg_login"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Contrase&ntilde;a</label>
                                <div class="controls">
                                    <input type="password" class="form-control" name="pass" id="pass" placeholder="Al menos de 8 caracteres" title="Digite la contraseña" maxlength="20" required>
                                    <span id="msg_pass"></span>
                                </div>
                            </div>
                            <div class="control-group col-sm-7">
                                <label class="control-label">* Confirmar contrase&ntilde;a</label>
                                <div class="controls">
                                    <input type="password" class="form-control" name="cpass" id="cpass" maxlength="20" title="Digite nuevamente la contraseña" required>
                                    <span id="msg_cpass"></span>
                                </div>
                            </div>                             
                            <div class="form-group">                                                                                    
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <button style="display:none;" type="submit" class="btn"></button>
                                    <button style="width:126px;" type="button" class="btn" title="Haga clic para guardar" onclick="sendUsuario('ins');">Guardar</button>                            
                                </div>
                            </div>
                        </form>                    
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />
<jsp:include page="/include/end.jsp" />

