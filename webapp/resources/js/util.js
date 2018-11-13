
// Genera fecha actual en formato yyyy-mm-dd.
function getDate() {    
    var date  = new Date();
    var month = date.getMonth() + 1;
    var day   = date.getDate();    
    var year  = date.getFullYear();
    
    if (month < 10) month = "0" + month;
    if (day   < 10) day   = "0" + day;
    var yyyymmdd = year + "-" + month + "-" + day;

    return yyyymmdd;
}

// Genera fecha en formato yyyy-mm-dd.
function getDateFrom(date) {
    var month = date.getMonth() + 1;
    var day   = date.getDate();    
    var year  = date.getFullYear();
    
    if (month < 10) month = "0" + month;
    if (day   < 10) day   = "0" + day;
    var yyyymmdd = year + "-" + month + "-" + day;

    return yyyymmdd;
}

// Valor de componente id_elemt es fecha actual.
function fechaActual(id_elemt) {    
    var fecha_actual = getDate();
    var fecha_sel    = document.getElementById(id_elemt);        
    
    if (fecha_sel != null && fecha_actual.localeCompare(fecha_sel.value) == 0)
        return true;
    return false;      
}

// Ubica y selecciona opcion con valor value
// en componente select identificado por id_elemt.
function selectElementAUX (id_elemt, value) {
    var s = document.getElementById(id_elemt);    
    
    if (s != null) {
        for (var i = 0; i < s.length; i++) {
            if (s.options[i].value == value) {
                s.options[i].selected = true;
                break;
            }            
        }
    }
}

// Ubica y selecciona opcion cuyo valor (formateado con separador sep)
// ubicado en posicion index es igual a value
// en componente select identificado por id_elemt.
function selectElementAUX_Fmt (id_elemt, value, sep, index) {
    var s = document.getElementById(id_elemt);
        
    if (s != null) {
        var options = s.options;
        for (var i = 0; i < options.length; i++) {
            var val = options[i].value;
            var arr = val.split(sep);
            if (arr != null && arr.length > index && index >= 0) {                
                if (arr[index] == value) {
                    s.options[i].selected = true;
                    break;
                }
            }
        }
    }
}

// Inicia seleccion de opciones para componentes select
// segun valores especificados.
function selectElement () {  
    
    var empresa     = document.getElementById("iempresa");
    var perfil      = document.getElementById("iperfil");
    var pais        = document.getElementById("ipais");
    var tipodoc     = document.getElementById("itipodoc");
    var prevemp     = document.getElementById("prevempresa");
    var moneda      = document.getElementById("imoneda");
    var zonahoraria = document.getElementById("izonahoraria");
    
    if (empresa != null)   { selectElementAUX ("sempresa", empresa.value); }
    if (perfil != null)    { selectElementAUX ("sperfil", perfil.value); } 
    if (pais != null)      { selectElementAUX ("spais", pais.value); }
    if (tipodoc != null)   { selectElementAUX ("stipodoc", tipodoc.value); }
    if (prevemp != null)   { selectElementAUX ("sempresa", prevemp.value); }
    if (moneda != null)    { selectElementAUX ("smoneda", moneda.value); }
    if (zonahoraria != null) { selectElementAUX ("szonahoraria", zonahoraria.value); }
}

/*
 * Crea lista separada por coma (,) de opciones seleccionadas en componente select.
 */
function listSelectedItems (id_elemt) {
    var select = document.getElementById(id_elemt);
    var s = "";
    
    if (select != null) {
        for (var i = 0; i < select.options.length; i++) {
            if (select.options[i].selected) {
                var v = select.options[i].value;
                s += (s == "") ? v : "," + v;                    
            }
        }
    }
    return s;
}

/*
 * Crea lista separada por coma (,) de opciones seleccionadas en componente
 * select, cuyo valor de opcion es extraido segun un separador e indice.
 */
function listSelectedItems (id_elemt, sep, index) {
    var select = document.getElementById(id_elemt);
    var s = "";
    
    if (select != null) {
        for (var i = 0; i < select.options.length; i++) {
            if (select.options[i].selected) {
                var v  = select.options[i].value;
                var vs = v.split(sep)[index];
                s += (s == "") ? vs : "," + vs;                    
            }
        }
    }
    return s;
}

// Marca/Desmarca elemento checkbox id_elemt segun valor especificado.
function marcarElemento(id_elemt, value) {
    document.getElementById(id_elemt).checked = value;
}

// Muestra/Oculta elemento id_elemt segun valor especificado.
function mostrarElemento(id_elemt, value) {
    if (value) {
        document.getElementById(id_elemt).style.display = "block";
    } else {
        document.getElementById(id_elemt).style.display = "none";
    }
}

// Cierra aplicacion.
function closeApp () {
    document.getElementById("form-close").submit();
}

// Establece codigo para el registro de un nuevo punto/base.
function setCodigoPunto (nombre_componente) {
    
    var max_cod  = document.getElementById(nombre_componente);
    var text_cod = document.getElementById("codPtoBase");
    
    if (max_cod != null) {
        var v = max_cod.value;
        var esPunto = (nombre_componente.indexOf("maxcodPto") >= 0) ? true : false;
    
        if (v == null || v == "") {
            v = (esPunto) ? 1 : 900;
        } else {            
            v = parseInt(v);
            if (esPunto) {
                v += 1;
                v = (v >= 900 && v <= 999) ? 1000 : v;                
                v = (v == 100 || v == 101) ? 102 : v;
            } else {        
                v += 2;
                v = (v < 900 || v > 999) ? 900 : v;                
            }
        }
    
        if (text_cod != null)
            text_cod.value = v;
    }
}

/*
 * Envia formulario de punto segun sea orden de insercion o actualizacion.
 */
