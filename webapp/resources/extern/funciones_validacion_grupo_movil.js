$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
	//var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
        
        $(".botonGrupoMovil").click(function(){  
		$(".error").fadeOut().remove();
                $(".error2").fadeOut().remove();
                
        if ($("#name").val() === "") 
        {  
            $("#name").focus().after('<span class="error">Ingrese nombre del nuevo grupo</span>');  
            $("#name").select();
            return false;  
        }          
        else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {   
            $("#name").focus().after('<span class="error">No es un nombre de grupo valido, Ingrese el nombre de un grupo valido</span>');                          
            $("#name").select();
            return false;            
        }
        
        
        if ($("#company").val() === "0")
        {  
            $("#company").focus().after('<span class="error2">Seleccione la compañia</span>');  
            $("#company").select();
            return false;  
	}        
                
        if ($("#prueba").prop('checked') === true) 
        {
            if ($("#route").val() === null) 
            {                      
                $("#route").focus().after('<span class="error2">Seleccione al menos una ruta</span>');  
                $("#route").select();
                return false;  
            }
            //return false;  
	}                
    });  
    
    
	$("#name, #company, #prueba, #route").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});	
	/*$(".email").bind('blur keyup', function(){  
        if ($(".email").val() != "" && emailreg.test($(".email").val())) {	
			$('.error').fadeOut();  
			return false;  
		}  
	});*/
});