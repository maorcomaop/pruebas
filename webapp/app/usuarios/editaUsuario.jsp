
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--
<div class="col-md-9 top-space bottom-space">
    <a href="#"><strong>> Usuarios</strong></a>
</div>
-->

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO USUARIOS</h1>    
    <section class="boxed padding">                    
        <ul class="nav nav-tabs">
            <li role="presentation"><a href="/RDW1/app/usuarios/nuevoUsuario.jsp">Registro Usuario</a></li>
            <li role="presentation" class="active"><a href="#">Editar Usuario</a></li>
            <li role="presentation"><a href="/RDW1/app/usuarios/consultaUsuario.jsp">Listado Usuarios</a></li>
            <li role="presentation"><a href="<c:url value='/nuevoPerfil' />">Registro Perfil</a></li>
            <li role="presentation"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado Perfiles</a></li>                    
        </ul>    

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active">                                 

                <form id="form-actualiza-usuario" class="form-horizontal" action="<c:url value='/editarUsuario' />" method="post">
                    <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">* Nombre</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="nombre" id="nombre" value="${usr.nombre}" maxlength="50" required>
                            <span id="msg_nombre"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">* Apellido</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="apellido" id="apellido" value="${usr.apellido}" maxlength="50" required>
                            <span id="msg_apellido"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">* Empresa</label>
                        <div class="controls">
                            <select id="sempresa" name="sempresa" class="form-control">
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
                            <select name="sperfil" id="sperfil" class="form-control">
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
                            <select name="stipodoc" id="stipodoc" class="form-control">
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
                            <input type="text" class="form-control" name="numdoc" id="numdoc" value="${usr.numdoc}" maxlength="13" required>
                            <span id="msg_numdoc"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">Direcci&oacute;n</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="direccion" id="direccion" maxlength="50">
                            <span id="msg_direccion"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">Tel&eacute;fono</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="telefono" id="telefono" maxlength="20">
                            <span id="msg_telefono"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">M&oacute;vil</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="movil" id="movil" maxlength="20">
                            <span id="msg_movil"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">* Correo electr&oacute;nico</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="email_data" id="email_data" value="${usr.email}" maxlength="50" required>
                            <span id="msg_email_data"></span>
                        </div>
                    </div>
                    <div class="control-group col-sm-7">
                        <label class="control-label">* Nombre de usuario</label>
                        <div class="controls">
                            <input type="text" class="form-control" name="login" id="login" value="${usr.login}" placeholder="Al menos de 4 caracteres" 
                                   maxlength="20" required>
                            <span id="msg_login"></span>
                        </div>
                    </div>                                    
                    <div class="form-group">                            
                        <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                            <button style="display:none;" type="submit" class="btn"></button>
                            <button style="width: 126px;" type="button" class="btn" onclick="sendUsuario('act');">Guardar</button>
                        </div>
                    </div>
                    <input type="hidden" name="iempresa" id="iempresa" value="${usr.idempresa}">
                    <input type="hidden" name="iperfil"  id="iperfil"  value="${usr.idperfil}">
                    <input type="hidden" name="itipodoc" id="itipodoc" value="${usr.idtipodoc}">
                    <input type="hidden" name="a_numdoc" id="a_numdoc" value="${usr.a_numdoc}">
                    <input type="hidden" name="a_email"  id="a_email"  value="${usr.a_email}">
                    <input type="hidden" name="a_login"  id="a_login"  value="${usr.a_login}">
                </form>
            </div>
        </div>
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />
<script>
    // Ubica perfil a editar en componente select
    selectElement();
</script>
<jsp:include page="/include/end.jsp" />
