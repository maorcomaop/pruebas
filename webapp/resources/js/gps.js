
/*  
 Formato actual datos gps:
 0  Id                
 1  FechaServidor     
 2  FechaGPS          
 3  Rumbo             
 4  Velocidad         
 5  Msg               
 6  TotalDia          
 7  Numeracion        
 8  Entradas          
 9 Salidas           
 10 Alarma            
 11 DistanciaMetros   
 12 Localizacion      
 13 Latitud           
 14 Longitud          
 15 Placa             
 16 NombreFlota       
 17 fechaServidorDate  
 18 fechaGPSDate      
 19 numeracionInicial 
 20 esPuntoControl    
 21 esPasajero        
 22 esAlarma 
 23 Numeracion final
 24 Distancia recorrida
 25 Trans reason
 26 Trans reason specific data
 27 Rumbo radianes
 28 Nivel ocupacion
 29 Noic
 30 ipk
 31 gpsId
 32 distanciaParcial
 33 ipkParcial
 */

var autoConsulta = false;
var verPuntoControl = true;
var verPasajero = true;
var verAlarma = true;
var verOtros = true;
var infoContextual = true;
var consultando = false;
var cambioParametros = false;
var separador = "||";
var intervalId = -1;

// Verifica si existe conexion con base de datos externa de registros gps
function gps_verificaConexion() {
    
    var msg_gps = document.getElementById("msg-gps");
    
    $.ajax({
        url: "/RDW1/conexionGPS",
        type: "POST",
        success: function(data) {
            if (data != null && data != "") {                
                var div_data = "<div class='form-msg style-msg'>" + data + "</div>";
                msg_gps.innerHTML = div_data;
            } else {
                msg_gps.innerHTML = "";                
            }
        }
    });
}

// Autoconsulta para visualizacion grafica de puntos
function gps_iniciarAutoConsulta() {

    if (!autoConsulta)
        return;

    console.log("> Consulta en mapa gps");

    // Obtiene parametros de consulta desde componentes graficos
    var infogps = parametros_infogps();
    var grupo_movil = document.getElementById("sgrupo").value;

    data = {
        "placa": infogps.placa,
        "fechaInicio": infogps.fechaInicio,
        "fechaFinal": infogps.fechaFinal,
        "verPuntoControl": infogps.verPuntoControl,
        "verPasajero": infogps.verPasajero,
        "verAlarma": infogps.verAlarma,
        "verConsolidado": infogps.verConsolidado,
        "verOtros": infogps.verOtros,
        "fechaPredeterminada": infogps.fechaPredeterminada,
        "grupoMovil": grupo_movil        
    };

    resultType("WAITING");

    $.ajax({
        url: "/RDW1/visualizarGPS",
        type: "POST",
        data: data,
        success: function (data) {
            $('#datosGPS div').empty();
            $('#datosGPS').append(data);
            consultando = false;

            // Verifica parametros de respuesta,
            // imprime mensaje de error
            gps_verificarParametrosRespuesta();
            gps_limpiarCamposInfo("");
            removeAllMarks();

            // Verifica que placa seleccionada corresponda a los datos obtenidos
            var veh_con = document.getElementById("placa_con");
            veh_con.value = placa;

            if (gps_vehiculoSeleccionado()) {
                infoContextual = true;
                gps_cargarDatos('lst_evt', true);
            } else {
                infoContextual = false;
                gps_cargarDatos('lst_evt', false);
            }            

            // Mostrar panel de informacion
            //gps_mostrarPanelInformacion();
            // Verifica y advierte fin de sesion
            gps_anunciarFinDeSesion();            
        }
    });
}

// Verifica si existe filtro establecido
function existe_filtro() {

    var filtros = document.getElementById("sfiltro");
    var options = filtros.options;
    if (options[1].selected ||
        options[2].selected ||
        options[3].selected ||
        options[4].selected ||
        options[5].selected) {
        return true;
    }
    return false;
}

