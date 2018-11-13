var intervalId = -1;
var lastCheckDate = null;
var currentPlate = null;
var lastTextChat = "";
var hUserMessagesId = {};
var hUsedPosition = {};
hUsedPosition.used = 0;
hUsedPosition.inList = {};
hUsedPosition.plates = {};
var hAllPlates = {};
var aAllPlates = [];
var hAllGroups = {};
var aAllGroups = [];
var hTypeMessages = {};
hTypeMessages["1"] = "Falla mec&aacute;nica";
hTypeMessages["2"] = "Accidente de tr&aacute;nsito";
hTypeMessages["3"] = "Novedad con pasajero";
hTypeMessages["4"] = "Solicitud de llamada";
hTypeMessages["101"] = "Anuncio";
var hTypeMessagesClasses = {};
hTypeMessagesClasses["1"] = "falla-mecanica";
hTypeMessagesClasses["2"] = "accidente-transito";
hTypeMessagesClasses["3"] = "novedad-pasajero";
hTypeMessagesClasses["4"] = "solicitud-llamada";
hTypeMessagesClasses["101"] = "anuncio";
$(document).ready(function () {

    lastCheckDate = getLastCheckDate();
    currentPlate = null;
    var chatContainer = null;
    var idComingChat = null;
    var lastIdMovilInChat = null;
    var oLastMovilInChat = null;
    var sLastMovilInChat = null;
    var movilInfoInList = null;
    var mgcList = null;
    var gmList = null;
    var mgcOneList = null;
    var oChatSideBar = null;
    var oChatContent = null;
    var textArea = null;
    var message = null;
    var bAllViewed = false;
    var oMovilInfo = null;
    var oNumNoViewed = null;
    var oNewChats = null;
    var sPlate = null;
    var sOptions = null;
    var sGroupOptions = null;
    var sMovilInfo = null;
    $.ajax({
        url: "/RDW1/loadMovilGPSChatList",
        method: "POST",
        data: null,
        success: function (data) {
            gmList = data.gmList;
            mgcList = data.mgcList;
            oChatSideBar = $('#moviles-info');
            var iCount = 0;
            mgcList.forEach(function (a) {
                sMovilInfo = "<div id='" + a.plate + "_list' class='movil-info" + (a.numNotViewed > 0 ? " new-chat" : "") + "' data-plate = '" + a.plate + "' data-gps='" + a.fkGPS + "' data-num='" + a.internalNum + "'>\n\
            <div class ='detail'><span>" + a.plate + " - " + a.internalNum + "</span></div>\n\
                    <div class='status-gps indicator-gps inactive' title = 'gps desconectado' data-gps='" + a.fkGPS + "' data-plate='" + a.plate + "'></div>\n\
                    <div class ='num-messages" + (a.numNotViewed > 0 ? " messages" : "") + "'>" + (a.numNotViewed > 0 ? a.numNotViewed : 0) + "</div>\n\
            </div>";
                iCount++;
                if (!a.viewed && a.fkOrigin === 1) {
                    $(oChatSideBar).prepend(sMovilInfo);
                } else {
                    $(oChatSideBar).append(sMovilInfo);
                }

                if (a.numNotViewed > 0) {
                    if (hUsedPosition.used === 0) {
                        hUsedPosition.inList["last"] = {};
                        hUsedPosition.inList["last"].plate = a.plate;
                    }
                    hUsedPosition.plates[a.plate] = 1;
                    hUsedPosition.used++;
                }

                hAllPlates[a.plate] = {};
                hAllPlates[a.plate].plate = a.plate;
                hAllPlates[a.plate].fkGPS = a.fkGPS;
                hAllPlates[a.plate].internalNum = a.internalNum;

                aAllPlates.push(a.plate);
            });

            aAllPlates.sort();

            gmList.forEach(function (a) {
                aAllGroups.push({fk: a.fkGrupo, name: a.nombreGrupo});
            });

            aAllGroups.sort(function (a, b) {

                var x = a.name.toLowerCase();
                var y = b.name.toLowerCase();
                if (x < y) {
                    return -1;
                }
                if (x > y) {
                    return 1;
                }
                return 0;
            });

            $('.moviles-info .movil-info , .broadcast-icon').click(function () {

                idComingChat = $(this)[0].id;
                if (lastIdMovilInChat !== null) {
                    oLastMovilInChat = $("#" + lastIdMovilInChat);
                    $(oLastMovilInChat).toggleClass("obfuscate");
                    if (hUsedPosition.used > 0 && idComingChat !== "controller") {
                        sPlate = hUsedPosition.inList["last"].plate;
                        if (sPlate !== sLastMovilInChat) {
                            $("#" + sPlate + "_list").after($(oLastMovilInChat));
                        }
                        if (hUsedPosition.plates.hasOwnProperty(sLastMovilInChat)) {
                            hUsedPosition.used--;
                            delete hUsedPosition.plates[sLastMovilInChat];
                            oNewChats = $(".movil-info.new-chat");
                            if (oNewChats.length > 0) {
                                hUsedPosition.inList["last"].plate = $(oNewChats[oNewChats.length - 1]).data("plate");
                            }
                        }
                    } else {
                        $(movilInfoInList).prependTo('#moviles-info');
                    }
                }

                var sClassIndicatorGPS = "inactive";
                if (GLOBAL_GPS_STATUS !== undefined && GLOBAL_GPS_STATUS.hasOwnProperty($(this).data("gps"))) {
                    sClassIndicatorGPS = GLOBAL_GPS_STATUS[$(this).data("gps")]._class;
                }

                var sDataPlate = "";
                var sDataNum = "";
                var sDataGPS = "";
                chatContainer = $('#chat-container').empty();
                if (idComingChat === "controller") {

                    sDataPlate = idComingChat;
                    sDataNum = idComingChat;
                    sDataGPS = idComingChat;

                    lastIdMovilInChat = null;

                    sOptions = "";
                    for (var i = 0; i < aAllPlates.length; i++) {
                        sOptions += "<option value='" + aAllPlates[i] + "'>" + aAllPlates[i] + "-" + hAllPlates[aAllPlates[i]].internalNum + "</option>";
                    }

                    sGroupOptions = "";
                    for (var i = 0; i < aAllGroups.length; i++) {
                        sGroupOptions += "<option class='group-option-color' value='gr-" + aAllGroups[i].fk + "'>Gr-" + aAllGroups[i].name + "</option>";
                    }

                    $(chatContainer).append("<div id='" + idComingChat + "_chat' class='movil-info' data-plate='" + sDataPlate + "' data-num='" + sDataNum + "'>\n\
                                            <div class = 'detail'>\n\
                                                <span>ANUNCIO</span>\n\
                                            </div>\n\
                                            <div class='multicombo-selected'>\n\
                                            <select id='multicombo-plate' name='combo-plate' class='selectpicker form-control combo-to-chat'\n\
                                            data-live-search='true' multiple data-actions-box='true' data-selected-text-format='values'\n\
                                            data-container='body' title='Seleccione los veh&iacute;culos'>" + sOptions + "" + sGroupOptions + "\n\
                                            </select>\n\
                                            </div>\n\
                                            </div>");

                    $('.selectpicker.combo-to-chat').selectpicker({width: 180});

                } else {
                    sDataPlate = $(this).data("plate");
                    sDataNum = $(this).data("num");
                    sDataGPS = $(this).data("gps");

                    lastIdMovilInChat = idComingChat;
                    sLastMovilInChat = lastIdMovilInChat.replace("_list", "");
                    movilInfoInList = $("#" + idComingChat);
                    $(movilInfoInList).toggleClass("obfuscate");
                    $(movilInfoInList).removeClass("new-chat");
                    $(chatContainer).append("<div id='" + idComingChat + "_chat' class='movil-info' data-plate='" + sDataPlate + "' data-num='" + sDataNum + "'>\n\
                                            <div class = 'detail'>\n\
                                                <span>" + sDataPlate + " - " + sDataNum + "</span>\n\
                                            </div>\n\
                                            <div class='status-gps indicator-gps " + sClassIndicatorGPS + "' data-gps='" + sDataGPS + "' data-plate='" + sDataPlate + "'></div>\n\
                                        </div>");
                }


                $(chatContainer).append("<div id ='chat-content' class='chat-content'></div>");
                $(chatContainer).append("<div id ='chat-input' class='chat-input'>\n\
                                            <textarea class='input-area' rows='2' cols='45' placeholder='Escriba un mensaje'></textarea>\n\
                                            <div id='ic-send' class='ic-send' data-plate='" + sDataPlate + "' data-gps='" + sDataGPS + "' ></div>\n\
                                            </div>");
                currentPlate = sDataPlate;
                $.ajax({
                    url: "/RDW1/loadMovilGPSChatOneList",
                    method: "POST",
                    data: {plate: sDataPlate},
                    success: function (data) {
                        mgcOneList = data.mgcOneList;
                        oChatContent = $("#chat-content");
                        mgcOneList.forEach(function (a) {
                            $(oChatContent).append(addMeesageInChat(a));
                        });
                        bAllViewed = data.mgcAllViewed;
                        if (bAllViewed && mgcOneList.length) {
                            oMovilInfo = $("#" + mgcOneList[0].plate + "_list");
                            oNumNoViewed = $(oMovilInfo).find(".num-messages");
                            $(oNumNoViewed).removeClass("messages");
                            $(oNumNoViewed).toggleClass("no-messages");
                            $(oNumNoViewed).text("");
                        }
                        $(oChatContent).scrollTop(1000000);
                    }
                });
                $('#ic-send').click(function () {
                    textArea = $('textarea.input-area');
                    message = $(textArea).val();
                    message = message.trim();

                    if (message !== '') {

                        var sPlateToSend;
                        var sGPSToSend;
                        var sGroupToSend;
                        var sType;

                        if ($(this).data("plate") !== "controller") {

                            sPlateToSend = $(this).data("plate");
                            sGPSToSend = $(this).data("gps");
                            sGroupToSend = null;
                            sType = "one";


                        } else {

                            var sGroupId = "";
                            sPlateToSend = "";
                            sGroupToSend = "";
                            var bFirstGroup = false;
                            var bFirstPlate = false;
                            var oMultiComboPlate = $('.selectpicker.combo-to-chat');
                            var sMultiComboPlate = $(oMultiComboPlate).val();
                            if (sMultiComboPlate !== null && sMultiComboPlate !== "") {
                                for (var i = 0; i < sMultiComboPlate.length; i++) {
                                    if (sMultiComboPlate[i] !== "") {
                                        if (sMultiComboPlate[i].indexOf("gr-") !== -1) {

                                            sGroupId = ((sMultiComboPlate[i]).split("gr-"))[1];
                                            if (bFirstGroup) {
                                                sGroupToSend += ",";
                                            }
                                            bFirstGroup = true;
                                            sGroupToSend += sGroupId;

                                        } else {

                                            if (bFirstPlate) {
                                                sPlateToSend += ",";
                                            }
                                            bFirstPlate = true;
                                            sPlateToSend += "'" + sMultiComboPlate[i] + "'";

                                        }
                                    }
                                }
                            } else {
                                alert("Seleccione un vehiculo");
                                return null;
                            }

                            //only plates are needed
                            sGPSToSend = null;
                            sType = "multi";
                        }

                        $.ajax({
                            url: "/RDW1/saveMovilGPSChat",
                            method: "POST",
                            data: {type: sType, plate: sPlateToSend, gps: sGPSToSend, group: sGroupToSend, message: message},
                            success: function (data) {
                                if (data.response === "OK") {
                                    textArea = $('textarea.input-area');
                                    $(textArea).val("");
                                    oChatContent = $("#chat-content");
                                    $(oChatContent).append(addMeesageInChat(data.mgcOne[0]));
                                    hUserMessagesId["" + (data.mgcOne[0]).pkGPSChat] = 1;
                                    $(oChatContent).scrollTop(1000000);
                                }
                            }
                        });
                    }
                });
                $(".input-area").keyup(function (e) {
                    var keycode = (e.keyCode ? e.keyCode : e.which);
                    if (keycode === 13) {
                        $(this).val(lastTextChat);
                        $('#ic-send').click();
                    } else {
                        lastTextChat = normalizeText($(this).val());
                        $(this).val(lastTextChat);
                    }
                });
                $("#input-search").keyup();
            });
            intervalId = setInterval("gpsChatRefresh()", 3000);
            /** called from gps_status.js file **/
            getGPSStatus(true);
        }
    });
    $('#sruta').selectpicker({
        size: 8
    });
    $('#fecha-ctr').datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        language: 'es'
    });
    $('#smovil').selectpicker({
        size: 8
    });
    $('[data-toggle="tooltip"]').tooltip();
    dph_iniciaConsultaControl();
    $("#input-search").keyup(function () {
        searchPlate($(this).val());
    });
});
function gpsChatRefresh() {
    var mgcList = null;
    var oMovilInfo = null;
    var oChatContent = null;
    var oMessageAux = null;
    var oNumNoViewed = null;
    var bNewMessagesInChat = false;
    var sPlate = null;
    $.ajax({
        url: "/RDW1/loadCurrentMovilGPSChat",
        method: "POST",
        data: {plate: currentPlate, lastCheckDate: lastCheckDate},
        success: function (data) {
            if (data.response === "OK") {
                mgcList = data.mgcList;
                mgcList.forEach(function (a) {
                    if (!a.edition) {
                        if (currentPlate !== a.plate && a.fkOrigin === 1) {
                            oMovilInfo = $("#" + a.plate + "_list");
                            if (!(hUsedPosition.plates.hasOwnProperty(a.plate))) {
                                if (hUsedPosition.used === 0) {
                                    $(oMovilInfo).prependTo('#moviles-info');
                                } else {
                                    sPlate = hUsedPosition.inList["last"].plate;
                                    $("#" + sPlate + "_list").after($(oMovilInfo));
                                }
                                hUsedPosition.used++;
                                hUsedPosition.inList["last"] = {};
                                hUsedPosition.inList["last"].plate = a.plate;
                                hUsedPosition.plates[a.plate] = 1;
                            }
                            $(oMovilInfo).removeClass("new-chat");
                            $(oMovilInfo).toggleClass("new-chat");
                            oNumNoViewed = $(oMovilInfo).find(".num-messages");
                            $(oNumNoViewed).removeClass("messages");
                            $(oNumNoViewed).toggleClass("messages");
                            $(oNumNoViewed).text(a.numNotViewed);
                        } else if (currentPlate === a.plate) {
                            oMessageAux = $("#" + a.pkGPSChat + "_message");
                            if (oMessageAux.length === 0) {
                                oChatContent = $("#chat-content");
                                $(oChatContent).append(addMeesageInChat(a));
                                $(oChatContent).scrollTop(1000000);
                                bNewMessagesInChat = true;
                            }
                        }
                    } else {
                        updateMeesageInChat(a);
                    }
                });
            }
            if (bNewMessagesInChat) {
                $.ajax({
                    url: "/RDW1/updateAllViewed",
                    method: "POST",
                    data: {plate: currentPlate},
                    success: function (data) {

                    }
                });
            }
        }
    });
    lastCheckDate = getLastCheckDate();
}

