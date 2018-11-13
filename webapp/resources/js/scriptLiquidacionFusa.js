
var now = new Date();
now.setDate(now.getDate() - 1);
$('#dpd1').datetimepicker({format: 'YYYY-M-D', useCurrent: true, locale: 'es', defaultDate: 'now'});
$("#dpd1").on("dp.change", function (e) {
    $('#dpd2').data("DateTimePicker").minDate(e.date);
});

$('#dpd2').datetimepicker({format: 'YYYY-M-D', useCurrent: false, defaultDate: new Date()});
$("#dpd2").on('dp.change', function (e) {
    $('#dpd1').data("DateTimePicker").maxDate(e.date);
});




//var data = JSON.stringify(myArray);
$(document).ready(function () {
    actualizarEtiquetasFusa();
    /*Carga los items del combo placa segun la busqueda del combo numero interno*/
    $("#placa_vehiculo").change(function () {
        $('#numero_interno.selectpicker').selectpicker('val', $(this).val());
    });

    /*Carga los items del combo numero interno segun la busqueda del combo placa*/
    $("#numero_interno").change(function () {
        $('#placa_vehiculo.selectpicker').selectpicker('val', $(this).val());
    });


    //BUSCA LAS TARIFAS REGISTRADAS
    $.post("/RDW1/buscarTarifas", {id: 1, estado: 1},
            function (result) {
                $("#comboTarifaF").html(result);
                $("#comboTarifaF").selectpicker("refresh");
            });

    $('.selectpicker').selectpicker({
        style: 'btn-primary',
        size: 4,
        liveSearch: true});

    //CAMBIA LA TARIFA Y LOS VALORES
    $("#comboTarifaF").change(function ()
    {
        $(".tarifaF").empty();
        $(".tarifaF").html($.trim($("#comboTarifaF option:selected").text()));

        $("#tableVueltasF_wrapper .pasajerosVueltaF").each(function () {

            var iPasajeros = parseInt(($(this).html()).trim());

            var oTotalVuelta = $(this).siblings(".totalVueltaF");
            $(oTotalVuelta).html(iPasajeros * $.trim($("#comboTarifaF option:selected").text()));

        });
        calculoInicialFusa();

    });

    /* Carga vehiculo seleccionado desde informacion registradora (id vehiculo)
     * Retrasa fecha de inicio tres dias
     */
    var id_veh = $('#veh_inforeg').val();
    if (id_veh) {
        $('#numero_interno').selectpicker('val', id_veh);
        $('#placa_vehiculo').selectpicker('val', id_veh);
        $('#numero_interno').selectpicker('refresh');
        $('#placa_vehiculo').selectpicker('refresh');
        var day3 = new Date();
        day3.setDate(day3.getDate() - 3);
        $('#dpd1').data("DateTimePicker").date(day3);
    }

    // BOTON QUE PERMITE CARGAR LAS VUELTAS Y LOS DESCUENTOS QUE ESTAN DEFINIDOS DESDE EL INICIO EN LAS DOS PESTAÑAS*         
    $('#myModal').on('hidden.bs.modal', function () {
        window.location.reload(true);
    });

    $('#btnBuscarVueltasF').on("click", function ()
    {
        /*ENVÍO POST QUE TRAE LAS VUELTAS A LIQUIDAR Y LAS PONE EN LA PRIMERA PESTAÑA*/
        $.post("/RDW1/vueltasALiquidarFusa", {id_vehiculo: $("#placa_vehiculo").val(),
            id_empresa: $("#id_emp").val(),
            fecha_inicio: $("#dpd1").val(),
            fecha_fin: $("#dpd2").val(),
            fecha_hora_inicio: $("#dpd3").val(),
            fecha_hora_fin: $("#dpd4").val()},
                function (result) {

                    $("#pestana10").html(result);

                    var oTable = null;
                    if ($.fn.dataTable.isDataTable('#tableVueltasF')) {
                        oTable = $('#tableVueltasF').DataTable();
                    } else {
                        oTable = $('#tableVueltasF').DataTable({
                            scrollY: 105,
                            retrieve: true,
                            searching: false,
                            info: false,
                            paging: false,
                            columnDefs: [
                                {targets: [7], data: function (row, type, val, meta) {
                                        if (row[6] === "-") {
                                            return "-";
                                        } else {
                                            return $("#simboloMoneda").val() + " " + parseInt(row[4], 10) * parseInt(row[6], 10);
                                        }
                                    }
                                },
                                {className: "dt-center", targets: "_all"}
                            ]
                        }).columns.adjust();
                    }

                    var intervalValue = setInterval(function () {
                        intervaling();
                    }, 250);
                    function intervaling() {
                        if ($.fn.dataTable.isDataTable('#tableVueltasF')) {
                            var sc = $('#tableVueltasF_wrapper table.dataTable thead .sorting_asc');
                            if (sc.length > 0) {
                                if (sc[0].style.width === "0px") {
                                    $(sc[0]).trigger("click");
                                    $(sc[0]).trigger("click");
                                } else {
                                    clearInterval(intervalValue);
                                }
                            }
                        }
                    }

                    /*ENVO POST QUE TRAE LOS DESCUENTOS Y LOS PONE EN LA SEGUNDA PESTAA*/
                    $.post("/RDW1/categoriasDeDescuentoFijasFusa", {fijo: 1, descuento: 1},
                            function (result) {
                                $("#pestana20").html(result);

                                var oTable = null;
                                if ($.fn.dataTable.isDataTable('#tableDescuentosF')) {
                                    oTable = $('#tableDescuentosF').DataTable();
                                } else {
                                    oTable = $('#tableDescuentosF').DataTable({
                                        aLengthMenu: [100],
                                        retrieve: true,
                                        scrollY: 140,
//            scrollCollapse: true,
                                        searching: false,
                                        info: false,
                                        paging: false,
                                        columnDefs: [
                                            {visible: true, targets: 6},
                                            {className: "dt-center", targets: "_all"}
                                        ]
                                    });
                                }
                            });
                });

        // setting action buttons in initial state;
        var msgDiv = $("#msg-liquidation");
        msgDiv.html("");
        msgDiv.removeClass("alert");
        msgDiv.removeClass("alert-warning");
        msgDiv.removeClass("alert-success");
        msgDiv.removeClass("alert-dager");
        $("#btn_guardar_lq").removeClass("obfuscate");
    });

    window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 4000);

    $("#vueltasTabF").on("click", function () {

        if ($.fn.dataTable.isDataTable('#tableVueltasF')) {
            var sc = $('#tableVueltasF_wrapper table.dataTable thead .sorting_asc');
            if (sc.length > 0) {
                if (sc[0].style.width === "0px") {
                    $(sc[0]).trigger("click");
                    $(sc[0]).trigger("click");
                }
            }
        }

    });
    $("#descuentosTabF").on("click", function () {

        if ($.fn.dataTable.isDataTable('#tableDescuentosF')) {
            var sc = $('#tableDescuentosF_wrapper table.dataTable thead .sorting_asc');
            if (sc.length > 0) {
                if (sc[0].style.width === "0px") {
                    $(sc[0]).trigger("click");
                    $(sc[0]).trigger("click");
                }
            }
        }
    });

    //viene de vueltas a liquidar
//    var oTable = null;
//    if ($.fn.dataTable.isDataTable('#tableVueltasF')) {
//        oTable = $('#tableVueltasF').DataTable();
//    } else {
//        oTable = $('#tableVueltasF').DataTable({
//            scrollY: 105,
//            retrieve: true,
//            searching: false,
//            info: false,
//            paging: false,
//            columnDefs: [
//                {targets: [7], data: function (row, type, val, meta) {
//                        return parseInt(row[4], 10) * parseInt(row[6], 10);
//                    }
//                },
//                {className: "dt-center", targets: "_all"}
//            ]
////            ,
////            ordering: false
//        }).columns.adjust();
//    }

    $('.selectpicker').selectpicker({
        size: 4,
        liveSearch: true
    });

    $('.selectpicker').selectpicker('setStyle', 'btn-xs', 'add');

    $('#btn-vehiculo-conductorF').on("click", function () {
        var modalBackdrop = [];
        modalBackdrop = $("body .modal-backdrop");
        if (modalBackdrop.length > 0) {
            $(modalBackdrop[0]).css("opacity", "0");
            $("#myModal").css("z-index", "100");
        }
        $.post("/RDW1/verMasRelacionVehiculoConductor",
                {id: $("#btn-vehiculo-conductorF").attr("data-reveco-id"),
                    vehiculoId: $("#btn-vehiculo-conductorF").attr("data-ve-id"),
                    include_: true
                },
                function (result) {
                    $("#content-relacion-vehiculo-conductor").html(result);
                });
    });

    $('#myModal_1 button.close-button').on("click", function () {
        var modalBackdrop = [];
        modalBackdrop = $("body .modal-backdrop");
        if (modalBackdrop.length > 0) {
            $(modalBackdrop[0]).css("opacity", "0.5");
            $("#myModal").css("z-index", "1050");
            $("#myModal").css("overflow", "auto");
        }
    });



});//FIN DOCUMENT AND READY