// Extrae parametros de consulta de componentes graficos
// que son guardados en objeto infogps
function parametros_infogps() {

    var veh_sel = document.getElementById("splaca").value; // placa, numero interno, capacidad        
    var fecha_ini = document.getElementById("fechaInicio").value;
    var fecha_fin = document.getElementById("fechaFinal").value;

    var filtros = document.getElementById("sfiltro");

    // Coordinar indices con componente select sfiltro
    var verPuntoControl = (filtros.options[1].selected) ? 1 : 0;
    var verPasajero     = (filtros.options[2].selected) ? 1 : 0;
    var verAlarma       = (filtros.options[3].selected) ? 1 : 0;
    var verConsolidado  = (filtros.options[4].selected) ? 1 : 0;
    var verOtros        = (filtros.options[5].selected) ? 1 : 0;
    
    var fechaPredeterminada = 0;
    var sfecha = document.getElementById("sfecha");
    if (sfecha != null) {
        fechaPredeterminada = sfecha.value;
    }    

    var placa = "";

    if (veh_sel.indexOf(",") > 0) {
        placa = veh_sel.split(",")[0];
    }

    var infogps = new Object();
    infogps.placa = placa;
    infogps.fechaInicio = fecha_ini;
    infogps.fechaFinal = fecha_fin;
    infogps.verPuntoControl = verPuntoControl;
    infogps.verPasajero = verPasajero;
    infogps.verAlarma = verAlarma;
    infogps.verConsolidado = verConsolidado;
    infogps.verOtros = verOtros;
    infogps.fechaPredeterminada = fechaPredeterminada;

    return infogps;
}

// Opaca tabla
function gps_opacaTabla() {    
    // Estilo de consulta en proceso (opaca tabla)
    document.getElementsByTagName("body")[0].className = "load";
}

// Aclarece tabla 
function gps_aclareceTabla() {    
    // Recuperacion de vista normal
    document.getElementsByTagName("body")[0].className = "none";
}

// Autoconsulta para despliegue de datos en tabla
function gps_iniciarAutoConsultaTabla() {

    if (!autoConsulta)
        return;

    console.log("> Consulta en tabla gps");

    consultando = true;
    gps_permitirConsulta();

    // Verifica si hubo consulta previa, de haberla
    // establece sus parametros
    gps_verificarParametrosConsulta();

    // Se conserva posicion actual en pagina tras cada consulta,
    // a traves de variable pagePosition
    var pagePosition = scrollPagePosition();

    // Obtiene parametros de consulta desde componentes graficos
    var infogps = parametros_infogps();

    data = {
        "placa": infogps.placa,
        "fechaInicio": infogps.fechaInicio,
        "fechaFinal": infogps.fechaFinal,
        "verPuntoControl": infogps.verPuntoControl,
        "verPasajero": infogps.verPasajero,
        "verAlarma": infogps.verAlarma,
        "verConsolidado": infogps.verConsolidado,
        "verOtros": infogps.verOtros,
        "fechaPredeterminada": infogps.fechaPredeterminada
    };        

    $.ajax({
        url: "/RDW1/infoGPS",
        type: "POST",
        data: data,
        success: function (data) {
            $('#tbodyid tr').empty();
            $('#tbodyid').append(data);

            gps_aclareceTabla();
            consultando = false;

            gps_verificarParametrosRespuesta();

            if (gps_vehiculoSeleccionado()) {
                gps_cargarCamposInfo();
            } else {
                gps_limpiarCamposInfo("");
            }

            // Verifica y advierte fin de session
            gps_anunciarFinDeSesion();

            // Se conserva posicion de pagina
            window.scrollTo(pagePosition[0], pagePosition[1]);
        }
    });
}

// Comprueba si variable que informa fin de sesion esta activa,
// de estarlo notifica al usuario
function gps_anunciarFinDeSesion() {
    var session_end   = document.getElementById("session_end");
    var session_time  = document.getElementById("session_time");
    var alert_ui      = document.getElementById("alert_ui");
    var alert_ui_text = alert_ui.getElementsByTagName("div")[0];
    
    if (session_end != null && session_end.value == '1') {
        alert_ui_text.innerHTML = "Ha finalizado la sesi&oacute;n.";
        alert_ui.style.display = "block";
    } else {
        if (session_time != null) {
            var time = parseInt(session_time.value);
            if (time == 1) {
                alert_ui_text.innerHTML = "Resta 1 minuto para que finalice la sesi&oacute;n.";
                alert_ui.style.display = "block";
            } else if (time >= 2) {
                alert_ui_text.innerHTML = "Restan " + time + " minutos para que finalice la sesi&oacute;n.";
                alert_ui.style.display = "block";
            }
        }
    }
}

