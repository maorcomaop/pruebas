
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-9 centered">
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
        <c:choose>
            <c:when test="${permissions.check(30)}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="/RDW1/app/movil/nuevoMovil.jsp">Registro</a></li>
                        <c:if test="${permissions.check(29)}">
                        <li role="presentation"><a href="/RDW1/app/movil/listadoMovil.jsp">Listado Veh&iacute;culos</a></li>                    
                        </c:if>
                        <li role="presentation"><a href="/RDW1/app/grupo_movil/listadoGrupoMovil.jsp">Grupos de Veh&iacute;culos</a></li>    
                </ul>
                <!--FORMULARIO PARA REGISTRAR UN NUEVO MOVIL-->
                
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1">                                 
                        <div>Los campos con asterisco (*) con obligatorios</div>                         
                        <form class="form-horizontal" action="<c:url value='/guardarMovil' />" method="post">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">INFORMACION DEL VEH&Iacute;CULO</h3>
                                </div>
                                <div class="panel-body">               
                                    <blockquote class="blockquote mb-0">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">Placa*</label>                                    
                                                <input type="text" name="placa" id="matricula" data-toggle="tooltip" title="Digite aqui la placa" class="form-control" autofocus=""/>
                                            </div>
                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">N&uacute;mero interno*</label>                                  
                                                <input type="text" name="numero_interno" id="number" data-toggle="tooltip" title="Digite aqui el numero interno" class="form-control" />
                                            </div>
                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">Propietario</label>                                    
                                                <select class="selectpicker" id="propietario" data-style="btn-primary" name="propietario"  ></select>
                                            </div>                                            
                                        </div>

                                        <div class="row">
                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">Capacidad de pasajeros</label>                                    
                                                <input type="text" name="cant_pasajeros" id="pasenger" data-toggle="tooltip" title="Digite la capacidad de pasajeros" class="form-control" value="0"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">Grupo</label>

                                                <select class="selectpicker" data-style="btn-primary" title="" name="grupo" id="group">                                        
                                                    <c:forEach items="${select.lstgrupomoviles}" var="grupo">
                                                        <option value="${grupo.id}">${grupo.nombreGrupo}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                        </div>
                                    </blockquote>
                                </div>
                            </div>
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">INFORMACION DESPACHO</h3>
                                </div>                                
                                <div class="panel-body">               
                                    <blockquote class="blockquote mb-0">
                                        <div class="row">

                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">D&iacute;a Pico y Placa</label>

                                                <select class="selectpicker" multiple name="dia_pico_placa" id="dayPico" data-actions-box="true" size="3" title="Seleccione los d&iacute;as de restricci&oacute;n" data-width="250px" data-style="btn-primary" >                                            
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

                                                <select class="selectpicker" name="dias_descanso" id="dayholiday" multiple data-actions-box="true" size="3" title="Seleccione los d&iacute;as de descanso" data-width="250px" data-style="btn-primary" >
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
                                                <label class="control-label">Agregar informacion GPS</label>
                                                <input id="aplicar" data-toggle="toggle" data-onstyle="primary" data-offstyle="danger" data-on="Si" data-off="No" type="checkbox" name="aplica" value="1">                         
                                            </div>  -->
                                        </div>
                                         
                                    </blockquote>
                                </div>
                                
                            </div>                            
                            <div class="panel panel-primary" id='info_gps'>
                                <div class="panel-heading">
                                    <h3 class="panel-title"><a data-toggle="collapse" href="#collapse11">INFORMACION HARDWARE Y GPS</a></h3>
                                </div>
                                <div class="panel-body">                                     
                                    <div class="col-sm-11" id="msjRec"></div>                                     
                                    <blockquote class="blockquote mb-0">                                        
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label class="control-label" for="inputName">Dispositivo</label>                                
                                                <select class="selectpicker" name="hardware" id="hrdwr" data-live-search="true" tabindex="1" size="3" title="Seleccione una tecnolog&iacute;a" data-width="180px" data-style="btn-primary" >                                        
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label id="etq_idgps" class="control-label" for="inputName">Id GPS</label>                                    
                                                <input type="text" name="id_gps" id="idgps" title="Digite aqui el codigo del GPS" tabindex="2" class="form-control" />
                                            </div>
                                            <div class="col-md-4">
                                                <label id="etq_brand" class="control-label" for="inputName">Marca</label>                            
                                                <select class="selectpicker" name="marca_gps" id="brand" data-live-search="true" tabindex="3" size="3" title="Seleccione una marca" data-width="150px" data-style="btn-primary" ></select>
                                            </div>                                                

                                        </div> 
                                        <div class="row">   
                                            <div class="col-md-4">
                                                <label id="etq_model" class="control-label" for="inputName">Modelo</label>                            
                                                <select class="selectpicker" name="modelo_gps" id="model" data-live-search="true" tabindex="4" size="3" title="Seleccione una modelo" data-width="150px" data-style="btn-primary" ></select>
                                            </div>
                                            <div class="col-md-4">
                                                <label id="etq_n_cel" class="control-label" for="inputName">N&uacute;mero celular</label>                                                    
                                                <input type="text" name="num_cel_gps" id="n_cel" tabindex="5" class="form-control">                                   
                                            </div>
                                            <div class="col-md-4">
                                                <label id="etq_oper" class="control-label" for="inputName">Operador</label>                                
                                                <select class="selectpicker" name="oper_cel_gps" id="oper" data-live-search="true" tabindex="6" size="3" title="Seleccione el operador" data-width="150px" data-style="btn-primary" ></select>                                                            
                                            </div>
                                        </div><!--FIN ROW-->                                        
                                    </blockquote>
                                </div>
                            </div> 
                            <!--***********************************************************************************************************************-->
                            <!--
                            <div class="panel panel-primary" id='info_docs'>
                                <div class="panel-heading">
                                    <h3 class="panel-title"><a data-toggle="collapse" href="#collapse20">DOCUMENTACI&Oacute;N</a></h3>
                                </div>
                                <div id="collapse20" class="panel-collapse collapse">
                                    <blockquote class="blockquote mb-0">
                                        
                                        <div class="panel panel-primary" id='RTM'>
                                            <div class="panel-heading">
                                                <h3 class="panel-title">REVISION TECNICO MECANICA</h3>
                                            </div>                                    
                                                <div class="panel panel-default">
                                                    <blockquote class="blockquote mb-0">
                                                        <div class="panel-body">
                                                            <div class="row">
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">C&oacute;digo</label>
                                                                    <input type="text" name="codigo_" id="c_"  class="form-control" data-toggle="tooltip" title="Digite" autofocus>
                                                                </div>                                                                
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Fecha de vencimiento</label>
                                                                    <input type="text" name="f_vto_" id="f_v_" class="form-control" data-toggle="tooltip" title="Digite">
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName" id="etq_cda">Centro de Diagnostico</label>
                                                                    <select class="selectpicker " data-style="btn-primary" name="centro_diag_" id="ce_d_" title="Seleccione">                                                                                       
                                                                    </select>
                                                                </div>                                                                
                                                            </div>
                                                            <div class="row">
                                                                
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Observaciones</label>
                                                                    <textarea cols="10" rows="3" name="observaciones_" id="obs_e" class="form-control"></textarea>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </blockquote>
                                                </div>

                                        

                                        </div> 
                                        <div class="panel panel-primary" id='TO'>
                                            <div class="panel-heading">
                                                <h3 class="panel-title">TARJETA DE OPERACION</h3>
                                            </div>                                    
                                                <div class="panel panel-default">
                                                    <blockquote class="blockquote mb-0">
                                                        <div class="panel-body">
                                                            <div class="row">
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">C&oacute;digo</label>
                                                                    <input type="text" name="codigo_to" id="c_to"  class="form-control" data-toggle="tooltip" title="Digite" autofocus>
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Modelo</label>
                                                                    <input type="text" name="modelo_to" id="m_to"  class="form-control" data-toggle="tooltip" title="Digite" autofocus>
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Fecha de vencimiento</label>
                                                                    <input type="text" name="f_vto_to" id="f_v_to" class="form-control" data-toggle="tooltip" title="Digite">
                                                                </div>                                                                                                                                                 
                                                            </div>
                                                            <div class="row">                                                            
                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName" id="etq_exp">Expedida en:</label>
                                                                    <select class="selectpicker " data-style="btn-primary" name="centro_exp_to" id="ce_e_to" title="Seleccione">                                                                                        
                                                                    </select>
                                                                </div> 

                                                                <div class="col-md-4">
                                                                    <label class="control-label" for="inputName">Observaciones*</label>
                                                                    <textarea cols="10" rows="3" name="observaciones_to" id="obs_to" class="form-control"></textarea>
                                                                </div>
                                                            </div>                                                           
                                                            
                                                        </div>
                                                    </blockquote>
                                                </div>

                                        

                                        </div> 
                                    </blockquote>
                                </div>
                            </div> 
                            -->
                                        <div class="row">
                                            <div class="col-md-4"></div>
                                            <div class="col-md-4"></div>
                                            <div class="col-md-4 ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="btn btn-primary botonMovil" data-toggle="tooltip" title="Haga clic para guardar" value="Guardar"></div>
                                        </div><!--FIN ROW--> 
                                        
                        </form>                    
                        
                    </div><!--FIN TAB-->
                </div><!--FIN TAB CONTENT-->
            </c:when>
            <c:otherwise>
                <jsp:include page="/include/accesoDenegado.jsp" />
            </c:otherwise>
        </c:choose> 
    </section>
</div>
<jsp:include page="/include/footerHome.jsp" />
<jsp:include page="/include/end.jsp" />