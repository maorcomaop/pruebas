<!--PAGINA QUE SE IMPRIME CUANDO SE VA A LIQUIDAR UNA VEHICULO-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="col-lg-10 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO LIQUIDACI&Oacute;N</h1>

    <section class="boxed padding">
        <c:choose>
            <c:when test="${permissions.check(['Liquidacion','LiquidarVehiculo'])}">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active" ><a href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">Liquidar Veh&iacute;culo</a></li>
                        <c:if test="${permissions.check(['Liquidacion','ConsultarLiquidacion'])}">
                        <li role="presentation" ><a href="/RDW1/app/liquidacion/consultarLiquidacion.jsp">Consulta de Liquidaci&oacute;n</a></li>
                        </c:if>
                </ul>
                <c:choose>
                    <c:when test ="${idInfo == 0}">                                    
                        <div class="alert alert-info">
                            <button type="button" class="close fade" data-dismiss="alert">&times;</button>
                            <strong>Información </strong>${msg}.
                        </div>
                    </c:when>        
                    <c:when test ="${idInfo == 1}">                                    
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Información </strong>${msg}.
                        </div>
                    </c:when>
                    <c:when test ="${idInfo == 2}">                                    
                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Información </strong>${msg}.
                        </div>
                    </c:when>       
                </c:choose>      
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding active" id="pestana1"> 

                        <form class="form-inline" action="#" method="post">
                            <input type="hidden" name="id_empresa" id="id_emp" value="${login.idempresa}">
                            <input type="hidden" name="veh_inforeg" id="veh_inforeg" value="${veh_inforeg}">
                            <input type="hidden" name="simboloMoneda" id="simboloMoneda" value="${emp.simboloMoneda}">

                            <div class="row">    
                                <div class="col-sm-12 col-xs-12">
                                    <div class="control-group">
                                        <div>
                                            <label>Configuración de etiquetas: <c:choose>
                                                    <c:when test="${configuracion_etiquetas_sesion ne null}">${configuracion_etiquetas_sesion.nombreConfiguracionLiquidacion}.</c:when>
                                                    <c:otherwise>no se ha configurado para este perfil.</c:otherwise>
                                                </c:choose> </label>
                                        </div>
                                        <div>
                                            <label>Tipo de liquidación: <c:choose>
                                                    <c:when test="${configuracion_etiquetas_sesion.liquidacionPorRuta eq true}">liquidación por ruta.</c:when>
                                                    <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTramo eq true}">liquidación por tramo.</c:when>
                                                    <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">liquidación por tiempo.</c:when>
                                                    <c:when test="${configuracion_etiquetas_sesion.liquidacionSoloPasajeros eq true}">liquidación sólo pasajeros.</c:when>
                                                    <c:when test="${configuracion_etiquetas_sesion.liquidacionNormal eq true}">liquidación normal.</c:when>
                                                    <c:otherwise>no se ha configurado el tipo de liquidación.</c:otherwise>
                                                </c:choose> </label>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="controls col-sm-3 col-xs-12">
                                        <div class="row">
                                            <label class="control-label" for="inputName">Placa</label>
                                        </div>
                                        <div class="row">
                                            <select class="selectpicker" data-style="btn-primary btn" title="Seleccione placa del veh&iacute;culo" data-width="150px" name="placa_vehiculo"  id="placa_vehiculo">
                                                <option value="0">Seleccione</option>
                                                <c:forEach items="${select.lstmovil}" var="movil">
                                                    <option value="${movil.id}">${movil.placa}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="controls col-sm-3 col-xs-12">
                                        <div class="row">
                                            <label class="control-label" for="inputName">N&uacute;mero Interno</label>
                                        </div>
                                        <div class="row">
                                            <select class="selectpicker" data-style="btn-primary btn" title="Seleccione n&uacute;mero interno" data-width="165px" name="numero_interno_vehiculo"  id="numero_interno">                                        
                                                <c:forEach items="${select.lstmovil}" var="movil">
                                                    <option value="${movil.id}">${movil.numeroInterno}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <c:choose>
                                        <c:when test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                                            <div id="box_with_hour">
                                                <div class="controls col-sm-3 col-xs-12">
                                                    <div class="row">
                                                        <label class="control-label" for="fecha_hora_inicio">Fecha y Hora Inicio</label>
                                                    </div>
                                                    <div class="row">                                           
                                                        <input type="text" class="form-control" name="fecha_hora_inicio" id="dpd3">                                            
                                                    </div>
                                                </div>
                                                <div class="controls col-sm-3 col-xs-12">
                                                    <div class="row">
                                                        <label class="control-label" for="fecha_hora_fin">Fecha y Hora Fin</label>
                                                    </div>
                                                    <div class="row">
                                                        <input type="text" class="form-control" name="fecha_hora_fin" id="dpd4">
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div id="box_without_hour">                            
                                                <div class="controls col-sm-3 col-xs-12">
                                                    <div class="row">
                                                        <label class="control-label" for="fecha_inicio">Fecha Inicio</label>
                                                    </div>
                                                    <div class="row">                                           
                                                        <input type="text" class="form-control" name="fecha_inicio" id="dpd1" data-toggle="tooltip" title="Haga clic aqui para seleccionar la fecha de inicio">                                            
                                                    </div>
                                                </div>
                                                <div class="controls col-sm-3 col-xs-12">
                                                    <div class="row">
                                                        <label class="control-label" for="fecha_fin">Fecha Fin</label>
                                                    </div>
                                                    <div class="row">
                                                        <input type="text" class="form-control" name="fecha_fin" id="dpd2" data-toggle="tooltip" title="Haga clic aqui para seleccionar la fecha de fin">
                                                    </div>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="col-sm-2 pull-right">
                                        <br>                                
                                        <a href="#myModal" role="button"  data-toggle="modal" class="btn btn-primary botonNuevaLiquidacion" id="btnBuscarVueltasN" title="Haga clic aqui para iniciar la busquedad">Buscar</a>                                                                
                                    </div>
                                </div>

                            </div>
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