function updateMeesageInChat(mgcOne) {
    if (mgcOne !== null) {
        var oStatus = null;
        oStatus = $("#" + mgcOne.pkGPSChat + "_message .status");
        $(oStatus).removeClass("replied");
        $(oStatus).removeClass("sent");
        $(oStatus).removeClass("processed");
        if (mgcOne.replied) {
            $(oStatus).addClass("replied");
        } else if (mgcOne.sent) {
            $(oStatus).addClass("sent");
        } else if (mgcOne.processed) {
            $(oStatus).addClass("processed");
        }
    }
}
function addMeesageInChat(mgcOne) {
    if (mgcOne !== null) {
        var sOrigin = (mgcOne.fkOrigin === 1 ? "driver" : "controller");
        var sStatus = (mgcOne.sent ? " sent" : "");
        sStatus = (mgcOne.replied ? " replied" : "");
        var sMessageType = "";

        if (hTypeMessages.hasOwnProperty("" + mgcOne.fkGPSMessageType) && mgcOne.plate !== "controller") {
            sMessageType = "<span class='type-message " + hTypeMessagesClasses["" + mgcOne.fkGPSMessageType] + "'>" + hTypeMessages["" + mgcOne.fkGPSMessageType] + "&nbsp;&nbsp; </span>";
        }

        return  "<div id='" + mgcOne.pkGPSChat + "_message' class='container-message " + sOrigin + "' data-plate = '" + mgcOne.plate + "' data-num='" + (mgcOne.internalNum ? mgcOne.internalNum : "_") + "'>" +
                "<div class='message message-" + sOrigin + " tooltip_'>" + sMessageType + " " + mgcOne.message + "<span class='tooltiptext " + sOrigin + "'>" + formatDateToChat(mgcOne.creationDate) + "</span></div>" +
                "<div class='info-status " + sOrigin + "'>" +
                "<div class='status" + sStatus + "'></div>" +
                "</div>" +
                "</div>";
    }
    return "";
}

