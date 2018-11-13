/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var espaciosEnBlanco = /^\s*$/;

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
 * Función que reemplaza la primera letra por mayúscula y elimina las comillas dobles.
 */
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().replace(/^\w/, c => c.toUpperCase()).replace(/"/g, ""));
});


$(function () {
    $("#btnGuardar").click(function () {
        let errorNombreConfiguracion = document.getElementById("errorNombreConfiguracion");
        
        if (errorNombreConfiguracion !== null) {
            errorNombreConfiguracion.remove();
        }
        
        let nombreConfiguracionLiquidacion = $("#nombreConfiguracionLiquidacion").val();
        
        if (nombreConfiguracionLiquidacion === null || espaciosEnBlanco.test(nombreConfiguracionLiquidacion)) {
            $("#nombreConfiguracionLiquidacion").focus().after('<span class="error2" id="errorNombreConfiguracion">Inserte un nombre...</span>');  
            $("#nombreConfiguracionLiquidacion").select();
            return false;
        } else if (nombreConfiguracionLiquidacion.length > 40) {
            $("#nombreConfiguracionLiquidacion").focus().after('<span class="error2" id="errorNombreConfiguracion">Supera los 40 caracteres permitidos...</span>');  
            $("#nombreConfiguracionLiquidacion").select();
            return false;
        }
        
        let errorEtqPasajeros;
        let valorEtqPasajeros;
        
        for (let i = 1; i <= 5; i++) {
            errorEtqPasajeros = document.getElementById(`errorEtqPasajeros${i}`);
        
            if (errorEtqPasajeros !== null) {
                errorEtqPasajeros.remove();
            }
            
            valorEtqPasajeros = $(`#etqPasajeros${i}`).val();

            if (valorEtqPasajeros === null || espaciosEnBlanco.test(valorEtqPasajeros)) {
                $(`#etqPasajeros${i}`).val("No definido en configuración.");
            } else if (valorEtqPasajeros.length > 40) {
                $(`#etqPasajeros${i}`).focus().after('<span class="error2" id="errorNombreConfiguracion">Supera los 40 caracteres permitidos...</span>');  
                $(`#etqPasajeros${i}`).select();
                return false;
            }
        }
        
        let errorEtqTotal;
        let valorEtqTotal;
        
        for (let i = 1; i <= 8; i++) {
            errorEtqTotal = document.getElementById(`errorEtqTotal${i}`);
        
            if (errorEtqTotal !== null) {
                errorEtqTotal.remove();
            }
            
            valorEtqTotal = $(`#etqTotal${i}`).val();

            if (valorEtqTotal === null || espaciosEnBlanco.test(valorEtqTotal)) {
                $(`#etqTotal${i}`).val("No definido en configuración.");
            } else if (valorEtqTotal.length > 40) {
                $(`#etqTotal${i}`).focus().after(`<span class="error2" id="errorEtqTotal${i}">Supera los 40 caracteres permitidos...</span>`);  
                $(`#etqTotal${i}`).select();
                return false;
            }
        }
        
        let errorEtqRepPasajeros;
        let valorEtqRepPasajeros;
        
        for (let i = 1; i <= 5; i++) {
            errorEtqRepPasajeros = document.getElementById(`errorEtqRepPasajeros${i}`);
            
            if (errorEtqRepPasajeros !== null) {
                errorEtqRepPasajeros.remove();
            }
            
            valorEtqRepPasajeros = $(`#etqRepPasajeros${i}`).val();

            if (valorEtqRepPasajeros === null || espaciosEnBlanco.test(valorEtqRepPasajeros)) {
                $(`#etqRepPasajeros${i}`).focus().after(`<span class="error2" id="errorEtqRepPasajeros${i}">Inserte un valor...</span>`);  
                $(`#etqRepPasajeros${i}`).select();
                return false;
            } else if (valorEtqRepPasajeros.length > 40) {
                $(`#etqRepPasajeros${i}`).focus().after(`<span class="error2" id="errorEtqRepPasajeros${i}">Supera los 40 caracteres permitidos...</span>`);  
                $(`#etqRepPasajeros${i}`).select();
                return false;
            }
        }
        
        let errorEtqRepTotal;
        let valorEtqRepTotal;
        
        for (let i = 1; i <= 8; i++) {
            errorEtqRepTotal = document.getElementById(`errorEtqRepTotal${i}`);
            
            if (errorEtqRepTotal !== null) {
                errorEtqRepTotal.remove();
            }
            
            valorEtqRepTotal = $(`#etqRepTotal${i}`).val();

            if (valorEtqRepTotal === null || espaciosEnBlanco.test(valorEtqRepTotal)) {
                $(`#etqRepTotal${i}`).focus().after(`<span class="error2" id="errorEtqRepTotal${i}">Inserte un valor...</span>`);  
                $(`#etqRepTotal${i}`).select();
                return false;
            } else if (valorEtqRepTotal.length > 40) {
                $(`#etqRepTotal${i}`).focus().after(`<span class="error2" id="errorEtqRepTotal${i}">Supera los 40 caracteres permitidos...</span>`);  
                $(`#etqRepTotal${i}`).select();
                return false;
            }
        }
        
        let radiosModoLiquidacion = [...document.getElementsByName("modoLiquidacion")];
        let checked = false;

        for (let i = 0; i < radiosModoLiquidacion.length; i++) {

            if (radiosModoLiquidacion[i].checked) {
                checked = true;
                break;
            }
        }

        if (!checked) {
            alert("Seleccione una opción de modo de liquidación.");
            return false;
        }
        
        var errorPerfil = document.getElementById("errorPerfil");
        
        if (errorPerfil !== null) {
            errorPerfil.remove();
        }
        
        var perfil = $("#perfil").val();
        
        if (perfil !== null && perfil <= 0) {
            $("#perfil").focus().after('<span class="error2" id="errorPerfil">Seleccione un perfil.</span>');  
            $("#perfil").select();
            return false;
        }
        
        let radiosEstado = [...document.getElementsByName("estado")];
        checked = false;

        for (let i = 0; i < radiosEstado.length; i++) {

            if (radiosEstado[i].checked) {
                checked = true;
                break;
            }
        }

        if (!checked) {
            alert("Seleccione un estado.");
            return false;
        }
        
        return true;
    });
});
