<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${invall.size() > 0}">         
         <c:forEach items="${invall}" var="a">             
            <option value="${a.id}">${a.num_cel}</option>                                                         
        </c:forEach>
     </c:when>
     <c:otherwise>
      <option value="0">No hay datos</option>
     </c:otherwise>            
</c:choose>
