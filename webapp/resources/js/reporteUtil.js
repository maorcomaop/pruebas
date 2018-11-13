
/*
 1 Puntos de control por vehículo     <!-- v,fi,ff -->        
 2 Producción por vehículo            <!-- v,fi,ff -->                                       
 3 Ruta por vehículo                  <!-- v,fi,ff -->        
 4 Alarmas por vehículo               <!-- v,fi,ff -->        
 5 Nivel de ocupación                 <!-- v,fi,ff,r -->      
 6 Conteos en perímetro por vehículo  <!-- v,fi,ff -->        
 7 Consolidado por empresa            <!-- e,fi,ff -->                          
 8 Comparativo producción por ruta    <!-- rts,fi,ff -->      
 9 Producción por ruta                <!-- r,fi,ff -->        
 11 Vehículos por ruta                <!-- r,fi,ff -->        
 12 Vehículos por alarma              <!-- alm,fi,ff -->      
 13 Estadístico                       <!-- e -->      
 14 Descripción de ruta               <!-- e,r -->                      
 15 Gerencia                          <!-- e,rts,fi,ff -->    
 16 Despachador                       <!-- e,bs,fi,ff,r -->   
 17 Propietario                       <!-- v,fi,ff -->        
 18 Gerencia por vehiculo             <!-- v,fi,ff -->    
 19 Consolidado de liquidacion           <!-- fi,ff,excel -->           
 20 Consolidado vehiculos no liquidados  <!-- fi,ff,excel -->
 21 Liquidacion por liquidador           <!-- liq,fi,ff,pdf -->
 22 Indice pasajeros por kilometro       <!-- fi,ff,excel -->
 23 Consolidado de rutas                 <!-- fi,ff,meta -->
 24 Cumplimiento de ruta por vehiculo    <!-- fi,ff,ruta,meta -->
 25 Cumplimiento de ruta por conductor   <!-- fi,ff,ruta,meta -->
 26 Consolidado despacho                 <!-- fi,ff,ruta,v -->
 27 28 29 Incumplimiento de puntos por ruta    <!-- fi,ff,ruta -->
 30 Calificacion de conductor            <!-- fi,ff,conductor -->
 31 Consolidado productividad por hora   <!-- fi,v,excel -->
 */

// Oculta los componentes graficos que soportan
// los parametros de los reportes
function ocultarParametros() {
    document.getElementById('div-fec').style.display = "none";
    document.getElementById('div-veh').style.display = "none";
    document.getElementById('div-rts').style.display = "none";    
    document.getElementById('div-bs').style.display = "none";
    document.getElementById('div-liq').style.display = "none";
    document.getElementById('div-meta').style.display = "none";

    document.getElementById('div-mult-alm').style.display = "none";
    document.getElementById('div-mult-rts').style.display = "none";
    document.getElementById('div-mult-veh').style.display = "none";
    document.getElementById('div-mult-cond').style.display = "none";

    document.getElementById('opt-trts').style.display = "none";
}

