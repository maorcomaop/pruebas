<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/include/headerNoMenu.jsp" /> <!-- /include/headerHomeNew_.jsp -->
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Planilla despacho</title>

        <link rel="icon" type="image/png" href="img/favicon.png"/>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
        <meta http-equiv='X-UA-Compatible' content='IE=Edge'>
        <meta name="robots" content="index, follow"/>
        <meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="author" content="">        

        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">
    </head>
    <body>
        <div class="col-lg-7 centered">
            <div class="tab-pane padding-smin">
                <c:if test="${permissions.check(['Despacho'])}">
                    
                    <h2>Demostraci&oacute;n planilla despacho</h2>
                    <hr>
                    
                    <!-- Informacion general -->
                    <table class="tbl-style">
                        <thead>
                            <tr>
                                <th colspan="5">${despacho.nombreRuta}</th>
                            </tr>
                            <tr>
                                <th class="lbl">N° vuelta</th>
                                <th class="lbl">Fecha</th>                    
                                <th class="lbl">Veh&iacute;culo</th>
                                <th class="lbl">Punto</th>
                                <th class="lbl">Hora</th>                        
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${lst_planillaDespacho}" var="pd">
                                <tr>
                                    <c:choose>
                                        <c:when test="${pd.numeroVuelta % 2 != 0}">
                                            <td>${pd.numeroVuelta}</td>
                                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${pd.fecha}" /></td>
                                            <td>${pd.placa}</td>
                                            <td>${pd.punto}</td>
                                            <td>${pd.horaPlanificada}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="lbl">${pd.numeroVuelta}</td>
                                            <td class="lbl"><fmt:formatDate pattern="yyyy-MM-dd" value="${pd.fecha}" /></td>
                                            <td class="lbl">${pd.placa}</td>
                                            <td class="lbl">${pd.punto}</td>
                                            <td class="lbl">${pd.horaPlanificada}</td>
                                        </c:otherwise>
                                    </c:choose>                        
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </body>
</html>
