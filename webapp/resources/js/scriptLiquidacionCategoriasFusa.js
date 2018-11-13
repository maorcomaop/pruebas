var tablaDescuentos = "";
var data = {};
$(document).ready(function () {
    $.ajaxSetup({cache: false});
    calculoInicialFusa();

    var firstClick = false;
    $('#descuentosTabF').click(function () {
        if (!firstClick) {
            var sc = $('#tableDescuentosF_wrapper table.dataTable thead .sorting_asc');
            $(sc).trigger("click");
            $(sc).trigger("click");
            firstClick = true;
        }
    });

    $(".categoria-descuento select.selectpicker").change(function () {

        var idCombo = $(this).attr('id');
        if ($(this).val() > 0) {

            $.post("/RDW1/encontrarCategoriasDeDescuentoFusa", {id: $(this).val()}, function (result) {

                if ($.fn.dataTable.isDataTable('#tableDescuentosF')) {
                    var oTable = $('#tableDescuentosF').DataTable();
                    if (idCombo.indexOf("cobx") !== -1) {
                        oTable.row($("#" + idCombo).parents('tr')).remove().draw();
                    }
                    oTable.row.add($(result)[0]).draw();
                }

            });
            $('#' + idCombo).selectpicker('val', 0);
        }
    });

    $("#addCategoriaF").change(function () {
        var idCombo = $(this).attr('id');
        if ($(this).val() > 0) {
            $.post("/RDW1/encontrarCategoriasDeDescuentoFusa", {id: $(this).val()}, function (result) {
                if ($.fn.dataTable.isDataTable('#tableDescuentosF')) {

                    var oTable = $('#tableDescuentosF').DataTable();
                    if (idCombo.indexOf("cobx") !== -1) {
                        oTable.row($("#" + idCombo).parents('tr')).remove().draw();
                    }
                    oTable.row.add($(result)[0]).draw();
                }
            });
            $('#' + idCombo).selectpicker('val', 0);
        }
    });





    configuarEventosTablaDescuentosFusa();

});//FIN DOCUMENT READER

function guardarLiquidacionFusa(button) {
    if (parseInt($("#btn-vehiculo-conductorF").attr("data-co-id")) <= 0) {
        alert("Seleccione un Conductor");
        return;
    }
    $(button).prop('disabled', true);

    data.descuentosInfo = [];
    obtnerDatosFusa();
    var sTarifa = parseFloat($("#comboTarifaF option:selected").text());
    var sCantidadPasajerosDescontados = parseFloat($("#psjrsDsctdsF").val());
    //liquidacion general
    data.fkTarifaFija = $("#comboTarifaF").val();
    data.fkVehiculo = $("#id_vh_lqF").val();
    data.fkConductor = $("#btn-vehiculo-conductorF").attr("data-co-id");
    data.totalPasajeros = $("#psjrsRgstdsF").val();
    data.totalPasajerosLiquidados = $("#ttlpsjrsF").val();
    data.totalValorVueltas = $("#sbttpaxF").val();
    data.totalValoDescuentoNetoPasajeros = $("#vlrdstntpsjrsF").val();
    data.totalValorDescuentosPasajeros = parseFloat(sCantidadPasajerosDescontados * sTarifa);
    data.totalValorDescuentosAdicional = $("#vlrttldctsF").val();
    data.totalValorOtrosDescuentos = $("#vlrttlotrdctsF").val();
    data.distancia = $("#distanciaRecorridaF").val();
    //calculo IPK   
    if (parseFloat($("#distanciaRecorridaF").val()) > 0) {
        data.ipk = (parseFloat($("#ttlpsjrsF").val()) / parseFloat($("#distanciaRecorridaF").val()));
    } else {
        data.ipk = 0;
    }

    //liquidacion adicional
    data.cantidadPasajerosDescontados = sCantidadPasajerosDescontados;
    //console.log(data);
    var json = JSON.stringify(data);
    //console.log(json);
    //console.log(json);
    $.ajax({
        url: "/RDW1/guardarLiquidacion",
        type: "POST",
        dataType: 'json',
        data: {
            json: json, 
            fecha_hora_inicio: $("#dpd3").val(),
            fecha_hora_fin: $("#dpd4").val(),
            fecha_inicio: $("#dpd1").val(),
            fecha_fin: $("#dpd2").val()
        },
        success: function (data) {
            //console.log("here " + data);
            console.log("se guarda ");
            var messageDiv = $("#msg-liquidation");
            messageDiv.css("display", "inline");
            if (data.saved) {
                messageDiv.addClass("alert alert-success");
                messageDiv.html("Liquidación guardada exitosamente");
                $("#btn_guardar_lq").addClass("obfuscate");

                // auto opening invoice nust generated 
                var pkLiquidacion = data.response[0].id;
                var fkVehiculo = data.response[0].fkVehiculo;
                var formAuxHTML = "<form id='form-invoice-id' action='/RDW1/verReciboLiquidacion' method='post' target='_blank'>\n\
                                            <input type='hidden' name='id' value='" + fkVehiculo + "'>\n\
                                            <input type='hidden' name='pk_liquidacion' value='" + pkLiquidacion + "'>\n\
                                            <input type='submit' class='btn  btn-xs  btn-info' value='Recibo'></form>";
                var formAuxDiv = $("#html-form-invoice");
                formAuxDiv.html(formAuxHTML);
                document.getElementById('form-invoice-id').submit();
            } else {
                $(this).prop('disabled', false);
                messageDiv.addClass("alert alert-warning");
                messageDiv.html("Liquidación no guardada");
            }
        },
        error: function (e) {
            console.log(e);
            var messageDiv = $("#msg-liquidation");
            messageDiv.css("display", "inline");
            messageDiv.addClass("alert alert-danger");
            messageDiv.html("Error guardando la liquidación");
            $(this).prop('disabled', false);
        }
    });
    /*FIN AJAX*/
    console.log("se termina la funcion");
}//FIN FUNCION

