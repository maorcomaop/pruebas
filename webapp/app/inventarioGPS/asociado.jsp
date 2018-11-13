<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<c:choose>
    <c:when test="${gpsf != null}">
        {"id":${g0}, "fk_gps":"${gpsf.id}", "fk_marca":${gpsf.fk_marca}, "fk_modelo":${gpsf.fk_modelo}, "num_celular":"${simf.num_cel}", "fk_operador":${simf.fk_operador}}
    </c:when>
    <c:otherwise>
        {"id":${g0}}
    </c:otherwise>
</c:choose>
     