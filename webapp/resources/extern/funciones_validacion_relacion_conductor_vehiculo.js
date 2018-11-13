$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
	
        $(".botonRelacionCV").click(function(){  
		$(".error2").fadeOut().remove();		
                
        if ($("#car").val() === "0") {  
			$("#car").focus().after('<span class="error2">Seleccione un veh\u00CDculo</span>');  
                        $("#car").select();
			return false;  
		}
        if ($("#driver").val() === "0") {  
			$("#driver").focus().after('<span class="error2">Seleccione un conductor</span>');  
                        $("#driver").select();
			return false;  
		}                 
    });      
    
	$("#car, #driver").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error2').fadeOut();
			return false;  
		}  
	});		
});