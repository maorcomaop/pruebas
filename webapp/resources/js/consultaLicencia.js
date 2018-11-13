
$(document).ready(function () {

    /** Commentado provisional**/
    //    var cnx = setInterval(conexion, 3000);    


    //var val = setInterval(vencimiento_lic, 3000);    
    var url = window.location.href;
});

function conexion() {
    $.post("/RDW1/conexionInternet", {},
            function (result) {
                if ($.trim(result) === '1') {
//                            console.log("hay conexion");
                    consultaServicioRemoto();
                    //$(".offline-ui").addClass("offline-ui-up");                  
                    //$(".offline-ui").removeClass("offline-ui-down"); 
                    //$(".offline-ui").show();
                } else {
                    console.log("No hay conexion NO SE CONSULTA EL WEB SERVICE");
                    //$(".offline-ui").addClass("offline-ui-down");                            
                    setTimeout(rem, 15000);
                }
            });
}

function rem() {
    $(".offline-ui").removeClass("offline-ui-up");
    $(".offline-ui").css("display", "none");
}
function consultaServicioRemoto() {

    $.post("/RDW1/consultarEmpresaInicio", {id: 1},
            function (result) {
                var obj = jQuery.parseJSON($.trim(result));
                $.post("/RDW1/consultarLicenciaLocal", {doc_cliente: obj.nit, nom_cliente: obj.nombre},
                        function (result) {
                            var obj = jQuery.parseJSON($.trim(result));
                            $.post("/RDW1/consultaServicio", {doc_cliente: obj.doc, nom_cliente: obj.nom},
                                    function (result) {
                                        var obj3 = jQuery.parseJSON($.trim(result));
                                        if (obj3.valor === 0)
                                        {
                                            closeApp1();
                                            console.log("Servicio suspendido");
                                        } else
                                        {
//                                    console.log("Servicio activo");
                                        }

                                    });/*FIN CONSULTAR SERVICIO*/
                        });/*FIN CONSULTAR LICENCIA LOCAL*/
            });/*FIN CONSULTAR EMPRESA INICIO*/
}


function consultaEmpresaActiva() {
    $.post("/RDW1/consultarEmpresaInicio", {id: 1},
            function (result) {
                var obj = jQuery.parseJSON($.trim(result));
                //var nit= obj.nit.replace(".","").replace("-","");                
                //console.log("***> "+obj.nit+" Nit: "+obj.nit.replace(".","").replace("-","")+" Nom: "+obj.nombre);
                console.log("***> " + obj.nit + " Nom: " + obj.nombre);
                $.post("/RDW1/consultarLicenciaRemota", {doc_cliente: obj.nit, nom_cliente: obj.nombre},
                        function (result) {
                            var obj = jQuery.parseJSON($.trim(result));
                            console.log("id_licencia:" + obj.id);
                            console.log("id_cliente:" + obj.fk);
                        });
            });
}
;

function valida_vence() {
    /*DEBE VALIDAR LA FECHA DE ACTUALIZACION SI CONCUERDA CON LA ACTUAL DE EJECITAR CRONTABA CADA CIERTO TIEMPO SINO NO*/
    jsCron.set("38 16 * * *", vencimiento_lic);
    jsCron.set("39 16 * * *", vencimiento_lic);
    jsCron.set("41 16 * * *", vencimiento_lic);
}

function vencimiento_lic() {
    $.post("/RDW1/vencimientoLicencia", {},
            function (result) {
                var obj = jQuery.parseJSON($.trim(result));
                var fecha1 = moment().format('YYYY/MM/DD');
                var fecha2 = moment(obj.id2);
                var fecha3 = moment(obj.id1);

                var cantidadDeDiasVence = fecha2.diff(fecha1, 'days');
                console.log("FALTAN " + cantidadDeDiasVence + "");
                if (cantidadDeDiasVence > 0)
                {
                    console.log("LE FALTAN " + cantidadDeDiasVence + " para vencer, realiza autizalicion ahora");
                    alert("LE FALTAN " + cantidadDeDiasVence + " para vencer su licencia, ACTUALICE YA");
                } else {

                }
            });
}


function closeApp1() {
    try {
        document.getElementById("form-close").submit();
    } catch (e) {
        console.log(e.toString());
    }
}
    