
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DOCUMENTOS - REVISION TECNICO MEC&Aacute;NICA</h1>
    
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
          <li role="presentation" ><a href="/RDW1/app/revisionTMecanica/nuevoRevisionTMecanica.jsp">Registro</a></li>                        
          <li role="presentation"><a href="/RDW1/app/revisionTMecanica/listadoRevisionTMecanica.jsp">Listado Documentos</a></li>                                            
          <li role="presentation" class="active"><a href="/RDW1/revisionTMecanica/soat/unaRevisionTMecanica.jsp"><b>Editar</b> Revision: <b>${soat_v.codigo}</b></a></li>                                            
        </ul>        <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
        
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                <form class="form-horizontal" action="<c:url value='/editarTMecanica' />" method="post">
                    <div class="control-group">
                        <div class="control-group">                                        
                            <div class="controls">
                                <input type="hidden" name="id_edit" value="${rev_v.id}">
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
                                        <input type="text" name="cod_edit" id="cod_e"  class="form-control" data-toggle="tooltip" title="Digite" value="${rev_v.codigo}">
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Centro de diagnostico automotor*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <select class="selectpicker " data-style="btn-primary" name="fk_c_d_edit" id="fk_cd_edit" title="Seleccione">                                                                                        
                                        </select>
                                    </div>
                                </div>
                            </div>                            
                            <br>
                            <div class="control-group">                               
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Fecha de vencimiento*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                    
                                        <input type="text" name="f_vto_edit" id="f_v_edit" class="form-control" title="Digite" value="${rev_v.fecha_vencimiento}">
                                    </div>
                                </div>
                            </div>

                            <br>

                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="inputName">Observaciones*</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">                                                                            
                                        <textarea cols="10" rows="5" id="ob_edit" name="obs_edit" class="form-control">${rev_v.observaciones}</textarea>
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
    cargarCDA_edit("${rev_v.fk_centro_diag}");
</script>

<jsp:include page="/include/end.jsp" />