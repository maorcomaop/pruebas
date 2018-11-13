var iCmdAdded = 0;
var intervalId = -1;
var refreshTime = 3;
var modalaux = null;
var modalauxClose = null;
var modalauxMessage = null;

var hCommandPackageType = {};
hCommandPackageType[1] = "GENERICO";
hCommandPackageType[2] = "DESPACHO";
hCommandPackageType[3] = "CHAT SIMPLE";
hCommandPackageType[4] = "CHAT MULTI";
hCommandPackageType[5] = "INTERFAZ";

var hCommandGroupType = {};
hCommandGroupType[1] = "GENERICO";
hCommandGroupType[2] = "PUNTO_CONTROL";
hCommandGroupType[3] = "INFO_PUNTO_DESPACHO";
hCommandGroupType[4] = "CHAT";
hCommandGroupType[5] = "CONFIGURACION";

var hCommandTypeLoadInfo = {};
hCommandTypeLoadInfo[2] = "ACTIVAR_GEO_PUNTOS";
hCommandTypeLoadInfo[5] = "MDFCR_REP_TIEMPO_MOVIL_ON";
hCommandTypeLoadInfo[6] = "MDFCR_REP_TIEMPO_MOVIL_OFF";
hCommandTypeLoadInfo[7] = "MDFCR_REP_DISTANCIA";
hCommandTypeLoadInfo[10] = "MDFCR_VELOCIDAD_LIMITE";
hCommandTypeLoadInfo[18] = "MDFCR_PARAM_CONTADOR";
hCommandTypeLoadInfo[22] = "MDFCR_CONEXION_IP";
hCommandTypeLoadInfo[23] = "MDFCR_CONEXION_PUERTO";
hCommandTypeLoadInfo[27] = "MDFCR_CONEXION_APN";

var hCommandGroupStatusGPS = {};
hCommandGroupStatusGPS[1] = "created";
hCommandGroupStatusGPS[2] = "sending";
hCommandGroupStatusGPS[3] = "completed-succesfull";
hCommandGroupStatusGPS[4] = "cancelled-automatically";
hCommandGroupStatusGPS[5] = "postponed";
hCommandGroupStatusGPS[6] = "restored";
hCommandGroupStatusGPS[7] = "completed-no-succesfull";
hCommandGroupStatusGPS[8] = "cancelled-manual";

var hCommandGroupStatusGPSToolTip = {};
hCommandGroupStatusGPSToolTip[1] = "pendiente";
hCommandGroupStatusGPSToolTip[2] = "enviando";
hCommandGroupStatusGPSToolTip[3] = "completado exitosamente";
hCommandGroupStatusGPSToolTip[4] = "cancelado automaticamente";
hCommandGroupStatusGPSToolTip[5] = "aplazado";
hCommandGroupStatusGPSToolTip[6] = "restaurado";
hCommandGroupStatusGPSToolTip[7] = "intentos vencidos";
hCommandGroupStatusGPSToolTip[8] = "cancelado manualmente";

var hCommandSendStatusGPSToolTip = {};
hCommandSendStatusGPSToolTip[1] = "pendiente";
hCommandSendStatusGPSToolTip[2] = "en cola de envio";
hCommandSendStatusGPSToolTip[3] = "enviando";
hCommandSendStatusGPSToolTip[4] = "respuesta exitosa";
hCommandSendStatusGPSToolTip[5] = "cancelado automaticamente";

