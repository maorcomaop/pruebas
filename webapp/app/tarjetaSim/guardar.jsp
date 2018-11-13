<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:choose>
     <c:when test="${a == 1}">
     {"id":${a},"id_gps":"${g.id}", "fk_marca":${g.fk_marca}, "fk_modelo":${g.fk_modelo}, "fk_sim":${s.id}, "fk_operador":${s.fk_operador}}
     </c:when>
     <c:otherwise>
      {"id":${a}}
     </c:otherwise>            
</c:choose>