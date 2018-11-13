$( document ).ready(function() {
                               
   $('.selectpicker').selectpicker({
       style: 'btn-primary',
       size: 4,       
       liveSearch: true});
    $('#f_v_edit').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});  
    
        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);
      
    //cargarCDA();
    //ciudadCDA();   
    
    

});/*FIN DOCUMENT READY*/
   
  function cargarCDA(){
    $.post("/RDW1/consultaTodosCentroDiagnostico", {id:1},
                function (result) {
                    //console.log(result);
                    $("#fk_cd").html(result);
                    $("#fk_cd").selectpicker("refresh");
    });
}

function ciudadCDA(){
    $.post("/RDW1/todasLasCiudadesCda", {id:0},
                function (result) {
                    //console.log(result);
                    $("#fk_c").html(result);
                    $("#fk_c").selectpicker("refresh");
    });
}


/**************************************************************************/
function cargarCDA_edit(dato1){
    $.post("/RDW1/consultaTodosCentroDiagnostico", {id:1},
                function (result) {
                    //console.log(result);
                    $("#fk_cd_edit").append($.trim(result));
                    $("#fk_cd_edit option[value="+dato1+"]").attr("selected",true);
                    $("#fk_cd_edit").selectpicker("refresh");
    });
}

function ciudadCDA_edit(dato){
    $.post("/RDW1/todasLasCiudadesCda", {id:0},
                function (result) {
                    //console.log(result);
                    $("#fk_cda_edit").append($.trim(result));
                    $("#fk_cda_edit option[value="+dato+"]").attr("selected",true);
                    $("#fk_cda_edit").selectpicker("refresh");
    });
}
