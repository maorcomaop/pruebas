
/* Identificador de intervalo-consulta para indicador */
var id_ctrans = null;

/* Objeto chart para indicador */
var chart_ctrans = null;

/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Indicador de capacidad transportadora */

function detiene_CapacidadTransportadora() {
    if (id_ctrans != null) {
        clearInterval(id_ctrans);
        id_ctrans = null;
    }
}

function consulta_CapacidadTransportadora() {
    
    var fecha = document.getElementById(fecha_indicador).value;    
    data = { "fecha": fecha };
    
    $.ajax({
        url: "/RDW1/indicadorCapacidadTransportadora",
        data: data,
        method: "POST",        
        success: function(data) {
            $('#data-ict div').empty();
            $('#data-ict').append(data);
            var resumen = document.getElementById("resumen-ict");
            
            var tbl_ind = document.getElementById("tabla-indicador");
            var msg_ict = document.getElementById("msg-ict");            

            resumen.style.display = "none"; var result = false;
            if (data.indexOf("table") >= 0) {
                resumen.style.display = "block";
                result = true;
            }             
            
            if (tbl_ind != null) {
                msg_ict.className = (!result) ? "opacity1" : "opacity0";                
            } else {
                msg_ict.innerHTML = (!result) ? "* Sin datos para visualizar." : "";
            }
            
            console.log('> Indicador capacidad transportadora...');
        }
    });
}

function prepara_CapacidadTransportadora() {
    
    if (fechaActual(fecha_indicador)) {
        if (id_ctrans == null) {
            consulta_CapacidadTransportadora();
            id_ctrans = setInterval('consulta_CapacidadTransportadora()', TIME_GRAPH_REFRESH);
        }
    } else {
        consulta_CapacidadTransportadora();
        detiene_CapacidadTransportadora();
    }
}