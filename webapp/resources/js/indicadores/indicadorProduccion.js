
/* Identificador de intervalo-consulta para indicador */
var id_prod = null;

/* Objeto chart para indicador */
var chart_prd = null;

function addData(chart, labels, data) {
    
    var size = labels.length;
    for (var i = 0; i < size; i++) {
        chart.data.labels.push(labels[i]);
        chart.data.datasets.forEach(function (dataset) {
            dataset.data.push(data[i]);
        });
    }
    chart.update();
}

function removeData(chart) {
    
    var size = chart.data.labels.length;    
    for (var i = 0; i < size; i++) {
        chart.data.labels.pop();
        chart.data.datasets.forEach(function (dataset) {
            dataset.data.pop();
        });
    }
    chart.update();
}

function updateChart(chart, labels, data) {
    removeData(chart);
    addData(chart, labels, data);
}

/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Indicador Produccion */

function detiene_Produccion() {
    if (id_prod != null) {
        clearInterval(id_prod);
        id_prod = null;
    }
}

function chart_Produccion() {
    
    var data = document.getElementById("sdata-ip");
    // nombre_ruta % produccion % distancia % ipk
    
    if (data != null) {
        
        var lst_opt  = data.options;
        var size     = lst_opt.length;
        var lst_ruta = [];
        var lst_data = [];
        var lst_ipk  = [];
        
        for (var i = 0; i < size; i++) {
            var value      = lst_opt[i].value;
            var arrayValue = value.split("%");
            lst_ruta.push(arrayValue[0]); // nombre ruta
            lst_data.push(arrayValue[1]); // produccion            
            
            var produccion = arrayValue[1];
            var distancia  = arrayValue[2]; // distancia            
            var ipk = 0.0;
            if (distancia > 0) {
                var float_ipk = (produccion / distancia) * 1000;
                var str_ipk   = float_ipk.toFixed(2);
                ipk = parseFloat(str_ipk);
            }
            lst_ipk.push(ipk);
        }        
        
        var tbl_ind   = document.getElementById("tabla-indicador");
        var msg_ip    = document.getElementById("msg-ip");
        var msg_ip_tr = document.getElementById("msg-ip-tr");
        
        var RND = false;
        if (size == 1) { // Unicamente ruta no detectada            
            RND = (lst_ruta[0].localeCompare("RND") == 0) ? true : false;
        } else if (size > 1) {
            lst_ruta[size-1] = "Ruta no detectada";
        }
        
        if (tbl_ind != null) {
            //if (msg_ip != null) msg_ip.className = (size == 0) ? "opacity1" : "opacity0";
            if (msg_ip_tr != null) msg_ip_tr.className = (size == 0 || RND) ? "opacity1" : "opacity0";
        } else {
            msg_ip.innerHTML = (size == 0 || RND) ? "* Sin datos para visualizar." : "";
        }
        
        if (size == 0 || RND)  return;        
        
        var colors = [];
        var colors_border = [];        
        
        for (var i = 0; i < size; i++) {
            var index = i % SIZE_COLORS2;
            colors_border.push(COLORS2[index][0]);
            colors.push(COLORS2[index][1]);
        }
        
        if (chart_prd == null) {
            Chart.defaults.global.defaultFontFamily = "'Roboto'";
            chart_prd = new Chart(document.getElementById("chart-ip"), {
                type: 'horizontalBar',
                data: {
                    labels: lst_ruta,
                    datasets: [{
                        data: lst_data,
                        backgroundColor: colors,
                        borderColor: colors_border,
                        borderWidth: 1
                    }]                    
                },
                options: {
                    plugins: {
                        datalabels: {
                            align: 'end',
                            color: 'black',
                            display: true,
                            font: {
                                weight: 'bold'
                            },
                            formatter: function(value, context) {
                                var i = context.dataIndex;
                                var ipk = lst_ipk[i];
                                return value + ' ( ' + ipk + ' psj / km ) ';
                            }
                        }
                    },
                    /*
                    scales: {
                        xAxes: [{ stacked: true }],
                        yAxes: [{ stacked: true }]                        
                    }, */
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Cantidad de pasajeros'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Ruta'
                            }
                        }]
                    },
                    legend: { display: false },
                    title: {
                        display: false,
                        text: 'Indice de productividad por ruta (Cantidad de pasajeros)',
                        fontSize: 16
                    }
                }
            });
        }
    }
}

function buildChartProduccion() {
    if (chart_prd != null) {
        chart_prd.destroy();
        chart_prd = null;
    }
    chart_Produccion();
}

function consulta_Produccion() {
    
    var fecha = document.getElementById(fecha_indicador).value;
    data = { "fecha" : fecha };    
    
    $.ajax({
        url: "/RDW1/indicadorProduccion",
        data: data,
        method: "POST",
        success: function(data) {
            $('#data-ip div').empty();
            $('#data-ip').append(data);
            buildChartProduccion();
            console.log("> Indicador produccion...");
        }
    });
}

function prepara_Produccion() {
    
    if (fechaActual(fecha_indicador)) {
        if (id_prod == null) {
            consulta_Produccion();
            id_prod = setInterval('consulta_Produccion()', TIME_GRAPH_REFRESH);
        }
    } else {
        consulta_Produccion();
        detiene_Produccion();
    }
}