// Aplica estilo para componente result-pp segun el resultado/estado
// obtenido en el proceso de consulta.
function resultType(type) {

    var result = document.getElementById("result-pp");

    if (type === "OK") {
        result.className = "track-label-positive-result";
    } else if (type === "ZERO") {
        result.className = "track-label-zero-result";

    } else if (type === "ERROR") {


    } else if (type === "WAITING") {
        result.className = "track-spin-gps";
    }
}

// Lanza peticion de consulta IPK segun parametros establecidos
function gps_generarIPK() {

    var veh_sel = document.getElementById("splaca").value;
    var fecha_ini = document.getElementById("fechaInicio").value;
    var fecha_fin = document.getElementById("fechaFinal").value;

    if (veh_sel == "") {
        document.getElementById("msg").innerHTML = "* Seleccione un veh&iacute;culo para reporte IPK.";
        return;
    }

    var data = null;
    var placa = "";
    if (veh_sel.indexOf(",") > 0)
        placa = veh_sel.split(",")[0];

    document.getElementById("placa_ipk").value = placa;
    document.getElementById("fechaInicio_ipk").value = fecha_ini;
    document.getElementById("fechaFinal_ipk").value = fecha_fin;

    document.getElementById("form-ipk").submit();

    //gps_verificarParametrosRespuesta();
}

// Lanza peticion de exportacion a excel segun parametros establecidos
function gps_exportarExcel() {

    var form_excel = document.getElementById("form-excel");
    var infogps = parametros_infogps();

    document.getElementById("placa_excel").value = infogps.placa;
    document.getElementById("fechaInicio_excel").value = infogps.fechaInicio;
    document.getElementById("fechaFinal_excel").value = infogps.fechaFinal;
    document.getElementById("verPuntoControl_excel").value = infogps.verPuntoControl;
    document.getElementById("verPasajero_excel").value = infogps.verPasajero;
    document.getElementById("verAlarma_excel").value = infogps.verAlarma;
    document.getElementById("verConsolidado_excel").value = infogps.verConsolidado;

    form_excel.submit();
}

//function base64ToArrayBuffer(base64) {
//    var binaryString = window.atob(base64);
//    var binaryLen = binaryString.length;
//    var bytes = new Uint8Array(binaryLen);
//    for (var i = 0; i < binaryLen; i++) {
//        var ascii = binaryString.charCodeAt(i);
//        bytes[i] = ascii;
//    } 
//    return bytes;
//}
//
//function saveByteArray(reportName, byte) {
//    var blob = new Blob([byte], { type: 'application/ms-excel' } );
//    var link = document.createElement('a');
//    link.href = window.URL.createObjectURL(blob);
//    var fileName = reportName;
//    link.download = fileName;
//    document.body.appendChild(link);
//    link.click();
//}

var NORMAL = 1;
var INDEXOF = 2;

// Verifica y establece valores en elementos graficos
// segun si vienen especificados de una consulta previa
// (Informacion en campos finalizados en _con)
function gps_verificarParametrosConsulta() {

    if (!cambioParametros) {

        var fechaIni_con = document.getElementById("fechaIni_con");
        var fechaFin_con = document.getElementById("fechaFin_con");
        var placa_con = document.getElementById("placa_con");
        var grupo_con = document.getElementById("grupo_con");
        var fechaPred_con = document.getElementById("fechaPred_con");

        if (grupo_con != null && grupo_con.value != "") {
            gps_selectElement("sgrupo", grupo_con.value, NORMAL);
        } else if (placa_con != null && placa_con.value != "") {
            gps_selectElement("splaca", placa_con.value, INDEXOF);
        }        
        if (fechaPred_con != null && 
            parseInt(fechaPred_con.value) > 0) {
            gps_selectElement("sfecha", fechaPred_con.value, NORMAL);
        }

        if (fechaIni_con.value != "" && fechaFin_con.value != "") {
            document.getElementById("fechaInicio").value = fechaIni_con.value;
            document.getElementById("fechaFinal").value = fechaFin_con.value;
        }
    }    
}

