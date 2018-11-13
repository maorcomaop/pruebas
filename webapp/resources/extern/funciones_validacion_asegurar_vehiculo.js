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


$(function () {
    $("#btnGuardar").click(function () {
        var errorPoliza = document.getElementById("errorPoliza");
        
        if (errorPoliza != null) {
            errorPoliza.parentNode.removeChild(errorPoliza);
        }
        
        var fkPoliza = $("#poliza").val();
        
        if (fkPoliza != null && fkPoliza <= 0) {
            $("#poliza").focus().after('<span class="error2" id="errorPoliza">Seleccione una póliza.</span>');  
            $("#poliza").select();
            return false;
        }
        
        var errorVehiculo = document.getElementById("errorVehiculo");
        
        if (errorVehiculo != null) {
            errorVehiculo.parentNode.removeChild(errorVehiculo);
        }
        
        var fkVehiculo = $("#vehiculo").val();
        
        if (fkVehiculo != null && fkVehiculo <= 0) {
            $("#vehiculo").focus().after('<span class="error2" id="errorVehiculo">Seleccione un vehículo.</span>');  
            $("#vehiculo").select();
            return false;
        }
        
        var errorNumeroPoliza = document.getElementById("errorNumeroPoliza");
        
        if (errorNumeroPoliza != null) {
            errorNumeroPoliza.parentNode.removeChild(errorNumeroPoliza);
        }
        
        var numeroPoliza = $("#numeroPoliza").val();
        
        if (numeroPoliza == null || numeroPoliza.length == 0 || numeroPoliza.length > 50) {
            $("#numeroPoliza").focus().after('<span class="error2" id="errorVehiculo">Ingrese el número de póliza.</span>');  
            $("#numeroPoliza").select();
            return false;
        } 
        
        var errorInicioVigencia = document.getElementById("errorInicioVigencia");

        if (errorInicioVigencia != null) {
            errorInicioVigencia.parentNode.removeChild(errorInicioVigencia);
        }
        
        var fechaInicio = $("#inicioVigencia").val();
        
        if (fechaInicio == "") {
            $("#inicioVigencia").focus().after('<span class="error2" id="errorInicioVigencia">Inicio de vigencia vacío.</span>');  
            $("#inicioVigencia").select();
            return false;
        }
        
        var errorFinVigencia = document.getElementById("errorFinVigencia");

        if (errorFinVigencia != null) {
            errorFinVigencia.parentNode.removeChild(errorFinVigencia);
        }
        
        var fechaFin = $("#finVigencia").val();
        
        if (fechaFin == "") {
            $("#finVigencia").focus().after('<span class="error2" id="errorFinVigencia">Fin de vigencia vacío.</span>');  
            $("#finVigencia").select();
            return false;
        }
        
        var inicioVigencia = new Date(fechaInicio);
        var finVigencia = new Date(fechaFin);
        
        if (inicioVigencia > finVigencia) {
            $("#inicioVigencia").focus().after('<span class="error2" id="errorInicioVigencia">Inicio de \n\
                vigencia  mayor al fin de vigencia.</span>');  
            $("#inicioVigencia").select();
            return false;
        }
        
        var errorTotalPrima = document.getElementById("errorTotalPrima");
        
        if (errorTotalPrima != null) {
            errorTotalPrima.parentNode.removeChild(errorTotalPrima);
        }
        
        var totalPrima = $("#valorPrima").val();
        
        if (totalPrima == null || totalPrima == "" || Number(totalPrima) < 1) {
            $("#valorPrima").focus().after('<span class="error2" id="errorFinVigencia">Valor no permitido.</span>');  
            $("#valorPrima").select();
            return false;
        }
        
        return true;
    });
});




