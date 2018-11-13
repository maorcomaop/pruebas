/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tooltipVencimientoDoc = "Días de anticipación al vencimiento de la documentación.";
var tooltipHorasTrabajadas = "Cantidad de horas trabajadas para recibir la primera notificación";
var tooltipKmRecorridos = "Cantidad de kilómetros recorridos para recibir la primera notificación.";
var tooltipDefault = "Cantidad de unidades con las cuales se generan las notificaciones del mantenimiento.";

/**
 * Esta función valores cuando la página ya ha cargado en el navegador.
 */
$(document).ready(function ()
{
    $('#tableMantenimientos').DataTable({
        aLengthMenu: [600, 1000],
        scrollY: 500,
        scrollX: true,
        searching: true,
        bAutoWidth: false,
        bInfo: false,
        paging: false,
        language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
    });
    
    cargarValoresRadio();
    actualizarLabelUnidadMedida();
    establecerFechaActivacion();
});

/**
 * Muestra un mensaje de confirmación para inactivar/eliminar registros.
 * @returns Retorna true si el usuario confirma que desea inactivar/eliminar el registro seleccionado.
 */
$(function () {
    $(".eliminar").click(function () {
        var mensaje = confirm("¿Desea desactivar el registro?");
        
        if (mensaje) {
            return true;
        } else {
            return false;
        }
    });
});

/**
 * Evento del select de tipo de evento que cambia la unidad de medida.
 */
$(function () {
    $("#tipoEventoMantenimiento").change(function () {
        actualizarLabelUnidadMedida();
    });
});

/**
 * Establece la fecha actual en el input de fecha de activación si no se ha asignado una fecha previamente.
 */
function establecerFechaActivacion() {
    var fechaActivacion = $("#fechaActivacion").val();
    
    if (fechaActivacion == null || fechaActivacion.length == 0) {
        $('#fechaActivacion').val(getDate().toLocaleLowerCase());
    }
}

/**
 * Evento que cambia el texto en el campo de unidad de medida.
 */
$("#tipoEventoMantenimiento").change(function () {
    cargarTipoEventoMantenimiento();
});

/**
 * Se utiliza para cambiar a mayúscula los valores de los input:text
 */
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().toUpperCase());
});

/**
 * Evento que activa e inactiva campos de acuerdo a la selección de los radios.
 */
$("input:radio").click(function() {
    cargarValoresRadio();
});

/**
 * Actualiza el texto del label de la unidad de medida del tipo de evento de mantenimiento.
 */
function actualizarLabelUnidadMedida() {
    $("#lblUnidadMedida").text("Unidad de Medida *");

    $.ajax({
        url: '/RDW1/cargarUnidadMedida',
        method: "POST",
        dataType: "json",
        data: {
            tipoEventoMantenimiento: $("#tipoEventoMantenimiento").val()
        },
        success: function (data) {

            if (data.response == "OK") {
                $("#lblUnidadMedida").text(data.unidadMedida + " *");
                
                switch(data.id) {
                    case 1:
                        document.getElementById("unidades").title = tooltipVencimientoDoc;
                        break;
                    case 2:
                        document.getElementById("unidades").title = tooltipHorasTrabajadas;
                        break;
                    case 3:
                        document.getElementById("unidades").title = tooltipKmRecorridos;
                        break;
                    default:
                    document.getElementById("unidades").title = tooltipDefault;
                }
            } else {
                $("#lblUnidadMedida").text("Unidad de Medida *");
            }
        }
    });
}

/**
 * Valida que los campos obligatorios tengan valores asignados al momento de guardar un registro.
 */