$(document).ready(function () {

    fModalController();
    fCommandPackageProcessInfo();

    $("#cmd-save").on("click", function () {


        var data = {};
        data.plates = [];
        data.gps = [];
        data.gpsBrand = [];
        data.groupId = [];
        data.routeId = [];
        data.simpleCmmnds = [];
        data.domainCmmnds = [];
        data.code = "";
        data.tries = 0;
        data.numCommands = 0;
        data.isMainResetEnd = true;
        var commandWithReset = 0;
        var oComboCommand = null;
        var oMultiComboPlateGps = null;
        var oComboPlateGps = null;
        var oComboSendType = null;
        var oCommands = null;
        var oCommandList = null;
        var sComboPlateGps = null;
        var sMultiComboPlateGps = null;
        var sComboGroup = null;
        var sComboSendType = null;
        var oCommandCode = null;
        var oCommandTries = 0;
        var sCommandCode = null;
        var iCommandTries = 0;
        oComboCommand = $("#combo-command");
        oMultiComboPlateGps = $("#multicombo-plate");
        oComboPlateGps = $("#combo-plate");
        oComboSendType = $("#combo-send-type");
        sMultiComboPlateGps = $(oMultiComboPlateGps).val();
        sComboPlateGps = $(oComboPlateGps).val();
        sComboSendType = $(oComboSendType).val();
        oCommandCode = $("#cmd-code");
        oCommandTries = $("#cmd-tries");
        sCommandCode = $(oCommandCode).val();
        iCommandTries = $(oCommandTries).val();
        oCommands = $(".command_list .command-container");
        oCommandList = $(".command_list");

        if (sMultiComboPlateGps === null) {
            sMultiComboPlateGps = "";
        }
        if (sComboPlateGps === null) {
            sComboPlateGps = "";
        }

        if (sComboPlateGps === "" && sMultiComboPlateGps === "") {
            alert("Seleccione un vehiculo");
            return;
        }

        if ($(oCommands).size() === 0) {
            alert("Seleccione un comando");
            return;
        }

        var i;
        var aValue = [];

        var bSuntechBrand = false;
        var bCellocatorBrand = false;

        if (sComboSendType === "IND") {
            if (sComboPlateGps !== null && sComboPlateGps !== "") {
                aValue = sComboPlateGps.split("-");
                if (aValue.length === 3) {
                    data.plates.push(aValue[0]);
                    data.gps.push((aValue[1].trim() === "" ? null : aValue[1]));
                    data.gpsBrand.push(aValue[2]);
                    if (aValue[2] === "2" && !bSuntechBrand) {
                        bSuntechBrand = true;
                    }
                    if (aValue[2] === "1" && !bCellocatorBrand) {
                        bCellocatorBrand = true;
                    }
                }
            }
        } else if (sComboSendType === "MUL") {
            if (sMultiComboPlateGps !== null && sMultiComboPlateGps !== "") {
                for (i = 0; i < sMultiComboPlateGps.length; i++) {
                    if (sMultiComboPlateGps[i] !== "") {
                        aValue = (sMultiComboPlateGps[i]).split("-");
                        if (aValue.length === 3) {
                            data.plates.push(aValue[0]);
                            data.gps.push((aValue[1].trim() === "" ? null : aValue[1]));
                            data.gpsBrand.push(aValue[2]);
                            if (aValue[2] === "2" && !bSuntechBrand) {
                                bSuntechBrand = true;
                            }
                            if (aValue[2] === "1" && !bCellocatorBrand) {
                                bCellocatorBrand = true;
                            }
                        }
                    }
                }
            }
        }

        var hCommand;
        var hCommands = [];
        var oInput;
        var oMultiInput;
        var oSelect;
        var j = 0;
        data.numCommands = 0;
        for (i = 0; i < oCommands.length; i++) {
            oInput = $(oCommands[i]).find("input.class-field");
            oMultiInput = $(oCommands[i]).find("div.class-field");
            oSelect = $(oCommands[i]).find("select.class-field");
            hCommand = {};
            hCommand.type = $(oCommands[i]).data("cmdtype");
            //reset command    
            if (hCommand.type === 12) {
                data.isMainResetEnd = false;
            }

            if (hCommandParams[hCommand.type].reset) {
                commandWithReset++;
            }

            if (oInput.length > 0 || oMultiInput.length > 0 || oSelect.length > 0) {
                hCommand.params = {};
            } else {
                hCommand.params = null;
            }

            if (oInput.length > 0) {
                for (j = 0; j < oInput.length; j++) {
                    hCommand.params[$(oInput[j]).attr("name")] = $(oInput[j]).val();
                }
            }
            if (oMultiInput.length > 0) {
                for (j = 0; j < oMultiInput.length; j++) {
                    if ($(oMultiInput[j]).attr("type") === "ip") {
                        hCommand.params[$(oMultiInput[j]).attr("name")] = $(oMultiInput[j]).find("input.part-1").val() + "." + $(oMultiInput[j]).find("input.part-2").val() + "." + $(oMultiInput[j]).find("input.part-3").val() + "." + $(oMultiInput[j]).find("input.part-4").val();
                    }
                }
            }
            if (oSelect.length > 0) {
                for (j = 0; j < oSelect.length; j++) {
                    hCommand.params[$(oSelect[j]).attr("name")] = $(oSelect[j]).val();
                }
            }

            hCommands.push(hCommand);

            if (hCommand.type !== 22 && hCommand.type !== 23 && hCommand.type !== 27) {
                data.simpleCmmnds.push(hCommand);
                data.numCommands++;
            }
        }

        if (bCellocatorBrand) {
            var bUsed23 = false;
            var bUsed282930 = false;
            var bUsed242531 = false;

            var hCommand23 = {};
            hCommand23.type = 23;
            hCommand23.params = {};

            /*For IP DOMAIN command group*/
            var hCommand24 = {};
            hCommand24.type = 24;
            hCommand24.params = {};

            var hCommand25 = {};
            hCommand25.type = 25;
            hCommand25.params = {};

            var hCommand31 = {};
            hCommand31.type = 31;
            hCommand31.params = {};

            /*For APN command group*/
            var hCommand28 = {};
            hCommand28.type = 28;
            hCommand28.params = {};

            var hCommand29 = {};
            hCommand29.type = 29;
            hCommand29.params = {};

            var hCommand30 = {};
            hCommand30.type = 30;
            hCommand30.params = {};

            for (i = 0; i < hCommands.length; i++) {

                // Asking for command type 22 - ip
                if (hCommands[i].type === 22) {
                    var access = hCommands[i].params["access"];
                    if (hCommands[i].params["access"] === "server_domain") {

                        var serverDomain = hCommands[i].params["server_domain"];
                        if (serverDomain.trim() !== "") {
                            var serverDomain1 = "";
                            var serverDomain2 = "";

                            if (serverDomain.length <= 15) {
                                serverDomain1 = serverDomain;
                                serverDomain2 = "";
                            } else {
                                serverDomain1 = serverDomain.substring(0, 15);
                                serverDomain2 = serverDomain.substring(15, 31);
                            }

                            hCommand24.params["server_domain"] = serverDomain;
                            hCommand24.params["server_domain_part"] = serverDomain1;
                            hCommand24.params["server_ip"] = "0.0.0.0";
                            hCommand24.params["access"] = access;
                            hCommand25.params["server_domain_part"] = serverDomain2;
                            hCommand31.params["server_ip"] = "0.0.0.0";
                            bUsed242531 = true;
                        }
                    } else if (hCommands[i].params["access"] === "server_ip") {

                        var serverIP = hCommands[i].params["server_ip"];
                        if (serverIP.trim() !== "") {

                            hCommand24.params["server_domain"] = "";
                            hCommand24.params["server_domain_part"] = "";
                            hCommand24.params["server_ip"] = serverIP;
                            hCommand24.params["access"] = access;
                            hCommand25.params["server_domain_part"] = "";
                            hCommand31.params["server_ip"] = serverIP;
                            bUsed242531 = true;
                        }

                    }
                    // Asking for command type 23 - server port
                } else if (hCommands[i].type === 23) {
                    if (hCommands[i].params["server_port_cellocator"] !== "0") {
                        hCommand23.params["server_port"] = hCommands[i].params["server_port_cellocator"];
                        bUsed23 = true;
                    }
                    // Asking for command type 27- set apn to cellocator gps
                } else if (hCommands[i].type === 27) {
                    if (hCommands[i].params["full_apn"] !== "") {
                        var serverAPN1;
                        var serverAPN2;
                        var serverAPN3;

                        var serverAPN = hCommands[i].params["full_apn"];

                        if (serverAPN.length <= 11) {
                            serverAPN1 = serverAPN;
                            serverAPN2 = "";
                            serverAPN3 = "";
                        } else if (serverAPN.length > 11 && serverAPN.length <= 27) {
                            serverAPN1 = serverAPN.substring(0, 11);
                            serverAPN2 = serverAPN.substring(11, serverAPN.length);
                            serverAPN3 = "";
                        } else if (serverAPN.length > 27 && serverAPN.length <= 29) {
                            serverAPN1 = serverAPN.substring(0, 11);
                            serverAPN2 = serverAPN.substring(11, 27);
                            serverAPN3 = serverAPN.substring(27, serverAPN.length);
                        }

                        hCommand28.params["full_apn"] = serverAPN;
                        hCommand28.params["part_apn"] = serverAPN1;
                        hCommand28.params["length_apn"] = serverAPN.length;

                        hCommand29.params["full_apn"] = serverAPN;
                        hCommand29.params["part_apn"] = serverAPN2;
                        hCommand30.params["full_apn"] = serverAPN;
                        hCommand30.params["part_apn"] = serverAPN3;
                        bUsed282930 = true;
                    }
                }
            }

            if (bUsed23) {
                data.simpleCmmnds.push(hCommand23);
                data.numCommands++;
            }

            if (bUsed242531) {
                data.simpleCmmnds.push(hCommand24);
                data.numCommands++;
                data.simpleCmmnds.push(hCommand25);
                data.numCommands++;
                data.simpleCmmnds.push(hCommand31);
                data.numCommands++;
            }

            if (bUsed282930) {
                data.simpleCmmnds.push(hCommand28);
                data.numCommands++;
                data.simpleCmmnds.push(hCommand29);
                data.numCommands++;
                data.simpleCmmnds.push(hCommand30);
                data.numCommands++;
            }
        }

        if (bSuntechBrand) {
            var bUsed20 = false;
            var bUsed21 = false;
            var bUsed26 = false;

            var hCommand20 = {};
            hCommand20.type = 20;
            hCommand20.params = {};

            var hCommand21 = {};
            hCommand21.type = 21;
            hCommand21.params = {};

            var hCommand26 = {};
            hCommand26.type = 26;
            hCommand26.params = {};

            for (i = 0; i < hCommands.length; i++) {
                // Asking for command type 5 - time movil on
                if (hCommands[i].type === 5) {
                    hCommand20.params["time_driving"] = hCommands[i].params["time"];
                    bUsed20 = true;
                }
                // Asking for command type 6 - time movil off
                if (hCommands[i].type === 6) {
                    hCommand20.params["time_parking"] = hCommands[i].params["time"];
                    bUsed20 = true;
                }
                // Asking for command type 7 - distancia
                if (hCommands[i].type === 7) {
                    hCommand20.params["distance"] = hCommands[i].params["distance"];
                    bUsed20 = true;
                }
                // Asking for command type 2 - geo fence
                if (hCommands[i].type === 2) {
                    hCommand21.params["geofence"] = hCommands[i].params["enabled"];
                    bUsed21 = true;
                }
                // Asking for command type 10 - over speed limit
                if (hCommands[i].type === 10) {
                    hCommand21.params["over_speed_limit"] = hCommands[i].params["speed"];
                    bUsed21 = true;
                }
                // Asking for command type 22 - ip
                if (hCommands[i].type === 22) {
                    hCommand26.params["server_ip"] = hCommands[i].params["server_ip"];
//                    hCommand26.params["server_domain"] = hCommands[i].params["server_domain"];
//                    hCommand26.params["access"] = hCommands[i].params["access"];
                    bUsed26 = true;
                }
                // Asking for command type 23 - port
                if (hCommands[i].type === 23) {
                    if (hCommands[i].params["server_port_suntech"] !== "0") {
                        hCommand26.params["server_port"] = hCommands[i].params["server_port_suntech"];
                        bUsed26 = true;
                    }
                }
                // Asking for command type 27 - apn
                if (hCommands[i].type === 27) {
                    if (hCommands[i].params["full_apn"] !== "") {
                        hCommand26.params["full_apn"] = hCommands[i].params["full_apn"];
                        bUsed26 = true;
                    }
                }
            }

            if (bUsed20) {
                data.simpleCmmnds.push(hCommand20);
                data.numCommands++;
            }
            if (bUsed21) {
                data.simpleCmmnds.push(hCommand21);
                data.numCommands++;
            }
            if (bUsed26) {
                data.simpleCmmnds.push(hCommand26);
                data.numCommands++;
            }
        }

        if (data.isMainResetEnd && commandWithReset > 0) {
            data.isMainResetEnd = true;
        } else {
            data.isMainResetEnd = false;
        }

        data.code = sCommandCode;
        data.tries = iCommandTries;
        var jData = JSON.stringify(data);

        modalaux.style.display = "block";

        $.ajax({
            url: "/RDW1/saveGPSCommandGroups",
            method: "POST",
            dataType: 'json',
            data: {data: jData},
            success: function (data) {
                if (data.response === "OK") {
                    modalauxMessage.innerHTML = "Comandos guardados y pendientes de envio";
                    $(oComboCommand).selectpicker('val', '');
                    $(oMultiComboPlateGps).selectpicker('val', '');
                    $(oComboPlateGps).selectpicker('val', '');
                    $(oCommandCode).val("...");
                    $(oCommandTries).val("2");
                    $(oCommandList).empty();
                    fCommandPackageRender(data.list, false);

                    /** called from gps_status.js file **/
                    getGPSStatus(true);

                } else {
                    modalauxMessage.innerHTML = "ERROR";
                }
            }
        });
    });
}
);

