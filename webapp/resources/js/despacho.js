
// Muestra/Oculta registros de tabla con informacion de puntos (tiempos) 
// asociados a ruta especifica, cuando es seleccionada.
// Muestra/Oculta bloque de punto-retorno.
function dph_mostrarRuta() {
    var ruta = document.getElementById("sruta").value;
    if (ruta == "") {
        $('#dph-puntoretorno').hide();
        $('#dph-ruta').hide();
        $('#tablaPuntosRutaDph').hide();
        return;
    }
    // Filtra tabla solo para puntos de ruta
    $('#tablaPuntosRutaDph').DataTable().columns(0).search(ruta).draw();
    $('#dph-puntoretorno').show();
    $('#dph-ruta').show();
    $('#tablaPuntosRutaDph').show();
}

// Muestra/Oculta bloque punto-retorno segun valor seleccionado en
// spuntoretorno.
function dph_mostrarPuntoRetorno() {
    var puntoretorno = document.getElementById("spuntoretorno").value;
    if (puntoretorno == "") {
        $('#dph-puntoretorno').hide();
        return;
    }
    $('#dph-puntoretorno').show();
}

// Muestra/Oculta bloques de grupo movil segun valor seleccionado en
// sgrupomovil. Filtra registros en tabla solo para moviles
// pertenecientes a grupo seleccionado.
function dph_mostrarGrupoMovil() {
    var grupo_movil = document.getElementById("sgrupomovil").value;
    if (grupo_movil == "") {
        $('#dph-tabla-grupomovil').hide();
        $('#dph-tabla-grupomovil table').hide();
        $('#tablaGrupoMovilesDph').hide();                
        $('#tablaGrupoMovilesDph_wrapper').hide();                
        return;
    }

    //dph_uncheckedGrupo();
    dph_filtrarFilas('tablaGrupoMovilesDph', grupo_movil);
    $('#dph-tabla-grupomovil').show();
    $('#dph-tabla-grupomovil table').show();
    $('#tablaGrupoMovilesDph').show();        
    $('#tablaGrupoMovilesDph_wrapper').show();  
    var tbl = $('#tablaGrupoMovilesDph').dataTable();
    tbl.fnAdjustColumnSizing();    
}

