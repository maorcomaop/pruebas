
/* Identificador de intervalo-consulta para indicador */
var id_cruta = null;

/* Objeto chart para indicador */
var chart_cruta = null;

/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Indicador Cumplimiento ruta */

function detiene_CumplimientoRuta() {
    if (id_cruta != null) {
        clearInterval(id_cruta);
        id_cruta = null;
    }
}

function chart_CumplimientoRuta() {
    
    var data = document.getElementById("sdata-icr");
    var maxVuelta = document.getElementById("maxVuelta-icr");
            
    if (data != null) {
        
        // nombre_ruta % vuelta1 % vuelta2 % vuelta3 % vueltaN
        var lst_opt  = data.options;
        var size     = lst_opt.length;
        
        var tbl_ind = document.getElementById("tabla-indicador");
        var msg_icr = document.getElementById("msg-icr");
        var msg_icr_tr = document.getElementById("msg-icr-tr");
        
        if (tbl_ind != null) {
            //if (msg_icr != null) msg_icr.className = (size == 0) ? "opacity1" : "opacity0";
            if (msg_icr_tr != null) msg_icr_tr.className = (size == 0) ? "opacity1" : "opacity0";
        } else {
            msg_icr.innerHTML = (size == 0) ? "* Sin datos para visualizar." : "";            
        }        
        
        if (size == 0) return;

        var lbl_vlt = [];
        for (var i = 0; i < maxVuelta.value; i++) {
            var lbl = "Vuelta " + (i+1);
            lbl_vlt.push(lbl);  
        }                
        
        if (chart_cruta == null) {
            Chart.defaults.global.defaultFontFamily = "'Roboto'";
            chart_cruta = new Chart(document.getElementById("chart-icr"), {
                type: 'line',                
                options: {
                    responsive: true,
                    //hoverMode: 'index',
                    //stacked: false,
                    legend: {
                        display: true,
                        position: 'bottom'                        
                    },
                    title: {
                        display: false,
                        text: "Indice de cumplimiento de ruta (%)",
                        fontSize: 16
                    },
                    plugins: {
                        datalabels: {
                            display: false
                        }
                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: '# Vuelta'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Indice de cumplimiento'
                            }
                        }]
                    }
                },
                data: {
                    labels: lbl_vlt // Lista de etiquetas (eje x - vueltas)
                }
            });
                    
            for (var i = 0; i < size; i++) {
                var value = lst_opt[i].value;                
                var array = value.split("%");
                var ruta  = array[0];
                var data  = [];
                
                // Adiciona calificacion de vueltas realizadas
                var nvlt  = array.length;
                for (var j = 1; j < nvlt; j++) {
                    data.push(array[j]);
                }
                // Ignora calificacion para vueltas no realizadas
                for (var j = nvlt+1; j <= maxVuelta.value; j++) {
                    data.push(null);
                }                
                
                var index = i % SIZE_COLORS2;
                var newDataSet = {
                    borderColor: COLORS2[index][0],
                    backgroundColor: COLORS2[index][0],
                    label: ruta,    // ruta
                    data: data,     // puntos de linea
                    fill: false,
                    lineTension: 0,
                    pointHoverRadius: 7
                };
                chart_cruta.data.datasets.push(newDataSet);
                
            }
            chart_cruta.update();
        }
    }
}

function buildChartCumplimientoRuta() {
    if (chart_cruta != null) {
        chart_cruta.destroy();
        chart_cruta = null;
    }
    chart_CumplimientoRuta();
}

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

function consulta_CumplimientoRuta() {
    
    var fecha = document.getElementById(fecha_indicador).value;
    var sruta = localSelectedItems("sruta-icr");
        
    data = { 
        "fecha" : fecha,
        "sruta-icr" : sruta
    };
    
    $.ajax({
        url: "/RDW1/indicadorCumplimientoRuta",
        data: data,
        method: "POST",
        success: function(data) {
            $('#data-icr div').empty();
            $('#data-icr').append(data);
            buildChartCumplimientoRuta();
            console.log("> Indicador cumplimiento ruta...");
        }
    });
}

function prepara_CumplimientoRuta() {
    
    if (fechaActual(fecha_indicador)) {                
        if (id_cruta == null) {
            consulta_CumplimientoRuta();
            id_cruta = setInterval('consulta_CumplimientoRuta()', TIME_GRAPH_REFRESH);        
        }
    } else {
        consulta_CumplimientoRuta();
        detiene_CumplimientoRuta();
    }
}