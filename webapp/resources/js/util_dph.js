
/* ========================= Puntos ruta y despacho ========================= */

// Muestra/Oculta puntos de ruta segun valor de variable mostrar.
function mostrarPuntosRuta(mostrar) { 
    var v = (mostrar) ? "block" : "none";
    document.getElementById("divPuntosRuta").style.display = v;
}

// Muestra/Oculta puntos ruta de despacho segun valor de variable mostrar.
function mostrarPuntosRutaDph(mostrar) {
    var v = (mostrar) ? "block" : "none";
    document.getElementById("divPuntosRutaDph").style.display = v;
}

/*
 * Visualiza los puntos de una ruta en el mapa.
 * Filtra puntos y bases correspondientes de la
 * ruta en las tablas.
 */
function marcarRuta_dph() {
    
    var sruta = document.getElementById("sruta");
    var ruta  = sruta.value;
    
    mostrarPuntosRuta(false);
    mostrarPuntosRutaDph(false);
    
    if (ruta == "") {
        removePath();        
        return;
    }            
    
    // Visualiza puntos de ruta seleccionada
    // idRuta, nom, lat, lon >> Lista con puntos de cada ruta    
    visualiza_dph ("lstpuntosruta", ruta);   
    
    // Filtra lista despacho por ruta seleccionada
    // idDph, idRuta
    filtra_dph ("sdespacho", ruta);
    
    // Muestra bloque tablaPuntosRuta
    mostrarPuntosRuta(true);
    // Fitra valores en tablaPuntosRuta segun ruta seleccionada
    $('#tablaPuntosRuta').DataTable().columns(1).search(ruta).draw();    
}

// Muestra y filtra puntos relacionados a despacho seleccionado de alguna ruta.
function marcarDespacho_dph() {
    
    var despacho = document.getElementById("sdespacho").value;
    
    mostrarPuntosRuta(false);
    mostrarPuntosRutaDph(false);
    
    if (despacho != "" && despacho.indexOf(",") > 0) {
        var dph = despacho.split(",")[0];        
        mostrarPuntosRutaDph(true);
        $('#tablaPuntosRutaDph').DataTable().columns(0).search(dph).draw();
    } else {
        //marcarRuta_dph();
        mostrarPuntosRuta(true);
    }
}

/*
 * Visualiza puntos/bases pertenecientes a una ruta en el mapa.
 */
function visualiza_dph(nselect, ruta) {
    
    removePath();
    
    var listaPuntos = document.getElementById(nselect).options;        
    for (var i = 0; i < listaPuntos.length; i++) {
        var prbd    = listaPuntos[i].value;
        var idRuta  = prbd.split(",")[0];
        var nom     = prbd.split(",")[1];
        var lat     = prbd.split(",")[2];
        var lon     = prbd.split(",")[3];        
        
        if (idRuta == ruta) {
            addNewMarkExternMult (nom, lat, lon);
        }
    }
    
    disableDrag();
}

/*
 * Filtra lista despacho por ruta.
 */
function filtra_dph(nselect, ruta) {
    
    var listaDph = document.getElementById(nselect);
    var size = listaDph.options.length;    
    
    nselect = "#" + nselect;
    listaDph.selectedIndex = "0";
    
    for (var i = 1; i < size; i++) {
        var elemt_val  = listaDph.options[i].value;
        var ruta_val   = elemt_val.split(",")[1];
        var option_val = nselect + " option[value='"+ elemt_val +"']";
         
        $(option_val).show();        
        if (ruta != "" && ruta_val != ruta) {
            $(option_val).hide();            
        }
        $(nselect).selectpicker("refresh");
    }
}

/*
 * Extrae informacion de tablaPuntosRuta y tablaBasesRuta que
 * posteriormente se envia en formulario de actualizacion.
 */
var TBL_PR  = 1;
var TBL_DPH = 2;
var LIM_MAX = 5760;

