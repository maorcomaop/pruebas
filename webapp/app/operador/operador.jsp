<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${ope != null}">                                
            {"id":${ope.id}, "nom":"${ope.nombre}", "desc":"${ope.descripcion}"}            
     </c:when>
     <c:otherwise>
            {"id":${ope.id}, "nom":"${ope.nombre}", "desc":"${ope.descripcion}"}            
     </c:otherwise>            
</c:choose>
