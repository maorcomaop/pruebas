
var tablaDescuentos;
var data = {};
$(document).ready(function () {

    $.ajaxSetup({cache: false});
    //console.log("entering...");
    calculoInicialNeiva();

    var firstClick = false;
    $('#descuentosTabN').click(function () {
        if (!firstClick) {
            var sc = $('#tableDescuentos_wrapper table.dataTable thead .sorting_asc');
            $(sc).trigger("click");
            $(sc).trigger("click");
            firstClick = true;
        }
    });
    //COMBO QUE SE ENCUNTRA EN LA PARTE SUPERIOR DEL FORMULARIO  
    console.log("#addCategoria");
    $("#addCategoria").change(function () {
        var idCombo = $(this).attr('id');
        //console.log("---> "+idCombo);
        if ($(this).val() > 0) {
            $.post("/RDW1/encontrarCategoriasDeDescuento", {id: $(this).val()}, function (result) {

                if ($.fn.dataTable.isDataTable('#tableDescuentos')) {

                    var oTable = $('#tableDescuentos').DataTable();
                    if (idCombo.indexOf("cobx") !== -1) {
                        oTable.row($("#" + idCombo).parents('tr')).remove().draw();
                    }
                    oTable.row.add($(result)[0]).draw();

                }
            });
            $('#' + idCombo).selectpicker('val', 0);
        }
    });
    //COMBOS POR FILA DE LA TABLA
    $(".categoria-descuento select.selectpicker").change(function () {
        var idCombo = $(this).attr('id');
        if ($(this).val() > 0) {
            $.post("/RDW1/encontrarCategoriasDeDescuento", {id: $(this).val()}, function (result) {
                if ($.fn.dataTable.isDataTable('#tableDescuentos')) {
                    oTable = $('#tableDescuentos').DataTable();
                    if (idCombo.indexOf("cobx") !== -1) {
                        oTable.row($("#" + idCombo).parents('tr')).remove().draw();
                    }
                    oTable.row.add($(result)[0]).draw();
                }
            });
            $('#' + idCombo).selectpicker('val', 0);
        }
    });

    configuarEventosTablaDescuentosNeiva();
});//FIN READY

