/* 
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
    
    cargarTabla();
});


/*Controla el acceso al boton eliminar del listado*/
$(function () {
    $(".eliminar").click(function () {
        confirmarEliminacion();
    });
});


function confirmarEliminacion() {
    var mensaje = confirm("¿Desea eliminar el registro?");

    if (mensaje) {
        console.log("¡Gracias por aceptar!");
        return true;
    } else {
        return false;
    }
}


$(function () {
    $("#btnGuardar").click(function () {
        var errorMantenimiento = document.getElementById("errorMantenimiento");
        
        if (errorMantenimiento != null) {
            errorMantenimiento.parentNode.removeChild(errorMantenimiento);
        }
        
        var fkMantenimiento = new Number($("#fkMantenimiento").val());
        
        if (fkMantenimiento == null || fkMantenimiento <= 0) {
            $("#fkMantenimiento").focus().after('<span class="error2" id="errorMantenimiento">Seleccione un mantenimiento.</span>');  
            $("#fkMantenimiento").select();
            return false;
        }
        
        var errorVehiculo = document.getElementById("errorVehiculo");
        
        if (errorVehiculo != null) {
            errorVehiculo.parentNode.removeChild(errorVehiculo);
        }
        
        var fkVehiculo = new Number($("#fkVehiculo").val());
        
        if (fkVehiculo == null || fkVehiculo <= 0) {
            $("#fkVehiculo").focus().after('<span class="error2" id="errorVehiculo">Seleccione un vehículo.</span>');  
            $("#fkVehiculo").select();
            return false;
        } 
        
        var errorValorInicial = document.getElementById("errorValorInicial");
        
        if (errorValorInicial != null) {
            errorValorInicial.parentNode.removeChild(errorValorInicial);
        }
        
        var valorInicial = new Number($("#valorInicialMantenimiento").val());
        
        if (valorInicial == null || valorInicial < 0) {
            $("#valorInicialMantenimiento").focus().after('<span class="error2" id="errorValorInicial">El valor no es válido.</span>');  
            $("#valorInicialMantenimiento").select();
            return false;
        } 
        
        return true;
    });
});

/*Se utiliza para cambiar a mayúscula los valores de los input:text*/
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().toUpperCase());
});


$(function () {
    $("#fkMantenimiento").change(function () {
        cargarTabla();
    });
});


function crearBotonEditar(valor) {
    var td = document.createElement("td");
    
    var form = document.createElement("form");
    form.action = "/RDW1/verVehiculoMantenimiento";
    form.method = "post";
    
    var inputHidden = document.createElement("input");
    inputHidden.type = "hidden";
    inputHidden.name = "id";
    inputHidden.value = valor;
    
    var inputSubmit = document.createElement("input");
    inputSubmit.type = "submit";
    inputSubmit.value = "Editar";
    inputSubmit.classList.add("btn");
    inputSubmit.classList.add("btn-xs");
    inputSubmit.classList.add("btn-info");
    
    form.appendChild(inputHidden);
    form.appendChild(inputSubmit);
    
    td.appendChild(form);
    
    return td;
}

function crearBotonDanger(valor, accion, etiqueta, estado, eliminar) {
    var td = document.createElement("td");
    
    var form = document.createElement("form");
    form.action = accion;
    form.method = "post";
    
    var inputHiddenEstado = document.createElement("input");
    inputHiddenEstado.type = "hidden";
    inputHiddenEstado.name = "estado";
    inputHiddenEstado.value = estado;
    
    var inputHiddenId = document.createElement("input");
    inputHiddenId.type = "hidden";
    inputHiddenId.name = "id";
    inputHiddenId.value = valor;
    
    var inputSubmit = document.createElement("input");
    inputSubmit.type = "submit";
    inputSubmit.value = etiqueta;
    inputSubmit.classList.add("btn");
    inputSubmit.classList.add("btn-xs");
    
    if (arguments[4] != null) {
        if (arguments[4] == 1) {
            inputSubmit.classList.add("btn-danger");
            inputSubmit.classList.add("eliminar");
        }
    } else {
        if (estado == 0) {
            inputSubmit.classList.add("btn-danger");
            inputSubmit.classList.add("eliminar");
        } else {
            inputSubmit.classList.add("btn-success");
        }
    }
    
    form.appendChild(inputHiddenEstado)
    form.appendChild(inputHiddenId);
    form.appendChild(inputSubmit);
    
    td.appendChild(form);
    
    return td;
}


function cargarTabla() {
    var tbodyRemove = document.getElementsByTagName("tbody")[0];

    if (tbodyRemove != null || tbodyRemove != undefined) {
        tbodyRemove.parentNode.removeChild(tbodyRemove);
    }

    $.ajax({
        url: '/RDW1/cargarVehiculosConfiguradosMto',
        method: "POST",
        dataType: "json",
        data: {
            idMantenimiento: $("#fkMantenimiento").val()
        },
        success: function (data) {

            if (data.response == "OK") {
                var table = document.getElementById("tableVehiculosMantenimiento");
                var tbody = document.createElement("tbody");

                for (var i = 0; i < data.listaVehiculosMto.length; i++) {
                    var tr = document.createElement("tr");

                    var tdMantenimiento = document.createElement("td");
                    var tdPlaca = document.createElement("td");
                    var tdValorInicial = document.createElement("td");

                    var txtMantenimiento = document.createTextNode(data.listaVehiculosMto[i].nombreMantenimiento);
                    var txtPlaca = document.createTextNode(data.listaVehiculosMto[i].placa);
                    var txtValorInicial = document.createTextNode(data.listaVehiculosMto[i].valorInicialMantenimiento);
                    var tdEditar = crearBotonEditar(data.listaVehiculosMto[i].id);
                    var tdCambiarEstado;

                    if (data.listaVehiculosMto[i].estado == 1) {
                        tdCambiarEstado = crearBotonDanger(data.listaVehiculosMto[i].id,
                                "/RDW1/cambiarEstadoVehiculoMantenimiento", "Desactivar", 0);
                    } else {
                        tdCambiarEstado = crearBotonDanger(data.listaVehiculosMto[i].id,
                                "/RDW1/cambiarEstadoVehiculoMantenimiento", "Acitvar", 1);
                    }

                    var tdEliminar = crearBotonDanger(data.listaVehiculosMto[i].id,
                            "/RDW1/eliminarVehiculoMantenimiento", "Eliminar", 1, 1);

                    tdMantenimiento.appendChild(txtMantenimiento);
                    tdPlaca.appendChild(txtPlaca);
                    tdValorInicial.appendChild(txtValorInicial);

                    tr.appendChild(tdMantenimiento);
                    tr.appendChild(tdPlaca);
                    tr.appendChild(tdValorInicial);
                    tr.appendChild(tdEditar);
                    tr.appendChild(tdCambiarEstado);
                    tr.appendChild(tdEliminar);

                    tbody.appendChild(tr);
                }

                table.appendChild(tbody);
                
                var botones = document.getElementsByClassName("eliminar");
                
                if (botones != null && botones.length > 0) {
                    
                    for (var i = 0; i < botones.length; i++) {
                        botones[i].addEventListener("click", confirmarEliminacion);
                    }
                }
            }
        }
    });
}