// Verifica si valores en componentes graficos
// y datos de respuesta (fecha, placa) han sido
// especificados
function gps_verificarParametrosRespuesta() {

    var fecha_ini = document.getElementById("fechaInicio").value;
    var fecha_fin = document.getElementById("fechaFinal").value;
    var placa = document.getElementById("splaca").value;

    var pruebaFecha = document.getElementById("pruebaFecha");
    var pruebaPlaca = document.getElementById("pruebaPlaca");

    var msg  = document.getElementById("msg");
    msg.innerHTML = ""; 
    msg.className = "form-msg";
    var smsg = "";

    if ((fecha_ini != "" && fecha_fin != "") && (pruebaFecha == null || pruebaFecha.value == 0))
        smsg = "* Rango de fechas es incorrecto, se trabaja con fecha actual.";

    if (placa != "" && (pruebaPlaca == null || pruebaPlaca.value == 0)) {
        var text = " Placa es incorrecta.";
        smsg += (smsg == "") ? "*" + text : text;
    }

    if (smsg != "") {
        msg.innerHTML = smsg;
        msg.className = "form-msg style-msg";
    }
}

// Verifica si existe un vehiculo seleccionado
function gps_vehiculoSeleccionado() {
    var placa = document.getElementById("splaca").value;
    var pruebaPlaca = document.getElementById("pruebaPlaca");

    if (placa != "" && pruebaPlaca != null && pruebaPlaca.value == 1)
        return true;
    return false;
}

// Construye objeto gps desde cadena formateada por cada registro
// obtenido en una consulta
function gps_prepararDatos(data) {
    var gps = new Object();

    var arrayData = data.split(separador);
    gps.id = arrayData[0];
    //gps.fechaServidor = arrayData[1];
    gps.fechaServidor = arrayData[2];
    gps.fechaGPS = arrayData[2];
    gps.rumbo = arrayData[3];
    gps.velocidad = arrayData[4];
    gps.msg = arrayData[5];
    gps.totalDia = arrayData[6];
    gps.numeracion = arrayData[7];
    gps.entradas = arrayData[8];
    gps.salidas = arrayData[9];
    gps.alarma = arrayData[10];
    gps.distanciaMetros = arrayData[11];
    gps.localizacion = arrayData[12];
    gps.latitud = arrayData[13];
    gps.longitud = arrayData[14];
    gps.placa = arrayData[15];
    gps.nombreFlota = arrayData[16];
    //gps.fechaServidorDate = arrayData[17];
    gps.fechaServidorDate = arrayData[18];
    gps.fechaGPSDate = arrayData[18];
    gps.numeracionInicial = arrayData[19];
    gps.esPuntoControl = arrayData[20];
    gps.esPasajero = arrayData[21];
    gps.esAlarma = arrayData[22];
    gps.numeracionFinal = arrayData[23];
    gps.distanciaRecorrida = arrayData[24];
    gps.razonTransmision = arrayData[25];
    gps.especificaRazonTransmision = arrayData[26];
    gps.rumboRadianes = arrayData[27];
    gps.nivelOcupacion = arrayData[28];
    gps.noic = arrayData[29];
    gps.ipk = arrayData[30];
    gps.gpsId = arrayData[31];
    gps.distanciaParcial = arrayData[32];
    gps.ipkParcial = arrayData[33];
    gps.info = gps_prepararPopup(gps);

    return gps;
}

