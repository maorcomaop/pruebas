$( document ).ready(function() {
                               
   $('.selectpicker').selectpicker({
       style: 'btn-primary',
       size: 4,       
       liveSearch: true});    
      
    cargarCDA();  

});/*FIN DOCUMENT READY*/
   
  function cargarCDA(){
    $.post("/RDW1/todasLasCiudadesCda", {id:1},
                function (result) {
                    //console.log(result);
                    $("#id_gps_sim_rec").html(result);
                    $("#id_gps_sim_rec").selectpicker("refresh");
    });
}

 