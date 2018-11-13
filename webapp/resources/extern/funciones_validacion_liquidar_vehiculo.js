$(function() { 	
        
        $(".botonLq").click(function(){  
		$(".error1").fadeOut().remove();                		
        
                if ($("#placa_vehiculo").val() === "") 
                {            
                    $("#placa_vehiculo").focus().after('<span class="error1">Seleccione el vehiculo</span>');  
                    return false;  
                }
    });  
    
    
	$("#placa_vehiculo, #numero_interno").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {  						
                        $(".error1").fadeOut().remove();
			return false;  
		}          
	});		
});