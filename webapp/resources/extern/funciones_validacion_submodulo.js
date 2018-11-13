$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        var numbers = /^([0-9])/;	
          var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
	
        $(".botonSubModulo").click(function(){  
		$(".error1").fadeOut().remove();
                $(".error").fadeOut().remove();
		
           
        if ($("#mod").val() === "0") 
        {  
            $("#mod").focus().after('<span class="error1">Seleccione un modulo</span>');  
            $("#mod").select();
            return false;  
	}
        
        if ($("#nameSubModule").val() === "") 
        {  
            $("#nameSubModule").focus().after('<span class="error">Ingrese nombre del submodulo</span>');  
            $("#nameSubModule").select();
            return false;  
	}
        else if ( ($("#nameSubModule").val() !== "") && (!n.test($("#nameSubModule").val())) )
        {   
            $("#nameSubModule").focus().after('<span class="error">Ingrese nombre sin numeros o simbolos extrañ&oacute;s</span>');                          
            $("#nameSubModule").select();
            return false;            
        } 
        
        
        if ($("#nameOption").val() === "") 
        {  
            $("#nameOption").focus().after('<span class="error">Ingrese nombre del submodulo</span>');  
            $("#nameOption").select();
            return false;  
	}
        else if ( ($("#nameOption").val() !== "") && (!n.test($("#nameOption").val())) )
        {   
            $("#nameOption").focus().after('<span class="error">Ingrese nombre sin numeros o simbolos extrañ&oacute;s</span>');                          
            $("#nameOption").select();
            return false;            
        }
                
    });     
    
	$("#mod, #nameSubModule, #nameOption").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error1').fadeOut();
                        $(".error").fadeOut();
			return false;  
		}  
	});        
        
});