// Extrae, valida y envia datos para actualzacion informacion
// de puntos ruta normal o despacho.
function sendPuntosRutaUPD_dph() {
    
    // Se extiende/muestra todas las filas de tabla dinamica
    // para ser procesada completamente.
    $('#tablaPuntosRuta').DataTable().rows().nodes().page.len(-1).draw();
    
    var msg      = document.getElementById("msg");
    
    var data        = extraePuntosRutaUPD_dph();
    var data_dph    = extraeDespachoUPD_dph();
    var ruta        = document.getElementById("sruta").value;    
    var despacho    = document.getElementById("sdespacho").value;
    var chk_ruta    = document.getElementById("chk_ruta");
    var actualizarRuta = (chk_ruta.checked) ? "1" : "0";
    
    if (ruta == "") {
        msg.innerHTML = "* Seleccione una ruta.";
        msg.setAttribute("class", "form-msg alert alert-info");
        return;
    }
    
    if (despacho != "") {
        
        // Verifica seleccion de puntos ruta para despacho
        if (!verificaDespacho_dph(data_dph)) {
            msg.innerHTML = "* Marque al menos dos puntos de ruta.";
            msg.setAttribute("class", "form-msg alert alert-info");
        
        // Verifica valor de variables numericas (tiempos)
        } else if (!verificaValores(TBL_DPH, data_dph)) {
            msg.innerHTML = "* Valores especificados incorrectos. Valores permitidos desde 0 - " + LIM_MAX + ".";
            msg.setAttribute("class", "form-msg alert alert-info");
            
        } else {
            despacho = despacho.split(",")[0];
            document.getElementById("idRuta").value         = ruta;
            document.getElementById("idDespacho").value     = despacho;
            document.getElementById("listaDespacho").value  = data_dph;            
            document.getElementById("actualizarRuta").value = actualizarRuta;
            document.getElementById("form-actualiza-ruta").submit();
        }
        return;
    }
              
    if (data != "") {
        
        // Verifica valor de variables numericas (tiempos)
        if (!verificaValores(TBL_PR, data)) {
            msg.innerHTML = "* Valores especificados incorrectos. Valores permitidos desde 0 - " + LIM_MAX + ".";
            msg.setAttribute("class", "form-msg alert alert-info");
            return;
        }
        document.getElementById("idRuta").value          = ruta;
        document.getElementById("listaPuntosRuta").value = data;    
        document.getElementById("idDespacho").value      = "";
        document.getElementById("listaDespacho").value   = "";
        document.getElementById("actualizarRuta").value  = "";
        document.getElementById("form-actualiza-ruta").submit();
    }
}

// Descompone cadena formateada data y 
// verifica que campos de tiempo sean
// valores numericos y dentro del rango.
function verificaValores(tbl, data) {
    
    var arr_data = data.split("&");
    var COL_MIN, COL_MAX;
    
    for (var i = 0; i < arr_data.length; i++) {
        var item_data = arr_data[i].split(",");        
        if (tbl == TBL_PR) {
            COL_MIN = col.PROMEDIO_MINUTOS-1; 
            COL_MAX = col.VALOR_PUNTO-1;
        } else {
            COL_MIN = col_dph.TIEMPO_PROMEDIO;
            COL_MAX = col_dph.TIEMPO_PICO;
        }
        for (var j = COL_MIN; j <= COL_MAX; j++) {
            var v = parseInt(item_data[j]);
            if (v < 0 || v > LIM_MAX) return false;
        } 
    }
    return true;
}

// Descompone cadena formateada str y 
// verifica que al menos dos puntos hayan
// sido seleccionados.
function verificaDespacho_dph(str) {
    var n = 0;
    
    var lst = str.split("&");
    for (var i = 0; i < lst.length; i++) {        
        var arr = lst[i].split(",");
        if (arr[col_dph.NOMBRE_PUNTO] == "1") {
            n++;
        }
    }
    if (n >= 2) return true;
    return false;
}


/*
 * Inicia extraccion de puntos ruta.
 * Retorna listado de informacion de puntos como cadena formateada.
 */
function extraePuntosRutaUPD_dph() {
    
    var data = extrae_dph("tablaPuntosRuta");    
    
    if (data.indexOf(",") < 0) return "";
    return data;
}

/*
 * Inicia extraccion de puntos ruta despacho.
 * Retorna listado de informacion de puntos despacho como cadena formateada.
 */
function extraeDespachoUPD_dph() {
    
    var data = extraeDph_dph("tablaPuntosRutaDph");
    
    if (data.indexOf(",") < 0) return "";
    return data;
}

var col = {
    ID: 0,
    ID_RUTA: 1,
    ID_PUNTO: 2,
    CODIGO_PUNTO: 3,
    NOMBRE_PUNTO: 4,
    PROMEDIO_MINUTOS: 5,
    HOLGURA_MINUTOS: 6,
    VALOR_PUNTO: 7,
    TIPO: 8
};

