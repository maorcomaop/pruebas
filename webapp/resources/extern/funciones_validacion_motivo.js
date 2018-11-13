$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
	
        $(".botonGrupoMovil").click(function(){  
		$(".error").fadeOut().remove();
                
        if ($("#description").val() === "") 
        {  
            $("#description").focus().after('<span class="error">Ingrese la descripci\u00D3n</span>');  
            $("#description").select();
            return false;  
	}
        else if ( ($("#description").val() !== "") && (!n.test($("#description").val())) )
        {   
            $("#description").focus().after('<span class="error">No es un descripci\u00D3n valida, Ingrese la descripci\u00D3n nuevamente</span>');                          
            $("#description").select();
            return false;
        }
    });  
    
    
	$("#description").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
			return false;  
		}  
	});		
});