// Construye componente popup por cada registro gps
// para ser mostrado en mapa
function gps_prepararPopup(gps) {
    var mostrar_es = document.getElementById("mostrar_es");
    var mostrar_no = document.getElementById("mostrar_no");
    var mostrar_noic = document.getElementById("mostrar_noic");

    var np   = "<br><b>Pasajeros:</b> " + gps.totalDia;
    var dt   = "<br><b>Distancia:</b> " + (parseFloat(gps.distanciaParcial) / 1000) + " Km";
    var ipk  = "<br><b>IPK:</b> " + gps.ipkParcial;
    var es   = "<br><b>Entradas:</b> " + gps.entradas + " <b>Salidas:</b> " + gps.salidas;
    var no   = "<br><b>Nivel de ocupaci&oacute;n:</b> " + gps.nivelOcupacion;
    var noic = "<br><b>Ocupaci&oacute;n / capacidad:</b> " + gps.noic;

    var mostrar_info_conteo = true;
    if (gps.gpsId === '0' && gps.totalDia == '0' && gps.entradas == '0' && gps.salidas == '0') {
        mostrar_info_conteo = false;
    }

    if (mostrar_es.value != '1' || !mostrar_info_conteo) {
        es = "";
    }
    if (mostrar_no.value != '1' || !mostrar_info_conteo) {
        no = "";
    }
    if (mostrar_noic.value != '1' || !mostrar_info_conteo) {
        noic = "";
    }
    if (!mostrar_info_conteo) {
        np  = "";        
        dt  = "";
        ipk = "";
    }
    var placa = document.getElementById("splaca");
    if (placa.value == "") {
        dt = ipk = "";
    }

    var str =
            "<b>Fecha:</b> " + gps.fechaServidorDate + "  " +
            "<b>Hora:</b> " + gps.fechaServidor +
            "<br><b>Evento:</b> " + gps.msg +
            "<br><b>Localizaci&oacute;n:</b> " + gps.localizacion +
            np +
            dt + ipk +
            es + no + noic +
            "<br><b>Placa:</b> " + gps.placa;
    return str;
}

// Extrae y crea listado de objetos para registros consultados,
// e inicia marcacion de puntos/eventos en mapa
function gps_cargarDatos(id, veh_sel) {
    
    var select    = document.getElementById(id);
    var msg_datos = document.getElementById("msg-datos");    
    msg_datos.innerHTML = ""; var msg = "";
    msg_datos.className = "form-msg";    

    if (select != null && select.options.length > 0) {
        var datos_gps = new Array();
        var numeracion_establecida = false;

        for (var i = 0; i < select.options.length; i++) {
            var value = select.options[i].value;
            var item_gps = gps_prepararDatos(value);

            if (i == 0) {
                datos_gps.push(item_gps);
            } else if (verPuntoControl && item_gps.esPuntoControl == 1) {
                datos_gps.push(item_gps);
            } else if (verPasajero && item_gps.esPasajero == 1) {
                datos_gps.push(item_gps);
            } else if (verAlarma && item_gps.esAlarma == 1) {
                datos_gps.push(item_gps);
            }
            // added to do test.
            else if (verOtros) {
                datos_gps.push(item_gps);
            }
        }

        // Se inicia marcacion de puntos/eventos en mapa
        addListMarks(datos_gps);
        updateInfoPanel(0);
        enableMap();

    } else {

        // Se notifica a usuario que no se reportan datos
        var splaca = document.getElementById("splaca").value;
        if (splaca == "") {
            msg = "* No se reportan datos.";
        } else {
            splaca = splaca.split(",");
            var movil = splaca[0] + " - " + splaca[1];
            msg = "* M&oacute;vil <b>[" + movil + "]</b> no reporta datos.";
        }                
        
        msg_datos.innerHTML = msg;
        msg_datos.className = "form-msg style-msg";

        // Se restablecen componentes de informacion y mapa
        resultType("ZERO");
        removeAllMarks();
        gps_limpiarCamposInfo("");
    }
}