function fCommandPackageRender(aList, bAppend) {
    var oProgressInfo = $('#progress-info');
    var iCount = 0;
    aList.forEach(function (a) {
        var sProgressInfo = "<div id='package_" + a.pkGpsCommandPackage + "_list' class='package-info'>\n\
                <div class='package-header'>\n\
                    <div class = 'p-info pack-type'>" + hCommandPackageType[a.fkGPSCommandPackageType] + "</div>\n\
                    <div class = 'p-info pack-date' data-toggle='tooltip' title='fecha de creación'>" + formatDateToHeader(a.creationDate) + "</div>\n\
                    </div>\n\
                    <div class = 'groups-info'>\n\
                    " + fCommandGroupRender(a.gpsCommandGroupList) + "\n\
                    </div>\n\
                </div>";
        iCount++;
        if (bAppend) {
            $(oProgressInfo).append(sProgressInfo);
        } else {
            $(oProgressInfo).prepend(sProgressInfo);
        }
        fEvents("package_" + a.pkGpsCommandPackage + "_list");
    });
}

function fCommandGroupRender(aList) {

    var sGroupsInfo = "";
    aList.forEach(function (a) {
        sGroupsInfo += "<div id='group_" + a.pkGpsCommandGroup + "_list' class='cgroup-info'>\n\
                    <div class = 'group-header'>\n\
                    <div class = 'g-info group-status " + hCommandGroupStatusGPS[a.fkStateInGps] + "' data-toggle='tooltip' title='" + hCommandGroupStatusGPSToolTip[a.fkStateInGps] + "'></div>\n\
                    <div class = 'g-info group-type'>" + hCommandGroupType[a.fkCommandGroupType] + "</div>\n\
                    <div class = 'g-info group-ind-gps indicator-gps inactive' data-gps='" + a.fkGPS + "' data-plate='" + a.plate + "' title='gps desconectado'></div>\n\
                    <div class = 'g-info group-plate' data-toggle='tooltip' title='placa'>" + (a.plate ? a.plate : "...") + "</div>\n\
                    <div class = 'g-info group-commands'>" + (a.gpsCommandSendList).length + "</div>\n\
                    <div class = 'track-label-cmd-gps' data-toggle='tooltip' title='comandos'></div>\n\
                    <div class = 'g-info group-attempts'>" + a.numPostponed + " de " + a.maxPostponed + "</div>\n\
                    <div class = 'g-info label-cmd-attempt' data-toggle='tooltip' title='intentos completados'></div>\n\
                    <div class = 'g-info group-forward label-cmd-forward' data-toggle='tooltip' title='reenviar' data-forward-id = '" + a.pkGpsCommandGroup + "' ></div>\n\
                    </div>\n\
                    <div class = 'commands-info'>\n\
                    " + fCommandSendRender(a.gpsCommandSendList) + "\
                    </div>\n\
                </div>";
    });
    return sGroupsInfo;
}

function fCommandSendRender(aList) {

    var sCommandsInfo = "";
    var sStatus;
    var sStatusTT;
    var sResponse;
    var sParam;
    var sResponseJSON;
    var sParamJSON;
    var sResponseDiv;
    var sParamsDiv;
    var keyAux = "";
    aList.forEach(function (a) {

        sStatus = "init";
        sStatus = (a.cancelled ? " cancelled" : (a.replied ? " replied" : (a.sent ? " sent" : (a.viewed ? " viewed" : "init"))));
        sStatusTT = "1";
        sStatusTT = (a.cancelled ? "5" : (a.replied ? "4" : (a.sent ? "3" : (a.viewed ? "2" : "1"))));
        keyAux = "";
        sResponse = "";
        sResponseDiv = "";
        if (a.response) {
            sResponseJSON = JSON.parse(a.response);
            if (sResponseJSON) {
                for (var key in sResponseJSON) {
                    keyAux = (hTranslate[key] ? (hTranslate[key]).toUpperCase() : key.toUpperCase());
                    sResponse += "<div class = 'response-param'><span class = 'label label-answer'>" + keyAux + "</span><span class = 'label' style='color:black'>" + sResponseJSON[key] + "</span></div>";
                }
            }
        }

        sParam = "";
        sParamsDiv = "";
        if (a.params && a.params !== "") {
            sParamJSON = JSON.parse(a.params);
            if (sParamJSON) {
                for (var key in sParamJSON) {
                    keyAux = (hTranslate[key] ? (hTranslate[key]).toUpperCase() : key.toUpperCase());
                    sParam += "<div class = 'parameter-param'><span class = 'label label-answer'>" + keyAux + "</span><span class = 'label' style='color:black'>" + sParamJSON[key] + "</span></div>";
                }
            }
            sParamsDiv = "<div class = 'csend-param'><span class='label label-title'>PARAMETROS</span><div class = 'param'>" + sParam + "</div></div>";
        }

        sResponseDiv = "<div class = 'csend-response'><span class='label label-title response-title" + (sResponse !== "" ? "" : " obfuscate") + "'>RESPUESTA</span><div class = 'response'>" + sResponse + "</div></div>";
        sCommandsInfo += "<div id='command_" + a.pkGpsCommandSend + "_list' class='csend-info'>\n\
                                <div class = 'send-header'>\n\
                                <div class = 'csend-status " + sStatus + "' data-toggle='tooltip' title='" + hCommandSendStatusGPSToolTip[sStatusTT] + "'></div>\n\
                                <div class = 'csend-type'>" + (hCommandParams[a.fkCommandKey].name ? hCommandParams[a.fkCommandKey].name : "NO_ESPRADO") + "</div>\n\
                                </div><div class='command-more-info obfuscate'>" + sParamsDiv + "" + sResponseDiv + "</div></div>";
    });
    return sCommandsInfo;
}

