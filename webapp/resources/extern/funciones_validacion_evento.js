$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
	var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var n1 = /^[A-Za-z\s]+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
        
        $(".botonEvento").click(function(){  
		$(".error").fadeOut().remove();
                $(".error4").fadeOut().remove();
		
                
        if ($("#code").val() === "") 
        {  
            $("#code").focus().after('<span class="error">Ingrese el c\u00F3digo del evento</span>'); 
            $("#code").select();
            return false;  
	}
        else if ( ($("#code").val() !== "") && (!cod.test($("#code").val())) )
        {
            $("#code").focus().after('<span class="error">No es un codigo valido, Ingrese un c\u00F3digo</span>');                          
            $("#code").select();
            return false;            
        }
        
        
        if ($("#name").val() === "") 
        {  
            $("#name").focus().after('<span class="error">Ingrese nombre del nuevo evento</span>');  
            $("#name").select();
            return false;  
	}
        else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {   
            $("#name").focus().after('<span class="error">No es un nombre valido, ingrese el nombre nuevamente</span>');                          
            $("#name").select();
            return false;            
        }
        
        
        if ($("#description").val() === "") 
        {  
            $("#description").focus().after('<span class="error">Ingrese la descripci\u00D3n</span>');  
            $("#descripcion").select();
            return false;  
	}
        else if ( ($("#description").val() !== "") && (!n.test($("#description").val())) )
        {   
            $("#description").focus().after('<span class="error">No es una descripci\u00D3n valida, ingrese la descripci\u00D3n nuevamente</span>');
            $("#descripcion").select();
            return false;            
        }
        
        
        

        if ($("#priority").val() === "0") 
        {  
            $("#priority").focus().after('<span class="error4">Seleccione la prioridad</span>');              
            return false;  
	}/*
         else if ( ($("#priority").val() !== "") && (!numbers.test($("#priority").val())) )
        {   
            $("#priority").focus().after('<span class="error">No es un prioridad valida, ingrese la prioridad nuevamente</span>');                          
            $("#priority").select();
            return false;            
        }*/
        
        
                
        if ($("#cant").val() === "") 
        {  
			$("#cant").focus().after('<span class="error">Ingrese la cantidad</span>');  
                        $("#cant").select();
			return false;  
	}
        else if ( ($("#cant").val() !== "") && (!numbers.test($("#cant").val())) )
        {   
            $("#cant").focus().after('<span class="error">No una cantidad valida, Ingrese la cantidad nuevamente</span>');                          
            $("#cant").select();
            return false;            
        }
        
        
        if ($("#type").val() === "0") 
        {  
			$("#type").focus().after('<span class="error4">Seleccione el tipo de evento</span>');                          
			return false;  
	}
        if ($("#tip").val() === "0") 
        {  
			$("#type").focus().after('<span class="error4">Seleccione el tipo de evento</span>');                          
			return false;  
	}
    });  
    
    
	$("#code, #name, #description, #priority, #cant, #type").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
                        $('.error4').fadeOut();
			return false;  
		}  
	});		
});