// Carga informacion en tabla resumen de pagina 'Mis moviles'
function gps_cargarCamposInfo() {

    var placa = document.getElementById("info_placa").value;
    var numi = document.getElementById("info_numeracionInicial").value;
    var numf = document.getElementById("info_numeracionFinal").value;
    var dist_mt = document.getElementById("info_distanciaRecorrida").value;
    var ipk = document.getElementById("info_ipk").value;
    var indicadorGPS = document.getElementById("info_indicadorGPS").value;
    var indicadorGPSTitle = document.getElementById("info_indicadorGPSTitle").value;
    var idGPS = document.getElementById("info_idGPS").value;
    var numDatos = document.getElementById("info_numDatos").value;
    var dist_km = dist_mt / 1000;

    var pasajeros = (numf - numi);
    pasajeros = (pasajeros >= 0) ? pasajeros : 0;

    var oIndicadorGPS = $(".resumen.indicator-gps");
    if (oIndicadorGPS !== null) {
        $(oIndicadorGPS).removeClass("active");
        $(oIndicadorGPS).removeClass("inactive");
        $(oIndicadorGPS).removeClass("unhooked");
        $(oIndicadorGPS).removeClass("reset");
        $(oIndicadorGPS).addClass(indicadorGPS);
        $(oIndicadorGPS).attr("data-gps", idGPS);
        $(oIndicadorGPS).attr("title", indicadorGPSTitle);
    }
    document.getElementById("placa").value = placa;
    document.getElementById("numInicial").value = (numDatos === "0" ? "-" : numi);
    document.getElementById("numFinal").value = (numDatos === "0" ? "-" : numf);
    document.getElementById("numPasajeros").value = (numDatos === "0" ? "-" : pasajeros);
    document.getElementById("distanciaRecorrida").value = (numDatos === "0" ? "-" : dist_km + " Km");
    document.getElementById("ipk").value = (numDatos === "0" ? "-" : ipk);
}

// Limpia campos del panel informativo
function gps_limpiarCamposInfo(val) {
    var campos = ["fecha", "placa", "localizacion", "puntoControl", "numInicial",
        "numFinal", "numPasajeros", "distanciaRecorrida", "ipk"];
    for (var i = 0; i < campos.length; i++) {
        var camp = document.getElementById(campos[i]);
        if (camp != null)
            camp.value = val;
    }

    var currentMark = document.getElementById("currentMark");
    var totalMark = document.getElementById("totalMark");
    if (currentMark !== null) {
        currentMark.value = "0";
    }
    if (totalMark !== null) {
        totalMark.value = "0";
    }
}

var informacion_tabular = false;
var lbl_play = "track-label-play";
var lbl_pause = "track-label-pause";

// Se cambian los nombres de los iconos (-min)
// usados en la pagina 'Mis moviles' (informacion tabular)
function gps_informacionTabular(val) {
    informacion_tabular = val;
    lbl_play += "-min";
    lbl_pause += "-min";
}

// Manipula transicion inicio/pausa de autoconsulta a traves
// de la transicion de estado/señal del componente btn_pp
function gps_iniciaPausaAutoConsulta() {

    var btn_pp = document.getElementById("btn_pp");

    if (btn_pp.className.indexOf("play") > 0) {
        // Se inicia autoconsulta, deja controles para pausa
        // (Actualmente en estado pausa)
        btn_pp.className = lbl_pause;
        autoConsulta = true;
        if (informacion_tabular) {
            gps_iniciarAutoConsultaTabla();
            intervalId = setInterval("gps_iniciarAutoConsultaTabla()", 6000);
        } else {
            disableMap();
            clearInteraction();
            gps_iniciarAutoConsulta();
            intervalId = setInterval("gps_iniciarAutoConsulta()", 8000);
        }
    } else {
        // Se pausa autoconsulta, se deja controles para iniciar
        // (Actualmente en estado consulta)
        btn_pp.className = lbl_play;
        autoConsulta = false;
        clearInterval(intervalId);
    }    
}

// Pausa autoconsulta y notifica en componente
function gps_pausarAutoConsultaAuto() {

    var btn_pp = document.getElementById("btn_pp");
    if (btn_pp.className.indexOf("disable") >= 0) {
        btn_pp.className = lbl_play + "-disable";
    } else {
        btn_pp.className = lbl_play;
    }
    autoConsulta = false;
}

// Reanuda autoconsulta y notifica en componente
function gps_reanudarAutoConsultaAuto() {

    var btn_pp = document.getElementById("btn_pp");
    btn_pp.className = lbl_pause;
    autoConsulta = true;
}

// Cambia estado de componente para inicio de autoconsulta
function gps_iniciaAutoConsulta() {
    
    var btn_pp = document.getElementById("btn_pp");
    btn_pp.className = lbl_play;
    gps_iniciaPausaAutoConsulta();
}

