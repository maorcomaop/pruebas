<jsp:include page="/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 padding">
            <img src="resources/img/logoregistel.png">
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form action="<c:url value='/iniciarSesion'/>" method="post" class="boxed padding">
                <h3 class="text-muted text-center">Inicio de sesi&oacute;n sin &eacute;xito.</h3>
                <div id="msg" class="form-msg ${msgType}" role="alert">${msgLogin}</div><br>                    
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-user"></span>
                    <input class="input-block-level form-control" type="text" name="login" id="login" required placeholder="Usuario">
                </div>
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-lock"></span>
                    <input class="input-block-level form-control" type="password" name="pass" id="pass" required placeholder="Contraseña"><br>
                </div>
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-home"></span>
                    <select id="sempresa" name="sempresa" class="form-control"></select>                    
                </div>
                <hr>
                <button class="btn btn-large btn-primary" type="submit">Iniciar sesi&oacute;n</button>
                <div style="margin-top: 22px;"></div>
                <div class="form-msg">
                    <a href="/RDW1/restore.jsp"  style="float:left;">¿Has olvidado la contrase&ntilde;a?</a>
                    <a href="/RDW1/register.jsp" style="float: right;">Reg&iacute;strate</a>
                </div>                        
            </form>
        </div>
    </div>
</div>

<jsp:include page="/include/footer.jsp" />
<script>
    $(document).ready(function() {        
        loginData();
    });
</script>
<jsp:include page="/include/end.jsp" />