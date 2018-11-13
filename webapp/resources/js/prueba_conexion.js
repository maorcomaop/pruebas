var sinConexion = null;
var conConexion = null;
var url = window.location.href;

if (url.endsWith('/iniciarSesion'))
{    
    conConexion = setInterval(conexionAInternet(), 5000);    
}
else
{
   sinConexion = setInterval(conexionSinInternet, 5000);
}
function conexionAInternet(){        
    $.ajax({
  url: '/RDW1/conexionInternet',  
  type:'POST',
  beforeSend: function (xhr) {            
       $(".offline-ui").removeClass("offline-ui-up");
       clearInterval(sinConexion);
   },        
  success: function(data) {            
      if ($.trim(data) === '1'){
          // console.log("hay conexion");
        $(".offline-ui").removeClass("offline-ui-down");        
        $(".offline-ui").addClass("offline-ui-up");                  
        $(".offline-ui").show();
    }
    else{
        sinConexion = setInterval(conexionSinInternet, 5000);
    }
},
    error: function(data) {}
}).done(function() {    
    clearInterval(conConexion);    
    conConexion = null;
});
}
/*****************************************/
function conexionSinInternet(){    
    $.ajax({
  url: '/RDW1/conexionInternet',  
  type:'POST',
  beforeSend: function (xhr) {            
            $(".offline-ui").removeClass("offline-ui-down");            
            clearInterval(conConexion);},
  success: function(data) {      
    if ($.trim(data) === '1') {
//        console.log("Hay conexion");
    }
    else{
//        console.log("No hay conexion");
        $(".offline-ui").removeClass("offline-ui-up");
        $(".offline-ui").addClass("offline-ui-down");                  
    }},
    error: function(data) {
    console.log('Error');    
  }
}).done(function() {
         
});
}

