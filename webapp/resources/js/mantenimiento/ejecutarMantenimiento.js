/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    setInterval(ejecutarMantenimiento, 60000);
    validarNotificaciones();
});

function ejecutarMantenimiento() {
    $.ajax({
        url: '/RDW1/ejecutarMantenimiento',
        method: "POST",
        dataType: "json",
        success: function (data) {

            if (data.response == "OK") {

                if (data.mensajes.length > 0) {
                    var notificador = document.getElementById("notificaciones");
                    notificador.style.color = "red";
                    var cantidadNotificaciones = $("#notificaciones").text();

                    if (cantidadNotificaciones == "") {
                        $("#notificaciones").text(data.mensajes.length);
                    } else {
                        cantidadNotificaciones = parseInt(cantidadNotificaciones);
                        $("#notificaciones").text(data.mensajes.length + cantidadNotificaciones);
                    }

                    if (data.mensajes.length <= 5) {
                        var options = {
                            autoHideDelay: 15000,
                            className: "info"
                        };

                        for (var i = 0; i < data.mensajes.length; i++) {
                            
                            if (data.mensajes[i].length <= 100) {
                                $.notify(data.mensajes[i], options);
                            } else {
                                $.notify(data.mensajes[i].substring(0, 100) + "...", options);
                            }
                        }
                    } else {
                        var options = {
                            autoHideDelay: 20000,
                            className: "info"
                        };

                        $.notify("Tiene " + data.mensajes.length + " nuevas notificaciones en la bandeja.", options);
                    }
                }
            }
        }
    });
}

$(document).mouseup(function (event) {
    setCookie("cantidadNotificaciones", $("#notificaciones").text(), 1);
    setCookie("elementoClic", $(event.target).context.id, 1);
})


function validarNotificaciones() {
    var cantidadNotificaciones = getCookie("cantidadNotificaciones");
    var elementoClic = getCookie("elementoClic");

    if (elementoClic == "linkNotificaciones") {
        setCookie("cantidadNotificaciones", "", 1);
        setCookie("elementoClic", "", 1);
    } else {
        if (cantidadNotificaciones != '') {
            $("#notificaciones").text(cantidadNotificaciones);
            $("#notificaciones").css("color", "red");
        }
        
        setCookie("cantidadNotificaciones", cantidadNotificaciones, 1);
        setCookie("elementoClic", elementoClic, 1);
    }
}


function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    
    for (var i = ca.length - 1; i >= 0; i--) {
        var c = ca[i];
        
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}