

/*
 * Carga puntos de ruta en componente select y los visualiza
 * en mapa segun ruta seleccionada.
 */
function marcarRuta2() {
    var listaPuntosGUI  = document.getElementById("listaPuntos").options;    
    var listaPuntosBD   = document.getElementById("listaPuntosInit").options;
    var idRuta          = document.getElementById("sruta").value;
    
    var listaPuntosRuta = document.getElementById("listaPuntosRuta");
    for (var i = listaPuntosRuta.options.length - 1; i >= 0; i--) {
        listaPuntosRuta.remove(i);
    }
    removePath();
    
    for (var i = 0; i < listaPuntosBD.length; i++) {
        var infoPuntoBD = listaPuntosBD[i].value;
        var idRutaBD    = infoPuntoBD.split(",")[0];
        var idPuntoBD   = infoPuntoBD.split(",")[1];
        var estadoBD    = infoPuntoBD.split(",")[2];
        
        for (var j = 0; j < listaPuntosGUI.length; j++) {
            var infoPunto = listaPuntosGUI[j].value;
            var idPunto = infoPunto.split(",")[0];
            var nom = infoPunto.split(",")[2];
            var lat = infoPunto.split(",")[3];
            var lon = infoPunto.split(",")[4];
            
            if (idRuta == idRutaBD && idPunto == idPuntoBD) {
                if (estadoBD == "1") {
                    var option = document.createElement("option");
                    var text   = document.createTextNode(nom);
                    option.appendChild(text);
                    option.setAttribute("value", infoPunto);
                    
                    listaPuntosRuta.appendChild(option);
                    
                    addNewMarkExternMult(nom, lat, lon);
                    break;
                }
            }
        }
    }
    
    // Agregamos bases a la ruta
    marcarBases(idRuta);
}

/*
 * Carga bases de ruta en componente select y las visualiza
 * en mapa segun ruta seleccionada. 
 */
function marcarBases (idRuta) {
    var listaPuntosRuta = document.getElementById("listaPuntosRuta");
    var listaBasesBD    = document.getElementById("listaBasesInit");
    var n = 0;
    
    // Formato entrada: idRuta, tipo, idBase, nom, lat, lon
    for (var i = 0; i < listaBasesBD.options.length; i++) {
        if (n == 2) break;  // Extrictamente dos bases
        var infoBase = listaBasesBD.options[i].value;
        var ruta     = infoBase.split(",")[0];
        
        if (ruta == idRuta) {
            var tipo    = infoBase.split(",")[1];
            var base    = infoBase.split(",")[2];
            var nom     = infoBase.split(",")[3];
            var lat     = infoBase.split(",")[4];
            var lon     = infoBase.split(",")[5];
            var value   = base +","+ nom +","+ lat +","+ lon;
            var text    = null; 
            
            var option  = document.createElement("option");           
            
            if (tipo == "1") { n += 1;
                value = "BS," + value;
                text  = document.createTextNode("[BS] " + nom);
                option.appendChild(text);
                option.setAttribute("value", value);                
                listaPuntosRuta.add(option, listaPuntosRuta[0]);
                addNewMarkExternMult(nom, lat, lon);
            } else { n += 1;
                value = "BL," + value;
                text  = document.createTextNode("[BL] " + nom);
                option.appendChild(text);
                option.setAttribute("value", value);                
                listaPuntosRuta.appendChild(option);                
                addNewMarkExternMult(nom, lat, lon);
            }
        }
    }
    disableDrag();
}

/*
 * Extrae puntos elegidos para una ruta.
 * Crea y retorna lista de puntos como cadena de texto formateada.
 */
function extractPuntosRuta2 () {
    
    var puntosRuta = document.getElementById("listaPuntosRuta").options;
    var puntos = "";
    
    // puntos := &* idPunto, codPunto, nombre, lat, lon
    //        := &* BS|BL, idBase, nombre, lat, lon
    
    var size = puntosRuta.length;
    var bs, bl, npuntos; bs = bl = npuntos = 0;
    
    // Se verifica que exista solo una base salida,
    // una base llegada y al menos un punto.
    for (var i = 0; i < puntosRuta.length; i++) {
        var first = puntosRuta[i].value.split(",")[0];
        if (i == 0 && first == "BS") bs += 1;
        if ((i == size-1) && first == "BL") bl += 1;
        npuntos += 1;
    }
    
    if (bs != 1 || bl != 1 || npuntos < 1)
        return false;
    
    for (var i = 0; i < puntosRuta.length; i++) {        
        var punto = puntosRuta[i].value; 
        if (puntos == "") {
            puntos = punto;
        } else {
            puntos += "&" + punto;
        }        
    }    
    return puntos;
}