function sendPunto (orden) {
    //document.getElementById("form-punto").submit();
    
    var msg = document.getElementById("msg");
    
    // Se verifica existencia de punto en el mapa
    if (!existPoint()) {
        msg.innerHTML = "* Elija una posici&oacute;n en el mapa.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    
    // Se verifica seleccion de tipo de punto
    var tipoPunto  = document.getElementById("tipoPunto");
    var rtipoPunto = tipoPunto.getElementsByTagName("input");
    var select = false;
    for (var i = 0; i < rtipoPunto.length; i++) {
        if (rtipoPunto[i].checked) {
            select = true; break;
        }
    }
    
    if (!select) {
        msg.innerHTML = "* Elija un tipo de punto.";
        msg.setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    
    var form = "#form-punto";
    if (orden == "ins") {
        document.getElementById("orden").value = "ins";
        $(form).find('[type="submit"]').trigger('click');
    } else if (orden == "act") {
        document.getElementById("orden").value = "act";        
        $(form).find('[type="submit"]').trigger('click');
    }
}

/*
 * Ejecuta acciones de restablecimiento tras cancelar
 * el registro de un punto/base.
 */
function cancelPunto () {
           
    EN_MODO_EDICION = false;
    removePath();    
    
    /* restaura elementos */
    /* 
    document.getElementById("nombre").value = "";    
    document.getElementById("codPtoBase").value = "";    
    document.getElementById("latitud").value = "";
    document.getElementById("longitud").value = "";    
    document.getElementById("radio").value = "1";   
    document.getElementById("direccion").value = "1";    
    document.getElementById("dirLatitud").value = ""; 
    document.getElementById("dirLongitud").value = "";    
    document.getElementById("dirLatLon").value = "";
    */
   document.getElementById("form-punto").reset();
        
    // Se habilita el tipo de punto
    var tipoPunto  = document.getElementById("tipoPunto");
    var rtipoPunto = tipoPunto.getElementsByTagName("input");
    for (var i = 0; i < rtipoPunto.length; i++) {
        rtipoPunto[i].disabled = false;
        rtipoPunto[i].checked  = false;
    }
    
    document.getElementById("msg").innerHTML = "";
    document.getElementById("msg").setAttribute("class", "form-msg bottom-space");
    
    // setCodigoPunto();
    // Inhabilita interfaz para actualizar punto
    prepareUpdatePunto(false);
}

/*
 * Envia lista de puntos junto con su codigo_punto asignado.
 */
function sendListaPuntos () {    
    /*
        var puntosKML = document.getElementById("puntosKML").value;
        if (puntosKML == "true") {
            document.getElementById("form-lista-puntos-kml").submit();
            return;
        } 
    */
    
    var codPto = document.getElementById("lastCod").value;    
    codPto = (codPto == null || codPto == "") ? 1 : parseInt(codPto) + 1;
    
    var listaPuntos = getListaPuntos(codPto);
    
    if (listaPuntos == "") {
        document.getElementById("msg").innerHTML = "* Elija al menos un punto en el mapa.";
        document.getElementById("msg").setAttribute("class", "form-msg bottom-space alert alert-info");
    } else {    
        document.getElementById("listaPuntos").value = listaPuntos;
        document.getElementById("form-lista-puntos").submit();    
    }
}

/*
 * Prepara formulario de punto/base para actualizacion.
 */
function prepareUpdatePunto (v) {
    
    var groupInsert = document.getElementById("group-insert");
    var groupUpdate = document.getElementById("group-update");
    
    if (v) {
        groupInsert.style.display = "none";
        groupUpdate.style.display = "block";
    } else {
        groupInsert.style.display = "block";
        groupUpdate.style.display = "none";
    }
}

var EN_MODO_EDICION = false;

/*
 * Carga datos de un punto/base a partir de tabla,
 * para iniciar edicion.
 */
function editarPunto (idPtoBase, tipoPunto_) {
    var tablaPunto = document.getElementById("tablaPuntos");
    EN_MODO_EDICION = true;
    
    for (var i = 0; i < tablaPunto.rows.length; i++) {        
        var cellsOfRow = tablaPunto.rows[i].getElementsByTagName("td");
        var colsLength = cellsOfRow.length;
        for (var j = 0; j < colsLength; j++) {
            if (j == colsLength-1) {
                // j == 1
                // var contentCell  = cellsOfRow[j].innerHTML; // codigo punto *** No usado por duplicacion ***
                // j == colsLength - 1
                var contentCell  = cellsOfRow[j].innerHTML; // Ultima columna especifica id punto/base
                var contentCell2 = cellsOfRow[colsLength-2].innerHTML; // Penultima columna especifica el tipo de punto                                
                
                if (contentCell == idPtoBase && contentCell2 == tipoPunto_) {
                    document.getElementById("nombre").value         = cellsOfRow[0].innerHTML;                    
                    document.getElementById("codPtoBase").value     = cellsOfRow[1].innerHTML;
                    document.getElementById("latitud").value        = cellsOfRow[2].innerHTML;
                    document.getElementById("longitud").value       = cellsOfRow[3].innerHTML;
                    document.getElementById("dirLatitud").value     = cellsOfRow[4].innerHTML;
                    document.getElementById("dirLongitud").value    = cellsOfRow[5].innerHTML;
                    document.getElementById("radio").value          = cellsOfRow[6].innerHTML;                    
                    
                    var lat = cellsOfRow[4].innerHTML;
                    var lon = cellsOfRow[5].innerHTML;
                    document.getElementById("dirLatLon").value = lat +"/"+ lon;
                    
                    var tipoPunto   = document.getElementById("tipoPunto");                     
                    var direccion   = document.getElementById("direccion"); 
                    var rtipoPunto  = tipoPunto.getElementsByTagName("input");
                    
                    var sdir = cellsOfRow[7].innerHTML;                   
                    var vdir = (sdir.indexOf("Entrando") >= 0) ? "1" :
                               (sdir.indexOf("Saliendo") >= 0) ? "2" : "1";
                    
                    // Selecciona direccion (entrando/saliendo) para punto no-base
                    direccion.value = vdir;
                            
                    if (sdir.indexOf("Base") >= 0) {                        
                        rtipoPunto[0].checked = true;  // Base
                        direccion.disabled = true;
                    } else {
                        rtipoPunto[1].checked = true;  // Punto control      
                        direccion.disabled = false;
                    }
                    
                    // Se habilitan tipos de punto
                    for (var k = 0; k < rtipoPunto.length; k++) {
                        rtipoPunto[k].disabled = false;
                    }
                    
                    // Se deshabilitan tipos de punto restantes
                    // (no seleccionados)
                    for (var k = 0; k < rtipoPunto.length; k++) {
                        if (!rtipoPunto[k].checked) {
                            rtipoPunto[k].disabled = true;
                        }
                    }
                    
                    var lat = cellsOfRow[2].innerHTML;
                    var lon = cellsOfRow[3].innerHTML;
                    addNewMarkExtern (lat, lon);
                    
                    // Prepara interfaz para actualizar punto
                    prepareUpdatePunto(true);
                    
                    break;
                }
            }
        }
    }
}

/*
 * Extrae puntos elegidos para una ruta.
 */
function extractPuntosRuta () {
    
    var puntosRuta = document.getElementById("spuntosruta").options;
    var puntos = "";
    
    for (var i = 1; i < puntosRuta.length; i++) {
        if (puntosRuta[i].selected && !puntosRuta[i].disabled) {
            var punto = puntosRuta[i].value; // &* codpto, nombre, lat, lon
            if (puntos == "") {
                puntos = punto;
            } else {
                puntos += "&" + punto;
            }
        }
    }
    return puntos;
}

/*
 * Envia lista de puntos de una ruta.
 */
function sendPuntosRuta () {
    var puntos = extractPuntosRuta();    
    var ruta = document.getElementById("sruta").value;
    
    document.getElementById("ruta").value = ruta;
    document.getElementById("puntos").value = puntos;
    document.getElementById("form-nueva-ruta").submit();
}

/*
 * Visualiza los puntos de una ruta en el mapa.
 * Filtra los puntos y bases correspondientes de la ruta en las tablas.
 */
function marcarRuta () {    
    
    var sruta = document.getElementById("sruta");
    var ruta  = sruta.value;
    //var truta = sruta.options[sruta.selectedIndex].text;
    
    if (ruta == "") {
        removePath();
        document.getElementById("divtablaPuntosRuta").style.display = "none";
        document.getElementById("divtablaBasesRuta").style.display = "none";
        return;
    }
        
    visualiza ("ispuntosruta", ruta);   // idRuta, nom, lat, lon >> Lista con puntos de cada ruta
    visualiza ("isbasesruta",  ruta);   // idRuta, nom, lat, lon >> Lista con bases de cada ruta
    
    // Mostramos tabla dinamica tablaPuntosRuta
    document.getElementById("divtablaPuntosRuta").style.display = "";
    // Mostramos tabla dinamica tablaBasesRuta
    document.getElementById("divtablaBasesRuta").style.display = "";    
    // Fitramos valores en tablaPuntosRuta segun la ruta seleccionada    
    $('#tablaPuntosRuta').DataTable().columns(1).search(ruta).draw();
    // Fitramos valores en tablaBasesRuta segun la ruta seleccionada 
    $('#tablaBasesRuta').DataTable().columns(1).search(ruta).draw();
}

/*
 * Visualiza puntos/bases en el mapa.
 */
function visualiza (nombreSelect, ruta) {
    
    removePath();
    
    var listaPuntos = document.getElementById(nombreSelect).options;    
    
    // Visualizacion de puntos/bases
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
}

/*
 * Extrae informacion de tablaPuntosRuta y tablaBasesRuta que
 * posteriormente se envia en formulario de actualizacion.
 */
function sendPuntosRutaUPD() {
    // Se hace que tabla dinamica muestre todas sus filas,
    // de lo contrario unicamente procesara las que
    // actualmente se encuentren paginadas.
    $('#tablaPuntosRuta').DataTable().rows().nodes().page.len(-1).draw();
        
    // Inicia extraccion de puntos/bases
    var dataStr = extractPuntosRutaUPD();
    var ruta    = document.getElementById("sruta").value;
    
    if (ruta == "") {
        document.getElementById("msg").innerHTML = "* Seleccione una ruta.";
        document.getElementById("msg").setAttribute("class", "form-msg alert alert-danger");
        return;
    }
    
    // Se envian puntos ruta para actualizar
    if (dataStr != "") {
        document.getElementById("idRuta").value = ruta;
        document.getElementById("updPuntosRuta").value = dataStr;
        document.getElementById("form-actualiza-ruta").submit();
    }
}

/*
 * Inicia extraccion de informacion de puntos desde tablas.
 */
function extractPuntosRutaUPD () {
    
    var ruta = document.getElementById("sruta").value; // idRuta
    var dataPuntos = extract ("tablaPuntosRuta");
    var dataBases  = extract ("tablaBasesRuta");    
    var data = dataPuntos + "&" + dataBases;
    
    if (data.indexOf(",") < 0) return "";
    return data;
}

/*
 * Extrae informacion de tablas tablaPuntosRuta y
 * tablaBasesRuta y crea listado de puntos como cadenas
 * de texto formateado.
 */
function extract (nombreTabla) {
    
    var tabla   = document.getElementById(nombreTabla);
    var dataStr = "";
    
    // Procesamiento de puntos/bases
    // FormatoEntrada: id, idRuta, idPunto, nombrePunto/nombreBase, nombreRuta, promMin, holguraMin, valorPunto, tipo
    // FormatoSalida:  id, idRuta, idPunto, promMin, holguraMin, valorPunto, tipo    
    
    for (var i = 0; i < tabla.rows.length; i++) {        
        var cellsOfRow = tabla.rows[i].getElementsByTagName("td");
        var str = "";
        for (var j = 0; j < cellsOfRow.length; j++) {
            
            var cell = cellsOfRow[j];
            
            if (j == 0) { str = cell.innerHTML; }
            if (j == 1) { str += "," + cell.innerHTML; }
            if (j == 2) { str += "," + cell.innerHTML; }
            if (j == 5 || 
                j == 6 || 
                j == 7) {
                var text = cell.getElementsByTagName("input");
                text = text[0];
                str += "," + text.value;
            }
            if (j == 8) {
                var select = cell.getElementsByTagName("select");
                select = select[0];
                str += "," + select.value;
            }
        }
        
        if  (dataStr == "") dataStr = str;
        else dataStr += "&" + str;
    }
    
    return dataStr;
}

/*
 * Filtra lista de dpto segun pais seleccionado.
 */
function filtrarDpto () {
    var compPais = document.getElementById("spais");
    var compDpto = document.getElementById("sdpto");    
    
    if (compPais != null &&
        compDpto != null) {
        
        hiddenOptSelect("sdpto");
        var pais = compPais.value;
        
        for (var i = 0; i < compDpto.options.length; i++) {
            var val = compDpto.options[i].value;            
            var paisDpto = val.split(",");
            
            if (paisDpto[0] == pais) {
                compDpto.options[i].style.display = "block";
            }
        }
    }
}

/*
 * Filtra lista de ciudades segun dpto seleccionado.
 */
function filtrarCiudad () {
    var compDpto = document.getElementById("sdpto");
    var compCiudad = document.getElementById("sciudad");
    
    if (compDpto    != null &&
        compCiudad  != null) {
        
        hiddenOptSelect("sciudad");
        var dpto = compDpto.value;
        dpto = dpto.split(",")[1];
        
        for (var i = 0; i < compCiudad.options.length; i++) {
            var val = compCiudad.options[i].value;
            var dptoCiudad = val.split(",");
            
            if (dptoCiudad[0] == dpto) {
                compCiudad.options[i].style.display = "block";
            }
        }
    }
}

/*
 * Oculta opciones de componente select identificado por id.
 */
function hiddenOptSelect (id) {
    var comp = document.getElementById(id);
    if (comp != null) {        
        for (var i = 1; i < comp.options.length; i++) {            
            comp.options[i].style.display = "none";
        }
    }
}

/*
 * Inicia ocultacion de opciones para elementos select
 * especificados en array arr.
 */
function hiddenOptSelectArray(arr) {
    for (var i = 0; i < arr.length; i++) {
        hiddenOptSelect(arr[i]);
    }
}

/*
 * Verifica valores de campos para formulario empresa
 * y envia datos segun el tipo de accion y si con correctos.
 * (type define si datos son para insercion o actualizacion).
 */
function sendEmpresa (type) {
    
    if (vdt_validaEmpresa()) {
        
        var form =
                (type == "ins") ? "#form-nueva-empresa" :
                (type == "act") ? "#form-actualiza-empresa" : null;
           if (form == null) return;

        //document.getElementById(form).submit();
        $(form).find('[type="submit"]').trigger('click');
    }
}

/*
 * Verifica y envia asignacion de grupo para una empresa.
 * (type define si datos son para insercion o actualizacion).
 */
function sendGrupoEmpresa (type) {
    var compEmpresa = document.getElementById("sempresa");
    var vmsg = "* Especifique una empresa.";
    
    var msg  = document.getElementById("msg");
    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");
    
    if (compEmpresa != null) {
        var num = /^[0-9]+$/;
        
        if (num.test(compEmpresa.value)) {
            var form =
                 (type == "ins") ? "#form-nuevo-gempresa" :
                 (type == "act") ? "#form-actualiza-gempresa" : null;
            if (form == null) return;
            
            //document.getElementById(form).submit();
            $(form).find('[type="submit"]').trigger('click');
        } else {
            msg.innerHTML = vmsg;
            msg.setAttribute("class", "form-msg bottom-space alert alert-danger");
        }
    }
}

/*
 * Verifica y envia asignacion de ruta para una empresa.
 * (type define si datos son para insercion o actualizacion).
 */
function sendRuta (type) {
    
    if (vdt_validaRuta()) {
        
        var form =
             (type == "ins") ? "#form-nueva-ruta" :
             (type == "act") ? "#form-actualiza-ruta" : null;
        if (form == null) return;

        //document.getElementById(form).submit();
        $(form).find('[type="submit"]').trigger('click');
    }
}

// Envia asignacion de longitud de ruta.
function sendLongitudRuta() {
    var mesAnterior  = document.getElementById("mesAnterior");
    var vmesAnterior = document.getElementById("vmesAnterior");    
    vmesAnterior.value = (mesAnterior.checked) ? "1" : "0";
    $('#form-longitud-ruta').submit();
}

/*
 * Verifica y envia asignacion de una tarifa.
 * (type define si datos son para insercion o actualizacion).
 */
function sendTarifa (type) {
    
    if (vdt_validaTarifa()) {
        
        var form =
                (type == "ins") ? "#form-nueva-tarifa" :
                (type == "act") ? "#form-actualiza-tarifa" : null;
        if (form == null) return;
    }
    
    //document.getElementById(form).submit();
    $(form).find('[type="submit"]').trigger('click');
}

/*
 * Ubica valores existentes para los componentes
 * select (pais, moneda, zona horaria, dpto y ciudad).
 * Para componentes dpto y ciudad se usa procedimiento
 * independiente debido a la conformacion de sus valores.
 */
function setUbicacion () {    
    
    // Seleccion de pais, moneda y zona horaria
    selectElement();
    
    var compDpto = document.getElementById("idpto");
    var compCiudad = document.getElementById("iciudad");
    
    if (compDpto    != null &&
        compCiudad  != null) {
        
        var arrComp  = ["sdpto", "sciudad"];
        var arrVal   = [compDpto.value, compCiudad.value];
        
        for (var i = 0; i < arrComp.length; i++) {
            var compUbic = document.getElementById(arrComp[i]);
            
            for (var j = 1; j < compUbic.options.length; j++) {
                var val = compUbic.options[j].value;
                val = val.split(",")[1];
                
                if (val == arrVal[i]) {
                    compUbic.options[j].selected = true;
                    break;
                }
            }
        }
    }
}

/*
 * Verifica campos e inicia registro/actualizacion de usuario.
 */
function sendUsuario (type) {
        
    if (vdt_validaUsuario()) {
        
        var form =
            (type == "ins") ? "#form-nuevo-usuario" :
            (type == "act") ? "#form-actualiza-usuario" : null;
        if (form == null) return; 

        //Accionar submit salta las restricciones de los campos
        //document.getElementById(form).submit();    
        $(form).find('[type="submit"]').trigger('click'); 
    }
}

/*
 * Verifica campos e inicia registro/actualizacion de perfil de usuario.
 */
function sendPerfilUsuario () {
    
    if (vdt_validaPerfilUsuario()) {
        
        var form = "#form-actualiza-perfil";
        $(form).find('[type="submit"]').trigger('click');
    }
}

/* 
 * Verifica campos e inicia registro de nuevo usuario (Registro externo).
 */
function sendUsuarioExtern () {
    
    if (vdt_validaUsuarioExterno()) {
        
        var form = "#form-nuevo-usuario-externo";
        $(form).find('[type="submit"]').trigger('click');
    }
}

/*
 * Hace restauracion de contraseña / verifica igualdad de contraseña.
 */
function sendRestorePassword () {
    var pass    = document.getElementById("pass").value;
    var cpass   = document.getElementById("cpass").value;    
    var msg     = document.getElementById("msg");
    
    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");
        
    if (pass != cpass) {        
        msg.innerHTML = "* Contrase&ntilde;as no coinciden.";        
        msg.setAttribute("class", "form-msg bottom-space alert alert-danger");
    } else {
        //document.getElementById("form-cambio-password").submit();
        var form = "#form-restaura-password";
        $(form).find('[type="submit"]').trigger('click');
    }
}

/*
 * Inicia cambio de contraseña por usuario / verifica igualdad de contraseña.
 */
function sendChangePassword () {
    
    if (vdt_validaCambioContrasena()) {        
    
        var form = "#form-cambio-password";
        $(form).find('[type="submit"]').trigger('click');
    }    
}

/*
 * Inicia proceso de restauracion de contraseña, verifica y envia
 * correo electronico a usuario para continuar el proceso.
 */
function sendEmailRestore() {
    
    if (vdt_validaCorreo()) {
        
        var form = "#form-correo-restauracion";
        $(form).find('[type="submit"]').trigger('click');
    }
}

/*
 * Habilita o deshabilita direccion y establece codigo de punto
 * segun el tipo de punto elegido.
 */
function setDireccionPunto (setPunto) {    
    var direccion = document.getElementById("direccion");
    
    if (direccion != null) {
        if (setPunto) { direccion.disabled = false; setCodigoPunto("maxcodPto"); }    // Punto control
        else          { direccion.disabled = true;  setCodigoPunto("maxcodBase"); }   // Base
    }
}

/*
 * Establece direccion latitud/longitud para punto especificado manualmente.
 */
function setDireccionPunto_m () {
    var lat = document.getElementById("latitud");
    var lon = document.getElementById("longitud");
    
    var floatReg = /^-?[0-9]+\.[0-9]+$/;
    
    if (lat != null && lon != null &&
        floatReg.test(lat.value) && 
        floatReg.test(lon.value)) {        
    
        addNewMarkExtern(lat.value, lon.value);
        setDirCoord (lat.value, lon.value);
    }
}

/*
 * Restaura componentes graficos y agrega nueva
 * marca en mapa para visualizar.
 */
function mostrarPunto (lat, lon) {
    
    //if (EDITAR_PUNTO) { EDITAR_PUNTO = false; }
    if (EN_MODO_EDICION) { EN_MODO_EDICION = false; }
    else { cancelPunto(); }
    
    if (lat != "NA" && lon != "NA")
        addNewMarkExtern (lat, lon);
}

/*
 * Elimina resaltado (clase css) de las filas que lo
 * estan y resalta fila especificada.
 */
function resaltarPuntoEnTabla(idtable, tr) {
    
    var tbl_puntos = $('#' + idtable).DataTable();
    tbl_puntos = tbl_puntos.rows().nodes();    
    
    // Elimina resaltado
    for (var i = 0; i < tbl_puntos.length; i++) {
        var row = tbl_puntos[i];
        var row_class = row.getAttribute("class");
        if (row_class != null) {
            var arr = row_class.split(" ");
            if (arr.length > 1) {
                row.setAttribute("class", arr[0]);
            }
        }
    }
    
    // Agrega resaltado
    var tr_class = tr.getAttribute("class");
    tr.setAttribute("class", tr_class + " tr-activo");    
}

/*
 * Sincroniza componentes select splaca y snumiterno,
 * para que apunten al mismo elemento segun su contenido
 * e indices especificados.
 */
function syncSelect (idselect, f, t) {
    
    var sfrom, sto; sfrom = sto = null;
    
    if (idselect == "splaca") {        
        sfrom   = document.getElementById(idselect);
        sto     = document.getElementById("snuminterno");        
    
    } else if (idselect == "snuminterno") {
        sfrom   = document.getElementById(idselect);
        sto     = document.getElementById("splaca");        
    }
    
    if (sfrom != null & sto != null) {
        var v   = sfrom.value;
        var idf = v.split(",")[f];        
        
        for (var i = 0; i < sto.options.length; i++) {
            var w   = sto.options[i].value;
            var idt = w.split(",")[t];
            
            if (idf == idt) {
                sto.options[i].selected = true;
                break;
            }
        }
    }
}

/*
 * Valida parametros de consulta de informacion registradora.
 */
function validaParametrosInfoReg() {
    
    var fechaInicio = document.getElementById("fechaInicio").value;
    var fechaFinal  = document.getElementById("fechaFinal").value;
    var movil       = document.getElementById("smovil").value; // id,placa,numInterno
    var limite      = document.getElementById("slimite").value;
    var msg         = document.getElementById("msg");
    
    // valores de filtro    
    $('#p_numvuelta').val($('#numvuelta').val());

    var fecha = /^[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2}$/;
    
    if (movil != null && movil != "") {
        var array_movil = movil.split(",");
        
        $('#id_movil').val(array_movil[0]);
        $('#placa').val(array_movil[1]);
        $('#num_interno').val(array_movil[2]);
        
        if (fechaInicio == null || fechaInicio == "") {
            msg.innerHTML = "* Por favor elija una fecha inicial."; 
            msg.setAttribute("class", "form-msg top-space bottom-space alert alert-info"); return false;
        }
        
        if (!fecha.test(fechaInicio)) {
            msg.innerHTML = "* Valor de fecha inicial es incorrecto.";
            msg.setAttribute("class", "form-msg top-space bottom-space alert alert-info"); return false;
        }
        
        if (fechaFinal != null && fechaFinal != "") {
            if (!fecha.test(fechaFinal)) {
                msg.innerHTML = "* Valor de fecha final es incorrecto.";
                msg.setAttribute("class", "form-msg top-space bottom-space alert alert-info"); return false;
            }
        }
    }
    
    return true;
}

/*
 * Inicia consulta de informacion registradora.
 */
function sendInfoReg () {
    
    if (validaParametrosInfoReg()) {
        var form = "#form-consulta-inforeg";
        $(form).find('[type="submit"]').trigger('click');
    }
}

/*
 * Inicia consulta de ultimos 30 registros de informacion registradora.
 */
function sendInfoReg30 () {
    document.getElementById("fechaInicio").value = "";
    document.getElementById("fechaFinal").value = "";
    document.getElementById("smovil").value = "";    
    document.getElementById("id_movil").value = "";    
    document.getElementById("placa").value = "";    
    document.getElementById("num_interno").value = "";    
    document.getElementById("slimite").selectedIndex = 1;
    
    document.getElementById("p_ruta").value = "";
    document.getElementById("p_numvuelta").value = "0";    
    
    document.getElementById("sruta").selectedIndex = 0;
    document.getElementById('numvuelta').value = "0";
    
    var form = "#form-consulta-inforeg";
    $(form).find('[type="submit"]').trigger('click');
}

/*
 * Establece valores, deshabilita componentes y filtra tabla
 * segun valores previos establecidos.
 */
function inforeg_inicio() {
    //inforeg_consultaInicial();
    inforeg_establecerParametros();
    inforeg_verificarAutoConsulta();
    inforeg_habilitarFechas(false);
    inforeg_filtrar();
    inforeg_defecto();
}

/*
 * Reestablece variables de acceso. 
 */
function inforeg_defecto() {
    document.getElementById("accesoInfoGeneral").value = "";
    document.getElementById("accesoInfoSalida").value = "";    
    document.getElementById("accesoAsignarRuta").value = "";
}

/* 
 * Consulta inicial con datos mas recientes para informacion registradora.
 */
function inforeg_consultaInicial() {
    var data = document.getElementById("data_ir");
    if (data.value == null || data.value == "") {
        sendInfoReg30();
    }
}

/*
 * Filtra registros de informacion registradora.
 */
function inforeg_filtrar () {
    
    var ruta        = document.getElementById("sruta").value;
    var numvuelta   = document.getElementById("numvuelta").value;
    
    numvuelta = (!exp_numerico.test(numvuelta)) ? "0" : numvuelta;
    document.getElementById("numvuelta").value = numvuelta;
        
    // Eliminamos todos los filtros anteriores
    $('#tablaInfoReg').DataTable().search('').columns().search('').draw();
        
    // Se filtra tabla respecto a ruta o numero de vuelta (si se encuentran establecidos)
    if (ruta != "")         { $('#tablaInfoReg').DataTable().columns(3).search('^'+ruta+'$', true, false).draw(); }
    if (numvuelta != "0")   { $('#tablaInfoReg').DataTable().columns(9).search('^'+numvuelta+'$', true, false).draw(); }
}

/*
 * Mantiene los parametros de busqueda de informacion registradora.
 * (Actualiza valores de componentes graficos)
 */
function inforeg_establecerParametros () {
        
    var movil       = document.getElementById("p_movil").value;
    var ruta        = document.getElementById("p_ruta").value;
    var numvuelta   = document.getElementById("p_numvuelta").value;
    var limite      = document.getElementById("p_limite").value;
    var tipodia     = document.getElementById("p_tipodia").value;
    
    selectElementAUX('sruta', ruta);    
    numvuelta = (!exp_numerico.test(numvuelta)) ? "0" : numvuelta;
    document.getElementById('numvuelta').value = numvuelta;
    
    if (movil == "") return;    
    
    $('#smovil').selectpicker('val', movil);
    $('#slimite').selectpicker('val', limite);    
    $('#stdia').selectpicker('val', tipodia);
}

/*
 * Consulta informacion especifica de registro informacion-registradora
 * identificado por id.
 */
function inforeg_mostrarRegistroIR(id) {
    
    var ruta = document.getElementById("sruta");
    var numvuelta = document.getElementById("numvuelta");
    document.getElementById("p_ruta").value = ruta.value;
    document.getElementById("p_numvuelta").value = numvuelta.value;    
    $('#form-mostrar-rir-' + id).submit();
}

// Muestra componente con identificador id.
function show (id) {
    var comp = document.getElementById(id);
    if (comp != null)
        comp.style.display = "block";
}

// Oculta componente con identificador id.
function hidden (id) {
    var comp = document.getElementById(id);
    if (comp != null)
        comp.style.display = "none";
}

// Crea arreglo de colaboracion para campos de informacion-registradora.
// Se usa posteriormente para habilitar campos, comprobar valores e
// imprimir mensajes de error.
function inforeg_campos(id) {
    
    var msg_numeracion  = "* Numeraci&oacute;n &uacute;nicamente entre valores 0 - 999999.";
    var msg_entsal      = "* Entradas/Salidas &uacute;nicamente entre valores 0 - 9999.";
    var msg_fecha       = "* Especifique valor de fechas en formato YYYY-MM-DD. Ej. 2017-01-30.";
    var msg_hora        = "* Especifique valor de horas en formato HH:MM:SS. Ej. 23:00:00.";
    
    var tid = id.toLowerCase();
    var habilitar_bs = true;
    if (tid.indexOf("salida") >= 0) { habilitar_bs = false; }    
    
    var habilitar = true;
    var campos_inforeg = [
        ["numeracionBS",        exp_numeracion, msg_numeracion, habilitar_bs],
        ["numeroLlegada",       exp_numeracion, msg_numeracion, habilitar],
        ["diferenciaNumero",    exp_numeracion, msg_numeracion, !habilitar],
        ["fechaIngreso",        exp_fecha, msg_fecha,   !habilitar],
        ["horaIngreso",         exp_hora,  msg_hora,    habilitar],
        ["fechaSalidaBS",       exp_fecha, msg_fecha,   !habilitar],
        ["horaSalidaBS",        exp_hora,  msg_hora,    habilitar]
    ];
    
    return campos_inforeg;
}

// Habilita componentes input de formulario.
function show_enable (id) {
    var id_comp   = "control" + id;
    var id_form   = "form" + id;
    
    // Obtiene array de colaboracion con campos
    var campos_editar_inforeg = inforeg_campos(id);
    
    show (id_comp);
    var form = document.getElementById(id_form);
    var inputs = form.getElementsByTagName("input"); 
    
    for (var i = 0; i < inputs.length; i++) {
        var id_campo = inputs[i].id;
        for (var j = 0; j < campos_editar_inforeg.length; j++) {            
            // diferenciaNumero y fechas siguen inhabilitados
            // habilita campos para edicion permitidos
            if (id_campo.localeCompare(campos_editar_inforeg[j][0]) == 0 &&
                campos_editar_inforeg[j][3]) {                
                inputs[i].readOnly = false;
                inputs[i].setAttribute("class", "input128 input-edit-color");
            }
        }        
    }
    inforeg_habilitarFechas(false);
}

// Deshabilita componentes input de formulario.
function hidden_disable (id) {
    var id_comp   = "control" + id;
    var id_form   = "form"    + id;
    var id_msg    = "msg"     + id;
    var id_motivo = "motivo"  + id;
    var id_acceso = "acceso"  + id;
    
    hidden (id_comp);
    var form = document.getElementById(id_form);
    var inputs = form.getElementsByTagName("input");
    
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].type == "password") continue;
        if (inputs[i].type == "button") {
            inputs[i].setAttribute("class", "input84");
        } else {
            inputs[i].readOnly = true;
            inputs[i].setAttribute("class", "input128");
        }
    }
    
    document.getElementById(id_msg).innerHTML = "";
    document.getElementById(id_motivo).value  = "";
    document.getElementById(id_acceso).value  = "";
    inforeg_habilitarFechas(false);
    inforeg_defecto();
}

