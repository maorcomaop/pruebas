$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
	
        $(".botonAditoria").click(function(){  
		$(".error").fadeOut().remove();
		
                
        if ($("#dpd1").val() === "") {  
			$("#dpd1").focus().after('<span class="error">Seleccione la fecha de inicio</span>');
                        $("#dpd1").select();
			return false;  
		}
        if ($("#dpd2").val() === "") {  
			$("#dpd2").focus().after('<span class="error">Seleccione la fecha de fin</span>');  
                        $("#dpd2").select();
			return false;  
		}          
        if ($("#type").val() === "0") {  
			$("#type").focus().after('<span class="error4">Seleccione al menos un item</span>');  
                        $("#type").select();
			return false;  
		}
    });      
    
	$("#dpd1, #dpd2, #type").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error4').fadeOut();
			return false;  
		}  
	});		
});