/*
 * Envia lista de puntos de una ruta para almacenar.
 */
function sendPuntosRuta2 () {
    var puntos = extractPuntosRuta2();        
    var ruta   = document.getElementById("sruta").value;
    var msg    = document.getElementById("msg");    
    
    if (ruta == "") {
        msg.innerHTML = "* Especifique una ruta.";
        msg.setAttribute("class", "form-msg alert alert-info");
        return;
    }
    
    if (puntos != false) {
        document.getElementById("ruta").value = ruta;
        document.getElementById("puntos").value = puntos;
        document.getElementById("form-nueva-ruta").submit();
    } else { 
        msg.innerHTML = "* Especificaci&oacute;n de puntos es incorrecta. Verifique la existencia y orden de las bases.";
        msg.setAttribute("class", "form-msg alert alert-info");
    }
}

/*
 * Verifica la existencia de bases en lista de puntos.
 */
function existBases () {
    var listaPuntosRuta = document.getElementById("listaPuntosRuta");
    
    var size = listaPuntosRuta.options.length;
    
    var baseSalidaOpt  = listaPuntosRuta.options[0].value;
    var baseLlegadaOpt = listaPuntosRuta.options[size-1].value;

    if (baseSalidaOpt.split(",")[0]  == "BS" ||
        baseLlegadaOpt.split(",")[0] == "BL") {
        return true;
    }
    return false;    
}

/*
 * Agrega bases a lista de puntos.
 */
function addBases () {
    var baseSalida  = document.getElementById("sbasesalida").value;
    var baseLlegada = document.getElementById("sbasellegada").value;
    var listaPuntosRuta = document.getElementById("listaPuntosRuta");
    var msg = document.getElementById("msg");
    
    var nombreSalida  = baseSalida.split(",")[1];
    var nombreLlegada = baseLlegada.split(",")[1];
    
    // Formato entrada: idBase, nom, lat, lon
    // Formato salida:  BS|BL, idBase, nom, lat, lon
    var size = listaPuntosRuta.options.length;
    if (size > 0) {
        if (!existBases()) {
            var baseSalidaOpt = document.createElement("option");
            var baseLlegadaOpt = document.createElement("option");
            var textBS = document.createTextNode("[BS] " + nombreSalida);
            var textBL = document.createTextNode("[BL] " + nombreLlegada);
            baseSalidaOpt.appendChild(textBS);            
            baseLlegadaOpt.appendChild(textBL);
            baseSalidaOpt.setAttribute("value",  "BS," + baseSalida);
            baseLlegadaOpt.setAttribute("value", "BL," + baseLlegada);            
            listaPuntosRuta.add(baseSalidaOpt, listaPuntosRuta[0]);     // Agregar al inicio            
            listaPuntosRuta.appendChild(baseLlegadaOpt);                // Agregar al final
            
            // desde entrada
            var nomBS = baseSalida.split(",")[1];
            var latBS = baseSalida.split(",")[2];
            var lonBS = baseSalida.split(",")[3];
            var nomBL = baseLlegada.split(",")[1];
            var latBL = baseLlegada.split(",")[2];
            var lonBL = baseLlegada.split(",")[3];
            
            addNewMarkExternMult(nomBS, latBS, lonBS);
            addNewMarkExternMult(nomBL, latBL, lonBL);
            
            msg.innerHTML = ""; msg.setAttribute("class", "");
        } else {            
            msg.innerHTML = "* Bases ya existen.";
            msg.setAttribute("class", "form-msg alert alert-info");            
        }
    } else {
        msg.innerHTML = "* Debe elegir m&iacute;nimo un punto primero.";
        msg.setAttribute("class", "form-msg alert alert-info");
    }
    
    disableDrag();
    
    $('#myModal').modal('hide');
    return;
}