// Inicia verificacion y envio de datos de informacion-registradora
// para ser actualizados.
function inforeg_editar(id) {
                
    var motivo = document.getElementById("motivo" + id).value;    
    var msg    = document.getElementById("msg" + id);    
    
    // Verifica campos
    if (!inforeg_verificarCampos(id)) {
        return;
    }
   
    // Verifica campo motivo y hace envio de datos si ha sido correcto
    if (exp_motivo.test(motivo)) {
        $("#form" + id).submit();
    } else {
        msg.innerHTML = "* Especifique un motivo correctamente. Caracteres alfanum&eacute;ricos y s&iacute;mbolos como ,.";      
    }
}

// Itera sobre cada campo de formulario de edicion informacion-registradora,
// y comprueba validez de cada uno de sus valores.
function inforeg_verificarCampos(id) {
    var form    = document.getElementById("form" + id);
    var msg     = document.getElementById("msg"  + id);
    var inputs  = form.getElementsByTagName("input");
    msg.innerHTML = "";
    
    // Obtiene array de colaboracion con campos
    var campos_editar_inforeg = inforeg_campos(id);
    
    for (var i = 0; i < inputs.length; i++) {
        var id_campo_form = inputs[i].id;
        
        for (var j = 0; j < campos_editar_inforeg.length; j++) {
            var id_campo  = campos_editar_inforeg[j][0];            
            var exp_campo = campos_editar_inforeg[j][1];            
            var exp_msg   = campos_editar_inforeg[j][2];                     
                        
            if (id_campo_form.localeCompare(id_campo) == 0) {
                if (!exp_campo.test(inputs[i].value)) {
                    // Notifica al usuario con mensaje de error
                    // en caso de ser valor incorrecto
                    msg.innerHTML = exp_msg;
                    return false;
                }
            }
        }
    }   
    return true;
}

