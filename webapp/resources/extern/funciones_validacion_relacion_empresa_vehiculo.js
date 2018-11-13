$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
	
        $(".botonRelacionEV").click(function(){  
		$(".error2").fadeOut().remove();
		
                
        if ($("#car").val() === "0") {  
			$("#car").focus().after('<span class="error2">Seleccione un veh\u00CDculo</span>');  
                        $("#car").select();
			return false;  
		}
        if ($("#company").val() === "0") {  
			$("#company").focus().after('<span class="error2">Seleccione la empresa</span>');  
                        $("#company").select();
			return false;  
		}                 
    });      
    
	$("#company, #car").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error2').fadeOut();
			return false;  
		}  
	});		
});