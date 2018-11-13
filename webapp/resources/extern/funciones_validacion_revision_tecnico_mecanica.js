$(function() { 	
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
         
        $(".botonRevisionTM").click(function(){  
		$(".error").fadeOut().remove();		
                
        if ($("#co").val() === "") 
        {  
            $("#co").focus().after('<span class="error">Ingrese el codigo</span>');  
            $("#co").select();
            return false;  
	}        
        /*else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {   
            $("#name").focus().after('<span class="error">No es un nombre valido, Ingrese el nombre nuevamente</span>');                          
            $("#name").select();
            return false;            
        }*/
        
         if ($("#fk_cd").val() === "") 
        {  
            $("#fk_cd").focus().after('<span class="error2">Seleccione una Centro de diagnostico</span>');  
            $("#fk_cd").select();
            return false;  
	}
        
    });  
    
    
	$("#co, #fk_cd").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
			return false;  
		}  
	});		
});