
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerNoMenu.jsp" />

<div class="col-lg-8 centered over">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REGISTRADORA</h1>    
    <section class="boxed padding-xmin over">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">Cierre de vuelta</a></li>
            <li role="presentation"><a href="/RDW1/app/registradora/consultaVueltasCerradas.jsp">Vueltas cerradas</a></li>
        </ul>
    
        <div class="tab-content over">
            <div role="tabpanel" class="tab-pane padding-smin active over">

                <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                

                <!-- Consulta de movil -->
                <form id="form-cierra-vuelta" name="form-cierra-vuelta" action="<c:url value='/cerrarVuelta' />" method="post">                              
                    <div class="row" style="margin-top: 10px;">
                        <div class="control-group">
                            <label class="col-sm-2">Veh&iacute;culo</label>
                            <div class="col-sm-4">
                                <select id="smovil" name="smovil" class="selectpicker form-control"
                                        data-live-search="true" data-container="body"> 
                                    <option value="">Seleccione un veh&iacute;culo</option>
                                    <c:forEach items="${select.lstmovil}" var="movil">
                                        <option value="${movil.id},${movil.placa},${movil.numeroInterno}">${movil.placa} - ${movil.numeroInterno}</option>
                                    </c:forEach>
                                </select>                                            
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="col-sm-2">Base</label>
                            <div class="col-sm-4">
                                <select id="sbase" name="sbase" class="selectpicker form-control"
                                        data-live-search="true" data-container="body">
                                    <option value="">Seleccione una base</option>
                                    <c:forEach items="${select.lstbase}" var="base"> 
                                        <option value="P${base.identificador}%${base.nombre}%${base.latitudWeb}%${base.longitudWeb}">${base.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 10px;">                        
                        <div class="control-group">
                            <label class="col-sm-2">Fecha</label>
                            <div class="col-sm-4">
                                <input id="fecha" name="fecha" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="col-sm-2">Motivo</label>
                            <div class="col-sm-4">
                                <textarea id="motivo" name="motivo" class="form-control" style="resize:none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 10px;">                        
                        <div class="control-group">
                            <div class="col-sm-8" style="float: right;">
                                <input class="btn" type="button" value="Aceptar" style="margin-left: 15px;"
                                       onclick="cons_cerrarVuelta();">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>

<jsp:include page="/include/footerNoMenu.jsp" />
<script>
    $(document).ready(function () {
        
        $('#smovil').selectpicker({
            size: 5
        });
        
        $('#sbase').selectpicker({
            size: 5
        });
        
        $('#fecha').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            useCurrent: true
        });                        
    });   
</script>
<jsp:include page="/include/end.jsp" />
