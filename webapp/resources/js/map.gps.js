var map;
var google_ROADMAP;
var google_SATELLITE;
var google_HYBRID;
var google_TERRAIN;
var openStret;
var popup = L.popup();

var size_icon = [26, 41];
var anchor_icon = [13, 41];

var size_arrow_icon = [26, 26];
var anchor_arrow_icon = [13, 8];

// Iconos navegacion
var norte_icon = L.divIcon({
    className: 'mark-norte',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var sur_icon = L.divIcon({
    className: 'mark-sur',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var oriente_icon = L.divIcon({
    className: 'mark-oriente',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var occidente_icon = L.divIcon({
    className: 'mark-occidente',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var nororiente_icon = L.divIcon({
    className: 'mark-nor-oriente',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var noroccidente_icon = L.divIcon({
    className: 'mark-nor-occidente',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var suroriente_icon = L.divIcon({
    className: 'mark-sur-oriente',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});
var suroccidente_icon = L.divIcon({
    className: 'mark-sur-occidente',
    iconSize: size_arrow_icon,
    iconAnchor: anchor_arrow_icon
});

// Iconos de eventos
var pasajero_icon = L.divIcon({
    className: 'mark-pasajero',
    iconSize: [28, 28],
    iconAnchor: [14, 28]
});
var alarma_icon = L.divIcon({
    className: 'mark-alarma',
    iconSize: [26, 26],
    iconAnchor: [13, 26]
});
var ultimo_reporte_icon = L.divIcon({
    className: 'green-mark', //'orange-mark',
    iconSize: size_icon,
    iconAnchor: anchor_icon
});
var primer_reporte_icon = L.divIcon({
    className: 'yellow-mark',
    iconSize: size_icon,
    iconAnchor: anchor_icon
});
var punto_control_icon = L.divIcon({
    className: 'mark-punto-a-tiempo',
    iconSize: [30, 30],
    iconAnchor: [15, 30]
});
var otros_icon = L.divIcon({
    className: 'blue-mark',
    iconSize: size_icon,
    iconAnchor: anchor_icon
});
var grey_icon = L.divIcon({
    className: 'grey-mark',
    iconSize: size_icon,
    iconAnchor: anchor_icon
});
var black_icon = L.divIcon({
    className: 'black-mark',
    iconSize: size_icon,
    iconAnchor: anchor_icon
});
var iconGeoIn = L.icon({
    iconUrl: '../../resources/icons/track/geo-in.svg',
    iconSize: [30, 30],
    iconAnchor: [15, 30]
});
var iconGeoOut = L.icon({
    iconUrl: '../../resources/icons/track/geo-out.svg',
    iconSize: [30, 30],
    iconAnchor: [15, 30]
});

// Obtiene ubicacion de empresa
function obtenerUbicacionEmpresa() {

    var lat_empresa = document.getElementById("lat_empresa");
    var lon_empresa = document.getElementById("lon_empresa");
    var ubicacion_empresa = new Object();

    if (lat_empresa != null && lat_empresa.value != null && lat_empresa.value != "" &&
            lon_empresa != null && lon_empresa.value != null && lon_empresa.value != "") {
        ubicacion_empresa.latitud = lat_empresa.value;
        ubicacion_empresa.longitud = lon_empresa.value;
        ubicacion_empresa.zoom = 8;
    } else {
        ubicacion_empresa.latitud = 4.083452772038619;
        ubicacion_empresa.longitud = -73.12500000000001;
        ubicacion_empresa.zoom = 6;
    }
    return ubicacion_empresa;
}

// Agrega evento para ubicacion de punto mas reciente en el mapa
function iniciarMapaConLocacion() {
    iniciarMapa();
    L.easyButton('<span>&target;</span>', focusFirstMark).addTo(map);
}

// Inicia mapa con ubicacion de la empresa
function iniciarMapa() {

    var ubicacion_empresa = obtenerUbicacionEmpresa();

    if (document.getElementById("map") != null) {

        google_ROADMAP = new L.Google('ROADMAP');
        google_SATELLITE = new L.Google('SATELLITE');
        google_HYBRID = new L.Google('HYBRID');
        google_TERRAIN = new L.Google('TERRAIN');

        map = new L.Map('map');
        openStret = L.tileLayer(
                'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
                {
                    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
                    maxZoom: 18
                });

        map.setView(new L.LatLng(
                ubicacion_empresa.latitud,
                ubicacion_empresa.longitud),
                ubicacion_empresa.zoom);

        map.on('click', disableMapRefresh);
        map.on('zoomend', saveMapCenter);
        map.on('mouseup', saveMapCenter);

        var baseLayers = {
            "vias 1": google_ROADMAP,
            "vias 2": openStret,
            "terreno": google_TERRAIN,
            "satelite": google_SATELLITE,
            "hibrido": google_HYBRID
        };

        L.control.layers(baseLayers).addTo(map);

        map.addLayer(google_ROADMAP);
    }
}

var NUM_LIM_MARKS = 1024;
var INDEX_MARK = 0;
var SIZE_MAP = 0;
var REFRESH_MAP = true;
var INDEX_KEEP_MARK = -1;
var CENTER_MAP = null;
var ZOOM_MAP = null;

var ZOOM_IN_BOUNDS1 = true;
var ZOOM_IN_BOUNDS2 = true;
var USER_FIRST_MARK = false;

// Agrega listado de puntos consultados como marcas al mapa
function addListMarks(lst_gps) {

    // Remueve marcas/puntos existentes
    removeAllMarks();

    // Si es agrupacion y ultimo punto activo
    // if (agrupacion_ultimoPunto(lst_gps)) {
    //      return;
    // }

    if (lst_gps.length > 0) {
        var item;
        var icon;
        var spcRsnTrns = "00000000";
        var splaca = document.getElementById("splaca");

        var size = lst_gps.length;

        // Visualizacion de puntos segun zoom
        // zoom <= 13 -> 40%
        // zoom >= 14 -> 80%
        // zoom >= 15 -> 90%
        // zoom >= 16 -> 100%

        var zoom = 13;
        if (map != null) {
            zoom = map.getZoom();
        }

        var show_size = size;
        var step_size = 1;
        show_size = (zoom >= 16) ? size :
                (zoom >= 15) ? size * 0.90 :
                (zoom >= 14) ? size * 0.70 : size * 0.30;
        show_size = parseInt(show_size);
        step_size = parseInt(size / show_size);

        // Si existe algun filtro especificado
        // Se visualiza totalidad de puntos (100%) a partir de zoom 14
        if (existe_filtro() && zoom >= 14) {
            show_size = size;
            step_size = 1;
        }

        // Si ningun vehiculo ha sido seleccionado
        // se visualiza la totalidad de los registros
        if (splaca.value == "") {
            show_size = size;
            step_size = 1;
        }

        // Si cantidad de registros no supera NUM_LIM_MARKS
        // no se restringe su visualizacion
        if (size <= NUM_LIM_MARKS) {
            show_size = size;
            step_size = 1;
        }

        var s = (size > 2) ? 1 : 0;

        for (var i = s; i < size - s; i++) {
            item = lst_gps[i];

            if (i % step_size != 0)
                continue;

            if (item.esPuntoControl == 1) {
                icon = 'punto_control';
            } else if (item.esPasajero == 1) {
                icon = 'pasajero';
            } else if (item.esAlarma == 1) {
                icon = 'alarma';
            } else if (item.razonTransmision == 191) {
                spcRsnTrns = item.especificaRazonTransmision;
                icon = 'geoOut';
                if (spcRsnTrns.slice(-1) === "1") {
                    icon = 'geoIn';
                }
            } else {
                icon = item.rumbo.toLowerCase();
            }

            item.index = i;
            addNewMark(item, icon, false);
        }

        if (s == 1) {
            // first report
            item = lst_gps[size - 1];
            item.index = size - 1;
            addNewMark(item, 'primer_reporte', false);

            // last report        
            item = lst_gps[0];
            item.index = 0;
            INDEX_MARK = 0;
            addNewMark(item, 'ultimo_reporte', false);
        } else {
            INDEX_MARK = 0;
        }

        // Obtiene cantidad de marcas/puntos agregados al mapa
        SIZE_MAP = sizeMap();
        document.getElementById("currentMark").innerHTML = SIZE_MAP;
        document.getElementById("totalMark").innerHTML = SIZE_MAP;

        // Ajusta zoom segun puntos en mapa
        if (ZOOM_IN_BOUNDS1 ||
                ZOOM_IN_BOUNDS2) {
            zoomInBounds();
        }
        // Ubica y muestra ultimo evento (de cumplirse ciertas condiciones)
        showLastEvent();

        disableDrag();
        resultType("OK");
    }
}

// Ajusta enfoque y zoom de mapa con respecto a punto mas reciente
function keepZoomLastPoint() {

    var mark = lst_mark_gps[0];
    if (mark != null) {
        var latlng = [mark.getLatLng()];
        var markBounds = L.latLngBounds(latlng);
        if (map != null) {
            map.fitBounds(markBounds);
        }
    }
}

// Enfoca punto mas reciente si existe autoconsulta,
// movil especificado e indice seleccionado corresponde a ese punto
function showLastEvent() {

    var indexMark = getIndexMark();
    var movil = document.getElementById("splaca");

    if (autoConsulta && indexMark <= 0 && movil.value != "") {
        if (!REFRESH_MAP)
            REFRESH_MAP = true;
        showFirstMarkMin();
    }
}

// Enfoca primer punto / punto mas reciente y habilita refresco de mapa
function focusFirstMarkAndTrack() {
    focusFirstMark();
    REFRESH_MAP = true;
}

// Enfoca primer punto / punto mas reciente
// Actualiza variables de referencia
function focusFirstMark() {

    var mark = lst_mark_gps[0];
    if (mark != null) {
        mark.openPopup();
        INDEX_MARK = 0;
        INDEX_KEEP_MARK = 0;
        USER_FIRST_MARK = true;
        updateInfoPanel(0);
        updatePosition(0);
    }
}

function clearInteraction() {
    USER_FIRST_MARK = false;
    INDEX_MARK = INDEX_KEEP_MARK = -1;
}

// Deshabilita ajuste de zoom
function disableZoomBounds() {
    ZOOM_IN_BOUNDS1 = false;
    ZOOM_IN_BOUNDS2 = false;
}

// Guarda posicion central actual del mapa
// (ocurre cuando se hace arrestre/zoom en el mapa)
function saveMapCenter() {
    if (map != null) {
        CENTER_MAP = map.getCenter();
        ZOOM_MAP = map.getZoom();
    }
    REFRESH_MAP = false;
    disableZoomBounds();
}

// Inhabilita refresco grafico de mapa tras nueva consulta
// (ocurre cuando se hace click en el mapa)
function disableMapRefresh() {
    REFRESH_MAP = false;
    INDEX_KEEP_MARK = -1;
    USER_FIRST_MARK = false;
    saveMapCenter();
    disableZoomBounds();
}

// Habilita refresco grafico de mapa tras nueva consulta
// (ocurre cuando lanzamos evento consultaGps manual)
function enableMapRefresh() {
    REFRESH_MAP = true;
    INDEX_KEEP_MARK = -1;
}

// Mantiene en vista ultima popup pulsada
// (para esto previamente se ha pulsado sobre una marca)
function indexMark(index_mark) {
    INDEX_KEEP_MARK = index_mark;
}

// Obtiene posicion de ultima marca pulsada
function getIndexMark() {
    return INDEX_KEEP_MARK;
}

// Agrega ultimo punto (mas reciente) de cada vehiculo consultado
function agrupacion_ultimoPunto(lst_gps) {

    // Remueve marcas/puntos existentes
    removeAllMarks();

    var agrupacion = document.getElementById("sgrupo");
    var ultimoPunto = document.getElementById("chk_ultimoPunto");

    if (agrupacion.value != "" && ultimoPunto.checked) {

        if (lst_gps.length > 0) {

            var lst_show = new Array();
            lst_show.push(lst_gps[0]);

            for (var i = 1; i < lst_gps.length; i++) {
                var item = lst_gps[i];

                var exist_item = false;
                for (var j = 0; j < lst_show.length; j++) {
                    var item_show = lst_show[j];
                    if (item.placa == item_show.placa) {
                        exist_item = true;
                        break;
                    }
                }

                if (!exist_item) {
                    lst_show.push(item);
                }
            }

            for (var i = 0; i < lst_show.length; i++) {
                addNewMark(lst_show[i], 'ultimo_reporte', false);
            }

            // Ajusta zoom segun puntos en mapa
            if (ZOOM_IN_BOUNDS1 ||
                    ZOOM_IN_BOUNDS2) {
                zoomInBounds();
            }

            disableDrag();
            resultType("OK");
            return true;
        }
    }
    return false;
}

// Selecciona icono con orientacion para ultimo-evento reportado,
// de no encontrarse orientacion se asigna icono por defecto.
function iconToLastReport(item, icon_sel) {

    var rumbo = item.rumbo.toLowerCase();

    if (rumbo == 'norte' ||
            rumbo == 'sur' ||
            rumbo == 'oriente' ||
            rumbo == 'occidente' ||
            rumbo == 'nor-oriente' ||
            rumbo == 'nor-occidente' ||
            rumbo == 'sur-oriente' ||
            rumbo == 'sur-occidente') {

        var className = 'mark-' + rumbo + ' green-arrow-mark';
        var direction_icon = L.divIcon({
            className: className,
            iconSize: size_arrow_icon,
            iconAnchor: anchor_arrow_icon
        });
        return direction_icon;

    } else {
        return ultimo_reporte_icon;
    }
}

// Crea y agrega marca con su respectivo icono distintivo e
// informacion relacionada del punto
function addNewMark(item, icon_sel, view) {

    var lat = item.latitud;
    var lon = item.longitud;
    var placa = item.placa;
    var data = item.info;

    setBounds(lat, lon);

    var icon =
            (icon_sel == 'geoIn') ? iconGeoIn :
            (icon_sel == 'geoOut') ? iconGeoOut :
            (icon_sel == 'punto_control') ? punto_control_icon :
            (icon_sel == 'pasajero') ? pasajero_icon :
            (icon_sel == 'alarma') ? alarma_icon :
            (icon_sel == 'ultimo_reporte') ? iconToLastReport(item, icon_sel) : //ultimo_reporte_icon :
            (icon_sel == 'primer_reporte') ? primer_reporte_icon :
            (icon_sel == 'norte') ? norte_icon :
            (icon_sel == 'sur') ? sur_icon :
            (icon_sel == 'oriente') ? oriente_icon :
            (icon_sel == 'occidente') ? occidente_icon :
            (icon_sel == 'nor-oriente') ? nororiente_icon :
            (icon_sel == 'nor-occidente') ? noroccidente_icon :
            (icon_sel == 'sur-oriente') ? suroriente_icon :
            (icon_sel == 'sur-occidente') ? suroccidente_icon : otros_icon;

    var newLatLng = new L.LatLng(lat, lon);
    var marker = null;

    if (icon_sel === "geoIn" || icon_sel === "geoOut") {
        marker = new L.Marker(newLatLng, {draggable: 'false', icon: icon, zIndexOffset: 10000})
                .bindPopup(data);
    } else {
        marker = new L.Marker(newLatLng, {draggable: 'false', icon: icon})
                .bindPopup(data);
    }

    var splaca = document.getElementById("splaca").value;
    if (splaca == "") {
        marker.bindLabel(placa, {className: 'custom_class', noHide: true, offset: [23, -16]});
    }

    // Registra posicion de marca para ubicacion y muestra posterior
    // cuando sea seleccionada
    marker.on('click', function (e) {
        indexMark(item.index);
    });
    map.addLayer(marker);
    // agregamos marca a lista
    lst_mark_gps.splice(item.index, 0, marker);
    // agregamos item gps a lista
    lst_global_gps.splice(item.index, 0, item);

    var index_keep_mark = getIndexMark();
    if (index_keep_mark >= 0) {

        // Si un punto ha sido pulsado,
        // se ubica y conserva enfoque
        showMarkAUX(index_keep_mark);
        if (CENTER_MAP != null) {
            map.setView(CENTER_MAP, ZOOM_MAP);
        }

    } else if (CENTER_MAP != null) {

        // Si ha ocurrido evento zoom o arrastre,
        // se conserva enfoque
        map.setView(CENTER_MAP, ZOOM_MAP);

    } else if (icon_sel == 'ultimo_reporte' && infoContextual && REFRESH_MAP) {

        // Se visualiza punto mas reciente
        marker.openPopup();
    }
}

// Valores limite para latitud y longitud
// latitud  : -90  +90
// longitud : -180 +180

// Establece valores limite por defecto para coordenadas
function resetBounds() {
    minLat = [91, 0];
    minLon = [0, 181];
    maxLat = [-91, 0];
    maxLon = [0, -181];
}

// Actualiza valores limite de coordenadas en base
// a coordenadas de puntos agregados
function setBounds(lat, lon) {

    // Se especifican latitudes y longitudes extremas (min, max)    
    if (lat < minLat[0])
        minLat = [lat, lon];
    if (lat > maxLat[0])
        maxLat = [lat, lon];
    if (lon < minLon[1])
        minLon = [lat, lon];
    if (lon > maxLon[1])
        maxLon = [lat, lon];
}

// Min/Max zoom map 0/18
// Establece zoom respecto a valores maximos de coordenadas
function zoomInBounds() {

    if (map != null) {
        //var mlat = (parseFloat(minLat[0]) + parseFloat(maxLat[0])) / 2;
        //var mlon = (parseFloat(minLon[1]) + parseFloat(maxLon[1])) / 2;        
        //console.log(mlat +" "+ mlon);
        //var med_point = new L.LatLng(mlat, mlon);
        //var zoom = 18;        
        var limit_points = [minLat, maxLat, minLon, maxLon];
        map.fitBounds(limit_points);
    }
}

// Funcion que inhabilita el arrastre de marcas creadas en el mapa
function disableDrag() {

    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            layer.dragging.disable();
        }
    });
}

// Actualiza informacion de panel informativo segun
// punto ubicado en posicion index
function updateInfoPanel(index) {

    var item_gps = lst_global_gps[index];

    if (item_gps != null) {

        var fechaHora = item_gps.fechaServidorDate + " " + item_gps.fechaServidor;
        var numeracion = item_gps.numeracion;
        var numeracionInicial = item_gps.numeracionInicial;
        var dist_km = (item_gps.distanciaRecorrida / 1000);
        var ipk = item_gps.ipk;
        var npasajeros = "-";

        if (numeracion >= 0 &&
                numeracionInicial >= 0 &&
                numeracion >= numeracionInicial) {
            npasajeros = (numeracion - numeracionInicial);
        } else {
            numeracion = numeracionInicial = "-";
        }

        document.getElementById("fecha").value = fechaHora;
        document.getElementById("placa").value = item_gps.placa;
        document.getElementById("localizacion").value = item_gps.localizacion;
        document.getElementById("puntoControl").value = item_gps.msg;
        document.getElementById("numInicial").value = numeracionInicial;
        document.getElementById("numFinal").value = numeracion;
        document.getElementById("numPasajeros").value = npasajeros;
        document.getElementById("distanciaRecorrida").value = dist_km;
        document.getElementById("ipk").value = ipk;

        var splaca = document.getElementById("splaca");
        if (splaca.value == "") {
            document.getElementById("numInicial").value = "-";
            document.getElementById("numFinal").value = "-";
            document.getElementById("numPasajeros").value = "-";
            document.getElementById("distanciaRecorrida").value = "-";
            document.getElementById("ipk").value = "-";
        }
    }
}

// Actualiza indicador de posicion para marca seleccionada.
function updatePosition(index) {

    var position = index + 1;

    if (SIZE_MAP > 0 &&
            position >= 1 && position <= SIZE_MAP) {
        position = (SIZE_MAP - position) + 1;
        document.getElementById("currentMark").innerHTML = position;
    }
}

// Muestra informacion de marca ubicada en posicion index
function showMarkAUX(index) {

    var mark = lst_mark_gps[index];
    if (mark != null) {
        indexMark(index);
        mark.openPopup();
        updateInfoPanel(index);
        updatePosition(index);
    }
}

// Muestra informacion de marca mas reciente (primera posicion)
function showFirstMarkMin() {
    map.closePopup();
    INDEX_MARK = 0;
    showMarkAUX(INDEX_MARK);
}

// Muestra informacion de marca mas antigua (ultima posicion)
function showLastMark() {
    gps_pausarAutoConsultaAuto();
    map.closePopup();
    INDEX_MARK = SIZE_MAP - 1;
    showMarkAUX(INDEX_MARK);
}

// Muestra informacion de marca mas reciente (primera posicion)
// Pausa autoconsulta
function showFirstMark() {
    gps_pausarAutoConsultaAuto();
    map.closePopup();
    INDEX_MARK = 0;
    showMarkAUX(INDEX_MARK);
}

// Muestra informacion de marca siguiente/anterior
// segun indicacion de variable previous
function showMark(previous) {

    gps_pausarAutoConsultaAuto();

    if (INDEX_MARK < 0)
        INDEX_MARK = 0;
    if (INDEX_MARK >= SIZE_MAP)
        INDEX_MARK = SIZE_MAP - 1;

    if (previous == 1) {
        // Ir a registro menos reciente '<'
        if (INDEX_MARK < SIZE_MAP - 1) {
            INDEX_MARK += 1;
        }
    } else {
        // Ir a registro mas reciente '>'
        if (INDEX_MARK > 0) {
            INDEX_MARK -= 1;
        }
    }

    map.closePopup();
    showMarkAUX(INDEX_MARK);
}

// Recupera cantidad de marcas existentes en el mapa
function sizeMap() {
    var n = 0;
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            n += 1;
        }
    });
    return n;
}

// Remueve todas las marcas del mapa
function removeAllMarks() {

    // Inicializa listas
    lst_mark_gps = [];
    lst_global_gps = [];

    // Inicializa coordenadas limite del mapa
    resetBounds();

    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });
}

// Habilita uso de mapa
function enableMap() {

    if (map != null) {
        map.dragging.enable();
        map.touchZoom.enable();
        map.doubleClickZoom.enable();
        map.scrollWheelZoom.enable();
        map.boxZoom.enable();
        map.keyboard.enable();
        if (map.tap)
            map.tap.enable();

        var divMap = document.getElementById('map');
        divMap.style.cursor = 'grab';
        $('#map').removeClass('map-load');
    }
}

// Inhabilita uso de mapa
function disableMap() {

    if (map != null) {
        removeAllMarks();

        map.dragging.disable();
        map.touchZoom.disable();
        map.doubleClickZoom.disable();
        map.scrollWheelZoom.disable();
        map.boxZoom.disable();
        map.keyboard.disable();
        if (map.tap)
            map.tap.disable();

        var divMap = document.getElementById('map');
        divMap.style.cursor = 'default';
        $('#map').addClass('map-load');
    }
}

// Funcion de inicio tras cargarse pagina
function onload() {
    iniciarMapa();
    gps_bloquearComponente("btn_consulta", true);
}