// Filtra filas de tabla identificada por id_tbl cuyos
// valores de primer columna que correspondan con value.
function dph_filtrarFilas(id_tbl, value) {

    var tbl = document.getElementById(id_tbl);
    var tbody = tbl.getElementsByTagName("tbody")[0];
    var tr = tbody.getElementsByTagName("tr");
    var size = tr.length;

    for (var i = size - 1; i >= 0; i--) {   // >= 0
        var td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML == value) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

// Muestra/Oculta bloque ruta segun estado actual del bloque
// y valor seleccionado para sruta.
function dph_mostrarBloqueRuta() {

    var ruta = document.getElementById("sruta");
    var dph_ruta_visible = $('#dph-ruta').is(':visible');

    if (ruta == null) {
        (!dph_ruta_visible)
            ? $('#dph-ruta').show()
            : $('#dph-ruta').hide();        

    } else {

        (!dph_ruta_visible && ruta.value != "")
            ? $('#dph-ruta').show()
            : $('#dph-ruta').hide();        
    }
}

// Muestra/Oculta bloque de punto-retorno segun estado del bloque
// y valor seleccionado para sruta.
function dph_mostrarBloquePuntoRetorno() {
    
    var ruta = document.getElementById("sruta");
    var dph_pr_visible = $('#dph-puntoretorno').is(':visible');

    if (ruta == null) {
        (!dph_pr_visible) 
            ? $('#dph-puntoretorno').show()
            : $('#dph-puntoretorno').hide();    

    } else {

        (!dph_pr_visible && ruta.value != "")
            ? $('#dph-puntoretorno').show()
            : $('#dph-puntoretorno').hide();            
    }
}

// Muestra/Oculta bloque y tabla de grupo movil segun sus estados
// y valor seleccionado para sgrupomovil.
function dph_mostrarBloqueGrupoMovil() {

    var grupo_movil = $('#sgrupomovil').val();
    var dph_grupo = $('#dph-grupomovil').is(":visible");
    var dph_tabla = $('#dph-tabla-grupomovil').is(":visible");

    if (dph_grupo || dph_tabla) {
        $('#dph-grupomovil').hide();
        $('#dph-tabla-grupomovil').hide();
    } else {
        if (grupo_movil != "") {
            $('#dph-grupomovil').show();
            $('#dph-tabla-grupomovil').show();
        } else {
            $('#dph-grupomovil').show();
        }
    }
}

var MIN_PUNTOS = 2;
var MIN_VEH = 1;

// Comprueba si valor numerico val se encuentra en intervalo [a, b].
function dph_valorNumerico(val, a, b) {
    var intval = parseInt(val);
    if (exp_numerico.test(val) &&
       (intval >= a && intval <= b)) {
       return true;
    }
    return false;
}

// Extrae, formatea (listados) y comprueba valores de los campos
// especificados para la configuracion de un despacho.
// Una vez se comprueban los valores, son enviados para almacenamiento
// o edicion segun el parametro tipoEvento.
function dph_procesarConfiguracion(tipoEvento) {

    var ruta            = document.getElementById("sruta");
    var tipo_dia        = document.getElementById("stipodia");
    var hora_inicio     = document.getElementById("horaInicio");
    var hora_fin        = document.getElementById("horaFin");
    var intv_valle      = document.getElementById("intervaloValle");
    var intv_pico       = document.getElementById("intervaloPico");
    var horas_pico      = document.getElementById("listaHoraPico");
    var rotar_veh       = document.getElementById("rotarVehiculo");
    var prog_ruta       = document.getElementById("usarProgRuta");
    var sin_movil       = document.getElementById("sinMoviles");
    var grupo_movil     = document.getElementById("sgrupomovil");
    var tiempo_salida   = document.getElementById("tiempoSalida");
    var horas_trabajo   = document.getElementById("horasTrabajo");
    var lim_conservacion= document.getElementById("limiteConservacion");
    var nombre          = document.getElementById("nombreDespacho");
    // Para programacion de ruta
    var nveh_retorno    = document.getElementById("cantidadMoviles_rt");

    var msg = document.getElementById("msg");
    var smsg = "";

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    var hi = hora_inicio.value;
    var hf = hora_fin.value;
    var lst_pr = "";

    if (ruta != null && ruta.value == "") {
        smsg = "* Especifique una ruta de despacho.";
    } else if (tipo_dia.value == "") {
        smsg = "* Especifique un tipo de dia.";
    } else if (hora_inicio.value == "") {
        smsg = "* Especifique una hora de inicio.";
    } else if (hf.localeCompare(hi) <= 0) {
        smsg = "* Hora final debe ser superior a hora de inicio.";
    } else if (hora_fin.value == "") {
        smsg = "* Especifique una hora de fin.";
    } else if (!dph_horaValida(hi)) {
        smsg = "* Especifique una hora de inicio correctamente."
    } else if (!dph_horaValida(hf)) {
        smsg = "* Especifique una hora fin correctamente."
    } else if (!dph_valorNumerico(intv_valle.value, 0, 480)) {
        smsg = "* Especifique un intervalo de hora valle. Valor permitido entre 0 y 480.";
    } else if (!dph_valorNumerico(intv_pico.value, 0, 480)) {
        smsg = "* Especifique un intervalo de hora pico. Valor permitido entre 0 y 480.";
    } else if (!dph_valorNumerico(tiempo_salida.value, 0, 180)) {
        smsg = "* Especifique un tiempo de salida. Valor permitido entre 0 y 180.";
    } else if (!dph_valorNumerico(horas_trabajo.value, 1, 24)) {
        smsg = "* Especifique las horas de trabajo. Valor permitido entre 1 y 24.";
    } else if (!dph_valorNumerico(lim_conservacion.value, 0, 90)) {
        smsg = "* Especifique un l&iacute;mite de conservaci&oacute;n. Valor permitido entre 0 y 90.";
    } else if (nombre.value == "" || !exp_alfanumerico_esp.test(nombre.value)) {
        smsg = "* Especifique el nombre del despacho correctamente. Valores alfanum&eacute;ricos y s&iacute;mbolos -_ permitidos.";
    } else if (!dph_haySeleccionPuntos(false)) {
        smsg = "* Especifique al menos " + MIN_PUNTOS + " puntos de control.";
    } else {
        
        // Filtrado y extraccion de puntos
        // Se crea listado de puntos
        dph_filtrarPuntos();
        lst_pr = dph_extraerPuntosRuta();

        if (lst_pr == "") {
            smsg = "* Especifique los puntos de control y/o tiempos de llegada del despacho.";
            smsg += " &Uacute;nicamente valores entre 0 - " + LIM_MAX + ".";
        }
    }
    
    if (smsg != "") {
        msg.innerHTML = smsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }   
    
    var id_puntos_ruta, puntos_ruta, tiempo_referencia, tiempo_holgura, tiempo_llegada_valle, tiempo_llegada_pico;
    if (lst_pr instanceof Array) {
        id_puntos_ruta = lst_pr[0];
        puntos_ruta         = lst_pr[1];
        tiempo_referencia   = lst_pr[2];
        tiempo_holgura      = lst_pr[3];
        tiempo_llegada_valle = lst_pr[4];
        tiempo_llegada_pico  = lst_pr[5];
    }
            
    var lst_gm, lst_veh, lst_veh_ret;
    var vprog_ruta = '0';
    
    if (sin_movil.checked) {        
        // >>> Sin asignacion de moviles/ruta programada
        
        lst_veh     = "NODATA";
        lst_veh_ret = "NODATA";
    
    } else if (prog_ruta.checked) {        
        // >>> Con asignacion de ruta programada
        
        // Valida parametros de punto-retorno si se ha especificado
        if (!dph_validaParametrosPuntoRetorno()) { return; }                
        if (!dph_valorNumerico(nveh_retorno.value, 0, 4096)) {
            smsg = "* Especifique la cantidad de veh&iacute;culos en punto retorno. Valor permitido entre 0 y 4096.";
        }
        lst_veh     = "NODATA";
        lst_veh_ret = "NODATA";
        vprog_ruta  = '1';
        
    } else {        
        // >>> Con asignacion de moviles
        
        // Valida parametros de punto-retorno si se ha especificado
        if (!dph_validaParametrosPuntoRetorno()) {
            return;
        }
        // Valida si hubo seleccion de vehiculos
        if (!dph_haySeleccionVehiculos()) {
            return;
        }
        
        // Filtrado y extraccion de vehiculos
        // Se crea listado de vehiculos
        dph_filtrarGrupo();
        lst_gm = dph_extraerVehiculos();
        
        lst_veh = lst_veh_ret = "";
        lst_veh = lst_gm[0];
        if (lst_gm.length == 2) {
            lst_veh_ret = lst_gm[1];
        }
        
        // Comprueba asignacion de vehiculos para cuando
        // exista o no punto-retorno
        var error_grupomovil = (grupo_movil.value == "") ? true : false;        
        if (dph_hayPuntoRetorno()) {
            if (lst_veh     == "" && 
                lst_veh_ret == "") {
                error_grupomovil = true;
            } else if (lst_veh == "") {
                lst_veh     = "NODATA";
            } else if (lst_veh_ret == "") { 
                lst_veh_ret = "NODATA";
            }
        } else {
            if (lst_veh == "") {
                error_grupomovil = true;
            } else {
                lst_veh_ret = "NODATA";
            }
        }
        if (error_grupomovil) { 
            smsg = "* Especifique los vehiculos del despacho o programe la ruta.";
        }
    }
        
    var vrotar_veh = (rotar_veh.checked) ? '1' : '0';    

    // Extrae y formatea rango de horas pico en cadena de texto
    var listaHoraPico = document.getElementById("listaHoraPico").value;
    listaHoraPico = listaHoraPico.split("  ");
    var horaPico = "";
    if (listaHoraPico.length >= 1) {
        horaPico = listaHoraPico[0];
        for (var i = 1; i < listaHoraPico.length; i++)
            horaPico += "," + listaHoraPico[i];
    }

    if (smsg != "") {
        msg.innerHTML = smsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    } else {
        
        // Se adiciona informacion no obtenida de forma directa
        document.getElementById("idPuntosRuta").value = id_puntos_ruta;
        document.getElementById("puntosRuta").value = puntos_ruta;
        document.getElementById("tiempoReferencia").value = tiempo_referencia;
        document.getElementById("tiempoHolgura").value = tiempo_holgura;
        document.getElementById("tiempoLlegadaValle").value = tiempo_llegada_valle;
        document.getElementById("tiempoLlegadaPico").value = tiempo_llegada_pico;
        document.getElementById("grupoMoviles").value = lst_veh;
        document.getElementById("vrotarVehiculo").value = vrotar_veh;
        document.getElementById("vprogramarRuta").value = vprog_ruta;
        document.getElementById("tipoEvento").value = tipoEvento;
        document.getElementById("listaHoraPico").value = horaPico;
        
        // Informacion de punto retorno
        document.getElementById("grupoMoviles_rt").value = lst_veh_ret;

        if (tipoEvento == 1) {
            $('#form-nuevo-despacho').submit();
        } else {
            $('#form-edita-despacho').submit();
        }
    }
}

// Verifica que valor hora presente el formato hh:mm y valores correctos.
function dph_horaValida(hora) {

    var exp_hora = /^[0-9]{2}:[0-9]{2}$/;

    if (exp_hora.test(hora)) {
        var hh = parseInt(hora.split(":")[0]);
        var mm = parseInt(hora.split(":")[1]);

        if (hh >= 0 && hh <= 23 &&
            mm >= 0 && mm <= 59) {
            return true;
        }
    }
    return false;
}

// Columnas de tablas puntosRuta y grupoMoviles.
var pr_col = {
    ID_RUTA: 0,
    ID_PUNTO: 1,
    COD_PUNTO: 2,
    NOM_PUNTO: 3,
    PUNTO_RETORNO: 4,
    TIEMPO_ESTABLECIDO: 5,
    TIEMPO_HOLGURA: 6,
    TIEMPO_VALLE: 7,
    TIEMPO_PICO: 8
};

var gm_col = {
    ID_GRUPO: 0, 
    NUM_INTERNO: 1, 
    PLACA: 2,
    INICIA_PUNTO_RETORNO: 3
};

var LIM_MAX = 5760;

// Extrae informacion de puntos de control
// en cadenas de texto formateadas individuales.
// Se generan (6) listados.
function dph_extraerPuntosRuta() {
    var tpr = document.getElementById("tablaPuntosRutaDph");

    var lst_idptos, lst_ptos, lst_treferencia, lst_tholgura, lst_tvalle, lst_tpico;
    lst_idptos = lst_ptos = lst_treferencia = lst_tholgura = lst_tvalle = lst_tpico = "";

    for (var i = 0; i < tpr.rows.length; i++) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {

            if (j == pr_col.ID_PUNTO) {
                var data = cols[j].innerHTML;
                lst_idptos += (lst_idptos == "")
                        ? data : "," + data;
            }

            if (j == pr_col.COD_PUNTO) {
                var select = cols[j].getElementsByTagName("select");
                var data = "";
                if (select != null && select.length > 0) {
                    select = select[0];
                    data = select.value;
                } else {
                    data = cols[j].innerHTML;
                }
                lst_ptos += (lst_ptos == "")
                        ? data : "," + data;
            }

            if (j == pr_col.TIEMPO_ESTABLECIDO) {
                var data = cols[j].innerHTML;
                lst_treferencia += (lst_treferencia == "")
                        ? data : "," + data;
            }

            if (j == pr_col.TIEMPO_HOLGURA ||
                j == pr_col.TIEMPO_VALLE ||
                j == pr_col.TIEMPO_PICO) {

                var text = cols[j].getElementsByTagName("input");
                var data = text[0].value;

                //if(!$.isNumeric(data) || data.indexOf("-") >= 0) {
                //  return "";
                //}
                // Comprueba valor numerico de columnas que lo necesitan
                var int_data = parseInt(data);
                if (!exp_numerico.test(data) || int_data < 0 || int_data > LIM_MAX) {
                    return "";
                }

                if (j == pr_col.TIEMPO_HOLGURA) {
                    lst_tholgura += (lst_tholgura == "")
                            ? data : "," + data;

                } else if (j == pr_col.TIEMPO_VALLE) {
                    lst_tvalle += (lst_tvalle == "")
                            ? data : "," + data;
                } else {
                    lst_tpico += (lst_tpico == "")
                            ? data : "," + data;
                }
            }
        }
    }

    if (lst_idptos != "" &&
        lst_ptos != "" &&
        lst_treferencia != "" &&
        lst_tholgura != "" &&
        lst_tvalle != "" &&
        lst_tpico != "") {
        return new Array(lst_idptos, lst_ptos, lst_treferencia, lst_tholgura, lst_tvalle, lst_tpico);
    }
    return "";
}

// Extrae informacion de vehiculos
// en cadenas de texto formateadas individuales.
// Se generan (1 o 2) listados.
function dph_extraerVehiculos() {

    var grupo_movil = document.getElementById("sgrupomovil").value;
    var tgm = document.getElementById("tablaGrupoMovilesDph");

   var lst_veh     = "",
       lst_veh_ret = "";
   var hay_puntoretorno = dph_hayPuntoRetorno();

    for (var i = 0; i < tgm.rows.length; i++) {
        
        var cols = tgm.rows[i].getElementsByTagName("td");
        var es_grupo = false;
        var veh_en_retorno = false;
        var data = "";
        var add_data = false;

        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.ID_GRUPO &&
                cols[j].innerHTML == grupo_movil) {
                es_grupo = true;                
            }
            if (j == gm_col.PLACA && es_grupo) {
                data = cols[j].innerHTML;                                
                if (!exp_alfanumerico_noesp.test(data)) return "";
            }            
            if (j == gm_col.INICIA_PUNTO_RETORNO && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (chk.checked) { veh_en_retorno = true; }
                add_data = true;
            }   
            if (data != "" && add_data) {
                if (hay_puntoretorno) {
                    if (veh_en_retorno) {
                        lst_veh_ret += (lst_veh_ret == "") ? data : "," + data;
                    } else {
                        lst_veh += (lst_veh == "") ? data : "," + data;
                    }
                } else {
                    lst_veh += (lst_veh == "") ? data : "," + data;
                }                
            }
        }
    }
    
    if (hay_puntoretorno) {
        return new Array(lst_veh, lst_veh_ret);
    } else {
        return new Array(lst_veh);
    }
}