// Edita valor de diferencia numeracion cuando
// valores de numeracion llegada y/o salida se modifican.
function inforeg_editarNumeracion() {
    var num_salida  = $('#numeracionBS').val();
    var num_llegada = $('#numeroLlegada').val();
    var diferencia  = (num_llegada - num_salida);    
    $('#diferenciaNumero').val(diferencia);
}

// Habilita/Deshabilita componentes fecha de formulario.
function inforeg_habilitarFechas(enable) {
    if (enable) {
        $('#fechaSalidaBS').datepicker('_attachEvents');
        $('#fechaSalidaBS').datepicker('_attachSecondaryEvents');
        $('#fechaIngreso').datepicker('_attachEvents');
        $('#fechaIngreso').datepicker('_attachSecondaryEvents');
    } else {
        $('#fechaSalidaBS').datepicker('_detachEvents');
        $('#fechaSalidaBS').datepicker('_detachSecondaryEvents');
        $('#fechaIngreso').datepicker('_detachEvents');
        $('#fechaIngreso').datepicker('_detachSecondaryEvents');
    }
}

// Envio de datos para asignacion de ruta a un registro 
// de especifico de informacion-registradora.
function inforeg_asignarRuta(id) {
    $("#form" + id).submit();
}

var cambioFecha = true;
function inforeg_cambioDia() {
    var stdia = document.getElementById("stdia");
    var fecha = null;
    
    if (stdia.value === "1") {
        fecha = new Date();
    }
    if (stdia.value === "2") {
        fecha = new Date();
        fecha.setDate(fecha.getDate()-1);
    }
    
    if (fecha != null) {
        cambioFecha = false;
        $('#fechaInicio').datepicker('setDate', fecha);
        $('#fechaFinal').datepicker('setDate', fecha);        
        $('#fechaInicio').datepicker('update');
        $('#fechaFinal').datepicker('update');
        cambioFecha = true;
    }    
}

