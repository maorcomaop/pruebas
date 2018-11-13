
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--nada--->         
<div class="col-lg-12">    
    <section class="boxed padding">
        <ul class="nav nav-tabs">
            <li role="presentation" style="display: none"><a href="/RDW1/app/track/listadoTrack.jsp">Reportes</a></li>        
            <li role="presentation" class="active"><a href="/RDW1/app/track/comandoTrack.jsp">Comandos</a></li>
            <li role="presentation" style="display: none"><a href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">GPSs</a></li>                                
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">

                <div class="control-group">
                    <label class="control-label" for="inputName">Commando</label>
                    <div class="controls">
                        <select class="selectpicker" data-style="btn-primary" name="command"  id="command">
                            <option value="0">Seleccione un comando</option>
                            <option value="000">Solicitud de estado</option>                                
                            <option value="002">Reset</option>                                
                            <option value="00D">Borrar registro de seguimiento</option>                                
                            <option value="101">Reporte cada Minuto</option>                                
                            <option value="105">Reporte cada 5 Mins</option>                                
                            <option value="160">Reporte cada 60 Mins</option>                                
                            <option value="200">Mensaje Serial</option>                                
                            <option value="005000">Habilitar reporte por distancia</option>                                
                            <option value="005001">Inhabilitar reporte por distancia</option>                                
                            <option value="0050S100">Reporte cada 100 Metros</option>                                
                        </select>
                    </div>
                </div>
                <br>
                <div class="control-group">                            
                    <div class="controls">
                        <input type="submit" class="btn btn-primary" id = "btnCommandSend" value="Enviar">
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>


<jsp:include page="/include/footerHome.jsp" />
<script>
    $(document).ready(function () {

        $("#btnCommandSend").click(function () {
            var data = {};
            data.commandKey = $("#command").val();
            $.ajax({
                url: "/RDW1/trackCommandSend",
                type: "POST",
                dataType: 'json',
                data: {json: JSON.stringify(data)},
                success: function (resp) {
                    if (resp.success) {
                        alert("OK");
                    } else {
                        alert("NO");
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            });
        });


        $.ajax({
            url: "/RDW1/trackList",
            type: "POST",
            dataType: 'json',
            data: {json: {}},
            success: function (resp) {
                if (resp.success) {
                    var oTableBody = $("#tableTrack tbody");
                    var sHtml = "";
                    var response = resp.response;
                    if (response.length > 0) {
                        for (var i = 0; i < response.length; i++) {
                            sHtml += "<tr>";
//                            sHtml += "<td>" + response[i].id + "</td>";
                            sHtml += "<td>" + (i + 1) + "</td>";
                            sHtml += "<td>" + response[i].unitID + "</td>";
                            sHtml += "<td>" + response[i].transReasson + "</td>";
                            sHtml += "<td>" + response[i].longitude + "</td>";
                            sHtml += "<td>" + response[i].latitude + "</td>";
                            sHtml += "<td>" + response[i].mileageCounter + "</td>";
                            sHtml += "<td>" + response[i].altitude + "</td>";
                            sHtml += "<td>" + response[i].groundSpeed + "</td>";
                            sHtml += "<td>" + response[i].speedDirection + "</td>";
                            sHtml += "<td>" + response[i].gpsDate + "</td>";
                            sHtml += "<td>" + response[i].numberSatellites + "</td>";
                            sHtml += "</tr>";
                        }
                        oTableBody.html(sHtml);
                    }

                    if ($('#tableTrack td').length > 0) {
                        $('#tableTrack').DataTable({
                            aLengthMenu: [15, 50, 100, 500],
                            scrollY: 500,
                            scrollX: true,
                            searching: true,
                            bAutoWidth: false,
                            bInfo: false,
                            paging: false
                        });
                    }
                } else {
                    alert("NO");
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
</script>
<jsp:include page="/include/end.jsp" />