function fCommandPackageProcessInfo() {

    $.ajax({
        url: "/RDW1/listGPSCommandGroups",
        method: "POST",
        dataType: 'json',
        data: {},
        success: function (data) {
            if (data.response === "OK") {
                fCommandPackageRender(data.list, true);

                /** called from gps_status.js file **/
                getGPSStatus(true);

            }
            intervalId = setInterval("gpsCommandRefresh()", refreshTime * 1000);
        }
    });
}

function gpsCommandRefresh() {

    $.ajax({
        url: "/RDW1/loadCurrentCommandStatus",
        method: "POST",
        data: {seconds: refreshTime + 3},
        success: function (data) {
            if (data.response === "OK") {
                if (data.listPackage) {
                    fUpdatePackageStatus(data.listPackage);
                }
                if (data.listGroup) {
                    fUpdateGroupStatus(data.listGroup);
                }
                if (data.listCommand) {
                    fUpdateCommandStatus(data.listCommand);
                }
            }
        }
    });
}

function fUpdatePackageStatus(aList) {
}

function fUpdateGroupStatus(aList) {

    var oStatus = null;
    var oAttempts = null;
    aList.forEach(function (a) {
        oStatus = $("#group_" + a.pkGpsCommandGroup + "_list .group-status");
        for (var key in hCommandGroupStatusGPS) {
            $(oStatus).removeClass(hCommandGroupStatusGPS[key]);
        }
        $(oStatus).addClass(hCommandGroupStatusGPS[a.fkStateInGps]);
        $(oStatus).attr("title", hCommandGroupStatusGPSToolTip[a.fkStateInGps]);

        oAttempts = $("#group_" + a.pkGpsCommandGroup + "_list .group-attempts");
        $(oAttempts).html(a.numPostponed + " de " + a.maxPostponed);
    });
}

function fUpdateCommandStatus(aList) {

    var oStatus;
    var sStatus;
    var sStatusTT;
    var oResponse;
    var oResponseTitle;

    var sResponse = "";
    var sResponseJSON = "";
    aList.forEach(function (a) {
        oStatus = $("#command_" + a.pkGpsCommandSend + "_list .csend-status");

        sStatus = "init";
        sStatus = (a.cancelled ? "cancelled" : (a.replied ? "replied" : (a.sent ? "sent" : (a.viewed ? "viewed" : "init"))));

        sStatusTT = "1";
        sStatusTT = (a.cancelled ? "5" : (a.replied ? "4" : (a.sent ? "3" : (a.viewed ? "2" : "1"))));

        $(oStatus).removeClass("cancelled");
        $(oStatus).removeClass("replied");
        $(oStatus).removeClass("sent");
        $(oStatus).removeClass("viewed");
        $(oStatus).removeClass("init");

        $(oStatus).addClass(sStatus);
        $(oStatus).attr("title", hCommandSendStatusGPSToolTip[sStatusTT]);

        sResponse = "";
        var keyAux = "";
        if (a.response) {
            sResponseJSON = JSON.parse(a.response);
            for (var key in sResponseJSON) {
                keyAux = (hTranslate[key] ? (hTranslate[key]).toUpperCase() : key.toUpperCase());
                sResponse += "<div class = 'response-param'><span class = 'label label-answer'>" + keyAux + "</span><span class = 'label' style='color:black'>" + sResponseJSON[key] + "</span></div>";
            }
        }
        oResponseTitle = $("#command_" + a.pkGpsCommandSend + "_list .response-title");
        if (sResponse !== "") {
            $(oResponseTitle).removeClass("obfuscate");
        } else {
            $(oResponseTitle).addClass("obfuscate");
        }
        oResponse = $("#command_" + a.pkGpsCommandSend + "_list .response");
        $(oResponse).html(sResponse);
    });
}

function fEvents(sIdParent) {

    $("#" + sIdParent + " .package-header").on("click", function (e) {
        e.stopPropagation();
        $($(this).siblings(".groups-info")[0]).toggleClass("obfuscate");
    });
    $("#" + sIdParent + " .group-header").on("click", function (e) {
        e.stopPropagation();
        $($(this).siblings(".commands-info")[0]).toggleClass("obfuscate");
    });
    $("#" + sIdParent + " .send-header").on("click", function (e) {
        e.stopPropagation();
        $($(this).siblings(".command-more-info")[0]).toggleClass("obfuscate");
    });

    $("#" + sIdParent + " .cgroup-info .group-forward").click(function (e) {
        e.stopPropagation();
        modalaux.style.display = "block";
        $.ajax({
            url: "/RDW1/resetCommandGroup",
            method: "POST",
            data: {pkGpsCommandGroup: $(this).data("forward-id"), seconds: refreshTime + 3},
            success: function (data) {

                if (data.response === "OK") {
                    modalauxMessage.innerHTML = "Comandos programados para reenvio";
                    if (data.listPackage) {
                        fUpdatePackageStatus(data.listPackage);
                    }
                    if (data.listGroup) {
                        fUpdateGroupStatus(data.listGroup);
                    }
                    if (data.listCommand) {
                        fUpdateCommandStatus(data.listCommand);
                    }
                } else {
                    modalauxMessage.innerHTML = "Error";
                }
            }
        });
    });
}

function change_combo_plate(sComboPlate) {

    var sBrand = (sComboPlate.split("-"))[2];
    var oCommandContainer1;
    var oCommandContainer2;

    oCommandContainer1 = $(".command-container .server_port_cellocator");
    oCommandContainer2 = $(".command-container .server_port_suntech");
    $(oCommandContainer1).removeClass("obfuscate");
    $(oCommandContainer2).removeClass("obfuscate");

    if (sBrand === "1") {
        $(oCommandContainer2).addClass("obfuscate");
    } else if (sBrand === "2") {
        $(oCommandContainer1).addClass("obfuscate");
    }


    loadCommandSave(true);
}
function change_multicombo_plate(sMultiComboPlate) {

}


function set_send_type(sSndTypeId) {
    $(".combo-selected").toggleClass("obfuscate");
    $(".multicombo-selected").toggleClass("obfuscate");
    var oMultiComboPlateGps = $("#multicombo-plate");
    var oComboPlateGps = $("#combo-plate");
    var oComboCommand = $("#combo-command");

    var oLabelParam1;
    var oLabelParam2;
    var oCommandContainer1 = $(".command-container .server_port_cellocator");
    var oCommandContainer2 = $(".command-container .server_port_suntech");
    oLabelParam1 = $(oCommandContainer1).find(".label-param");
    oLabelParam2 = $(oCommandContainer2).find(".label-param");

    $(oCommandContainer1).removeClass("obfuscate");
    $(oCommandContainer2).removeClass("obfuscate");

    $(oComboCommand).selectpicker('val', '');
    $(oMultiComboPlateGps).selectpicker('val', '');
    $(oComboPlateGps).selectpicker('val', '');


    if (sSndTypeId === "IND") {
        $(oCommandContainer2).addClass("obfuscate");

        $(oLabelParam1).html("Puerto : ");
        $(oLabelParam2).html("Puerto : ");

    } else if (sSndTypeId === "MUL") {

        $(oLabelParam1).html("Puerto Cellocator : ");
        $(oLabelParam2).html("Puerto Suntech : ");

        loadCommandSave(true);
    }
}

