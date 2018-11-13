<jsp:include page="/include/headerHome.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-lg-12">
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Configuracion','Eventos','Registro'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/evento/nuevoEvento.jsp">Registro</a></li>
                    <li role="presentation" ><a href="/RDW1/app/evento/listadoEventos.jsp">Listado de Eventos</a></li>                    
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
                        <form class="form-horizontal" action="<c:url value='/guardarEvento' />" method="post">   
                            <div class="control-group">
                                <label class="control-label" for="inputName">C&oacute;digo</label>
                                <div class="controls">
                                    <input type="text" name="codigo" id="code" class="form-control" pattern="[a-zA-z]+[-]+[0-9]+" placeholder="" title="Especifica un codigo correcto." autofocus>
                                </div>
                            </div>                    
                            <div class="control-group">
                                <label class="control-label" for="inputName">Nombre</label>
                                <div class="controls">
                                    <input type="text" name="nombre" id="name" class="form-control" pattern="[A-Za-z\s]+" placeholder="" title="Especifica un nombre correcto." >
                                </div>
                            </div>                    
                            <div class="control-group">
                                <label class="control-label" for="inputName">Descripci&oacute;n</label>
                                <div class="controls">
                                    <input type="text" name="descripcion" id="description"  class="form-control" pattern="[A-Za-z\s]+" title="Especifica una descripcion correcto." >
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="inputName">Prioridad</label>
                                <div class="controls">
                                    <input type="text" name="prioridad" id="priority" class="form-control" pattern="[0-9]+" title="Especifica un nombre correcto."  >
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="inputName">Cantidad</label>
                                <div class="controls">
                                    <input type="text" name="cantidad" id="cant"  class="form-control" pattern="[0-9]+" title="Especifica una cantidad correcta." >
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="inputName">Tipo</label>
                                <div class="controls">
                                    <select class="selectpicker" data-style="btn-primary" id="type" name="tipo">
                                        <option value="0">Seleccione</option>
                                        <option value="1">Software</option>
                                        <option value="2">Hardware</option>
                                        <option value="3">Otro</option>
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
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>
<jsp:include page="/include/footerHome.jsp" />       
<jsp:include page="/include/end.jsp" />
