$(function() { 	
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
         
        $(".botonSoat").click(function(){  
		$(".error").fadeOut().remove();		
                
        if ($("#co").val() === "") 
        {  
            $("#co").focus().after('<span class="error">Ingrese el codigo del poliza</span>');  
            $("#co").select();
            return false;  
	}        
        /*else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) )
        {   
            $("#name").focus().after('<span class="error">No es un nombre valido, Ingrese el nombre nuevamente</span>');                          
            $("#name").select();
            return false;            
        }*/
        
         if ($("#fk_a").val() === "") 
        {  
            $("#fk_a").focus().after('<span class="error">Seleccione una aseguradora</span>');  
            $("#fk_a").select();
            return false;  
	}
        
    });  
    
    
	$("#co, #fk_a").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
			return false;  
		}  
	});		
});