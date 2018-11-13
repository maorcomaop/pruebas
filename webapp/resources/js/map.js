var map;
var popup = L.popup();
var onlyOnePoint = true;

// Obtiene ubicacion de empresa
function obtenerUbicacionEmpresa() {
    
    var lat_empresa = document.getElementById("lat_empresa");
    var lon_empresa = document.getElementById("lon_empresa");
    var ubicacion_empresa = new Object();
    
    if (lat_empresa != null && lat_empresa.value != null && lat_empresa.value != "" &&
        lon_empresa != null && lon_empresa.value != null && lon_empresa.value != "") {
        ubicacion_empresa.latitud  = lat_empresa.value;
        ubicacion_empresa.longitud = lon_empresa.value;
        ubicacion_empresa.zoom = 8;
    } else {
        ubicacion_empresa.latitud  = 4.083452772038619;
        ubicacion_empresa.longitud = -73.12500000000001;
        ubicacion_empresa.zoom = 6;
    }   
    return ubicacion_empresa;
}

// Inicia mapa normal con ubicacion de empresa
function iniciarMapa () {    
        
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

        if (onlyOnePoint) {
            map.on('click', addNewMark);
        } else {
            map.on('click', addManyNewMark);
        }
    }
    
    // Agregamos marcas pre-establecidas
    // addInitPath();
    // Agregamos marcas recientes
    addCurrentMarks();
}

// Inicia mapa con ubicacion de empresa sin listeners
function iniciarMapaSoloLectura () {
    
    var ubicacion_empresa = obtenerUbicacionEmpresa();
    
    if (document.getElementById("map") != null) {
        
        map = new L.map('map');
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
}

// Funcion listener que crea una nueva marca
// dando click en el mapa
function addNewMark (e) {
    
    // Permitir solo una marca a la vez
    var existOne = false; var n = 0;
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            existOne = true;
            n += 1;
        }
    });
    
    // Verifica si existen puntos recientes y si hubo
    // creacion de punto nuevo (puntos totales > puntos recientes)
    var ncurrentMarks = sizeCurrentMarks();
    existOne = (n > ncurrentMarks || (n >= 1 && EN_MODO_EDICION)) ? true : false;
    
    // Si no existe punto nuevo creado en mapa se crea
    if (!existOne) {
        var marker = new L.Marker(e.latlng, {draggable:'true'})
                .bindPopup("<input type='button' value='Eliminar marca' class='eliminar-marker' />");
        
        var Lat = e.latlng.lat;
        var Lon = e.latlng.lng;
        
        document.getElementById("latitud").value = Lat;
        document.getElementById("longitud").value = Lon;
        
        // Asigna direccion de coordenada segun valores de latitud & longitud
        setDirCoord(Lat, Lon);
        
        marker.on('popupopen', onPopupOpen);
        marker.on('dragend', onDragEvent);
        map.addLayer(marker);
    }
}

// Funcion listener que posibilita la creacion
// de multiples marcas en el mapa
function addManyNewMark (e) {
    var marker = new L.Marker(e.latlng, {draggable:'true'})
                .bindPopup("<input type='button' value='Eliminar marca' class='eliminar-marker' />");
        
    // Limita creacion a un maximo de 512 puntos
    if (sizePath() >= 512) return;
        
    var Lat = e.latlng.lat;
    var Lon = e.latlng.lng;

    document.getElementById("latitud").value = Lat;
    document.getElementById("longitud").value = Lon;

    // Asigna direccion de coordenada segun valores de latitud & longitud
    setDirCoord(Lat, Lon);

    marker.on('popupopen', onPopupOpen);
    marker.on('dragend', onDragEvent);
    marker.on('click', onClickEvent);
    map.addLayer(marker);
}

// Agrega marca con informacion-contextual al mapa
function addMark(lat, lon, info) {
    
    // Se remueve marcas existentes
    removePath();
    
    var newLatLng = new L.LatLng(lat, lon);
    var marker = new L.Marker(newLatLng)
            .bindPopup(info);    
    map.addLayer(marker);
    map.setView(newLatLng, 16);
    marker.openPopup();
}

