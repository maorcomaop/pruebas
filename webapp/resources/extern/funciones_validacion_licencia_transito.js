/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    $("#btnGuardar").click(function () {
        var fk_conductor = $("#conductor").val();
        
        if (fk_conductor <= 0) {
            $("#conductor").focus().after('<span class="error2">Seleccione un conductor.</span>');  
            $("#conductor").select();
            return false;
        }
        
        var fk_categoria = $("#categoria").val();
        
        if (fk_categoria <= 0) {
            $("#categoria").focus().after('<span class="error2">Seleccione una categoria.</span>');  
            $("#categoria").select();
            return false;
        }
          
        var fk_oficina = $("#oficina").val();
        
        if (fk_oficina <= 0) {
            $("#oficina").focus().after('<span class="error2">Seleccione una oficina de tránsito.</span>');  
            $("#oficina").select();
            return false;
        }
        
        var fechaActual = new Date();
        var fechaExpedicion = new Date($("#fechaExpedicion").val());
        var vigencia = new Date($("#vigencia").val());
        
        var element = document.getElementById("fechaExpecionError");

        if (element != null) {
            element.parentNode.removeChild(element);
        }
        
        if (fechaExpedicion > fechaActual) {
            $("#fechaExpedicion").focus().after('<span class="error2" id="fechaExpecionError">Fecha de expedición mayor a la fecha actual.</span>');  
            $("#fechaExpedicion").select();
            return false;
        }
        
        if (fechaExpedicion > vigencia) {
            $("#fechaExpedicion").focus().after('<span class="error2">Fecha de expedición mayor a la vigencia.</span>');  
            $("#fechaExpedicion").select();
            return false;
        }
        
        return true;
    });
});



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