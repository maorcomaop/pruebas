<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:choose>
     <c:when test="${av != null}">
     {"id_gps":"${av.fk_gps}", "id_vh":${av.fk_vehiculo}}
     </c:when>
     <c:otherwise>
      {"id_gps":"no_aplica", "id_vh":0}
     </c:otherwise>            
</c:choose>
     