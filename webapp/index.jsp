<jsp:include page="/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.login != null}">
    <c:redirect url="/app/usuarios/index.jsp" />
</c:if>

<jsp:include page="login.jsp" />
<jsp:include page="/include/footer.jsp" />
<script>
    $(document).ready(function() {                
        loginData();     
//        consultaEmpresaActiva();        
//        valida_vence();
    }); 
</script>

<jsp:include page="/include/end.jsp" />
