$(function () {
    var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    var numbers = /^([0-9])/;
    var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;


    $(".botonLiquidacion").click(function () {
        $(".error").fadeOut().remove();
        $(".error2").fadeOut().remove();

        if ($("#company").val() === "0") {
            $("#company").focus().after('<span class="error2">Seleccione la empresa</span>');
            return false;
        }
        if ($("#liquidador").val() === "0") {
            $("#liquidador").focus().after('<span class="error2">Seleccione el liquidador</span>');
            return false;
        }
        if ($("#cedula").val() === "0") {
            $("#cedula").focus().after('<span class="error2">Seleccione la cedula</span>');
            return false;
        }
        if ($("#tipoReporte").val() === "0") {
            $("#tipoReporte").focus().after('<span class="error2">Seleccione el tipo de reporte</span>');
            return false;
        }

        if ($("#dpd1").val() === "") {
            $("#dpd1").focus().after('<span class="error">Seleccione una fecha inicial</span>');
            return false;
        }

        if ($("#dpd2").val() === "") {
            $("#dpd2").focus().after('<span class="error">Seleccione una fecha final</span>');
            return false;
        }

    });


    $("#car, #dpd1, #dpd2").bind('blur keyup', function () {
        if ($(this).val() !== "") {
            $('.error').fadeOut();
            $(".error2").fadeOut();
            return false;
        }
    });

});