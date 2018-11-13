
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-12">
    
    
        <section class="boxed padding">
            
            <ul class="nav nav-tabs">
        <li role="presentation" ><a href="/RDW1/app/motivo/nuevoMotivo.jsp">Registro</a></li>
        <li role="presentation"><a href="/RDW1/app/motivo/listadoMotivo.jsp">Listado de Motivos</a></li>                    
        <li role="presentation" class="active"><a href="/RDW1/app/motivo/unMotivo.jsp">Motivo</a></li>                    
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
            <table id="tableMotivo" class="display" cellspacing="0" width="100%">
                <thead>
                    <tr>                        
                        <th>Elementos</th>                                             
                        <th></th>
                        <th>Informacion</th>                                                                     
                    </tr>
                </thead>
                <tbody>
                    <tr>                             
                        <td>Codigo</td>                                 
                        <td></td>                                 
                        <th>${motivo.fk_item_a_auditar}</th>                                                
                    </tr>
                    <tr>                             
                        <td>Tabla auditada</td>
                        <td></td>                                 
                        <th>${motivo.nombreTablaAAuditar}</th>                         
                    </tr>

                    <tr>
                        <td>Descripcion</td>
                        <td></td>                                 
                        <th>${motivo.descripcionMotivo}</th>                            
                    </tr>
                    <tr>
                        <td>Usuario</td>
                        <td></td>                                 
                        <th>${motivo.usuario}</th>                          
                    </tr>
                    <tr>
                        <td>Usuario de la base de datos</td> 
                        <td></td>                                 
                        <th>${motivo.usuarioBD}</th>                        
                    </tr>                    
                </tbody>
                <tfoot>
                    <tr>
                        <td></td>
                        <td><a href="/RDW1/app/motivo/listadoMotivo.jsp" class="btn btn-primary">Volver</a></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>            
                    </div>
                    </div>
        </section>
    </div>
        <jsp:include page="/include/footerHome.jsp" />
<script>
    $(document).ready(function() {
        $('#tableMotivo').DataTable();
         window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000); 
		});                
                
</script>
<jsp:include page="/include/end.jsp" />