// Funcion que agrega marca al mapa
// desde fuente externa diferente a un click
function addNewMarkExtern (lat, lon) {
    
    // Se remueve marcas existentes            
    removePath();
    
    var newLatLng = new L.LatLng(lat, lon);
    var marker = new L.Marker(newLatLng, {draggable:'true'})
            .bindPopup("<input type='button' value='Eliminar marca' class='eliminar-marker' />");
        
    marker.on('popupopen', onPopupOpen);
    marker.on('dragend', onDragEvent);    
    map.addLayer(marker);
    
    map.setView(new L.LatLng(lat, lon), 17);
}

var greyIcon = L.divIcon({
    className: 'grey-mark',
    iconSize: [26, 41],
    iconAnchor: [13, 41]
});

// Adiciona marcas al mapa recientemente creadas, listadas
// en componente select
function addCurrentMarks () {
    
    // Se remueve marcas existentes
    removePath();
    
    // Obtiene listado de objetos point (coordenadas)
    var lst_pto = select2list('lst_pto_rte', '|');
        
    var newlatlon = null;
    for (var i = 0; i < lst_pto.length; i++) {
        var point  = lst_pto.pop();
        newlatlon  = new L.LatLng(point.latitud, point.longitud);        
        var marker = new L.Marker(newlatlon, {icon: greyIcon}, {draggable:'true'})
                .bindPopup(point.info);
        marker.on('popupopen', onPopupOpen);
        marker.on('dragend', onDragEvent);    
        map.addLayer(marker);
    }
    
    if (newlatlon != null) {
        map.setView(newlatlon, 8);
    }
}

// Extrae valores y crea listado de objetos point
// para puntos recientemente creados.
function select2list(id_select, separator) {
    var select  = document.getElementById(id_select);
    var options = select.getElementsByTagName("option");
    var lst = new Array(options.length);
    for (var i = 0; i < options.length; i++) {
        var val = options[i].value;
        lst.push(str2point(val, separator));
    }
    return lst;
}

// Cadena de texto de punto reciente : id | codigo | nombre | latitud | longitud | tipo
// Extrae y crea objeto point a partir de cadena de texto formateada del punto.
function str2point(val, separator) {
    var point = new Object();
    var array = val.split(separator);    
    point.id        = array[0];
    point.codigo    = array[1];
    point.nombre    = array[2];
    point.latitud   = array[3];
    point.longitud  = array[4];
    point.tipo      = array[5];
    point.info      = popUpPoint(point);
    return point;
}

// Declara codigo html para ventana flotante con informacion del punto especificado
function popUpPoint(point) {    
    var tipo = "'" + point.tipo + "'";
    var str = 
            "<table>"
                + "<tr> <td><b>Nombre:</b></td>    <td>"+point.nombre+"    </td> </tr>"
                + "<tr> <td><b>Tipo:</b></td>      <td>"+point.tipo+"      </td> </tr>"
                + "<tr> <td><b>Latitud:</b></td>   <td>"+point.latitud+"   </td> </tr>"
                + "<tr> <td><b>Longitud:</b></td>  <td>"+point.longitud+"  </td> </tr>"
            + "</table>"
            + "<input type=\"button\" value=\"Editar\" onclick=\"editarPunto("+ point.id +","+ tipo +");\"/>";
    return str;
}

// Obtiene cantidad de puntos recientemente creados
function sizeCurrentMarks() {
    var select  = document.getElementById("lst_pto_rte");
    var size    = select.options.length;
    return size;
}

// Funcion que agrega marca (sin listeners) al mapa
function addNewSimpleMark (lat, lon) {
    var newLatLng = new L.LatLng(lat, lon);
    var marker = new L.Marker(newLatLng, {draggable:'false'});
    
    // Asigna direccion de coordenada
    setDirCoord(lat, lon);    
    marker.on('click', onClickEvent);    
    map.addLayer(marker);
    
    map.setView(new L.LatLng(lat, lon), 12);
}