//FUNCION QUE GUARDA LA LIQUIDACION
function guardarLiquidacion(button) {
    if (parseInt($("#btn-vehiculo-conductor").attr("data-co-id")) <= 0) {
        alert("Seleccione un Conductor");
        return;
    }
    $(button).prop('disabled', true);
    //funcion que obtiene los datos de la tabla descuentos
    obtenerDatos();
    //se obtiene la tarifa
    var sTarifa = parseFloat($("#comboTarifaN option:selected").text());
    //se obtiene la cantidad de pasajeros.
    var sCantidadPasajerosDescontados = parseFloat($("#psjrsDsctdsN").val());
    //liquidacion general
    data.fkTarifaFija = $("#comboTarifaN").val();
    data.fkVehiculo = $("#id_vh_lqN").val();
    data.fkConductor = $("#btn-vehiculo-conductor").attr("data-co-id");
    data.totalPasajeros = $("#psjrsRgstdsN").val();
    data.totalPasajerosLiquidados = $("#ttlpsjrsN").val();
    data.totalValorVueltas = $("#sbttpaxN").val();
    //data.totalValorVueltas = $("#vlrntpsjrsN").val();
    data.totalValorDescuentosPasajeros = parseFloat(sCantidadPasajerosDescontados * sTarifa);
    data.totalValorDescuentosAdicional = $("#vlrttldctsN").val();
    data.totalValorOtrosDescuentos = $("#vlrttlotrdctsN").val();
    data.distancia = $("#distanciaRecorridaN").val();
    //calculo de IPK
    if (parseFloat($("#distanciaRecorridaN").val()) > 0) {
        data.ipk = (parseFloat($("#ttlpsjrsN").val()) / parseFloat($("#distanciaRecorridaN").val()));
    } else {
        data.ipk = 0;
    }
    data.cantidadPasajerosDescontados = sCantidadPasajerosDescontados;
    var json = JSON.stringify(data);
    $.post("/RDW1/guardarLiquidacion", 
        {
            json: json, 
            fecha_hora_inicio: $("#dpd3").val(),
            fecha_hora_fin: $("#dpd4").val(),
            fecha_inicio: $("#dpd1").val(),
            fecha_fin: $("#dpd2").val()
        }, 
        function (data) {
        var messageDiv = $("#msg-liquidation");
        messageDiv.css("display", "inline");
        //console.log("---> "+data);
        if (data.saved) {
            messageDiv.addClass("alert alert-success");
            messageDiv.html("Liquidación guardada exitosamente");
            $("#export-btn").addClass("obfuscate");

            // auto opening invoice nust generated 
            var pkLiquidacion = data.response[0].id;
            var fkVehiculo = data.response[0].fkVehiculo;
            //console.log(pkLiquidacion+", "+fkVehiculo);
            var formAuxHTML = "<form id='form-invoice-id' action='/RDW1/verReciboLiquidacion' method='post' target='_blank'>\n\
                                                    <input type='hidden' name='id' value='" + fkVehiculo + "'>\n\
                                                    <input type='hidden' name='pk_liquidacion' value='" + pkLiquidacion + "'>\n\
                                                    <input type='submit' class='btn  btn-xs  btn-info' value='Recibo'></form>";
            var formAuxDiv = $("#html-form-invoice");
            //console.log("---> "+formAuxHTML);
            formAuxDiv.html(formAuxHTML);
            document.getElementById('form-invoice-id').submit();
        } else {
            $(this).prop('disabled', false);
            messageDiv.addClass("alert alert-warning");
            messageDiv.html("Liquidación no guardada");
        }
    }, 'json').done(function (data) {
        console.log("LIQUIDACION GUARDADA");
    });//FIN POST            
}//FIN FUNCION    
//FUNCION QUE OBTIENE LOS DATOS DE TABLA DE DESCUENTOS    
function obtenerDatos() {

    data.descuentosInfo = [];
    $("#tableDescuentos tbody tr").each(function (index) {
        var campo0, campo1, campo2, campo3, campo4, campo5, campo6, campo7;
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
            }//fin switch

            $(this).css("background-color", "#ECF8E0");
        });//FIN FUNCION EACH

        data.descuentosInfo.push(hCampos);
    });//FIN EACH TABLE
}
//FUNCION QUE SE ENCARGA DE LOS EVENTOS DE BORRADO Y ADICION DE FILAS EN TABLA
function configuarEventosTablaDescuentosNeiva() {

    $('.selectpicker').selectpicker({
        style: 'btn-primary',
        size: 4,
        liveSearch: true
    });

    $('.selectpicker').selectpicker('setStyle', 'btn-xs', 'add');

    $('.table-remove').off("click");
    $('.table-remove').click(function () {
        if ($.fn.dataTable.isDataTable('#tableDescuentos')) {
            var oTable = $('#tableDescuentos').DataTable();
            oTable.row($(this).parents('tr')).remove().draw();
            calculoInicialNeiva();
        }
    });

    $("td").off("mouseleave");
    //Cambia el color de las celdas cuando sale el mouse
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
    //Cambia el color de las celdas cuando entra el mouse
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
                        if ((isNaN(newContent)))
                        {
                            throw "no es un numero";
                        } else
                        {
                            newContent = newContent.replace($("#simboloMoneda").val(), "");
                            $(this).parent().removeClass("cellEditing");
                            if (newContent.trim() !== "") {
                                if (esMoneda === "Si")
                                {
                                    if (newContent >= 0)
                                    {
                                        console.log("es contenido valido");
                                        $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + newContent : $("#simboloMoneda").val() + " " + newContent);
                                        calculoInicialNeiva();
                                    } else
                                    {
                                        $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " 0" : $("#simboloMoneda").val() + " 0");
                                        alert("Debe Ingresar Valores mayores de cero (0)");

                                    }
                                } else
                                {
                                    console.log("no es moneda");

                                }

                                //**********************************************************************************
                                if (esMoneda === "No")
                                {
                                    if ((newContent >= 0) && (newContent <= 100))
                                    {
                                        console.log("es contenido valido");
                                        $(this).parent().text(esMoneda === "No" ? newContent + "%" : newContent + "%");
                                        calculoInicialNeiva();
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
                                $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " 0" : $("#simboloMoneda").val() + " 0");
                            }
                        }//FIN ELSE NAN
                    } catch (e)
                    {
                        console.log("---> ERROR");
                        alert("Debe ingresar valores numericos");
                    }
                }

            });
            $(this).children().first().focusout(function () {
                var newContent = $(this).val();

                try
                {
                    if ((isNaN(newContent)))
                    {
                        throw "no es un numero";
                    } else
                    {
                        newContent = newContent.replace($("#simboloMoneda").val(), "");
                        $(this).parent().removeClass("cellEditing");
                        if (newContent.trim() !== "") {
                            if (esMoneda === "Si")
                            {
                                if (newContent >= 0)
                                {
                                    console.log("es contenido valido");
                                    $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + newContent : $("#simboloMoneda").val() + " " + newContent);
                                    calculoInicialNeiva();
                                } else
                                {
                                    $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " 0" : $("#simboloMoneda").val() + " 0");
                                    alert("Debe Ingresar Valores mayores de cero (0)");
                                }
                            } else
                            {
                                console.log("no es moneda");
                            }

                            //**********************************************************************************
                            if (esMoneda === "No")
                            {
                                if ((newContent >= 0) && (newContent <= 100))
                                {
                                    console.log("es contenido valido");
                                    $(this).parent().text(esMoneda === "No" ? newContent + "%" : newContent + "%");
                                    calculoInicialNeiva();
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
                            $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " 0" : $("#simboloMoneda").val() + " 0");
                        }
                    }//FIN ELSE NAN
                } catch (e)
                {
                    console.log("---> ERROR");
                    alert("Debe ingresar valores numericos");
                }
            });
            $(this).children().first().blur(function () {
                $(this).parent().removeClass("cellEditing");
                $(this).parent().text(esMoneda === "Si" ? $("#simboloMoneda").val() + " " + originalContent : originalContent + "%");
            });
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