/*
 * Extrae informacion desde tablaPuntosRuta.
 * Itera sobre cada fila y columna de la tabla, para extraer
 * cada valor, que sera especificado en cadena de texto formateada.
 */
function extrae_dph(nombreTabla) {
    
    var tabla = document.getElementById(nombreTabla);
    var data  = "";
    
    // Procesamiento de puntos/bases
    // FormatoSalida: &*  id, idRuta, idPunto, codigoPunto, promMin, holguraMin, valorPunto, tipo
    
    for (var i = 0; i < tabla.rows.length; i++) {        
        var cellsOfRow = tabla.rows[i].getElementsByTagName("td");
        var str = "";
        for (var j = 0; j < cellsOfRow.length; j++) {
            
            var cell = cellsOfRow[j];
            
            if (j == col.ID)           { str = cell.innerHTML; }
            if (j == col.ID_RUTA)      { str += "," + cell.innerHTML; }
            if (j == col.ID_PUNTO)     { str += "," + cell.innerHTML; }
            if (j == col.CODIGO_PUNTO) { str += "," + cell.innerHTML; }            
            
            if (j == col.PROMEDIO_MINUTOS ||
                j == col.HOLGURA_MINUTOS ||
                j == col.VALOR_PUNTO) {
                var text = cell.getElementsByTagName("input")[0];
                str += "," + text.value;
            }
            if (j == col.TIPO) {
                var select = cell.getElementsByTagName("select")[0];
                str += "," + select.value;
            }
        }
        
        if  (data == "") data = str;
        else data += "&" + str;
    }
    
    return data;
}



var col_dph = {
    ID_DESPACHO: 0,
    ID_PUNTORUTA: 1,
    ID_PUNTO: 2,
    CODIGO_PUNTO: 3,
    NOMBRE_PUNTO: 4,
    TIEMPO_PROMEDIO: 5,
    TIEMPO_HOLGURA: 6,
    TIEMPO_VALLE: 7,
    TIEMPO_PICO: 8
};

/*
 * Extrae informacion desde tablaPuntosRutaDph.
 * Itera sobre cada fila y columna de la tabla, para extraer
 * cada valor, que sera especificado en cadena de texto formateada.
 */
function extraeDph_dph(nombreTabla) {
    
    var tabla = document.getElementById(nombreTabla);
    var data  = "";
    
    // Procesamiento de puntos/bases
    // FormatoSalida: &* idDespacho, idPuntoRuta, idPunto, codigoPunto, 1-0 enDespacho, promMin, holguraMin, tiempoValle, tiempoPico
    
    for (var i = 0; i < tabla.rows.length; i++) {        
        var cellsOfRow = tabla.rows[i].getElementsByTagName("td");
        var str = "";
        for (var j = 0; j < cellsOfRow.length; j++) {
            
            var cell = cellsOfRow[j];
            
            if (j == col_dph.ID_DESPACHO)     { str = cell.innerHTML; }
            if (j == col_dph.ID_PUNTORUTA)    { str += "," + cell.innerHTML; }
            if (j == col_dph.ID_PUNTO)        { str += "," + cell.innerHTML; }
            if (j == col_dph.CODIGO_PUNTO)    { str += "," + cell.innerHTML; }
            if (j == col_dph.NOMBRE_PUNTO) { 
                var chk = cell.getElementsByTagName("input")[0];
                var v = (chk.checked) ? "1" : "0";
                str += "," + v;
            }
            
            if (j == col_dph.TIEMPO_PROMEDIO ||
                j == col_dph.TIEMPO_HOLGURA ||
                j == col_dph.TIEMPO_VALLE ||
                j == col_dph.TIEMPO_PICO) {                
                var text = cell.getElementsByTagName("input")[0];
                str += "," + text.value;
            }            
        }
        
        if  (data == "") data = str;
        else data += "&" + str;
    }
    
    return data;
}

// Establece en componentes graficos ruta que viene de ser creada/modificada.
function verificaRutaEditada() {
    
    var ruta_edt = document.getElementById("ruta_editada").value;
    var sruta = document.getElementById("sruta");
    
    if (ruta_edt != "" && parseInt(ruta_edt) > 0) {
        $('#sruta').val(ruta_edt);        
        $('#sruta').selectpicker("refresh");        
        selectElementAUX("sruta", ruta_edt);
        marcarRuta_dph();
    }    
}