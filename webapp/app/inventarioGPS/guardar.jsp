<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:if test="${asociado == 1}">
<c:choose>
     <c:when test="${gps != null}">
     {"id":"${gps.id}", "fk_marca":${gps.fk_marca}, "marca":"${gps.marca}", "fk_modelo":${gps.fk_modelo}, "modelo":"${gps.modelo}", "fk_sim": ${sim.id}, "celular": "${sim.num_cel}", "fk_operador":${sim.fk_operador}, "operador":"${sim.operador}", "idR": ${rhgv.id}, "fk_hard": ${rhgv.fk_hardware}, "asignado":${asociado}}
     </c:when>
     <c:otherwise>
      {"asignado":-1}
     </c:otherwise>            
</c:choose>
</c:if>      
<c:if test="${asociado == 0}">
<c:choose>
     <c:when test="${gps != null}">
     {"asignado":${asociado}}
     </c:when>
     <c:otherwise>
      {"asignado":-1}
     </c:otherwise>            
</c:choose>
</c:if>
<c:if test="${asociado == -1}">
  {"asignado":-1}
</c:if>
     