// Funcion que actualiza informacion de componentes de texto
// segun la marca seleccionada
function onClickEvent () {
    var latlon = this.getLatLng();
    document.getElementById("latitud").value = latlon.lat;
    document.getElementById("longitud").value = latlon.lng;
    
    // Asigna direccion de coordenada
    setDirCoord(latlon.lat, latlon.lng);
}

// Funcion que actualiza coordenadas tras cambiar la posicion
// de la marca
function onDragEvent () {
    var latlon = this.getLatLng();
    document.getElementById("latitud").value = latlon.lat;
    document.getElementById("longitud").value = latlon.lng;
    
    // Asigna direccion de coordenada
    setDirCoord(latlon.lat, latlon.lng);
}

// Funcion que establece direccion de latitud N,S y longitud E,O segun 
// sus valores decimales
function setDirCoord (Lat, Lon) {
    
    var dirLat = "";
    var dirLon = "";
    
    dirLat = (Lat > 0) ? "Norte" : "Sur";
    dirLon = (Lon > 0) ? "Este"  : "Oeste";
    
    document.getElementById("dirLatitud").value  = dirLat;
    document.getElementById("dirLongitud").value = dirLon;    
    var dirLatLon = document.getElementById("dirLatLon");
    
    if (dirLatLon != null)
        dirLatLon.value = dirLat +"/"+ dirLon;
}

// Funcion que remueve una marca e informacion
// en componentes asociados
function onPopupOpen () {
    var tmpMarker = this;
    $(".eliminar-marker:visible").click(function () {
            map.removeLayer(tmpMarker);
            document.getElementById("latitud").value = "";
            document.getElementById("longitud").value = "";
            document.getElementById("dirLatitud").value = "";
            document.getElementById("dirLongitud").value = "";
            
            var dirLatLon = document.getElementById("dirLatLon");
            if (dirLatLon != null)            
                dirLatLon.value = "";
    });
}

// Funcion que recorre todas las marcas existentes
//function addPath () {
//    map.eachLayer (function (layer) {
//        if (layer instanceof L.Marker) {
//            var latlng = layer.getLatLng();
//        }
//    });
//}

// Funcion que retorna cantidad de marcas existentes
function sizePath() {
    var n = 0;
    map.eachLayer (function (layer) {
        if (layer instanceof L.Marker) {
            n += 1;
        }
    });
    return n;
}

// Funcion que elimina todas las marcas existentes
function removePath () {
    map.eachLayer (function (layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });
}

// Agrega marcas ejemplo al mapa
function addInitPath () {

    var markers = [
            [3.453729015803198, -76.51140689849855,  "<input type='button' value='Eliminar marca' class='eliminar-marker' />"],
            [3.452379636734942, -76.51048421859741,  "<input type='button' value='Eliminar marca' class='eliminar-marker' />"],
            [3.4536433409987346, -76.50971174240114, "<input type='button' value='Eliminar marca' class='eliminar-marker' />"]
    ];

    for (var i = 0; i < markers.length; i++) {
            var lon = markers[i][0];
            var lat = markers[i][1];
            var popupText = markers[i][2];

            var location = new L.LatLng(lon, lat);
            var marker = new L.Marker(location, {draggable:'true'});
            marker.bindPopup(popupText);
            marker.on('popupopen', onPopupOpen);

            map.addLayer(marker);
    }
}

// Funcion que exporta a GeoJSON
function exportGeoJSON () {
    // Se crea coleccion de GeoJSON vacia
    var collection = {
            "type": "FeatureCollection",
            "features": []
    };
    // Se itera por cada marca
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            // Se crea objeto GeoJSON a partir de marca 
            // y se agrega en coleccion
            var geojson = layer.toGeoJSON();
            collection.features.push(geojson);
        }
    });
    
    return collection;
}

