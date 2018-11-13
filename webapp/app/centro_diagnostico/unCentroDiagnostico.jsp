
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
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Informacion </strong>${msg}.
                </div>
            </c:when>       
        </c:choose> 
        <ul class="nav nav-tabs">
          <li role="presentation" ><a href="/RDW1/app/centro_diagnostico/nuevoCentroDiagnostico.jsp">Registro</a></li>                        
          <li role="presentation"><a href="/RDW1/app/centro_diagnostico/listadoCentroDiagnostico.jsp">Listado Documentos</a></li>                                            
          <li role="presentation" class="active"><a href="/RDW1/app/centro_diagnostico/unCentroDiagnostico.jsp"><b>Editar</b> Centro: <b>${centro_v.nombre}</b></a></li>                                            
        </ul>        <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
        
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                <form class="form-horizontal" action="<c:url value='/editarCentroDiagnostico' />" method="post">
                    <div class="control-group">
                        <div class="control-group">                                        
                            <div class="controls">
                                <input type="hidden" name="id_edit" value="${centro_v.id}">
                            </div>
                        </div>
                    </div>               
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">C&oacute;digo</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">   
                                        <input type="text" name="nombre_edit" id="nom_e"  class="form-control" data-toggle="tooltip" title="Digite" value="${centro_v.nombre}">
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
                                        <select class="selectpicker " data-style="btn-primary" name="fk_ciudad_edit" id="fk_cda_edit" title="Seleccione">                                                                                        
                                        </select>
                                    </div>
                                </div>
                            </div>                            
                            <br>
                            <div class="control-group">                               
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Telefono*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <input type="text" name="telefono_edit" id="tel_edit" class="form-control" title="Digite" value="${centro_v.telefono}">
                                    </div>
                                </div>
                            </div>
                            <br>                           

                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn btn-primary " data-toggle="tooltip" title="Haga clic aqui para guardar los cambios" value="Guardar">
                                </div>
                            </div>    
                        </form>
            </div>    
        </div> 
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />

<script>
    $(document).ready(function () {
        ciudadCDA_edit("${centro_v.fk_ciudad}");
    });
</script>

<jsp:include page="/include/end.jsp" />