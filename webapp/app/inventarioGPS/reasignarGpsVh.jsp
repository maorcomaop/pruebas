<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
     <c:when test="${inv != null}">
     {"fk_h":${hard.fk_hardware}, "cod":"${gps.id_gps}", "fk_marca":${gps.fk_marca}, "fk_modelo":${gps.fk_modelo}, "fk_sim":${sim.id}, "fk_operador":${sim.fk_operador}}
     </c:when>
     <c:otherwise>
      {"fk_h":0, "cod_gps":0, "marca":0, "modelo":0, "fk_sim":0, "fk_op":0}
     </c:otherwise>            
</c:choose>