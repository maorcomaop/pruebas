<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:choose>
     <c:when test="${desconf != null}">
     {"id":${desconf.id}, "nom":"${desconf.nombre}", "p_max":${desconf.puntaje_max}, "p_dias_no_labor":${desconf.dias_descanso}, "d_desc":${desconf.dias_descanso}, "pctje_ruta":${desconf.porcentaje_ruta}, "p_ruta":${desconf.puntos_cumplimiento_de_ruta}, "p_ex_vel":${desconf.puntos_exceso_velocidad}, "vel_max":${desconf.velocidad_max}, "ipk_max": ${desconf.puntos_ipk_mas}, "ipk_menos":${desconf.puntos_ipk_menos}}
     </c:when>
     <c:otherwise>
      {"id":0}
     </c:otherwise>            
</c:choose>
