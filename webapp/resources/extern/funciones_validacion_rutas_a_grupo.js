$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        var n = /^[a-zA-Z]*$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;
         
        $(".botonRutasAGrupo").click(function(){  
                $(".error1").fadeOut().remove();	
                $(".error2").fadeOut().remove();
                                
        if ($("#group").val() === "0") 
        {  
            $("#group").focus().after('<span class="error1">Seleccione el nombre de un grupo</span>');  
            $("#group").select();
            return false;  
	}
         
        if ($("#route").val() === null) 
        {  
            $("#route").focus().after('<span class="error2">Seleccione los vehiculos que desea agregar al grupo</span>');  
            $("#route").select();
            return false;  
	}       
                
    });      
    
	$("#group, #route ").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  						
                        $('.error1').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});