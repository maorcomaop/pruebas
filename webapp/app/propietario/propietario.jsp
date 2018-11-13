<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${propietario.size() > 0}">         
         <c:forEach items="${propietario}" var="a">             
            <option value="${a.id}">${a.nombre} ${a.apellido}</option>                                                         
        </c:forEach>
     </c:when>
     <c:otherwise>
      <option value="0">No hay datos</option>
     </c:otherwise>
            
</c:choose>
