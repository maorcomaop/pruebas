<jsp:include page="/include/header.jsp" />
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">    
    <div class="row">
        <div class="col-md-6 col-md-offset-3 padding">
            <img src="resources/img/logoregistel.png">
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <form id="form-nuevo-usuario-externo" action="<c:url value='/crearCuenta' />" method="post" class="boxed padding">
                <h1 class="text-primary text-center">Reg&iacute;strate</h1>
                <h4 class="text-muted text-center">Regisdata Web</h4>
                <div id="msg" class="form-msg ${msgType}" role="alert">${msgRegister}</div><br>
                <div class="form-group">
                    <label>* Nombre</label>
                    <input class="form-control" type="text" id="nombre" name="nombre" value="${preusr.nombre}" maxlength="50" required>                    
                    <span id="msg_nombre"></span>
                </div>                
                <div class="form-group">
                    <label>* Apellido</label>
                    <input class="form-control" type="text" id="apellido" name="apellido" value="${preusr.apellido}" maxlength="50" required>                    
                    <span id="msg_apellido"></span>
                </div>                
                <div class="form-group">
                    <label>* Empresa</label>
                    <select id="sempresa" name="sempresa" class="form-control">
                        <option value="">Seleccione una empresa</option>
                        <c:forEach items="${select.lstempresa}" var="empresa">
                            <option value="${empresa.id}">${empresa.nombre}</option>
                        </c:forEach>
                    </select>
                    <span id="msg_sempresa"></span>
                </div>                    
                <div class="form-group">
                    <label>* Perfil</label>
                    <select id="sperfil" name="sperfil" class="form-control">
                        <option value="">Seleccione un perfil</option>
                        <c:forEach items="${select.lstperfil}" var="perfil">
                            <option value="${perfil.id}">${perfil.nombre}</option>
                        </c:forEach>
                    </select>
                    <span id="msg_sperfil"></span>
                </div>
                <div class="form-group">
                    <label>* N&uacute;mero de documento</label>        
                    <input class="form-control" type="text" id="numdoc" name="numdoc" value="${preusr.numdoc}" maxlength="13" required>
                    <span id="msg_numdoc"></span>
                </div>
                <div class="form-group">
                    <label>* Correo electr&oacute;nico</label>
                    <input class="form-control" type="email" id="email_data" name="email_data" value="${preusr.email}" maxlength="50" required>
                    <span id="msg_email_data"></span>
                </div>
                <div class="form-group">
                    <label>* Nombre de usuario</label>
                    <input class="form-control" type="text" id="nomusr" name="nomusr" value="${preusr.login}" placeholder="Al menos 4 caracteres" 
                           maxlength="20" required>
                    <span id="msg_nomusr"></span>
                </div>
                <div class="form-group">
                    <label>* Contrase&ntilde;a</label>
                    <input class="form-control" type="password" id="pass" name="pass" placeholder="Al menos 8 caracteres" 
                           maxlength="20" required>
                    <span id="msg_pass"></span>
                </div>
                <div class="form-group">
                    <label>* Confirmar contrase&ntilde;a</label>
                    <input class="form-control" type="password" id="cpass" name="cpass" maxlength="20" required>
                    <span id="msg_cpass"></span>
                </div>
                <div class="form-group">
                    <!--
                    %<
                        ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Lc7lhYUAAAAAFQdbWAJrBgMs6hFH3r6WUptOAIT", "6Lc7lhYUAAAAADzYIdH8YIVrRTh-cym5ageI4yGa", false);
                        out.print(captcha.createRecaptchaHtml(null, null));
                    %>
                    -->
                    <!-- <div style="padding-left: 10px;" class="g-recaptcha" data-sitekey="6Lc7lhYUAAAAAFQdbWAJrBgMs6hFH3r6WUptOAIT"></div> -->
                </div>
                
                <button class="btn" style="display: none;" type="submit"></button>
                <button class="btn btn-large btn-primary"  type="button" onclick="sendUsuarioExtern();">Crear usuario</button>
                
                <div class="form-msg pull-right" style="margin-top: 5px;"><a href="/RDW1/index.jsp">Volver</a></div>
            </form>
        </div>
    </div>
</div>
                
<jsp:include page="/include/footer.jsp" />
<script> /*
    $(document).ready(function() {
        document.getElementById("nomusr").value = "";
        document.getElementById("pass").value = "";
    }); */
</script>
<jsp:include page="/include/end.jsp" />