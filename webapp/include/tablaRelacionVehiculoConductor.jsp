<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="container-table-relations">
    <table id="tableRealations"   class="display compact" width="100%" cellspacing="0">
        <thead>                            
            <tr>
                <th>Conductor</th>
                <th>Activo</th>
                    <c:if test="${permissions.check(136) || permissions.check(5)}">
                    <th>Eliminar</th>
                    </c:if>
            </tr>    
        </thead>    
        <tbody>
            <c:forEach items="${listVehiculoConductor}" var="veco" begin="0" end="${listVehiculoConductor.size()}" varStatus="p">
                <tr>
                    <td>${veco.nombreConductor}</td>

                    <c:choose>
                        <c:when test ="${veco.activo == 1}">
                            <td class="activo" data-id="${veco.idRelacionVehiculoConductor}">
                                <span class="glyphicon glyphicon-check option" style="color:#00b3ee; cursor:pointer" data-active='true' data-id="${veco.idRelacionVehiculoConductor}" data-ve="${veco.idVehiculo}"></span>
                            </td>
                            <c:if test="${permissions.check(136) || permissions.check(5) }">
                                <td class="delete" data-id="${veco.idRelacionVehiculoConductor}">
                                    <span class="glyphicon glyphicon-remove option" style="color:gainsboro; cursor:not-allowed" data-id="${veco.idRelacionVehiculoConductor}" data-ve="${veco.idVehiculo}"></span>
                                </td>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <td class="activo" data-id="${veco.idRelacionVehiculoConductor}">
                                <span class="glyphicon glyphicon-unchecked option" style="color:gainsboro; cursor:pointer" data-active='false' data-id="${veco.idRelacionVehiculoConductor}" data-ve="${veco.idVehiculo}"></span>
                            </td>
                            <c:if test="${permissions.check(136) || permissions.check(5)}">
                                <td class="delete" data-id="${veco.idRelacionVehiculoConductor}">
                                    <span class="glyphicon glyphicon-remove option" style="color:red; cursor:pointer" data-remove='true' data-id="${veco.idRelacionVehiculoConductor}" data-ve="${veco.idVehiculo}"></span>
                                </td>
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>