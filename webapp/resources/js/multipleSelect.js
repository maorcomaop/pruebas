
// Verifica si opcion 'text' existe en componente 'select'
function existOption (text, select) {
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].text == text)
            return true;
    }
    return false;
}

// Verifica si una y solo una opcion fue seleccionada
function oneOptionSelected (select) {
    var n = 0;
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].selected)
            n += 1;
    }
	
    if (n > 1) return false;
    return true;
}

// Obtiene posicion de opcion seleccionada
function getPositionOption (select) {
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].selected)
            return i;
    }
}

// Transfiere opciones de componente 'select' a 'select_destiny' que
// han sido seleccionados. Actualiza marcas del mapa
function toRight() {
    var select = document.getElementById("listaPuntos");                // leftValues
    var select_destiny = document.getElementById("listaPuntosRuta");    // rightValues
	
    if (select != null) {
		
        for (var i = 0; i < select.options.length; i++) {
            if (select.options[i].selected) {
                var stext  = select.options[i].text;
                var svalue = select.options[i].value;
				
                var option = document.createElement("option");
                var text   = document.createTextNode(stext);				
                option.appendChild(text);
                option.setAttribute("value", svalue);

                //if (!existOption(stext, select_destiny))
                    select_destiny.appendChild(option);
            }
        } reviewMap();
    }
}

// Elimina opciones de componente destino 'select' que han sido seleccionados
function toRemove() {
    var select = document.getElementById("listaPuntosRuta");    // rightValues
    var lastSelected = null;
    
    if (select != null) {

        for (var i = select.options.length - 1; i >= 0; i--) {
            if (select.options[i].selected) {
                select.remove(i);
                lastSelected = i;
                break;
            }
        }       
        
        if (lastSelected != null &&
            select.options[lastSelected-1] != undefined) {
            select.options[lastSelected-1].selected = true;
        }
        
        reviewMap();
    }
}

// Transfiere todas las opciones de componente 'select' a 'select_destiny'.
// Actualiza marcas del mapa
function toRightAll() {
    var select = document.getElementById("listaPuntos");
    var select_destiny = document.getElementById("listaPuntosRuta");
    
    for (var i = 0; i < select.options.length; i++) {
        var infoPunto = select.options[i].value;
        var nom = infoPunto.split(",")[2];
                
        var option = document.createElement("option");
        var text   = document.createTextNode(nom);
        option.appendChild(text);
        option.setAttribute("value", infoPunto);
        
        select_destiny.appendChild(option);
    }
    reviewMap();
}

// Remueve todas las opciones del componente destino 'select'.
// Actualiza marcas del mapa
function removeAll() {
    var select = document.getElementById("listaPuntosRuta");
    
    for (var i = select.options.length - 1; i >= 0; i--) {
        select.remove(i);
    }
    reviewMap();
}

// Intercambia opcion seleccionada con la opcion inmediatamente superior
function up() {
    var select = document.getElementById("listaPuntosRuta");    // rightValues
    var min	= 0;
    var max = select.options.length-1;

    if (oneOptionSelected(select)) {
		
        pos = getPositionOption(select);				
		
        if (pos > min) {
            var myOption = select.options[pos];
            var text  = select.options[pos-1].text
            var value = select.options[pos-1].value;
			
            select.options[pos-1].text  = myOption.text;
            select.options[pos-1].value = myOption.value;
            select.options[pos].text    = text;
            select.options[pos].value   = value;
            select.options[pos-1].selected  = true;
            select.options[pos].selected    = false;
        }	
    }
}

// Intercambia opcion seleccionada con la opcion inmediatamente inferior
function down() {
    var select = document.getElementById("listaPuntosRuta");    // rightValues
    var min	= 0;
    var max = select.options.length-1;

    if (oneOptionSelected(select)) {
		
        pos = getPositionOption(select);

        if (pos < max) {
            var myOption = select.options[pos];
            var text  = select.options[pos+1].text;
            var value = select.options[pos+1].value;
			
            select.options[pos+1].text  = myOption.text;
            select.options[pos+1].value = myOption.value;
            select.options[pos].text  = text;
            select.options[pos].value = value;
            select.options[pos+1].selected  = true;
            select.options[pos].selected    = false;
        }
    }		
}

// Obtiene puntos y los agrega como marcas al mapa.
// Elimina las marcas establecidas
function reviewMap() {
    removePath();
    var select = document.getElementById("listaPuntosRuta");
    
    for (var i = 0; i < select.options.length; i++) {
        var infoPunto = select.options[i].value;
        var nom = infoPunto.split(",")[2];
        var lat = infoPunto.split(",")[3];
        var lon = infoPunto.split(",")[4];
        
        addNewMarkExternMult(nom, lat, lon);
    }        
    disableDrag();
}

// Visualiza un punto seleccionado
function viewPoint () {
    var infoPunto = document.getElementById("listaPuntosRuta").value;    
    var nom = infoPunto.split(",")[2];
    var lat = infoPunto.split(",")[3];
    var lon = infoPunto.split(",")[4];
    lightPoint(lat, lon);
}

/*
function info () {
    var select = document.getElementById("listaPuntosRuta");
    var s = "";

    for (var i = 0; i < select.options.length; i++) {
            s += select.options[i].text +" "+ select.options[i].value + "\n";
    }
    alert (s);
} */