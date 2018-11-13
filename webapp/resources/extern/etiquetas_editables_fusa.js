var defaultText = "complete el campo";
function endEdit(e) {
    var input = $(e.target),
        label = input && input.prev();        
    label.text(input.val() === '' ? defaultText : input.val());
    input.hide();
    label.show();    
}

$('.clickeditF').hide().focusout(endEdit).keyup(function (e) {
    if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
        endEdit(e);
        console.log("-->pass "+$("#pasF2").text()+"---"+$("#id_etqF").val());
        $.post("/RDW1/actualizarEtiquetasFusa", { ep1:'', ep2:$("#pasF2").text(), ep3:'', 
                                              ep4:'no aplica', ep5:'no aplica', et1:$("#totalF1").text(), 
                                              et2:$("#totalF2").text(), et3:$("#totalF3").text(), et4:$("#totalF4").text(), 
                                              et5:$("#totalF5").text(), et6:$("#totalF6").text(), et7:$("#totalF7").text(), 
                                              et8:$("#totalF8").text(), id:$("#id_etqF").val()},
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