//FUNCION QUE PERMITE CARGAR LAS ETIQUETAS DEL FORMULARIO DE LIQUIDACION
function actualizarEtiquetasFusa() {
    $.post("/RDW1/buscarEtiquetas", {id: 1},
            function (result) {
                var obj = jQuery.parseJSON($.trim(result));
                //console.log("<---- > "+$.trim(result));
                $("#id_etqF").val(obj.id);
                //$("#pasF1").text(obj.e1);
                $("#pasF2").text(obj.e2);
                //$("#pasF3").text(obj.e3);
                $("#totalF1").text(obj.t1);
                $("#totalF2").text(obj.t2);
                $("#totalF3").text(obj.t3);
                $("#totalF4").text(obj.t4);
                $("#totalF5").text(obj.t5);
                $("#totalF6").text(obj.t6);
                $("#totalF7").text(obj.t7);
                $("#totalF8").text(obj.t8);
            });
}


//FUNCIONES DE CARGA INICIAL                      
//FUNCION QUE CALCULA LOS VALORES INICIALES DEL FORMULARIO COMO SON LA DIFERENCIA DE PASAJEROS, LA CANTIDAD EN DINERO ENTRE OTRAS

function calculoInicialFusa() {
    try {

        paxF = $("#cantidadPasajerosRegistradosF").val();
        //console.log("pax --> "+paxF);
        if (typeof paxF === 'undefined' || paxF === null || paxF === '') {
            paxF = 0;
        } else {
            if (isNaN(paxF)) {
                throw "no es un numero";
            } else {
                /*CALCULA LOS PASAJEROS REGISTRADOS*/
                $("#psjrsRgstdsF").val(calcularPasajerosRegistradosFusa());
                $("#psjrsDsctdsF").val(calcularPasajerosDescontadosFusa());
                /*CALCULA EL TOTAL DE PASAJEROS A LIQUIDAR*/
                $("#ttlpsjrsF").val(calcularPasajerosALiquidarFusa($('#psjrsRgstdsF').val(), $('#psjrsDsctdsF').val()));

                //Calcula el valor subtotal 
                $("#subttlliqF").val(calcularValorFinalPasajeros($("#comboTarifaF option:selected").text(), $("#ttlpsjrsF").val()));

                //calcula los pasajeros registrados sin descuentos
                $("#vlrpaxregF").val(calcularValorTotalNetoPasajerosRegistradorFusa($("#comboTarifaF option:selected").text(), $('#psjrsRgstdsF').val()));
                /*se suman todos los descuentos que se aplican al neto*/

                /*CALCULA EL TOTAL EN DINERO DE PASAJEROS.*/
                //$("#vlrntpsjrs").val(calcularValorTotalNetoPasajerosFusa($("#comboTarifaF option:selected").text(), $("#ttlpsjrs").val()));                    
                $("#vlrntpsjrsF").val(calcularValorTotalNetoPasajerosFusa($("#comboTarifaF option:selected").text(), $("#psjrsDsctdsF").val(), $("#psjrsRgstdsF").val()));


                //Calcula los descuentos al neto de los pasajeros
                calcularValorFusa();
                $("#vlrdstntpsjrsF").val(calcularDescuentosAlNetoDePasajerosFusa());

                /*se calcula el subtotal del la liquidacion*/
                //$("#sbttl").val(calcularSubtotalFusa($("#vlrntpsjrsF").val(), $("#vlrdstntpsjrsF").val()));
                $("#sbttpaxF").val(calcularSubtotalFusa($("#vlrpaxregF").val(), $("#vlrntpsjrsF").val(), $("#vlrdstntpsjrsF").val()));
                calcularValorFusa();

                /*Recalcula los valores de descuentos operativos*/
                $("#vlrttldctsF").val(calcularDescuentosOperativosConductorFusa());

                /*calcula el valor que debe recibir del conductor*/
                $("#vlrRecCondF").val(calcularentregaConductorFusa($("#sbttpaxF").val(), $("#vlrttldctsF").val()));

                /*Recalcula el valor de descuentos administrativos*/
                $("#vlrttlotrdctsF").val(calcularDescuentosAdministrativoPropietarioFusa());

                /*Recalcula el valor total de la liquidacion*/
                $("#vlrttl").val(calcularTotalLiquidacionFusa($("#sbttpaxF").val(), $("#vlrttldctsF").val(), $("#vlrttlotrdctsF").val()));
            }
        }//FIN ELSE VERIFICA PAX
        //console.log("pasajeros--> "+paxF);

    } catch (e) {
        $("#ttlpsjrsF").val(0);
        $("#psjrsRgstdsF").val(0);
        $("#psjrsDsctdsF").val(0);
        /************************/
        $("#vlrntpsjrsF").val(0);
        $("#vlrttldctsF").val(0);
        //$("#sbttl").val(0);
        $("#sbttpaxF").val(0);
        $("#vlrttlotrdctsF").val(0);
        $("#vlrttl").val(0);
        console.log("fallo " + e);
    }
}