// Permite consultar eventos manualmente si no existe
// consulta en proceso
function gps_permitirConsulta() {
    if (!consultando) {
        gps_pausarAutoConsultaAuto();
        gps_bloquearComponente("btn_consulta", false);
    } else {
        gps_bloquearComponente("btn_consulta", true);
    }
}

// Establece que hubo cambio de parametros y detiene autoconsulta
// que se efectua en el momento
function gps_cambioParametros() {
    cambioParametros = true;

    if (autoConsulta) {
        var btn_pp = document.getElementById("btn_pp");        
        if (btn_pp.className.indexOf("disable") >= 0) {
            btn_pp.className = lbl_play + "-disable";
        } else {
            btn_pp.className = lbl_play;
        }
        autoConsulta = false;
        clearInterval(intervalId);
    }

    gps_permitirConsulta();
}

// Inicia consulta manual y pausa autoconsulta en 'Mis moviles'
function gps_consultarTabla() {
    autoConsulta = true;
    gps_opacaTabla();
    gps_iniciarAutoConsultaTabla(); // Consulta directamente
    gps_pausarAutoConsultaAuto();   // Pausa autoconsulta
}

// Inicia consulta manual en 'Ver mapa'
function gps_consultarGps() {
    autoConsulta = true;
    enableMapRefresh();
    disableMap();
    gps_iniciarAutoConsulta();  // Consulta directamente
}

// Bloquea componente con identificador id segun
// valor especificado en disable
function gps_bloquearComponente(id, disable) {
    var comp = document.getElementById(id);
    if (comp != null) {
        if (disable) {
            comp.disabled = true;
            comp.className = "btn-disable";
        } else {
            comp.disabled = false;
            comp.className = "";
        }
    }
}

// Muestra/Oculta componente identificado por id segun
// valor de variable show
function gps_mostrarComponente(id, show) {
    var comp = document.getElementById(id);
    if (comp != null) {
        if (show) {
            comp.style.display = "block";
            console.log("block");
        } else {
            comp.style.display = "none";
            console.log("none");
        }
    }
}

// Inicia autoconsulta con ajuste de zoom
function gps_autoconsulta() {
    ZOOM_IN_BOUNDS2 = true; 
    gps_iniciaPausaAutoConsulta();    
}

// Habilita/Deshabilita componente grafico de autoconsulta
function gps_habilitarAutoConsulta(habilitar) {
    var autoconsulta = document.getElementById("btn_pp");    
    
    if (habilitar) {        
        autoconsulta.onclick   = gps_autoconsulta;
        autoconsulta.className = lbl_play;
    } else {                
        autoconsulta.onclick   = null;        
        autoconsulta.className = lbl_play + "-disable";
    }        
}

// Establece valores de fecha a componentes graficos
function gps_asignarFechaComp(fecha) {
    var dia  = fecha.getDate();
    var mes  = fecha.getMonth();
    var anio = fecha.getFullYear();

    mes += 1;
    if (mes < 10)
        mes = "0" + mes;

    var sfecha = anio + "-" + mes + "-" + dia;
    var fechaIni = sfecha + " 00:00:00";
    var fechaFin = sfecha + " 23:59:00";    

    document.getElementById("fechaInicio").value = fechaIni;
    document.getElementById("fechaFinal").value = fechaFin;
    
    gps_cambioParametros();
}

// Establece fecha actual en componentes graficos
function gps_fechasHoy() {    
    var fecha = new Date();   
    gps_asignarFechaComp(fecha);
}

// Establece fecha del dia anterior en componentes graficos
function gps_fechasAyer() {    
    var fecha = new Date();
    var fecha_ayer = new Date(fecha.getTime());
    fecha_ayer.setDate(fecha.getDate() - 1);    
    gps_asignarFechaComp(fecha_ayer);
}

