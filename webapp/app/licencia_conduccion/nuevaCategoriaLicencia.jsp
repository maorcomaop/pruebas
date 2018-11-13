
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<link rel="stylesheet" href="/RDW1/resources/css/general.css">

<div class="col-lg-12 centered">
    <h1 class="display-3 bg-info text-center">MÓDULO LICENCIAS DE CONDUCCIÓN</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(172)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(172)}">
                        <li role="presentation" class="active">
                            <a href="/RDW1/app/licencia_conduccion/nuevaCategoriaLicencia.jsp">Nueva Categoría</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(173)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/listadoCategoriaLicencia.jsp">Lista Categorías</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(176)}">
                        <li role="presentation" >
                            <a href="/RDW1/app/licencia_conduccion/nuevaOficinaTransito.jsp">Nueva Oficina</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(177)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/listadoOficinaTransito.jsp">Lista Oficinas</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(180)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/nuevaLicenciaTransito.jsp">Nueva Licencia</a>
                        </li>
                    </c:if>
                    <c:if test="${permissions.check(181)}">
                        <li role="presentation">
                            <a href="/RDW1/app/licencia_conduccion/listadoLicenciaTransito.jsp">Lista Licencias</a>
                        </li>
                    </c:if>
                </ul>
                <c:choose>      
                    <c:when test ="${idInfo == 1}">                                    
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>
                    <c:when test ="${idInfo == 2}">                                    
                        <div class="alert alert-danger1">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose> 
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <p>
                            Los campos con asterisco (*) son obligatorios.
                        </p>
                        <form class="form-horizontal" action="<c:url value='/guardarCategoriaLicencia' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="nombre" class="control-label">Nombre Categoría *</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="text" name="nombre" id="nombre" required
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite el nombre de la categoría."
                                               maxlength="250">
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label for="descripcion" class="control-label">Descripción Categoría</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <textarea name="descripcion" id="descripcion" 
                                               class="form-control" data-toggle="tooltip" 
                                               title="Dígite la descripción de la categoría."
                                               style="resize: none;" rows="5" maxlength="1000"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary" data-toggle="tooltip" 
                                           title="Haga clic aquí para guardar." value="Guardar">
                                </div>
                            </div> 
                        </form>
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
<jsp:include page="/include/end.jsp" />