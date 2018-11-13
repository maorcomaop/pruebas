
/* Identificador de intervalo-consulta para indicador */
var id_iph;

/* Objeto chart para indicador */
var chart_iph;

/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Indicador Pasajero hora */

function detiene_PasajeroHora() {
    
    if (id_iph != null) {
        clearInterval(id_iph);
        id_iph = null;
    }
}

function itemIph(value) {
    
    var arrayData = value.split("%");
    var iph = new Object();
    
    iph.idRuta          = arrayData[0];
    iph.nombreRuta      = arrayData[1];
    iph.hora            = arrayData[2];    
    iph.entradas        = arrayData[3];
    
    return iph;
}

function chart_PasajeroHora() {
    
    var data = document.getElementById("sdata-iph");    
    // idruta % nombre ruta % hora % numero pasajero % total pasajero % iph
    
    if (data != null) {
        
        var hora_inicio = document.getElementById("hora_inicio_iph");
        var hora_fin    = document.getElementById("hora_fin_iph");
        var lst_opt     = data.options;
        var size        = lst_opt.length;        
        
        var tbl_ind = document.getElementById("tabla-indicador");
        var msg_iph = document.getElementById("msg-iph");        
        var msg_iph_tr = document.getElementById("msg-iph-tr");
        
        if (tbl_ind != null) {
            //if (msg_iph != null) msg_iph.className = (size == 0) ? "opacity1" : "opacity0";
            if (msg_iph_tr != null) msg_iph_tr.className = (size == 0) ? "opacity1" : "opacity0";
        } else {
            msg_iph.innerHTML = (size == 0) ? "* Sin datos para visualizar." : "";
        }
        
        if (size == 0) return;        
        
        var lst_iph  = [];         
        var lst_ruta = [];
        var ruta;
        
        // Crea listado de objetos iph, idruta y nombre
        for (var i = 0; i < size; i++) {
            var iph = itemIph(lst_opt[i].value);
            lst_iph.push(iph);
            
            if (i == 0) {                
                lst_ruta.push([iph.idRuta, iph.nombreRuta]);
                ruta = iph.idRuta;
            } else if (iph.idRuta != ruta) {                
                lst_ruta.push([iph.idRuta, iph.nombreRuta]);
                ruta = iph.idRuta;
            }
        }
        
        hora_inicio = parseInt(hora_inicio.value);
        hora_fin    = parseInt(hora_fin.value);
        var lst_lbl = [];
        
        for (var hr = hora_inicio; hr <= hora_fin; hr++) {
            var hora = (hr < 10) ? "0" + hr : "" + hr;
            hora += ":00";
            lst_lbl.push(hora);
        }
        
        if (chart_iph == null) {
            Chart.defaults.global.defaultFontFamily = "'Roboto'";
            chart_iph = new Chart(document.getElementById("chart-iph"), {
                type: 'line',
                options: {
                    responsive: true,
                    legend: {
                        display: true,
                        position: 'bottom'
                    },
                    title: {
                        display: false,
                        text: 'Indice de pasajeros por hora (%)',
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
                                labelString: 'Hora'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Indice de pasajeros'
                            }
                        }]
                    }
                },
                data: {
                    labels: lst_lbl
                }
            });
            
            var size_ruta = lst_ruta.length;
            var size_iph  = lst_iph.length;                        
            
            for (var i = 0; i < size_ruta; i++) {                
                var ruta  = lst_ruta[i][0];
                var nruta = lst_ruta[i][1];
                var iph_data = []; // Listado completo iph (0 para hora sin registro)
                
                for (var hr = hora_inicio; hr <= hora_fin; hr++) {
                    var val_iph = 0;
                    for (var j = 0; j < size_iph; j++) {
                        var iph = lst_iph[j];
                        if (iph.idRuta == ruta &&
                            iph.hora == hr) {
                            val_iph = iph.entradas;
                            break;
                        }
                    }                    
                    iph_data.push(val_iph);
                }                
                
                // Se crea dataset por ruta
                var index = i % SIZE_COLORS2;
                var newDataSet = {
                    borderColor: COLORS2[index][0],
                    backgroundColor: COLORS2[index][0],
                    label: nruta,
                    data: iph_data,
                    fill: false,
                    lineTension: 0,
                    pointHoverRadius: 7
                };
                chart_iph.data.datasets.push(newDataSet);
            }
            chart_iph.update();
        }
    }
}

function buildChartPasajeroHora() {
    
    if (chart_iph != null) {
        chart_iph.destroy();
        chart_iph = null;
    }
    
    chart_PasajeroHora();
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

function consulta_PasajeroHora() {
    
    var fecha = document.getElementById(fecha_indicador).value;
    var sruta = localSelectedItems("sruta-iph");
    
    data = {
        "fecha": fecha,
        "sruta-iph": sruta
    };
    
    $.ajax({
        url: "/RDW1/indicadorPasajeroHora",
        data: data,
        method: "POST",        
        success: function(data) {
            $('#data-iph div').empty();
            $('#data-iph').append(data);
            buildChartPasajeroHora();
            console.log("> Indicador pasajero hora...");
        }
    });
}

function prepara_PasajeroHora() {
    
    if (fechaActual(fecha_indicador)) {
        if (id_iph == null) {
            consulta_PasajeroHora();
            id_iph = setInterval("consulta_PasajeroHora()", TIME_GRAPH_REFRESH);
        }
    } else {
        consulta_PasajeroHora();
        detiene_PasajeroHora();
    }
}