//Funcion que calcula los descuentos
function calcularDescuentosAlNetoDePasajerosFusa() {
    //console.log("descuento al neto");
    var valorTotalDescuentoAlNeto = 0;
    $(".valorTotal").each(function () {
        var idFila = $(this).parent().attr("id");
        var esDescuento = $('#l' + idFila + ' span').text();
        var valorContenido = $(this).text();
        valorContenido = (valorContenido.replace($("#simboloMoneda").val(), "")).replace("%", "");
        //console.log("--->> "+valorContenido+", "+esDescuento);
        if (esDescuento === '-')
        {
            valorTotalDescuentoAlNeto += parseFloat(valorContenido.replace(",", "").replace(".", "")) || 0;
        }
    });
    return parseInt(valorTotalDescuentoAlNeto);
}

//Funcion que calcula los descuentos del conductor campo aplica descuento y retorna el total
function calcularDescuentosOperativosConductorFusa() {
    //console.log("descuentos operativos fusa100");
    var valorTotalDescuentoOperativo = 0;
    $(".valorTotal").each(function () {
        var idFila = $(this).parent().attr("id");
        var esDescuento = $('#l' + idFila + ' span').text();
        var valorContenido = $(this).text();
        valorContenido = (valorContenido.replace($("#simboloMoneda").val(), "")).replace("%", "");
        if (esDescuento === 'Si')
        {
            valorTotalDescuentoOperativo += parseFloat(valorContenido.replace(",", "").replace(".", "")) || 0;
        }
    });
    return parseInt(valorTotalDescuentoOperativo);
}

