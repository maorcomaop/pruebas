<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${inv != null}">                                
            {"id":${inv.id}, "cod":"${inv.id_gps}", "imei":"${inv.imei}", "fk_operador":${inv.fk_operador}, "fk_marca":${inv.fk_marca}, "fk_modelo":${inv.fk_modelo}, "celular":"${inv.num_cel}", "estado":"${inv.estado}"}            
     </c:when>
     <c:otherwise>
      {"id":0, "cod":"0", "imei":"0", "fk_operador":0, "fk_marca":0, "fk_modelo":0, "celular":"0", "estado":"0"}            
     </c:otherwise>            
</c:choose>
