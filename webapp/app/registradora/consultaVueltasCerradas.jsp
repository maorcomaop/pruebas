
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerNoMenu.jsp" />

<div class="col-lg-8 centered over">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO REGISTRADORA</h1>    
    <section class="boxed padding-xmin over">
        <ul class="nav nav-tabs">
            <li role="presentation"><a href="/RDW1/app/registradora/cierraVuelta.jsp">Cierre de vuelta</a></li>
            <li role="presentation" class="active"><a href="#">Vueltas cerradas</a></li>
        </ul>
    
        <div class="tab-content over">
            <div role="tabpanel" class="tab-pane padding-smin active over">

                <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>                
                
                <div class="row" style="margin-top: 10px;">                        
                    <div class="control-group">
                        <label class="col-sm-2">Fecha inicial</label>
                        <div class="col-sm-4">
                            <input id="fecha_inicial" name="fecha_inicial" class="form-control" type="text">
                        </div>
                    </div>                        
                    <div class="control-group">
                        <label class="col-sm-2">Fecha final</label>
                        <div class="col-sm-4">
                            <input id="fecha_final" name="fecha_final" class="form-control" type="text">
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </section>
                
    <div style="height: 20px;"></div>
                
    <div class="boxed padding-smin">
        <table id="tablaVueltaCerrada" name="tablaVueltaCerrada" class="display" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Hora</th>
                    <th>Placa</th>
                    <th>N° Interno</th>                    
                    <th>Base</th>
                    <th>Numeraci&oacute;n</th>
                    <th>Motivo</th>
                    <th>Usuario</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${select.lst_vueltacerrada.size() > 0}">
                    <c:forEach items="${select.lst_vueltacerrada}" var="vuelta_cerrada">
                        <tr>
                            <td>${vuelta_cerrada.fecha}</td>
                            <td>${vuelta_cerrada.hora}</td>
                            <td>${vuelta_cerrada.placa}</td>
                            <td>${vuelta_cerrada.numero_interno}</td>                            
                            <td>${vuelta_cerrada.base}</td>
                            <td>${vuelta_cerrada.numeracion}</td>
                            <td>${vuelta_cerrada.motivo}</td>
                            <td>${vuelta_cerrada.usuario}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/include/footerNoMenu.jsp" />
<script>
    $(document).ready(function () {
        
        $('#fecha_inicial').datetimepicker({
            format: 'YYYY-MM-DD',
            useCurrent: true
        });
        
        $('#fecha_final').datetimepicker({
            format: 'YYYY-MM-DD',
            useCurrent: true
        });
        
        $('#tablaVueltaCerrada').DataTable({
            responsive: true,
            order: [],
            aLengthMenu: [300, 500],
            scrollY: 230,
            searching: true,
            bAutoWidth: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
        
    });
</script>
<jsp:include page="/include/end.jsp" />
