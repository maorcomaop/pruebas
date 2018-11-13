
$(function() { 	       
        $(".botonMovilEditGps").click(function(){		
              var mensaje;      
              var cadena="";
            if ($("#hrdwr_edit").val() === "") 
            {                      
                cadena += "Hadware, ";
            }
            if ($("#cod_gps_rec").val() === "") 
            {                      
                cadena += "Gps, ";
            }   
            if ($("#num_cel_gps_rec").val() === "") 
            {                      
                cadena += "N\u00FAmero celular";
            } 
            
            
            if (typeof cadena === 'undefined' || cadena === null || cadena === ''){
                console.log("Esta vacía");
            }
            else{
                mensaje = confirm("La siguiente informaci\u00F3n no se encuentra: "+cadena+" \n \u00BF Desea Continuar sin agregar informaci\u00F3n \u003F");                
                if (mensaje) {
                console.log("¡Gracias por aceptar!");
                return true;
                } else {
                    return false;
                }
            }   
            
    });      
});

