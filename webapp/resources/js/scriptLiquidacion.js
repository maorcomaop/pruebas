
var now = new Date();
//var pasajerosRegistrados =0;
now.setDate(now.getDate() - 1);
$('#dpd1').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});
$("#dpd1").on("dp.change", function (e) {
    $('#dpd2').data("DateTimePicker").minDate(e.date);
});

$('#dpd2').datetimepicker({format: 'YYYY-MM-DD', useCurrent: false, defaultDate: new Date()});
$("#dpd2").on('dp.change', function (e) {
    $('#dpd1').data("DateTimePicker").maxDate(e.date);
});
/*******************************************************************************************************/

$('#dpd3').datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', useCurrent: true, defaultDate: 'now'});
$("#dpd3").on("dp.change", function (e) {
    $('#dpd4').data("DateTimePicker").minDate(e.date);
});

$('#dpd4').datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', useCurrent: false, defaultDate: 'now'});
$("#dpd4").on('dp.change', function (e) {
    $('#dpd3').data("DateTimePicker").maxDate(e.date);
});



$(document).ready(function () {


    var distanciaKm = 0;
    var pasajeros = 0;
    var totalLiq = 0;

    //FUNCION QUE CARGA LAS ETIQUETAS DEL FORMULARIO
    actualizarEtiquetasNeiva();
    $('.selectpicker').selectpicker({
        size: 4,
        liveSearch: true
    });

    $('.selectpicker').selectpicker('setStyle', 'btn-xs', 'add');

    $('#btn-vehiculo-conductor').on("click", function () {
        /*console.log("modal backdrop");*/
        var modalBackdrop = [];
        modalBackdrop = $("body .modal-backdrop");
        if (modalBackdrop.length > 0) {
            $(modalBackdrop[0]).css("opacity", "0");
            $("#myModal").css("z-index", "100");
        }
        $.post("/RDW1/verMasRelacionVehiculoConductor",
                {id: $("#btn-vehiculo-conductor").attr("data-reveco-id"),
                    vehiculoId: $("#btn-vehiculo-conductor").attr("data-ve-id"),
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

    $.post("/RDW1/buscarTarifas", {id: 1, estado: 1},
            function (result) {
                $("#comboTarifaN").html(result);
                $("#comboTarifaN").selectpicker("refresh");
            });


    /*Carga los items del combo placa segun la busqueda del combo numero interno*/
    $("#placa_vehiculo").change(function () {
        $('#numero_interno.selectpicker').selectpicker('val', $(this).val());
    });

    /*Carga los items del combo numero interno segun la busqueda del combo placa*/
    $("#numero_interno").change(function () {
        $('#placa_vehiculo.selectpicker').selectpicker('val', $(this).val());
    });

    $('.selectpicker').selectpicker({
        style: 'btn-primary',
        size: 4,
        liveSearch: true});

    /*CAMBIA LA TARIFA Y LOS VALORES*/
    $("#comboTarifaN").change(function () {
        $(".tarifaN").empty();
        $(".tarifaN").html($.trim($("#comboTarifaN option:selected").text()));

        $("#tableVueltas_wrapper .pasajerosVuelta").each(function () {

            var iPasajeros = parseInt(($(this).html()).trim());

            var oTotalVuelta = $(this).siblings(".totalVuelta");
            $(oTotalVuelta).html(iPasajeros * $.trim($("#comboTarifaN option:selected").text()));

        });

        calculoInicialNeiva();
    });


    $('[data-toggle="tooltip"]').tooltip();
//    $('#box_with_hour').hide();
    //VUELTAS POR HORAS O FRANJAS
//    $('#prueba').change(function ()
//    {
//        if ($(this).prop('checked') === true)
//        {
//            $('#box_with_hour').show();
//            $('#box_without_hour').hide();
//        } else {
//            $('#box_without_hour').show();
//            $('#box_with_hour').hide();
//        }
//    });



    //RECARGA LA PAGINA PARA ACTUALIZAR EL CACHE
    $('#myModal').on('hidden.bs.modal', function () {
        window.location.reload(true);
    });




    //BOTON QUE PERMITE CARGAR LAS VUELTAS Y LOS DESCUENTOS QUE ESTAN DEFINIDOS DESDE EL INICIO EN LAS DOS PESTAÑAS         
    $('#btnBuscarVueltasN').on("click", function () {
        //ENVÍO POST QUE TRAE LAS VUELTAS A LIQUIDAR Y LAS PONE EN LA PRIMERA PESTAÑA
        //console.log($("#placa_vehiculo").val());
        //console.log($("#numero_interno").val());
        $.post("/RDW1/vueltasALiquidar?nocache=" + new Date().getTime(), {placa_vehiculo: $("#placa_vehiculo").val(),
            numero_interno_vehiculo: $("#numero_interno").val(),
            id_empresa: $("#id_emp").val(),
            fecha_inicio: $("#dpd1").val(),
            fecha_fin: $("#dpd2").val(),
            fecha_hora_inicio: $("#dpd3").val(),
            fecha_hora_fin: $("#dpd4").val()},
            function (result) {

                    $("#pestana10").html(result);

                    var oTable = null;
                    if ($.fn.dataTable.isDataTable('#tableVueltas')) {
                        oTable = $('#tableVueltas').DataTable();
                    } else {
                        oTable = $('#tableVueltas').DataTable({
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
                                            return $("#simboloMoneda").val() + " " + parseInt(row[4].split(" ")[1], 10) * parseInt(row[6], 10);
                                        }
                                    }
                                },
                                {className: "dt-center", targets: "_all"}
                            ]
//                            ,
//                            ordering: false
                        }).columns.adjust();
                    }

                    var intervalValue = setInterval(function () {
                        intervaling();
                    }, 250);
                    function intervaling() {
                        if ($.fn.dataTable.isDataTable('#tableVueltas')) {
                            var sc = $('#tableVueltas_wrapper table.dataTable thead .sorting_asc');
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

                    //ENVÍO POST QUE TRAE LOS DESCUENTOS Y LOS PONE EN LA SEGUNDA PESTAÑA
                    $.post("/RDW1/categoriasDeDescuentoFijas", {fijo: 1},
                            function (result) {
                                //console.log($.trim(result));
                                $("#pestana20").html(result);

                                var oTable = null;
                                if ($.fn.dataTable.isDataTable('#tableDescuentos')) {
                                    oTable = $('#tableDescuentos').DataTable();
                                } else {
                                    oTable = $('#tableDescuentos').DataTable({
                                        aLengthMenu: [100],
                                        retrieve: true,
                                        scrollY: 140,
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
        $("#export-btn").removeClass("obfuscate");
    });
    window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 4000);

    $("#vueltasTabN").on("click", function () {
        if ($.fn.dataTable.isDataTable('#tableVueltas')) {
            var sc = $('#tableVueltas_wrapper table.dataTable thead .sorting_asc');
            if (sc.length > 0) {
                if (sc[0].style.width === "0px") {
                    $(sc[0]).trigger("click");
                    $(sc[0]).trigger("click");
                } 
            }
        }
    });
    $("#descuentosTabN").on("click", function () {

        if ($.fn.dataTable.isDataTable('#tableDescuentos')) {
            var sc = $('#tableDescuentos_wrapper table.dataTable thead .sorting_asc');
            if (sc.length > 0) {
                if (sc[0].style.width === "0px") {
                    $(sc[0]).trigger("click");
                    $(sc[0]).trigger("click");
                } 
            }
        }
    });

    // Carga vehiculo seleccionado desde informacion registradora (id vehiculo) 
    // Retrasa fecha de inicio tres dias

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


});//fin document ready


function actualizarEtiquetasNeiva() {
    /*  FUNCION QUE CONSULTA LAS ETIQUETAS DEL FORMULARIO*/
    $.post("/RDW1/buscarEtiquetas", {id: 1},
            function (result) {
                var obj = jQuery.parseJSON($.trim(result));
                //console.log("---> "+$.trim(result));
                $("#id_etqN").val(obj.id);
                //$("#pasN1").text(obj.e1);                    
                $("#pasN2").text(obj.e2);
                //$("#pasN3").text(obj.e3);                    
                //etiquetas del lado derecho
                $("#totalN1").text(obj.t1);
                $("#totalN2").text(obj.t2);
                $("#totalN3").text(obj.t3);
                $("#totalN4").text(obj.t5);
                $("#totalN5").text(obj.t6);
                $("#totalN6").text(obj.t7);
            });
}



/*FUNCIONES DE CARGA INICIAL*/
/*FUNCION QUE CALCULA LOS VALORES INICIALES DEL FORMULARIO COMO SON LA DIFERENCIA DE PASAJEROS, LA CANTIDAD EN DINERO ENTRE OTRAS*/
function calculoInicialNeiva() {
    console.log("calculoInicialNeiva");
    console.log("calculoInicialNeiva");
    console.log("calculoInicialNeiva");
    pax = $("#cantidadPasajerosRegistradosN").val();
    if (typeof pax === 'undefined' || pax === null || pax === '') {
        pax = 0;
    } else {
        try {
            if ((isNaN(pax))) {
                throw "no es un numero";
            } else {

                //CALCULA LOS PASAJEROS REGISTRADOS
                $("#psjrsRgstdsN").val(calcularPasajerosRegistradosNeiva());
                $("#psjrsDsctdsN").val(calcularPasajerosDescontadosNeiva());
                //CALCULA EL TOTAL DE PASAJEROS A LIQUIDAR
                $("#ttlpsjrsN").val(calcularPasajerosALiquidarNeiva($('#psjrsRgstdsN').val(), $('#psjrsDsctdsN').val()));
                //CALCULA LOS PASAJEROS REGISTRADOS SIN DESCUENTOS
                $("#vlrpaxreg").val(calcularValorTotalNetoPasajerosRegistradorNeiva($("#comboTarifaN option:selected").text(), $('#psjrsRgstdsN').val()));
                //CALCULA EL TOTAL EN DINERO DE PASAJEROS.
                //$("#vlrntpsjrsN").val(calcularValorTotalNetoPasajerosNeiva($("#comboTarifaN option:selected").text(), $("#ttlpsjrsN").val()));
                $("#vlrntpsjrsN").val(calcularValorTotalNetoPasajerosNeiva($("#comboTarifaN option:selected").text(), $("#psjrsDsctdsN").val()));

                //sub total entre el total de pasajeros y el total de pasajero descontados
                $("#sbttpaxN").val(calcularSubtotalPasajerosNeiva($("#vlrpaxreg").val(), $("#vlrntpsjrsN").val()));
                calcularValorNeiva();

                /*Recalcula los valores de descuentos operativos*/
                $("#vlrttldctsN").val(calcularDescuentosOperativosConductorNeiva());
                //calcula el valor a recibir del conductor
                $("#vlrRecCond").val(calcularSubtotalRecibirConductorNeiva($("#sbttpaxN").val(), $("#vlrttldctsN").val()));

                /*Recalcula el subtotal*/
                //$("#sbttlN").val(calcularSubtotalNeiva($("#vlrntpsjrsN").val(), $("#vlrttldctsN").val()));
                /*Recalcula el valor de descuentos administrativos*/
                $("#vlrttlotrdctsN").val(calcularDescuentosAdministrativoPropietarioNeiva());
                /*Recalcula el valor total de la liquidacion*/
                //$("#vlrttlN").val(calcularTotalLiquidacionNeiva($("#sbttlN").val(), $("#vlrttlotrdctsN").val()));                            
                $("#vlrttlN").val(calcularNuevoTotalLiquidacionNeiva($("#sbttpaxN").val(), $("#vlrttldctsN").val(), $("#vlrttlotrdctsN").val()));
            }
        } catch (e) {
            $("#ttlpsjrsN").val(0);
            $("#psjrsRgstdsN").val(0);
            $("#psjrsDsctdsN").val(0);
            /************************/
            $("#vlrntpsjrsN").val(0);
            $("#vlrttldctsN").val(0);
            $("#vlrpaxreg").val(0);
            //$("#sbttlN").val(0);
            $("#sbttpaxN").val(0);
            $("#vlrttlotrdctsN").val(0);
            $("#vlrttlN").val(0);
            console.log("fallo " + e);
        }
    }//FIN ELSE 

}

/*Funcion que calcula los descuentos del conductor campo aplica descuento y retorna el total*/
function calcularDescuentosOperativosConductorNeiva() {

    var valorTotalDescuentoOperativoN = 0;
    $(".valorTotal").each(function () {
        var idFila = $(this).parent().attr("id");
        var esDescuento = $('#l' + idFila + ' span').text();
        var valorContenido = $(this).text();
        valorContenido = (valorContenido.replace($("#simboloMoneda").val(), "")).replace("%", "");

        if (esDescuento === 'Si')
        {
            valorTotalDescuentoOperativoN += parseFloat(valorContenido.replace(",", "").replace(".", "")) || 0;
        }
    });
    return parseInt(valorTotalDescuentoOperativoN);
}



/*Funcion que calcula los descuentos del propietario campo aplica general y retorna el total*/
function calcularDescuentosAdministrativoPropietarioNeiva() {
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

/*Funcion que calcula el valor que se colocará en el columna valor; 
 * Si es un porcentaje se calcula sobre el total neto pasajeros.
 * Si NO es un porcentaje; es decir es un valor moneda este se agrega a la columna valor
 * */
function calcularValorNeiva() {
    $(".valor").each(function () {
        var idFila = $(this).parent().attr("id");
        var esMoneda = $('#vm' + idFila + ' span').text();
        var valorContenido = $(this).text();
        //console.log(esMoneda+" <---> "+valorContenido);
        if (esMoneda === 'Si')
        {
            //$("#v" + idFila).html(valorContenido.replace(",", "").replace(".", ""));
            $("#v" + idFila).html($("#simboloMoneda").val() + " " + parseFloat(valorContenido.replace($("#simboloMoneda").val(), "")));
        } else if (esMoneda === 'No')
        {
            //var valorNetoPasajeros = parseFloat($("#vlrntpsjrsN").val());
            var valorNetoPasajeros = parseFloat($("#sbttpaxN").val());
            var valorPorcentaje = valorNetoPasajeros * (parseFloat(valorContenido) / 100.0);
            $("#v" + idFila).html($("#simboloMoneda").val() + " " + parseInt(valorPorcentaje));
        }
    });
}

//retorna la cantidad de pasajeros registrados
function calcularPasajerosRegistradosNeiva() {
    //return pasajerosRegistrados;
    return $("#cantidadPasajerosRegistradosN").val();
}

//retorna los pasajeros a liquidar
function calcularPasajerosALiquidarNeiva(totalPasajerosRegistrados, totalPasajerosDescontados) {
    //console.log(totalPasajerosRegistrados+", "+totalPasajerosDescontados);
    return  (parseFloat(totalPasajerosRegistrados) - parseFloat(totalPasajerosDescontados));
}

//Funcion que calcula la suma total de la columna pasajeros descontados
function calcularPasajerosDescontadosNeiva() {
    var total = 0;
    $(".pasajeros").each(function () {
        total += parseInt($(this).html()) || 0;
    });
    return total;
}
function calcularValorTotalNetoPasajerosRegistradorNeiva(tarifa, totalPasajeros) {
    return (parseFloat(tarifa) * parseFloat(totalPasajeros));
}

function calcularValorTotalNetoPasajerosNeiva(tarifa, totalPasajerosALiquidar) {
    return (parseFloat(tarifa) * parseFloat(totalPasajerosALiquidar));
}

function calcularSubtotalPasajerosNeiva(valorTotalNetoPasajeros, valorPasajerosDescontados) {
    return (parseFloat(valorTotalNetoPasajeros) - parseFloat(valorPasajerosDescontados));
}
function calcularSubtotalRecibirConductorNeiva(valorTotalPasajeros, valorDescuentosOperativos) {
    return (parseFloat(valorTotalPasajeros) - parseFloat(valorDescuentosOperativos));
}
function calcularSubtotalNeiva(valorTotalNetoPasajeros, totalDescuentos) {
    return (parseFloat(valorTotalNetoPasajeros) - parseFloat(totalDescuentos));
}

function calcularTotalLiquidacionNeiva(subTotal, valorTotalOtrosDescuentos) {
    return (parseFloat(subTotal) - parseFloat(valorTotalOtrosDescuentos));
}
function calcularNuevoTotalLiquidacionNeiva(subTotal, valorTotalDescuentoOperativo, valorTotalOtrosDescuentos) {
    return (parseFloat(subTotal) - parseFloat(valorTotalDescuentoOperativo) - parseFloat(valorTotalOtrosDescuentos));
} 