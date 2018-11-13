
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CONDUCTOR</h1>
    <section class="boxed padding">

        <ul class="nav nav-tabs">
            <li role="presentation"><a href="/RDW1/app/conductor/nuevoConductor.jsp">Registro</a></li>
            <li role="presentation"><a href="/RDW1/app/conductor/listadoConductor.jsp">Listado de Conductores</a></li>                    
            <li role="presentation" class="active"><a href="/RDW1/app/conductor/listadoConductor.jsp">Conductor -> ${conductor.nombre} ${conductor.apellido}</a></li>                    
        </ul>

        <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
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
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                <form class="form-horizontal" action="<c:url value='/editarConductor' />" method="post">
                    <div class="control-group">
                        <div class="control-group">                                        
                            <div class="controls">
                                <input type="hidden" name="id_edit" value="${conductor.id}">
                            </div>
                        </div>
                    </div>               
                    <div class="control-group">
                        <label class="control-label" for="inputName">Tipo de documento</label>
                        <div class="controls">
                            <select class="selectpicker " data-style="btn-primary" name="tipo_doc_edit" id="type">
                                <option value="0">Seleccione un tipo</option>
                                <c:forEach items="${select.lst_tipodocumento}" var="typeDoc">
                                    <option value="${typeDoc.id}">${typeDoc.tipo}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>
                    <div class="control-group">
                        <div class="row">
                            <div class="col-sm-3">
                                <label class="control-label" for="inputName">N&uacute;mero de documento</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3">
                                <input type="text" name="numero_documento_edit" id="num_doc"  class="form-control" title="Especifica un documento valido " value="${conductor.cedula}" required>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="control-group">
                        <div class="row">
                            <div class="col-sm-3">
                                <label class="control-label" for="inputName">Nombre</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3">  
                                <input type="text" name="nombre_edit" id="name" class="form-control" placeholder="" pattern="[A-Za-z\s]+" title="Especifica un nombre correcto." value="${conductor.nombre}" required>
                            </div>
                        </div>
                    </div>             
                    <br>
                    <div class="control-group">
                        <div class="row">
                            <div class="col-sm-3">
                                <label class="control-label" for="inputName">Apellido</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3">
                                <input type="text" name="apellido_edit" id="last" class="form-control" pattern="[A-Za-z\s]+" title="Especifica un apellido correcto." value="${conductor.apellido}" required>
                            </div>
                        </div>
                    </div> 
                    <br>



                    <br>
                    <div class="control-group">                            
                        <div class="controls">
                            <input type="submit" class="btn btn-primary boton" value="Guardar">
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
        $("#type").selectpicker('val', '${conductor.id_tipo_documento}');
        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
    });
</script>

<jsp:include page="/include/end.jsp" />