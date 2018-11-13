/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Tabla dinamica
$(document).ready(function ()
{
    $('#tableLicenciaTransito').DataTable({
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


/*Se utiliza para cambiar a mayúscula los valores de los input:text*/
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().toUpperCase());
});


$(function () {
    $("#btnGuardar").click(function () {
        var element = document.getElementById("aseguradoraError");
        
        if (element != null) {
            element.parentNode.removeChild(element);
        }
        
        var fk_aseguradora = $("#aseguradora").val();
        
        if (fk_aseguradora <= 0) {
            $("#aseguradora").focus().after('<span class="error2" id="aseguradoraError">Seleccione una aseguradora.</span>');  
            $("#aseguradora").select();
            return false;
        }
        
        var errorTipoPoliza = document.getElementById("errorTipoPoliza");
        
        if (errorTipoPoliza != null) {
            errorTipoPoliza.parentNode.removeChild(errorTipoPoliza);
        }
        
        var fkTipoPoliza = $("#tipoPoliza").val();
        
        if (fkTipoPoliza != null && fkTipoPoliza <= 0) {
            $("#tipoPoliza").focus().after('<span class="error2" id="errorTipoPoliza">Seleccione un tipo de póliza.</span>');  
            $("#tipoPoliza").select();
            return false;
        }
                
        return true;
    });
});