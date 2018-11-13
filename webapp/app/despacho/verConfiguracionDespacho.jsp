<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<jsp:include page="/include/headerNoMenu.jsp" /> <!-- /include/headerHomeNew_.jsp -->

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configuracion despacho</title>

        <link rel="icon" type="image/png" href="img/favicon.png"/>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
        <meta http-equiv='X-UA-Compatible' content='IE=Edge'>
        <meta name="robots" content="index, follow"/>
        <meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="author" content="">        

        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">
    </head>
    <body>
        <div class="col-lg-7 centered">
            <div class="tab-pane padding-smin">
                <c:choose>
                    <c:when test="${permissions.check(['Despacho'])}">
                        <c:choose>
                            <c:when test="${despacho == null}">
                                No existe configuraci&oacute;n de despacho relacionado.
                            </c:when>
                            <c:otherwise>

                                <h2>Configuraci&oacute;n despacho</h2>
                                <hr>

                                <!-- Informacion general -->
                                <table class="tbl-style">
                                    <tbody>                                        
                                        <tr>
                                            <td class="lbl">Nombre despacho</td>
                                            <td>${despacho.nombreDespacho}</td>

                                            <td class="lbl">Ruta</td>
                                            <td>${despacho.nombreRuta}</td>
                                        </tr>
                                        <tr>
                                            <td class="lbl">Tipo d&iacute;a</td>
                                            <td>${despacho.tipoDia}</td>

                                            <td class="lbl">Rotaci&oacute;n entre veh&iacute;culos</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${despacho.rotarVehiculo == 1}">
                                                        Si
                                                    </c:when>
                                                    <c:otherwise>
                                                        No
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>        
                                        <tr>
                                            <td class="lbl">Hora de inicio</td>
                                            <td>${despacho.horaInicio}</td>

                                            <td class="lbl">Hora fin</td>
                                            <td>${despacho.horaFin}</td>
                                        </tr>
                                        <tr>
                                            <td class="lbl">Intervalo despacho valle (min)</td>
                                            <td>${despacho.intervaloValle}</td>

                                            <td class="lbl">Intervalo despacho pico (min)</td>
                                            <td>${despacho.intervaloPico}</td>
                                        </tr>
                                        <tr>
                                            <td class="lbl">Tiempo de salida (min)</td>
                                            <td>${despacho.tiempoSalida}</td>
                                            <td class="lbl">Tiempo de salida pico (min)</td>
                                            <td>${despacho.tiempoSalidaPico}</td>
                                        </tr>
                                        <tr>
                                            <td class="lbl">Horas de trabajo (hrs)</td>
                                            <td colspan="3">${despacho.horasTrabajo}</td>
                                        </tr>
                                        <tr>
                                            <td class="lbl">Horas pico</td>
                                            <td colspan="3">${despacho.horaPicoFmt}</td>
                                        </tr>
                                    </tbody>
                                </table>

                                <c:if test="${despacho.puntoRetorno != null}">
                                    <!-- Punto retorno -->
                                    <div style="height: 20px;"></div>

                                    <table class="tbl-style">
                                        <tbody>
                                            <tr>
                                                <td colspan="4">> Punto de retorno</td>
                                            </tr>
                                            <tr>
                                                <td class="lbl">Punto de retorno</td>
                                                <td>${despacho.puntoRetorno}</td>
                                                <td class="lbl">Hora de inicio</td>
                                                <td>${despacho.horaInicioRetorno}</td>
                                            </tr>
                                            <tr>
                                                <td class="lbl">Intervalo despacho (min)</td>
                                                <td>${despacho.intervaloRetorno}</td>                                        
                                                <td class="lbl">Tiempo de salida (min)</td>
                                                <td>${despacho.tiempoSalidaRetorno}</td>
                                            <tr>
                                                <td class="lbl">Tiempo de ajuste (1ra vuelta - min)</td>
                                                <td colspan="3">${despacho.tiempoAjusteRetorno}</td>                                            
                                            </tr>                                        
                                        </tbody>
                                    </table>
                                </c:if>
                                <c:if test="${despacho.puntoRetorno == null}">
                                    <div style="margin-top: 10px">* Sin punto retorno</div>
                                </c:if>

                                <div style="height: 20px;"></div>

                                <!-- Puntos de ruta | tiempo valle | tiempo pico -->
                                <table class="tbl-style">
                                    <tr>
                                        <td class="lbl">Puntos de ruta</td>
                                        <td class="lbl">Tiempo holgura (min)</td>
                                        <td class="lbl">Tiempo llegada valle (min)</td>
                                        <td class="lbl">Tiempo llegada pico (min)</td>
                                    </tr>
                                    <c:forEach items="${despacho.listaPuntosRuta}" var="puntoRuta">
                                        <tr>
                                            <c:forEach items="${puntoRuta}" var="celda">
                                                <td>${celda}</td>
                                            </c:forEach>                        
                                        </tr>
                                    </c:forEach>
                                </table>

                                <div style="height: 20px;"></div>

                                <!-- Vehiculos -->
                                <table class="tbl-style" width="276px"> 
                                    <c:choose>                                        
                                        <c:when test="${despacho.programarRuta == 1}">
                                            <tr>
                                                <td colspan="2" class="lbl" style="text-align: center;">Veh&iacute;culos</td>
                                            </tr>                                            
                                            <c:choose>
                                                <c:when test="${pgr == null}">                                                    
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td><strong>Nombre programaci&oacute;n</strong></td>
                                                        <td><i>${pgr.nombreProgramacion}</i></td>
                                                    </tr>
                                                    <tr>
                                                        <td><strong>N° de veh&iacute;culos en retorno</strong></td>
                                                        <td><i>${despacho.cantidadMovilesRetorno}</i></td>
                                                    </tr>            
                                                    <tr>
                                                        <td><strong>Tipo de programacion</td>
                                                        <td><i>
                                                            <c:choose>
                                                                <c:when test="${despacho.tipoProgramacionRuta == 'S'}">
                                                                    Semanal
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Mensual
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </i></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="lbl" style="text-align: center;">D&iacute;a</td>
                                                        <td class="lbl" style="text-align: center;">Grupo</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Lunes</td><td>${pgr.ngrupo_lun}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Martes</td><td>${pgr.ngrupo_mar}</td>                                                    
                                                    </tr>
                                                    <tr>
                                                        <td>Miercoles</td><td>${pgr.ngrupo_mie}</td>                                                    
                                                    </tr>
                                                    <tr>
                                                        <td>Jueves</td><td>${pgr.ngrupo_jue}</td>                                                    
                                                    </tr>
                                                    <tr>
                                                        <td>Viernes</td><td>${pgr.ngrupo_vie}</td>                                                    
                                                    </tr>
                                                    <tr>
                                                        <td>Sabado</td><td>${pgr.ngrupo_sab}</td>                                                    
                                                    </tr>
                                                    <tr>
                                                        <td>Domingo</td><td>${pgr.ngrupo_dom}</td>                                                    
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr> 
                                                <td colspan="2">
                                                    <span class="form-msg-sm">
                                                        * Veh&iacute;culos asignados por <a href="/RDW1/app/despacho/programacionRuta.jsp" target="_blank">Programaci&oacute;n de ruta.</a>
                                                    </span>
                                                    <br>
                                                    <span class="form-msg-sm">
                                                        * Asignaci&oacute;n de grupos puede variar seg&uacute;n el tipo de programaci&oacute;n establecido (semanal/mensual).
                                                    </span>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="3" style="text-align: center;">Veh&iacute;culos</td>
                                            </tr>
                                            <tr>
                                                <td class="lbl">N° Interno</td>
                                                <td class="lbl">Placa</td>
                                                <td class="lbl">Inicia en punto retorno</td>
                                            </tr>
                                            <c:choose>
                                                <c:when test="${despacho.listaGrupoMoviles_movil == null}">
                                                    <tr><td colspan="3">* Sin veh&iacute;culos asignados</td></tr>
                                                </c:when>
                                                <c:when test="${fn:length(despacho.listaGrupoMoviles_movil) == 0}">
                                                    <tr><td colspan="3">* Sin veh&iacute;culos asignados</td></tr>
                                                </c:when>                                                
                                                <c:otherwise>
                                                    <c:forEach items="${despacho.listaGrupoMoviles_movil}" var="movil">
                                                        <tr>
                                                            <td>${movil.numeroInterno}</td>
                                                            <td>${movil.placa}</td>
                                                            <c:if test="${movil.iniciaEnPuntoRetorno}">
                                                                <td style="text-align: center;">Si</td>
                                                            </c:if>
                                                            <c:if test="${!movil.iniciaEnPuntoRetorno}">
                                                                <td style="text-align: center;">-</td>
                                                            </c:if>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>                                    
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="/include/accesoDenegado.jsp" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
