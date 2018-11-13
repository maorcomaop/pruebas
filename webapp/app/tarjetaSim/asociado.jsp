<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:choose>
    <c:when test="${s1 != null}">
        {"id":${s0}, "id_sim":"${s1.id}", "num_celular":"${s1.num_cel}", "fk_operador":${s1.fk_operador}}
    </c:when>
    <c:otherwise>
        {"id":${s0}}
    </c:otherwise>
</c:choose>
     