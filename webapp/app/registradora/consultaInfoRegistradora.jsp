<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />
<!--<div style="display: inline; margin-left: 20px;">
    <a href="#"><strong>Información Registradora</strong></a>
</div>
</div>                
</header>-->

<div>
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REGISTRADORA</h1>
    <c:choose>
        <c:when test="${permissions.check(['Registradora','InformacionRegistradora'])}">
            <div class="boxed col-lg-11 centered">
                <div class="row">
                    <!--<div class="span10 left-xl-space">    -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active" id="pestana1">

                            <div id="msg" class="form-msg top-space bottom-space ${msgType}" role="alert">${msg}</div>

                            <div class="top-space bottom-space inforeg">> Criterios de b&uacute;squeda</div>

                            <form id="form-consulta-inforeg" action="<c:url value='/consultarInfoReg' />" method="post" class="form-inline inforeg">                                                                    

                                <input class="form-control" style="width: 192px;" type="text" id="fechaInicio" name="fechaInicio" 
                                       value="${parametrosBusqueda.fechaInicio}" placeholder="Fecha inicial" readonly="readonly">                                       

                                <input class="form-control" style="width: 192px;" type="text" id="fechaFinal"  name="fechaFinal" 
                                       value="${parametrosBusqueda.fechaFinal}" placeholder="Fecha final" readonly="readonly">                                       

                                <select id="smovil" name="smovil" class="select-picker form-control" title="Seleccione un veh&iacute;culo"
                                        data-live-search="true" data-actions-box="true" data-selected-text-format="values"
                                        data-width="192" data-max-options="10">
                                    <option value="">Seleccione un veh&iacute;culo</option>
                                    <c:forEach items="${select.lstmovil}" var="movil">
                                        <option value="${movil.id},${movil.placa},${movil.numeroInterno}">${movil.numeroInterno} - ${movil.placa}</option>
                                    </c:forEach>
                                </select>
                                
                                <select id="stdia" name="stdia" class="select-picker form-control" data-width="192">
                                    <option value="0">Ninguno</option>
                                    <option value="1">Hoy</option>
                                    <option value="2">Ayer</option>
                                </select>

                                <!--
                                <input class="form-control" type="number" id="limite" name="limite" min="30" max="512" value="{parametrosBusqueda.limite}" 
                                       placeholder="Límite (512)" style="width: 192px;"> -->

                                <select id="slimite" name="slimite" class="select-picker form-control" data-width="192">
                                    <option value="10">10</option>
                                    <option value="30">30</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                    <option value="512">512</option>
                                </select>

                                <input type="hidden" id="id_movil" name="id_movil">
                                <input type="hidden" id="placa" name="placa">
                                <input type="hidden" id="num_interno" name="num_interno">
                                <input type="hidden" id="autoconsulta" name="autoconsulta" value="${autoconsulta}">

                                <button style="display: none;" type="submit" class="btn"></button>
                                <button type="button" class="btn btn-primary btn-sm" id="btnBuscar"     onclick="sendInfoReg();">Buscar</button>
                                <button type="button" class="btn btn-primary btn-sm" id="btnBuscar30"   onclick="sendInfoReg30();">&Uacute;ltimos 30 registros</button>
                                <button type="button" class="btn btn-primary btn-sm" id="btnExportar"   onclick="sendExportExcel();">Exportar a excel</button>
                                <label class="top-space">
                                    <input type="checkbox" onclick="sendAutoQuery();" id="chk_autoconsulta" name="chk_autoconsulta">&nbsp;Consultar cada 10 segundos</label>

                            </form>

                            <!--ExportarRegistradora-->
                            <c:if test="${permissions.check(95)}">
                                <form id="form-exportar-excel-ir" action="<c:url value='/exportarExcel' />" method="post">
                                    <input type="hidden" id="id_movil_e" name="id_movil_e">
                                    <input type="hidden" id="placa_e" name="placa_e">
                                    <input type="hidden" id="num_interno_e" name="num_interno_e">
                                    <input type="hidden" id="fechaInicio_e" name="fechaInicio_e">
                                    <input type="hidden" id="fechaFinal_e" name="fechaFinal_e">
                                    <input type="hidden" id="slimite_e" name="slimite_e">
                                </form>
                            </c:if>

                            <div class="top-ml-space bottom-space inforeg">> Filtro sobre la b&uacute;squeda</div>

                            <form id="form-filtro-inforeg" class="form-inline inforeg">

                                <select id="sruta" class="normal-h" style="width: 192px;" onchange="inforeg_filtrar();">
                                    <option value="">Ruta</option>
                                    <c:forEach items="${select.lstruta}" var="ruta">
                                        <option value="${ruta.id}">${ruta.id} - ${ruta.nombre}</option>
                                    </c:forEach>
                                </select>

                                N° Vuelta <input class="input64" type="number" id="numvuelta" name="numvuelta" value="0" min="0" max="250" onchange="inforeg_filtrar();">
                                &nbsp;<a href="#" onclick="window.open('/RDW1/app/registradora/cierraVuelta.jsp',
                                                'newwindow',
                                                'width=1024,height=640');
                                        return false;">Cerrar vuelta</a>
                                <!--
                                <button type="button" class="btn btn-primary btn-sm" onclick="inforeg_filtrar();">Filtrar</button> 
                                -->
                            </form>

                            <!-- Tabla informacion registradora -->
                            <div class="table-responsive" style="margin-top: 56px;">
                                <table id="tablaInfoReg" class="display" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>                                        
                                            <th style="display: none;"></th>
                                            <th style="display: none;"></th>
                                            <th style="display: none;"></th>
                                            <th style="display: none;"></th>                                        
                                            <th>Placa</th>
                                            <th>N° interno</th>
                                            <th>Ruta</th>
                                            <th>Fecha</th>
                                            <th>Hora Llegada</th>
                                            <th>N° Vuelta</th>
                                            <th>Numeraci&oacute;n Inicial</th>
                                            <th>Numeraci&oacute;n Final</th>
                                            <th>Diferencia</th>
                                            <th>Entradas</th>
                                            <th>Salidas</th>
                                            <th>Total d&iacute;a</th>
                                            <th>Liquidado</th>
                                                <c:if test="${permissions.check(211)}">
                                                <th></th>
                                                </c:if>
                                        </tr>
                                    </thead>                                    
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${lstinforeg.size() > 0}">
                                                <c:forEach items="${lstinforeg}" var="inforeg">
                                                    <tr>                                                    
                                                        <td style="display: none;">${inforeg.id}</td>
                                                        <td style="display: none;">${inforeg.idBase}</td>          <!-- Base llegada -->
                                                        <td style="display: none;">${inforeg.idBaseSalida}</td>    <!-- Base salida -->
                                                        <td style="display: none;">${inforeg.idRuta}</td>          <!-- Ruta -->                                                                                                                                                                                    
                                                        <td class="cursor">
                                                            <!-- <span id="icon_more" style="display: none;">+</span> -->
                                                            <span>${inforeg.placa}</span>
                                                        </td>
                                                        <td>${inforeg.numeroInterno}</td>
                                                        <td>${inforeg.nombreRuta}</td>
                                                        <td>${inforeg.fechaIngreso}</td>
                                                        <td>${inforeg.horaIngreso}</td>
                                                        <td>${inforeg.numeroVuelta}</td>
                                                        <td>${inforeg.numeracionBS}</td>
                                                        <td>${inforeg.numeroLlegada}</td>
                                                        <td>${inforeg.diferenciaNumero}</td>
                                                        <td>${inforeg.entradas}</td>
                                                        <td>${inforeg.salidas}</td>
                                                        <td>${inforeg.totalDia}</td>
                                                        <td>
                                                            <c:if test="${inforeg.idLiquidacion != 0}">
                                                                <span style="color: #4bb647">Si</span>
                                                            </c:if>
                                                            <c:if test="${inforeg.idLiquidacion == 0}">
                                                                <span style="color: #ff0000">No</span>
                                                                <span class="label label-view">
                                                                    <a href="/RDW1/liquidarVehiculo?id=${inforeg.idVehiculo}">Liquidar</a>
                                                                </span>
                                                            </c:if>
                                                        </td>
                                                        <c:if test="${permissions.check(211)}">
                                                            <td>
                                                                <form id="form-mostrar-rir-${inforeg.id}" class="form-in-table" action="<c:url value='/mostrarInfoReg' />" method="post">
                                                                    <input type="hidden" id="idInforeg" name="idInforeg" value="${inforeg.id}">
                                                                    <input type="hidden" id="placa" name="placa" value="${inforeg.placa}">
                                                                    <input type="hidden" id="numeroInterno" name="numeroInterno" value="${inforeg.numeroInterno}">
                                                                    <input type="hidden" id="nombreBS" name="nombreBS" value="${inforeg.nombreBS}">
                                                                    <input type="hidden" id="nombreBL" name="nombreBL" value="${inforeg.nombreBL}">

                                                                    <input type="hidden" id="p_movil" name="p_movil" value="${parametrosBusqueda.itemMovil}">        
                                                                    <input type="hidden" id="p_numvuelta" name="p_numvuelta" value="${parametrosBusqueda.numeroVuelta}">
                                                                    <input type="hidden" id="p_limite" name="p_limite" value="${parametrosBusqueda.limite}">
                                                                    <input type="hidden" id="p_ruta" name="p_ruta" value="${parametrosBusqueda.idRuta}">

                                                                    <input id="submitMostrarInfo" type="button" onclick="inforeg_mostrarRegistroIR(${inforeg.id});" class="btn btn-success btn-info btn-xs" value="Mostrar">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>                                            
                                        </c:choose>                                    
                                    </tbody>
                                </table>                                
                            </div>

                            <c:if test="${lstinforeg.size() == 0}">
                                <input type="hidden" id="p_movil" name="p_movil" value="${parametrosBusqueda.itemMovil}">        
                                <input type="hidden" id="p_numvuelta" name="p_numvuelta" value="${parametrosBusqueda.numeroVuelta}">
                                <input type="hidden" id="p_limite" name="p_limite" value="${parametrosBusqueda.limite}">
                                <input type="hidden" id="p_ruta" name="p_ruta" value="${parametrosBusqueda.idRuta}">
                                <input type="hidden" id="p_tipodia" name="p_tipodia" value="${parametrosBusqueda.tipoDia}">
                            </c:if>

                            <div style="display: none;">
                                <input type="hidden" id="mostrarInfo" name="mostrarInfo" value="${mostrarInfo}">
                            </div>

                            <div id="dialog" title="Información" style="height: auto !important;">
                                <div id="tabs">
                                    <ul>
                                        <li><a href="#infoGeneral">General</a></li>
                                        <li><a href="#infoSalida">Datos de salida</a></li>
                                        <li><a href="#infoAlarmas">Alarmas</a></li>
                                        <li><a href="#infoRuta">Ruta</a></li>
                                        <li><a href="#infoPerimetro">Per&iacute;metro</a></li>
                                    </ul>
                                    <!-- Informacion general -->
                                    <div id="infoGeneral">                        
                                        <form id="formInfoGeneral" action="<c:url value='/editarInfoGeneral' />" method="post">
                                            <table>
                                                <tr>    <td>ID de registro</td>                 <td>${infoReg.id}</td> </tr>
                                                <tr>    <td colspan="4"><hr></td> </tr>
                                                <tr>    <td>Placa</td>                          <td><input class="input128" id="placa"               name="placa"            type="text"    value="${infoReg.placa}"                readonly required></td> 
                                                        <td>N° Interno</td>                     <td><input class="input128" id="numeroInterno"       name="numeroInterno"    type="text"    value="${infoReg.numeroInterno}"        readonly required></td> </tr>
                                                <tr>    <td>Fecha ingreso</td>                  <td><input class="input128 date_picker" 
                                                                                                           id="fechaIngreso"        name="fechaIngreso"     type="text"    value="${infoReg.fechaIngreso}"         readonly required></td> 
                                                        <td>Hora ingreso</td>                   <td><input class="input128" id="horaIngreso"         name="horaIngreso"      type="time"    value="${infoReg.horaIngreso}"          readonly required min="00:00:00" max="23:59:59" step="1"></td> </tr>                                                                                                 
                                                <tr>    <td>Numeraci&oacute;n inicial</td>      <td><input class="input128" id="numeracionBS"        name="numeracionBS"     type="number"  value="${infoReg.numeracionBS}"         readonly required min="0" max="999999" onchange="inforeg_editarNumeracion();"></td> 
                                                        <td>Numeraci&oacute;n final</td>        <td><input class="input128" id="numeroLlegada"       name="numeroLlegada"    type="number"  value="${infoReg.numeroLlegada}"        readonly required min="0" max="999999" onchange="inforeg_editarNumeracion();"></td> </tr>
                                                <tr>    <td>Diferencia numeraci&oacute;n</td>   <td><input class="input128" id="diferenciaNumero"    name="diferenciaNumero" type="number"  value="${infoReg.diferenciaNumero}"     readonly required min="0" max="999999"></td> </tr>
                                                <tr>    <td>Entradas</td>                       <td><input class="input128" id="entradas"            name="entradas"         type="text"    value="${infoReg.entradas}"             readonly required></td> 
                                                        <td>Salidas</td>                        <td><input class="input128" id="salidas"             name="salidas"          type="text"    value="${infoReg.salidas}"              readonly required></td> </tr>
                                                <tr>    <td>Diferencia entradas</td>            <td><input class="input128" id="diferenciaEntrada"   name="diferenciaEntrada" type="text"   value="${infoReg.diferenciaEntrada}"    readonly required></td> 
                                                        <td>Diferencia salidas</td>             <td><input class="input128" id="diferenciaSalida"    name="diferenciaSalida"  type="text"   value="${infoReg.diferenciaSalida}"     readonly required></td> </tr>                            

                                                <c:if test="${permissions.check(212)}">
                                                    <tr>    
                                                        <td colspan="3"></td>
                                                        <td><div class="top-lt-space" id="edicionInfoGeneral"><input type="button" class="input84" onclick="show_enable('InfoGeneral');" value="Editar"></div></td>
                                                    </tr>
                                                </c:if>
                                            </table>

                                            <c:if test="${permissions.check(212)}">
                                                <div id="controlInfoGeneral" style="display:none;">
                                                    <input type="hidden" id="idInfreg" name="idInfreg" value="${infoReg.id}">                                            
                                                    <table class="top-lt-space"><tbody>
                                                            <tr>   
                                                                <td>Motivo</td>
                                                                <td><textarea id="motivoInfoGeneral" name="motivoEdicion" style="resize: none; width: 256px;"></textarea></td>
                                                            </tr>
                                                            <tr>
                                                                <td>Contrase&ntilde;a de acceso</td>
                                                                <td><input style="width: 256px;" type="password" id="accesoInfoGeneral" name="accesoInfoGeneral"></td>                                                    
                                                            </tr>                                     
                                                            <tr>
                                                                <td></td>
                                                                <td>
                                                                    <div class="top-lt-space">
                                                                        <input type="button" class="input84" onclick="inforeg_editar('InfoGeneral');" value="Guardar">
                                                                        <input type="button" class="input84" onclick="hidden_disable('InfoGeneral');" value="Cancelar">                                                        
                                                                    </div>
                                                                </td>                                                 
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><span id="msgInfoGeneral" class="form-msg top-lt-space"></span></td>
                                                            </tr>
                                                        </tbody></table>
                                                </div>
                                            </c:if>
                                        </form>
                                    </div>
                                    <!-- Informacion de salida -->
                                    <div id="infoSalida">
                                        <form id="formInfoSalida" action="<c:url value='/editarInfoSalida' />" method="post">
                                            <table>
                                                <tr>    <td colspan="4">El registro ingres&oacute; a Base de Datos por</td>    </tr>
                                                <tr>    <td colspan="4"><hr></td>    </tr>
                                                <tr>    <td>Base salida</td>                <td><input class="input128" id="nombreBS" name="nombreBS"           type="text"     value="${infoReg.nombreBS}"         readonly required></td>    
                                                    <td>Base llegada</td>               <td><input class="input128" id="nombreBL" name="nombreBL"           type="text"     value="${infoReg.nombreBL}"         readonly required></td> </tr>
                                                <tr>    <td>Fecha salida</td>               <td><input class="input128 date_picker" 
                                                                                                       id="fechaSalidaBS" name="fechaSalidaBS" type="text"     value="${infoReg.fechaSalidaBS}"    readonly required></td>    
                                                    <td>Hora salida</td>                <td><input class="input128" id="horaSalidaBS" name="horaSalidaBS"   type="time"     value="${infoReg.horaSalidaBS}"     readonly required min="00:00:00" max="23:59:59" step="1"></td> </tr>
                                                <tr>    <td>Numeraci&oacute;n inicial</td>  <td><input class="input128" id="numeracionBS" name="numeracionBS"   type="text"     value="${infoReg.numeracionBS}"     readonly required></td> </tr>
                                                <tr>    <td>Entradas</td>                   <td><input class="input128" id="entradasBS" name="entradasBS"       type="text"     value="${infoReg.entradasBS}"       readonly required></td> 
                                                    <td>Salidas</td>                    <td><input class="input128" id="salidasBS" name="salidasBS"         type="text"     value="${infoReg.salidasBS}"        readonly required></td> </tr>                            

                                                <c:if test="${permissions.check(212)}">
                                                    <tr>    
                                                        <td colspan="3"></td>
                                                        <td><div class="top-lt-space" id="edicionInfoSalida"><input type="button" class="input84" onclick="show_enable('InfoSalida');" value="Editar"></div></td>
                                                    </tr>
                                                </c:if>

                                            </table>
                                            <c:if test="${permissions.check(212)}">
                                                <div id="controlInfoSalida" style="display:none;">
                                                    <input type="hidden" id="idInfreg" name="idInfreg" value="${infoReg.id}">                                            
                                                    <table class="top-lt-space"><tbody>
                                                            <tr>
                                                                <td>Motivo</td>
                                                                <td><textarea id="motivoInfoSalida" style="resize: none; width: 256px;"></textarea></td>
                                                            </tr>
                                                            <tr>
                                                                <td>Contrase&ntilde;a de acceso</td>
                                                                <td><input style="width: 256px;" type="password" id="accesoInfoSalida" name="accesoInfoSalida"></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td>
                                                                    <div class="top-lt-space">
                                                                        <input type="button" class="input84" onclick="inforeg_editar('InfoSalida');" value="Guardar">
                                                                        <input type="button" class="input84" onclick="hidden_disable('InfoSalida');" value="Cancelar">                                                        
                                                                    </div>
                                                                </td>
                                                            </tr>                                                
                                                            <tr>
                                                                <td></td>
                                                                <td><span id="msgInfoSalida" class="form-msg top-lt-space"></span></td>
                                                            </tr>
                                                        </tbody></table>
                                                </div>
                                            </c:if>
                                        </form>
                                    </div>
                                    <!-- Informacion de alarmas -->
                                    <div id="infoAlarmas">
                                        <table id="tablaAlarmas" class="display" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>C&oacute;digo</th>
                                                    <th>Nombre</th>
                                                    <th>Fecha</th>
                                                    <th>Hora</th>
                                                    <th>Cantidad</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${infoReg.lstalarma}" var="alarma">
                                                    <tr>
                                                        <td>${alarma.codigo}</td>
                                                        <td>${alarma.nombre}</td>
                                                        <td>${alarma.fechaAlarma}</td>
                                                        <td>${alarma.horaAlarma}</td>
                                                        <td>${alarma.cantidad}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- Informacion de ruta (Puntos de control) -->
                                    <div id="infoRuta">
                                        <div class="bottom-space">Ruta recorrida:&nbsp; ${infoReg.nombreRuta} (${infoReg.porcentajeRuta} %)
                                            <c:if test="${infoReg.porcentajeRuta == 0}">
                                                <c:if test="${permissions.check(213)}">
                                                    <button type="button" onclick="showDialog('dialog2');">Asignar ruta</button>                                
                                                </c:if>
                                            </c:if>
                                        </div>
                                        <table id="tablaPuntosControl" class="display" cellspacing="0">
                                            <thead>
                                                <tr>                                    
                                                    <th>C&oacute;digo</th>
                                                    <th>Nombre</th>
                                                    <th>Hora</th>
                                                    <th>Fecha</th>
                                                    <th>Numeraci&oacute;n</th>
                                                </tr>
                                            </thead>
                                            <tbody>                                                                
                                                <c:forEach items="${infoReg.lstptocontrol}" var="ptocontrol">
                                                    <tr>
                                                        <td>${ptocontrol.codigo}</td>
                                                        <td>${ptocontrol.nombre}</td>
                                                        <td>${ptocontrol.horaPuntoControl}</td>
                                                        <td>${ptocontrol.fechaPuntoControl}</td>
                                                        <td>${ptocontrol.numeracion}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- Informacion de perimetro -->
                                    <div id="infoPerimetro">
                                        <table id="tablaConteosPerimetro" class="display" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>Fecha</th>
                                                    <th>Hora</th>
                                                    <th>Numeraci&oacute;n inicial</th>
                                                    <th>Numeraci&oacute;n final</th>
                                                    <th>Diferencia</th>
                                                    <th>Entradas</th>
                                                    <th>Salidas</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${infoReg.lstcp}" var="cp">
                                                    <tr>
                                                        <td>${cp.fechaConteo}</td>
                                                        <td>${cp.horaIngreso}</td>
                                                        <td>${cp.numInicial}</td>
                                                        <td>${cp.numFinal}</td>
                                                        <td>${cp.diferencia}</td>
                                                        <td>${cp.entradas}</td>
                                                        <td>${cp.salidas}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <div id="dialog2" title="Asignar ruta">                
                                <form id="formAsignarRuta" action="<c:url value='/asignaRutaIR' />" method="post">
                                    <input type="hidden" id="idInfreg" name="idInfreg" value="${infoReg.id}">
                                    <table><tbody>
                                            <tr>
                                                <td>Ruta</td>
                                                <td>
                                                    <select id="sruta" name="sruta" class="input172">                    
                                                        <c:forEach items="${select.lstruta}" var="ruta">
                                                            <option value="${ruta.id}">${ruta.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Contrase&ntilde;a de acceso</td>                                    
                                                <td><input type="password" id="accesoAsignarRuta" name="accesoAsignarRuta" class="input172"></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="button" class="input84" onclick="inforeg_asignarRuta('AsignarRuta');" value="Asignar ruta">
                                                    <input type="button" class="input84" onclick="hiddenDialog('dialog2');" value="Cancelar">
                                                </td>
                                            </tr>
                                        </tbody></table>
                                </form>
                            </div>                                                            
                        </div>
                    </div>
                </div>
                <input type="hidden" id="data_ir" name="data_ir" value="${data_ir}">
            </div>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script>

    $(document).ready(function () {
        
        // Fecha inicio y final para consulta

        var fechaInicio = $('#fechaInicio').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es',
            todayHighlight: true,
            updateViewDate: true
        }).on('changeDate', inforeg_cambioFecha);

        var fechaFinal = $('#fechaFinal').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es',
            todayHighlight: true,
            updateViewDate: true
        }).on('changeDate', inforeg_cambioFecha);

        // Fecha salida y llegada de vuelta

        var fechaSalida = $('#fechaSalidaBS').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            fechaSalida.datepicker('hide');
        });

        var fechaIngreso = $('#fechaIngreso').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            fechaIngreso.datepicker('hide');
        });        

        $('#smovil').selectpicker({
            size: 8
        });
        
        $('#slimite').selectpicker({
            size: 5
        });
        
        $('#stdia').selectpicker({
            size: 3
        }).on('change', inforeg_cambioDia);

        $('#tablaInfoReg').DataTable({
            responsive: true,
            order: [[7, 8, "desc"]], // fecha, hora
            columnDefs: [{
                    targets: [0, 1, 2, 3],
                    visible: false
                }, {width: "100px", targets: 4}],
            bAutoWidth: true,
            scrollY: 500,
            searching: true,
            bInfo: true,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        $('#tablaAlarmas').DataTable({
            "aLengthMenu": [5, 8],
            "order": [[3, "asc"]],
            searching: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        $('#tablaPuntosControl').DataTable({
            "aLengthMenu": [5, 8],
            "order": [[2, "asc"]],
            searching: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        $('#tablaConteosPerimetro').DataTable({
            "aLengthMenu": [5, 8],
            searching: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        $('#tabs').tabs();

        $('#dialog').dialog({
            autoOpen: false,
            resizable: false,
            height: 512,
            width: 680
        });

        $('#dialog2').dialog({
            autoOpen: false,
            resizable: false
        });

        var mostrarInfo = $('#mostrarInfo').val();
        if (mostrarInfo == "true") {
            $('#dialog').dialog("open");
            $('#dialog').addClass("first-float");
            $('#navbar').addClass("last-float");
        }
        inforeg_inicio();
    });

</script>
<jsp:include page="/include/end.jsp" />
