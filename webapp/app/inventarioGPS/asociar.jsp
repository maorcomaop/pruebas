<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
     <c:when test="${gps != null}">
     {"id":${hard.fk_vehiculo}, "fk_h":${hard.fk_hardware}, "cod":"${gps.id}", 
     "fk_marca":${gps.fk_marca}, "marca":"${gps.marca}", "fk_modelo":${gps.fk_modelo}, 
     "modelo":"${gps.modelo}", "fk_sim":${sim.id}, "celular":"${sim.num_cel}", 
     "fk_operador":${sim.fk_operador}, "operador":"${sim.operador}"}
     </c:when>
     <c:otherwise>
      {"id":0}
     </c:otherwise>            
</c:choose>