// Muestra los parametros (en componentes graficos)
// segun el reporte seleccionado
function mostrarParametros(idx) {
    var dfec    = document.getElementById("div-fec");
    var dveh    = document.getElementById("div-veh");
    var drts    = document.getElementById("div-rts");    
    var dbs     = document.getElementById("div-bs");
    var dliq    = document.getElementById("div-liq");
    var dmeta   = document.getElementById("div-meta");
    var dmalm   = document.getElementById("div-mult-alm");
    var dmrts   = document.getElementById('div-mult-rts');
    var dmveh   = document.getElementById('div-mult-veh');
    var dmcond  = document.getElementById('div-mult-cond');
    var otrst   = document.getElementById('opt-trts');
    var srts    = document.getElementById('sruta');
    var lblRangoFecha = document.getElementById('lab-rng-fecha');
    var lblUnaFecha   = document.getElementById('lab-una-fecha');
    var fechaFinal    = document.getElementById('fechaFinal');

    var tipoArchivo = document.getElementsByName('tipoArchivo');
    tipoArchivo[0].disabled = false;    // pdf/web
    tipoArchivo[1].disabled = false;    // excel

    if (idx == 1 || idx == 2 ||
        idx == 3 || idx == 4 ||
        idx == 6 || idx == 17) {
        dveh.style.display = "block";
        dfec.style.display = "block";

    } else if (idx == 5) {
        dveh.style.display = "block";
        dfec.style.display = "block";
        drts.style.display = "block";
        otrst.style.display = "block";

    } else if (idx == 7) {
        dfec.style.display = "block";

    } else if (idx == 8 || idx == 9 ||
               idx == 11 || idx == 15) {

        if (idx == 8 || idx == 15) {
            dmrts.style.display = "block";
        } else {
            drts.style.display = "block";
        }
        dfec.style.display = "block";

    } else if (idx == 12) {
        dmalm.style.display = "block";
        dfec.style.display = "block";

    } else if (idx == 13) {
    } else if (idx == 14) {
        drts.style.display = "block";
        otrst.style.display = "block";

    } else if (idx == 16) {
        //dbs.style.display  = "block";
        dfec.style.display = "block";
        drts.style.display = "block";
        otrst.style.display = "block";

    } else if (idx == 18) {
        dfec.style.display = "block";
        dmveh.style.display = "block";

    } else if (idx == 19 || idx == 20 || idx == 22 || idx == 35) {
        dfec.style.display = "block";
        tipoArchivo[0].disabled = true;
        tipoArchivo[1].disabled = false;
        tipoArchivo[1].checked = true;

    } else if (idx == 21) {
        dliq.style.display = "block";
        dfec.style.display = "block";

    } else if (idx == 23) {
        dfec.style.display = "block";
        dmeta.style.display = "block";
        tipoArchivo[0].disabled = false;
        tipoArchivo[1].disabled = true;
        tipoArchivo[0].checked = true;

    } else if (idx == 24 || idx == 25) {
        dfec.style.display = "block";
        drts.style.display = "block";
        dmeta.style.display = "block";
        tipoArchivo[0].disabled = true;
        tipoArchivo[1].disabled = false;
        tipoArchivo[1].checked = true;
        if (idx == 25) {
            tipoArchivo[0].disabled = false;
            otrst.style.display = "block";
        }

    } else if (idx == 26) {
        dfec.style.display = "block";
        drts.style.display = "block";
        dmveh.style.display = "block";
        otrst.style.display = "block";
        tipoArchivo[0].disabled = false;
        tipoArchivo[0].checked = true;
        tipoArchivo[1].disabled = true;
        
    } else if (idx == 27) {
        dfec.style.display = "block";
        drts.style.display = "block";
        tipoArchivo[0].disabled = false;
        tipoArchivo[0].checked  = true;
        tipoArchivo[1].disabled = true;
    
    } else if (idx == 30) {
        dfec.style.display = "block";
        dmcond.style.display = "block";
        tipoArchivo[0].checked  = true;
        tipoArchivo[1].disabled = true;
    } else if (idx == 31) {
        dfec.style.display = "block";
        dmveh.style.display = "block";
        tipoArchivo[0].disabled = true;
        tipoArchivo[1].checked  = true;
        tipoArchivo[1].disabled = false;        
    }

    // Seleccion de unicamente dia inicial
    // para reporte ruta x vehiculo, vehiculos x ruta, despachador 
    // 3 11 16 31
    if (idx == 3 || idx == 11 || idx == 16 || idx == 31) {
        fechaFinal.readOnly = true;
        fechaFinal.style.display = "none";
        lblRangoFecha.style.display = "none";
        lblUnaFecha.style.display = "block";
    } else {
        fechaFinal.readOnly = false;
        fechaFinal.style.display = "block";
        lblRangoFecha.style.display = "block";
        lblUnaFecha.style.display = "none";
    }
    //limpia selección de todas las rutas para reportes que no usan
    if ((idx === "9" || idx === "11" || idx === "24") && srts.value === "0") {
        srts.value = "";
    }
}

// Da origen a la seleccion de parametros
// segun el reporte seleccionado
function seleccionReporte() {
    var tipoReporte = document.getElementById("tipoReporte").value;
    ocultarParametros();
    mostrarParametros(tipoReporte);

    var msg = document.getElementById("msg");
    msg.setAttribute("class", "form-msg");
    msg.innerHTML = "";
}

// Si existe cambio en fecha inicial y el reporte actualmente
// referenciado es ruta x vehiculo, vehiculos x ruta, despachador,
// producido por hora (3 11 16 31) se iguala valor de fecha final
function actualizarFechaFinal() {

    var tipoReporte = document.getElementById("tipoReporte").value;

    if (tipoReporte == 3 || tipoReporte == 11 || tipoReporte == 16 ||
        tipoReporte == 31) {
        document.getElementById("fechaFinal").value =
        document.getElementById("fechaInicio").value;
    }
}

