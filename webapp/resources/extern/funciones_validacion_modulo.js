$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
	
        $(".botonModulo").click(function(){  
		$(".error").fadeOut().remove();
                $(".error3").fadeOut().remove();
		
                
        if ($("#name").val() === "") {  
            $("#name").focus().after('<span class="error">Ingrese el nombre del modulo</span>');  
            $("#name").select();
            return false;  
	}else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {
            $("#name").focus().after('<span class="error">No es un nombre de modulo valido, Ingrese nombre del modulo nuevamente</span>');                          
            $("#name").select();
            return false;            
        }
                
    });  
    
    
	$("#name").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
                        $(".error3").fadeOut();
			return false;  
		}  
	});        
        
});