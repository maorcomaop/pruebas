+/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
        var mensaje = confirm("¿Desea eliminar el registro?");
        if (mensaje) {
            console.log("¡Gracias por aceptar!");
            return true;
        } else {
            return false;
        }
    });
});


$(function () {
    $("#btnGuardar").click(function () {
        var errorNombre = document.getElementById("errorNombre");
        
        if (errorNombre != null) {
            errorNombre.parentNode.removeChild(errorNombre);
        }
        
        var nombre = $("#nombre").val();
        
        if (nombre == null || nombre.length == 0 || nombre.length > 250) {
            $("#nombre").focus().after('<span class="error2" id="errorNombre">Nombre no válido.</span>');  
            $("#nombre").select();
            return false;
        }
        
        var errorUnidadMedida = document.getElementById("errorUnidadMedida");
        
        if (errorUnidadMedida != null) {
            errorUnidadMedida.parentNode.removeChild(errorUnidadMedida);
        }
        
        var unidadMedia = $("#unidadMedida").val();
        
        if (unidadMedia == null || unidadMedia.length == 0 || unidadMedia.length > 50) {
            $("#unidadMedida").focus().after('<span class="error2" id="errorUnidadMedida">Unidad de medida no válida..</span>');  
            $("#unidadMedida").select();
            return false;
        }
        
        var errorDescripcion = document.getElementById("errorDescripcion");
        
        if (errorDescripcion != null) {
            errorDescripcion.parentNode.removeChild(errorDescripcion);
        }
        
        var descripcion = $("#descripcion").val();
        
        if (descripcion != null && descripcion.length > 2000) {
            $("#descripcion").focus().after('<span class="error2" id="errorDescripcion">Limite de 2000 caracteres.</span>');  
            $("#descripcion").select();
            return false;
        } 
        
        return true;
    });
});

/*Se utiliza para cambiar a mayúscula los valores de los input:text*/
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().toUpperCase());
});