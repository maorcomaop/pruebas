
var cnx = null;    
var fecha_indicador = "fecha-indicador";
function irAIndicadorDescuentoPasajeros(ind) {
    
    var url      = "/RDW1/app/indicadores/";
    var urlPost  = "/RDW1/";
    var resource = "";
    var send = false;
    
    switch (ind) {        
        case IPC: { resource = "indicadorDescuentoDePasajerosExterno"; send=true; break; }
    }    
   
    if (send) {        
        url     += resource + ".jsp";
        urlPost += resource;        
        
         $.post(urlPost, {fecha: $('#'+fecha_indicador).datepicker({ dateFormat: 'yyyy-mm-dd' }).val(), desdePaginaIndicadores: '1'},
                    function (result) {                                                
                        window.location = url;
                    });
    }
}   
    

function prepara_PasajeroDescontado() {
    
    if (fechaActual("fecha-indicador")) {        
        if (cnx === null) {            
            graficoPasajerosDescontados();
            cnx = setInterval("graficoPasajerosDescontados()", TIME_GRAPH_REFRESH);
        }
    } else {        
        graficoPasajerosDescontados();
        detienePasajerosDescontados();
    }
}
function detienePasajerosDescontados() {    
    if (cnx !== null) {
        console.log("entra al si detiene");
        clearInterval(cnx);
        cnx = null;
    }
}
function graficoPasajerosDescontados(){    
    //console.log("Indicador de pasajros descontados "+$( "#fecha-indicador" ).val());
    var ctx = $("#chart-idpc");
    $.post("/RDW1/indicadorDescuentoDePasajeros", {fecha:$( "#fecha-indicador" ).datepicker({ dateFormat: 'yyyy-mm-dd' }).val()},function (result) {            
            var obj1 = JSON.parse($.trim(result));   
            console.log($.trim(result));
            var arr = [];
            var arr1 =[];
            var arr2 =[];            
            var names =[];
            var datos=[];
                        
            for (var prop in obj1) {
                arr.push(obj1[prop]);
            }            
            arr1=arr[0][0];
            arr2=arr[1][0];
            
            for (var prop in arr1) {
                names.push(arr1[prop]);
            }
            for (var prop in arr2) {
                datos.push(arr2[prop]);
            }
               //VERIFICA LOS DATOS QUE DEBE MOSTRAR
               if ($.trim(datos) !== '0'){
                   console.log("hay datos q mostrar");
                   $("#msg-idpc").removeClass('opacity1');
                   $("#msg-idpc").addClass('opacity0');
                   var myChart = new Chart(ctx, {
                       type: 'pie',
                       data: {                      
                           labels:names,                      
                           datasets: [{label:"porcentaje",
                                   backgroundColor: [ "#FAEBD7", "#DCDCDC","#E9967A","#F5DEB3","#9ACD32"],
                                   borderColor: 'rgb(0, 0, 0)',    
                                   borderWidth: 1,
                                   data:datos}]
                       },              
                       options: {                         
                           legend: { display: true,
                               position:'bottom',
                               labels:{ fontColor:'black', defaultFontStyle:'normal', defaultFontFamily:'Arial'}
                           },                                                
                           title: {
                             display: false,
                             text: 'Descuentos de pasajeros'
                         },            
                        tooltips: {
                                    enabled: true,
                                    titleFontStyle:'bold',
                                    callbacks:{ labelTextColor:function(tooltipItem, chart){return '#FFFFFF';}} 
                        }
                    }            
                });
                   
               }else{
                   console.log("NO hay datos q mostrar");
                   $("#msg-idpc").removeClass('opacity0');
                   $("#msg-idpc").addClass('opacity1');
               }//FIN DATOS VACIOS        
    });
    
}

/******************************************************/
function prepara_PasajeroDescontado1() {
    
    if (fechaActual("fecha_busqueda_pasajeros")) {        
        if (cnx === null) {            
            traerDatosParaElGrafico();
            cnx = setInterval("traerDatosParaElGrafico()", TIME_GRAPH_REFRESH);
        }
    } else {        
        traerDatosParaElGrafico();
        detienePasajerosDescontados();
    }
}
function traerDatosParaElGrafico(){        
    console.log("***> "+$( "#fecha_busqueda_pasajeros" ).val());
    var ctx = $("#pie-chart");
    $.post("/RDW1/indicadorDescuentoDePasajeros", {fecha:$( "#fecha_busqueda_pasajeros" ).val()},function (result) {            
            var obj1 = JSON.parse($.trim(result));   
            console.log($.trim(result));
            var arr = [];
            var arr1 =[];
            var arr2 =[];            
            var names =[];
            var datos=[];
                        
            for (var prop in obj1) {
                arr.push(obj1[prop]);
            }            
            arr1=arr[0][0];
            arr2=arr[1][0];
            
            for (var prop in arr1) {
                names.push(arr1[prop]);
            }
            for (var prop in arr2) {
                datos.push(arr2[prop]);
            }
               //console.log("---> "+datos);
                //VERIFICA LOS DATOS QUE DEBE MOSTRAR
               if ($.trim(datos) !== '0'){
                    var myChart = new Chart(ctx, {
                        type: 'pie',
                        data: {                      
                            labels:names,                      
                            datasets: [{label:"porcentaje",
                                    backgroundColor: [ "#FAEBD7", "#DCDCDC","#E9967A","#F5DEB3","#9ACD32"],
                                    borderColor: 'rgb(0, 0, 0)',    
                                    borderWidth: 1,
                                    data:datos}]
                        },              
                        options: {                         
                            legend: { display: true,
                                position:'bottom',
                                labels:{ fontColor:'black', defaultFontStyle:'normal', defaultFontFamily:'Arial'}
                            },                                                
                            title: {
                                display: false,
                                text: 'Descuentos de pasajeros'
                            },            
                            tooltips: {
                                enabled: true,
                                titleFontStyle:'bold',
                                callbacks:{ labelTextColor:function(tooltipItem, chart){return '#FFFFFF';}}                                 
                            }
                        }            
                    });
               }else{
                   console.log("NO hay datos q mostrar");
                   $("#msg-idpc").removeClass('opacity0');
                   $("#msg-idpc").addClass('opacity1');
               }//FIN DATOS VACIOS 
        
    });
    
}
