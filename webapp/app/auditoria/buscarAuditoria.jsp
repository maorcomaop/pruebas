
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-7 centered">   
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO AUDITOR&Iacute;A</h1>
    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Auditoria'])}"> 
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/auditoria/buscarAuditoria.jsp">Buscar auditor&iacute;as</a></li>        
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">                  
                        <form class="form-horizontal" action="<c:url value='/cargarAuditoria' />" method="post">                      

                            <div class="control-group">
                                <select class="selectpicker" data-style="btn-primary" name="tablas" id="type">
                                    <option value="0">Seleccione la tabla a auditar</option>                         
                                    <option value="tbl_auditoria_alarma">Alarma</option>
                                    <option value="tbl_auditoria_categoria_descuentos">Categor&iacute;as</option>
                                    <option value="tbl_auditoria_conductor">Conductor</option>
                                    <option value="tbl_auditoria_empresa">Empresa</option>
                                    <option value="tbl_auditoria_informacion_registradora">Informaci&oacute;n registradora</option>
                                    <option value="tbl_auditoria_liquidacion_general">Liquidaci&oacute;n</option>                                    
                                    <option value="tbl_auditoria_perfil">Perfil</option>                         
                                    <option value="tbl_auditoria_punto">Punto de Control</option>                                    
                                    <option value="tbl_auditoria_ruta">Ruta</option>                                    
                                    <option value="tbl_auditoria_tarifa">Tarifa</option>                                    
                                    <option value="tbl_auditoria_usuario">Usuario</option>
                                    <option value="tbl_auditoria_vehiculo">Veh&iacute;culo</option>
                                </select>                 
                            </div>
                           <br>
                           
                           <div class="control-group">                               
                               <label class="control-label">
                               <input type="radio" name="cantidad_registros" value="100">100
                               </label>
                               <br>
                               <label class="control-label">
                               <input type="radio" name="cantidad_registros" value="200">200
                               </label>
                               <br>
                               <label class="control-label">
                                   <input type="radio" name="cantidad_registros" value="0" checked="checked">Todos
                               </label>                               
                           </div>
                           <!--<div class="control-group">                                  
                                <label class="control-label">Consulta con horario</label>
                                <div class="controls">                                          
                                    <input id="prueba" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="buscar_tiempo" value="1">
                                </div>                            
                            </div>--> 
                            <br>
                            <div id="box_without_hour">
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="fecha_inicio">Fecha Inicio</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="controls">
                                            <input type="text" class="form-control" name="fecha_inicio" id="dpd1" placeholder="Ingrese la Fecha Inicio" >
                                        </div>  
                                    </div>  
                                </div>  
                            </div>  
                            <br>
                            <div class="control-group">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <label class="control-label" for="fecha_fin">Fecha Fin</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="controls">
                                            <input type="text" class="form-control" name="fecha_fin" id="dpd2" placeholder="Ingrese la Fecha Fin">
                                        </div>
                                    </div>
                                </div>

                            </div>
                            </div>
                            
                            
                            <!--<div id="box_with_hour">
                                <div class="control-group">

                                    <div class="row">
                                        <div class="col-sm-5">
                                            <label class="control-label" for="inputName">Fecha y hora Inicio</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <div class="controls">
                                                <input type="text" class="form-control"  name="fecha_hora_inicio" id="dpd3">                                            
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <div class="control-group">
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <label class="control-label" for="inputName">Fecha y hora Fin</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <div class="controls">
                                                <input type="text" class="form-control"  name="fecha_hora_fin" id="dpd4">
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                            </div>
                            
                            
                            <br>
                            <div class="control-group">
                                <input type="submit" class="btn btn-primary botonAditoria" value="Buscar">
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
<script>
    var now = new Date();
    $('#box_with_hour').hide();
    now.setDate(now.getDate() - 1);
    $('#dpd1').datetimepicker({format: 'YYYY-M-D', useCurrent: true, defaultDate: now});
    $("#dpd1").on("dp.change", function (e) {
        $('#dpd2').data("DateTimePicker").minDate(e.date);
    });

    $('#dpd2').datetimepicker({format: 'YYYY-M-D', useCurrent: false, defaultDate: new Date()});
    $("#dpd2").on('dp.change', function (e) {
        $('#dpd1').data("DateTimePicker").maxDate(e.date);
    });
     /*******************************************************************************************************/
    
   $('#dpd3').datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', useCurrent: true, defaultDate: 'now' });
    $("#dpd3").on("dp.change", function (e) {
        $('#dpd4').data("DateTimePicker").minDate(e.date);
    });

    $('#dpd4').datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', useCurrent: false, defaultDate: 'now' });
    $("#dpd4").on('dp.change', function (e) {
        $('#dpd3').data("DateTimePicker").maxDate(e.date);
    });
    
    $('#prueba').change(function ()
        {
            if ($(this).prop('checked') === true)
            {                                              
                $('#box_with_hour').show();
                $('#box_without_hour').hide();                 
            } else {
                $('#box_without_hour').show();
                $('#box_with_hour').hide();                
            }
        });
</script>
<jsp:include page="/include/end.jsp" />