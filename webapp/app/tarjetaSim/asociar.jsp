<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
     <c:when test="${tjt != null}">
     {"id":${tjt.id}, "id_oper":${tjt.fk_operador}, "operador":"${tjt.operador}", "celular":"${tjt.num_cel}", "id_gps":"${gps.id}"}
     </c:when> 
     <c:otherwise>
         {"id":0}
     </c:otherwise>
</c:choose>