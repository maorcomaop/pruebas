
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DOCUMENTOS - CENTRO DE DIAGNOSTICO</h1>
    <section class="boxed padding">
                        <c:choose>
                    <c:when test ="${idInfo == 0}">                                    
                        <div class="alert alert-info">
                            <button type="button" class="close fade" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>        
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
        <c:choose>
            <c:when test="${permissions.check(66)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/centro_diagnostico/nuevoCentroDiagnostico.jsp">Registro</a></li>                        
                    <li role="presentation"><a href="/RDW1/app/centro_diagnostico/listadoCentroDiagnostico.jsp">Listado Documentos</a></li>
                </ul>


                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        
                        <form class="form-horizontal" action="<c:url value='/guardarCentroDiagnostico' />" method="post">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">nombre</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">   
                                        <input type="text" name="nombre" id="nom"  class="form-control" data-toggle="tooltip" title="Digite" autofocus>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Ciudad*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="fk_ciudad" id="fk_c" title="Seleccione">                                                                                        
                                        </select>
                                    </div>
                                </div>
                            </div>                            
                            <br>
                            <div class="control-group">                               
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Tel&eacute;fono</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <input type="text" name="tele" id="t" class="form-control" data-toggle="tooltip" title="Digite">
                                    </div>
                                </div>
                            </div>
                            <br>

                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary botonCentro" data-toggle="tooltip" title="Haga clic aqui para guardar" value="Guardar">
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
<script>
    $(document).ready(function () {         
        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);

    });
</script>
<jsp:include page="/include/end.jsp" />