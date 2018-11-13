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
            <form id="form-correo-restauracion" action="<c:url value='/restaurarContrasena'/>" method="post" class="boxed padding">                
                <h1 class="text-primary text-center">Restauraci&oacute;n de contrase&ntilde;a</h1>
                <h4 class="text-muted text-center">Regisdata Web</h4>
                <div id="msg" class="form-msg ${msgType}" role="alert">${msgRestore}</div><br>
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-envelope"></span>
                    <input class="input-block-level form-control" type="email" name="email_data" id="email_data"
                           placeholder="Correo electrónico" maxlength="50" required>
                </div>                
                <span id="msg_email_data"></span>
                <hr>
                <button class="btn" style="display: none;" type="submit"></button>
                <button class="btn btn-large btn-primary"  onclick="sendEmailRestore();">Enviar</button>
                <div class="form-msg pull-right"><a href="/RDW1/index.jsp">Volver</a></div>
            </form>
        </div>
    </div>
</div>        
    
<jsp:include page="/include/footer.jsp" />
<jsp:include page="/include/end.jsp" />