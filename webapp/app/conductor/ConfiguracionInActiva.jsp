<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${inact != null}">
     {"id":${inact}}
     </c:when>
     <c:otherwise>
      {"id":0}
     </c:otherwise>            
</c:choose>
