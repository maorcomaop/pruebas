<jsp:include page="/include/headerHome.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-lg-12">    
    <section class="boxed padding">
        <ul class="nav nav-tabs">
            <li role="presentation" ><a href="/RDW1/app/evento/nuevoEvento.jsp">Registro</a></li>
            <li role="presentation" ><a href="/RDW1/app/evento/listadoEventos.jsp">Listado de Eventos</a></li>
            <li role="presentation" class="active"><a href="/RDW1/app/evento/listadoEventos.jsp">Evento -> ${evento.nombreGenerico}</a></li>                    
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
                <form class="form-horizontal" action="<c:url value='/editarEvento' />" method="post">   
                    <div class="control-group">                                        
                        <div class="controls">
                            <input type="hidden" name="id_edit" value="${evento.id}">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputName">C&oacute;digo</label>
                        <div class="controls">
                            <input type="text" name="codigo_edit" id="code" class="form-control" value="${evento.codigoEvento}" autofocus>
                        </div>
                    </div>                    
                    <div class="control-group">
                        <label class="control-label" for="inputName">Nombre</label>
                        <div class="controls">
                            <input type="text" name="nombre_edit" id="name" class="form-control" value="${evento.nombreGenerico}">
                        </div>
                    </div>                    
                    <div class="control-group">
                        <label class="control-label" for="inputName">Descripci&oacute;n</label>
                        <div class="controls">
                            <input type="text" name="descripcion_edit" id="description"  class="form-control" value="${evento.descripcion}">
                        </div>
                    </div>                    
                 <div class="control-group">
                 <label class="control-label" for="inputName" >Prioridad</label>
                 <div class="controls">
                     <select class="selectpicker" data-style="btn-primary" name="prioridad_edit" id="priority">
                         <option value="0">Seleccione</option>
                         <option value="3">Alta</option>
                         <option value="2">Media</option>                         
                         <option value="1">Baja</option>
                     </select>
                 </div>
                </div>
                    <div class="control-group">
                        <label class="control-label" for="inputName">Cantidad</label>
                        <div class="controls">
                            <input type="text" name="cantidad_edit" id="cant"  class="form-control" value="${evento.cantidad}">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputName">Tipo</label>
                        <div class="controls">
                            <select class="selectpicker" data-style="btn-primary" id="tip" name="tipo">
                                <option value="0">Seleccione</option>
                                <option value="Software">Software</option>
                                <option value="Hardware">Hardware</option>
                                <option value="Otro">Otro</option>
                            </select>
                        </div>
                    </div>      
                    <br>
                    <div class="control-group">
                        <div class="controls">
                            <input class="btn btn-primary botonEvento" type="submit" value="Guardar">                            
                        </div>
                    </div>                    
                </form>
            </div>
        </div>
    </section>
</div>                        
<jsp:include page="/include/footerHome.jsp" />
<script>                    
         $( document ).ready(function() {
         $("#priority option[value=${evento.prioridad}]").attr("selected",true);
         $("#tip option[value=${evento.tipoEvento}]").attr("selected",true);
             window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000);   
    });
    
    
</script>
<jsp:include page="/include/end.jsp" />
