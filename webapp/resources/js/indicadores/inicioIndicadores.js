
var IP = 1;
var ICR = 2;
var IPH = 3;
var ICT = 4;
var IPC = 5;

var fecha_indicador = "fecha-indicador";

// Selecciona elementos en componente selectpicker
// segun valores del parametro <data>
function setElements(id_elemt, data) {

    var array_data = data.split(",");
    var select = $('#'+id_elemt).selectpicker('val', array_data);        
}

// Construye cadena formateada de elementos seleccionados en un select
function localSelectedItems(id_elemt) {
    var select  = document.getElementById(id_elemt);
    if (select == null) return "";
    var options = select.options;
    var size    = options.length;
    
    var s = "";
    for (var i = 0; i < size; i++) {
        if (options[i].selected) {
            s += (s == "") ? options[i].value : "," + options[i].value;
        }
    }
    return s;
}

// Valida y establece en componentes graficos parametros de consulta previos
function validaParametrosIndicador() {
    
    var fecha = $('#' + fecha_indicador).datepicker();            
    var fechaIndGbl = $('#fecha-ind-gbl').val();
    
    if (fechaIndGbl != null && fechaIndGbl != "") {
        fecha.datepicker('setDate', fechaIndGbl);
        fecha.datepicker('update');        
    }
    
    var sruta_icr = document.getElementById("sruta-icr-gbl");
    var sruta_iph = document.getElementById("sruta-iph-gbl");
    
    if (sruta_icr != null) {
        setElements('sruta-icr', sruta_icr.value);
    }
    if (sruta_iph != null) {
        setElements('sruta-iph', sruta_iph.value);
    }
}

function iniciaIndicadores() {
    
    prepara_Produccion();
    prepara_CapacidadTransportadora();
    prepara_CumplimientoRuta();
    prepara_PasajeroHora();        
    //prepara_PasajerosDescontados();
    prepara_PasajeroDescontado();
}

// Valida indicador pulsado, establece parametros de consulta
// en el servidor (peticion ajax) para posterior uso y 
// finalmente redirecciona
function irAIndicador(ind) {
    
    var url      = "/RDW1/app/indicadores/";
    var urlPost  = "/RDW1/";
    var resource = "";
    var send = false;
    
    switch (ind) {
        case IP:  { resource = "indicadorProduccion"; send=true; break; }
        case ICR: { resource = "indicadorCumplimientoRuta"; send=true; break; }
        case IPH: { resource = "indicadorPasajeroHora"; send=true; break; }
        case ICT: { resource = "indicadorCapacidadTransportadora"; send=true; break; }        
    }
    
    var fecha = document.getElementById(fecha_indicador);
    var sruta_icr = localSelectedItems("sruta-icr");
    var sruta_iph = localSelectedItems("sruta-iph");
        
    if (send) {
        
        url     += resource + ".jsp";
        urlPost += resource;
        
        //console.log("--->*** "+url+" <----> "+urlPost);
        var data = {
            fecha: fecha.value,
            sruta_icr: sruta_icr,
            sruta_iph: sruta_iph,
            desdePaginaIndicadores: "1"
        };      
        
        $.ajax ({
            url: urlPost,
            data: data,
            method: "POST",
            success: function(data) {                
                window.location = url;
            }
        });     
        
        
    }
}

// Reinicia peticion de consulta de indicador
// especificado en parametro <ind>
function reiniciaIndicador(ind) {    
    
    if (ind == ICR) {
        prepara_CumplimientoRuta();
    }
    if (ind == IPH) {
        prepara_PasajeroHora();
    }
}