<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${respuesta != null}">                                
            {"id":${respuesta.id}, "clave":"${respuesta.clave}", "doc":"${respuesta.num_doc}", "nom":"${respuesta.nom_cliente}"}
     </c:when>
     <c:otherwise>      
      {"id":0}
     </c:otherwise>            
</c:choose>
