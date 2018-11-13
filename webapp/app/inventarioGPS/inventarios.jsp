<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${invallgps.size() > 0}">         
         <c:forEach items="${invallgps}" var="a">             
            <option value="${a.id}">${a.marca} ${a.modelo}</option>                                                         
        </c:forEach>
     </c:when>
     <c:otherwise>
      <option value="0">No hay datos</option>
     </c:otherwise>            
</c:choose>