// Transfiere valores de tiempo referencia a tiempo valle y/o tiempo pico.
function dph_usarTiempoEstablecido() {
    var tpr = document.getElementById("tablaPuntosRutaDph");
    var usarEnHoraPico = document.getElementById("usarEnHoraPico").checked;

    for (var i = 0; i < tpr.rows.length; i++) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {
            var tiempo = cols[pr_col.TIEMPO_ESTABLECIDO].innerHTML;

            if (j == pr_col.TIEMPO_VALLE) {
                cols[j].getElementsByTagName("input")[0].value = tiempo;
            }
            if (j == pr_col.TIEMPO_PICO && usarEnHoraPico) {
                cols[j].getElementsByTagName("input")[0].value = tiempo;
            }
        }
    }
}

// Comprueba si hay seleccion minima de puntos, al menos MIN_PUNTOS.
function dph_haySeleccionPuntos(imprimir_msg) {
    var msg = document.getElementById("msg");

    if (dph_seleccionPuntos() < MIN_PUNTOS) {
        if (imprimir_msg) {
            msg.innerHTML = "* Especifique al menos " + MIN_PUNTOS + " puntos de control.";
            msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        }
        return false;
    }
    return true;
}

// Comprueba si hay seleccion minima de vehiculos, al menos MIN_VEH.
// Se comprueba antes si hay seleccion de opcion programacion-ruta o 
// sin-moviles para retornar un valor verdadero. 
function dph_haySeleccionVehiculos() {
    
    var prog_ruta   = document.getElementById("usarProgRuta");
    var sin_moviles = document.getElementById("sinMoviles");
    
    if (prog_ruta.checked || sin_moviles.checked) { return true; }
    
    var msg = document.getElementById("msg");
    
    var chk_pgr = document.getElementById("usarProgRuta");
    if (chk_pgr.checked) return true;
    
    if (dph_seleccionVehiculos() < MIN_VEH) {
        msg.innerHTML = "* Especifique al menos " + MIN_VEH + " veh&iacute;culo o programe la ruta.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return false;
    }
    return true;
}

// Extrae cantidad de puntos seleccionados.
function dph_seleccionPuntos() {
    return dph_tamSeleccion("tablaPuntosRutaDph", -1, pr_col.NOM_PUNTO);
}

// Extrae cantidad de vehiculos seleccionados.
function dph_seleccionVehiculos() {
    return dph_tamSeleccion("tablaGrupoMovilesDph", gm_col.ID_GRUPO, gm_col.NUM_INTERNO);
}

// Calcula cantidad de elementos seleccionados en tabla id_tbl
// Si col_grupo es diferente a -1, se contabiliza solo con respecto
// a los elementos asociados a ese grupo, de lo contrario todos son
// contabilizados.
function dph_tamSeleccion(id_tbl, col_grupo, col_item) {
    
    var tbl       = document.getElementById(id_tbl);            
    var id_gmovil = document.getElementById("sgrupomovil").value;
    var size = 0;

    for (var i = tbl.rows.length - 1; i >= 0; i--) {
        var cols = tbl.rows[i].getElementsByTagName("td");
        var es_grupo = false;
        
        for (var j = 0; j < cols.length; j++) {                                                   
            if (col_grupo == -1) { es_grupo = true; }
            else {
                if (j == col_grupo &&
                    cols[j].innerHTML == id_gmovil) {
                    es_grupo = true;
                }
            }
            if (j == col_item && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (chk.checked) {
                    size++;
                    break;
                }
            }
        }
    }
    return size;
}

// Filtra puntos ruta seleccionados.
function dph_filtrarPuntos() {

    if (!dph_haySeleccionPuntos(true)) { return; }

    var tpr = document.getElementById("tablaPuntosRutaDph");

    for (var i = tpr.rows.length - 1; i >= 0; i--) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {
            if (j == pr_col.NOM_PUNTO) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (!chk.checked) {
                    tpr.deleteRow(i);
                    break;
                }
            }
        }
    }
}

// Filtra moviles segun el grupo_movil y movil seleccionado.
function dph_filtrarGrupo() {

    if (!dph_haySeleccionVehiculos()) { return; }

    var tgm = document.getElementById("tablaGrupoMovilesDph");
    var grupo_movil = document.getElementById("sgrupomovil").value;
    
    for (var i = tgm.rows.length-1; i >= 1; i--) {  // >= 0
        var cols = tgm.rows[i].getElementsByTagName("td");
        var es_grupo = false;
        
        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.ID_GRUPO &&
                cols[j].innerHTML == grupo_movil) {
                es_grupo = true;
            }
            if (j == gm_col.NUM_INTERNO && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (!chk.checked) {
                    tgm.deleteRow(i);
                    break;
                }
            }
        }
    }
}

// Deselecciona moviles de un grupo_movil especifico.
function dph_uncheckedGrupo() {

    var tgm = document.getElementById("tablaGrupoMovilesDph");
    var grupo_movil = document.getElementById("sgrupomovil").value;
    
    for (var i = 0; i < tgm.rows.length; i++) {
        var cols = tgm.rows[i].getElementsByTagName("td");
        var es_grupo = false;
        
        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.ID_GRUPO &&
                cols[j].innerHTML == grupo_movil) {
                es_grupo = true;
            }
            if (j == gm_col.NUM_INTERNO && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                chk.checked = false;
                break;
            }
        }
    }
}

// Permite mostrar todos los puntos ruta o unicamente los marcados
// segun valor de variable mostrarTodos.
function dph_mostrarTotalidadPuntos(mostrarTodos) {

    if (!dph_haySeleccionPuntos(true)) { return; }

    var tpr = document.getElementById("tablaPuntosRutaDph");

    for (var i = tpr.rows.length - 1; i >= 0; i--) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {
            if (j == pr_col.NOM_PUNTO) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (mostrarTodos) {
                    tpr.rows[i].style.display = "";
                    break;
                } else {
                    if (!chk.checked) {
                        tpr.rows[i].style.display = "none";
                        break;
                    }
                }
            }
        }
    }
}

// Permite mostrar todo el grupo de vehiculos o unicamente los marcados
// de un grupo_movil especifico, segun valor de variable mostrarTodos.
function dph_mostrarTotalidadGrupo(mostrarTodos) {
    
    if (!dph_haySeleccionVehiculos()) { return; }
    
    var tgm = document.getElementById("tablaGrupoMovilesDph");    
    var grupo_movil = document.getElementById("sgrupomovil").value;
    
    for (var i = tgm.rows.length-1; i >= 0; i--) {
        var cols = tgm.rows[i].getElementsByTagName("td");
        var es_grupo = false;
        
        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.ID_GRUPO &&
                cols[j].innerHTML == grupo_movil) {
                es_grupo = true;
            }            
            if (j == gm_col.NUM_INTERNO && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (mostrarTodos) {
                    tgm.rows[i].style.display = "";
                    break;
                } else {
                    if (!chk.checked) {
                        tgm.rows[i].style.display = "none";
                        break;
                    }
                }
            }                        
        }
        if (!es_grupo && i >= 1) {
            tgm.rows[i].style.display = "none";
        }
    }
}

// Selecciona o deselecciona todos los moviles de un grupo_movil especifico.
// Limpia lista lst_movil que mantiene el orden de los moviles seleccionados.
function dph_marcarGrupo(marcar) {

    var grupo_movil = document.getElementById("sgrupomovil").value;
    var tgm = document.getElementById("tablaGrupoMovilesDph");

    for (var i = 0; i < tgm.rows.length; i++) {
        var cols = tgm.rows[i].getElementsByTagName("td");
        var es_grupo = false;

        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.ID_GRUPO &&
                cols[j].innerHTML == grupo_movil) {
                es_grupo = true;
            }
            if (j == gm_col.NUM_INTERNO && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                chk.checked = marcar;
                break;
            }
        }
    }

    // Hace transicion de boton marcar/desmarcar segun accion marcar elegida
    if (marcar) {
        $('#marcarGrupo').hide();
        $('#desmarcarGrupo').show();
    } else {
        $('#marcarGrupo').show();
        $('#desmarcarGrupo').hide();
        lst_movil = [];
    }
}

// Maneja listado de horas pico como cadena de caracteres formateada 
// separada por espacio ' '.
var LIM_HORA_PICO = 4;
function dph_agregarHoraPico() {
    var horaPicoInicio = document.getElementById("horaPicoInicio").value;
    var horaPicoFin    = document.getElementById("horaPicoFin").value;
    var hpico          = document.getElementById("listaHoraPico");
    var msg            = document.getElementById("msg");

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    var rs = horaPicoFin.localeCompare(horaPicoInicio);
    if (rs <= 0) {
        msg.innerHTML = "* Hora pico final debe ser superior a hora pico de inicio.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    } else {
        var s = hpico.value;
        s += (hpico.value != "") ? "  " : "";
        s += horaPicoInicio + "-" + horaPicoFin;

        // Limita cantidad horas pico a LIM_HORA_PICO + 1
        var nhoras = s.split("  ").length - 1;
        if (nhoras <= LIM_HORA_PICO)
            hpico.value = s;
    }
}