<!--MODAL RELACION VEHICULO CONDUCTOR-->
<div class="modal fade container" id="myModal_1" style="z-index:2000;top:10%" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">                
                <button type="button" class="close close-button" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>                
                <h4 class="modal-title" id="myModalLabel">Relaci&oacute;n Veh&iacute;culo Conductor</h4>                
            </div>
            <div id="content-relacion-vehiculo-conductor" class="modal-body">                          
            </div>
            <div class="modal-footer">               
            </div>
        </div>
    </div>
</div>


<!--VENTANA MODAL QUE CARGA LAS VUELTAS LIQUIDADAS Y LOS DESCUENTOS-->
<div class="modal fade liquidacion_modal" id="myModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <div class="modal-header padding-xmin">                
                <div class="row">                    
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3"></div>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6"><h4 class="modal-title text-success text-center" id="myModalLabel" ><strong>Formulario de Liquidaci&oacute;n</strong></h4></div>                    
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><div class="btn-group pull-right"><button type="button" id="export-btn" onclick="guardarLiquidacion(this)" class="btn btn-primary">Liquidar</button></div></div>
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><button type="button" class="close close-button text-justify" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>
                </div>
            </div>         
            <div id="html-form-invoice" class="obfuscate"></div>
            <div id="msg-liquidation" class="form-msg bottom-space pull-left" role="alert"></div>                
            <div class="modal-body padding-xmin">
                <div class="panel panel-default1 padding-xmin">
                    <div class="panel-body padding-xmin">
                        <!--ETIQUETAS SUPERIORES--> 
                        <div class="row">
                            <div><input type="hidden" id="fecha_elaboracion_recibo"/></div>
                            <div class="col-md-1"><h5 class="display-2 text-left">Fecha:</h5></div>
                            <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><h5 class="display-2 text-left"><strong>
                                        <script>
                                            var f = new Date();
                                            var m;
                                            var d;
                                            if (((f.getMonth() + 1) >= 1) && ((f.getMonth() + 1) <= 9)) {
                                                m = '0' + (f.getMonth() + 1);
                                            } else {
                                                m = (f.getMonth() + 1);
                                            }

                                            if ((f.getDate() >= 1) && (f.getDate() <= 9)) {
                                                d = '0' + f.getDate();
                                            } else {
                                                d = f.getDate();
                                            }
                                            document.write(d + "/" + m + "/" + f.getFullYear());
                                        </script>
                                    </strong></h5></div>
                        </div>
                        <div class="row">
                            <div class="col-md-1"><h5 class="display-2 text-left">Veh&iacute;culo:</h5></div>
                            <div class="col-md-1"><h5 class="text-left"><strong><div id="PlacaN"></div></strong></h5></div>            
                            <div class="col-md-2"><h5 class="display-2 text-left">N&uacute;mero Interno:</h5></div>
                            <div class="col-md-1"><h5 class="text-left"><strong><div id="NIntN"></div></strong></h5></div>
                            <div class="col-md-1"><h5 class="display-2 text-left">Conductor:</h5></div>
                            <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1">
                                <h5 class="display-2 text-left " for="inputName">        
                                    <strong>
                                        <a href="#myModal_1" id="btn-vehiculo-conductor" data-reveco-id="0" data-co-id="0" 
                                            data-ve-id="0" role="button"  data-toggle="modal">                                           
                                        </a>
                                    </strong>
                                </h5>
                            </div>
                            
                            <c:if test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                                <div class="col-md-2">
                                    <h5 class="display-2 text-right">Numeración Inicial:</h5>
                                </div>
                                <div class="col-md-1">
                                    <h5 class="text-left">
                                        <strong>
                                            <div id="numeracionInicial"></div>
                                        </strong>
                                    </h5>
                                </div>
                            </c:if>
                            
                        </div>

                        <div class="row">            
                            <div class="col-md-2"><h5 class="display-2 text-left">Distancia Recorrida:</h5></div>                
                            <div class="col-md-1"><h5 class="text-left"><strong><div id="distN"></div></strong></h5></div>                
                            <div class="col-md-1"><h5 class="display-2 text-left">Tarifa:</h5></div>
                            <div class="col-md-1">
                                <h5 class="text-left">
                                    <select class="selectpicker" data-style="btn-primary" data-width="75px" name="tar"  id="comboTarifaN"></select>
                                </h5>
                            </div>
                            
                            <c:if test="${configuracion_etiquetas_sesion.liquidacionPorTiempo eq true}">
                                <div class="col-md-5">
                                    <h5 class="display-2 text-right">Numeración Final:</h5>
                                </div>
                                <div class="col-md-1">
                                    <h5 class="text-left">
                                        <strong>
                                            <div id="numeracionFinal"></div>
                                        </strong>
                                    </h5>
                                </div>
                            </c:if>
                            
                        </div>                        
                        <!--Titulo Pestañas-->
                        <ul class="nav nav-tabs" id="myTabs">
                            <!--<li role="presentation" class="active"><a href="#pestana10" id="vueltasTabN"><h5 class="text-success"><strong>Vueltas Reportadas</strong></h5></a></li>-->  
                            <li role="presentation" class="active"><a href="#pestana10" id="vueltasTabN"><h5 class="text-success"><strong>Vueltas Reportadas</strong></h5></a></li>  
                            <!--<li role="presentation" class="active"><a id="vueltasTabN"><h5 class="text-success"><strong>Vueltas Reportadas</strong></h5></a></li>-->  
                            <!--Titulo Pestaña donde se cargan los descuentos de la liquidacion-->
                            <li role="presentation"><a href="#pestana20" id="descuentosTabN"><h5 class="text-success"><strong>Descuentos</strong></h5></a></li>              
                            <!--<li role="presentation"><a id="descuentosTabN"><h5 class="text-success"><strong>Descuentos</strong></h5></a></li>-->              
                        </ul>
                        <div class="tab-content">                    
                            <!--Pestaña donde se imprime las vueltas a liquidar-->
                            <div role="tabpanel" class="tab-pane padding-xmin active" id="pestana10"></div>

                            <!--Pestaña donde se imprime las categorias de descuento a usar-->             
                            <div role="tabpanel" class="tab-pane padding-xmin" id="pestana20" ></div>                                        
                        </div>
                        <div class="panel padding-xmin total_side">                                   
                            <table id="" class="">
                                <thead>                        
                                </thead>
                                <tbody>                           
                                </tbody>
                                <tfoot>
                                    <!---->
                                    <tr>
                                        <td colspan="7" rowspan="2" id="notaAlerta">
                                        </td>
                                        <td><input type="hidden" id="distanciaRecorridaN">
                                            <h5 id="totalN1" class="misEtiquetas">Subtotal</h5>
                                            <!--<input class="clickedit" type="text" id="etq_tN1"/>-->                    
                                            <input type="hidden" id="id_etqN"/>
                                        </td>
                                        <td>                                                                      
                                            <input type="text" class="sinborde misEtiquetas izq" id="psjrsRgstdsN" size="3" readonly="true" name="pasajerosRegistrados" value="0"/>
                                        </td>
                                        <td size="1" class="der"><b>${emp.simboloMoneda}</b></td>
                                        <td>
                                            <input type="text" class="sinborde misEtiquetas der" id="vlrpaxreg"   size="5" readonly="true" name="valorNetoPasajerosREgistrados" value="0">                    
                                        </td>
                                    </tr>  
                                    <!---->
                                    <tr>
                                        <td>
                                            <h5 id="pasN2" class="descuento descuento misEtiquetas">Pasajeros Descontados:</h5>                    
                                            <!--<input class="clickedit" type="text" id="etq_pN2"/>-->                                     
                                        </td>                                
                                        <td class="celda">                                   
                                            <input type="text" class="sinborde descuento misEtiquetas izq"  id="psjrsDsctdsN" size="3"  readonly="true" name="pasajerosDescontados" value="0">
                                        </td>
                                        <td size="1" class="celda der"><b>${emp.simboloMoneda}</b></td>
                                        <td class="celda">
                                            <input type="text" class="sinborde descuento misEtiquetas der" id="vlrntpsjrsN" size="5"  readonly="true" name="totalPasajeros" value="0">
                                        </td>
                                    </tr>    
                                    <!---->
                                    <tr>
                                        <td colspan="7"></td>                 
                                        <td><h5 id="totalN2"  class="misEtiquetas">Sub Total: </h5>
                                            <!--<input class="clickedit" type="text" id="etq_tN2"/>-->
                                        </td>
                                        <td>                                   
                                            <input type="text" class="sinborde misEtiquetas izq" id="ttlpsjrsN" size="3"  readonly="true" name="totalPasajeros" value="0">
                                        </td>
                                        <td size="1" class="der"><b>${emp.simboloMoneda}</b></td>
                                        <td>
                                            <!--<input type="text" class="sinborde" id="sbttlN"   size="10" readonly="true" name="subTotal" style="text-align:right" value="0">                    -->
                                            <input type="text" class="sinborde misEtiquetas der" id="sbttpaxN"   size="5" readonly="true" name="subTotalPasajeros" value="0">
                                        </td>
                                    </tr>
                                    <!---->
                                    <tr>
                                        <td colspan="7"></td>
                                        <td><h5 id="totalN3" class="descuento misEtiquetas">Total Descuentos operativos:</h5>                    
                                            <!--<input class="clickedit" type="text" id="etq_tN3"/>-->
                                        </td>
                                        <td class="celda"></td>
                                        <td size="1" class="celda der"><b>${emp.simboloMoneda}</b></td>
                                        <td class="celda">
                                            <input type="text" class="sinborde descuento misEtiquetas der" id="vlrttldctsN"   size="5" readonly="true" name="valorTotalDescuentos" value="0">                                   
                                        </td>
                                    </tr>
                                    <!---->
                                    <tr>
                                        <td colspan="7"></td>
                                        <td><h5 id="totalN4" class="misEtiquetas">Recibir conductor:</h5>                    
                                            <!--<input class="clickedit" type="text" id="etq_tN4"/>-->
                                        </td>
                                        <td></td>
                                        <td size="1" class="der"><b>${emp.simboloMoneda}</b></td>
                                        <td>
                                            <input type="text" class="sinborde misEtiquetas der" id="vlrRecCond"   size="5" readonly="true" name="valorTotalDescuentos" value="0">
                                        </td>
                                    </tr>
                                    <!---->
                                    <tr>
                                        <td colspan="7"></td>
                                        <td><h5 id="totalN5" class="descuento misEtiquetas">Total Otros Descuentos: </h5>
                                            <!--<input class="clickedit" type="text" id="etq_tN5"/>-->
                                        </td>
                                        <td class="celda"></td>
                                        <td size="1" class="celda der"><b>${emp.simboloMoneda}</b></td>
                                        <td class="celda">
                                            <input type="text" class="sinborde descuento misEtiquetas der" id="vlrttlotrdctsN"   size="5" readonly="true" name="valorTotalOtrosDescuentos" value="0">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>                                
                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        <td></td>
                                        <td> <h5 id="totalN6" class="total">TOTAL A RECIBIR: </h5>
                                            <!--<input class="clickedit" type="text" id="etq_tN6"/>-->
                                        </td>
                                        <td>                                   
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                                    

                                        </td>
                                        <td size="1" class="der"><b>${emp.simboloMoneda}</b></td>
                                        <td>
                                            <input type="text" class="sinborde total der" id="vlrttlN"   size="5" readonly="true" name="valorTotal" value="0">
                                        </td>
                                    </tr>
                            </table>
                        </div><!--FIN PANEL-->
                    </div><!--FIN PANEL BODY-->                 
                </div><!--FIN PANEL ETIQUETAS SUPERIORES-->
            </div><!--FIN CUERPO MODAL-->   
        </div><!--FIN CONTENT MODAL-->            

    </div><!--FIN CONTENT MODAL LG-->
</div>


<jsp:include page="/include/footerHome.jsp" />
<jsp:include page="/include/footerHomeLiq.jsp" />
<jsp:include page="/include/end.jsp" />