$(function () {
    $("#btnGuardar").click(function () {
        var errorTipoEventoMantenimiento = document.getElementById("errorTipoEventoMantenimiento");
        
        if (errorTipoEventoMantenimiento != null) {
            errorTipoEventoMantenimiento.parentNode.removeChild(errorTipoEventoMantenimiento);
        }
        
        var fkTipoEventoMantenimiento = $("#tipoEventoMantenimiento").val();
        
        if (fkTipoEventoMantenimiento != null && fkTipoEventoMantenimiento <= 0) {
            $("#tipoEventoMantenimiento").focus().after('<span class="error2" id="errorTipoEventoMantenimiento">Seleccione un tipo de evento.</span>');  
            $("#tipoEventoMantenimiento").select();
            return false;
        }
        
        var fechaActivacion = $("#fechaActivacion").val();
        
        if (fechaActivacion == null || fechaActivacion.length == 0 || fechaActivacion == "") {
            $("#fechaActivacion").val(new Date());
        }
        
        var errorUnidaMedida = document.getElementById("errorUnidaMedida");
        
        if (errorUnidaMedida != null) {
            errorUnidaMedida.parentNode.removeChild(errorUnidaMedida);
        }
        
        var unidades = new Number($("#unidades").val());
        
        if (unidades == null || unidades == Number.NaN || unidades <= 0 || unidades > 9999999) {
            $("#unidades").focus().after('<span class="error2" id="errorUnidaMedida">Cantidad no válida.</span>');  
            $("#unidades").select();
            return false;
        } 
        
        var errorRepeticion = document.getElementById("errorRepeticion");

        if (errorRepeticion != null) {
            errorRepeticion.parentNode.removeChild(errorRepeticion);
        }
        
        var radios = document.getElementsByName("repeticion");
        var radioValue = null;
        
        if (radios != null && radios.length > 0) {
            var checked = false;
            
            for (var i = 0; i < radios.length; i++) {
                
                if (radios[i].checked) {
                    checked = true;
                    radioValue = $("#" + $(radios[i]).attr("id")).val();
                    break;
                }
            }
            
            if (!checked) {
                $("#lblRepeticion").focus().after('<span class="error2" id="errorRepeticion">Seleccione un opción.</span>');
                $("#lblRepeticion").select();
                return false;
            }
        }
        
        var errorIntervalo = document.getElementById("errorIntervalo");
        
        if (errorIntervalo != null) {
            errorIntervalo.parentNode.removeChild(errorIntervalo);
        }
        
        var intervalo = new Number($("#intervalo").val());
        
        if (intervalo == null || intervalo == Number.NaN || intervalo < 0 || intervalo > 525600) {
            $("#intervalo").focus().after('<span class="error2" id="errorIntervalo">Cantidad no válida.</span>');  
            $("#intervalo").select();
            return false;
        } 
        
        if (radioValue == "1" && intervalo <= 0) {
            $("#intervalo").focus().after('<span class="error2" id="errorIntervalo">Repetición es cíclica, definir intervalo.</span>');  
            $("#intervalo").select();
            return false;
        }
        
        var errorCantidadNotificaciones = document.getElementById("errorCantidadNotificaciones");
        
        if (errorCantidadNotificaciones != null) {
            errorCantidadNotificaciones.parentNode.removeChild(errorCantidadNotificaciones);
        }
        
        var cantidadNotificaciones = new Number($("#cantidadNotificaciones").val());
        
        if (cantidadNotificaciones == null || cantidadNotificaciones == Number.NaN || 
                cantidadNotificaciones < 0 || cantidadNotificaciones > 10) {
            $("#cantidadNotificaciones").focus().after('<span class="error2" id="errorCantidadNotificaciones">Cantidad no válida.</span>');  
            $("#cantidadNotificaciones").select();
            return false;
        } 
        
        var errorActivarMantenimiento = document.getElementById("errorActivarMantenimiento");

        if (errorActivarMantenimiento != null) {
            errorActivarMantenimiento.parentNode.removeChild(errorActivarMantenimiento);
        }
        
        var radiosActivar = document.getElementsByName("activarMantenimiento");
        var radioActivarValue = null;
        
        if (radiosActivar != null && radiosActivar.length > 0) {
            var checked = false;
            
            for (var i = 0; i < radiosActivar.length; i++) {
                
                if (radiosActivar[i].checked) {
                    checked = true;
                    radioActivarValue = $("#" + $(radiosActivar[i]).attr("id")).val();
                    break;
                }
            }
            
            if (!checked) {
                $("#lblActivarMantenimiento").focus().after('<span class="error2" id="errorActivarMantenimiento">Seleccione una opción.</span>');
                $("#lblActivarMantenimiento").select();
                return false;
            }
        }
        
        var errorEmail = document.getElementById("errorEmail");
        
        if (errorEmail != null) {
            errorEmail.parentNode.removeChild(errorEmail);
        }
        
        var correos = $("#correos").val();
        
        if (correos != null && correos.length > 2000) {
            $("#correos").focus().after('<span class="error2" id="errorEmail">Limite de caracteres superado.</span>');
            $("#correos").select();
            return false;
        } else {
            correos = correos.split(',');

            if (correos.length > 0) {

                for (var i = 0; i < correos.length; i++) {

                    if (correos[i].length > 0 && !validateEmail(correos[i])) {
                        $("#correos").focus().after('<span class="error2" id="errorEmail">' + correos[i] + ' no válido.</span>');
                        $("#correos").select();
                        return false;
                    }
                }
            }
        }
        
        var errorObservaciones = document.getElementById("errorObservaciones");

        if (errorObservaciones != null) {
            errorObservaciones.parentNode.removeChild(errorObservaciones);
        }
        
        var observaciones = $("#observaciones").val();
        
        if (observaciones != null && observaciones.length > 2000) {
            $("#observaciones").focus().after('<span class="error2" id="errorFinVigencia">Límite de caracteres superado.</span>');  
            $("#observaciones").select();
            return false;
        }
        
        if (radioValue == "0") {
            $("#intervalo").val(0);
            $("#cantidadNotificaciones").val(1);
        }
        
        return true;
    });
});

