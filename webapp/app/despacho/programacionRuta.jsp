
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <section class="boxed padding-smin">

        <c:choose>
            <c:when test="${permissions.check(['Despacho','EstadoDespacho'])}">

                <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="#">Programaci&oacute;n Ruta</a></li>
                    <c:if test="${permissions.check(['Despacho','RegistrarDespachos'])}">
                        <li><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Configuraci&oacute;n</a></li>
                    </c:if>
                    <c:if test="${permissions.check(['Despacho','EstadoDespacho'])}">
                        <li><a href="/RDW1/app/despacho/estadoDespacho.jsp">Estado</a></li>
                    </c:if>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active">

                        <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                

                        <div class="row">
                            <div class="control-group">   
                                <label class="col-sm-1">Nombre</label>
                                <div class="col-sm-3">                                        
                                    <input type="text" id="nombre" name="nombre" 
                                           class="form-control" maxlength="45" placeholder="Nombre de la programación">
                                </div>
                                <div class="col-sm-2">
                                    <button style="width: 70px;" onclick="dph_nuevaProgramacionRuta();">Nueva</button>
                                    <button style="width: 70px;" onclick="dph_cancelaProgramacionRuta();">Cancelar</button>
                                </div>
                                <div class="col-sm-3">                            
                                    <select id="spgruta" name="spgruta" class="form-control">
                                        <option value="">Seleccione una programación</option>
                                        <c:forEach items="${select.lst_pgruta_nombre}" var="pgruta">
                                            <option value="${pgruta.id}">${pgruta.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-3" style="margin-top: 3px;">                                    
                                    <button onclick="dph_activarProgramacionRuta();">Activar</button>                                    
                                    <button onclick="dph_eliminarProgramacionRuta();">Eliminar</button>
                                    <!-- <button onclick="dph_inactivarProgramacionRuta();" style="margin-left: 6px;">Nueva</button> -->
                                </div>                        
                            </div>
                        </div>

                        <div id="div_pgr_activa" class="row" style="margin-top: 25px;">
                            <div class="control-group" style="margin-left: 12px;">                                
                                &nbsp;<label>> Programaci&oacute;n activa:</label>
                                &nbsp;<span style="background-color: yellow">${pgruta_activa}</span>
                            </div>                            
                            <input type="hidden" id="pgruta_activa" name="pgruta_activa" value="${pgruta_activa}">
                            <input type="hidden" id="pgruta_activa_id" name="pgruta_activa_id" value="${pgruta_activa_id}">
                        </div>

                        <c:choose>                            
                            <c:when test="${select.lst_pgruta != null && select.lst_pgruta.size() > 0}">

                                <table id="tablaProgramacionRuta" class="display" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Ruta</th>
                                            <th>Lunes</th>
                                            <th>Martes</th>
                                            <th>Mi&eacute;rcoles</th>
                                            <th>Jueves</th>
                                            <th>Viernes</th>
                                            <th>S&aacute;bado</th>
                                            <th>Domingo</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${select.lst_pgruta}" var="pgruta">
                                            <tr>
                                                <td>${pgruta.idRuta}</td>
                                                <td><strong>${pgruta.nombreRuta}</strong></td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_lun}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_mar}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_mie}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_jue}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_vie}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_sab}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input type="hidden" value="${pgruta.grupo_dom}">
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table> 
                            </c:when>  
                            
                            <c:otherwise>
                                <table id="tablaProgramacionRuta" class="display" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Ruta</th>
                                            <th>Lunes</th>
                                            <th>Martes</th>
                                            <th>Mi&eacute;rcoles</th>
                                            <th>Jueves</th>
                                            <th>Viernes</th>
                                            <th>S&aacute;bado</th>
                                            <th>Domingo</th>
                                        </tr>
                                    </thead>
                                    <tbody>                        
                                        <c:forEach items="${select.lstruta}" var="ruta">
                                            <tr>
                                                <td>${ruta.id}</td>
                                                <!-- <td><label><input type="checkbox" id="chk_ruta" name="chk_ruta" checked="checked">&nbsp;\${ruta.nombre}</label></td> -->
                                                <td><strong>${ruta.nombre}</strong></td>
                                                <td> <!-- Lun -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td> <!-- Mar -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td> <!-- Mie -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td> <!-- Jue -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td> <!-- Vie -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td> <!-- Sab -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td> <!-- Dom -->
                                                    <select class="normal-w">
                                                        <option value="">Selec. grupo</option>
                                                        <c:forEach items="${select.lstgempresa}" var="grupoempresa">
                                                            <option value="${grupoempresa.id}">${grupoempresa.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                        
                            </c:otherwise>                                
                            
                        </c:choose>                        

                        <div style="padding-top: 16px;">                    
                            <button onclick="dph_rotarGruposEnSemana();">Rotar grupos</button>
                            <button onclick="dph_conservarGruposEnSemana();" style="margin-left: 6px;">Conservar grupos</button>                            
                            <button id="btn_guarda_pgr" onclick="dph_programarRuta();" style="width: 96px; margin-left: 6px;">Guardar</button>
                            <button id="btn_actualiza_pgr" onclick="dph_actualizarProgramacionRuta();" style="width: 96px; margin-left: 6px;">Actualizar</button>                            

                            <select id="smes" name="smes" style="margin-left: 96px;">
                                <option value="">Seleccione mes</option>
                                <option value="0">Enero</option>
                                <option value="1">Febrero</option>
                                <option value="2">Marzo</option>
                                <option value="3">Abril</option>
                                <option value="4">Mayo</option>
                                <option value="5">Junio</option>
                                <option value="6">Julio</option>
                                <option value="7">Agosto</option>
                                <option value="8">Septiembre</option>
                                <option value="9">Octubre</option>
                                <option value="10">Noviembre</option>
                                <option value="11">Diciembre</option>
                            </select>
                            <select id="stipo" name="stipo" style="margin-left: 6px;">
                                <option value="1">Mensual</option>
                                <option value="2">Semanal</option>
                            </select>
                            <button onclick="dph_verRodamiento();" style="margin-left:6px;">Ver rodamiento</button>
                            <span id="msg-rodamiento" class="form-msg" style="margin-left: 8px;"></span>
                        </div>

                        <form id="form-nueva-programacion-ruta" action="<c:url value='/creaProgramacionRuta' />" method="post">
                            <input type="hidden" id="nombreProgramacion" name="nombreProgramacion">
                            <input type="hidden" id="programacionRuta" name="programacionRuta">
                        </form>
                        <form id="form-actualiza-programacion-ruta" action="<c:url value='/actualizaProgramacionRuta' />" method="post">
                            <input type="hidden" id="idProgramacionRuta_upd" name="idProgramacionRuta_upd">
                            <input type="hidden" id="nombreProgramacion_upd" name="nombreProgramacion_upd">
                            <input type="hidden" id="programacionRuta_upd" name="programacionRuta_upd">
                        </form>
                        <form id="form-activa-programacion-ruta" action="<c:url value='/activaProgramacionRuta' />" method="post">
                            <input type="hidden" id="idProgramacionRuta_act" name="idProgramacionRuta_act">
                        </form>
                        <form id="form-elimina-programacion-ruta" action="<c:url value='/eliminaProgramacionRuta' />" method="post">
                            <input type="hidden" id="idProgramacionRuta_des" name="idProgramacionRuta_des">
                        </form>
                        <form id="form-desactiva-programacion-ruta" action="<c:url value='/inactivaProgramacionRuta' />" method="post"></form>

                        <!-- Tabla detalle de programacion ruta -->
                        
                        <table id="tablaProgramacionRuta_completa" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Ruta</th>
                                    <th>D&iacute;a</th>
                                    <th>
                                        M&oacute;viles
                                        <label style="padding-left: 10px;">
                                            <input id="chk_mostrarplaca" type="checkbox" onclick="dph_mostrarPlaca();"> Mostrar placa</label>
                                    </th>                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${select.lst_pgruta_completa}" var="pgruta">
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>Lunes</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_lun_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_lun_placa}</div>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>Martes</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_mar_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_mar_placa}</div>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>Mi&eacute;rcoles</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_mie_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_mie_placa}</div>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>Jueves</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_jue_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_jue_placa}</div>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>Viernes</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_vie_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_vie_placa}</div>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>S&aacute;bado</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_sab_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_sab_placa}</div>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td>${pgruta.idRuta}</td>
                                        <td>${pgruta.nombreRuta}</td>
                                        <td>Domingo</td>
                                        <td>
                                            <div id="lst_numinterno" name="lst_numinterno">${pgruta.grupo_dom_numinterno}</div>
                                            <div id="lst_placa" name="lst_placa" class="no-display">${pgruta.grupo_dom_placa}</div>
                                        </td>                                        
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>                    
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

        $('#tablaProgramacionRuta').DataTable({
            "bSort": false,
            "paging": false,
            "info": false,
            "responsive": true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        $('#tablaProgramacionRuta_completa').DataTable({
            "bSort": false,
            "paging": false,
            "info": false,
            "responsive": true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });

        //dph_cargarProgramacionRuta();
        dph_verProgramacionActiva(true);
    });
</script>
<jsp:include page="/include/end.jsp" />