function obtnerDatosFusa() {
    $("#tableDescuentosF tbody tr").each(function (index) {
        var campo0, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8;
        var hCampos = {};
        $(this).children("td").each(function (index2) {
            switch (index2) {
                case 0:
                    campo0 = parseFloat(($("#valor_cate" + $(this).attr("id").substring(1)).val()).trim());
                    hCampos.fkCategoria = campo0;
                    break;
                case 1:
                    campo1 = parseFloat((($("#" + $(this).attr("id")).text()).trim()).replace("%", "").replace($("#simboloMoneda").val(), ""));
                    hCampos.base = campo1;
                    break;
                case 2:
                    campo2 = parseFloat(($("#" + $(this).attr("id")).text()).trim());
                    hCampos.pasajeros = campo2;
                    break;
                case 3:
                    campo3 = parseFloat((($("#" + $(this).attr("id")).text()).trim()).replace("%", "").replace($("#simboloMoneda").val(), ""));
                    hCampos.valor = campo3;
                    break;
                case 4:
                    campo4 = $("#" + $(this).attr("id")).text();
                    hCampos.observaciones = campo4;
                    break;
                case 5:
                    {
                        if ($.trim($("#" + $(this).attr("id")).text()) === "Si")
                        {
                            campo5 = 1;
                        } else
                        {
                            campo5 = 0;
                        }
                    }
                    hCampos.liquidar = campo5;
                    break;
                case 6:
                    {
                        if ($.trim($("#" + $(this).attr("id")).text()) === "Si")
                        {
                            campo6 = 1;
                        } else
                        {
                            campo6 = 0;
                        }
                    }
                    hCampos.esMoneda = campo6;
                    break;
                case 7:
                    {
                        if ($.trim($("#" + $(this).attr("id")).text()) === "Si")
                        {
                            campo7 = 1;
                        } else
                        {
                            campo7 = 0;
                        }
                    }
                    hCampos.desPasajeros = campo7;
                    break;
                case 8:
                    {
                        if ($.trim($("#" + $(this).attr("id")).text()) === "Si")
                        {
                            campo8 = 1;
                        } else
                        {
                            campo8 = 0;
                        }
                    }
                    hCampos.desNetoPasajeros = campo8;
                    break;
            }/*fin switch*/

            $(this).css("background-color", "#ECF8E0");
        });/*FIN FUNCION EACH*/
        data.descuentosInfo.push(hCampos);
    });/*FIN EACH TABLE*/
}


