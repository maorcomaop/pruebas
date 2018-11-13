
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
                        <div class="alert alert-danger1">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Informacion </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose>     
        <c:choose>
            <c:when test="${permissions.check(66)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/inventarioGPS/nuevoGps.jsp">Registro</a></li>                        
                    <li role="presentation"><a href="/RDW1/app/inventarioGPS/listadoGps.jsp">Listado Documentos</a></li>
                </ul>


                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                        
                        <form class="form-horizontal" action="<c:url value='/guardarGPSInventario' />" method="post">
                        
                            <blockquote class="blockquote mb-0">                                                                             
                                <div class="row">
                                    <div class="col-md-4">
                                        <label class="control-label" for="inputName">Codigo GPS</label>                                                                                     
                                        <input type="text" name="id" id="c" class="form-control" title="Especifica ">
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
                                        <select class="selectpicker" name="modelo" id="" data-live-search="true" size="3" title="Seleccione un modelo" data-width="200px" data-style="btn-primary" >                                        
                                            <c:forEach items="${select.lst_gpsmodelo}" var="g1">
                                                <option value="${g1.id}">${g1.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class=" col-md-4">
                                            <label class="control-label" >Asociar una sim:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input name="asociar" id="as_sim" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" checked="" value="1">
                                                                                 
                                    </div>
                                    <div class="col-md-4" id="nc">
                                        <label class="control-label" for="inputName">N&uacute;mero celular</label>                                                                                         
                                        <input type="text" name="numero" id="n_c" class="form-control">
                                    </div>
                                    <div class="col-md-4" id="op">
                                        <label class="control-label" for="inputName">Operador</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <select class="selectpicker" name="operador" id="" data-live-search="true" size="3" title="Seleccione el operador" data-width="200px" data-style="btn-primary" >                                                                                    
                                            <c:forEach items="${select.lst_operador}" var="o">
                                                <option value="${o.id}">${o.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    
                                    </div>
                                    <div class="row">
                                        
                                        <div class=" col-md-4"></div>
                                        <div class=" col-md-4"></div>
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

            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose>
    </section>
</div>
<jsp:include page="/include/footerHome.jsp" />
<script>
    $("#as_sim").attr('checked',false);   
    $("#nc").hide();
    $("#op").hide();
    $(document).ready(function () {
        
         $('#f_v').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});      
         
          $('#as_sim').change(function (){
            if ($("#as_sim").prop('checked') === true)
            {                 
               $("#nc").show();
               $("#op").show();
               console.log("si");
            }else{
               $("#nc").hide();
               $("#op").hide();
                console.log("no");
            } 
        }); 
        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);

    });
</script>
<jsp:include page="/include/end.jsp" />