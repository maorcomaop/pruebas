
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags/" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">  
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PERFILES</h1>    
    <section class="boxed padding">
        <ul class="nav nav-tabs">
            <c:if test="${permissions.check(12)}">
                <li role="presentation" ><a href="<c:url value='/nuevoPerfil'/>">Registro</a></li>
                </c:if>
                <c:if test="${permissions.check(11)}">
                <li role="presentation"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado de Perfiles</a></li>                    
                </c:if>
            <li role="presentation" class="active"><a href="#">Perfil -> ${perfil.nombre}</a></li>                    
        </ul>      
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                <form class="form-horizontal" action="<c:url value='/editarPerfil' />" method="post">                                           
                    <div class="control-group">                                        
                        <div class="controls">
                            <input type="hidden"  name="id" value="${perfil.id}">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-10">
                            <div class="control-group">
                                <label class="control-label" for="inputName">Nombre</label>
                                <div class="controls">
                                    <span class="label label-view"> ${perfil.nombre}</span>
                                </div>
                            </div>                    
                            <div class="control-group">
                                <label class="control-label" for="inputName">Descripcion</label>
                                <div class="controls">
                                    <span class="label label-view"> ${perfil.descripcion}</span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="inputName">Accesos</label>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="control-group">
                                <div class="controls">
                                    <c:if test="${permissions.check(93)} ">
                                        <input class="btn btn-primary boton" type="submit" value="Editar">                            
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div id="treeDiv1">
                        <c:forEach var="grupo" items="${accesoPerfil}">
                            <ct:accesPerfil grupo="${grupo}" tipo="principal" editable="false"/>
                        </c:forEach>
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>   
<jsp:include page="../../include/footerHome.jsp" />
<script src="/RDW1/resources/js/accesoPerfil.js"></script>
<jsp:include page="/include/end.jsp" />
