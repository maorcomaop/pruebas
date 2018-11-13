<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${f1 != null}">         
     { "id1":"${f1}", "id2":"${f2}"}
     </c:when>
     <c:otherwise>
      {"id1":0}
     </c:otherwise>            
</c:choose>