function inforeg_cambioFecha() {    
    if (cambioFecha) {
        $('#stdia').selectpicker('val', '0');    
    }
}

// Muestra cuadro de dialogo especifico.
function showDialog (id) {            
    id = "#" + id;
    $(id).dialog("open");
}

// Oculta cuadro de dialogo especifico.
function hiddenDialog (id) {
    id = "#" + id;
    $(id).dialog("close");    
}

// Inicia exportacion de registros de informacion-registradora a excel.
function sendExportExcel () {
    
    if (validaParametrosInfoReg()) {
        
        document.getElementById("id_movil_e").value    = document.getElementById("id_movil").value;
	document.getElementById("placa_e").value       = document.getElementById("placa").value;
	document.getElementById("num_interno_e").value = document.getElementById("num_interno").value;
	document.getElementById("fechaInicio_e").value = document.getElementById("fechaInicio").value;
	document.getElementById("fechaFinal_e").value  = document.getElementById("fechaFinal").value;
	document.getElementById("slimite_e").value     = document.getElementById("slimite").value;
        
        document.getElementById("form-exportar-excel-ir").submit();
    }
}

// Inicia auto-consulta de informacion registradora. 
function sendAutoQuery () {
    
    var array = ["btnBuscar", "btnBuscar30", "btnExportar"];
    
    var chkquery = document.getElementById("chk_autoconsulta");
    if (chkquery.checked) {
        document.getElementById("autoconsulta").value = "true";
        disableElemts(array, true);
        var movil = document.getElementById("smovil").value;
        if (movil != "") { sendInfoReg(); }
        else             { sendInfoReg30(); }
    } else {
        document.getElementById("autoconsulta").value = "";
    }    
}

