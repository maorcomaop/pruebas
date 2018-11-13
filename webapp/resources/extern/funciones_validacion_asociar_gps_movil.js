$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;
        var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var intNum1 = /^([0-9]*)*$/;
        
        $(".botonMapGps").click(function(){		
                $(".error2").fadeOut().remove();		
                                
            if ($("#cod_gps_asociar_rec").val() === "") 
            {                      
                $("#cod_gps_asociar_rec").focus().before('<span class="error2">Seleccione el codigo del GPS</span>');                  
                return false;              
            }
            
            if ($("#numero_celular_gps").val() === "") 
            {                      
                $("#numero_celular_gps").focus().before('<span class="error2">Seleccione el numero celular</span>');                  
                return false;              
            }  
            
            if ($("#vh2").val() === "") 
            {            
                $("#vh2").focus().before('<span class="error2">Seleccione el vehiculo</span>');           
                return false;  
            }            
            
    });      

$("#cod_gps_asociar_rec, #numero_celular_gps, #vh2").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {			
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});

