$(function() { 	
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
         
        $(".botonCentro").click(function(){  
		$(".error").fadeOut().remove();		
                
        if ($("#nom").val() === "") 
        {  
            $("#nom").focus().after('<span class="error">Ingrese el nombre</span>');  
            $("#nom").select();
            return false;  
	}        
        /*else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {   
            $("#name").focus().after('<span class="error">No es un nombre valido, Ingrese el nombre nuevamente</span>');                          
            $("#name").select();
            return false;            
        }*/
        
         if ($("#fk_c").val() === "") 
        {  
            $("#fk_c").focus().after('<span class="error2">Seleccione la ciudad</span>');              
            return false;  
	}
         if ($("#t").val() === "") 
        {  
            $("#t").focus().after('<span class="error">Ingrese el telefono</span>');  
            $("#t").select();
            return false;  
	}
        
    });  
    
    
	$("#nom, #fk_c, #t").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
			return false;  
		}  
	});		
});