/*
 * Mantiene auto-consulta de informacion registradora cada
 * 10 segundos.
 */
function inforeg_verificarAutoConsulta () {
    
    var autoquery = document.getElementById("autoconsulta").value;
    var movil = document.getElementById("smovil").value;
    document.getElementById("chk_autoconsulta").checked = false;    
   
    var array = ["btnBuscar", "btnBuscar30", "btnExportar"];
   
    if (autoquery == "true") {        
        document.getElementById("chk_autoconsulta").checked = true;                
        disableElemts(array, true);
        setTimeout(function () {            
            if (movil != "") { sendInfoReg(); }
            else             { sendInfoReg30(); }
        }, 10000);
    }    
}

/*
 * Deshabilita/habilita segun valor de value 
 * componentes con ids especificados en array.
 */
function disableElemts (array, value) {
    for (var i = 0; i < array.length; i++) {
        var elemt = document.getElementById(array[i]);
        elemt.disabled = value;
    }
}

/*
 * Consulta vehiculos en perimetro cada 10 segundos.
 */
function consultaVehiculosPerimetro() {
    var form = document.getElementById("form-visualiza-perimetro");
    setTimeout(function () {
        form.submit();
    }, 10000);
}

/*
 * Visualiza puntos a partir de datos cargados desde un archivo KML.
 */
