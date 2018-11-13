
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-7 centered">                 
           <h1 class="display-3 bg-info text-center">M&Oacute;DULO GRUPO DE VEH&Iacute;CULOS</h1>
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
    </c:choose> 
         <section class="boxed padding">
             
             <ul class="nav nav-tabs">
        <li role="presentation"><a href="/RDW1/app/grupo_movil/nuevoGrupoMovil.jsp">Registro</a></li>
        <li role="presentation"><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Listado de grupos</a></li>                    
        <li role="presentation"><a href="/RDW1/app/grupo_movil/adicionarMovilesAGrupo.jsp">Adicionar Veh&iacute;culos</a></li>  
        <li role="presentation" class="active"><a href="/RDW1/app/grupo_movil/adicionarMovilesAGrupo.jsp">Editar Veh&iacute;culos de un grupo</a></li>  
        <!--<li role="presentation" ><a href="/RDW1/app/grupo_movil/adicionarRutasAGrupo.jsp">Adicionar Rutas a un Grupo</a></li>  -->
    </ul>
          
             <div class="tab-content">
                 <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                     <form class="form-horizontal" action="<c:url value='/editarGrupoMovil' />" method="post">
                         <input type="hidden" name="id_edit" id="id_grupo_edit" value="${grupoEncontrado.id}">
                         <input type="hidden" name="listado_vh" id="lst_vh" >
                         <div class="control-group">
                             <div class="row">
                                 <div class="col-sm-7">
                                     <label class="control-label" for="inputName">Nombre del grupo</label>
                                 </div>
                             </div>
                         </div>
                         <div class="control-group">
                             <div class="row">
                                 <div class="col-sm-7">
                                     <div class="controls">
                                         <input type="text" name="nombre_edit" id="name" class="form-control" value="${grupoEncontrado.nombreGrupo}">
                                     </div>                                      
                                 </div>             
                             </div> 
                         </div>
                         <div class="control-group">                 
                             <label class="control-label">Aplicar tiempos</label>
                             <div class="controls">                             
                                 <input id="aplica1" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="aplica" value="1">                         
                             </div>
                         </div>                 
                         <br>                                 
                         <br>
                         <div class="control-group">                            
                             <div class="controls">
                                 <input type="submit" class="btn btn-primary botonGrupoMovil" value="Guardar">
                             </div>
                         </div>
                         </form>
                         <div id="datos">
                             <table id="tableRelaciones" class="display compact" cellspacing="0" width="100%">
                                 <thead>
                                     <tr>
                                         <th>#</th>                                
                                         <th>Id</th>
                                         <th>Vehiculo</th>
                                         <th>Numero Interno</th>                                         
                                         <th>Eliminar</th>
                                     </tr>    
                                 </thead>    
                                 <tbody>
                                     <c:choose>
                                         <c:when test="${vehiculos.size() > 0}">         
                                             <c:set var="count" value="0" scope="page" />
                                             <tr>
                                                 <c:forEach items="${vehiculos}" var="grupovh" varStatus="status">
                                                     <td><c:out value="${status.count}" /></td>                                                             
                                                     <td>${grupovh.idVehiculo}</td>                                            
                                                     <td>${grupovh.placa}</td>
                                                     <td>${grupovh.numInterno}</td>                                                     
                                                     <td>
                                                         <c:choose>
                                                             <c:when test ="${grupovh.estado == 1}">                                                                                                     
                                                                 <form action="<c:url value='/eliminarMovilDeGrupo' />" method="post">
                                                                     <input type="hidden" name="estado" value="0">
                                                                     <input type="hidden" name="id" value="${grupovh.idVehiculo}">
                                                                     <input type="hidden" name="id_grupo" value="${grupovh.idGrupo}">
                                                                     <input type="submit" class="btn  btn-xs btn-danger" value="Eliminar">
                                                                     
                                                                 </form>
                                                             </c:when>
                                                             <c:otherwise>
                                                                 <form action="<c:url value='/eliminarMovilDeGrupo' />" method="post">                                                                 
                                                                     <input type="hidden" name="estado" value="1">
                                                                     <input type="hidden" name="id" value="${grupovh.idVehiculo}">
                                                                     <input type="hidden" name="id_grupo" value="${grupovh.idGrupo}">
                                                                     <input type="submit" class="btn  btn-xs btn-success" value="Activar">                                                                     
                                                                 </form>
                                                             </c:otherwise>                    
                                                         </c:choose>
                                                     </td>     
                                                 </tr>
                                             </c:forEach>                                   
                                         </c:when>     
                                     </c:choose>
                                 </tbody>                         
                             </table>
                         </div><!--FIN CAJA QUE GUARDA LA TABLA-->                                                
                 </div>        
             </div>
         </section>
        <jsp:include page="/include/footerHome.jsp" />
 
<script>
  cargarCombo(${grupoEncontrado.codEmpresa});
  aplicaTiempos(${grupoEncontrado.aplicaTiempos});
</script>
    
<jsp:include page="/include/end.jsp" />