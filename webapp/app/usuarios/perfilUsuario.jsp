
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<!--
<div class="col-md-7 top-space bottom-space">
    <a href="#"><strong>> Perfil usuario</strong></a>
</div>-->
    
<div class="col-lg-7 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO PERFILES</h1>        
    <section class="boxed padding">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">Perfil de usuario</a></li>
            <li role="presentation"><a href="/RDW1/app/usuarios/perfilUsuario_pass.jsp">Cuenta</a></li>
        </ul>    
    
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active">                    

            <form id="form-actualiza-perfil" action="<c:url value='/actualizarPerfilUsuario' />" method="post" class="form-horizontal">
                <div id="msg" class="form-msg bottom-space ${msgType}">${msg}</div>
                <div class="control-group col-md-7">
                    <label class="control-label">* Nombre</label>
                    <div class="controls">
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${login.nombre}" maxlength="50" required>
                        <span id="msg_nombre"></span>
                    </div>
                </div>
                <div class="control-group col-md-7">
                    <label class="control-label">* Apellido</label>
                    <div class="controls">
                        <input type="text" class="form-control" id="apellido" name="apellido" value="${login.apellido}" maxlength="50" required>
                        <span id="msg_apellido"></span>
                    </div>
                </div>            
                <div class="control-group col-md-7">
                    <label class="control-label">* N&uacute;mero de documento</label>
                    <div class="controls">
                        <input type="text" class="form-control" id="numdoc" name="numdoc" value="${login.numdoc}" maxlength="10" readonly required>
                        <span id="msg_numdoc"></span>
                    </div>
                </div>            
                <div class="control-group col-md-7">
                    <label class="control-label">* Correo electr&oacute;nico</label>
                    <div class="controls">
                        <input type="email" class="form-control" id="email_data" name="email_data" value="${login.email}" maxlength="50" required>
                        <span id="msg_email_data"></span>
                    </div>
                </div>
                <div class="control-group col-md-7">
                    <label class="control-label">* Nombre de usuario</label>
                    <div class="controls">
                        <input type="text" class="form-control" id="login" name="login" value="${login.login}" placeholder="Al menos de 4 caracteres" maxlength="20" required>
                        <span id="msg_login"></span>
                    </div>
                </div>
                <div class="form-group">                
                    <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">                    
                        <button style="display: none;" type="submit"></button>
                        <button type="button" class="btn" onclick="sendPerfilUsuario();" style="width: 150px;">Actualizar</button>
                        <!--
                        <button style="width: 150px;" type="submit" class="btn">Actualizar</button>
                        -->
                    </div>
                </div>
                <div class="control-group">
                    <input type="hidden" id="a_login" name="a_login" value="${login.login}">
                    <input type="hidden" id="a_email" name="a_email" value="${login.email}">
                </div>
            </form>
            </div>
        </div>
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />
<jsp:include page="/include/end.jsp" />