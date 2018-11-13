
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-9 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO CONDUCTOR</h1>    
    <section class="boxed padding">
        
        <c:choose>
            <c:when test="${permissions.check(30)}">
                <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="/RDW1/app/conductor/configuracionDesempeno.jsp">Configuracion</a></li>
                </ul>
                <!---->
                <!--FORMULARIO PARA REGISTRAR-->
                
                
                <!--FORMULARIO PARA REGISTRAR-->
                
                <div class="tab-content">
                
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">
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
                        <form class="form-horizontal" action="<c:url value='/guardarConfiguracion' />" method="post">                            
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <!--<h3 class="panel-title">PARAMETROS DE DESEMPEÑO </h3>-->
                                    <h4 class="panel-title"><a data-toggle="collapse" href="#collapse1">PARAMETROS DE DESEMPE&Ntilde;O</a></h4>
                                </div>
                                <div id="collapse1" class="panel-collapse collapse">
                                <div class="panel-body">               
                                    <blockquote class="blockquote mb-0">
                                        <div class="row">                                            
                                             <div class="col-md-3">
                                                <label class="control-label" for="inputName">Nombre</label>                                    
                                                <input type="text" name="nombre" id="nom_conf" data-toggle="tooltip" title="Digite aqui el nombre" class="form-control" autofocus=""/>
                                                <small id="" class="form-text text-muted">Nombre que se asigna a la configuracion</small>
                                            </div>
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Puntaje m&aacute;ximo</label>                                    
                                                <input type="text" name="puntaje_max" id="pto_max" data-toggle="tooltip" title="Digite aqui la placa" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Valor que se asigna de manera inicial a un conductor para evaluar su desempeño</small>
                                            </div>
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Puntos exceso de velocidad</label>                                  
                                                <input type="text" name="puntos_exceso_vel" id="pto_e_v" data-toggle="tooltip" title="Digite aqui el numero interno" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se descuenta del puntaje m&aacute;ximo por cada exceso de velocidad</small>
                                            </div>                                                                                       
                                            <div class="col-md-3">
                                                 <label class="control-label" for="inputName">Velocidad m&aacute;xima</label>                                    
                                                <input type="text" name="vel_max" id="v_max" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se asigna con base a la normatividad vigente de cada pais para limites de velocidad</small>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Puntos ruta no cumplida</label>                                    
                                                <input type="text" name="puntos_ruta" id="pto_ruta" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se descuenta del puntaje m&aacute;ximo por cada incumplimiento de ruta</small>
                                            </div> 
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Porcentaje de ruta</label>                                    
                                                <input type="text" name="porcentaje_ruta" id="por_ruta" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se compara con el porcentaje de ruta calculado por el sistema</small>
                                            </div>
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Puntos dias no laborados</label>                                    
                                                <input type="text" name="puntos_dias_no_labor" id="pto_dias" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se descuenta del puntaje m&&aacute;ximo por cada d&iacute;a no laborado</small>
                                            </div>
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">D&iacute;as de descanso</label>                                    
                                                <input type="text" name="cantidad_dias_descanso" id="d_des" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor corresponde a la cantidad de d&iacute;as libres que tiene un conductor en un reango de 30 d&iacuteas;s</small>
                                            </div>
                                            </div>                                        
                                          <div class="row">
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Puntos adicionales IPK > 0</label>                                    
                                                <input type="text" name="puntos_ipk_mayor" id="pto_ipk_max" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se ADICIONA al puntaje maximo cuando el IPK total de la empresa es un valor MENOR que el IPK asociado a un conductor</small>
                                            </div>
                                            <div class="col-md-3">
                                                <label class="control-label" for="inputName">Puntos descontados IPK < </label>                                    
                                                <input type="text" name="puntos_ipk_menor" id="pto_ipk_men" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                                <small id="" class="form-text text-muted">Este valor se DESCUENTA del puntaje m&aacute;ximo cuando el IPK total de la empresa es un valor MAYOR que el IPK asociado a un conductor</small>
                                            </div>                                            
                                             
                                        </div>
                                    
                                    <div class="row">
                                            <div class="col-md-4"></div>
                                            <div class="col-md-4"></div>
                                            <div class="col-md-4 ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="btn btn-primary botonConfigDes" data-toggle="tooltip" title="Haga clic para guardar" value="Guardar"></div>
                                        </div><!--FIN ROW--> 
                                        </blockquote>
                                </div>
                                </div>
                            </div> 
                        </form>
                        <!--******************************************************************************************-->                            
                        <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <!--<h3 class="panel-title">PARAMETROS DE DESEMPEÑO </h3>-->
                                    <h4 class="panel-title"><a data-toggle="collapse" href="#collapse2">BUSQUEDA</a></h4>
                                </div>
                                <div id="collapse2" class="panel-collapse collapse">
                                <div class="panel-body">               
                                    <blockquote class="blockquote mb-5">
                                        <div class="row">
                                            <div class="col-md-4">
                                                 <label id="etq_oper" class="control-label" for="inputName">Seleccione una configuracion</label>                                
                                                <select class="selectpicker" name="name_conf" id="name_conf" data-live-search="true" size="3" title="Seleccione el operador" data-width="250px" data-style="btn-primary" >                                                                                    
                                                </select>                                                 
                                                <small id="" class="form-text text-muted">Se debe escoger una configuracion para ser editada</small>                                                
                                            </div>                                                                                        
                                        </div>                                                                        
                                        </blockquote> 
                                        <div id="msgs"></div>                                        
                                        
                                        <div class="col-md-12">
                                            <form class="form-horizontal" action="" method="">
                                                <div class="panel panel-primary">
                                                <div class="panel-heading">                                                    
                                                    <h4 id="titulo" class="panel-title"></h4>
                                                </div>
                                            <div id="" class="panel-collapse">
                                            <div class="panel-body">               
                                                    <div class="row">                                            
                                                         <div class="col-md-3">
                                                             <input type="hidden" id="id_conf"/>
                                                            <label class="control-label" for="inputName">Nombre</label>                                    
                                                            <input type="text" name="" id="name_edit" data-toggle="tooltip" title="" class="form-control" autofocus=""/>
                                                            <small id="" class="form-text text-muted">Nombre que se asigna a la configuracion</small>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Puntaje m&aacute;ximo</label>                                    
                                                            <input type="text" name="" id="p_max_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Valor que se asigna de manera inicial a un conductor para evaluar su desempeño</small>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Puntos exceso de velocidad</label>                                  
                                                            <input type="text" name="" id="p_ex_vel_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se descuenta del puntaje m&aacute;ximo por cada exceso de velocidad</small>
                                                        </div>                                                                                       
                                                        <div class="col-md-3">
                                                             <label class="control-label" for="inputName">Velocidad m&aacute;xima</label>                                    
                                                            <input type="text" name="" id="vel_max_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se asigna con base a la normatividad vigente de cada pais para limites de velocidad</small>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Puntos ruta no cumplida</label>                                    
                                                            <input type="text" name="" id="p_ruta_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se descuenta del puntaje m&aacute;ximo por cada incumplimiento de ruta</small>
                                                        </div> 
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Porcentaje de ruta</label>                                    
                                                            <input type="text" name="" id="por_ruta_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se compara con el porcentaje de ruta calculado por el sistema</small>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Puntos dias no laborados</label>                                    
                                                            <input type="text" name="" id="p_dias_no_labor_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se descuenta del puntaje m&&aacute;ximo por cada d&iacute;a no laborado</small>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">D&iacute;as de descanso</label>                                    
                                                            <input type="text" name="" id="dias_desc_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor corresponde a la cantidad de d&iacute;as libres que tiene un conductor en un reango de 30 d&iacuteas;s</small>
                                                        </div>
                                                        </div>                                        
                                                      <div class="row">
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Puntos adicionales IPK > 0</label>                                    
                                                            <input type="text" name="" id="p_ipk_mayor_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se ADICIONA al puntaje maximo cuando el IPK total de la empresa es un valor MENOR que el IPK asociado a un conductor</small>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="control-label" for="inputName">Puntos descontados IPK < </label>                                    
                                                            <input type="text" name="" id="p_ipk_menor_edit" data-toggle="tooltip" title="" class="form-control" />
                                                            <small id="" class="form-text text-muted">Este valor se DESCUENTA del puntaje m&aacute;ximo cuando el IPK total de la empresa es un valor MAYOR que el IPK asociado a un conductor</small>
                                                        </div>                                            

                                                    </div>

                                                <div class="row">
                                                        <div class="col-md-4"></div>
                                                        <div class="col-md-4"></div>
                                                        <div class="col-md-4 ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <!---<input type="submit" class="btn btn-primary boton" data-toggle="tooltip" title="Haga clic para guardar" value="Guardar">-->
                                                            <a href="#" role="button"  class="btn btn-primary" id="guardar_edicion" title="Haga clic aqui para guardar">Guardar</a>                                                                
                                                        </div>
                                                    </div><!--FIN ROW--> 
                                        
                                                </div>
                                                </div>
                                            </div> 
                                            </form>
                                        </div>
                                     
                                    
                                </div>
                                </div>
                            </div><!--FIN PANEL BUSQUEDA-->                         
                        <!--******************************************************************************************-->
                        <div class="panel panel-primary">
                                <div class="panel-heading">                                    
                                    <h4 class="panel-title"><a data-toggle="collapse" href="#collapse4">ACTIVAR</a></h4>
                                </div>
                                <div id="collapse4" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <blockquote class="blockquote mb-0">
                                                 <div class="row">
                                                    <div class="col-md-5">
                                                        <label id="etq" class="control-label" for="inputName">Seleccione una configuracion</label>                                
                                                       <select class="selectpicker" name="" id="name_act" data-live-search="true" size="3" title="Seleccione" data-width="250px" data-style="btn-primary" >
                                                       </select>                                                 
                                                       <small id="" class="form-text text-muted">Se debe escoger una configuracion para HABILITAR; 
                                                           Recuerde que este proceso har&aacute; que el reporte funcione correctamente</small>
                                                    </div>
                                                </div>
                                      </blockquote> 
                                      <div id="msgs_act"></div>                                                                            
                                </div>
                                </div>
                            </div><!--FIN PANEL ACTIVAR-->                         
                        <!--******************************************************************************************-->    
                        <div class="panel panel-primary">
                                <div class="panel-heading">                                    
                                    <h4 class="panel-title"><a data-toggle="collapse" href="#collapse3">DESACTIVAR</a></h4>
                                </div>
                                <div id="collapse3" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <blockquote class="blockquote mb-0">
                                                 <div class="row">
                                                    <div class="col-md-5">
                                                        <label id="etq" class="control-label" for="inputName">Seleccione una configuracion</label>                                
                                                       <select class="selectpicker" name="" id="name_del" data-live-search="true" size="3" title="Seleccione" data-width="250px" data-style="btn-primary" >
                                                       </select>                                                 
                                                       <small id="" class="form-text text-muted">Se debe escoger una configuracion para INHABILITAR; 
                                                           Recuerde que este proceso har&aacute; que el reporte deje de funcionar</small>
                                                    </div>
                                                </div>
                                      </blockquote> 
                                      <div id="msgs_del"></div>                                                                            
                                </div>
                                </div>
                            </div><!--FIN PANEL INACTIVAR-->                         

                        
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