// Asigna fechas en componentes graficos segun la
// opcion de fecha predefinida seleccionada en sfecha
function gps_asignarFecha() {
    var sfecha = document.getElementById("sfecha").value;
                
    if (sfecha == 1 || sfecha == 2 || sfecha == 3) {
        gps_fechasHoy();        
    }
    if (sfecha == 4) {
        gps_fechasAyer();
    }
        
    gps_habilitarAutoConsulta(false);
    gps_mostrarComponente('gps-controls', true);

    // Habilita autoconsulta para opcion predefinida 2 (localizacion actual)
    if (sfecha == 2) {
        gps_habilitarAutoConsulta(true);
        gps_mostrarComponente('gps-controls', false);
    }
}

// Establece fecha predefinida (Ninguna) si hay cambio en
// componentes graficos de fecha directamente
function gps_ajusteFecha() {
    $('#sfecha').selectpicker('val', '1');
    gps_cambioParametros();
    gps_habilitarAutoConsulta(false);    
    gps_mostrarComponente('gps-controls', true);
}

// Comprueba si componente fecha-predefinida marca la opcion localizacion-actual
function gps_enLocalizacionActual() {
    var sfecha = document.getElementById("sfecha").value;        
    return sfecha == 2;
}

// Exclusivamente para componente select-picker
// Selecciona opcion en componente select que sea el valor 
// especificado (NORMAL) o lo contenga (INDEXOF)
function gps_selectElement(idcomp, value, type) {
    var s = document.getElementById(idcomp);
    var id = "#" + idcomp;

    if (type == NORMAL) {
        $(id).selectpicker('val', value);
        return true;

    } else {
        if (s != null) {
            for (var i = 0; i < s.length; i++) {
                var v = s.options[i].value;
                if (v.indexOf(value) >= 0) {
                    $(id).selectpicker('val', v);
                    return true;
                }
            }
        }
        return false;
    }
}

// Inicia cierre de aplicacion
function closeApp() {
    document.getElementById("form-close").submit();
}

// Busca y filtra informacion en tabla (registros consultados) de 'Mis moviles'
function gps_buscarInfoGPS() {
    var tbl = document.getElementById("tablaInfoGPS");
    var text = document.getElementById("textoBusqueda").value;
    text = text.toLowerCase();

    if (tbl != null) {

        for (var i = 0; i < tbl.rows.length; i++) {
            tbl.rows[i].style.display = "table-row";
        }

        // Reanuda autoconsulta si no hay texto de busqueda
        if (text == "") {
            gps_reanudarAutoConsultaAuto();
            return;
        }

        // Pausa autoconsulta si existe texto a buscar
        gps_pausarAutoConsultaAuto();

        // Oculta fila cuyo ningun valor de columna coincida
        // con texto buscado, de lo contrario se muestra.
        for (var i = 1; tbl.rows.length; i++) {
            if (typeof tbl.rows[i] == 'undefined')
                return;
            var cols = tbl.rows[i].getElementsByTagName("td");

            var match_data = false;
            for (var j = 0; j < cols.length; j++) {
                var str = cols[j].innerHTML;
                str = str.toLowerCase();
                if (str.indexOf(text) >= 0) {
                    match_data = true;
                    break;
                }
            }
            if (match_data) {
                tbl.rows[i].style.display = "table-row";
            } else {
                tbl.rows[i].style.display = "none";
            }
        }
    }
}

// Verifica si mostrar/ocultar tabla de resumen en 'Mis moviles'
function gps_tablaResumen() {
    var splaca = document.getElementById("splaca");
    var tbl_resumen = document.getElementById("tablaResumenGPS");
    if (splaca != null && splaca.value != "") {
        tbl_resumen.style.display = "block";
    } else {
        tbl_resumen.style.display = "none";
    }
}

// Establece componente select-picker en valor por defecto cadena-vacia
function gps_reset(id_select) {
    var id = "#" + id_select;
    $(id).selectpicker('val', '');        
}

// Comprueba si hay seleccion de grupo/ruta para
// trabajar unicamente con opcion (Localizacion actual) y fecha actual.
function gps_haySeleccionGrupo(seleccion) {        
    
    if (seleccion) {
        gps_fechasHoy();
        $('#sfecha').selectpicker('val', '2');
        document.getElementById("fechaInicio").disabled = true;
        document.getElementById("fechaFinal").disabled = true;
    } else {
        document.getElementById("fechaInicio").disabled = false;
        document.getElementById("fechaFinal").disabled = false;
    }
}