//Funcion que calcula los descuentos del propietario campo aplica general y retorna el total
function calcularDescuentosAdministrativoPropietarioFusa() {
    //console.log("descuentos administrativos fusa");
    var valorTotalDescuentoAdministrativo = 0;
    $(".valorTotal").each(function () {
        var idFila = $(this).parent().attr("id");
        var esDescuento = $('#l' + idFila + ' span').text();
        var valorContenido = $(this).text();
        valorContenido = (valorContenido.replace($("#simboloMoneda").val(), "")).replace("%", "");
        if (esDescuento === 'No')
        {
            valorTotalDescuentoAdministrativo += parseFloat(valorContenido.replace(",", "").replace(".", "")) || 0;
        }
    });
    return parseInt(valorTotalDescuentoAdministrativo);
}

//Funcion que calcula el valor que se colocará en el columna valor; 
//Si es un porcentaje se calcula sobre el total neto pasajeros.
// Si NO es un porcentaje; es decir es un valor moneda este se agrega a la columna valor    
function calcularValorFusa() {
    $(".valor").each(function () {
        var idFila = $(this).parent().attr("id");
        var esMoneda = $('#vm' + idFila + ' span').text();
        var esDescuento = $('#l' + idFila + ' span').text();
        //console.log("--> "+esDescuento+" $$<-> "+esMoneda);
        var valorContenido = $(this).text();

        if (esMoneda === 'Si')
        {
            $("#v" + idFila).html($("#simboloMoneda").val() + " " + parseFloat(valorContenido.replace($("#simboloMoneda").val(), "")));

        } else if (esMoneda === 'No') {
            //var valorNetoPasajeros = parseFloat($("#vlrntpsjrsF").val());
            var valorNetoPasajeros = 0;
            if (esDescuento === '-') {
                valorNetoPasajeros = parseFloat($("#vlrpaxregF").val()) - parseFloat($("#vlrntpsjrsF").val());
            } else {
                valorNetoPasajeros = parseFloat($("#sbttpaxF").val());
            }

            var valorPorcentaje = valorNetoPasajeros * (parseFloat(valorContenido) / 100.0);
            $("#v" + idFila).html($("#simboloMoneda").val() + " " + parseInt(valorPorcentaje));
        }
    });
}
//funcion que devuelve la cantidad de pasajeros registrados
function calcularPasajerosRegistradosFusa() {
    //console.log("pass registradod");
    var paxFullF = $("#cantidadPasajerosRegistradosF").val();
    return paxFullF;
}

