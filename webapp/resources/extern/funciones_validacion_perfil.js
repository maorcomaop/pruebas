$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
      
	
        $(".boton").click(function(){  
		$(".error").fadeOut().remove();
        
        
        if ($("#name").val() === "")
        {
            $("#name").focus().after('<span class="error">Ingrese nombre del nuevo perfil</span>');  
            $("#name").select();
            return false;
        }
        else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {
            $("#name").focus().after('<span class="error">Ingrese nombre del nuevo perfil sin numero o simbolos extrañ&oacute;s</span>');                          
            $("#name").select();
            return false;            
        }
        
            
        
        
        
            
        if ($("#description").val() === "")
        {  
            $("#description").focus().after('<span class="error">Ingrese una descripcion</span>');  
            $("#description").select();
            return false;  
	}
        else if ( ($("#description").val() !== "") && (!n.test($("#description").val())) )
        {
            $("#description").focus().after('<span class="error">la descripcion del nuevo perfil no puede contener numeros o simbolos extrañ&oacute;os</span>');                          
            $("#description").select();
            return false;
        }
    });  
    
    
	$("#name, #description").bind('blur keyup', function(){  
        if ($(this).val() !== "") 
        {  			
			$('.error').fadeOut();
			return false;  
	}  
	});	
});