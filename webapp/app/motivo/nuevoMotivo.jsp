
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-12">
    
<section class="boxed padding">
    
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="/RDW1/app/motivo/nuevoMotivo.jsp">Registro</a></li>
        <li role="presentation" ><a href="/RDW1/app/motivo/listadoMotivo.jsp">Listado de Motivos</a></li>                    
    </ul>


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
         <h2>Reportar un motivo</h2>         
         <form class="form-horizontal" action="<c:url value='/guardarMotivo' />" method="post">             
             <div class="control-group">             
                 <div class="controls">
                     <input type="hidden" name="id_auditoria" id="" value="154423">
                     <input type="hidden" name="id_usuario" id="" value="1">
                     <input type="hidden" name="tabla_a_auditar" id="" value="tbl_informacion_registradora">                     
                 </div>
             </div>             
             <div class="control-group">
                 <label class="control-label" for="inputName">Porqu&eacute; realiza la acci&oacute;n describa a continuaci&oacute;n las razones:</label>
                 <div class="controls">
                     <textarea name="descripcion" cols="10" id="description" rows="7"></textarea>                     
                 </div>
             </div>
                 <div class="control-group">                            
                <div class="controls">
                    <input type="submit" class="btn btn-primary botonGrupoMovil" value="Enviar">
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
             window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000);         
         
     });
     /* $('.selectpicker').selectpicker({
  style: 'btn-info',
  size: 4
});*/
</script>
<jsp:include page="/include/end.jsp" />