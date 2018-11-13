
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-8 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO TARIFAS</h1>    
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(108)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(48)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaTarifa.jsp">Nueva Tarifa</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="#">Listado Tarifas</a></li>
                </ul>    

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <div class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>

                        <table id="tablaTarifa" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Valor</th>
                                    <th>Activar/Desactivar</th>                                        
                                    <th>Edici&oacute;n</th>                                    
                                    <th>Eliminar/Restaurar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${select.lstruta.size() > 0}">
                                        <c:forEach items="${select.lsttarifa}" var="tarifa">                            
                                            <tr>
                                                <td>${tarifa.nombre}</td>
                                                <td>${tarifa.valor}</td>

                                                <td> <!-- Estado tarifa :: activar/desactivar -->
                                                    <form class="form-in-table" action="<c:url value='/activarTarifa' />" method="post">
                                                        <input type="hidden" id="idTarifa" name="idTarifa" value="${tarifa.id}">                                                        
                                                        <c:if test="${tarifa.activa == 1 && tarifa.estado == 1}">                                     
                                                            <input type="hidden" id="activar" name="activar" value="0">
                                                            <button class="button2text info-color" style="width: 80px;" disabled title="Indica que la tarifa se encuentra ACTIVA para liquidacion">Activa
                                                                
                                                            <c:choose>
                                                                <c:when test="${permissions.check(109)}">
                                                                    <button type="submit" class="btn btn-info btn-xs" style="width: 70px;" title="Haga clic aqui para inhabilitar esta tarifa y no pueda ser utilizada en liquidacion" >Desactivar
                                                                </c:when>
                                                            </c:choose>
                                                        </c:if>
                                                        <c:if test="${tarifa.activa == 0  && tarifa.estado == 1}">
                                                            <input type="hidden" id="activar" name="activar" value="1">
                                                            <button class="button2text error-color" style="width: 80px;" disabled title="Indica que la tarifa NO se encuentra activa para liquidacion">Desactivada
                                                            <c:choose>
                                                                <c:when test="${permissions.check(109)}">
                                                                    <button type="submit" class="btn btn-warning btn-xs" style="width: 70px;" title="Haga clic aqui para activar la tarifa y pueda ser utilizada en liquidacion">Activar
                                                                </c:when>
                                                            </c:choose>
                                                        </c:if>
                                                    </form>
                                                        <c:if test="${tarifa.activa == 0  && tarifa.estado == 0}">
                                                            <span class="label label-danger">Tarifa no disponible en el sistema</span>
                                                        </c:if>
                                                        <c:if test="${tarifa.activa == 1  && tarifa.estado == 0}">
                                                            <span class="label label-danger">Tarifa no disponible en el sistema</span>
                                                        </c:if>
                                                </td>
                                                
                                                <td> <!-- Edicion tarifa :: editar -->
                                                    <c:if test="${tarifa.estado == 1}">
                                                        <c:choose>
                                                            <c:when test="${permissions.check(109)}">                                                                
                                                                <form class="form-in-table" action="<c:url value='/pre_editarTarifa' />" method="post">
                                                                    <input type="hidden" id="idTarifa" name="idTarifa" value="${tarifa.id}">
                                                                    <button type="submit" class="button2text cursor info-color" style="width: 58px;" title="Haga clic aqui para modificar la tarifa">Editar
                                                                </form>                                                                
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="error-color">No editable</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                    <c:if test="${tarifa.estado == 0}">
                                                        <span class="error-color">No editable</span>
                                                    </c:if>                                                    
                                                </td>
                                                
                                                <td> <!-- Eliminacion tarifa :: eliminar/restaurar -->
                                                    <c:if test="${tarifa.estado == 1}">                                                                                                                
                                                        <c:choose>
                                                            <c:when test="${permissions.check(110)}">                                                                
                                                                <form class="form-in-table" action="<c:url value='/eliminarTarifa' />" method="post">
                                                                    <input type="hidden" id="idTarifa" name="idTarifa" value="${tarifa.id}">
                                                                    <input type="hidden" id="state" name="estado" value="0">
                                                                    <button type="submit" class="btn btn-danger btn-xs" style="width: 80px;" title="Haga clic aqui para  deshabilitar la tarifa del sistema">Eliminar
                                                                </form>                                                                
                                                            </c:when>                                                            
                                                        </c:choose>
                                                    </c:if>
                                                    <c:if test="${tarifa.estado == 0}">                                                                                                                
                                                        <c:choose>
                                                            <c:when test="${permissions.check(110)}">                                                        
                                                                <form class="form-in-table" action="<c:url value='/eliminarTarifa' />" method="post">
                                                                    <input type="hidden" id="idTarifa" name="idTarifa" value="${tarifa.id}">
                                                                    <input type="hidden" id="state" name="estado" value="1">
                                                                    <button type="submit" class="btn btn-success btn-xs" style="width: 80px;" title="Haga clic aqui para habilitar la tarifa del sistema">Restaurar
                                                                </form>                                                        
                                                            </c:when>  
                                                        </c:choose>
                                                    </c:if>
                                                </td>                                                
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />

<script>
    // Tabla dinamica de tarifas
    $(document).ready(function () {
        $('#tablaTarifa').DataTable({
            responsive: true,
            aLengthMenu: [300, 500],
            scrollY: 500,
            searching: true,
            bAutoWidth: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
    });
</script>

<jsp:include page="/include/end.jsp" />
