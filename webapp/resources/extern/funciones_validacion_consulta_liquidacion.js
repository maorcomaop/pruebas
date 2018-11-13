$(function () {
    var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    var numbers = /^([0-9])/;
    var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
    


    $(".botonConsultaLiquidacion").click(function () {
        $(".error").fadeOut().remove();
        $(".error2").fadeOut().remove();

        /*if ($("#car").val() === "0")
        {
            $("#car").focus().after('<span class="error2">Seleccione la placa del vehiculo</span>');
            $("#car").select();
            return false;
        }*/

        if ($("#dpd1").val() === "") {
            $("#dpd1").focus().after('<span class="error">Seleccione una fecha inicial</span>');
            $("#dpld").select();
            return false;
        }

        if ($("#dpd2").val() === "") {
            $("#dpd2").focus().after('<span class="error">Seleccione una fecha final</span>');
            $("#dpd2").select();
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