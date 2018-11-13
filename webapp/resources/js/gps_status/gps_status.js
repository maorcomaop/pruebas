var secondsSample = 10;
var GLOBAL_GPS_STATUS = {};
var gpsStatusClasses = {};
gpsStatusClasses[0] = "inactive";
gpsStatusClasses[1] = "active";
gpsStatusClasses[2] = "unhooked";
gpsStatusClasses[3] = "reset";

var gpsStatusTitle = {};
gpsStatusTitle[0] = "gps desconectado";
gpsStatusTitle[1] = "gps conectado";
gpsStatusTitle[2] = "gps sin enganchar";
gpsStatusTitle[3] = "gps modo reset";


// calls the status for the gps listed in the view. To work needs the class indicator-gps and the attribute data-gps. look examples in chat.js
function getGPSStatus(lastReports) {

    var secondsAux = secondsSample * 2;
    if (lastReports) {
        secondsAux = 0;
    }

    var sGPS = "";
    var sGPSAux = "";
    var sGPSMap = {};
    var oGPSNodes = $(".indicator-gps");
    for (var i = 0; i < oGPSNodes.length; i++) {

        sGPSAux = $(oGPSNodes[i]).data("gps");
        if (sGPSAux !== undefined && sGPSAux !== 0 && sGPSAux !== "") {
            if (!sGPSMap.hasOwnProperty(sGPSAux)) {
                sGPSMap[sGPSAux] = {};
                sGPS += sGPSAux + ",";
            }
        }
    }
    if (sGPS !== "") {

        sGPS = sGPS.slice(0, -1);

        $.ajax({
            url: "/RDW1/loadGPSStatus",
            method: "POST",
            data: {gpsIds: sGPS, seconds: secondsAux},
            success: function (data) {
                if (data.response === "OK") {
                    if (data.list.length > 0) {
                        (data.list).forEach(function (a) {
                            if (a.fkGPS) {
                                if (!GLOBAL_GPS_STATUS.hasOwnProperty(a.fkGPS)) {
                                    GLOBAL_GPS_STATUS[a.fkGPS] = {};
                                }
                                GLOBAL_GPS_STATUS[a.fkGPS].connectionStatus = a.estadoConexion;
                                GLOBAL_GPS_STATUS[a.fkGPS].disconnectionDate = a.fechaDesconexion;
                                GLOBAL_GPS_STATUS[a.fkGPS].lastReportDate = a.fechaUltimoReporte;
                                GLOBAL_GPS_STATUS[a.fkGPS].fkGPS = a.fkGPS;

                                var oGPSIndicator = $(".indicator-gps[data-gps='" + a.fkGPS + "']");
                                if (oGPSIndicator !== null) {

                                    for (var i = 0; i < oGPSIndicator.length; i++) {

                                        $(oGPSIndicator[i]).removeClass(gpsStatusClasses[0]);
                                        $(oGPSIndicator[i]).removeClass(gpsStatusClasses[1]);
                                        $(oGPSIndicator[i]).removeClass(gpsStatusClasses[2]);
                                        $(oGPSIndicator[i]).removeClass(gpsStatusClasses[3]);

                                        $(oGPSIndicator[i]).addClass(gpsStatusClasses[a.estadoConexion]);
                                        $(oGPSIndicator[i]).attr("title", gpsStatusTitle[a.estadoConexion]);
                                        GLOBAL_GPS_STATUS[a.fkGPS]._class = gpsStatusClasses[a.estadoConexion];
                                    }
                                }
                            }
                        });
                    }
                } else {
                    console.log("Error - Check Tomcat Log");
                }
            }
        });
    }

}

$(document).ready(function () {
    setInterval("getGPSStatus()", secondsSample * 1000);
});