$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
         
        $(".botonconductor").click(function(){  
		$(".error").fadeOut().remove();		
                $(".error1").fadeOut().remove();	
                $(".error2").fadeOut().remove();	
                
        if ($("#company").val() === "0") 
        {  
            $("#company").focus().after('<span class="error1">Seleccione una empresa</span>');  
            $("#company").select();
            return false;  
	}
                
                
                
        if ($("#name").val() === "") 
        {  
            $("#name").focus().after('<span class="error">Ingrese nombre del nuevo conductor</span>');  
            $("#name").select();
            return false;  
	}
        else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {   
            $("#name").focus().after('<span class="error">No es un nombre valido, Ingrese el nombre nuevamente</span>');                          
            $("#name").select();
            return false;            
        }
                
                
        if ($("#last").val() === "") 
        {  
            $("#last").focus().after('<span class="error">Ingrese el apellido</span>');  
            $("#last").select();
            return false;  
	}
        else if ( ($("#last").val() !== "") && (!n.test($("#last").val())) )
        {   
            $("#last").focus().after('<span class="error">No es un apellido valido, Ingrese el apellido nuevamente</span>');                          
            $("#last").select();
            return false;            
        }        
                

        if ($("#type").val() === "0") 
        {  
            $("#type").focus().after('<span class="error2">Seleccione un tipo de documento</span>');  
            $("#type").select();
            return false;  
	}
        
                
                
                
        if ($("#num_doc").val() === "") 
        {  
            $("#num_doc").focus().after('<span class="error">Ingrese un n\u00FAmero de documento</span>');  
            $("#num_doc").select();
            return false;  
	}
        else if ( ($("#num_doc").val() !== "") && (!numbers.test($("#num_doc").val())) )
        {   
            $("#num_doc").focus().after('<span class="error">No es un n\u00FAmero de documento valido, ingrese un n\u00FAmero de documento nuevamente</span>');                          
            $("#num_doc").select();
            return false;            
        } 
                
                
    });  
    
    
	$("#name, #last, #company, #doc, #num_doc, #type").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
                        $('.error1').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});