/**
 * Valida si la cadena que se pasa como parámetro corresponde a una direccion de correo electrónico.
 * @param {string} email Cadena que se pasa como parámetro para validar si es una dirección de correo eletrónica valida.
 * @returns {Boolean} Retorna true si la cadena tiene el formato de una dirección de correo electrónico.
 */
function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

/**
 * Valida si el usuario seleccionó que las notificaciones del mantenimiento se repita una única vez o varias veces.
 * Si selecciona que sea sólo una vez, se inhabilitan los campos de tiempo y cantidad de notificaciones.
 * Si selecciona que sean varias notificaciones, se habilitan los campos de tiempo y cantidad de notificaciones.
 * @returns {undefined}
 */
function cargarValoresRadio() {
    var radios = document.getElementsByName("repeticion");
    var radioValue = null;

    if (radios != null && radios.length > 0) {
        
        for (var i = 0; i < radios.length; i++) {

            if (radios[i].checked) {
                checked = true;
                radioValue = $("#" + $(radios[i]).attr("id")).val();
                break;
            }
        }
    }
    
    if (radioValue == 0) {
        $("#intervalo").prop("readonly", true);
        $("#cantidadNotificaciones").prop("readonly", true);
    } else if (radioValue == 1) {
        $("#intervalo").prop("readonly", false);
        $("#cantidadNotificaciones").prop("readonly", false);
    }
}

/**
 * Habilita e inhabilita los campos de cantidad de notificaciones.
 * Si el tipo de mantenimiento es VENCIMIENTO DE DOCUMENTACION, las notificaciones se repiten mas de una vez.
 * Si el algun otro, el usuario puede seleccionar si las notificaciones se muestran una o varias veces.
 * @returns {undefined}
 */
function cargarTipoEventoMantenimiento() {
    var tipoEventoMantenimiento = $("#tipoEventoMantenimiento").val();
    
    if (tipoEventoMantenimiento == "1") {
        $("#estatico").prop("disabled", true);
        $("#ciclico").prop("checked", true);
    } else {
        $("#estatico").prop("disabled", false);
    }
    
    cargarValoresRadio();
}
