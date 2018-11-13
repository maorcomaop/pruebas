
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<!-- 
    Modulo despacho - 0.1
    + Generacion de planilla con vueltas iniciando en base intermedia
    + Agrupacion de vehiculos que inician en base intermedia
    + Control de despacho para bases intermedia
-->

<div class="col-lg-9 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <section class="boxed padding-smin">
        <c:choose>
            <c:when test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="#">Nuevo Despacho</a></li>
                    <c:if test="${permissions.check(['Despacho','ListadoDespachos'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/consultaDespacho.jsp">Despachos</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','GeneracionDePlanilla'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaDespacho.jsp">Generaci&oacute;n de Planilla</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/generaPlanillaAPedido.jsp">Generaci&oacute;n de Planilla a Pedido</a></li>
                        <li role="presentation"><a href="/RDW1/app/despacho/sustituyeMovil.jsp">Sustituir Veh&iacute;culo</a></li>
                    </c:if>                        
                    <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                        <li role="presentation"><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','ControlDespacho'])}">
                        <li><a href="/RDW1/app/despacho/verControlDespacho.jsp">Control</a></li>
                    </c:if>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">

                        <form id="form-nuevo-despacho" name="form-nuevo-despacho" role="form" 
                              action="<c:url value='/crearConfDespacho' />" class="form-horizontal" method="post">

                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            
                            <div class="row">
                                <div class="control-group">
                                    <label class="col-sm-2">* Nombre del despacho</label>
                                    <div class="col-sm-4">
                                        <input type="text" name="nombreDespacho" id="nombreDespacho" maxlength="50" class="form-control">
                                    </div>
                                </div>
                            </div>

                            <div class="row top-space">
                                <div class="control-group">
                                    <label class="col-sm-2">* Ruta</label>
                                    <div class="col-sm-4">
                                        <select name="sruta" id="sruta" class="form-control">
                                            <option value="">Seleccione ruta</option>
                                            <c:forEach items="${select.lstruta}" var="ruta">
                                                <option value="${ruta.id}">${ruta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="col-sm-2">
                                        <label>* Aplicar los d&iacute;as</label>
                                        <div class="left-space"><a href="/RDW1/app/despacho/diaFestivo.jsp" class="form-msg-sm">Registrar d&iacute;as festivo</a></div>
                                    </div>
                                    <div class="col-sm-4">
                                        <select name="stipodia" id="stipodia" class="form-control" onchange="dph_verificaTipoDia();">                                
                                            <option value="">Seleccione rango de d&iacute;as</option>                                                                
                                            <option value="LD">Lunes - Domingo</option>
                                            <option value="LS">Lunes - S&aacute;bado</option>
                                            <option value="LV">Lunes - Viernes</option>                                
                                            <option value="F">Festivo</option>
                                            <option value="D">Domingo</option>
                                            <option value="S">S&aacute;bado</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row top-space">                        
                                <div class="control-group">
                                    <label class="col-sm-2">* Hora de inicio</label>                        
                                    <div class="col-sm-4">
                                        <input name="horaInicio" id="horaInicio" type="text" class="form_datetime form-control" readonly="readonly" />
                                    </div>
                                </div>                        
                                <div class="control-group">
                                    <label class="col-sm-2">* Hora fin</label>
                                    <div class="col-sm-4">
                                        <input name="horaFin" id="horaFin" type="text" class="form_datetime form-control" readonly="readonly" />
                                    </div>
                                </div>
                            </div>
                            
                            <hr>
                            
                            <div class="row top-space">
                                <div class="control-group">
                                    <label class="col-sm-1">Hora pico desde</label>
                                    <div class="col-sm-2">
                                        <input name="horaPicoInicio" id="horaPicoInicio" type="text" class="form_datetime form-control" readonly="readonly" />
                                    </div>
                                    <label class="col-sm-1">Hasta</label>
                                    <div class="col-sm-2">
                                        <input name="horaPicoFin" id="horaPicoFin" type="text" class="form_datetime form-control" readonly="readonly" />
                                    </div>
                                </div>
                                <div class="control-group">                                  
                                    <div class="col-sm-6">
                                        <textarea name="listaHoraPico" id="listaHoraPico" style="resize:none;" class="form-control" readonly="readonly"></textarea>                            
                                    </div>
                                </div>                        
                            </div>
                            
                            <div class="row">                        
                                <div class="control-group">
                                    <div class="col-sm-6">
                                        <input type="button" onclick="dph_borrarHorasPico();" style="width:80px; float:right;" value="Borrar">                            
                                        <input type="button" onclick="dph_agregarHoraPico();" style="width:80px; float:right;" value="Agregar">                                
                                    </div>
                                </div>
                            </div>
                            
                            <hr>

                            <div class="row top-space">
                                <div class="control-group">
                                    <label class="col-sm-2">* Intervalo despacho en hora valle (min)</label>
                                    <div class="col-sm-4">
                                        <input name="intervaloValle" id="intervaloValle" type="number" min="1" max="480" class="form-control">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-2">* Intervalo despacho en hora pico (min)</label>
                                    <div class="col-sm-4">
                                        <input name="intervaloPico" id="intervaloPico" type="number" min="1" max="480" class="form-control">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="control-group">
                                    <label class="col-sm-2">* Tiempo de espera en base (min)</label>                            
                                    <div class="col-sm-4">
                                        <input type="number" id="tiempoSalida" name="tiempoSalida" min="0" max="180" class="form-control">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="col-sm-2">* Horas de trabajo permitidas</label>
                                    <div class="col-sm-4">
                                        <input type="number" id="horasTrabajo" name="horasTrabajo" min="1" max="24" class="form-control">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="control-group">
                                    <label class="col-sm-2">* L&iacute;mite de conservaci&oacute;n de rotaci&oacute;n (Cantidad d&iacute;as)</label>
                                    <div class="col-sm-4">
                                        <input id="limiteConservacion" name="limiteConservacion" type="number" min="1" max="90" class="form-control" />
                                    </div>
                                </div>
                            </div>   
                            
                            <hr>

                            <div class="row top-space">
                                <div class="control-group">                                                        
                                    <div class="col-sm-6">
                                        <label class="top-space">
                                            <input name="rotarVehiculo" id="rotarVehiculo" type="checkbox">&nbsp;Rotaci&oacute;n lineal de veh&iacute;culos</label>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <input type="button" id="btn-guardar-dph" name="btn-guardar-dph" 
                                           style="width: 115px; margin-left: 15px;" 
                                           onclick="dph_crearConfiguracion();" class="btn" value="Guardar">
                                </div>
                            </div>

                            <input type="hidden" name="idPuntosRuta" id="idPuntosRuta" />
                            <input type="hidden" name="puntosRuta" id="puntosRuta" />                    
                            <input type="hidden" name="tiempoReferencia" id="tiempoReferencia" />
                            <input type="hidden" name="tiempoHolgura" id="tiempoHolgura" />
                            <input type="hidden" name="tiempoLlegadaValle" id="tiempoLlegadaValle" />
                            <input type="hidden" name="tiempoLlegadaPico" id="tiempoLlegadaPico" />
                            <input type="hidden" name="grupoMoviles" id="grupoMoviles" />
                            <input type="hidden" name="vrotarVehiculo" id="vrotarVehiculo" />
                            <input type="hidden" name="vprogramarRuta" id="vprogramarRuta" />
                            <input type="hidden" name="tipoEvento" id="tipoEvento" />              
                            <input type="hidden" name="grupoMoviles_rt" id="grupoMoviles_rt" />               

                            <hr>

                            <!-- Puntos de control para despacho -->
                            <div class="row top-ml-space">
                                <div id="lbl-ruta" class="lbl-showhide control-group col-sm-12"><strong>> Puntos de control *</strong></div>
                                <div id="dph-ruta" class="control-group col-sm-12" style="display: none;">
                                    <table id="tablaPuntosRutaDph" class="display" cellspacing="0" width="100%"
                                           style="display: none;">
                                        <thead>
                                            <tr>
                                                <th class="col-hidden-2"></th>
                                                <th class="col-hidden-2"></th>
                                                <th>C&oacute;digo punto</th>
                                                <th>Nombre punto</th>
                                                <th class="text-align-center">Punto de retorno</th>
                                                <th class="col-hidden-2">Tiempo establecido (min)</th>
                                                <th class="text-align-center">Tiempo de holgura (min)</th>
                                                <th class="text-align-center">Tiempo de llegada valle (min)</th>
                                                <th class="text-align-center">Tiempo de llegada pico (min)</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${select.lstpuntoruta_despacho}" var="puntoruta">
                                                <tr>
                                                    <td class="col-hidden-2">${puntoruta.idRuta}</td>
                                                    <td class="col-hidden-2">${puntoruta.idPunto}</td>                                                                                                
                                                    
                                                    <c:choose>
                                                        <c:when test="${(puntoruta.idPunto == 101)}">
                                                            <td>p${puntoruta.codigoPunto+1}</td>                                                            
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>${puntoruta.etiquetaPunto}</td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    
                                                    <td>
                                                        <label><input type="checkbox">&nbsp;${puntoruta.nombrePunto}</label>
                                                    </td>
                                                    <td class="text-align-center">
                                                        <input type="button" value="&#8617;" onclick="dph_establecerPuntoRetorno(${puntoruta.codigoPunto});">                                                        
                                                    </td>
                                                    <td class="col-hidden-2">${puntoruta.promedioMinutos}</td>
                                                    <td class="text-align-center"><input type="number" min="0" max="5760" value="${puntoruta.holguraMinutos}"></td>
                                                    <td class="text-align-center"><input type="number" min="0" max="5760" value="${puntoruta.promedioMinutos}"></td>
                                                    <td class="text-align-center"><input type="number" min="0" max="5760" value="${puntoruta.promedioMinutos}"></td>                                            
                                                </tr>
                                            </c:forEach>                                                                                
                                        </tbody>
                                    </table>                                                        
                                    <input style="margin-top: 8px;" type="button" value="Filtrar puntos" onclick="dph_filtrarPuntos();">                            
                                    <!--
                                    <input style="margin-top: 8px;" type="button" value="Usar tiempos" onclick="dph_usarTiempoEstablecido();">
                                    <label><input style="margin-top: 8px; margin-left: 4px;" type="checkbox" id="usarEnHoraPico">&nbsp;Usar tiempos en hora pico</label>
                                    -->
                                </div>
                            </div>
                            
                            <hr>   
                            
                            <!-- Punto retorno -->
                            <div class="row top-ml-space">
                                <div id="lbl-puntoretorno" class="lbl-showhide control-group col-sm-12"><strong>> Punto de retorno</strong></div>
                                <div id="dph-puntoretorno" class="control-group col-sm-12" style="display: none;">
                                    
                                    <div class="row top-space">
                                        <div class="control-group">
                                            <label class="col-sm-2">Punto de retorno</label>
                                            <div class="col-sm-4">
                                                <select id="spuntoretorno" name="spuntoretorno" class="form-control">
                                                    <option value="">Sin punto de retorno</option>
                                                    <c:forEach items="${select.lstpuntobase}" var="puntobase">
                                                        <option value="${puntobase.idpunto},${puntobase.etiquetaPunto}">
                                                            ${puntobase.nombre}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="col-sm-2">Hora de inicio</label>
                                            <div class="col-sm-4">
                                                <input name="horaInicio_rt" id="horaInicio_rt" type="text" class="form_datetime form-control" readonly="readonly" />
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row top-space">
                                        <div class="control-group">
                                            <label class="col-sm-2">Intervalo despacho (min)</label>
                                            <div class="col-sm-4">
                                                <input name="intervaloDespacho_rt" id="intervaloDespacho_rt" type="number" min="1" max="480" class="form-control">
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="col-sm-2">Tiempo de salida (min)</label>
                                            <div class="col-sm-4">
                                                <input type="number" id="tiempoSalida_rt" name="tiempoSalida_rt" min="0" max="180" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row top-space">
                                        <div class="control-group">
                                            <label class="col-sm-2">Tiempo de ajuste (1ra vuelta - min)</label>
                                            <div class="col-sm-4">
                                                <input type="number" id="tiempoAjuste_rt" name="tiempoAjuste_rt" min="0" max="180" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!--
                                    <div class="row top-space">
                                        <div class="control-group">
                                            <label class="col-sm-2">Cantidad de veh&iacute;los</label>
                                            <div class="col-sm-4">
                                                <input type="number" id="cantidadVehiculos_rt" name="cantidadVehiculos_rt" min="0" max="160" value="1" class="form-control">
                                            </div>
                                        </div>
                                    </div> -->
                                    
                                </div>
                            </div>
                            
                            <hr>

                            <!-- Vehiculos -->
                            <div class="row top-ml-space">
                                <div id="lbl-grupomovil" class="lbl-showhide control-group col-sm-12"><strong>> Veh&iacute;culos *</strong></div>
                                <div id="dph-grupomovil" class="control-group col-sm-4 top-ml-space" style="display: none;">                            
                                    
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <label><input id="sinMoviles" name="sinMoviles" onclick="dph_ajustarAsignacionMoviles();"
                                                                  type="checkbox">&nbsp;Sin asignaci&oacute;n de veh&iacute;culos</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span id="enlace-planilla" class="form-msg-sm top-space bottom-space">
                                                        * Asignaci&oacute;n debe ser realizada manualmente en 
                                                        generaci&oacute;n de planilla a pedido.</span>
                                                </td>                                            
                                            </tr>
                                        </tbody>
                                    </table>
                                    
                                    <hr>
                                    
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <label><input type="checkbox" id="usarProgRuta" name="usarProgRuta" onclick="dph_suspenderElementoMovil(true);">
                                                        &nbsp;Asignar grupo en programaci&oacute;n de ruta</label>
                                                </td>
                                                <td style="text-align: right;">&nbsp;
                                                    <select id="stipopg" name="stipopg">
                                                        <option value="M">Mensual</option>
                                                        <option value="S">Semanal</option>
                                                    </select>                                    
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>                                    
                                    
                                    <div id="enlace-pgruta" name="enlace-pgruta">
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td>N&uacute;mero de veh&iacute;culos en punto retorno</td>
                                                    <td style="text-align: right;">&nbsp;<input id="cantidadMoviles_rt" name="cantidadMoviles_rt" 
                                                                                                type="number" min="0" max="4096"></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">&nbsp;
                                                        <span class="form-msg-sm">
                                                            * Realice la programaci&oacute;n de ruta 
                                                            <a target="_blank" href="/RDW1/app/despacho/programacionRuta.jsp">aqu&iacute;.</a>
                                                        </span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>                                                  
                                    </div>
                                    
                                    <hr>
                                    
                                    <table width="100%">
                                        <tr>
                                            <td>
                                                <label>Asignar veh&iacute;culo(s) directamente</label>
                                            </td>
                                        </tr>
                                    </table>
                                    
                                    <select name="sgrupomovil" id="sgrupomovil" class="form-control" style="margin-top: 12px;">                                
                                        <option value="">Seleccione un grupo</option>
                                        <c:forEach items="${select.lstgrupomoviles}" var="grupomoviles">
                                            <option value="${grupomoviles.id}">${grupomoviles.nombreGrupo}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row top-ml-space">
                                <div id="dph-tabla-grupomovil" class="control-group col-sm-5" style="display: none;">
                                    <input style="margin-top: 8px;" type="button" value="Filtrar grupo" onclick="dph_filtrarGrupo();">
                                    <input style="margin-top: 8px;" type="button" value="Seleccionar todo" id="marcarGrupo" onclick="dph_marcarGrupo(true);">
                                    <input style="margin-top: 8px; display: none;" type="button" value="Deseleccionar todo" id="desmarcarGrupo" onclick="dph_marcarGrupo(false);">                                    
                                    <table id="tablaGrupoMovilesDph" class="display" cellspacing="0" width="100%" style="display: none;">
                                        <thead>
                                            <tr>
                                                <th class="col-hidden-2"></th>
                                                <th>N&uacute;mero interno</th>
                                                <th>Placa</th>
                                                <th>Inicia en punto retorno</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${select.lstgrupomoviles_despacho}" var="grupomovil">
                                                <tr id="${grupomovil.idMovil}">
                                                    <td class="col-hidden-2">${grupomovil.idGrupoMovil}</td>
                                                    <td>
                                                        <label><input type="checkbox" onclick="dph_restaurarFiltro('tablaGrupoMovilesDph', ${grupomovil.idMovil});">
                                                                      &nbsp;${grupomovil.numeroInterno}</label>
                                                    </td>
                                                    <td>${grupomovil.placa}</td>                                            
                                                    <td>
                                                        <label><input type="checkbox">&nbsp;Inicia en punto retorno</label>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>                                                                
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

<jsp:include page="/include/footerHome_.jsp" />
<script>

    $(document).ready(function () {

        $('#horaInicio').datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            startView: 1,
            minuteStep: 1,
            language: 'es'
        });
        
        $('#horaInicio_rt').datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            startView: 1,
            minuteStep: 1,
            language: 'es'
        });

        $('#horaFin').datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            startView: 1,
            minuteStep: 1,
            language: 'es'            
        });

        $('#horaPicoInicio').datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            startView: 1,
            language: 'es'
        });

        $('#horaPicoFin').datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            startView: 1,
            language: 'es'
        });

        $('#tablaPuntosRutaDph').DataTable({            
            "bSort": false,
            "paging": false,
            "info": false,
            "sDom": '<"top">rt<"bottom"><"clear">',
            responsive: true
        });

        $('#tablaGrupoMovilesDph').DataTable({            
            "bSort": false,
            "paging": false,
            "info": false,
            "scrollY": "720px",
            "scrollCollapse": true,
            "sDom": '<"top"f>rt<"bottom"><"clear">',
            responsive: true
        });
        
        /*
        $(window).bind('resize', function() {
            tbl.fnAdjustColumnSizing();
        }); */       
        
        $('#sruta').change(dph_mostrarRuta);
        $('#sgrupomovil').change(dph_mostrarGrupoMovil);
        $('#spuntoretorno').change(dph_mostrarPuntoRetorno);
        
        $('#tablaGrupoMovilesDph_filter').keyup(dph_conservarOrdenSeleccion);

        $('#lbl-ruta').click(dph_mostrarBloqueRuta);
        $('#lbl-puntoretorno').click(dph_mostrarBloquePuntoRetorno);
        $('#lbl-grupomovil').click(dph_mostrarBloqueGrupoMovil);

        // Funcion de ayuda que mantiene el tamaño de la fila cuando se ordena
        // manualmente
        var fixHelperModified = function (e, tr) {
            var $originals = tr.children();
            var $helper = tr.clone();
            $helper.children().each(function (index)
            {
                $(this).width($originals.eq(index).width())
            });
            return $helper;
        };

        // Hace la tabla ordenable manualmente        
        var tbl_grupoMoviles = "#tablaGrupoMovilesDph";

        $(tbl_grupoMoviles + " tbody").sortable({
            helper: fixHelperModified,
            stop: function (event, ui) {
                renumber_table(tbl_grupoMoviles)
            }
        }).disableSelection();
    });

</script>
<jsp:include page="/include/end.jsp" />