function add_command(sCmdTypeId) {
    if (sCmdTypeId !== "") {
        var hCmmnd = hCommandParams[sCmdTypeId];
        var sEditFields = "";
        var bEditable = false;
        var hComboOption;
        var sSelected;
        if (hCmmnd.params !== null) {
            bEditable = true;
            for (var sKey in hCmmnd.params) {
                sEditFields += "<div class='command-param " + hCmmnd.params[sKey].name + " " + (hCmmnd.params[sKey].addclass ? hCmmnd.params[sKey].addclass : "") + "'>\n\
                                            <span class = 'label label-param'>" + hCmmnd.params[sKey].label + " : </span>";
                if (hCmmnd.params[sKey].type === "number") {
                    sEditFields += "<input class='class-field class-" + hCmmnd.params[sKey].name + "' type='number' name='" + hCmmnd.params[sKey].name + "' min='" + hCmmnd.params[sKey].min + "' max='" + hCmmnd.params[sKey].max + "' value='" + hCmmnd.params[sKey].default + "' def-value='" + hCmmnd.params[sKey].default + "'> " + hCmmnd.params[sKey].units + "</div>";
                } else if (hCmmnd.params[sKey].type === "text") {
                    sEditFields += "<input class='class-field class-" + hCmmnd.params[sKey].name + "' type='text' name='" + hCmmnd.params[sKey].name + "' value='" + hCmmnd.params[sKey].default + "' maxlength=" + hCmmnd.params[sKey].maxlength + " def-value='" + hCmmnd.params[sKey].default + "'> " + hCmmnd.params[sKey].units + "</div>";
                } else if (hCmmnd.params[sKey].type === "combo") {
                    sEditFields += "<select class='class-field class-" + hCmmnd.params[sKey].name + "' name='" + hCmmnd.params[sKey].name + "' def-value='" + hCmmnd.params[sKey].default + "' " + (hCmmnd.params[sKey].onchange ? "onchange=\"" + hCmmnd.params[sKey].onchange + "\"" : "") + ">";
                    for (var i = 0; i < (hCmmnd.params[sKey].options).length; i++) {

                        hComboOption = hCmmnd.params[sKey].options[i];
                        sSelected = (hCmmnd.params[sKey].default === hComboOption.key_ ? "selected" : "");
                        sEditFields += "<option value = '" + hComboOption.key_ + "' " + sSelected + ">" + hComboOption.val_ + "</option>";
                    }
                    sEditFields += "</select></div>";
                } else if (hCmmnd.params[sKey].type === "ip") {

                    sEditFields += "<div class = 'class-field class-" + hCmmnd.params[sKey].name + "' type='ip' name='" + hCmmnd.params[sKey].name + "' style = 'display:contents'>";
                    sEditFields += "<input class='class-ip part-1' style='text-align:center;width: 15%;' type='number' name='" + hCmmnd.params[sKey].name + "-1' min='0' max='255' value='" + hCmmnd.params[sKey].default[0].def + "' def-value='" + hCmmnd.params[sKey].default[0].def + "'>.";
                    sEditFields += "<input class='class-ip part-2' style='text-align:center;width: 15%;' type='number' name='" + hCmmnd.params[sKey].name + "-2' min='0' max='255' value='" + hCmmnd.params[sKey].default[1].def + "' def-value='" + hCmmnd.params[sKey].default[1].def + "'>.";
                    sEditFields += "<input class='class-ip part-3' style='text-align:center;width: 15%;' type='number' name='" + hCmmnd.params[sKey].name + "-3' min='0' max='255' value='" + hCmmnd.params[sKey].default[2].def + "' def-value='" + hCmmnd.params[sKey].default[2].def + "'>.";
                    sEditFields += "<input class='class-ip part-4' style='text-align:center;width: 15%;' type='number' name='" + hCmmnd.params[sKey].name + "-4' min='0' max='255' value='" + hCmmnd.params[sKey].default[3].def + "' def-value='" + hCmmnd.params[sKey].default[3].def + "'>";
                    sEditFields += "</div>";
                    sEditFields += "</div>";
                }
            }
        }
        iCmdAdded++;
        var sCommandHtml = "<div id = 'cmd-" + iCmdAdded + "' data-cmdtype='" + sCmdTypeId + "' data-updated='false' class='command-container'><div class = 'command-header" + (bEditable ? " editable" : "") + "'>\n\
                                <div class = 'cedit-status " + (hCommandTypeLoadInfo.hasOwnProperty(sCmdTypeId) ? 'no-set' : '') + "' data-toggle='tooltip' title='parametros no configurados'></div>\n\
                                <div id ='name-cmd-" + iCmdAdded + "' data-cmd-num = " + iCmdAdded + " class = 'name'>" + hCmmnd.name + "</div>\n\
                                <div id ='del-cmd-" + iCmdAdded + "' data-cmd-num = " + iCmdAdded + " class='icon-cmd glyphicon glyphicon-remove'></div>\n\
                                " + (bEditable ? "<div id ='edit-cmd-" + iCmdAdded + "' data-cmd-num = " + iCmdAdded + " class='icon-cmd glyphicon glyphicon-chevron-up'>" : "") + "</div>\n\
                                </div>";
        if (bEditable) {
            sCommandHtml += "<div class='edit-area'>" + sEditFields + "</div></div>";
        } else {
            sCommandHtml += "</div>";
        }

        $(".command_list").append(sCommandHtml);
        $(".commands_added").scrollTop(1000000);
        $("#del-cmd-" + iCmdAdded).on('click', function () {
            $("#cmd-" + $(this).data("cmd-num")).remove();
        });
        $("#edit-cmd-" + iCmdAdded).on('click', function () {
            $("#cmd-" + $(this).data("cmd-num") + " .edit-area").toggleClass("obfuscate");
            $(this).toggleClass("glyphicon-chevron-up");
            $(this).toggleClass("glyphicon-chevron-down");
        });
        var oComboCommand = $("#combo-command");
        $(oComboCommand).selectpicker('val', '');

        var oComboSendType = $("#combo-send-type");

        if (sCmdTypeId === "23") {

            var oComboPlateGps = $("#combo-plate");

            var oCommandContainer1 = $(".command-container .server_port_cellocator");
            var oCommandContainer2 = $(".command-container .server_port_suntech");
            var oLabelParam1 = $(oCommandContainer1).find(".label-param");
            var oLabelParam2 = $(oCommandContainer2).find(".label-param");

            $(oCommandContainer1).removeClass("obfuscate");
            $(oCommandContainer2).removeClass("obfuscate");

            if ($(oComboSendType).val() === "MUL") {

                $(oLabelParam1).html("Puerto Cellocator : ");
                $(oLabelParam2).html("Puerto Suntech : ");

            } else if ($(oComboSendType).val() === "IND") {

                if ($(oComboPlateGps).val() !== "") {

                    var sBrand = ((oComboPlateGps.val()).split("-"))[2];
                    if (sBrand === "1") {
                        $(oCommandContainer2).addClass("obfuscate");
                    } else if (sBrand === "2") {
                        $(oCommandContainer1).addClass("obfuscate");
                    }

                } else {
                    $(oCommandContainer2).addClass("obfuscate");
                }
            }
        }
        loadCommandSave(false);
    }
}

