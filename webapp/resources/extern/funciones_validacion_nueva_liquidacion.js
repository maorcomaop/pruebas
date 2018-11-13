$(function () {
    var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    var numbers = /^([0-9])/;
    var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;


    $(".botonNuevaLiquidacion").click(function () {
        $(".error").fadeOut().remove();
        $(".error2").fadeOut().remove();

        if ($("#placa_vehiculo").val() === "0")
        {
            $("#placa_vehiculo").focus().after('<span class="error2">Seleccione la placa del veh\u00EDculo</span>');
            $("#placa_vehiculo").select();
            return false;
        }

        if ($("#numero_interno").val() === "0")
        {
            $("#numero_interno").focus().after('<span class="error2">Seleccione el n\u00FAmero interno del veh\u00EDculo</span>');
            $("#numero_interno").select();
            return false;
        }

        if ($("#dpd1").val() === "") {
            $("#dpd1").focus().after('<span class="error">Seleccione una fecha inicial</span>');
            $("#dpdl").select();
            return false;
        }

        if ($("#dpd2").val() === "") {
            $("#dpd2").focus().after('<span class="error">Seleccione una fecha final</span>');
            $("#dpd2").select();
            return false;
        }
    });


    $("#placa_vehiculo, #numero_interno, #dpd1, #dpd2").bind('blur keyup change', function () {
        if ($(this).val() !== "") {
            $('.error').fadeOut();
            $(".error2").fadeOut();
            return false;
        }
    });

});