// Envia peticion de reporte verificando antes
// los valores de parametros necesarios
function generarReporte2() {
    var tipoReporte     = document.getElementById("tipoReporte").value;
    var fechaInicio     = document.getElementById("fechaInicio").value;
    var fechaFinal      = document.getElementById("fechaFinal").value;
    var datosVehiculo   = document.getElementById("splaca").value;  // id,placa,numInterno,capacidad
    var datosMVehiculo  = document.getElementById("smplaca");       // id
    var datosRuta       = document.getElementById("sruta").value;   // id,nombre
    var datosMRuta      = document.getElementById("smruta");        // id,nombre
    var datosMAlarma    = document.getElementById("smalarma");      // id
    var datosBase       = document.getElementById("sbase").value;   // id,nombre
    var datosLiquidador = document.getElementById("sliquidador").value; // id
    var datosMeta       = document.getElementById("smeta").value;   // valor numerico
    var datosMConductor = document.getElementById("smconductor");   // id
    var tipoArchivo     = document.getElementById("tipoArchivo").value;

    var msg = document.getElementById("msg");
    msg.setAttribute("class", "form-msg");
    msg.innerHTML = "";

    var expTipoReporte = /^[0-9]+$/;
    var expFecha       = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    var expVeh         = /^[0-9a-zA-Z,]+$/;

    // Datos de multiples rutas
    var lstrutas = "";
    for (var i = 0; i < datosMRuta.options.length; i++) {
        if (datosMRuta.options[i].selected) {
            var data = datosMRuta.options[i].value;
            data = data.split(",")[0]; // id
            lstrutas += (lstrutas == "") ? data : "," + data;
        }
    }

    // Datos multiples vehiculos
    var lstveh = "";
    for (var i = 0; i < datosMVehiculo.options.length; i++) {
        if (datosMVehiculo.options[i].selected) {
            var data = datosMVehiculo.options[i].value;
            lstveh += (lstveh == "") ? data : "," + data;
        }
    }
    
    // Datos multiples conductor
    var lstcond = "";
    for (var i = 0; i < datosMConductor.options.length; i++) {
        if (datosMConductor.options[i].selected) {
            var data = datosMConductor.options[i].value;
            lstcond += (lstcond == "") ? data : "," + data;
        }
    }
    
    // Datos multiples alarma
    var lstalm = "";
    for (var i = 0; i < datosMAlarma.options.length; i++) {
        if (datosMAlarma.options[i].selected) {
            var data = datosMAlarma.options[i].value;
            lstalm += (lstalm == "") ? data : "," + data;
        }
    }

    // Se verifica los parametros que fueron elegidos
    // segun el tipo de documento seleccionado (divs block)

    var dfec   = document.getElementById("div-fec").style.display;
    var dveh   = document.getElementById("div-veh").style.display;
    var drts   = document.getElementById("div-rts").style.display;    
    var dbs    = document.getElementById("div-bs").style.display;
    var dliq   = document.getElementById("div-liq").style.display;
    var dmeta  = document.getElementById("div-meta").style.display;
    var dmalm  = document.getElementById("div-mult-alm").style.display;
    var dmrts  = document.getElementById("div-mult-rts").style.display;
    var dmveh  = document.getElementById("div-mult-veh").style.display;
    var dmcond = document.getElementById("div-mult-cond").style.display;

    // Se verifica eleccion de reporte
    if (tipoReporte == "" || tipoReporte == "0") {
        msg.innerHTML = "* Seleccione un tipo de reporte.";
        msg.setAttribute("class", "form-msg alert alert-danger");
        return;
    }

    // Para reportes produccion x ruta, vehiculos x ruta,
    // reporte cumplimiento ruta x vehiculo    
    // se verifica seleccion de una ruta
    if (tipoReporte == 9 || tipoReporte == 11 ||
        tipoReporte == 24) {

        if (datosRuta == "" || datosRuta == "0") {
            msg.innerHTML = "* Seleccione una ruta en especifico.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }
    }

    if (dfec == "block") { /* fecha */
        if (expFecha.test(fechaInicio) &&
            expFecha.test(fechaFinal)) {

            var fi = fechaInicio.split("-");
            var ff = fechaFinal.split("-");

            var anio_fi = fi[0];
            var anio_ff = ff[0];
            var mes_fi = fi[1];
            var mes_ff = ff[1];
            var dia_fi = fi[2];
            var dia_ff = ff[2];

            if (anio_ff < anio_fi ||
               (anio_ff == anio_fi && mes_ff < mes_fi) ||
               (anio_ff == anio_fi && mes_ff == mes_fi && dia_ff < dia_fi)) {
                msg.innerHTML = "* Especifique correctamente el rango de fechas.";
                msg.setAttribute("class", "form-msg alert alert-danger");
                return;
            }
            
            /* Para reporte 28 - Calificacion de conductor 
             * se restringe intervalo de fechas a un mes */
            
            var mensual = false;
            
            if ((anio_ff-1) == anio_fi) {
                 
                if (mes_fi == 12 && mes_ff == 1 &&
                    dia_ff <= dia_fi) {
                    mensual = true;
                }
            
            } else if (anio_ff == anio_fi) {
                
                if (mes_ff == mes_fi ||
                   ((mes_ff-1) == mes_fi &&
                    dia_ff <= dia_fi)) {
                    mensual = true;
                }
            }
            
            if (!mensual) {
                msg.innerHTML = "* Rango de fechas es de m&aacute;ximo un mes.";
                msg.setAttribute("class", "form-msg alert alert-danger");
                return;
            }
        }
    }
    
    if (dveh == "block") { /* vehiculo */
        if (datosVehiculo == null || datosVehiculo == "" || !expVeh.test(datosVehiculo)) {
            msg.innerHTML = "* Especifique correctamente un veh&iacute;culo.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }
    }
    if (dmveh == "block") { /* multiples vehiculos */
        if (datosMVehiculo == null || datosMVehiculo == "" || lstveh == "") {
            msg.innerHTML = "* Especifique correctamente al menos un veh&iacute;culo.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        } else {
            document.getElementById("smplaca_v").value = lstveh;
        }
    }
    if (drts == "block") { /* ruta */
        if (datosRuta == null || datosRuta == "") {
            msg.innerHTML = "* Especifique correctamente una ruta.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }
    }
    if (dmrts == "block") { /* multiples rutas */
        if (datosMRuta == null || datosMRuta == "" || lstrutas == "") {
            msg.innerHTML = "* Especifique correctamente al menos una ruta.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        } else {
            document.getElementById("smruta_v").value = lstrutas;
        }
    }
    if (dmalm == "block") { /* alarma */
        if (datosMAlarma == null || datosMAlarma == "" || lstalm == "") {
            msg.innerHTML = "* Especifique correctamente al menos una alarma.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        } else {
            document.getElementById("smalarma_v").value = lstalm;
        }
    }
    if (dbs == "block") { /* base */
        if (datosBase == null || datosBase == "") {
            msg.innerHTML = "* Especifique correctamente una base.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }
    }
    if (dliq == "block") { /* liquidador */
        if (datosLiquidador == null || datosLiquidador == "") {
            msg.innerHTML = "* Especifique un liquidador.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }
    }
    if (dmeta == "block") { /* meta */
        if (datosMeta == null || datosMeta == "") {
            msg.innerHTML = "* Especifique un valor meta.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }
    }
    if (dmcond == "block") { /* conductor */
        if (datosMConductor == null || datosMConductor == "" || lstcond == "") {
            msg.innerHTML = "* Especifique correctamente al menos un conductor.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        } else {
            document.getElementById("smconductor_v").value = lstcond;
        }
    }
    document.getElementById("form-generar-reporte").submit();
}

/* ========================================================================== */

function generarReporte() {
    var tipoReporte   = document.getElementById("tipoReporte").value;
    var fechaInicio   = document.getElementById("fechaInicio").value;
    var fechaFinal    = document.getElementById("fechaFinal").value;
    var datosVehiculo = document.getElementById("splaca").value;  // id,placa,numInterno
    //var esteDia     = document.getElementById("esteDia").value;
    var tipoArchivo   = document.getElementById("tipoArchivo").value;

    var msg = document.getElementById("msg");
    msg.setAttribute("class", "form-msg");
    msg.innerHTML = "";

    var expTipoReporte = /^[0-9]+$/;
    var expFecha = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    var expVeh = /^[0-9]+,[a-zA-Z]{3}[0-9]{3},[0-9]+$/;

    if (expTipoReporte.test(tipoReporte) &&
        expFecha.test(fechaInicio) &&
        expFecha.test(fechaFinal) &&
        expVeh.test(datosVehiculo) &&
        //(esteDia == "d" || esteDia == "h") &&
        (tipoArchivo == "r" || tipoArchivo == "w")) {

        var fi = fechaInicio.split("-");
        var ff = fechaFinal.split("-");

        var anio_fi = fi[0];
        var anio_ff = ff[0];
        var mes_fi = fi[1];
        var mes_ff = ff[1];
        var dia_fi = fi[2];
        var dia_ff = ff[2];

        if (anio_ff < anio_fi ||
                (anio_ff == anio_fi && mes_ff < mes_fi) ||
                (anio_ff == anio_fi && mes_ff == mes_fi && dia_ff < dia_fi)) {
            msg.innerHTML = "* Especifique correctamente el rango de fechas.";
            msg.setAttribute("class", "form-msg alert alert-danger");
            return;
        }

        document.getElementById("form-generar-reporte").submit();
    } else {
        msg.innerHTML = "* Especifique correctamente todos los campos.";
        msg.setAttribute("class", "form-msg alert alert-danger");
    }
}