function loadCommandSave(bRestore) {

    var oComboPlateGps = $("#combo-plate");
    var oComboSendType = $("#combo-send-type");
    var oCommandContainer;
    var oField;
    var oStatus;
    var oClassField;
    var sDefaultValue;
    oCommandContainer = $(".command-container");
    if (bRestore) {
        oCommandContainer = $(".command-container");
        for (var i = 0; i < oCommandContainer.length; i++) {
            $(oCommandContainer[i]).attr("data-updated", "false");

            oStatus = $(oCommandContainer[i]).find(".cedit-status");
            $(oStatus).removeClass("no-set");
            $(oStatus).removeClass("set");
            if (hCommandTypeLoadInfo.hasOwnProperty($(oCommandContainer[i]).attr("data-cmdtype"))) {
                $(oStatus).addClass("no-set");
                $(oStatus).attr("title", "parametros no configurados");
            }
            oField = $(oCommandContainer[i]).find(".class-field");
            for (var j = 0; j < oField.length; j++) {
                oClassField = $(oField[j]);
                sDefaultValue = $(oClassField).attr("def-value");
                $(oClassField).val(sDefaultValue);
            }
        }
    }

    if ($(oComboSendType).val() === "IND" && $(oComboPlateGps).val() !== null && oCommandContainer.length > 0) {
        var sComboPlateGps = $(oComboPlateGps).val();
        var sComboPlateGpsAux = sComboPlateGps.split("-");
        var aList;
        $.ajax({
            url: "/RDW1/loadValueCommandSaved",
            method: "POST",
            data: {fkGps: sComboPlateGpsAux[1], plate: sComboPlateGpsAux[0], fkGpsCommandType: '2,5,6,7,10,18,20,21,22,23,26,27'},
            success: function (data) {
                if (data.response === "OK") {
                    aList = data.list;
                    var sResponseJSON;
                    var fkGpsCommandType;
                    aList.forEach(function (a) {
                        fkGpsCommandType = a.fkCommandKey;
                        sResponseJSON = a.response;
                        sResponseJSON = JSON.parse(a.response);

                        /*Converting special commands to general commands*/
                        if (fkGpsCommandType === 20 || fkGpsCommandType === 21 || fkGpsCommandType === 23 || fkGpsCommandType === 26) {

                            var hCommandSplit = {};
                            if (fkGpsCommandType === 20) {
                                hCommandSplit[5] = {};
                                hCommandSplit[5].classRelation = {};
                                hCommandSplit[5].classRelation["time_driving"] = "time";
                                hCommandSplit[6] = {};
                                hCommandSplit[6].classRelation = {};
                                hCommandSplit[6].classRelation["time_parking"] = "time";
                                hCommandSplit[7] = {};
                                hCommandSplit[7].classRelation = {};
                                hCommandSplit[7].classRelation["distance"] = "distance";
                            } else if (fkGpsCommandType === 21) {
                                hCommandSplit[2] = {};
                                hCommandSplit[2].classRelation = {};
                                hCommandSplit[2].classRelation["geofence"] = "enabled";
                                hCommandSplit[10] = {};
                                hCommandSplit[10].classRelation = {};
                                hCommandSplit[10].classRelation["over_speed_limit"] = "speed";
                            } else if (fkGpsCommandType === 23) {
                                hCommandSplit[23] = {};
                                hCommandSplit[23].classRelation = {};
                                hCommandSplit[23].classRelation["server_port"] = "server_port_cellocator";
                            } else if (fkGpsCommandType === 26) {
                                hCommandSplit[22] = {};
                                hCommandSplit[22].classRelation = {};
                                hCommandSplit[22].classRelation["server_ip"] = "server_ip";
                                hCommandSplit[23] = {};
                                hCommandSplit[23].classRelation = {};
                                hCommandSplit[23].classRelation["server_port"] = "server_port_suntech";
                            }
                            for (var key in hCommandSplit) {
                                updateCommandInfo(key, sResponseJSON, hCommandSplit[key]);
                            }

                        } else {
                            updateCommandInfo(fkGpsCommandType, sResponseJSON, null);
                        }
                    });
                } else {
                }
            }
        });
    }
}

function updateCommandInfo(fkGpsCommandType, sResponseJSON, oCommandSplited) {

    var oField;
    var oStatus;
    var sIp;

    var oCommandContainer = $(".command-container[data-cmdtype='" + fkGpsCommandType + "'][data-updated='false']");
    for (var i = 0; i < oCommandContainer.length; i++) {
        oStatus = $(oCommandContainer[i]).find(".cedit-status");
        $(oStatus).removeClass("no-set");
        $(oStatus).removeClass("set");
        $(oStatus).addClass("set");
        $(oStatus).attr("title", "parametros configurados");

        if (sResponseJSON) {
            for (var key in sResponseJSON) {

                if (oCommandSplited === null) {
                    oField = $(oCommandContainer[i]).find(".class-" + key);
                } else {
                    oField = $(oCommandContainer[i]).find(".class-" + oCommandSplited.classRelation[key]);
                }
                if (oField !== null) {
                    if (key === "server_ip") {
                        sIp = sResponseJSON[key];
                        sIp = sIp.split(".");
                        $(oField).find("input.part-1").val(sIp[0]);
                        $(oField).find("input.part-2").val(sIp[1]);
                        $(oField).find("input.part-3").val(sIp[2]);
                        $(oField).find("input.part-4").val(sIp[3]);
                    } else if (key === "access") {
                        if (sResponseJSON[key] === "server_domain") {
                            ($(oCommandContainer[i]).find(".command-param.server_ip")).removeClass("obfuscate");
                            ($(oCommandContainer[i]).find(".command-param.server_ip")).addClass("obfuscate");
                            ($(oCommandContainer[i]).find(".command-param.server_domain")).removeClass("obfuscate");
                        }
                        if (sResponseJSON[key] === "server_ip") {
                            ($(oCommandContainer[i]).find(".command-param.server_domain")).removeClass("obfuscate");
                            ($(oCommandContainer[i]).find(".command-param.server_domain")).addClass("obfuscate");
                            ($(oCommandContainer[i]).find(".command-param.server_ip")).removeClass("obfuscate");
                        }
                        $(oField).val(sResponseJSON[key]);
                    } else {
                        $(oField).val(sResponseJSON[key]);
                    }
                }
            }
            $(oCommandContainer[i]).attr("data-updated", "true");
        }
    }
}

function fModalController() {
    modalaux = document.getElementById('modalaux-id');
    modalauxClose = document.getElementsByClassName("modalaux-close")[0];
    modalauxMessage = document.getElementsByClassName("modalaux-message")[0];
    modalauxContent = document.getElementsByClassName("modalaux-content")[0];

    modalauxClose.onclick = function () {
        modalaux.style.display = "none";
    };
    window.onclick = function (event) {
        if (event.target === modalaux) {
            modalaux.style.display = "none";
        }
    };
}

var hCommandParams = {};
hCommandParams[1] = {};
hCommandParams[1].name = "GEO_RUTA";
hCommandParams[1].reset = true;
hCommandParams[2] = {};
hCommandParams[2].name = "ACTIVAR_GEO_PUNTOS";
hCommandParams[2].reset = true;
hCommandParams[2].params = {
    "param1": {"name": "enabled", "label": "Activos", "units": "%", "type": "combo", "options": [{"key_": true, "val_": "Si"}, {"key_": false, "val_": "No"}], "default": false}};
