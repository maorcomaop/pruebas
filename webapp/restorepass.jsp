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
            <form id="form-restaura-password" action="<c:url value='/make_restaurarContrasena'/>" method="post" class="boxed padding">                        
                <h1 class="text-primary text-center">Cambio de contrase&ntilde;a</h1>
                <h4 class="text-muted text-center">Regisdata Web</h4>
                <div id="msg" class="form-msg ${msgType}" role="alert">${msgRestore}</div><br>
                <input type="hidden" name="token" value="${param.token}">
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-lock"></span>
                    <input class="input-block-level form-control" type="password" name="pass" id="pass" pattern="[\w!#$%&@\.]{8,20}"
                           title="&Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como !#." placeholder="De al menos 8 caracteres" 
                           maxlength="20" required>
                </div>
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-lock"></span>
                    <input class="input-block-level form-control" type="password" name="cpass" id="cpass" pattern="[\w!#$%&@\.]{8,20}"
                           maxlength="20" required>
                    <br>
                </div>
                <hr>
                <button class="btn" style="display: none;" type="submit"></button>
                <button class="btn btn-large btn-primary"  type="button" onclick="sendRestorePassword();">Cambiar contrase&ntilde;a</button>                
            </form>
        </div>
    </div>
</div>

<jsp:include page="/include/footer.jsp" />
<jsp:include page="/include/end.jsp" />