
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-7 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO ALARMAS</h1>
     <section class="boxed padding">
             <ul class="nav nav-tabs">
        <li role="presentation"><a href="/RDW1/app/alarma/nuevoAlarma.jsp">Registro</a></li>
        <li role="presentation"><a href="/RDW1/app/alarma/listadoAlarma.jsp">Listado de Alarmas</a></li>                    
        <li role="presentation" class="active"><a href="/RDW1/app/alarma/listadoAlarma.jsp">Alarma - ${alarma.nombreAlarma}</a></li>                    
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
                    <form class="form-horizontal" action="<c:url value='/editarAlarma' />" method="post">
                        <div class="control-group">
                            <div class="control-group">                                        
                                <div class="controls">
                                    <input type="hidden" name="id_edit" value="${alarma.id}">
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="row">
                                <div class="col-sm-5">
                                    <label class="control-label" for="inputName">C&oacute;digo Alarma</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-5">
                                    <div class="controls">
                                        <input type="text" name="codigo_alarma_edit" id="code" readonly class="form-control" value="${alarma.codigoAlarma}">
                                    </div>
                                </div>       
                            </div>  
                        </div>  
                        <div class="control-group">
                            <div class="row">
                                <div class="col-sm-5">
                                    <label class="control-label" for="inputName">Nombre</label>
                                </div>
                            </div>
                            <div class="controls">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <input type="text" name="nombre_edit" id="name" class="form-control" value="${alarma.nombreAlarma}">
                                    </div>
                                </div>   
                            </div>                 
                        </div>

                        <br>
                        <div class="control-group">
                            <label class="control-label" for="inputName">Unidad de Medida</label>
                            <div class="controls">
                                <select class="selectpicker" data-style="btn-primary" name="unidad_edit" id="measure">
                                    <option value="0">Seleccione</option>
                                    <option value="Minutos">Minutos</option>
                                    <option value="Segundos">Segundos</option>                         
                                    <option value="Bloque">Bloque</option>
                                    <option value="Otro">Otro</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-sm-5">
                                    <label class="control-label" id="etq3" for="cual">Cu&aacute;l?: </label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-5">
                                    <div class="controls">
                                        <input type="text" class="form-control" name="cual_unidad_edit" id="which_one">
                                    </div>                  
                                </div>                  
                            </div> 
                        </div>             
                        <br>
                        <div class="control-group">                            
                            <div class="controls">
                                <input type="submit" class="btn btn-primary botonAlarma" value="Guardar">
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
        $("#priority option[value=${alarma.prioridad}]").attr("selected",true);
        $("#etq1").hide();
        $("#which_type").hide();
        
        $("#etq3").hide();        
        $("#which_one").hide(); 
        
         /********************************************************************************/
        if("${alarma.tipoAlarma}".includes("Otro-"))
        {
            $("#which_type").show();
            $("#etq1").show();
            $("#which_type").val("${alarma.tipoAlarma}");  
            $("#which_type").select();
        }
        else
        {
            $("#which").hide();
            $("#etq").hide();
            $("#type option[value=${alarma.tipoAlarma}]").attr("selected",true);
        }
         
         
        
         if("${alarma.unidadDeMedicion}".includes("Otro-"))
         {
            $("#which_one").show();
            $("#etq3").show();
            $("#which_one").val("${alarma.unidadDeMedicion}");
             $("#which_one").select();
         }
         else
         {            
            $("#which_one").hide();
            $("#etq3").hide();
            $("#measure option[value=${alarma.unidadDeMedicion}]").attr("selected",true);            
         }
         
         
         /********************************************************************************/
         $("#type").change(function ()
        {
            var item = $(this).val();
            if (item === "Otro")
            {
                console.log("DEBE APARECER CUAL");
                $("#etq1").show();
                $("#which_type").show();
            }
            else
            {
                console.log("NO ENCUENTRA");
                $("#etq1").hide();       
                $("#which_type").hide();
                
            }
        }); 
        
        /************************************/
        $("#measure").change(function ()
        {
            var item = $(this).val();
            if (item === "Otro")
            {
                console.log("DEBE APARECER CUAL");
                $("#etq3").show();
                $("#which_one").show();
            }
            else
            {
                console.log("NO ENCUENTRA");
                $("#etq3").hide();       
                $("#which_one").hide();
                
            }
        });
         
      
      
         
         
         
             window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000);         
         
     });
     </script>
<jsp:include page="/include/end.jsp" />
