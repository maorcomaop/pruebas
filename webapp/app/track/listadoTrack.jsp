
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />
<!--nada--->         
<div class="col-lg-12">    
    <section class="boxed padding">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="/RDW1/app/track/listadoTrack.jsp">Reportes</a></li>        
            <li role="presentation" ><a href="/RDW1/app/track/comandoTrack.jsp">Comandos</a></li>
            <li role="presentation" ><a href="/RDW1/app/liquidacion/nuevaLiquidacion.jsp">GPSs</a></li>                                
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                <table id="tableTrack" class="display compact" cellspacing="0" width="100%">
                    <thead>
                        <tr>                                
                            <th>#</th>
                            <th>UNIDAD</th>
                            <!--<th>TIPO MENSAJE</th>-->
                            <!--<th>NUM. MENSAJE</th>-->
                            <th>MOTIVO</th>                                
                            <th>LONGITUD</th>
                            <th>LATITUD</th>                                
                            <th>KILOMETRAJE</th>
                            <th>ALTITUD</th>
                            <th>VELOCIDAD</th>
                            <th>DIRECCION</th>
                            <th>FECHA GPS</th>
                            <th>SATELITES</th>
                        </tr>
                    </thead>    
                    <tbody>                            

                    </tbody>    
                </table>
            </div>
        </div>
    </section>
</div>


<jsp:include page="/include/footerHome.jsp" />
<script>
    
    console.log("nn");
    
    // Tabla dinamica
    $(document).ready(function () {

        console.log("aa");

        $.ajax({
            url: "/RDW1/trackList",
            type: "POST",
            dataType: 'json',
            data: {json: {}},
            success: function (data) {
                if (data.success) {
                    console.log(data);
                    var oTableBody = $("#tableTrack tbody");
                    var sHtml = "";
                    var response = data.response;
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