// Borra elemento de hora pico uno a uno, iniciando en parte mas a la derecha
// de la cadena formateada.
function dph_borrarHorasPico() {

    var data = document.getElementById("listaHoraPico").value;

    if (data != "" && data.indexOf("  ") >= 0) {
        var arr_data = data.split("  ");
        var new_data = "";
        for (var i = 0; i < arr_data.length - 1; i++) {
            new_data += (new_data == "")
                    ? arr_data[i]
                    : "  " + arr_data[i];
        }
        document.getElementById("listaHoraPico").value = new_data;
    } else {
        document.getElementById("listaHoraPico").value = "";
    }
}

// Comprueba y envia datos para intercambio de grupo movil entre
// dos despachos.
function dph_hacerIntercambio() {

    var dph           = document.getElementById("idDespacho").value;
    var rotar_con_dph = document.getElementById("sidDespacho").value;
    var msg           = document.getElementById("msg");

    var smsg, error = false;
    if (dph == null || dph == "") {
        smsg = "* Seleccione un despacho principal desde la tabla inferior.";
        error = true;
    } else if (rotar_con_dph == "") {
        smsg = "* Seleccione un despacho con cual rotar.";
        error = true;
    } else if (dph == rotar_con_dph) {
        smsg = "* No se puede rotar con el mismo despacho.";
        error = true;
    }

    if (error) {
        msg.innerHTML = smsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }

    $('#form-rotar-despacho').submit();
}

// Comprueba y envia datos para generar planilla despacho de una ruta
// y rango de fecha especifico.
function dph_generarPlanilla() {

    var ruta_despacho = document.getElementById("sruta_despacho").value;
    var fecha_inicial = document.getElementById("fechaInicial").value;
    var fecha_final   = document.getElementById("fechaFinal").value;
    var msg           = document.getElementById("msg");
    var smsg;

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    if (ruta_despacho == "") {
        smsg = "* Especifique una ruta de despacho.";
    } else if (fecha_inicial == "" || fecha_final == "") {
        smsg = "* Especifique fecha inicial y/o final.";
    } else {
        $('#form-generar-planilla').submit();
        return;
    }

    msg.innerHTML = smsg;
    msg.setAttribute("class", "form-msg bottom-space alert alert-info");
}

var fecha_inicial_origen = "";

// Establece en componentes graficos valores de fecha a editar.
function dph_preEditarFechas(n) {

    var id    = document.getElementById("item_id_" + n).value;
    var fi    = document.getElementById("item_fechaInicial_" + n).value;
    var ff    = document.getElementById("item_fechaFinal_" + n).value;
    var rutas = document.getElementById("item_rutas_" + n).value;
    var msg   = document.getElementById("msg");

    fecha_inicial_origen = fi;

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    var dfi = new Date(fi + " 00:00:00");
    var dff = new Date(ff + " 00:00:00");

    $("#edt_fechaInicial").datepicker('setStartDate', new Date(dfi));
    $("#edt_fechaInicial").datepicker('setEndDate', new Date(dff));
    $("#edt_fechaFinal").datepicker('setStartDate', new Date(dff));
    $("#edt_fechaFinal").datepicker('setEndDate', new Date(dff));

    $("#edt_fechaInicial").datepicker('setDate', new Date(dfi));
    $("#edt_fechaFinal").datepicker('setDate', new Date(dff));
    $("#edt_id").val(id);
    document.getElementById("edt_nombre").innerHTML = id;

    dph_iniciarEdicionFechas();
}

// Muestra bloque de componentes graficos para editar rango de fecha
// si se he especificado.
function dph_iniciarEdicionFechas() {
    var edt_id = document.getElementById("edt_id").value;

    if (edt_id != "" && $.isNumeric(edt_id)) {
        $("#form-generar-planilla").hide();
        $("#form-edita-fecha-planilla").show();
    }
}