function formatDateToChat(sDate) {
    if (sDate) {
        var hMonths = {};
        hMonths["01"] = "Ene";
        hMonths["02"] = "Feb";
        hMonths["03"] = "Mar";
        hMonths["04"] = "Abr";
        hMonths["05"] = "May";
        hMonths["06"] = "Jun";
        hMonths["07"] = "Jul";
        hMonths["08"] = "Ago";
        hMonths["09"] = "Sep";
        hMonths["10"] = "Oct";
        hMonths["11"] = "Nov";
        hMonths["12"] = "Dic";
        var aDate = sDate.split(" ");
        var aDate_aux = (aDate[0]).split("-");
        var sMonth = hMonths[aDate_aux[1]];
        var sDay = aDate_aux[2];
        var resDate = sMonth + " " + sDay + " " + ((aDate[1]).substring(0, (aDate[1]).length - 5));
        return resDate;
    } else {
        return "";
    }
}

function getLastCheckDate() {
    var c = new Date();
    var d = new Date(c.getTime() - 3000);
    var currSecond = d.getSeconds();
    var currMinute = d.getMinutes();
    var currHour = d.getHours();
    var currDate = d.getDate();
    var currMonth = d.getMonth() + 1;
    var currYear = d.getFullYear();
    return currYear + "-" + currMonth + "-" + currDate + " " + currHour + ":" + currMinute + ":" + currSecond;
}

