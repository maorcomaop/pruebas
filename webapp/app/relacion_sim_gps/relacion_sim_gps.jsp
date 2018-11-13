<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${inv != null}">                                
            {"id":${inv.id}, "fk_sim":${inv.fk_sim}, "fk_gps":${inv.fk_gps}}            
     </c:when>
     <c:otherwise>
      {"id":0, "fk_sim":0, "fk_gps":0}            
     </c:otherwise>            
</c:choose>