function visualizaPuntosKML() {
    
    var puntosKML = document.getElementById("puntosKML").value;
    
    if (puntosKML == "true") { 
        iniciarMapaSoloLectura(); 
    } else { 
        iniciarMapa();
    }
    
    var spuntosKML = document.getElementById("spuntosKML");
    
    removePath();    
    for (var i = 0; i < spuntosKML.options.length; i++) {
        var data = spuntosKML.options[i].value;
        var lat = data.split(",")[0];
        var lon = data.split(",")[1];
        
        addNewSimpleMark(lat, lon);
    }
}

/*
 * Remueve puntos cargados en el mapa y
 * limpia componentes de informacion asociados.
 */
function removeListaPuntos() {
    
    map.remove();

    iniciarMapa();
    
    document.getElementById("latitud").value = "";
    document.getElementById("longitud").value = "";
    document.getElementById("dirLatitud").value = "";
    document.getElementById("dirLongitud").value = "";
}

// Valida y envia datos para actualizacion de informacion de entorno escritorio.
function sendEntorno() {
    
    if (vdt_validaEntorno()) {
        
        var form = "#form-actualiza-entorno";
        $(form).find('[type="submit"]').trigger('click');
    } 
}

// Valida y envia datos para actualizacion de informacion de entorno web.
function sendEntornoWeb() {
    
    if (vdt_validaEntornoWeb()) {
        
        var form = "#form-actualiza-entorno-web";
        $(form).find('[type="submit"]').trigger('click');
    } 
}

// Extrae y envia datos para actualizacion de informacion de presentacion web.
function sendPresentacionWeb() {
    
    var chk_mostrar_es      = document.getElementById("chk_mostrar_es");
    var chk_mostrar_no      = document.getElementById("chk_mostrar_no");
    var chk_mostrar_noic    = document.getElementById("chk_mostrar_noic");
    
    var input_ajuste_hs     = document.getElementById("input_ajuste_hs");    
    var chk_ajuste_pto      = document.getElementById("chk_ajuste_pto");
    
    document.getElementById("mostrar_es").value     = (chk_mostrar_es.checked) ? "1" : "0";
    document.getElementById("mostrar_no").value     = (chk_mostrar_no.checked) ? "1" : "0";
    document.getElementById("mostrar_noic").value   = (chk_mostrar_noic.checked) ? "1" : "0";
    document.getElementById("ajuste_hs").value      = input_ajuste_hs.value;
    document.getElementById("ajuste_pto").value     = (chk_ajuste_pto.checked) ? "1" : "0";
    
    document.getElementById("ajuste_hs").value      = input_ajuste_hs.value;    
    document.getElementById("ajuste_pto").value     = (chk_ajuste_pto.checked) ? "1" : "0";
    
    $('#form-actualiza-presentacion').submit();
}

// Recupera valores y actualiza componentes graficos de presentacion web.
function cargarPresentacionWeb() {
    
    var mostrar_es   = document.getElementById("mostrar_es");
    var mostrar_no   = document.getElementById("mostrar_no");
    var mostrar_noic = document.getElementById("mostrar_noic");
    
    var ajuste_hs  = document.getElementById("ajuste_hs");    
    var ajuste_pto = document.getElementById("ajuste_pto");
    
    document.getElementById("chk_mostrar_es").checked = (mostrar_es.value == "1") ? true : false;
    document.getElementById("chk_mostrar_no").checked = (mostrar_no.value == "1") ? true : false;
    document.getElementById("chk_mostrar_noic").checked = (mostrar_noic.value == "1") ? true : false;
    
    document.getElementById("input_ajuste_hs").value = ajuste_hs.value;    
    document.getElementById("chk_ajuste_pto").checked = (ajuste_pto.value == "1") ? true : false;
}

// Inicia lanzamiento de entorno escritorio.
function lanzarEntorno () {    
    document.getElementById("form-lanza-entorno").submit();
}

// Lanza peticion de informacion de empresa para cargar en pagina de inicio de sesion.
function loginData() {
    $.ajax({
        url: "/RDW1/logindata",
        method: "POST",
        success: function(data) {
            loginLoad(data);
        }
    });                
}

// Agrega lista de empresas a componente grafico select en pagina de inicio de sesion.
function loginLoad(data) {
    
    var s = document.getElementById('sempresa');
    
    if (data != null && data.indexOf("?") > 0) {
        var arr_data = data.split("?");
        for (var i = 0; i < arr_data.length; i++) {
            var item_data = arr_data[i].split(",");
            s.options[s.options.length] = 
                new Option(item_data[1], item_data[0]);
        }
    } else if (data != "") {
        var arr_data = data.split(",");
        s.options[s.options.length] = 
            new Option(arr_data[1], arr_data[0]);
    }
}

// Obtiene posicion actual de pagina.
function scrollPagePosition() {
      
    var position = [0, 0];
      
    if (typeof window.pageYOffset != 'undefined') {      
        position = [      
            window.pageXOffset,      
            window.pageYOffset      
        ];      
    } else if (typeof document.documentElement.scrollTop != 'undefined' && 
                      document.documentElement.scrollTop > 0) {
        position = [      
            document.documentElement.scrollLeft,      
            document.documentElement.scrollTop      
        ];      
    } else if (typeof document.body.scrollTop != 'undefined') {      
        position = [      
            document.body.scrollLeft,      
            document.body.scrollTop      
        ];      
    }

    return position;
}

