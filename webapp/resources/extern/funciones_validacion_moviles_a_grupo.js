$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        
         
        $(".botonMovilesAGrupo").click(function(){  
                $(".error1").fadeOut().remove();	
                $(".error2").fadeOut().remove();
                                
        if ($("#group").val() === "0") 
        {  
            $("#group").focus().after('<span class="error1">Seleccione el nombre de un grupo</span>');  
            $("#group").select();
            return false;  
	}
         
        if ($("#cars").val() === null) 
        {  
            $("#cars").focus().after('<span class="error2">Seleccione los veh\u00EDculos que desea agregar al grupo</span>');  
            $("#cars").select();
            return false;  
	}       
                
    });      
    
	$("#group, #cars ").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  						
                        $('.error1').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});