
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags/" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PERFILES</h1>        
    <section class="boxed padding"> 
        <c:choose>
            <c:when test="${permissions.check(12)}">
                <ul class="nav nav-tabs">
                    <c:if test="${permissions.check(9)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevoUsuario.jsp">Registro Usuario</a></li>                    
                        </c:if>
                        <c:if test="${permissions.check(90)}">
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaUsuario.jsp">Listado Usuarios</a></li>
                        </c:if>
                    <li role="presentation" class="active"><a href="<c:url value='/nuevoPerfil'/>">Registro Perfil</a></li>
                        <c:if test="${permissions.check(11)}">
                        <li role="presentation"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado Perfiles</a></li>                    
                        </c:if>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        <form id="profile-form" class="form-horizontal" action="<c:url value='/guardarPerfil' />" method="post">                                           
                            <div class="row">
                                <div class="col-sm-10">
                                    <div class="control-group">
                                        <label class="control-label" for="inputName">Nombre</label>
                                        <div class="row">
                                            <div class="controls col-sm-4">
                                                <input type="text" name="name_new" class="form-control" id="name" placeholder="" title="Digite el nombre" value="${perfil.nombre}" required>
                                            </div>
                                        </div>
                                    </div>                    
                                    <div class="control-group">
                                        <label class="control-label" for="inputName">Descripción</label>
                                        <div class="controls">
                                            <textarea class="form-control" name="description_new" rows="5" id="descripcion" title="Digite la descripci&oacute;n" required >${perfil.descripcion}</textarea>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="inputName">Accesos</label>
                                        <div id="for-alert" class="controls">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="control-group">
                                        <div class="controls">
                                            <input class="btn btn-primary boton" type="submit" title="Haga clic aqui para guardar" value="Guardar">                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div id="treeDiv1">
                                <c:forEach var="grupo" items="${accesoPerfil}">
                                    <ct:accesPerfil grupo="${grupo}" tipo="principal" editable="true"/>
                                </c:forEach>
                            </div>
                        </form>
                    </div>
                </div>

            </section>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../../include/footerHome.jsp" />
<script src="/RDW1/resources/js/accesoPerfil.js"></script>
<jsp:include page="/include/end.jsp" />
