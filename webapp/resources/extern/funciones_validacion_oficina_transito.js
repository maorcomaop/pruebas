/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    $("#btnGuardar").click(function () {
        var fk_ciudad = $("#ciudad").val();
        
        if (fk_ciudad > 0) {
            return true;
        } else {
             $("#ciudad").focus().after('<span class="error2">Seleccione una ciudad.</span>');  
            $("#ciudad").select();
            return false;
        }
    });
});


// Tabla dinamica
$(document).ready(function ()
{
    $('#tableOficinasTransito').DataTable({
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
        var mensaje = confirm("¿Desea eliminar el registro?");
        if (mensaje) {
            console.log("¡Gracias por aceptar!");
            return true;
        } else {
            return false;
        }
    });
});