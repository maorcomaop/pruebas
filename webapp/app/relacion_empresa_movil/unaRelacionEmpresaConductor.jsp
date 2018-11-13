
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO ASOCIACI&Oacute;N EMPRESA - VEH&Iacute;CULO</h1>    
     <section class="boxed padding">
             <ul class="nav nav-tabs">
        <li role="presentation"><a href="/RDW1/app/relacion_empresa_movil/nuevoRelacionEmpresaVehiculo.jsp">Registro</a></li>
        <li role="presentation" ><a href="/RDW1/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp">Listado de Veh&iacute;culo - Empresa</a></li>
        <li role="presentation" class="active"><a href="/RDW1/app/relacion_empresa_movil/listadoRelacionEmpresaVehiculo.jsp">Modificar Veh&iacute;culo-Empresa</a></li>                    
    </ul>          
   
     <section>         
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
         <form class="form-horizontal" action="<c:url value='/editarRelacionEmpresaVehiculo' />" method="post">
             <div class="control-group">
                 <div class="control-group">                                        
                        <div class="controls">
                            <input type="hidden" name="id_edit" value="${relacionVehiculoEmpresa.idRelacionVehiculoEmpresa}">                                                       
                            <input type="hidden" name="id_vehiculo" id="id_v">                            
                        </div>
                    </div>
                            </div>
                 <div class="control-group">
                     <label class="control-label" for="inputName">Veh&iacute;culo</label>
                 <div class="controls">                     
                     <select class="selectpicker " name="vehiculo" id="vh">
                      <option value="0">Seleccione</option>
                      <c:forEach items="${select.lstmovil}" var="movil">
                            <option value="${movil.id}">${movil.placa}</option>
                      </c:forEach>
                      </select>
                 </div>
             </div>

           <div class="control-group">
                 <label class="control-label" for="inputName">Empresa</label>
                 <div class="controls">
                     <select class="selectpicker" name="empresa" id="company">
                      <option value="0">Seleccione</option>
                      <c:forEach items="${select.lstempresa}" var="empresa">
                            <option value="${empresa.id}">${empresa.nombre}</option>
                      </c:forEach>
                      </select>
                 </div>
             </div>
             <br>
             <div class="control-group">                            
                <div class="controls">
                    <input type="submit" class="btn btn-primary botonRelacionEV" value="Guardar">
                </div>
            </div>    
            
             </form>         
                            </div>    
            </div>
           </section>
           </div>      
          <jsp:include page="/include/footerHome.jsp" />
 
<script>
  $("#vh option[value="+ ${relacionVehiculoEmpresa.idVehiculo}  +"]").attr("selected",true);
  $("#id_v").val( $("#vh option:selected").val() ) ;  
  $("#vh").attr('disabled', 'disabled');   
  
  $("#company option[value="+ ${relacionVehiculoEmpresa.idEmpresa}  +"]").attr("selected",true);    
  $( document ).ready(function() {
   $('.selectpicker').selectpicker({
       style: 'btn-primary',
       size: 4,       
       liveSearch: true
});

 window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000);
});
</script>
    
<jsp:include page="/include/end.jsp" />