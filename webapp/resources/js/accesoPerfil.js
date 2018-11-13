$(document).ready(function () {
    window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 4000);
    $(".app_checkbox input").click(function () {
        var checkBox = null;
        var appGrupos = $(this).parents(".app_grupo");
        var firstParent = appGrupos[0];
        var childrenCB = $(firstParent).find("input[type='checkbox']");
        var bCheckedState = this.checked;
        for (var i = 1; i < childrenCB.length; i++) {
            checkBox = $(childrenCB[i]);
            $(checkBox).each(function () {
                if (!($(this).attr("access_type") === "6" && $(this).attr("disabled") === "disabled")) {
                    this.checked = bCheckedState;
                }
            });
        }

        // access_type = 1  (access to complete page)
        var bUnchecking = true;
        if (!bCheckedState && $(this).attr("access_type") === "1") {
//        if (!bCheckedState) {
            for (var i = 0; i < appGrupos.length; i++) {
                bUnchecking = true;
                var siblingsCB = $(appGrupos[i]).siblings();
                for (var j = 0; j < siblingsCB.length; j++) {
                    checkBox = $(siblingsCB[j]).find("input[type='checkbox']")[0];
                    $(checkBox).each(function () {
                        if (this.checked) {
                            bUnchecking = false;
                        }
                    });
                    if (!bUnchecking) {
                        break;
                    }
                }
                if (bUnchecking) {
                    if (appGrupos[i + 1]) {
                        checkBox = $(appGrupos[i + 1]).find("input[type='checkbox']")[0];
                        $(checkBox).each(function () {
                            this.checked = false;
                        });
                    }
                } else {
                    break;
                }
            }
        } else {
            for (var i = 1; i < appGrupos.length; i++) {
                checkBox = $(appGrupos[i]).find("input[type='checkbox']")[0];
                $(checkBox).each(function () {
                    this.checked = true;
                });
            }
        }
    });
    $(".app_top , .app_bottom").click(function () {
        $(this).toggleClass("obfuscate");
        var firstParent = $(this).parents(".app_grupo")[0];
        var container = $(firstParent).find(".app_contenedor")[0];
        $(container).toggleClass("obfuscate");
        var deployButton = null;
        if ($(this).hasClass("app_top")) {
            deployButton = $(firstParent).find(".app_bottom")[0];
        } else if ($(this).hasClass("app_bottom")) {
            deployButton = $(firstParent).find(".app_top")[0];
        }

        $(deployButton).toggleClass("obfuscate");
    });

    $('#profile-form').submit(function () {
        var checkBox = "";
        var divContainer = $("#treeDiv1");
        var childrenCB = $(divContainer).find("input[type='checkbox']");
        var bUnchecked = true;
        for (var i = 0; i < childrenCB.length; i++) {
            checkBox = $(childrenCB[i]);
            $(checkBox).removeAttr("disabled");
            $(checkBox).each(function () {
                if (this.checked) {
                    bUnchecked = false;
                }
            });
        }
        if (bUnchecked) {
            $("#for-alert").append("<div class='alert alert-danger col-sm-4'><button type = 'button' class = 'close' data-dismiss = 'alert' >&times; </button><strong> Seleccione al menos un acceso </strong></div>");
            return false;
        }
    });
});