hCommandParams[3] = {};
hCommandParams[3].name = "SERIAL_DESPACHO";
hCommandParams[3].reset = false;
hCommandParams[4] = {};
hCommandParams[4].name = "SERIAL_CHAT";
hCommandParams[4].reset = false;
hCommandParams[5] = {};
hCommandParams[5].name = "MDFCR_REP_TIEMPO_MOVIL_ON";
hCommandParams[5].reset = true;
hCommandParams[5].params = {"param1": {"name": "time", "label": "Tiempo", "units": "Seg", "type": "number", "default": 900, "min": 1, "max": 3600}};
hCommandParams[6] = {};
hCommandParams[6].name = "MDFCR_REP_TIEMPO_MOVIL_OFF";
hCommandParams[6].reset = true;
hCommandParams[6].params = {"param1": {"name": "time", "label": "Tiempo", "units": "Min", "type": "number", "default": 60, "min": 1, "max": 60}};
hCommandParams[7] = {};
hCommandParams[7].name = "MDFCR_REP_DISTANCIA";
hCommandParams[7].reset = true;
hCommandParams[7].params = {"param1": {"name": "distance", "label": "Distancia", "units": "Mts", "type": "number", "default": 800, "min": 0, "max": 10000}};
hCommandParams[8] = {};
hCommandParams[8].name = "MOVIL_APAGADO";
hCommandParams[8].reset = false;
hCommandParams[8].params = null;
hCommandParams[9] = {};
hCommandParams[9].name = "MOVIL_ENCENDIDO";
hCommandParams[9].reset = false;
hCommandParams[9].params = null;
hCommandParams[10] = {};
hCommandParams[10].name = "MDFCR_VELOCIDAD_LIMITE";
hCommandParams[10].reset = true;
hCommandParams[10].params = {"param1": {"name": "speed", "label": "Lim. Vel", "units": "Km/h", "type": "combo", "options": [{"key_": 60, "val_": 60}, {"key_": 80, "val_": 80}, {"key_": 100, "val_": 100}], "default": 60}};
hCommandParams[11] = {};
hCommandParams[11].name = "SLCTD_ESTADO";
hCommandParams[11].reset = false;
hCommandParams[11].params = null;
hCommandParams[12] = {};
hCommandParams[12].name = "RESET_GPS";
hCommandParams[12].reset = false;
hCommandParams[12].params = null;
hCommandParams[13] = {};
hCommandParams[13].name = "SLCTD_CONTADOR";
hCommandParams[13].reset = false;
hCommandParams[13].params = null;
hCommandParams[14] = {};
hCommandParams[14].name = "SLCTD_ESTADO_SENSORES";
hCommandParams[14].reset = false;
hCommandParams[14].params = null;
hCommandParams[15] = {};
hCommandParams[15].name = "SLCTD_ESTADO_VOLTAJES";
hCommandParams[15].reset = false;
hCommandParams[15].params = null;
hCommandParams[16] = {};
hCommandParams[16].name = "SLCTD_PARAM_CONTADOR";
hCommandParams[16].reset = false;
hCommandParams[16].params = null;
hCommandParams[17] = {};
hCommandParams[17].name = "MDFCR_CONTADOR";
hCommandParams[17].reset = false;
hCommandParams[17].params = {
    "param1": {"name": "numeration", "label": "Numeracion", "units": "", "type": "number", "default": 0, "min": 0, "max": 999999},
    "param2": {"name": "total_day", "label": "Total dia", "units": "", "type": "number", "default": 0, "min": 0, "max": 999999},
    "param3": {"name": "entries", "label": "Entradas", "units": "", "type": "number", "default": 0, "min": 0, "max": 999999},
    "param4": {"name": "outs", "label": "Salidas", "units": "", "type": "number", "default": 0, "min": 0, "max": 999999}
};
hCommandParams[18] = {};
hCommandParams[18].name = "MDFCR_PARAM_CONTADOR";
hCommandParams[18].reset = false;
hCommandParams[18].params = {
    "param1": {"name": "volume", "label": "Volumen", "units": "%", "type": "combo", "options": [{"key_": 0, "val_": 0}, {"key_": 25, "val_": 25}, {"key_": 50, "val_": 50}, {"key_": 100, "val_": 100}], "default": 50},
    "param2": {"name": "door_number", "label": "Numero Puertas", "units": "", "type": "combo", "options": [{"key_": 1, "val_": 1}, {"key_": 2, "val_": 2}], "default": 1},
    "param3": {"name": "server_ip", "label": "IP servidor", "units": "", "type": "ip", "default": [{"def": "192"}, {"def": "168"}, {"def": "2"}, {"def": "1"}]},
    "param4": {"name": "server_port", "label": "Puerto servidor", "units": "", "type": "number", "default": 3000, "min": 0, "max": 65000},
    "param5": {"name": "discount", "label": "Descuento", "units": "$", "type": "combo", "options": [{"key_": 0, "val_": 0}, {"key_": 1, "val_": 25}, {"key_": 2, "val_": 50}, {"key_": 3, "val_": 100}], "default": 1},
    "param6": {"name": "plate", "label": "Placa", "units": "", "type": "text", "maxlength": -1, "default": "ABC123"},
    "param7": {"name": "utc", "label": "UTC", "units": "", "type": "number", "default": -5, "min": -12, "max": 12},
    "param8": {"name": "speed_limit", "label": "Lim. Vel", "units": "Km/h", "type": "combo", "options": [{"key_": 60, "val_": 60}, {"key_": 80, "val_": 80}, {"key_": 100, "val_": 100}], "default": 60}
};
hCommandParams[19] = {};
hCommandParams[19].name = "RESET_CONTADOR";
hCommandParams[19].params = null;
hCommandParams[20] = {};
hCommandParams[20].name = "MDFCR_REP_TIEMPO_DISTANCIA";
hCommandParams[20].reset = false;
hCommandParams[20].params = {
    "param1": {"name": "time_driving", "label": "Timpo On", "units": "Seg", "type": "number", "default": 900, "min": 1, "max": 3600},
    "param2": {"name": "time_parking", "label": "Timpo Off", "units": "Seg", "type": "number", "default": 3600, "min": 1, "max": 3600},
    "param3": {"name": "distance", "label": "Distancia", "units": "Mts", "type": "number", "default": 800, "min": 1, "max": 10000}
};
hCommandParams[21] = {};
hCommandParams[21].name = "MDFCR_PARAM_SERVICIO";
hCommandParams[21].reset = false;
hCommandParams[21].params = {
    "param1": {"name": "over_speed_limit", "label": "Lim. Vel", "units": "Km/h", "type": "combo", "options": [{"key_": 60, "val_": 60}, {"key_": 80, "val_": 80}, {"key_": 100, "val_": 100}], "default": 60},
    "param2": {"name": "geofence", "label": "Geozonas On", "units": "", "type": "combo", "options": [{"key_": true, "val_": "Si"}, {"key_": false, "val_": "No"}], "default": false},
    "param3": {"name": "data_log", "label": "Backup On", "units": "", "type": "combo", "options": [{"key_": true, "val_": "Si"}, {"key_": false, "val_": "No"}], "default": false}
};
hCommandParams[22] = {};
hCommandParams[22].name = "MDFCR_CONEXION_IP_DOMINIO";
hCommandParams[22].reset = true;
hCommandParams[22].params = {
    "param1": {"name": "access", "label": "Tipo", "units": "", "type": "combo", "options": [{"key_": "server_ip", "val_": "IP"}, {"key_": "server_domain", "val_": "Dominio"}], "default": "server_ip", "onchange": "onchangeNetworkParam(this);"},
    "param2": {"name": "server_ip", "label": "IP", "units": "", "type": "ip", "default": [{"def": "0"}, {"def": "0"}, {"def": "0"}, {"def": "0"}]},
    "param3": {"name": "server_domain", "label": "Dominio", "units": "", "type": "text", "addclass": "obfuscate", "maxlength": 31, "default": "regisdataweb.com"}
};
hCommandParams[23] = {};
hCommandParams[23].name = "MDFCR_CONEXION_PUERTO";
hCommandParams[23].reset = true;
hCommandParams[23].params = {
    "param1": {"name": "server_port_cellocator", "label": "Puerto", "label_multi": "Puerto cellocator", "units": "", "type": "number", "default": 6002, "min": 1024, "max": 49151},
    "param2": {"name": "server_port_suntech", "label": "Puerto", "label_multi": "Puerto suntech", "units": "", "type": "number", "addclass": "obfuscate", "default": 6003, "min": 1024, "max": 49151}
};

