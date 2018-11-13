$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var n1 = /^[A-Za-z+][\s[A-Za-z]+]*$/;
        var numbers = /^[0-9]*$/;
        var cod = /^[a-zA-Z]+[-]+[0-9]*$/;        
        var alfa = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
        
        $(".botonAlarma").click(function(){  
		$(".error").fadeOut().remove();
                $(".error2").fadeOut().remove();
		
                
        /*if ($("#code").val() === "") 
        {  
            $("#code").focus().after('<span class="error">Ingrese el c\u00D3digo de la alarma</span>'); 
            //$("#code").val("");
            $("#code").select();
            return false;  
	}
        else if ( ($("#code").val() !== "") && (!numbers.test($("#code").val())) )
        {
            $("#code").focus().after('<span class="error">Ingrese un valor n\u00FAmerico</span>');                          
            $("#code").select();
            return false;            
        }*/        
                
        if ($("#name").val() === "") 
        {  console.log("ingreso el nombre en blanco");
            $("#name").focus().after('<span class="error">Ingrese nombre de la alarma</span>');  
            $("#name").select();
            return false;  
	}
        else if ( ($("#name").val() !== "") && (!alfa.test($("#name").val())) )
        {
            $("#name").focus().after('<span class="error">No es un nombre valido, Ingrese el nombre de la alarma</span>');                          
            $("#name").select();
            return false;            
        }
        
        
        
        if ($("#code").val() === "0") 
        {            
            $("#code").focus().after('<span class="error2">Seleccione el tipo</span>');  
            return false;  
	}
       
        if ($("#priority").val() === "0") 
        {            
            $("#priority").focus().after('<span class="error2">Seleccione la prioridad</span>');  
            return false;  
	}     
        
        if ($("#measure").val() === "0") 
        {            
            $("#measure").focus().after('<span class="error2">Seleccione una unidad de medida</span>');  
            return false;  
	}  
        /*if ($("#measure").val() === "") 
        {  
            $("#measure").focus().after('<span class="error">Ingrese la unidad de medida</span>');  
            return false;  
	}*/
                 
                  
        
    });  
    
    
	$("#code, #name, #type, #measure, #priority").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
                        $(".error2").fadeOut().remove();
			return false;  
		}  
        if ($(this).val() !== "0") {  			
			$('.error').fadeOut();
                        $(".error2").fadeOut().remove();
			return false;  
		} 
	});		
});