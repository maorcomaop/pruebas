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
            <form action="<c:url value='/activarCuenta'/>" method="post" class="boxed padding">                        
                <h1 class="text-primary text-center">Inicia sesi&oacute;n</h1>
                <h4 class="text-muted text-center">Regisdata Web</h4>                
                <input type="hidden" name="token" value="${param.token}">
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-user"></span>
                    <input class="input-block-level form-control" type="text" name="nomusr" id="nomusr" required placeholder="Usuario">
                </div>
                <div class="input-group">
                    <span class="input-group-addon glyphicon glyphicon-lock"></span>
                    <input class="input-block-level form-control" type="password" name="pass" id="pass" required placeholder="Contraseña"><br>
                </div>
                <hr>
                <button class="btn btn-large btn-primary" type="submit">Iniciar sesi&oacute;n</button>                
            </form>
        </div>
    </div>
</div>
        
<jsp:include page="/include/footer.jsp" />
<jsp:include page="/include/end.jsp" />