hCommandParams[24] = {};
hCommandParams[24].name = "MDFCR_CONEXION_IP_DOMINIO_PARTE_1";
hCommandParams[24].reset = true;
hCommandParams[24].params = {
    "param1": {"name": "access", "label": "Tipo", "units": "", "type": "combo", "options": [{"key_": "server_ip", "val_": "IP"}, {"key_": "server_domain", "val_": "Dominio"}], "default": "server_ip", "onchange": "onchangeNetworkParam(this);"},
    "param2": {"name": "server_ip", "label": "IP", "units": "", "type": "ip", "default": [{"def": "0"}, {"def": "0"}, {"def": "0"}, {"def": "0"}]},
    "param3": {"name": "server_domain", "label": "Dominio", "units": "", "type": "text", "addclass": "obfuscate", "maxlength": 31, "default": "regisdataweb.com"}
};
hCommandParams[25] = {};
hCommandParams[25].name = "MDFCR_CONEXION_IP_DOMINIO_PARTE_2";
hCommandParams[25].reset = true;
hCommandParams[25].params = {
    "param1": {"name": "access", "label": "Tipo", "units": "", "type": "combo", "options": [{"key_": "server_ip", "val_": "IP"}, {"key_": "server_domain", "val_": "Dominio"}], "default": "server_ip", "onchange": "onchangeNetworkParam(this);"},
    "param2": {"name": "server_ip", "label": "IP", "units": "", "type": "ip", "default": [{"def": "0"}, {"def": "0"}, {"def": "0"}, {"def": "0"}]},
    "param3": {"name": "server_domain", "label": "Dominio", "units": "", "type": "text", "addclass": "obfuscate", "maxlength": 31, "default": "regisdataweb.com"}
};
hCommandParams[31] = {};
hCommandParams[31].name = "MDFCR_CONEXION_IP_DOMINIO_PARTE_3";
hCommandParams[31].reset = true;
hCommandParams[31].params = {
    "param1": {"name": "access", "label": "Tipo", "units": "", "type": "combo", "options": [{"key_": "server_ip", "val_": "IP"}, {"key_": "server_domain", "val_": "Dominio"}], "default": "server_ip", "onchange": "onchangeNetworkParam(this);"},
    "param2": {"name": "server_ip", "label": "IP", "units": "", "type": "ip", "default": [{"def": "0"}, {"def": "0"}, {"def": "0"}, {"def": "0"}]},
    "param3": {"name": "server_domain", "label": "Dominio", "units": "", "type": "text", "addclass": "obfuscate", "maxlength": 31, "default": "regisdataweb.com"}
};

hCommandParams[26] = {};
hCommandParams[26].name = "MDFCR_PARAM_RED";
hCommandParams[26].reset = true;
hCommandParams[26].params = {
    "param1": {"name": "server_ip", "label": "IP", "units": "", "type": "ip", "default": [{"def": "190"}, {"def": "147"}, {"def": "175"}, {"def": "47"}]},
    "param2": {"name": "server_port_cellocator", "label": "Puerto", "units": "", "type": "number", "default": 0, "min": 0, "max": 65000},
    "param3": {"name": "server_port_suntech", "label": "Puerto", "units": "", "type": "number", "default": 0, "min": 0, "max": 65000}
};
hCommandParams[27] = {};
hCommandParams[27].name = "MDFCR_CONEXION_APN";
hCommandParams[27].reset = true;
hCommandParams[27].params = {
    "param1": {"name": "full_apn", "label": "APN", "units": "", "type": "text", "default": "internet.comcel.com.co"}
};
hCommandParams[28] = {};
hCommandParams[28].name = "MDFCR_CONEXION_APN_PARTE_1";
hCommandParams[28].reset = true;
hCommandParams[28].params = {
    "param1": {"name": "full_apn", "label": "APN", "units": "", "type": "text", "default": "internet.comcel.com.co"}
};
hCommandParams[29] = {};
hCommandParams[29].name = "MDFCR_CONEXION_APN_PARTE_2";
hCommandParams[29].reset = true;
hCommandParams[29].params = {
    "param1": {"name": "full_apn", "label": "APN", "units": "", "type": "text", "default": "internet.comcel.com.co"}
};
hCommandParams[30] = {};
hCommandParams[30].name = "MDFCR_CONEXION_APN_PARTE_3";
hCommandParams[30].reset = true;
hCommandParams[30].params = {
    "param1": {"name": "full_apn", "label": "APN", "units": "", "type": "text", "default": "internet.comcel.com.co"}
};

function onchangeNetworkParam(this_) {

    var oParent = $(this_).closest(".command-container");
    $($(oParent).find(".command-param.server_domain")[0]).toggleClass("obfuscate");
    $($(oParent).find(".command-param.server_ip")[0]).toggleClass("obfuscate");
}

function formatDateToHeader(sDate) {
    var hMonths = {};
    hMonths["01"] = "ENE";
    hMonths["02"] = "FEB";
    hMonths["03"] = "MAR";
    hMonths["04"] = "ABR";
    hMonths["05"] = "MAY";
    hMonths["06"] = "JUN";
    hMonths["07"] = "JUL";
    hMonths["08"] = "AGO";
    hMonths["09"] = "SEP";
    hMonths["10"] = "OCT";
    hMonths["11"] = "NOV";
    hMonths["12"] = "DIC";
    var aDate = sDate.split(" ");
    var aDate_aux = (aDate[0]).split("-");

    var sMonth = hMonths[aDate_aux[1]];
    var sDay = aDate_aux[2];
    var resDate = sMonth + " " + sDay + " " + ((aDate[1]).substring(0, (aDate[1]).length - 5));
    return resDate;
}

var hTranslate = {};
hTranslate ["time"] = "tiempo";
hTranslate ["distance"] = "distancia";
hTranslate ["volume"] = "volumen";
hTranslate ["speed_limit"] = "lim_vel";
hTranslate ["door_number"] = "numero_puertas";
hTranslate ["server_ip"] = "ip_servidor";
hTranslate ["server_port"] = "puerto_servidor";
hTranslate ["server_port_suntech"] = "puerto_servidor";
hTranslate ["server_port_cellocator"] = "puerto_servidor";
hTranslate ["discount"] = "descuento";
hTranslate ["outs"] = "salidas";
hTranslate ["numeration"] = "numeracion";
hTranslate ["entries"] = "entradas";
hTranslate ["total_day"] = "total_dia";
hTranslate ["voltage_in"] = "voltaje_entrada";
hTranslate ["voltage_internal_battery"] = "voltaje_bateria_interna";
hTranslate ["voltage_sensors"] = "voltaje_sensores";
hTranslate ["switch_box"] = "switch_caja";
hTranslate ["plate"] = "placa";
hTranslate ["enabled"] = "Activados";
hTranslate ["time_driving"] = "tiempo on";
hTranslate ["time_parking"] = "tiempo off";
hTranslate ["speed"] = "velocidad";
hTranslate ["over_speed_limit"] = "limite_velocidad";
hTranslate ["data_log"] = "back_up";
hTranslate ["geofence"] = "geozonas";
hTranslate ["full_apn"] = "apn";
hTranslate ["part_apn"] = "bloque apn";
hTranslate ["server_domain"] = "dominio";
hTranslate ["server_domain_part"] = "bloque dominio";
hTranslate ["access"] = "acceso";



