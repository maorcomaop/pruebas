var map;
var popup = L.popup();

// Obtiene ubicacion de empresa
function obtenerUbicacionEmpresa() {

    var lat_empresa = document.getElementById("lat_empresa");
    var lon_empresa = document.getElementById("lon_empresa");
    var ubicacion_empresa = new Object();

    if (lat_empresa != null && lat_empresa.value != null && lat_empresa.value != "" &&
            lon_empresa != null && lon_empresa.value != null && lon_empresa.value != "") {
        ubicacion_empresa.latitud = lat_empresa.value;
        ubicacion_empresa.longitud = lon_empresa.value;
        ubicacion_empresa.zoom = 12;
    } else {
        ubicacion_empresa.latitud = 4.083452772038619;
        ubicacion_empresa.longitud = -73.12500000000001;
        ubicacion_empresa.zoom = 4;
    }
    return ubicacion_empresa;
}

// Inicia mapa con ubicacion de empresa
function iniciarMapa() {

    var ubicacion_empresa = obtenerUbicacionEmpresa();

    if (document.getElementById("map") != null) {

        map = new L.Map('map');
        L.tileLayer(
                'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
                {
                    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
                    maxZoom: 18
                }).addTo(map);

        map.setView(new L.LatLng(ubicacion_empresa.latitud,
                ubicacion_empresa.longitud),
                ubicacion_empresa.zoom);

    }
    // Agregamos marcas pre-establecidas
    //addInitPath();
}

// Crea cadena de texto con informacion de punto (nombre y posicion)
function getDataPoint(nom, lat, lon) {
    var data = "Nombre: " + nom + "<br>";
    data += "Lat: " + lat + "<br>";
    data += "Lon: " + lon;
    return data;
}

// Funcion que agrega marca al mapa
// desde fuente externa diferente a un click
function addNewMarkExtern(nom, lat, lon) {

    // Obtiene informacion de punto
    var data = getDataPoint(nom, lat, lon);

    // Verifica si coordenadas no existen
    if (!existNewMarkExtern(lat, lon)) {
        var newLatLng = new L.LatLng(lat, lon);
        var marker = new L.Marker(newLatLng, {draggable: 'false'})
                .bindPopup(data);

        map.addLayer(marker);
        map.setView(new L.LatLng(lat, lon), 10);
    } else {
        removeNewMarkExtern(lat, lon);
    }
}

// Funcion que agrega marca al mapa unicamente si no existe
// (Se asegura que exista una marca por posicion)
function addNewMarkExternUnique(nom, lat, lon) {

    // Obtiene informacion de punto
    var data = getDataPoint(nom, lat, lon);

    // Verifica si coordenadas no existen
    if (!existNewMarkExtern(lat, lon)) {
        var newLatLng = new L.LatLng(lat, lon);
        var marker = new L.Marker(newLatLng, {draggable: 'false'})
                .bindPopup(data);

        map.addLayer(marker);
        map.setView(new L.LatLng(lat, lon), 10);
    }
}

// Funcion que agrega marca al mapa sin importar existencia
function addNewMarkExternMult(nom, lat, lon) {

    // Obtiene informacion de punto
    var data = getDataPoint(nom, lat, lon);

    var newLatLng = new L.LatLng(lat, lon);
    var marker = new L.Marker(newLatLng, {icon: defaultIcon}, {draggable: 'false'})
            .bindPopup(data);

    map.addLayer(marker);
    map.setView(new L.LatLng(lat, lon), 10);
}

var greenIcon = L.divIcon({
    className: 'green-mark',
    iconSize: [26, 41],
    iconAnchor: [13, 41]
});

var defaultIcon = L.divIcon({
    className: 'blue-mark',
    iconSize: [26, 41],
    iconAnchor: [13, 41]
});

// Agrega y resalta marca en el mapa
function addAndSignalMark(nom, lat, lon) {

    // Obtiene informacion de punto
    var data = getDataPoint(nom, lat, lon);

    var newLatLng = new L.LatLng(lat, lon);
    var marker = new L.Marker(newLatLng, {icon: greenIcon}, {draggable: 'false'})
            .bindPopup(data);

    map.addLayer(marker);
    map.setView(new L.LatLng(lat, lon), 15);
}

// Funcion que remueve marca del mapa
// con posicion (lat, lon) especificada
function removeNewMarkExtern(latExt, lonExt) {
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            var latlng = layer.getLatLng();
            var lat = latlng.lat;
            var lon = latlng.lng;
            if (lat == latExt && lon == lonExt) {
                map.removeLayer(layer);
            }
        }
    });
}

// Funcion que verifica si existe una marca 
// con posicion (lat, lon) en el mapa
function existNewMarkExtern(latExt, lonExt) {
    var rs = false;
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            var latlng = layer.getLatLng();
            var lat = latlng.lat;
            var lon = latlng.lng;
            if (lat == latExt && lon == lonExt) {
                rs = true;
            }
        }
    });
    return rs;
}

// Funcion que inhabilita el arrastre en las marcas 
// creadas en el mapa
function disableDrag() {

    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            layer.dragging.disable();
        }
    });
}

// Funcion que remueve marca y componentes asociados
// desde accionador propio
function onPopupOpen() {
    var tmpMarker = this;
    $(".eliminar-marker:visible").click(function () {
        map.removeLayer(tmpMarker);
        document.getElementById("latitud").value = "";
        document.getElementById("longitud").value = "";
        document.getElementById("dirLatitud").value = "";
        document.getElementById("dirLongitud").value = "";
    });
}

// Funcion que elimina todas las marcas existentes
function removePath() {
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });
}

// Funcion que consulta si existe al menos un punto en el mapa
function existPoint() {
    var exist = false;
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            exist = true;
        }
    });
    return exist;
}

// Resalta y enfoca marca en mapa con posicion (lat, lng)
function updateIcon(lat, lng) {

    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            var latlng = layer.getLatLng();
            var lat_ext = latlng.lat;
            var lng_ext = latlng.lng;
            if (lat_ext == lat &&
                lng_ext == lng) {
                layer.setIcon(greenIcon);
            } else {
                layer.setIcon(defaultIcon);
            }
        }
    });
    viewMapPoint(lat, lng);
}

// Funcion que enfoca marca con posicion (lat, lon) en el mapa
function viewMapPoint(lat, lon) {
    map.setView(new L.LatLng(lat, lon), 16);
}

// Resalta y enfoca marca con posicion (lat, lon)
function lightPoint(lat, lon) {
    updateIcon(lat, lon);
}