function searchPlate(text) {
    var oNodePlate = null;
    var oNodeSpan = null;
    var sValue = null;
    for (var key in hAllPlates) {
        if (key !== currentPlate) {
            oNodePlate = $("#" + key + "_list");
            oNodeSpan = $("#" + key + "_list span");
            if (oNodePlate.length > 0) {
                sValue = $(oNodeSpan).text();
                if (text.trim() !== "") {
//                    if ((key.toLowerCase()).indexOf(text.toLowerCase()) === -1) {
                    if ((sValue.toLowerCase()).indexOf(text.toLowerCase()) === -1) {
                        if (!oNodePlate.hasClass("obfuscate")) {
                            $(oNodePlate).addClass("obfuscate");
                        }
                    } else {
                        $(oNodePlate).removeClass("obfuscate");
                    }
                } else {
                    $(oNodePlate).removeClass("obfuscate");
                }
            }
        }
    }
}

function normalizeText(text) {
    var hMaps = {};
    hMaps["Ã"] = "A";
    hMaps["À"] = "A";
    hMaps["Á"] = "A";
    hMaps["Ä"] = "A";
    hMaps["Â"] = "A";
    hMaps["È"] = "E";
    hMaps["É"] = "E";
    hMaps["Ë"] = "E";
    hMaps["Ê"] = "E";
    hMaps["Ì"] = "I";
    hMaps["Í"] = "I";
    hMaps["Ï"] = "I";
    hMaps["Î"] = "I";
    hMaps["Ò"] = "O";
    hMaps["Ó"] = "O";
    hMaps["Ö"] = "O";
    hMaps["Ô"] = "O";
    hMaps["Ø"] = "O";
    hMaps["Ù"] = "U";
    hMaps["Ú"] = "U";
    hMaps["Ü"] = "U";
    hMaps["Û"] = "U";
    hMaps["ã"] = "a";
    hMaps["à"] = "a";
    hMaps["á"] = "a";
    hMaps["ä"] = "a";
    hMaps["â"] = "a";
    hMaps["å"] = "a";
    hMaps["è"] = "e";
    hMaps["é"] = "e";
    hMaps["ë"] = "e";
    hMaps["ê"] = "e";
    hMaps["ì"] = "i";
    hMaps["í"] = "i";
    hMaps["ï"] = "i";
    hMaps["î"] = "i";
    hMaps["ò"] = "o";
    hMaps["ó"] = "o";
    hMaps["ö"] = "o";
    hMaps["ô"] = "o";
    hMaps["ø"] = "o";
    hMaps["ù"] = "u";
    hMaps["ú"] = "u";
    hMaps["ü"] = "u";
    hMaps["û"] = "u";
    hMaps["Ñ"] = "N";
    hMaps["ñ"] = "n";
    hMaps["Ç"] = "C";
    hMaps["ç"] = "c";
    hMaps["¢"] = "";
    hMaps["£"] = "";
    hMaps["¤"] = "";
    hMaps["¥"] = "";
    hMaps["§"] = "";
    hMaps["¨"] = "";
    hMaps["©"] = "";
    hMaps["ª"] = "";
    hMaps["«"] = "";
    hMaps["¬"] = "";
    hMaps["®"] = "";
    hMaps["¯"] = "";
    hMaps["°"] = "";
    hMaps["±"] = "";
    hMaps["·"] = "";
    hMaps["¸"] = "";
    hMaps["µ"] = "";
    hMaps["¶"] = "";
    hMaps["º"] = "";
    hMaps["»"] = "";
    hMaps["¿"] = "";
    hMaps["Æ"] = "";
    hMaps["æ"] = "";
    hMaps["ÿ"] = "";
    hMaps["?"] = "";
    var ret = [];
    var maxSize = 0;
    if (text.length > 90) {
        maxSize = 90;
    } else {
        maxSize = text.length;
    }
    for (var i = 0; i < maxSize; i++) {
        var c = text.charAt(i);
        if (hMaps.hasOwnProperty(c)) {
            ret.push(hMaps[c]);
        } else {
            ret.push(c);
        }
    }
    return ret.join('');
}