// Comprueba y envia datos para actualizar rango de fecha.
function dph_editarFechas() {

    var fecha_inicial_mod = $("#edt_fechaInicial").val();
    if (fecha_inicial_mod.localeCompare(fecha_inicial_origen) <= 0) {
        var msg = document.getElementById("msg");
        msg.innerHTML = "* Debe dejar al menos un d&iacute;a de planilla. Elija una fecha inicial superior.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }

    $("#form-edita-fecha-planilla").submit();
}

// Oculta componentes graficos para edicion de rango de fecha.
// Restablece campos a valor por defecto.
function dph_cancelarEdicionFechas() {

    $("#form-generar-planilla").show();
    $("#form-edita-fecha-planilla").hide();
    document.getElementById("edt_id").value = "";
    document.getElementById("edt_fechaInicial").value = "";
    document.getElementById("edt_fechaFinal").value = "";
    document.getElementById("edt_nombre").innerHTML = "";
}

// Inicia proceso de creacion de configuracion despacho.
function dph_crearConfiguracion() {
    dph_procesarConfiguracion(1);
}

// Envia solicitud de datos de planilla e imprime
// resultado en ventana independiente.
function dph_verPlanillaFmt(id_intervalo, url) {

    var data = {"idIntervalo": id_intervalo};

    var html =
            '<!DOCTYPE html>\
            <html>\
            <head>\
                <title>Planilla despacho</title>\
                <link href="/RDW1/resources/css/style1.css" rel="stylesheet">\
                <link href="/RDW1/resources/bootstrap-select/css/bootstrap-select.css" rel="stylesheet">\
                <link href="/RDW1/resources/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">\
            </head>\
            <body>';
    var html_bottom =
        '<script src="/RDW1/resources/extern/bootstrap-files/js/jquery-2.2.4.js"></script>\
         <script src="/RDW1/resources/bootstrap-3.3.7/js/bootstrap.js"></script>\
         <script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap-select.js"></script>\
         <script> $(\'#fecha_veh\').selectpicker({ size: 5, liveSearch: true, width: "256px" }); </script>';
    
    var msg = document.getElementById("msg");

    $.ajax({
        url: url,
        method: "POST",
        data: data,
        action: 'planilla',
        success: function(data) {                        
            var win = window.open("", "planilla", "width=864,height=512");
            html += data + html_bottom + '</body></html>';
            if (win.document) {
                win.document.open();
                win.document.write(html);
                win.document.close();
            }
        },
        error: function () {
            msg.innerHTML = "* Planilla despacho no relacionada.";
            msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        }
    });
}

// Verifica si tipo-dia/rango-de-dias para ruta seleccionada se encuentra en uso.
function dph_verificaTipoDia() {

    var ruta        = document.getElementById("sruta").value;
    var tipodia_sel = document.getElementById("stipodia").value;
    var tipodia_alm = document.getElementById("vtipoDia");
    var msg         = document.getElementById("msg");
    var btn_guardar = document.getElementById("btn-guardar-dph");

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    if (ruta == "") {
        msg.innerHTML = "* Especifique una ruta para corroborar el tipo de d&iacute;a.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }

    tipodia_alm = (tipodia_alm == null) ? "" : tipodia_alm.value;

    var data = {"idRuta_alm": ruta,
        "tipoDia_sel": tipodia_sel,
        "tipoDia_alm": tipodia_alm};

    $.ajax({
        url: "/RDW1/verificarTipoDia",
        method: "POST",
        data: data,
        success: function (data) {

            // Obtiene descripcion de tipo dia
            var desc_tipodia = dph_descripcionTipoDia(tipodia_sel);
            var tipodia      = tipodia_sel + " " + desc_tipodia;

            if (data == 1) {
                msg.innerHTML = "* Tipo de d&iacute;a " + tipodia + " permitido.";
                msg.setAttribute("class", "form-msg bottom-space alert alert-success");
                btn_guardar.disabled = false;
            } else {
                msg.innerHTML = "* Tipo de d&iacute;a " + tipodia + " no permitido. Verifique los tipos existentes.";
                msg.setAttribute("class", "form-msg bottom-space alert alert-info");
                btn_guardar.disabled = true;
            }
        }
    });
}

// Retorna descripcion de tipodia segun valor especificado.
function dph_descripcionTipoDia(tipodia) {
    tipodia = tipodia.toUpperCase();
    var rs = "";
    if (tipodia.localeCompare("LD") == 0)
        rs = "( Lunes - Domingo )";
    if (tipodia.localeCompare("LS") == 0)
        rs = "( Lunes - S&aacute;bado )";
    if (tipodia.localeCompare("LV") == 0)
        rs = "( Lunes - Viernes )";
    if (tipodia.localeCompare("D") == 0)
        rs = "( Domingo )";
    if (tipodia.localeCompare("S") == 0)
        rs = "( S&aacute;bado )";
    if (tipodia.localeCompare("F") == 0)
        rs = "( Festivo )";
    return rs;
}

// Muestra u oculta elementos de grupo moviles segun si
// se hace uso o no de programacion ruta.
function dph_suspenderElementoMovil(restaurar) {
    var checked = document.getElementById('usarProgRuta').checked;
    if (checked) {
        document.getElementById('sgrupomovil').disabled = true;
        if (restaurar) {
            $('#dph-tabla-grupomovil').hide();
            $('#tablaGrupoMovilesDph').hide();
            $('#tablaGrupoMovilesDph_wrapper').hide();
            $('#sgrupomovil')[0].selectedIndex = 0;
        }        
        marcarElemento('sinMoviles', false);
    } else {
        document.getElementById('sgrupomovil').disabled = false;        
    }
}

// Deshabilita/habilita, restablece y oculta componentes de grupo-movil
// segun valor especificado.
function dph_suspenderElementoMovil_directo(suspender) {
    if (suspender) {
        document.getElementById('sgrupomovil').disabled = true;
        $('#dph-tabla-grupomovil').hide();
        $('#tablaGrupoMovilesDph').hide();
        $('#tablaGrupoMovilesDph_wrapper').hide();
        $('#sgrupomovil')[0].selectedIndex = 0;
    } else {
        document.getElementById('sgrupomovil').disabled = false;
    }
}

// Actualiza y ordena tabla grupo-movil tras la adicion de un movil nuevo.
function dph_restaurarFiltro(id_table, id_movil) {    
    
    id_table = '#' + id_table;
    id_movil = '#' + id_movil;
    
    // Restaura tabla (despliegue original)
    $(id_table).DataTable().search('').draw();
    
    // Apila y ordena tabla segun movil nuevo y previamente seleccionados
    var lst_movil = dph_apilarMovil(id_movil);
    dph_ordenarTabla(id_table, lst_movil);
}

var lst_movil = [];

// Adiciona/Elimina en listado lst_movil cada movil nuevo/antiguo seleccionado.
function dph_apilarMovil(id_movil) {
    
    var pos_movil = -1;   
        
    if (lst_movil.length == 0) {            
        lst_movil.push(id_movil);    
    } else {
        for (var i = 0; i < lst_movil.length; i++) {
            if (lst_movil[i] == id_movil) {
                pos_movil = i;
            }
        }
        if (pos_movil >= 0) {
            lst_movil.splice(pos_movil, 1);
        } else {
            lst_movil.push(id_movil);
        }
    }
    
    return lst_movil;
}

// lst_movil : a b c
// Insercion en tabla siempre es en parte superior,
// debido a eso se ingresan los elementos de manera inversa.
// c -> b c -> a b c
function dph_ordenarTabla(id_table, lst_movil) {
    
    var table = $(id_table);
    
    for (var i = lst_movil.length; --i >= 0; ) {
        table.prepend(table.find(lst_movil[i]));
    }
}

// Conserva orden de moviles seleccionados justo despues de hacer busqueda
// sin seleccionar ningun movil.
function dph_conservarOrdenSeleccion() {
    var div_layer    = document.getElementById("tablaGrupoMovilesDph_filter");
    var input_search = div_layer.getElementsByTagName("input")[0];
    if (input_search.value == "") {
        dph_ordenarTabla("#tablaGrupoMovilesDph", lst_movil);
    }
}

/*
 * =============================================================================
 * Edicion de configuracion despacho
 * =============================================================================
 */

// Carga datos de configuracion despacho que no pueden ser
// cargados directamente (tipo dia, hora inicio, hora fin, tipo programacion ruta).
function dph_cargarDatosEdicion() {
    
    var tipodia      = $('#vtipoDia').val();
    var horaInicio   = $('#vhoraInicio').val();
    var horaFin      = $('#vhoraFin').val();
    var tipopg       = $('#vtipopg').val();
    var puntoretorno = $('#vpuntoretorno').val();

    selectElementAUX('stipodia', tipodia);
    selectElementAUX('stipopg', tipopg);
    selectElementAUX('spuntoretorno', puntoretorno); // Valor entero
}

// Ajusta despliegue de componentes relacionados en la
// asginacion de moviles, considerando si opcion sin-movil
// ha sido o no seleccionada.
function dph_ajustarAsignacionMoviles() {
    var sin_movil = document.getElementById("sinMoviles");
    if (sin_movil.checked) {
        marcarElemento('usarProgRuta', false);        
        dph_suspenderElementoMovil_directo(true);
    } else {
        dph_suspenderElementoMovil_directo(false);        
    }
}

// Despliega componentes correspondientes a la asignacion de movil
// en base a valores almacenados.
function dph_verificarAsignacionMoviles() {
    
    var pgr = $('#pgr').val();
    var gm  = $('#gm').val();
    var gmr = $('#gmr').val();
    
    if (pgr == "0" &&
        gm  == "NODATA" &&
        gmr == "NODATA") {
        // Sin moviles(s) asignados
        marcarElemento('sinMoviles', true);
        marcarElemento('usarProgRuta', false);        
        dph_suspenderElementoMovil_directo(true);
    } else {
        marcarElemento('sinMoviles', false);
    }
}

// Carga en estructura lst_movil los moviles almacenados,
// con el fin de conservar el orden en una nueva seleccion.
function dph_cargarListaMovil() {
    var tbl  = document.getElementById("tablaGrupoMovilesDph");
    var rows = tbl.rows;
        
    for (var i = 0; i < rows.length; i++) {
        var id_movil = "#" + rows[i].getAttribute("id");
        var cols     = rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.NUM_INTERNO) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (chk.checked) {
                    lst_movil.push(id_movil);
                }
                break;
            }
        }
    }
}

// Inicia proceso de edicion de configuracion despacho.
function dph_editarConfiguracion() {
    dph_procesarConfiguracion(2);
}

/*
 * =============================================================================
 * Programacion rutas en semana
 * =============================================================================
 */

// Columnas de tabla programacion ruta
var pgr_col = {
    ID_RUTA: 0,
    NOMBRE_RUTA: 1,
    GRUPO_LUN: 2,
    GRUPO_MAR: 3,
    GRUPO_MIE: 4,
    GRUPO_JUE: 5,
    GRUPO_VIE: 6,
    GRUPO_SAB: 7,
    GRUPO_DOM: 8
};

// Inicia rotacion lineal de grupos-moviles en tabla programacion ruta 
// con base a definicion de la primera columna.
// Si algun grupo-movil no se define para una ruta en la primera columna
// la rotacion se cancela.
function dph_rotarGruposEnSemana() {

    var tpr = document.getElementById("tablaProgramacionRuta");
    var arr_grupo = dph_verificarGruposEnSemana();
    if (arr_grupo == -1) {
        return;
    }

    var rows = tpr.rows.length;
    var ini_col = pgr_col.GRUPO_MAR;
    var fin_col = pgr_col.GRUPO_DOM;
    var idx = 0;

    arr_grupo = swap(arr_grupo);

    for (var c = ini_col; c <= fin_col; c++) {

        for (var r = 0; r < rows; r++) {
            var cols = tpr.rows[r].getElementsByTagName("td");

            for (var j = 0; j < cols.length; j++) {
                if (j == c) {
                    //cols[j].innerHTML = arr_grupo[idx++];                        
                    var select = cols[j].getElementsByTagName("select")[0];
                    dph_seleccionarGrupo(select, arr_grupo[idx++]);
                }
            }
            if (idx >= arr_grupo.length)
                idx = 0;
        }
        arr_grupo = swap(arr_grupo);
    }
}

// Inicia asignacion lineal de grupo-movil en tabla programacion ruta
// con base en definicion de la primera columna.
// Si algun grupo-movil no se define para una ruta en la primera columna
// la rotacion se cancela.
function dph_conservarGruposEnSemana() {

    var tpr = document.getElementById("tablaProgramacionRuta");
    var arr_grupo = dph_verificarGruposEnSemana();
    if (arr_grupo == -1) {
        return;
    }

    var rows = tpr.rows.length;
    for (var i = 0; i < rows; i++) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = pgr_col.GRUPO_MAR; j < cols.length; j++) {
            if (j >= pgr_col.GRUPO_MAR) {
                var select = cols[j].getElementsByTagName("select")[0];
                dph_seleccionarGrupo(select, arr_grupo[i - 1]);
            }
        }
    }
}

// Comprueba que todas las rutas tengan un grupo-movil asignado en
// la primera columna (Lunes).
function dph_verificarGruposEnSemana() {

    var tpr = document.getElementById("tablaProgramacionRuta");
    var msg = document.getElementById("msg");

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    var rows = tpr.rows.length;
    var arr_grupo = new Array();

    for (var i = 0; i < rows; i++) {
        var cols = tpr.rows[i].getElementsByTagName("td");

        for (var j = 0; j < cols.length; j++) {
            if (j == pgr_col.GRUPO_LUN) {
                var sgrupo = cols[j].getElementsByTagName("select")[0];

                if (sgrupo.value == "") {
                    msg.innerHTML = "* Debe especificar un grupo por cada ruta.";
                    msg.setAttribute("class", "form-msg bottom-space alert alert-info");
                    return -1;
                } else {
                    arr_grupo.push(sgrupo.value);
                }
            }
        }
    }
    return arr_grupo;
}