// Funcion que se ejecuta tras la carga de pagina web.
function onload() {        
    selectElement();
    iniciarMapa();    
    //setCodigoPunto();    
    //setCodigoBase();
}

// Establece zoom de pagina a valor por defecto.
function ZoomDefaultScale() {
    var scale = 'scale(1)';
    document.body.style.webkitTransform = scale;
    document.body.style.msTransform     = scale;
    document.body.style.transform       = scale;
}

/*
 * =============================================================================
 * Procedimientos para eventos de consolidacion
 * =============================================================================
 */

// Verifica y envia datos para cierre de vuelta.
function cons_cerrarVuelta() {
    
    var exp_fecha_hora  = /^[0-9]{4}-[0-9]{2}-[0-9]{2}\s{1,1}[0-9]{2}:[0-9]{2}:[0-9]{2}$/;
    var exp_motivo      = /^([a-zA-Z\u00C0-\u00FF,#\-:\.0-9]+\s*){10,512}$/;
    
    var movil  = document.getElementById("smovil");
    var base   = document.getElementById("sbase");
    var fecha  = document.getElementById("fecha");
    var motivo = document.getElementById("motivo");
    var error_class = "form-msg bottom-space alert alert-info";
    
    var msg = document.getElementById("msg");
    msg.innerHTML = "";
    msg.setAttribute("class", "form-msg bottom-space");
    
    if (movil.value == "") {
        msg.innerHTML = "* Especifique un veh&iacute;culo.";
        msg.setAttribute("class", error_class);
        return;
    }
    if (base.value == "") {
        msg.innerHTML = "* Especifique un punto base.";
        msg.setAttribute("class", error_class);
        return;
    }
    if (fecha.value == "" || !exp_fecha_hora.test(fecha.value)) {
        msg.innerHTML = "* Especifique una fecha correctamente.";
        msg.setAttribute("class", error_class);
        return;
    }
    if (motivo.value == "" || !exp_motivo.test(motivo.value)) {
        msg.innerHTML = "* Especifique un motivo correctamente. Se permiten caracteres alfanum&eacute;ricos y s&iacute;mbolos como ,#-:.";
        msg.setAttribute("class", error_class);
        return;
    }
 
    var arr = movil.value.split(",");
    var placa_nint = arr[1] + " - " + arr[2];
    var msg = "&iquest;Est&aacute; seguro de cerrar la vuelta para el veh&iacute;culo <strong>"+ placa_nint +"</strong>?";
            
    bootbox.confirm({
        title: "Cierre de vuelta",
        message: msg,
        buttons: {
            confirm: {
                label: 'Aceptar',
                className: 'btn btn-primary'
            },
            cancel: {
                label: 'Cancelar',
                className: 'btn'
            }
        },
        callback: function (rs) {
            if (rs) {
                $('#form-cierra-vuelta').submit();
            }
        }
    });
}

/*
 * =============================================================================
 * Form en nueva ventana
 * =============================================================================
 */

// Solicita informacion de despacho en ventana independiente.
function verDespacho(url, action, idDespacho) {    
    var params = { "idDespacho" : idDespacho };
    var windowoption = "width=864,height=512"; 
    doPostInNewWindow(url, action, params, windowoption);
}

// Crea formulario de peticion, ejecuta peticion y abre nueva ventana
// para mostrar resultados.
function doPostInNewWindow(url, action, params, windowoption) {
    
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", action);
    form.setAttribute("target", "newwindow");
    
    for (var i in params) {
        if (params.hasOwnProperty(i)) {
            var input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", i);
            input.value = params[i];
            form.appendChild(input);
        }
    }
    document.body.appendChild(form);
    win = window.open(url, 'newwindow', windowoption);        
    if (win) {
        form.submit();        
    }
    document.body.removeChild(form);        
}

/*
 * =============================================================================
 * Form en nueva ventana (Uso de doPostInNewWindow)
 * =============================================================================
 */

// Solicita informacion de planilla despacho a pedido en ventana independiente.
function dph_ventanaEdicionPlanillaAPedido(url, action, idIntervalo) {
    var params = { "idIntervalo" : idIntervalo };
    var windowoption = "width=864,height=512";
    doPostInNewWindow(url, action, params, windowoption);
}

/*
 * =============================================================================
 * Nueva ventana
 * =============================================================================
 */

// Visualiza punto (evento gps) en mapa en ventana independiente.
function gps_viewPoint(args) {
    
    //var params = "lat="+lat+"&lon="+lon;
    var args_encode = "args=" + encodeURIComponent(args);
    var url = "/RDW1/app/seguimiento/vistaPunto.jsp?" + args_encode;
    var windowoption = "width=864,height=512";
    window.open(url, 'newwindow', windowoption);
}

/*
 * ============================================================================= 
 * Variables de indicadores
 * =============================================================================
 */

// Colores para graficos

COLORS = [
    "#3E95CD",
    "#8E5EA2",
    "#3CBA9F",
    "#E8C3B9",
    "#C45850",
    "#00FFFF",
    "#7FFFD4",    
    "#397EE5",
    "#8A2BE2",
    "#A52A2A",
    "#DEB887",
    "#5F9EA0",
    "#7CE216",
    "#FF7F50",
    "#6495ED",
    "#AEAEAE",
    "#5AC45A",
    "#8B008B", 
    "#FF8C00",
    "#FF1493",
    "#00BFFF",
    "#F765F7",
    "#FFD700",
    "#46AACE",
    "#FF0000",
    "#4169E1",
    "#00FF7F",
    "#9ACD32",
    "#2E8B57"
];
SIZE_COLORS = COLORS.length;

COLORS2 = [
    ["#37A9F2",	"#87D0FF"],
    ["#3CBA9F",	"#87FFE5"],
    ["#D18875",	"#E8C3B9"],
    ["#C45850",	"#FF8D84"],
    ["#00FFFF",	"#C6FFFF"],
    ["#7FFFD4",	"#CCFFED"], 
    ["#397EE5",	"#AACCFF"],
    ["#8A2BE2",	"#E5C9FF"],
    ["#A52A2A",	"#FF9999"],
    ["#DEB887",	"#FFE6C6"],
    ["#5F9EA0",	"#B5E1E2"],
    ["#7CE216",	"#BDEF8B"],
    ["#FF7F50",	"#FFBBA3"],
    ["#6495ED",	"#BCD4FF"],
    ["#AEAEAE",	"#E5E5E5"],
    ["#5AC45A",	"#AAFFAA"],
    ["#8B008B",	"#B56CB5"],
    ["#FF8C00",	"#FFC075"],
    ["#FF1493",	"#FF87C7"],
    ["#00BFFF",	"#9EE6FF"],
    ["#F765F7",	"#FFAFFF"],
    ["#FFD700",	"#FFEC89"],
    ["#46AACE",	"#ADDAEA"],
    ["#FF0000",	"#FF8787"],
    ["#4169E1",	"#92ACFC"],
    ["#00FF7F",	"#A0FFCF"],
    ["#9ACD32",	"#CEE5A0"],
    ["#2E8B57",	"#88C9A5"] 
];
SIZE_COLORS2 = COLORS2.length;

TIME_GRAPH_REFRESH = 10000;
