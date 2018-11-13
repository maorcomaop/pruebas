
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">    
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO PERFILES</h1>    
            <section class="boxed padding">
                <ul class="nav nav-tabs">
        <li role="presentation" ><a href="<c:url value='/nuevoPerfil' />">Registro</a></li>
        <li role="presentation"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado de Perfiles</a></li>                    
        <li role="presentation" class="active"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Perfil -> ${perfil.nombre}</a></li>                    
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
                <form class="form-horizontal" action="<c:url value='/editarPerfil' />" method="post">                                           
                    <div class="control-group">                                        
                        <div class="controls">
                            <input type="hidden"  name="id_edit" value="${perfil.id}">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputName">Nombre</label>
                        <div class="controls">
                            <input type="text" name="name_edit" class="form-control" id="name" placeholder="" title="Especifica un nombre correcto." value="${perfil.nombre}">
                        </div>
                    </div>                    
                    <div class="control-group">
                        <label class="control-label" for="inputName">Descripción</label>
                        <div class="controls">
                            <input type="text" name="descripcion_edit" class="form-control" id="description"  title="Especifica un nombre correcto." value="${perfil.descripcion}" required>
                        </div>
                    </div>                                        
                    <br>
                    <div class="control-group">
                        <div class="controls">
                                <input class="btn btn-primary boton" type="submit" value="Guardar">                            
                        </div>
                    </div>                    
                </form>
                        
                </div>
                </div>  
                        
            </section>
                        
           </div>   
        <jsp:include page="../../include/footerHome.jsp" />
<script>
      $( document ).ready(function() {
             window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000);         
         
     });
</script>
<jsp:include page="/include/end.jsp" />
       