// Comprueba y envia datos para almacenamiento de nueva programacion de ruta.
// Se crea listado con asignacion de grupo-movil por ruta en cadena formateada.
function dph_enviaProgramacionRuta(type) {

    var nombre = document.getElementById("nombre");
    var tpr = document.getElementById("tablaProgramacionRuta");
    var msg = document.getElementById("msg");
    var rows = tpr.rows.length;

    msg.innerHTML = "";
    var s = "", data = "";
    msg.setAttribute("class", "form-msg bottom-space");

    if (!exp_alfanumerico_esp.test(nombre.value)) {
        var vmsg = "* Especifique el nombre de la programaci&oacute;n correctamente.";
        vmsg += " &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos -_."
        msg.innerHTML = vmsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    var n = nombre.value;

    for (var i = 0; i < rows; i++) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {
            if (j == pgr_col.ID_RUTA) {
                s += cols[j].innerHTML;
            }
            if (j >= pgr_col.GRUPO_LUN) {
                var select = cols[j].getElementsByTagName("select")[0];
                if (select.value == "") {
                    msg.innerHTML = "* Debe especificar un grupo por cada ruta.";
                    msg.setAttribute("class", "form-msg bottom-space alert alert-info");
                    return;
                } else {
                    s += "," + select.value;
                }
            }
        }
        if (i > 0 && i < rows - 1)
            s = s + "&";
    }

    if (type == 1) {
        $('#nombreProgramacion').val(n);
        $('#programacionRuta').val(s);
        $('#form-nueva-programacion-ruta').submit();
    } else {
        var id = document.getElementById("pgruta_activa_id").value;
        $('#idProgramacionRuta_upd').val(id);
        $('#nombreProgramacion_upd').val(n);
        $('#programacionRuta_upd').val(s);
        $('#form-actualiza-programacion-ruta').submit();
    }
}

function dph_programarRuta() {
    dph_enviaProgramacionRuta(1);
}

function dph_actualizarProgramacionRuta() {
    dph_enviaProgramacionRuta(2);
}

// Selecciona grupo-movil de cada ruta de cada dia 
// segun valor especificado en componente input adyacente.
function dph_cargarProgramacion(loadData) {

    var tpr = document.getElementById("tablaProgramacionRuta");

    var rows = tpr.rows.length;
    for (var i = 0; i < rows; i++) {
        var cols = tpr.rows[i].getElementsByTagName("td");
        for (var j = 0; j < cols.length; j++) {
            if (j >= pgr_col.GRUPO_LUN) {
                var input = cols[j].getElementsByTagName("input")[0];
                var select = cols[j].getElementsByTagName("select")[0];
                if (input != null) {
                    if (loadData) {
                        dph_seleccionarGrupo(select, input.value);
                    } else {
                        dph_seleccionarGrupo(select, "");
                    }
                }
            }
        }
    }
}

// Selecciona opcion de lista select correspondiente a valor val.
function dph_seleccionarGrupo(select, val) {
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].value == val) {
            select.options[i].selected = true;
            break;
        }
    }
}

function dph_cargarProgramacionRuta() {
    dph_cargarProgramacion(true);
}

function dph_resetProgramacionRuta() {
    dph_cargarProgramacion(false);
}

function dph_existeProgramacionActiva() {
    var pgr_activa    = document.getElementById("pgruta_activa");
    var pgr_activa_id = document.getElementById("pgruta_activa_id");
    if (pgr_activa != null && pgr_activa.value != "" &&
        pgr_activa_id != null && parseInt(pgr_activa_id.value) > 0) {    
        return true;
    } else {
        return false;
    }
}

function dph_guiNuevaProgramacion() {
    var btn_guarda = document.getElementById("btn_guarda_pgr");
    var btn_actualiza = document.getElementById("btn_actualiza_pgr");
    if (btn_guarda != null && btn_actualiza != null) {        
        btn_guarda.style.display = "inline";
        btn_actualiza.style.display = "none";        
    }
    document.getElementById("nombre").value = "";
}

function dph_guiEditaProgramacion() {
    var btn_guarda = document.getElementById("btn_guarda_pgr");
    var btn_actualiza = document.getElementById("btn_actualiza_pgr");
    if (btn_guarda != null && btn_actualiza != null) {        
        btn_guarda.style.display = "none";
        btn_actualiza.style.display = "inline";
    }    
    if (dph_existeProgramacionActiva()) {        
        var pgr_activa = document.getElementById("pgruta_activa");
        document.getElementById("nombre").value = pgr_activa.value;
    }
}

function dph_verProgramacionActiva(show) {
    var div_pgr = document.getElementById("div_pgr_activa");
    if (div_pgr != null) {
        if (show && dph_existeProgramacionActiva()) {
            dph_cargarProgramacionRuta();
            dph_guiEditaProgramacion();
            div_pgr.style.display = "block";
        } else {
            dph_resetProgramacionRuta();
            dph_guiNuevaProgramacion();
            div_pgr.style.display = "none";
        }
    }
}

function dph_nuevaProgramacionRuta() {
    dph_verProgramacionActiva(false);
}

function dph_cancelaProgramacionRuta() {
    dph_verProgramacionActiva(true);
}

