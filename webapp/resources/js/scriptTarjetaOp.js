$( document ).ready(function() {
   
   $('.selectpicker').selectpicker({
       style: 'btn-primary',
       size: 4,       
       liveSearch: true});    
   window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 4000);   
   
    //ciudadCE();    

});/*FIN DOCUMENT READY*/
   
 
function cargarCE_edit(dato){
    $.post("/RDW1/todosLosCentroExpedicion", {id:1},
                function (result) {
                    //console.log(result);
                    $("#fk_cx_edit").append($.trim(result));
                    $("#fk_cx_edit option[value="+dato+"]").attr("selected",true);
                    $("#fk_cx_edit").selectpicker("refresh");
    });
}
function ciudadCE_edit(dato){
    $.post("/RDW1/todasLasCiudadesCe", {id:0},
                function (result) {
                    //console.log(result);
                    $("#fk_ce_edit").append($.trim(result));
                    $("#fk_ce_edit option[value="+dato+"]").attr("selected",true);
                    $("#fk_ce_edit").selectpicker("refresh");
    });
}