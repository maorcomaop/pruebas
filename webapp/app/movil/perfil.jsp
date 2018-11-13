<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${perfil != null}">                  
         ${perfil.id}
     </c:when>
     <c:otherwise>
      0
     </c:otherwise>            
</c:choose>
