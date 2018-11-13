/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var espaciosEnBlanco = /^\s*$/;

// Tabla dinamica
$(document).ready(function ()
{
    $('.display').DataTable({
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


/*Controla el acceso al boton eliminar del listado*/
$(function () {
    $(".eliminar").click(function () {
        confirmarEliminacion();
    });
});


function confirmarEliminacion() {
    var mensaje = confirm("¿Desea desactivar el registro?");

    if (mensaje) {
        return true;
    } else {
        return false;
    }
}


$(function () {
    $("#btnGuardar").click(function () {
        var errorNombre = document.getElementById("errorNombre");
        
        if (errorNombre != null) {
            errorNombre.parentNode.removeChild(errorNombre);
        }
        
        var nombre = $("#nombre").val();
        
        if (nombre == null || espaciosEnBlanco.test(nombre)) {
            $("#nombre").focus().after('<span class="error2" id="errorNombre">Ingrese un nombre válido.</span>');  
            $("#nombre").select();
            return false;
        } else if (nombre.length > 250) {
            $("#nombre").focus().after('<span class="error2" id="errorNombre">Supera los 250 caracteres permitidos.</span>');  
            $("#nombre").select();
            return false;
        }
        
        var errorDescripcion = document.getElementById("errorDescripcion");
        
        if (errorDescripcion != null) {
            errorDescripcion.parentNode.removeChild(errorDescripcion);
        }
        
        var descripcion = $("#descripcion").val();
        
        if (descripcion == null || espaciosEnBlanco.test(descripcion)) {
            $("#descripcion").focus().after('<span class="error2" id="errorVehiculo">Ingrese una descripción válida.</span>');  
            $("#descripcion").select();
            return false;
        } else if (descripcion.length > 1000) {
            $("#descripcion").focus().after('<span class="error2" id="errorVehiculo">Supera los 1000 caracteres permitidos.</span>');  
            $("#descripcion").select();
            return false;
        }
        
        return true;
    });
});

/*Se utiliza para cambiar a mayúscula los valores de los input:text*/
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().trim().toUpperCase());
});