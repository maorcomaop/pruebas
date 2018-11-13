
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO GPS</h1>
    
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
          <li role="presentation" ><a href="/RDW1/app/inventarioGPS/nuevoGps.jsp">Registro</a></li>                        
          <li role="presentation"><a href="/RDW1/app/inventarioGPS/listadoGps.jsp">Listado Documentos</a></li>                                            
          <li role="presentation" class="active"><a href="/RDW1/app/inventarioGPS/unGps.jsp"><b>Editar</b> GPS: <b>${gps_v.id}</b></a></li>                                            
        </ul>        <!--FORMULARIO PARA REGISTRAR UN NUEVO PERFIL-->
        
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">
               <form class="form-horizontal" action="<c:url value='/guardarGPSInventario' />" method="post">
                        
                            <blockquote class="blockquote mb-0">                                                                             
                                <div class="row">
                                    <div class="col-md-4">
                                        <label class="control-label" for="inputName">Codigo GPS</label>                                                                                     
                                        <input type="text" name="id_edit" id="c" class="form-control" title="Especifica" value="${gps_v.id}">
                                    </div>
                                    <div class="col-md-4">
                                        <label class="control-label" for="inputName">Marca&nbsp;&nbsp;&nbsp;</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <select class="selectpicker" name="marca" id="ma" data-live-search="true" size="3" title="Seleccione una marca" data-width="200px" data-style="btn-primary" >                                        
                                            <c:forEach items="${select.lst_gpsmarca}" var="g">
                                                <option value="${g.id}">${g.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label class="control-label" for="inputName">Modelo</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <select class="selectpicker" name="modelo" id="mo" data-live-search="true" size="3" title="Seleccione un modelo" data-width="200px" data-style="btn-primary" >                                        
                                            <c:forEach items="${select.lst_gpsmodelo}" var="g1">
                                                <option value="${g1.id}">${g1.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label class="control-label" for="inputName">N&uacute;mero celular</label>                                                                                         
                                        <input type="text" name="numero" id="n_c" class="form-control" value="${gps_v.celular}">
                                    </div>
                                    <div class="col-md-4">
                                        <label class="control-label" for="inputName">Operador</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <select class="selectpicker" name="operador" id="op" data-live-search="true" size="3" title="Seleccione el operador" data-width="200px" data-style="btn-primary" >                                                                                    
                                            <c:forEach items="${select.lst_operador}" var="o">
                                                <option value="${o.id}">${o.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>                                    
                                    <div class="row">                                        
                                        <div class=" col-md-4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                                            
                                            <button type="submit" id="" class="btn btn-primary ">Guardar</button>
                                    </div>
                                </div>
                                </div>
                               
                            </blockquote>

                        </form> 
            </div>    
        </div> 
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />

<script>
    $(document).ready(function () {
        $("#ma").selectpicker('val', '${gps_v.fk_marca}');
        $("#mo").selectpicker('val', '${gps_v.fk_modelo}');
        $("#op").selectpicker('val', '${gps_v.fk_operador}');
        
        $('#f_v_edit').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});        
        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
    });
</script>

<jsp:include page="/include/end.jsp" />