function configuarEventosTablaDescuentosFusa() {

    $('.selectpicker').selectpicker({
        style: 'btn-primary',
        size: 4,
        liveSearch: true
    });

    $('.selectpicker').selectpicker('setStyle', 'btn-xs', 'add');

    $('.table-remove').off("click");
    $('.table-remove').click(function () {
        if ($.fn.dataTable.isDataTable('#tableDescuentosF')) {
            var oTable = $('#tableDescuentosF').DataTable();
            oTable.row($(this).parents('tr')).remove().draw();
            calculoInicialFusa();
        }
    });

    $("td").off("mouseleave");
    /*Cambia el color de las celdas cuando sale el mouse*/
    $("td").mouseleave(function () {
        var idFila = $(this).parent().attr("id");
        var idcelda = $(this).attr("id");
        switch (idcelda) {
            case "c" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "b" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "p" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "v" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "o" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "l" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "dp" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
            case "e" + idFila:
                $(this).css("background-color", "#FFFFFF");
                break;
        }
    });

    $("td").off("mouseover");
    /*Cambia el color de las celdas cuando entra el mouse*/
    $("td").mouseover(function () {
        var idFila = $(this).parent().attr("id");
        var idcelda = $(this).attr("id");
        switch (idcelda) {
            case "c" + idFila:
                $(this).css("background-color", "#FFCCCC");
                break;
            case "b" + idFila:
                $(this).css("background-color", "#7FFFD4");
                break;
            case "p" + idFila:
                if ($.trim($("#dp" + idFila).text()) === "Si")
                {
                    $(this).prop("contenteditable", "true");
                    $(this).css("background-color", "#7FFFD4");
                } else
                {
                    $(this).css("background-color", "#FFCCCC");
                }
                break;
            case "v" + idFila:
                $(this).css("background-color", "#FFCCCC");
                break;
            case "o" + idFila:
                $(this).css("background-color", "#7FFFD4");
                break;
            case "l" + idFila:
                $(this).css("background-color", "#FFCCCC");
                break;
            case "dp" + idFila:
                $(this).css("background-color", "#FFCCCC");
                break;
            case "e" + idFila:
                $(this).css("background-color", "#FFCCCC");
                break;
        }
    });

    $("td.valor").off("click");
    $("td.valor").click(function (e) {
        console.log("clickando valor");
        var idFila = $(this).parent().attr("id");
        var esMoneda = $('#vm' + idFila + ' span').text();
        if (!$(this).hasClass("cellEditing")) {
            var originalContent = $(this).text();
            originalContent = originalContent.replace($("#simboloMoneda").val(), "").replace("%", "");
            $(this).addClass("cellEditing");
            $(this).html("<input type='text' class='valor' value=" + originalContent + " />");
            $(this).children().first().select();
            $(this).children().first().focus();
            $(this).children().first().keypress(function (e) {
                if (e.which === 13) {
                    var newContent = $(this).val();
                    try
                    {
                        if ((isNaN(newContent))) {
                            throw "no es un numero";
                        } else {
                            newContent = newContent.replace($("#simboloMoneda").val(), "").replace("%", "");
                            $(this).parent().removeClass("cellEditing");
                            if (newContent.trim() !== "") {
                                //$(this).parent().text(esMoneda === "Si" ? "$" + newContent : newContent + "%");
                                //calculoInicial();
                                if (esMoneda === "Si")
                                {
                                    if (newContent >= 0)
                                    {
                                        console.log("es contenido valido");
                                        $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + newContent : "$" + newContent);
                                        calculoInicialFusa();
                                    } else
                                    {
                                        $(this).parent().text(esMoneda === "Si" ? "$0" : "$0");
                                        alert("Debe Ingresar Valores mayores de cero (0)");

                                    }
                                } else
                                {
                                    console.log("no es moneda");

                                }
                                /***********************************************************************************/
                                if (esMoneda === "No")
                                {
                                    if ((newContent >= 0) && (newContent <= 100))
                                    {
                                        console.log("es contenido valido" + newContent);
                                        $(this).parent().text(esMoneda === "No" ? newContent + "%" : newContent + "%");
                                        calculoInicialFusa();
                                    } else
                                    {
                                        $(this).parent().text(esMoneda === "No" ? "0%" : "0%");
                                        alert("Debe Ingresar Valores entre cero (0) y cien (100)");

                                    }
                                } else
                                {
                                    console.log("no es moneda");

                                }



                            } else {
                                //$(this).parent().text(esMoneda === "Si" ? "$" + originalContent : originalContent + "%");
                                $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + "0" : "0" + "%");
                            }
                            /*CALCULO DE PORCENTAJES*/
                            /*if (newContent.trim() !== "")
                             {
                             console.log("entra al editar si es porcentaje si")
                             $(this).parent().text(esMoneda === "No" ? "$" + newContent : newContent + "%");
                             calculoInicial();
                             } else
                             {
                             console.log("entra al editar si es porcentaje no")
                             //$(this).parent().text(esMoneda === "No" ? "$" + originalContent : originalContent + "%");
                             $(this).parent().text(esMoneda === "No" ? "$" + "0" : "0" + "%");
                             }*/
                        }/*FIN ELSE NAN*/
                    } catch (e)
                    {
                        console.log("---> ERROR");
                        alert("Debe ingresar valores numericos");
                    }

                } else
                {
                    console.log("nada");
                }
            });
            $(this).children().first().focusout(function () {
                var newContent = $(this).val();

                try
                {
                    if ((isNaN(newContent))) {
                        throw "no es un numero";
                    } else {
                        newContent = newContent.replace($("#simboloMoneda").val(), "").replace("%", "");
                        $(this).parent().removeClass("cellEditing");
                        
                        if (newContent.trim() !== "") {
                            
                            if (esMoneda === "Si")
                            {
                                if (newContent >= 0)
                                {
                                    console.log("es contenido valido");
                                    $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + newContent : "$" + newContent);
                                    calculoInicialFusa();
                                } else
                                {
                                    $(this).parent().text(esMoneda === "Si" ? "$0" : "$0");
                                    alert("Debe Ingresar Valores mayores de cero (0)");
                                }
                            } else
                            {
                                console.log("no es moneda");
                            }
                            /***********************************************************************************/
                            if (esMoneda === "No")
                            {
                                if ((newContent >= 0) && (newContent <= 100))
                                {
                                    console.log("es contenido valido" + newContent);
                                    $(this).parent().text(esMoneda === "No" ? newContent + "%" : newContent + "%");
                                    calculoInicialFusa();
                                } else
                                {
                                    $(this).parent().text(esMoneda === "No" ? "0%" : "0%");
                                    alert("Debe Ingresar Valores entre cero (0) y cien (100)");
                                }
                            } else
                            {
                                console.log("no es moneda");
                            }
                        } else {
                            $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + "0" : "0" + "%");
                        }
                    }/*FIN ELSE NAN*/
                } catch (e)
                {
                    console.log("---> ERROR");
                    alert("Debe ingresar valores numericos");
                }
            });
            $(this).children().first().blur(function () {
                $(this).parent().removeClass("cellEditing");
                //$(this).parent().text(esMoneda === "Si" ? "$" + originalContent : originalContent + "%");
                $(this).parent().text(originalContent);
            });
        } else {
            console.log("nada42");
        }
    });

    $("td.pasajeros").off("click");
    $("td.pasajeros").click(function (e) {
        var idFila = $(this).parent().attr("id");
        var esPasajero = $('#dp' + idFila + ' span').text();
        if (!$(this).hasClass("cellEditing")) {
            if (esPasajero === "Si")
            {
                var originalContent = $(this).text();
                $(this).addClass("cellEditing");
                $(this).html("<input type='text' class='pasajero' value=" + originalContent + " />");
                $(this).children().first().select();
                $(this).children().first().focus();
                $(this).children().first().keypress(function (e) {
                    if (e.which === 13) {
                        var newContent = $(this).val();
                        try
                        {
                            if ((isNaN(newContent))) {
                                throw "No es un número.";
                            } else {
                                $(this).parent().removeClass("cellEditing");
                                if (newContent.trim() !== "") {
                                    $(this).parent().text(newContent);
                                    calculoInicialFusa();
                                } else {
                                    //$(this).parent().text(originalContent);
                                    $(this).parent().text("0");
                                }
                            }/*FN ELSE NAN*/
                        } catch (e)
                        {
                            alert("Debe ingresar un valor numérico.");
                        }
                    }
                });
                $(this).children().first().focusout(function () {
                    var newContent = $(this).val();

                    try
                    {
                        if ((isNaN(newContent))) {
                            throw "No es un número.";
                        } else {
                            $(this).parent().removeClass("cellEditing");
                            if (newContent.trim() !== "") {
                                $(this).parent().text(newContent);
                                calculoInicialNeiva();
                            } else {
                                //$(this).parent().text(originalContent);
                                $(this).parent().text("0");
                            }
                        }//FIN SINO NAN
                    } catch (e)
                    {
                        alert("Debe ingresar un valor numérico.");
                    }
                });
                $(this).children().first().blur(function () {
                    $(this).parent().removeClass("cellEditing");
                    $(this).parent().text(originalContent);
                });
            }
        }
    });

    $("td.observaciones").off("click");
    $("td.observaciones").click(function (e) {
        var originalContent = $(this).text();
        if (!$(this).hasClass("cellEditing")) {
            $(this).addClass("cellEditing");
            $(this).html("<input type='text' class='observaciones' value=" + originalContent + " />");
            $(this).children().first().select();
            $(this).children().first().focus();
            $(this).children().first().keypress(function (e) {
                if (e.which === 13) {
                    var newContent = $(this).val();
                    $(this).parent().removeClass("cellEditing");
                    if (newContent.trim() !== "") {
                        $(this).parent().text(newContent);
                    } else {
                        //$(this).parent().text(originalContent);
                        $(this).parent().text("");
                    }
                }
            });

            $(this).children().first().blur(function () {
                $(this).parent().removeClass("cellEditing");
                $(this).parent().text(originalContent);
            });
        }
    });
}
