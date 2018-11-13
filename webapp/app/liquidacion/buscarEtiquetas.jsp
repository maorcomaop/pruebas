<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${configuracion_etiquetas_sesion != null}">
     {  "id": ${configuracion_etiquetas_sesion.id}, 
        "e1": "${configuracion_etiquetas_sesion.etqPasajeros1}", 
        "e2": "${configuracion_etiquetas_sesion.etqPasajeros2}",
        "e3": "${configuracion_etiquetas_sesion.etqPasajeros3}",
        "e4": "${configuracion_etiquetas_sesion.etqPasajeros4}",
        "e5": "${configuracion_etiquetas_sesion.etqPasajeros5}",
        "t1": "${configuracion_etiquetas_sesion.etqTotal1}",
        "t2": "${configuracion_etiquetas_sesion.etqTotal2}",
        "t3": "${configuracion_etiquetas_sesion.etqTotal3}",       
        "t4": "${configuracion_etiquetas_sesion.etqTotal4}",
        "t5": "${configuracion_etiquetas_sesion.etqTotal5}",
        "t6": "${configuracion_etiquetas_sesion.etqTotal6}",
        "t7": "${configuracion_etiquetas_sesion.etqTotal7}",        
        "t8": "${configuracion_etiquetas_sesion.etqTotal8}",        
        "es": "${configuracion_etiquetas_sesion.estado}"
      }
     </c:when>
     <c:otherwise>
      {"id":0}
     </c:otherwise>            
 </c:choose>
