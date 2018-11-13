
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<div class="col-lg-10 centered">
<h1 class="display-3 bg-info text-center">M&Oacute;DULO VEH&Iacute;CULOS</h1>    
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
        <li role="presentation"><a href="/RDW1/app/movil/nuevoMovil.jsp">Registro</a></li>
        <li role="presentation"><a href="/RDW1/app/movil/listadoMovil.jsp">Listado de Veh&iacute;culos</a></li>                    
        <li role="presentation" class="active"><a href="/RDW1/app/movil/unMovil.jsp">Veh&iacute;culo Placa No.-> ${movil.placa}</a></li>                    
    </ul>    
     <!--FORMULARIO PARA REGISTRAR UN NUEVO MOVIL-->    
     <div class="tab-content">
     <div role="tabpanel" class="tab-pane padding active" id="pestana1">
         <div class="panel panel-default">             
                 <div class="panel-body">               
                             <form class="form-horizontal" action="<c:url value='/editarMovil' />" method="post">             
                                 <input type="hidden" name="id_edit" id="id_car" value="${movil.id}"><!--CODIGO DEL VEHICULO A EDITAR-->
                                 <input type="hidden" name="placa_edit_old" id="placa_edit_old" value="${movil.placa}"><!--CODIGO DEL VEHICULO A EDITAR-->
                                 <input type="hidden" name="id_g_edit" id="id_group" value="${rg.idGrupo}"><!--CODIGO DEL GRUPO-->
                                 <input type="hidden" name="id_p_edit" id="id_prop" value="${rp.fk_propietario}"><!--CODIGO DEL PROPIETARIO-->
                                 <!--******GPS*****************************************************************-->
                                 <input type="hidden" name="id_rghv_edit_old" id="id_rel_hgv" value="${rhgv.id}"><!--CODIGO DE LA RELACION HARWARE-VEHICULO-GPS-->
                                 <input type="hidden" name="id_gps_edit_old" id="id_gps_a" value="${rhgv.fk_gps}"><!--CODIGO DEL GPS-->
                                 <input type="hidden" name="id_hard_edit_old" id="id_har" value="${rhgv.fk_hardware}"><!--CODIGO DEL HARDWARE-->                                 
                                 <input type="hidden" name="id_sim_edit_old" id="id_sim_edit_old" value="${rhgv.fk_sim}"><!--CODIGO DE LA SIM-->
                                 <input type="hidden" name="num_celular_old" id="num_cel" value="${rhgv.numero_celular}"><!--NUMERO CELULAR DE LA SIM-->
                                 <input type="hidden" name="id_op_edit" id="id_op" value="${sim.fk_operador}"><!--CODIGO DEL OPERADOR-->
                                 <!--******REVISION TECNICO MECANICA*****************************************************************-->
                                 <input type="hidden" name="id_rtm" id="id_rtm_e" value="${rtm.id}"><!--ID REGISTRO DEL REVISION-->
                                 <input type="hidden" name="cod_rtm" id="cod_rtm_e" value="${rtm.codigo}"><!--CODIGO DEL REVISION-->
                                 <input type="hidden" name="fk_cda_rtm" id="id_cda_rtm_e" value="${rtm.fk_centro_diag}"><!--ID CDA-->
                                 <input type="hidden" name="fvto_rtm" id="fv_rtm_e" value="${rtm.fecha_vencimiento}"><!--FECHA VENCIMIENTO-->
                                 <!--******TARJETA DE OPERACION*****************************************************************-->
                                 <input type="hidden" name="id_to" id="id_to_e" value="${to.id}"><!--ID REGISTRO DEL TAREJTA OPERACION-->
                                 <input type="hidden" name="cod_to" id="cod_to_e" value="${to.codigo}"><!--CODIGO TARJETA-->
                                 <input type="hidden" name="fk_ce_to" id="id_ce_to_e" value="${to.fk_centro_exp}"><!--ID CE-->
                                 <input type="hidden" name="fvto_to" id="fv_to_e" value="${to.fecha_vencimiento}"><!--FECHA VENCIMIENTO-->
                                 
                                 
                                 
                                 <!--INFORMACION INICIAL DE VEHICULO-->
                                 <div class="panel panel-primary">
                                     <div class="panel-heading">
                                         <h3 class="panel-title">INFORMACION DEL VEH&Iacute;CULO</h3>
                                     </div>
                                     <div class="panel-body">               
                                         <blockquote class="blockquote mb-0">                                            
                                                 <div class="row">
                                                     <div class="col-md-4">
                                                         <label class="control-label" for="inputName">Placa</label>
                                                         <input type="text" name="placa_edit_new" id="matricula_edit" placeholder="" class="form-control" title="Especifica un nombre correcto." value="${movil.placa}">
                                                     </div><!--FIN COL-->
                                                     <div class="col-md-4">
                                                         <label class="control-label" for="inputName">N&uacute;mero interno</label>                        
                                                         <input type="text" name="numero_interno_edit" id="number_edit" class="form-control" title="Especifica un apellido correcto." value="${movil.numeroInterno}">
                                                     </div><!--FIN COL-->

                                                     <div class="col-md-4">
                                                         <label class="control-label" for="inputName">Propietario</label>                         
                                                         <select class="selectpicker" name="propietario_edit" id="prop_edit" data-width="250px" data-style="btn-primary" title="Seleccione un propietario"  >
                                                             <c:forEach items="${select.lst_propietario}" var="p">
                                                                 <option value="${p.id}">${p.nombre} ${p.apellido}</option>
                                                             </c:forEach>
                                                         </select>
                                                     </div><!--FIN COL-->
                                                 </div><!--FIN ROW-->
                                                 <div class="row">
                                                     <div class="col-md-4">   
                                                         <label class="control-label" for="inputName">Capacidad de pasajeros</label>                    
                                                         <input type="text" name="cant_pasajeros_edit" id="pasenger_edit" class="form-control" value="${movil.capacidad}">
                                                     </div>
                                                     <div class="col-md-4"> 
                                                         <label class="control-label" for="inputName">Grupo</label>                   
                                                         <select class="selectpicker" name="grupo_edit" id="group_edit" data-style="btn-primary" data-width="250px" title="Seleccione un grupo" >                                        
                                                             <c:forEach items="${select.lstgrupomoviles}" var="grupo">
                                                                 <option value="${grupo.id}">${grupo.nombreGrupo}</option>
                                                             </c:forEach>
                                                         </select>
                                                     </div>                           
                                                 </div><!--FIN ROW-->                                                                                          
                                         </blockquote>
                                     </div>                   
                                 </div><!--FIN PANEL-->
                                 <!--INFORMACION DEL DESPACHO-->
                                 <div class="panel panel-primary">
                                     <div class="panel-heading">
                                         <h3 class="panel-title">INFORMACI&Oacute;N DESPACHO</h3>
                                     </div>
                                     <div class="panel-body">               
                                         <blockquote class="blockquote mb-0">
                                             <div class="row">
                                                 <div class="col-md-4">
                                                     <label class="control-label" for="inputName">D&iacute;a Pico y Placa</label>          
                                                     <select class="selectpicker" multiple name="dia_pico_placa_edit" id="dayPico_edit" data-actions-box="true" size="3" title="Seleccione los d&iacute;as de restricci&oacute;n" data-width="250px" data-style="btn-primary" >
                                                         <option value="1">Lunes</option>
                                                         <option value="2">Martes</option>
                                                         <option value="3">Mi&eacute;rcoles</option>
                                                         <option value="4">Jueves</option>
                                                         <option value="5">Viernes</option>
                                                         <option value="6">S&aacute;bado</option>
                                                         <option value="7">Domingo</option>                                            
                                                     </select>
                                                 </div>
                                                 <div class="col-md-4">
                                                     <label class="control-label" for="inputName">D&iacute;a de descanso</label>            
                                                     <select class="selectpicker" multiple name="dias_descanso_edit" id="dayholiday_edit" data-actions-box="true" size="3" title="Seleccione los d&iacute;as de descanso" data-width="250px" data-style="btn-primary" >
                                                         <option value="1">1</option>
                                                         <option value="2">2</option>
                                                         <option value="3">3</option>
                                                         <option value="4">4</option>
                                                         <option value="5">5</option>
                                                         <option value="6">6</option>
                                                         <option value="7">7</option>
                                                         <option value="8">8</option>
                                                         <option value="9">9</option>
                                                         <option value="10">10</option>
                                                         <option value="11">11</option>
                                                         <option value="12">12</option>
                                                         <option value="13">13</option>
                                                         <option value="14">14</option>
                                                         <option value="15">15</option>
                                                         <option value="16">16</option>
                                                         <option value="17">17</option>
                                                         <option value="18">18</option>
                                                         <option value="19">19</option>
                                                         <option value="20">20</option>
                                                         <option value="21">21</option>
                                                         <option value="22">22</option>
                                                         <option value="23">23</option>                                            
                                                         <option value="24">24</option>                                            
                                                         <option value="25">25</option>
                                                         <option value="26">26</option>
                                                         <option value="27">27</option>
                                                         <option value="28">28</option>
                                                         <option value="29">29</option>
                                                         <option value="30">30</option>
                                                         <option value="31">31</option>                                            
                                                     </select>
                                                 </div>
                                                 <!--<div class="col-md-4">                                    
                                                 <label class="control-label">Agregar informacion GPS<input id="aplicar" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="aplica"></label>
                                                </div>-->
                                             </div>                   
                                         </blockquote>
                                     </div>
                                 </div><!--FIN PANEL-->
                                 <!--INFORMACION DEL HARDWARE Y GPS-->
                                 <div class="panel panel-primary" id="">
                                     <div class="panel-heading">
                                         <h3 class="panel-title">INFORMACI&Oacute;N HARDWARE Y GPS</h3>
                                     </div>
                                     
                                         <div class="panel-body">
                                             <!--************GPS*****************************************************************************************-->
                                             <div class="panel panel-primary" id="">
                                                 <div class="panel-heading">
                                                     <h3 class="panel-title"><a data-toggle="collapse" href="#collapse120">GPS</a></h3>
                                                 </div>
                                                 <div class="panel-body">
                                                     <div class="col-sm-11" id="msj"></div> 
                                                         
                                                     <blockquote class="blockquote mb-0">                                                                             
                                                         <div class="row">
                                                              <div class="col-md-4">
                                                                 <label id="etq_hrdwr_edit" class="control-label" for="inputName">Seleccione Hardware</label>
                                                                 <select class="selectpicker" name="hardware_edit" id="hrdwr_edit" data-live-search="true" size="3" tabindex="1" title="Seleccione una tecnolog&iacute;a" data-width="175px" data-style="btn-primary" >                                        
                                                                     <c:forEach items="${select.lst_hardware}" var="h">
                                                                         <option value="${h.id}">${h.nombre}</option>
                                                                     </c:forEach>
                                                                 </select>
                                                             </div>   
                                                             <div class="col-md-4">
                                                                 <label class="control-label" for="inputName">Id GPS</label>                                                                                     
                                                                 <input type="text" name="codigo_gps_edit" id="codigo_gps_edit" class="form-control" title="Especifica " tabindex="2" value="${rhgv.fk_gps}">
                                                             </div>
                                                             <div class="col-md-4">
                                                                 <label class="control-label" for="inputName">Marca</label>
                                                                 <select class="selectpicker" name="marca_gps_edit" id="marca_gps_rec" data-live-search="true" size="3" tabindex="3" title="Seleccione una marca" data-width="150px" data-style="btn-primary" >                                        
                                                                     <c:forEach items="${select.lst_gpsmarca}" var="g">
                                                                         <option value="${g.id}">${g.nombre}</option>
                                                                     </c:forEach>
                                                                 </select>
                                                             </div>
                                                             
                                                         </div>
                                                         <div class="row">
                                                             <div class="col-md-4">
                                                                 <label class="control-label" for="inputName">Modelo</label>
                                                                 <select class="selectpicker" name="modelo_gps_edit" id="modelo_gps_rec" data-live-search="true" size="3" tabindex="4" title="Seleccione un modelo" data-width="150px" data-style="btn-primary" >                                        
                                                                     <c:forEach items="${select.lst_gpsmodelo}" var="g1">
                                                                         <option value="${g1.id}">${g1.nombre}</option>
                                                                     </c:forEach>
                                                                 </select>
                                                             </div>
                                                             <div class="col-md-4">
                                                                 <label class="control-label" for="inputName">N&uacute;mero celular</label>                                                                                         
                                                                 <input type="text" name="numero_celular_edit" id="numero_celular_edit" class="form-control"  tabindex="5" value="${rhgv.numero_celular}">
                                                                 <input type="hidden" name="id_sim_edit" id="id_sim_edit" value="${rhgv.fk_sim}">
                                                             </div>
                                                             <div class="col-md-4">
                                                                 <label class="control-label" for="inputName">Operador</label>
                                                                 <select class="selectpicker" name="operador_celular_edit" id="oper_gps_rec" data-live-search="true" size="3" tabindex="6" title="Seleccione el operador" data-width="150px" data-style="btn-primary" >                                                                                    
                                                                     <c:forEach items="${select.lst_operador}" var="o">
                                                                         <option value="${o.id}">${o.nombre}</option>
                                                                     </c:forEach>
                                                                 </select>
                                                             </div>                                                             
                                                         </div>                                                                                                                                                                                                                  
                                                     </blockquote>
                                                 </div>
                                             </div>    
                                            <!--<div class="panel panel-primary" id='RTM'>
                                            <div class="panel-heading">
                                                <h3 class="panel-title">REVISION TECNICO MECANICA</h3>
                                            </div>                                    
                                                <div class="panel panel-default">
                                                    <blockquote class="blockquote mb-0">
                                                        <div class="panel-body">
                                                            <div class="row">
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">C&oacute;digo</label>
                                                                    <input type="text" name="cod_rtm_edit" id="cod_rtm_ed"  class="form-control" data-toggle="tooltip" title="Digite" value="${rtm.codigo}">
                                                                </div>                                                                
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Fecha de vencimiento*</label>
                                                                    <input type="text" name="fv_rtm_edit" id="fv_rtm_ed" class="form-control" data-toggle="tooltip" title="Digite" value="${rtm.fecha_vencimiento}">
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Centro de diagnostico automotor*</label>
                                                                    <select class="selectpicker " data-style="btn-primary" name="id_cda_rtm_edit" id="fk_cda_rtm" title="Seleccione">                                                                                       
                                                                    </select>
                                                                </div>  
                                                            </div>
                                                            <div class="row">                                                                
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Observaciones*</label>
                                                                    <textarea cols="10" rows="3" name="obs_edit" id="obs_ed" class="form-control">${rtm.observaciones}</textarea>
                                                                </div>
                                                            </div> 
                                                        </div>
                                                    </blockquote>
                                                </div>                                      

                                        </div> -->
                                             <!--**********************************************************************-->
                                             <!--
                                             <div class="panel panel-primary" id='RTM'>
                                            <div class="panel-heading">
                                                <h3 class="panel-title">TARJETA DE OPERACION</h3>
                                            </div>                                    
                                                <div class="panel panel-default">
                                                    <blockquote class="blockquote mb-0">
                                                        <div class="panel-body">
                                                            <div class="row">
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">C&oacute;digo</label>
                                                                    <input type="text" name="cod_to_edit" id="cod_to_ed"  class="form-control" data-toggle="tooltip" title="Digite" value="${to.codigo}">
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Modelo</label>
                                                                    <input type="text" name="mod_to_edit" id="mod_to_ed"  class="form-control" data-toggle="tooltip" title="Digite" value="${to.modelo}">
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Fecha de vencimiento*</label>
                                                                    <input type="text" name="fv_to_edit" id="fv_to_ed" class="form-control" data-toggle="tooltip" title="Digite" value="${to.fecha_vencimiento}">
                                                                </div>                                                                                                                                                 
                                                            </div>
                                                            <div class="row">                                                                
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Expedida en:</label>
                                                                    <select class="selectpicker " data-style="btn-primary" name="fk_ce_to_edit" id="fk_ce_to" title="Seleccione">                                                                                        
                                                                    </select>
                                                                </div> 

                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Observaciones*</label>
                                                                    <textarea cols="10" rows="3" name="obs_to_edit" id="obs_to_e" class="form-control">${to.observaciones}</textarea>
                                                                </div>                                                            
                                                            </div>
                                                            
                                                        </div>
                                                    </blockquote>
                                                </div>

                                        

                                        </div> 
                                                                -->
                                         </div>
                                     
                                 </div><!--FIN BODY PANEL OTROS DATOS VEHICULO-->                                 
                                 <div class="row">
                                     <div class="col-md-4"></div>
                                     <div class="col-md-4"></div>
                                     <div class="col-md-4 ">
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         <input type="submit" class="btn btn-primary botonMovilEdit" value="Guardar"></div>
                                 </div><!--FIN ROW-->
                             </form>   
                                                                
                </div><!--FIN PANEL TOTAL-->             
    </div>
   </div>
             </section>
            </div>      
        <jsp:include page="/include/footerHome.jsp" /> 
<script>        
        $('.collap1se').collapse();
        pasarIdPicoDescanso("${movil.diaPicoplaca}", "${movil.diaDescanso}");
        pasarIdGrupo("${rg.idGrupo}");         
        pasarIdPropietario("${rp.fk_propietario}");
</script> 

<c:choose>
    <c:when test="${rhgv.fk_hardware > 0}">
        <script> 
        pasarIdHardware("${rhgv.fk_hardware}");         
        </script> 
     </c:when>
     <c:otherwise>
     <script> 
        pasarIdHardware("");         
     </script> 
     </c:otherwise>            
</c:choose>
        
<script> 
        pasarMarcaModelo1("${mca.id}", "${mdl.id}");
        /********************************************************************/        
        pasarIdOp1("${rhgv.fk_operador}"); 
        /*OTROS ITEMS**/
        tipoHardware("${rhgv.fk_hardware}");        
        cargarCDA_Edit("${rtm.fk_centro_diag}");
        cargarCE_Edit("${to.fk_centro_exp}");
        window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
            //$(this).removeClass("alert");
        });
    }, 4000);
</script>    
<jsp:include page="/include/end.jsp" />