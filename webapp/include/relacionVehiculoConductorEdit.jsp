<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row">
    <!--Registro-->
    <c:if test="${permissions.check(68) || permissions.check(5)}">
        <div class="col-sm-5">

            <div class="control-group">
                <div class="control-group">                                        
                    <div class="controls">
                        <input type="hidden" name="id_edit" id ="id_edit" value="${relacionVehiculoConductor.idRelacionVehiculoConductor}">
                        <input type="hidden" name="id_vehiculo" id="id_v" >                            
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputName">Vehículo</label>
                <div class="controls">                     
                    <select class="selectpicker " name="vehiculo" id="vh">
                        <option value="0">Seleccione</option>
                        <c:forEach items="${select.lstmovil}" var="movil">
                            <option value="${movil.id}">${movil.placa}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="inputName">Conductor</label>
                <div class="controls">
                    <select class="selectpicker" name="conductor" id="con">
                        <option value="0">Seleccione</option>
                        <c:forEach items="${select.lstconductoractivo}" var="conductor">
                            <option value="${conductor.id}">${conductor.apellido} ${conductor.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
            </div><br>
            <div class="control-group">                            
                <div class="controls">
                    <input type="submit" class="btn btn-primary" id="btn-edit-relation" data-ajax="${ajaxPage}" value="Crear">
                </div>
            </div>
            <br>
            <div class="control-alerts" id ="control-alerts" style="height:100px">
            </div>

        </div>
        <br>
    </c:if>
    <!--Editar-->
    <c:if test="${permissions.check(135) || permissions.check(5)}">
        <div class="col-sm-7" id="content-relation-table">
            <jsp:include page="/include/tablaRelacionVehiculoConductor.jsp" />
        </div>
    </c:if>
</div>

<script>

    var intervalValue = 0;
    var permissionToDelete = ${permissions.check(136) || permissions.check(5)};
    intervalValue = setInterval(function () {
        intervaling();
    }, 250);
    function intervaling() {
        var sc = $('#tableRealations_wrapper table.dataTable thead .sorting_asc');
        if (sc.length > 0) {
            if (sc[0].style.width === "0px") {
                $(sc[0]).trigger("click");
                $(sc[0]).trigger("click");
            } else {
                clearInterval(intervalValue);
            }
        }
    }

    var clickAvailable = true;
    $(document).ready(function () {

        $("#vh option[value=" + ${relacionVehiculoConductor.idVehiculo} + "]").attr("selected", true);
        $("#vh").attr('disabled', 'disabled');
        $("#con option[value=" + ${relacionVehiculoConductor.idConductor} + "]").attr("selected", true);
        $("#id_v").val($("#vh option:selected").val());
        $('.selectpicker').selectpicker({
            style: 'btn-primary',
            size: 4,
            liveSearch: true
        });
        clickAvailable = true;
        setTable("tableRealations");
        setEvents();
        $("#btn-edit-relation").click(function () {
            if (clickAvailable) {
                clickAction(false);
                $("#control-alerts").html("");
                var ajax = $(this).attr('data-ajax');
                if (ajax) {

                    $.post("/RDW1/editarRelacionVehiculoConductorAjax",
                            {
                                id_edit: $('#id_edit').val(),
                                id_vehiculo: $("#vh").val(),
                                conductor: $("#con").val()
                            },
                            function (response) {
                                console.log("vvvv");
                                var htmlResponse = "";
                                var message = "";
                                if (response.idInfo === 0) {
                                    message = response.msg;
                                    htmlResponse = "<div class ='alert alert-info'><button type ='button' class = 'close fade' data-dismiss = 'alert' ></button><strong> Información </strong>" + message + "</div>";
                                } else if (response.idInfo === 1) {
                                    message = response.msg;
                                    htmlResponse = "<div class ='alert alert-success'><button type ='button' class = 'close' data-dismiss = 'alert' ></button><strong> Información </strong>" + message + "</div>";
                                    setActiveDriver(response.relacionVehiculoConductor);
                                    printTable(response.relacionVehiculoConductor);
                                } else if (response.idInfo === 2) {
                                    message = response.msg;
                                    htmlResponse = "<div class='alert alert-error'><button type='button' class='close' data-dismiss='alert'>&times;</button><strong>Información </strong>" + message + "</div>";
                                }
                                $("#control-alerts").html(htmlResponse);
                                clickAction(true);
                            });
                }
            }
        });
    });
    function printTable(infoTable) {
        var sHtml = "";
        var oTableContainer = $("#container-table-relations");
        sHtml += "<table id='tableRealations' class='display compact' width='100%' cellspacing='0'>\n\
                    <thead>\n\
                        <tr>\n\
                            <th>Conductor</th>\n\
                            <th>Activo</th>\n";
        if (permissionToDelete) {
            sHtml += "<th>Eliminar</th>";
        }
        sHtml += "                </tr>\n\
                    </thead>\n\
                    <tbody>";
        for (var i = 0; i < infoTable.length; i++) {
            sHtml += "<tr>";
            sHtml += "<td>" + infoTable[i].nombreConductor + "</td>";
            if (infoTable[i].activo === 1) {
                sHtml += "<td><span class='glyphicon glyphicon-check option' style='color:#00b3ee; cursor:pointer' data-active='true' data-id='" + infoTable[i].idRelacionVehiculoConductor + "' data-ve='" + infoTable[i].idVehiculo + "'></span></td>";
                if (permissionToDelete) {
                    sHtml += "<td><span class='glyphicon glyphicon-remove option' style='color:gainsboro; cursor:not-allowed' data-id='" + infoTable[i].idRelacionVehiculoConductor + "' data-ve='" + infoTable[i].idVehiculo + "'></span></td>";
                }
            } else {
                sHtml += "<td><span class='glyphicon glyphicon-unchecked option' style='color:gainsboro; cursor:pointer' data-active='false' data-id='" + infoTable[i].idRelacionVehiculoConductor + "' data-ve='" + infoTable[i].idVehiculo + "'></span></td>";
                if (permissionToDelete) {
                    sHtml += "<td><span class='glyphicon glyphicon-remove option' style='color:red; cursor:pointer' data-remove='true' data-id='" + infoTable[i].idRelacionVehiculoConductor + "' data-ve='" + infoTable[i].idVehiculo + "'></span></td>";
                }
            }
            sHtml += "</tr>";
        }
        sHtml += "</tbody>\n\
                  </table>";
        oTableContainer.html(sHtml);
        setTable("tableRealations");
        setEvents();
    }
    function setActiveDriver(infoDrivers) {
        for (var i = 0; i < infoDrivers.length; i++) {
            if (infoDrivers[i].activo === 1) {
                console.log("btn-vehiculo-conductor");
                $("#btn-vehiculo-conductor").html(infoDrivers[i].nombreConductor);
                $("#btn-vehiculo-conductor").attr("data-reveco-id", infoDrivers[i].idRelacionVehiculoConductor);
                $("#btn-vehiculo-conductor").attr("data-co-id", infoDrivers[i].idConductor);
                $("#btn-vehiculo-conductorF").html(infoDrivers[i].nombreConductor);
                $("#btn-vehiculo-conductorF").attr("data-reveco-id", infoDrivers[i].idRelacionVehiculoConductor);
                $("#btn-vehiculo-conductorF").attr("data-co-id", infoDrivers[i].idConductor);
                break;
            }
        }
    }
    function setEvents() {

        $("#tableRealations .option").click(function () {

            if (clickAvailable) {

                if ($(this).attr('data-active') !== undefined) {
                    clickAction(false);
                    $.post("/RDW1/activarRelacionVehiculoConductor",
                            {
                                id: $(this).attr('data-id'),
                                id_vehicle: $(this).attr('data-ve'),
                                active: ($(this).attr('data-active') === "false" ? 1 : 0)
                            },
                            function (response) {
                                var htmlResponse = "";
                                var message = "";
                                var conId = "";
                                var revecoId = "";
                                var nombreCon = "";
                                if (response.idInfo === 0) {
                                    message = response.msg;
                                    htmlResponse = "<div class ='alert alert-info'><button type ='button' class = 'close fade' data-dismiss = 'alert' ></button><strong> Información </strong>" + message + "</div>";
                                } else if (response.idInfo === 1) {
                                    message = response.msg;
                                    htmlResponse = "<div class ='alert alert-success'><button type ='button' class = 'close' data-dismiss = 'alert' ></button><strong> Información </strong>" + message + "</div>";
                                    if (response.activo) {
                                        conId = response.conductorVehiculo[0].idConductor;
                                        nombreCon = response.conductorVehiculo[0].nombreConductor;
                                        revecoId = response.conductorVehiculo[0].idRelacionVehiculoConductor;
                                    } else {
                                        conId = 0;
                                        nombreCon = "Relacione un Conductor";
                                        revecoId = 0;
                                    }

                                    $("#id_v").val($("#vh option:selected").val());
                                    $('#con.selectpicker').selectpicker('val', conId);
                                    $("#btn-vehiculo-conductor").html(nombreCon);
                                    $("#btn-vehiculo-conductor").attr("data-reveco-id", revecoId);
                                    $("#btn-vehiculo-conductor").attr("data-co-id", conId);
                                    $("#btn-vehiculo-conductorF").html(nombreCon);
                                    $("#btn-vehiculo-conductorF").attr("data-reveco-id", revecoId);
                                    $("#btn-vehiculo-conductorF").attr("data-co-id", conId);
                                    printTable(response.relacionVehiculoConductor);
                                }
                                $("#control-alerts").css("display", "block");
                                $("#control-alerts").html(htmlResponse);
                                clickAction(true);
                            });
                } else if ($(this).attr('data-remove') !== undefined) {
                    clickAction(false);
                    $.post("/RDW1/actualizarRelacionVehiculoConductor",
                            {
                                id: $(this).attr('data-id'),
                                id_vehicle: $(this).attr('data-ve'),
                                status: 0
                            },
                            function (response) {
                                var htmlResponse = "";
                                var message = "";
                                if (response.idInfo === 0) {
                                    message = response.msg;
                                    htmlResponse = "<div class ='alert alert-info'><button type ='button' class = 'close fade' data-dismiss = 'alert' ></button><strong> Información </strong>" + message + "</div>";
                                } else if (response.idInfo === 1) {
                                    message = response.msg;
                                    htmlResponse = "<div class ='alert alert-success'><button type ='button' class = 'close' data-dismiss = 'alert' ></button><strong> Información </strong>" + message + "</div>";
                                    printTable(response.relacionVehiculoConductor);
                                }
                                $("#control-alerts").css("display", "block");
                                $("#control-alerts").html(htmlResponse);
                                clickAction(true);
                            });
                }
            }
        });
    }

    function setTable(tableId) {
//        if ($('#' + tableId + ' td').length > 0) {
        $('#' + tableId).DataTable({
            aLengthMenu: [15, 50, 100, 100],
            scrollY: false,
            scrollX: true,
            searching: true,
            bAutoWidth: true,
            bInfo: false,
            paging: false,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
//        }
    }

    function clickAction(enable) {

        if (enable) {
            clickAvailable = true;
            $("#btn-edit-relation").prop("disabled", false);
            $("#content-relation-table").toggleClass("opacity-07");
        } else {
            clickAvailable = false;
            $("#btn-edit-relation").prop("disabled", true);
            $("#content-relation-table").toggleClass("opacity-07");
        }
    }

</script>