function calcularPasajerosALiquidarFusa(totalPasajerosRegistrados, totalPasajerosDescontados) {
    //console.log("pasa liqui");
    return  (parseFloat(totalPasajerosRegistrados) - parseFloat(totalPasajerosDescontados));
}

function calcularPasajerosALiquidarFusa(totalPasajerosRegistrados, totalPasajerosDescontados) {
    //console.log("pasa liqui");
    return  (parseFloat(totalPasajerosRegistrados) - parseFloat(totalPasajerosDescontados));
}

/*Funcion que calcula la suma total de la columna pasajeros descontados*/
function calcularPasajerosDescontadosFusa() {
    //console.log("dcto pas");
    var total = 0;
    $(".pasajeros").each(function () {
        total += parseInt($(this).html()) || 0;
    });
    return total;
}

function calcularValorTotalNetoPasajerosRegistradorFusa(tarifa, totalPasajeros) {
    return (parseFloat(tarifa) * parseFloat(totalPasajeros));
}

function calcularValorTotalNetoPasajerosFusa(tarifa, totalPasajerosDescontados, totalPasajeros) {
    // console.log("total neto");
    var retorno = 0;
    retorno = (parseFloat(tarifa) * parseFloat(totalPasajerosDescontados));
    /*if (totalPasajerosDescontados > 0){
     retorno = (parseFloat(tarifa) * parseFloat(totalPasajerosDescontados));
     }else{
     //retorno = (parseFloat(tarifa) * parseFloat(totalPasajeros));
     retorno = (parseFloat(tarifa) * parseFloat(totalPasajerosDescontados));
     }*/
    //return (parseFloat(tarifa) * parseFloat(totalPasajerosALiquidar));
    return retorno;
}

function calcularValorFinalPasajeros(tarifa, totalPasajeros) {
    return (parseFloat(tarifa) * parseFloat(totalPasajeros));
}

function calcularSubtotalPasajerosFusa(valorTotalNetoPasajeros, valorPasajerosDescontados) {
    return (parseFloat(valorTotalNetoPasajeros) - parseFloat(valorPasajerosDescontados));
}

/*function calcularSubtotalFusa(valorTotalNetoPasajeros, totalDescuentosAlNeto) {
 //console.log("subtotal");
 return (parseFloat(valorTotalNetoPasajeros) - parseFloat(totalDescuentosAlNeto));
 }**/
function calcularSubtotalFusa(TotalNetoPasajeros, totalPasajerosDescontados, totalDescuentosAlNeto) {
    //console.log("subtotal");
    var subTotal = 0;
    if (TotalNetoPasajeros !== totalPasajerosDescontados) {
        subTotal = (parseFloat(TotalNetoPasajeros) - parseFloat(totalPasajerosDescontados) - parseFloat(totalDescuentosAlNeto));
    }

    if (TotalNetoPasajeros === totalPasajerosDescontados) {
        subTotal = (parseFloat(TotalNetoPasajeros) - parseFloat(totalPasajerosDescontados) - parseFloat(totalDescuentosAlNeto));
    }
    //return (parseFloat(TotalNetoPasajeros) - parseFloat(totalDescuentosAlNeto));
    return subTotal;
}

function calcularentregaConductorFusa(subTotal, totalDescuentosOperativos) {
    //console.log("condcto");
    return (parseFloat(subTotal) - parseFloat(totalDescuentosOperativos));
}

function calcularTotalLiquidacionFusa(subTotal, valorTotalDescuentos, valorTotalOtrosDescuentos) {
    //console.log("total lq");
    return (parseFloat(subTotal) - parseFloat(valorTotalDescuentos) - parseFloat(valorTotalOtrosDescuentos));
}

   