// Envia solicitud de activacion de programacion ruta seleccionada.
function dph_activarProgramacionRuta() {
    var id_pgruta = document.getElementById("spgruta").value;
    var msg = document.getElementById("msg");

    if (id_pgruta == "") {
        msg.innerHTML = "* Seleccione una programaci&oacute;n de ruta."
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    $('#idProgramacionRuta_act').val(id_pgruta);
    $('#form-activa-programacion-ruta').submit();
}

// Envia solicitud de desactivacion de programacion ruta.
function dph_inactivarProgramacionRuta() {
    $('#form-desactiva-programacion-ruta').submit();
}

// Envia solicitud de eliminacion de programacion ruta seleccionada.
function dph_eliminarProgramacionRuta() {
    var id_pgruta = document.getElementById("spgruta").value;
    var msg = document.getElementById("msg");

    if (id_pgruta == "") {
        msg.innerHTML = "* Seleccione una programaci&oacute;n de ruta."
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    $('#idProgramacionRuta_des').val(id_pgruta);
    $('#form-elimina-programacion-ruta').submit();
}

// Pasa ultimo elemento a ser el primero.
function swap(arr_grupo) {
    var grupo = arr_grupo.pop();
    arr_grupo.unshift(grupo);
    return arr_grupo;
}

// Envia solicitud de vista de rodamiento para un mes y tipo especifico.
// Se imprime resultado en ventana independiente.
function dph_verRodamiento() {

    var smes    = $('#smes').val();
    var stipo   = $('#stipo').val();
    var msg_rod = $('#msg-rodamiento')[0];
    var msg     = $('#msg')[0];

    msg_rod.innerHTML = "";

    var html =
            '<!DOCTYPE html>\
            <html>\
            <head>\
                <title>Planilla rodamiento</title>\
                <link href="/RDW1/resources/css/style1.css" rel="stylesheet"/>\
            </head>\
            <body>';

    if (smes == "") {
        msg_rod.innerHTML = "* Seleccione un mes.";
        return;

    } else {

        var data = {"mesRodamiento": smes, "tipoRodamiento": stipo};

        $.ajax({
            url: "/RDW1/verRodamiento",
            method: "POST",
            data: data,
            success: function (data) {
                var win = window.open();
                html += data + '</body></html>';
                if (win.document) {
                    win.document.open();
                    win.document.write(html);
                    win.document.close();
                }
            },
            error: function () {
                msg.innerHTML = "* Planilla rodamiento no relacionada.";
                msg.setAttribute("class", "form-msg bottom-space alert alert-info");
            }
        });
    }
}

// Hace transicion entre placa/numero interno que es mostrado
// en el detalle de los grupos moviles asignados en programacion ruta.
function dph_mostrarPlaca() {

    var mostrar_placa = document.getElementById("chk_mostrarplaca");
    var divs_numint   = document.getElementsByName("lst_numinterno");
    var divs_placa    = document.getElementsByName("lst_placa");
    var size          = divs_numint.length;

    var placa, num_int;
    if (mostrar_placa.checked) {
        placa   = true;
        num_int = false;
    } else {
        placa   = false;
        num_int = true;
    }

    for (var i = 0; i < size; i++) {
        divs_numint[i].style.display = (num_int) ? "block" : "none";
        divs_placa[i].style.display = (placa) ? "block" : "none";
    }
}

// Envia solicitud de consulta tipo dia asignado a ruta.
function dph_consultaTipoDia() {
    var ruta_dph = $('#sruta_despacho').val();
    
    $('#infoTipoDia table').empty();
    if (ruta_dph == "" || ruta_dph == "all") {
        return;
    }
    
    $.ajax({
        url: "/RDW1/consultaTipoDia",
        method: "POST",
        data: { "sruta_dph": ruta_dph },
        
        success: function(data) {
            $('#infoTipoDia').append(data);
        }
    });
}

/*
 * =============================================================================
 * Control despacho
 * =============================================================================
 */

// Inicia/pausa control de despacho.
function dph_iniciaControl() {
    $('#form-inicia-control-despacho').submit();
}

/*
 * =============================================================================
 * Vista control despacho
 * =============================================================================
 */

// Comprueba valores y envia solicitud de almacenamiento
// para tiempo de comprobacion y tipo de control de despacho.
function dph_guardarTiempoControl() {

    var tiempo_control = $('#tiempoControl').val();
    var tipo_control   = $('#stipoControl').val();
    
    var msg = $('#msg')[0];
    var exp_tiempo = /^[0-9]+$/;

    if (!exp_tiempo.test(tiempo_control) ||
            parseInt(tiempo_control) < 0 ||
            parseInt(tiempo_control) > 120) {
        msg.innerHTML = "* Rango de tiempo control despacho mal especificado.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-danger");
        return;
    }

    $.ajax({
        url: "/RDW1/tiempoControlDespacho",
        method: "POST",
        data: {"tiempoControl": tiempo_control, 
               "tipoControl": tipo_control},

        success: function (data) {
            var vmsg, msgtype;

            if (data == 1) {
                vmsg = "* Rango de tiempo control despacho almacenado correctamente.";
                msgtype = "form-msg bottom-space alert alert-success";
            } else {
                vmsg = "* Rango de tiempo control despacho no almacenado.";
                msgtype = "form-msg bottom-space alert alert-info";
            }
            msg.innerHTML = vmsg;
            msg.setAttribute("class", msgtype);
        }
    });
}

// Consulta registros de despacho controlado
// para su visualizacion.
function dph_iniciaConsultaControl() {
    dph_verificaParametrosControl();
    dph_consultaControl();
    setInterval("dph_consultaControl()", 6000);    
}

// Comprueba parametros de consulta para la obtencion
// especifica de registros de despacho controlado.
function dph_verificaParametrosControl() {

    var ruta             = $('#p_ruta').val();
    var fecha            = $('#p_fecha').val();    
    var mostrar_historia = $('#p_mostrarHistoria').val();
    var movil            = $('#p_movil').val();

    $('#fecha-ctr').val(fecha);
    var comp_mostrar_historia = $('#mostrarHistoria')[0];
    comp_mostrar_historia.checked = (mostrar_historia == "1") ? true : false;
    
    // Parametros ruta y movil pueden ser tanto un elemento o una lista
    // separados por (,).
    
    if (ruta != "") {
        var array_ruta = ruta.split(",");
        $('#sruta').selectpicker('val', array_ruta);
        $('#sruta').selectpicker('refresh');
    }

    if (movil != "") {
        var array_movil = movil.split(",");
        $('#smovil').selectpicker('val', array_movil);
        $('#smovil').selectpicker('refresh');
    }
}

// Extrae valores de opciones para elemento select identificado
// por id_elemt y crea cadena de texto formateada.
function localSelectedItems (id_elemt) {
    var select = document.getElementById(id_elemt);
    var size   = select.options.length;
    
    if (select != null) {
        var s = "";
        for (var i = 0; i < size; i++) {
            if (select.options[i].selected) {
                var v = select.options[i].value;
                s += (s == "") ? v : "," + v;
            }
        }
    }
    return s;
}

// Comprueba parametros de consulta y envia solicitud para obtener
// registros de despacho controlado.
function dph_consultaControl() {
    
    var fecha            = $('#fecha-ctr').val();    
    var mostrar_historia = $('#mostrarHistoria')[0];
    var msg              = $('#msg')[0];

    var vmostrar_historia = (mostrar_historia.checked) ? "1" : "0";

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg div-style-cpd");
    msg.style.display = "none";
    
    var lista_ruta  = localSelectedItems('sruta');
    var lista_movil = localSelectedItems('smovil');

    if (lista_ruta == "" ||
        fecha      == "") {
        msg.innerHTML = "* Especifique una ruta y fecha correctamente.";
        msg.style.display = "block";
        $('#controlDespacho').empty();
        return;
    }
    
    var data = {
        "sruta": lista_ruta,
        "fecha": fecha,
        "mostrarHistoria": vmostrar_historia,
        "smovil": lista_movil
    };

    $.ajax({
        url: "/RDW1/verControlDespacho",
        method: "POST",
        data: data,
        success: function (data) {
            $("#controlDespacho").empty();
            $("#controlDespacho").append(data);
            dph_filtrarPorMovil();
            dph_agregarFunciones();
        }
    });
}

function dph_agregarFunciones() {

    $("table td.to_customize").click(function () {
        var id = $(this)[0].id;
        var idList = $("#" + id + "_list");
        if (idList.length > 0) {
            $(idList).click();
        }
    });

}

// Aplica filtro para visualizar solo aquellos registros de moviles
// seleccionados, para ambas modalidades de presentacion 'mostrar-historia' o no.
function dph_filtrarPorMovil() {

    var lst_tbl = $('#controlDespacho table');

    // Se ocultan todos los moviles
    for (var i = 0; i < lst_tbl.length; i++) {
        lst_tbl[i].style.display = "none";
    }

    var movil_select = $('#smovil')[0];
    var historia = $('#mostrarHistoria')[0];
    var moviles = new Array();

    // Se listan aquellos moviles seleccionados para visualizar
    for (var i = 0; i < movil_select.options.length; i++) {
        var movil = movil_select.options[i];
        if (movil.selected) {
            moviles.push(movil);
        }
    }

    var mostrar_todos = (moviles.length > 0) ? false : true;

    if (moviles.length >= 0) {

        // Si es tabla resumen (se deselecciona 'mostrar historia' - registros de hora actual)
        if (!historia.checked) {
            
            var ntbl = lst_tbl.length;
            
            for (var i = 0; i < ntbl; i++) {
                lst_tbl[i].style.display = "block";
            }
            
            for (var i = 1; i < ntbl; i += 2) {

                var tbl_detalle = lst_tbl[i];
                if (typeof tbl_detalle == 'undefined') continue;

                var lst_rows = tbl_detalle.getElementsByTagName("tr");
                var unMovil  = (mostrar_todos) ? true : false;

                for (var j = 1; j < lst_rows.length; j++) {

                    var row = lst_rows[j];
                    row.style.display = (mostrar_todos)
                            ? "table-row" : "none";

                    for (var k = 0; k < moviles.length; k++) {
                        var movil = moviles[k].value;
                        if (row.id == movil) {
                            row.style.display = "table-row";
                            unMovil = true;
                            break;
                        }
                    }
                }
                
                if (!unMovil) {                    
                    lst_tbl[i-1].style.display = "none";
                    lst_tbl[i].style.display = "none";
                }
            }

        } else {
            
            // Se selecciona 'mostrar historia'
            for (var i = 0; i < lst_tbl.length; i++) {
                
                if (mostrar_todos) {
                    lst_tbl[i].style.display = "block";                    
                }                

                for (var j = 0; j < moviles.length; j++) {
                    var movil = moviles[j].value;
                    var tbl = lst_tbl[i];

                    // Se muentran moviles seleccionados
                    if (tbl.id == movil) {
                        tbl.style.display = "block";
                        break;
                    }
                }
            }
        }
    }
}

// Establece fecha-actual en componente grafico.
function dph_usarFechaHoy() {
    $('#fecha-ctr').datepicker('setDate', new Date());
    //$('#fechaFinal').datepicker('setDate', new Date());
}

/*
 * =============================================================================
 * Sustituir movil
 * =============================================================================
 */

// Comprueba pamametros de consulta y envia solicitud para obtener
// registros de planilla despacho de un movil a sustituir.
function dph_consultarPlanillaMovil() {

    var ruta  = $('#sruta').val();
    var fecha = $('#fecha-stc').val();
    var movil = $('#smovil').val();        
    var msg   = $('#msg')[0];
    var vmsg  = "";
    
    var totalidad_vueltas = document.getElementById("totalidad_vueltas");
    totalidad_vueltas = (totalidad_vueltas.checked) ? "1" : "0";

    if (ruta == "") {
        vmsg = "* Especifique una ruta.";
    } else if (fecha == "") {
        vmsg = "* Especifique una fecha.";
    } else if (movil == "") {
        vmsg = "* Especifique un veh&iacute;culo.";
    }

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    if (vmsg != "") {
        msg.innerHTML = vmsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }        

    var data = {
        "sruta": ruta,
        "fecha": fecha,
        "smovil": movil,
        "totalidad_vueltas": totalidad_vueltas
    };

    $.ajax({
        url: "/RDW1/consultarPlanillaMovil",
        data: data,
        method: "POST",
        success: function (data) {
            $('#tbodyid tr').empty();
            $('#tbodyid').append(data);
        }
    });
}

// Comprueba valores de parametros y envia solicitud de sustitucion de
// movil para una planilla despacho especifica.
function dph_sustituirMovil() {

    var ruta   = $('#sruta').val();
    var fecha  = $('#fecha-stc').val();
    var movil  = $('#smovil').val();
    var nmovil = $('#nsmovil').val();
    var msg    = $('#msg')[0];
    var vmsg   = "";

    if (ruta == "") {
        vmsg = "* Especifique una ruta.";
    } else if (fecha == "") {
        vmsg = "* Especifique una fecha.";
    } else if (movil == "") {
        vmsg = "* Especifique un veh&iacute;culo.";
    } else if (nmovil == "") {
        vmsg = "* Especifique un veh&iacute;culo de reemplazo.";
    }

    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");

    if (vmsg != "") {
        msg.innerHTML = vmsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }

    var lst_vueltas = dph_vueltasASustituir();
    if (lst_vueltas == "") {
        msg.innerHTML = "* Seleccione al menos una vuelta para ser reemplazada.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }

    $('#ruta_s').val(ruta);
    $('#fecha_s').val(fecha);
    $('#movil_s').val(movil);
    $('#lst_vueltas').val(lst_vueltas);
    $('#form-sustituye-movil').submit();
}

var col_pd = {
    NUMERO_VUELTA: 0,
    CANTIDAD_REGISTROS: 1,
    NUMERO_VUELTA_CHK: 2
};

// Extrae y formatea en texto, registros de vueltas de planilla despacho,
// de un movil a sustituir.
function dph_vueltasASustituir() {

    var tbl_pd = document.getElementById("tablaPlanillaMovil");
    var tr = tbl_pd.getElementsByTagName("tr");
    var lst_vueltas = "";
    var nregistros = 0;

    for (var i = 1; i < tr.length; i++) {
        var td     = tr[i].getElementsByTagName("td");
        var td_id  = td[col_pd.NUMERO_VUELTA];
        var td_cr  = td[col_pd.CANTIDAD_REGISTROS];
        var td_chk = td[col_pd.NUMERO_VUELTA_CHK];
        var id     = "";
        var n = 0;

        if (td_id) {
            id = td_id.innerHTML;
        }
        if (td_cr) {
            n = parseInt(td_cr.innerHTML);
        }
        if (td_chk) {
            var chk = td_chk.getElementsByTagName("input")[0];
            if (chk.checked) {
                lst_vueltas += (lst_vueltas == "") ? id : "," + id;
                nregistros += n;
            }
        }
    }

    $('#nregistros_s').val(nregistros);
    return lst_vueltas;
}

// Selecciona/deselecciona todos los registros de vueltas de un movil.
function dph_checkVueltas(v) {

    var tbl_pd = document.getElementById("tablaPlanillaMovil");
    var tr     = tbl_pd.getElementsByTagName("tr");
    var nchk   = 0;

    for (var i = 1; i < tr.length; i++) {
        var td = tr[i].getElementsByTagName("td");
        var td_chk = td[col_pd.NUMERO_VUELTA_CHK];
        if (td_chk) {
            var chk = td_chk.getElementsByTagName("input")[0];
            chk.checked = v;
            nchk += 1;
        }
    }

    var btn_chk = document.getElementById("btn_check");
    var btn_nchk = document.getElementById("btn_nocheck");

    if (nchk > 0) {
        if (v) {
            btn_chk.setAttribute("class", "no-display");
            btn_nchk.setAttribute("class", "");
        } else {
            btn_chk.setAttribute("class", "");
            btn_nchk.setAttribute("class", "no-display");
        }
    }
}

/*
 * =============================================================================
 * Ajuste modulo despacho (Punto retorno)
 * =============================================================================
 */

// Comprueba si se ha seleccionado un punto-retorno.
function dph_hayPuntoRetorno() {
    var puntoretorno = document.getElementById("spuntoretorno");
    if (puntoretorno.value == "")
        return false;
    return true;
}

// Establece en componente grafico un punto-retorno de codigo codpunto.
function dph_establecerPuntoRetorno(codpunto) {
    var pcodpunto = "p" + codpunto;
    selectElementAUX_Fmt('spuntoretorno', pcodpunto, ',', 1);
    dph_mostrarPuntoRetorno();
}

// Comprueba valores de parametros relacionados en la asignacion de un punto-retorno,
// de ser alguno especificado incorrectamente se notifica y retorna un valor falso.
// Se retorna verdadero en caso contrario.
function dph_validaParametrosPuntoRetorno() {
        
    var hora_inicio        = document.getElementById("horaInicio_rt");
    var intervalo_despacho = document.getElementById("intervaloDespacho_rt");
    var tiempo_salida      = document.getElementById("tiempoSalida_rt");    
    var tiempo_ajuste      = document.getElementById("tiempoAjuste_rt");    
    
    var hora_fin    = document.getElementById("horaFin");
    var prog_ruta   = document.getElementById("usarProgRuta");    
    var grupo_movil = document.getElementById("sgrupomovil");    
    
    var msg = document.getElementById("msg"); var smsg = "";
    msg.innerHTML = smsg;
    msg.setAttribute("class", "form-msg bottom-space");
    
    var vehiculos_en_pr = dph_vehiculosEnPuntoRetorno();
    
    if (dph_hayPuntoRetorno()) {
        
        if (!dph_horaValida(hora_inicio.value)) {
            smsg = "* Especifique una hora de inicio para punto retorno correctamente.";
        } else if (hora_inicio.value.localeCompare(hora_fin.value) >= 0) {
            smsg = "* Hora de inicio para punto retorno debe ser inferior a hora fin del despacho.";
        } else if (!dph_valorNumerico(intervalo_despacho.value, 0, 480)) {
            smsg = "* Especifique un intervalo despacho para punto retorno correctamente. Valor permitido entre 0 y 480.";
        } else if (!dph_valorNumerico(tiempo_salida.value, 0, 180)) { 
            smsg = "* Especifique un tiempo de salida para punto retorno correctamente. Valor permitido entre 0 y 180.";
        } else if (!dph_valorNumerico(tiempo_ajuste.value, 0, 180)) {
            smsg = "* Especifique un tiempo de ajuste para punto retorno correctamente. Valor permitido entre 0 y 180.";
        } else {
            if (!prog_ruta.checked && grupo_movil != "") {                
                if (vehiculos_en_pr <= 0) {
                    smsg = "* Especifique al menos un veh&iacute;culo en punto retorno.";
                }
            }
        }                   
    } 
    
    if (smsg != "") {
        msg.innerHTML = smsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return false;
    }
    
    return true;
}

// Retorna cantidad de moviles seleccionados
// que inician en punto-retorno.
function dph_vehiculosEnPuntoRetorno() {
    
    var tgm = document.getElementById("tablaGrupoMovilesDph");
    var grupo_movil = document.getElementById("sgrupomovil").value;
    var n = 0;
    
    for (var i = tgm.rows.length-1; i >= 0; i--) {  
        var cols     = tgm.rows[i].getElementsByTagName("td");
        var es_grupo = false;
        var veh_seleccionado = false;
        
        for (var j = 0; j < cols.length; j++) {
            if (j == gm_col.ID_GRUPO &&
                cols[j].innerHTML == grupo_movil) {
                es_grupo = true;
            }
            if (j == gm_col.NUM_INTERNO && es_grupo) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (chk.checked) { veh_seleccionado = true; }
            }
            if (j == gm_col.INICIA_PUNTO_RETORNO && veh_seleccionado) {
                var chk = cols[j].getElementsByTagName("input")[0];
                if (chk.checked) { n++; }
            }
        }
    }
    return n;
}

/*
 * =============================================================================
 * Generacion planilla a pedido
 * =============================================================================
 */

// Permite o no mostrar unicamente vueltas generadas del dia actual.
function dph_tabularVueltaDelDia() {
    
    var chk_dia_actual = $('#chk_dia_actual')[0];
    
    if (chk_dia_actual.checked) {
        var fecha_actual = getDate();
        $('#tablaDespachoManual').DataTable().columns(5).search(fecha_actual).draw();
    } else {
        $('#tablaDespachoManual').DataTable().columns(5).search('').draw();
    }
}

// Comprueba valores de parametros y envia solicitud de creacion
// de planilla despacho a pedido (Una vuelta) para un movil especifico.
function dph_generarPlanillaAPedido() {
    var ruta_dph    = $('#sruta_despacho').val();
    var movil_dph   = $('#smovil').val();
    var hora_salida = $('#hora_salida').val();
    var ipr         = $('#inicia_punto_retorno')[0];
    var vipr        = (ipr.checked) ? "1" : "0";
    $('#vinicia_punto_retorno').val(vipr);
    
    var msg = document.getElementById("msg"); var smsg = "";
    msg.innerHTML = smsg;
    msg.setAttribute("class", "form-msg bottom-space");    
    
    if (ruta_dph == "") {
        smsg = "* Especifique una ruta de despacho.";
    } else if (movil_dph == "") {
        smsg = "* Especifique un veh&iacute;culo para el despacho.";
    } else if (hora_salida == "") {
        smsg = "* Especifique una hora de salida.";
    }
    
    if (smsg != "") {
        msg.innerHTML = smsg;
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
    } else {
        $('#form-generar-planilla-pedido').submit();
    }
}

// Comprueba valor de parametro y envia solicitud de edicion
// de planilla despacho a pedido para un movil especifico.
function dph_editarPlanillaAPedido() {
    var ssmovil = $('#ssmovil').val();
    
    var msg = document.getElementById("msg");
    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");
    
    if (ssmovil == "") {        
        msg.innerHTML = "* Especifique un veh&iacute;culo para reemplazo.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
    } else {
        $('#form-edita-planilla-pedido').submit();
    }
}