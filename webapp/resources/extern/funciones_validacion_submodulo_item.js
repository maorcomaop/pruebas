$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	       
         var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
         
        $(".botonSubModuloItem").click(function(){  
		$(".error1").fadeOut().remove();
                $(".error").fadeOut().remove();
		
           
        if ($("#mod").val() === "0") 
        {  
            $("#mod").focus().after('<span class="error1">Seleccione un modulo</span>');  
            $("#mod").select();
            return false;  
	}
        
         if ($("#submod").val() === "0") 
        {  
            $("#submod").focus().after('<span class="error1">Seleccione un submodulo</span>');  
            $("#submod").select();
            return false;  
	}
       
        if ($("#nameSubModuleItem").val() === "") 
        {  
            $("#nameSubModuleItem").focus().after('<span class="error">Ingrese nombre del item</span>');  
            $("#nameSubModuleItem").select();
            return false;  
	}
        else if ( ($("#nameSubModuleItem").val() !== "") && (!n.test($("#nameSubModuleItem").val())) )
        { 
            $("#nameSubModuleItem").focus().after('<span class="error">Ingrese nombre sin numeros o simbolos extrañ&oacute;s</span>');                          
            $("#nameSubModuleItem").select();
            return false;            
        } 
        
                
                
    });     
    
	$("#mod, #nameSubModuleItem").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error1').fadeOut();
                        $(".error").fadeOut();
			return false;  
		}  
	});        
        
});