// Funcion que exporta puntos en el mapa
// a archivo JSON
function exportToJsonFile () {
    
    if (!existPoint()) {
        document.getElementById("msg").innerHTML = "* Elija al menos un punto en el mapa.";
        document.getElementById("msg").setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    
    var str = JSON.stringify(exportGeoJSON());
    var dataURI = 'data:application/json;charset=utf-8,'+ encodeURIComponent(str);
    var link = document.getElementById("downloadLink").href = dataURI;

    //var fileName = "data.json";
    // Creamos enlace virtual para iniciar descarga
    //var downloadLink = document.createElement('a');
    //downloadLink.setAttribute('id', "downloadLink");
    //downloadLink.setAttribute('href', dataURI);
    //downloadLink.setAttribute('download', fileName);
    //downloadLink.click();
    //$("#downloadLink").click();
}

// Funcion que exporta puntos en el mapa
// a archivo KML
function exportToKMLFile () {
    
    if (!existPoint()) {
        document.getElementById("msg").innerHTML = "* Elija al menos un punto en el mapa.";
        document.getElementById("msg").setAttribute("class", "form-msg bottom-space alert alert-info");
        return;
    }
    
    //var str = JSON.stringify(exportGeoJSON());
    var json  = exportGeoJSON();
    var kml = tokml(json);
    var dataURI = "data:application/vnd.google-earth.kml+xml;charset=utf-8," + encodeURIComponent(kml);    
    //window.open(dataURI, 'Exportar a KML');
    var link = document.getElementById("downloadLinkKML").href = dataURI;
}

// Funcion encargada de iniciar el proceso de
// subida de archivo de puntos en formato JSON
$(document).ready(function () {
    $('#uploadLink').click(function () {
        var file = document.getElementById('archivoPuntos').files[0];
        if (file) {
            // Se lee archivo con marcas en formato JSON
            var reader = new FileReader();
            reader.readAsText(file);
            reader.onload = function(e) {
                
                var data = e.target.result;
                var geojson = JSON.parse(data);

                addDataToMap(geojson);
                
                // Se elminina rastro de puntosKML
                document.getElementById("puntosKML").value = "";
            };
        }
    });
});

// Funcion que agrega marcas al mapa traidas desde archivo JSON
function addDataToMap (data) {

    removePath();
    var popupText = "<input type='button' value='Eliminar marca' class='eliminar-marker' />";
    var dataLayer = L.geoJSON(data);
    dataLayer.addTo(map);

    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            layer.dragging.enable();
            layer.bindPopup(popupText);
            layer.on('popupopen', onPopupOpen);
        }
    });
    
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            var latlng = layer.getLatLng();
            map.setView(latlng, 12);
            return;
        }
    });
}

// Funcion listener para evento 'click'
function onMapClick (e) {
    popup
        .setLatLng(e.latlng)
        .setContent("Diste click en Lat: " + e.latlng.lat +
                                 " Long: " + e.latlng.lng)
        .openOn(map);
}

// Funcion que agrupa los puntos ubicados en el mapa en una
// cadena de texto formateada. &* cod,lat,lon,dirlat,dirlon + ,lat,lng
function getListaPuntos (cod) {
    
    var listaPuntos = ""; var s = ",";
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            var latlng = layer.getLatLng();
            var lat    = latlng.lat;
            var lng    = latlng.lng;
            var dirLat = (lat > 0) ? "'Norte'" : "'Sur'";
            var dirLng = (lng > 0) ? "'Este'"  : "'Oeste'";            
            
            var data =  cod     + s +
                        lat     + s +         
                        lng     + s +
                        dirLat  + s +
                        dirLng  + s +
                        lat     + s +
                        lng;
            
            if (listaPuntos == "") {
                listaPuntos = data;
            } else {
                listaPuntos += "&" + data;
            }            
            cod = parseInt(cod) + 1;
        }
    });
    
    return listaPuntos;
}

// Funcion que verifica si existe al menos un punto en el mapa
function existPoint() {
    var exist = false;
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            exist = true;
        }
    });
    return exist;
}

// Funcion que visualiza un punto en el mapa
function viewMapPoint (lat, lon) {
    map.setView(new L.LatLng(lat, lon), 16);    
}