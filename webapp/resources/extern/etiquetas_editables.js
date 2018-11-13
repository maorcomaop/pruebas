var defaultText = "complete el campo";
function endEdit(e) {
    var input = $(e.target),
        label = input && input.prev();        
    label.text(input.val() === '' ? defaultText : input.val());
    input.hide();
    label.show();    
}

$('.clickedit').hide().focusout(endEdit).keyup(function (e) {
    if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
        endEdit(e);
        /*$.post("/RDW1/actualizarEtiquetas", { ep1:$("#pasN1").text(), ep2:$("#pasN2").text(), ep3:$("#pasN3").text(), 
                                              ep4:'no aplica', ep5:'no aplica', et1:$("#totalN1").text(), 
                                              et2:"", et3:$("#totalN2").text(), et4:$("#totalN3").text(), 
                                              et5:$("#totalN4").text(), et6:$("#totalN5").text(), id:$("#id_etqN").val()},
                function (result) {
                    var obj = jQuery.parseJSON( $.trim(result) );                       
                    //console.log("--->"+obj.id);
                });*/
        $.post("/RDW1/actualizarEtiquetas", { ep1:'', ep2:$("#pasN2").text(), ep3:'', 
                                              ep4:'no aplica', ep5:'no aplica', et1:$("#totalN1").text(), 
                                              et2:$("#totalN2").text(), et3:$("#totalN3").text(), et4:'', 
                                              et5:$("#totalN4").text(), et6:$("#totalN5").text(), et7:$("#totalN6").text(), id:$("#id_etqN").val()},
                function (result) {
                    var obj = jQuery.parseJSON( $.trim(result) );                       
                    //console.log("--->"+obj.id);
                });
        return false;
    } else {
        return true;
    }
}).prev().click(function () {    
    $(this).hide();
    $(this).next().val($(this).text());
    $(this).next().show().focus();
});


//